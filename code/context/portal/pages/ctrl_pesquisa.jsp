<%@ page language="java"%>
<!--  %@ taglib uri="http://celepar.pr.gov.br/taglibs/html-1.0" prefix="ch" % -->
<%@ taglib uri="/WEB-INF/ecar-util.tld" prefix="util" %>

<%@ include file="/login/validaAcesso.jsp"%>
<%@ include file="/frm_global.jsp"%>

<%@ page import="ecar.portal.bean.SecaoBean" %>
<%@ page import="ecar.portal.bean.InformacaoBean" %>
<%@ page import="ecar.dao.SegmentoItemDao" %>
<%@ page import="ecar.dao.AgendaOcorrenciaDao" %>
<%@ page import="ecar.pojo.AgendaOcorrenciaAgeo" %>
<%@ page import="ecar.pojo.SegmentoItemSgti" %>
<%@ page import="ecar.pojo.UsuarioUsu" %>  
<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.Iterator" %>
<%@ page import="java.util.Comparator" %>
<%@ page import="comum.util.Data" %>
<%@ page import="ecar.dao.AgendaDao" %>
<%@ page import="java.util.Arrays" %>
<%@ page import="java.util.Date" %>
<%@ page import="java.lang.reflect.Array" %>
<%@ page import="org.jfree.chart.needle.ArrowNeedle" %>

 
<html lang="pt-br">
<head>
<%@ include file="/include/meta.jsp"%>
<%@ include file="/titulo.jsp"%>
</head>
<body>

<form name="form" method="post">
<!-- campo de controle para mensagens -->
<input type="hidden" name="msgOperacao" value=""> 
</form>

<%
String submit = "resultado_pesquisa.jsp";	
try{
	String msg = null;
	String acao = Pagina.getParamStr(request, "hidAcao");
	
	if ("".equals(acao)) {
		acao = (request.getParameter("acao") != null ? request.getParameter("acao") : "");
	}

	UsuarioUsu usuario = new UsuarioUsu();	
	usuario = ((ecar.login.SegurancaECAR)session.getAttribute("seguranca")).getUsuario();
	
	SegmentoItemDao segmentoItemDao = new SegmentoItemDao(request);
	AgendaOcorrenciaDao agendaOcorrenciaDao = new AgendaOcorrenciaDao(request);
	
	if ("PESQUISAR".equals(acao)){
		String[] segmentos = Pagina.getParamStr(request, "codSegmentos").split(",");
		String codSgti = "";
		boolean agenda = false;
		SecaoBean ret = new SecaoBean();
		List listAgenda = new ArrayList();
		List listSegmentoItem = new ArrayList();
		
		for (int i = 0; i < segmentos.length; i++) {
			if ((!"T".equals(segmentos[i]))&&(!"A".equals(segmentos[i])))	{
				if (codSgti!="")	codSgti += ",";
				codSgti += segmentos[i];
			} else agenda = true;
		}
		if (!"".equals(codSgti))	{			
										
			request.setAttribute("codSgtis", codSgti);	
			List lista = segmentoItemDao.pesquisar(usuario);			
			
			if ((lista!=null)&&(!lista.isEmpty())) {
				Iterator iterator = lista.iterator();
				while (iterator.hasNext())	{		
					SegmentoItemSgti seg = (SegmentoItemSgti)iterator.next();			
					InformacaoBean inf = new InformacaoBean();
					if (seg.getSegmentoSgt().getCodSgt().intValue() == (Long.valueOf (_msg.getMensagem("admPortal.taxacoes"))).intValue()) {
						inf.setFonte("NA M&Iacute;DIA");					
						inf.setLink("ctrl_taxacao.jsp");
					}
					if (seg.getSegmentoSgt().getCodSgt().intValue() == (Long.valueOf (_msg.getMensagem("admPortal.materias"))).intValue()) {
						inf.setFonte("ARTIGO");
						//inf.setLink();
					}
					inf.setTitulo(seg.getTituloSgti());
					inf.setDataHora(Data.parseDate(seg.getDataItemSgti()));
					inf.setCodSgti(seg.getCodSgti());
					listSegmentoItem.add(inf);
				}				
				ret.setInformacaoBean(listSegmentoItem);
			}
		}
		if (agenda) {
			request.setAttribute("dataDe", Pagina.getParamStr(request, "dataInicial"));
			request.setAttribute("dataAte", Pagina.getParamStr(request, "dataFinal"));
			request.setAttribute("argumento", Pagina.getParamStr(request, "palavra"));
						
			List lista = agendaOcorrenciaDao.pesquisar(request);
			
			if ((lista!=null)&&(!lista.isEmpty())) {
				Iterator iterator = lista.iterator();
				while (iterator.hasNext())	{		
					AgendaOcorrenciaAgeo ag = (AgendaOcorrenciaAgeo)iterator.next();		
					InformacaoBean inf = new InformacaoBean();
					inf.setFonte("AGENDA");
					inf.setTitulo(ag.getAgendaAge().getEventoAge());
					inf.setDataHora(Data.parseDate(ag.getDataEventoAgeo()));
					inf.setLink("ctrl_agenda.jsp");
					inf.setCodSgti(ag.getCodAgeo());
					listAgenda.add(inf);
				}				
				ret.setInformacaoBean(listAgenda);
			}
		}
		if (!(listAgenda.isEmpty())&&!(listSegmentoItem.isEmpty())) {		
			listSegmentoItem.addAll(listAgenda);
			Object[] arrObject = listSegmentoItem.toArray();
			Arrays.sort( arrObject,
            	new Comparator() {
	                public int compare( Object obj1, Object obj2 ) {
	                   	return (Data.parseDate(((InformacaoBean)obj2).getDataHora())).compareTo((Data.parseDate(((InformacaoBean)obj1).getDataHora())));
	                }
            	}
        	);
        	ret.setInformacaoBean(Arrays.asList(arrObject));
		}		
		request.getSession().setAttribute("itensPesquisa", ret);
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

