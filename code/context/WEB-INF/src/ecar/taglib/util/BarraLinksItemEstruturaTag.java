/*
 * Criado em 14/12/2004
 *
 */
package ecar.taglib.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.Tag;
import javax.servlet.jsp.tagext.TagSupport;

import comum.util.Util;

import ecar.dao.ItemEstruturaDao;
import ecar.dao.PontoCriticoDao;
import ecar.pojo.EstruturaEtt;
import ecar.pojo.EstruturaFuncaoEttf;
import ecar.pojo.ItemEstruturaIett;

/**
 * @author felipev
 *  
 */
public class BarraLinksItemEstruturaTag extends TagSupport {
	private static final long serialVersionUID = -4120193640057668093L;

	private EstruturaEtt estrutura;
    private String selectedFuncao;
    private String codItemEstrutura;
    private Boolean desabilitarLinks;
    private String contextPath;
    private String idLinhaCadastro;

    /**
     *
     * @return
     */
    public String getIdLinhaCadastro() {
		return idLinhaCadastro;
	}

    /**
     *
     * @param idLinhaCadastro
     */
    public void setIdLinhaCadastro(String idLinhaCadastro) {
		this.idLinhaCadastro = idLinhaCadastro;
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
        try {
        	StringBuffer s = new StringBuffer();
            s.append("<table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" id=\"abas\"><tr><td>");
            List funcoes = new ArrayList( getEstrutura().getEstruturaFuncaoEttfs());
            Collections.sort(funcoes, new Comparator(){
	            public int compare(Object arg0, Object arg1) {
					EstruturaFuncaoEttf ef1 = (EstruturaFuncaoEttf) arg0;
					EstruturaFuncaoEttf ef2 = (EstruturaFuncaoEttf) arg1;
					
					Integer seq1 = ef1.getSeqApresentacaoTelaEttf();
					Integer seq2 = ef2.getSeqApresentacaoTelaEttf();
					
					if(seq1 == null && seq2 == null)
						return 0;
					if(seq1 != null && seq2 == null)
						return 1;
					if(seq1 == null && seq2 != null)
						return -1;
					return ef1.getSeqApresentacaoTelaEttf().compareTo(ef2.getSeqApresentacaoTelaEttf());
	            }
            });

//            //Aba Fixa: Dados Gerais
//            if(getSelectedFuncao() != null && !"".equals(getSelectedFuncao()))
//            	s.append("<table class=\"abadesabilitada\"><tr><td nowrap>");
//            else
//            	s.append("<table class=\"abahabilitada\"><tr><td nowrap>");
//            
//            if (desabilitarLinks == null || desabilitarLinks.booleanValue() == false) {
//                s.append("<a href=\"../dadosGerais/frm_con.jsp?")
//                 .append("codIett=").append(getCodItemEstrutura()).append("\">");
//            }
//            s.append("Dados Gerais");
//            if (desabilitarLinks == null || desabilitarLinks.booleanValue() == false)
//                s.append("</a>");
//            
//            s.append("</td></tr></table>");
//            // Fim Dados Gerais
            
            
            Iterator it = funcoes.iterator();
            
            while (it.hasNext()) {
                EstruturaFuncaoEttf estruturaFuncao = (EstruturaFuncaoEttf) it.next();
                if (!"N".equals(estruturaFuncao.getFuncaoFun().getIndOpcionalFun())
                        && !"N".equals(estruturaFuncao.getFuncaoFun().getIndAtivoFun())
                        && estruturaFuncao.getFuncaoFun().getLinkFuncaoFun() != null
                        && !estruturaFuncao.getFuncaoFun().getNomeFun().equals("Apontamentos")) {
                		//Conforme solicitado, a função de apontamentos não deve ser mostrada como uma aba,
                		//ela deve ser acessada apenas pela função de pontos críticos
                    String tipoAba = "abadesabilitada";
                    
                    if (!"".equals(getSelectedFuncao())) {
                        if (estruturaFuncao.getFuncaoFun().getCodFun().equals(Long.valueOf(getSelectedFuncao()))) {
                        	tipoAba = "abahabilitada";
                        }
                    }
                   if ("Pontos Críticos".equals(estruturaFuncao.getLabelEttf())) {
		            	// verificação dos pontos críticos
			        	try {
			        		ItemEstruturaDao itemEstruturaDao = new ItemEstruturaDao(null);
			        		ItemEstruturaIett itemEstrutura = (ItemEstruturaIett) itemEstruturaDao.buscar(ItemEstruturaIett.class, Long.valueOf(getCodItemEstrutura()));
			        		PontoCriticoDao pontoCriticoDao = new PontoCriticoDao(null);
			    	    	Collection pontosCriticos = Collections.EMPTY_LIST;
			    	    	pontosCriticos = pontoCriticoDao.getAtivos(itemEstrutura);
			    	    	
			    	    	if (pontoCriticoDao.verificaDatasUltrapassadas(pontosCriticos)) {
			    	    		tipoAba = "abapontocritico"; 
			    	    	}
			        	} catch (Exception e) {
			        		tipoAba = "abadesabilitada"; 
			        	}
			            //fim - verificação dos pontos críticos
		            }
                    
                    s.append("<table class=\"").append(tipoAba).append("\"><tr>");
                	s.append("<td nowrap>");
                	
                    if (desabilitarLinks == null || desabilitarLinks.booleanValue() == false) {
                        s.append("<a href=\"../").append(estruturaFuncao.getFuncaoFun().getLinkFuncaoFun())
                         .append("?codAba=").append(estruturaFuncao.getFuncaoFun().getCodFun()).append("&codIett=")
                         .append(getCodItemEstrutura()).append("&ultimoIdLinhaDetalhado=").append(getIdLinhaCadastro()).append("\">");
                    }
                    //s.append(estruturaFuncao.getFuncaoFun().getLabelPadraoFun());
                    s.append(estruturaFuncao.getLabelEttf());
                    if (desabilitarLinks == null || desabilitarLinks.booleanValue() == false)
                        s.append("</a>");
                    
                    // Mantis #8688. Por Rogério (22/03/2007)
                    // Aplica a dica com imagem pra chamar a atenção do usuário.
                    if (estruturaFuncao.getDicaEttf() != null && !"".equals(estruturaFuncao.getDicaEttf()) )
                    	s.append(Util.getTagDica(estruturaFuncao.getFuncaoFun().getNomeFun(), this.getContextPath(), estruturaFuncao.getDicaEttf()));
                   	
                    s.append("</td></tr></table>");
                }

            }
            s.append("</td></tr></table>");
            writer.print(s.toString());
        } catch (Exception e) {
        	org.apache.log4j.Logger.getLogger(this.getClass()).error(e);
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
        /* processa o restante da página jsp */
        return Tag.EVAL_PAGE;
    }

    /**
     * Retorna String selectedFuncao.<br>
     * 
     * @author N/C
	 * @since N/C
	 * @version N/C
     * @return String - (Returns the selectedEstrutura)
     */
    public String getSelectedFuncao() {
        return selectedFuncao;
    }

    /**
     * Atribui valor especificado para String selectedFuncao.<br>
     * 
     * @author N/C
	 * @since N/C
	 * @version N/C
     * @param selectedEstrutura
     */
    public void setSelectedFuncao(String selectedEstrutura) {
        this.selectedFuncao = selectedEstrutura;
    }

    /**
     * Retorna EstruturaEtt estrutura.<br>
     * 
     * @author N/C
	 * @since N/C
	 * @version N/C
     * @return EstruturaEtt - (Returns the estrutura)
     */
    public EstruturaEtt getEstrutura() {
        return estrutura;
    }

    /**
     * Atribui valor especificado para EstruturaEtt estrutura.<br>
     * 
     * @param estrutura
     * @author N/C
	 * @since N/C
	 * @version N/C
     */
    public void setEstrutura(EstruturaEtt estrutura) {
        this.estrutura = estrutura;
    }

    /**
     * Retorna String codItemEstrutura.<br>
     * 
     * @author N/C
	 * @since N/C
	 * @version N/C
     * @return String - (Returns the codItemEstrutura)
     */
    public String getCodItemEstrutura() {
        return codItemEstrutura;
    }

    /**
     * Atribui valor especificado para String codItemEstrutura.<br>
     * 
     * @param codItemEstrutura
     * @author N/C
	 * @since N/C
	 * @version N/C
     */
    public void setCodItemEstrutura(String codItemEstrutura) {
        this.codItemEstrutura = codItemEstrutura;
    }

    /**
     * Retorna Boolean desabilitarLinks.<br>
     * 
     * @author N/C
	 * @since N/C
	 * @version N/C
     * @return Boolean - (Returns the desabilitarLinks)
     */
    public Boolean getDesabilitarLinks() {
        return desabilitarLinks;
    }

    /**
     * Atribui valor especificado para Boolean desabilitarLinks.<br>
     * 
     * @author N/C
	 * @since N/C
	 * @version N/C
     * @param desabilitarLinks
     */
    public void setDesabilitarLinks(Boolean desabilitarLinks) {
        this.desabilitarLinks = desabilitarLinks;
    }
    
    /**
     * Retorna String contextPath.<br>
     * 
     * @author N/C
	 * @since N/C
	 * @version N/C
     * @return String
     */
    public String getContextPath() {
		return contextPath;
	}

    /**
     * Atribui valor especificado para String contextPath.<br>
     * 
     * @author N/C
	 * @since N/C
	 * @version N/C
     * @param contextPath
     */
	public void setContextPath(String contextPath) {
		this.contextPath = contextPath;
	}
}