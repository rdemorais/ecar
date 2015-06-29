
<jsp:directive.page import="ecar.pojo.PoderPod"/>
<jsp:directive.page import="ecar.dao.PoderDao"/><%@ include file="../../login/validaAcesso.jsp"%>

<%@ page import="ecar.pojo.OrgaoOrg" %>
<%@ page import="ecar.pojo.UsuarioUsu" %>
<%@ page import="java.util.List" %>
<%@ include file="../../frm_global.jsp"%>

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
	PoderPod poder = new PoderPod();
	PoderDao poderDao = new PoderDao(request);
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
		/* melhor usar getParamStr, pois nunca devolve null */
		poder.setNomePod(Pagina.getParamStr(request, "nomePod").trim()); 
		poder.setSiglaPod((Pagina.getParamStr(request, "siglaPod").trim()).toUpperCase());
		poder.setIndAtivoPod(Pagina.getParamStr(request, "indAtivoPod"));

		refazPesquisa = false;
		submit = "frm_inc.jsp";
		try {
			poderDao.salvar(poder);
			msg = _msg.getMensagem("poder.inclusao.sucesso");
			session.removeAttribute("objPoderPod");
		} catch (ECARException e) {
			org.apache.log4j.Logger.getLogger(this.getClass()).error(e);
			session.setAttribute("objUnidadeOrcamentaria", poder);
			msg = _msg.getMensagem(e.getMessageKey());
		} catch (Exception e){
		%>
			<%@ include file="/excecao.jsp"%>
		<%
		}
	} else if (alterar) {
		try {
			/* busca o objeto para manter o que já esta cadastrado */
			poder = (PoderPod) poderDao.buscar(PoderPod.class, Long.valueOf(Pagina.getParam(request, "codigo")));
			/* altera o que foi modificado na tela */
			
			poder.setNomePod(Pagina.getParamStr(request, "nomePod").trim()); 
			poder.setSiglaPod(Pagina.getParamStr(request, "siglaPod").trim().toUpperCase());
			poder.setIndAtivoPod(Pagina.getParamStr(request, "indAtivoPod")); 
			
			/* altera no BD */
			poderDao.alterar(poder);
			msg = _msg.getMensagem("poder.alteracao.sucesso");
			session.removeAttribute("objPoderPod");
			
		} catch (ECARException e) {
			org.apache.log4j.Logger.getLogger(this.getClass()).error(e);
			refazPesquisa = false;
			submit = "frm_alt.jsp";
			session.setAttribute("objPoderPod", poder);
			msg = _msg.getMensagem(e.getMessageKey());
		} catch (Exception e){
		%>
			<%@ include file="/excecao.jsp"%>
		<%
		}
	} else if (excluir) {
		try {
			/* busca e exclui o objeto do BD */
			poderDao.excluir((PoderPod) poderDao.buscar(PoderPod.class, Long.valueOf(Pagina.getParam(request, "codigo"))));
			msg = _msg.getMensagem("poder.exclusao.sucesso");
			
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
		poder.setNomePod(Pagina.getParam(request, "nomePod")); 
		
		if(Pagina.getParam(request, "siglaPod") != null)
			poder.setSiglaPod(Pagina.getParam(request, "siglaPod").toUpperCase());
		
		poder.setIndAtivoPod(Pagina.getParam(request, "indAtivoPod")); 
		
		session.setAttribute("objPesquisa", poder);
	}
	/* da o alert para incluir, alterar, pesquisar ou excluir */
	if (msg != null)
		Mensagem.setInput(_jspx_page_context, "document.form.msgOperacao.value", msg); 
	
	if (refazPesquisa) {
		try {
			poder = (PoderPod) session.getAttribute("objPesquisa");
			List lista = poderDao.pesquisar(poder, new String[] {"nomePod","asc"});
			boolean pesquisou = (lista.size() > 0) ? true : false;
			session.setAttribute("lista", lista);
			if (pesquisou) {
				submit = "frm_nav.jsp";
				msg = null;
			} else {
				Mensagem.setInput(_jspx_page_context, "document.form.hidPesquisou.value", "false");
				submit = "frm_pes.jsp";
				msg = _msg.getMensagem("poder.pesquisa.nenhumRegistro");
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
