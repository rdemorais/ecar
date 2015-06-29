package ecar.api.facade;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import comum.util.Data;

/**
 * 
 * Classe para tentar abstrair as várias formas do ecar lidar com datas...
 * Algumas classes usam "01-2010" outras "01/2010"
 * 
 * Mês varia de 1 a 12. Caso valor de mês venha das classes java, como a 
 * Date, deve-se somar 1.
 * 
 * Os métodos implememtados retornam as data em vários formatos que foram 
 * indentificados até agora durante as manutenções.
 * 
 * @author 82035644020
 *
 */
public class EcarData implements Comparable<EcarData> {

	public static final int ANTES = -1;
	public static final int IGUAL = 0;
	public static final int DEPOIS = 1;

	
	private int anoRef; 
	private int mesRef; 
	
	/**
	 * Mes variando de 1 a 12
	 * @param mes
	 * @param ano
	 */
	public EcarData(String mes, String ano){
		anoRef = Integer.valueOf(ano);
		mesRef = Integer.valueOf(mes);
	}

	public EcarData(int mes, int ano){
		anoRef = ano;
		mesRef = mes;
	}

	
	public EcarData(Date data){
		anoRef = data.getYear() + 1900;
		mesRef = data.getMonth() + 1;
	}
	
	public EcarData(GregorianCalendar calendar){
		anoRef = calendar.get(GregorianCalendar.YEAR);
		mesRef = calendar.get(GregorianCalendar.MONTH) + 1;
	}
	
	
	public EcarData(String dataFormatada){
		if(dataFormatada.contains("-")){
			mesRef = Integer.valueOf(dataFormatada.split("-")[0]);
			anoRef = Integer.valueOf(dataFormatada.split("-")[1]);
		}else if(dataFormatada.contains("/")){
			mesRef = Integer.valueOf(dataFormatada.split("/")[0]);
			anoRef = Integer.valueOf(dataFormatada.split("/")[1]);
		}
	}
	
	public int getMes(){
		return mesRef;
	}
	
	public int getAno(){
		return anoRef;
	}


	/**
	 * Retorna a data no forma JAN/09
	 * @return
	 */
	public String getDataFormatada(){
		StringBuffer data = new StringBuffer();
		
		data.append(getAbreviatura());		
		data.append("/");
		
		String ano = Integer.toString(anoRef);
		data.append(ano.substring(2));
		return data.toString();
	}
	
	/**
	 * Retorna a data no formato "Março/11"
	 * @return
	 */
	public String getDataFormatadaMesExtenso(){
		StringBuffer data = new StringBuffer();
		
		data.append(Data.getNomeMesExtenso(mesRef));		
		data.append("/");
		
		String ano = Integer.toString(anoRef);
		data.append(ano.substring(2));
		return data.toString();
		
	}
	
	
	/**
	 * Retorna a data no formato 04/2010
	 * @return
	 */
	public String getDataFormatadaComBarra(){
		if(mesRef < 10){
			return "0" +mesRef + "/" + anoRef;
		}else{
			return mesRef + "/" + anoRef;
		}
			
	}

	/**
	 * Retorna a data no formato "02-2010"
	 * @return
	 */
	public String getDataFormatadaComDash(){
		if(mesRef < 10){
			return "0" +mesRef + "-" + anoRef;
		}else{
			return mesRef + "-" + anoRef;
		}
	}


	public String getAbreviatura(){
		return Data.getAbreviaturaMes(mesRef);
	}
	
	
	
	
	public boolean equals(Object obj){
		
		if(this == obj){
			return true;
		}
		
		if(obj instanceof EcarData){
			if(((EcarData) obj).getAno() == this.anoRef &&
			   ((EcarData) obj).getMes() == this.mesRef){
				return true;
			}
		}
		
		return false;
	}
	
	
	
	/**
	 * Necessário para usar em TreeMaps..
	 * 
	 * @param data
	 * @return
	 */
	public int compareTo(EcarData data) {

	    if ( this == data || this.equals(data) ) return EcarData.IGUAL;
	    
	    if(anoRef == data.getAno() && mesRef == data.getMes()){
	    	return EcarData.IGUAL;
	    }
	    
	    if(anoRef > data.getAno()){
	    	return EcarData.DEPOIS;
	    }
	    
	    if(anoRef < data.getAno()){
	    	return EcarData.ANTES;
	    }
	    
	    if(mesRef < data.mesRef && anoRef == data.getAno()){
	    	return EcarData.ANTES;
	    }
	    
    	return EcarData.DEPOIS; 
	}
	
	
	public int hashCode(){
		GregorianCalendar data = new GregorianCalendar(anoRef, mesRef, 01);
		return (int)data.getTimeInMillis();
	}
	
	public String toString(){
		return getDataFormatada();
	}
	
	
	/**
	 * Verifica se uma determinada data está dentro de um intervalo.
	 * 
	 * @param data
	 * @param inicio
	 * @param fim
	 * @return
	 */
	public static boolean pertenceAoIntervalo(EcarData data, EcarData inicio, EcarData fim){
		if((data.compareTo(inicio) == EcarData.IGUAL ||	data.compareTo(inicio) == EcarData.DEPOIS)   && 
		   (data.compareTo(fim) == EcarData.IGUAL || data.compareTo(fim) == EcarData.ANTES)){
				return true;
		}
		
		return false;
	}
	
	
	
	public static final String DATE_FORMAT_NOW = "dd-MM-yyyy HH:mm:ss";
    /**
     * Retorna a hora e data corrente
     * @return
     */
	public static String now() {
	    Calendar cal = Calendar.getInstance();
	    SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT_NOW);
	    return sdf.format(cal.getTime());
    }
	
}
