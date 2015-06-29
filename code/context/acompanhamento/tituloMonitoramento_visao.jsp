<!-- TÍTULO -->
<h1>
<%
if(session.getAttribute("titulo") != null) {
	// teste necessário para tela inicial que estava aparecendo null 
	if(!session.getAttribute("titulo").toString().equals("null")){
	
		if(visaoDescricao != null){
			out.print(session.getAttribute("titulo").toString()+ " > " + visaoDescricao+" > "+tituloMonitoramento);
		} else{
			out.print(session.getAttribute("titulo").toString()+" > "+tituloMonitoramento);
		}		
	}
} else {
	out.print("MONITORAMENTO"+" > "+tituloMonitoramento);
}
%>
</h1><br>