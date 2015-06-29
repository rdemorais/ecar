<%@ include file="/login/validaAcesso.jsp"%>
<%@ include file="/frm_global.jsp"%>

<jsp:directive.page import="ecar.pojo.EfItemEstContaEfiec"/>
<jsp:directive.page import="java.util.ArrayList"/>
<%@ page import="ecar.dao.ExercicioDao" %>
<%@ page import="java.util.List" %>
<%@ page import="ecar.dao.FonteRecursoDao" %>
<%@ page import="ecar.dao.RecursoDao" %>
<%@ page import="ecar.pojo.FonteRecursoFonr" %>
<%@ page import="ecar.pojo.RecursoRec" %>
<%@ page import="ecar.dao.ItemEstruturaContaOrcamentoDao" %>
<%@ page import="ecar.util.Dominios" %>
 
<%@ taglib uri="/WEB-INF/ecar-util.tld" prefix="util" %>
<%@ taglib uri="/WEB-INF/ecar-combo.tld" prefix="combo" %>
 
<html lang="pt-br">
<head>

<%@ include file="/include/meta.jsp"%>
<%@ include file="/titulo.jsp"%>

<link rel="stylesheet" href="<%=_pathEcar%>/css/style_<%=configuracaoCfg.getEstilo().getNome()%>.css" type="text/css">

</head>

<body>

<%
String msg = "";
String submit = "popupContaOrcamento.jsp";

try{
	
	String codIett = Pagina.getParamStr(request, "codIett");
	String codFonr = Pagina.getParamStr(request, "codFonr");
	String codRec = Pagina.getParamStr(request, "codRec");
	String codAba = Pagina.getParamStr(request, "codAba");
	
	String exercicios[] = request.getParameterValues("exercicios");
	
	ItemEstruturaContaOrcamentoDao itemEstruturaContaOrcamentoDao = new ItemEstruturaContaOrcamentoDao(request);
%>
<form name="form" method="post">
	<input type="hidden" name="msgOperacao" value=""> 
	<input type="hidden" name="msgPesquisa" value="">

	<!-- Hiddens para controle das páginas... -->
	<input type="hidden" name="codIett" value="<%=codIett%>">
	<input type="hidden" name="codFonr" value="<%=codFonr%>">
	<input type="hidden" name="codRec" value="<%=codRec%>">
	<input type="hidden" name="codAba" value="<%=codAba %>">
<%

	if("incluir".equals(Pagina.getParamStr(request, "hidAcao"))){
		//Exercícios nunca vai ser null pq tem validação na tela anterior
		List contas = new ArrayList();
		if(exercicios != null){
			//Incluir a mesma conta (recurso + fonte) para cada exercício selecionado.
			for(int i = 0; i < exercicios.length; i++){
				EfItemEstContaEfiec conta = new EfItemEstContaEfiec();
				conta.setFormaInclusao(new Integer (Dominios.CONTA_FORMA_INCLUSAO_VIA_USUARIO));
				request.setAttribute("exercicioExe", exercicios[i]);
				request.setAttribute("fonteRecursoFonr", codFonr);
				request.setAttribute("recursoRec", codRec);
				itemEstruturaContaOrcamentoDao.setItemEstruturaContaOrcamento(request, conta);
				contas.add(conta);

			}
		}
		itemEstruturaContaOrcamentoDao.salvar(contas);
		msg = "Incluído com sucesso";
	}
%>	
</form>
<%
} catch ( ECARException e){
	msg = _msg.getMensagem(e.getMessageKey());
} catch (Exception e){
%>
	<%@ include file="/excecao.jsp"%>
<%
}

if (msg != null)
	Mensagem.setInput(_jspx_page_context, "document.form.msgOperacao.value", msg);
%>

	<script language="javascript">
		document.form.action = "<%=submit%>";
		document.form.submit();
	</script> 


</body>

<%@ include file="/include/mensagemAlert.jsp" %>
</html>