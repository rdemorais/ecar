<%@ include file="../../login/validaAcesso.jsp"%>
<%@ include file="../../frm_global.jsp"%>
 
<%@ page import="ecar.pojo.UsuarioUsu" %>
<%@ page import="ecar.dao.UsuarioDao" %>
<%@ page import="java.util.List" %>

<html lang="pt-br">
<head>
<%@ include file="../../include/meta.jsp"%>
<%@ include file="/titulo.jsp"%>
</head>
<body>

<form name="form" method="post">
	
	<!-- campo de controle para mensagens -->
	<input type="hidden" name="msgOperacao" value=""> 
	<input type="hidden" name="msgPesquisa" value="">	
	
</form>
 
<%
	UsuarioUsu usuario = new UsuarioUsu();
	UsuarioDao usuarioDao = new UsuarioDao(request);
	
	String msg = null;
	String submit = "form.jsp";

	try {			
		/* o buscar deve estar no try, pois pode ocorrer um erro de hibernate ou bd */			
		usuario = (UsuarioUsu) usuarioDao.buscar(UsuarioUsu.class, Long.valueOf(Pagina.getParamLong(request, "codUsu")));			
		usuarioDao.alterarCadastroSite(usuario, request);
		msg = _msg.getMensagem("usuario.alteracao.sucesso" );
		/* da o alert para incluir, alterar, pesquisar ou excluir */
		if (msg != null) 
			Mensagem.setInput(_jspx_page_context, "document.form.msgOperacao.value", msg); 
	} catch (ECARException e) {
		org.apache.log4j.Logger.getLogger(this.getClass()).error(e);
		msg = _msg.getMensagem(e.getMessageKey());
	} catch (Exception e){
	%>
		<%@ include file="/excecao.jsp"%>
	<%
	}
	%>
	<script language="javascript">
		document.form.action = "<%=submit%>";
		document.form.submit();
	</script> 
</body>
</html>
			