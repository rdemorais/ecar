<%@ include file="../login/validaAcesso.jsp"%>
<%@ include file="../frm_global.jsp"%>

<%@ page import="comum.util.FileUpload"%>

<%@ page import="java.io.File" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.Iterator" %>
<%@ page import="java.util.ArrayList"%>
<%@ page import="ecar.pojo.UsuarioUsu" %>

<%@ page import="org.apache.commons.fileupload.FileItem"%>
<%@ page import="comum.util.Data" %>


<%@page import="ecar.util.Dominios"%>
<%@page import="ecar.intercambioDados.Montador"%>
<%@page import="ecar.intercambioDados.ControladorIntercambioDados"%>
<%@page import="ecar.dao.intercambioDados.PerfilIntercambioDadosDao"%>
<%@page import="ecar.pojo.intercambioDados.PerfilIntercambioDadosPflid"%>

<%@page import="ecar.intercambioDados.recurso.txt.ConfiguracaoTXT"%>


<%@page import="ecar.exception.intercambioDados.ValidationException"%>


<%@page import="ecar.exception.intercambioDados.SintaticValidationException"%>
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
	
	List campos = FileUpload.criaListaCampos(request); 

	String hidAcao = FileUpload.verificaValorCampo(campos, "hidAcao");
	PerfilIntercambioDadosDao perfilDao = new PerfilIntercambioDadosDao(request);

	PerfilIntercambioDadosPflid perfilSelecionado = (PerfilIntercambioDadosPflid)session.getAttribute("perfilSelecionado");
	if (perfilSelecionado != null){
		session.removeAttribute("perfilSelecionado");
	}
%>
<form name="form" method="post">
    <!-- campo de controle para a navegação de registros -->
	<input type="hidden" name="msgOperacao" value=""> 
	<input type="hidden" name="msgPesquisa" value="">	

<%
		String msg = null;
		String submit="";
		String perfilId = "";
		
		if (hidAcao.equals("validarArquivo")){
			try{
		/* realiza o upload do arquivo */
	    	    Iterator it = campos.iterator();  
	        	while(it.hasNext()){
	            	FileItem fileItem = (FileItem) it.next();
		
	            	if(!fileItem.isFormField() && !fileItem.getName().equals(Dominios.STRING_VAZIA)){    
			
	                	if("arquivoImportacao".equals(fileItem.getFieldName())){
	                		
	                		perfilId = FileUpload.verificaValorCampo(campos, "perfilImportacao");
	                		
	                		//Buscar o perfil selecionado
	                		PerfilIntercambioDadosPflid perfil = (PerfilIntercambioDadosPflid)perfilDao.buscar(PerfilIntercambioDadosPflid.class,new Long(perfilId));
	                		
	                		request.getSession().setAttribute("perfilIntercambioDadosPflid", perfil);
	                		
	                		ConfiguracaoTXT configTxt = new ConfiguracaoTXT(perfil);
	                		configTxt.setStream(fileItem.getInputStream());
	                		configTxt.setFileName(fileItem.getName());
	                	
	                		Montador montador = new Montador (configTxt);
	                		ControladorIntercambioDados ctrl = montador.getControladorIntercambioDados();

	                		 
	                		request.getSession().setAttribute("validarRegistros", ctrl.validarRegistros(usuario));
	                		request.getSession().setAttribute("configTxt", configTxt);
	                		
	                		
		     				/* obtem os caminhos fisicos para gravacao do arquivo a partir do arquivo de propriedades */
					//String pathRaiz = configuracao.getRaizUpload();//_msg.getPathUploadRaiz("path .upload.raiz");
	                		//String caminho = "/upload/integracaoFinanceira";
	                		//String caminho = configuracao.getUploadIntegracao();
	                	
		                	//String formato = "ddMMyyyyHHmmss";
		            //SimpleDateFormat formatter = new SimpleDateFormat(formato);
	        	    
	            	    	//String nomeArquivo = usuario.getCodUsu()+"_"+formatter.format(new Date())+"_import_"+FileUpload.getNomeArquivo(fileItem);
		
	     	
	//        	        	File arquivoGravado = FileUpload.salvarNoDisco(fileItem, FileUpload.getPathFisico(pathRaiz, caminho , nomeArquivo));
	//        	        	List[] listaCriticada = csefDao.criticaArquivoImportado(arquivoGravado, configuracao, usuario, request);
	//        	        	List registrosValidos = listaCriticada[0];
	//        	        	List criticas = listaCriticada[1];
	        	        	
	//		        	    String caminhoArquivoGravado = arquivoGravado.getAbsolutePath();
	//		        	    request.getSession().setAttribute("listaCriticas", criticas);
				}
	            	}
	    	    }
	        	submit = "resultadoValidacao.jsp";
			}catch (ECARException e) {
				msg = _msg.getMensagem(e.getMessageKey());
				
				submit="importacao.jsp";
			} catch (SintaticValidationException sintex) {
				msg = sintex.getMensagem();
				
				session.setAttribute("perfilSelecionado",perfilSelecionado);
				
				submit="importacao.jsp";
				
			} catch (Exception e){
	%>
				<%@ include file="/excecao.jsp"%>
	<%
			}
	} else if (hidAcao.equals("alterarPerfil")) {
		perfilId = FileUpload.verificaValorCampo(campos, "perfilImportacao");
					
		if (!perfilId.equals(Dominios.STRING_VAZIA)) {
			PerfilIntercambioDadosPflid perfil = (PerfilIntercambioDadosPflid)perfilDao.buscar(PerfilIntercambioDadosPflid.class,new Long(perfilId));
			
			session.setAttribute("perfilSelecionado",perfil);
		}
		
		submit = "importacao.jsp";
	} else if (hidAcao.equals("importar")) {
		System.out.println("teste");
		Montador montador = new Montador ((ConfiguracaoTXT) request.getSession().getAttribute("configTxt"));
		ControladorIntercambioDados ctrl = montador.getControladorIntercambioDados();
		ctrl.importarDados(null, usuario,request);
		submit = "importacao.jsp";
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