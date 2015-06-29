<jsp:useBean id="seguranca" scope="session" class="ecar.login.SegurancaECAR"/>
<%
//nao pode utilizar ponto e virgula na URL, pois dá erro no tomcat < 5.5.9
String pagina="/sair.jsp?msgOperacao=Usuario nao autenticado!";
// O request.getHeader("referer") == null não funciona corretamente no IE
//if (seguranca == null || !seguranca.isAutenticado() || request.getHeader("referer") == null)
if (seguranca == null || !seguranca.isAutenticado())
{%>
    <jsp:forward page="<%=pagina%>" />
<%
}
%>