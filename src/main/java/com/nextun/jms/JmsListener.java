package com.nextun.jms;


import java.util.Date;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;

import org.apache.activemq.command.ActiveMQMapMessage;
import org.springframework.stereotype.Component;

public class JmsListener implements MessageListener{
 
  
    public void onMessage( final Message message ){
        ActiveMQMapMessage msg = null;
        System.out.println("ONMessage-----------------"+message.toString());
        try {
            if (message instanceof ActiveMQMapMessage) {
                msg = (ActiveMQMapMessage) message;

                String sentDate = msg.getString("date");
                
                String strMessage  = msg.getString("strMessage");
                int receiveId  = msg.getInt("receiveId");
                System.out.println("-------------New Message Arrival-----------"+new Date());
                System.out.println("It's "+receiveId+" time From Darcy: "+strMessage+"   ---Send time :" + sentDate);
                if(strMessage.equals("")){
                  //NUVariables.userIdNoticeSet.remove((long) receiveId);
                }else{
                  //NUVariables.userIdNoticeSet.add((long) receiveId);
                }
            }
        } catch (JMSException e) {
            System.out.println("JMSException in onMessage(): " + e.toString());
        } catch (Throwable t) {
            System.out.println("Exception in onMessage():" + t.getMessage());
        }
    }
    

}
