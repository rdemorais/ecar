<%@ include file="/login/validaAcesso.jsp"%>
<%@ include file="/frm_global.jsp"%>

<%@ taglib uri="/WEB-INF/ecar-util.tld" prefix="util" %>
<%@ taglib uri="/WEB-INF/ecar-combo.tld" prefix="combo" %>
<%@ page import="ecar.pojo.EstrutTpFuncAcmpEtttfa" %>
<%@ page import="ecar.pojo.EstrutTpFuncAcmpEtttfaPK" %>
<%@ page import="ecar.dao.EstruturaTipoFuncAcompDao" %> 
<%@ page import="ecar.pojo.EstruturaEtt" %>
<%@ page import="ecar.pojo.TipoFuncAcompTpfa" %>
<%@ page import="ecar.dao.EstruturaDao" %> 
<%@ page import="ecar.dao.TipoFuncAcompDao" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %>

<html lang="pt-br">
<head>
<%@ include file="/include/meta.jsp"%>
<%@ include file="/titulo.jsp"%>
<link rel="stylesheet" href="<%=_pathEcar%>/css/style_<%=configuracaoCfg.getEstilo().getNome()%>.css" type="text/css">
<script language="javascript" src="<%=_pathEcar%>/js/menu_retratil.js" type="text/javascript"></script>
<script language="javascript" src="<%=_pathEcar%>/js/focoInicial.js" type="text/javascript"></script>
<script language="javascript" src="<%=_pathEcar%>/js/frmPadrao.js" type="text/javascript"></script>
<script language="javascript" src="<%=_pathEcar%>/js/validacoes.js" type="text/javascript"></script>
<script language="javascript" src="<%=_pathEcar%>/js/numero.js" type="text/javascript"></script>
<script language="javascript" src="<%=_pathEcar%>/js/exibeEscondeListAcomp.js" type="text/javascript"></script>

<script language="javascript" type="text/javascript">

function validarAlterar(form){
	if(Trim(form.seqApresentTelaCampoEtttfa.value) != ""){
		if(!validaNum(form.seqApresentTelaCampoEtttfa, "Sequência de Apresentação em Telas Cadastramento", true)){
			form.seqApresentTelaCampoEtttfa.focus();
			return(false);
		}	
	} else{
		alert("<%=_msg.getMensagem("estruturaTipoFuncAcomp.validacao.indSeqCampoTelaEtttfa.obrigatorio")%>");
		form.seqApresentTelaCampoEtttfa.focus();
		return(false);
	}
	
	if(Trim(form.seqApresListagemTelaEtttfa.value) != ""){
		if(!validaNum(form.seqApresListagemTelaEtttfa, "Sequência de Apresentação em Telas de Listagem", true)){
			form.seqApresListagemTelaEtttfa.focus();
			return(false);
		}	
	}
	
	if(Trim(form.larguraListagemTelaEtttfa.value) != ""){
		if(!validaNum(form.larguraListagemTelaEtttfa, "Largura da Listagem em Tela", true)){
			form.larguraListagemTelaEtttfa.focus();
			return(false);
		}	
	}
	
	if(form.indListagemTelaEtttfa.checked == false 
		&& (Trim(form.seqApresListagemTelaEtttfa.value) != ""  
		|| Trim(form.larguraListagemTelaEtttfa.value) != "") ) {
		alert("A seleção do campo 'Exibir na Lista' é obrigatório para se informar a Seqüência e Largura em Lista do Cadastro de Itens.");
		return(false);
	} else if (form.indListagemTelaEtttfa.checked == true){
		if (Trim(form.seqApresListagemTelaEtttfa.value) == "") {
			alert("<%=_msg.getMensagem("estruturaTipoFuncAcomp.validacao.seqApresListagemTelaEtttfa.obrigatorio")%>");
			return (false);
		} else if (Trim(form.larguraListagemTelaEtttfa.value) == "") {
			alert("<%=_msg.getMensagem("estruturaTipoFuncAcomp.validacao.larguraListagemTelaEtttfa.obrigatorio")%>");
			return (false);
		}
	}
	
	return true;
}

</script>

<!-- sempre colocar o foco inicial no primeiro campo -->
</head><body onload="javascript:onLoad(document.form); document.form.estruturaEtt.disabled = true; document.form.tipoFuncAcompTpfa.disabled = true">

<%@ include file="/cabecalho.jsp" %>
<%@ include file="/divmenu.jsp"%>

<div id="conteudo">

<form name="form" method="post" action="">
	<input type="hidden" name="hidAcao" value="alterar">
	<input type="hidden" name="hidRegistro" value='<%=Pagina.getParamInt(request, "hidRegistro")%>'>
	
	<!-- TITULO -->
	<%@ include file="/titulo_tela.jsp"%>
<%	

boolean ehPesquisa=false;

try {
	List listaEstrutura = new EstruturaDao(request).listar(EstruturaEtt.class, new String[]{"nomeEtt", "asc"});
	List listaTipoFuncAcomp = new TipoFuncAcompDao(request).listar(TipoFuncAcompTpfa.class, new String[]{"labelTpfa", "asc"});

	String selected = "";

	EstruturaTipoFuncAcompDao estruturaTipoFuncAcompDao = new EstruturaTipoFuncAcompDao(request);

	EstrutTpFuncAcmpEtttfaPK chave = new EstrutTpFuncAcmpEtttfaPK();
	chave.setCodEtt(Long.valueOf(Pagina.getParamLong(request, "estruturaEtt")));
	chave.setCodTpfa(Long.valueOf(Pagina.getParamLong(request, "tipoFuncaoAcompTpfa")));
	
	EstrutTpFuncAcmpEtttfa estruturaTipoFuncAcomp = (EstrutTpFuncAcmpEtttfa) estruturaTipoFuncAcompDao.buscar(EstrutTpFuncAcmpEtttfa.class, chave);
			
	String selectedEstrutura = estruturaTipoFuncAcomp.getEstruturaEtt().getCodEtt().toString();
	String selectedTipoFuncAcomp = estruturaTipoFuncAcomp.getTipoFuncAcompTpfa().getCodTpfa().toString();
	
	_disabled = "";
%>

<%/* campos hidden com código estrutura e código função pois nesta página as combos com estas informações
		estão desabilitadas e, por isso, não são submetidas */
%>
		<input type="hidden" name="codEtt" value="<%=selectedEstrutura%>">
		<input type="hidden" name="codTpfa" value="<%=selectedTipoFuncAcomp%>">		

		<util:linkstop pesquisar="frm_pes.jsp"/>
		<util:barrabotoes alterar="Gravar" voltar="Cancelar"/>

		<%@ include file="form.jsp"%>
	
		<util:barrabotoes alterar="Gravar" voltar="Cancelar"/>

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
</html>
