<%@ include file="../../login/validaAcesso.jsp"%>

<%@ include file="../../frm_global.jsp"%>
<%@ page import="ecar.pojo.TipoParticipacaoTpp" %>
<%@ page import="ecar.dao.TipoParticipacaoDao" %>
<%@ page import="comum.util.Data" %> 
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
	<!-- campo de controle para as mensagens -->
	<input type="hidden" name="msgOperacao" value=""> 
	<input type="hidden" name="msgPesquisa" value="">	
	<input type="hidden" name="hidPesquisou" value="">
</form>

<%
	TipoParticipacaoTpp tpp = new TipoParticipacaoTpp();
	TipoParticipacaoDao tppDao = new TipoParticipacaoDao(request);
	
	String hidAcao = Pagina.getParam(request, "hidAcao");
	String msg = null;
	String submit = "frm_pes.jsp";
	boolean refazPesquisa = true;
	boolean incluir = "incluir".equalsIgnoreCase(hidAcao);
	boolean alterar = "alterar".equalsIgnoreCase(hidAcao);
	boolean excluir = "excluir".equalsIgnoreCase(hidAcao);
	boolean pesquisar = "pesquisar".equalsIgnoreCase(hidAcao);
	
	if (incluir) {
		/* melhor usar getParamStr, pois nunca devolve null */		
		tpp.setDescricaoTpp(Pagina.getParamStr(request, "descricaoTpp").trim()); 
		tpp.setIndAtivoTpp(Pagina.getParamStr(request, "indAtivoTpp"));
		tpp.setDataInclusaoTpp(Data.getDataAtual());
		tpp.setItemEstrutEntidadeIettes(null);
		refazPesquisa = false;
		submit = "frm_inc.jsp";
		try {
			tppDao.salvar(tpp);
			msg = _msg.getMensagem("tipoParticipacao.inclusao.sucesso" );
		} catch (ECARException e) {
			org.apache.log4j.Logger.getLogger(this.getClass()).error(e);
			msg = _msg.getMensagem(e.getMessageKey());
		} catch (Exception e){
		%>
			<%@ include file="/excecao.jsp"%>
		<%
		}
	} else if (alterar) {
		try {
			tpp = (TipoParticipacaoTpp) tppDao.buscar(TipoParticipacaoTpp.class, Long.valueOf(Pagina.getParam(request, "codigo")));
			tpp.setCodTpp(Long.valueOf(Pagina.getParam(request, "codigo")));
			tpp.setDescricaoTpp(Pagina.getParamStr(request, "descricaoTpp").trim());
			tpp.setIndAtivoTpp(Pagina.getParamStr(request, "indAtivoTpp"));
		
			tppDao.alterar(tpp);
			msg = _msg.getMensagem("tipoParticipacao.alteracao.sucesso" );
		} catch (ECARException e) {
			org.apache.log4j.Logger.getLogger(this.getClass()).error(e);
			msg = _msg.getMensagem(e.getMessageKey());
		} catch (Exception e){
		%>
			<%@ include file="/excecao.jsp"%>
		<%
		}
	} else if (excluir) {
		try {
			tppDao.excluir((TipoParticipacaoTpp)tppDao.buscar(TipoParticipacaoTpp.class, Long.valueOf(Pagina.getParam(request, "codigo"))));
			msg = _msg.getMensagem("tipoParticipacao.exclusao.sucesso");
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
		tpp.setDescricaoTpp(Pagina.getParam(request, "descricaoTpp")); 
		tpp.setIndAtivoTpp(Pagina.getParam(request, "indAtivoTpp")); 
		session.setAttribute("objPesquisa", tpp);
	}
	/* da o alert para incluir, alterar, pesquisar ou excluir */
	if (msg != null)
		Mensagem.setInput(_jspx_page_context, "document.form.msgOperacao.value", msg); 

	if (refazPesquisa) {
		try {
			tpp = (TipoParticipacaoTpp) session.getAttribute("objPesquisa");
			List lista = tppDao.pesquisar(tpp, new String[] {"descricaoTpp","asc"});
			boolean pesquisou = (lista.size() > 0) ? true : false;
			session.setAttribute("lista", lista);
			if (pesquisou) {
				submit = "frm_nav.jsp";
				msg = null;
			} else {
				Mensagem.setInput(_jspx_page_context, "document.form.hidPesquisou.value", "false");
				submit = "frm_pes.jsp";
				msg = _msg.getMensagem("tipoParticipacao.pesquisa.nenhumRegistro");
			}
		} catch (ECARException e) {
			org.apache.log4j.Logger.getLogger(this.getClass()).error(e);
			msg = _msg.getMensagem(e.getMessageKey());
		} catch (Exception e){
		%>
			<%@ include file="/excecao.jsp"%>
		<%
		}
		if (msg != null){
			Mensagem.setInput(_jspx_page_context, "document.form.msgPesquisa.value", msg);
		}
	}
	%>
	<script language="javascript">
		document.form.action = "<%=submit%>";
		document.form.submit();
	</script> 
</body>
</html>