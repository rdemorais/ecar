/*
 * Criado em 16/02/2005
 */ 
package ecar.taglib.util;

import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import ecar.dao.AbaDao;
import ecar.dao.EstruturaDao;
import ecar.dao.PontoCriticoDao;
import ecar.exception.ECARException;
import ecar.pojo.Aba;
import ecar.pojo.AcompReferenciaItemAri;
import ecar.pojo.EstruturaFuncaoEttf;
import ecar.pojo.UsuarioUsu;

/** 
 * @author felipev
 *
 */
public class BarraLinksRegAcompSuperiorTag extends TagSupport{
    
    /**
	 * 
	 */
	private static final long serialVersionUID = -6728974818698770839L;

	private AcompReferenciaItemAri acompReferenciaItem;
    private UsuarioUsu usuario;
    private String selectedFuncao;
    private AcompReferenciaItemAri acompReferenciaItemSubNivel;
    private String primeiroAcomp;
    private HttpServletRequest request;
    private String tela;
    private Set gruposUsuario;

    
    //private String srcImg;

    private static final String PATH_ACOMP = "/regAcompanhamento/elabAcompanhamento/";


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
        	//srcImg = "";
        	s.append("<table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" id=\"abas\"><tr><td>");
			
			/* Aba de relação dos Itens, é fixa não está no banco */
        	String situacaoAba = "abadesabilitada";
            if("RELACAO".equals(selectedFuncao))
                situacaoAba = "abahabilitada";
            
            String linkPrimeiro = "";
            if(!"".equals(primeiroAcomp))
            	linkPrimeiro = "primeiroAcomp=" + primeiroAcomp + "&";
            
            String link = request.getContextPath() + PATH_ACOMP + "listaItens.jsp?" + linkPrimeiro + "codAcomp=" + acompReferenciaItem.getAcompReferenciaAref().getCodAref()+"&";

            if(getTela() != null && !getTela().equals("") && getTela().equals("acompanhamento")) {
				//entra aqui no caso de ser chamado pela nova tela de monitoramento
            	link = "javascript:voltarTelaAnterior('../posicaoGeral.jsp');";
            	criaAbaMonitoramento(situacaoAba, link, "Relação", s);
            } else {
            	criaAba(situacaoAba, link, "Relação", s);
            }
            /* **** FIM - Aba de relação dos Itens, é fixa não está no banco */
            
            //---------------------------------------------------------------------------
            //situacaoAba = "abadesabilitada";
            //if("PARECER".equals(selectedFuncao))
            //    situacaoAba = "abahabilitada";
            
            //link = request.getContextPath() + PATH_ACOMP + "parecer.jsp?" + linkPrimeiro + "codAcomp=" + acompReferenciaItem.getAcompReferenciaAref().getCodAref()+"&";         
            
            //criaAba(situacaoAba, link, "Parecer", s);
            //---------------------------------------------------------------------------
                        
            AbaDao abaDao = new AbaDao(null);
			
			for  (Iterator it = abaDao.getListaAbasComAcesso(acompReferenciaItem.getAcompReferenciaAref().getTipoAcompanhamentoTa(), gruposUsuario).iterator();it.hasNext();) {
				Aba aba = (Aba) it.next();
				
				if (!"NIVEL_PLANEJAMENTO".equals(aba.getNomeAba())) {
					situacaoAba = "abadesabilitada";

					if (selectedFuncao.equals(aba.getNomeAba())) {
						situacaoAba = "abahabilitada";
					} else if ("PONTOS_CRITICOS".equals(aba.getNomeAba())) {
						// verificação dos pontos críticos
						try {
							PontoCriticoDao pontoCriticoDao = new PontoCriticoDao(
									null);
							Collection pontosCriticos = Collections.EMPTY_LIST;
							pontosCriticos = pontoCriticoDao
									.getPontosCriticosNaoSolucionados(acompReferenciaItem
											.getItemEstruturaIett());

							if (pontoCriticoDao
									.verificaDatasUltrapassadas(pontosCriticos)) {
								situacaoAba = "abapontocritico";
							}
						} catch (Exception e) {
							situacaoAba = "abadesabilitada";
						}
						// fim - verificação dos pontos críticos
					}
					// precisa melhorar isto aqui, é preciso armazenar em alguma
					// estrutura
					if(getTela() != null && !getTela().equals("") && getTela().equals("acompanhamento")) {
						//entra aqui no caso de ser chamado pela nova tela de monitoramento
						link = "";
						if ("DADOS_GERAIS".equals(aba.getNomeAba())) {
							link += "javaScript:trocarAba('../dadosGerais/frm_con.jsp');";
						} else if ("EVENTOS".equals(aba.getNomeAba())) {
							link += "javaScript:trocarAba('../realizacoes/eventos.jsp');";
						} else if ("PONTOS_CRITICOS".equals(aba.getNomeAba())) {
							link += "javaScript:trocarAba('../restricoes/pontosCriticos.jsp');";
						} else if(aba.getNomeAba().equals("SITUACAO_DATAS")) {
							link += "javaScript:trocarAba('../situacaoDatas/situacaoDatas.jsp');";
						} else if ("GALERIA".equals(aba.getNomeAba())) {
							link += "javaScript:trocarAba('../galeria/galeria.jsp');";
						} else if ("FINANCEIRO".equals(aba.getNomeAba())) {
							link += "javaScript:trocarAba('../financeiro/financeiro.jsp');";
						} else if("RELACAO".equals(aba.getNomeAba())){
			            	link += "javascript:voltarTelaAnterior('../detalharItem.jsp');";
			            } else if ("REL_FISICO_IND_RESULTADO".equals(aba.getNomeAba())) {
							link += "javaScript:trocarAba('../resultado/indicadoresResultado.jsp');";
						} else if (aba.getNomeAba().equals("SITUACAO_INDICADORES")) { 
							link += "javaScript:trocarAba('../situacaoIndicadores/situacaoIndicadores.jsp');";
						}	else if ("ETAPA".equals(aba.getNomeAba())) {
							link += "javaScript:trocarAba('../etapa/etapas.jsp');";
						} else if ("DATAS_LIMITES".equals(aba.getNomeAba())) {
							link += "javaScript:trocarAba('../datasLimites/datasLimites.jsp');";
						} else if("SITUACAO".equals(aba.getNomeAba())){
							link += "javaScript:trocarAba('../situacao/relatorios.jsp');";
						}

					} else {
						link = request.getContextPath() + PATH_ACOMP;
						if ("DADOS_GERAIS".equals(aba.getNomeAba())) {
							link += "dadosGerais/frm_con.jsp?" + linkPrimeiro;
						} else if ("EVENTOS".equals(aba.getNomeAba())) {
							link += "realizacoes/eventos.jsp?" + linkPrimeiro;
						} else if ("PONTOS_CRITICOS".equals(aba.getNomeAba())) {
							link += "pontosCriticos.jsp?" + linkPrimeiro;
						} else if ("GALERIA".equals(aba.getNomeAba())) {
							link += "galeria.jsp?" + linkPrimeiro;
						} else if ("FINANCEIRO".equals(aba.getNomeAba())) {
							link += "financeiro.jsp?" + linkPrimeiro;
						} else if("SITUACAO".equals(aba.getNomeAba())){
			            	link += "situacao.jsp?"+linkPrimeiro;
			            } else if ("REL_FISICO_IND_RESULTADO".equals(aba.getNomeAba())) {
							link += "realizadoFisico.jsp?" + linkPrimeiro;
						} else if ("ETAPA".equals(aba.getNomeAba())) {
							link += "Etapas/frm_con.jsp?" + linkPrimeiro;
						} else if ("DATAS_LIMITES".equals(aba.getNomeAba())) {
							link += "datasLimites.jsp?" + linkPrimeiro;
						}		            
						if (acompReferenciaItemSubNivel != null) {
							link += "codAriSubNivel="
									+ acompReferenciaItemSubNivel.getCodAri() + "&";
						}
					}
		            
				}
		            
				// criaAba(situacaoAba, link, aba.getLabelAba(),s);
				if(getTela() != null && !getTela().equals("") && getTela().equals("acompanhamento")) {
					//entra aqui no caso de ser chamado pela nova tela de monitoramento
					criaAbaMonitoramento(situacaoAba, link, aba,s);
				} else {
					criaAba(situacaoAba, link, aba,s);
				}
			}
            
            s.append("</td></tr></table>");
            writer.print(s.toString());
        } catch (Exception e) {
        	e.printStackTrace();
        }
        return BarraLinksRegAcompSuperiorTag.SKIP_BODY;

    }
	
	
	/**
	 * Cria Aba.<br>
	 * 
	 * @author N/C
	 * @since N/C
	 * @version N/C
	 * @param String situacaoAba
	 * @param String link
	 * @param String label
	 * @param StringBuffer s
	 * @throws ECARException
	 */
	private void criaAba(String situacaoAba, String link, String label, StringBuffer s) throws ECARException{
		link += "codAri=" + acompReferenciaItem.getCodAri() + "&codAcomp=" + acompReferenciaItem.getAcompReferenciaAref().getCodAref();
		
		s.append("<table class=\"").append(situacaoAba).append("\"><tr><td nowrap>");            
        s.append("<a href=\"").append(link).append("\">");
        s.append(label);
        s.append("</a>");
        s.append("</td></tr></table>");
    }
	
	private void criaAba(String situacaoAba, String link, Aba aba, StringBuffer s) throws ECARException{
		link += "codAri=" + acompReferenciaItem.getCodAri() + "&codAcomp=" + acompReferenciaItem.getAcompReferenciaAref().getCodAref();
		if (aba.getFuncaoFun() != null) {
			link += "&codAba=" + aba.getFuncaoFun().getCodFun().toString();
		}
		
		s.append("<table class=\"").append(situacaoAba).append("\"><tr><td nowrap>");            
        s.append("<a href=\"").append(link).append("\">");
        s.append(aba.getLabelAba());
        s.append("</a>");
        s.append("</td></tr></table>");
    }
	
	private void criaAbaMonitoramento(String situacaoAba, String link, Aba aba, StringBuffer s) throws ECARException{	
        //Caso exista alguma função associada a aba (na tela de "Exibição de Abas"), o sistema utilizará o label definido para a função na estrutura em questão.
        boolean existeFuncao = false;
        if(aba.getFuncaoFun()!= null){        	
        	EstruturaDao estruturaDao = new EstruturaDao(request);
        	Set listaFuncoes = acompReferenciaItem.getItemEstruturaIett().getEstruturaEtt().getEstruturaFuncaoEttfs();
        	Iterator itListaFuncoes = listaFuncoes.iterator();
        	while(itListaFuncoes.hasNext()){
        		EstruturaFuncaoEttf funcao = (EstruturaFuncaoEttf) itListaFuncoes.next();
        		if(aba.getFuncaoFun().getCodFun().equals(funcao.getFuncaoFun().getCodFun())){
        			s.append("<table class=\"").append(situacaoAba).append("\"><tr><td nowrap>");            
        	        s.append("<a href=\"").append(link).append("\">");
        			s.append(funcao.getLabelEttf());
        			s.append("</a>");
        	        s.append("</td></tr></table>");
        			existeFuncao = true;
        			break;
        		}
        	}
        }
        //Quando não existir função para a aba cadastrada na tela de "Exibição de Abas", o sistema utilizará o label cadastrado na tela de exibição de abas.
        else{
        	s.append("<table class=\"").append(situacaoAba).append("\"><tr><td nowrap>");            
	        s.append("<a href=\"").append(link).append("\">");
        	s.append(aba.getLabelAba());
        	s.append("</a>");
            s.append("</td></tr></table>");
        }
    }
	
	private void criaAbaMonitoramento(String situacaoAba, String link, String aba, StringBuffer s) throws ECARException{	
		s.append("<table class=\"").append(situacaoAba).append("\"><tr><td nowrap>");            
        s.append("<a href=\"").append(link).append("\">");
        s.append(aba);
        s.append("</a>");
        s.append("</td></tr></table>");
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
        return BarraLinksRegAcompSuperiorTag.EVAL_PAGE;
    }

    /**
     *
     * @return
     */
    public String getTela() {
		return tela;
	}


    /**
     *
     * @param tela
     */
    public void setTela(String tela) {
		this.tela = tela;
	}

	/**
     * Retorna AcompReferenciaItemAri acompReferenciaItem.<br>
     * 
     * @author N/C
	 * @since N/C
	 * @version N/C
     * @return AcompReferenciaItemAri - (Returns the acompReferenciaItem)
     */
    public AcompReferenciaItemAri getAcompReferenciaItem() {
        return acompReferenciaItem;
    }
    /**
     * Atribui valor especificado para AcompReferenciaItemAri acompReferenciaItem.<br>
     * 
     * @param acompReferenciaItem
     * @author N/C
	 * @since N/C
	 * @version N/C
     */
    public void setAcompReferenciaItem(AcompReferenciaItemAri acompReferenciaItem) {
        this.acompReferenciaItem = acompReferenciaItem;
    }
    
    /**
     * Retorna valor especificado para String selectedFuncao.<br>
     * 
     * @author N/C
	 * @since N/C
	 * @version N/C
     * @return String - (Returns the selectedFuncao)
     */
    public String getSelectedFuncao() {
        return selectedFuncao;
    }
    /**
     * Atribui valor especificado para String selectedFuncao.<br>
     * 
     * @param selectedFuncao
     * @author N/C
	 * @since N/C
	 * @version N/C
     */
    public void setSelectedFuncao(String selectedFuncao) {
        this.selectedFuncao = selectedFuncao;
    }
    
    /**
     * Retorna UsuarioUsu usuario.<br>
     * 
     * @author N/C
	 * @since N/C
	 * @version N/C
     * @return UsuarioUsu - (Returns the usuario)
     */
    public UsuarioUsu getUsuario() {
        return usuario;
    }
    
    /**
     * Atribui valor especificado para UsuarioUsu usuario.<br>
     * 
     * @param usuario
     * @author N/C
	 * @since N/C
	 * @version N/C
     */
    public void setUsuario(UsuarioUsu usuario) {
        this.usuario = usuario;
    }

    /**
     * Retorna AcompReferenciaItemAri acompReferenciaItemSubNivel.<br>
     * 
     * @author N/C
	 * @since N/C
	 * @version N/C
     * @return AcompReferenciaItemAri
     */
	public AcompReferenciaItemAri getAcompReferenciaItemSubNivel() {
		return acompReferenciaItemSubNivel;
	}

	/**
	 * Atribui valor especificado para AcompReferenciaItemAri acompReferenciaItemSubNivel.<br>
	 * 
	 * @author N/C
	 * @since N/C
	 * @version N/C
         * @param acompReferenciaItemSubNivel
	 */
	public void setAcompReferenciaItemSubNivel(AcompReferenciaItemAri acompReferenciaItemSubNivel) {
		this.acompReferenciaItemSubNivel = acompReferenciaItemSubNivel;
	}

	/**
	 * Retorna String primeiroAcomp.<br>
	 * 
	 * @author N/C
	 * @since N/C
	 * @version N/C
	 * @return String
	 */
	public String getPrimeiroAcomp() {
		return primeiroAcomp;
	}

	/**
	 * Atribui valor especificado para String primeiroAcomp.<br>
	 * 
	 * @author N/C
	 * @since N/C
	 * @version N/C
         * @param primeiroAcomp
	 */
	public void setPrimeiroAcomp(String primeiroAcomp) {
		this.primeiroAcomp = primeiroAcomp;
	}
	
    /**
     * @return HttpServletRequest
     */
    public HttpServletRequest getRequest() {
		return request;
	}

    /**
     * @param request 
     */
    public void setRequest(HttpServletRequest request) {
		this.request = request;
	}


    /**
     *
     * @param gruposUsuario
     */
    public void setGruposUsuario(Set gruposUsuario) {
		this.gruposUsuario = gruposUsuario;
	}
}
