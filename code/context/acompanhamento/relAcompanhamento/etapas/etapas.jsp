<%@ include file="/login/validaAcesso.jsp"%>
<%@ include file="/frm_global.jsp"%>

<%@ page import="ecar.dao.AcompReferenciaItemDao"%>
<%@ page import="ecar.pojo.AcompReferenciaItemAri"%>
<%@ page import="ecar.dao.ItemEstruturaDao"%>
<%@ page import="ecar.dao.EstruturaDao"%>
<%@ page import="ecar.pojo.EstruturaEtt"%>
<%@ page import="ecar.pojo.ObjetoEstrutura"%>
<%@ page import="ecar.pojo.ItemEstruturaIett"%>
<%@ page import="ecar.pojo.SisAtributoSatb"%>

<%@ page import="comum.util.Data"%>
<%@ page import="comum.util.Util"%>

<%@ page import="java.util.List"%>
<%@ page import="java.util.Iterator"%>
<%@ page import="ecar.pojo.FonteRecursoFonr"%>

<%@ taglib uri="/WEB-INF/ecar-util.tld" prefix="util"%>
<%@ taglib uri="/WEB-INF/ecar-combo.tld" prefix="combo"%>

<%
try{  
	AcompReferenciaItemDao acompReferenciaItemDao = new AcompReferenciaItemDao(request); 
	ItemEstruturaIett itemEstrutura = null;
	AcompReferenciaItemAri acompReferenciaItem = null;
	ItemEstruturaDao itemEstruturaDao = new ItemEstruturaDao(request);
	String strCodAri = Pagina.getParamStr(request, "codAri");
	String codIettRelacao = Pagina.getParamStr(request, "codIettRelacao");
	ValidaPermissao validaPermissao = new ValidaPermissao();
	EstruturaDao estruturaDao = new EstruturaDao(request);
	String codEttSelecionado = Pagina.getParamStr(request, "codEttSelecionado");
	String codTipoAcompanhamento = Pagina.getParamStr(request, "codTipoAcompanhamento");
	String mesReferencia = Pagina.getParamStr(request, "mesReferencia");
	String periodo = Pagina.getParamStr(request, "periodo");
	String funcaoSelecionada = "ETAPA";

	
	if(!"".equals(strCodAri)) {
		acompReferenciaItem = (AcompReferenciaItemAri) new AcompReferenciaItemDao(request).buscar(
						AcompReferenciaItemAri.class, Long.valueOf(strCodAri));
		itemEstrutura = acompReferenciaItem.getItemEstruturaIett();
		
	} else if(!"".equals(codIettRelacao)) {
		itemEstrutura = (ItemEstruturaIett)itemEstruturaDao.buscar(ItemEstruturaIett.class, Long.valueOf(codIettRelacao));
	}
	
	if(!"".equals(Pagina.getParamStr(request, "referencia")) && !"S".equals(Pagina.getParamStr(request, "linkControle"))){
		// Veio nova referência, selecionada na combo, deve trocar o Ari.
		strCodAri = Pagina.getParamStr(request, "referencia");
	}
	
	// essa variavel eh apenas utilizada no include do titulo
	String codArisControleV = Pagina.getParamStr(request, "codArisControleVisualizacao");
%>


<%@page import="comum.util.ConstantesECAR"%><html lang="pt-br">
	<head>
		<%@ include file="/include/meta.jsp"%>
		<%@ include file="/titulo.jsp"%>
		<link rel="stylesheet" href="<%=_pathEcar%>/css/style_<%=configuracaoCfg.getEstilo().getNome()%>.css" type="text/css">
		<script language="javascript" src="<%=_pathEcar%>/js/menu_retratil.js" type="text/javascript"></script>
		<script language="javascript" src="<%=_pathEcar%>/js/focoInicial.js" type="text/javascript"></script>
		<script language="javascript" src="<%=_pathEcar%>/js/tableSort.js" type="text/javascript"></script>
		<script type="text/javascript">
		function trocaAbaSemAri(link, codIettRelacao){
			document.form.codIettRelacao.value = codIettRelacao;
			document.form.itemDoNivelClicado.value = codIettRelacao;
			document.form.action = link;
			document.forms[0].clicouAba.value = "S";
			document.form.submit();
		}
		function recarregar(){
			document.forms[0].action = "eventos.jsp?codArisControleVisualizacao=<%=codArisControleV%>";
			document.forms[0].submit();
		}
		function trocaAba(link, codAri){
			document.forms[0].codIettRelacao.value = '';
			document.forms[0].codAri.value = codAri;
			document.forms[0].codArisControleVisualizacao.value = "<%=codArisControleV%>";
			document.forms[0].action = link + "&codArisControleVisualizacao=<%=codArisControleV%>";
			document.forms[0].clicouAba.value = "S";
			document.forms[0].submit();
		}

		function trocaAba(link, codAri, cod){
			document.forms[0].codIettRelacao.value = '';
			document.forms[0].codAri.value = codAri;
			document.form.codArisControleVisualizacao.value = "<%=codArisControleV%>";
			document.forms[0].action = link + "&codArisControleVisualizacao=<%=codArisControleV%>";
			document.forms[0].clicouAba.value = "S";
			document.forms[0].submit();
		}
		function trocaAbaImpressao(link){
			document.forms[0].action = link;
			document.forms[0].clicouAba.value = "S";
			document.forms[0].submit();
		}
		function aoClicarTrocaAbaEstrutura(form, codEtt){
			form.codEttSelecionado.value = codEtt;
			form.action = "etapas.jsp";
			document.forms[0].clicouAba.value = "S";
			form.submit();
		}

		'/ecar/acompanhamento/relAcompanhamento/eventos/eventos.jsp?primeiroIettClicado=3040&primeiroAriClicado=738','738',''
</script>
	</head>

	<body>

		<%@ include file="/cabecalho.jsp"%>
		<%@ include file="/divmenu.jsp"%>
 
		<div id="conteudo">
		<%@ include file="../../form_visualizar.jsp" %>	

		<form name="form" method="post" action="">	
				
	<!-- Campos para manter a seleção em Posição Geral -->

	<input type="hidden" name="codTipoAcompanhamento" value="<%=Pagina.getParamStr(request, "codTipoAcompanhamento")%>">
	<input type="hidden" name="periodo" value="<%=Pagina.getParamStr(request, "periodo")%>">
	<!-- input type="hidden" name="mesReferencia" value="<%=Pagina.getParamStr(request, "mesReferencia")%>" -->
	<input type="hidden" name="mesReferencia" value="<%=acompReferenciaItem.getAcompReferenciaAref().getCodAref().toString()%>">
	<input type="hidden" name="niveisPlanejamento" value="<%=Pagina.getParamStr(request, "niveisPlanejamento")%>">
	<input type="hidden" name="codAri" value="<%=Pagina.getParamStr(request, "codAri")%>">
	<input type="hidden" name="primeiroIettClicado" value="<%=Pagina.getParamStr(request, "primeiroIettClicado")%>">
	<input type="hidden" name="primeiroAriClicado" value="<%=Pagina.getParamStr(request, "primeiroAriClicado")%>">
	<input type="hidden" name="codIettRelacao" value="<%=Pagina.getParamStr(request, "codIettRelacao")%>">
	<input type="hidden" name="itemDoNivelClicado" value="<%=Pagina.getParamStr(request, "codIettRelacao")%>">
	<input type="hidden" name="codigosItem" value="<%=Pagina.getParamStr(request, "codigosItem")%>">
	<input type="hidden" name="codEttSelecionado" value="<%=codEttSelecionado%>">
	<!-- Campo necessario para guardar a forma de visualização escolhida no filtro -->
	<input type="hidden" name="hidFormaVisualizacaoEscolhida" value='<%=Pagina.getParamStr(request, "hidFormaVisualizacaoEscolhida")%>'>
	
	
	<!-- Campo necessário para botões de Avança/Retrocede -->
	<input type="hidden" name="codArisControle" value="<%=Pagina.getParamStr(request, "codArisControle")%>">
	<input type="hidden" name="codArisControleVisualizacao" value="<%=Pagina.getParamStr(request, "codArisControleVisualizacao")%>">
	
	<!-- Campos para manter a seleção em Posição Geral -->
	
	<input type="hidden" name="hidAcao" value="">
	
	<input type="hidden" name="codRegd" value="">
	
	<input type="hidden" name="clicouAba" value="<%= Pagina.getParamStr(request, "clicouAba")%>">
	<input type="hidden" name="tipoPadraoExibicaoAba" value="<%= Pagina.getParamStr(request, "tipoPadraoExibicaoAba")%>">

				<%
	List etapas = new ArrayList();
	List estruturaEtapas = estruturaDao.getEstruturasEtapas(acompReferenciaItem.getItemEstruturaIett().getEstruturaEtt());

	/* lista de colunas de uma estrutura */
	List lColunas = new ArrayList();
	EstruturaEtt estruturaAtual = new EstruturaEtt();
%>
				<br>
				<table id="abasestrutura" border="0" cellpadding="0" cellspacing="0"
					width="100%">
					<tr>
						<td>
							<%	
	
	boolean isPrimeiro = true;
	Iterator itEttEtapas = estruturaEtapas.iterator();
	boolean exibirNomeEtapa = (estruturaEtapas.size() > 1); //Se tiver mais de 1 etapa, exibir nome acima a listagem
	
	
	// Testa se não existem estapas cadastradas. 
	if (!itEttEtapas.hasNext()) {
	
%>			<tr>
				<td class="subtitulo"
					colspan="">
					<b>Não há Etapas para este ciclo de acompanhamento</b>
				</td>
			</tr>
<%
	} 
	
	while(itEttEtapas.hasNext()){
		EstruturaEtt estruturaFilha = (EstruturaEtt) itEttEtapas.next();
		
		if("".equals(codEttSelecionado)){
			estruturaAtual = estruturaFilha;
			codEttSelecionado = estruturaAtual.getCodEtt().toString();
		}else {
			if(codEttSelecionado.equals(estruturaFilha.getCodEtt().toString())){
				estruturaAtual = estruturaFilha;
			}
		}

		/*
		O teste deve ser feito dentro deste while, pois é aqui dentro que é 
		setado o objeto estruturaAtual, que é usado após o while.
		*/
		if(exibirNomeEtapa) {
			String tipoAba = "";
			if (codEttSelecionado.equals(estruturaFilha.getCodEtt().toString()))
				tipoAba = "abaestruturahabilitada";
			else
				tipoAba = "abaestruturadesabilitada";
					
			%>
							<table class="<%=tipoAba%>"
								style="background-color: <%= estruturaFilha.getCodCor1Ett() %>; border-bottom: <%= estruturaFilha.getCodCor1Ett() %>;">
								<tr>
									<td nowrap>
										<% if ("abaestruturadesabilitada".equals(tipoAba))
									{ %>
										<a href="#"
											onclick="javascript:aoClicarTrocaAbaEstrutura(document.form, <%=estruturaFilha.getCodEtt()%>)">
											<%=estruturaFilha.getNomeEtt() %> </a>
										<%} else { 
										out.print(estruturaFilha.getNomeEtt()); 
									} %>
									</td>
								</tr>
							</table>
							<%
		}
	}
		
%>
						</td>
					</tr>

					<tr>
						<td>
							<%
				if(estruturaAtual.getCodEtt() != null){
					lColunas = estruturaDao.getAtributosAcessoEstrutura(estruturaAtual);	
				}
				
				if(lColunas != null && lColunas.size() > 0) {
					//etapas = itemEstruturaDao.getItensFilho(acompReferenciaItem.getItemEstruturaIett(), estruturaAtual, ((ObjetoEstrutura)lColunas.get(0)).iGetNomeOrdenarLista());
					etapas = itemEstruturaDao.getItensFilho(acompReferenciaItem.getItemEstruturaIett(), estruturaAtual, lColunas);
				}
				else {
					etapas = itemEstruturaDao.getItensFilho(acompReferenciaItem.getItemEstruturaIett(), estruturaAtual, "");
				}

%>
							<table border="0" cellpadding="0" cellspacing="0" width="100%">
								<thead>
									<tr class="linha_subtitulo_estrutura"
										bgcolor="<%=estruturaAtual.getCodCor1Ett()%>">
										<%
						/* desenha as colunas de uma estrutura */
						Iterator itColunas = lColunas.iterator();
						int numColuna = 0;
						while (itColunas.hasNext()){
							ObjetoEstrutura coluna = (ObjetoEstrutura) itColunas.next();
%>
										<td width="<%=coluna.iGetLargura()%>%">
											<u> <a href="#"
												onclick="this.blur(); return sortTable('corpo',  <%=numColuna%>, false);">
													<%numColuna++;%> <%=coluna.iGetLabel()%> </a> </u>
										</td>
										<%
						}
%>
									</tr>
									<tr>
										<td class="espacadorestrutura" colspan="<%=lColunas.size()%>">
											<img src="<%=_pathEcar%>/images/pixel.gif" alt="">
										</td>
									</tr>
								</thead>
								<tbody id="corpo">
									<%
					Iterator itEtapa = etapas.iterator();
					while(itEtapa.hasNext()){
						ItemEstruturaIett etapa = (ItemEstruturaIett) itEtapa.next();
						
						//if(etapa.getEstruturaEtt().equals(estruturaAtual)){
%>
									<tr class="linha_subtitulo2_estrutura"
										bgcolor="<%=estruturaAtual.getCodCor3Ett()%>">
										<%							itColunas = lColunas.iterator();
							while (itColunas.hasNext()){
								ObjetoEstrutura coluna = (ObjetoEstrutura) itColunas.next();
%>
										<td width="<%=coluna.iGetLargura()%>%">
											<font color="<%=estruturaAtual.getCodCor4Ett()%>"> <%
									if("nivelPlanejamento".equals(coluna.iGetNome())){
										String niveis = "";
								    	if(etapa.getItemEstruturaNivelIettns() != null && !etapa.getItemEstruturaNivelIettns().isEmpty()){
									    	Iterator itNiveis = etapa.getItemEstruturaNivelIettns().iterator();
									    	while(itNiveis.hasNext()){
									    		SisAtributoSatb nivel = (SisAtributoSatb) itNiveis.next();
									    		niveis += nivel.getDescricaoSatb() + "; ";
									    	}
									    	niveis = niveis.substring(0, niveis.lastIndexOf(";"));
								    	}
										out.println(niveis);
									}
									else{
										out.println(coluna.iGetValor(etapa));
									}
									%> </font>
										</td>
										<%					
							}
%>
									</tr>
									<%
						//}
					}
%>
								</tbody>
							</table>
						</td>
					</tr>
				</table>
			</form>

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
		</div>
	</body>
	<% /* Controla o estado do Menu (aberto ou fechado) */%>
	<%@ include file="/include/estadoMenu.jsp"%>
	<%@ include file="/include/mensagemAlert.jsp"%>
</html>