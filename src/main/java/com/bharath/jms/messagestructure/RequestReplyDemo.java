package com.bharath.jms.messagestructure;

import javax.jms.JMSConsumer;
import javax.jms.JMSContext;
import javax.jms.JMSProducer;
import javax.jms.Queue;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.apache.activemq.artemis.jms.client.ActiveMQConnectionFactory;

public class RequestReplyDemo {
	
	private static Object lookup;

	public static void main(String[] args) throws NamingException {
		
		InitialContext context = new InitialContext();
		Queue queue = (Queue) context.lookup("queue/requestQueue");
		Queue replyQueue = (Queue) context.lookup("queue/replyQueue");
		
		try(ActiveMQConnectionFactory cf = new ActiveMQConnectionFactory(); 
			JMSContext jmsContext = cf.createContext()) {
				
			JMSProducer producer = jmsContext.createProducer();
			producer.send(queue, "Arise Awake and stop not till the goal is reached");
			
			JMSConsumer consumer = jmsContext.createConsumer(queue);
			String messageReceived = consumer.receiveBody(String.class);
			System.out.println(messageReceived);
			
			JMSProducer replyProducer = jmsContext.createProducer();
			replyProducer.send(replyQueue, "You are awesome");
			
			JMSConsumer replyConsumer = jmsContext.createConsumer(replyQueue);
			System.out.println(replyConsumer.receiveBody(String.class));
			
		}
	}
}