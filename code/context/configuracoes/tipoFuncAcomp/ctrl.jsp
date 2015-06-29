<%@ include file="/login/validaAcesso.jsp"%>
<%@ include file="/frm_global.jsp"%>

<%@ page import="ecar.pojo.TipoFuncAcompTpfa" %>
<%@ page import="ecar.dao.TipoFuncAcompDao" %>
<%@ page import="java.util.List" %>
<%@ page import="ecar.pojo.TfuncacompConfigmailTfacfgm" %>
<%@ page import="ecar.pojo.TfuncacompConfigmailTfacfgmPK" %>
<%@ page import="ecar.dao.ConfigMailCfgmDAO" %>
<%@ page import="java.util.Iterator" %>
<%@ page import="ecar.pojo.ConfigMailCfgm" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="ecar.dao.TfuncacompConfigmailTfacfgmDAO" %>
<%@ page import="ecar.util.Dominios"%>


<%@page import="ecar.pojo.TipoFuncAcompTpfaPermiteAlterar"%>
<%@page import="ecar.pojo.TipoFuncAcompTpfaPermiteAlterarPK"%>
<%@page import="java.util.Set"%>
<%@page import="java.util.HashSet"%><html lang="pt-br">
<head>
<%@ include file="/include/meta.jsp"%>
<%@ include file="/titulo.jsp"%>
</head>
<body>

<form name="form" method="post" action="">
    <!-- campo de controle para a navegação de registros -->
	<input type="hidden" name="hidRegistro" value='<%=Pagina.getParamInt(request, "hidRegistro")%>'>
	<input type="hidden" name="msgOperacao" value=""> 
	<input type="hidden" name="msgPesquisa" value="">	
	<input type="hidden" name="hidPesquisou" value="">
</form>

<%
	TipoFuncAcompTpfa tipoFuncAcomp = new TipoFuncAcompTpfa();
	TipoFuncAcompDao tipoFuncAcompDao = new TipoFuncAcompDao(request);
	Mensagem mensagem = new Mensagem(application);
	
	String hidAcao = Pagina.getParam(request, "hidAcao");
	String msg = null;
	String submit = "frm_pes.jsp";
	
	boolean refazPesquisa = true;
	
	if ("incluir".equalsIgnoreCase(hidAcao)) {
		/* melhor usar getParamStr, pois nunca devolve null */		
		tipoFuncAcomp.setDescricaoTpfa(Pagina.getParamStr(request, "descricaoTpfa").trim()); 
		tipoFuncAcomp.setLabelTpfa(Pagina.getParamStr(request, "labelTpfa").trim());
		if( Pagina.getParamStr(request, "indLerItemEstrutura") != null && 
			!(Pagina.getParamStr(request, "indLerItemEstrutura").trim()).equals("")) {
			tipoFuncAcomp.setIndLerItemEstrutura(Pagina.getParamStr(request, "indLerItemEstrutura").trim());
		} else {
			tipoFuncAcomp.setIndLerItemEstrutura(Dominios.NAO);
		}
		if( Pagina.getParamStr(request, "indAlterarItemEstrutura") != null && 
			!(Pagina.getParamStr(request, "indAlterarItemEstrutura").trim()).equals("")) {
			tipoFuncAcomp.setIndAlterarItemEstrutura(Pagina.getParamStr(request, "indAlterarItemEstrutura").trim());
		} else {
			tipoFuncAcomp.setIndAlterarItemEstrutura(Dominios.NAO);
		}
		if( Pagina.getParamStr(request, "indExcluirItemEstrutura") != null && 
			!(Pagina.getParamStr(request, "indExcluirItemEstrutura").trim()).equals("")) {
			tipoFuncAcomp.setIndExcluirItemEstrutura(Pagina.getParamStr(request, "indExcluirItemEstrutura").trim());
		} else {
			tipoFuncAcomp.setIndExcluirItemEstrutura(Dominios.NAO);
		}
		if( Pagina.getParamStr(request, "indVisualizarParecer") != null && 
			!(Pagina.getParamStr(request, "indVisualizarParecer").trim()).equals("")) {
			tipoFuncAcomp.setIndVisualizarParecer(Pagina.getParamStr(request, "indVisualizarParecer").trim());
		} else {
			tipoFuncAcomp.setIndVisualizarParecer(Dominios.NAO);
		}
		if( Pagina.getParamStr(request, "indBloquearPlanejamento") != null && 
			!(Pagina.getParamStr(request, "indBloquearPlanejamento").trim()).equals("")) {
			tipoFuncAcomp.setIndBloquearPlanejamento(Pagina.getParamStr(request, "indBloquearPlanejamento").trim());
		} else {
			tipoFuncAcomp.setIndBloquearPlanejamento(Dominios.NAO);
		}
		if( Pagina.getParamStr(request, "indDesbloquearPlanejamento") != null && 
			!(Pagina.getParamStr(request, "indDesbloquearPlanejamento").trim()).equals("")) {
			tipoFuncAcomp.setIndDesbloquearPlanejamento(Pagina.getParamStr(request, "indDesbloquearPlanejamento").trim());
		} else {
			tipoFuncAcomp.setIndDesbloquearPlanejamento(Dominios.NAO);
		}
		if( Pagina.getParamStr(request, "indAtivarMonitoramento") != null && 
			!(Pagina.getParamStr(request, "indAtivarMonitoramento").trim()).equals("")) {
			tipoFuncAcomp.setIndAtivarMonitoramento(Pagina.getParamStr(request, "indAtivarMonitoramento").trim());
		} else {
			tipoFuncAcomp.setIndAtivarMonitoramento(Dominios.NAO);
		}
		if( Pagina.getParamStr(request, "indDesativarMonitoramento") != null && 
			!(Pagina.getParamStr(request, "indDesativarMonitoramento").trim()).equals("")) {
			tipoFuncAcomp.setIndDesativarMonitoramento(Pagina.getParamStr(request, "indDesativarMonitoramento").trim());
		} else {
			tipoFuncAcomp.setIndDesativarMonitoramento(Dominios.NAO);
		}
		if( Pagina.getParamStr(request, "indInformaAndamentoTpfa")!= null && 
			!(Pagina.getParamStr(request, "indInformaAndamentoTpfa").trim()).equals("")) {
			tipoFuncAcomp.setIndInformaAndamentoTpfa(Pagina.getParamStr(request, "indInformaAndamentoTpfa").trim());
		} else {
			tipoFuncAcomp.setIndInformaAndamentoTpfa(Dominios.NAO);
		}
		if( Pagina.getParamStr(request, "indEmitePosicaoTpfa") != null &&
			!(Pagina.getParamStr(request, "indEmitePosicaoTpfa").trim()).equals("")) {
			tipoFuncAcomp.setIndEmitePosicaoTpfa(Pagina.getParamStr(request, "indEmitePosicaoTpfa").trim());
		} else {
			tipoFuncAcomp.setIndEmitePosicaoTpfa(Dominios.NAO);
		}
		if(Pagina.getParamStr(request, "indAtualizaSituacaoCadastro") != null &&
				!(Pagina.getParamStr(request, "indAtualizaSituacaoCadastro").trim()).equals("")) {
			tipoFuncAcomp.setIndAtualizaSituacaoCadastro(Pagina.getParamStr(request, "indAtualizaSituacaoCadastro").trim());
		}else {
			tipoFuncAcomp.setIndAtualizaSituacaoCadastro(Dominios.NAO);
		}
		tipoFuncAcomp.setDocumentacaoTpfa(Pagina.getParamStr(request, "documentacaoTpfa").trim());
		
		//verificação dos checks emite monitora e não monitorado
		/*
		Comentado Ref. Bug 5255
		
		tipoFuncAcomp.setIndInitMonitTpfa("N");
		tipoFuncAcomp.setIndNaoMonitTpfa("N");
		if ("S".equals(tipoFuncAcomp.getIndEmitePosicaoTpfa())) {
			if ("S".equals(Pagina.getParamStr(request, "indInitMonitTpfa").trim())) {
				tipoFuncAcomp.setIndInitMonitTpfa("S");
			}
			if ("S".equals(Pagina.getParamStr(request, "indNaoMonitTpfa").trim())) {
				tipoFuncAcomp.setIndNaoMonitTpfa("S");
			}
		}
		*/
		
		tipoFuncAcomp.setLabelPosicaoTpfa(Pagina.getParamStr(request, "labelPosicaoTpfa").trim());
		tipoFuncAcomp.setTamanhoSinalTpfa(Integer.valueOf(Pagina.getParamInt(request, "tamanhoSinalTpfa")));
		refazPesquisa = false;
		submit = "frm_inc.jsp";
		try {
			if(Pagina.getParam(request, "tipoFuncAcompTpfaPai") != null)
				tipoFuncAcomp.setTipoFuncAcompTpfa( (TipoFuncAcompTpfa) tipoFuncAcompDao.buscar(TipoFuncAcompTpfa.class, Long.valueOf(Pagina.getParam(request, "tipoFuncAcompTpfaPai"))));
				
			/* -- add por rogeriom -- */
			String[] listMail = request.getParameterValues("chkMailCfgM");
			String[] listSMS  = request.getParameterValues("chkSMSCfgM");
					
			ConfigMailCfgmDAO configMailDao = new ConfigMailCfgmDAO(request);
						
			List listaConfigMail = configMailDao.listar();
			List listaTFuncAcomp = new ArrayList();
			
			Iterator it = listaConfigMail.iterator();
			
			while( it.hasNext() ) {
				TfuncacompConfigmailTfacfgm tFuncAcomp = new TfuncacompConfigmailTfacfgm();
				TfuncacompConfigmailTfacfgmPK tFuncAcompPk = new TfuncacompConfigmailTfacfgmPK();
				
				ConfigMailCfgm configMail = (ConfigMailCfgm) it.next();
				
				//tFuncAcompPk.setCodTpfa(tipoFuncAcomp.getCodTpfa());
				tFuncAcompPk.setCodCfgm(configMail.getCodCfgm());
				tFuncAcomp.setComp_id(tFuncAcompPk);
				
				tFuncAcomp.setEnviaMailTfacfgm(String.valueOf("N"));
				tFuncAcomp.setEnviaSms(String.valueOf("N"));
				
				// email
				if( listMail != null ) {
					for( int i=0; i<listMail.length; i++ ) {
						if( configMail.getCodCfgm().equals(Integer.valueOf(listMail[i])) ) {
							tFuncAcomp.setEnviaMailTfacfgm(String.valueOf("S"));
						}
					}
				}
				
				// sms
				if( listSMS != null ) {
					for( int i=0; i<listSMS.length; i++ ) {
						if( configMail.getCodCfgm().equals(Integer.valueOf(listSMS[i])) ) {
							tFuncAcomp.setEnviaSms(String.valueOf("S"));
						}
					}
				}
				
				listaTFuncAcomp.add(tFuncAcomp);
			}
			/* -- add por rogeriom -- */
			
			//tipoFuncAcompDao.salvar(tipoFuncAcomp);
			tipoFuncAcompDao.salvar(tipoFuncAcomp, listaTFuncAcomp);
			
			msg = _msg.getMensagem("tipoFuncAcomp.inclusao.sucesso" );
		} catch (ECARException e) {
			org.apache.log4j.Logger.getLogger(this.getClass()).error(e);
			msg = _msg.getMensagem(e.getMessageKey());
		} catch (Exception e){
		%>
			<%@ include file="/excecao.jsp"%>
		<%
		}
	} else if ("alterar".equalsIgnoreCase(hidAcao)) {
		try {			/* o buscar deve estar no try, pois pode ocorrer um erro de hibernate ou bd */
			tipoFuncAcomp = (TipoFuncAcompTpfa) tipoFuncAcompDao.buscarComDescendentes(TipoFuncAcompTpfa.class, Long.valueOf(Pagina.getParamLong(request, "codigo")));
			
			tipoFuncAcomp.setDescricaoTpfa(Pagina.getParamStr(request, "descricaoTpfa").trim()); 
			tipoFuncAcomp.setLabelTpfa(Pagina.getParamStr(request, "labelTpfa").trim());
			
			if( Pagina.getParamStr(request, "indLerItemEstrutura") != null && 
				!(Pagina.getParamStr(request, "indLerItemEstrutura").trim()).equals("")) {
				tipoFuncAcomp.setIndLerItemEstrutura(Pagina.getParamStr(request, "indLerItemEstrutura").trim());
			} else {
				tipoFuncAcomp.setIndLerItemEstrutura(Dominios.NAO);
			}
			if( Pagina.getParamStr(request, "indAlterarItemEstrutura") != null && 
				!(Pagina.getParamStr(request, "indAlterarItemEstrutura").trim()).equals("")) {
				tipoFuncAcomp.setIndAlterarItemEstrutura(Pagina.getParamStr(request, "indAlterarItemEstrutura").trim());
			} else {
				tipoFuncAcomp.setIndAlterarItemEstrutura(Dominios.NAO);
			}
			if( Pagina.getParamStr(request, "indExcluirItemEstrutura") != null && 
				!(Pagina.getParamStr(request, "indExcluirItemEstrutura").trim()).equals("")) {
				tipoFuncAcomp.setIndExcluirItemEstrutura(Pagina.getParamStr(request, "indExcluirItemEstrutura").trim());
			} else {
				tipoFuncAcomp.setIndExcluirItemEstrutura(Dominios.NAO);
			}
			if( Pagina.getParamStr(request, "indVisualizarParecer") != null && 
				!(Pagina.getParamStr(request, "indVisualizarParecer").trim()).equals("")) {
				tipoFuncAcomp.setIndVisualizarParecer(Pagina.getParamStr(request, "indVisualizarParecer").trim());
			} else {
				tipoFuncAcomp.setIndVisualizarParecer(Dominios.NAO);
			}
			if( Pagina.getParamStr(request, "indBloquearPlanejamento") != null && 
				!(Pagina.getParamStr(request, "indBloquearPlanejamento").trim()).equals("")) {
				tipoFuncAcomp.setIndBloquearPlanejamento(Pagina.getParamStr(request, "indBloquearPlanejamento").trim());
			} else {
				tipoFuncAcomp.setIndBloquearPlanejamento(Dominios.NAO);
			}
			if( Pagina.getParamStr(request, "indDesbloquearPlanejamento") != null && 
				!(Pagina.getParamStr(request, "indDesbloquearPlanejamento").trim()).equals("")) {
				tipoFuncAcomp.setIndDesbloquearPlanejamento(Pagina.getParamStr(request, "indDesbloquearPlanejamento").trim());
			} else {
				tipoFuncAcomp.setIndDesbloquearPlanejamento(Dominios.NAO);
			}
			if( Pagina.getParamStr(request, "indAtivarMonitoramento") != null && 
				!(Pagina.getParamStr(request, "indAtivarMonitoramento").trim()).equals("")) {
				tipoFuncAcomp.setIndAtivarMonitoramento(Pagina.getParamStr(request, "indAtivarMonitoramento").trim());
			} else {
				tipoFuncAcomp.setIndAtivarMonitoramento(Dominios.NAO);
			}
			if( Pagina.getParamStr(request, "indDesativarMonitoramento") != null && 
				!(Pagina.getParamStr(request, "indDesativarMonitoramento").trim()).equals("")) {
				tipoFuncAcomp.setIndDesativarMonitoramento(Pagina.getParamStr(request, "indDesativarMonitoramento").trim());
			} else {
				tipoFuncAcomp.setIndDesativarMonitoramento(Dominios.NAO);
			}
			if( Pagina.getParamStr(request, "indInformaAndamentoTpfa")!= null && 
				!(Pagina.getParamStr(request, "indInformaAndamentoTpfa").trim()).equals("")) {
				tipoFuncAcomp.setIndInformaAndamentoTpfa(Pagina.getParamStr(request, "indInformaAndamentoTpfa").trim());
			} else {
				tipoFuncAcomp.setIndInformaAndamentoTpfa(Dominios.NAO);
			}
			if( Pagina.getParamStr(request, "indEmitePosicaoTpfa") != null &&
				!(Pagina.getParamStr(request, "indEmitePosicaoTpfa").trim()).equals("")) {
				tipoFuncAcomp.setIndEmitePosicaoTpfa(Pagina.getParamStr(request, "indEmitePosicaoTpfa").trim());
			} else {
				tipoFuncAcomp.setIndEmitePosicaoTpfa(Dominios.NAO);
			}
			tipoFuncAcomp.setDocumentacaoTpfa(Pagina.getParamStr(request, "documentacaoTpfa").trim());
			
			//verificação dos checks emite monitora e não monitorado
			/*
			Comentado Ref. Bug 5255
			
			tipoFuncAcomp.setIndInitMonitTpfa("N");
			tipoFuncAcomp.setIndNaoMonitTpfa("N");
			if ("S".equals(tipoFuncAcomp.getIndEmitePosicaoTpfa())) {
				if ("S".equals(Pagina.getParamStr(request, "indInitMonitTpfa").trim())) {
					tipoFuncAcomp.setIndInitMonitTpfa("S");
				}
				if ("S".equals(Pagina.getParamStr(request, "indNaoMonitTpfa").trim())) {
					tipoFuncAcomp.setIndNaoMonitTpfa("S");
				}
			}
			*/
			
			tipoFuncAcomp.setLabelPosicaoTpfa(Pagina.getParamStr(request, "labelPosicaoTpfa").trim());
			tipoFuncAcomp.setTamanhoSinalTpfa(Integer.valueOf(Pagina.getParamInt(request, "tamanhoSinalTpfa")));
			
			if(Pagina.getParam(request, "tipoFuncAcompTpfaPai") != null)
				tipoFuncAcomp.setTipoFuncAcompTpfa( (TipoFuncAcompTpfa) tipoFuncAcompDao.buscar(TipoFuncAcompTpfa.class, Long.valueOf(Pagina.getParam(request, "tipoFuncAcompTpfaPai"))));
			else {
				tipoFuncAcomp.setTipoFuncAcompTpfa(null);
			}
			
		//testar isso terça!!!	
			TipoFuncAcompTpfa tipoFuncAcompSuperiorAnterior = (TipoFuncAcompTpfa) request.getSession().getAttribute("tipoFuncAcompTpfaPaiAnterior");
			
			if( tipoFuncAcomp.getTipoFuncAcompTpfa() == null && tipoFuncAcompSuperiorAnterior == null && 
					tipoFuncAcomp.getTipoFuncAcompTpfasPermiteAlterarSuperior()!=null && tipoFuncAcomp.getTipoFuncAcompTpfasPermiteAlterarSuperior().size() > 0 ){

					Iterator it = tipoFuncAcomp.getTipoFuncAcompTpfasPermiteAlterarSuperior().iterator();
					while(it.hasNext()){
						TipoFuncAcompTpfaPermiteAlterar tipo = (TipoFuncAcompTpfaPermiteAlterar)it.next();
						if(tipo.getComp_id().getCod_inferior_tpfapa().getCodTpfa() == tipoFuncAcomp.getCodTpfa()){
							tipo.setPermiteAlterarParecer("N");
						}
					}
			} else if( (tipoFuncAcomp.getTipoFuncAcompTpfa() == null && tipoFuncAcompSuperiorAnterior != null ) || 
					( tipoFuncAcomp.getTipoFuncAcompTpfa() != null && tipoFuncAcompSuperiorAnterior != null && tipoFuncAcomp.getTipoFuncAcompTpfa().getCodTpfa() != tipoFuncAcompSuperiorAnterior.getCodTpfa() )){
				TipoFuncAcompTpfaPermiteAlterarPK tipoPK = new TipoFuncAcompTpfaPermiteAlterarPK();
				tipoPK.setCod_inferior_tpfapa(tipoFuncAcomp);
				tipoPK.setCod_superior_tpfapa(tipoFuncAcompSuperiorAnterior);
				
				if(tipoFuncAcomp.getTipoFuncAcompTpfasPermiteAlterarSuperior()!=null){
					Iterator it = tipoFuncAcomp.getTipoFuncAcompTpfasPermiteAlterarSuperior().iterator();
					while(it.hasNext()){
						TipoFuncAcompTpfaPermiteAlterar tipo = (TipoFuncAcompTpfaPermiteAlterar)it.next();
						if(tipo.getComp_id().equals(tipoPK)){
							tipo.setPermiteAlterarParecer("N");
						}
					}
				} else{
					TipoFuncAcompTpfaPermiteAlterar tipoPermiteAlterar = new TipoFuncAcompTpfaPermiteAlterar();
					tipoPermiteAlterar.setComp_id(tipoPK);
					tipoPermiteAlterar.setPermiteAlterarParecer("N");
					
					//Set listaSuperiores = new HashSet(  );
					//listaSuperiores.add(tipoPermiteAlterar);
					tipoFuncAcomp.getTipoFuncAcompTpfasPermiteAlterarSuperior().add(tipoPermiteAlterar);
				}
			}
			
			String[] valores = Pagina.getParamLista(request, "filhosTPFA");

			tipoFuncAcompDao.montarFilhos(tipoFuncAcomp, valores);
			
			
			/* -- add por rogeriom -- */
			String[] listMail = request.getParameterValues("chkMailCfgM");
			String[] listSMS  = request.getParameterValues("chkSMSCfgM");
					
			ConfigMailCfgmDAO configMailDao = new ConfigMailCfgmDAO(request);
						
			List listaConfigMail = configMailDao.listar();
			List listaTFuncAcomp = new ArrayList();
			
			Iterator it = listaConfigMail.iterator();
			
			while( it.hasNext() ) {
				TfuncacompConfigmailTfacfgm tFuncAcomp = new TfuncacompConfigmailTfacfgm();
				TfuncacompConfigmailTfacfgmPK tFuncAcompPk = new TfuncacompConfigmailTfacfgmPK();
				
				ConfigMailCfgm configMail = (ConfigMailCfgm) it.next();
				
				tFuncAcompPk.setCodTpfa(tipoFuncAcomp.getCodTpfa());
				tFuncAcompPk.setCodCfgm(configMail.getCodCfgm());
				tFuncAcomp.setComp_id(tFuncAcompPk);
				
				tFuncAcomp.setEnviaMailTfacfgm(String.valueOf("N"));
				tFuncAcomp.setEnviaSms(String.valueOf("N"));
				
				// email
				if( listMail != null ) {
					for( int i=0; i<listMail.length; i++ ) {
						if( configMail.getCodCfgm().equals(Integer.valueOf(listMail[i])) ) {
							tFuncAcomp.setEnviaMailTfacfgm(String.valueOf("S"));
						}
					}
				}
				
				// sms
				if( listSMS != null ) {
					for( int i=0; i<listSMS.length; i++ ) {
						if( configMail.getCodCfgm().equals(Integer.valueOf(listSMS[i])) ) {
							tFuncAcomp.setEnviaSms(String.valueOf("S"));
						}
					}
				}
				
				listaTFuncAcomp.add(tFuncAcomp);
			}
			/* -- add por rogeriom -- */
			
			
			tipoFuncAcompDao.alterar(tipoFuncAcomp, listaTFuncAcomp);
			msg = _msg.getMensagem("tipoFuncAcomp.alteracao.sucesso" );
		} catch (ECARException e) {
			org.apache.log4j.Logger.getLogger(this.getClass()).error(e);
			msg = _msg.getMensagem(e.getMessageKey());
		} catch (Exception e){
		%>
			<%@ include file="/excecao.jsp"%>
		<%
		}
	} else if ("excluir".equalsIgnoreCase(hidAcao)) {
		tipoFuncAcomp = (TipoFuncAcompTpfa) tipoFuncAcompDao.buscarComDescendentes(TipoFuncAcompTpfa.class, Long.valueOf(Pagina.getParamLong(request, "codigo")));
		try {
		
			/* -- add por rogeriom -- */
			String[] listMail = request.getParameterValues("chkMailCfgM");
			String[] listSMS  = request.getParameterValues("chkSMSCfgM");
					
			ConfigMailCfgmDAO configMailDao = new ConfigMailCfgmDAO(request);
						
			List listaConfigMail = configMailDao.listar();
			List listaTFuncAcomp = new ArrayList();
			
			Iterator it = listaConfigMail.iterator();
			
			while( it.hasNext() ) {
				TfuncacompConfigmailTfacfgm tFuncAcomp = new TfuncacompConfigmailTfacfgm();
				TfuncacompConfigmailTfacfgmPK tFuncAcompPk = new TfuncacompConfigmailTfacfgmPK();
				
				ConfigMailCfgm configMail = (ConfigMailCfgm) it.next();
				
				tFuncAcompPk.setCodTpfa(tipoFuncAcomp.getCodTpfa());
				tFuncAcompPk.setCodCfgm(configMail.getCodCfgm());
				tFuncAcomp.setComp_id(tFuncAcompPk);
				
				tFuncAcomp.setEnviaMailTfacfgm(String.valueOf("N"));
				tFuncAcomp.setEnviaSms(String.valueOf("N"));
				
				// email
				if( listMail != null ) {
					for( int i=0; i<listMail.length; i++ ) {
						if( configMail.getCodCfgm().equals(Integer.valueOf(listMail[i])) ) {
							tFuncAcomp.setEnviaMailTfacfgm(String.valueOf("S"));
						}
					}
				}
				
				// sms
				if( listSMS != null ) {
					for( int i=0; i<listSMS.length; i++ ) {
						if( configMail.getCodCfgm().equals(Integer.valueOf(listSMS[i])) ) {
							tFuncAcomp.setEnviaSms(String.valueOf("S"));
						}
					}
				}
				
				listaTFuncAcomp.add(tFuncAcomp);
			}
			/* -- add por rogeriom -- */
			
			tipoFuncAcompDao.excluir(tipoFuncAcomp,listaTFuncAcomp);
				
			msg = _msg.getMensagem("tipoFuncAcomp.exclusao.sucesso");
		} catch (ECARException e) {
			org.apache.log4j.Logger.getLogger(this.getClass()).error(e);
			msg = _msg.getMensagem(e.getMessageKey());
		} catch (Exception e){
		%>
			<%@ include file="/excecao.jsp"%>
		<%
		}
	} else if ("pesquisar".equalsIgnoreCase(hidAcao)) {
		/* aqui devemos usar getParam, pois queremos null se o campo estiver vazio */
		tipoFuncAcomp.setDescricaoTpfa(Pagina.getParam(request, "descricaoTpfa")); 
		tipoFuncAcomp.setLabelTpfa(Pagina.getParam(request, "labelTpfa"));
		tipoFuncAcomp.setIndLerItemEstrutura(Pagina.getParamStr(request, "indLerItemEstrutura").trim());
		
		
		tipoFuncAcomp.setLabelPosicaoTpfa(Pagina.getParam(request, "labelPosicaoTpfa"));
		
		if(Pagina.getParam(request, "tamanhoSinalTpfa") != null)
			tipoFuncAcomp.setTamanhoSinalTpfa(Integer.valueOf(Pagina.getParamInt(request, "tamanhoSinalTpfa")));

		try {
			if(Pagina.getParam(request, "tipoFuncAcompTpfaPai") != null)
				tipoFuncAcomp.setTipoFuncAcompTpfa( (TipoFuncAcompTpfa) tipoFuncAcompDao.buscar(TipoFuncAcompTpfa.class, Long.valueOf(Pagina.getParam(request, "tipoFuncAcompTpfaPai"))));
		} catch (ECARException e) {
			org.apache.log4j.Logger.getLogger(this.getClass()).error(e);
			msg = _msg.getMensagem(e.getMessageKey());
		} catch (Exception e){
		%>
			<%@ include file="/excecao.jsp"%>
		<%
		}
		session.setAttribute("objPesquisa", tipoFuncAcomp);
	}
	/* da o alert para incluir, alterar, pesquisar ou excluir */
	if (msg != null) 
		Mensagem.setInput(_jspx_page_context, "document.form.msgOperacao.value", msg); 
	
	if (refazPesquisa) {
		try {
			tipoFuncAcomp = (TipoFuncAcompTpfa) session.getAttribute("objPesquisa");
			//List lista = tipoFuncAcompDao.pesquisar(tipoFuncAcomp, new String[] {"codTpfa","asc"});
			List lista = tipoFuncAcompDao.pesquisarComDescendentes(tipoFuncAcomp, new String[] {"codTpfa","asc"});
			
			
			//
			boolean pesquisou = (lista.size() > 0) ? true : false;
			session.setAttribute("lista", lista);
			if (pesquisou) {
				submit = "frm_nav.jsp";
				msg = null;
			} else {
				Mensagem.setInput(_jspx_page_context, "document.form.hidPesquisou.value", "false");
				submit = "frm_pes.jsp";
				msg = _msg.getMensagem("tipoFuncAcomp.pesquisa.nenhumRegistro");
			}
		} catch (ECARException e) {
			org.apache.log4j.Logger.getLogger(this.getClass()).error(e);
			msg = _msg.getMensagem(e.getMessageKey());
		} catch (Exception e){
		%>
			<%@ include file="/excecao.jsp"%>
		<%
		}
		if (msg != null){
			Mensagem.setInput(_jspx_page_context, "document.form.msgPesquisa.value", msg);
		}
	}
	%>
	<script language="javascript" type="text/javascript">
		document.form.action = "<%=submit%>";
		document.form.submit();
	</script> 
</body>
</html>
