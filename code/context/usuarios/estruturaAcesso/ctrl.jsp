<jsp:directive.page import="org.apache.log4j.Logger"/>
<jsp:directive.page import="org.hibernate.ObjectNotFoundException"/>
<jsp:directive.page import="ecar.dao.TipoAcompGrpAcessoDao"/>
<jsp:directive.page import="ecar.pojo.TipoAcompanhamentoTa"/>
<jsp:directive.page import="ecar.pojo.TipoAcompGrpAcesso"/>
<jsp:directive.page import="ecar.dao.TipoAcompanhamentoDao"/>
<jsp:directive.page import="ecar.pojo.TipoAcompGrpAcessoId"/>
<jsp:directive.page import="ecar.dao.TipoFuncAcompDao"/>
<jsp:directive.page import="ecar.dao.TipoAcompFuncAcompDao"/>
<jsp:directive.page import="ecar.pojo.TipoFuncAcompTpfa"/>
<jsp:directive.page import="ecar.pojo.TipoAcompFuncAcompTafc"/>
<jsp:directive.page import="ecar.dao.AbaDao"/>
<jsp:directive.page import="ecar.pojo.Aba"/>
<jsp:directive.page import="java.util.ArrayList"/>
<jsp:directive.page import="ecar.pojo.ItemEstruturaIett"/>
<jsp:directive.page import="ecar.pojo.ItemEstrutUsuarioIettus"/>
<jsp:directive.page import="ecar.dao.ItemEstrutUsuarioDao"/>
<jsp:directive.page import="ecar.util.Dominios"/>
<jsp:directive.page import="org.hibernate.criterion.Example"/>
<jsp:directive.page import="org.hibernate.criterion.Restrictions"/>
<jsp:directive.page import="ecar.pojo.TipoAcompAbasSisatributoTaAbaSatb"/>
<jsp:directive.page import="ecar.dao.TipoAcompAbasSisatributoTaAbaSatbDao"/>
<jsp:directive.page import="ecar.dao.TipoAcompTipofuncacompSisatributoTaTpfaSatbDao"/>
<jsp:directive.page import="ecar.pojo.TipoAcompTipofuncacompSisatributoTaTpfaSatb"/>
<jsp:directive.page import="ecar.dao.DemandasGrpAcessoDao"/>
<jsp:directive.page import="ecar.dao.VisaoGrpAcessoDao"/>
<jsp:directive.page import="ecar.pojo.DemandasGrpAcesso"/>
<jsp:directive.page import="ecar.dao.PesquisaGrpAcessoDao"/>
<jsp:directive.page import="ecar.pojo.PesquisaGrpAcesso"/>
<jsp:directive.page import="ecar.dao.PesquisaDao"/>
<jsp:directive.page import="java.util.Set"/>

<%@ include file="/login/validaAcesso.jsp"%>
<%@ include file="/frm_global.jsp"%>

<%@ page import="ecar.dao.SisAtributoDao"%>
<%@ page import="ecar.pojo.SisAtributoSatb"%>
<%@ page import="ecar.dao.EstruturaDao"%>
<%@ page import="ecar.pojo.EstruturaEtt"%>
<%@ page import="ecar.dao.EstruturaAcessoDao"%>
<%@ page import="ecar.pojo.EstruturaAcessoEtta"%>
<%@ page import="ecar.pojo.EstruturaAcessoEttaPK"%>
<%@ page import="java.util.Iterator"%>
<%@ page import="java.util.List"%>

<html lang="pt-br">
<head>
	<%@ include file="/include/meta.jsp"%>
	<%@ include file="/titulo.jsp"%>
</head>
<body>

<form name="form" method="post">
    <!-- campo de controle para a navegação de registros -->
	<input type="hidden" name="msgOperacao" value=""/> 
	<input type="hidden" name="msgPesquisa" value=""/>
	<input type="hidden" name="sisAtributoSatb" value="<%=Pagina.getParam(request, "sisAtributoSatb")%>" />
	<input type="hidden" name="hidAcao" value="<%=Pagina.getParamStr(request,"hidAcao")%>" />
</form>

<%
	String msg = null; 
	try{
		SisAtributoDao atrDao          = new SisAtributoDao();
		EstruturaAcessoDao ettaDao     = new EstruturaAcessoDao();
		TipoAcompanhamentoDao taDao    = new TipoAcompanhamentoDao();
		TipoAcompGrpAcessoDao taaDao   = new TipoAcompGrpAcessoDao();
		TipoFuncAcompDao tpfaDao       = new TipoFuncAcompDao();
		EstruturaDao ettDao            = new EstruturaDao(request);
		AbaDao abaDao                  = new AbaDao(request);
//		List listatafc                 = new ArrayList();
		ItemEstrutUsuarioDao iettusDao = new ItemEstrutUsuarioDao(request);
		Set pesquisas = null;
		
		TipoAcompAbasSisatributoTaAbaSatbDao taabasatbDao = new TipoAcompAbasSisatributoTaAbaSatbDao();
		TipoAcompTipofuncacompSisatributoTaTpfaSatbDao tatpfasatbDao = new TipoAcompTipofuncacompSisatributoTaTpfaSatbDao();
		
		SisAtributoSatb satb = (SisAtributoSatb) atrDao.buscar(SisAtributoSatb.class, Long.valueOf(Pagina.getParam(request, "sisAtributoSatb")));

		for (Iterator it = ettDao.getListaEstruturas().iterator();it.hasNext();) {
			EstruturaEtt ett = (EstruturaEtt) it.next();
			 
	    	EstruturaAcessoEtta etta = (EstruturaAcessoEtta) ettaDao.getEstruturaAcessoEtta(satb,ett);
	    	
	    	if(etta == null) etta = new EstruturaAcessoEtta(satb,ett);
	    	
			ettaDao.setEstruturaAcessoEtta(etta, request);
			ettaDao.salvarOuAlterar(etta);
		}
		

		//Grupos de Acesso
		PesquisaGrpAcessoDao pesquisaGrpAcessoDao = new PesquisaGrpAcessoDao();
		PesquisaDao pesquisaDao = new PesquisaDao(request);
		PesquisaGrpAcesso pesquisaGrpAcesso = (PesquisaGrpAcesso) pesquisaGrpAcessoDao.getPesquisaGrpAcesso(satb);
		boolean ehSalvar = false;
		
		/*Salava as pesquisa do Sistema */
		pesquisas = pesquisaDao.setPesquisaSistemaGrupoAcesso(request);
		
		if (pesquisaGrpAcesso==null){
			pesquisaGrpAcesso = new PesquisaGrpAcesso();
			pesquisaGrpAcesso.setCodSatb(satb.getCodSatb());
			ehSalvar = true;			
		}
		
		if(Pagina.getParam(request, "indPodeVerGeral") != null && !Pagina.getParam(request, "indPodeVerGeral").equals("")) {
			pesquisaGrpAcesso.setIndPodeVerGeral(Dominios.SIM);
		} else {
			pesquisaGrpAcesso.setIndPodeVerGeral(Dominios.NAO);
		}
		
		if(Pagina.getParam(request, "indPodeVerMinhaVisao") != null && !Pagina.getParam(request, "indPodeVerMinhaVisao").equals("")) {
			pesquisaGrpAcesso.setIndPodeVerMinhaVisao(Dominios.SIM);
		} else {
			pesquisaGrpAcesso.setIndPodeVerMinhaVisao(Dominios.NAO);
		}

		if(Pagina.getParam(request, "indPodeVerPendencias") != null && !Pagina.getParam(request, "indPodeVerPendencias").equals("")) {
			pesquisaGrpAcesso.setIndPodeVerPendencias(Dominios.SIM);
		} else {
			pesquisaGrpAcesso.setIndPodeVerPendencias(Dominios.NAO);
		}

		if(Pagina.getParam(request, "indPodeVerPersonalizado") != null && !Pagina.getParam(request, "indPodeVerPersonalizado").equals("")) {
			pesquisaGrpAcesso.setIndPodeVerPersonalizado(Dominios.SIM);
		} else {
			pesquisaGrpAcesso.setIndPodeVerPersonalizado(Dominios.NAO);
		}
		
		if(Pagina.getParam(request, "indPodeCriarPesquisaUsuario") != null && !Pagina.getParam(request, "indPodeCriarPesquisaUsuario").equals("")) {
			pesquisaGrpAcesso.setIndPodeCriarPesquisaUsuario(Dominios.SIM);
		} else {
			pesquisaGrpAcesso.setIndPodeCriarPesquisaUsuario(Dominios.NAO);
		}

		if(Pagina.getParam(request, "indPodeCriarPesquisaSistema") != null && !Pagina.getParam(request, "indPodeCriarPesquisaSistema").equals("")) {
			pesquisaGrpAcesso.setIndPodeCriarPesquisaSistema(Dominios.SIM);
		} else {
			pesquisaGrpAcesso.setIndPodeCriarPesquisaSistema(Dominios.NAO);
		}

		if (ehSalvar){
			pesquisaGrpAcessoDao.salvar(pesquisaGrpAcesso);
		} else {
			pesquisaGrpAcessoDao.alterar(pesquisaGrpAcesso);
		}
		
		
		

		//Demanda
//		DemandasGrpAcessoDao demandasGrpAcessoDao = new DemandasGrpAcessoDao();
	//	DemandasGrpAcesso demandasGrpAcesso = (DemandasGrpAcesso) demandasGrpAcessoDao.getDemandasGrpAcesso(satb);
		//if(demandasGrpAcesso != null) {
			//if(Pagina.getParam(request, "acessoDemanda") != null && !Pagina.getParam(request, "acessoDemanda").equals("")) {
				//demandasGrpAcesso.setAcessoDemanda(Dominios.SIM);
			//} else {
				//demandasGrpAcesso.setAcessoDemanda(Dominios.NAO);
			//}
			//demandasGrpAcessoDao.alterar(demandasGrpAcesso);
		//} else {
			//demandasGrpAcesso = new DemandasGrpAcesso();
			//demandasGrpAcesso.setCodSatb(satb.getCodSatb());
			//if(Pagina.getParam(request, "acessoDemanda") != null && !Pagina.getParam(request, "acessoDemanda").equals("")) {
				//demandasGrpAcesso.setAcessoDemanda(Dominios.SIM);
			//} else {
				//demandasGrpAcesso.setAcessoDemanda(Dominios.NAO);
			//}
			//demandasGrpAcessoDao.salvar(demandasGrpAcesso);
		//}
		VisaoGrpAcessoDao visaoDao = new VisaoGrpAcessoDao();
		visaoDao.inserir(request.getParameterValues("visoes"), satb.getCodSatb());		
		
		//Visoes Grupo Acesso
		/** TO-DO  Perfdormance - Aqui começa a ficar lento **/
		List listatpfa = tpfaDao.getFuncAcompByLabel();
		List listaaba  = abaDao.listar(Aba.class,new String[]{"codAba",AbaDao.ORDEM_ASC});
		
		for(Iterator it = taDao.getTipoAcompanhamentoAtivosByDescricao().iterator();it.hasNext();){
			TipoAcompanhamentoTa ta = (TipoAcompanhamentoTa) it.next();
			TipoAcompGrpAcesso taa = (TipoAcompGrpAcesso) taaDao.getTipoAcompGrpAcesso(satb,ta);
			
			if (taa == null) taa = new TipoAcompGrpAcesso(satb,ta);
				
			taaDao.setTipoAcompGrpAcesso(taa,request);
			taaDao.salvarOuAlterar(taa);
			
			for(Iterator ittpfa = listatpfa.iterator();ittpfa.hasNext();){
				TipoFuncAcompTpfa tpfa = (TipoFuncAcompTpfa) ittpfa.next();
				
//				TipoAcompFuncAcompTafc tafc = tafcDao.buscar(ta.getCodTa(),tpfa.getCodTpfa());
				TipoAcompTipofuncacompSisatributoTaTpfaSatb tatpfasatb = tatpfasatbDao.buscar(ta.getCodTa(),tpfa.getCodTpfa(),satb.getCodSatb());
				
				if(tatpfasatb == null) tatpfasatb = new TipoAcompTipofuncacompSisatributoTaTpfaSatb(ta,tpfa,satb);
					
				tatpfasatbDao.setTipoAcompTipofuncacompSisatributoTaTpfaSatb(tatpfasatb,request);
				tatpfasatbDao.salvarOuAlterar(tatpfasatb);
//				listatafc.add(tafc);
			}
			
			for(Iterator itaba = listaaba.iterator();itaba.hasNext();){
				Aba aba = (Aba) itaba.next();
				
				TipoAcompAbasSisatributoTaAbaSatb taabasatb = taabasatbDao.buscar(ta.getCodTa(), new Long(aba.getCodAba().longValue()), satb.getCodSatb());
				
				if(taabasatb == null) taabasatb = new TipoAcompAbasSisatributoTaAbaSatb(ta,aba,satb);
				
				taabasatbDao.setTipoAcompAbasSisatributoTaAbaSatb(taabasatb,request);
				taabasatbDao.salvarOuAlterar(taabasatb);
			}
		}

		satb.setPesquisas(pesquisas);
		atrDao.salvar(satb);
		msg = _msg.getMensagem("estruturaAcesso.inclusao.sucesso");
	} catch (ECARException e) {
		Logger.getLogger(this.getClass()).error(e);
		msg = _msg.getMensagem("estruturaAcesso.atualizar.erro");
%>
	<%@ include file="/excecao.jsp"%>
<%
	}
	if (msg != null)
		Mensagem.setInput(_jspx_page_context, "document.form.msgOperacao.value", msg); 
%>

<script language="javascript">
	document.form.hidAcao.value = "";
	document.form.action = "frm_inc.jsp";
	document.form.submit();
</script> 

</body>
</html>
