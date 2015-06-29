<%@ include file="../../login/validaAcesso.jsp"%>

<%@ page import="ecar.pojo.ConfigExecFinanCef" %>
<%@ page import="ecar.dao.ConfigExecFinanDao" %>
<%@ page import="ecar.pojo.ConfigTipoDadoCtd" %>
<%@ page import="ecar.dao.ConfigTipoDadoDao" %>
<%@ page import="comum.util.Data" %>
<%@ page import="java.util.List" %>
<%@ page import="ecar.dao.ConfigSisExecFinanCsefvDao" %>
<%@ page import="ecar.pojo.ConfigSisExecFinanCsefv" %>
<%@ include file="../../frm_global.jsp" %>
 
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
	ConfigExecFinanCef configExecFinan = new ConfigExecFinanCef();
	ConfigExecFinanDao configExecFinanDao = new ConfigExecFinanDao(request);
	ConfigTipoDadoDao configTipoDadoDao = new ConfigTipoDadoDao(request);
	ConfigSisExecFinanCsefvDao configSisExecFinanCsefvDao = new ConfigSisExecFinanCsefvDao(request);
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
		configExecFinan.setNomeCef(Pagina.getParamStr(request, "nomeCef").trim()); 
		configExecFinan.setLabelCef(Pagina.getParamStr(request, "labelCef").trim()); 
		configExecFinan.setNumCaracteresCef(Integer.valueOf(Pagina.getParamStr(request, "numCaracteresCef").trim()));
		configExecFinan.setSeqApresentacaoCef(Integer.valueOf(Pagina.getParamStr(request, "seqApresentacaoCef").trim()));
		configExecFinan.setIdXmlCef(Pagina.getParamStr(request, "idXmlCef").trim());
		refazPesquisa = false;
		submit = "frm_inc.jsp";
		
		try {
			if(Pagina.getParam(request, "configTipoDadoCtd") != null)
				configExecFinan.setConfigTipoDadoCtd((ConfigTipoDadoCtd) configTipoDadoDao.buscar(ConfigTipoDadoCtd.class, Long.valueOf(Pagina.getParam(request, "configTipoDadoCtd"))));
					
			if(Pagina.getParam(request, "configSisExecFinanCsefv") != null)
				configExecFinan.setConfigSisExecFinanCsefv((ConfigSisExecFinanCsefv) configSisExecFinanCsefvDao.buscar(ConfigSisExecFinanCsefv.class, Long.valueOf(Pagina.getParam(request, "configSisExecFinanCsefv"))));
			
			configExecFinanDao.salvar(configExecFinan);
			msg = _msg.getMensagem("configExecFinan.inclusao.sucesso");
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
			configExecFinan = (ConfigExecFinanCef) configExecFinanDao.buscar(ConfigExecFinanCef.class, Long.valueOf(Pagina.getParam(request, "codigo")));
			/* altera o que foi modificado na tela */
			configExecFinan.setNomeCef(Pagina.getParamStr(request, "nomeCef").trim()); 
			configExecFinan.setLabelCef(Pagina.getParamStr(request, "labelCef").trim()); 
			configExecFinan.setNumCaracteresCef(Integer.valueOf(Pagina.getParamStr(request, "numCaracteresCef").trim()));
			if(Pagina.getParam(request, "configTipoDadoCtd") != null)
				configExecFinan.setConfigTipoDadoCtd((ConfigTipoDadoCtd)configTipoDadoDao.buscar(ConfigTipoDadoCtd.class, Long.valueOf(Pagina.getParam(request, "configTipoDadoCtd"))));
			if(Pagina.getParam(request, "configSisExecFinanCsefv") != null)
				configExecFinan.setConfigSisExecFinanCsefv((ConfigSisExecFinanCsefv) configSisExecFinanCsefvDao.buscar(ConfigSisExecFinanCsefv.class, Long.valueOf(Pagina.getParam(request, "configSisExecFinanCsefv"))));
			configExecFinan.setSeqApresentacaoCef(Integer.valueOf(Pagina.getParamStr(request, "seqApresentacaoCef").trim()));
			configExecFinan.setIdXmlCef(Pagina.getParamStr(request, "idXmlCef").trim());
			/* altera no BD */
			configExecFinanDao.alterar(configExecFinan);
			msg = _msg.getMensagem("configExecFinan.alteracao.sucesso");
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
			/* busca e exclui o objeto do BD */
			configExecFinanDao.excluir((ConfigExecFinanCef)configExecFinanDao.buscar(ConfigExecFinanCef.class, Long.valueOf(Pagina.getParam(request, "codigo"))));
			msg = _msg.getMensagem("configExecFinan.exclusao.sucesso");
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
			if(Pagina.getParam(request, "configTipoDadoCtd") != null)
				configExecFinan.setConfigTipoDadoCtd((ConfigTipoDadoCtd)configTipoDadoDao.buscar(ConfigTipoDadoCtd.class, Long.valueOf(Pagina.getParam(request, "configTipoDadoCtd"))));
			if(Pagina.getParam(request, "configSisExecFinanCsefv") != null)
				configExecFinan.setConfigSisExecFinanCsefv((ConfigSisExecFinanCsefv) configSisExecFinanCsefvDao.buscar(ConfigSisExecFinanCsefv.class, Long.valueOf(Pagina.getParam(request, "configSisExecFinanCsefv"))));
		} catch (ECARException e) {
			org.apache.log4j.Logger.getLogger(this.getClass()).error(e);
			msg = _msg.getMensagem(e.getMessageKey());
		}
		
		/* aqui devemos usar getParam, pois queremos null se o campo estiver vazio */
		configExecFinan.setNomeCef((Pagina.getParam(request, "nomeCef"))); 
		configExecFinan.setLabelCef((Pagina.getParam(request, "labelCef"))); 
		if (Pagina.getParam(request, "numCaracteresCef") != null)
			configExecFinan.setNumCaracteresCef(Integer.valueOf(Pagina.getParamInt(request, "numCaracteresCef")));
		if (Pagina.getParam(request, "seqApresentacaoCef") != null)
			configExecFinan.setSeqApresentacaoCef(Integer.valueOf(Pagina.getParamInt(request, "seqApresentacaoCef")));
		configExecFinan.setIdXmlCef((Pagina.getParam(request, "idXmlCef"))); 
		session.setAttribute("objPesquisa", configExecFinan);
	}
	/* da o alert para incluir, alterar, pesquisar ou excluir */
	if (msg != null)
		Mensagem.setInput(_jspx_page_context, "document.form.msgOperacao.value", msg); 
		
	if (refazPesquisa) {
		try {
			configExecFinan = (ConfigExecFinanCef) session.getAttribute("objPesquisa");
			List lista = configExecFinanDao.pesquisar(configExecFinan, new String[] {"nomeCef","asc"});
			boolean pesquisou = (lista.size() > 0) ? true : false;
			session.setAttribute("lista", lista);
			if (pesquisou) {
				submit = "frm_nav.jsp";
				msg = null;
			} else {
				Mensagem.setInput(_jspx_page_context, "document.form.hidPesquisou.value", "false");
				submit = "frm_pes.jsp";
				msg = _msg.getMensagem("configExecFinan.pesquisa.nenhumRegistro");
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