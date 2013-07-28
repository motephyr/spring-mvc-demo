package com.nextun.utils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CookiesUtil {
  
  public static final int COOKIE_DEFAULT_AGE = 30 * 24 * 60 * 60; // 30天
  private static final String COOKIE_DOMAIN = ".nextun.com";
  private static final String COOKIE_PATH = "/";
  
  /**
   * add cookie
   * 
   * @param cookieName
   * @param cookieValue
   * @param age : cookie Maxage -- 0 : delete cookie, negative value : cookie not store, cookie
   *   will be removed until browser is closed.
   * @param response
   */
  public static void addCookie(String cookieName, String cookieValue, int age,
      HttpServletResponse response) {
    addCookie(cookieName, cookieValue, age, COOKIE_DOMAIN, COOKIE_PATH, response);
  }
  
  /**
   * add cookie
   * @param cookieName
   * @param cookieValue
   * @param age 
   * @param domain
   * @param path
   * @param response
   */
  public static void addCookie(String cookieName, String cookieValue, int age,
      String domain, String path, HttpServletResponse response) {
    Cookie cookie = new Cookie(cookieName, cookieValue);
    cookie.setMaxAge(age);
    cookie.setDomain(domain);
    cookie.setPath(path);
    response.addCookie(cookie);
  }
  
  public static void removeCookie(String cookieName, HttpServletResponse response) {
    removeCookie(cookieName, COOKIE_DOMAIN, COOKIE_PATH, response);
  }
  
  /**
   * remove cookie
   * @param cookieName
   * @param domain
   * @param response
   */
  private static void removeCookie(String cookieName, String domain,
      String path, HttpServletResponse response) {
    Cookie cookie = new Cookie(cookieName, "");
    cookie.setPath(path);
    cookie.setDomain(domain);
    cookie.setMaxAge(0);
    response.addCookie(cookie);
  }
  
  /**
   * get cookie value by cookie name, if cookie not found, return null
   * 
   * @param cookieName
   * @param request
   * @return String, default is null
   */
  public static String getCookieValue(String cookieName,
      HttpServletRequest request) {
    String value = null;
    Cookie[] cookieArr = request.getCookies();
    if (cookieArr != null && cookieArr.length > 0) {
      for (Cookie cookie : cookieArr) {
        String name = cookie.getName();
        if (name.equals(cookieName)) {
          value = cookie.getValue();
          break;
        }
      }
    }
    return value;
  }
}