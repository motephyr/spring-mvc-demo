package com.nextun.db.service.user;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nextun.db.dao.user.UserDao;
import com.nextun.db.entity.User;
import com.nextun.db.service.BaseService;

@Service("userService")
public class UserService extends BaseService<User, Long, UserDao>  {

  @Autowired
  private UserDao userDao;

  @Resource(name = "userDao")
  public void setGenericDAO(UserDao genericDAO) {
    super.setGenericDAO(genericDAO);
  }

  public User newUser(String email,String nickname,String photo,int country) throws Exception {
    User newUser = new User();
    newUser.setEmail(email);
    newUser.setNickname(nickname);
    // TODO: 設定個人相片
    newUser.setPhoto(photo);

    return newUser;
  }


  
  public Object[] selectAccountPhotoCountryById(long id) throws Exception {
    User user = userDao.getById(id);
    Object[] os = null;
    if (user != null) {
      os = new Object[2];
      os[0] = user.getNickname();
      os[1] = user.getPhoto();
    }
    return os;
  }


}
