<%@ include file="../../login/validaAcesso.jsp"%>
<%@ include file="../../frm_global.jsp"%>
<%@ page import="ecar.pojo.PerfilPfl" %>
<%@ page import="ecar.dao.PerfilDao" %>
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
	PerfilPfl perfil = new PerfilPfl();
	PerfilDao perfilDao = new PerfilDao(request);
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
		/* melhor usar getParamStr, pois nunca devolve null */
		perfil.setDescricaoPfl(Pagina.getParamStr(request, "descricaoPfl").trim());
		perfil.setNivelPfl(Integer.valueOf(Pagina.getParamInt(request, "nivelPfl")));
		perfil.setIndAreaReservadaPfl(Pagina.getParamStr(request, "indAreaReservadaPfl"));
		
		refazPesquisa = false;
		submit = "frm_inc.jsp";
		try {
			perfilDao.salvar(perfil);
			msg = _msg.getMensagem("perfil.inclusao.sucesso");
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
			perfil = (PerfilPfl) perfilDao.buscar(PerfilPfl.class, Long.valueOf(Pagina.getParam(request, "codigo")));
			
			perfil.setDescricaoPfl(Pagina.getParamStr(request, "descricaoPfl").trim());
			perfil.setNivelPfl(Integer.valueOf(Pagina.getParamInt(request, "nivelPfl")));
			perfil.setIndAreaReservadaPfl(Pagina.getParamStr(request, "indAreaReservadaPfl"));
			
			perfilDao.alterar(perfil);
			msg = _msg.getMensagem("perfil.alteracao.sucesso");
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
			perfilDao.excluir((PerfilPfl) perfilDao.buscar(PerfilPfl.class, Long.valueOf(Pagina.getParam(request, "codigo"))));
			msg = _msg.getMensagem("perfil.exclusao.sucesso" );
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
		perfil.setDescricaoPfl(Pagina.getParam(request, "descricaoPfl"));
		
		if (Pagina.getParam(request, "nivelPfl") != null)
			perfil.setNivelPfl(Integer.valueOf(Pagina.getParamInt(request, "nivelPfl")));
		
		perfil.setIndAreaReservadaPfl(Pagina.getParam(request, "indAreaReservadaPfl"));
		
		session.setAttribute("objPesquisa", perfil);
	}
	
	/* da o alert para incluir, alterar, pesquisar ou excluir */
	if (msg != null)
		Mensagem.setInput(_jspx_page_context, "document.form.msgOperacao.value", msg); 
		Integer.valueOf
	if (refazPesquisa) {
		try {
			perfil = (PerfilPfl) session.getAttribute("objPesquisa");
			List lista = perfilDao.pesquisar(perfil, new String[] {"descricaoPfl","asc"});
			boolean pesquisou = (lista.size() > 0) ? true : false;
			session.setAttribute("lista", lista);
			if (pesquisou) {
				submit = "frm_nav.jsp";
				msg = null;
			} else {
				Mensagem.setInput(_jspx_page_context, "document.form.hidPesquisou.value", "false");
				submit = "frm_pes.jsp";
				msg = _msg.getMensagem("perfil.pesquisa.nenhumRegistro");
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

