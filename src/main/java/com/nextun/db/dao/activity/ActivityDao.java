package com.nextun.db.dao.activity;

import java.util.List;

import javax.annotation.Resource;

import org.hibernate.SessionFactory;
import org.springframework.stereotype.Component;

import com.nextun.db.dao.BaseDAOSupport;
import com.nextun.db.entity.Activity;

@Component("activityDao")
public class ActivityDao extends BaseDAOSupport<Activity, Long> {
  
  @Resource(name = "sessionFactory")
  private SessionFactory sessionFactory;
  
  @SuppressWarnings("unchecked")
  public List<Activity> listByTypeTypeStr( int first,
      int size,long userId) {

    Object[] value = null;
    String hql = "FROM Activity AS a WHERE a.userId = ? ";
    hql += "ORDER BY a.updateTime DESC ";
    value = new Object[] { userId };
    
    return queryForList(hql, value,first, size);
  }

  public long countByTypeTypeStr(long userId) {

    Object[] value = null;
    String hql = "SELECT COUNT(a.id) FROM Activity AS a WHERE a.userId = ?";
    value = new Object[] { userId };
    
    return queryForSize(hql, value);
  }
  
  @SuppressWarnings("unchecked")
  public List<Activity> listByTypeTypeStr( int first,
      int size) {

    Object[] value = null;
    String hql = "FROM Activity AS a ";
    hql += "ORDER BY a.updateTime DESC ";

    return queryForList(hql, value,first, size);
  }

  public long countByTypeTypeStr() {

    Object[] value = null;
    String hql = "SELECT COUNT(a.id) FROM Activity AS a ";
    
    return queryForSize(hql, value);
  }
}
