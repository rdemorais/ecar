<%
//Author: Carlos Eduardo
//Data  : 17/05/2007
%>

<%@ page language="java" import="java.util.*" pageEncoding="ISO-8859-1"%>

<%@ page import="java.util.List" %>
<%@ page import="java.io.File" %>
<%@ page import="ecar.exception.ECARException" %>
<%@ page import="ecar.pojo.ConfiguracaoCfg" %>
<%@ page import="ecar.dao.ConfiguracaoDao" %>
<%@ page import="javax.servlet.http.HttpServletRequest" %>

<%!
	public void excluirArquivos(HttpServletRequest request, String[] arquivos) throws ECARException {
		
		ConfiguracaoCfg configuracao = new ConfiguracaoDao(request).getConfiguracao();
	
		//Pasta onde encontram-se os arquivos exportados
		String path = configuracao.getRaizUpload() + configuracao.getUploadIntegracao();
		
		if(!path.endsWith("/"))
			path = path + "/";
		
		for(int i=0; i<arquivos.length; i++) {
			File file = new File(path + arquivos[i]);
			file.delete();
		}
		
	}
%>

<%
	String msg = null;
	String[] arquivos = (String[]) request.getParameterValues("deleteFiles");
	
	try
	{
		excluirArquivos(request, arquivos);
	}catch(Exception e){
		msg = "Não foi possível excluir os arquivos selecionados. - " + e.getMessage();	
	}
	
	String pathRetorno = null;
	if(msg != null) {
		pathRetorno = "/integracaoFinanceira/arquivosExportados/listarArquivosExportados.jsp?msgPesquisa=" + msg;
	} else {
		pathRetorno = "/integracaoFinanceira/arquivosExportados/listarArquivosExportados.jsp";
	}
	response.sendRedirect(request.getContextPath() + pathRetorno);
%>