package com.nextun.controller; 
  
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.nextun.db.entity.Activity;
import com.nextun.db.entity.User;
import com.nextun.db.service.activity.ActivityService;
import com.nextun.enums.EnumActivityState;
import com.nextun.enums.EnumAjax;
import com.nextun.utils.NUConstants;
   
@Controller  
@SessionAttributes(NUConstants.USER_SESSION_KEY)
@RequestMapping("/oldActivity")  
public class OldActivityController {  
      
    @Autowired
    private ActivityService activityService;  
    
    @RequestMapping(method=RequestMethod.GET, value="/{id}")
    public Map<String, String> getActivity(@PathVariable String id,@ModelAttribute(NUConstants.USER_SESSION_KEY) User user) throws Exception{
      Activity activity = activityService.getById(Long.parseLong(id));

      Map<String, String> map = new HashMap<String, String>();  
      try {  
        map.put("result", EnumAjax.SUCCESS.getMsg());
        map.put("activity", activity.toString());
      } catch (Exception e) {  
        e.printStackTrace();  
        map.put("result", EnumAjax.ERROR.getMsg());
        throw e;  
      }  
      return map; 
    }
    
    @RequestMapping(method=RequestMethod.PUT, value="/{id}")
    public Map<String, String> updateActivity(@RequestBody Activity activity,@ModelAttribute(NUConstants.USER_SESSION_KEY) User user) throws Exception{
      activityService.update(activity);
      
      Map<String, String> map = new HashMap<String, String>();  
      try {  
        map.put("result", EnumAjax.SUCCESS.getMsg());
      } catch (Exception e) {  
        e.printStackTrace();  
        map.put("result", EnumAjax.ERROR.getMsg());
        throw e;  
      }  
      return map; 
    }
    
    @RequestMapping(method=RequestMethod.POST)
    public Map<String, String> addActivity(@RequestBody Activity activity,@ModelAttribute(NUConstants.USER_SESSION_KEY) User user) throws Exception{
      activityService.save(activity);
      
      Map<String, String> map = new HashMap<String, String>();  
      try {  
        map.put("result", EnumAjax.SUCCESS.getMsg());
      } catch (Exception e) {  
        e.printStackTrace();  
        map.put("result", EnumAjax.ERROR.getMsg());
        throw e;  
      }  
      return map; 
    }
    
    @RequestMapping(method=RequestMethod.DELETE, value="/{id}")
    public Map<String, String> removeActivity(@PathVariable String id,@ModelAttribute(NUConstants.USER_SESSION_KEY) User user) throws Exception{
      activityService.removeById(Long.parseLong(id));

      Map<String, String> map = new HashMap<String, String>();  
      try {  
        map.put("result", EnumAjax.SUCCESS.getMsg());
      } catch (Exception e) {  
        e.printStackTrace();  
        map.put("result", EnumAjax.ERROR.getMsg());
        throw e;  
      }  
      return map; 
    }
    
    @RequestMapping(method=RequestMethod.GET)
    public Map<String, String> getActivitys(@ModelAttribute(NUConstants.USER_SESSION_KEY) User user) throws Exception{
      List<Activity> activitys = activityService.findByPageRequest(0, user.getId());

      Map<String, String> map = new HashMap<String, String>();  
      try {  
        map.put("result", EnumAjax.SUCCESS.getMsg());
        map.put("activitys", activitys.toString());
      } catch (Exception e) {  
        e.printStackTrace();  
        map.put("result", EnumAjax.ERROR.getMsg());
        throw e;  
      }  
      return map; 
    }
      
    @RequestMapping(value="/insert",method=RequestMethod.POST)  
    @ResponseBody  
    public Map<String, String> insert(
        @RequestParam String subject,
        @RequestParam Date startDate,
        @RequestParam Date endDate,
        @ModelAttribute(NUConstants.USER_SESSION_KEY) User user) throws Exception{  
        
      long userId=user.getId();
      Timestamp now = new Timestamp(Calendar.getInstance().getTimeInMillis());

      Activity activity = new Activity();

      activity.setUserId(userId);
      activity.setSubject(subject);
      activity.setState(EnumActivityState.INIT);
      activity.setStartDate(startDate);
      activity.setEndDate(endDate);
      activity.setUpdateTime(now);
      Activity newActivity = activityService.save(activity); 
      
      
      Map<String, String> map = new HashMap<String, String>();  
      try {  
        map.put("result", EnumAjax.SUCCESS.getMsg());
      } catch (Exception e) {  
        e.printStackTrace();  
        map.put("result", EnumAjax.ERROR.getMsg());
        throw e;  
      }  
      return map;   
    }  
    
    @RequestMapping(value="/delete",method=RequestMethod.POST)  
    @ResponseBody  
    public Map<String, String> delete(@RequestParam long activityId,@ModelAttribute(NUConstants.USER_SESSION_KEY) User user) throws Exception{  
      Timestamp now = new Timestamp(Calendar.getInstance().getTimeInMillis());
      
      Activity activity = activityService.getById(activityId);
      if(user.getId() ==activity.getUserId()){
        activity.setState(EnumActivityState.DELETE);
        activity.setUpdateTime(now);
        activityService.update(activity);
          
      }
      Map<String, String> map = new HashMap<String, String>();  
      try {  
        map.put("result", EnumAjax.SUCCESS.getMsg());
      } catch (Exception e) {  
        e.printStackTrace();  
        map.put("result", EnumAjax.ERROR.getMsg());
        throw e;  
      }  
      return map;   
    } 
      
}  