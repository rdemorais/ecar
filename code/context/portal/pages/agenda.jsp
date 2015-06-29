<%@ include file="/login/validaAcesso.jsp"%>
<%@ include file="/frm_global.jsp"%>
<%@ page import="ecar.dao.AgendaOcorrenciaDao" %>

<%@ page language="java" %>
<%@ page import="ecar.pojo.AgendaOcorrenciaAgeo" %>
<%@ page import="comum.util.Data"%>

<%@ page import="java.util.Date" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.Iterator" %>

<%
AgendaOcorrenciaDao agendaDao = new AgendaOcorrenciaDao(request);
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="pt-br">
<head>
<%@ include file="/titulo.jsp"%>
<%@ include file="/include/meta.jsp"%>
<link rel="stylesheet" href="<%=_pathEcar%>/css/style_<%=configuracaoCfg.getEstilo().getNome()%>.css" type="text/css">

<script language="JavaScript" src="<%=request.getContextPath()%>/js/datas.js"></script>

<script>
function detalhe(codAgeo) {
	document.formcalend.codAgeo.value = codAgeo;
	document.formcalend.hidAcao.value = "DETALHE";
	document.formcalend.action = "ctrl_agenda.jsp";	
	document.formcalend.submit();
}

function agendaDia(dia, mes) {	
	var iMes=parseInt(mes);
    iMes+=1;
	document.formcalend.data.value = dia+"/"+iMes+"/"+document.formcalend.Ano.value;
	document.formcalend.hidAcao.value = "AGENDA_DATA";
	document.formcalend.action = "ctrl_agenda.jsp";	
	document.formcalend.submit();
}

function agendaMes(dia, mes, ano) {		
	if(ano != -1 && mes != -1){
	    var iMes=parseInt(mes);
	    iMes+=1;
		document.formcalend.data.value = dia+"/"+iMes+"/"+ano;
		document.formcalend.hidAcao.value = "AGENDA";
		document.formcalend.action = "ctrl_agenda.jsp";	
		document.formcalend.submit();
	}
	else {
		if(mes == -1){
			alert("Mês inválido.");
		}
		else if (ano == -1){
			alert("Ano inválido.");
		}
	}
}
function todaAgenda(){
	document.formcalend.hidAcao.value = "AGENDA_TODOS";
	document.formcalend.action = "ctrl_agenda.jsp";
	document.formcalend.submit();
}
</script>


</head>
<body>
	<%@ include file="/cabecalho.jsp" %>
	<table id="barra_localizacao" cellspacing="0">
		<tr>
			<td><a href="index.jsp">Capa</a> > Agenda</td>
			<td align="right">&lt;&lt;<a href="index.jsp"> Voltar</a></td>
		</tr>
	</table>
	<table cellspacing="0" width="100%" cellpadding="0" class="list_paginacao">
	<tr>
		<td align="left"><h1 class="interno">AGENDA</h1></td>
		<td align="right"><a href="#" onclick="javascript:todaAgenda();">Ver todos os eventos</a> &gt;&gt;</td>
	</tr>
	</table>
	<table cellspacing="0" cellpadding="0" width="100%">
		<tr>
		
			<td width="233" valign="top">
<!-- inicio calendario -->
<div id="areacalendario">
			
			<form name="formcalend" id="formcalend">
<table>
	<tr>
		<td>		
			<select name="Mes">
				<option value="-1">Selecione um mês</option>
				<option value="0">Janeiro</option>
				<option value="1">Fevereiro</option>
				<option value="2">Março</option>
				<option value="3">Abril</option>
				<option value="4">Maio</option>
				<option value="5">Junho</option>
				<option value="6">Julho</option>
				<option value="7">Agosto</option>
				<option value="8">Setembro</option>
				<option value="9">Outubro</option>
				<option value="10">Novembro</option>
				<option value="11">Dezembro</option>
			</select>
		</td>
		<td>
			<select name="Ano">
				<option value="-1">Selecione um ano</option>
				<%
				String anoSelecionado = request.getParameter("Ano");
				
				List anos = agendaDao.getAnosComAgenda();

				Iterator it = anos.iterator();
				while(it.hasNext()) {
					String ano = (String) it.next();
				%>
					<option value="<%=ano%>"
							<%
							if(ano.equals(anoSelecionado)) {
							%>
								selected
							<%
							}
							%>
					>
							<%=ano%>
					</option>
				<%
				}
				%>
			</select>
		</td>
		<td>
<!--			<input type="button" value="Ok" onMouseDown="agendaMes(document.formcalend.Dia.value, document.formcalend.Mes.value, document.formcalend.Ano.value)">-->
			<input type="button" value="Ok" onClick="agendaMes(document.formcalend.Dia.value, document.formcalend.Mes.value, document.formcalend.Ano.value)">
		</td>
	</tr>
</table>

<input type="hidden" name="hidAcao" value="">			
<input type="hidden" name="codAgeo" value="">
<input type="hidden" name="Dia" value="">
<input type="hidden" name="data" value="">

</form>

<div id="areadocalendario"></div>

<SCRIPT LANGUAGE="JavaScript">

var linkcount=0;

var linkdays = new Array();
function addlink(month, day, href) { //o month deve ser o mês do sistema (array) 0=janeiro 1=fevereiro ...
	linkdays[linkcount] = new Array(month,day,href);
	linkcount++;
}
	
<%
List list = (List)request.getSession().getAttribute("listaAgendaMes");

if((list != null) && (list.size() > 0)) {	
    int iMes = 0, iDia = 0;
	Date dataDoCalendario = (Date)request.getSession().getAttribute("data");
	int iAno = Data.getAno(dataDoCalendario);
   
	Iterator iterator = list.iterator();
	while (iterator.hasNext())	{		
		AgendaOcorrenciaAgeo agO = (AgendaOcorrenciaAgeo)iterator.next();		
		int mes = Data.getMes(agO.getDataEventoAgeo());
		int dia = Data.getDia(agO.getDataEventoAgeo());
		int ano = Data.getAno(agO.getDataEventoAgeo());
		if ((iAno == ano) && (iMes != mes || iDia != dia)) {
		%>	
			addlink(<%=mes%>, <%=dia%>,"javascript:agendaDia(<%=dia%>, <%=mes%>);");
		<%	
		}
		iMes = mes; iDia = dia;
	}			
}
%>
	
	
function MontaCalendario(thismonth,thisyear, hoje) {

	todayDate=new Date(thisyear, thismonth, hoje);

	if (thismonth == todayDate.getMonth()) {
		thisday=todayDate.getDay();
		thisdate=todayDate.getDate();
	}
	else {
		first_of_month = new Date(thisyear, thismonth, 1);
		thisday=first_of_month.getDay()-1;
		thisdate='';
	}	
	
	startspaces=thisdate;//inicio do mes

	monthnames = new Array();
	
		monthnames[0]="Janeiro";
		monthnames[1]="Fevereiro";
		monthnames[2]="Março";
		monthnames[3]="Abril";
		monthnames[4]="Maio";
		monthnames[5]="Junho";
		monthnames[6]="Julho";
		monthnames[7]="Agosto";
		monthnames[8]="Setembro";
		monthnames[9]="Outubro";
		monthnames[10]="Novembro";
		monthnames[11]="Dezembro";

	monthdays = new Array(12);
	
		monthdays[0]=31;
		monthdays[1]=28;
		monthdays[2]=31;
		monthdays[3]=30;
		monthdays[4]=31;
		monthdays[5]=30;
		monthdays[6]=31;
		monthdays[7]=31;
		monthdays[8]=30;
		monthdays[9]=31;
		monthdays[10]=30;
		monthdays[11]=31;
	
	//ano bissexto
	thisyear = thisyear % 100;
	thisyear = ((thisyear < 50) ? (2000 + thisyear) : (1900 + thisyear));
	if (((thisyear % 4 == 0) && !(thisyear % 100 == 0))||(thisyear % 400 == 0)) monthdays[1]++;
	//ano bissexto
	
	//data de início na semana
	while (startspaces > 7) startspaces-=7;
	startspaces = thisday - startspaces + 1;
	if (startspaces < 0) startspaces+=7;
	//data de início na semana
	
	//escreve o mês e o ano em cima
	
	calendario = "<table id='tabelacalendario'>";
	//calendario += "<tr><th colspan=7>" + monthnames[thismonth] + " " + thisyear + "</th></tr>";
	calendario += "<tr>";
	calendario += "<th>D</th>";
	calendario += "<th>S</th>";
	calendario += "<th>T</th>";
	calendario += "<th>Q</th>";
	calendario += "<th>Q</th>";
	calendario += "<th>S</th>";
	calendario += "<th>S</th>"; 
	calendario += "</tr>";
	calendario += "<tr>";
	//escreve o mês e o ano em cima
	
	for (s=0;s<startspaces;s++) {
		calendario += "<td class='dia'>&nbsp;</td>";
	}
	
	count=1;
	while (count <= monthdays[thismonth]) {
		for (b = startspaces;b<7;b++) {
			linktrue=false;
			calendario += "<td";
			if (count==thisdate) {
				calendario += " class='hoje'>";
			}
			else {
				calendario += " class='dia'>";
			}
			
			for (c=0;c<linkdays.length;c++) {
				if (linkdays[c] != null) {
					if ((linkdays[c][0]==thismonth) && (linkdays[c][1]==count)) {
						calendario += "<a href=\"" + linkdays[c][2] + "\">";
						linktrue=true;
					}
				}
			}
							
			if (count <= monthdays[thismonth]) {
				calendario += count;
			}
			else {
				calendario += "&nbsp;";
			}
			//coloca o dia de hoje em destaque
			if (linktrue) {
				calendario += "</a>";
			}
			calendario += "</td>";
			count++;
		}
		calendario += "</tr>";
		calendario += "<tr>";
		startspaces=0;
	}
	calendario += "</table></p>";
	
	document.getElementById("areadocalendario").innerHTML = calendario;
	
}

//var dt = new Date();
<%
Date data = (Date)request.getSession().getAttribute("data");
%>

document.formcalend.Dia.value=<%=Data.getDia(data)%>; 
document.formcalend.Mes.selectedIndex=<%=Data.getMes(data)+1%>; //dt.getMonth()+1
document.formcalend.Ano.value=<%=Data.getAno(data)%>; //(1900+dt.getYear());

MontaCalendario(<%=Data.getMes(data)%>, <%=Data.getAno(data)%>, <%=Data.getDia(data)%>);


</SCRIPT>
			
</div>
<!-- fim do calendario -->			
			</td>
			<td valign="top">
			
	
			
	<table cellspacing="0" class="list_agenda">		
	<%
		List lista = (List)request.getSession().getAttribute("listaAgenda"); 

		java.text.SimpleDateFormat sf = new java.text.SimpleDateFormat("dd/MM/yyyy"); 
		if((lista != null) && (lista.size() > 0)) {	
			
			Iterator iterator = lista.iterator();
			while (iterator.hasNext())	{		
				AgendaOcorrenciaAgeo ag = (AgendaOcorrenciaAgeo)iterator.next();		
				
				String sHora = String.valueOf(ag.getHoraEventoAgeo());
				if (sHora.length()==1) sHora = "0"+sHora;
				
				String sMin = String.valueOf(ag.getMinutoEventoAgeo());
				if (sMin.length()==1) sMin = "0"+sMin;
				
				String dataHora = "("+ sf.format(ag.getDataEventoAgeo()) +" - "+ sHora +":"+ sMin +")";				
				%>
				<tr>
					<td>								
						<a href="#" onclick="javascript:detalhe('<%=ag.getCodAgeo()%>');"><%=dataHora%><br><%=ag.getAgendaAge().getEventoAge()%></a>
					</td>
				</tr> 
				<%					
			}			
		}
		else {
		%>
		<tr>
			<td>								
				<%=sf.format(data)%><br>Não existem eventos cadastrados para esta data.
			</td>
		</tr> 
		<%					
		}
	%>	
	</table>
			</td>
		</tr>
</table>
<table cellspacing="0" width="100%" cellpadding="0" class="list_paginacao">
	<tr>
		<td align="right"><a href="#" onclick="javascript:todaAgenda();">Ver todos os eventos</a> &gt;&gt;</td>
	</tr>
</table>
</body>
</html>
