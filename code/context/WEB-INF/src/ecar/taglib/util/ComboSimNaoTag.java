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
 * Taglib para Criação de Combo com Opções SIM e NÃO.
 * @author felipev
 */
public class ComboSimNaoTag implements Tag{
 
    String nome;
    String valorSelecionado;
    String opcaoBranco;
    String scripts;

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
	    s.append("<select name=\"").append(getNome()).append("\"").append(scripts).append(">");
	    if(opcaoBranco != null)
	        s.append("<option value=\"\">").append(getOpcaoBranco()).append("</option>");    
	    s.append("<option value=\"S\"");
	    if("S".equals(getValorSelecionado()))
	        s.append(" selected ");
	    s.append(">Sim</option>");
	    s.append("<option value=\"N\"");
	    if("N".equals(getValorSelecionado()))
	        s.append(" selected ");
	    s.append(">Não</option>");	    	    
	    s.append("</select>");
	    writer.print(s);
	} catch (Exception e) {
    	Logger logger = Logger.getLogger(this.getClass());
    	logger.error(e);
    	try{
    	    writer.print("Erro na geração da Combo: " + e.getMessage());
    	    logger.error(e);
    	}catch(IOException ioE){
    	    logger.error(ioE);
    	}   	
	}
		return Tag.EVAL_BODY_INCLUDE;
	}

    /**
     * @param arg0
     * @author N/C
	 * @since N/C
	 * @version N/C
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
     * @param page
     * @author N/C
	 * @since N/C
	 * @version N/C
     */
    public void setPage(PageContext page) {
        this.page = page;
    }

    /**
     * Retorna String nome
     * 
     * @author N/C
	 * @since N/C
	 * @version N/C
     * @return String - (Returns the nome)
     */
    public String getNome() {
        return nome;
    }
    
    /**
     * Atribui valor especificado para String nome.<br>
     * 
     * @author N/C
	 * @since N/C
	 * @version N/C
     * @param nome
     */
    public void setNome(String nome) {
        this.nome = nome;
    }
    
    /**
     * Retorna String opcaoBranco.<br>
     * 
     * @author N/C
	 * @since N/C
	 * @version N/C
     * @return String - (Returns the opcaoBranco)
     */
    public String getOpcaoBranco() {
        return opcaoBranco;
    }
    
    /**
     * Atribui valor especificado para String opcaoBranco.<br>
     * 
     * @author N/C
	 * @since N/C
	 * @version N/C
     * @param opcaoBranco
     */
    public void setOpcaoBranco(String opcaoBranco) {
        this.opcaoBranco = opcaoBranco;
    }
    
    /**
     * Retorna String valorSelecionado.<br>
     * 
     * @author N/C
	 * @since N/C
	 * @version N/C
     * @return String - (Returns the valorSelecionado)
     */
    public String getValorSelecionado() {
        return valorSelecionado;
    }
    
    /**
     * Atribui valor especificado para String valorSelecionado.<br>
     * 
     * @param valorSelecionado
     * @author N/C
	 * @since N/C
	 * @version N/C
     */
    public void setValorSelecionado(String valorSelecionado) {
        this.valorSelecionado = valorSelecionado;
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
     * Retorna String scripts.<br>
     * 
     * @author N/C
	 * @since N/C
	 * @version N/C
     * @return String - (Returns the scripts)
     */
    public String getScripts() {
        return scripts;
    }
    
    /**
     * Atribui valor especificado para String scripts.<br>
     * 
     * @param scripts
     * @author N/C
	 * @since N/C
	 * @version N/C
     */
    public void setScripts(String scripts) {
        this.scripts = scripts;
    }

}