<html lang="pt-br">
	<head>
	<%@ include file="../../include/meta.jsp"%>
	</head>

	<body>
<%
	session.setAttribute("indTabelaUso",  "E");
%>
   <jsp:forward page="frm_inc.jsp" />
	</body>
</html>