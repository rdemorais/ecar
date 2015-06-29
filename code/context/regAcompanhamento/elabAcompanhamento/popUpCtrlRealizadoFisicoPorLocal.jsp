<%@ page import="ecar.dao.AcompRealFisicoLocalDao" %>
<%@ page import="ecar.pojo.AcompRealFisicoLocalArfl" %>
<%@ page import="ecar.dao.LocalItemDao" %>
<%@ page import="ecar.pojo.LocalItemLit" %>
<%@ page import="ecar.dao.AcompRealFisicoDao" %>
<%@ page import="ecar.pojo.AcompRealFisicoArf" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="comum.util.Util" %>
<%@ taglib uri="/WEB-INF/ecar-util.tld" prefix="util" %>
<%@ include file="../../frm_global.jsp"%>

<html lang="pt-br">
<head>
<%@ include file="../../include/meta.jsp"%>
</head>

<body>
<form name="form" method="post">  
	<input type="hidden" name="codARF" value="<%=Pagina.getParamStr(request, "codARF")%>">
	<input type="hidden" name="nomeIndicador" value="<%=Pagina.getParamStr(request, "nomeIndicador")%>">
	<input type="hidden" name="podeGravar" value="<%=Pagina.getParamStr(request, "podeGravar")%>">
	
	<%
	String acao = Pagina.getParamStr(request, "hidAcao");
	AcompRealFisicoLocalDao acompRealFisicoLocalDao = new AcompRealFisicoLocalDao(request);
	LocalItemDao localItemDao = new LocalItemDao(request);
	AcompRealFisicoDao acompRealFisicoDao = new AcompRealFisicoDao(request);
	
	String msg = "";
	String submit = "popUpRealizadoFisicoPorLocal.jsp";
	try{
		if("incluir".equals(acao)) {
			AcompRealFisicoLocalArfl arfl = new AcompRealFisicoLocalArfl();
			arfl.setAcompRealFisicoArf((AcompRealFisicoArf)acompRealFisicoDao.buscar(AcompRealFisicoArf.class, Long.valueOf(Pagina.getParamStr(request, "codARF"))));
			arfl.setLocalItemLit((LocalItemLit)localItemDao.buscar(LocalItemLit.class, Long.valueOf(Pagina.getParamStr(request, "codLit"))));
			arfl.setQuantidadeArfl(Double.valueOf(Util.formataNumero(Pagina.getParamStr(request, "qtde"))));
			acompRealFisicoLocalDao.salvar(arfl);
			msg = _msg.getMensagem("acompanhamento.realizadoFisico.quantidadePorLocal.inclusao.sucesso");
		}
		else if("excluir".equals(acao)) {
			List listArfl = new ArrayList();
			if(request.getParameterValues("excluir") != null) {
				int i = 0;
				while(i < request.getParameterValues("excluir").length) {
					listArfl.add(request.getParameterValues("excluir")[i]);
					i++;
				}
				acompRealFisicoLocalDao.excluir(listArfl, Long.valueOf(Pagina.getParamStr(request, "codARF")));
				msg = _msg.getMensagem("acompanhamento.realizadoFisico.quantidadePorLocal.exclusao.sucesso");
			}
		}
	} catch ( ECARException e){
		org.apache.log4j.Logger.getLogger(this.getClass()).error(e);
		msg = _msg.getMensagem(e.getMessageKey());
	} catch (Exception e){
	%>
		<%@ include file="/excecao.jsp"%>
	<%
	}
	%>
	<script>
		alert("<%=msg%>");
		window.opener.document.form.submit();
		document.form.action="<%=submit%>";
		document.form.submit();
	</script>
</form>
</body>
</html>