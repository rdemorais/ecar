<%@ page import="ecar.exception.ECARException" %>
<%@ include file="../login/validaAcesso.jsp"%>
<%@ include file="../frm_global.jsp"%>
<html lang="pt-br">
<head>
<%@ include file="../include/meta.jsp"%>
<%@ include file="/titulo.jsp"%>
</head>
<body>
<%

	String msg = "";
	
try{
	//ecar.pojo.ConfiguracaoCfg configura = new ecar.dao.ConfiguracaoDao(request).getConfiguracao();

	String acao = Pagina.getParamStr(request, "hidAcao");

%>
<form name="form" method="post">
	<input type="hidden" name="titulo_sistema" value="<%=configuracaoCfg.getTituloSistema()%>">
	<input type="hidden" name="periodoIni" value="<%=Pagina.getParamStr(request, "periodoIni")%>">
	<input type="hidden" name="periodoFim" value="<%=Pagina.getParamStr(request, "periodoFim")%>">
	<input type="hidden" name="paginaInicial" value="<%=Pagina.getParamStr(request, "paginaInicial")%>">
	<input type="hidden" name="indTipoRelatorio" value="<%=Pagina.getParamStr(request, "indTipoRelatorio")%>">
	<input type="hidden" name="indTipoValor" value="<%=Pagina.getParamStr(request, "indTipoValor")%>">
	<input type="hidden" name="indMostrarTotalizador" value="<%=Pagina.getParamStr(request, "indMostrarTotalizador")%>">

	<!-- campo de controle para mensagens -->
	<input type="hidden" name="msgOperacao" value=""> 
<%
    // passa para o servlet todos os checkbox marcados
	String[] filhos = request.getParameterValues("itemFilho");
	if (filhos != null) {
		for (int i = 0; i < filhos.length; i++) {
%>
			<input type="hidden" name="itemFilho" value="<%=filhos[i]%>"><br>
<%
		}
	}
	
%>	
</form>
	
<%
	if("imprimir".equals(acao)){
%>
		<script language="javascript">
			if(document.form.indTipoRelatorio.value == "apendice2"){
				document.form.action = "<%=_pathEcar%>/RelatorioApendiceDois";
			}
			
			if(document.form.indTipoRelatorio.value == "apendice3"){
				document.form.action = "<%=_pathEcar%>/RelatorioApendiceTres";
			}
			document.form.submit();
		</script>
<%
	}

} catch (ECARException e){
	org.apache.log4j.Logger.getLogger(this.getClass()).error(e);
	msg = _msg.getMensagem(e.getMessageKey());
	/*
	if(e.getMessageArgs() != null && e.getMessageArgs().length == 2) {
		String item = (String) e.getMessageArgs()[0];
		String revisao = (String) e.getMessageArgs()[1];
		msg += ": Item (" + item + ") - Revisão (" + revisao + ")";
	}*/

} catch (Exception e){
%>
	<%@ include file="/excecao.jsp"%>
<%
}

if (msg != null)
	Mensagem.setInput(_jspx_page_context, "document.form.msgOperacao.value", msg); 
%>
</body>
<%@ include file="../include/mensagemAlert.jsp"%>
</html>

