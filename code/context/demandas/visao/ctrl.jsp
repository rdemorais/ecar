<jsp:directive.page import="ecar.dao.SisGrupoAtributoDao"/>
<jsp:directive.page import="ecar.pojo.SisGrupoAtributoSga"/>
<%@ include file="/login/validaAcesso.jsp"%>
<%@ include file="/frm_global.jsp"%>

<%@ page import="ecar.pojo.AtributoDemandaAtbdem" %>
<%@ page import="ecar.dao.AtributoDemandaDao" %>
<%@ page import="java.util.List" %>


<%@page import="ecar.pojo.VisaoDemandasVisDem"%>
<%@page import="ecar.dao.VisaoDao"%>
<%@page import="java.util.HashSet"%>
<%@page import="ecar.pojo.VisaoDemandasGrpAcesso"%>
<%@page import="ecar.pojo.SitDemandaSitd"%>
<%@page import="ecar.pojo.VisaoSituacaoDemanda"%>
<%@page import="ecar.dao.VisaoSituacaoDemandaDao"%>
<%@page import="java.util.ArrayList"%><html lang="pt-br">
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
	VisaoDemandasVisDem visao = new VisaoDemandasVisDem();
	VisaoSituacaoDemandaDao visaoSituacaodao = new VisaoSituacaoDemandaDao(request);
	VisaoDao visaoDao = new VisaoDao(request);
		
	String hidAcao = Pagina.getParam(request, "hidAcao");
	String msg = null;
	String submit = "frm_pes.jsp";
	boolean refazPesquisa = true;
	
	if ("incluir".equalsIgnoreCase(hidAcao)) {
		
		VisaoDemandasVisDem.mapearObjetoNegocio(request, visao);
		
		refazPesquisa = false;
		submit = "frm_inc.jsp";
		try {
			visaoSituacaodao.mantemVisaoSituacaoDemanda(request, visao);
			visaoDao.salvar(visao);
			msg = _msg.getMensagem("visao.inclusao.sucesso" );
			session.removeAttribute("objAtributoVisao");
		} catch (ECARException e) {
			org.apache.log4j.Logger.getLogger(this.getClass()).error(e);
			session.setAttribute("objAtributoVisao", visao);
			msg = _msg.getMensagem(e.getMessageKey());
		} catch (Exception e){
		%>
			<%@ include file="/excecao.jsp"%>
		<%
		}		
	} else if ("alterar".equalsIgnoreCase(hidAcao)) {
		
		try {			
			
			/* o buscar deve estar no try, pois pode ocorrer um erro de hibernate ou bd */
			visao = (VisaoDemandasVisDem) visaoDao.buscar(VisaoDemandasVisDem.class, Long.valueOf(Pagina.getParamLong(request, "codigo")));
			VisaoDemandasVisDem.mapearObjetoNegocio(request, visao);
			visaoSituacaodao.mantemVisaoSituacaoDemanda(request, visao);
			visaoDao.alterar(visao);
			msg = _msg.getMensagem("visao.alteracao.sucesso" );
			session.removeAttribute("objAtributoVisao");
			
		} catch (ECARException e) {
			org.apache.log4j.Logger.getLogger(this.getClass()).error(e);
			refazPesquisa = false;
			submit = "frm_alt.jsp";
			if (visao.getVisoesGrpAcesso() != null){
				visao.getVisoesGrpAcesso().size();	
			}
			
			if (visao.getVisaoSituacaoDemandas() != null){
				visao.getVisaoSituacaoDemandas().size();	
			}
			session.setAttribute("objAtributoVisao", visao);
			msg = _msg.getMensagem(e.getMessageKey());
		} catch (Exception e){
		%>
			<%@ include file="/excecao.jsp"%>
		<%
		}
	} else if ("excluir".equalsIgnoreCase(hidAcao)) {
		try {
			visaoDao.excluir( (VisaoDemandasVisDem) visaoDao.buscar(VisaoDemandasVisDem.class, Long.valueOf(Pagina.getParam(request, "codigo"))));
			msg = _msg.getMensagem("visao.exclusao.sucesso" );
			
		} catch (ECARException e) {
			org.apache.log4j.Logger.getLogger(this.getClass()).error(e);
			msg = _msg.getMensagem(e.getMessageKey());
		} catch (Exception e){
		%>
			<%@ include file="/excecao.jsp"%>
		<%
		}
	} else if ("pesquisar".equalsIgnoreCase(hidAcao)) {
		VisaoDemandasVisDem.mapearObjetoNegocio(request, visao);
		List situacoesFiltro = new ArrayList();
		List situacoesAlteracao = new ArrayList();
		visaoSituacaodao.mapearSituacoesSelecionadas(request, situacoesFiltro, situacoesAlteracao);
		session.setAttribute("objPesquisa", visao);
		session.setAttribute("situacoesFiltro", situacoesFiltro);
		session.setAttribute("situacoesAlteracao", situacoesAlteracao);
	}
	
	/* da o alert para incluir, alterar, pesquisar ou excluir */
	if (msg != null) 
		Mensagem.setInput(_jspx_page_context, "document.form.msgOperacao.value", msg); 

	if (refazPesquisa) {
		try {
			visao = (VisaoDemandasVisDem) session.getAttribute("objPesquisa");
			List situacoesFiltro = (List) session.getAttribute("situacoesFiltro");
			List situacoesAlteracao = (List) session.getAttribute("situacoesAlteracao");
			List lista = visaoDao.pesquisar(request, visao, situacoesFiltro, situacoesAlteracao, new String[] {"codVisao","asc"});
			
			for(int i=0;i<lista.size();i++) {
				if (((VisaoDemandasVisDem)lista.get(i)).getVisoesGrpAcesso() != null){
					((VisaoDemandasVisDem)lista.get(i)).getVisoesGrpAcesso().size();	
				}
				
				if (((VisaoDemandasVisDem)lista.get(i)).getVisaoSituacaoDemandas() != null){
					((VisaoDemandasVisDem)lista.get(i)).getVisaoSituacaoDemandas().size();	
				} 
				
				if (((VisaoDemandasVisDem)lista.get(i)).getVisaoSituacaoDemandas() != null){
					((VisaoDemandasVisDem)lista.get(i)).getVisaoSituacaoDemandas().size();	
				} 
			}
			boolean pesquisou = (lista.size() > 0) ? true : false;
			session.setAttribute("lista", lista);
			if (pesquisou) {
				submit = "frm_nav.jsp";
				msg = null;
			} else {
				Mensagem.setInput(_jspx_page_context, "document.form.hidPesquisou.value", "false");			
				submit = "frm_pes.jsp";
				msg = _msg.getMensagem("visao.pesquisa.nenhumRegistro");
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
			
