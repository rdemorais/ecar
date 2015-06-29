<%@ page import="comum.util.Pagina" %>
<%@ page import="comum.util.Mensagem" %>
<%@ page import="comum.util.EcarCfg" %>
<%@ page import="comum.database.SentinelaUtil" %>
<%@ page import="ecar.exception.ECARException" %>
<%@ page import="ecar.permissao.ValidaPermissao" %>

<%
SentinelaUtil sentinelaUtil = new SentinelaUtil(request);

Mensagem _msg = new Mensagem(application);
EcarCfg _cfg = new EcarCfg(application);
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

<script language="javascript" src="<%=_pathEcar%>/js/cookie.js"></script>