<%@ include file="../../login/validaAcesso.jsp"%>
<%@ include file="../../frm_global.jsp"%>
<%@ page import="ecar.pojo.LocalItemLit" %>
<%@ page import="ecar.dao.LocalItemDao" %>
<%@ page import="ecar.pojo.LocalGrupoLgp" %>
<%@ page import="ecar.dao.LocalGrupoDao" %>
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
	<input type="hidden" name="msgOperacao" value=""> 
	<input type="hidden" name="msgPesquisa" value="">	
	<input type="hidden" name="hidPesquisou" value="">
</form>

<%
	LocalItemLit localItem = new LocalItemLit();
	LocalItemDao localItemDao = new LocalItemDao(request);
	LocalGrupoDao localGrupoDao = new LocalGrupoDao(request);
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
		try {
			/* melhor usar getParamStr, pois nunca devolve null */
			localItem.setIdentificacaoLit(Pagina.getParamStr(request, "identificacaoLit").trim());			
			localItem.setSiglaLit(Pagina.getParamStr(request,"siglaLit").trim());
			
			if(Pagina.getParam(request, "localGrupoLgp") != null)
				localItem.setLocalGrupoLgp( (LocalGrupoLgp) localGrupoDao.buscar(LocalGrupoLgp.class, Long.valueOf(Pagina.getParam(request, "localGrupoLgp"))));
			
			localItem.setCodIbgeLit(Pagina.getParamStr(request, "codIbgeLit").trim());
			localItem.setCodPlanejamentoLit(Pagina.getParamStr(request, "codPlanejamentoLit").trim());
			localItem.setIndAtivoLit(Pagina.getParamStr(request, "indAtivoLit"));
			
			localItem.setDataInclusaoLit(Data.getDataAtual());
			
			localItemDao.setAtributos(request, localItem);
			
			refazPesquisa = false;
			submit = "frm_inc.jsp";
			localItemDao.salvar(localItem);
			msg = _msg.getMensagem("localItem.inclusao.sucesso");
			session.removeAttribute("objLocalItem");
		} catch (ECARException e) {
			org.apache.log4j.Logger.getLogger(this.getClass()).error(e);
			session.setAttribute("objLocalItem", localItem);
			msg = _msg.getMensagem(e.getMessageKey());
		} catch (Exception e){
		%>
			<%@ include file="/excecao.jsp"%>
		<%
		}
	} else if (alterar) {
		try {
			/* busca o objeto para manter o que já esta cadastrado */
			localItem = (LocalItemLit) localItemDao.buscar(LocalItemLit.class, Long.valueOf(Pagina.getParam(request, "codigo")));
			
			/* melhor usar getParamStr, pois nunca devolve null */
			localItem.setIdentificacaoLit(Pagina.getParamStr(request, "identificacaoLit").trim());
			localItem.setCodIbgeLit(Pagina.getParamStr(request, "codIbgeLit").trim());
			localItem.setCodPlanejamentoLit(Pagina.getParamStr(request, "codPlanejamentoLit").trim());
			localItem.setSiglaLit(Pagina.getParamStr(request,"siglaLit").trim());
			
			if(Pagina.getParam(request, "localGrupoLgp") != null)
				localItem.setLocalGrupoLgp( (LocalGrupoLgp) localGrupoDao.buscar(LocalGrupoLgp.class, Long.valueOf(Pagina.getParam(request, "localGrupoLgp"))));
			
			localItem.setIndAtivoLit(Pagina.getParamStr(request, "indAtivoLit"));
			
			localItemDao.alterar(localItem, request);
			msg = _msg.getMensagem("localItem.alteracao.sucesso");
			session.removeAttribute("objLocalItem");
		} catch (ECARException e) {
			org.apache.log4j.Logger.getLogger(this.getClass()).error(e);
			refazPesquisa = false;
			submit = "frm_alt.jsp";
			session.setAttribute("objLocalItem", localItem);
			msg = _msg.getMensagem(e.getMessageKey());
		} catch (Exception e){
		%>
			<%@ include file="/excecao.jsp"%>
		<%
		}
	} else if (excluir) {
		try {
			localItemDao.excluir((LocalItemLit) localItemDao.buscar(LocalItemLit.class, Long.valueOf(Pagina.getParam(request, "codigo"))));
			msg = _msg.getMensagem("localItem.exclusao.sucesso" );
		} catch (ECARException e) {
			org.apache.log4j.Logger.getLogger(this.getClass()).error(e);
			msg = _msg.getMensagem(e.getMessageKey());
		} catch (Exception e){
		%>
			<%@ include file="/excecao.jsp"%>
		<%
		}
	} else if (pesquisar) {
		try {
			/* aqui devemos usar getParam, pois queremos null se o campo estiver vazio */
			localItem.setIdentificacaoLit(Pagina.getParam(request, "identificacaoLit"));
			localItem.setSiglaLit(Pagina.getParam(request, "siglaLit"));
			
			if(Pagina.getParam(request, "localGrupoLgp") != null)
				localItem.setLocalGrupoLgp( (LocalGrupoLgp) localGrupoDao.buscar(LocalGrupoLgp.class, Long.valueOf(Pagina.getParam(request, "localGrupoLgp"))));
			
			localItem.setIndAtivoLit(Pagina.getParam(request, "indAtivoLit"));
			
			localItemDao.setAtributos(request, localItem);
			
			session.setAttribute("objPesquisa", localItem);
		} catch (ECARException e) {
			org.apache.log4j.Logger.getLogger(this.getClass()).error(e);
			msg = _msg.getMensagem(e.getMessageKey());
		} catch (Exception e){
		%>
			<%@ include file="/excecao.jsp"%>
		<%
		}
	}
	
	/* da o alert para incluir, alterar, pesquisar ou excluir */
	if (msg != null)
		Mensagem.setInput(_jspx_page_context, "document.form.msgOperacao.value", msg); 
	
	if (refazPesquisa) {
		try {
			localItem = (LocalItemLit) session.getAttribute("objPesquisa");
			List lista = localItemDao.pesquisar(localItem);
			boolean pesquisou = (lista.size() > 0) ? true : false;
			session.setAttribute("lista", lista);
			if (pesquisou) {
				submit = "frm_nav.jsp";
				msg = null;
			} else {
				Mensagem.setInput(_jspx_page_context, "document.form.hidPesquisou.value", "false");
				submit = "frm_pes.jsp";
				msg = _msg.getMensagem("localItem.pesquisa.nenhumRegistro");
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

