<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="/WEB-INF/tlds/c.tld" prefix="c"%>
<%@ taglib uri="/WEB-INF/ecar-util.tld" prefix="util" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>${sessionScope.configuracao.tituloSistema}</title>
<script src="${pageContext.request.contextPath}/js/jquery.1.7.1.js"></script>
<script src="${pageContext.request.contextPath}/js/jquery.ui.core.js"></script>
<script src="${pageContext.request.contextPath}/js/jquery.ui.widget.js"></script>
<script src="${pageContext.request.contextPath}/js/jquery.ui.position.js"></script>
<script src="${pageContext.request.contextPath}/js/jquery.ui.autocomplete.js"></script>

<style type="text/css">
	body{
		background-color: #EAEEF4;
		font-size: 10px;
		font-family: Verdana,Arial,Helvetica,sans-serif;
	}
	.botao {
    	background-color: #FFFFFF;
   	 	border: 1px solid #596D9B;
    	color: #596D9B;
	    font-family: Verdana,Arial,Helvetica,sans-serif;
	    font-size: 10px;
	    margin: 0;
	}
	label{
		color: #1C2265;
	}
	.ui-menu .ui-menu-item a {
		background-color: white;
    	display: block;
    	line-height: 1.5;
    	padding: 0.2em 0.4em;
    	text-decoration: none;
	}
	.ui-menu {
    	list-style: none outside none;
	}
	ul.ui-autocomplete{
		padding-left: 0;
	}
</style>
<script type="text/javascript">
function autoCompleteEtiquetas(){
	$.ajax({
		url: "autoCompleteEtiqueta?nomeEtiqueta="+$("#nomeEtiqueta").val(),
		dataType: "xml",
		success: function( xmlResponse ) {
			var data = $( "etiqueta", xmlResponse ).map(function() {
				return {
					value: $( "nome", this ).text(), id: $( "codigo", this ).text()
				};
			}).get();
			$( "#nomeEtiqueta" ).autocomplete({
				source: data,
				minLength: 2,
				select: function( event, ui ) {
					inserirCodigoEtiquetaSubstituta(ui.item.id);
				}
			});
		}
	});
}
function inserirCodigoEtiquetaSubstituta(codigo){
	document.getElementById("codigoEtiquetaSubst").value = codigo;
}
function inativarEtiqueta(){
	document.getElementById("formEtiqueta").action = "administracaoEtiqueta";
	document.getElementById("formEtiqueta").acao.value = "inativarEtiqueta";
	document.getElementById("formEtiqueta").submit();
}
function substituirEtiqueta(){
	document.getElementById("formEtiqueta").action = "administracaoEtiqueta";
	document.getElementById("formEtiqueta").acao.value = "substituirEtiqueta";
	document.getElementById("formEtiqueta").submit();
}
function fecharJanela(){
	window.close();
}

</script>
</head>
<body onunload="window.opener.location.href='administracaoEtiqueta';">
	<div id="mensagem" style="height: 40px; line-height: 40px; text-align: center; font-size: 12px;">
		${mensagem}
	</div>
	<form id="formEtiqueta" name="formEtiqueta">
		<input type="hidden" name="codigoEtiqueta" value="${etiqueta.codigo}"/>
		<input type="hidden" name="acao"/>
		<input type="hidden" name="codigoEtiquetaSubst" id="codigoEtiquetaSubst"/>
		<table style="width: 100%;">
			<tr>
				<td align="right">
					<label>ETIQUETA QUE SERÁ EXCLUÍDA:</label>
				</td>
			</tr>
			<tr>
				<td align="right">
					<input type="text" value="${etiqueta.nome}" readonly="readonly" size="70"/>
				</td>
			</tr>
			<tr>
				<td align="right">
					<label>SUBSTITUIR PELA ETIQUETA:</label>
				</td>
			</tr>
			<tr>
				<td align="right">
					<input type="text" size="70" id="nomeEtiqueta" name="nomeEtiqueta" onkeyup="autoCompleteEtiquetas();"/>
				</td>
			</tr>
			<tr>
				<td align="right">
					<table>
						<tr>
							<td>
								<input type="button" class="botao" value="Redirecionar" onclick="substituirEtiqueta();"/>
							</td>
							<td>
								<input type="button" class="botao" value="Inativar" onclick="inativarEtiqueta();"/>
							</td>
							<td>
								<input type="button" class="botao" value="Fechar" onclick="fecharJanela();"/>
							</td>
						</tr>
					</table>
				</td>
			</tr>
		</table>
	</form>
	
</body>
</html>