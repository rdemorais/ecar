<%@ include file="../../login/validaAcesso.jsp"%>

<%@ page import="ecar.pojo.LocalGrupoLgp" %>
<%@ page import="ecar.dao.LocalGrupoDao" %>
<%@ page import="comum.util.Data" %>
<%@ page import="java.util.List" %>
<%@ include file="../../frm_global.jsp"%>

<html lang="pt-br">
<head>
<%@ include file="../../include/meta.jsp"%>
<%@ include file="/titulo.jsp"%>
</head>
<body>

<form name="form" method="post">
    <!-- campo de controle para a navegação de registros -->
	<input type="hidden" name="hidRegistro" value="<%=Pagina.getParamInt(request, "hidRegistro")%>">
	
	<!-- campo de controle para mensagens -->
	<input type="hidden" name="msgOperacao" value=""> 
	<input type="hidden" name="msgPesquisa" value="">	
	<input type="hidden" name="hidPesquisou" value="">
	
</form>

<%
	LocalGrupoLgp localGrupo = new LocalGrupoLgp();
	LocalGrupoDao localGrupoDao = new LocalGrupoDao(request);
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
		
		localGrupo.setIdentificacaoLgp( Pagina.getParamStr(request, "identificacaoLgp").trim() );
		localGrupo.setSiglaLgp(Pagina.getParamStr(request,"siglaLgp").trim());
		 
		localGrupo.setIndAtivoLgp( Pagina.getParamStr(request, "indAtivoLgp")); 
		localGrupo.setDataInclusaoLgp(Data.getDataAtual());
		refazPesquisa = false;
		submit = "frm_inc.jsp";
		try {
			localGrupoDao.salvar(localGrupo);
			msg = _msg.getMensagem("localGrupo.inclusao.sucesso");
			session.removeAttribute("objLocalGrupo");
		} catch (ECARException e) {
			org.apache.log4j.Logger.getLogger(this.getClass()).error(e);
			session.setAttribute("objLocalGrupo", localGrupo);
			msg = _msg.getMensagem(e.getMessageKey());
		} catch (Exception e){
		%>
			<%@ include file="/excecao.jsp"%>
		<%
		}
	} else if (alterar) {
		try {
			/* busca o objeto para manter o que já esta cadastrado */
			localGrupo = (LocalGrupoLgp) localGrupoDao.buscar(LocalGrupoLgp.class, Long.valueOf(Pagina.getParam(request, "codigo")));
			/* altera o que foi modificado na tela */
			localGrupo.setSiglaLgp(Pagina.getParamStr(request,"siglaLgp").trim());
			
			localGrupo.setIdentificacaoLgp(Pagina.getParamStr(request, "identificacaoLgp").trim()); 
			localGrupo.setIndAtivoLgp((Pagina.getParamStr(request, "indAtivoLgp"))); 
			/* altera no BD */
			localGrupoDao.alterar(localGrupo);
			msg = _msg.getMensagem("localGrupo.alteracao.sucesso");
			session.removeAttribute("objLocalGrupo");
		} catch (ECARException e) {
			org.apache.log4j.Logger.getLogger(this.getClass()).error(e);
			refazPesquisa = false;
			submit = "frm_alt.jsp";
			session.setAttribute("objLocalGrupo", localGrupo);
			msg = _msg.getMensagem(e.getMessageKey());
		} catch (Exception e){
		%>
			<%@ include file="/excecao.jsp"%>
		<%
		}
	} else if (excluir) {
		try { 
			/* busca e exclui o objeto do BD */
			localGrupoDao.excluir((LocalGrupoLgp)localGrupoDao.buscar(LocalGrupoLgp.class, Long.valueOf(Pagina.getParam(request, "codigo"))));
			msg = _msg.getMensagem("localGrupo.exclusao.sucesso");
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
		
		localGrupo.setSiglaLgp((Pagina.getParam(request, "siglaLgp")));
		localGrupo.setIdentificacaoLgp((Pagina.getParam(request, "identificacaoLgp"))); 
		localGrupo.setIndAtivoLgp((Pagina.getParam(request, "indAtivoLgp"))); 
		session.setAttribute("objPesquisa", localGrupo);
	}
	/* da o alert para incluir, alterar, pesquisar ou excluir */
	if (msg != null)
		Mensagem.setInput(_jspx_page_context, "document.form.msgOperacao.value", msg); 
		
	if (refazPesquisa) {
		try {
			localGrupo = (LocalGrupoLgp) session.getAttribute("objPesquisa");
			List lista = localGrupoDao.pesquisar(localGrupo, new String[] {"identificacaoLgp","asc"});
			boolean pesquisou = (lista.size() > 0) ? true : false;
			session.setAttribute("lista", lista);
			if (pesquisou) {
				submit = "frm_nav.jsp";
				msg = null;
			} else {
				Mensagem.setInput(_jspx_page_context, "document.form.hidPesquisou.value", "false");
				submit = "frm_pes.jsp";
				msg = _msg.getMensagem("localGrupo.pesquisa.nenhumRegistro");
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

