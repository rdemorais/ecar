
<%@ include file="../../login/validaAcesso.jsp"%>
<%@ include file="../../frm_global.jsp"%>

<%@ taglib uri="/WEB-INF/ecar-util.tld" prefix="util"%>
<%@ taglib uri="/WEB-INF/ecar-combo.tld" prefix="combo"%>

<%@ page import="ecar.pojo.Aba"%>
<%@ page import="ecar.pojo.AcompReferenciaAref"%>
<%@ page import="ecar.pojo.AcompReferenciaItemAri"%>
<%@ page import="ecar.pojo.ExercicioExe"%>
<%@ page import="ecar.pojo.ItemEstruturaIett"%>
<%@ page import="ecar.pojo.UsuarioUsu" %>
<%@ page import="ecar.pojo.EfItemEstContaEfiec" %>
<%@ page import="ecar.pojo.EfItemEstPrevisaoEfiep" %>
<%@ page import="ecar.pojo.UsuarioUsu" %>
<%@ page import="ecar.pojo.FonteRecursoFonr" %>

<%@ page import="ecar.dao.AcompRealFisicoDao"%>
<%@ page import="ecar.dao.AcompReferenciaDao"%>
<%@ page import="ecar.dao.AcompReferenciaItemDao"%>
<%@ page import="ecar.dao.CorDao"%>
<%@ page import="ecar.dao.ItemEstruturaContaOrcamentoDao" %>
<%@ page import="ecar.dao.ItemEstruturaPrevisaoDao" %>
<%@ page import="ecar.dao.ItemEstruturaRealizadoDao" %>
<%@ page import="ecar.dao.ItemEstrtIndResulDao" %>
<%@ page import="ecar.dao.ItemEstruturaDao"%>
<%@ page import="ecar.dao.TipoFuncAcompDao"%>

<%@ page import="comum.util.Data"%>
<%@ page import="comum.util.Util" %>

<html lang="pt-br">
	<head>
		<%@ include file="../../include/meta.jsp"%>
		<%@ include file="/titulo.jsp"%>
		<link rel="stylesheet" href="<%=_pathEcar%>/css/style_<%=configuracaoCfg.getEstilo().getNome()%>.css" type="text/css">
		<script language="javascript" src="<%=_pathEcar%>/js/menu_retratil.js"
			type="text/javascript"></script>
		<script language="javascript" src="<%=_pathEcar%>/js/focoInicial.js"
			type="text/javascript"></script>
		<script language="javascript" src="<%=_pathEcar%>/js/popUp.js"
			type="text/javascript"></script>
		<script language="javascript" src="<%=_pathEcar%>/js/destaqueLinha.js"
			type="text/javascript"></script>
		<script type="text/javascript">
			function trocarAba(nomeAba) {
				document.forms[0].action = nomeAba;
				document.forms[0].submit();
			}
			<%--function voltarTelaAnterior(tela) {
				document.forms[0].action = tela;
				document.forms[0].submit();
			}--%>
		</script>
	</head>

	<%@ include file="../../cabecalho.jsp"%>
	<%@ include file="../../divmenu.jsp"%>

	<body>
		<div id="conteudo">
	<%
		AcompRealFisicoDao acompRealFisicoDao = new AcompRealFisicoDao(request);
		AcompReferenciaDao acompReferenciaDao = new AcompReferenciaDao(request);
		AcompReferenciaItemDao ariDao = new AcompReferenciaItemDao(request);
		//ConfiguracaoDao configuracaoDao = new ConfiguracaoDao(request);
		CorDao corDao = new CorDao(request);
		ItemEstrtIndResulDao indResultDao = new ItemEstrtIndResulDao(request);
		ItemEstruturaDao itemEstruturaDao = new ItemEstruturaDao(request);
		TipoFuncAcompDao tipoFuncAcompDao = new TipoFuncAcompDao(request);
		AcompReferenciaItemDao acompReferenciaItemDao = new AcompReferenciaItemDao(request);
	
		AcompReferenciaAref arefSelecionado = null;
		ItemEstruturaIett itemEstrutura = null;
		AcompReferenciaItemAri ari = null;
		
		UsuarioUsu usuario = ((ecar.login.SegurancaECAR)session.getAttribute("seguranca")).getUsuario();
		
		//pega os atributos do request
		String status = Pagina.getParamStr(request, "relatorio");
		String itemDoNivelClicado = Pagina.getParamStr(request, "itemDoNivelClicado");
		String codTipoAcompanhamento = Pagina.getParamStr(request, "codTipoAcompanhamento");
		String mesReferencia = Pagina.getParamStr(request, "referencia_hidden");

		String niveisPlanejamento = Pagina.getParamStr(request, "niveisPlanejamento");
		
		// Pra nao dar erro quando perder codTipoAcompanhamento
		// descobre pelo Ari
		String strAri = Pagina.getParamStr(request, "codAri");
		ari = (AcompReferenciaItemAri) new AcompReferenciaItemDao(request).buscar(
						AcompReferenciaItemAri.class, Long.valueOf(strAri));
		arefSelecionado = ari.getAcompReferenciaAref();
		
		if(codTipoAcompanhamento.equals("")) {
			codTipoAcompanhamento = arefSelecionado.getTipoAcompanhamentoTa().getCodTa().toString();
		} 
		
		List acompanhamentos = acompReferenciaDao.getListAcompReferenciaByTipoAcompanhamento(new Long(codTipoAcompanhamento));
	
		Iterator acompanhamentosIt = acompanhamentos.iterator();
		while(acompanhamentosIt.hasNext()) {
			arefSelecionado = (AcompReferenciaAref)acompanhamentosIt.next();
			if((arefSelecionado.getCodAref().toString()).equals(mesReferencia)) {
				break;
			}
		}
	
		if(arefSelecionado != null && !itemDoNivelClicado.trim().equals("") && itemDoNivelClicado != null ){
			itemEstrutura = (ItemEstruturaIett)itemEstruturaDao.buscar(ItemEstruturaIett.class, Long.valueOf(itemDoNivelClicado));
			// se tiver codAri
			if(ari == null && !strAri.equals("")) { 
				ari = (AcompReferenciaItemAri) ariDao.buscar(AcompReferenciaItemAri.class, Long.valueOf(strAri));
			} else if(ari == null) {
			// se nao tiver codAri
				ari = ariDao.getAcompReferenciaItemByItemEstruturaIett(arefSelecionado,itemEstrutura);
			}
			
		} else {
			if(ari != null)
				itemEstrutura = (ItemEstruturaIett) itemEstruturaDao.buscar(ItemEstruturaIett.class, ari.getItemEstruturaIett().getCodIett());
		}
	
		List tpfaOrdenadosPorEstrutura = tipoFuncAcompDao.getFuncaoAcompOrderByEstruturas();
		String periodo = Pagina.getParamStr(request, "periodo");
		String funcaoSelecionada = "FINANCEIRO";
	%>

			<%@ include  file="../form_registro.jsp" %>	
			
			<form name="form" id="form" method="post" action="">
				<input type="hidden" name="codTipoAcompanhamento" value="<%=codTipoAcompanhamento%>">
				<input type="hidden" name="periodo" value="<%=Pagina.getParamStr(request, "periodo")%>">
				<input type="hidden" name="niveisPlanejamento" value="<%=niveisPlanejamento%>">
				<input type="hidden" name="referencia_hidden" value="<%=mesReferencia%>">
				<input type="hidden" name="itemDoNivelClicado" value="<%=itemDoNivelClicado%>">
				<!-- Campo necessario para guardar a forma de visualização escolhida no filtro -->
				<input type="hidden" name="hidFormaVisualizacaoEscolhida" value='<%=Pagina.getParamStr(request, "hidFormaVisualizacaoEscolhida")%>'>
				<!-- input type="hidden" name="codAri" value="</%=ari.getCodAri().toString()%>"-->
				<input type="hidden" name="codAri" value="<%=Pagina.getParamStr(request, "codAri")%>">
				<input type="hidden" name="primeiroIettClicado" value="<%=Pagina.getParamStr(request, "primeiroIettClicado")%>">
				<input type="hidden" name="primeiroAriClicado" value="<%=Pagina.getParamStr(request, "primeiroAriClicado")%>">
				<!-- Campo necessário para botões de Avança/Retrocede -->
				<input type="hidden" name="codArisControle" value="<%=Pagina.getParamStr(request, "codArisControle")%>">
				<input type="hidden" name="tipoPadraoExibicaoAba" value="<%= Pagina.getParamStr(request, "tipoPadraoExibicaoAba")%>">

				<!-- começa a exibir informações do financeiro -->
				<%@ include file="financeiroTabela.jsp" %>
				<!-- termina de exibir as informações do financeiro -->
			</form>

		</div>
	</body>
	<%@ include file="/../../include/estadoMenu.jsp"%>
</html>