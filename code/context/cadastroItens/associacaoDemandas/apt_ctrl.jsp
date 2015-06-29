<%@ include file="../../login/validaAcesso.jsp"%>
<%@ include file="../../frm_global.jsp"%>

<%@ page import="ecar.pojo.RegDemandaRegd" %>
<%@ page import="ecar.dao.RegDemandaDao" %>
<%@ page import="ecar.pojo.RegApontamentoRegda" %>
<%@ page import="ecar.dao.RegApontamentoDao" %>
<%@ page import="ecar.pojo.UsuarioUsu" %>
<%@ page import="ecar.dao.UsuarioDao" %>

<%@ page import="comum.util.Data" %> 
<%@ page import="java.util.Date" %>

<html lang="pt-br">
<head>
<%@ include file="../../include/meta.jsp"%>
<%@ include file="/titulo.jsp"%>
</head>
<body>

<form name="form" method="post">
    <!-- campo de controle para a navegação de registros -->
	<input type="hidden" name="msgOperacao" value=""> 
	<input type="hidden" name="msgPesquisa" value="">	
	<input type="hidden" name="hidPesquisou" value="">
	
	<%//Campos de ordenação da listagem de Apontamento%>
	<input type="hidden" name="aptCampo" value="<%=Pagina.getParam(request, "aptCampo")%>">
	<input type="hidden" name="aptOrdem" value="<%=Pagina.getParam(request, "aptOrdem")%>">
	<input type="hidden" name="aptRefazPesquisa" value="">

	<input type="hidden" name="codRegd" value="<%=Pagina.getParam(request, "codRegd")%>">
	
	<input type=hidden name="codIett" value="<%=Pagina.getParamStr(request, "codIett")%>">
	<input type=hidden name="codAba" value="<%=Pagina.getParamStr(request, "codAba")%>">
	
	<%//Campos de ordenação da listagem de Demandas%>
	<input type="hidden" name="clCampo" value="<%=Pagina.getParam(request, "clCampo")%>">
	<input type="hidden" name="clOrdem" value="<%=Pagina.getParam(request, "clOrdem")%>">
	
</form>

<%
	RegApontamentoRegda regApontamento = new RegApontamentoRegda();
	RegApontamentoDao regApontamentoDao = new RegApontamentoDao(request);

	Mensagem mensagem = new Mensagem(application);
	
	String hidAcao = Pagina.getParam(request, "hidAcao");
	String msg = null;
	String submit = "frm_con.jsp";
	String origem = Pagina.getParam(request, "origem");
	
	/* default sempre refazer a pesquisa */
	boolean incluir = "incluir".equalsIgnoreCase(hidAcao);
	boolean excluir = "excluir".equalsIgnoreCase(hidAcao);
	
	RegDemandaDao regDemandaDao = new RegDemandaDao(request);
	RegDemandaRegd regDemanda = (RegDemandaRegd) regDemandaDao.buscar(RegDemandaRegd.class, Long.valueOf(Pagina.getParamLong(request, "codRegd")));
	
	if (incluir) {
		try {
			regApontamento.setRegDemandaRegd(regDemanda);
			regApontamento.setInfoRegda(Pagina.getParamStr(request, "infoRegda").trim());
			regApontamento.setDataRegda(Data.getDataAtual());
			regApontamento.setUsuarioUsu(((ecar.login.SegurancaECAR) session.getAttribute("seguranca")).getUsuario());
			
			regApontamentoDao.salvar(regApontamento);
			
			msg = _msg.getMensagem("registroApontamento.inclusao.sucesso");
			session.removeAttribute("objRegApontamento");
		} catch (ECARException e) {
			org.apache.log4j.Logger.getLogger(this.getClass()).error(e);
			session.setAttribute("objRegApontamento", regDemanda);
			msg = _msg.getMensagem(e.getMessageKey());
		} catch (Exception e){
		%>
			<%@ include file="/excecao.jsp"%>
		<%
		}
	} else if (excluir) {
		try {
			String codigosParaExcluir[] = request.getParameterValues("excluir");
			regApontamentoDao.excluir(codigosParaExcluir);
			msg = _msg.getMensagem("registroApontamento.exclusao.sucesso");
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

	<script language="javascript">
		//Após executar alguma ação refazer a listagem
		document.form.aptRefazPesquisa.value = "refaz";
		
		document.form.action = "<%=submit%>";
		document.form.submit();
	</script> 

</body>
</html>

