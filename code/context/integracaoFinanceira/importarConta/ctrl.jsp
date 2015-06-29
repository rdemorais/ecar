<%@ include file="../../login/validaAcesso.jsp"%>
<%@ include file="../../frm_global.jsp"%>

<%@ page import="comum.util.FileUpload"%>

<%@ page import="java.io.File" %>
<%@ page import="java.util.Iterator" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.Date" %>
<%@ page import="java.util.ArrayList"%>
<%@ page import="ecar.pojo.UsuarioUsu" %>
<%@ page import="java.text.SimpleDateFormat" %>

<%@ page import="org.apache.commons.fileupload.FileItem"%>
<%@ page import="ecar.dao.ConfiguracaoDao" %>
<%@ page import="ecar.pojo.ConfiguracaoCfg" %>
<%@ page import="ecar.dao.ConfigSisExecFinanDao" %>
<%@ page import="ecar.pojo.EfImportOcorrenciasEfio" %>
<%@ page import="comum.util.Data" %>


<html lang="pt-br">
<head>
<%
	if("S".equals(Pagina.getParamStr(request, "fechar"))){
%>
	<SCRIPT language="javascript:document.window.close()"></SCRIPT>
<%
	}
%>
<%@ include file="../../include/meta.jsp"%>
<%@ include file="/titulo.jsp"%>
</head>
<body>

<%
	UsuarioUsu usuario = ((ecar.login.SegurancaECAR)session.getAttribute("seguranca")).getUsuario();
	Mensagem mensagem = new Mensagem(application);
	//ConfiguracaoCfg configuracao = new ConfiguracaoDao(request).getConfiguracao();
	ConfigSisExecFinanDao csefDao = new ConfigSisExecFinanDao(request);
	
	List campos = new ArrayList();
	campos = FileUpload.criaListaCampos(request); 

	String hidAcao = FileUpload.verificaValorCampo(campos, "hidAcao");

	String mostrarOcorrenciaAnterior = "";
	String ocorrenciaAnterior = "";
	if("mostrarAnterior".equals(hidAcao)){
		mostrarOcorrenciaAnterior = FileUpload.verificaValorCampo(campos, "mostrarOcorrenciaAnterior");
		ocorrenciaAnterior = FileUpload.verificaValorCampo(campos, "ocorrenciasAnteriores");
	}
%>
<form name="form" method="post">
    <!-- campo de controle para a navegação de registros -->
	<input type="hidden" name="msgOperacao" value=""> 
	<input type="hidden" name="msgPesquisa" value="">	
	<input type="hidden" name="exibeImprimir" value=""> 
	<input type="hidden" name="mostrarOcorrenciaAnterior" value="<%=mostrarOcorrenciaAnterior%>">
	<input type="hidden" name="ocorrenciaAnterior" value="<%=ocorrenciaAnterior%>">
<%
	 
	String msg = null;
	String exibeImprimir = null;
	String submit = "importarConta.jsp";
	
	if ("criarContas".equals(hidAcao)) {
		
		try
		{
			//String[] contasInexistentes = (String[]) request.getParameterValues("contasInexistentes");
			Object[] contasInexistentes = (Object[]) FileUpload.verificaValorCampoArray(campos, "contasInexistentes");
	
			if(contasInexistentes.length > 0) {
				csefDao.criarContas(contasInexistentes);
			}
			
			msg = "Contas criadas com sucesso, deverá ser executado o 'Verificar Arquivo' novamente.";
			//Executa novamente a rotina de verificação do arquivo para atualizar as informações.
			hidAcao = "verificar";
		}catch (ECARException e) {
			msg = _msg.getMensagem(e.getMessageKey());
			hidAcao = "verificar";
		} catch (Exception e){
			hidAcao = "verificar";
			%>
				<%@ include file="/excecao.jsp"%>
			<%
		}
	}
	
	
	if ("verificar".equals(hidAcao)){
		try{
			/* realiza o upload do arquivo */
    	    Iterator it = campos.iterator();  
        	while(it.hasNext()){
            	FileItem fileItem = (FileItem) it.next();
			
            	if(!fileItem.isFormField() && !"".equals(fileItem.getName())){    
				
                	if("arquivoImportacao".equals(fileItem.getFieldName())){
	     				/* obtem os caminhos fisicos para gravacao do arquivo a partir do arquivo de propriedades */
						String pathRaiz = configuracaoCfg.getRaizUpload();//_msg.getPathUploadRaiz("path .upload.raiz");
                		//String caminho = "/upload/integracaoFinanceira";
                		String caminho = configuracaoCfg.getUploadIntegracao();
                	
	                	String formato = "ddMMyyyyHHmmss";
			            SimpleDateFormat formatter = new SimpleDateFormat(formato);
        	    
            	    	String nomeArquivo = usuario.getCodUsu()+"_"+formatter.format(new Date())+"_import_"+FileUpload.getNomeArquivo(fileItem);
	
						try {
	    	                FileUpload.apagarArquivo(nomeArquivo);
                	    }catch (Exception e) {
                	    	org.apache.log4j.Logger.getLogger(this.getClass()).error(e);
                	    	msg = _msg.getMensagem("integracaoFinanceira.verificarArquivo.erro" );
						}
     	
        	        	File arquivoGravado = FileUpload.salvarNoDisco(fileItem, FileUpload.getPathFisico(pathRaiz, caminho , nomeArquivo));
        	        	List[] listaCriticada = csefDao.criticaArquivoImportado(arquivoGravado, configuracaoCfg, usuario, request);
        	        	List registrosValidos = listaCriticada[0];
        	        	List criticas = listaCriticada[1];
        	        	
		        	    String caminhoArquivoGravado = arquivoGravado.getAbsolutePath();
		        	    request.getSession().setAttribute("listaCriticas", criticas);
		        	    %>
		        	    <input type="hidden" name="arquivoVerificado" value="<%=FileUpload.getNomeArquivo(fileItem)%>">
		        	    <input type="hidden" name="caminhoArquivoGravado" value="<%=caminhoArquivoGravado%>">
		        	    <%
					}
            	}
    	    }
		}catch (ECARException e) {
			msg = _msg.getMensagem(e.getMessageKey());
		} catch (Exception e){
			%>
				<%@ include file="/excecao.jsp"%>
			<%
		}
	} else if ("importar".equals(hidAcao)){
		try{
			String caminhoArquivoGravado = FileUpload.verificaValorCampo(campos, "caminhoArquivoGravado");
			File arquivoGravado = new File(caminhoArquivoGravado);
			csefDao.importarDadosArquivo(arquivoGravado, configuracaoCfg, usuario, request);
			msg = _msg.getMensagem("integracaoFinanceira.importarArquivo.importacao.ok");
		}catch (ECARException e) {
			msg = _msg.getMensagem(e.getMessageKey()) + ": " + e.getMessage();
		} catch (Exception e){
			%>
				<%@ include file="/excecao.jsp"%>
			<%
		}
		//msg = "importado.";
	} else if ("imprimir".equals(hidAcao)){
    	%>
  	    	<script language="javascript">
  	    		document.form.action = "<%=_pathEcar%>/RelatorioOcorrenciasImportacao";
  	    		document.form.submit();
  	    	</script>
    	<%
	} else if ("limpar".equals(hidAcao)) {
		
		request.getSession().removeAttribute("listaCriticas");
		request.getSession().removeAttribute("dadosImportacao");
		submit = "importarConta.jsp";
		
	}

	
	/* da o alert para incluir, alterar, pesquisar ou excluir */
	if (msg != null) 
		Mensagem.setInput(_jspx_page_context, "document.form.msgOperacao.value", msg); 
%>
</form>
<script language="javascript">
	document.form.action = "<%=submit%>";
	document.form.submit();
</script> 
</body>
</html>
