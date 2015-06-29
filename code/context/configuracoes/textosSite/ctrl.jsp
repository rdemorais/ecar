<%@ include file="../../login/validaAcesso.jsp"%>

<%@ page import="ecar.pojo.TextosSiteTxt" %>
<%@ page import="ecar.dao.TextosSiteDao" %>
<%@ page import="ecar.pojo.EmpresaEmp" %>
<%@ page import="ecar.pojo.IdiomaIdm" %>
<%@ page import="java.util.List" %>
<%@ include file="../../frm_global.jsp"%>

<html lang="pt-br">
<head>
<%@ include file="../../include/meta.jsp"%>
<%@ include file="/titulo.jsp"%>
</head>
<body>

<form name="form" method="post">
	<input type="hidden" name="hidRegistro" value="<%=Pagina.getParamInt(request, "hidRegistro")%>">
	<input type="hidden" name="msgOperacao" value=""> 
	<input type="hidden" name="msgPesquisa" value="">	
	
	<input type="hidden" name="hidPesquisou" value="">	
</form>

<%
	TextosSiteTxt textoSite = new TextosSiteTxt();
	TextosSiteDao textoSiteDao = new TextosSiteDao(request);
	
	String hidAcao = Pagina.getParam(request, "hidAcao");
	String msg = null;
	String submit = "frm_pes.jsp";
	boolean refazPesquisa = true;
	boolean incluir = "incluir".equalsIgnoreCase(hidAcao);
	boolean alterar = "alterar".equalsIgnoreCase(hidAcao);
	boolean excluir = "excluir".equalsIgnoreCase(hidAcao);
	boolean pesquisar = "pesquisar".equalsIgnoreCase(hidAcao);
	
	if (incluir) {
		refazPesquisa = false;
		submit = "frm_inc.jsp";
		try {
			if(Pagina.getParam(request, "empresaEmp") != null)
				textoSite.setEmpresaEmp((EmpresaEmp) textoSiteDao.buscar(EmpresaEmp.class, Long.valueOf(Pagina.getParam(request, "empresaEmp"))));
	
			if(Pagina.getParam(request, "idiomaIdm") != null)
				textoSite.setIdiomaIdm((IdiomaIdm) textoSiteDao.buscar(IdiomaIdm.class, Long.valueOf(Pagina.getParam(request, "idiomaIdm"))));
			
			/* melhor usar getParamStr, pois nunca devolve null */		
			textoSite.setDescricaoUsoTxts(Pagina.getParamStr(request, "descricaoUsoTxts").trim());
			textoSite.setTextoTxts(Pagina.getParamStr(request, "textoTxts").trim());
			textoSite.setIndAtivoTxts(Pagina.getParamStr(request, "indAtivoTxts").trim());
			
			if( Pagina.getParam(request, "emailResponsavelTxts") != null )
				textoSite.setEmailResponsavelTxts(Pagina.getParam(request, "emailResponsavelTxts"));

			textoSiteDao.salvar((TextosSiteTxt)textoSite);
			msg = _msg.getMensagem("textoSite.inclusao.sucesso" );
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
//			chave.setCodTxts(Long.valueOf(Pagina.getParamLong(request, "codigoTxts")));
//			chave.setCodEmp(Long.valueOf(Pagina.getParamLong(request, "codigoEmp")));//
//			chave.setCodIdm(Long.valueOf(Pagina.getParamLong(request, "codigoIdm")));
			textoSite.setCodTxtS(Long.valueOf(Pagina.getParamLong(request, "codigoTxts")));
			textoSite = (TextosSiteTxt) textoSiteDao.buscar(TextosSiteTxt.class, Long.valueOf(Pagina.getParamLong(request, "codigoTxts")));

			if(Pagina.getParam(request, "empresaEmp") != null)
				textoSite.setEmpresaEmp((EmpresaEmp) textoSiteDao.buscar(EmpresaEmp.class, Long.valueOf(Pagina.getParam(request, "empresaEmp"))));
	
			if(Pagina.getParam(request, "idiomaIdm") != null)
				textoSite.setIdiomaIdm((IdiomaIdm) textoSiteDao.buscar(IdiomaIdm.class, Long.valueOf(Pagina.getParam(request, "idiomaIdm"))));
			
			/* melhor usar getParamStr, pois nunca devolve null */		
			textoSite.setDescricaoUsoTxts(Pagina.getParamStr(request, "descricaoUsoTxts").trim());
			textoSite.setTextoTxts(Pagina.getParamStr(request, "textoTxts").trim());
			textoSite.setIndAtivoTxts(Pagina.getParamStr(request, "indAtivoTxts").trim());
			
			if( Pagina.getParam(request, "emailResponsavelTxts") != null )
				textoSite.setEmailResponsavelTxts(Pagina.getParam(request, "emailResponsavelTxts"));
			
			textoSiteDao.alterar(textoSite);
			msg = _msg.getMensagem("textoSite.alteracao.sucesso" );
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
//			chave.setCodTxts(Long.valueOf(Pagina.getParamLong(request, "codigoTxts")));
//			chave.setCodEmp(Long.valueOf(Pagina.getParamLong(request, "codigoEmp")));
//			chave.setCodIdm(Long.valueOf(Pagina.getParamLong(request, "codigoIdm")));
			textoSite.setCodTxtS(Long.valueOf(Pagina.getParamLong(request, "codigoTxts")));
			textoSite = (TextosSiteTxt) textoSiteDao.buscar(TextosSiteTxt.class, Long.valueOf(Pagina.getParamLong(request, "codigoTxts")));
		
			textoSiteDao.excluir((TextosSiteTxt) textoSite);
			msg = _msg.getMensagem("textoSite.exclusao.sucesso" );
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
		try {
			if(Pagina.getParam(request, "empresaEmp") != null)
				textoSite.setEmpresaEmp((EmpresaEmp) textoSiteDao.buscar(EmpresaEmp.class, Long.valueOf(Pagina.getParam(request, "empresaEmp"))));
	
			if(Pagina.getParam(request, "idiomaIdm") != null)
				textoSite.setIdiomaIdm((IdiomaIdm) textoSiteDao.buscar(IdiomaIdm.class, Long.valueOf(Pagina.getParam(request, "idiomaIdm"))));

		} catch (ECARException e) {
			org.apache.log4j.Logger.getLogger(this.getClass()).error(e);
			msg = _msg.getMensagem(e.getMessageKey());
		} catch (Exception e){
		%>
			<%@ include file="/excecao.jsp"%>
		<%
		}
		/* melhor usar getParamStr, pois nunca devolve null */		
		textoSite.setDescricaoUsoTxts(Pagina.getParam(request, "descricaoUsoTxts"));
		textoSite.setTextoTxts(Pagina.getParam(request, "textoTxts"));
		textoSite.setIndAtivoTxts(Pagina.getParam(request, "indAtivoTxts"));
		textoSite.setEmailResponsavelTxts(Pagina.getParam(request, "emailResponsavelTxts"));

		session.setAttribute("objPesquisa", textoSite);
	}
	/* da o alert para incluir, alterar, pesquisar ou excluir */
	if (msg != null) 
		Mensagem.setInput(_jspx_page_context, "document.form.msgOperacao.value", msg); 

	if (refazPesquisa) {
		try {
			textoSite = (TextosSiteTxt) session.getAttribute("objPesquisa");
			List lista = textoSiteDao.pesquisar(textoSite, null);
			boolean pesquisou = (lista.size() > 0) ? true : false;
			session.setAttribute("lista", lista);
			if (pesquisou) {
				submit = "frm_nav.jsp";
				msg = null;
			} else {
				Mensagem.setInput(_jspx_page_context, "document.form.hidPesquisou.value", "false");
				submit = "frm_pes.jsp";
				msg = _msg.getMensagem("textoSite.pesquisa.nenhumRegistro");
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