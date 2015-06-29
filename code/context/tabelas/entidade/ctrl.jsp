<%@ include file="../../login/validaAcesso.jsp"%>
<%@ include file="../../frm_global.jsp"%>
<%@ page import="ecar.pojo.EntidadeEnt" %>
<%@ page import="ecar.dao.EntidadeDao" %>
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
	EntidadeEnt entidade = new EntidadeEnt();
	EntidadeDao entidadeDao = new EntidadeDao(request);
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
		entidadeDao.setEntidade(request, entidade, true);
		entidade.setItemEstrutEntidadeIettes(null);
		entidade.setDataInclusaoEnt(Data.getDataAtual());
		refazPesquisa = false;
		submit = "frm_inc.jsp";
		try {
			entidadeDao.salvar((EntidadeEnt) entidade);
			msg = _msg.getMensagem("entidade.inclusao.sucesso");
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
			/* busca o objeto para manter o que já esta cadastrado */
			entidade = (EntidadeEnt) entidadeDao.buscar(EntidadeEnt.class, Long.valueOf(Pagina.getParam(request, "codigo")));
			/* altera no BD */
			entidadeDao.alterar(entidade,request);
			entidadeDao.salvar(entidade);
			msg = _msg.getMensagem("entidade.alteracao.sucesso");
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
			/* busca e exclui o objeto do BD */
			entidadeDao.excluir((EntidadeEnt)entidadeDao.buscar(EntidadeEnt.class, Long.valueOf(Pagina.getParam(request, "codigo"))));
			msg = _msg.getMensagem("entidade.exclusao.sucesso");
		} catch (ECARException e) {
			org.apache.log4j.Logger.getLogger(this.getClass()).error(e);
			msg = _msg.getMensagem(e.getMessageKey());
		} catch (Exception e){
		%>
			<%@ include file="/excecao.jsp"%>
		<%
		}
	} else if (pesquisar) {
	
		entidadeDao.setEntidade(request, entidade, false);
		/* aqui devemos usar getParam, pois queremos null se o campo estiver vazio */
//		entidade.setNomeEnt(Pagina.getParam(request, "nomeEnt")); 
//		entidade.setSiglaEnt(Pagina.getParam(request, "siglaEnt")); 
//		entidade.setIndAtivoEnt(Pagina.getParam(request, "indAtivoEnt")); 
//		entidade.setCpfCnpjEnt(Pagina.getParam(request, "cpfCnpjEnt")); 
		session.setAttribute("objPesquisa", entidade);
	}
	/* da o alert para incluir, alterar, pesquisar ou excluir */
	if (msg != null)
		Mensagem.setInput(_jspx_page_context, "document.form.msgOperacao.value", msg); 
		
	if (refazPesquisa) {
		try {
			entidade = (EntidadeEnt) session.getAttribute("objPesquisa");
			List lista = entidadeDao.pesquisar(entidade);
			boolean pesquisou = (lista.size() > 0) ? true : false;
			session.setAttribute("lista", lista);
			if (pesquisou) {
				submit = "frm_nav.jsp";
				msg = null;
			} else {
				Mensagem.setInput(_jspx_page_context, "document.form.hidPesquisou.value", "false");
				submit = "frm_pes.jsp";
				msg = _msg.getMensagem("entidade.pesquisa.nenhumRegistro");
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
