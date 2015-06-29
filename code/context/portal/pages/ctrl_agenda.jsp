<%@ include file="/login/validaAcesso.jsp"%>
<%@ include file="/frm_global.jsp"%>

<%@ page import="ecar.dao.SegmentoItemDao" %>
<%@ page import="java.util.Date" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.Iterator" %>
<%@ page import="ecar.dao.AgendaOcorrenciaDao" %>
<%@ page import="ecar.dao.AgendaDao" %>
<%@ page import="comum.util.Util" %>
<%@ page import="comum.util.Data" %>
<%@ page import="ecar.pojo.AgendaAge" %>
<%@ page import="ecar.pojo.AgendaOcorrenciaAgeo" %>
<%@ page import="java.util.Collections" %>
 
<html lang="pt-br">
<head>
<%@ include file="/include/meta.jsp"%>
<%@ include file="/titulo.jsp"%>
</head>
<body>

<form name="form" method="post">
<!-- campo de controle para mensagens -->
<input type="hidden" name="msgOperacao" value=""> 
<input type="hidden" name="dataFim" value=""> 
</form>

<%
String submit = "agenda.jsp";	
try{
	String msg = null;
	String acao = Pagina.getParamStr(request, "hidAcao");
	
	if ("".equals(acao)) {
		acao = (request.getParameter("acao") != null ? request.getParameter("acao") : "");
	}

	AgendaOcorrenciaDao agendaDao = new AgendaOcorrenciaDao(request);
	String data = Pagina.getParamStr(request, "data");
	
	if ("AGENDA".equals(acao)){					
		
		if ((data==null)||("".equals(data))) data = Data.parseDate(Data.getDataAtual());
		
		request.setAttribute("orderHoraMinuto", "true");
		request.setAttribute("dataDe", data);
		//request.setAttribute("dataAte", data);
		request.setAttribute("dataLimite", data);
		request.getSession().setAttribute("listaAgenda", agendaDao.pesquisar(request));
		Date dt = Data.parseDate(data);
		int mes = Data.getMes(dt)+1;
		int ano = Data.getAno(dt);
		String strData = "01/"+mes+"/"+ano;
		
		request.setAttribute("orderHoraMinuto", "true");
		request.setAttribute("dataDe", strData);

		int dia = Data.getUltimoDiaMes(Data.parseDate(data)); 
		strData = dia+"/"+mes+"/"+ano;
		//request.setAttribute("dataAte", strData);	
		request.setAttribute("dataLimite", strData);	
			
		List l = agendaDao.pesquisar(request);
		request.getSession().setAttribute("listaAgendaMes", l);
				
		request.getSession().setAttribute("data", Data.parseDate(data));//Data.getDataAtual());
	} else if ("AGENDA_DATA".equals(acao)) {
		request.setAttribute("orderHoraMinuto", "true");			
		request.setAttribute("dataDe", data);
		request.setAttribute("dataLimite", data);
		request.getSession().setAttribute("listaAgenda", agendaDao.pesquisar(request));
	} else if ("DETALHE".equals(acao)) {
		submit = "agenda_detalhe.jsp";
		request.setAttribute("codAgeo", Pagina.getParamStr(request, "codAgeo"));		
		request.getSession().setAttribute("listaAgenda", agendaDao.pesquisar(request));
	} else if ("AGENDA_TODOS".equals(acao)){
		submit = "agenda_todos.jsp";
		List tudo = agendaDao.pesquisar(request);
		Collections.reverse(tudo);
		request.getSession().setAttribute("listaTodaAgenda", tudo);
		
	}
		
	if (msg != null)
		Mensagem.setInput(_jspx_page_context, "document.form.msgOperacao.value", msg);

} catch (Exception e){
%>
	<%@ include file="/excecao.jsp"%>
<%
}
%>

	<script language="javascript">
		document.form.action = "<%=submit%>";
		document.form.submit();
	</script> 

</body>
</html>

