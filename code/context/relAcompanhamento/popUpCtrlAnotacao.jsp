<%@ page import="ecar.dao.ItemEstrutMarcadorDao" %>
<%@ page import="ecar.pojo.ItemEstrutMarcadorIettm" %>
<%@ taglib uri="/WEB-INF/ecar-util.tld" prefix="util" %>
<%@ include file="../frm_global.jsp"%>

<html lang="pt-br">
<head>
<%@ include file="../include/meta.jsp"%>
</head>

<body>
<form name="form" action="popUpListaAnotacao.jsp">
<input type="hidden" name="codIett" value=<%=Pagina.getParamStr(request, "codIett")%>>
<input type="hidden" name="codUsu" value=<%=Pagina.getParamStr(request, "codUsu")%>>

<%
String acao = Pagina.getParamStr(request, "hidAcao");
ItemEstrutMarcadorDao itemMarcadorDao = new ItemEstrutMarcadorDao(request);
boolean fechar = false;
String msg = "";
try{
	if("incluir".equals(acao)){
		ItemEstrutMarcadorIettm anotacao = new ItemEstrutMarcadorIettm();
		itemMarcadorDao.setItemEstrutMarcador(request, anotacao);
		itemMarcadorDao.salvar(anotacao);
		fechar = true;
		msg = _msg.getMensagem("itemEstrutura.anotacao.inclusao.sucesso");
	}
	if("alterar".equals(acao)){
		ItemEstrutMarcadorIettm anotacao = (ItemEstrutMarcadorIettm) itemMarcadorDao.buscar(ItemEstrutMarcadorIettm.class, Long.valueOf(Pagina.getParamStr(request,"codIettm")));
		itemMarcadorDao.setItemEstrutMarcador(request, anotacao);
		itemMarcadorDao.alterar(anotacao);
		fechar = true;
		msg = _msg.getMensagem("itemEstrutura.anotacao.alteracao.sucesso");
	}
	if("excluir".equals(acao)){
		itemMarcadorDao.excluir(request.getParameterValues("excluir"));
		fechar = true;
		msg = _msg.getMensagem("itemEstrutura.anotacao.exclusao.sucesso");		
	}
} catch ( ECARException e){
	org.apache.log4j.Logger.getLogger(this.getClass()).error(e);
	msg = _msg.getMensagem(e.getMessageKey());
} catch (Exception e){
%>
	<%@ include file="/excecao.jsp"%>
<%
}
%>
</form>
<script>
alert("<%=msg%>");
window.opener.reload();
<%
if(fechar)
	out.println("window.close();");
else
	out.println("document.form.submit();");
%>

</script>
</body>
</html>