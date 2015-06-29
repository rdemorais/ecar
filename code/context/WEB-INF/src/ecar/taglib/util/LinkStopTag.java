/*
 * Created on 06/10/2004
 *
 */
package ecar.taglib.util;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.Tag;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.log4j.Logger;

/**
 * @author garten
 * 
 * TagLib para montar a barra de links
 *
 */
public class LinkStopTag extends TagSupport {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7350219627754223836L;

	private Logger logger = Logger.getLogger(this.getClass());

	// constantes da sintaxe do html
	//private final String ASPAS = "\"";
	private final String DIVISAO = "&nbsp;|&nbsp;";
	
	// parametros da taglib
	private String incluir;
	private String alterar;
	private String excluir;
	private String pesquisar;

	/**
	 * Inicializa a montagem da tag para ser adicionada na tela de HTML.<br>
	 * 
	 * @author N/C
	 * @since N/C
	 * @version N/C
	 * @return int
	 * @throws JspException
	 */
    @Override
	public int doStartTag() throws JspException {
		JspWriter writer = this.pageContext.getOut();
		boolean imprimiu = false;
		
		try {
			writer.println("<div id=\"linkstop\">");
			if (this.getIncluir() != null) {
				writer.println(imprimiu ? DIVISAO : "");
				writer.println("<a href=\"" + this.getIncluir() + "\">Incluir</a>");
				imprimiu = true;
			}

			if (this.getAlterar() != null){
				writer.println(imprimiu ? DIVISAO : "");
				writer.println("<a href=\"" + this.getAlterar() + "\">Alterar</a>");
				imprimiu = true;
			}

			if (this.getExcluir() != null){
				writer.println(imprimiu ? DIVISAO : "");
				writer.println("<a href=\"" + this.getExcluir() + "\">Excluir</a>");
				imprimiu = true;
			}
			
			if (this.getPesquisar() != null){
				writer.println(imprimiu ? DIVISAO : "");
				writer.println("<a href=\"" + this.getPesquisar() + "\">Pesquisar</a>");
				imprimiu = true;
			}
			writer.println("</div>");
		} catch (Exception e) {
			logger.error(e);
		}
		return Tag.SKIP_BODY;
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
    @Override
	public int doEndTag() throws JspException {
		return Tag.EVAL_PAGE;
	}

	/**
	 * Retorna String alterar.<br>
	 * 
	 * @author N/C
	 * @since N/C
	 * @version N/C
	 * @return String
	 */
	public String getAlterar() {
		return alterar;
	}
	
	/**
	 * Atribui valor especificado para String alterar.<br>
	 * 
	 * @author N/C
	 * @since N/C
	 * @version N/C
         * @param alterar
	 */
	public void setAlterar(String alterar) {
		this.alterar = alterar;
	}
	
	/**
	 * Retorna String excluir.<br>
	 * 
	 * @author N/C
	 * @since N/C
	 * @version N/C
	 * @return String
	 */
	public String getExcluir() {
		return excluir;
	}
	
	/**
	 * Atribui valor especificado para String excluir.<br>
	 * 
         * @param excluir
         * @author N/C
	 * @since N/C
	 * @version N/C
	 */
	public void setExcluir(String excluir) {
		this.excluir = excluir;
	}
	
	/**
	 * Retorna String incluir.<br>
	 * 
	 * @author N/C
	 * @since N/C
	 * @version N/C
	 * @return String
	 */
	public String getIncluir() {
		return incluir;
	}
	
	/**
	 * Atribui valor especificado para String incluir.<br>
	 * 
	 * @author N/C
	 * @since N/C
	 * @version N/C
         * @param incluir
	 */
	public void setIncluir(String incluir) {
		this.incluir = incluir;
	}
	
	/**
	 * Retorna String pesquisar.<br>
	 * 
	 * @author N/C
	 * @since N/C
	 * @version N/C
	 * @return String
	 */
	public String getPesquisar() {
		return pesquisar;
	}
	
	/**
	 * Atribui valor especificado para String pesquisar.<br>
	 * 
         * @param pesquisar
         * @author N/C
	 * @since N/C
	 * @version N/C
	 */
	public void setPesquisar(String pesquisar) {
		this.pesquisar = pesquisar;
	}
}	
