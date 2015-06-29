
<jsp:directive.page import="ecar.pojo.ApontamentoAnexo"/>
<jsp:directive.page import="ecar.dao.ApontamentoAnexoDao"/>
<jsp:directive.page import="comum.util.FileUpload"/>
<jsp:directive.page import="java.util.ArrayList"/>
<jsp:directive.page import="java.util.List"/>
<jsp:directive.page import="java.util.Iterator"/>
<jsp:directive.page import="java.io.File"/>

<jsp:directive.page import="org.apache.commons.fileupload.FileItem"/><%@ include file="../../login/validaAcesso.jsp"%>
<%@ include file="../../frm_global.jsp"%>

<%@ page import="ecar.pojo.RegDemandaRegd" %>
<%@ page import="ecar.dao.RegDemandaDao" %>
<%@ page import="ecar.pojo.RegApontamentoRegda" %>
<%@ page import="ecar.dao.RegApontamentoDao" %>
<%@ page import="ecar.pojo.UsuarioUsu" %>
<%@ page import="ecar.dao.UsuarioDao" %>

<%@ page import="comum.util.Data" %> 
<%@ page import="java.util.Date" %>

<html lang="pt-br">
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
String codRegd = "";
String codRegda = "";
String hidAcao = "";
String codAnexo = "";
String origem = ""; 
String descricaoApontamento = "";
String telaAssociacaoDemandas = "";
String codAba = "";
String codIett = "";
String ultimoIdLinhaDetalhado = "";

if(isFormUpload) {
	campos = FileUpload.criaListaCampos(request);
	aptCampo = String.valueOf(FileUpload.verificaValorCampo(campos,"aptCampo"));
	aptOrdem = String.valueOf(FileUpload.verificaValorCampo(campos,"aptOrdem"));
	codRegd = String.valueOf(FileUpload.verificaValorCampo(campos,"codRegd"));
	codRegda = String.valueOf(FileUpload.verificaValorCampo(campos,"codRegda"));
	hidAcao = String.valueOf(FileUpload.verificaValorCampo(campos,"hidAcaoAnexo"));
	codAnexo = String.valueOf(FileUpload.verificaValorCampo(campos,"codAnexo"));
	origem = String.valueOf(FileUpload.verificaValorCampo(campos,"origem"));
	descricaoApontamento = String.valueOf(FileUpload.verificaValorCampo(campos,"infoRegda"));	
	telaAssociacaoDemandas = String.valueOf(FileUpload.verificaValorCampo(campos,"telaAssociacaoDemandas"));
	codAba = String.valueOf(FileUpload.verificaValorCampo(campos,"codAba"));
	codIett = String.valueOf(FileUpload.verificaValorCampo(campos,"codIett"));
	ultimoIdLinhaDetalhado = String.valueOf(FileUpload.verificaValorCampo(campos,"ultimoIdLinhaDetalhado"));
} else {
	aptCampo = Pagina.getParam(request, "aptCampo");
	aptOrdem = Pagina.getParam(request, "aptOrdem");
	codRegd = Pagina.getParam(request, "codRegd");
	codRegda = Pagina.getParam(request, "codRegd");
	hidAcao = Pagina.getParam(request, "hidAcaoAnexo");
	codAnexo = Pagina.getParam(request, "codAnexo");
	origem = Pagina.getParam(request, "origem");
	descricaoApontamento = String.valueOf(FileUpload.verificaValorCampo(campos,"infoRegda"));	
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
	<input type="hidden" name="aptRefazPesquisa" value="">

	<input type="hidden" name="codRegd" value="<%=codRegd%>">
	<input type="hidden" name="codRegda" value="<%=codRegda%>">
	<input type="hidden" id="telaAssociacaoDemandas" name="telaAssociacaoDemandas" value="<%=telaAssociacaoDemandas%>">
	<input type="hidden" id="codAba" name="codAba" value="<%=codAba%>">
	<input type="hidden" id="codIett" name="codIett" value="<%=codIett%>">
	<input type="hidden" id="ultimoIdLinhaDetalhado" name="ultimoIdLinhaDetalhado" value="<%=ultimoIdLinhaDetalhado%>">
</form>

<%
	RegApontamentoRegda regApontamento = new RegApontamentoRegda();
	RegApontamentoDao regApontamentoDao = new RegApontamentoDao(request);
	RegDemandaDao regDemandaDao = new RegDemandaDao(request);
	ApontamentoAnexoDao apontAnexoDao = new ApontamentoAnexoDao(request);

	Mensagem mensagem = new Mensagem(application);
	
	String msg = null;
	String submit = "../apontamento/detalhaApontamento.jsp";
	
	// origem é a página de alteração
	if (origem!=null && origem.equals("alteracaoEncaminhamento")) {
		submit = "../apontamento/frm_alt.jsp";
	} 
	
	/* default sempre refazer a pesquisa */
	boolean incluir = "adicionaAnexo".equalsIgnoreCase(hidAcao);
	boolean excluir = "excluirAnexo".equalsIgnoreCase(hidAcao);
	
	RegDemandaRegd regDemanda = (RegDemandaRegd) regDemandaDao.buscar(
			RegDemandaRegd.class, Long.valueOf(codRegd));

	if(codRegda != null && !codRegda.equals("")) {
		regApontamento = (RegApontamentoRegda)regApontamentoDao.buscar(
				RegApontamentoRegda.class, Long.valueOf(codRegda));
	}
	
	// se mudou a informação, é necessário preservar a informação alterada
	if (descricaoApontamento!=null && regApontamento!=null && !descricaoApontamento.equals(regApontamento.getInfoRegda())) {
		regApontamento = (RegApontamentoRegda) session.getAttribute("objRegApontamento");
		regApontamento.setInfoRegda(descricaoApontamento);
	}
	
	/* obtem os caminhos fisicos para gravacao do arquivo a partir do arquivo de propriedades */
	ConfiguracaoCfg configura = (ConfiguracaoCfg)session.getAttribute("configuracao");
	String pathRaiz = configura.getRaizUpload();//_msg.getPathUploadRaiz("path .upload.raiz");
	String pathAnexo = configura.getUploadAnexos(); //_msg.getMensagem("path .upload.anexos");
	
	if(incluir) {
			
		ApontamentoAnexo apontAnexo = new ApontamentoAnexo();
		apontAnexo.setRegApontamentoRegda(regApontamento);
		
		/* realiza o upload do arquivo */
        Iterator it = campos.iterator();  
        boolean salvou = false;
		while(it.hasNext()){
            FileItem fileItem = (FileItem) it.next();             
            if(!fileItem.isFormField() && !fileItem.getName().equals("")){   
                apontAnexo.setSrcAnexo(FileUpload.getNomeArquivo(fileItem));
                FileUpload.salvarNoDisco(fileItem, FileUpload.getPathFisicoCompleto(pathRaiz, pathAnexo,  FileUpload.getNomeArquivo(fileItem)));
                salvou = true;
            }
        }
        
        //verifica se o arquivo existe 
        if (salvou){
        	apontAnexoDao.salvar(apontAnexo);
        	msg = _msg.getMensagem("registroApontamentoAnexo.inclusao.sucesso");
        } else {
        	msg = _msg.getMensagem("erro.arquivoNaoEncontrado");
        }
  		
  		
		
	
		
	} else if(excluir) {
		ApontamentoAnexo apontAnexo = (ApontamentoAnexo)apontAnexoDao.buscar(ApontamentoAnexo.class, new Long(codAnexo));
		FileUpload.apagarArquivo(FileUpload.getPathFisicoCompleto(pathRaiz, pathAnexo, apontAnexo.getSrcAnexo()));
		apontAnexoDao.excluir(apontAnexo);
		msg = _msg.getMensagem("registroApontamentoAnexo.exclusao.sucesso");
	}
	
	if (msg != null)
			Mensagem.setInput(_jspx_page_context, "document.form.msgPesquisa.value", msg);
%>

	<script language="javascript">
		//Após executar alguma ação refazer a listagem
		document.form.aptRefazPesquisa.value = "refaz";
		
		document.form.action = "<%=submit%>";
		document.form.submit();
	</script> 

</body>
</html>