<%@page import="comum.util.ConstantesECAR"%>
<%@page import="ecar.pojo.intercambioDados.PerfilIntercambioDadosPflid"%>
<%@page import="comum.util.Data"%>
<%@page import="ecar.intercambioDados.dto.IBusinessObjectDTO"%>
<%@page import="ecar.exception.intercambioDados.SemanticValidationException"%>
<%@page import="ecar.pojo.UsuarioUsu"%>
<%@ include file="../login/validaAcesso.jsp"%>
<%@ include file="../frm_global.jsp"%>

<%@ taglib uri="/WEB-INF/ecar-util.tld" prefix="util" %>
<%@ taglib uri="/WEB-INF/ecar-combo.tld" prefix="combo" %>

<%
ResultadoValidacaoBean bean = (ResultadoValidacaoBean) request.getSession().getAttribute("validarRegistros");
PerfilIntercambioDadosPflid perfil = (PerfilIntercambioDadosPflid) request.getSession().getAttribute("perfilIntercambioDadosPflid");
ConfiguracaoImportacaoTXT configTxt = (ConfiguracaoImportacaoTXT) request.getSession().getAttribute("configTxt"); 
UsuarioUsu usuario = ((ecar.login.SegurancaECAR)session.getAttribute("seguranca")).getUsuario();
int manutencao = 0; 
int exclusao = 0;
int rejeitados = 0;
if (bean.getLinhasRegistrosValidos().size() > 0)
for(int i = 0; bean.getLinhasRegistrosValidos().size() > i; i++){
    if ( ((LinhaResultadoValidacaoTXT) bean.getLinhasRegistrosValidos().get(i)).getOperacao().equals(ConstantesECAR.TIPO_OPERACAO_MANUTENCAO)) {
		manutencao++;
    } else {
	    exclusao++;
    }
}

if (bean.getLinhasRegistrosInvalidos() != null && bean.getLinhasRegistrosInvalidos().size() > 0){
	rejeitados = bean.getLinhasRegistrosInvalidos().size();
}
//TODO Refatorar o objeto tecnologia para identificar possibilidade de tecnologia WS
DadosTecnologiaPerfilTxtDtpt tecnologiaTxt = (DadosTecnologiaPerfilTxtDtpt)perfil.getDadosTecnologiaPerfilDtp();


%>


<%@page import="ecar.login.SegurancaECAR"%>
<%@page import="ecar.pojo.intercambioDados.funcionalidade.TipoFuncionalidadeEnum"%>
<%@page import="ecar.intercambioDados.importacao.comunicacao.ConfiguracaoImportacaoTXT"%>
<%@page import="ecar.pojo.intercambioDados.tecnologia.DadosTecnologiaPerfilTxtDtpt"%>
<%@page import="ecar.pojo.intercambioDados.tecnologia.DadosTecnologiaPerfilDtp"%>
<%@page import="ecar.pojo.intercambioDados.funcionalidade.PerfilIntercambioDadosCadastroPidc"%>
<%@page import="ecar.intercambioDados.montador.ResultadoValidacaoBean"%>
<%@page import="ecar.intercambioDados.montador.LinhaResultadoValidacaoTXT"%>
<%@page import="ecar.pojo.intercambioDados.tecnologia.*"%><html lang="pt-br">
<head>
<%@ include file="../include/meta.jsp"%>
<%@ include file="/titulo.jsp"%>
<link rel="stylesheet" href="<%=_pathEcar%>/css/style_<%=configuracaoCfg.getEstilo().getNome()%>.css" type="text/css">
<script language="javascript" src="../js/menu_retratil.js"></script>
<script language="javascript" src="../js/validacoes.js"></script>
<script language="javascript" src="../js/destaqueLinha.js"></script>
<script language="javascript">


	function disparaAcao(){
		var btnGravar = document.getElementsByName("confirmarBtn");
		var btnVoltar = document.getElementsByName("voltarBtn");

		for ( var i = 0; i < btnGravar.length; i++) {
			btnGravar[i].disabled=true;	
		}

		for ( var i = 0; i < btnVoltar.length; i++) {
			btnVoltar[i].disabled=true;	
		}
		
		document.form.hidAcao.value="importar";
		document.form.action = "ctrl.jsp";
		document.form.submit();
	}

	function voltar(){
		document.form.hidAcao.value="voltar";
		document.form.action = "importacao.jsp";
		document.form.submit();
	}

	
	
</script>

</head>

<%
String expDisabled = "";

if (bean.getLinhasRegistrosValidos() == null || bean.getLinhasRegistrosValidos().size() < 1){
	expDisabled = "disabled";
}
	
%>

<!-- sempre colocar o foco inicial no primeiro campo -->
<body onload="javascript:destacaLinhaDisplayTag('logImportados');destacaLinhaDisplayTag('logRejeitados');">
<%@ include file="../cabecalho.jsp" %>
<%@ include file="../divmenu.jsp"%>

<div id="conteudo">

<form name="form" method="post" action="ctrl.jsp"  enctype="multipart/form-data">
	<input type="hidden" name="hidAcao" value="importar">
		
	<!-- TITULO -->
	<%@ include file="/titulo_tela.jsp"%>
		<c:if test="<%=session.getAttribute("msgTitulo") != null%>" >
			<center><h1><%=session.getAttribute("msgTitulo").toString()%> </h1></center>
		</c:if>
		<table class="barrabotoes" cellpadding="0" cellspacing="0">
			<tr>
				<c:choose>
					<c:when test="<%=bean.getLinhasRegistrosValidos().isEmpty()%>">
						<td colspan="1" align="center">
							<input id="2" type='button' name='voltarBtn' value='Voltar' onclick="javascript:voltar();">
						</td>
					</c:when>
					<c:otherwise>
						<td colspan="1" align="center">
							<input id="1" type='button' name='confirmarBtn' value='Confirmar' onclick="javascript:disparaAcao();" <%=expDisabled %> >
							<input id="2" type='button' name='voltarBtn' value='Cancelar' onclick="javascript:voltar();">
						</td>
					</c:otherwise>
				</c:choose>
			</tr>
		</table>
	
		<table class="form">
			<tr>
				<td class="label">
					Nome do perfil:
				</td>
				<td>
					<%= perfil.getNomePflid() %>
				</td>
			</tr>
		    <tr>
				<td class="label">
					Sistema origem:
				</td>
				<td>
					<%= perfil.getNomeSistemaOrigemPflid() %>
				</td>
			</tr>
			</table>
			<table class="form">
			 <tr>
				<td class="label">
					Tipo de funcionalidade:
				</td>
				<td>
					<%= perfil.getTipoFuncionalidade().getDescricao() %>
				</td>
			</tr>
			<tr>
				<td class="label">
					Estrutura base:
				</td>
				<td>
					<%=((PerfilIntercambioDadosCadastroPidc)perfil).getEstruturaBasePidc().getNomeEtt() %>
				</td>
			</tr>
			<tr>
				<td class="label">
					Estrutura do item de nível superior:
				</td>
				<td>
					<%=((PerfilIntercambioDadosCadastroPidc)perfil).getEstruturaItemNivelSuperiorPidc().getNomeEtt() %>
				</td>
			</tr>
			<tr>
				<td class="label">
					Estrutura de criação de itens: 
				</td>
				<td>
					<%=((PerfilIntercambioDadosCadastroPidc)perfil).getEstruturaCriacaoItemPidc().getNomeEtt() %>
				</td>
			</tr>
			<tr>
				<td class="label">
					Usuário associado ao item:
				</td>
				<td>
					<%=perfil.getUsuarioImportacao()!= null ? perfil.getUsuarioImportacao().getNomeUsu():((SegurancaECAR)session.getAttribute("seguranca")).getUsuario().getNomeUsu() %>
				</td>
			</tr>
			<tr>
				<td class="label">
					Grupo de acesso para permissão ao item:
				</td>
				<td>
					<%=((PerfilIntercambioDadosCadastroPidc)perfil).getGrupoAcessoItensImportadosPidc().getDescricaoSatb() %>
				</td>
			</tr>
			</table>
			<table class="form">
			<tr>
				<td class="label">
					Tecnologia de comunicação:
				</td>
				<td>
					<%= perfil.getDadosTecnologiaPerfilDtp().getTipoTecnologia().getDescricao() %>
				</td>
			</tr>
			 <tr>
				<td class="label">
					Nome da especificação:
				</td>
				<td>
					<%= perfil.getDadosTecnologiaPerfilDtp().getTipoTecnologia().getEspecificacao() %>
				</td>
			</tr>
			<tr>
				<td class="label">
					Versão da especificação:
				</td>
				<td>
					<%= perfil.getDadosTecnologiaPerfilDtp().getTipoTecnologia().getVersao() %>
				</td>
			</tr>
			<tr>
				<td class="label">
					Codificação de caracteres:
				</td>
				<td>
					<%= perfil.getDadosTecnologiaPerfilDtp().getEncodeDtp() %>
				</td>
			</tr>
			<c:if test="<%= perfil.getDadosTecnologiaPerfilDtp().getTipoTecnologia() == TipoTecnologiaEnum.ARQUIVOTEXTO_PACINTER  %>">
			<tr>
				<td class="label">
					Separador padrão:
				</td>
				<td>
					<%=((DadosTecnologiaPerfilTxtDtpt) perfil.getDadosTecnologiaPerfilDtp()).getSeparadorCamposDtpt() %>
				</td>
			</tr>
			</table>
			</c:if>
			<table class="form">
			<tr>
				<td class="label">
					Data/hora de processamento:
				</td>
				<td>
					<%= Data.parseDateHourMinuteSecond(Data.getDataAtual()) %>
				</td>
			</tr>
			<c:if test="<%= perfil.getDadosTecnologiaPerfilDtp().getTipoTecnologia() == TipoTecnologiaEnum.ARQUIVOTEXTO_PACINTER  %>">
			<tr>
				<td class="label">
					Arquivo:
				</td>
				<td>
					<%= configTxt.getFileName() %>
				</td>
			</tr>
			<tr>
				<td class="label">
					Data/hora de geração do arquivo:
				</td>
				<td>
					
					<%if (configTxt.getDataGeracaoArquivo() != null) {
						out.println(Data.parseDateHourMinuteSecond(configTxt.getDataGeracaoArquivo()));
					} else {
						out.println("Data não registrada");
					}%>
				</td>
			</tr>
			<tr>
				<td class="label">
					Usuário responsável:
				</td>
				<td>
					<%= usuario.getNomeUsu() %>
				</td>
			</tr>
			
		</table>
		</c:if>
		
		<h3>Estatísticas</h3>
		<table class="form">
			<tr>
				<td class="label"><h3>Elementos Válidos</h3></td>
			</tr>
			<tr>
				<td class="label">
					Inclusão/Alteração:
				</td>
				<td>
					<%=manutencao %>
				</td>
			</tr>
		    <tr>
				<td class="label">
					Exclusão:
				</td>
				<td>
					<%=exclusao %>
				</td>
			</tr>
			<tr>
				<td class="label">
				&nbsp;
				</td>
			</tr>
			<tr>
				<td class="label"><h3>Elementos Rejeitados</h3></td>
			</tr>
			<tr>
				<td class="label">
					Rejeitados:
				</td>
				<td>
					<%=rejeitados%>
				</td>
			</tr>
			
		</table>
		
		<h3>Válidos</h3>
		<display:table id="logImportados" name="<%=bean.getLinhasRegistrosValidos()%>" class="sortable" export="true"
			pagesize="<%=configuracao.getNuItensExibidosPaginacao() %>">
			<display:column property="objetoNegocio.siglaIett" title="Identificador do item no sistema origem" sortable="true"	 style="text-align: center"/>
			<display:column property="objetoNegocio.descricaoR1" title="Valor de associação" sortable="true" style="text-align: center"	/>
			<display:column property="objetoNegocio.descricaoR3" title="Valor de associação do tipo de empreendimento" sortable="true" style="text-align: center"	/>
			<display:column property="numeroLinha" title="Número da linha" sortable="true"	 style="text-align: center"/>
			<display:column property="linha" title="Linha" sortable="true"	/>
		</display:table>

		<h3>Rejeitados</h3>
		<display:table id="logRejeitados" name="<%=bean.getLinhasRegistrosInvalidos()%>" class="sortable" export="true"
			pagesize="<%=configuracao.getNuItensExibidosPaginacao() %>">
			<display:column property="objetoNegocio.siglaIett" title="Identificador do item no sistema origem" sortable="true" 	 style="text-align: center"/>
			<display:column property="objetoNegocio.descricaoR1" title="Valor de associação" sortable="true" style="text-align: center"	/>
			<display:column property="objetoNegocio.descricaoR3" title="Valor de associação do tipo de empreendimento" sortable="true" style="text-align: center"	/>
			<display:column property="motivo.mensagem" title="Motivo de rejeição" sortable="true"	/>
			<display:column property="numeroLinha" title="Número da linha" sortable="true"  style="text-align: center"	/>
			<display:column property="linha" title="Linha" sortable="true"/>
		</display:table>
			
		<table class="barrabotoes" cellpadding="0" cellspacing="0">
			<tr>
				<c:choose>
					<c:when test="<%=bean.getLinhasRegistrosValidos().isEmpty()%>">
						<td colspan="1" align="center">
							<input id="2" type='button' name='voltarBtn' value='Voltar' onclick="javascript:voltar();">
						</td>
					</c:when>
					<c:otherwise>
						<td colspan="1" align="center">
							<input id="1" type='button' name='confirmarBtn' value='Confirmar' onclick="javascript:disparaAcao();" <%=expDisabled %> >
							<input id="2" type='button' name='voltarBtn' value='Cancelar' onclick="javascript:voltar();">
						</td>
					</c:otherwise>
				</c:choose>
			</tr>
		</table>

</form>

</div>
</body>
<% /* Controla o estado do Menu (aberto ou fechado) */%>
<%@ include file="../include/estadoMenu.jsp"%>
<%@ include file="../include/mensagemAlert.jsp" %>
</html>