<jsp:directive.page import="ecar.util.Dominios"/>
<jsp:directive.page import="ecar.pojo.EstruturaFuncaoEttf"/>
<jsp:directive.page import="ecar.dao.EstruturaFuncaoDao"/><%@ include file="../../login/validaAcesso.jsp"%>
<%@ include file="../../frm_global.jsp"%>
 
<%@ page import="ecar.pojo.EstruturaEtt" %>
<%@ page import="ecar.pojo.ItemEstruturaIett" %>
<%@ page import="ecar.pojo.SisAtributoSatb" %>
<%@ page import="ecar.pojo.ObjetoEstrutura" %> 
<%@ page import="ecar.dao.EstruturaDao" %>
<%@ page import="ecar.dao.ItemEstruturaDao" %> 
<%@ page import="ecar.dao.ConfiguracaoDao" %>
<%@ page import="ecar.pojo.ConfigMailCfgm" %>
<%@ page import="ecar.permissao.ValidaPermissao" %> 
<%@ page import="ecar.dao.ConfigMailCfgmDAO" %>

<%@ page import="comum.util.ConstantesECAR" %>
 
<%@ page import="java.util.List" %> 
<%@ page import="java.util.Set" %>  
<%@ page import="java.util.Iterator" %>
  
<%@ taglib uri="/WEB-INF/ecar-util.tld" prefix="util" %>
<%@ taglib uri="/WEB-INF/ecar-combo.tld" prefix="combo" %>
 
<%
try{
	EstruturaDao estruturaDao = new EstruturaDao(request);
	ItemEstruturaDao itemEstruturaDao = new ItemEstruturaDao(request); 
	ItemEstruturaIett itemEstrutura = (ItemEstruturaIett) itemEstruturaDao.buscar(ItemEstruturaIett.class, Long.valueOf(Pagina.getParamStr(request, "codIett")));
	ValidaPermissao validaPermissao = new ValidaPermissao();
	if ( !validaPermissao.permissaoConsultaIETT( itemEstrutura.getCodIett() , seguranca ) )
	{
		response.sendRedirect( request.getContextPath() +  "/acessoIndevido.jsp" );
	}	
	
	
	EstruturaEtt estrutura = itemEstrutura.getEstruturaEtt();
	List atributos = estruturaDao.getAtributosEstruturaDadosGerais(estrutura);
	List niveisPlanejamentoSelected = itemEstruturaDao.getNivelAcessoById(itemEstrutura);
	long codIettPrincipal = 0;
	Boolean desabilitar = new Boolean(false);
	if(itemEstrutura.getItemEstruturaIett()!=null)
		codIettPrincipal = itemEstrutura.getItemEstruturaIett().getCodIett().longValue();	
		
	String codAba = Pagina.getParamStr(request, "codAba");
	
	EstruturaFuncaoEttf estruturaFuncao = new EstruturaFuncaoEttf();
	EstruturaFuncaoDao estruturaFuncaoDao = new EstruturaFuncaoDao(request);
	
	estruturaFuncao = estruturaFuncaoDao.getLabelFuncao(itemEstrutura.getEstruturaEtt(), Long.valueOf(codAba));
		
	//Verifica permissao para exibir o histórico de acordo com a estrutura/funcao
	boolean permissaoExbirHistoricoEstruturaFuncao = estruturaFuncaoDao.permissaoExibirHistorico(estruturaFuncao);
	//Verifica permissao para exibir histórico de acordo com as permissões de grupo de acesso
	boolean permissaoExibirHistoricoGruposAcesso = validaPermissao.permissaoExibirHistorico(itemEstrutura.getEstruturaEtt(),seguranca.getGruposAcesso());
	//O usuário só poderá visualizar o histórico caso tenha as duas permissões
	boolean permissaoExibirHistorico = (permissaoExbirHistoricoEstruturaFuncao && permissaoExibirHistoricoGruposAcesso);


%> 

<html lang="pt-br">
<head>
<%@ include file="../../include/meta.jsp"%>
<%@ include file="/titulo.jsp"%>
<link rel="stylesheet" href="<%=_pathEcar%>/css/style_<%=configuracaoCfg.getEstilo().getNome()%>.css" type="text/css">
<script language="javascript" src="../../js/menu_retratil.js"></script>
<script language="javascript" src="../../js/focoInicial.js"></script>
<script language="javascript" src="../../js/frmPadraoItemEstrut.js"></script>
<!--<script language="javascript" src="../../js/mascaraMoeda.js"></script>-->
<script language="javascript" src="../../js/numero.js"></script>
<script language="javascript" src="../../js/popUp.js"></script> 
<script language="javascript" src="../../js/validacoes.js"></script>
<script language="javascript" src="validacao.js"></script>
<script language="javascript" src="../../js/validacoesAtributoLivre.js"></script>
<script language="javascript" src="../../js/datas.js"></script>

<script language="javascript" src="<%=_pathEcar%>/js/destaqueLinha.js"></script>
<script language="javascript" src="<%=_pathEcar%>/js/listaItensCadastro.js" type="text/javascript"></script>		 
<script language="javascript" src="<%=_pathEcar%>/js/jquery.js" type="text/javascript"></script>
<script type="text/javascript">jQuery.noConflict();</script>		
<script language="javascript" src="<%=_pathEcar%>/js/cookie.js"></script>	
<script language="javascript" src="<%=_pathEcar%>/js/prototype.js" type="text/javascript"></script>

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
		form.action = "ctrl.jsp";
		form.submit();
	}
}

function aoClicarVoltar(){ 
	document.form.action = "frm_con.jsp";
	document.form.submit();
}

function aoClicarBtn4(form){
	document.form.action = "lista_historico.jsp";
	document.form.submit();
}

<util:validacaoItemEstrutura atributos="<%=atributos%>" sisGrupoAtributoSgaObrigatorio="<%=listSisGrupoAtributoSgaObrigatorio%>" sisGrupoAtributoDao="<%= new SisGrupoAtributoDao(request)%>" acao="<%=ConstantesECAR.ACAO_ALTERAR%>" itemEstrutura ="<%=itemEstrutura%>"/>
</script>
</head>

<%@ include file="../../cabecalho.jsp" %>
<%@ include file="../../divmenu.jsp"%> 

<body onload="focoInicial(document.form);">
<div id="conteudo">
 
	<div id="tituloTelaCadastro">
		<!-- TITULO -->
		<%@ include file="/titulo_tela.jsp"%> 
	</div>
	
	 
 
	<%
	%>
	
	

	<form  name="form" method="post" >
	<input type="hidden" name="configMailCfgmAtivar" value="<%=configMailCfgmAtivar.getAtivoCfgm()%>">
	<input type="hidden" name="configMailCfgmRetirar" value="<%=configMailCfgmRetirar.getAtivoCfgm()%>">
	<input type="hidden" name="configMailCfgmBloquear" value="<%=configMailCfgmBloquear.getAtivoCfgm()%>">
	<input type="hidden" name="configMailCfgmDesbloquear" value="<%=configMailCfgmDesbloquear.getAtivoCfgm()%>">
	<input type="hidden" name="configMailCfgmAlterarFuncAcomp" value="<%=configMailCfgmAlterarFuncAcomp.getAtivoCfgm()%>">
	
	<input type="hidden" name="configMailCfgmAtivarObrigatorio" value="<%=configMailCfgmAtivar.getIndEnvioObrigatorio()%>">
	<input type="hidden" name="configMailCfgmRetirarObrigatorio" value="<%=configMailCfgmRetirar.getIndEnvioObrigatorio()%>">
	<input type="hidden" name="configMailCfgmBloquearObrigatorio" value="<%=configMailCfgmBloquear.getIndEnvioObrigatorio()%>">
	<input type="hidden" name="configMailCfgmDesbloquearObrigatorio" value="<%=configMailCfgmDesbloquear.getIndEnvioObrigatorio()%>">
	<input type="hidden" name="configMailCfgmAlterarFuncAcompObrigatorio" value="<%=configMailCfgmAlterarFuncAcomp.getIndEnvioObrigatorio()%>">	
	
	
	
	<input type="hidden" name="msgAutorizaAtivar" value='<%=_msg.getMensagem("acompanhamento.cadastroItens.dadosGerais.ativar.autorizaEnviaEmail")%>'>
	<input type="hidden" name="msgAutorizaRetirar" value='<%=_msg.getMensagem("acompanhamento.cadastroItens.dadosGerais.retirar.autorizaEnviaEmail")%>'>
	<input type="hidden" name="msgAutorizaBloquear" value='<%=_msg.getMensagem("acompanhamento.cadastroItens.dadosGerais.bloquear.autorizaEnviaEmail")%>'>
	<input type="hidden" name="msgAutorizaDesbloquear" value='<%=_msg.getMensagem("acompanhamento.cadastroItens.dadosGerais.desbloquear.autorizaEnviaEmail")%>'>
	<input type="hidden" name="msgAtivarItensFilho" value='<%=_msg.getMensagem("acompanhamento.cadastroItens.dadosGerais.ativar.todosItensFilhos")%>'>
	<input type="hidden" name="msgRetirarItensFilho" value='<%=_msg.getMensagem("acompanhamento.cadastroItens.dadosGerais.retirar.todosItensFilhos")%>'>
	<input type="hidden" name="msgDesbloquearItensFilho" value='<%=_msg.getMensagem("acompanhamento.cadastroItens.dadosGerais.desbloquearPlanejamento.todosItensFilhos")%>'>
	<input type="hidden" name="msgBloquearItensFilho" value='<%=_msg.getMensagem("acompanhamento.cadastroItens.dadosGerais.bloquearPlanejamento.todosItensFilhos")%>'>
	<input type="hidden" name="autorizarMail" value="N">
	<input type="hidden" name="ativarRetirarMonitoramentoItensFilho" value="N">
	<input type="hidden" name="bloquearDesbloquearPlanejamentoItensFilho" value="N">
	<input type="hidden" name="codAba" value="<%=codAba%>">
	<input type="hidden" name="codEttSelecionado" value="<%=itemEstrutura.getEstruturaEtt().getCodEtt()%>">
	 
	<%
	boolean ehTelaListagem = false;
	EstruturaEtt estruturaEttSelecionada = null;
	EstruturaDao estruturaDaoArvoreItens = new EstruturaDao(request);
		
	//ConfiguracaoCfg configuracaoCfg = new ecar.dao.ConfiguracaoDao(request).getConfiguracao();
	
	if(configuracaoCfg.getIndExibirArvoreNavegacaoCfg() != null && configuracaoCfg.getIndExibirArvoreNavegacaoCfg().equals("S")){
		%>

		<script language="javascript" src="../../js/menu_retratil_cadastro.js"></script>
		<script language="javascript" src="../../js/menu_cadastro.js"></script>	

		<%
		}else{
		%>
		<script language="javascript" src="../../js/menu_retratil.js"></script>
		<%
		}
		%>
	
	<%@ include file="../arvoreItens.jsp"%>	
	
	<table width="100%" cellspacing="0" cellpadding="0" border="0">
	
		<tr>
			<td>
			<%
			//Se usar a árvore o id do div precisa ser "conteudoCadastroEstrutura" para que seja utilizado o estilo da árvore
			if(configuracaoCfg.getIndExibirArvoreNavegacaoCfg() != null && configuracaoCfg.getIndExibirArvoreNavegacaoCfg().equals("S")){ %>
			<div id="conteudoCadastroEstrutura">
			<%
			}else{
			%>
			<div>
			<%
			}
			%>
			<table width="100%" border="0" cellpadding="0" cellspacing="0">
			<tr>
				<%
					//Utilizado apenas quando a árvore está configurada para aparecer
					if (configuracaoCfg.getIndExibirArvoreNavegacaoCfg() != null && configuracaoCfg.getIndExibirArvoreNavegacaoCfg().equals("S")) {
					%>
					<!-- Barra amarela -->
		    			<td class="menuHideShowCadastro">
		    			<!-- Botão na barra -->
					<div id="btmenuCadastro"></div>
					</td>
					<script language="javascript">			
						//Inicia com o menu cadastro aberto
						botaoCadastro("aberto");
						mudaEstadoCadastro("aberto");			
					</script>
				<%} %>
				<td width="100%" valign="top">
					<util:arvoreEstruturas itemEstrutura="<%=itemEstrutura%>" contextPath="<%=_pathEcar%>" seguranca="<%=seguranca%>" idLinhaCadastro="<%=Pagina.getParamStr(request,"ultimoIdLinhaDetalhado") %>" ultimoIdLinhaExpandido="<%=Pagina.getParamStr(request,"ultimoIdLinhaExpandido")%>"/>
					<util:barraLinksItemEstrutura 
					estrutura="<%=estrutura%>" 
					selectedFuncao="<%=codAba%>" 
					desabilitarLinks="false"
					codItemEstrutura="<%=itemEstrutura.getCodIett().toString()%>"
					contextPath="<%=request.getContextPath()%>"
					idLinhaCadastro="<%=Pagina.getParamStr(request,"ultimoIdLinhaDetalhado") %>"
					
					/>
					<br><br>
					<util:barrabotoes btn2="Gravar" voltar="Cancelar" btn4="Histórico" exibirBtn4="<%=new Boolean(permissaoExibirHistorico)%>" textoDireita="<%="( ID: "+itemEstrutura.getCodIett().toString()+" )"%>"/>
							
					<table name="form" class="form" width="100%">
					<util:msgObrigatorio obrigatorio="<%=_obrigatorio%>" />
						<%@ include file="form.jsp" %>				
					</table>
				
					<util:barrabotoes btn2="Gravar" voltar="Cancelar" btn4="Histórico" exibirBtn4="<%=new Boolean(permissaoExibirHistorico)%>" textoDireita="<%="( ID: "+itemEstrutura.getCodIett().toString()+" )"%>"/>
						
				</td>
			</tr>
		</table>			
	</div>
			
		</td>
	</tr>
	
	</table>
	<input type="hidden" name="hidTelaAlteracao" value="S">
</form>


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
<br>
</div>
</body>

<%@ include file="../../include/mensagemAlert.jsp" %>
</html>