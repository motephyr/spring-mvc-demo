package com.nextun.controller;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.RequestContextHolder;

import com.nextun.controller.exception.AppNotLoginException;
import com.nextun.controller.exception.AppSessionExpiredException;
import com.nextun.db.entity.User;
import com.nextun.db.entity.UserNu;
import com.nextun.db.service.user.UserNuService;
import com.nextun.db.service.user.UserService;
import com.nextun.enums.EnumAjax;

@Controller
@RequestMapping("/app")  
public class AppController implements Runnable {

  @Autowired private UserService userService;
  @Autowired private UserNuService userNuService;

  private static Map<String,Long> sessionAliveMap = new HashMap<String,Long>();
  private static HashMap<String,Map<String,Object>> sessionDataMap = new HashMap<String,Map<String,Object>>();
  
  private static ScheduledExecutorService checkSessionAliveScheduler = Executors.newScheduledThreadPool(1);
  private static final long sessionTimeout = 3600 * 1000; // 1h
  private static boolean isStop = false;
  
  @javax.annotation.PostConstruct
  public void init(){
    checkSessionAliveScheduler.scheduleAtFixedRate(this, 1, 5, TimeUnit.MINUTES);
  }
  

  public void run() {
    long now = System.currentTimeMillis();
    
    synchronized(this){
      Iterator<Map.Entry<String, Long>> it = sessionAliveMap.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<String, Long> pairs = it.next();
                
        long t = pairs.getValue();
        if(t-now > sessionTimeout){
          String sid = pairs.getKey();
          sessionDataMap.remove(sid);
          it.remove();
        }
      }
    }
  }
  public static void close(){
    checkSessionAliveScheduler.shutdown();
    isStop = true;
  }
  
  @RequestMapping(value="/getSession") 
  @ResponseBody
  public Map<String, String> getSession(
      @RequestParam(required=false) String oldSession) {
    System.out.println("getSession");
    String message = "";
    String newSession = null;
    
    if(StringUtils.isNotBlank(oldSession)){
      if(sessionAliveMap.containsKey(oldSession)){
        newSession = oldSession;
        message = "session exists.";
      }
      else{
        newSession = RequestContextHolder.currentRequestAttributes().getSessionId();
        sessionAliveMap.put(newSession, System.currentTimeMillis());
        message = "old session expired.";
      }
    }
    else{
      newSession = RequestContextHolder.currentRequestAttributes().getSessionId();
      sessionAliveMap.put(newSession, System.currentTimeMillis());
      message = "new session created.";
    }
    
    Map<String, String> map = new HashMap<String, String>();

    map.put("result", EnumAjax.SUCCESS.getMsg());
    map.put("sessionId", newSession);
    map.put("message", message);

    return map;
  }
  
  @RequestMapping(method=RequestMethod.POST,value="/login") 
  @ResponseBody
  public Map<String, Object> login(
      @RequestParam String sessionId,
      @RequestParam String email,
      @RequestParam(required=false) String password,
      @RequestParam(required=false) String fbId) throws Exception {
    System.out.println("login");
//    System.out.println("sessionId"+sessionId);
//    System.out.println("email"+email);
//    System.out.println("password"+password);
    checkSession(sessionId);
    
    Map<String, Object> map = new HashMap<String, Object>();
    try {
      UserNu userNu = userNuService.selectByEmail(email);
      if (userNu.getPassword().equals(password)) {
        User user = userService.getById(userNu.getUserId());
        
        this.putSession(sessionId, "myselfId", user.getId());
  
        map.put("user", user);
        map.put("result", EnumAjax.SUCCESS.getMsg());
      } else {
        map.put("result", EnumAjax.ERROR.getMsg());
      }

    } catch (Exception e) {
      e.printStackTrace();
      map.put("result", EnumAjax.ERROR.getMsg());
      throw e;
    }
    return map;
  }
  
  @RequestMapping(method=RequestMethod.POST,value="/logout") 
  @ResponseBody
  public Map<String, Object> logout(
      @RequestParam String sessionId) throws Exception {
    System.out.println("logout");
    checkSession(sessionId);
    
    Map<String, Object> map = new HashMap<String, Object>();
    try {
      this.deleteSession(sessionId);
      map.put("result", EnumAjax.SUCCESS.getMsg());

    } catch (Exception e) {
      e.printStackTrace();
      map.put("result", EnumAjax.ERROR.getMsg());
      throw e;
    }
    return map;
  }
  
  public static void checkSession(String sessionId) throws AppSessionExpiredException{
    if(sessionAliveMap.containsKey(sessionId)){
      sessionAliveMap.put(sessionId, System.currentTimeMillis());
    }else{
      throw new AppSessionExpiredException();
    }
  }
  
  private void putSession(String sessionId, String key, Object value){
    Map<String,Object> map = sessionDataMap.get(sessionId);
    if(map == null){
      map = new HashMap<String,Object>();
      sessionDataMap.put(sessionId, map);
    }
    map.put(key, value);
  }
  
  private void deleteSession(String sessionId){
    Map<String,Object> map = sessionDataMap.get(sessionId);
    if(map != null){
      sessionDataMap.remove(sessionId);
    }
  }
  
  public static void checkLogin(String sessionId, Long userId) throws AppNotLoginException{
    Long sid = (Long)getSessionValue(sessionId, "myselfId");
    if(sid == null || !sid.equals(userId)){
      throw new AppNotLoginException();
    }
  }
  
  private static Object getSessionValue(String sessionId, String key){
    Map<String,Object> map = sessionDataMap.get(sessionId);
    if(map == null)
      return null;
    return map.get(key);
  }

  
  
//  @Autowired
//  private ActivityService activityService;  
//  
//  @RequestMapping(method=RequestMethod.GET, value="/activity/{id}")
//  @ResponseBody
//  public Map<String, Object> getActivity(@PathVariable String id,@RequestParam String sessionId) throws Exception{
//    Activity activity = activityService.getById(Long.parseLong(id));
//    System.out.println("get"+id);
////    System.out.println("sessionId"+sessionId);
//    checkSession(sessionId);
//    Map<String, Object> map = new HashMap<String, Object>();  
//    try {  
//      map.put("activity", activity);
//      map.put("result", EnumAjax.SUCCESS.getMsg());
//    } catch (Exception e) {  
//      e.printStackTrace();  
//      map.put("result", EnumAjax.ERROR.getMsg());
//      throw e;  
//    }  
//    return map; 
//  }
//  
//  @RequestMapping(method=RequestMethod.PUT, value="/activity/{id}")
//  @ResponseBody
//  public Map<String, Object> updateActivity(@PathVariable String id,@RequestParam String activity_subject,@RequestParam String sessionId,@RequestParam long userId) throws Exception{
//    System.out.println("update"+activity_subject);
////    System.out.println("sessionId"+sessionId);
//    checkSession(sessionId);
//    checkLogin(sessionId, userId);
//    
//    Map<String, Object> map = new HashMap<String, Object>();  
//    try {  
//      Activity act = activityService.getById(Long.parseLong(id));
//      act.setSubject(activity_subject);
//      activityService.update(act);
//      
//      map.put("activity", act);
//      map.put("result", EnumAjax.SUCCESS.getMsg());
//    } catch (Exception e) {  
//      e.printStackTrace();  
//      map.put("result", EnumAjax.ERROR.getMsg());
//      throw e;  
//    }  
//    return map; 
//  }
//  
//  @RequestMapping(method=RequestMethod.POST, value="/activity")
//  @ResponseBody
//  public Map<String, Object> addActivity(@RequestParam String activity_subject,
//      @RequestParam long startDateInterval,
//      @RequestParam long endDateInterval,
//      @RequestParam String sessionId,
//      @RequestParam long userId) throws Exception{
//    System.out.println("add"+activity_subject);
////    System.out.println("sessionId"+sessionId);
////    System.out.println(startDateInterval);
////    System.out.println(endDateInterval);
//    checkSession(sessionId);
//    checkLogin(sessionId, userId);
//    
//    Map<String, Object> map = new HashMap<String, Object>();  
//    try {  
//      Activity act = new Activity();
//      act.setSubject(activity_subject);
//      act.setStartDate(new Date(startDateInterval*1000));
//      act.setEndDate(new Date(endDateInterval*1000));
//      act.setState(EnumActivityState.INIT);
//      act.setUserId(userId);
//      act.setUpdateTime(new Date());
//      activityService.save(act);
//    
//      map.put("activity", act);
//      map.put("result", EnumAjax.SUCCESS.getMsg());
//    } catch (Exception e) {  
//      e.printStackTrace();  
//      map.put("result", EnumAjax.ERROR.getMsg());
//      throw e;  
//    }  
//    return map; 
//  }
//  
//  @RequestMapping(method=RequestMethod.DELETE, value="/activity/{id}")
//  @ResponseBody
//  public Map<String, String> removeActivity(@PathVariable String id,@RequestParam String sessionId,@RequestParam long userId) throws Exception{
//    System.out.println("remove"+id);
////    System.out.println("sessionId"+sessionId);
//    checkSession(sessionId);
//    checkLogin(sessionId, userId);
//   
//    Map<String, String> map = new HashMap<String, String>();  
//    try {  
//      activityService.removeById(Long.parseLong(id));
//      map.put("result", EnumAjax.SUCCESS.getMsg());
//    } catch (Exception e) {  
//      e.printStackTrace();  
//      map.put("result", EnumAjax.ERROR.getMsg());
//      throw e;  
//    }  
//    return map; 
//  }
//  
//  @RequestMapping(method=RequestMethod.GET, value="/activity")
//  @ResponseBody
//  public Map<String, String> getActivitys(@RequestParam String sessionId,@RequestParam long userId) throws Exception{
//    System.out.println("list");
//    System.out.println("sessionId"+sessionId);
//    checkSession(sessionId);
//    checkLogin(sessionId, userId);
//
//    Map<String, String> map = new HashMap<String, String>();  
//    try {  
//      List<Activity> activitys = activityService.findByPageRequest(0, userId);
//      
//      map.put("result", EnumAjax.SUCCESS.getMsg());
//      map.put("activitys", activitys.toString());
//    } catch (Exception e) {  
//      e.printStackTrace();  
//      map.put("result", EnumAjax.ERROR.getMsg());
//      throw e;  
//    }  
//    return map; 
//  }
 
}