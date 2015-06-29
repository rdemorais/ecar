<%@ include file="../../login/validaAcesso.jsp"%>
<%@ include file="../../frm_global.jsp"%>

<%@ page import="ecar.pojo.AcompReferenciaAref" %> 
<%@ page import="ecar.dao.AcompReferenciaDao" %> 
<%@ page import="ecar.pojo.AcompRefLimitesArl" %>
<%@ page import="ecar.pojo.TipoFuncAcompTpfa" %> 
<%@ page import="ecar.dao.TipoFuncAcompDao" %> 

<%@ page import="comum.util.Data" %> 

<%@ page import="java.util.Iterator" %> 
<%@ page import="java.util.List" %> 
<%@ page import="java.util.Set" %>
<%@ page import="java.util.Collections" %>
<%@ page import="java.util.Comparator" %>
<%@ page import="comum.util.Pagina" %>
<%@ page import="comum.util.Util" %>
<%@ page import="ecar.pojo.OrgaoOrg" %>
<%@ page import="ecar.dao.TipoAcompanhamentoDao" %>
<%@ page import="ecar.bean.OrdenacaoDataTpfa" %>
<%@ page import="ecar.pojo.TipoAcompanhamentoTa" %>

<%@ taglib uri="/WEB-INF/ecar-util.tld" prefix="util" %>

<%
session.removeAttribute("acompReferencia");
session.removeAttribute("listaAcompReferencia");
session.removeAttribute("periodoRef");
%>

<html lang="pt-br">
<head>  
<%@ include file="../../include/meta.jsp"%>
<%@ include file="/titulo.jsp"%>
<link rel="stylesheet" href="<%=_pathEcar%>/css/style_<%=configuracaoCfg.getEstilo().getNome()%>.css" type="text/css">
<script language="javascript" src="../../js/menu_retratil.js"></script>
<script language="javascript" src="../../js/focoInicial.js"></script>
<script language="javascript" src="../../js/frmPadraoRegAcompanhamento.js"></script>
<script language="javascript" src="../../js/validacoes.js"></script>
<script language="javascript" src="<%=_pathEcar%>/js/destaqueLinha.js"></script>

<script language="javascript">
function aoClicarConsultar(form, codigo){
	form.codigo.value = codigo;
	form.hidAcao.value = 'alterar';
	document.form.action = "frm_alt.jsp";
	document.form.submit();
}

function aoClicarExcluirReferencia(form, nomeCheckBox){
	if(validarExclusao(form, nomeCheckBox)){
		if(form.existeQtde.value == ""){
			if(!confirm("Confirma a exclusão?")){
				return(false);
			}
		}
		
		if(form.existeQtde.value == "SIM"){
			if(confirm("<%=_msg.getMensagem("periodoReferencia.exclusao.temQuantidade")%>") == true){
				form.hidAcao.value = "excluir";
				form.nomeCheckBox.value = nomeCheckBox;
				form.action = "ctrl.jsp";
				form.submit();
			}
			else
			{
				alert("<%=_msg.getMensagem("periodoReferencia.exclusao.cancelado")%>");
				form.action = "lista.jsp";
				form.submit();
			}
		}
		else if(form.existeQtde.value == "NAO"){
			form.hidAcao.value = "excluir";
			form.nomeCheckBox.value = nomeCheckBox;
			form.action = "ctrl.jsp";
			form.submit();	
		}
		else {
			form.nomeCheckBox.value = nomeCheckBox;
			form.action = "lista.jsp";
			form.submit();
		}
	}
}	
</script>
 
</head>

<%
request.setAttribute("exibirAguarde", "true");
%>
<%@ include file="../../cabecalho.jsp" %>
<%@ include file="../../divmenu.jsp"%>
<%
String codTipoAcompanhamento = Pagina.getParamStr(request, "codTipoAcompanhamento");
TipoAcompanhamentoDao taDao = new TipoAcompanhamentoDao(request);

//ConfiguracaoCfg configuracao = new ConfiguracaoDao(request).getConfiguracao();

// controle para mostrar como default o primeiro tipo de acompanhamento das abas selecionado
if("".equals(codTipoAcompanhamento)) {
	List listTa = taDao.getTipoAcompanhamentoAtivosComAcompanhamentoBySeqApresentacao();
	
	if(!listTa.isEmpty()) {
		codTipoAcompanhamento = ((TipoAcompanhamentoTa)listTa.get(0)).getCodTa().toString();
	} 
}

TipoAcompanhamentoTa tipoAcompanhamentoTa = new TipoAcompanhamentoTa();
if(!"".equals(codTipoAcompanhamento))
	tipoAcompanhamentoTa = (TipoAcompanhamentoTa) taDao.buscar(TipoAcompanhamentoTa.class, Long.valueOf(codTipoAcompanhamento));

ValidaPermissao validaPermissao = new ValidaPermissao();
boolean permissaoAdicionarReferencia = false;

/*if(validaPermissao.permissaoAcessoReferenciaMonitoramento(seguranca.getGruposAcesso()) ||
	validaPermissao.permissaoAcessoReferenciaSecretaria(seguranca.getGruposAcesso()) ||
	validaPermissao.permissaoAcessoReferenciaOutraSecretaria(seguranca.getGruposAcesso())) {

	permissaoAcesso = true;
}*/


permissaoAdicionarReferencia = validaPermissao.permissaoAcessoReferencia(seguranca.getGruposAcesso());

%>

<body> 
<div id="conteudo">

<!-- TÍTULO -->
<%@ include file="/titulo_tela.jsp"%> 

<%
if(permissaoAdicionarReferencia) {
%>
	<input type="button" value="Adicionar Referência" class="botao" onclick="javascript:aoClicarIncluir(document.form)"> 	
	<br></br>
<%
}
%>

<%
if("".equals(codTipoAcompanhamento)) {
%>
	<form name="form" method="post">
		<input type="hidden" name="hidAcao" value="">
		<table width="100%" border="0" cellpadding="0" cellspacing="0">	
			<tr class="linha_subtitulo" align="left">
				<td>
					<%=_msg.getMensagem("tipoAcompanhamento.ativo.comAref.nenhumRegistro")%>
				</td>
			</tr>
			<%
			if(permissaoAdicionarReferencia) {
			%>
				<tr>
					<td class="espacador">
						<img src="../../images/pixel.gif">
					</td>
				</tr>
				<tr class="linha_titulo" align="left">
					<td align="right">

						<!-- input type="button" value="Adicionar Referência" class="botao" onclick="javascript:aoClicarIncluir(document.form)"--> 
					</td>
				</tr>
				<tr>
					<td class="espacador">
						<img src="../../images/pixel.gif">
					</td>
				</tr>
			<%
			} else {
			%>
				<tr>
					<td class="espacador">
						<img src="../../images/pixel.gif">
					</td>
				</tr>
				<tr class="linha_titulo">
					<td align="center">Você não possui acesso aos acompanhamentos</td>
				</tr>
				<tr>
					<td class="espacador">
						<img src="../../images/pixel.gif">
					</td>
				</tr>
			<%
			}
			%>
		</table>
	</form>

<%
} else {
	boolean permissaoAcesso = false;
	permissaoAcesso = validaPermissao.permissaoAcessoReferencia(tipoAcompanhamentoTa, seguranca.getGruposAcesso());	
%>
	<util:barraLinkTipoAcompanhamentoTag
		codTipoAcompanhamentoSelecionado="<%=codTipoAcompanhamento%>"
		chamarPagina="cadastroPeriodoReferencia/lista.jsp"
		exibirAbaFiltro="<%=Boolean.FALSE%>"
		gerarPeriodo="<%=Boolean.TRUE%>"
	/> 

<%
	try {
		String descricaoTipoAcompanhamentoSelecionado = ((TipoAcompanhamentoTa)taDao.buscar(TipoAcompanhamentoTa.class, Long.valueOf(codTipoAcompanhamento))).getDescricaoTa();
		TipoFuncAcompDao tipoFuncAcompDao = new TipoFuncAcompDao(request);
		AcompReferenciaDao acompDao = new AcompReferenciaDao(request);
		
		List listCodigosParaExcluir = new ArrayList();
		
		String codigosParaExcluir[] = request.getParameterValues(Pagina.getParamStr(request, "nomeCheckBox"));
		String existeQtde = "";
			
		if(codigosParaExcluir != null && codigosParaExcluir.length > 0){
			for(int i = 0; i< codigosParaExcluir.length; i++){
				listCodigosParaExcluir.add(codigosParaExcluir[i]);
			}
			if(acompDao.existeQuantidades(codigosParaExcluir)){
				existeQtde = "SIM";
			}
			else{
				existeQtde = "NAO";
			}
		}
	
		List lAcomp = new ArrayList();
		
	
		if (validaPermissao.permissaoAcessoReferenciaTodosOrgaos(tipoAcompanhamentoTa, seguranca.getGruposAcesso())) {
			lAcomp.addAll(acompDao.getListAcompReferencia());	
		}
		
	
		if (validaPermissao.permissaoAcessoReferenciaSeusOrgaos(tipoAcompanhamentoTa, seguranca.getGruposAcesso())){
			Iterator itUsuOrg = seguranca.getUsuario().getOrgaoOrgs().iterator();
			while (itUsuOrg.hasNext()) {
				OrgaoOrg usuOrg = (OrgaoOrg) itUsuOrg.next();

				List listAux = acompDao.getListAcompReferenciaProprioOrgao(usuOrg);
				Iterator itAux = listAux.iterator();
				while (itAux.hasNext()) {
					AcompReferenciaAref acompAref = (AcompReferenciaAref) itAux.next();
					if(!lAcomp.contains(acompAref)) {
						lAcomp.add(acompAref);
					}
				}
			}
		}
		
			
		if(!lAcomp.isEmpty()) {
			Iterator itAcomp = lAcomp.iterator();
			
			while(itAcomp.hasNext()) {
				AcompReferenciaAref acompAref = (AcompReferenciaAref) itAcomp.next();
				
				// filtrar os AREFs pelo tipo de acompanhamento selecionado
				if(acompAref.getTipoAcompanhamentoTa().getCodTa().longValue() != Long.parseLong(codTipoAcompanhamento)) {
					itAcomp.remove();
				}	
				
			}
		}
		
		// agrupar os acompanhamentos pela sequencia de apresentação do tipo de acompanhamento
		// e, se existir, pelo órgão também.
		if(lAcomp != null) {
	
			Collections.sort(lAcomp,
				new Comparator() {
					public int compare(Object o1, Object o2) {
						AcompReferenciaAref aRef1 = (AcompReferenciaAref) o1;
						AcompReferenciaAref aRef2 = (AcompReferenciaAref) o2;
						
						String ordem1 = Util.completarZerosEsquerda(aRef1.getTipoAcompanhamentoTa().getSeqApresentacaoTa(), 3)
							 + Util.completarZerosEsquerda((aRef1.getOrgaoOrg() != null) ? aRef1.getOrgaoOrg().getCodOrg() : null, 8);
	
						String ordem2 = Util.completarZerosEsquerda(aRef2.getTipoAcompanhamentoTa().getSeqApresentacaoTa(), 3)
							 + Util.completarZerosEsquerda((aRef2.getOrgaoOrg() != null) ? aRef2.getOrgaoOrg().getCodOrg() : null, 8);
	
						return ordem2.compareToIgnoreCase(ordem1);
					}
				});
		}
	
		List listOrdenacaoDataTpfa = tipoFuncAcompDao.getTpfaOfArlAndTipoAcompanhamentoOrderByDatas(Long.valueOf(codTipoAcompanhamento), lAcomp);

		Iterator itOrdenacaoDataTpfa;
%>
	
	<div id="subconteudo">
		
	<form name="form" method="post">
	
		<input type="hidden" name="codTipoAcompanhamento" value="<%=codTipoAcompanhamento%>">
		<input type="hidden" name="hidAcao" value="">
		<input type="hidden" name="codigo" value="">
		<input type="hidden" name="nomeCheckBox" value="">
		<input type="hidden" name="existeQtde" value="<%=existeQtde%>">
		 
		<table width="100%" border="0" cellpadding="0" cellspacing="0">	
<%
		Iterator itAcomp = lAcomp.iterator();
		
		boolean exibirCabecalho = true;
		boolean referenciasSemOrgao = false;
		String orgaoAtual = "";

		int cont = 0;
		String cor = new String();
		
		if (itAcomp.hasNext()){
			while (itAcomp.hasNext()) {
				if (cont % 2 == 0){
					cor = "cor_nao";
				} else {
					cor = "cor_sim"; 
				}
				AcompReferenciaAref acompAref = (AcompReferenciaAref) itAcomp.next();
				
				if (acompAref.getOrgaoOrg() == null){
					referenciasSemOrgao = true;
				}
%>				
				<%
				if(exibirCabecalho) {
				%>
					<tr class="linha_subtitulo2">
						<td colspan="<%=listOrdenacaoDataTpfa.size() + 5%>">&nbsp;</td>
					</tr>
					<tr>
						<td class="espacador" colspan="<%=listOrdenacaoDataTpfa.size() + 5%>"><img src="../../images/pixel.gif"></td>
					</tr>
					<tr class="linha_titulo" align="left">
						<td colspan="<%=listOrdenacaoDataTpfa.size()+3%>" align="left"><%=descricaoTipoAcompanhamentoSelecionado%>
							&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
							<%
							if(permissaoAcesso) {
							%> 
								<input type="button" value="Excluir" class="botao" onclick="javascript:aoClicarExcluirReferencia(document.form, 'excluir')">
							<%
							}
							%>
						</td>
					</tr>
					<tr>
						<td class="espacador" colspan="<%=listOrdenacaoDataTpfa.size() + 5%>"><img src="../../images/pixel.gif"></td>
					</tr>
					
					<tr class="linha_subtitulo">
						<td width="2%">
							<input type="checkBox" class="form_check_radio" name="todos" onclick="javascript:selectAll(document.form, 'todos', 'excluir')">
						</td>
						<td>
							Referência
						</td>
						<td>
							&nbsp;&nbsp;Dia/Mês/Ano
						</td>
						<%
						itOrdenacaoDataTpfa = listOrdenacaoDataTpfa.iterator();
						while (itOrdenacaoDataTpfa.hasNext()) {
							OrdenacaoDataTpfa ordenacao = (OrdenacaoDataTpfa) itOrdenacaoDataTpfa.next(); 
						%>
							<td align="center"><%=ordenacao.getLabel()%></td>
						<%				
						}
						%>
					</tr>
															
					<tr>
						<td class="espacador" colspan="<%=listOrdenacaoDataTpfa.size() + 5%>"><img src="../../images/pixel.gif"></td>
					</tr>	
				<%
					exibirCabecalho = false;
				}
	
				
								
				
				String orgaoItem = "";
				
				if(acompAref.getOrgaoOrg() != null){
					orgaoItem = Pagina.trocaNull(acompAref.getOrgaoOrg().getDescricaoOrg());
				}
												
				if(!orgaoAtual.equals(orgaoItem)){
					orgaoAtual = orgaoItem; 
					
					
					
				%>
					<tr class="linha_titulo">
						<td colspan="<%=listOrdenacaoDataTpfa.size() + 5%>">
				<%			if (referenciasSemOrgao){
					
				%>
							&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<%=configuracao.getLabelAgrupamentoItensSemOrgao()%>
							
				<%			}else{					
				%>			
							&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<%=orgaoAtual%>
							
				<%
								if("N".equalsIgnoreCase(acompAref.getOrgaoOrg().getIndAtivoOrg())){
									String linkImagem = request.getContextPath() + "/images/atencao.gif";
									String mensagemOrgaoInativo = "Órgão Inativo após a geração de ciclo.";
				%>
									<img src="<%=linkImagem%>" border="0" title="<%=mensagemOrgaoInativo%>">
				<%
								}
							}
				%>
						</td>
					</tr>	
				<%
					
				}
							
				String checked = "";
				if(listCodigosParaExcluir.contains(String.valueOf(acompAref.getCodAref()))){
					checked = "checked";
				}
%>
		
				<tr class="<%=cor%>" onmouseover="javascript:destacaLinha(this,'over','')" onmouseout="javascript: destacaLinha(this,'out','<%=cor%>')" onClick="javascript: destacaLinha(this,'click','<%=cor%>')" >
					<td width="2%">
						<%
						if(permissaoAcesso) {
						%>
							<input type="checkbox" class="form_check_radio" name="excluir" id="excluir" value="<%=acompAref.getCodAref()%>" <%=checked%>>
						<%
						} else {
						%>
							&nbsp;
						<%
						}
						%>
					</td>
					<td>
						<%
						if(permissaoAcesso) {
						%>
							<a href="javascript:aoClicarConsultar(document.form,<%=acompAref.getCodAref()%>)"><%=acompAref.getNomeAref()%></a>
						<%
						} else {
						%>
							<a href="javascript:alert('Você não tem permissão de acesso a este acompanhamento')"><%=acompAref.getNomeAref()%></a>
						<%
						}
						%>
					</td>
					<td>
						<%=acompAref.getDiaAref()%>/<%=acompAref.getMesAref()%>/<%=acompAref.getAnoAref()%>
					</td>
					<%
					itOrdenacaoDataTpfa = listOrdenacaoDataTpfa.iterator();
					
					List listAcompLimite = acompDao.getAcompRefLimitesOrderByFuncaoAcomp(acompAref);
					
					while (itOrdenacaoDataTpfa.hasNext()) {
						OrdenacaoDataTpfa ordenacao = (OrdenacaoDataTpfa) itOrdenacaoDataTpfa.next();
						
						String strValueData = "";
						Iterator itAcompLimite = listAcompLimite.iterator();
						
						while(itAcompLimite.hasNext()){
							AcompRefLimitesArl acompLimite = (AcompRefLimitesArl) itAcompLimite.next();
							
							if(ordenacao.getTpfa() != null && acompLimite.getTipoFuncAcompTpfa().equals(ordenacao.getTpfa())) {
								strValueData = Data.parseDate(acompLimite.getDataLimiteArl());
								break;
							}
							else if(ordenacao.getTpfaFixo() != null) {
								if(ordenacao.getTpfaFixo().equals(OrdenacaoDataTpfa.FUNCAO_INICIO)) {
									strValueData = Data.parseDate(acompAref.getDataInicioAref());
									break;
								}
								else if(ordenacao.getTpfaFixo().equals(OrdenacaoDataTpfa.FUNCAO_LIMITE)) {
									strValueData = Data.parseDate(acompAref.getDataLimiteAcompFisicoAref());
									break;
								}
							}
						}
						if("".equals(strValueData)) {
							strValueData = "&nbsp;&nbsp;&nbsp;-&nbsp;&nbsp;&nbsp;";
						}
					%>
						<td align="center"><%=strValueData%></td>
					<%
					}
					%>
				</tr>
	<%
				cont++;
			}
		} else {
			if(permissaoAcesso) {
	%>
				<tr>
					<td class="espacador">
						<img src="../../images/pixel.gif">
					</td>
				</tr>
				<tr class="linha_titulo" align="left">
					<td align="right">
						<!--input type="button" value="Adicionar Referência" class="botao" onclick="javascript:aoClicarIncluir(document.form)"--> 
					</td>
				</tr>
				<tr>
					<td class="espacador">
						<img src="../../images/pixel.gif">
					</td>
				</tr>
				<tr class="linha_subtitulo2">
					<td align="center">Acompanhamento "<%=descricaoTipoAcompanhamentoSelecionado%>" não encontrado.</td>
				</tr>
				<tr>
					<td class="espacador">
						<img src="../../images/pixel.gif">
					</td>
				</tr>
	<%
			} else {
	%>
				<tr>
					<td class="espacador">
						<img src="../../images/pixel.gif">
					</td>
				</tr>
				<tr class="linha_titulo">
					<td align="center">Você não possui acesso aos acompanhamentos</td>
				</tr>
				<tr>
					<td class="espacador">
						<img src="../../images/pixel.gif">
					</td>
				</tr>
	<%
				}
			}
			
			if(listCodigosParaExcluir.size() > 0) {
			%>
				<script>aoClicarExcluirReferencia(document.form, 'excluir')</script>
			<%
			}
			%>
		</table>
	</form>
	</div>
		
	<table width="100%" border="0" cellpadding="0" cellspacing="0">
		<tr><td>&nbsp;</td></tr>
	</table>
	
<%
	} catch (ECARException e) {
		org.apache.log4j.Logger.getLogger(this.getClass()).error(e);
		Mensagem.alert(_jspx_page_context, e.getMessageKey());
	} catch (Exception e){
	%>
		<%@ include file="/excecao.jsp"%>
	<%
	}
}
%>
</div>

</body>
<%@ include file="../../include/ocultarAguarde.jsp"%>
<% /* Controla o estado do Menu (aberto ou fechado) */%>
<%@ include file="../../include/estadoMenu.jsp"%>
<%@ include file="../../include/mensagemAlert.jsp" %>
</html>

