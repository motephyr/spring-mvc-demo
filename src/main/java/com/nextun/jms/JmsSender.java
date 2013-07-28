package com.nextun.jms;

 
import javax.jms.Destination;

import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
 
public abstract class JmsSender {
 
  protected JmsTemplate jmsTemplate;
  protected Destination[] destinations;
  
  public JmsTemplate getJmsTemplate() {
    return jmsTemplate;
  }

  public void setJmsTemplate(JmsTemplate jmsTemplate) {
    this.jmsTemplate = jmsTemplate;
  }

  public Destination[] getDestinations() {
    return destinations;
  }

  public void setDestinations(Destination[] destinations) {
    this.destinations = destinations;
  }
 
  
  protected void send(MessageCreator creator){
    for(int i = 0; i < destinations.length;i++){
      jmsTemplate.send(destinations[i],creator);
    }
  }

 
}