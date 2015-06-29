<!-- Esta tela contém as informações necessárias pra chamar a parte de anexo e restrições 
da parte de visualizar. -->

<%
	msgConfirm = null;
	String msgAcomp = "";
	
	/* verificar se já existe o acompanhamento Relatório*/
	AcompRelatorioArel acompanhamento = new AcompRelatorioArel();
	AcompRelatorioArel acompBusca = acompRelatorioDao.getAcompRelatorio(funcao,ari);
	if(acompBusca != null)
		acompanhamento = acompBusca;
	
	int podeAcessarRelatorio = acompRelatorioDao.podeAcessarRelatorio(usuario, funcao, acompanhamento);
	
	Calendar dataAtual = Calendar.getInstance();
	dataAtual.clear(Calendar.HOUR);
	dataAtual.clear(Calendar.HOUR_OF_DAY);
	dataAtual.clear(Calendar.MINUTE);
	dataAtual.clear(Calendar.SECOND);
	dataAtual.clear(Calendar.MILLISECOND);
	
	Calendar dataInicioAcomp = Calendar.getInstance();
	dataInicioAcomp.setTime(ari.getDataInicioAri());
	dataInicioAcomp.clear(Calendar.HOUR);
	dataInicioAcomp.clear(Calendar.HOUR_OF_DAY);
	dataInicioAcomp.clear(Calendar.MINUTE);
	dataInicioAcomp.clear(Calendar.SECOND);
	dataInicioAcomp.clear(Calendar.MILLISECOND);
	
	isFormUpload = FileUpload.isMultipartContent(request);
	campos = new ArrayList();
	if(isFormUpload){
		campos = FileUpload.criaListaCampos(request); 
	}
	
	Long codAri = null;
	Long codTpfa = null;
	String funcaoStr = null;
	String funcaoParecer = null;
	String primeiroAcomp = null;
	String codAcomp = null;
	String cod = null;
	String inclusaoAnexo = null;
	String descricaoIettuc = null;

	if (isFormUpload) {
		codAri = Long.valueOf(FileUpload.verificaValorCampo(campos, "codAri"));
		codTpfa = Long.valueOf(FileUpload.verificaValorCampo(campos, "codTpfa"));
		funcaoStr = FileUpload.verificaValorCampo(campos, "funcao");
		funcaoParecer = FileUpload.verificaValorCampo(campos, "funcaoParecer");
		primeiroAcomp = FileUpload.verificaValorCampo(campos, "primeiroAcomp");
		codAcomp = FileUpload.verificaValorCampo(campos, "codAcomp");
		cod = FileUpload.verificaValorCampo(campos, "cod");
		inclusaoAnexo = FileUpload.verificaValorCampo(campos, "inclusaoAnexo");
		msgConfirm = FileUpload.verificaValorCampo(campos, "msgConfirm");
	} else {
		codAri = Long.valueOf(Pagina.getParamStr(request, "codAri"));
		codTpfa = acompRelatorio.getTipoFuncAcompTpfa().getCodTpfa() ; //codTpfa = Long.valueOf(Pagina.getParamStr(request,"codTpfa"));
		funcaoStr = Pagina.getParamStr(request, "funcao");
		funcaoParecer = Pagina.getParamStr(request, "funcaoParecer");
		primeiroAcomp = Pagina.getParamStr(request, "primeiroAcomp");
		codAcomp = acompRelatorio.getAcompReferenciaItemAri().getAcompReferenciaAref().getTipoAcompanhamentoTa().getCodTa().toString(); //Pagina.getParamStr(request, "codAcomp");
		codAcomp = Pagina.getParamStr(request, "codAcomp");
		cod = Pagina.getParamStr(request, "cod");
		inclusaoAnexo = Pagina.getParamStr(request, "inclusaoAnexo");
		msgConfirm = Pagina.getParamStr(request, "msgConfirm");
	}

	String msgData = "";

	descricaoIettuc = "Anexos Acomp. " + ari.getAcompReferenciaAref().getNomeAref();
	String paramCodIett = ari.getItemEstruturaIett().getCodIett().toString();
	
	ItemEstrutUploadIettup anexo = null;
	if (cod != null && !cod.equals("")){
		anexo = (ItemEstrutUploadIettup) new ItemEstruturaUploadDao(request).buscar(ItemEstrutUploadIettup.class, Long.valueOf(cod));
	} else {
		anexo = new ItemEstrutUploadIettup();
	} 

	EstruturaFuncaoDao estruturaFuncaoDao = new EstruturaFuncaoDao(request);
	
	ItemEstruturaIett itemEstrutura = (ItemEstruturaIett) itemEstruturaDao.buscar(ItemEstruturaIett.class, Long.valueOf(paramCodIett));
	
	EstruturaFuncaoEttf estruturaFuncao = (EstruturaFuncaoEttf) estruturaFuncaoDao.getItensAnexo(itemEstrutura.getEstruturaEtt());
		
	/*EstruturaFuncaoEttf*/
	estruturaFuncaoItensAnexo = estruturaFuncaoDao.getItensAnexo(itemEstrutura.getEstruturaEtt());
	
	String caminhoRaizUpload = configuracao.getRaizUpload();
	
	ItemEstruturaUploadCategoriaDao categoriaAnexoDao = new ItemEstruturaUploadCategoriaDao(null);
    ItemEstrUplCategIettuc categoriaAnexo = new ItemEstrUplCategIettuc();

   	categoriaAnexo.setAcompReferenciaItemAri(ari);
   	
    List listaCategoriaAnexo = categoriaAnexoDao.pesquisar(categoriaAnexo, new String[]{"acompReferenciaItemAri", "asc"});
	Iterator itAbas = listaCategoriaAnexo.iterator();

	if (itAbas.hasNext()) {
		categoriaAnexo = (ItemEstrUplCategIettuc) itAbas.next();
	} else {
		categoriaAnexo = null;
	}
		
	
	ItemEstUsutpfuacIettutfa itemEstUsu = (ItemEstUsutpfuacIettutfa) itemEstUsutpfuacDao.getUsuarioAcompanhamento(ari.getItemEstruturaIett(), funcao);
	
	List anexos = null;
	ItemEstrutUploadIettup itemEstrutUploadIettup = new ItemEstrutUploadIettup();
	itemEstrutUploadIettup.setAcompRelatorioArel(acompanhamento);
	itemEstrutUploadIettup.setIndAtivoIettup("S");
	anexos = categoriaAnexoDao.pesquisar(itemEstrutUploadIettup, new String[]{"acompRelatorioArel", "asc"});  			
	
	UploadTipoCategoriaDao uploadTipoCategoriaDao = new UploadTipoCategoriaDao(request);
	
	// Busca a categoria 4: Anexos de Acompanhamento.
	UploadTipoCategoriaUtc uploadTipoCategoriaUtc = (UploadTipoCategoriaUtc) uploadTipoCategoriaDao.buscar(UploadTipoCategoriaUtc.class, new Long(4));
	 
	UploadTipoArquivoUta uploadTipoArquivoUta = new UploadTipoArquivoUta();
	
	uploadTipoArquivoUta.setUploadTipoCategoriaUtc(uploadTipoCategoriaUtc);
	
	List categorias =  uploadTipoCategoriaDao.pesquisar(uploadTipoArquivoUta, new String[]{"uploadTipoCategoriaUtc", "asc"});
    		
	if(
		podeAcessarRelatorio == AcompRelatorioDao.OPERACAO_NEGADA_USUARIO_SEM_PERMISSAO
		&& acompRelatorio.getTipoFuncAcompTpfa().getIndVisualizarParecer().equalsIgnoreCase("N")
	) {
		msgAcomp += "Usuário sem permissão para acesso\n";

	} else if(podeAcessarRelatorio == AcompRelatorioDao.OPERACAO_NEGADA_POSICAO_EM_EDICAO) {
		msgAcomp += "Acesso aos dados não permitido. Situação não foi liberada\n";
	} 
	// BUG 3273
	else if(dataAtual.before(dataInicioAcomp)) {
		msgAcomp += "acompanhamento.acompRelatorio.validacao.dataAtualAnteriorDataInicioAcompanhamento\n";
	} else { // monta a página normalmente
		ConfigMailCfgm configMailCfgm = null;
		if("S".equals(acompanhamento.getIndLiberadoArel())){
			configMailCfgm = (ConfigMailCfgm) configMailCfgmDAO.buscar(ConfigMailCfgm.class, Dominios.CFG_MAIL_RECUPERACAO_PARECER);
		} else {
			configMailCfgm = (ConfigMailCfgm) configMailCfgmDAO.buscar(ConfigMailCfgm.class, Dominios.CFG_MAIL_LIBERACAO_PARECER);
		}
%>
<!-- %@ include file="script.jsp" %-->
<%
		String selectedFuncaoParecer = funcaoParecer;
		String selectedFuncao = funcaoStr;
%>
		<input type="hidden" name="mostrarUltimasPosicoes" value="hidden">
		<input type="hidden" name="autorizarMail<%=acompanhamento.getCodArel()%>" id="autorizarMail<%=acompanhamento.getCodArel()%>" value="N">
		<input type="hidden" name="vemDaPgAcompRelatorio" value="N">
		<input type="hidden" name="cod<%=acompanhamento.getCodArel()%>" id="cod<%=acompanhamento.getCodArel()%>" value="<%=cod%>">
		<input type="hidden" name="inclusaoAnexo<%=acompanhamento.getCodArel()%>" id="inclusaoAnexo<%=acompanhamento.getCodArel()%>" value="<%=inclusaoAnexo%>">
		<input type="hidden" name="codAri<%=acompanhamento.getCodArel()%>" id="codAri<%=acompanhamento.getCodArel()%>" value="<%=codAri%>">
		<input type="hidden" name="codTpfa<%=acompanhamento.getCodArel()%>" id="codTpfa<%=acompanhamento.getCodArel()%>" value="<%=codTpfa%>">  
		<input type="hidden" name="funcao<%=acompanhamento.getCodArel()%>" id="funcao<%=acompanhamento.getCodArel()%>" value="<%=selectedFuncao%>">	
		<input type="hidden" name="funcaoParecer<%=acompanhamento.getCodArel()%>" id="funcaoParecer<%=acompanhamento.getCodArel()%>" value="<%=selectedFuncaoParecer%>">
		<input type="hidden" name="indLiberado<%=acompanhamento.getCodArel()%>" id="indLiberado<%=acompanhamento.getCodArel()%>" value="<%=Pagina.trocaNull(acompanhamento.getIndLiberadoArel())%>">	
		<input type="hidden" name="obrigatorio<%=acompanhamento.getCodArel()%>" id="obrigatorio<%=acompanhamento.getCodArel()%>" value="<%=configMailCfgm.getIndEnvioObrigatorio()%>">
		<input type="hidden" name="envioEmailAtivo<%=acompanhamento.getCodArel()%>" id="envioEmailAtivo<%=acompanhamento.getCodArel()%>" value="<%=configMailCfgm.getAtivoCfgm()%>">
		<input type="hidden" name="hidAcao<%=acompanhamento.getCodArel()%>" id="hidAcao<%=acompanhamento.getCodArel()%>" value="">
		<input type="hidden" name="descricaoIettuc" value="<%=descricaoIettuc%>">
		<input type="hidden" name="nomeIettuc" value="<%=descricaoIettuc%>">
		<input type="hidden" name="codIettuc<%=acompanhamento.getCodArel()%>" id="codIettuc<%=acompanhamento.getCodArel()%>" value="<%=categoriaAnexo == null? "": String.valueOf(categoriaAnexo.getCodIettuc())%>">
		<input type="hidden" name="uploadTipoCategoriaUtc" value="<%=uploadTipoCategoriaUtc.getCodUtc()%>">
		  
		<tr>
			<td class="espacador" colspan="2"><img src="<%=_pathEcar%>/images/pixel.gif" alt=""></td>
		</tr>
		<tr>
			<td colspan="2">
				<!-- Chama a parte de anexos da aba de situação -->
				<%@ include  file="acompAnexo.jsp"%>
			</td>
		</tr>
		
<%
} 
%>

<!-- Funções necessárias para  funcionar a parte de anexos-->
<script language=javascript>
	
	function mostrarEsconder(indice) {
		if (document.getElementById('ultimasPosicoes'+indice).style.display=='none') {
		     document.getElementById('ultimasPosicoes'+indice).style.display='';
		} else {
		     document.getElementById('ultimasPosicoes'+indice).style.display='none';
		}
	}   
	
		function mostrarEsconderAnexos(indice) {
		if (document.getElementById('anexos'+indice).style.display=='none') {
		     document.getElementById('anexos'+indice).style.display='';
		} else {
		    document.getElementById('anexos'+indice).style.display='none';
		}
	}

	
	function aoClicarVoltar(form, indice){
		var anexosUploadTag = 'anexosUploadTag'+indice;
		if (document.getElementById(anexosUploadTag).style.display!='none') {
		     document.getElementById(anexosUploadTag).style.display='none';
		     document.getElementById('anexosListaTag'+indice).style.display ='';
		     document.getElementById('quantidade_anexos'+indice).style.display='';
		     document.getElementById('cod').value = '';
		}
	}
	
</script>
		
