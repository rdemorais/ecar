<%@ taglib uri="http://celepar.pr.gov.br/taglibs/informacao.tld" prefix="info" %>
<%@ include file="/login/validaAcesso.jsp"%>
<%@ include file="/frm_global.jsp"%>

<%@ page language="java" %>
<%@ page import="ecar.pojo.UsuarioUsu" %>
<%@ page import="ecar.portal.bean.SecaoBean" %>
<%@ page import="ecar.portal.bean.InformacaoBean" %>
<%@ page import="ecar.pojo.DestaqueAreaDtqa" %>
<%@ page import="ecar.pojo.DestaqueSubAreaDtqsa" %>
<%@ page import="ecar.pojo.DestaqueItemRelDtqir" %>
<%@ page import="ecar.pojo.ImageInfo" %>
<%@ page import="ecar.portal.CapaPrincipal" %>

<%@ page import="comum.util.Data" %>
<%@ page import="java.io.File" %>
<%@ page import="java.util.Set" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.Iterator" %>
<%@ page import="ecar.dao.DestaqueAreaDao" %>
<%@ page import="java.util.StringTokenizer" %>
<%@ page import="java.io.FileInputStream" %>
<%@ page import="java.text.NumberFormat" %>
<%
	CapaPrincipal capaPrincipal = new CapaPrincipal(request);
	UsuarioUsu usuarioLogado = ((ecar.login.SegurancaECAR)session.getAttribute("seguranca")).getUsuario();
%>

<html lang="pt-br">
<head>

<script language="javascript">
function subCapaArtigos(){
	document.form.hidAcao.value = "ARTIGOS_DESTAQUE";
	document.form.action = "ctrl_artigos.jsp";	
	document.form.submit();
}
function subCapaAgenda(){
	document.form.hidAcao.value = "AGENDA";
	document.form.action = "ctrl_agenda.jsp";	
	document.form.submit();
}
function subCapaTaxacao(){
	document.form.hidAcao.value = "TAXACAO";
	document.form.action = "ctrl_taxacao.jsp";	
	document.form.submit();
}
function detalhe(link, codSgti){
	document.form.codAgeo.value=codSgti;
	document.form.codSgti.value=codSgti;
	document.form.hidAcao.value = "DETALHE";
	document.form.action = link;	
	document.form.submit();
}
function subCapaGenerica(link){
	document.form.hidAcao.value = "GENERICA";
	document.form.action = link;	
	document.form.submit();
}
</script>


<%@ include file="/include/meta.jsp"%>
<%@ include file="/titulo.jsp"%>

</head>
<body>
<%
request.setAttribute("exibirAguarde", "true");
%>
<%@ include file="/cabecalho.jsp" %>

<form name="form" method="post" action="ctrl_artigos.jsp">
<input type="hidden" name="hidAcao" value="">	
<input type="hidden" name="codSgti" value="">
<input type="hidden" name="codAgeo" value="">
<input type="hidden" name="codEmail" value="" />
</form>

<table id="conteudo_index" cellspacing="0">
	<tr>
		<td class="destaque1">
			<h1>MEU SISTEMA</h1>
			<p>Bem vindo <b><%=usuarioLogado.getNomeUsuSent()%>.</b><br>
			Seu �ltimo acesso foi em <info:lastLogin />
			</p>
			<h2>Meu Cadastro</h2>
			<a href="<%=_pathEcar%>/abrirUrl.jsp?url=<%=_pathEcar%>/usuarios/usuarioPortal/form.jsp&titulo=Altere seu cadastro">Altere seu cadastro</a>
		</td>
		<td colspan="2">
<%
			SecaoBean secaoSuperDestaque = capaPrincipal.getSuperDestaqueArtigos();
			
			if(secaoSuperDestaque != null && 
					(secaoSuperDestaque.getInformacaoBean() != null && secaoSuperDestaque.getInformacaoBean().size() > 0)) {

				InformacaoBean infoSuperDestaque = (InformacaoBean)secaoSuperDestaque.getInformacaoBean().get(0);
				
				String link = infoSuperDestaque.getLink() + "?cod=" + infoSuperDestaque.getCodSgti();
				
				StringTokenizer st = new StringTokenizer (infoSuperDestaque.getDescricao()," ");	
	
				int tamanho = st.countTokens();
	
				int cont = 0;
				
				int limite = 220;
				
				String descricao = null;
				
				String imagemCaminho = infoSuperDestaque.getImagem();
								    			
    			File arquivo = new File(imagemCaminho);    			
    			
    			ImageInfo imagem = new ImageInfo();    			
    			
    			if (arquivo.exists()){
    			    				
    				FileInputStream file = new FileInputStream(imagemCaminho);
    				imagem.setInput(file);
    			}
    			int largura = 0;
    			int altura = 0;    			
%>  			
				<h1><%=secaoSuperDestaque.getTitulo()%></h1>
				<%	if (arquivo.exists()){ %>
				<div class="imagem_noticia_home_sem_tamanho">
					<a href="javascript:detalhe('<%=infoSuperDestaque.getLink()%>', <%=infoSuperDestaque.getCodSgti().intValue()%>);">
					<%		if (!imagem.check()) { 
					%>					
						<img src="<%=_pathEcar%>/DownloadFile?tipo=open&RemoteFile=<%=infoSuperDestaque.getImagem()%>" 
						border="0">
					<% 		} 
							
							else { 
						
							imagem.redimensionaImagem(limite);
							largura = imagem.getWidth();
    						altura = imagem.getHeight();    						
    						
					%>
						<img src="<%=_pathEcar%>/DownloadFile?tipo=open&RemoteFile=<%=infoSuperDestaque.getImagem()%>" 
						border="0" width="<%=largura%>" height="<%=altura%>">
						<% } %>						
					</a>					
						<% if (infoSuperDestaque.getTitulo() != null){ %>
							<p class="legenda"><%=infoSuperDestaque.getTitulo()%></p>
						<% } %>
										
					</div>
					
					<% } %>
					
					<p><%=infoSuperDestaque.getDataHora()%> <br> 
					<%=infoSuperDestaque.getFonte().toUpperCase()%> <br><br>
					 
					<%						 
						while (st.hasMoreTokens()) { 
							
							descricao = st.nextToken();
							
							if(cont < 68){
					%>		
								<%=descricao%>	
					 
					<%
							} 
							
							else if (cont == 68){
					%>		
							...
					<%		
							}		
							
							cont = cont + 1;
						}
					%><br><br>
					<a href="javascript:detalhe('<%=infoSuperDestaque.getLink()%>', <%=infoSuperDestaque.getCodSgti().intValue()%>);" class="iconmais">
					Leia mais
					</a>	
					</p>			
				<%
								
			}
			%>
		</td>
	</tr>
	<tr>
		<td class="destaque1">
			<h1>AGENDA</h1>
			<%
			SecaoBean secaoAgenda = (SecaoBean)capaPrincipal.getAgenda();
			
			int cont = 0;
						
			if(secaoAgenda != null && (secaoAgenda.getInformacaoBean() != null && secaoAgenda.getInformacaoBean().size() > 0)) {	
				Iterator iterator = secaoAgenda.getInformacaoBean().iterator();
				while (iterator.hasNext())	{		
					InformacaoBean infoAgenda = (InformacaoBean)iterator.next();		
					if( cont < 6 ){
					%>
						<a href="javascript:detalhe('<%=infoAgenda.getLink()%>', <%=infoAgenda.getCodSgti().intValue()%>);">
							<p>(<%=infoAgenda.getDataHora()%>)<br><%=infoAgenda.getTitulo()%></p>	
						</a>					
					<%
					}
					cont++;					
				}	
			} else {%>
				<p>N&atilde;o existem eventos para esta data</p>
				<%					
			}
			%>	
			<a href="#" onclick="javascript:subCapaAgenda();" class="iconmais"><img src="../images/icon_mais.png" border="0"></a>	
		</td>
		<td class="destaque2">
			<h1>MAIS ARTIGOS</h1>
			<%
			SecaoBean secaoMaisArtigos = (SecaoBean)capaPrincipal.getMaisArtigos();
			
			if(secaoMaisArtigos != null && (secaoMaisArtigos.getInformacaoBean() != null && secaoMaisArtigos.getInformacaoBean().size() > 0)) {	
				Iterator iterator = secaoMaisArtigos.getInformacaoBean().iterator();
				while (iterator.hasNext())	{		
					InformacaoBean infoMaisArtigos = (InformacaoBean)iterator.next();		
					%>
					<p><b><a href="javascript:detalhe('<%=infoMaisArtigos.getLink()%>', <%=infoMaisArtigos.getCodSgti()%>);"><%=infoMaisArtigos.getTitulo()%></a></b>
					<br>(<%=infoMaisArtigos.getDataHora()%>)<br>
					<a href="javascript:detalhe('<%=infoMaisArtigos.getLink()%>', <%=infoMaisArtigos.getCodSgti()%>);"><%=infoMaisArtigos.getDescricao()%></a></p>
					<%					
				}

			} else { %>
				<p>N�o existem artigos cadastrados para o ciclo atual</p>
		<% }//else %>
		<a href="#" onclick="javascript:subCapaArtigos();" class="iconmais"><img src="../images/icon_mais.png" border="0"></a>
		</td>
		<td class="destaque2">
			<h1>NA M&Iacute;DIA</h1>
			<%
			SecaoBean secaoTaxacaoClipping = (SecaoBean)capaPrincipal.getTaxacaoClips();
			
			if(secaoTaxacaoClipping != null && (secaoTaxacaoClipping.getInformacaoBean() != null && secaoTaxacaoClipping.getInformacaoBean().size() > 0)) {	
				Iterator iterator = secaoTaxacaoClipping.getInformacaoBean().iterator();
				while (iterator.hasNext())	{		
					InformacaoBean infoMaisArtigos = (InformacaoBean)iterator.next();		
					%>					
					<a href="javascript:detalhe('<%=infoMaisArtigos.getLink()%>', <%=infoMaisArtigos.getCodSgti().intValue()%>);">
					<p><b>(<%=infoMaisArtigos.getDataHora()%>)</b> <%=infoMaisArtigos.getDescricao()%><br><br><%=infoMaisArtigos.getTitulo()%></p>
					</a>
					<%					
				}
					
			} else { %>
					<p>N&atilde;o existem imagens da m&iacute;dia nesta data</p>
					<%				
			}
			%>	
			<a href="#" onclick="javascript:subCapaTaxacao();" class="iconmais"><img src="../images/icon_mais.png" border="0"></a>		
		</td>
	</tr>
	
	<%
	DestaqueAreaDtqa destaqueArea = (DestaqueAreaDtqa) new DestaqueAreaDao(request).buscar(
										DestaqueAreaDtqa.class, 
										Long.valueOf("1"));
	
	Set subAreas = destaqueArea.getDestaqueSubAreaDtqsas();
	
	int numColunas = 0;
	if(destaqueArea.getQtdColunasDtqa() != null) {
		numColunas = destaqueArea.getQtdColunasDtqa().intValue();
	}
	
	int contCol = 0;
	
	if ((subAreas!=null)&&(subAreas.size()>0)) {
		Iterator iterator = subAreas.iterator();
%>
	<tr>
<%
		while (iterator.hasNext())	{		
			DestaqueSubAreaDtqsa subDestaque = (DestaqueSubAreaDtqsa)iterator.next();
						
			if (contCol==numColunas) {
%>
	</tr>
	<tr>
<%
				contCol=0;
			}			
			
			Set itens = subDestaque.getDestaqueItemRelDtqirs();
			if ((itens!=null)&&(itens.size()>0)) {
				contCol++;
%>
	<td><h1><%=subDestaque.getIdentificacaoDtqsa()%></h1>
<%	
			Iterator itItens = itens.iterator();
				while (itItens.hasNext())	{		
					DestaqueItemRelDtqir item = (DestaqueItemRelDtqir)itItens.next(); 
					String titulo = "", titulo2 = "";
					String linkSgtl = "";
					String linkSgtil = "";
					long codSgti;
					NumberFormat nf=NumberFormat.getInstance();
					nf.setMinimumIntegerDigits(2);
					
					if ((item.getSegmentoItemSgti()==null)&&(item.getAgendaOcorrenciaAgeo()!=null))	{
						titulo = item.getAgendaOcorrenciaAgeo().getDescricaoAgeo();
					%>
		<a href="javascript:detalhe('ctrl_agenda.jsp', <%=item.getAgendaOcorrenciaAgeo().getCodAgeo()%>);">
							<p>(<%=Data.parseDate(item.getAgendaOcorrenciaAgeo().getDataEventoAgeo())%> - 
							<%=item.getAgendaOcorrenciaAgeo().getHoraEventoAgeo()%>:<%=nf.format((item.getAgendaOcorrenciaAgeo().getMinutoEventoAgeo()).longValue())%>)
							<br><%=item.getAgendaAge().getEventoAge()%></p>	
		</a>
		<a href="#" onclick="javascript:subCapaAgenda();" class="iconmais"><img src="../images/icon_mais.png" border="0"></a>	
<%
					} else if ((item.getAgendaOcorrenciaAgeo()==null)&&(item.getSegmentoItemSgti()!=null))	{
						titulo = item.getSegmentoItemSgti().getTituloSgti();
						titulo2 = item.getSegmentoItemSgti().getLinhaApoioSgti();	
						linkSgtl = item.getSegmentoItemSgti().getSegmentoSgt().getSegmentoLeiauteSgtl().getLinkSgtl();				
						codSgti = item.getSegmentoItemSgti().getCodSgti().longValue();
						linkSgtil = item.getSegmentoItemSgti().getSegmentoItemLeiauteSgtil().getLinkSgtil();
						
						int contaPalavras = 0;										
						String titulo2Resumido = null;
%>
		<p><a href="javascript:detalhe('<%=linkSgtil%>',<%=codSgti%>);"><b><%=titulo%></b><br>					
<% 
						if (titulo2 != null){ 
							StringTokenizer token = new StringTokenizer (titulo2," ");
							
							while (token.hasMoreTokens()) { 
							
								titulo2Resumido = token.nextToken();
							
								if(contaPalavras < 34){
%>
								<%=titulo2Resumido%>
<%
								} 
							
								else if (contaPalavras == 34){
%>		
								...
<%		
								}		
								contaPalavras = contaPalavras + 1;
							}
%>
		</a></p>
		<a href="#" onclick="javascript:subCapaGenerica('<%=linkSgtl%>');" class="iconmais"><img src="../images/icon_mais.png" border="0"></a>
										
<% 
						}
						else{
%>
		<br><a href="<%=linkSgtil%>" class="iconmais"><img src="../images/icon_mais.png" border="0"></a>
<% 
					} 
				}
			}
		}			
	}
%>
		</tr>
<%
}	
%>
</table>
</body>
<%@ include file="/include/ocultarAguarde.jsp"%>

</html>
