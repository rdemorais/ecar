<%@ include file="../../login/validaAcesso.jsp"%>

<%@ page import="ecar.pojo.PaginaPgn" %>
<%@ page import="ecar.dao.PaginaDao" %>
<%@ page import="ecar.pojo.PaginaAreaSitePa" %>
<%@ page import="ecar.dao.PaginaAreaSiteDao" %>
<%@ page import="ecar.pojo.IdiomaIdm" %>
<%@ page import="ecar.dao.IdiomaDao" %>
<%@ include file="../../frm_global.jsp" %>
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
	<!-- campo de controle para mensagens -->
	<input type="hidden" name="msgOperacao" value=""> 
	<input type="hidden" name="msgPesquisa" value="">	
	<input type="hidden" name="hidPesquisou" value="">		
	
</form>

<%
	PaginaPgn paginaPgn = new PaginaPgn();
	PaginaDao paginaDao = new PaginaDao(request);
	
	PaginaAreaSitePa paginaAreaSite = new PaginaAreaSitePa();
	PaginaAreaSiteDao paginaAreaSiteDao = new PaginaAreaSiteDao(request);
	
	IdiomaIdm idioma = new IdiomaIdm();
	IdiomaDao idiomaDao = new IdiomaDao(request);
	
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
		paginaPgn.setUrlPgn(Pagina.getParamStr(request, "urlPgn").trim()); 
		paginaPgn.setParametrosPgn(Pagina.getParamStr(request, "parametrosPgn").trim()); 
		paginaPgn.setIndHomePgn((Pagina.getParamStr(request, "indHomePgn"))); 
		paginaPgn.setIndLoginPgn((Pagina.getParamStr(request, "indLoginPgn"))); 
		paginaPgn.setIndMapaPgn((Pagina.getParamStr(request, "indMapaPgn"))); 
		paginaPgn.setTituloMapaPgn(Pagina.getParamStr(request, "tituloMapaPgn").trim()); 
		paginaPgn.setDescricaoMapaPgn(Pagina.getParamStr(request, "descricaoMapaPgn").trim()); 
		paginaPgn.setQtdSubAreaPgn(Integer.valueOf(Pagina.getParamStr(request, "qtdSubAreaPgn").trim()));
		paginaPgn.setNomePgn(Pagina.getParamStr(request, "nomePgn").trim()); 
		paginaPgn.setTituloPgn(Pagina.getParamStr(request, "tituloPgn").trim()); 
		refazPesquisa = false;
		submit = "frm_inc.jsp";
		
		try {
			if(Pagina.getParam(request, "paginaAreaSitePa") != null)
				paginaPgn.setPaginaAreaSitePa((PaginaAreaSitePa) paginaDao.buscar(PaginaAreaSitePa.class, Long.valueOf(Pagina.getParam(request, "paginaAreaSitePa"))));
			
			if(Pagina.getParam(request, "paginaPgn") != null)
				paginaPgn.setPaginaPgn((PaginaPgn) paginaDao.buscar(PaginaPgn.class, Long.valueOf(Pagina.getParam(request, "paginaPgn"))));
			
			if(Pagina.getParam(request, "idiomaIdm") != null)
			paginaPgn.setIdiomaIdm((IdiomaIdm) paginaDao.buscar(IdiomaIdm.class, Long.valueOf(Pagina.getParam(request, "idiomaIdm"))));

			paginaDao.salvar(paginaPgn);
			msg = _msg.getMensagem("paginaPgn.inclusao.sucesso");
		} catch (ECARException e) {
			org.apache.log4j.Logger.getLogger(this.getClass()).error(e);
			msg = _msg.getMensagem(e.getMessageKey());
		} catch (Exception e){
		%>
			<%@ include file="/excecao.jsp"%>
		<%
		}
	} else if (alterar) {
		try { /* busca o objeto para manter o que já esta cadastrado */
			paginaPgn = (PaginaPgn) paginaDao.buscar(PaginaPgn.class, Long.valueOf(Pagina.getParamLong(request, "codigo")));
			/* altera o que foi modificado na tela */
			if(Pagina.getParam(request, "paginaAreaSitePa") != null)
				paginaPgn.setPaginaAreaSitePa((PaginaAreaSitePa) paginaDao.buscar(PaginaAreaSitePa.class, Long.valueOf(Pagina.getParam(request, "paginaAreaSitePa"))));

			paginaPgn.setNomePgn(Pagina.getParamStr(request, "nomePgn").trim()); 
		
			if(Pagina.getParam(request, "paginaPgn") != null)
				paginaPgn.setPaginaPgn((PaginaPgn) paginaDao.buscar(PaginaPgn.class, Long.valueOf(Pagina.getParam(request, "paginaPgn"))));
		
			paginaPgn.setTituloPgn(Pagina.getParamStr(request, "tituloPgn").trim()); 
		
			if(Pagina.getParam(request, "idiomaIdm") != null)
				paginaPgn.setIdiomaIdm((IdiomaIdm) paginaDao.buscar(IdiomaIdm.class, Long.valueOf(Pagina.getParam(request, "idiomaIdm"))));
		
			paginaPgn.setUrlPgn(Pagina.getParamStr(request, "urlPgn").trim()); 
			paginaPgn.setParametrosPgn(Pagina.getParamStr(request, "parametrosPgn").trim()); 
			paginaPgn.setIndHomePgn((Pagina.getParamStr(request, "indHomePgn"))); 
			paginaPgn.setIndLoginPgn((Pagina.getParamStr(request, "indLoginPgn"))); 
			paginaPgn.setIndMapaPgn((Pagina.getParamStr(request, "indMapaPgn"))); 
			paginaPgn.setTituloMapaPgn(Pagina.getParamStr(request, "tituloMapaPgn").trim()); 
			paginaPgn.setDescricaoMapaPgn(Pagina.getParamStr(request, "descricaoMapaPgn").trim());
			paginaPgn.setQtdSubAreaPgn(Integer.valueOf(Pagina.getParamStr(request, "qtdSubAreaPgn").trim()));
			submit = "frm_alt.jsp";
			/* altera no BD */
			paginaDao.alterar(paginaPgn);
			msg = _msg.getMensagem("paginaPgn.alteracao.sucesso");
		}catch (ECARException e) {
			org.apache.log4j.Logger.getLogger(this.getClass()).error(e);
			msg = _msg.getMensagem(e.getMessageKey());
		} catch (Exception e){
		%>
			<%@ include file="/excecao.jsp"%>
		<%
		}
	}else if (excluir) {
		try {
			/* busca e exclui o objeto do BD */
			paginaDao.excluir((PaginaPgn)paginaDao.buscar(PaginaPgn.class, Long.valueOf(Pagina.getParam(request, "codigo"))));
			msg = _msg.getMensagem("paginaPgn.exclusao.sucesso");
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
			if(Pagina.getParam(request, "paginaAreaSitePa") != null)
				paginaPgn.setPaginaAreaSitePa((PaginaAreaSitePa)paginaAreaSiteDao.buscar(PaginaAreaSitePa .class, Long.valueOf(Pagina.getParam(request, "paginaAreaSitePa"))));
	
			paginaPgn.setNomePgn((Pagina.getParam(request, "nomePgn"))); 
	
			if(Pagina.getParam(request, "paginaPgn") != null)
				paginaPgn.setPaginaPgn((PaginaPgn)paginaDao.buscar(PaginaPgn .class, Long.valueOf(Pagina.getParam(request, "paginaPgn"))));
	
			paginaPgn.setTituloPgn((Pagina.getParam(request, "tituloPgn"))); 
			
			if(Pagina.getParam(request, "idiomaIdm") != null)
				paginaPgn.setIdiomaIdm((IdiomaIdm)idiomaDao.buscar(IdiomaIdm .class, Long.valueOf(Pagina.getParam(request, "idiomaIdm"))));
	
			paginaPgn.setUrlPgn((Pagina.getParam(request, "urlPgn")));
			paginaPgn.setParametrosPgn((Pagina.getParam(request, "parametrosPgn")));
			paginaPgn.setIndHomePgn((Pagina.getParam(request, "indHomePgn")));
			paginaPgn.setIndLoginPgn((Pagina.getParam(request, "indLoginPgn")));
			paginaPgn.setIndMapaPgn((Pagina.getParam(request, "indMapaPgn")));
			paginaPgn.setTituloMapaPgn((Pagina.getParam(request, "tituloMapaPgn")));
			paginaPgn.setDescricaoMapaPgn((Pagina.getParam(request, "descricaoMapaPgn(")));
	
			if (Pagina.getParam(request, "qtdSubAreaPgn") != null)
				paginaPgn.setQtdSubAreaPgn(Integer.valueOf(Pagina.getParamInt(request, "qtdSubAreaPgn")));
			session.setAttribute("objPesquisa", paginaPgn);
		} catch (ECARException e) {
			org.apache.log4j.Logger.getLogger(this.getClass()).error(e);
			msg = _msg.getMensagem(e.getMessageKey());
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
			paginaPgn = (PaginaPgn) session.getAttribute("objPesquisa");
			List lista = paginaDao.pesquisar(paginaPgn, new String[] {"nomePgn","asc"});
			boolean pesquisou = (lista.size() > 0) ? true : false;
			session.setAttribute("lista", lista);
			if (pesquisou) {
				submit = "frm_nav.jsp";
				msg = null;
			} else {
				Mensagem.setInput(_jspx_page_context, "document.form.hidPesquisou.value", "false");
				submit = "frm_pes.jsp";
				msg = _msg.getMensagem("paginaPgn.pesquisa.nenhumRegistro");
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