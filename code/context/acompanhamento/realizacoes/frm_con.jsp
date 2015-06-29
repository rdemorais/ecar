
<jsp:directive.page import="ecar.pojo.AcompReferenciaAref"/><%@ include file="/login/validaAcesso.jsp"%>
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
	/* carrega o usuário da session */
	UsuarioUsu usuario = ((ecar.login.SegurancaECAR)session.getAttribute("seguranca")).getUsuario();
	
	AcompReferenciaItemDao ariDao = new AcompReferenciaItemDao(request); 
	
	//pega os parametros do request
	String strCodAri = Pagina.getParamStr(request, "codAri");
	String codTipoAcompanhamento = Pagina.getParamStr(request, "codTipoAcompanhamento");
	String itemDoNivelClicado = Pagina.getParamStr(request, "itemDoNivelClicado");
	String mesReferencia = Pagina.getParamStr(request, "mesReferencia");
	
	
	AcompReferenciaItemAri ari = (AcompReferenciaItemAri) ariDao.buscar(AcompReferenciaItemAri.class, Long.valueOf(strCodAri));
	
	ValidaPermissao validaPermissao = new ValidaPermissao();
	
	
	//Pega a aba
	AbaDao abaDao = new AbaDao(request);
	Aba aba = new Aba();
	aba.setNomeAba("EVENTOS");
	Aba aba_teste = (Aba)(abaDao.pesquisar(aba, new String[]{"codAba", "asc"}).get(0));		
	
	
	ItemEstruturaDao itemEstruturaDao = new ItemEstruturaDao(request);
	ItemEstruturaIett itemEstrutura = (ItemEstruturaIett) itemEstruturaDao.buscar(ItemEstruturaIett.class, ari.getItemEstruturaIett().getCodIett());
	EstruturaFuncaoEttf estruturaFuncao = new EstruturaFuncaoEttf();
	EstruturaFuncaoDao estruturaFuncaoDao = new EstruturaFuncaoDao(request);
	estruturaFuncao = (EstruturaFuncaoEttf) estruturaFuncaoDao.getLabelFuncao(itemEstrutura.getEstruturaEtt(), Long.valueOf(aba_teste.getCodAba().toString()));
	Boolean permissaoAlterar = validaPermissao.permissaoAlterarItem(itemEstrutura, seguranca.getUsuario(), seguranca.getGruposAcesso(), estruturaFuncao);
	String formaVisualizacaoControle =  Pagina.getParamStr(request, "hidFormaVisualizacaoEscolhida");
	ItemEstrutAcaoIetta ieAcao = (ItemEstrutAcaoIetta) new ItemEstruturaAcaoDao(request).buscar(ItemEstrutAcaoIetta.class, Long.valueOf(Pagina.getParamStr(request, "codIetta")));		
	
	
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
		<script language="javascript" type="text/javascript">
			function trocarAba(nomeAba) {
				document.forms[0].action = nomeAba;
				document.forms[0].clicouAba.value = "S";
				document.forms[0].submit();
			}
			function voltarTelaAnterior(tela) {
				document.forms[0].action = tela;
				document.forms[0].submit();
			}
			function aoClicarVoltar(form){
				form.action = "eventos.jsp?hidFormaVisualizacaoEscolhida=<%=formaVisualizacaoControle%>&codTipoAcompanhamento=<%=codTipoAcompanhamento%>";
				form.submit();	
			}
			
			function aoClicarBtn1(form) {
				form.action = "frm_alt.jsp?hidFormaVisualizacaoEscolhida=<%=formaVisualizacaoControle%>&codTipoAcompanhamento=<%=codTipoAcompanhamento%>";
				form.submit();	
			
			}
		</script>

	</head>
	<body onload="focoInicial(document.form);">
	<%@ include file="/../../cabecalho.jsp"%>
	<%@ include file="/../../divmenu.jsp"%>
	
	<div id="conteudo">
	<!-- o cabecalho da tela -->
	<%
	String periodo = Pagina.getParamStr(request, "periodo");
	AcompReferenciaAref	arefSelecionado = null;
	arefSelecionado = ari.getAcompReferenciaAref();
	String funcaoSelecionada = "EVENTOS";
	%>
	<%@ include  file="../form_registro.jsp" %>		
	
		
	<form name="form" method="post" action="">
<%
_disabled = "disabled";
_readOnly = "readonly";
%>
				<input type="hidden" name="Usuario"   value="<%=usuario.getCodUsu().toString()%>">
				<input type="hidden" name="codTipoAcompanhamento" value="<%=codTipoAcompanhamento%>">
				<input type="hidden" name="mesReferencia" value="<%=ari.getAcompReferenciaAref().getCodAref().toString()%>">
				<input type="hidden" name="niveisPlanejamento" value="<%=Pagina.getParamStr(request, "niveisPlanejamento")%>">
				<input type="hidden" name="itemDoNivelClicado" value="<%=itemDoNivelClicado%>">
				<input type="hidden" name="codAri" value="<%=ari.getCodAri().toString()%>">
				<input type="hidden" name="primeiroIettClicado" value="<%=Pagina.getParamStr(request, "primeiroIettClicado")%>">
				<input type="hidden" name="primeiroAriClicado" value="<%=Pagina.getParamStr(request, "primeiroAriClicado")%>">
				<!-- Campo necessário para botões de Avança/Retrocede -->
				<input type="hidden" name="codArisControle" value="<%=Pagina.getParamStr(request, "codArisControle")%>">
				<input type="hidden" name="clicouAba" value="<%= Pagina.getParamStr(request, "clicouAba")%>">
				<input type="hidden" name="tipoPadraoExibicaoAba" value="<%= Pagina.getParamStr(request, "tipoPadraoExibicaoAba")%>">
	
	
				<%
				
					if("S".equals(itemEstrutura.getEstruturaEtt().getIndAtivoEtt())){ 
				
				%>
						
					<util:barrabotoes btn1="Alterar" exibirBtn1="<%=permissaoAlterar%>" voltar="Cancelar" />
			
				<%
				
					} else {
				
				%>
				
					<util:barrabotoes voltar="Cancelar" />
				
				<%
				
					}
				
				%>
				
				<%@ include file="form.jsp"%>
				
				<%
				
					if("S".equals(itemEstrutura.getEstruturaEtt().getIndAtivoEtt())){ 
				
				%>
						
					<util:barrabotoes btn1="Alterar" exibirBtn1="<%=permissaoAlterar%>" voltar="Cancelar" />
			
				<%
				
					} else {
				
				%>
				
					<util:barrabotoes voltar="Cancelar" />
				
				<%
				
					}
				
				%>
				
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
