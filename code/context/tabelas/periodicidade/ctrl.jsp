<%@ include file="../../login/validaAcesso.jsp"%>

<%@ include file="../../frm_global.jsp"%>
<%@ page import="ecar.pojo.PeriodicidadePrdc" %>
<%@ page import="ecar.dao.PeriodicidadeDao" %>
<%@ page import="java.util.List" %>

<html lang="pt-br">
<head>
<%@ include file="../../include/meta.jsp"%>
<%@ include file="/titulo.jsp"%>
</head>
<body>

<form name="form" method="post">
    <!-- campo de controle para a navegação de registros -->
	<input type="hidden" name="hidRegistro" value="<%=Pagina.getParamInt(request, "hidRegistro")%>">
	<input type="hidden" name="msgOperacao" value=""> 
	<input type="hidden" name="msgPesquisa" value="">	
	<input type="hidden" name="hidPesquisou" value="">
</form>

<%
	PeriodicidadePrdc periodicidade = new PeriodicidadePrdc();
	PeriodicidadeDao periodicidadeDao = new PeriodicidadeDao(request);
	Mensagem mensagem = new Mensagem(application);
	
	String hidAcao = Pagina.getParam(request, "hidAcao");
	String msg = null;
	String submit = "frm_pes.jsp";
	
	/* default sempre refazer a pesquisa */
	boolean refazPesquisa = true;  
	boolean incluir = "incluir".equalsIgnoreCase(hidAcao);
	boolean alterar = "alterar".equalsIgnoreCase(hidAcao);
	boolean excluir = "excluir".equalsIgnoreCase(hidAcao);
	boolean pesquisar = "pesquisar".equalsIgnoreCase(hidAcao);
	
	if (incluir) {
		/* melhor usar getParamStr, pois nunca devolve null */
		periodicidade.setDescricaoPrdc(Pagina.getParamStr(request, "descricaoPrdc").trim());
		periodicidade.setNumMesesPrdc(Integer.valueOf(Pagina.getParamInt(request, "numMesesPrdc")));
		
		refazPesquisa = false;
		submit = "frm_inc.jsp";
		try {
			periodicidadeDao.salvar(periodicidade);
			msg = _msg.getMensagem("periodicidade.inclusao.sucesso");
			session.removeAttribute("objPeriodicidade");
		} catch (ECARException e) {
			org.apache.log4j.Logger.getLogger(this.getClass()).error(e);
			session.setAttribute("objPeriodicidade", periodicidade);
			msg = _msg.getMensagem(e.getMessageKey());
		} catch (Exception e){
		%>
			<%@ include file="/excecao.jsp"%>
		<%
		}
	} else if (alterar) {
		try {
			/* busca o objeto para manter o que já esta cadastrado */
			periodicidade = (PeriodicidadePrdc) periodicidadeDao.buscar(PeriodicidadePrdc.class, Long.valueOf(Pagina.getParam(request, "codigo")));
			
			periodicidade.setDescricaoPrdc(Pagina.getParamStr(request, "descricaoPrdc").trim());
			periodicidade.setNumMesesPrdc(Integer.valueOf(Pagina.getParamInt(request, "numMesesPrdc")));
			
			periodicidadeDao.alterar(periodicidade);
			msg = _msg.getMensagem("periodicidade.alteracao.sucesso");
			session.removeAttribute("objPeriodicidade");
		} catch (ECARException e) {
			org.apache.log4j.Logger.getLogger(this.getClass()).error(e);
			refazPesquisa = false;
			submit = "frm_alt.jsp";
			session.setAttribute("objPeriodicidade", periodicidade);
			msg = _msg.getMensagem(e.getMessageKey());
		} catch (Exception e){
		%>
			<%@ include file="/excecao.jsp"%>
		<%
		}
	} else if (excluir) {
		try {
			periodicidadeDao.excluir((PeriodicidadePrdc) periodicidadeDao.buscar(PeriodicidadePrdc.class, Long.valueOf(Pagina.getParam(request, "codigo"))));
			msg = _msg.getMensagem("periodicidade.exclusao.sucesso" );
		} catch (ECARException e) {
			org.apache.log4j.Logger.getLogger(this.getClass()).error(e);
			msg = _msg.getMensagem(e.getMessageKey());
		} catch (Exception e){
		%>
			<%@ include file="/excecao.jsp"%>
		<%
		}
	} else if (pesquisar) {
		/* aqui devemos usar getParam, pois queremos null se o campo estiver vazio */
		periodicidade.setDescricaoPrdc(Pagina.getParam(request, "descricaoPrdc"));
		
		if (Pagina.getParam(request, "numMesesPrdc") != null)
			periodicidade.setNumMesesPrdc(Integer.valueOf(Pagina.getParamInt(request, "numMesesPrdc")));
			
		session.setAttribute("objPesquisa", periodicidade);
	}
	
	/* da o alert para incluir, alterar, pesquisar ou excluir */
	if (msg != null)
		Mensagem.setInput(_jspx_page_context, "document.form.msgOperacao.value", msg); 
		
	if (refazPesquisa) {
		try {
			periodicidade = (PeriodicidadePrdc) session.getAttribute("objPesquisa");
			List lista = periodicidadeDao.pesquisar(periodicidade, new String[] {"descricaoPrdc","asc"});
			boolean pesquisou = (lista.size() > 0) ? true : false;
			session.setAttribute("lista", lista);
			if (pesquisou) {
				submit = "frm_nav.jsp";
				msg = null;
			} else {
				Mensagem.setInput(_jspx_page_context, "document.form.hidPesquisou.value", "false");
				submit = "frm_pes.jsp";
				msg = _msg.getMensagem("periodicidade.pesquisa.nenhumRegistro");
			}
		} catch (ECARException e) {
			org.apache.log4j.Logger.getLogger(this.getClass()).error(e);
			msg = _msg.getMensagem(e.getMessageKey());
		} catch (Exception e){
		%>
			<%@ include file="/excecao.jsp"%>
		<%
		}
		if (msg != null)
			Mensagem.setInput(_jspx_page_context, "document.form.msgPesquisa.value", msg);
	}
	%>
	<script language="javascript">
		document.form.action = "<%=submit%>";
		document.form.submit();
	</script> 

</body>
</html>

