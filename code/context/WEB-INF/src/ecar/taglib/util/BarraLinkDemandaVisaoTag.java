/*
 * Criado em 06/04/2005
 *
 */
package ecar.taglib.util;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.Tag;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.log4j.Logger;

import ecar.exception.ECARException;
import ecar.pojo.RegDemandaRegd;

/**
 * @author felipev
 *
 */
public class BarraLinkDemandaVisaoTag extends TagSupport{

	private static final long serialVersionUID = 2869225621544161026L;

    private static final String ABA_DEMANDA_VISAO_HABILITADA = "abatipoacompanhamentohabilitada";
    private static final String ABA_DEMANDA_VISAO_DESABILITADA = "abatipoacompanhamentodesabilitada";
    
    private String chamarPagina;
    private String tabAtual;
    private String codRegd;
    
    private RegDemandaRegd regDemanda;
    
    /**
     *
     */
    public static final String TAB_DADOS_GERAIS   = "Dados Gerais";
    /**
     *
     */
    public static final String TAB_ENCAMINHAMENTO = "Encaminhamento";
    /**
     *
     */
    public static final String TAB_ITENS_RELACIONADOS = "Itens Relacionados";
    /**
     *
     */
    public static final String TAB_ANEXOS = "Anexos";
    private String[] labelsTab;// = {TAB_DADOS_GERAIS, TAB_ENCAMINHAMENTO, TAB_ITENS_RELACIONADOS, TAB_ANEXOS};
    

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
        inicializaLabelsTab();        
        try {
            StringBuffer s = new StringBuffer();

            s.append("<table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" id=\"abastipoacompanhamento\">")
             .append("<tr>")
             .append("<td class=\"texto_negrito\">");
    			
        	String situacaoAba = ABA_DEMANDA_VISAO_DESABILITADA;
        	for(int i=0;i<labelsTab.length;i++) {
        		if(tabAtual.equals(labelsTab[i]))
        			situacaoAba = ABA_DEMANDA_VISAO_HABILITADA;
        		else
        			situacaoAba = ABA_DEMANDA_VISAO_DESABILITADA;
        		criaAba(labelsTab[i], getChamarPagina() + "?tabAtual=" +  labelsTab[i] + "&codRegd=" + codRegd, situacaoAba, s);        				
        	}
        	s.append("</td>")
        	.append("</tr>")
        	.append("</table>");
            writer.print(s.toString());
            
        } catch (IOException e) {
        	Logger.getLogger(this.getClass()).error(e);
        }

        return Tag.SKIP_BODY;

    }
    
        /**
         *
         */
        public void inicializaLabelsTab() {
		
	    if(regDemanda.getItemRegdemandaIregds().size() > 0){
	    	String[] labels	= {TAB_DADOS_GERAIS, TAB_ENCAMINHAMENTO, TAB_ITENS_RELACIONADOS, TAB_ANEXOS};
	    	labelsTab = labels;
	    } else{
	    	String[] labels	= {TAB_DADOS_GERAIS, TAB_ENCAMINHAMENTO, TAB_ANEXOS};
	    	labelsTab = labels;
	    }
	}

	
	/**
	 * Cria Aba.<br>
	 * 
	 * @author N/C
	 * @since N/C
	 * @version N/C
	 * @param String label
	 * @param String pagina
	 * @param String situacaoAba
	 * @param StringBuffer s
	 * @throws ECARException
	 */
    private void criaAba(String label, String pagina, String situacaoAba, StringBuffer s){
        s.append("<table class=\"").append(situacaoAba).append("\">")
         .append("<tr>")
         .append("<td nowrap>");
        s.append("<a href=\"javascript:aoClicarTrocarAba(form,'" + pagina + "');").append("\">");

		s.append(label);
		
		s.append("</a>");
        s.append("</td>")
         .append("</tr>")
         .append("</table>");            
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
	 * Retorna String chamarPagina.<br>
	 * 
	 * @author N/C
	 * @since N/C
	 * @version N/C
	 * @return String
	 */
    public String getChamarPagina() {
		return chamarPagina;
	}

    /**
     * Atribui valor especificado para String chamarPagina.<br>
     * 
     * @param chamarPagina
     * @author N/C
	 * @since N/C
	 * @version N/C
     */
	public void setChamarPagina(String chamarPagina) {
		this.chamarPagina = chamarPagina;
	}

        /**
         *
         * @return
         */
        public String getTabAtual() {
		return tabAtual;
	}

        /**
         *
         * @param tabAtual
         */
        public void setTabAtual(String tabAtual) {
		this.tabAtual = tabAtual;
	}

        /**
         *
         * @return
         */
        public String getCodRegd() {
		return codRegd;
	}

        /**
         *
         * @param codRegd
         */
        public void setCodRegd(String codRegd) {
		this.codRegd = codRegd;
	}

        /**
         *
         * @return
         */
        public RegDemandaRegd getRegDemanda() {
		return regDemanda;
	}

        /**
         *
         * @param regDemanda
         */
        public void setRegDemanda(RegDemandaRegd regDemanda) {
		this.regDemanda = regDemanda;
	}

	
	
	
	
}