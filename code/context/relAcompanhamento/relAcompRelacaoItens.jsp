
<%@ include file="../login/validaAcesso.jsp"%>
<%@ include file="../frm_global.jsp"%>

<%@ taglib uri="/WEB-INF/ecar-util.tld" prefix="util" %>
<%@ taglib uri="/WEB-INF/ecar-combo.tld" prefix="combo" %>

<%@ page import="ecar.bean.AtributoEstruturaListagemItens" %>
<%@ page import="ecar.pojo.AcompReferenciaAref" %>
<%@ page import="ecar.dao.AcompReferenciaDao" %>
<%@ page import="ecar.pojo.ItemEstruturaIett" %>
<%@ page import="ecar.pojo.ItemEstruturaIettMin" %>  
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
 

<%@page import="comum.util.Util"%>
<%@page import="comum.util.ConstantesECAR"%>
<%@page import="ecar.pojo.TipoAcompFuncAcompTafc"%><html lang="pt-br">
<head> 
<%@ include file="../include/meta.jsp"%>
<%@ include file="/titulo.jsp"%>
<link rel="stylesheet" href="<%=_pathEcar%>/css/style_<%=configuracaoCfg.getEstilo().getNome()%>.css" type="text/css">
<script language="javascript" src="../js/menu_retratil.js"></script>
<script language="javascript" src="../js/focoInicial.js"></script>
<script language="javascript" src="../js/popUp.js"></script>
<script language="javascript" src="<%=_pathEcar%>/js/destaqueLinha.js"></script>

<script language="javascript">

function reload(){
	document.form.primeiraChamada.value="N";
	document.form.action = "relAcompRelacaoItens.jsp";
	document.form.submit();		
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

function Selecionar(){
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
    	alert("<%=_msg.getMensagem("relAcompanhamento.relAccess.relacaoItens.nenhumItem")%>");
   	}  	 
	else{
		document.form.arisSelecionados.value = selecionados;
		document.form.codAri.value = primeiroCodAri;
    	document.form.action = "relAcompFormato.jsp";
		document.form.submit();
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
	//Configuração	
	//ConfiguracaoCfg configuracao = new ConfiguracaoDao(request).getConfiguracao();
	String pathRaiz = configuracao.getRaizUpload();	

	TipoAcompanhamentoDao taDao = new TipoAcompanhamentoDao(request);
	
	String status = Pagina.getParamStr(request, "relatorio");
	String flag = "";
	int contador = 0;
	String codTipoAcompanhamento = Pagina.getParamStr(request, "codTipoAcompanhamento");
	boolean ehSeparadoPorOrgao = false;
	
	// controle para mostrar como default o primeiro tipo de acompanhamento das abas selecionado
	if("".equals(codTipoAcompanhamento)) {
		List listTa = taDao.getTipoAcompanhamentoAtivosComAcompanhamentoBySeqApresentacao();
		
		if(!listTa.isEmpty()) {
			codTipoAcompanhamento = ((TipoAcompanhamentoTa)listTa.get(0)).getCodTa().toString();
		} 
	}
	TipoAcompanhamentoTa tipoAcompanhamento = (TipoAcompanhamentoTa) taDao.buscar(TipoAcompanhamentoTa.class, Long.valueOf(codTipoAcompanhamento));
	
	if(tipoAcompanhamento != null && tipoAcompanhamento.getIndSepararOrgaoTa().equals("S")) {
		ehSeparadoPorOrgao = true;
	}	

%>
<body> 

<div id="conteudo">

<!--<%@ include file="/titulo_tela.jsp"%>-->

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
		chamarPagina="listaRelAcomp.jsp"
		tela="opcoes"
		caminho="<%=_pathEcar%>"
		gerarRelatorio="<%=Boolean.TRUE%>"
		exibirAbaFiltro="<%=Boolean.FALSE%>"
	/> 

	<form name="form" method="post">
	<%
	
	List itensBarra = new ArrayList();
	itensBarra.add("Modelos");
	itensBarra.add("Filtros");
	itensBarra.add("Formatos");
	
	int index = 1; //Filtros
	%>
	<util:barraLinksRelatorioItens
		itensBarra="<%=itensBarra%>"
		chamarPagina="relAcompOpcoes"
		indexAtivo="<%=index%>"
		semLinks="S"
	/>

	<br>

<%
	Dao dao = new Dao();

	AcompReferenciaItemDao acompReferenciaItemDao = new AcompReferenciaItemDao(request);
	SisAtributoDao sisAtributoDao = new SisAtributoDao(request);
	ItemEstruturaDao itemDao = new ItemEstruturaDao(request);
	AcompReferenciaDao acompReferenciaDao = new AcompReferenciaDao(request);
	TipoFuncAcompDao tipoFuncAcompDao = new TipoFuncAcompDao(request);
	EstruturaDao estruturaDao = new EstruturaDao(request);
	UsuarioDao usuarioDao = new UsuarioDao(request);
	OrgaoDao orgaoDao = new OrgaoDao(request);
	CorDao corDao = new CorDao(request);
	AbaDao abaDao = new AbaDao(request);
	//ConfiguracaoDao configuracaoDao = new ConfiguracaoDao(request);
	AcompReferenciaAref acompAref = null;
	List listOrgao = null;
	//String exigeLiberarAcompanhamento = configura.getIndLiberarAcompCfg();
	String exigeLiberarAcompanhamento = tipoAcompanhamento.getIndLiberarAcompTa();
	int qtdePeriodosAnteriores = 1;
	
	List tpfaOrdenadosPorEstrutura = tipoFuncAcompDao.getFuncaoAcompOrderByEstruturasAtivasInativas();
	
	List acompanhamentos = acompReferenciaDao.getListAcompReferenciaByTipoAcompanhamento(Long.valueOf(codTipoAcompanhamento));
	
	AcompReferenciaAref arefAtual = acompReferenciaDao.getAcompSelecionado(acompanhamentos);
	
	String mesReferencia = Pagina.getParamStr(request, "mesReferencia");
	if(!"".equals(mesReferencia)) {
		acompAref = (AcompReferenciaAref)acompReferenciaDao.buscar(AcompReferenciaAref.class, Long.valueOf(mesReferencia));
	}	
	
	String strLabelOrgao = configuracao.getLabelOrgao();
	
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
	String periodo = "1";
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
	
	OrgaoOrg orgaoResponsavel = null;
	if(!"".equals(Pagina.getParamStr(request, "orgaoResponsavel"))){
		orgaoResponsavel = (OrgaoOrg) dao.buscar(OrgaoOrg.class, Long.valueOf( Pagina.getParamStr(request, "orgaoResponsavel") ) );
	}
	boolean comboOrgao = true;
	String orgaoEscolhido = (orgaoResponsavel != null) ? orgaoResponsavel.getSiglaOrg() : "Todos";
	String periodoEscolhido = periodo + (("1".equals(periodo)) ? " Ciclo" : " Ciclos");

	String semInformacaoNivelPlanejamento = Pagina.getParamStr(request, "semInformacaoNivelPlanejamento");
	
	String filtroSituacoes = Pagina.getParamStr(request, "filtroSituacoes");
	String nivPlan = Pagina.getParamStr(request, "nivPlan");
	String filtroOEs = Pagina.getParamStr(request, "filtroOEs");
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
		<input type="hidden" name="mesReferencia" value="<%=mesReferencia%>">
		<input type="hidden" name="semInformacaoNivelPlanejamento" value="<%=semInformacaoNivelPlanejamento%>">
		<input type="hidden" name="arisSelecionados" value="">	
		<input type="hidden" name="opcaoModelo" value="<%=Pagina.getParamStr(request, "opcaoModelo")%>">
		
		<%
		// se não tiver tipo de acompanhamento selecionado, não carregar dados
		if(!"".equals(codTipoAcompanhamento)) {
		%>
		
			<table border="0" width="100%">
				<tr>
					<td valign="top" class="texto" align="left"> 
						<input type="button" value="Prosseguir" name="btProsseguir" class="botao" onclick="javascript:Selecionar();">
					</td>			
					<td align="right" class="texto">
						Data do relatório: <b><%=Pagina.trocaNullData(Data.getDataAtual())%></b>
						<br><br>
		<%
							String nomeReferencia = acompAref.getDiaAref() + "/" + acompAref.getMesAref() + "/" + acompAref.getAnoAref();
							
							if(ehSeparadoPorOrgao && acompReferenciaDao.getListaMesmaReferenciaDiaMesAno(acompAref).size() > 1){
								nomeReferencia = nomeReferencia + " - " + ConstantesECAR.LABEL_ORGAO_CONSOLIDADO;
							} else {
								nomeReferencia = nomeReferencia + " - " + acompAref.getNomeAref();
							}
	%>
						<b>Ciclo de referência: </b><%=nomeReferencia %>	
									
					</td>
				</tr>
			</table>
		
		<%
			String scriptComboSituacao = " onchange=\"reload()\"";
		
			if((mesReferencia != null && !"".equals(mesReferencia)) && !"".equals(periodo)){	
				List listNiveis = new ArrayList();
				String[] niveis = niveisPlanejamento.split(":");
				for(int n = 0; n < niveis.length; n++){
					String codNivel = niveis[n];
					
					if(!"".equals(codNivel)){
						listNiveis.add(sisAtributoDao.buscar(SisAtributoSatb.class, Long.valueOf(codNivel)));
					}
				}
				
				Long codArefReferencia = Long.valueOf(mesReferencia);
				Collection periodosConsideradosAgrupados = new ArrayList();
				Collection secretarias = new ArrayList();
				Collection periodosConsideradosListagem = new ArrayList();
				
				//Se o tipo de acompanhamento estiver configurada para separar por órgãos
				if (tipoAcompanhamento.getIndSepararOrgaoTa().equals("S")){
					//Guarda a rafarência selecionada tanto se veio do filtro quanto se veio do combo da tela
					AcompReferenciaAref referenciaSelecionada = (AcompReferenciaAref) acompReferenciaDao.buscar(AcompReferenciaAref.class, codArefReferencia);
					
					
					//Pega a lista de referências consideradas agrupadas por dia/mes/ano
					List comboReferencia = acompReferenciaDao.getComboReferencia(tipoAcompanhamento);
					periodosConsideradosAgrupados = acompReferenciaDao.getPeriodosAgrupadosConsiderados(comboReferencia, referenciaSelecionada, qtdePeriodosAnteriores);
												
					//Completa a lista de ciclos de referência com outas referências de mesmo dia/mes/ano das referências consideradas
					periodosConsideradosListagem = acompReferenciaDao.getListaMesmaReferenciaDiaMesAno(periodosConsideradosAgrupados);
					
					//se nao houver um orgao selecionado no combo.
					if(orgaoResponsavel != null && orgaoResponsavel.getCodOrg() != null) {
						secretarias.add(orgaoResponsavel);
					} else {
						//Recupera os órgãos da lista total de referências agrupadas
						secretarias = acompReferenciaDao.getOrgaosReferencias(periodosConsideradosListagem);
					}
				}
				else{
						 
					periodosConsideradosListagem = acompReferenciaDao.getPeriodosAnteriores(codArefReferencia, qtdePeriodosAnteriores,  Long.valueOf(codTipoAcompanhamento));
					periodosConsideradosAgrupados = periodosConsideradosListagem;
					if(orgaoResponsavel != null)
						secretarias.add(orgaoResponsavel);
				}
					
				List listFunAcomp = tipoFuncAcompDao.getTipoFuncAcompEmitePosicao();
				
				StatusRelatorioSrl statusLiberado = (StatusRelatorioSrl) itemDao.
		                         buscar(StatusRelatorioSrl.class, Long.valueOf(AcompReferenciaItemDao.STATUS_LIBERADO));
				
				boolean primeiro = true;	
				boolean ultimo = false;
				AcompReferenciaAref referenciaOrgaoAtual = null;
				
						
				Iterator itSecretarias = secretarias.iterator();
				
				//se nao tiver nenhuma secretaria, passa logo secretaria == null
				
		
				
				if(!itSecretarias.hasNext() && orgaoResponsavel== null) {
					ultimo = true;
				}
				
				
		%>		
				<table border="0" width="100%">
					<tr><td class="texto"><b>Selecione os itens que deseja imprimir:</b></td></tr>
				</table>
		<%
				
				while(itSecretarias.hasNext() || ultimo) {
					/* Na primeira vez, atribui null à secretaria para imprimir informação de periodos EM MONITORAMENTO */
					OrgaoOrg secretaria = null;
					String nomeOrgaoSepararPorOrgao = ""; 
								
					if(ultimo) {
						secretaria = null;
						ultimo = false;
					}else{
						secretaria = (OrgaoOrg) itSecretarias.next();
						nomeOrgaoSepararPorOrgao = "_org" + secretaria.getCodOrg();
						if(!itSecretarias.hasNext() && orgaoResponsavel == null) {
							ultimo= true;	
						}
					}
					
					listOrgao = new ArrayList();
					
					if(secretaria != null)
						listOrgao.add(secretaria);
				
					
					//referencia do combo de referencias (a referencia do combo pode nao ser a mesma referencia que esta sendo iterada baseada nos órgãos)
					//para cada órgão é uma referência diferente
					AcompReferenciaAref arefSelecionada = acompAref;	
					//guarda a lista de referencia que vao ser usadas para imprimir os pareceres
					Collection listaReferenciasOrgaoAtualCarinha = new ArrayList();
					
					if(ehSeparadoPorOrgao) {
                        
						 comboOrgao = false;
						// descobre a referencia que tem o mesmo dia,mes e ano da referencia do combo mas com orgao diferente
                        referenciaOrgaoAtual = acompReferenciaDao.getAcompReferenciaByOrgaoDiaMesAnoAref(secretaria, arefSelecionada);
                        //se existe uma referencia para o orgao atual, adiciona as duas listas
                        if(referenciaOrgaoAtual != null) {
                        	listaReferenciasOrgaoAtualCarinha.add(referenciaOrgaoAtual);
                        }	
                        
                   } else {
                        //se nao for separado por orgao, só vai existir uma referência. 
                        referenciaOrgaoAtual =  arefSelecionada;
                    }
					
			
					Boolean isSemInformacaoNivelPlanejamento = new Boolean(false);
					if("S".equals(semInformacaoNivelPlanejamento)) {
						isSemInformacaoNivelPlanejamento = new Boolean(true);
					}
					/* Recupera lista de itens para os periodos considerados por orgão responsavel */
					
					Long situacao = null;
					if(!"".equals(filtroSituacoes)){
						situacao = Long.valueOf(filtroSituacoes);
					}
					
					String indLiberado = "S";
					if("N".equals(exigeLiberarAcompanhamento)){
						indLiberado = "";
					}
					
					List lNP = new ArrayList();
					List<SisAtributoSatb> listNP = new SisGrupoAtributoDao().getAtributosOrdenados(configuracao.getSisGrupoAtributoSgaByCodSgaGrAtrNvPlan());
					
					if(!"".equals(nivPlan)) {
						lNP.add(sisAtributoDao.buscar(SisAtributoSatb.class, Long.valueOf(nivPlan)));
						isSemInformacaoNivelPlanejamento = false;
					}else {
						lNP.addAll(listNP);
						isSemInformacaoNivelPlanejamento = true;
					}
					
					Object itens[]  = acompReferenciaItemDao.getItensAcompanhamentoInPeriodosByOrgaoResp(
												periodosConsideradosListagem, lNP, listOrgao, 
												usuario, seguranca.getGruposAcesso(), 
												Long.valueOf(codTipoAcompanhamento), null,
												isSemInformacaoNivelPlanejamento, situacao, indLiberado, filtroOEs);
			 

					List itensAcompanhamentos = new ArrayList((Collection)itens[0]);
					List itensGeral = new ArrayList((Collection)itens[1]);
					
					EstruturaEtt ett = (EstruturaEtt) new EstruturaDao(null).localizar(EstruturaEtt.class, new Long(13));
					List<ItemEstruturaIettMin> siglasOE = new ItemEstruturaDao(null).getDistinctSigla(1, ett);
	
					/* Se for encontrado algum periodo, imprime lista de itens */
					if(itensAcompanhamentos != null && itensAcompanhamentos.size() > 0) {
					%>
						<br>
						<div id="subconteudo">
							<table border="0" cellpadding="0" cellspacing="0" width="100%">		
								<tr>						
									<td class="espacador" colspan="<%=periodosConsideradosAgrupados.size() + 6%>">
										<img src="../images/pixel.gif">
									</td>
								</tr>
								
								<!-- TÍTULO DA TABELA -->
								<tr class="linha_titulo">
							 		<td colspan="<%=periodosConsideradosAgrupados.size() + 6%>">
		<%
								 	if(secretaria != null) {
								 		out.println(secretaria.getDescricaoOrg());
								 	} else if(tipoAcompanhamento != null & tipoAcompanhamento.getIndSepararOrgaoTa() != null && tipoAcompanhamento.getIndSepararOrgaoTa().equals("S")){
								 		out.println(configuracao.getLabelAgrupamentoItensSemOrgao());
								 	}
		%>
								 	</td>						 						 	
								</tr>
		
								<tr>						
									<td class="espacador" colspan="<%=periodosConsideradosAgrupados.size() + 6%>">
										<img src="../images/pixel.gif">
									</td>
								</tr>
								
								<!-- CABEÇALHO DA TABELA -->
								<tr class="linha_subtitulo">
														
									<td width="40%" colspan="2">Acompanhamentos</td>
									
									<%
									if(comboOrgao){
										String scriptOE =  _disabled + " onchange=\"reload();\"";
									%>
									<td width="10%" align="center">OEs<br>
										<combo:ComboTag 
											nome="filtroOEs" 
											objeto="ecar.pojo.ItemEstruturaIettMin" 
											label="siglaIett" 
											value="siglaIett" 
											textoPadrao="Todos"
											order="siglaIett" 
											colecao="<%=siglasOE%>"
											selected="<%=filtroOEs%>"
											scripts="<%=scriptOE%>"
										/>
									</td>
									<td width="10%" align="center">Status<br>
										<combo:ComboTag 
											nome="filtroSituacoes" 
											objeto="ecar.pojo.Cor" 
											label="significadoCor" 
											value="codCor" 
											order="ordemCor" 
											textoPadrao="Todos" 
											selected="<%=filtroSituacoes%>"
											scripts="<%=scriptComboSituacao%>"
										/>
									</td>
									<%
									String scriptNP =  _disabled + " onchange=\"reload();\"";
									
									%>
									<td width="10%" align="center">
									Nível do Planejamento
													<combo:ComboTag 
															nome="nivPlan"
															objeto="ecar.pojo.SisAtributoSatb" 
															label="descricaoSatb" 
															value="codSatb" 
															order="codSatb"
															colecao="<%=listNP%>"
															scripts="<%=scriptNP%>"
															selected="<%=nivPlan%>"
															textoPadrao="Todos"
													/>
									</td>
									<td width="10%" align="center" nowrap>
										<%=strLabelOrgao%><br>
									<%
											comboOrgao = false;
											String scriptCombo =  _disabled + " onchange=\"reload();\"";
											
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
											
											if ( orgaoResponsavel != null ) {
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
											
											
											%>
											<td width="10%" align="center" nowrap></td>
											<td width="10%" align="center" nowrap><%=strLabelOrgao%></td>
											<% 
										/*	if ( orgaoResponsavel.getCodOrg() != null ) {
												out.println(orgaoResponsavel.getSiglaOrg());
											}else{
												out.println("Todos");
											}*/
										}
									%>
									</td>
		<%
								/* Imprime o nome dos ciclos */
								boolean ehPrimeiro = true;
								Iterator it = periodosConsideradosAgrupados.iterator();
								double tamanhoColuna = 38 / periodosConsideradosAgrupados.size();
								while(it.hasNext()){
									AcompReferenciaAref acompanhamento = (AcompReferenciaAref) it.next();
									int tamanhoListaMesmaReferencia = acompReferenciaDao.getListaMesmaReferenciaDiaMesAnoCount(acompanhamento);
									String estilo = "";
									if(ehPrimeiro){
										estilo = "class=\"corSelecao\"";
										ehPrimeiro = false;
									}
									String nomePeriodoReferencia = "";
									if(tamanhoListaMesmaReferencia > 1) {
										nomeReferencia = acompanhamento.getDiaAref() + "/" + acompanhamento.getMesAref() + "/" + acompanhamento.getAnoAref();
									} else {
										nomeReferencia = acompanhamento.getNomeAref();
									}
		%>
									<td align="center" id="selecionado" <%=estilo%> width="<%=tamanhoColuna%>%"><%=nomeReferencia%></td>
		<%							
								}					
		%>							
									<td align="center">
									Todos
		
									<% if(secretaria != null){ %>
										<input type="checkbox" class="form_check_radio" value="todos" id="<%=secretaria.getCodOrg()%>"	onClick="javascript:checkAll(<%=secretaria.getCodOrg()%>)">
										<input type="hidden" id="ComboClicado<%=secretaria.getCodOrg()%>" value="">
									<%} else {%>
										<input type="checkbox" class="form_check_radio" value="todos" id="<%=ConstantesECAR.ZERO%>" onClick="javascript:checkAll(<%=ConstantesECAR.ZERO%>)">
										<input type="hidden" id="ComboClicado<%=ConstantesECAR.ZERO%>" value="">
									<%}%>
									
									</td>
		
								</tr>	
		
								<tr>
									<td class="espacador" colspan="<%=periodosConsideradosAgrupados.size() + 6%>">
										<img src="../images/pixel.gif">
									</td>
								</tr>
								
								<!-- DADOS DA TABELA -->
		<%
								Map dadosGrafico = new HashMap();
								boolean skip = false;
								/* Itera pelos itens */
								Iterator itItens = itensAcompanhamentos.iterator();	
								while(itItens.hasNext()){
									
									AtributoEstruturaListagemItens aeIett = (AtributoEstruturaListagemItens) itItens.next();
									//ItemEstruturaIett item = (ItemEstruturaIett) itItens.next();
									ItemEstruturaIett item = aeIett.getItem();
									
									if(!filtroOEs.equals("")) {
										if(item.getSiglaIett().indexOf("OE") != -1) {
											if(item.getSiglaIett().equals(filtroOEs)) {
												skip = false;
											}else {
												skip = true;
											}
										}										
									}
									
									if(skip) {
										continue;
									}
		%>
									<tr class="cor_sim" onmouseover="javascript:destacaLinha(this,'over','')" onmouseout="javascript: destacaLinha(this,'out','cor_sim')" onClick="javascript: destacaLinha(this,'click','cor_sim')" bgcolor="<%=item.getEstruturaEtt().getCodCor3Ett()%>">
										<td colspan="5">
											<table >
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
												<td title="<%=item.getEstruturaEtt().getNomeEtt().trim()%>:<%=item.getNomeIett().trim()%>">
													<font color="<%=item.getEstruturaEtt().getCodCor4Ett()%>">
														<%="".equals(aeIett.getDescricao()) ? "[Não Informado]" : aeIett.getDescricao()%>
													</font>
												</td>
											</tr>
											</table>
										</td>
																
										<td align="center">
		<%
											/* Imprime nome do orgão responsável pelo item */
											if(item.getOrgaoOrgByCodOrgaoResponsavel1Iett() != null){
												if(item.getOrgaoOrgByCodOrgaoResponsavel1Iett().getSiglaOrg() != null)
													out.println(item.getOrgaoOrgByCodOrgaoResponsavel1Iett().getSiglaOrg());	
												else
													out.println(item.getOrgaoOrgByCodOrgaoResponsavel1Iett().getDescricaoOrg());	
											} //else {
												/* Se não possuir orgao procura orgao do pai */
												/*ItemEstruturaIett itemAux = item;
												while(itemAux != null && itemAux.getOrgaoOrgByCodOrgaoResponsavel1Iett() == null && itemAux.getItemEstruturaIett() != null){
													itemAux = itemAux.getItemEstruturaIett();
												}
												if(itemAux != null && itemAux.getOrgaoOrgByCodOrgaoResponsavel1Iett() != null){
													if(itemAux.getOrgaoOrgByCodOrgaoResponsavel1Iett().getSiglaOrg() != null)
														out.println(itemAux.getOrgaoOrgByCodOrgaoResponsavel1Iett().getSiglaOrg());	
													else
														out.println(itemAux.getOrgaoOrgByCodOrgaoResponsavel1Iett().getDescricaoOrg());											
												}*/
											//}
		%>
										</td>
		<%
										/* Apresentar os pareceres */
										Iterator itPeriodos = periodosConsideradosAgrupados.iterator();
										//Retorna um AREF na chave do Map e um ARI referente ao item no valor do MAP.
										Map map = null;
										//map utilizado para recuperar o Ari
										if(tipoAcompanhamento != null && tipoAcompanhamento.getIndSepararOrgaoTa() != null && tipoAcompanhamento.getIndSepararOrgaoTa().equals("S")) {
											map = acompReferenciaItemDao.criarMapPeriodoAriMontarListagem(periodosConsideradosAgrupados, listaReferenciasOrgaoAtualCarinha,  item);
									    } else {
									    	map = acompReferenciaItemDao.criarMapPeriodoAri(periodosConsideradosAgrupados, item);
									    }
										while(itPeriodos.hasNext()){ 
											AcompReferenciaAref acompReferencia = (AcompReferenciaAref) itPeriodos.next();
											
											if(map.isEmpty()){ %>
												<td align="center" colspan="2">&nbsp;</td> <%
											} else if(!map.containsKey(acompReferencia)) { %>
												<td align="center" valign="middle" colspan="2">
													<p title="Não foi solicitado acompanhamento">N/A</p>
												</td><%
											} else {
												flag = "acompanhado"; 
												AcompReferenciaItemAri ari = (AcompReferenciaItemAri) map.get(acompReferencia);										
		%>
												<td align="center" nowrap>
		<%
												if("N".equals(exigeLiberarAcompanhamento) || ari.getStatusRelatorioSrl().equals(statusLiberado)){
													//List relatorios = acompReferenciaItemDao.getAcompRelatorioArelOrderByFuncaoAcomp(ari, listFunAcomp);
													List relatorios = acompReferenciaItemDao.getAcompRelatorioArelOrderByFuncaoAcomp(ari, tpfaOrdenadosPorEstrutura);
													Iterator itRelatorios = relatorios.iterator();
		
													String aval = "";
													String imagePath = "";
													
													ValidaPermissao validaPermissao = new ValidaPermissao();
													List listaPermissaoTpfa = validaPermissao.permissaoVisualizarPareceres(tipoAcompanhamento,seguranca.getGruposAcesso());
													
													while(itRelatorios.hasNext()){												
															
														AcompRelatorioArel relatorio = (AcompRelatorioArel) itRelatorios.next();
														
														if(listaPermissaoTpfa.contains(relatorio.getTipoFuncAcompTpfa())) {
														//		&& validaPermissao.permissaoLeituraAcompanhamento(ari, seguranca.getUsuario(), seguranca.getGruposAcesso())){
															UsuarioUsu usuarioImagem = null;  
										            		String hashNomeArquivo = null;
															
															// Por Rogério (05/10/2007)
															// Modificada a forma de busca da imagem relacionada com a Cor.
															// Referente ao Mantis #7442
		
															boolean imageError = false;
															if( (Dominios.SIM).equals(relatorio.getIndLiberadoArel()) ) {
																Cor cor = ( relatorio.getCor()!=null ? relatorio.getCor() : null );
																TipoFuncAcompTpfa tpfa = ( relatorio.getTipoFuncAcompTpfa() != null ? relatorio.getTipoFuncAcompTpfa() : null );
																														
																imagePath = corDao.getImagemPersonalizada(cor, tpfa, "L");
																
																if( imagePath != null && imagePath.trim().length()>0) {
																	
																	usuarioImagem = ((ecar.login.SegurancaECAR)request.getSession().getAttribute("seguranca")).getUsuario(); 
											    					hashNomeArquivo = Util.calcularHashNomeArquivo(pathRaiz, imagePath);
											    					Util.adicionarMapArquivosAtuaisUsuarios(usuarioImagem, hashNomeArquivo, pathRaiz, imagePath);
																	
																	/* -- As tags do CSS "max-width" e "max-height" não funcionam no IE6 ou menor -- */ 
																    aval += "<img border=\"0\" src=\"" + request.getContextPath() + "/DownloadFile?tipo=open&RemoteFile=";
																    aval += hashNomeArquivo + "\" style=\"width: 23px; height: 23px;\" title=\"" + relatorio.getTipoFuncAcompTpfa().getLabelTpfa() + "\">";
																} else {
																	if( relatorio.getCor() != null && relatorio.getCor().getCodCor() != null ) { 
																		aval += "<img border=\"0\" src=\"" + _pathEcar + "/images/relAcomp/";
																		aval += corDao.getImagemRelatorio(relatorio.getCor(), relatorio.getTipoFuncAcompTpfa()) + "\" title=\"";
																		aval += relatorio.getTipoFuncAcompTpfa().getLabelTpfa() + "\" >";
																	} else {
																		imageError = true;
																	}
																}
															} else {
																imageError = true;
															}
																													
															// Verifica se o parecer é obrigatorio ou opcional
															List listTipoAcompFuncAcomp = new ArrayList(acompReferencia.getTipoAcompanhamentoTa().getTipoAcompFuncAcompTafcs());
															boolean ehObrigatorio = true;
															if(listTipoAcompFuncAcomp != null) {
																Iterator tipoFuncAcompIt = listTipoAcompFuncAcomp.iterator();
																while(tipoFuncAcompIt.hasNext()) {
																	TipoAcompFuncAcompTafc tipoAcompFuncAcompTafc = (TipoAcompFuncAcompTafc)tipoFuncAcompIt.next();
																	if(	relatorio.getTipoFuncAcompTpfa().getCodTpfa().equals(tipoAcompFuncAcompTafc.getTipoFuncAcompTpfa().getCodTpfa())
																		&& tipoAcompFuncAcompTafc.getIndRegistroPosicaoTafc() != null 
																		&& tipoAcompFuncAcompTafc.getIndRegistroPosicaoTafc().equals(Dominios.OPCIONAL)) {
																		ehObrigatorio = false;
																	}
																}
															}
															
															if( imageError && ehObrigatorio) {
																aval += "<img border=\"0\" src=\"" + _pathEcar + "/images/relAcomp/";
																aval += corDao.getImagemRelatorio(null, relatorio.getTipoFuncAcompTpfa()) + "\" title=\"";
																aval += relatorio.getTipoFuncAcompTpfa().getLabelTpfa() + "\" >";
															}													
														}																																								
													}
													
													out.println(aval);
												} else {
													if("N".equals(exigeLiberarAcompanhamento) || (ari.getAcompRelatorioArels() != null && ari.getAcompRelatorioArels().size() > 0)) {
													%>	
														<p title="Não liberado">N/L</p>
													<%
													}
													else {
														flag = "";
														
													%>
														<!-- p title="Não foi solicitado acompanhamento">N/A</p-->&nbsp;
													<%
													}
												} %>
												</td>
												<td align="center"><input type="checkbox" class="form_check_radio" id="<%if(secretaria != null)%><%=secretaria.getCodOrg()%>" value="<%=ari.getCodAri()%>"></td> <%
											}
										} %>										
									</tr> <%
									contador++;
								}
		%>
		
								<tr class="linha_subtitulo2">
									<td colspan="<%=periodosConsideradosAgrupados.size() + 6%>">
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
			
		
			if(contador < 1){
				List orgaos2 = (List) session.getAttribute("orgaosPrimeiraChamada");
				
				String scriptCombo2 = _disabled + " onchange=\"reload();\"";
		%>
				<br>
				<br>
				<table border="0" width="100%">
<%--					<tr><td class="texto" align="center"><b><%=_msg.getMensagem("posicaoGeral.filtro.nenhumRegistro")%></b></td></tr>--%>
					<tr><td></td></tr>
					<tr><td></td></tr>
					<tr>
						<td class="texto">
							<b>Selecione o orgão responsável:</b>
					<%
							if (orgaoResponsavel!= null) {
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
						<td class="texto"><b>Selecione a situação:</b>
						<combo:ComboTag 
							nome="filtroSituacoes" 
							objeto="ecar.pojo.Cor" 
							label="significadoCor" 
							value="codCor" 
							order="ordemCor" 
							textoPadrao="Todos" 
							selected="<%=filtroSituacoes%>"
							scripts="<%=scriptComboSituacao%>"
						/>
						<%
									String scriptNP =  _disabled + " onchange=\"reload();\"";
									List listNP = new SisGrupoAtributoDao().getAtributosOrdenados(configuracao.getSisGrupoAtributoSgaByCodSgaGrAtrNvPlan());
									%>
									<td width="10%" align="center">
									Nível do Planejamento
													<combo:ComboTag 
															nome="nivPlan"
															objeto="ecar.pojo.SisAtributoSatb" 
															label="descricaoSatb" 
															value="codSatb" 
															order="codSatb"
															colecao="<%=listNP%>"
															scripts="<%=scriptNP%>"
															selected="<%=nivPlan%>"
															textoPadrao="Todos"
													/>
									<td/>
					<tr>
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