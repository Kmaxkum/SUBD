package view;

import database.ConnectionInfo;
import model.*;
import org.hibernate.*;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class MainView {
    private JFrame frame;
    private JLabel currentEntityLabel;
    private JTable table;
    private JLabel statusLabel;

    private ConnectionInfo connectionInfo;
    private Session session;
    private Class currentEntity;
    private EntityView<?> currentEntityView;

    public MainView() {
        this.connectionInfo = null;
        this.session = null;
        this.currentEntity = null;

        this.initUI();
        Runtime.getRuntime().addShutdownHook(new Thread(this::disconnect));
    }

    private void initUI() {
        this.frame = new JFrame();
        this.frame.setSize(1280, 720);
        this.frame.setLocationRelativeTo(null);
        this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JMenuBar menuBar = new JMenuBar();
        JMenu connectionMenu = new JMenu("Подключение к БД");
        {
            JMenuItem newConnectMenuItem = new JMenuItem("Подключение к БД");
            newConnectMenuItem.addActionListener(event -> this.connect());
            connectionMenu.add(newConnectMenuItem);

            JMenuItem disconnectMenuItem = new JMenuItem("Отключится от БД");
            disconnectMenuItem.addActionListener(event -> this.disconnect());
            connectionMenu.add(disconnectMenuItem);

            JMenuItem exitMenuItem = new JMenuItem("Выход");
            exitMenuItem.addActionListener(event -> System.exit(0));
            connectionMenu.add(exitMenuItem);

            menuBar.add(connectionMenu);
        }

        JMenu tablesMenu = new JMenu("Справочники");
        {
        	JMenuItem openAdsBoardsMenuItem = new JMenuItem("AdsBoard");
        	openAdsBoardsMenuItem.addActionListener(event -> this.openTable(AdsBoard.class, new AdsBoardView()));
            tablesMenu.add(openAdsBoardsMenuItem);
            
            JMenuItem openListOfAdsMenuItem = new JMenuItem("ListOfAds");
            openListOfAdsMenuItem.addActionListener(event -> this.openTable(ListOfAds.class, new ListOfAdsView()));
            tablesMenu.add(openListOfAdsMenuItem);
            
            JMenuItem openListOfAdsAdsMenuItem = new JMenuItem("ListOfAdsAds");
            openListOfAdsAdsMenuItem.addActionListener(event -> this.openTable(ListOfAdsAds.class, new ListOfAdsAdsView()));
            tablesMenu.add(openListOfAdsAdsMenuItem);
            
            JMenuItem openStatusMenuItem = new JMenuItem("Status");
            openStatusMenuItem.addActionListener(event -> this.openTable(Status.class, new StatusView()));
            tablesMenu.add(openStatusMenuItem);
            
            JMenuItem openApplicantMenuItem = new JMenuItem("Applicant");
            openApplicantMenuItem.addActionListener(event -> this.openTable(Applicant.class, new ApplicantView()));
            tablesMenu.add(openApplicantMenuItem);
            
            JMenuItem openRespondedMenuItem = new JMenuItem("Responded");
            openRespondedMenuItem.addActionListener(event -> this.openTable(Responded.class, new RespondedView()));
            tablesMenu.add(openRespondedMenuItem);
            
            JMenuItem openAdsMenuItem = new JMenuItem("Ads");
            openAdsMenuItem.addActionListener(event -> this.openTable(Ads.class, new AdsView()));
            tablesMenu.add(openAdsMenuItem);
            
            JMenuItem openRespondedAdsMenuItem = new JMenuItem("RespondedAds");
            openRespondedAdsMenuItem.addActionListener(event -> this.openTable(RespondedAds.class, new RespondedAdsView()));
            tablesMenu.add(openRespondedAdsMenuItem);
            
            menuBar.add(tablesMenu);
        }

        this.frame.setJMenuBar(menuBar);

        this.currentEntityLabel = new JLabel();
        this.frame.getContentPane().add(this.currentEntityLabel, BorderLayout.NORTH);

        this.table = new JTable();
        this.table.setDragEnabled(false);
        this.table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        this.frame.getContentPane().add(new JScrollPane(this.table));

        this.statusLabel = new JLabel("Отключено");
        this.frame.getContentPane().add(this.statusLabel, BorderLayout.SOUTH);

        JPanel buttonsPanel = new JPanel();
        {
            buttonsPanel.setLayout(new BoxLayout(buttonsPanel, BoxLayout.Y_AXIS));

            JButton addButton = new JButton("Добавить запись");
            addButton.setAlignmentX(Component.CENTER_ALIGNMENT);
            addButton.addActionListener(event -> {
                try {
                    this.addEntity(this.currentEntity, this.currentEntityView);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
            buttonsPanel.add(addButton);

            JButton editButton = new JButton("Изменить запись");
            editButton.setAlignmentX(Component.CENTER_ALIGNMENT);
            editButton.addActionListener(event -> {
                try {
                    this.editEntity(this.currentEntity, this.currentEntityView);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
            buttonsPanel.add(editButton);

            JButton deleteButton = new JButton("Удалить запись");
            deleteButton.setAlignmentX(Component.CENTER_ALIGNMENT);
            deleteButton.addActionListener(event -> this.deleteEntity(this.currentEntity));
            buttonsPanel.add(deleteButton);

            JButton refreshButton = new JButton("Обновить таблицу");
            refreshButton.setAlignmentX(Component.CENTER_ALIGNMENT);
            refreshButton.addActionListener(event -> this.refreshTable());
            buttonsPanel.add(refreshButton);

        }

        this.frame.getContentPane().add(buttonsPanel, BorderLayout.EAST);
    }

    private void refreshTable() {
        if (this.currentEntity != null) this.openTable(this.currentEntity, this.currentEntityView);
    }

    private void connect() {
        ConnectDialog connectDialog = new ConnectDialog(this.frame);
        connectDialog.setVisible(true);
        if (connectDialog.isAccepted()) {
            this.connectionInfo = connectDialog.getConnectionInfo();
            try {
                SessionFactory sessionFactory = this.connectionInfo.getSessionFactory();
                this.session = sessionFactory.openSession();
                this.statusLabel.setText(String.format("Подключено: %s", this.connectionInfo.toString()));
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this.frame, ex, "Ошибка при подключении", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private boolean tryConnect() {
        if (this.session == null) {
            this.connect();
            if (this.session == null) {
                return false;
            }
        }
        return true;
    }

    private void disconnect() {
        if (this.session != null) {
            try {
                this.session.close();
            } catch (Exception ignored) {

            }
        }
        this.session = null;
        this.statusLabel.setText("Отключено");
    }

    private <T> void openTable(Class<T> entity, EntityView<T> entityView) {
        if (!this.tryConnect()) {
            return;
        }

        List q = this.session.createQuery(String.format("FROM %s ORDER BY id", entity.getName())).list();
        ArrayList<T> models = (ArrayList<T>) q
                .stream()
                .map(e -> entity.cast(e))
                .collect(Collectors.toList());

        this.currentEntity = entity;
        if (!models.isEmpty()) {
            this.table.setModel(new view.TableModel(models));
            this.currentEntityLabel.setText(models.get(0).getClass().getName());
            this.currentEntityView = entityView;
        }
    }

    private <T> void addEntity(Class<T> e, EntityView<T> ev) throws Exception {
        if (e == null) throw new Exception("no");
        T s = null;
        ev.setSession(this.session);
        ev.setModel(s);
        ev.invoke();
    }

    private <T> void editEntity(Class<T> e, EntityView<T> ev) throws Exception {
        if (e == null) throw new Exception("no");

        T s = e.cast(((view.TableModel) this.table.getModel()).getEntity(this.table.getSelectedRow()));
        ev.setSession(this.session);
        ev.setModel(s);
        ev.invoke();
    }

    private <T> void deleteEntity(Class<T> c) {
        if (!this.tryConnect() || this.table == null || this.table.getModel().getRowCount() <= 0 || this.table.getSelectedRow() == -1) {
            return;
        }

        view.TableModel tableModel = (view.TableModel) this.table.getModel();
        T model = c.cast(tableModel.getEntity(this.table.getSelectedRow()));

        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            this.session.delete(model);
            transaction.commit();
        } catch (HibernateException ex) {
            if (transaction != null) {
                transaction.rollback();
                JOptionPane.showMessageDialog(this.frame, ex, "Ошибка при выполнении транзакции", JOptionPane.ERROR_MESSAGE);
            }
        }

        this.openTable(this.currentEntity, this.currentEntityView);
    }

    public void setVisible() {
        this.frame.setVisible(true);
    }
}
