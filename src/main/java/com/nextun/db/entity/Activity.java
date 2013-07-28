package com.nextun.db.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.nextun.enums.EnumActivityState;

/**
 * 活動
 * 
 * @author Mark
 * 
 */
@Entity
@Table(name = "activity")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE) 

public class Activity implements Serializable{
  /**
   * 
   */
  private static final long serialVersionUID = 8470841777810818162L;

  @Id 
  @SequenceGenerator (name="activity_id_seq", sequenceName="activity_id_seq",allocationSize=1,initialValue =1)
  @GeneratedValue(strategy = GenerationType.SEQUENCE,generator="activity_id_seq")
	private long id;
  
  @Column(name = "userid",nullable = false)
	private long userId;

  @Column(name = "subject",nullable = false,columnDefinition = "character varying(255) NOT NULL DEFAULT ''::character varying")
  private String subject;
  
  @Column(name = "startdate",nullable = false)
  @Temporal(TemporalType.DATE)
  private Date startDate;

  @Column(name = "enddate",nullable = false)
  @Temporal(TemporalType.DATE)
  private Date endDate;

  @Column(name = "state",nullable = false)
  @Enumerated(EnumType.STRING)
  private EnumActivityState state;

  @Column(name = "updatetime",nullable = false)
  @Temporal(TemporalType.TIMESTAMP)
  private Date updateTime;

	public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getSubject() {
		return subject;
	}

  public void setUserId(long userId) {
    this.userId = userId;
  }

  public long getUserId() {
    return userId;
  }

  public Date getStartDate() {
    return startDate;
  }

  public void setStartDate(Date startDate) {
    this.startDate = startDate;
  }

  public Date getEndDate() {
    return endDate;
  }

  public void setEndDate(Date endDate) {
    this.endDate = endDate;
  }

  public EnumActivityState getState() {
    return state;
  }

  public void setState(EnumActivityState state) {
    this.state = state;
  }

  public Date getUpdateTime() {
    return updateTime;
  }

  public void setUpdateTime(Date updateTime) {
    this.updateTime = updateTime;
  }


}