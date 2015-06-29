<%@ include file="/login/validaAcesso.jsp"%>
<%@ include file="/frm_global.jsp"%> 

<%@ taglib uri="/WEB-INF/ecar-util.tld" prefix="util" %>
<%@ taglib uri="/WEB-INF/ecar-combo.tld" prefix="combo" %>
<%@ page import="ecar.pojo.EstruturaEtt" %>
<%@ page import="ecar.pojo.AtributosAtb"%>
<%@ page import="ecar.dao.EstruturaDao" %>
<%@ page import="ecar.dao.FuncaoDao" %>
<%@ page import="ecar.dao.AtributoDao" %>
<%@ page import="ecar.dao.EstruturaAtributoDao"%>
<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %>
 

<%@page import="ecar.pojo.FuncaoFun"%>
<%@page import="ecar.pojo.SisAtributoSatb"%><html lang="pt-br">
<head>
<%@ include file="/include/meta.jsp"%>
<%@ include file="/titulo.jsp"%>
<link rel="stylesheet" href="<%=_pathEcar%>/css/style_<%=configuracaoCfg.getEstilo().getNome()%>.css" type="text/css">
<script language="javascript" src="<%=_pathEcar%>/js/menu_retratil.js"></script>
<script language="javascript" src="<%=_pathEcar%>/js/focoInicial.js"></script>
<script language="javascript" src="<%=_pathEcar%>/js/frmPadrao.js"></script>
<script language="javascript" src="<%=_pathEcar%>/js/AnchorPosition.js" type="text/javascript"></script>
<script language="javascript" src="<%=_pathEcar%>/js/PopupWindow.js" type="text/javascript"></script>
<script language="javascript" src="<%=_pathEcar%>/js/ColorPicker2.js" type="text/javascript"></script>

<script language="javascript">
function validarPesquisar(form){
	return(true);
}
function reload(form){
	form.action = "frm_pes.jsp";
	form.submit();
}

var field = "";
// Create a new ColorPicker object using Window Popup
var cp = new ColorPicker('window');

function pickColor(color) {
	field.value = color;
}

function selecionarCor(campo){
	field = campo;
	cp.select(campo,'pick');		
}
</script> 

</head>

<!-- sempre colocar o foco inicial no primeiro campo -->
<body onload="javascript:onLoad(document.form);">

<%@ include file="/cabecalho.jsp" %>
<%@ include file="/divmenu.jsp"%>

<div id="conteudo">

<form name="form" method="post">
	<input type="hidden" name="hidAcao" value="">
	
	<!-- TITULO -->
	<%@ include file="/titulo_tela.jsp"%>

	<util:linkstop incluir="frm_inc.jsp" pesquisar="frm_pes.jsp"/>
	<util:barrabotoes pesquisar="Pesquisar" cancelar="Cancelar"/>

	<%
	
	boolean ehPesquisa = true;
	boolean ehNavegacao = false;
	boolean ehInclusao = false;
	boolean ehAlteracao = false;
	
	try{
	
		EstruturaEtt estrutura = new EstruturaEtt();
		EstruturaDao estruturaDao = new EstruturaDao(request);
		EstruturaAtributoDao estruturaAtributoDao = new EstruturaAtributoDao(request);
		FuncaoDao funcaoDao = new FuncaoDao(request);
		
		boolean alteraNivel = true;
		
		
		FuncaoFun funcaoDadosGerais = funcaoDao.getFuncaoDadosGerais();
		FuncaoFun funcaoPontosCriticos = funcaoDao.getFuncaoPontosCriticos();
		List lAtributosDadosGerais = new ArrayList();
		List listaAtributoEstrutura = new ArrayList(0);
		List lAtributosPontosCriticos = new ArrayList();
		List lFuncoes = new ArrayList();
		List lFuncoesAcompanhamento = new ArrayList();
		List funcoesPermitidas = new ArrayList();
		EstruturaEtt estruturaPai = null;
		AtributosAtb atributoSelecionado = null;
		String selectedAtributo = "";
		String selected = "";
		List atributosEttSuperior = new ArrayList();
		List lAtributosEttSuperior = new ArrayList();
		List atributosEttSuperiorEditaveis = new ArrayList();
		List lAtributosEttSuperiorEditaveis = new ArrayList();
		
		
		
		_disabled = "";
		_obrigatorio = "";
		_comboSimNao = "Todos";
		String _pesqdisabled ="disabled";
		
		
		//monta a lista com todos os  atributos de dados gerais  (pesquisa por todos)
		List atributosPermitidosDadosGerais = new AtributoDao(request).getAtributosOpcionais(funcaoDadosGerais);
		//monta a lista com todos os  atributos de pontos criticos  (pesquisa por todos)
		List atributosPermitidosPontosCriticos = new AtributoDao(request).getAtributosOpcionais(funcaoPontosCriticos);
		//monta a lista de funções  (pesquisa por todos)
		funcoesPermitidas = new FuncaoDao(request).getFuncoesOpcionais();
		
		//se carregou a pagina, recupera os campos que foram alterados (pelo request) e seta na estrutura 
		if(Pagina.getParam(request, "indCarregouPagina") != null){
			
			//método para setar parametros na estrutura criada com os campos preenchidos pelo usuario
			//usado porque nao existe valor default na pesquisa
			estruturaDao.setEstruturaPesquisa(request, estrutura, true, true, true);
			//guarda as funções selecionadas (a pesquisa pode ser por todas)
			lFuncoes = estruturaDao.getFuncoesById(estrutura);
			//guarda os atributos de dados gerais selecionados (a pesquisa pode ser por todos)
			lAtributosDadosGerais = estruturaDao.getAtributosById(estrutura, funcaoDadosGerais);
			//guarda as funções de acompanhamento selecionadas (a pesquisa pode ser por todas)
			lFuncoesAcompanhamento = estruturaDao.getFuncoesAcompanhamentoById(estrutura);
			//guarda os atributos de pontos criticos que foram selecionados (a pesquisa pode ser por todos)
			lAtributosPontosCriticos = estruturaDao.getAtributosById(estrutura, funcaoPontosCriticos);
			
			//verifica os campos selecionados na lista
			if(estrutura.getEstruturaEtt() != null){
				estruturaPai = estrutura.getEstruturaEtt();
				//guarda o codigo da estrutura pai
				selected = estruturaPai.getCodEtt().toString();
				//monta a lista de grupos de atributos a partir do nivel superior
				listaAtributoEstrutura = estruturaAtributoDao.getAtributosVisualizarEttSuperior(estruturaPai, funcaoDadosGerais);
			
				// se tiver selecionado um grupo de atributo
				if (estrutura.getAtributoAtbExibirEstruturaEtt() != null){	
					atributoSelecionado = estrutura.getAtributoAtbExibirEstruturaEtt();
					selectedAtributo = atributoSelecionado.getCodAtb().toString();
					
					Iterator itSisAtributos;
					
					//guarda toda a  lista de atributos do grupo de atributo selecionado
					if(atributoSelecionado.getSisGrupoAtributoSga() != null && atributoSelecionado.getSisGrupoAtributoSga().getSisAtributoSatbs() != null){
						itSisAtributos = atributoSelecionado.getSisGrupoAtributoSga().getSisAtributoSatbs().iterator();
					
						while (itSisAtributos.hasNext()){
							SisAtributoSatb sisAtributo = (SisAtributoSatb) itSisAtributos.next();
							
							if (sisAtributo.isAtivo()){
								atributosEttSuperior.add(sisAtributo);
							}
						}
					}
					
					//recupera a lista de todos os atributos selecionados do grupo acima selecionado 
					if(estrutura.getSisAtributoSatbEttSuperior() != null)
						lAtributosEttSuperior = estruturaAtributoDao.getAtributosById(estrutura.getSisAtributoSatbEttSuperior());
				}	

			} 
			
			
		} else {
			
			//se a estrutura de nivel superior nao foi selecionada
			estrutura = new EstruturaEtt();
			atributosPermitidosDadosGerais = estruturaDao.getAtributosPermitidos(null, funcaoDadosGerais);
			atributosPermitidosPontosCriticos = estruturaDao.getAtributosPermitidos(null, funcaoPontosCriticos);
			funcoesPermitidas = estruturaDao.getFuncoesPermitidas(null);
		}

		

		
		
		
		
	//	String _pesqdisabled ="disabled";
		
	//	List listaAtributoEstrutura = estruturaAtributoDao.getAtributosVisualizarEttSuperior(null, funcaoDadosGerais);
	//	List atributosEttSuperior = new ArrayList();
	//	List lAtributosEttSuperior = new ArrayList();
		
	//	String selectedAtributo = Pagina.getParamStr(request, "codAtbExibirEstruturaEtt");
		
	/*	String codEstruturaPaiAnterior = "";
		if(Pagina.getParamStr(request,"codEstruturaEttAnterior").equals("")) {
			codEstruturaPaiAnterior = Pagina.getParamStr(request,"estruturaEttPai");
		} else {
			codEstruturaPaiAnterior = Pagina.getParamStr(request,"codEstruturaEttAnterior");
		}
		
		if(!"".equals(Pagina.getParamStr(request,"estruturaEttPai"))){
			EstruturaEtt estruturaPai = (EstruturaEtt) estruturaDao.buscar(EstruturaEtt.class, Long.valueOf(Pagina.getParam(request,"estruturaEttPai")));
			listaAtributoEstrutura = estruturaAtributoDao.getAtributosVisualizarEttSuperior(estruturaPai, funcaoDadosGerais);
			selected = Pagina.getParamStr(request,"estruturaEttPai");
			
			if(!codEstruturaPaiAnterior.equals(selected)) {
				selectedAtributo = "";
				codEstruturaPaiAnterior = Pagina.getParamStr(request,"estruturaEttPai");
			}
			if(selectedAtributo != null && !selectedAtributo.equals("")) {
				AtributosAtb atributoSelecionado = (AtributosAtb) estruturaDao.buscar(AtributosAtb.class, Long.valueOf(selectedAtributo));
				if(atributoSelecionado.getSisGrupoAtributoSga().getSisAtributoSatbs() != null) {
					atributosEttSuperior = new ArrayList(atributoSelecionado.getSisGrupoAtributoSga().getSisAtributoSatbs());
				}
			}
		} else {
			if(selectedAtributo != null && !selectedAtributo.equals("")) {
				AtributosAtb atributoSelecionado = (AtributosAtb) estruturaDao.buscar(AtributosAtb.class, Long.valueOf(selectedAtributo));
				if(atributoSelecionado.getSisGrupoAtributoSga().getSisAtributoSatbs() != null) {
					atributosEttSuperior = new ArrayList(atributoSelecionado.getSisGrupoAtributoSga().getSisAtributoSatbs());
				}
			}
		}*/
%>			
			<%@ include file="form.jsp"%>
		
			<util:barrabotoes pesquisar="Pesquisar" cancelar="Cancelar"/>	
<%
	} catch (ECARException e) {
		org.apache.log4j.Logger.getLogger(this.getClass()).error(e);
		/* redireciona para o controle */
		%>
		<script language="javascript">
		document.form.action = "ctrl.jsp";
		document.form.submit();
		</script>
		<%
	} catch (Exception e){
	%>
		<%@ include file="/excecao.jsp"%>
	<%
	}
%>
</form>
</div>
</body>
<%@ include file="/include/mensagemAlert.jsp"%>
</html>