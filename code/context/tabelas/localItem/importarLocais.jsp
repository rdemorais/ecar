<!-- http://localhost:8080/ecar/tabelas/localItem/importarLocais.jsp -->

<%@ page import="ecar.servlet.importaLocal.ImportarLocalIBGE" %>

<%@ include file="/frm_global.jsp"%>

<html lang="pt-br">
<head>  
<%@ include file="/include/meta.jsp"%>
<%@ include file="/titulo.jsp"%>

<script language="javascript">
function aoClicarAtualizar(form){
	document.form.hidAcao.value = "atualizar";
	document.form.submit();
}
</script>
 
</head>

<%
String acao = Pagina.getParamStr(request, "hidAcao");
String msg = "";
%>

<body> 
<div id="conteudo">
<%
	if("atualizar".equals(acao)) {
%>
		<div id="aguarde" class="fontAguardeAtualizaBD">
			&nbsp;Aguarde...
		</div>
<%
		try {
			ImportarLocalIBGE importa = new ImportarLocalIBGE();
			msg = importa.importaDadosIBGE("", request, response);
		} catch (ECARException e) {
			org.apache.log4j.Logger.getLogger(this.getClass()).error(e);
			msg = "Erro ao importar";
			Mensagem.alert(_jspx_page_context, e.getMessageKey());
		} catch (Exception e){
			msg = "Erro ao importar";
%>
			<%@ include file="/excecao.jsp"%>
<%
		}
	}
%>

	<form name="form" method="post" action="importarLocais.jsp">
		<input type="hidden" name="hidAcao" value="">
		<table width="100%" border="0" cellpadding="0" cellspacing="0">	
			<tr class="linha_titulo">
				<td align="center">
					Importação de Locais da fonte do IBGE
				</td>
			</tr>
			<tr class="texto">
				<td align="center">
					&nbsp;
				</td>
			</tr>
			<tr class="texto">
				<td align="left">
					*** Clique somente uma vez no botão ***
				</td>
			</tr>
			<tr class="linha_titulo">
				<td align="center">
					<input type="button" value="Importar" onclick="javascript:aoClicarAtualizar(document.form)"> 
				</td>
			</tr>
			<tr class="linha_titulo">
				<td align="center">
					<%=msg%>
				</td>
			</tr>
		</table>
	</form>

	<table width="100%" border="0" cellpadding="0" cellspacing="0">
		<tr><td>&nbsp;</td></tr>
	</table>

<%
if("atualizar".equals(acao)) {
%>
	<script>
		document.getElementById("aguarde").style.display="none";
	</script>
<%
}
%>

</div>

</body>

</html>