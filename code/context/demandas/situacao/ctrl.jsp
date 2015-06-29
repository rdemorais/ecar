<%@ include file="../../frm_global.jsp"%>
<%@ page import="ecar.pojo.SisAtributoSatb" %>
<%@ page import="ecar.dao.SisAtributoDao" %>
<%@ page import="ecar.pojo.SisGrupoAtributoSga" %>
<%@ page import="ecar.dao.SisGrupoAtributoDao" %>
<%@ page import="ecar.pojo.SitDemandaSitd" %>
<%@ page import="ecar.dao.SitDemandaDao" %>
<%@ page import="comum.util.Data" %>
<%@ page import="java.util.List" %>

 
<html lang="pt-br">
<head>
<%@ include file="../../include/meta.jsp"%>
<%@ include file="/titulo.jsp"%>
</head>
<body>

<form name="form" method="post">
    <!-- campo de controle para a navegação de registros -->
	<input type="hidden" name="hidRegistro" value="<%=Pagina.getParamInt(request, "hidRegistro")%>">
	<input type="hidden" name="msgOperacao" value=""> 
	<input type="hidden" name="msgPesquisa" value="">
	<input type="hidden" name="hidPesquisou" value="">	
	<INPUT type="hidden" name="indTabelaUso" value="<%=request.getParameter("indTabelaUso")%>">		
</form>

<%
	SitDemandaSitd situacao = new SitDemandaSitd();
	
	SitDemandaDao situacaoDao = new SitDemandaDao(request);
	
	Mensagem mensagem = new Mensagem(application);
	
	String hidAcao = Pagina.getParam(request, "hidAcao");
	String msg = null;
	String submit = "frm_pes.jsp";
	
	/* default sempre refazer a pesquisa */
	boolean refazPesquisa = true;  
	boolean incluir = "incluir".equalsIgnoreCase(hidAcao);
	boolean alterar = "alterar".equalsIgnoreCase(hidAcao);
	boolean excluir = "excluir".equalsIgnoreCase(hidAcao);
	boolean pesquisar = "pesquisar".equalsIgnoreCase(hidAcao);
	
	if (incluir) {
		try {
			/* melhor usar getParamStr, pois nunca devolve null */
			situacao.setDescricaoSitd(Pagina.getParamStr(request, "descricaoSitd").trim());
			situacao.setIndConclusaoSitd(Pagina.getParamStr(request, "indConclusaoSitd"));
			situacao.setIndPrimeiraSituacaoSitd(Pagina.getParamStr(request, "indPrimeiraSituacaoSitd"));
			
			refazPesquisa = false;
			submit = "frm_inc.jsp";
			situacaoDao.salvar(situacao);
			msg = _msg.getMensagem("situacaoDemanda.inclusao.sucesso");
			session.removeAttribute("objSituacao");
		} catch (ECARException e) {
			org.apache.log4j.Logger.getLogger(this.getClass()).error(e);
			session.setAttribute("objSituacao", situacao);
			msg = _msg.getMensagem(e.getMessageKey());
		} catch (Exception e){
		%>
			<%@ include file="/excecao.jsp"%>
		<%
		}
	} else if (alterar) {
		try {
			/* busca o objeto para manter o que já esta cadastrado */
			situacao = (SitDemandaSitd) situacaoDao.buscar(SitDemandaSitd.class,Long.valueOf(Pagina.getParam(request, "codigo")));
			situacao.setDescricaoSitd(Pagina.getParamStr(request, "descricaoSitd").trim());
			situacao.setIndConclusaoSitd(Pagina.getParamStr(request, "indConclusaoSitd"));
			situacao.setIndPrimeiraSituacaoSitd(Pagina.getParamStr(request, "indPrimeiraSituacaoSitd"));
			
			situacaoDao.alterar(situacao);
			msg = _msg.getMensagem("situacaoDemanda.alteracao.sucesso");
			session.removeAttribute("objSituacao");
		} catch (ECARException e) {
			org.apache.log4j.Logger.getLogger(this.getClass()).error(e);
			refazPesquisa = false;
			submit = "frm_alt.jsp";
			session.setAttribute("objSituacao", situacao);
			msg = _msg.getMensagem(e.getMessageKey());
		} catch (Exception e){
		%>
			<%@ include file="/excecao.jsp"%>
		<%
		}
	} else if (excluir) {
		try {
			situacaoDao.excluir((SitDemandaSitd) situacaoDao.buscar(SitDemandaSitd.class, Long.valueOf(Pagina.getParam(request, "codigo"))));
			msg = _msg.getMensagem("situacaoDemanda.exclusao.sucesso" );
		} catch (ECARException e) {
			org.apache.log4j.Logger.getLogger(this.getClass()).error(e);
			msg = _msg.getMensagem(e.getMessageKey());
		} catch (Exception e){
		%>
			<%@ include file="/excecao.jsp"%>
		<%
		}
	} else if (pesquisar) {
		try {
			/* aqui devemos usar getParam, pois queremos null se o campo estiver vazio */
						
			situacao.setDescricaoSitd(Pagina.getParamStr(request, "descricaoSitd").trim());
			situacao.setIndConclusaoSitd(Pagina.getParamStr(request, "indConclusaoSitd"));
			situacao.setIndPrimeiraSituacaoSitd(Pagina.getParamStr(request, "indPrimeiraSituacaoSitd"));
						
			session.setAttribute("objPesquisa", situacao);
		} catch (Exception e){
		%>
			<%@ include file="/excecao.jsp"%>
		<%
		}
	}
	
	/* da o alert para incluir, alterar, pesquisar ou excluir */
	if (msg != null)
		Mensagem.setInput(_jspx_page_context, "document.form.msgOperacao.value", msg); 
		
	if (refazPesquisa) {
		try {
			situacao = (SitDemandaSitd) session.getAttribute("objPesquisa");
			List lista = situacaoDao.pesquisar(situacao, null);
			boolean pesquisou = (lista.size() > 0) ? true : false;
			session.setAttribute("objPesquisa", situacao);
			session.setAttribute("lista", lista);
			if (pesquisou) {
				submit = "frm_nav.jsp";
				msg = null;
			} else {
				Mensagem.setInput(_jspx_page_context, "document.form.hidPesquisou.value", "false");
				submit = "frm_pes.jsp";
				msg = _msg.getMensagem("atributo.pesquisa.nenhumRegistro");
			}
		} catch (ECARException e) {
			org.apache.log4j.Logger.getLogger(this.getClass()).error(e);
			msg = _msg.getMensagem(e.getMessageKey());
		} catch (Exception e){
		%>
			<%@ include file="/excecao.jsp"%>
		<%
		}
		if (msg != null)
			Mensagem.setInput(_jspx_page_context, "document.form.msgPesquisa.value", msg);
	}
	
	%>
	<script language="javascript">
		document.form.action = "<%=submit%>";
		document.form.submit();
	</script> 

</body>
</html>

