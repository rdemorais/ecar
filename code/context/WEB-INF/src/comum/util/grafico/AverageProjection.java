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
 * Calcula a projeção baseado na média.
 * 
 * @author N/A
 *
 */
//FIXME: Adicionar iteration limit
public class AverageProjection implements Projection {

	//Armazena os valores reais realizados
	private List<Double> values = null;
	//Data de início dos valores realizados
	private GregorianCalendar startDate = null;
	//Data de fim dos valores realizados
	private GregorianCalendar endDate = null;
	//Armazena o incremento
	private Double increment = null;
	
	public AverageProjection(List<Double> values, GregorianCalendar start){
		this.values = values;
		this.startDate = CalendarUtil.cloneMonthCalendar(start);
		this.endDate = CalendarUtil.cloneMonthCalendar(start);
		this.endDate.add(GregorianCalendar.MONTH, values.size()-1);
		this.increment=this.calcIncrement(values);
	}
	
	
	/*
	 * (non-Javadoc)
	 * @see comum.util.grafico.Projection#getDate(double)
	 */
	public GregorianCalendar getDate(double value) {
		if(value<values.get(values.size()-1))
			return this.getDoneDate(value);
		else
			return this.getProjectedDate(value,this.increment);
		
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
		//Se data maior que o final do realizado pega valor projetado
		if(date.compareTo(this.endDate)>0){
			//Pega o número de meses a projetar
			int months = CalendarUtil.monthDifference(this.endDate,date);
			return this.values.get(this.values.size()-1)+(months*this.increment);
		}
		//Caso a data seja menor ou igual, retornar um valor realizado
		else{
			return this.getRealizedValue(date);
		}
	}
	/**
	 * Calcula o incremento baseado na média aritmética
	 * @param values
	 * @return
	 */
	private double calcIncrement(List<Double> values){
		if(values==null)
			return 0;
		int size = values.size();
		if (size<2)
			return 0;
		return (values.get(values.size()-1)-values.get(0))/size;
	}
	
	/**
	 * Obtém a data em que um valor foi realizado
	 * @param value
	 * @return
	 */
	private GregorianCalendar getDoneDate(Double value){
		ListIterator<Double> it = this.values.listIterator();
		GregorianCalendar date = CalendarUtil.cloneMonthCalendar(this.startDate);
		while(it.hasNext()){
			if(it.next()>value)
				break;
			else
				date.add(GregorianCalendar.MONTH, 1);
		}
		return date;
	}
	/**
	 * Obtém a data em que um valor será realizado segundo a projeção.
	 * Para isso, subtrai o valor desejado do último realizado e divide pelo incremento.
	 * O resultado é arredondado para cima pois a granularidade é mês.
	 * @param value
	 * @param increment
	 * @return
	 */
	private GregorianCalendar getProjectedDate(Double value, Double increment){
		double amount;
		int months;
		amount = value-this.values.get(this.values.size()-1);
		months = (int) Math.ceil(amount/increment);
		GregorianCalendar date = CalendarUtil.cloneMonthCalendar(this.startDate);
		date.add(GregorianCalendar.MONTH, months+this.values.size()-1);
		return date;
	}
	/**
	 * Retorna o valor realizado para a data informada
	 * @param date
	 * @return
	 */
	private Double getRealizedValue(GregorianCalendar date){
		int months = CalendarUtil.monthDifference(this.startDate, date);
		return this.values.get(months);
	}


    //FIXME: testar data de start;
	public Map<GregorianCalendar, Double> getMapProjection(
			GregorianCalendar start, GregorianCalendar end) {
		
		GregorianCalendar currDate = CalendarUtil.cloneMonthCalendar(start);	
		Map<GregorianCalendar,Double> values = new HashMap<GregorianCalendar,Double>();
		while(currDate.compareTo(end)<=0){
			values.put(CalendarUtil.cloneMonthCalendar(currDate),this.getValue(currDate));
			currDate.add(GregorianCalendar.MONTH, 1);
		}
		return values;
	}
}
