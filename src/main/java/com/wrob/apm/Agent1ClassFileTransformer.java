package com.wrob.apm;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.security.ProtectionDomain;
import java.util.Properties;
import java.util.Set;

import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;

final class Agent1ClassFileTransformer implements ClassFileTransformer {
	
	private static final String RESOURCE_FILE = "java-strings.xml";
	private Properties properties;
	

	public Agent1ClassFileTransformer() throws IOException {
		properties = new Properties();
		properties.loadFromXML(getClass().getClassLoader().getResourceAsStream(RESOURCE_FILE));
		
		Set<Object> keySet = properties.keySet();
		System.out.println("prop size: "+ keySet.size());
		 for (Object object : keySet) {
			 System.out.println("o: "+ object);
		}
	}

	public byte[] transform(ClassLoader loader, String className, Class<?> classBeingRedefined,
			ProtectionDomain protectionDomain, byte[] classfileBuffer) throws IllegalClassFormatException {

		byte[] byteCode = classfileBuffer;

		if (className.contains("String")) {
			// TODO find if calling class is HttpServlet
			// TODO

			System.out.println("!!!2" + className);
			StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();

		}

		if (className.contains("javax/servlet/http/HttpServlet")) {

			System.out.println("!!!Instrumenting " + className);
			// HttpServlet
			// HttpServletRequest
			// HttpServletResponse
			// TODO add unique value
			// HttpServletResponse httpServletResponseWrapper = new
			// HttpServletResponseWrapper();
			// httpServletResponseWrapper.flushBuffer();

			try {
				ClassPool classPool = ClassPool.getDefault();
				CtClass ctClass = classPool.makeClass(new ByteArrayInputStream(classfileBuffer));
				CtMethod[] methods = ctClass.getDeclaredMethods("service");

				// ctClass.getDeclaredMethod("service", new CtClass[]{,});

				methodLoop: for (CtMethod method : methods) {
					System.out.println("!!!!" + method.getLongName());

					for (CtClass parameter : method.getParameterTypes()) {
						if (parameter.getName().equals("javax.servlet.ServletResponse")
								|| parameter.getName().equals("javax.servlet.ServletRequest")) {
							System.out.println("found method");
							break;
						} else {
							System.out.println("!!!Wrong!!!");
							continue methodLoop;
						}

						/*
						 * if(!(class1.equals(ServletResponse.class) ||
						 * class1.equals(ServletRequest.class))){
						 * System.out.println("!!!!Wrong method!!!!"); break; }
						 */
					}

					// Thread.currentThread().getStackTrace()[0].getMethodName()

					// TODO save unique name per request
					System.out.println("!!!Applying to " + method.getLongName());
					method.addLocalVariable("startTime", CtClass.longType);
					method.insertBefore("startTime = System.nanoTime();");

					method.insertAfter(properties.getProperty("HttpServlet.service.insertAfter"));
					// HttpServletRequest req = null;
					// req.getRemoteAddr();
					// req.getLocalAddr();

				}
				byteCode = ctClass.toBytecode();
				ctClass.detach();
				System.out.println("Instrumentation complete.");
			} catch (Throwable ex) {
				System.out.println("Exception: " + ex);
				ex.printStackTrace();
			}

		}
		// javax.servlet.http.HttpServlet.service(HttpServlet.java:728)

		return byteCode;
	}
}