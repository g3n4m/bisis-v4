package com.gint.app.bisis4.reports.gbns;

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
import java.util.regex.Pattern;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.gint.app.bisis4.format.HoldingsDataCodersJdbc;
import com.gint.app.bisis4.records.Primerak;
import com.gint.app.bisis4.records.Record;
import com.gint.app.bisis4.reports.Report;
import com.gint.app.bisis4.utils.LatCyrUtils;

public class PresigliraneMonografske extends Report {
  
 
	 @Override
	  public void init() {
			nf = NumberFormat.getInstance(Locale.GERMANY);
			nf.setMinimumFractionDigits(2);
			nf.setMaximumFractionDigits(2);
			itemMap.clear();
			pattern = Pattern
					.compile(getReportSettings().getParam("invnumpattern"));
			log.info("Report initialized.");
	  }
	  public void finishInv() {
		  
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
			}
			closeFiles();
			itemMap.clear();
			log.info("Report finished.");
	  }
	  public List<Item> getList(String key) {
			List<Item> list = itemMap.get(key);
			if (list == null) {
				list = new ArrayList<Item>();
				itemMap.put(key, list);
			}
			return list;
		}
	  @Override
	  public void handleRecord(Record rec) {
		  if (rec == null)
		      return;
			    
		    for (Primerak p : rec.getPrimerci()) {
		    	String invBr=p.getInvBroj();
		    	if (invBr == null)
		    		continue;
		    	if (p.getStatus()==null)
		    		continue;
		    	if(!p.getStatus().equals("5"))
		    		continue;
		      	Date  presiglirnoDate = p.getDatumStatusa(); 
		    	String ogr=p.getOdeljenje();

		    	String key = settings.getParam("file")+ getFilenameSuffix(presiglirnoDate);
				Item item = getItem(getList(key), ogr);
			      if (item == null ){
			         	item=new Item(ogr);
			         	item.presiglirano++;
			         	getList(key).add(item);	
			      }else{
			    	  item.presiglirano++;
			      }
		      }
		    }
		  
	      
	  
	  public String nvl(String s) {
	    return s == null ? "" : s;
	  }
	 
	  
	  public Item getItem(List<Item> iteml, String sigla) {
			
			for (Item it : iteml){
				
				if (it.sigla.compareToIgnoreCase(sigla)==0){	
					return it;
				}
			}
		    return null;
		    
		    
		  }

	  public class Item implements Comparable  {
		  String sigla;
		  int presiglirano;

		 
		    

		    public Item(String sigla) {
				super();
				this.sigla = sigla;
				this.presiglirano = 0;
			}
		    
		    public int compareTo(Object o) {
			      if (o instanceof Item) {
			        Item b = (Item)o;
			        return sigla.compareTo(b.sigla);
			      }
			      return 0;
			    }
			
		    public String toString() {
		      StringBuffer buf = new StringBuffer();
		      buf.append("\n  <item> \n <sigla>");
		      buf.append(sigla);
		      buf.append("</sigla>\n");
		      buf.append("<ogranak>");
		      buf.append(LatCyrUtils.toCyrillic(HoldingsDataCodersJdbc.getValue(HoldingsDataCodersJdbc.ODELJENJE_CODER, sigla)));
		      buf.append("</ogranak>\n    <presiglirano>");
		      buf.append(presiglirano);
		      buf.append("</presiglirano>\n  </item>");
		      return buf.toString();
		    }
		    		   
		  }
	 
	 
	  SimpleDateFormat intern = new SimpleDateFormat("yyyy");

	  private Pattern pattern;
	  private Map<String, List<Item>> itemMap = new HashMap<String, List<Item>>();
	  private static Log log = LogFactory.getLog(PresigliraneMonografske.class);
	  NumberFormat nf;
	@Override
	public void finishOnline(StringBuffer buff) {
		// TODO Auto-generated method stub
		
	}
	}
