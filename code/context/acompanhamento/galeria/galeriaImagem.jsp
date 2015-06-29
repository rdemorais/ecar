<%@ page import="ecar.dao.ItemEstruturaUploadCategoriaDao" %>
<%@ page import="ecar.dao.ConfiguracaoDao" %>

<%@ page import="comum.util.Pagina" %>
<%@ page import="comum.util.Mensagem" %> 

<%@ include file="/login/validaAcesso.jsp"%>
<head>
<%@ include file="../../include/meta.jsp"%>
<%
	Mensagem _msg = new Mensagem(application);
	String _pathEcar = request.getContextPath();
%>
<%@ include file="/titulo.jsp"%>
<link rel="stylesheet" href="<%=_pathEcar%>/css/style_<%=configuracaoCfg.getEstilo().getNome()%>.css" type="text/css">
</head>
<%
	ItemEstruturaUploadCategoriaDao itemEstUCDao = new ItemEstruturaUploadCategoriaDao(request); 
	
	String strCodIettuc = Pagina.getParamStr(request, "codIettuc");
%>
<FRAMESET cols="70%, 30%" border="0">
	<FRAME name="imagem" src="galeriaImagemVisualizacao.jsp">
	<FRAME name="menu" src="galeriaImagemMenu.jsp?codIettuc=<%=strCodIettuc%>">
</FRAMESET>

</html>