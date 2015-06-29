
<jsp:directive.page import="ecar.pojo.AgendaAge"/>
<jsp:directive.page import="ecar.dao.AgendaDao"/>
<jsp:directive.page import="ecar.dao.ItemEstrtIndResulDao"/>
<jsp:directive.page import="ecar.pojo.ItemEstrtIndResulIettr"/><%@ include file="../../login/validaAcesso.jsp"%>
<%@ include file="../../frm_global.jsp"%>

<%@ taglib uri="/WEB-INF/ecar-util.tld" prefix="util" %>

<%@ page import="ecar.pojo.ItemEstruturaIett" %>
<%@ page import="ecar.pojo.ItemEstrtBenefIettb" %>
<%@ page import="ecar.dao.EstruturaDao" %>
<%@ page import="ecar.permissao.ValidaPermissao" %> 
<%@ page import="ecar.dao.ItemEstruturaDao" %>
<%@ page import="ecar.dao.ItemEstruturaBeneficiarioDao" %>
<%@ page import="ecar.pojo.EstruturaFuncaoEttf" %>
<%@ page import="ecar.dao.EstruturaFuncaoDao" %>

<html lang="pt-br">
<head>
<%@ include file="../../include/meta.jsp"%>
<%@ include file="/titulo.jsp"%>
<%@ include file="funcoes.jsp" %>
<link rel="stylesheet" href="<%=_pathEcar%>/css/style_<%=configuracaoCfg.getEstilo().getNome()%>.css" type="text/css">
<script language="javascript" src="../../js/focoInicial.js"></script>
<script language="javascript" src="../../js/frmPadraoItemEstrut.js"></script>
<script language="javascript" src="../../js/validacoes.js"></script>
<script language="javaScript" src="../../js/html2xhtml.js"></script>
<script language="javaScript" src="../../js/richtext.js"></script>
<script language="javascript" src="<%=_pathEcar%>/js/destaqueLinha.js"></script>
<script language="javascript" src="<%=_pathEcar%>/js/listaItensCadastro.js" type="text/javascript"></script>		 
<script language="javascript" src="<%=_pathEcar%>/js/jquery.js" type="text/javascript"></script>
<script type="text/javascript">jQuery.noConflict();</script>		
<script language="javascript" src="<%=_pathEcar%>/js/cookie.js"></script>

<script>
function aoClicarVoltar(form){
	form.action = "lista.jsp";
	form.submit();	
}
</script>
<%@ include file="validacao.jsp"%>

</head>

<%@ include file="../../cabecalho.jsp" %>
<%@ include file="../../divmenu.jsp"%> 

<body>
<div id="conteudo">

<%
try{
	ValidaPermissao validaPermissao = new ValidaPermissao();
	EstruturaDao estruturaDao = new EstruturaDao(request);
	ItemEstruturaDao itemEstruturaDao = new ItemEstruturaDao(request);
	ItemEstruturaIett itemEstrutura = (ItemEstruturaIett) itemEstruturaDao.buscar(ItemEstruturaIett.class, Long.valueOf(Pagina.getParamStr(request, "codIett")));
	ItemEstrtIndResulDao itemEstrtIndResulDao = new ItemEstrtIndResulDao(request);
	ItemEstrtIndResulIettr itemEstrtIndResul = new ItemEstrtIndResulIettr();
	
	
	if ( !validaPermissao.permissaoConsultaIETT(  Long.valueOf(Pagina.getParamStr(request, "codIett")), seguranca ) )
	{response.sendRedirect( request.getContextPath() +  "/acessoIndevido.jsp" );
	}
	
//	AgendaDao agendaDao = new AgendaDao(request); 
//	AgendaAge agenda = (AgendaAge) agendaDao.buscar(AgendaAge.class, Long.valueOf(Pagina.getParamStr(request, "codAge")));
	//ItemEstrtBenefIettb ieBenef = new ItemEstruturaBeneficiarioDao(request).buscar(Long.valueOf(Pagina.getParamStr(request, "codIett")),Long.valueOf(Pagina.getParamStr(request, "codBnf")));		
	
	String codAba = Pagina.getParamStr(request, "codAba");

	EstruturaFuncaoEttf estruturaFuncao = new EstruturaFuncaoEttf();
	EstruturaFuncaoDao estruturaFuncaoDao = new EstruturaFuncaoDao(request);
	
	estruturaFuncao = (EstruturaFuncaoEttf) estruturaFuncaoDao.getLabelFuncao(itemEstrutura.getEstruturaEtt(), Long.valueOf(codAba));
	
	Boolean permissaoAlterar = validaPermissao.permissaoAlterarItem(itemEstrutura, seguranca.getUsuario(), seguranca.getGruposAcesso(), estruturaFuncao);
%>

<div id="tituloTelaCadastro">
	<!-- TITULO -->
	<%@ include file="/titulo_tela.jsp"%> 
</div>

<form  name="form" method="post" >

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
											idLinhaCadastro="<%=Pagina.getParamStr(request,"ultimoIdLinhaDetalhado") %>"
											/>
							<br><br>
							
								<util:barrabotoes btn1="Alterar" exibirBtn1="<%=permissaoAlterar%>" voltar="Cancelar"/>
								<%
								_disabled = "disabled";
								_readOnly = "readonly";
								String tipoAgenda="F";
								String operacao = "consultar";
								%>
							
								<table name="form" class="form">
									<input type="hidden" name="hidAcao" id="hidAcao" value="">
									<input type="hidden" name="itemEstruturaIett" value="<%=itemEstrutura.getCodIett().toString() %>">
									<input type="hidden" name="codIett" value="<%=itemEstrutura.getCodIett()%>">
									<input type="hidden" name="codAba" value="<%=Pagina.getParamStr(request, "codAba")%>">
									
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
<% /* controla o estado do menu (aberto ou fechado)*/ %>
<%@ include file="../../include/estadoMenu.jsp"%>
</div>
</body>
<%@ include file="../../include/mensagemAlert.jsp" %>
</html>