package view;

import model.*;
import org.hibernate.Session;
import org.hibernate.Transaction;

import javax.swing.*;
import java.awt.*;

public class ListOfAdsView implements EntityView<ListOfAds> {
	private Session session;
	private ListOfAds model;
	
	/**
	 * @wbp.parser.constructor
	 */
	
	@Override
    public void setSession(Session session) {
        this.session = session;
    }

    public ListOfAds getModel() {
        return model;
    }

    @Override
    public void setModel(ListOfAds model) {
        this.model = model;
    }
	
	/**
	 * @wbp.parser.entryPoint
	 */
	public void invoke() {
		JFrame frame = new JFrame();
        frame.setBounds(100, 100, 316, 380);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(null);
                
        JTextField textDateFrom = new JTextField();
        textDateFrom.setBounds(110, 13, 116, 22);
        if (model != null) textDateFrom.setText(model.getDateFrom().toString());
        frame.getContentPane().add(textDateFrom);
        textDateFrom.setColumns(10);

        JTextField textDateTo = new JTextField();
        textDateTo.setBounds(110, 48, 116, 22);
        if (model != null) textDateTo.setText(model.getDateTo().toString());
        frame.getContentPane().add(textDateTo);
        textDateTo.setColumns(10);

        JTextField textRelevance = new JTextField();
        textRelevance.setBounds(110, 83, 116, 22);
        if (model != null) textRelevance.setText(model.getRelevance().toString());
        frame.getContentPane().add(textRelevance);
        textRelevance.setColumns(10);

        JComboBox comboBoxAdsBoard = new JComboBox(this.session.createQuery("FROM model.AdsBoard ORDER BY id").list().toArray());
        if(model != null) comboBoxAdsBoard.setSelectedItem(model.getAdsBoard());
        comboBoxAdsBoard.setBounds(110, 188, 116, 22);
        frame.getContentPane().add(comboBoxAdsBoard);

        JLabel lblDataFrom = new JLabel("Data From");
        lblDataFrom.setBounds(12, 16, 86, 16);
        frame.getContentPane().add(lblDataFrom);

        JLabel lblDateTo = new JLabel("Date To");
        lblDateTo.setBounds(12, 51, 71, 16);
        frame.getContentPane().add(lblDateTo);

        JLabel lblRelevance = new JLabel("Relevance");
        lblRelevance.setBounds(12, 86, 71, 16);
        frame.getContentPane().add(lblRelevance);

        JLabel lblAdsBoard = new JLabel("Ads Board");
        lblAdsBoard.setBounds(12, 191, 56, 16);
        frame.getContentPane().add(lblAdsBoard);
        
        JButton btnOk = new JButton("OK");
        btnOk.addActionListener(e -> {
            Transaction tx1 = session.beginTransaction();
            if (model == null) {
            	model = new ListOfAds(
            			java.sql.Date.valueOf(textDateFrom.getText()),
            			java.sql.Date.valueOf(textDateTo.getText()),
                        Boolean.parseBoolean(textRelevance.getText()),
                        (AdsBoard) comboBoxAdsBoard.getSelectedItem()
                );
            } else {
            	model.setDateFrom(java.sql.Date.valueOf(textDateFrom.getText()));
            	model.setDateTo(java.sql.Date.valueOf(textDateTo.getText()));
            	model.setRelevance(Boolean.parseBoolean(textRelevance.getText()));
            	model.setAdsBoard((AdsBoard) comboBoxAdsBoard.getSelectedItem());
            }
            session.saveOrUpdate(model);
            
            tx1.commit();
            frame.dispose();
        });
        btnOk.setBounds(80, 293, 97, 25);
        frame.getContentPane().add(btnOk);
        
        JButton btnCancel = new JButton("Cancel");
        btnCancel.addActionListener(e -> frame.dispose());
        btnCancel.setBounds(189, 293, 97, 25);
        frame.getContentPane().add(btnCancel);
        frame.setVisible(true);
	}
}
