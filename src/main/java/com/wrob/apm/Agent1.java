package com.wrob.apm;

import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.lang.instrument.Instrumentation;
import java.security.ProtectionDomain;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;


/**
 * All projects need to count:

    * How many string objects were created for a single page request or RESTful request?
    * Instrument the response to include a unique id

	We then want you to explore (2 out of 3) data points specifically:

    * Time the request from start to finish.
    * How much memory does a single page request take?
    * How many assemblies/classes/methods were loaded (depends on language)?

 * @author jwrobel
 *
 */
public class Agent1 {
	
	public static void premain(String args, Instrumentation inst) {
		System.out.println("!!!1");
		ClassFileTransformer transformer = new ClassFileTransformer(){

			public byte[] transform(ClassLoader loader
					, String className
					, Class<?> classBeingRedefined
					,ProtectionDomain protectionDomain
					, byte[] classfileBuffer
					) throws IllegalClassFormatException {
				if(className.contains("HttpServletRequest")){
					System.out.println(
							"!!!2"
							+className);
							System.out.println(classBeingRedefined.isInstance(HttpServletRequest.class));
							System.out.println("!!!3");
					
				}
				//	javax.servlet.http.HttpServlet.service(HttpServlet.java:728)
				
				return null;
			}
			
		};
		inst.addTransformer(transformer);
		
        
    }

}
