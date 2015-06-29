<util:msgObrigatorio obrigatorio="<%=_obrigatorio%>" />
<jsp:directive.page import="ecar.pojo.ConfiguracaoCfg"/>
<jsp:directive.page import="ecar.pojo.SisGrupoAtributoSga"/>
<jsp:directive.page import="ecar.dao.PontoCriticoDao"/>
<jsp:directive.page import="ecar.pojo.PontoCriticoPtc"/>
<jsp:directive.page import="java.util.List"/>
<jsp:directive.page import="ecar.dao.ConfiguracaoDao"/>
<jsp:directive.page import="java.util.Iterator"/>
<jsp:directive.page import="ecar.pojo.ObjetoEstrutura"/>	

	<!-- Campo necessario para guardar a forma de visualização escolhida no filtro -->
	<input type="hidden" name="hidFormaVisualizacaoEscolhida" value='<%=Pagina.getParamStr(request, "hidFormaVisualizacaoEscolhida")%>'>
	<!--  -->
	<input type="hidden" name="mesReferencia" value="<%=ari.getAcompReferenciaAref().getCodAref().toString()%>">
	<input type="hidden" name="codTipoAcompanhamento" value="<%=Pagina.getParamStr(request, "codTipoAcompanhamento")%>">
	<input type="hidden" name="niveisPlanejamento" value="<%=Pagina.getParamStr(request, "niveisPlanejamento")%>">
	<input type="hidden" name="primeiroIettClicado" value="<%=Pagina.getParamStr(request, "primeiroIettClicado")%>">
	<input type="hidden" name="primeiroAriClicado" value="<%=Pagina.getParamStr(request, "primeiroAriClicado")%>">
	<input type="hidden" name="codIettRelacao" value="<%=Pagina.getParamStr(request, "codIettRelacao")%>">
	<input type="hidden" name="itemDoNivelClicado" value="<%=Pagina.getParamStr(request, "itemDoNivelClicado")%>">
	<input type="hidden" name="codigosItem" value="<%=Pagina.getParamStr(request, "codigosItem")%>">
	<!-- campo hidden para nao perder variaveis necessarias ao botao avançar e retroceder e o link voltar -->
	<input type="hidden" name="codArisControle" value="<%=Pagina.getParamStr(request, "codArisControle")%>">
	<input type="hidden" name="codArisControleVisualizacao" value="<%=Pagina.getParamStr(request, "codArisControleVisualizacao")%>">			
	<input type="hidden" name="clicouAba" value="<%= Pagina.getParamStr(request, "clicouAba")%>">
	<input type="hidden" name="codAri"        value="<%=Pagina.getParamStr(request, "codAri")%>">
	<input type="hidden" name="primeiroAcomp" value="<%=Pagina.getParamStr(request, "primeiroAcomp")%>">
	<input type="hidden" name="codAcomp"      value="<%=Pagina.getParamStr(request, "codAcomp")%>">
	<input type="hidden" name="hidAcao"       value="<%=Pagina.getParamStr(request, "hidAcao")%>">
	<input type="hidden" name="codIett"       value="<%=ari.getItemEstruturaIett().getCodIett()%>">
	<input type="hidden" name="codIetta"      value="<%=Pagina.getParamStr(request, "codIetta")%>">
	<input type="hidden" name="codAba"        value="<%=Pagina.getParamStr(request, "codAba")%>">
	<input type="hidden" name="labelEttf"     value="<%=estruturaFuncao.getLabelEttf()%>">
	<input type="hidden" name="Usuario"       value="<%=usuario.getCodUsu().toString()%>">
	<%String cod = Pagina.getParamStr(request, "cod"); %>
	<input type="hidden" name="cod"        value="<%=cod%>">
	<input type="hidden" name="periodo" value="<%=Pagina.getParamStr(request, "periodo")%>">
	<input type="hidden" name="periodo" 	  value="<%=Pagina.getParamStr(request, "periodo")%>">
	<input type="hidden" name="referencia_hidden" value="<%=Pagina.getParamStr(request, "referencia_hidden")%>">
	
	<input type="hidden" name="tipoPadraoExibicaoAba" value="<%= Pagina.getParamStr(request, "tipoPadraoExibicaoAba")%>">
	
	<%
	// ConfiguracaoCfg configuracao = new ConfiguracaoDao(request).getConfiguracao();
	// SisGrupoAtributoSga grupoAssunto = configuracao.getSisGrupoAtributoSgaTipoPontoCritico(); 
	
	PontoCriticoDao pontoCriticoDao = new PontoCriticoDao(request);
	PontoCriticoPtc pontoCritico = new PontoCriticoPtc();
	
	
	 
	if (!"".equals(cod)){
		pontoCritico = (PontoCriticoPtc) new PontoCriticoDao(request).buscar(PontoCriticoPtc.class, Long.valueOf(cod));
	} 
	 
	List pontosCriticos = null;
	pontoCritico.setUsuarioUsuInclusao(usuario);
	
	Boolean abaPontoCriticoDeAcompanhamento = new Boolean(true);
	//pontoCritico.setAcompRelatorioArel(acompanhamento);
	%>
		
	<%
	
	if(atributos != null){
		Iterator it = atributos.iterator();
		while(it.hasNext()){
			ObjetoEstrutura atributo = (ObjetoEstrutura) it.next();
			%>
				<util:formPontoCriticoTag 
					atributo="<%=atributo%>" 
					pontoCriticoPtc="<%=pontoCritico%>"
					iett="<%=itemEstrutura%>" 
					desabilitar="<%=_disabled.equals("disabled") ? Boolean.TRUE: Boolean.FALSE%>" 
					seguranca="<%=seguranca%>" 
					exibirBotoes="<%=Boolean.TRUE%>"
					request="<%=request%>"
					acompReferenciaItemAri="<%=ari%>"
					contextPath="<%=request.getContextPath()%>"
					novoPontoCritico="<%= novoPontoCritico %>"
				/>	
			<%
		} 
	}
	%>
		
		

<%-- span id="listaApontamentos" style="display:none">

	<  %  @ include file="listaApontamentos.jsp"   %  >

</span--%>

		
		

	<%  /* Controla o estado do Menu (aberto ou fechado) */%>
	<%@ include file="/include/estadoMenu.jsp"%>
	
	