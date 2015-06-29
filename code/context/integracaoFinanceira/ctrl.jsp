
<jsp:directive.page import="org.hibernate.Transaction"/>
<jsp:directive.page import="java.util.HashSet"/>
<jsp:directive.page import="java.util.ArrayList"/><%@ include file="../login/validaAcesso.jsp"%>
<%@ include file="../frm_global.jsp"%>

<%@ page import="ecar.pojo.RegDemandaRegd" %>
<%@ page import="ecar.dao.RegDemandaDao" %>
<%@ page import="ecar.pojo.UsuarioUsu" %>
<%@ page import="ecar.dao.UsuarioDao" %>
<%@ page import="ecar.pojo.SitDemandaSitd" %>
<%@ page import="ecar.dao.SitDemandaDao" %>
<%@ page import="ecar.pojo.PrioridadePrior" %>
<%@ page import="ecar.pojo.EntidadeEnt" %>
<%@ page import="ecar.dao.EntidadeDao" %>

<%@ page import="comum.util.Data" %> 
<%@ page import="java.util.List" %>
<%@ page import="java.util.Iterator" %>
<%@ page import="java.util.Date" %>
<%@ page import="ecar.dao.ItemEstruturaRealizadoDao" %>
<%@ page import="ecar.pojo.EfItemEstRealizadoEfier" %>
<%@ page import="ecar.exception.ECARException" %>
<%@ page import="ecar.pojo.ConfigSisExecFinanCsef" %>
<%@ page import="ecar.dao.ConfigSisExecFinanDao" %>


<html lang="pt-br">
<head>
<%@ include file="../../include/meta.jsp"%>
<%@ include file="/titulo.jsp"%>
</head>
<body>

<%
	String codEfier = Pagina.getParamStr(request, "codEfier");
%>
<form name="form" method="post">
    <!-- campo de controle para a navegação de registros -->
	<input type="hidden" name="msgOperacao" value=""> 
	<input type="hidden" name="msgPesquisa" value="">	
	<input type="hidden" name="hidPesquisou" value="">
	<input type="hidden" name="codEfier" value="<%=codEfier%>">
	<input type="hidden" name="configSisExecFinanCsef" value="<%=Pagina.getParamStr(request, "configSisExecFinanCsef")%>">

	<!-- Hiddens do Filtro... -->
	<input type="hidden" name="filtroConta" value="<%=Pagina.getParamStr(request, "filtroConta")%>">
	<input type="hidden" name="filtroAno" value="<%=Pagina.getParamStr(request, "filtroAno")%>">
	<input type="hidden" name="filtroMes" value="<%=Pagina.getParamStr(request, "filtroMes")%>">
	<input type="hidden" name="filtroCodSistema" value="<%=Pagina.getParamStr(request, "filtroCodSistema")%>">
	<input type="hidden" name="nomeSistemaFiltrado" value="<%=Pagina.getParamStr(request, "nomeSistemaFiltrado")%>">

<%
	ItemEstruturaRealizadoDao realizadoDao = new ItemEstruturaRealizadoDao(request);
	
	String hidAcao = Pagina.getParamStr(request, "hidAcao");
	String msg = null;
	String submit = "";

	/* default sempre refazer a pesquisa */
	boolean incluir = "incluir".equalsIgnoreCase(hidAcao);
	boolean incluirMultiplos = "incluirMultiplos".equalsIgnoreCase(hidAcao);
	boolean alterar = "alterar".equalsIgnoreCase(hidAcao);
	boolean excluir = "excluir".equalsIgnoreCase(hidAcao);
	boolean pesquisar = "pesquisar".equalsIgnoreCase(hidAcao);	

	if (incluir) {
		EfItemEstRealizadoEfier realizado = new EfItemEstRealizadoEfier();
		try{
			submit = "frm_inc.jsp";
			realizadoDao.setEfItemEstRealizadoEfier(realizado, request);
			realizadoDao.gravar(realizado, null);
			%>
			<input type="hidden" name="incSistema" value="<%=Pagina.getParamStr(request, "configSisExecFinanCsef")%>">
			<input type="hidden" name="incMes" value="<%=Pagina.getParamStr(request, "mesReferenciaEfier")%>">
			<input type="hidden" name="incAno" value="<%=Pagina.getParamStr(request, "anoReferenciaEfier")%>">
			<%
			msg = _msg.getMensagem("integracaoFinanceira.manual.inclusao.ok");
		} catch (ECARException e) {
			org.apache.log4j.Logger.getLogger(this.getClass()).error(e);
			request.getSession().setAttribute("efier", realizado);
			msg = _msg.getMensagem(e.getMessageKey());
		} catch (Exception e){
		%>
			<%@ include file="/excecao.jsp"%>
		<%
		}		
	} else if (incluirMultiplos) {
		EfItemEstRealizadoEfier realizado = null;
		List realizados  =  new ArrayList();
		try{
			submit = "frm_inc.jsp";
			%>
			<input type="hidden" name="incSistema" value="<%=Pagina.getParamStr(request, "configSisExecFinanCsef")%>">
			<input type="hidden" name="incMes" value="<%=Pagina.getParamStr(request, "mesReferenciaEfier")%>">
			<input type="hidden" name="incAno" value="<%=Pagina.getParamStr(request, "anoReferenciaEfier")%>">
			<%
			boolean ok = true;
			for(int j = 1;j<=10;j++){
				realizado = new EfItemEstRealizadoEfier();
				realizadoDao.setEfItemEstRealizadoEfier(realizado, request, j);
				if (realizado.getContaSistemaOrcEfier()!=null&&(!realizado.getContaSistemaOrcEfier().equals(""))) {
					realizados.add(realizado);
				}
				if ((realizado.getContaSistemaOrcEfier()==null)||(realizado.getContaSistemaOrcEfier().equals(""))) {
					if (	((realizado.getValor1Efier() != null) && (realizado.getValor1Efier().doubleValue() != 0)) ||
							((realizado.getValor2Efier() != null) && (realizado.getValor2Efier().doubleValue() != 0)) ||
							((realizado.getValor3Efier() != null) && (realizado.getValor3Efier().doubleValue() != 0)) ||
							((realizado.getValor4Efier() != null) && (realizado.getValor4Efier().doubleValue() != 0)) ||
							((realizado.getValor5Efier() != null) && (realizado.getValor5Efier().doubleValue() != 0)) ||
							((realizado.getValor6Efier() != null) && (realizado.getValor6Efier().doubleValue() != 0)) ) {
						ok = false;
						realizados.add(realizado);
		//				throw new ECARException("integracaoFinanceira.manual.inclusao.valorContaNaoInformado");
						
					}
				}
			}
			if (!ok) {
				throw new ECARException("integracaoFinanceira.manual.inclusao.valorContaNaoInformado");
			}
			realizadoDao.salvarOuAlterar(realizados);
			msg = _msg.getMensagem("integracaoFinanceira.manual.inclusao.ok");
		} catch (ECARException e) {
			org.apache.log4j.Logger.getLogger(this.getClass()).error(e);
			request.getSession().setAttribute("efier", realizado);
			request.getSession().setAttribute("efierList", realizados);
			request.getSession().setAttribute("sessaoEscolhida", Pagina.getParamStr(request, "codVersaoEscolhida"));
			msg = _msg.getMensagem(e.getMessageKey());
		} catch (Exception e){
		%>
			<%@ include file="/excecao.jsp"%>
		<%
		}
	} else if (alterar) {
		try{
			EfItemEstRealizadoEfier realizado = (EfItemEstRealizadoEfier) realizadoDao.buscar(EfItemEstRealizadoEfier.class, Long.valueOf(codEfier));
			realizadoDao.setEfItemEstRealizadoEfier(realizado, request);
			realizadoDao.alterar(realizado);
			submit = "lista.jsp";
		} catch (ECARException e) {
			org.apache.log4j.Logger.getLogger(this.getClass()).error(e);
			msg = _msg.getMensagem(e.getMessageKey());
			submit = "frm_alt.jsp";
		} catch (Exception e){
		%>
			<%@ include file="/excecao.jsp"%>
		<%
		}		
	} else if (excluir) {
		try {
			String codigosParaExcluir[] = request.getParameterValues("excluir");
			realizadoDao.excluir(codigosParaExcluir);
			msg = _msg.getMensagem("integracaoFinanceira.manual.exclusao.ok");
			submit = "lista.jsp";
		} catch (ECARException e) {
			org.apache.log4j.Logger.getLogger(this.getClass()).error(e);
			msg = _msg.getMensagem(e.getMessageKey());
		} catch (Exception e){
		%>
			<%@ include file="/excecao.jsp"%>
		<%
		}	
	} else if (pesquisar) {
		try{
			String conta = Pagina.getParamStr(request, "conta");
			String ano = Pagina.getParamStr(request, "anoReferenciaEfier");
			String mes = Pagina.getParamStr(request, "mesReferenciaEfier");
			String sistema = Pagina.getParamStr(request, "configSisExecFinanCsef");
			String nomeSistema = "";
			if(!"".equals(sistema.trim())){
				ConfigSisExecFinanCsef tempSistema = (ConfigSisExecFinanCsef) new ConfigSisExecFinanDao(request).buscar(ConfigSisExecFinanCsef.class, Long.valueOf(sistema));
				nomeSistema = tempSistema.getNomeCsef();
			}
			
			submit = "lista.jsp?filtroConta=" + conta + "&filtroAno=" + ano + "&filtroMes=" + mes + "&filtroCodSistema=" + sistema + "&nomeSistemaFiltrado=" + nomeSistema;
		} catch (ECARException e) {
			org.apache.log4j.Logger.getLogger(this.getClass()).error(e);
			msg = _msg.getMensagem(e.getMessageKey());
		} catch (Exception e){
		%>
			<%@ include file="/excecao.jsp"%>
		<%
		}	
	}
	
	if (msg != null)
			Mensagem.setInput(_jspx_page_context, "document.form.msgPesquisa.value", msg);
%>
</form>

	<script language="javascript">
		document.form.action = "<%=submit%>";
		document.form.submit();
	</script> 

</body>
</html>

