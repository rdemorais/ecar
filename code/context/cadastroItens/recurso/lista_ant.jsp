<%@ include file="/login/validaAcesso.jsp"%>
<%@ include file="/frm_global.jsp"%>

<%@ page import="ecar.pojo.ItemEstruturaIett" %>
<%@ page import="ecar.pojo.EfIettFonteTotEfieft" %>
<%@ page import="ecar.pojo.EstruturaFuncaoEttf" %>
<%@ page import="ecar.pojo.ExercicioExe" %>
<%@ page import="ecar.pojo.EfItemEstPrevisaoEfiep" %>
<%@ page import="ecar.dao.EstruturaDao" %>
<%@ page import="ecar.dao.ItemEstruturaDao" %>
<%@ page import="ecar.dao.ItemEstruturaFonteRecursoDao" %>
<%@ page import="ecar.dao.EstruturaFuncaoDao" %>
<%@ page import="ecar.dao.ExercicioDao" %>
<%@ page import="ecar.dao.FonteRecursoDao" %>
<%@ page import="ecar.dao.ItemEstruturaPrevisaoDao" %>

<%@ page import="comum.util.Util" %>
  
<%@ page import="java.util.Iterator" %> 
<%@ page import="java.util.List" %>
<%@ page import="java.util.Collection" %>
<%@ page import="java.util.HashSet" %>

<%@ taglib uri="/WEB-INF/ecar-util.tld" prefix="util" %>
<html lang="pt-br">
<head> 

<%@ include file="/include/meta.jsp"%>
<%@ include file="/titulo.jsp"%>

<link rel="stylesheet" href="<%=_pathEcar%>/css/style_<%=configuracaoCfg.getEstilo().getNome()%>.css" type="text/css">
<script language="javascript" src="../../js/menu_retratil.js"></script>
<script language="javascript" src="../../js/focoInicial.js"></script>
<script language="javascript" src="../../js/frmPadraoItemEstrut.js"></script>
<script language="javascript" src="../../js/validacoes.js"></script>

<script language="javascript">
function aoClicarConsultar(form, codigo1){
	form.codFonr.value = codigo1;
	document.form.action = "frm_con.jsp";
	document.form.submit();	
} 

function aoClicarEditar(form, codigo1){
	form.codFonr.value = codigo1;
	form.hidAcao.value = 'alterar'; 
	document.form.action = "frm_alt.jsp";
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

<%@ include file="/cabecalho.jsp" %>
<%@ include file="/divmenu.jsp"%> 

<body> 
<div id="conteudo"> 

<% 
try{
	EstruturaDao estruturaDao = new EstruturaDao(request);
	ItemEstruturaDao itemEstruturaDao = new ItemEstruturaDao(request); 
	
	ItemEstruturaIett itemEstrutura = (ItemEstruturaIett) itemEstruturaDao.buscar(ItemEstruturaIett.class, Long.valueOf(Pagina.getParamStr(request, "codIett")));
	ItemEstruturaFonteRecursoDao ieFonteRecDao = new ItemEstruturaFonteRecursoDao(request);

	// Comentado devido ao BUG 4851, NÃO retirar a linha abaixo
	//boolean podeIncluir = ieFonteRecDao.verificaPossibilidadeInclusao(itemEstrutura);
	boolean podeIncluir = true;
	
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
	ValidaPermissao validaPermissao = new ValidaPermissao();
	EstruturaFuncaoEttf estruturaFuncao = new EstruturaFuncaoEttf();
	EstruturaFuncaoDao estruturaFuncaoDao = new EstruturaFuncaoDao(request);
	
	estruturaFuncao = (EstruturaFuncaoEttf) estruturaFuncaoDao.getLabelFuncao(itemEstrutura.getEstruturaEtt(), Long.valueOf(codAba));
	
	Boolean permissaoAlterar = validaPermissao.permissaoAlterarItem(itemEstrutura, seguranca.getUsuario(), seguranca.getGruposAcesso(), estruturaFuncao);
%>

<br><br>


<form name="form" method="post">

<input type=hidden name="codIett" value="<%=itemEstrutura.getCodIett()%>">
<input type=hidden name="hidAcao" value="">
<input type=hidden name="codAba" value="<%=Pagina.getParamStr(request, "codAba")%>">
<input type=hidden name="codFonr" value="">
 
<table width="100%" border="0" cellpadding="0" cellspacing="0">
	<tr><td class="espacador"><img src="../../images/pixel.gif"></td></tr>
	<tr class="linha_titulo" align="right">
		<td>
		<%if(podeIncluir && permissaoAlterar.booleanValue()){ %>
			<input type="button" value="Adicionar <%=estruturaFuncao.getLabelEttf()%>" class="botao" onclick="javascript:aoClicarIncluir(document.form)"> 
			<input type="button" value="Excluir <%=estruturaFuncao.getLabelEttf()%>" class="botao" onclick="javascript:aoClicarExcluir(document.form)">
		<%} else {%>
			&nbsp;
		<%}%>
		</td>
	</tr>
</table>
	<!-- ############### Listagem  ################### -->
<table width="100%" border="0" cellpadding="0" cellspacing="0">
	<tr><td class="espacador" colspan=7><img src="../../images/pixel.gif"></td></tr>
	<tr class="linha_subtitulo">
		<td width="4%">
			<input type=checkBox class="form_check_radio" name=todos onclick="javascript:selectAll(document.form)">&nbsp;
		</td>
		<td width="4%"></td>
		<td>Nome</td>
		<td>Total</td>
		<td>Data C&acirc;mbio</td> 
	</tr>
	<tr><td class="espacador" colspan=7><img src="../../images/pixel.gif"></td></tr>
	<%
		int linha = 0;
		if(podeIncluir){
				/* Percorre a lista de Beneficiários de ItemEstrutura e imprime na tela */
				List fontesAtivas = ieFonteRecDao.getAtivos(itemEstrutura);
				if(fontesAtivas != null ){ 
					Iterator itFonteRecurso = fontesAtivas.iterator();
					while(itFonteRecurso.hasNext()){
						EfIettFonteTotEfieft ieFonteRecurso = (EfIettFonteTotEfieft) itFonteRecurso.next();
						List listaRecurso = new ArrayList();
						linha++;
						%>
						<tr class="linha_subtitulo2"> 
							<td width="4%">
								<input type="checkbox" class="form_check_radio" name="excluir" value="<%=ieFonteRecurso.getFonteRecursoFonr().getCodFonr()%>">
							</td>
							<td width="4%">
								<a href="javascript:aoClicarEditar(document.form,<%=ieFonteRecurso.getFonteRecursoFonr().getCodFonr()%>)">
								<img border="0" src="../../images/icon_alterar.png" alt="Alterar"></a>&nbsp;
							</td>
							<td>	
								<a href="javascript:MostraLinha('<%=linha%>');">
								<%=ieFonteRecurso.getFonteRecursoFonr().getNomeFonr()%></a>
							</td>
							<%
							double somaRecursos = new ItemEstruturaFonteRecursoDao(request).getSomaRecursosFonteRecurso(ieFonteRecurso);
							%>
							<td><%=Pagina.trocaNullNumeroDecimalSemMilhar(new Double(somaRecursos))%></td>					
							<td><%=Pagina.trocaNullData(ieFonteRecurso.getDataValorEfieft())%></td>										
						</tr>
						<tr>
							<td colspan="8">
								<table id="linha_<%=linha%>" style="display:none" border="1" cellspacing="0">
								
								<tr>
									<td class="titulo">&nbsp;</td>
									<%
									ExercicioDao exercicioDao = new ExercicioDao(request);
									FonteRecursoDao fonteRecursoDao = new FonteRecursoDao(request);
									
									Collection listaExercicios = exercicioDao.getExerciciosValidos(itemEstrutura.getCodIett());
									Iterator itExerc = listaExercicios.iterator();
									int cont = 0;
									
									while (itExerc.hasNext()) {
										ExercicioExe exercicio = (ExercicioExe) itExerc.next();
										cont++;
										
										//** Buscar Recurso do Exercício e adicionar na lista
										if (ieFonteRecurso.getComp_id() != null) {
											EfItemEstPrevisaoEfiep ieRecurso = new ItemEstruturaPrevisaoDao(request).buscar(
													itemEstrutura.getCodIett(),
													ieFonteRecurso.getFonteRecursoFonr().getCodFonr(),
													exercicio.getCodExe());
											
											if (ieRecurso != null) {
												listaRecurso.add(ieRecurso);
											}
										}
										%>
										<td class="titulo" align="center"><%=exercicio.getDescricaoExe()%></td>
										<%
									}
									%>
									<td class="titulo" align="center">Total</td>
								</tr>
								<tr>
									<td class="form_label" align="left">Recurso</td>
									<%
									Iterator itExerc2 = listaExercicios.iterator();
									while (itExerc2.hasNext()) {
										ExercicioExe exercicio = (ExercicioExe) itExerc2.next();
										
										String nomeCombo = "recursoRec" + exercicio.getCodExe().toString();
										String selRecurso = "";
										
										//** Comparar com exercicio da lista e atualizar valor
										Iterator itRecurso = listaRecurso.iterator();
										while (itRecurso.hasNext()) {
											EfItemEstPrevisaoEfiep recAux = (EfItemEstPrevisaoEfiep) itRecurso.next();
											if (recAux.getExercicioExe().equals(exercicio)) {
												selRecurso = recAux.getRecursoRec().getNomeRec();
											}
										}
										%>
										<td class="form_label"><%=selRecurso%>&nbsp;</td>
									<%
									}
									%>
									<td class="form_label">&nbsp;</td>
								</tr>
								<tr>
									<td class="form_label">Valor Aprovado</td>
									<%
									double totalAprovado = 0;
									Iterator itExerc3 = listaExercicios.iterator();
									while (itExerc3.hasNext()) {
										ExercicioExe exercicio = (ExercicioExe) itExerc3.next();
										
										String valorCampo = "";
										
										//** Comparar com exercicio da lista e atualizar valor
										Iterator itRecurso = listaRecurso.iterator();
										while (itRecurso.hasNext()) {
											EfItemEstPrevisaoEfiep recAux = (EfItemEstPrevisaoEfiep) itRecurso.next();
											if (recAux.getExercicioExe().equals(exercicio)) {
												valorCampo = Pagina.trocaNullNumeroDecimalSemMilhar(recAux.getValorAprovadoEfiep());
												totalAprovado += recAux.getValorAprovadoEfiep().doubleValue();
											}
										}
										%>
										<td class="form_label" align="center"><%=valorCampo%>&nbsp;</td>
									<%
									}
									%>
									<td class="form_label" align="center"><%=Pagina.trocaNullNumeroDecimalSemMilhar(new Double(totalAprovado))%></td>
								</tr>
								<tr>
									<td class="form_label">Valor Revisado</td>
									<%
									double totalRevisado = 0;
									Iterator itExerc4 = listaExercicios.iterator();
									while (itExerc4.hasNext()) {
										ExercicioExe exercicio = (ExercicioExe) itExerc4.next();
										
										String valorCampo = "";
										
										//** Comparar com exercicio da lista e atualizar valor
										Iterator itRecurso = listaRecurso.iterator();
										while (itRecurso.hasNext()) {
											EfItemEstPrevisaoEfiep recAux = (EfItemEstPrevisaoEfiep) itRecurso.next();
											if (recAux.getExercicioExe().equals(exercicio)) {
												valorCampo = Pagina.trocaNullNumeroDecimalSemMilhar(recAux.getValorRevisadoEfiep());
												totalRevisado += recAux.getValorRevisadoEfiep().doubleValue();
											}
										}
										%>
										<td class="form_label" align="center"><%=valorCampo%>&nbsp;</td>
									<%
									}
									%>
									<td class="form_label" align="center"><%=Pagina.trocaNullNumeroDecimalSemMilhar(new Double(totalRevisado))%></td>
								</tr>
								<tr>
									<td class="titulo" align="center">Total</td>
									<%
									double totalGeral = 0;
									Iterator itExerc5 = listaExercicios.iterator();
									while (itExerc5.hasNext()) {
										ExercicioExe exercicio = (ExercicioExe) itExerc5.next();
										
										String valorTotal = "0,00";
										
										//** Comparar com exercicio da lista e atualizar valor
										Iterator itRecurso = listaRecurso.iterator();
										while (itRecurso.hasNext()) {
											EfItemEstPrevisaoEfiep recAux = (EfItemEstPrevisaoEfiep) itRecurso.next();
											if (recAux.getExercicioExe().equals(exercicio)) {
												valorTotal = Pagina.trocaNullNumeroDecimalSemMilhar(new Double(recAux.getValorRevisadoEfiep().doubleValue() + recAux.getValorAprovadoEfiep().doubleValue()));
												totalGeral += recAux.getValorRevisadoEfiep().doubleValue() + recAux.getValorAprovadoEfiep().doubleValue();
											}
										}
										%>
										<td class="titulo" align="center"><%=valorTotal%></td>
									<%
									}
									%>
									<td class="titulo" align="center"><%=Pagina.trocaNullNumeroDecimalSemMilhar(new Double(totalGeral))%></td>
								</tr>
								
								</table>
							</td>
						</tr>
						<%
					}  
				}			
		}else{
			%>
			<tr><td colspan=6><br><%=_msg.getMensagem("itemEstrutura.fonteRecurso.inclusao.naoPermitida")%><br></td></tr>
			<%
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

<table>
</div>
<br>
</form>

</body>
<% /* Controla o estado do Menu (aberto ou fechado) */%>
<%@ include file="/include/estadoMenu.jsp"%>
<%@ include file="/include/mensagemAlert.jsp" %>
</html>