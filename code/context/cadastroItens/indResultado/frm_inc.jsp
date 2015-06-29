<%@ include file="../../login/validaAcesso.jsp"%>
<%@ include file="../../frm_global.jsp"%>

<%@ page import="ecar.pojo.ItemEstruturaIett" %>
<%@ page import="ecar.dao.ItemEstruturaDao" %>
<%@ page import="ecar.pojo.ItemEstrtIndResulIettr" %>
<%@ page import="ecar.dao.ItemEstrtIndResulDao" %>
<%@ page import="ecar.pojo.EstruturaFuncaoEttf" %>
<%@ page import="ecar.dao.EstruturaFuncaoDao" %>
<%@ page import="ecar.pojo.ExercicioExe" %>
<%@ page import="ecar.dao.ExercicioDao" %>
<%@ page import="ecar.pojo.ItemEstrutFisicoIettf" %>
<%@ page import="ecar.dao.ItemEstrutFisicoDao" %>
<%@ page import="ecar.permissao.ValidaPermissao" %> 
<%@ page import="java.util.List" %>

<%@ taglib uri="/WEB-INF/ecar-util.tld" prefix="util" %>
<%@ taglib uri="/WEB-INF/ecar-combo.tld" prefix="combo" %>
<%@ taglib uri="/WEB-INF/ecar-input.tld" prefix="ecar" %>

<%@ include file="../../include/exibirAguarde.jsp"%>
<html lang="pt-br">
<head>
<%@ include file="../../include/meta.jsp"%>
<%@ include file="/titulo.jsp"%>
<link rel="stylesheet" href="<%=_pathEcar%>/css/style_<%=configuracaoCfg.getEstilo().getNome()%>.css" type="text/css">

<script language="javascript" src="../../js/focoInicial.js"></script>
<script language="javascript" src="../../js/frmPadraoItemEstrut.js"></script>
<script language="javascript" src="../../js/validacoes.js"></script>
<script language="javascript" src="../../js/datas.js"></script>
<script language="javascript" src="../../js/numero.js"></script>
<script language="javascript" src="../../js/validacoes.js"></script>
<script type="text/javascript" src="../../js/limpezaCamposMultivalorados.js"></script>

<script language="javascript" src="<%=_pathEcar%>/js/destaqueLinha.js"></script>
<script language="javascript" src="<%=_pathEcar%>/js/listaItensCadastro.js" type="text/javascript"></script>		 
<script language="javascript" src="<%=_pathEcar%>/js/jquery.js" type="text/javascript"></script>
<script type="text/javascript">jQuery.noConflict();</script>		
<script language="javascript" src="<%=_pathEcar%>/js/cookie.js"></script>

<script language="javascript">
<%@ include file="validacao.jsp"%>

function aoClicarVoltar(form){
	form.action = "lista.jsp";
	form.submit();	
}
</script>



</head>

<body onload="focoInicial(document.form);">

<%@ include file="../../cabecalho.jsp" %>
<%@ include file="../../divmenu.jsp"%>

<div id="conteudo">

<%
String tipoAcao = "incluir";
try{
	ItemEstruturaDao itemEstruturaDao = new ItemEstruturaDao(request);
	ItemEstruturaIett itemEstrutura = (ItemEstruturaIett) itemEstruturaDao.buscar(ItemEstruturaIett.class, Long.valueOf(Pagina.getParamStr(request, "codIett")));
	
	ValidaPermissao validaPermissao = new ValidaPermissao();
	if ( !validaPermissao.permissaoConsultaIETT(  Long.valueOf(Pagina.getParamStr(request, "codIett")), seguranca ) )
	{response.sendRedirect( request.getContextPath() +  "/acessoIndevido.jsp" );
	}	
	
	String codAba = Pagina.getParamStr(request, "codAba");
%>

<!-- TITULO -->
<div id="tituloTelaCadastro">
	<!-- TITULO -->
	<%@ include file="/titulo_tela.jsp"%> 
</div>

<form name="form" method="post">

<%
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
							
							<util:arvoreEstruturas itemEstrutura="<%=itemEstrutura%>" contextPath="<%=_pathEcar%>" seguranca="<%=seguranca%>" idLinhaCadastro="<%=Pagina.getParamStr(request,"ultimoIdLinhaDetalhado") %>" ultimoIdLinhaExpandido="<%=Pagina.getParamStr(request,"ultimoIdLinhaExpandido")%>"/>

							<util:barraLinksItemEstrutura estrutura="<%=itemEstrutura.getEstruturaEtt()%>" selectedFuncao="<%=codAba%>" codItemEstrutura="<%=itemEstrutura.getCodIett().toString()%>" contextPath="<%=request.getContextPath()%>" idLinhaCadastro="<%=Pagina.getParamStr(request,"ultimoIdLinhaDetalhado") %>"/>
						
							<%
								EstruturaFuncaoEttf estruturaFuncao = new EstruturaFuncaoEttf();
								EstruturaFuncaoDao estruturaFuncaoDao = new EstruturaFuncaoDao(request);
								
								estruturaFuncao = (EstruturaFuncaoEttf) estruturaFuncaoDao.getLabelFuncao(itemEstrutura.getEstruturaEtt(), Long.valueOf(codAba));
							%>
							
							<br><br>
							
								<util:barrabotoes incluir="Gravar" voltar="Cancelar"/>
							
							<%
								ItemEstrtIndResulIettr itemEstrtIndResul = new ItemEstrtIndResulIettr();
								ItemEstrtIndResulCorIettrcor itemEstrtIndResulCorIettrcor = new ItemEstrtIndResulCorIettrcor();
								
								//Verifica se é o primeiro acesso a página ou se ela está sendo recarregada
								if("S".equals(Pagina.getParamStr(request, "existeNomeGraficoGrupo"))){
									itemEstrtIndResul = (ItemEstrtIndResulIettr) session.getAttribute("indicadorResultado");
									//itemEstrtIndResulCorIettrcor = (ItemEstrtIndResulCorIettrcor) session.getAttribute("indicadorResultadoCor");
								}
								_disabled = "";
								String _disabledQtdePorLocal = "";
								String _msgQtdePorLocal = "";
								
								String _disabledPrevQtdePorLocal = "";
								String _msgPrevQtdePorLocal = "";
								
								/*
								 * Verifica se possui localização geográfica e desabilita os campos, se for o caso
								 */
								 
								 if ((itemEstrutura.getItemEstrutLocalIettls() == null)||(itemEstrutura.getItemEstrutLocalIettls().size() == 0)){
									 _disabledQtdePorLocal = "disabled";
									 _disabledPrevQtdePorLocal = "disabled";
									 itemEstrtIndResul.setIndPrevPorLocal("N");
									 itemEstrtIndResul.setIndRealPorLocal("N");									 
								 }
								
							%>
							
								<input type="hidden" name="existeAcompRealFisico" id="existeAcompRealFisico" value="">
								<input type="hidden" name="existePrevistos" id="existePrevistos" value="">
                                <input type="hidden" name="nivelAbrangencia" id="nivelAbrangencia" value="">								
                                <input type="hidden" name="previstoPorLocal" id="previstoPorLocal" value="">
								<input type="hidden" name="ind_sinalizacao"   id="ind_sinalizacao"   value="S"/>
		
								<table class="form">
									<%@ include file="form.jsp"%>
								</table>
							
								<util:barrabotoes incluir="Gravar" voltar="Cancelar"/>
						
						</td>
					</tr>
				</table>			
			</div>
			
		</td>
	</tr>
	
</table>
	
</form>

<script language="JavaScript">
if( form.indAcumulavelIettr[1].checked == true) {
	document.getElementById('linha').style.display = "";
}
</script>

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

</div>

</body>
<%@ include file="../../include/ocultarAguarde.jsp"%>
<%@ include file="../../include/mensagemAlert.jsp"%>
</html>