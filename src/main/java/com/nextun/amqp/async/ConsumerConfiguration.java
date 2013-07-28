package com.nextun.amqp.async;

import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ConsumerConfiguration extends ProducerConfiguration {

//	@Bean
//	public SimpleMessageListenerContainer listenerContainer() {
//	  System.out.println("async listenerContainer start");
//		SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
//		container.setConnectionFactory(connectionFactory());
//		container.setQueueNames(this.helloWorldQueueName);
//		container.setMessageListener(new MessageListenerAdapter(new HelloWorldHandler()));
//		return container;
//	}

}
