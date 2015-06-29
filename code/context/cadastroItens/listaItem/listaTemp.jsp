
<jsp:directive.page import="ecar.pojo.SisGrupoAtributoSga"/>
<jsp:directive.page import="ecar.pojo.ItemEstruturaSisAtributoIettSatb"/><!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@ include file="../../login/validaAcesso.jsp"%>
<%@ include file="../../frm_global.jsp"%>

<%@ page import="ecar.pojo.EstruturaEtt" %>
<%@ page import="ecar.pojo.ItemEstruturaIett" %>
<%@ page import="ecar.pojo.ObjetoEstrutura" %> 
<%@ page import="ecar.dao.EstruturaDao" %>
<%@ page import="ecar.dao.ItemEstruturaDao" %>
<%@ page import="ecar.permissao.ValidaPermissao" %>
<%@ page import="java.util.Iterator" %> 
<%@ page import="java.util.Set" %>
<%@ page import="java.util.List" %>

<%@ page import="java.util.Date" %>
<%@ page import="comum.util.Data" %>
<%@ page import="ecar.pojo.ConfiguracaoCfg" %>
<%@ page import="ecar.dao.ConfiguracaoDao" %>
<%@ page import="ecar.pojo.SisAtributoSatb" %>
<%@ page import="ecar.pojo.AtributoLivre" %>
<%@ page import="ecar.taglib.util.Input"%>
 
<%@ taglib uri="/WEB-INF/ecar-util.tld" prefix="util" %>

<html lang="pt-br">
<head>
<%@ include file="../../include/meta.jsp"%>
<%@ include file="/titulo.jsp"%>
<link rel="stylesheet" href="<%=_pathEcar%>/css/style_<%=configuracaoCfg.getEstilo().getNome()%>.css" type="text/css">
<script language="javascript" src="../../js/menu_retratil_cadastro.js"></script>
<script language="javascript" src="../../js/menu_cadastro.js"></script>
<script language="javascript" src="../../js/focoInicial.js"></script>
<script language="javascript" src="../../js/frmPadraoItemEstrut.js"></script>
<script language="javascript" src="../../js/validacoes.js"></script>
<script language="javascript" src="../../js/tableSort.js"></script>
<script language="javascript" src="<%=_pathEcar%>/js/destaqueLinha.js"></script>
<script language="javascript" src="<%=_pathEcar%>/js/jquery.js" type="text/javascript"></script>		

<script language="javascript">

function aoClicarIncluirItem(form, codEtt){
	form.hidAcao.value = 'incluir';
	form.codEtt.value = codEtt;
	form.action = "<%=_pathEcar%>/cadastroItens/dadosGerais/frm_inc.jsp";
	form.submit();
}

function aoClicarExcluirItem(form, nomeCheckBox){
	if(validarExclusao(form, nomeCheckBox)){
		if(!confirm("Confirma a exclusão?")){
			return(false);
		}
		form.hidAcao.value = "excluir";
		form.nomeCheckBox.value = nomeCheckBox;
		form.action = "ctrl.jsp";
		form.submit();	
	}
}	

function aoClicarImprimirItem(form, estrutura, itemPai){
	form.codEttImprimir.value = estrutura;
	form.codIettPaiImprimir.value = itemPai;
	//form.action = "<%=_pathEcar%>/cadastroItens/relatorios/frm_rel.jsp?imprimirEstrutura=S";
	form.action = "<%=_pathEcar%>/cadastroItens/relatorios/frm_rel.jsp";
	form.submit();
}	

function aoClicarConsultarItem(form, codIett){
	form.hidAcao.value = 'consultar';
	form.codIett.value = codIett;
	form.action = "<%=_pathEcar%>/cadastroItens/dadosGerais/frm_con.jsp";
	form.submit();
}

function aoClicarProximoNivel(form, codIett){
	form.codIettPrincipal.value = codIett;
	form.codEttSelecionado.value = "";
	form.action = "<%=_pathEcar%>/cadastroItens/listaItem/lista.jsp";
	form.submit();
}

function aoClicarTrocaAba(form, codEtt){
	form.codEttSelecionado.value = codEtt;
	form.action = "lista.jsp";
	form.submit();
}

function aoClicarExibir(form){
	form.action = "lista.jsp";
	form.submit();
}

function gerarArquivos() {
	form.hidAcao.value = "exportar";
	form.action = "geracaoArquivos.jsp";
	form.submit();
}

</script>

</head>

<body>

<%
request.setAttribute("exibirAguarde", "true");
%>
<%@ include file="../../cabecalho.jsp" %>
<%@ include file="../../divmenu.jsp" %>





<div id="conteudo">
	
	
	<div id="tituloTelaCadastro">
		<!-- TITULO -->
		<%@ include file="/titulo_tela.jsp"%> 
	</div>
	
	
	
	
<% 


//Date dataInicio = Data.getDataAtual();
try {
	/* item em que se está no momento dentro da hierarquia */
	/* é o item do qual se obtem as e a partir destas se obtem os itens filhos do item principal */
	ItemEstruturaIett itemPrincipal;
	EstruturaDao estruturaDao = new EstruturaDao(request);
	ItemEstruturaDao itemDao = new ItemEstruturaDao(request);
	//ConfiguracaoCfg configuracao = new ConfiguracaoDao(request).getConfiguracao();
	ValidaPermissao validaPermissao = new ValidaPermissao();
	long codIettPrincipal = Pagina.getParamLong(request, "codIettPrincipal");

	/* lista das estruturas filhas da estrutura de um item */
	List lEstruturas;

	request.getSession().removeAttribute("listEstruturas");

	if (codIettPrincipal != 0) {
		itemPrincipal = (ItemEstruturaIett) itemDao.buscar(ItemEstruturaIett.class, Long.valueOf(codIettPrincipal));
		lEstruturas = estruturaDao.getSetEstruturasItem(itemPrincipal);
	} else {
		itemPrincipal = null;
		lEstruturas = estruturaDao.getEstruturaPrincipal();
	}
	
	request.getSession().setAttribute("listEstruturas", lEstruturas);

%>

	
	
	<form name="form" method="post">
	

		
	
<%
	/* percorre os filhos da estrutura desenhando as colunas e seus respectivos itens */

	ItemEstruturaIett item;
	
	/* lista de itens de uma estrutura */	
	List lItensEstrutura;
	
	/* lista de colunas de uma estrutura */
	List lColunas;
	
	/* uma estrutura */
	EstruturaEtt estruturaFilha;
	
	/* uma coluna de uma estrutura */
	ObjetoEstrutura coluna;
	
	/* String auxiliar */
	String strCheckBox;
	String strHRef;
	String strHRefProx;
	String strA;
	
	/* nome do checkbox de controle (selecionar todos) */
	String nomeCbCtrl;
	 
	/* nome do checkbox dependente do checkbox de controle */
	String nomeCbDep;
	
	/* Iterador de colunas  da estrutura */
	Iterator itColunas;
	
	/* Iterador de itens da estrutura */
	Iterator itItens;

	Iterator itEstrutura = lEstruturas.iterator();
	
	StringBuffer strDiv = new StringBuffer("<div id=\"subconteudo\">");
	boolean temMaisDeUmaEstrutura = false;

	if(lEstruturas != null && lEstruturas.size() > 1){
		temMaisDeUmaEstrutura = true;
	}
	
	
	int numTabelas = 0;	
	String radConcluido = "";
	radConcluido = Pagina.getParamStr(request, "radConcluido");
	if("".equals(radConcluido) || radConcluido == null)
		radConcluido = configuracao.getExibDefaultEstCfg();		%>
	<table border="0" cellpadding="0" cellspacing="0" width="100%">
		<tr class="linha_subtitulo_estrutura">
			<td align="left" valign="top">
				<util:arvoreEstruturas itemEstrutura="<%=itemPrincipal%>" proximoNivel="false" contextPath="<%=_pathEcar%>" seguranca="<%=seguranca%>"/>
			</td>
			<td align="right" valign="top">Exibir:
				<input type="radio" class="form_check_radio" name="radConcluido" value="T" onclick="aoClicarExibir(form);" <%=Pagina.isChecked(radConcluido, "T")%>> Todos 
				<input type="radio" class="form_check_radio" name="radConcluido" value="N" onclick="aoClicarExibir(form);" <%=Pagina.isChecked(radConcluido, "N")%>> Não Concluídos 
				<input type="radio" class="form_check_radio" name="radConcluido" value="C" onclick="aoClicarExibir(form);" <%=Pagina.isChecked(radConcluido, "C")%>> Concluídos
			</td>					
		</tr>
	</table>
	
	
	
	<!-- Menu Cadastro -->
	
		<div id="menuCadastro">Teste
		  <table border="0" cellpadding="0" cellspacing="0">
		    <tr> Teste    
		      <div id="conteudoCadastroPasta">		
		      <tr><td nowrap> conteudoCadastroPastadsssssssssssssssssssssssssssssssssssssssssssssssssssssss</td></tr>
		      </div>
		    </tr>
		  </table>
		</div>
	
		<div id="conteudoCadastroEstrutura">
		
		<table border="0" cellpadding="0" cellspacing="0">
			<tr>
			<!-- Barra amarela -->
		    <td class="menuHideShowCadastro">
		    <!-- Botão na barra -->
			<div id="btmenuCadastro"></div>
			</td>
			
			<td width="100%"  valign="top">
	
		<script language="javascript">
		
		//Inicia com o menu cadastro aberto
		botaoCadastro("aberto");
		mudaEstadoCadastro("aberto");
		
		</script>

	<!-- Menu Cadastro -->
	
	
	
		
	<table id="abasestrutura" border="0" cellpadding="0" cellspacing="0" width="100%">
	<%
	boolean getPrimeiro = false;
	EstruturaEtt estruturaAtual = new EstruturaEtt();
	String codEttSel = Pagina.getParamStr(request, "codEttSelecionado");
	
	// Rogerio (28/03/2007). Mantis #9358.
    // Trata o ultimo Ett selecionado para saber em qual aba apresenta no retorno dos detalhes do item.

	String lastEttSelected = request.getParameter("ultEttSelecionado");
	if( lastEttSelected != null && "".equals(codEttSel) ) 
		codEttSel = lastEttSelected;

	// Rogerio Fim Mantis #9358
	
	if (("".equals(codEttSel)) || (codEttSel == null)) {
		getPrimeiro = true;
	} else {
		estruturaAtual = (EstruturaEtt) estruturaDao.buscar(EstruturaEtt.class, Long.valueOf(codEttSel));
		getPrimeiro = false;
	}	

	boolean isPrimeiro = true;
	
	while (itEstrutura.hasNext()){	
		estruturaFilha = (EstruturaEtt) itEstrutura.next();	
		if (getPrimeiro) {
			estruturaAtual = estruturaFilha;
			getPrimeiro = false;
			codEttSel = estruturaAtual.getCodEtt().toString();
		}
		
		if (isPrimeiro) { %>
			<tr>		
				<td> <%
			isPrimeiro = false;
		}
		
		String tipoAba = "";
		boolean isHabilitada = false;
		if (codEttSel.equals(estruturaFilha.getCodEtt().toString())){
			tipoAba = "abahabilitada";
			isHabilitada = true;
		} else {
			tipoAba = "abadesabilitada";
			isHabilitada=false;
		}
				
		%>
				<table class="<%=tipoAba%>" style="background-color: <%=estruturaFilha.getCodCor1Ett()%>; border-bottom: <%=estruturaFilha.getCodCor1Ett()%>;">
						<tr>
							<td nowrap>
								<% 
								if (!isHabilitada)
								{ %>
									<a href="#" onclick="javascript:aoClicarTrocaAba(document.form,  <%=estruturaFilha.getCodEtt()%>)">
									<%-- a href="<%="lista.jsp?codEttSelecionado="+estruturaFilha.getCodEtt() %>" onclick="javascript:aoClicarTrocaAba(document.form, <%=estruturaFilha.getCodEtt()%>)"--%>
									<%
									if(estruturaFilha.getLabelEtt() != null && !"".equals(estruturaFilha.getLabelEtt())){
										out.print(estruturaFilha.getLabelEtt()); 
									}
									else {
										out.print(estruturaFilha.getNomeEtt()); 
									}
									%>
									</a>
								<%} else { 
									if(estruturaFilha.getLabelEtt() != null && !"".equals(estruturaFilha.getLabelEtt())){
										out.print(estruturaFilha.getLabelEtt()); 
									}
									else {
										out.print(estruturaFilha.getNomeEtt()); 
									}
								}  %>
							</td>
						</tr>
				</table>
		<%
	}
	
	%>
			</td>
		</tr>	
	</table>
	<input type="hidden" name="codEttSelecionado" id="codEttSelecionado" value="<%=codEttSel%>">		
	
	 
	
	<%
		itEstrutura = lEstruturas.iterator();

		numTabelas++;			
		estruturaFilha = estruturaAtual;
		
		if(temMaisDeUmaEstrutura){
			if(estruturaFilha.getTamanhoListagemVerticalEtt() != null){
				strDiv = new StringBuffer("<div class=\"divListagemLateral\" style=\"") 
                     .append("height: ")
                     .append(estruturaFilha.getTamanhoListagemVerticalEtt().toString())
                     .append("px; ")
                     .append("\">");
			}
			else{
				strDiv.append("<div>");
			}
		}
		
		lColunas = estruturaDao.getAtributosAcessoEstrutura(estruturaFilha);
		if(lColunas != null && lColunas.size() > 0) {
			//lItensEstrutura = itemDao.getItensFilho(itemPrincipal, estruturaFilha, ((ObjetoEstrutura)lColunas.get(0)).iGetNomeOrdenarLista());
			lItensEstrutura = itemDao.getItensFilho(itemPrincipal, estruturaFilha, lColunas);
		}
		else {
			lItensEstrutura = itemDao.getItensFilho(itemPrincipal, estruturaFilha, "");
		}
		lItensEstrutura = itemDao.getItensIndConclusao(lItensEstrutura, radConcluido);
		nomeCbCtrl = "cbCtrl" + estruturaFilha.getCodEtt();
		nomeCbDep = "cbDep" + estruturaFilha.getCodEtt();
		strCheckBox = "<td width=\"1%\"><input type=\"checkbox\" class=\"form_check_radio\" name=\"" + nomeCbCtrl + "\" onclick=\"javascript:selectAll(document.form, '" + nomeCbCtrl + "', '" + nomeCbDep + "');\"></td>";
		String strColunaVazia = "<!--  Igor -->	<td width=\"1%\"> &nbsp;</td> <!-- Coluna para colocar icone para listagem -->";
		/* imprime o nome da estrutura em uma nova tabela */
%>	
 	    <%=strDiv%>
		<table border="0" cellpadding="0" cellspacing="0" width="100%"> 
					<%
					if(estruturaFilha.getLabelEtt() != null && !"".equals(estruturaFilha.getLabelEtt())){
					%>
					<tr class="linha_titulo_estrutura" bgcolor="<%=estruturaFilha.getCodCor1Ett()%>">
						<td colspan="2"><%=estruturaFilha.getNomeEtt()%></td>
					</tr>
					<%
					}
					%>
					<tr class="linha_titulo_estrutura" bgcolor="<%=estruturaFilha.getCodCor1Ett()%>">
						<td>
							<%if(validaPermissao.permissaoAdicionarItem(estruturaFilha, seguranca.getGruposAcesso())){%>
								<input type="button" value="Adicionar" class="botao" onclick="javascript:aoClicarIncluirItem(document.form, <%=estruturaFilha.getCodEtt()%>);"> 
							<%}%>
							<% if (!(lItensEstrutura!=null && lItensEstrutura.size()==0)) { %>
								<input type="button" value="Excluir" class="botao" onclick="javascript:aoClicarExcluirItem(document.form, '<%=nomeCbDep%>');">
							<%} %>
						</td>
						<td align="right">
							
							<input type="button" value="Gerar Arquivos" class="botao" onclick="javascript:gerarArquivos()">
							
							<%
							if("S".equals(estruturaFilha.getIndExibirImprimirListagem())){
							%>
								<input type="button" value="Imprimir" class="botao" onclick="javascript:aoClicarImprimirItem(document.form, <%=estruturaFilha.getCodEtt()%>, <%=codIettPrincipal%>);">
								<input type="hidden" name="imprimirEstrutura" value="S">
							<%
							}
							%>
						</td>
					</tr>
				</table>
				<table border="0" cellpadding="0" cellspacing="0" width="100%">		
					<tr><td class="espacadorestrutura" colspan="<%=lColunas.size() + 3%>"><img src="../../images/pixel.gif"></td></tr>
					<tr class="linha_subtitulo_estrutura" bgcolor="<%=estruturaFilha.getCodCor2Ett()%>">
			<%
					/* desenha as colunas de uma estrutura */
					itColunas = lColunas.iterator();
					int numColuna = 2;
					while (itColunas.hasNext()){
						coluna = (ObjetoEstrutura) itColunas.next();
			%>			
						<%=strCheckBox%>
						<%=strColunaVazia%>
						<td width="<%=coluna.iGetLargura()%>%">
						<a href="#" onclick="this.blur(); return sortTable('corpo<%=numTabelas%>',  <%=numColuna%>, false);">											
						<%numColuna++;%>
						<%=coluna.iGetLabel()%>
						</a>
						</td>
			<%
						strColunaVazia =  strCheckBox = "";
					}
			%>		
					<td> &nbsp; </td>
					</tr> <!-- linha_subtitulo -->
					<tr><td class="espacadorestrutura" colspan="<%=lColunas.size() + 3%>"><img src="../../images/pixel.gif"></td></tr>
	</thead>
	<tbody id="corpo<%=numTabelas%>" >
<%
		/* imprimir os itens da estrutura */
		itItens = lItensEstrutura.iterator();
		while (itItens.hasNext()) {
			
			item = (ItemEstruturaIett) itItens.next();
			
			if ((item.getIndAtivoIett() != null || !"".equals(item.getIndAtivoIett().trim())) 
											&& !"N".equals(item.getIndAtivoIett().toUpperCase())) {
				
				validaPermissao.permissoesItem(item, seguranca.getUsuario(), seguranca.getGruposAcesso());
				
				boolean permissaoAcessoItem = validaPermissao.permissaoExcluirItem() || validaPermissao.permissaoConsultarItem();
//				boolean permissaoAcessoItem = validaPermissao.permissaoConsultarParecerItem();
				boolean permissaoAcessoItensFilhos = false;
	
				/* hint de otimização. Só testa se tem permissão para os filhos (recursivo) se não tiver para si próprio */			
				if(!permissaoAcessoItem){
					permissaoAcessoItensFilhos = validaPermissao.permissaoAcessoItensFilhos(item, seguranca.getUsuario(), seguranca.getGruposAcesso());
				}
				
				
				if(permissaoAcessoItem || permissaoAcessoItensFilhos){
	
					/* desenha as colunas de um item */
					itColunas = lColunas.iterator();
					
					if (validaPermissao.permissaoExcluirItem() && (item.getIndBloqPlanejamentoIett() == null || item.getIndBloqPlanejamentoIett().equals(validaPermissao.NAO))) {
						strCheckBox = "<td width=\"1%\"><input type=\"checkbox\" class=\"form_check_radio\" name=\"" + nomeCbDep + "\" value=\"" + item.getCodIett() + "\"></td>"; 
					} else {
						strCheckBox = "<td width=\"1%\"><input type=\"checkbox\" class=\"form_check_radio\" name=\"" + nomeCbDep + "\" value=\"" + item.getCodIett() + "\" disabled></td>"; 
					}
		
					strHRef = "";
					strA = "";
					strHRefProx = "";
					strColunaVazia = "<!--  Igor -->	<td width=\"1%\"> &nbsp;</td> <!-- Coluna para colocar icone para listagem -->";
					if (validaPermissao.permissaoConsultarItem()) {
						strHRef = "<a href=\"javascript:aoClicarConsultarItem(document.form, " + item.getCodIett() + ");\">";
						strA = "</a>";
						/* Igor Desenho da seta para dar acesso aas estruturas filhas */
						strHRefProx = "<td width=\"1%\"><a href=\"javascript:aoClicarProximoNivel(document.form, " + item.getCodIett() + ");\"> <img src=\"../../images/collapsed_button.gif\" border=0> </td>";
						/* /Igor */					
					} else{
						if (permissaoAcessoItensFilhos) {
							strHRef = "";// <a href=\"javascript:aoClicarProximoNivel(document.form, " + item.getCodIett() + ");\">";
							strA = "</a>";
							strHRefProx = "<td width=\"1%\"><a href=\"javascript:aoClicarProximoNivel(document.form, " + item.getCodIett() + ");\"> <img src=\"../../images/collapsed_button.gif\" border=0> </td>";		
						}			
					}
					
					// Linhas com os dados da Estrutura 
					out.println("<tr class=\"cor_sim\" onmouseover=\"javascript: destacaLinha(this,'over','')\" onmouseout=\"javascript: destacaLinha(this,'out','cor_sim')\" onClick=\"javascript: destacaLinha(this,'click','cor_sim')\" class=\"linha_subtitulo2_estrutura\" bgcolor=\"" + estruturaFilha.getCodCor3Ett() + "\">");
					
					
					while (itColunas.hasNext()) {
						coluna = (ObjetoEstrutura) itColunas.next();		
						out.println(strCheckBox);
					/* Igor Verifica se existem estruturas em um nível abaixo */
					/* Se houverem, a seta é colocada*/
				    	request.setAttribute("codIettPrincipal", item.getCodIett());
						List lEstruturasFilhas = (new EstruturaDao(request)).getSetEstruturasItem(item);
				    	request.setAttribute("codIettPrincipal", Integer.valueOf(""+codIettPrincipal));	
						if (lEstruturasFilhas != null && lEstruturasFilhas.size() != 0)
						{
							out.println(strHRefProx);
							out.println(strA);
						}
						else
							out.println(strColunaVazia);
						/* /Igor */
					%>	
					
					<td width="<%=coluna.iGetLargura()%>%">
							<%=strHRef%>
								<font color="<%=estruturaFilha.getCodCor4Ett()%>">
									<%
									if("nivelPlanejamento".equals(coluna.iGetNome())){
										String niveis = "";
								    	if(item.getItemEstruturaNivelIettns() != null && !item.getItemEstruturaNivelIettns().isEmpty()){
									    	Iterator itNiveis = item.getItemEstruturaNivelIettns().iterator();
									    	while(itNiveis.hasNext()){
									    		SisAtributoSatb nivel = (SisAtributoSatb) itNiveis.next();
									    		niveis += nivel.getDescricaoSatb() + "; ";
									    	}
									    	niveis = niveis.substring(0, niveis.lastIndexOf(";"));
								    	}
										out.println(niveis);
									}
									else{
										String informacaoIettSatb = "";
										if (coluna.iGetGrupoAtributosLivres() != null)  {
											Iterator itIettSatbs =  item.getItemEstruturaSisAtributoIettSatbs().iterator();
											while (itIettSatbs.hasNext()) {
												ItemEstruturaSisAtributoIettSatb itemEstruturaSisAtributoIettSatb = (ItemEstruturaSisAtributoIettSatb) itIettSatbs.next();
												
												if (itemEstruturaSisAtributoIettSatb.getSisAtributoSatb().getSisGrupoAtributoSga().equals(coluna.iGetGrupoAtributosLivres())){
													if (coluna.iGetGrupoAtributosLivres().getSisTipoExibicGrupoSteg().getCodSteg().equals(new Long(Input.TEXT)) ||
													 	coluna.iGetGrupoAtributosLivres().getSisTipoExibicGrupoSteg().getCodSteg().equals(new Long(Input.TEXTAREA)) ||
													 	coluna.iGetGrupoAtributosLivres().getSisTipoExibicGrupoSteg().getCodSteg().equals(new Long(Input.MULTITEXTO)) ||
													 	coluna.iGetGrupoAtributosLivres().getSisTipoExibicGrupoSteg().getCodSteg().equals(new Long(Input.VALIDACAO)) ) {
													 
														informacaoIettSatb = informacaoIettSatb + itemEstruturaSisAtributoIettSatb.getInformacao() +  "; ";
													
													} else if (!coluna.iGetGrupoAtributosLivres().getSisTipoExibicGrupoSteg().getCodSteg().equals(new Long(Input.IMAGEM))) {
														//se for do tipo imagem não faz nada, deixa em branco.
														informacaoIettSatb = informacaoIettSatb + itemEstruturaSisAtributoIettSatb.getSisAtributoSatb().getDescricaoSatb() +  "; ";
													} 
												} 
											}
											if (informacaoIettSatb.length() > 0){
												informacaoIettSatb = informacaoIettSatb.substring(0, informacaoIettSatb.length() - 2); 
											}
											out.println(informacaoIettSatb);
										} else {
											out.println(coluna.iGetValor(item));
										}
									}
									%>
								</font>
							<%=strA%>
					</td>
					
					<%
						strColunaVazia = strHRefProx = strCheckBox = strHRef = strA = "";
					}
					%>
					<td> &nbsp; </td>
				</tr>
				<%
					
				}
				
			}
		}
		out.println("</tbody></table></div>");
//	}
%>

		<input type="hidden" name="codEttImprimir" value="">
		<input type="hidden" name="codIettPaiImprimir" value="">
		
		
		<%@ include file="../../include/estadoMenu.jsp"%>
		<input type=hidden name="hidAcao" value="">
		<!-- usado na inclusao de um item de nivel 1 -->
		<input type=hidden name="codEtt" value="">
		<!-- usado na consulta de um item -->
		<input type=hidden name="codIett" value="">		
		<input type=hidden name="nomeCheckBox" value="">			
		<!-- usado na inclusao de um item de qualquer nivel -->
		<input type=hidden name="codIettPrincipal" value="<%=Pagina.getParamLong(request, "codIettPrincipal")%>">			
	</form>
<%	
} catch ( ECARException e ){
	org.apache.log4j.Logger.getLogger(this.getClass()).error(e);
	Mensagem.alert(_jspx_page_context, _msg.getMensagem(e.getMessageKey()));
	%>
	<script language="javascript">
	</script>
	<%
} catch (Exception e){
%>
	<%@ include file="/excecao.jsp"%>
<%
}
%>
<table border="0" cellpadding="0" cellspacing="0" width="100%">
<tr><td>&nbsp;</td></tr>
</table>

</td></tr>
</table>
</div> <!-- conteudoEstrutura -->
</div> <!-- conteudo -->
</div> <!-- conteudo -->

</body>
<%@ include file="../../include/ocultarAguarde.jsp"%>
<% /* Controla o estado do Menu (aberto ou fechado) */%>
<%@ include file="../../include/mensagemAlert.jsp" %>
</html>