package com.nextun.controller.exception;

public class AppSessionExpiredException extends Exception{

  public AppSessionExpiredException(){ 
    System.out.println("AppSessionExpiredException");
  }
}
