package com.nextun.jms.test;

import javax.jms.ExceptionListener;
import javax.jms.JMSException;

import org.springframework.stereotype.Component;

@Component
public class JmsExceptionListener implements ExceptionListener
{
    public void onException( final JMSException e )
    {
        e.printStackTrace();
    }
}
