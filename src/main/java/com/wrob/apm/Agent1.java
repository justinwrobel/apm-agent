package com.wrob.apm;

import java.io.IOException;
import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.Instrumentation;

/**
 * All projects need to count:
 * 
 * Instrument the response to include a unique id How many string objects were
 * created for a single page request or RESTful request?
 * 
 * 
 * We then want you to explore (2 out of 3) data points specifically:
 * 
 * Time the request from start to finish. How much memory does a single page
 * request take? How many assemblies/classes/methods were loaded (depends on
 * language)?
 * 
 * @author jwrobel
 *
 */
public class Agent1 {

	public static void premain(String args, Instrumentation inst) throws IOException {
		System.out.println("!!!1");
		ClassFileTransformer transformer = new Agent1ClassFileTransformer();
		inst.addTransformer(transformer);

	}

}
