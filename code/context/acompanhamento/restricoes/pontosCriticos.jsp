
<jsp:directive.page import="ecar.pojo.EstruturaFuncaoEttf"/>
<jsp:directive.page import="ecar.dao.EstruturaFuncaoDao"/>
<jsp:directive.page import="ecar.pojo.Aba"/>
<jsp:directive.page import="ecar.dao.AbaDao"/>
<jsp:directive.page import="ecar.pojo.AcompRelatorioArel"/>
<jsp:directive.page import="ecar.pojo.TipoFuncAcompTpfa"/>
<jsp:directive.page import="ecar.dao.AcompRelatorioDao"/>
<jsp:directive.page import="ecar.pojo.SisGrupoAtributoSga"/>
<jsp:directive.page import="ecar.pojo.ConfigMailCfgm"/>
<jsp:directive.page import="ecar.dao.ConfigMailCfgmDAO"/>
<jsp:directive.page import="ecar.dao.FuncaoDao"/>
<jsp:directive.page import="ecar.pojo.ObjetoEstrutura"/>
<jsp:directive.page import="ecar.dao.EstruturaDao"/>
<jsp:directive.page import="ecar.pojo.PontoCriticoSisAtributoPtcSatb"/>
<jsp:directive.page import="ecar.taglib.util.Input"/>
<%@ include file="../../login/validaAcesso.jsp"%>
<%@ include file="../../frm_global.jsp"%>

<%@ taglib uri="/WEB-INF/ecar-util.tld" prefix="util"%>
<%@ taglib uri="/WEB-INF/ecar-combo.tld" prefix="combo"%>

<%@ page import="ecar.pojo.ApontamentoApt" %>
<%@ page import="ecar.pojo.AcompReferenciaAref"%>
<%@ page import="ecar.pojo.AcompReferenciaItemAri"%>
<%@ page import="ecar.pojo.ItemEstruturaIett"%>
<%@ page import="ecar.pojo.UsuarioUsu" %>

<%@ page import="ecar.dao.AcompRealFisicoDao"%>
<%@ page import="ecar.dao.AcompReferenciaDao"%>
<%@ page import="ecar.dao.AcompReferenciaItemDao"%>
<%@ page import="ecar.pojo.PontoCriticoPtc" %>
<%@ page import="ecar.dao.PontoCriticoDao" %> 
<%@ page import="ecar.dao.ItemEstrtIndResulDao" %>
<%@ page import="ecar.dao.ItemEstruturaDao"%>
<%@ page import="ecar.dao.TipoFuncAcompDao"%>

<%@	page import="ecar.api.facade.*" %>


<%@ page import="comum.util.Data"%>
<%@ page import="comum.util.Util"%>
<%@ page import="java.util.Calendar" %>
<%@ page import="java.util.Collection" %> 
<%@ page import="java.util.Collections" %>

<html lang="pt-br">
	<head>
		<%@ include file="../../include/meta.jsp"%>
		<%@ include file="/titulo.jsp"%>
		<link rel="stylesheet" href="<%=_pathEcar%>/css/style_<%=configuracaoCfg.getEstilo().getNome()%>.css" type="text/css">
		<script language="javascript" src="<%=_pathEcar%>/js/menu_retratil.js" type="text/javascript"></script>
		<script language="javascript" src="<%=_pathEcar%>/js/focoInicial.js" type="text/javascript"></script>
		<script language="javascript" src="<%=_pathEcar%>/js/popUp.js" type="text/javascript"></script>
		<script language="javascript" src="<%=_pathEcar%>/js/destaqueLinha.js" type="text/javascript"></script>
		<script language="javascript" src="<%=_pathEcar%>/js/frmPadraoRegAcompanhamento.js" type="text/javascript"></script>
		<script language="javascript" src="<%=_pathEcar%>/js/tableSort.js"></script>
		<%
		ConfigMailCfgmDAO configMailCfgmDAO = new ConfigMailCfgmDAO();
		ConfigMailCfgm configMailCfgm = (ConfigMailCfgm) configMailCfgmDAO.buscar(ConfigMailCfgm.class, Dominios.CFG_MAIL_MANUTENCAO_PONTO_CRITICO);
		
		String codArisControleV = Pagina.getParamStr(request, "codArisControleVisualizacao");
		boolean telaVisualizacao = Pagina.getParamStr(request, "tela").equals("V");
		
		%>
		<script type="text/javascript">
		<% if (telaVisualizacao) { %>

			function reload(){
				document.form.codArisControleVisualizacao.value = "<%=codArisControleV%>";
				document.forms[0].action = "indicadoresResultado.jsp?codArisControleVisualizacao=<%=codArisControleV%>";
				document.forms[0].submit();
			}
			function trocaAba(link, codAri){
				document.forms[0].codAri.value = codAri;
				document.form.codArisControleVisualizacao.value = "<%=codArisControleV%>";
				document.forms[0].action = link + "&codArisControleVisualizacao=<%=codArisControleV%>";
				document.forms[0].clicouAba.value = "S";
				document.forms[0].submit();
			}


			function trocaAbaImpressao(link){
				document.form.codArisControleVisualizacao.value = "<%=codArisControleV%>";
				document.forms[0].action = link;
				document.forms[0].clicouAba.value = "S";
				document.forms[0].submit();
			}
			
			function trocarAba(nomeAba) {
				document.form.codArisControleVisualizacao.value = "<%=codArisControleV%>";
				document.forms[0].clicouAba.value = "S";
				document.forms[0].action = nomeAba;
				document.forms[0].submit();
			}
			function recarregar(){
				document.form.codArisControleVisualizacao.value = "<%=codArisControleV%>";
				document.forms[0].action = "pontosCriticos.jsp";
				document.forms[0].submit();
			}
			
			function aoClicarEditar(form, cod){
				document.form.codArisControleVisualizacao.value = "<%=codArisControleV%>";
				document.form.codPtc.value = cod;
				document.form.cod.value = cod;
				document.form.action = "frm_con.jsp";
				document.form.submit();
			}


<%} else {%>
			function aoClicarBtn2(form){
				if(validarExclusao(form, "excluirPontoCritico")){
					if(!confirm("Confirma a exclusão?")){
						return(false);
					}
					if('<%=configMailCfgm.getAtivoCfgm()%>' == 'S') {
						if(document.form.obrigatorio.value == 'S' || (document.form.obrigatorio.value == 'N' && confirm('<%=_msg.getMensagem("acompanhamento.cadastroItens.pontosCriticos.exclusao.autorizaEnviaEmail")%>') == true )) {
							document.form.autorizarMail.value = 'S';
						} 
					}
					form.hidAcao.value = "excluir";
					form.action = "ctrl.jsp";
					form.submit();
				}	
			}

			function aoClicarConsultar(form, cod){
				form.codPtc.value = cod;
				form.cod.value = cod;
				document.form.action = "frm_con.jsp";
				document.form.submit();
			} 

			function aoClicarConsultarApontamento(form, cod, codIett){
				form.codPtc.value = cod;
				form.codIett.value = codIett;
				document.form.action = "../restricoes/apontamentos/lista.jsp";
				document.form.submit();
			} 
						
			
			function aoClicarEditar(form, cod){
				form.codPtc.value = cod;
				form.cod.value = cod;
				document.form.action = "frm_con.jsp";
				document.form.submit();
			}
			
			function trocarAba(nomeAba) {
				document.forms[0].action = nomeAba;
				document.forms[0].clicouAba.value = "S";
				document.forms[0].submit();
			}
			function recarregar(){
				document.forms[0].action = "pontosCriticos.jsp";
				document.forms[0].submit();
			}
<%}%>
		</script>

				
		<script type="text/javascript">
			
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
		UsuarioUsu usuario = ((ecar.login.SegurancaECAR)session.getAttribute("seguranca")).getUsuario(); 
		AcompRealFisicoDao acompRealFisicoDao = new AcompRealFisicoDao(request);
		AcompReferenciaDao acompReferenciaDao = new AcompReferenciaDao(request);
		AcompReferenciaItemDao ariDao = new AcompReferenciaItemDao(request);
		//ConfiguracaoDao configuracaoDao = new ConfiguracaoDao(request);
		ItemEstrtIndResulDao indResultDao = new ItemEstrtIndResulDao(request);
		ItemEstruturaDao itemEstruturaDao = new ItemEstruturaDao(request);
		TipoFuncAcompDao tipoFuncAcompDao = new TipoFuncAcompDao(request);
		AcompReferenciaItemDao acompReferenciaItemDao = new AcompReferenciaItemDao(request);
	
		AcompReferenciaAref arefSelecionado = null;
		ItemEstruturaIett itemEstrutura = null;
		
		//pega os atributos do request
		String status = Pagina.getParamStr(request, "relatorio");
		String itemDoNivelClicado = Pagina.getParamStr(request, "itemDoNivelClicado");
		String codTipoAcompanhamento = Pagina.getParamStr(request, "codTipoAcompanhamento");
		String mesReferencia = Pagina.getParamStr(request, "referencia_hidden");
		String niveisPlanejamento = Pagina.getParamStr(request, "niveisPlanejamento");
		String primeiroIettClicado = Pagina.getParamStr(request, "primeiroIettClicado");
		
		
		AcompReferenciaItemAri ari = null;
		
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
			if (telaVisualizacao && primeiroIettClicado!=null) {
				itemEstrutura = (ItemEstruturaIett)itemEstruturaDao.buscar(ItemEstruturaIett.class, Long.valueOf(primeiroIettClicado));
			} else if (ari != null) {
				itemEstrutura = ari.getItemEstruturaIett();
			} else {
				itemEstrutura = (ItemEstruturaIett)itemEstruturaDao.buscar(ItemEstruturaIett.class, Long.valueOf(itemDoNivelClicado));
			}
		
			// se tiver codAri
			if(ari == null && !strAri.equals("")) { 
				ari = (AcompReferenciaItemAri) ariDao.buscar(AcompReferenciaItemAri.class, Long.valueOf(strAri));
			} else if(ari == null) {
			// se nao tiver codAri
				ari = ariDao.getAcompReferenciaItemByItemEstruturaIett(arefSelecionado,itemEstrutura);
			}
		} else {
			if(ari != null)
				itemEstrutura = (ItemEstruturaIett) itemEstruturaDao.buscar(ItemEstruturaIett.class, ari.getItemEstruturaIett().getCodIett());
		}
	
		//Pega a aba
		AbaDao abaD = new AbaDao(request);
		Aba aba = new Aba();
		aba.setNomeAba("PONTOS_CRITICOS");
		aba = (Aba)(abaD.pesquisar(aba, new String[]{"codAba", "asc"}).get(0));		
		
		ValidaPermissao validaPermissao = new ValidaPermissao();
		
		//Verifica permissão para exibição do botão adicionar.
		EstruturaFuncaoEttf estruturaFuncao = new EstruturaFuncaoEttf();
		EstruturaFuncaoDao estruturaFuncaoDao = new EstruturaFuncaoDao(request);
		estruturaFuncao = (EstruturaFuncaoEttf) estruturaFuncaoDao.getLabelFuncao(itemEstrutura.getEstruturaEtt(), new FuncaoDao(request).getFuncaoPontosCriticos().getCodFun());
		Boolean permissaoAlterar = validaPermissao.permissaoAlterarItem(itemEstrutura, seguranca.getUsuario(), seguranca.getGruposAcesso(), estruturaFuncao);
		
		
      	/*  
      	 * Verifica se o usuário é função de acompanhamento com permissão de 
      	 *  de editar os relatórios (parecer)
      	 */ 
      	 
		/*if (permissaoAlterar.booleanValue()==true  ) {
			AcompRelatorioDao arelDao = new AcompRelatorioDao(request);
			permissaoAlterar = new Boolean(  arelDao.emiteRelatorio(seguranca.getUsuario(), ari));
	   	} */
		
		Calendar dataAtual = Calendar.getInstance();
		dataAtual.clear(Calendar.HOUR);
	    dataAtual.clear(Calendar.HOUR_OF_DAY);
	    dataAtual.clear(Calendar.MINUTE);
	    dataAtual.clear(Calendar.SECOND);
	    dataAtual.clear(Calendar.MILLISECOND);
	    dataAtual.clear(Calendar.AM_PM);
		
		
		/* uma coluna de uma estrutura */
		ObjetoEstrutura coluna = null;
		EstruturaDao estruturaDao = new EstruturaDao(request);
		List lColunas  = null;
		if(estruturaFuncao != null){
			lColunas  = estruturaDao.getAtributosAcessoEstrutura(estruturaFuncao.getEstruturaEtt(), estruturaFuncao.getFuncaoFun());
		}
		Iterator itColunas = null;	
		String strCheckBox = "<td width=\"1%\"><input type=\"checkbox\" class=\"form_check_radio\" name=\"todosPontosCriticos\" onclick=\"javascript:selectAll(document.form, 'todosPontosCriticos', 'excluirPontoCritico');\"></td>";
		String strColunaVazia = "<td width=\"1%\"> &nbsp;</td> <!-- Coluna para colocar icone para listagem -->";
		int numTabelas = 1;
		Boolean abaPontoCriticoDeAcompanhamento = Boolean.TRUE;
		String tipoSelecao = Pagina.getParamStr(request, "tipoSelecao");
		PontoCriticoDao pontoCriticoDao = new PontoCriticoDao(request);
		String funcaoSelecionada = "PONTOS_CRITICOS";
		String periodo = Pagina.getParamStr(request, "periodo");
		
		if (telaVisualizacao ){ 
			AcompReferenciaItemAri acompReferenciaItem = ari;
		%>
			<%@ include file="../form_visualizar.jsp" %>		
		<% 
		} else { // provém de Registro, fazer o tratamento de registro
			arefSelecionado = ari.getAcompReferenciaAref();
		%>
			<%@ include  file="../form_registro.jsp" %>
		<% } %>


			<form name="form" id="form" method="post" action="">
				
				<input type="hidden" name="codigosItem" value="<%=Pagina.getParamStr(request, "codigosItem")%>">
				<input type="hidden" name="mesReferencia" value="<%=Pagina.getParamStr(request, "mesReferencia")%>">
				<input type="hidden" name="codTipoAcompanhamento" value="<%=codTipoAcompanhamento%>">
				<input type="hidden" name="niveisPlanejamento" value="<%=niveisPlanejamento%>">
				<input type="hidden" name="referencia_hidden" value="<%=mesReferencia%>">
				<input type="hidden" name="itemDoNivelClicado" value="<%=itemDoNivelClicado%>">
				<input type="hidden" name="codAri" value="<%=Pagina.getParamStr(request, "codAri")%>">
				<input type="hidden" name="primeiroIettClicado" value="<%=Pagina.getParamStr(request, "primeiroIettClicado")%>">
				<input type="hidden" name="primeiroAriClicado" value="<%=Pagina.getParamStr(request, "primeiroAriClicado")%>">
				<!-- Campo necessário para botões de Avança/Retrocede -->
				<input type="hidden" name="codArisControle" value="<%=Pagina.getParamStr(request, "codArisControle")%>">
				<input type="hidden" name="codArisControleVisualizacao" value="<%=Pagina.getParamStr(request, "codArisControleVisualizacao")%>">
				<input type="hidden" name="hidAcao"       value="">
				<input type="hidden" name="autorizarMail" value="">
				<input type="hidden" name="cod" value="">
				<input type="hidden" name="obrigatorio" value="<%=configMailCfgm.getIndEnvioObrigatorio()%>">
				<input type="hidden" name="codIett" value = "<%= Pagina.getParamStr(request,"codIett")%>" >
				<input type="hidden" name="codPtc" value = "<%= Pagina.getParamStr(request,"codPtc")%>" >
				<input type="hidden" name="tela" value="<%=Pagina.getParamStr(request, "tela")%>">	
				<input type="hidden" name="periodo" value="<%=Pagina.getParamStr(request, "periodo")%>">	
				<!-- Campo necessario para guardar a forma de visualização escolhida no filtro -->
				<input type="hidden" name="hidFormaVisualizacaoEscolhida" value='<%=Pagina.getParamStr(request, "hidFormaVisualizacaoEscolhida")%>'>			
				<input type="hidden" name="clicouAba" value="<%= Pagina.getParamStr(request, "clicouAba")%>">
				<input type="hidden" name="tipoPadraoExibicaoAba" value="<%= Pagina.getParamStr(request, "tipoPadraoExibicaoAba")%>">

<%

	if(estruturaFuncao != null){

%>

				<!-- começa a exibir informações de pontos críticos -->


				
				<table class="barrabotoes" cellpadding="0" cellspacing="0">
					<tr class="linha_titulo"> 
						<td align="left" style="width: 50%">
							<b> <%=abaD.getLabelAbaEstrutura(aba, ari.getItemEstruturaIett().getEstruturaEtt()) %> </b>
						</td>
						
				<%	if( permissaoAlterar.booleanValue() && !telaVisualizacao && "S".equals(itemEstrutura.getEstruturaEtt().getIndAtivoEtt()) ){%>
					<td class="barrabotoes" align="right" width= "50%" style="text-align: right;">
						<input type="button" name="btnAdicionar" value="Adicionar" class="botao" onclick="javascript:aoClicarIncluir(form)">
						<!-- input type="button" name="btnExcluir"   value="Excluir"   class="botao" onclick="javascript:aoClicarExcluir(form,'excluir')"-->
				<%  } %>

				<%	
				
				//verifica se é permitido remover a restrição ou ponto crítico. Mantis 0011103
				boolean permiteExcluir = validaPermissao.permiteExcluirPontoCritico(itemEstrutura, estruturaFuncao, seguranca);

				if(!telaVisualizacao && "S".equals(itemEstrutura.getEstruturaEtt().getIndAtivoEtt()) && permiteExcluir){%>
						<input type="button" name="btnExcluir"   value="Excluir"   class="botao" onclick="javascript:aoClicarBtn2(document.form)">
				<%}%>
					</td>
					</tr>
				</table>
		
				<table name="form" class="form" width="100%">
				<!-- tr>
					<td>Selecione uma das opções abaixo:</td>
				</tr-->
				<tr> 
					<td colspan="2">
						<%
						String paramPesquisa = Pagina.getParamStr(request, "tipoSelecao");
						if("".equals(paramPesquisa))
							paramPesquisa = "T";
						%>
						<input type="radio" class="form_check_radio" name="tipoSelecao" value="T" onclick="recarregar()" <%if("T".equals(paramPesquisa)){ out.println("checked");}%>> Todos
						<input type="radio" class="form_check_radio" name="tipoSelecao" value="N" onclick="recarregar()" <%if("N".equals(paramPesquisa)){ out.println("checked");}%>> Não Solucionados
						<input type="radio" class="form_check_radio" name="tipoSelecao" value="S" onclick="recarregar()" <%if("S".equals(paramPesquisa)){ out.println("checked");}%>> Solucionados								
					</td>
				</tr>
				</table>
				<table width="100%" border="0" cellpadding="0" cellspacing="0">
	<tr>
		<td class="espacador" colspan="<%=lColunas.size() + 3%>">
			<img src="<%=request.getContextPath()%>/images/pixel.gif">
		</td>
	</tr>
	<tr class="linha_subtitulo">
		<%
		/* desenha as colunas de uma estrutura */
		itColunas = lColunas.iterator();
		int numColuna = 2;
		while (itColunas.hasNext()){
			coluna = (ObjetoEstrutura) itColunas.next();
		%>			
			<%=strCheckBox%>
			<%=strColunaVazia%>
			<td width="<%=coluna.iGetLargura()%>">
			<a href="#" onclick="this.blur(); return sortTable('corpo<%=numTabelas%>', <%=numColuna%>, false);">											
			<%numColuna++;%>
			<%=coluna.iGetLabel()%>
			</a>
			</td>
		<%
			strColunaVazia = strCheckBox = "";
		}
		%>
	</tr>
	<tr>
		<td class="espacador" colspan="<%=lColunas.size() + 3%>">
			<img src="<%=request.getContextPath()%>/images/pixel.gif">
		</td>
	</tr>
	<tbody id="corpo<%=numTabelas%>" >
	<%
	Collection pontosAtivos = Collections.EMPTY_LIST;
	//Verifica qual opção está marcada na tela
	if(abaPontoCriticoDeAcompanhamento.booleanValue() ){
		if( "".equals(tipoSelecao) || "T".equals(tipoSelecao) )
			pontosAtivos  = pontoCriticoDao.getPontosCriticosOrdenadoDataLimite(itemEstrutura); 
		else{
			if("S".equals(tipoSelecao))
				pontosAtivos  = pontoCriticoDao.getPontosCriticosSolucionadosOrdenadoDataLimite(itemEstrutura);
			if("N".equals(tipoSelecao))
				pontosAtivos  = pontoCriticoDao.getPontosCriticosNaoSolucionadosOrdenadoDataLimite(itemEstrutura);
		}  	
  	// Percorre a lista de Pontos Criticos de ItemEstrutura e imprime na tela
	} else  {
   		//pontosAtivos = pontoCriticoDao.getAtivos(itemEstrutura);
   		pontosAtivos = pontoCriticoDao.getPontosCriticosOrdenadoDataLimite(itemEstrutura);
    }
    
    if (pontosAtivos != null){
    	Iterator itPontosCriticos = pontosAtivos.iterator();
    	while (itPontosCriticos.hasNext()){
    	
    		PontoCriticoPtc pontoCriticoPtc = (PontoCriticoPtc) itPontosCriticos.next();
    		
    		if (pontoCriticoPtc.getIndExcluidoPtc() == null || !pontoCriticoPtc.getIndExcluidoPtc().equals("S")){
    			%><tr>
    				<td nowrap="nowrap" width="1%" valign="top">
						<input type="checkbox" class="form_check_radio" name="excluirPontoCritico" value="<%=pontoCriticoPtc.getCodPtc()%>">
					</td>
					<td nowrap="nowrap" width="1%" valign="top">
						<a href="javascript:aoClicarEditar(document.form,<%=pontoCriticoPtc.getCodPtc()%>)">
						<img border="0" src="<%=request.getContextPath()%>/images/icon_alterar.png" alt="Alterar"></a>&nbsp;
					</td>
				<%
    			itColunas = lColunas.iterator();
    			
				while (itColunas.hasNext()){
					%><td valign="top" align="left" width="<%=coluna.iGetLargura()%>%"><%
					coluna = (ObjetoEstrutura) itColunas.next();
					
					String informacaoAtbdem = "";
					
					if (coluna.iGetGrupoAtributosLivres() != null)  {
					
						Iterator itPontoCriticoSisAtributoPtcSatbs = pontoCriticoPtc.getPontoCriticoSisAtributoPtcSatbs().iterator();
						
						while (itPontoCriticoSisAtributoPtcSatbs.hasNext()) {
						
							PontoCriticoSisAtributoPtcSatb pontoCriticoSisAtributoPtcSatb = (PontoCriticoSisAtributoPtcSatb) itPontoCriticoSisAtributoPtcSatbs.next();
							
							if (pontoCriticoSisAtributoPtcSatb.getSisAtributoSatb().getSisGrupoAtributoSga().equals(coluna.iGetGrupoAtributosLivres())){
							
								if (coluna.iGetGrupoAtributosLivres().getSisTipoExibicGrupoSteg().getCodSteg().equals(new Long(Input.TEXT)) ||
								 	coluna.iGetGrupoAtributosLivres().getSisTipoExibicGrupoSteg().getCodSteg().equals(new Long(Input.TEXTAREA)) ||
								 	coluna.iGetGrupoAtributosLivres().getSisTipoExibicGrupoSteg().getCodSteg().equals(new Long(Input.MULTITEXTO)) ||
								 	coluna.iGetGrupoAtributosLivres().getSisTipoExibicGrupoSteg().getCodSteg().equals(new Long(Input.VALIDACAO)) ) {
								 
									informacaoAtbdem = informacaoAtbdem + pontoCriticoSisAtributoPtcSatb.getInformacao() +  "; ";
								
								} else if (!coluna.iGetGrupoAtributosLivres().getSisTipoExibicGrupoSteg().getCodSteg().equals(new Long(Input.IMAGEM))) {
									//se for do tipo imagem não faz nada, deixa em branco.
									informacaoAtbdem = informacaoAtbdem + pontoCriticoSisAtributoPtcSatb.getSisAtributoSatb().getDescricaoSatb() +  "; ";
								}
							}
						}
						if (informacaoAtbdem.length() > 0){
							informacaoAtbdem = informacaoAtbdem.substring(0, informacaoAtbdem.length() - 2); 
						}
					
						out.println("<a href=\"javascript:aoClicarConsultarApontamento(document.form,"+ pontoCriticoPtc.getCodPtc()+"," + itemEstrutura.getCodIett()+")\">" +
								informacaoAtbdem + "</a>");
						//out.println(informacaoAtbdem);
					} else {
						if (coluna.iGetNome().equals("pontoCriticoCorPtccores")){
							String corRelogio = "Branco";
    						String descRelogio = "";
    						String[] relogioAtual = pontoCriticoDao.getRelogioNaData(pontoCriticoPtc, Data.getDataAtual());
							corRelogio = relogioAtual[0];
							descRelogio = relogioAtual[1];
							%>
								<img src="<%=request.getContextPath()%>/images/pc<%=corRelogio%>1.png" title="<%=descRelogio%>">
							<%
						} else if (coluna.iGetNome().equals("acompRelatorioArel")){
							AcompRelatorioArel arel = pontoCriticoPtc.getAcompRelatorioArel();
							if (arel != null && arel.getTipoFuncAcompTpfa() != null) {
								out.println(arel.getTipoFuncAcompTpfa().getLabelTpfa());
							} else {
								out.println("");
							}
						} else {
							out.println("<a href=\"javascript:aoClicarConsultarApontamento(document.form,"+ pontoCriticoPtc.getCodPtc()+"," + itemEstrutura.getCodIett()+")\">" +
									coluna.iGetValor(pontoCriticoPtc) + "</a>");
						}
					}
					%>
				</td>
				<%						
				}
		%></tr><%
			}		
   		}
   	}
	%>
	</tbody>
</table>

<!-- termina de exibir as informações de pontos críticos -->
</form>

</div>
<%

	} else {

%>
<br><br>
				<table name="form" width="100%">
				<!-- tr>
					<td>Selecione uma das opções abaixo:</td>
				</tr-->
				<tr> 
					<td colspan="2" align="center">
						<b>A função <%= new FuncaoDao(request).getFuncaoPontosCriticos().getLabelPadraoFun()%> não está configurada para a estrutura <%=itemEstrutura.getEstruturaEtt().getNomeEtt() %>.</b>								
					</td>
				</tr>
				</table>

<%
	
	}

%>
<%
} catch (Exception e){
%>
	<%@ include file="/excecao.jsp"%>
<%
}
%>
	</body>
	<%@ include file="/include/estadoMenu.jsp"%>
	<%@ include file="/include/mensagemAlert.jsp" %>
</html>