<%@ include file="../../login/validaAcesso.jsp"%>
<%@ include file="../../frm_global.jsp"%>
<%@ page import="ecar.pojo.PeriodoRevisaoPrev" %>
<%@ page import="ecar.dao.PeriodoRevisaoPrevDao" %>
<%@ page import="ecar.dao.ConfiguracaoDao" %>

<html lang="pt-br"> 
<head>
<%@ include file="../../include/meta.jsp"%>
</head>
<body>
<form name="form" method="post">  

<!-- campo de controle para mensagens -->
<input type="hidden" name="msgOperacao" value=""> 
<!-- campo hidden para cada chave primaria da tabela --> 
<input type="hidden" name="codPrev" value=<%=Pagina.getParamStr(request, "codPrev")%>>

<%
	String msg = "";
	String submit = "";
	try{
	PeriodoRevisaoPrevDao prevDao = new PeriodoRevisaoPrevDao(request);
	PeriodoRevisaoPrev prev = new PeriodoRevisaoPrev();

			if("incluir".equals(Pagina.getParamStr(request, "hidAcao"))){
				if (prevDao.setPeriodoRevisaoPrev(request, prev))
				{
					prevDao.salvar(prev);
					msg = _msg.getMensagem("periodoRevisao.inclusao.sucesso");
				}
				else
					msg = _msg.getMensagem("periodoRevisao.intersecao.periodo");					
				submit = "lista.jsp";			
			}	
			if("alterar".equals(Pagina.getParamStr(request, "hidAcao"))){
				prev = (PeriodoRevisaoPrev) prevDao.buscar(PeriodoRevisaoPrev.class, Long.valueOf(Pagina.getParamStr(request, "codPrev")));
				if (prevDao.setPeriodoRevisaoPrev(request, prev))
				{
					prevDao.alterar(prev);
					msg = _msg.getMensagem("periodoRevisao.alteracao.sucesso");
				}
				else
					msg = _msg.getMensagem("periodoRevisao.intersecao.periodo");									
				submit = "frm_con.jsp";
			}
			if("excluir".equals(Pagina.getParamStr(request, "hidAcao"))){
				String[] codigosParaExcluir = request.getParameterValues("excluir");
				prevDao.excluir(codigosParaExcluir);
				submit = "lista.jsp";
				msg = _msg.getMensagem("periodoRevisao.exclusao.sucesso");				
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