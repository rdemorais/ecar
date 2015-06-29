<%@ include file="../../login/validaAcesso.jsp"%>

<%@ include file="../../frm_global.jsp"%>
<%@ page import="ecar.pojo.Uf" %>
<%@ page import="ecar.dao.UfDao" %>
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
	Uf uf = new Uf();
	UfDao ufDao = new UfDao(request);
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
		uf.setCodUf(Pagina.getParamStr(request, "codUf").trim().toUpperCase());
		uf.setDescricaoUf(Pagina.getParamStr(request, "descricaoUf").trim()); 
		submit = "frm_inc.jsp";
		refazPesquisa = false;
		try {
			ufDao.salvar(uf);
			msg = _msg.getMensagem("uf.inclusao.sucesso" );
			session.removeAttribute("objUf");
		} catch (ECARException e) {
			org.apache.log4j.Logger.getLogger(this.getClass()).error(e);
			session.setAttribute("objUf", uf);
			msg = _msg.getMensagem(e.getMessageKey());
		} catch (Exception e){
		%>
			<%@ include file="/excecao.jsp"%>
		<%
		}
	} else if (alterar) {
		try {
			uf = (Uf) ufDao.buscar(Uf.class, Pagina.getParamStr(request, "codigo").toUpperCase());
			uf.setCodUf(Pagina.getParamStr(request, "codigo").trim().toUpperCase());
			uf.setDescricaoUf(Pagina.getParamStr(request, "descricaoUf").trim());
			ufDao.alterar(uf);
			msg = _msg.getMensagem("uf.alteracao.sucesso");
			session.removeAttribute("objUf");
		} catch (ECARException e) {
			org.apache.log4j.Logger.getLogger(this.getClass()).error(e);
			refazPesquisa = false;
			submit = "frm_alt.jsp";
			session.setAttribute("objUf", uf);
			msg = _msg.getMensagem(e.getMessageKey());
		} catch (Exception e){
		%>
			<%@ include file="/excecao.jsp"%>
		<%
		}
	} else if (excluir) {
		try {
			ufDao.excluir(ufDao.buscar(Uf.class, Pagina.getParam(request, "codigo").toUpperCase()));
			msg = _msg.getMensagem("uf.exclusao.sucesso" );
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
		uf.setCodUf(Pagina.getParam(request, "codUf"));
		uf.setDescricaoUf(Pagina.getParam(request, "descricaoUf"));
		session.setAttribute("objPesquisa", uf);
	}
	/* da o alert para incluir, alterar, pesquisar ou excluir */
	if (msg != null)
		Mensagem.setInput(_jspx_page_context, "document.form.msgOperacao.value", msg); 
		
	if (refazPesquisa) {
		try {
			uf = (Uf) session.getAttribute("objPesquisa");
			List lista = ufDao.pesquisar(uf, new String[] {"codUf","asc"});
			boolean pesquisou = (lista.size() > 0) ? true : false;
			session.setAttribute("lista", lista);
			if (pesquisou) {
				submit = "frm_nav.jsp";
				msg = null;
			} else {
				Mensagem.setInput(_jspx_page_context, "document.form.hidPesquisou.value", "false");
				submit = "frm_pes.jsp";
				msg = _msg.getMensagem("uf.pesquisa.nenhumRegistro");
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
			
