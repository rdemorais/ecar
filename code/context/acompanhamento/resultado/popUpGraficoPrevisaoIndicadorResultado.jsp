<%@ page import="ecar.pojo.AcompRealFisicoArf" %>
<%@ page import="ecar.pojo.ItemEstrtIndResulIettr" %>
<%@ page import="ecar.dao.ItemEstrtIndResulDao" %>

<%@ include file="../../frm_global.jsp"%>
 
<html lang="pt-br">
<head>
<%@ include file="../../include/meta.jsp"%>
<%@ include file="../../titulo.jsp"%>

<link rel="stylesheet" href="<%=_pathEcar%>/css/style_<%=configuracaoCfg.getEstilo().getNome()%>.css" type="text/css">

<%
	//TODO: Retirar validação pra casacivil quando o protótipo for implementado.
	boolean exibirPrototipo = false;

	if(_pathEcar.contains("/casacivil") || "127.0.0.1".equals(request.getRemoteAddr())){
		exibirPrototipo = true;
	}
%>

<%
if(exibirPrototipo){
%>
<script language="javascript">
function incrementar(form){
	var valor = form.comboIndicadoresGrafico.value;

	if(valor != ""){
		eval("document.form.hid_" + valor + ".value = 'S';");
		form.action = "popUpGraficoPrevisaoIndicadorResultado.jsp";
		form.submit();
	}
	else {
		alert("Escolha uma opção!");
	}
}
function decrementar(form){
	var valor = form.comboIndicadoresGrafico.value;

	if(valor != ""){
		eval("document.form.hid_" + valor + ".value = '';");
		form.action = "popUpGraficoPrevisaoIndicadorResultado.jsp";
		form.submit();
	}
	else {
		alert("Escolha uma opção!");
	}
}


function gerarGrafico(){
	baseActionURL = "<%=_pathEcar%>/acompanhamento/resultado/popUpGraficoPrevisaoIndicadorResultado.jsp";

	baseActionURL += "?comQtde=<%=Pagina.getParamStr(request,"comQtde")%>";
	baseActionURL += "&soPrevisao=<%=Pagina.getParamStr(request,"soPrevisao")%>";
	baseActionURL += "&mesIni=<%=Pagina.getParamStr(request,"mesIni")%>";
	baseActionURL += "&anoIni=<%=Pagina.getParamStr(request,"anoIni")%>";
	baseActionURL += "&codAri=<%=Pagina.getParamStr(request,"codAri")%>";
	baseActionURL += "&qtdeAcompRealFisico=<%=Pagina.getParamStr(request,"qtdeAcompRealFisico")%>";

	projecao = document.getElementById('select_projecao').value

	baseActionURL += "&projecao="+projecao;

	document.getElementById('form').action = baseActionURL;
	document.getElementById('form').submit();
				 
	//alert(baseActionURL);
	
}

</script>
<%
}
%>

</head>

<body class="corpo_popup">
<%
try{
%>

<%
	ItemEstrtIndResulIettr indicador = (ItemEstrtIndResulIettr) new ItemEstrtIndResulDao(request).buscar(ItemEstrtIndResulIettr.class, Long.valueOf(Pagina.getParamStr(request,"codIndicador")));
%>

<H1>Gráfico de Previsão - <%=indicador.getNomeIettir()%></H1>

<select id="select_projecao" style="align:left;" onchange="javascript:gerarGrafico()">
	<option id="opt_media"      value="1" <%= Pagina.getParamStr(request,"projecao").equals("1")?"selected":"" %> >Média</option>
	<option id="opt_aceleracao" value="2" <%= Pagina.getParamStr(request,"projecao").equals("2")?"selected":"" %> >Variação da Média</option>
</select>


<form name="form" id="form" method="post" action="">

	<input type="hidden" name="codIndicador" value="<%=request.getParameter("codIndicador")%>">
	<input type="hidden" name="codAri" value="<%=request.getParameter("codAri")%>">

	<%
	String parametrosHidden = "";
	
	if(exibirPrototipo){
	parametrosHidden = "&exibirIdh=" + request.getParameter("hid_idh") + 
							"&exibirIsa=" + request.getParameter("hid_isa") + 
							"&exibirEes=" + request.getParameter("hid_ees") + 
							"&exibirPsa=" + request.getParameter("hid_psa");
	}
	%>
	<iframe 
		scrolling="auto"
		width="720px"
		height="450px"
		src="<%=_pathEcar%>/GraficoPrevisaoIndicadoresResultado?comQtde=<%=Pagina.getParamStr(request,"comQtde")%>&soPrevisao=<%=Pagina.getParamStr(request,"soPrevisao")%>&mesIni=<%=Pagina.getParamStr(request,"mesIni")%>&anoIni=<%=Pagina.getParamStr(request,"anoIni")%>&codIndicador=<%=request.getParameter("codIndicador")%>&codAri=<%=request.getParameter("codAri")%>&projecao=<%=request.getParameter("projecao")%><%=parametrosHidden%>"
	>
	</iframe>
	<%--
	if(exibirPrototipo){ //verficiar se é casacivil
	%>
		<select name="comboIndicadoresGrafico">
			<option value=""></option>
			<option value="idh">IDH</option>
			<option value="isa">Inv. Saneamento</option>
			<option value="ees">Estrutura Esgoto</option>
			<option value="psa">Posto Saúde</option>
		</select>
		<input type="button" name="btIncluir" value="+" onclick="javascript:incrementar(form)">
		<input type="button" name="btExcluir" value="-" onclick="javascript:decrementar(form)">
		
		<input type="hidden" name="hid_idh" value="<%=request.getParameter("hid_idh")%>">
		<input type="hidden" name="hid_isa" value="<%=request.getParameter("hid_isa")%>">
		<input type="hidden" name="hid_ees" value="<%=request.getParameter("hid_ees")%>">
		<input type="hidden" name="hid_psa" value="<%=request.getParameter("hid_psa")%>">
	<%
	}
	--%>
	
</form>
<%
} catch (Exception e){
%>
	<%@ include file="/excecao.jsp"%>
<%
}
%>
</body>

</html>

