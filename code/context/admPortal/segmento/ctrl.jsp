<%@ include file="../../login/validaAcesso.jsp"%>
<%@ include file="../../frm_global.jsp"%>

<%@ page import="java.util.List" %>
<%@ page import="ecar.pojo.SegmentoSgt" %>
<%@ page import="ecar.dao.SegmentoDao" %>
<%@ page import="java.util.Iterator" %>
<%@ page import="comum.util.FileUpload" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="ecar.pojo.ConfiguracaoCfg" %>

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
    
    <%if(isFormUpload){%>
		<input type="hidden" name="hidRegistro" value="<%=FileUpload.verificaValorCampo(campos, "hidRegistro")%>">
	<%} else {%>
		<input type="hidden" name="hidRegistro" value="<%=Pagina.getParamStr(request, "hidRegistro")%>">	
	<%}%>
	<!-- campo de controle para mensagens -->
	<input type="hidden" name="msgOperacao" value=""> 
	<input type="hidden" name="msgPesquisa" value="">
	<input type="hidden" name="hidPesquisou" value="">	
	
	</form>
	
	<%

	SegmentoSgt segmento = new SegmentoSgt();
	SegmentoDao segmentoDao = new SegmentoDao(request);
	
	String hidAcao = "";
	
	if(isFormUpload)
		hidAcao = FileUpload.verificaValorCampo(campos, "hidAcao");
	else
		hidAcao = Pagina.getParamStr(request, "hidAcao");
		
	String msg = null;
	String submit = "frm_pes.jsp";

	/* default sempre refazer a pesquisa */
	boolean refazPesquisa = true;  

	boolean incluir = "incluir".equalsIgnoreCase(hidAcao);
	boolean alterar = "alterar".equalsIgnoreCase(hidAcao);
	boolean excluir = "excluir".equalsIgnoreCase(hidAcao);
	boolean pesquisar = "pesquisar".equalsIgnoreCase(hidAcao);
		
	/* obtem os caminhos fisicos para gravacao do arquivo a partir do arquivo de propriedades */
	ConfiguracaoCfg configura = (ConfiguracaoCfg)session.getAttribute("configuracao");
	String pathRaiz = configura.getRaizUpload();//_msg.getPathUploadRaiz("path .upload.raiz");
	String pathUpload = configura.getUploadAdmPortal();//_msg.getMensagem("path .upload.admPortal");
	
	if (incluir) {
		refazPesquisa = false;
		submit = "frm_inc.jsp";
		try {
			segmentoDao.salvar(segmento, campos, pathRaiz, pathUpload);
			msg = _msg.getMensagem("segmento.inclusao.sucesso");
			session.removeAttribute("objSegmento");
		} catch (ECARException e) {
			org.apache.log4j.Logger.getLogger(this.getClass()).error(e);
			session.setAttribute("objSegmento", segmento);
			msg = _msg.getMensagem(e.getMessageKey());
		} catch (Exception e){
		%>
			<%@ include file="/excecao.jsp"%>
		<%
		}
	} else if (alterar) {
		try {
			/* busca o objeto para manter o que já esta cadastrado */
			segmento = (SegmentoSgt) segmentoDao.buscar(SegmentoSgt.class, Long.valueOf(FileUpload.verificaValorCampo(campos, "codigo")));
			segmentoDao.alterar(segmento, campos, pathRaiz, pathUpload);
			msg = _msg.getMensagem("segmento.alteracao.sucesso");
			session.removeAttribute("objSegmento");
		} catch (ECARException e) {
			refazPesquisa = false;
			submit = "frm_alt.jsp";
			session.setAttribute("objSegmento", segmento);
			msg = _msg.getMensagem(e.getMessageKey());
		} catch (Exception e){
		%>
			<%@ include file="/excecao.jsp"%>
		<%
		}
	} else if (excluir) {
		try {
			/* busca e exclui o objeto do BD */
			segmentoDao.excluir((SegmentoSgt)segmentoDao.buscar(SegmentoSgt.class, Long.valueOf(Pagina.getParamStr(request, "codigo"))), pathRaiz);
			msg = _msg.getMensagem("segmento.exclusao.sucesso");
		} catch (ECARException e) {
			msg = _msg.getMensagem(e.getMessageKey());
		} catch (Exception e){
		%>
			<%@ include file="/excecao.jsp"%>
		<%
		}
	} else if (pesquisar) {
		/* aqui devemos usar getParam, pois queremos null se o campo estiver vazio */
		segmentoDao.setSegmento(segmento, request, false);		
		session.setAttribute("objPesquisa", segmento);
	}
	/* da o alert para incluir, alterar, pesquisar ou excluir */
	if (msg != null)
		Mensagem.setInput(_jspx_page_context, "document.form.msgOperacao.value", msg); 
		
	if (refazPesquisa) {
		try {
			segmento = (SegmentoSgt) session.getAttribute("objPesquisa");
			List lista = segmentoDao.pesquisar(segmento, application);
			boolean pesquisou = (lista.size() > 0) ? true : false;
			session.setAttribute("lista", lista);
			if (pesquisou) {
				submit = "frm_nav.jsp";
				msg = null;
			} else {
				Mensagem.setInput(_jspx_page_context, "document.form.hidPesquisou.value", "false");
				submit = "frm_pes.jsp";
				msg = _msg.getMensagem("segmento.pesquisa.nenhumRegistro");
			}
		} catch (ECARException e) {
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