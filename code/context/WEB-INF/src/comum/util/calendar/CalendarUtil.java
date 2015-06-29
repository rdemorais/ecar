package comum.util.calendar;

import java.util.GregorianCalendar;

public class CalendarUtil {
	/**
	 * Retorna um calendário com mesmo ano e mês e dia sempre igual a 1
	 * @param calendar
	 * @return
	 */
	public static GregorianCalendar cloneMonthCalendar(GregorianCalendar calendar){
		GregorianCalendar newCalendar =  new GregorianCalendar(calendar.get(GregorianCalendar.YEAR),calendar.get(GregorianCalendar.MONTH),1);
		return newCalendar;
	}
	/**
	 * Retorn a diferença em meses entre duas datas
	 * @param start
	 * @param end
	 * @return
	 */
	public static int monthDifference(GregorianCalendar start, GregorianCalendar end){
		int startMonth, endMonth, startYear, endYear;
		startMonth = start.get(GregorianCalendar.MONTH);
		endMonth = end.get(GregorianCalendar.MONTH);
		startYear = start.get(GregorianCalendar.YEAR);
		endYear = end.get(GregorianCalendar.YEAR);
		return ((endYear-startYear)*12)+endMonth-startMonth;
	}
}
