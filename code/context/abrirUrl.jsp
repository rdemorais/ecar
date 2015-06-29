<%@ page import="java.util.Enumeration" %>
<%
// controle para remover objetos da sess�o que n�o s�o mais necess�rios
Enumeration e = session.getAttributeNames();
while (e.hasMoreElements()) {
	String atr = e.nextElement().toString();

	// teste para n�o remover objetos necess�rios:
	// - SENTINELA_xxxx: objetos utilizados pelo Sentinela
	// - seguranca: objeto contendo dados do usu�rio logado
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
