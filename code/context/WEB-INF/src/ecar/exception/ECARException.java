/*
 * Criado em 26/10/2004
 *
 */
package ecar.exception;

import java.io.PrintWriter;
import java.io.StringWriter;


/**
 * @author N/C
 * @since N/C
 * @version N/C
 */
public class ECARException extends Exception {
	private static final long serialVersionUID = 8965552994271665523L;
        /**
         *
         */
        protected Throwable causaRaiz = null;
	private String messageKey = null;
	private Object[] messageArgs = null;

	/**
	 * Construtor.<br>
	 * 
	 * @author N/C
     * @since N/C
     * @version N/C
	 */
	public ECARException() {
		super();
	}

	/**
	 * Atribui valor especificado para String MessageKey.<br>
	 * 
	 * @author N/C
     * @since N/C
     * @version N/C
         * @param messageKeyLoc
	 */
	public ECARException(String messageKeyLoc) {
		setMessageKey(messageKeyLoc);
	}
	
	/**
	 * Atribui valor especificado para Throwable CausaRaiz.<br>
	 * 
         * @param causa
         * @author N/C
     * @since N/C
     * @version N/C
	 */
	public ECARException(Throwable causa) {
		setCausaRaiz(causa);
	}
	
	/**
	 * Atribui valores especificados para String messageKey e Throwable causaRaiz.<br>
	 * 
	 * @author N/C
     * @since N/C
     * @version N/C
         * @param messageKeyLoc
         * @param causa
	 */
	public ECARException(String messageKeyLoc, Throwable causa) {
		setMessageKey(messageKeyLoc);
		setCausaRaiz(causa);
	}
	
	/**
	 * Atribui valores especificados para String messageKey, Throwable causaRaiz e Object[] messageArgs.<br>
	 * 
	 * @author N/C
     * @since N/C
     * @version N/C
         * @param messageKeyLoc
         * @param causa
         * @param messageArgsLoc
	 */
	public ECARException(String messageKeyLoc, 
				Throwable causa, 
				Object[] messageArgsLoc) {
		setMessageKey(messageKeyLoc);
		setCausaRaiz(causa);
		setMessageArgs(messageArgsLoc);
	}
	
	/**
	 * Retorna Throwable causaRaiz.<br>
	 * 
	 * @author N/C
     * @since N/C
     * @version N/C
	 * @return Throwable - Returns the causaRaiz.
	 */
	public Throwable getCausaRaiz() {
		return causaRaiz;
	}
	
	/**
	 * Atribui valor especificado para Throwable causaRaiz.<br>
	 * 
	 * @author N/C
     * @since N/C
     * @version N/C
         * @param causaRaiz
	 */
	public void setCausaRaiz(Throwable causaRaiz) {
		this.causaRaiz = causaRaiz;
	}
	
	/**
	 * Retorna Object[] messageArgs.<br>
	 * 
	 * @author N/C
     * @since N/C
     * @version N/C
	 * @return Object[] - Returns the messageArgs.
	 */
	public Object[] getMessageArgs() {
		return messageArgs;
	}
	
	/**
	 * Atribui valor especificado para Object[] messageArgs.<br>
	 * 
	 * @author N/C
     * @since N/C
     * @version N/C
         * @param messageArgs
	 */
	public void setMessageArgs(Object[] messageArgs) {
		this.messageArgs = messageArgs;
	}
	
	/**
	 * Retorna String messageKey.<br>
	 * 
	 * @author N/C
     * @since N/C
     * @version N/C
	 * @return String - Returns the messageKey.
	 */
	public String getMessageKey() {
		return messageKey;
	}
	
	/**
	 * Atribui valor especificado para String messageKey.<br>
	 * 
	 * @author N/C
     * @since N/C
     * @version N/C
         * @param messageKey
	 */
	public void setMessageKey(String messageKey) {
		this.messageKey = messageKey;
	}
	
	public String getStackTraceString(){
		 StringWriter sw = new StringWriter();
	     PrintWriter pw = new PrintWriter(sw, true);
	     printStackTrace(pw);
	     pw.flush();
	     sw.flush();
	     return sw.toString();
	}
	
}
