package com.gint.app.bisis4.client.circ.warnings;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.swing.table.AbstractTableModel;

import org.apache.derby.tools.sysinfo;

import com.gint.app.bisis4.client.circ.model.Lending;
import com.gint.app.bisis4.client.circ.model.Users;
import com.gint.app.bisis4.client.circ.common.Utils;

public class WarningFrameTableModel extends AbstractTableModel {
	
	private List list = null;
	private Date startDate = null;
	private Date endDate = null;
	private int[] check = null;
	private static final int COL_COUNT = 6;
	private String[] columnNames = {"\u0160tampa se", "Broj \u010dlana", "Prezime", "Ime", "Broj telefona", "Broj publikacija"};
	
	public WarningFrameTableModel(List list, Date startDate, Date endDate){
		this.list = list;
		this.startDate = startDate;
		this.endDate = endDate;
		repairDates();
		check = new int[list.size()];
		for (int i = 0; i < check.length; i++){
			check[i] = 1;
		}
	}
	
	public WarningFrameTableModel(){
		
	}
	
	public void setData(List list, Date startDate, Date endDate){
		if (list != null){
			this.list = list;
			this.startDate = startDate;
			this.endDate = endDate;
			repairDates();
			check = new int[list.size()];
			for (int i = 0; i < check.length; i++){
				check[i] = 1;
			}
			fireTableDataChanged();
		}
	}
	
	public void resetModel(){
		list = null;
		check = null;
		fireTableDataChanged();
	}
	
	public boolean printOrNot(int i){
		if (check[i] == 1)
			return true;
		else
			return false;
	}
  
  public void cleanModel(){
	for (int i = check.length-1; i >= 0; i--){
      if (check[i] == 0){
        list.remove(i);
      }
    }
  }
	
	public int getColumnCount(){
		return COL_COUNT;
	}
	
	public int getRowCount(){
		if (list == null){
			return 0;
		}else{
			return list.size();
		}
	}
	
	public String getColumnName(int col) {
        return columnNames[col];
    }
	
	public Object getValueAt(int row, int col){
		
		switch (col){
		 case 0: if (check[row] == 1){
					 return new Boolean(true);
				 }else{
					 return new Boolean(false);
				 }
		 case 1: return ((Users)list.get(row)).getUserId();
//		 case 2: return ((Users)((Map)list.get(row)).get("users")).getLastName();
		 case 2: return ((Users)list.get(row)).getLastName();
		 case 3: return ((Users)list.get(row)).getFirstName();
		 case 4: return ((Users)list.get(row)).getPhone();
		 case 5: return getWarnLendings(((Users)list.get(row)).getLendings());
		 default: return null;
		}
	}
  
  private int getWarnLendings(Set<Lending> set){
    int i = 0;
    for(Lending l : set){
      if (l.getDeadline().compareTo(startDate) >= 0 && l.getDeadline().compareTo(endDate) <= 0){
        i++;
      }
    }
    return i;
  }
	
	public Class getColumnClass(int col) {
		if (col == 0){
			return Boolean.class;
		}else if (col == 5){
			return Integer.class;
		}else {
			return String.class;
		}
    }
	
	public boolean isCellEditable(int row, int col) {
        if (col == 0) {
            return true;
        } else {
            return false;
        }
    }
	
	public void setValueAt(Object value, int row, int col) {
		if (col == 0){
			if (((Boolean)value).booleanValue() == true){
				check[row] = 1;
			}else{
				check[row] = 0;
			}
			fireTableCellUpdated(row, col);
		}
	}
  
  private void repairDates(){
    if (startDate == null) {
      endDate = Utils.setMaxDate(endDate);
      startDate = Utils.setMinDate(endDate);
    } else if (endDate == null) {
      endDate = Utils.setMaxDate(startDate);
      startDate = Utils.setMinDate(startDate);
    } else {
      startDate = Utils.setMinDate(startDate);
      endDate = Utils.setMaxDate(endDate);
    }
  }

}
