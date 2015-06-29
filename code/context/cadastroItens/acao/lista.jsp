<%@ include file="/login/validaAcesso.jsp"%>
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

<%@ taglib uri="/WEB-INF/ecar-util.tld" prefix="util" %>
<%
try{ 
	/* carrega o usuário da session */
	UsuarioUsu usuario = ((ecar.login.SegurancaECAR)session.getAttribute("seguranca")).getUsuario();
	
	ItemEstruturaDao itemEstruturaDao = new ItemEstruturaDao(request);
	ItemEstruturaIett itemEstrutura = (ItemEstruturaIett) itemEstruturaDao.buscar(ItemEstruturaIett.class, Long.valueOf(Pagina.getParamStr(request, "codIett")));
	
	ValidaPermissao validaPermissao = new ValidaPermissao();
	if ( !validaPermissao.permissaoConsultaIETT(  Long.valueOf(Pagina.getParamStr(request, "codIett")), seguranca ) )
	{
		response.sendRedirect( request.getContextPath() +  "/acessoIndevido.jsp" );
	}
	String codAba = Pagina.getParamStr(request, "codAba");
%>
	
<html lang="pt-br">
<head>
<%@ include file="/include/meta.jsp"%> 
<%@ include file="/titulo.jsp"%>
<link rel="stylesheet" href="<%=_pathEcar%>/css/style_<%=configuracaoCfg.getEstilo().getNome()%>.css" type="text/css">

<script language="javascript" src="../../js/focoInicial.js"></script>
<script language="javascript" src="../../js/frmPadraoItemEstrut.js"></script>
<script language="javascript" src="../../js/validacoes.js"></script>
<script language="javascript" src="../../js/tableSort.js"></script>

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

function aoClicarBtn2(form){
	if(validarExclusao(form, "excluir")){
		if(!confirm("Confirma a exclusão?")){
			return(false);
		}
		if(document.form.enviaEmailAtivo.value == 'S') {
			if(document.form.enviaEmailObrigatorio.value == 'S' || (document.form.enviaEmailObrigatorio.value == 'N' && confirm('<%=_msg.getMensagem("acompanhamento.cadastroItens.eventos.exclusao.autorizaEnviaEmail")%>') == true )) {
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

function aoClicarConsultar(form, codigo){
	form.codIetta.value = codigo;
	form.hidAcao.value = 'alterar';
	document.form.action = "frm_con.jsp";
	document.form.submit();
} 


</script>
 
</head>

<%@ include file="../../cabecalho.jsp" %>
<%@ include file="../../divmenu.jsp"%> 

<body> 
<div id="conteudo">

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
							<!-- ############### Árvore de Estruturas  ################### -->
							<util:arvoreEstruturas itemEstrutura="<%=itemEstrutura%>" contextPath="<%=_pathEcar%>" seguranca="<%=seguranca%>" idLinhaCadastro="<%=Pagina.getParamStr(request,"ultimoIdLinhaDetalhado") %>" ultimoIdLinhaExpandido="<%=Pagina.getParamStr(request,"ultimoIdLinhaExpandido")%>"/> 
							 <!-- ############### Árvore de Estruturas  ################### -->
							
							<!-- ############### Barra de Links  ################### -->
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

<div id="subconteudo">


<input type="hidden" name="autorizarMail" value="N">
<input type="hidden" name="enviaEmailAtivo" value="<%=configMailCfgm.getAtivoCfgm()%>">
<input type="hidden" name="enviaEmailObrigatorio" value="<%=configMailCfgm.getIndEnvioObrigatorio()%>">
<input type=hidden name="codIett" value="<%=itemEstrutura.getCodIett()%>">
<input type="hidden" name="codEttSelecionado" value="<%=itemEstrutura.getEstruturaEtt().getCodEtt()%>">
<input type=hidden name="hidAcao" value="">
<input type=hidden name="codAba" value="<%=Pagina.getParamStr(request, "codAba")%>">
<input type=hidden name="codIetta" value="">
<input type="hidden" name="Usuario" value="<%=usuario.getCodUsu().toString()%>">
 
<table width="100%" border="0" cellpadding="0" cellspacing="0">
	<tr><td class="espacador"><img src="../../images/pixel.gif"></td></tr>
	<tr class="linha_titulo" align="right">
		<td  colspan="3">
			<%if(permissaoAlterar.booleanValue()){%>
			<input type="button" value="Adicionar" class="botao" onclick="javascript:aoClicarIncluir(document.form)"> 
			<input type="button" value="Excluir"   class="botao" onclick="javascript:aoClicarBtn2(document.form)">
			<%}%>
		</td>
	</tr>
</table>
	<!-- ############### Listagem  ################### -->
<table width="100%" border="0" cellpadding="0" cellspacing="0">
	<thead> 
			<tr><td class="espacador" colspan=5><img src="../../images/pixel.gif"></td></tr>
			<tr class="linha_subtitulo">
				<td width="5%">
					<input type=checkBox class="form_check_radio" name=todos onclick="javascript:selectAll(document.form)">&nbsp;
				</td>
				<td >
					<a href="#" onclick="this.blur(); return sortTable('corpo',  1, true);" title="Data">Data</a>
				</td>
				<td>
					<a href="#" onclick="this.blur(); return sortTable('corpo',  2, true);" title="Descricao">Descri&ccedil;&atilde;o</a>
				</td>
				<td>
					<a href="#" onclick="this.blur(); return sortTable('corpo',  3, true);" title="Última atualização">Última atualização</a>
				</td>
				<td>
					<a href="#" onclick="this.blur(); return sortTable('corpo',  4, true);" title="Data de inclusão">Data de inclusão</a>
				</td>
			</tr>
			<tr><td class="espacador" colspan="5"><img src="../../images/pixel.gif"></td></tr>	
	</thead>
	<tbody id="corpo">	
	<%
		/* Percorre a lista de Beneficiários de ItemEstrutura e imprime na tela */
		if(itemEstrutura.getItemEstrutAcaoIettas() != null ){
			List acoes = itemEstruturaDao.ordenaSet(itemEstrutura.getItemEstrutAcaoIettas(), "this.dataIetta", "desc");
			Iterator itAcao = acoes.iterator();
			int cont = 0;
			String cor = new String(); 
			while(itAcao.hasNext()){
				if (cont % 2 == 0){
					cor = "cor_nao";
				} else {
					cor = "cor_sim"; 
				}
				
				ItemEstrutAcaoIetta ieAcao = (ItemEstrutAcaoIetta) itAcao.next();
				
				// ignorar registro inativo
				if(Dominios.NAO.equals(ieAcao.getIndAtivoIetta())) {
					continue;
				}
				%>
				<tr class="<%=cor%>" onmouseover="javascript:destacaLinha(this,'over','')" onmouseout="javascript: destacaLinha(this,'out','<%=cor%>')" onClick="javascript: destacaLinha(this,'click','<%=cor%>')" class="linha_subtitulo2">
					<td valign="top">
						<input type="checkbox" class="form_check_radio" name="excluir" value="<%=ieAcao.getCodIetta()%>">
					</td>
					<td valign="top">						
						<a href="javascript:aoClicarConsultar(document.form,<%=ieAcao.getCodIetta()%>)">
						<%=Pagina.trocaNullData(ieAcao.getDataIetta())%></a>
					</td>
					<!-- td><div id="pre"><%=Pagina.trocaNull(ieAcao.getDescricaoIetta())%></div></td -->
					<td><%=Pagina.trocaNull(ieAcao.getDescricaoIetta())%></td>
					<td valign="top"><% if (ieAcao.getUsuarioUsu() != null) %><%=ieAcao.getUsuarioUsu().getNomeUsu()%></td>
					<td valign="top"><% if (ieAcao.getUsuarioUsu() != null) %><%=Pagina.trocaNullData(ieAcao.getDataInclusaoIetta())%></td>
				</tr>	
				<%
				cont++;
			}
		}
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
	</tbody>
</table>
</div>
						</td>
					</tr>
				</table>			
			</div>
			
		</td>
	</tr>
	
</table>





<br>
</div>

</form>
</body>
<% /* Controla o estado do Menu (aberto ou fechado) */%>
<%@ include file="/include/estadoMenu.jsp"%>
<%@ include file="/include/mensagemAlert.jsp" %>
</html>