package com.gint.app.bisis4.reports.gbns;

import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.regex.Pattern;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.gint.app.bisis4.records.Primerak;
import com.gint.app.bisis4.records.Record;
import com.gint.app.bisis4.reports.Period;
import com.gint.app.bisis4.reports.Report;
import com.gint.app.bisis4.utils.FormatBigDecimal;
import com.gint.app.bisis4.utils.Signature;
import com.gint.util.string.StringUtils;

public class InvKnjigaZaRukopisnuGradju extends Report {
	public class Item implements Comparable {
	    public String invbr;
	    public Date datum;
	    public String opis;
	    public String vrGradje;
	    public String dim;
	    public String publikovano;
	    public String nabavkaO;
	    public String nabavkaK;
	    public String nabavkaR;
	    public String nabavkaP;
	    public String cena;
	    public String sig;
	    public String napomena;
	    public String ogr;
	    
	    public int compareTo(Object o) {
	      if (o instanceof Item) {
	        Item b = (Item)o;
	        return invbr.compareTo(b.invbr);
	      }
	      return 0;
	    }

	    public String toString() {
	      StringBuffer buf = new StringBuffer();
	      buf.append("\n  <item>\n    <rbr>");
	      buf.append(invbr);
	      buf.append("</rbr>\n    <datum>");
	      buf.append(sdf.format(datum));
	      buf.append("</datum>\n    <opis>");
	      buf.append(StringUtils.adjustForHTML(opis));
	      buf.append("</opis>\n    <vrGradje>");
	      buf.append(vrGradje);
	      buf.append("</vrGradje>\n    <dim>");
	      buf.append(StringUtils.adjustForHTML(dim));
	      buf.append("</dim>\n    <publikovano>");
	      buf.append(StringUtils.adjustForHTML(publikovano));
	      buf.append("</publikovano>\n     <nabavkaO>");
		  buf.append(StringUtils.adjustForHTML(nabavkaO));
		  buf.append("</nabavkaO>\n   <nabavkaK>");
		  buf.append(StringUtils.adjustForHTML(nabavkaK));
		  buf.append("</nabavkaK>\n   <nabavkaR>");
		  buf.append(StringUtils.adjustForHTML(nabavkaR));
		  buf.append("</nabavkaR>\n   <nabavkaP>");
		  buf.append(StringUtils.adjustForHTML(nabavkaP));
		  buf.append("</nabavkaP>\n   <cena>");
	      buf.append(StringUtils.adjustForHTML(cena));
	      buf.append("</cena>\n    <signatura>");
	      buf.append(StringUtils.adjustForHTML(sig));
	      buf.append("</signatura>\n    <napomena>");
	      buf.append(StringUtils.adjustForHTML(napomena));
	      buf.append("</napomena>\n  </item>");
	      return buf.toString();
	    }
	  }

	 @Override
	  public void init() {
		  itemMap.clear();
		    pattern = Pattern.compile(getReportSettings().getParam("invnumpattern"));
		    log.info("Report initialized.");
	  }
	  public void finishInv() {  //zbog inventerni one se snimaju u fajl po segmentima a ne sve od jednom
		  log.info("Finishing report...");
		    for (List<Item> list : itemMap.values())
		      Collections.sort(list);
		    
		    for (String key : itemMap.keySet()) {
		      List<Item> list = itemMap.get(key);
		      PrintWriter out = getWriter(key);
		      for (Item i : list){
		    	  System.out.println("ovde");
		    	   out.print(i.toString());
		    	   out.flush();
		      }
		 
		    }
		    itemMap.clear();
		    log.info("Report finished.");
	  }
	  @Override
	  public void finish() {
		  log.info("Finishing report...");
		    for (List<Item> list : itemMap.values())
		      Collections.sort(list);
		    
		    for (String key : itemMap.keySet()) {
		      List<Item> list = itemMap.get(key);
		      PrintWriter out = getWriter(key);
		      for (Item i : list){
		    	   out.print(i.toString());
		      }
		     
		    }
		  //  closeFiles();
		    itemMap.clear();
		    log.info("Report finished.");
	  }

  @Override
  public void handleRecord(Record rec) {
    if (rec == null)
      return;
 
    String naslov = rec.getSubfieldContent("200a");
    if (naslov == null)
      naslov = "";
    String autor = getAutor(rec); 
    if (autor == null)
      autor = "";
    autor.trim();
    String mesto = rec.getSubfieldContent("210a");
    if (mesto == null)
    	mesto = "";
    String opisDela = rec.getSubfieldContent("200b");
    if (opisDela == null)
    	opisDela = "";
    String god = rec.getSubfieldContent("210d");
    if (god == null)
      god = "";
    
    
    StringBuffer opis = new StringBuffer();
    opis.append(autor);
    if (opis.length() > 0)
      opis.append(". ");
    opis.append(naslov);
    opis.append(": ");
    opis.append(opisDela);
    opis.append(", ");
    
    if (mesto.length() > 0)
      opis.append(". - ");
    opis.append(mesto);
    
    String dim = rec.getSubfieldContent("215d");
    if (dim == null)
      dim = " ";
    String vrGradje = rec.getSubfieldContent("001b");  
    if (vrGradje == null){
    	vrGradje=opisDela;	
    }else{
     vrGradje=vrGradje+", "+opisDela;
    }
    String publikovano = " "; // NISU STAVILI IZ KOG POLJA SE VADI PODATAK
    if (publikovano == null)
    	publikovano = " ";
    
    String sig = " ";

    for (Primerak p : rec.getPrimerci()) {
    	
      if(p.getInvBroj()==null)
    	  continue;
      if (p.getInvBroj().substring(0, 2).compareToIgnoreCase("31")==0){
		  if(p.getInvBroj().substring(5, 7).compareToIgnoreCase("10")!=0)
			  continue;
      }else if (p.getInvBroj().substring(2, 4).compareToIgnoreCase("10")!=0){
	      continue;
      }
      sig = Signature.format(p.getSigDublet(), p.getSigPodlokacija(), 
          p.getSigIntOznaka(), p.getSigFormat(), p.getSigNumerusCurens(), 
          p.getSigUDK());
      if (sig.equals(""))
    	  sig=" ";
      Item i = new Item();
      i.invbr =  nvl(p.getInvBroj());
      i.datum = p.getDatumInventarisanja();
      i.opis = opis.toString();
      i.vrGradje = vrGradje;
      i.publikovano = publikovano;
      i.dim = dim;
      String dobavljac=nvl(p.getDobavljac());
      String vrnab = nvl(p.getNacinNabavke());
//    ******************    NABAVKA NIJE ZAVRSENA   *************************
      String nabavkaO=" ";
      String nabavkaK=" ";
      String nabavkaR=" ";
      String nabavkaP=" ";
      if (vrnab.equals("c") || vrnab.equals("p")) {
    	  nabavkaP = "poklon";
          if (dobavljac!="" && dobavljac!=" ")
            nabavkaP += ", " + dobavljac;
        } else if (vrnab.equals("a") || vrnab.equals("k")) {
          nabavkaK = "kupovina";
          if (dobavljac!="" && dobavljac!=" ")
            nabavkaK += ", " + dobavljac;
          String brRac=nvl(p.getBrojRacuna());
          if (brRac!="" && brRac!=" ")
              nabavkaK += ", " + brRac;
            
        } else if (vrnab.equals("b")) {
          nabavkaR = "razmena";
        } else if (vrnab.equals("d")) {
          nabavkaO = "obavezni primerak";
        } else if (vrnab.equals("e")) {
          //nabavka = "zate\u010deni fond";
        } else if (vrnab.equals("f") || vrnab.equals("s")) {
          //nabavka = "sopstvena izdanja";
        } else if (vrnab.equals("o")) {
          //nabavka = "otkup";
        }
      i.cena = p.getCena() == null ? " " : 
        FormatBigDecimal.format(p.getCena(), 2).toPlainString();
      i.sig = sig;
      i.napomena = nvl(p.getNapomene());
      String key = settings.getParam("file") + getFilenameSuffix(p.getDatumInventarisanja());
      getList(key).add(i);
      
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
  
  
  public String getAutor(Record rec) {
    if (rec.getField("700") != null) {
      String sfa = rec.getSubfieldContent("700a");
      String sfb = rec.getSubfieldContent("700b");
      if (sfa != null) {
        if (sfb != null)
          return toSentenceCase(sfa) + ", " + toSentenceCase(sfb);
        else
          return toSentenceCase(sfa);
      } else {
        if (sfb != null)
          return toSentenceCase(sfb);
        else
          return "";
      }
    } else if (rec.getField("701") != null) {
      String sfa = rec.getSubfieldContent("701a");
      String sfb = rec.getSubfieldContent("701b");
      if (sfa != null) {
        if (sfb != null)
          return toSentenceCase(sfa) + ", " + toSentenceCase(sfb);
        else
          return toSentenceCase(sfa);
      } else {
        if (sfb != null)
          return toSentenceCase(sfb);
        else
          return "";
      }
    } else if (rec.getField("702") != null) {
      String sfa = rec.getSubfieldContent("702a");
      String sfb = rec.getSubfieldContent("702b");
      if (sfa != null) {
        if (sfb != null)
          return toSentenceCase(sfa) + ", " + toSentenceCase(sfb);
        else
          return toSentenceCase(sfa);
      } else {
        if (sfb != null)
          return toSentenceCase(sfb);
        else
          return "";
      }
    }
    return "";
  }

  public String trimZeros(String s) {
    if (s == null)
      return null;
    String retVal = s;
    while (retVal.length() > 0 && retVal.charAt(0) == '0')
      retVal = retVal.substring(1);
    return retVal;
  }
  
  public String toSentenceCase(String s) {
    StringBuffer retVal = new StringBuffer();
    StringTokenizer st = new StringTokenizer(s, " -.", true);
    while (st.hasMoreTokens()) {
      String word = st.nextToken();
      if (word.length() > 0)
        retVal.append(Character.toUpperCase(word.charAt(0)) + 
            word.substring(1).toLowerCase());
        
    }
    return retVal.toString();
  }
  
  public String nvl(String s) {
    return s == null ? " " : s;
  }

  

  SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy.");
  private Period period;
  private Pattern pattern;
  private Map<String, List<Item>> itemMap = new HashMap<String, List<Item>>();
  private String name;
  private static Log log = LogFactory.getLog(InvKnjigaZaRukopisnuGradju.class);
@Override
public void finishOnline(StringBuffer buff) {
	// TODO Auto-generated method stub
	
}
}
