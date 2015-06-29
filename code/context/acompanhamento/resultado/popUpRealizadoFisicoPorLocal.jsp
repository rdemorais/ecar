<%@ page import="java.util.List" %>
<%@ page import="java.util.Iterator" %>
<%@ page import="ecar.dao.AcompRealFisicoDao" %>
<%@ page import="ecar.pojo.AcompRealFisicoArf" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="ecar.pojo.AcompRealFisicoLocalArfl" %>
<%@ page import="java.util.Collections" %>
<%@ page import="java.util.Comparator" %>

<%@ page import="ecar.pojo.LocalGrupoLgp"%>
<%@ page import="ecar.pojo.ItemEstruturaIett" %>
<%@ page import="ecar.pojo.ExercicioExe" %>
<%@ page import="ecar.dao.ItemEstruturaDao" %>
<%@ page import="ecar.dao.ExercicioDao" %>
<%@ page import="java.util.Set"%>
<%@ page import="comum.util.Util"%>

<%@ page import="ecar.pojo.ItemEstrutLocalIettl" %>
<%@ page import="ecar.pojo.LocalItemLit" %>

<%@ page import="ecar.api.facade.*" %>


<%@ taglib uri="/WEB-INF/ecar-util.tld" prefix="util" %>
<%@ taglib uri="/WEB-INF/ecar-combo.tld" prefix="combo"%>

<%@ include file="../../frm_global.jsp"%>

<html lang="pt-br">
<head>

<%@ include file="../../include/meta.jsp"%>
<%@ include file="/titulo.jsp"%>

<link rel="stylesheet" href="<%=_pathEcar%>/css/style_<%=configuracaoCfg.getEstilo().getNome()%>.css" type="text/css">

<script language="javascript" src="../../js/prototype.js"></script>
<script language="javascript" src="../../js/jquery.js"></script>
<script language="javascript" src="../../js/focoInicial.js"></script>
<script language="javascript" src="../../js/validacoes.js"></script>
<script language="javascript" src="../../js/frmPadraoItemEstrut.js"></script>
<script language="javascript" src="../../js/popUp.js"></script>
<script language="javascript" src="../../js/numero.js"></script>
<script language="javascript" src="../../js/form_acom_real_local.js"></script>

<script language="JavaScript">

jQuery.noConflict();

function aoClicarVoltar(Form){
 	alert('aoClicarVoltar');
}

function aoClicarAlterar(Form){

	
	// Obtendo a referência para a tela principal e ao form
	// da popup
	var documentMaster = window.opener.document;
	var nomeIndicador = $('nomeIndicador').value;
	var nomeSituacao = $('nomeSituacao').value;

	

	var campoTotal = documentMaster.getElementById(nomeIndicador);
	var campoSituacao = documentMaster.getElementById(nomeSituacao);
	var situacaoLocal = $('situacaoLocal'); 	

	var temValores = false;
	var valoresValidos = true;

	jQuery(".realizadoPorLocal").each(function(){
        if($(this).value != ''){
          	temValores = true;
    		if(!validaDecimal($(this).value)) {
    			alert("<%=_msg.getMensagem("acompanhamento.realizadoFisico.validacao.qtdRealizadaArf.invalido")%>");
    			$(this).focus();
    			valoresValidos = false;
    			return(false); 
    		}

       }
   });

   if(!valoresValidos)
	   return(false);	   
		
   if(temValores){
		if( situacaoLocal[situacaoLocal.selectedIndex].value == ''){							
			alert("<%=_msg.getMensagem("acompanhamento.realizadoFisico.validacao.situacaoSit.obrigatorio")%>");
			situacaoLocal.focus();
			return(false); 				
		}
		campoTotal.value = $('total').value;
	} else { 

		if( situacaoLocal[situacaoLocal.selectedIndex].value != ''){							
			alert("<%=_msg.getMensagem("acompanhamento.realizadoFisico.validacao.qtdRealizadaArf.obrigatorio")%>");
			return(false); 
		}
	
		campoTotal.value = '';
	}

	campoSituacao.value =$('situacaoLocal')[$('situacaoLocal').selectedIndex].text;
		
	salvarLocal();

}

function aoClicarVoltar(){
		window.close();
}

// monta tooltip e apresenta em uma popup
function montaTooltipMouseClick(periodo, codLocal, nomeLocal){

	var spanname = 'spanLocal|' + codLocal;
	var cmpNome = 'cmp|' + codLocal;

	var htmlResult = '<br><b>Mês: </b>' + periodo + '<br><br><b>Local</b>: ' + nomeLocal + '<br><br><b>Quantidade/Valor do Local:</b> ' + $(cmpNome).value + '<br><br><b>Total por Mês: </b>' + $('total').value;

	var page = '<%=request.getContextPath()%>/popUp/toolTip.jsp?tip='+htmlResult;
	var options  = 'tollbar=no, directories=no, menubar=no, status=no, scrollbars=yes, ';
	    options += 'resizable=no, width=325, height=200';
	window.open(page, 'tooltip', options);

}

// Funcao gerar e apresentar tooltip com totais
function montaTooltipMouseOver(obj, periodo, codLocal, nomeLocal){

	var spanname = 'spanLocal|' + codLocal;
	var cmpNome = 'cmp|' + codLocal;
	var total = $('total').value;
	var msg = '<b>Mês: </b>' + periodo + '<br><b>Nome do Local:</b> ' + nomeLocal + '<br/><b>Quantidade/Valor do Local:</b> ' + $(cmpNome).value + '<br/><b>Total por Mês: </b>' + total;

	$( spanname ).innerHTML = msg;

	var screenwidth = ( isIE() ? document.body.clientWidth : document.width );
	var objspan     = $(spanname);
	var imgposw     = 0;

    while (obj) { <% /* Obtem a posicao do objeto na tela */ %>
        imgposw += obj.offsetLeft;
        obj      = obj.offsetParent;
    }

	if( !isIE() ) { <% /* Apenas mostra o tooltip se for != IE */ %>
		objspan.className = ( screenwidth - imgposw <= 300 ? 'dicaright' : 'dicaleft');
    	objspan.style.display = 'block';				
	}
}

// Funcao gerar tooltip no evento onmouseout - faz tooltip sumir
function montaTooltipMouseOut( codLocal ) {
	var spanname = 'spanLocal|' + codLocal;
	var objspan = $(spanname);
   	objspan.style.display = 'none';
}

</script>

<%
AcompRealFisicoDao acompRealFisicoDao = new AcompRealFisicoDao(request);

AcompRealFisicoArf arf = (AcompRealFisicoArf)acompRealFisicoDao.buscar(AcompRealFisicoArf.class, Long.valueOf(Pagina.getParamStr(request, "codARF")));

String qtdeOuValor = "Quantidade";
if ("V".equalsIgnoreCase(arf.getItemEstrtIndResulIettr().getIndTipoQtde())) {
	qtdeOuValor = "Valor";
}

Boolean podeGravarAleracoes = "S".equals(Pagina.getParamStr(request, "podeGravar")) ? new Boolean(true) : new Boolean(false);
%>

<script>
function aoClicarBtn1(form){
	form.hidAcao.value = "incluir";
	form.action = "popUpCtrlRealizadoFisicoPorLocal.jsp";

	if(validar(form))
		form.submit();
}

function validar(form) {
	if(form.codLit.value == '') {
		alert('<%=_msg.getMensagem("acompanhamento.realizadoFisico.quantidadePorLocal.local.invalido")%>');
		return false;
	}
	
	form.qtde.value = Trim(form.qtde.value.replace('.', ''));
	if (Trim(form.qtde.value) != "") {
		if(!validaDecimal(form.qtde.value)) {
			alert('<%=_msg.getMensagem("acompanhamento.realizadoFisico.quantidadePorLocal.qtde.invalido")%>');
			form.qtde.focus();
			return false;
		}
	} else {
		alert('<%=_msg.getMensagem("acompanhamento.realizadoFisico.quantidadePorLocal.qtde.invalido")%>');
		form.qtde.focus();
		return false;
	}

	return true;
}

function aoClicarBtn2(form){
	window.close();
}

function setarLocal(codLit, identificacaoLit){
	document.form.codLit.value = codLit;
	document.form.nomeLocal.value = identificacaoLit;
}

function excluirLocal(form){
	if(validarExclusao(form, 'excluir')){
		if(!confirm("Confirma a exclusão?")){
			return false;
		}
		form.hidAcao.value = "excluir";
		form.action = "popUpCtrlRealizadoFisicoPorLocal.jsp";
		form.submit();
	}
}

function validarQtdeValor(obj){
	if(!isInteger(obj.value)){
		alert("<%=_msg.getMensagem("itemEstrutura.quantPrevista.validacao.indTipoQtde.valorInt")%>");
		obj.focus();
	}
}
</script>

</head>

<body class="corpo_popup">
<%
try {
%>

<form name="form" id="form" method="post" action="">
	<input type="hidden" name="hidAcao" id="hidAcao">
	<input type="hidden" name="podeGravar" id="podeGravar" value="<%=Pagina.getParamStr(request, "podeGravar")%>">
	<input type="hidden" name="nomeIndicador" id="nomeIndicador" value="<%=Pagina.getParamStr(request, "nomeIndicador") %>">
	<input type="hidden" name="nomeSituacao" id="nomeSituacao" value="<%=Pagina.getParamStr(request, "nomeSituacao") %>">	
	<input type="hidden" name="nivel" id="nivel" value="<%=Pagina.getParamStr(request, "nivel") %>">	
	<%
	
		Long codARFLong = Long.valueOf(Pagina.getParamStr(request, "codARF"));
		AcompRealFisicoArf acompRealFisico = (AcompRealFisicoArf) acompRealFisicoDao.buscar(AcompRealFisicoArf.class, codARFLong);
		ItemEstruturaIett item = acompRealFisico.getItemEstruturaIett();
	
		
		Realizado realizado = new Realizado(acompRealFisico);
		IndicadorResultado indicador = new IndicadorResultado(acompRealFisico.getItemEstrtIndResulIettr());
		boolean isConcluidoNoPeriodo = false;
		
		AcompanhamentoItemEstrutura periodoReferencia = new AcompanhamentoItemEstrutura(new Long(Pagina.getParamLong(request,"codARI")));
		//ciclo selecionado no select box "Ciclo de referência"
		EcarData dataPeriodoAtual = new EcarData(periodoReferencia.getMesReferencia(), periodoReferencia.getAnoReferencia());
		
		//se o indicador foi concluído antes a esse ciclo
		//o forma será bloqueado para não ter seus valores modificados
		if(indicador.isConcluido() && 
				   indicador.getDataConclusao().compareTo(dataPeriodoAtual) == EcarData.ANTES ){
			
			isConcluidoNoPeriodo = true;
			//atribui o valor realizado do ciclo em que o indicador foi concluído.
			acompRealFisico = indicador.getValorRealizadoConclusao().getRealObject();
		}
		
	 %>	
	
	<input type="hidden" name="codARF" id="codARF" value="<%=acompRealFisico.getCodArf().toString() %>" />	

	<util:barrabotoes alterar="Gravar" voltar="Cancelar"/>	
	<table id="indicadores" width="100%" class="indicadoresResultado">
		<tr>
		<td colspan="2"><br/></td>
		</tr>
		<tr>
			<td width="50%" id="indicadores" class="indicadoresResultado">
				Situação
			</td>
		<td width="50%" class="indicadoresResultado">
   <%							
   
   
	String _disabledQtdSituacao = _disabled;
	
	if (acompRealFisico.getItemEstrtIndResulIettr().getRealizadoServicoSer() != null && acompRealFisico.getQtdRealizadaArf() != null) {
		_disabledQtdSituacao = "disabled";
	}
	//else{
	//	_disabled = "";
	//}
	
	// Em todos os casos que a data de início não for informada, 
	// o sistema não permite a inclusão/alteração de valores realizados nos indicadores
	if(acompRealFisico.getItemEstruturaIett().getDataInicioIett() == null) {
		_disabledQtdSituacao = "disabled";
	}
	
	String scripts = _disabledQtdSituacao + " onchange=\"javascript:verificarSituacao(form, " + acompRealFisico.getCodArf().toString() + ", this.value)\"";

   
   if ( acompRealFisico.getSituacaoSit() != null ) {
	%>
								<combo:ComboTag 
										nome="situacaoLocal"
										objeto="ecar.pojo.SituacaoSit" 
										label="descricaoSit" 
										value="codSit" 
										order="descricaoSit"
										filters="indMetaFisicaSit=S"
										selected="<%=acompRealFisico.getSituacaoSit().getCodSit().toString()%>"
										scripts="<%=scripts%>"
										disabled="<%= new Boolean(isConcluidoNoPeriodo).toString() %>"
										
								/>
	<%
							} else {
	%>
								<combo:ComboTag 
										nome="situacaoLocal"
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
		<tr>
		<td colspan="2"><br/></td>
		</tr>
		<tr>
			<td width="50%" id="indicadores" class="indicadoresResultado">
				Local
			</td>
			<td width="50%" id="indicadores" class="indicadoresResultado">
				Mês
			</td>
		</tr>
		<tr>
			<td colspan="2" width="100%" id="indicadores" class="indicadoresResultado">
				<div id="divLocal"></div>
			</td>
		</tr>
	</table>
	
    <util:barrabotoes alterar="Gravar" voltar="Cancelar"/>

 
</form>
 
 
  <script>
	carregarlocalRaiz();
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
</body>

</html>