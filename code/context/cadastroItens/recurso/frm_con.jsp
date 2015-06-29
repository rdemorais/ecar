<%@ include file="/login/validaAcesso.jsp"%>
<%@ include file="/frm_global.jsp"%>

<%@ taglib uri="/WEB-INF/ecar-util.tld" prefix="util" %>
<%@ taglib uri="/WEB-INF/ecar-combo.tld" prefix="combo" %>

<%@ page import="ecar.pojo.ItemEstruturaIett" %>
<%@ page import="ecar.pojo.FonteRecursoFonr" %>
<%@ page import="ecar.pojo.EfIettFonteTotEfieft" %>
<%@ page import="ecar.pojo.EstruturaFuncaoEttf" %>
<%@ page import="ecar.pojo.ExercicioExe" %>
<%@ page import="ecar.pojo.EfItemEstPrevisaoEfiep" %>
<%@ page import="ecar.dao.EstruturaDao" %>
<%@ page import="ecar.permissao.ValidaPermissao" %> 
<%@ page import="ecar.dao.ExercicioDao" %> 
<%@ page import="ecar.dao.FonteRecursoDao" %>
<%@ page import="ecar.dao.ItemEstruturaDao" %>
<%@ page import="ecar.dao.ItemEstruturaFonteRecursoDao" %>
<%@ page import="ecar.dao.EstruturaFuncaoDao" %>
<%@ page import="ecar.dao.ItemEstruturaPrevisaoDao" %>
 
<%@ page import="comum.util.Util" %> 
<%@ page import="comum.util.Data" %>

<%@ page import="java.util.List" %> 
<%@ page import="java.util.Date" %>  
<%@ page import="java.util.ArrayList" %> 
<%@ page import="java.util.Collection" %>
<%@ page import="java.util.HashSet" %>

<html lang="pt-br">
<head>

<%@ include file="/include/meta.jsp"%>
<%@ include file="/titulo.jsp"%>

<link rel="stylesheet" href="<%=_pathEcar%>/css/style_<%=configuracaoCfg.getEstilo().getNome()%>.css" type="text/css">
<script language="javascript" src="../../js/focoInicial.js"></script>
<script language="javascript" src="../../js/frmPadraoItemEstrut.js"></script>
<script language="javascript" src="../../js/numero.js"></script>
<script language="javascript" src="../../js/datas.js"></script>
<script language="javascript" src="../../js/validacoes.js"></script>
<script language="javascript" src="<%=_pathEcar%>/js/destaqueLinha.js"></script>
<script language="javascript" src="<%=_pathEcar%>/js/listaItensCadastro.js" type="text/javascript"></script>		 
<script language="javascript" src="<%=_pathEcar%>/js/jquery.js" type="text/javascript"></script>
<script type="text/javascript">jQuery.noConflict();</script>		
<script language="javascript" src="<%=_pathEcar%>/js/cookie.js"></script>
<script>
function aoClicarVoltar(form){
	form.action = 'lista.jsp';
	form.submit();	
} 
function mudaTela(codFr){
	document.form.codFonr.value = codFr;
	document.form.action = "../recurso/lista.jsp";
	document.form.submit();	
}
</script>
</head>

<%@ include file="/cabecalho.jsp" %>
<%@ include file="/divmenu.jsp"%> 

<body onload="focoInicial(document.form);">
<div id="conteudo">
<form  name="form" method="post" >
<%
try{
	EstruturaDao estruturaDao = new EstruturaDao(request);
	ItemEstruturaDao itemEstruturaDao = new ItemEstruturaDao(request);
	ItemEstruturaIett itemEstrutura = (ItemEstruturaIett) itemEstruturaDao.buscar(ItemEstruturaIett.class, Long.valueOf(Pagina.getParamStr(request, "codIett")));
	
	ValidaPermissao validaPermissao = new ValidaPermissao();
	if ( !validaPermissao.permissaoConsultaIETT(  Long.valueOf(Pagina.getParamStr(request, "codIett")), seguranca ) )
	{response.sendRedirect( request.getContextPath() +  "/acessoIndevido.jsp" );
	}	
	
	
	EfIettFonteTotEfieft ieFonte = new ItemEstruturaFonteRecursoDao(request).buscar(
												 Long.valueOf(Pagina.getParamStr(request, "codIett")), 
												 Long.valueOf(Pagina.getParamStr(request, "codFonr")));
	
	String codAba = Pagina.getParamStr(request, "codAba");
%>

<div id="tituloTelaCadastro">
	<!-- TITULO -->
	<%@ include file="/titulo_tela.jsp"%> 
</div>

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
								
								<util:barraLinksItemEstrutura 
											estrutura="<%=itemEstrutura.getEstruturaEtt()%>" 
											selectedFuncao="<%=codAba%>"
											codItemEstrutura="<%=itemEstrutura.getCodIett().toString()%>"
											contextPath="<%=request.getContextPath()%>"
											idLinhaCadastro="<%=Pagina.getParamStr(request,"ultimoIdLinhaDetalhado") %>"/>
								
								<%
									EstruturaFuncaoEttf estruturaFuncao = new EstruturaFuncaoEttf();
									EstruturaFuncaoDao estruturaFuncaoDao = new EstruturaFuncaoDao(request);
									
									estruturaFuncao = (EstruturaFuncaoEttf) estruturaFuncaoDao.getLabelFuncao(itemEstrutura.getEstruturaEtt(), Long.valueOf(codAba));
									
									Boolean permissaoAlterar = validaPermissao.permissaoAlterarItem(itemEstrutura, seguranca.getUsuario(), seguranca.getGruposAcesso(), estruturaFuncao);
								%>
								
								<br><br>
								
									<util:barrabotoes btn1="Alterar" exibirBtn1="<%=permissaoAlterar%>" voltar="Cancelar"/>
									<%
									_disabled = "disabled";
									_readOnly = "readonly";
									%>
								
									<table name="form" class="form">
									<%@ include file="form.jsp"%>
									</table>
									
									<util:barrabotoes btn1="Alterar" exibirBtn1="<%=permissaoAlterar%>" voltar="Cancelar"/>
					
						
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
} catch (Exception e){
%>
	<%@ include file="/excecao.jsp"%>
<%
}
%>

<br>
</div>
</body>
<%@ include file="/include/mensagemAlert.jsp" %>
</html>