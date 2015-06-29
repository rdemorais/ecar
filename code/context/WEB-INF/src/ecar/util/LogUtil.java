package ecar.util;

import org.apache.log4j.Logger;

public class LogUtil {

	private Logger log;
	private Class classe;

	public LogUtil() {
	}
	
	public LogUtil(Class classe) {
		this.classe = classe;
		log = Logger.getLogger(this.classe.getClass());
		log.info("O log abriu na classe: " + classe.getName());
	}
	
	public void logInfo(String message, long inicioTempo, long finalizaTempo){
		log.info(String.format(message + ": %.2f segundos%n", (finalizaTempo - inicioTempo) / 1000d));
	}
	
	public void logError(String message, Throwable t){
		log.error(message, t);
	}
	
	public Logger getLog() {
		return log;
	}

	public void setLog(Logger log) {
		this.log = log;
	}
	
	
}
