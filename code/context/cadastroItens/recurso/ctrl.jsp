
<%@ include file="/login/validaAcesso.jsp"%>
<%@ include file="/frm_global.jsp"%>

<%@ page import="ecar.pojo.EfIettFonteTotEfieft"%>
<%@ page import="ecar.dao.ItemEstruturaFonteRecursoDao" %>
<%@ page import="ecar.dao.FonteRecursoDao" %>
<%@ page import="ecar.pojo.FonteRecursoFonr" %>
<%@ page import="java.util.List"%>

<html lang="pt-br">
<head>

<%@ include file="/include/meta.jsp"%> 

<head>
<body>
<%
	String ultimoIdLinhaDetalhado = Pagina.getParamStr(request,"ultimoIdLinhaDetalhado");
%>
<form name="form" method="post">   

<!-- campo de controle para mensagens -->
<input type="hidden" name="msgOperacao" value=""> 
<input type="hidden" name="codAba" value=<%=Pagina.getParamStr(request, "codAba")%>>
<!-- campo hidden para cada chave primaria da tabela --> 
<input type="hidden" name="codIett" value=<%=Pagina.getParamStr(request, "codIett")%>>
<input type="hidden" name="codFonr" value=<%=Pagina.getParamStr(request, "codFonr")%>>
<input type="hidden" name="ultimoIdLinhaDetalhado" value=<%=ultimoIdLinhaDetalhado%>>
<%
	int numRecursos = Pagina.getParamInt(request, "numRecursos");

	String msg = "";
	String submit = "";
	try{
		EfIettFonteTotEfieft itemEstruturaFonteRecurso = new EfIettFonteTotEfieft();
		ItemEstruturaFonteRecursoDao itemEstruturaFonteRecursoDao = new ItemEstruturaFonteRecursoDao(request);			
		if("incluir".equals(Pagina.getParamStr(request, "hidAcao"))){
			itemEstruturaFonteRecursoDao.setItemEstruturaFonteRecurso(request, itemEstruturaFonteRecurso);
			List recursos = itemEstruturaFonteRecursoDao.setListaRecurso(request, itemEstruturaFonteRecurso, numRecursos);
			itemEstruturaFonteRecursoDao.salvar(itemEstruturaFonteRecurso, recursos);
			submit = "lista.jsp";
			msg = _msg.getMensagem("itemEstrutura.fonteRecurso.inclusao.sucesso");
		}
		if("incluirProximo".equals(Pagina.getParamStr(request, "hidAcao"))){
			itemEstruturaFonteRecursoDao.setItemEstruturaFonteRecurso(request, itemEstruturaFonteRecurso);
			List recursos = itemEstruturaFonteRecursoDao.setListaRecurso(request, itemEstruturaFonteRecurso, numRecursos);
			itemEstruturaFonteRecursoDao.salvar(itemEstruturaFonteRecurso, recursos);

			String[] proximoRecurso = itemEstruturaFonteRecursoDao.verificaExistenciaRecursoIett(itemEstruturaFonteRecurso.getItemEstruturaIett());
			
			if(proximoRecurso == null){
				submit = "lista.jsp";
				msg = _msg.getMensagem("itemEstrutura.recurso.validacao.todosRecursos");
			}
			else {//Ainda existem fontes que não foram cadastradas... verificar qual é a próxima fonte...
				submit = "frm_inc.jsp";
				msg = _msg.getMensagem("itemEstrutura.fonteRecurso.inclusao.sucesso");
			%>
				<input type="hidden" name="codProximaFonte" value="<%=proximoRecurso[0]%>">
				<input type="hidden" name="nomeProximaFonte" value="<%=proximoRecurso[1]%>">
				<input type="hidden" name="incluirNovoValor" value="S">
			<%
			}
		}
		if("alterar".equals(Pagina.getParamStr(request, "hidAcao"))){
			itemEstruturaFonteRecurso = itemEstruturaFonteRecursoDao.buscar(
						 Long.valueOf(Pagina.getParamStr(request, "codIett")), 
						 Long.valueOf(Pagina.getParamStr(request, "codFonr")));
			itemEstruturaFonteRecurso.setDataValorEfieft(Pagina.getParamDataBanco(request, "dataValorEfieft"));
			List recursos = itemEstruturaFonteRecursoDao.setListaRecurso(request, itemEstruturaFonteRecurso, numRecursos);
			itemEstruturaFonteRecursoDao.alterar(itemEstruturaFonteRecurso, recursos);
			submit = "frm_con.jsp";
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
		e.printStackTrace();
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