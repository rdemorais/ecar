<%@ include file="../../login/validaAcesso.jsp"%>
<%@ include file="../../frm_global.jsp"%>

<%@ page import="java.util.List" %>
<%@ page import="ecar.pojo.SisAtributoSatb" %>
<%@ page import="ecar.pojo.SegmentoSgt" %>
<%@ page import="ecar.dao.SisAtributoDao" %>
<%@ page import="ecar.dao.SegmentoDao" %>
<%@ page import="java.util.Iterator" %>
<%@ page import="java.util.ArrayList" %>

<html lang="pt-br">
<head>
<%@ include file="../../include/meta.jsp"%>
<%@ include file="/titulo.jsp"%>
</head>
<body>
	
	<form name="form" method="post">
    <!-- campo de controle para a navegação de registros -->
	
	<input type="hidden" name="hidRegistro" value="<%=Pagina.getParamStr(request, "hidRegistro")%>">
	<input type="hidden" name="codSegmento" value="<%=Pagina.getParamStr(request, "codSegmento")%>">	


	<!-- campo de controle para mensagens -->
	<input type="hidden" name="msgOperacao" value=""> 
	<input type="hidden" name="msgPesquisa" value="">
	<input type="hidden" name="hidPesquisou" value="">	
		
	</form>
	
	<%
	
	String codSegmento = Pagina.getParamStr(request, "codSegmento");

	SisAtributoSatb editoria = new SisAtributoSatb();
	SisAtributoDao editoriaDao = new SisAtributoDao(request);
	
	String hidAcao = Pagina.getParamStr(request, "hidAcao");
		
	String msg = null;
	String submit = "frm_pes.jsp?codSegmento=" + codSegmento;

	/* default sempre refazer a pesquisa */
	boolean refazPesquisa = true;  

	boolean incluir = "incluir".equalsIgnoreCase(hidAcao);
	boolean alterar = "alterar".equalsIgnoreCase(hidAcao);
	boolean excluir = "excluir".equalsIgnoreCase(hidAcao);
	boolean pesquisar = "pesquisar".equalsIgnoreCase(hidAcao);
		
	if (incluir) {
		refazPesquisa = false;
		submit = "frm_inc.jsp?codSegmento=" + codSegmento;
		try {
			SegmentoSgt segmento = (SegmentoSgt) new SegmentoDao(request).buscar(SegmentoSgt.class, Long.valueOf(codSegmento));
			editoriaDao.setEditorias(editoria, segmento, request, true);
			editoriaDao.salvar(editoria);
			msg = _msg.getMensagem("editoria.inclusao.sucesso");
			session.removeAttribute("objEditoria");
		} catch (ECARException e) {
			org.apache.log4j.Logger.getLogger(this.getClass()).error(e);
			session.setAttribute("objEditoria", editoria);
			msg = _msg.getMensagem(e.getMessageKey());
		} catch (Exception e){
		%>
			<%@ include file="/excecao.jsp"%>
		<%
		}
	} else if (alterar) {
		try {
			SegmentoSgt segmento = (SegmentoSgt) new SegmentoDao(request).buscar(SegmentoSgt.class, Long.valueOf(codSegmento));
			/* busca o objeto para manter o que já esta cadastrado */
			editoria = (SisAtributoSatb) editoriaDao.buscar(SisAtributoSatb.class, 
											Long.valueOf(Pagina.getParamStr(request, "codigo")));
			editoriaDao.setEditorias(editoria, segmento, request, true);											
			editoriaDao.alterar(editoria);	
			msg = _msg.getMensagem("editoria.alteracao.sucesso");
			session.removeAttribute("objEditoria");
		} catch (ECARException e) {
			org.apache.log4j.Logger.getLogger(this.getClass()).error(e);
			refazPesquisa = false;
			submit = "frm_alt.jsp?codSegmento=" + codSegmento;
			session.setAttribute("objEditoria", editoria);
			msg = _msg.getMensagem(e.getMessageKey());
		} catch (Exception e){
		%>
			<%@ include file="/excecao.jsp"%>
		<%
		}
	} else if (excluir) {
		try {
			/* busca e exclui o objeto do BD */
			editoriaDao.excluir((SisAtributoSatb)editoriaDao.buscar(SisAtributoSatb.class, Long.valueOf(Pagina.getParamStr(request, "codigo"))));
			msg = _msg.getMensagem("editoria.exclusao.sucesso");
		} catch (ECARException e) {
			org.apache.log4j.Logger.getLogger(this.getClass()).error(e);
			msg = _msg.getMensagem(e.getMessageKey());
		} catch (Exception e){
		%>
			<%@ include file="/excecao.jsp"%>
		<%
		}
	} else if (pesquisar) {
		SegmentoSgt segmento = (SegmentoSgt) new SegmentoDao(request).buscar(SegmentoSgt.class, Long.valueOf(codSegmento));
		editoriaDao.setEditorias(editoria, segmento, request, false);		
		session.setAttribute("objPesquisa", editoria);
	}
	/* da o alert para incluir, alterar, pesquisar ou excluir */
	if (msg != null)
		Mensagem.setInput(_jspx_page_context, "document.form.msgOperacao.value", msg); 
		
	if (refazPesquisa) {
		try {
			editoria = (SisAtributoSatb) session.getAttribute("objPesquisa");
			List lista = new ArrayList();
			SegmentoSgt segmento = (SegmentoSgt) new SegmentoDao(request).buscar(SegmentoSgt.class, Long.valueOf(codSegmento));			
			lista = editoriaDao.pesquisarEditoriasBySegmento(editoria, segmento);

			boolean pesquisou = (lista.size() > 0) ? true : false;
			session.setAttribute("lista", lista);
			if (pesquisou) {
				submit = "frm_nav.jsp?codSegmento=" + codSegmento;
				msg = null;
			} else {
				Mensagem.setInput(_jspx_page_context, "document.form.hidPesquisou.value", "false");
				submit = "frm_pes.jsp?codSegmento=" + codSegmento;
				msg = _msg.getMensagem("editoria.pesquisa.nenhumRegistro");
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