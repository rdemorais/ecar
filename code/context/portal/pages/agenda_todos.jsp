<%@ page language="java"%>
<%@ taglib uri="/WEB-INF/ecar-util.tld" prefix="util" %>

<%@ include file="/login/validaAcesso.jsp"%>
<%@ include file="/frm_global.jsp"%>

<%@ page import="java.util.Date" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.lang.Math" %>
<%@ page import="comum.util.Data" %>
<%@ page import="ecar.pojo.SegmentoItemSgti" %>
<%@ page import="ecar.pojo.AgendaAge" %>
<%@ page import="ecar.pojo.AgendaOcorrenciaAgeo" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<html lang="pt-br">
<head>

<script language="javascript">
function artigos(){
	document.form.hidAcao.value = "AGENDA_TODOS";
	document.form.action = "ctrl_agenda.jsp";	
	document.form.submit();
}

function detalhe(codAgeo){
	document.formDetalhes.codAgeo.value=codAgeo;
	document.formDetalhes.hidAcao.value = "DETALHE";
	document.formDetalhes.action = "ctrl_agenda.jsp";	
	document.formDetalhes.submit();
}
</script>


<%@ include file="/titulo.jsp"%>
<%@ include file="/include/meta.jsp"%>

<link rel="stylesheet" href="<%=_pathEcar%>/css/style_<%=configuracaoCfg.getEstilo().getNome()%>.css" type="text/css">
<script language="javascript" src="../../js/frmPesquisa.js"></script>
</head>
<body>
<%@ include file="/cabecalho.jsp" %>
	
	<form name="formDetalhes" method="post" action="ctrl_agenda.jsp">
		<input type="hidden" name="hidAcao" value="">	
		<input type="hidden" name="codAgeo" value="">
	</form>
	
	<form name="form" method="post" action="agenda_todos.jsp?hidAcao=navegar">
	<table id="barra_localizacao" cellspacing="0">
		<tr>
			<td><a href="index.jsp">Capa</a> > <a href="agenda.jsp">Agenda</a> > Todos</td>
			<td align="right">&lt;&lt;<a href="agenda.jsp"> Voltar</a></td>
		</tr>
	</table>
	<h1 class="interno">AGENDA</h1>
    <div id="list_moldura">	
	<hr>
	<%
	try {
		final int ITENS_PAGINA = _msg.getQtdeItensPaginaPesquisa("portal.agenda.itensPagina");
			
		int hidNumPagina = Pagina.getParamInt(request, "hidNumPagina");
		int hidTotPagina = Pagina.getParamInt(request, "hidTotPagina");
		
		List lista = (List) session.getAttribute("listaTodaAgenda");
		
		int tamLista = (lista!=null?lista.size():0);
		int limite = Math.min(tamLista, hidNumPagina * ITENS_PAGINA + ITENS_PAGINA);
		
		hidTotPagina = tamLista / ITENS_PAGINA + (tamLista % ITENS_PAGINA == 0 ? 0 : 1);
		
		if (limite > 0) { 
%>
		<table cellspacing="0" cellpadding="0" class="list_paginacao">
		<tr>
			<td align="center">
			<util:barrapesquisa atual="<%=hidNumPagina+1%>" total="<%=hidTotPagina%>"/>
			<input type="hidden" name="hidNumPagina" value="<%=hidNumPagina%>">	
			<input type="hidden" name="hidTotPagina" value="<%=hidTotPagina%>">	
			</td>
		<tr>
		</table>
		<table cellspacing="0" class="list_artigos">
<%
		}
		int mes = 0, mes_dt = 0;
		java.text.SimpleDateFormat sf = new java.text.SimpleDateFormat("dd/MM/yyyy"); 	
		for (int i = hidNumPagina*ITENS_PAGINA; i < limite; i++) {
			AgendaOcorrenciaAgeo agenda = (AgendaOcorrenciaAgeo) lista.get(i);

			Date dt = agenda.getDataEventoAgeo();
			
			out.println("<tr><td>");
			
			mes_dt = Data.getMes(dt)+1;
			if (mes_dt != mes) {
				out.println("<h1>"+Data.getNomeMesExtenso(mes_dt)+" / "+Data.getAno(dt)+"</h1>");
				mes = mes_dt;
			}
			
			if( agenda.getDescricaoAgeo() != null ) { 

				String sHora = String.valueOf(agenda.getHoraEventoAgeo());
				if (sHora.length()==1) sHora = "0"+sHora;
				
				String sMin = String.valueOf(agenda.getMinutoEventoAgeo());
				if (sMin.length()==1) sMin = "0"+sMin;

				String dataHora = "("+ sf.format(agenda.getDataEventoAgeo()) +" - "+ sHora +":"+ sMin +")";
%>						
<!--				<a href="javascript:detalhe(<%=agenda.getCodAgeo()%>);"><%=sf.format(dt)%> <%=agenda.getDescricaoAgeo()%> - <%=agenda.getLocalAgeo()%></a>-->
				<a href="javascript:detalhe(<%=agenda.getCodAgeo().longValue()%>);"><%=dataHora%> <%=agenda.getAgendaAge().getEventoAge()%></a>
<%				
			}			
		}
		
		if (limite > 0) { 
%>
		</table>
		<table cellspacing="0" cellpadding="0" class="list_paginacao">
		<tr>
			<td align="center">
			<util:barrapesquisa atual="<%=hidNumPagina+1%>" total="<%=hidTotPagina%>"/>
			</td>
		<tr>
		</table>
<%
		} else {	%>
			<table>
				<tr><td class="texto" align="center">
					<b>Nenhum registro foi encontrado</b>
				</td></tr>
			</table>	<%
			
		}		
	} catch (Exception e) {
		org.apache.log4j.Logger.getLogger(this.getClass()).error(e);
		Mensagem.alert(_jspx_page_context, _msg.getMensagem("erro.exception"));
	}
	%>
</div>
</form>
</body>
</html>
