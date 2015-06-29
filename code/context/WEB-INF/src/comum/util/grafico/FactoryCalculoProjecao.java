package comum.util.grafico;

import java.util.GregorianCalendar;
import java.util.List;

public class FactoryCalculoProjecao {

	public static final int MEDIA = 1;
	public static final int MEDIA_ACELERADA = 2;
	
	
	/**
	 * Retorna o objeto respons�vel por calcular a proje��o
	 * de acordo com o tipo da proje��o (MEDIA, MEDIA_ACELERADA)
	 * 
	 * @see Projection
	 * 
	 * @param projecao
	 * @param values
	 * @return 
	 */
	public static Projection getProjection(int projecao, List<Double> values, GregorianCalendar start){
		
		if(projecao == MEDIA){
			return new AverageProjection(values, start);
		}else if(projecao == MEDIA_ACELERADA){
			return new AcelerationAverageProjection(values, start);
		}
		
		return null;
	}
	
	/**
	 * Retorna o objeto respons�vel por calcular a proje��o
	 * escolhendo o algoritmo mais adequado de acordo com os
	 * valores.
	 * 
	 * @see Projection
	 * 
	 * @param values
	 * @return
	 */
	public static Projection getProjection(List<Double> values, GregorianCalendar start){
		return new AverageProjection(values, start);
	}

	
}
