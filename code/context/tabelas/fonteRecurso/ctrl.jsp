<%@ include file="../../login/validaAcesso.jsp"%>

<%@ include file="../../frm_global.jsp"%>
<%@ page import="ecar.pojo.RecursoRec" %>
<%@ page import="ecar.dao.RecursoDao" %>
<%@ page import="comum.util.Data" %> 
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
	RecursoRec recurso = new RecursoRec();
	RecursoDao recursoDao = new RecursoDao(request);
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
		try {
			/* melhor usar getParamStr, pois nunca devolve null */
			recurso.setNomeRec(Pagina.getParamStr(request, "nomeRec").trim());
			recurso.setSiglaRec(Pagina.getParamStr(request, "siglaRec").trim());
			recurso.setIndOrcamentoRec(Pagina.getParamStr(request, "indOrcamentoRec"));
			
			recurso.setDataIniValidadeRec( Data.parseDate(Pagina.getParamStr(request, "dataIniValidadeRec")));
			recurso.setDataFimValidadeRec( Data.parseDate(Pagina.getParamStr(request, "dataFimValidadeRec")));
			recurso.setIndAtivoRec(Pagina.getParamStr(request, "indAtivoRec"));
			recurso.setDataInclusaoRec(Data.getDataAtual());
			
			if(!"".equals(Pagina.getParamStr(request, "sequenciaRec")))
				recurso.setSequenciaRec(Integer.valueOf(Pagina.getParamStr(request, "sequenciaRec")));
			else
				recurso.setSequenciaRec(null);
			
			refazPesquisa = false;
			submit = "frm_inc.jsp";
			recursoDao.salvar(recurso);
			msg = _msg.getMensagem("recurso.inclusao.sucesso");
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
			/*Carregar o objeto para armazenar a data de inclusão*/
			recurso = (RecursoRec) recursoDao.buscar(RecursoRec.class, Long.valueOf(Pagina.getParam(request, "codigo")));
			
			/* melhor usar getParamStr, pois nunca devolve null */
			recurso.setNomeRec(Pagina.getParamStr(request, "nomeRec").trim());
			recurso.setSiglaRec(Pagina.getParamStr(request, "siglaRec").trim());
			recurso.setIndOrcamentoRec(Pagina.getParamStr(request, "indOrcamentoRec"));
			
			recurso.setDataIniValidadeRec( Data.parseDate(Pagina.getParamStr(request, "dataIniValidadeRec")));
			recurso.setDataFimValidadeRec( Data.parseDate(Pagina.getParamStr(request, "dataFimValidadeRec")));
			recurso.setIndAtivoRec(Pagina.getParamStr(request, "indAtivoRec"));

			if(!"".equals(Pagina.getParamStr(request, "sequenciaRec")))
				recurso.setSequenciaRec(Integer.valueOf(Pagina.getParamStr(request, "sequenciaRec")));
			else
				recurso.setSequenciaRec(null);
			
			recursoDao.alterar(recurso);
			msg = _msg.getMensagem("recurso.alteracao.sucesso");
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
			recursoDao.excluir((RecursoRec) recursoDao.buscar(RecursoRec.class, Long.valueOf(Pagina.getParam(request, "codigo"))));
			msg = _msg.getMensagem("recurso.exclusao.sucesso" );
		} catch (ECARException e) {
			org.apache.log4j.Logger.getLogger(this.getClass()).error(e);
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
		/* aqui devemos usar getParam, pois queremos null se o campo estiver vazio */
		recurso.setNomeRec(Pagina.getParam(request, "nomeRec"));
		recurso.setSiglaRec(Pagina.getParam(request, "siglaRec"));
		recurso.setIndOrcamentoRec(Pagina.getParam(request, "indOrcamentoRec"));
		
		if (Pagina.getParam(request, "dataIniValidadeRec") != null)
			recurso.setDataIniValidadeRec(Data.parseDate(Pagina.getParam(request, "dataIniValidadeRec")));
		
		if (Pagina.getParam(request, "dataFimValidadeRec") != null)
			recurso.setDataFimValidadeRec(Data.parseDate(Pagina.getParamStr(request, "dataFimValidadeRec")));
		
		recurso.setIndAtivoRec(Pagina.getParam(request, "indAtivoRec"));

		if(Pagina.getParam(request, "sequenciaRec") != null)
			recurso.setSequenciaRec(Integer.valueOf(Pagina.getParamStr(request, "sequenciaRec")));
		else
			recurso.setSequenciaRec(null);
			
		session.setAttribute("objPesquisa", recurso);
	}
	
	/* da o alert para incluir, alterar, pesquisar ou excluir */
	if (msg != null)
		Mensagem.setInput(_jspx_page_context, "document.form.msgOperacao.value", msg); 
		
	if (refazPesquisa) {
		try {
			recurso = (RecursoRec) session.getAttribute("objPesquisa");
			List lista = recursoDao.pesquisar(recurso, new String[] {"nomeRec","asc"});
			boolean pesquisou = (lista.size() > 0) ? true : false;
			session.setAttribute("lista", lista);
			if (pesquisou) {
				submit = "frm_nav.jsp";
				msg = null;
			} else {
				Mensagem.setInput(_jspx_page_context, "document.form.hidPesquisou.value", "false");
				submit = "frm_pes.jsp";
				msg = _msg.getMensagem("recurso.pesquisa.nenhumRegistro");
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

