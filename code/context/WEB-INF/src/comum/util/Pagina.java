package comum.util;

import java.util.Date;
import java.util.Enumeration;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import ecar.util.Dominios;

/**
 * @author rodrigo.hjort
 */
public class Pagina {
	
    /**
     *
     */
    public static final String SIM = "S";
    /**
     *
     */
    public static final String NAO = "N";

	/**
	 * <b>Exemplo:</b><br>
	 *	int codEnsino = Pagina.getParam(request, "codEnsino");<br>
	 *	int codModalidade = Pagina.getParam(request, "codModalidade");<br>
	 *	int codFormaOrganiz = Pagina.getParam(request, "codFormaOrganizCurso");<b>
	 * 
	 * @author N/C
     * @since N/C
     * @version N/C
         * @param request
         * @param nomeParam
         * @return int 							- valor do inteiro<br>
	 */
	public static final int getParamInt(HttpServletRequest request, String nomeParam) {
		String valor = request.getParameter(nomeParam);
		if (((valor == null) || ("".equals(valor))) && (request.getAttribute(nomeParam) != null))
			valor = request.getAttribute(nomeParam).toString();
		if ((valor != null) && (!"".equals(valor))) {
			try {
				return(Integer.parseInt(valor));
			} catch (Exception e) {
				return(0);
			}
		} else
			return(0);
	}
	
	/**
	 * <b>Exemplo:</b><br>
	 *	int codEnsino = Pagina.getParam(request, "codEnsino");<br>
	 *	int codModalidade = Pagina.getParam(request, "codModalidade");<br>
	 *	int codFormaOrganiz = Pagina.getParam(request, "codFormaOrganizCurso");<br>
	 *
	 * @author N/C
     * @since N/C
     * @version N/C
         * @param request
         * @param nomeParam
	 * @return long							- valor do long
	 */
	public static final long getParamLong(HttpServletRequest request, String nomeParam) {
		String valor = request.getParameter(nomeParam);
		if (((valor == null) || ("".equals(valor))) && (request.getAttribute(nomeParam) != null))
			valor = request.getAttribute(nomeParam).toString();
		if ((valor != null) && (!"".equals(valor))) {
			try {
				return(Long.parseLong(valor));
			} catch (Exception e) {
				return(0);
			}
		} else
			return(0);
	}
	
        
	/**
	 * <b>Exemplo:</b><br>
	 *	int codEnsino = Pagina.getParam(request, "codEnsino");<br>
	 *	int codModalidade = Pagina.getParam(request, "codModalidade");<br>
	 *	int codFormaOrganiz = Pagina.getParam(request, "codFormaOrganizCurso");<br>
	 * 
	 * @author N/C
     * @since N/C
     * @version N/C
         * @param request
         * @param nomeParam
         * @return Short 						- valor do inteiro
	 */
	public static final Short getParamShort(HttpServletRequest request, String nomeParam) {
		String valor = request.getParameter(nomeParam);
		if (((valor == null) || ("".equals(valor))) && (request.getAttribute(nomeParam) != null))
			valor = request.getAttribute(nomeParam).toString();
		if ((valor != null) && (!"".equals(valor))) {
			try {
				return(Short.decode(valor));
			} catch (Exception e) {
				return(Short.decode("0"));
			}
		} else
			return(Short.decode("0"));
	}
        
		
	/**
	 * Obtem dados de um campo especificado. Caso o retorno seja nulo retorna vazio.<br>
	 * 
	 * @author N/C
     * @since N/C
     * @version N/C
         * @param request
         * @param nomeParam
	 * @return String 						- valor do texto
	 */
	public static final String getParamStr(HttpServletRequest request, String nomeParam) {
		String valor = request.getParameter(nomeParam);
		if (((valor == null) || ("".equals(valor))) && (request.getAttribute(nomeParam) != null))
			valor = request.getAttribute(nomeParam).toString();
		if (valor != null) {
			try {
				return(valor.toString().replaceAll("\"","'"));
			} catch (Exception e) {
				return("");
			}
		} else
			return("");
	}
	
	/**
	 * Obtem dados de um campo especificado. Caso o retorno seja nulo retorna vazio.<br>
	 * 
	 * @author N/C
     * @since N/C
     * @version N/C
         * @param request
         * @param nomeParam
	 * @return String 						- valor do texto
	 */
	public static final String[] getParamLista(HttpServletRequest request, String nomeParam) {
		String[] valores = request.getParameterValues(nomeParam);
		if (valores != null) {
			try {
				return(valores);
			} catch (Exception e) {
				return(null);
			}
		} else
			return(null);
	}
	
	/**
	 * Obtem dados de um campo TEXTO especificado. Caso o retorno seja nulo retorna vazio.<br>
	 * 
	 * @author N/C
     * @since N/C
     * @version N/C
         * @param request
         * @param nomeParam 
         * @param arg
         * @return String 						- valor do texto
	 */
	public static final String getParamStr(HttpServletRequest request, String nomeParam, boolean arg) {
		
		String codigo = null;
		
		Enumeration parametros = request.getParameterNames();
		
		while(parametros.hasMoreElements()) {
			String[] p = ((String) parametros.nextElement()).split(":");
			if(p[0].equals(nomeParam.trim())) {
				codigo = p[1];
				break;
			}
		}
		
		String valor = request.getParameter(nomeParam + ":" + codigo);
		
		
		if (((valor == null) || ("".equals(valor))) && (request.getAttribute(nomeParam) != null))
			valor = request.getAttribute(nomeParam).toString();
		if (valor != null) {
			try {
				return(valor.toString().replaceAll("\"","'"));
			} catch (Exception e) {
				return("");
			}
		} else
			return("");
	}
	
	/**
	 * Obtem codigo de atributo de um campo tipo TEXTO especificado. Caso o retorno seja nulo retorna vazio.<br>
	 * 
         * @param request
         * @param nomeParam
         * @author N/C
     * @since N/C
     * @version N/C
	 * @return String 						- valor do texto
	 */
	public static final String getParamStrInputText(HttpServletRequest request, String nomeParam) {
		
		String codigo = null;
		
		Enumeration parametros = request.getParameterNames();
		
		while(parametros.hasMoreElements()) {
			String[] p = ((String) parametros.nextElement()).split(":");
			if(p[0].equals(nomeParam.trim())) {
				codigo = p[1];
				break;
			}
		}
		
		return codigo;
	}	
		
	/**
	 * Retorna a string com os "\" trocados por "'".<br>
	 * Se a string for "" retorna nulo.<br>
	 *  
         * @param request
         * @param param
         * @author N/C
     * @since N/C
     * @version N/C
	 * @return String
	 */
	public static final String getParam(HttpServletRequest request, String param) {
		if (param != null && !"".equalsIgnoreCase(param))
		{		
			String valor = request.getParameter(param);
			if (valor == null || "".equals(valor.trim())) {
					return null;
			} else 
				return valor.replaceAll("\"","'").trim();
		}
		return null;
	}
	
	/**
	 * Retorna a string com os "\" trocados por "'".<br>
	 * Se a string for "" retorna nulo.<br>
	 *  
         * @param request
         * @param param
         * @author N/C
     * @since N/C
     * @version N/C
	 * @return String
	 */
	public static final String[] getParamArray(HttpServletRequest request, String param) {
		return request.getParameterValues(param);
	}
        
	/**
	 * <b>Exemplo:</b>
	 *	int codEnsino = Pagina.getParam(request, "codEnsino");<br>
	 *	int codModalidade = Pagina.getParam(request, "codModalidade");<br>
	 *	int codFormaOrganiz = Pagina.getParam(request, "codFormaOrganizCurso");<br>
	 * 
	 * @author N/C
     * @since N/C
     * @version N/C
         * @param request
         * @param nomeParam
         * @return Date
	 */
	public static final Date getParamDataBanco(HttpServletRequest request, String nomeParam) {
		
		String valor = request.getParameter(nomeParam);//.toString();                
		if ((valor != null) && (!"".equals(valor))) {
                    try {
                            return(new Date(valor.substring(6)+"/"+valor.substring(3,5)+"/"+valor.substring(0,2)));
                    } catch (Exception e) {
                            return(null);
                    }
		} else
                    return(null);
	}
	
	/**
	 * <b>Exemplo:</b><br>
	 *	boolean docCertNasc = Pagina.getParamBool(request, "docCertNasc");<br>
	 *	boolean docHistorico = Pagina.getParamBool(request, "docHistorico");<br>
	 * 
	 * @author N/C
     * @since N/C
     * @version N/C
         * @param request
         * @param nomeParam
	 * @return boolean
	 */
	public static final boolean getParamBool(HttpServletRequest request, String nomeParam) {
		return("true".equals(getParamStr(request, nomeParam)));
	}
	
	/** 
	 * @author Robson Andr� da Costa
	 * @param request 
	 * @param nomeParam 
	 * @param strDefault 
	 * @return 
     * @since 16/10/2007
     */
	public static final String getParamOrDefault(HttpServletRequest request, String nomeParam, String strDefault){
		try{
			if(request == null)
				return strDefault;
			else{
				String valor = request.getParameter(nomeParam);
				if (valor == null || valor.trim() == "")
					return strDefault;
				else
					return valor;
			}
		}catch(NullPointerException e){
			return strDefault;
		}
	}
	
	/**
	 * Se objeto for nulo retorna uma String em branco, do contrario troca todos os "\" da string por "'".<br>
	 * 
         * @param objeto
         * @author N/C
     * @since N/C
     * @version N/C
	 * @return String 			- se nulo retorna uma String em branco
	 */
	public static final String trocaNull(Object objeto){
	    if(objeto == null)
	        return "";
	    else
	        return objeto.toString().trim().replaceAll("\"","'").trim();
	}
	
	/**
	 * Retorna String em formatacao monetaria.<br>
	 * Se objeto for nulo ou "" retorna uma string em branco.<br>
	 * 
	 * @author N/C
     * @since N/C
     * @version N/C
         * @param objeto
	 * @return String 		- se nulo retorna uma String em branco
	 */
	public static final String trocaNullMoeda(Object objeto){
	    if(objeto == null || "".equals(objeto.toString()))
	        return "";
	    else
	        return Util.formataMoeda(Double.valueOf(objeto.toString().trim()).doubleValue());
	}
	
	
	/**
	 * Retorna um n�mero com formata��o das casas decimais (sem unidades de milhar).<br>
	 * Se objeto for nulo ou "", retorna uma string em branco.<br>
	 * Ex.:<br>
	 * "#0.00"
	 * 
	 * @author N/C
     * @since N/C
     * @version N/C
         * @param objeto
	 * @return String 		- se nulo retorna uma String em branco
	 */
	public static final String trocaNullNumeroDecimalSemMilhar(Object objeto){
	    if(objeto == null || "".equals(objeto.toString()))
	        return "";
	    else {
	    	String valor = Util.formataNumeroDecimalSemMilhar(Double.valueOf(objeto.toString().trim()).doubleValue());
	    	return valor.replaceAll("\\.", ",");
	    }
	    	
	}
	
	
	
	/**
	 * Se o item informado for quantidade, retorna um n�mero sem formata��o nenhuma<br>
	 * sen�o se for valor ele retorna o numero formatado com casas decimais.
	 * Se objeto for nulo ou "", retorna uma string em branco.<br>
	 * Ex. qtd:<br>
	 * "#000"
	 * Ex. valor:<br>
	 * "#0.00"
	 * 
         * @param objeto
         * @param indQtd
         * @author luanaoliveira
     * @since N/C
     * @version N/C
	 * @return String 		- se nulo retorna uma String em branco
	 */
	public static final String trocaNullQtdValor(Object objeto, String indQtd){
	    if(objeto == null || "".equals(objeto.toString()))
	        return "";
	    else
	        return Util.formataQtdValor(Double.valueOf(objeto.toString().trim()).doubleValue(), indQtd);
	}
	
	public static final String trocaNullQtdValorPT_BR(Object objeto, String indQtd){
	    if(objeto == null || "".equals(objeto.toString()))
	        return "";
	    else
	        return Util.formataQtdValorPT_BR(Double.valueOf(objeto.toString().trim()).doubleValue(), indQtd);
	}
	
	/**
	 * Formata o numero com casas decimais.<br>
	 * Se objeto for nulo ou "" retorna uma string em branco.<br>
	 * ex.:<br>
	 * "###,###,##0.##"
	 *  
	 * @author N/C
     * @since N/C
     * @version N/C
         * @param objeto
	 * @return String 		- se nulo retorna uma String em branco
	 */
	public static final String trocaNullNumeroDecimal(Object objeto){
	    if(objeto == null || "".equals(objeto.toString()))
	        return "";
	    else
	        return Util.formataNumeroDecimal(Double.valueOf(objeto.toString().trim()).doubleValue());
	}
	
	public static final String trocaNullNumeroDecimalPT_BR(Object objeto){
	    if(objeto == null || "".equals(objeto.toString()))
	        return "";
	    else
	        return Util.formataDecimalPT_BR(Double.valueOf(objeto.toString().trim()).doubleValue());
	}

	/**
	 * Retorna numero sem casas decimais.<br>
	 * Se objeto for nulo ou "" retorna string em branco.<br>
	 * Ex.:<br>
	 * "###,###,##0"
	 * 
	 * @author N/C
     * @since N/C
     * @version N/C
         * @param objeto
	 * @return String 		- se nulo retorna uma String em branco
	 */
	public static final String trocaNullNumeroSemDecimal(Object objeto){
	    if(objeto == null || "".equals(objeto.toString()))
	        return "";
	    else
	        return Util.formataNumeroSemDecimal(Double.valueOf(objeto.toString().trim()).doubleValue());
	}

	public static final String trocaNullComDecimal(Object objeto){
	    if(objeto == null || "".equals(objeto.toString()))
	        return "";
	    else
	        return Util.formataDecimalPT_BR(Double.valueOf(objeto.toString().trim()).doubleValue());
	}
	
	
	/**
	 * Converte um Date para uma string.
	 * Se objeto for nulo retorna uma string em branco.<br>
	 * 
         * @param data
         * @author N/C
     * @since N/C
     * @version N/C
	 * @return String 	- se nulo retorna uma String em branco
	 */
	public static final String trocaNullData(Date data){
	    if(data == null)
	        return "";
	    else
	    	return Data.parseDate(data);
	}
	
	/**
	 * Converte um Date para uma string.
	 * Se objeto for nulo retorna uma string em branco.<br>
	 * 
         * @param data
         * @author N/C
     * @since N/C
     * @version N/C
	 * @return String 	- se nulo retorna uma String em branco
	 */
	public static final String trocaNullDataHora(Date data){
	    if(data == null)
	        return "";
	    else
	    	return Data.parseDateHourMinuteSecond(data);
	}
	/**
	 * <b>Exemplo:</b><br>
	 *	Utilizado para radiobutton comparando o valor do objeto a uma determinada String:<br>
	 *  Ativo Sim ou N�o<br>
	 *  "S" ou "N" == getIndAtivo()<br>
	 * 
	 * @author N/C
     * @since N/C
     * @version N/C
         * @param objeto
         * @param str
	 * @return String 		- checked - se valores iguais
	 */
	public static final String isChecked(Object objeto, String str){
		String chk;
		chk = "";
		if (objeto != null && str.equalsIgnoreCase(objeto.toString()))
			chk = "checked";
		
		return chk;
	}
	
	public static final String isChecked(Long l, String str){
		if (l != null && Long.valueOf(str).equals(l))
			return "checked";		
		return "";
	}
	
        /**
         *
         * @param paramObj
         * @param param
         * @return
         */
        public static final String isChecked(Boolean paramObj, boolean param){

		String chk = "";
		
		if (paramObj != null && paramObj.booleanValue() ==  param) {
			chk = "checked";
		}
		
		return chk;
	}
	

	/**
	 * <b>Exemplo:</b><br>
	 *	Utilizado para checkbox comparando o valor do objeto a uma determinada Lista de Valores:<br>
	 *  Ativo Sim ou N�o<br>
	 *   
	 * @author N/C
     * @since N/C
     * @version N/C
         * @param objeto
         * @param listValores
	 * @return String 		- checked - se valores iguais
	 */

	public static final String isChecked(Object objeto, List listValores){
		String chk;
		chk = "";
		if (listValores==null||listValores.size()==0 )
			return chk;
		else if (listValores.contains(objeto))
			chk = "checked";
		return chk;
	}

	
	
	/**
	 * <b>Exemplo:</b><br>
	 *	Utilizado para select  comparando o valor do objeto a uma determinada String:<br>

	 * 
	 * @author N/C
     * @since N/C
     * @version N/C
         * @param objeto
         * @param str
	 * @return String 		- select - se valores iguais
	 */
	public static final String isSelected (Object objeto, String str){
		String select;
		select = "";
		if (objeto != null && str.equalsIgnoreCase(objeto.toString()))
			select = "selected";
		
		return select;
	}
	
	/**
	 * <b>Exemplo:</b><br>
	 *	Utilizado para checkbox comparando o valor do objeto a uma determinada String:<br>
	 *  Ativo Sim ou N�o<br>
	 *  "S" ou "N" == getIndAtivo()<br>
	 * 
         * @param objeto
         * @param str
         * @author N/C
     * @since N/C
     * @version N/C
	 * @return String 		- value e checked(se valores iguais)
	 */
	public static final String isBoxChecked(Object objeto, String str){
		String chk;
		chk = "";
		if (objeto != null) {
			chk += " value=\"" + objeto.toString() + "\"";
			if(str.equalsIgnoreCase(objeto.toString()))
				chk += " checked=\"checked\" ";
		}		
		return chk;
	}
	
	/**
	 * Metodo para retornar valor < 10 com Zero � Esquerda, utilizado para mostrar horas e minutos<br>
	 * Ex.: 07 <br>
	 * 
	 * @author N/C
     * @since N/C
     * @version N/C
         * @param valor
	 * @return String
	 */
	public static final String getZeroAEsquerda(String valor){
		if (!"".equals(valor)){
			int valorInt = (Integer.valueOf (valor)).intValue();
		
			if (valorInt < 10)
				valor = "0" + valor;
		}
		
		return valor;
	}
	
	
	/**
	 * Retorna o conteudo do parametro, se o conteudo for igual a branco retorna null. 
	 * @param request
	 * @param nomeParam
	 * @return
	 */
	public static String getParamStrOrNull (HttpServletRequest request, String nomeParam){
		
		String retorno = null;
		
		retorno = request.getParameter(nomeParam);
	
		if (retorno != null && retorno.equals(Dominios.STRING_VAZIA)){
			retorno = null;
		}
		
		return retorno;
	}
	
	/**
	 * Troca os valores do formato
	 * @param request
	 * @param nomeParam
	 * @return
	 */
	public static final String trocaFormato(Object objeto){
	    if(objeto == null)
	        return "";
	    else{
	        String formato = objeto.toString().trim().replaceAll("\"","'");
	        if("Q".equals(formato)){
	        	formato = "N�mero";
	        } else if("V".equals(formato)){
	        	formato = "Moeda";
	        }
	    	return formato;
	    }
	}
	
	
}