package com.nextun.controller; 
  
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.nextun.db.entity.Activity;
import com.nextun.db.service.activity.ActivityService;
import com.nextun.enums.EnumActivityState;
import com.nextun.enums.EnumAjax;
import com.nextun.utils.NUConstants;
   
@Controller  
@SessionAttributes(NUConstants.USER_SESSION_KEY)
@RequestMapping("/activity")  
public class ActivityController {  
      
  @Autowired
  private ActivityService activityService;  
  
  @RequestMapping(method=RequestMethod.GET, value="/{id}")
  @ResponseBody
  public Map<String, Object> getActivity(@PathVariable String id,@RequestParam String sessionId) throws Exception{
    Activity activity = activityService.getById(Long.parseLong(id));
    System.out.println("get"+id);
//    System.out.println("sessionId"+sessionId);
    AppController.checkSession(sessionId);
    Map<String, Object> map = new HashMap<String, Object>();  
    try {  
      map.put("activity", activity);
      map.put("result", EnumAjax.SUCCESS.getMsg());
    } catch (Exception e) {  
      e.printStackTrace();  
      map.put("result", EnumAjax.ERROR.getMsg());
      throw e;  
    }  
    return map; 
  }
  
  @RequestMapping(method=RequestMethod.PUT, value="/{id}")
  @ResponseBody
  public Map<String, Object> updateActivity(@PathVariable String id,@RequestParam String subject,@RequestParam String sessionId,@RequestParam long userId) throws Exception{
    System.out.println("update"+subject);
//    System.out.println("sessionId"+sessionId);
    AppController.checkSession(sessionId);
    AppController.checkLogin(sessionId, userId);
    
    Map<String, Object> map = new HashMap<String, Object>();  
    try {  
      Activity act = activityService.getById(Long.parseLong(id));
      act.setSubject(subject);
      activityService.update(act);
      
      map.put("activity", act);
      map.put("result", EnumAjax.SUCCESS.getMsg());
    } catch (Exception e) {  
      e.printStackTrace();  
      map.put("result", EnumAjax.ERROR.getMsg());
      throw e;  
    }  
    return map; 
  }
  
  @RequestMapping(method=RequestMethod.POST)
  @ResponseBody
  public Map<String, Object> addActivity(@RequestParam String activity_subject,
      @RequestParam long startDateInterval,
      @RequestParam long endDateInterval,
      @RequestParam String sessionId,
      @RequestParam long userId) throws Exception{
    System.out.println("add"+activity_subject);
//    System.out.println("sessionId"+sessionId);
//    System.out.println(startDateInterval);
//    System.out.println(endDateInterval);
    AppController.checkSession(sessionId);
    AppController.checkLogin(sessionId, userId);
    
    Map<String, Object> map = new HashMap<String, Object>();  
    try {  
      Activity act = new Activity();
      act.setSubject(activity_subject);
      act.setStartDate(new Date(startDateInterval*1000));
      act.setEndDate(new Date(endDateInterval*1000));
      act.setState(EnumActivityState.INIT);
      act.setUserId(userId);
      act.setUpdateTime(new Date());
      activityService.save(act);
    
      map.put("activity", act);
      map.put("result", EnumAjax.SUCCESS.getMsg());
    } catch (Exception e) {  
      e.printStackTrace();  
      map.put("result", EnumAjax.ERROR.getMsg());
      throw e;  
    }  
    return map; 
  }
  
  @RequestMapping(method=RequestMethod.DELETE, value="/{id}")
  @ResponseBody
  public Map<String, String> removeActivity(@PathVariable String id,@RequestParam String sessionId,@RequestParam long userId) throws Exception{
    System.out.println("remove"+id);
//    System.out.println("sessionId"+sessionId);
    AppController.checkSession(sessionId);
    AppController.checkLogin(sessionId, userId);
   
    Map<String, String> map = new HashMap<String, String>();  
    try {  
      activityService.removeById(Long.parseLong(id));
      map.put("result", EnumAjax.SUCCESS.getMsg());
    } catch (Exception e) {  
      e.printStackTrace();  
      map.put("result", EnumAjax.ERROR.getMsg());
      throw e;  
    }  
    return map; 
  }
  
  @RequestMapping(method=RequestMethod.GET)
  @ResponseBody
  public Map<String, String> getActivitys(@RequestParam String sessionId,@RequestParam long userId) throws Exception{
    System.out.println("list");
//    System.out.println("sessionId"+sessionId);
    AppController.checkSession(sessionId);
    AppController.checkLogin(sessionId, userId);

    Map<String, String> map = new HashMap<String, String>();  
    try {  
      List<Activity> activitys = activityService.findByPageRequest(0, userId);
      
      map.put("result", EnumAjax.SUCCESS.getMsg());
      map.put("activitys", activitys.toString());
    } catch (Exception e) {  
      e.printStackTrace();  
      map.put("result", EnumAjax.ERROR.getMsg());
      throw e;  
    }  
    return map; 
  }
      
}  