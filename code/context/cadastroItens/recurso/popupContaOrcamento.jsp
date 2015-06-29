
<%@ include file="/login/validaAcesso.jsp"%>
<%@ include file="/frm_global.jsp"%>
	
<%@ page import="ecar.pojo.ItemEstruturaIett" %>
<%@ page import="ecar.pojo.EstruturaFuncaoEttf" %>
<%@ page import="ecar.dao.ItemEstruturaDao" %>
<%@ page import="ecar.dao.EstruturaFuncaoDao" %>

<%@ page import="ecar.dao.ExercicioDao" %>
<%@ page import="java.util.List" %>
<%@ page import="ecar.dao.FonteRecursoDao" %>
<%@ page import="ecar.dao.RecursoDao" %>
<%@ page import="ecar.pojo.FonteRecursoFonr" %>
<%@ page import="ecar.pojo.RecursoRec" %>
<%@ page import="ecar.dao.ItemEstruturaContaOrcamentoDao" %>
 
<%@ taglib uri="/WEB-INF/ecar-util.tld" prefix="util" %>
<%@ taglib uri="/WEB-INF/ecar-combo.tld" prefix="combo" %>
 
<html lang="pt-br">
<head>

<%@ include file="/include/meta.jsp"%>
<%@ include file="/titulo.jsp"%>

<link rel="stylesheet" href="<%=_pathEcar%>/css/style_<%=configuracaoCfg.getEstilo().getNome()%>.css" type="text/css">

<script language="javascript" src="../../js/menu_retratil.js"></script>
<script language="javascript" src="../../js/focoInicial.js"></script>
<script language="javascript" src="../../js/popUp.js"></script> 
<script language="javascript" src="../../js/validacoes.js"></script>
<script language="javascript" src="../../js/datas.js"></script>
<script language="javascript" src="../../js/numero.js"></script>

<script language="javascript">
function aoClicarGravar(form){
	if(validar(form)){
		form.hidAcao.value = "incluir";
		form.action = "ctrl_popupContaOrcamento.jsp";
		form.submit();
	}
}

function aoClicarVoltar(form){
	window.close();
}	

function validar(form){
	if(validarCheckSelecionado(form, "exercicios") == false){
		alert("É necessário informar ao menos um exercício.");
		return false;
	}

	//Validação para o campo Conta
	<%=ecar.dao.ItemEstruturaContaOrcamentoDao.geraValidacaoCadastroEstruturaConta(request)%>	
	
	return true;
}
</script>

</head>

<body onload="focoInicial(document.form);">

<%
try{
	String codIett = Pagina.getParamStr(request, "codIett");
	String codFonr = Pagina.getParamStr(request, "codFonr");
	String codRec = Pagina.getParamStr(request, "codRec");
	String codAba = Pagina.getParamStr(request, "codAba");
	
	ExercicioDao exercicioDao = new ExercicioDao(request);
	List exercicios = exercicioDao.getExerciciosValidos(Long.valueOf(codIett));
	
	FonteRecursoDao fonrDao = new FonteRecursoDao(request);
	RecursoDao recDao = new RecursoDao(request);
	
	FonteRecursoFonr recurso = (FonteRecursoFonr) fonrDao.buscar(FonteRecursoFonr.class, Long.valueOf(codFonr));
	RecursoRec fonte = (RecursoRec) recDao.buscar(RecursoRec.class, Long.valueOf(codRec));
	
	ItemEstruturaDao itemEstruturaDao = new ItemEstruturaDao(request);
	ItemEstruturaIett itemEstrutura = (ItemEstruturaIett) itemEstruturaDao.buscar(ItemEstruturaIett.class, Long.valueOf(Pagina.getParamStr(request, "codIett")));
	
	EstruturaFuncaoEttf estruturaFuncao = new EstruturaFuncaoEttf();
	EstruturaFuncaoDao estruturaFuncaoDao = new EstruturaFuncaoDao(request);
	
	estruturaFuncao = (EstruturaFuncaoEttf) estruturaFuncaoDao.getLabelFuncao(itemEstrutura.getEstruturaEtt(), Long.valueOf(codAba)); 
%>

<h1>Cadastramento de <%=estruturaFuncaoDao.getLabelFuncaoContasOrcamento(estruturaFuncao.getEstruturaEtt()).toString() %> </h1>

<form name="form" method="post">

	<input type=hidden name="hidAcao" value="incluir">
	<!-- Hiddens para controle das páginas... -->
	<input type="hidden" name="codIett" value="<%=codIett%>">
	<input type="hidden" name="codFonr" value="<%=codFonr%>">
	<input type="hidden" name="codRec" value="<%=codRec%>">
	<input type="hidden" name="codAba" value="<%=Pagina.getParamStr(request, "codAba")%>">

	<util:barrabotoes incluir="Gravar" voltar="Cancelar"/>
	<table class="form">
		<tr>
			<td class="label"><%=estruturaFuncaoDao.getLabelFuncaoFonteRecurso(estruturaFuncao.getEstruturaEtt()).toString()%></td>
			<td><%=recurso.getNomeFonr()%></td>
		</tr>
		<tr>
			<td class="label"><%=estruturaFuncaoDao.getLabelFuncaoRecurso(estruturaFuncao.getEstruturaEtt()).toString()%></td>
			<td><%=fonte.getNomeRec()%></td>
		</tr>
		<tr>
			<td class="label" valign="top">Exercício</td>
			<td>
				<combo:CheckListTag 
					nome="exercicios" 
					objeto="ecar.pojo.ExercicioExe" 
					label="descricaoExe" 
					value="codExe"
					colecao="<%=exercicios%>"
				/>
			</td>
		</tr>
		<tr>
			<td class="label">Acumulado</td>
			<td>
				<input type="radio" class="form_check_radio" name="indAcumuladoEfiec" value="S">Sim
				<input type="radio" class="form_check_radio" name="indAcumuladoEfiec" value="N" checked>Não
			</td>
		</tr>
		<tr>
			<td class="label">Estrutura</td>
			<td><%=new ItemEstruturaContaOrcamentoDao(request).geraHTMLCadastroEstruturaConta("",false, request)%></td>
		</tr>
	</table>

	<util:barrabotoes incluir="Gravar" voltar="Cancelar"/>
</form>
<%
} catch (Exception e){
%>
	<%@ include file="/excecao.jsp"%>
<%
}
%>

</body>

<%@ include file="/include/mensagemAlert.jsp" %>
</html>