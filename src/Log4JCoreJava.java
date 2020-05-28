import org.apache.log4j.Logger;

public class Log4JCoreJava {
	 final static Logger logger = Logger.getLogger(Log4JCoreJava.class);
	 public void callMeInAppInfo(String parameter) {
	  if (logger.isInfoEnabled()) {
	   logger.info("A entrado : " + parameter);
	  }
	 }
	 public void callMeInAppWarn(String parameter) {
	  if (logger.isInfoEnabled()) {
	   logger.warn("Alerta : " + parameter);
	  }
	 }
	 public void callMeInAppDebug(String parameter) {
	  if (logger.isDebugEnabled()) {
	   logger.debug("This is Debug : " + parameter);
	  }
	 }
	 public void callMeInAppError(String parameter) {
	  if (logger.isInfoEnabled()) {
	   logger.error("This is error : " + parameter);
	  }
	 }
	 public void callMeInAppFatal(String parameter) {
	  if (logger.isInfoEnabled()) {
	   logger.fatal("This is fatal : " + parameter);
	  }
	 }
}
