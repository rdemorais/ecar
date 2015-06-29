<%@ page import="ecar.pojo.ObjetoDemanda" %>
<%@ page import="ecar.dao.AtributoDemandaDao" %>
<%@ page import="ecar.dao.SisGrupoAtributoDao" %>
<%@page  import="ecar.pojo.VisaoDemandasVisDem"%>

<%@page import="ecar.pojo.SitDemandaSitd"%><jsp:directive.page import="ecar.pojo.EstruturaAtributoEttat"/>
<jsp:directive.page import="ecar.pojo.AtributoDemandaAtbdem"/>
<jsp:directive.page import="ecar.util.Dominios"/>

<script language="javascript" src="../../js/limpezaCamposMultivalorados.js"></script>

<%
	/*
	 * arquivo comum para as telas de inclusao, alteracao, exclusao e pesquisa 
	 * observar a variavel _disabled e setar o objeto corretamente pelo construtor
	 * assinalando os campos com os valores iniciais para inclusao
	 */ 
	AtributoDemandaDao atributoDemandaDao = new AtributoDemandaDao(request);

	//faz a consulta das visoes grupos de acesso para saber se o usuario possui permissão para visualizar as demandas de uma visao definida em "visaoParameter"
	int visaoParameter = 0;
	if (request.getSession().getAttribute(VisaoDemandasVisDem.VISAO_SELECIONADA)==null) {
		visaoParameter = Pagina.getParamInt(request, "visao");
		VisaoDemandasVisDem visao = (VisaoDemandasVisDem)visaoDao.buscar(VisaoDemandasVisDem.class, new Long(visaoParameter));
		request.getSession().setAttribute(VisaoDemandasVisDem.VISAO_SELECIONADA, visao);
	} else {
		visaoParameter = ((VisaoDemandasVisDem)request.getSession().getAttribute(VisaoDemandasVisDem.VISAO_SELECIONADA)).getCodVisao().intValue();
	}

	List atributos = atributoDemandaDao.getAtributosDemandaVisaoAtivosOrdenadosPorSequenciaTelaCampo(new Long(visaoParameter));
	 
	 
	//****INICIO****Seta variáveis para o campo "Demanda Relacionada"****
	List listObjetosIgnorado = new ArrayList();
	if(regDemanda.getCodRegd() != null) {
		listObjetosIgnorado.add(regDemanda);
	}

	request.getSession().removeAttribute("objetosIgnorados");
	request.getSession().setAttribute("objetosIgnorados", listObjetosIgnorado);
	
	//****FIM*****Seta variáveis para o campo "Demanda Relacionada"****
	
	//O padrão é desabilitado 
	Boolean desabilitar = new Boolean(true);
	if(_disabled.equals(""))
		desabilitar = new Boolean(false);
	
	//Chama a tag que imprime os atributos
	if(atributos != null){
		Iterator it = atributos.iterator();  
		
		//Campos de ordenação da listagem  %>				
		<input type="hidden" name="clCampo" value="<%=Pagina.getParam(request, "clCampo")%>">
		<input type="hidden" name="clOrdem" value="<%=Pagina.getParam(request, "clOrdem")%>">
		<input type="hidden" name="refazPesquisa" value="">
		
		
<script language="javascript">
		
		<util:validacaoRegDemanda
			atributos="<%=atributos%>"  
			sisGrupoAtributoDao="<%= new SisGrupoAtributoDao(request)%>" 
			acao="<%=acao%>"
			codigoVisao ="<%=Integer.toString(visaoParameter)%>"	
		/>
		
</script>

		
		<input type="hidden" name="codRegd" value="<%=regDemanda.getCodRegd()%>">
				
		<%	
		
		session.setAttribute("gruposAcesso", seguranca.getGruposAcesso());
		
		
		Boolean desabilitarAux= new Boolean(true);
		
		while(it.hasNext()){
			
			desabilitarAux = desabilitar;
			
			AtributoDemandaAtbdem atbdem = (AtributoDemandaAtbdem) it.next(); 
			
			if (acao.equals("classificar") && 
					
				//órgão solucionador || data limite || status || prioridade
				( atbdem.iGetNome().equals("orgaoOrg") || atbdem.iGetNome().equals("dataLimiteRegd") || 
				atbdem.iGetNome().equals("sitDemandaSitd") ||atbdem.iGetNome().equals("prioridadePrior")
				|| atbdem.iGetGrupoAtributosLivres() != null  ) 	) { //Verifica se é atributo livre
				

				desabilitarAux = new Boolean(false);
				
				
			// se for classificar e for descricao ou observacao na tela de classfica, pode ser editavel
			} else if(acao.equals("classificar") && 
								//Observação
								( atbdem.iGetNome().equals("observacaoRegd") || 
								//Descrição
								atbdem.iGetNome().equals("descricaoRegd")||
								//Municipio/Gov. do Estado
								atbdem.iGetNome().equals("localDemandaLdems") ||
								// Entidades
								atbdem.iGetNome().equals("entidadeDemandaEntds") ||
								//Data de protocolo
								atbdem.iGetNome().equals("dataSolicitacaoRegd") ||
								//Orgao Solucionador
								atbdem.iGetNome().equals("entidadeOrgaoDemandaEntorgds") ||
								//Nome do Solicitante
								atbdem.iGetNome().equals("nomeSolicitanteRegd") )) {
					
				desabilitarAux = new Boolean(false);	
			
				// se acao é diferente de classificarz
			} else if (acao.equals("incluir")){
				
				SitDemandaSitd sitDemanda = new SitDemandaSitd();
				sitDemanda.setIndPrimeiraSituacaoSitd("S");
				
				regDemanda.setSitDemandaSitd(sitDemanda);
				
			} 
			if(!acao.equals("classificar")) {
				
					// se não for editavel o atributo
					if (atbdem.recuperarEditavel(new Long(visaoParameter)).equals("S")) {
						desabilitarAux = new Boolean(false);	
				
					// se for editavel
					} else {
						desabilitarAux = new Boolean(true);
					}
			}
			
			ObjetoDemanda atributo = atbdem; 
			
			
			
			%>
			<util:formRegDemanda 
				atributo="<%=atributo%>" 
				regDemanda="<%=regDemanda%>" 
				desabilitar="<%=desabilitarAux%>" 
				seguranca="<%=seguranca%>" 
				contextPath="<%=request.getContextPath()%>"
				acao="<%=acao%>"
				request="<%=request%>"		
				codigoVisao ="<%=Integer.toString(visaoParameter)%>"	
			/>							
			<%
		} 
	} %>

<% /* Controla o estado do Menu (aberto ou fechado) */%>
<%@ include file="../../include/estadoMenu.jsp"%>