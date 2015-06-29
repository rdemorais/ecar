<%@ include file="../../login/validaAcesso.jsp"%>

<%@ page import="ecar.pojo.ConfiguracaoCfg" %>
<%@ page import="ecar.pojo.TipoFuncAcompTpfa" %>
<%@ page import="ecar.pojo.SisAtributoSatb" %>
<%@ page import="ecar.pojo.SisGrupoAtributoSga" %>

<%@ page import="ecar.dao.ConfiguracaoDao" %>
<%@ page import="ecar.dao.TipoFuncAcompDao" %>
<%@ page import="ecar.dao.SisAtributoDao" %>
<%@ page import="ecar.dao.SisGrupoAtributoDao" %>

<%@ page import="java.util.Iterator" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.Set" %>

<%@ include file="../../frm_global.jsp"%>

<html lang="pt-br">
<head>
<%@ include file="../../include/meta.jsp"%>
<%@ include file="/titulo.jsp"%>
</head>
<body>

<form name="form" method="post">
<!-- campo de controle para mensagens -->
<input type="hidden" name="msgOperacao" value=""> 
</form>

<%
	String msg = null;
	String submit = "form.jsp";
	try{
		ConfiguracaoCfg configuracao = new ConfiguracaoCfg();
		ConfiguracaoDao configuracaoDao = new ConfiguracaoDao(request);
		
		List confg = configuracaoDao.listar(ConfiguracaoCfg.class, null);
		if(confg != null && confg.size() > 0)
				configuracao = (ConfiguracaoCfg) confg.iterator().next();
		
		configuracaoDao.setConfiguracao(request, configuracao);
		
		configuracaoDao.salvar(request, configuracao);
		msg = _msg.getMensagem("configuracao.geral.alteracao.sucesso" );	
	} catch(ECARException e){
		org.apache.log4j.Logger.getLogger(this.getClass()).error(e);
		msg = _msg.getMensagem(e.getMessageKey(),(String[])e.getMessageArgs());
	} catch (Exception e){
	%>
		<%@ include file="/excecao.jsp"%>
	<%
	}

	/* da o alert para incluir, alterar, pesquisar ou excluir */
	if (msg != null) 
		Mensagem.setInput(_jspx_page_context, "document.form.msgOperacao.value", msg); 	
	
	
%>

<script language="javascript">
document.form.action = "<%=submit%>";
document.form.submit();
</script> 
</body>
</html>