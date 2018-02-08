package Apache.ActiveMQ;

import java.awt.event.TextListener;
import java.util.HashMap;

/*
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;



public class MQconnection {
	
	public static String xmlReceivedMessage;

	public static void messageReceiver() throws Exception {
		// Get the connection Factory Local
		System.out.println("conection strt");
		ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(ActiveMQConnection.DEFAULT_BROKER_URL);
		
		// Get the connection Factory Server
		//ConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://devopsapp.southcentralus.cloudapp.azure.com:61616");
		
		// Get The connection
		Connection connection = connectionFactory.createConnection();

		// Start the connection
		connection.start();

		Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

		// Queue
		Destination queue = session.createQueue("JMSsrequestQ");

		// QueueSender
		MessageConsumer consumer = session.createConsumer (queue);

		// Receive Message
		Message message = consumer.receive();
		
		System.out.println("Done******************");


	}

}*/

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.ExceptionListener;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.camel.component.jms.JmsMessage;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jms.JmsException;
import org.springframework.jms.connection.CachingConnectionFactory;
import org.springframework.jms.listener.DefaultMessageListenerContainer;
import org.springframework.jms.listener.adapter.MessageListenerAdapter;
import org.springframework.util.backoff.FixedBackOff;
import org.springframework.web.bind.annotation.RequestMapping;


public class MQconnection implements MessageListener, ExceptionListener   {
	static CachingConnectionFactory cachingConnectionFactory;
	static ConnectionFactory connectionFactory ;
	static DefaultMessageListenerContainer messageListenerContainer;
	private static final String DML_CONCURRENCY_FORMAT = "%d-%d";

	private static final int DEFAULT_MAX_THREAD = 2;
	private static final int DEFAULT_MAX_MESSAGE_PER_TASK = 5;
	private static final int sleeptime = 30000;
	private static final String DEFAULT_LISTENER_METHOD = "getEvent";
	private static int maxthread = DEFAULT_MAX_THREAD;
	static Session session;
	static Destination queue;
	public static MessageConsumer consumer;
	static Connection connection;
	public static TextListener listener;
	public static TextMessage msg;
	public static  String s;
static int i;
  static int x;
  HashMap< String, Integer> hmap = new HashMap<String, Integer>();
	public static void messageReceiver() throws Exception {
		
		// Get the connection Factory Local
		System.out.println("conection strt");
		connectionFactory	= new ActiveMQConnectionFactory(ActiveMQConnection.DEFAULT_BROKER_URL);
//		
//		// Get the connection Factory Server
//		//ConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://devopsapp.southcentralus.cloudapp.azure.com:61616");
//		
		// Get The connection
		Connection connection = connectionFactory.createConnection();



		 session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

		// Queue
		queue = session.createQueue("JMSrequestQ");

		// Queueconsumer
		consumer = session.createConsumer (queue);
	//	consumer.setMessageListener(listener);
		
		MQconnection mq=new MQconnection();
		
		consumer.setMessageListener(mq);

		
		//MQQueueender sender = session.createConsumer(queue);
		
	
		connection.start();
		System.out.println("came hereeeee");
//	Message message = consumer.receive();
	
//System.out.println("***************"+message);

 	//   DefaultMessageListenerContainer messageListenerContainer = new DefaultMessageListenerContainer();
		cachingConnectionFactory = new CachingConnectionFactory();
		cachingConnectionFactory.setTargetConnectionFactory(connectionFactory);
        cachingConnectionFactory.setSessionCacheSize(50);
//        cachingConnectionFactory.setCacheProducers(true);
        cachingConnectionFactory.setCacheConsumers(true);
        cachingConnectionFactory.setReconnectOnException(false);
  	  messageListenerContainer.setConnectionFactory(cachingConnectionFactory);
  	  
		
		messageListenerContainer.setConcurrency(String.format(DML_CONCURRENCY_FORMAT,1,maxthread));
  	  messageListenerContainer.setSessionTransacted(true);
  	  messageListenerContainer.setMaxMessagesPerTask(DEFAULT_MAX_MESSAGE_PER_TASK);
  	  FixedBackOff fixedBackOff = new FixedBackOff();
  	  fixedBackOff.setInterval(sleeptime);
  	  fixedBackOff.setMaxAttempts(2);
  	  messageListenerContainer.setBackOff(fixedBackOff);
  	  messageListenerContainer.initialize();
  	 messageListenerContainer.start();
  	  MessageListenerAdapter messageListenerAdapter = new MessageListenerAdapter(messageListenerContainer.getConcurrentConsumers());
  	messageListenerAdapter.setDefaultListenerMethod(DEFAULT_LISTENER_METHOD);    

	}


    public void onMessage(Message message)
    {
       TextMessage msg = (TextMessage) message;
       
       try {
    	   s= msg.getText();
    	   if(msg.getText().equals("exit")){
    		   connection.close();
    		   System.out.println("Application Exits");
    	   }else{
    		   System.out.println("received: " + s);
    		   MongoSend ms = new   MongoSend();
    			  ms.getmessage(s);   
 			  i++;
    			  
//    			  HashMap< String, Integer> hmap = new HashMap<String, Integer>();
//    			 
//   				hmap.put(s, i);
//    				x = hmap.size();
//   				System.out.println("number of values stores is"+hmap.size());
    		  
    		  //count(s); 
    		 
    	   }
       } catch (JMSException ex) {
          ex.printStackTrace();
       }
    }
    
//public void count(){}
    public void onException(JMSException exception)
    {
       System.err.println("an error occurred: " + exception);
    }
    
    public void stopserver() throws JMSException{
    	session.close();
    	connection.close();
    	messageListenerContainer.stop();
    	
    	
    }
    
    public void startserver() throws Exception{
    	
    	messageReceiver();
    	
    	
    }
//    @RequestMapping("/stop")
//	public void stopMQ() {
//		try {
//		MQconnection m = new MQconnection();
//		messageListenerContainer.stop();
//			System.out.println("Done******************");
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	}
    
//    @RequestMapping("/start")
//	public void startMQ() {
//		try {
//		MQconnection m = new MQconnection();
//		messageReceiver();
//		//messageListenerContainer.start();
//			System.out.println("Done******************");
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
	
}

	 



