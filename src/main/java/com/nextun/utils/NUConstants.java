package com.nextun.utils;

import org.apache.commons.lang.math.RandomUtils;

public final class NUConstants { 
  
  public final static String GLOBAL_RESOURCE_NAME = "messages";

  public final static int COOKIE_AGE = 2592000; // 2592000 s -> 30 days
  
  public final static String USER_SESSION_KEY = "USER_SESSION";
  
  public static final String NEXTUN_COOKIE_NAME = "nextun_a";
  
  public final static int JS_VERSION = RandomUtils.nextInt(10000); 
  
  public final static String ROOT_DIR = new NUFunctions().getCurrentConfigPath(null,null) + "/"; //"/Users/tomcat7/dido/";
  
}