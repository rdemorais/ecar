<%
	ConfiguracaoCfg configuracao = (ConfiguracaoCfg)session.getAttribute("configuracao");
%>
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
<%@ page import="ecar.dao.ItemEstruturaDao" %>
<%@ page import="ecar.dao.ConfiguracaoDao" %>
<%@ page import="ecar.pojo.ItemEstruturaIett" %>
<%@ page import="ecar.pojo.ItemEstrutLocalIettl" %>
<%@ page import="java.util.Iterator" %>

<%@page import="ecar.pojo.ConfiguracaoCfg"%><html lang="pt-br">
	<head>
		<%@ include file="../../include/meta.jsp"%>
		<link rel="stylesheet" href="<%=_pathEcar%>/css/style_<%=configuracao.getEstilo().getNome()%>.css" type="text/css">
		<script language="javascript" src="../../js/focoInicial.js"></script>
		<script language="javascript" src="../../js/frmPesquisa.js"></script>
		<script language="javascript" src="../../js/validacoes.js"></script>
		<script>
function aoClicarBtn1(form){
	form.submit();
}
function pesquisar(form){
	document.form.action="popUpPesquisaLocalRealizadoFisico.jsp?hidAcao=pesquisar";
	document.form.submit();
}
function redimensionaTela(altura, largura){
	window.outerHeight = altura;
	window.outerWidth = largura;
}
function adicionar(form, codLit, identificacaoLit){
	var selecionado = false;

	if (verificaRadios(form, "hidOpcoes") > 1) {
		for (i = 0; i < form.hidOpcoes.length; i++) {
			if (form.hidOpcoes[i].checked == true) {
				dados = form.hidOpcoes[i].value.split(":");
				escolhido = form.hidOpcoes[i];
				window.opener.setarLocal(dados[0], dados[1]);
				selecionado = true;
				break;
			}
		}
	} else {
		if (form.hidOpcoes.checked == true) {
			dados = form.hidOpcoes.value.split(":");
			escolhido = form.hidOpcoes;			
			window.opener.setarLocal(dados[0], dados[1]);
			selecionado = true;
		}
	}
	if (selecionado) {
		window.close();
	} else {
		alert('Selecione um local');
	}
}

</script>
	</head>

	<body class="corpo_popup">

	<%//Aproveitar (se possivel)
	try {
		LocalGrupoDao localGrupoDao = new LocalGrupoDao(request);
		LocalItemDao localItemDao = new LocalItemDao(request);
		ItemEstruturaDao itemEstruturaDao = new ItemEstruturaDao(request);
		
		ItemEstruturaIett item = (ItemEstruturaIett)itemEstruturaDao.buscar(ItemEstruturaIett.class, Long.valueOf(Pagina.getParamStr(request, "iett")));
		
		List locaisItem = new ArrayList(item.getItemEstrutLocalIettls());

		/* lista que contém o resultado da pesquisa */
		List lista;
		
		/* controles de página */
		int hidNumPagina = Pagina.getParamInt(request, "hidNumPagina");
		int hidTotPagina = Pagina.getParamInt(request, "hidTotPagina");
		
		/* define a quantidade de itens exibidos por pagina */
		final int ITENS_PAGINA = new ConfiguracaoDao(null).getConfiguracao().getNumRegistros().intValue();
		
		String hidAcao = Pagina.getParamStr(request, "hidAcao");
		
		LocalItemLit local = new LocalItemLit();
		if ("pesquisar".equalsIgnoreCase(hidAcao)) {
			session.removeAttribute("lista");
		
			local.setIdentificacaoLit(Pagina.getParamStr(request, "identificacaoLit"));
			if(!"".equals(Pagina.getParamStr(request, "localGrupoItem"))) {
				local.setLocalGrupoLgp( (LocalGrupoLgp) localGrupoDao.buscar(LocalGrupoLgp.class, Long.valueOf(Pagina.getParamStr(request, "localGrupoItem"))));
			}
			local.setCodIbgeLit(Pagina.getParamStr(request, "codIbgeLit"));
			local.setCodPlanejamentoLit(Pagina.getParamStr(request, "codPlanejamentoLit"));

			lista = localItemDao.getLocalItemByLocalItem(local);
		
			session.setAttribute("lista", lista);
		
			hidNumPagina = 0;
			hidTotPagina = 0;
		} else if ("navegar".equalsIgnoreCase(hidAcao)) {
			lista = (List) session.getAttribute("lista");
		} else {
			lista = new ArrayList();
		}
	%>
		
		<form name="form" method="post" action="popUpPesquisaLocalRealizadoFisico.jsp?hidAcao=navegar" onsubmit="pesquisar(document.form);">

			<input type="hidden" name="hidAcao">
			<input type="hidden" name="iett" value="<%=Pagina.getParamStr(request, "iett")%>">
			
			<h1>
			Pesquisa de Locais
			<h1>

			<% // Aproveitar se possivel
			if(locaisItem == null || locaisItem.isEmpty()) {
			%>
				<table class="form">
					<tr>
						<td class="texto">
							<%=_msg.getMensagem("acompanhamento.realizadoFisico.quantidadePorLocal.faltaAbrangenciaItem")%>
						</td>
					</tr>
					<tr>
						<td class="texto">
							<b>Item:</b> <%=item.getNomeIett()%>
						</td>
					</tr>
				</table>
			<%
			} else {
				LocalGrupoLgp localGrupoItem = ((ItemEstrutLocalIettl)locaisItem.get(0)).getLocalItemLit().getLocalGrupoLgp();
			%>
				<input type="hidden" name="localGrupoItem" value="<%=localGrupoItem.getCodLgp()%>">
				
				<table width="100%" border="0" cellpadding="0" cellspacing="0">
					<tr>
						<td class="label">
							Item
						</td>
						<td class="texto" align="left">
							<%=item.getNomeIett()%>
						</td>
					</tr>
					<tr>
						<td class="label">
							Abrangência
						</td>
						<td class="texto" align="left">
							<%=localGrupoItem.getIdentificacaoLgp()%>
							<%// Aproveitar
							List listGruposFilhos = new ArrayList(localGrupoItem.getLocalGrupoHierarquiaLgphsByCodLgpPai());
							
							if(listGruposFilhos != null && !listGruposFilhos.isEmpty()) {
								out.print("&nbsp;(contêm:&nbsp;");
								Iterator itGruposFilhos = listGruposFilhos.iterator();
								int i = 0;
								while(itGruposFilhos.hasNext()) {
									LocalGrupoLgp lgp = (LocalGrupoLgp)itGruposFilhos.next();
									out.print(lgp.getIdentificacaoLgp());
									i++;
									if(i < listGruposFilhos.size()) {
										out.print(", ");
									}
								}
								out.print(")");
							}
							%>
						</td>
					</tr>
				</table>
				<table class="form">
					<tr>
						<td class="label">
							Descri&ccedil;&atilde;o
						</td>
						<td>
							<input type="text" name="identificacaoLit" size="45" maxlength="40" value="<%=Pagina.trocaNull(local.getIdentificacaoLit())%>" <%=_disabled%>>
						</td>
					</tr>
					<tr>
						<td class="label">
							C&oacute;digo IBGE
						</td>
						<td>
							<input type="text" name="codIbgeLit" size="8" maxlength="6" value="<%=Pagina.trocaNull(local.getCodIbgeLit())%>" <%=_disabled%>>
						</td>
					</tr>
					<tr>
						<td class="label">
							C&oacute;digo Planejamento
						</td>
						<td>
							<input type="text" name="codPlanejamentoLit" size="8" maxlength="6" value="<%=Pagina.trocaNull(local.getCodPlanejamentoLit())%>" <%=_disabled%>>
								</td>
							</tr>
							<tr>
								<td> &nbsp;
								</td>
								<td><!-- Aproveitar -->
									<input type="button" name="btnOk" class="botao" value="Pesquisar" onclick="pesquisar(document.form);">
								</td>
							</tr>
							
				</table>
	
				<h2>
					Resultado:
				</h2>
		<%
		}
		%>

		<%//Aproveitar
		int tamLista = lista.size();
		int limite = Math.min(tamLista, hidNumPagina * ITENS_PAGINA + ITENS_PAGINA);
		hidTotPagina = tamLista / ITENS_PAGINA + (tamLista % ITENS_PAGINA == 0 ? 0 : 1);
		if (limite > 0) { 
		%>
			<util:barrapesquisa atual="<%=hidNumPagina+1%>" total="<%=hidTotPagina%>" />
			<input type="hidden" name="hidNumPagina" value="<%=hidNumPagina%>">
			<input type="hidden" name="hidTotPagina" value="<%=hidTotPagina%>">
		<%
		}
		
		if(!lista.isEmpty()) {
		%>
			<table width="100%">
				<tr class="linha_subtitulo">
					<td width="1%">
						&nbsp;
					</td>
					<td width="79%">
						Local
					</td>
					<td width="10%">
						IBGE
					</td>
					<td width="10%">
						Planejamento
					</td>
				</tr>
			<%
			for (int i = hidNumPagina*ITENS_PAGINA; i < limite; i++) {
			%>
				<SCRIPT language="javascript">redimensionaTela(590,550);</SCRIPT>
				<%
				LocalItemLit lit = (LocalItemLit) lista.get(i);
				String valor = lit.getCodLit() + ":" + lit.getIdentificacaoLit();
			%>
				<tr class="linha_subtitulo2">
					<td>
						<input type="radio" class="form_check_radio" name="hidOpcoes" id="hidOpcoes" value="<%=valor%>">
					</td>
					<td class="texto">
						<%=lit.getIdentificacaoLit()%>&nbsp;&nbsp;(<%=lit.getLocalGrupoLgp().getIdentificacaoLgp()%>)
					</td>
					<td class="texto">
						<%=Pagina.trocaNull(lit.getCodIbgeLit())%>
					</td>
					<td class="texto">
						<%=Pagina.trocaNull(lit.getCodPlanejamentoLit())%>
					</td>
				</tr>
			<%
			}
			%>
			</table>
		<%
		}
		
		if (limite > 0) { 
		%>
			<util:barrapesquisa atual="<%=hidNumPagina+1%>" total="<%=hidTotPagina%>" />
			<center>
				<input type="button" name="btnAdicionar" class="botao" value="Adicionar" onclick="adicionar(document.form);">
				<input type="button" name="btnCancelar" class="botao" value="Cancelar" onclick="aoClicarCancelar(document.form);">
			</center>
		<%
		} else {
			if("pesquisar".equalsIgnoreCase(hidAcao)){
		%>
			<table>
				<tr>
					<td class="texto">
						<b>
							Nenhum registro foi encontrado para os critérios de pesquisa especificados.
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
