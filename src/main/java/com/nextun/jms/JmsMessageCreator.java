package com.nextun.jms;

import java.util.Date;

import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.Session;

import org.springframework.jms.core.MessageCreator;
public class JmsMessageCreator implements MessageCreator {
    private int receiveId;
    private String strMessage;
 
    public JmsMessageCreator(int no,String paramMessage) {
        this.receiveId = no;
        this.strMessage = paramMessage;
    }
 
    public Message createMessage(Session session) throws JMSException {
        MapMessage message = session.createMapMessage();
        message.setString("date", new Date().toString());
        message.setInt("receiveId", receiveId);
        message.setString("strMessage", strMessage);
        
        return message;
    }
}