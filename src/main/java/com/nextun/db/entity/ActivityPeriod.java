package com.nextun.db.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * 活動時間
 * 
 * @author Mark
 * 
 */
@Entity
@Table(name = "activityperiod")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE) 

public class ActivityPeriod implements Serializable{
  /**
   * 
   */
  private static final long serialVersionUID = -7813723477125664779L;

  @Id 
  @SequenceGenerator (name="activityperiod_id_seq", sequenceName="activityperiod_id_seq",allocationSize=1,initialValue =1)
  @GeneratedValue(strategy = GenerationType.SEQUENCE,generator="activityperiod_id_seq")
	private long id;
  
  @Column(name = "senddate",nullable = false)
  @Temporal(TemporalType.DATE)
  private Date sendDate;
  
  @Column(name = "activityid",nullable = false)
	private long activityId;
  
  @Column(name = "starttime",nullable = false)
  @Temporal(TemporalType.DATE)
  private Date startTime;

  @Column(name = "endtime",nullable = false)
  @Temporal(TemporalType.DATE)
  private Date endTime;
  
  @Column(name = "content")
  private String content;
  
  @Column(name = "place")
  private String ps;
  
  @Column(name = "mapphoto")
  private String mapPhoto;
  
  @Column(name = "impression")
  private String impression;


	public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public long getActivityId() {
    return activityId;
  }

  public void setActivityId(long activityId) {
    this.activityId = activityId;
  }

  public Date getSendDate() {
    return sendDate;
  }

  public void setSendDate(Date sendDate) {
    this.sendDate = sendDate;
  }

  public Date getStartTime() {
    return startTime;
  }

  public void setStartTime(Date startTime) {
    this.startTime = startTime;
  }

  public Date getEndTime() {
    return endTime;
  }

  public void setEndTime(Date endTime) {
    this.endTime = endTime;
  }

  public String getContent() {
    return content;
  }

  public void setContent(String content) {
    this.content = content;
  }

  public String getPs() {
    return ps;
  }

  public void setPs(String ps) {
    this.ps = ps;
  }

  public String getMapPhoto() {
    return mapPhoto;
  }

  public void setMapPhoto(String mapPhoto) {
    this.mapPhoto = mapPhoto;
  }

  public String getImpression() {
    return impression;
  }

  public void setImpression(String impression) {
    this.impression = impression;
  }







}