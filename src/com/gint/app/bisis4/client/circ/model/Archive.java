package com.gint.app.bisis4.client.circ.model;

// Generated Nov 15, 2006 4:24:31 PM by Hibernate Tools 3.2.0.beta8

import java.util.Date;

/**
 * Archive generated by hbm2java
 */
public class Archive implements java.io.Serializable {

	// Fields    

	private int id;

	private int sysId;

	private Date archDate;

	private String content;

	// Constructors

	/** default constructor */
	public Archive() {
	}

	/** minimal constructor */
	public Archive(int id, int sysId, Date archDate) {
		this.id = id;
		this.sysId = sysId;
		this.archDate = archDate;
	}

	/** full constructor */
	public Archive(int id, int sysId, Date archDate, String content) {
		this.id = id;
		this.sysId = sysId;
		this.archDate = archDate;
		this.content = content;
	}

	// Property accessors
	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getSysId() {
		return this.sysId;
	}

	public void setSysId(int sysId) {
		this.sysId = sysId;
	}

	public Date getArchDate() {
		return this.archDate;
	}

	public void setArchDate(Date archDate) {
		this.archDate = archDate;
	}

	public String getContent() {
		return this.content;
	}

	public void setContent(String content) {
		this.content = content;
	}

}
