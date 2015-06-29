<%@ include file="../../login/validaAcesso.jsp"%>
<%@ include file="../../frm_global.jsp"%>

<%@ page import="java.util.List" %>
<%@ page import="ecar.pojo.ContatoAreaCtta" %>
<%@ page import="ecar.dao.ContatoAreaDao" %>
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

	ContatoAreaCtta contatoArea = new ContatoAreaCtta();
	ContatoAreaDao contatoAreaDao = new ContatoAreaDao(request);
	
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
			contatoAreaDao.setContatoArea(contatoArea,request,true);
			contatoAreaDao.salvar(contatoArea);
			msg = _msg.getMensagem("contatoArea.inclusao.sucesso");
			session.removeAttribute("objContatoArea");
		} catch (ECARException e) {
			org.apache.log4j.Logger.getLogger(this.getClass()).error(e);
			session.setAttribute("objContatoArea", contatoArea);
			msg = _msg.getMensagem(e.getMessageKey());
		} catch (Exception e){
		%>
			<%@ include file="/excecao.jsp"%>
		<%
		}
	} else if (alterar) {
		try {
			/* busca o objeto para manter o que já esta cadastrado */
			contatoArea = (ContatoAreaCtta) contatoAreaDao.buscar(ContatoAreaCtta.class, Long.valueOf(Pagina.getParamStr(request, "codigo")));
			contatoAreaDao.setContatoArea(contatoArea,request,true);
			contatoAreaDao.alterar(contatoArea);
			msg = _msg.getMensagem("contatoArea.alteracao.sucesso");
			session.removeAttribute("objContatoArea");
		} catch (ECARException e) {
			org.apache.log4j.Logger.getLogger(this.getClass()).error(e);
			refazPesquisa = false;
			submit = "frm_alt.jsp";
			session.setAttribute("objContatoArea", contatoArea);
			msg = _msg.getMensagem(e.getMessageKey());
		} catch (Exception e){
		%>
			<%@ include file="/excecao.jsp"%>
		<%
		}
	} else if (excluir) {
		try {
			/* busca e exclui o objeto do BD */
			contatoAreaDao.excluir((ContatoAreaCtta)contatoAreaDao.buscar(ContatoAreaCtta.class, Long.valueOf(Pagina.getParamStr(request, "codigo"))));
			msg = _msg.getMensagem("contatoArea.exclusao.sucesso");
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
		contatoAreaDao.setContatoArea(contatoArea,request,false);		
		session.setAttribute("objPesquisa", contatoArea);
	}
	/* da o alert para incluir, alterar, pesquisar ou excluir */
	if (msg != null)
		Mensagem.setInput(_jspx_page_context, "document.form.msgOperacao.value", msg); 
		
	if (refazPesquisa) {
		try {
			contatoArea = (ContatoAreaCtta) session.getAttribute("objPesquisa");
			List lista = contatoAreaDao.pesquisar(contatoArea, new String[]{"nomeCtta", "asc"});
			boolean pesquisou = (lista.size() > 0) ? true : false;
			session.setAttribute("lista", lista);
			if (pesquisou) {
				submit = "frm_nav.jsp";
				msg = null;
			} else {
				Mensagem.setInput(_jspx_page_context, "document.form.hidPesquisou.value", "false");
				submit = "frm_pes.jsp";
				msg = _msg.getMensagem("contatoArea.pesquisa.nenhumRegistro");
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