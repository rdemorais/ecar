
<jsp:directive.page import="ecar.util.Dominios"/>
<jsp:directive.page import="ecar.pojo.AcompRelatorioArel"/><%@ include file="../../login/validaAcesso.jsp"%>
<%@ include file="../../frm_global.jsp"%>
<%@ page import="ecar.dao.ItemEstruturaUploadDao" %>
<%@page import="ecar.pojo.ItemEstrutUploadIettup"%>  
 
<%@page import="comum.util.FileUpload"%>
<%@ page import="ecar.dao.ConfigMailCfgmDAO" %>
<%@ page import="ecar.pojo.ConfigMailCfgm" %>
<%@ page import="ecar.email.AgendadorEmail" %>
<%@ page import="ecar.dao.UsuarioDao" %>
<%@ page import="ecar.pojo.UsuarioUsu" %>
<%@ page import="ecar.pojo.ItemEstruturaIett" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.Iterator" %>
<%@ page import="ecar.pojo.ItemEstUsutpfuacIettutfa" %>
<%@ page import="ecar.pojo.ItemEstrUplCategIettuc" %>
<%@ page import="ecar.pojo.TipoFuncAcompTpfa" %>
<%@ page import="ecar.pojo.TfuncacompConfigmailTfacfgmPK" %>
<%@ page import="ecar.pojo.TfuncacompConfigmailTfacfgm" %>
<%@ page import="ecar.pojo.AcompReferenciaItemAri" %>
<%@ page import="ecar.dao.TfuncacompConfigmailTfacfgmDAO" %>
<%@ page import="ecar.dao.TipoFuncAcompDao" %>
<%@ page import="ecar.dao.AcompRelatorioDao" %>
<%@ page import="ecar.dao.AcompReferenciaItemDao" %>
<%@ page import="ecar.dao.ItemEstruturaUploadCategoriaDao" %>
<%@ page import="ecar.pojo.EmpresaEmp" %>
<%@ page import="ecar.dao.EmpresaDao" %>
<%@ page import="comum.database.Dao" %>
<%@ page import="comum.util.Util" %>
<%@ page import="comum.util.Data" %>

 

<%@page import="ecar.pojo.ConfiguracaoCfg"%><html lang="pt-br"> 
<head>
<%@ include file="../../include/meta.jsp"%>
</head>
<body> 
<form name="form" method="post">   
<%
	Long codIett = null;
	Long codTpfa = null;
	Long codAri = null;
	String codIettuc = null;
	Dao dao = new Dao();
	UsuarioDao usuDAO = new UsuarioDao();
	ItemEstruturaUploadDao anexoDao = new ItemEstruturaUploadDao(request);
	Object codigosParaExcluirArray[]= null;
	String codigosParaExcluir[] = null;
%>
<% 
	String msg = "";
	String submit = "";
	try{ 
		boolean isFormUpload = FileUpload.isMultipartContent(request);
		List campos = new ArrayList();
		if(isFormUpload){
			campos = FileUpload.criaListaCampos(request); 
			 }
%>

<!-- campo de controle para mensagens -->
<input type="hidden" name="msgOperacao" value=""> 

<%if(isFormUpload){
		codIett	= Long.valueOf(FileUpload.verificaValorCampo(campos,"codIett"));
		codTpfa = Long.valueOf(FileUpload.verificaValorCampo(campos, "codTpfa"));
		codAri = Long.valueOf(FileUpload.verificaValorCampo(campos, "codAri"));
		codIettuc = FileUpload.verificaValorCampo(campos, "codIettuc");
		codigosParaExcluirArray = FileUpload.verificaValorCampoArray(campos,"excluir");
		codigosParaExcluir = new String[codigosParaExcluirArray.length];
		for (int i = 0; i < codigosParaExcluirArray.length; i++) {
			codigosParaExcluir[i] = codigosParaExcluirArray[i].toString();
		}
		
		%>
		
<input type="hidden" name="codAba" value=<%=FileUpload.verificaValorCampo(campos,"codAba")%>>
<input type="hidden" name="codAri" value=<%=FileUpload.verificaValorCampo(campos, "codAri")%>>
<input type="hidden" name="codTpfa" value=<%=FileUpload.verificaValorCampo(campos, "codTpfa")%>>
<input type="hidden" name="funcao" value=<%=FileUpload.verificaValorCampo(campos, "funcao")%>>
<input type="hidden" name="funcaoParecer" value="<%=FileUpload.verificaValorCampo(campos, "funcaoParecer")%>">
<input type="hidden" name="primeiroAcomp" value=<%=FileUpload.verificaValorCampo(campos, "primeiroAcomp")%>>
<input type="hidden" name="codAcomp" value=<%=FileUpload.verificaValorCampo(campos, "codAcomp")%>>
<input type="hidden" name="codArel" value=<%=FileUpload.verificaValorCampo(campos, "codArel")%>>

<!-- campo hidden para cada chave primaria da tabela --> 
<input type="hidden" name="cod" value=<%=FileUpload.verificaValorCampo(campos,"cod")%>>
<input type="hidden" name="codIettuc" value=<%=FileUpload.verificaValorCampo(campos,"codIettuc")%>>
<input type="hidden" name="codIett" value=<%=FileUpload.verificaValorCampo(campos,"codIett")%>>
<%}else{
		codIett	= Long.valueOf(Pagina.getParamStr(request,"codIett"));
		codTpfa = Long.valueOf(Pagina.getParamStr(request,"codTpfa"));
		codAri = Long.valueOf(Pagina.getParamStr(request,"codAri"));
		codIettuc = Pagina.getParamStr(request, "codIettuc");
		codigosParaExcluir = request.getParameterValues("excluir");%>
<input type="hidden" name="codAba" value=<%=Pagina.getParamStr(request, "codAba")%>>

<input type="hidden" name="codAri" value=<%=Pagina.getParamStr(request, "codAri")%>>
<input type="hidden" name="codTpfa" value=<%=Pagina.getParamStr(request, "codTpfa")%>>
<input type="hidden" name="funcao" value=<%=Pagina.getParamStr(request, "funcao")%>>
<input type="hidden" name="funcaoParecer" value="<%=Pagina.getParamStr(request, "funcaoParecer")%>">
<input type="hidden" name="primeiroAcomp" value=<%=Pagina.getParamStr(request, "primeiroAcomp")%>>
<input type="hidden" name="codAcomp" value=<%=Pagina.getParamStr(request, "codAcomp")%>>
<input type="hidden" name="codArel" value=<%=Pagina.getParamStr(request, "codArel")%>>


<!-- campo hidden para cada chave primaria da tabela --> 
<input type="hidden" name="cod" value=<%=Pagina.getParamStr(request, "cod")%>>
<input type="hidden" name="codIettuc" value=<%=Pagina.getParamStr(request, "codIettuc")%>>
<input type="hidden" name="codIett" value=<%=codIett%>>
<%}%>

<%
			String descricaoEvento = null;

			String strAcao = "";
			if(isFormUpload)
				strAcao = FileUpload.verificaValorCampo(campos, "hidAcao");
			else
				strAcao = Pagina.getParamStr(request, "hidAcao");
							
			/* obtem os caminhos fisicos para gravacao do arquivo a partir do arquivo de propriedades */
			ConfiguracaoCfg configura = (ConfiguracaoCfg)session.getAttribute("configuracao");
			String pathRaiz = configura.getRaizUpload();//_msg.getPathUploadRaiz("path .upload.raiz");
			String pathAnexo = configura.getUploadAnexos(); //_msg.getMensagem("path .upload.anexos");
			
			AcompRelatorioDao acompRelatorioDao = new AcompRelatorioDao(request);
			TipoFuncAcompDao tipoFuncAcompDao = new TipoFuncAcompDao(request);
			AcompReferenciaItemDao acompReferenciaItemDao = new AcompReferenciaItemDao(request);
			TipoFuncAcompTpfa funcao = (TipoFuncAcompTpfa) tipoFuncAcompDao.buscar(TipoFuncAcompTpfa.class, codTpfa);
			AcompReferenciaItemAri acompReferenciaItem = (AcompReferenciaItemAri) acompReferenciaItemDao.buscar(AcompReferenciaItemAri.class, codAri);
			AcompRelatorioArel acompRelatorioArel = acompRelatorioDao.getAcompRelatorio(funcao,acompReferenciaItem);
			
			
			//ecar.pojo.ConfiguracaoCfg configura = new ConfiguracaoDao(request).getConfiguracao();
			//String pathRaiz = configura.getRaizUpload();//_msg.getPathUploadRaiz("path .upload.raiz");
			String pathCategoria = configura.getUploadCategoria();//_msg.getMensagem("path .upload.categoria");
			
			ItemEstrUplCategIettuc categoria = new ItemEstrUplCategIettuc();
			ItemEstruturaUploadCategoriaDao categoriaDao = new ItemEstruturaUploadCategoriaDao(request);					
			
			ItemEstrutUploadIettup anexo = new ItemEstrutUploadIettup();
			if("incluir".equals(strAcao)){
				descricaoEvento = "Inclusão de " + Pagina.getParamStr(request, "labelEttf");	
				
				if (codIettuc == null || codIettuc.equals("")) {
					categoriaDao.setItemEstruturaUploadCategoria(campos, categoria, pathRaiz, pathCategoria, false);	
					categoriaDao.salvar(categoria);
					categoria = (ItemEstrUplCategIettuc) categoriaDao.buscar(
                        ItemEstrUplCategIettuc.class, categoria.getCodIettuc());
				} else {
					categoria = (ItemEstrUplCategIettuc) categoriaDao.buscar(
                        ItemEstrUplCategIettuc.class, Long.valueOf(FileUpload.verificaValorCampo(campos, "codIettuc")));
				}
				 
				
				anexoDao.setItemEstruturaUpload(campos, anexo, pathRaiz, pathAnexo, acompRelatorioArel, categoria);              
				anexoDao.salvar(anexo, ((ecar.login.SegurancaECAR)session.getAttribute("seguranca")).getUsuario());
				submit = "acompRelatorio.jsp";
				msg = _msg.getMensagem("itemEstrutura.anexo.inclusao.sucesso");
			}	
			if("alterar".equals(strAcao)){
				descricaoEvento = "Alteração de " +Pagina.getParamStr(request, "labelEttf");
				anexo = (ItemEstrutUploadIettup) anexoDao.buscar(ItemEstrutUploadIettup.class, Long.valueOf(FileUpload.verificaValorCampo(campos, "cod")));	
				anexoDao.setItemEstruturaUpload(campos, anexo, pathRaiz, pathAnexo, acompRelatorioArel, categoria);
				anexoDao.alterar(anexo, ((ecar.login.SegurancaECAR)session.getAttribute("seguranca")).getUsuario() );
				submit = "acompRelatorio.jsp";
				msg = _msg.getMensagem("itemEstrutura.anexo.alteracao.sucesso");
			%>
			<script language=javascript>
				document.form.cod.value = '';
			</script>
			<%
			}
			if("excluir".equals(strAcao)){
				descricaoEvento = "Exlusão de " + Pagina.getParamStr(request, "labelEttf");;
				anexoDao.excluir(codigosParaExcluir, pathRaiz, ((ecar.login.SegurancaECAR)session.getAttribute("seguranca")).getUsuario(),true );
				submit = "acompRelatorio.jsp";
				msg = _msg.getMensagem("itemEstrutura.anexo.exclusao.sucesso");				
			}
			
	}
	catch ( ECARException e){
		org.apache.log4j.Logger.getLogger(this.getClass()).error(e);
		submit = "acompRelatorio.jsp";
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