package com.nextun.servlet;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import com.nextun.controller.AppController;


public class ShutDownListener implements ServletContextListener {
  // private static ExecutorService pool = Executors.newFixedThreadPool(1);

  public void contextDestroyed(ServletContextEvent arg0) {
    try {
      AppController.close();
//      WebApplicationContext context = ContextLoader.getCurrentWebApplicationContext(); 
//      String[] beanName = context.getBeanNamesForType(Scheduler.class);
//      for(int i=0;i < beanName.length;i++){
//        Scheduler scheduler = (Scheduler) context.getBean(beanName[i]); 
//        scheduler.shutdown(true);
//      }
//      Thread.sleep(1000);
//      
//      // This manually deregisters JDBC driver, which prevents Tomcat 7 from
//      // complaining about memory leaks wrto this class
//      Enumeration<Driver> drivers = DriverManager.getDrivers();
//      while (drivers.hasMoreElements()) {
//        Driver driver = drivers.nextElement();
//        DriverManager.deregisterDriver(driver);
//      }

    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public void contextInitialized(ServletContextEvent arg0) {

  }

}