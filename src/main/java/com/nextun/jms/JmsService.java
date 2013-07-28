package com.nextun.jms;
 
public class JmsService extends JmsSender {
 
  
  public void addMessage(int receiveId , String strMessage){
    try {
      JmsMessageCreator creator = new JmsMessageCreator(receiveId,strMessage);
      super.send( creator);
    } catch (Exception e) {
      e.printStackTrace();
      // TODO Auto-generated catch block
      System.out.println("Can't link jms server");
    }
  }
  
  public void deleteMessage(int receiveId){
    try {
      JmsMessageCreator creator = new JmsMessageCreator(receiveId,"");
      super.send( creator);
    } catch (Exception e) {
      // TODO Auto-generated catch block
      System.out.println("Can't link jms server");
    }
  }



 
}