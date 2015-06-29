<%@ include file="/login/validaAcesso.jsp"%>
<%@ include file="/frm_global.jsp"%>

<%@ page import="ecar.dao.ItemEstruturaUploadCategoriaDao" %>
<%@ page import="ecar.dao.ConfiguracaoDao" %>
<%@ page import="ecar.pojo.ItemEstrUplCategIettuc" %>
<%@ page import="ecar.pojo.ItemEstrutUploadIettup" %>

<%@ page import="comum.util.Util" %> 

<%@ page import="java.util.Iterator" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %> 
<%@ page import="java.awt.Image" %>
<%@ page import="javax.swing.ImageIcon" %>
<%@ page import="java.io.File"%>


<%@page import="ecar.pojo.UsuarioUsu"%>
<%@page import="ecar.pojo.ConfiguracaoCfg"%><html lang="pt-br"> 
<head>
<%@ include file="/include/meta.jsp"%>
<%@ include file="/titulo.jsp"%>

<link rel="stylesheet" href="<%=_pathEcar%>/css/style_<%=configuracaoCfg.getEstilo().getNome()%>.css" type="text/css">
<script language="javascript" src="<%=_pathEcar%>/js/popUp.js"></script>
<script language="Javascript">
function abrir(caminho){
	window.open(caminho,"", 'width=600, height=550,toolbar=no,location=no,directories=no,status=no,menubar=no,resizable=no,scrollbars=yes');
}
</script>
</head>

<body class="body_anexos">
<center>
<%
	StringBuffer tagHtml = new StringBuffer();
	tagHtml.append("");
	//out.println("<center>");
	String codigo = Pagina.getParamStr(request, "codImagem");
	//String codigo = Pagina.getParamStr(request, "codIettuc");
	
	// Configuração	
	//ConfiguracaoCfg configuracao = new ConfiguracaoDao(request).getConfiguracao();
	String pathRaiz = configuracaoCfg.getRaizUpload();
	
	UsuarioUsu usuarioImagem = null;  
	String hashNomeArquivo = null;
	
	if (codigo != null && !"".equals(codigo)) {
		ItemEstruturaUploadCategoriaDao itemEstUCDao = new ItemEstruturaUploadCategoriaDao(request);
		ItemEstrutUploadIettup item = (ItemEstrutUploadIettup) itemEstUCDao.buscar(ItemEstrutUploadIettup.class, Long.valueOf(codigo));

		String caminhoArquivo = configuracaoCfg.getRaizUpload() + item.getArquivoIettup();
		File file = new File(caminhoArquivo);
		
		tagHtml.append("<BR><BR>O arquivo não existe no servidor<BR><BR>");
		
		usuarioImagem = ((ecar.login.SegurancaECAR)request.getSession().getAttribute("seguranca")).getUsuario(); 
		hashNomeArquivo = Util.calcularHashNomeArquivo(pathRaiz, item.getArquivoIettup());
		Util.adicionarMapArquivosAtuaisUsuarios(usuarioImagem, hashNomeArquivo, pathRaiz, item.getArquivoIettup());
		
		if (item.getUploadTipoArquivoUta().getUploadTipoCategoriaUtc().getCodUtc().longValue() == 1) { //Imagem

			Image image = new ImageIcon(caminhoArquivo).getImage();

			tagHtml = new StringBuffer();
			
			double intImgWidth;
			double intImgHeight;
			double intPadrWidth = 330;
			double intPadrHeight = 250;
			double intPropImg = image.getWidth(null) / image.getHeight(null);
			double intPropPadr = intPadrWidth / intPadrHeight;

			if (image.getWidth(null) > intPadrWidth && intPropImg >= intPropPadr) {
				intImgWidth = intPadrWidth;
				intImgHeight = image.getHeight(null) * intPadrWidth / image.getWidth(null);
			} else if (image.getHeight(null) > intPadrHeight && intPropImg < intPropPadr) {
				intImgWidth = image.getWidth(null) * intPadrHeight / image.getHeight(null);
				intImgHeight = intPadrHeight;
			} else {
				intImgWidth = image.getWidth(null);
				intImgHeight = image.getHeight(null);
			}
			if (file.exists()) {

				tagHtml = new StringBuffer();
				
				tagHtml.append("<table border=0 cellpadding=\"0\" cellspacing=\"0\"><tr><td height=" + intPadrHeight + " width=" + intPadrWidth
						+ " align=\"center\" valign=\"center\">");
				
				
				
				tagHtml.append("<A href='javascript:abrir(\"#CAMINHO#/DownloadFile?tipo=open&RemoteFile=#ARQUIVO#\")'>");
				tagHtml.append("<IMG src=\"#CAMINHO#/DownloadFile?tipo=open&RemoteFile=#ARQUIVO#\" height=" + intImgHeight + " width=" + intImgWidth
						+ " border=0>");
				tagHtml.append("</A></td></tr></table>");
				tagHtml.append("<BR><BR>Clique na imagem para ampliá-la");
				
			} 
		} else if (item.getUploadTipoArquivoUta().getUploadTipoCategoriaUtc().getCodUtc().longValue() == 2) { //Audio
			
			if (file.exists()) {
			
				tagHtml = new StringBuffer();
				
				tagHtml.append("<embed src=\"#CAMINHO#/DownloadFile?tipo=multimidia&RemoteFile=#ARQUIVO#\" AUTOSTART=FALSE CONTROLS=TRUE></embed>");
			} 

		} else if (item.getUploadTipoArquivoUta().getUploadTipoCategoriaUtc().getCodUtc().longValue() == 4) { //Vídeo
			if (file.exists()) {
				
				tagHtml = new StringBuffer();

				tagHtml.append("<embed src=\"#CAMINHO#/DownloadFile?tipo=multimidia&RemoteFile=#ARQUIVO#\"></embed>");
			}
		} else { // download documento
			if (file.exists()) {
				tagHtml = new StringBuffer();
				tagHtml.append("	<a href=\"#CAMINHO#/DownloadFile?RemoteFile=#ARQUIVO#\">Clique aqui para fazer o download</a>");
			}
		}
		
		while(tagHtml.indexOf("#CAMINHO#")>0)
			tagHtml.replace(tagHtml.indexOf("#CAMINHO#"),tagHtml.indexOf("#CAMINHO#") +  "#CAMINHO#".length(), _pathEcar);
		
		while(tagHtml.indexOf("#ARQUIVO#")>0)
			tagHtml.replace(tagHtml.indexOf("#ARQUIVO#"),tagHtml.indexOf("#ARQUIVO#") +  "#ARQUIVO#".length(), hashNomeArquivo);
		
		//out.println(tagHtml);
		//out.println("<br><br>");
%>
		<%= tagHtml.toString()%>
		Dados do Arquivo<br><br>
		<table width="70%" cellpadding="0" cellspacing="0">
			<tr>
				<td width="30%" class="texto">Nome Original: </td>
				<td width="70%" class="texto_negrito"><a href="<%=_pathEcar + "/DownloadFile?RemoteFile=" + hashNomeArquivo%>"><%=item.getNomeOriginalIettup()%></a></td>
			</tr>
			<tr>
				<td width="30%" class="texto">&nbsp; </td>
				<td width="70%" class="texto"> <br></td>
			</tr>
			<tr>
				<td width="30%" class="texto">Tipo do Arquivo: </td>
				<td width="70%" class="texto_negrito"><%=item.getUploadTipoArquivoUta().getDescricaoUta()%></td>
			</tr>
			<tr>
				<td width="30%" class="texto">Tamanho: </td>
				<td width="70%" class="texto_negrito"><%=Util.formataByte(item.getTamanhoIettup())%></td>
			</tr>
			<tr>
				<td width="30%" class="texto">Descrição: </td>
				<td width="70%" class="texto_negrito"><%=item.getDescricaoIettup()%></td>
			</tr>
		</table>
		<br><br>
		<%
			List listaArquivos = (List) request.getSession().getAttribute("listaArquivos");

				request.getSession().removeAttribute("listaArquivos");

				if (listaArquivos != null && listaArquivos.size() > 0) {
					Iterator itArquivos = listaArquivos.iterator();
					int atual = 0;
					int ultimo = listaArquivos.size() - 1;
					Long anterior = Long.valueOf(-1);
					Long proximo = Long.valueOf(-1);
					while (itArquivos.hasNext()) {
						Long codItem = (Long) itArquivos.next();
						if (codItem.longValue() == Long.valueOf(codigo).longValue()) {
							if (atual == 0) { //Se for o primeiro registro
								anterior = Long.valueOf(-1);
								// O IF abaixo é referente ao Bug 3825:
								// Quando só tinha 1 registro, ele tentava com o comando abaixo pegar o próximo 
								// da lista e dava um IndexOutOfBoundException pq não tinha o próximo registro
								// (Se tiver mais de 1 item na lista, então procure o próximo)
								if (listaArquivos.size() > 1) {
									proximo = (Long) listaArquivos.get(atual + 1);
								}
							} else if (atual == ultimo) { //Se for o último registro
								anterior = (Long) listaArquivos.get(atual - 1);
								proximo = Long.valueOf(-1);
							} else {
								anterior = (Long) listaArquivos.get(atual - 1);
								proximo = (Long) listaArquivos.get(atual + 1);
							}
							break;
						}
						atual++;
					}

					String tagAnterior = ((anterior.intValue() != -1) ? "<a href=\"galeriaImagemVisualizacao.jsp?codImagem=" + anterior
							+ "\">&lt;&lt; Anterior</a>" : "&lt;&lt; Anterior");
					String tagProximo = ((proximo.intValue() != -1) ? "<a href=\"galeriaImagemVisualizacao.jsp?codImagem=" + proximo
							+ "\">Proximo &gt;&gt;</a>" : "Proximo &gt;&gt;");

					request.getSession().setAttribute("listaArquivos", listaArquivos);
		%>
			<%=tagAnterior%>&nbsp;&nbsp;<%=tagProximo%>
			<%
				}
				} else { %>
					<br><br><br>Selecione um item do menu ao lado para visualização
				<% } %>
</center>
</body>
</html>