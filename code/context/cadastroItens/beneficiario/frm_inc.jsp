<%@ include file="../../login/validaAcesso.jsp"%>
<%@ include file="../../frm_global.jsp"%>

<%@ taglib uri="/WEB-INF/ecar-util.tld" prefix="util" %>

<%@ page import="ecar.pojo.ItemEstruturaIett" %>
<%@ page import="ecar.pojo.ItemEstrtBenefIettb" %>
<%@ page import="ecar.dao.EstruturaDao" %>
<%@ page import="ecar.dao.ItemEstruturaDao" %>
<%@ page import="ecar.dao.ItemEstruturaBeneficiarioDao" %>
<%@ page import="ecar.pojo.EstruturaFuncaoEttf" %>
<%@ page import="ecar.dao.EstruturaFuncaoDao" %>

<html lang="pt-br">
<head>
<%@ include file="../../include/meta.jsp"%>
<%@ include file="/titulo.jsp"%>
<link rel="stylesheet" href="<%=_pathEcar%>/css/style_<%=configuracaoCfg.getEstilo().getNome()%>.css" type="text/css">
<script language="javascript" src="../../js/menu_retratil.js"></script>
<script language="javascript" src="../../js/focoInicial.js"></script>
<script language="javascript" src="../../js/frmPadraoItemEstrut.js"></script>
<script language="javascript" src="../../js/validacoes.js"></script>
<script language="javascript" src="../../js/popUp.js"></script>
<script language="javascript" src="../../js/numero.js"></script>
<script language="javascript" src="../../js/util.js"></script>
<script language="javascript" src="<%=_pathEcar%>/js/destaqueLinha.js"></script>
<script language="javascript" src="<%=_pathEcar%>/js/listaItensCadastro.js" type="text/javascript"></script>		 
<script language="javascript" src="<%=_pathEcar%>/js/jquery.js" type="text/javascript"></script>
<script type="text/javascript">jQuery.noConflict();</script>		
<script language="javascript" src="<%=_pathEcar%>/js/cookie.js"></script>
<!--<script language="javascript" src="../../js/mascaraMoeda.js"></script>-->

<script>
<%@ include file="validacao.jsp"%>
/*
 * Seleciona os dados da janela de pesquisa
 * E como uma interface que deve ser implementada para receber dados de uma janela de pesquisa
 * Sempre deve receber um código e uma descricao
 */
function getDadosPesquisa(codigo, descricao){
	document.form.codBnf.value = codigo;
	document.form.nomeBnf.value = descricao;
}

function reformatNumber(number) {

    // No error checking. Assumes only ever 1 DP per number
    var text = number.value;

    // Strip off anything to the right of the DP
    var rightOfDp = '';
    var dpPos = text.indexOf(',');
	text = text.replace(/\D/g,'');
	
    if (dpPos != -1) {
        rightOfDp = text.substr(dpPos);
        text = text.substr(0, dpPos);
    }

    var leftOfDp = '';
    var counter = 0;
    // Format the remainder into 3 char blocks, starting from the right
    for (var loop=text.length-1; loop>-1; loop--) {
        var char = text.charAt(loop);

        // Ignore existing spaces
        if (char == '.') continue;

        leftOfDp = char + leftOfDp;
        counter++;
        if (counter % 3 == 0) leftOfDp = '.' + leftOfDp;

    }

    // Strip leading space if present
    if (leftOfDp.charAt(0) == ' ') leftOfDp = leftOfDp.substr(1);

	var textfinal = leftOfDp + rightOfDp;   
	
	if(textfinal.length>0 && textfinal.charAt(0) == '.')
	textfinal = textfinal.substr(1, textfinal.length);
	
	if(textfinal.length>13) {
		textfinal = textfinal.substr(0, 13);
	}
	
	number.value = textfinal;	    	

}

function aoClicarVoltar(form){
	form.action = "lista.jsp";
	form.submit();	 
}
</script>

</head>

<%@ include file="../../cabecalho.jsp" %>
<%@ include file="../../divmenu.jsp"%> 

<body onload="focoInicial(document.form);">
<div id="conteudo">

<%
try{
	ItemEstruturaDao itemEstruturaDao = new ItemEstruturaDao(request);
	ItemEstruturaIett itemEstrutura = (ItemEstruturaIett) itemEstruturaDao.buscar(ItemEstruturaIett.class, Long.valueOf(Pagina.getParamStr(request, "codIett")));
	ItemEstrtBenefIettb ieBenef = new  ItemEstrtBenefIettb(); 
	
	String codAba = Pagina.getParamStr(request, "codAba");
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
								<util:arvoreEstruturas 
									itemEstrutura="<%=itemEstrutura%>" 
									contextPath="<%=_pathEcar%>" 
									seguranca="<%=seguranca%>" 
									idLinhaCadastro="<%=Pagina.getParamStr(request,"ultimoIdLinhaDetalhado") %>"
									ultimoIdLinhaExpandido="<%=Pagina.getParamStr(request,"ultimoIdLinhaExpandido") %>"/> 
 
								<util:barraLinksItemEstrutura 
												estrutura="<%=itemEstrutura.getEstruturaEtt()%>" 
												selectedFuncao="<%=codAba%>"
												codItemEstrutura="<%=itemEstrutura.getCodIett().toString()%>"
												contextPath="<%=request.getContextPath()%>"
												idLinhaCadastro="<%=Pagina.getParamStr(request,"ultimoIdLinhaDetalhado") %>"
												/>
								
								<%
									EstruturaFuncaoEttf estruturaFuncao = new EstruturaFuncaoEttf();
									EstruturaFuncaoDao estruturaFuncaoDao = new EstruturaFuncaoDao(request);
									
									estruturaFuncao = (EstruturaFuncaoEttf) estruturaFuncaoDao.getLabelFuncao(itemEstrutura.getEstruturaEtt(), Long.valueOf(codAba));
								%>
								
								<br><br>
								<util:barrabotoes incluir="Gravar" voltar="Cancelar"/>

								<table name="form" class="form">
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