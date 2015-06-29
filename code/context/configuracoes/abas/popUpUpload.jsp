<%@ taglib uri="/WEB-INF/ecar-util.tld" prefix="util" %>
<%@ include file="../../frm_global.jsp"%>
<%@ page import="ecar.pojo.UsuarioUsu" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="javax.swing.ImageIcon" %>
<%@ page import="comum.util.FileUpload"%>
<%@ page import="java.util.Iterator" %>
<%@ page import="org.apache.commons.fileupload.FileItem"%>
<%@ page import="java.io.File" %>
<%@ page import="javax.media.jai.ImageMIPMap" %>
<%@ page import="ecar.dao.ConfiguracaoDao" %>

<%@page import="comum.util.Util"%><html lang="pt-br">
<head>
<%@ include file="../../include/meta.jsp"%>
<%@ include file="/titulo.jsp"%>
<link rel="stylesheet" href="<%=_pathEcar%>/css/style_<%=configuracaoCfg.getEstilo().getNome()%>.css" type="text/css">
<script language="javascript" src="../../js/focoInicial.js"></script>
<script language="javascript" src="../../js/validacoes.js"></script>

<script>
<%
String nome = request.getParameter("nomeCampo"); // Nome do campo que receberá o caminho da imagem (é um campo oculto)
String nomeCampoForm = request.getParameter("nomeCampo"); // Nome do campo que receberá o caminho da imagem (é um campo oculto)
String nomeArquivo = request.getParameter("excluir"); // O que deve ser excluído
String labelCampo = request.getParameter("labelCampo"); //O label do campo que está sendo alterado
boolean excluiImagem = ((nomeArquivo != null) && !"undefined".equals(nomeArquivo)); // Utilizado para excluir ("autorizar exclusao") imagem do servidor
if (nome.indexOf('\'') >= 0)
	nome = nome.substring(nome.indexOf('\'')+1, nome.lastIndexOf('\'')); // Se vier com ', retira 
%>

function redimensionaTela(altura, largura){
	window.outerHeight = altura;
	window.outerWidth = largura;
}

</script>
</head>

<%
String pathRaizUpload = configuracaoCfg.getRaizUpload();
String pathUploadIconeLinks = configuracaoCfg.getUploadIconeLinks();
String imagemIndisponivel = request.getContextPath() + "/images/ImagemIndisponivel.gif";
// Rotina para excluir imagem do servidor.
if(excluiImagem) {
	// Trecho abaixo tem por função extrair o nome do arquivo.
	// Ao final a variavel nomeArquivo contém apenas o nome do arquivo, sem o caminho...
    if(nomeArquivo.lastIndexOf("\\") != -1) {
		nomeArquivo = nomeArquivo.substring(nomeArquivo.lastIndexOf("\\") + 1); 
	} else if (nomeArquivo.lastIndexOf("/") != -1) {     
        	nomeArquivo = nomeArquivo.substring(nomeArquivo.lastIndexOf("/") + 1);
    }
}

String hashNomeArquivo = null;
UsuarioUsu usuarioImagem = null;

// Rotina para realizar Upload de imagem e mostrá-la na página principal.
boolean isFormUpload = FileUpload.isMultipartContent(request); 
if(isFormUpload) {
	List campos = new ArrayList();
	campos = FileUpload.criaListaCampos(request); 
	String hidAcao = FileUpload.verificaValorCampo(campos, "hidAcao");
	if ("upload".equals(hidAcao)) {
	    Iterator it = campos.iterator();  
	    while(it.hasNext()){
			FileItem fileItem = (FileItem) it.next();
			if(!fileItem.isFormField() && !"".equals(fileItem.getName())){    
				File arquivoGravado = FileUpload.salvarNoDisco(fileItem, FileUpload.getPathFisico(pathRaizUpload, pathUploadIconeLinks, FileUpload.getNomeArquivo(fileItem)));								
				String nomeArqGravado = arquivoGravado.getCanonicalPath().substring(arquivoGravado.getCanonicalPath().indexOf(pathUploadIconeLinks));
				String pathArqCompleto = arquivoGravado.getCanonicalPath();
				
				hashNomeArquivo = Util.calcularHashNomeArquivo(pathRaizUpload, nomeArqGravado);
				usuarioImagem = ((ecar.login.SegurancaECAR)request.getSession().getAttribute("seguranca")).getUsuario();  
				Util.adicionarMapArquivosAtuaisUsuarios(usuarioImagem, hashNomeArquivo, pathRaizUpload, nomeArqGravado);
				
				String pathArqMostrar = request.getContextPath()+"/DownloadFile?tipo=open&RemoteFile="+hashNomeArquivo;
				ImageIcon imagem = new ImageIcon(arquivoGravado.getCanonicalPath());
				String heig = ((imagem.getIconHeight() > 0) ? ("" + imagem.getIconHeight()) : "0");
				String widt = ((imagem.getIconWidth() > 0) ? ("" + imagem.getIconWidth()) : "0");
				if (imagem.getIconWidth() == -1) {
					pathArqMostrar = imagemIndisponivel;
				}
			%>
			<script>
				//window.opener.document.forms[0].< %=nome%>.value="< %=pathArqCompleto%>";
				window.opener.document.forms[0].icone<%=nomeCampoForm%>.src="<%=pathArqMostrar%>";
				window.opener.document.forms[0].iconeLink<%=nomeCampoForm%>.value = "<%=nomeArqGravado%>"; 
				window.opener.document.forms[0].icone<%=nomeCampoForm%>.width="<%=widt%>";
				window.opener.document.forms[0].icone<%=nomeCampoForm%>.heigth="<%=heig%>";
				//if (window.opener.document.forms[0].b1< %=nome%>.value == "Inserir < %=labelCampo%>")
					//window.opener.document.forms[0].b1< %=nome%>.value = "Alterar < %=labelCampo%>";			
				//window.opener.document.getElementById('link< %=nome%>').style.visibility = "hidden";				
				window.close();
			</script>
			<%
			}
		}
	}	
}	
%>


<body class="corpo_popup">

<form name="form" method="post" action="popUpUpload.jsp?hidAcao=upload&labelCampo=<%=labelCampo%>&nomeCampo=<%=nome%>" enctype="multipart/form-data">
	
	<input type="hidden" name="hidAcao" value="upload">

	<h1>Upload de Imagens<h1>

	<table class="form">

		<tr>
			<td class="label">Caminho da Imagem</td>
			<td>
				<input type="file" name="buscaArquivo" id="buscaArquivo" size="30" maxlength="20" value=""> 	
			</td>
		</tr>
	</table>
	<center>
	<input type="submit" name="btnAdicionar" class="botao" value="Adicionar" onclick="">
	<input type="button" name="btnCancelar" class="botao" value="Cancelar" onClick="window.close();">
	</center>
</form>
</body>
</html>