<%@ include file="/login/validaAcesso.jsp"%>
<%@ include file="/frm_global.jsp"%>

<%@ page language="java" %>
<%@ page import="comum.util.Data" %>
<%@ page import="ecar.pojo.AgendaOcorrenciaAgeo" %>

<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.Iterator" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<html lang="pt-br">
<head>
<%@ include file="/titulo.jsp"%>
<%@ include file="/include/meta.jsp"%>
<link rel="stylesheet" href="<%=_pathEcar%>/css/style_<%=configuracaoCfg.getEstilo().getNome()%>.css" type="text/css">

<script>
function voltar() {
	document.form.submit();
}

</script>

</head>
<body>
<%@ include file="/cabecalho.jsp" %>
<%
try{
%>
	<form name="form" id="form" action="ctrl_agenda.jsp">
		<input type="hidden" name="hidAcao" value="AGENDA">	
		<input type="hidden" name="data" value=<%=Data.parseDate(Data.getDataAtual())%>>	
	</form>		
	<table id="barra_localizacao" cellspacing="0">
		<tr>
			<td><a href="index.jsp">Capa</a> > <a href="#" onclick="javascript:voltar();">Agenda</a> > Detalhe </td>
			<td align="right">&lt;&lt;<a href="#" onclick="javascript:voltar();"> Voltar</a></td>
		</tr>
	</table>
	<h1 class="interno">AGENDA</h1>
    <table cellspacing="0" cellpadding="0" width="100%">
	<tr>
		<td class="conteudo_interno" width="100%">
		<%
			List lista = (List)request.getSession().getAttribute("listaAgenda");
			if((lista != null) && (lista.size() > 0)) {	
				AgendaOcorrenciaAgeo ag = (AgendaOcorrenciaAgeo)lista.get(0);
				
				String sHora = String.valueOf(ag.getHoraEventoAgeo());
				if (sHora.length()==1) sHora = "0"+sHora;
				
				String sMin = String.valueOf(ag.getMinutoEventoAgeo());
				if (sMin.length()==1) sMin = "0"+sMin;
			%>
			<p>Data: <b><%=Data.parseDate(ag.getDataEventoAgeo())%></b><br>
		    Hora: <b><%=sHora+":"+sMin%></b></p>
		    Local: <b><%=ag.getLocalAgeo()%></b><br><br>
	        <p><b><%=ag.getAgendaAge().getEventoAge()%></b></p>
      <p><%=ag.getAgendaAge().getDescricaoAge()%></p></td>
      <%}%>
	</tr>
</table><hr>
<%
} catch (Exception e){
%>
	<%@ include file="/excecao.jsp"%>
<%
}
%>
</body>
</html>
