
<%@ include file="../../login/validaAcesso.jsp"%>
<%@ include file="../../frm_global.jsp"%>

<%@ page import="ecar.pojo.EfItemEstContaEfiec" %>
<%@ page import="ecar.dao.ItemEstruturaContaOrcamentoDao" %>
<%@ page import="ecar.util.Dominios" %>
<html lang="pt-br">
<head>
<%@ include file="../../include/meta.jsp"%>
<%@ include file="/titulo.jsp"%>
</head>
<body>
<%
	String ultimoIdLinhaDetalhado = Pagina.getParamStr(request,"ultimoIdLinhaDetalhado");
%>
<form name="form" method="post">
	<input type="hidden" name="msgOperacao" value=""> 
	<input type="hidden" name="msgPesquisa" value="">	
	<input type="hidden" name="codAba" value="<%=Pagina.getParamStr(request, "codAba")%>">
	<input type="hidden" name="ultimoIdLinhaDetalhado" value=<%=ultimoIdLinhaDetalhado%>>	
	
	<!-- chave primaria-->
	<input type="hidden" name="cod" value="<%=Pagina.getParamInt(request, "cod")%>">
	<input type="hidden" name="codIett" value="<%=Pagina.getParamInt(request, "codIett")%>">	
	<input type="hidden" name="codFonr" value="<%=Pagina.getParamInt(request, "codFonr")%>">
</form>

<%
	String msg = "";
	String submit = "";
	
	try{
		EfItemEstContaEfiec conta = new EfItemEstContaEfiec();
		ItemEstruturaContaOrcamentoDao itemEstruturaContaOrcamentoDao = new ItemEstruturaContaOrcamentoDao(request);
		
		if("incluir".equals(Pagina.getParamStr(request, "hidAcao"))){
			conta.setFormaInclusao(Integer.valueOf(Dominios.CONTA_FORMA_INCLUSAO_VIA_USUARIO));
			itemEstruturaContaOrcamentoDao.setItemEstruturaContaOrcamento(request, conta);
			
			submit = "frm_inc.jsp";
						
			if (itemEstruturaContaOrcamentoDao.consisteEfItemEstContaEfiec(conta)) {
				itemEstruturaContaOrcamentoDao.salvar(conta);
				msg = _msg.getMensagem("itemEstrutura.contaOrcamento.inclusao.sucesso");
			} else {
				msg = _msg.getMensagem("itemEstrutura.contaOrcamento.alteracao.consistencia");
			}
		}
		
		if("alterar".equals(Pagina.getParamStr(request, "hidAcao"))){
			conta = (EfItemEstContaEfiec) itemEstruturaContaOrcamentoDao.buscar(EfItemEstContaEfiec.class, Long.valueOf(Pagina.getParamStr(request, "cod")));
			itemEstruturaContaOrcamentoDao.setItemEstruturaContaOrcamento(request, conta);
			
			submit = "frm_con.jsp";
			
			if (itemEstruturaContaOrcamentoDao.consisteEfItemEstContaEfiec(conta)) {
				itemEstruturaContaOrcamentoDao.alterar(conta);
				msg = _msg.getMensagem("itemEstrutura.contaOrcamento.alteracao.sucesso");
			} else {
				msg = _msg.getMensagem("itemEstrutura.contaOrcamento.alteracao.consistencia");
			}			

		}
		
		if("excluir".equals(Pagina.getParamStr(request, "hidAcao"))){
			String codigosParaExcluir[] = request.getParameterValues("excluir");
			itemEstruturaContaOrcamentoDao.excluir(codigosParaExcluir);
			submit = "lista.jsp";
			msg = _msg.getMensagem("itemEstrutura.contaOrcamento.exclusao.sucesso");				
		}
		
	} catch ( ECARException e){
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

	<script language="javascript">
		document.form.action = "<%=submit%>";
		document.form.submit();
	</script> 

</body>
</html>

