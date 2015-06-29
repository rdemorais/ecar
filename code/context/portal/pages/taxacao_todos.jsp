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


<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<html lang="pt-br">
<head>
<%@ include file="/titulo.jsp"%>
<%@ include file="/include/meta.jsp"%>
<link rel="stylesheet" href="<%=_pathEcar%>/css/style_<%=configuracaoCfg.getEstilo().getNome()%>.css" type="text/css">

<script language="javascript" src="../../js/frmPesquisa.js"></script>
<SCRIPT>

function detalhe(codSgti) {
	document.form.codSgti.value = codSgti;
	document.form.hidAcao.value = "DETALHE";
	document.form.action = "ctrl_taxacao.jsp";	
	document.form.submit();
}

</SCRIPT>

</head>
<body>
<form name="form" id="form">

	<input type="hidden" name="codSgti" value=""> 
	<input type="hidden" name="hidAcao" value=""> 

<%@ include file="/cabecalho.jsp" %>
	<table id="barra_localizacao" cellspacing="0">
		<tr>
			<td><a href="index.jsp">Capa</a> > <a href="taxacao.jsp">NA M&Iacute;DIA</a> > Todas as Imagens na m&iacute;dia </td>
			<td align="right">&lt;&lt;<a href="taxacao.jsp"> Voltar</a></td>
		</tr>
	</table>
	<h1 class="interno">NA M&Iacute;DIA</h1>
    <div id="list_moldura">
	<%
	try {
		final int ITENS_PAGINA = _msg.getQtdeItensPaginaPesquisa("portal.taxacoes.itensPagina");
			
		int hidNumPagina = Pagina.getParamInt(request, "hidNumPagina");
		int hidTotPagina = Pagina.getParamInt(request, "hidTotPagina");
		
		List lista = (List) session.getAttribute("listaTaxacoes");
		
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
			SegmentoItemSgti seg = (SegmentoItemSgti)lista.get(i);
			
			Date dt = seg.getDataItemSgti();
						
			out.println("<tr><td>");
			
			mes_dt = Data.getMes(dt)+1;
			if (mes_dt != mes) {
				out.println("<h1>"+Data.getNomeMesExtenso(mes_dt)+" / "+Data.getAno(dt)+"</h1>");
				mes = mes_dt;
			}
						
			out.println("<a href=\"#\" onclick=\"javascript:detalhe('"+seg.getCodSgti().longValue()+"');\">("+ sf.format(dt) +") "+
				seg.getSegmentoItemFonteSgtif().getDescricaoSgtif()+" - "+seg.getTituloSgti()+"</td></tr>");
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
