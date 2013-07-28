package com.nextun.db.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * Facebook相關帳號記錄資料
 * 
 * @author Mark
 * 
 */
@Entity
@Table(name = "userfb")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE) 

public class UserFb implements Serializable {
  /**
   * 
   */
  private static final long serialVersionUID = 2310971520823584970L;

  @Id 
  @SequenceGenerator (name="userfb_id_seq", sequenceName="userfb_id_seq",allocationSize=1,initialValue =1)
  @GeneratedValue(strategy = GenerationType.SEQUENCE,generator="userfb_id_seq")
	private long id;

  @Column(name = "userid",nullable = false)
	private long userId;
  
  @Column(name = "uid",nullable = false,columnDefinition = "character varying(255) NOT NULL DEFAULT ''::character varying")
	private String uid;
  
  @Column(name = "accesstoken",nullable = false,columnDefinition = "character varying(255) NOT NULL DEFAULT ''::character varying")
	private String accessToken;

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

  public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

  public String getAccessToken() {
    return accessToken;
  }

  public void setAccessToken(String accessToken) {
    this.accessToken = accessToken;
  }
	


}
