/*
 * Criado em 16/02/2005
 */ 
package ecar.taglib.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import ecar.dao.AbaDao;
import ecar.dao.AcompRelatorioDao;
import ecar.dao.EstruturaDao;
import ecar.dao.PontoCriticoDao;
import ecar.exception.ECARException;
import ecar.permissao.ValidaPermissao;
import ecar.pojo.Aba;
import ecar.pojo.AcompReferenciaAref;
import ecar.pojo.AcompReferenciaItemAri;
import ecar.pojo.EstruturaFuncaoEttf;
import ecar.pojo.UsuarioUsu;
import ecar.util.Dominios;

/** 
 * @author felipev
 *
 */
public class BarraLinksRegAcompTag extends TagSupport{
    
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
    private String abaSuperior=Dominios.NAO;
    private AcompReferenciaAref acompReferencia;
    private String listaGeral;
    private String telaDoItem;
    private String caminho;
    private String tipoFiltroEscolhido;
   
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
        	
        	s.append("<table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" id=\"abas\"><tr><td>");
			
			/* Aba de resumo é fixa, não está no banco */
        	String situacaoAba = "abadesabilitada";
            AbaDao abaDao = new AbaDao(null);
			
    
            List listAbasComAcesso = null;
            
            
            if (abaSuperior != null && abaSuperior.equals("S")) {
            	if(acompReferencia != null) {
            		listAbasComAcesso = abaDao.getListaAbasSuperiorComAcesso(acompReferencia.getTipoAcompanhamentoTa(), gruposUsuario);
            	} else if(acompReferenciaItem != null){
            		listAbasComAcesso = abaDao.getListaAbasSuperiorComAcesso(acompReferenciaItem.getAcompReferenciaAref().getTipoAcompanhamentoTa(), gruposUsuario);
            	} else {
            		listAbasComAcesso = null;
            	}
            	
            	montarAbasSuperior(listAbasComAcesso, situacaoAba, s);
            	
            
            } else {
            	
            	listAbasComAcesso = abaDao.getListaAbasComAcesso(acompReferenciaItem.getAcompReferenciaAref().getTipoAcompanhamentoTa(), gruposUsuario);
            	montarAbasItem(listAbasComAcesso, situacaoAba, s);
            }
            
       
            s.append("</td></tr></table>");
            writer.print(s.toString());
        } catch (Exception e) {
        	e.printStackTrace();
        }
        return BarraLinksRegAcompTag.SKIP_BODY;

    }
	
	
	/**
	 * Monta as abas do item.<br>
	 * 
	 * @author N/C
	 * @since N/C
	 * @version N/C
	 * @param List listAbasComAcesso
	 * @param String situacaoAba
	 * @param StringBuffer s
	 * @throws ECARException
	 */
	private void montarAbasItem(List listAbasComAcesso, String situacaoAba, StringBuffer s) throws ECARException {
		
		AbaDao abaDao = new AbaDao(request);
		String linkPrimeiro = "";
		String link="";
		if (listAbasComAcesso ==null)
	         	listAbasComAcesso =new ArrayList<Aba>();
		
		// NÃO MAIS FIXA NO CODIGO- LEONARDO RIBEIRO
		/***************ABA RESUMO - HOJE FIXA NO CODIGO*********************/
//	    if("RESUMO".equals(selectedFuncao))
//        	situacaoAba = "abahabilitada";
//         
//        if(!"".equals(primeiroAcomp))
//        	linkPrimeiro = "primeiroAcomp=" + primeiroAcomp + "&";
//            
//        link = request.getContextPath() + PATH_ACOMP + "listaItens.jsp?" + linkPrimeiro + "codAcomp=" + 
//            	acompReferenciaItem.getAcompReferenciaAref().getCodAref()+"&";
           
//        if(getTela() != null && !getTela().equals("") && getTela().equals("acompanhamento")) {
//				//entra aqui no caso de ser chamado pela nova tela de monitoramento
//            	link = "javaScript:trocarAba('../resumo/detalharItem.jsp');";
//            	criaAbaMonitoramento(situacaoAba, link, "Resumo", s);
//        } else {
//            	criaAba(situacaoAba, link, "Resumo", s);
//        }
    			
	         
        /***************ABAS CADASTRADAS NO BANCO *********************/
	    for  (Iterator it = listAbasComAcesso.iterator();it.hasNext();) {
	    	 Aba aba = (Aba) it.next();
	    	 
	    	 boolean possuiAba = false;
	    	 if (acompReferenciaItem != null){
	    	        if(aba.getFuncaoFun()!= null){        	
	    	        	EstruturaDao estruturaDao = new EstruturaDao(request);
	    	        	Set listaFuncoes = acompReferenciaItem.getItemEstruturaIett().getEstruturaEtt().getEstruturaFuncaoEttfs();
	    	        	Iterator itListaFuncoes = listaFuncoes.iterator();
	    	        	while(itListaFuncoes.hasNext()){
	    	        		EstruturaFuncaoEttf funcao = (EstruturaFuncaoEttf) itListaFuncoes.next();
	    	        		if(aba.getFuncaoFun().getCodFun().equals(funcao.getFuncaoFun().getCodFun())){
	    	        			possuiAba = true;
	    	        			break;
	    	        		}
	    	        	}
	    	        } else{
	    	        	possuiAba = true;
	    	        }
	    	 }
	    	 
	    	// Mantis 0011550: Ocultar a aba de parecer se o usuário não possuir permissão para visualização
	    	 if("SITUACAO".equals(aba.getNomeAba())){
	    		    
	    		    ValidaPermissao validaPermissao = new ValidaPermissao();
					//Obtem a lista das funções de acompanhamento que podem visualizar pareceres.
	            	List listaPermissaoTpfa = validaPermissao.permissaoVisualizarPareceres(acompReferenciaItem.getAcompReferenciaAref().getTipoAcompanhamentoTa(),gruposUsuario/* seguranca.getGruposAcesso()*/);
					
					AcompRelatorioDao arelDao = new AcompRelatorioDao(request);
					//conta os pareceres que existem das funcoes de acompanhamento que podem visualizar pareceres. 
	            	if (arelDao.ContaArelsDasFuncoesDoAri(acompReferenciaItem, listaPermissaoTpfa) == 0){
	            		possuiAba = false;	
	            	}
	    	 }
	    	 
	    	 
	    	 if (!aba.getNomeAba().equals("SITUACAO_INDICADORES") && possuiAba) {
	    		 
				 String labelAba;
				 link = "";
				 situacaoAba = "abadesabilitada"; 
				  
				 if (acompReferenciaItem != null){
					labelAba = abaDao.getLabelAbaEstrutura(aba, acompReferenciaItem.getItemEstruturaIett().getEstruturaEtt());
				 } else {
					 labelAba = aba.getLabelAba();
				 }
					 
		     	
				 //habilita a aba
				 if (selectedFuncao.equals(aba.getNomeAba())) {
					situacaoAba = "abahabilitada";
				 }
					
				 
				 if ("PONTOS_CRITICOS".equals(aba.getNomeAba())) {
					// verificação dos pontos críticos
					try {
						PontoCriticoDao pontoCriticoDao = new PontoCriticoDao(null);
						Collection pontosCriticos = Collections.EMPTY_LIST;
						pontosCriticos = pontoCriticoDao
										.getPontosCriticosNaoSolucionados(acompReferenciaItem
												.getItemEstruturaIett());
	
						if (pontoCriticoDao.verificaDatasUltrapassadas(pontosCriticos)) {
							situacaoAba = "abapontocritico";
						}
					
					} catch (Exception e) {
						situacaoAba = "abadesabilitada";
					}
					// fim - verificação dos pontos críticos
				}
					
						
						
				if(getTela() != null && !getTela().equals("") && getTela().equals("acompanhamento")) {
					//entra aqui no caso de ser chamado pela nova tela de monitoramento
					link = "";
							
					/***************ABA DADOS GERAIS*********************/
					if ("DADOS_GERAIS".equals(aba.getNomeAba())) {
						link += "javaScript:trocarAba('../dadosGerais/frm_con.jsp');";
					
					/***************ABA EVENTOS*********************/
					} else if ("EVENTOS".equals(aba.getNomeAba())) {
						link += "javaScript:trocarAba('../realizacoes/eventos.jsp');";						
					
					/***************ABA PONTOS CRITICOS*********************/	
					} else if ("PONTOS_CRITICOS".equals(aba.getNomeAba())) {
						link += "javaScript:trocarAba('../restricoes/pontosCriticos.jsp');";
					
					/***************ABA SITUACAO PONTOS CRITICOS*********************/	
					} else if(aba.getNomeAba().equals("SITUACAO_PONTOS_CRITICOS")) {
						link += "javaScript:trocarAba('../situacaoDatas/situacaoDatas.jsp');";
					
					/***************ABA GALERIA*********************/	
					} else if ("GALERIA".equals(aba.getNomeAba())) {
						link += "javaScript:trocarAba('../galeria/galeria.jsp');";
							
					/***************ABA FINANCEIRO*********************/
					} else if ("FINANCEIRO".equals(aba.getNomeAba())) {
						link += "javaScript:trocarAba('../financeiro/financeiro.jsp');";
							
					/***************ABA RESUMO*********************/
					} else if("RELACAO".equals(aba.getNomeAba())){
				       	link += "javascript:voltarTelaAnterior('../resumo/detalharItem.jsp');";
				            	
				    /***************ABA METAS E INDICADORES*********************/	
				    } else if ("REL_FISICO_IND_RESULTADO".equals(aba.getNomeAba())) {
				    	link += "javaScript:trocarAba('../resultado/indicadoresResultado.jsp');";	
								
					/***************ABA SITUACAO E INDICADORES*********************/	
				    } else if (aba.getNomeAba().equals("SITUACAO_INDICADORES")) {
				    	link += "javaScript:trocarAba('../situacaoIndicadores/situacaoIndicadores.jsp');";
											
				    /***************ABA ETAPA*********************/		
				    }	else if ("ETAPA".equals(aba.getNomeAba())) {
				    	link += "javaScript:trocarAba('../etapa/etapas.jsp');";
							
					/***************ABA DATAS LIMITES*********************/	
					} else if ("DATAS_LIMITES".equals(aba.getNomeAba())) {
						link += "javaScript:trocarAba('../datasLimites/datasLimites.jsp');";
							
					/***************ABA SITUACAO*********************/
					} else if("SITUACAO".equals(aba.getNomeAba())){
						link += "javaScript:trocarAba('../situacao/relatorios.jsp');";
							
					/***************ABA GRAFICO DE GANTT*********************/
					} else if("GRAFICO_DE_GANTT".equals(aba.getNomeAba())){
								link += "javaScript:trocarAba('../graficoGantt/graficoGantt.jsp');";
							
					/***************ABA RELATORIO ***************************/
					}	else if("RELATORIO".equals(aba.getNomeAba())){
					  	
						link = "javaScript:trocarAba('../relatorios/relatorioImpresso.jsp?tela=R');";
			       
					
					}	else if("RESUMO".equals(aba.getNomeAba())){
				
			       		link = "javaScript:trocarAba('../resumo/detalharItem.jsp');";
					}
					
				}
				
//				if("RESUMO".equals(aba.getNomeAba())){
//					link = "javaScript:trocarAba('../resumo/detalharItem.jsp;');";
//				}
				
				
				if(getTela() != null && !getTela().equals("") && getTela().equals("acompanhamento")) {
					//entra aqui no caso de ser chamado pela nova tela de monitoramento
					if(!labelAba.equals("")){
						criaAbaMonitoramento(situacaoAba, link, labelAba,s);
					} else {
						labelAba = aba.getLabelAba();
						criaAbaMonitoramento(situacaoAba, link, labelAba,s);
					}
				} else {
					criaAba(situacaoAba, link, aba,s);
				}
	    	 }
		}
	
	}
	
	/**
	 * Monta as abas superiores.<br>
	 * 
	 * @author N/C
	 * @since N/C
	 * @version N/C
	 * @param List listAbasComAcesso
	 * @param String situacaoAba
	 * @param StringBuffer s
	 * @throws ECARException
	 */
	private void montarAbasSuperior(List listAbasComAcesso, String situacaoAba, StringBuffer s) throws ECARException {
		
		AbaDao abaDao = new AbaDao(request);
		
		
		 if (listAbasComAcesso ==null)
         	listAbasComAcesso =new ArrayList<Aba>();
         
    	 for  (Iterator it = listAbasComAcesso.iterator();it.hasNext();) {
    		 Aba aba = (Aba) it.next();
    		 
    		 if (!aba.getNomeAba().equals("SITUACAO_INDICADORES")) {
				 String labelAba;
				 String link = "";
				 String mesReferencia = "";
				 situacaoAba = "abadesabilitada";
				 
				 if (acompReferenciaItem != null){
					labelAba = abaDao.getLabelAbaEstrutura(aba, acompReferenciaItem.getItemEstruturaIett().getEstruturaEtt());
				 } else {
					 labelAba = aba.getLabelAba();
				 }
				 
				 //habilita a aba
				 if (selectedFuncao.equals(aba.getNomeAba())) {
					situacaoAba = "abahabilitada";
				 }
				 
				 
				//Registro ou Visualização
	     		if(telaDoItem != null && (telaDoItem.equals("R") || telaDoItem.equals("V"))) {
	     			situacaoAba = "abadesabilitada";
	     			mesReferencia = acompReferenciaItem.getAcompReferenciaAref().getCodAref().toString();
	     		} else  {
		    		mesReferencia = acompReferencia.getCodAref().toString();
		    	}
				 
	     		
	     		String parametros = "codTipoAcompanhamento="+ this.primeiroAcomp + "&mesReferencia="+ mesReferencia;
	     		String relatorio= request.getParameter("relatorio");
	     		
	     		if(tipoFiltroEscolhido != null && tipoFiltroEscolhido.equals("pesquisaSalva")) { 
					parametros = "formaVisualizacao=" + request.getParameter("formaVisualizacao");
	     		}
	     			
				/***************ABA SITUACAO PONTOS CRITICOS**************/	
				if(aba.getNomeAba().equals("SITUACAO_PONTOS_CRITICOS")) {
					
					link += "javaScript:trocarAba('"+ caminho + "/acompanhamento/posicaoGeral.jsp?relatorio=true&situacaoDatas=true&primeiraChamada=N&"+
									parametros +"');";
				/***************ABA SITUACAO*********************/
				} else if("SITUACAO".equals(aba.getNomeAba())){
					
	        		link += "javaScript:trocarAba('"+ caminho + "/acompanhamento/posicaoGeral.jsp?relatorio=&situacaoDatas=&"+ parametros +"');";
	    		
				/***************ABA SITUACAO INDICADORES*********************/	
				} else if (aba.getNomeAba().equals("SITUACAO_INDICADORES")) {
					
					link += "javaScript:trocarAba('"+ caminho + "/acompanhamento/situacaoIndicadores.jsp?"+ parametros +"');";
				}
				
		
					
				if(getTela() != null && !getTela().equals("") && getTela().equals("acompanhamento")) {
					//entra aqui no caso de ser chamado pela nova tela de monitoramento
					if(!labelAba.equals("")){
						criaAbaMonitoramento(situacaoAba, link, labelAba,s);
					} else {
						labelAba = aba.getLabelAba();
						criaAbaMonitoramento(situacaoAba, link, labelAba,s);
					}
				} else {
					criaAba(situacaoAba, link, aba,s);
				}
    		 } 
    	 }		  
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
		if(situacaoAba != null && (situacaoAba.equals("abadesabilitada") || situacaoAba.equals("abapontocritico"))) {
			s.append("<a href=\"").append(link).append("\">");
	        s.append(label);
	        s.append("</a>");
        } else {
            s.append(label);
        }
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
	
	
	/**
	 * Cria as Aba das Listas<br>
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
	private void criaAbaMonitoramento(String situacaoAba, String link, String aba, StringBuffer s) throws ECARException{	
		
		s.append("<table class=\"").append(situacaoAba).append("\"><tr><td nowrap>");     
		 if(situacaoAba != null && (situacaoAba.equals("abadesabilitada") || situacaoAba.equals("abapontocritico"))) {
	        s.append("<a href=\"").append(link).append("\">");
	        s.append(aba);
	        s.append("</a>");
		 } else {
			s.append(aba);
		 }
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
        return BarraLinksRegAcompTag.EVAL_PAGE;
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
     * @author N/C
	 * @since N/C
	 * @version N/C
     * @param selectedFuncao
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
     * @author N/C
	 * @since N/C
	 * @version N/C
     * @param usuario
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
         * @param acompReferenciaItemSubNivel
         * @author N/C
	 * @since N/C
	 * @version N/C
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
	 * Retorna String caminho.<br>
	 * 
	 * @author N/C
	 * @since N/C
	 * @version N/C
	 * @return String
	 */
	public String getCaminho() {
		return caminho;
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


        /**
         *
         * @return
         */
        public String getAbaSuperior() {
		return abaSuperior;
	}


        /**
         *
         * @param abaSuperior
         */
        public void setAbaSuperior(String abaSuperior) {
		this.abaSuperior = abaSuperior;
	}


        /**
         *
         * @return
         */
        public Set getGruposUsuario() {
		return gruposUsuario;
	}


        /**
         *
         * @return
         */
        public String getListaGeral() {
		return listaGeral;
	}


        /**
         *
         * @param listaGeral
         */
        public void setListaGeral(String listaGeral) {
		this.listaGeral = listaGeral;
	}


        /**
         *
         * @return
         */
        public AcompReferenciaAref getAcompReferencia() {
		return acompReferencia;
	}


        /**
         *
         * @param acompReferencia
         */
        public void setAcompReferencia(AcompReferenciaAref acompReferencia) {
		this.acompReferencia = acompReferencia;
	}


        /**
         *
         * @return
         */
        public String getTelaDoItem() {
		return telaDoItem;
	}

	
        /**
         *
         * @return
         */
        public String getTipoFiltroEscolhido() {
		return tipoFiltroEscolhido;
	}

        /**
         *
         * @param tipoFiltroEscolhido
         */
        public void setTipoFiltroEscolhido(String tipoFiltroEscolhido) {
		this.tipoFiltroEscolhido =  tipoFiltroEscolhido;
	}
	

        /**
         *
         * @param telaDoItem
         */
        public void setTelaDoItem(String telaDoItem) {
		this.telaDoItem = telaDoItem;
	}
	
        /**
         *
         * @param caminho
         */
        public void setCaminho(String caminho) {
		this.caminho = caminho;
	}
	
}
