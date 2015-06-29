<%
if(ari != null) {
	
	String retroceder = "";
	String avancar = "";
	
	String paginaBtAvanca = "";
	String paginaBtRetrocede = "";
	
	String codArisControleRequest = Pagina.getParamStr(request, "codArisControle");
	String formaVisualizar = Pagina.getParamStr(request, "hidFormaVisualizacaoEscolhida");
		
	if(!codArisControleRequest.equals("")){
		String strCodArisControle = Pagina.getParamStr(request, "codArisControle");
		
		if(strCodArisControleAux != null && !strCodArisControleAux.equals(""))
			strCodArisControle = strCodArisControleAux; 
		
		String[] codArisControle = strCodArisControle.split("\\|");
		
		List itensControle = new ArrayList();
		
		for(int i = 0; i < codArisControle.length; i++){
			String codLido = codArisControle[i];
			if(!codLido.equals("")){
				AcompReferenciaItemAri ariSessao = (AcompReferenciaItemAri) new AcompReferenciaItemDao(null).buscar(AcompReferenciaItemAri.class, Long.valueOf(codLido));
				
				if (Dominios.SIM.equals(ari.getAcompReferenciaAref().getTipoAcompanhamentoTa().getIndSepararOrgaoTa())){
					if (ariSessao.getAcompReferenciaAref().getDiaAref().equals(ari.getAcompReferenciaAref().getDiaAref()) &&
							ariSessao.getAcompReferenciaAref().getMesAref().equals(ari.getAcompReferenciaAref().getMesAref()) && 
							ariSessao.getAcompReferenciaAref().getAnoAref().equals(ari.getAcompReferenciaAref().getAnoAref())){
						itensControle.add(ariSessao);
					}
				} else {
					if(ariSessao.getAcompReferenciaAref().equals(ari.getAcompReferenciaAref())){
						itensControle.add(ariSessao);
					}
				}
			}
		}
		
		AbaDao abaDaoBotaoAvancaoRetroceder = new AbaDao(request);
		TipoAcompanhamentoDao taDaoAvancaRetrocede = new TipoAcompanhamentoDao(request);
		TipoAcompanhamentoTa tipoAcompanhamentoAvancaRetrocede = (TipoAcompanhamentoTa) taDaoAvancaRetrocede.buscar(TipoAcompanhamentoTa.class, Long.valueOf(codTipoAcompanhamento));
		

		Aba proximaAba = null;

    	// valor tipo 1 => registro 2=> visualização
        List listAbasComAcessoAvancaRetrocede = abaDaoBotaoAvancaoRetroceder.getListaAbasComAcesso(tipoAcompanhamentoAvancaRetrocede, seguranca.getGruposAcesso(), 1);
        
		int tam = itensControle.size();
		for(int i = 0; i < tam; i++){
			String paginaAba = "";	
			String paginaAbaVisualizacao = "";
			
			AcompReferenciaItemAri ariLido = (AcompReferenciaItemAri) itensControle.get(i);
			if(ariLido.equals(ari)){
				if(i > 0){
					boolean possuiFuncaoConfiguradaRetrocede = false;
					AcompReferenciaItemAri ariAnt = (AcompReferenciaItemAri) itensControle.get(i-1);
					retroceder = ariAnt.getCodAri().toString() + "," + ariAnt.getItemEstruturaIett().getCodIett();
					
					
					if (listAbasComAcessoAvancaRetrocede!=null && !listAbasComAcessoAvancaRetrocede.isEmpty()) {
						Aba abaDirecionaRegistro = null;
						String nomeAba = "";
						if(usuarioClicouAba!=null && usuarioClicouAba.equals("S") && 
								funcaoSelecionada != null && !"".equals(funcaoSelecionada)){
							abaDirecionaRegistro = abaDaoBotaoAvancaoRetroceder.buscarAba(funcaoSelecionada);
							 nomeAba = abaDirecionaRegistro.getNomeAba();	
						} else{
			            	abaDirecionaRegistro = (Aba) listAbasComAcessoAvancaRetrocede.get(0);
			            	nomeAba = abaDirecionaRegistro.getNomeAba();
						}
		            	
		    	        if(abaDirecionaRegistro.getFuncaoFun()!= null){        	
		    	        	if(ariAnt.getItemEstruturaIett().getEstruturaEtt() != null){
			    	        	Set listaFuncoes = ariAnt.getItemEstruturaIett().getEstruturaEtt().getEstruturaFuncaoEttfs();
			    	        	Iterator itListaFuncoes = listaFuncoes.iterator();
			    	        	while(itListaFuncoes.hasNext()){
			    	        		EstruturaFuncaoEttf funcao = (EstruturaFuncaoEttf) itListaFuncoes.next();
			    	        		if(abaDirecionaRegistro.getFuncaoFun().getCodFun().equals(funcao.getFuncaoFun().getCodFun())){
			    	        			possuiFuncaoConfiguradaRetrocede = true;
			    	        			break;
			    	        		}
			    	        	}
		    	        	} else{
		    	        		possuiFuncaoConfiguradaRetrocede = true;
		    	        	}
		    	        } else{
		    	        	possuiFuncaoConfiguradaRetrocede = true;
		    	        }
		            }
					
		           if(!possuiFuncaoConfiguradaRetrocede){
			            List listAbasComAcessoTotal = abaDaoBotaoAvancaoRetroceder.getListaAbasComAcesso(tipoAcompanhamentoAvancaRetrocede, seguranca.getGruposAcesso());
				            
			            
			            Iterator itAbas = listAbasComAcessoTotal.iterator();
			    	    while (itAbas.hasNext()) {
			   	    	 Aba abaAvancRetroc = (Aba) itAbas.next();
			   	    	 
			   	    	 boolean possuiAba = false;
			   	    	 if (ariAnt != null){
			   	    	        if(abaAvancRetroc.getFuncaoFun()!= null){        								   	    	        	
			   	    	        	Set listaFuncoes = ariAnt.getItemEstruturaIett().getEstruturaEtt().getEstruturaFuncaoEttfs();
			   	    	        	Iterator itListaFuncoes = listaFuncoes.iterator();
			   	    	        	while(itListaFuncoes.hasNext()){
			   	    	        		EstruturaFuncaoEttf funcao = (EstruturaFuncaoEttf) itListaFuncoes.next();
			   	    	        		if(abaAvancRetroc.getFuncaoFun().getCodFun().equals(funcao.getFuncaoFun().getCodFun())){
			   	    	        			possuiFuncaoConfiguradaRetrocede = true;
			   	    	        			proximaAba = abaAvancRetroc; 
			   	    	        			break;
			   	    	        		}
			   	    	        	}
			   	    	        } else{
			   	    	        	possuiFuncaoConfiguradaRetrocede = true;
			   	    	        	proximaAba = abaAvancRetroc;
			   	    	        }
			   	    	 }
			   	    	 if(possuiFuncaoConfiguradaRetrocede){
			   	    		 break;
			   	    	 }
			    	    }
			    	    
			    	    if(proximaAba != null){
			    	    								    	    
			    	    	
							/***************ABA DADOS GERAIS*************************/
							if ("DADOS_GERAIS".equals(proximaAba.getNomeAba())) {
								paginaAba = "acompanhamento/dadosGerais/frm_con.jsp";
								paginaAbaVisualizacao = "acompanhamento/relAcompanhamento/dadosGerais/dadosGerais.jsp";
							/***************ABA EVENTOS*****************************/
							} else if ("EVENTOS".equals(proximaAba.getNomeAba())) {
								paginaAba = "acompanhamento/realizacoes/eventos.jsp";	
								paginaAbaVisualizacao = "acompanhamento/relAcompanhamento/eventos/eventos.jsp";													
							/***************ABA PONTOS CRITICOS*********************/	
							} else if ("PONTOS_CRITICOS".equals(proximaAba.getNomeAba())) {
								paginaAba = "acompanhamento/restricoes/pontosCriticos.jsp";
								paginaAbaVisualizacao = "acompanhamento/restricoes/pontosCriticos.jsp?tela=V";
							/***************ABA SITUACAO PONTOS CRITICOS***********/	
							} else if(proximaAba.getNomeAba().equals("SITUACAO_PONTOS_CRITICOS")) {
								paginaAba =  "acompanhamento/situacaoDatas/situacaoDatas.jsp";
								paginaAbaVisualizacao = "acompanhamento/relAcompanhamento/situacaoDatas/situacaoDatas.jsp";
							/***************ABA GALERIA***************************/	
							} else if ("GALERIA".equals(proximaAba.getNomeAba())) {
								paginaAba =  "acompanhamento/galeria/galeria.jsp";
								paginaAbaVisualizacao = "acompanhamento/relAcompanhamento/galeria/galeria.jsp";
							/***************ABA FINANCEIRO************************/
							} else if ("FINANCEIRO".equals(proximaAba.getNomeAba())) {
								paginaAba =  "acompanhamento/financeiro/financeiro.jsp";
								paginaAbaVisualizacao = "acompanhamento/relAcompanhamento/financeiro/financeiro.jsp";
							/***************ABA RESUMO***************************/
							} else if("RELACAO".equals(proximaAba.getNomeAba())){
								paginaAba =  "acompanhamento/resumo/detalharItem.jsp";
								paginaAbaVisualizacao = "acompanhamento/relAcompanhamento/resumo/detalharItem.jsp";
				            /***************ABA METAS E INDICADORES***************/	
				            } else if ("REL_FISICO_IND_RESULTADO".equals(proximaAba.getNomeAba())) {
				            	paginaAba = "acompanhamento/resultado/indicadoresResultado.jsp";	
				            	paginaAbaVisualizacao = "acompanhamento/resultado/indicadoresResultado.jsp?tela=V";
							/***************ABA SITUACAO E INDICADORES*************/	
				            } else if (proximaAba.getNomeAba().equals("SITUACAO_INDICADORES")) {
				            	paginaAba = "acompanhamento/situacaoIndicadores/situacaoIndicadores.jsp";
				            	paginaAbaVisualizacao = "acompanhamento/relAcompanhamento/situacaoIndicadores/situacaoIndicadores.jsp";
				            /***************ABA ETAPA*****************************/		
				            }	else if ("ETAPA".equals(proximaAba.getNomeAba())) {
				            	paginaAba = "acompanhamento/etapa/etapas.jsp";
				            	paginaAbaVisualizacao = "acompanhamento/relAcompanhamento/etapas/etapas.jsp";
							/***************ABA DATAS LIMITES*********************/	
							} else if ("DATAS_LIMITES".equals(proximaAba.getNomeAba())) {
								paginaAba = "acompanhamento/datasLimites/datasLimites.jsp";
								paginaAbaVisualizacao = "acompanhamento/relAcompanhamento/datasLimites/datasLimites.jsp";
							/***************ABA SITUACAO***************************/
							} else if("SITUACAO".equals(proximaAba.getNomeAba())){
								paginaAba = "acompanhamento/situacao/relatorios.jsp";
								paginaAbaVisualizacao = "acompanhamento/situacao/relatorios.jsp?tela=V";
							/***************ABA GRAFICO DE GANTT*********************/
							} else if("GRAFICO_DE_GANTT".equals(proximaAba.getNomeAba())){
								paginaAba = "acompanhamento/graficoGantt/graficoGantt.jsp";
								paginaAbaVisualizacao = "acompanhamento/relAcompanhamento/graficoGantt/graficoGantt.jsp";
							/***************ABA RELATORIO ***************************/
							}	else if("RELATORIO".equals(proximaAba.getNomeAba())) {
								paginaAba= "acompanhamento/relatorios/relatorioImpresso.jsp?tela=R";
								paginaAbaVisualizacao = "acompanhamento/relatorios/relatorioImpresso.jsp?tela=V";
			        		}else {
			                	
			                	//Caso nenhuma aba esteja configurada ou nao tenha permissao, o sistema direciona para a tela Resumo, que hoje é uma tela fixa.
			                	paginaAba = "acompanhamento/resumo/detalharItem.jsp";
			                	paginaAbaVisualizacao = "acompanhamento/relAcompanhamento/resumo/detalharItem.jsp";
			                }
														    	    
						// adiciona tipo padrao de exibicao
						if (paginaAba!=null) {
							String tipoPadraoAba = "1";
							paginaAba += (paginaAba.indexOf("&")>0 || paginaAba.indexOf("?")>0?"&tipoPadraoExibicaoAba=" + tipoPadraoAba:"?tipoPadraoExibicaoAba=" + tipoPadraoAba);												
		           		}	
		            }
		           }
					
					if(possuiFuncaoConfiguradaRetrocede){
						if(paginaAba != null && !"".equals(paginaAba)){
							paginaBtRetrocede = _pathEcar + "/" + paginaAba ;
						} else{
							paginaBtRetrocede = paginaBtAvancRetroceder;	
						}
						
					} else{
						paginaBtRetrocede =  _pathEcar + "/acompanhamento/acessoSemFuncao.jsp";
						String tipoPadraoExibicaoAbaRetrocede = "1"; 
						paginaBtRetrocede += (paginaBtRetrocede.indexOf("&")>0 || paginaBtRetrocede.indexOf("?")>0?"&tipoPadraoExibicaoAba=" + tipoPadraoExibicaoAbaRetrocede:"?tipoPadraoExibicaoAba=" + tipoPadraoExibicaoAbaRetrocede);
					}
					
				}
				if(i < (tam-1)){
					boolean possuiFuncaoConfiguradaAvanca = false;
					AcompReferenciaItemAri ariPos = (AcompReferenciaItemAri) itensControle.get(i+1);
					avancar = ariPos.getCodAri().toString() + "," + ariPos.getItemEstruturaIett().getCodIett();
					
					if (listAbasComAcessoAvancaRetrocede!=null && !listAbasComAcessoAvancaRetrocede.isEmpty()) {
						Aba abaDirecionaRegistro = null;
						String nomeAba = "";
						if(usuarioClicouAba!=null && usuarioClicouAba.equals("S") && 
								funcaoSelecionada != null && !"".equals(funcaoSelecionada)){
							abaDirecionaRegistro = abaDaoBotaoAvancaoRetroceder.buscarAba(funcaoSelecionada);
							 nomeAba = abaDirecionaRegistro.getNomeAba();	
						} else{
			            	abaDirecionaRegistro = (Aba) listAbasComAcessoAvancaRetrocede.get(0);
			            	nomeAba = abaDirecionaRegistro.getNomeAba();
						}
		            	
		    	        if(abaDirecionaRegistro.getFuncaoFun()!= null){        	
		    	        	if(ariPos.getItemEstruturaIett().getEstruturaEtt() != null){
			    	        	Set listaFuncoes = ariPos.getItemEstruturaIett().getEstruturaEtt().getEstruturaFuncaoEttfs();
			    	        	Iterator itListaFuncoes = listaFuncoes.iterator();
			    	        	while(itListaFuncoes.hasNext()){
			    	        		EstruturaFuncaoEttf funcao = (EstruturaFuncaoEttf) itListaFuncoes.next();
			    	        		if(abaDirecionaRegistro.getFuncaoFun().getCodFun().equals(funcao.getFuncaoFun().getCodFun())){
			    	        			possuiFuncaoConfiguradaAvanca = true;
			    	        			break;
			    	        		}
			    	        	}
		    	        	} else{
		    	        		possuiFuncaoConfiguradaAvanca = true;
		    	        	}
		    	        } else{
		    	        	possuiFuncaoConfiguradaAvanca = true;
		    	        }		    	        
		            }
					
			           if(!possuiFuncaoConfiguradaAvanca){
				            List listAbasComAcessoTotal = abaDaoBotaoAvancaoRetroceder.getListaAbasComAcesso(tipoAcompanhamentoAvancaRetrocede, seguranca.getGruposAcesso());
					            
				            
				            Iterator itAbas = listAbasComAcessoTotal.iterator();
				    	    while (itAbas.hasNext()) {
				   	    	 Aba abaAvancRetroc = (Aba) itAbas.next();
				   	    	 
				   	    	 boolean possuiAba = false;
				   	    	 if (ariPos != null){
				   	    	        if(abaAvancRetroc.getFuncaoFun()!= null){        								   	    	        	
				   	    	        	Set listaFuncoes = ariPos.getItemEstruturaIett().getEstruturaEtt().getEstruturaFuncaoEttfs();
				   	    	        	Iterator itListaFuncoes = listaFuncoes.iterator();
				   	    	        	while(itListaFuncoes.hasNext()){
				   	    	        		EstruturaFuncaoEttf funcao = (EstruturaFuncaoEttf) itListaFuncoes.next();
				   	    	        		if(abaAvancRetroc.getFuncaoFun().getCodFun().equals(funcao.getFuncaoFun().getCodFun())){
				   	    	        			possuiFuncaoConfiguradaAvanca = true;
				   	    	        			proximaAba = abaAvancRetroc; 
				   	    	        			break;
				   	    	        		}
				   	    	        	}
				   	    	        } else{
				   	    	        	possuiFuncaoConfiguradaAvanca = true;
				   	    	        	proximaAba = abaAvancRetroc;
				   	    	        }
				   	    	 }
				   	    	 if(possuiFuncaoConfiguradaAvanca){
				   	    		 break;
				   	    	 }
				    	    }
				    	    
				    	    if(proximaAba != null){
				    	    								    	    
				    	    	
								/***************ABA DADOS GERAIS*************************/
								if ("DADOS_GERAIS".equals(proximaAba.getNomeAba())) {
									paginaAba = "acompanhamento/dadosGerais/frm_con.jsp";
									paginaAbaVisualizacao = "acompanhamento/relAcompanhamento/dadosGerais/dadosGerais.jsp";
								/***************ABA EVENTOS*****************************/
								} else if ("EVENTOS".equals(proximaAba.getNomeAba())) {
									paginaAba = "acompanhamento/realizacoes/eventos.jsp";	
									paginaAbaVisualizacao = "acompanhamento/relAcompanhamento/eventos/eventos.jsp";													
								/***************ABA PONTOS CRITICOS*********************/	
								} else if ("PONTOS_CRITICOS".equals(proximaAba.getNomeAba())) {
									paginaAba = "acompanhamento/restricoes/pontosCriticos.jsp";
									paginaAbaVisualizacao = "acompanhamento/restricoes/pontosCriticos.jsp?tela=V";
								/***************ABA SITUACAO PONTOS CRITICOS***********/	
								} else if(proximaAba.getNomeAba().equals("SITUACAO_PONTOS_CRITICOS")) {
									paginaAba =  "acompanhamento/situacaoDatas/situacaoDatas.jsp";
									paginaAbaVisualizacao = "acompanhamento/relAcompanhamento/situacaoDatas/situacaoDatas.jsp";
								/***************ABA GALERIA***************************/	
								} else if ("GALERIA".equals(proximaAba.getNomeAba())) {
									paginaAba =  "acompanhamento/galeria/galeria.jsp";
									paginaAbaVisualizacao = "acompanhamento/relAcompanhamento/galeria/galeria.jsp";
								/***************ABA FINANCEIRO************************/
								} else if ("FINANCEIRO".equals(proximaAba.getNomeAba())) {
									paginaAba =  "acompanhamento/financeiro/financeiro.jsp";
									paginaAbaVisualizacao = "acompanhamento/relAcompanhamento/financeiro/financeiro.jsp";
								/***************ABA RESUMO***************************/
								} else if("RELACAO".equals(proximaAba.getNomeAba())){
									paginaAba =  "acompanhamento/resumo/detalharItem.jsp";
									paginaAbaVisualizacao = "acompanhamento/relAcompanhamento/resumo/detalharItem.jsp";
					            /***************ABA METAS E INDICADORES***************/	
					            } else if ("REL_FISICO_IND_RESULTADO".equals(proximaAba.getNomeAba())) {
					            	paginaAba = "acompanhamento/resultado/indicadoresResultado.jsp";	
					            	paginaAbaVisualizacao = "acompanhamento/resultado/indicadoresResultado.jsp?tela=V";
								/***************ABA SITUACAO E INDICADORES*************/	
					            } else if (proximaAba.getNomeAba().equals("SITUACAO_INDICADORES")) {
					            	paginaAba = "acompanhamento/situacaoIndicadores/situacaoIndicadores.jsp";
					            	paginaAbaVisualizacao = "acompanhamento/relAcompanhamento/situacaoIndicadores/situacaoIndicadores.jsp";
					            /***************ABA ETAPA*****************************/		
					            }	else if ("ETAPA".equals(proximaAba.getNomeAba())) {
					            	paginaAba = "acompanhamento/etapa/etapas.jsp";
					            	paginaAbaVisualizacao = "acompanhamento/relAcompanhamento/etapas/etapas.jsp";
								/***************ABA DATAS LIMITES*********************/	
								} else if ("DATAS_LIMITES".equals(proximaAba.getNomeAba())) {
									paginaAba = "acompanhamento/datasLimites/datasLimites.jsp";
									paginaAbaVisualizacao = "acompanhamento/relAcompanhamento/datasLimites/datasLimites.jsp";
								/***************ABA SITUACAO***************************/
								} else if("SITUACAO".equals(proximaAba.getNomeAba())){
									paginaAba = "acompanhamento/situacao/relatorios.jsp";
									paginaAbaVisualizacao = "acompanhamento/situacao/relatorios.jsp?tela=V";
								/***************ABA GRAFICO DE GANTT*********************/
								} else if("GRAFICO_DE_GANTT".equals(proximaAba.getNomeAba())){
									paginaAba = "acompanhamento/graficoGantt/graficoGantt.jsp";
									paginaAbaVisualizacao = "acompanhamento/relAcompanhamento/graficoGantt/graficoGantt.jsp";
								/***************ABA RELATORIO ***************************/
								}	else if("RELATORIO".equals(proximaAba.getNomeAba())) {
									paginaAba= "acompanhamento/relatorios/relatorioImpresso.jsp?tela=R";
									paginaAbaVisualizacao = "acompanhamento/relatorios/relatorioImpresso.jsp?tela=V";
				        		}else {
				                	
				                	//Caso nenhuma aba esteja configurada ou nao tenha permissao, o sistema direciona para a tela Resumo, que hoje é uma tela fixa.
				                	paginaAba = "acompanhamento/resumo/detalharItem.jsp";
				                	paginaAbaVisualizacao = "acompanhamento/relAcompanhamento/resumo/detalharItem.jsp";
				                }
															    	    
							// adiciona tipo padrao de exibicao
							if (paginaAba!=null) {
								String tipoPadraoAba = "1";
								paginaAba += (paginaAba.indexOf("&")>0 || paginaAba.indexOf("?")>0?"&tipoPadraoExibicaoAba=" + tipoPadraoAba:"?tipoPadraoExibicaoAba=" + tipoPadraoAba);												
			           		}	
			            }
			           }
					
					if(possuiFuncaoConfiguradaAvanca){
						if(paginaAba != null && !"".equals(paginaAba)){
							paginaBtAvanca = _pathEcar + "/" + paginaAba ;
						} else{
							paginaBtAvanca = paginaBtAvancRetroceder;	
						}
					} else{
						paginaBtAvanca =  _pathEcar + "/acompanhamento/acessoSemFuncao.jsp";
						String tipoPadraoExibicaoAbaAvanca = "1"; 
						paginaBtAvanca += (paginaBtAvanca.indexOf("&")>0 || paginaBtAvanca.indexOf("?")>0?"&tipoPadraoExibicaoAba=" + tipoPadraoExibicaoAbaAvanca:"?tipoPadraoExibicaoAba=" + tipoPadraoExibicaoAbaAvanca);
					}
					
					
				}
				break;
			}
		}
	}
		
	%>
	
	
<%@page import="ecar.pojo.TipoAcompanhamentoTa"%>
<%@page import="ecar.dao.TipoAcompanhamentoDao"%>
<%@page import="comum.util.Pagina"%>
<%@page import="java.util.List"%>
<%@page import="ecar.pojo.Aba"%>
<%@page import="java.util.Iterator"%>
<%@page import="ecar.pojo.EstruturaFuncaoEttf"%>
<%@page import="java.util.Set"%>
<%@page import="ecar.dao.AbaDao"%>
<%@page import="ecar.pojo.AcompReferenciaAref"%>
<%@page import="ecar.util.Dominios"%><table align="center">
		<tr>
	<%
	String desabilitarBotao = "disabled";
	
	if(!retroceder.equals("")){
		desabilitarBotao = "";
	}
	%>
			<td>
				<input type="button" name="btRetroceder" value="&lt;&lt; Retroceder" onclick="javascript:previous(<%=retroceder%>)" <%=desabilitarBotao%>>
			</td>
	<%
	desabilitarBotao = "disabled";
	if(!avancar.equals("")){
		desabilitarBotao = "";
	}
	%>
			<td>
				<input type="button" name="btAvancar" value="Avançar &gt;&gt;" onclick="javascript:next(<%=avancar%>)" <%=desabilitarBotao%>>
			</td>
		</tr>
	</table>
	
	<script language="javascript">
	//Função utilizada nos botões "Avançar" e "Retroceder".
	function next(codAri,codIett){
		document.form.codAri.value = codAri;
		document.form.itemDoNivelClicado.value = codIett;
		document.form.action = "<%=paginaBtAvanca%>&hidFormaVisualizacaoEscolhida=<%=formaVisualizar%>";
		document.form.submit();
	}

	function previous(codAri,codIett){
		document.form.codAri.value = codAri;
		document.form.itemDoNivelClicado.value = codIett;
		document.form.action = "<%=paginaBtRetrocede%>&hidFormaVisualizacaoEscolhida=<%=formaVisualizar%>";
		document.form.submit();
	}
	</script>
	
	<%
	
	
	
}
%>