package com.nextun.db.dao.user;

import java.util.List;

import javax.annotation.Resource;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Component;

import com.nextun.db.dao.BaseDAOSupport;
import com.nextun.db.entity.UserNu;


@Component("userNuDao")
public class UserNuDao extends BaseDAOSupport<UserNu,Long> {

  @Resource(name = "sessionFactory")
  private SessionFactory sessionFactory;
  
  public UserNu selectByEmail(String email) {
    final String hql = "FROM UserNu WHERE email = :email";
    
    UserNu result = null;
    Query q = sessionFactory.getCurrentSession().createQuery(hql);
    q.setString("email", email);
    List<?> list = q.list();
    
    if (list != null && list.size() > 0) {
      result = (UserNu) list.get(0);
    }
    return result;
  }

  public UserNu selectByEmailPassword(String email, String password) {
    final String hql = "FROM UserNu WHERE email = :email AND password = :password";
    
    UserNu result = null;
    Query q = sessionFactory.getCurrentSession().createQuery(hql);
    q.setString("email", email);
    q.setString("password", password);
    List<?> list = q.list();
    if (list != null && list.size() > 0) {
      result = (UserNu) list.get(0);
    }
    return result;
  }
}
