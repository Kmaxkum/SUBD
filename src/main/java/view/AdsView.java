package view;

import model.*;
import org.hibernate.Session;
import org.hibernate.Transaction;

import javax.swing.*;
import java.awt.*;

public class AdsView implements EntityView<Ads> {
	private Session session;
    private Ads model;
    private JFrame frame;
    
    public Session getSession() {
        return session;
    }

    @Override
    public void setSession(Session session) {
        this.session = session;
    }

    public Ads getModel() {
        return model;
    }

    @Override
    public void setModel(Ads model) {
    	this.model = model;
    }
    
    /**
     * @wbp.parser.entryPoint
     */
    public void invoke(){
        frame = new JFrame();
        frame.setBounds(100, 100, 347, 254);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(null);

        JLabel lblName = new JLabel("Create Date");
        lblName.setBounds(15, 13, 94, 16);
        frame.getContentPane().add(lblName);

        JTextField textName = new JTextField();
        textName.setBounds(118, 10, 199, 22);
        if(model != null) textName.setText(model.getCreateDate().toString());
        frame.getContentPane().add(textName);
        textName.setColumns(10);
        
        JLabel lbPhoneNumber = new JLabel("Content");
        lbPhoneNumber.setBounds(31, 42, 56, 16);
        frame.getContentPane().add(lbPhoneNumber);
        
        JTextField textPhoneNumber = new JTextField();
        textPhoneNumber.setBounds(118, 39, 199, 22);
        if(model != null) textPhoneNumber.setText(model.getContent());
        frame.getContentPane().add(textPhoneNumber);
        textPhoneNumber.setColumns(10);

        JLabel lbEMail = new JLabel("Applicant");
        lbEMail.setBounds(31, 71, 56, 16);
        frame.getContentPane().add(lbEMail);
        
        JComboBox comboBoxApplicant = new JComboBox(this.session.createQuery("FROM model.Applicant ORDER BY id").list().toArray());
        if(model != null) comboBoxApplicant.setSelectedItem(model.getApplicant());
        comboBoxApplicant.setBounds(118, 68, 199, 22);
        frame.getContentPane().add(comboBoxApplicant);
                
        JLabel lbState = new JLabel("State");
        lbState.setBounds(31, 105, 56, 16);
        frame.getContentPane().add(lbState);
        
        JComboBox comboBoxState = new JComboBox(this.session.createQuery("FROM model.Status ORDER BY id").list().toArray());
        if(model != null) comboBoxState.setSelectedItem(model.getApplicant());
        comboBoxState.setBounds(118, 102, 199, 22);
        frame.getContentPane().add(comboBoxState);
                
        JButton btnOk = new JButton("OK");
        btnOk.addActionListener(e -> {
        	try {
                if (model == null) {
                    model = new Ads(
                    		java.sql.Date.valueOf(textName.getText()),
                    		textPhoneNumber.getText(),
                    		(Applicant) comboBoxApplicant.getSelectedItem(),
                            (Status) comboBoxState.getSelectedItem()
                    );
                } else {
                	model.setCreateDate(java.sql.Date.valueOf(textName.getText()));
                	model.setContent(textPhoneNumber.getText());
                	model.setApplicant((Applicant) comboBoxApplicant.getSelectedItem());
                	model.setStatus((Status) comboBoxState.getSelectedItem());
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
        btnOk.setBounds(12, 169, 97, 25);
        frame.getContentPane().add(btnOk);

        JButton btnCancel = new JButton("Cancel");
        btnCancel.addActionListener(e -> frame.dispose());
        btnCancel.setBounds(220, 169, 97, 25);
        frame.getContentPane().add(btnCancel);
        frame.setVisible(true);
    }
}
