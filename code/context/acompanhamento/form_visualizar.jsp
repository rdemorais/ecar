<%@ page import="ecar.dao.TipoAcompanhamentoDao"%>
<%@ page import="ecar.dao.AbaDao"%>
<%@ page import="ecar.pojo.TipoAcompanhamentoTa"%>
<% 
String strCodArisControleAux = "";
String formaVisualizacao =  Pagina.getParamStr(request, "hidFormaVisualizacaoEscolhida");
String hidAcao = Pagina.getParamStr(request, "hidAcao");
String codRegdTemp =  Pagina.getParamStr(request, "codRegd");
String usuarioClicouAba =  Pagina.getParamStr(request, "clicouAba");
String tipoPadraoExibicaoAba =  Pagina.getParamStr(request, "tipoPadraoExibicaoAba");
Boolean exibirAbasAcompanhamento = Boolean.TRUE;
	
if(formaVisualizacao.equalsIgnoreCase("personalizado")) {
	exibirAbasAcompanhamento= Boolean.FALSE;
} 	

String paginaBtAvancRetroceder = _pathEcar + "/acompanhamento/resumo/detalharItem.jsp";

//CODIGO ADICIONADO PARA GERENCIAMENTO DE BOTÃO AVANÇAR/RETROCEDER - SABE-SE SE O USUARIO CLICOU OU NÃO NA ABA E A PARTIR DAÍ DEFINISSE O ENDEREÇO DA PAGINA PARA RETROCEDER/AVANAÇAR
TipoAcompanhamentoDao taDaoFormRegistro = new TipoAcompanhamentoDao(request);
TipoAcompanhamentoTa tipoAcompanhamentoFormRegistro = (TipoAcompanhamentoTa) taDaoFormRegistro.buscar(TipoAcompanhamentoTa.class, Long.valueOf(codTipoAcompanhamento));
AbaDao abaDaoFormRegistro = new AbaDao(request);
String enderecoAbaVisualizacaoFormRegistro = null;
if (usuarioClicouAba!=null && usuarioClicouAba.equals("S")) {
	enderecoAbaVisualizacaoFormRegistro = abaDaoFormRegistro.pesquisaEnderecoAbaRegistro(tipoAcompanhamentoFormRegistro, seguranca.getGruposAcesso(), Integer.parseInt(tipoPadraoExibicaoAba), funcaoSelecionada);
} else {
	enderecoAbaVisualizacaoFormRegistro = abaDaoFormRegistro.pesquisaEnderecoAbaRegistro(tipoAcompanhamentoFormRegistro, seguranca.getGruposAcesso(), Integer.parseInt(tipoPadraoExibicaoAba), null);
}
paginaBtAvancRetroceder = _pathEcar + "/" + enderecoAbaVisualizacaoFormRegistro;

String telaAnterior =  _pathEcar + "/acompanhamento/posicaoGeral.jsp?codTipoAcompanhamento="+codTipoAcompanhamento +"&mesReferencia="+mesReferencia + "&periodo=" + periodo + "&periodo=" + Pagina.getParamStr(request, "periodo")+"&hidFormaVisualizacaoEscolhida="+Pagina.getParamStr(request, "hidFormaVisualizacaoEscolhida")+"&hidAcao="+Pagina.getParamStr(request, "hidAcao");

String tituloMonitoramento = "VISUALIZAÇÃO";

AcompReferenciaDao acompReferenciaDaoCabecalho = new AcompReferenciaDao(request);
AcompReferenciaAref arefSelecionadoCabecalho = acompReferenciaItem.getAcompReferenciaAref();
TipoAcompanhamentoTa tipoAcompCabecalho = arefSelecionadoCabecalho.getTipoAcompanhamentoTa();


StringBuffer opcaoCombo = new StringBuffer(arefSelecionadoCabecalho.getDiaAref())
										.append("/").append(arefSelecionadoCabecalho.getMesAref())
										.append("/").append(arefSelecionadoCabecalho.getAnoAref());

if(tipoAcompCabecalho!= null && tipoAcompCabecalho.getIndSepararOrgaoTa() != null && tipoAcompCabecalho.getIndSepararOrgaoTa().equals("S") &&
		acompReferenciaDaoCabecalho.getListaMesmaReferenciaDiaMesAno(arefSelecionadoCabecalho).size() > 1){
	opcaoCombo.append(" - " + ConstantesECAR.LABEL_ORGAO_CONSOLIDADO);
} else {
	opcaoCombo.append(" - ").append(arefSelecionadoCabecalho.getNomeAref());
}
                    

String visaoDescricao = null; 
if ((request.getSession().getAttribute(VisaoDemandasVisDem.VISAO_SELECIONADA)) != null){
	visaoDescricao = ((VisaoDemandasVisDem)request.getSession().getAttribute(VisaoDemandasVisDem.VISAO_SELECIONADA)).getDescricaoVisao();
}
%>		


<%@page import="ecar.pojo.VisaoDemandasVisDem"%>
<%@page import="comum.util.ConstantesECAR"%><input type="hidden" id="hidFormaVisualizacaoEscolhida" name="hidFormaVisualizacaoEscolhida" value="<%=formaVisualizacao %>">

<%@ include file="tituloMonitoramento_visao.jsp" %>
<%		
	if("".equals(codTipoAcompanhamento)) {
%>
			<table width="100%" border="0" cellpadding="0" cellspacing="0">
				<tr class="linha_subtitulo" align="left">
					<td>
						<%=_msg.getMensagem("tipoAcompanhamento.ativo.comAref.nenhumRegistro")%>
					</td>
				</tr>
			</table>

<%
	} else {
		
	
%>

			<util:barraLinkTipoAcompanhamentoTag
				codTipoAcompanhamentoSelecionado="<%=codTipoAcompanhamento%>"
				exibirAbasAcompanhamento="<%=exibirAbasAcompanhamento%>" 
				chamarPagina = "posicaoGeral.jsp"
				caminho="<%=_pathEcar%>"
				gruposUsuario= "<%=seguranca.getGruposAcesso()%>"
				request = "<%=request%>"
                hidAcao = "<%=hidAcao%>"
			/>
<% } %>			

		
	
	
	<util:barraLinksRelatorioAcompanhamento
		acompReferenciaItem="<%=acompReferenciaItem%>"  
		funcaoSelecionada="<%=funcaoSelecionada%>"
		primeiroIettClicado="<%=request.getParameter("primeiroIettClicado")%>"
		primeiroAriClicado="<%=request.getParameter("primeiroAriClicado")%>"
		caminho="<%=_pathEcar + "/relAcompanhamento/"%>"
		codTipoAcompanhamento="<%=Pagina.getParamStr(request, "codTipoAcompanhamento")%>"
		gruposUsuario="<%=seguranca.getGruposAcesso()%>"
		abaSuperior ="S" 
		contextPath="<%=_pathEcar%>"
		listaGeral="posicaoGeral.jsp"
		codRegd="<%=codRegdTemp%>"
	/> 
	

	<%@ include file="relAcompanhamento/botoesAvancaRetrocede.jsp" %>
	
	<%
		String telaAnt = "";

		if(acompReferenciaItem.getItemEstruturaIett() != null ){
            telaAnt = _pathEcar + "/acompanhamento/posicaoGeral.jsp?fecharMenu=true&titulo=Geral&codTipoAcompanhamento=" + Pagina.getParamStr(request, "codTipoAcompanhamento") + "&mesReferencia="+acompReferenciaItem.getAcompReferenciaAref().getCodAref()  + "&periodo=" + Pagina.getParamStr(request, "periodo") +"&hidAcao="+Pagina.getParamStr(request, "hidAcao")+"&hidFormaVisualizacaoEscolhida="+Pagina.getParamStr(request, "hidFormaVisualizacaoEscolhida")+  "#ancora" + acompReferenciaItem.getItemEstruturaIett().getCodIett();
        }
        
	%>

	<util:arvoreEstruturas 
		itemEstrutura="<%=itemEstrutura%>" 
		exibirLinks="false" 
		proximoNivel="false" 
		contextPath="<%=_pathEcar%>"
		primeiroIettClicado="<%=Pagina.getParamStr(request, "primeiroIettClicado")%>"
		primeiroAriClicado="<%=request.getParameter("primeiroAriClicado")%>"
		telaAnterior="<%=telaAnt%>"		
		codTipoAcompanhamentoSelecionado="<%=Pagina.getParamStr(request, "codTipoAcompanhamento")%>"
		
	/> 
	
						
	

	<table border="0" width="100%">
		<tr>
			<td valign="bottom" class="texto" align="right">
					<b>Ciclo	 de refer&ecirc;ncia: </b>
					<%out.println(opcaoCombo);%>
				</td>
			</tr>
	</table>
	
	<util:barraLinksRelatorioAcompanhamento
		acompReferenciaItem="<%=acompReferenciaItem%>"  
		funcaoSelecionada="<%=funcaoSelecionada%>"
		primeiroIettClicado="<%=request.getParameter("primeiroIettClicado")%>"
		primeiroAriClicado="<%=request.getParameter("primeiroAriClicado")%>"
		caminho="<%=_pathEcar + "/acompanhamento/relAcompanhamento/"%>"
		codTipoAcompanhamento="<%=Pagina.getParamStr(request, "codTipoAcompanhamento")%>"
		gruposUsuario="<%=seguranca.getGruposAcesso()%>"
		contextPath="<%=_pathEcar%>"
		codRegd="<%=codRegdTemp%>"
	/> 
	
	<br>


<input type="hidden" name="semInformacaoNivelPlanejamento" value="<%=Pagina.getParamStr(request, "semInformacaoNivelPlanejamento")%>">
<input type="hidden" name="hidAcao" value="<%=Pagina.getParamStr(request, "hidAcao")%>">