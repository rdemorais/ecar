
<jsp:directive.page import="ecar.util.Dominios"/><%@ include file="../../login/validaAcesso.jsp"%>
<%@ include file="../../frm_global.jsp"%>

<%@ page import="ecar.pojo.ItemEstruturaIett" %>
<%@ page import="ecar.dao.ItemEstruturaUploadDao" %>
<%@ page import="ecar.dao.ItemEstruturaUploadCategoriaDao" %> 
<%@page import="ecar.pojo.ItemEstrUplCategIettuc"%> 
<%@page import="ecar.pojo.ItemEstrutUploadIettup"%> 
<%@ page import="ecar.pojo.UsuarioUsu" %>
<%@ page import="ecar.dao.UsuarioDao" %> 
<%@ page import="ecar.dao.ItemEstruturaDao" %>
<%@ page import="ecar.permissao.ValidaPermissao" %> 
<%@ page import="ecar.pojo.EstruturaFuncaoEttf" %>
<%@ page import="ecar.dao.EstruturaFuncaoDao" %>
<%@ page import="ecar.pojo.ConfigMailCfgm" %>
<%@ page import="ecar.dao.ConfigMailCfgmDAO" %>

<%@ page import="java.util.Iterator" %> 
<%@ page import="java.util.Set" %> 
<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="comum.util.Util" %>  
<%@ page import="comum.util.FileUpload" %><%@ page import="java.util.Comparator" %><%@ page import="java.util.Collections" %> 
 
<%@ taglib uri="/WEB-INF/ecar-util.tld" prefix="util" %>
<html lang="pt-br">
<head> 
<%@ include file="../../include/meta.jsp"%>
<%@ include file="/titulo.jsp"%>
<link rel="stylesheet" href="<%=_pathEcar%>/css/style_<%=configuracaoCfg.getEstilo().getNome()%>.css" type="text/css">
<script language="javascript" src="../../js/focoInicial.js"></script>
<script language="javascript" src="../../js/frmPadraoItemEstrut.js"></script>
<script language="javascript" src="../../js/validacoes.js"></script>
<script language="javascript" src="../../js/tableSort.js"></script>
<script language="javascript" src="../../js/popUp.js"></script>
<script language="javascript" src="<%=_pathEcar%>/js/destaqueLinha.js"></script>
<script language="javascript" src="<%=_pathEcar%>/js/listaItensCadastro.js" type="text/javascript"></script>		 
<script language="javascript" src="<%=_pathEcar%>/js/jquery.js" type="text/javascript"></script>
<script type="text/javascript">jQuery.noConflict();</script>		
<script language="javascript" src="<%=_pathEcar%>/js/cookie.js"></script>

<%
	ConfigMailCfgmDAO configMailCfgmDAO = new ConfigMailCfgmDAO();
	ConfigMailCfgm configMailCfgm = (ConfigMailCfgm) configMailCfgmDAO.buscar(ConfigMailCfgm.class, Dominios.CFG_MAIL_MANUTENCAO_ANEXO);
%>

<script>

function aoClicarExcluir(form){
	if(validarExclusao(form, "excluir")){
		if(!confirm("Confirma a exclusão?")){
			return(false);
		}
		if('<%=configMailCfgm.getAtivoCfgm()%>' == 'S') {
			if(document.form.obrigatorio.value == 'S' || (document.form.obrigatorio.value == 'N' && confirm('<%=_msg.getMensagem("acompanhamento.cadastroItens.anexo.exclusao.autorizaEnviaEmail")%>') == true )){
				document.form.autorizarMail.value = 'S';
			} 
		}
		form.hidAcao.value = "excluir";
		form.action = "ctrl.jsp";
		form.submit();
	}
}

</script>

<script language="javascript">

function aoClicarConsultar(form, cod){
	form.cod.value = cod;
	document.form.action = "frm_con.jsp";
	document.form.submit();
} 
function mudaTela(cod){
	document.form.cod.value = cod;
	//document.form.action = "../categoriaAnexo/frm_con.jsp";
	document.form.action = "../categoriaAnexo/lista.jsp";
	document.form.submit();	
}
</script>
 
</head>

<%@ include file="../../cabecalho.jsp" %>
<%@ include file="../../divmenu.jsp"%> 

<%

try{
	/* variável para verificar se o request veio de um form de upload */
	boolean isFormUpload = FileUpload.isMultipartContent(request);
	
	List campos = new ArrayList();
	
	String paramCodIett = "";
	String paramCodIettuc = "";
	String paramCod = "";
	String codAba = "";
	String labelEttf = "";
	
	if(isFormUpload){
		/* se for formulário de upload ( veio do frm_alt para o frm_con) deve tratar o request dessa forma */
		campos = FileUpload.criaListaCampos(request); 
		paramCodIett = FileUpload.verificaValorCampo(campos, "codIett");
		paramCodIettuc = FileUpload.verificaValorCampo(campos, "codIettuc");
		paramCod = FileUpload.verificaValorCampo(campos, "cod");
		codAba = FileUpload.verificaValorCampo(campos, "codAba");
		labelEttf = FileUpload.verificaValorCampo(campos,"labelEttf");
	} else {
		paramCodIett = Pagina.getParamStr(request, "codIett");
		paramCodIettuc = Pagina.getParamStr(request, "codIettuc");
		paramCod = Pagina.getParamStr(request, "cod");
		codAba = Pagina.getParamStr(request, "codAba");
		labelEttf = Pagina.getParamStr(request, "labelEttf");
	}

	ItemEstrUplCategIettuc categoriaAnexo = (ItemEstrUplCategIettuc) new ItemEstruturaUploadDao(request).buscar(ItemEstrUplCategIettuc.class, Long.valueOf(paramCodIettuc));

	if (!categoriaAnexo.getIndAtivoIettuc().equals("S")){
	%>
		<body onload="javascript:mudaTela('');">  
	<%
	}else{
	%>
		<body> 
	<%
	}
	%>
	
	<div id="conteudo"> 
	
	<% 


	ValidaPermissao validaPermissao = new ValidaPermissao();
	ItemEstruturaDao itemEstruturaDao = new ItemEstruturaDao(request); 
	
	ItemEstruturaIett itemEstrutura = (ItemEstruturaIett) itemEstruturaDao.buscar(ItemEstruturaIett.class, Long.valueOf(paramCodIett));
	
	if ( !validaPermissao.permissaoConsultaIETT(  Long.valueOf(paramCodIett) , seguranca ) )
	{response.sendRedirect( request.getContextPath() +  "/acessoIndevido.jsp" );
	}
					
	ItemEstrutUploadIettup anexo = new ItemEstrutUploadIettup();

	EstruturaFuncaoEttf estruturaFuncao = new EstruturaFuncaoEttf();
	EstruturaFuncaoEttf estruturaFuncaoItensAnexo = new EstruturaFuncaoEttf();

	EstruturaFuncaoDao estruturaFuncaoDao = new EstruturaFuncaoDao(request);
	
	estruturaFuncao = (EstruturaFuncaoEttf) estruturaFuncaoDao.getLabelFuncao(itemEstrutura.getEstruturaEtt(), Long.valueOf(codAba));
	estruturaFuncaoItensAnexo = estruturaFuncaoDao.getItensAnexo(itemEstrutura.getEstruturaEtt());
	
	Boolean permissaoAlterar = validaPermissao.permissaoAlterarItem(itemEstrutura, seguranca.getUsuario(), seguranca.getGruposAcesso(), estruturaFuncaoItensAnexo);
	
	String caminhoRaizUpload = configuracao.getRaizUpload();
%>

<div id="tituloTelaCadastro">
	<!-- TITULO -->
	<%@ include file="/titulo_tela.jsp"%> 
</div>

<form name="form" method="post" onsubmit="lista.jsp">

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
							<!-- ############### Árvore de Estruturas  ################### -->
							<util:arvoreEstruturas itemEstrutura="<%=itemEstrutura%>" contextPath="<%=_pathEcar%>" seguranca="<%=seguranca%>" idLinhaCadastro="<%=Pagina.getParamStr(request,"ultimoIdLinhaDetalhado") %>" ultimoIdLinhaExpandido="<%=Pagina.getParamStr(request,"ultimoIdLinhaExpandido")%>"/>
							<!-- ############### Árvore de Estruturas  ################### -->
							
							<!-- ############### Barra de Links  ################### -->
							<util:barraLinksItemEstrutura 
								estrutura="<%=itemEstrutura.getEstruturaEtt()%>" 
								selectedFuncao="<%=codAba%>" 
								codItemEstrutura="<%=itemEstrutura.getCodIett().toString()%>"
								contextPath="<%=request.getContextPath()%>"
								idLinhaCadastro="<%=Pagina.getParamStr(request,"ultimoIdLinhaDetalhado") %>"
								/>
							<!-- ############### Barra de Links  ################### -->
							<br><br>
							
							
							<input type="hidden" name="autorizarMail" value="N">
							<input type=hidden name="codIett" value="<%=itemEstrutura.getCodIett()%>">
							<input type=hidden name="hidAcao" value="">
							<input type=hidden name="codAba" value="<%=codAba%>">
							<input type=hidden name="codIettuc" value="<%=categoriaAnexo.getCodIettuc()%>">
							<input type=hidden name="cod" value="">
							<input type=hidden name="tipoOrdenacao" value="">
							<input type="hidden" name="obrigatorio" value="<%=configMailCfgm.getIndEnvioObrigatorio()%>">
							<input type="hidden" name="labelEttf" value="<%=labelEttf%>">
							
							<table width="100%" >
								<tr>
									<td align="left" >
										<%@ include file="../../include/problemaDown.jsp"%> 
									</td>
									<td align="right" >
										<div id="linkstop">
										<a href="javascript:mudaTela(<%=categoriaAnexo.getCodIettuc()%>)"><%=estruturaFuncaoDao.getLabelFuncaoCategoriaAnexo(itemEstrutura.getEstruturaEtt())%></a>&nbsp;|&nbsp;<b><%=estruturaFuncaoDao.getLabelFuncaoAnexo(itemEstrutura.getEstruturaEtt())%></b>
										</div>
									</td>	
								</tr>
							</table>
								<!-- ############### Tag de Anexos ################### -->
								<util:anexosListaTag 
									estruturaFuncao="<%=estruturaFuncao%>"
									categoriaAnexo="<%=categoriaAnexo%>"
									permissaoAlterar="<%=permissaoAlterar%>"
									anexo="<%=anexo%>" 
									caminhoRaizUpload="<%=caminhoRaizUpload%>"
									pathEcar="<%=_pathEcar%>"
									request="<%=request %>"
								/>
								<!-- ############### Tag de Anexos  ################### -->
						
						</td>
					</tr>
				</table>			
			</div>
			
		</td>
	</tr>
	
</table>	

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

<% /* Controla o estado do Menu (aberto ou fechado) */%>
<%@ include file="../../include/estadoMenu.jsp"%>
<%@ include file="../../include/mensagemAlert.jsp" %>
<br>
</div>
</form>
</body>
</html>
