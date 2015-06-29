
<jsp:directive.page import="ecar.pojo.Aba" />
<jsp:directive.page import="ecar.pojo.Link" />
<jsp:directive.page import="ecar.pojo.HistoricoConfig" />
<jsp:directive.page import="ecar.pojo.FuncaoFun" />
<jsp:directive.page import="ecar.dao.LinkDao"/>
<jsp:directive.page import="ecar.dao.HistoricoDao"/>
<jsp:directive.page import="ecar.dao.FuncaoDao" />

<jsp:directive.page import="comum.util.FileUpload" />

<jsp:directive.page import="java.io.File" />
<jsp:directive.page import="java.util.Iterator" />
<jsp:directive.page import="java.util.List" />
<jsp:directive.page import="java.util.ArrayList" />

<jsp:directive.page import="org.apache.commons.fileupload.FileItem" />

<%@ include file="/login/validaAcesso.jsp"%>
<%@ include file="/frm_global.jsp"%>

<%@ page import="ecar.dao.AbaDao" %>


<%@page import="ecar.util.Dominios"%>

<html lang="pt-br">
<head>
<%@ include file="/include/meta.jsp"%>
<%@ include file="/titulo.jsp"%>
</head>
<body>

<form name="form" method="post">
    <!-- campo de controle para a navegação de registros -->
	<input type="hidden" name="msgOperacao" value=""> 
	<input type="hidden" name="msgPesquisa" value="">	
	<input type="hidden" name="hidPesquisou" value="">
</form>

<%
	Aba aba = new Aba();
	Link link = new Link();
	HistoricoConfig historico = new HistoricoConfig();
	AbaDao abaDao = new AbaDao(request);
	LinkDao linkDao = new LinkDao(request);
	HistoricoDao historicoDao = new HistoricoDao(request); 
	
	String msg = null;
	String submit = "frm_inc.jsp";
	
	try {
		
		String pathRaiz = configuracaoCfg.getRaizUpload();
		//pathRaiz = "/home/02759475484/pessoal";
	
		String pathIconeLinks = configuracaoCfg.getUploadIconeLinks();
		
		// Pega os campos do formulario
		List campos = new ArrayList();
		campos = FileUpload.criaListaCampos(request);
        Iterator itCampos = campos.iterator();
        
    	String hidAcao = FileUpload.verificaValorCampo(campos, "hidAcao");
        
        // Pega a configuração das abas
        List listAbasAtualizadas = new ArrayList();
        List listFuncoesSelecionadas = new ArrayList();
		ArrayList listAbas = (ArrayList)abaDao.listar(Aba.class, new String[]{"codAba", "asc"});
		Iterator abasIt = listAbas.iterator();

		// Pega os valores padrão para registro e visualizazao	
		String ehPadraoRegistroLista = FileUpload.verificaValorCampo(campos, "ehPadraoRegistro");
		String ehPadraoVisualizacaoLista = FileUpload.verificaValorCampo(campos, "ehPadraoVisualizacao");
				
		// Para cada aba seta os dados que estao no formulario e adiciona a aba atualiza na lista listAbasAtualizadas
		while(abasIt.hasNext()) {
			aba = (Aba)abasIt.next();
			if (!aba.getNomeAba().equals("NIVEL_PLANEJAMENTO") && !aba.getNomeAba().equals("SITUACAO_INDICADORES")) {			
				if (Dominios.SIM.equals(FileUpload.verificaValorCampo(campos, "exibePosicaoAba"+aba.getCodAba().toString()).trim())) {
					aba.setExibePosicaoAba(Dominios.SIM);
				} else {
					aba.setExibePosicaoAba(Dominios.NAO);
				}
				
				aba.setOrdemAba(Integer.valueOf(FileUpload.verificaValorCampo(campos, "ordemAba"+aba.getCodAba().toString())));
				aba.setLabelAba(FileUpload.verificaValorCampo(campos, "labelAba"+aba.getCodAba().toString()));
					
				if("".equals(FileUpload.verificaValorCampo(campos, "funAba"+aba.getCodAba().toString()))) {
						aba.setFuncaoFun(null);
				} else {
					FuncaoDao funcaoDao = new FuncaoDao(request);
					FuncaoFun funcao = (FuncaoFun)funcaoDao.buscar(FuncaoFun.class,Long.valueOf(FileUpload.verificaValorCampo(campos, "funAba"+aba.getCodAba().toString()).trim())); 
					aba.setFuncaoFun(funcao);
					listFuncoesSelecionadas.add(funcao);
				}
				
				if (ehPadraoRegistroLista.equals(aba.getCodAba().toString())) {
					aba.setEhPadraoRegistro(Dominios.SIM);
				} else {
					aba.setEhPadraoRegistro(Dominios.NAO);
				}
				
				if (ehPadraoVisualizacaoLista.equals(aba.getCodAba().toString())) {
					aba.setEhPadraoVisualizacao(Dominios.SIM);
				} else {
					aba.setEhPadraoVisualizacao(Dominios.NAO);
				}
				
				listAbasAtualizadas.add(aba);
			}
		}
		
		Iterator funcoesIt = listFuncoesSelecionadas.iterator();
		while(funcoesIt.hasNext()){
			FuncaoFun funcao = (FuncaoFun) funcoesIt.next();
			int contadorFuncoes = 0;
			Iterator abasAtualizadasIt = listAbasAtualizadas.iterator();
			while(abasAtualizadasIt.hasNext()) {
				Aba abaVerificada = (Aba)abasAtualizadasIt.next();
				if(abaVerificada.getFuncaoFun() != null){
					if(abaVerificada.getFuncaoFun().getCodFun() == funcao.getCodFun()){
						contadorFuncoes++;
						if(contadorFuncoes > 1){						
							throw new ECARException("aba.validacao.funcao");
						}
					}
				}
			}
		}
		
        // Pega a lista de configuração dos históricos
        List configuracaoHistorico = historicoDao.listar(HistoricoConfig.class, null);
        List configuracaoHistoricoAtualizada = new ArrayList();
        Iterator configHistIt = null;

		// Pega a lista de configuração dos links
		List configuracaoLinks = linkDao.listar(Link.class, null);
		List configuracaoLinksAtualizada = new ArrayList();
		Iterator configLinkIt = null;
		
        while(itCampos.hasNext()) {
            FileItem fileItem = (FileItem) itCampos.next();
			
            String nomeArquivo = FileUpload.verificaValorCampo(campos, "hidNomeArquivo" + fileItem.getFieldName()).trim();
            
            if(!fileItem.isFormField()) {
                
            	// se for ícones do histórico
            	if(fileItem.getFieldName().startsWith("iconeHistorico")) {
            		configHistIt = configuracaoHistorico.iterator();
            		while(configHistIt.hasNext()) {
				    	historico = (HistoricoConfig) configHistIt.next();
	                	
				    	if(fileItem.getFieldName().equals("iconeHistorico"+historico.getCodHistorico())) {
		                	String status = FileUpload.verificaValorCampo(campos, "hidiconeHistorico"+historico.getCodHistorico()).trim();
		                	
					    	// se for atualização ou exclusao, apaga o icone anterior
					    	if((historico.getIconeHistorico() != null && !"".equals(fileItem.getName())) || status.equals("_excluir")) {
						    	try {
				                    FileUpload.apagarArquivo(pathRaiz + historico.getIconeHistorico());
			                    } catch (Exception e) {
			                    	org.apache.log4j.Logger.getLogger(this.getClass()).error(e);
								}
			                }
					    	
					    	// verifica se foi solicitada uma ação de exclusão
					    	// se for exclusao, seta o icone vazio. 
					    	// caso contrario, salva o icone no disco e seta o icone no historico 
		                	if (status.equals("_excluir")) {
								historico.setIconeHistorico("");
							} 
					    	if (!"".equals(fileItem.getName())) {
		                		File arquivoGravado = FileUpload.salvarNoDisco(fileItem, FileUpload.getPathFisico(pathRaiz, pathIconeLinks, FileUpload.getNomeArquivo(fileItem.getName())));
		    	                historico.setIconeHistorico(FileUpload.getPathFisico("", pathIconeLinks, FileUpload.getNomeArquivo(arquivoGravado.getName())));
		                	}
		                	
		                	// seta demais dados da config. do historico coloca o objeto atualizado na lista configuracaoHistoricoAtualizada
		                	historico.setCorHistorico(FileUpload.verificaValorCampo(campos, "corHistorico"+historico.getCodHistorico()).trim());
		                	configuracaoHistoricoAtualizada.add(historico);
				    	}
				    }

            	// se for icones de links
				} else if(fileItem.getFieldName().startsWith("iconeLink")) {
					configLinkIt = configuracaoLinks.iterator();

					while(configLinkIt.hasNext()) {
				    	link = (Link) configLinkIt.next();
				    	if(fileItem.getFieldName().equals("iconeLink"+link.getCodLink())) {
		                	String status = FileUpload.verificaValorCampo(campos, "hidiconeLink"+link.getCodLink()).trim();
		                	
					    	// se for atualização ou exclusão, apaga o icone anterior
					    	if((link.getIconeLink() != null && !"".equals(fileItem.getName())) || status.equals("_excluir")) {
						    	try {
				                    FileUpload.apagarArquivo(pathRaiz + link.getIconeLink());
			                    } catch (Exception e) {
			                    	org.apache.log4j.Logger.getLogger(this.getClass()).error(e);
								}
			                } 
							
					    	// verifica se foi solicitada uma ação de exclusão
					    	// se for exclusao, seta o icone vazio. 
					    	// caso contrario, salva o icone no disco e seta o icone no link
		                	if(status.equals("_excluir")) { 
		                		link.setIconeLink("");
		                	} 
					    	if(!"".equals(fileItem.getName())) {
		                		File arquivoGravado = FileUpload.salvarNoDisco(fileItem, FileUpload.getPathFisico(pathRaiz, pathIconeLinks, FileUpload.getNomeArquivo(fileItem.getName())));
		    	                link.setIconeLink(FileUpload.getPathFisico("", pathIconeLinks, FileUpload.getNomeArquivo(arquivoGravado.getName())));
		                	}
		                	
		                	// seta demais dados do link e coloca o objeto atualizado na lista configuracaoLinksAtualizada
		                	String exigeMonitoramentoLink = FileUpload.verificaValorCampo(campos, "exibeMonitoramentoLink"+link.getCodLink()).trim();
		                	if ((Dominios.SIM).equals(exigeMonitoramentoLink)) {
		    					link.setExibeMonitoramentoLink(Dominios.SIM);
		    				} else {
		    					link.setExibeMonitoramentoLink(Dominios.NAO);					
		    				}
		                	link.setLabelLink(FileUpload.verificaValorCampo(campos, "labelLink"+link.getCodLink()).trim());
		                	link.setOrdemLink(Integer.valueOf(FileUpload.verificaValorCampo(campos, "ordemLink"+link.getCodLink()).trim()));
		                	configuracaoLinksAtualizada.add(link);
				    	}
					}
				}
            }
        }//while
		
		// Altera os dados da configuracao de abas e icones
		abaDao.alterarAbaLinkHistorico(listAbasAtualizadas, configuracaoHistoricoAtualizada, configuracaoLinksAtualizada);
		
        //Alteração para colocar as alterações em uma única transação
		//abaDao.alterarAbaLinkHistorico(request);
		
		//abaDao.alterar(request);
		//linkDao.alterar(request);
		//historicoDao.alterar(request);
		msg = _msg.getMensagem("aba.alteracao.sucesso");
	} catch (ECARException e) {
		org.apache.log4j.Logger.getLogger(this.getClass()).error(e);
		msg = _msg.getMensagem(e.getMessageKey());
	} catch (Exception e) {
	%>
		<%@ include file="/excecao.jsp"%>
	<%
	}
	
	/* da o alert para incluir, alterar, pesquisar ou excluir */
	if (msg != null) 
		Mensagem.setInput(_jspx_page_context, "document.form.msgOperacao.value", msg); 
%>
	<script language="javascript">
		document.form.action = "<%=submit%>";
		document.form.submit();
	</script> 
</body>
</html>
