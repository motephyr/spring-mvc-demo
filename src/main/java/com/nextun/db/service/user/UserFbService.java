package com.nextun.db.service.user;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nextun.db.dao.user.UserFbDao;
import com.nextun.db.entity.UserFb;
import com.nextun.db.service.BaseService;

@Service("userFbService")
public class UserFbService extends BaseService<UserFb, Long, UserFbDao>  {

  @Autowired
  private UserFbDao userFbDao;

  @Resource(name = "userFbDao")
  public void setGenericDAO(UserFbDao genericDAO) {
    super.setGenericDAO(genericDAO);
  }
  
  public UserFb selectByUid(String uid) throws Exception {
    return userFbDao.selectByUid(uid);
  } 
}
