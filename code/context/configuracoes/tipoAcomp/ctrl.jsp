<jsp:directive.page import="org.apache.log4j.Logger"/>
<%@ include file="/login/validaAcesso.jsp"%>
<%@ include file="/frm_global.jsp"%>

<%@ page import="ecar.dao.EstruturaDao" %>
<%@ page import="ecar.pojo.EstruturaEtt" %>
<%@ page import="ecar.pojo.TipoAcompanhamentoTa" %>
<%@ page import="ecar.dao.TipoAcompanhamentoDao" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.Iterator" %>

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
	TipoAcompanhamentoTa tipoAcomp = new TipoAcompanhamentoTa();
	TipoAcompanhamentoDao tipoAcompDao = new TipoAcompanhamentoDao(request);
	Mensagem mensagem = new Mensagem(application);
	
	String hidAcao = Pagina.getParam(request, "hidAcao");
	String msg = null;
	String submit = "frm_pes.jsp";
	
	boolean refazPesquisa = true;
	boolean incluir = "incluir".equalsIgnoreCase(hidAcao);
	boolean alterar = "alterar".equalsIgnoreCase(hidAcao);
	boolean excluir = "excluir".equalsIgnoreCase(hidAcao);
	boolean pesquisar = "pesquisar".equalsIgnoreCase(hidAcao);
	
	if (incluir) {
		try {
			tipoAcompDao.setTipoAcompanhamento(request, tipoAcomp, true);
			List listaTafc = tipoAcompDao.getTpFuncAcompanhamento(tipoAcomp, request);
			//List listaTasit = tipoAcompDao.getTipoAcompSituacaoTasit(tipoAcomp, request);
			refazPesquisa = false;
			submit = "frm_inc.jsp";
			
			tipoAcompDao.salvar(tipoAcomp, listaTafc);
			msg = _msg.getMensagem("tipoAcompanhamento.inclusao.sucesso" );
		} catch (ECARException e) {
			Logger.getLogger(this.getClass()).error(e);
			msg = _msg.getMensagem(e.getMessageKey());
		} catch (Exception e){
		%>
			<%@ include file="/excecao.jsp"%>
		<%
		}
	} else if (alterar) {
		try {
			tipoAcomp = (TipoAcompanhamentoTa) tipoAcompDao.buscar(TipoAcompanhamentoTa.class, Long.valueOf(Pagina.getParamLong(request, "codigo")));
			tipoAcompDao.setTipoAcompanhamento(request, tipoAcomp, true);
			List listaTafc = tipoAcompDao.getTpFuncAcompanhamento(tipoAcomp, request);
			//List listaTasit = tipoAcompDao.getTipoAcompSituacaoTasit(tipoAcomp, request);
			
			out.println("Descrição: "+ tipoAcomp.getDescricaoTa());
			
			tipoAcompDao.alterar(tipoAcomp, listaTafc);
			msg = _msg.getMensagem("tipoAcompanhamento.alteracao.sucesso" );
		} catch (ECARException e) {
			Logger.getLogger(this.getClass()).error(e);
			msg = _msg.getMensagem(e.getMessageKey());
		} catch (Exception e){
		%>
			<%@ include file="/excecao.jsp"%>
		<%
		}
	} else if (excluir) {
		try {
			tipoAcomp = (TipoAcompanhamentoTa) tipoAcompDao.buscar(TipoAcompanhamentoTa.class, Long.valueOf(Pagina.getParamLong(request, "codigo")));
			
			tipoAcompDao.excluir(tipoAcomp);
			msg = _msg.getMensagem("tipoAcompanhamento.exclusao.sucesso" );
		} catch (ECARException e) {
			Logger.getLogger(this.getClass()).error(e);
			msg = _msg.getMensagem(e.getMessageKey());
			
			if(e.getMessageArgs() != null) {
				for(int k = 0; k < e.getMessageArgs().length; k++) {
					msg += ": " + e.getMessageArgs()[k];
				}
			}
			
		} catch (Exception e){
		%>
			<%@ include file="/excecao.jsp"%>
		<%
		}
	} else if (pesquisar) {
	
		tipoAcompDao.setTipoAcompanhamento(request, tipoAcomp, false);
		tipoAcompDao.setTpFuncAcompanhamento(tipoAcomp, request);
		
		session.setAttribute("objPesquisa", tipoAcomp);
	}
	/* da o alert para incluir, alterar, pesquisar ou excluir */
	if (msg != null) 
		Mensagem.setInput(_jspx_page_context, "document.form.msgOperacao.value", msg); 
	
	if (refazPesquisa) {
		try {
			tipoAcomp = (TipoAcompanhamentoTa) session.getAttribute("objPesquisa");
			List lista = tipoAcompDao.pesquisar(tipoAcomp);
			boolean pesquisou = (lista.size() > 0) ? true : false;
			
			/* Percorrer os níveis para evitar problema de lazy */
			if (lista != null && lista.size() > 0) {
				Iterator itList = lista.iterator();
				while (itList.hasNext()) {
					TipoAcompanhamentoTa tipoAcompAux = (TipoAcompanhamentoTa) itList.next();
					tipoAcompAux.getSisAtributoSatbs().size();
					tipoAcompAux.getSituacaoSits().size();
				}
			}
			session.setAttribute("lista", lista);
			
			if (pesquisou) {
				submit = "frm_nav.jsp";
				msg = null;
			} else {
				Mensagem.setInput(_jspx_page_context, "document.form.hidPesquisou.value", "false");
				submit = "frm_pes.jsp";
				msg = _msg.getMensagem("tipoAcompanhamento.pesquisa.nenhumRegistro");
			}
		} catch (ECARException e) {
			Logger.getLogger(this.getClass()).error(e);
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
