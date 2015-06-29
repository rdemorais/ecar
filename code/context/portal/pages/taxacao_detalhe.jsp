<%@ include file="../../login/validaAcesso.jsp"%>
<%@ include file="../../frm_global.jsp"%>

<%@ page language="java" %>
<%@ page import="comum.util.Data" %>
<%@ page import="ecar.pojo.SegmentoItemSgti" %>
<%@ page import="ecar.dao.SegmentoItemDao" %>

<%@ page import="java.io.File" %>
<%@ page import="java.util.List" %>
<%@ page import="ecar.pojo.ImageInfo" %>
<%@ page import="java.io.FileInputStream" %>
<%@ page import="java.util.Set" %>
<%@ page import="java.util.Iterator" %>


<%@ page import="ecar.dao.SegmentoDao" %>
<%@ page import="ecar.dao.SisAtributoDao" %>
<%@ page import="ecar.pojo.SegmentoSgt" %>
<%@ page import="ecar.pojo.SisAtributoSatb" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="comum.util.FileUpload" %>
<%@ page import="java.lang.String" %>

<%@ taglib uri="/WEB-INF/ecar-util.tld" prefix="util" %>
<%@ taglib uri="/WEB-INF/ecar-combo.tld" prefix="combo" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">

<%@page import="ecar.pojo.UsuarioUsu"%>
<%@page import="comum.util.Util"%><html lang="pt-br">
<head>
<script language="javascript">

function zoomImagem( codigo ){
	window.location.href = "taxacao_detalhe.jsp?tipo=" + codigo;
}

</script>
<%@ include file="/titulo.jsp"%>
<%@ include file="/include/meta.jsp"%>
<link rel="stylesheet" href="<%=_pathEcar%>/css/style_<%=configuracaoCfg.getEstilo().getNome()%>.css" type="text/css">
</head>
<body>
<%@ include file="/cabecalho.jsp" %>

<%
try{
%>
	<form name="form" id="form" action="ctrl_taxacao.jsp">
		<input type="hidden" name="hidAcao" value="TAXACAO">
	</form>		
	<table id="barra_localizacao" cellspacing="0">
		<tr>
			<td><a href="index.jsp">Capa</a> > <a href="ctrl_taxacao.jsp">Na M&iacute;dia</a> > Detalhe </td>
			<td align="right">&lt;&lt;<a href="taxacao_todos.jsp"> Voltar</a></td>
		</tr>
	</table>
	<h1 class="interno">NA M&Iacute;DIA</h1>
    <table width="100%" cellpadding="0" cellspacing="0">
	<tr>
	<%
	//ecar.pojo.ConfiguracaoCfg configuracao = new ecar.dao.ConfiguracaoDao(request).getConfiguracao();
	String pathRaiz = configuracao.getRaizUpload();	
	
	SegmentoItemSgti seg = (SegmentoItemSgti) request.getSession().getAttribute("segmentoItem");
	java.text.SimpleDateFormat sf = new java.text.SimpleDateFormat("dd/MM/yyyy"); 
	
	String imagemCaminho = pathRaiz + seg.getImagem1Stgi();
								    			
    File arquivo = new File(imagemCaminho);    			
    			
    ImageInfo imagem = new ImageInfo();    			
    			
	int largura = 0;    			
	
    int altura = 0;
    
    String parametro = "0";
    
    String zoomAtual = "100%";
    
    if (arquivo.exists() && !arquivo.isDirectory()){
    			    				
    	FileInputStream file = new FileInputStream(imagemCaminho);
    	imagem.setInput(file);
    	
    	if(request.getQueryString() != null){
    		
    		if (imagem.check()) {	
    		
    			parametro = request.getParameter("tipo");	
    		
    			if ("areazoom50".equals(parametro)){
    				imagem.zoomImagem("50%");
    				zoomAtual = "50%";							
    			}
    			
    			if ("areazoom75".equals(parametro)){
    				imagem.zoomImagem("75%");
    				zoomAtual = "75%";			
    			}
    			
    			if ("areazoom100".equals(parametro)){
    				zoomAtual = "100%";
    			}
    		
    			if ("areazoom150".equals(parametro)){
    				imagem.zoomImagem("150%");
    				zoomAtual = "150%";
    			}
    		
    			if ("areazoom200".equals(parametro)){
    				imagem.zoomImagem("200%");
    				zoomAtual = "200%";
    			}
    		
    			if ("areazoom300".equals(parametro)){
    				imagem.zoomImagem("300%");
    				zoomAtual = "300%";
    			}		
    		
    			if (!"areazoom100".equals(parametro)){		
    				largura = imagem.getWidth();
        			altura = imagem.getHeight();
        		}
        	}			
    	} 
    			 				
    }
    
	
		
			
	
	%>
		<td class="conteudo_interno">
		<h1><%=seg.getTituloSgti()%></h1>
			<p><%=seg.getLinhaApoioSgti()%></p>
	        <%
	        SegmentoItemDao segItemDao = new SegmentoItemDao(request);
	        request.getSession().setAttribute("segmentoItem", segItemDao.getSegmentoItemCodSgti(seg.getCodSgti().longValue()));
	        List editorias = segItemDao.getEditoriasBySegItem((SegmentoItemSgti) request.getSession().getAttribute("segmentoItem"));
		
			List editSelecionados = segItemDao.getIdsEditSelecionados((SegmentoItemSgti) request.getSession().getAttribute("segmentoItem"));
			Iterator iterator = editorias.iterator(); 
			int count = 0;      
	        %>
	        <p>Assunto: 
	        <%	while (iterator.hasNext())	{
	        		SisAtributoSatb atributoSatb = (SisAtributoSatb)iterator.next();
	        		String descrEditoria = atributoSatb.getDescricaoSatb();
	        		String codEditoria1 = "[" + atributoSatb.getCodSatb().toString() + "]";
	        		String codEditoria2 = atributoSatb.getCodSatb().toString() + ",";
	        		String codEditoria3 = ", " + atributoSatb.getCodSatb().toString();
	        		String selecionados = editSelecionados.toString();
	        		//out.print(selecionados);
	        		if (selecionados.contains(codEditoria1) || 
	        		selecionados.contains(codEditoria2) ||
	        		selecionados.contains(codEditoria3)) {
	        			if (count > 0)
	        				out.print(",");	        			
	        %>			<b><%=descrEditoria%></b>
	        <%			count++;
	        		}
	        	}
	        %><b></b><br>
	          Fonte: <b><%=seg.getSegmentoItemFonteSgtif().getDescricaoSgtif()%></b><br>
      Data: <b><%=request.getSession().getAttribute("strDia")%></b></p></td>
	</tr>
	<tr>
		<td>
			<table align="right" cellspacing="0" class="taxacao_zoom">
				<tr>					
					<td><img src="../images/icon_zoom.png" ></td>
					<td>Zoom</td>
					<td>
					<form>
					<select name="zoom" onChange="javascript:zoomImagem(this.form.zoom.options[this.form.zoom.selectedIndex].value);">
						<option value="" selected>Selecione</option>
						<option value="areazoom50">50%</option>
						<option value="areazoom75">75%</option>
						<option value="areazoom100">100%</option>
						<option value="areazoom150">150%</option>
						<option value="areazoom200">200%</option>
						<option value="areazoom300">300%</option>
					</select>
					</form>				
					</td>
				</tr>
				<tr>
					<td></td>
					<td><b>Zoom atual:</b></td>
					<td><b><%=zoomAtual%></b></td>
				</tr>
		  </table>
		</td>
	</tr>
	<tr>
		<td class="tdareazoom">
			<div id="areazoom">
			<%
			if (seg.getImagem1Stgi() != null && !seg.getImagem1Stgi().equals("")){
				String hashNomeArquivo = null;
				UsuarioUsu usuarioImagem = null;  
			
				hashNomeArquivo = Util.calcularHashNomeArquivo(pathRaiz, seg.getImagem1Stgi());
				usuarioImagem = ((ecar.login.SegurancaECAR)request.getSession().getAttribute("seguranca")).getUsuario();  
				Util.adicionarMapArquivosAtuaisUsuarios(usuarioImagem, hashNomeArquivo, pathRaiz, seg.getImagem1Stgi());
			%>
				<img src="<%=request.getContextPath()%>/DownloadFile?tipo=open&RemoteFile=<%=hashNomeArquivo%>" 
				alt=<%=seg.getLegendaImag1Sgti()%>
				<% if(largura > 0){ %> width="<%=largura%>" <% } %> <% if(altura > 0){ %> height="<%=altura%>" <% } %>>
			
			<%} %>
				<p><%=seg.getLegendaImag1Sgti()%></p>
				
			</div>			
		</td>
	</tr>
</table><hr>
<%
} catch (Exception e){
%>
	<%@ include file="/excecao.jsp"%>
<%
}
%>
</body> 
</html>
