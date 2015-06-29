
<jsp:directive.page import="ecar.pojo.SisAtributoSatb"/>
<jsp:directive.page import="ecar.dao.AtributoDemandaDao"/><%@ include file="../../login/validaAcesso.jsp"%>
<%@ taglib uri="/WEB-INF/ecar-util.tld" prefix="util" %>
<%@ taglib uri="/WEB-INF/ecar-combo.tld" prefix="combo" %>
<%@ page import="ecar.pojo.SisGrupoAtributoSga" %>
<%@ page import="ecar.dao.SisGrupoAtributoDao" %>
<%@ include file="../../frm_global.jsp"%>
	

<%
boolean ehPesquisa = false;
String indTabelaUso = session.getAttribute("indTabelaUso").toString();
%>
 
<html lang="pt-br">
<head>
<%@ include file="../../include/meta.jsp"%>
<%@ include file="/titulo.jsp"%>
<link rel="stylesheet" href="<%=_pathEcar%>/css/style_<%=configuracaoCfg.getEstilo().getNome()%>.css" type="text/css">
<script language="javascript" src="../../js/menu_retratil.js"></script>
<script language="javascript" src="../../js/focoInicial.js"></script>
<script language="javascript" src="../../js/frmPadrao.js"></script>

<script language="javascript" src="../../js/numero.js"></script>
<script language="javascript" src="../../js/validacoes.js"></script>
<script language="javascript">

function validarAlterar(form){
	<%@ include file="validacao.jsp"%>
}
</script>

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
		SisGrupoAtributoDao grupoAtributoDao = new SisGrupoAtributoDao(request);
		AtributoDemandaDao atributoDemandaDao = new AtributoDemandaDao (request);
		SisGrupoAtributoSga grupoAtributo = (SisGrupoAtributoSga) grupoAtributoDao.buscar(SisGrupoAtributoSga.class, Long.valueOf(Pagina.getParamStr(request, "codigo")));
		_disabled = "";
		
%>

		<util:linkstop incluir="frm_inc.jsp" pesquisar="frm_pes.jsp"/>	
		<util:barrabotoes alterar="Gravar" voltar="Cancelar"/>
	
		<input type="hidden" name="codigo" value="<%=grupoAtributo.getCodSga()%>">

		<table class="form">
			<%@ include file="form.jsp"%>
		</table>
		
		<%List listaAtributoDemanda = (ArrayList) atributoDemandaDao.getAtributosDemandaAtivosPorGrupo(grupoAtributo);
		%>
		
		<util:barrabotoes alterar="Gravar" voltar="Cancelar"/>
		
		<script language="javascript">
			function aoClicarAlterar(form) {
				var indAtivo = document.getElementsByName("indAtivoSga");
				if(indAtivo[0].value == "N" && indAtivo[0].value != "<%=grupoAtributo.getIndAtivoSga()%>") {
					if(<%=listaAtributoDemanda.size()%> > 0) {
						if(!confirm('<%=_msg.getMensagem("demAtributo.alteracao.indAtivo")%>')){
							return false;
						}
					}	
				}
				
				if(validarAlterar(form)) {
					document.form.hidAcao.value = "alterar";
					document.form.action = "ctrl.jsp";
					document.form.submit();
				}	
				
		 	}
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
</html>