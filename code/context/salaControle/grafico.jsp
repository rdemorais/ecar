
<%@ include file="../login/validaAcesso.jsp"%>
<%@ include file="../frm_global.jsp"%>

<%@ taglib uri="/WEB-INF/ecar-util.tld" prefix="util" %>
<%@ taglib uri="/WEB-INF/ecar-combo.tld" prefix="combo" %>

<html lang="pt-br">
<head> 
<%@ include file="../include/meta.jsp"%>
<%@ include file="/titulo.jsp"%>
<link rel="stylesheet" href="<%=_pathEcar%>/css/style_<%=configuracaoCfg.getEstilo().getNome()%>.css" type="text/css">
<script language="javascript" src="../js/menu_retratil.js"></script>
<script language="javascript" src="../js/popUp.js"></script>
<script language="javascript">
function abrirGrafico(form, grafico, posicao){
	//var janela = abreJanela("popupGraficoOrgao.jsp?tipoGrafico=" + parametro, 700, 450, "popupGraficoOrgao");
	//janela.focus();
	
	/*
	grafico = "P" = Grafico Posições
	grafico = "O" = Grafico Órgão
	posicao = "A" = Administrador
	posicao = "R" = Responsavel Técnico
	*/
	
	var graficoFechar = "P";
	
	if(grafico == "P"){
		graficoFechar = "O";
	}
	
	var idAbrir = grafico + posicao;
	var idFechar = graficoFechar + posicao;
	
	document.getElementById(idAbrir).style.display = "";
	document.getElementById(idFechar).style.display = "none";
}

function pausar(form){
	if(form.pause.value == "S"){
		form.pause.value = "N";
		form.btPause.value = "Pausar [ || ]";
		refresh();
	}
	else {
		form.pause.value = "S";
		form.btPause.value = "Continue [ > ]";
	}
}

<%@ include file="tempoGrafico_temp.jsp"%>
function doLoad(){
	setTimeout("refresh()", <%=tempoTransicao%>);
}
function refresh(){
	if(document.form.pause.value == "N"){
		document.form.action = "situacaoPosicao.jsp";
		document.form.submit();
	}
}

function proximo(form){
	form.action = "situacaoPosicao.jsp";
	form.submit();
}
</script>
</head>
<%
try {
%>
<%@ include file="../cabecalho.jsp"%> 
<%@ include file="../divmenu.jsp"%> 
<body onload="javascript:doLoad()"> 
<div id="conteudo">
<%@ include file="/titulo_tela.jsp"%>

<form name="form" method="post">
	<input type="hidden" name="codAri" value="">	
	<input type="hidden" name="itemDoNivelClicado" value="">	
	<input type="hidden" name="pause" value="N">
		
	<div id="subconteudo">
		<!-- 
		Ver com o Beier se vai ter Popup Gráfico.
		
		<a href="javascript:abrirPopUpGrafico(0)" title="Gráfico de Evolução das Posições"><img src="../images/relAcomp/icon_grafico.png" border="0"></a>			
		-->
		<input type="button" name="btPause" value="Pausar [ || ]" onclick="javascript:pausar(form)">
		<input type="button" name="btProximo" value="Próximo [ >> ]" onclick="javascript:proximo(form)">
		<table border="0" cellpadding="0" cellspacing="0" width="100%">		
			<tr class="linha_titulo">						
				<td align="center">
					Respons&aacute;vel T&eacute;cnico
				</td>
				<td align="center">
					Administrador
				</td>
			</tr>
			
			<%
				String _w = "350px";
				String _h = "300px";
			%>
			<tr>						
				<td align="center" id="PR">
					<a href="#" onclick="javascript:abrirGrafico(form, 'O', 'R')">
					<img src="<%=_pathEcar%>/images/evPosicoesTemp.png" border="0" width="<%=_w%>" height="<%=_h%>">
					</a>
				</td>
				<td align="center" style="display:none" id="OR">
					<a href="#" onclick="javascript:abrirGrafico(form, 'P', 'R')">
					<img src="<%=_pathEcar%>/images/evPosicoesOrgaoTemp.png" border="0" width="<%=_w%>" height="<%=_h%>">
					</a>
				</td>
				<td align="center" id="PA">
					<a href="#" onclick="javascript:abrirGrafico(form, 'O', 'A')">
					<img src="<%=_pathEcar%>/images/evPosicoesTempAdm.png" border="0" width="<%=_w%>" height="<%=_h%>">
					</a>
				</td>
				<td align="center" style="display:none" id="OA">
					<a href="#" onclick="javascript:abrirGrafico(form, 'P', 'A')">
					<img src="<%=_pathEcar%>/images/evPosicoesOrgaoTemp.png" border="0" width="<%=_w%>" height="<%=_h%>">
					</a>
				</td>
			</tr>
			<tr>						
				<td align="center" colspan="2">
					<br>
					<a href="../geo/geoMapa.jsp">
					<img src="<%=_pathEcar%>/images/mapa_pr_mini.gif" border="0">
					</a>
				</td>
			</tr>
		</table>
	</div>	
	
	
</form>
	<%

//} catch (ECARException e) {
//	org.apache.log4j.Logger.getLogger(this.getClass()).error(e);
//	Mensagem.alert(_jspx_page_context, e.getMessageKey());
} catch (Exception e){
%>
	<%@ include file="/excecao.jsp"%>
<%
}
%>
	
</div>

</body>
<%@ include file="../include/ocultarAguarde.jsp"%>
<%@ include file="../include/estadoMenu.jsp"%>
<%@ include file="../include/mensagemAlert.jsp" %>
</html>