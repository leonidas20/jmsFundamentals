package com.bharath.jms.messagestructure;

import java.io.Serializable;

import javax.jms.BytesMessage;
import javax.jms.JMSContext;
import javax.jms.JMSException;
import javax.jms.JMSProducer;
import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.ObjectMessage;
import javax.jms.Queue;
import javax.jms.StreamMessage;
import javax.jms.TextMessage;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.apache.activemq.artemis.jms.client.ActiveMQConnectionFactory;

public class MessageTypesDemo implements Serializable {
	
	  private static final long serialVersionUID = 1L;

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
			
			ObjectMessage objectMessage = jmsContext.createObjectMessage();
			Patient patient = new Patient();
			patient.setId(123);
			patient.setname("John");
			objectMessage.setObject(patient);
			
//			producer.send(queue, mapMessage);
			producer.send(queue, objectMessage);

			
//			BytesMessage messageReceived = (BytesMessage) jmsContext.createConsumer(queue).receive(5000);
//			System.out.println(messageReceived.readUTF());
//			System.out.println(messageReceived.readLong());
			
//			MapMessage messageReceived = (MapMessage) jmsContext.createConsumer(queue).receive(5000);
//			System.out.println(messageReceived.getBoolean("isCreditAvailable"));
//			System.out.println(messageReceived.readFloat());

			ObjectMessage messageReceived = (ObjectMessage) jmsContext.createConsumer(queue).receive(5000);
			Patient object = (Patient) messageReceived.getObject();
			System.out.println(object.getId());
			System.out.println(object.getname());
		}
	}
}
