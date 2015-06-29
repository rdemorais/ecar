<jsp:directive.page import="ecar.pojo.AcompReferenciaAref"/><%@ include file="/login/validaAcesso.jsp"%>
<%@ include file="/frm_global.jsp"%>

<%@ page import="ecar.util.Dominios"%>
<%@ page import="ecar.pojo.EstruturaEtt"%> 
<%@ page import="ecar.pojo.AcompReferenciaItemAri"%> 
<%@ page import="ecar.pojo.ItemEstruturaIett" %>
<%@ page import="ecar.pojo.SisAtributoSatb" %>
<%@ page import="ecar.pojo.ObjetoEstrutura"%>
<%@ page import="ecar.pojo.ConfigMailCfgm" %>
<%@ page import="ecar.pojo.UsuarioUsu"%>
<%@ page import="ecar.dao.EstruturaDao"%>
<%@ page import="ecar.dao.ItemEstruturaDao" %>
<%@ page import="ecar.dao.ConfiguracaoDao" %> 
<%@ page import="ecar.dao.ConfigMailCfgmDAO" %>
<%@ page import="ecar.dao.AcompReferenciaItemDao"%>

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

	AcompReferenciaAref arefSelecionado = null;
	arefSelecionado = ari.getAcompReferenciaAref();

	EstruturaDao estruturaDao = new EstruturaDao(request);
	ItemEstruturaDao itemEstruturaDao = new ItemEstruturaDao(request);
	ItemEstruturaIett itemEstrutura = (ItemEstruturaIett) itemEstruturaDao.buscar(ItemEstruturaIett.class, ari.getItemEstruturaIett().getCodIett());
	EstruturaEtt estrutura = itemEstrutura.getEstruturaEtt();
	List atributos = estruturaDao.getAtributosEstruturaDadosGerais(estrutura);
	List niveisPlanejamentoSelected = itemEstruturaDao.getNivelAcessoById(itemEstrutura);
	_disabled = " disabled ";
	/* no form.jsp, vai indicar para a taglib que os campos devem ser desabilitados */
	Boolean desabilitar = new Boolean(true);
	long codIettPrincipal =0;
	if(itemEstrutura.getItemEstruturaIett()!=null)
		codIettPrincipal = itemEstrutura.getItemEstruturaIett().getCodIett().longValue();
	
	ValidaPermissao validaPermissao = new ValidaPermissao();
	
	Boolean permissaoAlterar = validaPermissao.permissaoAlterarItem(itemEstrutura, seguranca.getUsuario(), seguranca.getGruposAcesso(), null);
	
	// essa variavel é utilizada no include do cabecalho das paginas
	String codTipoAcompanhamento = Pagina.getParamStr(request, "codTipoAcompanhamento");
	String mesReferencia = Pagina.getParamStr(request, "referencia_hidden");
	String itemDoNivelClicado = Pagina.getParamStr(request, "itemDoNivelClicado");
	String periodo = Pagina.getParamStr(request, "periodo");
		
	AbaDao abaDao = new AbaDao(request);
	Aba aba = new Aba();
	aba.setNomeAba("DADOS_GERAIS");
	aba = (Aba)((new AbaDao(request)).pesquisar(aba, new String[]{"codAba", "asc"}).get(0));
	String nomeAbaSelecionada = "";
	
	if(ari!=null){
		nomeAbaSelecionada = abaDao.getLabelAbaEstrutura(aba, ari.getItemEstruturaIett().getEstruturaEtt());
	}
	else{
		nomeAbaSelecionada = "Dados Gerais";
	}
	
%>


<%@page import="ecar.pojo.TipoAcompanhamentoTa"%>
<%@page import="ecar.dao.AbaDao"%>
<%@page import="ecar.pojo.Aba"%><html lang="pt-br"> 
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

<%
	ConfigMailCfgmDAO configMailCfgmDAO = new ConfigMailCfgmDAO();
	ConfigMailCfgm configMailCfgmAtivar = null;
	ConfigMailCfgm configMailCfgmRetirar = null;
	ConfigMailCfgm configMailCfgmBloquear = null;
	ConfigMailCfgm configMailCfgmDesbloquear = null;
	ConfigMailCfgm configMailCfgmAlterarFuncAcomp = null;
	
	configMailCfgmAtivar = (ConfigMailCfgm) configMailCfgmDAO.buscar(ConfigMailCfgm.class, Dominios.CFG_MAIL_ATIVAR_MONITORAMENTO);
	configMailCfgmRetirar = (ConfigMailCfgm) configMailCfgmDAO.buscar(ConfigMailCfgm.class,Dominios.CFG_MAIL_DESATIVAR_MONITORAMENTO);
	configMailCfgmBloquear = (ConfigMailCfgm) configMailCfgmDAO.buscar(ConfigMailCfgm.class, Dominios.CFG_MAIL_BLOQUEAR_PLANEJAMENTO);
	configMailCfgmDesbloquear = (ConfigMailCfgm) configMailCfgmDAO.buscar(ConfigMailCfgm.class, Dominios.CFG_MAIL_DESBLOQUEAR_PLANEJAMENTO);
	configMailCfgmAlterarFuncAcomp = (ConfigMailCfgm) configMailCfgmDAO.buscar(ConfigMailCfgm.class, Dominios.CFG_MAIL_MANUTENCAO_FUNCOES_ACOMPANHAMENTO);
	//ConfiguracaoDao configuracaoDao = new ConfiguracaoDao(request);
	List listSisGrupoAtributoSgaObrigatorio = new ArrayList();
	
	if("S".equals(configuracaoCfg.getSisGrupoAtributoSgaByCodSgaGrAtrNvPlan().getIndObrigatorioSga())
		&& estruturaDao.verificaExistenciaAtributoNaEstruturaByNomeAtb(estrutura, "nivelPlanejamento")) {
		listSisGrupoAtributoSgaObrigatorio.add(configuracaoCfg.getSisGrupoAtributoSgaByCodSgaGrAtrNvPlan());
	}
	
	
	String funcaoSelecionada = "DADOS_GERAIS";
			
%>
<script>
function trocarAba(nomeAba) {
	document.forms[0].action = nomeAba;
	document.forms[0].clicouAba.value = "S";
	document.forms[0].submit();
}

function aoClicarBtn2(form)
{
	if (document.form.alterou.value == 'S'){
		if('<%=configMailCfgmAlterarFuncAcomp.getAtivoCfgm()%>' == 'S') {
			if(document.form.obrigatorio.value == 'S' || ( document.form.obrigatorio.value == 'N' && confirm('<%=_msg.getMensagem("acompanhamento.dadosBasicos.alteracao.autorizaEnviaEmail")%>') == true ) ) {
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
	<%
	String estruturaPai = "";
	if(estrutura.getEstruturaEtt()!=null)
		estruturaPai = estrutura.getEstruturaEtt().getCodEtt().toString();
	%>
	window.location = "../listaItem/lista.jsp?codEttPrincipal=<%=estruturaPai%>";
}


<util:validacaoItemEstrutura 
	atributos="<%=atributos%>" 
	sisGrupoAtributoSgaObrigatorio="<%=listSisGrupoAtributoSgaObrigatorio%>"/>


</script>


</head>
<body>

<%@ include file="/cabecalho.jsp" %>
<%@ include file="/divmenu.jsp"%> 

<div id="conteudo"> 



<form name="form" method="post">
	<input type="hidden" name="obrigatorio" value="<%=configMailCfgmAlterarFuncAcomp.getIndEnvioObrigatorio()%>">
	<input type="hidden" name="codEttSelecionado" id="codEttSelecionado" value="<%=Pagina.getParamStr(request, "codEttSelecionado")%>" >
	<input type="hidden" name="codTipoAcompanhamento" value="<%=codTipoAcompanhamento%>">
	<input type="hidden" name="niveisPlanejamento" value="<%=Pagina.getParamStr(request, "niveisPlanejamento")%>">
	<input type="hidden" name="referencia_hidden" value="<%=ari.getAcompReferenciaAref().getCodAref().toString()%>">
	<input type="hidden" name="itemDoNivelClicado" value="<%=itemDoNivelClicado%>">
	<input type="hidden" name="codArisControle" value="<%=Pagina.getParamStr(request, "codArisControle")%>">
	<input type="hidden" name="periodo" value="<%=Pagina.getParamStr(request, "periodo")%>">	
	<input type="hidden" name="clicouAba" value="<%= Pagina.getParamStr(request, "clicouAba")%>">
	<input type="hidden" name="tipoPadraoExibicaoAba" value="<%= Pagina.getParamStr(request, "tipoPadraoExibicaoAba")%>">
	
	<%@ include  file="../form_registro.jsp" %>		
	<%
	
		if("S".equals(itemEstrutura.getEstruturaEtt().getIndAtivoEtt())){ 
	
	%>
	<util:barrabotoes btn1="Alterar" exibirBtn1="<%=permissaoAlterar%>" textoDireita="<%="( ID: "+itemEstrutura.getCodIett().toString()+" )"%>"/>			
	
	<%
	
		} else {
	
	%>
		
	<table class="barrabotoes" cellpadding="0" cellspacing="0">
		<tr>
			<td align="left">
			<b><%=nomeAbaSelecionada%></b>
			</td>
		</tr>
	</table>
	
	<%
		
		}
		
	%>
	
	<%@ include file="form.jsp" %>	

	<%
	
		if("S".equals(itemEstrutura.getEstruturaEtt().getIndAtivoEtt())){ 
	
	%>
			
	<util:barrabotoes btn1="Alterar" exibirBtn1="<%=permissaoAlterar%>" textoDireita="<%="( ID: "+itemEstrutura.getCodIett().toString()+" )"%>"/>

	<%
	
		} else {
	
	%>
		
	<table class="barrabotoes" cellpadding="0" cellspacing="0">
		<tr>
			<td align="left">
			&nbsp;
			</td>
		</tr>
	</table>
	
	<%
		
		}
		
	%>

	<input type="hidden" name="hidTelaAlteracao" value="N">
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