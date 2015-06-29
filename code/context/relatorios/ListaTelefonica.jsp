<%@ taglib uri="/WEB-INF/ecar-util.tld" prefix="util" %>
<%@ include file="../../login/validaAcesso.jsp"%>
<%@ include file="../../frm_global.jsp"%>

<%@ page import="ecar.dao.UsuarioDao" %>
<jsp:directive.page import="comum.util.Pagina"/>


<%@page import="comum.util.Util"%>
<%@page import="ecar.pojo.UsuarioUsu"%><html lang="pt-br">
<head>
<%@ include file="../include/meta.jsp"%>
<%@ include file="/titulo.jsp"%>
<link rel="stylesheet" href="<%=_pathEcar%>/css/style_<%=configuracaoCfg.getEstilo().getNome()%>.css" type="text/css">
<script language="javascript" src="../js/menu_retratil.js"></script>
<script language="javascript" src="../js/focoInicial.js"></script>
<script language="javascript" src="../js/frmPadrao.js"></script>
<script language="javascript" src="../js/validacoes.js"></script>

<script type="text/javascript">

function filtraPorIndAtivo(status){
	
	document.form.indAtivoUsu.value = status;
	document.form.action = "ListaTelefonica.jsp";
	document.form.submit();

}

</script>
</head>

<!-- sempre colocar o foco inicial no primeiro campo -->
<body>

<%@ include file="../cabecalho.jsp" %>
<%@ include file="../divmenu.jsp"%>

<%
try{
	StringBuffer html = new StringBuffer("");
	String status = Pagina.getParamStr(request, "indAtivoUsu");
	if("".equals(status) || status == null){
		status = "ativo";
	}
	
	String pathRaiz = new ecar.dao.ConfiguracaoDao(request).getConfiguracao().getRaizUpload();
	
	String hashNomeArquivo = null;
	UsuarioUsu usuarioImagem = null; 
	
	String paginaLista = new UsuarioDao(request).geraListaTelefonica(html, status);
	
	hashNomeArquivo = Util.calcularHashNomeArquivo(pathRaiz, paginaLista);
	usuarioImagem = ((ecar.login.SegurancaECAR)request.getSession().getAttribute("seguranca")).getUsuario();  
	Util.adicionarMapArquivosAtuaisUsuarios(usuarioImagem, hashNomeArquivo, pathRaiz, paginaLista);
	
			
	String linkDownload = request.getContextPath()+"/DownloadFile?RemoteFile="+hashNomeArquivo;
%>

<div id="conteudo">
	<!-- TITULO -->
	<%@ include file="/titulo_tela.jsp"%>
<form name="form" method="post">
	<input type=hidden name="indAtivoUsu" id="indAtivoUsu" value="">
	<table class="form">
	 	<a href="<%=request.getContextPath()%>/DownloadFile?RemoteFile=<%=hashNomeArquivo%>">
	 		Clique aqui para fazer o download da Lista de Telefones (formato .txt)</a>		
	 			
	</table>
	<p>
	<%=html%>
</form>
<% /* Controla o estado do Menu (aberto ou fechado) */%>
<%@ include file="../include/estadoMenu.jsp"%>
</div>
<%
} catch (Exception e){
%>
	<%@ include file="/excecao.jsp"%>
<%
}
%>

</body>
<%@ include file="../include/mensagemAlert.jsp"%>
</html>
