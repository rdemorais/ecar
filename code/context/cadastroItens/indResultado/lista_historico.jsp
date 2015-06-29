<%@ include file="../../login/validaAcesso.jsp"%>
<%@ include file="../../frm_global.jsp"%>

<%@ page import="ecar.dao.EstruturaFuncaoDao" %>
<%@ page import="ecar.dao.ItemEstruturaDao" %>
<%@ page import="ecar.dao.ItemEstrtIndResulDao" %>

<%@ page import="ecar.pojo.EstruturaFuncaoEttf" %>
<%@ page import="ecar.pojo.ItemEstruturaIett" %>
<%@ page import="ecar.pojo.ItemEstrtIndResulIettr" %>
<%@ page import="ecar.pojo.ItemEstrutFisicoIettf" %>
<%@ page import="ecar.permissao.ValidaPermissao" %> 

<%@ page import="java.util.Iterator"%>
<%@ page import="java.util.List"%>
<%@ page import="java.util.Collection" %>
<%@ page import="java.util.Collections" %>
<%@ page import="java.util.Comparator" %>
 
<%@ taglib uri="/WEB-INF/ecar-util.tld" prefix="util" %>

<%
	session.removeAttribute("objPesquisa");
%>


<%@page import="ecar.pojo.historico.HistoricoItemEstrtIndResulIettr"%>
<%@page import="java.io.Serializable"%>
<%@page import="comum.util.Data"%><html lang="pt-br">
<head>
<%@ include file="../../include/meta.jsp"%>
<%@ include file="/titulo.jsp"%>
<link rel="stylesheet" href="<%=_pathEcar%>/css/style_<%=configuracaoCfg.getEstilo().getNome()%>.css" type="text/css">
<script language="javascript" src="../../js/menu_retratil.js"></script>
<script language="javascript" src="../../js/frmPadraoItemEstrut.js"></script>

<script language="javascript" src="../../js/validacoes.js"></script>
<script language="javascript">
function aoClicarConsultar(form, codIettir){
	form.codIettir.value = codIettir;
	document.form.action = "../quantPrevista/lista.jsp";
	document.form.submit();
} 

function aoClicarComparar(form){
	document.form.action = "frm_con_historico.jsp";
	document.form.submit();
} 

function MostraLinha(parmCodigo) {
  if (document.getElementById('linha' + '_' + parmCodigo).style.display=='none') {
     document.getElementById('linha' + '_' + parmCodigo).style.display='';
  } else {
     document.getElementById('linha' + '_' + parmCodigo).style.display='none';
  }
}
</script>

</head>

<body>

<%@ include file="../../cabecalho.jsp" %>
<%@ include file="../../divmenu.jsp"%>

<div id="conteudo">

<%
	try {
		ItemEstruturaDao itemEstruturaDao = new ItemEstruturaDao(request);
		ItemEstruturaIett itemEstrutura = (ItemEstruturaIett) itemEstruturaDao.buscar(ItemEstruturaIett.class, Long.valueOf(Pagina.getParamStr(request,
				"codIett")));

		ValidaPermissao validaPermissao = new ValidaPermissao();
		if (!validaPermissao.permissaoConsultaIETT(Long.valueOf(Pagina.getParamStr(request, "codIett")), seguranca)) {
			response.sendRedirect(request.getContextPath() + "/acessoIndevido.jsp");
		}

		String codAba = Pagina.getParamStr(request, "codAba");
%>

<%@ include file="/titulo_tela.jsp"%>

<!-- ############### Árvore de Estruturas  ################### -->
<util:arvoreEstruturas itemEstrutura="<%=itemEstrutura%>" contextPath="<%=_pathEcar%>" seguranca="<%=seguranca%>"/>
<!-- ############### Árvore de Estruturas  ################### -->

<!-- ############### Barra de Links  ################### -->
<util:barraLinksItemEstrutura estrutura="<%=itemEstrutura.getEstruturaEtt()%>" selectedFuncao="<%=codAba%>" codItemEstrutura="<%=itemEstrutura.getCodIett().toString()%>" contextPath="<%=request.getContextPath()%>"/>
<!-- ############### Barra de Links  ################### -->

<%
	EstruturaFuncaoEttf estruturaFuncao = new EstruturaFuncaoEttf();
		EstruturaFuncaoDao estruturaFuncaoDao = new EstruturaFuncaoDao(request);

		estruturaFuncao = (EstruturaFuncaoEttf) estruturaFuncaoDao.getLabelFuncao(itemEstrutura.getEstruturaEtt(), Long.valueOf(codAba));

		Boolean permissaoAlterar = validaPermissao.permissaoAlterarItem(itemEstrutura, seguranca.getUsuario(), seguranca.getGruposAcesso(),
				estruturaFuncao);
%>

<br><br>
<!-- TITULO -->




<form name="form" method="post">
	<input type="hidden" name="hidAcao" value="">
	<input type="hidden" name="codIett" value="<%=itemEstrutura.getCodIett()%>">
	<input type=hidden name="codAba" value="<%=Pagina.getParamStr(request, "codAba")%>">
	<!--codigos-->
	<input type="hidden" name="codHistAntes" value="">
	<input type="hidden" name="codHistDepois" value="">
	
	<table width="100%" border="0" cellpadding="0" cellspacing="0">
	<tr><td class="espacador"><img src="../../images/pixel.gif"></td></tr>
	<tr class="linha_titulo" align="right">
		<td>
		<%
			if (permissaoAlterar.booleanValue()) {
		%>
			<input type="button" class="botao" value="Comparar" onclick="javascript:aoClicarComparar(form);">&nbsp;
		<%
			}
		%>
		</td>
	</tr>
	</table>
<div>	
	<table width="100%" border="0" cellpadding="0" cellspacing="0">
	<tr><td class="espacador" colspan="9"><img src="../../images/pixel.gif"></td></tr>
	<tr class="linha_subtitulo">
		<td width="2%"><input type=checkBox class="form_check_radio" name=todos onclick="javascript:selectAll(document.form)"></td>
		<td width="4%"></td>
		<td>Tipo</td>
		<td>Histórico</td>
		<td>Código</td>
		<td>Informações Complementares</td>
		<td>Unidade<br>Medida</td>
		<td>Formato</td>
		<td>Projeção?</td>
		<td>Acumulável?</td>
		<td>Qtde. Realizada por Local?</td>
		<td>Data Histórico</td>
		<td>Tipo Histórico</td>
	</tr>
	<tr><td class="espacador" colspan="12"><img src="../../images/pixel.gif"></td></tr>
<%
	ItemEstrtIndResulDao itemEstrtIndResulDao = new ItemEstrtIndResulDao(null);

		List lista = null;
		if (itemEstrutura.getItemEstrtIndResulIettrs() != null) {

			//lista = itemEstruturaDao.ordenaSet(itemEstrutura.getItemEstrtIndResulIettrs(), "this.nomeIettir", "asc");

			lista = itemEstrtIndResulDao.listaHistorico(Data.addDias(-30, Data.getDataAtual()), Data.getDataAtual());

			Iterator it = lista.iterator();

			HistoricoItemEstrtIndResulIettr itemEstrtIndResul = null;

			int cont = 0;
			while (it.hasNext()) {
				cont++;

				itemEstrtIndResul = (HistoricoItemEstrtIndResulIettr) it.next();

				if ((itemEstrtIndResul.getIndAtivoIettr() == null) || ("S".equals(itemEstrtIndResul.getIndAtivoIettr()))) {
%>		
					<tr class="linha_subtitulo2">
						<td width="2%">
							<input type="checkbox" class="form_check_radio" name="codHist" value="<%=itemEstrtIndResul.getIdHistorico()%>">
							<input type="hidden" name="codIettir" value="<%=itemEstrtIndResul.getIdHistorico()%>">
						</td>
						<td width="4%">
							<a href="javascript:aoClicarComparar(document.form)">
							<img border="0" src="../../images/icon_alterar.png" alt="Alterar"></a>&nbsp;
						</td>
						<td>
							<a href="javascript:MostraLinha('<%=cont%>');" title='Mostrar/esconder Quantidades Previstas'>
							<%=itemEstrtIndResul.getNomeIettir()%></a>
						</td>
						<td><%=itemEstrtIndResul.getIdHistorico()%></td>
						<td><%=itemEstrtIndResul.getCodIettir()%></td>
						<td><%=itemEstrtIndResul.getDescricaoIettir()%></td>
						<td><%=itemEstrtIndResulDao.getUnidadeUsada(itemEstrtIndResul)%></td>
						<td>
		<%
			if ("Q".equalsIgnoreCase(itemEstrtIndResul.getIndTipoQtde())) {
								out.println("Quantidade");
							} else if ("V".equalsIgnoreCase(itemEstrtIndResul.getIndTipoQtde())) {
								out.println("Valor");
							}
		%>				
						</td>
						<td>
		<%
			if ("S".equalsIgnoreCase(itemEstrtIndResul.getIndProjecaoIettr())) {
								out.println("Sim");
							} else {
								out.println("Não");
							}
		%>
						</td>
						<td>
		<%
			if ("S".equalsIgnoreCase(itemEstrtIndResul.getIndAcumulavelIettr())) {
								out.println("Sim");
							} else {
								out.println("Não");
							}
		%>
						</td>
						<td>
		<%
			if ("S".equalsIgnoreCase(itemEstrtIndResul.getIndRealPorLocal())) {
								out.println("Sim");
							} else {
								out.println("Não");
							}
		%>
						</td>
						<td>
		<%
			out.println(Pagina.trocaNullDataHora(itemEstrtIndResul.getDataHistorico()));
		%>
						</td>
						<td>
		<%
			if (itemEstrtIndResul.getTipoHistorico() != null)
								switch (itemEstrtIndResul.getTipoHistorico().intValue()) {
								case 1:
									out.println("Incluído");
									break;
								case 2:
									out.println("Alterardo");
									break;
								case 3:
									out.println("Excluído");
									break;
								default:
									out.println("-");
									break;
								}
		%>
						</td>
					</tr>
					<tr>
						<td colspan="12">
							<table id="linha_<%=cont%>" style="display:none" border="0" cellspacing="0">
		<%
			if (itemEstrtIndResul.getItemEstrutFisicoIettfs() != null && itemEstrtIndResul.getItemEstrutFisicoIettfs().size() > 0) {
		%>
									<tr>
										<td class="titulo">Exercício</td>
										<td class="titulo">Quantidade Prevista</td>
									</tr>
		<%
			List listaQtdPrev = new ArrayList();
								listaQtdPrev.addAll(itemEstrtIndResul.getItemEstrutFisicoIettfs());
								Collections.sort(listaQtdPrev, new Comparator() {
									public int compare(Object o1, Object o2) {
										return ((ItemEstrutFisicoIettf) o1).getExercicioExe().getDescricaoExe().compareToIgnoreCase(
												((ItemEstrutFisicoIettf) o2).getExercicioExe().getDescricaoExe());
									}
								});
								Iterator itQtPrev = listaQtdPrev.iterator();
								while (itQtPrev.hasNext()) {
									ItemEstrutFisicoIettf ieEstFis = (ItemEstrutFisicoIettf) itQtPrev.next();
		%>
										<tr>
											<td class="form_label" align="center">
												<%=ieEstFis.getExercicioExe().getDescricaoExe()%>
											</td>
											<td class="form_label" align="center">
												<%=Pagina.trocaNullMoeda(ieEstFis.getQtdPrevistaIettf())%>
											</td>
										</tr>
		<%
			}

								itemEstrtIndResulDao = new ItemEstrtIndResulDao(request);
		%>
									<tr>
										<td class="titulo" align="center">Total</td>
										<td class="titulo" align="center">
											<%
												//Pagina.trocaNullNumeroSemDecimal(itemEstrtIndResulDao.getSomaQuantidadePrevista(itemEstrtIndResul))
											%>
											<%=itemEstrtIndResulDao.getSomaQuantidadePrevista(itemEstrtIndResul)%>
										</td>
									</tr>
		<%
			} else {
		%>							
									<tr><td class="form_label">Não há quantidades previstas</td></tr>
		<%
			}
		%>
							</table>
						</td>
					</tr>
		<%
			}

					}

				}
		%>
	</table>
	</div>

	<%@ include file="../../include/estadoMenu.jsp"%>
</form>


<%
	} catch (ECARException e) {
		org.apache.log4j.Logger.getLogger(this.getClass()).error(e);
		Mensagem.alert(_jspx_page_context, e.getMessageKey());
	} catch (Exception e) {
%>
	<%@ include file="/excecao.jsp"%>
<%
	}
%>
</div>

</body>
<%@ include file="../../include/mensagemAlert.jsp"%>
</html>