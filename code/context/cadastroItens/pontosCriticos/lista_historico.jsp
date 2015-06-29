<jsp:directive.page import="ecar.util.Dominios" />
<jsp:directive.page import="ecar.dao.EstruturaDao" />
<jsp:directive.page import="ecar.pojo.ObjetoEstrutura" />
<jsp:directive.page import="java.util.Collections" />
<jsp:directive.page import="ecar.pojo.AcompRelatorioArel" />
<jsp:directive.page import="ecar.pojo.PontoCriticoSisAtributoPtcSatb" />
<jsp:directive.page import="ecar.taglib.util.Input" />
<%@ include file="/login/validaAcesso.jsp"%>
<%@ include file="/frm_global.jsp"%>

<%@ page import="ecar.pojo.ItemEstruturaIett"%>
<%@ page import="ecar.pojo.PontoCriticoPtc"%>
<%@ page import="ecar.pojo.PontocriticoCorPtccor"%>
<%@ page import="ecar.dao.PontoCriticoDao"%>
<%@ page import="ecar.dao.ItemEstruturaDao"%>
<%@ page import="ecar.pojo.EstruturaFuncaoEttf"%>
<%@ page import="ecar.dao.EstruturaFuncaoDao"%>
<%@ page import="ecar.pojo.ConfigMailCfgm"%>
<%@ page import="ecar.permissao.ValidaPermissao"%>
<%@ page import="ecar.dao.ConfigMailCfgmDAO"%>

<%@ page import="comum.util.Util"%>

<%@ page import="java.util.Iterator"%>
<%@ page import="java.util.Collection"%>
<%@ page import="java.util.List"%>
<%@ page import="comum.util.Data"%>
<%@ page import="java.util.Date"%>
<%@ page import="ecar.pojo.ConfiguracaoCfg"%>
<%@ page import="ecar.dao.ConfiguracaoDao"%>
<%@ page import="ecar.pojo.SisGrupoAtributoSga"%>

<%@ taglib uri="/WEB-INF/ecar-util.tld" prefix="util"%>

<%@page import="ecar.pojo.historico.HistoricoPontoCriticoPtc"%>
<%@page import="ecar.pojo.HistoricoConfig"%>
<%@page import="ecar.dao.HistoricoDao"%>
<%@page import="java.util.HashSet"%>
<%@page import="ecar.pojo.historico.HistoricoXml"%>
<%@page import="ecar.pojo.UsuarioUsu"%><html lang="pt-br">
<head>
<%@ include file="/include/meta.jsp"%>
<%@ include file="/titulo.jsp"%>
<link rel="stylesheet" href="<%=_pathEcar%>/css/style_<%=configuracaoCfg.getEstilo().getNome()%>.css"
	type="text/css">
<script language="javascript" src="../../js/focoInicial.js"
	type="text/javascript"></script>
<script language="javascript"
	src="../../js/frmPadraoItemEstrut.js" type="text/javascript"></script>
<script language="javascript" src="../../js/validacoes.js"
	type="text/javascript"></script>
<script language="javascript" src="../../js/datas.js"></script>
<script language="javascript" src="../../js/tableSort.js"></script>
<script language="javascript" src="<%=_pathEcar%>/js/destaqueLinha.js"></script>
<script language="javascript" src="<%=_pathEcar%>/js/listaItensCadastro.js" type="text/javascript"></script>		 
<script language="javascript" src="<%=_pathEcar%>/js/jquery.js" type="text/javascript"></script>
<script type="text/javascript">jQuery.noConflict();</script>		
<script language="javascript" src="<%=_pathEcar%>/js/cookie.js"></script>
<script language="javascript">

function inicializarTipos(tipos){
	for(i= 0; i < tipos.length; i++){
		tipos[i].checked = true;
	}
}

function aoClicarComparar(form){
	if (validarDoisChecksMarcados(form)){
		ultimoIdLinhaDetalhado = document.getElementById('ultimoIdLinhaDetalhado').value;
		document.form.action = "frm_con_historico.jsp?ultimoIdLinhaDetalhado=" + ultimoIdLinhaDetalhado;
		document.form.submit();
	}
} 

function aoClicarConsultar(form){
	ultimoIdLinhaDetalhado = document.getElementById('ultimoIdLinhaDetalhado').value;
	document.form.action = "lista_historico.jsp?ultimoIdLinhaDetalhado=" + ultimoIdLinhaDetalhado;
	document.form.submit();
} 

function aoClicarVoltar(form){
	ultimoIdLinhaDetalhado = document.getElementById('ultimoIdLinhaDetalhado').value;
	document.form.action = "lista.jsp?ultimoIdLinhaDetalhado=" + ultimoIdLinhaDetalhado;
	document.form.submit();
}

function validarDoisChecksMarcados(form){
	var numChecks = 0;
	var count = 0;
    var nomeCheckBox = "codHist";
    var historico = "";
    numChecks = verificaChecks(form, nomeCheckBox);
    
    if (numChecks > 0) {
		if(numChecks > 1){
			for(i = 0; i < eval('form.' + nomeCheckBox + '.length'); i++)
				if(eval('form.' + nomeCheckBox + '[i]').checked){
					if (historico == "")
						historico = form.codPtc[i].value;
					else
						if (historico != form.codPtc[i].value){
							alert("Selecione apenas dois registros do mesmo histórico para comparação.");
							return false;
						}
					count++;
				}
		} else {
			count++
		}
	}

    if(count == 0){
		alert("Não existe registros para comparação.");
		return false;
	}

	if(count != 2){
		alert("Selecione apenas dois registros do mesmo histórico para comparação.");
		return false;
	} else {
		return true;
	}
}

</script>
</head>

<%@ include file="/cabecalho.jsp"%>
<%@ include file="/divmenu.jsp"%>

<body>
<div id="conteudo">
<%
	try {
		ItemEstruturaDao itemEstruturaDao = new ItemEstruturaDao(request);
		EstruturaDao estruturaDao = new EstruturaDao(request);
		EstruturaFuncaoEttf estruturaFuncao = new EstruturaFuncaoEttf();
		EstruturaFuncaoDao estruturaFuncaoDao = new EstruturaFuncaoDao(request);
		ItemEstruturaIett itemEstrutura = (ItemEstruturaIett) itemEstruturaDao.buscar(ItemEstruturaIett.class, Long.valueOf(Pagina.getParamStr(request,
		        "codIett")));
		ValidaPermissao validaPermissao = new ValidaPermissao();
		if (!validaPermissao.permissaoConsultaIETT(Long.valueOf(Pagina.getParamStr(request, "codIett")), seguranca)) {
			response.sendRedirect(request.getContextPath() + "/acessoIndevido.jsp");
		}
		Iterator lista;
		String[] codigos = Pagina.getParamArray(request, "excluirPontoCritico");
		String[] tipos = Pagina.getParamArray(request, "tipos");
		
		String dataInicio = Pagina.getParamStr(request, "dtInicio");
		if (dataInicio == null || dataInicio.equals(""))
			dataInicio = "";
			//dataInicio = Data.parseDate(Data.addDias(-30, Data.getDataAtual()));
		
		String dataFim = Pagina.getParamStr(request, "dtFim");
		if (dataFim == null || dataFim.equals(""))
			dataFim = "";
			//dataFim = Data.parseDate(Data.getDataAtual());
		
		String primeira = Pagina.getParam(request, "primeira");
		String cincluir = "";
		String calterar = "";
		String cexcluir = "";
		
		if (tipos != null)
		for(int i = 0; i < tipos.length; i++){
			if ("1".equals(tipos[i]))
				cincluir = "checked";
			if ("2".equals(tipos[i]))
				calterar = "checked";
			if ("3".equals(tipos[i]))
				cexcluir = "checked";
		} 
				
		PontoCriticoDao pontoCriticoDao = new PontoCriticoDao(request);

		String codAba = Pagina.getParamStr(request, "codAba");
		//*******************************************************
		Boolean abaPontoCriticoDeAcompanhamento = Boolean.FALSE;
		String tipoSelecao = "";
		//*******************************************************
		estruturaFuncao = (EstruturaFuncaoEttf) estruturaFuncaoDao.getLabelFuncao(itemEstrutura.getEstruturaEtt(), Long.valueOf(codAba));
		HistoricoDao historicoDao = new HistoricoDao(null);
		String imagem = "";
		List histCor = historicoDao.listar(HistoricoConfig.class, new String[] { "codHistorico", "asc" });
		List lColunas = estruturaDao.getAtributosAcessoEstrutura(estruturaFuncao.getEstruturaEtt(), estruturaFuncao.getFuncaoFun());
		Iterator itColunas = null;
		Iterator itColunasDel = null;
		String strCheckBox = "<td width=\"1%\"><input type=\"checkbox\" class=\"form_check_radio\" name=\"todosPontosCriticos\" onclick=\"javascript:selectAll(document.form, 'todosPontosCriticos', 'excluirPontoCritico');\"></td>";
		String strColunaVazia = "<td width=\"1%\"> &nbsp;</td> <!-- Coluna para colocar icone para listagem -->";
		int numTabelas = 1;
		ObjetoEstrutura coluna = null;
		
		%>
		
		 
<div id="tituloTelaCadastro">
	<!-- TITULO -->
	<%@ include file="/titulo_tela.jsp"%> 
</div>
<%
boolean ehTelaListagem = false;
 
EstruturaEtt estruturaEttSelecionada = null;
EstruturaDao estruturaDaoArvoreItens = new EstruturaDao(request);


ConfiguracaoCfg conf = new ecar.dao.ConfiguracaoDao(request).getConfiguracao();

if(conf.getIndExibirArvoreNavegacaoCfg() != null && conf.getIndExibirArvoreNavegacaoCfg().equals("S")){
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
			if(conf.getIndExibirArvoreNavegacaoCfg() != null && conf.getIndExibirArvoreNavegacaoCfg().equals("S")){ %>
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
					if (conf.getIndExibirArvoreNavegacaoCfg() != null && conf.getIndExibirArvoreNavegacaoCfg().equals("S")) {
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
							ultimoIdLinhaExpandido="<%=Pagina.getParamStr(request,"ultimoIdLinhaExpandido")%>"
							/> 
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
						<form name="form" method="post">
							<input type="hidden" name="codIett" value="<%=itemEstrutura.getCodIett()%>"> 
							<input type="hidden" name="codAba" value="<%=codAba%>">
							<input type="hidden" name="primeira" value="ok">
							<input type="hidden" name="hidAcao" value="">
							
							<%if (codigos != null) for(int i = 0; i <  codigos.length; i++){%> 
								<input type="hidden" name="excluirPontoCritico" value="<%=codigos[i]%>">
							<%}%>
						
							
							
						<table width="100%" border="0" cellpadding="0" cellspacing="0">
							<tr><td class="espacador"><img src="<%=_pathEcar%>/images/pixel.gif" alt=""></td></tr>
							<tr class="linha_titulo" align="right">
								<td>
								Ciclo de
								
								<input type="text" name="dtInicio" size="13" maxlength="10" value="<%=dataInicio %>" onkeyup="mascaraData(event, this);" onblur="validaData(this.form.dtInicio, true, true, false);">
									<img class="posicao" title="Selecione a data" src="../../images/icone_calendar.gif" onclick="open_calendar('data', document.forms[0].dtInicio, '');">
								até 
								<input type="text" name="dtFim" size="13" maxlength="10" value="<%=dataFim %>" onkeyup="mascaraData(event, this);" onblur="validaData(this.form.dtFim, true, true, false);">
									<img class="posicao" title="Selecione a data" src="../../images/icone_calendar.gif" onclick="open_calendar('data', document.forms[0].dtFim, '');"> 
										  <input type="checkbox" name="tipos" value="1" <%=cincluir%> /> Incluídos 
										  <input type="checkbox" name="tipos" value="2" <%=calterar%> /> Alterados 
										  <input type="checkbox" name="tipos" value="3" <%=cexcluir%> /> Excluídos
								</td>
							</tr>
						</table>
						<%
							if(primeira == null || "".equals(primeira)){	
						%>
						
						<script type="text/javascript">
						
							inicializarTipos(document.forms[0].tipos);
						
						</script>
						
						<%	} %>
						<table width="100%" border="0" cellpadding="0" cellspacing="0">
							<tr><td class="espacador"><img src="<%=_pathEcar%>/images/pixel.gif" alt=""></td></tr>
							<tr class="linha_titulo" align="right">
								<td>
									<input type="button" class="botao" value="Comparar" onclick="javascript:aoClicarComparar(form);"> 
									<input type="button" class="botao" value="Consultar" onclick="javascript:aoClicarConsultar(form);">
									<input type="button" class="botao" value="Voltar" onclick="javascript:aoClicarVoltar(form);">
								</td>
							</tr>
						</table>
						<table width="100%" border="0" cellpadding="0" cellspacing="0">
							<tr>
								<td class="espacador" colspan="<%=lColunas.size() + 3%>">
								<img src="<%=request.getContextPath()%>/images/pixel.gif"></td>
							</tr>
						
							<tr class="linha_subtitulo">
							
								<%
								/* desenha as colunas de uma estrutura */
									itColunas = lColunas.iterator();
									int numColuna = 0;
								while (itColunas.hasNext()) {
									coluna = (ObjetoEstrutura) itColunas.next();
								%>
								<%=strColunaVazia%>
								
								<td width="<%=coluna.iGetLargura()%>%" align="left">
									
								<%
									numColuna++;
								%><%=coluna.iGetLabel()%></td>
								<%
									strColunaVazia = strCheckBox = "";
								}
								%>
								
							</tr>
							<tr>
							<td class="espacador" colspan="<%=lColunas.size() + 3%>">
								<img src="<%=request.getContextPath()%>/images/pixel.gif">
							</td>
							</tr>
							<tbody id="corpo<%=numTabelas%>">
							<%
							int i = 0;
							if (codigos != null){
								while (codigos.length > i) {
							
									PontoCriticoPtc pontoCriticoPtc = (PontoCriticoPtc)pontoCriticoDao.buscar(PontoCriticoPtc.class, Long.valueOf(codigos[i]));
									%>
								<tr>
								<td>
								<br>
								</td>
								</tr>			
								<tr >
								<td width="1%"> &nbsp;</td> <!-- Coluna para colocar icone para listagem -->
								<%	itColunas = lColunas.iterator();
									while (itColunas.hasNext()) {
									coluna = (ObjetoEstrutura) itColunas.next();
									%>
									<td valign="top" align="left" width="<%=coluna.iGetLargura()%>%">
									
									<%
									
									String informacaoAtbdem = "";
									if (coluna.iGetGrupoAtributosLivres() != null) {
										Iterator itPontoCriticoSisAtributoPtcSatbs = pontoCriticoPtc.getPontoCriticoSisAtributoPtcSatbs().iterator();
										while (itPontoCriticoSisAtributoPtcSatbs.hasNext()) {
											PontoCriticoSisAtributoPtcSatb pontoCriticoSisAtributoPtcSatb = (PontoCriticoSisAtributoPtcSatb) itPontoCriticoSisAtributoPtcSatbs.next();
											if (pontoCriticoSisAtributoPtcSatb.getSisAtributoSatb().getSisGrupoAtributoSga().equals(coluna.iGetGrupoAtributosLivres())) {
												if (coluna.iGetGrupoAtributosLivres().getSisTipoExibicGrupoSteg().getCodSteg().equals(new Long(Input.TEXT))
												        || coluna.iGetGrupoAtributosLivres().getSisTipoExibicGrupoSteg().getCodSteg().equals(new Long(Input.TEXTAREA))
												        || coluna.iGetGrupoAtributosLivres().getSisTipoExibicGrupoSteg().getCodSteg().equals(new Long(Input.MULTITEXTO))
												        || coluna.iGetGrupoAtributosLivres().getSisTipoExibicGrupoSteg().getCodSteg().equals(new Long(Input.VALIDACAO))) {
													informacaoAtbdem = informacaoAtbdem + pontoCriticoSisAtributoPtcSatb.getInformacao() + "; ";
												}
												else
													if (!coluna.iGetGrupoAtributosLivres().getSisTipoExibicGrupoSteg().getCodSteg().equals(new Long(Input.IMAGEM))) {
														//se for do tipo imagem não faz nada, deixa em branco.
														informacaoAtbdem = informacaoAtbdem + pontoCriticoSisAtributoPtcSatb.getSisAtributoSatb().getDescricaoSatb() + "; ";
													}
											}
										}
										if (informacaoAtbdem.length() > 0) {
											informacaoAtbdem = informacaoAtbdem.substring(0, informacaoAtbdem.length() - 2);
										}
										out.println(informacaoAtbdem);
									}
									else 
									{
										if (coluna.iGetNome().equals("pontoCriticoCorPtccores")) {
											String corRelogio = "Branco";
											String descRelogio = "";
											String[] relogioAtual = pontoCriticoDao.getRelogioNaData(pontoCriticoPtc, Data.getDataAtual());
											corRelogio = relogioAtual[0];
											descRelogio = relogioAtual[1];
										%> <img	src="<%=request.getContextPath()%>/images/pc<%=corRelogio%>1.png" title="<%=descRelogio%>"> <%
							 			}
							 			else {out.println(coluna.iGetValor(pontoCriticoPtc));
							 			}%>
							 		
								  </td>
								  <%}%>
									
									<%
										}%>
									</tr>	
										<tr>
									<td>
										<br>
									</td>
									</tr>
										<tr>
										<td colspan="<%=lColunas.size() + 3%>">
										<table width="100%" border="0" cellpadding="0" cellspacing="0">
											<tr> 
												<td width="15%" ></td>
												<td width="5%"></td>
												<td width="5%" align="center">Tipo</td>
												<td width="15%"align="center">Data</td>
												<td>Usuário</td>
											</tr>
											<%
											
											if(tipos == null || tipos.length == 0){
												tipos = new String[3];
												tipos[0] = "1";
												tipos[1] = "2";
												tipos[2] = "3";
											}
											
											if (primeira != null && primeira != ""){
											if (dataInicio != null & dataFim != null)
												lista = pontoCriticoDao.listaHistorico(Data.parseDate(dataInicio), Data.parseDate(dataFim), tipos, new String[] { codigos[i] }).iterator();
											else
												lista = pontoCriticoDao.listaHistorico(null, null, tipos, new String[] { codigos[i] }).iterator();
											
											while (lista.hasNext()) {
												HistoricoXml hptc = (HistoricoXml) lista.next();
												String tipo = "";
												String cor = "";
												if (hptc.getTipoHistorico() != null)
													switch (hptc.getTipoHistorico().intValue()) {
														case 1: tipo = "Incluído"; cor = ((HistoricoConfig) histCor.get(0)).getCorHistorico();
															imagem = historicoDao.getImagemPersonalizadaHistorico(((HistoricoConfig) histCor.get(0)));break;
														case 2: tipo = "Alterado";cor = ((HistoricoConfig) histCor.get(1)).getCorHistorico();
															imagem = historicoDao.getImagemPersonalizadaHistorico(((HistoricoConfig) histCor.get(1)));break;
														case 3: tipo = "Excluído";cor = ((HistoricoConfig) histCor.get(2)).getCorHistorico();
															imagem = historicoDao.getImagemPersonalizadaHistorico(((HistoricoConfig) histCor.get(2)));break;
														default: tipo = "-"; break;
													}
											%>
											<tr class="linha_subtitulo2">
												<td width="15%"></td>
												<td width="5%">
												<%
												UsuarioUsu usuarioImagem = null;  
												String pathRaiz = configuracao.getRaizUpload();
												String hashNomeArquivo = null;
												if (imagem != null) {	
													usuarioImagem = ((ecar.login.SegurancaECAR)request.getSession().getAttribute("seguranca")).getUsuario(); 
													hashNomeArquivo = Util.calcularHashNomeArquivo(pathRaiz, imagem);
													Util.adicionarMapArquivosAtuaisUsuarios(usuarioImagem, hashNomeArquivo, pathRaiz, imagem);	
													
													imagem = request.getContextPath() + "/DownloadFile?tipo=open&RemoteFile=" + hashNomeArquivo;
												%>
													<img name="iconeHistorico<%=hptc.getCodigo()%>" src="<%=imagem%>" style="width: 15px; height: 15px;" width="" heigth=""> 
												<%}%> 
												<input type="checkbox" class="form_check_radio" name="codHist" value="<%=hptc.getCodigo()%>"> 
												<input type="hidden" name="codPtc" value="<%=hptc.getIdObjetoSerializado()%>">
												</td>
												<td style="color:<%=cor%>" align="center"><%=tipo%></td>
												<td style="color:<%=cor%>" align="center"><%=Pagina.trocaNullDataHora(hptc.getDataHistorico())%></td>
												<td style="color:<%=cor%>"><%=hptc.getUsuarioUsu() != null ? hptc.getUsuarioUsu().getNomeUsu() : ""%></td>
											</tr>
											<%
												}
											}
											%>
										</table>
										</td>
									</tr>
									<tr>
									<td>
										<br>
									</td>
									</tr>
									
									<tr>
									<td class="espacador" colspan="<%=lColunas.size() + 3%>">
										
										<img src="<%=request.getContextPath()%>/images/pixel.gif">
										<br>
									</td>
									</tr>
										<%
										i++;
										}
								}
								%>
							</tbody>
						</table>
						
						</td>
					</tr>
				</table>			
			</div>
			
		</td>
	</tr>
	
</table>


</form>
<%
	}
	catch (ECARException e) {
		org.apache.log4j.Logger.getLogger(this.getClass()).error(e);
		Mensagem.alert(_jspx_page_context, e.getMessageKey());
	}
	catch (Exception e) {
%> <%@ include file="/excecao.jsp"%> <%
 	}
 %>
</div>
<br>
</div>

</body>
<%
	/* Controla o estado do Menu (aberto ou fechado) */
%>
<%@ include file="/include/estadoMenu.jsp"%>
<%@ include file="/include/mensagemAlert.jsp"%>
</html>
