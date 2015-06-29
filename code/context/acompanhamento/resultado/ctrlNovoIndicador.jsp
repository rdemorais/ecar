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
<!-- campo hidden para nao perder variaveis necessarias ao botao avançar e retroceder-->
<input type="hidden" name="codArisControle" value="<%=Pagina.getParamStr(request, "codArisControle")%>">
<input type="hidden" name="itemDoNivelClicado" value="<%=Pagina.getParamStr(request, "itemDoNivelClicado")%>">
<input type="hidden" name="periodo" value="<%=Pagina.getParamStr(request, "periodo")%>">
<input type="hidden" name="clicouAba" value="<%= Pagina.getParamStr(request, "clicouAba")%>">
<input type="hidden" name="codArisControleVisualizacao"	value="<%=Pagina.getParamStr(request, "codArisControleVisualizacao")%>">

<input type="hidden" name="mesReferencia" value="<%=Pagina.getParamStr(request, "mesReferencia")%>">
<input type="hidden" name="codigosItem" value="<%=Pagina.getParamStr(request, "codigosItem")%>">
<input type="hidden" name="semInformacaoNivelPlanejamento" value="<%=Pagina.getParamStr(request, "semInformacaoNivelPlanejamento")%>" >
<input type="hidden" name = "tela" value="<%=Pagina.getParamStr(request, "tela") %>">
<input type="hidden" name="codTipoAcompanhamento" value="<%= Pagina.getParamStr(request, "codTipoAcompanhamento") %>">
<input type="hidden" name="primeiroIettClicado" value="<%= Pagina.getParamStr(request, "primeiroIettClicado") %>">
<input type="hidden" name="codAriFilho" value="<%= Pagina.getParamStr(request, "codAriFilho") %>">


<!-- Campo necessario para guardar a forma de visualização escolhida no filtro -->
<input type="hidden" name="hidFormaVisualizacaoEscolhida" value='<%=Pagina.getParamStr(request, "hidFormaVisualizacaoEscolhida")%>'>

<input type="hidden" name="tipoPadraoExibicaoAba" value="<%= Pagina.getParamStr(request, "tipoPadraoExibicaoAba")%>">

<%
	String msg = "";
	String submit = "";
	try{
		new AcompRealFisicoDao(request).incAcompRealFisicoArf(request);
		msg = _msg.getMensagem("acompanhamento.realizadoFisico.novoIndicador.inclusaoSucesso");
		submit = "indicadoresResultado.jsp";
	}
	catch ( ECARException e){
		org.apache.log4j.Logger.getLogger(this.getClass()).error(e);
		submit = "indicadoresResultado.jsp";
		msg = _msg.getMensagem(e.getMessageKey());
	} catch (Exception e){
	%>
		<%@ include file="/excecao.jsp"%>
	<%
	}
	if (msg != null)
		Mensagem.setInput(_jspx_page_context, "document.form.msgOperacao.value", msg); 
%>

<script>
    document.form.codAriFilho.value = '';
	document.form.action = "<%=submit%>";
	document.form.submit();
</script>

</form>
</body>
</html>

