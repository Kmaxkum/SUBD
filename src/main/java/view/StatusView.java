package view;

import model.*;
import org.hibernate.Session;
import org.hibernate.Transaction;

import javax.swing.*;
import java.awt.*;

public class StatusView implements EntityView<Status>{
	private Session session;
    private Status model;
    private JFrame frame;
    
    public Session getSession() {
        return session;
    }

    @Override
    public void setSession(Session session) {
        this.session = session;
    }

    public Status getModel() {
        return model;
    }

    @Override
    public void setModel(Status model) {
    	this.model = model;
    }
    
    public void invoke(){
        frame = new JFrame();
        frame.setBounds(100, 100, 347, 132);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(null);

        JLabel lblState = new JLabel("State");
        lblState.setBounds(12, 13, 56, 16);
        frame.getContentPane().add(lblState);

        JTextField textName = new JTextField();
        textName.setBounds(80, 10, 116, 22);
        if(model != null) textName.setText(model.getState());
        frame.getContentPane().add(textName);
        textName.setColumns(10);

        JButton btnOk = new JButton("OK");
        btnOk.addActionListener(e -> {
        	Transaction tx1 = session.beginTransaction();
            if (model == null) {
            	model = new Status(textName.getText());
            } else {
            	model.setState(textName.getText());
            }
            session.saveOrUpdate(model);
            tx1.commit();
            frame.dispose();
        });
        btnOk.setBounds(111, 47, 97, 25);
        frame.getContentPane().add(btnOk);

        JButton btnCancel = new JButton("Cancel");
        btnCancel.addActionListener(e -> frame.dispose());
        btnCancel.setBounds(220, 47, 97, 25);
        frame.getContentPane().add(btnCancel);
        frame.setVisible(true);
    }
}
