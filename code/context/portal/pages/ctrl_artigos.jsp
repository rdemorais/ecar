<%@ include file="/login/validaAcesso.jsp"%>
<%@ include file="/frm_global.jsp"%>

<%@ page import="ecar.dao.SegmentoItemDao" %>
<%@ page import="ecar.pojo.UsuarioUsu" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.Iterator" %>
 
<html lang="pt-br">
<head>
<%@ include file="/include/meta.jsp"%>
<%@ include file="/titulo.jsp"%>
</head>
<body>

<form name="form" method="post">
<!-- campo de controle para mensagens -->
<input type="hidden" name="msgOperacao" value=""> 
</form>

<%
UsuarioUsu usuario = new UsuarioUsu();
usuario = ((ecar.login.SegurancaECAR)session.getAttribute("seguranca")).getUsuario();
String submit = "artigos.jsp";	

try{
	String msg = null;
	String acao = Pagina.getParamStr(request, "hidAcao");
	String codSatb = Pagina.getParamStr(request, "codSatb");
	String descrSatb = Pagina.getParamStr(request, "descrSatb");
	if ("".equals(acao)) {
		acao = (request.getParameter("acao") != null ? request.getParameter("acao") : "");
	}

	SegmentoItemDao segmentoItemDao = new SegmentoItemDao(request);
	
		if ("ARTIGOS_DESTAQUE".equals(acao) || "GENERICA".equals(acao)){
			request.setAttribute("qtd", "10");
			
			List list = segmentoItemDao.getSegmentoItemMaisArtigos(request.getSession().getServletContext(),usuario);
			long l  = 0; 
			l = segmentoItemDao.getQtdSegmentoItemMaisArtigos(request.getSession().getServletContext(),usuario);
			request.getSession().setAttribute("qtdSegmentoItem", Long.valueOf(l));
			request.getSession().setAttribute("strQtdSegmentoItem", (l>10?"10 artigos de "+l:l+" artigos de "+l));
			request.getSession().setAttribute("listaSegmentoItem", list);
			request.setAttribute("todos", "");
		} else if ("ARTIGOS_TODOS".equals(acao)){
			submit = "artigos_todos.jsp";	
			request.setAttribute("qtd", "00");			
			request.setAttribute("todos", "todos");		
			request.getSession().setAttribute("listaArtigos", segmentoItemDao.getSegmentoItemMaisArtigos(request.getSession().getServletContext(),usuario));
							
		} else if ("ARTIGOS_POR_EDITORIA".equals(acao)){
			submit = "artigos_todos.jsp?codSatb=" + codSatb + "&descrSatb=" + descrSatb;	
			request.setAttribute("qtd", "00");			
			request.setAttribute("todos", "todos");
			request.setAttribute("codSatb", codSatb);
			request.setAttribute("descrSatb", descrSatb);					
			request.getSession().setAttribute("listaArtigos", segmentoItemDao.getSegmentoItemArtigosPorEditoria(request.getSession().getServletContext(),usuario,codSatb));
							
		}
	
		
	if (msg != null)
		Mensagem.setInput(_jspx_page_context, "document.form.msgOperacao.value", msg);
} catch (Exception e){
%>
	<%@ include file="/excecao.jsp"%>
<%
}
%>
	<script language="javascript">
		document.form.action = "<%=submit%>";
		document.form.submit();
	</script> 

</body>
</html>

