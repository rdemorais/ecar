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
 * Calcula a projeção baseado na variação da média.
 * 
 * @author N/A
 *
 */
public class AcelerationAverageProjection implements Projection {
	
	//Constante janela de média, ou seja, a média não é desde o início do sistema
	//isto tenta evitar que distorções se propaguem infinitamente
	private static final int AVERAGE_WINDOW = 3;
	private static final int ITERATION_LIMIT = 1000;
	//Armazena os valores reais realizados
	private List<Double> values = null;
	//Data de início dos valores realizados
	private GregorianCalendar startDate = null;
	//Data de fim dos valores realizados
	private GregorianCalendar endDate = null;
	//Lista de valores por mês com as médias e diferenças calculadas 
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
	 * Calcula novos dados até obter o valor informado
	 */
	private GregorianCalendar getProjectedDate(Double value){
		int numValues = this.monthValues.size()-1;
		while(value.compareTo(this.monthValues.get(numValues).value) > 0){
			this.addMonthProjection();
			numValues++;
			if(numValues>ITERATION_LIMIT){
				break;
				//FIXME: lançar exceção correta;
			}
		}
		GregorianCalendar date = CalendarUtil.cloneMonthCalendar(this.endDate);
		return date;
	}
	/**
	 * Calcula e adiciona um mês na lista de valores
	 */
	private void addMonthProjection(){
		this.monthValues.add(new MonthData(null,this.monthValues));
		this.endDate.add(GregorianCalendar.MONTH, 1);
	}
	/**
	 * Obtém a data em que um valor foi realizado
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
				//FIXME: lançar exceção correta;
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
				//FIXME: lançar exceção correta;
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
			
			//Se não foi enviado o valor ele é calculado 
			if(value==null){
				if(numValues!=0){
					this.value=(this.calcValue(monthValues.get(numValues-1)));
				}else{
					this.value=0.0;
				}
			}else{
				this.value=value;
			}
			
			//Se é o primeiro valor a velocidade é zero 
			if(numValues==0){
				this.velocity=0.0;
			}
			//Caso contrário: velocidade é posição final menos posição inicial sobre o tempo
			// V=(pf-pi)/t
			//como nosso tempo aqui é sempre 1 mês
			// V=(pf-pi)/1=pf-pi
			else{
				this.velocity=this.value-monthValues.get(numValues-1).value;
			}
			//A aceleração é a taxa de variação da velocidade em função do tempo.
			//Com tempo fixo 1 nossa aceleração é própria variação da velocidade.
			//A aceleração percentual é a proporção entre a velocidade atual e a anterior.
			//No primeiro valor da lista a variação é zero, então a proporção é 100% ou 1.
			if(numValues==0){
				this.acceleration=1.0;
			}
			//Se a velocidade anterior é zero a variação é desconsiderada
			//obs:Esta condição poderia estar com a outra, mas foi deixada aqui para 
			//facilitar o entendimento.
			else if(monthValues.get(numValues-1).velocity==0){
				this.acceleration=1.0;				
			}
			else{
				this.acceleration=this.velocity/monthValues.get(numValues-1).velocity;
			}

			//Aqui é calculada a média da aceleração percentual dentro da janela de valores anteriores
			//a janela de valores será igual a no máximo AVERAGE_WINDOW, porém se a lista de valores
			//anteriores for menor, utilizará a janela do damanho da lista de valores.
			int window=(numValues>AVERAGE_WINDOW)?AVERAGE_WINDOW:numValues;
			
			//A variável auxiliar total somará os valores das acelerações dentro da janela
			Double total = this.acceleration;
			for(int i=0;i<window;i++){
				total+=monthValues.get(numValues-i-1).acceleration;
			}
			//A média atual de aceleração é o total sobre o número de elementos da janela
			//(elementos anteriores) mais 1 (referente ao valor da aceleração atual)
			this.accelerationAverage=total/(window+1);
		}
		private Double calcValue(MonthData monthData){
			//O valor calculado é igual a 
			//valor anterior + (velocidade anterior * média de aceleração * tempo)
			//mas o tempo é fixo em 1 mês
			Double value = monthData.value+(monthData.accelerationAverage*monthData.velocity);
			return value;
		}

	}
}
