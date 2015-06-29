<%@ include file="/login/validaAcesso.jsp"%>
<%@ include file="/frm_global.jsp"%>
 
<%@ page import="ecar.util.Dominios"%>
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
 
<%@ page import="java.util.List" %> 
<%@ page import="java.util.Set" %>  
<%@ page import="java.util.Iterator" %>
  
<%@ taglib uri="/WEB-INF/ecar-util.tld" prefix="util" %>
<%@ taglib uri="/WEB-INF/ecar-combo.tld" prefix="combo" %>
<%
try{
/* carrega o usuário da session */
UsuarioUsu usuario = ((ecar.login.SegurancaECAR)session.getAttribute("seguranca")).getUsuario();

/* item pai do item que está sendo cadastrado */  
AcompReferenciaItemDao acompReferenciaItemDao = new AcompReferenciaItemDao(request); 
AcompReferenciaItemAri acompReferenciaItem = (AcompReferenciaItemAri) acompReferenciaItemDao.buscar(AcompReferenciaItemAri.class, Long.valueOf(Pagina.getParamStr(request, "codAri")));

ValidaPermissao validaPermissao = new ValidaPermissao();
if ( !validaPermissao.permissaoConsultaIETT( acompReferenciaItem.getItemEstruturaIett().getCodIett() , seguranca ) )
{
	response.sendRedirect( request.getContextPath() +  "/acessoIndevido.jsp" );
}	

EstruturaDao estruturaDao = new EstruturaDao(request);
ItemEstruturaDao itemEstruturaDao = new ItemEstruturaDao(request);
ItemEstruturaIett itemEstrutura = (ItemEstruturaIett) itemEstruturaDao.buscar(ItemEstruturaIett.class, acompReferenciaItem.getItemEstruturaIett().getCodIett());
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
<script language="javascript" src="<%=_pathEcar%>/js/menu_retratil.js" type="text/javascript"></script>
<script language="javascript" src="<%=_pathEcar%>/js/focoInicial.js" type="text/javascript"></script>
<script language="javascript" src="<%=_pathEcar%>/js/frmPadraoRegAcompanhamento.js" type="text/javascript"></script>
<script language="javascript" src="<%=_pathEcar%>/js/datas.js" type="text/javascript"></script>
<script language="javascript" src="<%=_pathEcar%>/js/numero.js" type="text/javascript"></script>
<script language="javascript" src="<%=_pathEcar%>/js/popUp.js" type="text/javascript"></script> 
<script language="javascript" src="<%=_pathEcar%>/js/validacoes.js" type="text/javascript"></script>
<script language="javascript" src="<%=_pathEcar%>/cadastroItens/dadosGerais/validacao.js" type="text/javascript"></script>
<script language="javascript" src="<%=_pathEcar%>/js/prototype.js" type="text/javascript"></script>
<script language="javascript" src="<%=_pathEcar%>/js/validacoesAtributoLivre.js" type="text/javascript"></script>

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
<script type="text/javascript">

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
<util:validacaoItemEstrutura atributos="<%=atributos%>" sisGrupoAtributoSgaObrigatorio="<%=listSisGrupoAtributoSgaObrigatorio%>"/>
</script>
</head>
<%@ include file="/cabecalho.jsp" %>
<%@ include file="/divmenu.jsp"%> 

<body onload="focoInicial(document.form);">
<div id="conteudo">
<!-- TÍTULO -->
<%@ include file="/titulo_tela.jsp"%>
<%@ include file="../botoesAvancaRetrocede.jsp" %>
 <br>
	<util:barraLinksRegAcomp 
		acompReferenciaItem="<%=acompReferenciaItem%>"  
		selectedFuncao="DADOS_GERAIS"
		usuario="<%=usuario%>"
		primeiroAcomp='<%=Pagina.getParamStr(request, "primeiroAcomp")%>'
		request="<%=request%>"
		gruposUsuario="<%=seguranca.getGruposAcesso() %>"
	/>
	<br>

<table width="100%" border="0" cellpadding="0" cellspacing="0">
		<tr><td class="espacador"><img src="<%=_pathEcar%>/images/pixel.gif" alt=""></td></tr>
		<tr class="linha_titulo" align="left">
			<td>
				Refer&ecirc;ncia: <%=acompReferenciaItem.getAcompReferenciaAref().getNomeAref()%>&nbsp;&nbsp;&nbsp;(<%=acompReferenciaItem.getAcompReferenciaAref().getTipoAcompanhamentoTa().getDescricaoTa()%>)
			</td> 
		</tr>
		<tr class="linha_titulo" align="left">
			<td>
				M&ecirc;s/Ano: <%=acompReferenciaItem.getAcompReferenciaAref().getMesAref()%>/<%=acompReferenciaItem.getAcompReferenciaAref().getAnoAref()%>
			</td> 
		</tr>
		<tr><td class="espacador"><img src="<%=_pathEcar%>/images/pixel.gif" alt=""></td></tr>
</table>
<util:arvoreEstruturaElabAcomp
	itemEstrutura="<%=acompReferenciaItem.getItemEstruturaIett()%>" 
	codigoAcomp='<%=Pagina.getParamStr(request, "codAcomp")%>'
	contextPath="<%=_pathEcar%>"
	listaParaArvore='<%=(java.util.List)session.getAttribute("listaParaArvore")%>'  
	nivelPrimeiroIettClicado='<%=(String)session.getAttribute("nivelPrimeiroIettClicado")%>'
	abaAtual="<%=ecar.taglib.util.ArvoreEstruturaElabAcompTag.ABA_DADOS_BASICOS%>"
	primeiroAcomp='<%=Pagina.getParamStr(request, "primeiroAcomp")%>'
/> 
	<form  name="form" method="post" action="">
	
	<util:barrabotoes btn2="Gravar" voltar="Cancelar" textoDireita='<%="( ID: "+itemEstrutura.getCodIett().toString()+" )"%>'/>
	
	<%@ include file="form.jsp" %>	
	
	<util:barrabotoes btn2="Gravar" voltar="Cancelar" textoDireita='<%="( ID: "+itemEstrutura.getCodIett().toString()+" )"%>'/>
	
	<input type="hidden" name="hidTelaAlteracao" value="S">
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