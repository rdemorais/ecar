<%@page import="ecar.pojo.intercambioDados.PerfilIntercambioDadosPflid"%>
<%@page import="comum.util.ConstantesECAR"%>
<%@page import="ecar.dao.intercambioDados.PerfilIntercambioDadosDao"%>
<%@ page import="comum.util.Data" %>
<%@ include file="../login/validaAcesso.jsp"%>
<%@ include file="../frm_global.jsp"%>

<%@ taglib uri="/WEB-INF/ecar-util.tld" prefix="util" %>
<%@ taglib uri="/WEB-INF/ecar-combo.tld" prefix="combo" %>

<%
	String caminhoArquivoGravado = Pagina.getParamStr(request, "caminhoArquivoGravado");
	PerfilIntercambioDadosDao perfilDao = new PerfilIntercambioDadosDao(request);
	
	List listaPerfil = perfilDao.pesquisar(null);
	PerfilIntercambioDadosPflid perfilSelecionado = null;
	
	String hidAcao = "";
	boolean isFormUpload = FileUpload.isMultipartContent(request);
	if (isFormUpload){
		List campos = FileUpload.criaListaCampos(request); 
		hidAcao = FileUpload.verificaValorCampo(campos, "hidAcao");	
	}
		
	if (!hidAcao.equals("voltar")){
		perfilSelecionado = (PerfilIntercambioDadosPflid)session.getAttribute("perfilSelecionado");
	}

	boolean _disabledManual = false;
%>




<%@page import="ecar.login.SegurancaECAR"%>
<%@page import="ecar.pojo.intercambioDados.funcionalidade.TipoFuncionalidadeEnum"%>
<%@page import="ecar.pojo.intercambioDados.funcionalidade.PerfilIntercambioDadosCadastroPidc"%>
<%@page import="ecar.pojo.intercambioDados.tecnologia.*"%>

<%@page import="comum.util.FileUpload"%><html lang="pt-br">
<head>
<%@ include file="../include/meta.jsp"%>
<%@ include file="/titulo.jsp"%>
<link rel="stylesheet" href="<%=_pathEcar%>/css/style_<%=configuracaoCfg.getEstilo().getNome()%>.css" type="text/css">
<script language="javascript" src="../js/menu_retratil.js"></script>
<script language="javascript" src="../js/validacoes.js"></script>
<script language="javascript" src="../js/util.js"></script>
<script language="javascript" src="../js/datas.js" type="text/javascript"></script>
<script language="javascript">

	function verificar_arq(acao){

		var validado = true;	

		var nomeArquivo = getNomeArquivo(document.form.arquivoImportacao.value);

		if (document.form.perfilImportacao.value == ""){
			alert("Selecione um perfil para a importação");
			validado = false;
		} else if (nomeArquivo.length > <%=Dominios.TAMANHO_MAXIMO_NOME_ARQUIVO_INTERCAMBIO_DADOS%> ) {
			<%
			String[] valorStr = new String[]{new Integer (Dominios.TAMANHO_MAXIMO_NOME_ARQUIVO_INTERCAMBIO_DADOS).toString()};
			%>
			alert ('<%=_msg.getMensagem("erro.tamanho.nome.arquivo",valorStr)%>');
			validado = false;
		} else {
			if (document.form.arquivoImportacao.value == ""){
				alert("Selecione um arquivo para importação.");
				validado = false;
			}
		}

		if (validado) {
			disparaAcao(acao);
		}
	}
	
	function disparaAcao(acao){

		if (acao == "alterarPerfil" || document.form.perfilImportacao.value != "") {
			document.form.hidAcao.value=acao;
			document.form.action = "ctrl.jsp";
			document.form.submit();
		} else if(document.form.perfilImportacao.value == ""){
			alert("Selecione o perfil.");
		}		
	}

	
	
</script>

</head>

<!-- sempre colocar o foco inicial no primeiro campo -->
<body>
<%@ include file="../cabecalho.jsp" %>
<%@ include file="../divmenu.jsp"%>

<div id="conteudo">

<form name="form" method="post" enctype="multipart/form-data" action="ctrl.jsp">
	<input type="hidden" name="hidAcao" value="">
	<input type="hidden" name="caminhoArquivoGravado" value="<%=caminhoArquivoGravado%>">
	
	<!-- TITULO -->
	<%@ include file="/titulo_tela.jsp"%>
	
		<table class="form">
			<tr>
				<td class="label">
					* Perfil
				</td>
				<td>
					<select name="perfilImportacao" onchange="javascript:disparaAcao('alterarPerfil');">
						<option value=""> </option>
						<%
							Iterator itListaPerfil = listaPerfil.iterator();
							while (itListaPerfil.hasNext()){
								PerfilIntercambioDadosPflid perfilIntegracao = (PerfilIntercambioDadosPflid)itListaPerfil.next();
								String selected = "";
								
								if (perfilIntegracao.equals(perfilSelecionado)) {
									selected = "selected";	
							}
						%>
							<option value="<%=perfilIntegracao.getCodPflid()%>" <%=selected%> ><%=perfilIntegracao.getNomePflid()%></option>
						<%
						}
						%>
					</select>
				</td>
			</tr>
			<c:if test="<%=perfilSelecionado != null%>">
			
				<tr>
					<td class="label">Sistema origem:</td>
					<td><%=perfilSelecionado.getNomeSistemaOrigemPflid()%></td>
				</tr>

				<tr>
					<td class="label">Usuário de associação aos elementos importados:</td>
					<td><%=perfilSelecionado.getUsuarioImportacao() != null ? perfilSelecionado.getUsuarioImportacao().getNomeUsu() : ((SegurancaECAR)session.getAttribute("seguranca")).getUsuario().getNomeUsu()%></td>
				</tr>
				
				
				
				<%if (perfilSelecionado.getIndAtivoAvisoImpPflid().equals(Dominios.SIM)) { %>
					<tr>
						<td class="label">Grupo de acesso para envio de email:</td>
						<td><%=perfilSelecionado.getGrupoAcessoEnvioEmailPflid().getDescricaoSatb()%></td>
					</tr>
				<%}%>
				<tr>
					<td class="label">Usuário responsável pela importação:</td>
					<td><%=((SegurancaECAR)session.getAttribute("seguranca")).getUsuario().getNomeUsu()%></td>
				</tr>
				
		
			<c:if test="<%= perfilSelecionado.getTipoFuncionalidade().equals(TipoFuncionalidadeEnum.CADASTRO)  %>">
			<table class="form">
			<tr>
				<td class="label">
					Tipo de funcionalidade:
				</td>
				<td>
					<%=TipoFuncionalidadeEnum.CADASTRO.getDescricao() %>
				</td>
			</tr>
			<tr>
				<td class="label">
					Grupo de acesso:
				</td>
				<td>
					<%=((PerfilIntercambioDadosCadastroPidc)perfilSelecionado).getGrupoAcessoItensImportadosPidc().getDescricaoSatb() %>
				</td>
			</tr>
			<tr>
				<td class="label">
					<strong>Situação</strong>
				</td>
				<td>
					
				</td>
			</tr>
			<tr>
				<td class="label">
					Valor não informado:
				</td>
				<td>
					<%=((PerfilIntercambioDadosCadastroPidc)perfilSelecionado).getSituacaoNaoInformadaPidc().getDescricaoSit() %>
				</td>
			</tr>
			<tr>
				<td class="label">
					Valor sem correpondência:
				</td>
				<td>
					<%=((PerfilIntercambioDadosCadastroPidc)perfilSelecionado).getSituacaoSemCorrespondentePidc().getDescricaoSit() %>
				</td>
			</tr>
			<tr>
				<td class="label">
					<strong>Associação</strong>
				</td>
				<td>
					
				</td>
			</tr>
			<tr>
				<td class="label">
					Estrutura base:
				</td>
				<td>
					<%=((PerfilIntercambioDadosCadastroPidc)perfilSelecionado).getEstruturaBasePidc().getNomeEtt() %>
				</td>
			</tr>
			<tr>
				<td class="label">
					Estrutura de nível superior:
				</td>
				<td>
					<%=((PerfilIntercambioDadosCadastroPidc)perfilSelecionado).getEstruturaItemNivelSuperiorPidc().getNomeEtt() %>
				</td>
			</tr>
			<tr>
				<td class="label">
					Estrutura de criação de itens:
				</td>
				<td>
					<%=((PerfilIntercambioDadosCadastroPidc)perfilSelecionado).getEstruturaCriacaoItemPidc().getNomeEtt() %>
				</td>
			</tr>
			</table>
			</c:if>
						
			<c:if test="<%= perfilSelecionado.getDadosTecnologiaPerfilDtp().getTipoTecnologia() == TipoTecnologiaEnum.ARQUIVOTEXTO_PACINTER  %>">
			<table class="form">
			<tr>
				<td class="label">
					Tecnologia de comunicação:
				</td>
				<td>
					<%=TipoTecnologiaEnum.ARQUIVOTEXTO_PACINTER.getDescricao()%>
				</td>
			</tr>
			</c:if>
			<tr>
				<td class="label">
					Nome da especificação:
				</td>
				<td>
					<%=((DadosTecnologiaPerfilTxtDtpt) perfilSelecionado.getDadosTecnologiaPerfilDtp()).getTipoTecnologia().getEspecificacao()%>
				</td>
			</tr>
			<tr>
				<td class="label">
					Versão da especificação:
				</td>
				<td>
					<%=((DadosTecnologiaPerfilTxtDtpt) perfilSelecionado.getDadosTecnologiaPerfilDtp()).getTipoTecnologia().getVersao()%>
				</td>
			</tr>
			<tr>
				<td class="label">
					Codificação de caracteres:
				</td>
				<td>
					<%=perfilSelecionado.getDadosTecnologiaPerfilDtp().getEncodeDtp()%>
				</td>
			</tr>
		
			<c:if test="<%= perfilSelecionado.getDadosTecnologiaPerfilDtp().getTipoTecnologia() == TipoTecnologiaEnum.ARQUIVOTEXTO_PACINTER  %>">
		
			<tr>
				<td class="label">
					Separador de campos:
				</td>
				<td>
					<%=((DadosTecnologiaPerfilTxtDtpt) perfilSelecionado.getDadosTecnologiaPerfilDtp()).getSeparadorCamposDtpt() %>
				</td>
			</tr>
			</c:if>
			
			<c:if test="<%= perfilSelecionado.getDadosTecnologiaPerfilDtp().getTipoTecnologia() == TipoTecnologiaEnum.ARQUIVOTEXTO_PACINTER  %>">
			  
			<tr>
				<td class="label">
					* Arquivo 
				</td>
				<td>
					<input type="file" id="arquivoImportacao" name="arquivoImportacao" maxlength="50" size="50" >
					<input type="hidden" name="hidArquivoImportacao" value="" >
				</td>
			</tr>
			</table>
 			</c:if> 
 		</c:if> 
		</table>
		<table class="barrabotoes" cellpadding="0" cellspacing="0">
			<tr>
				<td colspan="1" align="center">
					<input type='button' name='validarBtn' value='Importar' onclick="javascript:verificar_arq('validarArquivo');">
				</td>
			</tr>
		</table>
</form>

</div>
</body>
<% /* Controla o estado do Menu (aberto ou fechado) */%>
<%@ include file="../include/estadoMenu.jsp"%>
<%@ include file="../include/mensagemAlert.jsp" %>
</html>