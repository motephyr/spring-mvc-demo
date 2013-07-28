package com.nextun.db.dao.user;

import java.util.List;

import javax.annotation.Resource;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Component;

import com.nextun.db.dao.BaseDAOSupport;
import com.nextun.db.entity.UserFb;

@Component("userFbDao")
public class UserFbDao extends BaseDAOSupport<UserFb,Long>  {

  @Resource(name = "sessionFactory")
  private SessionFactory sessionFactory;
  
  public UserFb selectByAccount(int userId) {
    final String hql = "FROM UserFb WHERE userId = :userId";
    
    UserFb result = null;
    
    Query q = sessionFactory.getCurrentSession().createQuery(hql);
    q.setInteger("userId", userId);
    List<?> list = q.list();
    
    if (list != null && list.size() > 0) {
      result = (UserFb) list.get(0);
    }
    return result;
  }
  
  public UserFb selectByUid(String uid) {
    final String hql = "FROM UserFb WHERE uid = :uid";
    
    UserFb result = null;

    Query q = sessionFactory.getCurrentSession().createQuery(hql);
    q.setString("uid", uid);
    List<?> list = q.list();
    
    if (list != null && list.size() > 0) {
      result = (UserFb) list.get(0);
    }
    return result;
  }

	public void deleteByUserId(int userId) {
		String hql = "delete from UserFb f where f.userId = :userId";
		Query q = sessionFactory.getCurrentSession().createQuery(hql);
		q.setInteger("userId", userId);
		q.executeUpdate();
	}

	public void deleteByUid(String uid) {
    String hql = "delete from UserFb f where uid = :uid";
    Query q = sessionFactory.getCurrentSession().createQuery(hql);
    q.setString("uid", uid);
    q.executeUpdate();
	}

  public boolean isUidExist(String uid) {
    
    boolean result = false;
    if(selectByUid(uid) != null){
      result = true;
    }
    return result;
  }
	
//  public List<Integer> selectUserIdByUids(final List<String> uids) {
//    List list = getHibernateTemplate().executeFind(new HibernateCallback() {
//      public List doInHibernate(Session session) throws HibernateException, SQLException {
//          return session.createQuery
//          ("SELECT f.userId FROM UserFb WHERE id IN ?")//
//          .setParameterList("ids", uids).list();
//      }});
//    return list;
//  }
	
}
