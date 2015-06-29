
<jsp:directive.page import="ecar.popup.PopUpRegDemanda"/>
<jsp:directive.page import="ecar.pojo.UsuarioUsu"/>
<jsp:directive.page import="ecar.dao.VisaoDao"/>
<jsp:directive.page import="java.util.Iterator"/>
<jsp:directive.page import="ecar.pojo.VisaoDemandasVisDem"/><%@ taglib uri="/WEB-INF/ecar-util.tld" prefix="util" %>
<%@ include file="/frm_global.jsp"%>
<%@ include file="/login/validaAcesso.jsp"%>
<%@ page import="ecar.popup.PopUpPesquisa" %>
<%@ page import="java.util.Enumeration" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.lang.Math" %>


<%@page import="ecar.pojo.ConfiguracaoCfg"%>
<%@page import="ecar.dao.ConfiguracaoDao"%>
<%@page import="ecar.pojo.ObjetoDemanda"%>
<%@page import="ecar.dao.AtributoDemandaDao"%>
<%@page import="ecar.dao.RegDemandaDao"%>
<%@page import="ecar.pojo.RegDemandaRegd"%>
<%@page import="ecar.pojo.DemAtributoDema"%>
<%@page import="ecar.taglib.util.Input"%><html lang="pt-br">
<head>
<%@ include file="../../include/meta.jsp"%>
<%@ include file="/titulo.jsp"%>
<link rel="stylesheet" href="../../css/style_<%=configuracaoCfg.getEstilo().getNome()%>.css" type="text/css">

<script language="javascript" src="../../js/focoInicial.js"></script>
<script language="javascript" src="<%=_pathEcar%>/js/destaqueLinha.js"></script>
<script language="javascript" src="<%=_pathEcar%>/js/tableSort.js"></script>
<SCRIPT language="Javascript">


function onLoad(form) {
	form.reset();
	focoInicial(form);
}

function pesquisar(form){	
	document.form.action="popup_pesquisaRegDemanda.jsp?hidAcao=pesquisar";	 
	document.form.submit();
}

function aoClicarPesquisar(form){
	document.form.action="popup_pesquisaRegDemanda.jsp?hidAcao=pesquisar";
	document.form.submit();
}

function aoClicarAdicionar(form, funcaoGetDadosPesquisa){
	var selecionado = false;
	var codigoSelecionado = "";
    if (typeof funcaoGetDadosPesquisa == "undefined") funcaoGetDadosPesquisa = "getDadosPesquisa";
	
	var x = document.getElementsByName("hidOpcoes");
	if(x.length > 1){
		for (i = 0; i < form.hidOpcoes.length; i++) {
			if (form.hidOpcoes[i].checked == true) {
				codigoSelecionado += form.hidOpcoes[i].value;
				selecionado = true;
			}
		}
	}
	else {
		if (form.hidOpcoes.checked == true){
			codigoSelecionado += form.hidOpcoes.value;
			selecionado = true;
		}
	}
	
	if (selecionado) {
		eval('window.opener.' + funcaoGetDadosPesquisa)(codigoSelecionado, "Teste");
		window.close();
	} else {
		alert('Selecione uma opção!');
	}
}

function aoClicarCancelar(form){
	window.close();
}
</SCRIPT>

</head>

<!-- sempre colocar o foco inicial no primeiro campo -->
<body onload="onLoad(document.form);" class="corpo_popup">

<form name="form" method="post" action="popup_pesquisaRegDemanda.jsp?hidAcao=navegar" onsubmit="pesquisar(document.form);">
	
<%
	try { 
		/*
		 * Formulario template de pesquisa
		 * Para cada tela que precisa exibir um popup de pesquisa, criar uma classe
		 * que implementa a iterface PopUpPesquisa
		 */
		//ConfiguracaoCfg configuracaoCfg = new ConfiguracaoDao(request).getConfiguracao(); 
		VisaoDemandasVisDem visaoDemandasVisDem = configuracaoCfg.getVisaoDemandasVisDem();
		/* define a quantidade de itens exibidos por pagina */
		final int ITENS_PAGINA = configuracaoCfg.getNumRegistros().intValue();//_msg.getQtdeItensPaginaPesquisa("popup.pesquisa.itensPagina");
		
		//Lista com as colunas configuradas para a visão
		List lColunas = new ArrayList();
		int numColunas = 0;
		ObjetoDemanda coluna;
		Iterator itColunas;
		
		//Dao de visão
		VisaoDao visaoDao = new VisaoDao(request);
		
		//Dao de Atributo na demanda
		AtributoDemandaDao atributoDemandaDao = new AtributoDemandaDao(request);
		
		boolean usuarioPermissaoVisaoConfigurada = true;
		boolean visaoAssociacaoDemandasConfigurada = true;
		boolean apresentouDemanda = false;
		if (configuracaoCfg.getVisaoDemandasVisDem() != null) {
			if (visaoDao.getVisoesGrupoAcesso(seguranca.getUsuario(), true, request).contains(configuracaoCfg.getVisaoDemandasVisDem())){
				lColunas = atributoDemandaDao.getAtributosDemandaVisaoAtivosOrdenadosPorSequenciaTelaListaDemandas(configuracaoCfg.getVisaoDemandasVisDem().getCodVisao());
				numColunas = lColunas.size();	
			} else {
				usuarioPermissaoVisaoConfigurada = false;
				//Usuário sem permissão de acesso à visão. Favor entrar em contato com o administrador do sistema.
			}	
		} else {
			visaoAssociacaoDemandasConfigurada = false;
			//Falta configurar uma visão. Favor entrar em contato com o administrador do sistema.
		}
		
		/* lista que contém o resultado da pesquisa */
		List lista;
		
		/* instancia o pojo que vai ser utilizado na pesquisa */
		Class c = Class.forName(Pagina.getParamStr(request, "hidPojo"));
//		PopUpPesquisa p = (PopUpPesquisa) c.newInstance();
		PopUpPesquisa p = new PopUpRegDemanda(request);

		/* controles de página */
		int hidNumPagina = Pagina.getParamInt(request, "hidNumPagina");
		int hidTotPagina = Pagina.getParamInt(request, "hidTotPagina");

		/*
		 * Acao = nada - entrou na pagina; "pesquisar" - deve pesquisar; "navegar" - deve navegar
		 */
		String hidAcao = Pagina.getParamStr(request, "hidAcao");
		
		/* persiste o pojo que deve ser pesquisado */
		String hidPojo = Pagina.getParamStr(request, "hidPojo");

		/* pega os códigos dos critérios que já estão cadastrados para que não apareçam na pesquisa */
		String jaIncluidos = Pagina.getParamStr(request, "jaIncluidos");
		
		/* funcao que vai receber os retorno da pesquisa ao clicar adicionar */
		String hidFuncao = Pagina.getParamStr(request, "hidFuncao");
		
		
		if ("pesquisar".equalsIgnoreCase(hidAcao) || "".equals(hidAcao.trim())) {
			session.removeAttribute("lista");
			session.setAttribute("gruposAcesso", seguranca.getGruposAcesso());
			session.setAttribute("usuario", seguranca.getUsuario());
			session.setAttribute("visaoDemandasVisDem", visaoDemandasVisDem);
			p.setArgPesquisa(request);
			lista = p.pesquisar();
			session.setAttribute("lista", lista);
			hidNumPagina = 0;
			hidTotPagina = 0;
		} 
		else if ("navegar".equalsIgnoreCase(hidAcao))
			lista = (List) session.getAttribute("lista");
		else
			lista = new ArrayList();
		
		Enumeration e = request.getParameterNames();
		String hidden = new String();
		
		/* Recupera os valores passados pelo request em campos hidden */
		/*    excluindo os campos fixos do popup_pesquisa, onde foi adotado que seus */
		/*    nomes iniciem com "hid" diferenciando dos demais que venham do request */
		/*    evitando de duplicar campos hidden com mesmo nome. */
		while (e.hasMoreElements()){
			hidden = (String) e.nextElement();
			
			if(!hidden.startsWith("hid")){
%>
				<input type="hidden" name="<%=hidden%>" value="<%=Pagina.getParamStr(request, hidden)%>">
<%
			}
		}
%>		

		<input type="hidden" name="hidPojo" value="<%=hidPojo%>">
		<input type="hidden" name="hidAcao" value="<%=hidAcao%>">
		<input type="hidden" name="hidFuncao" value="<%=hidFuncao%>">

		<h1>Pesquisa de <%=p.getTitulo()%></h1>
		<table class="form" width="100%">
			<tr>
				<td class="label">Argumento de Pesquisa:</td>
				<td>
					<input type="text" name="hidArg" size="20" value="<%=Pagina.getParamStr(request, "hidArg")  %>">
					<input type="button" name="btnOk" class="botao" value="Ok" onclick="pesquisar(document.form);"> 					
				</td>
				<td>
					
				</td>
			</tr>
			<tr>
				
				<td class="label">Pesquisar em:</td>				
				<td class=texto>
<%
		String[] opcoes = p.getPesquisarEm();
		int k = 0;
		short contadorOpcoes = 0;
		String[] pesquisarEm = {"0"};

		if (request.getParameterValues("hidPesquisarEm")!=null) { //Opções de pesquisa selecionada)
			pesquisarEm = request.getParameterValues("hidPesquisarEm");
		} 
						
		for (int i = 0; i < opcoes.length; i++) {
			if (pesquisarEm.length > k  && pesquisarEm[k].equals(i+"") ) { //para comparar o valor selecionado com o da lista
				out.println("<input type=\"checkbox\" class=\"form_check_radio\" name=\"hidPesquisarEm\" value=\"" + i + "\" checked >" + opcoes[i] + "&nbsp;&nbsp;");
				k++;
			} else {
				out.println("<input type=\"checkbox\" class=\"form_check_radio\" name=\"hidPesquisarEm\" value=\"" + i + "\">" + opcoes[i] + "&nbsp;&nbsp;");
			}
			contadorOpcoes ++;
		}
						
%>
				</td>
			</tr>
			<tr></td></td></td></tr>
			<tr></td></td></td></tr>
			<tr></td></td></td></tr>
			<tr>				
				<td class="label" valign="top">Visão:</td>			
				<td class="texto">
					<%=visaoDemandasVisDem != null? visaoDemandasVisDem.getDescricaoVisao() : ""%>
				</td>			
			</tr>
		</table>
		<h2>Resultado:</h2>
		<%
		if (!visaoAssociacaoDemandasConfigurada){
			%>
			<table width="100%">
			<tr>		
				<td valign="top" align="center">
					<h1><%=_msg.getMensagem("itemEstrutura.regDemanda.associacao.semVisaoConfigurada")%></h1>
				</td>
			</tr>				
			</table>
			<%
		} else if (!usuarioPermissaoVisaoConfigurada){
			%>
			<table width="100%">
			<tr>		
				<td valign="top" align="center">
					<h1><%=_msg.getMensagem("itemEstrutura.regDemanda.associacao.usuarioSemAcessoVisaoConfigurada")%></h1>
				</td>
			</tr>				
			</table>
			<%
		} else {
			
			List listaDemandasVisaoConfigurada = new RegDemandaDao(request).getDemandasComPermissaoNosGruposAcessosUsuario(seguranca.getUsuario(), configuracaoCfg.getVisaoDemandasVisDem(), false);
			
			%>
			
			<div id="subconteudo">
			 
			<%
			String clCampo = "";
			String clOrdem = "";
			boolean classifica = false;
			
			if (Pagina.getParam(request, "clCampo") != null) {
				clCampo = Pagina.getParam(request, "clCampo");
			} else {
				clCampo = "codRegd";
			}
			
			if (Pagina.getParam(request, "clOrdem") != null) {
				clOrdem = Pagina.getParam(request, "clOrdem");
			} else {
				clOrdem = "asc";
			}
			
			//Campos de ordenação da listagem%>
			<input type="hidden" name="clCampo" value="<%=clCampo%>">
			<input type="hidden" name="clOrdem" value="<%=clOrdem%>">
			<input type="hidden" name="refazOrdenacao" value="">
		
			<!-- ############### Listagem  ################### -->
			<table width="100%" border="0" cellpadding="0" cellspacing="0">
				<tr><td class="espacador" colspan=<%=numColunas+1%>><img src="../../images/pixel.gif"></td></tr>
				<tr class="linha_subtitulo">
			
				<%
						/* desenha as colunas de uma estrutura */
						itColunas = lColunas.iterator();
						int numColuna = 1;
						String strColunaVazia = "<td width=\"1%\"> &nbsp;</td> <!-- Coluna para colocar icone para listagem -->";
						
						while (itColunas.hasNext()){
							coluna = (ObjetoDemanda) itColunas.next();
					%>
						<%=strColunaVazia%>			
						<td width="<%=coluna.iGetLargura()%>%" align="left">
						<a href="#" onclick="this.blur(); return sortTable('corpo1',  <%=numColuna%>, false);">											
						<%numColuna++;%>
						<%=coluna.iGetLabel()%>
						</a>
						</td>
					<%
						strColunaVazia = "";
						}
					%>
				
				</tr>
				<tr><td class="espacador" colspan=<%=numColunas+1%>><img src="../../images/pixel.gif"></td></tr>
			<%
				
				RegDemandaDao regDemandaDao = new RegDemandaDao(request);
				UsuarioUsu usuario = ((ecar.login.SegurancaECAR)session.getAttribute("seguranca")).getUsuario();
								
				if(lista != null && lista.size() > 0){ 
				
				%>
					<tbody id="corpo1">
				<%
				
					int cont = 0;
					String cor = new String(); 
					Iterator it = lista.iterator();
					configuracaoCfg.getVisaoDemandasVisDem().getVisaoSituacaoDemandas().size();
					request.getSession().setAttribute(VisaoDemandasVisDem.VISAO_SELECIONADA, configuracaoCfg.getVisaoDemandasVisDem());
					while(it.hasNext()){
						if (cont % 2 == 0){
							cor = "cor_nao";
						} else {
							cor = "cor_sim"; 
						}
						
						RegDemandaRegd regDemanda = (RegDemandaRegd) it.next();
						
						boolean listar = true;
						
						if(jaIncluidos.length() > 0){
							String[] codigosIncluidos = jaIncluidos.split("\\|");
							for(int j = 0; j < codigosIncluidos.length; j++){
								if(regDemanda.getCodRegd().toString().equals(codigosIncluidos[j])){
									listar = false;
									break;	
								}
							}
						}
						
						if(listar){						
			%>
						<tr class="<%=cor%>" onmouseover="javascript:destacaLinha(this,'over','')" onmouseout="javascript: destacaLinha(this,'out','<%=cor%>')" onClick="javascript: destacaLinha(this,'click','<%=cor%>')" > 
							<td width="4%">
								<input type="radio" class="form_check_radio" name="hidOpcoes" value="<%=regDemanda.getCodRegd()%>">
							</td>
							
							<%
								apresentouDemanda = true;
								itColunas = lColunas.iterator();
							
								while (itColunas.hasNext()){
								
									coluna = (ObjetoDemanda) itColunas.next();
							%>
							
							<td valign="top" align="left" width="<%=coluna.iGetLargura()%>%">
								<%
								String informacaoAtbdem = "";
								
								if (coluna.iGetGrupoAtributosLivres() != null)  {
										Iterator itRegDem =  regDemanda.getDemAtributoDemas().iterator();
										while (itRegDem.hasNext()) {
											DemAtributoDema demAtributoDema = (DemAtributoDema) itRegDem.next();
											
											if (demAtributoDema.getSisAtributoSatb().getSisGrupoAtributoSga().equals(coluna.iGetGrupoAtributosLivres())){
												if (coluna.iGetGrupoAtributosLivres().getSisTipoExibicGrupoSteg().getCodSteg().equals(new Long(Input.TEXT)) ||
												 	coluna.iGetGrupoAtributosLivres().getSisTipoExibicGrupoSteg().getCodSteg().equals(new Long(Input.TEXTAREA)) ||
												 	coluna.iGetGrupoAtributosLivres().getSisTipoExibicGrupoSteg().getCodSteg().equals(new Long(Input.MULTITEXTO)) ||
												 	coluna.iGetGrupoAtributosLivres().getSisTipoExibicGrupoSteg().getCodSteg().equals(new Long(Input.VALIDACAO)) ) {
												 
													informacaoAtbdem = informacaoAtbdem + demAtributoDema.getInformacao() +  configuracaoCfg.getSeparadorCampoMultivalor();
												
												} else if (!coluna.iGetGrupoAtributosLivres().getSisTipoExibicGrupoSteg().getCodSteg().equals(new Long(Input.IMAGEM))) {
													//se for do tipo imagem não faz nada, deixa em branco.
													informacaoAtbdem = informacaoAtbdem + demAtributoDema.getSisAtributoSatb().getDescricaoSatb() +  "; ";
												} 
											} 
										}
										if (informacaoAtbdem.length() > 0){
											informacaoAtbdem = informacaoAtbdem.substring(0, informacaoAtbdem.length() - 2); 
										}
										out.println(informacaoAtbdem);

									} else {
										
										if (coluna.iGetNome().equals("localDemandaLdems")){
											out.println(coluna.iGetValor(regDemanda));
										}else{
										
											String valor;
											
											if (coluna.iGetValor(regDemanda).equalsIgnoreCase("S")){
												
												valor = "Sim";
											}
											else if (coluna.iGetValor(regDemanda).equalsIgnoreCase("N")){
												valor = "Não";
											}
											else{
												valor = coluna.iGetValor(regDemanda);
											}
											out.println(valor);
										}
									}
								%>
								
								
							</td>	
							<%
								}
							%>			
						</tr>	
			<%
							}
						}
			%>
					</tbody>
					<%}%>
				</table>
			<%}%>
<%
		if (apresentouDemanda) { 
%>			<center>
			<input type="button" name="btnAdicionar" class="botao" value="Adicionar" onclick="aoClicarAdicionar(document.form, '<%=hidFuncao%>');">
			<input type="button" name="btnCancelar" class="botao" value="Cancelar" onclick="aoClicarCancelar(document.form);">
			</center>
<%
		}else{
			if("pesquisar".equalsIgnoreCase(hidAcao)){
%>
			<table>
				<tr><td class="texto">
					<b>Nenhum registro foi encontrado para os critérios de pesquisa especificados</b>
				</td></tr>
			</table>
<%
			}
		}
	 } catch (ECARException e) {
		org.apache.log4j.Logger.getLogger(this.getClass()).error(e);
		Mensagem.alert(_jspx_page_context, _msg.getMensagem("erro.exception"));
%>
		<script language="javascript">
		aoClicarCancelar(document.form);
		</script>
<%
	 }
%>	
	
</form>
</body>
</html>
