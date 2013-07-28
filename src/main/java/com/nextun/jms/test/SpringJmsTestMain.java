package com.nextun.jms.test;

import org.springframework.jms.listener.DefaultMessageListenerContainer;
 
public class SpringJmsTestMain {
 
    private SpringPublish publisher;
    private DefaultMessageListenerContainer javaConsumer;
    public SpringPublish getPublisher() {
        return publisher;
    }
    public void setPublisher(SpringPublish publisher) {
        this.publisher = publisher;
    }
    public void testJMS()
    {
         javaConsumer.start();
         publisher.chart();
 
    }
    public void setJavaConsumer(DefaultMessageListenerContainer javaConsumer) {
        this.javaConsumer = javaConsumer;
    }
    public DefaultMessageListenerContainer getJavaConsumer() {
        return javaConsumer;
    }
}