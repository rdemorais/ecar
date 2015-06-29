<jsp:directive.page import="ecar.util.Dominios"/>
<jsp:directive.page import="ecar.dao.EstruturaDao"/>
<jsp:directive.page import="ecar.pojo.ObjetoEstrutura"/>
<jsp:directive.page import="java.util.Collections"/>
<jsp:directive.page import="ecar.pojo.AcompRelatorioArel"/>
<jsp:directive.page import="ecar.pojo.PontoCriticoSisAtributoPtcSatb"/>
<jsp:directive.page import="ecar.taglib.util.Input"/>
<%@ include file="../../login/validaAcesso.jsp"%>
<%@ include file="../../frm_global.jsp"%>

<%@ page import="ecar.pojo.ItemEstruturaIett" %>
<%@ page import="ecar.pojo.PontoCriticoPtc" %>
<%@ page import="ecar.pojo.PontocriticoCorPtccor" %>
<%@ page import="ecar.dao.PontoCriticoDao" %>
<%@ page import="ecar.dao.ItemEstruturaDao" %>
<%@ page import="ecar.pojo.EstruturaFuncaoEttf" %>
<%@ page import="ecar.dao.EstruturaFuncaoDao" %>
<%@ page import="ecar.pojo.ConfigMailCfgm" %>
<%@ page import="ecar.permissao.ValidaPermissao" %> 
<%@ page import="ecar.dao.ConfigMailCfgmDAO" %>
<%@page import="ecar.pojo.SisAtributoSatb"%>
<%@page import="ecar.pojo.EstruturaAtributoEttat"%>
<%@page import="ecar.api.facade.*" %>


<%@ page import="comum.util.Util" %>
  
<%@ page import="java.util.Iterator" %> 
<%@ page import="java.util.Collection" %> 
<%@ page import="java.util.List" %><%@ page import="comum.util.Data" %><%@ page import="java.util.Date" %><%@ page import="ecar.pojo.ConfiguracaoCfg" %><%@ page import="ecar.dao.ConfiguracaoDao" %><%@ page import="ecar.pojo.SisGrupoAtributoSga" %>
<%@ page import="java.text.SimpleDateFormat" %>  

<%@ taglib uri="/WEB-INF/ecar-util.tld" prefix="util" %>

<%@page import="ecar.pojo.historico.HistoricoPontoCriticoPtc"%>
<%@page import="ecar.dao.HistoricoDao"%>
<%@page import="ecar.pojo.HistoricoConfig"%><html lang="pt-br">
<head> 
<%@ include file="../../include/meta.jsp"%>
<%@ include file="/titulo.jsp"%>
<link rel="stylesheet" href="<%=_pathEcar%>/css/style_<%=configuracaoCfg.getEstilo().getNome()%>.css" type="text/css">
<script language="javascript" src="<%=_pathEcar%>/js/focoInicial.js" type="text/javascript"></script>
<script language="javascript" src="<%=_pathEcar%>/js/frmPadraoItemEstrut.js" type="text/javascript"></script>
<script language="javascript" src="<%=_pathEcar%>/js/validacoes.js" type="text/javascript"></script>
<!-- 
<script language="javascript" src="<%=_pathEcar%>/js/ativarSortTable.js"></script>
<script language="javascript" src="<%=_pathEcar%>/js/sorttable.js"></script>
<script language="javascript" src="<%=_pathEcar%>/js/tableSort.js"></script>
 -->
<script language="javascript" src="<%=_pathEcar%>/js/newSortTable.js"></script>
<script language="javascript" src="<%=_pathEcar%>/js/destaqueLinha.js"></script>
<script language="javascript" src="<%=_pathEcar%>/js/listaItensCadastro.js" type="text/javascript"></script>		 
<script language="javascript" src="<%=_pathEcar%>/js/jquery.js" type="text/javascript"></script>
<script type="text/javascript">jQuery.noConflict();</script>		
<script language="javascript" src="<%=_pathEcar%>/js/cookie.js"></script>
 
<%
	ConfigMailCfgmDAO configMailCfgmDAO = new ConfigMailCfgmDAO();
	ConfigMailCfgm configMailCfgm = (ConfigMailCfgm) configMailCfgmDAO.buscar(ConfigMailCfgm.class, Dominios.CFG_MAIL_MANUTENCAO_PONTO_CRITICO);
	//ConfiguracaoCfg configuracao = new ConfiguracaoDao(request).getConfiguracao();
	//SisGrupoAtributoSga grupoAssunto = configuracao.getSisGrupoAtributoSgaTipoPontoCritico();
%>

<script type="text/javascript">

//window.setInterval("ordenacaoColunasListener()", 1000);

function aoClicarBtn2(form){
	if(validarExclusao(form, "excluirPontoCritico")){
		if(!confirm("Confirma a exclusão?")){
			return(false);
		}
		if('<%=configMailCfgm.getAtivoCfgm()%>' == 'S') {
			if(document.form.obrigatorio.value == 'S' || (document.form.obrigatorio.value == 'N' && confirm('<%=_msg.getMensagem("acompanhamento.cadastroItens.pontosCriticos.exclusao.autorizaEnviaEmail")%>') == true )) {
				document.form.autorizarMail.value = 'S';
			} 
		}
		form.hidAcao.value = "excluir";
		form.action = "ctrl.jsp";
		form.submit();
	}	
}

</script>

<script language="javascript" type="text/javascript">

function aoClicarConsultar(form, cod){
	form.codPtc.value = cod;
	document.form.action = "../apontamentos/lista.jsp";
	document.form.submit();
} 

function aoClicarEditar(form, cod){
	form.cod.value = cod;
	document.form.action = "frm_con.jsp";
	document.form.submit();
}

function aoClicarHistorico(form){
	if (validarChecksMarcado(form)){
		document.form.action = "lista_historico.jsp";
		document.form.submit();
	}
}

function aoClicarDeletados(form){
	form.mostrarDeletados.value = "Sim"
	document.form.action = "lista.jsp";
	document.form.submit();
}

function validarChecksMarcado(form){
	var numChecks = 0;
	var count = 0;
    var nomeCheckBox = "excluirPontoCritico";

    numChecks = verificaChecks(form, nomeCheckBox);
    
    if (numChecks > 0) {
		if(numChecks > 1){
			for(i = 0; i < eval('form.' + nomeCheckBox + '.length'); i++)
				if(eval('form.' + nomeCheckBox + '[i]').checked){
					count++;
				}
		} else {
			count++
		}
	}

  	if(count < 1){
		alert("Selecione pelo menos um registro para exibir o histórico.");
		return false;
	} else {
		return true;
	}
}

</script>
 
</head>

<%@ include file="../../cabecalho.jsp" %>
<%@ include file="../../divmenu.jsp"%>

<body onload='initTable("tabelaOrdenada",null);'> 
<div id="conteudo"> 

<% 
try{
	ItemEstruturaDao itemEstruturaDao = new ItemEstruturaDao(request);
	EstruturaDao estruturaDao = new EstruturaDao(request); 
	
	ItemEstruturaIett itemEstrutura = (ItemEstruturaIett) itemEstruturaDao.buscar(ItemEstruturaIett.class, Long.valueOf(Pagina.getParamStr(request, "codIett")));
	
	ValidaPermissao validaPermissao = new ValidaPermissao();
	if ( !validaPermissao.permissaoConsultaIETT(  Long.valueOf(Pagina.getParamStr(request, "codIett")), seguranca ) )
	{response.sendRedirect( request.getContextPath() +  "/acessoIndevido.jsp" );
	}
	
	String mostrarDeletados = Pagina.getParamStr(request, "mostrarDeletados");
	PontoCriticoDao pontoCriticoDao = new PontoCriticoDao(request);
	
	String codAba = Pagina.getParamStr(request, "codAba");
	//*******************************************************
	Boolean abaPontoCriticoDeAcompanhamento = Boolean.FALSE;
	String tipoSelecao = "";
	//*******************************************************
%>
 
<div id="tituloTelaCadastro">
	<!-- TITULO -->
	<%@ include file="/titulo_tela.jsp"%> 
</div>

<form name="form" method="post" action="">
<input type="hidden" name="autorizarMail" value="N">
<input type="hidden" name="codIett" value="<%=itemEstrutura.getCodIett()%>">
<input type="hidden" name="hidAcao" value="">
<input type="hidden" name="codAba" value='<%=Pagina.getParamStr(request, "codAba")%>'>
<input type="hidden" name="cod" value="">
<input type="hidden" name="codPtc" value="">
<input type="hidden" name="mostrarDeletados" value="">
<input type="hidden" name="obrigatorio" value="<%=configMailCfgm.getIndEnvioObrigatorio()%>">
<input type="hidden" name="primeira" value="">

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
							<util:barraLinksItemEstrutura estrutura="<%=itemEstrutura.getEstruturaEtt()%>" selectedFuncao="<%=codAba%>" codItemEstrutura="<%=itemEstrutura.getCodIett().toString()%>" contextPath="<%=request.getContextPath()%>" idLinhaCadastro="<%=Pagina.getParamStr(request,"ultimoIdLinhaDetalhado") %>"/>
							<!-- ############### Barra de Links  ################### -->

<%
	EstruturaFuncaoEttf estruturaFuncao = new EstruturaFuncaoEttf();
	EstruturaFuncaoDao estruturaFuncaoDao = new EstruturaFuncaoDao(request);
	
	estruturaFuncao = (EstruturaFuncaoEttf) estruturaFuncaoDao.getLabelFuncao(itemEstrutura.getEstruturaEtt(), Long.valueOf(codAba));
	
	Boolean permissaoAlterar = validaPermissao.permissaoAlterarItem(itemEstrutura, seguranca.getUsuario(), seguranca.getGruposAcesso(), estruturaFuncao);
	
	
	/* uma coluna de uma estrutura */
	ObjetoEstrutura coluna = null;
	EstruturaAtributoEttat colunaItem = null;
	
	List lColunas  = estruturaDao.getAtributosAcessoEstrutura(estruturaFuncao.getEstruturaEtt(), estruturaFuncao.getFuncaoFun());
	Iterator itColunas = null;	
	Iterator itColunasDel = null;
	String strCheckBox = "<td width=\"1%\"><input type=\"checkbox\" class=\"form_check_radio\" name=\"todosPontosCriticos\" onclick=\"javascript:selectAll(document.form, 'todosPontosCriticos', 'excluirPontoCritico');\"></td>";
	//String strColunaVazia = "<th width=\"1%\"></th> <!-- Coluna para colocar icone para listagem -->";
	String ordenaPor = null;
	int numTabelas = 1;
	
	//Verifica permissao para exibir o histórico de acordo com a estrutura/funcao
	boolean permissaoExbirHistoricoEstruturaFuncao = estruturaFuncaoDao.permissaoExibirHistorico(estruturaFuncao);
	//Verifica permissao para exibir histórico de acordo com as permissões de grupo de acesso
	boolean permissaoExibirHistoricoGruposAcesso = validaPermissao.permissaoExibirHistorico(itemEstrutura.getEstruturaEtt(),seguranca.getGruposAcesso());
	//O usuário só poderá visualizar o histórico caso tenha as duas permissões
	boolean permissaoExibirHistorico = (permissaoExbirHistoricoEstruturaFuncao && permissaoExibirHistoricoGruposAcesso);
	
%>

<br><br>

<div id="subconteudo">
 
<table width="100%" border="0" cellpadding="0" cellspacing="0">
	<tr><td class="espacador"><img src="<%=_pathEcar%>/images/pixel.gif" alt=""></td></tr>
	<tr class="linha_titulo" align="right">
		<td>
		
		<%if(permissaoAlterar.booleanValue()){%>
			<input type="button" value="Adicionar" class="botao" onclick="javascript:aoClicarIncluir(document.form)"> 
		<%}%>
		
		<%
			
			//verifica se é permitido remover a restrição ou ponto crítico. Mantis 0011103
			boolean permiteExcluir = validaPermissao.permiteExcluirPontoCritico(itemEstrutura, estruturaFuncao, seguranca);
			
			if(permiteExcluir)  
		    {%>
				<input type="button" value="Excluir" class="botao" onclick="javascript:aoClicarBtn2(document.form)">
			<%}%>
		<%if (permissaoExibirHistorico){ %>
			<input type="button" value="Mostrar Excluídos" class="botao" onclick="javascript:aoClicarDeletados(document.form)">
			<input type="button" value="Histórico" class="botao" onclick="javascript:aoClicarHistorico(document.form)">
		<%} %>
		</td>
	</tr>
</table>

<div>
	<table id="tabelaOrdenada" class="sortable" width="100%" border="0" cellpadding="0" cellspacing="0">
		<thead>
			<!-- <tr bgcolor="#EAEEF4"> -->
			<tr class="linha_subtitulo">
				<%
				/* desenha as colunas de uma estrutura */
				itColunas = lColunas.iterator();
				int numColuna = 2;
				while (itColunas.hasNext()){
					colunaItem = (EstruturaAtributoEttat) itColunas.next();
				%>			
					<%=strCheckBox%>
					
					<th width="<%=colunaItem.getLarguraListagemTelaEttat() %>%" align="left">
					<%numColuna++;%>
					<%=colunaItem.getLabelEstruturaEttat()%>
					</th>
				<%

				    if ((ordenaPor == null)&&(colunaItem.iGetGrupoAtributosLivres() == null)){
				    		ordenaPor = colunaItem.iGetNome();
					}

				strCheckBox = "";
				}
				%>
			</tr>
		</thead>
	
		<tbody id="corpo<%=numTabelas%>" >
		<%
		Collection pontosAtivos = Collections.EMPTY_LIST;
		//Verifica qual opção está marcada na tela
		if(abaPontoCriticoDeAcompanhamento.booleanValue() ){
			if( "".equals(tipoSelecao) || "T".equals(tipoSelecao) )
				pontosAtivos  = pontoCriticoDao.getPontosCriticosOrdenadoDataLimite(itemEstrutura, ordenaPor); 
			else{
				if("S".equals(tipoSelecao))
					pontosAtivos  = pontoCriticoDao.getPontosCriticosSolucionadosOrdenadoDataLimite(itemEstrutura, ordenaPor);
				if("N".equals(tipoSelecao))
					pontosAtivos  = pontoCriticoDao.getPontosCriticosNaoSolucionadosOrdenadoDataLimite(itemEstrutura, ordenaPor);
			}  	
  		// Percorre a lista de Pontos Criticos de ItemEstrutura e imprime na tela 
		} else {                           
   			pontosAtivos = pontoCriticoDao.getPontosCriticosOrdenadoDataLimite(itemEstrutura, ordenaPor);
	    }
    
	    if ((pontosAtivos != null)&&(pontosAtivos.size() > 0)){
    	
    		Iterator itPontosCriticos = pontosAtivos.iterator();
    		while (itPontosCriticos.hasNext()){
    	
	    		PontoCriticoPtc pontoCriticoPtc = (PontoCriticoPtc) itPontosCriticos.next();
    		
    			if (pontoCriticoPtc.getIndExcluidoPtc() == null || !pontoCriticoPtc.getIndExcluidoPtc().equals("S")){
    				%><tr  class="linha_subtitulo2">    				    
    					<td nowrap="nowrap" width="1%" valign="top" sorttable_customkey="0">
							<input type="checkbox" class="form_check_radio" name="excluirPontoCritico" value="<%=pontoCriticoPtc.getCodPtc()%>">
						
							<a href="javascript:aoClicarEditar(document.form,<%=pontoCriticoPtc.getCodPtc()%>)">
							<img border="0" src="<%=request.getContextPath()%>/images/icon_alterar.png" alt="Alterar"></a>&nbsp;
						</td>
					<%
	    			itColunas = lColunas.iterator();
					StringBuffer tag;
    				
					while (itColunas.hasNext()){
						//String informacaoAtbdem = "";
						tag = new StringBuffer();
		
						tag.append ("<td valign=\"top\" align=\"left\" ");
					
						coluna = (ObjetoEstrutura) itColunas.next();
					
						if (coluna.iGetGrupoAtributosLivres() != null)  {
					
							Iterator itPontoCriticoSisAtributoPtcSatbs = pontoCriticoPtc.getPontoCriticoSisAtributoPtcSatbs().iterator();
						
							while (itPontoCriticoSisAtributoPtcSatbs.hasNext()) {
						
								PontoCriticoSisAtributoPtcSatb pontoCriticoSisAtributoPtcSatb = (PontoCriticoSisAtributoPtcSatb) itPontoCriticoSisAtributoPtcSatbs.next();
							
								if (pontoCriticoSisAtributoPtcSatb.getSisAtributoSatb().getSisGrupoAtributoSga().equals(coluna.iGetGrupoAtributosLivres())){
							
									if (coluna.iGetGrupoAtributosLivres().getSisTipoExibicGrupoSteg().getCodSteg().equals(new Long(Input.TEXT)) ||
									 	coluna.iGetGrupoAtributosLivres().getSisTipoExibicGrupoSteg().getCodSteg().equals(new Long(Input.TEXTAREA)) ||
								 		coluna.iGetGrupoAtributosLivres().getSisTipoExibicGrupoSteg().getCodSteg().equals(new Long(Input.MULTITEXTO)) ||
									 	coluna.iGetGrupoAtributosLivres().getSisTipoExibicGrupoSteg().getCodSteg().equals(new Long(Input.VALIDACAO)) ) {
								 
										//Se for um atributo livre do tipo ID
										SisAtributoSatb sisAtributo = pontoCriticoSisAtributoPtcSatb.getSisAtributoSatb();
									
										if (sisAtributo.isAtributoAutoIcremental() || sisAtributo.isAtributoContemMascara()) {
											tag.append(" sorttable_customkey=\""+pontoCriticoDao.obterTipoSequencial(pontoCriticoSisAtributoPtcSatb).getConteudo()+"\" >");
										} else {
											tag.append(" >");
										}
										tag.append("<a href=\"javascript:aoClicarConsultar(document.form,"+ pontoCriticoPtc.getCodPtc()+")\">");
										tag.append(pontoCriticoSisAtributoPtcSatb.getInformacao());
										tag.append("</a>");
										//tag.append(pontoCriticoSisAtributoPtcSatb.getInformacao());
								
									} else if (!coluna.iGetGrupoAtributosLivres().getSisTipoExibicGrupoSteg().getCodSteg().equals(new Long(Input.IMAGEM))) {
										//se for do tipo imagem não faz nada, deixa em branco.
										//informacaoAtbdem = informacaoAtbdem + pontoCriticoSisAtributoPtcSatb.getSisAtributoSatb().getDescricaoSatb() +  "; ";
									
										//tag. append(" > "+pontoCriticoSisAtributoPtcSatb.getSisAtributoSatb().getDescricaoSatb() );
										tag.append(" > <a href=\"javascript:aoClicarConsultar(document.form,"+ pontoCriticoPtc.getCodPtc()+")\">");
										tag.append(pontoCriticoSisAtributoPtcSatb.getSisAtributoSatb().getDescricaoSatb());
										tag.append("</a>");
									}
								}
							}

					
						} else {
							if (coluna.iGetNome().equals("pontoCriticoCorPtccores")){
								String corRelogio = "Branco";
    							String descRelogio = "";
    							String[] relogioAtual = pontoCriticoDao.getRelogioNaData(pontoCriticoPtc, Data.getDataAtual());
								corRelogio = relogioAtual[0];
								descRelogio = relogioAtual[1];
								tag.append("><img src=\"" + request.getContextPath() + "/images/pc" + corRelogio + "1.png\" title=\"" + descRelogio + "\">");
							} else if (coluna.iGetNome().equals("acompRelatorioArel")){
								AcompRelatorioArel arel = pontoCriticoPtc.getAcompRelatorioArel();
								tag.append("> ");
								if (arel != null && arel.getTipoFuncAcompTpfa() != null) {
									tag.append(arel.getTipoFuncAcompTpfa().getLabelTpfa());
								} else {
									tag.append("");
								} 
							} else if ((coluna.iGetNome().equals("dataLimitePtc")) ||
									   (coluna.iGetNome().equals("dataInclusaoPtc"))  ||
									   (coluna.iGetNome().equals("dataUltManutencaoPtc"))  ||
									   (coluna.iGetNome().equals("dataIdentificacaoPtc"))  ||
									   (coluna.iGetNome().equals("dataSolucaoPtc")) ) {
								String retornoValor = coluna.iGetValor(pontoCriticoPtc);

								Date dataLimite = Data.parseDate(retornoValor);
								
								if (dataLimite != null){
							        String formato = "yyyyMMdd";
							        String strRetorno= null;
							        SimpleDateFormat formatter = new SimpleDateFormat(formato);
						            strRetorno = formatter.format((java.util.Date)dataLimite); 									
									
									tag.append(" sorttable_customkey= \""+ strRetorno  +"\"");
								}
								
								tag.append("> ");
								//tag.append(coluna.iGetValor(pontoCriticoPtc));
								tag.append("<a href=\"javascript:aoClicarConsultar(document.form,"+ pontoCriticoPtc.getCodPtc()+")\">");
								tag.append(retornoValor);
								tag.append("</a>");
								
								
								
							} else {
								
								tag.append("> ");
								//tag.append(coluna.iGetValor(pontoCriticoPtc));
								tag.append("<a href=\"javascript:aoClicarConsultar(document.form,"+ pontoCriticoPtc.getCodPtc()+")\">");
								tag.append(coluna.iGetValor(pontoCriticoPtc));
								tag.append("</a>");
							}
							
						}
						
						tag.append("</td>");
					
						out.println(tag.toString());
					}
			%></tr>
	<%
				}		
   			}
   		}
		%>
		</tbody>
	</table>
</div>	
<%
if (mostrarDeletados != null & mostrarDeletados.length() > 0){
	
	Collection todosPontosCriticos = pontoCriticoDao.getPontosCriticosComExcluidosOrdenadoDataLimite(itemEstrutura);
	
	Iterator itTodosPontosCriticos = todosPontosCriticos.iterator();
		
	String[] codigos = new String[todosPontosCriticos.size()];
	int i=0;
	
	while (itTodosPontosCriticos.hasNext()){		
		codigos[i] = ((PontoCriticoPtc) itTodosPontosCriticos.next()).getCodPtc().toString();
		i++;
	}
		
	List ldel = pontoCriticoDao.listaObjetoHistorico(null, null, new String[]{"3"}, codigos);
%>

<table width="100%" border="0" cellpadding="0" cellspacing="0">
	<tr>
		<td class="espacador" colspan="<%=lColunas.size() + 3%>">
			<img src="<%=request.getContextPath()%>/images/pixel.gif">
		</td>
	</tr>
	<tr class="linha_subtitulo">
		<b>Itens Excluídos</b>
	</tr>
	<tr>
		<td class="espacador" colspan="<%=lColunas.size() + 3%>">
			<img src="<%=request.getContextPath()%>/images/pixel.gif">
		</td>
	</tr>
</table>
<div>
	<table id="tabelaOrdenada" class="sortable" width="100%" border="0" cellpadding="0" cellspacing="0">
		<thead>
			<tr class="linha_subtitulo">
				<td class="sorttable_nosort" nowrap="nowrap" width="1%" valign="top">
					
				</td>
				<%
				/* desenha as colunas de uma estrutura */
				itColunasDel = lColunas.iterator();
				//int numColunasDel = 2;
				while (itColunasDel.hasNext()){
					coluna = (ObjetoEstrutura) itColunasDel.next();
				%>			
					<%=strCheckBox%>
					<td width="<%=coluna.iGetLargura()%>%" align="left">
					<%numColuna++;%>
					<%=coluna.iGetLabel()%>
					</td>
				<%
					strCheckBox = "";
				}
				%>
			</tr>
		</thead>

		<tbody id="corpo<%=numTabelas%>">
		<%
	    
	    if (ldel != null){
	    	Iterator itPontosCriticos = ldel.iterator();
	    	while (itPontosCriticos.hasNext()){
	    	
	    		HistoricoPontoCriticoPtc pontoCriticoPtc = (HistoricoPontoCriticoPtc) itPontosCriticos.next();
	    		
	    		if (pontoCriticoPtc.getIndExcluidoPtc() != null || pontoCriticoPtc.getIndExcluidoPtc().equals("S")){
	    			%><tr class="linha_subtitulo2">
	    				<td nowrap="nowrap" width="1%" valign="top" sorttable_customkey="0">
							<input type="checkbox" class="form_check_radio" name="excluirPontoCritico" value="<%=pontoCriticoPtc.getCodPtc()%>">
						</td>
					<%
	    			itColunasDel = lColunas.iterator();
					StringBuffer tagDel;
	    			
					while (itColunasDel.hasNext()){
						String informacaoAtbdem = "";
						tagDel = new StringBuffer();
						
						tagDel.append ("<td valign=\"top\" align=\"left\" ");
						
						coluna = (ObjetoEstrutura) itColunasDel.next();
						
						
						if (coluna.iGetGrupoAtributosLivres() != null)  {
						
							Iterator itPontoCriticoSisAtributoPtcSatbs = pontoCriticoPtc.getPontoCriticoSisAtributoPtcSatbs().iterator();
							
							while (itPontoCriticoSisAtributoPtcSatbs.hasNext()) {
							
								PontoCriticoSisAtributoPtcSatb pontoCriticoSisAtributoPtcSatb = (PontoCriticoSisAtributoPtcSatb) itPontoCriticoSisAtributoPtcSatbs.next();
							
								if (pontoCriticoSisAtributoPtcSatb.getSisAtributoSatb().getSisGrupoAtributoSga().equals(coluna.iGetGrupoAtributosLivres())){
								
									if (coluna.iGetGrupoAtributosLivres().getSisTipoExibicGrupoSteg().getCodSteg().equals(new Long(Input.TEXT)) ||
									 	coluna.iGetGrupoAtributosLivres().getSisTipoExibicGrupoSteg().getCodSteg().equals(new Long(Input.TEXTAREA)) ||
									 	coluna.iGetGrupoAtributosLivres().getSisTipoExibicGrupoSteg().getCodSteg().equals(new Long(Input.MULTITEXTO)) ||
									 	coluna.iGetGrupoAtributosLivres().getSisTipoExibicGrupoSteg().getCodSteg().equals(new Long(Input.VALIDACAO)) ) {
									 
										//Se for um atributo livre do tipo ID
										SisAtributoSatb sisAtributo = pontoCriticoSisAtributoPtcSatb.getSisAtributoSatb();
										
										if (sisAtributo.isAtributoAutoIcremental() || sisAtributo.isAtributoContemMascara()) {
											tagDel.append(" sorttable_customkey=\""+pontoCriticoDao.obterTipoSequencial(pontoCriticoSisAtributoPtcSatb).getConteudo()+"\" >");
										} else {
											tagDel.append(" >");
										}
										
										tagDel.append(pontoCriticoSisAtributoPtcSatb.getInformacao());
									
									} else if (!coluna.iGetGrupoAtributosLivres().getSisTipoExibicGrupoSteg().getCodSteg().equals(new Long(Input.IMAGEM))) {
										
										tagDel.append(" > "+pontoCriticoSisAtributoPtcSatb.getSisAtributoSatb().getDescricaoSatb() );
									}
								}
							}

						} else 	{	
							if (coluna.iGetNome().equals("pontoCriticoCorPtccores")){
								String corRelogio = "Branco";
		    					String descRelogio = "";
		    					String[] relogioAtual = pontoCriticoDao.getRelogioNaData(pontoCriticoPtc, Data.getDataAtual());
								corRelogio = relogioAtual[0];
								descRelogio = relogioAtual[1];
								tagDel.append("<img src=\"" + request.getContextPath() + "/images/pc" + corRelogio + "1.png\" title=\"" + descRelogio + "\">");
							} else if (coluna.iGetNome().equals("acompRelatorioArel")){
								AcompRelatorioArel arel = pontoCriticoPtc.getAcompRelatorioArel();
								tagDel.append("> ");
								if (arel != null && arel.getTipoFuncAcompTpfa() != null) {
									tagDel.append(arel.getTipoFuncAcompTpfa().getLabelTpfa());
								} else {
									tagDel.append("");
								}
							} else {
								tagDel.append("> ");
								tagDel.append(coluna.iGetValor(pontoCriticoPtc));
							}
						}
						tagDel.append("</td>");
						
						out.println(tagDel.toString());		
					}
			%></tr><%
				}		
	   		}
	   	}
	  
		%>
		</tbody>
	</table>
</div>
<%} %>

						
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
</form></div>
<br>
</div>

</body>
<% /* Controla o estado do Menu (aberto ou fechado) */%>
<%@ include file="/include/estadoMenu.jsp"%>
<%@ include file="/include/mensagemAlert.jsp" %>
</html>
