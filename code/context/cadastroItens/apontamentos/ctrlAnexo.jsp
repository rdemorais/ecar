
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
String codIett = "";
String codAba = "";

if(isFormUpload) {
	campos = FileUpload.criaListaCampos(request);
	aptCampo = String.valueOf(FileUpload.verificaValorCampo(campos,"aptCampo"));
	aptOrdem = String.valueOf(FileUpload.verificaValorCampo(campos,"aptOrdem"));
	codRegd = String.valueOf(FileUpload.verificaValorCampo(campos,"codRegd"));
	codRegda = String.valueOf(FileUpload.verificaValorCampo(campos,"codRegda"));
	hidAcao = String.valueOf(FileUpload.verificaValorCampo(campos,"hidAcaoAnexo"));
	codAnexo = String.valueOf(FileUpload.verificaValorCampo(campos,"codAnexo"));
	codIett = String.valueOf(FileUpload.verificaValorCampo(campos,"codIett"));
	codAba = String.valueOf(FileUpload.verificaValorCampo(campos, "codAba"));
} else {
	aptCampo = Pagina.getParam(request, "aptCampo");
	aptOrdem = Pagina.getParam(request, "aptOrdem");
	codRegd = Pagina.getParam(request, "codRegd");
	codRegda = Pagina.getParam(request, "codRegd");
	hidAcao = Pagina.getParam(request, "hidAcaoAnexo");
	codAnexo = Pagina.getParam(request, "codAnexo");
	codIett = Pagina.getParam(request, "codIett");
	codAba = Pagina.getParamStr(request, "codAba");
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
	
	<!--variaveis necessarias para voltar para a aba associacao de demandas  -->
	<input type=hidden name="codIett" value="<%=codIett%>">
	<input type=hidden name="codAba" value="<%=codAba%>">
</form>

<%
	RegApontamentoRegda regApontamento = new RegApontamentoRegda();
	RegApontamentoDao regApontamentoDao = new RegApontamentoDao(request);
	RegDemandaDao regDemandaDao = new RegDemandaDao(request);
	ApontamentoAnexoDao apontAnexoDao = new ApontamentoAnexoDao(request);

	Mensagem mensagem = new Mensagem(application);
	
	String msg = null;
	String submit = "../apontamentos/detalhaApontamento.jsp";
	
	/* default sempre refazer a pesquisa */
	boolean incluir = "adicionaAnexo".equalsIgnoreCase(hidAcao);
	boolean excluir = "excluirAnexo".equalsIgnoreCase(hidAcao);
	
	RegDemandaRegd regDemanda = (RegDemandaRegd) regDemandaDao.buscar(
			RegDemandaRegd.class, Long.valueOf(codRegd));

	if(codRegda != null && !codRegda.equals("")) {
		regApontamento = (RegApontamentoRegda)regApontamentoDao.buscar(
				RegApontamentoRegda.class, Long.valueOf(codRegda));
	}
	
	/* obtem os caminhos fisicos para gravacao do arquivo a partir do arquivo de propriedades */
	ecar.pojo.ConfiguracaoCfg configura = new ecar.dao.ConfiguracaoDao(request).getConfiguracao();
	String pathRaiz = configura.getRaizUpload();//_msg.getPathUploadRaiz("path .upload.raiz");
	String pathAnexo = configura.getUploadAnexos(); //_msg.getMensagem("path .upload.anexos");
	
	if(incluir) {
			
		ApontamentoAnexo apontAnexo = new ApontamentoAnexo();
		apontAnexo.setRegApontamentoRegda(regApontamento);
		
		/* realiza o upload do arquivo */
        Iterator it = campos.iterator();  
        while(it.hasNext()){
            FileItem fileItem = (FileItem) it.next();             
            if(!fileItem.isFormField() && !fileItem.getName().equals("")){   
                apontAnexo.setSrcAnexo(FileUpload.getNomeArquivo(fileItem));
                FileUpload.salvarNoDisco(fileItem, FileUpload.getPathFisico(pathRaiz, pathAnexo,  FileUpload.getNomeArquivo(fileItem)));
            }
        }
        
        //verifica se o arquivo existe 
        File file = new File(FileUpload.getPathFisico(pathRaiz, pathAnexo, apontAnexo.getSrcAnexo()));
        if (file.exists()){
        	apontAnexoDao.salvar(apontAnexo);
        	msg = _msg.getMensagem("registroApontamentoAnexo.inclusao.sucesso");
        } else {
        	msg = _msg.getMensagem("erro.arquivoNaoEncontrado");
        }
		
		
	} else if(excluir) {
		ApontamentoAnexo apontAnexo = (ApontamentoAnexo)apontAnexoDao.buscar(ApontamentoAnexo.class, new Long(codAnexo));
		FileUpload.apagarArquivo(FileUpload.getPathFisico(pathRaiz, pathAnexo, apontAnexo.getSrcAnexo()));
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