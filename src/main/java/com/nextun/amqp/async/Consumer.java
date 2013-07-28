package com.nextun.amqp.async;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Consumer {

	public static void main(String[] args) {
		new AnnotationConfigApplicationContext(ConsumerConfiguration.class);
		System.out.println("async consumer start");
	}

}
