package com.nextun.db.service.user;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nextun.db.dao.user.UserNuDao;
import com.nextun.db.entity.UserNu;
import com.nextun.db.service.BaseService;

@Service("userNuService")
public class UserNuService extends BaseService<UserNu, Long, UserNuDao>  {

  @Autowired
  private UserNuDao userNuDao;

  @Resource(name = "userNuDao")
  public void setGenericDAO(UserNuDao genericDAO) {
    super.setGenericDAO(genericDAO);
  }

  public UserNu selectByEmail(String email) throws Exception {
    return userNuDao.selectByEmail(email);
  } 
  
  public UserNu selectByEmailPassword(String email, String password) throws Exception{
    return userNuDao.selectByEmailPassword(email, password);
  }
  



}
