<%@ taglib uri="/WEB-INF/ecar-util.tld" prefix="util" %>
<%@ include file="../../frm_global.jsp"%>
<%@ page import="ecar.dao.UsuarioDao" %>
<%@ page import="ecar.pojo.UsuarioUsu" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %>

<%@page import="ecar.dao.ConfiguracaoDao"%><html lang="pt-br">
<head>
<%@ include file="../../include/meta.jsp"%>
<%@ include file="/titulo.jsp"%>
<link rel="stylesheet" href="<%=_pathEcar%>/css/style_<%=configuracaoCfg.getEstilo().getNome()%>.css" type="text/css">
<script language="javascript" src="../../js/focoInicial.js"></script>
<script language="javascript" src="../../js/frmPesquisa.js"></script>
<script language="javascript" src="../../js/validacoes.js"></script>
<script>
function aoClicarBtn1(form){
	form.submit();
}
function pesquisar(form){
	document.form.action="popUpPesquisarUsuarioSentinela.jsp?hidAcao=pesquisar";
	document.form.submit();
}
function redimensionaTela(altura, largura){
	window.outerHeight = altura;
	window.outerWidth = largura;
}
function adicionar(form, codUsu, nomeUsu, login, cpfCnpjUsu, email){
	var selecionado = false;

	if (verificaRadios(form, "hidOpcoes") > 1) {
		for (i = 0; i < form.hidOpcoes.length; i++) {
			if (form.hidOpcoes[i].checked == true) {
				dados = form.hidOpcoes[i].value.split(":");
				window.opener.setarValoresSentinela(dados[0], dados[1], dados[2], dados[3], dados[4]);
				selecionado = true;
				break;
			}
		}
	} else {
		if (form.hidOpcoes.checked == true) {
			dados = form.hidOpcoes.value.split(":");
			window.opener.setarValoresSentinela(dados[0], dados[1], dados[2], dados[3], dados[4]);
			selecionado = true;
		}
	}
	if (selecionado)
		window.close();
	else
		alert('Selecione uma opção!');
}

</script>
</head>

<body class="corpo_popup">

<%
try{
/* lista que contém o resultado da pesquisa */
List lista;

/* controles de página */
int hidNumPagina = Pagina.getParamInt(request, "hidNumPagina");
int hidTotPagina = Pagina.getParamInt(request, "hidTotPagina");
/*
* Acao = nada - entrou na pagina; "pesquisar" - deve pesquisar; "navegar" - deve navegar
*/

/* define a quantidade de itens exibidos por pagina */
final int ITENS_PAGINA = new ConfiguracaoDao(request).getConfiguracao().getNumRegistros().intValue();//_msg.getQtdeItensPaginaPesquisa("popup.pesquisa.itensPagina");

String hidAcao = Pagina.getParamStr(request, "hidAcao");
if ("pesquisar".equalsIgnoreCase(hidAcao)) {
		session.removeAttribute("lista");
		lista = new UsuarioDao(request).pesquisarUsuarioSentinela(request.getParameter("argumento"), request.getParameter("parametro"));
		session.setAttribute("lista", lista);
		hidNumPagina = 0;
		hidTotPagina = 0;
} 
else if ("navegar".equalsIgnoreCase(hidAcao))
		lista = (List) session.getAttribute("lista");
else
		lista = new ArrayList();
%>

<form name="form" method="post" action="popUpPesquisarUsuarioSentinela.jsp?hidAcao=navegar" onsubmit="pesquisar(document.form);">
	
	<input type="hidden" name="hidAcao">

	<h1>Pesquisa de Usuários do Sentinela<h1>

	<table class="form">

		<tr>
			<td class="label">Argumento de Pesquisa:</td>
			<td>
				<input type="text" name="argumento" size="30" maxlength="20" value=""> 	
				<input type="button" name="btnOk" class="botao" value="Ok" onclick="pesquisar(document.form);">
			</td>
		</tr>
		<tr>
			<td class="label">Pesquisar em:</td>
			<td class="texto">
				<input type="radio" class="form_check_radio" name="parametro" value="N" checked>Nome&nbsp;&nbsp;
				<input type="radio" class="form_check_radio" name="parametro" value="L">Login&nbsp;&nbsp;
			</td>	
		</tr>
	</table>
	
	<h2>Resultado:</h2>
	
	<%
	int tamLista = lista.size();
	int limite = Math.min(tamLista, hidNumPagina * ITENS_PAGINA + ITENS_PAGINA);
	hidTotPagina = tamLista / ITENS_PAGINA + (tamLista % ITENS_PAGINA == 0 ? 0 : 1);
	if (limite > 0) { 
	%>	
		<util:barrapesquisa atual="<%=hidNumPagina+1%>" total="<%=hidTotPagina%>"/>
		<input type="hidden" name="hidNumPagina" value="<%=hidNumPagina%>">	
		<input type="hidden" name="hidTotPagina" value="<%=hidTotPagina%>">	
		<table cellpadding="3">
	<%
	}	
	for (int i = hidNumPagina*ITENS_PAGINA; i < limite; i++) {
	%>
		<SCRIPT language="javascript">redimensionaTela(500,500);</SCRIPT>
	<%
		UsuarioUsu usuario = (UsuarioUsu) lista.get(i);
		String valor = usuario.getCodUsu() + ":" + usuario.getNomeUsuSent() + ":" + usuario.getIdUsuarioUsu() + ":" + usuario.getCnpjCpfUsu() + ":" + usuario.getEmail1UsuSent();
		out.println("<tr><td><input type=\"radio\" class=\"form_check_radio\" name=\"hidOpcoes\" id=\"hidOpcoes\" value=\"" + valor + "\"></td><td class=\"texto\">" +  usuario.getNomeUsuSent() + "</td>");						
	}
		if (limite > 0) { 
%>
			</table>
			<util:barrapesquisa atual="<%=hidNumPagina+1%>" total="<%=hidTotPagina%>"/>
			<center>
			<input type="button" name="btnAdicionar" class="botao" value="Adicionar" onclick="adicionar(document.form);">
			<input type="button" name="btnCancelar" class="botao" value="Cancelar" onclick="aoClicarCancelar(document.form);">
			</center>
<%
		}else{
			if("pesquisar".equalsIgnoreCase(hidAcao)){
%>
			<table>
				<tr><td class="texto">
					<b>Nenhum registro foi encontrado para os critérios de pesquisa especificados</b>
				</td></tr>
			</table>
<%
			}
		}		
	%>	
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