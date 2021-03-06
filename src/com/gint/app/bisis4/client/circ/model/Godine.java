package com.gint.app.bisis4.client.circ.model;

// Generated Jan 11, 2007 11:36:46 AM by Hibernate Tools 3.2.0.beta8

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Date;
import java.util.Set;

/**
 * Godine generated by hbm2java
 */
public class Godine implements java.io.Serializable {

  // Fields    

  private int godinaId;

  private InternaOznaka internaOznaka;

  private Sigformat sigformat;

  private Odeljenje odeljenje;

  private Povez povez;

  private NacinNabavke nacinNabavke;

  private Records records;

  private Podlokacija podlokacija;

  private String invBroj;

  private Date datumRacuna;

  private String brojRacuna;

  private String dobavljac;

  private BigDecimal cena;

  private String finansijer;

  private String sigDublet;

  private String sigNumerusCurens;

  private String sigNumeracija;

  private String sigUdk;

  private String godiste;

  private String godina;

  private String broj;

  private Date datumInventarisanja;

  private String napomene;

  private Set sveskes = new HashSet(0);

  // Constructors

  /** default constructor */
  public Godine() {
  }

  /** minimal constructor */
  public Godine(int godinaId, Records records) {
    this.godinaId = godinaId;
    this.records = records;
  }

  /** full constructor */
  public Godine(int godinaId, InternaOznaka internaOznaka, Sigformat sigformat,
      Odeljenje odeljenje, Povez povez, NacinNabavke nacinNabavke,
      Records records, Podlokacija podlokacija, String invBroj,
      Date datumRacuna, String brojRacuna, String dobavljac, BigDecimal cena,
      String finansijer, String sigDublet, String sigNumerusCurens,
      String sigNumeracija, String sigUdk, String godiste, String godina,
      String broj, Date datumInventarisanja, String napomene, Set sveskes) {
    this.godinaId = godinaId;
    this.internaOznaka = internaOznaka;
    this.sigformat = sigformat;
    this.odeljenje = odeljenje;
    this.povez = povez;
    this.nacinNabavke = nacinNabavke;
    this.records = records;
    this.podlokacija = podlokacija;
    this.invBroj = invBroj;
    this.datumRacuna = datumRacuna;
    this.brojRacuna = brojRacuna;
    this.dobavljac = dobavljac;
    this.cena = cena;
    this.finansijer = finansijer;
    this.sigDublet = sigDublet;
    this.sigNumerusCurens = sigNumerusCurens;
    this.sigNumeracija = sigNumeracija;
    this.sigUdk = sigUdk;
    this.godiste = godiste;
    this.godina = godina;
    this.broj = broj;
    this.datumInventarisanja = datumInventarisanja;
    this.napomene = napomene;
    this.sveskes = sveskes;
  }

  // Property accessors
  public int getGodinaId() {
    return this.godinaId;
  }

  public void setGodinaId(int godinaId) {
    this.godinaId = godinaId;
  }

  public InternaOznaka getInternaOznaka() {
    return this.internaOznaka;
  }

  public void setInternaOznaka(InternaOznaka internaOznaka) {
    this.internaOznaka = internaOznaka;
  }

  public Sigformat getSigformat() {
    return this.sigformat;
  }

  public void setSigformat(Sigformat sigformat) {
    this.sigformat = sigformat;
  }

  public Odeljenje getOdeljenje() {
    return this.odeljenje;
  }

  public void setOdeljenje(Odeljenje odeljenje) {
    this.odeljenje = odeljenje;
  }

  public Povez getPovez() {
    return this.povez;
  }

  public void setPovez(Povez povez) {
    this.povez = povez;
  }

  public NacinNabavke getNacinNabavke() {
    return this.nacinNabavke;
  }

  public void setNacinNabavke(NacinNabavke nacinNabavke) {
    this.nacinNabavke = nacinNabavke;
  }

  public Records getRecords() {
    return this.records;
  }

  public void setRecords(Records records) {
    this.records = records;
  }

  public Podlokacija getPodlokacija() {
    return this.podlokacija;
  }

  public void setPodlokacija(Podlokacija podlokacija) {
    this.podlokacija = podlokacija;
  }

  public String getInvBroj() {
    return this.invBroj;
  }

  public void setInvBroj(String invBroj) {
    this.invBroj = invBroj;
  }

  public Date getDatumRacuna() {
    return this.datumRacuna;
  }

  public void setDatumRacuna(Date datumRacuna) {
    this.datumRacuna = datumRacuna;
  }

  public String getBrojRacuna() {
    return this.brojRacuna;
  }

  public void setBrojRacuna(String brojRacuna) {
    this.brojRacuna = brojRacuna;
  }

  public String getDobavljac() {
    return this.dobavljac;
  }

  public void setDobavljac(String dobavljac) {
    this.dobavljac = dobavljac;
  }

  public BigDecimal getCena() {
    return this.cena;
  }

  public void setCena(BigDecimal cena) {
    this.cena = cena;
  }

  public String getFinansijer() {
    return this.finansijer;
  }

  public void setFinansijer(String finansijer) {
    this.finansijer = finansijer;
  }

  public String getSigDublet() {
    return this.sigDublet;
  }

  public void setSigDublet(String sigDublet) {
    this.sigDublet = sigDublet;
  }

  public String getSigNumerusCurens() {
    return this.sigNumerusCurens;
  }

  public void setSigNumerusCurens(String sigNumerusCurens) {
    this.sigNumerusCurens = sigNumerusCurens;
  }

  public String getSigNumeracija() {
    return this.sigNumeracija;
  }

  public void setSigNumeracija(String sigNumeracija) {
    this.sigNumeracija = sigNumeracija;
  }

  public String getSigUdk() {
    return this.sigUdk;
  }

  public void setSigUdk(String sigUdk) {
    this.sigUdk = sigUdk;
  }

  public String getGodiste() {
    return this.godiste;
  }

  public void setGodiste(String godiste) {
    this.godiste = godiste;
  }

  public String getGodina() {
    return this.godina;
  }

  public void setGodina(String godina) {
    this.godina = godina;
  }

  public String getBroj() {
    return this.broj;
  }

  public void setBroj(String broj) {
    this.broj = broj;
  }

  public Date getDatumInventarisanja() {
    return this.datumInventarisanja;
  }

  public void setDatumInventarisanja(Date datumInventarisanja) {
    this.datumInventarisanja = datumInventarisanja;
  }

  public String getNapomene() {
    return this.napomene;
  }

  public void setNapomene(String napomene) {
    this.napomene = napomene;
  }

  public Set getSveskes() {
    return this.sveskes;
  }

  public void setSveskes(Set sveskes) {
    this.sveskes = sveskes;
  }

}
