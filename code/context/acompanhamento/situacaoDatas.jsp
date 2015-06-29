
<jsp:directive.page import="ecar.dao.AbaDao"/>
<jsp:directive.page import="ecar.pojo.Aba"/>
<jsp:directive.page import="java.util.ArrayList"/>
<jsp:directive.page import="ecar.permissao.ValidaPermissao"/>
<%@ include file="../../login/validaAcesso.jsp"%>
<%@ include file="../../frm_global.jsp"%>

<%@ taglib uri="/WEB-INF/ecar-util.tld" prefix="util"%>
<%@ taglib uri="/WEB-INF/ecar-combo.tld" prefix="combo"%>

<%@ page import="ecar.dao.AcompReferenciaItemDao" %>
<%@ page import="ecar.dao.ItemEstruturaDao" %>
<%@ page import="ecar.pojo.AcompReferenciaItemAri" %> 
<%@ page import="ecar.pojo.ItemEstruturaIett" %> 
<%@ page import="ecar.pojo.OrgaoOrg" %> 
<%@ page import="ecar.pojo.PontoCriticoPtc" %>
<%@ page import="ecar.dao.PontoCriticoDao" %>
<%@ page import="ecar.bean.AtributoEstruturaListagemItens" %>
<%@ page import="ecar.pojo.UsuarioUsu" %>
<%@ page import="comum.util.Data" %> 
<%@ page import="java.util.Calendar" %>
<%@ page import="java.util.Date" %>

<%@ page import="java.util.List" %> 
<%@ page import="java.util.Iterator" %>
<%@ page import="java.util.Collection" %>
<%@ page import="java.util.Collections" %>

<%@ page import="ecar.pojo.AcompReferenciaAref"%>
<%@ page import="ecar.dao.AcompReferenciaDao" %>
<%@ page import="ecar.pojo.Cor" %>
<%@ page import="ecar.dao.CorDao" %>
<%@ page import="ecar.dao.TipoFuncAcompDao" %>
<%@ page import="ecar.pojo.PontocriticoCorPtccor" %>
<%@ page import="ecar.pojo.PontocriticoCorPtccorPK" %>
<%@ page import="ecar.dao.PontocriticoCorPtccorDAO" %>
<%@ page import="ecar.pojo.SisAtributoSatb" %>
<%@ page import="ecar.pojo.SisGrupoAtributoSga" %>
<%@ page import="ecar.dao.SisGrupoAtributoDao" %>

<%
try{ 
	
	UsuarioUsu usuario = ((ecar.login.SegurancaECAR)session.getAttribute("seguranca")).getUsuario();
	request.setAttribute("exibirAguarde", "true");
	String codTipoAcompanhamento = Pagina.getParamStr(request, "codTipoAcompanhamento");
	String mesReferencia = 	Pagina.getParamStr(request, "referencia_hidden");
	String strCodigosItem = Pagina.getParamStr(request, "codigosItem");
	String niveisPlanejamento = Pagina.getParamStr(request, "niveisPlanejamento");
	String itemDoNivelClicado = Pagina.getParamStr(request, "itemDoNivelClicado");
	String orgaoResponsavelCod = Pagina.getParamStr(request, "orgaoResponsavel");
	AcompReferenciaAref aref = null;
	AcompReferenciaAref acompReferencia = null;					
	ItemEstruturaIett item = null;
	ItemEstruturaIett itemEstrutura = null;
	AcompReferenciaItemAri ari = null;
	AcompReferenciaItemAri acompReferenciaItem = null;
	TipoAcompanhamentoTa tipoAcomp = null;
	String strCodAri = "";
	AcompReferenciaItemDao acompReferenciaItemDao = new AcompReferenciaItemDao(request);
	ItemEstruturaDao itemDao = new ItemEstruturaDao(request);
	AcompReferenciaItemDao ariDao = new AcompReferenciaItemDao(request);
	ItemEstruturaDao itemEstruturaDao = new ItemEstruturaDao(request);
	AcompReferenciaDao acompReferenciaDao = new AcompReferenciaDao(request);
	boolean ehSeparadoPorOrgao = false;
	List arisByIetts = new ArrayList();
	OrgaoOrg orgao = null;
	OrgaoDao orgaoDao = new OrgaoDao(request);
	List listaOrgaos = new ArrayList();
	Iterator itListaOrgaos = null;
	boolean ultimo = false;
	AcompReferenciaAref arefOrgao = null;
    String codigosItensSelecionados = null;
    TipoFuncAcompDao tipoFuncAcompDao = new TipoFuncAcompDao(request);
    List arvoreItensSelecionados = new ArrayList();
    String codigosAriOrdenados = "";
	
    
	OrgaoOrg orgaoResponsavel =  new OrgaoOrg();
	if(!"".equals(orgaoResponsavelCod)){
		orgaoResponsavel = (OrgaoOrg) acompReferenciaItemDao.buscar(OrgaoOrg.class, Long.valueOf(orgaoResponsavelCod) );
	}
	
	//buscar o aref
	 if(mesReferencia != null && !mesReferencia.equals("")) {
     	aref = (AcompReferenciaAref) acompReferenciaDao.buscar(AcompReferenciaAref.class, Long.valueOf(mesReferencia));
     	if(aref.getTipoAcompanhamentoTa() != null && aref.getTipoAcompanhamentoTa().getIndSepararOrgaoTa() != null 
     			&& aref.getTipoAcompanhamentoTa().getIndSepararOrgaoTa().equals("S") )
     		ehSeparadoPorOrgao = true;
     }
	
	
	if(itemDoNivelClicado != null && !itemDoNivelClicado.equals("")) {
		
		if(itemDoNivelClicado.contains("_org")) {
			String strCodItem = itemDoNivelClicado.substring(0, itemDoNivelClicado.indexOf("_org"));
			itemEstrutura = (ItemEstruturaIett) itemDao.buscar(ItemEstruturaIett.class, Long.valueOf(strCodItem));
		} else {
			itemEstrutura = (ItemEstruturaIett) itemDao.buscar(ItemEstruturaIett.class, Long.valueOf(itemDoNivelClicado));
		}
			
	} else {
		// Pra nao dar erro quando perder codTipoAcompanhamento
		// descobre pelo Ari
		if(ari != null)
			itemEstrutura = (ItemEstruturaIett) itemEstruturaDao.buscar(ItemEstruturaIett.class, ari.getItemEstruturaIett().getCodIett());
	}
		
	
	
	
	if(ehSeparadoPorOrgao) {
		//monta a lista de orgaos
		if(!"".equals(strCodigosItem)){
	       	String[] iettSelecionado = strCodigosItem.split(";");
	       	
	      //cria a lista de orgaos
	      	for(int i = 0; i < iettSelecionado.length; i++){
				if(!"".equals(iettSelecionado[i])){
					String strCodOrg = iettSelecionado[i].substring(iettSelecionado[i].indexOf("_org") +4 , iettSelecionado[i].length());
					if(!strCodOrg.equals("X")) {
						orgao = (OrgaoOrg) orgaoDao.buscar(OrgaoOrg.class, new Long(strCodOrg));
						if(!listaOrgaos.contains(orgao)) {
							listaOrgaos.add(orgao);
						}
					}	
					
				}
			}
		}
	}
	
	
	 if(!listaOrgaos.isEmpty()) {
     	itListaOrgaos  = listaOrgaos.iterator();
     } else {	
     	ultimo = true;
	}
    

	final String strDataComparativo = request.getParameter("dataComparativo");
	final String strRelogios = request.getParameter("relogios");
	final String strTipoAssunto = request.getParameter("tipoAssunto");
	String flag = "";
	String flagPrimeiroPt = "";


	// essa variavel é utilizada no include do cabecalho das paginas
	String telaAnterior = "posicaoGeral.jsp?codTipoAcompanhamento="+codTipoAcompanhamento +"&mesReferencia="+mesReferencia + "&periodo=" + Pagina.getParamStr(request, "periodo")+"&hidFormaVisualizacaoEscolhida="+Pagina.getParamStr(request, "hidFormaVisualizacaoEscolhida");
	
	// essa variavel eh apenas utilizada no include do cabecalho da tela
	boolean exibeCombo = false;
	
	
%>


<%@page import="comum.util.Util"%>
<%@page import="ecar.pojo.TipoAcompanhamentoTa"%>
<%@page import="ecar.dao.OrgaoDao"%><html lang="pt-br"> 
<head>
<%@ include file="../../../include/meta.jsp"%>
<%@ include file="/titulo.jsp"%>
<link rel="stylesheet" href="<%=_pathEcar%>/css/style_<%=configuracaoCfg.getEstilo().getNome()%>.css" type="text/css">
<script language="javascript" src="<%=_pathEcar%>/js/menu_retratil.js"></script>
<script language="javascript" src="<%=_pathEcar%>/js/validacoes.js"></script>
<script language="javascript" src="<%=_pathEcar%>/js/datas.js"></script>
<script language="javascript" src="<%=_pathEcar%>/js/popUp.js"></script>
<script language="javascript" src="<%=_pathEcar%>/js/prototype.js"></script>
<script language="javascript" src="<%=_pathEcar%>/js/destaqueLinha.js"></script>
<script>
function reload(){
	document.forms[0].action = "situacaoDatas.jsp";
	document.forms[0].submit();
}

function trocarAba(nomeAba) {
	document.forms[0].action = nomeAba;
	document.forms[0].submit();
}
function trocaAba(link, codAri){
	document.forms[0].codAri.value = codAri;
	document.forms[0].action = link;
	document.forms[0].submit();
}

function trocaAbaSemAri(link, codIettRelacao){
	document.form.itemDoNivelClicado.value = codIettRelacao;
	document.form.action = link;
	document.form.submit();
}

function trocaAbaImpressao(link){
	document.forms[0].action = link;
	document.forms[0].submit();
}

function Relogios(){
	var selecionados = "";
	var tpAssunto = "";
	var i = 0;
	var el = document.getElementsByTagName("INPUT");
	var fim = el.length;
  
   	while (i < fim){
    	if (el.item(i).type  == "checkbox") {
    		if (el.item(i).checked == true)
       	  		selecionados = selecionados + el.item(i).value + ";";         	      	
        }        
        i++;  
   	}
   	//INSERI AO FIM PRA PODER TIRAR AS FIGURAS QUE ESTAVAM POLUINDO A TELA.
	selecionados = selecionados + "Proibido;";
   	
	if (selecionados == "Proibido;" ) {
    	alert("Não há ítens selecionados!");
   	}
	else{
		//tpAssunto = document.form.codSgaPontoCritico.value;
		tpAssunto = formPrincipal.codSgaPontoCritico.value;
		formPrincipal.tipoAssunto.value = tpAssunto;		
		formPrincipal.relogios.value = selecionados;	
    	formPrincipal.action = "situacaoDatas.jsp";
		formPrincipal.submit();
	}
	
}

var formPrincipal ; 

function ValidaRelogios(){
	formPrincipal = document.getElementById('formPrincipal');
	var comparativo = formPrincipal.dataComparativo.value;
	var atual = formPrincipal.dataAtual.value;
	var comparativoParts = comparativo.split("/");
	var atualParts = atual.split("/");
	if ( (Number(comparativoParts[2]) < Number(atualParts[2])) ||
		(Number(comparativoParts[2]) == Number(atualParts[2]) &&
		( (Number(comparativoParts[1]) < Number(atualParts[1])) ||
		( (Number(comparativoParts[1]) == Number(atualParts[1])) &&
		(Number(comparativoParts[0]) < Number(atualParts[0])) ))))
	{
		alert("A data comparativa deve ser posterior à data de hoje.");
		return false;
	
	}	else if(!validaData(formPrincipal.dataComparativo,false,true,true)){
		if(form.dataComparativo.value == ""){
			alert("Digite uma data comparativa.");
		}else{
			alert("Campo inválido para data comparativa.");
		}
		
		return false;
	}	
		Relogios();
}

function abrePopUpPontosCriticos(codPtc){
	if(isIE()) {		
		abreJanela("<%=_pathEcar + "/acompanhamento/popUp/popUpPontosCriticos.jsp"%>?codPtc=" + codPtc + "&ehPopup=S", 500, 320, "pCriticos");
	}
	else {
		document.getElementById("popup_flutuante").style.display = "block";
		var url = "<%=_pathEcar + "/acompanhamento/popUp/popUpPontosCriticos.jsp"%>?codPtc=" + codPtc;
		openAjax(url, "popup_flutuante", null, null, true, "<%=_pathEcar%>/images");
	}
}

function agruparPontosCriticosItem(idItem){

	var item = document.getElementById(idItem);
	
	if (item.style.display == "none"){
		item.style.display = "";	
	}
	else{
		item.style.display = "none";		
	}
}

</script>

</head>

<body style="height:100%; overflow-y:auto;">

<%@ include file="../../cabecalho.jsp"%>
<%@ include file="../../divmenu.jsp"%>

<div id="popup_flutuante"> </div>

<div id="conteudo"> 

	<!-- TÍTULO -->
	<%@include file="/titulo_tela.jsp"%>
	<br><br>	
	<%
	
	
	String periodo = Pagina.getParamStr(request, "periodo");
	
	String telaListagem = "posicaoGeral.jsp?codTipoAcompanhamento="+codTipoAcompanhamento +"&mesReferencia="+mesReferencia + "&periodo=" + periodo;
	
	//Define a forma de visualização das abas de acompanhamento
	//Filtro geral -> exibe todas as abas
	// Demais filtros -> só exibe a aba específica do acompanhamento selecionado
	String formaVisualizacao =  Pagina.getParamStr(request, "hidFormaVisualizacaoEscolhida");		
	Boolean exibirAbasAcompanhamento = Boolean.TRUE;
	if(formaVisualizacao.equalsIgnoreCase("personalizado")) {
		exibirAbasAcompanhamento= Boolean.FALSE;
	}  		
			
	// Configuração	
	//ConfiguracaoCfg configuracao = new ConfiguracaoDao(request).getConfiguracao();
	String pathRaiz = configuracao.getRaizUpload();	
	
	%>
	<!-- ABA DE TIPO DE ACOMPANHAMENTO -->
	<util:barraLinkTipoAcompanhamentoTag
			codTipoAcompanhamentoSelecionado="<%=codTipoAcompanhamento%>"
			chamarPagina="posicaoGeral.jsp" 
			exibirAbasAcompanhamento="<%=exibirAbasAcompanhamento%>"
			tela="lista"
			caminho="<%=_pathEcar%>" 
			gruposUsuario= "<%=seguranca.getGruposAcesso()%>"
			request = "<%=request%>"
	/>
	
		
	<!-- ABA DE LISTAS -->
	<util:barraLinksRegAcomp
		acompReferencia="<%=aref%>"  
		selectedFuncao="SITUACAO_PONTOS_CRITICOS"
		primeiroAcomp="<%=codTipoAcompanhamento%>"
		request="<%=request%>"
		usuario="<%=usuario%>"
		tela="acompanhamento"
		gruposUsuario="<%=seguranca.getGruposAcesso() %>"
		abaSuperior ="S"
		listaGeral="<%=telaListagem %>"
		caminho="<%=_pathEcar%>"
		/>  
	
<%
	boolean exibirComboTipoPtc = configuracao.getSisGrupoAtributoSgaTipoPontoCritico() != null;
%>
<br></br>	
<%




	if(arisByIetts == null || arisByIetts.isEmpty()){
%>
	<util:barraLinksRegAcomp
		acompReferencia="<%=aref%>"  
		selectedFuncao="SITUACAO_PONTOS_CRITICOS"
		primeiroAcomp="<%=codTipoAcompanhamento%>"
		request="<%=request%>"
		usuario="<%=usuario%>"
		tela="acompanhamento"
		gruposUsuario="<%=seguranca.getGruposAcesso() %>"
	/>

<br>

<% } %>	
<form  name="form" method="post" id="formPrincipal" onsubmit="return false">	

	<table>
		<tr>
			<td class="label" width="20px">
				Estado
			</td>
<%
			PontoCriticoPtc pontoCriticoPtc = new PontoCriticoPtc();
			PontocriticoCorPtccor ptcCor = null;
			List setCores = new CorDao(request).listar(Cor.class, new String[]{"ordemCor","asc"});
			Cor cor = null;
			PontocriticoCorPtccorPK id = null;
			Iterator itCores = null;
			if (setCores != null)
				itCores = setCores.iterator();
			
			String imagePath = "";
			CorDao cDao = new CorDao(request);
			
			UsuarioUsu usuarioImagem = null;  
    		String hashNomeArquivo = null;
			
			while (itCores.hasNext())
			{
				cor = (Cor) itCores.next(); 			
				id = new PontocriticoCorPtccorPK(pontoCriticoPtc.getCodPtc(), cor.getCodCor());
				ptcCor = (PontocriticoCorPtccor)new PontocriticoCorPtccorDAO(request).buscar(cor, pontoCriticoPtc);
				
				if(cor.getIndPontoCriticoCor().equals("S")){

%>			
			<td>
				<table>
					<tr>
						<td>
							<input type="checkbox" class="form_check_radio" value="<%=cor.getNomeCor()%>" <% if((strRelogios == null || strRelogios.equals("")) || (strRelogios != null && !strRelogios.equals("") && strRelogios.contains(cor.getNomeCor()))){ %> checked <% } %>>
						</td>
						<td>
						<%
						imagePath = cDao.getImagemPersonalizada(cor, null, null);
						if( imagePath != null ) {
							
							usuarioImagem = ((ecar.login.SegurancaECAR)request.getSession().getAttribute("seguranca")).getUsuario(); 
	    					hashNomeArquivo = Util.calcularHashNomeArquivo(pathRaiz, imagePath);
	    					Util.adicionarMapArquivosAtuaisUsuarios(usuarioImagem, hashNomeArquivo, pathRaiz, imagePath);
							
							/* -- As tags do CSS "max-width" e "max-height" não funcionam no IE6 ou menor -- */ %>
<!-- 	     		    	<img border="0" src="<%=request.getContextPath()%>/DownloadFile?tipo=open&RemoteFile=<%=hashNomeArquivo%>" style="width: 23px; height: 23px;" title="<%=cor.getSignificadoCor()%>"> -->
					    	<img border="0" src="<%=request.getContextPath()%>/DownloadFile?tipo=open&RemoteFile=<%=hashNomeArquivo%>" title="<%=cor.getSignificadoCor()%>"> <%
						} else {
							if( cor.getCodCor() != null ) { %>
								<img src="<%=request.getContextPath()%>/images/pc<%=cor.getNomeCor()%>1.png" title="<%=cor.getSignificadoCor()%>" /> <% 
							}
						} %>
							
						</td>
					</tr>
				</table>
			</td>
			<%	
				}			
			}
			%>
			<td>
				<table>
					<tr>
						<td>
							<input type="checkbox" class="form_check_radio" value="Branco" <% if((strRelogios == null || strRelogios.equals("")) || (strRelogios != null && !strRelogios.equals("") && strRelogios.contains("Branco"))){ %> checked <% } %>>
						</td>
						<td>
							<img src="<%=request.getContextPath()%>/images/pcBranco1.png" title="Registrado">
						</td>
					</tr>
				</table>
			</td>
			<td>
				<table>
					<tr>
						<td>
							<input type="checkbox" class="form_check_radio" value="Checked" <% if((strRelogios == null || strRelogios.equals("")) || (strRelogios != null && !strRelogios.equals("") && strRelogios.contains("Checked"))){ %> checked <% } %>>
						</td>
						<td>
							<img src="<%=request.getContextPath()%>/images/pcChecked1.png" title="Solucionado">
						</td>
					</tr>
				</table>
			</td>
			<td>
				<table>
					<tr>
						<td>
							<input type="checkbox" class="form_check_radio" value="PretoFixo" <% if((strRelogios == null || strRelogios.equals("")) || (strRelogios != null && !strRelogios.equals("") && strRelogios.contains("PretoFixo"))){ %> checked <% } %>>
						</td>
						<td>
							<img src="<%=request.getContextPath()%>/images/pcPretoFixo1.png" title="Ultrapassado">
						</td>
					</tr>
				</table>
			</td>
			<td width="140px">&nbsp;</td>
			
			<tr>
				<td colspan="10" align="center">
					<table>
						<%			
						SisAtributoSatb selectedTipoPtC = pontoCriticoPtc.getSisAtributoTipo();
						SisGrupoAtributoSga sisGrupoPtc = configuracao.getSisGrupoAtributoSgaTipoPontoCritico();
						if (sisGrupoPtc != null)
						{
						%>
						<tr>
							<td class="label">Tipo/Assunto</td>
							<td>
								<%		
								List tiposPontosCriticos = new SisGrupoAtributoDao().getAtributosOrdenados(sisGrupoPtc);
													
								String script = _disabled;
								if (selectedTipoPtC != null)
								{
								%>
									<combo:ComboTag 
											nome="codSgaPontoCritico"
											objeto="ecar.pojo.SisAtributoSatb"
											label="descricaoSatb" 
											value="codSatb" 
											order="descricaoSatb"
											textoPadrao="Todos"
											colecao="<%=tiposPontosCriticos%>"
											selected="<%=selectedTipoPtC.getCodSatb().toString()%>"
											scripts="<%=script%>"
											filters="indAtivoSga=S"
									/>
								<%
								} else {
									//deixa fixo o que o usuário selecionou na combo quandoregarregar a tela.
									String aux  = (!"".equals(Pagina.getParamStr(request, "codSgaPontoCritico")) ? Pagina.getParamStr(request, "codSgaPontoCritico") : "");
								%>
									<combo:ComboTag 
										nome="codSgaPontoCritico"
										objeto="ecar.pojo.SisAtributoSatb" 
										label="descricaoSatb" 
										value="codSatb" 
										order="descricaoSatb"
										textoPadrao="Todos"
										colecao="<%=tiposPontosCriticos%>"
										scripts="<%=script%>"
										filters="indAtivoSga=S"
										selected="<%=aux%>"
									/>
								<%
								}
								%>
							</td>
						</tr>
						<%			
						}
						else {
						%>
						<tr>
							<td colspan="2">
								<input type="hidden" name="codSgaPontoCritico" value="">
							</td>
						</tr>
						<%
						}
						%>
					</table>
				</td>
			</tr>
		</tr>			
	</table>
	

	
	<br>
	<center><input type="button" value="Recarregar" class="botao" onclick="javascript:ValidaRelogios();"></center>	
	<br>

<input type="hidden" name="codTipoAcompanhamento" value="<%=Pagina.getParamStr(request, "codTipoAcompanhamento")%>">
<input type="hidden" name="periodo" value="<%=Pagina.getParamStr(request, "periodo")%>">
<!-- input type="hidden" name="mesReferencia" value="<%=Pagina.getParamStr(request, "mesReferencia")%>" -->
<input type="hidden" name="mesReferencia" value="<%=aref.getCodAref().toString()%>">
<input type="hidden" name="niveisPlanejamento" value="<%=Pagina.getParamStr(request, "niveisPlanejamento")%>">
<input type="hidden" name="semInformacaoNivelPlanejamento" value="<%=Pagina.getParamStr(request, "semInformacaoNivelPlanejamento")%>">
<input type="hidden" name="codAri" value="<%=strCodAri%>">
<input type="hidden" name="primeiroIettClicado" value="<%=Pagina.getParamStr(request, "primeiroIettClicado")%>">
<input type="hidden" name="primeiroAriClicado" value="<%=Pagina.getParamStr(request, "primeiroAriClicado")%>">
<input type="hidden" name="codigosItem" value="<%=strCodigosItem%>">
<input type="hidden" name="relogios" value="">
<input type="hidden" name="tipoAssunto" value="">
<!-- Campo necessario para guardar a forma de visualização escolhida no filtro -->
<input type="hidden" name="hidFormaVisualizacaoEscolhida" value='<%=Pagina.getParamStr(request, "hidFormaVisualizacaoEscolhida")%>'>



<table border="0" cellpadding="0" cellspacing="0" width="100%">
	<tr><td class="espacador" colspan="2"><img src="<%=_pathEcar%>/images/pixel.gif"></td></tr>
	<tr class="linha_subtitulo">		
	</tr>
	<tr><td class="espacador" colspan="2"><img src="<%=_pathEcar%>/images/pixel.gif"></td></tr>
</table>
	
<table cellpadding="0" cellspacing="0" width="100%">	
	<tr>						
				<td class="espacador" colspan="7">
					<img src="<%=_pathEcar%>/images/pixel.gif">
				</td>
			</tr>
			
			<!-- TÍTULO DA TABELA -->
			<tr class="linha_titulo">
		 		<td colspan="7">
			 	</td>						 						 	
			</tr>

			<tr>						
				<td class="espacador" colspan="7">
					<img src="<%=_pathEcar%>/images/pixel.gif">
				</td>
			</tr>			
			
			<%
 					final Date dataAtual = Data.getDataAtual();
			
					//Comparativo = dataAtual + 5 dias
					Calendar calComparativo = Data.getCalendar(dataAtual);
					calComparativo.add(Calendar.DAY_OF_MONTH, 5);
					Date dataComparativo = calComparativo.getTime();
				if (strDataComparativo != null && !strDataComparativo.equals("")){
					java.text.SimpleDateFormat sf = new java.text.SimpleDateFormat("dd/MM/yyyy"); 
					dataComparativo = sf.parse(strDataComparativo);
					
				}
				
				String dataComp = "";
				if(strDataComparativo == null || strDataComparativo.equals(""))
					dataComp = Pagina.trocaNullData(dataComparativo);
				else 
					dataComp = strDataComparativo;	
				
				
				//Pega a aba
				AbaDao abaDao = new AbaDao(request);
				Aba aba = new Aba();
				aba.setNomeAba("SITUACAO_PONTOS_CRITICOS");
				aba.setAbaSuperior("S");
				aba = (Aba)(abaDao.pesquisar(aba, new String[]{"codAba", "asc"}).get(0));
				
		
			%>
			<!-- CABEÇALHO DA TABELA -->
			<tr class="linha_subtitulo">
											
				<td width="55%" colspan="2"><%=aba.getLabelAba() %></td>
<!-- 			<td width="30%">Descrição</td>  -->
				<% //luana
				if("".equals(Pagina.getParamStr(request, "codSgaPontoCritico")) && exibirComboTipoPtc){%>
<!--					<td width="10%" align="center" nowrap>Tipo/Assunto</td>  -->
					<td width="10%" align="center" nowrap></td>
				<% } else { %>
					<td width="10%" align="center"></td>
				<%} %>
				
<!--				<td width="10%" align="center" nowrap>Limite</td> -->
				<td width="16%" align="center" nowrap></td>
				<td align="center" width="7%">Atual<br><%=Pagina.trocaNullData(dataAtual)%></td>
				<td align="center" width="7%">Comparativo<br><input type="text" name="dataComparativo" id ="dataComparativo" value="<%=dataComp%>" size="11" onkeyup="mascaraData(event, this);"></td>
			</tr>	

			<tr>
				<td class="espacador" colspan="7">
					<img src="<%=_pathEcar%>/images/pixel.gif">
				</td>
			</tr>



			
			
<%  




while((itListaOrgaos != null && itListaOrgaos.hasNext()) || ultimo) {
  	if(ultimo){
			orgao = null;
			ultimo = false;			
		}else{
			orgao = (OrgaoOrg) itListaOrgaos.next();
			if(!itListaOrgaos.hasNext()) {
				ultimo= true;	
			}
		} 
  	
  	if(ehSeparadoPorOrgao) {
  		//descobre a referencia correspondente ao orgao 
		arefOrgao =  acompReferenciaDao.getAcompReferenciaByOrgaoDiaMesAnoAref(orgao, aref);
  	} else {
  		arefOrgao =  aref;
  	}	
		
	if(arefOrgao != null) {
		
	  	if(ehSeparadoPorOrgao) {

	  		if(orgao!= null){
		  	%>	<table border="0" cellpadding="0" cellspacing="0" width="100%">
				<tr><td class="espacador" colspan="2"><img src="<%=_pathEcar%>/images/pixel.gif"></td></tr>
				<tr class="linha_subtitulo"><td width="55%" colspan="2">
			<% 	
	  			out.println(orgao.getDescricaoOrg());
	  		} 
	  		%>
	  			</td></tr>
	  			<tr class="linha_subtitulo"></tr>
				<tr><td class="espacador" colspan="2"><img src="<%=_pathEcar%>/images/pixel.gif"></td></tr>
				</table>
			<%
	
			if(!"".equals(strCodigosItem)){
				//monta a lista de itens que pertencem ao orgao
			  	String[] iettSelecionado = strCodigosItem.split(";");
			  	codigosItensSelecionados = "";
              	for(int i = 0; i < iettSelecionado.length; i++){
        			if(!"".equals(iettSelecionado[i])){
        				String strIett = iettSelecionado[i].substring(0, iettSelecionado[i].indexOf("_org"));
        				String strCodOrg = iettSelecionado[i].substring(iettSelecionado[i].indexOf("_org") +4 , iettSelecionado[i].length());
        				
        				//se o item pertencer ao orgao
        				if((orgao != null && orgao.getCodOrg().toString().equals(strCodOrg)) ||
        					//se o item for sem orgao
        					(orgao == null && strCodOrg.equals("X"))) {
        					if(codigosItensSelecionados != null && codigosItensSelecionados.equals(""))
        						codigosItensSelecionados =  strIett ;
        					else
        						codigosItensSelecionados =  codigosItensSelecionados + ";" + strIett ;
        				} 
        				
        			}
        		}
			}
			
			if(orgao== null && !"".equals(codigosItensSelecionados)){
			  	%>
				  	<table border="0" cellpadding="0" cellspacing="0" width="100%">
					<tr><td class="espacador" colspan="2"><img src="<%=_pathEcar%>/images/pixel.gif"></td></tr>
					<tr class="linha_subtitulo"><td width="55%" colspan="2">
				<% 
				out.println(configuracao.getLabelAgrupamentoItensSemOrgao());
			}				
			
			arisByIetts = acompReferenciaItemDao.getAcompReferenciaItemFilhosByIettComSeparacaoOrgao(codigosItensSelecionados, arefOrgao, orgao);
		} else {
			codigosItensSelecionados = strCodigosItem;
			arisByIetts = acompReferenciaItemDao.getAcompReferenciaItemFilhosByIett(strCodigosItem, arefOrgao, orgao);
		}
		
		
		arvoreItensSelecionados = new ArrayList();
		if(!arisByIetts.isEmpty()) {
			List listaIetts = new ArrayList();	
			for (Iterator iter = arisByIetts.iterator(); iter.hasNext();) {
				AcompReferenciaItemAri acompAri = (AcompReferenciaItemAri) iter.next();
				listaIetts.add(acompAri.getItemEstruturaIett());
			}

	        arvoreItensSelecionados = itemDao.getItensOrdenados(listaIetts, arefOrgao.getTipoAcompanhamentoTa());
	    	acompReferenciaItem = (AcompReferenciaItemAri) arisByIetts.get(0);
		} 
		
		strCodAri = acompReferenciaItem.getCodAri().toString();
		List listItensGravados = new ArrayList();
		listItensGravados = acompReferenciaItemDao.getListaItensAcompanhamento(arefOrgao);	
		
		
		
	codigosAriOrdenados = "";
	boolean habilitarCheckTodos = false;
	List itensApresentados = new ArrayList();
	Iterator itArvoreItensSelecionados = arvoreItensSelecionados.iterator();
	while(itArvoreItensSelecionados.hasNext()) {
	
	
		AtributoEstruturaListagemItens aeSelecionado = (AtributoEstruturaListagemItens) itArvoreItensSelecionados.next();
		ItemEstruturaIett iettSelecionado = aeSelecionado.getItem();
		AcompReferenciaItemAri ariSelecionado = null;
		
		Iterator iteraAri = arisByIetts.iterator();
		while(iteraAri.hasNext()) {
			AcompReferenciaItemAri ariAux = (AcompReferenciaItemAri) iteraAri.next();
			
			if(ariAux.getItemEstruturaIett().equals(iettSelecionado)) {
				ariSelecionado = ariAux;
			}
			if(ariSelecionado != null) {
				break;
			}
		}
		
		if(ariSelecionado == null) {
			continue;
		}
	
		
		// obter os itens filhos do item selecionado na tela
		List listaItensFilhos = new ArrayList();
		if(ehSeparadoPorOrgao)
			listaItensFilhos = acompReferenciaItemDao.getAcompReferenciaItemFilhosByAriComSeparacaoOrgao(ariSelecionado, orgao); 
		else 	
			listaItensFilhos = acompReferenciaItemDao.getAcompReferenciaItemFilhosByAri(ariSelecionado, orgao);
		
		// arvore para os itens
		Iterator itArvore = listaItensFilhos.iterator();
		List listaIetts = new ArrayList();
		
		
		while(itArvore.hasNext()){
			AcompReferenciaItemAri acompAri = (AcompReferenciaItemAri) itArvore.next();
			listaIetts.add(acompAri.getItemEstruturaIett());
		}
	    
	   List arvoreItens = itemDao.getItensOrdenados(listaIetts, arefOrgao.getTipoAcompanhamentoTa());
	
       Iterator it = arvoreItens.iterator();
		
	   while(it.hasNext()){
			AtributoEstruturaListagemItens aeListItens = (AtributoEstruturaListagemItens) it.next();
			ItemEstruturaIett iett = aeListItens.getItem();
			
			// controle devido a árvore
			if(!itensApresentados.contains(iett)){
				itensApresentados.add(iett);
			}
			else {
				continue;
			}
			
			//Identação pelo nível do item
			int nivel = iett.getNivelIett().intValue();
			
			String imgNivel = "";
			
			for(int i = 1; i < nivel; i++){
				imgNivel = imgNivel + "<img src=\"" + _pathEcar + "/images/pixel.gif\" width=\"22\" height=\"9\">";
			}	
			
			if(iett.equals(iettSelecionado)){
				codigosAriOrdenados += ariSelecionado.getCodAri().toString() + ";";
				
				
				if((iett.getPontoCriticoPtcs() != null && iett.getPontoCriticoPtcs().size() > 0 && (strRelogios != null && !strRelogios.equals("")) || (strTipoAssunto != null && !strTipoAssunto.equals("")))){
					Collection pontosCriticosAux = Collections.EMPTY_LIST;
					PontoCriticoDao pontoCriticoDaoAux = new PontoCriticoDao(request);
					pontosCriticosAux = pontoCriticoDaoAux.getAtivos(iett);
					flag= "";		
					Iterator itPCAux = pontosCriticosAux.iterator();
							
															
					while(itPCAux.hasNext()){
						PontoCriticoPtc pontoCriticoAux = (PontoCriticoPtc) itPCAux.next();	
						String[] relogioAtual = pontoCriticoDaoAux.getRelogioNaData(pontoCriticoAux, Data.getDataAtual());		
						if(strRelogios.contains(relogioAtual[0]) && selectedTipoPtC != null && selectedTipoPtC.getCodSatb().toString().equals(strTipoAssunto))
							flag = "S";					
								
						String[] relogioComparativo = pontoCriticoDaoAux.getRelogioNaData(pontoCriticoAux, dataComparativo);
						
						if(strRelogios.contains(relogioComparativo[0])  && selectedTipoPtC != null && selectedTipoPtC.getCodSatb().toString().equals(strTipoAssunto))
						
							flag = "S";				
					}				
				}
				if((strRelogios == null || "".equals(strRelogios) || strRelogios.contains("Proibido")) || (strRelogios != null && !"".equals(strRelogios) && "S".equals(flag))) {
				
	%>
						
				<tr class="cor_sim" onmouseover="javascript:destacaLinha(this,'over','')" onmouseout="javascript: destacaLinha(this,'out','cor_sim')" onClick="javascript: destacaLinha(this,'click','cor_sim')">
				
					<%
						if(iett.getPontoCriticoPtcs() != null && iett.getPontoCriticoPtcs().size() > 0){  
					%>	
						<td colspan="7" valign="top"> 
					<%
						}  
						else { %>
							<td colspan="3" valign="top">
						<%	} 	
					%>
						<table>
							<tr class="cor_sim" onmouseover="javascript:destacaLinha(this,'over','')" onmouseout="javascript: destacaLinha(this,'out','cor_sim')" onClick="javascript: destacaLinha(this,'click','cor_sim')">
								<td><%=imgNivel%><%=imgNivel%><%=imgNivel%>							
								<img align="right" src="<%=_pathEcar%>/images/icon_bullet_seta.png" onclick="javascript:agruparPontosCriticosItem('iett_<%=iett.getCodIett()%>')"></td>
								<td><%=aeListItens.getDescricao()%></td>
							</tr>
						</table>
					</td>	
					
					
					<% //aqui começa as sub-linhas
	
					
						if(iett.getPontoCriticoPtcs() != null && iett.getPontoCriticoPtcs().size() > 0 && listItensGravados.contains(iett)){
							
						%>
						
						</tr>
						
						<table  id="<%="iett_" + iett.getCodIett()%>" width="100%" >
						
						
						<%
							
							Collection pontosCriticos = Collections.EMPTY_LIST;
							PontoCriticoDao pontoCriticoDao = new PontoCriticoDao(request);
							pontosCriticos = pontoCriticoDao.getPontosCriticosAtivosOrdenadosDataLimite(iett, strTipoAssunto);
							
							Iterator itPC = pontosCriticos.iterator();	
							String corRelogio = "Branco";
							String descRelogio = "";
							String corRelogioComp = "Branco";
							String descRelogioComp = "";
							flagPrimeiroPt = "";
							int cont = 0;		
							boolean condicaoVerificacao = false;
							
							
							
							while(itPC.hasNext()){
								
								PontoCriticoPtc pontoCritico = (PontoCriticoPtc) itPC.next();
							
								String[] relogioAtual = pontoCriticoDao.getRelogioNaData(pontoCritico, Data.getDataAtual());							
								corRelogio = relogioAtual[0];
								descRelogio = relogioAtual[1];
								
								String[] relogioComparativo = pontoCriticoDao.getRelogioNaData(pontoCritico, dataComparativo);
								corRelogioComp = relogioComparativo[0];
								descRelogioComp = relogioComparativo[1]; 
								
								condicaoVerificacao = (strRelogios == null || "".equals(strRelogios) || "Proibido".equals(strRelogios) ) || (strRelogios != null && !strRelogios.equals("") && (strRelogios.contains(corRelogio) || strRelogios.contains(corRelogioComp))
								&& (("".equals(Pagina.getParamStr(request, "codSgaPontoCritico"))) || (pontoCritico.getSisAtributoTipo() != null && Pagina.getParamStr(request, "codSgaPontoCritico").equals(pontoCritico.getSisAtributoTipo().getCodSatb().toString()))));
								
						   
						   
						   	if (cont==0 && condicaoVerificacao) {
						   %>
						   					  					   
					    	<tr class="label">
								<td width="15%">&nbsp;</td>
								<td width="36%" colspan="2" align="left">Descrição</td>
								<td width="15%" align="center" nowrap>Tipo/Assunto</td>
								<td width="10%" align="center" nowrap>Limite</td>
								<td width="7%">&nbsp;</td>
								<td width="7%">&nbsp;</td>
							</tr>
				 	
								<tr><td>&nbsp;</td></tr>
						  
							<%
						   	}
							%>
								
								  <tr class="cor_sim" onmouseover="javascript:destacaLinha(this,'over','')" onmouseout="javascript: destacaLinha(this,'out','cor_sim')" onClick="javascript: destacaLinha(this,'click','cor_sim')">
								  <tr class="cor_sim" onmouseover="javascript:destacaLinha(this,'over','')" onmouseout="javascript: destacaLinha(this,'out','cor_sim')" onClick="javascript: destacaLinha(this,'click','cor_sim')">
								
								
								<%if(condicaoVerificacao){
									if(cont > 0){									
								%>					    		
												<td width="15%">&nbsp;</td>
												<td width="36%" colspan="2" align="left"><%=pontoCritico.getDescricaoPtc() + Pagina.getParamStr(request, "codSgaPontoCritico")%></td> 
									<%// } 
												
									%>												
													<td align="center" width="15%"> 
													<table>
														<tr >
															<td>
															<% if(exibirComboTipoPtc){
																	if(pontoCritico.getSisAtributoTipo() != null){
																		out.print(pontoCritico.getSisAtributoTipo().getDescricaoSatb() ); //aqui
																	}
																	else {
																		out.print("Não Informado");
																	}
																}
															%>
															</td>
														</tr>
													</table>
													</td>										
												<td align="center" width="10%">
												<%=Pagina.trocaNullData(pontoCritico.getDataLimitePtc())%>
												</td>
												<td align="center" width="7%"><%
												if(!"Proibido".equals(corRelogio))%>
													<a href="#pontoCritico<%=corRelogio%><%=pontoCritico.getCodPtc().toString()%>" name="pontoCritico<%=corRelogio%><%=pontoCritico.getCodPtc().toString()%>" onclick="abrePopUpPontosCriticos(<%=pontoCritico.getCodPtc().toString()%>)"><img border="0"  src="<%=_pathEcar%>/images/pc<%=corRelogio%>1.png" title="<%=descRelogio%>"></a>
												</td>
												<td align="center" width="7%"><%											
												if(!"Proibido".equals(corRelogioComp))%>
													<img border="0"  src="<%=_pathEcar%>/images/pc<%=corRelogioComp%>1.png" title="<%=descRelogioComp%>">
												</td>
									</tr>
	<%										
										flagPrimeiroPt = "F";
										
									}else{
									
										flagPrimeiroPt = "P";
										%>
										<!--tr class="cor_sim" onmouseover="javascript:destacaLinha(this,'over','')" onmouseout="javascript: destacaLinha(this,'out','cor_sim')" onClick="javascript: destacaLinha(this,'click','cor_sim')"-->
										<td width="15%">&nbsp;</td>
												<td width="36%" colspan="2" align="left"><%=pontoCritico.getDescricaoPtc()%></td>
											<td align="center" width="15%">  	
										 <%
																			
										if(exibirComboTipoPtc){
										%>		
									
											
											<%
											if(pontoCritico.getSisAtributoTipo() != null){
												out.print(pontoCritico.getSisAtributoTipo().getDescricaoSatb()	);
											}
											else {
												out.print("Não Informado");
											}
											%>
											
	<%
										} 
	%>									</td>
										
										<td align="center" width="10%">
											<%=Pagina.trocaNullData(pontoCritico.getDataLimitePtc())%>
										</td>
										<td align="center" width="7%"><%
										if(!"Proibido".equals(corRelogio))%>
											<a href="#pontoCritico<%=corRelogio%><%=pontoCritico.getCodPtc().toString()%>" name="pontoCritico<%=corRelogio%><%=pontoCritico.getCodPtc().toString()%>" onclick="abrePopUpPontosCriticos(<%=pontoCritico.getCodPtc().toString()%>)"><img border="0"  src="<%=_pathEcar%>/images/pc<%=corRelogio%>1.png" title="<%=descRelogio%>"></a>
										</td>
										
										<td align="center" width="7%"><%									
										if(!"Proibido".equals(corRelogioComp))%>
											<img border="0"  src="<%=_pathEcar%>/images/pc<%=corRelogioComp%>1.png" title="<%=descRelogioComp%>">
										</td>
										<!--/tr>-->
	<%
									}
								
									cont++;
								}
			//					% >
				//				< /tr>
								 
					//			< %
								
							}//fim do while
							
							%>
							</table>
							<%
							
						} else {
	%>						
	
						</tr>
							
						<table width="100%">
						<tr>
																	
							<%	if("".equals(Pagina.getParamStr(request, "codSgaPontoCritico"))){  %>
							<td  width="15%" align="center">  </td>
							<%  } else {%>
							<td width="15%" align="center"> - </td>
							<% } %>
							<td width="36%" align="center">	- </td>
							<td width="15%" align="center">	- </td>
							<td width="10%" align="center"> - </td>							<!-- <img src="../images/pcProibido1.png" title="Nenhum Ponto Crítico Cadastrado">-->
							<td width="7%" align="center">  </td>
							<td width="7%" align="center">  </td>
							
					<!--		<td align="center">
								 <img src="../images/pcProibido1.png" title="Nenhum Ponto Crítico Cadastrado">
								 
								 // Para não aparecer mais os relogios dos pontos criticos que não estavam cadastrados
							  // comentei as partes que carregavam a imagem na tela
							  // e deixei fixo na função javaScript  Relogios() o valor "Proibido" 						   
							  // pq senão a tela não carrega *se algum dia alguem for mexer tentar ver porque isso acontece   %>
							</td>
					-->
					
						</tr>
						</table>
																 
	<%
						}
						flag = "";
				}
	
			} else {
			
				AcompReferenciaItemAri ariFilho = acompReferenciaItemDao.getAriByIett(listaItensFilhos, iett);
				
				if(aeListItens.getItem().getPontoCriticoPtcs() != null && aeListItens.getItem().getPontoCriticoPtcs().size() > 0 && strRelogios != null && !strRelogios.equals("")){
					Collection pontosCriticosAux = Collections.EMPTY_LIST;
					PontoCriticoDao pontoCriticoDaoAux = new PontoCriticoDao(request);
					pontosCriticosAux = pontoCriticoDaoAux.getAtivos(aeListItens.getItem());
					flag= "";		
					Iterator itPCAux = pontosCriticosAux.iterator();
							
															
					while(itPCAux.hasNext()){
						PontoCriticoPtc pontoCriticoAux = (PontoCriticoPtc) itPCAux.next();	
						String[] relogioAtual = pontoCriticoDaoAux.getRelogioNaData(pontoCriticoAux, Data.getDataAtual());	
									
						if(strRelogios.contains(relogioAtual[0]) && selectedTipoPtC != null && selectedTipoPtC.getCodSatb().toString().equals(strTipoAssunto))
							flag = "S";					
								
						String[] relogioComparativo = pontoCriticoDaoAux.getRelogioNaData(pontoCriticoAux, dataComparativo);
						
						if(strRelogios.contains(relogioComparativo[0]) && selectedTipoPtC != null && selectedTipoPtC.getCodSatb().toString().equals(strTipoAssunto))
							flag = "S";				
					}				
				}
				if((strRelogios == null || strRelogios.equals("") || strRelogios.contains("Proibido")) || (strRelogios != null && !strRelogios.equals("") && flag.equals("S"))){
				
	%>
	
	
	
					<tr class="cor_sim" onmouseover="javascript:destacaLinha(this,'over','')" onmouseout="javascript: destacaLinha(this,'out','cor_sim')" onClick="javascript: destacaLinha(this,'click','cor_sim')">		
						<td colspan="3" valign="top">
							<table>
								<tr class="cor_sim" onmouseover="javascript:destacaLinha(this,'over','')" onmouseout="javascript: destacaLinha(this,'out','cor_sim')" onClick="javascript: destacaLinha(this,'click','cor_sim')">
									<td><%=imgNivel%><%=imgNivel%><%=imgNivel%>
									<img align="right" src="<%=_pathEcar%>/images/icon_bullet_seta.png" onclick="javascript:agruparPontosCriticosItem('iett_<%=iett.getCodIett()%>')"></td>
									<td><%=aeListItens.getDescricao()%>
														
									</td>
									
								</tr>
							</table>
						</td>
						
													
	<%
						if(aeListItens.getItem().getPontoCriticoPtcs() != null && aeListItens.getItem().getPontoCriticoPtcs().size() > 0 && listItensGravados.contains(aeListItens.getItem())){
							
						%>
						</tr>
						
						<table  id="<%="iett_" + iett.getCodIett()%>" width="100%">
																
						
						<%
							
							Collection pontosCriticos = Collections.EMPTY_LIST;
							PontoCriticoDao pontoCriticoDao = new PontoCriticoDao(request);
							pontosCriticos = pontoCriticoDao.getPontosCriticosAtivosOrdenadosDataLimite(aeListItens.getItem(), true);
							
							Iterator itPC = pontosCriticos.iterator();	
							String corRelogio = "Branco";
							String descRelogio = "";
							String corRelogioComp = "Branco";
							String descRelogioComp = "";
							flagPrimeiroPt = "";
							int cont = 0;		
														
							while(itPC.hasNext()){
								PontoCriticoPtc pontoCritico = (PontoCriticoPtc) itPC.next();	
							
								String[] relogioAtual = pontoCriticoDao.getRelogioNaData(pontoCritico, Data.getDataAtual());
								corRelogio = relogioAtual[0];
								descRelogio = relogioAtual[1];
								
								String[] relogioComparativo = pontoCriticoDao.getRelogioNaData(pontoCritico, dataComparativo);
								corRelogioComp = relogioComparativo[0];
								descRelogioComp = relogioComparativo[1];
								
								if((strRelogios == null || strRelogios.equals("")) || (strRelogios != null && !strRelogios.equals("") && (strRelogios.contains(corRelogio) || strRelogios.contains(corRelogioComp)))
										&& (("".equals(Pagina.getParamStr(request, "codSgaPontoCritico"))) || (pontoCritico.getSisAtributoTipo() != null && Pagina.getParamStr(request, "codSgaPontoCritico").equals(pontoCritico.getSisAtributoTipo().getCodSatb().toString())))){
									
									if (cont==0){
										
									%>
									<tr class="label">
										<td width="15%">&nbsp;</td>
										<td width="36%" colspan="2" align="left">Descrição</td>
										<td width="15%" align="center" nowrap>Tipo/Assunto</td>
										<td width="10%" align="center" nowrap>Limite</td>
										<td width="7%">&nbsp;</td>
										<td width="7%">&nbsp;</td>
									</tr>	
									<%	
									}
									
									if(cont > 0){
										if((flagPrimeiroPt != null && !flagPrimeiroPt.equals(""))){ 										
	%>
										
											<tr class="cor_sim" onmouseover="javascript:destacaLinha(this,'over','')" onmouseout="javascript: destacaLinha(this,'out','cor_sim')" onClick="javascript: destacaLinha(this,'click','cor_sim')">					
												<td width="15%">
													&nbsp;
												</td>
									<% } 
										
										
									%>		
											
											<td width="36%" colspan="2" align="left"><%=pontoCritico.getDescricaoPtc()%></td> 
											
											
												<td align="center" width="15%"> 
											<table>
																					
								
											<tr>
											<td align="center">
											<%
											if(exibirComboTipoPtc){
												if(pontoCritico.getSisAtributoTipo() != null){
													out.print(pontoCritico.getSisAtributoTipo().getDescricaoSatb());
												}
												else {
													out.print("Não Informado");
												}
											}
											%>
											</td>
											</tr>
											
											</table>
											
										</td>	
										
								
										<td align="center" width="10%">
										<%=Pagina.trocaNullData(pontoCritico.getDataLimitePtc())%>
										</td>
										<td align="center" width="7%"><%
										if(!"Proibido".equals(corRelogio))%>
											<a href="#pontoCritico<%=corRelogio%><%=pontoCritico.getCodPtc().toString()%>" name="pontoCritico<%=corRelogio%><%=pontoCritico.getCodPtc().toString()%>" onclick="abrePopUpPontosCriticos(<%=pontoCritico.getCodPtc().toString()%>)"><img border="0"  src="<%=_pathEcar%>/images/pc<%=corRelogio%>1.png" title="<%=descRelogio%>"></a>
										</td>
										<td align="center" width="7%"><%									
										if(!"Proibido".equals(corRelogioComp))%>
											<img border="0"  src="<%=_pathEcar%>/images/pc<%=corRelogioComp%>1.png" title="<%=descRelogioComp%>">
										</td>
									
									</tr>
									
	<%								
										flagPrimeiroPt = "F";
										cont++;
									}else{
									
										flagPrimeiroPt = "S";
										
	%>									
										
										
										<tr><td>&nbsp;</td></tr>
										
										<tr><td width="15%"></td>
										<td width="36%" colspan="2" align="left"><%=pontoCritico.getDescricaoPtc()%></td> 
										
										<td align="center" width="15%"> 
											<table>
												<tr >
													<td>
											
											<%
											if(exibirComboTipoPtc){ 
												if(pontoCritico.getSisAtributoTipo() != null){
													out.print(pontoCritico.getSisAtributoTipo().getDescricaoSatb());
												}
												else {
													out.print("Não Informado");
												}
											}
											%>
													</td>
												</tr>
											</table>
										</td>	
							
											
										<td align="center" width="10%">
											<%=Pagina.trocaNullData(pontoCritico.getDataLimitePtc())%>
										</td>
										<td align="center" width="7%"><%									
										if(!"Proibido".equals(corRelogio))%>
											<a href="#pontoCritico<%=corRelogio%><%=pontoCritico.getCodPtc().toString()%>" name="pontoCritico<%=corRelogio%><%=pontoCritico.getCodPtc().toString()%>" onclick="abrePopUpPontosCriticos(<%=pontoCritico.getCodPtc().toString()%>)"><img border="0"  src="<%=_pathEcar%>/images/pc<%=corRelogio%>1.png" title="<%=descRelogio%>"></a>
										</td>
										
										<td align="center" width="7%"><%									
										if(!"Proibido".equals(corRelogioComp))%>
											<img border="0"  src="<%=_pathEcar%>/images/pc<%=corRelogioComp%>1.png" title="<%=descRelogioComp%>">
										</td>
										</tr>
										
	<%									cont++;
									}
									
								}
								
							}//fim do while
							%>
						
					 	  
					 	  </table>
							
							<%
						}else{
	%>				
						</tr>
						<table width="100%">
						<tr>
																	
							<%	if("".equals(Pagina.getParamStr(request, "codSgaPontoCritico"))){  %>
							<td  width="15%" align="center">  </td>
							<%  } else {%>
							<td width="15%" align="center"> - </td>
							<% } %>
							<td width="36%" align="center">	- </td>
							<td width="15%" align="center">	- </td>
							<td width="10%" align="center"> - </td>							<!-- <img src="../images/pcProibido1.png" title="Nenhum Ponto Crítico Cadastrado">-->
							<td width="7%" align="center">  </td>
							<td width="7%" align="center">  </td>
							
					<!--		<td align="center">
								 <img src="../images/pcProibido1.png" title="Nenhum Ponto Crítico Cadastrado">
							</td>
					-->
						</tr>
						</table>
	<%
						}
						flag = "";
					}
					%>
					
					<%
				}
			}
%>
<%
		}
	}
  	
}
  		
	
%>
	<tr><td class="espacador" colspan="7"><img src="<%=_pathEcar%>/images/pixel.gif"></td></tr>

</table>

<input type="hidden" name="codigosAri" value="<%=codigosAriOrdenados%>">
<input type="hidden" name="dataAtual" id ="dataAtual" value="<%=Pagina.trocaNullData(dataAtual)%>">
<input type="hidden" name="codTipoAcompanhamento" value="<%=codTipoAcompanhamento%>">
<input type="hidden" name="codAri" value="<%=Pagina.getParamStr(request, "codAri")%>">	
<input type="hidden" name="itemDoNivelClicado" value="<%=Pagina.getParamStr(request, "itemDoNivelClicado")%>">	
<input type="hidden" name="mesReferencia" value="<%=Pagina.getParamStr(request, "mesReferencia")%>">
<input type="hidden" name="codigosItem" value="<%=Pagina.getParamStr(request, "codigosItem")%>">
<input type="hidden" name="primeiroCodAri" value="">

<input type="hidden" name="niveisPlanejamento" value="<%=niveisPlanejamento%>">
<input type="hidden" name="referencia_hidden" value="<%=mesReferencia%>">
<input type="hidden" name="primeiroIettClicado" value="<%=Pagina.getParamStr(request, "primeiroIettClicado")%>">
<input type="hidden" name="primeiroAriClicado" value="<%=Pagina.getParamStr(request, "primeiroAriClicado")%>">
<!-- Campo necessário para botões de Avança/Retrocede -->
<input type="hidden" name="codArisControle" value="<%=Pagina.getParamStr(request, "codArisControle")%>">
<input type="hidden" name="subNiveis" value="<%=Pagina.getParamStr(request, "subNiveis")%>">


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
<%@ include file="/include/ocultarAguarde.jsp"%>
<% /* Controla o estado do Menu (aberto ou fechado) */%>
<%@ include file="/include/estadoMenu.jsp"%>
<%@ include file="/include/mensagemAlert.jsp" %>
</html>