/*
 * Criado em 06/04/2005
 *
 */
package ecar.taglib.util;

import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.Tag;
import javax.servlet.jsp.tagext.TagSupport;

import ecar.dao.AbaDao;
import ecar.dao.AcompRelatorioDao;
import ecar.dao.EstruturaDao;
import ecar.dao.ItemEstruturaDao;
import ecar.dao.PontoCriticoDao;
import ecar.dao.TipoAcompanhamentoDao;
import ecar.exception.ECARException;
import ecar.permissao.ValidaPermissao;
import ecar.pojo.Aba;
import ecar.pojo.AcompReferenciaItemAri;
import ecar.pojo.EstruturaFuncaoEttf;
import ecar.pojo.ItemEstruturaIett;
import ecar.pojo.TipoAcompanhamentoTa;
import ecar.util.Dominios;

/**
 * @author felipev
 *
 */
public class BarraLinksRelatorioAcompanhamentoTag extends TagSupport{

	private static final long serialVersionUID = -6791633584021020042L;
	private String funcaoSelecionada;
	private String links;
    private AcompReferenciaItemAri acompReferenciaItem;
    
    private String primeiroIettClicado;
    private String primeiroAriClicado;

    private String caminho;
    
    private String codTipoAcompanhamento;
    
    private String codRegd;
    
    
    private static final String ABA_HABILITADA = "abahabilitada";
    private static final String ABA_DESABILITADA = "abadesabilitada";
    
    private Set gruposUsuario;
    private String contextPath;
    
    private String listaGeral;
    private HttpServletRequest request;
    
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
        	
        	
        	ItemEstruturaDao itemEstruturaDao = new ItemEstruturaDao(null);
        	TipoAcompanhamentoDao tipoAcompanhamentoDao= new TipoAcompanhamentoDao(null);
	    	PontoCriticoDao pontoCriticoDao = new PontoCriticoDao(null);
	    	AbaDao abaDao = new AbaDao(null);
	    	primeiroIettClicado = this.pageContext.getRequest().getParameter("primeiroIettClicado");
            StringBuffer s = new StringBuffer();
            
            // se vier de demanda	
            Long linkCodDemanda = (Long) this.pageContext.getSession().getAttribute("linkCodDemanda");
            
                       // Variavel usada para que a tag BarraLinksRelatorioAcompanhamento reconheça que 
			// a requisicao para acessar a tela avaliações veio de associacao e nao de registro de demandas
            String ehAssociacao = (String) this.pageContext.getSession().getAttribute("ehAssociacao");
            
            // o teste getAbaSuperior().equals(Dominios.NAO) é feito porque a pagina chama essa barraTag duas vezes
            if (getAbaSuperior().equals(Dominios.NAO) && linkCodDemanda != null && !"".equals(linkCodDemanda)) {
            	
            	//guarda se é associação de demanda 
            	// se exisir, é porque aponta pra associação
            	// se não existir, aponta para registro demanda 
            	String associacaoDemanda =  (String) this.pageContext.getRequest().getAttribute("associacaoDemanda");

            	s.append("<script language=\"JavaScript\">");
            	s.append("function aoClicarAssociacaoDemandas(){");
            	s.append("	document.form.action = \"" + contextPath + "/cadastroItens/associacaoDemandas/frm_con.jsp\";");
            	s.append("	document.form.submit();");
            	s.append("}");
            	s.append("function aoClicarDemandas(){");
            	s.append("	document.form.action = \"" + contextPath + "/demandas/registro/frm_cons.jsp?tabAtual=Itens Relacionados&codRegd=" + codRegd + "\";");
            	s.append("	document.form.submit();");
            	s.append("}");
            	s.append("</script>");
            	s.append("<table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\">");
            	s.append("<tr><td align=\"right\">");
             	// se for voltar pra associação de demandas
             	if(associacaoDemanda != null && associacaoDemanda.equals("V")) {
             		s.append("<a href=\"#\" onclick=\"aoClicarAssociacaoDemandas();\">[ Voltar para Associação de Demandas ]</a>");
            		// variaves necessarias para voltar para associacao de demanda
            		String codAbaDemanda = (String) this.pageContext.getSession().getAttribute("codAbaDemanda");
            		String codIettDemanda = (String) this.pageContext.getSession().getAttribute("codIettDemanda");
            		// guarda no hidden
            		s.append("<input type=\"hidden\" name=\"codAbaDemanda\" value=\"");
                	s.append(codAbaDemanda);
                	s.append("\">");
                	s.append("<input type=\"hidden\" name=\"codIettDemanda\" value=\"");
                	s.append(codIettDemanda);
                	s.append("\">");
            	} else {
            		//se for voltar par registro de demandas
            		s.append("<a href=\"#\" onclick=\"aoClicarDemandas();\">[ Voltar para Demandas ]</a>");
            	}
            	s.append("</td></tr>");
            	s.append("</table>");
            	s.append("\n");
            	s.append("<input type=\"hidden\" name=\"codRegd\" value=\"" +linkCodDemanda.toString() + "\">" );
            	s.append("\n");
            }
            
        	
            
        	s.append("<table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" id=\"abas\"><tr><td>");
        	
        	String codItemPai = "";
        	
        	TipoAcompanhamentoTa tipoAcompanhamento = (TipoAcompanhamentoTa) tipoAcompanhamentoDao.buscar(TipoAcompanhamentoTa.class, Long.valueOf(getCodTipoAcompanhamento()));
        	Long longPrimeiroItemClicado = (primeiroIettClicado != null && primeiroIettClicado != "")?Long.valueOf(primeiroIettClicado):null;
        	
        	if(tipoAcompanhamento != null && tipoAcompanhamento.getEstruturaEtt() != null && longPrimeiroItemClicado != null){
            	ItemEstruturaIett itemFilho = (ItemEstruturaIett) itemEstruturaDao.buscar(ItemEstruturaIett.class, longPrimeiroItemClicado);
            	if(itemFilho != null && itemFilho.getItemEstruturaIett() != null){
            		codItemPai = itemFilho.getItemEstruturaIett().getCodIett().toString();
            	}
            	if("".equals(codItemPai)) {
            		codItemPai = itemFilho.getCodIett().toString();
            	}
        	}
            
            String pagina = "";
            String situacaoAba = "";
            
     
            
            List<Aba> listAbas = null;
            
            if (getAbaSuperior().equals(Dominios.SIM)) {
            	listAbas = abaDao.getListaAbasSuperiorComAcesso(tipoAcompanhamento, gruposUsuario);
            } else {
            	listAbas = abaDao.getListaAbasComAcesso(tipoAcompanhamento, gruposUsuario);
            }
            
            ValidaPermissao validaPermissao = new ValidaPermissao();
			for  (Iterator it = listAbas.iterator();it.hasNext();) {
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
		    	 
		    	 if (getAbaSuperior().equals(Dominios.NAO)) {
		    		 // Mantis 0011550: Ocultar a aba de parecer se o usuário não possuir permissão para visualização 
		    		 if ("SITUACAO".equals(aba.getNomeAba())){
		    			 //Obtem a lista das funções de acompanhamento que podem visualizar pareceres.
		    			 List listaPermissaoTpfa = validaPermissao.permissaoVisualizarPareceres(tipoAcompanhamento,gruposUsuario);
	            	
		    			 AcompRelatorioDao arelDao = new AcompRelatorioDao(request);
	            	
		    			 //conta os pareceres que existem das funcoes de acompanhamento que podem visualizar pareceres. 
		    			 if (arelDao.ContaArelsDasFuncoesDoAri(acompReferenciaItem, listaPermissaoTpfa) == 0){
		    				 possuiAba = false;	
		    			 }
		    		 }
		    	 }
		    	 
				
				if(!aba.getNomeAba().equals("SITUACAO_INDICADORES") && possuiAba) {
				
					String nomeAba = aba.getNomeAba();
					String labelAba = abaDao.getLabelAbaEstrutura(aba, acompReferenciaItem.getItemEstruturaIett().getEstruturaEtt());
					
					situacaoAba = ABA_DESABILITADA;            
		            
		            if (!"NIVEL_PLANEJAMENTO".equals(nomeAba)) {
		            	
		            	if (funcaoSelecionada.equals(nomeAba)) {
			                situacaoAba = ABA_HABILITADA;
			            }
		            	
		            	if ("PONTOS_CRITICOS".equals(nomeAba)) {
		            		ItemEstruturaIett iett = null;
		            		String codIettRelacao = this.pageContext.getRequest().getParameter("codIettRelacao");
		            		if(acompReferenciaItem != null) {
		            			iett = acompReferenciaItem.getItemEstruturaIett();
		            		} else if(codIettRelacao != null && !"".equals(codIettRelacao) && !"situacaoDatas".equals(funcaoSelecionada) && !"situacaoIndicadores".equals(funcaoSelecionada)){
		            	    	iett = (ItemEstruturaIett) itemEstruturaDao.buscar(ItemEstruturaIett.class, Long.valueOf(codIettRelacao));
		            		}
		            		if(iett != null) {
				            	// verificação dos pontos críticos
					        	try {
					    	    	Collection pontosCriticos = Collections.EMPTY_LIST;
					    	    	pontosCriticos = pontoCriticoDao.getPontosCriticosNaoSolucionados(iett);
					    	    	
					    	    	if (pontoCriticoDao.verificaDatasUltrapassadas(pontosCriticos) && !funcaoSelecionada.equals(aba.getNomeAba())) {
					    	    		situacaoAba = "abapontocritico"; 
					    	    	}
					        	} catch (Exception e) {
					        		situacaoAba = ABA_DESABILITADA; 
					        	}
		            		} else {
				        		situacaoAba = ABA_DESABILITADA; 
		            		}
				            //fim - verificação dos pontos críticos
			            	pagina = "pontosCriticos";
			            } else if ("DADOS_GERAIS".equals(nomeAba)) {
			            	pagina = "dadosGerais/dadosGerais.jsp";
			            } else if ("EVENTOS".equals(nomeAba)) {
			            	pagina = "eventos/eventos.jsp";
			            } else if ("GALERIA".equals(nomeAba)) {
			            	pagina = "galeria/galeria.jsp";
			            } else if ("FINANCEIRO".equals(nomeAba)) {
			            	pagina = "financeiro/financeiro.jsp";
			            } else if ("REL_FISICO_IND_RESULTADO".equals(nomeAba)) {
			            	pagina = "../resultado/indicadoresResultado.jsp?tela=V";
			            } else if ("DATAS_LIMITES".equals(nomeAba)) {
			            	pagina = "datasLimites/datasLimites.jsp";
			            } else if ("SITUACAO".equals(nomeAba)){			            	
			            		//ABA DE LISTAS = (LISTA GERAL)
								if(abaSuperior != null && getAbaSuperior().equals(Dominios.SIM)) {
									pagina =  "listaGeral";
									situacaoAba = ABA_DESABILITADA;
								} else {
									pagina = "situacao/relatorios.jsp?tela=V";
								}			            	
			            } else if ("SITUACAO_PONTOS_CRITICOS".equals(nomeAba)) {
			            	if(abaSuperior != null && getAbaSuperior().equals(Dominios.SIM)) {
			            		if(codTipoAcompanhamento != null && acompReferenciaItem != null) {
									pagina = "situcaoPontosCriticosLista";	
				        		}	
			            		
			            		situacaoAba = ABA_DESABILITADA;  
			            	
			            	}else{
			            		pagina = "situacaoDatas/situacaoDatas.jsp";	
			            	}
			   
			            } else if ("ETAPA".equals(nomeAba)) {
			            	pagina = "etapas/etapas.jsp";
			            }
			            else if ("RELATORIO".equals(nomeAba)){
			            	pagina = "relatorio";
						}
			            else if ("GRAFICO_DE_GANTT".equals(nomeAba)){
			            	pagina = "graficoGantt/graficoGantt.jsp";
			            } else if ("RESUMO".equals(nomeAba)){
			            	pagina = "resumo/detalharItem.jsp";
			            }
						
		                boolean ehProximosItens = false;
			            if("proximosItens".equals(funcaoSelecionada)) {
			            	if("DADOS_GERAIS".equals(nomeAba)
			            			|| "EVENTOS".equals(nomeAba)
			            			|| "GALERIA".equals(nomeAba)
			            			|| "PONTOS_CRITICOS".equals(nomeAba)) {
				                ehProximosItens = true;
			            	}
						}
			            
			            if(podeCriarAba(aba.getLabelAba() , pagina, situacaoAba, ehProximosItens)){
			            	if(!labelAba.equals("")){
			            		criaAba(labelAba, pagina, situacaoAba, s, codItemPai, false);
				            }
			            }			            
					}
				}
			}
        	
            s.append("</td></tr></table>");
            
            writer.print(s.toString());
        } catch (Exception e) {
        	org.apache.log4j.Logger.getLogger(this.getClass()).error(e);
        	e.printStackTrace();
        }
        return Tag.SKIP_BODY;
    }
    
    
    /**
     * Verifica se a aba pode ser criada. Apenas abas com links ou selecionadas podem ser criadas<br>
     * 
     * @author N/C
	 * @since N/C
	 * @version N/C
	 * @param String label
	 * @param String pagina
	 * @param String situacaoAba
	 * @param boolean liberarAbasSeProximosItens
     * @throws ECARException
     */
    private boolean podeCriarAba(String label, String pagina, String situacaoAba, 
    				boolean liberarAbasSeProximosItens) throws ECARException {
    	
    	boolean podeCriar = false;
    	ItemEstruturaDao itemEstruturaDao = new ItemEstruturaDao(null);
    	
    	// Se a aba estiver habilitada, ou seja, selecionada, ela deve ser criada
    	if (situacaoAba == ABA_HABILITADA) {
    		podeCriar = true;
    	} else {
    		
    		//São feitos os mesmos testes do método criaAba
    		
    		String codIettRelacao = (this.pageContext.getRequest().getParameter("codIettRelacao") == null)?"":this.pageContext.getRequest().getParameter("codIettRelacao");
    		
        	if(acompReferenciaItem == null && codIettRelacao != null && !"".equals(codIettRelacao) 
             		&& (	"eventos/eventos.jsp".equals(pagina)
             				|| "dadosGerais/dadosGerais.jsp".equals(pagina)
             				|| "galeria/galeria.jsp".equals(pagina)
             				|| "pontosCriticos".equals(pagina)
             				|| "proximosItens.jsp".equals(pagina))) {
     			
        		podeCriar = true;
     			
        	} else 	if("etapas/etapas.jsp".equals(pagina) && acompReferenciaItem != null && itemEstruturaDao.verificaItemPossuiEtapas(acompReferenciaItem.getItemEstruturaIett())){
        		podeCriar = true;
        		
            } else if(liberarAbasSeProximosItens && acompReferenciaItem == null) {
     			podeCriar = true;
            }
             else if(!"N".equals(getLinks()) && acompReferenciaItem != null){
     			podeCriar = true;
            }
     		 else if ("relatorio".equals(pagina) && ABA_DESABILITADA.equals(situacaoAba)){     			
     			 podeCriar = true;     			
     		}
     		else if (("situacaoDatas/situacaoDatas.jsp".equals(pagina) || "situacaoIndicadores/situacaoIndicadores.jsp".equals(pagina))&& ABA_DESABILITADA.equals(situacaoAba)){			
     			podeCriar = true;
     		}
        	
    		
    	}
    	
    	return podeCriar;
    	
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
	 * @param String codItemPai
	 * @param boolean liberarAbasSeProximosItens
     * @throws ECARException
     */
    private void criaAba(String label, String pagina, String situacaoAba, StringBuffer s, 
    		String codItemPai, boolean liberarAbasSeProximosItens) throws ECARException{
    	
    	String link = "";
    	
    	
    	
    	if(!"N".equals(getLinks()) &&  !pagina.equals("situcaoPontosCriticosLista") && !pagina.equals("situcaoIndicadoresLista") 
    					&& !pagina.equals("listaGeral") ) {
    		
    		
    		if(pagina.equals("pontosCriticos")) {
    			//quando fizer parte das pastas acompanhamento
    			link = contextPath + "/acompanhamento/restricoes/pontosCriticos.jsp?tela=V&primeiroIettClicado=" 
    										+ getPrimeiroIettClicado() + "&primeiroAriClicado=" + getPrimeiroAriClicado()
    										+ "&primeiroAriClicado=" + getPrimeiroAriClicado();
    		} else {
    			//quando fizer parte das pastas de relAcompanhamento
    			link = contextPath + "/acompanhamento/relAcompanhamento/" + pagina;
    		}
    			
    		if (pagina.indexOf("?") < 0){ //Não encontrou o caractere ?
    			link += "?primeiroIettClicado=" + getPrimeiroIettClicado() + "&primeiroAriClicado=" + getPrimeiroAriClicado() ;
    		} else {
    			link += "&primeiroIettClicado=" + getPrimeiroIettClicado() + "&primeiroAriClicado=" + getPrimeiroAriClicado();
    		}
    		
			if(codItemPai != null && !"".equals(codItemPai)) {
    			link += "&itemDoNivelClicado=" + codItemPai;
    		} 
    	} 
    	
    	
    	String codIettRelacao = (this.pageContext.getRequest().getParameter("codIettRelacao") == null)?"":this.pageContext.getRequest().getParameter("codIettRelacao");
    	
    	
        s.append("<table class=\"" + situacaoAba + "\"><tr><td nowrap>");
        
        // só cria o link se a aba estiver desabilitada
        /***************************ABAS DO ITEM***************************************/
        if(situacaoAba.equals(ABA_DESABILITADA) || situacaoAba.equals("abapontocritico")) {
	        if(acompReferenciaItem == null && codIettRelacao != null && !"".equals(codIettRelacao) 
	        		&& (	"dadosGerais/dadosGerais.jsp".equals(pagina)
	        				|| "eventos/eventos.jsp".equals(pagina)
	        				|| "galeria/galeria.jsp".equals(pagina)
	        				|| "proximosItens.jsp".equals(pagina))){
				s.append("<a href=\"#\" onclick=\"javascript:trocaAbaSemAri('" + link + "','" + this.pageContext.getRequest().getParameter("itemDoNivelClicado") + "')\">");
	        } else if(liberarAbasSeProximosItens && acompReferenciaItem == null) {
				s.append("<a href=\"#\" onclick=\"javascript:trocaAbaImpressao('" + link + "')\">");
	        } else if("pontosCriticos".equals(pagina)) {
	        	s.append("<a href=\"#\" onclick=\"javascript:trocaAba('" + link + "','" + acompReferenciaItem.getCodAri() + "','" + codRegd + "')\">");
	        } else if ("relatorio".equals(pagina)){
				link = contextPath + "/acompanhamento/relAcompanhamento/relatorios/relatorioImpresso.jsp?tela=V";
				s.append("<a href=\"#\" onclick=\"javascript:trocaAbaImpressao('" + link + "'" + ")\">");
			} else if("indicadoresResultado".equals(pagina)) {
				pagina = "/acompanhamento/resultado/indicadoresResultado.jsp?tela=V&codAri=" + acompReferenciaItem.getCodAri() 
						+ "&codAcomp=" + acompReferenciaItem.getAcompReferenciaAref().getCodAref()
						+ "&itemDoNivelClicado" + this.pageContext.getRequest().getParameter("itemDoNivelClicado");
				link = contextPath + pagina;
				s.append("<a href=\"#\" onclick=\"javascript:trocaAbaImpressao('" + link + "'" + ")\">");
			}
		    
	        /***************************ABAS DE LISTAS***************************************/
			else if(pagina.equals("situcaoPontosCriticosLista")){
				
				pagina = "/acompanhamento/posicaoGeral.jsp?relatorio=true&situacaoDatas=true&primeiraChamada=N&"+
										"codTipoAcompanhamento="+ this.codTipoAcompanhamento + 
										"&mesReferencia="+ acompReferenciaItem.getAcompReferenciaAref().getCodAref().toString();
				link = contextPath + pagina;
				s.append("<a href=\"#\" onclick=\"javascript:trocaAbaImpressao('" + link + "'" + ")\">");
			
			} else if(pagina.equals("situcaoIndicadoresLista")) {
				pagina = "/acompanhamento/situacaoIndicadores.jsp?codTipoAcompanhamento="+ this.codTipoAcompanhamento + 
												"&mesReferencia="+ acompReferenciaItem.getAcompReferenciaAref().getCodAref().toString();
			
				link = contextPath + pagina;
				s.append("<a href=\"#\" onclick=\"javascript:trocaAbaImpressao('" + link + "'" + ")\">");
			
			} else if(pagina.equals("listaGeral")) {
				pagina = "/acompanhamento/posicaoGeral.jsp?codTipoAcompanhamento="+ this.codTipoAcompanhamento + 
								"&mesReferencia="+ acompReferenciaItem.getAcompReferenciaAref().getCodAref().toString();
				link = contextPath + pagina;
				s.append("<a href=\"#\" onclick=\"javascript:trocaAbaImpressao('" + link + "'" + ")\">");
	
				
			/*****************************************************/	
			}else if(!"N".equals(getLinks()) && acompReferenciaItem != null){
				s.append("<a href=\"#\" onclick=\"javascript:trocaAba('" + link + "','" + acompReferenciaItem.getCodAri() +  "','" + codRegd + "')\">");
			}
	        
		
			s.append(label);
			s.append("</a>");
        
        } else {
        	
        	s.append(label);
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
        return Tag.EVAL_PAGE;
    }
    
    /**
     * Retorna AcompReferenciaItemAri acompReferenciaItem.<br>
     * 
     * @author N/C
	 * @since N/C
	 * @version N/C
     * @return AcompReferenciaItemAri Returns the acompReferenciaItem.
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
     * Retorna String funcaoSelecionada.<br>
     * 
     * @author N/C
	 * @since N/C
	 * @version N/C
     * @return String - (Returns the funcaoSelecionada)
     */
    public String getFuncaoSelecionada() {
        return funcaoSelecionada;
    }
    
    /**
     * Atribui valor especificado para String funcaoSelecionada.<br>
     * 
     * @author N/C
	 * @since N/C
	 * @version N/C
     * @param funcaoSelecionada
     */
    public void setFuncaoSelecionada(String funcaoSelecionada) {
        this.funcaoSelecionada = funcaoSelecionada;
    }

	/**
	 * Retorna String links.<br>
	 * 
	 * @author N/C
	 * @since N/C
	 * @version N/C
	 * @return String - (Returns the links)
	 */
	public String getLinks() {
		return links;
	}

	/**
	 * Atribui valor especificado para String links.<br>
	 * 
         * @param links
         * @author N/C
	 * @since N/C
	 * @version N/C
	 */
	public void setLinks(String links) {
		this.links = links;
	}
    
	/**
	 * Retorna String primeiroIettClicado.<br>
	 * 
	 * @author N/C
	 * @since N/C
	 * @version N/C
	 * @return String
	 */
    public String getPrimeiroIettClicado() {
		return primeiroIettClicado;
	}

    /**
     * Atribui valor especificado para String primeiroIettClicado.<br>
     * 
     * @author N/C
	 * @since N/C
	 * @version N/C
     * @param primeiroIettClicado 
     */
	public void setPrimeiroIettClicado(String primeiroIettClicado) {
		
		this.primeiroIettClicado = (primeiroIettClicado == null || primeiroIettClicado.trim().equals(""))?null:primeiroIettClicado;
	}

	/**
	 * Retorna String primeiroAriClicado.<br>
	 * 
	 * @author N/C
	 * @since N/C
	 * @version N/C
	 * @return String
	 */
	public String getPrimeiroAriClicado() {
		return primeiroAriClicado;
	}

	/**
	 * Atribui valor especificado para String primeiroAriClicado.<br>
	 * 
         * @param primeiroAriClicado
         * @author N/C
	 * @since N/C
	 * @version N/C
	 */
	public void setPrimeiroAriClicado(String primeiroAriClicado) {
		this.primeiroAriClicado = primeiroAriClicado;
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
	 * Atribui valor especificado para String caminho.<br>
	 * 
         * @param caminho
         * @author N/C
	 * @since N/C
	 * @version N/C
	 */
	public void setCaminho(String caminho) {
		this.caminho = caminho;
	}

	/**
	 * Retorna String codTipoAcompanhamento.<br>
	 * 
	 * @author N/C
	 * @since N/C
	 * @version N/C
	 * @return String
	 */
	public String getCodTipoAcompanhamento() {
		return codTipoAcompanhamento;
	}

	/**
	 * Atribui valor especificado para String codTipoAcompanhamento.<br>
	 * 
	 * @author N/C
	 * @since N/C
	 * @version N/C
         * @param codTipoAcompanhamento
	 */
	public void setCodTipoAcompanhamento(String codTipoAcompanhamento) {
		this.codTipoAcompanhamento = codTipoAcompanhamento;
	}

        /**
         *
         * @param gruposUsuario
         */
        public void setGruposUsuario(Set gruposUsuario) {
		this.gruposUsuario = gruposUsuario;
	}
	
	/**
	 * Guarda o valor do pathEcar.<br>
	 * 
         * @return
         * @author N/C
	 * @since N/C
	 * @version N/C
	 */
	public String getContextPath() {
		return contextPath;
	}

        /**
         *
         * @param contextPath
         */
        public void setContextPath(String contextPath) {
		this.contextPath = contextPath;
	}
	
	    
    
    /**
     * Serve para distinguir entre a aba superior que é comum a vários acompanhamentos
     * e a aba mais embaixo que não aparece essas abas superior.
     */
    private String abaSuperior= Dominios.NAO ;
    
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
	
	
	
}