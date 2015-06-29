<%@ taglib uri="/WEB-INF/ecar-util.tld" prefix="util" %>
<%@ taglib uri="/WEB-INF/ecar-combo.tld" prefix="combo" %>
<%@ taglib uri="/WEB-INF/ecar-input.tld" prefix="ecar" %>
<%@ include file="../../login/validaAcesso.jsp"%>
<%@ include file="../../frm_global.jsp"%>
<%@ page import="ecar.pojo.EntidadeEnt" %>
<%@ page import="ecar.pojo.SisGrupoAtributoSga" %>
<%@ page import="ecar.pojo.SisAtributoSatb" %>
<%@ page import="ecar.dao.EntidadeDao" %>
<%@ page import="ecar.pojo.LocalItemLit" %>
<%@ page import="java.util.List" %>
<%@ page import="ecar.dao.SisGrupoAtributoDao" %>
<%@ page import="java.util.HashSet"%>
<%@ page import="ecar.pojo.EnderecoEnd"%>
<%@ page import="ecar.dao.TipoEnderecoDao"%>
<%@ page import="ecar.dao.UfDao"%>
<%@ page import="java.util.Set"%>
<%@ page import="java.util.Iterator"%>
<%@ page import="ecar.pojo.TelefoneTel" %>

<html lang="pt-br">
<head>
<%@ include file="../../include/meta.jsp"%>
<%@ include file="/titulo.jsp"%>
<link rel="stylesheet" href="<%=_pathEcar%>/css/style_<%=configuracaoCfg.getEstilo().getNome()%>.css" type="text/css">
<script language="javascript" src="../../js/menu_retratil.js"></script>
<script language="JavaScript" src="../../js/focoInicial.js"></script>
<script language="JavaScript" src="../../js/frmPadrao.js"></script>


<script>

<%
	TipoEnderecoDao tpEndDao = new TipoEnderecoDao(request);
	out.println(tpEndDao.getTipoEnderecoJS());
	UfDao ufDao = new UfDao(request);
	out.println(ufDao.getUfJS());
%>

</script>
<%
	_disabled = "disabled";
	_readOnly = "readonly";
%>

<%@ include file="funcoes.jsp"%>

</head>

<%@ include file="../../cabecalho.jsp" %>
<%@ include file="../../divmenu.jsp"%>

<body>

<div id="conteudo">

<form name="form" method="post" action="frm_nav.jsp">
	<input type="hidden" name="hidAcao" value="">
	
	<!-- TITULO -->
	<%@ include file="/titulo_tela.jsp"%>

<%
	try {
		EntidadeEnt entidade = new EntidadeEnt();
		EntidadeDao entidadeDao = new EntidadeDao(request);
		boolean pesquisa = false;		
		boolean navega = true;		
		
		List lista = (List) session.getAttribute("lista");
		
		int i = 0;
		int hidTotRegistro = lista.size(); //Total de Registros
		int hidRegistro = Pagina.getParamInt(request, "hidRegistro"); //Registro atual
		
		//Se o último registro for excluído deve apontar para a última posição.
		if (hidRegistro >= hidTotRegistro)
			hidRegistro = hidTotRegistro - 1;
		
		entidade = (EntidadeEnt) lista.get(hidRegistro);
%>

		<input type="hidden" name="hidRegistro" value="<%=hidRegistro%>">
		<input type="hidden" name="hidTotRegistro" value="<%=hidTotRegistro%>">
		<input type="hidden" name="codigo" value="<%=entidade.getCodEnt()%>">
		
		<util:linkstop incluir="frm_inc.jsp" pesquisar="frm_pes.jsp"/>
		<util:barrabotoes navegacao="true" btn1="Alterar" excluir="Excluir" atual="<%=hidRegistro+1%>" total="<%=hidTotRegistro%>"/>
	
		<table class="form">
		<%@ include file="form.jsp"%>
		</table>
		<util:barrabotoes navegacao="true" btn1="Alterar" excluir="Excluir"/>
<%
	} catch (Exception e) {
		org.apache.log4j.Logger.getLogger(this.getClass()).error(e);
		Mensagem.alert(_jspx_page_context, _msg.getMensagem("erro.exception"));
		%>
		<script language="javascript">
		document.form.action = "frm_pes.jsp";
		document.form.submit();
		</script>
		<%
	}
%>
	
</form>
</div>
</body>
<%@ include file="../../include/mensagemAlert.jsp"%>
</html>