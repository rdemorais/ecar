<%@ page import="comum.util.Pagina" %>
<%@ page import="comum.util.Mensagem" %> 
<%@ page import="ecar.exception.ECARException" %>

<%
Mensagem _msg = new Mensagem(application);
String _disabled = "";
String _readOnly = "";
String _comboSimNao = null;
String _obrigatorio = "*";
String _pathEcar = request.getContextPath();   /* ex. "/ecar" */
String _urlHome = _pathEcar + "/index.jsp";

%>
<script language="javascript">
_pathEcar = "<%=_pathEcar%>";
</script>