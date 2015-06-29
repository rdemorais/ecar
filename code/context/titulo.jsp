<%@page import="ecar.pojo.ConfiguracaoCfg"%>
<%
	ConfiguracaoCfg configuracaoCfg = (ConfiguracaoCfg)session.getAttribute("configuracao");
%>
<title><%=configuracaoCfg.getTituloSistema()%></title>