<jsp:directive.page import="ecar.util.Dominios"/>
<jsp:directive.page import="comum.util.Util"/>
<jsp:directive.page import="ecar.dao.ItemEstruturaUploadCategoriaDao"/>
<jsp:directive.page import="java.util.HashSet"/>
<jsp:directive.page import="ecar.pojo.UploadTipoArquivoUta"/>
<jsp:directive.page import="ecar.pojo.UploadTipoCategoriaUtc"/>
<jsp:directive.page import="ecar.dao.UploadTipoCategoriaDao"/>
<jsp:directive.page import="ecar.pojo.Aba"/>
<jsp:directive.page import="ecar.dao.AbaDao"/>
<%@page import="java.util.Collection"%>
<%@page import="ecar.dao.TipoAcompanhamentoDao"%>
<%@page import="ecar.pojo.TipoAcompFuncAcompTafc"%>
<%@page import="java.net.URLEncoder"%>

<%@ include file="/login/validaAcesso.jsp"%>
<%@ include file="/frm_global.jsp"%>

<%@ page import="ecar.pojo.AcompReferenciaItemAri" %> 
<%@ page import="ecar.pojo.UsuarioUsu" %>
<%@ page import="ecar.pojo.AcompRelatorioArel" %> 
<%@ page import="ecar.pojo.Cor" %>
<%@ page import="ecar.pojo.SituacaoSit" %>
<%@ page import="ecar.pojo.TipoFuncAcompTpfa" %>
<%@ page import="ecar.dao.AcompReferenciaItemDao" %> 
<%@ page import="ecar.dao.TipoFuncAcompDao" %>
<%@ page import="ecar.dao.AcompRelatorioDao" %>
<%@ page import="ecar.dao.CorDao" %>
<%@ page import="ecar.dao.SituacaoDao" %>
<%@ page import="ecar.dao.ItemEstUsutpfuacDao" %>
<%@ page import="ecar.pojo.ConfigMailCfgm" %>
<%@ page import="ecar.dao.ConfigMailCfgmDAO" %>
<%@ page import="ecar.pojo.ItemEstrutUploadIettup"%>
<%@ page import="ecar.dao.EstruturaFuncaoDao" %>
<%@ page import="ecar.pojo.EstruturaFuncaoEttf" %>
<%@ page import="ecar.pojo.ItemEstruturaIett" %>
<%@page import="ecar.pojo.ItemEstrUplCategIettuc"%>
<%@ page import="ecar.dao.ItemEstruturaUploadDao" %>
<%@ page import="ecar.pojo.SisGrupoAtributoSga"%>
<%@ page import="ecar.pojo.PontoCriticoPtc"%>
<%@ page import="ecar.dao.PontoCriticoDao"%>
<%@ page import="java.util.Set"%>


<%@ page import="java.util.Iterator" %> 
<%@ page import="java.util.List" %>
<%@ page import="java.util.Date" %>
<%@ page import="java.util.Calendar" %>
<%@ page import="java.util.Comparator" %>
<%@ page import="java.util.Collections" %>
<%@ page import="ecar.dao.AcompReferenciaDao" %>
<%@ page import="ecar.pojo.AcompReferenciaAref" %>
<%@ page import="java.util.Map" %>
<%@ page import="ecar.pojo.StatusRelatorioSrl" %>
<%@ page import="ecar.dao.ItemEstruturaDao" %>
<%@ page import="comum.util.Data" %>
<%@ page import="ecar.pojo.ItemEstUsutpfuacIettutfa" %>
<%@ page import="comum.util.FileUpload" %>

<%@ taglib uri="/WEB-INF/ecar-util.tld" prefix="util" %>
<%@ taglib uri="/WEB-INF/ecar-combo.tld" prefix="combo" %>
 
<%
String msgConfirm = null;
try{ 

	// Configuração	
	ConfiguracaoCfg configuracao = (ConfiguracaoCfg)session.getAttribute("configuracao");
	String pathRaiz = configuracao.getRaizUpload();
	
	boolean isFormUpload = FileUpload.isMultipartContent(request);
	List campos = new ArrayList();
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
	String codPontoCritico = null;
	String inclusaoAnexo = null;
	String inclusaoPontoCritico = null;
	String descricaoIettuc = null;
	
	if (isFormUpload){
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
		codTpfa = Long.valueOf(Pagina.getParamStr(request,"codTpfa"));
		funcaoStr = Pagina.getParamStr(request, "funcao");
		funcaoParecer = Pagina.getParamStr(request, "funcaoParecer");
		primeiroAcomp = Pagina.getParamStr(request, "primeiroAcomp");
		codAcomp = Pagina.getParamStr(request, "codAcomp");
		cod = Pagina.getParamStr(request, "cod");
		codPontoCritico = Pagina.getParamStr(request, "codPontoCritico");
		inclusaoAnexo = Pagina.getParamStr(request, "inclusaoAnexo");
		inclusaoPontoCritico = Pagina.getParamStr(request, "inclusaoPontoCritico");
		msgConfirm = Pagina.getParamStr(request, "msgConfirm");
	}
	
	SituacaoDao situacaoDao = new SituacaoDao(request);
	AcompReferenciaDao acompRefDao = new AcompReferenciaDao(request);
	AcompReferenciaItemDao acompReferenciaItemDao = new AcompReferenciaItemDao(request); 
	ItemEstruturaDao itemEstruturaDao = new ItemEstruturaDao(request);
	CorDao corDao = new CorDao(request);
	AcompRelatorioDao acompRelatorioDao = new AcompRelatorioDao(request);
	ItemEstUsutpfuacDao itemEstUsutpfuacDao = new ItemEstUsutpfuacDao();
	TipoFuncAcompDao tipoFuncAcompDao = new TipoFuncAcompDao(request);
	ConfigMailCfgmDAO configMailCfgmDAO = new ConfigMailCfgmDAO();

	String ocultarObservacoesParecer = configuracao.getIndOcultarObservacoesParecer();

	String msgData = "";

	UsuarioUsu usuario = ((ecar.login.SegurancaECAR)session.getAttribute("seguranca")).getUsuario();
	AcompReferenciaItemAri acompReferenciaItem = (AcompReferenciaItemAri) acompReferenciaItemDao.buscar(AcompReferenciaItemAri.class, codAri);
	
	descricaoIettuc = "Anexos Acomp. " + acompReferenciaItem.getAcompReferenciaAref().getNomeAref();
	
	
	//
	String paramCodIett = acompReferenciaItem.getItemEstruturaIett().getCodIett().toString();
	
	ItemEstrutUploadIettup anexo = null;
	if (cod != null && !cod.equals("")){
		anexo = (ItemEstrutUploadIettup) new ItemEstruturaUploadDao(request).buscar(ItemEstrutUploadIettup.class, Long.valueOf(cod));
	} else {
		anexo = new ItemEstrutUploadIettup();
	} 

	EstruturaFuncaoDao estruturaFuncaoDao = new EstruturaFuncaoDao(request);
	
	ItemEstruturaIett itemEstrutura = (ItemEstruturaIett) itemEstruturaDao.buscar(ItemEstruturaIett.class, Long.valueOf(paramCodIett));
	
	EstruturaFuncaoEttf estruturaFuncao = (EstruturaFuncaoEttf) estruturaFuncaoDao.getItensAnexo(itemEstrutura.getEstruturaEtt());
		
	EstruturaFuncaoEttf estruturaFuncaoItensAnexo = estruturaFuncaoDao.getItensAnexo(itemEstrutura.getEstruturaEtt());
	
	String caminhoRaizUpload = configuracao.getRaizUpload();
	
	ItemEstruturaUploadCategoriaDao categoriaAnexoDao = new ItemEstruturaUploadCategoriaDao(null);
    ItemEstrUplCategIettuc categoriaAnexo = new ItemEstrUplCategIettuc();

   	categoriaAnexo.setAcompReferenciaItemAri(acompReferenciaItem);
   	
    List lista = categoriaAnexoDao.pesquisar(categoriaAnexo, new String[]{"acompReferenciaItemAri", "asc"});
	Iterator itAbas = lista.iterator();

	if (itAbas.hasNext()) {
		categoriaAnexo = (ItemEstrUplCategIettuc) itAbas.next();
	} else {
		categoriaAnexo = null;
	}
	 
	//ItemEstrUplCategIettuc categoriaAnexo = (ItemEstrUplCategIettuc) new ItemEstruturaUploadDao(request).buscar(ItemEstrUplCategIettuc.class, new Long(6));
	
	//
	
	
	ValidaPermissao validaPermissao = new ValidaPermissao();
	if ( !validaPermissao.permissaoConsultaIETT( acompReferenciaItem.getItemEstruturaIett().getCodIett() , seguranca ) )
	{
		response.sendRedirect( request.getContextPath() +  "/acessoIndevido.jsp" );
	}	
	
	Boolean permissaoAlterar = validaPermissao.permissaoAlterarItem(itemEstrutura, seguranca.getUsuario(), seguranca.getGruposAcesso(), estruturaFuncaoItensAnexo);
	
	TipoFuncAcompTpfa funcao = (TipoFuncAcompTpfa) tipoFuncAcompDao.buscar(TipoFuncAcompTpfa.class, codTpfa);
	
	/* verificar se já existe o acompanhamento Relatório*/
	AcompRelatorioArel acompanhamento = new AcompRelatorioArel();
	AcompRelatorioArel acompBusca = acompRelatorioDao.getAcompRelatorio(funcao,acompReferenciaItem);
	if(acompBusca != null)
		acompanhamento = acompBusca;
	boolean permissaoAcesso = false;
	
	int podeGravarRelatorio = acompRelatorioDao.podeGravarRelatorio(usuario, funcao, acompReferenciaItem, acompanhamento);
	int podeAcessarRelatorio = acompRelatorioDao.podeAcessarRelatorio(usuario, funcao, acompanhamento);
	/* verificar se o usuário acessa essas informações */
	if(podeAcessarRelatorio != AcompRelatorioDao.OPERACAO_PERMITIDA){
		_disabled = "disabled";
		_readOnly = "readOnly";
	}
	
	if(podeGravarRelatorio == AcompRelatorioDao.OPERACAO_PERMITIDA){
		_disabled = "";
		_readOnly = "";
	} else {
		_disabled = "disabled";
		_readOnly = "readOnly";
	}
	
	/* verificar se Acompanhamento já está concluído ( não edita) */
	if(acompReferenciaItem.getStatusRelatorioSrl() != null && acompReferenciaItem.getStatusRelatorioSrl().getCodSrl().intValue() == AcompReferenciaItemDao.STATUS_LIBERADO){
		_disabled = "disabled";
		_readOnly = "readOnly";	
	}
	
	/* verificar se Data limite foi superada ( não edita) */
	if(acompRelatorioDao.isDataLimiteParecerVencida(funcao, acompReferenciaItem)){
		_disabled = "disabled";
		_readOnly = "readOnly";	
		podeGravarRelatorio = AcompRelatorioDao.OPERACAO_NEGADA_DATA_ULTRAPASSADA;
		msgData = "Data limite para emissão da posição foi ultrapassada.";
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
	
	dataInicioAcomp.setTime(acompReferenciaItem.getDataInicioAri());
	
	dataInicioAcomp.clear(Calendar.HOUR);
	dataInicioAcomp.clear(Calendar.HOUR_OF_DAY);
	dataInicioAcomp.clear(Calendar.MINUTE);
	dataInicioAcomp.clear(Calendar.SECOND);
	dataInicioAcomp.clear(Calendar.MILLISECOND);
	
	ItemEstUsutpfuacIettutfa itemEstUsu = (ItemEstUsutpfuacIettutfa) itemEstUsutpfuacDao.getUsuarioAcompanhamento(acompReferenciaItem.getItemEstruturaIett(), funcao);
	
	
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
    		
    		
	

	if(podeAcessarRelatorio == AcompRelatorioDao.OPERACAO_NEGADA_USUARIO_SEM_PERMISSAO) {
	%>
		<script language="javascript">
	    	alert('Usuário sem permissão para acesso');
	    	history.back();
		</script>
	<%
	} 
	else if(podeAcessarRelatorio == AcompRelatorioDao.OPERACAO_NEGADA_POSICAO_EM_EDICAO) {
	%>
		<script language="javascript">
		    alert('Acesso aos dados não permitido. Posição não foi liberada');
	    	history.back();
		</script>
	<%
	} 
	// BUG 3273
	else if(dataAtual.before(dataInicioAcomp)) {
	%>
		<script language="javascript">
	    	alert('<%=_msg.getMensagem("acompanhamento.acompRelatorio.validacao.dataAtualAnteriorDataInicioAcompanhamento")%>');
	    	history.back();
		</script>
	<%
	}
	else 
	{ // monta a página normalmente
	%>

	<html lang="pt-br"> 
	<head>
	<%@ include file="/include/meta.jsp"%>
	<%@ include file="/titulo.jsp"%>
	<link rel="stylesheet" href="<%=_pathEcar%>/css/style_<%=configuracaoCfg.getEstilo().getNome()%>.css" type="text/css">
	
	<script language="javascript" src="<%=_pathEcar%>/js/menu_retratil.js"></script>
	<script language="javascript" src="<%=_pathEcar%>/js/focoInicial.js"></script>
	<script language="javascript" src="<%=_pathEcar%>/js/datas.js"></script>
	<script language="javascript" src="<%=_pathEcar%>/js/mascaraMoeda.js"></script>
	<script language="javascript" src="<%=_pathEcar%>/js/validacoes.js"></script>
	<script language="javascript" src="<%=_pathEcar%>/js/popUp.js"></script>
	<script language="javascript" src="<%=_pathEcar%>/js/tableSort.js"></script>
	<script language="javascript" src="<%=_pathEcar%>/js/prototype.js"></script>
	<script language="javascript" src="<%=_pathEcar%>/js/frmPadraoItemEstrut.js"></script>
	<script language="javascript" src="<%=_pathEcar%>/js/destaqueLinha.js"></script>

	<!-- script language="javascript" src="../../js/frmPadraoItemEstrut.js"></script-->
	

	
	<%
		ConfigMailCfgm configMailCfgm = null;
		if("S".equals(acompanhamento.getIndLiberadoArel())){
			configMailCfgm = (ConfigMailCfgm) configMailCfgmDAO.buscar(ConfigMailCfgm.class, Dominios.CFG_MAIL_RECUPERACAO_PARECER);
		} else {
			configMailCfgm = (ConfigMailCfgm) configMailCfgmDAO.buscar(ConfigMailCfgm.class, Dominios.CFG_MAIL_LIBERACAO_PARECER);
		}
	%>
	
	
	<script> 
	
	<%
	
	String periodoSel = Pagina.getParamStr(request, "periodoSel");
	
	if (periodoSel.equals("") )
		periodoSel = "3";
	%>
	

	function reload(){
		periodoSel = document.form.periodoSel.value;
		if(periodoSel != ""){
			form.action = "acompRelatorio.jsp?periodoSel="+periodoSel;
			form.submit();
		}
	}
	
	
	
	function abrirPopUpTodasPosicoes(form){
		abreJanela("popupTodasPosicoes.jsp?codAri=" + form.codAri.value + "&codTpfa=" + form.codTpfa.value, 700, 500, "Todas");
	}
	
	function aoClicarBtn2(form){
			form.reset();
	}
	<%
	if(podeGravarRelatorio==AcompRelatorioDao.OPERACAO_PERMITIDA){
	%>
	
	function aoClicarBtn1(form){
			if(valida(form)){
				if(confirm("<%=_msg.getMensagem("acompanhamento.acompRelatorio.gravacao.liberarposicao")%>")){
					form.indLiberado.value = "S";
					if(document.form.envioEmailAtivo.value == 'S') {
						if(document.form.obrigatorio.value == 'S' || (document.form.obrigatorio.value == 'N' && confirm('<%=_msg.getMensagem("acompanhamento.dadosBasicos.alteracao.autorizaEnviaEmail")%>') == true )) {
							document.form.autorizarMail.value = 'S';
						} 
					}
				} else {
					form.indLiberado.value = "N";
				}
				form.action = "ctrlAcompRelatorio.jsp";
				form.submit();							
			}
	}
	
	function aoClicarExcluir(form){
		if(validarExclusao(form, "excluir")){
			if(!confirm("Confirma a exclusão?")){
				return(false);
			}

			form.hidAcao.value = "excluir";
			form.action = "ctrlAnexo.jsp";
			form.submit();
		}
	
	}
	
	function aoClicarExcluirPontoCritico(form){
		if(validarExclusao(form, "excluirPontoCritico")){
			if(!confirm("Confirma a exclusão?")){
				return(false);
			}

			form.hidAcao.value = "excluir";
			form.action = "ctrlPontoCritico.jsp";
			form.submit();
		}
	
	}
	
	
	
	<%
	} else{
		%>
	function aoClicarBtn1(form){
			<%
			_disabled = "disabled";
			_readOnly = "readOnly";		
			String msg = "";
			if(podeGravarRelatorio == AcompRelatorioDao.OPERACAO_NEGADA_USUARIO_SEM_PERMISSAO)
				msg = _msg.getMensagem("acompanhamento.acompRelatorio.gravacao.usuarioInvalido");
			if(podeGravarRelatorio == AcompRelatorioDao.OPERACAO_NEGADA_ACOMPANHAMENTO_REFERENCIA_LIBERADO)
				msg = _msg.getMensagem("acompanhamento.acompRelatorio.gravacao.acompanhamentoLiberado");
			if(podeGravarRelatorio == AcompRelatorioDao.OPERACAO_NEGADA_POSICAO_LIBERADA)
				msg = _msg.getMensagem("acompanhamento.acompRelatorio.gravacao.posicaoLiberada");
			if(podeGravarRelatorio == AcompRelatorioDao.OPERACAO_NEGADA_DATA_ULTRAPASSADA)
				msg = _msg.getMensagem("acompanhamento.acompRelatorio.gravacao.dataUltrapassada");			
			%>
			alert("<%=msg%>");
	}
	
	function aoClicarExcluir(form){
		<%
		String msgAnexo = "";
		msgAnexo = _msg.getMensagem("acompanhamento.acompRelatorio.anexo.exclusaoNaoPermitida");
		%>
		alert("<%=msgAnexo%>");	
	}
	
	function aoClicarExcluirPontoCritico(form){
		<%
		String msgPontoCritico = "";
		msgPontoCritico = _msg.getMensagem("acompanhamento.acompRelatorio.pontoCritico.exclusaoNaoPermitida");
		%>
		alert("<%=msgPontoCritico%>");	
	}
	
		<%
	}
	%>
	
	<%
	String labelBotao = "";
	 
	int podeLiberarRelatorio = acompRelatorioDao.podeLiberarRelatorio(usuario, funcao, acompReferenciaItem, acompanhamento);
	
	if(podeLiberarRelatorio==AcompRelatorioDao.OPERACAO_PERMITIDA && podeGravarRelatorio != AcompRelatorioDao.OPERACAO_NEGADA_DATA_ULTRAPASSADA){
		labelBotao = "Liberar Posi&ccedil;&atilde;o";
		%>	
		function aoClicarBtn3(form){
					
					if(document.form.envioEmailAtivo.value == 'S') {
						if(document.form.obrigatorio.value == 'S' || (document.form.obrigatorio.value == 'N' && confirm('<%=_msg.getMensagem("acompanhamento.dadosBasicos.alteracao.autorizaEnviaEmail")%>') == true )) {
							document.form.autorizarMail.value = 'S';
						} 
					}
		
					form.action = "ctrlAcompRelatorio.jsp";
					form.indLiberado.value = "S";
					if(valida(form)){
						form.submit();
					}	 
		}	
		<%
	} 
	
	int podeRecuperarRelatorio = acompRelatorioDao.podeRecuperarRelatorio(usuario, funcao, acompReferenciaItem, acompanhamento);
	
	if(podeRecuperarRelatorio==AcompRelatorioDao.OPERACAO_PERMITIDA && podeGravarRelatorio != AcompRelatorioDao.OPERACAO_NEGADA_DATA_ULTRAPASSADA){
		labelBotao = "Recuperar";
		%>	
		function aoClicarBtn3(form){
					if(document.form.envioEmailAtivo.value == 'S') {
						if(document.form.obrigatorio.value == 'S' || (document.form.obrigatorio.value == 'N' && confirm('<%=_msg.getMensagem("acompanhamento.dadosBasicos.alteracao.autorizaEnviaEmail")%>') == true )) {
							document.form.autorizarMail.value = 'S';
						} 
					}
					
					form.action = "ctrlAcompRelatorio.jsp";
					form.indLiberado.value = "N";
					form.submit();
		}	
		<%
	} 
	
	if("".equals(labelBotao) || podeGravarRelatorio == AcompRelatorioDao.OPERACAO_NEGADA_DATA_ULTRAPASSADA){
		if("S".equals(acompanhamento.getIndLiberadoArel())){
			labelBotao = "Recuperar";
			String msg = "";
			if(podeRecuperarRelatorio == AcompRelatorioDao.OPERACAO_NEGADA_USUARIO_SEM_PERMISSAO)
				msg = _msg.getMensagem("acompanhamento.acompRelatorio.recuperacao.usuarioInvalido");
			if(podeRecuperarRelatorio == AcompRelatorioDao.OPERACAO_NEGADA_ACOMPANHAMENTO_REFERENCIA_LIBERADO)
				msg = _msg.getMensagem("acompanhamento.acompRelatorio.recuperacao.acompanhamentoLiberado");
			if(podeRecuperarRelatorio == AcompRelatorioDao.OPERACAO_NEGADA_FUNCAO_SUPERIOR_LIBERADA)
				msg = _msg.getMensagem("acompanhamento.acompRelatorio.recuperacao.funcaoSuperiorLiberada");
		%>	
		function aoClicarBtn3(form){
			alert("<%=msg%>");
		}	
		<%
		}
		if(acompanhamento.getIndLiberadoArel() == null || "N".equals(acompanhamento.getIndLiberadoArel())){
			labelBotao = "Liberar Posi&ccedil;&atilde;o";	
			String msg = "";
			if(podeLiberarRelatorio == AcompRelatorioDao.OPERACAO_NEGADA_USUARIO_SEM_PERMISSAO)
				msg = _msg.getMensagem("acompanhamento.acompRelatorio.liberacao.usuarioInvalido");
			if(podeLiberarRelatorio == AcompRelatorioDao.OPERACAO_NEGADA_ACOMPANHAMENTO_REFERENCIA_LIBERADO)
				msg = _msg.getMensagem("acompanhamento.acompRelatorio.liberacao.acompanhamentoLiberado");
			if(podeLiberarRelatorio == AcompRelatorioDao.OPERACAO_NEGADA_FUNCAO_SUPERIOR_LIBERADA)
				msg = _msg.getMensagem("acompanhamento.acompRelatorio.liberacao.funcaoSuperiorLiberada");
		%>	 
		function aoClicarBtn3(form){
			alert("<%=msg%>");
		}	
		<%
		}
	     if(podeGravarRelatorio == AcompRelatorioDao.OPERACAO_NEGADA_DATA_ULTRAPASSADA){
	     	String msg=_msg.getMensagem("acompanhamento.acompRelatorio.gravacao.dataUltrapassada");
	     	%>
			function aoClicarBtn3(form){
				alert("<%=msg%>");
			}	
			<%
	     }
	} 
	
	%>
	
	function valida(form){
		if(!validaString(form.situacaoSit,"Situação",true)){
			return(false);
		}
		
		if(!validaRadioSelecionado(form, "cor")){
			alert("<%=_msg.getMensagem("acompanhamento.acompRelatorio.validacao.cor.obrigatorio")%>");
			return false;
		}
		
		if(!validaString(form.descricaoArel,"<%=funcao.getLabelPosicaoTpfa()%>",true)){
			return false;
		}
		
		return true;
	}	
	
	
	function mostrarEsconder() {
	
		
		if (document.getElementById('ultimasPosicoes').style.display=='none') {
		     document.getElementById('ultimasPosicoes').style.display='';
		} else {
		     document.getElementById('ultimasPosicoes').style.display='none';
		}
	}   
	
	function mostrarEsconderAnexos() {
	
		if (document.getElementById('anexos').style.display=='none') {
		     document.getElementById('anexos').style.display='';
		} else {
		     document.getElementById('anexos').style.display='none';
		}
	}
	
	function mostrarEsconderPontosCriticos() {
	
		if (document.getElementById('pontosCriticos').style.display=='none') {
		     document.getElementById('pontosCriticos').style.display='';
		} else {
		     document.getElementById('pontosCriticos').style.display='none';
		}
	}
	
	
	function aoClicarIncluir(form){
		form.inclusaoAnexo.value = "S";
		form.cod.value = "";
		document.form.action = "acompRelatorio.jsp";
		document.form.submit();
	}
	
	function aoClicarIncluirPontoCritico(form){
		form.inclusaoPontoCritico.value = "S";
		form.codPontoCritico.value = "";
		document.form.action = "acompRelatorio.jsp";
		document.form.submit();
	}
	
	function aoClicarAlterar(form){
		if(validarAnexo(form)){
			if (form.inclusaoAnexo.value == "S"){
				form.hidAcao.value = "incluir";
			} else {
				form.hidAcao.value = "alterar";
			}
			
			
			form.action = "ctrlAnexo.jsp";
			form.submit();		
		}
	}	
	
	function aoClicarAlterarPontoCritico(form){
		if(validarPontoCritico(form)){
			if (form.inclusaoPontoCritico.value == "S"){
				form.hidAcao.value = "incluir";
			} else {
				form.hidAcao.value = "alterar";
			}
			
			
			form.action = "ctrlPontoCritico.jsp";
			form.submit();		
		}
	}
	
	function aoClicarEditar(form, cod){
		form.inclusaoPontoCritico.value = "N";
		form.codPontoCritico.value = cod;
		document.form.action = "acompRelatorio.jsp";
		document.form.submit();
	}
	
	
	function validarAnexo(form){

		if (!validaString(form.descricaoIettup,'Descrição',true)){
			return(false);
		}
		
		if (!validaString(form.uploadTipoArquivoUta,'Tipo',true)){
			return(false);
		}
		return true;
	}
	
	function aoClicarVoltar(){
		if (document.getElementById('anexosUploadTag').style.display!='none') {
		     document.getElementById('anexosUploadTag').style.display='none';
		     document.getElementById('anexosListaTag').style.display ='';
		     document.getElementById('quantidade_anexos').style.display='';
		     form.cod.value = cod;
		}
	}
	
	function aoClicarVoltarPontoCritico(){
		if (document.getElementById('pontosCriticosTag').style.display!='none') {
		     document.getElementById('pontosCriticosTag').style.display='none';
		     document.getElementById('pontosCriticosListaTag').style.display ='';
		     document.getElementById('quantidade_pontos_criticos').style.display='';
		     form.codPontoCritico.value = '';
		}
	}
	
	function aoClicarConsultar(form, cod){
		form.cod.value = cod;
		form.inclusaoAnexo.value = "N";
		document.form.action = "acompRelatorio.jsp";
		document.form.submit();
	}
	
	function aoClicarConsultarPontoCritico(form, cod){
		form.codPontoCritico.value = cod;
		form.inclusaoPontoCritico.value = "N";
		document.form.action = "acompRelatorio.jsp";
		document.form.submit();
	} 
	
	/*
	 * Seleciona os dados da janela de pesquisa
	 * E como uma interface que deve ser implementada para receber dados de uma janela de pesquisa
	 * Sempre deve receber um código e uma descricao
	 */
	function getDadosPesquisa(codigo, descricao){
		document.form.codUsu.value = codigo;
		document.form.nomeUsu.value = descricao;
	}
	
	
	function validarPontoCritico(form){

		if(!validaData(form.dataIdentificacaoPtc,false,true,true)){
			if(form.dataIdentificacaoPtc.value == ""){
				alert("<%=_msg.getMensagem("itemEstrutura.pontoCritico.validacao.dataIdentificacaoPtc.obrigatorio")%>");
			}else{
				alert("<%=_msg.getMensagem("itemEstrutura.pontoCritico.validacao.dataIdentificacaoPtc.invalido")%>");
			}
			return(false);
		}	
		if (!validaString(form.descricaoPtc,'Descrição',true)){
			return(false);
		}
		if(form.indAmbitoInternoGovPtc[0].checked == false && form.indAmbitoInternoGovPtc[1].checked == false){
			alert("<%=_msg.getMensagem("itemEstrutura.pontoCritico.validacao.indAmbitoInternoGovPtc.obrigatorio")%>");
			return(false);
		}	
		if(!validaData(form.dataLimitePtc,false,true,true)){
			if(form.dataLimitePtc.value == ""){
				alert("<%=_msg.getMensagem("itemEstrutura.pontoCritico.validacao.dataLimitePtc.obrigatorio")%>");
			}else{
				alert("<%=_msg.getMensagem("itemEstrutura.pontoCritico.validacao.dataLimitePtc.invalido")%>");
			}
			return(false);
		}	
		if(form.dataSolucaoPtc.value != ""){
			if(!validaData(form.dataSolucaoPtc,false,true,true)){
				alert("<%=_msg.getMensagem("itemEstrutura.pontoCritico.validacao.dataSolucaoPtc.obrigatorio")%>");		
				return(false);
			}
		}
		if(form.indAtivoPtc[0].checked == false && form.indAtivoPtc[1].checked == false){
			alert("<%=_msg.getMensagem("itemEstrutura.pontoCritico.validacao.indAtivoPtc.obrigatorio")%>");
			return(false);
		}	
		
		return true;
	}

	</script>
	
	
	</head>
	<%@ include file="/cabecalho.jsp" %>
	<%@ include file="/divmenu.jsp"%> 
	
	<body>
	
		<input type="hidden" name="mostrarUltimasPosicoes" value="hidden">
		
	<div id="popup_flutuante">
	</div>
	
	<div id="conteudo"> 
	
	<!-- TÍTULO -->
	<%@ include file="/titulo_tela.jsp"%>
	
	<%@ include file="botoesAvancaRetrocede.jsp" %>
	
	 <br>
	 	<%
	 	String selectedFuncao = funcaoStr;
	 	%>
		<util:barraLinksRegAcomp 
			acompReferenciaItem="<%=acompReferenciaItem%>"  
			selectedFuncao="<%=selectedFuncao%>"
			usuario="<%=usuario%>"
			primeiroAcomp="<%=primeiroAcomp%>"
			request="<%=request%>"
			gruposUsuario="<%=seguranca.getGruposAcesso() %>"
		/> 
	<br>

	
	<table width="100%" border="0" cellpadding="0" cellspacing="0">
			<tr><td class="espacador"><img src="<%=_pathEcar%>/images/pixel.gif"></td></tr>
			<tr class="linha_titulo" align="left">
				<td>
					Refer&ecirc;ncia: <%=acompReferenciaItem.getAcompReferenciaAref().getNomeAref() %>&nbsp;&nbsp;&nbsp;(<%=acompReferenciaItem.getAcompReferenciaAref().getTipoAcompanhamentoTa().getDescricaoTa() %> )
				</td> 
			</tr> 
			<tr class="linha_titulo" align="left">
				<td>
					M&ecirc;s/Ano: <%=acompReferenciaItem.getAcompReferenciaAref().getMesAref()%>/<%=acompReferenciaItem.getAcompReferenciaAref().getAnoAref()%>
				</td> 
			</tr>
			<tr><td class="espacador"><img src="<%=_pathEcar%>/images/pixel.gif"></td></tr>
	</table>
	
	<util:arvoreEstruturaElabAcomp
		itemEstrutura="<%=acompReferenciaItem.getItemEstruturaIett()%>" 
		codigoAcomp="<%=codAcomp%>"
		contextPath="<%=_pathEcar%>"
		listaParaArvore="<%=(java.util.List)session.getAttribute("listaParaArvore")%>"  
		nivelPrimeiroIettClicado="<%=(String)session.getAttribute("nivelPrimeiroIettClicado")%>"
		abaAtual="<%=ecar.taglib.util.ArvoreEstruturaElabAcompTag.ABA_ACOMPANHAMENTOS%>"
		primeiroAcomp="<%=primeiroAcomp%>"
	/>
	
	<form  name="form" method="post" enctype="multipart/form-data">
	
	
	
	
		<table border="0" width="100%">
	
		<tr>
			<td valign="bottom" class="texto" align="left">
				<!-- COMBO DE PERÍODOS DE EXIBIÇÃO -->
				<b>Ciclo de Exibição:</b>
				<select name="periodoSel" onchange="reload()" >
					<option value=""> --SELECIONE-- </option>
					<%
					int intPeriodo = Integer.parseInt(periodoSel);
					for (int i=1; i<=12; i++){ 
					%>
					<option value="<%=i %>" <%= (intPeriodo ==i ?"selected":" ") %>> <%=i %> <%=i > 1 ? "Ciclos" : "Ciclo" %></option>
					<%} %>
				</select>
			</td>
		</tr>
	</table>
	
	<table style="width: 100%; border: 0; text-align: center; ">

		<!-- Linha de contendo os periodos exibidos -->
		<tr class="linha_subtitulo">
		<td>Ciclo </td>
		<% /* Imprime o nome dos ciclos */
		Collection periodosConsiderados = new ArrayList();
		Long codArefReferencia = acompReferenciaItem.getAcompReferenciaAref().getCodAref();
		
		if(codArefReferencia.intValue() > 0) {
			periodosConsiderados =  acompRefDao.getPeriodosAnteriores(codArefReferencia, intPeriodo, acompReferenciaItem.getAcompReferenciaAref().getTipoAcompanhamentoTa().getCodTa());
		}
		
		Iterator itPeriodo = periodosConsiderados.iterator();
		double tamanhoColuna = 38 / periodosConsiderados.size() ;
		while(itPeriodo.hasNext()){
			AcompReferenciaAref acompAref = (AcompReferenciaAref) itPeriodo.next();
			String estilo = "";
			if(!itPeriodo.hasNext()){
				estilo = "style='background-color: #CED7E7;'";
			} 
	%>
	<td align="center" <%=estilo%> width="<%=tamanhoColuna%>%"><%=acompAref.getNomeAref()%> </td>
	<%							
		}
	%>					
		</tr>
		<tr ><td class="espacador" colspan="<%= periodosConsiderados.size()+1 %>"><img src="<%=_pathEcar%>/images/pixel.gif" alt=""></td></tr>
		<%
		 
		 List listAcompItemReferenciasOrderByReferecias   = acompReferenciaItemDao.getAcompItemReferenciasOrderByReferecias(acompReferenciaItem, periodosConsiderados );

		 //Valores da tabela
		 Map mapLabelPosicaoRelatorio = acompReferenciaItemDao.agrupaRelatorioTpfa_e_Aref (listAcompItemReferenciasOrderByReferecias,periodosConsiderados );

		 //coluna da tabela
		 Set funcoesAcomp = acompReferenciaItem.getAcompRelatorioArels();
		 
		 String corLinha ="";
		 int numLinhas = 1; 
		 int cont =0;
		 		
		//List lFuncoesAcomp =   new AcompReferenciaItemDao(null).getTipoFuncAcompByAcompRefrenciaItem(acompReferenciaItem);
        Iterator itFuncoesAcomp =null;
		if(funcoesAcomp !=null )
			itFuncoesAcomp = funcoesAcomp.iterator();
			while(itFuncoesAcomp.hasNext())	{ 
				
				AcompRelatorioArel arelAux = (AcompRelatorioArel)  itFuncoesAcomp.next();
				TipoFuncAcompTpfa tpfa = arelAux.getTipoFuncAcompTpfa(); //(TipoFuncAcompTpfa)itFuncoesAcomp.next();
				
				if(tpfa.getIndEmitePosicaoTpfa().equalsIgnoreCase("S")){  //  tpfa.getIndEmitePosicaoTpfa().equalsIgnoreCase("o") /*|| ( tpfa.getIndEmitePosicaoTpfa().equalsIgnoreCase("p") && tpfa.getAcompRelatorioArels()!=null  )*/ )  { //p-opcional o-obrigatorio
				
				if (( (numLinhas++) % 2) == 0  ){
					corLinha="list_elab_acomp_cor_sim";
				} else{
					corLinha="";
				}
				
		%>
		<tr class="<%=corLinha  %>" style="border: 0" onmouseover="javascript:destacaLinha(this,'over','')" onmouseout="javascript: destacaLinha(this,'out','<%=corLinha%>')" onClick="javascript: destacaLinha(this,'click','<%=corLinha%>')"  >
		
			<td class="linha_subtitulo" style="text-align: left;" > <%= tpfa.getLabelPosicaoTpfa() %> </td>
			
			<%
			
			Map mapRefRelatorio = (Map )mapLabelPosicaoRelatorio.get(tpfa );

			for(itPeriodo = periodosConsiderados.iterator(); itPeriodo.hasNext(); ){
				AcompReferenciaAref acompAref = (AcompReferenciaAref) itPeriodo.next();
				
				if (mapRefRelatorio.containsKey(acompAref ) ){
					AcompRelatorioArel tabArel = (AcompRelatorioArel) mapRefRelatorio.get(acompAref);
					cont++;
		
					String titulo = "";
					String titleImg = "Sem parecer";
					String srcImg= _pathEcar + "/images/sBranco3.png";
					boolean parecrLiberado = false;
					
					 if (tabArel.getIndLiberadoArel()!=null && tabArel.getIndLiberadoArel().equals("S")) {
					 	srcImg = Util.getURLImagemAcompanhamento(tabArel.getCor(), request, tabArel.getTipoFuncAcompTpfa());
					 	titleImg = tabArel.getCor().getSignificadoCor();
				 	}
				 	
				 	StringBuffer strBfDica = new StringBuffer();
				 	if ( tabArel.getIndLiberadoArel()!=null && tabArel.getIndLiberadoArel().equals(Dominios.SIM )) {
				 		
					 	strBfDica.append("<b>Mês/Ano:</b> ").append(tabArel.getAcompReferenciaItemAri().getAcompReferenciaAref().getMesAref()).append(" / ").append(tabArel.getAcompReferenciaItemAri().getAcompReferenciaAref().getAnoAref()).append("<br />\n");
	              		strBfDica.append("<b>Situação </b>: ").append( ( (tabArel.getSituacaoSit() !=null && tabArel.getSituacaoSit().getDescricaoSit()!=null)? tabArel.getSituacaoSit().getDescricaoSit():"") ).append("<br>\n");
	                  	strBfDica.append("<b>Posição</b>: ").append( (tabArel. getDescricaoArel()!=null?tabArel.getDescricaoArel() :"") ).append("<br> \n");
	                  	strBfDica.append("<b>Observação</b>: ").append( (tabArel.getComplementoArel() !=null?tabArel.getComplementoArel() :"") ).append("<br> \n");
	                    strBfDica.append("<b>Resp</b>: ").append( ( (tabArel.getUsuarioUsu()!=null && tabArel.getUsuarioUsu().getNomeUsuSent()!=null ) ?tabArel.getUsuarioUsu().getNomeUsuSent():"") ).append("<br> \n");
	                    strBfDica.append("<b>Tel</b>: " ).append( ((tabArel.getUsuarioUsu()!=null && tabArel.getUsuarioUsu().getComercTelefoneUsu()!=null) ?tabArel.getUsuarioUsu().getComercTelefoneUsu():"")).append(" <br> \n");
	                    strBfDica.append("<b>e-mail</b>: ").append(( (tabArel.getUsuarioUsu()!=null && tabArel.getUsuarioUsu().getEmail1UsuSent()!=null ) ?tabArel.getUsuarioUsu().getEmail1UsuSent():"")).append("<br> <br>\n \n");
	                } 
                    
                    
			%>
		<td nowrap >
            <label class="dica" onmouseover="javascript:viewFieldTip(this, '<%=tabArel.getTipoFuncAcompTpfa().getLabelPosicaoTpfa()+"_"+cont %>');"  onmouseout="javascript:noViewFieldTip('<%=tabArel.getTipoFuncAcompTpfa().getLabelPosicaoTpfa()+"_"+cont %>');" >
              <img title=<%=titleImg %> src="<%=srcImg %>" align="absmiddle" border="0" onclick="javascript:viewFieldTipPopUp('<%= URLEncoder.encode(strBfDica.toString() , Dominios.ENCODING_DEFAULT) %>')" />
              <span id="<%=tabArel.getTipoFuncAcompTpfa().getLabelPosicaoTpfa()+"_"+cont %>" style="z-index: 5"  ><br>
              		 <%=strBfDica.toString() %>
              </span>
            </label>
          </td>
				<%  
				} else{
				%>
			<td>&nbsp;</td>
				<%
				}
			}
			
			 %>
		</tr>	
		  <%
			  	}//fim if 1
			} //fim while %>

		<tr><td class="espacador" colspan="12"><img src="../../images/pixel.gif"></td></tr>
		<tr><td colspan="5">&nbsp; </td></tr> 
	
	</table>
	
	<%
	String selectedFuncaoParecer = funcaoParecer;
	 %>
	<util:barraLinksRegAcompParecer 
			acompReferenciaItem="<%=acompReferenciaItem%>"  
			selectedFuncao="<%=selectedFuncaoParecer%>"
			usuario="<%=usuario%>"
			primeiroAcomp="<%=primeiroAcomp%>"
			request="<%=request%>"
		/>  
		<br>
		
	
	
		<input type="hidden" name="autorizarMail" value="N">
		<input type="hidden" name="vemDaPgAcompRelatorio" value="N">
		<input type="hidden" name="codAcomp" value="<%=codAcomp%>">
		<input type="hidden" name="codArel" value="<%=Pagina.trocaNull(acompanhamento.getCodArel())%>">
		<input type="hidden" name="codAri" value="<%=acompReferenciaItem.getCodAri()%>">
		<input type="hidden" name="cod" value="<%=cod%>">
		<input type="hidden" name="codPontoCritico" value="<%=codPontoCritico%>">
		<input type="hidden" name="inclusaoAnexo" value="<%=inclusaoAnexo%>">
		<input type="hidden" name="inclusaoPontoCritico" value="<%=inclusaoPontoCritico%>">
		<input type="hidden" name="codTpfa" value="<%=codTpfa%>">
		<input type="hidden" name="codUsuario" value="<%=usuario.getCodUsu()%>">  
		<input type="hidden" name="funcao" value="<%=selectedFuncao%>">	
		<input type="hidden" name="funcaoParecer" value="<%=selectedFuncaoParecer%>">
		<input type="hidden" name="indLiberado" value="<%=Pagina.trocaNull(acompanhamento.getIndLiberadoArel())%>">	
		<input type="hidden" name="primeiroAcomp" value="<%=primeiroAcomp%>">
		<input type="hidden" name="obrigatorio" value="<%=configMailCfgm.getIndEnvioObrigatorio()%>">
		<input type="hidden" name="envioEmailAtivo" value="<%=configMailCfgm.getAtivoCfgm()%>">
		<input type="hidden" name="hidAcao" value="">
		<input type="hidden" name="codIett" value="<%=paramCodIett%>">
		<input type="hidden" name="descricaoIettuc" value="<%=descricaoIettuc%>">
		<input type="hidden" name="nomeIettuc" value="<%=descricaoIettuc%>">
		<input type="hidden" name="codIettuc" value="<%=categoriaAnexo == null? "": String.valueOf(categoriaAnexo.getCodIettuc())%>">
		<input type="hidden" name="uploadTipoCategoriaUtc" value="<%=uploadTipoCategoriaUtc.getCodUtc()%>">
		
		
		<util:barrabotoes btn1="Gravar" btn2="Cancelar" btn3="<%=labelBotao%>"/>
		<table name="form" class="form" width="100%">
				<tr>
					<td>&nbsp;</td>
				 	<td><b style="color:red;"><%=msgData%></b></td>
				</tr>
				<tr>
				 <td class="label">* Situa&ccedil;&atilde;o</td>
				 <td>
				 <%
				 String selected = "";
				 List situacoesItem = situacaoDao.getSituacaoByTipoFuncaoAcompanhamentoEstrutura(funcao, acompReferenciaItem.getItemEstruturaIett().getEstruturaEtt());
	
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
				 %>
				<combo:ComboTag 
						nome="situacaoSit"
						objeto="ecar.pojo.SituacaoSit"
						label="descricaoSit"
						value="codSit"
						order="descricaoSit"
						selected="<%=selected%>"
						scripts="<%=_disabled%>"
						colecao="<%=situacoesItem%>" 
				/>			 
				</td>
			</tr>  
				<tr>
				 <td class="label" valign="top">* Cor</td>
				 <td>
				<%
				
					UsuarioUsu usuarioImagem = null;  
	        		String hashNomeArquivo = null;
				
					String imagePath = "";
					Iterator it = corDao.listar(Cor.class, null).iterator();
					while(it.hasNext()){
						Cor cor = (Cor) it.next();
						String checked= "";
						if(acompanhamento.getCor() != null){
							if(acompanhamento.getCor().equals(cor)){
								checked = "checked";
							}
						}
						
						// Alterado por Rogério (05/03/2007)
						// Referencia a alterações no cadastro de Cor. 
						// Mantis #7442
						if(cor.getIndPosicoesGeraisCor().equals("S")){ %>
							<input type="radio" class="form_check_radio" name="cor" <%=_disabled%> <%=checked%> value="<%=cor.getCodCor()%>"> <%
							imagePath = corDao.getImagemPersonalizada(cor, funcao, "D");
							if( imagePath != null ) { 
							
								usuarioImagem = ((ecar.login.SegurancaECAR)request.getSession().getAttribute("seguranca")).getUsuario(); 
		    					hashNomeArquivo = Util.calcularHashNomeArquivo(pathRaiz, imagePath);
		    					Util.adicionarMapArquivosAtuaisUsuarios(usuarioImagem, hashNomeArquivo, pathRaiz, imagePath);
								
							%>
<!--						    <img border="0" src="<%=request.getContextPath()%>/DownloadFile?tipo=open&RemoteFile=<%=hashNomeArquivo%>" style="width: 23px; height: 23px;" title="<%=cor.getSignificadoCor()%>" align="absmiddle"> -->
							    <img border="0" src="<%=request.getContextPath()%>/DownloadFile?tipo=open&RemoteFile=<%=hashNomeArquivo%>" title="<%=cor.getSignificadoCor()%>" align="absmiddle"> <%
							} else {
								if( cor.getCodCor() != null ) { %>
								<img border="0" src="<%=_pathEcar%>/images/<%=corDao.getImagemSinal(cor,funcao)%>" title="<%=cor.getSignificadoCor()%>"  align="absmiddle"> <% 
								}
							} %> <%=cor.getSignificadoCor()%> <br> <%
						}
					}
				%>			 
				</td>
			</tr>   
			<tr>
				<td class="label" valign="top">* <%=funcao.getLabelPosicaoTpfa()%></td>
				<td>
					<textarea name="descricaoArel" <%=_readOnly%> cols="96" rows="4" onkeyup="return validaTamanhoLimite(this, 5000);" onkeydown="return validaTamanhoLimite(this, 5000);" onblur="return validaTamanhoLimite(this, 5000);"><%=Pagina.trocaNull((acompanhamento.getDescricaoArel()))%></textarea>
				</td>
			</tr>
			
			<%
			if (ocultarObservacoesParecer == null || ocultarObservacoesParecer.equals("N")){
			%>
			
				<tr>
					<td class="label" valign="top">Observações</td>
					<td>
						<textarea name="complementoArel" <%=_readOnly%> cols="96" rows="4" onkeyup="return validaTamanhoLimite(this, 2000);" onkeydown="return validaTamanhoLimite(this, 2000);" onblur="return validaTamanhoLimite(this, 2000);"><%=Pagina.trocaNull((acompanhamento.getComplementoArel()))%></textarea>
					</td>
				</tr>	
			
			<%
			}
			%>
			
			<tr>
				 <td colspan="2">
				 	<table align="center">
				 		<tr>
							 <td class="label">Inclus&atilde;o:</td>
							 <td>
							 <%=Pagina.trocaNullData(acompanhamento.getDataInclusaoArel())%>
							 <%
							 if(acompanhamento.getUsuarioUsu() != null) {
								 //Imagem para Indicador de Usuario INAtivo
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
							 <%=Pagina.trocaNullData(acompanhamento.getDataUltManutArel())%>
							 <%
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
			<%
			List arels = acompReferenciaItemDao.getUltimosAcompanhamentosItem(acompReferenciaItem, funcao, Integer.valueOf(2));
	
			if (arels != null && arels.size() > 0){
			%>
			<tr>
				<td colspan="2"><a href="#" onclick="mostrarEsconder();">Mostrar &Uacute;ltimas Posi&ccedil;&otilde;es Emitidas</a></td>			
			</tr>
			<tr>
			<td colspan="2" >
					<table id="ultimasPosicoes" style="display:none" >
						<%
					String periodo = "";
					String mesAno = "";
					String situacao = "";
					String cor = "";
					String descricao = "";
					String observacao = "";
	
					Iterator itArels = arels.iterator();
					while(itArels.hasNext()){					
						AcompRelatorioArel arel = (AcompRelatorioArel) itArels.next();
						//if(Pagina.getParamStr(request, "codTpfa").equals(arel.getTipoFuncAcompTpfa().getCodTpfa().toString())){
							
							if(arel.getAcompReferenciaItemAri() != null &&
								arel.getAcompReferenciaItemAri().getAcompReferenciaAref() != null){
								periodo = arel.getAcompReferenciaItemAri().getAcompReferenciaAref().getNomeAref();
								mesAno = arel.getAcompReferenciaItemAri().getAcompReferenciaAref().getMesAref() + "/" + arel.getAcompReferenciaItemAri().getAcompReferenciaAref().getAnoAref();
							}
														
							if(arel.getSituacaoSit() != null){
								situacao = arel.getSituacaoSit().getDescricaoSit();
							}
							else{
								situacao = "N/I";
							}
							
							if(arel.getCor() != null){
								cor = "<img src=\"" + "" + _pathEcar + "/images/" + corDao.getImagemSinal(arel.getCor(), arel.getTipoFuncAcompTpfa()) + 
									  "\" align=\"top\">";
							}
							else {
								cor = "N/I";
							}
							
							if(arel.getDescricaoArel() != null && !"".equals(arel.getDescricaoArel().trim())){
								descricao = Util.normalizaQuebraDeLinhaHTML(arel.getDescricaoArel());
							}
							else {
								descricao = "N/I";
							}
							
							if(arel.getComplementoArel() != null && !"".equals(arel.getComplementoArel().trim())){
								observacao = Util.normalizaQuebraDeLinhaHTML(arel.getComplementoArel());
							}
							else {
								observacao = "N/I";
							}
							%>
								
								<tr>
									<td class="label">Per&iacute;odo</td>
									<td><%=periodo%></td>
								</tr>
								<tr>
									<td class="label">Mês/Ano</td>
									<td><%=mesAno%></td>
								</tr>
								<tr>
									 <td class="label">Situa&ccedil;&atilde;o:</td>
									 <td><%=situacao%></td>
								</tr> 	
								<tr>
									 <td class="label">Cor:</td>
									 <td><%=cor%></td>
								</tr> 
								<tr>
									 <td class="label">Descri&ccedil;&atilde;o:</td>
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
									 <td class="label" colspan="2">&nbsp;</td>
								</tr> 
							<%
						//}
					}
					%>
					</table>
				</td>
			</tr>
			<%
			}
			%>
			<tr>
				<td colspan="2"><a href="#" onclick="abrirPopUpTodasPosicoes(form);">Mostrar Todas Posi&ccedil;&otilde;es Emitidas</a></td>			
			</tr>
		</table>
		<util:barrabotoes btn1="Gravar" btn2="Cancelar" btn3="<%=labelBotao%>"/>
		
		<br>
		
		<%
		String displayQuantidadeAnexos = "";
		String displayAnexos = "";
		String displayAnexosListaTag = "";
		String displayAnexosUploadTag = "";
		
		
		if (((cod != null && !cod.equals("")) || (inclusaoAnexo != null && (inclusaoAnexo.equals("S")||inclusaoAnexo.equals("N"))))) {
			displayAnexosUploadTag = "";
			displayQuantidadeAnexos = "none";
			displayAnexos = "";
			displayAnexosListaTag = "none";
		} else {
			displayAnexosUploadTag = "none";
			displayQuantidadeAnexos = "";
			displayAnexos = "none";
			displayAnexosListaTag = "";
		}

		boolean exibirAnexos = false;
		boolean exibirPontosCriticos = false;

		Aba aba = new Aba();
		aba.setNomeAba("GALERIA");
		
		AbaDao abaDao = new AbaDao(request);
		aba = (Aba)(abaDao.pesquisar(aba, new String[]{"codAba", "asc"}).get(0));
		if (aba != null && aba.getExibePosicaoAba().equals("S") && estruturaFuncaoDao.existeFuncaoAnexo(itemEstrutura.getEstruturaEtt())) {
			exibirAnexos = true;
		}
		
		aba = null;
		aba = new Aba();
		aba.setNomeAba("PONTOS_CRITICOS");
		
		aba = (Aba)(abaDao.pesquisar(aba, new String[]{"codAba", "asc"}).get(0));
		if (aba != null && aba.getExibePosicaoAba().equals("S") && estruturaFuncaoDao.existeFuncaoPontosCriticos(itemEstrutura.getEstruturaEtt())) {
			exibirPontosCriticos = true;
		}

		if (exibirAnexos){
		%>
		
		
    		
    	<span id="quantidade_anexos" style="display:<%=displayQuantidadeAnexos%>">	
	    	<table class="form">
		    	<tr class="label" align="left">
		    		<td align="left">
		    			<a href="javascript:mostrarEsconderAnexos();"><%=estruturaFuncaoDao.getLabelFuncaoAnexo(itemEstrutura.getEstruturaEtt()) + ": " + anexos.size()%></a>
		    		</td>
		    	</tr>
		    </table>
		</span>
		<span id="anexos" style="display:<%=displayAnexos%>">
			<!-- ############### Tag Lista de Anexos ################### -->
			<span id="anexosListaTag" style="display:<%=displayAnexosListaTag%>">
			<util:anexosListaTag 
				estruturaFuncao="<%=estruturaFuncao%>"
				categoriaAnexo="<%=categoriaAnexo%>"
				permissaoAlterar="<%=permissaoAlterar%>"
				anexo="<%=anexo%>" 
				caminhoRaizUpload="<%=caminhoRaizUpload%>"
				pathEcar="<%=_pathEcar%>"
				acompRelatorioArel="<%=acompanhamento%>"
				disabled="<%=_disabled%>"
				request="<%=request%>"
			/>
			</span>
			<!-- ############### Tag Lista de Anexos  ################### -->
			
			<!-- ############### Tag Upload de Anexos ################### -->
			<span id="anexosUploadTag" style="display:<%=displayAnexosUploadTag%>">
				<util:barrabotoes alterar="Gravar" voltar="Cancelar" exibirAlterar="<%=_disabled.equals("")?new Boolean(true):new Boolean(false) %>"/>
				<util:anexosUploadTag 
					anexo="<%=anexo%>" 
					disabled=""
					readOnly=""
					nomeComboTag="uploadTipoArquivoUta"
					objetoComboTag="ecar.pojo.UploadTipoArquivoUta"
					labelComboTag="descricaoUta"
					valueComboTag="codUta"
					orderComboTag="descricaoUta"
					scriptsComboTag=""
					colecao="<%=categorias%>"
				/>
				<util:barrabotoes alterar="Gravar" voltar="Cancelar" exibirAlterar="<%=_disabled.equals("")?new Boolean(true):new Boolean(false) %>"/>
			</span>
		</span>
		<%
		}
		if (exibirPontosCriticos) { 
			//ConfiguracaoCfg configuracao = new ConfiguracaoDao(request).getConfiguracao();
			SisGrupoAtributoSga grupoAssunto = configuracao.getSisGrupoAtributoSgaTipoPontoCritico(); 
			PontoCriticoDao pontoCriticoDao = new PontoCriticoDao(request);
			PontoCriticoPtc pontoCritico = null;
			 
			List pontosCriticos = null;
			pontoCritico = new PontoCriticoPtc();
			pontoCritico.setAcompRelatorioArel(acompanhamento);
			
			pontosCriticos = pontoCriticoDao.pesquisar(pontoCritico, new String[]{"acompRelatorioArel", "asc"});
			 
			pontoCritico = null;
			 
			if (codPontoCritico != null && !codPontoCritico.equals("")){
				pontoCritico = (PontoCriticoPtc) new PontoCriticoDao(request).buscar(PontoCriticoPtc.class, Long.valueOf(codPontoCritico));
			} else {
				pontoCritico = new PontoCriticoPtc();	   
			}
			
			String _disabledCampo = "";
			String _readonlyCampo = "";
			 
			String displayQuantidadePontosCriticos = "";
			String displayPontosCriticos = "";
			String displayPontosCriticosListaTag = "";
			String displayPontosCriticosTag = "";
			
			
			if (((codPontoCritico != null && !codPontoCritico.equals("")) || (inclusaoPontoCritico != null && (inclusaoPontoCritico.equals("S")||inclusaoPontoCritico.equals("N"))))) {
				displayPontosCriticosTag = "";
				displayQuantidadePontosCriticos = "none";
				displayPontosCriticos = "";
				displayPontosCriticosListaTag = "none";
			} else {
				displayPontosCriticosTag = "none";
				displayQuantidadePontosCriticos = "";
				displayPontosCriticos = "none";
				displayPontosCriticosListaTag = "";
			}
			   
			   
			   
			%>
			<br>
			<span id="quantidade_pontos_criticos" style="display:<%=displayQuantidadePontosCriticos%>">	
		    	<table class="form">
			    	<tr class="label" align="left">
			    		<td align="left">
			    			<a href="javascript:mostrarEsconderPontosCriticos();"><%=estruturaFuncaoDao.getLabelFuncaoPontosCriticos(itemEstrutura.getEstruturaEtt()) + ": " +pontosCriticos.size()%></a>
			    		</td>
			    	</tr>
			    </table>
			</span>
			<span id="pontosCriticos" style="display:<%=displayPontosCriticos%>">
				
				
				
				<span id="pontosCriticosListaTag" style="display:<%=displayPontosCriticosListaTag%>">
					<table width="100%" border="0" cellpadding="0" cellspacing="0">
		    			<tr> <td class="espacador"><img src="<%=_pathEcar%>/images/pixel.gif"></td> </tr>
		    			<tr class="linha_titulo" align="right">
		    				<td>
		    		
								<input <%=_disabled%> name="btnAdicionarCritico" type="button" value="Adicionar" class="botao" onclick="aoClicarIncluirPontoCritico(form);">
								<input <%=_disabled%> name="btnExcluirPontoCritico" type="button" value="Excluir" class="botao" onclick="aoClicarExcluirPontoCritico(form);">    		
		    		
		    				</td>
		    			</tr>
		    		</table>
					<util:pontosCriticosListaTag 
						grupoAssunto="<%=grupoAssunto%>" 
						pontoCriticoDao="<%=pontoCriticoDao%>" 
						itemEstrutura="<%=itemEstrutura%>"
						acompRelatorioArel="<%=acompanhamento%>"
						apontamentos="<%=new Boolean(false)%>"
						contextPath = "<%=_pathEcar%>"
					/>
				</span>
				
				<span id="pontosCriticosTag" style="display:<%=displayPontosCriticosTag%>">
					<table class="barrabotoes" cellpadding="0" cellspacing="0">
					<tr>
						<td class="label">&nbsp;</td>
						<td>
							<input <%=_disabled%> name="btnAlterarPontoCritico" type="button" value="Gravar" class="botao" onclick="aoClicarAlterarPontoCritico(form);">
							<input name="btnVoltarPontoCritico" type="button" value="Cancelar" class="botao" onclick="aoClicarVoltarPontoCritico(form);">
						</td>
					</tr>
					</table>
					<util:pontosCriticosTag
						pontoCritico="<%=pontoCritico%>"
						disabled="<%=_disabled%>"
						disabledCampo="<%=_disabledCampo%>"
						readOnly="<%=_readOnly%>"
						readOnlyCampo="<%=_readonlyCampo%>"
						request="<%=request%>"
						nomeComboTag="codSgaPontoCritico"
						objetoComboTag="ecar.pojo.SisAtributoSatb"
						labelComboTag="descricaoSatb"
						valueComboTag="codSatb"
						orderComboTag="descricaoSatb"
						filtersComboTag="indAtivoSga=S"
					/>
					<table class="barrabotoes" cellpadding="0" cellspacing="0">
					<tr>
						<td class="label">&nbsp;</td>
						<td>
							<input <%=_disabled%> name="btnAlterarPontoCritico" type="button" value="Gravar" class="botao" onclick="aoClicarAlterarPontoCritico(form);">
							<input name="btnVoltarPontoCritico" type="button" value="Cancelar" class="botao" onclick="aoClicarVoltarPontoCritico(form);">
						</td>
					</tr>
					</table>
				</span>
			</span>
		<%} %>
		
		
		

</form>
<%
	}
} catch (ECARException e){
	org.apache.log4j.Logger.getLogger(this.getClass()).error(e);
	Mensagem.alert(_jspx_page_context, e.getMessageKey());
} catch (Exception e){
%>
	<%@ include file="/excecao.jsp"%>
<%
}
%>
<script language=javascript>
	if('<%=msgConfirm%>' != 'null' && '<%=msgConfirm%>' != ''){
  		if (confirm('<%=msgConfirm%>')){
			form.action = "datasLimites.jsp";
			form.vemDaPgAcompRelatorio.value = "S";
			form.submit();	
  		} 
	}
</script>
<% /* Controla o estado do Menu (aberto ou fechado) */%>
<%@ include file="/include/estadoMenu.jsp"%>
<%@ include file="/include/mensagemAlert.jsp" %>
</html>
