
<jsp:directive.page import="ecar.pojo.UsuarioAtributoUsua"/>
<%@ include file="/login/validaAcesso.jsp"%>
<%@ include file="/frm_global.jsp"%>

<%@ page import="comum.util.Util"%>
<%@ page import="comum.util.ImagemAcompanhamento"%>
<%@ page import="comum.util.Data" %>
<%@ page import="java.text.SimpleDateFormat"%>
<%@ page import="ecar.login.SegurancaECAR"%>
<%@ page import="ecar.bean.OrdenacaoDataTpfa" %>
<%@ page import="ecar.bean.AtributoEstruturaListagemItens" %>
<%@ page import="ecar.bean.AcessoRelatorio" %> 
<%@ page import="ecar.dao.CorDao"%>
<%@ page import="ecar.dao.UsuarioDao"%>
<%@ page import="ecar.dao.AcompReferenciaDao" %> 
<%@ page import="ecar.dao.ItemEstruturaDao" %> 
<%@ page import="ecar.dao.TipoFuncAcompDao" %> 
<%@ page import="ecar.dao.ItemEstUsutpfuacDao" %>
<%@ page import="ecar.dao.AcompReferenciaItemDao" %> 
<%@ page import="ecar.dao.AcompRelatorioDao" %> 
<%@ page import="ecar.dao.TipoAcompanhamentoDao" %>
<%@ page import="ecar.pojo.Cor"%>
<%@ page import="ecar.pojo.AcompReferenciaAref" %> 
<%@ page import="ecar.pojo.ItemEstruturaIett" %>  
<%@ page import="ecar.pojo.TipoFuncAcompTpfa" %> 
<%@ page import="ecar.pojo.UsuarioUsu" %>
<%@ page import="ecar.pojo.ItemEstUsutpfuacIettutfa" %>
<%@ page import="ecar.pojo.AcompReferenciaItemAri" %> 
<%@ page import="ecar.pojo.AcompRelatorioArel" %>
<%@ page import="ecar.pojo.TipoAcompFuncAcompTafc"%>
<%@ page import="ecar.pojo.ConfiguracaoCfg" %>
<%@ page import="ecar.util.Dominios" %>

<%@ page import="java.util.Iterator" %> 
<%@ page import="java.util.List" %>
<%@ page import="java.util.Collections" %>
<%@ page import="java.util.Date" %>
<%@ page import="java.util.ArrayList" %>

<%@ taglib uri="/WEB-INF/ecar-util.tld" prefix="util" %>

<%
Date dataInicio = Data.getDataAtual();
%>

<html lang="pt-br">
<head>
 
<%@ include file="/include/meta.jsp"%>
<%@ include file="/titulo.jsp"%>

<link rel="stylesheet" href="<%=_pathEcar%>/css/style_<%=configuracaoCfg.getEstilo().getNome()%>.css" type="text/css">

<script language="javascript" src="<%=_pathEcar%>/js/menu_retratil.js" type="text/javascript"></script>
<script language="javascript" src="<%=_pathEcar%>/js/focoInicial.js" type="text/javascript"></script>
<script language="javascript" src="<%=_pathEcar%>/js/destaqueLinha.js" type="text/javascript"></script>
<script type="text/javascript">
function aoClicarConsultar(form, codigo, nivelIett){ 
	form.codAri.value = codigo;
	form.nivelIettClicado.value = nivelIett;
	document.form.action = "datasLimites.jsp";
	document.form.submit();
}

function filtroSomenteAcompanhamento(somenteAcompanhamento, nuPagina) {
	form.nuPagina.value = nuPagina;
	document.form.action = "listaItens.jsp";
	document.form.submit();
}

function aoClicarTpfa(form, primeiroAcomp, codTpfa, codAri, codAcomp){
	//<a href=\"acompRelatorio.jsp?primeiroAcomp=" + primeiroAcomp.getCodAref().toString() + "&funcao=ACOMP" + funcao.getCodTpfa().toString() + "&codTpfa=" + funcao.getCodTpfa().toString() + "&codAri=" + acompRefItem.getCodAri().toString() + "&codAcomp=" + acompanhamento.getCodAref().toString() +"\" class=\"" + _nomeUsuario + "\">";
	form.action = "acompRelatorio.jsp?primeiroAcomp=" + primeiroAcomp + "&funcao=SITUACAO&funcaoParecer=ACOMP" + codTpfa + "&codTpfa=" + codTpfa + "&codAri=" + codAri + "&codAcomp=" + codAcomp;
	form.submit();
}

// funcao que realiza a paginacao
function irParaPagina(form, nuPagina){
	form.nuPagina.value = nuPagina;
	form.hidAcao.value = 'alterar';
	document.form.action = "listaItens.jsp";
	document.form.submit();
}

</script>

</head>
 
<%
request.setAttribute("exibirAguarde", "true");
%>
<%@ include file="/cabecalho.jsp" %>
<%@ include file="/divmenu.jsp"%> 

<body > 

<div id="conteudo">

<!-- TÍTULO -->
<%@ include file="/titulo_tela.jsp"%>

<%
try{
	AcompReferenciaDao acompReferenciaDao = new AcompReferenciaDao(request);
	TipoFuncAcompDao funcaoDao = new TipoFuncAcompDao(request);
	ItemEstUsutpfuacDao itemEstUsuDao = new ItemEstUsutpfuacDao(request);
	AcompRelatorioDao acompRelatorioDao = new AcompRelatorioDao(request);
	TipoAcompanhamentoDao tipoAcompanhamentoDao = new TipoAcompanhamentoDao(request);
	ItemEstruturaDao itemDao = new ItemEstruturaDao(request);
	AcompReferenciaItemDao acompRefItemDao = new AcompReferenciaItemDao(request);
	
	UsuarioDao usuDao = new UsuarioDao(request);
	ValidaPermissao validaPermissao = new ValidaPermissao();
	UsuarioUsu usuarioLogado = ((SegurancaECAR)request.getSession().getAttribute("seguranca")).getUsuario(); 

	request.getSession().removeAttribute("listaParaArvore");
	request.getSession().removeAttribute("nivelPrimeiroIettClicado");

	List listaParaArvore = new ArrayList();
	
	AcompReferenciaAref acompanhamento = (AcompReferenciaAref) acompReferenciaDao.buscar(AcompReferenciaAref.class, Long.valueOf(Pagina.getParamStr(request, "codAcomp")));
	AcompReferenciaAref primeiroAcomp = (AcompReferenciaAref) acompReferenciaDao.buscar(AcompReferenciaAref.class, Long.valueOf(Pagina.getParamStr(request, "primeiroAcomp")));
	
	TipoFuncAcompTpfa funcao = new TipoFuncAcompTpfa();
	
	List lFuncoesPrincipais = tipoAcompanhamentoDao.getTipoFuncaoAcompanhamento(acompanhamento.getTipoAcompanhamentoTa());
	
	int nuPagina = 1;
	if(Pagina.getParamStr(request, "nuPagina") != null && !(Pagina.getParamStr(request, "nuPagina")).equals("")) {
		nuPagina = Integer.parseInt(Pagina.getParamStr(request, "nuPagina")); //qual a pagina que esta sendo visualizada
	}
%>
	<div id="subconteudo">

	<form name="form" method="post" action="">

	<input type="hidden" name="hidAcao" value="">
	<input type="hidden" name="codTipoAcompanhamento" value="<%=acompanhamento.getTipoAcompanhamentoTa().getCodTa()%>">
	<input type="hidden" name="codAcomp" value='<%=Pagina.getParamStr(request, "codAcomp")%>'>
	<input type="hidden" name="codAri" value="">
	<input type="hidden" name="nivelIettClicado" value="">
	<input type="hidden" name="primeiroAcomp" value='<%=Pagina.getParamStr(request, "primeiroAcomp")%>'>
	<input type="hidden" name="nuPagina" value="<%=nuPagina%>">
<%
	List lFuncao = new ArrayList();
	List listaAcompTemp = new ArrayList();
	/*Para que o método abaixo ordene as funções conforme o primeiro acompanhamento da lista, e não o acompanhamento escolhido, devo passar uma "lista" com 1 item - o primeiro acompanhamento da tela anterior*/
	listaAcompTemp.add(primeiroAcomp);
	List lOrdenacaoDataTpfa = funcaoDao.getTpfaOfArlAndTipoAcompanhamentoOrderByDatas(acompanhamento.getTipoAcompanhamentoTa().getCodTa(), listaAcompTemp);

	Iterator itOrd = lOrdenacaoDataTpfa.iterator();
	while(itOrd.hasNext()){
		OrdenacaoDataTpfa ord = (OrdenacaoDataTpfa) itOrd.next();
		if(ord.getTpfaFixo() == null || "".equals(ord.getTpfaFixo())){
			if(lFuncoesPrincipais.contains(ord.getTpfa())){
				lFuncao.add(ord.getTpfa());
			}
		}
	}
	
	int cols = lFuncao.size() + 2;
	
	String tipoAcesso = "";
	
	/* Verifica o tipo de acesso (Monitoramento, secretaria ou outra secretaria) */
	if("S".equals(acompanhamento.getTipoAcompanhamentoTa().getIndMonitoramentoTa()))
		tipoAcesso = validaPermissao.EM_MONITORAMENTO;
	else
		if(seguranca.getUsuario().getOrgaoOrgs().contains(acompanhamento.getOrgaoOrg()))
			tipoAcesso = validaPermissao.PROPRIA_SECRETARIA;
		else
			tipoAcesso = validaPermissao.OUTRAS_SECRETARIAS;
			
			
	String somenteAcompanhamento = Pagina.getParamStr(request, "somenteAcompanhamento");
	
	if("".equals(somenteAcompanhamento)) {
		somenteAcompanhamento = "S";
	}
	
	List listAris = new ArrayList(acompanhamento.getAcompReferenciaItemAris());
%>
	
	<table width="100%" border="0" cellpadding="0" cellspacing="0">	
		<tr><td class="espacador" colspan="<%=cols%>"><img src="<%=_pathEcar%>/images/pixel.gif" alt=""></td></tr>
		<tr class="linha_titulo" align="left">
			<td colspan="<%=cols%>">
				Referência: <%=acompanhamento.getNomeAref()%>&nbsp;&nbsp;&nbsp;(<%=acompanhamento.getTipoAcompanhamentoTa().getDescricaoTa()%>)
				&nbsp;
				<input type="radio" class="form_check_radio" name="somenteAcompanhamento" <%=Pagina.isChecked(somenteAcompanhamento, "S")%> value="S" onclick="javascript:filtroSomenteAcompanhamento('S',<%=nuPagina%>);">Somente Acompanhamento
				&nbsp;&nbsp;
				<input type="radio" class="form_check_radio" name="somenteAcompanhamento" <%=Pagina.isChecked(somenteAcompanhamento, "N")%> value="N" onclick="javascript:filtroSomenteAcompanhamento('N',<%=nuPagina%>);">Acompanhamento e Físico
				
			</td> 
		</tr>
	
		<tr><td class="espacador" colspan="<%=cols%>"><img src="<%=_pathEcar%>/images/pixel.gif" alt=""></td></tr>
		<tr><td class="linha_subtitulo_legenda" colspan="<%=cols%>">
			<table cellpadding="0" cellspacing="0" width="100%">
				<tr>
					<td><img src="<%=_pathEcar%>/images/vermelho_legenda.png" width="10px" height="10px" alt=""><span style="margin-left: 10px;" class="link_vermelho">Aguarda elaboração do parecer </span></td>
					<td><img src="<%=_pathEcar%>/images/verde_legenda.png" width="10px" height="10px" alt=""><span style="margin-left: 10px;" class="link_verde">Aguarda liberação do parecer  </span></td>
					<td><img src="<%=_pathEcar%>/images/azul_legenda.png" width="10px" height="10px" alt=""><span style="margin-left: 10px;">Parecer liberado  </span></td>
				</tr>
			</table>
		</td></tr>
		<tr><td class="espacador" colspan="<%=cols%>"><img src="<%=_pathEcar%>/images/pixel.gif" alt=""></td></tr>
		<tr class="linha_subtitulo"> 
			<td>&nbsp;</td>
<%
			Iterator itFuncao = lFuncao.iterator();
			while (itFuncao.hasNext()) {
				funcao = (TipoFuncAcompTpfa) itFuncao.next();
				%><td><%=funcao.getLabelPosicaoTpfa()%></td><%
			}
%>
			<td>Acompanhamento</td>
		</tr>
		<tr><td class="espacador" colspan="<%=cols%>"><img src="<%=_pathEcar%>/images/pixel.gif" alt=""></td></tr>	
<%		
		// Pega a configuração para saber o número de itens para paginação
		//ConfiguracaoCfg configuracaoCfg = new ConfiguracaoDao(request).getConfiguracao();
		int nuItensPaginacao = configuracaoCfg.getNuItensExibidosPaginacao(); //numero de itens a serem exibidos por pagina
		
		List[] listas = acompReferenciaDao.getItemEstruturaAcompanhamento(listAris, 
			acompanhamento.getTipoAcompanhamentoTa(), seguranca.getUsuario(), seguranca.getGruposAcesso(), 
			tipoAcesso, somenteAcompanhamento, nuPagina, nuItensPaginacao);
		List arvore = listas[0];
		List selecionaveis = listas[1];
		List itensSemParecer = listas[2];
		List selecionaveisOrdenados = listas[3]; 
				
		Iterator it = arvore.iterator();
		int cont = 0;
		String cor = new String(); 
		
		List listItemEstUsu = null;
		List listArel = null;
		List listPermissaoAcessoRelatorio = null;
		
		if(!lFuncao.isEmpty()) {
			List listItemEstrutura = new ArrayList();
			Iterator itAris = listAris.iterator();
			while(itAris.hasNext()) {
				listItemEstrutura.add(((AcompReferenciaItemAri)itAris.next()).getItemEstruturaIett());
			}
			listItemEstUsu = itemEstUsuDao.getItemEstUsutpfuacIettutfa(listItemEstrutura);
			listArel = acompRelatorioDao.getArelsOfAris(listAris);
			
			List listTipoAcompFuncAcomp = new ArrayList(acompanhamento.getTipoAcompanhamentoTa().getTipoAcompFuncAcompTafcs());
			listPermissaoAcessoRelatorio = acompRelatorioDao.getListaAcessoRelatorio(seguranca.getUsuario(), lFuncao, listArel, listTipoAcompFuncAcomp);
		}
		
		List itensSessao = new ArrayList();
		
		while(it.hasNext()){
	
			AcompReferenciaItemAri acompRefItem = new AcompReferenciaItemAri();
			
			if (cont % 2 == 0){
				cor = "cor_nao";
			} else {
				cor = "cor_sim"; 
			}
			
			
	        AtributoEstruturaListagemItens aeList = (AtributoEstruturaListagemItens) it.next();
	        ItemEstruturaIett itemEstrutura = aeList.getItem();

			String sinalizarItem = Pagina.getParamStr(request, "sinalizarItem");

			if(!"".equals(sinalizarItem)) {
				if(sinalizarItem.equals(itemEstrutura.getCodIett().toString())) {
					sinalizarItem = _pathEcar + "/images/icon_bullet_seta_amarelo.png";

					if("cor_sim".equals(cor)) {
						cor = "list_elab_acomp_cor_sim";
					}
					else {
						cor = "list_elab_acomp_cor_nao";
					}
				}
				else {
					sinalizarItem = _pathEcar + "/images/icon_bullet_seta.png";
				}
			}
			else {
				sinalizarItem = _pathEcar + "/images/icon_bullet_seta.png";
			}
%>
			<tr class="<%=cor%>" onmouseover="javascript:destacaLinha(this,'over','')" onmouseout="javascript: destacaLinha(this,'out','<%=cor%>')" onClick="javascript: destacaLinha(this,'click','<%=cor%>')" ><td>
			<table>
				<tr>
<%
		        String nomeIettLink = "".equals(aeList.getDescricao().trim()) ? "[Não Informado]" : aeList.getDescricao();

		        int nivel = itemEstrutura.getNivelIett().intValue();
		        for(int i = 1; i < nivel; i++){
	   			    %><td><img src="<%=_pathEcar%>/images/pixel.gif" width="22" height="9" alt=""></td><%
			 	}

			  	boolean link = false;
			 	if(selecionaveis.contains(itemEstrutura) && !itensSemParecer.contains(itemEstrutura)){
	            	link = true;
				}
%>
				<td valign="top"><img src="<%=sinalizarItem%>" alt=""></td>
				<td>
<%
				if (link){
					Iterator itAris = listAris.iterator();
					while(itAris.hasNext()) {
						AcompReferenciaItemAri ariTmp = (AcompReferenciaItemAri)itAris.next();
						if(ariTmp.getItemEstruturaIett().equals(itemEstrutura)) {
							acompRefItem = ariTmp;
							break;
						}
					}
					
					listaParaArvore.add(itemEstrutura.getCodIett() + "_" + acompRefItem.getCodAri());
%>
					<a name="ancoraItem<%=itemEstrutura.getCodIett().toString()%>" href="javascript:aoClicarConsultar(document.form,<%=acompRefItem.getCodAri()%>,<%=String.valueOf(nivel)%>)" title="<%=itemEstrutura.getEstruturaEtt().getNomeEtt()%>">
						<%=nomeIettLink%>
					</a>
<%
					itensSessao.add(acompRefItem.getCodAri().toString());
				}else{
				%>
					<a name="ancoraItem<%=itemEstrutura.getCodIett().toString()%>" class="a_ancora">
						<%
						if(itensSemParecer.contains(itemEstrutura)) {
						%>
							<span class="link_vermelho">
								<%=nomeIettLink%>
							</span>
						<%
						} else {
						%>
							<%=nomeIettLink%>
						<%
						}
						%>
					</a>
				<%
				}
%>
				</td></tr>
			</table>
			</td>
<%
				/* mostra os campos variáveis de acompanhamento */
				itFuncao = lFuncao.iterator();
				String urlImg="";
				while (itFuncao.hasNext()) {
					funcao = (TipoFuncAcompTpfa) itFuncao.next();
					
					if (link){
						//ItemEstUsutpfuacIettutfa itemEstUsu = (ItemEstUsutpfuacIettutfa) itemEstUsuDao.getUsuarioAcompanhamento(itemEstrutura, funcao);
						ItemEstUsutpfuacIettutfa itemEstUsu = null;
						if(listItemEstUsu != null) {
							Iterator itItemEstUsu = listItemEstUsu.iterator();
							while(itItemEstUsu.hasNext()) {
								ItemEstUsutpfuacIettutfa itemEstUsuTemp = (ItemEstUsutpfuacIettutfa)itItemEstUsu.next();
								if(itemEstUsuTemp.getItemEstruturaIett().equals(itemEstrutura)
									&& itemEstUsuTemp.getTipoFuncAcompTpfa().equals(funcao)) {
									itemEstUsu = itemEstUsuTemp;
									break;
								}
							}
						}
						
						String _nomeUsuario = "";
						if (itemEstUsu != null) {
							String mensagem = "";
							//AcompRelatorioArel arel = acompRelatorioDao.getAcompRelatorio(itemEstUsu.getTipoFuncAcompTpfa(), acompRefItem);
							AcompRelatorioArel arel = null;
							if(listArel != null) {
								Iterator itArel = listArel.iterator();
								while(itArel.hasNext()) {
									AcompRelatorioArel arelTemp = (AcompRelatorioArel)itArel.next();
									if(arelTemp.getAcompReferenciaItemAri().equals(acompRefItem)
										&& arelTemp.getTipoFuncAcompTpfa().equals(funcao)) {
										arel = arelTemp;
										break;
									}
								}
							}
	
							String titulo = "";
							String titleImg = "Sem parecer.";
							String srcImg= _pathEcar + "/images/sBranco3.png";
							boolean parecrLiberado = false;

							
							if(arel != null) {

								if(arel.getDataUltManutArel() == null){
									_nomeUsuario = "link_vermelho";
									titulo += "Aguarda elaboração do parecer <br>";
								}
								else if(arel.getIndLiberadoArel() == null || "N".equals(arel.getIndLiberadoArel())) {
										_nomeUsuario = "link_verde";			
										titulo += "Aguarda liberação do parecer  <br> ";
								}else if(arel.getIndLiberadoArel() != null || "S".equals(arel.getIndLiberadoArel())){
									titulo += "Parecer liberado <br>Atualização: " 
									+ Data.parseDate(arel.getDataUltManutArel()) + " <br>";
									parecrLiberado = true;
									srcImg =  Util.getURLImagemAcompanhamento(arel.getCor(), request, funcao);
									 //ImagemAcompanhamento.getURLImagem(arel.getCor(), request, funcao);
									titleImg= arel.getCor().getSignificadoCor();
								}
								
							}
							// nova verificação devido ao BUG 3674:
							// - Na relação de itens mostrar o nome do usuário em negrito e o title "Sem registro de acompanhamento"
							else {								
								_nomeUsuario = "link_vermelho";
								titulo += "Aguarda elaboração do parecer <br>";
							}
							
							//String _linkTpfa = "<a href=\"acompRelatorio.jsp?primeiroAcomp=" + primeiroAcomp.getCodAref().toString() + "&funcao=ACOMP" + funcao.getCodTpfa().toString() + "&codTpfa=" + funcao.getCodTpfa().toString() + "&codAri=" + acompRefItem.getCodAri().toString() + "&codAcomp=" + acompanhamento.getCodAref().toString() +"\" class=\"" + _nomeUsuario + "\">";
							String _linkTpfa = "<a href=\"#\" class=\"" + _nomeUsuario + "\" onclick=\"javascript:aoClicarTpfa(form,"+primeiroAcomp.getCodAref().toString()+", "+ funcao.getCodTpfa().toString() +", " + acompRefItem.getCodAri().toString() + ", " + acompanhamento.getCodAref().toString() + ")\"  >";
							String _linkTpfaFim = "</a>";

							//String _linkTpfaImg = "<img "+  acompanhamento.getCor() + ")\"  >";
							//String _linkTpfaFimImg = "</a>";

							//int podeAcessarRelatorio = acompRelatorioDao.podeAcessarRelatorio(seguranca.getUsuario(), funcao, arel);
							int podeAcessarRelatorio = AcompRelatorioDao.OPERACAO_NEGADA_USUARIO_SEM_PERMISSAO;
							if(listPermissaoAcessoRelatorio != null && arel != null) {
								Iterator itAcessoRelatorio = listPermissaoAcessoRelatorio.iterator();
								while(itAcessoRelatorio.hasNext()) {
									AcessoRelatorio acessoRelatorioTemp = (AcessoRelatorio)itAcessoRelatorio.next();
									if(acessoRelatorioTemp.getArel().equals(arel) && acessoRelatorioTemp.getTpfa().equals(funcao)) {
										podeAcessarRelatorio = acessoRelatorioTemp.getPermissao();
										break;
									}
								}
							}
							
							if(podeAcessarRelatorio == AcompRelatorioDao.OPERACAO_NEGADA_USUARIO_SEM_PERMISSAO){
								_linkTpfa = "";
								_linkTpfaFim = "";
							}
							
							String nomeUsuarioGrupo = "";
							String posicao = "";
							String imagem_inativa = "";
														
							UsuarioUsu usuarioLabel = null;
							
							if (parecrLiberado && arel.getUsuarioUsuUltimaManutencao() != null){
								usuarioLabel = arel.getUsuarioUsuUltimaManutencao();
								if (itemEstUsu.getSisAtributoSatb() != null) {
									nomeUsuarioGrupo = itemEstUsu.getSisAtributoSatb().getDescricaoSatb();
									titulo += "Resp: " + usuarioLabel.getNomeUsuSent() + " <br> ";
								} else {
									nomeUsuarioGrupo = usuarioLabel.getNomeUsuSent(); 
								}	
							} else if (itemEstUsu.getUsuarioUsu() != null) {
								usuarioLabel = itemEstUsu.getUsuarioUsu();	
								nomeUsuarioGrupo = usuarioLabel.getNomeUsuSent();
							}
															
							if (usuarioLabel != null) {			
																		
								String telefone = "";
						        if(usuarioLabel.getComercDddUsu() != null && !"".equals(usuarioLabel.getComercDddUsu())){
						        	telefone += usuarioLabel.getComercDddUsu();
						        }
						        if(usuarioLabel.getComercTelefoneUsu() != null && !"".equals(usuarioLabel.getComercTelefoneUsu())){
						        	if(!"".equals(telefone)){
						        		telefone += " ";
						        	}
						        	telefone += usuarioLabel.getComercTelefoneUsu();
						        }
						        titulo += "Tel: " + telefone;
						        
						        if (usuDao.getCelularByUsuario(usuarioLabel) != null && !"".equals(usuDao.getCelularByUsuario(usuarioLabel))){
							        titulo += " <br> ";
							        titulo += "Cel: " + usuDao.getCelularByUsuario(usuarioLabel);
						        }
					        
						        if(!"".equals(titulo)){
									titulo += " <br> ";
								}
								titulo +="<br>" + usuarioLabel.getEmail1UsuSent();
	
							} else if (itemEstUsu.getSisAtributoSatb() != null) {
								titulo += itemEstUsu.getSisAtributoSatb().getDescricaoSatb();
								nomeUsuarioGrupo = itemEstUsu.getSisAtributoSatb().getDescricaoSatb();
							}		
							
							posicao = "10";	
							imagem_inativa = "";
							if (usuarioLabel != null){
								if (!"S".equals(usuarioLabel.getIndAtivoUsu())){
									imagem_inativa="<img src=\"" + _pathEcar + "/images/icon_usuario_inativo.png\" title=\"Usuário Inativo\">";
								}
							}		
							
							if(!"".equals(mensagem)){
								mensagem += " (" + titulo + ")";
							}
							else {
								mensagem = titulo;
							}
							
								%>
								<%
							// verifica se o parecer é obrigatorio ou opicional
							// se opcional a imagem sBranco3 não deverá ser exibida
							boolean obrigatorio = true;
							List listTipoAcompFuncAcomp = new ArrayList(acompanhamento.getTipoAcompanhamentoTa().getTipoAcompFuncAcompTafcs());
							if( listTipoAcompFuncAcomp != null ) {
								Iterator listTipoAcompFuncAcompIt = listTipoAcompFuncAcomp.iterator();
								while(listTipoAcompFuncAcompIt.hasNext()) {
									TipoAcompFuncAcompTafc tipoAcompFuncAcompTafc = (TipoAcompFuncAcompTafc)listTipoAcompFuncAcompIt.next();
									if( tipoAcompFuncAcompTafc.getTipoFuncAcompTpfa().getCodTpfa() != null && funcao.getCodTpfa() != null && 
										tipoAcompFuncAcompTafc.getTipoFuncAcompTpfa().getCodTpfa().equals(funcao.getCodTpfa())) {
										if(tipoAcompFuncAcompTafc.getIndRegistroPosicaoTafc() != null && tipoAcompFuncAcompTafc.getIndRegistroPosicaoTafc().equals(Dominios.OPCIONAL)) {
											obrigatorio = false;
											break;
										}
									}
								}
							}
							
							boolean usuarioLogadoEmiteParecer = false; 
							
							if (itemEstUsu.getUsuarioUsu() != null) {
								usuarioLogadoEmiteParecer = itemEstUsu.getUsuarioUsu().getCodUsu().equals(usuarioLogado.getCodUsu());
							} else if (itemEstUsu.getSisAtributoSatb() != null) {
								if (itemEstUsu.getSisAtributoSatb().getUsuarioAtributoUsuas() != null) {
									Iterator itUsuarios = itemEstUsu.getSisAtributoSatb().getUsuarioAtributoUsuas().iterator();
									while (itUsuarios.hasNext()) {
										UsuarioAtributoUsua usuarioAtributoUsua = (UsuarioAtributoUsua) itUsuarios.next();
										if (usuarioAtributoUsua.getUsuarioUsu().getCodUsu().equals(usuarioLogado.getCodUsu())){
											usuarioLogadoEmiteParecer = true;
											break;
										}
									}
								}
							}
								
							if(usuarioLogadoEmiteParecer || parecrLiberado) { %>
								<td style="vertical-align: middle;" class="<%=_nomeUsuario%>" onMouseOver="javascript:showInfoUsu(<%=cont%>,<%=funcao.getCodTpfa().toString()%>,<%=posicao%>)" onMouseOut="javascript:hideInfoUsu(<%=cont%>,<%=funcao.getCodTpfa().toString()%>,<%=posicao%>)">
									<% if(obrigatorio || parecrLiberado) { %> 
										<img src="<%=srcImg%>" style="display: block; border: none; float: left; clear: left; margin-right: 5px;" alt="<%=titleImg %>" title="<%=titleImg %>" class="srcImg" />
									<% } %>
									<%=_linkTpfa%>
										<span><%=Pagina.trocaNull(nomeUsuarioGrupo)%></span>
									<%=_linkTpfaFim%>
									<span id="spanInfoUsu<%=cont%>_<%=funcao.getCodTpfa().toString()%>_<%=posicao%>" class='item_InfoUsu_span'>
										<%=mensagem%>
									</span>
									<%=imagem_inativa%>
								</td> 
							<% } else { %> 
								<td style="vertical-align: middle;" class="<%=_nomeUsuario%>" onMouseOver="javascript:showInfoUsu(<%=cont%>,<%=funcao.getCodTpfa().toString()%>,<%=posicao%>)" onMouseOut="javascript:hideInfoUsu(<%=cont%>,<%=funcao.getCodTpfa().toString()%>,<%=posicao%>)">
									<% if(obrigatorio && usuarioLogadoEmiteParecer) { %>
										<img  src="<%=srcImg%>"  title="<%=titleImg %>" class="srcImg" alt="">
										<span>
											<%=Pagina.trocaNull(nomeUsuarioGrupo)%>
										</span>
										<span id="spanInfoUsu<%=cont%>_<%=funcao.getCodTpfa().toString()%>_<%=posicao%>" class='item_InfoUsu_span'>
											<%=mensagem%>
										</span>
										<%=imagem_inativa%>
									<% } %>
								</td> 
							<% }  %>
								<%
						} else {
							out.println("<td>&nbsp;</td>");
							// VERIFICAÇÃO RETIRADA PARA MELHORAR PERFORMANCE DA TELA (MANTIS 6913)
							// se o item tem a funcao de acompanhamento, mas o usuario nao foi informado, mostra "N/I"
							//if (itemDao.funcaoAcompanhamentoPertenceEstrutura(itemEstrutura, funcao))
							//	out.println("<td><p title=\"Não Informado\">N/I</p></td>");
							//else
							//	out.println("<td>&nbsp;</td>");
						}
					} else {
						%><td>&nbsp;</td><%
					}
				}
				
				if(itensSemParecer.contains(itemEstrutura)) {
					%><td nowrap><p title="Não foi solicitado acompanhamento" class="link_vermelho">N/A</p></td><%
				/* imprime o status */
				}else if (acompRefItem.getStatusRelatorioSrl() != null){
					%><td nowrap><%=acompRefItem.getStatusRelatorioSrl().getDescricaoSrl()%></td><%
				} else {
				 	%><td nowrap>&nbsp;</td><%
				}
%>
			</tr>
<%
			cont++;
		}
		
		//Setar lista na sessão
		request.getSession().setAttribute("codArisControleElab", itensSessao);
%>				
	</table>
	</form>
	</div> 
	<table width="100%" border="0" cellpadding="0" cellspacing="0">
	<tr><td>&nbsp;</td></tr>
	</table>
	<%
	Date dataFinal = Data.getDataAtual();
	%>
	<table class="form">
		<tr>
			<td>
				<% 
				int resultadoDe = ((nuPagina-1)*nuItensPaginacao + nuItensPaginacao);
				if(resultadoDe > selecionaveisOrdenados.size()) {
					resultadoDe -= (resultadoDe-selecionaveisOrdenados.size());  
				}
				%>
				<b>Resultados <%=((nuPagina-1)*nuItensPaginacao + 1)%> - <%=resultadoDe%> de <%=selecionaveisOrdenados.size()%> itens</b>
			</td>
			<td align="right">
				<% int contPaginas = selecionaveisOrdenados.size()/nuItensPaginacao;
				if(selecionaveisOrdenados.size()%nuItensPaginacao > 0) {
					contPaginas += 1; 
				} %>
				<b>Página de resultados:</b> 
				<% for(int nuPag = 1; nuPag <= contPaginas; nuPag++) { 
					if(nuPag != nuPagina) {%> 
						<a href="javascript:irParaPagina(document.form,<%=nuPag%>)"><%=nuPag%></a> |  
				<%	} else { %>
						<%=nuPag%> | 
				<%  } 
				   } %>
			</td>
		</tr>
	</table>
	<table class="form">
		<tr>
			<td>
				<%
				long df = dataFinal.getTime();
				long di = dataInicio.getTime();
				%>
				<b>Tempo para processar esta página:</b> <%=Data.parseDateHourSegundos( new Date(df - di) )%> mm.ss.SSS
		</td></tr>
	</table>
	
<%
	request.getSession().setAttribute("listaParaArvore", listaParaArvore);
} catch (ECARException e) {
	org.apache.log4j.Logger.getLogger(this.getClass()).error(e);
	Mensagem.alert(_jspx_page_context, e.getMessageKey());
} catch (Exception e){
%>
	<%@ include file="/excecao.jsp"%>
<%
}
%>
</div>
</body>
<%@ include file="/include/ocultarAguarde.jsp"%>
<% /* Controla o estado do Menu (aberto ou fechado) */%>
<%@ include file="/include/estadoMenu.jsp"%>
<%@ include file="/include/mensagemAlert.jsp" %>
</html>