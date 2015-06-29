
<%@ include file="../../login/validaAcesso.jsp"%>
<%@ include file="../../frm_global.jsp"%>

<%@ page import="ecar.pojo.ItemEstrutEntidadeIette" %>
<%@ page import="ecar.dao.ItemEstrutEntidadeDao" %>
<%@ page import="java.util.Date"%>

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
	<input type="hidden" name="codIett" value="<%=Pagina.getParamInt(request, "codIett")%>">
	<input type="hidden" name="codEnt" value="<%=Pagina.getParamInt(request, "codEnt")%>">
	<input type="hidden" name="codTpp" value="<%=Pagina.getParamInt(request, "codTpp")%>">
</form>

<%
	String msg = "";
	String submit = "";
	
	try{
		ItemEstrutEntidadeIette itemEstrutEntidade = new ItemEstrutEntidadeIette();
		ItemEstrutEntidadeDao itemEstrutEntidadeDao = new ItemEstrutEntidadeDao(request);
		
		if("incluir".equals(Pagina.getParamStr(request, "hidAcao"))){
			itemEstrutEntidadeDao.setItemEstrutEntidade(request, itemEstrutEntidade);	
			itemEstrutEntidadeDao.salvar(itemEstrutEntidade);
			submit = "lista.jsp";
			msg = _msg.getMensagem("itemEstrutura.entidade.inclusao.sucesso");
		}
		
		if("alterar".equals(Pagina.getParamStr(request, "hidAcao"))){
			itemEstrutEntidade = itemEstrutEntidadeDao.buscar(Long.valueOf(Pagina.getParamStr(request, "codIett")), Long.valueOf(Pagina.getParamStr(request, "codEnt")), Long.valueOf(Pagina.getParamStr(request, "codTpp")));
			itemEstrutEntidadeDao.setItemEstrutEntidade(request, itemEstrutEntidade);
			itemEstrutEntidade.setUsuarioUsuManutencao(((ecar.login.SegurancaECAR)session.getAttribute("seguranca")).getUsuario());
			itemEstrutEntidade.setDataUltManutencaoIette(new Date());
			itemEstrutEntidadeDao.alterar(itemEstrutEntidade);
			submit = "frm_con.jsp";
			msg = _msg.getMensagem("itemEstrutura.entidade.alteracao.sucesso");
		}
		
		if("excluir".equals(Pagina.getParamStr(request, "hidAcao"))){
			String codigosParaExcluir[] = request.getParameterValues("excluir");
			itemEstrutEntidadeDao.excluir(codigosParaExcluir, Long.valueOf(Pagina.getParamStr(request, "codIett")),
					((ecar.login.SegurancaECAR)session.getAttribute("seguranca")).getUsuario());
			submit = "lista.jsp";
			msg = _msg.getMensagem("itemEstrutura.entidade.exclusao.sucesso");				
		}
		
	} catch ( ECARException e){
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

	<script language="javascript">
		document.form.action = "<%=submit%>";
		document.form.submit();
	</script> 

</body>
</html>

