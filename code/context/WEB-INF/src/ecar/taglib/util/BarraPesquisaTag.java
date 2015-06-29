/*
 * Created on 13/12/2004
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
 */
public class BarraPesquisaTag extends TagSupport {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8613849191957082475L;

	private Logger logger = Logger.getLogger(this.getClass());

	// constantes da sintaxe do html
	//private final String ASPAS = "\"";
	//private final String DIVISAO = "";
	
	// parametros da taglib
	private int atual;
	private int total;
	private int numItensExibidosPaginacao;
	private int totalItens;
	

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
		
		try {
			writer.println("<table class=\"barrabotoes\" cellpadding=\"0\" cellspacing=\"0\">");
			writer.println("<tr>");

			writer.println("<td width=\"20%\" align=\"left\">");
			writer.println("<input name=\"btnPrimeiro\" type=\"button\" value=\"<<\" class=\"botao\" onclick=\"aoClicarPrimeiro(form);\">");
			writer.println("<input name=\"btnAnterior\" type=\"button\" value=\"<\" class=\"botao\" onclick=\"aoClicarAnterior(form);\">");
			writer.println("</td>");

			writer.println("<td align=\"center\" width=\"60%\">");
			if (this.getAtual() != 0)
			    writer.print("P&aacute;gina " + this.getAtual());
			
			if (this.getTotal() != 0) {
				if (this.getAtual() != 0)
				    writer.print(" de ");
			    writer.println(this.getTotal());
			}
			
			if(this.getNumItensExibidosPaginacao() != 0 && this.getTotalItens() != 0){
				writer.println(", Registros ");
				writer.println((this.getAtual()-1)*this.getNumItensExibidosPaginacao() + 1);
				writer.println("-");
				if(this.getAtual() == this.getTotal()){
					writer.println(this.getTotalItens());
				} else{
					writer.println(this.getAtual()*this.getNumItensExibidosPaginacao());
				}
				writer.print(" de ");
				writer.print(this.getTotalItens());
			}
			
			writer.println("</td>");
			
			
			writer.println("<td width=\"20%\" align=\"right\">");
			writer.println("<input name=\"btnProximo\" type=\"button\" value=\">\" class=\"botao\" onclick=\"aoClicarProximo(form);\">");
			writer.println("<input name=\"btnUltimo\" type=\"button\" value=\">>\" class=\"botao\" onclick=\"aoClicarUltimo(form);\">");
			writer.println("</td>");

			writer.println("</tr>");
			writer.println("</table>");
		} catch (Exception e) {
			logger.error(e);
		}
		/* nao processa o conteudo do corpo da tag, porque nao existe */
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
	    /* processa o restante da página jsp */
		return Tag.EVAL_PAGE;
	}

    /**
     * Retorna int atual.<br>
     * 
     * @author N/C
	 * @since N/C
	 * @version N/C
     * @return int - (Returns the atual)
     */
    public int getAtual() {
        return atual;
    }
    
    /**
     * Atribui valor especificado para int atual.<br>
     * 
     * @author N/C
	 * @since N/C
	 * @version N/C
     * @param atual
     */
    public void setAtual(int atual) {
        this.atual = atual;
    }
    
    /**
     * Retorna int total.<br>
     * 
     * @author N/C
	 * @since N/C
	 * @version N/C
     * @return int - (Returns the total)
     */
    public int getTotal() {
        return total;
    }
    
    /**
     * Atribui valor especificado para int total.<br>
     * 
     * @author N/C
	 * @since N/C
	 * @version N/C
     * @param total
     */
    public void setTotal(int total) {
        this.total = total;
    }

    /**
     *
     * @return
     */
    public int getNumItensExibidosPaginacao() {
		return numItensExibidosPaginacao;
	}

        /**
         *
         * @param numItensExibidosPaginacao
         */
        public void setNumItensExibidosPaginacao(int numItensExibidosPaginacao) {
		this.numItensExibidosPaginacao = numItensExibidosPaginacao;
	}

        /**
         *
         * @return
         */
        public int getTotalItens() {
		return totalItens;
	}

        /**
         *
         * @param totalItens
         */
        public void setTotalItens(int totalItens) {
		this.totalItens = totalItens;
	}
}
