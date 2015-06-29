<%@ include file="/login/validaAcesso.jsp"%>
<%@ include file="/frm_global.jsp"%>

<%@ page import="ecar.pojo.EfIettFonteTotEfieft"%>
<%@ page import="ecar.dao.ItemEstruturaFonteRecursoDao" %>

<%@ page import="java.util.List"%>
<%@ page import="ecar.pojo.EfIettFonTotRevEfieftr" %>
<%@ page import="ecar.dao.ItemEstruturaFonteRecursoRevDao" %>

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
<input type="hidden" name="codIettrev" value=<%=Pagina.getParamStr(request, "codIettrev")%>>
<input type="hidden" name="codFonr" value=<%=Pagina.getParamStr(request, "codFonr")%>>
<input type="hidden" name="ultimoIdLinhaDetalhado" value="<%=Pagina.getParamStr(request, "ultimoIdLinhaDetalhado")%>">

<%
	int numRecursos = Pagina.getParamInt(request, "numRecursos");

	String msg = "";
	String submit = "";
	try{
		EfIettFonTotRevEfieftr itemEstruturaFonteRecurso = new EfIettFonTotRevEfieftr();
		ItemEstruturaFonteRecursoRevDao itemEstruturaFonteRecursoDao = new ItemEstruturaFonteRecursoRevDao(request);			
		if("incluir".equals(Pagina.getParamStr(request, "hidAcao"))){
			itemEstruturaFonteRecursoDao.setItemEstruturaFonteRecurso(request, itemEstruturaFonteRecurso);
			List recursos = itemEstruturaFonteRecursoDao.setListaRecurso(request, itemEstruturaFonteRecurso, numRecursos);
			itemEstruturaFonteRecursoDao.salvar(itemEstruturaFonteRecurso, recursos);
			submit = "lista.jsp";
			msg = _msg.getMensagem("itemEstrutura.fonteRecurso.inclusao.sucesso");
		}
		if("alterar".equals(Pagina.getParamStr(request, "hidAcao"))){
			itemEstruturaFonteRecurso = itemEstruturaFonteRecursoDao.buscar(
						 Long.valueOf(Pagina.getParamStr(request, "codIettrev")), 
						 Long.valueOf(Pagina.getParamStr(request, "codFonr")));
			itemEstruturaFonteRecurso.setDataValorEfieftr(Pagina.getParamDataBanco(request, "dataValorEfieft"));
			List recursos = itemEstruturaFonteRecursoDao.setListaRecurso(request, itemEstruturaFonteRecurso, numRecursos);
			itemEstruturaFonteRecursoDao.alterar(itemEstruturaFonteRecurso, recursos);
			submit = "lista.jsp";
			msg = _msg.getMensagem("itemEstrutura.fonteRecurso.alteracao.sucesso");
		}
		if("excluir".equals(Pagina.getParamStr(request, "hidAcao"))){
			String codigosParaExcluir[] = request.getParameterValues("excluirRecurso");
			itemEstruturaFonteRecursoDao.excluir(codigosParaExcluir, Long.valueOf(Pagina.getParamStr(request, "codIettrev")));
			submit = "../frm_inc.jsp?hidGravado=S";
			msg = _msg.getMensagem("itemEstrutura.fonteRecurso.exclusao.sucesso");				
		}
	}
	catch ( ECARException e){
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

<script>
	document.form.action = "<%=submit%>";
	document.form.submit();
</script>

</form>
</body>
</html>