<%@ include file="../../login/validaAcesso.jsp"%>
<%@ include file="../../frm_global.jsp"%>
<%@ page import="ecar.pojo.BeneficiarioBnf" %>
<%@ page import="ecar.dao.BeneficiarioDao" %>
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
	BeneficiarioBnf beneficiario = new BeneficiarioBnf();
	BeneficiarioDao beneficiarioDao = new BeneficiarioDao(request);
	
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
		beneficiario.setNomeBnf(Pagina.getParamStr(request, "nomeBnf").trim()); 
		beneficiario.setIndAtivoBnf((Pagina.getParamStr(request, "indAtivoBnf"))); 
		beneficiario.setDataInclusaoBnf(Data.getDataAtual());
		refazPesquisa = false;
		submit = "frm_inc.jsp";
		try {
			beneficiarioDao.salvar(beneficiario);
			msg = _msg.getMensagem("beneficiario.inclusao.sucesso" );
			session.removeAttribute("objBeneficiario");
		} catch (ECARException e) {
			org.apache.log4j.Logger.getLogger(this.getClass()).error(e);
			session.setAttribute("objBeneficiario", beneficiario);
			msg = _msg.getMensagem(e.getMessageKey());
		} catch (Exception e){
		%>
			<%@ include file="/excecao.jsp"%>
		<%
		}
	} else if (alterar) {
		try {			/* o buscar deve estar no try, pois pode ocorrer um erro de hibernate ou bd */
			beneficiario = (BeneficiarioBnf) beneficiarioDao.buscar(BeneficiarioBnf.class, Long.valueOf(Pagina.getParamLong(request, "codigo")));
			/* altera o que foi modificado na tela */
			beneficiario.setCodBnf(Long.valueOf (Pagina.getParam(request, "codigo")));
			beneficiario.setNomeBnf((Pagina.getParamStr(request, "nomeBnf").trim())); 
			beneficiario.setIndAtivoBnf((Pagina.getParamStr(request, "indAtivoBnf"))); 
			/* altera no BD */
			beneficiarioDao.alterar(beneficiario);
			msg = _msg.getMensagem("beneficiario.alteracao.sucesso" );
			session.removeAttribute("objBeneficiario");
		} catch (ECARException e) {
			org.apache.log4j.Logger.getLogger(this.getClass()).error(e);
			refazPesquisa = false;
			submit = "frm_alt.jsp";
			session.setAttribute("objBeneficiario", beneficiario);
			msg = _msg.getMensagem(e.getMessageKey());
		} catch (Exception e){
		%>
			<%@ include file="/excecao.jsp"%>
		<%
		}
	} else if (excluir) {
		try {
			beneficiarioDao.excluir(beneficiarioDao.buscar(BeneficiarioBnf.class, Long.valueOf(Pagina.getParam(request, "codigo"))));
			msg = _msg.getMensagem("beneficiario.exclusao.sucesso" );
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
		beneficiario.setNomeBnf((Pagina.getParam(request, "nomeBnf"))); 
		beneficiario.setIndAtivoBnf((Pagina.getParam(request, "indAtivoBnf"))); 
		session.setAttribute("objPesquisa", beneficiario);
	}
	/* da o alert para incluir, alterar, pesquisar ou excluir */
	if (msg != null) 
		Mensagem.setInput(_jspx_page_context, "document.form.msgOperacao.value", msg); 

	if (refazPesquisa) {
		try {
			beneficiario = (BeneficiarioBnf) session.getAttribute("objPesquisa");
			List lista = beneficiarioDao.pesquisar(beneficiario, new String[] {"codBnf","asc"});
			boolean pesquisou = (lista.size() > 0) ? true : false;
			session.setAttribute("lista", lista);
			if (pesquisou) {
				submit = "frm_nav.jsp";
				msg = null;
			} else {
				Mensagem.setInput(_jspx_page_context, "document.form.hidPesquisou.value", "false");
				submit = "frm_pes.jsp";
				msg = _msg.getMensagem("beneficiario.pesquisa.nenhumRegistro");
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