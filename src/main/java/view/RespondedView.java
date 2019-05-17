package view;

import model.*;
import org.hibernate.Session;
import org.hibernate.Transaction;

import javax.swing.*;
import java.awt.*;

public class RespondedView implements EntityView<Responded>{
	private Session session;
    private Responded model;
    private JFrame frame;
    
    public Session getSession() {
        return session;
    }

    @Override
    public void setSession(Session session) {
        this.session = session;
    }

    public Responded getModel() {
        return model;
    }

    @Override
    public void setModel(Responded model) {
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

        JLabel lblName = new JLabel("Name");
        lblName.setBounds(31, 13, 56, 16);
        frame.getContentPane().add(lblName);

        JTextField textName = new JTextField();
        textName.setBounds(118, 10, 199, 22);
        if(model != null) textName.setText(model.getName());
        frame.getContentPane().add(textName);
        textName.setColumns(10);
        
        JLabel lbPhoneNumber = new JLabel("Phone Number");
        lbPhoneNumber.setBounds(12, 42, 97, 16);
        frame.getContentPane().add(lbPhoneNumber);
        
        JTextField textPhoneNumber = new JTextField();
        textPhoneNumber.setBounds(118, 39, 199, 22);
        if(model != null) textPhoneNumber.setText(Integer.toString(model.getPhoneNumber()));
        frame.getContentPane().add(textPhoneNumber);
        textPhoneNumber.setColumns(10);

        JLabel lbEMail = new JLabel("E Mail");
        lbEMail.setBounds(31, 71, 56, 16);
        frame.getContentPane().add(lbEMail);
        
        JTextField textEMail = new JTextField();
        textEMail.setBounds(118, 68, 199, 22);
        if(model != null) textEMail.setText(model.getEMail());
        frame.getContentPane().add(textEMail);
        textEMail.setColumns(10);
        
        JLabel lbState = new JLabel("State");
        lbState.setBounds(31, 105, 56, 16);
        frame.getContentPane().add(lbState);
        
        JComboBox comboBoxState = new JComboBox(this.session.createQuery("FROM model.Status ORDER BY id").list().toArray());
        if(model != null) comboBoxState.setSelectedItem(model.getStatus());
        comboBoxState.setBounds(118, 102, 199, 22);
        frame.getContentPane().add(comboBoxState);
        
        JButton btnOk = new JButton("OK");
        btnOk.addActionListener(e -> {
        	try {
                if (model == null) {
                    model = new Responded(
                    		textName.getText(),
                    		Integer.parseInt(textPhoneNumber.getText()),
                    		textEMail.getText(),
                            (Status) comboBoxState.getSelectedItem()
                    );
                } else {
                	model.setName(textName.getText());
                	model.setPhoneNumber(Integer.parseInt(textPhoneNumber.getText()));
                	model.setEMail(textEMail.getText());
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
