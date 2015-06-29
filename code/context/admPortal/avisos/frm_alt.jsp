<%@ page import="ecar.pojo.PopupPpp" %>
<%@ page import="ecar.dao.PopUpDao" %>

<%@ page import="java.util.List" %>

<%@ taglib uri="/WEB-INF/ecar-combo.tld" prefix="combo" %>
<%@ taglib uri="/WEB-INF/ecar-util.tld" prefix="util" %>

<%@ include file="../../login/validaAcesso.jsp"%>
<%@ include file="../../frm_global.jsp"%>

<%
int hidRichText;
	hidRichText = Pagina.getParamInt(request, "hidRichText");
%> 

<html lang="pt-br">
<head>
<%@ include file="../../include/meta.jsp"%>
<%@ include file="/titulo.jsp"%>
<link rel="stylesheet" href="<%=_pathEcar%>/css/style_<%=configuracaoCfg.getEstilo().getNome()%>.css" type="text/css">
<script language="javascript" src="../../js/menu_retratil.js"></script>
<script language="javascript" src="../../js/focoInicial.js"></script>
<script language="javascript" src="../../js/frmPadraoAdmPortal.js"></script>
<script language="javascript" src="../../js/validacoes.js"></script>
<script language="javascript" src="../../js/datas.js"></script>
<script language="javascript" src="../../js/numero.js"></script>
<script language="javascript" src="../../js/popUp.js"></script>
<script language="javaScript" src="../../js/html2xhtml.js"></script>
<script language="javaScript" src="../../js/richtext.js"></script>

<script language="javascript">
<%@ include file="validacao.jsp"%>

function aoClicarBtn1(form){
	if(validar(form)){
		var url = "../../popUp/popUpAvisoPadrao.jsp?visualizacao=true&texto="+escape(form.conteudoPpp.value)+"&altura="+escape(form.janelaAlturaPpp.value)+"&largura="+escape(form.janelaLarguraPpp.value);
		abreJanela(url, form.janelaLarguraPpp.value, form.janelaAlturaPpp.value, "Visualização");		
	}
}

</script>

</head>

<!-- sempre colocar o foco inicial no primeiro campo -->
<body onload="javascript:onLoad(document.form);">

<%@ include file="../../cabecalho.jsp" %>
<%@ include file="../../divmenu.jsp"%>

<div id="conteudo">

	<%
	String linkIncluir = "frm_inc.jsp";
	String linkPesquisar = "frm_pes.jsp?hidRichText=1";
	
	try{
		PopUpDao popUpDao = new PopUpDao(request);
		PopupPpp popUp;
		
		if(session.getAttribute("objPopup") != null){
			popUp = (PopupPpp) session.getAttribute("objPopup");
			session.removeAttribute("objPopup");
		}else{
			popUp = (PopupPpp) popUpDao.buscar(PopupPpp.class, Long.valueOf(Pagina.getParamStr(request, "codigo")));
	  	}
		
		_disabled = "";
%>

	<form name="form" method="post">
		<input type="hidden" name="hidAcao" value="alterar">
		<input type="hidden" name="hidRegistro" value="<%=Pagina.getParamStr(request, "hidRegistro")%>">
		<input type="hidden" name="hidRichText" value="<%=hidRichText%>">	
				
		<!-- TITULO -->
		<%@ include file="/titulo_tela.jsp"%>
	
		<util:linkstop incluir="<%=linkIncluir%>" pesquisar="<%=linkPesquisar%>"/>
		<util:barrabotoes alterar="Gravar" voltar="Cancelar" btn1="Visualizar"/>


		<input type="hidden" name="codigo" value="<%=popUp.getCodPpp()%>">
	
		<table class="form">  
		<%@ include file="form.jsp"%>
		</table>
	
		<util:barrabotoes alterar="Gravar" voltar="Cancelar" btn1="Visualizar"/>
<%	
	}catch(ECARException e){
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