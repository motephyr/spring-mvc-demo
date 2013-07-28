package com.nextun.utils;

import org.apache.commons.lang.ClassUtils;


public class NUFunctions {
 
	public String getCurrentConfigPath(String toDir,String fileName) {
    String tempPath = this.getClass().getResource(ClassUtils.getShortCanonicalName(this.getClass()) + ".class").getFile();
    StringBuilder currentPath = new StringBuilder();
    if (toDir == null){
      toDir = "dido";
    }
    currentPath.append(tempPath.substring(0, tempPath.indexOf(toDir) + toDir.length()));
    if(fileName != null){
      currentPath.append(fileName);
    }
    return currentPath.toString();
  }
	
  public static String getCurrentConfigPathStartWithClassPath(String fileName) {
    String currentPath = NUFunctions.class.getResource(fileName).getPath();
    return currentPath;
  }
	
  public static void main(String[] args) {
    System.out.println(new NUFunctions().getCurrentConfigPath(null,null));
  }

}
