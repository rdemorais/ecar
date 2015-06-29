<%@ include file="../../login/validaAcesso.jsp"%>

<%@ taglib uri="/WEB-INF/ecar-util.tld" prefix="util" %>
<%@ include file="../../frm_global.jsp"%>
<%@ page import="ecar.pojo.Uf" %>
<%@ page import="ecar.dao.UfDao" %>


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
	if(!validaString(form.codUf, "UF", true)){
		return(false);
	}
	if(!validaString(form.descricaoUf, "Descrição", true)){
		return(false);
	}
	if (!isValidString(form.codUf.value)){
		alert("Código da UF inválido. Entre somente com caracteres válidos");
		document.form.codUf.select();
		document.form.codUf.focus();
		return(false);
	}
	return(true);
}
</script>

</head>

<%@ include file="../../cabecalho.jsp" %>
<%@ include file="../../divmenu.jsp"%>

<!-- sempre colocar o foco inicial no primeiro campo -->
<body>

<div id="conteudo">

<form name="form" method="post">
	<input type="hidden" name="hidAcao" value="alterar">
	<input type="hidden" name="hidRegistro" value="<%=Pagina.getParamInt(request, "hidRegistro")%>">
	
	
	<!-- TITULO -->
	<%@ include file="/titulo_tela.jsp"%>
<%
	try {
		UfDao ufDao = new UfDao(request);
		Uf uf;
		
		if(session.getAttribute("objUf") != null){
			uf = (Uf) session.getAttribute("objUf");
			session.removeAttribute("objUf");
		}else{
			uf = (Uf) ufDao.buscar(Uf.class, new String(Pagina.getParamStr(request, "codigo")));
	  	}
		
		_disabled = "";
%>

		<util:linkstop incluir="frm_inc.jsp" pesquisar="frm_pes.jsp"/>
		<util:barrabotoes alterar="Gravar" voltar="Cancelar"/>

		<input type="hidden" name="codigo" value="<%=uf.getCodUf()%>">

		<table class="form">
		<%@ include file="form.jsp"%>
		</table>

		<util:barrabotoes alterar="Gravar" voltar="Cancelar"/>

		<!-- desabilitar o campo codUf, pois é chave e não pode ser alterado -->
		<script language="javascript">
			document.form.codUf.disabled=true;
			document.form.descricaoUf.focus();
		</script>
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