package com.nextun.db.service.activity;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nextun.db.dao.activity.ActivityDao;
import com.nextun.db.entity.Activity;
import com.nextun.db.service.BaseService;

@Service("activityService")
public class ActivityService extends BaseService<Activity, Long, ActivityDao>  {

  @Autowired
  private ActivityDao activityDao;
  
  @Resource(name = "activityDao")
  public void setGenericDAO(ActivityDao genericDAO) {
    super.setGenericDAO(genericDAO);
  }
  
  public List<Activity> findByPageRequest(int page,long userId) {
    
    int size = 10;
    int first = (page - 1) * size;
    
    long total = countByTypeTypeStr(userId);
    
    if(total < first){
        page = 1;
        first = 0;
    }   
    List<Activity> activitys = listByTypeTypeStr(first, size,userId);
    return activitys;
  }
  
  public List<Activity> listByTypeTypeStr(int first, int size,long userId){
    return activityDao.listByTypeTypeStr(first, size,userId);
  }
  
  public long countByTypeTypeStr(long userId){
    return activityDao.countByTypeTypeStr(userId);
  }

  public List<Activity> listByTypeTypeStr(int first, int size){
    return activityDao.listByTypeTypeStr(  first, size);
  }
  
  public long countByTypeTypeStr(){
    return activityDao.countByTypeTypeStr();
  }



}
