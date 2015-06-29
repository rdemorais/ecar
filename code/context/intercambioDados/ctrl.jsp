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
<%@page import="ecar.intercambioDados.montador.Montador"%>
<%@page import="ecar.intercambioDados.ControladorIntercambioDados"%>
<%@page import="ecar.dao.intercambioDados.PerfilIntercambioDadosDao"%>
<%@page import="ecar.pojo.intercambioDados.PerfilIntercambioDadosPflid"%>


<%@page import="ecar.exception.intercambioDados.ValidationException"%>


<%@page import="ecar.exception.intercambioDados.SintaticValidationException"%>

<%@page import="ecar.pojo.intercambioDados.LogIntercambioDadosLid"%>
<%@page import="java.io.InputStream"%>
<%@page import="java.io.FileInputStream"%>
<%@page import="java.io.ByteArrayInputStream"%>

<%@page import="ecar.intercambioDados.montador.Montador"%>
<%@page import="ecar.intercambioDados.importacao.comunicacao.ConfiguracaoImportacaoTXT"%>
<%@page import="ecar.intercambioDados.montador.ResultadoValidacaoBean"%><html lang="pt-br">
<head>
<%
	if("S".equals(Pagina.getParamStr(request, "fechar"))){
%>
	<SCRIPT language="javascript:document.window.close()"></SCRIPT>
<%
	}
%>
<%@ include file="../include/meta.jsp"%>
<%@ include file="/titulo.jsp"%>
<%@ include file="../include/exibirAguarde.jsp"%>
</head>
<body>
<%@ include file="../cabecalho.jsp" %>
<%@ include file="../divmenu.jsp"%>
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
	
	session.removeAttribute("tituloPage");
	session.removeAttribute("msgTitulo");


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
	                		
	                		ConfiguracaoImportacaoTXT configTxt = null;
	                		
	                		if (fileItem.getInputStream() instanceof FileInputStream){
	                			byte[] array = fileItem.get();
	                			
	                			ByteArrayInputStream byteArraySream = new ByteArrayInputStream(array);
	                			configTxt = new ConfiguracaoImportacaoTXT(perfil, (InputStream)byteArraySream, fileItem.getName());
	                			
	                		} else {
	                			configTxt = new ConfiguracaoImportacaoTXT(perfil, fileItem.getInputStream(), fileItem.getName());
	                		}
	                		                	
	                		Montador montador = new Montador (configTxt,Montador.TIPO_MOVIMENTO_IMPORTACAO);
	                		ControladorIntercambioDados ctrl = montador.getControladorIntercambioDados();

	                		ResultadoValidacaoBean resultadoBean = ctrl.validarRegistros(usuario, perfil); 
	                		
	                		session.setAttribute("validarRegistros",resultadoBean);
	                		session.setAttribute("configTxt", configTxt);
	                		session.setAttribute("perfilSelecionado",perfilSelecionado);
	                		
	                		String [] msgArgs = new String[2];
	                		msgArgs[0] = perfilSelecionado.getNomeTipoServicoPflid();
	                		msgArgs[1] = perfilSelecionado.getNomeSistemaOrigemPflid();
	                		
	                		//se a lista de registros validos for vazia não exibe a mensagem de confirmação da importação.
	                		if (resultadoBean.getLinhasRegistrosValidos().isEmpty()) {
	                			session.setAttribute("msgTitulo",_msg.getMensagem("importacao.rejeitada"));
	                		} else {
	                			session.setAttribute("msgTitulo",_msg.getMensagem("confirma.importacao",msgArgs));
	                		}
						}
	            	}
	    	    }
	        	submit = "resultadoValidacao.jsp";
			}catch (ECARException e) {
				msg = _msg.getMensagem(e.getMessageKey(),(String[])e.getMessageArgs());
				session.setAttribute("perfilSelecionado",perfilSelecionado);
				session.removeAttribute("configTxt");
				
				submit="importacao.jsp";
			} catch (SintaticValidationException sintex) {
				String[] param = new String[1];
				param[0] = sintex.getNumeroLinha().toString();
				
				msg = sintex.getMensagem()+_msg.getMensagem("mensagem.erro.linha",param);

				
				session.setAttribute("perfilSelecionado",perfilSelecionado);
				session.removeAttribute("configTxt");
				
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
		try {
			Montador montador = new Montador ((ConfiguracaoImportacaoTXT) request.getSession().getAttribute("configTxt"), Montador.TIPO_MOVIMENTO_IMPORTACAO);
			ControladorIntercambioDados ctrl = montador.getControladorIntercambioDados();
			
			PerfilIntercambioDadosPflid perfil = (PerfilIntercambioDadosPflid)request.getSession().getAttribute("perfilIntercambioDadosPflid");
			
			ResultadoValidacaoBean resultadoValidacaoBean =  (ResultadoValidacaoBean)request.getSession().getAttribute("validarRegistros");
			
			LogIntercambioDadosLid logIntercambioDadosLid = ctrl.importarDados(resultadoValidacaoBean.getLinhasRegistrosOriginais(), perfil, usuario,request);
			
			String[] args = new String[2];
			args[0] = perfilSelecionado.getNomeTipoServicoPflid();
			args[1] = perfilSelecionado.getNomeSistemaOrigemPflid();
			
			
			msg = _msg.getMensagem("intercambioDados.validacao.importacao.sucesso",args);
			submit = "detalheLog.jsp?codLid="+logIntercambioDadosLid.getCodLid();
			
			//O objeto de config que contêm o arquivo(muito grande) deve ser removido da sessão para liberar memória.  
			session.removeAttribute("configTxt");
			
			session.setAttribute("tituloPage",_msg.getMensagem("resultado.processamento"));
		} catch (ECARException e) {
			msg = _msg.getMensagem(e.getMessageKey());
			
			session.setAttribute("perfilSelecionado",perfilSelecionado);
			session.removeAttribute("configTxt");
			
			submit="importacao.jsp";
			
		}
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
<%@ include file="../include/ocultarAguarde.jsp"%>
</html>