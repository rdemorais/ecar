/*
 * Criado em 26/10/2004
 *
 */
package comum.util;


import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Classe com funções para manipulação de Data e hora.<br>
 * 
 * @author felipev
 * @since 26/10/2004
 * @version N/C
 */
public abstract class Data {
	
	static int timeZone = -3;
	
	/**
	 * Converte uma String para um Date.<br>
	 * 
	 * @author N/C 
	 * @since N/C 
	 * @version N/C
         * @param data
	 * @return java.Util.Date
	 */
	public static Date parseDate(String data){
		if (data.length()==10)//veio bem formatada
			return parseDate(data, "dd/MM/yyyy");

		if (data.indexOf("/")==1){
			data= "0"+data;
		}
		if (data.indexOf("/", 3)==4){
			String aux = data;
			data = aux.substring(0, 3)+"0"+aux.substring(3);
		}
		if (data.length()==8){
			String aux = data;
			data = aux.substring(0, 6)+"20"+aux.substring(6);
		}
			
		return parseDate(data, "dd/MM/yyyy");
	}
	
	/**
	 * Converte um Long para um Date.<br>
	 * 
         * @param data
         * @author N/C
	 * @since N/C 
	 * @version N/C
	 * @return java.Util.Date
	 */
	public static Date parseDate(long data){
		return new Date(data);
	}
	
	/**
	 * Converte uma String para um Date, seguindo um formato específico.<br>
	 * Ex.: String data = "14021982"<br>
	 *      String formato = "ddMMyyyy"<br>
	 * Retorna um objeto Date, com dia = 14, mês = 02, ano = 1982<br>
	 * 
	 * @author N/C
	 * @since N/C
	 * @version N/C
         * @param data
         * @param formato
         * @return java.Util.Date
	 */
	public static Date parseDate(String data, String formato){
        return parseDate(data, formato,true);
	}

	/**
	 * Converte uma String para um Date, seguindo um formato específico.<br>
	 * 
	 * Baseado na variável lenient pode gerar ou não uma data aproximada. 
	 * 
	 * @author N/C
	 * @since N/C
	 * @version N/C
	 * 
	 * @param data
     * @param formato
     * @param lenient
     * @return java.Util.Date
	 */
	public static Date parseDate(String data, String formato, boolean lenient){
        java.util.Date date = null;
        try {
            DateFormat formatter = new SimpleDateFormat(formato);
            formatter.setLenient(lenient);
            date = (java.util.Date)formatter.parse(data);
        } catch (ParseException e) {            
            return null;
        }
        return date;
	}
	
	/**
	 * Converte um Date para uma String no formato dd/mm/aaaa HH:mm:ss.<br>
	 * 
	 * @author N/C
	 * @since N/C
	 * @version N/C
         * @param data
	 * @return String 
	 */
	public static String parseDateHour(Date data){
        String formato = "dd/MM/yyyy HH:mm:ss:SSS";
        String strRetorno= null;
        SimpleDateFormat formatter = new SimpleDateFormat(formato);
        if ((data != null))
            strRetorno = formatter.format((java.util.Date)data); 
        else
            strRetorno = "";
        return strRetorno;	
	}
	
	/**
	 * Converte um String para um Date no formato dd/mm/aaaa HH:mm:ss.<br>
	 * 
         * @param data
         * @author N/C
	 * @since N/C
	 * @version N/C
	 * @return Date
	 */
	public static Date parseDateHour(String data){
        java.util.Date date = null;
        
        try {
            DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss:SSS");
            date = (java.util.Date)formatter.parse(data);
        } catch (ParseException e) {            
            return null;
        }
        
        return date;
	}
	
	/**
	 * Converte um String para um Date no formato dd/mm/aaaa HH:mm:ss.<br>
	 * 
	 * @author N/C
	 * @since N/C
	 * @version N/C
         * @param data
	 * @return Date
	 */
	public static Timestamp parseDateHourTimeStamp(String data){
        Timestamp date = null;
        
        try {
            DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss:SSS");
            date = new Timestamp(formatter.parse(data).getTime());
        } catch (ParseException e) {            
            return null;
        }
        
        return date;
	}
	/**
	 * Converte um Date para uma string no formato mm.ss.SSS
	 * 
         * @param data
         * @author N/C
	 * @since N/C
 	 * @version N/C
 	 * @return String
 	 */
    public static String parseDateHourSegundos(Date data){
        String formato = "mm.ss.SSS";
        String strRetorno= null;
        SimpleDateFormat formatter = new SimpleDateFormat(formato);
        if ((data != null))
            strRetorno = formatter.format((java.util.Date)data); 
        else
            strRetorno = "";
        return strRetorno;  
    }
    
	/**
	 * Converte um Date para uma String no formato dd/mm/aaaa.<br>
	 * 
	 * @author N/C
	 * @since N/C
	 * @version N/C
         * @param data
	 * @return String 
	 */
	public static String parseDate(Date data){
        String formato = "dd/MM/yyyy";
        String strRetorno= null;
        SimpleDateFormat formatter = new SimpleDateFormat(formato);
        if ((data != null))
            strRetorno = formatter.format((java.util.Date)data); 
        else
            strRetorno = "";
        return strRetorno;	
	}

	
	/**
	 * Retorna um objeto Date com a data atual.<br>
	 * 
	 * @author N/C
	 * @since N/C
	 * @version N/C
	 * @return Date		- Data de hoje
	 */
	public static Date getDataAtual(){		 
		Date trialTime = new Date();
		Calendar calendar = getCalendar(trialTime);		 
		return calendar.getTime();		 
	}

	/**
	 * Retorna um objeto Calendar com a data fornecida como parâmetro.<br>
	 * 
	 * @author N/C
	 * @since N/C
	 * @version N/C
         * @param data
	 * @return Calendar		- Objeto Calendar com a Data fornecida.
	 */
	public static Calendar getCalendar(Date data){
		 //String[] ids = TimeZone.getAvailableIDs(timeZone * 60 * 60 * 1000);
		 //SimpleTimeZone pdt = new SimpleTimeZone(timeZone * 60 * 60 * 1000, ids[0]);
		 //Calendar calendar = new GregorianCalendar(pdt);		 
		 //calendar.setTime(data);		 
		 return getGregorianCalendar(data);		 
	}

	/**
	 * Retorna um objeto Calendario Gregoriano a partir de uma data fornecida como parâmetro
	 * utilizando o default locale e timezone.<br>
	 * 
         * @param data
         * @author N/C
	 * @since N/C
	 * @version N/C
	 * @return GregorianCalendar
	 */
	public static GregorianCalendar getGregorianCalendar(Date data) {
	    GregorianCalendar gCal = new GregorianCalendar();
	    gCal.setTime(data);
	    return gCal;
	}
	
	/**
	 * Soma ou subtrai uma quantidade de DIAS de uma determinada data.<br>
	 * 
	 * @author N/C
	 * @since N/C
	 * @version N/C
         * @param qtdeDias
         * @param data
         * @return Date				- Resultado da Operação
	 */
	public static Date addDias(int qtdeDias, Date data){
	     Calendar calendar = getCalendar(data);
	     calendar.add(Calendar.DATE, qtdeDias);
	     return calendar.getTime();
	}

	/**
	 * Soma ou subtrai uma quantidade de MESES de uma determinada data.<br>
	 * 
	 * @author N/C
	 * @since N/C
	 * @version N/C
         * @param qtdeMeses 
         * @param data
         * @return Date				- Resultado da Operação.
	 */
	public static Date addMeses(int qtdeMeses, Date data){
	     Calendar calendar = getCalendar(data);
	     calendar.add(Calendar.MONTH, qtdeMeses);
	     return calendar.getTime();
	}

	/**
	 * Soma ou subtrai uma quantidade de ANOS de uma determinada data.<br>
	 * 
	 * @author N/C
	 * @since N/C
	 * @version N/C
         * @param qtdeAnos
         * @param data
         * @return Date			- Resultado da Operação.
	 */
	public static Date addAnos(int qtdeAnos, Date data){
	     Calendar calendar = getCalendar(data);
	     calendar.add(Calendar.MONTH, qtdeAnos);
	     return calendar.getTime();
	}

	
	/**
	 * Verifica se a data passada como parâmetro é FUTURA.<br>
	 * 
         * @param data
         * @author N/C
	 * @since N/C
	 * @version N/C
	 * @return boolean
	 */
	public static boolean isFuturo(Date data){
		if(getDataAtual().after(data))
			return false;
		else
			return true;
	}

	/**
	 * Verifica se a data passada como parâmetro é FUTURA.<br>
	 * 
         * @param data
         * @author N/C
	 * @since N/C
	 * @version N/C
	 * @return boolean
	 */
	public static boolean isFuturo(String data){
		if(getDataAtual().after(parseDate(data)))
			return false;
		else
			return true;
	}
	
	/**
	 * Verifica se a data passada como parâmetro é PASSADA.<br>
	 * 
	 * @author N/C
	 * @since N/C
	 * @version N/C
         * @param data
	 * @return boolean
	 */
	public static boolean isPassado(String data){
		if(isFuturo(data))
			return false;
		else 
			return true;
	}

	/**
	 * Verifica se a data passada como parâmetro é PASSADA.<br>
	 * 
	 * @author N/C
	 * @since N/C
	 * @version N/C
         * @param data
	 * @return boolean
	 */
	public static boolean isPassado(Date data){
		if(isFuturo(data))
			return false;
		else 
			return true;
	}
	/**
	 * Converte o numero do mes para sua String abreviada.<br>
	 * Ex.: `2` para `FEV` .<br>
	 * 
	 * @author N/C
	 * @since N/C
	 * @version N/C
         * @param mes
	 * @return String
	 */
	public static String getAbreviaturaMes(int mes){
		switch(mes){
			case 1:
				return "JAN";
			case 2:
				return "FEV";
			case 3:
				return "MAR";
			case 4:
				return "ABR";
			case 5:
				return "MAI";
			case 6:
				return "JUN";
			case 7:
				return "JUL";
			case 8:
				return "AGO";
			case 9:
				return "SET";
			case 10:
				return "OUT";
			case 11:
				return "NOV";
			case 12:
				return "DEZ";
			default:
				return "";
		}	
	}

	/**
	 * Converte o numero do mes para a String correspondente.<br>
	 * Ex.: '3' para 'Março'.<br>
	 * 
	 * @author N/C
	 * @since N/C
	 * @version N/C
         * @param mes
	 * @return String
	 */
	public static String getNomeMesExtenso(int mes){
		switch(mes){
			case 1:
				return "Janeiro";
			case 2:
				return "Fevereiro";
			case 3:
				return "Março";
			case 4:
				return "Abril";
			case 5:
				return "Maio";
			case 6:
				return "Junho";
			case 7:
				return "Julho";
			case 8:
				return "Agosto";
			case 9:
				return "Setembro";
			case 10:
				return "Outubro";
			case 11:
				return "Novembro";
			case 12:
				return "Dezembro";
			default:
				return "";
		}	
	}

	/**
	 * Retorna o dia(int) do mes.<br>
	 * 
	 * @author N/C
	 * @since N/C
	 * @version N/C
         * @param data
	 * @return int
 	 */
	public static int getDia(Date data) {
		Calendar calendar = getCalendar(data);	   
	    return calendar.get(Calendar.DAY_OF_MONTH);
	}

	/**
	 * Retorna o mes (int).<br>
	 * 
	 * @author N/C
	 * @since N/C
 	 * @version N/C
         * @param data
 	 * @return int
 	 */
	public static int getMes(Date data) {
		Calendar calendar = getCalendar(data);	   
	    return calendar.get(Calendar.MONTH);
	}
	
	/**
	 * Retorna o Ano(int).<br>
	 * 
         * @param data
         * @author N/C
	 * @since N/C
	 * @version N/C
	 * @return int
	 */
	public static int getAno(Date data) {
		Calendar calendar = getCalendar(data);	   
	    return calendar.get(Calendar.YEAR);
	}
	
	/**
	 * Retorna o horário no formato HH:MM.<br>
	 * 
         * @param data
         * @author N/C
	 * @since N/C
	 * @version N/C
	 * @return String
	 */
	public static String getHorario(Date data) {
		Calendar calendar = getCalendar(data);
		StringBuffer horaRetorno = new StringBuffer();
		StringBuffer minutoRetorno = new StringBuffer();
		
		int hora = calendar.get(Calendar.HOUR_OF_DAY);
		int minuto = calendar.get(Calendar.MINUTE);
				
		if( hora < 10 ) {
			horaRetorno.append("0").append(hora);
		} else {
			horaRetorno.append(hora);
		}
		
		if( minuto < 10 ) {
			minutoRetorno.append("0").append(minuto);
		} else {
			minutoRetorno.append(minuto);
		}
		
		return (horaRetorno.append(":").append(minutoRetorno)).toString(); 
	}
	
	/**
	 * Obter a hora atual.<br>
	 * 
	 * @author N/C
	 * @since N/C
	 * @version N/C
         * @param comSeparador
	 * @return String				- No formato hh:mm:ss
	 */
	public static String getHoraAtual(boolean comSeparador)	{
		Calendar cal = Calendar.getInstance();
		StringBuffer  hh = new StringBuffer(); 
		StringBuffer  mm = new StringBuffer(); 
		StringBuffer  ss = new StringBuffer(); 
		hh.append(cal.get(Calendar.HOUR_OF_DAY));
		mm.append(cal.get(Calendar.MINUTE));
		ss.append(cal.get(Calendar.SECOND));

		if(mm.length() < 2)
			mm.insert(0, "0");
		if(hh.length() < 2)
			hh.insert(0, "0");
		if(ss.length() < 2)
			ss.insert(0, "0");
							
		if(comSeparador) {
			return (hh.append(":").append(mm).append(":").append(ss)).toString();
		}
		else {
			return (hh.append(mm).append(ss)).toString();
		}
	}
	
	/**
	 * Retorna o ultimo dia(int) do mes.<br>
	 * 
         * @param data
         * @author N/C
	 * @since N/C
	 * @version N/C
	 * @return int
	 */
	public static int getUltimoDiaMes(Date data){
	     Calendar calendar = getCalendar(data);	     
	     return calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
	}
	
	/**
	 * Retorna o Dia da Semana(String) Abreviado.<br>
	 * ex.: Segunda e nao Segunda-feira<br>
	 * 
	 * @author N/C
	 * @since N/C
	 * @version N/C
         * @param data
	 * @return String
	 */
	public static String getDiaSemanaNome(Date data) {   
	    switch ((getCalendar(data)).get(Calendar.DAY_OF_WEEK)) {
	    	case 1: return "Domingo";
	    	case 2: return "Segunda";
	    	case 3: return "Terça";
	    	case 4: return "Quarta";
	    	case 5: return "Quinta";
	    	case 6: return "Sexta";
	    	case 7: return "Sábado";
	    	default: return "";	    	
	    }
	}
	
	/**
	 * Retorna o Dia da Semana por completo.<br>
	 * Ex.: Segunda-feira, e nao Segunda.<br>
	 * 
	 * @author N/C
	 * @since N/C
	 * @version N/C
         * @param data
	 * @return String
	 */
	public static String getDiaSemanaNomeExtenso(Date data) {   
	    switch ((getCalendar(data)).get(Calendar.DAY_OF_WEEK)) {
	    	case 1: return "Domingo";
	    	case 2: return "Segunda-feira";
	    	case 3: return "Terça-feira";
	    	case 4: return "Quarta-feira";
	    	case 5: return "Quinta-feira";
	    	case 6: return "Sexta-feira";
	    	case 7: return "Sábado";
	    	default: return "";	    	
	    }
	}
	
	/**
	 * Converte um Date para uma String no formato dd/mm/aaaa HH:mm:ss.<br>
	 * 
         * @param data
         * @author N/C
	 * @since N/C
	 * @version N/C
	 * @return String
	 */
	public static String parseDateHourMinuteSecond(Date data){
        String formato = "dd/MM/yyyy - HH:mm:ss";
        String strRetorno= null;
        SimpleDateFormat formatter = new SimpleDateFormat(formato);
        if ((data != null))
            strRetorno = formatter.format((java.util.Date)data); 
        else
            strRetorno = "";
        return strRetorno;	
	}
	
	
    /**
     * Compara duas Datas baseadas no Ano, mês e dia.
     * Desconsidera horas, minutos, ...
     *
     * @param   data1    <code>Date</code> primeira data a ser comparada.
     * @param   data2    <code>Date</code> segunda data a ser comparada.
     * @return  o valor <code>0</code> se o argumento data1 é igual ao argumento data2;
     * 			um valor menor que <code>0</code> se o argumento data1 é menor que o argumento data2; 
     *          e um valor maior que <code>0</code> se o argumento data1 é maior que o argumento data2.
     * @since v8_00_b1
     * @exception NullPointerException se <code>data</code> é nula.
     * @author 05110500460
     */
	public static int compareAnoMesDia(Date data1, Date data2){
				
		if (data1 == null || data2 == null )
			throw new NullPointerException();
		
//		String str1 = Data.parseDate(data1);
//		String str2 = Data.parseDate(data2);
		
		if (Data.getAno(data1)  > Data.getAno(data2) ) {
			return 1;
		} else if (Data.getAno(data1)  < Data.getAno(data2) ){
			return -1;
		} 
		
		if (Data.getMes(data1)  > Data.getMes(data2) ) {
			return 1;
		} else if (Data.getMes(data1)  < Data.getMes(data2) ){
			return -1;
		} 
		
		if (Data.getDia(data1)  > Data.getDia(data2) ) {
			return 1;
		} else if (Data.getDia(data1)  < Data.getDia(data2) ){
			return -1;
		} 
		
		// se chegou aqui é porque é não é nulo 
		return 0;
	}
}
