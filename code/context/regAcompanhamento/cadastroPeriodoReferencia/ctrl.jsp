<%@ page import="ecar.util.Dominios"%>
<%@ page import="org.apache.log4j.Logger"%>

<%@ include file="/login/validaAcesso.jsp"%>
<%@ include file="/frm_global.jsp"%>

<%@ page import="ecar.pojo.AcompReferenciaAref"%>
<%@ page import="ecar.dao.AcompReferenciaDao"%>
<%@ page import="ecar.pojo.OrgaoOrg"%>
<%@ page import="ecar.dao.OrgaoDao"%>

<%@ page import="comum.util.Data"%>

<%@ page import="java.util.ArrayList"%>
<%@ page import="java.util.List"%>
<%@ page import="java.util.Iterator"%>
<%@ page import="ecar.dao.TipoAcompanhamentoDao"%>
<%@ page import="ecar.pojo.TipoAcompanhamentoTa"%>
<%@ page import="ecar.dao.ConfigMailCfgmDAO"%>
<%@ page import="ecar.pojo.ConfigMailCfgm"%>
<%@ page import="ecar.dao.AcompReferenciaItemDao"%>
<%@ page import="ecar.pojo.ItemEstUsutpfuacIettutfa"%>
<%@ page import="ecar.pojo.TfuncacompConfigmailTfacfgmPK"%>
<%@ page import="ecar.pojo.TfuncacompConfigmailTfacfgm"%>
<%@ page import="ecar.dao.TfuncacompConfigmailTfacfgmDAO"%>
<%@ page import="ecar.dao.UsuarioDao"%>
<%@ page import="ecar.pojo.UsuarioUsu"%>
<%@ page import="ecar.pojo.EmpresaEmp"%>
<%@ page import="ecar.dao.EmpresaDao"%>
<%@ page import="ecar.email.AgendadorEmail"%>
<%@ page import="comum.util.Util"%>
<%@ page import="ecar.pojo.AcompRefLimitesArl"%>
<%@ page import="ecar.dao.ItemEstruturaDao"%>
<%@ page import="ecar.pojo.ItemEstruturaIett"%>

<html lang="pt-br">
<head>
<%@ include file="/include/meta.jsp"%>
</head>
<body>
<form name="form" method="post">

	<input type="hidden" name="hidAcao"	value="<%=Pagina.getParamStr(request, "hidAcao")%>"> 

	<!-- lista.jsp -->
	<input type="hidden" name="codTipoAcompanhamento" value="<%=Pagina.getParamStr(request, "codTipoAcompanhamento")%>">
	<input type="hidden" name="codigo" value="<%=Pagina.getParamStr(request, "codigo")%>">
	
	 
	<input type="hidden" name="codOrg" value="<%=Pagina.getParamStr(request, "orgaoOrg")%>"> 
	<input type="hidden" name="niveisPlanejamento" value="<%=Pagina.getParamStr(request, "niveisPlanejamento")%>">
	 
	
	<!-- campo de controle para mensagens -->
	<input type="hidden" name="msgOperacao" value=""> 
	
	<!--  campos de controle para envio de e-mails -->
	<input type="hidden" name="indDataAlterada" value=""> 
	<input type="hidden" name="indDataRegistroAlterada" value="<%=Dominios.NAO%>"> 
	<input type="hidden" name="indDataParecerAlterada" value="<%=Dominios.NAO%>"> 
<%
	ConfigMailCfgmDAO mailDao = new ConfigMailCfgmDAO();
	ConfigMailCfgm    mail    = null;

	String msg = "";
	String submit = "";
	
	// acompanhamento físico
	String dataAcompFisicoAnt = "";
	String dataAcompFisicoAlt = "";
	String diaAref = Pagina.getParamStr(request, "diaAref");
	String anoAref = Pagina.getParamStr(request, "anoAref");
	String possuiOrgaoInformado = Pagina.getParamStr(request, "checkItensOrgaoInformado");
	
	
	
	int indAlterouDataLimite = 0;
	
	try{
		AcompReferenciaAref acompReferencia = new AcompReferenciaAref();
		AcompReferenciaDao  acompReferenciaDao = new AcompReferenciaDao(request);
   		TipoAcompanhamentoDao taDao = new TipoAcompanhamentoDao(request);
   		
   		 
   		TipoAcompanhamentoTa ta;
				
		ValidaPermissao validaPermissao = new ValidaPermissao();
		
		List listaAcompReferencia = new ArrayList();
		
		if("incluir".equals(Pagina.getParamStr(request, "hidAcao"))){
			try {
			
						
				mail = (ConfigMailCfgm) mailDao.buscar(ConfigMailCfgm.class, Dominios.CFG_MAIL_CRIACAO_REGISTRO_ACOMPANHAMENTO);
			
    			ta = (TipoAcompanhamentoTa)taDao.buscar(
		    			TipoAcompanhamentoTa.class, Long.valueOf(Pagina.getParamStr(request, "tipoAcompanhamento")));
		    	// para evitar o problema do lazy load.
				ta.getSituacaoSits().size();
		    	ta.getSisAtributoSatbs().size();
				List secretarias = new ArrayList();
				if("".equals(possuiOrgaoInformado)){
					possuiOrgaoInformado = "S";
				}
				
				
				// se for "S" o indicador de separar por órgão do tipo de acompanhamento
				if (Dominios.SIM.equals(ta.getIndSepararOrgaoTa())) {
				
					boolean acessoSomenteUsuariosOrgaos = validaPermissao.permissaoAcessoReferenciaSeusOrgaos(ta, seguranca.getGruposAcesso());
					//se for gerar apenas para os itens com orgaos nao informados
					if("N".equals(possuiOrgaoInformado)) {
					
						acompReferencia = new AcompReferenciaAref();
						acompReferenciaDao.setAcompReferencia(request, acompReferencia, null, ta);
						acompReferencia.setDataInclusaoAref(Data.getDataAtual());
						if(acompReferenciaDao.existeMesmaReferenciaDiaMesAno(acompReferencia)) {
				    		throw new ECARException("periodoReferencia.validacao.mesAnoTipoAcompanhamento.jaExistente");
				    	}
						listaAcompReferencia.add(acompReferencia);
					
					// se for para todos os órgãos presentes na combo da tela
					} else if("".equals(Pagina.getParamStr(request, "orgaoOrg"))) {
					
						//retorna todos os orgaos se em grupo de acesso estiver setado para gerar periodo para todos os orgaos
						// ou retorna apenas os orgaos do usuário se estiver setado para somente o seu
						secretarias = taDao.getOrgaosAcessoUsuarioPorTipoAcompanhamento(seguranca, ta, true);
						Iterator itOrgao = secretarias.iterator();
	
						for(Iterator it = secretarias.iterator();it.hasNext();){
							OrgaoOrg o = (OrgaoOrg) it.next();
							if (o.getIndAtivoOrg() != null && o.getIndAtivoOrg().equals(Dominios.ATIVO)){
								acompReferencia = new AcompReferenciaAref();
								acompReferencia.setDataInclusaoAref(Data.getDataAtual());
								acompReferenciaDao.setAcompReferencia(request, acompReferencia, o.getCodOrg(), ta);
								listaAcompReferencia.add(acompReferencia);	
							}
						}
						
						
						// se a opção for para todos os orgaos , adicionar os itens sem informação
						if(!acessoSomenteUsuariosOrgaos) {
							acompReferencia = new AcompReferenciaAref();
							acompReferenciaDao.setAcompReferencia(request, acompReferencia, null, ta);
							acompReferencia.setIndInformacaoOrgaoAref("N");
							acompReferencia.setDataInclusaoAref(Data.getDataAtual());
							listaAcompReferencia.add(acompReferencia);
						}
						
						
					}
					// se for para um órgão específico selecionado no combo
					else {
						
					
						acompReferencia = new AcompReferenciaAref();
						acompReferenciaDao.setAcompReferencia(request, acompReferencia, Long.valueOf(Pagina.getParamStr(request, "orgaoOrg")), ta);	
						acompReferencia.setDataInclusaoAref(Data.getDataAtual());
						if(acompReferenciaDao.existeMesmaReferenciaDiaMesAno(acompReferencia)) {
				    		throw new ECARException("periodoReferencia.validacao.mesAnoTipoAcompanhamento.jaExistente");
				    	}
						listaAcompReferencia.add(acompReferencia);
					}
				} else {
					// "N" o indicador de separar por órgão do tipo de acompanhamento, o órgão do AREF será NULO
					acompReferencia = new AcompReferenciaAref();
					acompReferenciaDao.setAcompReferencia(request, acompReferencia, null, ta);
					acompReferencia.setDataInclusaoAref(Data.getDataAtual());
					if(acompReferenciaDao.existeMesmaReferenciaDiaMesAno(acompReferencia)) {
			    		throw new ECARException("periodoReferencia.validacao.mesAnoTipoAcompanhamento.jaExistente");
			    	}
					listaAcompReferencia.add(acompReferencia);
				}

				session.setAttribute("listaAcompReferencia", listaAcompReferencia);
				session.setAttribute("periodoRef", request.getParameter("nomeAref"));
								
				submit = "selecaoItem.jsp";
			} catch(ECARException e) {
				Logger.getLogger(this.getClass()).error(e);
				submit = "frm_inc.jsp";
				msg = _msg.getMensagem(e.getMessageKey());
			}
		}	
		else if("alterar".equals(Pagina.getParamStr(request, "hidAcao"))){	
		
			mail = (ConfigMailCfgm) mailDao.buscar(ConfigMailCfgm.class, Dominios.CFG_MAIL_ALTERAR_DATA_LIMITE_REGISTRO_FISICO);
									
   			ta = (TipoAcompanhamentoTa) taDao.buscar(
		    			TipoAcompanhamentoTa.class, Long.valueOf(Pagina.getParamStr(request, "hidtipoAcompanhamento")));
			ta.getSituacaoSits().size();
			ta.getSisAtributoSatbs().size();
			acompReferencia = (AcompReferenciaAref) acompReferenciaDao.buscar(AcompReferenciaAref.class, Long.valueOf(Pagina.getParamStr(request, "codigo")));		
						
			List listArl = new ArrayList(acompReferencia.getAcompRefLimitesArls());
			AcompRefLimitesArl arl = null ;
		
			if( listArl != null && listArl.size() > 0 ) {
				Iterator itArl = listArl.iterator();
				
				while( itArl.hasNext() ) {
					arl = (AcompRefLimitesArl) itArl.next(); %> 
					
					<!-- data limite para (pega da tela anterior e do banco) ... -->
					<input type="hidden" name="prazoFinalPara<%=arl.getTipoFuncAcompTpfa().getCodTpfa()%>" value="<%=Pagina.getParamStr(request, "prazoFinalPara"+arl.getTipoFuncAcompTpfa().getCodTpfa())%>">
					<input type="hidden" name="prazoFinalParaAnt<%=arl.getTipoFuncAcompTpfa().getCodTpfa()%>" value="<%=Pagina.trocaNullData(arl.getDataLimiteArl())%>"> <%
					
					if( !Pagina.getParamStr(request, "prazoFinalPara"+arl.getTipoFuncAcompTpfa().getCodTpfa()).equals(Pagina.trocaNullData(arl.getDataLimiteArl())) ) {
						indAlterouDataLimite = 1;
					}
				}				
			}
			
			// apenas para os dados do Limite de Acompanhamento Fisico %>
			<input type="hidden" name="dataLimiteAcompFisicoArefAlt" value="<%=Pagina.getParamStr(request, "dataLimiteAcompFisicoAref")%>">
			<input type="hidden" name="dataLimiteAcompFisicoArefAnt" value="<%=Data.parseDate(acompReferencia.getDataLimiteAcompFisicoAref())%>"> <%
					
			try {
				
				

				List secretarias = new ArrayList();	
				
				// se for "S" o indicador de separar por órgão do tipo de acompanhamento
				//se for para separar por orgao e a opção é todos os órgãos
				/* Obs: a validação acompReferencia.getCodAref() == null serve para no caso da referencia ter sido gerada sem separacao por orgaos 
				e o tipo de acompanhamento ter sido modificado apos a geracao para separado por orgaos 	
				hoje o tipo de acompanhamento nao permite essa alteração caso ja tenha gerado um periodo. 
				Quando o tipo de acompanhemento é separado por órgão, a opção todos na separação por órgão só acontece na inclusão 
				(quando o codAref  é igual a null), porque depois o sistema gera uma referencia para cada orgao. 
				O orgao == null e com orgao informado = S só acontece quando o tipo de acompanhamento nao é separado por orgao. 
				*/
				if (Dominios.SIM.equals(ta.getIndSepararOrgaoTa()) && acompReferencia.getOrgaoOrg() == null && Dominios.SIM.equals(acompReferencia.getIndInformacaoOrgaoAref()) 
					&& acompReferencia.getCodAref() == null) {
					
					
					boolean acessoSomenteUsuariosOrgaos = validaPermissao.permissaoAcessoReferenciaSeusOrgaos(ta, seguranca.getGruposAcesso());
					
					//retorna todos os orgaos se em grupo de acesso estiver setado para gerar periodo para todos os orgaos
					// ou retorna apenas os orgaos do usuário se estiver setado para somente o seu
					secretarias = taDao.getOrgaosAcessoUsuarioPorTipoAcompanhamento(seguranca, ta, false);
					Iterator itOrgao = secretarias.iterator();
	
					for(Iterator it = secretarias.iterator();it.hasNext();){
						OrgaoOrg o = (OrgaoOrg) it.next();
						acompReferenciaDao.setAcompReferencia(request, acompReferencia, o.getCodOrg(), ta);
						listaAcompReferencia.add(acompReferencia);
					}
						
					// se a opção for para todos os orgaos, adicionar os itens sem informação
					if(!acessoSomenteUsuariosOrgaos) {
						acompReferenciaDao.setAcompReferencia(request, acompReferencia, null, ta);	
						listaAcompReferencia.add(acompReferencia);
					}
					
					
				} else {
					
					if ( !"".equals(Pagina.getParamStr(request, "orgaoOrg"))  ){
						acompReferenciaDao.setAcompReferencia(request, acompReferencia, Long.valueOf(Pagina.getParamStr(request, "orgaoOrg")), ta);
					} else {
						acompReferenciaDao.setAcompReferencia(request, acompReferencia, null, ta);
					}

					listaAcompReferencia.add(acompReferencia);
				}
				session.setAttribute("listaAcompReferencia", listaAcompReferencia);
				session.setAttribute("periodoRef", request.getParameter("nomeAref"));
				submit = "selecaoItem.jsp";
			} catch(ECARException e) {
				Logger.getLogger(this.getClass()).error(e);
				submit = "frm_alt.jsp";
				msg = _msg.getMensagem(e.getMessageKey());
			}
		} else if("excluir".equals(Pagina.getParamStr(request, "hidAcao"))){
			String codigosParaExcluir[] = request.getParameterValues(Pagina.getParamStr(request, "nomeCheckBox"));
			try{
				acompReferenciaDao.excluir(codigosParaExcluir);
				msg = _msg.getMensagem("periodoReferencia.exclusao.sucesso");
			}
			catch (ECARException e){
				Logger.getLogger(this.getClass()).error(e);
				msg = _msg.getMensagem(e.getMessageKey());
				if(e.getMessageArgs() != null) {
					for(int k = 0; k < e.getMessageArgs().length; k++) {
						msg += ": " + e.getMessageArgs()[k];
					}
				}
			}
			submit = "lista.jsp";
		}
		
		if(!"excluir".equals(Pagina.getParamStr(request, "hidAcao"))){ %> 
			<script>document.form.codigo.value = '<%=acompReferencia.getCodAref()%>';</script> <%
		}
	}
	catch (ECARException e) { 
		Logger.getLogger(this.getClass()).error(e);
		submit = "lista.jsp";
		msg = _msg.getMensagem(e.getMessageKey());
	} catch (Exception e){
	%> <%@ include file="/excecao.jsp"%> <%
	}
	if (msg != null)
		Mensagem.setInput(_jspx_page_context, "document.form.msgOperacao.value", msg); %> 
	
	<script>
		document.form.action = "<%=submit%>"; 
			
		<%
		if( "alterar".equals(Pagina.getParamStr(request, "hidAcao"))) { %>
			var config  = "<%=mail.getAtivoCfgm()%>";		
			if(config == 'S') {

				var indDatasAlt  = false; 			
	
				/* -- acompanhamento físico -- */
				var dataAcFisAnt = document.form.dataLimiteAcompFisicoArefAnt.value;
				var dataAcFisAlt = document.form.dataLimiteAcompFisicoArefAlt.value;	
			
				/* -- valida se alguma das datas sofreu alteração -- */
				if( dataAcFisAnt != dataAcFisAlt ) {
					indDatasAlt = true;
					document.form.indDataRegistroAlterada.value = 'S';
				}
			
				if( '<%=indAlterouDataLimite%>' == '1' ) {
					indDatasAlt = true;
					document.form.indDataParecerAlterada.value = 'S';
				}
		
			
				/* -- 
				 *	se alguma data sofreu alteração, se for a primeira vez que está se passando pela página e
				 *	e a configuração da estrutura permitir o envio de e-mails.
				 * -- */		
				if( indDatasAlt ) {
					document.form.indDataAlterada.value = 'S';
				} 
			}<%
		} else if( "incluir".equals(Pagina.getParamStr(request, "hidAcao")) ){ %>
			var config  = "<%=mail.getAtivoCfgm()%>";		
			if(config == 'S') {
				document.form.indDataAlterada.value = 'S'; 
			} 
			<%
		}%>
		document.form.submit();
	</script>
	
</form>
</body>
</html>
