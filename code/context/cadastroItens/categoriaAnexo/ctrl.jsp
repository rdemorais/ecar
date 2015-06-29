<%@ include file="../../login/validaAcesso.jsp"%>
<%@ include file="../../frm_global.jsp"%>
<%@ page import="ecar.dao.ItemEstruturaUploadCategoriaDao" %>
<%@page import="ecar.pojo.ItemEstrUplCategIettuc"%>
<%@page import="comum.util.FileUpload"%>

<%@page import="java.util.ArrayList"%>
<%@ page import="java.util.List" %>
<%@ page import="ecar.pojo.ConfiguracaoCfg" %>
<%@ page import="ecar.dao.ConfiguracaoDao" %>

<html lang="pt-br">
<head>
<%@ include file="../../include/meta.jsp"%>
</head> 
<body>
<form name="form" method="post">   
<% 
	String msg = "";
	String submit = "";
	
	try{
		boolean isFormUpload = FileUpload.isMultipartContent(request);
		List campos = new ArrayList();
		if(isFormUpload)
			campos = FileUpload.criaListaCampos(request); 
%>
<!-- campo de controle para mensagens -->
<input type="hidden" name="msgOperacao" value="">  


<%
String excluiImagem = "";

if(isFormUpload){ 
	String ultimoIdLinhaDetalhado = FileUpload.verificaValorCampo(campos,"ultimoIdLinhaDetalhado");%>
	<input type="hidden" name="codIett" value=<%=FileUpload.verificaValorCampo(campos,"codIett")%>>
	<input type="hidden" name="codAba" value=<%=FileUpload.verificaValorCampo(campos, "codAba")%>>
	<!-- campo hidden para cada chave primaria da tabela --> 
	<input type="hidden" name="cod" value=<%=FileUpload.verificaValorCampo(campos, "cod")%>>
	<input type="hidden" name="codIett" value=<%=FileUpload.verificaValorCampo(campos, "codIett")%>>
	<input type="hidden" name="ultimoIdLinhaDetalhado" value=<%=ultimoIdLinhaDetalhado%>>
<%
	excluiImagem = FileUpload.verificaValorCampo(campos, "excluiImagem");
} else {
	String ultimoIdLinhaDetalhado = Pagina.getParamStr(request, "ultimoIdLinhaDetalhado");%>
	<input type="hidden" name="codIett" value=<%=Pagina.getParamStr(request,"codIett")%>>
	<input type="hidden" name="codAba" value="<%=Pagina.getParamStr(request, "codAba")%>">
	<!-- campo hidden para cada chave primaria da tabela --> 
	<input type="hidden" name="cod" value="<%=Pagina.getParamStr(request, "cod")%>">
	<input type="hidden" name="codIett" value="<%=Pagina.getParamStr(request, "codIett")%>">
	<input type="hidden" name="ultimoIdLinhaDetalhado" value=<%=ultimoIdLinhaDetalhado%>>
<%
	excluiImagem = Pagina.getParamStr(request, "excluiImagem");
}%>

<%

			String strAcao = "";
			if(isFormUpload)
				strAcao = FileUpload.verificaValorCampo(campos, "hidAcao");
			else
				strAcao = Pagina.getParamStr(request, "hidAcao");
			
			/* obtem os caminhos fisicos para gravacao do arquivo a partir do arquivo de propriedades */
			ecar.pojo.ConfiguracaoCfg configura = new ConfiguracaoDao(request).getConfiguracao();
			String pathRaiz = configura.getRaizUpload();//_msg.getPathUploadRaiz("path .upload.raiz");
			String pathCategoria = configura.getUploadCategoria();//_msg.getMensagem("path .upload.categoria");
			
			ItemEstrUplCategIettuc categoria = new ItemEstrUplCategIettuc();
			ItemEstruturaUploadCategoriaDao categoriaDao = new ItemEstruturaUploadCategoriaDao(request);			
			 
			if("incluir".equals(strAcao)){ 
				categoriaDao.setItemEstruturaUploadCategoria(campos, categoria, pathRaiz, pathCategoria);	
				categoriaDao.salvar(categoria);
				submit = "lista.jsp";
				msg = _msg.getMensagem("itemEstrutura.categoriaAnexo.inclusao.sucesso");
			}	
			if("alterar".equals(strAcao)){
				
				//System.out.println("--------------------> "+excluiImagem);
				
				categoria = (ItemEstrUplCategIettuc) categoriaDao.buscar(ItemEstrUplCategIettuc.class, Long.valueOf(FileUpload.verificaValorCampo(campos, "cod")));	
				categoriaDao.setItemEstruturaUploadCategoria(campos, categoria, pathRaiz, pathCategoria);			
				categoriaDao.alterar(categoria, excluiImagem, pathRaiz);
				submit = "frm_con.jsp";
				msg = _msg.getMensagem("itemEstrutura.categoriaAnexo.alteracao.sucesso");
			}
			if("excluir".equals(strAcao)){
				String codigosParaExcluir[] = request.getParameterValues("excluir");
				categoriaDao.excluir(codigosParaExcluir, pathRaiz); 
				submit = "lista.jsp";
				msg = _msg.getMensagem("itemEstrutura.categoriaAnexo.exclusao.sucesso");
			}
	}
	catch ( ECARException e){
		org.apache.log4j.Logger.getLogger(this.getClass()).error(e);
		submit = "lista.jsp";
		msg = _msg.getMensagem(e.getMessageKey());
	} catch (Exception e){
	%>
		<%@ include file="/excecao.jsp"%>
	<%
	}
	if (msg != null)
		Mensagem.setInput(_jspx_page_context, "document.form.msgOperacao.value", msg); 
	%>

<script>
			document.form.action = "<%=submit%>";
			document.form.submit();
</script>

</form>
</body>
</html>