package view;

import model.*;

import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;
import java.util.stream.Collectors;

public class TableModel<T> extends DefaultTableModel {
    private ArrayList<T> entities;
    private Class<T> type;

    public TableModel(ArrayList<T> entities) {
        this.entities = entities;
        this.type = (Class<T>) entities.get(0).getClass();

        String[] columnsNames = new String[0];
        ArrayList<Object[]> rows = new ArrayList<>();

        switch (type.getName()) {
	        case "model.AdsBoard":
	        	rows = (ArrayList<Object[]>) ((ArrayList<AdsBoard>) this.entities).stream()
		        	.map(e -> new Object[] {
		        			e.getLocation()
		        	})
		        	.collect(Collectors.toList());
	        	this.setDataVector(rows.toArray(new Object[0][]), new Object[] {"location"});
	        	break;
	        case "model.ListOfAds":
	        	rows = (ArrayList<Object[]>) ((ArrayList<ListOfAds>) this.entities).stream()
	        		.map(e -> new Object[] {
	        				e.getDateFrom(),
	        				e.getDateTo(),
	        				e.getRelevance(),
	        				e.getAdsBoard().getLocation()
	        		})
	        		.collect(Collectors.toList());
	        	this.setDataVector(rows.toArray(new Object[0][]), new Object[] {"date_from", "date_to", "relevance", "board location"});
	        	break;
	        case "model.ListOfAdsAds":
	        	rows = (ArrayList<Object[]>) ((ArrayList<ListOfAdsAds>) this.entities).stream()
	        		.map(e -> new Object[] {
	        				e.getListOfAds().toString(),
	        				e.getAds().toString()
	        		})
	        		.collect(Collectors.toList());
	        	this.setDataVector(rows.toArray(new Object[0][]), new Object[] {"ListToAds", "Ads"});
	        	break; 
	        case "model.Status":
	        	rows = (ArrayList<Object[]>) ((ArrayList<Status>) this.entities).stream()
	        		.map(e -> new Object[] {
	        				e.getState()
	        		})
	        		.collect(Collectors.toList());
	        	this.setDataVector(rows.toArray(new Object[0][]), new Object[] {"state"});
	        	break;
	        case "model.Applicant":
	        	rows = (ArrayList<Object[]>) ((ArrayList<Applicant>) this.entities).stream()
	        		.map(e -> new Object[] {
	        				e.getName(),
	        				"" + e.getPhoneNumber(),
	        				e.getEMail(),
	        				e.getStatus().toString()
	        		})
	        		.collect(Collectors.toList());
	        	this.setDataVector(rows.toArray(new Object[0][]), new Object[] {"Name", "Phone Number", "E Mail", "Status"});
	        	break;
	        case "model.Responded":
	        	rows = (ArrayList<Object[]>) ((ArrayList<Responded>) this.entities).stream()
	        		.map(e -> new Object[] {
	        				e.getName(),
	        				"" + e.getPhoneNumber(),
	        				e.getEMail(),
	        				e.getStatus().toString()
	        		})
	        		.collect(Collectors.toList());
	        	this.setDataVector(rows.toArray(new Object[0][]), new Object[] {"Name", "Phone Number", "E Mail", "Status"});
	        	break;
	        case "model.Ads":
	        	rows = (ArrayList<Object[]>) ((ArrayList<Ads>) this.entities).stream()
	        		.map(e -> new Object[] {
	        				e.getCreateDate(),
	        				e.getContent(),
	        				e.getApplicant().toString(),
	        				e.getStatus().toString()
	        		})
	        		.collect(Collectors.toList());
	        	this.setDataVector(rows.toArray(new Object[0][]), new Object[] {"Create Date", "Content", "Applicant", "Status"});
	        	break;
	        case "model.RespondedAds":
	        	rows = (ArrayList<Object[]>) ((ArrayList<RespondedAds>) this.entities).stream()
	        		.map(e -> new Object[] {
	        				e.getResponded().toString(),
	        				e.getAds().toString()
	        		})
	        		.collect(Collectors.toList());
	        	this.setDataVector(rows.toArray(new Object[0][]), new Object[] {"Responded", "Ads"});
	        	break;
        }
    }

    public T getEntity(int index) {
        return this.entities.get(index);
    }

    @Override
    public boolean isCellEditable(int row, int col) {
        return false;
    }
}
