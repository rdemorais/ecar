<%@ include file="../../login/validaAcesso.jsp"%>
<%@ include file="../../frm_global.jsp"%>

<%@ page import="java.util.List" %>
<%@ page import="ecar.pojo.DestaqueSubAreaDtqsa" %>
<%@ page import="ecar.dao.DestaqueSubAreaDao" %>
<%@ page import="java.util.Iterator" %>
<%@ page import="java.util.ArrayList" %>

<html lang="pt-br">
<head>
<%@ include file="../../include/meta.jsp"%>
<%@ include file="/titulo.jsp"%>
</head>
<body>
	
	<form name="form" method="post">
    <!-- campo de controle para a navegação de registros -->
    
	<input type="hidden" name="hidRegistro" value="<%=Pagina.getParamStr(request, "hidRegistro")%>">	
	<!-- campo de controle para mensagens -->
	<input type="hidden" name="msgOperacao" value=""> 
	<input type="hidden" name="msgPesquisa" value="">
	<input type="hidden" name="hidPesquisou" value="">	
	
	</form>
	
	<%

	DestaqueSubAreaDtqsa destaqueSubArea = new DestaqueSubAreaDtqsa();
	DestaqueSubAreaDao destaqueSubAreaDao = new DestaqueSubAreaDao(request);
	
	String hidAcao = "";
	
	hidAcao = Pagina.getParamStr(request, "hidAcao");
		
	String msg = null;
	String submit = "frm_pes.jsp";

	/* default sempre refazer a pesquisa */
	boolean refazPesquisa = true;  

	boolean incluir = "incluir".equalsIgnoreCase(hidAcao);
	boolean alterar = "alterar".equalsIgnoreCase(hidAcao);
	boolean excluir = "excluir".equalsIgnoreCase(hidAcao);
	boolean pesquisar = "pesquisar".equalsIgnoreCase(hidAcao);
		
	if (incluir) {
		refazPesquisa = false;
		submit = "frm_inc.jsp";
		try {
			destaqueSubAreaDao.setDestaqueSubArea(destaqueSubArea,request,true);
			destaqueSubAreaDao.salvar(destaqueSubArea);
			msg = _msg.getMensagem("destaqueSubArea.inclusao.sucesso");
			session.removeAttribute("objDestaqueSubArea");
		} catch (ECARException e) {
			org.apache.log4j.Logger.getLogger(this.getClass()).error(e);
			session.setAttribute("objDestaqueSubArea", destaqueSubArea);
			msg = _msg.getMensagem(e.getMessageKey());
		} catch (Exception e){
		%>
			<%@ include file="/excecao.jsp"%>
		<%
		}
	} else if (alterar) {
		try {
			/* busca o objeto para manter o que já esta cadastrado */
			destaqueSubArea = (DestaqueSubAreaDtqsa) destaqueSubAreaDao.buscar(DestaqueSubAreaDtqsa.class, Long.valueOf(Pagina.getParamStr(request, "codigo")));
			destaqueSubAreaDao.setDestaqueSubArea(destaqueSubArea,request,true);
			destaqueSubAreaDao.alterar(destaqueSubArea);
			msg = _msg.getMensagem("destaqueSubArea.alteracao.sucesso");
			session.removeAttribute("objDestaqueSubArea");
		} catch (ECARException e) {
			org.apache.log4j.Logger.getLogger(this.getClass()).error(e);
			refazPesquisa = false;
			submit = "frm_alt.jsp";
			session.setAttribute("objDestaqueSubArea", destaqueSubArea);
			msg = _msg.getMensagem(e.getMessageKey());
		} catch (Exception e){
		%>
			<%@ include file="/excecao.jsp"%>
		<%
		}
	} else if (excluir) {
		try {
			/* busca e exclui o objeto do BD */
			destaqueSubAreaDao.excluir((DestaqueSubAreaDtqsa)destaqueSubAreaDao.buscar(DestaqueSubAreaDtqsa.class, Long.valueOf(Pagina.getParamStr(request, "codigo"))));
			msg = _msg.getMensagem("destaqueSubArea.exclusao.sucesso");
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
		destaqueSubAreaDao.setDestaqueSubArea(destaqueSubArea,request,false);		
		session.setAttribute("objPesquisa", destaqueSubArea);
	}
	/* da o alert para incluir, alterar, pesquisar ou excluir */
	if (msg != null)
		Mensagem.setInput(_jspx_page_context, "document.form.msgOperacao.value", msg); 
		
	if (refazPesquisa) {
		try {
			destaqueSubArea = (DestaqueSubAreaDtqsa) session.getAttribute("objPesquisa");
			List lista = destaqueSubAreaDao.pesquisar(destaqueSubArea, new String[]{"identificacaoDtqsa", "asc"});
			boolean pesquisou = (lista.size() > 0) ? true : false;
			session.setAttribute("lista", lista);
			if (pesquisou) {
				submit = "frm_nav.jsp";
				msg = null;
			} else {
				Mensagem.setInput(_jspx_page_context, "document.form.hidPesquisou.value", "false");
				submit = "frm_pes.jsp";
				msg = _msg.getMensagem("destaqueSubArea.pesquisa.nenhumRegistro");
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