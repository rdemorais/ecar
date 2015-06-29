<%@ include file="/login/validaAcesso.jsp"%>
<%@ include file="/frm_global.jsp"%>


<html lang="pt-br">
<head>
<%@ include file="/titulo.jsp"%>
<%@ include file="/include/meta.jsp"%>
<link rel="stylesheet" href="<%=_pathEcar%>/css/style_<%=configuracaoCfg.getEstilo().getNome()%>.css" type="text/css">

<script language="javascript" src="../../js/datas.js"></script>

<script language="JavaScript">

function todos() {
	for(i=0 ; document.form.segmentos[i] != null ; i++)	document.form.segmentos[i].checked = true;
}

function validar() {

	if (document.form.palavra.value == "") {
		alert('<%=_msg.getMensagem("portal.pesquisa.palavra.obrigatorio")%>');
		document.form.palavra.focus();
		return false;
	}	
	
	var check=false;
	for(i=0 ; document.form.segmentos[i] != null ; i++){
		if (document.form.segmentos[i].checked){
			check=true;
			break;
		}
	}	
	
	if (!check) {
		alert('<%=_msg.getMensagem("portal.pesquisa.segmentos.obrigatorio")%>');		
		return false;
	}
			
	for(i=0 ; document.form.segmentos[i] != null ; i++){
		if (document.form.segmentos[i].checked){
			if (document.form.codSegmentos.value != "")	document.form.codSegmentos.value += ",";
			document.form.codSegmentos.value += document.form.segmentos[i].value;			
		}
	}	
	
	
	document.form.hidAcao.value = "PESQUISAR";

	return true;
}

</script>


</head>
<body>
<%@ include file="/cabecalho.jsp" %>
<%
try{
%>
<form name="form" method="post" action="ctrl_pesquisa.jsp" onsubmit="javascript:return validar();">

	<input type="hidden" name="hidAcao" value="">	
	<input type="hidden" name="codSegmentos" value="">	

	<table id="barra_localizacao" cellspacing="0">
		<tr>
			<td><a href="index.jsp">Capa</a> &gt; Pesquisa</td>
			<td align="right">&lt;&lt;<a href="index.jsp"> Voltar</a></td>
		</tr>
	</table>
	<h1 class="interno">PESQUISA</h1>
	<table cellspacing="1"  class="formulario" id="pesquisa">
		<tr>
		  <td >
		  	<table cellspacing="2">
				<tr>
					<td class="form_label">Palavra chave</td>
					<td class="form_campo"><input type="text" name="palavra" size="40"></td>
				</tr>			
				<tr>
					<td class="form_label">Data inicial</td>
					<td class="form_campo"><input type="text" name="dataInicial" size="12" maxlength="10" onkeyup="mascaraData(event, this);"></td>
				</tr>
				<tr>
					<td class="form_label">Data final</td>
					<td class="form_campo"><input type="text" name="dataFinal" size="12" maxlength="10" onkeyup="mascaraData(event, this);"></td>
				</tr>
			</table>
		  </td>
		</tr>
		<tr>
		  <td >
		  	<table cellspacing="0">
				<tr>
					<td class="form_label">Segmentos</td>
					 <td >
		  				<table cellspacing="0">
							<tr><td class="form_campo"><input type="checkbox" class="form_check_radio" name="segmentos" value="T" onchange="javascript:todos();">Todos</td></tr>
							<tr><td class="form_campo"><input type="checkbox" class="form_check_radio" name="segmentos" value="A">Agenda</td></tr>
							<tr><td class="form_campo"><input type="checkbox" class="form_check_radio" name="segmentos" value="<%=_msg.getMensagem("admPortal.materias")%>">Artigos</td></tr>
							<tr><td class="form_campo"><input type="checkbox" class="form_check_radio" name="segmentos" value="<%=_msg.getMensagem("admPortal.taxacoes")%>">Na M&iacute;dia</td></tr>							
						</table>
					</td>
				</tr>
			</table>
		  </td>
		</tr>
		<tr>
		  <td class="form_grupo" align="center"><input class="form_botao" type="submit" value="Procurar">
		  </td>
		</tr>
	</table>
	<hr>
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
