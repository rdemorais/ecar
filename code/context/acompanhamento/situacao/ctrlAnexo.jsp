
<jsp:directive.page import="java.util.StringTokenizer"/>
<jsp:directive.page import="java.util.Collection"/>
<jsp:directive.page import="ecar.pojo.ConfiguracaoCfg"/>
<jsp:directive.page import="ecar.dao.ConfiguracaoDao"/>
<%@ include file="../../login/validaAcesso.jsp"%>
<%@ include file="../../frm_global.jsp"%>

<%@ page import="ecar.pojo.UsuarioUsu" %>
<%@ page import="ecar.pojo.ItemEstruturaIett" %>
<%@ page import="ecar.pojo.ConfigMailCfgm" %>
<%@ page import="ecar.pojo.ItemEstrutUploadIettup"%>
<%@ page import="ecar.pojo.AcompReferenciaItemAri" %>  
<%@ page import="ecar.pojo.AcompRelatorioArel"%> 
<%@ page import="ecar.pojo.ItemEstUsutpfuacIettutfa" %>
<%@ page import="ecar.pojo.ItemEstrUplCategIettuc" %>
<%@ page import="ecar.pojo.TipoFuncAcompTpfa" %>
<%@ page import="ecar.pojo.EmpresaEmp" %>
<%@ page import="ecar.pojo.TfuncacompConfigmailTfacfgmPK" %>
<%@ page import="ecar.pojo.TfuncacompConfigmailTfacfgm" %>

<%@ page import="ecar.dao.ItemEstruturaUploadDao" %>
<%@ page import="ecar.dao.ItemEstruturaUploadCategoriaDao" %>
<%@ page import="ecar.dao.ConfigMailCfgmDAO" %>
<%@ page import="ecar.dao.EmpresaDao" %>
<%@ page import="ecar.dao.UsuarioDao" %>
<%@ page import="ecar.dao.TfuncacompConfigmailTfacfgmDAO" %>
<%@ page import="ecar.dao.TipoFuncAcompDao" %>
<%@ page import="ecar.dao.AcompRelatorioDao" %>
<%@ page import="ecar.dao.AcompReferenciaItemDao" %>

<%@ page import="ecar.util.Dominios"%>
<%@ page import="ecar.email.AgendadorEmail" %>

<%@ page import="comum.database.Dao" %>
<%@ page import="comum.util.Util" %>
<%@ page import="comum.util.Data" %>
<%@ page import="comum.util.FileUpload"%>
<%@ page import="org.apache.commons.fileupload.FileItem" %>

<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.Iterator" %>

<html lang="pt-br"> 
	<head>
	<%@ include file="../../include/meta.jsp"%>
	</head>
<body> 
<form name="form" method="post">   
<%
	String codArel = null;
	Long codTpfa = null;
	String funcaoStr = null;
	String funcaoParecer = null;
	String codAcomp = null;
	String primeiroAcomp = null;
	String autorizarMail = null;
	Long codIett = null;
	Long codAri = null;
	String codIettuc = null;
	String tipoPadraoExibicaoAba = null;
	Dao dao = new Dao();
	UsuarioDao usuDAO = new UsuarioDao();
	ItemEstruturaUploadDao anexoDao = new ItemEstruturaUploadDao(request);
	StringTokenizer codigosParaExcluirSt = null;
	String codigosParaExcluir[] = null;

	String msg = "";
	String submit = "";
	try{ 
		ConfiguracaoCfg configuracao = (ConfiguracaoCfg)session.getAttribute("configuracao");
		boolean isFormUpload = FileUpload.isMultipartContent(request);
		List campos = new ArrayList();
		if(isFormUpload) {
			campos = FileUpload.criaListaCampos(request);
		}
		if(isFormUpload){
			codIett	= Long.valueOf(FileUpload.verificaValorCampo(campos,"codIett"));
			codIettuc = FileUpload.verificaValorCampo(campos, "codIettuc");
			codAri = Long.valueOf(FileUpload.verificaValorCampo(campos,"codAri"));
			codArel = FileUpload.verificaValorCampo(campos, "codArel");
			codTpfa = Long.valueOf(FileUpload.verificaValorCampo(campos, "codTpfa"));
			funcaoStr = FileUpload.verificaValorCampo(campos, "funcao");
			funcaoParecer = FileUpload.verificaValorCampo(campos, "funcaoParecer");
			codAcomp = FileUpload.verificaValorCampo(campos, "codAcomp");
			primeiroAcomp = FileUpload.verificaValorCampo(campos, "primeiroAcomp");
			autorizarMail = FileUpload.verificaValorCampo(campos, "autorizarMail");
			tipoPadraoExibicaoAba = FileUpload.verificaValorCampo(campos, "tipoPadraoExibicaoAba");
		} else {
			codIett	= Long.valueOf(Pagina.getParamStr(request,"codIett"));
			codIettuc = Pagina.getParamStr(request,"codIettuc");
			codAri = Long.valueOf(Pagina.getParamStr(request,"codAri"));
			codArel = Pagina.getParamStr(request, "codArel");
			codTpfa = Long.valueOf(Pagina.getParamStr(request, "codTpfa"));
			funcaoStr = Pagina.getParamStr(request, "funcao");
			funcaoParecer = Pagina.getParamStr(request, "funcaoParecer");
			codAcomp = Pagina.getParamStr(request, "codAcomp");
			primeiroAcomp = Pagina.getParamStr(request, "primeiroAcomp");
			autorizarMail = Pagina.getParamStr(request, "autorizarMail");
			campos = FileUpload.criaListaCampos(request);
		}
	 	if(isFormUpload) {
			codigosParaExcluirSt = new StringTokenizer(FileUpload.verificaValorCampo(campos,"excluirAnexo"), "|");	
			codigosParaExcluir = new String[codigosParaExcluirSt.countTokens()];
			int count = 0;
			while (codigosParaExcluirSt.hasMoreElements()){
				codigosParaExcluir[count] = (String) codigosParaExcluirSt.nextElement();
				count++;
			}

%>
<!-- campo de controle para mensagens -->
<input type="hidden" name="msgOperacao" value=""> 
<input type="hidden" name="codAba" value=<%=FileUpload.verificaValorCampo(campos,"codAba")%>>
<input type="hidden" name="codAri" value=<%=FileUpload.verificaValorCampo(campos,"codAri")%>>
<input type="hidden" name="codTpfa" value=<%=FileUpload.verificaValorCampo(campos,"codTpfa")%>>
<input type="hidden" name="funcao" value=<%=FileUpload.verificaValorCampo(campos,"funcao")%>>
<input type="hidden" name="funcaoParecer" value="<%=FileUpload.verificaValorCampo(campos,"funcaoParecer")%>">
<input type="hidden" name="primeiroAcomp" value=<%=FileUpload.verificaValorCampo(campos,"primeiroAcomp")%>>
<input type="hidden" name="codAcomp" value=<%=FileUpload.verificaValorCampo(campos,"codAcomp")%>>
<input type="hidden" name="codArel" value=<%=FileUpload.verificaValorCampo(campos,"codArel")%>>
<input type="hidden" name="codTipoAcompanhamento" value="<%=FileUpload.verificaValorCampo(campos,"codTipoAcompanhamento")%>">
<input type="hidden" name="referencia_hidden" value="<%=FileUpload.verificaValorCampo(campos,"referencia_hidden")%>">
<input type="hidden" name="mesReferencia" value="<%=FileUpload.verificaValorCampo(campos,"mesReferencia")%>">
<input type="hidden" name="niveisPlanejamento" value="<%=FileUpload.verificaValorCampo(campos,"niveisPlanejamento")%>">
<input type="hidden" name="primeiroIettClicado" value="<%=FileUpload.verificaValorCampo(campos,"primeiroIettClicado")%>">
<input type="hidden" name="primeiroAriClicado" value="<%=FileUpload.verificaValorCampo(campos,"primeiroAriClicado")%>">
<input type="hidden" name="codIettRelacao" value="<%=FileUpload.verificaValorCampo(campos,"codIettRelacao")%>">
<input type="hidden" name="itemDoNivelClicado" value="<%=FileUpload.verificaValorCampo(campos,"itemDoNivelClicado")%>">
<input type="hidden" name="codigosItem" value="<%=FileUpload.verificaValorCampo(campos,"codigosItem")%>">
<input type="hidden" name="tipoPadraoExibicaoAba" value="<%=tipoPadraoExibicaoAba%>">

<!-- Campo necessário para botões de Avança/Retrocede -->
<input type="hidden" name="codArisControle" value="<%=FileUpload.verificaValorCampo(campos,"codArisControle")%>">

<!-- Campos para manter a seleção em Posição Geral -->
<input type="hidden" name="hidItemAberto" value="<%=FileUpload.verificaValorCampo(campos,"hidItemAberto")%>">

<!-- campo hidden para cada chave primaria da tabela --> 
<input type="hidden" name="cod" value=<%=FileUpload.verificaValorCampo(campos,"cod")%>>
<input type="hidden" name="codIettuc" value=<%=FileUpload.verificaValorCampo(campos,"codIettuc")%>>
<input type="hidden" name="codIett" value=<%=FileUpload.verificaValorCampo(campos,"codIett")%>>

<!-- Campo necessario para guardar a forma de visualização escolhida no filtro -->
<input type="hidden" name="hidFormaVisualizacaoEscolhida" value='<%=FileUpload.verificaValorCampo(campos,"hidFormaVisualizacaoEscolhida")%>'>

<% } else {
		codigosParaExcluir = request.getParameterValues("excluirAnexo");%>
<!-- campo de controle para mensagens -->
<input type="hidden" name="msgOperacao" value=""> 
<input type="hidden" name="codAba" value=<%=Pagina.getParamStr(request, "codAba")%>>
<input type="hidden" name="codAri" value=<%=Pagina.getParamStr(request, "codAri")%>>
<input type="hidden" name="codTpfa" value=<%=Pagina.getParamStr(request, "codTpfa")%>>
<input type="hidden" name="funcao" value=<%=Pagina.getParamStr(request, "funcao")%>>
<input type="hidden" name="funcaoParecer" value="<%=Pagina.getParamStr(request, "funcaoParecer")%>">
<input type="hidden" name="primeiroAcomp" value=<%=Pagina.getParamStr(request, "primeiroAcomp")%>>
<input type="hidden" name="codAcomp" value=<%=Pagina.getParamStr(request, "codAcomp")%>>
<input type="hidden" name="codArel" value=<%=Pagina.getParamStr(request, "codArel")%>>
<input type="hidden" name="codTipoAcompanhamento" value="<%=Pagina.getParamStr(request, "codTipoAcompanhamento")%>">
<input type="hidden" name="referencia_hidden" value="<%=Pagina.getParamStr(request, "referencia_hidden")%>">
<input type="hidden" name="mesReferencia" value="<%=Pagina.getParamStr(request, "mesReferencia")%>">
<input type="hidden" name="niveisPlanejamento" value="<%=Pagina.getParamStr(request, "niveisPlanejamento")%>">
<input type="hidden" name="primeiroIettClicado" value="<%=Pagina.getParamStr(request, "primeiroIettClicado")%>">
<input type="hidden" name="primeiroAriClicado" value="<%=Pagina.getParamStr(request, "primeiroAriClicado")%>">
<input type="hidden" name="codIettRelacao" value="<%=Pagina.getParamStr(request, "codIettRelacao")%>">
<input type="hidden" name="itemDoNivelClicado" value="<%=Pagina.getParamStr(request, "itemDoNivelClicado")%>">
<input type="hidden" name="codigosItem" value="<%=Pagina.getParamStr(request, "codigosItem")%>">

<!-- Campo necessário para botões de Avança/Retrocede -->
<input type="hidden" name="codArisControle" value="<%=Pagina.getParamStr(request, "codArisControle")%>">

<!-- Campos para manter a seleção em Posição Geral -->
<input type="hidden" name="hidItemAberto" value="<%=Pagina.getParamStr(request, "hidItemAberto")%>">

<!-- campo hidden para cada chave primaria da tabela --> 
<input type="hidden" name="cod" value=<%=Pagina.getParamStr(request, "cod")%>>
<input type="hidden" name="codIettuc" value=<%=Pagina.getParamStr(request, "codIettuc")%>>
<input type="hidden" name="codIett" value=<%=codIett%>>

<!-- Campo necessario para guardar a forma de visualização escolhida no filtro -->
<input type="hidden" name="hidFormaVisualizacaoEscolhida" value='<%=Pagina.getParamStr(request, "hidFormaVisualizacaoEscolhida")%>'>


<%}

			
			String descricaoEvento = null;

			String strAcao = "";
			if(isFormUpload)
				strAcao = FileUpload.verificaValorCampo(campos, "hidAcao");
			else
				strAcao = Pagina.getParamStr(request, "hidAcao");
							
			/* obtem os caminhos fisicos para gravacao do arquivo a partir do arquivo de propriedades */
			String pathRaiz = configuracao.getRaizUpload();//_msg.getPathUploadRaiz("path .upload.raiz");
			String pathAnexo = configuracao.getUploadAnexos(); //_msg.getMensagem("path .upload.anexos");
			
			AcompRelatorioDao acompRelatorioDao = new AcompRelatorioDao(request);
			TipoFuncAcompDao tipoFuncAcompDao = new TipoFuncAcompDao(request);
			AcompReferenciaItemDao acompReferenciaItemDao = new AcompReferenciaItemDao(request);
			TipoFuncAcompTpfa funcao = (TipoFuncAcompTpfa) tipoFuncAcompDao.buscar(TipoFuncAcompTpfa.class, codTpfa);
			AcompReferenciaItemAri acompReferenciaItem = (AcompReferenciaItemAri) acompReferenciaItemDao.buscar(AcompReferenciaItemAri.class, codAri);
			AcompRelatorioArel acompRelatorioArel = acompRelatorioDao.getAcompRelatorio(funcao,acompReferenciaItem);
			
			//ecar.pojo.ConfiguracaoCfg configura = new ConfiguracaoDao(request).getConfiguracao();
			//String pathRaiz = configuracao.getRaizUpload();//_msg.getPathUploadRaiz("path .upload.raiz");
			String pathCategoria = configuracao.getUploadCategoria();//_msg.getMensagem("path .upload.categoria");
			
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
				//System.out.println(">>>>>>>>> "+FileUpload.verificaValorCampo(campos,"descricaoIettup"));
				anexoDao.setItemEstruturaUpload(campos, anexo, pathRaiz, pathAnexo, acompRelatorioArel, categoria);              
				anexoDao.salvar(anexo, ((ecar.login.SegurancaECAR)session.getAttribute("seguranca")).getUsuario());
				msg = _msg.getMensagem("itemEstrutura.anexo.inclusao.sucesso");
			}	
			if("alterar".equals(strAcao)){
				descricaoEvento = "Alteração de " +Pagina.getParamStr(request, "labelEttf");
				anexo = (ItemEstrutUploadIettup) anexoDao.buscar(ItemEstrutUploadIettup.class, Long.valueOf(FileUpload.verificaValorCampo(campos, "cod")));	
				anexoDao.setItemEstruturaUpload(campos, anexo, pathRaiz, pathAnexo, acompRelatorioArel, categoria);
				anexoDao.alterar(anexo, ((ecar.login.SegurancaECAR)session.getAttribute("seguranca")).getUsuario() );
				msg = _msg.getMensagem("itemEstrutura.anexo.alteracao.sucesso");
			%>
			<script language=javascript>
				document.form.cod.value = '';
			</script>
			<%
			}
			if("excluir".equals(strAcao)){
				descricaoEvento = "Exlusão de " + Pagina.getParamStr(request, "labelEttf");
				anexoDao.excluir(codigosParaExcluir, pathRaiz, ((ecar.login.SegurancaECAR)session.getAttribute("seguranca")).getUsuario(),true);
				msg = _msg.getMensagem("itemEstrutura.anexo.exclusao.sucesso");				
			}
			String codArisControle = Pagina.getParamStr(request, "codArisControle");
			submit = "relatorios.jsp?codArisControle=" + Pagina.getParamStr(request, "codArisControle");
	} catch ( ECARException e){
		org.apache.log4j.Logger.getLogger(this.getClass()).error(e);
		submit = "relatorios.jsp";
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