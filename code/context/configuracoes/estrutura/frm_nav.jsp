
<jsp:directive.page import="ecar.dao.FuncaoDao"/>
<jsp:directive.page import="ecar.pojo.FuncaoFun"/><%@ include file="/login/validaAcesso.jsp"%>
<%@ include file="/frm_global.jsp"%>

<%@ taglib uri="/WEB-INF/ecar-util.tld" prefix="util" %>
<%@ taglib uri="/WEB-INF/ecar-combo.tld" prefix="combo" %>
<%@ page import="ecar.pojo.EstruturaEtt" %>
<%@ page import="ecar.pojo.AtributosAtb"%>
<%@ page import="ecar.dao.EstruturaDao" %>
<%@ page import="ecar.dao.EstruturaAtributoDao"%>
<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %>


<%@page import="ecar.pojo.SisAtributoSatb"%><html lang="pt-br">
<head>
<%@ include file="/include/meta.jsp"%>
<%@ include file="/titulo.jsp"%>
<link rel="stylesheet" href="<%=_pathEcar%>/css/style_<%=configuracaoCfg.getEstilo().getNome()%>.css" type="text/css">
<script language="javascript" src="<%=_pathEcar%>/js/menu_retratil.js" type="text/javascript"></script>
<script language="JavaScript" src="<%=_pathEcar%>/js/focoInicial.js" type="text/javascript"></script>
<script language="JavaScript" src="<%=_pathEcar%>/js/frmPadrao.js" type="text/javascript"></script>

</head>

<body>

<%@ include file="/cabecalho.jsp" %>
<%@ include file="/divmenu.jsp"%>

<div id="conteudo">

<form name="form" method="post" action="frm_nav.jsp">
<input type="hidden" name="hidAcao" value="">
	
	<!-- TITULO -->
	<%@ include file="/titulo_tela.jsp"%>

<%

boolean ehPesquisa = false;
boolean ehNavegacao = true;
boolean ehInclusao = false;
boolean ehAlteracao = false;

	try {
		EstruturaEtt estrutura = new EstruturaEtt();
		EstruturaDao estruturaDao = new EstruturaDao(request);
		FuncaoDao funcaoDao = new FuncaoDao(request);
		EstruturaAtributoDao estruturaAtributoDao = new EstruturaAtributoDao(request);
		
		FuncaoFun funcaoDadosGerais = funcaoDao.getFuncaoDadosGerais();
		FuncaoFun funcaoPontosCriticos = funcaoDao.getFuncaoPontosCriticos();
		
		_disabled = "disabled";
		_readOnly = "readonly";

		List lista = (List) session.getAttribute("lista");
		
		/* listas para receber atributos e funçõe selecionadas */
		List lAtributosDadosGerais = new ArrayList();
		List lAtributosPontosCriticos = new ArrayList();
		List lFuncoes = new ArrayList();
		List lFuncoesAcompanhamento = new ArrayList();

		List listaAtributoEstrutura = new ArrayList(0);
		List atributosEttSuperior = new ArrayList();
		List lAtributosEttSuperior = new ArrayList();
		List atributosEttSuperiorEditaveis = new ArrayList();
		List lAtributosEttSuperiorEditaveis = new ArrayList();
		
		String selectedAtributo = "";
		
		boolean alteraNivel = false;
		
		int i = 0;
		int hidTotRegistro = lista.size(); //Total de Registros
		int hidRegistro = Pagina.getParamInt(request, "hidRegistro"); //Registro atual
		
		//Se o último registro for excluído deve apontar para a última posição.
		if (hidRegistro >= hidTotRegistro)
			hidRegistro = hidTotRegistro - 1;
		
		estrutura = (EstruturaEtt) lista.get(hidRegistro);
		estrutura = (EstruturaEtt)estruturaDao.buscar(EstruturaEtt.class, estrutura.getCodEtt());
		
		List atributosPermitidosDadosGerais = new ArrayList();
		List atributosPermitidosPontosCriticos = new ArrayList();
		List funcoesPermitidas = new ArrayList();
				
		String selected = "";
		EstruturaEtt estruturaPai = null;
		AtributosAtb atributoSelecionado = null;
		
		if(estrutura != null) {
			
			// verifica se pode altarar o nivel ou nao
			if ((estrutura.getItemEstruturaIetts() != null) && (estrutura.getItemEstruturaIetts().size() > 0)) {
				alteraNivel = false;
			//	permiteAlterarEstrutura = estruturaDao.verificaSeTemItemAtivo(estrutura);
			}
				
			//guarda as funções selecionadas
			lFuncoes = estruturaDao.getFuncoesById(estrutura); 
			//guarda os atributos de dados gerais selecionados
			lAtributosDadosGerais = estruturaDao.getAtributosById(estrutura, funcaoDadosGerais);
			//guarda os atributos de pontos criticos que foram selecionados
			lAtributosPontosCriticos = estruturaDao.getAtributosById(estrutura, funcaoPontosCriticos);
			//guarda as funções de acompanhamento selecionadas
			lFuncoesAcompanhamento =estruturaDao.getFuncoesAcompanhamentoById(estrutura);
			
			// Se a estrutura possui uma estrutura pai	
			if(estrutura.getEstruturaEtt() != null){
				estruturaPai = estrutura.getEstruturaEtt() ;
				//guarda a colecao das funcoes que podem ser selecionadas
				funcoesPermitidas = estruturaDao.getFuncoesPermitidas(estruturaPai);
				//guarda a coleção dos atributos de dados gerais que podem ser selecionados 
				atributosPermitidosDadosGerais = estruturaDao.getAtributosPermitidos(estruturaPai, funcaoDadosGerais);
				//guarda a coleção dos atributos de pontos críticos que podem ser selecionados
				atributosPermitidosPontosCriticos = estruturaDao.getAtributosPermitidos(estrutura, funcaoPontosCriticos);
				//guarda o codigo da estrutura pai
				selected = estruturaPai.getCodEtt().toString();
				//monta a lista de grupos de atributos do nivel superior
				listaAtributoEstrutura = estruturaAtributoDao.getAtributosVisualizarEttSuperior(estruturaPai, funcaoDadosGerais);
				
				// se tiver selecionado um grupo de atributo, recupera o grupo de atributo selecionado
				if (estrutura.getAtributoAtbExibirEstruturaEtt() != null){	
					atributoSelecionado = estrutura.getAtributoAtbExibirEstruturaEtt();
					selectedAtributo = atributoSelecionado.getCodAtb().toString();
					
					Iterator itSisAtributos;
					
					//recupera toda a  lista de atributos do grupo selecionado
					if(atributoSelecionado.getSisGrupoAtributoSga() != null && atributoSelecionado.getSisGrupoAtributoSga().getSisAtributoSatbs() != null){
						itSisAtributos = atributoSelecionado.getSisGrupoAtributoSga().getSisAtributoSatbs().iterator();
					
						while (itSisAtributos.hasNext()){
							SisAtributoSatb sisAtributo = (SisAtributoSatb) itSisAtributos.next();
							
							if (sisAtributo.isAtivo()){
								atributosEttSuperior.add(sisAtributo);
							}
						}
					}
					
					//recupera toda a lista de atributos que foi selecionado na estrutura
					if(estrutura.getSisAtributoSatbEttSuperior() != null){
						lAtributosEttSuperior = estruturaAtributoDao.getAtributosById(estrutura.getSisAtributoSatbEttSuperior());
					}
				}	
			
				
			}else{
				
				//se a estrutura nao possuir uma estrutura pai
				atributosPermitidosDadosGerais = estruturaDao.getAtributosPermitidos(null, funcaoDadosGerais);
				atributosPermitidosPontosCriticos = estruturaDao.getAtributosPermitidos(null, funcaoPontosCriticos);
				funcoesPermitidas = estruturaDao.getFuncoesPermitidas(null);
			}
	
		
		}	
		

		
		String _pesqdisabled ="disabled";
%>
  
		<util:linkstop incluir="frm_inc.jsp" pesquisar="frm_pes.jsp"/>
		<util:barrabotoes navegacao="true" btn1="Alterar" excluir="Excluir" atual="<%=hidRegistro+1%>" total="<%=hidTotRegistro%>"/>
			
		<input type="hidden" name="hidRegistro" value="<%=hidRegistro%>">
		<input type="hidden" name="hidTotRegistro" value="<%=hidTotRegistro%>">
		<input type="hidden" name="codigo" value="<%=estrutura.getCodEtt()%>">
		
		<%@ include file="form.jsp"%>
<%
	} catch (Exception e) {
		org.apache.log4j.Logger.getLogger(this.getClass()).error(e);
		Mensagem.alert(_jspx_page_context, _msg.getMensagem("erro.exception"));
		%>
		<script language="javascript" type="text/javascript">
		document.form.action = "frm_pes.jsp";
		document.form.submit();
		</script>
		<%
	}
%>
	
	<util:barrabotoes navegacao="true" btn1="Alterar" excluir="Excluir"/>
	
</form>

</div>

</body>
<%@ include file="/include/mensagemAlert.jsp"%>
</html>