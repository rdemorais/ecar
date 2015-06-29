<%@ include file="../../../login/validaAcesso.jsp"%>
<%@ include file="../../../frm_global.jsp"%>

<%@ page import="ecar.pojo.ItemEstruturaIett" %>
<%@ page import="ecar.dao.ItemEstruturaDao" %>
<%@ page import="ecar.pojo.EstruturaFuncaoEttf" %>
<%@ page import="ecar.pojo.EstruturaFuncaoEttfPK" %>
<%@ page import="ecar.pojo.IettIndResulRevIettrr" %>
<%@ page import="ecar.pojo.ItemEstFisicoRevIettfr" %>
<%@ page import="ecar.dao.IettIndResulRevIettrrDAO" %>
<%@ page import="ecar.dao.ItemEstruturarevisaoIettrevDAO" %>
<%@ page import="ecar.pojo.ItemEstruturarevisaoIettrev" %>
<%@ page import="ecar.dao.EstruturaFuncaoDao" %>
<%@ page import="ecar.pojo.ExercicioExe" %>
<%@ page import="ecar.dao.ExercicioDao" %>
<%@ page import="ecar.dao.ItemEstFisicoRevIettfrDAO" %>

<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.Iterator" %>
<%@ page import="ecar.pojo.ItemEstrtIndResulIettr" %>

<%@ taglib uri="/WEB-INF/ecar-util.tld" prefix="util" %>

<html lang="pt-br">
<head>
<%@ include file="/include/meta.jsp"%>
<link rel="stylesheet" href="<%=_pathEcar%>/css/style_<%=new ecar.dao.ConfiguracaoDao(request).getConfiguracao().getEstilo().getNome()%>.css" type="text/css">
<script language="javascript" src="../../../js/focoInicial.js"></script>

<script language="javascript" src="../../../js/numero.js"></script>
<script language="javascript" src="../../js/menu_retratil.js"></script>
<script language="javascript" src="../../../js/validacoes.js"></script>
<script language="javascript">
<%@ include file="validacao.jsp"%>

function aoClicarVoltar(form){
	window.close();
}
function aoClicarCancelar(form){
	form.reset();
}

function aoClicarAlterar(form){
	if(validar(form)){
		form.btnAlterar[0].disabled = true;
		form.btnAlterar[1].disabled = true;
		form.hidAcao.value = "alterar";
		form.action = "ctrl.jsp";
		form.submit();
	}
}	

function aoClicarGravar(form){
	if(validar(form)){
		form.btnGravar[0].disabled = true;
		form.btnGravar[1].disabled = true;
		form.hidAcao.value = "incluir";
		form.action = "ctrl.jsp"
		form.submit();
		window.opener.aoClicarBtn2(window.opener.document.form);
		window.close();		
	}
}
</script>

</head>

<body onload="focoInicial(document.form);">

<%
try{
	ItemEstruturaDao itemEstruturaDao = new ItemEstruturaDao(request);
	ItemEstruturaIett itemEstrutura = (ItemEstruturaIett) itemEstruturaDao.buscar(ItemEstruturaIett.class, Long.valueOf(Pagina.getParamStr(request, "codIett")));
	ItemEstruturarevisaoIettrevDAO itemEstruturaRevDao = new ItemEstruturarevisaoIettrevDAO(request);
	ItemEstruturarevisaoIettrev itemEstruturaRev = (ItemEstruturarevisaoIettrev) itemEstruturaRevDao.buscar(ItemEstruturarevisaoIettrev.class, Integer.valueOf(Pagina.getParamStr(request, "codIettrev")));
	
	String codAbaMeta = Pagina.getParamStr(request, "codAbaMeta");
	String codAba = Pagina.getParamStr(request, "codAba");
	EstruturaFuncaoEttf estruturaFuncao = new EstruturaFuncaoEttf();
	EstruturaFuncaoDao estruturaFuncaoDao = new EstruturaFuncaoDao(request);
	
	estruturaFuncao = (EstruturaFuncaoEttf) estruturaFuncaoDao.getLabelFuncao(itemEstrutura.getEstruturaEtt(), Long.valueOf(codAbaMeta));

	List indicadoresIett = new ArrayList(itemEstrutura.getItemEstrtIndResulIettrs());
	
%>
<!-- TITULO -->


<form name="form" method="post">
	<input type=hidden name="hidAcao" value="incluir">
	<input type="hidden" name="inseriuMeta" value="S">
	<util:barrabotoes incluir="Gravar" voltar="Cancelar"/>

<%
	IettIndResulRevIettrr itemEstrtIndResul = new IettIndResulRevIettrr();
	_disabled = "";
%>

	<table class="form">
		<%@ include file="form.jsp"%>
	</table>

	<util:barrabotoes incluir="Gravar" voltar="Cancelar"/>
	
</form>


<%
} catch (ECARException e){
	org.apache.log4j.Logger.getLogger(this.getClass()).error(e);
	Mensagem.alert(_jspx_page_context, e.getMessageKey());
} catch (Exception e){
%>
	<%@ include file="/excecao.jsp"%>
<%
}
%>

</div>

</body>
<%@ include file="../../../include/mensagemAlert.jsp"%>
</html>