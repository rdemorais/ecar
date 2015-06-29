<jsp:directive.page import="ecar.pojo.SisAtributoSatb"/>
<jsp:directive.page import="java.util.Set"/>


<%@ include file="../../login/validaAcesso.jsp"%>
<%@ include file="../../frm_global.jsp"%>

<%@ page import="ecar.util.Dominios" %>
<%@ page import="ecar.pojo.AcompReferenciaItemAri" %> 
<%@ page import="ecar.dao.AcompReferenciaItemDao" %>
<%@ page import="ecar.dao.AcompReferenciaDao" %>
<%@ page import="ecar.pojo.AcompRealFisicoArf" %>  
<%@ page import="ecar.dao.AcompRealFisicoDao" %>
<%@ page import="ecar.pojo.ItemEstruturaIett" %>
<%@ page import="ecar.dao.ItemEstruturaDao" %>
<%@ page import="ecar.pojo.ItemEstrtIndResulIettr" %>
<%@ page import="ecar.permissao.ValidaPermissao" %> 
<%@ page import="ecar.dao.ItemEstrtIndResulDao" %>
<%@ page import="ecar.pojo.SituacaoSit" %>
<%@ page import="ecar.dao.SituacaoDao" %>
<%@ page import="ecar.pojo.UsuarioUsu" %>
<%@ page import="ecar.pojo.ConfiguracaoCfg" %>

<%@ page import="comum.util.Util" %>

<%@ page import="java.util.List" %> 
<%@ page import="java.util.Iterator" %> 

<%@ taglib uri="/WEB-INF/ecar-util.tld" prefix="util" %>
<%@ taglib uri="/WEB-INF/ecar-combo.tld" prefix="combo" %>

<%
try{ 
	final String REALIZADO_NO_MES = "no mês";
	final String REALIZADO_ATE_O_MES = "até o mês";

	ValidaPermissao validaPermissao = new ValidaPermissao();

	ItemEstruturaDao itemDao = new ItemEstruturaDao(request);
	AcompReferenciaDao acompReferenciaDao = new AcompReferenciaDao(request);
	AcompReferenciaItemDao acompReferenciaItemDao = new AcompReferenciaItemDao(request); 
	AcompRealFisicoDao acompRealFisicoDao = new AcompRealFisicoDao(request);
	ItemEstrtIndResulDao indResulDao = new ItemEstrtIndResulDao(request);

	UsuarioUsu usuario = ((ecar.login.SegurancaECAR)session.getAttribute("seguranca")).getUsuario();
	
	/* item pai do item que está sendo cadastrado */  

	AcompReferenciaItemAri acompReferenciaItem;
	String codAriAtual;
	if(!"".equals(Pagina.getParamStr(request, "codAriSubNivel"))) {
		codAriAtual = Pagina.getParamStr(request, "codAriSubNivel");
	}
	else {
		codAriAtual = Pagina.getParamStr(request, "codAri");
	}

	acompReferenciaItem = (AcompReferenciaItemAri) acompReferenciaItemDao.buscar(AcompReferenciaItemAri.class, Long.valueOf(codAriAtual));

	if ( !validaPermissao.permissaoConsultaIETT( acompReferenciaItem.getItemEstruturaIett().getCodIett() , seguranca ) )
	{
		response.sendRedirect( request.getContextPath() +  "/acessoIndevido.jsp" );
	}	
	
	
	
	String radConcluido = Pagina.getParamStr(request, "radConcluido");
	String radAcumulados = Pagina.getParamStr(request, "radAcumulados");
	boolean emEdicao = true;
	String msg = "";
	
	//ConfiguracaoDao configuracaoDao = new ConfiguracaoDao(request);
	//ConfiguracaoCfg configuracao = new ConfiguracaoCfg();
	//List confg = configuracaoDao.listar(ConfiguracaoCfg.class, null);					
	
	//if(confg != null && confg.size() > 0)
			//configuracao = (ConfiguracaoCfg) confg.iterator().next();
	
	if("".equals(radConcluido) || radConcluido == null){
		//radConcluido = configuracao.getExibDefaultEstCfg();
		radConcluido = Dominios.NAO_CONCLUIDOS;
	}
	
	if("".equals(radAcumulados) || radAcumulados == null)
		radAcumulados = "P";
	

	String LABEL_TOTAL = ("T".equals(radAcumulados)) ? "Total" : "Até o Ciclo";
	
	
	/* VALIDAÇÃO DE ACESSO AO REALIZADO FISICO, CASO NÃO TENHA ACESSO, ABRE PARA CONSULTA */
	try{
		acompReferenciaItemDao.verificaEditarAcompRealFisico(usuario, acompReferenciaItem);
	}catch(ECARException e){
		msg = _msg.getMensagem(e.getMessageKey());
		_disabled = "disabled";
		_readOnly = "readonly";
		emEdicao = false;
	}

	/* VALIDAÇAO DE PERMISSAO EM ITEM_ESTRUT_USUARIO */
	if(!validaPermissao.permissaoInformarRealizadoFisico(acompReferenciaItem.getItemEstruturaIett(), seguranca.getUsuario(), seguranca.getGruposAcesso())){
		_disabled = "disabled";
		_readOnly = "readonly";
		emEdicao = false;			
	}

	boolean estavaDesabilitado = (!"".equals(_disabled)) ? true : false;
	
String primeiroItemClicado = Pagina.getParamStr(request, "primeiroItemClicado");
String primeiroItemAriClicado = Pagina.getParamStr(request, "primeiroItemAriClicado");

if("".equals(primeiroItemClicado)) {
	primeiroItemClicado = acompReferenciaItem.getItemEstruturaIett().getCodIett().toString();
}

if("".equals(primeiroItemAriClicado)) {
	primeiroItemAriClicado = Pagina.getParamStr(request, "codAri");
}

%>

<html lang="pt-br"> 
<head>
<%@ include file="../../include/meta.jsp"%>
<%@ include file="/titulo.jsp"%>
<link rel="stylesheet" href="<%=_pathEcar%>/css/style_<%=configuracaoCfg.getEstilo().getNome()%>.css" type="text/css">
<script language="javascript" src="../../js/menu_retratil.js"></script>
<script language="javascript" src="../../js/focoInicial.js"></script>
<!--<script language="javascript" src="../../js/mascaraMoeda.js"></script>-->
<script language="javascript" src="../../js/numero.js"></script>
<script language="javascript" src="../../js/validacoes.js"></script>
<script language="javascript" src="<%=_pathEcar%>/js/destaqueLinha.js"></script>
<script language="javascript" src="../../js/prototype.js"></script>

<script language="javascript">
function aoClicarExibir(form){
	form.codAri.value = "<%=primeiroItemAriClicado%>";
	form.action = "realizadoFisico.jsp";
	form.submit();
}

function aoClicarTrocar(radAcumulados, numFilho){
	if ('P' == radAcumulados)
	{
		document.getElementById('acumulaPeriodo'+numFilho).style.display='';
		document.getElementById('acumulaTotal'+numFilho).style.display='none';
	}
	if ('T' == radAcumulados)
	{
		document.getElementById('acumulaPeriodo'+numFilho).style.display='none';
		document.getElementById('acumulaTotal'+numFilho).style.display='';
	}
}


function mostrarEsconder(numero, codIett) {
    var i=0;
    var contador = document.getElementById('contFilho').value;

    var divdavez = null;
    for (i = 0; i <= contador; i++) {
		if (i != numero) {
			divdavez = document.getElementById('div'+i);
			if( divdavez != null ) {
	    		divdavez.style.display = 'none';
	    	}
		}
    }
    
	if (document.getElementById('div'+numero).style.display=='none') {
	     document.getElementById('div'+numero).style.display='';
	     document.getElementById('filhoSelecionado').value = codIett;
	     document.getElementById('numFilho').value = numero;
	} else {
	     document.getElementById('div'+numero).style.display='none';
	}
}

/* Quando está em edição liberar alteração, senão exibe msg */
function aoClicarGravarPai(form){
	<%if(emEdicao){%>
		form.hidNivel.value = "pai";		
		if (!validarPai(form)){
			return(false);
		}	
		form.codAri.value = "<%=primeiroItemAriClicado%>";
		form.action = "ctrlRealizadoFisico.jsp";
		form.submit();
	<%}else{%>
		alert("<%=msg%>");
	<%}%>
}


function aoClicarGravarFilho(form, block){
	<%if(emEdicao){%>
		form.hidNivel.value = "filho";				
		if (!validarFilho(form, block)){
			return(false);
		}

		form.action = "ctrlRealizadoFisico.jsp";
		form.submit();
	<%}else{%>
		alert("<%=msg%>");
	<%}%>
}


function aoClicarConsultar(form, codigo){
	form.codAriSubNivel.value = codigo;
	form.action = "realizadoFisico.jsp?subNiveis=true";
	form.submit();
}

/* Quando está em edição liberar inclusão, senão exibe msg */
function aoClicarNovoIndicador(form, codigo){
	<%if(emEdicao){%>
		if(confirm("<%=_msg.getMensagem("acompanhamento.realizadoFisico.novoIndicador.confirma")%>")){
			form.codNovoIndicador.value = codigo;
			form.action = "ctrlNovoIndicador.jsp";
			form.submit();
		}
	<%}else{%>
		alert("<%=msg%>");
	<%}%>
}

function aoClicarCancelar(form){
	form.codAri.value = "<%=primeiroItemAriClicado%>";
	form.action = "realizadoFisico.jsp";
	form.submit();
}

function aoClicarIndicadorPorLocal(form, codARF, nomeIndicador, podeGravar) {
	var janela = abreJanela('popUpRealizadoFisicoPorLocal.jsp?codARF=' + codARF + '&podeGravar=' + podeGravar + '&nomeIndicador=' + escape(nomeIndicador), 600, 500, 'popUpRealizadoFisicoPorLocal');
	janela.focus();
}

function verificarSituacao(form, codArf, codSit){
	if(codSit != ""){
		var url = "realizadoFisicoVerificaSit.jsp?verificaCodArf=" + codArf + "&verificaCodSit=" + codSit;
		openAjax(url, "verificaSituacaoConclusao", null, null, true, "../../images");
	}
}

</script>

</head>

<%@ include file="../../cabecalho.jsp" %>
<%@ include file="../../divmenu.jsp"%> 

<body>
<div id="verificaSituacaoConclusao" style="position: absolute;"></div>

<div id="conteudo"> 

<!-- TÍTULO -->
<%@ include file="/titulo_tela.jsp"%>

<%@ include file="botoesAvancaRetrocede.jsp" %>
 <br>
 	<%
 	if("".equals(Pagina.getParamStr(request, "codAriSubNivel"))) {
 	%>
		<util:barraLinksRegAcomp 
			acompReferenciaItem="<%=acompReferenciaItem%>"  
			selectedFuncao="REL_FISICO_IND_RESULTADO"
			usuario="<%=usuario%>"
			primeiroAcomp="<%=Pagina.getParamStr(request, "primeiroAcomp")%>"
			request="<%=request%>"
			gruposUsuario="<%=seguranca.getGruposAcesso() %>"
		/> 
	<%
	} else {
	%>
		<util:barraLinksRegAcomp 
			acompReferenciaItem="<%=(AcompReferenciaItemAri) acompReferenciaItemDao.buscar(AcompReferenciaItemAri.class, Long.valueOf(Pagina.getParamStr(request, "codAri")))%>"  
			selectedFuncao="REL_FISICO_IND_RESULTADO"
			usuario="<%=usuario%>"
			acompReferenciaItemSubNivel="<%=acompReferenciaItem%>"
			primeiroAcomp="<%=Pagina.getParamStr(request, "primeiroAcomp")%>"
		/> 
	<%
	}
	%>
	
<br>
<table width="100%" border="0" cellpadding="0" cellspacing="0">
	<tr><td class="espacador"><img src="../../images/pixel.gif"></td></tr>
	<tr class="linha_titulo" align="left">
		<td>
			Refer&ecirc;ncia: <%=acompReferenciaItem.getAcompReferenciaAref().getNomeAref()%>&nbsp;&nbsp;&nbsp;(<%=acompReferenciaItem.getAcompReferenciaAref().getTipoAcompanhamentoTa().getDescricaoTa()%>)
		</td> 
	</tr>
	<tr class="linha_titulo" align="left">
		<td>
			M&ecirc;s/Ano: <%=acompReferenciaItem.getAcompReferenciaAref().getMesAref()%>/<%=acompReferenciaItem.getAcompReferenciaAref().getAnoAref()%>
		</td> 
	</tr>
	<tr><td class="espacador"><img src="../../images/pixel.gif"></td></tr>
</table>
<%
if("".equals(Pagina.getParamStr(request, "subNiveis"))) {
%>
	<util:arvoreEstruturaElabAcomp
		itemEstrutura="<%=(ItemEstruturaIett)itemDao.buscar(ItemEstruturaIett.class, Long.valueOf(primeiroItemClicado))%>" 
		codigoAcomp="<%=Pagina.getParamStr(request, "codAcomp")%>"
		contextPath="<%=_pathEcar%>"
		listaParaArvore="<%=(java.util.List)session.getAttribute("listaParaArvore")%>"  
		nivelPrimeiroIettClicado="<%=(String)session.getAttribute("nivelPrimeiroIettClicado")%>"  
		abaAtual="<%=ecar.taglib.util.ArvoreEstruturaElabAcompTag.ABA_REALIZADO_FISICO%>"
		primeiroAcomp="<%=Pagina.getParamStr(request, "primeiroAcomp")%>"
	/> 
<%
} else {
%>
	<util:arvoreEstruturaElabAcomp
		itemEstrutura="<%=acompReferenciaItem.getItemEstruturaIett()%>" 
		codigoAcomp="<%=Pagina.getParamStr(request, "codAcomp")%>"
		contextPath="<%=_pathEcar%>"
		primeiroItemAriClicado="<%=primeiroItemAriClicado%>"
		codItemClicadoSubNiveis="<%=acompReferenciaItem.getItemEstruturaIett().getCodIett().toString()%>"
		listaParaArvore="<%=(java.util.List)session.getAttribute("listaParaArvore")%>"  
		nivelPrimeiroIettClicado="<%=(String)session.getAttribute("nivelPrimeiroIettClicado")%>"  
		abaAtual="<%=ecar.taglib.util.ArvoreEstruturaElabAcompTag.ABA_REALIZADO_FISICO%>"
		primeiroAcomp="<%=Pagina.getParamStr(request, "primeiroAcomp")%>"
	/> 
<%
}
%>
<div id="subconteudo">
<form  name="form" method="post" >

<input type="hidden" name="subNiveis" value="<%=Pagina.getParamStr(request, "subNiveis")%>">
<input type="hidden" id="contFilho" name="contFilho" value="">
<input type="hidden" id="numFilho" name="numFilho" value="">
<input type="hidden" id="hidNivel" name="hidNivel" value="">
<input type="hidden" id="filhoSelecionado" name="filhoSelecionado" value="">
<input type="hidden" name="primeiroItemClicado" value="<%=primeiroItemClicado%>">
<input type="hidden" name="primeiroItemAriClicado" value="<%=primeiroItemAriClicado%>">
<input type="hidden" name="codAcomp" value="<%=Pagina.getParamStr(request, "codAcomp")%>">
<input type="hidden" name="codAri" value="<%=codAriAtual%>">
<input type="hidden" name="codAriSubNivel" value="<%=Pagina.getParamStr(request, "codAriSubNivel")%>">
<input type="hidden" name="codNovoIndicador" value="">
<input type="hidden" name="primeiroAcomp" value="<%=Pagina.getParamStr(request, "primeiroAcomp")%>">

<table width="100%" border="0" cellpadding="0" cellspacing="0">
	<tr><td class="espacador"><img src="../../images/pixel.gif"></td></tr>
	<tr>
		<td><br>Exibir: 
			<input type="radio" class="form_check_radio" name="radConcluido" value="<%=Dominios.TODOS%>" onclick="aoClicarExibir(form);" <%=Pagina.isChecked(radConcluido, Dominios.TODOS)%>> Todos 
			<input type="radio" class="form_check_radio" name="radConcluido" value="<%=Dominios.NAO_CONCLUIDOS%>" onclick="aoClicarExibir(form);" <%=Pagina.isChecked(radConcluido, Dominios.NAO_CONCLUIDOS)%>> Não Concluídos 
			<input type="radio" class="form_check_radio" name="radConcluido" value="<%=Dominios.CONCLUIDOS%>" onclick="aoClicarExibir(form);" <%=Pagina.isChecked(radConcluido, Dominios.CONCLUIDOS)%>> Concluídos
		</td>
	</tr>
</table>

<br>
<%
	int cont = 0;
	
	/* selecionar os indicadores dependendo da situacao selecionada*/
	List lAcompRF = acompRealFisicoDao.getIndResulByAcompRefItemBySituacao(acompReferenciaItem, radConcluido, false);
	Iterator it = lAcompRF.iterator();
	String unidadeMedida = "";
	
	/* se possuir lista mostrar os botões */
	if (it.hasNext()){
		String nome = "";
%>
		<table class="barrabotoes" cellpadding="0" cellspacing="0">
			<tr>
				<td class="label">
					&nbsp;
				</td>
				<td>
					<input name="btnGravarPai" type="button" value="Gravar" class="botao" onclick="aoClicarGravarPai(form);">
					<input name="btnCancelar" type="button" value="Cancelar" class="botao" onclick="aoClicarCancelar(form);">
				</td>
			</tr>
		</table>		
		<table width="100%" border="0" cellpadding="0" cellspacing="0">	
			<tr><td class="espacador" colspan="9"><img src="../../images/pixel.gif"></td></tr>
			<tr class="linha_titulo">
				<td colspan="5">&nbsp;</td>
				<td colspan="3" align="right">
					Acumulados: 
					<input type="radio" class="form_check_radio" name="radAcumulados" value="P" onclick="aoClicarExibir(form);" <%=Pagina.isChecked(radAcumulados, "P")%>> Até o ciclo 
					<input type="radio" class="form_check_radio" name="radAcumulados" value="T" onclick="aoClicarExibir(form);" <%=Pagina.isChecked(radAcumulados, "T")%>> Total
				</td>
			</tr>
			<tr><td class="espacador" colspan="8"><img src="../../images/pixel.gif"></td></tr>
			<%
			if(configura.getSisGrupoAtributoSgaByCodSgaGrAtrMetasFisicas() == null){
			%>
			<tr class="linha_subtitulo">
				<td width="10%" align="center">Indicador de Resultado</td>
				<td width="5%" align="center">Previsto<br><%=LABEL_TOTAL%></td>
				<td width="5%" align="center">Realizado<br><%=LABEL_TOTAL%></td>
				<td width="5%" align="center">Previsto<br>Exercício</td>
				<td width="5%" align="center">Realizado<br>Exercício</td>
				<td width="18%" align="center" colspan="2" align="center">Realizado</td>
				<td width="12%" align="center">Situação</td>
			</tr>
			<%
			}
			%>
			<tr><td class="espacador" colspan="8"><img src="../../images/pixel.gif"></td></tr>
<%
				String cor = new String(); 
				String grupoIndicador = "Indicador de Resultado";
				String nomeIndicador = "";
				while (it.hasNext()){
					if (cont % 2 == 0){
						cor = "cor_nao";
					} else {
						cor = "cor_sim"; 
					}
					cont++;
					AcompRealFisicoArf acompRealFisico = (AcompRealFisicoArf) it.next();
					unidadeMedida = Util.substring(acompRealFisico.getItemEstrtIndResulIettr().getUnidMedidaIettr(), 0, 3);
					
					if(!estavaDesabilitado){
						//Verificar se é para habilitar ou não edição conforme regra do mantis 7795
						_disabled = (acompRealFisicoDao.habilitarEdicaoArf(acompRealFisico, acompReferenciaItem.getAcompReferenciaAref(), radConcluido)) ? "" : "disabled";
					}

					if(configura.getSisGrupoAtributoSgaByCodSgaGrAtrMetasFisicas() != null){
						if(acompRealFisico.getItemEstrtIndResulIettr().getSisAtributoSatb() != null && !grupoIndicador.equals(acompRealFisico.getItemEstrtIndResulIettr().getSisAtributoSatb().getDescricaoSatb())){
							grupoIndicador = acompRealFisico.getItemEstrtIndResulIettr().getSisAtributoSatb().getDescricaoSatb();
							%>
							<tr class="linha_subtitulo">
								<td width="10%" align="left"><%=grupoIndicador%></td>
								<td width="5%" align="center">Previsto<br><%=LABEL_TOTAL%></td>
								<td width="5%" align="center">Realizado<br><%=LABEL_TOTAL%></td>
								<td width="5%" align="center">Previsto<br>Exercício</td>
								<td width="5%" align="center">Realizado<br>Exercício</td>
								<td width="18%" align="center" colspan="2" align="center">Realizado</td>
								<td width="12%" align="center">Situação</td>
							</tr>
							<%
							
						}
						else if(acompRealFisico.getItemEstrtIndResulIettr().getSisAtributoSatb() == null && !"".equals(grupoIndicador)){
							%>
							<tr class="linha_subtitulo">
								<td width="10%" align="left"><%=grupoIndicador%></td>
								<td width="5%" align="center">Previsto<br><%=LABEL_TOTAL%></td>
								<td width="5%" align="center">Realizado<br><%=LABEL_TOTAL%></td>
								<td width="5%" align="center">Previsto<br>Exercício</td>
								<td width="5%" align="center">Realizado<br>Exercício</td>
								<td width="18%" align="center" colspan="2" align="center">Realizado</td>
								<td width="12%" align="center">Situação</td>
							</tr>
							<%
							grupoIndicador = "";
						}
					}					
					
					double realizado = 0;
					String linkPorLocal = "<img src=\"../../images/empty.gif\">";
					String desabilitarQtdeARF = "";
					
					if(acompRealFisico.getQtdRealizadaArf() != null) {
						realizado = acompRealFisico.getQtdRealizadaArf().doubleValue();
					}
					
					String previstoTotal = "0";
					String realizadoTotal = "0";
					String previstoExercicio = "0";
					String realizadoExercicio = "0";
					
					String labelRealizado = REALIZADO_NO_MES;

					if("S".equals(acompRealFisico.getItemEstrtIndResulIettr().getIndRealPorLocal())) {
						String podeGravar = (!"".equals(_disabled)) ? "N" : "S";
						linkPorLocal = "<a href=\"#\" onclick=\"javascript:aoClicarIndicadorPorLocal(form, '" + acompRealFisico.getCodArf() + "','qtdRealizadaArf" + cont +"','" + podeGravar + "')\">"
										+ "<img src=\"../../images/icon_exibir.png\" border=\"0\" title='Quantidade/Valor por Local'></a>";
						if("".equals(_disabled)) {
							desabilitarQtdeARF = "disabled";
						}
					}
					
					previstoTotal = indResulDao.getSomaQuantidadePrevista(acompRealFisico.getItemEstrtIndResulIettr());
					/* Tratamento diferencia para exibição de indicadores Acumulavel e não-Acumulável */
					if("S".equals(acompRealFisico.getItemEstrtIndResulIettr().getIndAcumulavelIettr())){
						labelRealizado = REALIZADO_ATE_O_MES;
						realizadoTotal = Util.formataNumeroDecimal(acompRealFisicoDao.getQuantidadeRealizada(acompReferenciaItem, acompRealFisico.getItemEstrtIndResulIettr(), radAcumulados));
						previstoExercicio = Util.formataNumeroDecimal(indResulDao.getQtdPrevistoExercicio(acompRealFisico.getItemEstrtIndResulIettr(), acompReferenciaItem.getAcompReferenciaAref().getExercicioExe()));
						realizadoExercicio = Util.formataNumeroDecimal(acompRealFisicoDao.getQtdRealizadaExercicio(acompReferenciaItem, acompRealFisico.getItemEstrtIndResulIettr(), radAcumulados));
					}else{
						realizadoTotal = Util.formataNumeroDecimal(acompRealFisicoDao.getQuantidadeRealizadaNaoAcumulados(acompReferenciaItem, acompRealFisico.getItemEstrtIndResulIettr(), radAcumulados, "T"));
						previstoExercicio = Util.formataNumeroDecimal(indResulDao.getQtdPrevistoExercicio(acompRealFisico.getItemEstrtIndResulIettr(), acompReferenciaItem.getAcompReferenciaAref().getExercicioExe()));
						realizadoExercicio = Util.formataNumeroDecimal(acompRealFisicoDao.getQuantidadeRealizadaNaoAcumulados(acompReferenciaItem, acompRealFisico.getItemEstrtIndResulIettr(), radAcumulados, "E"));
					}
					
					labelRealizado = labelRealizado + " " +  acompRealFisico.getMesArf().toString() + "/" + acompRealFisico.getAnoArf().toString();
					
					if(!nomeIndicador.equals(acompRealFisico.getItemEstrtIndResulIettr().getNomeIettir())){
						nomeIndicador = acompRealFisico.getItemEstrtIndResulIettr().getNomeIettir();
%>
					<tr class="<%=cor%>" onmouseover="javascript:destacaLinha(this,'over','')" onmouseout="javascript: destacaLinha(this,'out','<%=cor%>')" onClick="javascript: destacaLinha(this,'click','<%=cor%>')" class="linha_subtitulo2">
						<td width="10%">
							<%=acompRealFisico.getItemEstrtIndResulIettr().getNomeIettir()%>
							<!-- CODARF adicionado devido ao Mantis 5518, antes era verificado pelo codIettir+codAri+codIett -->
							<input type="hidden" name="codArf<%=cont%>" value="<%=acompRealFisico.getCodArf()%>">
						</td>
						
						<td width="5%"><%=previstoTotal%>&nbsp;<%=unidadeMedida%></td>
						<td width="5%"><%=realizadoTotal%>&nbsp;<%=unidadeMedida%></td>
						<td width="5%"><%=previstoExercicio%>&nbsp;<%=unidadeMedida%></td>
						<td width="5%"><%=realizadoExercicio%>&nbsp;<%=unidadeMedida%></td>

<%
					}
					else {
						%>
						<tr class="<%=cor%>" onmouseover="javascript:destacaLinha(this,'over','')" onmouseout="javascript: destacaLinha(this,'out','<%=cor%>')" onClick="javascript: destacaLinha(this,'click','<%=cor%>')" class="linha_subtitulo2">
							<td width="10%">&nbsp;<input type="hidden" name="codArf<%=cont%>" value="<%=acompRealFisico.getCodArf()%>"></td>
							<td width="5%">&nbsp;</td>
							<td width="5%">&nbsp;</td>
							<td width="5%">&nbsp;</td>
							<td width="5%">&nbsp;</td>

							<%
					}
							String tipoQtde = "V";
							if("Q".equals(acompRealFisico.getItemEstrtIndResulIettr().getIndTipoQtde())){
								tipoQtde = "Q";
							}
							String validacaoIndQtde = " onblur=\"javascript:validarQtdeValor(this,'" + tipoQtde + "')\"";
							%>

							<td width="11%" align="right" nowrap="nowrap">
								<%=labelRealizado%>
								<input type="text" name="qtdRealizadaArf<%=cont%>" id="qtdRealizadaArf<%=cont%>" <%if(acompRealFisico.getQtdRealizadaArf() != null) {%> value="<%=Util.formataNumeroDecimal(realizado)%>" <%} else { %> value="" <%}%> size="10" maxlength="20" <%=_disabled%> <%=desabilitarQtdeARF%> <%=validacaoIndQtde%>>
								<%=linkPorLocal%>
							</td>
							<td width="7%" align="left">
								<%=acompRealFisico.getItemEstrtIndResulIettr().getUnidMedidaIettr()%>
							</td>
							<td width="12%">
	<%
							nome = "situacaoSit" + cont;
							String scripts = _disabled + " onchange=\"javascript:verificarSituacao(form, " + acompRealFisico.getCodArf().toString() + ", this.value)\"";
							if ( acompRealFisico.getSituacaoSit() != null ) {
	%>
								<combo:ComboTag 
										nome="<%=nome%>"
										objeto="ecar.pojo.SituacaoSit" 
										label="descricaoSit" 
										value="codSit" 
										order="descricaoSit"
										filters="indMetaFisicaSit=S"
										selected="<%=acompRealFisico.getSituacaoSit().getCodSit().toString()%>"
										scripts="<%=scripts%>"
								/>
	<%
							} else {
	%>
								<combo:ComboTag 
										nome="<%=nome%>"
										objeto="ecar.pojo.SituacaoSit" 
										label="descricaoSit" 
										value="codSit" 
										order="descricaoSit"
										filters="indMetaFisicaSit=S"
										scripts="<%=scripts%>"
								/>
	<%
							}
	%>
							</td>
						</tr>
	<%
				}
//			}//fim do else sobre situação
%>
			<tr><td class="espacador" colspan="8"><img src="../../images/pixel.gif"></td></tr>
		</table>
		<input type="hidden" name="hidCont" value="<%=cont%>">
		
		<table class="barrabotoes" cellpadding="0" cellspacing="0">
			<tr>
				<td class="label">
					&nbsp;
				</td>
				<td>
					<input name="btnGravarPai" type="button" value="Gravar" class="botao" onclick="aoClicarGravarPai(form);">
					<input name="btnCancelar" type="button" value="Cancelar" class="botao" onclick="aoClicarCancelar(form);">
				</td>
			</tr>
		</table>		
<%
	} else {
		// se nao possui, verificar mensagem
%>
		<table width="100%" border="0" cellpadding="0" cellspacing="0">	
			<tr><td class="espacador" colspan="9"><img src="../../images/pixel.gif"></td></tr>
			<tr class="linha_subtitulo2" colspan="9">
				<td>Não há Indicadores de Resultado para este ciclo de acompanhamento</td>
			</tr>
			<tr><td class="espacador" colspan="9"><img src="../../images/pixel.gif"></td></tr>
		</table>
<%
	}
	
		//*** Aqui começa a codificação para novos indicadores que ainda não foram incluídos 
		
/************/ 
//	Captura apenas as Metas Físicas configuradas para os Tipo de Acompanhamento em questão
/***************/
		List novosIndicadores = null;// acompReferenciaItemDao.getNovosIndicadores(acompReferenciaItem);
		Iterator itIndicadores = null;// novosIndicadores.iterator();
		List listAtributosConfig = new ArrayList();
	   	
    	if(configuracao.getSisGrupoAtributoSgaByCodSgaGrAtrMetasFisicas() != null && acompReferenciaItem != null){
    		//Obter os sisAtributos de grupos de metas físicas configurados 
			Set sisAtributosSatbs = acompReferenciaItem.getAcompReferenciaAref().getTipoAcompanhamentoTa().getSisAtributoSatbs();
			 					//  acompRefItem.       getAcompReferenciaAref().getTipoAcompanhamentoTa().getSisAtributoSatbs();
			
			if(sisAtributosSatbs != null){
				Iterator itSatbs = sisAtributosSatbs.iterator();
				while(itSatbs.hasNext()) {
					SisAtributoSatb sisAtributoSatb = (SisAtributoSatb) itSatbs.next();
					if(sisAtributoSatb.getSisGrupoAtributoSga().equals(configuracao.getSisGrupoAtributoSgaByCodSgaGrAtrMetasFisicas())){
						listAtributosConfig.add(sisAtributoSatb);
					}
				}
			}
		
			
			novosIndicadores = acompReferenciaItemDao.getNovosIndicadores(acompReferenciaItem);
		
			//remover os indicadores que não pertençam à coleção de atributos identificadas acima
			if(!listAtributosConfig.isEmpty()){
			
				itIndicadores = novosIndicadores.iterator();
	   			while(itIndicadores.hasNext()) {
	   				ItemEstrtIndResulIettr itemEstrtIndResulIettr = (ItemEstrtIndResulIettr) itIndicadores.next(); 
	    				
	   				if(itemEstrtIndResulIettr.getSisAtributoSatb() != null && !listAtributosConfig.contains(itemEstrtIndResulIettr.getSisAtributoSatb())) {
	   					itIndicadores.remove();
	   				} 
	   			}
			}
			
		}
	
	
	/* MOSTRAR LISTA DE NOVOS INDICADORES NÃO CADASTRADOS PARA O ARI */
	if (novosIndicadores!=null){
		itIndicadores = novosIndicadores.iterator();
	} else {
		novosIndicadores = acompReferenciaItemDao.getNovosIndicadores(acompReferenciaItem);
		itIndicadores = novosIndicadores.iterator();
	}
	
	if(itIndicadores.hasNext()){
%>
		<BR>
		<table width="100%" border="0" cellpadding="0" cellspacing="0">	
			<tr><td class="espacador"><img src="../../images/pixel.gif"></td></tr>
			<tr class="linha_subtitulo"><td>Novo(s) Indicador(es) de Resultado (clique para adicionar)</td></tr>
			<tr><td class="espacador"><img src="../../images/pixel.gif"></td></tr>
<%
			while(itIndicadores.hasNext()){
				ItemEstrtIndResulIettr indResul = (ItemEstrtIndResulIettr) itIndicadores.next();
%>
				<tr class="linha_subtitulo2"><td>
					<a href="javaScript:aoClicarNovoIndicador(form, <%=indResul.getCodIettir()%>);">
					<%=indResul.getNomeIettir()%></a>
				</td></tr>
<%
			}
%>
			<tr><td class="espacador"><img src="../../images/pixel.gif"></td></tr>
		</table>
<!-- 		</form> -->
<%
	}
	
	/* MONTAR ÁRVORE COM SUBNÍVEIS DO ITEM, COM LINK PARA AQUELE QUE POSSUIREM ARI */
	
	List[] listas = acompReferenciaDao.getItensFilhosByAcompReferencia(acompReferenciaItem.getAcompReferenciaAref(), acompReferenciaItem.getItemEstruturaIett(),usuario);
	List arvore = listas[0];
	List selecionaveis = listas[1];
	
	Iterator itItem = arvore.iterator();
	
	int nivelPai = acompReferenciaItem.getItemEstruturaIett().getNivelIett().intValue();
	
	if(itItem.hasNext()){
%>
		<BR>
		<table width="100%" border="0" cellpadding="0" cellspacing="0">	
			<!-- tr><td class="espacador"><img src="../../images/pixel.gif"></td></tr -->
			<!-- Título retirado devido ao BUG 599 -->
			<!-- tr class="linha_subtitulo"><td>Sub-Níveis</td></tr-->
			<!-- tr><td class="espacador"><img src="../../images/pixel.gif"></td></tr -->
<%
	 	int contFilho = 0;
 		int contArfFilhos = 0;
			while(itItem.hasNext()){
				AcompReferenciaItemAri acompRefItemFilho = new AcompReferenciaItemAri();
				ItemEstruturaIett item = (ItemEstruturaIett) itItem.next();
				String linkVermelho = "";
				
				%>
				<tr valign="top">
				<td><%
				
				int nivel = item.getNivelIett().intValue() - nivelPai;
				
				for(int i = 1; i < nivel; i++) {
				%>
					<img src="../../images/pixel.gif" width="15" height="15">
				<%
				}
				
				boolean link = false;
				if(selecionaveis.contains(item)){
		        	link = true;
				}

				if (link){
					acompRefItemFilho = acompReferenciaItemDao.getAcompReferenciaItemByItemEstruturaIett(acompReferenciaItem.getAcompReferenciaAref(), item);
					%>
					<div class="cascata_nivel_<%=item.getNivelIett() %>">				
					
					<img src="../../images/icon_bullet_seta.png">&nbsp;<%
					
					/* testar Ari caso existam quantidades não informadas */
					List qtdNaoInformada = acompReferenciaItemDao.getAcompRealFisicoArfsComQtdNaoInformada(acompRefItemFilho);
					
					/* caso tenha quantidades não informadas mostrar link em vermelho */
					if(qtdNaoInformada.size() > 0) {
						linkVermelho = "class='link_vermelho'";
					}
					
					%>
					<%=item.getEstruturaEtt().getNomeEtt() %> - <a <%=linkVermelho%> href="javascript:mostrarEsconder(<%=contFilho%>, <%=acompRefItemFilho.getCodAri() %>)"><%=item.getNomeIett()%></a>
					<%
					
					
// Montagem do esqueminha de mostrar os níveis abaixo e seus respectivos indicadores de resultado,
// permitindo a inserção/alteração dos mesmos, na tela corrente

	/* selecionar os indicadores dependendo da situacao selecionada*/
	lAcompRF = acompRealFisicoDao.getIndResulByAcompRefItemBySituacao(acompRefItemFilho, radConcluido, false);
	it = lAcompRF.iterator();
	unidadeMedida = "";
%><div id="div<%=contFilho%>" style="display:none">
<!-- 	<form  name="form<%=contFilho %>" method="post" >	 -->
	<script>
		document.getElementById('contFilho').value = <%=contFilho%>;
	</script>
	<% 
	/* se possuir lista mostrar os botões */
	if (it.hasNext()){
		String nome = "";
%>
		<table class="barrabotoes" cellpadding="0" cellspacing="0">
			<tr>
				<td class="label">
					&nbsp;
				</td>
				<td>
					<input name="btnGravarFilho" type="button" value="Gravar" class="botao" onclick="aoClicarGravarFilho(form, <%=contFilho%>);">
					<input name="btnCancelar" type="button" value="Cancelar" class="botao" onclick="aoClicarCancelar(form);">
				</td>
			</tr>
		</table>		
		
		<table width="100%" border="0" cellpadding="0" cellspacing="0">	
			<tr><td class="espacador" colspan="9"><img src="../../images/pixel.gif"></td></tr>
			<tr class="linha_titulo">
				<td colspan="3">&nbsp;</td>
				<td colspan="5" align="right">
					Acumulados: 
					<input type="radio" class="form_check_radio" name="radAcumulados<%=contFilho %>" value="P" onclick="aoClicarTrocar('P', <%=contFilho %>);" checked> Até o ciclo 
					<input type="radio" class="form_check_radio" name="radAcumulados<%=contFilho %>" value="T" onclick="aoClicarTrocar('T', <%=contFilho %>);"> Total
				</td>
			</tr>
			<tr><td class="espacador" colspan="8"><img src="../../images/pixel.gif"></td></tr>
			
			<%
			if(configura.getSisGrupoAtributoSgaByCodSgaGrAtrMetasFisicas() == null){
			%>
			<tr class="linha_subtitulo">
				<td width="10%" align="center">Nome</td>
				<td width="5%" align="center">Previsto<br><%=LABEL_TOTAL%></td>
				<td width="5%" align="center">Realizado<br><%=LABEL_TOTAL%></td>
				<td width="5%" align="center">Previsto<br>Exercício</td>
				<td width="5%" align="center">Realizado<br>Exercício</td>
				<td width="18%" align="center" colspan="2" align="center">Realizado</td>
				<td width="12%" align="center">Situação</td>
			</tr>
			<%
			}
			%>
			<tr><td class="espacador" colspan="8"><img src="../../images/pixel.gif"></td></tr>
<%				
				String realizadoTotalP;
				String realizadoExercicioP;
				String realizadoTotalT;
				String realizadoExercicioT;
				contArfFilhos = 0;
				String grupoIndicador = "Indicador de Resultado";
				String corArfFilhos = new String();
				while (it.hasNext()){
					if (contArfFilhos % 2 == 0){
						corArfFilhos = "cor_nao";
					} else {
						corArfFilhos = "cor_sim"; 
					}
					contArfFilhos++;
					AcompRealFisicoArf acompRealFisico = (AcompRealFisicoArf) it.next();
					unidadeMedida = Util.substring(acompRealFisico.getItemEstrtIndResulIettr().getUnidMedidaIettr(), 0, 3);
					
					if(!estavaDesabilitado){
						//Verificar se é para habilitar ou não edição conforme regra do mantis 7795
						_disabled = (acompRealFisicoDao.habilitarEdicaoArf(acompRealFisico, acompReferenciaItem.getAcompReferenciaAref(), radConcluido)) ? "" : "disabled";
					}
					
					if(configura.getSisGrupoAtributoSgaByCodSgaGrAtrMetasFisicas() != null){
						if(acompRealFisico.getItemEstrtIndResulIettr().getSisAtributoSatb() != null && !grupoIndicador.equals(acompRealFisico.getItemEstrtIndResulIettr().getSisAtributoSatb().getDescricaoSatb())){
							grupoIndicador = acompRealFisico.getItemEstrtIndResulIettr().getSisAtributoSatb().getDescricaoSatb();
							%>
							<tr class="linha_subtitulo">
								<td width="10%" align="center"><%=grupoIndicador%></td>
								<td width="5%" align="center">Previsto<br><%=LABEL_TOTAL%></td>
								<td width="5%" align="center">Realizado<br><%=LABEL_TOTAL%></td>
								<td width="5%" align="center">Previsto<br>Exercício</td>
								<td width="5%" align="center">Realizado<br>Exercício</td>
								<td width="18%" align="center" colspan="2" align="center">Realizado</td>
								<td width="12%" align="center">Situação</td>
							</tr>
							<%
							
						}
						else if(acompRealFisico.getItemEstrtIndResulIettr().getSisAtributoSatb() == null && !"".equals(grupoIndicador)){					
							%>
							<tr class="linha_subtitulo">
								<td width="10%" align="center"><%=grupoIndicador%></td>
								<td width="5%" align="center">Previsto<br><%=LABEL_TOTAL%></td>
								<td width="5%" align="center">Realizado<br><%=LABEL_TOTAL%></td>
								<td width="5%" align="center">Previsto<br>Exercício</td>
								<td width="5%" align="center">Realizado<br>Exercício</td>
								<td width="18%" align="center" colspan="2" align="center">Realizado</td>
								<td width="12%" align="center">Situação</td>
							</tr>
							<%
							grupoIndicador = "";
						}
					}					
					
					double realizado = 0;
					String linkPorLocal = "<img src=\"../../images/empty.gif\">";
					String desabilitarQtdeARF = "";
					
					if(acompRealFisico.getQtdRealizadaArf() != null) {
						realizado = acompRealFisico.getQtdRealizadaArf().doubleValue();
					}
					
					String labelRealizado = REALIZADO_NO_MES;
					
					String previstoTotal = "0";
					String realizadoTotal = "0";
					String previstoExercicio = "0";
					String realizadoExercicio = "0";
					
					if("S".equals(acompRealFisico.getItemEstrtIndResulIettr().getIndRealPorLocal())) {
						String podeGravar = (!"".equals(_disabled)) ? "N" : "S";
						linkPorLocal = ("<a href=\"#\" onclick=\"javascript:aoClicarIndicadorPorLocal(form, '" + acompRealFisico.getCodArf() + "','" + acompRealFisico.getItemEstrtIndResulIettr().getNomeIettir() + "','" + podeGravar + "')\">" +
									   "<img src=\"../../images/icon_exibir.png\" border=\"0\" title='Quantidade/Valor por Local'></a>");
						if("".equals(_disabled)) {
							desabilitarQtdeARF = "disabled";
						}
					}
					
					previstoTotal = indResulDao.getSomaQuantidadePrevista(acompRealFisico.getItemEstrtIndResulIettr());
					/* Tratamento diferencia para exibição de indicadores Acumulavel e não-Acumulável */
					if("S".equals(acompRealFisico.getItemEstrtIndResulIettr().getIndAcumulavelIettr())){
						labelRealizado = REALIZADO_ATE_O_MES;
						previstoExercicio = Util.formataNumeroDecimal(indResulDao.getQtdPrevistoExercicio(acompRealFisico.getItemEstrtIndResulIettr(), acompRefItemFilho.getAcompReferenciaAref().getExercicioExe()));
						realizadoTotalP = Util.formataNumeroDecimal(acompRealFisicoDao.getQuantidadeRealizada(acompRefItemFilho, acompRealFisico.getItemEstrtIndResulIettr(), "P"));
						realizadoExercicioP = Util.formataNumeroDecimal(acompRealFisicoDao.getQtdRealizadaExercicio(acompRefItemFilho, acompRealFisico.getItemEstrtIndResulIettr(), "P"));
						realizadoTotalT = Util.formataNumeroDecimal(acompRealFisicoDao.getQuantidadeRealizada(acompRefItemFilho, acompRealFisico.getItemEstrtIndResulIettr(), "T"));
						realizadoExercicioT = Util.formataNumeroDecimal(acompRealFisicoDao.getQtdRealizadaExercicio(acompRefItemFilho, acompRealFisico.getItemEstrtIndResulIettr(), "T"));
					}else{
						previstoExercicio = Util.formataNumeroDecimal(indResulDao.getQtdPrevistoExercicio(acompRealFisico.getItemEstrtIndResulIettr(), acompRefItemFilho.getAcompReferenciaAref().getExercicioExe()));
						realizadoTotalP = Util.formataNumeroDecimal(acompRealFisicoDao.getQuantidadeRealizadaNaoAcumulados(acompRefItemFilho, acompRealFisico.getItemEstrtIndResulIettr(), "P", "T"));
						realizadoExercicioP = Util.formataNumeroDecimal(acompRealFisicoDao.getQuantidadeRealizadaNaoAcumulados(acompRefItemFilho, acompRealFisico.getItemEstrtIndResulIettr(), "P", "E"));
						realizadoTotalT = Util.formataNumeroDecimal(acompRealFisicoDao.getQuantidadeRealizadaNaoAcumulados(acompRefItemFilho, acompRealFisico.getItemEstrtIndResulIettr(), "T", "T"));
						realizadoExercicioT = Util.formataNumeroDecimal(acompRealFisicoDao.getQuantidadeRealizadaNaoAcumulados(acompRefItemFilho, acompRealFisico.getItemEstrtIndResulIettr(), "T", "E"));
					}
%>

			<!--  A tabela abaixo está duplica propositalmente, para que possamos ter o efeito de troca de
				Acumulado no periodo x acumulado total sem consulta ao servidor. -->
					<tr class="<%=corArfFilhos%>" onmouseover="javascript: destacaLinha(this,'over','')" onmouseout="javascript: destacaLinha(this,'out','<%=corArfFilhos%>')" onClick="javascript: destacaLinha(this,'click','<%=corArfFilhos%>')" class="linha_subtitulo2" id="acumulaPeriodo<%=contFilho %>" name="acumulaPeriodo<%=contFilho %>" >
						<td width="10%">
							<%=acompRealFisico.getItemEstrtIndResulIettr().getNomeIettir()%>
							<!-- CODARF adicionado devido ao Mantis 5518, antes era verificado pelo codIettir+codAri+codIett -->
							<input type="hidden" name="codArf<%=contArfFilhos%>-<%=contFilho%>" id="codArf<%=contArfFilhos%>-<%=contFilho%>" value="<%=acompRealFisico.getCodArf()%>">
						</td>
						
						<%
						String tipoQtde = "V";
						if("Q".equals(acompRealFisico.getItemEstrtIndResulIettr().getIndTipoQtde())){
							tipoQtde = "Q";
						}
						String validacaoIndQtde = " onblur=\"javascript:validarQtdeValor(this,'" + tipoQtde + "')\"";
						%>
						
						<td width="5%"><%=previstoTotal%>&nbsp;<%=unidadeMedida%></td>
						<td width="5%"><%=realizadoTotalP%>&nbsp;<%=unidadeMedida%></td>
						<td width="5%"><%=previstoExercicio%>&nbsp;<%=unidadeMedida%></td>
						<td width="5%"><%=realizadoExercicioP%>&nbsp;<%=unidadeMedida%></td>
						<td width="11%" align="right" nowrap="nowrap">
							<%=labelRealizado%>
							<input type="text" name="qtdRealizadaArf<%=contArfFilhos%>-<%=contFilho%>" id="qtdRealizadaArf<%=contArfFilhos%>-<%=contFilho%>" <%if(acompRealFisico.getQtdRealizadaArf() != null) {%> value="<%=Util.formataNumeroDecimal(realizado)%>" <%} else { %> value="" <%}%> size="10" maxlength="20" <%=_disabled%> <%=desabilitarQtdeARF%> <%=validacaoIndQtde%>>
							<%=linkPorLocal%>							
						</td>
						<td width="7%" align="left">
							<%=acompRealFisico.getItemEstrtIndResulIettr().getUnidMedidaIettr()%>
						</td>
						<td width="12%">
<%
						nome = "situacaoSit" + contArfFilhos + "-" + contFilho;
						String scripts = _disabled + " onchange=\"javascript:verificarSituacao(form, " + acompRealFisico.getCodArf().toString() + ", this.value)\"";
						if ( acompRealFisico.getSituacaoSit() != null ) {
%>
							<combo:ComboTag 
									nome="<%=nome%>"
									objeto="ecar.pojo.SituacaoSit" 
									label="descricaoSit" 
									value="codSit" 
									order="descricaoSit"
									filters="indMetaFisicaSit=S"
									selected="<%=acompRealFisico.getSituacaoSit().getCodSit().toString()%>"
									scripts="<%=scripts%>"
							/>
<%
						} else {
%>
							<combo:ComboTag 
									nome="<%=nome%>"
									objeto="ecar.pojo.SituacaoSit" 
									label="descricaoSit" 
									value="codSit" 
									order="descricaoSit"
									filters="indMetaFisicaSit=S"
									scripts="<%=scripts%>"
							/>
<%
						}
%>
						</td>
					</tr>
				
				
					<tr class="linha_subtitulo2" id="acumulaTotal<%=contFilho %>" name="acumulaTotal<%=contFilho %>" style="display:none">
						<td width="10%">
							<%=acompRealFisico.getItemEstrtIndResulIettr().getNomeIettir()%>
							<!-- CODARF adicionado devido ao Mantis 5518, antes era verificado pelo codIettir+codAri+codIett -->
							<input type="hidden" name="codArf<%=contArfFilhos%>-<%=contFilho%>" id="codArf<%=contArfFilhos%>-<%=contFilho%>" value="<%=acompRealFisico.getCodArf()%>">
						</td>
					
						<td width="5%"><%=previstoTotal%>&nbsp;<%=unidadeMedida%></td>
						<td width="5%"><%=realizadoTotalT%>&nbsp;<%=unidadeMedida%></td>
						<td width="5%"><%=previstoExercicio%>&nbsp;<%=unidadeMedida%></td>
						<td width="5%"><%=realizadoExercicioT%>&nbsp;<%=unidadeMedida%></td>
						
						<%
						tipoQtde = "V";
						if("Q".equals(acompRealFisico.getItemEstrtIndResulIettr().getIndTipoQtde())){
							tipoQtde = "Q";
						}
						validacaoIndQtde = " onblur=\"javascript:validarQtdeValor(this,'" + tipoQtde + "')\"";
						%>
						
						<td width="11%" align="right" nowrap="nowrap">
							<input type="text" alt="filho"  name="qtdRealizadaArf<%=contArfFilhos%>-<%=contFilho%>" id="qtdRealizadaArf<%=contArfFilhos%>-<%=contFilho%>" <%if(acompRealFisico.getQtdRealizadaArf() != null) {%> value="<%=Util.formataNumeroDecimal(realizado)%>" <%} else { %> value="" <%}%> size="10" maxlength="20" <%=_disabled%> <%=desabilitarQtdeARF%> <%=validacaoIndQtde%>>
							<%=linkPorLocal%>
						</td>
						<td width="7%" align="left">
							<%=acompRealFisico.getItemEstrtIndResulIettr().getUnidMedidaIettr()%>
						</td>
						<td width="12%">
<%
						nome = "situacaoSit" + contArfFilhos + "-" + contFilho;
						scripts = _disabled + " onchange=\"javascript:verificarSituacao(form, " + acompRealFisico.getCodArf().toString() + ", this.value)\"";

						if ( acompRealFisico.getSituacaoSit() != null ) {
%>
							<combo:ComboTag 
									nome="<%=nome%>"
									objeto="ecar.pojo.SituacaoSit" 
									label="descricaoSit" 
									value="codSit" 
									order="descricaoSit"
									filters="indMetaFisicaSit=S"
									selected="<%=acompRealFisico.getSituacaoSit().getCodSit().toString()%>"
									scripts="<%=scripts%>"
							/>
<%
						} else {
%>
							<combo:ComboTag 
									nome="<%=nome%>"
									objeto="ecar.pojo.SituacaoSit" 
									label="descricaoSit" 
									value="codSit" 
									order="descricaoSit"
									filters="indMetaFisicaSit=S"
									scripts="<%=scripts%>"
							/>
<%
						}
%>
						</td>
					</tr>
						
<%
				}
//			}//fim do else sobre situação
%>
			<tr><td class="espacador" colspan="8"><img src="../../images/pixel.gif"></td></tr>
		</table>
		
		<table class="barrabotoes" cellpadding="0" cellspacing="0">
			<tr>
				<td class="label">
					&nbsp;
				</td>
				<td>
					<input name="btnGravarFilho" type="button" value="Gravar" class="botao" onclick="aoClicarGravarFilho(form, <%=contFilho%>);">
					<input name="btnCancelar" type="button" value="Cancelar" class="botao" onclick="aoClicarCancelar(form);">
				</td>
			</tr>
		</table>
		
		<input type="hidden" name="hidContFilhos<%=contFilho%>" id="hidContFilhos<%=contFilho%>" value="<%=contArfFilhos%>">	
<%
	} else {
		// se nao possui, verificar mensagem
%>
		<table width="100%" border="0" cellpadding="0" cellspacing="0">	
			<tr><td class="espacador" colspan="9"><img src="../../images/pixel.gif"></td></tr>
			<tr class="linha_subtitulo2" colspan="9">
				<td>Não há Indicadores de Resultado para este ciclo de acompanhamento</td>
			</tr>
			<tr><td class="espacador" colspan="9"><img src="../../images/pixel.gif"></td></tr>
		</table>
<%
	}
	
	
//***********
//  Retira as Metas Físicas não configuradas para o Tipo de Acompanhamento da lista de exibição	
// ************	
	novosIndicadores=null;
	if(configuracao.getSisGrupoAtributoSgaByCodSgaGrAtrMetasFisicas() != null){	
		novosIndicadores =  acompReferenciaItemDao.getNovosIndicadores(acompRefItemFilho); 

		//remover os indicadores que não pertençam à coleção de atributos identificadas acima
		if(!listAtributosConfig.isEmpty()){
		
			itIndicadores = novosIndicadores.iterator();
	  			while(itIndicadores.hasNext()) {
	  				ItemEstrtIndResulIettr itemEstrtIndResulIettr = (ItemEstrtIndResulIettr) itIndicadores.next(); 
	   				
	  				if(itemEstrtIndResulIettr.getSisAtributoSatb() != null && !listAtributosConfig.contains(itemEstrtIndResulIettr.getSisAtributoSatb())) {
	  					itIndicadores.remove();
	  				} 
	  			}
			}
		
	}	
	
	
	/* MOSTRAR LISTA DE NOVOS INDICADORES NÃO CADASTRADOS PARA O ARI */
	//novosIndicadores = acompReferenciaItemDao.getNovosIndicadores(acompRefItemFilho);
	
	if(novosIndicadores!=null) {
		itIndicadores = novosIndicadores.iterator();
	} else{
		novosIndicadores =  acompReferenciaItemDao.getNovosIndicadores(acompRefItemFilho);
		itIndicadores = novosIndicadores.iterator();
	}
	

	if(itIndicadores.hasNext()){
%>
		<BR>
		<table width="100%" border="0" cellpadding="0" cellspacing="0">	
			<tr><td class="espacador"><img src="../../images/pixel.gif"></td></tr>
			<tr class="linha_subtitulo"><td>Novo(s) Indicador(es) de Resultado (clique para adicionar)</td></tr>
			<tr><td class="espacador"><img src="../../images/pixel.gif"></td></tr>
<%
			while(itIndicadores.hasNext()){
				ItemEstrtIndResulIettr indResul = (ItemEstrtIndResulIettr) itIndicadores.next();
%>
				<tr class="linha_subtitulo2"><td>
					<a href="javaScript:aoClicarNovoIndicador(form, <%=indResul.getCodIettir()%>);">
					<%=indResul.getNomeIettir()%></a>
				</td></tr>
<%
			}
%>
			<tr><td class="espacador"><img src="../../images/pixel.gif"></td></tr>
		</table>
<%
	}
		}else{
				%>
					<img src="../../images/icon_bullet_seta.png">&nbsp;<%=item.getNomeIett()%>
				<%
				}
				
				%>
				<!-- /div-->
				</td>
				</tr>
				
			<%
			contFilho++;
			}
%>
		</table>
<%
	}
%>
</div>
</form>
</div>
</div>

<script language="JavaScript">
	/* Para montar as validações em javascript: 										*/
	/*  	cont - é a variável que guardou a quantidade de registros (linhas) do form,	*/
	/*				sendo necessária para montar os nomes dos campos que se forma com 	*/
	/*				nomeCampo + cont (Ex: nome1, nome2)									*/
	function validarPai(form){
		var jsCont = 1;
		var cont   = <%=cont%>;
			
		while (jsCont <= cont){		
			if( eval("form.qtdRealizadaArf" + jsCont + ".disabled") == false ) {
				if( Trim( eval("form.qtdRealizadaArf" + jsCont + ".value") ) != "" ) {
					if( !validaDecimal( eval("form.qtdRealizadaArf" + jsCont + ".value") ) ) {
						alert("<%=_msg.getMensagem("acompanhamento.realizadoFisico.validacao.qtdRealizadaArf.invalido")%>");
						eval("form.qtdRealizadaArf" + jsCont + ".focus()");
						return(false); 
					}
				} else {
					alert("<%=_msg.getMensagem("acompanhamento.realizadoFisico.validacao.qtdRealizadaArf.obrigatorio")%>");
					eval("form.qtdRealizadaArf" + jsCont + ".focus()");
					return(false); 
				}
			}
			
			if( eval("form.situacaoSit"+jsCont+".disabled") == false ) {
				if( eval("form.situacaoSit"+jsCont+"[form.situacaoSit"+jsCont+".selectedIndex].value") == '' &&
					Trim( eval("form.qtdRealizadaArf" + jsCont + ".value") ) != '' ) {
					
					alert("<%=_msg.getMensagem("acompanhamento.realizadoFisico.validacao.situacaoSit.obrigatorio")%>");
					eval("form.situacaoSit" + jsCont + ".focus()");
					return(false); 				
				}
			}
			
			jsCont++;
		}
				
		return(true);
	}
	
	function validarFilho(form, block) {
		var numero = block; /*form.numFilho.value*/ 
		var jsCont = 1;
		var numReg = eval("form.hidContFilhos"+block+".value");
			
		var qtdRealizadaArf = 0;
		var situacaoSit = '';
		while( jsCont <= numReg ) {		
			qtdRealizadaArf = document.getElementById('qtdRealizadaArf'+(jsCont)+'-'+numero);
			if( qtdRealizadaArf == 'undefined' ) return false; /* se campo não encontrado, sai do tratamento */
			
			if( qtdRealizadaArf.disabled == false ) {
				if( Trim(qtdRealizadaArf.value) != '' ) {
					if( !validaDecimal(qtdRealizadaArf.value) ) {
						alert("<%=_msg.getMensagem("acompanhamento.realizadoFisico.validacao.qtdRealizadaArf.invalido")%>");
						qtdRealizadaArf.focus();
						return(false);
					}
				} else {
					alert("<%=_msg.getMensagem("acompanhamento.realizadoFisico.validacao.qtdRealizadaArf.obrigatorio")%>");
					qtdRealizadaArf.focus();
					return(false); 
				}
			} 
			
			situacaoSit = document.getElementById('situacaoSit'+jsCont+'-'+numero);
			if( situacaoSit.disabled == false ) {
				if( situacaoSit[situacaoSit.selectedIndex].value == '' && Trim(qtdRealizadaArf.value) != '' ) {
					alert("<%=_msg.getMensagem("acompanhamento.realizadoFisico.validacao.situacaoSit.obrigatorio")%>");
					situacaoSit.focus();
					return(false); 	
				}			
			}
			
			jsCont++; // next field
		}
		
		return(true);
	}
	
	function validarQtdeValor(obj, tipo){
		if(obj.value != ''){
			if(tipo == 'Q'){
				if(!isInteger(obj.value)){
					alert("<%=_msg.getMensagem("acompanhamento.realizadoFisico.validacao.qtdRealizadaArf.valorInteiro")%>");
					obj.value='';
					obj.focus();
				}
			}
			
			if(tipo == 'V'){
				if(!isNumeric(obj.value) && !validaDecimal(obj.value)){
					alert("<%=_msg.getMensagem("acompanhamento.realizadoFisico.validacao.qtdRealizadaArf.valorNumeric")%>");
					obj.value='';
					obj.focus();
				}
			}
		}
	}
</script>
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
<% /* Controla o estado do Menu (aberto ou fechado) */%>
<%@ include file="../../include/estadoMenu.jsp"%>
<%@ include file="../../include/mensagemAlert.jsp" %>
</html>