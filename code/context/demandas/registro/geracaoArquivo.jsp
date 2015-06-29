<%@ include file="/login/validaAcesso.jsp"%>
<%@ include file="/frm_global.jsp"%>
<%@page import="ecar.pojo.VisaoDemandasVisDem"%>
<%@page import="ecar.dao.VisaoDao"%>
<%@page import="ecar.pojo.UsuarioUsu"%>
<%@ page import="comum.util.Util" %>
<jsp:directive.page import="ecar.taglib.util.Input"/>
<jsp:directive.page import="java.util.List"/>
<jsp:directive.page import="java.util.Iterator"/>

<%@ taglib uri="/WEB-INF/ecar-util.tld" prefix="util"%>
<%@ taglib uri="/WEB-INF/ecar-combo.tld" prefix="combo"%>
<%@ taglib uri="/WEB-INF/tlds/c.tld" prefix="c"%> 

<%@page import="ecar.pojo.UsuarioUsu"%>
<%@page import="ecar.dao.ExportacaoRelatorioDemandasDao"%>

<jsp:directive.page import="java.util.HashMap"/>
<jsp:directive.page import="java.util.Map"/>
<%@ taglib uri="/WEB-INF/ecar-util.tld" prefix="util" %>


<html lang="pt-br">
	<head>
		<%@ include file="/include/meta.jsp"%>
		<%@ include file="/titulo.jsp"%>
		<link href="<%=_pathEcar%>/css/style.css" rel="stylesheet" type="text/css">
		<script language="javascript" src="<%=_pathEcar%>/js/menu_retratil.js" type="text/javascript"></script>
		<script language="javascript" src="<%=_pathEcar%>/js/focoInicial.js" type="text/javascript"></script>
		<script language="javascript" src="<%=_pathEcar%>/js/frmPadrao.js" type="text/javascript"></script>
		<script language="javascript" src="<%=_pathEcar%>/js/validacoes.js" type="text/javascript"></script>
		<script language="javascript" src="<%=_pathEcar%>/js/richtext.js" type="text/javascript"></script>	
		<script language="javascript" src="<%=_pathEcar%>/js/cookie.js"></script>
	</head>
	
<%@ include file="/cabecalho.jsp"%>
<%@ include file="/divmenu.jsp"%>

<div id="conteudo">
	<form name="form" method="post" >
	<!-- TITULO -->
	<%@ include file="/titulo_tela.jsp"%>		
	
<%
if(session.getAttribute("titulo") == null || session.getAttribute("titulo").toString().equals("null")) {
	
	%>
	<h1>
	Demandas > Cadastramento Demandas 
	</h1>
	<%
}


List listaRegistros = (List) session.getAttribute("listaRegistrosRelatorio");
String hashNomeArquivo = null;
UsuarioUsu usuarioImagem = null; 
int visaoParameter = 0;

//Aqui começa o download do arquivo... 
try{
	
	// recupera o id da visao atual selecionada
	visaoParameter = ((VisaoDemandasVisDem)request.getSession().getAttribute(VisaoDemandasVisDem.VISAO_SELECIONADA)).getCodVisao().intValue();
	
	ExportacaoRelatorioDemandasDao exporDao = new ExportacaoRelatorioDemandasDao(request);

	String[] arquivoExportado = exporDao.gerarArquivoExportacaoTxt("ListaDemandas", (List) session.getAttribute("listaRegistrosRelatorio"), new Long(visaoParameter), configuracao);

	usuarioImagem = ((ecar.login.SegurancaECAR)request.getSession().getAttribute("seguranca")).getUsuario();  
	
	// código de tratamento de permissão de acesso a arquivo
	hashNomeArquivo = Util.calcularHashNomeArquivo(null, arquivoExportado[0]);
	Util.adicionarMapArquivosAtuaisUsuarios(usuarioImagem, hashNomeArquivo, null, arquivoExportado[0]);
	
%>	
<table class="barrabotoes" cellpadding="0" cellspacing="0">
<tr>


<td  align="left"> Arquivo </td></td>
</tr>
</table>				
<table class="form">
<tr>
	
	<td align="left"> 
	<a href="<%=request.getContextPath()%>/DownloadFile?RemoteFile=<%=hashNomeArquivo%>")">ListaDemandas_export.txt</a>
	</td>
</tr>
</table>
</form>
		</div>
	</body>
	<%@ include file="/include/estadoMenu.jsp"%>
	
<%
		
	} catch (Exception e) {
		org.apache.log4j.Logger.getLogger(this.getClass()).error(e);
		Mensagem.alert(_jspx_page_context, _msg.getMensagem("erro.exception"));
	}
%>	
	
	
		<%@ include file="/include/mensagemAlert.jsp"%>
</html>