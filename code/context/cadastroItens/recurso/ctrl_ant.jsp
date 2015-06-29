<%@ include file="/login/validaAcesso.jsp"%>
<%@ include file="/frm_global.jsp"%>

<%@ page import="ecar.pojo.EfIettFonteTotEfieft"%>
<%@ page import="ecar.dao.ItemEstruturaFonteRecursoDao" %>

<%@ page import="java.util.List"%>

<html lang="pt-br">
<head>

<%@ include file="/include/meta.jsp"%> 

<head>
<body>
<form name="form" method="post">   

<!-- campo de controle para mensagens -->
<input type="hidden" name="msgOperacao" value=""> 
<input type="hidden" name="codAba" value=<%=Pagina.getParamStr(request, "codAba")%>>
<!-- campo hidden para cada chave primaria da tabela --> 
<input type="hidden" name="codIett" value=<%=Pagina.getParamStr(request, "codIett")%>>
<input type="hidden" name="codFonr" value=<%=Pagina.getParamStr(request, "codFonr")%>>

<%
	String msg = "";
	String submit = "";
	try{
		EfIettFonteTotEfieft itemEstruturaFonteRecurso = new EfIettFonteTotEfieft();
		ItemEstruturaFonteRecursoDao itemEstruturaFonteRecursoDao = new ItemEstruturaFonteRecursoDao(request);			
		if("incluir".equals(Pagina.getParamStr(request, "hidAcao"))){
			itemEstruturaFonteRecursoDao.setItemEstruturaFonteRecurso(request, itemEstruturaFonteRecurso);
			List recursos = itemEstruturaFonteRecursoDao.setListaRecurso(request, itemEstruturaFonteRecurso);
			itemEstruturaFonteRecursoDao.salvar(itemEstruturaFonteRecurso, recursos);
			submit = "lista.jsp";
			msg = _msg.getMensagem("itemEstrutura.fonteRecurso.inclusao.sucesso");
		}
		if("alterar".equals(Pagina.getParamStr(request, "hidAcao"))){
			itemEstruturaFonteRecurso = itemEstruturaFonteRecursoDao.buscar(
						 Long.valueOf(Pagina.getParamStr(request, "codIett")), 
						 Long.valueOf(Pagina.getParamStr(request, "codFonr")));
			itemEstruturaFonteRecurso.setDataValorEfieft(Pagina.getParamDataBanco(request, "dataValorEfieft"));
			List recursos = itemEstruturaFonteRecursoDao.setListaRecurso(request, itemEstruturaFonteRecurso);
			itemEstruturaFonteRecursoDao.alterar(itemEstruturaFonteRecurso, recursos);
			submit = "frm_alt.jsp";
			msg = _msg.getMensagem("itemEstrutura.fonteRecurso.alteracao.sucesso");
		}
		if("excluir".equals(Pagina.getParamStr(request, "hidAcao"))){
			String codigosParaExcluir[] = request.getParameterValues("excluir");
			itemEstruturaFonteRecursoDao.excluir(codigosParaExcluir, Long.valueOf(Pagina.getParamStr(request, "codIett")));
			submit = "lista.jsp";
			msg = _msg.getMensagem("itemEstrutura.fonteRecurso.exclusao.sucesso");				
		}
	}
	catch ( ECARException e){
		org.apache.log4j.Logger.getLogger(this.getClass()).error(e);
		submit = "lista.jsp";
		msg = _msg.getMensagem(e.getMessageKey());
	} catch (Exception e){
	%>
		<%@ include file="/excecao.jsp"%>
	<%
	}
	
	if (msg != null)
		Mensagem.setInput(_jspx_page_context, "document.form.msgOperacao.value", msg); 
	%>

<script>
	document.form.action = "<%=submit%>";
	document.form.submit();
</script>

</form>
</body>
</html>