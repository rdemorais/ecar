	
<%@ include file="../../login/validaAcesso.jsp"%>
<%@ include file="../../frm_global.jsp"%>

<%@ taglib uri="/WEB-INF/ecar-util.tld" prefix="util"%>
<%@ taglib uri="/WEB-INF/ecar-combo.tld" prefix="combo"%>

<%@ page import="ecar.pojo.AcompReferenciaAref"%>
<%@ page import="ecar.pojo.AcompReferenciaItemAri"%>
<%@ page import="ecar.pojo.EstruturaEtt" %>
<%@ page import="ecar.pojo.ItemEstruturaIett"%>
<%@ page import="ecar.pojo.UsuarioUsu" %>
<%@ page import="ecar.pojo.SisAtributoSatb"%>
<%@ page import="ecar.pojo.ObjetoEstrutura"%>

<%@ page import="ecar.dao.AcompRealFisicoDao"%>
<%@ page import="ecar.dao.AcompReferenciaDao"%>
<%@ page import="ecar.dao.AcompReferenciaItemDao"%>
<%@ page import="ecar.dao.EstruturaDao" %>
<%@ page import="ecar.dao.ItemEstrtIndResulDao" %>
<%@ page import="ecar.dao.ItemEstruturaDao"%>
<%@ page import="ecar.dao.TipoFuncAcompDao"%>

<%@ page import="comum.util.Data"%>
<%@ page import="comum.util.Util"%>
<%@ page import="java.util.Calendar" %>

<html lang="pt-br">
	<head>
		<%@ include file="../../include/meta.jsp"%>
		<%@ include file="/titulo.jsp"%>
		<link rel="stylesheet" href="<%=_pathEcar%>/css/style_<%=configuracaoCfg.getEstilo().getNome()%>.css" type="text/css">
		<script language="javascript" src="<%=_pathEcar%>/js/menu_retratil.js"
			type="text/javascript"></script>
		<script language="javascript" src="<%=_pathEcar%>/js/focoInicial.js"
			type="text/javascript"></script>
		<script language="javascript" src="<%=_pathEcar%>/js/popUp.js"
			type="text/javascript"></script>
		<script language="javascript" src="<%=_pathEcar%>/js/destaqueLinha.js"
			type="text/javascript"></script>
		<script language="javascript" src="<%=_pathEcar%>/js/tableSort.js"
			type="text/javascript"></script>	
		<script type="text/javascript">
			function trocarAba(nomeAba) {
				document.forms[0].action = nomeAba;
				document.forms[0].clicouAba.value = "S";
				document.forms[0].submit();
			}
			<%--function voltarTelaAnterior(tela) {
				document.forms[0].action = tela;
				document.forms[0].submit();
			}--%>
			function recarregar(){
				document.forms[0].action = "pontosCriticos.jsp";
				document.forms[0].submit();
			}
			function recarregar(form){
				form.action = "etapas.jsp";
				form.submit();
			}
			function aoClicarTrocaAbaEstrutura(form, codEtt){
				form.codEttSelecionado.value = codEtt;
				form.action = "etapas.jsp";
				document.forms[0].clicouAba.value = "S";
				form.submit();
			}
		</script>
	</head>

	<%@ include file="../../cabecalho.jsp"%>
	<%@ include file="../../divmenu.jsp"%>

	<body>
<%
try {
%>
		<div id="conteudo">
	<%
	
		AcompRealFisicoDao acompRealFisicoDao = new AcompRealFisicoDao(request);
		AcompReferenciaDao acompReferenciaDao = new AcompReferenciaDao(request);
		AcompReferenciaItemDao ariDao = new AcompReferenciaItemDao(request);
		//ConfiguracaoDao configuracaoDao = new ConfiguracaoDao(request);
		EstruturaDao estruturaDao = new EstruturaDao(request);
		ItemEstruturaDao itemEstruturaDao = new ItemEstruturaDao(request);
		TipoFuncAcompDao tipoFuncAcompDao = new TipoFuncAcompDao(request);
		AcompReferenciaItemDao acompReferenciaItemDao = new AcompReferenciaItemDao(request);
	
	
		AcompReferenciaAref arefSelecionado = null;
		ItemEstruturaIett itemEstrutura = null;
		AcompReferenciaItemAri ari = null;
		
		UsuarioUsu usuario = ((ecar.login.SegurancaECAR)session.getAttribute("seguranca")).getUsuario(); 
		
		//pega os atributos do request
		String status = Pagina.getParamStr(request, "relatorio");
		String itemDoNivelClicado = Pagina.getParamStr(request, "itemDoNivelClicado");
		String codTipoAcompanhamento = Pagina.getParamStr(request, "codTipoAcompanhamento");
		String mesReferencia = Pagina.getParamStr(request, "referencia_hidden");
		String codEttSelecionado = Pagina.getParamStr(request, "codEttSelecionado");
		String niveisPlanejamento = Pagina.getParamStr(request, "niveisPlanejamento");
		
		// Pra nao dar erro quando perder codTipoAcompanhamento
		// descobre pelo Ari
		String strAri = Pagina.getParamStr(request, "codAri");
		ari = (AcompReferenciaItemAri) new AcompReferenciaItemDao(request).buscar(
						AcompReferenciaItemAri.class, Long.valueOf(strAri));
		arefSelecionado = ari.getAcompReferenciaAref();
		if(codTipoAcompanhamento.equals("")) {
			codTipoAcompanhamento = arefSelecionado.getTipoAcompanhamentoTa().getCodTa().toString();
		} 
		
		
		List acompanhamentos = acompReferenciaDao.getListAcompReferenciaByTipoAcompanhamento(new Long(codTipoAcompanhamento));
	
		Iterator acompanhamentosIt = acompanhamentos.iterator();
		while(acompanhamentosIt.hasNext()) {
			arefSelecionado = (AcompReferenciaAref)acompanhamentosIt.next();
			if((arefSelecionado.getCodAref().toString()).equals(mesReferencia)) {
				break;
			}
		}
	
		if(arefSelecionado != null && !itemDoNivelClicado.trim().equals("") && itemDoNivelClicado != null ){
			itemEstrutura = (ItemEstruturaIett)itemEstruturaDao.buscar(ItemEstruturaIett.class, Long.valueOf(itemDoNivelClicado));
			ari = ariDao.getAcompReferenciaItemByItemEstruturaIett(arefSelecionado,itemEstrutura);
			// se a pesquisa nao resultar em nada 
			if(ari == null) {
				ari = (AcompReferenciaItemAri) new AcompReferenciaItemDao(request).buscar(
						AcompReferenciaItemAri.class, Long.valueOf(strAri));
			}
		} else {
			// Pra nao dar erro quando perder codTipoAcompanhamento
			// descobre pelo Ari
			if(ari != null)
				itemEstrutura = (ItemEstruturaIett) itemEstruturaDao.buscar(ItemEstruturaIett.class, ari.getItemEstruturaIett().getCodIett());
		}
	
		List tpfaOrdenadosPorEstrutura = tipoFuncAcompDao.getFuncaoAcompOrderByEstruturas();
		
		Calendar dataAtual = Calendar.getInstance();
		dataAtual.clear(Calendar.HOUR);
	    dataAtual.clear(Calendar.HOUR_OF_DAY);
	    dataAtual.clear(Calendar.MINUTE);
	    dataAtual.clear(Calendar.SECOND);
	    dataAtual.clear(Calendar.MILLISECOND);
	    dataAtual.clear(Calendar.AM_PM);
		
			
		String periodo = Pagina.getParamStr(request, "periodo");
		
		String funcaoSelecionada = "ETAPA";
 %>

	<%@ include  file="../form_registro.jsp" %>		
	
			<form name="form" id="form" method="post" action="">
				<input type="hidden" name="codTipoAcompanhamento" value="<%=codTipoAcompanhamento%>">
				<input type="hidden" name="periodo" value="<%=Pagina.getParamStr(request, "periodo")%>">
				<input type="hidden" name="niveisPlanejamento" value="<%=niveisPlanejamento%>">
				<input type="hidden" name="referencia_hidden" value="<%=mesReferencia%>">
				<input type="hidden" name="itemDoNivelClicado" value="<%=itemDoNivelClicado%>">
				<input type="hidden" name="codAri" value="<%=Pagina.getParamStr(request, "codAri")%>">
				<input type="hidden" name="primeiroIettClicado" value="<%=Pagina.getParamStr(request, "primeiroIettClicado")%>">
				<input type="hidden" name="primeiroAriClicado" value="<%=Pagina.getParamStr(request, "primeiroAriClicado")%>">
				<input type="hidden" name="codEttSelecionado" value="<%=codEttSelecionado%>">
				<!-- Campo necessário para botões de Avança/Retrocede -->
				<input type="hidden" name="codArisControle" value="<%=Pagina.getParamStr(request, "codArisControle")%>">
				<!-- Campo necessario para guardar a forma de visualização escolhida no filtro -->
				<input type="hidden" name="hidFormaVisualizacaoEscolhida" value='<%=Pagina.getParamStr(request, "hidFormaVisualizacaoEscolhida")%>'>	
				<input type="hidden" name="clicouAba" value="<%= Pagina.getParamStr(request, "clicouAba")%>">
				<input type="hidden" name="tipoPadraoExibicaoAba" value="<%= Pagina.getParamStr(request, "tipoPadraoExibicaoAba")%>">		
				
				<!-- começa a exibir informações das etapas -->
<%
	List etapas = new ArrayList();
	List estruturaEtapas = estruturaDao.getEstruturasEtapas(ari.getItemEstruturaIett().getEstruturaEtt());

	/* lista de colunas de uma estrutura */
	List lColunas =  new ArrayList();
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
		} else {
			if(codEttSelecionado.equals(estruturaFilha.getCodEtt().toString())){
				estruturaAtual = estruturaFilha;
			}
		}

		/** O teste deve ser feito dentro deste while, pois é aqui dentro que é 
		setado o objeto estruturaAtual, que é usado após o while. **/
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
										<% if ("abaestruturadesabilitada".equals(tipoAba)) { %>
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
				if(estruturaAtual.getCodEtt() != null)
					lColunas = estruturaDao.getAtributosAcessoEstrutura(estruturaAtual);
				
				if(lColunas != null && lColunas.size() > 0) {
					etapas = itemEstruturaDao.getItensFilho(ari.getItemEstruturaIett(), estruturaAtual, lColunas);
				} else {
					etapas = itemEstruturaDao.getItensFilho(ari.getItemEstruturaIett(), estruturaAtual, "");
				}
%>
							<table border="0" cellpadding="0" cellspacing="0" width="100%">
								<thead>
									<tr class="linha_subtitulo_estrutura" bgcolor="<%=estruturaAtual.getCodCor1Ett()%>">
<%
						/* desenha as colunas de uma estrutura */
						Iterator itColunas = lColunas.iterator();
						int numColuna = 0;
						while (itColunas.hasNext()){
							ObjetoEstrutura coluna = (ObjetoEstrutura) itColunas.next();
%>
										<td width="<%=coluna.iGetLargura()%>%">
											<u> <a href="#"
												onclick="return sortTable('corpo',  <%=numColuna%>, false);">
													<%numColuna++;%> <%=coluna.iGetLabel()%> </a> </u>
										</td>
<%						}
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
%>
									<tr class="linha_subtitulo2_estrutura" bgcolor="<%=estruturaAtual.getCodCor3Ett()%>">
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
									} else {
										out.println(coluna.iGetValor(etapa));
									}
%> 										</font>
										</td>
<%					
							}
%>
									</tr>
<%
					}
%>
								</tbody>
							</table>
						</td>
					</tr>
				</table>
				<!-- termina de exibir as informações das etapas -->
			</form>

		</div>
<%
} catch (Exception e){
%>
	<%@ include file="/excecao.jsp"%>
<%
}
%>
	</body>
	<%@ include file="/../../include/estadoMenu.jsp"%>
</html>