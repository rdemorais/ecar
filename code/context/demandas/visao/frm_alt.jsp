<%@ include file="/login/validaAcesso.jsp"%>
<%@ include file="/frm_global.jsp"%>

<%@ taglib uri="/WEB-INF/ecar-util.tld" prefix="util"%>
<%@ taglib uri="/WEB-INF/ecar-combo.tld" prefix="combo"%>

<%@ page import="ecar.pojo.VisaoDemandasVisDem"%>
<%@ page import="ecar.dao.VisaoDao"%>
<%

boolean ehPesquisa = false;
boolean ehIncluir = false;

try {

		VisaoDao visaoDaoAlteracao = new VisaoDao(request);
		VisaoDemandasVisDem visaoDemandas;
		
		if(session.getAttribute("objAtributoVisao") != null){
			visaoDemandas = (VisaoDemandasVisDem) visaoDaoAlteracao.buscar(VisaoDemandasVisDem.class, ((VisaoDemandasVisDem) session.getAttribute("objAtributoVisao")).getCodVisao());
			session.removeAttribute("objAtributoVisao");
		}else{
			visaoDemandas = (VisaoDemandasVisDem) visaoDaoAlteracao.buscar(VisaoDemandasVisDem.class, Long.valueOf(Pagina.getParamLong(request, "codigo")));
	  	} 
		
		_disabled = "";
		String strReadOnly = "false";
		boolean blVisualizaDesc = true;
		String _pesqdisabled = "";
%>
<html lang="pt-br">
	<head>
		<%@ include file="/include/meta.jsp"%>
		<%@ include file="/titulo.jsp"%>
		<link href="<%=_pathEcar%>/css/style.css" rel="stylesheet" type="text/css">
		<script language="javascript" src="<%=_pathEcar%>/js/menu_retratil.js" type="text/javascript"></script>
		<script language="javascript" src="<%=_pathEcar%>/js/focoInicial.js" type="text/javascript"></script>
		<script language="javascript" src="<%=_pathEcar%>/js/frmPadrao.js" type="text/javascript"></script>
		<script language="javascript" src="<%=_pathEcar%>/js/validacoes.js" type="text/javascript"></script>
		<script language="javascript" src="<%=_pathEcar%>/js/richtext.js" type="text/javascript"></script>
		<%@ include file="validacao.jsp" %>
<script language="javascript" type="text/javascript">
</script>		

	</head>
	<!-- sempre colocar o foco inicial no primeiro campo -->
	<body onload="javascript:onLoad(document.form);">
		<%@ include file="/cabecalho.jsp"%>
		<%@ include file="/divmenu.jsp"%>
		<div id="conteudo">

			<form name="form" method="post" action="">
				<input type="hidden" name="hidAcao" value="alterar">
				<input type="hidden" name="hidRegistro"	value='<%=Pagina.getParamInt(request, "hidRegistro")%>'>
				<input type="hidden" name="codigo" value="<%=visaoDemandas.getCodVisao()%>">

				<!-- TITULO -->
				<%@ include file="/titulo_tela.jsp"%>


				<util:linkstop incluir="frm_inc.jsp" pesquisar="frm_pes.jsp" />
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
	} catch (Exception e){
%>
<%@ include file="/excecao.jsp"%>
<%
}
%>
			</form>
		</div>
	</body>
	<%@ include file="/include/mensagemAlert.jsp"%>
</html>