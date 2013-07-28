package com.nextun.amqp.async;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Producer {

	public static void main(String[] args) throws Exception {
	  System.out.println("async producer start");
		new AnnotationConfigApplicationContext(ProducerConfiguration.class);
	}

}
