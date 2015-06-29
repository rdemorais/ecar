<%@ include file="../../login/validaAcesso.jsp"%>
<%@ include file="../../frm_global.jsp"%>

<%@ page import="ecar.pojo.RegDemandaRegd" %>
<%@ page import="ecar.dao.RegDemandaDao" %>
<%@ page import="ecar.pojo.RegApontamentoRegda" %>
<%@ page import="ecar.dao.RegApontamentoDao" %>
<%@ page import="ecar.pojo.UsuarioUsu" %>
<%@ page import="ecar.dao.UsuarioDao" %>

<%@ page import="comum.util.Data" %> 
<%@ page import="java.util.Date" %>


<%@page import="comum.util.FileUpload"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%><html lang="pt-br">
<head>
<%@ include file="../../include/meta.jsp"%>
<%@ include file="/titulo.jsp"%>
</head>
<body>
<%

boolean isFormUpload = FileUpload.isMultipartContent(request);
List campos = new ArrayList();
String aptCampo = "";
String aptOrdem = "";
String aptRefazPesquisa = "";
String codRegd = "";
String codRegda = "";
String hidAcao = "";
String descricaoApontamento = "";
String msg = null;
String telaAssociacaoDemandas = "";
String codAba = "";
String codIett = "";
String ultimoIdLinhaDetalhado = "";

if(isFormUpload) {
	campos = FileUpload.criaListaCampos(request);
	aptCampo = String.valueOf(FileUpload.verificaValorCampo(campos,"aptCampo"));
	aptOrdem = String.valueOf(FileUpload.verificaValorCampo(campos,"aptOrdem"));
	aptRefazPesquisa = String.valueOf(FileUpload.verificaValorCampo(campos,"aptRefazPesquisa"));
	codRegd = String.valueOf(FileUpload.verificaValorCampo(campos,"codRegd"));
	codRegda = String.valueOf(FileUpload.verificaValorCampo(campos,"codRegda"));
	hidAcao = String.valueOf(FileUpload.verificaValorCampo(campos,"hidAcao"));
	descricaoApontamento = String.valueOf(FileUpload.verificaValorCampo(campos,"infoRegda"));	
	telaAssociacaoDemandas = String.valueOf(FileUpload.verificaValorCampo(campos,"telaAssociacaoDemandas"));
	codAba = String.valueOf(FileUpload.verificaValorCampo(campos,"codAba"));
	codIett = String.valueOf(FileUpload.verificaValorCampo(campos,"codIett"));
	ultimoIdLinhaDetalhado = String.valueOf(FileUpload.verificaValorCampo(campos,"ultimoIdLinhaDetalhado"));
} else {
	
	aptCampo = Pagina.getParam(request, "aptCampo");
	aptOrdem = Pagina.getParam(request, "aptOrdem");
	aptRefazPesquisa = String.valueOf(FileUpload.verificaValorCampo(campos,"aptRefazPesquisa"));
	codRegd = Pagina.getParam(request, "codRegd");
	codRegda = Pagina.getParam(request, "codRegdan");
	hidAcao = Pagina.getParam(request, "hidAcao");
	descricaoApontamento = Pagina.getParam(request, "infoRegda");
	telaAssociacaoDemandas = Pagina.getParamStr(request, "telaAssociacaoDemandas");
	codAba = Pagina.getParamStr(request, "codAba");
	codIett = Pagina.getParamStr(request, "codIett");
	ultimoIdLinhaDetalhado = Pagina.getParamStr(request, "ultimoIdLinhaDetalhado");
}	
%>
<form name="form" method="post">
    <!-- campo de controle para a navegação de registros -->
	<input type="hidden" name="msgOperacao" value=""> 
	<input type="hidden" name="msgPesquisa" value="">	
	<input type="hidden" name="hidPesquisou" value="">
	
	<%//Campos de ordenação da listagem%>
	<input type="hidden" name="aptCampo" value="<%=aptCampo%>">
	<input type="hidden" name="aptOrdem" value="<%=aptOrdem%>">
	<input type="hidden" name="aptRefazPesquisa" value="<%=aptRefazPesquisa%>">

	<input type="hidden" name="codRegd" value="<%=codRegd%>">
	
	<input type="hidden" name="hidAcao" value="<%=hidAcao%>">
	
	<input type="hidden" id="telaAssociacaoDemandas" name="telaAssociacaoDemandas" value="<%=telaAssociacaoDemandas%>">
	<input type="hidden" id="codAba" name="codAba" value="<%=codAba%>">
	<input type="hidden" id="codIett" name="codIett" value="<%=codIett%>">
	<input type="hidden" id="ultimoIdLinhaDetalhado" name="ultimoIdLinhaDetalhado" value="<%=ultimoIdLinhaDetalhado%>">
</form>
<%
	RegApontamentoRegda regApontamento = new RegApontamentoRegda();
	RegApontamentoDao regApontamentoDao = new RegApontamentoDao(request);

	Mensagem mensagem = new Mensagem(application);
	
	String submit = "../registro/frm_cons.jsp?tabAtual=Encaminhamento";
//	String origem = Pagina.getParam(request, "origem");
	
	/* default sempre refazer a pesquisa */
	boolean incluir = "incluir".equalsIgnoreCase(hidAcao);
	boolean excluir = "excluir".equalsIgnoreCase(hidAcao);
	boolean alterar = "alterar".equalsIgnoreCase(hidAcao);
	
	RegDemandaDao regDemandaDao = new RegDemandaDao(request);
	RegDemandaRegd regDemanda = null;
	if (codRegd!=null && codRegd.length()>0)
		regDemanda = (RegDemandaRegd) regDemandaDao.buscar(RegDemandaRegd.class, Long.valueOf(codRegd));
	
	if (incluir) {
		try {
			regApontamento.setRegDemandaRegd(regDemanda);
			regApontamento.setInfoRegda(descricaoApontamento);
			regApontamento.setDataRegda(Data.getDataAtual());
			regApontamento.setUsuarioUsu(((ecar.login.SegurancaECAR) session.getAttribute("seguranca")).getUsuario());
			
			regApontamentoDao.salvar(regApontamento);
			
			msg = _msg.getMensagem("registroApontamento.inclusao.sucesso");
			session.removeAttribute("objRegApontamento");
		} catch (ECARException e) {
			org.apache.log4j.Logger.getLogger(this.getClass()).error(e);
			session.setAttribute("objRegApontamento", regDemanda);
			msg = _msg.getMensagem(e.getMessageKey());
			//}
		} catch (Exception e){
		%>
			<%@ include file="/excecao.jsp"%>
		<%
		}
	} else if (excluir) {
		try {
			String codigosParaExcluir[] = request.getParameterValues("excluir");
			regApontamentoDao.excluir(codigosParaExcluir);
			msg = _msg.getMensagem("registroApontamento.exclusao.sucesso");
		} catch (ECARException e) {
			org.apache.log4j.Logger.getLogger(this.getClass()).error(e);
			msg = _msg.getMensagem(e.getMessageKey());
		} catch (Exception e){
		%>
			<%@ include file="/excecao.jsp"%>
		<%
		}	
	} else if (alterar) {
		try {
			
			regApontamento = (RegApontamentoRegda) regApontamentoDao.buscar(RegApontamentoRegda.class, Long.valueOf(codRegda));
			
			regApontamento.setInfoRegda(descricaoApontamento.trim());
			
			regApontamentoDao.alterar(regApontamento);
			
			msg = _msg.getMensagem("registroApontamento.alteracao.sucesso");
			session.removeAttribute("objRegApontamento");
			
//			if (!"".equals(origem)) {
	//			submit = origem;
		//	}
			
			%>
			<script language="JavaScript">
				//Após executar alguma ação refazer a listagem
				document.form.refazPesquisa.value = "refaz";
				document.form.refazOrdenacao.value = "refaz";
			</script>
			<%
		} catch (ECARException e) {
			org.apache.log4j.Logger.getLogger(this.getClass()).error(e);
			session.setAttribute("objRegDemanda", regDemanda);
			msg = _msg.getMensagem(e.getMessageKey());
			
			//if (!"".equals(origem)) {
				//submit = origem;
			//}
		} catch (Exception e){
		%>
			<%@ include file="/excecao.jsp"%>
		<%
		}
	} 
	
	if (msg != null) {
			Mensagem.setInput(_jspx_page_context, "document.form.msgPesquisa.value", msg); 
	}
%>

	<script language="javascript">
		
		document.form.action = "<%=submit%>";
		document.form.submit();
	</script> 

</body>
</html>

