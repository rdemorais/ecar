<%
//Author: Carlos Eduardo
//Data  : 17/05/2007
%>

<%@ page language="java" import="java.util.*" pageEncoding="ISO-8859-1"%>

<%@ page import="java.util.List" %>
<%@ page import="java.io.File" %>
<%@ page import="ecar.exception.ECARException" %>
<%@ page import="ecar.pojo.ConfiguracaoCfg" %>
<%@ page import="ecar.dao.ConfiguracaoDao" %>
<%@ page import="javax.servlet.http.HttpServletRequest" %>
<%@ include file="../../login/validaAcesso.jsp"%>
<%@ include file="../../frm_global.jsp"%>

<%!
	public String[] listarArquivosExportados(HttpServletRequest request) throws ECARException {
	
		ConfiguracaoCfg configuracao = new ConfiguracaoDao(request).getConfiguracao();
		
		//Pasta onde encontram-se os arquivos exportados
		String path = configuracao.getRaizUpload() + configuracao.getUploadIntegracao();
		
		if(!path.endsWith("/"))
			path = path + "/";
		
		File diretorio = new File(path);
		String [] arquivos = diretorio.list();
		
		for(int i=0; i<arquivos.length; i++) {
			arquivos[i] = path + arquivos[i];
		}
		
		return arquivos;
	}
%>

<%
	String[] arquivos = listarArquivosExportados(request);
%>

<html lang="pt-br">
	<head>
		<%@ include file="../../include/meta.jsp"%>
		<%@ include file="/titulo.jsp"%>
		<link rel="stylesheet" href="<%=_pathEcar%>/css/style_<%=configuracaoCfg.getEstilo().getNome()%>.css" type="text/css">
		<script language="javascript" src="../../js/menu_retratil.js"></script>
		<script language="javascript" src="../../js/datas.js"></script>
		<script language="javascript" src="../../js/validacoes.js"></script>
		<script language="javascript" src="<%=_pathEcar%>/js/destaqueLinha.js"></script>
		
		<script language="javascript">
		
			function selecionarTodos() {
				var objetos = document.getElementsByTagName("input");
				var booleano = document.getElementById("checkPai").checked;
				
				for(i=0; i<objetos.length; i++) {
					if(objetos[i].type == 'checkbox') {
						objetos[i].checked = booleano;
					}
				}
			}
			
			function excluir(form) {
				
				var objetos = document.getElementsByTagName("input");
				var algumSelecionado = false;
				
				for(i=0; i<objetos.length; i++) {
					if(objetos[i].type == 'checkbox') {
						if(objetos[i].checked && objetos[i].name == 'deleteFiles') {
							algumSelecionado = true
							break;
						}
					}
				}				
				
				if(algumSelecionado) {
					if(confirm('Você tem certeza que deseja excluir o(s) arquivo(s) selecionado(s)?')) {
						form.action = "ctrl.jsp";
						form.submit();
					}
				}
			}
			
		</script>
		
	</head>
	
	<%@ include file="../../cabecalho.jsp" %>
	<%@ include file="../../divmenu.jsp"%>
	
	<body>		
		<div id="conteudo">
			<!-- TITULO -->
			<%@ include file="/titulo_tela.jsp"%>
			<form action="ctrl.jsp" method="post">
				<table class="form">
					<tr>
						<td>
							Nome
						</td>
						<td align="center" width="5%">
							Download
						</td>
						<td align="center" width="5%">
							<input type="button" value="Excluir" class="botao" onclick="javascript:excluir(form);">
						</td>
					</tr>
					<tr>
						<td class="espacador" colspan="3"><img src="../../images/pixel.gif">
						</td>
					</tr>
					<tr class="linha_subtitulo2">
						<td></td>
						<td></td>
						<td align="center">
							<input type="checkbox" class="form_check_radio" onclick="javascript:selecionarTodos();" name="checkPai" id="checkPai">
						</td>
					</tr>
					<%
					
					// Configuração	
					//ConfiguracaoCfg configuracao = new ConfiguracaoDao(request).getConfiguracao();
					String pathRaiz = configuracao.getRaizUpload();	
					
					for(int i=0; i<arquivos.length; i++) {%> 
					<% if((i % 2) == 0) {%>
						<tr class="cor_sim" onmouseover="javascript:destacaLinha(this,'over','')" onmouseout="javascript: destacaLinha(this,'out','cor_sim')" onClick="javascript: destacaLinha(this,'click','cor_sim')">
					<%} else {%>
						<tr class="cor_nao" onmouseover="javascript: destacaLinha(this,'over','')" onmouseout="javascript: destacaLinha(this,'out','cor_nao')" onClick="javascript: destacaLinha(this,'click','cor_nao')">
					<%}
					
					%>
						<td>
							<%=arquivos[i].substring(arquivos[i].lastIndexOf("/") + 1)%>
						</td>
					<%
						
						UsuarioUsu usuarioImagem = null;  
	            		String hashNomeArquivo = null;
					
	            		usuarioImagem = ((ecar.login.SegurancaECAR)request.getSession().getAttribute("seguranca")).getUsuario(); 
    					hashNomeArquivo = Util.calcularHashNomeArquivo(pathRaiz, arquivos[i]);
    					Util.adicionarMapArquivosAtuaisUsuarios(usuarioImagem, hashNomeArquivo, pathRaiz, arquivos[i]);
						%>
								<td align="center">
									<a href="<%=request.getContextPath()%>/DownloadFile?RemoteFile=<%=arquivos[i]%>"><img src="../../images/icon_download.png" border="0"></a>
								</td>
								<td align="center">
									<input type="checkbox" class="form_check_radio" value="<%=arquivos[i].substring(arquivos[i].lastIndexOf("/") + 1)%>" name="deleteFiles">
								</td>
							</tr>
					<%}%>
					<tr>
						<td class="espacador" colspan="3"><img src="../../images/pixel.gif"></td>
					</tr>					
					<tr>
						<td></td>
						<td></td>
						<td align="center">
							<input type="button" value="Excluir" class="botao" onclick="javascript:excluir(form);">
						</td>
					</tr>
				</table>
			</form>
		</div>
	</body>
	<% /* Controla o estado do Menu (aberto ou fechado) */%>
	<%@ include file="../../include/estadoMenu.jsp"%>
	<%@ include file="../../include/mensagemAlert.jsp" %>
</html>


