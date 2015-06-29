package comum.util.grafico;

import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;

import comum.util.calendar.CalendarUtil;



/**
 * 
 * Calcula a proje��o baseado na varia��o da m�dia.
 * 
 * @author N/A
 *
 */
public class AcelerationAverageProjection implements Projection {
	
	//Constante janela de m�dia, ou seja, a m�dia n�o � desde o in�cio do sistema
	//isto tenta evitar que distor��es se propaguem infinitamente
	private static final int AVERAGE_WINDOW = 3;
	private static final int ITERATION_LIMIT = 1000;
	//Armazena os valores reais realizados
	private List<Double> values = null;
	//Data de in�cio dos valores realizados
	private GregorianCalendar startDate = null;
	//Data de fim dos valores realizados
	private GregorianCalendar endDate = null;
	//Lista de valores por m�s com as m�dias e diferen�as calculadas 
	private List<MonthData> monthValues; 
	
	public AcelerationAverageProjection(List<Double> values, GregorianCalendar start){
		this.values = values;
		this.startDate = CalendarUtil.cloneMonthCalendar(start);
		this.monthValues = this.calcMonthValues();
	}
	
	
	private List<MonthData> calcMonthValues() {
		//Seta a data de fim do projeto
		this.endDate = CalendarUtil.cloneMonthCalendar(this.startDate);
		this.endDate.add(GregorianCalendar.MONTH, values.size()-1);
		
		List<MonthData> monthValues = new ArrayList<MonthData>();
		for(Double value: this.values){
			MonthData monthData = new MonthData(value,monthValues);
			monthValues.add(monthData);
		}
		return monthValues;
	}


	/*
	 * (non-Javadoc)
	 * @see comum.util.grafico.Projection#getDate(double)
	 */
	public GregorianCalendar getDate(double value) {
		if(value<=this.monthValues.get(monthValues.size()-1).value)
			return this.getDoneDate(value);
		else
			return this.getProjectedDate(value);
	}
	/*
	 * Calcula novos dados at� obter o valor informado
	 */
	private GregorianCalendar getProjectedDate(Double value){
		int numValues = this.monthValues.size()-1;
		while(value.compareTo(this.monthValues.get(numValues).value) > 0){
			this.addMonthProjection();
			numValues++;
			if(numValues>ITERATION_LIMIT){
				break;
				//FIXME: lan�ar exce��o correta;
			}
		}
		GregorianCalendar date = CalendarUtil.cloneMonthCalendar(this.endDate);
		return date;
	}
	/**
	 * Calcula e adiciona um m�s na lista de valores
	 */
	private void addMonthProjection(){
		this.monthValues.add(new MonthData(null,this.monthValues));
		this.endDate.add(GregorianCalendar.MONTH, 1);
	}
	/**
	 * Obt�m a data em que um valor foi realizado
	 * @param value
	 * @return
	 */
	private GregorianCalendar getDoneDate(Double value){
		ListIterator<MonthData> it = this.monthValues.listIterator();
		GregorianCalendar date = CalendarUtil.cloneMonthCalendar(this.startDate);
		while(it.hasNext()){
			if(it.next().value>value)
				break;
			else
				date.add(GregorianCalendar.MONTH, 1);
		}
		return date;
	}
	/*
	 * (non-Javadoc)
	 * @see comum.util.grafico.Projection#getMonthProjection(java.util.Date, java.util.Date)
	 */
	public List<Double> getMonthProjection(GregorianCalendar start, GregorianCalendar end) {
		GregorianCalendar currDate = CalendarUtil.cloneMonthCalendar(start);	
		List<Double> values = new ArrayList<Double>();
		while(currDate.compareTo(end)<=0){
			values.add(this.getValue(currDate));
			currDate.add(GregorianCalendar.MONTH, 1);
		}
		return values;
	}

	/*
	 * (non-Javadoc)
	 * @see comum.util.grafico.Projection#getValue(java.util.Date)
	 */
	public double getValue(GregorianCalendar date) {
		int months = CalendarUtil.monthDifference(this.startDate,date);
		int iterations=0;
		while((this.monthValues.size()-1)<months){
			this.addMonthProjection();
			iterations++;
			if(iterations>ITERATION_LIMIT){
				break;
				//FIXME: lan�ar exce��o correta;
			}
		}
		return this.monthValues.get(months).value;
		
	}

    //FIXME: testar data de start;
	public Map<GregorianCalendar, Double> getMapProjection(
			GregorianCalendar start, GregorianCalendar end) {
		
		GregorianCalendar currDate = CalendarUtil.cloneMonthCalendar(start);	
		Map<GregorianCalendar,Double> values = new HashMap<GregorianCalendar,Double>();
		int iterations=0;
		while(currDate.compareTo(end)<=0){
			values.put(CalendarUtil.cloneMonthCalendar(currDate),this.getValue(currDate));
			currDate.add(GregorianCalendar.MONTH, 1);
			iterations++;
			if(iterations>ITERATION_LIMIT){
				break;
				//FIXME: lan�ar exce��o correta;
			}
		}
		return values;
	}

	private class MonthData{
		
		public Double value;
		public Double velocity;
		public Double acceleration;
		public Double accelerationAverage;

		public MonthData(Double value, List<MonthData> monthValues) {
			
			//Tamanho da lista de valores anteriores
			int numValues = monthValues.size();
			
			//Se n�o foi enviado o valor ele � calculado 
			if(value==null){
				if(numValues!=0){
					this.value=(this.calcValue(monthValues.get(numValues-1)));
				}else{
					this.value=0.0;
				}
			}else{
				this.value=value;
			}
			
			//Se � o primeiro valor a velocidade � zero 
			if(numValues==0){
				this.velocity=0.0;
			}
			//Caso contr�rio: velocidade � posi��o final menos posi��o inicial sobre o tempo
			// V=(pf-pi)/t
			//como nosso tempo aqui � sempre 1 m�s
			// V=(pf-pi)/1=pf-pi
			else{
				this.velocity=this.value-monthValues.get(numValues-1).value;
			}
			//A acelera��o � a taxa de varia��o da velocidade em fun��o do tempo.
			//Com tempo fixo 1 nossa acelera��o � pr�pria varia��o da velocidade.
			//A acelera��o percentual � a propor��o entre a velocidade atual e a anterior.
			//No primeiro valor da lista a varia��o � zero, ent�o a propor��o � 100% ou 1.
			if(numValues==0){
				this.acceleration=1.0;
			}
			//Se a velocidade anterior � zero a varia��o � desconsiderada
			//obs:Esta condi��o poderia estar com a outra, mas foi deixada aqui para 
			//facilitar o entendimento.
			else if(monthValues.get(numValues-1).velocity==0){
				this.acceleration=1.0;				
			}
			else{
				this.acceleration=this.velocity/monthValues.get(numValues-1).velocity;
			}

			//Aqui � calculada a m�dia da acelera��o percentual dentro da janela de valores anteriores
			//a janela de valores ser� igual a no m�ximo AVERAGE_WINDOW, por�m se a lista de valores
			//anteriores for menor, utilizar� a janela do damanho da lista de valores.
			int window=(numValues>AVERAGE_WINDOW)?AVERAGE_WINDOW:numValues;
			
			//A vari�vel auxiliar total somar� os valores das acelera��es dentro da janela
			Double total = this.acceleration;
			for(int i=0;i<window;i++){
				total+=monthValues.get(numValues-i-1).acceleration;
			}
			//A m�dia atual de acelera��o � o total sobre o n�mero de elementos da janela
			//(elementos anteriores) mais 1 (referente ao valor da acelera��o atual)
			this.accelerationAverage=total/(window+1);
		}
		private Double calcValue(MonthData monthData){
			//O valor calculado � igual a 
			//valor anterior + (velocidade anterior * m�dia de acelera��o * tempo)
			//mas o tempo � fixo em 1 m�s
			Double value = monthData.value+(monthData.accelerationAverage*monthData.velocity);
			return value;
		}

	}
}
