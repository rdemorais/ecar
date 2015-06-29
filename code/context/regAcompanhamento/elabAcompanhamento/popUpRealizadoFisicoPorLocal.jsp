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

<%@ taglib uri="/WEB-INF/ecar-util.tld" prefix="util" %>
<%@ include file="../../frm_global.jsp"%>
<%
	ConfiguracaoCfg configuracao = (ConfiguracaoCfg)session.getAttribute("configuracao");
%>

<%@page import="ecar.pojo.ConfiguracaoCfg"%><html lang="pt-br">
<head>
<script language="javascript" src="../../js/prototype.js"></script>
<%@ include file="../../include/meta.jsp"%>
<link rel="stylesheet" href="<%=_pathEcar%>/css/style_<%=configuracao.getEstilo().getNome()%>.css" type="text/css">
<script language="javascript" src="../../js/focoInicial.js"></script>
<script language="javascript" src="../../js/validacoes.js"></script>
<script language="javascript" src="../../js/frmPadraoItemEstrut.js"></script>
<script language="javascript" src="../../js/popUp.js"></script>
<script language="javascript" src="../../js/numero.js"></script>
<script language="javascript" src="../../js/form_acom_real_local.js"></script>

<script language="JavaScript">

function aoClicarVoltar(Form){
 	alert('aoClicarVoltar');
}

function aoClicarAlterar(Form){

	// Obtendo a referência para a tela principal e ao form
	// da popup
	var documentMaster = window.opener.document;
	var nomeIndicador = $('nomeIndicador').value;

	var campoTotal = documentMaster.getElementById(nomeIndicador);
	campoTotal.value = $('total').value;

	salvarLocal();
}

function aoClicarVoltar(){
		window.close();
}

// monta tooltip e apresenta em uma popup
function montaTooltipMouseClick(periodo, codLocal, nomeLocal){

	var spanname = 'spanLocal|' + codLocal;
	var cmpNome = 'cmp|' + codLocal;

	var htmlResult = '<br><b>Ciclo: </b>' + periodo + '<br><br><b>Local</b>: ' + nomeLocal + '<br><br><b>Quantidade/Valor do Local:</b> ' + $(cmpNome).value + '<br><br><b>Total por Ciclo: </b>' + $('total').value;

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
	var msg = '<b>Ciclo: </b>' + periodo + '<br><b>Nome do Local:</b> ' + nomeLocal + '<br/><b>Quantidade/Valor do Local:</b> ' + $(cmpNome).value + '<br/><b>Total por Ciclo: </b>' + total;

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
/*
function pesquisarLocal(form){
	var janela = abreJanela('popUpPesquisaLocalRealizadoFisico.jsp?iett=<%=arf.getItemEstruturaIett().getCodIett()%>','590', '400', 'popUpPesquisaLocalRealizadoFisico');
	janela.focus();
}
*/

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


<form name="form" id="form" method="post" action="">
	
	<input type="hidden" name="hidAcao" id="hidAcao">
	<input type="hidden" name="codARF" id="codARF" value="<%=Pagina.getParamStr(request, "codARF")%>">	
	<input type="hidden" name="podeGravar" id="podeGravar" value="<%=Pagina.getParamStr(request, "podeGravar")%>">
	<input type="hidden" name="nomeIndicador" id="nomeIndicador" value="<%=Pagina.getParamStr(request, "nomeIndicador") %>">
	
	
	<%
		Long codARFLong = Long.valueOf(Pagina.getParamStr(request, "codARF"));
		AcompRealFisicoArf acompRealFisico = (AcompRealFisicoArf) acompRealFisicoDao.buscar(AcompRealFisicoArf.class, codARFLong);
		ItemEstruturaIett item = acompRealFisico.getItemEstruturaIett();
			
		List locaisItem = new ArrayList(item.getItemEstrutLocalIettls());
		LocalGrupoLgp localGrupoItem = ((ItemEstrutLocalIettl)locaisItem.get(0)).getLocalItemLit().getLocalGrupoLgp();
	
	 %>
	
	
	
	<util:barrabotoes alterar="Gravar" voltar="Cancelar"/>	
	<table width="100%" class='layoutFixo' bgcolor='#EAEEF4' style='table-layout: fixed; text-align: left; margin-left: auto; margin-right: auto;' border='0' cellpadding='0' cellspacing='0' >
		<tr>
			<td width="50%" style='font-size: 14px;color: #003366;font-weight: bold;'  align='center' >
				Local
			</td>
			<td width="50%" style='font-size: 14px;color: #003366;font-weight: bold;'  align='center' >
				Ciclo
			</td>
		</tr>
		<tr>
			<td colspan="2" width="100%">
				<div id="divLocal"></div>
			</td>
		</tr>
	</table>
    <util:barrabotoes alterar="Gravar" voltar="Cancelar"/>
 
</form>
 
  <script>
  	
	carregarlocalRaiz('<%=localGrupoItem.getCodLgp()%>');
  </script>

</body>

</html>