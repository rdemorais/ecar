<%@ page import="java.util.Enumeration" %>

<!--
	rhjort (29/07/2004)
	p�gina espec�fica para debug de par�metros
	para utiliz�-la, inclua a linha a seguir no JSP:
	< %@ include file="../debugador.jsp" %>
-->

<hr>

<h2>Debugador</h2>

<p>
<b>Parameters:</b>
<br>
 <%
	Enumeration parmNames = request.getParameterNames();
	while (parmNames.hasMoreElements()) {
   		String name = parmNames.nextElement().toString();
   		String value = request.getParameter(name);
   		out.println(name + ": " + value + "<br>");
	}
%>

<p>
<b>Attributes:</b>
<br>
<%
	Enumeration attrNames = request.getAttributeNames();
	while (attrNames.hasMoreElements()) {
		String name = attrNames.nextElement().toString();
		String value = request.getAttribute(name).toString();
   		out.println(name + ": " + value + "<br>");
	}
%>

<hr>