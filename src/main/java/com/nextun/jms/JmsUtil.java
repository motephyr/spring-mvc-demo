package com.nextun.jms;

 
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;
 
public class JmsUtil {
 private static WebApplicationContext ctx = ContextLoader.getCurrentWebApplicationContext();
 
 public static <T extends JmsSender>T getBean(Class<T> wantedJmsSender){
   T obj = ctx.getBean(wantedJmsSender);
  return obj;
 }
 
 
}