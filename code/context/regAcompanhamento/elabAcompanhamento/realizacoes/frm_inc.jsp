<%@ include file="/login/validaAcesso.jsp"%>
<%@ include file="/frm_global.jsp"%>

<%@ page import="ecar.dao.AcompReferenciaItemDao"%>
<%@ page import="ecar.dao.EstruturaDao"%>
<%@ page import="ecar.dao.EstruturaFuncaoDao" %>
<%@ page import="ecar.dao.ItemEstruturaDao"%>
<%@ page import="ecar.pojo.AcompReferenciaItemAri"%>
<%@ page import="ecar.pojo.ItemEstruturaIett" %>
<%@ page import="ecar.pojo.ItemEstrutAcaoIetta"%>
<%@ page import="ecar.pojo.EstruturaFuncaoEttf" %>
<%@ page import="ecar.pojo.AcompRelatorioArel"%>
<%@ page import="ecar.pojo.UsuarioUsu"%>

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
	
	AcompReferenciaItemDao acompReferenciaItemDao = new AcompReferenciaItemDao(request); 
	
	String strCodAri = Pagina.getParamStr(request, "codAri");
	
	AcompReferenciaItemAri acompReferenciaItem = (AcompReferenciaItemAri) acompReferenciaItemDao.buscar(AcompReferenciaItemAri.class, Long.valueOf(strCodAri));
	
	ValidaPermissao validaPermissao = new ValidaPermissao();
	if ( !validaPermissao.permissaoConsultaIETT( acompReferenciaItem.getItemEstruturaIett().getCodIett() , seguranca ) )
	{
		response.sendRedirect( request.getContextPath() +  "/acessoIndevido.jsp" );
	}
	ItemEstruturaDao itemEstruturaDao = new ItemEstruturaDao(request);
	ItemEstruturaIett itemEstrutura = (ItemEstruturaIett) itemEstruturaDao.buscar(ItemEstruturaIett.class, acompReferenciaItem.getItemEstruturaIett().getCodIett());
	ItemEstrutAcaoIetta ieAcao = new ItemEstrutAcaoIetta();
	EstruturaFuncaoEttf estruturaFuncao = new EstruturaFuncaoEttf();
	EstruturaFuncaoDao estruturaFuncaoDao = new EstruturaFuncaoDao(request);
	estruturaFuncao = (EstruturaFuncaoEttf) estruturaFuncaoDao.getLabelFuncao(itemEstrutura.getEstruturaEtt(), Long.valueOf(Pagina.getParamStr(request,"codAba")));
	
%>
<html lang="pt-br">
	<head>
<%@ include file="/include/meta.jsp"%>
<%@ include file="/titulo.jsp"%>
<link rel="stylesheet" href="<%=_pathEcar%>/css/style_<%=configuracaoCfg.getEstilo().getNome()%>.css" type="text/css">
<script language="javascript" src="<%=_pathEcar%>/js/menu_retratil.js" type="text/javascript"></script>
<script language="javascript" src="<%=_pathEcar%>/js/focoInicial.js" type="text/javascript"></script>
<script language="javascript" src="<%=_pathEcar%>/js/frmPadraoRegAcompanhamento.js" type="text/javascript"></script>
<script language="javascript" src="<%=_pathEcar%>/js/validacoes.js" type="text/javascript"></script>
<script language="javascript" src="<%=_pathEcar%>/js/datas.js" type="text/javascript"></script>
<script language="javascript" type="text/javascript">
<%@ include file="/cadastroItens/acao/validacao.jsp"%>
	function aoClicarVoltar(form){
		form.action = "eventos.jsp";
		form.submit();	
}
</script>
	</head>
	<body onload="focoInicial(document.form);">
		<%@ include file="/cabecalho.jsp"%>
		<%@ include file="/divmenu.jsp"%>
		<div id="conteudo">
			<!-- TÍTULO -->
			<%@ include file="/titulo_tela.jsp"%>
			<%@ include file= "../botoesAvancaRetrocede.jsp"%>
			<br>
			<util:barraLinksRegAcomp
				acompReferenciaItem="<%=acompReferenciaItem%>"
				selectedFuncao="EVENTOS" usuario="<%=usuario%>"
				primeiroAcomp="<%=Pagina.getParamStr(request, "primeiroAcomp")%>"
				request="<%=request%>"
				gruposUsuario="<%=seguranca.getGruposAcesso() %>"
			/>
			<br>
			<table width="100%" border="0" cellpadding="0" cellspacing="0">
				<tr>
					<td class="espacador">
						<img src="<%=_pathEcar%>/images/pixel.gif" alt="">
					</td>
				</tr>
				<tr class="linha_titulo" align="left">
					<td>
						Refer&ecirc;ncia:
						<%=acompReferenciaItem.getAcompReferenciaAref().getNomeAref()%>&nbsp;&nbsp;&nbsp;(<%=acompReferenciaItem.getAcompReferenciaAref().getTipoAcompanhamentoTa().getDescricaoTa()%>)
					</td>
				</tr>
				<tr class="linha_titulo" align="left">
					<td>
						M&ecirc;s/Ano:
						<%=acompReferenciaItem.getAcompReferenciaAref().getMesAref()%>/<%=acompReferenciaItem.getAcompReferenciaAref().getAnoAref()%>
					</td>
				</tr>
				<tr>
					<td class="espacador">
						<img src="<%=_pathEcar%>/images/pixel.gif" alt="">
					</td>
				</tr>
			</table>
			<util:arvoreEstruturaElabAcomp
				itemEstrutura="<%=acompReferenciaItem.getItemEstruturaIett()%>"
				codigoAcomp='<%=Pagina.getParamStr(request, "codAcomp")%>'
				contextPath="<%=_pathEcar%>"
				listaParaArvore='<%=(java.util.List)session.getAttribute("listaParaArvore")%>'
				nivelPrimeiroIettClicado='<%=(String)session.getAttribute("nivelPrimeiroIettClicado")%>'
				abaAtual="<%=ecar.taglib.util.ArvoreEstruturaElabAcompTag.ABA_DADOS_BASICOS%>"
				primeiroAcomp='<%=Pagina.getParamStr(request, "primeiroAcomp")%>' />
			<form name="form" method="post">
			<util:barrabotoes incluir="Gravar" voltar="Cancelar"/>
			<%@ include file="form.jsp"%>
			<util:barrabotoes incluir="Gravar" voltar="Cancelar"/>
			</form>
		</div>
	</body>
</html>
<% 
} catch ( ECARException e ){
	org.apache.log4j.Logger.getLogger(this.getClass()).error(e);
	Mensagem.alert(_jspx_page_context, e.getMessageKey());
} catch (Exception e){
%>
<%@ include file="/excecao.jsp"%>
<%
}
%>
<%@ include file="/include/mensagemAlert.jsp"%>
