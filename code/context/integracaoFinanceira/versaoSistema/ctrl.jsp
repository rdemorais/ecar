<%@ include file="../../login/validaAcesso.jsp"%>

<%@ include file="../../frm_global.jsp"%>

<%@ page import="ecar.pojo.ConfigSisExecFinanCsefv" %>
<%@ page import="ecar.dao.ConfigSisExecFinanCsefvDao" %>
<%@ page import="ecar.pojo.ConfigSisExecFinanCsef" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.Date" %>
<%@ page import="java.text.DecimalFormat" %>

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
	ConfigSisExecFinanCsefv consefv = new ConfigSisExecFinanCsefv();
	ConfigSisExecFinanCsefvDao consefvDao = new ConfigSisExecFinanCsefvDao(request);
	
	
	String hidAcao = Pagina.getParamStr(request, "hidAcao");
	String msg = null;
	String submit = "frm_pes.jsp";
	boolean refazPesquisa = true;
	boolean incluir = "incluir".equalsIgnoreCase(hidAcao);
	boolean alterar = "alterar".equalsIgnoreCase(hidAcao);
	boolean excluir = "excluir".equalsIgnoreCase(hidAcao);
	boolean pesquisar = "pesquisar".equalsIgnoreCase(hidAcao);
	
	if (incluir) {
		/* melhor usar getParamStr, pois nunca devolve null */	

		ConfigSisExecFinanCsef con = new ConfigSisExecFinanCsef();
		con.setCodCsef(Long.valueOf(Pagina.getParamStr(request, "configSisExecFinanCsef").trim()));

		consefv.setConfigSisExecFinanCsef(con);
		consefv.setVersaoCsefv(Long.valueOf(Pagina.getParamStr(request, "versaoCsefv").trim()));
				
		String mesAno = Pagina.getParamStr(request, "mesVersaoCsefv").trim();
		
		String[] mesAnoRecuperado = recuperaMesAno(mesAno); 
		
		String mes = mesAnoRecuperado[0];
		String ano = mesAnoRecuperado[1];
		
		session.setAttribute("mesAno", mesAno);
		
		consefv.setMesVersaoCsefv(Long.valueOf(mes));		
		consefv.setAnoVersaoCsefv(Long.valueOf(ano));
		consefv.setIndAtivoCsefv(Pagina.getParamStr(request, "ativo").trim());
		consefv.setDataInclusaoCsefv(new Date());

		refazPesquisa = false;
		submit = "frm_inc.jsp";
		try {
		
			//Verifica para não permitir gravar mais de um registro de versão conforme Mês/Ano + Sistema.
			ConfigSisExecFinanCsefv duplo = (ConfigSisExecFinanCsefv)consefvDao.getConfigSisExecFinanCsefv(consefv.getAnoVersaoCsefv(), 
																											consefv.getMesVersaoCsefv(), con);

			if (duplo != null) {
				msg = _msg.getMensagem("integracaoFinanceira.versaoSistema.inclusao.duplicidade");
			} else {
				consefvDao.salvar(consefv);
				msg = _msg.getMensagem("integracaoFinanceira.versaoSistema.inclusao.sucesso");
			}
			
			session.removeAttribute("objConsefv");
		} catch (ECARException e) {
			org.apache.log4j.Logger.getLogger(this.getClass()).error(e);
			session.setAttribute("objConsefv", consefv);
			msg = _msg.getMensagem(e.getMessageKey());
		} catch (Exception e){
		%>
			<%@ include file="/excecao.jsp"%>
		<%
		}
	} else if (alterar) {
		try {			/* o buscar deve estar no try, pois pode ocorrer um erro de hibernate ou bd */
		
			String alteracaoParcial = Pagina.getParamStr(request, "alteracaoParcial").trim();
		
			consefv = (ConfigSisExecFinanCsefv) consefvDao.buscar(ConfigSisExecFinanCsefv.class, Long.valueOf(Pagina.getParamLong(request, "codigo")));
			
			if (alteracaoParcial.equals("")) {
				ConfigSisExecFinanCsef con = new ConfigSisExecFinanCsef();
				con.setCodCsef(Long.valueOf(Pagina.getParamStr(request, "configSisExecFinanCsef").trim()));
				consefv.setConfigSisExecFinanCsef(con);
				String mesAno = Pagina.getParamStr(request, "mesVersaoCsefv").trim();
				
				String[] mesAnoRecuperado = recuperaMesAno(mesAno); 
				
				String mes = mesAnoRecuperado[0];
				String ano = mesAnoRecuperado[1];
				consefv.setMesVersaoCsefv(Long.valueOf(mes));		
				consefv.setAnoVersaoCsefv(Long.valueOf(ano));
				consefv.setVersaoCsefv(Long.valueOf(Pagina.getParamStr(request, "versaoCsefv").trim()));
				session.setAttribute("mesAno", mesAno);
			}
			consefv.setIndAtivoCsefv(Pagina.getParamStr(request, "ativo").trim());
			
			consefvDao.alterar(consefv);
			
			msg = _msg.getMensagem("integracaoFinanceira.versaoSistema.alteracao.sucesso");
			//session.removeAttribute("objConsefv");
		} catch (ECARException e) {
			org.apache.log4j.Logger.getLogger(this.getClass()).error(e);
			refazPesquisa = false;
			submit = "frm_alt.jsp";
			session.setAttribute("objConsefv", consefv);
			msg = _msg.getMensagem(e.getMessageKey());
		} catch (Exception e){
		%>
			<%@ include file="/excecao.jsp"%>
		<%
		}
	} else if (excluir) {
		try {
			consefvDao.excluir( (ConfigSisExecFinanCsefv) consefvDao.buscar(ConfigSisExecFinanCsefv.class, Long.valueOf(Pagina.getParam(request, "codigo"))));
			msg = _msg.getMensagem("integracaoFinanceira.versaoSistema.exclusao.sucesso");
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

		ConfigSisExecFinanCsef con = new ConfigSisExecFinanCsef();
		String codConfigSisExecFinanCsef = Pagina.getParamStr(request, "configSisExecFinanCsef");
		
		if (!codConfigSisExecFinanCsef.trim().equals("")) {		
			con.setCodCsef(Long.valueOf(codConfigSisExecFinanCsef));
			consefv.setConfigSisExecFinanCsef(con);
		}
		
		if (!Pagina.getParamStr(request, "versaoCsefv").trim().equals("")) {
			consefv.setVersaoCsefv(Long.valueOf(Pagina.getParamStr(request, "versaoCsefv").trim()));
		}
		
		
		String mesAno = Pagina.getParamStr(request, "mesVersaoCsefv").trim();
		
		if (!mesAno.equals("")) {
		
			String[] mesAnoRecuperado = recuperaMesAno(mesAno); 
			String mes = mesAnoRecuperado[0];
			String ano = mesAnoRecuperado[1];
			consefv.setMesVersaoCsefv(Long.valueOf(mes));		
			consefv.setAnoVersaoCsefv(Long.valueOf(ano));
		}

		if (!Pagina.getParamStr(request, "ativo").trim().equals("")) {
			consefv.setIndAtivoCsefv(Pagina.getParamStr(request, "ativo").trim());
		}		

		session.setAttribute("objPesquisa", consefv);
	}
	/* da o alert para incluir, alterar, pesquisar ou excluir */
	if (msg != null) 
		Mensagem.setInput(_jspx_page_context, "document.form.msgOperacao.value", msg); 
	
	if (refazPesquisa) {
		try {
			consefv = (ConfigSisExecFinanCsefv) session.getAttribute("objPesquisa");
			List lista = consefvDao.pesquisar(consefv, new String[] {"versaoCsefv","asc"});
			boolean pesquisou = (lista.size() > 0) ? true : false;
			session.setAttribute("lista", lista);
			if (pesquisou) {
				submit = "frm_nav.jsp";
				msg = null;
			} else {
				Mensagem.setInput(_jspx_page_context, "document.form.hidPesquisou.value", "false");
				submit = "frm_pes.jsp";
				msg = _msg.getMensagem("integracaoFinanceira.versaoSistema.pesquisar.nenhumRegistro");
			}
		} catch (ECARException e) {
			org.apache.log4j.Logger.getLogger(this.getClass()).error(e);
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
<%!
	public String[] recuperaMesAno(String arg) {
	
		String mes = arg.substring(0, 2);
		String ano = arg.substring(3, 7);
		
		return new String[]{mes, ano};
	}
	
	public String formataMesAno(String mes, String ano) {
	
		DecimalFormat df = new DecimalFormat("00");		
		mes = df.format(mes);
	
		return mes + "/" + ano;
	}	
%>
