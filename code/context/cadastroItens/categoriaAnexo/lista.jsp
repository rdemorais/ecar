<%@ include file="../../login/validaAcesso.jsp"%>
<%@ include file="../../frm_global.jsp"%>

<%@ page import="ecar.pojo.ItemEstruturaIett" %>
<%@ page import="ecar.dao.ItemEstruturaUploadCategoriaDao" %> 
<%@page import="ecar.pojo.ItemEstrUplCategIettuc"%> 
<%@ page import="ecar.pojo.UsuarioUsu" %>
<%@ page import="ecar.permissao.ValidaPermissao" %> 
<%@ page import="ecar.dao.UsuarioDao" %> 
<%@ page import="ecar.dao.ItemEstruturaDao" %>
<%@ page import="ecar.pojo.EstruturaFuncaoEttf" %>
<%@ page import="ecar.dao.EstruturaFuncaoDao" %>

<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.Iterator" %> 
<%@ page import="java.util.Collection" %> 

<%@ page import="comum.util.FileUpload" %> 

<%@ taglib uri="/WEB-INF/ecar-util.tld" prefix="util" %>

<%@page import="ecar.dao.EstruturaDao"%><html lang="pt-br">
<head> 
<%@ include file="../../include/meta.jsp"%>
<%@ include file="/titulo.jsp"%>
<link rel="stylesheet" href="<%=_pathEcar%>/css/style_<%=configuracaoCfg.getEstilo().getNome()%>.css" type="text/css">
<script language="javascript" src="../../js/focoInicial.js"></script>
<script language="javascript" src="../../js/frmPadraoItemEstrut.js"></script>
<script language="javascript" src="../../js/validacoes.js"></script>
<script language="javascript" src="<%=_pathEcar%>/js/destaqueLinha.js"></script>
<script language="javascript" src="<%=_pathEcar%>/js/listaItensCadastro.js" type="text/javascript"></script>		 
<script language="javascript" src="<%=_pathEcar%>/js/jquery.js" type="text/javascript"></script>
<script type="text/javascript">jQuery.noConflict();</script>		
<script language="javascript" src="<%=_pathEcar%>/js/cookie.js"></script>

<script language="javascript">

function aoClicarConsultar(form, cod){
	form.codIettuc.value = cod;
	document.form.action = "../anexo/lista.jsp";
	document.form.submit();
} 

function aoClicarEditar(form, cod){
	form.cod.value = cod;
	document.form.action = "frm_con.jsp";
	document.form.submit();
}

</script>
 
</head>

<%@ include file="../../cabecalho.jsp" %>
<%@ include file="../../divmenu.jsp"%> 

<body> 
<div id="conteudo"> 

<% 
try{
	/* variável para verificar se o request veio de um form de upload */
	boolean isFormUpload = FileUpload.isMultipartContent(request);
	
	List campos = new ArrayList();
	
	String paramCodIett = "";
	String paramCod = "";
	String codAba = "";
	
	if(isFormUpload){
		/* se for formulário de upload ( veio do frm_alt para o frm_con) deve tratar o request dessa forma */
		campos = FileUpload.criaListaCampos(request); 
		paramCodIett = FileUpload.verificaValorCampo(campos, "codIett");
		paramCod = FileUpload.verificaValorCampo(campos, "cod");
		codAba = FileUpload.verificaValorCampo(campos, "codAba");			
	} else {
		paramCodIett = Pagina.getParamStr(request, "codIett");
		paramCod = Pagina.getParamStr(request, "cod");
		codAba = Pagina.getParamStr(request, "codAba");			
	}
	
	ValidaPermissao validaPermissao = new ValidaPermissao();
	ItemEstruturaDao itemEstruturaDao = new ItemEstruturaDao(request); 
	
	ItemEstruturaIett itemEstrutura = (ItemEstruturaIett) itemEstruturaDao.buscar(ItemEstruturaIett.class, Long.valueOf(paramCodIett));
	
	if ( !validaPermissao.permissaoConsultaIETT(  Long.valueOf(paramCodIett) , seguranca ) )
	{response.sendRedirect( request.getContextPath() +  "/acessoIndevido.jsp" );
	}	
	
	ItemEstruturaUploadCategoriaDao categoriaDao = new ItemEstruturaUploadCategoriaDao(request);
	
	EstruturaFuncaoEttf estruturaFuncao = new EstruturaFuncaoEttf();
	EstruturaFuncaoDao estruturaFuncaoDao = new EstruturaFuncaoDao(request);
	
	estruturaFuncao = (EstruturaFuncaoEttf) estruturaFuncaoDao.getLabelFuncao(itemEstrutura.getEstruturaEtt(), Long.valueOf(codAba));

	Boolean permissaoAlterar = validaPermissao.permissaoAlterarItem(itemEstrutura, seguranca.getUsuario(), seguranca.getGruposAcesso(), estruturaFuncao);
	
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

<div id="tituloTelaCadastro">
	<!-- TITULO -->
	<%@ include file="/titulo_tela.jsp"%> 
</div>
 
<form name="form" method="post">

<input type=hidden name="codIett" value="<%=itemEstrutura.getCodIett()%>">
<input type=hidden name="hidAcao" value="">
<input type=hidden name="codAba" value="<%=codAba%>">
<input type=hidden name="cod" value="">
<input type=hidden name="codIettuc" value="">
<input type="hidden" name="labelEttf" value="<%=estruturaFuncao.getLabelEttf()%>">
 
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
							<util:arvoreEstruturas 
								itemEstrutura="<%=itemEstrutura%>" 
								contextPath="<%=_pathEcar%>" 
								seguranca="<%=seguranca%>" 
								idLinhaCadastro="<%=Pagina.getParamStr(request,"ultimoIdLinhaDetalhado") %>"
								ultimoIdLinhaExpandido="<%=Pagina.getParamStr(request,"ultimoIdLinhaExpandido")%>"/>
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
							
							<table width="100%" border="0" cellpadding="0" cellspacing="0">
								<tr><td class="espacador"><img src="../../images/pixel.gif"></td></tr>
								<tr class="linha_titulo" align="right">
									<td>
										<%if(permissaoAlterar.booleanValue()){%>
											<input type="button" value="Adicionar" class="botao" onclick="javascript:aoClicarIncluir(document.form)"> 
											<input type="button" value="Excluir" class="botao" onclick="javascript:aoClicarExcluir(document.form)">
										<%}%>
									</td>
								</tr>
							</table>
								<!-- ############### Listagem  ################### -->
							<table width="100%" border="0" cellpadding="0" cellspacing="0">
								<tr><td class="espacador" colspan=4><img src="../../images/pixel.gif"></td></tr>
								<tr class="linha_subtitulo">
									<td width="4%">
										<input type=checkBox class="form_check_radio" name=todos onclick="javascript:selectAll(document.form)">&nbsp;
									</td>
									<td width="4%"></td>
									<td>Nome da Pasta</td>
									<td>Tipo</td>
								</tr>
								<tr><td class="espacador" colspan=4><img src="../../images/pixel.gif"></td></tr>
								<%
									/* Percorre a lista de Pontos Criticos de ItemEstrutura e imprime na tela */
									Collection categoriasAtivas = categoriaDao.getAtivos(itemEstrutura);
									if(categoriasAtivas != null ){ 
												Iterator itCat = categoriasAtivas.iterator();
												while(itCat.hasNext()){
													ItemEstrUplCategIettuc categoria = (ItemEstrUplCategIettuc) itCat.next();
													%>
													<tr class="linha_subtitulo2"> 
														<td width="4%">
															<input type="checkbox" class="form_check_radio" name="excluir" value="<%=categoria.getCodIettuc()%>">
														</td>
														<td width="4%">
															<a href="javascript:aoClicarEditar(document.form,<%=categoria.getCodIettuc()%>)">
															<img border="0" src="../../images/icon_alterar.png" alt="Alterar"></a>&nbsp;
														</td>
														<td>
															<a href="javascript:aoClicarConsultar(document.form,<%=categoria.getCodIettuc()%>)">
															<%=categoria.getNomeIettuc()%></a>
														</td>
														<td>
														<%if(categoria.getUploadTipoCategoriaUtc()!=null){%>
																<%=categoria.getUploadTipoCategoriaUtc().getNomeUtc()%>
														<%}%>		
														</td>										
													</tr>	
													<%
												}  
											}	
									%>
							</table>		
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
	<!-- ############### Listagem  ################### -->

<br>
</form>
</div>
</body>
<% /* Controla o estado do Menu (aberto ou fechado) */%>
<%@ include file="../../include/estadoMenu.jsp"%>
<%@ include file="../../include/mensagemAlert.jsp" %>
</html>

