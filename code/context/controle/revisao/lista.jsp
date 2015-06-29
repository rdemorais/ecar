<%@ include file="../../login/validaAcesso.jsp"%>
<%@ include file="../../frm_global.jsp"%>

<%@ page import="java.util.Iterator" %>  
<%@ page import="java.util.List" %>
<%@ page import="ecar.pojo.PeriodoRevisaoPrev" %>
<%@ page import="ecar.dao.PeriodoRevisaoPrevDao" %>
<%@ page import="ecar.pojo.ConfiguracaoCfg" %>
<%@ page import="ecar.dao.ConfiguracaoDao" %> 

<%@ taglib uri="/WEB-INF/ecar-util.tld" prefix="util" %>
<html lang="pt-br">
<head>
<%@ include file="../../include/meta.jsp"%> 
<%@ include file="/titulo.jsp"%>
<link rel="stylesheet" href="<%=_pathEcar%>/css/style_<%=configuracaoCfg.getEstilo().getNome()%>.css" type="text/css">
<script language="javascript" src="../../js/menu_retratil.js"></script>
<script language="javascript" src="../../js/focoInicial.js"></script>
<script language="javascript" src="../../js/frmPadraoItemEstrut.js"></script>
<script language="javascript" src="../../js/validacoes.js"></script>
<script language="javascript" src="../../js/tableSort.js"></script>

<script language="javascript">

function aoClicarConsultar(form, codigo){
	form.codPrev.value = codigo;
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
	ConfiguracaoDao confDao = new ConfiguracaoDao(request);
	ConfiguracaoCfg conf = confDao.getConfiguracao();
%>

<br><br>
<%@ include file="/titulo_tela.jsp"%>

<div id="subconteudo">

<form name="form" method="post">

<input type=hidden name="hidAcao" value="">
<input type=hidden name="codPrev" value="">
 
<table width="100%" border="0" cellpadding="0" cellspacing="0">
	<tr><td class="espacador"><img src="../../images/pixel.gif"></td></tr>
	<tr class="linha_titulo" align="right">
		<td  colspan="4">
			<input type="button" value="Adicionar Ciclo de Revisão" class="botao" onclick="javascript:aoClicarIncluir(document.form)"> 
			<input type="button" value="Excluir Ciclo de Revisão" class="botao" onclick="javascript:aoClicarExcluir(document.form)">
		</td>
	</tr>
</table>
	<!-- ############### Listagem  ################### -->
<table width="100%" border="0" cellpadding="0" cellspacing="0">
	<thead> 
			<tr><td class="espacador" colspan=4><img src="../../images/pixel.gif"></td></tr>
			<tr class="linha_subtitulo">
				<td width="5%">
					<input type=checkBox class="form_check_radio" name=todos onclick="javascript:selectAll(document.form)">&nbsp;
				</td>
				<td width="45%">
					<a href="#" onclick="this.blur(); return sortTable('corpo',  1, false);" title="Descricao">Decrição</a>
				</td>
				<td width="20%">
					<a href="#" onclick="this.blur(); return sortTable('corpo',  2, true);" title="DataInicio">Data de Início</a>
				</td>
				<td>
					<a href="#" onclick="this.blur(); return sortTable('corpo',  3, true);" title="DataFim">Data de Fim</a>
				</td>
			</tr>
			<tr><td class="espacador" colspan="4"><img src="../../images/pixel.gif"></td></tr>	
	</thead>
	<tbody id="corpo">	
	<%
		if(conf.getPeriodoRevisaoPrevs() != null) {
			List periodos = confDao.ordenaSet(conf.getPeriodoRevisaoPrevs(), "this.dtInicioPrev", "asc");
			Iterator itPrev = periodos.iterator();
			while(itPrev.hasNext()){
				PeriodoRevisaoPrev prev = (PeriodoRevisaoPrev) itPrev.next();
				%>
				<tr class="linha_subtitulo2">
					<td>
						<input type="checkbox" class="form_check_radio" name="excluir" value="<%=prev.getCodPrev()%>">
					</td>
					<td>						
						<a href="javascript:aoClicarConsultar(document.form,<%=prev.getCodPrev()%>)">
						<%=Pagina.trocaNull(prev.getDescricaoPrev())%></a>
					</td>
					<td>						
						<a href="javascript:aoClicarConsultar(document.form,<%=prev.getCodPrev()%>)">
						<%=Pagina.trocaNullData(prev.getDtInicioPrev())%></a>
					</td>
					<td>						
						<a href="javascript:aoClicarConsultar(document.form,<%=prev.getCodPrev()%>)">
						<%=Pagina.trocaNullData(prev.getDtFimPrev())%></a>
					</td>
				</tr>	
				<%
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
<br>
</div>

</form>
</body>
<% /* Controla o estado do Menu (aberto ou fechado) */%>
<%@ include file="../../include/estadoMenu.jsp"%>
<%@ include file="../../include/mensagemAlert.jsp" %>
</html>