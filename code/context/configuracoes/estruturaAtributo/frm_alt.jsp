
<jsp:directive.page import="ecar.dao.FuncaoDao"/>
<jsp:directive.page import="ecar.pojo.FuncaoFun"/><%@ include file="/login/validaAcesso.jsp"%>
<%@ include file="/frm_global.jsp"%>

<%@ taglib uri="/WEB-INF/ecar-util.tld" prefix="util"%>
<%@ taglib uri="/WEB-INF/ecar-combo.tld" prefix="combo"%>

<%@ page import="ecar.pojo.EstruturaEtt"%>
<%@ page import="ecar.pojo.AtributosAtb"%>
<%@ page import="ecar.pojo.EstruturaAtributoEttat"%>
<%@ page import="ecar.pojo.EstruturaAtributoEttatPK"%>
<%@ page import="ecar.dao.EstruturaDao"%>
<%@ page import="ecar.dao.AtributoDao"%>
<%@ page import="ecar.dao.EstruturaAtributoDao"%>
<%@ page import="java.util.List"%>


<%@page import="ecar.pojo.SisAtributoSatb"%><html lang="pt-br">
	<head>
<%@ include file="/include/meta.jsp"%>
<%@ include file="/titulo.jsp"%>
<link rel="stylesheet" href="<%=_pathEcar%>/css/style_<%=configuracaoCfg.getEstilo().getNome()%>.css" type="text/css">
<script language="javascript" src="<%=_pathEcar%>/js/menu_retratil.js" type="text/javascript"></script>
<script language="javascript" src="<%=_pathEcar%>/js/focoInicial.js" type="text/javascript"></script>
<script language="javascript" src="<%=_pathEcar%>/js/frmPadrao.js" type="text/javascript"></script>
<script language="javascript" src="<%=_pathEcar%>/js/numero.js" type="text/javascript"></script>
<script language="javascript" src="<%=_pathEcar%>/js/validacoes.js" type="text/javascript"></script>
<script language="javascript" src="<%=_pathEcar%>/js/richtext.js" type="text/javascript"></script>
<script language="javascript" src="<%=_pathEcar%>/js/exibeEscondeListAcomp.js" type="text/javascript"></script>
<script language="javascript" type="text/javascript">

function validarAlterar(form){
	setHiddenVal('rte');
	form.richText.value = form.richText.value.replace(/>/g, "&gt;").replace(/\n/g, "");
	form.richText.value = form.richText.value.replace(/&gt;/g, ">");
	form.documentacaoEttat.value = form.richText.value;
	
	if(!validaString(form.labelEstruturaEttat,'Label a utilizar no Nível', true)){
		return(false);
	}

	if(form.indListagemTelaEttat.checked == true){
		if(Trim(form.larguraListagemTelaEttat.value) != ""){
				if(!validaNum(form.larguraListagemTelaEttat, "Largura da Coluna em Tela", true)){
					form.larguraListagemTelaEttat.focus();
					return(false);
				}	
		}else{
				alert("<%=_msg.getMensagem("estruturaAtributo.validacao.larguraListagemTelaEttat.obrigatorio")%>");
				form.larguraListagemTelaEttat.focus();
				return(false);
		}
	}

	if(Trim(form.seqApresentTelaCampoEttat.value) != ""){
			if(!validaNum(form.seqApresentTelaCampoEttat, "Sequência de Apresentação em Telas de Informação", true)){
				form.seqApresentTelaCampoEttat.focus();
				return(false);
			}
	}else{
			alert("<%=_msg.getMensagem("estruturaAtributo.validacao.seqApresentTelaCampoEttat.obrigatorio")%>");
			form.seqApresentTelaCampoEttat.focus();
			return(false);
	}
	
	if(form.indListagemTelaEttat.checked == true){
		if(Trim(form.seqApresListagemTelaEttat.value) != ""){
				if(!validaNum(form.seqApresListagemTelaEttat, "Sequência de Apresentação em Telas de Listagem", true)){
					form.seqApresListagemTelaEttat.focus();
					return(false);
				}	
		}else{
				alert("<%=_msg.getMensagem("estruturaAtributo.validacao.seqApresListagemTelaEttat.obrigatorio")%>");
				form.seqApresListagemTelaEttat.focus();
				return(false);
		}
	}
	
	/*Validação dos Tipos de Acompanhamentos*/
	
	if(form.codTas != null && form.codTas.value != ""){
		var tas = form.codTas.value.split('|');
		var tam = tas.length;
		var cont = 0;
		while(cont < tam){		
			if(trim(tas[cont]) != ""){
				var campoCheck = "form.exibirEata" + tas[cont];
				var campoSequencia = "form.sequenciaEata" + tas[cont];
				
				if(eval(campoCheck).checked == true){
				
					if(!validaNum(eval(campoSequencia), "Sequência de Apresentação do Tipo de Acompanhamento", true)){
						eval(campoSequencia).focus();
						return false;
					}
				}
			}
			cont++;
		}
	}
	if(form.labelTamanhoConteudo.value != "" ) {
		
		if(!isNumeric(form.labelTamanhoConteudo.value)){
			alert("Digite somente números");
			eval(form.labelTamanhoConteudo.focus());				
			return false;
		}	
			
	}
	/*
	else{
		alert("Campo Tamanho do Conteudo é obrigatório!");
		return false;
	}
	*/
	return true;
}

</script>

		<!-- sempre colocar o foco inicial no primeiro campo -->
	</head><body
		onload="javascript:onLoad(document.form); document.form.estruturaEtt.disabled = true; document.form.funcaoFun.disabled = true; document.form.atributosAtb.disabled = true">

		<%@ include file="/cabecalho.jsp"%>
		<%@ include file="/divmenu.jsp"%>

		<div id="conteudo">

			<form name="form" method="post" action="">
				<input type="hidden" name="hidAcao" value="alterar">
				<input type="hidden" name="hidRegistro"
					value='<%=Pagina.getParamInt(request, "hidRegistro")%>'>

				<!-- TITULO -->
				<%@ include file="/titulo_tela.jsp"%>
<%

boolean ehPesquisa = false;

try {
	List listaEstrutura = new EstruturaDao(request).listar(
	EstruturaEtt.class, new String[] { "nomeEtt", "asc" });
	List listaFuncao = new FuncaoDao(request).listar(
	FuncaoFun.class, new String[] {"nomeFun", "asc"});
	List listaAtributo = new AtributoDao(request).listar(
	AtributosAtb.class, new String[] { "nomeAtb", "asc" });

	String selected = "";

	EstruturaAtributoDao estruturaAtributoDao = new EstruturaAtributoDao(request);

	EstruturaAtributoEttatPK chave = new EstruturaAtributoEttatPK();
	chave.setCodEtt(Long.valueOf(Pagina.getParamLong(request,"estruturaEtt")));
	chave.setCodAtb(Long.valueOf(Pagina.getParamLong(request,"atributosAtb")));

	EstruturaAtributoEttat estruturaAtributo = (EstruturaAtributoEttat) estruturaAtributoDao.buscar(EstruturaAtributoEttat.class, chave);

	String selectedEstrutura = estruturaAtributo.getEstruturaEtt().getCodEtt().toString();
	String selectedAtributo = estruturaAtributo.getAtributosAtb().getCodAtb().toString();
	String selectedFuncao = estruturaAtributo.getAtributosAtb().getFuncaoFun().getCodFun().toString();

	_disabled = "";
	
	String strReadOnly = "false";
	boolean blVisualizaDesc = true;
	String _pesqdisabled = "";
	String obrigatorioDisabled = "";
	
	
    SisAtributoSatb sisAtributo = null;
    if (estruturaAtributo.getAtributosAtb().getSisGrupoAtributoSga() != null && estruturaAtributo.getAtributosAtb().getSisGrupoAtributoSga().getSisAtributoSatbs() != null && !estruturaAtributo.getAtributosAtb().getSisGrupoAtributoSga().getSisAtributoSatbs().isEmpty()) {
    	sisAtributo = (SisAtributoSatb) estruturaAtributo.getAtributosAtb().getSisGrupoAtributoSga().getSisAtributoSatbs().iterator().next();
    	
    	if (sisAtributo.isAtributoAutoIcremental()) {
    		obrigatorioDisabled = "disabled";
    	}
    	
    }
	
	
%>

<%
		 /* campos hidden com código estrutura e código função pois nesta página as combos com estas informações
		 estão desabilitadas e, por isso, não são submetidas */
%>
				<input type="hidden" name="codEtt" value="<%=selectedEstrutura%>">
				<input type="hidden" name="codAtb" value="<%=selectedAtributo%>">
				<input type="hidden" name="codFun" value="<%=selectedFuncao%>">

				<util:linkstop pesquisar="frm_pes.jsp" />
				<util:barrabotoes alterar="Gravar" voltar="Cancelar" />

				<%@ include file="form.jsp"%>

				<util:barrabotoes alterar="Gravar" voltar="Cancelar" />

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
} catch (Exception e) {
%>
<%@ include file="/excecao.jsp"%>
<%
}
%>
			</form>
		</div>
	</body>
</html>
