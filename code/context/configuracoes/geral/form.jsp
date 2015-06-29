<%@ include file="../../login/validaAcesso.jsp"%>

<%@ taglib uri="/WEB-INF/ecar-util.tld" prefix="util" %>
<%@ taglib uri="/WEB-INF/ecar-combo.tld" prefix="combo" %>

<%@ page import="ecar.pojo.ConfiguracaoCfg" %>
<%@ page import="ecar.pojo.TipoFuncAcompTpfa" %>
<%@ page import="ecar.pojo.SisAtributoSatb" %>
<%@ page import="ecar.pojo.SisGrupoAtributoSga" %>
<%@ page import="ecar.pojo.Estilo" %>

<%@ page import="ecar.dao.ConfiguracaoDao" %>
<%@ page import="ecar.dao.TipoFuncAcompDao" %>
<%@ page import="ecar.dao.SisAtributoDao" %>
<%@ page import="ecar.dao.SisGrupoAtributoDao" %>
<%@ page import="ecar.dao.ConfigMailCfgmDAO" %>
<%@ page import="ecar.dao.EstiloDao" %>

<%@ page import="java.util.Iterator" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.Set" %>
<%@ page import="ecar.pojo.ConfigMailCfgm" %>
<%@ page import="ecar.dao.TextosSiteDao" %>
<%@ page import="ecar.pojo.TextosSiteTxt" %>

<%@ include file="../../frm_global.jsp"%>
 
<%
/* remove um objeto de pesquisa que pode ter sido utilizado em outra tela */
session.removeAttribute("objPesquisa");
%>

<html lang="pt-br">
<head>
<%@ include file="../../include/meta.jsp"%>
<%@ include file="/titulo.jsp"%>
<link rel="stylesheet" href="<%=_pathEcar%>/css/style_<%=configuracaoCfg.getEstilo().getNome()%>.css" type="text/css">
<script language="javascript" src="../../js/menu_retratil.js"></script>
<script language="javascript" src="../../js/focoInicial.js"></script>
<script language="javascript" src="../../js/frmPadrao.js"></script>
<script language="javascript" src="../../js/validacoes.js"></script>
<script language="javascript">
function validarAlterar(form){
/*	if(!validaString(form.estruturaEtt, "Estrutura Principal", true)){
		return false;
	}	
*/
	if(!validaString(form.nomeEstruturaCfg, "Nome da Estrutura Principal", true)){
		return false;
	}			
	if(!validaString(form.periodicidadePrdc, "Periodicidade", true)){
		return false;
	}	
	if(form.indGerarHistoricoCfg[0].checked == false && form.indGerarHistoricoCfg[1].checked == false){
		alert("<%=_msg.getMensagem("geral.validacao.indGerarHistoricoCfg.obrigatorio")%>");
		return false;
	}
	if(form.tpArqIntegFinanceiraCfg[0].checked == false && form.tpArqIntegFinanceiraCfg[1].checked == false){
		alert("<%=_msg.getMensagem("geral.validacao.tpArqIntegFinanceiraCfg.obrigatorio")%>");
		return false;
	}			
	if(!validaString(form.codSgaGrAtrClAcesso, "Grupo de Atributos para Grupo de Acesso", true)){
		return false;
	}	
	if(!validaString(form.codSgaGrAtrPgIni, "Grupo de Atributos para Página Inicial do Usuário", true)){
		return false;
	}	
	if(!validaString(form.codSapadrao, "Página Inicial do Sistema", true)){
		return false;
	}	
	if(!validaString(form.codSgaGrAtrLeiCapa, "Grupo de Atributos para Leiaute de Capa", true)){
		return false;
	}		
	if(!validaString(form.codSacapa, "Leiaute de Capa do Sistema", true)){
		return false;
	}	
	if(!validaString(form.atrTpAcesso, "Grupo de Atributos para Tipo de Acesso", true)){
		return false;
	}		
	if(!validaString(form.codSaUsu, "Atributos de Usuário Não Autenticado", true)){
		return false;
	}
	if(!validaString(form.atrNvPlan, "Grupo de Atributos para Nível de Planejamento", true)){
		return false;
	}
	if(!validaString(form.separadorArqTXT, "Caracter Separador em Arq. Textos", true)) {
		return false;
	}
	if(!validaString(form.separadorCampoMultivalor, "Caracter Separador em Campos Multivalorados", true)) {
		return false;
	}
	if(Trim(form.separadorCampoMultivalor.value) == Trim(form.separadorArqTXT.value)) {
		alert("<%=_msg.getMensagem("geral.validacao.separadoresTexto.iguais")%>");
		return false;
	}
	
	if(form.indOcultarObservacoesParecer[0].checked == false && form.indOcultarObservacoesParecer[1].checked == false){
		alert("<%=_msg.getMensagem("geral.validacao.indOcultarObservacoesParecer.obrigatorio")%>");
		return false;
	}

	<% String[] args; %>

	
	if (!isInteger(form.numRegistros.value)) {
		<% args = new String[]{"\'\"+form.numRegistros.value+\"\'","\'Número de Registros no PopUp de Pesquisa\'"}; %>

		alert("<%=_msg.getMensagem("geral.validacao.numero.inteiro",args)%>");
		form.numRegistros.focus();

		return false;
	}

	
	if (!isInteger(form.qteItensGalAnexo.value)) {
		<% args = new String[]{"\'\"+form.qteItensGalAnexo.value+\"\'","\'Número de Itens na galeria de Anexos\'"}; %>

		alert("<%=_msg.getMensagem("geral.validacao.numero.inteiro",args)%>");
		form.qteItensGalAnexo.focus();
		
		return false;
	}

	if (!isInteger(form.nuItensExibidosPaginacao.value)) {
		<% args = new String[]{"\'\"+form.nuItensExibidosPaginacao.value+\"\'","\'Número de Itens a serem Exibidos na Paginação de Demandas\'"}; %>

		alert("<%=_msg.getMensagem("geral.validacao.numero.inteiro",args)%>");
		form.nuItensExibidosPaginacao.focus();
		
		return false;
	}

	if (!isInteger(form.intervaloAtualizacaoEmail.value)) {
		<% args = new String[]{"\'\"+form.intervaloAtualizacaoEmail.value+\"\'","\'Intervalo entre atualizações de e-mails\'"}; %>

		alert("<%=_msg.getMensagem("geral.validacao.numero.inteiro",args)%>");
		form.intervaloAtualizacaoEmail.focus();
		
		return false;
	}

	if (!isInteger(form.diasAntecedenciaMailCfg.value)) {
		<% args = new String[]{"\'\"+form.diasAntecedenciaMailCfg.value+\"\'","\'Dias de antecedência para envio de lembrete\'"}; %>

		alert("<%=_msg.getMensagem("geral.validacao.numero.inteiro",args)%>");
		form.diasAntecedenciaMailCfg.focus();
		
		return false;
	}
		
	return true;
}
function reload(form){
	form.hidAcao.value="reload";
	form.action = "form.jsp";
	form.submit();
}
</script>
</head>

<!-- sempre colocar o foco inicial no primeiro campo -->
<body onload="javascript:onLoad(document.form)">

<%@ include file="../../cabecalho.jsp" %>
<%@ include file="../../divmenu.jsp"%>

<div id="conteudo">

<form name="form" method="post">
	<input type="hidden" name="hidAcao" value="">

	<!-- TITULO -->
	<%@ include file="/titulo_tela.jsp"%>
	
	<util:barrabotoes alterar="Gravar" cancelar="Cancelar"/>
	<%
	try{	 
				//ConfiguracaoCfg configuracao = new ConfiguracaoCfg();
				ConfiguracaoDao configuracaoDao = new ConfiguracaoDao(request);
				ConfigMailCfgmDAO configMailDao = new ConfigMailCfgmDAO(request);
				
				/* variável que serva para controlar se os dados da configuração estão sendo recuperados do request ou do banco */
				boolean objetoPorRequest = false;
				
				if("reload".equals(Pagina.getParamStr(request, "hidAcao"))){
					/* se está vindo do reload, recupera informações da configuração por request */
					configuracaoDao.setConfiguracao(request, configuracao);				
					objetoPorRequest = true;
				} else{
					/* senão, recupera objeto configuração do banco de dados */
					objetoPorRequest = false;
					List confg = configuracaoDao.listar(ConfiguracaoCfg.class, null);					
					if(confg != null && confg.size() > 0)
						configuracao = (ConfiguracaoCfg) confg.iterator().next();
				}
	%>
	<table class="form">
	<input type="hidden" name="codCfg" value="<%=Pagina.trocaNull(configuracao.getCodCfg())%>">
	
	
	<!-- Este parametro guarda o endereço do servidor que será usado no envio de email de eventos gerados pelo sistema-->
	<!-- Atualizado na tela de login com o servidor do request-->		
	<!--input type="hidden" name="contextPath" value=""-->

	<util:msgObrigatorio obrigatorio="<%=_obrigatorio%>" />

	<tr>
		<td class="label"><%=_obrigatorio%> Nome da Estrutura Principal</td>		
		<td><input type="text" name="nomeEstruturaCfg" size="35" maxlength="30" 
					value="<%=Pagina.trocaNull(configuracao.getNomeEstruturaCfg())%>">
		</td>		
	</tr>
	
						<tr>
						<td class="label">
							<%=_obrigatorio%> Opção Default para Exibir Estrutura
						</td>
						<td>
							<select name="exibicaoDefaultEstruturaCfg">
								<option value="T" <%if("T".equals(configuracao.getExibDefaultEstCfg())){%>selected<% }%> >
									Todos
								</option>
								<option value="C" <%if("C".equals(configuracao.getExibDefaultEstCfg())){%>selected<% }%> >
									Concluídos
								</option>
								<option value="N" <%if("N".equals(configuracao.getExibDefaultEstCfg())){%>selected<% }%> >
									Não Concluídos
								</option>
							</select>
						</td>

					</tr>
	
	
	<tr>
		<td class="label"><%=_obrigatorio%> Periodicidade Padr&atilde;o</td>
		<td>
		<%
		String selectedPeriodicidade = "";
		if(configuracao.getPeriodicidadePrdc() != null){
			selectedPeriodicidade = configuracao.getPeriodicidadePrdc().getCodPrdc().toString();
		}
		%>
		<combo:ComboTag 
						nome="periodicidadePrdc"
						objeto="ecar.pojo.PeriodicidadePrdc" 
						label="descricaoPrdc" 
						value="codPrdc" 
						order="descricaoPrdc"
						selected="<%=selectedPeriodicidade%>"
		/>
		</td>
	</tr>	
	<tr>
		<td class="label"><%=_obrigatorio%> Gerar Hist&oacute;rico</td>
		<td>
			<input type="radio" class="form_check_radio" name="indGerarHistoricoCfg" value="S" 
				<%=Pagina.isChecked(configuracao.getIndGerarHistoricoCfg(), "S")%> 
				<%=_disabled%>>Sim 
			<input type="radio" class="form_check_radio" name="indGerarHistoricoCfg" value="N" 
				<%=Pagina.isChecked(configuracao.getIndGerarHistoricoCfg(), "N")%>
				<%=_disabled%>>N&atilde;o
		</td>
	</tr>
	<tr>
		<td class="label"><%=_obrigatorio%> Exibir &Aacute;rvore de Navega&ccedil;&atilde;o</td>
		<td>
			<input type="radio" class="form_check_radio" name="indExibirArvoreNavegacaoCfg" value="S" 
				<%=Pagina.isChecked(configuracao.getIndExibirArvoreNavegacaoCfg(), "S")%> 
				<%=_disabled%>>Sim 
			<input type="radio" class="form_check_radio" name="indExibirArvoreNavegacaoCfg" value="N" 
				<%=Pagina.isChecked(configuracao.getIndExibirArvoreNavegacaoCfg(), "N")%>
				<%=_disabled%>>N&atilde;o
		</td>
	</tr>
	<tr>
		<td class="label"><%=_obrigatorio%> Arquivo Integra&ccedil;&atilde;o Financeira</td>
		<td>
			<input type="radio" class="form_check_radio" name="tpArqIntegFinanceiraCfg" value="TXT" 
				<%=Pagina.isChecked(configuracao.getTpArqIntegFinanceiraCfg(), "TXT")%> 
				<%=_disabled%>>TXT 
			<input type="radio" class="form_check_radio" name="tpArqIntegFinanceiraCfg" value="XML" 
				<%=Pagina.isChecked(configuracao.getTpArqIntegFinanceiraCfg(), "XML")%>
				<%=_disabled%>>XML
		</td>
	</tr>	
	
<!--  Comentado por não existir no HBM da configuração o campo "caminha" -->
<!--	<tr>-->
<!--		<td class="label"><%=_obrigatorio%> Caminho Exporta&ccedil;&atilde;o <br> Integra&ccedil;&atilde;o Financeira</td>		-->
<!--		<td><textarea name="caminha" rows="4" cols="60"></textarea>-->
<!--		</td>		-->
<!--	</tr>	-->
	<tr>
		<td class="label">Grupo de Atributos Utilizados</td>		
		<td>&nbsp;</td>		
	</tr>	
	<tr>
		<td class="label"><%=_obrigatorio%> Grupo de Atributos para Grupo de Acesso</td>
		<td>
		<%
		String selectedCodSgaGrAtrClAcesso = "";
		
		if(objetoPorRequest){
			if(!"".equals(Pagina.getParamStr(request,"codSgaGrAtrClAcesso"))){
				selectedCodSgaGrAtrClAcesso = Pagina.getParamStr(request,"codSgaGrAtrClAcesso");
			}		
		}
		else{
			if(configuracao.getSisGrupoAtributoSgaByCodSgaGrAtrClAcesso()!= null){
				selectedCodSgaGrAtrClAcesso = configuracao.getSisGrupoAtributoSgaByCodSgaGrAtrClAcesso().getCodSga().toString();
			} 
		}
		String scripts = " onchange=reload(document.form) title=\""+_msg.getMensagem("geral.grAtributos.instrucao") + "\"";
		if(configuracao.getCodCfg() != null)
			scripts += " title=\""+_msg.getMensagem("geral.grAtributos.instrucao") + "\"";
		%>
		<combo:ComboTag 
						nome="codSgaGrAtrClAcesso"
						objeto="ecar.pojo.SisGrupoAtributoSga" 
						label="descricaoSga" 
						value="codSga" 
						order="descricaoSga"
						selected="<%=selectedCodSgaGrAtrClAcesso%>"
						scripts="<%=scripts%>"
						filters="indAtivoSga=S"
		/>
		</td>
	</tr>
	<tr>
		<td class="label"><%=_obrigatorio%> Grupo de Atributos para <br> P&aacute;gina Inicial do Usu&aacute;rio</td>
		<td>
		<%
		String selectedCodSgaGrAtrPgIni = "";
		if(objetoPorRequest){
			if(!"".equals(Pagina.getParamStr(request, "codSgaGrAtrPgIni"))){
				selectedCodSgaGrAtrPgIni = Pagina.getParamStr(request, "codSgaGrAtrPgIni");
			}		
		}else{
			if(configuracao.getSisGrupoAtributoSgaByCodSgaGrAtrPgIni()!= null){
				selectedCodSgaGrAtrPgIni = configuracao.getSisGrupoAtributoSgaByCodSgaGrAtrPgIni().getCodSga().toString();
			}		
		}
		%>
		<combo:ComboTag 
						nome="codSgaGrAtrPgIni"
						objeto="ecar.pojo.SisGrupoAtributoSga" 
						label="descricaoSga" 
						value="codSga" 
						order="descricaoSga"
						selected="<%=selectedCodSgaGrAtrPgIni%>"
						scripts="onchange=reload(document.form)"
		/>
		</td>
	</tr>	
	<tr>
		<td class="label"><%=_obrigatorio%> P&aacute;gina Inicial do Sistema</td>
		<td>
		<%
		String selectedCodSapadrao = "";
		List colecaoAtrPaginaInicial = new ArrayList();
		if(objetoPorRequest){
				if(!"".equals(Pagina.getParamStr(request, "codSapadrao"))){
					selectedCodSapadrao = Pagina.getParamStr(request, "codSapadrao");
				}
				if(!"".equals(Pagina.getParamStr(request, "codSgaGrAtrPgIni"))){
					SisGrupoAtributoSga sisGrupoAtributo   =  (SisGrupoAtributoSga) new SisGrupoAtributoDao(request).buscar(SisGrupoAtributoSga.class, Long.valueOf(Pagina.getParamStr(request, "codSgaGrAtrPgIni")));				
					/* tenho que criar um list recebendo os elementos do Set pois o parâmetro para a taglib deve ser deste tipo */
					colecaoAtrPaginaInicial.addAll(sisGrupoAtributo.getSisAtributoSatbs());				
				}
		}else{
				if(configuracao.getSisAtributoSatbByCodSapadrao()!= null){
					selectedCodSapadrao = configuracao.getSisAtributoSatbByCodSapadrao().getCodSatb().toString();
				} 
				if(configuracao.getSisGrupoAtributoSgaByCodSgaGrAtrPgIni() != null){
					colecaoAtrPaginaInicial.addAll(configuracao.getSisGrupoAtributoSgaByCodSgaGrAtrPgIni().getSisAtributoSatbs());				
				}
		}
		%>
			<combo:ComboTag 
							nome="codSapadrao"
							objeto="ecar.pojo.SisAtributoSatb" 
							label="descricaoSatb" 
							value="codSatb" 
							order="descricaoSatb"
							selected="<%=selectedCodSapadrao%>"
							colecao="<%=colecaoAtrPaginaInicial%>"
							filters="indAtivoSatb=S"
			/>
		</td>
	</tr>
	
		<tr>
		<td class="label"><%=_obrigatorio%> Grupo de Atributos para <br> Leiautes de Capa</td>
		<td>
		<%
		String selectedCodSgaGrAtrLeiCapa = "";
		if(objetoPorRequest){
				if(!"".equals(Pagina.getParamStr(request, "codSgaGrAtrLeiCapa"))){
					selectedCodSgaGrAtrLeiCapa = Pagina.getParamStr(request, "codSgaGrAtrLeiCapa");
				}		
		}else{
				if(configuracao.getSisGrupoAtributoSgaByCodSgaGrAtrLeiCapa()!= null){
					selectedCodSgaGrAtrLeiCapa = configuracao.getSisGrupoAtributoSgaByCodSgaGrAtrLeiCapa().getCodSga().toString();
				}
		}
		%>
		<combo:ComboTag 
						nome="codSgaGrAtrLeiCapa"
						objeto="ecar.pojo.SisGrupoAtributoSga" 
						label="descricaoSga" 
						value="codSga" 
						order="descricaoSga"
						selected="<%=selectedCodSgaGrAtrLeiCapa%>"
						filters="indAtivoSga=S"
						scripts="onchange=reload(document.form)"
		/>
		</td>
	</tr>	
	<tr>
		<td class="label"><%=_obrigatorio%> Leiaute de Capa do Sistema</td>
		<td>
		<%
		String selectedCodSacapa = "";
		List colecaoLeiCapa = new ArrayList();		
		
		if(objetoPorRequest){
				if(!"".equals(Pagina.getParamStr(request, "codSacapa"))){
					selectedCodSacapa = Pagina.getParamStr(request, "codSacapa");
				}		
				if(!"".equals(Pagina.getParamStr(request, "codSgaGrAtrLeiCapa"))){
					SisGrupoAtributoSga sisGrupoAtributo   =  (SisGrupoAtributoSga) new SisGrupoAtributoDao(request).buscar(
									SisGrupoAtributoSga.class, Long.valueOf(Pagina.getParamStr(request, "codSgaGrAtrLeiCapa")));
					/* tenho que criar um list pois o parâmetro para a taglib deve ser deste tipo */
					colecaoLeiCapa.addAll(sisGrupoAtributo.getSisAtributoSatbs());				
				}
		} else {
				if(configuracao.getSisAtributoSatbByCodSacapa()!= null){
					selectedCodSacapa = configuracao.getSisAtributoSatbByCodSacapa().getCodSatb().toString();
				} 	
				if(configuracao.getSisGrupoAtributoSgaByCodSgaGrAtrLeiCapa() != null)	
					colecaoLeiCapa.addAll(configuracao.getSisGrupoAtributoSgaByCodSgaGrAtrLeiCapa().getSisAtributoSatbs());
		} 
		%>
			<combo:ComboTag 
							nome="codSacapa"
							objeto="ecar.pojo.SisAtributoSatb" 
							label="descricaoSatb" 
							value="codSatb" 
							order="descricaoSatb"
							selected="<%=selectedCodSacapa%>"
							colecao="<%=colecaoLeiCapa%>"
							filters="indAtivoSatb=S"							
			/>
		</td>
	</tr>
	<tr>
		<td class="label"><%=_obrigatorio%> Grupo de Atributos para <br> Tipos de Acesso</td>
		<td>
		<%
		String selectedAtrTpAcesso = "";
		if(objetoPorRequest){
				if(!"".equals(Pagina.getParamStr(request, "atrTpAcesso"))){
					selectedAtrTpAcesso = Pagina.getParamStr(request, "atrTpAcesso");
				}
		}else{
				if(configuracao.getSisGrupoAtributoSgaByCodSgaGrAtrTpAcesso()!= null){
					selectedAtrTpAcesso = configuracao.getSisGrupoAtributoSgaByCodSgaGrAtrTpAcesso().getCodSga().toString();
				}
		}
		%>
		<combo:ComboTag 
						nome="atrTpAcesso"
						objeto="ecar.pojo.SisGrupoAtributoSga" 
						label="descricaoSga" 
						value="codSga" 
						order="descricaoSga"
						selected="<%=selectedAtrTpAcesso%>"
					    filters="indAtivoSga=S"	
					    scripts="onchange=reload(document.form)"					
		/>
		</td>
	</tr>
	<!-- em testes... -->
	<tr>
		<td class="label"><%=_obrigatorio%> Atributos para Usuário Não Autenticado</td>
		<td>
		<%
		String selectedCodSaUsu = "";
		List colecaoAtrUsuNaoAut = new ArrayList();
		if(objetoPorRequest){
				if(!"".equals(Pagina.getParamStr(request, "codSaUsu"))){
					selectedCodSapadrao = Pagina.getParamStr(request, "codSaUsu");
				}
				if(!"".equals(Pagina.getParamStr(request, "atrTpAcesso"))){
					SisGrupoAtributoSga sisGrupoAtributo   =  (SisGrupoAtributoSga) new SisGrupoAtributoDao(request).buscar(SisGrupoAtributoSga.class, Long.valueOf(Pagina.getParamStr(request, "atrTpAcesso")));				
					/* tenho que criar um list recebendo os elementos do Set pois o parâmetro para a taglib deve ser deste tipo */
					colecaoAtrUsuNaoAut.addAll(sisGrupoAtributo.getSisAtributoSatbs());				
				}
		}else{
				if(configuracao.getSisAtributoSatbByCodSaAcesso()!= null){
					selectedCodSaUsu = configuracao.getSisAtributoSatbByCodSaAcesso().getCodSatb().toString();
				} 
				if(configuracao.getSisGrupoAtributoSgaByCodSgaGrAtrTpAcesso() != null){
					colecaoAtrUsuNaoAut.addAll(configuracao.getSisGrupoAtributoSgaByCodSgaGrAtrTpAcesso().getSisAtributoSatbs());				
				}
		}
		%> 
			<combo:ComboTag 
							nome="codSaUsu"
							objeto="ecar.pojo.SisAtributoSatb" 
							label="descricaoSatb" 
							value="codSatb" 
							order="descricaoSatb"
							selected="<%=selectedCodSaUsu%>"
							colecao="<%=colecaoAtrUsuNaoAut%>"
							filters="indAtivoSatb=S"
			/>
		</td>
	</tr>
	<!-- fim da seção em testes -->
	<tr>
		<td class="label"><%=_obrigatorio%> Grupo de Atributos para <br> N&iacute;vel do Planejamento</td>
		<td>
		<%
		String selectedNvPlan = "";
		if(objetoPorRequest){
				if(!"".equals(Pagina.getParamStr(request, "atrNvPlan"))){
					selectedNvPlan = Pagina.getParamStr(request, "atrNvPlan");
				}
		}else{
				if(configuracao.getSisGrupoAtributoSgaByCodSgaGrAtrNvPlan()!= null){
					selectedNvPlan = configuracao.getSisGrupoAtributoSgaByCodSgaGrAtrNvPlan().getCodSga().toString();
				}		
		}
		%>
		<combo:ComboTag 
						nome="atrNvPlan"
						objeto="ecar.pojo.SisGrupoAtributoSga" 
						label="descricaoSga" 
						value="codSga" 
						order="descricaoSga"
						selected="<%=selectedNvPlan%>"
						filters="indAtivoSga=S"						
		/>
		</td>
	</tr>		


	<% // Igor 08/08/2005 - Tipo do Ponto Critico %> 

	<tr>
		<td class="label">Grupo de Atributos para Tipo/Assunto do Ponto Crítico</td>
		<td>
		<%
		String selectedCodSgaPontoCritico = "";
		
		if(objetoPorRequest){
			if(!"".equals(Pagina.getParamStr(request,"codSgaPontoCritico"))){
				selectedCodSgaPontoCritico = Pagina.getParamStr(request,"codSgaPontoCritico");
			}		
		}
		else{
			if(configuracao.getSisGrupoAtributoSgaTipoPontoCritico()!= null){
				selectedCodSgaPontoCritico = configuracao.getSisGrupoAtributoSgaTipoPontoCritico().getCodSga().toString();
			} 
		}
		scripts = " title=\""+_msg.getMensagem("geral.grAtributos.instrucao") + "\"";
		if(configuracao.getCodCfg() != null)
			scripts += " title=\""+_msg.getMensagem("geral.grAtributos.instrucao") + "\"";
		%>
		<combo:ComboTag 
						nome="codSgaPontoCritico"
						objeto="ecar.pojo.SisGrupoAtributoSga" 
						label="descricaoSga" 
						value="codSga" 
						order="descricaoSga"
						selected="<%=selectedCodSgaPontoCritico%>"
						scripts="<%=scripts%>"
						filters="indAtivoSga=S"
		/>
		</td>
	</tr>

	<% // FIM - Igor 08/08/2005 - Tipo do Ponto Critico %> 
	
	<% 
	// Mantis #8889 - Adição de campo (combo) na tela.
	// Por Rogério (15/03/2007)
	%> 
	
	<tr>
		<td class="label">Grupo de Atributos para Metas Físicas</td>
		<td>
		<%
		String selectedCodSgaMetasFisicas = "";
		
		if(objetoPorRequest){
			if(!"".equals(Pagina.getParamStr(request,"codSgaMetasFisicas"))){
				selectedCodSgaMetasFisicas = Pagina.getParamStr(request,"codSgaMetasFisicas");
			}		
		}
		else{
			if(configuracao.getSisGrupoAtributoSgaByCodSgaGrAtrMetasFisicas() != null){
				selectedCodSgaMetasFisicas = configuracao.getSisGrupoAtributoSgaByCodSgaGrAtrMetasFisicas().getCodSga().toString();
			} 
		}
		
		scripts = " title=\""+_msg.getMensagem("geral.grAtributos.instrucao") + "\"";
		if(configuracao.getCodCfg() != null)
			scripts += " title=\""+_msg.getMensagem("geral.grAtributos.instrucao") + "\"";
		%>
		
		<combo:ComboTag 
						nome="codSgaMetasFisicas"
						objeto="ecar.pojo.SisGrupoAtributoSga" 
						label="descricaoSga" 
						value="codSga" 
						order="descricaoSga"
						selected="<%=selectedCodSgaMetasFisicas%>"
						scripts="<%=scripts%>"
						filters="indAtivoSga=S"
		/>
		</td>
	</tr>

	
	<%
	// Fim Mantis #8889
	%>


	<% 
	// Desenvolvido no Serpro
	%> 
	
	<tr>
		<td class="label">Grupo de Atributos para Unidade de Medida</td>
		<td>
		<%
		String selectedCodSgaUnidMedida = "";
		
		if(objetoPorRequest){
			if(!"".equals(Pagina.getParamStr(request,"codSgaUnidMedida"))){
				selectedCodSgaMetasFisicas = Pagina.getParamStr(request,"codSgaUnidMedida");
			}		
		}
		else{
			if(configuracao.getSisGrupoAtributoSgaByUnidMedida() != null){
				selectedCodSgaUnidMedida = configuracao.getSisGrupoAtributoSgaByUnidMedida().getCodSga().toString();
			} 
		}
		
		scripts = " title=\""+_msg.getMensagem("geral.grAtributos.instrucao") + "\"";
		if(configuracao.getCodCfg() != null)
			scripts += " title=\""+_msg.getMensagem("geral.grAtributos.instrucao") + "\"";
		%>
		
		<combo:ComboTag 
						nome="codSgaUnidMedida"
						objeto="ecar.pojo.SisGrupoAtributoSga" 
						label="descricaoSga" 
						value="codSga" 
						order="descricaoSga"
						selected="<%=selectedCodSgaUnidMedida%>"
						scripts="<%=scripts%>"
						filters="indAtivoSga=S"
		/>
		</td>
	</tr>
	
	<tr>
		<td class="label">Grupo de Atributos para Tipo de Evento</td>
		<td>
		<%
		String selectedCodSgaTipoEvento = "";
		
		if(objetoPorRequest){
			if(!"".equals(Pagina.getParamStr(request,"codSgaTipoEvento"))){
				selectedCodSgaMetasFisicas = Pagina.getParamStr(request,"codSgaTipoEvento");
			}		
		}
		else{
			if(configuracao.getSisGrupoAtributoSgaByTipoEvento() != null){
				selectedCodSgaTipoEvento = configuracao.getSisGrupoAtributoSgaByTipoEvento().getCodSga().toString();
			} 
		}
		
		scripts = " title=\""+_msg.getMensagem("geral.grAtributos.instrucao") + "\"";
		if(configuracao.getCodCfg() != null)
			scripts += " title=\""+_msg.getMensagem("geral.grAtributos.instrucao") + "\"";
		%>
		
		<combo:ComboTag 
						nome="codSgaTipoEvento"
						objeto="ecar.pojo.SisGrupoAtributoSga" 
						label="descricaoSga" 
						value="codSga" 
						order="descricaoSga"
						selected="<%=selectedCodSgaTipoEvento%>"
						scripts="<%=scripts%>"
						filters="indAtivoSga=S"
		/>
		</td>
	</tr>
	
	<tr>
		<td class="label">Número de Ciclos de Acompanhamento Padrão</td>
		<td>
		
		<%
			String periodoPadraoSelecionado = "";
				
			//pega ciclo padrão do banco
//			ConfiguracaoCfg configuracao = new ConfiguracaoCfg();
//			ConfiguracaoDao configuracaoDao = new ConfiguracaoDao(request);
		
			List confg = configuracaoDao.listar(ConfiguracaoCfg.class, null);					
			if(confg != null && confg.size() > 0)
				configuracao = (ConfiguracaoCfg) confg.iterator().next();
				
			Long periodoPadrao = configuracao.getPeriodoPadrao();
					
			//pega o perído padrão da request se estiver
			if(!"".equals(Pagina.getParamStr(request,"periodoPadrao"))){
				periodoPadraoSelecionado = Pagina.getParamStr(request,"periodoPadrao");
			}
		 %>
		
			<!-- COMBO DE PERÍODOS DE EXIBIÇÃO -->							
					<select name="periodoPadrao">
											
						<option value="">
							
						</option>
						<% 
						for(int i=1;i<=12;i++){ 
							Integer inteiro = new Integer(i);
						%>
							<option value="<%=Pagina.trocaNull(String.valueOf(i))%>" 
							<%
							 if(periodoPadrao!=null && inteiro.toString().equals(periodoPadrao.toString())){
							%>
							 	selected
							<% 
							 }							
							 if(inteiro.toString().equals(periodoPadraoSelecionado)){
							%>
								selected
							<%
							 }
							%>
							> 								
								<%=inteiro%> <%if (inteiro.intValue()==1){%> Ciclo <%}else {%> Ciclos <%}%>
							</option>
					  <%}%>
					</select>
		</td>
	</tr>
	
	<tr>
		<td class="label">Visão para Associação de Demandas</td>
		<td>
		<%
		String selectedVisaoDemandas = "";
		if(configuracao.getVisaoDemandasVisDem() != null){
			selectedVisaoDemandas = configuracao.getVisaoDemandasVisDem().getCodVisao().toString();
		}
		%>
		<combo:ComboTag 
						nome="visaoDemandasVisDem"
						objeto="ecar.pojo.VisaoDemandasVisDem" 
						label="descricaoVisao" 
						value="codVisao" 
						order="descricaoVisao"
						selected="<%=selectedVisaoDemandas%>"
		/>
		</td>
	</tr>
	
	<tr>
		<td class="label">Minutos que faltam para a exibir uma mensagem avisando do encerramento da sessão </td>
		<td>
			<%
			String tempoSessaoSelecionado = "";
				
			//pega ciclo padrão do banco
//			// ConfiguracaoCfg configuracao = new ConfiguracaoCfg();
//			// ConfiguracaoDao configuracaoDao = new ConfiguracaoDao(request);
		
			//List configTempo = configuracaoDao.listar(ConfiguracaoCfg.class, null);					
			//if(configTempo != null && configTempo.size() > 0)
			//	configuracao = (ConfiguracaoCfg) confg.iterator().next();
				
			Long tempoSessao = configuracao.getTempoSessao();
					
			//pega o perído padrão da request se estiver
			if(!"".equals(Pagina.getParamStr(request,"tempoSessao"))){
				periodoPadraoSelecionado = Pagina.getParamStr(request,"tempoSessao");
			}
		 %>
					<!-- COMBO PARA ESCOLHA DO TEMPO PADRÃO -->							
					<select name="tempoSessao">
											
						<option value="0">
						0 - Sem Aviso	
						</option>
						<% 
						for(int i=1;i<= ((session.getMaxInactiveInterval()/60) - 2); i++){ 
							Integer inteiro = new Integer(i);
						%>
							<option value="<%=Pagina.trocaNull(String.valueOf(i))%>" 
							<%
							 if(tempoSessao!=null && inteiro.toString().equals(tempoSessao.toString())){
							%>
							 	selected
							<% 
							 }							
							 if(inteiro.toString().equals(tempoSessaoSelecionado)){
							%>
								selected
							<%
							 }
							%>
							> 								
								<%=inteiro%> <%if (inteiro.intValue()==1){%> Minuto <%}else {%> Minutos <%}%>
							</option>
					  <%}%>
					</select>
		
	</tr>
	
	<%
	// Fim Desenvolvido no Serpro
	%>



<% /* -- novo -- */ %>
	<tr> <td colspan="2">&nbsp;</td> </tr>
	
	<tr>
		<td class="label">Título do Sistema</td>
		<td><input type="text" name="tituloSistema" size="35" maxlength="50" 
					value="<%=Pagina.trocaNull(configuracao.getTituloSistema())%>">
	</tr>
	<tr>
		<td class="label">Servidor de E-mail</td>
		<td><input type="text" name="emailServer" size="35" maxlength="50" 
					value="<%=Pagina.trocaNull(configuracao.getEmailServer())%>">
	</tr>

	<tr>
		<td class="label">Estilo (.css) do Sistema</td>
		<td valign="top">
			<script language="javascript">
				function atualizaPreview(valor){
					form.preview.src = "<%=_pathEcar%>/images/estilo/"+valor+".png";
				}	
			</script>
			<select name="estilo" onchange="atualizaPreview(this.value);">
				<option value="0"></option> 
				<%
				EstiloDao estiloDao = new EstiloDao(request);
				List estilos = estiloDao.listar(Estilo.class, null);
				Iterator estiloIt = estilos.iterator();
				
				while( estiloIt.hasNext() ) {
					Estilo estilo = (Estilo) estiloIt.next();  %>
					<option value="<%=estilo.getCodEstilo()%>" <%
					if( configuracao.getEstilo() != null && 
						configuracao.getEstilo().getCodEstilo().equals(estilo.getCodEstilo())) {
						out.print("selected");
					} %> ><%=estilo.getDescricao()%></option> <%
				} %>
			</select> <br><br> Pré-visualização:<br>
			<img name="preview" id="preview" src="<%=_pathEcar%>/images/estilo/<%=configuracao.getEstilo().getCodEstilo()%>.png" width="270" height="145">
	</tr>
	<tr>
		<td class="label">Imagem da Esquerda (caminho)</td>
		<td>
			<input type="text" name="imagemEsquerda" size="35" maxlength="50" 
					value="<%=Pagina.trocaNull(configuracao.getImagemEsquerda())%>">
	</tr>
	<tr>
		<td class="label">Diretório Raiz de Uploads</td>
		<td><input type="text" name="raizUpload" size="35" maxlength="50" 
					value="<%=Pagina.trocaNull(configuracao.getRaizUpload())%>">
	</tr>

	<tr>
		<td class="label">Diretório de Upload Categoria (relativo)</td>
		<td><input type="text" name="uploadCategoria" size="35" maxlength="50" 
					value="<%=Pagina.trocaNull(configuracao.getUploadCategoria())%>">
	</tr>
	
		
	<tr>
		<td class="label">Diretório de Upload Anexos (relativo)</td>
		<td><input type="text" name="uploadAnexos" size="35" maxlength="50" 
					value="<%=Pagina.trocaNull(configuracao.getUploadAnexos())%>">
	</tr>

		
	<tr>
		<td class="label">Diretório de Upload Empresa (relativo)</td>
		<td><input type="text" name="uploadEmpresa" size="35" maxlength="50" 
					value="<%=Pagina.trocaNull(configuracao.getUploadEmpresa())%>">
	</tr>

		
	<tr>
		<td class="label">Diretório de Upload Adm Portal (relativo)</td>
		<td><input type="text" name="uploadAdmPortal" size="35" maxlength="50" 
					value="<%=Pagina.trocaNull(configuracao.getUploadAdmPortal())%>">
	</tr>
	
		
	<tr>
		<td class="label">Diretório de Upload Usuarios (relativo)</td>
		<td><input type="text" name="uploadUsuarios" size="35" maxlength="50" 
					value="<%=Pagina.trocaNull(configuracao.getUploadUsuarios())%>">
	</tr>
	

	<tr>
		<td class="label">Diretório de Upload Integra&ccedil;&atilde;o Financeira (relativo)</td>
		<td><input type="text" name="uploadIntegracao" size="35" maxlength="50" 
					value="<%=Pagina.trocaNull(configuracao.getUploadIntegracao())%>">
	</tr>

	<tr>
		<td class="label">Diretório de Upload de Ícones de Links (relativo)</td>
		<td><input type="text" name="uploadIconeLinks" size="35" maxlength="50" 
					value="<%=Pagina.trocaNull(configuracao.getUploadIconeLinks())%>">
	</tr>
	
	<tr>
		<td class="label">Diretório de Upload de Arquivo Exportação Demandas (relativo)</td>
		<td><input type="text" name="uploadExportacaoDemandas" size="35" maxlength="50" 
					value="<%=Pagina.trocaNull(configuracao.getUploadExportacaoDemandas())%>">
	</tr>
	
	<tr>
		<td class="label">* Caracter Separador em Arq. Textos</td>
		<td><input type="text" name="separadorArqTXT" size="35" maxlength="40" 
					value="<%=Pagina.trocaNull(configuracao.getSeparadorArqTXT())%>">
	</tr>
	
	<tr>
		<td class="label">* Caracter Separador para Campos Multivalorados</td>
		<td><input type="text" name="separadorCampoMultivalor" size="35" maxlength="40" 
					value="<%=Pagina.trocaNull(configuracao.getSeparadorCampoMultivalor())%>">
	</tr>
		
	<tr>
		<td class="label">Número de Registros no PopUp de Pesquisa</td>
		<td>
			<input type="text" name="numRegistros" size="5" maxlength="3" value="<%=Pagina.trocaNull(configuracao.getNumRegistros())%>">
		</td>
	</tr>
	
		
	<tr>
		<td class="label">Número de Itens na galeria de Anexos</td>
		<td><input type="text" name="qteItensGalAnexo" size="5" maxlength="3" value="<%=Pagina.trocaNull(configuracao.getQtdeItensGalAnexo())%>">
	</tr>

	<tr>
		<td class="label">Número de Itens a serem Exibidos na Paginação de Demandas</td>
		<td><input type="text" name="nuItensExibidosPaginacao" size="5" maxlength="3" value="<%=Pagina.trocaNull(new Integer(configuracao.getNuItensExibidosPaginacao()))%>">
	</tr>
	
	<tr>
		<td class="label">Label para Monitorado</td>
		<td><input type="text" name="labelMonitorado" size="35" maxlength="50"  
			value="<%=Pagina.trocaNull(configuracao.getLabelMonitorado())%>">
	</tr>
	
	<tr>
		<td class="label">Label para Órgão</td>
		<td><input type="text" name="labelOrgao" size="35" maxlength="50"  
			value="<%=Pagina.trocaNull(configuracao.getLabelOrgao())%>">
	</tr>
	
	<tr>
		<td class="label">Label para o agrupamento de itens sem órgão definido</td>
		<td><input type="text" name="labelAgrupamentoItensSemOrgao" size="35" maxlength="50"  
			value="<%=Pagina.trocaNull(configuracao.getLabelAgrupamentoItensSemOrgao())%>">
	</tr>
	
	<tr>
		<td class="label">Label para Situação em Parecer</td>
		<td><input type="text" name="labelSituacaoParecer" size="35" maxlength="50"  
			value="<%=Pagina.trocaNull(configuracao.getLabelSituacaoParecer())%>">
	</tr>
	
	<tr>
		<td class="label">Label para Cor em Parecer</td>
		<td><input type="text" name="labelCorParecer" size="35" maxlength="50"  
			value="<%=Pagina.trocaNull(configuracao.getLabelCorParecer())%>">
	</tr>
	
	<tr>
		<td class="label">Label para Avaliações/Posições em Lista de Pareceres</td>
		<td><input type="text" name="labelSituacaoListaPareceres" size="35" maxlength="50"  
			value="<%=Pagina.trocaNull(configuracao.getLabelSituacaoListaPareceres())%>">
	</tr>
	
	<tr> <td colspan="2">&nbsp;</td> </tr>

	<tr>
		<td class="label">Configuração para Integração Financeira</td>
 		<td class="label">&nbsp;</td>
	</tr>
	<tr>
		<td class="label">Descrição para Valor Financeiro 1</td>		
		<td><input type="text" name="financeiroDescValor1Cfg" size="35" maxlength="40" 
					value="<%=Pagina.trocaNull(configuracao.getFinanceiroDescValor1Cfg())%>">
		</td>		
	</tr>
	<tr>
		<td class="label">Descrição para Valor Financeiro 2</td>		
		<td><input type="text" name="financeiroDescValor2Cfg" size="35" maxlength="40" 
					value="<%=Pagina.trocaNull(configuracao.getFinanceiroDescValor2Cfg())%>">
		</td>		
	</tr>
	<tr>
		<td class="label">Descrição para Valor Financeiro 3</td>		
		<td><input type="text" name="financeiroDescValor3Cfg" size="35" maxlength="40" 
					value="<%=Pagina.trocaNull(configuracao.getFinanceiroDescValor3Cfg())%>">
		</td>		
	</tr>
	<tr>
		<td class="label">Descrição para Valor Financeiro 4</td>		
		<td><input type="text" name="financeiroDescValor4Cfg" size="35" maxlength="40" 
					value="<%=Pagina.trocaNull(configuracao.getFinanceiroDescValor4Cfg())%>">
		</td>		
	</tr>
	<tr>
		<td class="label">Descrição para Valor Financeiro 5</td>		
		<td><input type="text" name="financeiroDescValor5Cfg" size="35" maxlength="40" 
					value="<%=Pagina.trocaNull(configuracao.getFinanceiroDescValor5Cfg())%>">
		</td>		
	</tr>
	<tr>
		<td class="label">Descrição para Valor Financeiro 6</td>		
		<td><input type="text" name="financeiroDescValor6Cfg" size="35" maxlength="40" 
					value="<%=Pagina.trocaNull(configuracao.getFinanceiroDescValor6Cfg())%>">
		</td>		
	</tr>
	
	<% /* -- novo -- */ %>
	
	<% /* -- novo -- */ %>
	<tr> <td colspan="2">&nbsp;</td> </tr>

	<tr>
		<td class="label">Configuração para Integração Financeira (Recursos)</td>
 		<td class="label">&nbsp;</td>
	</tr>
	<tr>
		<td class="label">Descrição para Valor 1</td>		
		<td><input type="text" name="recursoDescValor1Cfg" size="35" maxlength="40" 
					value="<%=Pagina.trocaNull(configuracao.getRecursoDescValor1Cfg())%>">
		</td>		
	</tr>
	<tr>
		<td class="label">Descrição para Valor 2</td>		
		<td><input type="text" name="recursoDescValor2Cfg" size="35" maxlength="40" 
					value="<%=Pagina.trocaNull(configuracao.getRecursoDescValor2Cfg())%>">
		</td>		
	</tr>
	<tr>
		<td class="label">Descrição para Valor 3</td>		
		<td><input type="text" name="recursoDescValor3Cfg" size="35" maxlength="40" 
					value="<%=Pagina.trocaNull(configuracao.getRecursoDescValor3Cfg())%>">
		</td>		
	</tr>
	
	<% /* -- novo -- */ %>
	
	
	<tr> <td colspan="2">&nbsp;</td> </tr>
	
	<tr>
		<td class="label">Opções de Envio de E-Mail</td>
 		<td class="label">&nbsp;</td>
	</tr>
	<tr>
		<td class="label">Intervalo entre atualizações de e-mails</td>		
		<td><input type="text" name="intervaloAtualizacaoEmail" size="10" maxlength="2" 
					value="<%=Pagina.trocaNull(configuracao.getIntervaloAtualizacaoEmail())%>"> Dias
		</td>		
	</tr>
	<tr>
		<td class="label">Dias de antecedência para envio de lembrete</td>		
		<td><input type="text" name="diasAntecedenciaMailCfg" size="10" maxlength="2" 
					value="<%=Pagina.trocaNull(configuracao.getDiasAntecedenciaMailCfg())%>"> 
		</td>		
	</tr>
	<tr>
		<td class="label">Hora de envio (HH:MM)</td>		
		<td><input type="text" name="horaEnvioMailCfg" size="10" maxlength="5" 
					value="<%=Pagina.trocaNull(configuracao.getHoraEnvioMailCfg())%>">
		</td>		
	</tr>
	
	<tr> <td colspan="2">&nbsp;</td> </tr>
	
	<tr>
		<td class="label">Opções de Monitoramento</td>
 		<td class="label">&nbsp;</td>
	</tr>
	<tr>
		<td class="label"><%=_obrigatorio%> Ocultar campo observações em parecer</td>
		<td>
			<input type="radio" name="indOcultarObservacoesParecer" value="S" 
				<%=Pagina.isChecked(configuracao.getIndOcultarObservacoesParecer(), "S")%> 
				<%=_disabled%>>Sim 
			<input type="radio" name="indOcultarObservacoesParecer" value="N" 
				<%=Pagina.isChecked(configuracao.getIndOcultarObservacoesParecer(), "N")%>
				<%=_disabled%>>N&atilde;o
		</td>
	</tr>
	
	<tr>
		<td class="label">Exibir situações em formato de abas</td>
		<td>
			<input type="radio" name="indExibirSituacoesFormatoAbas" value="S" 
				<%=Pagina.isChecked(configuracao.getIndExibirSituacoesFormatoAbas(), "S")%> 
				<%=_disabled%>>Sim 
			<input type="radio" name="indExibirSituacoesFormatoAbas" value="N" 
				<%=Pagina.isChecked(configuracao.getIndExibirSituacoesFormatoAbas(), "N")%>
				<%=_disabled%>>N&atilde;o
		</td>
	</tr>
	
	<tr> <td colspan="2">&nbsp;</td> </tr>
	
	<tr> 
		<td>
			<table border="0">
				<tr>
					<td align="right"><strong>Evento</strong></td>
				</tr> 
			</table> 
		</td>
		<td>
			<table border="0">
				<tr>
					<td width="3%"><strong>Ativo</strong></td>
					<!-- Luana -->
					<td width="3%"><strong>Obrig.</strong></td>
					<td align="center"><strong>E-Mail</strong></td>
					<td align="center"><strong>SMS</strong></td>
				</tr>
			</table>
		</td>
	</tr>					
			<%
				TextosSiteDao textoSiteDao = new TextosSiteDao(request);
				
				List listaConfigMail = configMailDao.listar(ConfigMailCfgm.class, new String[] {"descricaoCfgm", "asc"});

				List listaTextosSite = new ArrayList();
				
				listaTextosSite = textoSiteDao.listar(TextosSiteTxt.class, null);

				Iterator itConfigMail = listaConfigMail.iterator();
				
				while( itConfigMail.hasNext() ) {
					ConfigMailCfgm configMail = (ConfigMailCfgm) itConfigMail.next(); %>
					<tr> 
					<td>
						<table border="0">
						<tr>					
						<td align="right"><%=Pagina.trocaNull(configMail.getDescricaoCfgm())%></td>
						</tr> 
						</table> 
					</td>
					<td>
						<table border="0">
						<tr>						
						<td width="3%" align="right"><input type="checkbox" class="form_check_radio" name="chkAtivoCfgM" value="<%=Pagina.trocaNull(configMail.getCodCfgm())%>" <%=(configMail.getAtivoCfgm().equals(String.valueOf("S")) ? "checked" : "")%> ></td>
						<!-- Luana -->
						<td align="center"><input type="checkbox" class="form_check_radio" name="chkObrigatorioCfgM" value="<%=Pagina.trocaNull(configMail.getCodCfgm())%>" <%=(configMail.getIndEnvioObrigatorio().equals(String.valueOf("S")) ? "checked" : "N")%> ></td>
						<td align="center">
							<select name="cboCodTextoSiteEmail">
								<option value="0"></option> <%
								Iterator itTxt = listaTextosSite.iterator();
								
								while( itTxt.hasNext() ) {
									TextosSiteTxt texto = (TextosSiteTxt) itTxt.next();  %>
									<option value="<%=texto.getCodTxtS()%>" <%
									if(configMail.getTextosSiteMail() != null) {
										if( configMail.getTextosSiteMail().getCodTxtS().equals(texto.getCodTxtS()) ) {
											out.print("selected");
										} 
									} %> ><%=texto.getDescricaoUsoTxts()%></option> <%
								} %>
							</select>
						</td>
						<td align="center">
							<select name="cboCodTextoSiteSMS">
								<option value="0"></option> <%
								itTxt = listaTextosSite.iterator();
								
								while( itTxt.hasNext() ) {
									TextosSiteTxt texto = (TextosSiteTxt) itTxt.next(); %>
									<option value="<%=texto.getCodTxtS()%>" <%
									if( configMail.getTextosSiteSms() != null ) {
										if( configMail.getTextosSiteSms().getCodTxtS().equals(texto.getCodTxtS()) ) {
											out.print("selected");
										} 
									} %> ><%=texto.getDescricaoUsoTxts()%></option> <%
								}    %>
							</select>
						</td>
					</tr>
					</table>
				</td>
				</tr>										 <%
				} %>
			</table>
		</td> 
	</tr>
	
	
	</table>

	<util:barrabotoes alterar="Gravar" cancelar="Cancelar"/>

	<%
	} 
	catch (ECARException e) {
		org.apache.log4j.Logger.getLogger(this.getClass()).error(e);
		/* redireciona para o controle */
		%>
		<script language="javascript">
		document.form.action = "ctrl.jsp";
		document.form.submit();
		</script>
		<%
	} catch (Exception e){
	%>
		<%@ include file="/excecao.jsp"%>
	<%
	}
	%>

	<%@ include file="../../include/estadoMenu.jsp"%>
	
</form>

</div>

</body>
<%@ include file="../../include/mensagemAlert.jsp"%>
</html>
