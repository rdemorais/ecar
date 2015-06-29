
<jsp:directive.page import="ecar.bean.NomeImgsNivelPlanejamentoBean"/><%@ include file="../login/validaAcesso.jsp"%>
<%@ include file="../frm_global.jsp"%>

<%@ page import="ecar.dao.AcompReferenciaItemDao" %>
<%@ page import="ecar.dao.ItemEstruturaDao" %>
<%@ page import="ecar.pojo.AcompReferenciaItemAri" %> 
<%@ page import="ecar.pojo.ItemEstruturaIett" %> 
<%@ page import="ecar.pojo.OrgaoOrg" %> 
<%@ page import="ecar.bean.AtributoEstruturaListagemItens" %>
<%@ page import="comum.util.Data" %> 

<%@ page import="java.util.List" %> 
<%@ page import="java.util.Iterator" %>

<%@ page import="ecar.pojo.AcompReferenciaAref"%>
<%@ page import="ecar.dao.AcompReferenciaDao" %>

<%@ taglib uri="/WEB-INF/ecar-util.tld" prefix="util" %>
<%@ taglib uri="/WEB-INF/ecar-combo.tld" prefix="combo" %>

<%
try{ 
	request.setAttribute("exibirAguarde", "true");
	
	AcompReferenciaItemDao acompReferenciaItemDao = new AcompReferenciaItemDao(request); 
	
	ItemEstruturaDao itemDao = new ItemEstruturaDao(request);
	
	String strCodAri = Pagina.getParamStr(request, "codAri");
	List listCodAri = new ArrayList();
	
	if(!"".equals(Pagina.getParamStr(request, "referencia")) && !"S".equals(Pagina.getParamStr(request, "linkControle"))){
		// Veio nova referência, selecionada na combo, deve trocar o Ari.
		strCodAri = Pagina.getParamStr(request, "referencia");
	}
	
	OrgaoOrg orgaoResponsavel = new OrgaoOrg();
	if(!"".equals(Pagina.getParamStr(request, "orgaoResponsavel"))){
		orgaoResponsavel = (OrgaoOrg) acompReferenciaItemDao.buscar(OrgaoOrg.class, Long.valueOf( Pagina.getParamStr(request, "orgaoResponsavel") ) );
	}
	
	String strCodigosItem = Pagina.getParamStr(request, "codigosItem");

	if("".equals(strCodigosItem) && !"".equals(strCodAri)) {
		// buscar o item do ARI corrente
		AcompReferenciaItemAri acompReferenciaItem = (AcompReferenciaItemAri) acompReferenciaItemDao.buscar(AcompReferenciaItemAri.class, Long.valueOf(strCodAri));
		strCodigosItem = acompReferenciaItem.getItemEstruturaIett().getCodIett().toString() + ";";
	}
	
	AcompReferenciaAref aref = null;
	
	if(!"".equals(Pagina.getParamStr(request, "referencia_hidden")))
		aref = (AcompReferenciaAref) new AcompReferenciaDao(null).buscar(AcompReferenciaAref.class, Long.valueOf(Pagina.getParamStr(request, "referencia_hidden")));
	else
		aref = (AcompReferenciaAref) new AcompReferenciaDao(null).buscar(AcompReferenciaAref.class, Long.valueOf(Pagina.getParamStr(request, "mesReferencia")));
	
	List arisByIetts = acompReferenciaItemDao.getAcompReferenciaItemFilhosByIett(strCodigosItem, aref, orgaoResponsavel);
	
	AcompReferenciaItemAri acompReferenciaItem;
	List arvoreItensSelecionados = new ArrayList();
	if(!arisByIetts.isEmpty()) {
		Iterator itArvore = arisByIetts.iterator();
		List listaIetts = new ArrayList();
		
		while(itArvore.hasNext()){
			AcompReferenciaItemAri ari = (AcompReferenciaItemAri) itArvore.next();
			listaIetts.add(ari.getItemEstruturaIett());
		}
        arvoreItensSelecionados = itemDao.getItensOrdenados(listaIetts, aref.getTipoAcompanhamentoTa());
        
    	acompReferenciaItem = (AcompReferenciaItemAri) arisByIetts.get(0);
	} else {
		acompReferenciaItem = (AcompReferenciaItemAri) acompReferenciaItemDao.buscar(AcompReferenciaItemAri.class, Long.valueOf(strCodAri));
	}
	
	strCodAri = acompReferenciaItem.getCodAri().toString();
%>

<html lang="pt-br"> 
<head>
<%@ include file="../include/meta.jsp"%>
<%@ include file="/titulo.jsp"%>
<link rel="stylesheet" href="<%=_pathEcar%>/css/style_<%=configuracaoCfg.getEstilo().getNome()%>.css" type="text/css">
<script language="javascript" src="../js/menu_retratil.js"></script>
<script language="javascript" src="../js/validacoes.js"></script>
<script language="javascript" src="<%=_pathEcar%>/js/destaqueLinha.js"></script>
<script>
function recarregar(){
	document.forms[0].action = "relatorioImpresso.jsp";
	document.forms[0].submit();
}
function trocaAba(link, codAri){
	document.forms[0].codAri.value = codAri;
	document.forms[0].action = link;
	document.forms[0].submit();
}

function aoClicarGerar(form){
	form.action = "relatorioImpresso2.jsp";
	form.submit();
}
function trocaAbaImpressao(link){
	document.forms[0].action = link;
	document.forms[0].submit();
}


// Função para selecionar todos os Checkbox
function aoClicarSelecionar(form){
	var numChecks = verificaChecks(form, 'codAriFilhos');
	
	if(numChecks > 0){
		if(numChecks > 1){
			for(i = 0; i < form.codAriFilhos.length; i++)
				form.codAriFilhos[i].checked = form.selecionarTodos.checked;	
		}

		if(numChecks == 1)
			form.codAriFilhos.checked = form.selecionarTodos.checked;
	}else{
		alert("<%=_msg.getMensagem("relAcompanhamento.relImpresso.nenhumItem")%>");
	}
}
</script>
</head>

<body>

<%@ include file="../cabecalho.jsp" %>
<%@ include file="../divmenu.jsp"%> 

<div id="conteudo"> 

<util:barraLinkTipoAcompanhamentoTag
	codTipoAcompanhamentoSelecionado="<%=Pagina.getParamStr(request, "codTipoAcompanhamento")%>"
	chamarPagina="posicaoGeral.jsp"
/> 

<% if(arisByIetts == null || arisByIetts.isEmpty()){ %>

	<%@ include file="botoesAvancaRetrocede.jsp" %>

	<util:arvoreEstruturas 
		itemEstrutura="<%=acompReferenciaItem.getItemEstruturaIett()%>" 
		exibirLinks="false" 
		proximoNivel="false" 
		contextPath="<%=_pathEcar%>"
		primeiroIettClicado="<%=request.getParameter("primeiroIettClicado")%>"
		primeiroAriClicado="<%=request.getParameter("primeiroAriClicado")%>"
		codTipoAcompanhamentoSelecionado="<%=Pagina.getParamStr(request, "codTipoAcompanhamento")%>"
	/> 

<br>
	
	<util:barraLinksRelatorioAcompanhamento
		acompReferenciaItem="<%=acompReferenciaItem%>"  
		funcaoSelecionada="relatorioImpresso"
		primeiroIettClicado="<%=request.getParameter("primeiroIettClicado")%>"
		primeiroAriClicado="<%=request.getParameter("primeiroAriClicado")%>"
		caminho="<%=_pathEcar + "/relAcompanhamento/"%>"
		codTipoAcompanhamento="<%=Pagina.getParamStr(request, "codTipoAcompanhamento")%>"
		gruposUsuario="<%=seguranca.getGruposAcesso() %>" 
	/>

<br>
<% }else{ %>
<br>

	<util:barraLinksRelatorioAcompanhamento
		funcaoSelecionada="relatorioImpresso"
		links="N"
		caminho="<%=_pathEcar + "/relAcompanhamento/"%>"
		codTipoAcompanhamento="<%=Pagina.getParamStr(request, "codTipoAcompanhamento")%>"
	/>

<br>
<% } %>	
<form  name="form" method="post" >	
	
<table border="0" width="100%">
	<tr>
		<td valign="bottom" class="texto" align="left" colspan="2"> 
			<b>Data do relatório:</b> <%=Data.parseDate(Data.getDataAtual())%><br>
			<% if(arisByIetts == null || arisByIetts.isEmpty()){ %>
					<b>Mês de referência: </b>
					<combo:ComboReferenciaTag
						nome="referencia"
						acompReferenciaItem="<%=acompReferenciaItem%>"
						scripts="onchange='recarregar()'"
					/>
			<% } %>
		</td>
	</tr>
</table>

<input type="hidden" name="codTipoAcompanhamento" value="<%=Pagina.getParamStr(request, "codTipoAcompanhamento")%>">
<input type="hidden" name="periodo" value="<%=Pagina.getParamStr(request, "periodo")%>">
<input type="hidden" name="mesReferencia" value="<%=Pagina.getParamStr(request, "mesReferencia")%>">
<input type="hidden" name="niveisPlanejamento" value="<%=Pagina.getParamStr(request, "niveisPlanejamento")%>">
<input type="hidden" name="codAri" value="<%=strCodAri%>">
<input type="hidden" name="primeiroIettClicado" value="<%=Pagina.getParamStr(request, "primeiroIettClicado")%>">
<input type="hidden" name="primeiroAriClicado" value="<%=Pagina.getParamStr(request, "primeiroAriClicado")%>">
<input type="hidden" name="codigosItem" value="<%=strCodigosItem%>">

<!-- Campo necessário para botões de Avança/Retrocede -->
<input type="hidden" name="codArisControle" value="<%=Pagina.getParamStr(request, "codArisControle")%>">


<table border="0" cellpadding="0" cellspacing="0" width="100%">
	<tr><td class="espacador" colspan="2"><img src="../images/pixel.gif"></td></tr>
	<tr class="linha_titulo">
		<td>
			<% if(arisByIetts == null || arisByIetts.isEmpty()){ %>
				<%if("S".equals(acompReferenciaItem.getAcompReferenciaAref().getTipoAcompanhamentoTa().getIndMonitoramentoTa())){%>
					Monitorado
				<%}else{%>
					<%=acompReferenciaItem.getAcompReferenciaAref().getOrgaoOrg().getDescricaoOrg()%>
				<%}%>
			<%}%>
			<br>
			<input type="checkbox" class="form_check_radio" name="selecionarTodos" onclick="aoClicarSelecionar(form);">Marcar Todos
		</td>
		<td align="right">
			<input type="button" value="Selecionar opções de impressão" class="botao" onclick="aoClicarGerar(form);"> 
		</td>
	</tr>
	<tr><td class="espacador" colspan="2"><img src="../images/pixel.gif"></td></tr>
</table>

<div id=subconteudo>
<table border="0" cellpadding="0" cellspacing="0" width="100%">	
	<tr class="linha_subtitulo">
		<td colspan="2">&nbsp;&nbsp;&nbsp;&nbsp;Acompanhamentos</td>
		<td>
<%
			String scriptCombo = "onchange=\"recarregar();\"";
			if ( orgaoResponsavel.getCodOrg() != null ) {
%>
				<combo:ComboTag 
						nome="orgaoResponsavel"
						objeto="ecar.pojo.OrgaoOrg" 
						label="siglaOrg" 
						value="codOrg" 
						order="siglaOrg"
						filters="indAtivoOrg=S"
						selected="<%=orgaoResponsavel.getCodOrg().toString()%>"
						scripts="<%=scriptCombo%>"
						textoPadrao="Todos"
				/>
<%
			} else {
%>
				<combo:ComboTag 
						nome="orgaoResponsavel"
						objeto="ecar.pojo.OrgaoOrg" 
						label="siglaOrg" 
						value="codOrg" 
						order="siglaOrg"
						filters="indAtivoOrg=S"
						scripts="<%=scriptCombo%>"
						textoPadrao="Todos"
				/>
<%
			}
%>
		</td>
	</tr>
<%  
String codigosAriOrdenados = "";
boolean habilitarCheckTodos = false;

List itensApresentados = new ArrayList();

Iterator itArvoreItensSelecionados = arvoreItensSelecionados.iterator();
while(itArvoreItensSelecionados.hasNext()) {
	AtributoEstruturaListagemItens aeSelecionado = (AtributoEstruturaListagemItens) itArvoreItensSelecionados.next();
	ItemEstruturaIett iettSelecionado = aeSelecionado.getItem();
	AcompReferenciaItemAri ariSelecionado = null;
	
	Iterator iteraAri = arisByIetts.iterator();
	while(iteraAri.hasNext()) {
		AcompReferenciaItemAri ariAux = (AcompReferenciaItemAri) iteraAri.next();
		
		if(ariAux.getItemEstruturaIett().equals(iettSelecionado)) {
			ariSelecionado = ariAux;
		}
		if(ariSelecionado != null) {
			break;
		}
	}
	
	if(ariSelecionado == null) {
		continue;
	}
	
	//int nivelItemSelecionado = iettSelecionado.getNivelIett().intValue();
	
	// obter os itens filhos do item selecionado na tela
	List lista = acompReferenciaItemDao.getAcompReferenciaItemFilhosByAri(ariSelecionado, orgaoResponsavel);
	
	// arvore para os itens
	Iterator itArvore = lista.iterator();
	List listaIetts = new ArrayList();
	
	while(itArvore.hasNext()){
		AcompReferenciaItemAri ari = (AcompReferenciaItemAri) itArvore.next();
		listaIetts.add(ari.getItemEstruturaIett());
	}
    
    List arvoreItens = itemDao.getItensOrdenados(listaIetts, aref.getTipoAcompanhamentoTa());

	Iterator it = arvoreItens.iterator();
	int cont = 0;
	String cor = new String();
	while(it.hasNext()){
		AtributoEstruturaListagemItens aeListItens = (AtributoEstruturaListagemItens) it.next();
		ItemEstruturaIett iett = aeListItens.getItem();
		
		// controle devido a árvore
		if(!itensApresentados.contains(iett)){
			itensApresentados.add(iett);
		}
		else {
			continue;
		}
		
		//Identação pelo nível do item
		int nivel = iett.getNivelIett().intValue();
		
		String imgNivel = "";
		
		for(int i = 1; i < nivel; i++){
			imgNivel = imgNivel + "<img src=\"../images/pixel.gif\" width=\"22\" height=\"9\">";
		}	
		
		if(iett.equals(iettSelecionado)){
			cont = -1;
			codigosAriOrdenados += ariSelecionado.getCodAri().toString() + ";";
%>
			<tr class="cor_sim bgCinzaClaro2" onmouseover="javascript:destacaLinha(this,'over','')" onmouseout="javascript: destacaLinha(this,'out','cor_sim bgCinzaClaro2')" onClick="javascript: destacaLinha(this,'click','cor_sim bgCinzaClaro2')">		
				<td><%=imgNivel%><b><%=aeListItens.getDescricao()%></b></td>
				<td>
<%
					Iterator itNiveis = itemDao.getNomeImgsNivelPlanejamentoItemAgrupado(iett).iterator();
					while(itNiveis.hasNext()){
						NomeImgsNivelPlanejamentoBean imagemNivelPlanejamento = (NomeImgsNivelPlanejamentoBean)itNiveis.next();
						out.print(imagemNivelPlanejamento.geraHtmlImg(request));
					}
%>
				</td>
				<td>
<%
					/* Imprime nome do orgão responsável pelo item */
					if(iettSelecionado.getOrgaoOrgByCodOrgaoResponsavel1Iett() != null){
						if(iettSelecionado.getOrgaoOrgByCodOrgaoResponsavel1Iett().getSiglaOrg() != null)
							out.println(iettSelecionado.getOrgaoOrgByCodOrgaoResponsavel1Iett().getSiglaOrg());	
						else
							out.println(iettSelecionado.getOrgaoOrgByCodOrgaoResponsavel1Iett().getDescricaoOrg());	
					} else {
						/* Se não possuir orgao procura orgao do pai */
						ItemEstruturaIett itemAux = iettSelecionado;
						while(itemAux != null && itemAux.getOrgaoOrgByCodOrgaoResponsavel1Iett() == null && itemAux.getItemEstruturaIett() != null){
							itemAux = itemAux.getItemEstruturaIett();
						}
						if(itemAux != null && itemAux.getOrgaoOrgByCodOrgaoResponsavel1Iett() != null){
							if(itemAux.getOrgaoOrgByCodOrgaoResponsavel1Iett().getSiglaOrg() != null)
								out.println(itemAux.getOrgaoOrgByCodOrgaoResponsavel1Iett().getSiglaOrg());	
							else
								out.println(itemAux.getOrgaoOrgByCodOrgaoResponsavel1Iett().getDescricaoOrg());											
						}
					}
%>
				</td>
			</tr>
<%
		} else {
			if (cont % 2 == 0){
				cor = "cor_nao";
			} else {
				cor = "cor_sim"; 
			}		
			
			AcompReferenciaItemAri ariFilho = acompReferenciaItemDao.getAriByIett(lista, iett);
			
%>
			<tr class="<%=cor%>" onmouseover="javascript:destacaLinha(this,'over','')" onmouseout="javascript: destacaLinha(this,'out','<%=cor%>')" onClick="javascript: destacaLinha(this,'click','<%=cor%>')">		
				<td>
					<%=imgNivel%>
					<img src="../images/icon_bullet_seta.png">
					<%
					if(ariFilho != null) {
						habilitarCheckTodos = true;
					%>
						<input type="checkbox" class="form_check_radio" name="codAriFilhos" value="<%=ariFilho.getCodAri()%>">
					<%
					}
					%>
					<%=aeListItens.getDescricao()%>
				</td>
				<td>
<%
					Iterator itNiveis = itemDao.getNomeImgsNivelPlanejamentoItemAgrupado(iett).iterator();
					while(itNiveis.hasNext()){
						NomeImgsNivelPlanejamentoBean imagemNivelPlanejamento = (NomeImgsNivelPlanejamentoBean)itNiveis.next();
						out.print(imagemNivelPlanejamento.geraHtmlImg(request));
					}
%>
				</td>
				<td>
<%
					/* Imprime nome do orgão responsável pelo item */
					if(iettSelecionado.getOrgaoOrgByCodOrgaoResponsavel1Iett() != null){
						if(iettSelecionado.getOrgaoOrgByCodOrgaoResponsavel1Iett().getSiglaOrg() != null)
							out.println(iettSelecionado.getOrgaoOrgByCodOrgaoResponsavel1Iett().getSiglaOrg());	
						else
							out.println(iettSelecionado.getOrgaoOrgByCodOrgaoResponsavel1Iett().getDescricaoOrg());	
					} else {
						/* Se não possuir orgao procura orgao do pai */
						ItemEstruturaIett itemAux = iettSelecionado;
						while(itemAux != null && itemAux.getOrgaoOrgByCodOrgaoResponsavel1Iett() == null && itemAux.getItemEstruturaIett() != null){
							itemAux = itemAux.getItemEstruturaIett();
						}
						if(itemAux != null && itemAux.getOrgaoOrgByCodOrgaoResponsavel1Iett() != null){
							if(itemAux.getOrgaoOrgByCodOrgaoResponsavel1Iett().getSiglaOrg() != null)
								out.println(itemAux.getOrgaoOrgByCodOrgaoResponsavel1Iett().getSiglaOrg());	
							else
								out.println(itemAux.getOrgaoOrgByCodOrgaoResponsavel1Iett().getDescricaoOrg());											
						}
					}
%>
				</td>
			</tr>
<%
			}
			cont++;
		}
%>
<%
}
%>
	<tr><td class="espacador" colspan="3"><img src="../images/pixel.gif"></td></tr>

</table>
</div>
<%
	if(!habilitarCheckTodos){
%>
		<script language="javascript">
			document.form.selecionarTodos.checked = false;
			document.form.selecionarTodos.disabled = true;
		</script>
<%
	}
%>

<input type="hidden" name="codigosAri" value="<%=codigosAriOrdenados%>">

</form>

<%
} catch (ECARException e){
	org.apache.log4j.Logger.getLogger(this.getClass()).error(e);
	Mensagem.alert(_jspx_page_context, e.getMessageKey());
} catch (Exception e){
%>
	<%@ include file="/excecao.jsp"%>
<%
}
%>

<br>
</div>
</body>
<%@ include file="../include/ocultarAguarde.jsp"%>
<% /* Controla o estado do Menu (aberto ou fechado) */%>
<%@ include file="../include/estadoMenu.jsp"%>
<%@ include file="../include/mensagemAlert.jsp" %>
</html>