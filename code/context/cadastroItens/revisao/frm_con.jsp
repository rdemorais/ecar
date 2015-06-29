<%@ include file="../../login/validaAcesso.jsp"%>
<%@ include file="../../frm_global.jsp"%>

<%@ taglib uri="/WEB-INF/ecar-util.tld" prefix="util" %>
<%@ taglib uri="/WEB-INF/ecar-combo.tld" prefix="combo" %>

<%@ page import="ecar.pojo.ItemEstruturaIett" %>
<%@ page import="ecar.pojo.ItemEstrutAcaoIetta" %>
<%@ page import="ecar.pojo.EstruturaFuncaoEttf" %>
<%@ page import="ecar.pojo.EstruturaEtt" %>
<%@ page import="ecar.dao.EstruturaFuncaoDao" %>
<%@ page import="ecar.dao.EstruturaDao" %>
<%@ page import="ecar.pojo.ObjetoEstrutura" %>
<%@ page import="ecar.pojo.SisAtributoSatb" %>
<%@ page import="ecar.dao.ItemEstruturaDao" %>
<%@ page import="ecar.dao.ItemEstruturarevisaoIettrevDAO" %>
<%@ page import="ecar.pojo.ItemEstruturarevisaoIettrev" %>
<%@ page import="ecar.dao.ConfiguracaoDao" %>
<%@ page import="ecar.dao.SisGrupoAtributoDao" %>
<%@ page import="ecar.pojo.IettIndResulRevIettrr" %>
<%@ page import="ecar.pojo.ItemEstFisicoRevIettfr" %>
<%@ page import="ecar.dao.FuncaoDao" %>
<%@ page import="ecar.pojo.FuncaoFun" %>
<%@ page import="ecar.pojo.EstruturaFuncaoEttfPK" %>
<%@ page import="ecar.dao.ItemEstLocalRevIettlrDAO" %>
<%@ page import="ecar.pojo.ItemEstLocalRevIettlr" %>
<%@ page import="ecar.permissao.ValidaPermissao" %> 

<%@ page import="java.util.List" %> 
<%@ page import="java.util.Set" %> 
<%@ page import="java.util.Iterator" %>
<%@ page import="ecar.dao.PeriodoRevisaoPrevDao" %>
<html lang="pt-br">
<head>
<%@ include file="../../include/meta.jsp"%>
<%@ include file="/titulo.jsp"%>
<link rel="stylesheet" href="<%=_pathEcar%>/css/style_<%=configuracaoCfg.getEstilo().getNome()%>.css" type="text/css">
<script language="javascript" src="../../js/focoInicial.js"></script>
<script language="javascript" src="../../js/frmPadraoItemEstrut.js"></script>
<script language="javascript" src="../../js/validacoes.js"></script>
<script language="javascript" src="../../js/datas.js"></script>
<script language="javascript" src="<%=_pathEcar%>/js/destaqueLinha.js"></script>
<script language="javascript" src="<%=_pathEcar%>/js/listaItensCadastro.js" type="text/javascript"></script>		 
<script language="javascript" src="<%=_pathEcar%>/js/jquery.js" type="text/javascript"></script>
<script type="text/javascript">jQuery.noConflict();</script>		
<script language="javascript" src="<%=_pathEcar%>/js/cookie.js"></script>

<script>
<%@ include file="validacao.jsp"%>

function aoClicarVoltar(form){
	form.action = "lista.jsp";
	form.submit();	
}

function aoClicarBtn2(form){
	if(validar(form)){
		form.btn2[0].disabled = true;
		form.btn2[1].disabled = true;
		form.hidAcao.value = "alterar";
		form.action = "frm_inc.jsp";
		form.submit();
	}
}	


</script>
</head>

<%@ include file="../../cabecalho.jsp" %>
<%@ include file="../../divmenu.jsp"%> 

<body onload="focoInicial(document.form);">
<div id="conteudo">

<%
/* item pai do item que está sendo cadastrado */  
//long codIettPrincipal = Pagina.getParamLong(request, "codIettPrincipal");


try{
	ItemEstruturaDao itemEstruturaDao = new ItemEstruturaDao(request);
//	ItemEstruturarevisaoIettrevDAO itemEstrevDao = new ItemEstruturarevisaoIettrevDAO(request);
	ItemEstruturarevisaoIettrevDAO ieRevisaoDao = new ItemEstruturarevisaoIettrevDAO(request);
	ItemEstruturaIett itemEstrutura = (ItemEstruturaIett) itemEstruturaDao.buscar(ItemEstruturaIett.class, Long.valueOf(Pagina.getParamStr(request, "codIett")));
	
	ValidaPermissao validaPermissao = new ValidaPermissao();
	if ( !validaPermissao.permissaoConsultaIETT(  Long.valueOf(Pagina.getParamStr(request, "codIett")), seguranca ) )
	{response.sendRedirect( request.getContextPath() +  "/acessoIndevido.jsp" );
	}	
	
	
	ItemEstruturarevisaoIettrev ieRevisao = (ItemEstruturarevisaoIettrev) ieRevisaoDao.buscar(ItemEstruturarevisaoIettrev.class, Integer.valueOf(Pagina.getParamStr(request, "codIettrev")));	
	EstruturaDao estruturaDao = new EstruturaDao(request);	
	
	_disabled = " disabled ";
	String codAba = Pagina.getParamStr(request, "codAba");
	
	/* seta o pai desse item para ser capaz de fazer a arvore de estruturas */
//	if (codIettPrincipal > 0)
//		itemEstrutura.setItemEstruturaIett((ItemEstruturaIett) itemEstruturaDao.buscar(ItemEstruturaIett.class, Long.valueOf(codIettPrincipal)));

	EstruturaEtt estrutura = itemEstrutura.getEstruturaEtt();

	Set niveisPlanejamentoSelected = null;
	List atributos = estruturaDao.getAtributosEstruturaRevisao(estrutura);

//	itemEstrutura.setEstruturaEtt(estrutura);

/* no form.jsp vai indicar para a taglib que os campos não devem ser desabilitados */
	boolean desabilitar = true;
	
%>
<div id="tituloTelaCadastro">
	<!-- TITULO -->
	<%@ include file="/titulo_tela.jsp"%> 
</div>		
				
<%
	EstruturaFuncaoEttf estruturaFuncao = new EstruturaFuncaoEttf();
	EstruturaFuncaoDao estruturaFuncaoDao = new EstruturaFuncaoDao(request);
	boolean haPeriodo = (new PeriodoRevisaoPrevDao(request)).estaNoPeriodoAtual(ieRevisao.getDataInclusaoIettrev());
	estruturaFuncao = (EstruturaFuncaoEttf) estruturaFuncaoDao.getLabelFuncao(itemEstrutura.getEstruturaEtt(), Long.valueOf(codAba));
	Boolean permissao = new Boolean((ieRevisao.getUsuarioUsuByCodUsuIncIettrev().equals(seguranca.getUsuario())) && (haPeriodo));
	boolean ehTelaListagem = false;
	 
	EstruturaEtt estruturaEttSelecionada = null;
	EstruturaDao estruturaDaoArvoreItens = new EstruturaDao(request);


	//ConfiguracaoCfg configuracaoCfg = new ecar.dao.ConfiguracaoDao(request).getConfiguracao();

	if(configuracaoCfg.getIndExibirArvoreNavegacaoCfg() != null && configuracaoCfg.getIndExibirArvoreNavegacaoCfg().equals("S")){
	%>

		<script language="javascript" src="../../js/menu_retratil_cadastro.js"></script>
		<script language="javascript" src="../../js/menu_cadastro.js"></script>	

	<%
	}else{
	%>
		<script language="javascript" src="../../js/menu_retratil.js"></script>
	<%
	}
	%>

<form  name="form" method="post" >
<%@ include file="../arvoreItens.jsp"%>

<table width="100%" cellspacing="0" cellpadding="0" border="0">
	<tr>
		<td>
			<%
			//Se usar a árvore o id do div precisa ser "conteudoCadastroEstrutura" para que seja utilizado o estilo da árvore
			if(configuracaoCfg.getIndExibirArvoreNavegacaoCfg() != null && configuracaoCfg.getIndExibirArvoreNavegacaoCfg().equals("S")){ %>
			<div id="conteudoCadastroEstrutura">
			<%
			}else{
			%>
			<div>
			<%
			}
			%>
				<table width="100%" border="0" cellpadding="0" cellspacing="0">
					<tr>
					<%
					//Utilizado apenas quando a árvore está configurada para aparecer
					if (configuracaoCfg.getIndExibirArvoreNavegacaoCfg() != null && configuracaoCfg.getIndExibirArvoreNavegacaoCfg().equals("S")) {
					%>
					<!-- Barra amarela -->
		    			<td class="menuHideShowCadastro">
		    			<!-- Botão na barra -->
					<div id="btmenuCadastro"></div>
					</td>
					<script language="javascript">			
						//Inicia com o menu cadastro aberto
						botaoCadastro("aberto");
						mudaEstadoCadastro("aberto");			
					</script>
					<%} %>
						<td width="100%" valign="top">
							<util:arvoreEstruturas itemEstrutura="<%=itemEstrutura%>"
							contextPath="<%=_pathEcar%>" 
							seguranca="<%=seguranca%>"
							idLinhaCadastro="<%=Pagina.getParamStr(request,"ultimoIdLinhaDetalhado") %>"
							ultimoIdLinhaExpandido="<%=Pagina.getParamStr(request,"ultimoIdLinhaExpandido")%>"/>
 
							<util:barraLinksItemEstrutura 
							estrutura="<%=itemEstrutura.getEstruturaEtt()%>" 
							selectedFuncao="<%=codAba%>" 
							desabilitarLinks="false"
							codItemEstrutura="<%=itemEstrutura.getCodIett().toString()%>"
							contextPath="<%=request.getContextPath()%>"
							idLinhaCadastro="<%=Pagina.getParamStr(request,"ultimoIdLinhaDetalhado") %>"
							/>
							<br><br>
							<input type="hidden" name="hidAcao" value="consultar">
							  <util:barrabotoes btn2="Alterar" exibirBtn2="<%=permissao%>" voltar="Voltar"/>
							
								<table name="form" class="form">
									<%@ include file="form.jsp"%>
								</table>
									<%@include file="metaFisica.jsp"%>
									<%@include file="localizacao.jsp"%>		
									<%@include file="fonteRecurso.jsp"%>
							  <util:barrabotoes btn2="Alterar" exibirBtn2="<%=permissao%>" voltar="Voltar"/>
						</td>
					</tr>
				</table>			
			</div>
			
		</td>
	</tr>
	
</table>
</form>
<%
} catch (ECARException e){
	org.apache.log4j.Logger.getLogger(this.getClass()).error(e);
	Mensagem.alert(_jspx_page_context, e.getMessageKey());
	%>
	<script>
	document.form.action = "lista.jsp";
	document.submit();
	</script>
	<%
} catch (Exception e){
%>
	<%@ include file="/excecao.jsp"%>
<%
}
%>

<br>
</div>
</body>
<%@ include file="../../include/mensagemAlert.jsp" %>
</html>