<html lang="pt-br">
	<head>
	<%@ include file="../../include/meta.jsp"%>
	</head>

	<body>
<%
		session.setAttribute("classifica",  "classifica");
%>
   		<jsp:forward page="lista.jsp" />
	</body>
</html>