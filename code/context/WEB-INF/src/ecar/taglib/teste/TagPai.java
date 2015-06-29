/*
 * Criado em 08/12/2004
 *
 */
package ecar.taglib.teste;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.Tag;

/**
 * @author felipev
 *
 */
public class TagPai implements Tag{
    
    private String label;
    
    private PageContext page = null;
    
    /**
     * Inicializa a montagem da tag para ser adicionada na tela de HTML.<br>
     * 
     * @author N/C
	 * @since N/C
	 * @version N/C
	 * @reutnr int
	 * @throws JspException
     */
    public int doStartTag() throws JspException {
        JspWriter writer = this.page.getOut();
        StringBuffer s = new StringBuffer();
        s.append(getLabel()).append(": <BR>");
        try {
            writer.print(s.toString());
        } catch (IOException e) {
			org.apache.log4j.Logger.getLogger(this.getClass()).error(e);
        }
        return Tag.EVAL_BODY_INCLUDE;
    }
    
    /**
     * Atribui valor especificado para PageContext page.<br>
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
     * 
     * 
     * @author N/C
	 * @since N/C
	 * @version N/C
     * @param arg0
     */
	public void setParent(Tag arg0) {
	}

	/**
	 * Retorna null.<br>
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
        JspWriter writer = this.page.getOut();
        StringBuffer s = new StringBuffer();
        s.append(" TERMINOU!");
        try {
            writer.print(s.toString());
        } catch (IOException e) {
			org.apache.log4j.Logger.getLogger(this.getClass()).error(e);
        }
		return Tag.EVAL_PAGE;
	}

	/**
	 * @author N/C
	 * @since N/C
	 * @version N/C
	 */
	public void release() {
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
     * Atribui valoe especificado para PageContext page.<br>
     * 
     * @param page
     * @author N/C
	 * @since N/C
	 * @version N/C
     */
    public void setPage(PageContext page) {
        this.page = page;
    }

    /**
     * Retorna String label.<br>
     * 
     * @author N/C
	 * @since N/C
	 * @version N/C
     * @return String - (Returns the label)
     */
    public String getLabel() {
        return label;
    }
    /**
     * Atribui valor especificado String label.<br>
     * 
     * @author N/C
	 * @since N/C
	 * @version N/C
     * @param label
     */
    public void setLabel(String label) {
        this.label = label;
    }
}