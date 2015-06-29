
<jsp:directive.page import="ecar.bean.NomeImgsNivelPlanejamentoBean"/><%@ include file="../login/validaAcesso.jsp"%> 
<%@ include file="../frm_global.jsp"%>

<%@ taglib uri="/WEB-INF/ecar-util.tld" prefix="util" %>
<%@ taglib uri="/WEB-INF/ecar-combo.tld" prefix="combo" %>

<%@ page import="ecar.bean.AtributoEstruturaListagemItens"%>
<%@ page import="ecar.pojo.AcompReferenciaAref" %>
<%@ page import="ecar.dao.AcompReferenciaDao" %>
<%@ page import="ecar.pojo.ItemEstruturaIett" %>  
<%@ page import="ecar.dao.ItemEstruturaDao" %> 
<%@ page import="ecar.pojo.UsuarioUsu" %>
<%@ page import="ecar.dao.TipoFuncAcompDao" %> 
<%@ page import="ecar.pojo.AcompReferenciaItemAri" %> 
<%@ page import="ecar.dao.AcompReferenciaItemDao" %> 
<%@ page import="ecar.pojo.AcompRelatorioArel" %> 
<%@ page import="ecar.dao.CorDao" %>
<%@ page import="ecar.dao.OrgaoDao" %>
<%@ page import="ecar.pojo.OrgaoOrg" %>
<%@ page import="ecar.dao.SisAtributoDao" %>
<%@ page import="ecar.pojo.SisAtributoSatb" %>
<%@ page import="ecar.dao.ItemEstrutMarcadorDao" %>
<%@ page import="ecar.pojo.ItemEstrutMarcadorIettm" %>
<%@ page import="ecar.pojo.StatusRelatorioSrl" %>
<%@ page import="ecar.pojo.UsuarioAtributoUsua" %>
<%@ page import="ecar.dao.SisGrupoAtributoDao" %>
<%@ page import="ecar.dao.ConfiguracaoDao" %>
<%@ page import="ecar.dao.AbaDao" %>

<%@ page import="comum.util.Pagina" %>
<%@ page import="comum.util.Data" %>
<%@ page import="comum.database.Dao" %>

<%@ page import="java.util.Calendar" %> 
<%@ page import="java.util.Iterator" %> 
<%@ page import="java.util.Collection" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.HashSet" %>
<%@ page import="java.util.Set" %>
<%@ page import="java.util.Collections" %>
<%@ page import="java.util.Map" %>
<%@ page import="java.util.Date" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.HashMap" %>
<%@ page import="ecar.dao.EstruturaDao" %>
<%@ page import="ecar.dao.UsuarioDao" %>
<%@ page import="ecar.dao.TipoAcompanhamentoDao" %>
<%@ page import="ecar.pojo.TipoAcompanhamentoTa" %>
<%@ page import="ecar.pojo.EstruturaEtt" %>
<%@ page import="ecar.pojo.Cor" %>
<%@ page import="ecar.pojo.TipoFuncAcompTpfa" %>

<%
Date dataInicio = Data.getDataAtual();
%>
 
<html lang="pt-br">
<head> 
<%@ include file="../include/meta.jsp"%>
<%@ include file="/titulo.jsp"%>
<link rel="stylesheet" href="<%=_pathEcar%>/css/style_<%=configuracaoCfg.getEstilo().getNome()%>.css" type="text/css">
<script language="javascript" src="../js/menu_retratil.js"></script>
<script language="javascript" src="../js/focoInicial.js"></script>
<script language="javascript" src="../js/popUp.js"></script>

<script>
function trocaAbaSemAri(link, codIettRelacao){
	document.form.codIettRelacao.value = codIettRelacao;
	document.form.itemDoNivelClicado.value = codIettRelacao;
	SituacaoDatas();
	document.form.action = link;
	document.form.submit();
}
function trocaAba(link, codAri){
	document.forms[0].codIettRelacao.value = '';
	document.forms[0].codAri.value = codAri;
	SituacaoDatas();
	document.forms[0].action = link;	
	document.forms[0].submit();
}
function trocaAbaImpressao(link){
    SituacaoDatas();
	document.forms[0].action = link;
	document.forms[0].submit();
}
function abrirPopUpInserirAnotacao(codItem){
	abreJanela("popUpFormAnotacao.jsp?codIett=" + codItem, 500, 300, "Inserir");
}
function abrirPopUpListaAnotacao(codItem){
	abreJanela("popUpListaAnotacao.jsp?codIett=" + codItem, 500, 300, "Listar");
}
function abrirPopUpGrafico(codOrgao){
	var parametros = "niveisPlanejamento=" + document.form.niveisPlanejamento.value;
	parametros += "&orgaoEscolhido=" + document.form.orgaoEscolhido.value;
	parametros += "&periodoEscolhido=" + document.form.periodoEscolhido.value;
	if(document.form.referencia_hidden.value == null || document.form.referencia_hidden.value == ""){
		parametros += "&referencia=" + document.form.mesReferencia.value;
	}
	else{
		parametros += "&referencia=" + document.form.referencia_hidden.value;
	}
	
	if(codOrgao > 0){
		var janela = abreJanela("popUpGrafico.jsp?codOrgao=" + codOrgao + "&" + parametros, 700, 550, "popupGrafico");
		janela.focus();
	} 
	else {
		var janela = abreJanela("popUpGrafico.jsp?" + parametros, 700, 550, "popupGrafico");
		janela.focus();
	}
	
}

function abrirDadosGerais(){
	document.form.action = "dadosGerais.jsp";
	document.form.submit();	
}

function reload(){
	document.form.primeiraChamada.value="N";
	if(document.form.periodo.value != ""){
		document.form.action = "relatorioOrgaos.jsp";
		document.form.submit();		
	}
}
function reload2(){
	document.form.primeiraChamada.value="N";
	if(document.form.periodo.value != ""){
		document.form.action = "relatorioOrgaos.jsp?relatorio=true";
		document.form.submit();		
	}
}
function abrirItem(form, iett, status){
	if(status == "true")
		form.action = "proximosItens.jsp?relatorio=" + status;
	else
		form.action = "proximosItens.jsp";
	
	form.itemDoNivelClicado.value = iett;
	form.submit();
}

function aoClicarConsultar(form, codigo, codIett){ 
	form.codAri.value = codigo;
	document.form.action = "avaliacoes.jsp?primeiroIettClicado=" + codIett + "&primeiroAriClicado=" + codigo;
	document.form.submit();
}
function abrirPopUpNivelPlanejamento(){

	var info = document.getElementById("semInformacaoNivelPlanejamento").value;
	if(info == ""){
		abreJanela("popUpNivelPlanejamento.jsp?opcaoSemInformacao=N", 500, 400, "Nivel");
	}
	else{
		abreJanela("popUpNivelPlanejamento.jsp?opcaoSemInformacao=" + info, 500, 400, "Nivel");
	}
}

function checkAll(idItem){
	var i = 0;
	var el = document.getElementsByTagName("INPUT");
   	var fim = el.length;
   	   	   	  		
   	if (idItem != null){
   		var idCombo = "ComboClicado" + idItem;    		
   		if(document.getElementById(idCombo).value == "")
   			document.getElementById(idCombo).value = "true";   	
   		else
  			document.getElementById(idCombo).value = "";
  			
   		while (i < fim ){
    		if (el.item(i).type  == "checkbox") {          
        		if (document.getElementById(idCombo).value == ""){        			
        			if (el.item(i).checked == true && el.item(i).id == idItem)
            			el.item(i).checked = false;            		
            	}
            	else{            		
            		if (el.item(i).checked == false && el.item(i).id == idItem)
            			el.item(i).checked = true;
            	}
        	}
        	i++;  
   		}   		
   }
   else{
   
   		if(document.getElementById('ComboClicado').value == "")
   			document.getElementById('ComboClicado').value = "true";   	
   		else
  			document.getElementById('ComboClicado').value = "";
  			
   		while (i < fim ){
    		if (el.item(i).type  == "checkbox") {          
        		if (document.getElementById('ComboClicado').value == ""){
        			if (el.item(i).checked == true)
            			el.item(i).checked = false;            		
            	}
            	else{
            		if (el.item(i).checked == false)
            			el.item(i).checked = true;
            	}
        	}
        	i++;  
   		}   
   }
}

function relatorio(){
	var selecionados = "";
	var primeiroCodAri = "";
	var i = 0;
	var el = document.getElementsByTagName("INPUT");
	var fim = el.length;
  
   	while (i < fim ){
    	if ((el.item(i).type  == "checkbox") && (el.item(i).value != "todos")) {          
        	if (el.item(i).checked == true){
         	  	selecionados = selecionados + el.item(i).value + ";";
         		if (primeiroCodAri == "")
        			primeiroCodAri = el.item(i).value;
        	}
        }        
        i++;  
   	}
   
	if (selecionados == "") {
    	alert("Não há ítens selecionados!");
   	}  	 
	else{
		document.form.codigosItem.value = selecionados;
    	document.form.action = "relatorioImpresso.jsp?codAri="+primeiroCodAri;
		document.form.submit();
	}
}

function SituacaoDatas(){
	var selecionados = "";
	var primeiroCodAri = "";
	var i = 0;
	var el = document.getElementsByTagName("INPUT");
	var fim = el.length;
  
   	//while (i < fim ){
   	while (i <= el.length - 1){
    	if (el.item(i).id.substr(0,5) == "item_") {
    		selecionados = selecionados + el.item(i).value + ";";
        	if (primeiroCodAri == "")
        		primeiroCodAri = el.item(i).value;        	
        }        
        i++;  
   	}
   
	if (selecionados != "") {
    	document.form.codigosItem.value = selecionados;
    	document.form.primeiroCodAri.value = primeiroCodAri;    	
	} else{
		document.form.codigosItem.value = "teste";
	}
	
}

</script>

</head>
<%
try {
	request.setAttribute("exibirAguarde", "true");
%>
<%@ include file="../cabecalho.jsp"%> 
<%@ include file="../divmenu.jsp"%> 
<%
	TipoAcompanhamentoDao taDao = new TipoAcompanhamentoDao(request);

	String status = Pagina.getParamStr(request, "relatorio");
	String flag = "";
	int contador = 0;
	String codTipoAcompanhamento = Pagina.getParamStr(request, "codTipoAcompanhamento");

	// controle para mostrar como default o primeiro tipo de acompanhamento das abas selecionado
	if("".equals(codTipoAcompanhamento)) {
		List listTa = taDao.getTipoAcompanhamentoAtivosComAcompanhamentoBySeqApresentacao();
		
		if(!listTa.isEmpty()) {
			codTipoAcompanhamento = ((TipoAcompanhamentoTa)listTa.get(0)).getCodTa().toString();
		}
	}

	TipoAcompanhamentoTa tipoAcompanhamento = (TipoAcompanhamentoTa) taDao.buscar(TipoAcompanhamentoTa.class, Long.valueOf(codTipoAcompanhamento));
%>
<body> 
<div id="conteudo">
<%
	if("".equals(codTipoAcompanhamento)) {
%>
	<table width="100%" border="0" cellpadding="0" cellspacing="0">	
		<tr class="linha_subtitulo" align="left">
			<td>
				<%=_msg.getMensagem("tipoAcompanhamento.ativo.comAref.nenhumRegistro")%>
			</td>
		</tr>
	</table>

<%
	} else {
		
%>
	<util:barraLinkTipoAcompanhamentoTag
		codTipoAcompanhamentoSelecionado="<%=codTipoAcompanhamento%>"
		chamarPagina="relatorioOrgaos.jsp"
	/> 
	<% 
		if(!"true".equals(status)) { %>
		<util:barraLinksRelatorioAcompanhamento
			funcaoSelecionada="relatorioOrgaos"
			links="N"
			caminho="<%=_pathEcar + "/relAcompanhamento/"%>"
			codTipoAcompanhamento="<%=codTipoAcompanhamento%>"
			gruposUsuario="<%=seguranca.getGruposAcesso() %>" 
		/> 
	<%
		}
		else {
	%>	
		<util:barraLinksRelatorioAcompanhamento
			funcaoSelecionada="relatorioOrgaos"
			links="N"
			caminho="<%=_pathEcar + "/relAcompanhamento/"%>"
			codTipoAcompanhamento="<%=codTipoAcompanhamento%>"
		/>
	<%
		}
	%>
	<br>
<%
	AcompReferenciaItemDao acompReferenciaItemDao = new AcompReferenciaItemDao(request);
	SisAtributoDao sisAtributoDao = new SisAtributoDao(request);
	ItemEstruturaDao itemDao = new ItemEstruturaDao(request);
	AcompReferenciaDao acompReferenciaDao = new AcompReferenciaDao(request);
	TipoFuncAcompDao tipoFuncAcompDao = new TipoFuncAcompDao(request);
	EstruturaDao estruturaDao = new EstruturaDao(request);
	UsuarioDao usuarioDao = new UsuarioDao(request);
	OrgaoDao orgaoDao = new OrgaoDao(request);
	ItemEstrutMarcadorDao marcadorDao = new ItemEstrutMarcadorDao(request);
	CorDao corDao = new CorDao(request);
	AbaDao abaDao = new AbaDao(request);
	//ConfiguracaoDao configuracaoDao = new ConfiguracaoDao(request);
	
	List tpfaOrdenadosPorEstrutura = tipoFuncAcompDao.getFuncaoAcompOrderByEstruturas();
	
	//String exigeLiberarAcompanhamento = configura.getIndLiberarAcompCfg();
	String exigeLiberarAcompanhamento = tipoAcompanhamento.getIndLiberarAcompTa();
	EstruturaEtt menorEttCfg = null;
	int menorNivel = -1;
	String itemDoNivelClicado = "";
	
	if(configura != null){
		//menorEttCfg = configura.getEstruturaEtt();
		menorEttCfg = tipoAcompanhamento.getEstruturaEtt();
		if(menorEttCfg != null){
			menorNivel = estruturaDao.getNivel(menorEttCfg);
		}
	}
		
	List acompanhamentos = acompReferenciaDao.getListAcompReferenciaByTipoAcompanhamentoOrdemOrgao(Long.valueOf(codTipoAcompanhamento));
	
	AcompReferenciaAref arefAtual = acompReferenciaDao.getAcompSelecionado(acompanhamentos);
	
	String strLabelOrgao = estruturaDao.getLabelPadraoOrgaoResponsavel();
	
	UsuarioUsu usuario = ((ecar.login.SegurancaECAR)session.getAttribute("seguranca")).getUsuario();
	
	List niveisUsuario = usuarioDao.getNiveisPlanejamentoUsuario(usuario);
	Iterator itNiveisUsu = niveisUsuario.iterator();
	StringBuffer strAux = new StringBuffer("");
	
	while(itNiveisUsu.hasNext()) {
		UsuarioAtributoUsua usuAtributo = (UsuarioAtributoUsua) itNiveisUsu.next();
		strAux.append(usuAtributo.getSisAtributoSatb().getCodSatb()); 
		strAux.append(":");
	}
	String strNiveisPlanejamentoUsuario = strAux.toString();
	String periodo = "";
	
	if(!"true".equals(status))
		periodo = Pagina.getParamStr(request, "periodo");
	else
		periodo = "1";
		
	if("".equals(periodo))
		periodo = "1";
	
	String mesReferencia = Pagina.getParamStr(request, "mesReferencia");
		
	String niveisPlanejamento = Pagina.getParamStr(request, "niveisPlanejamento");
	
	/* Verifica a exibição do filtro Nível Planejamento se não mostrar selecionar TODOS */
	if (abaDao.verificaNivelPlanejamento("P")) {
		if("".equals(niveisPlanejamento)) {
			niveisPlanejamento = strNiveisPlanejamentoUsuario;
		}
	} else {
		String strAux2 = "";
		List listNiveisPlanejamento = new SisGrupoAtributoDao().getAtributosOrdenados(configuracao.getSisGrupoAtributoSgaByCodSgaGrAtrNvPlan());
		Iterator it = listNiveisPlanejamento.iterator();
		while(it.hasNext()){
			SisAtributoSatb nivel = (SisAtributoSatb) it.next();
			
			strAux2 += nivel.getCodSatb() + ":";
		}
		if("".equals(niveisPlanejamento)){
			niveisPlanejamento = strAux2.toString();
		}
	}
	
	OrgaoOrg orgaoResponsavel = new OrgaoOrg();
	if(!"".equals(Pagina.getParamStr(request, "orgaoResponsavel"))){
		orgaoResponsavel = (OrgaoOrg) orgaoDao.buscar(OrgaoOrg.class, Long.valueOf( Pagina.getParamStr(request, "orgaoResponsavel")));
	}
	boolean comboOrgao = true;
	
	String orgaoEscolhido = (orgaoResponsavel.getCodOrg() != null) ? orgaoResponsavel.getSiglaOrg() : "Todos";
	String periodoEscolhido = periodo + (("1".equals(periodo)) ? " Ciclo" : " Ciclos");

	String semInformacaoNivelPlanejamento = Pagina.getParamStr(request, "semInformacaoNivelPlanejamento");
	%>	
	
	<form name="form" method="post">
	
		<input type="hidden" name="codTipoAcompanhamento" value="<%=codTipoAcompanhamento%>">
		<input type="hidden" name="codAri" value="">	
		<input type="hidden" name="niveisPlanejamento" value="<%=niveisPlanejamento%>">
		<input type="hidden" name="primeiraChamada" value="<%=Pagina.getParamStr(request, "primeiraChamada")%>">
		<input type="hidden" name="orgaoEscolhido" value="<%=orgaoEscolhido%>">
		<input type="hidden" name="periodoEscolhido" value="<%=periodoEscolhido%>">
		<input type="hidden" name="ComboClicado" id="ComboClicado" value="">
		<input type="hidden" name="referencia_hidden" value="<%=mesReferencia%>">
		<input type="hidden" name="itemDoNivelClicado" value="<%=itemDoNivelClicado%>">
		<input type="hidden" name="semInformacaoNivelPlanejamento" id="semInformacaoNivelPlanejamento" value="<%=semInformacaoNivelPlanejamento%>">
		<input type="hidden" name="codIettRelacao" value="<%=Pagina.getParamStr(request, "codIettRelacao")%>">
		<input type="hidden" name="codigosItem" value="">
		<input type="hidden" name="primeiroCodAri" value="">
		<input type="hidden" name="codArisControle" value="">
		
		<%
		// se não tiver tipo de acompanhamento selecionado, não carregar dados
		if(!"".equals(codTipoAcompanhamento)) {
		%>
		
			<table border="0" width="100%">
				<% if("true".equals(status)){ %>
						<tr>
							<input type="button" value="Prosseguir..." class="botao" onclick="javascript:relatorio();">
						</tr>
				<% } %>	
		
				<tr>
								
					<td align="left" valign="bottom" class="texto">
						<% /* Verifica a exibição do filtro Nível Planejamento */
						if (abaDao.verificaNivelPlanejamento("P")) { %>
							<% if(!"true".equals(status)){ %>
							<a href="javascript:abrirPopUpNivelPlanejamento()" title="Selecionar <%=configuracao.getSisGrupoAtributoSgaByCodSgaGrAtrNvPlan().getDescricaoSga()%>"><img src="../images/relAcomp/icon_nivelplanejamento.png" border="0"></a>
							<% } %>
						<% } %>
						<br>
						Data do relatório: <b><%=Pagina.trocaNullData(Data.getDataAtual())%></b>
						<br><br>
						
						<b>Ciclo de referência: </b>
						<!-- COMBO DE PERÍODOS DE REFERÊNCIA -->
						<select name="mesReferencia"
						<% if(!"true".equals(status)){ %> onchange="javascript:reload()" <% }
						   else { %>onchange="javascript:reload2()"<%}%>>
							<option value="0"
								<%
								if("0".equals(mesReferencia)){
								%>
									selected
								<%
								}
								%> >--SELECIONE--</option>				
		<%
							String selMesRef = "";
	
							Iterator itAcomp = acompanhamentos.iterator();
							while(itAcomp.hasNext()){
								AcompReferenciaAref acompReferencia = (AcompReferenciaAref) itAcomp.next();
	
								if(!"0".equals(mesReferencia) && mesReferencia.equals(acompReferencia.getCodAref().toString())){
									selMesRef = "selected";
									mesReferencia = acompReferencia.getCodAref().toString();
								}
								else if((mesReferencia == null || "".equals(mesReferencia)) && (arefAtual != null && arefAtual.equals(acompReferencia))) {
									selMesRef = "selected";
									mesReferencia = acompReferencia.getCodAref().toString();
								} else {
									selMesRef = "";
								}
								
								StringBuffer opcaoCombo = new StringBuffer(acompReferencia.getMesAref())
														.append("/").append(acompReferencia.getAnoAref())
														.append(" - ").append(acompReferencia.getNomeAref());
								
								if(acompReferencia.getOrgaoOrg() != null){
									opcaoCombo.append(" - ").append(acompReferencia.getOrgaoOrg().getSiglaOrg());
								}
		%>
								<option value="<%=acompReferencia.getCodAref()%>" <%=selMesRef%>>
									<%=opcaoCombo%>
								</option>
		<%
						}
		%>			
						</select>				
					</td>
				</tr>
			</table>
		
		<%
			if((mesReferencia != null && !"".equals(mesReferencia)) && !"".equals(periodo)){	
				List listNiveis = new ArrayList();
				String[] niveis = niveisPlanejamento.split(":");
				for(int n = 0; n < niveis.length; n++){
					String codNivel = niveis[n];
					
					if(!"".equals(codNivel)){
						listNiveis.add(sisAtributoDao.buscar(SisAtributoSatb.class, Long.valueOf(codNivel)));
					}
				}
	
				/* Busca coleção com todas as secretarias que possuem algum acompanhamento de referência cadastrado */
				Collection secretarias = acompReferenciaDao.getOrgaosComAcompanhamentosCriados(codTipoAcompanhamento);
				List listFunAcomp = tipoFuncAcompDao.getTipoFuncAcompEmitePosicao();
				
				StatusRelatorioSrl statusLiberado = (StatusRelatorioSrl) itemDao.
		                         buscar(StatusRelatorioSrl.class, Long.valueOf(AcompReferenciaItemDao.STATUS_LIBERADO));
				
				boolean primeiro = true;		
						
				Iterator itSecretarias = secretarias.iterator();
				
				if("true".equals(status)){ 
		%>		
					<br>
					<table border="0" width="100%">
						<tr><td class="texto"><b>Selecione os ítens que deseja imprimir:</b></td></tr>
					</table>
		<%
				}
				
				while(itSecretarias.hasNext() || primeiro) {
					/* Na primeira vez, atribui null à secretaria para imprimir informação de periodos EM MONITORAMENTO */
					OrgaoOrg secretaria = null;
								
					if(primeiro){
						secretaria = null;
					}else{
						secretaria = (OrgaoOrg) itSecretarias.next();
					}
					
					Long codArefReferencia = Long.valueOf(mesReferencia);
					int qtdePeriodosAnteriores = Integer.valueOf(periodo).intValue();
					
					/* Busca coleção com ciclos a serem considereados */
					Collection periodosConsiderados = new ArrayList();
					
					if(codArefReferencia.intValue() > 0) {
						periodosConsiderados = acompReferenciaDao.getPeriodosAnteriores(codArefReferencia, qtdePeriodosAnteriores,  Long.valueOf(codTipoAcompanhamento));
					}
					
					/* seta na sessão coleção de periodos, para ser utilizado no grafico */
					if(secretaria == null)
						session.setAttribute("periodosConsiderados", periodosConsiderados);
					else
						session.setAttribute("periodosConsiderados" + secretaria.getCodOrg(), periodosConsiderados);

					Long codIettPai = null;
					
					//if("proximosItens".equals(idPagina)) {
					//	codIettPai = Long.valueOf(itemDoNivelClicado);
					//}

					Boolean isSemInformacaoNivelPlanejamento = new Boolean(false);
					if("S".equals(semInformacaoNivelPlanejamento) || !abaDao.verificaNivelPlanejamento("P")) {
						isSemInformacaoNivelPlanejamento = new Boolean(true);
					}
					
					/* Recupera lista de itens para os periodos considerados por orgão responsavel */
					Object itens[] = acompReferenciaItemDao.getItensAcompanhamentoInPeriodosByOrgaoResp(
											periodosConsiderados, listNiveis, orgaoResponsavel, 
											usuario, seguranca.getGruposAcesso(), 
											Long.valueOf(codTipoAcompanhamento), codIettPai,
											isSemInformacaoNivelPlanejamento, null, null);

					List itensAcompanhamentos = new ArrayList((Collection)itens[0]);
					List itensGeral = new ArrayList((Collection)itens[1]);
	
					/* Se for encontrado algum periodo, imprime lista de itens */
					if(itensAcompanhamentos != null && itensAcompanhamentos.size() > 0) {
					%>
						<br>
						<div id="subconteudo">
							<%if(secretaria == null){%>
								<a href="javascript:abrirPopUpGrafico(0)" title="Gráfico de Evolução das Posições"><img src="../images/relAcomp/icon_grafico.png" border="0"></a>			
							<%} else { %>
								<a href="javascript:abrirPopUpGrafico(<%=secretaria.getCodOrg()%>)" title="Gráfico de Evolução das Posições"><img src="../images/relAcomp/icon_grafico.png" border="0"></a>								
							<%}%>	
							
							<table border="0" cellpadding="0" cellspacing="0" width="100%">		
								<tr>						
									<td class="espacador" colspan="<%=periodosConsiderados.size() + 6%>">
										<img src="../images/pixel.gif">
									</td>
								</tr>
								
								<!-- TÍTULO DA TABELA -->
								<tr class="linha_titulo">
							 		<td colspan="<%=periodosConsiderados.size() + 6%>">
		<%
								 	if(secretaria != null) {
								 		out.println(secretaria.getDescricaoOrg());
								 	}
		%>
								 	</td>						 						 	
								</tr>
		
								<tr>						
									<td class="espacador" colspan="<%=periodosConsiderados.size() + 6%>">
										<img src="../images/pixel.gif">
									</td>
								</tr>
								
								<!-- CABEÇALHO DA TABELA -->
								<tr class="linha_subtitulo">
									<td width="1%"></td>							
									<td width="50%" colspan="3">Acompanhamentos</td>
									<td width="10%" align="center" nowrap>
										<%=strLabelOrgao%><br>
									<%
										if(comboOrgao){
											comboOrgao = false;
											String scriptCombo = "";
											if(!"true".equals(status)){
												scriptCombo = _disabled + " onchange=\"reload();\"";
											}else{
												scriptCombo = _disabled + " onchange=\"reload2();\"";
											}
											Long codArefOrgao;
											if(!"".equals(mesReferencia))
												codArefOrgao = Long.valueOf(mesReferencia);
											else
												codArefOrgao = null;
											
											//List orgaos = new OrgaoDao(request).getOrgaosRespItemEstrutura(codArefOrgao);
											List orgaos;
											if(Pagina.getParamStr(request, "primeiraChamada") == null
														|| "".equals(Pagina.getParamStr(request, "primeiraChamada").trim())) {
												orgaos = orgaoDao.getOrgaosRespItemEstruturaRelatorio(itensAcompanhamentos);
												session.setAttribute("orgaosPrimeiraChamada", orgaos);
											}else{
												if (session.getAttribute("orgaosPrimeiraChamada") == null) {
													orgaos = new ArrayList();
												}else{
													orgaos = (List) session.getAttribute("orgaosPrimeiraChamada");
												}
											}
											
											if ( orgaoResponsavel.getCodOrg() != null ) {
									%>
												<combo:ComboTag 
														nome="orgaoResponsavel"
														objeto="ecar.pojo.OrgaoOrg" 
														label="siglaOrg" 
														value="codOrg" 
														order="siglaOrg"
														colecao="<%=orgaos%>"
														selected="<%=orgaoResponsavel.getCodOrg().toString()%>"
														scripts="<%=scriptCombo%>"
														textoPadrao="Todos"
												/>		
									<%
											} else {
									%>
												<combo:ComboTag 
														nome="orgaoResponsavel"
														objeto="ecar.pojo.OrgaoOrg" 
														label="siglaOrg" 
														value="codOrg" 
														order="siglaOrg"
														colecao="<%=orgaos%>"
														scripts="<%=scriptCombo%>"
														textoPadrao="Todos"
												/>		
									<%
											}
										}else{
											if ( orgaoResponsavel.getCodOrg() != null ) {
												out.println(orgaoResponsavel.getSiglaOrg());
											}else{
												out.println("Todos");
											}
										}
									%>
									</td>
		<%
								/* Imprime o nome dos ciclos */
								boolean ehPrimeiro = true;
								Iterator it = periodosConsiderados.iterator();
								double tamanhoColuna = 38 / periodosConsiderados.size();
								while(it.hasNext()){
									AcompReferenciaAref acompanhamento = (AcompReferenciaAref) it.next();
									String estilo = "";
									if(ehPrimeiro){
										estilo = "class=\"corSelecao\"";
										ehPrimeiro = false;
									}
		%>
									<td align="center" id="selecionado" <%=estilo%> width="<%=tamanhoColuna%>%"><%=acompanhamento.getNomeAref()%></td>
		<%							
								}					
								
								if("true".equals(status)){
		%>							
									<td align="center">Todos<input type="checkbox" class="form_check_radio" value="todos" id="<%if(secretaria != null)%><%=secretaria.getCodOrg()%>" onClick="checkAll(<%if(secretaria != null)%><%=secretaria.getCodOrg()%>)">
									<% if(secretaria != null){ %>
										<input type="hidden" id="ComboClicado<%=secretaria.getCodOrg()%>" value="">
									<% } %>
									</td>
		<%
								}
		%>
								</tr>	
		
								<tr>
									<td class="espacador" colspan="<%=periodosConsiderados.size() + 6%>">
										<img src="../images/pixel.gif">
									</td>
								</tr>
								
								<!-- DADOS DA TABELA -->
		<%
								Map dadosGrafico = new HashMap();
								
								List itensSessao = new ArrayList();
								/* Itera pelos itens */
								Iterator itItens = itensAcompanhamentos.iterator();	
		
								while(itItens.hasNext()){
									
									AtributoEstruturaListagemItens aeIett = (AtributoEstruturaListagemItens) itItens.next();
									//ItemEstruturaIett item = (ItemEstruturaIett) itItens.next();
									ItemEstruturaIett item = aeIett.getItem();
									
									String linkAbrirIettComeco = "";
									String linkAbrirIettFim = "";

									//if("posicaoGeral".equals(idPagina)) {
										//Verificar para mostrar itens até o menor nivel configurado nas configurações gerais...
										//Se menorNivel == -1, não foi informado nehuma estrutura, então ignora esta validação
										int nivelEtt = item.getNivelIett().intValue();
										if(menorNivel != -1 && nivelEtt > menorNivel) {
											continue;
										}
									//}

									if(menorNivel != -1){
										List filhos = itemDao.getDescendentesViaQry(item);
										if(filhos != null && filhos.size() > 0){
											Iterator itFilhos = filhos.iterator();
											while(itFilhos.hasNext()){
												ItemEstruturaIett filho = (ItemEstruturaIett) itFilhos.next();
												if(itensGeral.contains(filho)){
													linkAbrirIettComeco = "<a href=\"#\" onclick=\"javascript:abrirItem(form, '" + item.getCodIett() + "', '" + status + "')\">";
													linkAbrirIettFim = "</a>";
													break;
												}
											}
										}
									}
									
		%>
									<tr class="cor_sim" onmouseover="javascript:this.className='cor_over';" onClick="javascript:this.className='cor_clicked';" onmouseout="javascript:this.className='cor_sim';" bgcolor="<%=item.getEstruturaEtt().getCodCor3Ett()%>" >
										<td >
		<%								
										if(!"true".equals(status)){
											
											ItemEstrutMarcadorIettm marcador = marcadorDao.getUltimoMarcador(item, usuario);
											
											if(marcador != null){
		%>
											<a href="javascript:abrirPopUpListaAnotacao(<%=item.getCodIett()%>)">
											<%if(marcador.getCor() != null){%>
												<img src='../images/relAcomp/<%=marcador.getCor().getNomeCor().toLowerCase()%>_inf.png' border='0'>
											<%}else{%>
												<img src='../images/relAcomp/branco_inf.png' border='0'>
											<%}%>
											</a>
										<%	}
										}
										%>								
										</td>
										<td>
											<table>
											 <tr bgcolor="<%=item.getEstruturaEtt().getCodCor3Ett()%>">
												<td nowrap>											
													&nbsp;
		<%
													/* Identação pelo nível do item */
													int nivel = item.getNivelIett().intValue();
													for(int i = 1; i < nivel;i++){
		%>
														<img src="<%=request.getContextPath()%>/images/pixel.gif" width="22" height="9">
		<%
													}
		%>
												</td>
												<td valign="top"><img src="../images/icon_bullet_seta.png"></td>
												<td title="<%=item.getEstruturaEtt().getNomeEtt().trim()%>">
													<font color="<%=item.getEstruturaEtt().getCodCor4Ett()%>">
													
														<%
															String nomeIett = "".equals(aeIett.getDescricao()) ? "[Não Informado]" : aeIett.getDescricao();
														%>
													
														<%=linkAbrirIettComeco%><%=nomeIett%><%=linkAbrirIettFim%>
													</font>
												</td>
											</tr>
											</table>
										</td>
										<td nowrap> 
		<%
											Iterator itNiveis = itemDao.getNomeImgsNivelPlanejamentoItemAgrupado(item).iterator();
											int contNivel = 0;
											while(itNiveis.hasNext()){
												NomeImgsNivelPlanejamentoBean imagemNivelPlanejamento = (NomeImgsNivelPlanejamentoBean)itNiveis.next();
												out.print(imagemNivelPlanejamento.geraHtmlImg(request));
											}
		%>
										</td>
										
										<td>
										<% if(!"true".equals(status)){ %>
											<a href="javascript:abrirPopUpInserirAnotacao(<%=item.getCodIett()%>)" title="Inserir anotação">
												<img src="../images/relAcomp/icon_anotacoes.png" border="0">
											</a>
										<% } %>	
										</td>	
																
										<td align="center">
		<%
											/* Imprime nome do orgão responsável pelo item */
											if(item.getOrgaoOrgByCodOrgaoResponsavel1Iett() != null){
												if(item.getOrgaoOrgByCodOrgaoResponsavel1Iett().getSiglaOrg() != null)
													out.println(item.getOrgaoOrgByCodOrgaoResponsavel1Iett().getSiglaOrg());	
												else
													out.println(item.getOrgaoOrgByCodOrgaoResponsavel1Iett().getDescricaoOrg());	
											} else {
												/* Se não possuir orgao procura orgao do pai */
												ItemEstruturaIett itemAux = item;
												while(itemAux != null && itemAux.getOrgaoOrgByCodOrgaoResponsavel1Iett() == null && itemAux.getItemEstruturaIett() != null){
													itemAux = itemAux.getItemEstruturaIett();
												}
												if(itemAux != null && itemAux.getOrgaoOrgByCodOrgaoResponsavel1Iett() != null){
													if(itemAux.getOrgaoOrgByCodOrgaoResponsavel1Iett().getSiglaOrg() != null)
														out.println(itemAux.getOrgaoOrgByCodOrgaoResponsavel1Iett().getSiglaOrg());	
													else
														out.println(itemAux.getOrgaoOrgByCodOrgaoResponsavel1Iett().getDescricaoOrg());											
												}
											}
		%>
										</td>
		<%
										/* Apresentar os pareceres */
										Iterator itPeriodos = periodosConsiderados.iterator();
										Map  map = acompReferenciaItemDao.criarMapPeriodoAri(periodosConsiderados, item);
										while(itPeriodos.hasNext()){ 
											AcompReferenciaAref acompReferencia = (AcompReferenciaAref) itPeriodos.next();
											
											if(map.isEmpty()){
												%><td align="center">&nbsp;</td><%
											}
											else if(!map.containsKey(acompReferencia)){
												%><td align="center" valign="middle"><p title="Não foi solicitado acompanhamento">N/A</p></td><%
											} else {
												flag = "acompanhado"; 
												AcompReferenciaItemAri ari = (AcompReferenciaItemAri) map.get(acompReferencia);										
		%>
												<td align="center" nowrap>
		<%
												if("N".equals(exigeLiberarAcompanhamento) || ari.getStatusRelatorioSrl().equals(statusLiberado)){
													if(!"true".equals(status)){
												%>
													<a name="ancora<%=ari.getItemEstruturaIett().getCodIett()%>" href="#" onclick="javascript:aoClicarConsultar(form, <%=ari.getCodAri()%>, <%=ari.getItemEstruturaIett().getCodIett()%>)">
												<%	
														itensSessao.add(ari.getCodAri().toString());
													}
													//List relatorios = acompReferenciaItemDao.getAcompRelatorioArelOrderByFuncaoAcomp(ari, listFunAcomp);
													List relatorios = acompReferenciaItemDao.getAcompRelatorioArelOrderByFuncaoAcomp(ari, tpfaOrdenadosPorEstrutura);
													Iterator itRelatorios = relatorios.iterator();
		
													String imagePath = "";
													String aval = "";
													while(itRelatorios.hasNext()){												
														AcompRelatorioArel relatorio = (AcompRelatorioArel) itRelatorios.next();
														
														// Por Rogério (05/10/2007)
														// Modificada a forma de busca da imagem relacionada com a Cor.
														// Referente ao Mantis #7442
																																																						
														boolean imageError = false;
														if( "S".equals(relatorio.getIndLiberadoArel()) ) {
															Cor cor = ( relatorio.getCor()!=null ? relatorio.getCor() : null );
															TipoFuncAcompTpfa tpfa = ( relatorio.getTipoFuncAcompTpfa() != null ? relatorio.getTipoFuncAcompTpfa() : null );
															
															imagePath = corDao.getImagemPersonalizada(cor, tpfa, "L");
															
															if( imagePath != null ) {
																/* -- As tags do CSS "max-width" e "max-height" não funcionam no IE6 ou menor -- */ 
															    aval += "<img border=\"0\" src=\"" + request.getContextPath() + "/DownloadFile?tipo=open&RemoteFile=";
															    aval += imagePath + "\" style=\"width: 23px; height: 23px;\" title=\"" + relatorio.getTipoFuncAcompTpfa().getLabelTpfa() + "\">";
															} else {
																if( relatorio.getCor() != null && relatorio.getCor().getCodCor() != null ) { 
																	aval += "<img border=\"0\" src=\"../images/relAcomp/";
																	aval += corDao.getImagemRelatorio(relatorio.getCor(), relatorio.getTipoFuncAcompTpfa()) + "\" title=\"";
																	aval += relatorio.getTipoFuncAcompTpfa().getLabelTpfa() + "\" >";
																} else {
																	imageError = true;
																}
															}
														} else {
															imageError = true;
														}
														
														if( imageError ) {
															aval += "<img border=\"0\" src=\"../images/relAcomp/";
															aval += corDao.getImagemRelatorio(null, relatorio.getTipoFuncAcompTpfa()) + "\" title=\"";
															aval += relatorio.getTipoFuncAcompTpfa().getLabelTpfa() + "\" >";
														}
													}
													
													out.println(aval);
																										
													if(!"true".equals(status)){
												%>
													</a>
												<%
													}
												} else {
													if("N".equals(exigeLiberarAcompanhamento) || (ari.getAcompRelatorioArels() != null && ari.getAcompRelatorioArels().size() > 0)) {
														if(!"true".equals(status)){
													%>
														<a name="ancora<%=ari.getItemEstruturaIett().getCodIett()%>"  href="#" onclick="javascript:aoClicarConsultar(form, <%=ari.getCodAri()%>, <%=ari.getItemEstruturaIett().getCodIett()%>)">
													<% 	
															itensSessao.add(ari.getCodAri().toString());

														}  %>	
														<p title="Não liberado">N/L</p>
													<%  if(!"true".equals(status)){  %>	
														</a>
													<%	}
													}
													else {
														flag = "";
													%>
														<!--p title="Não foi solicitado acompanhamento">N/A</p -->&nbsp;
													<%
													}
												}
		%>
												</td>
		<%
											}
										}
										if("true".equals(status)){ 
		%>
											<td align="center"><input type="checkbox" class="form_check_radio" id="<%if(secretaria != null)%><%=secretaria.getCodOrg()%>" value="<%=item.getCodIett()%>"></td>
		<%
										}else{
		%>
											<input type="hidden" name="item_<%=contador%>" id="item_<%=contador%>" value="<%=item.getCodIett()%>">
		<%
										}
		%>							
									</tr>
		<%
									contador++;
									
								}
								//Setando hidden com os itens da listagem para os botões Avançar/Retroceder
								String strCodArisControle = "";
								Iterator itCodArisControle = itensSessao.iterator();
								while(itCodArisControle.hasNext()){
									String codAri = (String) itCodArisControle.next();
									strCodArisControle += codAri + "|";
								}
		%>
								<script language="Javascript">
								document.form.codArisControle.value = "<%=strCodArisControle%>";
								</script>
		<%						
								request.getSession().setAttribute("codArisControle", itensSessao);
		%>
		
								<tr class="linha_subtitulo2">
									<td colspan="<%=periodosConsiderados.size() + 6%>">
										&nbsp;
									</td>
								</tr>
							
							</table>
						</div>	
					<%
					} else {
					%>
						<table border="0" width="100%">
							<tr><td class="texto" align="center"><b><%=_msg.getMensagem("posicaoGeral.filtro.nenhumRegistro")%></b></td></tr>
						</table>
					<%
					}
					if(primeiro)
						primeiro = false;
				}
			}
			
		
			if("true".equals(status) && contador < 1){
				List orgaos2 = (List) session.getAttribute("orgaosPrimeiraChamada");
				
				String scriptCombo2 = _disabled + " onchange=\"reload2();\"";
		%>
				<br>
				<br>
				<table border="0" width="100%">
					<tr><td class="texto" align="center"><b><%=_msg.getMensagem("posicaoGeral.filtro.nenhumRegistro")%></b></td></tr>
					<tr><td></td></tr>
					<tr><td></td></tr>
					<tr>
						<td class="texto">
							<b>Selecione o orgão responsável:</b>
					<%
							if (orgaoResponsavel.getCodOrg() != null) {
					%>
								<combo:ComboTag 
									nome="orgaoResponsavel"
									objeto="ecar.pojo.OrgaoOrg" 
									label="siglaOrg" 
									value="codOrg" 
									order="siglaOrg"
									colecao="<%=orgaos2%>"
									selected="<%=orgaoResponsavel.getCodOrg().toString()%>"
									scripts="<%=scriptCombo2%>"
									textoPadrao="Todos"
								/>
					<%
							}else{
					%>
								<combo:ComboTag 
									nome="orgaoResponsavel"
									objeto="ecar.pojo.OrgaoOrg" 
									label="siglaOrg" 
									value="codOrg" 
									order="siglaOrg"
									colecao="<%=orgaos2%>"
									scripts="<%=scriptCombo2%>"
									textoPadrao="Todos"
								/>
					<%
							}
					%>
						</td>
					</tr>
				</table>
	<% 
			}
		} else {
	%>
			<table width="100%" border="0" cellpadding="0" cellspacing="0">
				<tr>
					<td class="texto" align="center">
						<%=_msg.getMensagem("posicaoGeral.validacao.tipoAcompanhamento.obrigatorio")%>
					</td>
				</tr>
			</table>
	<% 
		}
	%>
		
	</form>
		
	<table width="100%" border="0" cellpadding="0" cellspacing="0">
		<tr>
			<td>
				&nbsp;
			</td>
		</tr>
	</table>
	<%
	Date dataFinal = Data.getDataAtual();
	%>
	<table class="form">
		<tr>
			<td>
				<%
				long df = dataFinal.getTime();
				long di = dataInicio.getTime();
				%>
				<b>Tempo para processar esta página:</b> <%=Data.parseDateHourSegundos( new Date(df - di) )%> mm.ss.SSS
			<td>
		</tr>
	</table>
	
	<%
	}

} catch (ECARException e) {
	org.apache.log4j.Logger.getLogger(this.getClass()).error(e);
	Mensagem.alert(_jspx_page_context, e.getMessageKey());
} catch (Exception e){
%>
	<%@ include file="/excecao.jsp"%>
<%
}
%>
	
</div>

</body>
<%@ include file="../include/ocultarAguarde.jsp"%>
<%@ include file="../include/estadoMenu.jsp"%>
<%@ include file="../include/mensagemAlert.jsp" %>
</html>