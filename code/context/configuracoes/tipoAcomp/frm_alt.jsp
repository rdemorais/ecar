
<jsp:directive.page import="org.apache.log4j.Logger"/>
<%@ include file="/login/validaAcesso.jsp"%>
<%@ include file="/frm_global.jsp"%>

<%@ taglib uri="/WEB-INF/ecar-util.tld" prefix="util" %>
<%@ taglib uri="/WEB-INF/ecar-combo.tld" prefix="combo" %>

<%@ page import="ecar.pojo.TipoAcompanhamentoTa" %>
<%@ page import="ecar.dao.TipoAcompanhamentoDao" %>

<%@ page import="java.util.List" %>
<%@ page import="java.util.Iterator" %>



<html lang="pt-br">
<head>

<%@ include file="/include/meta.jsp"%>
<%@ include file="/titulo.jsp"%>

<link rel="stylesheet" href="<%=_pathEcar%>/css/style_<%=configuracaoCfg.getEstilo().getNome()%>.css" type="text/css">

<script language="javascript" src="<%=_pathEcar%>/js/menu_retratil.js"></script>
<script language="javascript" src="<%=_pathEcar%>/js/focoInicial.js"></script>
<script language="javascript" src="<%=_pathEcar%>/js/frmPadrao.js"></script>

<script language="javascript" src="<%=_pathEcar%>/js/validacoes.js"></script>
<script language="javascript" src="<%=_pathEcar%>/js/numero.js"></script>
<%@ include file="validacao.jsp" %>
</head>

 
<%@ include file="/cabecalho.jsp" %>
<%@ include file="/divmenu.jsp"%>

<%
	boolean ehPesquisa = false;
	boolean ehIncluir = false; 
%>

<div id="conteudo">

<form name="form" method="post">
	<input type="hidden" name="hidAcao" value="alterar">
	<input type="hidden" name="hidRegistro" value="<%=Pagina.getParamInt(request, "hidRegistro")%>">
	
	<!-- TITULO -->
	<%@ include file="/titulo_tela.jsp"%>
<%	
	try {
		TipoAcompanhamentoDao tipoAcompDao = new TipoAcompanhamentoDao(request);
		TipoAcompanhamentoTa tipoAcomp = (TipoAcompanhamentoTa) tipoAcompDao.buscar(TipoAcompanhamentoTa.class, Long.valueOf(Pagina.getParamLong(request, "codigo")));
	
		_disabled = "";
		String _pesqdisabled = "";
		
		String _disabledOrgao = _pesqdisabled;
		
		if (tipoAcomp != null && tipoAcomp.getAcompReferenciaArefs() != null && tipoAcomp.getAcompReferenciaArefs().size() > 0){
			_disabledOrgao = "disabled";
		}
%>

		<util:linkstop incluir="frm_inc.jsp" pesquisar="frm_pes.jsp"/>
		<util:barrabotoes alterar="Gravar" voltar="Cancelar"/>
		<%@ include file="form.jsp"%>
		<util:barrabotoes alterar="Gravar" voltar="Cancelar"/>
<%
	} catch (ECARException e) {
		Logger.getLogger(this.getClass()).error(e);
		/* redireciona para o controle */
		%>
		<script language="javascript">
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