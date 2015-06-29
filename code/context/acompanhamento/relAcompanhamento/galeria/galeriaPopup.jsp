<%@ include file="../../../login/validaAcesso.jsp"%>
<%@ include file="../../../frm_global.jsp"%>

<%@ page import="ecar.dao.ItemEstruturaUploadCategoriaDao" %>
<%@ page import="ecar.pojo.ItemEstrUplCategIettuc" %>
<%@ page import="ecar.pojo.ItemEstrutUploadIettup" %>

<%@ page import="comum.util.Util" %> 

<%@ page import="java.util.Iterator" %> 

<%@page import="ecar.pojo.UsuarioUsu"%>
<%@page import="ecar.pojo.ConfiguracaoCfg"%><html lang="pt-br">
<%@ page import="ecar.dao.ConfiguracaoDao" %>

<%
try{ 
	ItemEstruturaUploadCategoriaDao itemEstUCDao = new ItemEstruturaUploadCategoriaDao(request); 
	
	String strCodIettuc = Pagina.getParamStr(request, "codIettuc");
	
	ItemEstrUplCategIettuc itemEstUC = (ItemEstrUplCategIettuc) itemEstUCDao.buscar(ItemEstrUplCategIettuc.class, Long.valueOf(strCodIettuc));
%>

<html lang="pt-br"> 
<head>
<%@ include file="../../../include/meta.jsp"%>
<%@ include file="/titulo.jsp"%>
<link rel="stylesheet" href="<%=_pathEcar%>/css/style_<%=configuracaoCfg.getEstilo().getNome()%>.css" type="text/css">

<!--<script language="javascript" src="../../../js/menu_retratil.js"></script>-->
<script language="javascript" src="../../../js/popUp.js"></script>
<script language="javascript" src="../../../js/tableSort.js"></script>
<script language="Javascript">
function abrir(caminho){
	window.open(caminho,"", 'width=400, height=350,toolbar=no,location=no,directories=no,status=no,menubar=no,resizable=yes,scrollbars=yes');
}
</script>
</head>

<!--<body class="body_anexos">-->
<body class="corpo_popup">

<!--<div class="titulo_anexos"><%=itemEstUC.getNomeIettuc()%></div>-->
<div align="right" ><%@ include file="../../../../include/problemaDown.jsp"%></div> 
<div class="texto_negrito"><%=itemEstUC.getNomeIettuc()%></div>

<table id="list_anexos" cellspacing="0">
	<thead>
	<tr>
		<th><a href="#" onclick="this.blur(); return sortTable('corpo', 0, false, true);">Data de Upload</a></th>
		<th align="left"><a href="#" onclick="this.blur(); return sortTable('corpo', 1, false);">Descri&ccedil;&atilde;o</a></th>
		<th align="left"><a href="#" onclick="this.blur(); return sortTable('corpo', 2, false);">Nome Original</a></th>
		<th><a href="#" onclick="this.blur(); return sortTable('corpo', 3, false);">Tamanho</a></th>
		<th>A&ccedil;&atilde;o</th>
	</tr>
	</thead>
	<tbody id="corpo">
<%
	//Configuração	
	ConfiguracaoCfg configuracao = (ConfiguracaoCfg)session.getAttribute("configuracao");
	String pathRaiz = configuracao.getRaizUpload();

	UsuarioUsu usuarioImagem = null;  
	String hashNomeArquivo = null;

	if(itemEstUC.getItemEstrutUploadIettups().size() > 0){
		Iterator itItemEstUp = itemEstUCDao.getItemEstrutUploadIettups(itemEstUC).iterator();
		
		String cor = "sim";
		while (itItemEstUp.hasNext()){
			ItemEstrutUploadIettup itemEstrutUpload = (ItemEstrutUploadIettup) itItemEstUp.next();
			
			String caminhoArquivo = configuracao.getRaizUpload() + itemEstrutUpload.getArquivoIettup();
			_pathEcar = request.getContextPath();
			
			String tagHtml;
			
			usuarioImagem = ((ecar.login.SegurancaECAR)request.getSession().getAttribute("seguranca")).getUsuario(); 
			hashNomeArquivo = Util.calcularHashNomeArquivo(pathRaiz, itemEstrutUpload.getArquivoIettup());
			Util.adicionarMapArquivosAtuaisUsuarios(usuarioImagem, hashNomeArquivo, pathRaiz, itemEstrutUpload.getArquivoIettup());

			if (itemEstrutUpload.getUploadTipoArquivoUta().getUploadTipoCategoriaUtc().getCodUtc().longValue() == 1) { //Imagem
				tagHtml = "<A href='javascript:abrir(\"#CAMINHO#/DownloadFile?tipo=open&RemoteFile=#ARQUIVO#\")'>";
				tagHtml += "<IMG src=\"#CAMINHO#/DownloadFile?tipo=open&RemoteFile=#ARQUIVO#\" height=250 width=330 border=0>";
				tagHtml += "</A><BR><BR>Clique na imagem para ampliá-la";
			} else if (itemEstrutUpload.getUploadTipoArquivoUta().getUploadTipoCategoriaUtc().getCodUtc().longValue() == 2) { //Audio
				tagHtml = "<embed src=\"#CAMINHO#/DownloadFile?tipo=multimidia&RemoteFile=#ARQUIVO#\" AUTOSTART=FALSE CONTROLS=TRUE></embed>";
			} else if (itemEstrutUpload.getUploadTipoArquivoUta().getUploadTipoCategoriaUtc().getCodUtc().longValue() == 4) { //Vídeo
				tagHtml = "<embed src=\"#CAMINHO#/DownloadFile?tipo=multimidia&RemoteFile=#ARQUIVO#\"></embed>";
			} else { // download documento
				tagHtml = "	<a href=\"#CAMINHO#/DownloadFile?RemoteFile=#ARQUIVO#\">Clique aqui para fazer o download</a>";
			}
			
			tagHtml = tagHtml.replaceAll("#CAMINHO#", _pathEcar);
			tagHtml = tagHtml.replaceAll("#ARQUIVO#", hashNomeArquivo);
%>
				<tr class="list_anexos_cor_<%=cor%>">
					<td align="center"><%=Pagina.trocaNullData(itemEstrutUpload.getDataInclusaoIettup())%></td>
					<td><%=itemEstrutUpload.getDescricaoIettup()%></td>
					<td><%=itemEstrutUpload.getNomeOriginalIettup()%></td>
					<td align="center"><%=Util.formataByte(itemEstrutUpload.getTamanhoIettup())%></td>
					<td align="center"><%=tagHtml%></td>
				</tr>
<%
			}
			
			
			if(!"sim".equals(cor))
				cor = "sim";
			else
				cor = "nao";
	
	}else{
%>	
		<tr>
			<td align="center" colspan=5><br>Nenhum anexo cadastrado<br><br></td>
		</tr>
<%
	}
%>
	</tbody>
</table>


<%
} catch (ECARException e){
	org.apache.log4j.Logger.getLogger(this.getClass()).error(e);
	Mensagem.alert(_jspx_page_context, _msg.getMensagem(e.getMessageKey()));
} catch (Exception e){
%>
	<%@ include file="/excecao.jsp"%>
<%
}
%>

<br>

</body>
</html>