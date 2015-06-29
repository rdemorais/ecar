<jsp:directive.page import="ecar.pojo.Link"/>
<jsp:directive.page import="ecar.dao.LinkDao"/>
<jsp:directive.page import="ecar.dao.ItemEstUsutpfuacDao"/>
<jsp:directive.page import="ecar.dao.EstruturaAcessoDao"/>

<%@ include file="/login/validaAcesso.jsp"%>
<%@ include file="/frm_global.jsp"%>

<%@ taglib uri="/WEB-INF/ecar-util.tld" prefix="util"%>
<%@ taglib uri="/WEB-INF/ecar-combo.tld" prefix="combo"%>

<%@ page import="ecar.bean.NomeImgsNivelPlanejamentoBean"%>
<%@ page import="java.util.Comparator"%>
<%@ page import="ecar.bean.AtributoEstruturaListagemItens"%>
<%@ page import="ecar.pojo.AcompReferenciaAref"%>
<%@ page import="ecar.dao.AcompReferenciaDao"%>
<%@ page import="ecar.pojo.ItemEstruturaIett"%>
<%@ page import="ecar.dao.ItemEstruturaDao"%>
<%@ page import="ecar.pojo.UsuarioUsu"%>
<%@ page import="ecar.dao.TipoFuncAcompDao"%>
<%@ page import="ecar.pojo.AcompReferenciaItemAri"%>
<%@ page import="ecar.dao.AcompReferenciaItemDao"%>
<%@ page import="ecar.pojo.AcompRelatorioArel"%>
<%@ page import="ecar.dao.CorDao"%>
<%@ page import="ecar.dao.OrgaoDao"%>
<%@ page import="ecar.pojo.OrgaoOrg"%>
<%@ page import="ecar.dao.SisAtributoDao"%>
<%@ page import="ecar.pojo.SisAtributoSatb"%>
<%@ page import="ecar.dao.ItemEstrutMarcadorDao"%>
<%@ page import="ecar.pojo.ItemEstrutMarcadorIettm"%>
<%@ page import="ecar.pojo.StatusRelatorioSrl"%>
<%@ page import="ecar.pojo.UsuarioAtributoUsua"%>
<%@ page import="ecar.pojo.TipoAcompFuncAcompTafc"%>
<%@ page import="ecar.util.Dominios" %>
<%@ page import="ecar.dao.SisGrupoAtributoDao"%>
<%@ page import="ecar.dao.ConfiguracaoDao"%>
<%@ page import="ecar.dao.AbaDao"%>
<%@ page import="ecar.dao.EstruturaAcessoDao"%>

<%@ page import="comum.util.Data"%>
<%@ page import="comum.database.Dao"%>

<%@ page import="java.util.Calendar"%>
<%@ page import="java.util.Iterator"%>
<%@ page import="java.util.Collection"%>
<%@ page import="java.util.List"%>
<%@ page import="java.util.HashSet"%>
<%@ page import="java.util.Set"%>
<%@ page import="java.util.Collections"%>
<%@ page import="java.util.Map"%>
<%@ page import="java.util.Date"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="java.util.HashMap"%>
<%@ page import="ecar.dao.EstruturaDao"%>
<%@ page import="ecar.dao.UsuarioDao"%>
<%@ page import="ecar.dao.TipoAcompanhamentoDao"%>
<%@ page import="ecar.pojo.TipoAcompanhamentoTa"%>
<%@ page import="ecar.pojo.EstruturaEtt"%>
<%@ page import="ecar.pojo.Cor"%>
<%@ page import="ecar.pojo.TipoFuncAcompTpfa"%>

<%@ page import="ecar.pojo.ItemEstUsutpfuacIettutfa" %>

<%
Date dataInicio = Data.getDataAtual();
//ConfiguracaoDao configuracaoDao = new ConfiguracaoDao(request);
%>

<html lang="pt-br">
	<head>
		<%@ include file="/include/meta.jsp"%>
		<%@ include file="/titulo.jsp"%>
		<link rel="stylesheet" href="<%=_pathEcar%>/css/style_<%=configuracaoCfg.getEstilo().getNome()%>.css" type="text/css">
		<script language="javascript" src="<%=_pathEcar%>/js/menu_retratil.js" type="text/javascript"></script>
		<script language="javascript" src="<%=_pathEcar%>/js/focoInicial.js" type="text/javascript"></script>
		<script language="javascript" src="<%=_pathEcar%>/js/popUp.js" type="text/javascript"></script>
		<script language="javascript" src="<%=_pathEcar%>/js/destaqueLinha.js" type="text/javascript"></script>
		<script type="text/javascript">
function trocaAbaSemAri(link, codIettRelacao){
	document.form.codIettRelacao.value = codIettRelacao;
	document.form.itemDoNivelClicado.value = codIettRelacao;
	SituacaoDatas();
	document.form.action = link;
	document.form.submit();
}
function trocaAba(link, codAri){
	document.forms[0].codIettRelacao.value = '';
	document.forms[0].codAri.value = codAri;
	SituacaoDatas();
	document.forms[0].action = link;	
	document.forms[0].submit();
}
function trocaAbaImpressao(link){
    SituacaoDatas();
	document.forms[0].action = link;
	document.forms[0].submit();
}
function abrirPopUpInserirAnotacao(codItem){
	abreJanela("popUp/popUpFormAnotacao.jsp?codIett=" + codItem, 500, 300, "Inserir");
}
function abrirPopUpListaAnotacao(codItem){
	abreJanela("popUp/popUpListaAnotacao.jsp?codIett=" + codItem, 500, 300, "Listar");
}
function abrirPopUpGrafico(codOrgao, codTipoAcompanhamento){
	var parametros = "niveisPlanejamento=" + document.form.niveisPlanejamento.value;
	parametros += "&orgaoEscolhido=" + document.form.orgaoEscolhido.value;
	parametros += "&periodoEscolhido=" + document.form.periodoEscolhido.value;
	if(document.form.referencia_hidden.value == null || document.form.referencia_hidden.value == ""){
		parametros += "&referencia=" + document.form.mesReferencia.value;
	}
	else{
		parametros += "&referencia=" + document.form.referencia_hidden.value;
	}
	
	if(codOrgao > 0){
		var janela = abreJanela("popUp/popUpGrafico.jsp?codOrgao=" + codOrgao + "&" + "codTipoAcompanhamento=" + codTipoAcompanhamento + "&" + parametros, 700, 550, "popupGrafico");
		janela.focus();
	} 
	else {
		var janela = abreJanela("popUp/popUpGrafico.jsp?codTipoAcompanhamento=" + codTipoAcompanhamento + "&" + parametros, 700, 550, "popupGrafico");
		janela.focus();
	}	
	
}

function abrirDadosGerais(){
	document.form.action = "dadosGerais.jsp";
	document.form.submit();	
}

function reload(){
	document.form.primeiraChamada.value="N";
	if(document.form.periodo.value != ""){
		document.form.action = "<%=idPagina%>.jsp";
		document.form.submit();		
	}
}
function reload2(){
	document.form.primeiraChamada.value="N";
	if(document.form.periodo.value != ""){
		document.form.action = "<%=idPagina%>.jsp?relatorio=true";
		document.form.submit();		
	}
}
function abrirItem(form, iett, status, qtdeFilhos){
	for(indice = 1; indice <= qtdeFilhos; indice ++) {
		var itemFilho = document.getElementById('itensFilhoDoItem'+iett+'Filho'+indice);
		if(itemFilho.style.display == "none")
			itemFilho.style.display = "";
		else
			itemFilho.style.display = "none";
	}
	form.itemDoNivelClicado.value = iett;
}

function detalharItemEstrutura(form, iett, status) {
	if(status == "true")
		form.action = "../acompanhamento/resumo/detalharItem.jsp?relatorio=" + status;
	else
		form.action = "../acompanhamento/resumo/detalharItem.jsp";
	document.form.itemDoNivelClicado.value = iett;
	document.form.codIettRelacao.value = iett;
	SituacaoDatas();
	form.submit();
}

function aoClicarConsultar(form, codigo, codIett){ 
	form.codAri.value = codigo;
	document.form.action = "../acompanhamento/relAcompanhamento/resumo/detalharItem.jsp?primeiroIettClicado=" + codIett + "&primeiroAriClicado=" + codigo;
	document.form.submit();
}
function abrirPopUpNivelPlanejamento(){

	var info = document.getElementById("semInformacaoNivelPlanejamento").value;
	if(info == ""){
		abreJanela("popUp/popUpNivelPlanejamento.jsp?opcaoSemInformacao=N", 500, 400, "Nivel");
	}
	else{
		abreJanela("popUp/popUpNivelPlanejamento.jsp?opcaoSemInformacao=" + info, 500, 400, "Nivel");
	}
}

function checkAll(idItem){
	var i = 0;
	var el = document.getElementsByTagName("INPUT");
   	var fim = el.length;
   	   	   	  		
   	if (idItem != null){
   		var idCombo = "ComboClicado" + idItem;    		
   		if(document.getElementById(idCombo).value == "")
   			document.getElementById(idCombo).value = "true";   	
   		else
  			document.getElementById(idCombo).value = "";
  			
   		while (i < fim ){
    		if (el.item(i).type  == "checkbox") {          
        		if (document.getElementById(idCombo).value == ""){        			
        			if (el.item(i).checked == true && el.item(i).id == idItem)
            			el.item(i).checked = false;            		
            	}
            	else{            		
            		if (el.item(i).checked == false && el.item(i).id == idItem)
            			el.item(i).checked = true;
            	}
        	}
        	i++;  
   		}   		
   }
   else{
   
   		if(document.getElementById('ComboClicado').value == "")
   			document.getElementById('ComboClicado').value = "true";   	
   		else
  			document.getElementById('ComboClicado').value = "";
  			
   		while (i < fim ){
    		if (el.item(i).type  == "checkbox") {          
        		if (document.getElementById('ComboClicado').value == ""){
        			if (el.item(i).checked == true)
            			el.item(i).checked = false;            		
            	}
            	else{
            		if (el.item(i).checked == false)
            			el.item(i).checked = true;
            	}
        	}
        	i++;  
   		}   
   }
}

function relatorio(){
	var selecionados = "";
	var primeiroCodAri = "";
	var i = 0;
	var el = document.getElementsByTagName("INPUT");
	var fim = el.length;
  
   	while (i < fim ){
    	if ((el.item(i).type  == "checkbox") && (el.item(i).value != "todos")) {          
        	if (el.item(i).checked == true){
         	  	selecionados = selecionados + el.item(i).value + ";";
         		if (primeiroCodAri == "")
        			primeiroCodAri = el.item(i).value;
        	}
        }        
        i++;  
   	}
   
	if (selecionados == "") {
    	alert("Não há ítens selecionados!");
   	}  	 
	else{
		document.form.codigosItem.value = selecionados;
    	document.form.action = "relAcompanhamento/relatorios/relatorioImpresso.jsp?codAri="+primeiroCodAri;
		document.form.submit();
	}
}

function SituacaoDatas(){
	var selecionados = "";
	var primeiroCodAri = "";
	var i = 0;
	var el = document.getElementsByTagName("INPUT");
	var fim = el.length;
  
   	while (i <= el.length - 1){
    	if (el.item(i).id.substr(0,5) == "item_") {
    		selecionados = selecionados + el.item(i).value + ";";
        	if (primeiroCodAri == "")
        		primeiroCodAri = el.item(i).value;        	
        }        
        i++;  
   	}

	document.form.codigosItem.value = selecionados;
	document.form.primeiroCodAri.value = primeiroCodAri;
	
}
// funcao que realiza a paginacao
function irParaPagina(form, nuPagina){
	form.nuPagina.value = nuPagina;
	document.form.action = "<%=idPagina%>.jsp";
	document.form.submit();
}

function aoClicarGrafico(){ 	
	codSecretaria = document.form.codSecretaria; 
	codTipoAcompanhamento = document.getElementById('codTipoAcompanhamento').value;
	abrirPopUpGrafico(codSecretaria, codTipoAcompanhamento);	
}

//Chama tela de filtros
function aoClicarPesquisar(){
   	codTipoAcompanhamento = document.form.codTipoAcompanhamento.value;
   	periodo = document.form.periodo.value;
   	codReferencia = document.form.mesReferencia.value;
   	document.form.action = "filtroItens.jsp?codTipoAcompanhamento="
   								+codTipoAcompanhamento+"&periodo="+periodo+"&codReferencia="+codReferencia;
	document.form.submit();
}

</script>

	</head>
<%
try {
	request.setAttribute("exibirAguarde", "true");
%>
<%@ include file="/cabecalho.jsp"%>
<%@ include file="/divmenu.jsp"%>
<%
	TipoAcompanhamentoDao taDao = new TipoAcompanhamentoDao(request);
	ValidaPermissao validaPermissao = new ValidaPermissao();

	//status indica se é relatório
	String status = Pagina.getParamStr(request, "relatorio");
	String flag = "";
	int contador = 0;
	String codTipoAcompanhamento = Pagina.getParamStr(request, "codTipoAcompanhamento");

	// controle para mostrar como default o primeiro tipo de acompanhamento das abas selecionado
	if("".equals(codTipoAcompanhamento)) {
		List listTa = taDao.getTipoAcompanhamentoAtivosComAcompanhamentoBySeqApresentacao();
		
		if(!listTa.isEmpty()) {
			codTipoAcompanhamento = ((TipoAcompanhamentoTa)listTa.get(0)).getCodTa().toString();
		}
	}

	TipoAcompanhamentoTa tipoAcompanhamento = (TipoAcompanhamentoTa) taDao.buscar(TipoAcompanhamentoTa.class, Long.valueOf(codTipoAcompanhamento));
	
	//verifica se o usuário pode "Gerar Ciclo de Acompanhamento"
	boolean permissaoAdministradorAcompanhamento = new EstruturaAcessoDao(null).temPermissoesAcessoAcomp(tipoAcompanhamento, seguranca.getGruposAcesso());															
	
%>
	<body>

		<div id="conteudo">
		
		<!-- TÍTULO -->
		<%@ include file="/titulo_tela.jsp"%>
<%
	if("".equals(codTipoAcompanhamento)) {
%>
			<table width="100%" border="0" cellpadding="0" cellspacing="0">
				<tr class="linha_subtitulo" align="left">
					<td>
						<%=_msg.getMensagem("tipoAcompanhamento.ativo.comAref.nenhumRegistro")%>
					</td>
				</tr>
			</table>

<%
	} else {
		
%>
		<util:barraLinkTipoAcompanhamentoTag
			codTipoAcompanhamentoSelecionado="<%=codTipoAcompanhamento%>"
			chamarPagina="posicaoGeral.jsp" />
<% 
		// só deverá exibir as abas de Dados Gerais, Restrições, etc quando for na tela de detalhar o item 
		if(idPagina.equals("proximosItens")) {
			if(!"true".equals(status)) { 
%>
				<util:barraLinksRelatorioAcompanhamento
					funcaoSelecionada="<%=idPagina%>" links="N"
					caminho='<%=_pathEcar + "/acompanhamento/relAcompanhamento/"%>'
					codTipoAcompanhamento="<%=codTipoAcompanhamento%>"
					gruposUsuario="<%=seguranca.getGruposAcesso() %>" 
				/>
					
<%
			} else {
%>
				<util:barraLinksRelatorioAcompanhamento
					funcaoSelecionada="relatorioImpresso" links="N"
					caminho='<%=_pathEcar + "/acompanhamento/relAcompanhamento/"%>'
					codTipoAcompanhamento="<%=codTipoAcompanhamento%>" 
					gruposUsuario="<%=seguranca.getGruposAcesso() %>" 
				/>
<%
			}
		}
%><br>
<%
	AcompReferenciaItemDao acompReferenciaItemDao = new AcompReferenciaItemDao(request);
	SisAtributoDao sisAtributoDao = new SisAtributoDao(request);
	ItemEstruturaDao itemDao = new ItemEstruturaDao(request);
	AcompReferenciaDao acompReferenciaDao = new AcompReferenciaDao(request);
	TipoFuncAcompDao tipoFuncAcompDao = new TipoFuncAcompDao(request);
	EstruturaDao estruturaDao = new EstruturaDao(request);
	UsuarioDao usuarioDao = new UsuarioDao(request);
	OrgaoDao orgaoDao = new OrgaoDao(request);
	ItemEstrutMarcadorDao marcadorDao = new ItemEstrutMarcadorDao(request);
	CorDao corDao = new CorDao(request);
	AbaDao abaDao = new AbaDao(request);
	
	// Pega da configuração para saber o número de itens para paginação
	int nuPagina = 1; //qual a pagina que esta sendo visualizada
	if(Pagina.getParamStr(request, "nuPagina") != null && !Pagina.getParamStr(request, "nuPagina").equals("")) {
		nuPagina = Integer.parseInt(Pagina.getParamStr(request, "nuPagina"));
	} else if(request.getAttribute("nuPagina") != null && !request.getAttribute("nuPagina").equals("")) {
		nuPagina = Integer.parseInt((String)request.getAttribute("nuPagina"));
	}
	int nuItensPaginacao = configuracao.getNuItensExibidosPaginacao(); //numero de itens a serem exibidos por pagina
	int nuItensTotal = 0;
	
	List tpfaOrdenadosPorEstrutura = tipoFuncAcompDao.getFuncaoAcompOrderByEstruturas();
	
	String exigeLiberarAcompanhamento = tipoAcompanhamento.getIndLiberarAcompTa();
	EstruturaEtt menorEttCfg = null;
	int menorNivel = -1;
	String itemDoNivelClicado = "";
	
	if("posicaoGeral".equals(idPagina)) {
		if(configuracao != null){
			menorEttCfg = tipoAcompanhamento.getEstruturaEtt();
			if(menorEttCfg != null){
				menorNivel = estruturaDao.getNivel(menorEttCfg);
			}
		}
	} else if("proximosItens".equals(idPagina)) {
		itemDoNivelClicado = Pagina.getParamStr(request, "itemDoNivelClicado");

		if(itemDoNivelClicado != null && !"".equals(itemDoNivelClicado)){
			menorEttCfg = ((ItemEstruturaIett)(itemDao.buscar(ItemEstruturaIett.class, Long.valueOf (itemDoNivelClicado)))).getEstruturaEtt();
			if(menorEttCfg != null){
				//Menor nivel = nivel do item escolhido na tela anterior + 1
				menorNivel = estruturaDao.getNivel(menorEttCfg) + 1;
			}
		}
	}
	
	//Lista todos os AREF's (ciclos de referência) por tipo de acompanhamento(esse tipo de acompanhamento foi passado pelo request)
	List acompanhamentos = acompReferenciaDao.getListAcompReferenciaByTipoAcompanhamento(Long.valueOf(codTipoAcompanhamento));
	
	//Lista o AREF mais próximo da data atual
	//ATENÇÃO: Depois, esse Aref poderá ser passado via request 
	AcompReferenciaAref arefAtual = acompReferenciaDao.getAcompSelecionado(acompanhamentos);
	
	String strLabelOrgao = estruturaDao.getLabelPadraoOrgaoResponsavel();
	
	UsuarioUsu usuario = ((ecar.login.SegurancaECAR)session.getAttribute("seguranca")).getUsuario();
	
	//Níveis de Planejamento por usuário -> Lista em strAux os CodSatb separados por ":"  
	List niveisUsuario = usuarioDao.getNiveisPlanejamentoUsuario(usuario);
	Iterator itNiveisUsu = niveisUsuario.iterator();
	StringBuffer strAux = new StringBuffer("");	
	while(itNiveisUsu.hasNext()) {
		UsuarioAtributoUsua usuAtributo = (UsuarioAtributoUsua) itNiveisUsu.next();
		strAux.append(usuAtributo.getSisAtributoSatb().getCodSatb()); 
		strAux.append(":");
	}
	String strNiveisPlanejamentoUsuario = strAux.toString();
	
	//Seta o ciclo (1 Ciclo, 2 Ciclo, etc..)
	String periodo = "";	
	String periodoEscolhido="";
	if(!"true".equals(status))
		periodo = Pagina.getParamStr(request, "periodo");
	else
		periodo = "1";		
	if("".equals(periodo))
		periodo = "1";
	periodoEscolhido = periodo + (("1".equals(periodo)) ? " Ciclo" : " Ciclos");
	
	//Pega no request (como String q na primeira iteração são vazios) o "mesReferencia" e o "niveisPlanejamento"
	String mesReferencia = Pagina.getParamStr(request, "mesReferencia");		
	String niveisPlanejamento = Pagina.getParamStr(request, "niveisPlanejamento");
	
	// Seta "niveisPlanejamento" com os códigos do sisAtributo separados por ":" ex.:(64:59:), isso por usuário
	/* Verifica a exibição do filtro Nível Planejamento se não mostrar selecionar TODOS */
	if (abaDao.verificaNivelPlanejamento("P")) {
		if("".equals(niveisPlanejamento)) {
			niveisPlanejamento = strNiveisPlanejamentoUsuario;
		}
	} else {
		String strAux2 = "";
		List listNiveisPlanejamento = new SisGrupoAtributoDao().getAtributosOrdenados(configuracao.getSisGrupoAtributoSgaByCodSgaGrAtrNvPlan());
		Iterator it = listNiveisPlanejamento.iterator();
		while(it.hasNext()){
			SisAtributoSatb nivel = (SisAtributoSatb) it.next();
			
			strAux2 += nivel.getCodSatb() + ":";
		}
		if("".equals(niveisPlanejamento)){
			niveisPlanejamento = strAux2.toString();
		}
	}
	
	//Seta o "orgaoResponsavel" com o valor do request
	OrgaoOrg orgaoResponsavel = new OrgaoOrg();
	if(!"".equals(Pagina.getParamStr(request, "orgaoResponsavel"))){
		orgaoResponsavel = (OrgaoOrg) orgaoDao.buscar(OrgaoOrg.class, Long.valueOf( Pagina.getParamStr(request, "orgaoResponsavel")));
	}
	//Se "orgaoResponsavel" for igual a null-> seta "orgaoEscolhido" como "Todos"
	String orgaoEscolhido = (orgaoResponsavel.getCodOrg() != null) ? orgaoResponsavel.getSiglaOrg() : "Todos";	
	
	//?Acho que não está usando?
	boolean comboOrgao = true;
	
	String semInformacaoNivelPlanejamento = Pagina.getParamStr(request, "semInformacaoNivelPlanejamento");
	
	AcompReferenciaItemAri ariHidden = null;
	
	if(!itemDoNivelClicado.trim().equals("") && itemDoNivelClicado != null ){
		AcompReferenciaItemDao ariDao = new AcompReferenciaItemDao(request);
		ItemEstruturaDao itemEstruturaDao = new ItemEstruturaDao(request);	
		ItemEstruturaIett iett = (ItemEstruturaIett)itemEstruturaDao.buscar(ItemEstruturaIett.class, Long.valueOf (itemDoNivelClicado));
		ariHidden = ariDao.getAcompReferenciaItemByItemEstruturaIett(arefAtual,iett);		
	}
%>
			<form name="form" method="post" action="">
				<input type="hidden" name="nuPagina" value="<%=nuPagina%>">
				<input type="hidden" name="codTipoAcompanhamento" value="<%=codTipoAcompanhamento%>">						
		  		<% 
		  		if(ariHidden != null){ %>
			  		<input type="hidden" name="codAri" value="<%=ariHidden.getCodAri()%>">								
					<input type="hidden" name="primeiroAriClicado" value="<%=ariHidden.getCodAri()%>">
				<%} else {%>
			  		<input type="hidden" name="codAri" value="">								
					<input type="hidden" name="primeiroAriClicado" value="">				
				<%} 				
				if(!itemDoNivelClicado.trim().equals("") && itemDoNivelClicado != null){ %>
					<input type="hidden" name="primeiroIettClicado" value="<%=itemDoNivelClicado%>">					
				<%} else {%>
					<input type="hidden" name="primeiroIettClicado" value="">
				<%}%>																
				<input type="hidden" name="niveisPlanejamento" value="<%=niveisPlanejamento%>">
				<input type="hidden" name="primeiraChamada" value='<%=Pagina.getParamStr(request, "primeiraChamada")%>'>
				<input type="hidden" name="orgaoEscolhido" value="<%=orgaoEscolhido%>">
				<input type="hidden" name="periodoEscolhido" value="<%=periodoEscolhido%>">				
				<input type="hidden" name="ComboClicado" id="ComboClicado" value="">
				<input type="hidden" name="referencia_hidden" value="<%=mesReferencia%>">
				<input type="hidden" name="referencia" value="<%=mesReferencia%>">
				<input type="hidden" name="itemDoNivelClicado" value="<%=itemDoNivelClicado%>">
				<input type="hidden" name="semInformacaoNivelPlanejamento" id="semInformacaoNivelPlanejamento" value="<%=semInformacaoNivelPlanejamento%>">
				<input type="hidden" name="codIettRelacao" value='<%=Pagina.getParamStr(request, "codIettRelacao")%>'>
				<input type="hidden" name="codigosItem" value="">
				<input type="hidden" name="primeiroCodAri" value="">
				<input type="hidden" name="codArisControle" value="">
				<input type="hidden" name="codArisControleVisualizacao" value="">
				

<%
		// se não tiver tipo de acompanhamento selecionado, não carregar dados,
		// ver "else" no fim do arquivo
		if(!"".equals(codTipoAcompanhamento)) {
%>
				<table border="0" width="100%">
					<% if("true".equals(status)){ %>
					<tr>
						<input type="button" value="Prosseguir..." class="botao"
							onclick="javascript:relatorio();">
					</tr>
					<% } %>
					<tr>
						<td valign="bottom" class="texto" align="left">

							<!-- COMBO DE PERÍODOS DE EXIBIÇÃO -->
							<b>Ciclo de Exibição:</b>
							<select name="periodo" onchange="reload()"
								<% if("true".equals(status)){ %> disabled <% } %>>
								<option value="">
									--SELECIONE--
								</option>
								<% 
								for(int i=1;i<=12;i++){ 
									Integer inteiro = new Integer(i);
								%>
									<option value="<%=i%>"
										<%if(inteiro.toString().equals(periodo)) out.println("selected");%>>
										<%=inteiro%> Ciclo
									</option>
							  <%}%>
							</select>
						</td>
						<td align="right" valign="bottom" class="texto">
							<% /* Verifica a exibição do filtro Nível Planejamento */
						if (abaDao.verificaNivelPlanejamento("P")) { %>
							<% if(!"true".equals(status)){ %>
							<a href="javascript:abrirPopUpNivelPlanejamento()"
								title="Selecionar <%=configuracao.getSisGrupoAtributoSgaByCodSgaGrAtrNvPlan().getDescricaoSga()%>"><img
									src="<%=_pathEcar%>/images/relAcomp/icon_nivelplanejamento.png"
									border="0" alt="">
							</a>
							<% } %>
						<% } %>
							<br>
							Data do relatório:
							<b><%=Pagina.trocaNullData(Data.getDataAtual())%></b>
							<br><br>
							<b>Ciclo de referência: </b>
							<!-- COMBO DE PERÍODOS DE REFERÊNCIA -->
							<select name="mesReferencia" <% if(!"true".equals(status)){ %>
								onchange="javascript:reload()" <% }
								else { %>
								onchange="javascript:reload2()" <%}%>>
								<option value="0"
								<%
								if("0".equals(mesReferencia)){
								%> selected
								<%
								}
								%>>
									--SELECIONE--
								</option>
								<%
							String selMesRef = "";
	
							Iterator itAcomp = acompanhamentos.iterator();
							while(itAcomp.hasNext()){
								AcompReferenciaAref acompReferencia = (AcompReferenciaAref) itAcomp.next();
								
								String codReferencia = Pagina.getParamStr(request, "codReferencia");
								if(!codReferencia.equals("") && acompReferencia.getCodAref().equals(new Long(codReferencia))){
									selMesRef = "selected";
									mesReferencia = Pagina.getParamStr(request, "codReferencia");
							%>
									<script language="Javascript" type="text/javascript">
										document.form.referencia_hidden.value = "<%=mesReferencia%>";
										document.form.referencia.value = "<%=mesReferencia%>";
									</script>								
						<%		}
								else if(!"0".equals(mesReferencia) && mesReferencia.equals(acompReferencia.getCodAref().toString())){
									selMesRef = "selected";
									mesReferencia = acompReferencia.getCodAref().toString();
							%>
									<script language="Javascript" type="text/javascript">
										document.form.referencia_hidden.value = "<%=mesReferencia%>";
										document.form.referencia.value = "<%=mesReferencia%>";
									</script>
							<%
								}
								else if((mesReferencia == null || "".equals(mesReferencia)) && (arefAtual != null && arefAtual.equals(acompReferencia))) {
									selMesRef = "selected";
									mesReferencia = acompReferencia.getCodAref().toString();
							%>
									<script language="Javascript" type="text/javascript">
										document.form.referencia_hidden.value = "<%=mesReferencia%>";
										document.form.referencia.value = "<%=mesReferencia%>";										
									</script>
							<%
								} else {
									selMesRef = "";
								}
								
								StringBuffer opcaoCombo = new StringBuffer(acompReferencia.getMesAref())
														.append("/").append(acompReferencia.getAnoAref())
														.append(" - ").append(acompReferencia.getNomeAref());
								
								if(acompReferencia.getOrgaoOrg() != null){
									opcaoCombo.append(" - ").append(acompReferencia.getOrgaoOrg().getSiglaOrg());
								}
							%>
								<option value="<%=acompReferencia.getCodAref()%>" <%=selMesRef%>>
									<%=opcaoCombo%>
								</option>
							<%
							} %>
							</select>
						</td>
					</tr>
				</table>
			<%
			if((mesReferencia != null && !"".equals(mesReferencia)) && !"".equals(periodo)){	
				List listNiveis = new ArrayList();
				String[] niveis = niveisPlanejamento.split(":");
				for(int n = 0; n < niveis.length; n++){
					String codNivel = niveis[n];
					
					if(!"".equals(codNivel)){
						listNiveis.add(sisAtributoDao.buscar(SisAtributoSatb.class, Long.valueOf(codNivel)));
					}
				}
	
				/* Busca coleção com todas as secretarias que possuem algum acompanhamento de referência cadastrado */
				Collection secretarias = acompReferenciaDao.getOrgaosComAcompanhamentosCriados(codTipoAcompanhamento);
				List listFunAcomp = tipoFuncAcompDao.getTipoFuncAcompEmitePosicao();
				
				StatusRelatorioSrl statusLiberado = (StatusRelatorioSrl) itemDao.
		                         buscar(StatusRelatorioSrl.class, Long.valueOf(AcompReferenciaItemDao.STATUS_LIBERADO));
				
				boolean primeiro = true;		
						
				Iterator itSecretarias = secretarias.iterator();
				
				if("true".equals(status)){ 
%>
				<br>
				<table border="0" width="100%">
					<tr>
						<td class="texto">
							<b>Selecione os ítens que deseja imprimir:</b>
						</td>
					</tr>
				</table>
<%
				}
				
				while(itSecretarias.hasNext() || primeiro) {
					/* Na primeira vez, atribui null à secretaria para imprimir informação de periodos EM MONITORAMENTO */
					OrgaoOrg secretaria = null;
								
					if(primeiro){
						secretaria = null;
					}else{
						secretaria = (OrgaoOrg) itSecretarias.next();
					}
					
					Long codArefReferencia = Long.valueOf(mesReferencia);
					int qtdePeriodosAnteriores = Integer.valueOf(periodo).intValue();
					
					/* Busca coleção com ciclos a serem considereados */
					Collection periodosConsiderados = new ArrayList();
					
					if(codArefReferencia.intValue() > 0) {
						periodosConsiderados = acompReferenciaDao.getPeriodosAnterioresOrdenado(codArefReferencia, qtdePeriodosAnteriores,  Long.valueOf(codTipoAcompanhamento), false);
					}
					
					/* seta na sessão coleção de periodos, para ser utilizado no grafico */
					if(secretaria == null)
						session.setAttribute("periodosConsiderados", periodosConsiderados);
					else
						session.setAttribute("periodosConsiderados" + secretaria.getCodOrg(), periodosConsiderados);

					Long codIettPai = null;
					
					if("proximosItens".equals(idPagina)) {
						codIettPai = Long.valueOf(itemDoNivelClicado);
					}

					Boolean isSemInformacaoNivelPlanejamento = new Boolean(false);
					if((Dominios.SIM).equals(semInformacaoNivelPlanejamento) || !abaDao.verificaNivelPlanejamento("P")) {
						isSemInformacaoNivelPlanejamento = new Boolean(true);
					}
					
					Object itens[] = null;
					//if (session.getAttribute("listaItensEstrutura") != null){
					//	itens = (Object[]) session.getAttribute("listaItensEstrutura");
					//	session.removeAttribute("listaItensEstrutura");
					String hidAcao = Pagina.getParam(request, "hidAcao");
					if (Pagina.getParamStr(request, "hidAcao").equals("pesquisarFiltros")) {
						//-------------------------------------------
						String codTipoAcompanhamentoEscolhido =	Pagina.getParamStr(request, "codTipoAcompanhamento");
						//ciclo referência
						String referenciaEscolhidaFiltro = Pagina.getParamStr(request, "mesReferencia");
						//ciclo exibição	
						String periodoEscolhidoFiltro = Pagina.getParamStr(request, "periodo");
						String msg = null;
				
						String[] cores  = request.getParameterValues("cor");
				        Collection lCoresSelecionadas = new ArrayList();
				       
				        if (cores != null) {
				        	for (int i = 0; i < cores.length; i++) {
				            	Cor cor = (Cor) corDao.buscar(Cor.class, Long.valueOf(cores[i]));
				                lCoresSelecionadas.add(cor);
				        	}
						}
		
						String[] tipoFuncoesAcompanhamento  = request.getParameterValues("tipoFuncAcompTpfa");
									Collection lFuncoesAcompanhamentoSelecionadas = new ArrayList();
							
						if (tipoFuncoesAcompanhamento != null) {
				        	for (int i = 0; i < tipoFuncoesAcompanhamento.length; i++) {
				            	TipoFuncAcompTpfa tipoFuncAcompTpfa = (TipoFuncAcompTpfa) tipoFuncAcompDao.buscar(TipoFuncAcompTpfa.class, Long.valueOf(tipoFuncoesAcompanhamento[i]));
				                lFuncoesAcompanhamentoSelecionadas.add(tipoFuncAcompTpfa);
				            }
				        }
				
				
						//pega os ciclos considerados de acordo com o periodo de referencia e a quantidade de periodos
						//anteriores a esta referencia (periodos exibição)
						
						Long codArefReferenciaFiltro;
						int qtdePeriodosAnterioresFiltro;
						/* Busca coleção com ciclos a serem considereados */
						Collection periodosConsideradosFiltros = new ArrayList();
				
						if (referenciaEscolhidaFiltro != null && referenciaEscolhidaFiltro != ""){
							codArefReferenciaFiltro= Long.valueOf(referenciaEscolhidaFiltro);
							qtdePeriodosAnterioresFiltro = Integer.valueOf(periodoEscolhidoFiltro).intValue();
							
							if(codArefReferenciaFiltro.intValue() > 0) {
								periodosConsideradosFiltros = acompReferenciaDao.getPeriodosAnteriores(codArefReferenciaFiltro, qtdePeriodosAnterioresFiltro,  Long.valueOf(codTipoAcompanhamentoEscolhido));
							}
						
							if(qtdePeriodosAnterioresFiltro > 0) {
								periodosConsideradosFiltros = acompReferenciaDao.getPeriodosAnteriores(codArefReferenciaFiltro, qtdePeriodosAnterioresFiltro,  Long.valueOf(codTipoAcompanhamentoEscolhido));
							}
							
						}
	
						List niveisUsuarioFiltro = usuarioDao.getNiveisPlanejamentoUsuario(usuario);
						Iterator itNiveisUsuFiltro = niveisUsuario.iterator();
						StringBuffer strAuxFiltro = new StringBuffer("");
						
						while(itNiveisUsuFiltro.hasNext()) {
							UsuarioAtributoUsua usuAtributo = (UsuarioAtributoUsua) itNiveisUsuFiltro.next();
							strAuxFiltro.append(usuAtributo.getSisAtributoSatb().getCodSatb()); 
							strAuxFiltro.append(":");
						}
						String strNiveisPlanejamentoUsuarioFiltro = strAux.toString();
		
		
						itens =	acompReferenciaItemDao.getItensAcompanhamentoFiltroItens(
							new Long(codTipoAcompanhamentoEscolhido),
							periodosConsideradosFiltros,
							lCoresSelecionadas,
							lFuncoesAcompanhamentoSelecionadas,
							usuario,
							seguranca.getGruposAcesso(),
							listNiveis,
							isSemInformacaoNivelPlanejamento,
							orgaoResponsavel,
							request //MODIFICAR POR LISTA DE ESTRUTURA ATRIBUTOS FILTROS
							);

						//-------------------------------------------- 
					} else {
					
						itens = acompReferenciaItemDao.getItensAcompanhamentoInPeriodosByOrgaoRespPaginado(
											periodosConsiderados, listNiveis, orgaoResponsavel, 
											usuario, seguranca.getGruposAcesso(), 
											Long.valueOf(codTipoAcompanhamento), codIettPai,
											isSemInformacaoNivelPlanejamento, null, null, -2, nuPagina, null);
					}
					 
					/* Recupera lista de itens para os periodos considerados por orgão responsavel */
					
 
					List itensAcompanhamentos = new ArrayList((Collection)itens[0]);
					List itensGeral = new ArrayList((Collection)itens[1]);
						
					/* Se for encontrado algum periodo, imprime lista de itens */
					if(itensAcompanhamentos != null && itensAcompanhamentos.size() > 0) {
%>
				<br>
				<div id="subconteudo" align="right">
					<%
					LinkDao linkDao = new LinkDao(request);
					List links = linkDao.listar(Link.class, new String[]{"codLink", "asc"});
					Iterator itLinks = links.iterator();
										
					if(secretaria == null){
					%>
						<input type="hidden" name="codSecretaria" value="0">												
<!--  					
					<a href="javascript:abrirPopUpGrafico(0)"
						title="Gráfico de Evolução das Posições"><img
							src="< %=_pathEcar%>/images/relAcomp/icon_grafico.png" border="0"
							alt="">
					</a>
-->
					<%} else { %>						
						<input type="hidden" name="codSecretaria" value="<%=secretaria.getCodOrg()%>">						
<!--  					<a href="javascript:abrirPopUpGrafico(< %=secretaria.getCodOrg()%>)"
						title="Gráfico de Evolução das Posições"><img
							src="< %=_pathEcar%>/images/relAcomp/icon_grafico.png" border="0"
							alt="">
					</a>
-->
					<%}%>
					<util:barraLinks					
						_pathEcar="<%= _pathEcar%>"
						verificaMonitoramento="<%=new Boolean(true) %>" />
					
					<table border="0" cellpadding="0" cellspacing="0" width="100%">
						<tr>
							<td class="espacador"
								colspan="<%=periodosConsiderados.size() + 6%>">
								<img src="<%=_pathEcar%>/images/pixel.gif" alt="">
							</td>
						</tr>
						<!-- TÍTULO DA TABELA -->
						<tr class="linha_titulo">
							<td colspan="<%=periodosConsiderados.size() + 6%>">
								<%
							 	if(secretaria != null) {
							 		out.println(secretaria.getDescricaoOrg());
							 	}
								%>
							</td>
						</tr>
						<tr>
							<td class="espacador"
								colspan="<%=periodosConsiderados.size() + 6%>">
								<img src="<%=_pathEcar%>/images/pixel.gif" alt="">
							</td>
						</tr>
						<!-- CABEÇALHO DA TABELA -->
						<tr class="linha_subtitulo">
							<td width="1%"></td>
							<td width="50%" colspan="2">Acompanhamentos</td>
							<!-- o órgão escolhido vem da tela de filtro -->
							<td width="10%" align="center" nowrap><%=strLabelOrgao%>:<br><%=orgaoEscolhido%></td>
							<td width="5%" align="center" colspan="2">Ação</td>
							<%
								/* Imprime o nome dos ciclos */
								boolean ehPrimeiro = true;
								Iterator it = periodosConsiderados.iterator();
								double tamanhoColuna = 38 / periodosConsiderados.size();
								while(it.hasNext()){
									AcompReferenciaAref acompanhamento = (AcompReferenciaAref) it.next();
									String estilo = "";
									//coloca estilo somente na referencia selecionada
									if(ehPrimeiro){
										estilo = "class=\"corSelecao\"";
										ehPrimeiro = false;
									}
							%>
							<td id="selecionado" <%=estilo%> align="center" width="<%=tamanhoColuna%>%">
							<%=acompanhamento.getNomeAref()%> 
							</td>
							<%							
								}					
								if("true".equals(status)){
							%>
							<td align="center">
								Todos
								<input type="checkbox" value="todos"
									id="<%if(secretaria != null)%><%=secretaria.getCodOrg()%>"
									onClick="checkAll(<%if(secretaria != null)%><%=secretaria.getCodOrg()%>)">
								<% if(secretaria != null){ %>
								<input type="hidden"
									id="ComboClicado<%=secretaria.getCodOrg()%>" value="">
								<% } %>
							</td>
							<%
								}
							%>
						</tr>
						<tr>
							<td class="espacador"
								colspan="<%=periodosConsiderados.size() + 6%>">
								<img src="<%=_pathEcar%>/images/pixel.gif" alt="">
							</td>
						</tr>
						<!-- Aqui começam a ser imprimidos os itens de acompanhamento -->
						<!-- DADOS DA TABELA -->
						<%
								boolean acaoLapis = true; // mudar de acordo com a regra de exibicao de acao
								Map dadosGrafico = new HashMap();
								List itensSessao = new ArrayList();
								List itensSessaoVisualizar = new ArrayList();
															
								/* Itera pelos itens */
								Iterator itItens = itensAcompanhamentos.iterator();
		
								while(itItens.hasNext()){
									
									if(request.getAttribute("itensSessao")!=null){
										itensSessao = (List)request.getAttribute("itensSessao");
										itensSessaoVisualizar = (List)request.getAttribute("itensSessaoVisualizar");
									}
									
									AtributoEstruturaListagemItens aeIett = (AtributoEstruturaListagemItens) itItens.next();
									
									ItemEstruturaIett item = aeIett.getItem();
									AcompReferenciaItemDao ariDao = new AcompReferenciaItemDao(request);
																		
									String codReferencia = Pagina.getParamStr(request, "codReferencia");
									if(codReferencia.equals("")){
										codReferencia = mesReferencia;
									}									
									AcompReferenciaAref arefSelecionada = (AcompReferenciaAref) acompReferenciaDao.buscar(AcompReferenciaAref.class, Long.valueOf(codReferencia));   
									AcompReferenciaItemAri acompAri = ariDao.getAcompReferenciaItemByItemEstruturaIett(arefSelecionada,item);
									
									String linkAbrirIettComeco = "";
									String linkAbrirIettFim = "";
									String linkAbrirArvoreIettComeco = "";
									String linkAbrirArvoreIettFim = "";
																		
									if("posicaoGeral".equals(idPagina)) {
										//Verificar para mostrar itens até o menor nivel configurado nas configurações gerais...
										//Se menorNivel == -1, não foi informado nehuma estrutura, então ignora esta validação
										int nivelEtt = item.getNivelIett().intValue();
										if(menorNivel != -1 && nivelEtt > menorNivel) {
											continue;
										}
									}

									//criação do link para detalhamento do item 
									linkAbrirIettComeco = "<a href=\"javascript:detalharItemEstrutura(form, '" + item.getCodIett() + "', '" + status + "')\" title=\"Editar\">";
									linkAbrirIettFim = "</a>";

									List filhosExibir = new ArrayList();
									if(menorNivel != -1){
										List filhos = itemDao.getDescendentesViaQry(item);
										if(filhos != null && filhos.size() > 0){
											Iterator itFilhos = filhos.iterator();
											while(itFilhos.hasNext()){
												ItemEstruturaIett filho = (ItemEstruturaIett) itFilhos.next();
												if(itensGeral.contains(filho)){
													if(filho.getNivelIett().intValue() > menorNivel) {
														filhosExibir.add(filho);
													}
													if(!idPagina.equals("proximosItens")) {
														//criação do link para abrir a arvore de itens do item
														linkAbrirArvoreIettComeco = "<a href=\"javascript:abrirItem(form, '" + item.getCodIett() + "', '" + status + "',"+filhosExibir.size()+")\">";
														linkAbrirArvoreIettFim = "</a>";
													}
												}
											}
										}
									}
		%>
						<!-- começa a imprimir a linha do item -->
						<tr class="cor_sim"
							onmouseover="javascript:destacaLinha(this,'over','')"
							onmouseout="javascript: destacaLinha(this,'out','cor_sim')"
							onClick="javascript: destacaLinha(this,'click','cor_sim')"
							bgcolor="<%=item.getEstruturaEtt().getCodCor3Ett()%>">
							<!-- coluna imprime se tiver algum marcador (anotação para o item) -->
							<td>
		<%							if(!"true".equals(status)){
										ItemEstrutMarcadorIettm marcador = marcadorDao.getUltimoMarcador(item, usuario);
										if(marcador != null){
		%>
								<a
									href="javascript:abrirPopUpListaAnotacao(<%=item.getCodIett()%>)" title="Lembrete">
		<%								if(marcador.getCor() != null){ %> <img
											src='<%=_pathEcar%>/images/relAcomp/<%=marcador.getCor().getNomeCor().toLowerCase()%>_inf.png'
											border='0' alt=""> <%
										} else { %> <img
											src='<%=_pathEcar%>/images/relAcomp/branco_inf.png' border='0'
											alt=""> <%
										} %> </a>
		<%							}									
								} %>
							</td>
							<!-- termina de imprimir a coluna do marcador -->

							<!-- começa a imprimir a coluna que faz a identação de acordo com o nivel do item -->
							<td>
								<table>
									<tr bgcolor="<%=item.getEstruturaEtt().getCodCor3Ett()%>">
										<td nowrap>
											&nbsp;
		<%
								/* Identação pelo nível do item */
								int nivel = item.getNivelIett().intValue();
								for(int i = 1; i < nivel;i++) {
		%>							<img src="<%=request.getContextPath()%>/images/pixel.gif"
										width="22" height="9" alt="">
		<%
								}
		%>
								</td>
								<td valign="top"> 
										
		<%						
								// Verificar se a imagem é de + ou -							
								String caminhoImagem = "";
								if(linkAbrirArvoreIettFim.equals("")) {
									//Quando nao puder expandir o item, a imagem mostrada sera a de -
									caminhoImagem = "/images/expanded_button.gif";
								} else {
									//Quando puder expandir o item, a imagem mostrada sera a de +
									caminhoImagem = "/images/collapsed_button.gif";
								}
								
				
		%>								
										<%=linkAbrirArvoreIettComeco%><img src="<%=_pathEcar%><%=caminhoImagem%>" alt="" border="0"><%=linkAbrirArvoreIettFim%>
										</td>
										<td title="<%=item.getEstruturaEtt().getNomeEtt().trim()%>">
											<font color="<%=item.getEstruturaEtt().getCodCor4Ett()%>">
											<%
											String nomeIett = "".equals(aeIett.getDescricao()) ? "[Não Informado]" : aeIett.getDescricao();
											%><%=nomeIett%>
											</font>
										</td>
									</tr>
								</table>
							</td>
							<!-- termina de imprimir a coluna que faz a identação de acordo com o nivel do item -->

							<!-- começa a imprimir as imagens para NivelPlanejamento -->
							<td nowrap>
								<%
									Iterator itNiveis = itemDao.getNomeImgsNivelPlanejamentoItemAgrupado(item).iterator();
									int contNivel = 0;
									while(itNiveis.hasNext()){
										NomeImgsNivelPlanejamentoBean imagemNivelPlanejamento = (NomeImgsNivelPlanejamentoBean) itNiveis.next();
										out.print(imagemNivelPlanejamento.geraHtmlImg(request));
									}
								%>
							</td>
							<!-- termina de imprimir as imagens para NivelPlanejamento -->

							<!-- começa a imprimir a coluna com o nome do órgão responsável pelo item -->
							<td align="center">
								<%
									if(item.getOrgaoOrgByCodOrgaoResponsavel1Iett() != null){
										if(item.getOrgaoOrgByCodOrgaoResponsavel1Iett().getSiglaOrg() != null)
											out.println(item.getOrgaoOrgByCodOrgaoResponsavel1Iett().getSiglaOrg());	
										else
											out.println(item.getOrgaoOrgByCodOrgaoResponsavel1Iett().getDescricaoOrg());	
									} else {
										/* Se não possuir orgao procura orgao do pai */
										ItemEstruturaIett itemAux = item;
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
							<!-- termina de imprimir a coluna com o nome do órgão responsável pelo item -->
							
							<!-- começa a imprimir a coluna de anotação -->
							<td>
								<% if(!"true".equals(status)){ %>
								<a href="javascript:abrirPopUpInserirAnotacao(<%=item.getCodIett()%>)"
									title="Inserir anotação"> <img
									src="<%=_pathEcar%>/images/relAcomp/icon_anotacoes.png"
									border="0" alt=""> </a>
								<% } %>
							</td>
							<!-- termina de imprimir a coluna de anotação -->
							
							<!-- começa a imprimir a coluna de ação -->
							<td align="center">
							<% 
							
							//INÍCIO DA LÓGICA DE PERMISSÃO DE REGISTRO DE PARECER
							boolean usuarioLogadoEmiteParecer = false;
							boolean permissaoLapis = false;
							
							if(permissaoAdministradorAcompanhamento){
								permissaoLapis = true;
							}																				
																				
							//Dao do objeto principal(ItemEstUsutpfuac) onde veremos a permissão do usuário/grupo 
							ItemEstUsutpfuacDao itemEstUsuDao = new ItemEstUsutpfuacDao(request);																					
							Iterator itPeriodosAcao = periodosConsiderados.iterator();
							Map  mapAcao = acompReferenciaItemDao.criarMapPeriodoAri(periodosConsiderados, item);

							if(itPeriodosAcao.hasNext()) {
								//Pega só o ciclo selecionado (Aref), que é o primeiro
								AcompReferenciaAref acompReferencia = (AcompReferenciaAref) itPeriodosAcao.next();
								if(!mapAcao.isEmpty() && mapAcao.containsKey(acompReferencia)) {
									AcompReferenciaItemAri ariAcao = (AcompReferenciaItemAri) mapAcao.get(acompReferencia);
									
									//Pega os Arels do Ari selecionado 
									List relatorios = acompReferenciaItemDao.getAcompRelatorioArelOrderByFuncaoAcomp(ariAcao, tpfaOrdenadosPorEstrutura);
									Iterator itRelatorios = relatorios.iterator();
									
									while(itRelatorios.hasNext()){												
										AcompRelatorioArel relatorio = (AcompRelatorioArel) itRelatorios.next();										
										
										ItemEstUsutpfuacIettutfa itemEstUsu 
												= itemEstUsuDao.buscar(item.getCodIett(), relatorio.getTipoFuncAcompTpfa().getCodTpfa());
										 
										//Verifica se a permissão é de grupo ou usuário							 							
										if(itemEstUsu != null) {
											if (itemEstUsu.getUsuarioUsu() != null) {
												usuarioLogadoEmiteParecer = itemEstUsu.getUsuarioUsu().getCodUsu().equals(usuario.getCodUsu());
											} else if (itemEstUsu.getSisAtributoSatb() != null) {
												if (itemEstUsu.getSisAtributoSatb().getUsuarioAtributoUsuas() != null) {
													Iterator itUsuarios = itemEstUsu.getSisAtributoSatb().getUsuarioAtributoUsuas().iterator();
													while (itUsuarios.hasNext()) {
														UsuarioAtributoUsua usuarioAtributoUsua = (UsuarioAtributoUsua) itUsuarios.next();
														if (usuarioAtributoUsua.getUsuarioUsu().getCodUsu().equals(usuario.getCodUsu())){
															usuarioLogadoEmiteParecer = true;
															break;
														}
													}
												}
											}
										}	
										if(usuarioLogadoEmiteParecer==true)
											break;											
									}//fecha while relatórios																
								}
							}

							// Não coloca o lápis para o primeiro nível da estrutura 
							if((usuarioLogadoEmiteParecer || permissaoLapis) && item.getNivelIett().intValue()>1 && acompAri != null) { %>
								<!-- ADICIONAR A IMAGEM DO LÁPIS -->
								<%=linkAbrirIettComeco%><img
									src="<%=_pathEcar%>/images/icon_alterar.png"
									border="0" alt=""></a>
							<%} %>
							</td>
							<!-- termina de imprimir a coluna de ação -->
							
							<!-- começa a imprimir os pareceres -->
							<%		Iterator itPeriodos = periodosConsiderados.iterator();
									//map utilizado para recuperar o Ari
									Map  map = acompReferenciaItemDao.criarMapPeriodoAri(periodosConsiderados, item);
									while(itPeriodos.hasNext()) { 
										AcompReferenciaAref acompReferencia = (AcompReferenciaAref) itPeriodos.next();										
										if(map.isEmpty()) {
							%>
							<td align="center">&nbsp;</td>
							<%
										} else if(!map.containsKey(acompReferencia)) {
							%>
							<td align="center" valign="middle">
								<p title="Não foi solicitado acompanhamento">N/A</p>
							</td>
							<%
										} else {
											flag = "acompanhado"; 
											AcompReferenciaItemAri ari = (AcompReferenciaItemAri) map.get(acompReferencia);										
							%>
							<td align="center" nowrap>
							<%			
											if((Dominios.NAO).equals(exigeLiberarAcompanhamento) || ari.getStatusRelatorioSrl().equals(statusLiberado)){
												if(!"true".equals(status)){
%>
								<a name="ancora<%=ari.getItemEstruturaIett().getCodIett()%>"
									href="#" onclick="javascript:aoClicarConsultar(form, <%=ari.getCodAri()%>, <%=ari.getItemEstruturaIett().getCodIett()%>)">
<%	
														itensSessaoVisualizar.add(ari.getCodAri().toString());
														request.setAttribute("itensSessaoVisualizar",itensSessaoVisualizar );
														if(usuarioLogadoEmiteParecer || permissaoLapis) {
															itensSessao.add(ari.getCodAri().toString());
															request.setAttribute("itensSessao",itensSessao );
														}
												}
												List relatorios = acompReferenciaItemDao.getAcompRelatorioArelOrderByFuncaoAcomp(ari, tpfaOrdenadosPorEstrutura);
												Iterator itRelatorios = relatorios.iterator();
	
												String imagePath = "";
												String aval = "";
												while(itRelatorios.hasNext()){												
													AcompRelatorioArel relatorio = (AcompRelatorioArel) itRelatorios.next();
													
													if(validaPermissao.permissaoConsultaParecerIETTGrupos(ari.getItemEstruturaIett().getCodIett(), relatorio.getTipoFuncAcompTpfa().getCodTpfa(),Long.valueOf(codTipoAcompanhamento),seguranca)){
														// Por Rogério (05/10/2007)
														// Modificada a forma de busca da imagem relacionada com a Cor.
														// Referente ao Mantis #7442
	
														boolean imageError = false;
														if( (Dominios.SIM).equals(relatorio.getIndLiberadoArel()) ) {
															Cor cor = ( relatorio.getCor()!=null ? relatorio.getCor() : null );
															TipoFuncAcompTpfa tpfa = ( relatorio.getTipoFuncAcompTpfa() != null ? relatorio.getTipoFuncAcompTpfa() : null );
																													
															imagePath = corDao.getImagemPersonalizada(cor, tpfa, "L");
															
															if( imagePath != null ) {
																/* -- As tags do CSS "max-width" e "max-height" não funcionam no IE6 ou menor -- */ 
															    aval += "<img border=\"0\" src=\"" + request.getContextPath() + "/DownloadFile?tipo=open&RemoteFile=";
															    aval += imagePath + "\" style=\"width: 23px; height: 23px;\" title=\"" + relatorio.getTipoFuncAcompTpfa().getLabelTpfa() + "\">";
															} else {
																if( relatorio.getCor() != null && relatorio.getCor().getCodCor() != null ) { 
																	aval += "<img border=\"0\" src=\"" + _pathEcar + "/images/relAcomp/";
																	aval += corDao.getImagemRelatorio(relatorio.getCor(), relatorio.getTipoFuncAcompTpfa()) + "\" title=\"";
																	aval += relatorio.getTipoFuncAcompTpfa().getLabelTpfa() + "\" >";
																} else {
																	imageError = true;
																}
															}
														} else {
															imageError = true;
														}
																												
														// Verifica se o parecer é obrigatorio ou opcional
														List listTipoAcompFuncAcomp = new ArrayList(acompReferencia.getTipoAcompanhamentoTa().getTipoAcompFuncAcompTafcs());
														boolean ehObrigatorio = true;
														if(listTipoAcompFuncAcomp != null) {
															Iterator tipoFuncAcompIt = listTipoAcompFuncAcomp.iterator();
															while(tipoFuncAcompIt.hasNext()) {
																TipoAcompFuncAcompTafc tipoAcompFuncAcompTafc = (TipoAcompFuncAcompTafc)tipoFuncAcompIt.next();
																if(	relatorio.getTipoFuncAcompTpfa().getCodTpfa().equals(tipoAcompFuncAcompTafc.getTipoFuncAcompTpfa().getCodTpfa())
																	&& tipoAcompFuncAcompTafc.getIndRegistroPosicaoTafc() != null 
																	&& tipoAcompFuncAcompTafc.getIndRegistroPosicaoTafc().equals(Dominios.OPCIONAL)) {
																	ehObrigatorio = false;
																}
															}
														}
														
														if( imageError && ehObrigatorio) {
															aval += "<img border=\"0\" src=\"" + _pathEcar + "/images/relAcomp/";
															aval += corDao.getImagemRelatorio(null, relatorio.getTipoFuncAcompTpfa()) + "\" title=\"";
															aval += relatorio.getTipoFuncAcompTpfa().getLabelTpfa() + "\" >";
														}													
													}																																								
												}
												
												out.println(aval);
																									
												if(!"true".equals(status)){
												%> </a>	<%
												}
											} else {
												if((Dominios.NAO).equals(exigeLiberarAcompanhamento) || (ari.getAcompRelatorioArels() != null && ari.getAcompRelatorioArels().size() > 0)) {
													if(!"true".equals(status)){
								%>
								<a name="ancora<%=ari.getItemEstruturaIett().getCodIett()%>" href="#"
									onclick="javascript:aoClicarConsultar(form, <%=ari.getCodAri()%>, <%=ari.getItemEstruturaIett().getCodIett()%>)">
									<%					itensSessaoVisualizar.add(ari.getCodAri().toString());
														request.setAttribute("itensSessaoVisualizar",itensSessaoVisualizar );					 
														if(usuarioLogadoEmiteParecer || permissaoLapis) {
															itensSessao.add(ari.getCodAri().toString());
															request.setAttribute("itensSessao",itensSessao );
														}
													}  
									%>
									<p title="Não liberado">N/L</p> 
												<%  if(!"true".equals(status)){  %> 
														</a>
												<%	}
											} else {
											flag = "";
							%> &nbsp; <%
											}
										}
							%>
							</td>
							<!-- termina de imprimir os pareceres -->
							<%
								}
							}
							if("true".equals(status)){ 
							%>
							<td align="center">
								<input type="checkbox"
									id="<%if(secretaria != null)%><%=secretaria.getCodOrg()%>"
									value="<%=item.getCodIett()%>">
							</td>
							<%
							}
							%>
							<input type="hidden" name="item_<%=contador%>" id="item_<%=contador%>" value="<%=item.getCodIett()%>">
						</tr>
						<!-- termina de imprimir a linha do item -->
							<% 
							//Aqui começa a imprimir os filhos do item que deverão estar display:none
							//e ser exibido apenas quando clicar no pai
							if(filhosExibir != null && filhosExibir.size() > 0) {
								Iterator filhosExibirIt = filhosExibir.iterator();
								int contadorFilhos = 1;
								while(filhosExibirIt.hasNext()){
									ItemEstruturaIett itemFilho = (ItemEstruturaIett) filhosExibirIt.next();
									if(itensGeral.contains(itemFilho)){
							%>
								<!-- chama a taglib que imprime a arvore dos filhos -->
								<tr id="itensFilhoDoItem<%=item.getCodIett()%>Filho<%=contadorFilhos%>" style="display:none" class="cor_sim"
									onmouseover="javascript:destacaLinha(this,'over','')" onmouseout="javascript: destacaLinha(this,'out','cor_sim')"
									onClick="javascript:destacaLinha(this,'click','cor_sim')" bgcolor="<%=itemFilho.getEstruturaEtt().getCodCor3Ett()%>">
									<util:arvoreFilhosItemEstruturaTag
										itemEstrutura="<%=itemFilho%>" usuario="<%=usuario%>"  
										status="<%=status%>" pathEcar="<%=_pathEcar%>"
										idPagina="<%=idPagina%>"
										caminhoUrl='<%=_pathEcar + "/acompanhamento/relAcompanhamento/"%>'  
										codTipoAcompanhamento="<%=codTipoAcompanhamento%>" 
										exigeLiberarAcompanhamento="<%=exigeLiberarAcompanhamento%>"
										statusLiberado="<%=statusLiberado%>"
										periodosConsiderados="<%=periodosConsiderados%>" 
										colecaoItens="<%=itensGeral%>"
										request="<%=request%>" 
										tpfaOrdenadosPorEstrutura="<%=tpfaOrdenadosPorEstrutura%>"
										permissaoAdministradorAcompanhamento="<%= permissaoAdministradorAcompanhamento%>"
										codAref="<%=codReferencia%>"/>
								</tr>
								<!-- acaba a taglib da arvore dos filhos -->
							<%
										contadorFilhos++;
									}
								}								
							}
							// Aqui termina a impressão dos filhos
							
							contador++;
							
						}// fim do while(itItens.hasNext())
						
						//Setando hidden com os itens da listagem para os botões Avançar/Retroceder
						String strCodArisControle = "";
						Iterator itCodArisControle = itensSessao.iterator();
						
						while(itCodArisControle.hasNext()){
							String codAri = (String) itCodArisControle.next();
							strCodArisControle += codAri + "|";
						}
											
						//Setando hidden com os itens da listagem para os botões Avançar/Retroceder de Visualizar parecer
						String strCodArisControleVisualizar = "";
						Iterator itCodArisControleVisualizar = itensSessaoVisualizar.iterator();
						
						while(itCodArisControleVisualizar.hasNext()){
							String codAri = (String) itCodArisControleVisualizar.next();
							strCodArisControleVisualizar += codAri + "|";
						}
						
						%>
						<script language="Javascript" type="text/javascript">
								document.form.codArisControle.value = "<%=strCodArisControle%>";
								document.form.codArisControleVisualizacao.value = "<%=strCodArisControleVisualizar%>";								
						</script>
						<%						
								request.setAttribute("codArisControle", itensSessao);								
						%>
						<tr class="linha_subtitulo2">
							<td colspan="<%=periodosConsiderados.size() + 6%>">
								&nbsp;
							</td>
						</tr>

					</table>
				</div>
				<%
					} else {
				%>
				
				
				<br>																					
				<table border="0" width="100%">
															
					<tr>
						<td class="texto" align="right">
							<util:barraLinks					
							_pathEcar="<%= _pathEcar%>"
							verificaMonitoramento="<%=new Boolean(true) %>" />
						</td>
					</tr>
					
				</table>
				
				<br>
				<br>
				<br>
				<table border="0" width="100%">
					<tr>
						<td class="texto" align="center">
							<b><%=_msg.getMensagem("posicaoGeral.filtro.nenhumRegistro")%></b>
						</td>
					</tr>
				</table>
				
				<%
					}
					if(primeiro)
						primeiro = false;
				}
			}
			
		
			if("true".equals(status) && contador < 1){
				List orgaos2 = (List) session.getAttribute("orgaosPrimeiraChamada");
				
				String scriptCombo2 = _disabled + " onchange=\"reload2();\"";
		%>
				<br>
				<br>
				<table border="0" width="100%">
					<tr>
						<td class="texto" align="center">
							<b><%=_msg.getMensagem("posicaoGeral.filtro.nenhumRegistro")%></b>
						</td>
					</tr>
					<tr>
						<td></td>
					</tr>
					<tr>
						<td></td>
					</tr>
					<tr>
						<td class="texto">
							<b>Selecione o orgão responsável:</b>
							<%
							if (orgaoResponsavel.getCodOrg() != null) {
%>
							<combo:ComboTag nome="orgaoResponsavel"
								objeto="ecar.pojo.OrgaoOrg" label="siglaOrg" value="codOrg"
								order="siglaOrg" colecao="<%=orgaos2%>"
								selected="<%=orgaoResponsavel.getCodOrg().toString()%>"
								scripts="<%=scriptCombo2%>" textoPadrao="Todos" />
							<%
							}else{
%>
							<combo:ComboTag nome="orgaoResponsavel"
								objeto="ecar.pojo.OrgaoOrg" label="siglaOrg" value="codOrg"
								order="siglaOrg" colecao="<%=orgaos2%>"
								scripts="<%=scriptCombo2%>" textoPadrao="Todos" />
							<%
							}
					%>
						</td>
					</tr>
				</table>
				<% 
			}
		} else {
	%>
			<!--  Mensagem para alertar que o tipoAcompanhamento é obrigatório  -->
				<table width="100%" border="0" cellpadding="0" cellspacing="0">
					<tr>
						<td class="texto" align="center">
							<%=_msg.getMensagem("posicaoGeral.validacao.tipoAcompanhamento.obrigatorio")%>
						</td>
					</tr>
				</table>
				<% 
		}
	%>
			</form>
			
			<br>
			<!--  Rodapé com o tempo para processar a página -->
			<table width="100%" border="0" cellpadding="0" cellspacing="0">
				<tr>
					<td>
						&nbsp;
					</td>
				</tr>
			</table>
			<% Date dataFinal = Data.getDataAtual(); %>
			<table class="form">
				<tr>
					<td>
						<%
						long df = dataFinal.getTime();
						long di = dataInicio.getTime();
						%>
						<b>Tempo para processar esta página:</b>
						<%=Data.parseDateHourSegundos( new Date(df - di) )%>
						mm.ss.SSS
					</td>
					<td>
					</td>
				</tr>
			</table>
<%
	}
} catch (ECARException e) {
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
	<%@ include file="/include/estadoMenu.jsp"%>
	<%@ include file="/include/mensagemAlert.jsp"%>
</html>