package com.nextun.db.service;

import java.io.Serializable;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.nextun.db.dao.GenericDAO;

public abstract class BaseService<T, PK extends Serializable, D extends GenericDAO<T, PK>>
    implements GenericService<T, PK> {
  protected final Log log = LogFactory.getLog(getClass().getName());

  protected D genericDAO;

  public void setGenericDAO(D genericDAO) {
    this.genericDAO = genericDAO;
  }

  @Transactional
  public T save(T target) throws Exception {
    T t = genericDAO.save(target);
    return t;
  }

//  @Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
//  public void delete(T target) throws Exception {
//    genericDAO.delete(target);
//  }
  
  @Transactional
  public void removeById(PK id) throws Exception {
    genericDAO.removeById(id);
  }

  @Transactional
  public T update(T target) throws Exception {
    return genericDAO.update(target);
  }

  @Transactional
  public T saveOrUpdate(T target) throws Exception {
    return genericDAO.saveOrUpdate(target);
  }

  public T getById(PK id) throws Exception {
    return genericDAO.getById(id);
  }
  
  public int count() throws Exception {
    return genericDAO.count();
  }

}
