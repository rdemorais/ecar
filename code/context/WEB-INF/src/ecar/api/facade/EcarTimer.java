package ecar.api.facade;


/**
 * 
 * Classe usada para calcular tempo gasto 
 * em um método.
 * 
 * @author N/A
 *
 */
public class EcarTimer {

	private  long start;
	private  long finish;
	
	public void start(){
		start = System.currentTimeMillis();
	}
	
	public void stop(){
		finish = System.currentTimeMillis();
	}
	
	
	public long getEllapsedTimeInMillis(){
		return (finish - start);
	}
	
}
