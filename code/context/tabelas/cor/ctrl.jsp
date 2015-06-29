<%@ include file="../../login/validaAcesso.jsp"%>
<%@ include file="../../frm_global.jsp"%>
<%@ page import="ecar.pojo.Cor" %>
<%@ page import="ecar.dao.CorDao" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="comum.util.FileUpload" %>
<%@ page import="java.util.Iterator" %>
<%@ page import="org.apache.commons.fileupload.FileItem" %>
<%@ page import="ecar.dao.TipoFuncAcompDao" %>
<%@ page import="ecar.pojo.TipoFuncAcompTpfa" %>
<%@ page import="ecar.pojo.CorTipoFuncAcompCtfaPK" %>
<%@ page import="ecar.pojo.CorTipoFuncAcompCtfa" %>
<%@ page import="ecar.dao.CorTipoFuncAcompDao" %>
<%@ page import="java.util.HashSet" %>


<%@page import="ecar.util.Dominios"%>
<%@page import="java.util.Map"%><html lang="pt-br">
<head>
<%@ include file="../../include/meta.jsp"%>
<%@ include file="/titulo.jsp"%>
</head>
<body>
<%
	boolean isFormUpload = FileUpload.isMultipartContent(request); 
	List campos = new ArrayList();
	if(isFormUpload){
		campos = FileUpload.criaListaCampos(request); 
	}
%>

<form name="form" method="post">
    <!-- campo de controle para a navegação de registros -->
	<%if(isFormUpload){%>
		<input type="hidden" name="hidRegistro" value="<%=FileUpload.verificaValorCampo(campos, "hidRegistro")%>">
	<%} else {%>
		<input type="hidden" name="hidRegistro" value="<%=Pagina.getParamInt(request, "hidRegistro")%>">	
	<%}%>
    
	<!-- campo de controle para as mensagens -->
	<input type="hidden" name="msgOperacao" value=""> 
	<input type="hidden" name="msgPesquisa" value="">	
	<input type="hidden" name="hidPesquisou" value="">	
</form>

<%
	Cor cor = new Cor();
	CorDao corDao = new CorDao(request);
	ConfiguracaoCfg configuracao = (ConfiguracaoCfg)session.getAttribute("configuracao");
	String imagePath = configuracao.getRaizUpload() + Dominios.PATH_REMOTE_IMAGES;
	
	String hidAcao = "";
	
	if(isFormUpload)
		hidAcao = FileUpload.verificaValorCampo(campos, "hidAcao");
	else
		hidAcao = Pagina.getParam(request, "hidAcao");

	String msg = null;
	String submit = "frm_pes.jsp";

	boolean refazPesquisa = true;
	boolean incluir = "incluir".equalsIgnoreCase(hidAcao);
	boolean alterar = "alterar".equalsIgnoreCase(hidAcao);
	boolean excluir = "excluir".equalsIgnoreCase(hidAcao);
	boolean pesquisar = "pesquisar".equalsIgnoreCase(hidAcao);
	
	if (incluir) {
		// Faz a inclusão das cores de funções de acompanhamento, com imagens que podem ser 
		// personalizadas pelo usuário. Caso não haja personalização, o sistema buscará imagens 
		// padrões, cujos nomes são montados de acordo com o nome da cor e funções de acompanhamento.
	
		/* melhor usar getParamStr, pois nunca devolve null */		
		cor.setNomeCor(FileUpload.verificaValorCampo(campos, "nomeCor").trim()); 
		cor.setSignificadoCor(FileUpload.verificaValorCampo(campos, "significadoCor").trim());
		cor.setCodCorGrafico(FileUpload.verificaValorCampo(campos, "codCorGrafico").trim());
		cor.setIndPosicoesGeraisCor(FileUpload.verificaValorCampo(campos,"indPosicoesGeraisCor").trim());
		cor.setIndPontoCriticoCor(FileUpload.verificaValorCampo(campos, "indPontoCriticoCor").trim());
		cor.setIndIndicadoresFisicosCor(FileUpload.verificaValorCampo(campos, "indIndicadoresFisicosCor").trim());
	
		refazPesquisa = false;
		submit = "frm_inc.jsp";
		try {
			
			Map arquivos = corDao.uploadImagem(campos);
			
			// Atribui nome da imagem do ponto crítico
			Iterator itu = campos.iterator();
			while( itu.hasNext() ) {
				FileItem file = (FileItem) itu.next();
												
				if( "caminhoImagemPontoCriticoCor".equals(file.getFieldName())){ // && !"".equals(file.getName()) ) {
					String nomeArquivo = FileUpload.verificaValorCampo(campos, "hidcaminhoImagemPontoCriticoCor").trim();
					if (nomeArquivo.equals("_excluir")){
						cor.setCaminhoImagemPontoCriticoCor("");
					}
					else if (!"".equals(file.getName())){
											
						Object nomeArquivoGravado = arquivos.get(file.getFieldName());
						if (nomeArquivoGravado != null && !nomeArquivoGravado.equals("")){
							cor.setCaminhoImagemPontoCriticoCor(FileUpload.getNomeArquivo(nomeArquivoGravado.toString())); //FileUpload.getPathFisico("", imagePath, FileUpload.getNomeArquivo(nomeArquivoGravado.toString())));
						}
						else{
							cor.setCaminhoImagemPontoCriticoCor(FileUpload.getNomeArquivo(file.getName()));
						}
					}
				}
				
				if( "caminhoImagemIndResul".equals(file.getFieldName())){ // && !"".equals(file.getName()) ) {
					String nomeArquivo = FileUpload.verificaValorCampo(campos, "hidcaminhoImagemIndResul").trim();
					if (nomeArquivo.equals("_excluir")){
						cor.setCaminhoImagemIndResulCor("");
					}
					else if (!"".equals(file.getName())){
						
						Object nomeArquivoGravado = arquivos.get(file.getFieldName());
						if (nomeArquivoGravado != null && !nomeArquivoGravado.equals("")){
							cor.setCaminhoImagemIndResulCor(FileUpload.getNomeArquivo(nomeArquivoGravado.toString()));
						}
						else{
							cor.setCaminhoImagemIndResulCor(FileUpload.getNomeArquivo(file.getName()));
						}
					}
				}
			}
			
			CorTipoFuncAcompDao corTipoFunAcompDao = new CorTipoFuncAcompDao(null);	
			TipoFuncAcompDao tipoFuncAcompDao = new TipoFuncAcompDao();
			List funcoes = tipoFuncAcompDao.getTipoFuncAcompEmitePosicao();

			List listCorTipoFuncAcomp = new ArrayList();
			
			// Listagem
			listCorTipoFuncAcomp = corTipoFunAcompDao.criarListaCorTipoFuncAcomp("L", funcoes, campos, cor, listCorTipoFuncAcomp, arquivos);
			// Detalhes
			listCorTipoFuncAcomp = corTipoFunAcompDao.criarListaCorTipoFuncAcomp("D", funcoes, campos, cor, listCorTipoFuncAcomp, arquivos);
			// Relatorio
			listCorTipoFuncAcomp = corTipoFunAcompDao.criarListaCorTipoFuncAcomp("R", funcoes, campos, cor, listCorTipoFuncAcomp, arquivos);
			
			cor.setCorTipoFuncAcompCtfas(new HashSet(listCorTipoFuncAcomp));
		
			corDao.salvar(cor);
						
			
			
			msg = _msg.getMensagem("cor.inclusao.sucesso" );
			session.removeAttribute("objCor");
		} catch (ECARException e) {
			org.apache.log4j.Logger.getLogger(this.getClass()).error(e);
			session.setAttribute("objCor", cor);
			msg = _msg.getMensagem(e.getMessageKey());
		} catch (Exception e){
		%>
			<%@ include file="/excecao.jsp"%>
		<%
		}
	} else if (alterar) {
		try {			
			/* o buscar deve estar no try, pois pode ocorrer um erro de hibernate ou bd */
			cor = (Cor) corDao.buscar(Cor.class, Long.valueOf(FileUpload.verificaValorCampo(campos, "codigo")));
			cor.setCodCor(Long.valueOf(FileUpload.verificaValorCampo(campos, "codigo")));
			cor.setNomeCor(FileUpload.verificaValorCampo(campos, "nomeCor").trim());
			cor.setCodCorGrafico(FileUpload.verificaValorCampo(campos, "codCorGrafico").trim());
			cor.setSignificadoCor(FileUpload.verificaValorCampo(campos, "significadoCor").trim());
			cor.setIndPosicoesGeraisCor(FileUpload.verificaValorCampo(campos, "indPosicoesGeraisCor").trim());
			cor.setIndPontoCriticoCor(FileUpload.verificaValorCampo(campos, "indPontoCriticoCor").trim());
			cor.setIndIndicadoresFisicosCor(FileUpload.verificaValorCampo(campos, "indIndicadoresFisicosCor").trim());
			
			Map arquivos = corDao.uploadImagem(campos);
			
			// Atribui nome da imagem do ponto crítico
			Iterator itu = campos.iterator();
			while( itu.hasNext() ) {
				FileItem file = (FileItem) itu.next();
				
				
				if( "caminhoImagemPontoCriticoCor".equals(file.getFieldName())){ // && !"".equals(file.getName()) ) {
					String status = FileUpload.verificaValorCampo(campos, "hidcaminhoImagemPontoCriticoCor").trim();
					if (status.equals("_excluir")){
						cor.setCaminhoImagemPontoCriticoCor(null);
					}
					if (!"".equals(file.getName())){
						
						Object nomeArquivoGravado = arquivos.get(file.getFieldName());
						if (nomeArquivoGravado != null && !nomeArquivoGravado.equals("")){
							cor.setCaminhoImagemPontoCriticoCor(FileUpload.getNomeArquivo(nomeArquivoGravado.toString()));
						}
						else{
							cor.setCaminhoImagemPontoCriticoCor(FileUpload.getNomeArquivo(file.getName()));
						}
						//(FileUpload.getPathFisico("", pathEmpresa, FileUpload.getNomeArquivo(arquivoGravado.getName())))
					}
				}
				
				if( "caminhoImagemIndResul".equals(file.getFieldName())){ // && !"".equals(file.getName()) ) {
					String status = FileUpload.verificaValorCampo(campos, "hidcaminhoImagemIndResul").trim();
					if (status.equals("_excluir")){
						cor.setCaminhoImagemIndResulCor(null);
					}
					if (!"".equals(file.getName())){
										
						Object nomeArquivoGravado = arquivos.get(file.getFieldName());
						if (nomeArquivoGravado != null && !nomeArquivoGravado.equals("")){
							cor.setCaminhoImagemIndResulCor(FileUpload.getNomeArquivo(nomeArquivoGravado.toString()));
						}
						else{
							cor.setCaminhoImagemIndResulCor(FileUpload.getNomeArquivo(file.getName()));
						}
					}
				}
			}
			
			CorTipoFuncAcompDao corTipoFunAcompDao = new CorTipoFuncAcompDao(null);	
			TipoFuncAcompDao tipoFuncAcompDao = new TipoFuncAcompDao();
			List funcoes = tipoFuncAcompDao.getTipoFuncAcompEmitePosicao();

			List listCorTipoFuncAcomp = new ArrayList();
			
			// Adiciona na lista de imagens as do grupo "Listagem"
			listCorTipoFuncAcomp = corTipoFunAcompDao.criarListaCorTipoFuncAcomp("L", funcoes, campos, cor, listCorTipoFuncAcomp, arquivos);
			// Adiciona na lista de imagens as do grupo "Detalhes"
			listCorTipoFuncAcomp = corTipoFunAcompDao.criarListaCorTipoFuncAcomp("D", funcoes, campos, cor, listCorTipoFuncAcomp, arquivos);
			// Adiciona na lista de imagens as do grupo "Relatorio"
			listCorTipoFuncAcomp = corTipoFunAcompDao.criarListaCorTipoFuncAcomp("R", funcoes, campos, cor, listCorTipoFuncAcomp, arquivos);
			// Atribui a lista de imagens ao CorTipoFuncaoAcomp da Cor
			cor.setCorTipoFuncAcompCtfas(new HashSet(listCorTipoFuncAcomp));
			
			corDao.alterar(cor);
			

			msg = _msg.getMensagem("cor.alteracao.sucesso");
			session.removeAttribute("objCor");
		} catch (ECARException e) {
			org.apache.log4j.Logger.getLogger(this.getClass()).error(e);
			refazPesquisa = false;
			submit = "frm_alt.jsp";
			session.setAttribute("objCor", cor);
			msg = _msg.getMensagem(e.getMessageKey());
		} catch (Exception e){
		%>
			<%@ include file="/excecao.jsp"%>
		<%
		}
	} else if (excluir) {
		try {
			corDao.excluirCor((Cor)corDao.buscar(Cor.class, Long.valueOf(Pagina.getParam(request, "codigo"))));
			msg = _msg.getMensagem("cor.exclusao.sucesso" );
		} catch (ECARException e) {
			org.apache.log4j.Logger.getLogger(this.getClass()).error(e);
			msg = _msg.getMensagem(e.getMessageKey(), (String[]) e.getMessageArgs());
		} catch (Exception e){
		%>
			<%@ include file="/excecao.jsp"%>
		<%
		}
	} else if (pesquisar) {
		/* aqui devemos usar getParam, pois queremos null se o campo estiver vazio */
		cor.setNomeCor(Pagina.getParam(request, "nomeCor"));
		cor.setSignificadoCor(Pagina.getParam(request, "significadoCor"));
		cor.setCodCorGrafico(Pagina.getParam(request, "codCorGrafico"));
		cor.setIndPosicoesGeraisCor(Pagina.getParam(request,"indPosicoesGeraisCor"));
		cor.setIndPontoCriticoCor(Pagina.getParam(request, "indPontoCriticoCor"));
		cor.setIndIndicadoresFisicosCor(Pagina.getParam(request, "indIndicadoresFisicosCor"));
		session.setAttribute("objPesquisa", cor);
	}
	/* da o alert para incluir, alterar, pesquisar ou excluir */
	if (msg != null) 
		Mensagem.setInput(_jspx_page_context, "document.form.msgOperacao.value", msg); 

	if (refazPesquisa) {
		try {
			cor = (Cor) session.getAttribute("objPesquisa");
			List lista = corDao.pesquisar(cor, new String[] {"codCor","asc"});
			boolean pesquisou = (lista.size() > 0) ? true : false;
			session.setAttribute("lista", lista);
			if (pesquisou) {
				submit = "frm_nav.jsp";
				msg = null;
			} else {
				Mensagem.setInput(_jspx_page_context, "document.form.hidPesquisou.value", "false");
				submit = "frm_pes.jsp";
				msg = _msg.getMensagem("cor.pesquisa.nenhumRegistro");
			}
		} catch (ECARException e) {
			org.apache.log4j.Logger.getLogger(this.getClass()).error(e);
			msg = _msg.getMensagem(e.getMessageKey());
		} catch (Exception e){
		%>
			<%@ include file="/excecao.jsp"%>
		<%
		}
		if (msg != null){
			Mensagem.setInput(_jspx_page_context, "document.form.msgPesquisa.value", msg);
		}
	}
	%>
	<script language="javascript">
		document.form.action = "<%=submit%>";
		document.form.submit();
	</script> 
</body>
</html>
			
