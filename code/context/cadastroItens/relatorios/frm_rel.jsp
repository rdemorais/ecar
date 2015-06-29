<%@ include file="../../login/validaAcesso.jsp"%>
<%@ include file="../../frm_global.jsp"%>

<%@ page import="ecar.pojo.ItemEstruturaIett" %>
<%@ page import="ecar.dao.ItemEstruturaDao" %>
<%@ page import="ecar.pojo.EstruturaFuncaoEttf" %>
<%@ page import="ecar.dao.EstruturaFuncaoDao" %>
<%@ page import="ecar.dao.SisGrupoAtributoDao" %>
<%@ page import="ecar.pojo.SisAtributoSatb" %>
<%@ page import="ecar.permissao.ValidaPermissao" %> 
<%@ page import="java.util.Iterator" %>
<%@ page import="java.util.List" %>
<%@ page import="ecar.login.SegurancaECAR" %>
<%@ page import="ecar.pojo.UsuarioUsu" %>
<%@ page import="java.util.Set" %>
<%@ page import="ecar.dao.OrgaoDao" %>
<%@ page import="ecar.pojo.OrgaoOrg" %>
<%@ page import="comum.database.Dao" %>
<%@ page import="ecar.dao.ItemEstruturaCriterioDao" %>
<%@ page import="ecar.pojo.FuncaoFun" %>
<%@ page import="ecar.dao.FuncaoDao" %>
<%@ page import="ecar.pojo.CriterioCri" %>
<%@ page import="ecar.pojo.SituacaoSit" %>
<%@ page import="ecar.dao.SituacaoDao" %>
<%@ page import="ecar.dao.EstruturaDao" %>
<%@ page import="ecar.pojo.EstruturaEtt" %>
<%@ page import="java.util.Collections" %>
<%@ page import="java.util.Comparator" %>
<%@ page import="ecar.bean.AtributoEstruturaListagemItens" %>

<%@ taglib uri="/WEB-INF/ecar-util.tld" prefix="util" %>
<%@ taglib uri="/WEB-INF/ecar-combo.tld" prefix="combo" %>

<%
	int indexBarra = (Pagina.getParamStr(request, "proximaAba")) == "" ? 0 : Integer.valueOf(Pagina.getParamStr(request, "proximaAba")).intValue();
	//String submitRevisao = Pagina.getParamStr(request, "submitRevisao");
	
	/* TODO: Retirar essa variável quando o processo do PPA terminar.*/
	boolean exibePPA = ("/ecar".equals(request.getContextPath())) ? true : false;
	
	if(!exibePPA){
		exibePPA = ("/ecardemo".equals(request.getContextPath())) ? true : false;
	}


	
	String submitRevisao = "";
	String submitPPA = "";
	if(exibePPA){
		//submitPPA = Pagina.getParamStr(request, "submitPPA");
		if(indexBarra == 2 || indexBarra == 3)
			submitRevisao = "S";
		if(indexBarra == 1)
			submitPPA = "S";
	}
	else{
		if(indexBarra == 1)
			submitRevisao = "S";
	}
%>
<html lang="pt-br">
<head>
<%@ include file="../../include/meta.jsp"%>
<%@ include file="/titulo.jsp"%>
<link rel="stylesheet" href="<%=_pathEcar%>/css/style_<%=configuracaoCfg.getEstilo().getNome()%>.css" type="text/css">

<script language="javascript" src="../../js/focoInicial.js"></script>
<script language="javascript" src="../../js/validacoes.js"></script>

<script language="javascript" src="<%=_pathEcar%>/js/destaqueLinha.js"></script>
<script language="javascript" src="<%=_pathEcar%>/js/listaItensCadastro.js" type="text/javascript"></script>		 
<script language="javascript" src="<%=_pathEcar%>/js/jquery.js" type="text/javascript"></script>
<script type="text/javascript">jQuery.noConflict();</script>		
<script language="javascript" src="<%=_pathEcar%>/js/cookie.js"></script>

<script>

function aoClicarBtn3(form) {
	if(form.desabilitarGerarRelatorio.value == "false"){
		if(validaNumeroRelatorios(form)){
				form.target = "_blank";
				form.action="ctrl.jsp";
				form.hidAcao.value="imprimir";
				if(form.indTipoRelatorioTodos.checked){
					form.todosCheck.value = "T";
				}
				else if(form.indTipoRelatorioMonitorados.checked){
					form.todosCheck.value = "M";
				}
				else if(form.indTipoRelatorioNaoMonitorados.checked){
					form.todosCheck.value = "N";
				}
				else{
					form.todosCheck.value = "I";
				}
				form.submit();	
				form.target = "";
		}
	}else{
		alert(form.msgErro.value);
	}
}

function aoClicarVoltar(form){
	form.codIettPrincipal.value = "<%=Pagina.getParamLong(request, "codIettPaiImprimir")%>";
	form.ultEttSelecionado.value = "<%=Pagina.getParamLong(request, "codEttImprimir")%>";
	form.action = "<%=_pathEcar%>/cadastroItens/listaItem/lista.jsp";
	form.submit();
}

function validaNumeroRelatorios(form){
	var totalChecks = verificaChecks(form, "itemFilho");
	var totalMarcado = 0;
	//if(form.indTipoRelatorio[2].checked == false){
		if(totalChecks > 1){
			for(i = 0; i < form.itemFilho.length; i++){
				if(form.itemFilho[i].checked){
					totalMarcado++;
				}	
			}
		}
		else {
			if (totalChecks == 1){
				if(form.itemFilho.checked){
					totalMarcado = 1;
				}	
			}
		}
	//}
	if(totalMarcado == 0){
		alert("Nenhum item selecionado.");
		return false;		
	}

	/*
	if(form.indTipoRelatorio[0].checked == false && totalMarcado > <%=_msg.getMensagem("itemEstrutura.relatorio.validacao.qtdeItem")%>){
		alert("Número de itens selecionados excedeu o máximo permitido (<%=_msg.getMensagem("itemEstrutura.relatorio.validacao.qtdeItem")%>).");
	
		if(form.indTipoRelatorioTodos.checked){
			desmarcarTudo(form);
			form.indTipoRelatorioTodos.checked = false;
		}

		return false;
	}
	*/

	return true;
}

/* Controle de seleção para checks, selecionados pela estrutura. */
function controleItensFilhosByEstrutura(form,codEtt){

	form.indTipoRelatorioTodos.checked = false;
	form.indTipoRelatorioMonitorados.checked = false;
	form.indTipoRelatorioNaoMonitorados.checked = false;
	form.indTipoRelatorioSelecionadosPeloUsuario.checked = true;

	checkItensFilhos(form, "T", false);

	//Limpando check dos níveis...
	var idsNivel = form.idNiveis.value.split("|");
	for(x = 0; x < idsNivel.length; x++){
		if(idsNivel[x] != ""){
			eval("form.nivel" + idsNivel[x] + ".checked = false;");
		}
	}	
	
	var idsEst = form.idsEstruturas.value.split("|");
	var marcou = false;
	for(x = 0; x < idsEst.length; x++){
		//estruturaItemFilho[i]: Campo Hidden correspondente ao item listado com código da sua estrutura.
		if(idsEst[x] != ""){
			for(i = 0; i < form.estruturaItemFilho.length; i++){
				//Para cada estrutura gerada, percorre os itens marcando/desmarcando os itens que forem daquela estrutura, dependendo do checkbox da estrutura.
				if(form.estruturaItemFilho[i].value == idsEst[x]){
					form.itemFilho[i].checked = eval("form.indEstrutura" + idsEst[x] + ".checked;");
				}
				
				if(form.itemFilho[i].checked == true){
					marcou = true;
				}
			}
		}
	}
	
	if(!marcou){
		alert("Nenhum item selecionado.");
	}	
}

/* Controle de seleção para checks, selecionados pelos níveis. */
function controleItensFilhosByNiveis(form,status){
	if(status.checked == true){
		form.indTipoRelatorioTodos.checked = false;
		form.indTipoRelatorioMonitorados.checked = false;
		form.indTipoRelatorioNaoMonitorados.checked = false;
		form.indTipoRelatorioSelecionadosPeloUsuario.checked = true;

		checkItensFilhos(form, "T", false);
	
		//Limpando check das estruturas...
		var idsEst = form.idsEstruturas.value.split("|");
		for(x = 0; x < idsEst.length; x++){
			if(idsEst[x] != ""){
				eval("form.indEstrutura" + idsEst[x] + ".checked = false;");
			}
		}	

		var idsNivel = form.idNiveis.value.split("|");
		var marcou = false;

		//Para cada item filho, verificar se possui níveis.
		for(i = 0; i < form.niveisItemFilho.length; i++){
			//Para cada nivel do item, verificar se algum nível é igual a qualquer um dos níveis selecionados.
			var idsNivelItem = form.niveisItemFilho[i].value.split("|");
			var temNivel = false;
			for(n = 0; n < idsNivelItem.length; n++){
				if(idsNivelItem[n] != ""){
					if(eval("form.nivel" + idsNivelItem[n] + ".checked == true")){
						temNivel = true;
						break;
					}
				}
			}

			form.itemFilho[i].checked = temNivel;

			if(form.itemFilho[i].checked == true){
				marcou = true;
			}
		}
		if(!marcou){
			alert("Nenhum item selecionado.");
		}
	
	}	
}

/* Controle de seleção para checks de Todos, Monitorados e Não Monitorados. */
function controleItensFilhos(form, selecionado){
	
	var marcar = false;

	//Limpando check das estruturas...
	var idsEst = form.idsEstruturas.value.split("|");
	for(x = 0; x < idsEst.length; x++){
		if(idsEst[x] != ""){
			eval("form.indEstrutura" + idsEst[x] + ".checked = false;");
		}
	}	

	//Limpando check dos níveis...
	var idsNivel = form.idNiveis.value.split("|");
	for(x = 0; x < idsNivel.length; x++){
		if(idsNivel[x] != ""){
			eval("form.nivel" + idsNivel[x] + ".checked = false;");
		}
	}	
	
	if(selecionado == "T"){ // Todos
		form.indTipoRelatorioMonitorados.checked = false;
		form.indTipoRelatorioNaoMonitorados.checked = false;
		form.indTipoRelatorioSelecionadosPeloUsuario.checked = false;
		
		checkItensFilhos(form, "M", false);
		checkItensFilhos(form, "N", false);
		checkItensFilhos(form, "S", false);
		
		marcar = form.indTipoRelatorioTodos.checked;
	}

	else if(selecionado == "M"){ // Monitorados
		form.indTipoRelatorioTodos.checked = false;
		form.indTipoRelatorioNaoMonitorados.checked = false;
		form.indTipoRelatorioSelecionadosPeloUsuario.checked = false;

		checkItensFilhos(form, "T", false);
		checkItensFilhos(form, "N", false);
		checkItensFilhos(form, "S", false);
		
		marcar = form.indTipoRelatorioMonitorados.checked;
	}

	else if(selecionado == "N"){ // Não Monitorados
		form.indTipoRelatorioTodos.checked = false;
		form.indTipoRelatorioMonitorados.checked = false;
		form.indTipoRelatorioSelecionadosPeloUsuario.checked = false;

		checkItensFilhos(form, "T", false);
		checkItensFilhos(form, "M", false);
		checkItensFilhos(form, "S", false);
		
		marcar = form.indTipoRelatorioNaoMonitorados.checked;
	}
	
	else if(selecionado == "naoPPA"){ //Não incluídos no PPA
		form.action = "frm_rel.jsp?proximaAba=<%=indexBarra%>";
		form.submit();
	}
	
	else if(selecionado == "S"){
		form.indTipoRelatorioTodos.checked = false;
		form.indTipoRelatorioMonitorados.checked = false;
		form.indTipoRelatorioNaoMonitorados.checked = false;

		checkItensFilhos(form, "T", false);
		checkItensFilhos(form, "M", false);
		checkItensFilhos(form, "N", false);
		
		marcar = false;
	}

	checkItensFilhos(form, selecionado, marcar);
	validaNumeroRelatorios(form);
}
/**
* Marca/Desmarca itens Filhos. 
* @param form.
* @param selecionado: T = Todos, M = Monitorados, N = Não monitorados.
* @param check: true = marca itens filhos, false = desmarca itens filhos.
*/
function checkItensFilhos(form, selecionado, check){
	var passou = false;
	for(i = 0; i < form.itemFilho.length; i++){
		if(selecionado == "M" && form.itemFilho[i].id == "monitorado"){
			passou = true;
			form.itemFilho[i].checked = check;
		}
		else if(selecionado == "N" && form.itemFilho[i].id == "naoMonitorado"){
			passou = true;
			form.itemFilho[i].checked = check;
		}
		else if(selecionado == "T"){
			passou = true;
			form.itemFilho[i].checked = check;
		}
		else if(selecionado == "S"){
			passou = true;
			form.itemFilho[i].checked = check;
		}
	}
}

function reload(){
	document.form.primeiraChamada.value="N";
	document.form.action = "frm_rel.jsp?proximaAba=<%=indexBarra%>";
	document.form.submit();		
}

function desmarcarTipoRelatorio(form){
	form.indTipoRelatorioTodos.checked = false;
	form.indTipoRelatorioMonitorados.checked = false;
	form.indTipoRelatorioNaoMonitorados.checked = false;
	form.indTipoRelatorioSelecionadosPeloUsuario.checked = true;
}

/*Essa função é usada para pegar os critérios selecionados 
no popup --> NÂO MUDE A ASSINATURA DESTA FUNÇÃO!
*/
function getDadosPesquisa(codigosCom, codigosSem, descricao){
	document.form.criteriosIncluidosCom.value = codigosCom;
	document.form.criteriosIncluidosSem.value = codigosSem;
	document.form.action = "frm_rel.jsp?proximaAba=<%=indexBarra%>";
	document.form.submit();
}

function popup_pesquisaCriterio(pojo, funcaoGetDadosPesquisa, parametros) {
	var jaIncluidosCom = document.form.criteriosIncluidosCom.value;
	var jaIncluidosSem = document.form.criteriosIncluidosSem.value;
		
    if (typeof funcaoGetDadosPesquisa == "undefined") funcaoGetDadosPesquisa = 'getDadosPesquisa';
    if (typeof parametros == "undefined") parametros = '';

    if (jaIncluidosCom != "" && jaIncluidosSem == "")
		return abreJanela('popup_pesquisaCriterio.jsp?hidPojo=' + pojo + '&jaIncluidosCom=' + jaIncluidosCom + '&hidFuncao=' + funcaoGetDadosPesquisa + parametros,'500','400','pesquisa');
    else if (jaIncluidosCom == "" && jaIncluidosSem != "")
		return abreJanela('popup_pesquisaCriterio.jsp?hidPojo=' + pojo + '&jaIncluidosSem=' + jaIncluidosSem + '&hidFuncao=' + funcaoGetDadosPesquisa + parametros,'500','400','pesquisa');
    else if (jaIncluidosCom != "" && jaIncluidosSem != "")
		return abreJanela('popup_pesquisaCriterio.jsp?hidPojo=' + pojo + '&jaIncluidosCom=' + jaIncluidosCom + '&jaIncluidosSem=' + jaIncluidosSem + '&hidFuncao=' + funcaoGetDadosPesquisa + parametros,'500','400','pesquisa');
	else
		return abreJanela('popup_pesquisaCriterio.jsp?hidPojo=' + pojo + '&hidFuncao=' + funcaoGetDadosPesquisa + parametros,'500','400','pesquisa');
		
}

/*Essa função é usada para pegar as situações selecionadas 
no popup --> NÂO MUDE A ASSINATURA DESTA FUNÇÃO!
*/
function getDadosPesquisaSituacoes(codigos, descricao){
	document.form.situacoesIncluidas.value = codigos;
	document.form.action = "frm_rel.jsp?proximaAba=<%=indexBarra%>";
	document.form.submit();
}

function popup_pesquisaSituacoes(pojo, funcaoGetDadosPesquisa, parametros) {
	var jaIncluidos = document.form.situacoesIncluidas.value;
		
    if (typeof funcaoGetDadosPesquisa == "undefined") funcaoGetDadosPesquisa = 'getDadosPesquisaSituacoes';
    if (typeof parametros == "undefined") parametros = '';

    if (jaIncluidos != "")
		return abreJanela('popup_pesquisaSituacoes.jsp?hidPojo=' + pojo + '&jaIncluidos=' + jaIncluidos + '&hidFuncao=' + funcaoGetDadosPesquisa + parametros,'400','400','pesquisa');
	else
		return abreJanela('popup_pesquisaSituacoes.jsp?hidPojo=' + pojo + '&hidFuncao=' + funcaoGetDadosPesquisa + parametros,'400','400','pesquisa');
		
}

function desmarcarCheckBoxes(form){
	var marcarTodos
	for (i=0;i<form.elements.length;i++){
		if((form.elements[i].type == "checkbox") && (form.elements[i].name == "itemFilho")) {
			if (form.elements[i].checked) {
				marcarTodos = false;
			} else {
				marcarTodos = true;
			}
		break;
		}
    }
	for (i=0;i<form.elements.length;i++){
		if((form.elements[i].type == "checkbox") && (form.elements[i].name == "itemFilho")) {
			if (marcarTodos) {
				form.elements[i].checked=1;      		
	      	} else {
		      	form.elements[i].checked=0;      		
	      	}
		}
	}
}

</script>

</head>

<body onload="focoInicial(document.form);">

<%@ include file="../../cabecalho.jsp" %>
<%@ include file="../../divmenu.jsp"%>

<div id="conteudo">

<div id="tituloTelaCadastro">
	<!-- TITULO -->
	<%@ include file="/titulo_tela.jsp"%> 
</div>

<form name="form" method="post">
	<input type=hidden name="codIettPrincipal" value="<%=Pagina.getParam(request, "codIettPrincipal")%>">
	<input type=hidden name="ultEttSelecionado" value="<%=Pagina.getParam(request, "ultEttSelecionado")%>">

<%
try{
	boolean possuiFiltros = false;
	List itensFiltrados = new ArrayList();
	List estruturas = new ArrayList();
	Dao dao = new Dao();
	SisGrupoAtributoDao sisGrupoAtribDao =  new SisGrupoAtributoDao(request);
	
	ItemEstruturaDao itemEstruturaDao = new ItemEstruturaDao(request);
	
	boolean imprimirEstrutura = ("S".equals(Pagina.getParamStr(request, "imprimirEstrutura"))) ? true : false;
	

	String codIettPrincipal = Pagina.getParamStr(request, "codIettPaiImprimir");
		
	
	ItemEstruturaIett itemEstrutura = new ItemEstruturaIett();
	String codAba = "";
	EstruturaFuncaoEttf estruturaFuncao = new EstruturaFuncaoEttf();
	EstruturaFuncaoDao estruturaFuncaoDao = new EstruturaFuncaoDao(request);
	
	
	if(!imprimirEstrutura){
		itemEstrutura = (ItemEstruturaIett) itemEstruturaDao.buscar(ItemEstruturaIett.class, Long.valueOf(Pagina.getParamStr(request, "codIett")));
		
		ValidaPermissao validaPermissao = new ValidaPermissao();
		if ( !validaPermissao.permissaoConsultaIETT( itemEstrutura.getCodIett() , seguranca ) )
		{
			response.sendRedirect( request.getContextPath() +  "/acessoIndevido.jsp" );
		}	
		
		codAba = Pagina.getParamStr(request, "codAba");
		
		estruturaFuncao = (EstruturaFuncaoEttf) estruturaFuncaoDao.getLabelFuncao(itemEstrutura.getEstruturaEtt(), Long.valueOf(codAba));
	}

	ItemEstruturaCriterioDao itemCriterioDao = new ItemEstruturaCriterioDao(request);
	SituacaoDao situacaoDao = new SituacaoDao(request);

	//SegurancaECAR seguranca = (SegurancaECAR) session.getAttribute("seguranca");

	UsuarioUsu usuarioLogado = seguranca.getUsuario();
	Set gruposUsuario = seguranca.getGruposAcesso();

	OrgaoOrg orgaoResponsavel = new OrgaoOrg();
	if(!"".equals(Pagina.getParamStr(request, "orgaoResponsavel"))){
		orgaoResponsavel = (OrgaoOrg) dao.buscar(OrgaoOrg.class, Long.valueOf( Pagina.getParamStr(request, "orgaoResponsavel") ) );
	}
	
	String orgaoEscolhido = (orgaoResponsavel.getCodOrg() != null) ? orgaoResponsavel.getSiglaOrg() : "Todos";
	

	/*Comentado devido ao Bug 3606*/
	//List filhos = itemEstruturaDao.getDescendentes(itemEstrutura, true);
	List filhos = new ArrayList();
	if(!imprimirEstrutura){
		filhos.add(itemEstrutura);
		filhos.addAll(itemEstruturaDao.getDescendentesComPermissoesOrdenado(itemEstrutura, usuarioLogado, gruposUsuario));
	}
	else {
		EstruturaEtt estruturaEscolhida = (EstruturaEtt) new EstruturaDao(request).buscar(EstruturaEtt.class, Long.valueOf(Pagina.getParamStr(request, "codEttImprimir")));
		long codIettPai = Pagina.getParamLong(request, "codIettPaiImprimir");
		filhos.addAll(itemEstruturaDao.getDescendentesComPermissoesOrdenadoByEstrutura(estruturaEscolhida, codIettPai, usuarioLogado, gruposUsuario));
	}

	//Pegando lista de Critérios e Situações selecionadas...
	String criteriosCom = Pagina.getParamStr(request, "criteriosIncluidosCom");
	String criteriosSem = Pagina.getParamStr(request, "criteriosIncluidosSem");
	String situacoes = Pagina.getParamStr(request, "situacoesIncluidas");
	String[] criteriosParaFiltrarCom = (!"".equals(criteriosCom.trim())) ? criteriosCom.split("\\|") : new String[] {};
	String[] criteriosParaFiltrarSem = (!"".equals(criteriosSem.trim())) ? criteriosSem.split("\\|") : new String[] {};
	String[] situacoesParaFiltrar = (!"".equals(situacoes.trim())) ? situacoes.split("\\|") : new String[] {};

	List listaCriteriosCom = new ArrayList();
	List listaCriteriosSem = new ArrayList();
	List listaSituacoes = new ArrayList();
					
	for(int i = 0; i < criteriosParaFiltrarCom.length; i++){
		listaCriteriosCom.add(criteriosParaFiltrarCom[i]);
	}

	for(int i = 0; i < criteriosParaFiltrarSem.length; i++){
		listaCriteriosSem.add(criteriosParaFiltrarSem[i]);
	}
	
	for(int i = 0; i < situacoesParaFiltrar.length; i++){
		listaSituacoes.add(situacoesParaFiltrar[i]);
	}

	List itensTemp = new ArrayList();

	itensTemp = new ArrayList(itemEstruturaDao.filtrarRelatorioItemEstrutura(filhos, orgaoResponsavel.getCodOrg(), listaCriteriosCom, listaCriteriosSem, listaSituacoes, submitRevisao, submitPPA));
	

	//Verifica se possui algum tipo de filtro e se tiver, monta a árvore de itens...
	if("S".equals(submitPPA) || "S".equals(submitRevisao) || listaSituacoes.size() > 0 || listaCriteriosCom.size() > 0 || listaCriteriosSem.size() > 0 || orgaoResponsavel.getCodOrg() != null){
		if (listaSituacoes.size() > 0 || listaCriteriosCom.size() > 0 || listaCriteriosSem.size() > 0 || orgaoResponsavel.getCodOrg() != null)
			possuiFiltros = true;
		itensFiltrados.addAll(itensTemp);
		if(!imprimirEstrutura && !itensTemp.contains(itemEstrutura))
			itensTemp.add(itemEstrutura);
		filhos.clear();
		filhos.addAll(itensTemp);
		
		// remover os itens superiores ao nível atual
		
		if(!imprimirEstrutura){
			Iterator it = filhos.iterator();
			while(it.hasNext()) {
				ItemEstruturaIett iett = (ItemEstruturaIett) it.next();
				
				if(iett.getNivelIett().intValue() <= itemEstrutura.getNivelIett().intValue()) {
					it.remove();
				}
			}
		}
	}
	
	// ordenar os itens pela sigla
	itensTemp = new ArrayList(filhos);
	if(!imprimirEstrutura && !itensTemp.contains(itemEstrutura))
		itensTemp.add(itemEstrutura);
	filhos.clear();
	List atributosListagem = new ArrayList(itemEstruturaDao.getItensOrdenados(itensTemp, null));
	Iterator itAtrList = atributosListagem.iterator();
	while(itAtrList.hasNext()){
		AtributoEstruturaListagemItens atList = (AtributoEstruturaListagemItens) itAtrList.next();
		filhos.add(atList.getItem());	
	}

	/*Populando a lista de estruturas dos itens*/
	Iterator itEstruturas = filhos.iterator();
	while(itEstruturas.hasNext()){
		ItemEstruturaIett iettEstrutura = (ItemEstruturaIett) itEstruturas.next();
		if(!estruturas.contains(iettEstrutura.getEstruturaEtt())){
			estruturas.add(iettEstrutura.getEstruturaEtt());
		}
	}
	
	Collections.sort(estruturas, new Comparator(){
		public int compare(Object obj1, Object obj2){
			EstruturaEtt e1 = (EstruturaEtt) obj1;
			EstruturaEtt e2 = (EstruturaEtt) obj2;
			return e1.getCodEtt().compareTo(e2.getCodEtt());
		}
	});

	
	OrgaoDao orgaoDao = new OrgaoDao(null);
	List orgaos = null;	
	if(Pagina.getParamStr(request, "primeiraChamada") == null
		|| Pagina.getParamStr(request, "primeiraChamada").trim().equals("")) {

		List orgaosPPA = null; //Usado somente aqui para buscar os orgaosPPA na primeira vez que a tela é chamada.

		orgaos = orgaoDao.getOrgaosRespItemEstrutura(filhos); //OrgaoResp1
		orgaosPPA = orgaoDao.getOrgaosResp2ItemEstrutura(filhos); //OrgaoResp2

		session.setAttribute("orgaos1PrimeiraChamada", orgaos);
		session.setAttribute("orgaos2PrimeiraChamada", orgaosPPA);
	}
	else {
		if("S".equals(submitRevisao))
			orgaos = new ArrayList((List)session.getAttribute("orgaos2PrimeiraChamada"));
		else
			orgaos = new ArrayList((List)session.getAttribute("orgaos1PrimeiraChamada"));
	}	

boolean ehTelaListagem = false;
 
EstruturaEtt estruturaEttSelecionada = null;
EstruturaDao estruturaDaoArvoreItens = new EstruturaDao(request);


ConfiguracaoCfg configuracaoCfg3 = new ecar.dao.ConfiguracaoDao(request).getConfiguracao();

if(configuracaoCfg3.getIndExibirArvoreNavegacaoCfg() != null && configuracaoCfg3.getIndExibirArvoreNavegacaoCfg().equals("S")){
	%>
	
	<script language="javascript" src="../../js/menu_retratil_cadastro.js"></script>
	<script language="javascript" src="../../js/menu_cadastro.js"></script>	

	<%
	}else{
	%>
	
	<script language="javascript" src="../../js/menu_retratil.js"></script>
	<%
	}
	%>
<%@ include file="../arvoreItens.jsp"%>

<table width="100%" cellspacing="0" cellpadding="0" border="0">
	<tr>
		<td>
			<%
			//Se usar a árvore o id do div precisa ser "conteudoCadastroEstrutura" para que seja utilizado o estilo da árvore
			if(conf.getIndExibirArvoreNavegacaoCfg() != null && conf.getIndExibirArvoreNavegacaoCfg().equals("S")){ %>
			<div id="conteudoCadastroEstrutura">
			<%
			}else{
			%>
			<div>
			<%
			}
			%>
				<table width="100%" border="0" cellpadding="0" cellspacing="0">
					<tr>
					<%
					//Utilizado apenas quando a árvore está configurada para aparecer
					if (conf.getIndExibirArvoreNavegacaoCfg() != null && conf.getIndExibirArvoreNavegacaoCfg().equals("S")) {
					%>
					<!-- Barra amarela -->
		    			<td class="menuHideShowCadastro">
		    			<!-- Botão na barra -->
					<div id="btmenuCadastro"></div>
					</td>
					<script language="javascript">			
						//Inicia com o menu cadastro aberto
						botaoCadastro("aberto");
						mudaEstadoCadastro("aberto");			
					</script>
					<%} %>
						<td width="100%" valign="top">
							
							<%
							if(!imprimirEstrutura){
							%>
							<util:arvoreEstruturas itemEstrutura="<%=itemEstrutura%>" contextPath="<%=_pathEcar%>" seguranca="<%=seguranca%>" idLinhaCadastro="<%=Pagina.getParamStr(request,"ultimoIdLinhaDetalhado") %>" ultimoIdLinhaExpandido="<%=Pagina.getParamStr(request,"ultimoIdLinhaExpandido")%>"/> 
						 	<%
						 	}
						 	%>
								<%
							if(!imprimirEstrutura){
							%>
							<util:barraLinksItemEstrutura 
								estrutura="<%=itemEstrutura.getEstruturaEtt()%>" 
								selectedFuncao="<%=codAba%>" 
								codItemEstrutura="<%=itemEstrutura.getCodIett().toString()%>"
								contextPath="<%=request.getContextPath()%>" 
								idLinhaCadastro="<%=Pagina.getParamStr(request,"ultimoIdLinhaDetalhado") %>"
							/>
						
							<br><br>

	
							<%
							}
							%>
						
							
								<input type=hidden name="hidAcao" value="">	
								<input type=hidden name="codIett" value="<%=itemEstrutura.getCodIett()%>">
								<input type=hidden name="codAba" value="<%=Pagina.getParamStr(request, "codAba")%>">
								<input type=hidden name="todosCheck" value="S">
								<input type=hidden name="naoIncluidoPPA" value="<%=Pagina.getParamStr(request, "indTipoRelatorioNaoPPA")%>">
								<input type="hidden" name="primeiraChamada" value="<%=Pagina.getParamStr(request, "primeiraChamada")%>">	
								<input type=hidden name="criteriosIncluidosCom" value="<%=Pagina.getParamStr(request, "criteriosIncluidosCom")%>">
								<input type=hidden name="criteriosIncluidosSem" value="<%=Pagina.getParamStr(request, "criteriosIncluidosSem")%>">
								<input type=hidden name="situacoesIncluidas" value="<%=Pagina.getParamStr(request, "situacoesIncluidas")%>">
								<input type="hidden" name="submitRevisao" value="<%=submitRevisao%>">
								<input type="hidden" name="imprimirEstrutura" value="<%=Pagina.getParamStr(request, "imprimirEstrutura")%>">
								<input type="hidden" name="codEttImprimir" value="<%=Pagina.getParamStr(request, "codEttImprimir")%>">
								<input type="hidden" name="codIettPaiImprimir" value="<%=Pagina.getParamLong(request, "codIettPaiImprimir")%>">
								<!-- usado para guardar a estrutura detalhada na tela de cadastro -->								
								
								
								<%
								if(exibePPA){
								%>
									<input type="hidden" name="submitPPA" value="<%=submitPPA%>">
								<%
								}
						
								String codigoOrgaoResponsavel = "";
								if (orgaoResponsavel != null && orgaoResponsavel.getCodOrg() != null){
									codigoOrgaoResponsavel = orgaoResponsavel.getCodOrg().toString();
								}
								
								Boolean desabilitar = new Boolean(true);
								if (atributosListagem != null && !atributosListagem.isEmpty())
									desabilitar = new Boolean(false);
									
								List itensBarra = new ArrayList();
								String titulo = "Estrutura do " + conf.getTituloSistema();
								itensBarra.add(titulo);
								if(exibePPA){
									itensBarra.add("Resumo - PPA");
									itensBarra.add("Revisão - PPA");
									itensBarra.add("PPA - Metas Físicas");
								}
						
								String complementoRequest = "";
								if(!imprimirEstrutura){
									complementoRequest = "&codIett=" + itemEstrutura.getCodIett() + "&codAba=" + Pagina.getParamStr(request, "codAba") + "&ultimoIdLinhaDetalhado=" + Pagina.getParamStr(request, "ultimoIdLinhaDetalhado");
								}
								else{
									complementoRequest = "&codEttImprimir=" + Pagina.getParamStr(request, "codEttImprimir") + "&imprimirEstrutura=" + Pagina.getParamStr(request, "imprimirEstrutura") + "&codIettPaiImprimir=" + Pagina.getParamStr(request, "codIettPaiImprimir");
								}
								%>
								<util:barraLinksRelatorioItens
									itensBarra="<%=itensBarra%>"
									chamarPagina="frm_rel.jsp"
									indexAtivo="<%=indexBarra%>"
									complementoRequest="<%=complementoRequest%>"
								/>
								<input type="hidden" name="codOrgaoSelecionado" value="<%=codigoOrgaoResponsavel%>">
						
								<%
								if(!imprimirEstrutura){
								%>
									<util:barrabotoes btn3="Gerar Relatório" desabilitarBtn3="<%=desabilitar%>"/>
									<input type="hidden" name="desabilitarGerarRelatorio" value="<%=desabilitar%>"/>
								<%
								}
								else {
								%>
									<util:barrabotoes btn3="Gerar Relatório" voltar="Voltar para Listagem"/>
									<input type="hidden" name="desabilitarGerarRelatorio" value="<%=desabilitar%>"/>
								<%
								}
								%>
						
								<table class="form" width="100%">
								<tr>
									<td class="label_rel" colspan="2">&nbsp;</td>
								</tr>
								<tr>
									<%
									String labelModelo = "&nbsp;";
									if(indexBarra == 0)
										labelModelo = "Modelo do Relatório";
									%>
									<td class="label_rel" nowrap="nowrap" width="10%"><%=labelModelo%></td>
									<td width="90%">&nbsp;
									<%
										String _checkedRelacao = ("L".equals(Pagina.getParamStr(request, "indTipoRelatorio")) || "".equals(Pagina.getParamStr(request, "indTipoRelatorio"))) ? "checked=\"checked\"" : "";
										String _checkedResumo = ("R".equals(Pagina.getParamStr(request, "indTipoRelatorio"))) ? "checked=\"checked\"" : "";
										String _checkedCompleto = ("C".equals(Pagina.getParamStr(request, "indTipoRelatorio"))) ? "checked=\"checked\"": "";
										
										if(indexBarra == 0){
										
									%>
										<input type="radio" class="form_check_radio" name="indTipoRelatorio" value="L" <%=_checkedRelacao%>>Rela&ccedil;&atilde;o 
										<input type="radio" class="form_check_radio" name="indTipoRelatorio" value="R"  <%=_checkedResumo%>>Resumo
										<input type="radio" class="form_check_radio" name="indTipoRelatorio" value="C" <%=_checkedCompleto%>>Completo
									<%
										}
										
										if(exibePPA){
											if(indexBarra == 1){
											%>
												<input type="hidden" name="indTipoRelatorio" value="RPPA">
											<%
											}
											if(indexBarra == 2){
											%>
												<input type="hidden" name="indTipoRelatorio" value="RE">
											<%
											}
											if(indexBarra == 3){
												%>
													<input type="hidden" name="indTipoRelatorio" value="REMF">
												<%
											}
										}
										else{
											if(indexBarra == 1){
											%>
												<input type="hidden" name="indTipoRelatorio" value="RE">
											<%
											}
										}
									%>
									</td>
								</tr>
								<tr>
									<td width="10%" class="label_rel" valign="top" nowrap="nowrap">&Oacute;rg&atilde;o Selecionado</td>
									<td width="90%">
						<%
									if ( orgaoResponsavel.getCodOrg() != null ) {
						%>
										<combo:ComboTag 
												nome="orgaoResponsavel"
												objeto="ecar.pojo.OrgaoOrg" 
												label="siglaOrg" 
												value="codOrg" 
												order="siglaOrg"
											colecao="<%=orgaos%>"
											selected="<%=orgaoResponsavel.getCodOrg().toString()%>"
												scripts=" onchange='reload();'"
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
												colecao="<%=orgaos%>"
												scripts=" onchange='reload();'"
												textoPadrao="Todos"
										/>		
						<%
									}
						%>			
									</td>			
								</tr>
								<tr>
									<td width="10%" class="label_rel" valign="top" nowrap="nowrap">Itens Selecionados</td>
									<td width="90%" valign="top">
										<%
											String _checkTodos = (!possuiFiltros) ? "checked" : "";
											String _checkSelecionadoPeloUsuario = (possuiFiltros) ? "checked" : "";
										%>
										<input type="radio" class="form_check_radio" name="indTipoRelatorioTodos" <%=_checkTodos%> onclick="javascript:controleItensFilhos(form, 'T')">Todos<br>
										<input type="radio" class="form_check_radio" name="indTipoRelatorioMonitorados" onclick="javascript:controleItensFilhos(form, 'M')">Monitorados<br>
										<input type="radio" class="form_check_radio" name="indTipoRelatorioNaoMonitorados" onclick="javascript:controleItensFilhos(form, 'N')">N&atilde;o Monitorados<br>
										<input type="radio" class="form_check_radio" name="indTipoRelatorioSelecionadosPeloUsuario" <%=_checkSelecionadoPeloUsuario%> onclick="javascript:controleItensFilhos(form, 'S')">Selecionados pelo usuário<br>
									</td>			
								</tr>
								<%
								if(!estruturas.isEmpty()){
								%>
								
								<!-- Inicia Estrutura -->
								
						
								<tr>
									<td colspan="2"><hr>
								<table width="90%" >
								<tr>
									<td colspan="2">
										&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
										&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
										&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
										&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
										&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
										&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
										&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
										Par&acirc;metros Selecionados pelo Usu&aacute;rio<br>
									</td>
								</tr>
								<tr>
									<td width="10%" class="label_rel" valign="top" nowrap="nowrap" style="border-right-width: 0px;">Estrutura</td>
									<td width="90%" style="border-left-width: 0px;">
										<%
										Iterator itEtt = estruturas.iterator();
										String identacao = "";
										int nivelEtt = -1;
										EstruturaDao estDao = new EstruturaDao(request);
										String idsEstruturas = "";
										while(itEtt.hasNext()){
											EstruturaEtt ett = (EstruturaEtt) itEtt.next();
										%>
											<%=identacao%><input type="checkbox" class="form_check_radio" name="indEstrutura<%=ett.getCodEtt().toString()%>" onclick="javascript:controleItensFilhosByEstrutura(form,<%=ett.getCodEtt().toString()%>)"><%=ett.getNomeEtt()%><br>
										<%	
											if(nivelEtt != estDao.getNivel(ett)){
												identacao += "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;";
												nivelEtt = estDao.getNivel(ett);
											}
											idsEstruturas += ett.getCodEtt() + "|";
										}
										%>
										<input type="hidden" name="idsEstruturas" value="<%=idsEstruturas%>">
									</td>
								</tr>
								<%
								}
								else{
								%>
								
								<tr>
									<td colspan="2"><hr>
								<table width="90%" >
								<tr>
									<td colspan="2">
										&nbsp;
									</td>
								</tr>
								<tr>
									<td width="10%" class="label_rel" valign="top" nowrap="nowrap" style="border-right-width: 0px;"></td>
									<td width="90%" style="border-left-width: 0px;">
										<input type="hidden" name="idsEstruturas" value="">
									</td>
								</tr>
								
								<%
								}
						
								
								List niveisPlanejamento = sisGrupoAtribDao.getAtributosOrdenados(conf.getSisGrupoAtributoSgaByCodSgaGrAtrNvPlan());
								
								if(niveisPlanejamento != null && !niveisPlanejamento.isEmpty()){
								%>
								<tr>
									<td width="10%" class="label_rel" valign="top" nowrap="nowrap" style="border-right-width: 0px;"><%=conf.getSisGrupoAtributoSgaByCodSgaGrAtrNvPlan().getDescricaoSga()%></td>
									<td width="90%" align="left" style="border-left-width: 0px;">
										<table>
									<%
										Iterator itNivelPlan = niveisPlanejamento.iterator();
										String idNiveis = "";
										while(itNivelPlan.hasNext()){
											SisAtributoSatb nivel = (SisAtributoSatb) itNivelPlan.next();
											%>
											<tr>
												<td align="right" width="20px">
													<img src="../../images/relAcomp/<%=nivel.getAtribInfCompSatb()%>" border="0">
												</td>
												<td><input type="checkbox" class="form_check_radio" name="nivel<%=nivel.getCodSatb().toString()%>" value="<%=nivel.getCodSatb().toString()%>" onclick="javascript:controleItensFilhosByNiveis(form,nivel<%=nivel.getCodSatb().toString()%>)"><%=nivel.getDescricaoSatb()%></td>
											</tr>
											<%
											idNiveis += nivel.getCodSatb().toString() + "|";
										}
									%>
											<tr>
												<td colspan="2">
													<input type="hidden" name="idNiveis" value="<%=idNiveis%>">
												</td>
											</tr>
										</table>
									</td>
								</tr>
								<%
								}
								else {
								%>
								<tr>
									<td colspan="2">
										<input type="hidden" name="idNiveis" value="">
									</td>
								</tr>
								<%
								}
								%>
								<tr>
									<td width="10%" class="label_rel" valign="top" nowrap="nowrap" style="border-right-width: 0px;">&nbsp;</td>
									<td width="90%" style="border-left-width: 0px;">
										<%
											FuncaoDao funcaoDao = new FuncaoDao(request);
											FuncaoFun funcao = funcaoDao.getFuncao(Long.valueOf("6")); //Função Critérios
											String labelFuncao = (funcao != null) ? funcao.getLabelPadraoFun() : "";
											
											String labelAuxCriterios = (!"".equals(Pagina.getParamStr(request, "criteriosIncluidosCom")) || !"".equals(Pagina.getParamStr(request, "criteriosIncluidosSem"))) ? _msg.getMensagem("itemEstrutura.relatorio.filtro.criterios") : "";
										%>
										<input type="button" value="Selecionar <%=labelFuncao%>" class="botao" onclick="popup_pesquisaCriterio('ecar.popup.PopUpCriterio');">
										&nbsp;<%=labelAuxCriterios%>
										<br> 
										<%
										if((listaCriteriosCom != null && listaCriteriosCom.size() > 0) ||
										(listaCriteriosSem != null && listaCriteriosSem.size() > 0)){
										%>
										<br>
										<table>
											<tr>
												<td width="40%"><b>Critério Selecionado</b></td>
													<td width="5%" align="center">Com</td>
												<td width="5%" align="center">Sem</td>
												<td width="50%">&nbsp;</td>
											</tr>
										<%
											if(listaCriteriosCom != null && listaCriteriosCom.size() > 0){
									        	Iterator itCriterios = listaCriteriosCom.iterator();
										        while(itCriterios.hasNext()){
										        	String id = (String) itCriterios.next();
										        	CriterioCri criterio = (CriterioCri) itemCriterioDao.buscar(CriterioCri.class, Long.valueOf(id));
													%>
													<tr>
														<td width="40%"><%=criterio.getDescricaoCri()%></td>
														<td width="5%" align="center">X</td>
														<td width="5%" align="center"></td>
														<td width="50%">&nbsp;</td>
													</tr>
													<%
										        }
											}
											if(listaCriteriosSem != null && listaCriteriosSem.size() > 0){
									        	Iterator itCriterios = listaCriteriosSem.iterator();
										        while(itCriterios.hasNext()){
										        	String id = (String) itCriterios.next();
										        	CriterioCri criterio = (CriterioCri) itemCriterioDao.buscar(CriterioCri.class, Long.valueOf(id));
													%>
													<tr>
														<td width="40%"><%=criterio.getDescricaoCri()%></td>
														<td width="5%" align="center"></td>
														<td width="5%" align="center">X</td>
														<td width="50%">&nbsp;</td>
													</tr>
													<%
										        }
											}
										%>
										</table>
										<%
										}
										
										String labelAuxSituacoes = (!"".equals(Pagina.getParamStr(request, "situacoesIncluidas"))) ? _msg.getMensagem("itemEstrutura.relatorio.filtro.situacoes") : "";
										%>
										<br>
										<input type="button" value="Selecionar Situações" class="botao" onclick="popup_pesquisaSituacoes('ecar.popup.PopUpSituacao');">
										&nbsp;<%=labelAuxSituacoes%>
										<br> 
										<%
										if(listaSituacoes != null && listaSituacoes.size() > 0){
										%>
										<br>
										<table>
											<tr>
												<td width="40%"><b>Situação Selecionada</b></td>
												<td width="60%">&nbsp;</td>
											</tr>
										<%
								        	Iterator itSituacoes = listaSituacoes.iterator();
									        while(itSituacoes.hasNext()){
									        	String id = (String) itSituacoes.next();
									        	SituacaoSit situacao = (SituacaoSit) situacaoDao.buscar(SituacaoSit.class, Long.valueOf(id));
												%>
												<tr>
													<td width="40%"><%=situacao.getDescricaoSit()%></td>
													<td width="60%">&nbsp;</td>
												</tr>
												<%
									        }
										%>
										
										</table>
										<%
										}
										%>				
									</td>			
								</tr>
								</table>
								<hr>
								<!-- Finaliza Estrutura -->
						
								</td>
								</tr>
								<tr>
									<td colspan="2">&nbsp;</td>
								</tr>
								<tr>
									<td width="10%" class="label_rel" valign="top" nowrap="nowrap">
										Itens selecionados para Impress&atilde;o<br>
										<input type="button" value="Marcar/Desmarcar Todos" class="botao" onclick="javascript:desmarcarCheckBoxes(form)">
										<br>
									</td>
									<td width="90%" nowrap="nowrap">
									<%
									//if (filhos != null && filhos.size() > 0){
									if (atributosListagem != null && !atributosListagem.isEmpty()){
						
										Iterator it = atributosListagem.iterator();
										
										//Cria checkbox para desmarcar todas as selções de uma vez só
										//Alterado devido mantis 7628: Colocado na coluna anterior, abaixo do label.
										//out.println("<input type='button' value='Marcar/Desmarcar Todos' class='botao' onclick='javascript:desmarcarCheckBoxes(form)'><br>");
										
										
										while(it.hasNext()){
											//ItemEstruturaIett itemFilho = (ItemEstruturaIett) it.next();
											AtributoEstruturaListagemItens atbList = (AtributoEstruturaListagemItens) it.next();
											ItemEstruturaIett itemFilho = atbList.getItem();
											
											String orgaoItens = "";
											
											if("S".equals(submitRevisao)){
												if(itemFilho.getOrgaoOrgByCodOrgaoResponsavel2Iett() != null){
													orgaoItens = " / " + itemFilho.getOrgaoOrgByCodOrgaoResponsavel2Iett().getSiglaOrg();
												}
											}
											else {
												if(itemFilho.getOrgaoOrgByCodOrgaoResponsavel1Iett() != null){
													orgaoItens = " / " + itemFilho.getOrgaoOrgByCodOrgaoResponsavel1Iett().getSiglaOrg();
												}
											}
						
											String monitoramento = "";
											for(int i = 1; i < itemFilho.getNivelIett().intValue(); i++){
												%>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<%
											}
									
											if(itemFilho != null && "S".equals(itemFilho.getIndMonitoramentoIett())){
												monitoramento = "monitorado";
											}
											else {
												monitoramento = "naoMonitorado";
											}
											
											String _checked = "";
											if(possuiFiltros){
												if(itensFiltrados.contains(itemFilho)){
													_checked = "checked";
												}
											}
											else {
												_checked = "checked";
											}
						
											
											String niveisItemFilho = "";
											if(itemFilho.getItemEstruturaNivelIettns() != null && !itemFilho.getItemEstruturaNivelIettns().isEmpty()){
												Iterator itNivel = itemFilho.getItemEstruturaNivelIettns().iterator();
												while(itNivel.hasNext()){
													SisAtributoSatb nivelFilho = (SisAtributoSatb) itNivel.next();
													niveisItemFilho += nivelFilho.getCodSatb().toString() + "|";
												}
											}
											
											if(!"Todos".equalsIgnoreCase(orgaoEscolhido)){
												if("S".equals(submitRevisao)){
													if (itemFilho.getOrgaoOrgByCodOrgaoResponsavel2Iett() != null){
														if (orgaoEscolhido.equals(itemFilho.getOrgaoOrgByCodOrgaoResponsavel2Iett().getSiglaOrg())){
												%>
															<input type="checkbox" class="form_check_radio" name="itemFilho" id="<%=monitoramento%>" <%=_checked%> onclick="javascript:desmarcarTipoRelatorio(form)" value=<%=itemFilho.getCodIett()%>>
															<input type="hidden" name="estruturaItemFilho" value="<%=itemFilho.getEstruturaEtt().getCodEtt().toString()%>">
															<input type="hidden" name="niveisItemFilho" value="<%=niveisItemFilho%>">
												<%
														}
													}
												}
												else {
													if (itemFilho.getOrgaoOrgByCodOrgaoResponsavel1Iett() != null){
														if (orgaoEscolhido.equals(itemFilho.getOrgaoOrgByCodOrgaoResponsavel1Iett().getSiglaOrg())){
												%>
															<input type="checkbox" class="form_check_radio" name="itemFilho" id="<%=monitoramento%>" <%=_checked%> onclick="javascript:desmarcarTipoRelatorio(form)" value=<%=itemFilho.getCodIett()%>>
															<input type="hidden" name="estruturaItemFilho" value="<%=itemFilho.getEstruturaEtt().getCodEtt().toString()%>">
															<input type="hidden" name="niveisItemFilho" value="<%=niveisItemFilho%>">
												<%
														}
													}
												}
												
												String exibirItem = "".equals(atbList.getDescricao().trim()) ? "[Não Informado]" : atbList.getDescricao();
											%>
												<%=exibirItem%><br>
											<%
											}
											else {
												String exibirItem = "".equals(atbList.getDescricao().trim()) ? "[Não Informado]" : atbList.getDescricao();
											%>
												<input type="checkbox" class="form_check_radio" name="itemFilho" id="<%=monitoramento%>" <%=_checked%> onclick="javascript:desmarcarTipoRelatorio(form)" value=<%=itemFilho.getCodIett()%>>
												<%=exibirItem%><br>
												<input type="hidden" name="estruturaItemFilho" value="<%=itemFilho.getEstruturaEtt().getCodEtt().toString()%>">
												<input type="hidden" name="niveisItemFilho" value="<%=niveisItemFilho%>">
											<%
											}
										}
									}
									else {
										if(possuiFiltros){
											out.println(_msg.getMensagem("itemEstrutura.relatorio.filtro.nenhumItem"));
											%><input type="hidden" name="msgErro" value="<%=_msg.getMensagem("itemEstrutura.relatorio.filtro.nenhumItem")%>"/><%				
										}else{
											out.println(_msg.getMensagem("itemEstrutura.relatorio.nenhumItem"));
											%><input type="hidden" name="msgErro" value="<%=_msg.getMensagem("itemEstrutura.relatorio.nenhumItem")%>"/><%
										}
									}
									%>
									</td>
								</tr>
								<tr>
									<td class="label_rel" colspan="2">&nbsp;</td>
								</tr>
								</table>
						
								<%
								if(!imprimirEstrutura){
								%>
									<util:barrabotoes btn3="Gerar Relatório" desabilitarBtn3="<%=desabilitar%>"/>
								<%
								}
								else {
								%>
									<util:barrabotoes btn3="Gerar Relatório" voltar="Voltar para Listagem"/>
								<%
								}
								%>
						
								<%@ include file="../../include/estadoMenu.jsp"%>
						
						</td>
					</tr>
				</table>			
			</div>
			
		</td>
	</tr>
	
</table>
	
<%
} catch (ECARException e){
	org.apache.log4j.Logger.getLogger(this.getClass()).error(e);
	Mensagem.alert(_jspx_page_context, e.getMessageKey());
%>
<script language="javascript">
	aoClicarVoltar(form);			
</script>
<% 
} catch (Exception e){
%>
	<%@ include file="/excecao.jsp"%>
<%
}
%>
</form>
</div>

</body>
<%@ include file="../../include/mensagemAlert.jsp"%>
</html>