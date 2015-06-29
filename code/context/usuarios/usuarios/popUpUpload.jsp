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

function aoClicarCancelar(form){
	window.close();
}

</script>
</head>

<%
String pathRaiz = configuracaoCfg.getRaizUpload();
String pathUpload = configuracaoCfg.getUploadUsuarios();//_msg.getMensagem("path.upload.usuarios");
String imagemIndisponivel = request.getContextPath() + "/images/ImagemIndisponivel.gif";
// Rotina para excluir imagem do servidor.
if(excluiImagem)
{
	// Trecho abaixo tem por função extrair o nome do arquivo.
	// Ao final a variavel nomeArquivo contém apenas o nome do arquivo, sem o caminho...
    if(nomeArquivo.lastIndexOf("\\") != -1) {
		nomeArquivo = nomeArquivo.substring(nomeArquivo.lastIndexOf("\\") + 1); 
	} else 
		if (nomeArquivo.lastIndexOf("/") != -1)     
        	nomeArquivo = nomeArquivo.substring(nomeArquivo.lastIndexOf("/") + 1);
        	
	%>
		<script>
			window.opener.document.forms[0].<%=nome%>.value="";
//			window.opener.document.forms[0].imagem%=nome%>.src="%=imagemIndisponivel%>";
			if (window.opener.document.forms[0].b1<%=nome%>.value == "Alterar <%=labelCampo%>")
				window.opener.document.forms[0].b1<%=nome%>.value = "Inserir <%=labelCampo%>";
			window.opener.document.forms[0].b2<%=nome%>.style.visibility = "hidden";
			window.opener.document.getElementById('link<%=nome%>').innerHTML = '';
			//window.opener.document.getElementById('link<%=nome%>').style.visibility = "hidden";
			window.opener.document.forms[0].imagem<%=nome%>.style.display='none';
			
			window.opener.document.getElementById('msg<%=nome%>').innerHTML = 'A exclusão será efetuada na atualização do formulário';
			 				
			window.close();
		</script>
	<%
	}

// Rotina para realizar Upload de imagem e mostrá-la na página principal.
boolean isFormUpload = FileUpload.isMultipartContent(request); 
if(isFormUpload){
	List campos = new ArrayList();
	campos = FileUpload.criaListaCampos(request); 
	String hidAcao = FileUpload.verificaValorCampo(campos, "hidAcao");
	if ("upload".equals(hidAcao))
	{
	    Iterator it = campos.iterator();  
	    while(it.hasNext()){
			FileItem fileItem = (FileItem) it.next();
			if(!fileItem.isFormField() && !"".equals(fileItem.getName())){    
				
				String hashNomeArquivo = null;
				UsuarioUsu usuarioImagem = null;
				
				hashNomeArquivo = Util.calcularHashNomeArquivo(pathRaiz, pathUpload + "/" + FileUpload.getNomeArquivo(fileItem));
				usuarioImagem = ((ecar.login.SegurancaECAR)request.getSession().getAttribute("seguranca")).getUsuario();  
				Util.adicionarMapArquivosAtuaisUsuarios(usuarioImagem, hashNomeArquivo, pathRaiz, pathUpload + "/" + FileUpload.getNomeArquivo(fileItem));
				
				String pathArqCompletoReal = FileUpload.getPathFisico(pathRaiz, pathUpload, FileUpload.getNomeArquivo(fileItem));
				String pathArqMostrarReal = request.getContextPath()+"/DownloadFile?RemoteFile="+ FileUpload.getNomeArquivo(fileItem);
				byte[] imgBytes = FileUpload.salvarArquivoTemporarioSessao(request, fileItem, nome, pathArqCompletoReal);
				String pathArqSessao = request.getContextPath()+"/DownloadFile?RemoteFileSessao=" + nome + "&caminhoReal=" + pathArqCompletoReal;
				ImageIcon imagem = new ImageIcon(imgBytes);
				String heig = ((imagem.getIconHeight() > 0) ? ("" + imagem.getIconHeight()) : "0");
				String widt = ((imagem.getIconWidth() > 0) ? ("" + imagem.getIconWidth()) : "0");
			%>
			<script>
				window.opener.document.forms[0].<%=nome%>.value="<%=pathArqMostrarReal%>";
				window.opener.document.forms[0].hidImagem<%=nome%>.value="<%=pathArqSessao %>";
				window.opener.document.forms[0].imagem<%=nome%>.src="<%=pathArqSessao%>";
				window.opener.document.forms[0].imagem<%=nome%>.width="<%=widt%>";
				window.opener.document.forms[0].imagem<%=nome%>.heigth="<%=heig%>";
				window.opener.document.forms[0].imagem<%=nome%>.style.display='none';
				window.opener.document.getElementById('link<%=nome%>').style.display='none';
				if (window.opener.document.forms[0].b1<%=nome%>.value == "Inserir <%=labelCampo%>"){
					window.opener.document.forms[0].b1<%=nome%>.value = "Alterar <%=labelCampo%>";
					window.opener.document.getElementById('msg<%=nome%>').innerHTML = 'A inclusão será efetuada na atualização do formulário';
				}
				else if (window.opener.document.forms[0].b1<%=nome%>.value == "Alterar <%=labelCampo%>"){
					window.opener.document.getElementById('msg<%=nome%>').innerHTML = 'A alteração será efetuada na atualização do formulário';	
				}	
				//window.opener.document.getElementById('link<%=nome%>').style.visibility = "hidden";
				window.opener.document.getElementById('link<%=nome%>').innerHTML = '<a href="<%=pathArqSessao%>"><%=FileUpload.getNomeArquivo(fileItem)%></a>';	
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

	<h1>Upload de Arquivos<h1>

	<table class="form">

		<tr>
			<td class="label">Caminho do Arquivo</td>
			<td>
				<input type="file" name="buscaArquivo" id="buscaArquivo" size="30" maxlength="20" value=""> 	
			</td>
		</tr>
	</table>
	<center>
	<input type="submit" name="btnAdicionar" class="botao" value="Adicionar" onclick="">
	<input type="button" name="btnCancelar" class="botao" value="Cancelar" onClick="aoClicarCancelar(document.form);">
	</center>
</form>
</body>
</html>