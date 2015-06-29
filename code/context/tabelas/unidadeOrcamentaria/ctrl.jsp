<%@ include file="../../login/validaAcesso.jsp"%>

<%@ page import="ecar.pojo.UnidadeOrcamentariaUO" %>
<%@ page import="ecar.dao.UnidadeOrcamentariaDao" %>
<%@ page import="ecar.pojo.OrgaoOrg" %>
<%@ page import="ecar.pojo.UsuarioUsu" %>
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
	UnidadeOrcamentariaUO unidade = new UnidadeOrcamentariaUO();
	UnidadeOrcamentariaDao unidadeDao = new UnidadeOrcamentariaDao(request);
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
		unidade.setOrgaoOrg( (OrgaoOrg) unidadeDao.buscar(OrgaoOrg.class, Long.valueOf(Pagina.getParam(request, "orgaoOrg"))) );
		if(!"".equals(Pagina.getParamStr(request, "codigoIdentUo")))
			unidade.setCodigoIdentUo(Long.valueOf (Pagina.getParamLong(request, "codigoIdentUo")) );
		unidade.setDescricaoUo(Pagina.getParamStr(request, "descricaoUo").trim()); 
		unidade.setSiglaUo(Pagina.getParamStr(request, "siglaUo").trim());
		
		/*Luana: Usuario autorizador opcional*/
		if("".equals(Pagina.getParamStr(request, "codUsu")))
			unidade.setUsuarioUsu(null);
		else
			unidade.setUsuarioUsu( (UsuarioUsu) unidadeDao.buscar(UsuarioUsu.class, Long.valueOf(Pagina.getParam(request, "codUsu"))));
		
		
		unidade.setIndAtivoUo(Pagina.getParamStr(request, "indAtivoUo"));
		unidade.setIndTipoAdministracaoUo(Pagina.getParamStr(request, "indTipoAdmUo"));
		unidade.setIndTipoOrcamentoUo(Pagina.getParamStr(request, "indTipoOrcUo"));
		refazPesquisa = false;
		submit = "frm_inc.jsp";
		try {
			unidadeDao.salvar(unidade);
			msg = _msg.getMensagem("unidadeOrcamentaria.inclusao.sucesso");
			session.removeAttribute("objUnidadeOrcamentaria");
		} catch (ECARException e) {
			org.apache.log4j.Logger.getLogger(this.getClass()).error(e);
			session.setAttribute("objUnidadeOrcamentaria", unidade);
			msg = _msg.getMensagem(e.getMessageKey());
		} catch (Exception e){
		%>
			<%@ include file="/excecao.jsp"%>
		<%
		}
	} else if (alterar) {
		try {
			/* busca o objeto para manter o que já esta cadastrado */
			unidade = (UnidadeOrcamentariaUO) unidadeDao.buscar(UnidadeOrcamentariaUO.class, Long.valueOf(Pagina.getParam(request, "codigo")));
			/* altera o que foi modificado na tela */
			unidade.setOrgaoOrg( (OrgaoOrg) unidadeDao.buscar(OrgaoOrg.class, Long.valueOf(Pagina.getParam(request, "orgaoOrg"))) );
			if(!"".equals(Pagina.getParamStr(request, "codigoIdentUo")))
				unidade.setCodigoIdentUo(Long.valueOf (Pagina.getParamLong(request, "codigoIdentUo")) );
			else
				unidade.setCodigoIdentUo(null);
			unidade.setDescricaoUo(Pagina.getParamStr(request, "descricaoUo").trim()); 
			unidade.setSiglaUo(Pagina.getParamStr(request, "siglaUo").trim());
			
			/*Luana: Usuario autorizador opcional*/
			if("".equals(Pagina.getParamStr(request, "codUsu")))
				unidade.setUsuarioUsu(null);
			else
				unidade.setUsuarioUsu( (UsuarioUsu) unidadeDao.buscar(UsuarioUsu.class, Long.valueOf(Pagina.getParam(request, "codUsu"))));
			
			
			unidade.setIndAtivoUo(Pagina.getParamStr(request, "indAtivoUo")); 
			unidade.setIndTipoAdministracaoUo(Pagina.getParamStr(request, "indTipoAdmUo"));
			unidade.setIndTipoOrcamentoUo(Pagina.getParamStr(request, "indTipoOrcUo"));
			/* altera no BD */
			unidadeDao.alterar(unidade);
			msg = _msg.getMensagem("unidadeOrcamentaria.alteracao.sucesso");
			session.removeAttribute("objUnidadeOrcamentaria");
		} catch (ECARException e) {
			org.apache.log4j.Logger.getLogger(this.getClass()).error(e);
			refazPesquisa = false;
			submit = "frm_alt.jsp";
			session.setAttribute("objUnidadeOrcamentaria", unidade);
			msg = _msg.getMensagem(e.getMessageKey());
		} catch (Exception e){
		%>
			<%@ include file="/excecao.jsp"%>
		<%
		}
	} else if (excluir) {
		try {
			/* busca e exclui o objeto do BD */
			unidadeDao.excluir((UnidadeOrcamentariaUO) unidadeDao.buscar(UnidadeOrcamentariaUO.class, Long.valueOf(Pagina.getParam(request, "codigo"))));
			msg = _msg.getMensagem("unidadeOrcamentaria.exclusao.sucesso");
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
		if(!"".equals(Pagina.getParamStr(request, "orgaoOrg")))
			unidade.setOrgaoOrg( (OrgaoOrg) unidadeDao.buscar(OrgaoOrg.class, Long.valueOf(Pagina.getParam(request, "orgaoOrg"))) );
		if(!"".equals(Pagina.getParamStr(request, "codigoIdentUo")))
			unidade.setCodigoIdentUo(Long.valueOf (Pagina.getParamLong(request, "codigoIdentUo")) );
		unidade.setDescricaoUo(Pagina.getParam(request, "descricaoUo")); 
		unidade.setSiglaUo(Pagina.getParam(request, "siglaUo"));
		if(!"".equals(Pagina.getParamStr(request, "codUsu")))
			unidade.setUsuarioUsu( (UsuarioUsu) unidadeDao.buscar(UsuarioUsu.class, Long.valueOf(Pagina.getParam(request, "codUsu"))) );
		unidade.setIndAtivoUo(Pagina.getParam(request, "indAtivoUo")); 
		if(!"".equals(Pagina.getParamStr(request, "indTipoAdmUo")))
			unidade.setIndTipoAdministracaoUo(Pagina.getParamStr(request, "indTipoAdmUo"));
		if(!"".equals(Pagina.getParamStr(request, "indTipoOrcUo")))
			unidade.setIndTipoOrcamentoUo(Pagina.getParamStr(request, "indTipoOrcUo"));
		
		session.setAttribute("objPesquisa", unidade);
	}
	/* da o alert para incluir, alterar, pesquisar ou excluir */
	if (msg != null)
		Mensagem.setInput(_jspx_page_context, "document.form.msgOperacao.value", msg); 
	
	if (refazPesquisa) {
		try {
			unidade = (UnidadeOrcamentariaUO) session.getAttribute("objPesquisa");
			List lista = unidadeDao.pesquisar(unidade, new String[] {"descricaoUo","asc"});
			boolean pesquisou = (lista.size() > 0) ? true : false;
			session.setAttribute("lista", lista);
			if (pesquisou) {
				submit = "frm_nav.jsp";
				msg = null;
			} else {
				Mensagem.setInput(_jspx_page_context, "document.form.hidPesquisou.value", "false");
				submit = "frm_pes.jsp";
				msg = _msg.getMensagem("unidadeOrcamentaria.pesquisa.nenhumRegistro");
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
