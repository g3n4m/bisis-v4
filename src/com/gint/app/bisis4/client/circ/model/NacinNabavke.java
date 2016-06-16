package com.gint.app.bisis4.client.circ.model;

import java.util.HashSet;
import java.util.Set;

// Generated Jan 11, 2007 11:36:46 AM by Hibernate Tools 3.2.0.beta8



/**
 * NacinNabavke generated by hbm2java
 */
public class NacinNabavke implements java.io.Serializable {

  // Fields    

  private char nacinId;

  private String nacinOpis;

  private Set godines = new HashSet(0);

  private Set primercis = new HashSet(0);

  // Constructors

  /** default constructor */
  public NacinNabavke() {
  }

  /** minimal constructor */
  public NacinNabavke(char nacinId) {
    this.nacinId = nacinId;
  }

  /** full constructor */
  public NacinNabavke(char nacinId, String nacinOpis, Set godines, Set primercis) {
    this.nacinId = nacinId;
    this.nacinOpis = nacinOpis;
    this.godines = godines;
    this.primercis = primercis;
  }

  // Property accessors
  public char getNacinId() {
    return this.nacinId;
  }

  public void setNacinId(char nacinId) {
    this.nacinId = nacinId;
  }

  public String getNacinOpis() {
    return this.nacinOpis;
  }

  public void setNacinOpis(String nacinOpis) {
    this.nacinOpis = nacinOpis;
  }

  public Set getGodines() {
    return this.godines;
  }

  public void setGodines(Set godines) {
    this.godines = godines;
  }

  public Set getPrimercis() {
    return this.primercis;
  }

  public void setPrimercis(Set primercis) {
    this.primercis = primercis;
  }

}