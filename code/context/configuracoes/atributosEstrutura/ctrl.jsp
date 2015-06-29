<jsp:directive.page import="ecar.dao.SisGrupoAtributoDao"/>
<jsp:directive.page import="ecar.pojo.SisGrupoAtributoSga"/>
<%@ include file="/login/validaAcesso.jsp"%>
<%@ include file="/frm_global.jsp"%>

<%@ page import="ecar.pojo.AtributosAtb" %>
<%@ page import="ecar.dao.AtributoDao" %>
<%@ page import="java.util.List" %>

<html lang="pt-br">
<head>
<%@ include file="/include/meta.jsp"%>
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
	AtributosAtb atributo = new AtributosAtb();
	AtributoDao atributoDao = new AtributoDao(request);
	SisGrupoAtributoDao sgaDao = new SisGrupoAtributoDao(request);
	
	String hidAcao = Pagina.getParam(request, "hidAcao");
	String msg = null;
	String submit = "frm_pes.jsp";
	boolean refazPesquisa = true;
	
	if ("incluir".equalsIgnoreCase(hidAcao)) {
		/* melhor usar getParamStr, pois nunca devolve null */
		
		atributoDao.setAtributosAtb(request,atributo, true);		
		
		if(!"".equals(Pagina.getParamStr(request, "sisGrupoAtributoSga"))){
			atributo.setSisGrupoAtributoSga((SisGrupoAtributoSga)sgaDao.buscar(SisGrupoAtributoSga.class, Long.valueOf(Pagina.getParamStr(request, "sisGrupoAtributoSga"))));
		}
		
		refazPesquisa = false;
		submit = "frm_inc.jsp";
		try {
			atributoDao.salvar(atributo);
			msg = _msg.getMensagem("atributo.inclusao.sucesso" );
			session.removeAttribute("objAtributo");
		} catch (ECARException e) {
			org.apache.log4j.Logger.getLogger(this.getClass()).error(e);
			session.setAttribute("objAtributo", atributo);
			msg = _msg.getMensagem(e.getMessageKey());
		} catch (Exception e){
		%>
			<%@ include file="/excecao.jsp"%>
		<%
		}		
	} else if ("alterar".equalsIgnoreCase(hidAcao)) {
		try {			/* o buscar deve estar no try, pois pode ocorrer um erro de hibernate ou bd */
			atributo = (AtributosAtb) atributoDao.buscar(AtributosAtb.class, Long.valueOf(Pagina.getParamLong(request, "codigo")));
			atributoDao.setAtributosAtb(request, atributo, true);
			
			if(!"".equals(Pagina.getParamStr(request, "sisGrupoAtributoSga"))){
				atributo.setSisGrupoAtributoSga((SisGrupoAtributoSga)sgaDao.buscar(SisGrupoAtributoSga.class, Long.valueOf(Pagina.getParamStr(request, "sisGrupoAtributoSga"))));
			}
			else {
				atributo.setSisGrupoAtributoSga(null);
			}
			
			atributoDao.alterar(atributo);
			msg = _msg.getMensagem("atributo.alteracao.sucesso" );
			session.removeAttribute("objAtributo");
		} catch (ECARException e) {
			org.apache.log4j.Logger.getLogger(this.getClass()).error(e);
			refazPesquisa = false;
			submit = "frm_alt.jsp";
			session.setAttribute("objAtributo", atributo);
			msg = _msg.getMensagem(e.getMessageKey());
		} catch (Exception e){
		%>
			<%@ include file="/excecao.jsp"%>
		<%
		}
	} else if ("excluir".equalsIgnoreCase(hidAcao)) {
		try {
			atributoDao.excluir( (AtributosAtb) atributoDao.buscar(AtributosAtb.class, Long.valueOf(Pagina.getParam(request, "codigo"))));
			msg = _msg.getMensagem("atributo.exclusao.sucesso" );
		} catch (ECARException e) {
			org.apache.log4j.Logger.getLogger(this.getClass()).error(e);
			msg = _msg.getMensagem(e.getMessageKey());
		} catch (Exception e){
		%>
			<%@ include file="/excecao.jsp"%>
		<%
		}
	} else if ("pesquisar".equalsIgnoreCase(hidAcao)) {
		/* aqui devemos usar getParam, pois queremos null se o campo estiver vazio */
		atributoDao.setAtributosAtb(request, atributo, false);

		if(!"".equals(Pagina.getParamStr(request, "sisGrupoAtributoSga"))){
			atributo.setSisGrupoAtributoSga((SisGrupoAtributoSga)sgaDao.buscar(SisGrupoAtributoSga.class, Long.valueOf(Pagina.getParamStr(request, "sisGrupoAtributoSga"))));
		}
		else {
			atributo.setSisGrupoAtributoSga(null);
		}
		
		session.setAttribute("objPesquisa", atributo);
	}
	/* da o alert para incluir, alterar, pesquisar ou excluir */
	if (msg != null) 
		Mensagem.setInput(_jspx_page_context, "document.form.msgOperacao.value", msg); 

	if (refazPesquisa) {
		try {
			atributo = (AtributosAtb) session.getAttribute("objPesquisa");
			List lista = atributoDao.pesquisar(atributo, new String[] {"codAtb","asc"});
			boolean pesquisou = (lista.size() > 0) ? true : false;
			session.setAttribute("lista", lista);
			if (pesquisou) {
				submit = "frm_nav.jsp";
				msg = null;
			} else {
				Mensagem.setInput(_jspx_page_context, "document.form.hidPesquisou.value", "false");			
				submit = "frm_pes.jsp";
				msg = _msg.getMensagem("atributo.pesquisa.nenhumRegistro");
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
			
