<%@ include file="../login/validaAcesso.jsp"%>
<%@ include file="../frm_global.jsp"%>

<%@ page import="ecar.pojo.ConfigRelatorioCfgrel"%>
<%@ page import="ecar.dao.ConfigRelatorioCfgrelDAO"%>
<%@ page import="ecar.pojo.AcompReferenciaAref" %> 
<%@ page import="ecar.dao.AcompReferenciaDao" %> 
<%@ page import="ecar.pojo.AcompRefLimitesArl" %>
<%@ page import="ecar.pojo.TipoFuncAcompTpfa" %> 
<%@ page import="ecar.dao.TipoFuncAcompDao" %>  

<%@ page import="comum.util.Data" %> 

<%@ page import="java.util.Iterator" %> 
<%@ page import="java.util.List" %>
<%@ page import="ecar.permissao.ValidaPermissao" %>
<%@ page import="java.util.Comparator" %>
<%@ page import="java.util.Collections" %>
<%@ page import="comum.util.Util" %>
<%@ page import="ecar.dao.TipoAcompanhamentoDao" %>
<%@ page import="ecar.pojo.TipoAcompanhamentoTa" %>
<%@ page import="ecar.bean.OrdenacaoDataTpfa" %>
<%@ page import="ecar.pojo.ModeloRelatorioMrel" %>
<%@ page import="ecar.dao.ModeloRelatorioMrelDAO" %>
<%@ page import="java.util.Collection" %>

<%@ taglib uri="/WEB-INF/ecar-util.tld" prefix="util" %>
<%@ taglib uri="/WEB-INF/ecar-combo.tld" prefix="combo" %>

<html lang="pt-br"> 
<head> 
<%@ include file="../include/meta.jsp"%>
<%@ include file="/titulo.jsp"%>
<link rel="stylesheet" href="<%=_pathEcar%>/css/style_<%=configuracaoCfg.getEstilo().getNome()%>.css" type="text/css">
<script language="javascript" src="../js/menu_retratil.js"></script>
<script language="javascript" src="../js/focoInicial.js"></script>
<script language="javascript" src="../js/frmPadraoRegAcompanhamento.js"></script>
<script language="javascript" src="../js/validacoes.js"></script>

<% 
	String pathEcar = request.getContextPath();
%>

<script language="javascript">
function aoClicarBtn3(form){
	if(validar(form)){
		form.action = "<%=_pathEcar%>/RelatorioPE";
		form.target = "_blank";
		form.submit();
		form.target = "";
	}
}

function validar(form){
	if(form.opcaoModelo.value == ""){
		alert("<%=_msg.getMensagem("relAcompanhamento.relAccess.formato.ordemClassificacao.obrigatorio")%>");
		form.opcaoModelo.focus();
		return false;
	}
	return true;
}

function changeProjecao(form){
	if (form.indResultado[0].checked == true){
		form.indProjecao[0].disabled = false;
		form.indProjecao[1].disabled = false;
		form.indProjecao[1].checked = true;
	}
	else {
		form.indProjecao[0].disabled = true;
		form.indProjecao[1].disabled = true;
		form.indProjecao[0].checked = false;
		form.indProjecao[1].checked = false;
	}
}
</script>
 
</head>

<%
request.setAttribute("exibirAguarde", "true");
%>
<%@ include file="../cabecalho.jsp" %>
<%@ include file="../divmenu.jsp"%> 
<%

String codTipoAcompanhamento = Pagina.getParamStr(request, "codTipoAcompanhamento");
String strMesReferencia  = Pagina.getParamStr(request, "mesReferencia");

// controle para mostrar como default o primeiro tipo de acompanhamento das abas selecionado
if("".equals(codTipoAcompanhamento)) {
	TipoAcompanhamentoDao taDao = new TipoAcompanhamentoDao(request);
	List listTa = taDao.getTipoAcompanhamentoAtivosComAcompanhamentoBySeqApresentacao();
	
	if(!listTa.isEmpty()) {
		codTipoAcompanhamento = ((TipoAcompanhamentoTa)listTa.get(0)).getCodTa().toString();
	} 
}
%>
<body> 
<div id="conteudo">
<%
try{
	
	ConfigRelatorioCfgrelDAO configRelDao = new ConfigRelatorioCfgrelDAO(request);
	ConfigRelatorioCfgrel configRel = configRelDao.getConfigRelatorioCfgrel();
	ModeloRelatorioMrelDAO modeloRelDao = new ModeloRelatorioMrelDAO(request);
	
	ModeloRelatorioMrel modeloRel = modeloRelDao.getModeloRelatorioByCodAlfa(Pagina.getParamStr(request, "opcaoModelo"));
	
%>
<!-- TÍTULO -->
<%@ include file="/titulo_tela.jsp"%>

<util:barraLinkTipoAcompanhamentoTag
	codTipoAcompanhamentoSelecionado="<%=codTipoAcompanhamento%>"
	chamarPagina="listaRelAcomp.jsp"
	tela="opcoes"
	caminho="<%=_pathEcar%>"
	gerarRelatorio="<%=Boolean.TRUE%>"
	exibirAbaFiltro="<%=Boolean.FALSE%>"
/> 

	<form name="form" method="post">
		<input type="hidden" name="codTipoAcompanhamento" value="<%=codTipoAcompanhamento%>">
		<input type="hidden" name="mesReferencia" value="<%=Pagina.getParamStr(request, "mesReferencia")%>">
		<input type="hidden" name="arisSelecionados" value="<%=Pagina.getParamStr(request, "arisSelecionados") %>">
		<input type="hidden" name="codAri" value="<%=Pagina.getParamStr(request, "codAri") %>">
	<%
	
	String _widthTabela = "15%";
	
	List itensBarra = new ArrayList();
	itensBarra.add("Modelos");
	itensBarra.add("Filtros");
	itensBarra.add("Formatos");
	
	int index = 2; //Formatos
	%>
	<util:barraLinksRelatorioItens
		itensBarra="<%=itensBarra%>"
		chamarPagina="relAcompOpcoes"
		indexAtivo="<%=index%>"
		semLinks="S"
	/>

	<util:barrabotoes btn3="Gerar Relatório"/>

	<table class="form" border="0">
		<tr>
			<td width="<%=_widthTabela%>">&nbsp;</td>
			<td>&nbsp;</td>
			<td>&nbsp;</td>
		</tr>
		<tr>
			<td width="<%=_widthTabela%>">&nbsp;</td>
			<td class="label">Título Padrão</td>
			<td><%=Pagina.trocaNull(configRel.getTituloCfgrel())%></td>
		</tr>
		<tr>
			<td width="<%=_widthTabela%>">&nbsp;</td>
			<td class="label">Título Customizado</td>
			<td><input type="text" name="tituloCustomizado" value="" size="50" maxlength="200"></td>
		</tr>
		<tr>
			<td width="<%=_widthTabela%>">&nbsp;</td>
			<td class="label"><%=(modeloRel != null) ? "" : "*"%> Ordem de Classificação</td>
			<td>
			<%
				if(modeloRel != null){
					out.println(Pagina.trocaNull(modeloRel.getClassifMrel()) + " (" + Pagina.trocaNull(modeloRel.getCodAlfaMrel()) + ")");
			%>
				<input type="hidden" name="opcaoModelo" value="<%=Pagina.trocaNull(modeloRel.getCodAlfaMrel())%>">
			<%
				}
				else {
			%>
				<select name="opcaoModelo">
					<option value=""></option>
					<option value="ECAR-001C">Estrutura</option>
					<option value="ECAR-001A">Órgão e Estrutura</option>
					<option value="ECAR-002A">Situação e Órgão</option>
				</select>
			<%
				}
			%>		
			</td>
		</tr>
		<tr>
			<td width="<%=_widthTabela%>">&nbsp;</td>
			<td class="label">Parecer</td>
			<td>
				<combo:ComboTag
				 	nome="tipoFuncAcompTpfa" 
				 	objeto="ecar.pojo.TipoFuncAcompTpfa" 
				 	label="labelTpfa" 
				 	value="codTpfa"
				 	order="labelTpfa"
				 	textoPadrao="Todos"
				 />
			</td>
		</tr>
		<tr>
			<td width="<%=_widthTabela%>">&nbsp;</td>
			<td class="label">Nota de Rodapé Padrão</td>
			<td><%=Pagina.trocaNull(configRel.getNotaRodapeCfgrel())%></td>
		</tr>
		<tr>
			<td width="<%=_widthTabela%>">&nbsp;</td>
			<td class="label">Nota de Rodapé Customizada</td>
			<td><input type="text" name="rodapeCustomizado" value="" size="50" maxlength="2000"></td>
		</tr>
		<tr>
			<td width="<%=_widthTabela%>">&nbsp;</td>
			<td class="label">Formato de Arquivo</td>
			<td>
				<select name="formatoRelatorio" id="id_formatoRelatorio" title="Formato de arquivo a ser apresentado.">
					<option value="PDF" selected="selected" >PDF</option>
					<option value="XLS">XLS (Excel)</option>
				</select>
			</td>
		</tr>
		<%if(pathEcar.contains("prve")) { //Tinha um ! antes%>
			<tr>
				<td width="<%=_widthTabela%>">&nbsp;</td>
				<td class="label">Exibir Indicadores de Resultado</td>
				<td><input type="radio" class="form_check_radio" name="indResultado" value="S" checked="checked" onclick="javascript:changeProjecao(form)">Sim
					<input type="radio" class="form_check_radio" name="indResultado" value="N" onclick="javascript:changeProjecao(form)">Não</td>
			</tr>
			<tr>
				<td width="<%=_widthTabela%>">&nbsp;</td>
				<td class="label">Exibir Projeção</td>
				<td><input type="radio" class="form_check_radio" name="indProjecao" value="S">Sim
					<input type="radio" class="form_check_radio" name="indProjecao" value="N" checked="checked">Não</td>
			</tr>
			<tr>
				<td width="<%=_widthTabela%>">&nbsp;</td>
				<td class="label">Exibir Quadro de Recursos Or&ccedil;ament&aacute;rio-Financeiros</td>
				<td><input type="radio" class="form_check_radio" name="evolucaoFinanceira" checked="checked" value="S">Sim
					<input type="radio" class="form_check_radio" name="evolucaoFinanceira" value="N">Não</td>
			</tr>
		<%} %>
		<tr><td class="label" colspan="3">&nbsp;</td></tr>
	</table>
	</form>
<%
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
<%@ include file="../include/ocultarAguarde.jsp"%>
<% /* Controla o estado do Menu (aberto ou fechado) */%>
<%@ include file="../include/estadoMenu.jsp"%>
<%@ include file="../include/mensagemAlert.jsp" %>
</html>

