
<jsp:directive.page import="ecar.pojo.TipoAcompanhamentoTa"/><!-- Esta tela contém as informações sobre parecer -->

<%@page import="ecar.pojo.ConfiguracaoCfg"%>
<%@page import="ecar.dao.ConfiguracaoDao"%><jsp:directive.page import="ecar.pojo.TipoAcompanhamentoTa"/><!-- Esta tela contém as informações sobre parecer -->

<%
	
	String msgAcomp = "";
	isFormUpload = FileUpload.isMultipartContent(request);
	campos = new ArrayList();
	msgConfirm = null;
	Long codAri = null;
	Long codTpfa = null;
	String funcaoStr = null;
	String funcaoParecer = null;
	String primeiroAcomp = null;
	String codAcomp = null;
	String cod = null;
	String codPontoCritico = null;
	String inclusaoAnexo = null;
	String inclusaoPontoCritico = null;
	String descricaoIettuc = null;
	String radioCorAzul = null;
	String radioCorCinza = null;
	String situacaoSitAlcancado = null;
	String situacaoSitCancelado = null;

	// Configuração	

	String pathRaiz = configuracao.getRaizUpload();	
	
	if (isFormUpload) {
		codAri = Long.valueOf(FileUpload.verificaValorCampo(campos, "codAri"));
		codTpfa = Long.valueOf(FileUpload.verificaValorCampo(campos, "codTpfa"));
		funcaoStr = FileUpload.verificaValorCampo(campos, "funcao");
		funcaoParecer = FileUpload.verificaValorCampo(campos, "funcaoParecer");
		primeiroAcomp = FileUpload.verificaValorCampo(campos, "primeiroAcomp");
		codAcomp = FileUpload.verificaValorCampo(campos, "codAcomp");
		cod = FileUpload.verificaValorCampo(campos, "cod");
		codPontoCritico = FileUpload.verificaValorCampo(campos, "codPontoCritico");
		inclusaoAnexo = FileUpload.verificaValorCampo(campos, "inclusaoAnexo");
		inclusaoPontoCritico = FileUpload.verificaValorCampo(campos, "inclusaoPontoCritico");
		msgConfirm = FileUpload.verificaValorCampo(campos, "msgConfirm");
	} else {
		codAri = Long.valueOf(Pagina.getParamStr(request, "codAri"));
		codTpfa = acompRelatorio.getTipoFuncAcompTpfa().getCodTpfa(); 
		funcaoStr = Pagina.getParamStr(request, "funcao");
		funcaoParecer = Pagina.getParamStr(request, "funcaoParecer");
		primeiroAcomp = Pagina.getParamStr(request, "primeiroAcomp");
		codAcomp = acompRelatorio.getAcompReferenciaItemAri().getAcompReferenciaAref().getTipoAcompanhamentoTa().getCodTa().toString(); 
		codAcomp = Pagina.getParamStr(request, "codAcomp");
		cod = Pagina.getParamStr(request, "cod");
		codPontoCritico = Pagina.getParamStr(request, "codPontoCritico");
		inclusaoAnexo = Pagina.getParamStr(request, "inclusaoAnexo");
		inclusaoPontoCritico = Pagina.getParamStr(request, "inclusaoPontoCritico");
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
		
	
	TipoFuncAcompTpfa funcao = (TipoFuncAcompTpfa) tipoFuncAcompDao.buscar(TipoFuncAcompTpfa.class, codTpfa);
	
	/* verificar se já existe o acompanhamento Relatório*/
	AcompRelatorioArel acompanhamento = new AcompRelatorioArel();
	AcompRelatorioArel acompBusca = acompRelatorioDao.getAcompRelatorio(funcao,ari);
	if(acompBusca != null)
		acompanhamento = acompBusca;
	boolean permissaoAcesso = false;
	
	int podeGravarRelatorio = acompRelatorioDao.podeGravarRelatorio(usuario, funcao, ari, acompanhamento);
	int podeAcessarRelatorio = acompRelatorioDao.podeAcessarRelatorio(usuario, funcao, acompanhamento);
	/* verificar se o usuário acessa essas informações */
	if(podeAcessarRelatorio != AcompRelatorioDao.OPERACAO_PERMITIDA) {
		_disabled = "disabled";
		_readOnly = "readOnly";
	}
	
	if(podeGravarRelatorio == AcompRelatorioDao.OPERACAO_PERMITIDA) {
		_disabled = "";
		_readOnly = "";
	} else {
		_disabled = "disabled";
		_readOnly = "readOnly";
	}
	
	/* verificar se Acompanhamento já está concluído ( não edita) */
	if(ari.getStatusRelatorioSrl() != null && ari.getStatusRelatorioSrl().getCodSrl().intValue() == AcompReferenciaItemDao.STATUS_LIBERADO){
		_disabled = "disabled";
		_readOnly = "readOnly";	
	}
	
	/* verificar se Data limite foi superada ( não edita) */
	if(acompRelatorioDao.isDataLimiteParecerVencida(funcao, ari)){
		_disabled = "disabled";
		_readOnly = "readOnly";	
		podeGravarRelatorio = AcompRelatorioDao.OPERACAO_NEGADA_DATA_ULTRAPASSADA;
		msgData = "Data limite para emissão da posição foi ultrapassada.";
		msgAcomp += msgData + "\n";
	}
	
	// BUG 3273
	/*  Permitir registrar informação somente a partir da data de início do acompanhamento */
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
    		
    		
	if(podeAcessarRelatorio == AcompRelatorioDao.OPERACAO_NEGADA_USUARIO_SEM_PERMISSAO && !(acompRelatorio.getTipoFuncAcompTpfa().getIndVisualizarParecer().equalsIgnoreCase("S"))) {
		msgAcomp += "Usuário sem permissão para acesso\n"; 
%>
		<!-- script language="javascript">
	    	alert('Usuário sem permissão para acesso');
	    	history.back();
		</script-->
<%
	} else if(podeAcessarRelatorio == AcompRelatorioDao.OPERACAO_NEGADA_POSICAO_EM_EDICAO && !(acompRelatorio.getTipoFuncAcompTpfa().getIndVisualizarParecer().equalsIgnoreCase("S"))) {
		msgAcomp += "Acesso aos dados não permitido. Situação não foi liberada\n";
%>
		<!--script language="javascript">
		    alert('Acesso aos dados não permitido. Situação não foi liberada');
	    	history.back();
		</script-->
<%
	} 
	// BUG 3273
	else if(dataAtual.before(dataInicioAcomp)) {
		msgAcomp += "acompanhamento.acompRelatorio.validacao.dataAtualAnteriorDataInicioAcompanhamento\n";
%>
		<!--script language="javascript">
	    	alert('<%--=_msg.getMensagem("acompanhamento.acompRelatorio.validacao.dataAtualAnteriorDataInicioAcompanhamento")--%>');
	    	history.back();
		</script-->
<%
	} else { // monta a página normalmente
%>
<%
		ConfigMailCfgm configMailCfgm = null;
		if("S".equals(acompanhamento.getIndLiberadoArel())){
			configMailCfgm = (ConfigMailCfgm) configMailCfgmDAO.buscar(ConfigMailCfgm.class, Dominios.CFG_MAIL_RECUPERACAO_PARECER);
		} else {
			configMailCfgm = (ConfigMailCfgm) configMailCfgmDAO.buscar(ConfigMailCfgm.class, Dominios.CFG_MAIL_LIBERACAO_PARECER);
		}
		
		%>
		<%@ include file="script.jsp" %>
		<%
	

		String selectedFuncaoParecer = funcaoParecer;
		String selectedFuncao = funcaoStr;
%>
		<input type="hidden" name="mostrarUltimasPosicoes" value="hidden">
		<input type="hidden" name="autorizarMail<%=acompanhamento.getCodArel()%>" id="autorizarMail<%=acompanhamento.getCodArel()%>" value="N">
		<input type="hidden" name="vemDaPgAcompRelatorio" value="N">
		<input type="hidden" name="cod<%=acompanhamento.getCodArel()%>" id="cod<%=acompanhamento.getCodArel()%>" value="<%=cod%>">
		<input type="hidden" name="codPontoCritico<%=acompanhamento.getCodArel()%>" id="codPontoCritico<%=acompanhamento.getCodArel()%>" value="<%=codPontoCritico%>">
		<input type="hidden" name="inclusaoAnexo<%=acompanhamento.getCodArel()%>" id="inclusaoAnexo<%=acompanhamento.getCodArel()%>" value="<%=inclusaoAnexo%>">
		<input type="hidden" name="inclusaoPontoCritico<%=acompanhamento.getCodArel()%>" id="inclusaoPontoCritico<%=acompanhamento.getCodArel()%>" value="<%=inclusaoPontoCritico%>">
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
				  
		<table name="form" class="form" width="100%">
			<tr>
				<td colspan="2">	
					<table class="barrabotoes" cellpadding="0" cellspacing="0">
						<tr>
							<td class="label">&nbsp;</td>
							<td>
								<%//Verifica se a situação já foi liberada para exibir os botões Gravar e Cancelar. 
								if(!labelBotao.equals("Recuperar")){%>
									<input name="btn1" type="button" value="Gravar" class="botao" onclick="aoClicarBtn1Arel<%=acompanhamento.getCodArel()%>(form, '<%=acompanhamento.getCodArel()%>');">				
									<input name="btn2" type="button" value="Cancelar" class="botao" onclick="aoClicarBtn2Arel<%=acompanhamento.getCodArel()%>(form, '<%=acompanhamento.getCodArel()%>');">
								<%}
								
								//Exibe o botão liberar caso a opção "Exige Liberar Parecer" esteja marcada na configuração do tipo de acompanhamento.
								if(tipoAcompanhamento.getIndLiberarParecerTa().equals("S") || labelBotao.equals("Recuperar")){%>
								    <input type="hidden" name="indLiberarParecer<%=acompanhamento.getCodArel()%>" id="indLiberarParecer<%=acompanhamento.getCodArel()%>" value="S">
									<input name="btn3" type="button" value="<%=labelBotao%>"  class="botao" onclick="if(<%=usuarioLogadoEmitiuUltimoParecer%> == false){alert('Apenas usuários com hierarquia igual ou superior ao que realizou a liberação poderão efetuar a operação.');} else {aoClicarBtn3Arel<%=acompanhamento.getCodArel()%>(form, '<%=acompanhamento.getCodArel()%>');}">
								<%}else{%>
									<input type="hidden" name="indLiberarParecer<%=acompanhamento.getCodArel()%>" id="indLiberarParecer<%=acompanhamento.getCodArel()%>" value="N">
								<%}%>
							</td>
						</tr>
					</table>
				</td>
			</tr>
			<tr>
				<td>&nbsp;</td>
			 	<td><b style="color:red;"><%=msgData%></b></td>
			</tr>
			<tr>
			 	<td class="label">* <%=configuracao.getLabelSituacaoParecer() %>:</td></td>
			 	<td>
<%
		String selected = "";
		List situacoesItem = situacaoDao.getSituacaoByTipoFuncaoAcompanhamentoEstrutura(funcao, ari.getItemEstruturaIett().getEstruturaEtt());

		if(situacoesItem != null && situacoesItem.size() > 0){
		 	/*Ordenar alfabeticamente as situações. */
		 	Collections.sort(situacoesItem,
			new Comparator() {
        		public int compare(Object o1, Object o2) {
        		    return ( (SituacaoSit)o1 ).getDescricaoSit().compareToIgnoreCase( ( (SituacaoSit)o2 ).getDescricaoSit() );
        		}
    		} );
		}

		 
		if(acompanhamento.getSituacaoSit() != null){ 
		 	selected = acompanhamento.getSituacaoSit().getCodSit().toString();
		}
		String scriptsSituacao = _disabled + " onchange=\"javascript:verificarSituacao(this.value)\"";
		String situacaoSit = "situacaoSit" + acompanhamento.getCodArel();
		situacaoSitCancelado = "\"select#"+situacaoSit+ " option[value=17]\").attr(\"selected\",true";
		situacaoSitAlcancado = "\"select#"+situacaoSit+ " option[value=2]\").attr(\"selected\",true";
%>
				<combo:ComboTag nome="<%=situacaoSit%>"
					objeto="ecar.pojo.SituacaoSit"
					label="descricaoSit" value="codSit"
					order="descricaoSit" selected="<%=selected%>"
					scripts="<%=scriptsSituacao%>" colecao="<%=situacoesItem%>"  />			 
				</td>
			</tr>  
			<tr>
				 <td class="label" valign="top">* <%=configuracao.getLabelCorParecer() %>:</td></td>
				 <td>
<%
		String imagePath = "";
		Iterator itCores = corDao.listar(Cor.class, null).iterator();
		int contCor = 0;
		
		String hashNomeArquivo = null;
		UsuarioUsu usuarioImagem = null;
		
		while(itCores.hasNext()){
			Cor cor_ = (Cor) itCores.next();
			String checked= "";
			if(acompanhamento.getCor() != null){
				if(acompanhamento.getCor().equals(cor_)){
					checked = "checked";
				}
			}
			
			if(cor_.getIndPosicoesGeraisCor().equals("S")){ %>
				<input type="radio" class="form_check_radio" name="cor<%=acompanhamento.getCodArel()%>" id="cor<%=acompanhamento.getCodArel()%>i<%=++contCor %>" <%=_disabled%> <%=checked%> value="<%=cor_.getCodCor()%>" onclick=mostraMensagem();> 
				<label for="cor"+<%=contCor %>> 
<%
				if (cor_.getCodCor() == 10) {
					radioCorAzul = "\"#cor" + acompanhamento.getCodArel() + "i" + contCor + "\"";
				} 
				if (cor_.getCodCor() == 11) {
					radioCorCinza =  "\"#cor" + acompanhamento.getCodArel() + "i" + contCor + "\"";
				} 
				imagePath = corDao.getImagemPersonalizada(cor_, funcao, "D");
				if( imagePath != null ) { 
				
					hashNomeArquivo = Util.calcularHashNomeArquivo(pathRaiz, imagePath);
					usuarioImagem = ((ecar.login.SegurancaECAR)request.getSession().getAttribute("seguranca")).getUsuario();  
					Util.adicionarMapArquivosAtuaisUsuarios(usuarioImagem, hashNomeArquivo, pathRaiz, imagePath);
				%>
					<%--<img border="0" src="<%=request.getContextPath()%>/DownloadFile?tipo=open&RemoteFile=<%=hashNomeArquivo%>" style="width: 23px; height: 23px;" title="<%=cor_.getSignificadoCor()%>" align="absmiddle"> --%>
				    <img border="0" src="<%=request.getContextPath()%>/DownloadFile?tipo=open&RemoteFile=<%=hashNomeArquivo%>" title="<%=cor_.getSignificadoCor()%>" align="absmiddle"> <%
				} else {
					if( cor_.getCodCor() != null ) { %>
					<img border="0" src="<%=_pathEcar%>/images/<%=corDao.getImagemSinal(cor_,funcao)%>" title="<%=cor_.getSignificadoCor()%>"  align="absmiddle"> <% 
					}
				} %> <%=cor_.getSignificadoCor()%> <!-- br --> &nbsp; &nbsp;
				</label>
<%			}
		}//fim while itCores
%>			 
				</td>
			</tr>   
			<tr>
			
			
			
				<td class="label" valign="top">* <%=funcao.getLabelPosicaoTpfa()%></td>
				
				<td>
				
				<script language="JavaScript" type="text/javascript">
	                
					initRTE("<%=_pathEcar%>/images/rte/", "<%=_pathEcar%>", "", false);
					//initRTE("<%=_pathEcar%>/images/rte/", "<%=_pathEcar%>/", "");
					
					function mostraMensagem() {
						if($('input:radio[name=cor<%=acompanhamento.getCodArel()%>]:checked').val() == 10){
							$('input:radio[name=cor<%=acompanhamento.getCodArel()%>]:checked').addClass('form_check_radio_qtip');
							$(<%=situacaoSitAlcancado%>);
							$('.form_check_radio_qtip')	
								.removeData('qtip') 
								.qtip({
									content: {
										text: 'A situação "alcançada" representa <b>conclusão</b> do Resultado. Quando alcançado, o item deixa de aparecer nos próximos monitoramentos.', 
										title: {
											text: 'Alerta ',
											button: true
										}
									},
									position: {
										my: 'top left', // Use the corner...
										at: 'bottom left' // ...and opposite corner
									},
									show: {
										event: false, // Don't specify a show event...
										ready: true // ... but show the tooltip when ready
									},
									hide: false, // Don't specify a hide event either!
									style: {
										classes: 'qtip-shadow qtip-dark' 
									}
								});
							}
							if($('input:radio[name=cor<%=acompanhamento.getCodArel()%>]:checked').val() == 11){
								$(<%=situacaoSitCancelado%>);
							}
						}

					function verificarSituacao(codSit){
						if(codSit == "2"){
							$(<%=radioCorAzul%>).attr("checked",true);
							mostraMensagem();
						}
						if(codSit == "17"){
							$(<%=radioCorCinza%>).attr("checked",true);
							mostraMensagem();
						}
					}
	                </script>
	               
	           <script language="JavaScript" type="text/javascript">
	                writeRichText('DescricaoArel<%=acompanhamento.getCodArel()%>', "<%=Pagina.trocaNull(acompanhamento.getDescricaoArel()).replaceAll("\n", " ").replaceAll("\r", " ").replaceAll("</SCRIPT>","&lt;/SCRIPT&gt;").replaceAll("</script>","&lt;/script&gt;").replaceAll("</script -->","&lt;/script --&gt;").replaceAll("</SCRIPT -->","&lt;/SCRIPT --&gt;")%>", 600, 200, true,<%=_readOnly.equals("readOnly")? "true":"false"%>);
		           </script>
				
	                <input type="hidden" name="richTextDescricaoArel<%=acompanhamento.getCodArel()%>" id="richTextDescricaoArel<%=acompanhamento.getCodArel()%>" size="2000">
	                <input type="hidden" name="descricaoArel<%=acompanhamento.getCodArel()%>" id="descricaoArel<%=acompanhamento.getCodArel()%>" size="2000" value="<%=acompanhamento.getDescricaoArel()%>">
            	</td>
				 
				
				
			</tr>	

			<%
				if (ocultarObservacoesParecer == null || ocultarObservacoesParecer.equals("N")){
			%>
			
			<tr>
				<td class="label" valign="top">Observações</td>
				<td>
					<textarea name="complementoArel<%=acompanhamento.getCodArel()%>" 
							id="complementoArel<%=acompanhamento.getCodArel()%>" 
							<%=_readOnly%> 
							cols="96" 
							rows="2" 
							onkeyup="return validaTamanhoLimite(this, 2000);" 
							onkeydown="return validaTamanhoLimite(this, 2000);" 
							onblur="return validaTamanhoLimite(this, 2000);">
							<%=Pagina.trocaNull((acompanhamento.getComplementoArel()))%>
					</textarea>
				</td>
			</tr>
			
			<%
				}
				else{
				
			%>
				<input type="hidden" name="complementoArel<%=acompanhamento.getCodArel()%>" id="complementoArel<%=acompanhamento.getCodArel()%>	" value="">
			<%
				}
			%>

			<tr>
				<td class="label" valign="top" style="color:red;">Data Limite:</td>
				<%
				AcompRefItemLimitesArli limites = ariDao.getAcompRefItemLimitesByAcompReferenciaItemTipoFuncAcomp(funcao, ari);
				%>
				<td style="color:red;"><b><%=limites != null ? Pagina.trocaNullData(limites.getDataLimiteArli()) : ""%></b></td>
			</tr>
						
			<tr>
				 <td colspan="2">
				 	<table align="center">
				 		<tr>
							 <td class="label">Inclus&atilde;o:</td>
							 <td><%=Pagina.trocaNullData(acompanhamento.getDataInclusaoArel())%>
<%
		if(acompanhamento.getUsuarioUsu() != null) {
			 //Imagem para Indicador de Usuario Inativo
			 String imagem_inativa = "";
				if (!"S".equals(acompanhamento.getUsuarioUsu().getIndAtivoUsu())){
					imagem_inativa="<img src=\"" + _pathEcar + "/images/icon_usuario_inativo.png\" title=\"Usuário Inativo\">";
				}
				
			 out.println("&nbsp;&nbsp;" + acompanhamento.getUsuarioUsu().getNomeUsuSent() + "&nbsp;" +imagem_inativa);
		} 
%>
							</td>
						</tr> 	
				 	</table>
				 </td>
			</tr> 	
			<tr>
				 <td colspan="2">
				 	<table align="center">
				 		<tr>
							 <td class="label">&Uacute;ltima Altera&ccedil;&atilde;o:</td>
							 <td>
<%
		if(acompRelatorio.getDataUltManutArel() != null)
			out.print(Data.parseDate(acompRelatorio.getDataUltManutArel()));
		else
			out.print("N/I");
		if(acompanhamento.getUsuarioUsuUltimaManutencao() != null) {
			//Imagem para Indicador de Usuario INAtivo
			String imagem_inativa = "";
				if (!"S".equals(acompanhamento.getUsuarioUsuUltimaManutencao().getIndAtivoUsu())){
					imagem_inativa="<img src=\"" + _pathEcar + "/images/icon_usuario_inativo.png\" title=\"Usuário Inativo\">";
				}
			 
			 out.println("&nbsp;&nbsp;" + acompanhamento.getUsuarioUsuUltimaManutencao().getNomeUsuSent() + "&nbsp;" +imagem_inativa);
		}
%>
							</td>
						</tr> 	
				 	</table>
				 </td>
			</tr> 	
			<tr>
				<td colspan="2">&nbsp;</td>
			</tr>
			<tr>
				<td colspan="2">	
					<table class="barrabotoes" cellpadding="0" cellspacing="0">
						<tr>
							<td class="label">&nbsp;</td>
							<td>
								<%//Verifica se a situação já foi liberada para exibir os botões Gravar e Cancelar. 
								if(!labelBotao.equals("Recuperar")){%>
									<input name="btn1" type="button" value="Gravar" class="botao" onclick="aoClicarBtn1Arel<%=acompanhamento.getCodArel()%>(form, '<%=acompanhamento.getCodArel()%>');">				
									<input name="btn2" type="button" value="Cancelar" class="botao" onclick="aoClicarBtn2Arel<%=acompanhamento.getCodArel()%>(form, '<%=acompanhamento.getCodArel()%>');">
								<%}
								
								//Exibe o botão liberar caso a opção "Exige Liberar Parecer" esteja marcada na configuração do tipo de acompanhamento.
								if(tipoAcompanhamento.getIndLiberarParecerTa().equals("S") || labelBotao.equals("Recuperar")){%>
								    <input type="hidden" name="indLiberarParecer<%=acompanhamento.getCodArel()%>" id="indLiberarParecer<%=acompanhamento.getCodArel()%>" value="S">
									<input name="btn3" type="button" value="<%=labelBotao%>"  class="botao" onclick="if(<%=usuarioLogadoEmitiuUltimoParecer%> == false){alert('Apenas usuários com hierarquia igual ou superior ao que realizou a liberação poderão efetuar a operação.');} else {aoClicarBtn3Arel<%=acompanhamento.getCodArel()%>(form, '<%=acompanhamento.getCodArel()%>');}">
								<%}else{%>
									<input type="hidden" name="indLiberarParecer<%=acompanhamento.getCodArel()%>" id="indLiberarParecer<%=acompanhamento.getCodArel()%>" value="N">
								<%}%>
							</td>
						</tr>
					</table>
				</td>
			</tr>
	</table> 
<% 	//Exibe Ultimas Posições e popup de Todas Posições s
	List arels = ariDao.getUltimosAcompanhamentosItem(ari, funcao, Integer.valueOf(2));
	if (arels != null && arels.size() > 0) {
%>
	<table>
		<tr>
			<td colspan="2" class="label" style="text-align: left"><!--  href="#" onclick="mostrarEsconder('</%=acompanhamento.getCodArel()%>');" >Mostrar &Uacute;ltimas Posi&ccedil;&otilde;es Emitidas</a></td-->			
			<a href="javascript:mostrarEsconder('<%=acompanhamento.getCodArel()%>');">Mostrar &Uacute;ltimos(as) <%=configuracao.getLabelSituacaoListaPareceres()%> Emitidos(as)</a></td>			
		</tr>
		<tr>
			<td colspan="2" >
				<table id="ultimasPosicoes<%=acompanhamento.getCodArel()%>" style="text-align:left;display:none" class="label" >
<%
			periodo = "";
			String mesAno = "";
			String situacao = "";
			cor = "";
			String descricao = "";
			String observacao = "";

			Iterator itArels = arels.iterator();
			while(itArels.hasNext()){					
				AcompRelatorioArel arel = (AcompRelatorioArel) itArels.next();
				//if(Pagina.getParamStr(request, "codTpfa").equals(arel.getTipoFuncAcompTpfa().getCodTpfa().toString())){				
					if(arel.getAcompReferenciaItemAri() != null &&
						arel.getAcompReferenciaItemAri().getAcompReferenciaAref() != null){
						periodo = arel.getAcompReferenciaItemAri().getAcompReferenciaAref().getNomeAref();
						mesAno = arel.getAcompReferenciaItemAri().getAcompReferenciaAref().getDiaAref() + "/" +
							arel.getAcompReferenciaItemAri().getAcompReferenciaAref().getMesAref() + "/" +
							arel.getAcompReferenciaItemAri().getAcompReferenciaAref().getAnoAref();
					}
												
					if(arel.getSituacaoSit() != null){
						situacao = arel.getSituacaoSit().getDescricaoSit();
					}
					else{
						situacao = "N/I";
					}
					
					if(arel.getCor() != null){
						imagePath = corDao.getImagemPersonalizada(arel.getCor(), arel.getTipoFuncAcompTpfa(), "D");
						if( imagePath != null && imagePath.trim().length()>0) {
							usuarioImagem = ((ecar.login.SegurancaECAR)request.getSession().getAttribute("seguranca")).getUsuario(); 
							hashNomeArquivo = Util.calcularHashNomeArquivo(pathRaiz, imagePath);
							Util.adicionarMapArquivosAtuaisUsuarios(usuarioImagem, hashNomeArquivo, pathRaiz, imagePath);
							
							/* -- As tags do CSS "max-width" e "max-height" não funcionam no IE6 ou menor -- */ 
						    cor = "<img border=\"0\" src=\"" + request.getContextPath() + "/DownloadFile?tipo=open&RemoteFile=";
						    cor += hashNomeArquivo + "\" style=\"width: 23px; height: 23px;\" title=\"" + arel.getTipoFuncAcompTpfa().getLabelTpfa() + "\">";
						} else {
							if( arel.getCor() != null && arel.getCor().getCodCor() != null ) { 
								cor = "<img border=\"0\" src=\"" + _pathEcar + "/images/";
								cor += corDao.getImagemSinal(arel.getCor(), arel.getTipoFuncAcompTpfa()) + "\" title=\"";
								cor += arel.getTipoFuncAcompTpfa().getLabelTpfa() + "\" >";
							}
						}
					}
					else {
						cor = "N/I";
					}
					
					if(arel.getDescricaoArel() != null && !"".equals(arel.getDescricaoArel().trim())){
						descricao = Util.normalizaQuebraDeLinhaHTML(arel.getDescricaoArel());
						descricao = Util.normalizaCaracterMarcador(descricao);
						descricao = Util.normalizaCaracterHTML(descricao);
					}
					else {
						descricao = "N/I";
					}
					
					if(arel.getComplementoArel() != null && !"".equals(arel.getComplementoArel().trim())){
						observacao = Util.normalizaQuebraDeLinhaHTML(arel.getComplementoArel());
						observacao = Util.normalizaCaracterMarcador(observacao);
						observacao = Util.normalizaCaracterHTML(observacao);
					}
					else {
						observacao = "N/I";
					}
%>
					<!-- Mostrar últimas posições emitidas -->
					<tr> 
						<td class="label">Per&iacute;odo</td>
						<td><%=periodo%></td>
					</tr>
					<tr>
						<td class="label">Dia/Mês/Ano</td>
						<td><%=mesAno%></td>
					</tr>
					<tr>
						 <td class="label"><%=configuracao.getLabelSituacaoParecer() %>:</td>
						 <td><%=situacao%></td>
					</tr> 	
					<tr>
						 <td class="label"><%=configuracao.getLabelCorParecer() %>:</td>
						 <td><%=cor%></td>
					</tr> 
					<tr>
						 <td class="label"><%=acompRelatorio.getTipoFuncAcompTpfa().getLabelPosicaoTpfa()%>:</td>
						 <td><%=descricao%></td>
					</tr> 
					
					<%
					if (ocultarObservacoesParecer == null || ocultarObservacoesParecer.equals("N")){
					%>
					
						<tr>
							 <td class="label">Observa&ccedil;&otilde;es:</td>
							 <td><%=observacao%></td>
						</tr> 
					
					<%
					}
					%>
					
					<tr>
						 <td class="label" colspan="2"> &nbsp;</td>
					</tr> 
<%
				//}
			}//fim while
%>
				</table>
			</td>
		</tr>
<%
	}// fim if (arels != null && arels.size() > 0){
%>
		<tr>
			<td colspan="2" class="label" style="text-align: left"> 
				<a href="#" onclick="abrirPopUpTodasPosicoes(form, '<%=acompanhamento.getCodArel()%>');">Mostrar Todos(as) <%=configuracao.getLabelSituacaoListaPareceres() %> Emitidos(as)</a> 
			</td>			
		</tr>
		<tr>
			<td class="espacador" colspan="2"><img src="<%=_pathEcar%>/images/pixel.gif" alt=""></td>
		</tr>
		<tr>
			<td colspan="2">
				<!-- Chama a parte de anexos e pontos criticos da aba de situação -->
				<%@ include  file="acompAnexo.jsp"  %>
			</td>
		</tr>
	</table>
<%
} 
%>