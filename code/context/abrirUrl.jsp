<%@ page import="java.util.Enumeration" %>
<%
// controle para remover objetos da sessão que não são mais necessários
Enumeration e = session.getAttributeNames();
while (e.hasMoreElements()) {
	String atr = e.nextElement().toString();

	// teste para não remover objetos necessários:
	// - SENTINELA_xxxx: objetos utilizados pelo Sentinela
	// - seguranca: objeto contendo dados do usuário logado
	if(!atr.startsWith("SENTINELA_")
		&& !"seguranca".equals(atr)
		&& !atr.equals("configuracao")
		&& !atr.equals("portal")
		&& !atr.equals("cabEmpresa")) {
		session.removeAttribute(atr);
	}
}

session.setAttribute("titulo", request.getParameter("titulo"));
%>
<script language="Javascript">
	window.location.href="<%=request.getParameter("url")%>";
</script>
