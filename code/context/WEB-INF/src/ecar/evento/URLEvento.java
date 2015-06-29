package ecar.evento;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;
import java.util.Properties;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import ecar.exception.ECARException;
import ecar.util.Dominios;

/**
 *
 * @author 70744416353
 */
public class URLEvento {

	private static Properties  navegacaoURL = null;
	
	/**
     * Monta URL base da aplicação.
     * 
         * @param request
         * @return String caminhoBase
     */	
	public static String montaURLBase(HttpServletRequest request) {
		String esquema  = request.getScheme();
		String servidor = request.getServerName(); 
		int porta    = request.getServerPort();
		
	    String urlBase = esquema + "://" +
	    				 servidor;
	    
	    if( porta > 0 ) {
		    urlBase += ":";
		    urlBase += request.getServerPort();
	    }
	    urlBase += request.getContextPath();
	    
	    return urlBase;
	}
	
	/**
     * Monta URL associada ao evento para envio por email.
     * 
         * @param evento
         * @param request
         * @return String urlEvento
     */	
	public static String montaURLEvento(Evento evento, HttpServletRequest request) {
		String urlEvento = null;
		List<String> parametros = evento.getParametros();
		String strParam, valor;

		urlEvento = montaURLBase(request) +
			  		Dominios.PATH_LOGIN +
			  		Dominios.LOGIN_EVENTO + evento.getId() +
			  		"&" +
			  		Dominios.LOGIN_PARAM;
		
		for (int i = 0; i < parametros.size(); i++) {
			strParam = parametros.get(i);
			
			valor = request.getParameter(strParam);
			
			
			if( valor == null || valor.equals("")) {
				//pode ser que esteja setado pelo atributo do request
				//necessario em algumas telas
				valor = (String) request.getAttribute(strParam);
				//já remove o atributo
				request.removeAttribute(strParam);
				if(valor == null)
					valor = "";
			}
			
			urlEvento += strParam + "=" + valor; 
			
			if( i != (parametros.size() - 1) ) {
				urlEvento += ",";
			}
		}
		
		return urlEvento;
	}
	
	
	/**
     * Monta URL associada ao evento para envio por email nos casos em que não possui um request ( o evento é disparado pelo próprio sistema numa
     * determinada hora do dia).
     * 
         * @param evento
         * @param contextPath 
         * @param valores
     * @return String urlEvento
     */	
	public static String montaURLEventoSemRequest(Evento evento, String contextPath, String[] valores) {
		String urlEvento = null;
		List<String> parametros = evento.getParametros();
		String strParam, valorParam;

		urlEvento = contextPath +
			  		Dominios.PATH_LOGIN +
			  		Dominios.LOGIN_EVENTO + evento.getId() +
			  		"&" +
			  		Dominios.LOGIN_PARAM;
		
		for (int i = 0; i < parametros.size(); i++) {
			strParam = parametros.get(i);
			valorParam = valores[i];
			
			urlEvento += strParam + "=" + valorParam; 
			
			if( i != (parametros.size() - 1) ) {
				urlEvento += ",";
			}
		}
		
		return urlEvento;
	}

	/**
     * Pega URL base associada ao evento no arquivo de properties.
     * 
         * @param idURL
         * @param servletContext
         * @return String
     * 
     * @throws IOException
     */	
	public static String obtemURLBaseRedirecionamento(String idURL, ServletContext servletContext) throws IOException {
		if (navegacaoURL == null) {			
			navegacaoURL = new Properties();
			navegacaoURL.load(new FileInputStream(servletContext.getRealPath("/WEB-INF/classes/properties/url.properties")));	
		}
		
		return navegacaoURL.getProperty(idURL);		
	}

	/**
     * Monta URL para redirecionamento de tela.
     * 
         * @param idURL
         * @param parametros
         * @param servletContext
     * @return String url
     * 
     * @throws ECARException
     */	
	public static String montaURLRedirecionamento(String idURL, String parametros, ServletContext servletContext) throws ECARException {
		String url = null;
		String param = "";
		
		try {
			if( parametros != null && !parametros.equals("") ) {
				param = parametros.replaceAll(",", "&");	
			}
			
			url = obtemURLBaseRedirecionamento(idURL, servletContext);
			
			if( url == null || url.equals("") ) {
				throw new ECARException("erro.email.url.evento"); 
			}
			
			// Monta a url com os parametros  
			url += param;
		}
		catch(IOException ioE) {
			ioE.printStackTrace();
			throw new ECARException(ioE);
		}
		
		return url;		
	}

}
