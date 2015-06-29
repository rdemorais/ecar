<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@ include file="../../login/validaAcesso.jsp"%>
<%@ include file="../../frm_global.jsp"%>

<%@ page import="ecar.pojo.ItemEstruturaIett" %>
<%@ page import="ecar.dao.ItemEstruturaDao" %>

<%@ page import="ecar.pojo.ItemFuncaoLink" %>

<%@ page import="java.util.Collection" %>
<%@ page import="java.util.Collections" %>
<%@ page import="java.util.Comparator" %>
<%@ page import="java.util.HashMap" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.Iterator" %>


<%@page import="ecar.pojo.UsuarioUsu"%>
<%@page import="ecar.pojo.ItemEstrutMarcadorIettm"%>
<%@page import="ecar.dao.ItemEstrutMarcadorDao"%><html lang="pt-br">
<head>
<%@ include file="../../include/meta.jsp"%>
<%@ include file="/titulo.jsp"%>
<link rel="stylesheet" href="<%=_pathEcar%>/css/style_<%=configuracaoCfg.getEstilo().getNome()%>.css" type="text/css">
<script language="javascript" src="../../js/menu_retratil.js"></script>
<script language="javascript" src="../../js/focoInicial.js"></script>
<script language="javascript" src="../../js/datas.js"></script>
<script language="javascript" src="../../js/validacoes.js"></script>
<script language="javascript" src="<%=_pathEcar%>/js/destaqueLinha.js"></script>
<script language="javascript" src="<%=_pathEcar%>/js/relacaoItensJs.js" type="text/javascript"></script>

<script language="javascript">
function aoClicarVoltar(form){
	form.action = "frm_pes.jsp";
	form.submit();
}
function abrirPopUpListaAnotacao(codItem){
	abreJanela("../../acompanhamento/popUp/popUpListaAnotacao.jsp?codIett=" + codItem + "&origem=p", 500, 300, "Listar");
}
</script>

</head>
<%
request.setAttribute("exibirAguarde", "true");
%>
<%@ include file="../../cabecalho.jsp" %>
<%@ include file="../../divmenu.jsp"%> 

<body>
<div id="conteudo">

<H1>Resultado da Pesquisa</H1>

<form name="form" method="post">

<%
try{
	ItemEstruturaDao itemDao = new ItemEstruturaDao(request);

	HashMap itensMap = itemDao.pesquisarNaEstrutura(request, seguranca.getCodUsu(), application);
	//session.setAttribute("itensMap", itensMap);
	
	//HashMap itensMap = (HashMap) session.getAttribute("itensMap");
	//session.removeAttribute("itensMap");
%>

<div id="subconteudo">

<table width="100%" border="0" cellpadding="0" cellspacing="0">	
	<tr><td class="espacador" colspan=2><img src="../../images/pixel.gif"></td></tr>

<%
	Iterator it = itensMap.keySet().iterator();
	if(it.hasNext()){
		
		List listaItem = new ArrayList();
		
		/* Extrai os itens do map, necessário para montar árvore */
		while(it.hasNext()){
			ItemEstruturaIett item = (ItemEstruturaIett) it.next();
			//item.getItemEstruturaIetts().size();
			listaItem.add(item);
		}
		
		List arvore = itemDao.getArvoreItensPesquisaEstrutura(listaItem, null);
		Iterator itArvore = arvore.iterator();
		
		while(itArvore.hasNext()){
			ItemEstruturaIett item = (ItemEstruturaIett) itArvore.next();
			
			String cor = "cor_nao";
			if(listaItem.contains(item)){
				cor = "cor_sim";
			}
%>
			<tr class="<%=cor%>" onmouseover="javascript:destacaLinha(this,'over','')" onmouseout="javascript: destacaLinha(this,'out','<%=cor%>')" onClick="javascript: destacaLinha(this,'click','<%=cor%>')">
				<td valign="top">
					<table>
						<tr>
<%
		/* Gera tabulação de níveis */
		for(int i = 1; i < item.getNivelIett().intValue(); i++){
			%><td width=1><img src="../../images/pixel.gif" width="22" height="9"></td><%
		}
%>
						<td width=1><img src="../../images/icon_bullet_seta.png"></td>
						<td><%=item.getNomeIett()%></td>
						</tr>
					</table>
				</td>
				<td>
<%
				if(listaItem.contains(item)){
					List lista = new ArrayList();
					lista.addAll((Collection) itensMap.get(item));
					
					Collections.sort(lista,
						new Comparator() {
			        		public int compare(Object o1, Object o2) {
			        		    return ( (ItemFuncaoLink)o1 ).getNome().compareToIgnoreCase( ( (ItemFuncaoLink)o2 ).getNome() );
			        		}
			    		} );
					
					Iterator itLista = lista.iterator();
					
					UsuarioUsu usuario = ((ecar.login.SegurancaECAR)session.getAttribute("seguranca")).getUsuario();
					ItemEstrutMarcadorDao marcadorDao = new ItemEstrutMarcadorDao(request);
					
					while(itLista.hasNext()){
						ItemFuncaoLink funcaoLink = (ItemFuncaoLink) itLista.next();
						if (funcaoLink.getNome().equals("marcadores")){
														
							ItemEstrutMarcadorIettm marcador = marcadorDao.getUltimoMarcador(item, usuario);
							if(marcador != null){
						%>	
							<a
							href="javascript:abrirPopUpListaAnotacao(<%=item.getCodIett()%>, 'p')" title="Lembrete">
							<%=funcaoLink.getNome()%>
							</a><BR>
						<%
							}
						}
						else{
							%><a href="<%=funcaoLink.getLink()%>"><%=funcaoLink.getNome()%></a><BR><%	
						}
						
					}
				}
%>
				</td>
			</tr>
<%
		}
	} else {
%>
		<tr><td colspan=2>&nbsp;</td></tr>
		<tr><td colspan=2 align="center">Nenhum resultado encontrado</td></tr>
		<tr><td colspan=2>&nbsp;</td></tr>
<%
	}
%>
	<tr><td class="espacador" colspan=2><img src="../../images/pixel.gif"></td></tr>
	<tr><td colspan=2>&nbsp;</td></tr>
	<tr class="cor_sim">
		<td colspan=2 align="center">
			<input type="button" name="btVoltar" value="Voltar" onclick="aoClicarVoltar(form);">
		</td>
	</tr>
	<tr><td colspan=2>&nbsp;</td></tr>
	<tr><td class="espacador" colspan=2><img src="../../images/pixel.gif"></td></tr>
</table>
</div>
<%
}catch(ECARException e){
	org.apache.log4j.Logger.getLogger(this.getClass()).error(e);
	Mensagem.alert(_jspx_page_context, e.getMessageKey());
	
} catch (Exception e){
%>
	<%@ include file="/excecao.jsp"%>
<%
}
%>

<%@ include file="../../include/estadoMenu.jsp"%>

<input type="hidden" name="msgPesquisa" value="">

</form>

</div> <!-- conteudo -->
</body>
<% /* Controla o estado do Menu (aberto ou fechado) */%>
<%@ include file="../../include/mensagemAlert.jsp" %>
<%@ include file="/include/ocultarAguarde.jsp"%>
</html>
