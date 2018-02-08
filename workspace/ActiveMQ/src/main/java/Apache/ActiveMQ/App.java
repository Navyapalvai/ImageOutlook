package Apache.ActiveMQ;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import org.springframework.boot.web.support.SpringBootServletInitializer;


@SpringBootApplication
public class App extends SpringBootServletInitializer  {

     //Used when run as JAR

    public static void main(String[] args) throws Exception {
        SpringApplication.run(App.class, args);
//        try {
       MQconnection.messageReceiver(); 
//	//	consumer.setMessageListener(listener);
//			
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}

        
    }
}































