	
<jsp:directive.page import="ecar.bean.NomeImgsNivelPlanejamentoBean"/>
<%@ include file="/login/validaAcesso.jsp"%>
<%@ include file="/frm_global.jsp"%>

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
<%@ page import="ecar.pojo.UsuarioUsu"%>

<%@ taglib uri="/WEB-INF/ecar-util.tld" prefix="util" %>
<%@ taglib uri="/WEB-INF/ecar-combo.tld" prefix="combo" %>

<%
try{
	
	request.setAttribute("exibirAguarde", "true");
	
	String codTipoAcompanhamento = Pagina.getParamStr(request, "codTipoAcompanhamento");
	String mesReferencia = "";
	if(!"".equals(Pagina.getParamStr(request, "referencia_hidden")))
		mesReferencia = Pagina.getParamStr(request, "referencia_hidden");
	else
		mesReferencia = Pagina.getParamStr(request, "mesReferencia");
	String periodo = Pagina.getParamStr(request, "periodo");
	String codIettRelacao = Pagina.getParamStr(request, "codIettRelacao");
	
	AcompReferenciaItemDao acompReferenciaItemDao = new AcompReferenciaItemDao(request); 
	ItemEstruturaDao itemDao = new ItemEstruturaDao(request);
	ItemEstruturaIett itemEstrutura = null;	
	
	String strCodAri = Pagina.getParamStr(request, "codAri");
	
	List listCodAri = new ArrayList();
	
//	if(!"".equals(Pagina.getParamStr(request, "referencia")) && !"S".equals(Pagina.getParamStr(request, "linkControle"))){
		// Veio nova referência, selecionada na combo, deve trocar o Ari.
//		strCodAri = Pagina.getParamStr(request, "referencia");
		
//	}
	
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
	
		List listaIetts = new ArrayList();	
		
		for (Iterator iter = arisByIetts.iterator(); iter.hasNext();) {
			AcompReferenciaItemAri ari = (AcompReferenciaItemAri) iter.next();
			listaIetts.add(ari.getItemEstruturaIett());
		}

        arvoreItensSelecionados = itemDao.getItensOrdenados(listaIetts, aref.getTipoAcompanhamentoTa());
    	acompReferenciaItem = (AcompReferenciaItemAri) arisByIetts.get(0);
	} else {
		acompReferenciaItem = (AcompReferenciaItemAri) acompReferenciaItemDao.buscar(AcompReferenciaItemAri.class, Long.valueOf(strCodAri));
	}
	
	
	if(!"".equals(strCodAri)) {
		acompReferenciaItem = (AcompReferenciaItemAri) new AcompReferenciaItemDao(request).buscar(
						AcompReferenciaItemAri.class, Long.valueOf(strCodAri));
		itemEstrutura = acompReferenciaItem.getItemEstruturaIett();
	} else if(!"".equals(codIettRelacao)) {
		itemEstrutura = (ItemEstruturaIett)itemDao.buscar(ItemEstruturaIett.class, Long.valueOf(codIettRelacao));
	}
	
	AcompReferenciaItemAri ari = acompReferenciaItem;
	AcompReferenciaItemDao ariDao = new AcompReferenciaItemDao(request);
	
%>

<html lang="pt-br"> 
<head>
<%@ include file="/include/meta.jsp"%>
<%@ include file="/titulo.jsp"%>
<link rel="stylesheet" href="<%=_pathEcar%>/css/style_<%=configuracaoCfg.getEstilo().getNome()%>.css" type="text/css">
<script language="javascript" src="<%=_pathEcar%>/js/menu_retratil.js"></script>
<script language="javascript" src="<%=_pathEcar%>/js/validacoes.js"></script>
<script language="javascript" src="<%=_pathEcar%>/js/destaqueLinha.js"></script>
<script>
function recarregar(tela){
	document.forms[0].action = "relatorioImpresso.jsp?tela=" + tela;
	document.forms[0].submit();
}
function trocaAba(link, codAri){
	document.forms[0].codAri.value = codAri;
	document.forms[0].action = link;
	document.forms[0].submit();
}

function aoClicarGerarAbaRelatorioImpresso(form, codigoItem, tela){
	var selecionados = codigoItem;
	var primeiroCodAri = "";
	var i = 0;
	var contador = 0;
	var el = document.getElementsByTagName("INPUT");
	var fim = el.length;
	
   	while (i < fim ){
   		var idFilho = "codItemFilhos_"+contador;
   		if ((el.item(i).type  == "checkbox") && (el.item(i).value != "todos")) {  
   		   	if (el.item(i).checked == true){
    	     	selecionados = selecionados + document.getElementById(idFilho).value + ";";
         		if (primeiroCodAri == "")
        			primeiroCodAri = el.item(i).value;
        	}
        	
        	contador++;  
        }
            
        i++;  
   	}
  
  	
	document.form.codigosItem.value = selecionados;  	 
	form.action = "relatorioImpresso2.jsp?tela=" + tela;
	form.submit();
}

function aoClicarExibirDatasLimites(form, codigoItem){
	alert ("Em desenvolvimento");
}

function trocaAba(link, codAri){
	document.forms[0].codAri.value = codAri;
	document.forms[0].action = link;
	document.forms[0].clicouAba.value = "S";
	document.forms[0].submit();
}

function trocaAbaSemAri(link, codIettRelacao){
	document.form.itemDoNivelClicado.value = codIettRelacao;
	document.form.action = link;
	document.forms[0].clicouAba.value = "S";
	document.form.submit();
}

function trocaAbaImpressao(link){
	document.forms[0].action = link;
	document.forms[0].clicouAba.value = "S";
	document.forms[0].submit();
}

function trocarAba(nomeAba) {
	document.forms[0].action = nomeAba;
	document.forms[0].clicouAba.value = "S";
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

<%@ include file="/cabecalho.jsp" %>
<%@ include file="/divmenu.jsp"%> 

<body>

<div id="conteudo"> 

<form  name="form" method="post" >	

<%
	boolean ehRegistro = false;
	boolean ehVisualizacao = false;
	
	String itemDoNivelClicado = Pagina.getParamStr(request, "itemDoNivelClicado");
	String funcaoSelecionada = "RELATORIO";
	
	String tela = Pagina.getParamStr(request, "tela");
	if(tela != null && !tela.equals("")){
		if(tela.equals("R") || tela.startsWith("R?"))
			ehRegistro = true;
		if(tela.equals("V"))
			ehVisualizacao = true;
	}

	/************************************REGISTRO****************************************************/
	if(ehRegistro){	  	
		UsuarioUsu usuario = ((ecar.login.SegurancaECAR)session.getAttribute("seguranca")).getUsuario();
		if(itemDoNivelClicado != null && !itemDoNivelClicado.equals("")) {
			itemEstrutura = (ItemEstruturaIett)itemDao.buscar(ItemEstruturaIett.class, Long.valueOf(itemDoNivelClicado));
		} else {
			itemEstrutura= ari.getItemEstruturaIett();
		}
		
		AcompReferenciaAref arefSelecionado = ari.getAcompReferenciaAref();
		
		%>
		<input type="hidden" name="tela" value="R">
		<%@ include  file="../../form_registro.jsp" %>		
		<%			
		paginaBtAvancRetroceder = "relatorioImpresso.jsp";
	/************************************VISUALIZAÇÃO****************************************************/	
	} else if(ehVisualizacao){
	
		
		%>
		<input type="hidden" name="tela" value="V">
		<%@ include  file="../../form_visualizar.jsp" %>	
		<%
		paginaBtAvancRetroceder = "relatorioImpresso.jsp";
	} 
	
	
	/************************************LISTA GERAL****************************************************/
	else { %>
		<%@ include file="/titulo_tela.jsp"%><br>
		
		<%
		String telaListagem = "posicaoGeral.jsp?codTipoAcompanhamento="+codTipoAcompanhamento +"&mesReferencia="+mesReferencia + "&periodo=" + periodo;
		%>
		
		<util:barraLinkTipoAcompanhamentoTag
			codTipoAcompanhamentoSelecionado="<%=Pagina.getParamStr(request, "codTipoAcompanhamento")%>"
			chamarPagina="<%= _pathEcar + "/acompanhamento/posicaoGeral.jsp" %>"/>
			
		<util:barraLinksRelatorioAcompanhamento
			acompReferenciaItem="<%=acompReferenciaItem%>"  
			funcaoSelecionada="relatorioImpresso"
			primeiroIettClicado="<%=request.getParameter("primeiroIettClicado")%>"
			primeiroAriClicado="<%=request.getParameter("primeiroAriClicado")%>"
			caminho="<%=_pathEcar + "/acompanhamento/"%>"
			codTipoAcompanhamento="<%=Pagina.getParamStr(request, "codTipoAcompanhamento")%>"
			gruposUsuario="<%=seguranca.getGruposAcesso() %>"
			abaSuperior ="S"  
			contextPath="<%=_pathEcar%>"
			listaGeral="<%=telaListagem %>"
		/>
			
		<br><br><br>	
<%	}
	 %>	


<input type="hidden" name="codTipoAcompanhamento" value="<%=Pagina.getParamStr(request, "codTipoAcompanhamento")%>">
<input type="hidden" name="periodo" value="<%=Pagina.getParamStr(request, "periodo")%>">
<%
if(!Pagina.getParamStr(request, "mesReferencia").equals("")){
%>
	<input type="hidden" name="mesReferencia" value="<%=Pagina.getParamStr(request, "mesReferencia")%>">
<%}else {%>
	<input type="hidden" name="mesReferencia" value="<%=Pagina.getParamStr(request, "referencia_hidden")%>">
<%} %>
<input type="hidden" name="niveisPlanejamento" value="<%=Pagina.getParamStr(request, "niveisPlanejamento")%>">
<input type="hidden" name="primeiroIettClicado" value="<%=Pagina.getParamStr(request, "primeiroIettClicado")%>">
<input type="hidden" name="primeiroAriClicado" value="<%=Pagina.getParamStr(request, "primeiroAriClicado")%>">
<input type="hidden" name="codAri" value="<%=strCodAri%>">
<input type="hidden" name="codigosItem" value="<%=strCodigosItem%>">
<input type="hidden" name="itemDoNivelClicado" value="<%=Pagina.getParamStr(request, "itemDoNivelClicado")%>">
<!--input type="hidden" name="referencia_hidden" value="< %=Pagina.getParamStr(request, "referencia_hidden")% >"-->
<!-- Campo necessario para guardar a forma de visualização escolhida no filtro -->
<input type="hidden" name="hidFormaVisualizacaoEscolhida" value='<%=Pagina.getParamStr(request, "hidFormaVisualizacaoEscolhida")%>'>


<!-- Campo necessário para botões de Avança/Retrocede -->
<input type="hidden" name="codArisControle" value="<%=Pagina.getParamStr(request, "codArisControle")%>">
<input type="hidden" name="codArisControleVisualizacao" value="<%=Pagina.getParamStr(request, "codArisControleVisualizacao")%>">

<input type="hidden" name="codRegd" value="">
<input type="hidden" name="clicouAba" value="<%= Pagina.getParamStr(request, "clicouAba")%>">
<input type="hidden" name="tipoPadraoExibicaoAba" value="<%= Pagina.getParamStr(request, "tipoPadraoExibicaoAba")%>">



<table border="0" cellpadding="0" cellspacing="0" width="100%">
	<tr><td class="espacador" colspan="2"><img src="<%=_pathEcar%>/images/pixel.gif"></td></tr>
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
			<input type="checkbox" class="form_check_radio" name="selecionarTodos" onclick="aoClicarSelecionar(form);" value="todos">Marcar Todos
		</td>
		
		
		<td align="right">
			<%if(ehRegistro||ehVisualizacao) {
				if(true){%>
						<input type="button" value="Selecionar opções de impressão" class="botao" onclick="aoClicarGerarAbaRelatorioImpresso(form, '<%=strCodigosItem%>', '<%=tela%>');"><%
				}else{%>
						<input type="button" value="Exibir Datas Limites" class="botao" onclick="aoClicarExibirDatasLimites(form, '<%=strCodigosItem%>');"><%
				} 
			} else {%>
				<input type="button" value="Selecionar opções de impressão" class="botao" onclick="aoClicarGerar(form, '<%=tela%>');">
			<%}%>
		</td>
	
	</tr>
	<tr><td class="espacador" colspan="2"><img src="<%=_pathEcar%>/images/pixel.gif"></td></tr>
</table>

<div id=subconteudo>
<table border="0" cellpadding="0" cellspacing="0" width="100%">	
	<tr class="linha_subtitulo">
		<td colspan="2">&nbsp;&nbsp;&nbsp;&nbsp;Acompanhamentos</td>
		<td>
<%
			String scriptCombo = "onchange=\"recarregar('" + tela + "');\"";
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
List itensApresentadosComFilhos = new ArrayList();

//int contadorPais = 0;
int contadorFilhos = 0;

//Iterator itArvoreItensSelecionados = arvoreItensSelecionados.iterator();
//while(itArvoreItensSelecionados.hasNext()) {
	//AtributoEstruturaListagemItens aeSelecionado = (AtributoEstruturaListagemItens) itArvoreItensSelecionados.next();
	ItemEstruturaIett iettSelecionado = itemEstrutura; //aeSelecionado.getItem();
	AcompReferenciaItemAri ariSelecionado = ari; // null;
	
	/*Iterator iteraAri = arisByIetts.iterator();
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
	}*/
	
	//int nivelItemSelecionado = iettSelecionado.getNivelIett().intValue();
	
	// obter os itens filhos do item selecionado na tela
	List lista = acompReferenciaItemDao.getAcompReferenciaItemFilhosByAri(ariSelecionado, orgaoResponsavel);
	
	// arvore para os itens
	Iterator itArvore = lista.iterator();
	List listaIetts = new ArrayList();
	
	while(itArvore.hasNext()){
		ari = (AcompReferenciaItemAri) itArvore.next();
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
			itensApresentadosComFilhos.add(iett);
		}
		else {
			continue;
		}
		
		//Identação pelo nível do item
		int nivel = iett.getNivelIett().intValue();
		
		String imgNivel = "";
		
		for(int i = 1; i < nivel; i++){
			imgNivel = imgNivel + "<img src=\"" + _pathEcar + "/images/pixel.gif\" width=\"22\" height=\"9\">";
		}	
		
		if(iett.equals(iettSelecionado)){
			cont = -1;
			codigosAriOrdenados += ariSelecionado.getCodAri().toString() + ";";
%>
			<tr class="cor_sim_relatorio" onmouseover="javascript:destacaLinha(this,'over','')" onmouseout="javascript: destacaLinha(this,'out','cor_sim_relatorio')" onClick="javascript: destacaLinha(this,'click','cor_sim_relatorio')">		
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
				cor = "cor_sim_relatorio"; 
			}		
			
			AcompReferenciaItemAri ariFilho = acompReferenciaItemDao.getAriByIett(lista, iett);
			
%>
			<tr class="<%=cor%>" onmouseover="javascript:destacaLinha(this,'over','')" onmouseout="javascript: destacaLinha(this,'out','<%=cor%>')" onClick="javascript: destacaLinha(this,'click','<%=cor%>')">		
				<td>
					<%=imgNivel%>
					<img src="<%=_pathEcar%>/images/icon_bullet_seta.png">
					<%
					if(ariFilho != null) {
						itensApresentadosComFilhos.add(ariFilho.getItemEstruturaIett());
						habilitarCheckTodos = true;
					%>
						<input type="checkbox" class="form_check_radio" name="codAriFilhos" value="<%=ariFilho.getCodAri()%>">
						<input type="hidden" name="codItemFilhos_<%=contadorFilhos%>" id="codItemFilhos_<%=contadorFilhos%>" value="<%=ariFilho.getItemEstruturaIett().getCodIett()%>">
					<%
						
						contadorFilhos++;
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
		
//		contadorPais++;
%>
<%
//}

int contadorItens=0;
if(itensApresentadosComFilhos != null) {
	Iterator itItensApresentadosComFilhos	= itensApresentadosComFilhos.iterator();
	while(itItensApresentadosComFilhos.hasNext()) {
		ItemEstruturaIett itemE = (ItemEstruturaIett) itItensApresentadosComFilhos.next();
		%>
			<input type="hidden" name="codItemMonitoramento_<%=contadorItens%>" id="codItemMonitoramento_<%=contadorItens%>" value="<%=itemE.getCodIett()%>">
		<%
		contadorItens++;
	}
	
}


%>
	<tr><td class="espacador" colspan="3"><img src="<%=_pathEcar%>/images/pixel.gif"></td></tr>

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
<%@ include file="/include/ocultarAguarde.jsp"%>
<% /* Controla o estado do Menu (aberto ou fechado) */%>
<%@ include file="/include/estadoMenu.jsp"%>
<%@ include file="/include/mensagemAlert.jsp" %>
</html>

<script language="javascript">
function aoClicarGerar(form){

	var selecionados = "";
	var primeiroCodAri = "";
	var i = 0;
	var j = 0;
	var el = document.getElementsByTagName("INPUT");
	var elem = document.getElementsByTagName("INPUT");
	var fim = el.length;
	var existe = false;
	var contadorItens = 0;
	var contadorCheckBox = 0;
	var contador = 0;
	
		
	//percorre os hidden do codigo
	while (i < fim ){
		existe = false; 
		
		
		if ((el.item(i).type  == "hidden") && (el.item(i).id.substr(0,21) == "codItemMonitoramento_")) {
	   		j=0;
	   		contadorCheckBox = 0;
	   		while(j<fim ) {
	   			if ((elem.item(j).type  == "checkbox") && (el.item(j).value != "todos")) { 
	   				var idFilho = "codItemFilhos_"+ contadorCheckBox;
	   				
	   				contadorCheckBox++;
	   				if(document.getElementById(idFilho).value == el.item(i).value) {
	   					
		   			   	if (elem.item(j).checked == true) {
			   		   		var idItem = "codItemMonitoramento_" + contadorItens;
			   		   	 	selecionados = selecionados + document.getElementById(idItem).value + ";";
			    	     	
			        	}
			        	
			        	existe = true; 
			        	j = fim;
			        }	
		        } 
		        j++;
	   		}
	   		
	   		if(existe == false) {
	   			var idItem = "codItemMonitoramento_" + contadorItens;
	   			selecionados = selecionados + document.getElementById(idItem).value + ";";
	   		} else 
	   			existe = false;
	   		
	   		contadorItens++;		
	   	}
	   	
	   	       
	   	i++;
	   	
	   
   	}
   	
  
  	document.form.codigosItem.value = selecionados;
  	form.action = "relatorioImpresso2.jsp";
	form.submit();
}


</script>
