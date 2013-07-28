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

/** User資料
*
*/
@Entity
@Table(name = "userprofile")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE) 

public class UserProfile implements Serializable{
  /**
   * 
   */
  private static final long serialVersionUID = -5636010897887751804L;

  @Id 
  @SequenceGenerator (name="userprofile_id_seq", sequenceName="userprofile_id_seq",allocationSize=1,initialValue =1)
  @GeneratedValue(strategy = GenerationType.SEQUENCE,generator="userprofile_id_seq")
  private long id;
  
  @Column(name = "userid",nullable = false)
  private long userId;
  
  @Column(name = "sendtime",nullable = false)
  @Temporal(TemporalType.TIMESTAMP)
  private Date sendTime;
  
  @Column(name = "logintime",nullable = false)
  @Temporal(TemporalType.TIMESTAMP)
  private Date loginTime;
  
  @Column(name = "ip",nullable = false,columnDefinition = "character varying(15) NOT NULL DEFAULT ''::character varying")
  private String ip;

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public long getUserId() {
    return userId;
  }

  public void setUserId(long userId) {
    this.userId = userId;
  }

  public Date getSendTime() {
    return sendTime;
  }

  public void setSendTime(Date sendTime) {
    this.sendTime = sendTime;
  }

  public Date getLoginTime() {
    return loginTime;
  }

  public void setLoginTime(Date loginTime) {
    this.loginTime = loginTime;
  }

  public void setIp(String ip){
    this.ip = ip;
  }
  
  public String getIp(){
    return ip;
  }
  
}