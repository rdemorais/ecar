
<jsp:directive.page import="ecar.dao.AgendaDao"/><%@ include file="../../login/validaAcesso.jsp"%>
<%@ include file="../../frm_global.jsp"%>
<%@ page import="ecar.pojo.ItemEstruturaIett" %>
<%@ page import="ecar.dao.EstruturaDao" %>
<%@ page import="ecar.dao.ItemEstruturaDao" %>
<%@ page import="ecar.permissao.ValidaPermissao" %> 
<%@ page import="ecar.pojo.EstruturaFuncaoEttf" %>
<%@ page import="ecar.pojo.AgendaAge" %>
<%@ page import="ecar.dao.EstruturaFuncaoDao" %>
<%@ page import="comum.util.Util" %>
<%@ page import="java.util.Iterator" %> 
<%@ page import="java.util.List" %> 


<%@ taglib uri="/WEB-INF/ecar-util.tld" prefix="util" %>

<html lang="pt-br">
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
<% //script language="javascript" src="<%=_pathEcar% >/js/destaqueLinha.js"></script%>
<script language="javascript">

function aoClicarConsultar(form, codigo){
	form.codAge.value = codigo;
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

<% 
try{
	EstruturaDao estruturaDao = new EstruturaDao(request);
	ItemEstruturaDao itemEstruturaDao = new ItemEstruturaDao(request);
	ItemEstruturaIett itemEstrutura = (ItemEstruturaIett) itemEstruturaDao.buscar(ItemEstruturaIett.class, Long.valueOf(Pagina.getParamStr(request, "codIett")));
	
	ValidaPermissao validaPermissao = new ValidaPermissao();

	if ( !validaPermissao.permissaoConsultaIETT(  Long.valueOf(Pagina.getParamStr(request, "codIett")), seguranca ) ) {
		response.sendRedirect( request.getContextPath() +  "/acessoIndevido.jsp" );
	}
	
	String codAba = Pagina.getParamStr(request, "codAba");
%>
 
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
								<util:arvoreEstruturas itemEstrutura="<%=itemEstrutura%>" contextPath="<%=_pathEcar%>" idLinhaCadastro="<%=Pagina.getParamStr(request,"ultimoIdLinhaDetalhado") %>" ultimoIdLinhaExpandido="<%=Pagina.getParamStr(request,"ultimoIdLinhaExpandido")%>"/> 
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
							<%
								EstruturaFuncaoEttf estruturaFuncao = new EstruturaFuncaoEttf();
								EstruturaFuncaoDao estruturaFuncaoDao = new EstruturaFuncaoDao(request);
								
								estruturaFuncao = (EstruturaFuncaoEttf) estruturaFuncaoDao.getLabelFuncao(itemEstrutura.getEstruturaEtt(), Long.valueOf(codAba));
								
								Boolean permissaoAlterar = validaPermissao.permissaoAlterarItem(itemEstrutura, seguranca.getUsuario(), seguranca.getGruposAcesso(), estruturaFuncao);
							%>
							
							<div id="subconteudo">
							
							
								<input type=hidden name="codIett" value="<%=itemEstrutura.getCodIett()%>">
								<input type=hidden name="hidAcao" value="">
								<input type=hidden name="codAba" value="<%=Pagina.getParamStr(request, "codAba")%>">
								<input type=hidden name="codAge" value="">
								<input type="hidden" name="hidPesquisou" value="false">
								<input type="hidden" name="hidExcluir" value="unico">
							
								<table width="100%" border="0" cellpadding="0" cellspacing="0">
									<tr><td class="espacador" colspan="8"><img src="../../images/pixel.gif"></td></tr>
									<tr class="linha_titulo"> 
										<td>Eventos:</td>
										<td align="right">&nbsp;
								<%if(permissaoAlterar.booleanValue()){%>
											<input type="button" value="Adicionar" class="botao" onclick="javascript:aoClicarIncluir(document.form)"> 
											<input type="button" value="Excluir" class="botao" onclick="javascript:aoClicarExcluir(document.form)">
								<%}%>
										</td>
									</tr>
								</table>
								
								<table border="0" cellspacing="0" style="width: 100%">	
									<tr><td class="espacador" colspan="12"><img src="../../images/pixel.gif"></td></tr>
									<tr class="linha_subtitulo">
								        <td  style="width: 3%;">&nbsp;</td>
								        <!--  td  style="width: 3%;">&nbsp;</td-->
								        <td colspan="2" style="width: 24%;" > &nbsp;&nbsp;&nbsp;&nbsp; Periodo Proposto </td>
								        <td rowspan="2" style="width: 15%;">Tipo</td>
								        <td rowspan="2" style="width: 21%;">Nome Evento</td>
								        <td rowspan="2" style="width: 20%;">Descri&ccedil;&atilde;o</td>
								        <td rowspan="2" style="width: 10%;" >Local</td>
								        <td rowspan="2" style="width: 7%;">Realizado</td>
									</tr>
									<tr class="linha_subtitulo">
								        <td width="2%"><input type="checkBox" class="form_check_radio" name="todos" onclick="javascript:selectAll(document.form)"></td>
								        <!-- td > &nbsp; </td-->
								        <td style="width: 11%;" >In&iacute;cio</td>
								        <td style="width: 11%;" >Fim</td>
									</tr>
									</tr>
									<tr><td class="espacador" colspan="12"><img src="../../images/pixel.gif"></td></tr>
										
								<%
										
								if (itemEstrutura.getAgendaAge()!=null){
									String cor="";
									int contIt = 0;
											
									//List lista =  itemEstruturaDao.ordenaSet(itemEstrutura.getAgendaAge() , "this.agendaAge.codAge", "asc");
									Iterator it = itemEstrutura.getAgendaAge().iterator(); //lista.iterator();
							
									if (it!=null)
									while(it.hasNext()){
									  	AgendaAge agenda = (AgendaAge) it.next();
									  	contIt++;
									  	
									  	cor = ((contIt%2==0)?"cor_sim":"cor_nao");
									%>
									<!--  tr-->
									
									<tr class="<%=cor%>"  >
										<td> <input type="checkbox" class="form_check_radio" name="excluir" value="<%= agenda.getCodAge()%>" id="chkAge+<%=contIt%>" >
										</td>
										<!-- td> <input type="image" name="imgAge" src="../../images/bt_lupa.gif" value="<%= agenda.getCodAge()%>" id="imgAge+<%=contIt%>" onclick="aoClicarConsultar(document.form,<%=agenda.getCodAge()%>)">
										</td-->
										<td>
											<a href="javascript:aoClicarConsultar(document.form,<%= Pagina.trocaNull(agenda.getCodAge()+"") %>)" title="Visualizar todos os campos"> 
												<%=Pagina.trocaNullData(agenda.getDataAge()) %>	
											</a>
										</td>
										<td> <%=Pagina.trocaNullData(agenda.getDataLimiteAge()) %>	</td>
								<%if (agenda.getTipoEventoSatb()!=null){ %>
										<td> <%= Pagina.trocaNull(agenda.getTipoEventoSatb().getDescricaoSatb())%></td>
								<%}else{ %>	
									<td> &nbsp; </td>	
								<%}%>
										<td> <%=Pagina.trocaNull(agenda.getEventoAge()) %>	</td>
										<td> <%=Pagina.trocaNull(agenda.getDescricaoAge()) %>	</td>
										<td> <%=Pagina.trocaNull(agenda.getLocalAge()) %>	</td> 
										<td> <%=  (agenda.getRealizado().equals("S")?"Sim":"N&atilde;o")  %>	</td>
									</tr>
									<tr class="<%=cor%>" class="linha_subtitulo2">
									<!-- tr-->
										<td> 	</td>
										<td colspan="7">
							
											<table style="width: 92%;"> 
											  <tr style="font-weight: bold;width: 60%;" >
												<td style="padding: 5px;width: 40%;">Contato: </td>
												<td>Coment&aacute;rio</td>
											  </tr>
											  <tr>
												<td ><%= Pagina.trocaNull(agenda.getNomeContato()) %> - 
													 <%= Pagina.trocaNull(agenda.getOrgaoContato()) %> -  
													 <%= Pagina.trocaNull(agenda.getTelefoneContato()) %> 
												</td>
												<td><%= Pagina.trocaNull(agenda.getComentario()) %> </td>
											  </tr>
											</table>
										
										</td>
									</tr>
							<%
									}
								}
							%>
									<tr><td class="espacador" colspan="12"><img src="../../images/pixel.gif"></td></tr>
								</table>
						
						</td>
					</tr>
				</table>			
			</div>
			
		</td>
	</tr>
	
</table>
 




<%
} catch (ECARException e) {
	org.apache.log4j.Logger.getLogger(this.getClass()).error(e);
	Mensagem.alert(_jspx_page_context, _msg.getMensagem("erro.exception"));
} catch (Exception e){%>
<%@ include file="/excecao.jsp"%>
<% 
}
%>	

</form>

</body>
<% /* Controla o estado do Menu (aberto ou fechado) */%>
<%@ include file="../../include/estadoMenu.jsp"%>
<%@ include file="../../include/mensagemAlert.jsp" %>
</html>

