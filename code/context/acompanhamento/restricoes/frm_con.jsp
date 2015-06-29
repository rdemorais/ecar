<jsp:directive.page import="ecar.dao.AcompRelatorioDao"/>
<jsp:directive.page import="ecar.pojo.PontoCriticoPtc"/>
<jsp:directive.page import="ecar.pojo.AcompReferenciaAref"/>
<jsp:directive.page import="ecar.dao.PontoCriticoDao"/>
<jsp:directive.page import="ecar.dao.FuncaoDao"/>
<jsp:directive.page import="ecar.dao.SisGrupoAtributoDao"/><%@ include file="/login/validaAcesso.jsp"%>
<%@ include file="/frm_global.jsp"%>

<%@ page import="ecar.dao.AcompReferenciaItemDao"%>
<%@ page import="ecar.dao.EstruturaDao"%>
<%@ page import="ecar.dao.ItemEstruturaDao"%>
<%@ page import="ecar.dao.ItemEstruturaAcaoDao" %>
<%@ page import="ecar.dao.EstruturaFuncaoDao" %>
<%@ page import="ecar.pojo.AcompReferenciaItemAri"%>
<%@ page import="ecar.pojo.AcompRelatorioArel"%>
<%@ page import="ecar.pojo.ItemEstruturaIett" %>
<%@ page import="ecar.pojo.ItemEstrutAcaoIetta" %>
<%@ page import="ecar.pojo.EstruturaFuncaoEttf" %>
<%@ page import="ecar.pojo.UsuarioUsu"%>
<%@ page import="ecar.pojo.Aba"%>
<%@ page import="ecar.dao.AbaDao"%>

<%@ page import="ecar.util.Dominios"%>

<%@ page import="comum.util.Data"%>

<%@ page import="java.util.List"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="java.util.Iterator"%>
<%@ page import="ecar.pojo.StatusRelatorioSrl"%>


<%@ taglib uri="/WEB-INF/ecar-util.tld" prefix="util"%>
<%@ taglib uri="/WEB-INF/ecar-combo.tld" prefix="combo"%>
<%
try{
	String codArisControleV = Pagina.getParamStr(request, "codArisControleVisualizacao");
	boolean telaVisualizacao = Pagina.getParamStr(request, "tela").equals("V");

	/* carrega o usuário da session */
	UsuarioUsu usuario = ((ecar.login.SegurancaECAR)session.getAttribute("seguranca")).getUsuario();
	AcompReferenciaItemDao ariDao = new AcompReferenciaItemDao(request); 
	
	//pega os parametros do request
	String strCodAri = Pagina.getParamStr(request, "codAri");
	String codTipoAcompanhamento = Pagina.getParamStr(request, "codTipoAcompanhamento");
	String itemDoNivelClicado = Pagina.getParamStr(request, "itemDoNivelClicado");
	String mesReferencia = Pagina.getParamStr(request, "referencia_hidden");	
	String periodo = Pagina.getParamStr(request, "periodo");
	String funcaoSelecionada = "PONTOS_CRITICOS";
	AcompReferenciaItemAri ari = (AcompReferenciaItemAri) ariDao.buscar(AcompReferenciaItemAri.class, Long.valueOf(strCodAri));
	
	ValidaPermissao validaPermissao = new ValidaPermissao();
	
	ItemEstruturaDao itemEstruturaDao = new ItemEstruturaDao(request);
	ItemEstruturaIett itemEstrutura = (ItemEstruturaIett) itemEstruturaDao.buscar(ItemEstruturaIett.class, ari.getItemEstruturaIett().getCodIett());
	EstruturaFuncaoEttf estruturaFuncao = new EstruturaFuncaoEttf();
	EstruturaFuncaoDao estruturaFuncaoDao = new EstruturaFuncaoDao(request);

	EstruturaFuncaoEttf estruturaFuncaoApontamento = new EstruturaFuncaoEttf();
	estruturaFuncaoApontamento = estruturaFuncaoDao.getApontamentos(itemEstrutura.getEstruturaEtt());
	
	
	AbaDao abaD = new AbaDao(request);
	Aba aba = new Aba();
	aba.setNomeAba("PONTOS_CRITICOS");
	aba = (Aba)(abaD.pesquisar(aba, new String[]{"codAba", "asc"}).get(0));		

	//Verifica permissão para exibição do botão adicionar.
	estruturaFuncao = (EstruturaFuncaoEttf) estruturaFuncaoDao.getLabelFuncao(itemEstrutura.getEstruturaEtt(), new FuncaoDao(request).getFuncaoPontosCriticos().getCodFun());
	Boolean permissaoAlterar = new ValidaPermissao().permissaoAlterarItem(itemEstrutura, seguranca.getUsuario(), seguranca.getGruposAcesso(), estruturaFuncao);
		
	EstruturaDao estruturaDao = new EstruturaDao(request);
	List atributos = estruturaDao.getAtributosEstrutura(estruturaFuncao.getEstruturaEtt(), estruturaFuncao.getFuncaoFun());	
	
   	/*  
   	 * Verifica se o usuário é função de acompanhamento com permissão de 
   	 *  de editar os relatórios (parecer)
   	 */ 
     	 
	/*if (permissaoAlterar.booleanValue()==true  ) {
		AcompRelatorioDao arelDao = new AcompRelatorioDao(request);
		permissaoAlterar = new Boolean(  arelDao.emiteRelatorio(seguranca.getUsuario(), ari));
   	}*/ 
	
	
	
%>
<html lang="pt-br">
	<head>
		<%@ include file="/../include/meta.jsp"%>
		<%@ include file="/titulo.jsp"%>
		<link rel="stylesheet" href="<%=_pathEcar%>/css/style_<%=configuracaoCfg.getEstilo().getNome()%>.css" type="text/css">
		<script language="javascript" src="<%=_pathEcar%>/js/menu_retratil.js" type="text/javascript"></script>
		<script language="javascript" src="<%=_pathEcar%>/js/focoInicial.js" type="text/javascript"></script>
		<script language="javascript" src="<%=_pathEcar%>/js/frmPadraoRegAcompanhamento.js" type="text/javascript"></script>
		<script language="javascript" src="<%=_pathEcar%>/js/validacoes.js" type="text/javascript"></script>
		<script language="javascript" src="<%=_pathEcar%>/js/datas.js" type="text/javascript"></script>
		<script language="javascript" src="<%=_pathEcar%>/js/exibeEscondePorId.js" type="text/javascript" ></script>
		<script language="javascript" src="<%=_pathEcar%>/js/limpezaCamposMultivalorados.js"></script>
		<%@ include file="validacao.jsp"%>
		<script language="javascript" type="text/javascript">
		
<% if (telaVisualizacao) { %>

			function trocaAba(link, codAri){
				document.forms[0].codAri.value = codAri;
				document.form.codArisControleVisualizacao.value = "<%=codArisControleV%>";
				document.forms[0].action = link + "&codArisControleVisualizacao=<%=codArisControleV%>";
				document.forms[0].submit();
			}


			function trocaAbaImpressao(link){
				document.form.codArisControleVisualizacao.value = "<%=codArisControleV%>";
				document.forms[0].action = link;
				document.forms[0].submit();
			}
			
			function trocarAba(nomeAba) {
				document.form.codArisControleVisualizacao.value = "<%=codArisControleV%>";
				document.forms[0].action = nomeAba;
				document.forms[0].submit();
			}
			
			function voltarTelaAnterior(tela) {
				document.form.codArisControleVisualizacao.value = "<%=codArisControleV%>";
				document.forms[0].action = tela;
				document.forms[0].submit();
			}
			function aoClicarVoltar(form){
				document.form.codArisControleVisualizacao.value = "<%=codArisControleV%>";
				document.form.action = "pontosCriticos.jsp";
				document.form.submit();	
			}
		
			function mudaTela(cod){
				document.form.codArisControleVisualizacao.value = "<%=codArisControleV%>";
				document.form.codPtc.value = cod;
				document.form.action = "apontamentos/lista.jsp";
				document.form.submit();	
			}
					

<%} else {%>
		

	function trocarAba(nomeAba) {
		document.forms[0].action = nomeAba;
		document.forms[0].submit();
	}
	function voltarTelaAnterior(tela) {
		document.forms[0].action = tela;
		document.forms[0].submit();
	}
	function aoClicarVoltar(form){
		form.action = "pontosCriticos.jsp";
		form.submit();	
	}

	function mudaTela(cod){
		document.form.codPtc.value = cod;
		document.form.action = "apontamentos/lista.jsp";
		document.form.submit();	
	}

/*
 * Seleciona os dados da janela de pesquisa
 * E como uma interface que deve ser implementada para receber dados de uma janela de pesquisa
 * Sempre deve receber um código e uma descricao
 */
function getDadosPesquisa(codigo, descricao){
	document.form.codUsu.value = codigo;
	document.form.nomeUsu.value = descricao;
}

<util:validacaoPontoCriticoTag atributos="<%=atributos%>" sisGrupoAtributoDao="<%= new SisGrupoAtributoDao(request)%>" />

<% } %>

		</script>
	</head>
	<body onload="focoInicial(document.form);">
	<%@ include file="/../../cabecalho.jsp"%>
	<%@ include file="/../../divmenu.jsp"%>
		<div id="conteudo">
	
	
	

	<%if (telaVisualizacao){ 
			 
		AcompReferenciaItemAri acompReferenciaItem = ari;
		
		%>
			<%@ include file="../form_visualizar.jsp"%>
		<% 
		} else { 
			AcompReferenciaAref	arefSelecionado = null;
			arefSelecionado = ari.getAcompReferenciaAref();
		%>
			<%@ include file="../form_registro.jsp"%>
		<%} %>

<br>
	<%
	Boolean novoPontoCritico = Boolean.FALSE;
	PontoCriticoPtc ptc = new PontoCriticoPtc();
	String codPtc = Pagina.getParamStr(request, "cod");
	if (!"".equals(codPtc)){
		ptc = (PontoCriticoPtc) new PontoCriticoDao(request).buscar(PontoCriticoPtc.class, Long.valueOf(codPtc));
	}
	
	if (estruturaFuncaoApontamento != null){			
	%>
		<div id="linkstop">
			<b><%=estruturaFuncao.getLabelEttf()%></b>&nbsp;|&nbsp;<a href="javascript:mudaTela(<%=ptc.getCodPtc()%>)"><%=estruturaFuncaoApontamento.getLabelEttf()%></a>
		</div>
	<%} %>
		
			<form name="form" method="post" action="">
<%
_disabled = "disabled";
_readOnly = "readonly";
String _disabledCampo="";
String _readOnlyCampo="";

%>
				<input type="hidden" name="Usuario"   value="<%=usuario.getCodUsu().toString()%>">
				<input type="hidden" name="codTipoAcompanhamento" value="<%=codTipoAcompanhamento%>">
				<input type="hidden" name="mesReferencia" value="<%=ari.getAcompReferenciaAref().getCodAref().toString()%>">
				<input type="hidden" name="niveisPlanejamento" value="<%=Pagina.getParamStr(request, "niveisPlanejamento")%>">
				<input type="hidden" name="itemDoNivelClicado" value="<%=itemDoNivelClicado%>">
				<input type="hidden" name="primeiroIettClicado" value="<%=Pagina.getParamStr(request, "primeiroIettClicado")%>">
				<input type="hidden" name="primeiroAriClicado" value="<%=Pagina.getParamStr(request, "primeiroAriClicado")%>">
				<input type="hidden" name="codPtc" value="<%=Pagina.getParamStr(request, "cod")%>">
				<input type="hidden" name="tela" value="<%=Pagina.getParamStr(request, "tela")%>">	
				<input type="hidden" name="tipoPadraoExibicaoAba" value="<%= Pagina.getParamStr(request, "tipoPadraoExibicaoAba")%>">
				
				<util:barrabotoes btn1="Alterar" exibirBtn1="<%=new Boolean(!telaVisualizacao && "S".equals(itemEstrutura.getEstruturaEtt().getIndAtivoEtt()))%>" voltar="Cancelar" />
				<table name="form" class="form" width="100%">
					<%@ include file="form.jsp"%>
				</table>
				
				<util:barrabotoes btn1="Alterar" exibirBtn1="<%=new Boolean(!telaVisualizacao && "S".equals(itemEstrutura.getEstruturaEtt().getIndAtivoEtt()))%>" voltar="Cancelar" />

			</form>
			<br>
		</div>
	</body>
</html>	
<%
} catch (ECARException e){
	
	org.apache.log4j.Logger.getLogger(this.getClass()).error(e);
	Mensagem.alert(_jspx_page_context, e.getMessageKey());
} catch (Exception e){
%>
<%@ include file="/excecao.jsp"%>
<%
}
%>
<%@ include file="/include/mensagemAlert.jsp"%>
