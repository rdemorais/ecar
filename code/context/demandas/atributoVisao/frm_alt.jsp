<%@ include file="/login/validaAcesso.jsp"%>
<%@ include file="/frm_global.jsp"%>

<%@ taglib uri="/WEB-INF/ecar-util.tld" prefix="util"%>
<%@ taglib uri="/WEB-INF/ecar-combo.tld" prefix="combo"%>

<%@ page import="ecar.pojo.AtributoDemandaAtbdem"%>
<%@ page import="ecar.dao.AtributoDemandaDao"%>
<%

boolean ehPesquisa = false;
boolean ehIncluir = false;

try {

		VisaoAtributoDemandaDao atributoDemandaDaoAlt = new VisaoAtributoDemandaDao(request);
		VisaoAtributoDemanda visaoAtributoDemanda = new VisaoAtributoDemanda();
		VisaoAtributoDemandaPK visaoAtributoDemandaPK = new VisaoAtributoDemandaPK();
		VisaoDemandasVisDem visao = null;
		AtributoDemandaAtbdem atributoDemanda = null;
		
		if(session.getAttribute("objAtributoVisao") != null){
			visaoAtributoDemanda = (VisaoAtributoDemanda) session.getAttribute("objAtributoVisao");
			session.removeAttribute("objAtributoVisao");
		}else{
			
			atributoDemanda = new AtributoDemandaAtbdem();
			atributoDemanda.setCodAtbdem(Long.valueOf(Pagina.getParam(request, "codigoAtributoDemanda")));
			visao = new VisaoDemandasVisDem();
			visao.setCodVisao(Long.valueOf(Pagina.getParam(request, "codigoVisao")));
			visaoAtributoDemandaPK.setAtributoDemanda(atributoDemanda);
			visaoAtributoDemandaPK.setVisao(visao);
			
			visaoAtributoDemanda = (VisaoAtributoDemanda) atributoDemandaDaoAlt.buscar(VisaoAtributoDemanda.class, visaoAtributoDemandaPK);
	  	}
		
		_disabled = "";
		String strReadOnly = "false";
		boolean blVisualizaDesc = true;
		String _pesqdisabled = "";
%>

<%@page import="ecar.dao.VisaoAtributoDemandaDao"%>
<%@page import="ecar.pojo.VisaoAtributoDemandaPK"%>
<%@page import="ecar.pojo.VisaoDemandasVisDem"%>
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
function validarAlterar(form){
	return validar(form);
}
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
				<input type="hidden" name="codigoAtributoDemanda" value="<%=visaoAtributoDemanda.getVisaoAtributoDemandaPk().getAtributoDemanda().getCodAtbdem().toString()%>">
				<input type="hidden" name="codigoVisao" value="<%=visaoAtributoDemanda.getVisaoAtributoDemandaPk().getVisao().getCodVisao().toString()%>">

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