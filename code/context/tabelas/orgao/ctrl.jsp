
<jsp:directive.page import="ecar.pojo.PoderPod"/>
<jsp:directive.page import="ecar.dao.PoderDao"/><%@ include file="../../login/validaAcesso.jsp"%>

<%@ page import="ecar.pojo.OrgaoOrg" %>
<%@ page import="ecar.dao.OrgaoDao" %>
<%@ page import="java.util.List" %>
<%@ include file="../../frm_global.jsp"%>

<html lang="pt-br">
<head>
<%@ include file="../../include/meta.jsp"%>
<%@ include file="/titulo.jsp"%>
</head>
<body>

<form name="form" method="post">
    <!-- campo de controle para a navega��o de registros -->
	<input type="hidden" name="hidRegistro" value="<%=Pagina.getParamInt(request, "hidRegistro")%>">
	
	<!-- campo de controle para mensagens -->
	<input type="hidden" name="msgOperacao" value=""> 
	<input type="hidden" name="msgPesquisa" value="">	
	<input type="hidden" name="hidPesquisou" value="">
</form>

<%
	OrgaoOrg orgao = new OrgaoOrg();
	OrgaoDao orgaoDao = new OrgaoDao(request);
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
		if(!"".equals(Pagina.getParamStr(request, "codigoIdentOrg")))
			orgao.setCodigoIdentOrg(Long.valueOf (Pagina.getParamLong(request, "codigoIdentOrg")) );
		orgao.setDescricaoOrg(Pagina.getParamStr(request, "descricaoOrg").trim()); 
		orgao.setSiglaOrg(Pagina.getParamStr(request, "siglaOrg").trim());
		orgao.setIndAtivoOrg(Pagina.getParamStr(request, "indAtivoOrg")); 
		//
		if(!"".equals(Pagina.getParamStr(request, "codPoderPod")))			
			orgao.setPoderPod((PoderPod) new PoderDao(request).buscar(PoderPod.class, Long.valueOf(Pagina.getParamStr(request, "codPoderPod"))));
		
		refazPesquisa = false;
		submit = "frm_inc.jsp";
		try {
			orgaoDao.salvar(orgao);
			msg = _msg.getMensagem("orgao.inclusao.sucesso");
			session.removeAttribute("objOrgao");
		} catch (ECARException e) {
			org.apache.log4j.Logger.getLogger(this.getClass()).error(e);
			session.setAttribute("objOrgao", orgao);
			msg = _msg.getMensagem(e.getMessageKey());
		} catch (Exception e){
		%>
			<%@ include file="/excecao.jsp"%>
		<%
		}
	} else if (alterar) {
		try {
			/* busca o objeto para manter o que j� esta cadastrado */
			orgao = (OrgaoOrg) orgaoDao.buscar(OrgaoOrg.class, Long.valueOf(Pagina.getParam(request, "codigo")));
			/* altera o que foi modificado na tela */
			if(!"".equals(Pagina.getParamStr(request, "codigoIdentOrg")))
				orgao.setCodigoIdentOrg(Long.valueOf (Pagina.getParamLong(request, "codigoIdentOrg")) );
			else
				orgao.setCodigoIdentOrg(null);
			orgao.setDescricaoOrg(Pagina.getParamStr(request, "descricaoOrg").trim() ); 
			orgao.setSiglaOrg(Pagina.getParamStr(request, "siglaOrg").trim() );
			orgao.setIndAtivoOrg((Pagina.getParamStr(request, "indAtivoOrg"))); 
			//
			if(!"".equals(Pagina.getParamStr(request, "codPoderPod")))				
				orgao.setPoderPod((PoderPod) new PoderDao(request).buscar(PoderPod.class, Long.valueOf(Pagina.getParamStr(request, "codPoderPod"))));
			else
				orgao.setPoderPod(null);
			
			
			/* altera no BD */
			orgaoDao.alterar(orgao);
			msg = _msg.getMensagem("orgao.alteracao.sucesso");
			session.removeAttribute("objOrgao");
		} catch (ECARException e) {
			org.apache.log4j.Logger.getLogger(this.getClass()).error(e);
			refazPesquisa = false;
			submit = "frm_alt.jsp";
			session.setAttribute("objOrgao", orgao);
			msg = _msg.getMensagem(e.getMessageKey());
		} catch (Exception e){
		%>
			<%@ include file="/excecao.jsp"%>
		<%
		}
	} else if (excluir) {
		try {
			/* busca e exclui o objeto do BD */
			orgaoDao.excluir((OrgaoOrg)orgaoDao.buscar(OrgaoOrg.class, Long.valueOf(Pagina.getParam(request, "codigo"))));
			msg = _msg.getMensagem("orgao.exclusao.sucesso");
		} catch (ECARException e) {
			org.apache.log4j.Logger.getLogger(this.getClass()).error(e);
			msg = _msg.getMensagem(e.getMessageKey());
			if(e.getMessageArgs() != null) {
				for(int k = 0; k < e.getMessageArgs().length; k++) {
					msg += ": " + e.getMessageArgs()[k];
				}
			}
		} catch (Exception e){
		%>
			<%@ include file="/excecao.jsp"%>
		<%
		}
	} else if (pesquisar) {
		/* aqui devemos usar getParam, pois queremos null se o campo estiver vazio */
		if(!"".equals(Pagina.getParamStr(request, "codigoIdentOrg")))
			orgao.setCodigoIdentOrg(Long.valueOf (Pagina.getParamLong(request, "codigoIdentOrg")) );
		orgao.setDescricaoOrg((Pagina.getParam(request, "descricaoOrg"))); 
		orgao.setSiglaOrg((Pagina.getParam(request, "siglaOrg")));
		orgao.setIndAtivoOrg((Pagina.getParam(request, "indAtivoOrg")));
		
		if(!"".equals(Pagina.getParamStr(request, "codPoderPod")))			
			orgao.setPoderPod((PoderPod) new PoderDao(request).buscar(PoderPod.class, Long.valueOf(Pagina.getParamStr(request, "codPoderPod"))));
		else
			orgao.setPoderPod(null);
		
		session.setAttribute("objPesquisa", orgao);
		
	}
	/* da o alert para incluir, alterar, pesquisar ou excluir */
	if (msg != null)
		Mensagem.setInput(_jspx_page_context, "document.form.msgOperacao.value", msg); 
	
	if (refazPesquisa) {
		try {
			orgao = (OrgaoOrg) session.getAttribute("objPesquisa");
			List lista = orgaoDao.pesquisar(orgao, new String[] {"descricaoOrg","asc"});
			boolean pesquisou = (lista.size() > 0) ? true : false;
			session.setAttribute("lista", lista);
			if (pesquisou) {
				submit = "frm_nav.jsp";
				msg = null;
			} else {
				Mensagem.setInput(_jspx_page_context, "document.form.hidPesquisou.value", "false");
				submit = "frm_pes.jsp";
				msg = _msg.getMensagem("orgao.pesquisa.nenhumRegistro");
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


