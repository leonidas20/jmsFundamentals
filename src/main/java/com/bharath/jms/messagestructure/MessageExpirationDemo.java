package com.bharath.jms.messagestructure;

import javax.jms.JMSContext;
import javax.jms.JMSProducer;
import javax.jms.Message;
import javax.jms.Queue;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.apache.activemq.artemis.jms.client.ActiveMQConnectionFactory;

public class MessageExpirationDemo {
	
	
	private static Object lookup;

	public static void main(String[] args) throws NamingException, InterruptedException {
		
		InitialContext context = new InitialContext();
		Queue queue = (Queue) context.lookup("queue/myQueue");
		
		try(ActiveMQConnectionFactory cf = new ActiveMQConnectionFactory(); 
			JMSContext jmsContext = cf.createContext()) {
				
			JMSProducer producer = jmsContext.createProducer();
			//producer.setTimeToLive(2000);
			producer.send(queue, "Arise Awake and stop not till the goal is reached");
			Thread.sleep(5000);
			
			Message messageReceived = jmsContext.createConsumer(queue).receive(2000);
			System.out.println(messageReceived);
		}
	}
}
