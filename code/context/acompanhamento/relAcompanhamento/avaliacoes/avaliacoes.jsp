	<%@ include file="/login/validaAcesso.jsp"%>
<%@ include file="/frm_global.jsp"%>

<%@ taglib uri="/WEB-INF/ecar-util.tld" prefix="util" %>
<%@ taglib uri="/WEB-INF/ecar-combo.tld" prefix="combo" %>

<%@ page import="ecar.pojo.TipoAcompanhamentoTa"%>
<%@ page import="ecar.dao.TipoAcompanhamentoDao"%>
<%@ page import="ecar.dao.UsuarioDao"%>
<%@ page import="comum.util.Util"%>
<%@ page import="ecar.dao.AcompReferenciaItemDao" %>
<%@ page import="ecar.dao.CorDao" %>
<%@ page import="ecar.pojo.AcompReferenciaItemAri" %> 
<%@ page import="ecar.pojo.AcompRelatorioArel"%>
<%@ page import="comum.util.Data" %> 
<%@ page import="java.util.List" %> 
<%@ page import="java.util.ArrayList" %> 
<%@ page import="java.util.Iterator" %>
<%@ page import="ecar.pojo.StatusRelatorioSrl" %>
<%@ page import="ecar.dao.ConfiguracaoDao" %> 
<%
try{ 
	
	ConfiguracaoCfg conf = (ConfiguracaoCfg)session.getAttribute("configuracao");

	if(request.getParameter("primeiroAriClicado") != null && !"".equals(request.getParameter("primeiroAriClicado"))) {
		request.setAttribute("codAri", request.getParameter("primeiroAriClicado"));
	}
	
	AcompReferenciaItemDao acompReferenciaItemDao = new AcompReferenciaItemDao(request); 
	CorDao corDao     = new CorDao(request);
	UsuarioDao usuDao = new UsuarioDao(request);
	TipoAcompanhamentoDao tipoAcompDao = new TipoAcompanhamentoDao(request);
	
	String ocultarObservacoesParecer = conf.getIndOcultarObservacoesParecer();
	String strCodAri = (String) request.getAttribute("codAri");
	
	
//José André Fernandes	
//Comentado por enquanto -> sem utilidade ?
// o Ari já vem modificado	

//	if(!"".equals(Pagina.getParamStr(request, "referencia")) && !"S".equals(Pagina.getParamStr(request, "linkControle"))){
//		// Veio nova referência, selecionada na combo, deve trocar o Ari.
//		strCodAri = Pagina.getParamStr(request, "referencia");
//	}
	

	
	AcompReferenciaItemAri acompReferenciaItem = (AcompReferenciaItemAri) acompReferenciaItemDao.buscar(AcompReferenciaItemAri.class, Long.valueOf(strCodAri));
	
	ValidaPermissao validaPermissao = new ValidaPermissao();	
	//if ( !validaPermissao.permissaoConsultaParecerIETT( acompReferenciaItem.getItemEstruturaIett().getCodIett() , seguranca ) )
	//{
	//	response.sendRedirect( request.getContextPath() +  "/acessoIndevido.jsp" );
	//}	
	
	StatusRelatorioSrl statusLiberado = (StatusRelatorioSrl) acompReferenciaItemDao.
                                            buscar(StatusRelatorioSrl.class, Long.valueOf(AcompReferenciaItemDao.STATUS_LIBERADO));

	TipoAcompanhamentoTa tipoAcompanhamento = (TipoAcompanhamentoTa) tipoAcompDao.buscar(TipoAcompanhamentoTa.class, Long.valueOf(Pagina.getParamStr(request, "codTipoAcompanhamento")));
	
	String exigeLiberarAcompanhamento = tipoAcompanhamento.getIndLiberarAcompTa();

	// essa variavel eh apenas utilizada no include do titulo
	String tituloMonitoramento = "VISUALIZAÇÃO";
	
	String primeiroIettClicado = Pagina.getParamStr(request, "primeiroIettClicado");
	String codArisControleV = Pagina.getParamStr(request, "codArisControleVisualizacao");
	

%>

<html lang="pt-br"> 
<head>
<%@ include file="/include/meta.jsp"%>
<%@ include file="/titulo.jsp"%>
<link rel="stylesheet" href="<%=_pathEcar%>/css/style_<%=configuracaoCfg.getEstilo().getNome()%>.css" type="text/css">
<script language="javascript" src="<%=_pathEcar%>/js/menu_retratil.js"></script>
<script language="javascript" src="<%=_pathEcar%>/js/focoInicial.js"></script>
<script>
function recarregar(){
	document.forms[0].referencia = "";
	document.forms[0].action = "avaliacoes.jsp";
	document.forms[0].submit();
}
function trocaAba(link, codAri){	
	document.form.codAri.value = codAri;
	document.form.itemDoNivelClicado.value = "<%=primeiroIettClicado%>";
	document.form.primeiroIettClicado.value = "<%=primeiroIettClicado%>";
	document.form.codArisControleVisualizacao.value = "<%=codArisControleV%>";
	document.form.action = link + "&codArisControleVisualizacao=<%=codArisControleV%>";
	document.form.submit();
}
function trocaAbaImpressao(link){
	document.forms[0].action = link;
	document.forms[0].submit();
}
function mostrarEsconder(codTpfa) {
	if (document.getElementById('ultimasPosicoes'+codTpfa).style.display=='none') {
	     document.getElementById('ultimasPosicoes'+codTpfa).style.display='';
	     document.getElementById('linkMostrar'+codTpfa).style.display='none';
	     document.getElementById('linkEsconder'+codTpfa).style.display='';
	} else {
	     document.getElementById('ultimasPosicoes'+codTpfa).style.display='none';
	     document.getElementById('linkMostrar'+codTpfa).style.display='';
	     document.getElementById('linkEsconder'+codTpfa).style.display='none';
	}
}
</script>
</head>

<body>

<%@ include file="/../../cabecalho.jsp" %>
<%@ include file="/../../divmenu.jsp"%> 

<div id="conteudo"> 
	<%String visaoDescricao = null;%>
	<%@ include file="../../tituloMonitoramento.jsp" %>

	<%
	String formaVisualizacao =  Pagina.getParamStr(request, "hidFormaVisualizacaoEscolhida");		
	Boolean exibirAbasAcompanhamento = Boolean.FALSE;
	
	if(formaVisualizacao.equalsIgnoreCase("geral")) {
		exibirAbasAcompanhamento= Boolean.TRUE;
	} 
	 %>

	<input type="hidden" id="hidFormaVisualizacaoEscolhida" name="hidFormaVisualizacaoEscolhida" value="<%=formaVisualizacao %>">

	<util:barraLinkTipoAcompanhamentoTag
		codTipoAcompanhamentoSelecionado="<%=Pagina.getParamStr(request, "codTipoAcompanhamento")%>"
		chamarPagina="../../posicaoGeral.jsp"
		tela="visualizacao"
		exibirAbasAcompanhamento="<%=exibirAbasAcompanhamento%>"
	/>
	
	<%
	String codTipoAcompanhamento = Pagina.getParamStr(request, "codTipoAcompanhamento");
	String mesReferencia = Pagina.getParamStr(request, "mesReferencia");
	String periodo = Pagina.getParamStr(request, "periodo");
	
	String telaListagem = "posicaoGeral.jsp?codTipoAcompanhamento="+codTipoAcompanhamento +"&mesReferencia="+mesReferencia + "&periodo=" + periodo;
	%>

	<util:barraLinksRelatorioAcompanhamento
		acompReferenciaItem="<%=acompReferenciaItem%>"  
		funcaoSelecionada="avaliacoes"
		primeiroIettClicado="<%=request.getParameter("primeiroIettClicado")%>"
		primeiroAriClicado="<%=request.getParameter("primeiroAriClicado")%>"
		caminho="<%=_pathEcar + "/acompanhamento/"%>"
		codTipoAcompanhamento="<%=Pagina.getParamStr(request, "codTipoAcompanhamento")%>"
		gruposUsuario="<%=seguranca.getGruposAcesso()%>"
		abaSuperior ="S"
		listaGeral="<%=telaListagem %>"
	/>
	
	
	<%@ include file="../botoesAvancaRetrocede.jsp" %>

	<%
	String telaAnt = "";

	if(acompReferenciaItem.getItemEstruturaIett() != null ){
		telaAnt = _pathEcar + "/acompanhamento/posicaoGeral.jsp?fecharMenu=true&titulo=Geral&codTipoAcompanhamento=" + Pagina.getParamStr(request, "codTipoAcompanhamento")  + "&mesReferencia="+acompReferenciaItem.getAcompReferenciaAref().getCodAref()+ "&periodo=" + Pagina.getParamStr(request, "periodo") + "&hidFormaVisualizacaoEscolhida="+Pagina.getParamStr(request, "hidFormaVisualizacaoEscolhida")+ "#ancora" + acompReferenciaItem.getItemEstruturaIett().getCodIett();
	}
	
	
	%>
	
	<util:arvoreEstruturas 
		itemEstrutura="<%=acompReferenciaItem.getItemEstruturaIett()%>" 
		exibirLinks="false" 
		proximoNivel="false" 
		contextPath="<%=_pathEcar%>"
		primeiroIettClicado="<%=primeiroIettClicado%>"
		primeiroAriClicado="<%=Pagina.getParamStr(request, "primeiroAriClicado")%>"
		telaAnterior="<%=telaAnt%>"
	/> 

	<table border="0" width="100%">
		<tr>
			<td valign="bottom" class="texto" align="right">
					<b>Data do relat&oacute;rio:</b> <%=Data.parseDate(Data.getDataAtual())%><br>
					<b>M&ecirc;s de refer&ecirc;ncia: </b>
					<%=acompReferenciaItem.getAcompReferenciaAref().getNomeAref() %>
				</td>
			</tr>
	</table>

	<util:barraLinksRelatorioAcompanhamento
		acompReferenciaItem="<%=acompReferenciaItem%>"  
		funcaoSelecionada="avaliacoes"
		primeiroIettClicado="<%=primeiroIettClicado%>"
		primeiroAriClicado="<%=request.getParameter("primeiroAriClicado")%>"
		caminho="<%=_pathEcar + "/acompanhamento/relAcompanhamento/"%>"
		codTipoAcompanhamento="<%=Pagina.getParamStr(request, "codTipoAcompanhamento")%>"
		gruposUsuario="<%=seguranca.getGruposAcesso()%>"
	/>
<br>

	
<form  name="form" method="post" >	
<input type="hidden" name="codTipoAcompanhamento" value="<%=Pagina.getParamStr(request, "codTipoAcompanhamento")%>">
<input type="hidden" name="periodo"               value="<%=Pagina.getParamStr(request, "periodo")%>">
<input type="hidden" name="mesReferencia"         value="<%=acompReferenciaItem.getAcompReferenciaAref().getCodAref().toString()%>">
<input type="hidden" name="niveisPlanejamento"    value="<%=Pagina.getParamStr(request, "niveisPlanejamento")%>">
<input type="hidden" name="codAri"                value="<%=Pagina.getParamStr(request, "codAri")%>">
<input type="hidden" name="primeiroIettClicado"   value="<%=primeiroIettClicado%>">
<input type="hidden" name="primeiroAriClicado"    value="<%=Pagina.getParamStr(request, "primeiroAriClicado")%>">
<input type="hidden" name="codigosItem"           value="<%=Pagina.getParamStr(request, "codigosItem")%>">
<input type="hidden" name="itemDoNivelClicado" value="<%=primeiroIettClicado%>">

<!-- Campo necessário para botões de Avança/Retrocede -->
<input type="hidden" name="codArisControle" value="<%=Pagina.getParamStr(request, "codArisControle")%>">
<input type="hidden" name="codArisControleVisualizacao" value="<%=Pagina.getParamStr(request, "codArisControleVisualizacao")%>">
<input type="hidden" name="codAri" value="<%=acompReferenciaItem.getCodAri()%>">
<input type="hidden" name="referencia" value="<%=acompReferenciaItem.getCodAri()%>">

	
<!-- Campos para manter a seleção em Posição Geral -->
<%-- 
if(acompReferenciaItem != null) {
%>
	<table border="0" width="100%">
		<tr>
			<td valign="bottom" class="texto" align="left" colspan="2"> 
				<b>Data do relatório:</b> <%=Data.parseDate(Data.getDataAtual())%><br>
				<b>Mês de referência: </b>
				<combo:ComboReferenciaTag
					nome="referencia"
					acompReferenciaItem="<%=acompReferenciaItem%>"
					scripts="onchange='recarregar()'"
				/>
			</td>
		</tr>
</table>

<%
}
--%>

<!-- Campos para manter a seleção em Posição Geral -->
<table class="barrabotoes" cellpadding="0" cellspacing="0">
	<tr>
		<td align="left">
		<b>Avaliações</b>
		</td>
	</tr>
</table>

<table class="form" width="100%" border="0" cellpadding="0" cellspacing="0">
<%
	List lista = new ArrayList();
	if("N".equals(exigeLiberarAcompanhamento) || acompReferenciaItem.getAcompRelatorioArels() != null){
		lista = acompReferenciaItemDao.getAcompRelatorioArelOrderByFuncaoAcomp(acompReferenciaItem);
	}
	Iterator it = lista.iterator();
	
	String descricao = "";
	String atribuidoPor = "";
	String situacao = "";
	String cor = "";
	String complemento = "";
	String dataUltimaAtualizacao = "";
	int cont = 0;
	
	String titulo = "";
	
	while (it.hasNext()){
		AcompRelatorioArel acompRelatorio = (AcompRelatorioArel) it.next();
		
		if(acompRelatorio.getUsuarioUsuUltimaManutencao() != null)
			atribuidoPor = acompRelatorio.getUsuarioUsuUltimaManutencao().getNomeUsuSent();
		else{
			atribuidoPor = "N/I";
			titulo += " Não Informado <br> ";
			
		}
		if(acompRelatorio.getDescricaoArel() != null)
			descricao = Util.normalizaQuebraDeLinhaHTML(acompRelatorio.getDescricaoArel());
		else
			descricao = "N/I";
		
		if(acompRelatorio.getComplementoArel() != null)
			complemento = Util.normalizaQuebraDeLinhaHTML(acompRelatorio.getComplementoArel());
		else
			complemento = "N/I";
		
		if(acompRelatorio.getSituacaoSit() != null)
			situacao = acompRelatorio.getSituacaoSit().getDescricaoSit();
		else
			situacao = "N/I";
		
		if(acompRelatorio.getDataUltManutArel() != null)
			dataUltimaAtualizacao = Data.parseDate(acompRelatorio.getDataUltManutArel());
		else
			dataUltimaAtualizacao = "N/I";
		
		if(acompRelatorio.getCor() != null){
			cor = "<image src=\"../../../images/";
			cor = cor + corDao.getImagemSinal(acompRelatorio.getCor(), acompRelatorio.getTipoFuncAcompTpfa());
			cor = cor + "\">";
		}
		else {
			cor = "N/I";
		}	
			
		if(acompRelatorio.getIndLiberadoArel() == null || "N".equals(acompRelatorio.getIndLiberadoArel())) {
			cor = "N/L";
			situacao = "N/L";
			descricao = "N/L";
			complemento = "N/L";
			dataUltimaAtualizacao = "N/L";
			
		}
		
		if(acompRelatorio.getUsuarioUsuUltimaManutencao() != null){
			String telefone = "";
	        if(acompRelatorio.getUsuarioUsuUltimaManutencao().getComercDddUsu() != null && !"".equals(acompRelatorio.getUsuarioUsuUltimaManutencao().getComercDddUsu())){
	        	telefone += acompRelatorio.getUsuarioUsuUltimaManutencao().getComercDddUsu();
	        }
	        if(acompRelatorio.getUsuarioUsuUltimaManutencao().getComercTelefoneUsu() != null && !"".equals(acompRelatorio.getUsuarioUsuUltimaManutencao().getComercTelefoneUsu())){
	        	if(!"".equals(telefone)){
	        		telefone += " ";
	        	}
	        	telefone += acompRelatorio.getUsuarioUsuUltimaManutencao().getComercTelefoneUsu();
	        }
	        titulo += telefone;
	        
	        if (usuDao.getCelularByUsuario(acompRelatorio.getUsuarioUsuUltimaManutencao()) != null && !"".equals(usuDao.getCelularByUsuario(acompRelatorio.getUsuarioUsuUltimaManutencao()))){ 
	        	titulo += " <br> ";
	        	titulo += usuDao.getCelularByUsuario(acompRelatorio.getUsuarioUsuUltimaManutencao());
	        }
			
	        if(!"".equals(titulo)){
				titulo += " <br> ";
			}
			titulo += acompRelatorio.getUsuarioUsuUltimaManutencao().getEmail1UsuSent();
		
			
		}
		
		//variavel de controle, passada como parametro para a funcao que faz o hint
		String posicao ="-1";
		String imagem_inativa = "";
		if (acompRelatorio.getUsuarioUsuUltimaManutencao() != null)
			if (!"S".equals(acompRelatorio.getUsuarioUsuUltimaManutencao().getIndAtivoUsu())){
				imagem_inativa="<img src=\"../../../images/icon_usuario_inativo.png\" title=\"Usuário Inativo\">";
			}
%>
		<tr><td colspan=2>&nbsp;</td></tr>
		<tr>
			<td class="label" align="right" width="30%">
				<%=acompRelatorio.getTipoFuncAcompTpfa().getLabelPosicaoTpfa()%> atribuído por:
			</td>
			<td  class="item_InfoUsu_td" onMouseOver="javascript:showInfoUsu(<%=cont%>,'<%=atribuidoPor%>','<%=posicao%>')" onMouseOut="javascript:hideInfoUsu(<%=cont%>,'<%=atribuidoPor%>','<%=posicao%>')"><%=atribuidoPor%><span id="spanInfoUsu<%=cont%>_<%=atribuidoPor%>_<%=posicao%>" class='item_InfoUsu_span'><%=titulo%></span><%=imagem_inativa%></td>
		</tr>
		<tr>
			<td class="label" align="right">Função:</td>
			<td><%=acompRelatorio.getTipoFuncAcompTpfa().getLabelTpfa()%></td>
		</tr>
		<%
		if("N".equals(exigeLiberarAcompanhamento) || acompRelatorio.getAcompReferenciaItemAri().getStatusRelatorioSrl().equals(statusLiberado)){
		%>

				<tr>
					<td class="label" align="right">Situação:</td>
					<td><%=situacao%></td>
				</tr>
				<tr>
					<td class="label" align="right">Cor:</td>
					<td><%=cor%></td>
				</tr>
				<tr>
					<td class="label" align="right">Descrição:</td>
					<td><%=descricao%></td>
				</tr>
				
				<%
				if (ocultarObservacoesParecer == null || ocultarObservacoesParecer.equals("N")){
				%>
				
				<tr>
					<td class="label" align="right">Observações:</td>
					<td><%=complemento%></td>
				</tr>
				
				<%
				}
				%>
				
				<tr>
					<td class="label" align="right">Atualização:</td>
					<td><%=dataUltimaAtualizacao%></td>
				</tr>
		<%
		} else {
			%>	<tr>
					<td></td>
					<td><b>Acompanhamento não liberado.</b></td>
				</tr>			
			<%
		}

		%>
		<tr><td colspan=2>&nbsp;</td></tr>
		<tr><td class="espacador" colspan=2><img src="../../../images/pixel.gif"></td></tr>
		<!--  3 Ultimas Posicoes... -->
		<%
		List arels = acompReferenciaItemDao.getUltimosAcompanhamentosItem(acompRelatorio.getAcompReferenciaItemAri(), acompRelatorio.getTipoFuncAcompTpfa(), Integer.valueOf(3));

		if (arels != null && arels.size() > 0){
		%>
		<tr class="bgBranco" id="linkMostrar<%=acompRelatorio.getTipoFuncAcompTpfa().getCodTpfa()%>">
			<td align="right"><a href="#" onclick="mostrarEsconder(<%=acompRelatorio.getTipoFuncAcompTpfa().getCodTpfa()%>);">Mostrar &Uacute;ltimas Posi&ccedil;&otilde;es Emitidas pelo <%=acompRelatorio.getTipoFuncAcompTpfa().getLabelTpfa()%></a></td>			
			<td>&nbsp;</td>
		</tr>
		<tr class="bgBranco" id="linkEsconder<%=acompRelatorio.getTipoFuncAcompTpfa().getCodTpfa()%>" style="display:none">
			<td align="right"><a href="#" onclick="mostrarEsconder(<%=acompRelatorio.getTipoFuncAcompTpfa().getCodTpfa()%>);">Fechar &Uacute;ltimas Posi&ccedil;&otilde;es Emitidas pelo <%=acompRelatorio.getTipoFuncAcompTpfa().getLabelTpfa()%></a></td>			
			<td>&nbsp;</td>
		</tr>
		<tr class="bgBranco">
		<td colspan="2">
				<table id="ultimasPosicoes<%=acompRelatorio.getTipoFuncAcompTpfa().getCodTpfa()%>" style="display:none" class="bgBranco">
					<%
				String ultPeriodo = "";
				String ultMesAno = "";
				String ultSituacao = "";
				String ultCor = "";
				String ultAvaliacao = "";
				String ultObservacao = "";
				String atribuidoPorQuem = "";

				Iterator itArels = arels.iterator();
				while(itArels.hasNext()){					
					AcompRelatorioArel arel = (AcompRelatorioArel) itArels.next();
					//if(Pagina.getParamStr(request, "codTpfa").equals(arel.getTipoFuncAcompTpfa().getCodTpfa().toString())){
						
						if(arel.getAcompReferenciaItemAri() != null &&
							arel.getAcompReferenciaItemAri().getAcompReferenciaAref() != null){
							ultPeriodo = arel.getAcompReferenciaItemAri().getAcompReferenciaAref().getNomeAref();
							ultMesAno = arel.getAcompReferenciaItemAri().getAcompReferenciaAref().getMesAref() + "/" + arel.getAcompReferenciaItemAri().getAcompReferenciaAref().getAnoAref();
						}
						
						if(arel.getUsuarioUsuUltimaManutencao() != null) {
							atribuidoPorQuem = arel.getUsuarioUsuUltimaManutencao().getNomeUsu();
						}						
						
													
						if(arel.getSituacaoSit() != null){
							ultSituacao = arel.getSituacaoSit().getDescricaoSit();
						}
						else{
							ultSituacao = "N/I";
						}
						
						if(arel.getCor() != null){
							ultCor = "<img src=\"" + "../../../images/" + corDao.getImagemSinal(arel.getCor(), arel.getTipoFuncAcompTpfa()) + 
								  "\" align=\"top\">";
						}
						else {
							ultCor = "N/I";
						}
						
						if(arel.getDescricaoArel() != null && !"".equals(arel.getDescricaoArel().trim())){
							ultAvaliacao = Util.normalizaQuebraDeLinhaHTML(arel.getDescricaoArel());
						}
						else {
							ultAvaliacao = "N/I";
						}
						
						if(arel.getComplementoArel() != null && !"".equals(arel.getComplementoArel().trim())){
							ultObservacao = Util.normalizaQuebraDeLinhaHTML(arel.getComplementoArel());
						}
						else {
							ultObservacao = "N/I";
						}
						%>-
							
							<tr>
								 <td class="label"><%=acompRelatorio.getTipoFuncAcompTpfa().getLabelPosicaoTpfa()%> atribuído por:</td>
								 <td><%=atribuidoPorQuem%></td>
							</tr> 							
							
							<tr>
								<td class="label">Per&iacute;odo</td>
								<td><%=ultPeriodo%></td>
							</tr>
							<tr>
								<td class="label">Mês/Ano</td>
								<td><%=ultMesAno%></td>
							</tr>
							<tr>
								 <td class="label">Situa&ccedil;&atilde;o:</td>
								 <td><%=ultSituacao%></td>
							</tr> 	
							<tr>
								 <td class="label">Cor:</td>
								 <td><%=ultCor%></td>
							</tr> 
							<tr>
								 <td class="label">Descri&ccedil;&atilde;o:</td>
								 <td><%=ultAvaliacao%></td>
							</tr> 
							
							<%
							if (ocultarObservacoesParecer == null || ocultarObservacoesParecer.equals("N")){
							%>
							
								<tr>
									 <td class="label">Observa&ccedil;&otilde;es:</td>
									 <td><%=ultObservacao%></td>
								</tr>
							
							<%
							}
							%>
							
							<tr>
								 <td class="label" colspan="2">&nbsp;</td>
							</tr> 
						<%
					//}
				}
				%>
				</table>
			</td>
		</tr>
		<tr><td class="espacador" colspan=2><img src="../../../images/pixel.gif"></td></tr>
		<tr><td colspan=2>&nbsp;</td></tr>
		<%
		}
		%>
		<!-- /Ultimas Posicoes... -->
		<%
		cont++;
	}
%>
	<tr><td colspan=2>&nbsp;</td></tr>
</table>

<table class="barrabotoes" cellpadding="0" cellspacing="0">
	<tr>
		<td align="left">
			&nbsp;
		</td>
	</tr>
</table>

</form>

<%
} catch (ECARException e){
	org.apache.log4j.Logger.getLogger(this.getClass()).error(e);
	Mensagem.alert(_jspx_page_context, e.getMessageKey());
} catch (Exception e){
%>
	<%@ include file="/excecao.jsp"%>
<%
}
%>

<br>
</div>
</body>
<% /* Controla o estado do Menu (aberto ou fechado) */%>
<%@ include file="../../../include/estadoMenu.jsp"%>
<%@ include file="../../../include/mensagemAlert.jsp" %>
</html>