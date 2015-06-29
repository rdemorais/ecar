<%@ page import="gov.pr.celepar.sentinela.comunicacao.*" %>
<%@ taglib uri="/WEB-INF/ecar-util.tld" prefix="util" %>

<%@ page import="ecar.dao.ConfiguracaoDao" %>
<%@ page import="ecar.pojo.ConfiguracaoCfg" %>

<%
ConfiguracaoCfg configuraMn = (ConfiguracaoCfg)session.getAttribute("configuracao");
%>
<link href="css/style_<%=configuraMn.getEstilo().getNome()%>.css" rel="stylesheet" type="text/css">

<script type="text/javascript">
	/* -- 
	 * Criado para resolver o problema dos paths de cookies 
	 * -- */
	var __pathSystemCookie = '<%=_pathEcar%>'; 
</script>
<script type="text/javascript" src="<%=_pathEcar%>/js/dtree.js"></script>
<script type="text/javascript">
	<!--			
	<util:menu request="<%=request%>"/>
    -->
</script>
