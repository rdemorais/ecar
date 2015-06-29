<jsp:directive.page import="ecar.dao.FuncaoDao"/>
<jsp:directive.page import="ecar.pojo.FuncaoFun"/><%@ include file="/login/validaAcesso.jsp"%>
<%@ include file="/frm_global.jsp"%>

<%@ taglib uri="/WEB-INF/ecar-util.tld" prefix="util" %>
<%@ taglib uri="/WEB-INF/ecar-combo.tld" prefix="combo" %>
<%@ page import="ecar.pojo.EstruturaEtt" %>
<%@ page import="ecar.pojo.SisAtributoSatb" %>
<%@ page import="ecar.pojo.AtributosAtb" %>
<%@ page import="ecar.dao.EstruturaDao" %>
<%@ page import="ecar.dao.EstruturaAtributoDao" %>
<%@ page import="ecar.dao.SisAtributoDao" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %>
<%
session.removeAttribute("objPesquisa");
%> 

<html lang="pt-br">
<head>
<%@ include file="/include/meta.jsp"%>
<%@ include file="/titulo.jsp"%>
<link rel="stylesheet" href="<%=_pathEcar%>/css/style_<%=configuracaoCfg.getEstilo().getNome()%>.css" type="text/css">
<script language="javascript" src="<%=_pathEcar%>/js/menu_retratil.js" type="text/javascript"></script>
<script language="javascript" src="<%=_pathEcar%>/js/focoInicial.js" type="text/javascript"></script>
<script language="javascript" src="<%=_pathEcar%>/js/frmPadrao.js" type="text/javascript"></script>
<script language="javascript" src="<%=_pathEcar%>/js/validacoes.js" type="text/javascript"></script>
<script language="javascript" src="<%=_pathEcar%>/js/AnchorPosition.js" type="text/javascript"></script>
<script language="javascript" src="<%=_pathEcar%>/js/PopupWindow.js" type="text/javascript"></script>
<script language="javascript" src="<%=_pathEcar%>/js/ColorPicker2.js" type="text/javascript"></script>
<script language="javascript" type="text/javascript">
	var field = "";
	// Create a new ColorPicker object using Window Popup
	var cp = new ColorPicker('window');

	function pickColor(color) {
		field.value = color;
	}
	
	function selecionarCor(campo){
		field = campo;
		cp.select(campo,'pick');		
	}
	
function validarGravar(form){

	if(!validaString(form.nomeEtt, "Nome", true)){
		return false;
	}	
	if(!validaString(form.siglaEtt, "Sigla", true)){
		return false;
	}	

	if(!validaIntervalo(document.form.seqApresentacaoEtt.value, 1, 999)){
		alert("<%=_msg.getMensagem("estrutura.validacao.seqApresentacaoInvalida")%>");
		return false;
	}
	
	if(!validaString(form.codCor1Ett, "Cor de fundo do Título", true)){
		return false;
	}
	
	if(!validaString(form.codCor2Ett, "Cor de fundo do Cabeçalho", true)){
		return false;
	}
	
	if(!validaString(form.codCor3Ett, "Cor de fundo dos Itens", true)){
		return false;
	}
	
	if(!validaString(form.codCor4Ett, "Cor da Fonte", true)){
		return false;
	}
	
	if(trim(form.tamanhoListagemVerticalEtt.value) != "" && !isInteger(form.tamanhoListagemVerticalEtt.value)){
		alert("<%=_msg.getMensagem("estrutura.validacao.tamanhoListagemVerticalEtt.invalido")%>");
		form.tamanhoListagemVerticalEtt.focus();
		return false;
	}

	var checksAtributosEttSuperior = document.getElementsByName('sisAtributoSatbEttSuperior');
	var encontrouCheckedAtributosEttSuperior = false;
	//Avaliar checks marcados dos atributos da estrutura superior 
	for(i = 0; i < checksAtributosEttSuperior.length; i++){
		//Para Dados gerais
		if(checksAtributosEttSuperior[i].id == 'sisAtributoSatbEttSuperior' && checksAtributosEttSuperior[i].checked == true){
			encontrouCheckedAtributosEttSuperior = true;	
			break;
		}
	}
	if(encontrouCheckedAtributosEttSuperior && form.codAtbExibirEstruturaEtt.value != "" && form.indExibirEstruturaEtt.checked == false) {
		alert("<%=_msg.getMensagem("estrutura.validacao.visualizacaoPelaEstruturaSuperior")%>");
		return false;
	}
	if(!encontrouCheckedAtributosEttSuperior && form.codAtbExibirEstruturaEtt.value != "" && form.indExibirEstruturaEtt.checked == false) {
		alert("<%=_msg.getMensagem("estrutura.validacao.visualizacaoPelaEstruturaSuperior")%>");
		return false;
	}
	if(form.indExibirEstruturaEtt.checked == true && form.codAtbExibirEstruturaEtt.value == "") {
		alert("<%=_msg.getMensagem("estrutura.validacao.visualizacaoPelaEstruturaSuperiorGprAtributo")%>");
		return false;
	}
	if(form.indExibirEstruturaEtt.checked == true && form.codAtbExibirEstruturaEtt.value != "" && !encontrouCheckedAtributosEttSuperior) {
		alert("<%=_msg.getMensagem("estrutura.validacao.visualizacaoPelaEstruturaSuperiorAtributos")%>");
		return false;
	}
	
	var checksAtributos = document.getElementsByName('atributosAtb');
	var encontrouCheckedDadosGerais = false;
	var encontrouCheckedPontosCriticos = false;
	var avaliaPontosCriticos = false;
	
	//Avaliar checks marcados das funções 
	for(i = 0; i < checksAtributos.length; i++){
		//Para Dados gerais
		if(checksAtributos[i].id == 'dadosGerais' && checksAtributos[i].checked == true){
			encontrouCheckedDadosGerais = true;	
			break;
		}
	}

	if(!encontrouCheckedDadosGerais && document.getElementsByName('virtual')[0].checked == false){
		alert("<%=_msg.getMensagem("estrutura.validacao.atributosAtb.obrigatorio",new String[]{new FuncaoDao(request).getFuncaoDadosGerais().getLabelPadraoFun()})%>");
		return false;	
	}
	

	//Para Pontos Criticos
	var checksAtributosPontosCriticos = document.getElementsByName('funcaoFun');
	for(i = 0; i < checksAtributosPontosCriticos.length; i++){
		//Verifica se o check de pontos criticos está marcado
		if(checksAtributosPontosCriticos[i].id == 'pontosCriticos' && checksAtributosPontosCriticos[i].checked == true) {
			avaliaPontosCriticos = true;
			for(z = 0; z < checksAtributos.length; z++){
				if(checksAtributos[z].id == 'pontosCriticos' && checksAtributos[z].checked == true){
					encontrouCheckedPontosCriticos = true;
					break;
				}
			}

			//Se encontrou o check marcado aborta o loop
			if (encontrouCheckedPontosCriticos) {
				break;
			} 
		}
	}

	//verifica se deve avaliar o ponto critico e veriica se encontrou item do ponto critico marcado.
	if(avaliaPontosCriticos && !encontrouCheckedPontosCriticos){
		alert("<%=_msg.getMensagem("estrutura.validacao.atributosAtb.obrigatorio",new String[]{new FuncaoDao(request).getFuncaoPontosCriticos().getLabelPadraoFun()})%>");
		return false;	
	}
	
	if(!validaAtributos(form)){
		return false;
	}
	
	return true;
}

function reload(form){
	form.action = "frm_inc.jsp";
	form.submit();
}

function validaFuncaoDadosGerais(form){
	var indDadosGeraisMarcado = true;
	for(i = 0; i < form.funcaoFun.length; i++){
		if(form.funcaoFun[i].value == <%=new FuncaoDao(request).getCodFuncaoDadosGerais()%> && form.funcaoFun[i].checked == false){
			indDadosGeraisMarcado = false;
		}
	}
	return indDadosGeraisMarcado;
}

function validaAtributos(form){

	/*
	* Nivel atributos, se o Atributo unidade Orcamentaria estiver checado,
	* obrigatoriamente o Orgão responsavel 1  tbm tera de estar ativo!
	*/

	var orgRespons1 = false;
	var unidadeOrcamentaria = false;
	
	for(i = 0; i < form.atributosAtb.length; i++){
	
		// verifica se o campo Unidade Orçamentaria esta ativo
		if(form.atributosAtb[i].checked == true && form.atributosAtb[i].value == 35){
			unidadeOrcamentaria = true;
		}
		// verifica se o campo Orgão Responsavel 1 esta ativo
		if(form.atributosAtb[i].checked == true && form.atributosAtb[i].value == 12){
			orgRespons1 = true;
		}		
	}
	
	// se unidade orcamentaria esta ativa e orgao responsavel 1 não
	if(unidadeOrcamentaria == true && orgRespons1 == false){
		alert("<%=_msg.getMensagem("estrutura.validacao.atributos.unidadeOrcamentaria")%>");
		return false;
	}
	return true;
}

function carregarInicio(){
	onLoad(document.form);
	}


</script>
</head>

<!-- sempre colocar o foco inicial no primeiro campo -->
<body onload="javascript:carregarInicio();">


<%@ include file="/cabecalho.jsp" %>
<%@ include file="/divmenu.jsp"%>

<div id="conteudo">

<form name="form" method="post">
	<input type="hidden" name="hidAcao" value="">
	<input type="hidden" name="codEstruturaEtt" value="">
	
	<!-- TITULO -->
	<%@ include file="/titulo_tela.jsp"%>
<%

boolean ehPesquisa = false;
boolean ehNavegacao = false;
boolean ehInclusao = true;
boolean ehAlteracao = false;

try{

	EstruturaEtt estrutura = new EstruturaEtt();;
	EstruturaDao estruturaDao = new EstruturaDao(request);
	EstruturaAtributoDao estruturaAtributoDao = new EstruturaAtributoDao(request);
	FuncaoDao funcaoDao = new FuncaoDao(request);
	SisAtributoDao sisAtributoAtbDao = new SisAtributoDao(request);
	
	List lAtributosDadosGerais = new ArrayList();
	List lAtributosPontosCriticos = new ArrayList();
	List lFuncoes = new ArrayList();
	List lFuncoesAcompanhamento = new ArrayList();
	FuncaoFun funcaoDadosGerais = funcaoDao.getFuncaoDadosGerais();
	FuncaoFun funcaoPontosCriticos = funcaoDao.getFuncaoPontosCriticos(); 
	
	String selected = "";
	
	//Exibir estrutura de acordo com atributo estrutura-pai
	SisAtributoSatb sisAtributoSatb;
	List listaAtributoEstrutura = new ArrayList(0);
	String selectedAtributo = "";
	List atributosEttSuperior = new ArrayList();
	List lAtributosEttSuperior = new ArrayList();
	List lAtributosEttSuperiorEditaveis = new ArrayList();
	List atributosEttSuperiorEditaveis = new ArrayList();
	
	
	List atributosPermitidosDadosGerais = new ArrayList();
	List atributosPermitidosPontosCriticos = new ArrayList();
	List funcoesPermitidas = new ArrayList();
	AtributosAtb atributoSelecionado = null;
	EstruturaEtt estruturaPai = null;
	
	boolean alteraNivel = true;
		
	//se carregou a pagina, recupera os campos que foram alterados (pelo request) e seta na estrutura 
	if(Pagina.getParam(request, "indCarregouPagina") != null){
		estruturaDao.setEstrutura(request, estrutura, true, true, true);
		
		//guarda as funções selecionadas
		lFuncoes = estruturaDao.getFuncoesById(estrutura);
		//guarda os atributos de dados gerais selecionados
		lAtributosDadosGerais = estruturaDao.getAtributosById(estrutura, funcaoDadosGerais);
		//guarda as funções de acompanhamento selecionadas
		lFuncoesAcompanhamento = estruturaDao.getFuncoesAcompanhamentoById(estrutura);
		//guarda os atributos de pontos criticos que foram selecionados
		lAtributosPontosCriticos = estruturaDao.getAtributosById(estrutura, funcaoPontosCriticos);
		
		//verifica os campos selecionados na lista
		if(estrutura.getEstruturaEtt() != null){
			estruturaPai = estrutura.getEstruturaEtt();
			//guarda o codigo da estrutura pai
			selected = estruturaPai.getCodEtt().toString();
			//guarda a coleção dos atributos de dados gerais que podem ser selecionados 
			atributosPermitidosDadosGerais = estruturaDao.getAtributosPermitidos(estruturaPai, funcaoDadosGerais);
			//guarda a coleção dos atributos de pontos críticos que podem ser selecionados
			atributosPermitidosPontosCriticos = estruturaDao.getAtributosPermitidos(estrutura, funcaoPontosCriticos);
			//guarda a colecao das funcoes que podem ser selecionadas
			funcoesPermitidas = estruturaDao.getFuncoesPermitidas(estruturaPai);
			//monta a lista de grupos de atributos a partir do nivel superior
			listaAtributoEstrutura = estruturaAtributoDao.getAtributosVisualizarEttSuperior(estruturaPai, funcaoDadosGerais);
			
		
			
			// se tiver selecionado um grupo de atributo
			if (estrutura.getAtributoAtbExibirEstruturaEtt() != null){	
				atributoSelecionado = estrutura.getAtributoAtbExibirEstruturaEtt();
				//guarda o codigo do grupo de atributo que foi selecionado
				selectedAtributo = atributoSelecionado.getCodAtb().toString();
				
				Iterator itSisAtributos;
				
				//guarda toda a  lista de atributos do grupo de atributo selecionado
				if(atributoSelecionado.getSisGrupoAtributoSga() != null && atributoSelecionado.getSisGrupoAtributoSga().getSisAtributoSatbs() != null){
					itSisAtributos = atributoSelecionado.getSisGrupoAtributoSga().getSisAtributoSatbs().iterator();
				
					while (itSisAtributos.hasNext()){
						SisAtributoSatb sisAtributo = (SisAtributoSatb) itSisAtributos.next();
						
						if (sisAtributo.isAtivo()){
							atributosEttSuperior.add(sisAtributo);
						}
					}
				}
				
				//recupera toda a lista de atributos que foi selecionado na estrutura
				if(estrutura.getSisAtributoSatbEttSuperior() != null)
					lAtributosEttSuperior = estruturaAtributoDao.getAtributosById(estrutura.getSisAtributoSatbEttSuperior());
			}	

		} else {
			
			//se a estrutura nao possuir uma estrutura pai
			atributosPermitidosDadosGerais = estruturaDao.getAtributosPermitidos(null, funcaoDadosGerais);
			atributosPermitidosPontosCriticos = estruturaDao.getAtributosPermitidos(null, funcaoPontosCriticos);
			funcoesPermitidas = estruturaDao.getFuncoesPermitidas(null);
			
		}
		
		
	} else {
		
		estrutura = new EstruturaEtt();
		atributosPermitidosDadosGerais = estruturaDao.getAtributosPermitidos(null, funcaoDadosGerais);
		atributosPermitidosPontosCriticos = estruturaDao.getAtributosPermitidos(null, funcaoPontosCriticos);
		funcoesPermitidas = estruturaDao.getFuncoesPermitidas(null);
	}
	
	
	
	


	
	
	
	String _pesqdisabled ="";
%>
	<util:linkstop incluir="frm_inc.jsp" pesquisar="frm_pes.jsp"/> 
	<util:barrabotoes incluir="Gravar" cancelar="Cancelar"/>

	<%@ include file="form.jsp"%>

	<util:barrabotoes incluir="Gravar" cancelar="Cancelar"/>	

<%
	} catch (ECARException e) {
		org.apache.log4j.Logger.getLogger(this.getClass()).error(e);
		/* redireciona para o controle */
		%>
		<script language="javascript" type="text/javascript">
		document.form.action = "ctrl.jsp";
		document.form.submit();
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
<%@ include file="/include/mensagemAlert.jsp"%>
</html>