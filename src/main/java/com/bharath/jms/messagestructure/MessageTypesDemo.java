package com.bharath.jms.messagestructure;

import javax.jms.BytesMessage;
import javax.jms.JMSContext;
import javax.jms.JMSException;
import javax.jms.JMSProducer;
import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.Queue;
import javax.jms.StreamMessage;
import javax.jms.TextMessage;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.apache.activemq.artemis.jms.client.ActiveMQConnectionFactory;

public class MessageTypesDemo {
	
		public static void main(String[] args) throws NamingException, InterruptedException, JMSException {
		
		InitialContext context = new InitialContext();
		Queue queue = (Queue) context.lookup("queue/myQueue");
		
		try(ActiveMQConnectionFactory cf = new ActiveMQConnectionFactory(); 
			JMSContext jmsContext = cf.createContext()) {
				
			JMSProducer producer = jmsContext.createProducer();
			TextMessage textMessage = jmsContext.createTextMessage("Arise Awake and stop not till the goal is reached");
			BytesMessage bytesMessage = jmsContext.createBytesMessage();
			bytesMessage.writeUTF("John");
			bytesMessage.writeLong(123l);
//			producer.send(queue, bytesMessage);
			
			StreamMessage streamMessage = jmsContext.createStreamMessage();
			streamMessage.writeBoolean(true);
			streamMessage.writeFloat(2.5f);
			
			MapMessage mapMessage = jmsContext.createMapMessage();
			mapMessage.setBoolean("isCreditAvailable", true);
			
			producer.send(queue, mapMessage);

			
//			BytesMessage messageReceived = (BytesMessage) jmsContext.createConsumer(queue).receive(5000);
//			System.out.println(messageReceived.readUTF());
//			System.out.println(messageReceived.readLong());
			
			MapMessage messageReceived = (MapMessage) jmsContext.createConsumer(queue).receive(5000);
			System.out.println(messageReceived.getBoolean("isCreditAvailable"));
//			System.out.println(messageReceived.readFloat());

			
			

		}
	}
}
