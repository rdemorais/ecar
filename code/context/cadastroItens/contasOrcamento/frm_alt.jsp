<%@ include file="../../login/validaAcesso.jsp"%>
<%@ include file="../../frm_global.jsp"%>
  
<%@ page import="ecar.pojo.ItemEstruturaIett" %>
<%@ page import="ecar.dao.ItemEstruturaDao" %>
<%@ page import="ecar.permissao.ValidaPermissao" %> 
<%@ page import="ecar.pojo.EfItemEstContaEfiec" %>
<%@ page import="ecar.dao.ItemEstruturaContaOrcamentoDao" %>
<%@ page import="ecar.pojo.EstruturaFuncaoEttf" %>
<%@ page import="ecar.dao.EstruturaFuncaoDao" %>

<%@ page import="java.util.List" %> 

<%@ taglib uri="/WEB-INF/ecar-util.tld" prefix="util" %>
<%@ taglib uri="/WEB-INF/ecar-combo.tld" prefix="combo" %>


<%@page import="ecar.dao.EstruturaDao"%>
<html lang="pt-br">
<head>
<%@ include file="../../include/meta.jsp"%>
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
<script language="javascript" src="../../js/prototype.js"></script>

<script language="javascript">
<%@ include file="validacao.jsp"%>

function aoClicarVoltar(form){
	form.action = "frm_con.jsp";
	form.submit();	
}
function atualizaComboExercicio(form, disabled){
	var url = "combo_fonteRecurso.jsp?disabled=" + disabled + "&exercicioExe=" + form.exercicioExe.value + "&codIett=" + form.codIett.value;
	openAjax(url, "comboFonteRec", null, null, true, "../../images");
}
function atualizaComboFonteRecurso(form, disabled){
	var url = "combo_recurso.jsp?disabled=" + disabled + "&exercicioExe=" + form.exercicioExe.value + "&fonteRecursoFonr=" + form.fonteRecursoFonr.value + "&codIett=" + form.codIett.value;
	openAjax(url, "comboRec", null, null, true, "../../images");
}
function carregaAjax(form, disabled, codConta){
	var url = "combo_fonteRecurso.jsp?codConta=" + codConta + "&disabled=" + disabled + "&exercicioExe=" + form.exercicioExe.value + "&codIett=" + form.codIett.value;
	openAjax(url, "comboFonteRec", null, null, true, "../../images");
	var url2 = "combo_recurso.jsp?codConta=" + codConta + "&disabled=" + disabled + "&exercicioExe=" + form.exercicioExe.value + "&codIett=" + form.codIett.value;
	openAjax(url2, "comboRec", null, null, true, "../../images");
}

</script>
 
</head>

<body onload="focoInicial(document.form);">

<%@ include file="../../cabecalho.jsp" %>
<%@ include file="../../divmenu.jsp"%>

<div id="conteudo">

<%
try{
	ItemEstruturaDao itemEstruturaDao = new ItemEstruturaDao(request);
	ItemEstruturaIett itemEstrutura = (ItemEstruturaIett) itemEstruturaDao.buscar(ItemEstruturaIett.class, Long.valueOf(Pagina.getParamStr(request, "codIett")));
	
	ValidaPermissao validaPermissao = new ValidaPermissao();
	if ( !validaPermissao.permissaoConsultaIETT(  Long.valueOf(Pagina.getParamStr(request, "codIett")), seguranca ) )
	{
		response.sendRedirect( request.getContextPath() +  "/acessoIndevido.jsp" );
	}		
	
	
	String codAba = Pagina.getParamStr(request, "codAba");
%>
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

<form name="form" method="post">  


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
								
								<util:arvoreEstruturas 
									itemEstrutura="<%=itemEstrutura%>" 
									contextPath="<%=_pathEcar%>" 
									seguranca="<%=seguranca%>" 
									idLinhaCadastro="<%=Pagina.getParamStr(request,"ultimoIdLinhaDetalhado") %>" 
									ultimoIdLinhaExpandido="<%=Pagina.getParamStr(request,"ultimoIdLinhaExpandido")%>"/> 
	 
								<util:barraLinksItemEstrutura 
										estrutura="<%=itemEstrutura.getEstruturaEtt()%>" 
										selectedFuncao="<%=codAba%>" 
										codItemEstrutura="<%=itemEstrutura.getCodIett().toString()%>"
										contextPath="<%=request.getContextPath()%>"
										idLinhaCadastro="<%=Pagina.getParamStr(request,"ultimoIdLinhaDetalhado") %>"
										/>

								<br><br>
								<util:barrabotoes alterar="Gravar" voltar="Cancelar"/>
								
								<%
								EfItemEstContaEfiec conta = new EfItemEstContaEfiec();
								ItemEstruturaContaOrcamentoDao itemEstruturaContaOrcamentoDao = new ItemEstruturaContaOrcamentoDao(request);
								_disabled = "";
								_readOnly = "";
								boolean desabilitarEstrutura = false;
								conta = (EfItemEstContaEfiec) itemEstruturaContaOrcamentoDao.buscar(EfItemEstContaEfiec.class, Long.valueOf(Pagina.getParamStr(request, "cod")));
							
								%>
							 
								<table class="form">
									<%@ include file="form.jsp"%> 
								</table>
							
								<util:barrabotoes alterar="Gravar" voltar="Cancelar"/>
								
								<script language="javascript">								  
									document.form.indAcumuladoEfiec[0].disabled = false;
									document.form.indAcumuladoEfiec[1].disabled = false;		
									
									carregaAjax(form, '<%=_disabled%>', <%=conta.getCodEfiec().toString()%>);
								</script>

							
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
	Mensagem.alert(_jspx_page_context, e.getMessageKey());
} catch (Exception e){
%>
	<%@ include file="/excecao.jsp"%>
<%
}
%>

</div>

</body>

<%@ include file="../../include/mensagemAlert.jsp"%>
</html>