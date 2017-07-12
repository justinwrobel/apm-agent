# APM Agent


 * https://stackoverflow.com/questions/11898566/tutorials-about-javaagents#11898653
 * https://stackoverflow.com/questions/15872642/how-to-put-classes-for-javaagent-in-the-classpath
 * https://stackoverflow.com/questions/52353/in-java-what-is-the-best-way-to-determine-the-size-of-an-object?noredirect=1&lq=1
 * http://javapapers.com/core-java/java-instrumentation/
 * https://jboss-javassist.github.io/javassist/tutorial/tutorial.html
 
 
## Start it up
```
echo ' CATALINA_OPTS="-javaagent:C:/Users/jwrobel/Desktop/ws/git/apm-agent/target/apm-agent-0.0.1-SNAPSHOT-jar-with-dependencies.jar"' >> bin/setenv.sh
bin/shutdown.sh;sleep 3; bin/startup.sh; tail -f logs/catalina.out
```

## JSP error
```
Stacktrace:
	org.apache.jasper.compiler.DefaultErrorHandler.javacError(DefaultErrorHandler.java:102)
	org.apache.jasper.compiler.ErrorDispatcher.javacError(ErrorDispatcher.java:198)
	org.apache.jasper.compiler.JDTCompiler.generateClass(JDTCompiler.java:450)
	org.apache.jasper.compiler.Compiler.compile(Compiler.java:361)
	org.apache.jasper.compiler.Compiler.compile(Compiler.java:336)
	org.apache.jasper.compiler.Compiler.compile(Compiler.java:323)
	org.apache.jasper.JspCompilationContext.compile(JspCompilationContext.java:585)
	org.apache.jasper.servlet.JspServletWrapper.service(JspServletWrapper.java:363)
	org.apache.jasper.servlet.JspServlet.serviceJspFile(JspServlet.java:396)
	org.apache.jasper.servlet.JspServlet.service(JspServlet.java:340)
	javax.servlet.http.HttpServlet.service(HttpServlet.java:729)
	org.apache.tomcat.websocket.server.WsFilter.doFilter(WsFilter.java:52)
```

agent error
```
java.lang.NullPointerException
        at com.wrob.apm.Agent1$1.transform(Agent1.java:66)
        at sun.instrument.TransformerManager.transform(TransformerManager.java:188)
        at sun.instrument.InstrumentationImpl.transform(InstrumentationImpl.java:428)
        at java.lang.ClassLoader.defineClass1(Native Method)
        at java.lang.ClassLoader.defineClass(ClassLoader.java:760)
        at java.security.SecureClassLoader.defineClass(SecureClassLoader.java:142)
        at java.net.URLClassLoader.defineClass(URLClassLoader.java:455)
        at java.net.URLClassLoader.access$100(URLClassLoader.java:73)
        at java.net.URLClassLoader$1.run(URLClassLoader.java:367)
        at java.net.URLClassLoader$1.run(URLClassLoader.java:361)
        at java.security.AccessController.doPrivileged(Native Method)
        at java.net.URLClassLoader.findClass(URLClassLoader.java:360)
        at java.lang.ClassLoader.loadClass(ClassLoader.java:424)
        at java.lang.ClassLoader.loadClass(ClassLoader.java:357)
        at java.lang.ClassLoader.defineClass1(Native Method)
        at java.lang.ClassLoader.defineClass(ClassLoader.java:760)
        at java.security.SecureClassLoader.defineClass(SecureClassLoader.java:142)
        at java.net.URLClassLoader.defineClass(URLClassLoader.java:455)
        at java.net.URLClassLoader.access$100(URLClassLoader.java:73)
        at java.net.URLClassLoader$1.run(URLClassLoader.java:367)
        at java.net.URLClassLoader$1.run(URLClassLoader.java:361)
        at java.security.AccessController.doPrivileged(Native Method)
        at java.net.URLClassLoader.findClass(URLClassLoader.java:360)
        at java.lang.ClassLoader.loadClass(ClassLoader.java:424)
        at java.lang.ClassLoader.loadClass(ClassLoader.java:357)
        at java.lang.Class.getDeclaredMethods0(Native Method)
        at java.lang.Class.privateGetDeclaredMethods(Class.java:2693)
        at java.lang.Class.privateGetPublicMethods(Class.java:2894)
        at java.lang.Class.getMethods(Class.java:1607)
        at org.apache.tomcat.util.IntrospectionUtils.findMethods(IntrospectionUtils.java:302)
        at org.apache.tomcat.util.IntrospectionUtils.setProperty(IntrospectionUtils.java:54)
        at org.apache.tomcat.util.IntrospectionUtils.setProperty(IntrospectionUtils.java:41)
        at org.apache.catalina.startup.SetAllPropertiesRule.begin(SetAllPropertiesRule.java:66)
        at org.apache.tomcat.util.digester.Digester.startElement(Digester.java:1178)
        at com.sun.org.apache.xerces.internal.parsers.AbstractSAXParser.startElement(AbstractSAXParser.java:509)
        at com.sun.org.apache.xerces.internal.parsers.AbstractXMLDocumentParser.emptyElement(AbstractXMLDocumentParser.java:182)
        at com.sun.org.apache.xerces.internal.impl.XMLDocumentFragmentScannerImpl.scanStartElement(XMLDocumentFragmentScannerImpl.java:1343)
        at com.sun.org.apache.xerces.internal.impl.XMLDocumentFragmentScannerImpl$FragmentContentDriver.next(XMLDocumentFragmentScannerImpl.java:2786)
        at com.sun.org.apache.xerces.internal.impl.XMLDocumentScannerImpl.next(XMLDocumentScannerImpl.java:606)
        at com.sun.org.apache.xerces.internal.impl.XMLDocumentFragmentScannerImpl.scanDocument(XMLDocumentFragmentScannerImpl.java:510)
        at com.sun.org.apache.xerces.internal.parsers.XML11Configuration.parse(XML11Configuration.java:848)
        at com.sun.org.apache.xerces.internal.parsers.XML11Configuration.parse(XML11Configuration.java:777)
        at com.sun.org.apache.xerces.internal.parsers.XMLParser.parse(XMLParser.java:141)
        at com.sun.org.apache.xerces.internal.parsers.AbstractSAXParser.parse(AbstractSAXParser.java:1213)
        at com.sun.org.apache.xerces.internal.jaxp.SAXParserImpl$JAXPSAXParser.parse(SAXParserImpl.java:649)
        at org.apache.tomcat.util.digester.Digester.parse(Digester.java:1451)
        at org.apache.catalina.startup.Catalina.load(Catalina.java:551)
        at org.apache.catalina.startup.Catalina.load(Catalina.java:599)
        at sun.reflect.NativeMethodAccessorImpl.invoke0(Native Method)
        at sun.reflect.NativeMethodAccessorImpl.invoke(NativeMethodAccessorImpl.java:62)
        at sun.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:43)
        at java.lang.reflect.Method.invoke(Method.java:483)
        at org.apache.catalina.startup.Bootstrap.load(Bootstrap.java:310)
        at org.apache.catalina.startup.Bootstrap.main(Bootstrap.java:484)
```