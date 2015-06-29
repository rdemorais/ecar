package ecar.email;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * 
 * @author 70744416353
 */
public class AgendadorListener implements ServletContextListener {

    /**
     * Inicializador.<br>
     * 
     * @author N/C
     * @since N/C
     * @version N/C
     * @param event
     */
    public void contextInitialized(ServletContextEvent event) {
    	/*
	ThreadLocal threadEmail = new ThreadLocal();
	ThreadLocal threadPerformance = new ThreadLocal();

	AgendadorEmail agenda = AgendadorEmail.getInstance();
	threadEmail.set(agenda);
	agenda.disparaTimer();
	
	AgendadorPerformance perf = new AgendadorPerformance();
	threadPerformance.set(perf);
	perf.disparaTimer();
	*/
    }

    /**
     * Destrutor.<br>
     * 
     * @author N/C
     * @since N/C
     * @version N/C
     * @param event
     */
    public void contextDestroyed(ServletContextEvent event) {

    }
}
