<jsp:directive.page import="ecar.pojo.ItemEstruturaIett"/>
<jsp:directive.page import="ecar.servlet.geraFilhosIettCadastro.ItemEstruturaCadastroHtml"/>
<jsp:directive.page import="ecar.dao.FuncaoDao"/>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@ taglib uri="/WEB-INF/ecar-util.tld" prefix="util" %>
<%@ taglib uri="/WEB-INF/tlds/c.tld" prefix="c"%>

<%@page import="ecar.dao.EtiquetaDao"%>

<% 
// Esta JSP tem por função mostrar para o usuário comum (não-administrador) a tela de criação de etiquetas

EtiquetaService etiquetaService = new EtiquetaService();
%>


<%@page import="ecar.servico.EtiquetaService"%><html lang="pt-br">
	<head>
	</head>
	
	<body>
		<div id="conteudo">	
			<div id="tituloTelaCadastro">
				<!-- TITULO -->
				<h1>
				<% etiquetaService.getTitulo(); %>
				</h1>
			</div>
		</div>
	<form name="form" method="post" action="EtiquetaService">
	
		<input type="submit">
	</form>
	</body>
</html>