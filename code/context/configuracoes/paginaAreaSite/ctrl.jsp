<%@ include file="../../login/validaAcesso.jsp"%>

<%@ include file="../../frm_global.jsp"%>
<%@ page import="ecar.pojo.PaginaAreaSitePa" %>
<%@ page import="ecar.dao.PaginaAreaSiteDao" %>
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
	<!-- campo de controle para as mensagens -->
	<input type="hidden" name="msgOperacao" value=""> 
	<input type="hidden" name="msgPesquisa" value="">	

	<input type="hidden" name="hidPesquisou" value="">		
</form>

<%
	PaginaAreaSitePa paginaAreaSite = new PaginaAreaSitePa();
	PaginaAreaSiteDao paginaAreaSiteDao = new PaginaAreaSiteDao(request);
	
	String hidAcao = Pagina.getParam(request, "hidAcao");
	String msg = null;
	String submit = "frm_pes.jsp";
	boolean refazPesquisa = true;
	boolean incluir = "incluir".equalsIgnoreCase(hidAcao);
	boolean alterar = "alterar".equalsIgnoreCase(hidAcao);
	boolean excluir = "excluir".equalsIgnoreCase(hidAcao);
	boolean pesquisar = "pesquisar".equalsIgnoreCase(hidAcao);
	
	if (incluir) {
		/* melhor usar getParamStr, pois nunca devolve null */		
		paginaAreaSite.setNomePas(Pagina.getParamStr(request, "nomePas").trim());
		paginaAreaSite.setTextoPas(Pagina.getParamStr(request, "textoPas").trim());
		paginaAreaSite.setIndCapaPas((Pagina.getParamStr(request, "indCapaPas"))); 
		paginaAreaSite.setUrlPas(Pagina.getParamStr(request, "urlPas").trim());
		paginaAreaSite.setCorPas(Pagina.getParamStr(request, "corPas").trim());
		paginaAreaSite.setTituloPas(Pagina.getParamStr(request, "tituloPas").trim());
		paginaAreaSite.setSeqApresentacaoPas(Integer.valueOf(Pagina.getParamInt(request, "seqApresentacaoPas")));
		refazPesquisa = false;
		submit = "frm_inc.jsp";
		try {
			paginaAreaSiteDao.salvar(paginaAreaSite);
			msg = _msg.getMensagem("paginaAreaSite.inclusao.sucesso" );
		} catch (ECARException e) {
			org.apache.log4j.Logger.getLogger(this.getClass()).error(e);
			msg = _msg.getMensagem(e.getMessageKey());
		} catch (Exception e){
		%>
			<%@ include file="/excecao.jsp"%>
		<%
		}
	} else if (alterar) {
		try {			/* o buscar deve estar no try, pois pode ocorrer um erro de hibernate ou bd */
			paginaAreaSite = (PaginaAreaSitePa) paginaAreaSiteDao.buscar(PaginaAreaSitePa.class, Long.valueOf(Pagina.getParamLong(request, "codigo")));
			/* altera o que foi modificado na tela */
			paginaAreaSite.setNomePas((Pagina.getParamStr(request, "nomePas").trim())); 
			paginaAreaSite.setIndCapaPas((Pagina.getParamStr(request, "indCapaPas"))); 
			paginaAreaSite.setTextoPas(Pagina.getParamStr(request, "textoPas").trim());
			paginaAreaSite.setUrlPas(Pagina.getParamStr(request, "urlPas").trim());
			paginaAreaSite.setCorPas(Pagina.getParamStr(request, "corPas").trim());
			paginaAreaSite.setTituloPas(Pagina.getParamStr(request, "tituloPas").trim());
			/*converte campo numérico para string*/
			paginaAreaSite.setSeqApresentacaoPas(Integer.valueOf(Pagina.getParamInt(request, "seqApresentacaoPas")));
			/* altera no BD */
			paginaAreaSiteDao.alterar(paginaAreaSite);
			msg = _msg.getMensagem("paginaAreaSite.alteracao.sucesso" );
		} catch (ECARException e) {
			org.apache.log4j.Logger.getLogger(this.getClass()).error(e);
			msg = _msg.getMensagem(e.getMessageKey());
		} catch (Exception e){
		%>
			<%@ include file="/excecao.jsp"%>
		<%
		}
	} else if (excluir) {
		try {
			paginaAreaSiteDao.excluir((PaginaAreaSitePa) paginaAreaSiteDao.buscar(PaginaAreaSitePa.class, Long.valueOf(Pagina.getParam(request, "codigo"))));
			msg = _msg.getMensagem("paginaAreaSite.exclusao.sucesso" );
		} catch (ECARException e) {
			org.apache.log4j.Logger.getLogger(this.getClass()).error(e);
			msg = _msg.getMensagem(e.getMessageKey());
		} catch (Exception e){
		%>
			<%@ include file="/excecao.jsp"%>
		<%
		}
	} else if (pesquisar) {
		/* aqui devemos usar getParam, pois queremos null se o campo estiver vazio */
		paginaAreaSite.setNomePas((Pagina.getParam(request, "nomePas"))); 
		paginaAreaSite.setTextoPas((Pagina.getParam(request, "textoPas"))); 
		paginaAreaSite.setIndCapaPas((Pagina.getParam(request, "indCapaPas")));
		paginaAreaSite.setUrlPas((Pagina.getParam(request, "urlPas")));
		paginaAreaSite.setCorPas((Pagina.getParam(request, "corPas")));
		paginaAreaSite.setTituloPas(Pagina.getParam(request, "tituloPas"));
		if (Pagina.getParam(request, "seqApresentacaoPas") != null)
			paginaAreaSite.setSeqApresentacaoPas(Integer.valueOf(Pagina.getParamInt(request, "seqApresentacaoPas")));
		
		session.setAttribute("objPesquisa", paginaAreaSite);
		}
	/* da o alert para incluir, alterar, pesquisar ou excluir */
	if (msg != null) 
		Mensagem.setInput(_jspx_page_context, "document.form.msgOperacao.value", msg); 

	if (refazPesquisa) {
		try {
			paginaAreaSite = (PaginaAreaSitePa) session.getAttribute("objPesquisa");
			List lista = paginaAreaSiteDao.pesquisar(paginaAreaSite, new String[] {"codPas","asc"});
			boolean pesquisou = (lista.size() > 0) ? true : false;
			session.setAttribute("lista", lista);
			if (pesquisou) {
				submit = "frm_nav.jsp";
				msg = null;
			} else {
				Mensagem.setInput(_jspx_page_context, "document.form.hidPesquisou.value", "false");
				submit = "frm_pes.jsp";
				msg = _msg.getMensagem("paginaAreaSite.pesquisa.nenhumRegistro");
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