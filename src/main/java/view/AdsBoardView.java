package view;

import model.*;
import org.hibernate.Session;
import org.hibernate.Transaction;

import javax.swing.*;
import java.awt.*;

public class AdsBoardView implements EntityView<AdsBoard> {
	private Session session;
    private AdsBoard model;
    private JFrame frame;
    
    public Session getSession() {
        return session;
    }

    @Override
    public void setSession(Session session) {
        this.session = session;
    }

    public AdsBoard getModel() {
        return model;
    }

    @Override
    public void setModel(AdsBoard model) {
    	this.model = model;
    }
    
    /**
     * @wbp.parser.entryPoint
     */
    public void invoke(){
        frame = new JFrame();
        frame.setBounds(100, 100, 347, 132);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(null);

        JLabel lblLocation = new JLabel("Location");
        lblLocation.setBounds(12, 13, 56, 16);
        frame.getContentPane().add(lblLocation);

        JTextField textName = new JTextField();
        textName.setBounds(80, 10, 116, 22);
        if(model != null) textName.setText(model.getLocation());
        frame.getContentPane().add(textName);
        textName.setColumns(10);

        JButton btnOk = new JButton("OK");
        btnOk.addActionListener(e -> {
        	Transaction tx1 = session.beginTransaction();
            if (model == null) {
            	model = new AdsBoard(textName.getText());
            } else {
            	model.setLocation(textName.getText());
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
