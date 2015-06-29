<%@ include file="../../login/validaAcesso.jsp"%>
<%@ include file="../../frm_global.jsp"%>

<%@ page import="java.util.List" %>
<%@ page import="ecar.pojo.SegmentoCategoriaSgtc" %>
<%@ page import="ecar.dao.SegmentoCategoriaDao" %>
<%@ page import="java.util.Iterator" %>
<%@ page import="comum.util.FileUpload" %>
<%@ page import="java.util.ArrayList" %>

<html lang="pt-br">
<head>
<%@ include file="../../include/meta.jsp"%>
<%@ include file="/titulo.jsp"%>
</head>
<body>

<%
	boolean isFormUpload = FileUpload.isMultipartContent(request);
	List campos = new ArrayList();
	if(isFormUpload){
			campos = FileUpload.criaListaCampos(request); 
	}

	%>
	
	<form name="form" method="post">
    <!-- campo de controle para a navegação de registros -->
	    
    <%
    String hidRegistro = "";
    String codSegmento = "";
    if(isFormUpload){
    	hidRegistro = FileUpload.verificaValorCampo(campos, "hidRegistro");
    	codSegmento = FileUpload.verificaValorCampo(campos, "codSegmento");
	} else {
    	hidRegistro = Pagina.getParamStr(request, "hidRegistro");
    	codSegmento = Pagina.getParamStr(request, "codSegmento");
	}%>

	<input type="hidden" name="hidRegistro" value="<%=hidRegistro%>">
	<input type="hidden" name="codSegmento" value="<%=codSegmento%>">	


	<!-- campo de controle para mensagens -->
	<input type="hidden" name="msgOperacao" value=""> 
	<input type="hidden" name="msgPesquisa" value="">
	<input type="hidden" name="hidPesquisou" value="">	
		
	</form>
	
	<%

	SegmentoCategoriaSgtc segmentoCategoria = new SegmentoCategoriaSgtc();
	SegmentoCategoriaDao segmentoCategoriaDao = new SegmentoCategoriaDao(request);
	
	String hidAcao = "";
	
	if(isFormUpload)
		hidAcao = FileUpload.verificaValorCampo(campos, "hidAcao");
	else
		hidAcao = Pagina.getParamStr(request, "hidAcao");
		
	String msg = null;
	String submit = "frm_pes.jsp?codSegmento=" + codSegmento;

	/* default sempre refazer a pesquisa */
	boolean refazPesquisa = true;  

	boolean incluir = "incluir".equalsIgnoreCase(hidAcao);
	boolean alterar = "alterar".equalsIgnoreCase(hidAcao);
	boolean excluir = "excluir".equalsIgnoreCase(hidAcao);
	boolean pesquisar = "pesquisar".equalsIgnoreCase(hidAcao);
		
	/* obtem os caminhos fisicos para gravacao do arquivo a partir do arquivo de propriedades */
	String pathRaiz = configuracaoCfg.getRaizUpload();//_msg.getPathUploadRaiz("path .upload.raiz");
	String pathUpload = configuracaoCfg.getUploadAdmPortal();//_msg.getMensagem("path .upload.admPortal");
	
	if (incluir) {
		refazPesquisa = false;
		submit = "frm_inc.jsp?codSegmento=" + codSegmento;
		try {
			segmentoCategoriaDao.salvar(segmentoCategoria, campos, pathRaiz, pathUpload);
			msg = _msg.getMensagem("segmentoCategoria.inclusao.sucesso");
			session.removeAttribute("objSegmentoCategoria");
		} catch (ECARException e) {
			org.apache.log4j.Logger.getLogger(this.getClass()).error(e);
			session.setAttribute("objSegmentoCategoria", segmentoCategoria);
			msg = _msg.getMensagem(e.getMessageKey());
		} catch (Exception e){
		%>
			<%@ include file="/excecao.jsp"%>
		<%
		}
	} else if (alterar) {
		try {
			/* busca o objeto para manter o que já esta cadastrado */
			segmentoCategoria = (SegmentoCategoriaSgtc) segmentoCategoriaDao.buscar(SegmentoCategoriaSgtc.class, 
											Long.valueOf(FileUpload.verificaValorCampo(campos, "codigo")));
			segmentoCategoriaDao.alterar(segmentoCategoria, campos, pathRaiz, pathUpload);
			msg = _msg.getMensagem("segmentoCategoria.alteracao.sucesso");
			session.removeAttribute("objSegmentoCategoria");
		} catch (ECARException e) {
			org.apache.log4j.Logger.getLogger(this.getClass()).error(e);
			refazPesquisa = false;
			submit = "frm_alt.jsp?codSegmento=" + codSegmento;
			session.setAttribute("objSegmentoCategoria", segmentoCategoria);
			msg = _msg.getMensagem(e.getMessageKey());
		} catch (Exception e){
		%>
			<%@ include file="/excecao.jsp"%>
		<%
		}
	} else if (excluir) {
		try {
			/* busca e exclui o objeto do BD */
			segmentoCategoriaDao.excluir((SegmentoCategoriaSgtc)segmentoCategoriaDao.buscar(SegmentoCategoriaSgtc.class, Long.valueOf(Pagina.getParamStr(request, "codigo"))), pathRaiz);
			msg = _msg.getMensagem("segmentoCategoria.exclusao.sucesso");
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
		segmentoCategoriaDao.setSegmentoCategoria(segmentoCategoria, request, false);		
		session.setAttribute("objPesquisa", segmentoCategoria);
	}
	/* da o alert para incluir, alterar, pesquisar ou excluir */
	if (msg != null)
		Mensagem.setInput(_jspx_page_context, "document.form.msgOperacao.value", msg); 
		
	if (refazPesquisa) {
		try {
			segmentoCategoria = (SegmentoCategoriaSgtc) session.getAttribute("objPesquisa");
			List lista;
			if(segmentoCategoria.getSegmentoSgt() == null || (segmentoCategoria.getSegmentoSgt() != null && segmentoCategoria.getSegmentoSgt().getCodSgt().longValue() > 4))
				lista = segmentoCategoriaDao.pesquisarSegmentosCategoriaItensLivres(segmentoCategoria);
			else
				lista = segmentoCategoriaDao.pesquisar(segmentoCategoria);
			boolean pesquisou = (lista.size() > 0) ? true : false;
			session.setAttribute("lista", lista);
			if (pesquisou) {
				submit = "frm_nav.jsp?codSegmento=" + codSegmento;
				msg = null;
			} else {
				Mensagem.setInput(_jspx_page_context, "document.form.hidPesquisou.value", "false");
				submit = "frm_pes.jsp?codSegmento=" + codSegmento;
				msg = _msg.getMensagem("segmentoCategoria.pesquisa.nenhumRegistro");
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