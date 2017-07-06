package co.wrob.apm;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

public class Tester {
	
	public void test(){
		ServletResponse res;
		ServletRequest req;
		long startTime;
		
		StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
		for (int i = 0; i < stackTrace.length; i++) {
				StackTraceElement ste = stackTrace[i];
					System.out.println(ste);
		}
		
		javax.servlet.http.HttpServletResponse response = (javax.servlet.http.HttpServletResponse) res;
		javax.servlet.http.HttpServletRequest request = (javax.servlet.http.HttpServletRequest) req;
		
		if(!response.containsHeader("reqid")) {response.setHeader("reqid", java.util.UUID.randomUUID().toString() );}
		if(!response.containsHeader("start")) {response.setHeader("start", ""+startTime);}
		
		//Use the latest duration
		response.setHeader("duration", ""+(System.nanoTime() - startTime));
		
			
		System.out.println("Execution Duration "
		 + request.getMethod() 
		 + request.getLocalAddr() 
		 + Thread.currentThread().getStackTrace()[Thread.currentThread().getStackTrace().length-1].getLineNumber() 
		 + "(nano sec): "+ (System.nanoTime() - startTime) );
	}
	
	public void test2(){
		//HttpServletRequest request = (HttpServletRequest)org.apache.catalina.core.ApplicationFilterChain.getLastServicedRequest();
		//ThreadLocal
		
	}

}
