<%@page import="ecar.pojo.intercambioDados.EntidadeLogIntercambioDadosEtlogid"%>
<%@page import="ecar.pojo.intercambioDados.LogIntercambioDadosLid"%>
<%@page import="comum.util.ConstantesECAR"%>
<%@page import="ecar.dao.intercambioDados.LogIntercambioDadosDao"%>

<%@ include file="../login/validaAcesso.jsp"%>
<%@ include file="../frm_global.jsp"%>

<%@ taglib uri="/WEB-INF/ecar-util.tld" prefix="util" %>
<%@ taglib uri="/WEB-INF/ecar-combo.tld" prefix="combo" %>

<%
    LogIntercambioDadosDao logDao = new LogIntercambioDadosDao(request);
    String codLid = Pagina.getParamStr(request, "codLid");
    LogIntercambioDadosLid log = (LogIntercambioDadosLid) logDao.buscar(LogIntercambioDadosLid.class, new Long(codLid));
    log.gerarResumo();
     
%>

<%@page import="ecar.login.SegurancaECAR"%>
<%@page import="comum.util.Data"%>
<%@page import="ecar.pojo.intercambioDados.funcionalidade.PerfilIntercambioDadosCadastroPidc"%>
<%@page import="ecar.pojo.intercambioDados.tecnologia.DadosTecnologiaPerfilTxtDtpt"%>
<%@page import="ecar.pojo.intercambioDados.funcionalidade.TipoFuncionalidadeEnum"%><html lang="pt-br">
<head>
<%@ include file="../include/meta.jsp"%>
<%@ include file="/titulo.jsp"%>
<link rel="stylesheet" href="<%=_pathEcar%>/css/style_<%=configuracaoCfg.getEstilo().getNome()%>.css" type="text/css">
<script language="javascript" src="../js/menu_retratil.js"></script>
<script language="javascript" src="../js/validacoes.js"></script>
<script language="javascript" src="../js/destaqueLinha.js"></script>
<script language="javascript">


	function disparaAcao(acao){
		document.form.hidAcao.value=acao;
		document.form.action = "ctrl.jsp";
		document.form.submit();
	}

	function voltar(){
		document.form.hidAcao.value="voltar";
		document.form.action = "listaLog.jsp?d-2423786-p=<%=request.getParameter("voltar")%>";
		document.form.submit();
	}
	
	
</script>

</head>

<!-- sempre colocar o foco inicial no primeiro campo -->
<body onload="javascript:destacaLinhaDisplayTag('logRejeitados');destacaLinhaDisplayTag('logImportados');">
<%@ include file="../cabecalho.jsp" %>
<%@ include file="../divmenu.jsp"%>

<div id="conteudo">

<form name="form" method="post" action="ctrl.jsp">
	<input type="hidden" name="hidAcao" value="">
		
	<!-- TITULO -->
	<%@ include file="/titulo_tela.jsp"%>
	
		<c:if test="<%=session.getAttribute("tituloPage") != null%>" >
			<center><h1><%=session.getAttribute("tituloPage").toString()%> </h1></center>
		</c:if>
		
		<table class="barrabotoes" cellpadding="0" cellspacing="0">
			<tr>
				<td align="center">
					<input id="4" type='button' name='voltarBtn' value='Voltar' onclick="javascript:voltar();">
				</td>
			</tr>
		</table>
		<table class="form">
			<tr>
				<td class="label">
					Data/hora de processamento
				</td>
				<td>
					<%=Data.parseDateHourMinuteSecond(log.getDataHoraProcessamentoLid())%>
				</td>
			</tr>
		     <tr>
				<td class="label">
					Situação
				</td>
				<td>
					<%=log.getDescSituacaoProcessamentoLid()%>
				</td>
			</tr>
			
			 <tr>
				<td class="label">
					Usuário responsável
				</td>
				<td>
					<%=log.getUsuarioUsu().getNomeUsu()%>
				</td>
			</tr>
			</table >
			<table class="form">
			<tr>
				<td class="label">
					Perfil
				</td>
				<td>
					
				</td>
			</tr>
			<tr>
				<td class="label">
					Nome
				</td>
				<td>
					<%=log.getPerfilLog().getNomePflid()%>
				</td>
			</tr>
			<tr>
				<td class="label">
					Sistema origem
				</td>
				<td>
					<%=log.getPerfilLog().getNomeSistemaOrigemPflid()%>
				</td>
			</tr>
			<tr>
				<td class="label">
					Usuário associado ao elemento
				</td>
				<td>
					<%=log.getUsuarioUsu() != null ? log.getUsuarioUsu().getNomeUsu() : ((SegurancaECAR)session.getAttribute("seguranca")).getUsuario().getNomeUsu()%>
				</td>
			</tr>
			</table>
			<table class="form">
			<tr>
				<td class="label">
					Tipo de funcionalidade
				</td>
				<td>
					<%=log.getPerfilLog().getTipoFuncionalidade()%>
				</td>
			</tr>
			<tr>
				<td class="label">
					Grupo de acesso para permissão ao item
				</td>
				<td>
					<%
						if (log.getPerfilLog().getTipoFuncionalidade().equals(TipoFuncionalidadeEnum.CADASTRO.getDescricao())) {
							out.println (log.getPerfilLog().getGrupoAcessoItensImportadosPidc());
						}
					 %>
				</td>
			</tr>
			<tr>
				<td class="label">
					Estrutura base
				</td>
				<td>
					<%
					if (log.getPerfilLog().getTipoFuncionalidade().equals(TipoFuncionalidadeEnum.CADASTRO.getDescricao())){
						out.println (log.getPerfilLog().getEstruturaBasePidc());
					}
					%>
				</td>
			</tr>
			<tr>
				<td class="label">
					Atributo de associação na estrutura base 
				</td>
				<td>
					<%
						if (log.getPerfilLog().getTipoFuncionalidade().equals(TipoFuncionalidadeEnum.CADASTRO.getDescricao())) {
							out.println (log.getPerfilLog().getAtributoBasePidc());
						}
					%>
				</td>
			</tr>			
			<tr>
				<td class="label">
					Estrutura do item de nível superior
				</td>
				<td>
					<%
						if (log.getPerfilLog().getTipoFuncionalidade().equals(TipoFuncionalidadeEnum.CADASTRO.getDescricao())) {
							out.println (log.getPerfilLog().getEstruturaItemNivelSuperiorPidc());
						}
					%>
				</td>
			</tr>
			<tr>
				<td class="label">
					Atributo de associação na estrutura do item de nível superior 
				</td>
				<td>
					<%
						if (log.getPerfilLog().getTipoFuncionalidade().equals(TipoFuncionalidadeEnum.CADASTRO.getDescricao())) {
							out.println (log.getPerfilLog().getAtributoNivelSuperiorPidc());
						}
					%> 
				</td>
			</tr>
			<tr>
				<td class="label">
					Estrutura de criação de itens
				</td>
				<td>
					<%
						if (log.getPerfilLog().getTipoFuncionalidade().equals(TipoFuncionalidadeEnum.CADASTRO.getDescricao())) {
							out.println (log.getPerfilLog().getEstruturaCriacaoItemPidc());
						}
					%>
				</td>
			</tr>
			</table>
			<table class="form">
			<tr>
				<td class="label">
					Tecnologia de comunicação
				</td>
				<td>
					<%=log.getPerfilLog().getTipoTecnologia()%>
				</td>
			</tr>
			
			<tr>
				<td class="label">
					Nome da especificação
				</td>
				<td>
					<%=log.getPerfilLog().getNomeEspecificacaoDtp()%>
				</td>
			</tr>
			<tr>
				<td class="label">
					Versão da especificação
				</td>
				<td>
					<%=log.getPerfilLog().getVersaoEspecificacaoDtp()%>
				</td>
			</tr>
			<tr>
				<td class="label">
					Separador padrão
				</td>
				<td>
					<%=log.getPerfilLog().getSeparadorCamposDtpt()%>
				</td>
			</tr>
			<tr>
				<td class="label">
					Codificação de caracteres
				</td>
				<td>
					<%=log.getPerfilLog().getEncodeDtp()%>
				</td>
			</tr>
			<tr>
				<td class="label">
					Nome do arquivo
				</td>
				<td>
					<a href="#" onclick="javaScript:window.open('<%=_pathEcar%>/DownloadSGDB?idObjeto=<%=log.getCodLid()%>&classeInstanciada=ecar.action.ActionIntercambioDados')" ><%=log.getDadosTecnologia().getNomeArquivoDtlid()%></a>					
				</td>
			</tr>
			
			<tr>
				<td class="label">
					Data de geração
				</td>
				<td>
					<%if (log.getDadosTecnologia().getDataGeracaoArquivoDtlid() != null) {
						out.println(Data.parseDateHourMinuteSecond(log.getDadosTecnologia().getDataGeracaoArquivoDtlid()));
					} else {
						out.println("Data não registrada");
					}%>
				</td>
			</tr>
			
		</table>
		<%
		    if (log.getIndSituacaoProcessamentoLid().equalsIgnoreCase(ConstantesECAR.REJEICAO)
		    	&& log.getEntidadeLogIntercambioDadosEtlogidRejeitadas()!=null
		    	&& log.getEntidadeLogIntercambioDadosEtlogidRejeitadas().size()>0) {
		    	EntidadeLogIntercambioDadosEtlogid lid = (EntidadeLogIntercambioDadosEtlogid)log.getEntidadeLogIntercambioDadosEtlogidRejeitadas().iterator().next();
		%>
		<br>
			<h1>Rejeitado</h1>		
		<br>
		
		<table class="form">
			<tr>
				<td class="label">
					Motivo da Rejeição
				</td>
				<td>
					<%= lid.getMotivoRejeicaoEtlogid().getMensagem() %>
				</td>
			</tr>
			<tr>
				<td class="label">
					Numero da Linha
				</td>
				<td>
					<%=lid.getNumeroRegistroEtlogid()%>
				</td>
			</tr>
			<tr>
				<td class="label">
					Linha
				</td>
				<td>
					<%=lid.getRegistroEtlogid()%>
				</td>
			</tr>
		</table>
		<%
		    }
		%>
		<%
		    if (log.getIndSituacaoProcessamentoLid().equalsIgnoreCase(ConstantesECAR.PROCESSADO)) {
		%>
		<br>
			<h1>Estatísticas</h1>		
		<br>
		<table class="form">

			<tr>
				<td class="label">
					Elementos processados
				</td>
				<td>
					<%=log.getItensProcessados()%>
				</td>
			</tr>
			<tr>
				<td class="label">
					Elementos importados
				</td>
				<td>
					<%=log.getItensImportados()%>
				</td>
			</tr>
			<tr>
				<td class="label">
					Incluídos
				</td>
				<td>
					<%=log.getItensIncluidos()%>
				</td>
			</tr>
			<tr>
				<td class="label">
					Alterados
				</td>
				<td>
					<%=log.getItensAlterados()%>
				</td>
			</tr>
			<tr>
				<td class="label">
					Excluídos
				</td>
				<td>
					<%=log.getItensExcluidos()%>
				</td>
			</tr>
			<tr>
				<td class="label">
					Elementos rejeitados
				</td>
				<td>
					<%=log.getItensRejeitados()%>
				</td>
			</tr>
			
		</table>
  		<br>
  			<h1>Importados</h1>
  		<br>
  		<% if (log.getEntidadeLogIntercambioDadosEtlogidAceitas()!=null && log.getEntidadeLogIntercambioDadosEtlogidAceitas().size()>0) { %>
		<display:table id="logImportados" name="<%=log.getEntidadeLogIntercambioDadosEtlogidAceitas()%>" cellspacing="5" class="sortable" 
		pagesize="<%=configuracao.getNuItensExibidosPaginacao() %>" excludedParams="msgOperacao"
		export="true" list="list">
			<!-- display:column property="codLidi" title="Id" sortable="true" style="text-align: center"/-->
			<display:column property="descOperacaoRealizadaEtlogid" title="Operação realizada" sortable="true" style="text-align: center"/>
			<display:column property="codItemSistemaOrigemEtlogid" title="Identificador do item no sistema origem" sortable="true" style="text-align: center"/>
			<display:column property="valorLigacaoParsed" title="Valor de associação" sortable="true" style="text-align: center"/>
			<display:column property="valorTipoEmpreendimentoEtlogid" title="Valor de associação do tipo de empreendimento" sortable="true" style="text-align: center"/>
			<display:column property="numeroRegistroEtlogid" title="Número da linha" sortable="true" style="text-align: center"/>
			<display:column property="registroEtlogid" title="Linha processada" sortable="true"/>
		</display:table>
		<% } %>
		
		<br>
  			<h1>Rejeitados</h1>
  		<br>
  		<% if (log.getEntidadeLogIntercambioDadosEtlogidRejeitadas()!=null && log.getEntidadeLogIntercambioDadosEtlogidRejeitadas().size()>0) { %>
		<display:table id="logRejeitados" name="<%=log.getEntidadeLogIntercambioDadosEtlogidRejeitadas()%>" cellspacing="5" class="sortable" 
		pagesize="<%=configuracao.getNuItensExibidosPaginacao() %>"excludedParams="msgOperacao" 
		export="true" list="list">
			<!-- display:column property="codLidr" title="Id" sortable="true" paramId="codLidr" style="text-align: center"/-->
			<display:column property="codItemSistemaOrigemEtlogid" title="Identificador do item no sistema origem" sortable="true" style="text-align: center"/>
			<display:column property="valorLigacaoParsed" title="Valor de associação" sortable="true" style="text-align: center"/>
			<display:column property="valorTipoEmpreendimentoEtlogid" title="Valor de associação do tipo de empreendimento" sortable="true" style="text-align: center"/>
			<display:column property="motivoRejeicaoEtlogid.mensagem" title="Motivo da rejeição" sortable="true"/>
			<display:column property="numeroRegistroEtlogid" title="Número da linha" sortable="true" style="text-align: center"/>
			<display:column property="registroEtlogid" title="Linha processada" sortable="true" />
		</display:table>
		<% } %>
		
		<%}%>
		
		<table class="barrabotoes" cellpadding="0" cellspacing="0">
			<tr>
				<td align="center">
					<input id="4" type='button' name='voltarBtn' value='Voltar' onclick="javascript:voltar();">
				</td>
			</tr>
		</table>
</form>

</div>
</body>
<%
    /* Controla o estado do Menu (aberto ou fechado) */
%>
<%@ include file="../include/estadoMenu.jsp"%>
<%@ include file="../include/mensagemAlert.jsp"%>
</html>