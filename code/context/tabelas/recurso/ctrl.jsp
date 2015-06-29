<%@ include file="../../login/validaAcesso.jsp"%>

<%@ include file="../../frm_global.jsp"%>
<%@ page import="ecar.pojo.FonteRecursoFonr" %>
<%@ page import="ecar.dao.FonteRecursoDao" %>
<%@ page import="ecar.pojo.ConfigSisExecFinanCsef" %>
<%@ page import="ecar.dao.ConfigSisExecFinanDao" %>
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
	FonteRecursoFonr fonteRecurso = new FonteRecursoFonr();
	FonteRecursoDao fonteRecursoDao = new FonteRecursoDao(request);
	ConfigSisExecFinanDao configSisExecFinanDao = new ConfigSisExecFinanDao(request);
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
		refazPesquisa = false;
		submit = "frm_inc.jsp";
		/* melhor usar getParamStr, pois nunca devolve null */
		fonteRecurso.setNomeFonr(Pagina.getParamStr(request, "nomeFonr").trim());
		fonteRecurso.setIndOrcamentoFonr(Pagina.getParamStr(request, "indOrcamentoFonr"));
		fonteRecurso.setDataIniValidadeFonr( Data.parseDate(Pagina.getParamStr(request, "dataIniValidadeFonr")));
		fonteRecurso.setDataFimValidadeFonr( Data.parseDate(Pagina.getParamStr(request, "dataFimValidadeFonr")));
		fonteRecurso.setIndAtivoFonr(Pagina.getParamStr(request, "indAtivoFonr"));
		fonteRecurso.setDataInclusaoFonr(Data.getDataAtual());
		fonteRecurso.setSiglaFonr(Pagina.getParamStr(request, "siglaFonr"));

		if(!"".equals(Pagina.getParamStr(request, "sequenciaFonr")))
				fonteRecurso.setSequenciaFonr(Integer.valueOf(Pagina.getParamStr(request, "sequenciaFonr")));
			else
				fonteRecurso.setSequenciaFonr(null);
			
		try {
			if(Pagina.getParam(request, "configSisExecFinanCsef") != null)
				fonteRecurso.setConfigSisExecFinanCsef( (ConfigSisExecFinanCsef) configSisExecFinanDao.buscar(ConfigSisExecFinanCsef.class, Long.valueOf(Pagina.getParam(request, "configSisExecFinanCsef"))));
			fonteRecursoDao.salvar(fonteRecurso);
			msg = _msg.getMensagem("fonteRecurso.inclusao.sucesso");
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
			fonteRecurso = (FonteRecursoFonr) fonteRecursoDao.buscar(FonteRecursoFonr.class, Long.valueOf(Pagina.getParam(request, "codigo")));
			
			/* melhor usar getParamStr, pois nunca devolve null */
			fonteRecurso.setNomeFonr(Pagina.getParamStr(request, "nomeFonr").trim());
			fonteRecurso.setIndOrcamentoFonr(Pagina.getParamStr(request, "indOrcamentoFonr"));
			
			if(Pagina.getParam(request, "configSisExecFinanCsef") != null)
				fonteRecurso.setConfigSisExecFinanCsef( (ConfigSisExecFinanCsef) configSisExecFinanDao.buscar(ConfigSisExecFinanCsef.class, Long.valueOf(Pagina.getParam(request, "configSisExecFinanCsef"))));
			
			fonteRecurso.setDataIniValidadeFonr( Data.parseDate(Pagina.getParamStr(request, "dataIniValidadeFonr")));
			fonteRecurso.setDataFimValidadeFonr( Data.parseDate(Pagina.getParamStr(request, "dataFimValidadeFonr")));
			fonteRecurso.setIndAtivoFonr(Pagina.getParamStr(request, "indAtivoFonr"));
			fonteRecurso.setSiglaFonr(Pagina.getParamStr(request, "siglaFonr"));
			
			if(!"".equals(Pagina.getParamStr(request, "sequenciaFonr")))
				fonteRecurso.setSequenciaFonr(Integer.valueOf(Pagina.getParamStr(request, "sequenciaFonr")));
			else
				fonteRecurso.setSequenciaFonr(null);
			
			fonteRecursoDao.alterar(fonteRecurso);
			msg = _msg.getMensagem("fonteRecurso.alteracao.sucesso");
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
			fonteRecursoDao.excluir((FonteRecursoFonr) fonteRecursoDao.buscar(FonteRecursoFonr.class, Long.valueOf(Pagina.getParam(request, "codigo"))));
			msg = _msg.getMensagem("fonteRecurso.exclusao.sucesso" );
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
		try {
			/* aqui devemos usar getParam, pois queremos null se o campo estiver vazio */
			fonteRecurso.setNomeFonr(Pagina.getParam(request, "nomeFonr"));
			fonteRecurso.setIndOrcamentoFonr(Pagina.getParam(request, "indOrcamentoFonr"));
			
			if(Pagina.getParam(request, "configSisExecFinanCsef") != null)
				fonteRecurso.setConfigSisExecFinanCsef( (ConfigSisExecFinanCsef) configSisExecFinanDao.buscar(ConfigSisExecFinanCsef.class, Long.valueOf(Pagina.getParam(request, "configSisExecFinanCsef"))));
			
			if (Pagina.getParam(request, "dataIniValidadeFonr") != null)
				fonteRecurso.setDataIniValidadeFonr(Data.parseDate(Pagina.getParam(request, "dataIniValidadeFonr")));
			
			if (Pagina.getParam(request, "dataFimValidadeFonr") != null)
				fonteRecurso.setDataFimValidadeFonr(Data.parseDate(Pagina.getParamStr(request, "dataFimValidadeFonr")));
			
			fonteRecurso.setIndAtivoFonr(Pagina.getParam(request, "indAtivoFonr"));
			
			fonteRecurso.setSiglaFonr(Pagina.getParam(request, "siglaFonr"));
			
			if (Pagina.getParam(request, "sequenciaFonr") != null)
				fonteRecurso.setSequenciaFonr(Integer.valueOf(Pagina.getParam(request, "sequenciaFonr")));

			session.setAttribute("objPesquisa", fonteRecurso);
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
			fonteRecurso = (FonteRecursoFonr) session.getAttribute("objPesquisa");
			List lista = fonteRecursoDao.pesquisar(fonteRecurso, new String[] {"nomeFonr","asc"});
			boolean pesquisou = (lista.size() > 0) ? true : false;
			session.setAttribute("lista", lista);
			if (pesquisou) {
				submit = "frm_nav.jsp";
				msg = null;
			} else {
				Mensagem.setInput(_jspx_page_context, "document.form.hidPesquisou.value", "false");
				submit = "frm_pes.jsp";
				msg = _msg.getMensagem("fonteRecurso.pesquisa.nenhumRegistro");
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

