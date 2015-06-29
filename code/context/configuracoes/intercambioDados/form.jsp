<%@ taglib uri="/WEB-INF/ecar-util.tld" prefix="util" %>
<%@ taglib uri="/WEB-INF/ecar-combo.tld" prefix="combo" %>

<%@ page import="ecar.pojo.ConfiguracaoCfg" %>
<%@ page import="ecar.pojo.TipoFuncAcompTpfa" %>
<%@ page import="ecar.pojo.SisAtributoSatb" %>
<%@ page import="ecar.pojo.SisGrupoAtributoSga" %>
<%@ page import="ecar.pojo.Estilo" %>
<%@ page import="ecar.pojo.ConfigMailCfgm" %>
<%@ page import="ecar.dao.TextosSiteDao" %>
<%@ page import="ecar.pojo.TextosSiteTxt" %>
<%@ page import="ecar.pojo.intercambioDados.PerfilIntercambioDadosPflid"%>
<%@ page import="ecar.pojo.EstruturaEtt"%>
<%@ page import="ecar.pojo.AtributosAtb"%>
<%@ page import="ecar.pojo.ItemEstruturaIett"%>
<%@ page import="ecar.pojo.intercambioDados.funcionalidade.PerfilIntercambioDadosCadastroPidc"%>
<%@ page import="ecar.pojo.intercambioDados.funcionalidade.TipoFuncionalidadeEnum"%>
<%@ page import="ecar.pojo.intercambioDados.tecnologia.DadosTecnologiaPerfilTxtDtpt"%>

<%@ page import="ecar.dao.ConfiguracaoDao" %>
<%@ page import="ecar.dao.TipoFuncAcompDao" %>
<%@ page import="ecar.dao.SisAtributoDao" %>
<%@ page import="ecar.dao.SisGrupoAtributoDao" %>
<%@ page import="ecar.dao.ConfigMailCfgmDAO" %>
<%@ page import="ecar.dao.EstiloDao" %>
<%@ page import="ecar.dao.intercambioDados.PerfilIntercambioDadosDao"%>
<%@ page import="ecar.dao.EstruturaDao"%>
<%@ page import="ecar.dao.AtributoDao"%>
<%@ page import="ecar.dao.ItemEstruturaDao"%>

<%@ page import="java.util.Iterator" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.Set" %>

<%@ page import="comum.util.Pagina"%>


<%@page import="ecar.util.Dominios"%>
<%@page import="ecar.pojo.intercambioDados.tecnologia.DadosTecnologiaPerfilDtp"%>
<%@page import="ecar.pojo.intercambioDados.tecnologia.TipoTecnologiaEnum"%>
<%@page import="java.util.Arrays"%>
<%@page import="java.util.Collections"%>


<%@page import="comum.util.Data"%><html lang="pt-br">
<script language="javascript" src="../../js/util.js"></script>
<script language="javascript">

function atualizarEstruturaItemNivelSuperior(form){
	form.hidAcao.value="recarregaEstruturaItemNivelSuperior";
	form.action = "ctrl.jsp";

	//metodo localizado nos JSP's externos que incluem o form para identificar a origme da requisição.
	form.jspOrigem.value = caminhoRetorno();
	
	form.submit();	
}


function atualizarEstruturaCriacaoItem (form){
	form.hidAcao.value="recarregaEstruturaCriacaoItem";
	form.action = "ctrl.jsp";
	//metodo localizado nos JSP's externos que incluem o form para identificar a origme da requisição.
	form.jspOrigem.value = caminhoRetorno();
	
	form.submit();	
	
}

function habilitarCampo(campo, liberarCampo){
	if (liberarCampo) {
		document.getElementById(campo).disabled = "";
	} else {
		document.getElementById(campo).value = "";
		document.getElementById(campo).disabled = "disabled";
	}
}

function validarGravar (form){
	
	if (form.nomePflid.value == "") {
		alert ('<%=_msg.getMensagem("perfil.intercambioDados.campo.obrigatorio",new String[]{"Nome"})%>');
		return false;
	}

	var indUsuProcessamentoAssociacaoItem = valorRadioSelecionado(form.indUsuProcessamentoAssociacaoItemPflid);
	if (indUsuProcessamentoAssociacaoItem == "N" && form.usuarioUsuImportacao.value == ""){
		alert ('<%=_msg.getMensagem("perfil.intercambioDados.campo.obrigatorio",new String[]{"Usuario de processamento"})%>');
		return false;
	}

	var indAtivoAvisoImp = valorRadioSelecionado(form.indAtivoAvisoImpPflid);
	
	if (indAtivoAvisoImp == ""){
		alert ('<%=_msg.getMensagem("perfil.intercambioDados.campo.obrigatorio",new String[]{"Aviso de importação"})%>');
		return false;
	} else {
		if (indAtivoAvisoImp == "S"){
			if (form.sisAtributoSatbAcessoEnvioEmailImp.value == "") {
				alert ('<%=_msg.getMensagem("perfil.intercambioDados.campo.obrigatorio",new String[]{"Grupo de acesso para envio de e-mail"})%>');
				return false;
			}

			if (form.textosSiteTxt.value == "") {
				alert ('<%=_msg.getMensagem("perfil.intercambioDados.campo.obrigatorio",new String[]{"Texto para envio de e-mail"})%>');
				return false;
			}
			
		}
	}	

	if (form.situacaoSitNaoInformadoImp.value == ""){
		alert ('<%=_msg.getMensagem("perfil.intercambioDados.campo.obrigatorio",new String[]{"Situação não informada"})%>');
		return false;
	}

	if (form.situacaoSitSemCorrespondenteImp.value == ""){
		alert ('<%=_msg.getMensagem("perfil.intercambioDados.campo.obrigatorio",new String[]{"Situação sem correspondência"})%>');
		return false;
	}

	if (form.estruturaEttBaseImp.value == ""){
		alert ('<%=_msg.getMensagem("perfil.intercambioDados.campo.obrigatorio",new String[]{"Estrutura base"})%>');
		return false;
	}

	if (form.estruturaEttItemNivelSuperiorImp.value == ""){
		alert ('<%=_msg.getMensagem("perfil.intercambioDados.campo.obrigatorio",new String[]{"Estrutura do item de nível superior"})%>');
		return false;
	}

	if (form.estruturaEttCriacaoItemImp.value == ""){
		alert ('<%=_msg.getMensagem("perfil.intercambioDados.campo.obrigatorio",new String[]{"Estrutura de criação de itens"})%>');
		return false;
	}

	if (form.sisAtributoSatbAcessoPermItemImp.value == ""){
		alert ('<%=_msg.getMensagem("perfil.intercambioDados.campo.obrigatorio",new String[]{"Grupo de Acesso aos itens incluídos"})%>');
		return false;
	}
	
	if (form.separadorCamposPflid.value == ""){
		alert ('<%=_msg.getMensagem("perfil.intercambioDados.campo.obrigatorio",new String[]{"Separador de campos"})%>');
		return false;
	}
	
	
	return true;
}

</script>
	<%
	SisAtributoDao sisAtributoDao = new SisAtributoDao(request);
	EstruturaDao estruturaDao = new EstruturaDao(request);
	AtributoDao atributoDao = new AtributoDao(request);

	PerfilIntercambioDadosPflid perfilIntercambioDados = (PerfilIntercambioDadosPflid)session.getAttribute("perfilConsultado");
	
	Long idEstruturaBaseSelecionada = (Long)session.getAttribute("idEstruturaBaseSelecionada");
	
	Long idEstruturaItemNivelSuperiorSelecionada = (Long)session.getAttribute("idEstruturaItemNivelSuperiorSelecionada");
	
	%>

	<div id="dados_gerais" >
		<table class="form">
	
			<util:msgObrigatorio obrigatorio="<%=_obrigatorio%>" />
			
			<tr>
				<td class="label"><%=_obrigatorio%> Nome</td>		
				<%
							String nomePflid = "";
								
							if(perfilIntercambioDados != null && perfilIntercambioDados.getNomePflid()!= null){
								nomePflid = perfilIntercambioDados.getNomePflid();
							}		
						%>
				<td><input type="text" name="nomePflid" size="35" maxlength="40" value="<%=nomePflid%>" <%=_disabled%>>
				</td>
			</tr>
			
			<%
				if (!ehPesquisa) {
			%>
			<tr> <td colspan="2">&nbsp;</td> </tr>
			<tr>
				<td class="label">Serviço</td>		
				<td>&nbsp;</td>		
			</tr>
			
			<tr>
				<td class="label"><%=_obrigatorio%> Identificador</td>
				<td><input disabled="disabled" type="text" name="codTipoServicoPflid" size="5" maxlength="5" value="<%=Dominios.PERFIL_IDENTIFICADOR_SERVICO%>" >
				</td>
			</tr>
			
			<tr>
				<td class="label"><%=_obrigatorio%> Nome</td>		
				<td><input disabled="disabled" type="text" name="nomeTipoServicoPflid" size="35" maxlength="40" value="<%=Dominios.PERFIL_NOME_SERVICO%>" >
				</td>		
			</tr>
			<%
				}
			%>
			<%
				if (!ehPesquisa) {
			%>
			<tr> <td colspan="2">&nbsp;</td> </tr>
			<tr>
				<td class="label">Processamento</td>		
				<td>&nbsp;</td>		
			</tr>
			<tr>
				<td class="label"><%=_obrigatorio%> Modo</td>
				<td>
					<input type="radio" disabled="disabled" class="form_check_radio" name="indModoProcessamentoPflid" value="<%=Dominios.PERFIL_MODO_PROCESSAMENTO_MANUAL %>" checked="checked" >Manual
					<input type="radio" disabled="disabled" class="form_check_radio" name="indModoProcessamentoPflid" value="<%=Dominios.PERFIL_MODO_PROCESSAMENTO_AUTOMATICO %>" >Automático
				</td>
			</tr>
			<%
				}
			%>
			<%
				if (!ehPesquisa) {
			%>
			<tr> <td colspan="2">&nbsp;</td> </tr>
			<tr>
				<td class="label">Sistema destino</td>		
				<td>&nbsp;</td>		
			</tr>
			
			<tr>
				<td class="label"><%=_obrigatorio%> Identificador</td>
				<td>
					<input type="text" disabled="disabled" name="codSistemaDestinoPflid" size="5" maxlength="5" value="<%=Dominios.PERFIL_IDENTIFICADOR_SISTEMA_DESTINO%>" >
				</td>
			</tr>
			
			<tr>
				<td class="label"><%=_obrigatorio%> Nome</td>		
				<td>
					<input type="text" disabled="disabled" name="nomeSistemaDestinoPflid" size="35" maxlength="40"	value="<%=Dominios.PERFIL_NOME_SISTEMA_DESTINO%>" >
				</td>		
			</tr>
			<%
				}
			%>
			
			<%
				if (!ehPesquisa) {
			%>
			<tr> <td colspan="2">&nbsp;</td> </tr>
			<tr>
				<td class="label">Sistema origem</td>		
				<td>&nbsp;</td>		
			</tr>
			
			<tr>
				<td class="label"><%=_obrigatorio%> Identificador</td>
				<td>
					<input type="text" disabled="disabled" name="codSistemaOrigemPflid" size="5" maxlength="5"	value="<%=Dominios.PERFIL_IDENTIFICADOR_SISTEMA_ORIGEM%>" >
				</td>
			</tr>
			
			<tr>
				<td class="label"><%=_obrigatorio%> Nome</td>		
				<td>
					<input type="text" disabled="disabled" name="nomeSistemaOrigemPflid" size="35" maxlength="40"	value="<%=Dominios.PERFIL_NOME_SISTEMA_ORIGEM%>" >
				</td>		
			</tr>
			<%
				}

				String checkedIndUsuProcessamentoAssociacaoItemPflidSim = "";
				String checkedIndUsuProcessamentoAssociacaoItemPflidNao = "";
			%>
			<tr> <td colspan="2">&nbsp;</td> </tr>
			
			<tr>
				<td class="label">Dados de Manutenção dos Elementos</td>		
				<td>&nbsp;</td>		
			</tr>
			
			<tr>
				<td class="label"><%=_obrigatorio%> Associar usuário do processamento ao elemento incluído?</td>
				<td>
					<%
						
						if(perfilIntercambioDados != null && perfilIntercambioDados.getIndUsuarioProcessamentoAssociacaoItemPflid()!= null){
							if ("S".equals(perfilIntercambioDados.getIndUsuarioProcessamentoAssociacaoItemPflid())){
								checkedIndUsuProcessamentoAssociacaoItemPflidSim = "checked";
							} else if ("N".equals(perfilIntercambioDados.getIndUsuarioProcessamentoAssociacaoItemPflid())){
								checkedIndUsuProcessamentoAssociacaoItemPflidNao = "checked";	
							}
						}

						if (!ehPesquisa) {
							if (checkedIndUsuProcessamentoAssociacaoItemPflidSim.equals("") && checkedIndUsuProcessamentoAssociacaoItemPflidNao.equals("")){
								checkedIndUsuProcessamentoAssociacaoItemPflidNao = "checked";	
							}
						}
					%>
				
					<input type="radio" <%=_disabled%> class="form_check_radio" name="indUsuProcessamentoAssociacaoItemPflid" value="<%=Dominios.SIM %>" onclick="habilitarCampo('usuarioUsuImportacao', false)" <%=checkedIndUsuProcessamentoAssociacaoItemPflidSim%>>Sim 
					<input type="radio" <%=_disabled%> class="form_check_radio" name="indUsuProcessamentoAssociacaoItemPflid" value="<%=Dominios.NAO %>" onclick="habilitarCampo('usuarioUsuImportacao', true)" <%=checkedIndUsuProcessamentoAssociacaoItemPflidNao%>>N&atilde;o
				</td>
			</tr>
			
			<tr>
				<td class="label"> Usuário </td>
				<td>
				<%
					String selectedUsuarioIncluindoItem = "";
						
					if(perfilIntercambioDados != null && perfilIntercambioDados.getUsuarioImportacao()!= null){
						selectedUsuarioIncluindoItem = perfilIntercambioDados.getUsuarioImportacao().getCodUsu().toString();
					}
				%>
				<combo:ComboTag 
					nome="usuarioUsuImportacao"
					objeto="ecar.pojo.UsuarioUsu" 
					label="nomeUsu" 
					value="codUsu" 
					order="nomeUsu"
					filters="indAtivoUsu=S"
					disabled="<%=(checkedIndUsuProcessamentoAssociacaoItemPflidNao.equals("checked") && !"disabled".equals(_disabled))? "false" : "true"%>"
					selected="<%=selectedUsuarioIncluindoItem%>"
				/>
				</td>
			</tr>
			<tr> <td colspan="2">&nbsp;</td> </tr>
			
			<tr>
				<td class="label">Aviso de Importação</td>		
				<td>&nbsp;</td>		
			</tr>
			
			<tr>
				<td class="label"><%=_obrigatorio%> Ativar</td>
				<td>
				
					<%
						String checkedIndAtivoAvisoImpPflidSim = "";
						String checkedIndAtivoAvisoImpPflidNao = "";
		
						
						if(perfilIntercambioDados != null && perfilIntercambioDados.getIndAtivoAvisoImpPflid()!= null){
							if ("S".equals(perfilIntercambioDados.getIndAtivoAvisoImpPflid())){
								checkedIndAtivoAvisoImpPflidSim = "checked";
							} else if ("N".equals(perfilIntercambioDados.getIndAtivoAvisoImpPflid())){
								checkedIndAtivoAvisoImpPflidNao = "checked";
							}
						} 
						
						if (!ehPesquisa) {
							if (checkedIndAtivoAvisoImpPflidSim.equals(Dominios.STRING_VAZIA) && checkedIndAtivoAvisoImpPflidNao.equals(Dominios.STRING_VAZIA)){
								checkedIndAtivoAvisoImpPflidSim = "checked";
								checkedIndAtivoAvisoImpPflidNao = "";
							}
						}
					%>
				
					<input type="radio" <%=_disabled%> class="form_check_radio" name="indAtivoAvisoImpPflid" value="<%=Dominios.SIM%>" onclick="habilitarCampo('sisAtributoSatbAcessoEnvioEmailImp', true);habilitarCampo('textosSiteTxt', true)" <%=checkedIndAtivoAvisoImpPflidSim%>>Sim 
					<input type="radio" <%=_disabled%> class="form_check_radio" name="indAtivoAvisoImpPflid" value="<%=Dominios.NAO%>" onclick="habilitarCampo('sisAtributoSatbAcessoEnvioEmailImp', false);habilitarCampo('textosSiteTxt', false)" <%=checkedIndAtivoAvisoImpPflidNao%>>N&atilde;o
				</td>
			</tr>
			
			<tr>
				<td class="label"> Grupo de acesso para <br> envio de e-mail</td>
				<td>
				<%
					String selectedSisAtributoSatbAcessoEnvioEmailImp = "";
					
					if(perfilIntercambioDados != null && perfilIntercambioDados.getGrupoAcessoEnvioEmailPflid()!= null){
						selectedSisAtributoSatbAcessoEnvioEmailImp = perfilIntercambioDados.getGrupoAcessoEnvioEmailPflid().getCodSatb().toString();
					}
				%>
				<combo:ComboTag 
					nome="sisAtributoSatbAcessoEnvioEmailImp"
					objeto="ecar.pojo.SisAtributoSatb" 
					label="descricaoSatb" 
					value="codSatb" 
					order="descricaoSatb"
					disabled="<%=(checkedIndAtivoAvisoImpPflidSim.equals("checked") && !"disabled".equals(_disabled)) ? "false" : "true"%>"
					colecao="<%=sisAtributoDao.getListaAcesso()%>"
					selected="<%=selectedSisAtributoSatbAcessoEnvioEmailImp%>"
				/>
				</td>
			</tr>
			
			<tr>
				<td class="label"> E-Mail</td>
				<td>
				<%
				String selectedTextosSiteTxt = "";
		
				if(perfilIntercambioDados != null && perfilIntercambioDados.getComposicaoEmailPflid()!= null){
					selectedTextosSiteTxt = perfilIntercambioDados.getComposicaoEmailPflid().getCodTxtS().toString();
				}		
				%>
				<combo:ComboTag 
					nome="textosSiteTxt"
					objeto="ecar.pojo.TextosSiteTxt" 
					label="descricaoUsoTxts" 
					value="codTxtS" 
					filters="indAtivoTxts=S"
					order="descricaoUsoTxts"
					disabled="<%=(checkedIndAtivoAvisoImpPflidSim.equals("checked") && !"disabled".equals(_disabled)) ? "false" : "true"%>"
					selected="<%=selectedTextosSiteTxt%>"
				/>
				</td>
			</tr>
			
			
			<tr> <td colspan="2">&nbsp;</td> </tr>
		</table>
	</div>
	<%
		if (!ehPesquisa) {
	%>
	<div id="seleciona_funcionalidade">
		<table class="form"> 
			<tr>
				<td class="label">Tipo de Funcionalidade</td>		
				<td>&nbsp;</td>		
			</tr>
			<tr>
				<td class="label"><%=_obrigatorio%> Nome</td>
				<td>
					<select disabled="disabled" name="tipoFuncionalidade">
						<% 
						TipoFuncionalidadeEnum[] tipoFuncionalidadeEnum = TipoFuncionalidadeEnum.values();
						
						for (int i=0; i<tipoFuncionalidadeEnum.length;i++){%>
							<option selected="selected" value="<%=tipoFuncionalidadeEnum[i].getCodigo() %>" ><%=tipoFuncionalidadeEnum[i].getDescricao() %></option>	
						<% } %>
					</select>
				</td>
			</tr>
		</table>
	</div>
	<%
		}
	%>
	
	<div id="funcionalidade_cadastro">
		<table class="form"> 
			<tr>
				<td class="label">Situação</td>		
				<td>&nbsp;</td>		
			</tr>
			
			<tr>
				<td class="label"><%=_obrigatorio%> Valor n&atilde;o informado</td>
				<td>
				<%
					String selectedSituacaoSitNaoInformadoImp = "";
						
					if (perfilIntercambioDados != null && perfilIntercambioDados.getTipoFuncionalidade().equals(TipoFuncionalidadeEnum.CADASTRO)) {
						if(((PerfilIntercambioDadosCadastroPidc)perfilIntercambioDados).getSituacaoNaoInformadaPidc()!= null){
							selectedSituacaoSitNaoInformadoImp = ((PerfilIntercambioDadosCadastroPidc)perfilIntercambioDados).getSituacaoNaoInformadaPidc().getCodSit().toString();
						}
					}
				%>
				<combo:ComboTag 
					nome="situacaoSitNaoInformadoImp"
					objeto="ecar.pojo.SituacaoSit" 
					label="descricaoSit" 
					value="codSit" 
					order="descricaoSit"
					selected="<%=selectedSituacaoSitNaoInformadoImp%>"
					disabled="<%=!"disabled".equals(_disabled) ? "false" : "true"%>"
				/>
				</td>
			</tr>
			
			<tr>
				<td class="label"><%=_obrigatorio%> Valor sem correspondência</td>
				<td>
				<%
					String selectedSituacaoSitSemCorrespondenteImp = "";
						
					if (perfilIntercambioDados != null && perfilIntercambioDados.getTipoFuncionalidade().equals(TipoFuncionalidadeEnum.CADASTRO)) {
						if(((PerfilIntercambioDadosCadastroPidc)perfilIntercambioDados).getSituacaoSemCorrespondentePidc()!= null){
							selectedSituacaoSitSemCorrespondenteImp = ((PerfilIntercambioDadosCadastroPidc)perfilIntercambioDados).getSituacaoSemCorrespondentePidc().getCodSit().toString();
						}
					}
				%>
				<combo:ComboTag 
					nome="situacaoSitSemCorrespondenteImp"
					objeto="ecar.pojo.SituacaoSit" 
					label="descricaoSit" 
					value="codSit" 
					order="descricaoSit"
					selected="<%=selectedSituacaoSitSemCorrespondenteImp%>"
					disabled="<%=!"disabled".equals(_disabled) ? "false" : "true"%>"
				/>
				</td>
			</tr>
			
			<tr> <td colspan="2">&nbsp;</td> </tr>
			
			<tr>
				<td class="label">Associação</td>		
				<td>&nbsp;</td>		
			</tr>
			
			<tr>
				<td class="label"><%=_obrigatorio%> Estrutura base</td>
				<td>
				<%
				
					String idStrEstruturaBaseSelecionada = "";
					
					if (idEstruturaBaseSelecionada != null){
						idStrEstruturaBaseSelecionada = idEstruturaBaseSelecionada.toString(); 
					}
					
					if (perfilIntercambioDados != null && perfilIntercambioDados.getTipoFuncionalidade().equals(TipoFuncionalidadeEnum.CADASTRO)) {
						if(((PerfilIntercambioDadosCadastroPidc)perfilIntercambioDados).getEstruturaBasePidc()!= null){
							idEstruturaBaseSelecionada = ((PerfilIntercambioDadosCadastroPidc)perfilIntercambioDados).getEstruturaBasePidc().getCodEtt();
							idStrEstruturaBaseSelecionada = idEstruturaBaseSelecionada.toString(); 
						}
					}
				%>
				<combo:ComboTag 
					nome="estruturaEttBaseImp"
					objeto="ecar.pojo.EstruturaEtt" 
					label="nomeEtt" 
					value="codEtt" 
					order="nomeEtt"
					filters="indAtivoEtt=S;virtual=false"
					selected="<%=idStrEstruturaBaseSelecionada%>"
					scripts="onchange=atualizarEstruturaItemNivelSuperior(document.form)"
					disabled="<%=!"disabled".equals(_disabled) ? "false" : "true"%>"
				/>
				</td>
			</tr>
			
			<%
				if (!ehPesquisa) {
			%>
			<tr>
				<td class="label"><%=_obrigatorio%> Atributo na estrutura base</td>
				<td>
				<%
					String selectedAtributoBaseImp = "";
						
						AtributosAtb atributosAtb = atributoDao.getAtributosAtbByNomeAtb(PerfilIntercambioDadosDao.ATRIBUTO_ATB_VALOR_ASSOCIACAO);
						
						if (atributosAtb != null){
							selectedAtributoBaseImp = atributosAtb.getCodAtb().toString();
						}
				%>
				<input type="hidden" name="atributoBaseImp" value="<%=selectedAtributoBaseImp%>">
				<combo:ComboTag 
					nome="atributoBaseImp_disabled"
					objeto="ecar.pojo.AtributosAtb" 
					label="labelPadraoAtb" 
					value="codAtb" 
					order="nomeAtb"
					filters="indAtivoAtb=S"
					selected="<%=selectedAtributoBaseImp%>"
					disabled="true"
				/>
				</td>
			</tr>
			<%
				}
			%>
			
			<tr>
				<td class="label"><%=_obrigatorio%> Estrutura do item de nível superior</td>
				<td>
					<%
				String idStrEstruturaItemNivelSuperiorSelecionada = "";
				
				List listEstruturasEttItemNivelSuperiorImp = new ArrayList();
				EstruturaEtt estruturaEttBaseImp = null;
				EstruturaEtt estruturaEttItemNivelSuperiorImp = null;
				
				//Inclusão
				if (idEstruturaBaseSelecionada != null && idEstruturaBaseSelecionada.longValue()>0){
					estruturaEttBaseImp = (EstruturaEtt) estruturaDao.buscar(EstruturaEtt.class, idEstruturaBaseSelecionada);
					if (estruturaEttBaseImp!=null)
						listEstruturasEttItemNivelSuperiorImp = estruturaDao.getEstruturasReaisOuVirtuais(false, estruturaEttBaseImp);
				} 

				if (idEstruturaItemNivelSuperiorSelecionada != null && idEstruturaItemNivelSuperiorSelecionada.longValue()>0){
					idStrEstruturaItemNivelSuperiorSelecionada = idEstruturaItemNivelSuperiorSelecionada.toString(); 
				}
				
				
				if (perfilIntercambioDados != null && perfilIntercambioDados.getTipoFuncionalidade().equals(TipoFuncionalidadeEnum.CADASTRO)) {
					if(((PerfilIntercambioDadosCadastroPidc)perfilIntercambioDados).getEstruturaItemNivelSuperiorPidc()!= null && idEstruturaBaseSelecionada != null && !idEstruturaBaseSelecionada.equals(Long.valueOf(0))){
						idStrEstruturaItemNivelSuperiorSelecionada = ((PerfilIntercambioDadosCadastroPidc)perfilIntercambioDados).getEstruturaItemNivelSuperiorPidc().getCodEtt().toString();
						estruturaEttItemNivelSuperiorImp = ((PerfilIntercambioDadosCadastroPidc)perfilIntercambioDados).getEstruturaItemNivelSuperiorPidc();
						listEstruturasEttItemNivelSuperiorImp = estruturaDao.getEstruturasReaisOuVirtuais(false, (((PerfilIntercambioDadosCadastroPidc)perfilIntercambioDados).getEstruturaBasePidc()));
					}
				}
				
				%>
				<combo:ComboTag 
					nome="estruturaEttItemNivelSuperiorImp"
					objeto="ecar.pojo.EstruturaEtt" 
					label="nomeEtt" 
					value="codEtt" 
					order="nomeEtt"
					colecao="<%=listEstruturasEttItemNivelSuperiorImp%>"
					filters="indAtivoEtt=S;virtual=false"
					selected="<%=idStrEstruturaItemNivelSuperiorSelecionada%>"
					scripts="onchange=atualizarEstruturaCriacaoItem(document.form)"
					disabled="<%=!"disabled".equals(_disabled) ? "false" : "true"%>"
				/>
				</td>
			</tr>
			<%
				if (!ehPesquisa) {
			%>
			
			<tr>
				<td class="label"><%=_obrigatorio%> Atributo na estrutura do item de nível superior</td>
				<td>
				<%
				String selectedAtributoNivelSuperior = "";
					
				AtributosAtb atributosAtbSuperior = atributoDao.getAtributosAtbByNomeAtb(PerfilIntercambioDadosDao.ATRIBUTO_ATB_PADRAO_TIPO_EMPREENDIMENTO);
				
				if(atributosAtbSuperior != null) {
					selectedAtributoNivelSuperior = atributosAtbSuperior.getCodAtb().toString();
				}
				%>
				<input type="hidden" name="atributoNivelSuperiorImp" value="<%=selectedAtributoNivelSuperior%>">
				<combo:ComboTag 
					nome="atributoNivelSuperiorImp_disabled"
					objeto="ecar.pojo.AtributosAtb" 
					label="labelPadraoAtb" 
					value="codAtb" 
					order="nomeAtb"
					filters="indAtivoAtb=S"
					selected="<%=selectedAtributoNivelSuperior%>"
					disabled="true"
				/>
				</td>
			</tr>
			<%
				}
			%>
			
			<tr>
				<td class="label"><%=_obrigatorio%> Estrutura de criação de itens</td>
				<td>
				<%
				String selectedEstruturaEttCriacaoItemImp = (String) session.getAttribute("estruturaEttCriacaoItemImp");
				List listEstruturasEttCriacaoItemImp = new ArrayList();
				EstruturaEtt estruturaEttCriacaoItemImp = null;
				
				
				if (idEstruturaItemNivelSuperiorSelecionada != null && idEstruturaItemNivelSuperiorSelecionada.longValue()>0){
					estruturaEttItemNivelSuperiorImp = (EstruturaEtt)estruturaDao.buscar(EstruturaEtt.class,idEstruturaItemNivelSuperiorSelecionada);
					
					if (estruturaEttItemNivelSuperiorImp!=null)
						listEstruturasEttCriacaoItemImp = estruturaDao.getEstruturasReais(estruturaEttItemNivelSuperiorImp);
				}
				
				if (perfilIntercambioDados != null && perfilIntercambioDados.getTipoFuncionalidade().equals(TipoFuncionalidadeEnum.CADASTRO)) {
					if(((PerfilIntercambioDadosCadastroPidc)perfilIntercambioDados).getEstruturaCriacaoItemPidc() != null && (idEstruturaItemNivelSuperiorSelecionada != null || estruturaEttItemNivelSuperiorImp != null)){
					    selectedEstruturaEttCriacaoItemImp = ((PerfilIntercambioDadosCadastroPidc)perfilIntercambioDados).getEstruturaCriacaoItemPidc().getCodEtt().toString();
						listEstruturasEttCriacaoItemImp = estruturaDao.getEstruturasReais (((PerfilIntercambioDadosCadastroPidc)perfilIntercambioDados).getEstruturaItemNivelSuperiorPidc());
					}
				}
				
				
				%>
				<combo:ComboTag 
					nome="estruturaEttCriacaoItemImp"
					objeto="ecar.pojo.EstruturaEtt" 
					label="nomeEtt" 
					value="codEtt" 
					order="nomeEtt"
					colecao="<%=listEstruturasEttCriacaoItemImp%>"
					filters="indAtivoEtt=S;virtual=false"
					selected="<%=selectedEstruturaEttCriacaoItemImp%>"
					disabled="<%=!"disabled".equals(_disabled) ? "false" : "true"%>"
				/>
				</td>
			</tr>

			<tr>
				<td class="label">Dados de Manutenção dos Itens</td>		
				<td>&nbsp;</td>		
			</tr>

			<tr>
				<td class="label"><%=_obrigatorio%> Grupo de acesso <br> aos itens incluídos</td>
				<td>
				<%
				String selectedSisAtributoSatbAcessoPermItemImp = request.getParameter("sisAtributoSatbAcessoPermItemImp");
		
				if (perfilIntercambioDados != null && perfilIntercambioDados.getTipoFuncionalidade().equals(TipoFuncionalidadeEnum.CADASTRO)) {
					if(((PerfilIntercambioDadosCadastroPidc)perfilIntercambioDados).getGrupoAcessoItensImportadosPidc()!= null){
						selectedSisAtributoSatbAcessoPermItemImp = ((PerfilIntercambioDadosCadastroPidc)perfilIntercambioDados).getGrupoAcessoItensImportadosPidc().getCodSatb().toString();
					}
				}
				%>
				<combo:ComboTag 
					nome="sisAtributoSatbAcessoPermItemImp"
					objeto="ecar.pojo.SisAtributoSatb" 
					label="descricaoSatb" 
					value="codSatb" 
					order="descricaoSatb"
					colecao="<%=sisAtributoDao.getListaAcesso()%>"
					selected="<%=selectedSisAtributoSatbAcessoPermItemImp%>"
					disabled="<%=!"disabled".equals(_disabled) ? "false" : "true"%>"
				/>
				</td>
			</tr>
			
			<tr> <td colspan="2">&nbsp;</td> </tr>
			
		</table>
	</div>
	
	<%
	if (!ehPesquisa) {
	%>
	
		<div id="seleciona_tecnologia">
			<table class="form"> 
		
				<tr> <td colspan="2">&nbsp;</td> </tr>
				
				<tr>
					<td class="label">Tecnologia de Comunicação</td>		
					<td>&nbsp;</td>		
				</tr>
				<tr>
					<td class="label"><%=_obrigatorio%> Nome</td>
					<td>
						<select name="tipoTecnologia" disabled="disabled">
							<% 
							TipoTecnologiaEnum[] tipoTecnologiaEnum = TipoTecnologiaEnum.values();
							
							for (int i=0; i<tipoTecnologiaEnum.length;i++){%>
								
								<option selected="selected" value="<%=tipoTecnologiaEnum[i].getCodigo() %>" ><%=tipoTecnologiaEnum[i].getDescricao() %></option>
							 <% } %>
						</select>
					</td>
				</tr>
				
			</table>	
		</div>
	
	<%}
		if (!ehPesquisa) {
	%>
	<div id="dados_tecnologia_geral">
		<table class="form">
		 	<tr>
				<td class="label">Especificação de Comunicação</td>		
				<td>&nbsp;</td>		
			</tr>
		 
			<tr>
				<td class="label"><%=_obrigatorio%> Nome</td>		
				<td>
					<input type="text" disabled="disabled" name="nomeLayoutAquivoPflid" size="35" maxlength="40" value="<%=TipoTecnologiaEnum.ARQUIVOTEXTO_PACINTER.getEspecificacao()%>" >
				</td>		
			</tr>
			
			<tr>
				<td class="label"><%=_obrigatorio%> Versão</td>
				<td>
					<input type="text" disabled="disabled" name="versaoLayoutArquivoPflid" size="35" maxlength="20"	value="<%=TipoTecnologiaEnum.ARQUIVOTEXTO_PACINTER.getVersao()%>">
				</td>		
			</tr>
			
			<tr> <td colspan="2">&nbsp;</td> </tr>
		 	
		 	<tr>
				<td class="label">Codificação de Caracteres</td>		
				<td>&nbsp;</td>		
			</tr>

			<tr> <td colspan="2">&nbsp;</td> </tr>
			
			<tr>
				<td class="label"><%=_obrigatorio%> Padrão usado</td>		
				<td>
					<input type="text" disabled="disabled" name="codificacaoCaracteresArquivoPflid" size="35" maxlength="20" value="<%=Dominios.ENCODING_DEFAULT%>" >
				</td>		
			</tr>

			<tr> <td colspan="2">&nbsp;</td> </tr>
		</table>
	</div>
	<%
			}
	%>
	
	<div id="dados_tecnologia_txt">
		<table class="form">
		<%
		if (!ehPesquisa) {
		%>
			<tr>
				<td class="label"><%=_obrigatorio%> Rejeitar se nomenclatura diferente da esperada</td>
				<td>
					<input type="radio" class="form_check_radio" name="indRejeitarNomenclaturaDiferentePflid" value="<%=Dominios.SIM%>" disabled="disabled">Sim 
					<input type="radio" class="form_check_radio" name="indRejeitarNomenclaturaDiferentePflid" value="<%=Dominios.NAO%>" checked="checked" disabled="disabled">N&atilde;o
				</td>
			</tr>
		<%} %>
			<tr>
				<td class="label"><%=_obrigatorio%> Separador de campos</td>
				<td>
					<%
					String separador = "";
					
					if (request.getParameter("separador") != null)
					   separador = request.getParameter("separador");
					else
					if (perfilIntercambioDados != null && ((DadosTecnologiaPerfilTxtDtpt)perfilIntercambioDados.getDadosTecnologiaPerfilDtp()).getSeparadorCamposDtpt() != null) {
						separador = ((DadosTecnologiaPerfilTxtDtpt)perfilIntercambioDados.getDadosTecnologiaPerfilDtp()).getSeparadorCamposDtpt(); 
					}
					%>
					<input type="text" name="separadorCamposPflid" size="35" maxlength="4" value="<%=separador%>" <%=_disabled%>>
				</td>		
			</tr>
					
			<%if (exibirDadosManutencao){
			%>
				<table class="form">
			 	<tr>
					<td class="label">Data/hora de inclusão</td>		
					<td>
					<%
						String dataInclusaoPflid = "";
							
						if(perfilIntercambioDados != null && perfilIntercambioDados.getDataInclusaoPflid() != null){
						    dataInclusaoPflid = Data.parseDateHourMinuteSecond(perfilIntercambioDados.getDataInclusaoPflid());
						}		
					%>
					<%=dataInclusaoPflid%>
					</td>		
				</tr>
				<tr>
					<td class="label">Usuário de inclusão</td>		
					<td>
					<%
						String usuarioInclusaoPflid = "";
							
						if(perfilIntercambioDados != null && perfilIntercambioDados.getUsuarioInclusaoPflid() != null){
						    usuarioInclusaoPflid = perfilIntercambioDados.getUsuarioInclusaoPflid().getNomeUsu();
						}		
					%>
					<%=usuarioInclusaoPflid%>
					</td>		
				</tr>
				<tr>
					<%
						String dataAlteracaoPflid = "";
							
						if(perfilIntercambioDados != null && perfilIntercambioDados.getDataAlteracaoPflid() != null){
						    dataAlteracaoPflid = Data.parseDateHourMinuteSecond(perfilIntercambioDados.getDataAlteracaoPflid());
						    
					%>
							<td class="label">Data/hora da última modificação</td>		
							<td>
					<%
						}		
					%>
					
					<%=dataAlteracaoPflid%>
					</td>		
				</tr>
				<tr>					
					<%
						String usuarioAlteracaoPflid = "";
							
						if(perfilIntercambioDados != null && perfilIntercambioDados.getUsuarioAlteracaoPflid() != null){
						    usuarioAlteracaoPflid = perfilIntercambioDados.getUsuarioAlteracaoPflid().getNomeUsu();
						    
					%>
						    <td class="label">Usuário da última modificação</td>		
							<td>
					<%
						}		
					%>
					<%=usuarioAlteracaoPflid%>
					</td>		
				</tr>
			</table>
		<%} %>
	
			
		</table>
		
	
		
	</div>
	
	<div id="dados_tecnologia_ws" style="display: none;">
		<table class="form">
		 	<tr>
				<td class="label">Intervalo de Seleção dos Elementos</td>		
				<td>&nbsp;</td>		
			</tr>
		 
			<tr>
				<td class="label"><%=_obrigatorio%> Modo</td>		
				<td>
					<input type="radio" <%=_disabled%> class="form_check_radio" name="modoSelecaoElementos" value="<%=Dominios.PERFIL_MODO_PROCESSAMENTO_AUTOMATICO%>" >Automático 
					<input type="radio" <%=_disabled%> class="form_check_radio" name="modoSelecaoElementos" value="<%=Dominios.PERFIL_MODO_PROCESSAMENTO_MANUAL%>" >Manual
				</td>		
			</tr>
			
			<tr>
				<td class="label"><%=_obrigatorio%> Servidores</td>
				<td>&nbsp;</td>		
			</tr>

			<tr>
				<td class="label"><%=_obrigatorio%> Primário</td>
				<td>&nbsp;</td>		
			</tr>
			
			<tr>
				<td class="label"><%=_obrigatorio%> Localização</td>		
				<td>&nbsp;</td>		
			</tr>

			<tr>
				<td class="label"><%=_obrigatorio%> Protocolo</td>		
				<td>
					<input type="radio" <%=_disabled%> class="form_check_radio" name="protocoloServidorPrimario" value="<%=Dominios.PERFIL_PROTOCOLO_HTTP%>" checked="checked" ><%=Dominios.PERFIL_PROTOCOLO_HTTP%> 
					<input type="radio" <%=_disabled%> class="form_check_radio" name="protocoloServidorPrimario" value="<%=Dominios.PERFIL_PROTOCOLO_HTTPS%>"  ><%=Dominios.PERFIL_PROTOCOLO_HTTPS%>
				</td>		
			</tr>
			<tr>
				<td class="label"><%=_obrigatorio%> Tipo host</td>		
				<td>
					<input type="radio" <%=_disabled%> class="form_check_radio" name="tipoHostServidorPrimario" value="<%=Dominios.PERFIL_TIPO_HOST_DOMINIO%>" >Domínio
					<input type="radio" <%=_disabled%> class="form_check_radio" name="tipoHostServidorPrimario" value="<%=Dominios.PERFIL_TIPO_HOST_IP%>" checked="checked" >IP
				</td>		
			</tr>
			<tr>
				<td class="label"><%=_obrigatorio%> Host</td>		
				<td>
					<input type="text" name="hostServidorPrimario" size="50" maxlength="50" value="" <%=_disabled%>>
				</td>		
			</tr>
			<tr>
				<td class="label"><%=_obrigatorio%> Porta</td>		
				<td>
					<input type="text" name="portaServidorPrimario" size="4" maxlength="4" value="80" <%=_disabled%>>
				</td>		
			</tr>
			<tr>
				<td class="label"><%=_obrigatorio%> Serviço</td>		
				<td>
					<input type="text" name="servicoServidorPrimario" size="50" maxlength="50" value="" <%=_disabled%>>
				</td>		
			</tr>
			
			<tr>
				<td class="label"><%=_obrigatorio%> Acesso</td>		
				<td>&nbsp;</td>		
			</tr>
			
			<tr>
				<td class="label"><%=_obrigatorio%> Login</td>		
				<td>
					<input type="text" name="loginServidorPrimario" size="50" maxlength="50" value="">
				</td>		
			</tr>
			
			<tr>
				<td class="label"><%=_obrigatorio%> Senha</td>		
				<td>
					<input type="password" name="senhaServidorPrimario" size="50" maxlength="50" value="">
				</td>		
			</tr>
			
			<tr>
				<td class="label"><%=_obrigatorio%> Confirmação de senha</td>		
				<td>
					<input type="password"" name="confirmacaoSenhaServidorPrimario" size="50" maxlength="50" value="">
				</td>		
			</tr>
						
			<tr> <td colspan="2">&nbsp;</td> </tr>

			<tr>
				<td class="label"><%=_obrigatorio%> Secundário</td>
				<td>&nbsp;</td>		
			</tr>
			
			<tr>
				<td class="label"><%=_obrigatorio%> Localização</td>		
				<td>&nbsp;</td>		
			</tr>

			<tr>
				<td class="label"><%=_obrigatorio%> Protocolo</td>		
				<td>
					<input type="radio" <%=_disabled%> class="form_check_radio" name="protocoloServidorSecundario" value="<%=Dominios.PERFIL_PROTOCOLO_HTTP%>" checked="checked" ><%=Dominios.PERFIL_PROTOCOLO_HTTP%> 
					<input type="radio" <%=_disabled%> class="form_check_radio" name="protocoloServidorSecundario" value="<%=Dominios.PERFIL_PROTOCOLO_HTTPS%>"  ><%=Dominios.PERFIL_PROTOCOLO_HTTPS%>
				</td>		
			</tr>
			<tr>
				<td class="label"><%=_obrigatorio%> Tipo host</td>		
				<td>
					<input type="radio" <%=_disabled%> class="form_check_radio" name="tipoHostServidorSecundário" value="<%=Dominios.PERFIL_TIPO_HOST_DOMINIO%>" >Domínio
					<input type="radio" <%=_disabled%> class="form_check_radio" name="tipoHostServidorSecundário" value="<%=Dominios.PERFIL_TIPO_HOST_IP%>" checked="checked" >IP
				</td>		
			</tr>
			<tr>
				<td class="label"><%=_obrigatorio%> Host</td>		
				<td>
					<input type="text" name="hostServidorSecundário" size="50" maxlength="50" value="" <%=_disabled%>>
				</td>		
			</tr>
			<tr>
				<td class="label"><%=_obrigatorio%> Porta</td>		
				<td>
					<input type="text" name="portaServidorSecundário" size="4" maxlength="4" value="80" <%=_disabled%>>
				</td>		
			</tr>
			<tr>
				<td class="label"><%=_obrigatorio%> Serviço</td>		
				<td>
					<input type="text" name="servicoServidorSecundario" size="50" maxlength="50" value="" <%=_disabled%>>
				</td>		
			</tr>
			
						<tr>
				<td class="label"><%=_obrigatorio%> Acesso</td>		
				<td>&nbsp;</td>		
			</tr>
			
			<tr>
				<td class="label"><%=_obrigatorio%> Login</td>		
				<td>
					<input type="text" name="loginServidorSecundario" size="50" maxlength="50" value="">
				</td>		
			</tr>
			
			<tr>
				<td class="label"><%=_obrigatorio%> Senha</td>		
				<td>
					<input type="password" name="senhaServidorSecundario" size="50" maxlength="50" value="">
				</td>		
			</tr>
			
			<tr>
				<td class="label"><%=_obrigatorio%> Confirmação de senha</td>		
				<td>
					<input type="password"" name="confirmacaoSenhaServidorSecundario" size="50" maxlength="50" value="">
				</td>		
			</tr>
						
			<tr> <td colspan="2">&nbsp;</td> </tr>
			
		</table>	
	</div>

	
	<%@ include file="../../include/estadoMenu.jsp"%>
	
</html>