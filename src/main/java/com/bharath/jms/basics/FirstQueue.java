package com.bharath.jms.basics;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public class FirstQueue {

	public static void main(String[] args) {

		InitialContext initialContext = null;
		
		try {
			initialContext = new InitialContext();
			ConnectionFactory cf = (ConnectionFactory) initialContext.lookup("ConnectionFactory");
			Connection connection = cf.createConnection();
			Session session = connection.createSession();
			Queue queue = (Queue) initialContext.lookup("queue/myQueue=");
				
			session.createProducer(queue);
			MessageProducer producer = session.createProducer(queue);
			TextMessage message = session.createTextMessage("I am the creator of my destiny");
			producer.send(message);
			
			
		} catch (NamingException e) {
			e.printStackTrace();
		} catch (JMSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
