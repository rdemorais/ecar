<%@ include file="../../login/validaAcesso.jsp"%>
<%@ include file="../../frm_global.jsp"%>

<%@ page import="ecar.dao.AcompRealFisicoDao" %>

<html lang="pt-br">
<head>
<%@ include file="../../include/meta.jsp"%>
</head>   
<body>
<form name="form" method="post">  

<!-- campo de controle para mensagens -->
<input type="hidden" name="msgOperacao" value=""> 
<input type="hidden" name="codAri" value="<%=Pagina.getParamStr(request, "codAri")%>"> 
<input type="hidden" name="subNiveis" value="<%=Pagina.getParamStr(request, "subNiveis")%>">
<input type="hidden" name="primeiroItemClicado" value="<%=Pagina.getParamStr(request, "primeiroItemClicado")%>">
<input type="hidden" name="primeiroItemAriClicado" value="<%=Pagina.getParamStr(request, "primeiroItemAriClicado")%>">
<input type="hidden" name="codAcomp" value="<%=Pagina.getParamStr(request, "codAcomp")%>">
<input type="hidden" name="codAriSubNivel" value="<%=Pagina.getParamStr(request, "codAriSubNivel")%>">
<input type="hidden" name="primeiroAcomp" value="<%=Pagina.getParamStr(request, "primeiroAcomp")%>">
<input type="hidden" name="radConcluido" value="<%=Pagina.getParamStr(request, "radConcluido")%>">
<!-- Campo necessario para guardar a forma de visualização escolhida no filtro -->
<input type="hidden" name="hidFormaVisualizacaoEscolhida" value='<%=Pagina.getParamStr(request, "hidFormaVisualizacaoEscolhida")%>'>
<input type="hidden" name="tipoPadraoExibicaoAba" value="<%= Pagina.getParamStr(request, "tipoPadraoExibicaoAba")%>">

<%
    String nivel = Pagina.getParamStr(request, "hidNivel");
	String msg = "";
	String submit = "";
	
	if ("filho".equals(nivel))
	{
		try{
			new AcompRealFisicoDao(request).atualizarFilho(request);
			msg = _msg.getMensagem("acompanhamento.realizadoFisico.atualizacao.sucesso");
			submit = "realizadoFisico.jsp";
		}
		catch ( ECARException e){
			org.apache.log4j.Logger.getLogger(this.getClass()).error(e);
			submit = "realizadoFisico.jsp";
			msg = _msg.getMensagem(e.getMessageKey());
		} catch (Exception e){
		%>
			<%@ include file="/excecao.jsp"%>
		<%
		}
		if (msg != null)
			Mensagem.setInput(_jspx_page_context, "document.form.msgOperacao.value", msg); 
	}
	else
	{
		try{
			new AcompRealFisicoDao(request).atualizar(request);
			msg = _msg.getMensagem("acompanhamento.realizadoFisico.atualizacao.sucesso");
			submit = "realizadoFisico.jsp";
		}
		catch ( ECARException e){
			org.apache.log4j.Logger.getLogger(this.getClass()).error(e);
			submit = "realizadoFisico.jsp";
			msg = _msg.getMensagem(e.getMessageKey());
		} catch (Exception e){
		%>
			<%@ include file="/excecao.jsp"%>
		<%
		}
		if (msg != null)
			Mensagem.setInput(_jspx_page_context, "document.form.msgOperacao.value", msg); 
	}
%>

<script>
	document.form.action = "<%=submit%>";
	document.form.submit();
</script>

</form>
</body>
</html>

