package com.nextun.db.service.user;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nextun.db.dao.user.ProfileDao;
import com.nextun.db.entity.UserProfile;
import com.nextun.db.service.BaseService;

@Service("profileService")
public class ProfileService extends BaseService<UserProfile, Long, ProfileDao>  {

  @Autowired
  private ProfileDao profileDao;

  @Resource(name = "profileDao")
  public void setGenericDAO(ProfileDao genericDAO) {
    super.setGenericDAO(genericDAO);
  }

}
