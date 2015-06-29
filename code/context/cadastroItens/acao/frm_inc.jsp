
<jsp:directive.page import="ecar.util.Dominios"/><%@ include file="../../login/validaAcesso.jsp"%>
<%@ include file="../../frm_global.jsp"%>

<%@ taglib uri="/WEB-INF/ecar-util.tld" prefix="util" %>

<%@ page import="ecar.pojo.ItemEstruturaIett" %>
<%@ page import="ecar.pojo.ItemEstrutAcaoIetta" %>
<%@ page import="ecar.pojo.EstruturaFuncaoEttf" %>
<%@ page import="ecar.dao.EstruturaFuncaoDao" %>
<%@ page import="ecar.dao.EstruturaDao" %>
<%@ page import="ecar.dao.ItemEstruturaDao" %>
<%@ page import="ecar.dao.ItemEstruturaAcaoDao" %>
<%@ page import="ecar.pojo.ConfigMailCfgm" %>
<%@ page import="ecar.dao.ConfigMailCfgmDAO" %>
<%@ page import="ecar.pojo.UsuarioUsu" %>

<html lang="pt-br">
<head>
<%@ include file="../../include/meta.jsp"%>

<link rel="stylesheet" href="<%=_pathEcar%>/css/style_<%=new ecar.dao.ConfiguracaoDao(request).getConfiguracao().getEstilo().getNome()%>.css" type="text/css">
<script language="javascript" src="../../js/menu_retratil.js"></script>
<script language="javascript" src="../../js/focoInicial.js"></script>
<script language="javascript" src="../../js/frmPadraoItemEstrut.js"></script>
<script language="javascript" src="../../js/validacoes.js"></script>
<script language="javascript" src="../../js/datas.js"></script>
<script language="javascript" src="<%=_pathEcar%>/js/destaqueLinha.js"></script>
<script language="javascript" src="<%=_pathEcar%>/js/listaItensCadastro.js" type="text/javascript"></script>		 
<script language="javascript" src="<%=_pathEcar%>/js/jquery.js" type="text/javascript"></script>
<script type="text/javascript">jQuery.noConflict();</script>		
<script language="javascript" src="<%=_pathEcar%>/js/cookie.js"></script>

<%
	ConfigMailCfgmDAO configMailCfgmDAO = new ConfigMailCfgmDAO();
	ConfigMailCfgm configMailCfgm = (ConfigMailCfgm) configMailCfgmDAO.buscar(ConfigMailCfgm.class, Dominios.CFG_MAIL_MANUTENCAO_EVENTO);
%>

<script>
function aoClicarBtn2(form)
{
	if(document.form.enviaEmailAtivo.value == 'S') {
		if(document.form.obrigatorio.value == 'S' || (document.form.obrigatorio.value == 'N' && confirm('<%=_msg.getMensagem("acompanhamento.cadastroItens.eventos.criacao.autorizaEnviaEmail")%>') == true )) {
			document.form.autorizarMail.value = 'S';
		} 
	}
	if(validar(form)){
		form.btn2[0].disabled = true;
		form.btn2[1].disabled = true;
		form.hidAcao.value = "incluir";
		form.action = "ctrl.jsp"
		form.submit();
	}
}
<%@ include file="validacao.jsp"%>

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
	ItemEstrutAcaoIetta ieAcao = new ItemEstrutAcaoIetta();		
	
	String codAba = Pagina.getParamStr(request, "codAba");
	
	/* carrega o usuário da session */
	UsuarioUsu usuario = ((ecar.login.SegurancaECAR)session.getAttribute("seguranca")).getUsuario();
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


ConfiguracaoCfg configuracaoCfg = new ecar.dao.ConfiguracaoDao(request).getConfiguracao();

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
							<%
								EstruturaFuncaoEttf estruturaFuncao = new EstruturaFuncaoEttf();
								EstruturaFuncaoDao estruturaFuncaoDao = new EstruturaFuncaoDao(request);
								
								estruturaFuncao = (EstruturaFuncaoEttf) estruturaFuncaoDao.getLabelFuncao(itemEstrutura.getEstruturaEtt(), Long.valueOf(codAba));
							%>
							
								<input type="hidden" name="enviaEmailAtivo" value="<%=configMailCfgm.getAtivoCfgm()%>">
								<input type="hidden" name="obrigatorio" value="<%=configMailCfgm.getIndEnvioObrigatorio()%>">
								<input type="hidden" name="labelEttf" value="<%=estruturaFuncao.getLabelEttf()%>">
								<input type="hidden" name="autorizarMail" value="N">
								<util:barrabotoes btn2="Gravar" voltar="Cancelar"/>
								<%@ include file="form.jsp"%>
								<util:barrabotoes btn2="Gravar" voltar="Cancelar"/>
						
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