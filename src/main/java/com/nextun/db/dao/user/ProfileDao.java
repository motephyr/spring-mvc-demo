package com.nextun.db.dao.user;

import java.util.List;

import javax.annotation.Resource;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Component;

import com.nextun.db.dao.BaseDAOSupport;
import com.nextun.db.entity.UserProfile;

@Component("profileDao")
public class ProfileDao extends BaseDAOSupport<UserProfile,Long> {

  @Resource(name = "sessionFactory")
  private SessionFactory sessionFactory;
  
  public UserProfile selectByUserId(int userId) {
    
    final String hql = "FROM Profile WHERE userId = :userId";
    
    UserProfile result = null;
    Query q = sessionFactory.getCurrentSession().createQuery(hql);
    q.setInteger("userId", userId);
    List<?> list = q.list();
    
    if (list != null && list.size() > 0) {
      result = (UserProfile) list.get(0);
    }
    return result;
  }

  public String selectNameByUserId(int userId) {
    
    final String hql = "SELECT name FROM Profile WHERE userId = :userId";
    String result = null;
    Query q = sessionFactory.getCurrentSession().createQuery(hql);
    q.setInteger("userId", userId);
    List<?> list = q.list();
    
    if (list != null && list.size() > 0) {
      result = list.get(0).toString();
    }
    return result;
  }
  
}
