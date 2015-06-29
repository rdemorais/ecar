
<%@ include file="../../login/validaAcesso.jsp"%>
<%@ include file="../../frm_global.jsp"%>

<%@ page import="ecar.dao.SegmentoItemDao" %>
<%@ page import="ecar.pojo.SegmentoItemSgti" %>
<%@ page import="ecar.dao.ConfiguracaoDao" %>

<%@ page import="comum.util.FileUpload" %>

<%@ page import="java.util.List" %>
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
	<%if(isFormUpload){%>
		<input type="hidden" name="hidRegistro" value="<%=FileUpload.verificaValorCampo(campos, "hidRegistro")%>">
	<%} else {%>
		<input type="hidden" name="hidRegistro" value="<%=Pagina.getParamStr(request, "hidRegistro")%>">	
	<%}%>
	<!-- campo de controle para as mensagens -->
	<input type="hidden" name="msgOperacao" value=""> 
	<input type="hidden" name="msgPesquisa" value="">
	<input type="hidden" name="hidPesquisou" value="">
	<!-- código da página para que depois de gravar monte ela corretamente -->
	<%if(isFormUpload){%>
		<input type="hidden" name="codSegmento" value="<%=FileUpload.verificaValorCampo(campos, "codSegmento")%>">
	<%} else {%>
		<input type="hidden" name="codSegmento" value="<%=Pagina.getParamStr(request, "codSegmento")%>">
	<%}%>
</form>

<%
	SegmentoItemSgti segItem = new SegmentoItemSgti();
	SegmentoItemDao segItemDao = new SegmentoItemDao(request);
	//ConfiguracaoDao configuracaoDao = new ConfiguracaoDao(request);
	
	String hidAcao = "";
	
	if(isFormUpload)
		hidAcao = FileUpload.verificaValorCampo(campos, "hidAcao");
	else
		hidAcao = Pagina.getParamStr(request, "hidAcao");
		
	String msg = null;
	String submit = "frm_pes.jsp";
	
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
		submit = "frm_inc.jsp";
		
		try {
			segItemDao.salvar(segItem, campos, pathRaiz, pathUpload, application);
			msg = _msg.getMensagem("segmentoItem.inclusao.sucesso");
			session.removeAttribute("objSegmentoItem");
		} catch (ECARException e) {
			org.apache.log4j.Logger.getLogger(this.getClass()).error(e);
			session.setAttribute("objSegmentoItem", segItem);
			msg = _msg.getMensagem(e.getMessageKey());
		} catch (Exception e){
		%>
			<%@ include file="/excecao.jsp"%>
		<%
		}
	} else if (alterar) {
		try {
			/* busca o objeto para manter o que já esta cadastrado */
			segItem = (SegmentoItemSgti) segItemDao.buscar(SegmentoItemSgti.class, Long.valueOf(FileUpload.verificaValorCampo(campos, "codigo")));
			segItemDao.alterar(segItem, campos, pathRaiz, pathUpload, application);
			msg = _msg.getMensagem("segmentoItem.alteracao.sucesso");
			session.removeAttribute("objSegmentoItem");
		} catch (ECARException e) {
			org.apache.log4j.Logger.getLogger(this.getClass()).error(e);
			refazPesquisa = false;
			submit = "frm_alt.jsp";
			session.setAttribute("objSegmentoItem", segItem);
			msg = _msg.getMensagem(e.getMessageKey());
		} catch (Exception e){
		%>
			<%@ include file="/excecao.jsp"%>
		<%
		}
	} else if (excluir) {
		try {
			/* busca e exclui o objeto do BD */
			segItemDao.excluir((SegmentoItemSgti)segItemDao.buscar(SegmentoItemSgti.class, Long.valueOf(Pagina.getParamStr(request, "codigo"))), pathRaiz);
			msg = _msg.getMensagem("segmentoItem.exclusao.sucesso");
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
			segItemDao.setSegmentoItem(segItem, request, false, application, true);
			session.setAttribute("objPesquisa", segItem);
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
			segItem = (SegmentoItemSgti) session.getAttribute("objPesquisa");
			List lista = segItemDao.pesquisar(segItem, application);
			boolean pesquisou = (lista.size() > 0) ? true : false;
			session.setAttribute("lista", lista);
			if (pesquisou) {
				submit = "frm_nav.jsp";
				msg = null;
			} else {
				Mensagem.setInput(_jspx_page_context, "document.form.hidPesquisou.value", "false");
				submit = "frm_pes.jsp";
				msg = _msg.getMensagem("segmentoItem.pesquisa.nenhumRegistro");
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