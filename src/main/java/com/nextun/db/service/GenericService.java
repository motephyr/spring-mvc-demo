package com.nextun.db.service;

import java.io.Serializable;

public interface GenericService<T, PK extends Serializable> {
  public T save(T target) throws Exception;

//  public void delete(T target) throws Exception;
  
  public void removeById(PK id) throws Exception;

  public T update(T target) throws Exception;

  public T saveOrUpdate(T target) throws Exception;

  public T getById(PK id) throws Exception;

  public int count() throws Exception;
}
