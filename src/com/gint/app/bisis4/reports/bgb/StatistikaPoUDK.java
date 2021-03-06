package com.gint.app.bisis4.reports.bgb;

import java.io.PrintWriter;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.gint.app.bisis4.records.Primerak;
import com.gint.app.bisis4.records.Record;
import com.gint.app.bisis4.records.Subfield;
import com.gint.app.bisis4.reports.Report;
import com.gint.app.bisis4.reports.bgb.StatistikaPoNacinuNabavke.Item;


public class StatistikaPoUDK extends Report {
	
	@Override
	public void init() {
		 nf = NumberFormat.getInstance(Locale.GERMANY);
			nf.setMinimumFractionDigits(2);
			nf.setMaximumFractionDigits(2);
			itemMap.clear();
			log.info("Report initialized.");
		
	}
	
	@Override
	public void finish() {
		log.info("Finishing report...");

		for (List<Item> list : itemMap.values())
			Collections.sort(list);

		for (String key : itemMap.keySet()) {
			List<Item> list = itemMap.get(key);
			PrintWriter out = getWriter(key);
			
			for (Item i : list) {
				out.print(i.toString());
			}
			out.println("</report>");
			out.close();	
		}
		itemMap.clear();
		log.info("Report finished.");
		
	}
	 public void handleRecord(Record rec) {
		 Map<String, Boolean> recMap = new HashMap<String, Boolean>();
		    if (rec == null)
		      return;
		    Subfield udcSubfield = rec.getSubfield("675a");
		    char firstDigit = 'x';
		    String udc = null;
		    if (udcSubfield != null)
		      udc = udcSubfield.getContent().trim();
		    firstDigit = getFirstDigit(udc);
		    if (firstDigit == ' ')
		      firstDigit = 'x';
		    if (firstDigit == '8' && udc.startsWith("821.163.41"))
		      firstDigit = 'd';
		    Date dateInv;
		    boolean recordAdded = false;
		    for (Primerak p : rec.getPrimerci()) {
		        dateInv=p.getDatumInventarisanja();
		        String key = settings.getParam("file") + getFilenameSuffix(dateInv);		        
		        Item i = getItem(getList(key),firstDigit);
		        if (i == null ){
		        	String udkkey = ""+firstDigit;
				    if (firstDigit == '8')
				    	udkkey = "8-strana";
				    else if (firstDigit == 'd')
				    	udkkey = "8-doma\u0107a";
				    else if (firstDigit == 'x')
				    	udkkey = "bez UDK";
		        	i = new Item(udkkey);
		        	Boolean recAdded=recMap.get(key);
		        	if(recAdded==null){
		               i.add(1, 1);
		               recMap.put(key, new Boolean(true));
		            } else {
		              i.add(0, 1);
		            }
		            getList(key).add(i); 
		        }else{
		        	Boolean recAdded=recMap.get(key);
		        	if(recAdded==null){
			           i.add(1, 1);
			           recMap.put(key, new Boolean(true));
			         } else {
			           i.add(0, 1);
			         }
		        }
		    }
		  }
	 
	  public List<Item> getList(String key) {
		    List<Item> list = itemMap.get(key);
		    if (list == null) {
		      list = new ArrayList<Item>();
		      itemMap.put(key, list);
		    }
		    return list;
	  }
	  
	  private Item getItem(List<Item> il,char firstDigit) {
			for (Item i : il){		
			    String key = ""+firstDigit;
			    if (firstDigit == '8')
			      key = "8-strana";
			    else if (firstDigit == 'd')
			      key = "8-doma\u0107a";
			    else if (firstDigit == 'x')
			      key = "bez UDK";
			    if (i.udkDigit.equalsIgnoreCase(key))
                    return i;
			}
		    return null;        
	  }
  
  
  public class Item implements Comparable {
    public String udkDigit;
    public int brNas;
    public int brPr;
    public boolean isTotal;

    public Item(String udkDigit) {
      this.udkDigit = udkDigit;
      this.brNas = 0;
      this.brPr = 0;
      this.isTotal = false;
    }

    public int compareTo(Object o) {
      if (o instanceof Item) {
        Item b = (Item) o;
        return udkDigit.compareTo(b.udkDigit);
      }
      return 0;
    }

    public String toString() {
      StringBuffer buf = new StringBuffer();
      buf.append("\n  <item  total=\"");
      buf.append(isTotal);
      buf.append("\">\n    <udk>");
      buf.append(udkDigit);
      buf.append("</udk>\n    <brNas>");
      buf.append(brNas);
      buf.append("</brNas>\n   <brPr>");
      buf.append(brPr);
      buf.append("</brPr>\n    </item>");
      return buf.toString();
    }

    public void add(int brNas, int brPr) {
      this.brNas += brNas;
      this.brPr += brPr;
    }

    public void addItem(Item i) {
      brNas += i.brNas;
      brPr += i.brPr;
      this.isTotal = true;
    }
  }

  public char getFirstDigit(String s) {
    if (s == null)
      return ' ';
    if (s.length() == 0)
      return ' ';
    int pos = 0;
    if (s.charAt(0) == '(') {
      pos = s.indexOf(')') + 1;
      if (pos == 0 || pos == s.length())
        pos = 1;
    }
    try {
      while (pos < s.length() && (!Character.isDigit(s.charAt(pos))))
        pos++;
      if (pos == s.length())
        return ' ';
      return s.charAt(pos);
    } catch (Exception e) {
      e.printStackTrace();
      return ' ';
    }
  }


  int totalRecords = 0;
  int noInvDate = 0;
  int noInvNum = 0;
  SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy.");
  SimpleDateFormat intern = new SimpleDateFormat("yyyyMMdd");
  SimpleDateFormat intern2 = new SimpleDateFormat("ddMMyy");
  private Map<String, List<Item>> itemMap = new HashMap<String, List<Item>>();
  private static Log log = LogFactory.getLog(StatistikaPoUDK.class);
  NumberFormat nf;


@Override
public void finishOnline(StringBuffer buff) {
	// TODO Auto-generated method stub
	
}

@Override
public void finishInv() {
	// TODO Auto-generated method stub
	
}
  
}
