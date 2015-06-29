<%@ include file="../../login/validaAcesso.jsp"%>
<%@ include file="../../frm_global.jsp"%>

<%@ page import="ecar.pojo.SegmentoItemFonteSgtif" %>
<%@ page import="ecar.dao.SegmentoItemFonteDao" %>
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
	
	<!-- campo de controle para mensagens -->
	<input type="hidden" name="msgOperacao" value=""> 
	<input type="hidden" name="msgPesquisa" value="">
	<input type="hidden" name="hidPesquisou" value="">	
	
</form>

<%
	SegmentoItemFonteSgtif fonte = new SegmentoItemFonteSgtif();
	SegmentoItemFonteDao fonteDao = new SegmentoItemFonteDao(request);
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
		fonte.setDescricaoSgtif(Pagina.getParamStr(request, "descricaoSgtif").trim()); 
		refazPesquisa = false;
		submit = "frm_inc.jsp";
		try {
			fonteDao.salvar(fonte);
			msg = _msg.getMensagem("fonte.inclusao.sucesso");
			session.removeAttribute("objFonte");
		} catch (ECARException e) {
			org.apache.log4j.Logger.getLogger(this.getClass()).error(e);
			session.setAttribute("objFonte", fonte);
			msg = _msg.getMensagem(e.getMessageKey());
		} catch (Exception e){
		%>
			<%@ include file="/excecao.jsp"%>
		<%
		}
	} else if (alterar) {
		try {
			/* busca o objeto para manter o que já esta cadastrado */
			fonte = (SegmentoItemFonteSgtif) fonteDao.buscar(SegmentoItemFonteSgtif.class, Long.valueOf(Pagina.getParam(request, "codigo")));
			/* altera o que foi modificado na tela */
			fonte.setDescricaoSgtif(Pagina.getParamStr(request, "descricaoSgtif").trim()); 			
			/* altera no BD */
			fonteDao.alterar(fonte);
			msg = _msg.getMensagem("fonte.alteracao.sucesso");
			session.removeAttribute("objFonte");
		} catch (ECARException e) {
			org.apache.log4j.Logger.getLogger(this.getClass()).error(e);
			refazPesquisa = false;
			submit = "frm_alt.jsp";
			session.setAttribute("objFonte", fonte);
			msg = _msg.getMensagem(e.getMessageKey());
		} catch (Exception e){
		%>
			<%@ include file="/excecao.jsp"%>
		<%
		}
	} else if (excluir) {
		try {
			/* busca e exclui o objeto do BD */
			fonteDao.excluir((SegmentoItemFonteSgtif)fonteDao.buscar(SegmentoItemFonteSgtif.class, Long.valueOf(Pagina.getParam(request, "codigo"))));
			msg = _msg.getMensagem("fonte.exclusao.sucesso");
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
		fonte.setDescricaoSgtif(Pagina.getParam(request, "descricaoSgtif")); 					
		session.setAttribute("objPesquisa", fonte);
	}
	/* da o alert para incluir, alterar, pesquisar ou excluir */
	if (msg != null)
		Mensagem.setInput(_jspx_page_context, "document.form.msgOperacao.value", msg); 
		
	if (refazPesquisa) {
		try {
			fonte = (SegmentoItemFonteSgtif) session.getAttribute("objPesquisa");
			List lista = fonteDao.pesquisar(fonte, new String[] {"descricaoSgtif","asc"});
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

