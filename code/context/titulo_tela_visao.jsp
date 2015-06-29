<h1>
<%
if(session.getAttribute("titulo") != null) {
	
	// teste necessário para tela inicial que estava aparecendo null 
	if(!session.getAttribute("titulo").toString().equals("null")){
		if(visaoDescricao != null){
			out.print(session.getAttribute("titulo").toString() + " > " + visaoDescricao);
		} else{
			out.print(session.getAttribute("titulo").toString());
		}
	}
	
}
else {
	out.print("");
}
%>
</h1>