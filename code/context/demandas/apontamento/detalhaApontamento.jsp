
<jsp:directive.page import="ecar.pojo.ApontamentoAnexo"/>
<%@ include file="../../login/validaAcesso.jsp"%>

<%@ taglib uri="/WEB-INF/ecar-util.tld" prefix="util" %>
<%@ taglib uri="/WEB-INF/ecar-combo.tld" prefix="combo" %>
<%@ taglib uri="/WEB-INF/ecar-input.tld" prefix="ecar" %>

<%@ page import="java.util.Set"%>
<%@ page import="java.util.HashSet"%>
<%@ page import="java.util.Comparator" %>
<%@ page import="java.util.Collections" %>

<%@ page import="ecar.pojo.EntidadeEnt" %>
<%@ page import="ecar.pojo.RegApontamentoRegda" %>
<%@ page import="ecar.pojo.RegDemandaRegd" %>

<%@ page import="ecar.dao.RegApontamentoDao" %>
<%@ page import="ecar.dao.RegDemandaDao" %>
<%@ page import="ecar.dao.ApontamentoAnexoDao" %>

<%@ page import="comum.util.Data"%>

<%@ include file="../../frm_global.jsp"%>

<%
session.removeAttribute("objPesquisa");
%> 


<%@page import="ecar.pojo.UsuarioUsu"%>
<%@page import="comum.util.Util"%><html lang="pt-br">
<head>
<%@ include file="../../include/meta.jsp"%>
<%@ include file="/titulo.jsp"%>
<link href="../../css/style.css" rel="stylesheet" type="text/css">
<script language="javascript" src="../../js/menu_retratil.js"></script>
<script language="javascript" src="../../js/focoInicial.js"></script>
<script language="javascript" src="../../js/datas.js"></script>
<script language="javascript" src="../../js/numero.js"></script>
<script language="javascript" src="../../js/popUp.js"></script>
<script language="javascript" src="../../js/validacoes.js"></script>
<script language="javascript">
function validar(form){
	if(!validaString(form.infoRegda,'Texto',true)){
		return(false);
	}

	return(true);
}
function aoClicarApontamento() {
	document.forms[0].action = "../registro/frm_cons.jsp";
	document.forms[0].submit();
}

function aoClicarVoltar(form) {
	document.forms[0].hidAcao.value = "";
	document.forms[0].submit();
}
function adicionarAnexo(form) {
	if(document.getElementById("arquivoApontamento").value != "") {
		form.action = "../apontamento/ctrlAnexo.jsp";
		form.hidAcaoAnexo.value = "adicionaAnexo";
		form.submit();
	} else {
		alert("Selecione um arquivo para upload!");
	}
}
function excluirAnexo(codAnexo) {
	document.forms[1].action = "../apontamento/ctrlAnexo.jsp";
	document.getElementById("hidAcaoAnexo").value = "excluirAnexo";
	document.getElementById("codAnexo").value = codAnexo;
	document.formMulti.submit();
}

function aoClicarExcluirAnexo(form){
	if(validarExclusao(form)){
		if(!confirm("Confirma a exclus�o?")){
			return(false);
		}
		
		numChecks = verCheckExc(form, "excluir");
		if(numChecks > 1) {
			for(i = 0; i < form.excluir.length; i++)
				if(form.excluir[i].checked)
					excluirAnexo(form.excluir[i].value);
		} else {
			excluirAnexo(document.getElementById("excluir").value);
		}	
	}
}

function validarExclusao(form){
	var algumMarcado = false;
	var numChecks = 0;

    numChecks = verCheckExc(form, "excluir");
	
    if (numChecks > 0) {
		if(numChecks > 1){
			for(i = 0; i < form.excluir.length; i++)
				if(form.excluir[i].checked)
					algumMarcado = true;	
		} else {
			algumMarcado = form.excluir.checked;
		}
	}
	
	if(!algumMarcado){
		alert("Pelo menos um registro deve ser selecionado.");
		return false;
	} else {
		return true;
	}
}

function verCheckExc(form, nome){
	var el = document.getElementsByTagName("INPUT");
	var i = 0, n = 0;
	while (i < el.length)
	{		
		if(el.item(i) != null){
			if (el.item(i).type == "checkbox" && el.item(i).name == nome){
				n++;
			}
		}
		i++;
	}
	
	return n;
}
</script>

</head>

<!-- sempre colocar o foco inicial no primeiro campo -->
<body>

<%@ include file="../../cabecalho.jsp" %>
<%@ include file="../../divmenu.jsp"%>
<%
try{
%>
<div id="conteudo">

<%
	RegDemandaDao regDemandaDao = new RegDemandaDao(request);
	RegDemandaRegd regDemanda = (RegDemandaRegd) regDemandaDao.buscar(RegDemandaRegd.class, Long.valueOf (Pagina.getParam(request, "codRegd")));
	RegApontamentoDao regApontamentoDao = new RegApontamentoDao(request);
	
	String acao = "detalhar";

	RegApontamentoRegda regApontamento;
	
	if (session.getAttribute("objRegApontamento") != null) {
		regApontamento = (RegApontamentoRegda) session.getAttribute("objRegApontamento");
		session.removeAttribute("objRegApontamento");
	} else {
		regApontamento = new RegApontamentoRegda();
	}
	
	if(request.getParameter("codRegda") != null && !request.getParameter("codRegda").equals("")) {
		regApontamento = (RegApontamentoRegda)regApontamentoDao.buscar(RegApontamentoRegda.class, new Long(request.getParameter("codRegda")));
	}
	
	VisaoDemandasVisDem visaoSessao = ((VisaoDemandasVisDem)request.getSession().getAttribute(VisaoDemandasVisDem.VISAO_SELECIONADA));
	String telaAssociacaoDemandas = Pagina.getParamStr(request, "telaAssociacaoDemandas");
	String codAba = Pagina.getParamStr(request, "codAba");
	String codIett = Pagina.getParamStr(request, "codIett");
	String ultimoIdLinhaDetalhado = Pagina.getParamStr(request, "ultimoIdLinhaDetalhado");
%>

<form name="voltar" id="voltar" method="post" action="../registro/frm_cons.jsp">
	<input type="hidden" name="aptCampo" value="<%=Pagina.getParam(request, "aptCampo")%>">
	<input type="hidden" name="aptOrdem" value="<%=Pagina.getParam(request, "aptOrdem")%>">
	<input type="hidden" name="aptRefazPesquisa" value="">
	<input type="hidden" name="codRegd" value="<%=regDemanda.getCodRegd()%>">
	<input type="hidden" name="codRegda" value="<%=regApontamento.getCodRegda()%>">
	<input type="hidden" name="hidAcao" id="hidAcao" value="">
	<input type="hidden" id="telaAssociacaoDemandas" name="telaAssociacaoDemandas" value="<%=telaAssociacaoDemandas%>">
	<input type="hidden" id="codAba" name="codAba" value="<%=codAba%>">
	<input type="hidden" id="codIett" name="codIett" value="<%=codIett%>">
	<input type="hidden" id="ultimoIdLinhaDetalhado" name="ultimoIdLinhaDetalhado" value="<%=ultimoIdLinhaDetalhado%>">
</form>

<form name="formMulti" id="formMulti" method="post" enctype="multipart/form-data">
	<input type="hidden" name="hidAcaoAnexo" id="hidAcaoAnexo" value="">
	
	<!-- TITULO -->
	<%@ include file="/titulo_tela.jsp"%>
	
	<div id="linkstop">
		<a href="../registro/lista.jsp">Demandas</a>
		&nbsp;|&nbsp;
		<a href="javascript:aoClicarApontamento();">Encaminhamentos</a>
	</div>
	
	<%@ include file="../registro/form_consulta.jsp"%>
		
	<br>
	
	<h1>Encaminhamento</h1>
	
	<util:barrabotoes voltar="Voltar"/>

	<table class="form">
		<%//Campos de ordena��o da listagem%>
		<input type="hidden" name="aptCampo" value="<%=Pagina.getParam(request, "aptCampo")%>">
		<input type="hidden" name="aptOrdem" value="<%=Pagina.getParam(request, "aptOrdem")%>">
		<input type="hidden" name="aptRefazPesquisa" value="">
		
		<input type="hidden" name="codRegd" value="<%=regDemanda.getCodRegd()%>">
		<input type="hidden" name="codRegda" value="<%=regApontamento.getCodRegda()%>">
		<input type="hidden" name="codAnexo" id="codAnexo" value="">
		<tr>
			<td class="label">Data do Encaminhamento</td>
			<td width="90%"><%=Data.parseDate(regApontamento.getDataRegda())%></td>
		</tr>
		<tr>
			<td class="label">Usu�rio</td>
			<td><%=Pagina.trocaNull(regApontamento.getUsuarioUsu().getNomeUsu())%></td>
		</tr>
		<tr>
			<td class="label">Coment�rio</td>
			<td><%=Pagina.trocaNull(regApontamento.getInfoRegda()).replaceAll("\r\n", "<br>")%></td>
		</tr>
		<tr><td class="label" colspan="2">&nbsp;</td></tr>
    	<tr>
			<td class="label">Arquivos Anexos:</td>
		<%
				ApontamentoAnexoDao anexoDao = new ApontamentoAnexoDao(request);
				List anexosApontamento = anexoDao.getAnexosApontamentoRegda(regApontamento);
				String pathRaiz = configura.getRaizUpload();
				String pathAnexo = configura.getUploadAnexos();
				UsuarioUsu usuarioImagem = null;
				String hashNomeArquivo = null;
				
				if(anexosApontamento != null && !anexosApontamento.isEmpty()) {
					Iterator anexosApontamentosIt = anexosApontamento.iterator();
					while(anexosApontamentosIt.hasNext()) {
						ApontamentoAnexo anexoApto = (ApontamentoAnexo)anexosApontamentosIt.next();
						
						usuarioImagem = ((ecar.login.SegurancaECAR)request.getSession().getAttribute("seguranca")).getUsuario();  
						
						// c�digo de tratamento de permiss�o de acesso a arquivo
						hashNomeArquivo = Util.calcularHashNomeArquivo(pathRaiz, anexoApto.getSrcAnexo());
						Util.adicionarMapArquivosAtuaisUsuarios(usuarioImagem, hashNomeArquivo, pathRaiz, anexoApto.getSrcAnexo());	
				
				%>
					<tr>
						<td></td>
						<td>
							<input type="checkbox" name="excluir" id="excluir" value="<%=anexoApto.getCod()%>">
							<!-- Mostra o arquivo anexo com op��o de download -->
							<a href="<%=request.getContextPath()%>/DownloadFile?RemoteFile=<%=hashNomeArquivo%>"><%=anexoApto.getSrcAnexo()%></a>
						</td>
					</tr>
				<%
					}
				} else { %>
					<tr>	
					<td></td>
					<td>Nenhum anexo cadastrado para o apontamento!
					</tr>
				<%}
				 %>
		
		<%
			
			if(anexosApontamento != null && !anexosApontamento.isEmpty() && (visaoSessao==null || (visaoSessao.getEhPermitidoExcluirAnexoApontamento().equals("S")))) {
		%>
		<tr>
			<td>
			</td><td>
				<input type="button" class="botao" value="Excluir Arquivo" onclick="aoClicarExcluirAnexo(form);">
			</td>
		</tr>
		<%
			}
		
		if(visaoSessao==null || (visaoSessao.getEhPermitidoIncluirAnexoApontamento().equals("S"))) {
		%>
			<tr><td class="label" colspan="2">&nbsp;</td></tr>
	    	<tr class="label" align="left">
	    		<td class="label">Upload de arquivo:</td>
	    		<td align="left"><input type="file" name="arquivoApontamento" id="arquivoApontamento">
	    		
	    		<input type="button" class="botao" value="Salvar Arquivo" onclick="javascript:adicionarAnexo(form)"></td>
	    	</tr>
		<%
			}
		%>
    	
    		
    		
    		
    </table>
    
    <%@ include file="../../include/estadoMenu.jsp"%>

	<util:barrabotoes voltar="Voltar"/>
	
</form>

</div>
<%
} catch (Exception e){
%>
	<%@ include file="/excecao.jsp"%>
<%
}
%>
</body>
<%@ include file="../../include/mensagemAlert.jsp"%>
</html>