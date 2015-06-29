<%@ include file="/login/validaAcesso.jsp"%>

<%@ taglib uri="/WEB-INF/ecar-combo.tld" prefix="combo" %>
<%@ taglib uri="/WEB-INF/ecar-util.tld" prefix="util" %>
<%@ page import="ecar.pojo.EstruturaEtt" %>
<%@ page import="ecar.pojo.TipoFuncAcompTpfa" %>
<%@ page import="ecar.pojo.EstrutTpFuncAcmpEtttfa" %>
<%@ page import="ecar.dao.EstruturaTipoFuncAcompDao" %> 
<%@ page import="ecar.dao.EstruturaDao" %> 
<%@ page import="ecar.dao.TipoFuncAcompDao" %>
<%@ page import="java.util.List" %>
<%@ include file="/frm_global.jsp"%>

<html lang="pt-br">
<head>
<%@ include file="/include/meta.jsp"%>
<%@ include file="/titulo.jsp"%>
<link rel="stylesheet" href="<%=_pathEcar%>/css/style_<%=configuracaoCfg.getEstilo().getNome()%>.css" type="text/css">
<script language="javascript" src="<%=_pathEcar%>/js/menu_retratil.js" type="text/javascript"></script>
<script language="JavaScript" src="<%=_pathEcar%>/js/focoInicial.js" type="text/javascript"></script>
<script language="JavaScript" src="<%=_pathEcar%>/js/frmPadrao.js" type="text/javascript"></script>

</head>

<body>

<%@ include file="/cabecalho.jsp" %>
<%@ include file="/divmenu.jsp"%>

<div id="conteudo">

<form name="form" method="post" action="frm_nav.jsp">
<input type="hidden" name="hidAcao" value="">
	
	<!-- TITULO -->
	<%@ include file="/titulo_tela.jsp"%>

<%

boolean ehPesquisa=false;

try{
	EstrutTpFuncAcmpEtttfa estruturaTipoFuncAcomp = new EstrutTpFuncAcmpEtttfa();
	EstruturaTipoFuncAcompDao estruturaTipoFuncAcompDao = new EstruturaTipoFuncAcompDao(request);
	_disabled = "disabled";
	_readOnly = "readonly";

	List lista = (List) session.getAttribute("lista");

	List listaEstrutura = new EstruturaDao(request).listar(EstruturaEtt.class, new String[]{"nomeEtt", "asc"});
	List listaTipoFuncAcomp = new TipoFuncAcompDao(request).listar(TipoFuncAcompTpfa.class, new String[]{"labelTpfa", "asc"});
	
	int i = 0;
	int hidTotRegistro = lista.size(); //Total de Registros
	int hidRegistro = Pagina.getParamInt(request, "hidRegistro"); //Registro atual
	
	//Se o último registro for excluído deve apontar para a última posição.
	if (hidRegistro >= hidTotRegistro)
		hidRegistro = hidTotRegistro - 1;
		
	estruturaTipoFuncAcomp = (EstrutTpFuncAcmpEtttfa) lista.get(hidRegistro);	
	
	String selectedEstrutura = estruturaTipoFuncAcomp.getEstruturaEtt().getCodEtt().toString();
	String selectedTipoFuncAcomp = estruturaTipoFuncAcomp.getTipoFuncAcompTpfa().getCodTpfa().toString();
	String _pesqdisabled = "disabled";
%>
		<util:linkstop  pesquisar="frm_pes.jsp"/>
		<util:barrabotoes navegacao="true" btn1="Alterar" atual="<%=hidRegistro+1%>" total="<%=hidTotRegistro%>"/>
			
		<input type="hidden" name="hidRegistro" value="<%=hidRegistro%>">		
		<input type="hidden" name="hidTotRegistro" value="<%=hidTotRegistro%>">

		<%/* campos hidden com código estrutura e código função pois nesta página as combos com estas informações
				estão desabilitadas e, por isso, não são submetidas */
		%>
		<input type="hidden" name="estruturaEtt" value="<%=selectedEstrutura%>">
		<input type="hidden" name="tipoFuncaoAcompTpfa" value="<%=selectedTipoFuncAcomp%>">		
				
		<%@ include file="form.jsp"%>
		
		<util:barrabotoes navegacao="true" btn1="Alterar" atual="<%=hidRegistro+1%>" total="<%=hidTotRegistro%>"/>

<%
} catch (Exception e) {
	org.apache.log4j.Logger.getLogger(this.getClass()).error(e);
	Mensagem.alert(_jspx_page_context, _msg.getMensagem("erro.exception"));
		%>
		<script language="javascript" type="text/javascript">
		document.form.action = "frm_pes.jsp";
		document.form.submit();
		</script>
		<%
}
%>
</form>
</div>
</body>
<%@ include file="/include/mensagemAlert.jsp"%>
</html>