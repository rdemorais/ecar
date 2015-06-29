/*
 * Criado em 14/01/2005
 *
 */
package ecar.taglib.util;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.Tag;

import org.apache.log4j.Logger;
 
/**
 */
public class MensagemObrigatorioTag implements Tag{
 
    String obrigatorio;

    private PageContext page = null;	

    /**
     * Inicializa a montagem da tag para ser adicionada na tela de HTML.<br>
     * 
     * @author N/C
	 * @since N/C
	 * @version N/C
	 * @return int
	 * @throws JspException
     */
    public int doStartTag() throws JspException {
		JspWriter writer = this.page.getOut();
		StringBuffer s = new StringBuffer();
	try{
	    if ("*".equals(getObrigatorio())) {
		    s.append("<tr>");
		    s.append("<td colspan=\"4\">");
		    s.append("<div class=\"camposobrigatorios\">* Campos de preenchimento obrigat&oacute;rio</div>");
		    s.append("</td>");
		    s.append("</tr>");
	    } else
	        s.append("<tr><td colspan=\"2\">&nbsp;</td></tr>");
	    
	    writer.print(s);
	} catch (Exception e) {
	   	try{
    	    writer.print("Erro na geração da TagLib: " + e.getMessage());
        	Logger logger = Logger.getLogger(this.getClass());
        	logger.error(e);
    	}catch(IOException ioE){
        	Logger logger = Logger.getLogger(this.getClass());
        	logger.error(ioE);
    	}   	
	}
		return Tag.EVAL_BODY_INCLUDE;
	}

    /**
     * @author N/C
	 * @since N/C
	 * @version N/C
     * @param arg0
     */
	public void setParent(Tag arg0) {
	}

	/**
	 * Retorna Tag null.<br>
	 * 
	 * @author N/C
	 * @since N/C
	 * @version N/C
	 * @return Tag
	 */
	public Tag getParent() {
		return null;
	}
	
	/**
	 * Encerra Tag.<br>
	 * 
	 * @author N/C
	 * @since N/C
	 * @version N/C
	 * @return int
	 * @throws JspException
	 */
	public int doEndTag() throws JspException {
		return Tag.EVAL_PAGE;
	}

	/**
	 * @author N/C
	 * @since N/C
	 * @version N/C
	 */
	public void release() {
		//this.selected = null;
	}
	
    /**
     * Retorna PageContext page.<br>
     * 
     * @author N/C
	 * @since N/C
	 * @version N/C
     * @return PageContext - (Returns the page)
     */
    public PageContext getPage() {
        return page;
    }
    
    /**
     * Atribui valor especificado para PageContext page.<br>
     * 
     * @author N/C
	 * @since N/C
	 * @version N/C
     * @param page
     */
    public void setPage(PageContext page) {
        this.page = page;
    }

    /**
     * Retorna PageContext page.<br>
     * 
     * @param arg0
     * @author N/C
	 * @since N/C
	 * @version N/C
     */
    public void setPageContext(PageContext arg0) {
		this.page = arg0;
	}

    /**
     * Retorna String obrigatorio.<br>
     * 
     * @author N/C
	 * @since N/C
	 * @version N/C
     * @return String - (Returns the obrigatorio)
     */
    public String getObrigatorio() {
        return obrigatorio;
    }
    
    /**
     * Atribui valor especificado para String obrigatorio.<br>
     * 
     * @param obrigatorio
     * @author N/C
	 * @since N/C
	 * @version N/C
     */
    public void setObrigatorio(String obrigatorio) {
        this.obrigatorio = obrigatorio;
    }
}
