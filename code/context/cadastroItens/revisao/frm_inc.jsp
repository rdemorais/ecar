<%@ include file="../../login/validaAcesso.jsp"%>
<%@ include file="../../frm_global.jsp"%>

<%@ taglib uri="/WEB-INF/ecar-util.tld" prefix="util" %>
<%@ taglib uri="/WEB-INF/ecar-combo.tld" prefix="combo" %>

<%@ page import="ecar.pojo.ItemEstruturaIett" %>
<%@ page import="ecar.pojo.ItemEstrutAcaoIetta" %>
<%@ page import="ecar.pojo.EstruturaFuncaoEttf" %>
<%@ page import="ecar.pojo.EstruturaFuncaoEttfPK" %>
<%@ page import="ecar.pojo.IettIndResulRevIettrr" %>
<%@ page import="ecar.pojo.ItemEstFisicoRevIettfr" %>
<%@ page import="ecar.pojo.EstruturaEtt" %>
<%@ page import="ecar.permissao.ValidaPermissao" %> 
<%@ page import="ecar.dao.EstruturaFuncaoDao" %>
<%@ page import="ecar.dao.EstruturaDao" %>
<%@ page import="ecar.pojo.ObjetoEstrutura" %>
<%@ page import="ecar.dao.FuncaoDao" %>
<%@ page import="ecar.pojo.FuncaoFun" %>
<%@ page import="ecar.pojo.SisAtributoSatb" %>
<%@ page import="ecar.dao.ItemEstruturaDao" %>
<%@ page import="ecar.dao.ItemEstruturarevisaoIettrevDAO" %>
<%@ page import="ecar.pojo.ItemEstruturarevisaoIettrev" %>
<%@ page import="ecar.dao.ConfiguracaoDao" %>
<%@ page import="ecar.dao.SisGrupoAtributoDao" %>
<%@ page import="ecar.dao.ItemEstLocalRevIettlrDAO" %>
<%@ page import="ecar.pojo.ItemEstLocalRevIettlr" %>

<%@ page import="java.util.List" %> 
<%@ page import="java.util.Set" %> 
<%@ page import="java.util.Iterator" %>
<html lang="pt-br">
<head>
<%@ include file="../../include/meta.jsp"%>
<%@ include file="/titulo.jsp"%>
<link rel="stylesheet" href="<%=_pathEcar%>/css/style_<%=configuracaoCfg.getEstilo().getNome()%>.css" type="text/css">
<script language="javascript" src="../../js/focoInicial.js"></script>
<script language="javascript" src="../../js/frmPadraoItemEstrut.js"></script>
<script language="javascript" src="../../js/validacoes.js"></script>
<script language="javascript" src="../../js/datas.js"></script>
<script language="javascript" src="../../js/popup.js"></script>
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
		if(form.hidGravado.value != "S")
		{
			form.hidAcao.value = "primeira";
			form.action = "ctrl.jsp"
			form.submit();
		}
		else
		{
			form.hidAcao.value = "alterar";
			form.action = "ctrl.jsp"
			form.submit();
		}
	}
}	

</script>
</head>

<%@ include file="../../cabecalho.jsp" %>
<%@ include file="../../divmenu.jsp"%> 

<body onload="focoInicial(document.form);">
<div id="conteudo">

<%
/* item pai do item que est� sendo cadastrado */  
//long codIettPrincipal = Pagina.getParamLong(request, "codIettPrincipal");


try{
	ValidaPermissao validaPermissao = new ValidaPermissao();
	ItemEstruturaDao itemEstruturaDao = new ItemEstruturaDao(request);
	ItemEstruturaIett itemEstrutura = (ItemEstruturaIett) itemEstruturaDao.buscar(ItemEstruturaIett.class, Long.valueOf(Pagina.getParamStr(request, "codIett")));
	ItemEstruturarevisaoIettrev ieRevisao = new ItemEstruturarevisaoIettrev();	
	ItemEstruturarevisaoIettrevDAO ieRevisaoDao = new ItemEstruturarevisaoIettrevDAO(request);
	EstruturaDao estruturaDao = new EstruturaDao(request);	
	
	String codAba = Pagina.getParamStr(request, "codAba");
	
	/* seta o pai desse item para ser capaz de fazer a arvore de estruturas */
//	if (codIettPrincipal > 0)
//		itemEstrutura.setItemEstruturaIett((ItemEstruturaIett) itemEstruturaDao.buscar(ItemEstruturaIett.class, Long.valueOf(codIettPrincipal)));

	EstruturaEtt estrutura = itemEstrutura.getEstruturaEtt();

	Set niveisPlanejamentoSelected = null;
	List atributos = estruturaDao.getAtributosEstruturaRevisao(estrutura);


/* no form.jsp vai indicar para a taglib que os campos n�o devem ser desabilitados */
	boolean desabilitar = false;
	
%>
<script>
<util:validacaoItemEstruturaRevisao atributos="<%=atributos%>"/>
</script>

<div id="tituloTelaCadastro">
	<!-- TITULO -->
	<%@ include file="/titulo_tela.jsp"%> 
</div>
<%
	EstruturaFuncaoEttf estruturaFuncao = new EstruturaFuncaoEttf();
	EstruturaFuncaoDao estruturaFuncaoDao = new EstruturaFuncaoDao(request);
	
	estruturaFuncao = (EstruturaFuncaoEttf) estruturaFuncaoDao.getLabelFuncao(itemEstrutura.getEstruturaEtt(), Long.valueOf(codAba));
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
			//Se usar a �rvore o id do div precisa ser "conteudoCadastroEstrutura" para que seja utilizado o estilo da �rvore
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
					//Utilizado apenas quando a �rvore est� configurada para aparecer
					if (configuracaoCfg.getIndExibirArvoreNavegacaoCfg() != null && configuracaoCfg.getIndExibirArvoreNavegacaoCfg().equals("S")) {
					%>
					<!-- Barra amarela -->
		    			<td class="menuHideShowCadastro">
		    			<!-- Bot�o na barra -->
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
							estrutura="<%=estrutura%>" 
							selectedFuncao="<%=codAba%>" 
							desabilitarLinks="false"
							codItemEstrutura="<%=itemEstrutura.getCodIett().toString()%>"
							contextPath="<%=request.getContextPath()%>"
							idLinhaCadastro="<%=Pagina.getParamStr(request,"ultimoIdLinhaDetalhado") %>"
							/>
							<br><br>
							<%if (("incluir".equals(Pagina.getParamStr(request, "hidAcao"))) || ("".equals(Pagina.getParamStr(request, "hidAcao")))){%>
								<input type="hidden" name="hidAcao" value="incluir">
								<%if ("S".equals(Pagina.getParamStr(request, "inseriuMeta"))) {%>
									<input type="hidden" name="hidGravado" value="S">
								<%}else{%>
									<input type="hidden" name="hidGravado" value="N">
							<%}
							}else{%>
								<input type="hidden" name="hidAcao" value="<%=Pagina.getParamStr(request, "hidAcao")%>">
								<input type="hidden" name="hidGravado" value="S">
							<%}%>
							  <util:barrabotoes incluir="Gravar e Voltar" btn2="Gravar" voltar="Cancelar"/>
								<table name="form" class="form">
								<%@ include file="form.jsp"%>
								</table>
								<%@include file="metaFisica.jsp"%>
								<%@include file="localizacao.jsp"%>
								<%@include file="fonteRecurso.jsp"%>
							
							  <util:barrabotoes incluir="Gravar e Voltar" btn2="Gravar" voltar="Cancelar"/>

						
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
	form.submit();
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