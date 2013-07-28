package com.nextun.jms.test;

import java.util.Date;
 
import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.Session;
import org.springframework.jms.core.MessageCreator;
public class MyMessageCreator implements MessageCreator {
    private int msgNo;
    private String strMessage;
 
    public MyMessageCreator(int no,String paramMessage) {
        this.msgNo = no;
        this.strMessage = paramMessage;
    }
 
    public Message createMessage(Session session) throws JMSException {
        MapMessage message = session.createMapMessage();
        message.setString("date", new Date().toString());
        message.setString("message", strMessage);
        message.setInt("count", msgNo);
        return message;
    }
}