/*
 * Created on 05/10/2004
 *
 */
package comum.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.Normalizer;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Properties;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import com.thoughtworks.xstream.core.util.Base64Encoder;

import ecar.dao.ConfiguracaoDao;
import ecar.dao.CorDao;
import ecar.dao.EmailDao;
import ecar.exception.ECARException;
import ecar.pojo.ConfiguracaoCfg;
import ecar.pojo.Cor;
import ecar.pojo.Email;
import ecar.pojo.ItemEstruturaIett;
import ecar.pojo.SisAtributoSatb;
import ecar.pojo.TipoFuncAcompTpfa;
import ecar.pojo.UsuarioUsu;
import ecar.util.Dominios;


/**
 * @author garten
 */
public class Util {

	@SuppressWarnings("unchecked")
	public static boolean contemSisAtributo(ItemEstruturaIett iett, SisAtributoSatb atrib) {
		Set<SisAtributoSatb> listNiveis = iett.getItemEstruturaNivelIettns();
		if(listNiveis != null && listNiveis.size() > 0) {
            Iterator<SisAtributoSatb> itNiveis = listNiveis.iterator();
            while(itNiveis.hasNext()) {
                SisAtributoSatb nivel = (SisAtributoSatb) itNiveis.next();
                
                if(listNiveis.contains(nivel)) {
                	return true;
                }
                
            }
    	}
		return false;
	}
	
	/**
	 * Lista os m�todos get de um objeto passado como par�metro.<br>
	 * 
         * @param o
         * @author N/C
	 * @since N/C
	 * @version N/C
	 * @return List - lista de m�todos da classe Method 
	 */
	public static List listaMetodosGet(Object o) {
		return listaMetodos(o, Dominios.REGEXP_METODOS_GET);
    }
        
	/**
	 * Lista os m�todos set de um objeto passado como par�metro.<br>
	 * 
         * @param o
         * @author N/C
	 * @since N/C
	 * @version N/C
	 * @return List - lista de m�todos da classe Method 
	 */
	public static List listaMetodosSet(Object o) {
		return listaMetodos(o, Dominios.REGEXP_METODOS_SET);
	}

	/**
	 * Lista todos os m�todos declarados de um objeto passado como par�metro.<br>
	 * 
         * @param o
         * @author N/C
	 * @since N/C
	 * @version N/C
	 * @return List - lista de m�todos da classe Method 
	 */
	public static List listaMetodos(Object o) {
		return listaMetodos(o, Dominios.REGEXP_TODOS);
	}
	
	
	/**
	 * Devolve uma lista de m�todos declarados pelo programador em um objeto.<br>
	 * 
	 * @author N/C
	 * @since N/C
	 * @version N/C
         * @param o
         * @param pattern
	 * @return List - lista de m�todos da classe Method<br>
	 * <p>
	 * Exemplo de utiliza��o:<br>
	 * List l = new ArrayList();<br>
	 * l = Util.listaMetodos(new Object(), ".*");  // todos os metodos da classe Object<br>
	 * for (int i = 0; i < l.size(); i++)<br>
	 * 		System.out.println(((Method)l.get(i)).getName());<br>
	 */
	public static List listaMetodos(Object o, String pattern) {
		List l = new ArrayList();
    		
		try {
			Method[] m = o.getClass().getDeclaredMethods();
    		    
			for (int i = 0; i < m.length; i++)
				if (m[i].getName().matches(pattern))
					l.add(m[i]);
    		    
		} catch (SecurityException s) {
			
		}
    	
		return l;
	}
	
	public static List listaTodosMetodosGet(Object o) {
		return listaTodosMetodos(o, Dominios.REGEXP_METODOS_GET);
    }
	
	public static List listaTodosMetodos(Object o, String pattern) {
		List l = new ArrayList();
    		
		try {
			Method[] m = o.getClass().getMethods();
    		    
			for (int i = 0; i < m.length; i++)
				if (m[i].getName().matches(pattern))
					l.add(m[i]);
    		    
		} catch (SecurityException s) {
			
		}
    	
		return l;
	}

	/**
	 * Lista os todos os atributos declarados de um objeto passado como par�metro.<br>
	 * 
         * @param o
         * @author N/C
	 * @since N/C
	 * @version N/C
	 * @return List - lista de atributos da classe Method 
	 */
	
	public static List listaAtributos(Object o) {
		return listaAtributos(o, Dominios.REGEXP_TODOS);
	}
	
	
	/**
	 * Devolve uma lista de atributos declarados pelo programador em um objeto.<br>
	 * 
	 * @author N/C
	 * @since N/C
	 * @version N/C
         * @param o
         * @param pattern
	 * @return List - lista de atributos da classe Field<br>
	 * <p>
	 * Exemplo de utiliza��o:<br>
	 * List l = new ArrayList();<br>
	 * l = Util.listaAtributos(new Object(), ".*");  // todos os atributos da classe Object<br>
	 * for (int i = 0; i < l.size(); i++)<br>
	 * 		System.out.println(((Field)l.get(i)).getName());<br>
	 */
	public static List listaAtributos(Object o, String pattern) {
		List l = new ArrayList();
    		
		try {
			Field[] f = o.getClass().getDeclaredFields();
    		    
			for (int i = 0; i < f.length; i++)
				if (f[i].getName().matches(pattern))
					l.add(f[i]);
    		    
		} catch (SecurityException s) {
			
		}
    	
		return l;
	}
	
	/**
	 * Invoca um metodo getXxxxx() para o objeto e o atributo informado.<br>
	 * 
	 * @author N/C
	 * @since N/C
	 * @version N/C
         * @param o
         * @param atributo
         * @param objParams
         * @return Object
	 * @throws ECARException
	 */
	public static Object invocaGet(Object o, String atributo, Object[] objParams) throws ECARException {
	    String nomeGetter = "get" + primeiraLetraToUpperCase(atributo);
        try {
            /* supoe que existe apenas um unico metodo com esse nome, portanto passa null no array
             * de classes do getMethod
             */
            return o.getClass().getMethod(nomeGetter, null).invoke(o, objParams);
        } catch (IllegalArgumentException e) {
            throw new ECARException("erro.exception");
        } catch (SecurityException e) {
           throw new ECARException("erro.exception");
        } catch (IllegalAccessException e) {
            throw new ECARException("erro.exception");
        } catch (InvocationTargetException e) {
            throw new ECARException("erro.exception");
        } catch (NoSuchMethodException e) {
            throw new ECARException("erro.exception");
        }
	}

	/**
	 * InvocaGet Simplificado, n�o recebe os par�metros de invoca��o de um objeto.<br>
	 * 
	 * @author N/C
	 * @since N/C
	 * @version N/C
         * @param o
         * @param atributo
         * @return Object
	 * @throws ECARException
	 */
	public static Object invocaGet(Object o, String atributo) throws ECARException {
	    return invocaGet(o, atributo, null);
	}

	/**
	 * Invoca o metodo setXxxx para o objeto e atributo informados.<br>
	 * 
         * @param o
         * @param atributo
         * @param objParams
         * @author N/C
	 * @since N/C
	 * @version N/C
	 * @return Object
	 * @throws ECARException
	 */
	public static Object invocaSet(Object o, String atributo, Object[] objParams) throws ECARException {
	    String nomeSetter = "set" + primeiraLetraToUpperCase(atributo);
        try {
            /* supoe que existe apenas um unico metodo com esse nome, portanto passa null no array
             * de classes do getMethod
             */
            return o.getClass().getMethod(nomeSetter, null).invoke(o, objParams);
        } catch (IllegalArgumentException e) {
            throw new ECARException("erro.exception");
        } catch (SecurityException e) {
            throw new ECARException("erro.exception");
        } catch (IllegalAccessException e) {
            throw new ECARException("erro.exception");
        } catch (InvocationTargetException e) {
           throw new ECARException("erro.exception");
        } catch (NoSuchMethodException e) {
            throw new ECARException("erro.exception");
        }
	}
		
	/**
	 * Retorna a String informada como par�metro modificando sua primeira letra pra letra mai�scula.<br>
	 * 
         * @param string
         * @author N/C
	 * @since N/C
	 * @version N/C
	 * @return String - a string modificada
	 */
	public static String primeiraLetraToUpperCase(String string){
	    return string.substring(0,1).toUpperCase().concat(string.substring(1));
	}

	/**
	 * Retorna a String informada como par�metro com a primeira letra em mai�scula e o restante min�scula.<br>
	 * 
	 * @author aleixo
	 * @since 12/07/2007
	 * @param string
	 * @return String
	 */
	public static String soPrimeiraLetraToUpperCase(String string){
		return primeiraLetraToUpperCase(string.toLowerCase());
	}
	
	/**
	 * Troca todas as primeiras letras de uma string para ma�scula.<br>
	 * Exemplos:<br>
	 * String - Retorno<br>
	 * "TEXTO DE TESTE" - Texto de Teste<br>
	 * "texto de teste" - Texto de Teste<br>
	 * 
	 * @author aleixo
	 * @since 17/07/2007
	 * @param string
	 * @return String
	 */
    public static String todasPrimeirasLetrasToUpperCase(String string){
    	if(string == null || (string != null && "".equals(string.trim()))){
    		return "";
    	}
    	
    	string = string.trim().toLowerCase().replaceAll("\"", "");
	    	
    	StringBuilder stringModificada = new StringBuilder();
    	String[] temp = string.split(" ");
    	
    	for(int i = 0; i < temp.length; i++){
    		String aux = temp[i].trim();
    		if("".equals(aux))
    			continue;
    		
    		if(!"de".equals(temp[i]) &&
    		   !"da".equals(temp[i]) &&		
    		   !"das".equals(temp[i]) &&		
    		   !"do".equals(temp[i]) &&		
    		   !"dos".equals(temp[i]) &&		
    		   !"em".equals(temp[i]) &&		
    		   !"no".equals(temp[i]) &&		
    		   !"na".equals(temp[i]) && 		
    		   !"nos".equals(temp[i]) &&
    		   !"nas".equals(temp[i]) &&		
    		   !"a".equals(temp[i]) &&		
    		   !"e".equals(temp[i]) &&		
    		   !"i".equals(temp[i]) &&		
    		   !"o".equals(temp[i]) &&		
    		   !"u".equals(temp[i]) &&		
    		   !"com".equals(temp[i]) &&		
    		   !"sem".equals(temp[i])		
    		){
    			aux = primeiraLetraToUpperCase(aux);
    		}
    		
    		stringModificada.append(aux).append(" ");
    	}
    	return stringModificada.toString();
    }

    /**
     * Remove os espa�os que est�o sobrando em uma string.<br>
     * 
     * @author aleixo
     * @since 30/08/2007
     * @param string
     * @return
     */
    public static String removeEspacosDuplicados(String string){
    	if(string == null || (string != null && "".equals(string.trim()))){
    		return "";
    	}
    	
    	StringBuilder stringModificada = new StringBuilder();
    	String[] temp = string.split(" ");
    	
    	for(int i = 0; i < temp.length; i++){
    		String aux = temp[i].trim();
    		if("".equals(aux))
    			continue;
    		
    		stringModificada.append(aux).append(" ");
    	}
    	
    	return stringModificada.toString().trim();
    }
	
	/**
	 * Retorna se um determinado valorRefer�ncia est� contido no intervalo entre
	 * outros dois valores (valor1 e valor2).<br>
	 * 
	 * @author N/C
	 * @since N/C
	 * @version N/C
         * @param valorReferencia
         * @param valor1
         * @param valor2
	 * @return boolean
	 */

	public static boolean entre(int valorReferencia, int valor1, int valor2) 
	{
		try{
			if ((valor1 > valor2) 
			  && (valorReferencia < valor1)	 
			  && (valorReferencia > valor2))
				return true;
			if ((valor2 > valor1)
			 && (valorReferencia < valor2)	 
			 && (valorReferencia > valor1))
				return true;		
			return false;
			}catch (NumberFormatException e){
				return false;
		}
		
	}
	
	/**
	 * Retorna se um determinado valorRefer�ncia est� contido no intervalo entre
	 * outros dois valores (valor1 e valor2).<br>
	 * Foi implementado para receber strings, pois existe a compara��o com "inf" e "sup"
	 * que indicam se um valor limite s�o todos os menores que o outro, ou todos os
	 * maiores, respectivamente.<br>
	 * 
	 * @author N/C
	 * @since N/C
	 * @version N/C
         * @param valorReferencia
         * @param valor1 
         * @param valor2
         * @return boolean
	 */

	public static boolean entre(String valorReferencia, String valor1, String valor2) 
	{
		try{
			return (entre(Integer.parseInt(valorReferencia), valor1, valor2));
		}catch (NumberFormatException e){
			return false;
		}
		
	}
	
	/**
	 * Retorna se um determinado valorRefer�ncia est� contido no intervalo entre
	 * outros dois valores (valor1 e valor2).<br>
	 * Foi implementado para receber strings, pois existe a compara��o com "inf" e "sup"
	 * que indicam se um valor limite s�o todos os menores que o outro, ou todos os
	 * maiores, respectivamente.<br>
	 * 
	 * @author N/C
	 * @since N/C
	 * @version N/C
         * @param valorReferencia
         * @param valor2
         * @param valor1
         * @return boolean
	 */

	public static boolean entre(int valorReferencia, String valor1, String valor2)
	{
		try{
			if (("sup".equals(valor1) && "inf".equals(valor2))
			 || ("sup".equals(valor2) && "inf".equals(valor1)))
				 	return true;			
			if ("sup".equals(valor1))
				if (valorReferencia > Integer.parseInt(valor2))
					return true;
			if ("inf".equals(valor1))
				if (valorReferencia < Integer.parseInt(valor2))
					return true;
			if ("sup".equals(valor2))
				if (valorReferencia > Integer.parseInt(valor1))
					return true;
			if ("inf".equals(valor2))
				if (valorReferencia < Integer.parseInt(valor1))
					return true;
			if ((Integer.parseInt(valor1) > Integer.parseInt(valor2))
			 && (valorReferencia < Integer.parseInt(valor1))	 
			 && (valorReferencia > Integer.parseInt(valor2)))
				return true;
			if ((Integer.parseInt(valor2) > Integer.parseInt(valor1))
			 && (valorReferencia < Integer.parseInt(valor2))	 
			 && (valorReferencia > Integer.parseInt(valor1)))
				return true;
			return false;
		} catch(NumberFormatException e) {
			return false;
		}
	}
	
	
	/**
	 * Retorna uma substring  da posicao inicio at� a posicao fim ou at� o comprimento maximo da string.<br>
	 * 
	 * @author N/C
	 * @since N/C
	 * @version N/C
         * @param string
         * @param inicio
         * @param fim
         * @return String
	 */
	public static String substring(String string, int inicio, int fim) {
	    return string.substring(inicio, Math.min(fim, string.length()));
	}
	
	/**
	 * Retorna um n�mero com formata��o Monet�ria.<br>
	 * 
	 * @author N/C
	 * @since N/C
	 * @version N/C
         * @param number
	 * @return String
	 */
	public static String formataMoeda(double number){
	    NumberFormat formatter = new DecimalFormat("###,###,##0.00");	    
	    //NumberFormat fn = NumberFormat.getInstance(new Locale("pt", "BR"));	    
	    return formatter.format(number);
	}
	
	public static String formataMoedaPT_BR(double number){
	    return formataDecimalPT_BR(number);
	}
	
	/**
	 * Retorna um n�mero com formata��o de unidades de milhar e casas decimais.<br>
	 * 
	 * @author N/C
	 * @since N/C
	 * @version N/C
         * @param number
	 * @return String
	 */
	public static String formataNumeroDecimal(double number){
	    NumberFormat formatter = new DecimalFormat("#,###,##0.00");	    
	    //NumberFormat fn = NumberFormat.getInstance(new Locale("pt", "BR"));	    
	    return formatter.format(number);
	}

        /**
         *
         * @param number
         * @return
         */
        public static String formataNumeroDecimal(Double number){
	    return formataNumeroDecimal((double)number);
	}
	
	/**
	 * Retorna um n�mero com formata��o de unidades de milhar e 
	 * casas decimais mas sem pontos e v�rgulas, para valores de exporta��o.<br>
	 * 
	 * @author N/C
	 * @since N/C
	 * @version N/C
         * @param number
         * @param tamanho
	 * @return String
	 */
	public static String formataNumeroDecimalParaExportacao(double number, int tamanho){
		String temp = formataMoeda(number);
		String retorno = "";
		for(int i = 0; i < temp.length(); i++){
			if(temp.charAt(i) != '.' && temp.charAt(i) != ','){
				retorno += temp.charAt(i);
			}
		}
		int tam = retorno.length();
		for(int i = tam; i < tamanho; i++){
			retorno = " " + retorno;
		}
		return retorno;
	}
	
	/**
	 * Retorna um n�mero com formata��o sem casas decimais.<br>
	 * 
         * @param number
         * @author N/C
	 * @since N/C
	 * @version N/C
	 * @return String
	 */
	public static String formataNumeroSemDecimal(double number){
	    NumberFormat formatter = new DecimalFormat("###,###,##0");	    
	    //NumberFormat fn = NumberFormat.getInstance(new Locale("pt", "BR"));	    
	    return formatter.format(number);
	}
	
	public static String formataDecimalPT_BR(double number) {
		DecimalFormat nFormat = new DecimalFormat ("#,###,##0.00", new DecimalFormatSymbols (new Locale ("pt", "BR")));  
		nFormat.setMaximumFractionDigits(3);
		
		return nFormat.format(number);
	}
	
	public static Double parseDecimalPT_BR(String number) {
		DecimalFormat nFormat = new DecimalFormat ("#,##0.00", new DecimalFormatSymbols (new Locale ("pt", "BR")));  
		nFormat.setMaximumFractionDigits(3);
		
		try {
			return nFormat.parse(number).doubleValue();
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public static String formataDecimalPT_BR(String number) {
		DecimalFormat nFormat = new DecimalFormat ("#,##0.00", new DecimalFormatSymbols (new Locale ("pt", "BR")));  
		nFormat.setMaximumFractionDigits(3);
		
		return nFormat.format(number);
	}
	
	public static String formatarDecimal(Double numero){
		String numeroFormatado = "";
		if(numero != null){
			NumberFormat format = NumberFormat.getNumberInstance(new Locale("pt", "BR"));
			format.setMaximumFractionDigits(2);
			numeroFormatado = format.format(numero).replace(",00", "");
		}
		
		return numeroFormatado;
	}

	/**
	 * Retorna um n�mero com formata��o das casas decimais (sem unidades de milhar).<br>
	 * 
         * @param number
         * @author N/C
	 * @since N/C
	 * @version N/C
	 * @return String
	 */
	public static String formataNumeroDecimalSemMilhar(double number){
	    NumberFormat formatter = new DecimalFormat("#0.00");
	    return formatter.format(number);
	}
	
	/**
	 * Retorna um n�mero com formata��o sem casas decimais <br>
	 * 
	 * @author luanaoliveira
	 * @since N/C
	 * @version N/C
         * @param number
	 * @return String
	 */
	public static String formataNumeroInteiroSemMilhar(double number){
	    NumberFormat formatter = new DecimalFormat("#0");	    
	    //NumberFormat fn = NumberFormat.getInstance(new Locale("pt", "BR"));	    
	    return formatter.format(number);
	}
	
	/**
	 * formata o numero de acordo com o indQtd<br>
	 * se for qtd: #000
	 * se for valor: #0.00
	 * 
	 * @author luanaoliveira
	 * @param number
	 * @param indQtd
	 * @return String com o numero formatado
	 */
	public static String formataQtdValor(double number, String indQtd){
		if(Dominios.IETTR_QUANTIDADE.equals(indQtd)){
			return(formataNumeroDecimal(number));
		}else{
			return formataMoeda(number);
		}
	}
	
	public static String formataQtdValorPT_BR(double number, String indQtd){
		if(Dominios.IETTR_QUANTIDADE.equals(indQtd)){
			return(formataDecimalPT_BR(number));
		}else{
			return formataMoedaPT_BR(number);
		}
	}
	
    /**
     * Fun��o que retorna um n�mero formatado com '.' para separa��o decimal e sem v�rgulas.<br>
     * 
     * @param original
     * @author N/C
	 * @since N/C
	 * @version N/C
     * @return String
     */
    public static String formataNumero(String original){       
        original = original.replaceAll("\\.","");
        original = original.replaceAll(",",".");
        return original;        
    }
    
    /**
     * Retorna formatado a String do tamanho do arquivo em KB ou MB.<br>  
     * 
     * @param bytes
     * @author N/C
	 * @since N/C
	 * @version N/C
     * @return String
     */
    public static String formataByte(Long bytes){
        double kb = converteParaKb(bytes.doubleValue());
        if(kb > 1000){
            return formataNumeroDecimal(converteParaMb(kb)) + " MB";
        } else
            return formataNumeroDecimal(kb) + " KB";
    }
    
    /**
     * Converte para KB.<br>
     * 
     * @author N/C
	 * @since N/C
	 * @version N/C
     * @param bytes
     * @return double
     */
    public static double converteParaKb(double bytes){
        return bytes / new Double(1024).doubleValue();
    }

    /**
     * Converte para MB.<br>
     * 
     * @author N/C
	 * @since N/C
	 * @version N/C
     * @param kBytes
     * @return double
     */
    public static double converteParaMb(double kBytes){
        return kBytes / new Double(1024).doubleValue();
    }
    
    
    /**
     * Retorna uma cole��o com os elementos de col1 que est�o em col2
     * (col1 e col2 devem ser listas de objetos da mesma classe).<br>
     * 
     * @author N/C
	 * @since N/C
	 * @version N/C
     * @param col1
     * @param col2
     * @return Collection
     */
    public static Collection intersecao(Collection col1, Collection col2){
        Collection a = new ArrayList(col1);
        Collection b = new ArrayList(col2);
        a.retainAll(b);
        return a;
    }
    
    /**
     * Retorna uma cole��o com os elementos de col1 que n�o est�o em col2 ( col1 - col2)
     * (col1 e col2 devem ser listas de objetos da mesma classe).<br>
     * 
     * @author N/C
	 * @since N/C
	 * @version N/C
     * @param col1 
     * @param col2
     * @return Collection
     */
    public static Collection diferenca(Collection col1, Collection col2){
        Collection a = new ArrayList(col1);
        Collection b = new ArrayList(col2);
        a.removeAll(b);
        return a;
    } 
	
    /**
     * Retira acentuacao.<br>
     * 
     * @author N/C
	 * @since N/C
	 * @version N/C
     * @param string
     * @return String
     */
//	public static String retiraAcentuacao(String string){
//		return string.replace('á','a').
//				  replace('à','a').
//				  replace('ã','a').
//				  replace('é','e').
//				  replace('ê','e').
//				  replace('í','i').
//				  replace('ó','o').
//				  replace('õ','o').
//				  replace('ú','u').
//				  replace('ũ','u');
//	}
	
	/**
	 * Retira acentuacao.<br>
	 * 
	 * @author N/C
	 * @since N/C
	 * @version N/C
	 * @param string
	 * @return String
	 */
	public static String retiraAcentuacao(String string) {
//		String acentuado = "çÇáéíóúýÁÉÍÓÚÝàèìòùÀÈÌÒÙãõñäëïöüÿÄËÏÖÜÃÕÑâêîôûÂÊÎÔÛ";
//		String semAcento = "cCaeiouyAEIOUYaeiouAEIOUaonaeiouyAEIOUAONaeiouAEIOU";
//		char[] tabela = new char[256];
//		for (int i = 0; i < tabela.length; ++i) {
//			tabela[i] = (char) i;
//		}
//		for (int i = 0; i < acentuado.length(); ++i) {
//			tabela[acentuado.charAt(i)] = semAcento.charAt(i);
//		}
//		StringBuffer sb = new StringBuffer();
//		for (int i = 0; i < string.length(); ++i) {
//			char ch = string.charAt(i);
//			if (ch < 256) {
//				sb.append(tabela[ch]);
//			} else {
//				sb.append(ch);
//			}
//		}
//		return sb.toString();
		string = Normalizer.normalize(string, Normalizer.Form.NFD);
		string = string.replaceAll("[^\\p{ASCII}]", "");
		return string;
	}
	
	/**
	 * Substitui espacos numa string por um caracter especificado.<br>
	 * 
         * @param string
         * @param caracter
         * @author N/C
	 * @since N/C
	 * @version N/C
	 * @return String
	 */
	public static String trocarEspacoPorCaracter(String string, String caracter){
		return string.replaceAll(" ", caracter);
	}
	
	/**
	 * M�todo para substituir na vari�vel uma barra "\" e colocar "\\"<br>
	 * 		Utilizado para passar para JavaScript<br>
	 * Funcionamento: o java interpreta uma string "\\\\" como sendo "\",<br>
	 * 			por isso a necessidade de tantas barras.<br>
	 * 
         * @param texto
         * @author N/C
	 * @since N/C
	 * @version N/C
	 * @return String
	 */
	public static String trocaBarraParaDuasBarras(String texto){
		return texto.replaceAll("\\\\", "\\\\\\\\");
	}
	
	/**
	 * M�todo para substituir na vari�vel uma barra "\" e colocar "\\"<br>
	 * 		Utilizado para passar para JavaScript<br>
	 * Funcionamento: o java interpreta uma string "\\\\" como sendo "\",<br>
	 * 			por isso a necessidade de tantas barras.<br>
	 * Caso a String passada por par�metro seja nula, retorna a String ""
	 * 
         * @param texto
         * @author N/C
	 * @since N/C
	 * @version N/C
	 * @return String
	 */
	public static String trocaBarra(String texto){
		if (texto == null)
			return "";
		
		if (texto.contains("\\")){
			texto = texto.replace("\\", "\\\\");
		}
		return texto;
	}
	
	/**
	 * Calcula a m�dia de valores Double contidos em uma lista.<br>
	 * 
         * @param valores
         * @author N/C
	 * @since N/C
	 * @version N/C
	 * @return double
	 */
	public static double calculaMediaValores(Collection valores){
		Iterator it = valores.iterator();
		double total = 0;
		while(it.hasNext()){
			Double valor = (Double) it.next();
			total += valor.doubleValue();
		}
		return total / valores.size();
	}

	/**
	 * Calcula a m�dia de valores Integer contidos em uma lista.<br>
	 * 
	 * @author N/C
	 * @since N/C
	 * @version N/C
         * @param valores
	 * @return double
	 */

	public static double calculaMediaValoresInteger(Collection valores){
		Iterator it = valores.iterator();
		double total = 0;
		while(it.hasNext()){
			Integer valor = (Integer) it.next();
			total += valor.intValue();
		}
		return total / valores.size();
	}
    
	/**
	 * ????????
	 * 
	 * @author N/C
	 * @since N/C 
	 * @version N/C
         * @param palavra
         * @param original
         * @param novo
	 * @return String
	 */
	public static String trocar(String palavra, String original, String novo){
		String retorno = "";
		for(int i = 0; i< palavra.length(); i++){
			if(palavra.charAt(i) == original.charAt(0)){
				retorno += novo;
			} else {
				retorno += palavra.charAt(i);
			}
		}
		return retorno;
			
	}
    
	/**
	* Enviar um e-mail no formato texto ou html.<br>
	* 
	* @author Cristiano
	* @since 16/09/05
	* @version N/C
         * @param assunto
         * @param nomeRemetente
         * @param remetente
         * @param texto
         * @param destinatarioBcc
         * @param destinatarioCc 
         * @param usuario
         * @param destinatarioPara
         * @throws AddressException - Endere�o de e-mail inv�lido
	* @throws MessagingException - N�o foi poss�vel enviar e-mail
         * @throws Exception
         * @throws ECARException
	*/
	
	public static void enviarEmail(String assunto, String nomeRemetente, String remetente,
		String texto, String destinatarioPara, String destinatarioCc, String destinatarioBcc, UsuarioUsu usuario)
		throws AddressException, MessagingException, Exception, ECARException {
			ConfiguracaoCfg config = new ConfiguracaoDao(null).getConfiguracao();
    		if(config.getEmailServer() == null || "".equals(config.getEmailServer().trim())) {
    			throw new ECARException("erro.servidor.email.invalido");
    		}
    		
            Properties mailProps = new Properties();
            mailProps.put("mail.smtp.host", config.getEmailServer());

            Session mailSession = Session.getInstance(mailProps, null);
            mailSession.setDebug(false);
            Message email = new MimeMessage(mailSession);
            email.setRecipients( Message.RecipientType.TO, InternetAddress.parse(destinatarioPara));
            if (destinatarioCc != null && !"".equals(destinatarioCc.trim())) {
                email.setRecipients( Message.RecipientType.CC, InternetAddress.parse(destinatarioCc));
            }
            if (destinatarioBcc != null && !"".equals(destinatarioBcc.trim())) {
                email.setRecipients( Message.RecipientType.BCC, InternetAddress.parse(destinatarioBcc));
            }
            InternetAddress iAdd = new InternetAddress();
         
            if (remetente != null) iAdd.setAddress(remetente);
            if (nomeRemetente != null) iAdd.setPersonal(nomeRemetente);
            
            email.setFrom(iAdd);
            email.setSubject(assunto);
            email.setContent(texto, "text/html; charset=" + Dominios.ENCODING_DEFAULT);
            
            Transport.send(email);
            
            if (usuario != null){
				Email mailUsu = new Email(nomeRemetente, new Date(), assunto, destinatarioPara, destinatarioCc, destinatarioBcc, texto,"", usuario);
				EmailDao emailDao = new EmailDao();
				emailDao.salvar(mailUsu);
            }
    }
	
	/**
	 * Normaliza Chars:<br>
	 * Ex.:<br>
	 * de '<' para "&lt;"<br>
	 * 
	 * 
	 * @author N/C
	 * @since N/C
	 * @version N/C
         * @param s
         * @param str
         * @param i
	 */
	public static void normalizeChars(String s, StringBuffer str, int i) {
		final char ch = s.charAt(i);
		switch (ch) {
			case 60: // '<'
				str.append("&lt;"); 
				break;
			case 62: // '>'
				str.append("&gt;"); 
				break;
			case 38: // '&'
				str.append("&amp;"); 
				break;
			case 34: // '"'
				str.append("&quot;"); 
				break;
			case 39: // '''
				str.append("&apos;"); 
				break;
			default:
				str.append(ch);
				break;
		}
	}

	/**
	 * Normaliza Enter.<br>
	 * 
	 * @author N/C
	 * @since N/C
	 * @version N/C
         * @param s
         * @param str
         * @param i
	 */
	public static void normalizeEnter(String s, StringBuffer str, int i) {
		final char ch = s.charAt(i);
		switch (ch) {
			case 10: // '\n'
			case 13: // '\r'
				//if(canonical) {
				str.append("&#"); 
				str.append(Integer.toString(ch));
				str.append(';');
		}
	}

	/** Normaliza o String para apresenta-lo em HTML sem retirar o retorno de linha.<br>
	 * 
         * @param s
         * @author N/C
	 * @since N/C
	 * @version N/C
	 * @return String
	 */
	public static String normalize(String s) {
		StringBuffer str = new StringBuffer();
		final int len = (s == null) ? 0 : s.length();
		for (int i = 0; i < len; i++) {
			normalizeChars(s, str, i);
			normalizeEnter(s, str, i);
		}
		return str.toString();
	}

	/** Normaliza a quantidade de caracteres da quebra de linha.<br>
	 * Ex.: o conte�do de um texto tem 4060 caracteres.<br>
     *   	  A valida��o da pagina (permitir textos at� 4000 caracteres) estava funcionando.<br>
     *   	  Quando entrava nesta fun��o, toda quebra de linha era representada por 2 caracteres ("\r" e "\n"),
     *   		ent�o um texto que tivesse 5 Enters, na valida��o em javascript seria cortado com 4000 caracteres,
     *   		mas teria 5 caracteres a mais daqueles que foram validados no javascript (5 "\r" a mais).<br>
     *   	  Como no java, o "\n" j� representa uma quebra de linha, foi ignorado um eventual 
     *   		"\r\n" (retorno de linha, quebra de linha)<br>
     *   
         * @param s
         * @author N/C
	 * @since N/C
	 * @version N/C
	 * @return String
     */
	public static String normalizaQuebraDeLinha(String s) {
		return s.replaceAll("\r\n","\n");
	}
	
	/**
	 * Normaliza uma string para apresenta��o em HTML.<br>
	 * Troca "\n" por tag "&lt;br&gt;";
	 * @param s
	 * @return
	 */
	public static String normalizaQuebraDeLinhaHTML(String s){
		return (s != null) ? (normalizaQuebraDeLinha(s.trim())).replaceAll("\n", "<br>") : "";
	}
	
	/**
	 * Normaliza uma string para apresenta��o em HTML.<br>
	 * Troca "&lt;" por "<", "&gt;" por ">", e "&amp;" por "&"
	 * @param s
	 * @return
	 */
	public static String normalizaCaracterHTML(String s){
		return (s != null) ? (s.trim()).replaceAll("&lt;", "<").replaceAll("&gt;", ">").replaceAll("&amp;", "&") : "";
	}
	
	/**
	 * Normaliza caracter Marcador.<br>
	 * 
         * @param s
         * @author N/C
	 * @since N/C
	 * @version N/C
	 * @return String
	 */
	public static String normalizaCaracterMarcador(String s){
		if(s != null && !"".equals(s))
			return s.replace(Dominios.CARACTER_ESTRANHO_MARCADOR, "-").
					 replace(Dominios.CARACTER_ESTRANHO_MARCADOR2, "-").
					 replace(Dominios.CARACTER_ESTRANHO_ABREASPAS, "\"").
					 replace(Dominios.CARACTER_ESTRANHO_FECHAASPAS, "\"").
					 replace(Dominios.CARACTER_ESTRANHO_ABREASPAS_SIMPLES, "'").
					 replace(Dominios.CARACTER_ESTRANHO_FECHAASPAS_SIMPLES, "'");
		
		return "";
	}
	
	/**
	 * Completa um n�mero com zeros � esquerda.<br>
	 * 
	 * @author N/C
	 * @since N/C
	 * @version N/C
         * @param numero
         * @param tamanho
	 * @return String
	 */
	public static String completarZerosEsquerda(Long numero, int tamanho){
		
		String mascara = "";
		for(int i = 0; i < tamanho; i++){
			mascara = "0" + mascara;
		}

		NumberFormat nf = new DecimalFormat(mascara);
		
		if(numero != null)
			return nf.format(numero.longValue());
		else
			return mascara;
	}
	
	/**
	 * Completa uma string com caracteres at� o tamanho, seguindo uma direcao.<br>
	 *
	 * @author N/C
	 * @since N/C
	 * @version N/C
         * @param string
         * @param caracter
         * @param tamanho
         * @param direcao
         * @return String
	 */
	public static String completarCaracteres(String string, String caracter, int tamanho, String direcao){
		String retorno = string;
		if(string.length() < tamanho){
			if("E".equalsIgnoreCase(direcao)){
				int tam = string.length();
				for(int i = tam; i < tamanho; i++){
					retorno = caracter + retorno;
				}
			}
			if("D".equalsIgnoreCase(direcao)){
				int tam = string.length();
				for(int i = tam; i < tamanho; i++){
					retorno = retorno + caracter;
				}
			}
		}
		return retorno;
	}

	/**
	 * Retorna apenas o nome do arquivo, de acordo com a string do caminho completo ou relativo
	 * informado como par�metro do m�todo.<br>
	 * 
         * @param path
         * @author rogerio
	 * @since 0.1, 06/03/2007
	 * @version 0.1, 06/03/2007
	 * @return String
	 */
	public static String getNomeArquivo(String path) {
		String nome = null;
		
		if( path != null ) {
			if( path.lastIndexOf("/") != -1 ) 
				nome = path.substring(path.lastIndexOf("/")+1);  // windows
			else if( path.lastIndexOf("\\") != -1 )
				nome = path.substring(path.lastIndexOf("\\")+1); // linux
			else
				nome = path;
		}
		
		return nome;
	} // fim getNomeArquivo
	
	/**
	 * Retorna a tag usada para mostrar a imagem e a dica para a estrutura de telas do sistema. <br>
	 * @author rogerio
	 * @since 0.1, 22/03/2007
	 * @version 0.1, 22/03/2007
	 * @param nome - nome do atributo
	 * @param contexto - conexto da aplica��o
	 * @param dica - texto usado no tooltip
	 * @return String
	 */
	public static String getTagDica(String nome, String contexto, String dica) {
		StringBuffer s = new StringBuffer("&nbsp;&nbsp;") ;

		// Por Rog�rio. (05/04/2007. Mantis #9612.
		// Bloco colocado dentro do TRY para tratar um poss�vel retorno de erro na codifica��o da dica para ASCII.
		// Na realidade nenhum erro � retornado para evitar problemas na compila��o com o JSP.
		try {
	    	s.append("<label class=\"dica\" onmouseover=\"javascript:viewFieldTip(this, '"+nome+"SPAN');\"  onmouseout=\"javascript:noViewFieldTip('"+nome+"SPAN');\" >");
	    	s.append("<img src=\""+contexto+"/images/dica.png\" align=\"absmiddle\" border=\"0\" onclick=\"javascript:viewFieldTipPopUp(\'" + URLEncoder.encode(dica, Dominios.ENCODING_DEFAULT) + "\')\" >" );
	    	s.append("<span id=\""+nome+"SPAN\">" + dica + "</span></label>");
		} catch (Exception e) {
			// Ignora um retorno de erro, evitando problemas de compila��o com a JSP.
		}
	
    	return s.toString();
	} // fim getTagDica
	
	/**
	 * 
	 * @param nome - nome do atributo
	 * @param contexto - 
	 * @param urlImagem
	 * @param dica
	 * @return
	 */
	public static String getTagDicaComImagemParecer(String nome, String contexto, String urlImagem, String dica) {
		StringBuffer s = new StringBuffer("&nbsp;&nbsp;") ;

		// Por Rog�rio. (05/04/2007. Mantis #9612.
		// Bloco colocado dentro do TRY para tratar um poss�vel retorno de erro na codifica��o da dica para ASCII.
		// Na realidade nenhum erro � retornado para evitar problemas na compila��o com o JSP.
		try {
	    	s.append("<label class=\"dica\" onmouseover=\"javascript:viewFieldTip(this, '"+nome+"SPAN');\"  onmouseout=\"javascript:noViewFieldTip('"+nome+"SPAN');\" >");
	    	if(urlImagem.equals(contexto))
	    		s.append("<img src=\""+urlImagem+"/images/dica.png\" align=\"absmiddle\" border=\"0\"  onclick=\"javascript:viewFieldTipPopUp(\'" + URLEncoder.encode(dica, Dominios.ENCODING_DEFAULT) + "\')\" >" );
	    	else 
	    		s.append("<img src=\""+urlImagem+"\" align=\"absmiddle\" border=\"0\" style=\"width:16px;height:16px;\" onclick=\"javascript:viewFieldTipPopUp(\'" + URLEncoder.encode(dica, Dominios.ENCODING_DEFAULT) + "\')\" >" );
	    	
	    	s.append("<span id=\""+nome+"SPAN\">" + dica + "</span></label>");
		} catch (Exception e) {
			// Ignora um retorno de erro, evitando problemas de compila��o com a JSP.
		}
	
    	return s.toString();
	} // fim getTagDica

	
	
	
	/**
	 * Retorna a tag usada para mostrar a imagem e a dica para a estrutura de telas do sistema. <br>
	 * @author rogerio
	 * @since 0.1, 22/03/2007
	 * @version 0.1, 22/03/2007
	 * @param nome - nome do atributo
	 * @param contexto - conexto da aplica��o
         * @param urlImagem
         * @param dica - texto usado no tooltip
	 * @return String
	 */
	public static String getTagDica(String nome, String contexto, String urlImagem, String dica) {
		StringBuffer s = new StringBuffer("&nbsp;&nbsp;") ;

		// Por Rog�rio. (05/04/2007. Mantis #9612.
		// Bloco colocado dentro do TRY para tratar um poss�vel retorno de erro na codifica��o da dica para ASCII.
		// Na realidade nenhum erro � retornado para evitar problemas na compila��o com o JSP.
		try {
	    	s.append("<label class=\"dica\" onmouseover=\"javascript:viewFieldTip(this, '"+nome+"SPAN');\"  onmouseout=\"javascript:noViewFieldTip('"+nome+"SPAN');\" >");
	    	if(contexto==null ||contexto.equals("") )
	    		s.append("<img src=\""+urlImagem+"\" align=\"absmiddle\" border=\"0\" onclick=\"javascript:viewFieldTipPopUp(\'" + URLEncoder.encode(dica, Dominios.ENCODING_DEFAULT) + "\')\" >" );
	    	else
	    		s.append("<img src=\""+contexto+"/images/dica.png\" align=\"absmiddle\" border=\"0\" onclick=\"javascript:viewFieldTipPopUp(\'" + URLEncoder.encode(dica, Dominios.ENCODING_DEFAULT) + "\')\" >" );
	    	s.append("<span id=\""+nome+"SPAN\">" + dica + "</span></label>");
		} catch (Exception e) {
			// Ignora um retorno de erro, evitando problemas de compila��o com a JSP.
		}
	
    	return s.toString();
	} // fim getTagDica

	/* -- 
	 * M�todos utilizados para contornar o problema com o https no e-CAR.
	 * Dentro dos servlets dos relat�rios, em cada local onde � realizada uma chamada a uma imagem, faz-se necess�rio chamar 
	 *  o m�todo "Util.liberarImagem(String imagePath);". Dessa forma a imagem ser� exibida no relat�rio quando o e-CAR estiver 
	 *  sendo utilizado sob o protocolo SSL.
	 * Estes m�todos foram testados com a gera��o de relat�rios em FOP.
	 * -- */
	
	/**
	 * M�todo para liberar as imagens utilizadas em relat�rios sob protocolo SSL.<Br>
	 * (Obtido em pesquisas na Internet).<br>
	 * 
	 * @author N/C
	 * @since N/C
	 * @version N/C
	 * @throws ECARException
	 */
	public static void liberarImagem() throws ECARException {
		try {
			trustAllHttpsCertificates();
			
			HostnameVerifier hv = new HostnameVerifier() {
		        public boolean verify(String urlHostName, SSLSession session) {
		            //System.out.println("Warning: URL Host: " + urlHostName + " vs. " + session.getPeerHost());
		            return true;
		        }
		    };
			
			HttpsURLConnection.setDefaultHostnameVerifier(hv);
			
			URL url = new URL("");
			BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));

	        //int buff;
	        //while( (buff = in.read()) != -1 ) {}
	        //in.close();

		} catch (Exception e) {
            new ECARException("erro.liberar.imagem.ssl");
        }
	}
	
	/**
	 * M�todo para liberar as imagens utilizadas em relat�rios sob protocolo SSL.<Br>
	 * (Obtido em pesquisas na Internet).<br>
	 * 
	 * @author N/C
	 * @since N/C
	 * @version N/C
	 * @throws Exception
	 */    
    private static void trustAllHttpsCertificates() throws Exception {
        //  Create a trust manager that does not validate certificate chains:
    	TrustManager[] trustAllCerts = new TrustManager[1];
        TrustManager tm = new miTM();
        trustAllCerts[0] = tm;
        SSLContext sc = SSLContext.getInstance("SSL");
        sc.init(null, trustAllCerts, null);
        HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
    }

	/**
	 * M�todo para liberar as imagens utilizadas em relat�rios sob protocolo SSL.<br>
	 * (Obtido em pesquisas na Internet).<br>
	 * 
	 * @author N/C
	 * @since N/C
	 * @version N/C
	 */
    public static class miTM implements TrustManager, X509TrustManager {
        /**
         *
         * @return
         */
        public X509Certificate[] getAcceptedIssuers() { return null; }
        /**
         *
         * @param certs
         * @return
         */
        public boolean isServerTrusted(X509Certificate[] certs) { return true; }
                /**
                 *
                 * @param certs
                 * @return
                 */
                public boolean isClientTrusted(X509Certificate[] certs) { return true; }
                /**
                 *
                 * @param certs
                 * @param authType
                 * @throws CertificateException
                 */
                public void checkServerTrusted(X509Certificate[] certs, String authType) throws CertificateException { return; }
                /**
                 *
                 * @param certs
                 * @param authType
                 * @throws java.security.cert.CertificateException
                 */
                public void checkClientTrusted(X509Certificate[] certs, String authType) throws java.security.cert.CertificateException { return; }
	}
    
    
	/**
	 * = (TipoFuncAcompTpfa) tipoFuncAcompDao.buscar(TipoFuncAcompTpfa.class, codTpfa);
	 * 
	 * @param cor
	 * @param request
         * @param funcao
         * @return
	 * @throws ECARException 
	 */
	public static String getURLImagemAcompanhamento(Cor cor, HttpServletRequest request , TipoFuncAcompTpfa funcao ) throws ECARException, NoSuchAlgorithmException, UnsupportedEncodingException{
		String url=null;
		CorDao corDao = new CorDao(request);
		
		// Configura��o	
		ConfiguracaoCfg configuracao = new ConfiguracaoDao(request).getConfiguracao();
		String pathRaiz = configuracao.getRaizUpload();
		
		UsuarioUsu usuarioImagem = null;  
		String hashNomeArquivo = null;
		
		if(cor.getIndPosicoesGeraisCor().equals("S")){ 
			cor.getCodCor();
			
		url = corDao.getImagemPersonalizada(cor, funcao, "D");
		if( url != null ) { 
			
			usuarioImagem = ((ecar.login.SegurancaECAR)request.getSession().getAttribute("seguranca")).getUsuario(); 
			hashNomeArquivo = Util.calcularHashNomeArquivo(pathRaiz, url);
			Util.adicionarMapArquivosAtuaisUsuarios(usuarioImagem, hashNomeArquivo, pathRaiz, url);
			
			url=request.getContextPath()+"/DownloadFile?tipo=open&RemoteFile="+ hashNomeArquivo ;
		} else {
			if( cor.getCodCor() != null ) { 
				url =request.getContextPath() + "/images/" + corDao.getImagemSinal(cor,funcao)+ "" ;
			}
		} 
	}
		
		return url;
	}
    
	/**
	 * Retira os c�digos html do texto passado e retorna somente o texto
	 * substituindo o <br> por " " e &nbps; por " "
	 * 
	 * @param strHtml
	 * @return newString 
	 */
	public static String stripHTML(String strHtml){
		String newString = new String();
		if (strHtml != null){
			newString = strHtml.replaceAll("<br>", " ").replaceAll("&nbsp;", " ").replaceAll("&lt;", " ").replaceAll("<LI.*?>", "- ").replaceAll("<.*?>", "").trim();
		} 
						
		return newString;
	}
	
	/**
	 * Retira todas as tags htmls, n�o incluindo comentarios
	 * @param strHtml
	 * @return newString 
	 */
	public static String stripHTMLModificado(String strHtml){
		String newString = new String();
		if (strHtml != null){
			newString = strHtml.replaceAll("<br>", " ").replaceAll("&shy;", "-").replaceAll("&amp;","&").replaceAll("&nbsp;", " ").replaceAll("<LI.*?>", "- ").replaceAll("</?\\w++[^>]*+>", "").replaceAll("\\<\\?xml(.*)\\/\\>", "").trim();
		} 
						
		return newString;
	}
	
	/**
	 * Retira todas as tags htmls, n�o incluindo comentarios
	 * @param strHtml
	 * @return newString 
	 */
	public static String stripHTMLCommentsModificado(String strHtml){
		String newString = new String();
		if (strHtml != null){
			newString = strHtml.replaceAll("<!--.*?-->", "").trim();
		} 
						
		return newString;
	}
	
	/**
	 * Verifica se a string � um valor num�rico
         *
         * @param str
         * @return
         */

	public static boolean ehValor( String str) {
		try {
			Float.parseFloat(str);
			return true;
		} catch (NumberFormatException e) {
			return false;
		}
	}
	
	/**
	 * Faz a divis�o entre os argumentos
	 * 
	 * @param dividendo
	 * @param divisor
	 * @return
	 */
	public static double trataDivisaoPorZero(double dividendo, double divisor){
		if (divisor  == 0)
			return 0;
		else {
			return dividendo/divisor;
		}
	}
	
//	private static Properties  propriedadesHQL = null;
//	private static String NOME_PROPRIEDADES_HQL = "/context/WEB-INF/classes/properties/hql.properties";
//	static {
////		propriedadesHQL = new Properties();
////		
////		String caminhoPropriedadesHQL = null;
////		try{
////			File currentDirectory = new File (".");
//////			ResourceBundle resource = ResourceBundle.getBundle(currentDirectory.getCanonicalPath()+ NOME_PROPRIEDADES_HQL);
////			caminhoPropriedadesHQL = currentDirectory.getCanonicalPath()+ NOME_PROPRIEDADES_HQL;
////			propriedadesHQL.load(new BufferedReader(new FileReader(currentDirectory.getCanonicalPath()+ NOME_PROPRIEDADES_HQL)));
////		} catch (IOException e) {
////			System.out.println("Erro ao carregar propriedades " + caminhoPropriedadesHQL);
////		}
//		String curDir = System.getProperty("user.dir");
//	       
//        System.out.println("\nThe current working directory is:");
//        System.out.println("  - " + curDir);
//	}
//	
	
	private static Properties  propriedadesHQL = null;
        /**
         *
         * @param nomeHQL
         * @param servletContext
         * @return
         * @throws IOException
         */
        public static String getHql(String nomeHQL, ServletContext servletContext) throws IOException {
		
		//if (propriedadesHQL==null) {			
			propriedadesHQL = new Properties();
			propriedadesHQL.load(new FileInputStream(servletContext.getRealPath("/WEB-INF/classes/properties/hql.properties")));	
		//} 		
		return propriedadesHQL.getProperty(nomeHQL);		
	}
	
        /**
         *
         * @param numero
         * @return
         */
        public static String getEspacosEmBrancoHTML(int numero){
		StringBuffer sb = new StringBuffer();
		
		for (int i=0; i<numero; i++){
			sb.append("&nbsp;");
		}
		return sb.toString();
	}

	
	/**
	 * Identifica no conteudo a quantidade de ocorr�ncias do caracter passado, desde de que esteja em sequencia. 
	 * @param conteudo
         * @param caracter
         * @return
	 */
	public static int obterQuantidadeCaracterSequencial(String conteudo,char caracter) {
		
		int pos = conteudo.indexOf(caracter);
		int qtde = 0;
		
		for (; pos < conteudo.length(); pos++) {
			if (conteudo.charAt(pos) != caracter){
				break;
			}
			qtde++;
		}
		
		return qtde;
	}

	
        /**
         *
         * @param conteudo
         * @return
         */
        public static String removerZerosEsquerda(String conteudo) {
		return conteudo.replace("0","");
	}

        /**
         *
         * @param colecao
         * @return
         */
        public static List converteParaList(Collection colecao){
		
		List novaColecao = new ArrayList();
		
		Iterator it = colecao.iterator();
		
		while (it.hasNext()){
			
			novaColecao.add(it.next());
		}
		
		return novaColecao;
	}
	

        /**
         * 
         * @param usuario
         * @param hashNomeArquivo
         * @param pathRaiz
         * @param nomeArquivo
         */
    	public static void adicionarMapArquivosAtuaisUsuarios(UsuarioUsu usuario, String hashNomeArquivo, String pathRaiz, String nomeArquivo) {
    		
    		if ((nomeArquivo != null) && (hashNomeArquivo != null) ){
    			
    		if (usuario.getMapArquivosAtuaisUsuarios()==null)
    			usuario.setMapArquivosAtuaisUsuarios(new HashMap<String, String>());
    		
    	 	if (!usuario.getMapArquivosAtuaisUsuarios().containsKey(hashNomeArquivo)) {
    	 		if (pathRaiz!=null && !nomeArquivo.contains(pathRaiz)) {
    	 			if (!pathRaiz.endsWith(File.separator))
        	    		nomeArquivo = pathRaiz + File.separator + nomeArquivo;
    	 			else
    	 				nomeArquivo = pathRaiz + nomeArquivo;
    	 			
    	 			usuario.getMapArquivosAtuaisUsuarios().put(hashNomeArquivo, nomeArquivo);
    	 		} else {
    	 			usuario.getMapArquivosAtuaisUsuarios().put(hashNomeArquivo, nomeArquivo);
    	 		}
    	 	}
    		}
    	}
        
    	/**
    	 * 
    	 * @param arquivo
    	 * @return
    	 * @throws NoSuchAlgorithmException
    	 * @throws UnsupportedEncodingException 
    	 */
    	public static String calcularHashNomeArquivo(String pathRaiz, String arquivo) throws NoSuchAlgorithmException, UnsupportedEncodingException {
    		String hashCodeStr = "";
    		MessageDigest md = MessageDigest.getInstance("MD5");
    	    
    		if (arquivo == null)
    			return hashCodeStr; 

    	    if (pathRaiz != null && !arquivo.contains(pathRaiz)) {
    	    	if (!pathRaiz.endsWith(File.separator))
    	    		arquivo = pathRaiz + File.separator + arquivo;
    	    	else
    	    		arquivo = pathRaiz + arquivo;
    	    } 
    	    
    	    md.update(arquivo.getBytes());
    	    
    	    Base64Encoder bEncoder = new Base64Encoder();
    	    
    	   
    	    
    	    hashCodeStr = URLEncoder.encode(bEncoder.encode(md.digest()), Dominios.ENCODING_DEFAULT);
    	    
    	    	// 	%2B
    	    return hashCodeStr; 
    	    
    	}
    	
    	/**
    	 * Sobrecarga do m�todo calcularHashNomeArquivo podendo n�o usar o URLEncoder, atrav�s da vari�vel booleana encode
    	 * 
    	 * @param arquivo
    	 * @return
    	 * @throws NoSuchAlgorithmException
    	 * @throws UnsupportedEncodingException 
    	 */
    	public static String calcularHashNomeArquivo(String pathRaiz, String arquivo, boolean encode) throws NoSuchAlgorithmException, UnsupportedEncodingException {
    		String hashCodeStr = "";
    		MessageDigest md = MessageDigest.getInstance("MD5");
    	    
    		if (arquivo == null)
    			return hashCodeStr; 

    	    if (pathRaiz != null && !arquivo.contains(pathRaiz)) {
    	    	if (!pathRaiz.endsWith(File.separator))
    	    		arquivo = pathRaiz + File.separator + arquivo;
    	    	else
    	    		arquivo = pathRaiz + arquivo;
    	    } 
    	    
    	    md.update(arquivo.getBytes());
    	    
    	    Base64Encoder bEncoder = new Base64Encoder();
    	    
    	    
    	    
    	    if (encode){
    	    	hashCodeStr = URLEncoder.encode(bEncoder.encode(md.digest()), Dominios.ENCODING_DEFAULT);
    	    }
    	    else{
    	    	hashCodeStr = bEncoder.encode(md.digest());
    	    }
    	    
    	    	// 	%2B
    	    return hashCodeStr; 
    	    
    	}

    	/**
    	 * Executa o split, com qualquer separador. 
    	 * @param valorStr
    	 * @param separador
    	 * @return
    	 */
    	public static String[] split(String valorStr,String separador){
    		
    		String[] retorno= new String[0];
    		List<String> listaTemp;
    		int tamanhoSeparador = separador.length();
    		
    		
			String tmpStr = valorStr;
			listaTemp = new ArrayList<String>();
			int posicaoOcorenciaSeparador,proximaPosicaoValida,ultimaPosicaoStringAvaliada;
			
			
			while (!tmpStr.equals("")) {
				posicaoOcorenciaSeparador = tmpStr.indexOf(separador);
				ultimaPosicaoStringAvaliada = tmpStr.length();
				
				if (posicaoOcorenciaSeparador >= 0) {
					proximaPosicaoValida = posicaoOcorenciaSeparador + tamanhoSeparador; 
					
					listaTemp.add(tmpStr.substring(0, posicaoOcorenciaSeparador));
				} else {
					proximaPosicaoValida = 0;
					
					listaTemp.add(tmpStr.substring(0, ultimaPosicaoStringAvaliada));
					
					ultimaPosicaoStringAvaliada = 0;
				}
				
				tmpStr = tmpStr.substring(proximaPosicaoValida,ultimaPosicaoStringAvaliada);
			}
    			
    		return listaTemp.toArray(retorno);
    	}
    	
    	/**
    	 * Converte um array de String em array de Long
    	 * @param array
    	 * @return
    	 */
    	public static Long[] arrayStringToArrayLong(String[] array) {
    		Long[] arrayLong = new Long[array.length];
    		for (int i = 0; i < array.length; i++) {
				arrayLong[i] = new Long(array[i]);
			}
    		return arrayLong;
    	}
    	
    	
    	/**
    	 * Valida um endere�o IP
    	 * @param ipAddress
    	 * @return
    	 */
    	public static boolean validaIP(String ipAddress){
    		
    		Pattern  pattern = Pattern.compile(ConstantesECAR.IPADDRESS_PATTERN);
    		Matcher matcher = pattern.matcher(ipAddress);
  		  
    		return matcher.matches();	    
    	}
	

    	/**
    	 * Valida o formato hora minuto (HH:MM)
    	 * @param timeStr
    	 * @return
    	 */
    	public static boolean validaHora(String timeStr){
    		
    		Pattern  pattern = Pattern.compile(ConstantesECAR.TIME_PATTERN);
    		Matcher matcher = pattern.matcher(timeStr);
  		  
    		return matcher.matches();	    
    	}

    	
    	/**
    	 * Imprime a stack trace em uma string para que possa ser
    	 * impressa no log
    	 * 
    	 * @param t
    	 * @return
    	 */
    	public static String getStackTrace(Throwable t) {
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw, true);
            t.printStackTrace(pw);
            pw.flush();
            sw.flush();
            return sw.toString();
        }
    	

    	
} // fim class




