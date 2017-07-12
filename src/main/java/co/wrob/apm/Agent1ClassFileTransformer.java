package co.wrob.apm;

import java.io.ByteArrayInputStream;
import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.security.ProtectionDomain;
import java.util.Properties;

import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtConstructor;
import javassist.CtMethod;
import lombok.extern.slf4j.Slf4j;

@Slf4j
final class Agent1ClassFileTransformer implements ClassFileTransformer {

	private static final String RESOURCE_FILE = "java-strings.xml";
	private Properties properties;
	private ClassPool classPool = ClassPool.getDefault();

	public Agent1ClassFileTransformer() throws Exception {
		properties = new Properties();
		properties.loadFromXML(getClass().getClassLoader().getResourceAsStream(RESOURCE_FILE));
		addStringTransform();
	}

	public byte[] transform(ClassLoader loader
			, String className
			, Class<?> classBeingRedefined
			, ProtectionDomain protectionDomain
			, byte[] classfileBuffer
			) throws IllegalClassFormatException {

		byte[] byteCode = classfileBuffer;

//		log.debug("!!! " + className);
//		if (className.contains("java/lang/String")) {
//			log.debug("!!!!!!!!!!!!!!!!!!!!!!!!!");
//			// byteCode = addStringTransform(classfileBuffer, byteCode);
//		}

		if (className.equals("javax/servlet/http/HttpServlet")) {
			log.debug("!!!Instrumenting " + className);
			byteCode = addHttpServletTransform(byteCode);
		}

		return byteCode;
	}

	private void addStringTransform() {
		log.debug("addStringTransform");
		
		try {
			
			CtClass ctClass = classPool.get("java.lang.String");
			log.debug(ctClass.getName());
			
			ctClass.defrost();
			CtConstructor[] constructors = ctClass.getConstructors();
			for (CtConstructor ctConstructor : constructors) {
				log.debug("!!!" + ctConstructor);
			}

			ctClass.writeFile(".");
			ctClass.detach();
			log.debug("detached");

		} catch (Throwable e) {
			e.printStackTrace();
		}
		
	}

	private byte[] addHttpServletTransform(byte[] byteCode) {
		try {
			CtClass ctClass = classPool.makeClass(new ByteArrayInputStream(byteCode));
			CtMethod[] methods = ctClass.getDeclaredMethods("service");

			// Apply transform only to service(ServletRequest req,
			// ServletResponse res)
			methodLoop: for (CtMethod method : methods) {
				log.debug("!!!!" + method.getLongName());
				for (CtClass parameter : method.getParameterTypes()) {
					String paramName = parameter.getName();
					if (paramName.equals("javax.servlet.ServletResponse")
							|| paramName.equals("javax.servlet.ServletRequest")) {
						log.debug("found method");
						break;
					} else {
						log.debug("!!!Wrong!!!");
						continue methodLoop;
					}
				}

				log.debug("!!!Applying to " + method.getLongName());
				method.addLocalVariable("startTime", CtClass.longType);
				method.insertBefore(properties.getProperty("HttpServlet.service.insertBefore"));
				method.insertAfter(properties.getProperty("HttpServlet.service.insertAfter"));
			}
			byteCode = ctClass.toBytecode();
			ctClass.detach();
			log.debug("Instrumentation complete.");
		} catch (Throwable ex) {
			log.error("Exception: " + ex);
			ex.printStackTrace();
		}
		return byteCode;
	}
}