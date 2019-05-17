package view;

import model.*;
import org.hibernate.Session;
import org.hibernate.Transaction;

import javax.swing.*;
import java.awt.*;

public class RespondedAdsView implements EntityView<RespondedAds>  {
	private Session session;
    private RespondedAds model;
    private JFrame frame;
    
    public Session getSession() {
        return session;
    }

    @Override
    public void setSession(Session session) {
        this.session = session;
    }

    public RespondedAds getModel() {
        return model;
    }

    @Override
    public void setModel(RespondedAds model) {
        this.model = model;
    }
    
    public void invoke(){
    	frame = new JFrame();
        frame.setBounds(100, 100, 347, 194);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(null);

        JLabel lbResponded = new JLabel("Responded");
        lbResponded.setBounds(33, 13, 56, 16);
        frame.getContentPane().add(lbResponded);
        
        JLabel lbAds = new JLabel("Ads");
        lbAds.setBounds(33, 50, 56, 16);
        frame.getContentPane().add(lbAds);
        
        JComboBox comboBoxResponded = new JComboBox(this.session.createQuery("FROM model.Responded ORDER BY id").list().toArray());
        if(model != null) comboBoxResponded.setSelectedItem(model.getResponded());
        comboBoxResponded.setBounds(119, 10, 116, 22);
        frame.getContentPane().add(comboBoxResponded);
        
        JComboBox comboBoxAds = new JComboBox(this.session.createQuery("FROM model.Ads ORDER BY id").list().toArray());
        if(model != null) comboBoxAds.setSelectedItem(model.getAds());
        comboBoxAds.setBounds(119, 47, 116, 22);
        frame.getContentPane().add(comboBoxAds);
        
        JButton btnOk = new JButton("OK");
        btnOk.addActionListener(e -> {
        	try {
                if (model == null) {
                    model = new RespondedAds(
                            (Responded) comboBoxResponded.getSelectedItem(),
                            (Ads) comboBoxAds.getSelectedItem()
                    );
                } else {
                	model.setResponded((Responded) comboBoxResponded.getSelectedItem());
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
