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
 * 活動時間
 * 
 * @author Mark
 * 
 */
@Entity
@Table(name = "activityperiodmoney")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class ActivityPeriodMoney implements Serializable {
  /**
   * 
   */
  private static final long serialVersionUID = -944960856268009732L;

  @Id
  @SequenceGenerator(name = "activityperiodmoney_id_seq", sequenceName = "activityperiodmoney_id_seq", allocationSize = 1, initialValue = 1)
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "activityperiodmoney_id_seq")
  private long id;

  @Column(name = "activityperiodid", nullable = false)
  private long activityPeriodId;

  @Column(name = "name", nullable = false, columnDefinition = "character varying(255) NOT NULL DEFAULT ''::character varying")
  private String name;

  @Column(name = "money", nullable = false)
  private int money;

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public long getActivityPeriodId() {
    return activityPeriodId;
  }

  public void setActivityPeriodId(long activityPeriodId) {
    this.activityPeriodId = activityPeriodId;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public int getMoney() {
    return money;
  }

  public void setMoney(int money) {
    this.money = money;
  }

}