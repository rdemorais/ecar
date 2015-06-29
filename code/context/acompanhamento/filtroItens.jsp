
<jsp:directive.page import="ecar.pojo.PesquisaGrpAcesso"/>
<jsp:directive.page import="ecar.dao.PesquisaGrpAcessoDao"/>
<jsp:directive.page import="ecar.dao.PesquisaDao"/>
<jsp:directive.page import="ecar.pojo.Pesquisa"/>
<jsp:directive.page import="java.util.Collections"/>
<jsp:directive.page import="java.util.Comparator"/>
<jsp:directive.page import="java.util.HashMap"/>
<jsp:directive.page import="java.util.Map"/>
<jsp:directive.page import="java.util.LinkedList"/>
<%@page import="java.util.Enumeration"%>
<%@ taglib uri="/WEB-INF/ecar-util.tld" prefix="util" %>
<%@ taglib uri="/WEB-INF/ecar-combo.tld" prefix="combo" %>

<%@ page import="ecar.dao.TipoAcompTipofuncacompSisatributoTaTpfaSatbDao" %>
<%@ page import="ecar.pojo.Cor"%>
<%@ page import="ecar.pojo.AcompRefLimitesArl" %> 
<%@ page import="ecar.pojo.OrgaoOrg" %>
<%@ page import="ecar.pojo.TipoFuncAcompTpfa" %> 
<%@ page import="ecar.pojo.TipoAcompanhamentoTa" %>
<%@ page import="ecar.pojo.EstAtribTipoAcompEata"%>
<%@ page import="ecar.pojo.EstruturaEtt"%>
<%@ page import="ecar.pojo.ObjetoEstrutura"%>
<%@ page import="ecar.pojo.ItemEstruturaIett"%>
<%@ page import="ecar.pojo.AcompReferenciaAref" %> 
<%@ page import="ecar.pojo.EstruturaAtributoEttat"%>
<%@ page import="ecar.pojo.AtributosAtb"%>
<%@ page import="ecar.pojo.ConfiguracaoCfg" %>

<%@ page import="ecar.dao.CorDao"%>
<%@ page import="ecar.dao.EstAtribTipoAcompEataDao"%>
<%@ page import="ecar.dao.EstruturaDao"%>
<%@ page import="ecar.dao.AcompReferenciaDao" %> 
<%@ page import="ecar.dao.TipoFuncAcompDao" %> 
<%@ page import="ecar.dao.TipoAcompanhamentoDao" %>
<%@ page import="ecar.dao.OrgaoDao" %>
<%@ page import="ecar.dao.AbaDao" %>
<%@ page import="ecar.dao.CorDao" %>
<%@ page import="ecar.dao.ConfiguracaoDao" %>

<%@ page import="java.util.Collection"%>
<%@ page import="java.util.Iterator" %> 
<%@ page import="java.util.List" %> 
<%@ page import="java.util.HashSet"%>
<%@ page import="java.util.Set" %>


<%@page import="ecar.pojo.PesquisaIett"%>
<%@page import="comum.util.ConstantesECAR"%><html lang="pt-br">
<head>

<link rel="stylesheet" href="${pageContext.request.contextPath}/css/style_${sessionScope.configuracao.estilo.nome}.css" type="text/css"/>
<style type="text/css">
.ui-menu .ui-menu-item a {
	background-color: white;
   	display: block;
   	line-height: 1.5;
   	padding: 0.2em 0.4em;
   	text-decoration: none;
   	width: 300px;
}
.ui-menu {
   	list-style: none outside none;
   	width: 300px;
}
ul.ui-autocomplete{
	padding-left: 0;
	width: 300px;
}
</style>
<script src="${pageContext.request.contextPath}/js/jquery.1.7.1.js"></script>
<script src="${pageContext.request.contextPath}/js/jquery.ui.core.js"></script>
<script src="${pageContext.request.contextPath}/js/jquery.ui.widget.js"></script>
<script src="${pageContext.request.contextPath}/js/jquery.ui.position.js"></script>
<script src="${pageContext.request.contextPath}/js/jquery.ui.autocomplete.js"></script>
<script language="javascript" src="../js/menu_retratil.js"></script>
<script language="javascript" src="../js/focoInicial.js"></script>
<script language="javascript" src="../js/frmPadraoRegAcompanhamento.js"></script>		
<script language="javascript" src="${pageContext.request.contextPath}/js/popUp.js" type="text/javascript"></script>
<script language="javascript" src="${pageContext.request.contextPath}/js/destaqueLinha.js" type="text/javascript"></script>
<script language="javascript" src="${pageContext.request.contextPath}/js/validacoesAtributoLivre.js"></script>
<script language="javascript" src="${pageContext.request.contextPath}/js/datas.js"></script>
<script language="javascript" src="../js/numero.js"></script>
<script language="javascript" src="../js/validacoes.js"></script>
<script language="javascript" src="${pageContext.request.contextPath}/js/limpezaCamposMultivalorados.js"></script>
<!--<script language="javascript" src="../js/prototype.js"></script>-->
<script language="javascript" src="${pageContext.request.contextPath}/js/cookie.js"></script>
<%@ include file="/login/validaAcesso.jsp"%>
<%@ include file="/frm_global.jsp"%>
<%@ include file="/include/meta.jsp"%>
<%@ include file="/titulo.jsp"%>

<script language="javascript">
var codigosEtiquetas = new Array();
function autoCompleteEtiquetas(){
	$.ajax({
		url: "autoCompleteEtiqueta?nomeEtiqueta="+$("#nomeEtiqueta").val(),
		dataType: "xml",
		success: function( xmlResponse ) {
			var data = $( "etiqueta", xmlResponse ).map(function() {
				return {
					value: $( "nome", this ).text(), id: $( "codigo", this ).text()
				};
			}).get();
			$( "#nomeEtiqueta" ).autocomplete({
				source: data,
				minLength: 2,
				select: function( event, ui ) {
					inserirCodigoEtiquetaSelecionada(ui.item.id, ui.item.value);
				}
			});
		}
	});
}

function inserirCodigoEtiquetaSelecionada(codigo, nome){
	var existe = false;
	for(var i = 0; i < codigosEtiquetas.length; i++){
		if(codigosEtiquetas[i] == codigo){
			var existe = true;
		}
	}
	if(existe == false){
		var etiquetas = "<div id='etiqueta"+codigo+"' style='float: left; vertical-align: middle; display: table-cell;'>"
							+"<label class='label'>"+nome+"</label>"
							+"<a href='javascript:removerEtiquetaFiltro(etiqueta"+codigo+", "+codigo+")'>"
								+"<img src='"+${pageContext.request.contextPath}/+"images/excluir.png'/>"
							+"</a>"
	  				 +  "</div>";
		$("#etiquetasSelecionadas").append(etiquetas);
		codigosEtiquetas.push(codigo);
		$("#etiquetas").attr('value', codigosEtiquetas);
	}else{
		alert('Esta etiqueta já foi adicionada ao filtro.');
	}
}

function removerEtiquetaFiltro(nomeDiv, codigoEtiqueta){
	$(nomeDiv).remove();
	var index = codigosEtiquetas.indexOf(codigoEtiqueta);
	codigosEtiquetas.splice(index, 1);
}

function abrirPopUpNivelPlanejamento(){
	abreJanela("../relAcompanhamento/popUpNivelPlanejamento.jsp?telaGerarPeriodo=S", 500, 400, "Nivel");
}

function aoClicarTipoAcompanhamento(form, codTipoAcompanhamento){
	form.codTipoAcompanhamento.value = codTipoAcompanhamento;
	form.hidAcao.value = "personalizado";
	document.getElementById('hidAbaAberta').value = 'acompanhamento';
	form.action = "filtroItens.jsp";
	form.submit();
	
}

function apresentarLayer(layerAparece) {
	
	if (layerAparece != 'dadosGerais'){
	
		document.getElementById('filtros').style.display='none';
	    document.getElementById('acompanhamento').style.display='none';
	    document.getElementById('dadosGerais').style.display='none';
		document.getElementById(layerAparece).style.display = '';
		btnCancelar = document.getElementsByName('btnCancelar');
		for (var i=0; i< btnCancelar.length; i++){
			btnCancelar[i].style.display='';
		}
	}
	
	if (layerAparece == 'filtros'){
		document.getElementById('filtrosAba').className = 'abatipoacompanhamentohabilitada';
		document.getElementById('acompanhamentoAba').className = 'abatipoacompanhamentodesabilitada';
		document.getElementById('dadosGeraisAba').className = 'abatipoacompanhamentodesabilitada';
		document.getElementById('hidAbaAberta').value = 'filtros';
		for (var i=0; i< btnCancelar.length; i++){
			btnCancelar[i].style.display='none';
		}
	} else if (layerAparece == 'acompanhamento'){
		document.getElementById('filtrosAba').className = 'abatipoacompanhamentodesabilitada';
		document.getElementById('acompanhamentoAba').className = 'abatipoacompanhamentohabilitada';
		document.getElementById('dadosGeraisAba').className = 'abatipoacompanhamentodesabilitada';
		document.getElementById('hidAbaAberta').value = 'acompanhamento';
		
	} else{
	
		if (validarCamposObrigatorios(form)){
		
			document.getElementById('filtros').style.display='none';
		    document.getElementById('acompanhamento').style.display='none';
		    document.getElementById('dadosGerais').style.display='none';
			document.getElementById(layerAparece).style.display = '';
			btnCancelar = document.getElementsByName('btnCancelar');
			for (var i=0; i< btnCancelar.length; i++){
				btnCancelar[i].style.display='';
			}
		
			document.getElementById('filtrosAba').className = 'abatipoacompanhamentodesabilitada';
			document.getElementById('acompanhamentoAba').className = 'abatipoacompanhamentodesabilitada';
			document.getElementById('dadosGeraisAba').className = 'abatipoacompanhamentohabilitada';
			document.getElementById('hidAbaAberta').value = 'dadosGerais';
		
		}		
	}
	
}

function aoClicarPesquisar(form){	
	if (validarCamposObrigatorios(form)){
		form.hidAcao.value = "pesquisarFiltros";
		form.action = "posicaoGeral.jsp";
		form.submit();
	}
}

function aoClicarFormaVisualizacao(form, formaVisualizacao){
	form.hidFormaVisualizacaoEscolhida.value = formaVisualizacao;
	if(formaVisualizacao=='personalizado'){
		document.getElementById('dadosGeraisAba').style.display = '';
		document.getElementById('acompanhamentoAba').style.display = '';
		
	}else{
		document.getElementById('dadosGeraisAba').style.display = 'none';
		document.getElementById('acompanhamentoAba').style.display = 'none';
	}	
}

//botao avançar
function aoClicarBtn1(form){

	// geral: vai direto para a página de monitoramento sem realizar nenhum filtro
	// minhaVisao: filtra pelos acompanhamentos que o usuário logado tem permissão para editar algo
	// minhasPendencias: filtra pelos acompanhamentos que o usuário logado tenha algum parecer ainda não
	// liberado e/ou pelos acompanhamentos em que o usuário é administrador e que estes acompanhamentos exijam
	// liberação e o mesmo ainda não tenha sido liberado
	// personalizado: passa para a próxima aba (acompanhamento) para o usuário selecionar seus filtros
	
	form.action = "posicaoGeral.jsp";
	
	/* Validação se nenhuma opção de filtro for escolhida o sistema não segue para o próximo passo. */
	if (form.hidAbaAberta.value=='filtros' ) {
		radiosFormaVisualizacao = document.getElementsByName('formaVisualizacao');
		opcEsolhida = true;
		for (var i=0; i< radiosFormaVisualizacao.length; i++){
			if (radiosFormaVisualizacao[i].checked) {
				opcEsolhida = false;	
			}
		}
		if (opcEsolhida == true){
			alert('<%=_msg.getMensagem("filtroAcompanhamento.validacao.selecaoFiltro")%>');
			return false;
		}
	}
	
	/* Validacao dos campos nao pode seguir para a pagina de monitoramento sem os preenchimento dos campos obrigatorios */
	
	//Se existe algum tipo de acompanhamento cadastrado e ativo	
	if (form.codTipoAcompanhamento != null){
		if (form.hidAbaAberta.value == 'acompanhamento'){
			if (!validarCamposObrigatorios(form)){
			//	apresentarLayer('acompanhamento');
				return false;
			}
		}

		tirarLinkDadosGerais();

		codPrimeiroTA = document.getElementById('codPrimeiroTA').value;
		codArefAtual = document.getElementById('codArefAtual').value;
		
		if (form.hidFormaVisualizacaoEscolhida.value == 'geral'){			
			setarParametrosPesquisa(codPrimeiroTA, codArefAtual, 1);
			form.hidAcao.value = "";		
			form.submit();
		} else if (form.hidFormaVisualizacaoEscolhida.value == 'pesquisa') {
			form.hidAcao.value = "pesquisaSalva";		
			form.submit();
		}	
		else if (form.hidFormaVisualizacaoEscolhida.value == 'minhaVisao'){
			setarParametrosPesquisa(codPrimeiroTA, codArefAtual, 1);
			form.hidAcao.value = "pesquisarMinhaVisao";
			form.submit();
		}
		else if (form.hidFormaVisualizacaoEscolhida.value == 'minhasPendencias'){
			setarParametrosPesquisa(codPrimeiroTA, codArefAtual, 1);
			//form.hidAcao.value = "pesquisarMinhasPendencias";
			form.hidAcao.value = "";
			form.niveisPlanejamento.value = "64";
			form.niveisPlanejamentoAlterado.value = "S";
			form.submit();
		}
		else if (form.hidFormaVisualizacaoEscolhida.value == 'personalizado'){
			document.getElementById('dadosGeraisAba').style.display = '';
			document.getElementById('acompanhamentoAba').style.display = '';
			
			criarLinkDadosGerais ();
			
			//está na aba de dados gerais ou acompanhamento e quer pesquisar com os filtros ja escolhidos
			 if (form.hidAbaAberta.value == 'dadosGerais' || form.hidAbaAberta.value == 'acompanhamento' ){
				if (validar(form)){
					form.hidAcao.value = "pesquisarFiltros";			
					form.submit();
				}
			}
			//está na aba de filtros e quer ir para a aba de acompanhamento
			else{
				apresentarLayer('acompanhamento');
			}
			
		}
	
	}
	else{	
		alert('<%=_msg.getMensagem("filtroAcompanhamento.validacao.tipoAcompanhamento")%>');
	}	
	
}

function criarLinkDadosGerais (){
	document.getElementById("idDadosGerais").innerHTML= '<a href="javascript:apresentarLayer(\'dadosGerais\');" style="font-weight: bold; text-decoration:underline;"> Dados Gerais </a>';
}

function tirarLinkDadosGerais(){
	document.getElementById("idDadosGerais").innerHTML= ' Dados Gerais ';
}

function aoClicarCancelar(form){
	if (document.getElementById('hidAbaAberta').value == 'acompanhamento'){
		apresentarLayer('filtros');
		tirarLinkDadosGerais();
		document.getElementById('dadosGeraisAba').style.display = '';
		document.getElementById('acompanhamentoAba').style.display = '';
	}
	else if (document.getElementById('hidAbaAberta').value == 'dadosGerais'){
		apresentarLayer('acompanhamento');
		criarLinkDadosGerais ();
	}	
}

function validarCamposObrigatorios(form){

	var validaTudo = false;

	if (form.codTipoAcompanhamento.value == ""){
		alert("Obrigatório o preenchimento do campo Tipo Acompanhamento.");
	}
	else if (form.mesReferencia.value == "0") {
		alert("Obrigatório o preenchimento do campo Referência.");
	} 
	else if (form.periodo.value == "") {
		alert("Obrigatório o preenchimento do campo Exibir.");
	} else {
		validaTudo = true;
	}
		
	return validaTudo;
}

function aoClicarGravar(form){
	form.hidAcao.value = "gravar";
	form.action = "ctrlFiltroItens.jsp";
	form.submit();
}

function abrirItem(form, codEstrutura){
	
		var atributoEstrutura = document.getElementById(codEstrutura);

		if(atributoEstrutura.style.display == "none")
			atributoEstrutura.style.display = "";
		else
			atributoEstrutura.style.display = "none";

}


function setarParametrosPesquisa(tipoAcompanhamento, codReferencia, numPeriodo) {
	document.getElementById('codReferencia').value =  codReferencia;
	var optTipoAcomp = document.getElementsByName('codTipoAcompanhamento');
	var selPeriodos = document.getElementById('periodo');
	
	for (var i = 0;i<optTipoAcomp.length ; i++){
		if (optTipoAcomp[i].value == tipoAcompanhamento){
			optTipoAcomp[i].checked = true;
		} else {
			optTipoAcomp[i].checked = false;
		}
	} 
	
	for (var i = 0;i<selPeriodos.options.length ; i++){
		if (selPeriodos.options[i].value == numPeriodo){
			selPeriodos[i].selected = true;
		} else {
			selPeriodos.options[i].selected = false;
		}
	} 
}

var codPes;
// Funcao Ajax - realiza chamada para o servico Excluir uma pesquisa
function aoClicarExcluir(codPesquisaExcluir){
	codPes = codPesquisaExcluir;
	var url = 'popUp/ctrlPesquisa.jsp';
	var pars = 'codPesquisaExcluir='+codPesquisaExcluir+'&hidAcao=excluir';
	
	if ( confirm('Deseja excluir essa pesquisa? ') ) {
	
		var radioFormaVisu = document.getElementsByName('formaVisualizacao');
		for (var i = 0;i<radioFormaVisu.length ; i++){
			if (radioFormaVisu[i].value == codPesquisaExcluir){
				radioFormaVisu[i].checked = false;
			} 
		} 
	
		var myAjax = new Ajax.Request( url, { method: 'get', parameters: pars, 
											onComplete:msgExcluirPesquisa, 
											onLoading: function(){ },
											onFailure: function(){ } 
										 }
								   );
	
	} 
}

function msgExcluirPesquisa (requisicaoOriginal){
	resposta = requisicaoOriginal.responseText;
	if (requisicaoOriginal.responseText.trim()=='FALHA'){
		alert ("Erro ao excluir a pesquisa.");
		document.getElementById('linhaPesquisa'+codPesq).style.display = "";
	} else {
		document.getElementById('linhaPesquisa'+codPes).style.display = "none";
		document.getElementById('td'+codPes).style.display = "none";
		document.getElementById('tr'+codPes).style.display = "none";
	}
	codPes='';
}

function montalistagemLocal(requisicaoOriginal)
{
	alert(requisicaoOriginal.responseText.length + ' ' + requisicaoOriginal.responseText.trim().length);
	alert(requisicaoOriginal.responseText);
}

/**************************************************************************************************************/
/* Funcao para transformar mascara moeda para formato double suportado pelo interpretador de javascript
 * caso não esteja no formato moeda, não é transformado
 *        */
/*************************************************************************************************************/
function transformaValorMascaraMoedaParaDouble(numero, tipoInfinito){
	
	var pos = "";
	var valorTransformado = "";
	
	// se for de tamanho maior que 
	if(numero.length>2 && (numero.indexOf(".")!= -1 || numero.indexOf(",")!= -1)) {
			for(var i=numero.length; i>0; i--){
				pos = numero.substring(i, i-1);
				if (pos==".") {
					if((numero.length-i)==2) {
						valorTransformado = pos + valorTransformado;
					}
				} else if(pos==",") {
					if ((numero.length-i)==2) {
						valorTransformado = "." + valorTransformado;
					}
				} else {
					valorTransformado = pos + valorTransformado;
				}
			}
	} else {
		valorTransformado = numero;	
	}

	
    return valorTransformado;
}

</script>

</head>

<% request.setAttribute("exibirAguarde", "true"); %>

<%@ include file="/cabecalho.jsp" %>
<%@ include file="/divmenu.jsp"%> 




<body onload="focoInicial(document.form);">


	
<div id="conteudo">  
 
<!-- TÍTULO -->
<%@ include file="/titulo_tela.jsp"%>

<form name="form" method="post" onsubmit="">

<util:msgObrigatorio obrigatorio="<%=_obrigatorio%>" />

<%


	String abaAberta =  "filtros";
	String validacao = "";
	if (request.getParameter("hidAbaAberta")!=null)
		abaAberta = request.getParameter("hidAbaAberta");
	
	String formaVisualizacao = "";
	String codTipoAcompanhamento = "";
	String mesReferencia = "";
	boolean ehSeparadoPorOrgao = false;
	
	//Verifica se vem de monitoramento
	String vemMonitoramento =  Pagina.getParamStr(request, "vemMonitoramento");
	
	//se nao vier de monitoramento, remove os atributos pra nao mostrar formulario preenchido 
	if(vemMonitoramento == null || vemMonitoramento.equals("")) {
		vemMonitoramento = "N";
		Enumeration enu = request.getParameterNames();
		while (enu.hasMoreElements()){
			String nomeAtributo = (String)enu.nextElement();
			String valor = request.getParameter(nomeAtributo);
			request.removeAttribute(nomeAtributo);
		}
	} 
			
	_disabled = "";
	List acompanhamentos = null;
	AcompReferenciaAref arefAtual = null;
	
	String periodo = "";
	
	periodo = Pagina.getParamStr(request, "periodo");	
	String periodoEscolhido = "";
	//String periodoEscolhido = periodo + (("1".equals(periodo)) ? " Ciclo" : " Ciclos");

	CorDao corDao = new CorDao(request);

	String hidAcao = request.getParameter("hidAcao");

try {

	Set gruposAcesso = ((ecar.login.SegurancaECAR)session.getAttribute("seguranca")).getGruposAcesso();

	if(hidAcao != null){
		formaVisualizacao = "personalizado" ;
	}else{
		formaVisualizacao = Pagina.getParamStr(request, "formaVisualizacao");
	}

	PesquisaGrpAcesso configPesquisaGrpAcesso = new PesquisaGrpAcesso();
	PesquisaGrpAcessoDao pesqGrpAcessoDao = new PesquisaGrpAcessoDao();
	PesquisaDao pesqDao = new PesquisaDao(request);
	List pesquisas = new ArrayList();
	
	
	configPesquisaGrpAcesso = pesqGrpAcessoDao.getConfiguracaoPesquisaGrupoAcesso(seguranca.getGruposAcesso());
	
	pesquisas.addAll(pesqDao.listarPesquisaSistemaDeGruposAcesso(seguranca.getGruposAcesso()));
	pesquisas.addAll(pesqDao.listarPesquisaUsuario( seguranca.getUsuario()));

	if(pesquisas != null && pesquisas.size() > 0){
	 	/*Ordenar alfabeticamente as pesquisas. */
	 	Collections.sort(pesquisas,
			new Comparator() {
		      		public int compare(Object o1, Object o2) {
		      		    return ( (Pesquisa)o1 ).getNomePesquisa().compareTo(((Pesquisa)o2).getNomePesquisa() ) ;
		      		}
		  		} );
	}
 
	//formaVisualizacao = Pagina.getParamStr(request, "formaVisualizacao");

	TipoAcompanhamentoDao tipoAcompanhamentoDao = new TipoAcompanhamentoDao(request);
	
	AcompReferenciaDao acompReferenciaDao = new AcompReferenciaDao(request);
	
	EstruturaDao estruturaDao = new EstruturaDao(request);
	
   	List tiposAcompanhamentos = tipoAcompanhamentoDao.getTipoAcompanhamentoAtivosComAcompanhamentoBySeqApresentacao();
   	 	   	
   	TipoAcompanhamentoTa primeiroTA = null;
   
//	Set gruposAcesso = ((ecar.login.SegurancaECAR)session.getAttribute("seguranca")).getGruposAcesso();

	String codPrimeiroTA = "";

   	if (tiposAcompanhamentos != null && tiposAcompanhamentos.size() > 0){
        
        Iterator itTipoAcompanhamentos = tiposAcompanhamentos.iterator();
        
        while (itTipoAcompanhamentos.hasNext()){
            
            TipoAcompanhamentoTa ta = (TipoAcompanhamentoTa) itTipoAcompanhamentos.next(); 
            
            if (tipoAcompanhamentoDao.existeAbaConfiguradaTipoAcompanhamento(ta, gruposAcesso)){            
                primeiroTA = (TipoAcompanhamentoTa) ta;
                codPrimeiroTA = primeiroTA.getCodTa().toString();
                break;
            }
        }
    }
   	
   	
   	
 	codTipoAcompanhamento = Pagina.getParamStr(request, "codTipoAcompanhamento");
 	//Se não pego o código do tipo de acompanhamento da request, selecionar o primeiro da lista
 	if (primeiroTA != null && (codTipoAcompanhamento == "" || codTipoAcompanhamento == null)){
 	
 		codTipoAcompanhamento = primeiroTA.getCodTa().toString();
 	}
   	
 	TipoAcompanhamentoTa tipoAcompanhamento = null;
 	
 	if (codTipoAcompanhamento != null && !codTipoAcompanhamento.equals("")){
 		tipoAcompanhamento = (TipoAcompanhamentoTa) tipoAcompanhamentoDao.buscar(TipoAcompanhamentoTa.class, Long.valueOf(codTipoAcompanhamento));
 		
 		if(tipoAcompanhamento != null && tipoAcompanhamento.getIndSepararOrgaoTa() != null && tipoAcompanhamento.getIndSepararOrgaoTa().equals("S")) {
 			ehSeparadoPorOrgao = true;
 			acompanhamentos = acompReferenciaDao.getComboReferencia(tipoAcompanhamento);
 		} else { 
   			acompanhamentos = acompReferenciaDao.getListAcompReferenciaByTipoAcompanhamento(Long.valueOf(codTipoAcompanhamento));	
 		}
   			
   		arefAtual = acompReferenciaDao.getAcompSelecionado(acompanhamentos);
   	}
   	
   	String codArefAtual = ""; 
   	
   	if (arefAtual != null){
   		codArefAtual = arefAtual.getCodAref().toString();
   	}
   	
   	if (Pagina.getParamStr(request, "codReferencia") != null){
   		mesReferencia = Pagina.getParamStr(request, "codReferencia");
   	}
   	else {   	
   		mesReferencia = arefAtual.getCodAref().toString();
   	}
		
	String periodoPadrao = periodo;
	if (configuracaoCfg.getPeriodoPadrao()!= null && (periodoPadrao==null || periodoPadrao.equals(""))) {
		periodoPadrao = configuracaoCfg.getPeriodoPadrao().toString();
	}
           	
   	%>
	<input type="hidden" id="codReferencia" name="codReferencia" value="<%=mesReferencia%>">
   	<input type="hidden" name="periodoEscolhido" value="<%=periodoEscolhido%>">
   	<input type="hidden" name="hidAcao" value="">
   	<input type="hidden" name="niveisPlanejamento"/>
   	<input type="hidden" name="niveisPlanejamentoAlterado"/> 
   	<input type="hidden" id="hidAbaAberta" name="hidAbaAberta" value="<%=abaAberta %>">
   	<input type="hidden" id="hidFormaVisualizacaoEscolhida" name="hidFormaVisualizacaoEscolhida" value="<%=formaVisualizacao %>">
   	<input type="hidden" id="codPrimeiroTA" name="codPrimeiroTA" value="<%=codPrimeiroTA%>">
   	<input type="hidden" id="codArefAtual" name="codArefAtual" value="<%=codArefAtual%>">
   	<input type="hidden" id="etiquetas" name="etiquetas"/>
   	
   	  	
   	  	
	<!--  <util:barrabotoes pesquisar="Pesquisar" cancelar="Cancelar"/> -->

	<%if(abaAberta.equals("filtros")){%>
		<util:barrabotoes btn1="Avançar" cancelar="Voltar" esconderCancelar="true"/>
	<%}else{%>
		<util:barrabotoes btn1="Avançar" cancelar="Voltar"/>
	<%}%>

	<table id="abas" cellpadding="0" cellspacing="0" width="100%">
		<tbody>
			<tr>
				<td nowrap="nowrap">
					<table class= "<%= (abaAberta.equals("filtros")?"abatipoacompanhamentohabilitada":"abatipoacompanhamentodesabilitada")  %>" id="filtrosAba">
						<tbody>
							<tr>
								<td>
					<%--  	  	<a href="javascript:apresentarLayer('filtros');">Filtros</a>    --%>
								 Filtros
								</td>
							</tr>
						</tbody>
					</table>
					<!-- table class="abatipoacompanhamentodesabilitada" id="acompanhamentoAba"-->
					
					<table class= "<%= (abaAberta.equals("acompanhamento")?"abatipoacompanhamentohabilitada":"abatipoacompanhamentodesabilitada")  %>" id="acompanhamentoAba" 
							<%if(formaVisualizacao.equals("personalizado")){%>
									style="display: '';"
								<%}else{%>
									style="display: none;"
								<%}%>>
						<tbody>
							<tr>
								<td>
					<%--	  	<a href="javascript:apresentarLayer('acompanhamento');">Dados Acompanhamento ></a--%>   
								 Dados Acompanhamento
								</td>
							</tr>
						</tbody>
					</table>

					<table class="<%= (abaAberta.equals("dadosGerais")?"abatipoacompanhamentohabilitada":"abatipoacompanhamentodesabilitada")  %>" id="dadosGeraisAba"
							<%if(formaVisualizacao.equals("personalizado")){%>
								style="display: '';"
							<%}else{%>
								style="display: none;"
							<%}%>>
						<tbody>
							<tr>
								<td> <span id="idDadosGerais" > 
								<% //Quando faz recarga da página aparecer o link em Dados Gerais
								if (abaAberta.equals("acompanhamento") || abaAberta.equals("dadosGerais") ) { %>
									<a href="javascript: apresentarLayer('dadosGerais');" style="font-weight: bold; text-decoration:underline;"> Dados Gerais </a>
								<% } else { %> 
									Dados Gerais
								<% }%> 
								<%--<a href="javascript:apresentarLayer('dadosGerais');">Dados Gerais</a--%>   
								</span> </td>
							</tr>
						</tbody>
					</table>
				</td>
			</tr>
		</tbody>
	</table>

	<%
//	if (abaAberta.equals("filtros")) {
	 %>
	<span id="filtros" style= "<%=abaAberta.equalsIgnoreCase("filtros") ? "style:" : "display:none" %>">
	<table name="form" class="form">
		<tr><td colspan="2">&nbsp;</td></tr>
		<tr>
			<td class="label" valign="top"><%=_obrigatorio%> Visualização</td>
			<td class="widthCol" colspan="3"> 
			<!-- RADIO BUTTONS DE FORMAS DE VISUALIZAÇÃO -->
			
		
		
			
		<%if (configPesquisaGrpAcesso.getIndPodeVerGeral().equals("S")){%>			
			<label><input type="radio" class="form_check_radio" name="formaVisualizacao" value="geral" onclick="aoClicarFormaVisualizacao(form, 'geral');" 
			<%
				if (formaVisualizacao.equalsIgnoreCase("geral")){
			%> checked
			<%
				}
			%>> 
			Geral </label></td></tr>			
		<%}%>
		
		
		<%if (configPesquisaGrpAcesso.getIndPodeVerMinhaVisao().equals("S")){%>			
			<tr><td></td><td>
			<label><input type="radio" class="form_check_radio" name="formaVisualizacao" value="minhaVisao" onclick="aoClicarFormaVisualizacao(form, 'minhaVisao');" 
			<%
				if (formaVisualizacao.equalsIgnoreCase("minhaVisao")){
			%> checked
			<%
				}
			%>>  
			Minha Vis&atilde;o </label></td></tr>
		<%}%>
		
		<%if (configPesquisaGrpAcesso.getIndPodeVerPendencias().equals("S")){%>		
			<tr><td></td><td>
			<label><input type="radio" class="form_check_radio" name="formaVisualizacao" value="minhasPendencias" onclick="aoClicarFormaVisualizacao(form, 'minhasPendencias');"
			<%
				if (formaVisualizacao.equalsIgnoreCase("minhasPendencias")){
			%> checked
			<%
				}
			%>> 
			Resultados Prioritários </label></td></tr>

		<%}
		
		String strPeriodo = "";
		
		Map mapTipoAcompComPesquisa = new HashMap();
		//Map com os tipo de acompanhamento que tem pesquisa 
		
		 if ( pesquisas != null  ){
			Iterator itPesquisas = pesquisas.iterator();
			
			while (itPesquisas.hasNext()) {
				Pesquisa pesquisa = (Pesquisa) itPesquisas.next();
				AcompReferenciaAref arefPesquisa = null;
				if (tipoAcompanhamentoDao.existeAbaConfiguradaTipoAcompanhamento(pesquisa.getTipoAcompanhamentoTa(), gruposAcesso)){
				
					if (!mapTipoAcompComPesquisa.containsKey(pesquisa.getTipoAcompanhamentoTa()) ){
						pesqDao.adicionaTipoAcompReferencias(mapTipoAcompComPesquisa, pesquisa);
					}
					strPeriodo = pesqDao.getNomeFiltroReferenciaHtml( mapTipoAcompComPesquisa,  pesquisa);
					
					List arefs = (List)mapTipoAcompComPesquisa.get(pesquisa.getTipoAcompanhamentoTa());
					if (pesquisa.getIndTipoReferencia()==null || pesquisa.getIndTipoReferencia().equals(PesquisaDao.PERIODO_ATUAL) ){
						arefPesquisa = (AcompReferenciaAref)arefs.get(0);
					} else if (pesquisa.getIndTipoReferencia().equals(PesquisaDao.ULTIMO_PERIODO) ){
						arefPesquisa = (AcompReferenciaAref)arefs.get(1);
					} else {
						//Existe uma validação na inclusão 
						//da pesquisa para não deixar salvar uma pesquisa sem referência
						Iterator itPesquisaIetts = pesquisa.getPesquisaIetts().iterator();
						if (itPesquisaIetts.hasNext()){
							PesquisaIett pesquisaIett = (PesquisaIett) itPesquisaIetts.next();
							arefPesquisa = pesquisaIett.getAcompReferenciaAref();
						}
					}
		%>
		<tr id="tr<%=pesquisa.getCodPesquisa()%>" ><td id="td<%=pesquisa.getCodPesquisa()%>"></td><td nowrap>
			<span id="linhaPesquisa<%=pesquisa.getCodPesquisa()%>" style="display:"> 
			<label><input type="radio" class="form_check_radio" name="formaVisualizacao" value="<%=pesquisa.getCodPesquisa()%>" onclick="aoClicarFormaVisualizacao(form, 'pesquisa');setarParametrosPesquisa('<%= pesquisa.getTipoAcompanhamentoTa().getCodTa() %>', '<%=arefPesquisa.getCodAref()%>', '<%=pesquisa.getPeriodo() %>')">
																																																																	 
			<%=pesquisa.getNomePesquisa()  %> - <%= strPeriodo %>
			<%if (pesquisa.getIndTipoPesquisa().equals(PesquisaDao.PESQ_USUARIO) || (configPesquisaGrpAcesso.getIndPodeCriarPesquisaSistema().equals(PesquisaDao.PESQ_SISTEMA))) { %></label>  
				&nbsp;&nbsp;&nbsp;<a href="#" onclick="aoClicarExcluir('<%=pesquisa.getCodPesquisa() %>')">Excluir</a> &nbsp;&nbsp; 
			<%} %>
			</span> 
		</td></tr>
		<%
				}//fim if
			} // fim while
		  } //fim if
		 %>
		</td>
		
		
		
		
		<%if (configPesquisaGrpAcesso.getIndPodeVerPersonalizado().equals("S")){%>			
			<tr><td></td><td>
			<label><input type="radio" class="form_check_radio" name="formaVisualizacao" value="personalizado" onclick="aoClicarFormaVisualizacao(form, 'personalizado');setarParametrosPesquisa('<%= codTipoAcompanhamento %>', '<%= mesReferencia %>', '<%=periodoPadrao%>')"
			<%
				if (formaVisualizacao.equalsIgnoreCase("personalizado")){
			%> checked
			<%
				}
			%>>  
			Personalizado </label>
			</td></tr>
		<%}%>
			
		</td></tr>
		<tr></tr>
	
	</table>
	</span>
	<%
//	}	 
//	else if (abaAberta.equals("acompanhamento")) {
	%>
<!-- style="display:none;text-align: left; margin: 0" -->
	
	<span id="acompanhamento" style="<%=abaAberta.equalsIgnoreCase("acompanhamento") ? "style:" : "display:none" %>" class="form">
	<table name="form" class="form">
		<tr><td colspan="2">&nbsp;</td></tr>
		<tr>
			<td class="label"><%=_obrigatorio%> Tipo de Acompanhamento</td>
			<td class="widthCol" colspan="3"> 
			<!-- COMBO DE TIPOS DE ACOMPANHAMENTO -->
			
			<%
			
				if(tiposAcompanhamentos != null && tiposAcompanhamentos.size() > 0) {
       	
			       	Iterator itTipoAcompanhamento = tiposAcompanhamentos.iterator();
					
			       	
			       	
			   		while (itTipoAcompanhamento.hasNext()) {
			   			TipoAcompanhamentoTa ta = (TipoAcompanhamentoTa)itTipoAcompanhamento.next();
			   			
			   			//Exibe o tipo de acompanhamento apenas se existe alguma aba configurada para ele que o 
			   			//usuário logado possua acesso
			   			if (tipoAcompanhamentoDao.existeAbaConfiguradaTipoAcompanhamento(ta, gruposAcesso)){
			   			
							%><input type="radio" class="form_check_radio" name="codTipoAcompanhamento" 
							value="<%=Pagina.trocaNull(ta.getCodTa().toString())%>"  
							onclick="aoClicarTipoAcompanhamento(form, '<%=ta.getCodTa()%>')" 
							<%if (ta.getCodTa().toString().equals(codTipoAcompanhamento)){%>
							checked
							<%} 
							%>
							<%= Pagina.isChecked(ta.getCodTa(), codTipoAcompanhamento) %> >
							<%=ta.getDescricaoTa()%><br>
							<% 
			   			}
			   		}
			   	}
			%>
			</td>
		</tr>
		
		<tr>
		
				<td class="label"><%=_obrigatorio%> Referência</td>
				<td colspan="3" class="widthCol">
				
					<!-- COMBO DE PERÍODOS DE REFERÊNCIA -->
					<select name="mesReferencia"  > 
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
	
						if (acompanhamentos!= null && acompanhamentos.size()>0){
								Iterator itAcomp = acompanhamentos.iterator();
							
							while(itAcomp.hasNext()){
								AcompReferenciaAref acompReferencia = (AcompReferenciaAref) itAcomp.next();
	
								if(!"0".equals(mesReferencia) && mesReferencia.equals(acompReferencia.getCodAref().toString())){
									selMesRef = "selected";
									mesReferencia = acompReferencia.getCodAref().toString();
								}
								else if((mesReferencia == null || "".equals(mesReferencia)) && (arefAtual != null && arefAtual.equals(acompReferencia))) {
									selMesRef = "selected";
									mesReferencia = acompReferencia.getCodAref().toString();
								} else {
									selMesRef = "";
								}
								
								StringBuffer opcaoCombo = new StringBuffer(acompReferencia.getDiaAref())
														.append("/").append(acompReferencia.getMesAref())
														.append("/").append(acompReferencia.getAnoAref());
								
								if(ehSeparadoPorOrgao  && acompReferenciaDao.getListaMesmaReferenciaDiaMesAno(acompReferencia).size() > 1){
									opcaoCombo.append(" - " + ConstantesECAR.LABEL_ORGAO_CONSOLIDADO);
								} else {
									opcaoCombo.append(" - ").append(acompReferencia.getNomeAref());
								}
							
						%>
								<option value="<%=Pagina.trocaNull(acompReferencia.getCodAref())%>" <%=selMesRef%>> <!-- acompReferencia.getCodAref() -->
									<%=opcaoCombo%>
								</option>
						<%
								}
							}
						%>
							</select>
				</td>
			</tr>
			<tr>
				<td class="label"><%=_obrigatorio%> Exibir</td>
				<td valign="bottom" class="texto;widthCol" align="left" colspan="3">



					<!-- COMBO DE PERÍODOS DE EXIBIÇÃO -->							
					<select name="periodo" id="periodo" >
											
						<option value="">
							--SELECIONE--
						</option>
						<% 
						for(int i=1;i<=12;i++){ 
							Integer inteiro = new Integer(i);
						%>
							<option value="<%=Pagina.trocaNull(String.valueOf(i))%>" 
							 <%
							 if (periodo == "" && (periodoPadrao != null|| (!periodoPadrao.equals("") ) ) && inteiro.toString().equals(periodoPadrao)){
							 %>
								selected
							 <%
							 }
							 if(inteiro.toString().equals(periodo)){
							 %>
								selected
							 <%
							 }
							 %>
							> 								
								<%=inteiro%> <%if (inteiro.intValue()==1){%> Ciclo <%}else {%> Ciclos <%}%>
							</option>
					  <%}%>
					</select>
				</td>
			</tr>
					
			<tr>
			
			<td class="texto;widthCol" align="left" colspan="3">
			<table width=100px border="0"> 
				<tr> 
				
				<td class="label" valign="top"> Fun&ccedil;&otilde;es de Acompanhamento</td>
				<td class="colunaFormFiltroItensAcomp" valign="top">
				<!-- CHECK BOX LIST DE TIPO FUNCAO ACOMPANHAMENTO -->
				
				<%
					TipoFuncAcompDao tipoFuncAcompDao = new TipoFuncAcompDao(request);
				
					String[] tipoFuncoesAcompanhamento  = request.getParameterValues("tipoFuncAcompTpfa");
					List lFuncoesAcompanhamentoSelecionadas = new ArrayList();
					
					List listaTipoFuncaoAcompanhamentoTpfa = new ArrayList();
					
					if (arefAtual != null){
					
						TipoAcompTipofuncacompSisatributoTaTpfaSatbDao tipoAcompTipofuncacompSisatributoTaTpfaSatbDao = new TipoAcompTipofuncacompSisatributoTaTpfaSatbDao();
						Iterator itTipoFuncaoAcompanhamentoTpfa = tipoAcompTipofuncacompSisatributoTaTpfaSatbDao.pesquisarPermissaoVisualizarParecerTipoFuncaoAcompTpfa(arefAtual.getTipoAcompanhamentoTa(), seguranca.getGruposAcesso()).iterator();
	
						
						while (itTipoFuncaoAcompanhamentoTpfa.hasNext()) {
							TipoFuncAcompTpfa tipoFuncAcompTpfa = (TipoFuncAcompTpfa) itTipoFuncaoAcompanhamentoTpfa.next();
							if("S".equalsIgnoreCase(tipoFuncAcompTpfa.getIndEmitePosicaoTpfa())){
								listaTipoFuncaoAcompanhamentoTpfa.add(tipoFuncAcompTpfa);
							}
						}
					}
				
					if (tipoFuncoesAcompanhamento != null) {
			        	for (int i = 0; i < tipoFuncoesAcompanhamento.length; i++) {
			            	TipoFuncAcompTpfa tipoFuncAcompTpfa = (TipoFuncAcompTpfa) tipoFuncAcompDao.buscar(TipoFuncAcompTpfa.class, Long.valueOf(tipoFuncoesAcompanhamento[i]));
			                lFuncoesAcompanhamentoSelecionadas.add(tipoFuncAcompTpfa);
			            }
			        }				
					
				 %>
					<combo:CheckListTag nome="tipoFuncAcompTpfa"
					objeto="ecar.pojo.TipoFuncAcompTpfa" label="labelPosicaoTpfa"
					value="codTpfa" order="labelTpfa" scripts="<%=_disabled%>"
					selected="<%=lFuncoesAcompanhamentoSelecionadas%>" 
					colecao="<%=listaTipoFuncaoAcompanhamentoTpfa%>"
					/>
				</td>
				
			<!--  	<td valign="top">  -->
				
					 
				<td  class="label" valign="top" align="left" style="width: 5%">Cor</td>
				<td align="left" valign="top" >
					 				 				 
				 <!-- CHECK BOX LIST DE COR -->
				 
				<%						
					String imagePath = null;
					List coresList = corDao.listar(Cor.class, null);
										
					String[] cores  = request.getParameterValues("cor");
			        List lCoresSelecionadas = new ArrayList();
			       
			        if (cores != null) {
			        	for (int i = 0; i < cores.length; i++) {
			            	Cor cor = (Cor) corDao.buscar(Cor.class, Long.valueOf(cores[i]));
			                lCoresSelecionadas.add(cor);
			            }
			        }
														
				%>						
			
						<combo:CheckListTag nome="cor"
							objeto="ecar.pojo.Cor" label="significadoCor"
							value="codCor" order="nomeCor"
							colecao="<%=coresList%>" scripts="<%=_disabled%>"
							selected="<%=lCoresSelecionadas%>" />	 
					</td>
												
				</tr> 
			</table>
			</td>
				
			</tr>
			</table
				
			</tr>
							
	</table>
	</span>
	<%
//	}
	
//	else if (abaAberta.equals("dadosGerais")) {	
	 %>
	
	<!-- ABA DE DADOS GERAIS -->
	<span id="dadosGerais" style="<%=abaAberta.equalsIgnoreCase("dadosGerais") ? "style:" : "display:none" %>" class="form">
		<table name="form" border="0" class="form" >
			<tr><td colspan="2"></td></tr>
			<tr>
				
				<!-- ÁRVORE DE ESTRUTURA -->
				
				<%
					Collection listItensEstrutura = null;
				
					//Recupera todos os atributos de itens de estrutura relacionados a um tipo acompanhamento
					//que podem ser filtro
				
					if (codTipoAcompanhamento != null && codTipoAcompanhamento != ""){
					
				%>
				
				<td class="label" style="text-align: left"> Estrutura</td>
				<td>
				
				<%
					
						Iterator itTipoAcompanhamento = tiposAcompanhamentos.iterator();
						TipoAcompanhamentoTa tipoAcompanhamentoEscolhido = null;
						List listEstruturas = new ArrayList();
											
			   			while (itTipoAcompanhamento.hasNext()) {			   				
			   				
			   				TipoAcompanhamentoTa tipoAcompanhamentoTa = (TipoAcompanhamentoTa)itTipoAcompanhamento.next();
			   				if ( tipoAcompanhamentoTa.getCodTa().equals(Long.valueOf(codTipoAcompanhamento))){
			   					tipoAcompanhamentoEscolhido = tipoAcompanhamentoTa;
			   				}
			   			}

			   			if (tipoAcompanhamentoEscolhido != null){	
			   							
							EstAtribTipoAcompEataDao estAtribTipoAcompEataDao = new EstAtribTipoAcompEataDao(request);
							
							listEstruturas = 
							estAtribTipoAcompEataDao.getEstruturaEhFiltro(tipoAcompanhamentoEscolhido);
							
		    			}
				%>
				</td>
			</tr>
			<!-- INÍCIO DA ÁRVORE DE ÍTENS DE ESTRUTURA E SEUS ATRIBUTOS QUE PODEM SER FILTRO -->
			
			<!-- CABEÇALHO DA TABELA -->
						<!-- Aqui começam a ser imprimidos os itens de acompanhamento -->
						<!-- DADOS DA TABELA -->
						<%
													
						if (listEstruturas != null && listEstruturas.size() > 0){	
							/* Itera pelos itens */
							Iterator itEstrutura = listEstruturas.iterator();
							
																				
							while(itEstrutura.hasNext()){
								
								EstruturaEtt estruturaEtt = (EstruturaEtt) itEstrutura.next();																										
																	
								List atributosPorEstrutura = new ArrayList();
										
								String linkAbrirArvoreEstruturaComeco = "";
								String linkAbrirArvoreEstruturaFim = "";
								
								//criação do link para abrir a arvore de itens do item
								linkAbrirArvoreEstruturaComeco = "<a href=\"javascript:abrirItem(form, '" + estruturaEtt.getCodEtt().toString() + "')\">";
								linkAbrirArvoreEstruturaFim = "</a>";
	
					%>
								<!-- começa a imprimir a linha da estrutura -->
								<tr style="text-align: left;" class="form"
									bgcolor="<%=estruturaEtt.getCodCor3Ett()%>">
																							
									<!-- começa a imprimir a coluna que faz a identação de acordo com o nivel da estrutura -->
									<td>
										<table  class="form">
											<tr>
												<td nowrap width="2%"> &nbsp;
			    <%
																					
												/* Identação pelo nível do item */
												int nivel = 0;
												if (estruturaEtt.getEstruturaEtt() != null){
													nivel = estruturaDao.getNivel(estruturaEtt);
												}
												
												for(int i = 0; i < nivel;i++) {
						%>							<img src="<%=request.getContextPath()%>/images/pixel.gif"
														width="22" height="9" alt="">
						<%
												}
						%>
												</td>
												<td valign="top" width="5%">
													<%=linkAbrirArvoreEstruturaComeco%><img src="<%=_pathEcar%>/images/icon_bullet_seta.png" alt="" border="0"><%=linkAbrirArvoreEstruturaFim%>
												</td>
												<td title="<%=estruturaEtt.getNomeEtt().trim()%>">
													<font color="<%=estruturaEtt.getCodCor4Ett()%>">
													<%
													String nomeEstrutura = "".equals(estruturaEtt.getNomeEtt()) ? "[Não Informado]" : estruturaEtt.getNomeEtt();
													%> <%=nomeEstrutura%><%="</a>"%>
													</font>
												</td>
											</tr>
										</table>
								<%
										
									
									%>
									</td>
								</tr>	
								
								<%
									validacao = validacao + " if (validar_" + estruturaEtt.getCodEtt() + "(form) == false) { return false;} \n";
								%> 
								
								<span id="<%=estruturaEtt.getCodEtt()%>" style="display:none" 
														bgcolor="<%=estruturaEtt.getCodCor3Ett()%>" class="form">
									<%
									
									List atributos = estruturaDao.getAtributosEstruturaDadosGeraisEhFiltro(estruturaEtt, tipoAcompanhamentoEscolhido);
									
									if(atributos != null){
										Iterator it = atributos.iterator();
										%>
										<script language="javascript">
										<util:validacaoEstruturaAtributoFiltroTag
										 	atributos="<%=atributos%>" 
										 	codEstrutura="<%=estruturaEtt.getCodEtt()%>"
										/>
										</script>
										
										
										<%
										while(it.hasNext()){
											ObjetoEstrutura atributo = (ObjetoEstrutura) it.next();
											%>
											<util:formEstruturaAtributoFiltro atributo="<%=atributo%>"
											itemEstrutura="<%=new ItemEstruturaIett()%>"
											estrutura="<%=estruturaEtt%>"
											desabilitar="<%=new Boolean(false)%>" seguranca="<%=seguranca%>"
											contextPath="<%=request.getContextPath()%>"
											codEstrutura="<%=estruturaEtt.getCodEtt()
																.toString()%>"
											request="<%=request%>" 
											transformarComboBoxListaChecks="<%=new Boolean(true)%>"
											transformarRadioListaChecks="<%=new Boolean(true)%>"
											/>	
											<%
										} 
									} //fim do if(atributos != null){
										
									%>		
									
								</span>		
								
								
								<%
							}//fim do while(itEstrutura.hasNext())
						}// fim do if (listEstrutura != null && listEstrutura.size() > 0)
					}//fim do if (codTipoAcompanhamento != null && codTipoAcompanhamento != ""){
					else{
					
					%>
						<td class="label" style="text-align: left">%</td>
						<td>
							<br>
							<br>
							<b> Não foi escolhido nenhum Tipo de Acompanhamento </b>
							<br>
							<br>
							<br>
						</td>
					
					<% 
					}
					
					%>
					<script language="javascript">
					function validar(form){
						<%=validacao + "return true;"%>
					}
					
					//Carrega a unidade orçamentária quando seleciona o orgao
					function carregaUnidadeOrc(codOrgao, disabled, codEtt){
						var url = _pathEcar + "/acompanhamento/unidadeOrcForm.jsp?codOrgaoUnidadeOrc=" + codOrgao + "&codIett=" +  codEtt + "&disabled=" + disabled;
						openAjax(url, codEtt + "_unidadeOrcamentariaDiv", null, null, true, "../images");
					}
					
					</script>
									
					<!-- termina de imprimir a coluna que faz a identação de acordo com o nivel do item -->
		</table>
	</span>
	<%
//	}
	 %>
		<table style="background-color: #EAEEF4; width: 100%; padding-bottom: 10px;">
			<tr>
				<td colspan="2">
					<label class="label">Etiquetas</label>
				</td>
			</tr>
			<tr>
				<td style="width: 80%;">
					<input type="text" id="nomeEtiqueta" name="nomeEtiqueta" style="text-transform: uppercase; width: 100%;" onkeyup="autoCompleteEtiquetas();"/>
				</td>
				<td style="width: 20%;">
					<input type="button" class="botao" value="Adicionar" onclick="adicionarEtiquetaAoFiltro();"/>
				</td>
			</tr>
		</table>
		<div id="etiquetasSelecionadas" style="background-color: #EAEEF4; min-height: 50px;"></div>
<!--  			<util:barrabotoes pesquisar="Pesquisar" cancelar="Cancelar"/>   -->
				<%if(abaAberta.equals("filtros")){%>
					<util:barrabotoes btn1="Avançar" cancelar="Voltar" esconderCancelar="true"/>
				<%}else{%>
					<util:barrabotoes btn1="Avançar" cancelar="Voltar"/>
				<%}%>
			
<%
} catch (ECARException e){
	org.apache.log4j.Logger.getLogger(this.getClass()).error(e);
	Mensagem.alert(_jspx_page_context, e.getMessageKey());
	%>
	<script>
	document.form.action = "filtroItens.jsp";
	document.submit();
	</script>
	<%
} catch (Exception e){
%>
	<%@ include file="/excecao.jsp"%>
<%
}
%>
<%@ include file="/include/estadoMenu.jsp"%>
</form>

<br>
</div>
</body>
<% /* Controla o estado do Menu (aberto ou fechado) */%>
<%@ include file="/include/ocultarAguarde.jsp"%>
<%@ include file="/include/mensagemAlert.jsp" %>
</html>


<%


//se vier de monitoramento vai diretamente para dados gerais
if (vemMonitoramento != null && vemMonitoramento.equals("S")){   %>
	<script>
		apresentarLayer('dadosGerais');
	</script>
<%}%>
