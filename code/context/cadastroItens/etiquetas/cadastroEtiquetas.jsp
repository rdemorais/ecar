<%@ taglib uri="/WEB-INF/tlds/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/tlds/displaytag.tld" prefix="display" %>
<%@ include file="/frm_global.jsp"%>
<%@ page import="ecar.dao.EstruturaFuncaoDao" %>
<%@ page import="ecar.dao.EstruturaDao" %>
<%@ page import="ecar.dao.ItemEstruturaDao" %>
<%@ page import="ecar.permissao.ValidaPermissao" %> 
<%@ page import="ecar.pojo.ItemEstruturaIett" %>
<%@ page import="ecar.pojo.ItemEstrutAcaoIetta" %>
<%@ page import="ecar.pojo.EstruturaFuncaoEttf" %>
<%@ page import="ecar.pojo.UsuarioUsu" %>
<%@ page import="ecar.pojo.ConfigMailCfgm" %>
<%@ page import="ecar.dao.ConfigMailCfgmDAO" %>
<%@ page import="ecar.util.Dominios" %> 


<%@ page import="java.util.Iterator" %>  
<%@ page import="java.util.List" %> 
<jsp:useBean id="seguranca" scope="session" class="ecar.login.SegurancaECAR"/>
<%@ taglib uri="/WEB-INF/ecar-util.tld" prefix="util" %>
<%
ValidaPermissao validaPermissao = new ValidaPermissao();
String codAba = "";
ItemEstruturaIett itemEstrutura = new ItemEstruturaIett();
UsuarioUsu usuario = new UsuarioUsu();
List<RelacionamentoIettEtiqueta> listaRelEtiquetas = new ArrayList<RelacionamentoIettEtiqueta>();
ArrayList<String> msgSistema = new ArrayList<String>();

try{ 
	/* carrega o usuário da session */
	usuario = ((ecar.login.SegurancaECAR)session.getAttribute("seguranca")).getUsuario();
	
	ItemEstruturaDao itemEstruturaDao = new ItemEstruturaDao(request);
	itemEstrutura = (ItemEstruturaIett) itemEstruturaDao.buscar(ItemEstruturaIett.class, Long.valueOf(Pagina.getParamStr(request, "codIett")));
	
	listaRelEtiquetas = (List<RelacionamentoIettEtiqueta>) request.getAttribute("listaRelEtiquetas");
	msgSistema = (ArrayList<String>) request.getAttribute("msgSistema");
	
	
	if ( !validaPermissao.permissaoConsultaIETT(  Long.valueOf(Pagina.getParamStr(request, "codIett")), seguranca ) )
	{
		response.sendRedirect( request.getContextPath() +  "/acessoIndevido.jsp" );
	}
	codAba = Pagina.getParamStr(request, "codAba");
}catch (Exception e){
	e.printStackTrace();
}
%>
	

<%@page import="ecar.pojo.EstruturaEtt"%>


<%@page import="ecar.pojo.Etiqueta"%>
<%@page import="ecar.pojo.RelacionamentoIettEtiqueta"%><html lang="pt-br">
	<head>
		<style type="text/css">
			body{
				background-color: #EAEEF4;
				font-size: 10px;
				font-family: Verdana,Arial,Helvetica,sans-serif;
			}
			.botao {
		    	background-color: #FFFFFF;
		   	 	border: 1px solid #596D9B;
		    	color: #596D9B;
			    font-family: Verdana,Arial,Helvetica,sans-serif;
			    font-size: 10px;
			    margin: 0;
			}
			label{
				color: #1C2265;
			}
			.ui-menu .ui-menu-item a {
				background-color: white;
		    	display: block;
		    	line-height: 1.5;
		    	padding: 0.2em 0.4em;
		    	text-decoration: none;
			}
			.ui-menu {
		    	list-style: none outside none;
			}
			ul.ui-autocomplete{
				padding-left: 0;
			}
		</style>
		<script src="${pageContext.request.contextPath}/js/jquery.1.7.1.js"></script>
		<script src="${pageContext.request.contextPath}/js/jquery.ui.core.js"></script>
		<script src="${pageContext.request.contextPath}/js/jquery.ui.widget.js"></script>
		<script src="${pageContext.request.contextPath}/js/jquery.ui.position.js"></script>
		<script src="${pageContext.request.contextPath}/js/jquery.ui.autocomplete.js"></script>
		<%@ include file="/include/meta.jsp"%> 
		<%@ include file="/titulo.jsp"%>
		
		
		
		
		<%
			ConfigMailCfgmDAO configMailCfgmDAO = new ConfigMailCfgmDAO();
			ConfigMailCfgm configMailCfgm = (ConfigMailCfgm) configMailCfgmDAO.buscar(ConfigMailCfgm.class, Dominios.CFG_MAIL_MANUTENCAO_EVENTO);
		%>
		<script type="text/javascript">
			
			<% /* Função Responsável por desativar o relacionamento entre o item de estrutura e a etiqueta */ %>
			function retirarEtiqueta(codRelEtiqueta){
				var idEtiqueta = document.getElementById("idEtiqueta");
				var acao = document.getElementById("acao");
				var formEtiquetas = document.getElementById("formEtiquetas");

				if (confirm('Confirma exclusão?')){
					acao.value = "apagarEtiqueta";
					idEtiqueta.value = codRelEtiqueta;
					document.getElementById("formEtiquetas").submit();
				}
				
				

			}

			<% /* Função responsável pelo autocomplete via AJAX */ %>
			function autoCompleteEtiquetas(){
				$.ajax({
					url: "acCadastroEtiquetas?nomeEtiq="+$("#nomeEtiq").val()+"&codIett="+$("#codIett").val(),
					dataType: "xml",
					success: function( xmlResponse ) {
						var data = $( "etiqueta", xmlResponse ).map(function() {
							return {
								value: $( "nome", this ).text(), id: $( "codigo", this ).text()
							};
						}).get();
						$( "#nomeEtiq" ).autocomplete({
							source: data,
							minLength: 0,
							select: function( event, ui ) {
								inserirCodigoEtiquetaSubstituta(ui.item.value);
							}
						});
					}
				});
			} 

			<% /* Função complementar à função autoCompleteEtiquetas */ %>
			function inserirCodigoEtiquetaSubstituta(nome){
				document.getElementById("nomeEtiq").value = nome;
			}
		
		</script>
	 
	</head>

	<body>
		<%@ include file="../../cabecalho.jsp" %>
		<%@ include file="../../divmenu.jsp"%> 
		<div id="conteudo">
			<div id="tituloTelaCadastro">
				<!-- TITULO -->
				<%@ include file="/titulo_tela.jsp"%> 
			</div>
			
			<form name="form" method="post" action="servicoEtiqueta" id="formEtiquetas">
				<input type="hidden" value="<%= itemEstrutura.getCodIett() %>" name="codIett" id="codIett">	
				<input type="hidden" value="<%= codAba %>" name="codAba">
				<input type="hidden" value="<%= Pagina.getParamStr(request,"ultimoIdLinhaDetalhado") %>" name="ultimoIdLinhaDetalhado">
				<input type="hidden" value="" name="acao" id="acao">
				<input type="hidden" value="" name="idEtiqueta" id="idEtiqueta">
<%
				boolean ehTelaListagem = false;
				 
				EstruturaEtt estruturaEttSelecionada = null;
				EstruturaDao estruturaDaoArvoreItens = new EstruturaDao(request);
				
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
											<% //############### Árvore de Estruturas  ################### %>
											
											<util:arvoreEstruturas itemEstrutura="<%=itemEstrutura%>" contextPath="<%=_pathEcar%>" seguranca="<%=seguranca%>" idLinhaCadastro="<%=Pagina.getParamStr(request,"ultimoIdLinhaDetalhado") %>" ultimoIdLinhaExpandido="<%=Pagina.getParamStr(request,"ultimoIdLinhaExpandido")%>"/>
											<% //############### Árvore de Estruturas  ################### %> 
											 
											<% //############### Barra de Links  ################### %>
											
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
										</td>
									</tr>
								</table>
							</div>
						</td>
					</tr>
				</table>
				<br>
				<br>
	
				<div id="subconteudo"> 
					<table width="100%" border="0" cellpadding="0" cellspacing="0">
						<tr><td class="espacador"><img src="../../images/pixel.gif"></td></tr>
						<tr class="linha_titulo">
							<td  colspan="3">
								Etiqueta
							</td>
						</tr>
					</table>
					<table class="form">
						<tr>
							<td class="label" align="left" >Nome</td>
							<td colspan="2">
								<input type="text" name="nomeEtiq" id="nomeEtiq" size="150" title="Informe o nome da etiqueta que deseja adicionar ao item." style=" width : 508px;" onkeyup="autoCompleteEtiquetas()">
								<input type="submit" class="botao" name="adicionarEtiq" value="Adicionar" >
								<br/>
<% 
									if(!msgSistema.isEmpty()){
										for(String msg : msgSistema){
											out.println("								" + "<div>");
											out.println("								" + msg);
											out.println("								" + "<div/>");
										}
									}
								
%>
							</td>
						</tr>
						<tr class="label">
							<td colspan="3">
								<div id="divEtiquetasDoItem" style="position: relative;" align="center" >
								
<% 
									
									for (RelacionamentoIettEtiqueta rel : listaRelEtiquetas) {
										out.println("										<div id=\"idDivRel"+ rel.getCodigo() + "\" style=\"float: left; margin: 13px;\" > ");
										out.println("											" + rel.getEtiqueta().getNome());
										out.println("											" + "<a href=\"#\">");
										out.println("												" + "<img onclick=\"javascript:retirarEtiqueta(" + rel.getCodigo() +");\" alt=\"imagemExcluir\" src=\"../../images/icon_excluir_transparente.gif\" title=\"Clique para retirar esta etiqueta do item\" />");
										out.println("											" + "</a>");
										out.println("										</div> ");
									} 
									
%>
								</div>
							</td>
						</tr>
					</table>
					<table width="100%" border="0" cellpadding="0" cellspacing="0">
						<tr>
							<td class="espacador" >
								<img src="../../images/pixel.gif">
							</td>
						</tr>
						<tr class="linha_titulo" style="padding: 0px;" >
							<td  colspan="3" style="padding: 5px;">
								<br/>
							</td>
						</tr>
					</table>
				</div>
			</form>
		</div>
	</body>
</html>