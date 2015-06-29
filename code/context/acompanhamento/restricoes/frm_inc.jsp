<jsp:directive.page import="ecar.dao.AcompRelatorioDao"/>
<jsp:directive.page import="ecar.dao.FuncaoDao"/>
<jsp:directive.page import="ecar.dao.SisGrupoAtributoDao"/>
<jsp:directive.page import="ecar.dao.ConfigMailCfgmDAO"/>
<jsp:directive.page import="ecar.pojo.ConfigMailCfgm"/>
<jsp:directive.page import="ecar.pojo.AcompReferenciaAref"/>
<%@ include file="/login/validaAcesso.jsp"%>
<%@ include file="/frm_global.jsp"%>

<%@ page import="ecar.dao.AcompReferenciaItemDao"%>
<%@ page import="ecar.dao.EstruturaDao"%>
<%@ page import="ecar.dao.EstruturaFuncaoDao" %>
<%@ page import="ecar.dao.ItemEstruturaDao"%>
<%@ page import="ecar.pojo.AcompReferenciaItemAri"%>
<%@ page import="ecar.pojo.ItemEstruturaIett" %>
<%@ page import="ecar.pojo.ItemEstrutAcaoIetta"%>
<%@ page import="ecar.pojo.EstruturaFuncaoEttf" %>
<%@ page import="ecar.pojo.AcompRelatorioArel"%>
<%@ page import="ecar.pojo.UsuarioUsu"%>
<%@ page import="ecar.pojo.Aba"%>
<%@ page import="ecar.dao.AbaDao"%>
<%@ page import="comum.util.ConstantesECAR"%>

<%@ page import="ecar.util.Dominios"%>

<%@ page import="comum.util.Data"%>

<%@ page import="java.util.List"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="java.util.Iterator"%>
<%@ page import="ecar.pojo.StatusRelatorioSrl"%>

<%@ taglib uri="/WEB-INF/ecar-util.tld" prefix="util"%>
<%@ taglib uri="/WEB-INF/ecar-combo.tld" prefix="combo"%>
<%



try{ 
	
	
	/* carrega o usuário da session */
	UsuarioUsu usuario = ((ecar.login.SegurancaECAR)session.getAttribute("seguranca")).getUsuario();
	
	ConfigMailCfgmDAO configMailCfgmDAO = new ConfigMailCfgmDAO();
	ConfigMailCfgm configMailCfgm = (ConfigMailCfgm) configMailCfgmDAO.buscar(ConfigMailCfgm.class, Dominios.CFG_MAIL_MANUTENCAO_PONTO_CRITICO);
	
	// recupera os parametros do request
	String strCodAri = Pagina.getParamStr(request, "codAri");
	String codTipoAcompanhamento = Pagina.getParamStr(request, "codTipoAcompanhamento");
	String itemDoNivelClicado = Pagina.getParamStr(request, "itemDoNivelClicado");
	String mesReferencia = Pagina.getParamStr(request, "referencia_hidden");
	
	AcompReferenciaItemDao ariDao = new AcompReferenciaItemDao(request);
	AcompReferenciaItemAri ari = (AcompReferenciaItemAri) ariDao.buscar(AcompReferenciaItemAri.class, Long.valueOf(strCodAri));
	
	ValidaPermissao validaPermissao = new ValidaPermissao();
	
	//Pega a aba
	AbaDao abaDao = new AbaDao(request);
	Aba aba = new Aba();
	aba.setNomeAba("PONTOS_CRITICOS");
	aba = (Aba)(abaDao.pesquisar(aba, new String[]{"codAba", "asc"}).get(0));	
	
	ItemEstruturaDao itemEstruturaDao = new ItemEstruturaDao(request);
	ItemEstruturaIett itemEstrutura = (ItemEstruturaIett) itemEstruturaDao.buscar(ItemEstruturaIett.class, ari.getItemEstruturaIett().getCodIett());
	ItemEstrutAcaoIetta ieAcao = new ItemEstrutAcaoIetta();
	EstruturaFuncaoEttf estruturaFuncao = new EstruturaFuncaoEttf();
	EstruturaFuncaoDao estruturaFuncaoDao = new EstruturaFuncaoDao(request);
	estruturaFuncao = (EstruturaFuncaoEttf) estruturaFuncaoDao.getLabelFuncao(itemEstrutura.getEstruturaEtt(),  new FuncaoDao(request).getFuncaoPontosCriticos().getCodFun() ); // Long.valueOf(Pagina.getParamStr(request,"codAba"));
	
	
	
	Boolean permissaoAlterar = new ValidaPermissao().permissaoAlterarItem(itemEstrutura, seguranca.getUsuario(), seguranca.getGruposAcesso(), estruturaFuncao);
		
   	/*  
   	 * Verifica se o usuário é função de acompanhamento com permissão de 
   	 *  de editar os relatórios (parecer)
   	 */ 
     	 
	if (permissaoAlterar.booleanValue()==false  ) {
		_disabled = "disabled";
		_readOnly = "readonly";
		/*AcompRelatorioDao arelDao = new AcompRelatorioDao(request);
		boolean podeAlterar = arelDao.emiteRelatorio(seguranca.getUsuario(), ari);
		
		permissaoAlterar = new Boolean(podeAlterar);
		if(!podeAlterar) {
			_disabled = "disabled";
			_readOnly = "readonly";
		}
		
   	} else {
		_disabled = "disabled";
		_readOnly = "readonly";*/
   	}
	
	EstruturaDao estruturaDao = new EstruturaDao(request);
	List atributos = estruturaDao.getAtributosEstrutura(estruturaFuncao.getEstruturaEtt(), estruturaFuncao.getFuncaoFun());
	
	// vai buscar o label do atributo Datas Limites configurada para a estrutura
	AtributosAtb atributoRecuperado = new AtributosAtb();
	atributoRecuperado.setNomeAtb("dataLimitePtc");
	
	if(atributos != null){
		Iterator it = atributos.iterator();
		while(it.hasNext()){
			ObjetoEstrutura atributo = (ObjetoEstrutura) it.next();
			if(atributo.iGetNome().equals("dataLimitePtc")) {
				atributoRecuperado.setLabelPadraoAtb( atributo.iGetLabel());
				break;
			}
		}
	}	
	
	// se não existir na estrutura o atributo Datas Limites, coloca o label padrão. 
	if (atributoRecuperado.getLabelPadraoAtb() == null) {
		atributoRecuperado.setLabelPadraoAtb("Data Limite");
	}
%>

<%@page import="ecar.pojo.AtributosAtb"%><html lang="pt-br">
	<head>
<%@ include file="/../include/meta.jsp"%>
<%@ include file="/titulo.jsp"%>
<link rel="stylesheet" href="<%=_pathEcar%>/css/style_<%=configuracaoCfg.getEstilo().getNome()%>.css" type="text/css">
<script language="javascript" src="<%=_pathEcar%>/js/menu_retratil.js" type="text/javascript"></script>
<script language="javascript" src="<%=_pathEcar%>/js/focoInicial.js" type="text/javascript"></script>
<script language="javascript" src="<%=_pathEcar%>/js/frmPadraoRegAcompanhamento.js" type="text/javascript"></script>
<script language="javascript" src="<%=_pathEcar%>/js/validacoes.js" type="text/javascript"></script>
<script language="javascript" src="<%=_pathEcar%>/js/datas.js" type="text/javascript"></script>
<script language="javascript" src="<%=_pathEcar%>/js/popUp.js"></script>
<script language="javascript" src="<%=_pathEcar%>/js/exibeEscondePorId.js" type="text/javascript" ></script>
<script language="javascript" src="<%=_pathEcar%>/js/limpezaCamposMultivalorados.js"></script>
<script language="javascript" src="<%=_pathEcar%>/js/validacoesAtributoLivre.js"></script>
<script language="javascript" src="<%=_pathEcar%>/js/numero.js"></script>


<%@ include file="validacao.jsp"%>
<script language="javascript" type="text/javascript">

function trocarAba(nomeAba) {
	document.forms[0].action = nomeAba;
	document.forms[0].submit();
}
function voltarTelaAnterior(tela) {
	document.forms[0].action = tela;
	document.forms[0].submit();
}
function aoClicarVoltar(form){
		form.action = "pontosCriticos.jsp";
		form.submit();	
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

function aoClicarBtn2(form){
	if(validar(form)){
		if('<%=configMailCfgm.getAtivoCfgm()%>' == 'S') {
			if(document.form.obrigatorio.value == 'S' || (document.form.obrigatorio.value == 'N' && confirm('<%=_msg.getMensagem("acompanhamento.cadastroItens.pontosCriticos.criacao.autorizaEnviaEmail")%>') == true )) {
				document.form.autorizarMail.value = 'S';
			} 
		}
		form.btn2[0].disabled = true;
		form.btn2[1].disabled = true;
		form.hidAcao.value = "incluir";
		form.action = "ctrl.jsp"
		form.submit();
	}
}

function validarCampo (difDias,campoCodCor){

	var label = "<%=atributoRecuperado.getLabelPadraoAtb()%>";

	if (document.forms[0].dataLimitePtc != null){
		open_calendar_comValidacao(difDias, campoCodCor, document.forms[0].dataLimitePtc.value, null ,label);
	} else {
		alert ('O campo '+label+' deve ser associado a estrutura.');
	}

}

<util:validacaoPontoCriticoTag atributos="<%=atributos%>" sisGrupoAtributoDao="<%= new SisGrupoAtributoDao(request)%>" monitoramento = "<%=Dominios.SIM%>"/>

</script>
	</head>
	<body onload="focoInicial(document.form);">
		<%@ include file="/../cabecalho.jsp"%>
		<%@ include file="/../divmenu.jsp"%>
		<div id="conteudo">
		<!-- o cabecalho da tela -->
		<% 
		AcompReferenciaAref	arefSelecionado = null;
		arefSelecionado = ari.getAcompReferenciaAref();
		String periodo = Pagina.getParamStr(request, "periodo");
		String funcaoSelecionada = "PONTOS_CRITICOS";
		Boolean novoPontoCritico = Boolean.TRUE; //utilizado na taglib FormPontoCritico
		%>
		
		<%@ include  file="../form_registro.jsp" %>
		
		<form name="form" method="post" action="">
		<input type="hidden" name="mesReferencia" value="<%=ari.getAcompReferenciaAref().getCodAref().toString()%>">
		<input type="hidden" name="niveisPlanejamento" value="<%=Pagina.getParamStr(request, "niveisPlanejamento")%>">
		<input type="hidden" name="primeiroIettClicado" value="<%=Pagina.getParamStr(request, "primeiroIettClicado")%>">
		<input type="hidden" name="primeiroAriClicado" value="<%=Pagina.getParamStr(request, "primeiroAriClicado")%>">
		<input type="hidden" name="codIettRelacao" value="<%=Pagina.getParamStr(request, "codIettRelacao")%>">
		<input type="hidden" name="itemDoNivelClicado" value="<%=Pagina.getParamStr(request, "itemDoNivelClicado")%>">
		<input type="hidden" name="codigosItem" value="<%=Pagina.getParamStr(request, "codigosItem")%>">
		<input type="hidden" name="autorizarMail" value="N">
		<input type="hidden" name="obrigatorio" value="<%=configMailCfgm.getIndEnvioObrigatorio()%>">
		
			<util:barrabotoes btn2="Gravar" voltar="Cancelar"/>
			<table name="form" class="form" width="100%">
			<%@ include file="form.jsp"%>
			</table>
			
			<util:barrabotoes btn2="Gravar" voltar="Cancelar"/>
			
		</form>
		</div>
	</body>
</html>
<%  
} catch ( ECARException e ){
	org.apache.log4j.Logger.getLogger(this.getClass()).error(e);
	Mensagem.alert(_jspx_page_context, e.getMessageKey());
} catch (Exception e){
%>
<%@ include file="/excecao.jsp"%>
<%
}
%>
<%@ include file="/include/mensagemAlert.jsp"%>