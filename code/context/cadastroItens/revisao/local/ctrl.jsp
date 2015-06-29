<%@ include file="../../../login/validaAcesso.jsp"%>
<%@ include file="../../../frm_global.jsp"%>


<%@ page import="comum.util.Data" %>
<%@ page import="ecar.pojo.ItemEstLocalRevIettlr" %>
<%@ page import="ecar.dao.ItemEstLocalRevIettlrDAO" %>

<html lang="pt-br">
<head>
<%@ include file="../../../include/meta.jsp"%>
<%@ include file="/titulo.jsp"%>
</head>
<body>

<form name="form" method="post">
	<input type="hidden" name="msgOperacao" value=""> 
	<input type="hidden" name="msgPesquisa" value="">	
	<input type="hidden" name="codAba" value="<%=Pagina.getParamStr(request, "codAba")%>">
	<input type="hidden" name="codAbaLocal" value="<%=Pagina.getParamStr(request, "codAbaLocal")%>">
	<input type="hidden" name="inseriuMeta" value="S">
	<input type="hidden" name="codLgp" value="<%=Pagina.getParamInt(request, "codLgp")%>">
	<input type="hidden" name="ultimoIdLinhaDetalhado" value="<%=Pagina.getParamStr(request, "ultimoIdLinhaDetalhado")%>">
	
	<!-- chave primaria-->
	<input type="hidden" name="codIett" value="<%=Pagina.getParamStr(request, "codIett")%>">
	<input type="hidden" name="codIettrev" value="<%=Pagina.getParamStr(request, "codIettrev")%>">
	<input type="hidden" name="codLit" value="<%=Pagina.getParamInt(request, "codLit")%>">
</form>

<%
	String msg = "";
	String submit = "";
	
	try{
		ItemEstLocalRevIettlr itemEstrutLocal = new ItemEstLocalRevIettlr();
		ItemEstLocalRevIettlrDAO itemEstrutLocalDao = new ItemEstLocalRevIettlrDAO(request);
		
		if("incluir".equals(Pagina.getParamStr(request, "hidAcao"))){
			itemEstrutLocalDao.setItemEstLocalRevIettlrDAO(request, itemEstrutLocal);
			itemEstrutLocal.setDataInclusaoIettlr(Data.getDataAtual());
			itemEstrutLocalDao.salvar(itemEstrutLocal);
			submit = "../frm_inc.jsp";
			msg = _msg.getMensagem("itemEstrutura.localItem.inclusao.sucesso");
		}
		
		if("excluir".equals(Pagina.getParamStr(request, "hidAcao"))){
			String codigosParaExcluir[] = request.getParameterValues("excluirLocal");
			itemEstrutLocalDao.excluir(codigosParaExcluir, Integer.valueOf(Pagina.getParamStr(request, "codIettrev")));
			submit = "../frm_inc.jsp";
			msg = _msg.getMensagem("itemEstrutura.localItem.exclusao.sucesso");				
		}
		
	} catch ( ECARException e){
		org.apache.log4j.Logger.getLogger(this.getClass()).error(e);
		submit = "../frm_inc.jsp";
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
</html>

