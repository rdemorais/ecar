<%@ include file="../../frm_global.jsp"%>
<jsp:directive.page import="comum.util.Pagina"/>
<jsp:directive.page import="ecar.pojo.AcompRealFisicoArf"/>
<jsp:directive.page import="ecar.dao.AcompRealFisicoDao"/>
<jsp:directive.page import="ecar.pojo.SituacaoSit"/>
<jsp:directive.page import="ecar.dao.SituacaoDao"/>
<jsp:directive.page import="java.util.List"/>

<%
	AcompRealFisicoDao arfDao = new AcompRealFisicoDao(request);
	AcompRealFisicoArf arf = (AcompRealFisicoArf) arfDao.buscar(AcompRealFisicoArf.class, Long.valueOf(Pagina.getParamStr(request, "verificaCodArf")));
	SituacaoSit situacao = (SituacaoSit) new SituacaoDao(request).buscar(SituacaoSit.class, Long.valueOf(Pagina.getParamStr(request, "verificaCodSit")));

	if("S".equals(situacao.getIndConcluidoSit())){
		List arfsPosteriores = arfDao.getArfsPosteriores(arf);
		
		if(arfsPosteriores != null && !arfsPosteriores.isEmpty()){
%>
			<script language="javascript">
				alert("<%=_msg.getMensagem("periodoReferencia.elaboracao.realizadoFisico.verificaSituacao.concluido")%>");
			</script>
<%		
		}
	}
%>
