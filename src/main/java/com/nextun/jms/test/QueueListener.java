package com.nextun.jms.test;


import java.util.Date;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;

import org.apache.activemq.command.ActiveMQMapMessage;
import org.springframework.stereotype.Component;

@Component
public class QueueListener implements MessageListener{
  
    public void onMessage( final Message message ){
        ActiveMQMapMessage msg = null;
        System.out.println("ONMessage-----------------"+message.toString());
        try {
            if (message instanceof ActiveMQMapMessage) {
                msg = (ActiveMQMapMessage) message;
                String sentDate = msg.getString("date");
                String reMessage  = msg.getString("message");
                int sentCount  = msg.getInt("count");
                System.out.println("-------------New Message Arrival-----------"+new Date());
                System.out.println("It's "+sentCount+" time From Darcy: "+reMessage+"   ---Send time :" + sentDate);
            }
        } catch (JMSException e) {
            System.out.println("JMSException in onMessage(): " + e.toString());
        } catch (Throwable t) {
            System.out.println("Exception in onMessage():" + t.getMessage());
        }
    }
}
