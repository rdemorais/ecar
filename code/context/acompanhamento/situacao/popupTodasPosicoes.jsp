<%@ page import="ecar.pojo.AcompReferenciaItemAri" %>
<%@ page import="ecar.pojo.TipoFuncAcompTpfa" %>
<%@ page import="ecar.pojo.AcompRelatorioArel" %>

<%@ page import="ecar.dao.AcompReferenciaItemDao" %>
<%@ page import="ecar.dao.TipoFuncAcompDao" %>
<%@ page import="ecar.dao.CorDao" %>

<%@ page import="comum.util.Pagina" %>
<%@ page import="comum.util.Util"%>

<%@ page import="java.util.List" %>
<%@ page import="java.util.Iterator" %>
<jsp:directive.page import="ecar.dao.ConfiguracaoDao"/>
<jsp:directive.page import="ecar.pojo.ConfiguracaoCfg"/>

<%@ include file="../../frm_global.jsp"%> 

<%@page import="gov.pr.celepar.sentinela.client.pojo.Usuario"%>
<%@page import="ecar.pojo.UsuarioUsu"%><html lang="pt-br">
	<head>
		<%@ include file="../../include/meta.jsp"%>
		<%@ include file="/titulo.jsp"%>
		
		<link rel="stylesheet" href="<%=_pathEcar%>/css/style_<%=configuracaoCfg.getEstilo().getNome()%>.css" type="text/css">
		
		<script language="javascript" src="../../js/popUp.js"></script>
	</head>
	<body>
		<br>
<%
try {
	AcompReferenciaItemDao acompReferenciaItemDao = new AcompReferenciaItemDao(request); 
	String codAri = Pagina.getParamStr(request, "codAri");
	AcompReferenciaItemAri acompReferenciaItem = null;
	List arels = null;
	TipoFuncAcompTpfa funcao = null;
	String codTpfa = Pagina.getParamStr(request,"codTpfa");
	//ConfiguracaoCfg configuracao = new ConfiguracaoDao(request).getConfiguracao();
	

	String ocultarObservacoesParecer = configuracaoCfg.getIndOcultarObservacoesParecer();
	if(codAri != null && codTpfa!= null) {
		acompReferenciaItem = (AcompReferenciaItemAri) acompReferenciaItemDao.buscar(AcompReferenciaItemAri.class,Long.valueOf(codAri));
		funcao = (TipoFuncAcompTpfa) new TipoFuncAcompDao(request).buscar(TipoFuncAcompTpfa.class, Long.valueOf(codTpfa));
		arels = acompReferenciaItemDao.getUltimosAcompanhamentosItem(acompReferenciaItem, funcao, null);
		
		
	
%>
		<H1>Relação de Todos(as) <%=configuracaoCfg.getLabelSituacaoListaPareceres() %> Emitidos(as)
		<br>
		<%=funcao.getLabelPosicaoTpfa()%>
		</H1>
<%
	
	}

	if(arels != null && arels.size() > 0) {
%>
		<table class="form">
<%
		String periodo = "";
		String mesAno = "";
		String situacao = "";
		String cor = "";
		String avaliacao = "";
		String observacao = "";
		String imagePath = "";
		CorDao corDao = new CorDao(request);
		UsuarioUsu usuarioImagem = null;
		String hashNomeArquivo = "";
		String pathRaiz = configuracaoCfg.getRaizUpload();

		Iterator itArels = arels.iterator();
		while(itArels.hasNext()){					
			AcompRelatorioArel arel = (AcompRelatorioArel) itArels.next();
					
			if(arel.getAcompReferenciaItemAri() != null &&
			arel.getAcompReferenciaItemAri().getAcompReferenciaAref() != null){
				periodo = arel.getAcompReferenciaItemAri().getAcompReferenciaAref().getNomeAref();
				mesAno = arel.getAcompReferenciaItemAri().getAcompReferenciaAref().getDiaAref() + "/" +
					arel.getAcompReferenciaItemAri().getAcompReferenciaAref().getMesAref() + "/" +
					arel.getAcompReferenciaItemAri().getAcompReferenciaAref().getAnoAref();
			}
													
			if(arel.getSituacaoSit() != null){
				situacao = arel.getSituacaoSit().getDescricaoSit();
			}
			else{
				situacao = "N/I";
			}
			
			if(arel.getCor() != null){
				imagePath = corDao.getImagemPersonalizada(arel.getCor(), arel.getTipoFuncAcompTpfa(), "D");
				if( imagePath != null && imagePath.trim().length()>0) {
					usuarioImagem = ((ecar.login.SegurancaECAR)request.getSession().getAttribute("seguranca")).getUsuario(); 
					hashNomeArquivo = Util.calcularHashNomeArquivo(pathRaiz, imagePath);
					Util.adicionarMapArquivosAtuaisUsuarios(usuarioImagem, hashNomeArquivo, pathRaiz, imagePath);
					
					/* -- As tags do CSS "max-width" e "max-height" não funcionam no IE6 ou menor -- */ 
				    cor = "<img border=\"0\" src=\"" + request.getContextPath() + "/DownloadFile?tipo=open&RemoteFile=";
				    cor += hashNomeArquivo + "\" style=\"width: 23px; height: 23px;\" title=\"" + arel.getTipoFuncAcompTpfa().getLabelTpfa() + "\">";
				} else {
					if( arel.getCor() != null && arel.getCor().getCodCor() != null ) { 
						cor = "<img border=\"0\" src=\"" + _pathEcar + "/images/";
						cor += corDao.getImagemSinal(arel.getCor(), arel.getTipoFuncAcompTpfa()) + "\" title=\"";
						cor += arel.getTipoFuncAcompTpfa().getLabelTpfa() + "\" >";
					}
				}
			}
			else {
				cor = "N/I";
			}
						
			if(arel.getDescricaoArel() != null && !"".equals(arel.getDescricaoArel().trim())){
				avaliacao = Util.normalizaQuebraDeLinhaHTML(arel.getDescricaoArel());
				avaliacao = Util.normalizaCaracterMarcador(avaliacao);
				avaliacao = Util.normalizaCaracterHTML(avaliacao);
			}
			else {
				avaliacao = "N/I";
			}
						
			if(arel.getComplementoArel() != null && !"".equals(arel.getComplementoArel().trim())){
				observacao = Util.normalizaQuebraDeLinhaHTML(arel.getComplementoArel());
				observacao = Util.normalizaCaracterMarcador(observacao);
				observacao = Util.normalizaCaracterHTML(observacao);
			}
			else {
				observacao = "N/I";
			}
%>
			<tr>
				 <td class="label" colspan="2">
				 	<table class="barrabotoes" cellpadding="0" cellspacing="0">
						<tr><td align="left">&nbsp;</td></tr>
					</table>
				 </td>
			</tr>
			<tr>
				<td class="label">Per&iacute;odo</td>
				<td><%=periodo%></td>
			</tr>
			<tr>
				<td class="label">Dia/Mês/Ano</td>
				<td><%=mesAno%></td>
			</tr>
			<tr>
				 <td class="label"><%=configuracaoCfg.getLabelSituacaoParecer() %>:</td>
				 <td><%=situacao%></td>
			</tr> 	
			<tr>
				 <td class="label"><%=configuracaoCfg.getLabelCorParecer() %>:</td>
				 <td><%=cor%></td>
			</tr> 
			<tr>
				 <td class="label"><%=funcao.getLabelPosicaoTpfa()%>:</td>
				 <td><%=avaliacao%></td>
			</tr> 
			<%
			if (ocultarObservacoesParecer == null || ocultarObservacoesParecer.equals("N")){
			%>
			
				<tr>
					 <td class="label">Observa&ccedil;&otilde;es:</td>
					 <td><%=observacao%></td>
				</tr>
			
			<%
			}
			%>
			<tr>
				 <td class="label" colspan="2">&nbsp;</td>
			</tr> 
<%
		}
%>
		</table>
<%
	} else {
		out.println("Não existem registros informados.");
	}
} catch (ECARException e){
	org.apache.log4j.Logger.getLogger(this.getClass()).error(e);
	Mensagem.alert(_jspx_page_context, e.getMessageKey());
} catch (Exception  e){
%>
	<%@ include file="/excecao.jsp"%>
<%
}
%>
	</body>
</html>

