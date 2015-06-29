<%@ include file="../../login/validaAcesso.jsp"%>
<%@ taglib uri="/WEB-INF/ecar-util.tld" prefix="util" %>
<%@ taglib uri="/WEB-INF/ecar-combo.tld" prefix="combo" %>
<%@ page import="ecar.pojo.SisAtributoSatb" %>
<%@ page import="ecar.dao.SisAtributoDao" %>
<%@ page import="ecar.pojo.SitDemandaSitd" %>
<%@ page import="ecar.dao.SitDemandaDao" %>
<%@ include file="../../frm_global.jsp"%>


<html lang="pt-br">
<head>
<%@ include file="../../include/meta.jsp"%>
<%@ include file="/titulo.jsp"%>
<link rel="stylesheet" href="<%=_pathEcar%>/css/style_<%=configuracaoCfg.getEstilo().getNome()%>.css" type="text/css">
<script language="javascript" src="../../js/menu_retratil.js"></script>
<script language="javascript" src="../../js/focoInicial.js"></script>
<script language="javascript" src="../../js/frmPadrao.js"></script>

<script language="javascript" src="../../js/validacoes.js"></script>
<script language="javascript">

function validarAlterar(form){
	if(!validaString(form.descricaoSitd,'Descrição',true)){
		return(false);
	}
	
	if(form.indConclusaoSitd.value == "S" && form.indPrimeiraSituacaoSitd.value == "S"){
		alert("Pelo menos um dos índices precisa ser Não!");
		form.indConclusaoSitd.focus();
		return(false);
	}
			
	return(true);
}
</script>
<%String indTabelaUso = session.getAttribute("indTabelaUso").toString();%>
<title>
	<%=_msg.getMensagem("atributo.titulo."+indTabelaUso)%>
</title>

</head>

<!-- sempre colocar o foco inicial no primeiro campo -->
<body onload="javascript:onLoad(document.form);">

<%@ include file="../../cabecalho.jsp" %>
<%@ include file="../../divmenu.jsp"%>

<div id="conteudo">

<form name="form" method="post">
	<input type="hidden" name="hidAcao" value="">
	<input type="hidden" name="hidRegistro" value="<%=Pagina.getParamInt(request, "hidRegistro")%>">
	
	
	<!-- TITULO -->
	<%@ include file="/titulo_tela.jsp"%>
	
<%	
	try {
		SitDemandaDao situacaoDao = new SitDemandaDao(request);
		SitDemandaSitd situacao = new SitDemandaSitd();
		
		if(session.getAttribute("objSituacao") != null){
			situacao = (SitDemandaSitd) session.getAttribute("objSituacao");
			session.removeAttribute("objSituacao");
		}else{
			situacao = (SitDemandaSitd) situacaoDao.buscar(SitDemandaSitd.class, Long.valueOf(Pagina.getParam(request, "codigo")));
	  	}
		
		_disabled = "";
	%>
	<util:linkstop incluir="frm_inc.jsp" pesquisar="frm_pes.jsp"/>	
	<util:barrabotoes alterar="Gravar" voltar="Cancelar"/>
	
		<input type="hidden" name="codigo" value="<%=situacao.getCodSitd()%>">

		<table class="form">
			<%@ include file="form.jsp"%>
		</table>
	
		<util:barrabotoes alterar="Gravar" voltar="Cancelar"/>

<%
	} catch (ECARException e) {
		org.apache.log4j.Logger.getLogger(this.getClass()).error(e);
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
<%@ include file="../../include/mensagemAlert.jsp"%>
</html>