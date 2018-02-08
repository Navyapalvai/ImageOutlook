package Apache.ActiveMQ;

import java.util.HashMap;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Controller {
	@RequestMapping("/start")
	public ResponseEntity<String> startMQ() {
		try {
			MQconnection m = new MQconnection();
			m.startserver();
			System.out.println("Done******************");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		ResponseEntity<String> response=new ResponseEntity<String>("Listening started", HttpStatus.OK);
		return response;
	}
	
	@RequestMapping("/stop")
	public ResponseEntity<String> stopMQ() {
		try {
			MQconnection m = new MQconnection();
			m.stopserver();
		
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		ResponseEntity<String> response=new ResponseEntity<String>("Listening stoped", HttpStatus.OK);
		
		return response;
	}
	
	@RequestMapping("/count")
	public ResponseEntity<Integer> countMQ() {
		int count = 0;
		try {
			MQconnection m = new MQconnection();
			count = m.i;
		//	m.count();
		//	count=m.count();
		//	count=m.count();
			System.out.println("Done******************"+ m.i+"bvcdfg"+count);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		ResponseEntity<Integer> response=new ResponseEntity<Integer>( count , HttpStatus.OK);
		
		return response;
		//		
		
	}
}


//}
//
//public void count()
//{
//	  HashMap<Integer, String> hmap = new HashMap<Integer, String>();
//
//	  hmap.put(i, s);
//	  i++;
//	  System.out.println("number of values stores is"+i);
//	  
//}
