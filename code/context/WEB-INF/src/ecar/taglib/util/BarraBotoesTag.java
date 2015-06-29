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
 * TagLib para montar a barra de botoes.<br>
 * @author garten
 */
public class BarraBotoesTag extends TagSupport {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7126604742141401956L;

	private Logger logger = Logger.getLogger(this.getClass());

	// constantes da sintaxe do html
	//private final String ASPAS = "\"";
	private final String DIVISAO = "";
	
	// parametros da taglib
	private String navegacao;
	private String btn1;             // botoes extras 1, 2, 3
	private Boolean exibirBtn1;
	private String btn2;
	private Boolean exibirBtn2;
	private String btn3;	
	private Boolean exibirBtn3;
	private String incluir;
	private Boolean exibirIncluir;
	private String alterar;
	private Boolean exibirAlterar;
	private String excluir;
	private Boolean exibirExcluir;
	private String pesquisar;
	private String cancelar;
	private Boolean esconderCancelar;
	private String voltar;
	private int atual;
	private int total;
	private Boolean desabilitarBtn3;
	private String btn4;		//botão extra 4
	private Boolean exibirBtn4;
	private Boolean controlaFluxo;

	private String textoDireita;

        /**
         *
         * @return
         */
        public String getTextoDireita() {
		return textoDireita;
	}

        /**
         *
         * @param textoDireita
         */
        public void setTextoDireita(String textoDireita) {
		this.textoDireita = textoDireita;
	}

	/**
	 * Retorna Boolean desabilitarBtn3.<br>
	 * 
	 * @author N/C
	 * @since N/C
	 * @version N/C
	 * @return Boolean
	 */
	public Boolean getDesabilitarBtn3() {
		return desabilitarBtn3;
	}

	/**
	 * Atribui valor especificado para Boolean desabilitarBtn3.<br>
	 * 
         * @param desabilitarBtn3
         * @author N/C
	 * @since N/C
	 * @version N/C
	 */
	public void setDesabilitarBtn3(Boolean desabilitarBtn3) {
		this.desabilitarBtn3 = desabilitarBtn3;
	}

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
			writer.println("<table class=\"barrabotoes\" cellpadding=\"0\" cellspacing=\"0\">");
			writer.println("<tr>");

			writer.println("<td class=\"label\">");
			
			/* imprime os registros da navegação */
			if (this.getAtual() != 0)
			    writer.println(this.getAtual());
			
			if (this.getTotal() != 0) {
				if (this.getAtual() != 0)
				    writer.println(" de ");
			    writer.println(this.getTotal());
			}
			
			writer.println("&nbsp;</td>");			
			writer.println("<td>");
			
			if (this.getNavegacao() != null) {
				writer.println(imprimiu ? DIVISAO : "");
				writer.println("<input name=\"btnPrimeiro\" type=\"button\" value=\"<<\" class=\"botao\" onclick=\"aoClicarPrimeiro(form);\">");
				writer.println(imprimiu ? DIVISAO : "");
				writer.println("<input name=\"btnAnterior\" type=\"button\" value=\"<\" class=\"botao\" onclick=\"aoClicarAnterior(form);\">");
				writer.println(imprimiu ? DIVISAO : "");
				writer.println("<input name=\"btnProximo\" type=\"button\" value=\">\" class=\"botao\" onclick=\"aoClicarProximo(form);\">");
				writer.println(imprimiu ? DIVISAO : "");
				writer.println("<input name=\"btnUltimo\" type=\"button\" value=\">>\" class=\"botao\" onclick=\"aoClicarUltimo(form);\">");
				imprimiu = true;
			}
			
			if (this.getBtn1() != null && (this.getExibirBtn1() == null || this.getExibirBtn1().booleanValue()) ) {
				writer.println(imprimiu ? DIVISAO : "");
				writer.println("<input name=\"btn1\" type=\"button\" value=\"" + this.getBtn1() + "\" class=\"botao\" onclick=\"aoClicarBtn1(form);\">");
				imprimiu = true;
			}

			if (this.getBtn2() != null && (this.getExibirBtn2() == null || this.getExibirBtn2().booleanValue()) ) {
				writer.println(imprimiu ? DIVISAO : "");
				writer.println("<input name=\"btn2\" type=\"button\" value=\"" + this.getBtn2() + "\" class=\"botao\" onclick=\"aoClicarBtn2(form);\">");
				imprimiu = true;
			}

			if (this.getBtn3() != null && (this.getExibirBtn3() == null || this.getExibirBtn3().booleanValue()) ) {
				writer.println(imprimiu ? DIVISAO : "");
				String disabled = "";
				
				if(this.getDesabilitarBtn3() != null && this.getDesabilitarBtn3().booleanValue()) {
					disabled = "disabled";
				}
				
				writer.println("<input name=\"btn3\" type=\"button\" value=\"" + this.getBtn3() + "\" " + disabled + " class=\"botao\" onclick=\"aoClicarBtn3(form);\">");
				imprimiu = true;
			}
			
			if (this.getIncluir() != null && (this.getExibirIncluir() == null || this.getExibirIncluir().booleanValue()) ) {
				writer.println(imprimiu ? DIVISAO : "");
				writer.println("<input name=\"btnGravar\" type=\"button\" value=\"" + this.getIncluir() + "\" class=\"botao\" onclick=\"aoClicarGravar(form);\">");
				imprimiu = true;
			}

			if (this.getAlterar() != null && (this.getExibirAlterar() == null || this.getExibirAlterar().booleanValue()) ){
				writer.println(imprimiu ? DIVISAO : "");
				writer.println("<input name=\"btnAlterar\" type=\"button\" value=\"" + this.getAlterar() + "\" class=\"botao\" onclick=\"aoClicarAlterar(form);\">");
				imprimiu = true;
			}

			if (this.getExcluir() != null && (this.getExibirExcluir() == null || this.getExibirExcluir().booleanValue()) ){
				writer.println(imprimiu ? DIVISAO : "");
				writer.println("<input name=\"btnExcluir\" type=\"button\" value=\"" + this.getExcluir() + "\" class=\"botao\" onclick=\"aoClicarExcluir(form);\">");
				imprimiu = true;
			}
			
			if (this.getPesquisar() != null){
				writer.println(imprimiu ? DIVISAO : "");
				writer.println("<input name=\"btnPesquisar\" type=\"button\" value=\"" + this.getPesquisar() + "\" class=\"botao\" onclick=\"aoClicarPesquisar(form);\">");
				imprimiu = true;
			}
			
			if (this.getCancelar() != null){
				writer.println(imprimiu ? DIVISAO : "");
				if(this.getEsconderCancelar() != null && this.getEsconderCancelar().booleanValue()){
					writer.println("<input style=\"display:none;\" name=\"btnCancelar\" type=\"button\" value=\"" + this.getCancelar() + "\" class=\"botao\" onclick=\"aoClicarCancelar(form);\">");
				}else {
					if (getControlaFluxo()!= null && getControlaFluxo()) {
						writer.println("<input name=\"btnCancelar\" type=\"button\" value=\"" + this.getCancelar() + "\" class=\"botao\" onclick=\"aoClicarControlaFluxo(form,'cancelar');\">");
					} else {
						writer.println("<input name=\"btnCancelar\" type=\"button\" value=\"" + this.getCancelar() + "\" class=\"botao\" onclick=\"aoClicarCancelar(form);\">");
					}
				}
				imprimiu = true;
			}
			
			if (this.getVoltar() != null){
				writer.println(imprimiu ? DIVISAO : "");
				writer.println("<input name=\"btnVoltar\" type=\"button\" value=\"" + this.getVoltar() + "\" class=\"botao\" onclick=\"aoClicarVoltar(form);\">");
				imprimiu = true;
			}
			
			if (this.getBtn4() != null && (this.getExibirBtn4() == null || this.getExibirBtn4().booleanValue()) ) {
				writer.println(imprimiu ? DIVISAO : "");
				writer.println("<input name=\"btn4\" type=\"button\" value=\"" + this.getBtn4() + "\" class=\"botao\" onclick=\"aoClicarBtn4(form);\">");
				imprimiu = true;
			}

			writer.println("</td>");
			if(this.getTextoDireita() != null && !"".equals(this.getTextoDireita())) {
				writer.println("<td class=\"texto\" align=\"right\">");
				writer.println(this.getTextoDireita());			
				writer.println("<td>");
			}
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
         * @param alterar
         * @author N/C
	 * @since N/C
	 * @version N/C
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
	 * @author N/C
	 * @since N/C
	 * @version N/C
         * @param excluir
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
         * @param incluir
         * @author N/C
	 * @since N/C
	 * @version N/C
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
	 * @author N/C
	 * @since N/C
	 * @version N/C
         * @param pesquisar
	 */
	public void setPesquisar(String pesquisar) {
		this.pesquisar = pesquisar;
	}
	
	/**
	 * Retorna String cancelar.<br>
	 * 
	 * @author N/C
	 * @since N/C
	 * @version N/C
	 * @return String
	 */
	public String getCancelar() {
		return cancelar;
	}
	
	/**
	 * Atribui valor especificado para String cancelar.<br>
	 * 
	 * @author N/C
	 * @since N/C
	 * @version N/C
         * @param cancelar
	 */
	public void setCancelar(String cancelar) {
		this.cancelar = cancelar;
	}
	
	/**
	 * Retorna String voltar.<br>
	 * 
	 * @author N/C
	 * @since N/C
	 * @version N/C
	 * @return String
	 */
	public String getVoltar() {
		return voltar;
	}
	
	/**
	 * Atribui valor especificado para String voltar.<br>
	 * 
	 * @author N/C
	 * @since N/C
	 * @version N/C
         * @param voltar
	 */
	public void setVoltar(String voltar) {
		this.voltar = voltar;
	}
	
	/**
	 * Retorna String navegacao.<br>
	 * 
	 * @author N/C
	 * @since N/C
	 * @version N/C
	 * @return String
	 */
	public String getNavegacao() {
		return navegacao;
	}
	
	/**
	 * Atribui valor especificado para String navegacao.<br>
	 * 
	 * @author N/C
	 * @since N/C
	 * @version N/C
         * @param navegacao
	 */
	public void setNavegacao(String navegacao) {
		this.navegacao = navegacao;
	}
	
	/**
	 * Retorna String btn1.<br>
	 * 
	 * @author N/C
	 * @since N/C
	 * @version N/C
	 * @return String - (Returns the btn1)
	 */
	public String getBtn1() {
		return btn1;
	}
	
	/**
	 * Atribui valor especficado para String btn1.<br>
	 * 
	 * @author N/C
	 * @since N/C
	 * @version N/C
         * @param btn1
	 */
	public void setBtn1(String btn1) {
		this.btn1 = btn1;
	}
	
	/**
	 * Retorna String btn2.<br>
	 * 
	 * @author N/C
	 * @since N/C
	 * @version N/C
	 * @return String - (Returns the btn2)
	 */
	public String getBtn2() {
		return btn2;
	}
	
	/**
	 * Atribui valor especificado para String btn2.<br>
	 * 
         * @param btn2
         * @author N/C
	 * @since N/C
	 * @version N/C
	 */
	public void setBtn2(String btn2) {
		this.btn2 = btn2;
	}
	
	/**
	 * Retorna String btn3.<br>
	 * 
	 * @author N/C
	 * @since N/C
	 * @version N/C
	 * @return String - (Returns the btn3)
	 */
	public String getBtn3() {
		return btn3;
	}
	
	/**
	 * Atribui valor especificado para String btn3.<br>
	 * 
         * @param btn3
         * @author N/C
	 * @since N/C
	 * @version N/C
	 */
	public void setBtn3(String btn3) {
		this.btn3 = btn3;
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
     * @param atual
     * @author N/C
	 * @since N/C
	 * @version N/C
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
     * @param total
     * @author N/C
	 * @since N/C
	 * @version N/C
     */
    public void setTotal(int total) {
        this.total = total;
    }
	
	/**
	 * Retorna Boolean exibirAlterar.<br>
	 * 
	 * @author N/C
	 * @since N/C
	 * @version N/C
	 * @return Boolean - (Returns the exibirAlterar)
	 */
	public Boolean getExibirAlterar() {
		return exibirAlterar;
	}
	
	/**
	 * Atribui valor especificado para Boolean exibirAlterar.<br>
	 * 
         * @param exibirAlterar
         * @author N/C
	 * @since N/C
	 * @version N/C
	 */
	public void setExibirAlterar(Boolean exibirAlterar) {
		this.exibirAlterar = exibirAlterar;
	}
	
	/**
	 * Retorna Boolean exibirBtn1.<br>
	 * 
	 * @author N/C
	 * @since N/C
	 * @version N/C
	 * @return Boolean - (Returns the exibirBtn1)
	 */
	public Boolean getExibirBtn1() {
		return exibirBtn1;
	}
	
	/**
	 * Atribui valor especificado para Boolean exibirBtn1.<br>
	 * 
         * @param exibirBtn1
         * @author N/C
	 * @since N/C
	 * @version N/C
	 */
	public void setExibirBtn1(Boolean exibirBtn1) {
		this.exibirBtn1 = exibirBtn1;
	}
	
	/**
	 * Retorna Boolean exibirBtn2.<br>
	 * 
	 * @author N/C
	 * @since N/C
	 * @version N/C
	 * @return Boolean - (Returns the exibirBtn2)
	 */
	public Boolean getExibirBtn2() {
		return exibirBtn2;
	}
	
	/**
	 * Atribui valor especificado Boolean exibirBtn2.<br>
	 * 
         * @param exibirBtn2
         * @author N/C
	 * @since N/C
	 * @version N/C
	 */
	public void setExibirBtn2(Boolean exibirBtn2) {
		this.exibirBtn2 = exibirBtn2;
	}
	
	
	/**
	 * Retorna Boolean exibirBtn3.<br>
	 * 
	 * @author N/C
	 * @since N/C
	 * @version N/C
	 * @return Boolean - (Returns the exibirBtn3)
	 */
	public Boolean getExibirBtn3() {
		return exibirBtn3;
	}
	
	/**
	 * Atribui valor especificado para Boolean exibirBtn3.<br>
	 * 
         * @param exibirBtn3
         * @author N/C
	 * @since N/C
	 * @version N/C
	 */
	public void setExibirBtn3(Boolean exibirBtn3) {
		this.exibirBtn3 = exibirBtn3;
	}
	
	
	/**
	 * Retorna Boolean exibirExcluir.<br>
	 * 
	 * @author N/C
	 * @since N/C
	 * @version N/C
	 * @return Boolean - (Returns the exibirExcluir)
	 */
	public Boolean getExibirExcluir() {
		return exibirExcluir;
	}
	
	/**
	 * Atribui valor especificado para Boolean exibirExcluir.<br>
	 * 
         * @param exibirExcluir
         * @author N/C
	 * @since N/C
	 * @version N/C
	 */
	public void setExibirExcluir(Boolean exibirExcluir) {
		this.exibirExcluir = exibirExcluir;
	}
	
	/**
	 * Retorna Boolean exibirIncluir.<br>
	 * 
	 * @author N/C
	 * @since N/C
	 * @version N/C
	 * @return Boolean - (Returns the exibirIncluir)
	 */
	public Boolean getExibirIncluir() {
		return exibirIncluir;
	}
	
	/**
	 * Atribui valor especificado para Boolean exibirIncluir.<br>
	 * 
         * @param exibirIncluir
         * @author N/C
	 * @since N/C
	 * @version N/C
	 */
	public void setExibirIncluir(Boolean exibirIncluir) {
		this.exibirIncluir = exibirIncluir;
	}
	
	/**
	 * Retorna Boolean esconderCancelar.<br>
	 * 
	 * @author N/C
	 * @since N/C
	 * @version N/C
	 * @return Boolean - (Returns the esconderCancelar)
	 */
	public Boolean getEsconderCancelar() {
		return esconderCancelar;
	}
	
	/**
	 * Atribui valor especificado para Boolean esconderCancelar.<br>
	 * 
         * @param esconderCancelar
         * @author N/C
	 * @since N/C
	 * @version N/C
	 */
	public void setEsconderCancelar(Boolean esconderCancelar) {
		this.esconderCancelar = esconderCancelar;
	}
	
	/**
	 * Retorna String btn4.<br>
	 * 
	 * @author N/C
	 * @since N/C
	 * @version N/C
	 * @return String - (Returns the btn4)
	 */
	public String getBtn4() {
		return btn4;
	}
	
	/**
	 * Atribui valor especificado para String btn4.<br>
	 * 
	 * @author N/C
	 * @since N/C
	 * @version N/C
         * @param btn4
	 */
	public void setBtn4(String btn4) {
		this.btn4 = btn4;
	}
	
	/**
	 * Retorna Boolean exibirBtn4.<br>
	 * 
	 * @author N/C
	 * @since N/C
	 * @version N/C
	 * @return Boolean - (Returns the exibirBtn4)
	 */
	public Boolean getExibirBtn4() {
		return exibirBtn4;
	}
	
	/**
	 * Atribui valor especificado Boolean exibirBtn4.<br>
	 * 
	 * @author N/C
	 * @since N/C
	 * @version N/C
         * @param exibirBtn4
	 */
	public void setExibirBtn4(Boolean exibirBtn4) {
		this.exibirBtn4 = exibirBtn4;
	}

	/**
	 * Controla o fluxo do botão
	 * @return
	 */
	public Boolean getControlaFluxo() {
		return controlaFluxo;
	}

	public void setControlaFluxo(Boolean controlaFluxo) {
		this.controlaFluxo = controlaFluxo;
	}

}