package com.gint.app.bisis4.client.circ.model;

// Generated Nov 15, 2006 4:24:31 PM by Hibernate Tools 3.2.0.beta8

import java.util.HashSet;
import java.util.Set;

/**
 * UserCategs generated by hbm2java
 */
public class UserCategs implements java.io.Serializable {

	// Fields    

	private int id;

	private String name;

	private Integer titlesNo;

	private Integer period;
	
	private Integer maxPeriod;

	private Set userses = new HashSet(0);

	private Set memberships = new HashSet(0);

	// Constructors

	/** default constructor */
	public UserCategs() {
	}

	/** minimal constructor */
	public UserCategs(int id) {
		this.id = id;
	}

	/** full constructor */
	public UserCategs(int id, String name, Integer titlesNo, Integer period,
			Set userses, Set memberships) {
		this.id = id;
		this.name = name;
		this.titlesNo = titlesNo;
		this.period = period;
		this.userses = userses;
		this.memberships = memberships;
	}

	// Property accessors
	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getTitlesNo() {
		return this.titlesNo;
	}

	public void setTitlesNo(Integer titlesNo) {
		this.titlesNo = titlesNo;
	}

	public Integer getPeriod() {
		return this.period;
	}

	public void setPeriod(Integer period) {
		this.period = period;
	}
	
	public Integer getMaxPeriod() {
		return this.maxPeriod;
	}

	public void setMaxPeriod(Integer maxPeriod) {
		this.maxPeriod = maxPeriod;
	}

	public Set getUserses() {
		return this.userses;
	}

	public void setUserses(Set userses) {
		this.userses = userses;
	}

	public Set getMemberships() {
		return this.memberships;
	}

	public void setMemberships(Set memberships) {
		this.memberships = memberships;
	}

}
