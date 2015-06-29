<jsp:directive.page import="ecar.bean.CargaFinalidadesPPA"/><%@ include file="/login/validaAcesso.jsp"%>
<%@ include file="/frm_global.jsp"%>

<%@ page import="ecar.exception.ECARException" %> 
<%@ page import="ecar.pojo.ItemEstruturaIett" %>


<%@page import="ecar.dao.CargaItemEstruturaIettDao"%><html lang="pt-br">
<head>  
<%@ include file="/include/meta.jsp"%>
<%@ include file="/titulo.jsp"%>

<link rel="stylesheet" href="<%=_pathEcar%>/css/style_<%=configuracaoCfg.getEstilo().getNome()%>.css" type="text/css">
<script language="javascript" src="<%=_pathEcar%>/js/menu_retratil.js" type="text/javascript"></script>

<script language="javascript">
function aoClicarAtualizar(form){
	document.form.hidAcao.value = "atualizar";
	document.form.submit();
}
</script>
 
</head>

<%@ include file="/cabecalho.jsp"%>
<%@ include file="/divmenu.jsp"%>

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
			CargaItemEstruturaIettDao cargaItemEstruturaIettDao = new CargaItemEstruturaIettDao(request);
			cargaItemEstruturaIettDao.efetuarCargaItens();
			
			msg = "Atualização efetuada com sucesso";
		} catch (ECARException e) {
			org.apache.log4j.Logger.getLogger(this.getClass()).error(e);
			msg = "Erro ao atualizar.<br>" + e.getMessageKey();
			Mensagem.alert(_jspx_page_context, e.getMessageKey());
		} catch (Exception e){
			msg = "Erro ao atualizar";
	%>
			<%@ include file="/excecao.jsp"%>
		<%
		}
	}
	%>

	<form name="form" method="post" action="cargaPacInter.jsp">
		<input type="hidden" name="hidAcao" value="">
		<table width="100%" border="0" cellpadding="0" cellspacing="0">	
			<tr class="linha_titulo">
				<td align="center">
					Importação PacInter
				</td>
			</tr>
			<tr class="texto">
				<td align="center">
					&nbsp;
				</td>
			</tr>
			<tr class="texto">
				<td align="left">
					*** Clique somente uma vez no Atualizar ***
				</td>
			</tr>
			<tr class="linha_titulo">
				<td align="center">
					<input type="button" value="Atualizar" onclick="javascript:aoClicarAtualizar(document.form)"> 
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
<%@ include file="/include/estadoMenu.jsp"%>
</html>