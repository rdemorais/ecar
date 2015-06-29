<%@ include file="../../../login/validaAcesso.jsp"%>
<%@ include file="../../../frm_global.jsp"%>

<%@ page import="ecar.pojo.IettIndResulRevIettrr" %>
<%@ page import="ecar.dao.IettIndResulRevIettrrDAO" %>

<%@ page import="java.util.List" %>
<%@ page import="ecar.pojo.ItemEstrtIndResulIettr" %>
<%@ page import="ecar.dao.ItemEstrtIndResulDao" %>

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
	<input type="hidden" name="codAbaMeta" value="<%=Pagina.getParamStr(request, "codAbaMeta")%>">
	<input type="hidden" name="ultimoIdLinhaDetalhado" value="<%=Pagina.getParamStr(request, "ultimoIdLinhaDetalhado")%>">
	<input type="hidden" name="inseriuMeta" value="S">
	
	
	<!-- chave primaria-->
	<input type="hidden" name="codIett" value="<%=Pagina.getParamStr(request, "codIett")%>">
	<input type="hidden" name="codIettrev" value="<%=Pagina.getParamStr(request, "codIettrev")%>">
	<input type="hidden" name="codIettirr" value="<%=Pagina.getParamStr(request, "codIettirr")%>">
</form>

<%
	String msg = "";
	String submit = "";
	
	try{
		IettIndResulRevIettrr itemEstrtIndResul = new IettIndResulRevIettrr();
		IettIndResulRevIettrrDAO itemEstrtIndResulDao = new IettIndResulRevIettrrDAO(request);
		
		ItemEstrtIndResulIettr itemIndResul = new ItemEstrtIndResulIettr();
		ItemEstrtIndResulDao itemIndResulDao = new ItemEstrtIndResulDao(request);
		
		
		if("incluir".equals(Pagina.getParamStr(request, "hidAcao"))){

			Long codIR = null;
			if(Pagina.getParam(request, "codIettir") != null){
				codIR = Long.valueOf(Pagina.getParamStr(request, "codIettir"));
			}
		
			if(codIR != null)
				itemIndResul = (ItemEstrtIndResulIettr) itemIndResulDao.buscar(ItemEstrtIndResulIettr.class, codIR);
			else
				itemIndResul = null;
		
			itemEstrtIndResul.setItemEstrtIndResulIettr(itemIndResul);
			itemEstrtIndResulDao.setItemEstrtIndResul(request, itemEstrtIndResul);
			List listaQtd = itemEstrtIndResulDao.getListaQuantidadePrevista(request);
			itemEstrtIndResulDao.salvar(itemEstrtIndResul, listaQtd);
			submit = "../frm_inc.jsp";
			msg = _msg.getMensagem("itemEstrutura.indResultado.inclusao.sucesso");
		}
		
		if("alterar".equals(Pagina.getParamStr(request, "hidAcao"))){
			Long codIR = null;
			if(Pagina.getParam(request, "codIettir") != null){
				codIR = Long.valueOf(Pagina.getParamStr(request, "codIettir"));
			}
		
			if(codIR != null)
				itemIndResul = (ItemEstrtIndResulIettr) itemIndResulDao.buscar(ItemEstrtIndResulIettr.class, codIR);
			else
				itemIndResul = null;
		
			itemEstrtIndResul = (IettIndResulRevIettrr) itemEstrtIndResulDao.buscar(IettIndResulRevIettrr.class, Integer.valueOf(Pagina.getParamInt(request, "codIettirr")));
			itemEstrtIndResul.setItemEstrtIndResulIettr(itemIndResul);
			itemEstrtIndResulDao.setItemEstrtIndResul(request, itemEstrtIndResul);
			List listaQtd = itemEstrtIndResulDao.getListaQuantidadePrevista(request);
			itemEstrtIndResulDao.alterar(itemEstrtIndResul, listaQtd);
			submit = "frm_con.jsp";
			msg = _msg.getMensagem("itemEstrutura.indResultado.alteracao.sucesso");
		}
		
		if("excluir".equals(Pagina.getParamStr(request, "hidAcao"))){
			String codigosParaExcluir[] = request.getParameterValues("excluirMeta");
			itemEstrtIndResulDao.excluir(codigosParaExcluir);
			submit = "../frm_inc.jsp?hidGravado=S";
			msg = _msg.getMensagem("itemEstrutura.indResultado.exclusao.sucesso");
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

