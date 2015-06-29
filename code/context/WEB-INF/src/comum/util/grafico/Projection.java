package comum.util.grafico;

import java.util.Collection;
import java.util.GregorianCalendar;
import java.util.Map;

/**
 * 
 * Interface para definição classes responsáveis 
 * por calcular as projeções.
 * 
 * @author N/A
 *
 */
public interface Projection {

	/**
	 * Obtém o valor projetado para uma 
	 * determinada data, a granularidade de projeção é o mês.
	 * Todas as funções de projeção ignorarão os dias nas datas.
	 * 
	 * @param date mês e ano
	 * @return
	 */
	public double getValue(GregorianCalendar date);		
	
	/**
	 * Dado um valor projetado, obtém a data (mês e ano)
	 * que o valor ocorrerá.
	 * 
	 * @param value
	 * @return
	 */
	public GregorianCalendar getDate(double value);

	/**
	 * Retorna uma matriz de valores projetados para
	 * um determinado período, mês a mês.
	 * 
	 * @param start
	 * @param end
	 * @return
	 */
	public Collection<Double> getMonthProjection(GregorianCalendar start, GregorianCalendar end);
	
	/**
	 * Retorna o mapa de valores projetados para
	 * um determinado período, mês a mês.
	 * 
	 * @param start
	 * @param end
	 * @return
	 */
	public Map<GregorianCalendar,Double> getMapProjection(GregorianCalendar start, GregorianCalendar end);
	
}
