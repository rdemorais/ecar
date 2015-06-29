
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

<%@page import="ecar.dao.RegDemandaAnexoDao"%>
<%@page import="ecar.pojo.RegDemandaAnexoRegdan"%><html lang="pt-br">
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
String codRegdan = "";
String hidAcao = "";
String codAnexoDemanda = "";
String descricaoAnexoDemanda = "";

String telaAssociacaoDemandas = "";
String codAba = "";
String codIett = "";
String ultimoIdLinhaDetalhado = "";

if(isFormUpload) {
	campos = FileUpload.criaListaCampos(request);
	aptCampo = String.valueOf(FileUpload.verificaValorCampo(campos,"aptCampo"));
	aptOrdem = String.valueOf(FileUpload.verificaValorCampo(campos,"aptOrdem"));
	codRegd = String.valueOf(FileUpload.verificaValorCampo(campos,"codRegd"));
	codRegdan = String.valueOf(FileUpload.verificaValorCampo(campos,"codRegdan"));
	hidAcao = String.valueOf(FileUpload.verificaValorCampo(campos,"hidAcao"));
	descricaoAnexoDemanda = String.valueOf(FileUpload.verificaValorCampo(campos,"descricaoAnexoDemanda"));
	telaAssociacaoDemandas = String.valueOf(FileUpload.verificaValorCampo(campos,"telaAssociacaoDemandas"));
	codAba = String.valueOf(FileUpload.verificaValorCampo(campos,"codAba"));
	codIett = String.valueOf(FileUpload.verificaValorCampo(campos,"codIett"));
	ultimoIdLinhaDetalhado = String.valueOf(FileUpload.verificaValorCampo(campos,"ultimoIdLinhaDetalhado"));
} else {
	aptCampo = Pagina.getParam(request, "aptCampo");
	aptOrdem = Pagina.getParam(request, "aptOrdem");
	codRegd = Pagina.getParam(request, "codRegd");
	codRegdan = Pagina.getParam(request, "codRegdan");
	hidAcao = Pagina.getParam(request, "hidAcao");
	descricaoAnexoDemanda = Pagina.getParam(request, "descricaoAnexoDemanda");
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
	<input type="hidden" name="codRegdan" value="<%=codRegdan%>">
	
	<input type="hidden" id="telaAssociacaoDemandas" name="telaAssociacaoDemandas" value="<%=telaAssociacaoDemandas%>">
	<input type="hidden" id="codAba" name="codAba" value="<%=codAba%>">
	<input type="hidden" id="codIett" name="codIett" value="<%=codIett%>">
	<input type="hidden" id="ultimoIdLinhaDetalhado" name="ultimoIdLinhaDetalhado" value="<%=ultimoIdLinhaDetalhado%>">
</form>

<%
	RegDemandaAnexoRegdan regDemandaAnexo = null;
	RegDemandaRegd regDemanda = null;
	RegDemandaDao regDemandaDao = new RegDemandaDao(request);
	RegDemandaAnexoDao regDemandaAnexoDao = new RegDemandaAnexoDao(request);


	Mensagem mensagem = new Mensagem(application);
	
	String msg = null;
	String submit = null;
	
	/* default sempre refazer a pesquisa */
	boolean incluir = "incluir".equalsIgnoreCase(hidAcao);
	boolean excluir = "excluir".equalsIgnoreCase(hidAcao);
	boolean alterar = "alterar".equalsIgnoreCase(hidAcao);
	boolean cancelar = "cancelar".equalsIgnoreCase(hidAcao);
	
	if (incluir)
		submit = "../registro/frm_cons.jsp?tabAtual=Anexos";
	else if(excluir)
		submit = "../registro/frm_cons.jsp?tabAtual=Anexos";
	else if(cancelar)
		submit = "../registro/frm_cons.jsp?tabAtual=Anexos";
	else if(alterar)
		submit = "../registro/frm_cons.jsp?tabAtual=Anexos";
	else
		submit = "frm_inc.jsp";
	
	regDemanda = (RegDemandaRegd) regDemandaDao.buscar(
			RegDemandaRegd.class, Long.valueOf(codRegd));

	if(codRegdan != null && !codRegdan.equals("") && !codRegdan.equals("null")) {
		regDemandaAnexo = (RegDemandaAnexoRegdan)regDemandaAnexoDao.buscar(
				RegDemandaAnexoRegdan.class, Long.valueOf(codRegdan));
	}
	
	/* obtem os caminhos fisicos para gravacao do arquivo a partir do arquivo de propriedades */
	ConfiguracaoCfg configura = (ConfiguracaoCfg)session.getAttribute("configuracao");
	String pathRaiz = configura.getRaizUpload();//_msg.getPathUploadRaiz("path .upload.raiz");
	String pathAnexo = configura.getUploadAnexos(); //_msg.getMensagem("path .upload.anexos");
	
	if(incluir) {
			
		regDemandaAnexo = new RegDemandaAnexoRegdan();
		regDemandaAnexo.setRegDemanda(regDemanda);
		regDemandaAnexo.setDescricao(descricaoAnexoDemanda);
		regDemandaAnexo.setDataInclusao(new java.sql.Date(System.currentTimeMillis()));
		
		/* realiza o upload do arquivo */
        Iterator it = campos.iterator();  
		boolean salvou = false;
        while(it.hasNext()){
            FileItem fileItem = (FileItem) it.next();             
            if(!fileItem.isFormField() && !fileItem.getName().equals("")){   
            	regDemandaAnexo.setSrcAnexo(FileUpload.getNomeArquivo(fileItem));
                File file = FileUpload.salvarNoDisco(fileItem, FileUpload.getPathFisico(pathRaiz, pathAnexo,  FileUpload.getNomeArquivo(fileItem)));
                regDemandaAnexo.setSrcAnexo(file.getName());
                salvou = true;
            }
        }
        
        //verifica se o arquivo existe 
        if (salvou){
        	regDemandaAnexoDao.salvar(regDemandaAnexo);
        	msg = _msg.getMensagem("registroAnexoDemanda.inclusao.sucesso");
        } else {
        	msg = _msg.getMensagem("erro.arquivoNaoEncontrado");
        }
  		
  		
		
	
		
	} else if(excluir) {
		String codigosParaExcluir[] = request.getParameterValues("excluir");
		if (codigosParaExcluir!=null && codigosParaExcluir.length>0) {
			for(int i=0;i<codigosParaExcluir.length;i++) {
				regDemandaAnexo = (RegDemandaAnexoRegdan)regDemandaAnexoDao.buscar(RegDemandaAnexoRegdan.class, new Long(codigosParaExcluir[i]));
				FileUpload.apagarArquivo(FileUpload.getPathFisicoCompleto(pathRaiz, pathAnexo, regDemandaAnexo.getSrcAnexo()));
				regDemandaAnexoDao.excluir(regDemandaAnexo);
			}
		} else if (codRegdan!=null && codRegdan.length()>0) {
			regDemandaAnexo = (RegDemandaAnexoRegdan)regDemandaAnexoDao.buscar(RegDemandaAnexoRegdan.class, new Long(codRegdan));
			FileUpload.apagarArquivo(FileUpload.getPathFisicoCompleto(pathRaiz, pathAnexo, regDemandaAnexo.getSrcAnexo()));
			regDemandaAnexoDao.excluir(regDemandaAnexo);
		}
		msg = _msg.getMensagem("registroAnexoDemanda.exclusao.sucesso");

	}  else if(alterar) {
		if(codRegdan != null && codRegdan.length() > 0) {
			
			regDemandaAnexo = (RegDemandaAnexoRegdan)regDemandaAnexoDao.buscar(RegDemandaAnexoRegdan.class, new Long(codRegdan));
			// só pode alterar a descrição do anexo da demanda
			regDemandaAnexo.setDescricao(descricaoAnexoDemanda);

			regDemandaAnexoDao.alterar(regDemandaAnexo);
	        msg = _msg.getMensagem("registroAnexoDemanda.alteracao.sucesso");
		}
	} 
	
	if (msg != null)
			Mensagem.setInput(_jspx_page_context, "document.form.msgPesquisa.value", msg);
%>


	<script language="javascript">
		//Após executar alguma ação refazer a listagem e limpa os campos
		document.form.action = "<%=submit%>";
		document.form.submit();
	</script> 

</body>
</html>