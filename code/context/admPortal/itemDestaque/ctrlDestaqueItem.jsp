<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.List" %>
<%@ page import="ecar.pojo.SegmentoItemSgti" %>
<%@ page import="ecar.dao.SegmentoItemDao" %>
<%@ page import="ecar.pojo.SubAreaSare" %>
<%@ page import="ecar.pojo.DestaqueSubAreaDtqsa" %>
<%@ page import="ecar.dao.DestaqueSubAreaDao" %>
<%@ page import="ecar.exception.ECARException" %>
<%@ page import="ecar.pojo.DestaqueItemRelDtqir" %>
<%@ page import="ecar.pojo.AgendaOcorrenciaAgeo" %>
<%@ page import="ecar.dao.AgendaDao" %>
<%@ include file="../../frm_global.jsp"%>
<%@ include file="../../login/validaAcesso.jsp"%>

<html lang="pt-br"> 
<head>
<%@ include file="../../include/meta.jsp"%>
</head>
<body>
<form name="form" method="post">  

<!-- campo de controle para mensagens -->
<input type="hidden" name="msgOperacao" value=""> 
<input type="hidden" name="codSubArea" value="<%=Pagina.getParamStr(request, "codSubArea")%>"> 
<input type="hidden" name="segAgenda" value="<%=Pagina.getParamStr(request, "segAgenda")%>"> 
<input type="hidden" name="segmentoCategoriaSgtc" value="<%=Pagina.getParamStr(request, "segmentoCategoriaSgtc")%>"> 

<%
String submit = "";
String msg = "";
try{
	Long codSubArea = Long.valueOf(Pagina.getParamStr(request, "codSubArea"));
	DestaqueSubAreaDtqsa subArea = (DestaqueSubAreaDtqsa) new DestaqueSubAreaDao(request).buscar(DestaqueSubAreaDtqsa.class, codSubArea);
	// vincular segmentos
	if("S".equals(Pagina.getParamStr(request, "tipoVinculo"))){
		String[] codItens = request.getParameterValues("segmentoItem");
		List itens = new ArrayList();
		for(int i = 0; i<codItens.length;i++){
			SegmentoItemSgti segmentoItem = (SegmentoItemSgti) new SegmentoItemDao(request).buscar(SegmentoItemSgti.class, Long.valueOf(codItens[i]));
			itens.add(segmentoItem);
		}
		new DestaqueSubAreaDao(request).vincularSegmentosaSubArea(itens, subArea);
		msg = _msg.getMensagem("admPortal.itemDestaque.inclusao.sucesso");
		submit = "destaqueItem.jsp";
	}	
	// vincular agendas ocorrencia
	if("A".equals(Pagina.getParamStr(request, "tipoVinculo"))){
		String[] codItens = request.getParameterValues("segmentoItem");
		List itens = new ArrayList();
		for(int i = 0; i<codItens.length;i++){
			AgendaOcorrenciaAgeo agendaOcorrencia = (AgendaOcorrenciaAgeo) new AgendaDao(request).buscar(AgendaOcorrenciaAgeo.class, Long.valueOf(codItens[i]));
			itens.add(agendaOcorrencia);
		}
		new DestaqueSubAreaDao(request).vincularAgendasSubArea(itens, subArea);
		msg = _msg.getMensagem("admPortal.itemDestaque.inclusao.sucesso");
		submit = "destaqueItem.jsp";
	}		
	// excluir segmentos
	if("EXC".equals(Pagina.getParamStr(request, "tipoVinculo"))){
		DestaqueSubAreaDao dao = new DestaqueSubAreaDao(request);
		String[] codItens = request.getParameterValues("segmentoItemVinculados");		
		List itens = new ArrayList();
		for(int i = 0; i<codItens.length;i++){
			DestaqueItemRelDtqir segmentoItem = (DestaqueItemRelDtqir) dao.buscar(DestaqueItemRelDtqir.class, Long.valueOf(codItens[i]));
			itens.add(segmentoItem);
		}
		dao.desvincularDestaquesSubArea(itens);
		msg = _msg.getMensagem("admPortal.itemDestaque.exclusao.sucesso");
		submit = "destaqueItem.jsp";
	}
} catch (Exception e){
%>
	<%@ include file="/excecao.jsp"%>
<%
}
if (msg != null)
	Mensagem.setInput(_jspx_page_context, "document.form.msgOperacao.value", msg); 
%>

<script>
			document.form.action = "<%=submit%>";
			document.form.submit();
</script>

</form>
</body>
</html>