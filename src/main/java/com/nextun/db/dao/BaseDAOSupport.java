package com.nextun.db.dao;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

import javax.annotation.Resource;

import org.hibernate.Query;
import org.hibernate.SessionFactory;

public class BaseDAOSupport<T, PK extends Serializable> implements GenericDAO<T, PK> {
  private Class<T> persistenClass;
  

  @SuppressWarnings("unchecked")
  public BaseDAOSupport() {
    this.persistenClass = (Class<T>) getSuperClassGenricType(getClass());
  }

  @Resource(name = "sessionFactory")
  private SessionFactory sessionFactory;
  
  private static Class<?> getSuperClassGenricType(Class<?> clazz) {
    return getSuperClassGenricType(clazz, 0);
  }

  /**
   * 通過反射,獲得定義Class時聲明的父類的范型參數的類型. 如public BookManager extends
   * GenricManager<Book>
   * 
   * @param clazz
   *          clazz The class to introspect
   * @param index
   *          the Index of the generic ddeclaration,start from 0.
   */
  private static Class<?> getSuperClassGenricType(Class<?> clazz, int index)
      throws IndexOutOfBoundsException {

    Type genType = clazz.getGenericSuperclass();

    if (!(genType instanceof ParameterizedType)) {
      return Object.class;
    }

    Type[] params = ((ParameterizedType) genType).getActualTypeArguments();

    if (index >= params.length || index < 0) {
      return Object.class;
    }
    if (!(params[index] instanceof Class)) {
      return Object.class;
    }
    return (Class<?>) params[index];
  }

  public Class<T> getPersistenClass() {
    return persistenClass;
  }

  public T save(T target) {
    sessionFactory.getCurrentSession().save(target);
    return target;
  }

//  public void delete(T target) {
//    getHibernateTemplate().delete(target);
//  }
  
  public void removeById(PK id) {
    T target = (T) sessionFactory.getCurrentSession().get(getPersistenClass(), id);
    sessionFactory.getCurrentSession().delete(target);
  }

  public T update(T target) {
    sessionFactory.getCurrentSession().update(target);
    return target;
  }

  public T saveOrUpdate(T target) {
    sessionFactory.getCurrentSession().saveOrUpdate(target);
    return target;
  }

  public T getById(PK id) {
    T target = (T) sessionFactory.getCurrentSession().get(getPersistenClass(), id);
    return target;
  }
  
  public int count() {
    final String hql = "SELECT COUNT(*) FROM " + persistenClass.getName();
    Query q = sessionFactory.getCurrentSession().createQuery(hql);
    List<?> list = q.list();
    return (Integer) list.get(0);
  }
  
  @SuppressWarnings("unchecked")  
  public List<T> queryForList(String hql, final Object[] values,
      final int offset, final int pageSize) {   

      Query query = sessionFactory.getCurrentSession().createQuery(hql);  
      for (int i = 0; i < values.length; i++) {
        query.setParameter(i, values[i]);
      }
      query.setFirstResult(offset);  
      query.setMaxResults(pageSize);  
      return query.list();  
  }  

  /** 
   * 该方法会改变参数page的totalCount字段 
   *  
   * @param originHql 
   *            原始hql语句 
   * @param params 
   *            原始参数 
   * @param page 
   *            页面对象 
   */  
  public long queryForSize(String originHql, final Object[] values) {  
      String generatedCountHql = "select count(*) " + originHql;  
      Query countQuery = sessionFactory.getCurrentSession().createQuery(  
              generatedCountHql);  
      for (int i = 0; i < values.length; i++) {
        countQuery.setParameter(i, values[i]);
      }
      return ((Long) countQuery.uniqueResult());  
  }  
  
}
