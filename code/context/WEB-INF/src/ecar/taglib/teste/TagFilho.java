/*
 * Criado em 08/12/2004
 *
 */
package ecar.taglib.teste;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.Tag;

/**
 * @author felipev
 *
 */
public class TagFilho implements Tag{
    
    private List textos;
    
    private PageContext page = null;
    
    private Tag parent;
    
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
        Iterator it = textos.iterator();
        
        TagPai tagPai = (TagPai) getParent();
        
        while(it.hasNext())
            s.append(it.next()).append("e o label da Tag Pai é ").append(tagPai.getLabel()).append(" <BR> " );
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
     * @author N/C
	 * @since N/C
	 * @version N/C
     * @param arg0
     */
    public void setPageContext(PageContext arg0) {
		this.page = arg0;
	}

    /**
     * Atribui valor especificado para Tag parent.<br>
     * 
     * @param arg0
     * @author N/C
	 * @since N/C
	 * @version N/C
     */
	public void setParent(Tag arg0) {
	    parent = arg0;
	}

	/**
	 * Retorna Tag parent.<br>
	 * 
	 * @author N/C
	 * @since N/C
	 * @version N/C
	 * @return Tag
	 */
	public Tag getParent() {
		return parent;
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
	 * 
	 * 
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
     * Retorna List textos.<br>
     * 
     * @author N/C
	 * @since N/C
	 * @version N/C
     * @return List - (Returns the textos)
     */
    public List getTextos() {
        return textos;
    }
    
    /**
     * Atribui valor especificado para List textos.<br>
     * 
     * @param textos
     * @author N/C
	 * @since N/C
	 * @version N/C
     */
    public void setTextos(List textos) {
        this.textos = textos;
    }
}