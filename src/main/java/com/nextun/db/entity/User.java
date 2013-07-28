package com.nextun.db.entity;

import java.io.Serializable;

import javax.persistence.*;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

@Entity
@Table(name = "users")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE) 
public class User implements Serializable {
  /**
   * 
   */
  private static final long serialVersionUID = 3137997820308064797L;

  @Id 
  @SequenceGenerator (name="users_id_seq", sequenceName="users_id_seq",allocationSize=1,initialValue =1)
  @GeneratedValue(strategy = GenerationType.SEQUENCE,generator="users_id_seq")
	private long id;
  
  @Column(name = "nickname",nullable = false,columnDefinition = "character varying(16) NOT NULL DEFAULT ''::character varying")
	private String nickname;

  @Column(name = "email",nullable = false,columnDefinition = "character varying(255) NOT NULL DEFAULT ''::character varying")
  private String email;
  
  
  @Column(name = "photo",nullable = false,columnDefinition = "character varying(30) NOT NULL DEFAULT ''::character varying")
  private String photo;
  
	@Transient 
	private String language;


  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

	public void setEmail(String email) {
		this.email = email;
	}

	public String getEmail() {
		return email;
	}

	public String getNickname() {
    return nickname;
  }

  public void setNickname(String nickname) {
    this.nickname = nickname;
  }

  public void setPhoto(String photo) {
		this.photo = photo;
	}

	public String getPhoto() {
		return photo;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public String getLanguage() {
		return language;
	}
	
	@Override
	public User clone() {
		User user = null;
	  try {
	  	user = (User)super.clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
		return user;
	}



}