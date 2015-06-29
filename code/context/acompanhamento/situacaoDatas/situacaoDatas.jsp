
<jsp:directive.page import="ecar.dao.AbaDao"/>
<jsp:directive.page import="ecar.pojo.Aba"/>
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


	// Request
	String codIettRelacao = Pagina.getParamStr(request, "codIettRelacao");
	String strCodAri = Pagina.getParamStr(request, "codAri");
	String referencia= Pagina.getParamStr(request, "referencia");
	String linkControle = Pagina.getParamStr(request, "linkControle");
	String codTipoAcompanhamento = Pagina.getParamStr(request, "codTipoAcompanhamento");
	String itemDoNivelClicado = Pagina.getParamStr(request, "itemDoNivelClicado");
	String mesReferencia = Pagina.getParamStr(request, "referencia_hidden");
	
	UsuarioUsu usuario = ((ecar.login.SegurancaECAR)session.getAttribute("seguranca")).getUsuario();
	request.setAttribute("exibirAguarde", "true");
	
	
	AcompReferenciaItemDao acompReferenciaItemDao = new AcompReferenciaItemDao(request);
	ItemEstruturaDao itemDao = new ItemEstruturaDao(request);
    
	
	
	AcompReferenciaItemAri ari = null;
	if(!strCodAri.equals(""))
		ari = (AcompReferenciaItemAri) new AcompReferenciaItemDao(request).buscar(AcompReferenciaItemAri.class, Long.valueOf(strCodAri));
	

	
	OrgaoOrg orgaoResponsavel = new OrgaoOrg();
	if(!"".equals(Pagina.getParamStr(request, "orgaoResponsavel"))){
		orgaoResponsavel = (OrgaoOrg) acompReferenciaItemDao.buscar(OrgaoOrg.class, Long.valueOf( Pagina.getParamStr(request, "orgaoResponsavel") ) );
	}
	
	final String strDataComparativo = request.getParameter("dataComparativo");
	final String strRelogios = request.getParameter("relogios");
	final String strTipoAssunto = request.getParameter("tipoAssunto");
	String strCodigosItem = Pagina.getParamStr(request, "codigosItem");
	String flag = "";
	String flagPrimeiroPt = "";

	if("".equals(strCodigosItem) && strCodAri != null && !strCodAri.equals("") ) {
		// buscar o item do ARI corrente
		AcompReferenciaItemAri acompReferenciaItem = (AcompReferenciaItemAri) acompReferenciaItemDao.buscar(AcompReferenciaItemAri.class, Long.valueOf(strCodAri));
		strCodigosItem = acompReferenciaItem.getItemEstruturaIett().getCodIett().toString() + ";";
	} 
	
	AcompReferenciaAref aref = null;
	
	if(!"".equals(Pagina.getParamStr(request, "referencia_hidden"))) {
		aref = (AcompReferenciaAref) new AcompReferenciaDao(null).buscar(AcompReferenciaAref.class, Long.valueOf(Pagina.getParamStr(request, "referencia_hidden")));
	} else {
	
		if(mesReferencia != null & !mesReferencia.equals(""))
			aref = (AcompReferenciaAref) new AcompReferenciaDao(null).buscar(AcompReferenciaAref.class, Long.valueOf(Pagina.getParamStr(request, "mesReferencia")));
		else if(ari != null)
			aref = ari.getAcompReferenciaAref();
	}
	
	List arisByIetts = acompReferenciaItemDao.getAcompReferenciaItemFilhosByIett(strCodigosItem, aref, orgaoResponsavel);
	
	AcompReferenciaItemAri acompReferenciaItem;
	List arvoreItensSelecionados = new ArrayList();
	if(!arisByIetts.isEmpty()) {
	
		List listaIetts = new ArrayList();	
		
		for (Iterator iter = arisByIetts.iterator(); iter.hasNext();) {
			AcompReferenciaItemAri acompAri = (AcompReferenciaItemAri) iter.next();
			listaIetts.add(acompAri.getItemEstruturaIett());
		}

        arvoreItensSelecionados = itemDao.getItensOrdenados(listaIetts, aref.getTipoAcompanhamentoTa());
    	acompReferenciaItem = (AcompReferenciaItemAri) arisByIetts.get(0);
	} else {
		acompReferenciaItem = (AcompReferenciaItemAri) acompReferenciaItemDao.buscar(AcompReferenciaItemAri.class, Long.valueOf(strCodAri));
	}
	
	strCodAri = acompReferenciaItem.getCodAri().toString();
	
	TipoFuncAcompDao tipoFuncAcompDao = new TipoFuncAcompDao(request);
	AcompReferenciaAref arefSelecionado = null;
	arefSelecionado = ari.getAcompReferenciaAref();
	List tpfaOrdenadosPorEstrutura = tipoFuncAcompDao.getFuncaoAcompOrderByEstruturas();
		
	ValidaPermissao validaPermissao = new ValidaPermissao();
	/* permissão de visualização de parecer */
	boolean permissaoConsultarParecer = false;
	if (validaPermissao.permissaoConsultaParecerIETT(ari.getItemEstruturaIett().getCodIett(), seguranca)
		|| validaPermissao.permissaoConsultaIETT(ari.getItemEstruturaIett().getCodIett(), seguranca)) {
		permissaoConsultarParecer = true;
	}
	
	// essa variavel é utilizada no include do cabecalho das paginas
	String niveisPlanejamento = Pagina.getParamStr(request, "niveisPlanejamento");
	AcompReferenciaItemDao ariDao = new AcompReferenciaItemDao(request);
	ItemEstruturaDao itemEstruturaDao = new ItemEstruturaDao(request);
	ItemEstruturaIett itemEstrutura = null;
	
	if(arefSelecionado != null && !itemDoNivelClicado.trim().equals("") && itemDoNivelClicado != null ){
		itemEstrutura = (ItemEstruturaIett)itemEstruturaDao.buscar(ItemEstruturaIett.class, Long.valueOf(itemDoNivelClicado));
	} else {
		// Pra nao dar erro quando perder codTipoAcompanhamento
		// descobre pelo Ari
		if(ari != null)
			itemEstrutura = (ItemEstruturaIett) itemEstruturaDao.buscar(ItemEstruturaIett.class, ari.getItemEstruturaIett().getCodIett());
	}
	
%>


<%@page import="comum.util.Util"%><html lang="pt-br"> 
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
	document.forms[0].clicouAba.value = "S";
	document.forms[0].submit();
}
function trocaAba(link, codAri){
	document.forms[0].codAri.value = codAri;
	document.forms[0].action = link;
	document.forms[0].clicouAba.value = "S";
	document.forms[0].submit();
}

function trocaAbaSemAri(link, codIettRelacao){
	document.form.itemDoNivelClicado.value = codIettRelacao;
	document.form.action = link;
	document.forms[0].clicouAba.value = "S";
	document.form.submit();
}

function trocaAbaImpressao(link){
	document.forms[0].action = link;
	document.forms[0].clicouAba.value = "S";
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

var formPrincipal ; // document.getElementById('formPrincipal');

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
	
	} else if(!validaData(formPrincipal.dataComparativo,false,true,true)){
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
	
<% 	
	
	String periodo = Pagina.getParamStr(request, "periodo");
	arefSelecionado = ari.getAcompReferenciaAref();
	String funcaoSelecionada = "SITUACAO_PONTOS_CRITICOS";
	
	// Configuração	
	//ConfiguracaoCfg configuracao = new ConfiguracaoDao(request).getConfiguracao();
	String pathRaiz = configuracao.getRaizUpload();	
%>

	<%@ include  file="../form_registro.jsp" %>		
	
<%
	boolean exibirComboTipoPtc = configuracao.getSisGrupoAtributoSgaTipoPontoCritico() != null;

%>
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
	
	
	
	<!-- Campos para manter a seleção em Posição Geral -->

<!-- ESCOLHER UMA VARIAVEL PRA GUARDAR -->
<input type="hidden" name="codTipoAcompanhamento" value="<%=codTipoAcompanhamento%>">
<input type="hidden" name="referencia_hidden" value="<%=Pagina.getParamStr(request, "referencia_hidden")%>">
<input type="hidden" name="codAcomp" value="<%=Pagina.getParamStr(request, "codAcomp")%>">
<input type="hidden" name="periodo" value="<%=Pagina.getParamStr(request, "periodo")%>">
<input type="hidden" name="mesReferencia" value="<%=ari.getAcompReferenciaAref().getCodAref().toString()%>">
<input type="hidden" name="niveisPlanejamento" value="<%=Pagina.getParamStr(request, "niveisPlanejamento")%>">
<input type="hidden" name="primeiroIettClicado" value="<%=Pagina.getParamStr(request, "primeiroIettClicado")%>">
<input type="hidden" name="primeiroAriClicado" value="<%=Pagina.getParamStr(request, "primeiroAriClicado")%>">
<input type="hidden" name="codIettRelacao" value="<%=Pagina.getParamStr(request, "codIettRelacao")%>">
<input type="hidden" name="itemDoNivelClicado" value="<%=Pagina.getParamStr(request, "itemDoNivelClicado")%>">
<input type="hidden" name="codigosItem" value="<%=Pagina.getParamStr(request, "codigosItem")%>">
<input type="hidden" name="relogios" value="">
<input type="hidden" name="tipoAssunto" value="">
<!-- Campo necessario para guardar a forma de visualização escolhida no filtro -->
<input type="hidden" name="hidFormaVisualizacaoEscolhida" value='<%=Pagina.getParamStr(request, "hidFormaVisualizacaoEscolhida")%>'>
<!-- Campo necessário para botões de Avança/Retrocede -->
<input type="hidden" name="codArisControle" value="<%=Pagina.getParamStr(request, "codArisControle")%>">
<!-- Campos para manter a seleção em Posição Geral -->
<input type="hidden" name="codAri" value="<%=Pagina.getParamStr(request, "codAri")%>">
<input type="hidden" name="hidItemAberto" value="<%=Pagina.getParamStr(request, "hidItemAberto")%>">
<!-- PODE SER QUE USE -->
<input type=hidden name="primeiroAcomp" value="<%=Pagina.getParamStr(request, "primeiroAcomp")%>">
<input type="hidden" name="clicouAba" value="<%= Pagina.getParamStr(request, "clicouAba")%>">
<input type="hidden" name="tipoPadraoExibicaoAba" value="<%= Pagina.getParamStr(request, "tipoPadraoExibicaoAba")%>">
	
<table border="0" cellpadding="0" cellspacing="0" width="100%">
	<tr><td class="espacador" colspan="2"><img src="<%=_pathEcar%>/images/pixel.gif"></td></tr>
	<tr class="linha_subtitulo">		
	</tr>
	<tr><td class="espacador" colspan="2"><img src="<%=_pathEcar%>/images/pixel.gif"></td></tr>
</table>
	
<table border="0" cellpadding="0" cellspacing="0" width="100%">	
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
				aba.setAbaSuperior("N");
				aba = (Aba)(abaDao.pesquisar(aba, new String[]{"codAba", "asc"}).get(0));
			%>
			<!-- CABEÇALHO DA TABELA -->
			<tr class="linha_subtitulo">
											
				<td width="55%" colspan="2"><%=abaDao.getLabelAbaEstrutura(aba, ari.getItemEstruturaIett().getEstruturaEtt()) %> </td>
				<% 
				if("".equals(Pagina.getParamStr(request, "codSgaPontoCritico")) && exibirComboTipoPtc){%>
					<td width="10%" align="center" nowrap></td>
				<% } else { %>
					<td width="10%" align="center"></td>
				<%} %>
				
				<td width="16%" align="center" nowrap></td>
				<td align="center" width="7%">Atual<br><%=Pagina.trocaNullData(dataAtual)%></td>
				<td align="center" width="7%">Comparativo<br><input type="text" name="dataComparativo" id ="dataComparativo" value="<%=dataComp%>" size="11" onkeyup="mascaraData(event, this);" ></td>
			</tr>	

			<tr>
				<td class="espacador" colspan="7">
					<img src="<%=_pathEcar%>/images/pixel.gif">
				</td>
			</tr>	
<%  
String codigosAriOrdenados = "";
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
	List lista = acompReferenciaItemDao.getAcompReferenciaItemFilhosByAri(ariSelecionado, orgaoResponsavel);
	
	// arvore para os itens
	Iterator itArvore = lista.iterator();
	List listaIetts = new ArrayList();
	
	while(itArvore.hasNext()){
		AcompReferenciaItemAri acompAri = (AcompReferenciaItemAri) itArvore.next();
		listaIetts.add(acompAri.getItemEstruturaIett());
	}
    
    List arvoreItens = itemDao.getItensOrdenados(listaIetts, aref.getTipoAcompanhamentoTa());

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
			
			if(iett.getPontoCriticoPtcs() != null && iett.getPontoCriticoPtcs().size() > 0 && (strRelogios != null && !strRelogios.equals("")) || (strTipoAssunto != null && !strTipoAssunto.equals(""))){
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
							<td><%=aeListItens.getDescricao()%> </td>
						</tr>
					</table>
				</td>	
				
				<% //aqui começa as sub-linhas
					if(iett.getPontoCriticoPtcs() != null && iett.getPontoCriticoPtcs().size() > 0){
																		
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
						
						
						if (pontosCriticos != null && pontosCriticos.size() > 0){
								
						%>			
						
						</tr>
						
						<table width="100%" id="<%="iett_" + iett.getCodIett()%>" >
									
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
														
						while(itPC.hasNext()){
							PontoCriticoPtc pontoCritico = (PontoCriticoPtc) itPC.next();
						
							String[] relogioAtual = pontoCriticoDao.getRelogioNaData(pontoCritico, Data.getDataAtual());							
							corRelogio = relogioAtual[0];
							descRelogio = relogioAtual[1];
							
							String[] relogioComparativo = pontoCriticoDao.getRelogioNaData(pontoCritico, dataComparativo);
							corRelogioComp = relogioComparativo[0];
							descRelogioComp = relogioComparativo[1]; %>
							
							<tr class="cor_sim" onmouseover="javascript:destacaLinha(this,'over','')" onmouseout="javascript: destacaLinha(this,'out','cor_sim')" onClick="javascript: destacaLinha(this,'click','cor_sim')"><tr class="cor_sim" onmouseover="javascript:destacaLinha(this,'over','')" onmouseout="javascript: destacaLinha(this,'out','cor_sim')" onClick="javascript: destacaLinha(this,'click','cor_sim')">
							
							
							<%if((strRelogios == null || "".equals(strRelogios) || "Proibido".equals(strRelogios) ) || (strRelogios != null && !strRelogios.equals("") && (strRelogios.contains(corRelogio) || strRelogios.contains(corRelogioComp)))
									&& (("".equals(Pagina.getParamStr(request, "codSgaPontoCritico"))) || (pontoCritico.getSisAtributoTipo() != null && Pagina.getParamStr(request, "codSgaPontoCritico").equals(pontoCritico.getSisAtributoTipo().getCodSatb().toString())))){
								if(cont > 0){
									//if(flagPrimeiroPt != null && !flagPrimeiroPt.equals("")){ 									
							%>									    		
											<td width="15%">&nbsp;</td>
											<td width="36%" colspan="2" align="left"><%=pontoCritico.getDescricaoPtc()%></td> 
								<% // } 
											
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
							}
		//					% >
			//				< /tr>
				//			< %
							cont++;
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
						<td width="36%" align="center" colspan="2">	- </td>
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
		
			AcompReferenciaItemAri ariFilho = acompReferenciaItemDao.getAriByIett(lista, iett);
			
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
					<td valign="top">
						<table>
							<tr class="cor_sim" onmouseover="javascript:destacaLinha(this,'over','')" onmouseout="javascript: destacaLinha(this,'out','cor_sim')" onClick="javascript: destacaLinha(this,'click','cor_sim')">
								<td><%=imgNivel%><%=imgNivel%><%=imgNivel%>
								<img align="right" src="<%=_pathEcar%>/images/icon_bullet_seta.png" onclick="javascript:agruparPontosCriticosItem('iett_<%=iett.getCodIett()%>')"></td>
								<td><%=aeListItens.getDescricao()%></td>
							</tr>
						</table>
					</td>
									
<%
					if(aeListItens.getItem().getPontoCriticoPtcs() != null && aeListItens.getItem().getPontoCriticoPtcs().size() > 0){
						
						%>
					</tr>
					
					<table width="100%" id="<%="iett_" + iett.getCodIett()%>" >
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
								if(cont > 0){
									if(flagPrimeiroPt != null && !flagPrimeiroPt.equals("")){ 										
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
									<tr >
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

									<tr class="label">
										<td width="15%">&nbsp;</td>
										<td width="36%" colspan="2" align="left">Descrição</td>
										<td width="15%" align="center" nowrap>Tipo/Assunto</td>
										<td width="10%" align="center" nowrap>Limite</td>
										<td width="7%">&nbsp;</td>
										<td width="7%">&nbsp;</td>
									</tr>
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
			}
		}
%>
<%
}
%>
	<tr><td class="espacador" colspan="7"><img src="<%=_pathEcar%>/images/pixel.gif"></td></tr>

</table>

<input type="hidden" name="codigosAri" value="<%=codigosAriOrdenados%>">
<input type="hidden" name="dataAtual" id ="dataAtual" value="<%=Pagina.trocaNullData(dataAtual)%>">
<input type="hidden" name="codTipoAcompanhamento" value="<%=codTipoAcompanhamento%>">
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