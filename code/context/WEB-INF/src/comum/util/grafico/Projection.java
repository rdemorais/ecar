package comum.util.grafico;

import java.util.Collection;
import java.util.GregorianCalendar;
import java.util.Map;

/**
 * 
 * Interface para defini��o classes respons�veis 
 * por calcular as proje��es.
 * 
 * @author N/A
 *
 */
public interface Projection {

	/**
	 * Obt�m o valor projetado para uma 
	 * determinada data, a granularidade de proje��o � o m�s.
	 * Todas as fun��es de proje��o ignorar�o os dias nas datas.
	 * 
	 * @param date m�s e ano
	 * @return
	 */
	public double getValue(GregorianCalendar date);		
	
	/**
	 * Dado um valor projetado, obt�m a data (m�s e ano)
	 * que o valor ocorrer�.
	 * 
	 * @param value
	 * @return
	 */
	public GregorianCalendar getDate(double value);

	/**
	 * Retorna uma matriz de valores projetados para
	 * um determinado per�odo, m�s a m�s.
	 * 
	 * @param start
	 * @param end
	 * @return
	 */
	public Collection<Double> getMonthProjection(GregorianCalendar start, GregorianCalendar end);
	
	/**
	 * Retorna o mapa de valores projetados para
	 * um determinado per�odo, m�s a m�s.
	 * 
	 * @param start
	 * @param end
	 * @return
	 */
	public Map<GregorianCalendar,Double> getMapProjection(GregorianCalendar start, GregorianCalendar end);
	
}
