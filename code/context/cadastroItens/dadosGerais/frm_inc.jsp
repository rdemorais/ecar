<jsp:directive.page import="ecar.util.Dominios"/><%@ include file="../../login/validaAcesso.jsp"%>
<%@ include file="../../frm_global.jsp"%>

<%@ page import="ecar.pojo.EstruturaEtt" %>
<%@ page import="ecar.pojo.ObjetoEstrutura" %>
<%@ page import="ecar.pojo.SisAtributoSatb" %>
<%@ page import="ecar.pojo.ItemEstruturaIett" %>  
<%@ page import="ecar.dao.ItemEstruturaDao" %>  
<%@ page import="ecar.dao.EstruturaDao" %>
<%@ page import="ecar.dao.ConfiguracaoDao" %>
<%@ page import="ecar.pojo.ConfigMailCfgm" %>
<%@ page import="ecar.dao.ConfigMailCfgmDAO" %>
 
<%@ page import="java.util.List" %> 
<%@ page import="java.util.Set" %> 
<%@ page import="java.util.Iterator" %> 

<%@ taglib uri="/WEB-INF/ecar-util.tld" prefix="util" %>
<%@ taglib uri="/WEB-INF/ecar-combo.tld" prefix="combo" %>
 
<%
try{ 

boolean estruturaUsaIndModelo = false; // Verifica se o indicador É modelo está configurado para ser usado pela estrutura  


/* item pai do item que está sendo cadastrado */  
long codIettPrincipal = Pagina.getParamLong(request, "codIettPrincipal");
EstruturaDao estruturaDao = new EstruturaDao(request);
ItemEstruturaDao itemDao = new ItemEstruturaDao(request);

ItemEstruturaIett itemEstrutura = new ItemEstruturaIett();

/* seta o pai desse item para ser capaz de fazer a arvore de estruturas */
if (codIettPrincipal > 0){
	ItemEstruturaIett itemEstruturaSelecionado = (ItemEstruturaIett) itemDao.buscar(ItemEstruturaIett.class, new Long(codIettPrincipal));	
	itemEstrutura.setItemEstruturaIett(itemEstruturaSelecionado);
	request.getSession().setAttribute("itemEstruturaSelecionado", itemEstruturaSelecionado);
}
	

EstruturaEtt estrutura = null;

/* TODO ?? o que acontece se nao entrar no if ?? precisa do if ??*/
if(!"".equals(Pagina.getParamStr(request, "codEtt")))
	estrutura = (EstruturaEtt) estruturaDao.buscar(ecar.pojo.EstruturaEtt.class, Long.valueOf(Pagina.getParamStr(request, "codEtt")));

List atributos = estruturaDao.getAtributosEstruturaDadosGerais(estrutura);

int qtdeTpfa = estrutura.getEstrutTpFuncAcmpEtttfas().size();
boolean temTpfa= false;

if (qtdeTpfa > 0)
	temTpfa = true;


itemEstrutura.setEstruturaEtt(estrutura);

/* no form.jsp vai indicar para a taglib que os campos não devem ser desabilitados */
Boolean desabilitar = new Boolean(false);
estruturaUsaIndModelo = estruturaDao.verificaSeEstruturaUsaModelo(estrutura);
String exibeModelo = estrutura.getIndExibirOpcaoModelo();

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
<script language="javascript" src="<%=_pathEcar%>/js/destaqueLinha.js"></script>
<script language="javascript" src="<%=_pathEcar%>/js/listaItensCadastro.js" type="text/javascript"></script>		 
<script language="javascript" src="<%=_pathEcar%>/js/jquery.js" type="text/javascript"></script>
<script type="text/javascript">jQuery.noConflict();</script>		
<script language="javascript" src="<%=_pathEcar%>/js/cookie.js"></script>
<script language="javascript" src="../../js/prototype.js"></script>

<script language="javascript" src="../../js/datas.js"></script>
<script language="javascript" src="../../js/validacoesAtributoLivre.js"></script>

<%
	ConfigMailCfgmDAO configMailCfgmDAO = new ConfigMailCfgmDAO();
	ConfigMailCfgm configMailCfgm = (ConfigMailCfgm) configMailCfgmDAO.buscar(ConfigMailCfgm.class, Dominios.CFG_MAIL_MANUTENCAO_FUNCOES_ACOMPANHAMENTO);
	//ConfiguracaoDao configuracaoDao = new ConfiguracaoDao(request);
	List listSisGrupoAtributoSgaObrigatorio = new ArrayList();
	
	if("S".equals(configuracaoCfg.getSisGrupoAtributoSgaByCodSgaGrAtrNvPlan().getIndObrigatorioSga())
		&& estruturaDao.verificaExistenciaAtributoNaEstruturaByNomeAtb(estrutura, "nivelPlanejamento")) {
		listSisGrupoAtributoSgaObrigatorio.add(configuracaoCfg.getSisGrupoAtributoSgaByCodSgaGrAtrNvPlan());
	}
%>

<script type="text/javascript">
<util:validacaoItemEstrutura atributos="<%=atributos%>" sisGrupoAtributoSgaObrigatorio="<%=listSisGrupoAtributoSgaObrigatorio%>"/>
</script>
<script>
	function aoClicarBtn3(form){
		var parametros = 'codEtt=<%=estrutura.getCodEtt()%>&ultimoIdLinhaDetalhado=<%=Pagina.getParamStr(request,"ultimoIdLinhaDetalhado")%>&ultimoIdLinhaExpandido=<%=Pagina.getParamStr(request,"ultimoIdLinhaExpandido")%>';
		window.open("popUpPesquisaItemModelo.jsp?"+parametros,"", 'width=600, height=600,toolbar=no,location=no,directories=no,status=no,menubar=no,resizable=no,scrollbars=yes');
	}
	
		
function aoClicarBtn2(form)
{
	if(validar(form)){
		if (document.form.temTpfa.value == 'true')
		{		
			if (document.form.alterou.value == 'S')
			{
				if('<%=configMailCfgm.getAtivoCfgm()%>' == 'S') {
					if(document.form.obrigatorio.value == 'S' || (document.form.obrigatorio.value == 'N' && confirm('<%=_msg.getMensagem("acompanhamento.dadosBasicos.criacao.autorizaEnviaEmail")%>') == true )) {
						document.form.autorizarMail.value = 'S';
					} 
				}
			}
		}
		form.btn2[0].disabled = true;
		form.btn2[1].disabled = true;
		form.hidAcao.value = "incluir";
		form.action = "ctrl.jsp";
		form.submit();
	}
}

function aoClicarCancelar(form) {
	var act = '<%=request.getContextPath()%>/cadastroItens/listaItem/lista.jsp?codIettPrincipal=<%=codIettPrincipal%>';
	if( jsUltEttSelecionado != 'undefined' )
		act = act + '&ultEttSelecionado=' + jsUltEttSelecionado; 
	form.action = act;
	form.submit();
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
	
 	<%
		String codAba = Pagina.getParamStr(request, "codAba");
		Set niveisPlanejamentoSelected = null;
	%>
	<form  name="form" method="post" >
	<input type="hidden" name="autorizarMail" value="N">
	<input type="hidden" name="temTpfa" value="<%=temTpfa%>">
	<input type="hidden" name="alterou" value="">
	<input type="hidden" name="obrigatorio" value="<%=configMailCfgm.getIndEnvioObrigatorio()%>">
	<input type="hidden" name="codAba" value="<%=codAba%>">
	
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
							String codEtt = "";
							String codIett = "";
							String codAvo = "";
							String idLinhaCadastro= (String) Pagina.getParamStr(request,"ultimoIdLinhaDetalhado");
					    	if(idLinhaCadastro != null && idLinhaCadastro.startsWith("ett") ){
								
								//caso da estrutura "filha" da virtual
								if(idLinhaCadastro.contains("_avo_")) {
									codEtt = idLinhaCadastro.substring(idLinhaCadastro.indexOf("_ett") + 4, idLinhaCadastro.indexOf("_avo_"));
									codIett = idLinhaCadastro.substring(idLinhaCadastro.indexOf("_avo_")+5, idLinhaCadastro.length());
									codAvo = idLinhaCadastro.substring(idLinhaCadastro.indexOf("_avo_")+5, idLinhaCadastro.length());
								} else {
									codEtt = idLinhaCadastro.substring(3, idLinhaCadastro.indexOf("_pai_"));
									codIett = idLinhaCadastro.substring(idLinhaCadastro.indexOf("_pai_iett")+9, idLinhaCadastro.length());
								
								}
							} 
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
							<util:arvoreEstruturas itemEstrutura="<%=itemEstrutura%>" contextPath="<%=_pathEcar%>" seguranca="<%=seguranca%>" 
							idLinhaCadastro="<%=Pagina.getParamStr(request,"ultimoIdLinhaDetalhado") %>"
							ultimoIdLinhaExpandido="<%=Pagina.getParamStr(request,"ultimoIdLinhaExpandido") %>"
							proximoNivel="<%=Boolean.FALSE%>"
							/> 
							
							<util:barraLinksItemEstrutura 
								estrutura="<%=estrutura%>" 
								selectedFuncao="<%=codAba%>"  
								desabilitarLinks="true"
								contextPath="<%=request.getContextPath()%>"
							/>
							<br><br>
							<% if ( "S".equalsIgnoreCase(exibeModelo)) { %>
							<util:barrabotoes btn2="Gravar" cancelar="Cancelar" btn3="Usar Modelo"/>
							<%} else { %>
							<util:barrabotoes btn2="Gravar" cancelar="Cancelar"/>
							<%} %>
							
							<table name="form" class="form" width="100%">
							<util:msgObrigatorio obrigatorio="<%=_obrigatorio%>" />
								<%@ include file="form.jsp" %>				
							</table>
				
							<% if ( "S".equalsIgnoreCase(exibeModelo)) { %>
								<util:barrabotoes btn2="Gravar" cancelar="Cancelar" btn3="Usar Modelo"/>
							<%} else { %>
								<util:barrabotoes btn2="Gravar" cancelar="Cancelar"/>
							<%} %>
						
						</td>
					</tr>
				</table>			
			</div>
			
		</td>
	</tr>
	
</table>
	<input type="hidden" name="hidTelaAlteracao" value="N">
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