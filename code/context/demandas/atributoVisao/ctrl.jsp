<jsp:directive.page import="ecar.dao.SisGrupoAtributoDao"/>
<jsp:directive.page import="ecar.pojo.SisGrupoAtributoSga"/>
<%@ include file="/login/validaAcesso.jsp"%>
<%@ include file="/frm_global.jsp"%>

<%@ page import="ecar.pojo.AtributoDemandaAtbdem" %>
<%@ page import="ecar.dao.AtributoDemandaDao" %>
<%@ page import="java.util.List" %>


<%@page import="ecar.pojo.VisaoAtributoDemanda"%>
<%@page import="ecar.dao.VisaoAtributoDemandaDao"%>
<%@page import="ecar.pojo.VisaoAtributoDemandaPK"%>
<%@page import="ecar.pojo.VisaoDemandasVisDem"%><html lang="pt-br">
<head>
<%@ include file="/include/meta.jsp"%>
<%@ include file="/titulo.jsp"%>
</head>
<body>

<form name="form" method="post">
    <!-- campo de controle para a navegação de registros -->
	<input type="hidden" name="hidRegistro" value="<%=Pagina.getParamInt(request, "hidRegistro")%>">
	<input type="hidden" name="msgOperacao" value=""> 
	<input type="hidden" name="msgPesquisa" value="">	
	<input type="hidden" name="hidPesquisou" value="">		
</form>

<%
	VisaoAtributoDemanda visaoAtributoDemanda = new VisaoAtributoDemanda();
	VisaoAtributoDemandaPK visaoAtributoDemandaPK = new VisaoAtributoDemandaPK();
	VisaoDemandasVisDem visao = null;
	AtributoDemandaAtbdem atributoDemanda = null;
	VisaoAtributoDemandaDao visaoAtributoDemandaDao = new VisaoAtributoDemandaDao(request);
	AtributoDemandaDao atributoDemandaDao = new AtributoDemandaDao(request);
	SisGrupoAtributoDao sgaDao = new SisGrupoAtributoDao(request);
	
	String hidAcao = Pagina.getParam(request, "hidAcao");
	String msg = null;
	String submit = "frm_pes.jsp";
	boolean refazPesquisa = true;
	
	if ("incluir".equalsIgnoreCase(hidAcao)) {
		
		atributoDemanda = new AtributoDemandaAtbdem();	
		atributoDemanda = (AtributoDemandaAtbdem)atributoDemandaDao .buscar(AtributoDemandaAtbdem.class, Long.valueOf(Pagina.getParam(request, "atributosDemandas")));
		
//		atributoDemanda.setCodAtbdem(Long.valueOf(Pagina.getParam(request, "atributosDemandas")));
		visao = new VisaoDemandasVisDem();
		visao.setCodVisao(Long.valueOf(Pagina.getParam(request, "visoes")));
		visaoAtributoDemandaPK.setAtributoDemanda(atributoDemanda);
		visaoAtributoDemandaPK.setVisao(visao);
		visaoAtributoDemanda.setVisaoAtributoDemandaPk(visaoAtributoDemandaPK);
		VisaoAtributoDemanda.mapearObjetoNegocio(request, visaoAtributoDemanda);
		
		refazPesquisa = false;
		submit = "frm_inc.jsp";
		try {
			visaoAtributoDemandaDao.salvar(visaoAtributoDemanda);
			msg = _msg.getMensagem("atributoVisao.inclusao.sucesso" );
			session.removeAttribute("objAtributoVisao");
		} catch (ECARException e) {
			org.apache.log4j.Logger.getLogger(this.getClass()).error(e);
			session.setAttribute("objAtributoVisao", visaoAtributoDemanda);
			msg = _msg.getMensagem(e.getMessageKey());
		} catch (Exception e){
		%>
			<%@ include file="/excecao.jsp"%>
		<%
		}		
	} else if ("alterar".equalsIgnoreCase(hidAcao)) {
		
		try {			
			
			atributoDemanda = new AtributoDemandaAtbdem();
			atributoDemanda.setCodAtbdem(Long.valueOf(Pagina.getParam(request, "codigoAtributoDemanda")));
			visao = new VisaoDemandasVisDem();
			visao.setCodVisao(Long.valueOf(Pagina.getParam(request, "codigoVisao")));
			visaoAtributoDemandaPK.setAtributoDemanda(atributoDemanda);
			visaoAtributoDemandaPK.setVisao(visao);
			visaoAtributoDemanda = (VisaoAtributoDemanda)visaoAtributoDemandaDao .buscar(VisaoAtributoDemanda.class, visaoAtributoDemandaPK);
			VisaoAtributoDemanda.mapearObjetoNegocio(request, visaoAtributoDemanda);
						
			visaoAtributoDemandaDao.alterar(visaoAtributoDemanda);
			msg = _msg.getMensagem("atributoVisao.alteracao.sucesso" );
			session.removeAttribute("objAtributoVisao");
			
		} catch (ECARException e) {
			org.apache.log4j.Logger.getLogger(this.getClass()).error(e);
			refazPesquisa = false;
			submit = "frm_alt.jsp";
			session.setAttribute("objAtributoVisao", visaoAtributoDemanda);
			msg = _msg.getMensagem(e.getMessageKey());
		} catch (Exception e){
		%>
			<%@ include file="/excecao.jsp"%>
		<%
		}
	} else if ("excluir".equalsIgnoreCase(hidAcao)) {
		try {
			
			atributoDemanda = new AtributoDemandaAtbdem();
			atributoDemanda.setCodAtbdem(Long.valueOf(Pagina.getParam(request, "codigoAtributoDemanda")));
			visao = new VisaoDemandasVisDem();
			visao.setCodVisao(Long.valueOf(Pagina.getParam(request, "codigoVisao")));
			visaoAtributoDemandaPK.setAtributoDemanda(atributoDemanda);
			visaoAtributoDemandaPK.setVisao(visao);
			visaoAtributoDemanda.setVisaoAtributoDemandaPk(visaoAtributoDemandaPK);
						
			visaoAtributoDemandaDao.excluir( (VisaoAtributoDemanda) visaoAtributoDemandaDao.buscar(VisaoAtributoDemanda.class, visaoAtributoDemandaPK));
			msg = _msg.getMensagem("atributoVisao.exclusao.sucesso" );
			
		} catch (ECARException e) {
			org.apache.log4j.Logger.getLogger(this.getClass()).error(e);
			msg = _msg.getMensagem(e.getMessageKey());
		} catch (Exception e){
		%>
			<%@ include file="/excecao.jsp"%>
		<%
		}
	} else if ("pesquisar".equalsIgnoreCase(hidAcao)) {
				
		
		if (Pagina.getParam(request, "atributosDemandas")!=null) {
			atributoDemanda = new AtributoDemandaAtbdem();
			atributoDemanda.setCodAtbdem(Long.valueOf(Pagina.getParam(request, "atributosDemandas")));
		}
		if (Pagina.getParam(request, "visoes")!=null) {
			visao = new VisaoDemandasVisDem();
			visao.setCodVisao(Long.valueOf(Pagina.getParam(request, "visoes")));
		}
		visaoAtributoDemandaPK.setAtributoDemanda(atributoDemanda);
		visaoAtributoDemandaPK.setVisao(visao);
		visaoAtributoDemanda.setVisaoAtributoDemandaPk(visaoAtributoDemandaPK);		
		
		/* aqui devemos usar getParam, pois queremos null se o campo estiver vazio */
		VisaoAtributoDemanda.mapearObjetoNegocio(request, visaoAtributoDemanda);

		
		session.setAttribute("objPesquisa", visaoAtributoDemanda);
		session.setAttribute("ehPesquisa", new Boolean(true));
	}
	
	/* da o alert para incluir, alterar, pesquisar ou excluir */
	if (msg != null) 
		Mensagem.setInput(_jspx_page_context, "document.form.msgOperacao.value", msg); 

	if (refazPesquisa) {
		try {
			visaoAtributoDemanda = (VisaoAtributoDemanda) session.getAttribute("objPesquisa");
			List lista = visaoAtributoDemandaDao.getVisoesAtributoDemanda(visaoAtributoDemanda);
			boolean pesquisou = (lista.size() > 0) ? true : false;
			session.setAttribute("lista", lista);
			if (pesquisou) {
				submit = "frm_nav.jsp";
				msg = null;
			} else {
				Mensagem.setInput(_jspx_page_context, "document.form.hidPesquisou.value", "false");			
				submit = "frm_pes.jsp";
				msg = _msg.getMensagem("atributoVisao.pesquisa.nenhumRegistro");
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
	<script language="javascript">
		document.form.action = "<%=submit%>";
		document.form.submit();
	</script> 
</body>
</html>
			
