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
@Table(name = "activityperiodphoto")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class ActivityPeriodPhoto implements Serializable {
  /**
   * 
   */
  private static final long serialVersionUID = -8694573599630296733L;

  @Id
  @SequenceGenerator(name = "activityperiodphoto_id_seq", sequenceName = "activityperiodphoto_id_seq", allocationSize = 1, initialValue = 1)
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "activityperiodphoto_id_seq")
  private long id;

  @Column(name = "activityperiodid", nullable = false)
  private long activityPeriodId;

  @Column(name = "filename", nullable = false, columnDefinition = "character varying(255) NOT NULL DEFAULT ''::character varying")
  private String fileName;

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

  public String getFileName() {
    return fileName;
  }

  public void setFileName(String fileName) {
    this.fileName = fileName;
  }


}