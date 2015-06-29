<%@ include file="../../login/validaAcesso.jsp"%>
<%@ page import="ecar.pojo.AreaAre" %>
<%@ page import="ecar.dao.AreaDao" %>
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
	AreaAre area = new AreaAre();
	AreaDao areaDao = new AreaDao(request);
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
		if(!"".equals(Pagina.getParamStr(request, "codigoIdentAre")))
			area.setCodigoIdentAre(Long.valueOf (Pagina.getParamLong(request, "codigoIdentAre")) );
		area.setNomeAre(Pagina.getParamStr(request, "nomeAre").trim()); 
		area.setIndAtivoAre(Pagina.getParamStr(request, "indAtivoAre").trim()); 
		area.setDataInclusaoAre(Data.getDataAtual());
		refazPesquisa = false;
		submit = "frm_inc.jsp";
		try {
			areaDao.salvar(area);
			msg = _msg.getMensagem("area.inclusao.sucesso");
			session.removeAttribute("objArea");
		} catch (ECARException e) {
			org.apache.log4j.Logger.getLogger(this.getClass()).error(e);
			session.setAttribute("objArea", area);
			msg = _msg.getMensagem(e.getMessageKey());
		} catch (Exception e){
		%>
			<%@ include file="/excecao.jsp"%>
		<%
		}
	} else if (alterar) {
		try {
			/* busca o objeto para manter o que já esta cadastrado */
			area = (AreaAre) areaDao.buscar(AreaAre.class, Long.valueOf(Pagina.getParam(request, "codigo")));
			/* altera o que foi modificado na tela */
			if(!"".equals(Pagina.getParamStr(request, "codigoIdentAre")))
				area.setCodigoIdentAre(Long.valueOf (Pagina.getParamLong(request, "codigoIdentAre")) );
			else
				area.setCodigoIdentAre(null);
			area.setNomeAre(Pagina.getParamStr(request, "nomeAre").trim()); 
			area.setIndAtivoAre(Pagina.getParamStr(request, "indAtivoAre").trim()); 
			/* altera no BD */
			areaDao.alterar(area);
			msg = _msg.getMensagem("area.alteracao.sucesso");
			session.removeAttribute("objArea");
		} catch (ECARException e) {
			org.apache.log4j.Logger.getLogger(this.getClass()).error(e);
			refazPesquisa = false;
			submit = "frm_alt.jsp";
			session.setAttribute("objArea", area);
			msg = _msg.getMensagem(e.getMessageKey());
		} catch (Exception e){
		%>
			<%@ include file="/excecao.jsp"%>
		<%
		}
	} else if (excluir) {
		try {
			/* busca e exclui o objeto do BD */
			areaDao.excluir((AreaAre)areaDao.buscar(AreaAre.class, Long.valueOf(Pagina.getParam(request, "codigo"))));
			msg = _msg.getMensagem("area.exclusao.sucesso");
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
		if(!"".equals(Pagina.getParamStr(request, "codigoIdentAre")))
			area.setCodigoIdentAre(Long.valueOf (Pagina.getParamLong(request, "codigoIdentAre")) );
		area.setNomeAre((Pagina.getParam(request, "nomeAre"))); 
		area.setIndAtivoAre((Pagina.getParam(request, "indAtivoAre"))); 
		session.setAttribute("objPesquisa", area);
	}
	/* da o alert para incluir, alterar, pesquisar ou excluir */
	if (msg != null)
		Mensagem.setInput(_jspx_page_context, "document.form.msgOperacao.value", msg); 
		
	if (refazPesquisa) {
		try {
			area = (AreaAre) session.getAttribute("objPesquisa");
			List lista = areaDao.pesquisar(area, new String[] {"nomeAre","asc"});
			boolean pesquisou = (lista.size() > 0) ? true : false;
			session.setAttribute("lista", lista);
			if (pesquisou) {
				submit = "frm_nav.jsp";
				msg = null;
			} else {
				Mensagem.setInput(_jspx_page_context, "document.form.hidPesquisou.value", "false");
				submit = "frm_pes.jsp";
				msg = _msg.getMensagem("area.pesquisa.nenhumRegistro");
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

