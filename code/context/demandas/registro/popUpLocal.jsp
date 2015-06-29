<%@ taglib uri="/WEB-INF/ecar-util.tld" prefix="util"%>
<%@ taglib uri="/WEB-INF/ecar-combo.tld" prefix="combo" %>
<%@ include file="../../frm_global.jsp"%>
<%@ page import="ecar.dao.LocalItemDao"%>
<%@ page import="ecar.pojo.LocalItemLit"%>
<%@ page import="ecar.pojo.LocalGrupoLgp"%>
<%@ page import="ecar.dao.LocalGrupoDao"%>
<%@ page import="java.util.List"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="ecar.pojo.EntidadeEnt"%>
<%@ page import="java.util.Set"%>
<%@ page import="java.util.Iterator"%>
<html lang="pt-br">
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
	document.form.action="popUpLocal.jsp?hidAcao=pesquisar";
	document.form.submit();
}
function redimensionaTela(altura, largura){
	window.outerHeight = altura;
	window.outerWidth = largura;
}
function adicionar(form, codLit, identificacaoLit){
	var selecionado = false;
	var inserido = false;

	if (verificaRadios(form, "hidOpcoes") > 1) {
		for (i = 0; i < form.hidOpcoes.length; i++) {
			if (form.hidOpcoes[i].checked == true) {
				dados = form.hidOpcoes[i].value.split(":");
				escolhido = form.hidOpcoes[i];
				inserido = window.opener.adicionaLocal(dados[0], dados[1]);
				selecionado = true;
				break;
			}
		}
	} else {
		if (form.hidOpcoes.checked == true) {
			dados = form.hidOpcoes.value.split(":");
			escolhido = form.hidOpcoes;			
			inserido = window.opener.adicionaLocal(dados[0], dados[1]);
			selecionado = true;
		}
	}
	if (selecionado) {
		if (inserido) {
			alert ('Local adicionado.');
		}
		escolhido.checked = false;
	} else {
		alert('Selecione uma opção!');
	}
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
final int ITENS_PAGINA = configuracaoCfg.getNumRegistros().intValue();//_msg.getQtdeItensPaginaPesquisa("popup.pesquisa.itensPagina");

LocalGrupoDao localGrupoDao = new LocalGrupoDao(request);
String hidAcao = Pagina.getParamStr(request, "hidAcao");

if ("pesquisar".equalsIgnoreCase(hidAcao) ){//|| "".equalsIgnoreCase(hidAcao.trim())) { //Esse final foi comentado para apenas exibir a listagem depois da consulta.
		session.removeAttribute("lista");
		LocalItemLit local = new LocalItemLit();
		local.setIdentificacaoLit(Pagina.getParam(request, "identificacaoLit"));
		if(Pagina.getParam(request, "localGrupoLgp") != null)
			local.setLocalGrupoLgp( (LocalGrupoLgp) localGrupoDao.buscar(LocalGrupoLgp.class, Long.valueOf(Pagina.getParam(request, "localGrupoLgp"))));
		local.setCodIbgeLit(Pagina.getParam(request, "codIbgeLit"));
		local.setCodPlanejamentoLit(Pagina.getParam(request, "codPlanejamentoLit"));
		lista = new LocalItemDao(request).pesquisar(local,  new String[] {"identificacaoLit","asc"});
		session.setAttribute("lista", lista);
		hidNumPagina = 0;
		hidTotPagina = 0;
} 
else if ("navegar".equalsIgnoreCase(hidAcao))
		lista = (List) session.getAttribute("lista");
else
		lista = new ArrayList();
%>

		<form name="form" method="post" action="popUpLocal.jsp?hidAcao=navegar" onsubmit="pesquisar(document.form);">

			<input type="hidden" name="hidAcao">

			<h1>
				Pesquisa de Locais
				<h1>

					<table class="form">

						<tr>
							<td class="label">
								Grupo
							</td>
							<td>
								<%
									LocalItemLit localItem = new LocalItemLit();
									LocalGrupoLgp localgrupoLgp = new LocalGrupoLgp();
								%>
								<combo:ComboTag 
									nome="localGrupoLgp" 
									objeto="ecar.pojo.LocalGrupoLgp" 
									label="identificacaoLgp" 
									value="codLgp" 
									order="identificacaoLgp" 
									filters="indAtivoLgp=S" 
									scripts="<%=_disabled%>"
									selected="<%= Pagina.getParamStr(request,"localGrupoLgp") %>" 
								/>
							</td>
						<tr>
							<td class="label">
								<%=_obrigatorio%>
								Descri&ccedil;&atilde;o
							</td>
							<td>
								<input type="text" name="identificacaoLit" size="45" maxlength="40" value="<%=Pagina.getParamStr(request,"identificacaoLit" )%>" <%=_disabled%>>
							</td>
						</tr>
						<tr>
							<td class="label">
								C&oacute;digo IBGE
							</td>
							<td>
								<input type="text" name="codIbgeLit" size="8" maxlength="6" value="<%= Pagina.getParamStr(request,"codIbgeLit") %>" <%=_disabled%>>
							</td>
						</tr>
						<tr>
							<td class="label">
								C&oacute;digo Planejamento
							</td>
							<td>
								<input type="text" name="codPlanejamentoLit" size="8" maxlength="6" value="<%= Pagina.getParamStr(request,"codPlanejamentoLit") %>" <%=_disabled%>>
							</td>
						</tr>
						<tr>
							<td> &nbsp;
							</td>
							<td>
								<input type="button" name="btnOk" class="botao" value="Pesquisar" onclick="pesquisar(document.form);">
							</td>
						</tr>
					</table>
					
					<%-- Para exibir Resultado só se a lista não estiver vazia --%>
					<%= (lista.isEmpty() ?"<br>" : "<h2> Resultado: </h2> ") %>
					
					<%
	int tamLista = lista.size();
	int limite = Math.min(tamLista, hidNumPagina * ITENS_PAGINA + ITENS_PAGINA);
	hidTotPagina = tamLista / ITENS_PAGINA + (tamLista % ITENS_PAGINA == 0 ? 0 : 1);
	if (limite > 0) { 
	%>
					<util:barrapesquisa atual="<%=hidNumPagina+1%>" total="<%=hidTotPagina%>" />
					<input type="hidden" name="hidNumPagina" value="<%=hidNumPagina%>">
					<input type="hidden" name="hidTotPagina" value="<%=hidTotPagina%>">
					<table cellpadding="3">
						<%
	}	
	for (int i = hidNumPagina*ITENS_PAGINA; i < limite; i++) {
	%>
						<SCRIPT language="javascript">redimensionaTela(530,500);</SCRIPT>
						<%
		LocalItemLit local = (LocalItemLit) lista.get(i);
		String valor = local.getCodLit() + ":" + local.getIdentificacaoLit();
		
		// verifica se o local tem um pai
		if(local.getLocalItemHierarquiaLithsByCodLit() != null && !local.getLocalItemHierarquiaLithsByCodLit().isEmpty()) {
		
			// se tiver imprime na tela o nome do registro com o nome do pai -> diferenciar cidades que sejam de regiões diferentes -> aplica-se para todos os registros
			Iterator itListaLocalPai = local.getLocalItemHierarquiaLithsByCodLit().iterator();
			LocalItemLit pai = null;
			
			if(itListaLocalPai.hasNext()) {
				pai = (LocalItemLit) itListaLocalPai.next();
				valor += " - " + pai.getIdentificacaoLit();
				out.println("<tr><td><input type=\"radio\" class=\"form_check_radio\" name=\"hidOpcoes\" id=\"hidOpcoes\" value=\"" + valor + "\"></td><td class=\"texto\">" +  local.getIdentificacaoLit() + " - " + pai.getIdentificacaoLit() + "</td>");	
			} else 
				out.println("<tr><td><input type=\"radio\" class=\"form_check_radio\" name=\"hidOpcoes\" id=\"hidOpcoes\" value=\"" + valor + "\"></td><td class=\"texto\">" +  local.getIdentificacaoLit() + "</td>");
		} else {
			
			out.println("<tr><td><input type=\"radio\" class=\"form_check_radio\" name=\"hidOpcoes\" id=\"hidOpcoes\" value=\"" + valor + "\"></td><td class=\"texto\">" +  local.getIdentificacaoLit() + "</td>");			
		}
		
	}
		if (limite > 0) { 
%>
					</table>
					<util:barrapesquisa atual="<%=hidNumPagina+1%>" total="<%=hidTotPagina%>" />
					<center>
						<input type="button" name="btnAdicionar" class="botao" value="Adicionar" onclick="adicionar(document.form);">
						<input type="button" name="btnCancelar" class="botao" value="Cancelar" onclick="aoClicarCancelar(document.form);">
					</center>
					<%
		}else{
			if("pesquisar".equalsIgnoreCase(hidAcao)){
%>
					<table>
						<tr>
							<td class="texto">
								<b>
									Nenhum registro foi encontrado para os critérios de pesquisa especificados
								</b>
							</td>
						</tr>
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
