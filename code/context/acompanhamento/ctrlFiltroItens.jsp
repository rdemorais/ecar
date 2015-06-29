<%@ include file="/login/validaAcesso.jsp"%>

<%@ page import="ecar.dao.AcompReferenciaItemDao"%>

<%@ page import="java.util.Iterator" %> 
<%@ page import="java.util.List" %> 
<%@ page import="ecar.dao.CorDao" %>
<%@ page import="ecar.pojo.Cor" %>
<%@ page import="ecar.dao.TipoFuncAcompDao"%>
<%@ page import="ecar.pojo.TipoFuncAcompTpfa"%>
<%@ page import="ecar.dao.AbaDao"%>
<%@ page import="ecar.dao.SisGrupoAtributoDao"%>
<%@ page import="ecar.dao.ConfiguracaoDao"%>
<%@ page import="ecar.pojo.SisAtributoSatb"%>
<%@ page import="ecar.dao.SisAtributoDao"%>
<%@ page import="ecar.util.Dominios" %>
<%@ page import="ecar.pojo.OrgaoOrg"%>
<%@ page import="ecar.dao.OrgaoDao"%>
<jsp:directive.page import="java.util.Collection"/>
<jsp:directive.page import="ecar.dao.AcompReferenciaDao"/>
<jsp:directive.page import="gov.pr.celepar.sentinela.client.pojo.Usuario"/>
<jsp:directive.page import="ecar.pojo.UsuarioUsu"/>
<jsp:directive.page import="ecar.dao.UsuarioDao"/>
<jsp:directive.page import="ecar.pojo.UsuarioAtributoUsua"/>
<jsp:directive.page import="java.util.ArrayList"/>
<%@ include file="/../frm_global.jsp"%>
<%@ page import="comum.util.Pagina" %>


<html lang="pt-br">
<head>

<%@ include file="/../include/meta.jsp"%>
<%@ include file="/../titulo.jsp"%>
</head>
<body>

<%@ include file="/../cabecalho.jsp" %>
<%@ include file="/../divmenu.jsp"%> 

<form name="form" method="post">
    <!-- campo de controle para a navegação de registros -->
	
	<!-- campo de controle para mensagens -->
	<input type="hidden" name="msgOperacao" value=""> 
	<input type="hidden" name="msgPesquisa" value="">	
	<input type="hidden" name="hidPesquisou" value="">			
</form>

<%

	String submit = "posicaoGeral.jsp";
	
	String codTipoAcompanhamentoEscolhido = 
	Pagina.getParamStr(request, "codTipoAcompanhamento");

		
	//ciclo referência
	
	String referenciaEscolhida = Pagina.getParamStr(request, "mesReferencia");
	
	
	//ciclo exibição
	
	String periodoEscolhido = Pagina.getParamStr(request, "periodo");

	try{

		AcompReferenciaItemDao acompReferenciaItemDao = new AcompReferenciaItemDao(request);
	
		String hidAcao = Pagina.getParam(request, "hidAcao");
		String msg = null;
				
		//cor		
			
		CorDao corDao = new CorDao(request);
		String[] cores  = request.getParameterValues("cor");
			        Collection lCoresSelecionadas = new ArrayList();
			       
			        if (cores != null) {
			        	for (int i = 0; i < cores.length; i++) {
			            	Cor cor = (Cor) corDao.buscar(Cor.class, Long.valueOf(cores[i]));
			                lCoresSelecionadas.add(cor);
			        }
		}
		
		//tipo função de acompanhamento
		
		TipoFuncAcompDao tipoFuncAcompDao = new TipoFuncAcompDao(request);
		
		String[] tipoFuncoesAcompanhamento  = request.getParameterValues("tipoFuncAcompTpfa");
					Collection lFuncoesAcompanhamentoSelecionadas = new ArrayList();
					
					if (tipoFuncoesAcompanhamento != null) {
			        	for (int i = 0; i < tipoFuncoesAcompanhamento.length; i++) {
			            	TipoFuncAcompTpfa tipoFuncAcompTpfa = (TipoFuncAcompTpfa) tipoFuncAcompDao.buscar(TipoFuncAcompTpfa.class, Long.valueOf(tipoFuncoesAcompanhamento[i]));
			                lFuncoesAcompanhamentoSelecionadas.add(tipoFuncAcompTpfa);
			            }
			        }
		
		
		//pega os ciclos considerados de acordo com o periodo de referencia e a quantidade de periodos
		//anteriores a esta referencia (periodos exibição)
		
		AcompReferenciaDao acompReferenciaDao = new AcompReferenciaDao(request);
		
		Long codArefReferencia;
		int qtdePeriodosAnteriores;
		/* Busca coleção com ciclos a serem considereados */
		Collection periodosConsiderados = new ArrayList();
		
		if (referenciaEscolhida != null && referenciaEscolhida != ""){
			codArefReferencia= Long.valueOf(referenciaEscolhida);
			qtdePeriodosAnteriores = Integer.valueOf(periodoEscolhido).intValue();
			
			if(codArefReferencia.intValue() > 0) {
			periodosConsiderados = acompReferenciaDao.getPeriodosAnteriores(codArefReferencia, qtdePeriodosAnteriores,  Long.valueOf(codTipoAcompanhamentoEscolhido));
			}
		
			if(qtdePeriodosAnteriores > 0) {
				periodosConsiderados = acompReferenciaDao.getPeriodosAnteriores(codArefReferencia, qtdePeriodosAnteriores,  Long.valueOf(codTipoAcompanhamentoEscolhido));
			}
			
		}
				
			
		//Pega Usuário da Sessão
		UsuarioUsu usuario = ((ecar.login.SegurancaECAR)session.getAttribute("seguranca")).getUsuario();
		
		//Niveis de planejamento do usuário
		UsuarioDao usuarioDao = new UsuarioDao(request);
		
		List niveisUsuario = usuarioDao.getNiveisPlanejamentoUsuario(usuario);
		Iterator itNiveisUsu = niveisUsuario.iterator();
		StringBuffer strAux = new StringBuffer("");
		
		while(itNiveisUsu.hasNext()) {
			UsuarioAtributoUsua usuAtributo = (UsuarioAtributoUsua) itNiveisUsu.next();
			strAux.append(usuAtributo.getSisAtributoSatb().getCodSatb()); 
			strAux.append(":");
		}
		String strNiveisPlanejamentoUsuario = strAux.toString();
		
		
		String niveisPlanejamento = Pagina.getParamStr(request, "niveisPlanejamento");
		AbaDao abaDao = new AbaDao(request);
		//ConfiguracaoDao configuracaoDao = new ConfiguracaoDao(request);
		OrgaoDao orgaoDao = new OrgaoDao(request);
		SisAtributoDao sisAtributoDao = new SisAtributoDao(request);
		
		//Seta o "orgaoResponsavel" com o valor do request
		OrgaoOrg orgaoResponsavel = new OrgaoOrg();
		if(!"".equals(Pagina.getParamStr(request, "orgaoResponsavel"))){
			orgaoResponsavel = (OrgaoOrg) orgaoDao.buscar(OrgaoOrg.class, Long.valueOf( Pagina.getParamStr(request, "orgaoResponsavel")));
		}
		
		// Seta "niveisPlanejamento" com os códigos do sisAtributo separados por ":" ex.:(64:59:), isso por usuário
		/* Verifica a exibição do filtro Nível Planejamento se não mostrar selecionar TODOS */
		if (abaDao.verificaNivelPlanejamento("P")) {
			if("".equals(niveisPlanejamento)) {
				niveisPlanejamento = strNiveisPlanejamentoUsuario;
			}
		} else {
			String strAux2 = "";
			List listNiveisPlanejamento = new SisGrupoAtributoDao().getAtributosOrdenados(configuracao.getSisGrupoAtributoSgaByCodSgaGrAtrNvPlan());
			Iterator it = listNiveisPlanejamento.iterator();
			while(it.hasNext()){
				SisAtributoSatb nivel = (SisAtributoSatb) it.next();
				
				strAux2 += nivel.getCodSatb() + ":";
			}
			if("".equals(niveisPlanejamento)){
				niveisPlanejamento = strAux2.toString();
			}
		}
		
		String semInformacaoNivelPlanejamento = Pagina.getParamStr(request, "semInformacaoNivelPlanejamento");
		Boolean isSemInformacaoNivelPlanejamento = new Boolean(false);
		if((Dominios.SIM).equals(semInformacaoNivelPlanejamento) || !abaDao.verificaNivelPlanejamento("P")) {
			isSemInformacaoNivelPlanejamento = new Boolean(true);
		}
		
		List listNiveis = new ArrayList();
		String[] niveis = niveisPlanejamento.split(":");
		for(int n = 0; n < niveis.length; n++){
			String codNivel = niveis[n];					
			if(!"".equals(codNivel)){
				listNiveis.add(sisAtributoDao.buscar(SisAtributoSatb.class, Long.valueOf(codNivel)));
			}
		}
	
	
		//PESQUISA
		if (hidAcao.equalsIgnoreCase("pesquisar")){
		
		/* Recupera lista de itens para os periodos considerados por orgão responsavel */
			Object itens[] = 
			acompReferenciaItemDao.getItensAcompanhamentoFiltroItens(
							new Long(codTipoAcompanhamentoEscolhido),
							periodosConsiderados,
							lCoresSelecionadas,
							lFuncoesAcompanhamentoSelecionadas,
							usuario,
							seguranca.getGruposAcesso(),
							listNiveis,
							isSemInformacaoNivelPlanejamento,
							orgaoResponsavel,
							request //MODIFICAR POR LISTA DE ESTRUTURA ATRIBUTOS FILTROS
							);
			
			session.setAttribute("listaItensEstrutura", itens);
												
		}
	
	
		} catch (ECARException e) {
		org.apache.log4j.Logger.getLogger(this.getClass()).error(e);
		Mensagem.alert(_jspx_page_context, e.getMessageKey());
		} catch (Exception e){
	%>
		<%@ include file="/excecao.jsp"%>
	<%
		}	
	%>

<script language="javascript">
		document.form.action = "<%=submit + "?codTipoAcompanhamento="
   								+codTipoAcompanhamentoEscolhido+"&periodo="+periodoEscolhido+"&codReferencia="+referenciaEscolhida%>";
		document.form.submit();
</script> 

</body>
</html>