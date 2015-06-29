<%@ include file="../../login/validaAcesso.jsp"%>

<%@ taglib uri="/WEB-INF/ecar-util.tld" prefix="util" %>
<%@ taglib uri="/WEB-INF/ecar-combo.tld" prefix="combo" %>
<%@ taglib uri="/WEB-INF/ecar-input.tld" prefix="ecar" %>

<%@ page import="ecar.pojo.AcompReferenciaAref" %>
<%@ page import="ecar.pojo.AcompReferenciaItemAri" %>
<%@ page import="ecar.pojo.AcompRelatorioArel" %>
<%@ page import="ecar.pojo.EntidadeEnt" %>
<%@ page import="ecar.pojo.ItemEstruturaIett" %>
<%@ page import="ecar.pojo.RegApontamentoRegda" %>
<%@ page import="ecar.pojo.RegDemandaRegd" %>
<%@ page import="ecar.pojo.StatusRelatorioSrl" %>

<%@ page import="ecar.dao.AcompReferenciaDao" %>
<%@ page import="ecar.dao.AcompReferenciaItemDao" %>
<%@ page import="ecar.dao.CorDao" %>
<%@ page import="ecar.dao.ItemEstruturaDao" %>
<%@ page import="ecar.dao.RegApontamentoDao" %>
<%@ page import="ecar.dao.RegDemandaDao" %>
<%@ page import="ecar.dao.TipoFuncAcompDao" %> 

<%@ page import="comum.util.Data" %>

<%@ page import="java.util.Collections" %>
<%@ page import="java.util.Map" %>
<%@ page import="java.util.Comparator" %>

<%@ include file="../../frm_global.jsp"%>

<%
session.removeAttribute("objPesquisa");
%> 

<html lang="pt-br">
<head>
<%@ include file="../../include/meta.jsp"%>
<%@ include file="/titulo.jsp"%>
<link rel="stylesheet" href="<%=_pathEcar%>/css/style_<%=configuracaoCfg.getEstilo().getNome()%>.css" type="text/css">
<script language="javascript" src="../../js/menu_retratil.js"></script>
<script language="javascript" src="../../js/focoInicial.js"></script>

<script language="javascript" src="../../js/validacoes.js"></script>
<script language="javascript" src="<%=_pathEcar%>/js/destaqueLinha.js"></script>

<script language="javascript">
function aoClicarCancelar(form){
	form.reset();
}
function onLoad(form) {
	aoClicarCancelar(form);
}
function aoClicarDemandas(form, codigo,codigoAba) {
	form.action = "lista.jsp?codIett="+codigo+"&codAba="+codigoAba;
	form.submit();
}
function aoClicarApontamento(form) {
	form.action = "frm_con.jsp";
	form.submit();
}
function aoClicarCadItem(form) {
	form.action = "lista.jsp?codAba=<%=Pagina.getParamStr(request, "codAba")%>&codIett=<%=Pagina.getParamStr(request, "codIett")%>";
	form.submit();
}
function aoClicarIncluirApontamento(form) {
	form.hidAcao.value = "incluir";
	form.action = "apt_frm_inc.jsp";
	form.submit();
}
function aoClicarDetalharApontamento(codRegda) {
	form.hidAcao.value = "detalhar";
	document.getElementById('codRegda').value = codRegda;
	form.action = "../apontamentos/detalhaApontamento.jsp";
	form.submit();
}
function aoClicarClassificarOrdenar(campo){
	if (campo != form.aptCampo.value) {
		form.aptCampo.value = campo;
		form.aptOrdem.value = "asc";
	} else {
		if (form.aptOrdem.value == "asc") {
			form.aptOrdem.value = "desc";
		} else {
			form.aptOrdem.value = "asc";
		}
	}
	form.aptRefazPesquisa.value = "refaz";
	form.action = "frm_con.jsp";
	form.submit();
}
function aoClicarExcluirApontamento(){
	if(validarExclusao()){
		if(!confirm("Confirma a exclusão?")){
			return(false);
		}
		form.hidAcao.value = "excluir";
		form.action = "apt_ctrl.jsp";
		form.submit();	
	}
}
function validarExclusao(){
	var algumMarcado = false;
	var numChecks = 0;

    numChecks = verCheckExc(form, "excluir");
	
    if (numChecks > 0) {
		if(numChecks > 1){
			for(i = 0; i < form.excluir.length; i++)
				if(form.excluir[i].checked)
					algumMarcado = true;	
		} else {
			algumMarcado = form.excluir.checked;
		}
	}
	
	if(!algumMarcado){
		alert("Pelo menos um registro deve ser selecionado.");
		return false;
	} else {
		return true;
	}
}
function verCheckExc(form, nome){
	var el = document.getElementsByTagName("INPUT");
	var i = 0, n = 0;
	while (i < el.length)
	{		
		if(el.item(i) != null){
			if (el.item(i).type == "checkbox" && el.item(i).name == nome){
				n++;
			}
		}
		i++;
	}
	
	return n;
}
function selectAll(form, nomeCheckBoxCtrl, nomeCheckBoxDep){

    if (typeof nomeCheckBoxCtrl == "undefined") nomeCheckBoxCtrl = "todos";
    if (typeof nomeCheckBoxDep == "undefined") nomeCheckBoxDep = "excluir";

	// Função para selecionar todos os Checkbox
	var numChecks = verificaChecks(form, nomeCheckBoxDep);

	if(numChecks > 1) {
		for(i = 0; i < eval('form.' + nomeCheckBoxDep + '.length'); i++)
			if(eval('form.' + nomeCheckBoxDep + '[i].disabled') == false) {
				eval('form.' + nomeCheckBoxDep + '[i]').checked = eval('form.' + nomeCheckBoxCtrl).checked;	
			}
	}
	
	if(numChecks == 1) {
		if(eval('form.' + nomeCheckBoxDep + '.disabled') == false) {
			eval('form.' + nomeCheckBoxDep).checked = eval('form.' + nomeCheckBoxCtrl).checked;	
		}
	}
}
function aoClicarConsultar(form, codigo, codIett, codTipoAcomp){ 
    document.form.codTipoAcompanhamento.value = codTipoAcomp;
    document.form.codIettRelacao.value = codIett;
	document.form.codAri.value = codigo;
	document.form.action = "<%=_pathEcar%>/acompanhamento/relAcompanhamento/resumo/detalharItem.jsp?primeiroIettClicado=" + codIett + "&primeiroAriClicado=" + codigo;
	document.form.submit();
}
</script>

</head>

<!-- sempre colocar o foco inicial no primeiro campo -->
<body onload="javascript:onLoad(document.form);">

<%@ include file="../../cabecalho.jsp" %>
<%@ include file="../../divmenu.jsp"%>

<%
try{

	// variaveis necessarias para voltar para a aba associacao de demandas
	String codIett = Pagina.getParam(request, "codIett");
	String codAba = Pagina.getParamStr(request, "codAba");
	String ultimoIdLinhaDetalhado = Pagina.getParamStr(request,"ultimoIdLinhaDetalhado");
	
	// se nao existirem as variaveis anteriores no request, elas estão guardadas na sessao
	// (somente guarda na sessao quando clicar no item de acompanhamento
	// para guardar as informações na navegação de abas)
	// A barraLinksRelatorioAcompanhamento é que trata dessas variaveis
	if(codAba== null || codAba.equals("")) {
		codAba = (String) session.getAttribute("codAbaDemanda");
	}
	
	if(codIett== null || codIett.equals("")) {
		codIett = (String) session.getAttribute("codIettDemanda");
	}
	
%>

<div id="conteudo">

<form name="form" method="post">
	<input type=hidden name="codIett" value="<%=codIett%>">
	<input type=hidden name="codAba" value="<%=codAba%>">
	<input type=hidden name="hidAcao" value="">
	<input type="hidden" name="codAri" value="">
	<input type="hidden" name="codTipoAcompanhamento" value="">
	<input type="hidden" name="ultimoIdLinhaDetalhado" value=<%=ultimoIdLinhaDetalhado%>>	
	
	<%//Campos de ordenação da listagem%>
	<input type="hidden" name="clCampo" value="<%=Pagina.getParam(request, "clCampo")%>">
	<input type="hidden" name="clOrdem" value="<%=Pagina.getParam(request, "clOrdem")%>">

	<!-- TITULO -->
	<%@ include file="/titulo_tela.jsp"%>
	
	
	<div id="linkstop">
		<a href="javascript:aoClicarDemandas(form, <%=codIett%>, <%=codAba%>);">Demandas</a>
		&nbsp;|&nbsp;
		<!-- a href="javascript:aoClicarApontamento(form);">c</a-->Encaminhamentos
		&nbsp;|&nbsp;
		[ <a href="javascript:aoClicarCadItem(form);">Voltar para Cadastro de Item</a> ]
	</div>
	
<%
	RegDemandaRegd regDemanda;
	RegDemandaDao regDemandaDao = new RegDemandaDao(request);
	TipoFuncAcompDao tipoFuncAcompDao = new TipoFuncAcompDao(request);
	AcompReferenciaDao acompReferenciaDao = new AcompReferenciaDao(request);
	AcompReferenciaItemDao acompReferenciaItemDao = new AcompReferenciaItemDao(request);
	ItemEstruturaDao itemEstruturaDao = new ItemEstruturaDao(request);
	CorDao corDao = new CorDao(request);
	
	List tpfaOrdenadosEstrutura = tipoFuncAcompDao.getFuncaoAcompOrderByEstruturas();
	
	if(session.getAttribute("objRegDemanda") != null){
		regDemanda = (RegDemandaRegd) session.getAttribute("objRegDemanda");
		session.removeAttribute("objRegDemanda");
	}else{
		regDemanda = (RegDemandaRegd) regDemandaDao.buscar(RegDemandaRegd.class, Long.valueOf (Pagina.getParam(request, "codRegd")));
	}
	
	String acao = "consulta";
	
	
%>

	<%@ include file="form_consulta.jsp"%>
	
	<br>
	
	<h1>Encaminhamentos</h1>
	
<%
	String aptCampo = "";
	String aptOrdem = "";
	
	if (Pagina.getParam(request, "aptCampo") != null) {
		aptCampo = Pagina.getParam(request, "aptCampo");
	} else {
		aptCampo = "dataRegda";
	}
	
	if (Pagina.getParam(request, "aptOrdem") != null) {
		aptOrdem = Pagina.getParam(request, "aptOrdem");
	} else {
		aptOrdem = "asc";
	}

	

	%>
	<!--Campos para a tela de detalhar item quando o acompanhamento é clicado  -->
	<input type="hidden" name="codIettRelacao" value="">
	<input type="hidden" name="associacaoDemanda" value="V">
	
	<!--Campos de ordenação da listagem  -->
	<input type="hidden" name="aptCampo" value="<%=aptCampo%>">
	<input type="hidden" name="aptOrdem" value="<%=aptOrdem%>">
	<input type="hidden" name="aptRefazPesquisa" value="">
	<div id="subconteudo">
    <table border="0" cellpadding="0" cellspacing="0" width="100%">
    	<tr><td class="espacador" colspan="4"><img src="../../images/pixel.gif"></td></tr>
    	
   		<tr class="linha_titulo" align="right">
			<td colspan="4">
				<input type="button" value="Adicionar Encaminhamento" class="botao" onclick="aoClicarIncluirApontamento(form);">
				<input type="button" value="Excluir Encaminhamento" class="botao" onclick="aoClicarExcluirApontamento();">
			</td>
		</tr>
		<tr><td class="espacador" colspan="4"><img src="../../images/pixel.gif"></td></tr>
<%
	try {
		RegApontamentoDao regApontamentoDao = new RegApontamentoDao(request);
		
		List lista = new ArrayList();
		lista.addAll(regDemanda.getRegApontamentoRegdas());
			
		// refaz a ordenação
		regApontamentoDao.classificarOrdenacao(aptCampo, aptOrdem, lista);
		request.getSession().setAttribute("listaApontamentos", lista);
		
		if (lista.size() > 0) {
%>
			<tr class="linha_subtitulo" align="right">
				<td width="1%" nowrap>
					<input type="checkbox" class="form_check_radio" name="todos" onclick="selectAll(form);">
				</td>
				<td align="center">
					<a href="javascript:aoClicarClassificarOrdenar('infoRegda');">
					Comentário</a>
				</td>
				<td align="center">
					<a href="javascript:aoClicarClassificarOrdenar('dataRegda');">
					Data Apontamento</a>
				</td>
				<td align="center">
					<a href="javascript:aoClicarClassificarOrdenar('usuarioUsu');">
					Usuário</a>
				</td>
			</tr>
			<tr><td class="espacador" colspan="4"><img src="../../images/pixel.gif"></td></tr>
<%
			Iterator it = lista.iterator();
			int cont = 0;
			String cor = new String(); 
			while (it.hasNext()) {
				if (cont % 2 == 0){
					cor = "cor_nao";
				} else {
					cor = "cor_sim"; 
				}
				
				RegApontamentoRegda regApontamento = (RegApontamentoRegda) it.next();
				
				String data = "";
				
				if (regApontamento.getDataRegda() != null) {
					data = Data.parseDate(regApontamento.getDataRegda());
				}
%>
				<tr class="<%=cor%>" onmouseover="javascript:destacaLinha(this,'over','')" onmouseout="javascript: destacaLinha(this,'out','<%=cor%>')" onClick="javascript: destacaLinha(this,'click','<%=cor%>')">
					<td nowrap="nowrap" width="1%">
						<input type="checkbox" class="form_check_radio" name="excluir" value="<%=regApontamento.getCodRegda()%>">
					</td>
					<td><a href="javaScript:aoClicarDetalharApontamento(<%=regApontamento.getCodRegda()%>);">]
					<%=regApontamento.getInfoRegda().replace("\r\n","<br>")%></a></td>
					<td align="center"><%=data%></td>
					<td><%=regApontamento.getUsuarioUsu().getNomeUsuSent()%></td>
				</tr>
<%
				cont++;
			}
%>
			<input type="hidden" name="codRegda" id="codRegda" value="">
<%
		} else {
%>
			<tr>
				<td class="texto" align="center" colspan="4">
				<b>Nenhum registro foi encontrado</b>
				</td>
			</tr>
			<tr><td class="espacador" colspan="4"><img src="../../images/pixel.gif"></td></tr>
<%
		}		
	} catch (Exception e) {
		org.apache.log4j.Logger.getLogger(this.getClass()).error(e);
		Mensagem.alert(_jspx_page_context, _msg.getMensagem("erro.exception"));
	}
%>
	
	</table>
	</div>
	<br>
	
	<h1>Itens Relacionados</h1>
	<div id="subconteudo">
	<table border="0" cellpadding="0" cellspacing="0" width="100%">
    	<tr><td class="espacador" colspan="4"><img src="../../images/pixel.gif"></td></tr>
   		<tr class="linha_subtitulo">
			<td align="center">Itens</td>
			<td align="center">Ult.<br>Acompanhamento</td>
			<td align="center">Situação</td>
			<td align="center">Data<br>Término</td>
		</tr>
		<tr><td class="espacador" colspan="4"><img src="../../images/pixel.gif"></td></tr>
<%
		List listaItens = new ArrayList();
		listaItens.addAll(regDemanda.getItemRegdemandaIregds());
		
		if (listaItens.size() > 0) {
			Iterator itItem = listaItens.iterator();
			
			List listFunAcomp = tipoFuncAcompDao.getTipoFuncAcompEmitePosicao();
			StatusRelatorioSrl statusLiberado = (StatusRelatorioSrl) itemEstruturaDao.buscar(StatusRelatorioSrl.class, Long.valueOf(AcompReferenciaItemDao.STATUS_LIBERADO));
			
			
			session.removeAttribute("linkCodDemanda");
			session.removeAttribute("associacaoDemanda");
			session.setAttribute("linkCodDemanda", regDemanda.getCodRegd());
			
			
			// Variavel usada para que a tag BarraLinksRelatorioAcompanhamento reconheça que 
			// a requisicao para acessar a tela avaliações veio de associacao e nao de registro de demandas
			session.removeAttribute("ehAssociacao");
			session.setAttribute("ehAssociacao", "S");
			
			// Variavel usada para que a tag BarraLinksRelatorioAcompanhamento  
			// guarde as informações necessarias para voltar para associacao de demandas
			session.removeAttribute("codAbaDemanda");
			session.setAttribute("codAbaDemanda", codAba);
			session.removeAttribute("codIettDemanda");
			session.setAttribute("codIettDemanda", codIett);

			int cont = 0;
			String cor = new String(); 
			while (itItem.hasNext()) {
				if (cont % 2 == 0){
					cor = "cor_nao";
				} else {
					cor = "cor_sim"; 
				}
				ItemEstruturaIett item = (ItemEstruturaIett) itItem.next();
				
				Map map = acompReferenciaItemDao.criarMapPeriodoAri(null, item);
				
				//Verificar se tem Acompanhamento caso houver selecionar o último
				AcompReferenciaAref acomp = null;

				List arefs = acompReferenciaDao.getUltimoAcompanhamentoItem(item, Integer.valueOf(1));
				if (!arefs.isEmpty()){
					acomp = (AcompReferenciaAref) arefs.get(0);
				}

				AcompReferenciaItemAri ari = null;
				
				if (acomp != null) {
					ari = (AcompReferenciaItemAri) map.get(acomp);
				}
%>
				<tr class="<%=cor%>" onmouseover="javascript:destacaLinha(this,'over','')" onmouseout="javascript: destacaLinha(this,'out','<%=cor%>')" onClick="javascript: destacaLinha(this,'click','<%=cor%>')">
					<td>
						<p><%=item.getEstruturaEtt().getNomeEtt()%>&nbsp;-&nbsp;
						<%if (ari != null && (ari.getStatusRelatorioSrl().equals(statusLiberado) ||
								(ari.getAcompRelatorioArels() != null && ari.getAcompRelatorioArels().size() > 0))) {%>
							
							<a href="#" onclick="javascript:aoClicarConsultar(form, <%=ari.getCodAri()%>, <%=ari.getItemEstruturaIett().getCodIett()%>, <%=ari.getAcompReferenciaAref().getTipoAcompanhamentoTa().getCodTa()%>)">
						<%}%>
							<%=item.getNomeIett()%>
						<%if (ari != null && ari.getStatusRelatorioSrl().equals(statusLiberado)) {%>
							</a>
						<%}%>
						</p>
					</td>
					<td align="center">
					<%if (acomp == null) {%>
						<p title="Não foi solicitado acompanhamento">N/A</p>
					<%} else {%>
						<p><%=acomp.getMesAref() + "/" + acomp.getAnoAref()%></p>
					<%}%>
					</td>
					<td align="center" nowrap>
<%
					if(acomp == null || map == null || !map.containsKey(acomp)) {
					%>
						<p title="Não foi solicitado acompanhamento">N/A</p>
					<%
					} else {
						if(ari != null && ari.getStatusRelatorioSrl().equals(statusLiberado)) {
							out.print("<p>");
							List relatorios = acompReferenciaItemDao.getAcompRelatorioArelOrderByFuncaoAcomp(ari, tpfaOrdenadosEstrutura);
							Iterator itRelatorios = relatorios.iterator();
							Collections.reverse(relatorios);

							while(itRelatorios.hasNext()) {
								StringBuffer aval = new StringBuffer("");
								AcompRelatorioArel relatorio = (AcompRelatorioArel) itRelatorios.next();
								
								if(relatorio.getCor() != null && "S".equals(relatorio.getIndLiberadoArel())) {
									aval.append("<image border=\"0\" src=\"../../images/relAcomp/");
									aval.append(corDao.getImagemRelatorio(relatorio.getCor(), relatorio.getTipoFuncAcompTpfa()));
									aval.append("\" title=\"").append(relatorio.getTipoFuncAcompTpfa().getLabelTpfa()).append("\"");
									aval.append(">");
								}
								else {
									aval.append("<image border=\"0\" src=\"../../images/relAcomp/");
									aval.append(corDao.getImagemRelatorio(null, relatorio.getTipoFuncAcompTpfa()));
									aval.append("\" title=\"").append(relatorio.getTipoFuncAcompTpfa().getLabelTpfa()).append("\"");
									aval.append(">");
								}
								out.print(aval);
							}
							out.print("</p>");
						} else {
							if (ari != null && ari.getAcompRelatorioArels() != null && ari.getAcompRelatorioArels().size() > 0) {
							%>
								<p title="Não liberado">N/L</p>
							<%
							} else {
							%>
								<p title="Não foi solicitado acompanhamento">N/A</p>
							<%
							}
						}
					}
%>
					</td>
					<td align="center">
						<p><%=Pagina.trocaNullData(item.getDataTerminoIett())%></p>
					</td>
				</tr>
<%
				cont++;
			}
		} else {
%>
			<tr>
				<td class="texto" align="center" colspan="4">
				<b>Nenhum registro foi encontrado</b>
				</td>
			</tr>
<%
		}		
%>
		<tr><td class="espacador" colspan="4"><img src="../../images/pixel.gif"></td></tr>
	</table>
	</div>	
	
	<%@ include file="../../include/estadoMenu.jsp"%>
</form>

</div>
<%
} catch (Exception e){
%>
	<%@ include file="/excecao.jsp"%>
<%
}
%>
</body>
<%@ include file="../../include/mensagemAlert.jsp"%>
</html>