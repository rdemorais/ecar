<jsp:directive.page import="ecar.util.Dominios"/>
<jsp:directive.page import="ecar.dao.EstruturaDao"/>
<%@ include file="../../login/validaAcesso.jsp"%>
<%@ include file="../../frm_global.jsp"%>

<%@ taglib uri="/WEB-INF/ecar-util.tld" prefix="util" %>
<%@ taglib uri="/WEB-INF/ecar-combo.tld" prefix="combo" %>

<%@ page import="ecar.pojo.ItemEstruturaIett" %>
<%@ page import="ecar.pojo.PontoCriticoPtc" %>
<%@ page import="ecar.pojo.UsuarioUsu" %> 
<%@ page import="ecar.dao.PontoCriticoDao" %>
<%@ page import="ecar.dao.ItemEstruturaDao" %>
<%@ page import="ecar.pojo.EstruturaFuncaoEttf" %>
<%@ page import="ecar.dao.EstruturaFuncaoDao" %>
<%@ page import="ecar.permissao.ValidaPermissao" %> 
<%@ page import="ecar.pojo.ConfigMailCfgm" %>
<%@ page import="ecar.dao.ConfigMailCfgmDAO" %>
<%@ page import="comum.database.Dao" %>
<%@page import="ecar.pojo.AtributosAtb"%>

<%@ page import="comum.util.Util" %>

<html lang="pt-br">
<head>
<%@ include file="../../include/meta.jsp"%>
<%@ include file="/titulo.jsp"%>
<link rel="stylesheet" href="<%=_pathEcar%>/css/style_<%=configuracaoCfg.getEstilo().getNome()%>.css" type="text/css">
<script language="javascript" src="../../js/focoInicial.js"></script>
<script language="javascript" src="../../js/frmPadraoItemEstrut.js"></script>
<script language="javascript" src="../../js/datas.js"></script>
<script language="javascript" src="../../js/mascaraMoeda.js"></script>
<script language="javascript" src="../../js/popUp.js"></script> 
<script language="javascript" src="../../js/validacoes.js"></script>
<script language="javascript" src="../../js/limpezaCamposMultivalorados.js"></script>
<script language="javascript" src="../../js/validacoesAtributoLivre.js"></script>
<script language="javascript" src="<%=_pathEcar%>/js/destaqueLinha.js"></script>
<script language="javascript" src="<%=_pathEcar%>/js/listaItensCadastro.js" type="text/javascript"></script>		 
<script language="javascript" src="<%=_pathEcar%>/js/jquery.js" type="text/javascript"></script>
<script type="text/javascript">jQuery.noConflict();</script>		
<script language="javascript" src="<%=_pathEcar%>/js/cookie.js"></script>


<%
try{
	ItemEstruturaDao itemEstruturaDao = new ItemEstruturaDao(request);
	ItemEstruturaIett itemEstrutura = (ItemEstruturaIett) itemEstruturaDao.buscar(ItemEstruturaIett.class, Long.valueOf(Pagina.getParamStr(request, "codIett")));
	
	ValidaPermissao validaPermissao = new ValidaPermissao();
	if ( !validaPermissao.permissaoConsultaIETT(  Long.valueOf(Pagina.getParamStr(request, "codIett")), seguranca ) )
	{response.sendRedirect( request.getContextPath() +  "/acessoIndevido.jsp" );
	}	
	
	
	PontoCriticoPtc pontoCritico = new PontoCriticoPtc();
	
	String codAba = Pagina.getParamStr(request, "codAba");

	ConfigMailCfgmDAO configMailCfgmDAO = new ConfigMailCfgmDAO();
	ConfigMailCfgm configMailCfgm = (ConfigMailCfgm) configMailCfgmDAO.buscar(ConfigMailCfgm.class, Dominios.CFG_MAIL_MANUTENCAO_PONTO_CRITICO);
	EstruturaFuncaoEttf estruturaFuncao = new EstruturaFuncaoEttf();
	EstruturaFuncaoDao estruturaFuncaoDao = new EstruturaFuncaoDao(request);
	
	estruturaFuncao = (EstruturaFuncaoEttf) estruturaFuncaoDao.getLabelFuncao(itemEstrutura.getEstruturaEtt(), Long.valueOf(codAba));
	String _disabledCampo = "";
	String _readonlyCampo = "";
	
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

<script>

function aoClicarBtn2(form)
{
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

function validarCampo (difDias,campoCodCor, ativo){

	var label = "<%=atributoRecuperado.getLabelPadraoAtb()%>";

	if(ativo.checked == false){
		return false;
	}

	if (document.forms[0].dataLimitePtc != null){
		open_calendar_comValidacao(difDias, campoCodCor, document.forms[0].dataLimitePtc.value, null ,label);
	} else {
		alert ('O campo '+label+' deve ser associado a estrutura.');
	}

}

function validaPontoCritico (cor){

	var ativo = document.getElementById('ativo_'+cor);
	var frequencia = document.getElementById('freq_'+cor);
	var antecedencia = document.getElementById('ant_'+cor);

	if(ativo.checked == false){
		frequencia.value='';
		frequencia.disabled = true;
		antecedencia.value='';
		antecedencia.disabled = true
	} else{
		frequencia.disabled = false;
		antecedencia.disabled = false;
	}
	
}

<util:validacaoPontoCriticoTag atributos="<%=atributos%>" sisGrupoAtributoDao="<%= new SisGrupoAtributoDao(request)%>" monitoramento = "<%=Dominios.NAO%>"/>
</script>

<script>
<%//@ include file="validacao.jsp"%>
function aoClicarVoltar(form){
	form.action = "lista.jsp";
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

<form  name="form" method="post" >
<input type="hidden" name="autorizarMail" value="N">
<input type="hidden" name="obrigatorio" value="<%=configMailCfgm.getIndEnvioObrigatorio()%>">



<%
boolean ehTelaListagem = false;
Boolean novoPontoCritico = Boolean.TRUE; 
EstruturaEtt estruturaEttSelecionada = null;
EstruturaDao estruturaDaoArvoreItens = new EstruturaDao(request);

configuracaoCfg = new ecar.dao.ConfiguracaoDao(request).getConfiguracao();

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
							<util:arvoreEstruturas itemEstrutura="<%=itemEstrutura%>" contextPath="<%=_pathEcar%>" seguranca="<%=seguranca%>" idLinhaCadastro="<%=Pagina.getParamStr(request,"ultimoIdLinhaDetalhado") %>"  ultimoIdLinhaExpandido="<%=Pagina.getParamStr(request,"ultimoIdLinhaExpandido")%>"/>
							 
							<util:barraLinksItemEstrutura 
										estrutura="<%=itemEstrutura.getEstruturaEtt()%>" 
										selectedFuncao="<%=codAba%>"
										codItemEstrutura="<%=itemEstrutura.getCodIett().toString()%>"
										contextPath="<%=request.getContextPath()%>"
										idLinhaCadastro="<%=Pagina.getParamStr(request,"ultimoIdLinhaDetalhado") %>"
										/>
							
							<%
								
							%>
							
							<br><br>
							
								<util:barrabotoes btn2="Gravar" voltar="Cancelar"/>
								<table name="form" class="form" width="100%">
								<util:msgObrigatorio obrigatorio="<%=_obrigatorio%>" />
										<%@ include file="form.jsp"%>
									</table>
								<util:barrabotoes btn2="Gravar" voltar="Cancelar"/>
						
						</td>
					</tr>
				</table>			
			</div>
			
		</td>
	</tr>
	
</table>
				


</form>
<%
} catch (ECARException e){
	org.apache.log4j.Logger.getLogger(this.getClass()).error(e);
	Mensagem.alert(_jspx_page_context, e.getMessageKey());
	%>
	<script>
	document.form.action = "lista.jsp";
	document.submit();
	</script>
	<%
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