<h1>
<%
if(session.getAttribute("titulo") != null) {
	
	// teste necess�rio para tela inicial que estava aparecendo null 
	if(!session.getAttribute("titulo").toString().equals("null"))
		out.print(session.getAttribute("titulo").toString());
	
}
else {
	out.print("");
}
%>
</h1>