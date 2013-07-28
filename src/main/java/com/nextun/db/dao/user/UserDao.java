package com.nextun.db.dao.user;

import org.springframework.stereotype.Component;

import com.nextun.db.dao.BaseDAOSupport;
import com.nextun.db.entity.User;


@Component("userDao")
public class UserDao extends BaseDAOSupport<User,Long> {


}
