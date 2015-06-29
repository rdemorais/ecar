<%@page import="comum.util.Util"%>
<%@ include file="/login/validaAcesso.jsp"%>
<%@ include file="/frm_global.jsp"%>

<%@ page import="ecar.util.Dominios"%>
<%@ page import="ecar.pojo.EstruturaEtt"%>
<%@ page import="ecar.pojo.SituacaoSit"%>
<%@ page import="ecar.pojo.AcompReferenciaAref" %> 
<%@ page import="ecar.pojo.ItemEstruturaIett" %>  
<%@ page import="ecar.pojo.OrgaoOrg" %>  
<%@ page import="ecar.pojo.UsuarioUsu" %> 
<%@ page import="ecar.pojo.SisAtributoSatb" %> 
<%@ page import="ecar.dao.AcompReferenciaDao" %> 
<%@ page import="ecar.dao.AcompReferenciaItemDao" %>  
<%@ page import="ecar.dao.ItemEstruturaDao" %> 
<%@ page import="ecar.dao.SisAtributoDao" %> 
<%@ page import="java.util.Iterator" %>  
<%@ page import="java.util.List" %>  
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.Set" %>
<%@ page import="java.util.Map" %>
<%@ page import="ecar.pojo.AcompReferenciaItemAri" %>
<%@ page import="ecar.pojo.AcompRefLimitesArl" %>
<%@ page import="ecar.bean.AtributoEstruturaListagemItens" %>
<%@ page import="java.util.Date" %>
<%@ page import="comum.util.Data" %>
<%@ page import="ecar.pojo.TipoAcompanhamentoTa" %>
<%@ page import="ecar.pojo.TipoFuncAcompTpfa" %>
<%@ page import="ecar.dao.ConfiguracaoDao" %>
<%@ page import="ecar.pojo.ConfiguracaoCfg" %>
<%@ page import="ecar.dao.TipoAcompanhamentoDao" %>
<%@ page import="ecar.dao.AcompRelatorioDao" %>
<%@ page import="ecar.pojo.ConfigMailCfgm" %>
<%@ page import="ecar.dao.ConfigMailCfgmDAO" %>

<%
Date dataInicio = Data.getDataAtual();
String existeAriFaltandoParecer = "N";
%>

<%@ taglib uri="/WEB-INF/ecar-util.tld" prefix="util" %>


<%@page import="ecar.action.ActionEstrutura"%>
<%@page import="ecar.dao.EstruturaDao"%>
<%@page import="java.util.HashMap"%>
<%@page import="comum.util.ConstantesECAR"%>
<html lang="pt-br">
<head> 
<META HTTP-EQUIV="Expires" CONTENT="never">
<%@ include file="/titulo.jsp"%>
<link rel="stylesheet" href="<%=_pathEcar%>/css/style_<%=configuracaoCfg.getEstilo().getNome()%>.css" type="text/css">
<script language="javascript" src="<%=_pathEcar%>/js/menu_retratil.js"></script>
<script language="javascript" src="<%=_pathEcar%>/js/focoInicial.js"></script>
<script language="javascript" src="<%=_pathEcar%>/js/validacoes.js"></script>
<%
ConfigMailCfgmDAO mailDao = new ConfigMailCfgmDAO();
ConfigMailCfgm    mail    = (ConfigMailCfgm) mailDao.buscar(ConfigMailCfgm.class, Dominios.CFG_MAIL_CRIACAO_REGISTRO_ACOMPANHAMENTO);
%>
<script language="Javascript">

function validaCheck(campoInicial, form, codigoItem, codigoPai,ehVirtual,codOrgao){

	var arrayFilhos;

	/* Verifica quais os filhos de uma estrutura virtual */
	if (codigoPai == null && ehVirtual){
		arrayFilhos = montarArray(campoInicial);
	}

	/* Só marca os filhos quando está marcando o pai, caso tire a marcação não fazer nada com o filho. */
	if (campoInicial.checked) {
		marcaFilho(campoInicial, form, codigoItem,arrayFilhos,codOrgao);
	}
	/* Marca as estruturas virtuais */
	checkTodasOcorrencias(campoInicial,form,codigoItem);

	arrayFilhos = null;
}

/**
 * Monta um array com os filhos do item clicado, caso haja
 */
function montarArray(campoClicado) {

	var listaFilhos = new Array();
	var el = document.getElementsByName("iett-virtual");
	var i,posArray=0;
	var x;
	var idPai,idElemento;
	
	for (i=0;i < el.length;i++) {
		idElemento = el[i].id;
		idPai = idElemento.substring(3,el[i].id.length);
		if (idPai == campoClicado.id) {
			if (!inArray (listaFilhos,el[i].value)) {
				listaFilhos[posArray] = el[i].value;
				posArray++;
			}
		}
	}		

	return listaFilhos;
}

function inArray (listaFilhos,valor){

	var ret = false;
	var i=0;
	 
	for (i;i<listaFilhos.length;i++){

		if (listaFilhos[i] == valor){
			ret = true;
			break;
		}
	} 

	return ret;
}
//Busca todas as ocorrencias 
function checkTodasOcorrencias(campoInicial,form,codigoItem) {

	var nome = campoInicial.name;
	var i=0;
	var x=0;
	
	if (nome == "iett") { //O checkbox clicado foi o iett, exibido no grupo de acompanhamentos.
		var el = document.getElementsByName("iett-virtual");

		for(i;i < el.length;i++){//Marca os checkboxs no grupo de estruturas virtuais que possuem o mesmo valor do elemento clicado no grupo de acompanhamentos.  
			if (el[i].value == codigoItem) {
				el[i].checked = campoInicial.checked;
			}
		}
	} else { //O checkbox clicado foi o iett-virtual, exibido no grupo de estruturas virtuais.
		var elIett = document.getElementsByName("iett");

		for(i;i < elIett.length;i++){//Marca os checkboxs no grupo de acompanhamentos que possuem o mesmo valor do elemento clicado no grupo de estrutura Virtual.  
			if (elIett[i].value == codigoItem) {
				elIett[i].checked = campoInicial.checked;
			}
		}

		var elIettV = document.getElementsByName("iett-virtual");

		for(x;x < elIettV.length;x++){//Marca os checkboxs no grupo de estruturas virtuais que possuem o mesmo valor do elemento clicado no proprio grupo de Estruturas Virtuais porém são diferentes do elemento clicado    
			if (elIettV[x].value == codigoItem && elIettV[i] != campoInicial) {
				elIettV[x].checked = campoInicial.checked;
			}
		}
		
		
	}

}


function marcaFilho(campoInicial, form, codigoItem,arrayFilho,codOrgao){ 
	//var estado = campoInicial.checked;
	var estado = true;
	var campo = eval("document.form.pai" + codigoItem);		

	//identifica quando existem itens cujo ID é pai+codigoItem, portanto obtem a lista de filhos que possui o elemento clicado como pai.
	if(verificaChecksById(form, "pai" + codigoItem) == 1){
		if (arrayFilho != null) {
			if (inArray(arrayFilho,codigoItem) && (!campo.disabled)) {
				campo.checked = estado;	
			}	
		} else if(!campo.disabled && campo.getAttribute("idorgao") == codOrgao){
			campo.checked = estado;		
		} 
		marcaFilho(campoInicial, form, campo.value,arrayFilho,codOrgao);
	} else if (verificaChecksById(form, "pai" + codigoItem) > 1){
		for(var i = 0; i < verificaChecksById(form, "pai" + codigoItem); i++){
			if (arrayFilho != null) {
				if ((inArray(arrayFilho,codigoItem)||(inArray(arrayFilho,campo[i].value))) && (!campo[i].disabled)) { //O teste envia para validação no metodo inArray o codigoItem(item atual) e o campo[i].value os filhos do codigoItem(item atual).
					campo[i].checked = estado;	
				}
			} else if(!campo[i].disabled && campo[i].getAttribute("idorgao") == codOrgao){
				campo[i].checked = estado;
			} 
			marcaFilho(campoInicial, form, campo[i].value,arrayFilho,codOrgao);
		}
	}			
	
}

function verificaPaiSelecionado(campoInicial, form, codigoPai){
	var el = document.getElementsByTagName("INPUT");
	var i = 0, n = 0;
	
	while (i < el.length)
	{
		if(el.item(i) != null){
			if (el.item(i).value == codigoPai){
				if(el.item(i).checked){
					campoInicial.checked = true;
				}
			} 		
		}
		i++;
	}
	
	return;
	
	/*
	if(form.iett.length > 1) {
		for(i = 0; i < form.iett.length; i++) {
			if (form.iett[i].value == codigoPai){
				if(form.iett[i].checked){
					campoInicial.checked = true;
				}
			} 		
		}
	} else if(form.iett.length == 1) {
		if (form.iett.value == codigoPai){
			if(form.iett.checked){
				campoInicial.checked = true;
			}
		} 		
	}
	*/
	
}

function gravar(){
	var numChecks = verificaChecks(document.form, "iett");
	var existeItemMarcado = false;
	
	if( '<%=Pagina.getParamStr(request, "indDataAlterada")%>' == 'S' ) {
		
		if (document.form.hidAcao.value == "incluir"){
		
			if('<%=mail.getAtivoCfgm()%>' == 'S'){
			
				if(document.form.obrigatorio.value == 'S' || (document.form.obrigatorio.value == 'N' && confirm('<%=_msg.getMensagem("acompanhamento.periodoReferencia.criacao.autorizaEnviaEmail")%>') == true )) {
					
					document.form.autorizarMail.value = 'S';
					document.form.criacaoPeriodo.value = 'S';
				} 
			}			
		} else {					
			if('<%=mail.getAtivoCfgm()%>' == 'S'){
			
				if(document.form.obrigatorio.value == 'S' || (document.form.obrigatorio.value == 'N' && confirm('<%=_msg.getMensagem("acompanhamento.periodoReferencia.alteracao.autorizaEnviaEmail")%>') == true )) {
					document.form.autorizarMail.value = 'S';
					document.form.alteraDataRegistro.value =  '<%=Pagina.getParamStr(request, "indDataRegistroAlterada")%>';
					document.form.alteraDataParecer.value =  '<%=Pagina.getParamStr(request, "indDataParecerAlterada")%>';
				}	
			} 
		}

	}

	/*
	if(numChecks > 1) {
		for(i = 0; i < form.iett.length && !existeItemMarcado; i++) {
			if(form.iett[i].checked) {
				existeItemMarcado = true;
			}
		 }
	} else  {
		
		if(form.iett != null && form.iett.checked) {
			existeItemMarcado = true;
		}
	}
	*/
	existeItemMarcado = true;
	
	if(!existeItemMarcado && document.form.hidAcao.value == "incluir") {
		alert('<%=_msg.getMensagem("periodoReferencia.validacao.itemSelecionado")%>');
	}
	else if(verificaAlterados()){
		if (document.form.existeAriFaltandoParecer.value == "S"){
			if(confirm('<%=_msg.getMensagem("acompanhamento.periodoReferencia.existeAriFaltandoParecer.aviso")%>') == true) {
				document.form.existeAriFaltandoParecerConfirma.value = 'S';
			} else {
				document.form.existeAriFaltandoParecerConfirma.value = 'N';
			}
		}
		document.form.action = "ctrlGeral.jsp";
		document.form.submit();	
	}
}

function voltar(){
	if (document.form.hidAcao.value == "incluir"){
		document.form.action = "frm_inc.jsp";
	} else {
		document.form.action = "frm_alt.jsp";
	}
	document.form.submit();
}

//Seleciona os recomendados
function selectRecomendados(form) {
	for(i = 0; i < form.iett.length; i++){
		if(form.iett[i].disabled == false) {
			if(form.iett[i].getAttribute("recomendado")) {
				form.iett[i].checked = !form.iett[i].checked;
			}	
		}		
	 }
}

//Seleciona os prioritarios
function selectPrioritarios(form) {
	for(i = 0; i < form.iett.length; i++){
		if(form.iett[i].disabled == false) {
			if(form.iett[i].getAttribute("prioritario")) {
				form.iett[i].checked = !form.iett[i].checked;
			}	
		}		
	 }
}

// Função para selecionar todos os Checkbox
function selectAll(form){
	
	var numChecks = verificaChecks(form, "iett");
	var numChecksVirtual = verificaChecks(form, "iett-virtual");
	var iettVirtual = document.getElementsByName("iett-virtual");
	
	if(numChecks > 1){
		if(form.todos.checked){
			for(i = 0; i < form.iett.length; i++){
				if(form.iett[i].disabled == false)
					form.iett[i].checked = true;	
			 }
			 
			 for(i = 0; i < form.iett.length; i++){
				if(form.iett[i].disabled == false)
					form.iett[i].checked = true;	
			 }
			
		}else{
			for(i = 0; i < form.iett.length; i++){
				if(form.iett[i].disabled == false)
					form.iett[i].checked = false;		
			}
		}
	} else if(numChecks == 1){
		if(form.todos.checked){
			if(form.iett.disabled == false) {
				form.iett.checked = true;	
			}
		}
		else{
			if(form.iett.disabled == false)
				form.iett.checked = false;		
		}
	}
	
	
	if(numChecksVirtual > 1){
		if(form.todos.checked){
			for(i = 0; i < iettVirtual.length; i++){
				if(iettVirtual[i].disabled == false)
					iettVirtual[i].checked = true;	
			 }
			 
			 for(i = 0; i < iettVirtual.length; i++){
				if(iettVirtual[i].disabled == false)
					iettVirtual[i].checked = true;	
			 }
			
		}else{
			for(i = 0; i < iettVirtual.length; i++){
				if(iettVirtual[i].disabled == false)
					iettVirtual[i].checked = false;		
			}
		}
	} else if(numChecksVirtual == 1){
		if(form.todos.checked){
			if(iettVirtual.disabled == false) {
				iettVirtual.checked = true;	
			}
		}
		else{
			if(iettVirtual.disabled == false)
				iettVirtual.checked = false;		
		}
	}
	
	
	
}
</script>

</head>

<%
request.setAttribute("exibirAguarde", "true");
%>
<%@ include file="/cabecalho.jsp" %>
<%@ include file="/divmenu.jsp"%> 

<body> 
<div id="conteudo">
<!-- TÍTULO -->
<%@ include file="/titulo_tela.jsp"%>

<H1>Per&iacute;odo de Refer&ecirc;ncia: <%=Pagina.trocaNull(session.getAttribute("periodoRef"))%></H1>

<%
List acompanhamentosAlterados = new ArrayList();
%>

<div id="subconteudo">

<form name="form" method="post">
		
<%
ItemEstruturaDao itemDao                      = new ItemEstruturaDao(request);
AcompReferenciaDao acompReferenciaDao         = new AcompReferenciaDao(request);
AcompReferenciaItemDao acompReferenciaItemDao = new AcompReferenciaItemDao(request);
SisAtributoDao sisAtributoDao                 = new SisAtributoDao(request);
AcompRelatorioDao acompRelatorioDao           = new AcompRelatorioDao(request);

UsuarioUsu usuario = ((ecar.login.SegurancaECAR)session.getAttribute("seguranca")).getUsuario();

/* Lista com os níveis de planejamentos */
String niveisPlanejamento = Pagina.getParamStr(request, "niveisPlanejamento");
List listNiveis = new ArrayList();
String[] niveis = niveisPlanejamento.split(":");
for(int n = 0; n < niveis.length; n++){
	String codNivel = niveis[n];
	if(!"".equals(codNivel)){
		listNiveis.add(sisAtributoDao.buscar(SisAtributoSatb.class, Long.valueOf(codNivel)));
	}
}
%>
<input type="hidden" name="codUsuario" value="<%=usuario.getCodUsu()%>"> 
<input type="hidden" name="hidAcao" value="<%=Pagina.getParamStr(request, "hidAcao")%>">
<input type="hidden" name="codigoAcomp" value="<%=Pagina.trocaNull(Pagina.getParamStr(request, "codigo"))%>">
<input type="hidden" name="codigo" value="<%=Pagina.getParamStr(request, "codigo")%>">
<input type="hidden" name="autorizarMail" value="">
<input type="hidden" name="criacaoPeriodo" value="N">
<input type="hidden" name="alteraDataParecer" value="N">
<input type="hidden" name="alteraDataRegistro" value="N">
<input type="hidden" name="niveisPlanejamento" value="<%=niveisPlanejamento%>">
<input type="hidden" name="obrigatorio" value="<%=mail.getIndEnvioObrigatorio()%>">
<%
List listaAcompReferencia = (List) session.getAttribute("listaAcompReferencia");

boolean primeiro = true;
if (listaAcompReferencia.isEmpty() == false) {
	
	Iterator itAcompRef = listaAcompReferencia.iterator();
	
	while (itAcompRef.hasNext()) {
		AcompReferenciaAref acompanhamento = (AcompReferenciaAref)itAcompRef.next();

		try {
			if (primeiro) {
				primeiro = false;
%>
				 
				<table width="100%" border="0" cellpadding="0" cellspacing="0"> 
					<tr><td class="espacador"><img src="<%=_pathEcar%>/images/pixel.gif"></td></tr>
					<tr class="linha_titulo" align="right">
<%
				boolean estaAlterando = false;
				String labelBotao = "Gerar Acompanhamento";
				
				//se for alteração
				if(acompanhamento.getCodAref() != null) {
					acompanhamento = (AcompReferenciaAref) acompReferenciaDao.buscar(AcompReferenciaAref.class, acompanhamento.getCodAref());
					List listArl = new ArrayList(acompanhamento.getAcompRefLimitesArls());
					AcompRefLimitesArl arl = null;
					if( listArl != null && listArl.size() > 0 ) {
						Iterator itArl = listArl.iterator();
						
						while( itArl.hasNext() ) {
							arl = (AcompRefLimitesArl) itArl.next(); %> 
							
							<!-- data limite para (pega da tela anterior e do banco) ... -->
							<input type="hidden" name="prazoFinalPara<%=arl.getTipoFuncAcompTpfa().getCodTpfa()%>" value="<%=Pagina.getParamStr(request, "prazoFinalPara"+arl.getTipoFuncAcompTpfa().getCodTpfa())%>">
							<input type="hidden" name="prazoFinalParaAnt<%=arl.getTipoFuncAcompTpfa().getCodTpfa()%>" value="<%=Pagina.getParamStr(request, "prazoFinalParaAnt"+arl.getTipoFuncAcompTpfa().getCodTpfa())%>"> <%
						}				
					} 
					// apenas para os dados do Limite de Acompanhamento Fisico %>
					<input type="hidden" name="dataLimiteAcompFisicoArefAlt" value="<%=Pagina.getParamStr(request, "dataLimiteAcompFisicoArefAlt")%>">
					<input type="hidden" name="dataLimiteAcompFisicoArefAnt" value="<%=Pagina.getParamStr(request, "dataLimiteAcompFisicoArefAnt")%>"> 
					<%

				}
				
				if(acompanhamento.getAcompReferenciaItemAris() != null && acompanhamento.getAcompReferenciaItemAris().size() > 0) {
						labelBotao = "Alterar Acompanhamento";
						estaAlterando = true;
				} %>
						<td>
							<input type="button" value="<%=labelBotao%>" class="botao" onclick="javascript:gravar()"> 
							<input type="button" value="Cancelar" class="botao" onclick="javascript:voltar()">
						</td> 
					</tr>
				</table>
						
				<table width="100%" border="0" cellpadding="0" cellspacing="0">	
					<tr><td class="espacador" colspan="5"><img src="<%=_pathEcar%>/images/pixel.gif"></td></tr>
					<tr class="linha_subtitulo">
						<td><input type="checkbox" class="form_check_radio" name="todos" onclick="javascript:selectAll(document.form)">N&iacute;vel/Sub-N&iacute;vel</td>
					</tr>
					<tr class="linha_subtitulo">
						<td><input type="checkbox" class="form_check_radio" name="todosR" onclick="javascript:selectRecomendados(document.form)">Marcar/Desmarcar Recomendados</td>
					</tr>
					<tr class="linha_subtitulo">
						<td><input type="checkbox" class="form_check_radio" name="todosR" onclick="javascript:selectPrioritarios(document.form)">Marcar/Desmarcar Priorit&aacute;rios</td>
					</tr>
					<tr><td class="espacador" colspan="5"><img src="<%=_pathEcar%>/images/pixel.gif"></td></tr>	
<%
			}
%>
			<tr><td class="espacador" colspan="5"><img src="<%=_pathEcar%>/images/pixel.gif"></td></tr>
			
			<%if (acompanhamento.getOrgaoOrg() != null) {%>
				
				<table width="100%" border="0" cellpadding="0" cellspacing="0">	
					<tr class="linha_subtitulo">
					<%
						String orgaoDesc = "";
						orgaoDesc = acompanhamento.getOrgaoOrg().getDescricaoOrg();
					%>
					<td ><%=orgaoDesc%></td>
				</tr>
				
				<tr><td class="espacador" colspan="5"><img src="<%=_pathEcar%>/images/pixel.gif"></td></tr>	
				
				</table>
				
			<%} else {
				if (Dominios.NAO.equals(acompanhamento.getTipoAcompanhamentoTa().getIndSepararOrgaoTa())) {%>
			
					<tr class="linha_subtitulo">
						<td><%=acompanhamento.getTipoAcompanhamentoTa().getDescricaoTa()%></td>
					</tr>
					<tr>
						<td class="espacador" colspan="5"><img src="<%=_pathEcar%>/images/pixel.gif"></td>
					</tr>	
				<%}else{ %>
					<table width="100%" border="0" cellpadding="0" cellspacing="0">
					<tr class="linha_subtitulo">
						<td><%=configuracao.getLabelAgrupamentoItensSemOrgao() %></td>
					</tr>
					<tr>
						<td class="espacador" colspan="5"><img src="<%=_pathEcar%>/images/pixel.gif"></td>
					</tr>	
					</table>
				
				<%} %>
			<%}%>
			<%
			ValidaPermissao validaPermissao = new ValidaPermissao(); 
			boolean acessoSomenteUsuariosOrgaos = validaPermissao.permissaoAcessoReferenciaSeusOrgaos(acompanhamento.getTipoAcompanhamentoTa() , seguranca.getGruposAcesso());
			OrgaoOrg orgao = null;
			if(acompanhamento.getOrgaoOrg() != null && !"".equals(acompanhamento.getOrgaoOrg().getCodOrg())){	
				orgao = (OrgaoOrg) itemDao.buscar(OrgaoOrg.class, acompanhamento.getOrgaoOrg().getCodOrg());
			}
			
			String tipoAcesso = "";
			
			/* Verifica o tipo de acesso (Monitoramento, secretaria ou outra secretaria) */
			if("S".equals(acompanhamento.getTipoAcompanhamentoTa().getIndMonitoramentoTa()))
				tipoAcesso = validaPermissao.EM_MONITORAMENTO;
			else
				if(seguranca.getUsuario().getOrgaoOrgs().contains(acompanhamento.getOrgaoOrg()))
					tipoAcesso = validaPermissao.PROPRIA_SECRETARIA;
				else
					tipoAcesso = validaPermissao.OUTRAS_SECRETARIAS;
			
			
			TipoAcompanhamentoTa tipoAcompanhamentoTa = null;
			TipoAcompanhamentoDao tipoAcompanhamentoDao = new TipoAcompanhamentoDao(request);
			if (acompanhamento.getTipoAcompanhamentoTa() != null) {
				tipoAcompanhamentoTa = (TipoAcompanhamentoTa) tipoAcompanhamentoDao.buscar(TipoAcompanhamentoTa.class, acompanhamento.getTipoAcompanhamentoTa().getCodTa());
			}
			
			List[] listas = null;
			List mostradosEmTela = new ArrayList();
			List selecionaveis = new ArrayList();	
			List listItensGravados = new ArrayList();
			ActionEstrutura action = new ActionEstrutura();
			List descendentesEstrutura = null;
			EstruturaDao estruturaDao = new EstruturaDao(request);
			EstruturaEtt estruturaNivelGeracao = null;
			boolean existeMesmaReferenciaDiaMesAno = acompReferenciaDao.existeMesmaReferenciaDiaMesAno(acompanhamento);
			Map mapItensMostradosEmTelaAgrupadoEstruturaVirtual = new HashMap(); 
			
			
			//se for uma alteração ou nao existir uma outra referencia com dia, mes e ano igual do mesmo órgão
			if(acompanhamento.getCodAref() != null || (!existeMesmaReferenciaDiaMesAno)) {
				// Itens gravados para o acompanhamento
				//se for alteração
				if(acompanhamento.getCodAref() != null) {
					listItensGravados = acompReferenciaItemDao.getListaItensAcompanhamento(acompanhamento);
					/*
					if(acompRelatorioDao.verificarInexistenciaArel(acompanhamento, listItensGravados)) {
						existeAriFaltandoParecer = "S";
					}
					*/
				}
				
				//acompanhamento
				if (acompanhamento.getCodAref() != null && 
					acompanhamento.getTipoAcompanhamentoTa() != null &&
					acompanhamento.getTipoAcompanhamentoTa().getIndSepararOrgaoTa() != null &&
					acompanhamento.getTipoAcompanhamentoTa().getIndSepararOrgaoTa().equals(Dominios.SIM) &&
					acompanhamento.getOrgaoOrg() != null && 
					acompanhamento.getOrgaoOrg().getIndAtivoOrg().equals(Dominios.INATIVO)){
					listas = itemDao.getItensAlterarAcompanhamentoPorOrgao(listItensGravados, orgao, acompanhamento);
					mostradosEmTela = listas[0];
					selecionaveis = listas[1];
				} else {
					listas = itemDao.getItensGerarAcompanhamento(orgao, acompanhamento, seguranca.getGruposAcesso(), tipoAcesso, listNiveis, acessoSomenteUsuariosOrgaos);
					mostradosEmTela = listas[0];
					selecionaveis = listas[1];	
				}
				
				if (acompanhamento.getTipoAcompanhamentoTa().getEstruturaNivelGeracaoTa() != null) {
					estruturaNivelGeracao = (EstruturaEtt) estruturaDao.buscar(EstruturaEtt.class, acompanhamento.getTipoAcompanhamentoTa().getEstruturaNivelGeracaoTa().getCodEtt()); 
					descendentesEstrutura = estruturaDao.getDescendentes(estruturaNivelGeracao);
					
				}
				mapItensMostradosEmTelaAgrupadoEstruturaVirtual = action.montarMapItensPorEstruturaVirtual(mostradosEmTela);
								
			%>
			</table>
				
				
			<table width="100%" border="0" cellpadding="0" cellspacing="0">	<% 
		
				//Começa a exibir os itens de estrutura
				Iterator it = mostradosEmTela.iterator();
				
				while(it.hasNext()){
						%>
						<tr>
						<%
				        AtributoEstruturaListagemItens atbList = (AtributoEstruturaListagemItens) it.next();
				        ItemEstruturaIett itemEstrutura = atbList.getItem();
						
				        if (itemDao.verificaNivelHierarquicoEstrutura(tipoAcompanhamentoTa.getEstruturaNivelGeracaoTa(), itemEstrutura.getEstruturaEtt())){
				        
				        	out.println(montaLinhaItens(itemEstrutura,acompanhamento,acompReferenciaItemDao,listItensGravados,selecionaveis,atbList,acompanhamentosAlterados,false, tipoAcompanhamentoTa, request, descendentesEstrutura, estruturaDao, estruturaNivelGeracao));
				        }
						%>
						</tr>
						
					<%} // fim do while
					
					if(mostradosEmTela.isEmpty()){
					%>	
						<tr>Nenhum item disponível para o acompanhamento.<tr>
					<% 	
					}
					
					
			
			} else {
				
				%>	
					<tr>Já existe um acompanhamento para o dia/mês/ano informado.<tr>
				<% 	
			
				
			}
					%>
		
			</table>
			<!-- Tabela que imprime os itens da estrutura virtual -->
			<table width="100%" border="0" cellpadding="0" cellspacing="0"> 
			
				<!--Exibe os itens das estruturas virtuais  -->
				<c:forEach var="estrutura" items="<%=mapItensMostradosEmTelaAgrupadoEstruturaVirtual.keySet()%>">
					<c:set scope="request" value="${estrutura}" var="estruturaRequest"/>
					
					<tr class="linha_subtitulo">
						<td colspan="5">${estrutura.nomeEtt}</td>
					</tr>
					<c:forEach var="itemEstrutura" items="<%=mapItensMostradosEmTelaAgrupadoEstruturaVirtual.get((EstruturaEtt)request.getAttribute("estruturaRequest"))%>">
						<c:set scope="request" value="${itemEstrutura}" var="itemEstruturaRequest"/>
						<tr>
							<%=montaLinhaItens((ItemEstruturaIett)request.getAttribute("itemEstruturaRequest"),acompanhamento,acompReferenciaItemDao,listItensGravados,selecionaveis,null,acompanhamentosAlterados,true, tipoAcompanhamentoTa, request, descendentesEstrutura, estruturaDao, estruturaNivelGeracao)%>
						</tr>
					</c:forEach>
					<tr>
						<td class="espacador" colspan="5"><img src="<%=_pathEcar%>/images/pixel.gif"></td>
					</tr>
				</c:forEach>
		
			</table>
		<%
			
		} catch (ECARException e) {
			org.apache.log4j.Logger.getLogger(this.getClass()).error(e);
			Mensagem.alert(_jspx_page_context, e.getMessageKey());
		} catch (Exception e){
		%>
			<%@ include file="/excecao.jsp"%>
		<%
		}
	}
}

Date dataFinal = Data.getDataAtual();
%>
<table class="form">
	<tr>
		<td>
			<%
			long df = dataFinal.getTime();
			long di = dataInicio.getTime();
			%>
			<b>Tempo para processar esta página:</b> <%=Data.parseDateHourSegundos( new Date(df - di) )%> mm.ss.SSS
		<td>
	</tr>
</table>
<input type="hidden" name="existeAriFaltandoParecer" value="<%=existeAriFaltandoParecer%>"> 
<input type="hidden" name="existeAriFaltandoParecerConfirma" value=""> 
</form>

</div>

<table width="100%" border="0" cellpadding="0" cellspacing="0">
	<tr><td>&nbsp;</td></tr>
</table>
</div>
</body>
<%@ include file="/include/ocultarAguarde.jsp"%>
<% /* Controla o estado do Menu (aberto ou fechado) */%>
<%@ include file="/include/estadoMenu.jsp"%>
<%@ include file="/include/mensagemAlert.jsp" %>
</html>

<script>

function getItemCorespondente(strItem){

	var arrayCheckBoxItem = document.getElementsByName("iett");

	for (i=0;i<arrayCheckBoxItem.length;i++){
		
		if (arrayCheckBoxItem[i].value == strItem){
			return arrayCheckBoxItem[i];
		}
		
	}
	
}


function verificaAlterados(){
	<%
		if(acompanhamentosAlterados.size() == 0){
				%>return true;<%
		} else {
				%>
				var total = 0;
				var separador = "";
				var msg = '';
				<%
				Iterator it = acompanhamentosAlterados.iterator();
				while(it.hasNext()){
					ItemEstruturaIett item = (ItemEstruturaIett) it.next();
					%>
					campo = getItemCorespondente('<%=item.getCodIett()%>');
					if(campo != null && campo.checked == false){
						msg += separador + "<%=item.getNomeIett().replaceAll("\n", " ").replaceAll("\r", " ").replaceAll("\t", " ").replaceAll("</SCRIPT>","&lt;/SCRIPT&gt;").replaceAll("</script>","&lt;/script&gt;").replaceAll("</script -->","&lt;/script --&gt;").replaceAll("</SCRIPT -->","&lt;/SCRIPT --&gt;")%>";
						separador = ", ";
						total++;
					}
					<%
				}
				%>
				if(msg != ''){
					var msgInicial = "";
					var msgFinal = "";
					
					if(total > 1){
						msgInicial = "Os valores informados para o realizado físico dos seguintes itens serão excluídos: \n"+msg;
						msgFinal = "\nA exclusão poderá influenciar na visualização dos valores em outros tipos de acompanhamentos associados aos itens, e/ou ciclos posteriores que utilizem valores acumulados. \nDeseja excluir acompanhamento?";
					} else {
						msgInicial = "Os valores informados para o realizado físico do seguinte item será excluído: \n"+msg;
						msgFinal = "\nA exclusão poderá influenciar na visualização dos valores em outros tipos de acompanhamentos associados ao item, e/ou ciclos posteriores que utilizem valores acumulados. \nDeseja excluir acompanhamento?";
					}
					
					if(confirm(msgInicial + msgFinal)){
						return true;
					} else {
						document.forms[0].reset();
						return false;
					}				
				} else {
					return true;
				}
				<%
		}
	%>
}
</script>

<%!

private String montaLinhaItens(ItemEstruturaIett itemEstrutura,AcompReferenciaAref acompanhamento,AcompReferenciaItemDao acompReferenciaItemDao,List listItensGravados,List selecionaveis,AtributoEstruturaListagemItens atbList,List acompanhamentosAlterados,boolean eItemEstruturaVirtual, TipoAcompanhamentoTa tipoAcompanhamentoTa, HttpServletRequest request, List descendentesEstrutura, EstruturaDao estruturaDao, EstruturaEtt estruturaNivelGeracao)
	throws ECARException {
	StringBuffer retorno = new StringBuffer();
	
	boolean marcar = false;
	
	//Verifica se o itemEstrutura está em na lista dos gravados.
	if(listItensGravados.contains(itemEstrutura)) {
		marcar = true;
	}

	//Verifica se o itemEstrutura está em na lista dos selecionáveis, se ele não estiver marca o item como disabled.
	String disabled = "";
	
	boolean possuiAtributoNivelPlanejamento = false;
	
	if (tipoAcompanhamentoTa.getSisAtributoSatbs() != null && tipoAcompanhamentoTa.getSisAtributoSatbs().size() > 0){
						
		Iterator itSisAtributosTa = tipoAcompanhamentoTa.getSisAtributoSatbs().iterator();
		
		while (itSisAtributosTa.hasNext()){
			
			SisAtributoSatb sisAtributo = (SisAtributoSatb) itSisAtributosTa.next();
			
			//Se for nivel de planejamento
			if (sisAtributo.getSisGrupoAtributoSga().equals(new ecar.dao.ConfiguracaoDao(request).getConfiguracao().getSisGrupoAtributoSgaByCodSgaGrAtrNvPlan())){
				
				possuiAtributoNivelPlanejamento = true;
				break;
			}
		}
	}
	
	if(selecionaveis.contains(itemEstrutura)){
		if (tipoAcompanhamentoTa.getSisAtributoSatbs() != null && 
			tipoAcompanhamentoTa.getSisAtributoSatbs().size() > 0 && possuiAtributoNivelPlanejamento){ //se for sisATributo do tipo nivel planejamento
				Iterator itTaSisAtributoSatb = tipoAcompanhamentoTa.getSisAtributoSatbs().iterator();
				boolean achou = false;
				while (itTaSisAtributoSatb.hasNext()){
					SisAtributoSatb satb = (SisAtributoSatb) itTaSisAtributoSatb.next();
					if (itemEstrutura.getItemEstruturaNivelIettns() != null && itemEstrutura.getItemEstruturaNivelIettns().contains(satb)){
						achou = true;
						break;
					}
				} 
				if (!achou){
					disabled = "disabled";
				}						
			}

	}            
	else {
	    disabled = "disabled";
	}   
	
	String strCodOrgao = ConstantesECAR.ZERO;
	
	
	if (itemEstrutura.getOrgaoOrgByCodOrgaoResponsavel1Iett() != null){
		strCodOrgao = itemEstrutura.getOrgaoOrgByCodOrgaoResponsavel1Iett().getCodOrg().toString();
	}
	
	//Verifica se o item possui Item pai
	if(itemEstrutura.getItemEstruturaIett() != null){
		retorno.append(" <td style=\"width: 25px\"> &nbsp; &nbsp;&nbsp; </td> <td> ");
		
    	for(int i = 2; i< itemEstrutura.getNivelIett().intValue(); i++){
	    	retorno.append(" &nbsp;&nbsp;&nbsp;&nbsp;&nbsp ");
		}
    	
   		retorno.append(" <input type=\"checkbox\" class=\"form_check_radio\" idOrgao="+strCodOrgao+" id=\"pai"+itemEstrutura.getItemEstruturaIett().getCodIett()+"\" onclick=\"validaCheck(this, form, this.value,"+itemEstrutura.getItemEstruturaIett().getCodIett()+","+eItemEstruturaVirtual+","+strCodOrgao+");\" ");
    } else {
   		retorno.append("<td colspan=\"3\"   nowrap>");
   		
   		for(int i = 2; i< itemEstrutura.getNivelIett().intValue(); i++){
	    	retorno.append(" &nbsp;&nbsp;&nbsp;&nbsp;&nbsp; ");
		}
    	
		retorno.append(" <input type=\"checkbox\" class=\"form_check_radio\" idOrgao="+strCodOrgao+" id=\""+itemEstrutura.getCodIett()+"\" onclick=\"validaCheck(this, form, this.value, null,"+eItemEstruturaVirtual+","+strCodOrgao+");\" ");
   	}
	
	//
	if (acompanhamento.getTipoAcompanhamentoTa().getSituacaoSits() != null) {
		
		Iterator itSituacaoSits = acompanhamento.getTipoAcompanhamentoTa().getSituacaoSits().iterator();
		while (itSituacaoSits.hasNext()) {
			SituacaoSit situacaoSit = (SituacaoSit) itSituacaoSits.next();
			if (itemEstrutura.getSituacaoSit() != null && situacaoSit.getCodSit().equals(itemEstrutura.getSituacaoSit().getCodSit())) {
				disabled = "disabled";
			}
		}
	}
	//Caso o tipo de acompanhamento esteja com o campo "Gerar acompanhamento a partir da estrutura" setado, o 
	//sistema não habilita os itens que não sejam filhos da estrutura setada em estruturaNivelGeracaoTa do tipo de acompanhamento. 
	if (estruturaNivelGeracao != null) {
		
		if (!itemEstrutura.getEstruturaEtt().equals(estruturaNivelGeracao) && !descendentesEstrutura.contains(itemEstrutura.getEstruturaEtt())) {
			disabled = "disabled";
		}
	
	}
	
	if (eItemEstruturaVirtual) {
		retorno.append(" name=\"iett-virtual\" "+disabled);
	} else {
		retorno.append(" name=\"iett\" "+disabled);
	}
	
	
	
	if(marcar){
		retorno.append(" checked ");
	}
	
	String nome = "";
	
	if (atbList != null) {
		nome = "".equals(atbList.getDescricao().trim()) ? "[Não Informado]" : atbList.getDescricao();
	} else {
		nome = "".equals(itemEstrutura.getNomeIett().trim()) ? "[Não Informado]" : itemEstrutura.getNomeIett();
	}
	

	retorno.append(" value=\""+itemEstrutura.getCodIett()+"\"");
	
	//Código específico para pegar o SisAtrb correspondente ao Produto Prioritário
	SisAtributoDao sisAtributoDao = new SisAtributoDao();
	SisAtributoSatb atrib = (SisAtributoSatb) sisAtributoDao.buscar(SisAtributoSatb.class, 64L);
	if(Util.contemSisAtributo(itemEstrutura, atrib)) {
		retorno.append(" prioritario=\"true\" ");	
	}
	
	//Fim
	
	if(acompReferenciaItemDao.verificaAcompanhamentoItemMes(itemEstrutura, acompanhamento) && disabled.equals("")){
		retorno.append(" recomendado=\"true\"");
		retorno.append(" >");

		retorno.append("<span class=\"fontVermelho\">"+nome+"</span>");
	} else {
		retorno.append(" >");
		retorno.append(nome);
	}
	
	
	
	//se o orgao do item for diferente do orgao da referencia do item (acompReferenciaItemAri) -> somente alteração
	if(	acompanhamento.getCodAref() != null && 
		"S".equals(acompanhamento.getTipoAcompanhamentoTa().getIndSepararOrgaoTa()) && 
		acompanhamento.getOrgaoOrg() != itemEstrutura.getOrgaoOrgByCodOrgaoResponsavel1Iett() &&
		disabled.equals("")) {
		String hintOrgao = "Item com órgão removido após a geração de ciclo."; 
		if (itemEstrutura.getOrgaoOrgByCodOrgaoResponsavel1Iett() != null){
			hintOrgao = "Item com órgão modificado após a geração de ciclo. Órgão atual: " + itemEstrutura.getOrgaoOrgByCodOrgaoResponsavel1Iett().getDescricaoOrg();
		}		
		retorno.append("<img src=\" "+ request.getContextPath() + "/images/atencao.gif\" border=\"0\" title=\"" + hintOrgao + " \">");
	}
	
	
	if (acompanhamentosAlterados != null) {
		if(acompReferenciaItemDao.isAcompanhamentoAlterado(acompanhamento, itemEstrutura)){
			acompanhamentosAlterados.add(itemEstrutura);
		}
	}
	
	retorno.append("</td>");
	
	
	return retorno.toString();
	
}

%>