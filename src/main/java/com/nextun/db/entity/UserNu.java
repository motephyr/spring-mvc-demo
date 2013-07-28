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
import org.hibernate.annotations.Index;

@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Entity
@Table(name = "usernu")
public class UserNu implements Serializable {

  /**
   * 
   */
  private static final long serialVersionUID = -6358496616661459029L;

  @Id
  @SequenceGenerator(name = "usernu_id_seq", allocationSize = 1, initialValue = 1, sequenceName = "usernu_id_seq")
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "usernu_id_seq")
  private long id;

  /**
   * userId
   */
  @Column(name = "userid", nullable = false)
  private long userId;
  
  /**
   * email(account)
   */
  @Column(name = "email", length = 255, nullable = false, columnDefinition = "character varying NOT NULL DEFAULT ''::character varying")
  @Index(name = "userpm_email_idx")
  private String email;

  /**
   * 密碼
   */
  @Column(name = "password", length = 20, nullable = false, columnDefinition = "character varying NOT NULL DEFAULT ''::character varying")
  private String password;

  /**
   * 驗證碼
   */
  @Column(name = "confirm", length = 10, columnDefinition = "character varying  DEFAULT ''::character varying")
  private String confirm;

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public String getConfirm() {
    return confirm;
  }

  public void setConfirm(String confirm) {
    this.confirm = confirm;
  }

  public long getUserId() {
    return userId;
  }

  public void setUserId(long userId) {
    this.userId = userId;
  }
  
  

}
