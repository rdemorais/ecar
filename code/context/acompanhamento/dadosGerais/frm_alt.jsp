<jsp:directive.page import="ecar.util.Dominios"/>
<jsp:directive.page import="ecar.pojo.AcompReferenciaAref"/>
<%@ include file="/login/validaAcesso.jsp"%>
<%@ include file="/frm_global.jsp"%>
 
<%@ page import="ecar.pojo.EstruturaEtt" %>
<%@ page import="ecar.pojo.AcompReferenciaItemAri" %> 
<%@ page import="ecar.pojo.ItemEstruturaIett" %>
<%@ page import="ecar.pojo.SisAtributoSatb" %>
<%@ page import="ecar.pojo.ObjetoEstrutura" %> 
<%@ page import="ecar.pojo.ConfigMailCfgm" %>
<%@ page import="ecar.pojo.UsuarioUsu"%>
<%@ page import="ecar.dao.EstruturaDao" %>
<%@ page import="ecar.dao.ItemEstruturaDao" %> 
<%@ page import="ecar.dao.ConfiguracaoDao" %>
<%@ page import="ecar.dao.ConfigMailCfgmDAO" %>
<%@ page import="ecar.dao.AcompReferenciaItemDao"%>
<%@ page import="comum.util.ConstantesECAR"%>
 
<%@ page import="java.util.List" %> 
<%@ page import="java.util.Set" %>  
<%@ page import="java.util.Iterator" %>
<%@ page import="comum.util.Data"%>
  
<%@ taglib uri="/WEB-INF/ecar-util.tld" prefix="util" %>
<%@ taglib uri="/WEB-INF/ecar-combo.tld" prefix="combo" %>
<%
try{
/* carrega o usuário da session */
UsuarioUsu usuario = ((ecar.login.SegurancaECAR)session.getAttribute("seguranca")).getUsuario();

/* item pai do item que está sendo cadastrado */  
AcompReferenciaItemDao ariDao = new AcompReferenciaItemDao(request); 
AcompReferenciaItemAri ari = (AcompReferenciaItemAri) ariDao.buscar(AcompReferenciaItemAri.class, Long.valueOf(Pagina.getParamStr(request, "codAri")));

AcompReferenciaAref	arefSelecionado = null;
arefSelecionado = ari.getAcompReferenciaAref();

ValidaPermissao validaPermissao = new ValidaPermissao();

EstruturaDao estruturaDao = new EstruturaDao(request);
ItemEstruturaDao itemEstruturaDao = new ItemEstruturaDao(request);
ItemEstruturaIett itemEstrutura = (ItemEstruturaIett) itemEstruturaDao.buscar(ItemEstruturaIett.class, ari.getItemEstruturaIett().getCodIett());
EstruturaEtt estrutura = itemEstrutura.getEstruturaEtt();
List atributos = estruturaDao.getAtributosEstruturaDadosGerais(estrutura);
List niveisPlanejamentoSelected = itemEstruturaDao.getNivelAcessoById(itemEstrutura);
Boolean desabilitar = new Boolean(false);
long codIettPrincipal = 0;
if(itemEstrutura.getItemEstruturaIett()!=null)
	codIettPrincipal = itemEstrutura.getItemEstruturaIett().getCodIett().longValue();	
%> 

<html lang="pt-br">
<head>
<%@ include file="/include/meta.jsp"%>
<%@ include file="/titulo.jsp"%>
<link rel="stylesheet" href="<%=_pathEcar%>/css/style_<%=configuracaoCfg.getEstilo().getNome()%>.css" type="text/css">
<script language="javascript" src="<%=_pathEcar%>/js/menu_retratil.js"></script>
<script language="javascript" src="<%=_pathEcar%>/js/focoInicial.js"></script>
<script language="javascript" src="<%=_pathEcar%>/js/frmPadraoRegAcompanhamento.js" type="text/javascript"></script>
<script language="javascript" src="<%=_pathEcar%>/js/datas.js"></script>
<script language="javascript" src="<%=_pathEcar%>/js/numero.js"></script>
<script language="javascript" src="<%=_pathEcar%>/js/popUp.js"></script> 
<script language="javascript" src="<%=_pathEcar%>/js/validacoes.js"></script>
<script language="javascript" src="<%=_pathEcar%>/cadastroItens/dadosGerais/validacao.js"></script>
<script language="javascript" src="<%=_pathEcar%>/js/prototype.js"></script>
<script language="javascript" src="<%=_pathEcar%>/js/validacoesAtributoLivre.js"></script>
<script language="javascript" src="<%=_pathEcar%>/js/datas.js"></script>

<%
	ConfigMailCfgmDAO configMailCfgmDAO = new ConfigMailCfgmDAO();
	ConfigMailCfgm configMailCfgmAtivar = null;
	ConfigMailCfgm configMailCfgmRetirar = null;
	ConfigMailCfgm configMailCfgmBloquear = null;
	ConfigMailCfgm configMailCfgmDesbloquear = null;
	ConfigMailCfgm configMailCfgmAlterarFuncAcomp = null;
	
	configMailCfgmAtivar = (ConfigMailCfgm) configMailCfgmDAO.buscar(ConfigMailCfgm.class, Dominios.CFG_MAIL_ATIVAR_MONITORAMENTO);
	configMailCfgmRetirar = (ConfigMailCfgm) configMailCfgmDAO.buscar(ConfigMailCfgm.class, Dominios.CFG_MAIL_DESATIVAR_MONITORAMENTO);
	configMailCfgmBloquear = (ConfigMailCfgm) configMailCfgmDAO.buscar(ConfigMailCfgm.class, Dominios.CFG_MAIL_BLOQUEAR_PLANEJAMENTO);
	configMailCfgmDesbloquear = (ConfigMailCfgm) configMailCfgmDAO.buscar(ConfigMailCfgm.class, Dominios.CFG_MAIL_DESBLOQUEAR_PLANEJAMENTO);
	configMailCfgmAlterarFuncAcomp = (ConfigMailCfgm) configMailCfgmDAO.buscar(ConfigMailCfgm.class, Dominios.CFG_MAIL_MANUTENCAO_FUNCOES_ACOMPANHAMENTO);
	//ConfiguracaoDao configuracaoDao = new ConfiguracaoDao(request);
	List listSisGrupoAtributoSgaObrigatorio = new ArrayList();
	
	if("S".equals(configuracaoCfg.getSisGrupoAtributoSgaByCodSgaGrAtrNvPlan().getIndObrigatorioSga())
		&& estruturaDao.verificaExistenciaAtributoNaEstruturaByNomeAtb(estrutura, "nivelPlanejamento")) {
		listSisGrupoAtributoSgaObrigatorio.add(configuracaoCfg.getSisGrupoAtributoSgaByCodSgaGrAtrNvPlan());
	}
%>
<script>

function aoClicarBtn2(form)
{
	if (document.form.alterou.value == 'S'){
		if('<%=configMailCfgmAlterarFuncAcomp.getAtivoCfgm()%>' == 'S') {
			if(document.form.configMailCfgmAlterarFuncAcompObrigatorio.value == 'S' || (document.form.configMailCfgmAlterarFuncAcompObrigatorio.value == 'N' && confirm('<%=_msg.getMensagem("acompanhamento.cadastroItens.dadosGerais.alterar.autorizaEnviaEmail")%>') == true )) {
				document.form.autorizarMail.value = 'S';
			} 
		}
	}
	if(validar(form)){
		form.btn2[0].disabled = true;
		form.btn2[1].disabled = true;
		form.hidAcao.value = "alterar";
		form.action = "ctrl.jsp"
		form.submit();
	}
}

function aoClicarVoltar(){ 
	document.form.action = "frm_con.jsp";
	document.form.submit();
}
function voltarTelaAnterior(tela) {
	document.forms[0].action = tela;
	document.forms[0].submit();
}
function trocarAba(nomeAba) {
	document.forms[0].action = nomeAba;
	document.forms[0].clicouAba.value = "S";
	document.forms[0].submit();
}

function habilitaCampoID (id){
	var campo = document.getElementById(id);
	
	campo.readOnly = false;
	campo.focus();
	
}

<util:validacaoItemEstrutura atributos="<%=atributos%>" sisGrupoAtributoSgaObrigatorio="<%=listSisGrupoAtributoSgaObrigatorio%>" acao="<%=ConstantesECAR.ACAO_ALTERAR%>" itemEstrutura="<%=itemEstrutura%>"/>
</script>
</head>
<%@ include file="/cabecalho.jsp" %>
<%@ include file="/divmenu.jsp"%> 

<body onload="focoInicial(document.form);">
<div id="conteudo">

<%
	// essa variavel é utilizada no include do cabecalho das paginas
	//String telaAnterior = "javascript:voltarTelaAnterior('../resumo/detalharItem.jsp');";
	// essa variavel é utilizada no include do cabecalho das paginas
	String codTipoAcompanhamento = Pagina.getParamStr(request, "codTipoAcompanhamento");
	String mesReferencia = Pagina.getParamStr(request, "referencia_hidden");
	String itemDoNivelClicado = Pagina.getParamStr(request, "itemDoNivelClicado");
	String funcaoSelecionada = "DADOS_GERAIS";


//String mesReferencia = Pagina.getParamStr(request, "mesReferencia");
String periodo = Pagina.getParamStr(request, "periodo");
%>

	<%@ include  file="../form_registro.jsp" %>		

	<form  name="form" method="post" >
	
	<util:barrabotoes btn2="Gravar" voltar="Cancelar" textoDireita="<%="( ID: "+itemEstrutura.getCodIett().toString()+" )"%>"/>
	
	<%@ include file="form.jsp" %>	
	
	<util:barrabotoes btn2="Gravar" voltar="Cancelar" textoDireita="<%="( ID: "+itemEstrutura.getCodIett().toString()+" )"%>"/>
	
	<input type="hidden" name="hidTelaAlteracao" value="S">
	<input type="hidden" name="itemDoNivelClicado" value="<%=itemDoNivelClicado%>">
	<input type="hidden" name="codTipoAcompanhamento" value="<%=codTipoAcompanhamento%>">
	<input type="hidden" name="referencia_hidden" value="<%=Pagina.getParamStr(request, "codAri")%>">
	<input type="hidden" name="codArisControle" value="<%=Pagina.getParamStr(request, "codArisControle")%>">
	<input type="hidden" name="periodo" value="<%=Pagina.getParamStr(request, "periodo")%>">	
		<input type="hidden" name="clicouAba" value="<%= Pagina.getParamStr(request, "clicouAba")%>">
	<input type="hidden" name="tipoPadraoExibicaoAba" value="<%= Pagina.getParamStr(request, "tipoPadraoExibicaoAba")%>">
</form>
<br>
</div>
</body>
<%
} catch (ECARException e){
	org.apache.log4j.Logger.getLogger(this.getClass()).error(e);
	Mensagem.alert(_jspx_page_context, e.getMessageKey());
} catch (Exception e){
%>
<%@ include file="/excecao.jsp"%>
<%
}
%>
<%@ include file="/include/mensagemAlert.jsp" %>
</html>