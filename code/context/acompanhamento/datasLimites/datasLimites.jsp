<jsp:directive.page import="ecar.util.Dominios"/>
<jsp:directive.page import="ecar.pojo.SisAtributoSatb"/>
<jsp:directive.page import="ecar.dao.SisAtributoDao"/>
<jsp:directive.page import="ecar.pojo.AcompReferenciaAref"/>
<%@ include file="/login/validaAcesso.jsp"%>
<%@ include file="/frm_global.jsp"%>
 
<%@ page import="ecar.pojo.AcompReferenciaItemAri" %> 
<%@ page import="ecar.pojo.ItemEstruturaIett" %>
<%@ page import="ecar.pojo.UsuarioUsu" %>
<%@ page import="ecar.pojo.AcompRefItemLimitesArli" %>
<%@ page import="ecar.pojo.TipoFuncAcompTpfa" %>
<%@ page import="ecar.pojo.ItemEstUsutpfuacIettutfa" %>
<%@ page import="ecar.dao.AcompReferenciaItemDao" %>
<%@ page import="ecar.dao.ItemEstruturaDao" %>
<%@ page import="ecar.dao.EstruturaAtributoDao" %>
<%@ page import="ecar.dao.UsuarioDao" %>
<%@ page import="ecar.dao.ItemEstUsutpfuacDao" %>
<%@ page import="ecar.pojo.ConfigMailCfgm" %>
<%@ page import="ecar.dao.ConfigMailCfgmDAO" %>
<%@ page import="comum.util.Util" %>
<%@ page import="comum.util.FileUpload" %>
<%@ page import="comum.util.Data" %>

<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.List" %> 
<%@ page import="java.util.Set" %> 
<%@ page import="java.util.Iterator" %> 

<%@ taglib uri="/WEB-INF/ecar-util.tld" prefix="util" %>
<%@ taglib uri="/WEB-INF/ecar-combo.tld" prefix="combo" %>

<%
	ConfigMailCfgmDAO configMailCfgmDAO = new ConfigMailCfgmDAO();
	ConfigMailCfgm configMailCfgm = null;
	configMailCfgm = (ConfigMailCfgm) configMailCfgmDAO.buscar(ConfigMailCfgm.class, Dominios.CFG_MAIL_CONCLUSAO_ACOMPANHAMENTO);

try{ 

	// parametros do request
	String codTipoAcompanhamento = Pagina.getParamStr(request, "codTipoAcompanhamento");
	String itemDoNivelClicado = Pagina.getParamStr(request, "itemDoNivelClicado");
	String mesReferencia = Pagina.getParamStr(request, "referencia_hidden");
	

	/* carrega o usuário da session */
	
	boolean isFormUpload = FileUpload.isMultipartContent(request);
	List campos = new ArrayList();
	if(isFormUpload){
		campos = FileUpload.criaListaCampos(request); 
	}
	
	String codAri = null;
	String primeiroAcomp = null;
	String nivelPrimeiroIettClicadoListaItens = null;
	String codAcomp = null;
	String vemDaPgAcompRelatorio = null;
	
	if (isFormUpload){
		codAri = FileUpload.verificaValorCampo(campos, "codAri");
		primeiroAcomp = FileUpload.verificaValorCampo(campos, "primeiroAcomp");
		nivelPrimeiroIettClicadoListaItens = FileUpload.verificaValorCampo(campos, "nivelIettClicado");
		codAcomp = FileUpload.verificaValorCampo(campos, "codAcomp");
		vemDaPgAcompRelatorio = FileUpload.verificaValorCampo(campos, "vemDaPgAcompRelatorio");
	} else {
		codAri = Pagina.getParamStr(request, "codAri");
		primeiroAcomp = Pagina.getParamStr(request, "primeiroAcomp");
		nivelPrimeiroIettClicadoListaItens = Pagina.getParamStr(request, "nivelIettClicado");
		codAcomp = Pagina.getParamStr(request, "codAcomp");
		vemDaPgAcompRelatorio = Pagina.getParamStr(request, "vemDaPgAcompRelatorio");
	}
	
	UsuarioUsu usuario = ((ecar.login.SegurancaECAR)session.getAttribute("seguranca")).getUsuario();
	
	/* item pai do item que está sendo cadastrado */  
	AcompReferenciaItemDao ariDao = new AcompReferenciaItemDao(request); 
	AcompReferenciaItemAri ari = (AcompReferenciaItemAri) ariDao.buscar(AcompReferenciaItemAri.class, Long.valueOf(codAri));
	ItemEstruturaIett itemEstrutura = ari.getItemEstruturaIett();
	
	AcompReferenciaAref arefSelecionado = null;
	arefSelecionado = ari.getAcompReferenciaAref();
	
	ValidaPermissao validaPermissao = new ValidaPermissao();

	List camposVariaveis = new ArrayList();
	String labelBtn = "";
	boolean emEdicao = true;
	if(ari.getStatusRelatorioSrl() != null && ari.getStatusRelatorioSrl().getCodSrl().intValue() == AcompReferenciaItemDao.STATUS_LIBERADO){
		_disabled = "disabled";
		labelBtn = "Recuperar Acompanhamento";
		emEdicao = false;
	}
	else{
		labelBtn = "Gravar e Liberar Acompanhamento";
	}
	
%>

<html lang="pt-br"> 
<head>
<%@ include file="/../include/meta.jsp"%>
<%@ include file="/titulo.jsp"%>
<link rel="stylesheet" href="<%=_pathEcar%>/css/style_<%=configuracaoCfg.getEstilo().getNome()%>.css" type="text/css">
<script language="javascript" src="<%=_pathEcar%>/js/menu_retratil.js" type="text/javascript"></script>
<script language="javascript" src="<%=_pathEcar%>/js/focoInicial.js" type="text/javascript"></script>
<script language="javascript" src="<%=_pathEcar%>/js/datas.js" type="text/javascript"></script>
<script language="javascript" src="<%=_pathEcar%>/js/mascaraMoeda.js" type="text/javascript"></script>
<script language="javascript" src="<%=_pathEcar%>/js/validacoes.js" type="text/javascript"></script>
 
<script type="text/javascript">
function trocarAba(nomeAba) {
	document.forms[0].action = nomeAba;
	document.forms[0].clicouAba.value = "S";
	document.forms[0].submit();
}


function aoClicarBtn1(form){
	<%if(ari.getStatusRelatorioSrl() != null 
	&& ari.getStatusRelatorioSrl().getCodSrl().intValue() == AcompReferenciaItemDao.STATUS_LIBERADO){
	%>
			alert("<%=_msg.getMensagem("acompanhamento.datasLimites.gravarJaLiberado")%>");
	<%} else {%>
		form.botaoClicado.value = "gravar";
		form.action = "ctrlDatasLimites.jsp";
		if(valida(form)){
			form.submit();
		}	 
	<%}%>
}

</script>

</head>
<%@ include file="/cabecalho.jsp" %>
<%@ include file="/divmenu.jsp"%> 

<%

String periodo = Pagina.getParamStr(request, "periodo");
String funcaoSelecionada = "DATAS_LIMITES";
 %>

<body>
<div id="conteudo"> 

<%@ include  file="../form_registro.jsp" %>	
<%

if(!"".equals(nivelPrimeiroIettClicadoListaItens)) {
	request.getSession().setAttribute("nivelPrimeiroIettClicado", nivelPrimeiroIettClicadoListaItens);
}
%>


<form  name="form" method="post" action="">
	<input type="hidden" name="autorizarMail" value="N">
	<input type="hidden" name="liberarOuRecuperar" value="N">
	<input type="hidden" name="primeiroAcomp" value="<%=primeiroAcomp%>">
	<input type="hidden" name="codAcomp" value="<%=codAcomp%>">
<%
	String codSrl = "";
	EstruturaAtributoDao estruturaAtributoDao = new EstruturaAtributoDao(request);
	if(ari.getStatusRelatorioSrl() != null){
		codSrl = ari.getStatusRelatorioSrl().getCodSrl().toString();
	}
	
	Boolean permissaoReferencia = new Boolean(false);
	
	/* Verifica permissão de acesso para alterar datas */
		
/*	if("S".equals(ari.getAcompReferenciaAref().getTipoAcompanhamentoTa().getIndMonitoramentoTa()))
		permissaoReferencia = new Boolean (validaPermissao.permissaoAcessoReferenciaMonitoramento(seguranca.getGruposAcesso()));
	else {
		if(seguranca.getUsuario().getOrgaoOrgs().contains(ari.getAcompReferenciaAref().getOrgaoOrg())){
			permissaoReferencia = new Boolean (validaPermissao.permissaoAcessoReferenciaSecretaria(seguranca.getGruposAcesso()));
			//se não tem permissão de proprio secretaria, e tiver em outras, pode alterar a da própria secretaria
			if(!permissaoReferencia.booleanValue())
				permissaoReferencia = new Boolean (validaPermissao.permissaoAcessoReferenciaOutraSecretaria(seguranca.getGruposAcesso()));
		}
		else {
			permissaoReferencia = new Boolean (validaPermissao.permissaoAcessoReferenciaOutraSecretaria(seguranca.getGruposAcesso()));
		}
	}
	*/
	
	permissaoReferencia = new Boolean (validaPermissao.permissaoAcessoReferencia(ari.getAcompReferenciaAref().getTipoAcompanhamentoTa(),seguranca.getGruposAcesso() ));
			
	if(!permissaoReferencia.booleanValue()) {
		_disabled = "disabled";
	}
	
%>
	<input type="hidden" name="obrigatorio" value="<%=configMailCfgm.getIndEnvioObrigatorio()%>">
	<input type="hidden" name="codAcomp" value="<%=codAcomp%>">
	<input type="hidden" name="codUsuario" value="<%=usuario.getCodUsu()%>"> 
	<input type="hidden" name="codSrl" value="<%=codSrl%>"> 	
	<input type="hidden" name="botaoClicado" value="">
	<input type="hidden" name="codTipoAcompanhamento" value="<%=codTipoAcompanhamento%>">
	<input type="hidden" name="mesReferencia" value="<%=ari.getAcompReferenciaAref().getCodAref().toString()%>">
	<input type="hidden" name="referencia_hidden" value="<%=Pagina.getParamStr(request, "referencia_hidden")%>">
	<input type="hidden" name="niveisPlanejamento" value="<%=Pagina.getParamStr(request, "niveisPlanejamento")%>">
	<input type="hidden" name="codAri" value="<%=Pagina.getParamStr(request, "codAri")%>">
	<input type="hidden" name="primeiroIettClicado" value="<%=Pagina.getParamStr(request, "primeiroIettClicado")%>">
	<input type="hidden" name="primeiroAriClicado" value="<%=Pagina.getParamStr(request, "primeiroAriClicado")%>">
	<input type="hidden" name="codIettRelacao" value="<%=Pagina.getParamStr(request, "codIettRelacao")%>">
	<input type="hidden" name="itemDoNivelClicado" value="<%=Pagina.getParamStr(request, "itemDoNivelClicado")%>">
	<input type="hidden" name="codigosItem" value="<%=Pagina.getParamStr(request, "codigosItem")%>">
	<input type="hidden" name="periodo" value="<%=Pagina.getParamStr(request, "periodo")%>">
	<!-- Campo necessario para guardar a forma de visualização escolhida no filtro -->
	<input type="hidden" name="hidFormaVisualizacaoEscolhida" value='<%=Pagina.getParamStr(request, "hidFormaVisualizacaoEscolhida")%>'>
	<input type="hidden" name="clicouAba" value="<%= Pagina.getParamStr(request, "clicouAba")%>">
	<input type="hidden" name="tipoPadraoExibicaoAba" value="<%= Pagina.getParamStr(request, "tipoPadraoExibicaoAba")%>">
	
	<!-- Campo necessário para botões de Avança/Retrocede -->
	<input type="hidden" name="codArisControle" value="<%=Pagina.getParamStr(request, "codArisControle")%>">

	<util:barrabotoes btn1="Gravar" exibirBtn1="<%=permissaoReferencia%>" btn3="<%=labelBtn%>" exibirBtn3="<%=new Boolean(permissaoReferencia.booleanValue())%>"/>

	<table name="form" class="form" width="100%">
		<%if(ari.getItemEstruturaIett().getOrgaoOrgByCodOrgaoResponsavel1Iett()!=null){%>
			<tr>
			 <td class="label"><%=estruturaAtributoDao.getLabelAtributoEstrutura("orgaoOrgByCodOrgaoResponsavel1Iett", ari.getItemEstruturaIett().getEstruturaEtt())%></td>
			 <td>
					<%=ari.getItemEstruturaIett().getOrgaoOrgByCodOrgaoResponsavel1Iett().getDescricaoOrg()%>
			</td>
		</tr>  
		<%}%>		
		<%if(ari.getItemEstruturaIett().getOrgaoOrgByCodOrgaoResponsavel2Iett()!=null){%>
			<tr>
			 <td class="label"><%=estruturaAtributoDao.getLabelAtributoEstrutura("orgaoOrgByCodOrgaoResponsavel2Iett", ari.getItemEstruturaIett().getEstruturaEtt())%></td>
			 <td>
					<%=ari.getItemEstruturaIett().getOrgaoOrgByCodOrgaoResponsavel2Iett().getDescricaoOrg()%>
			</td>
		</tr>  
		<%}%>		

		<%
			//Recuperar as funçoes de Acompanhamento do Item
			//Set funcoes = ari.getItemEstruturaIett().getItemEstUsutpfuacIettutfas();
			List funcoes = new ItemEstUsutpfuacDao(request).getFuacOrderByFuncAcomp(ari.getItemEstruturaIett());
			
			Iterator it = funcoes.iterator();
			while(it.hasNext()){
				ItemEstUsutpfuacIettutfa itemEstUsu = (ItemEstUsutpfuacIettutfa) it.next();
				%>
				<tr> 
				 <td class="label"><%=itemEstUsu.getTipoFuncAcompTpfa().getLabelTpfa()%></td>
			 	<%
			 	String nomeUsuarioFuac = "";
			 	String imagem_inativa = "";
			 	
			 	if (itemEstUsu.getUsuarioUsu() != null) {
			 		UsuarioUsu usuarioFuac = (UsuarioUsu) new UsuarioDao(request).buscar(UsuarioUsu.class, itemEstUsu.getUsuarioUsu().getCodUsu());
					//nomeUsuarioFuac possui o valor de "Responsavel Tecnico" ou "Administrador" ou...?
				 	nomeUsuarioFuac = usuarioFuac.getNomeUsuSent();
					//Imagem para Usuario INAtivo
					
					if (!"S".equals(itemEstUsu.getUsuarioUsu().getIndAtivoUsu())){
						imagem_inativa="<img src=\"" + _pathEcar + "/images/icon_usuario_inativo.png\" title=\"Usuário Inativo\">";
					}	
			 	} else if (itemEstUsu.getSisAtributoSatb() != null){
			 		SisAtributoSatb sisAtributoSatb = (SisAtributoSatb) new SisAtributoDao(request).buscar(SisAtributoSatb.class, itemEstUsu.getSisAtributoSatb().getCodSatb());
			 		nomeUsuarioFuac = sisAtributoSatb.getDescricaoSatb();
			 	}
			 	%>
					<td><%=nomeUsuarioFuac%> <%=imagem_inativa%></td>				
				 </tr>
				 <%
				 ecar.pojo.AcompRelatorioArel relatorio = new ecar.dao.AcompRelatorioDao(request).getAcompRelatorio(itemEstUsu.getTipoFuncAcompTpfa(), ari);
				 if(relatorio != null){
				 		UsuarioUsu usuarioUltimaManutencao = relatorio.getUsuarioUsuUltimaManutencao();
				 		if(usuarioUltimaManutencao != null && !usuarioUltimaManutencao.equals(itemEstUsu.getUsuarioUsu())){
				 			%>
							<tr>
								<td>&nbsp;</td>
								<td><%=itemEstUsu.getTipoFuncAcompTpfa().getLabelPosicaoTpfa()%> atribu&iacute;da por <b><%=usuarioUltimaManutencao.getNomeUsuSent()%></b></td>
							</tr>
							<%
				 		}
				 }
				 %>
				<%
			}
		%> 
		<tr>
			 <td class="label">* Data de In&iacute;cio</td>
			 <td>
				 <% if("disabled".equals(_disabled)) { %>
					 <input type="hidden" name="dataInicioAri" value="<%=Pagina.trocaNullData(ari.getDataInicioAri())%>">
					 <input type="text" name="dataInicioAriDisabled" size="13" maxlength="10" value="<%=Pagina.trocaNullData(ari.getDataInicioAri())%>" onkeyup="mascaraData(event, this);" <%=_disabled%>>
					 
				 <% } else {  %>
					 <input type="text" name="dataInicioAri" size="13" maxlength="10" value="<%=Pagina.trocaNullData(ari.getDataInicioAri())%>" onkeyup="mascaraData(event, this);" <%=_disabled%>>
					 <img class="posicao" title="Selecione a data" src="../../images/icone_calendar.gif" onclick="open_calendar('data', document.forms[0].dataInicioAri, '')">
				 <% } %>
			</td>
		</tr> 
		<tr>
			 <td class="label">* Data Limite para Acompanhamento F&iacute;sico</td>
			 <td>
				 <%
				 if("disabled".equals(_disabled)) {
				 %>
					 <input type="hidden" name="dataLimiteAcompFisico" value="<%=Pagina.trocaNullData(ari.getDataLimiteAcompFisicoAri())%>">
					 <input type="text" name="dataLimiteAcompFisicoDisabled" size="13" maxlength="10" value="<%=Pagina.trocaNullData(ari.getDataLimiteAcompFisicoAri())%>" onkeyup="mascaraData(event, this);" <%=_disabled%>>
				 <%
				 } else {
				 %>
					 <input type="text" name="dataLimiteAcompFisico" size="13" maxlength="10" value="<%=Pagina.trocaNullData(ari.getDataLimiteAcompFisicoAri())%>" onkeyup="mascaraData(event, this);" <%=_disabled%>>
					 <img class="posicao" title="Selecione a data" src="../../images/icone_calendar.gif" onclick="open_calendar('data', document.forms[0].dataLimiteAcompFisico, '')">
				 <%
				 }
				 %>
			</td>
		</tr> 
		<%
		List funcoesAcompanhamento = ariDao.getAcompRefItemLimitesArliByAcompRefrenciaItem(ari);
        Iterator itFuncoes = funcoesAcompanhamento.iterator();
        while(itFuncoes.hasNext()){
        	AcompRefItemLimitesArli acompLimite = (AcompRefItemLimitesArli) itFuncoes.next();
        	camposVariaveis.add(acompLimite.getTipoFuncAcompTpfa());
        	%>
			<tr>
				 <td class="label">* Data limite para <%=acompLimite.getTipoFuncAcompTpfa().getLabelPosicaoTpfa()%></td>
				 <td>
					 <%
					 if("disabled".equals(_disabled)) {
					 %>
						 <input type="hidden" name="dataLimite<%=acompLimite.getTipoFuncAcompTpfa().getCodTpfa()%>" value="<%=Pagina.trocaNullData(acompLimite.getDataLimiteArli())%>">
						 <input type="text" name="dataLimite<%=acompLimite.getTipoFuncAcompTpfa().getCodTpfa()%>Disabled" size="13" maxlength="10" value="<%=Pagina.trocaNullData(acompLimite.getDataLimiteArli())%>" onkeyup="mascaraData(event, this);" <%=_disabled%>>
					 <%
					 } else {
					 %>
						 <input type="text" name="dataLimite<%=acompLimite.getTipoFuncAcompTpfa().getCodTpfa()%>" size="13" maxlength="10" value="<%=Pagina.trocaNullData(acompLimite.getDataLimiteArli())%>" onkeyup="mascaraData(event, this);" <%=_disabled%>>
						 <img class="posicao" title="Selecione a data" src="../../images/icone_calendar.gif" onclick="open_calendar('data', document.forms[0].dataLimite<%=acompLimite.getTipoFuncAcompTpfa().getCodTpfa()%>, '')">
					 <%
					 }
					 %>
						 
				</td>
			</tr> 			
			<%
        }
		%>
		<tr>
			 <td class="label">Inclus&atilde;o:</td>
			 <td>
			 <%=Pagina.trocaNullData(ari.getDataInclusaoAri())%>
			 <%if(ari.getCodUsuincAri() != null) {
				 	UsuarioUsu usuarioInclusao = (UsuarioUsu) new UsuarioDao(request).buscar(UsuarioUsu.class, ari.getCodUsuincAri());
				 	
				 	//Imagem para Usuatio INAtivo
				 	String imagem_inativa = "";
					if (!"S".equals(usuarioInclusao.getIndAtivoUsu())){
						imagem_inativa="<img src=\"" + _pathEcar + "/images/icon_usuario_inativo.png\" title=\"Usuário Inativo\">";
					}
				 	//Termina "Imagem para Usuario INAtivo"
				 	
					out.println("&nbsp;&nbsp;" + usuarioInclusao.getNomeUsuSent() + " " + imagem_inativa);
			 	}
			 %>
			</td>
		</tr> 	
		<tr>
			 <td class="label">&Uacute;ltima Altera&ccedil;&atilde;o:</td>
			 <td>
			 <%=Pagina.trocaNullData(ari.getDataUltManutAri())%>
			 <%if(ari.getCodUsuUltManutAri() != null) {
				 	UsuarioUsu usuarioAlteracao = (UsuarioUsu) new UsuarioDao(request).buscar(UsuarioUsu.class, ari.getCodUsuUltManutAri());
				 	
					//Imagem para Usuatio INAtivo
				 	String imagem_inativa = "";
					if (!"S".equals(usuarioAlteracao.getIndAtivoUsu())){
						imagem_inativa="<img src=\"" + _pathEcar + "/images/icon_usuario_inativo.png\" title=\"Usuário Inativo\">";
					}
				 	//Termina "Imagem para Usuario INAtivo"
				 	
				 	out.println("&nbsp;&nbsp;" + usuarioAlteracao.getNomeUsuSent() + " " + imagem_inativa);
			 	}
			 %>
			</td>
		</tr> 			
	</table>
	
	<util:barrabotoes btn1="Gravar" exibirBtn1="<%=permissaoReferencia%>"  btn3="<%=labelBtn%>" exibirBtn3="<%=new Boolean(permissaoReferencia.booleanValue())%>"/>


</form>
<br>
</div>
</body>

<script type="text/javascript">
function valida(form){
	if(!validaData(form.dataInicioAri,false,true,true)){
		if(form.dataInicioAri.value == ""){
			alert("<%=_msg.getMensagem("acompanhamento.datasLimites.dataInicioAri.obrigatorio")%>");
		}else{
			alert("<%=_msg.getMensagem("acompanhamento.datasLimites.dataInicioAri.invalido")%>");
		}
		return(false);
	}	
	if(!validaData(form.dataLimiteAcompFisico,false,true,true)){
		if(form.dataLimiteAcompFisico.value == ""){
			alert("<%=_msg.getMensagem("acompanhamento.datasLimites.dataLimiteAcompFisico.obrigatorio")%>");
		}else{
			alert("<%=_msg.getMensagem("acompanhamento.datasLimites.dataLimiteAcompFisico.invalido")%>");
		}
		return(false);
	}	
	<%
		Iterator itCv = camposVariaveis.iterator();
		Object[] aCamposVariaveis = camposVariaveis.toArray();
		for (int i = 0; i< camposVariaveis.size(); i++){
			TipoFuncAcompTpfa fun = (TipoFuncAcompTpfa) aCamposVariaveis[i];
			String nomeCampo = "dataLimite" + fun.getCodTpfa();
			%>
				if(!validaData(form.<%=nomeCampo%>,false,true,true)){
					if(form.<%=nomeCampo%>.value == ""){
						alert("Campo Data Limite para <%=fun.getLabelPosicaoTpfa()%> obrigatório");
					}else{
						alert("Valor inválido para campo Data Limite para <%=fun.getLabelPosicaoTpfa()%>");
					}
					return(false);
				}	
		<%
		}
	%>
	return true;
}

//o if abaixo é para quando vem para esta pg qdo todas as posições já estão liberadas e o usuario deseja liberar o acompanhamento
<%if (!"S".equals(vemDaPgAcompRelatorio)){ %>
function aoClicarBtn3(form){	
<%} %>
		if('<%=configMailCfgm.getAtivoCfgm()%>' == 'S') {
			<%if ("Gravar e Liberar Acompanhamento".equals(labelBtn)){%>
			if(document.form.obrigatorio.value == 'S' || (document.form.obrigatorio.value == 'N' && confirm('<%=_msg.getMensagem("acompanhamento.elabAcompanhamento.liberacao.autorizaEnviaEmail")%>') == true )) {
				document.form.autorizarMail.value = 'S';
				document.form.liberarOuRecuperar.value = 'L';
			}
			<%} else {%> 
			if(document.form.obrigatorio.value == 'S' || (document.form.obrigatorio.value == 'N' && confirm('<%=_msg.getMensagem("acompanhamento.elabAcompanhamento.recuperacao.autorizaEnviaEmail")%>') == true )) {
				document.form.autorizarMail.value = 'S';
				document.form.liberarOuRecuperar.value = 'R';
			}
			<%}%> 
		}
	
		form.botaoClicado.value = "liberarRecuperar";
		form.action = "ctrlDatasLimites.jsp";
		if(form.codSrl.value == 2){
				form.codSrl.value = 1;
				form.submit();	
		}
		else{
				<%
				String msg = ariDao.getMensagemAlertLiberarAcompanhamento(ari);
				if(!"".equals(msg)){
						%>
						if(confirm("<%=msg%>")){
							if(valida(form)){
								form.dataInicioAri.disabled = false;
								form.dataLimiteAcompFisico.disabled = false;								
								form.codSrl.value = 2;				
								<%
								for (int i = 0; i< camposVariaveis.size(); i++){
									TipoFuncAcompTpfa fun = (TipoFuncAcompTpfa) aCamposVariaveis[i];
									String nomeCampo = "dataLimite" + fun.getCodTpfa();
									%>form.<%=nomeCampo%>.disabled = false;
									<%
								}
								%>
								form.submit();
							 }
						}
						<%
				} else {
					%>
							if(valida(form)){
								form.dataInicioAri.disabled = false;
								form.dataLimiteAcompFisico.disabled = false;								
								form.codSrl.value = 2;				
								<%
								for (int i = 0; i< camposVariaveis.size(); i++){
									TipoFuncAcompTpfa fun = (TipoFuncAcompTpfa) aCamposVariaveis[i];
									String nomeCampo = "dataLimite" + fun.getCodTpfa();
									%>form.<%=nomeCampo%>.disabled = false;
									<%
								}
								%>												
								form.submit();				
							}
					<%
				}
				%>
		}
<%if (!"S".equals(vemDaPgAcompRelatorio)){ %>
	}
<%} %>
</script>

<%
} catch (ECARException e) {
	org.apache.log4j.Logger.getLogger(this.getClass()).error(e);
	Mensagem.alert(_jspx_page_context, e.getMessageKey());
} catch (Exception e){
%>
	<%@ include file="/excecao.jsp"%>
<%
}
%>

<% /* Controla o estado do Menu (aberto ou fechado) */%>
<%@ include file="/../../include/estadoMenu.jsp"%>
<%@ include file="/../../include/mensagemAlert.jsp" %>

</html>