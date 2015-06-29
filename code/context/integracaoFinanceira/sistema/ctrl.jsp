<%@ include file="../../login/validaAcesso.jsp"%>

<%@ include file="../../frm_global.jsp"%>

<%@ page import="ecar.pojo.ConfigSisExecFinanCsef" %>
<%@ page import="ecar.dao.ConfigSisExecFinanDao" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.Date" %>
<%@ page import="java.text.DecimalFormat" %>

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
	ConfigSisExecFinanCsef consef = new ConfigSisExecFinanCsef();
	ConfigSisExecFinanDao consefDao = new ConfigSisExecFinanDao(request);
	
	
	String hidAcao = Pagina.getParamStr(request, "hidAcao");
	String msg = null;
	String submit = "frm_pes.jsp";
	boolean refazPesquisa = true;
	boolean incluir = "incluir".equalsIgnoreCase(hidAcao);
	boolean alterar = "alterar".equalsIgnoreCase(hidAcao);
	boolean excluir = "excluir".equalsIgnoreCase(hidAcao);
	boolean pesquisar = "pesquisar".equalsIgnoreCase(hidAcao);
	
	if (incluir) {
		/* melhor usar getParamStr, pois nunca devolve null */	

		consef.setNomeCsef(Pagina.getParamStr(request, "nome").trim());		
		consef.setSiglaCsef(Pagina.getParamStr(request, "sigla").trim());		
		consef.setIndPermiteValormanualorcCsef(Pagina.getParamStr(request, "valorManual").trim());
		consef.setIndAtivoCsef(Pagina.getParamStr(request, "ativo").trim());
		consef.setDataInclusaoCsef(new Date());

		refazPesquisa = false;
		submit = "frm_inc.jsp";
		try {

			consefDao.salvar(consef);
			msg = _msg.getMensagem("integracaoFinanceira.sistema.inclusao.sucesso");			
			session.removeAttribute("objConsef");

		} catch (ECARException e) {
			org.apache.log4j.Logger.getLogger(this.getClass()).error(e);
			session.setAttribute("objConsef", consef);
			msg = _msg.getMensagem(e.getMessageKey());
		} catch (Exception e){
		%>
			<%@ include file="/excecao.jsp"%>
		<%
		}
	} else if (alterar) {
		try {			/* o buscar deve estar no try, pois pode ocorrer um erro de hibernate ou bd */
		
			consef = (ConfigSisExecFinanCsef) consefDao.buscar(ConfigSisExecFinanCsef.class, Long.valueOf(Pagina.getParamLong(request, "codigo")));
			consef.setNomeCsef(Pagina.getParamStr(request, "nome").trim());		
			consef.setSiglaCsef(Pagina.getParamStr(request, "sigla").trim());		
			consef.setIndPermiteValormanualorcCsef(Pagina.getParamStr(request, "valorManual").trim());
			consef.setIndAtivoCsef(Pagina.getParamStr(request, "ativo").trim());
			
			consefDao.alterar(consef);
			msg = _msg.getMensagem("integracaoFinanceira.sistema.alteracao.sucesso");
		} catch (ECARException e) {
			org.apache.log4j.Logger.getLogger(this.getClass()).error(e);
			refazPesquisa = false;
			submit = "frm_alt.jsp";
			session.setAttribute("objConsef", consef);
			msg = _msg.getMensagem(e.getMessageKey());
		} catch (Exception e){
		%>
			<%@ include file="/excecao.jsp"%>
		<%
		}
	} else if (excluir) {
		try {
			consefDao.excluir( (ConfigSisExecFinanCsef) consefDao.buscar(ConfigSisExecFinanCsef.class, Long.valueOf(Pagina.getParam(request, "codigo"))));
			msg = _msg.getMensagem("integracaoFinanceira.sistema.exclusao.sucesso");
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
		
		
		if (!Pagina.getParamStr(request, "nome").trim().equals("")) {
			consef.setNomeCsef(Pagina.getParamStr(request, "nome").trim());
		}
		
		if (!Pagina.getParamStr(request, "sigla").trim().equals("")) {
			consef.setSiglaCsef(Pagina.getParamStr(request, "sigla").trim());
		}

		if (!Pagina.getParamStr(request, "valorManual").trim().equals("")) {
			consef.setIndPermiteValormanualorcCsef(Pagina.getParamStr(request, "valorManual").trim());
		}
		if (!Pagina.getParamStr(request, "ativo").trim().equals("")) {		
			consef.setIndAtivoCsef(Pagina.getParamStr(request, "ativo").trim());
		}

		session.setAttribute("objPesquisa", consef);
	}
	/* da o alert para incluir, alterar, pesquisar ou excluir */
	if (msg != null) 
		Mensagem.setInput(_jspx_page_context, "document.form.msgOperacao.value", msg); 
	
	if (refazPesquisa) {
		try {
			consef = (ConfigSisExecFinanCsef) session.getAttribute("objPesquisa");
			List lista = consefDao.pesquisar(consef, new String[] {"siglaCsef","asc"});
			boolean pesquisou = (lista.size() > 0) ? true : false;
			session.setAttribute("lista", lista);
			if (pesquisou) {
				submit = "frm_nav.jsp";
				msg = null;
			} else {
				Mensagem.setInput(_jspx_page_context, "document.form.hidPesquisou.value", "false");
				submit = "frm_pes.jsp";
				msg = _msg.getMensagem("integracaoFinanceira.sistema.pesquisar.nenhumRegistro");
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

