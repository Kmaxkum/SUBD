package view;

import model.*;
import org.hibernate.Session;
import org.hibernate.Transaction;

import javax.swing.*;
import java.awt.*;

public class ListOfAdsAdsView implements EntityView<ListOfAdsAds>  {
	private Session session;
    private ListOfAdsAds model;
    private JFrame frame;
    
    public Session getSession() {
        return session;
    }

    @Override
    public void setSession(Session session) {
        this.session = session;
    }

    public ListOfAdsAds getModel() {
        return model;
    }

    @Override
    public void setModel(ListOfAdsAds model) {
        this.model = model;
    }
    
    public void invoke(){
    	frame = new JFrame();
        frame.setBounds(100, 100, 347, 194);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(null);

        JLabel lblListOfAds = new JLabel("ListOfAds");
        lblListOfAds.setBounds(33, 13, 56, 16);
        frame.getContentPane().add(lblListOfAds);
        
        JLabel lbAds = new JLabel("Ads");
        lbAds.setBounds(33, 50, 56, 16);
        frame.getContentPane().add(lbAds);
        
        JComboBox comboBoxListOfAds = new JComboBox(this.session.createQuery("FROM model.ListOfAds ORDER BY id").list().toArray());
        if(model != null) comboBoxListOfAds.setSelectedItem(model.getListOfAds());
        comboBoxListOfAds.setBounds(119, 10, 116, 22);
        frame.getContentPane().add(comboBoxListOfAds);
        
        JComboBox comboBoxAds = new JComboBox(this.session.createQuery("FROM model.Ads ORDER BY id").list().toArray());
        if(model != null) comboBoxAds.setSelectedItem(model.getAds());
        comboBoxAds.setBounds(119, 47, 116, 22);
        frame.getContentPane().add(comboBoxAds);
        
        JButton btnOk = new JButton("OK");
        btnOk.addActionListener(e -> {
        	try {
                if (model == null) {
                    model = new ListOfAdsAds(
                            (ListOfAds) comboBoxListOfAds.getSelectedItem(),
                            (Ads) comboBoxAds.getSelectedItem()
                    );
                } else {
                	model.setListOfAds((ListOfAds) comboBoxListOfAds.getSelectedItem());
                	model.setAds((Ads) comboBoxAds.getSelectedItem());
                }
                Transaction tx1 = session.beginTransaction();
                session.saveOrUpdate(model);
                tx1.commit();
                frame.dispose();
            } catch(NumberFormatException ex) {
                JOptionPane.showMessageDialog(this.frame, "Проверьте поля!\n" + ex, "Ошибка!", JOptionPane.ERROR_MESSAGE);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this.frame, ex, "Ошибка!", JOptionPane.ERROR_MESSAGE);
            }
            frame.dispose();
        });
        btnOk.setBounds(12, 109, 97, 25);
        frame.getContentPane().add(btnOk);

        JButton btnCancel = new JButton("Cancel");
        btnCancel.addActionListener(e -> frame.dispose());
        btnCancel.setBounds(220, 109, 97, 25);
        frame.getContentPane().add(btnCancel);
        frame.setVisible(true);
    }
}
