<%@ page import="ecar.dao.TipoAcompanhamentoDao"%>
<%@ page import="ecar.dao.AbaDao"%>
<%@ page import="ecar.pojo.TipoAcompanhamentoTa"%>
<% 

String strCodArisControleAux = "";
String formaVisualizacao =  Pagina.getParamStr(request, "hidFormaVisualizacaoEscolhida");	
String usuarioClicouAba =  Pagina.getParamStr(request, "clicouAba");
String tipoPadraoExibicaoAba =  Pagina.getParamStr(request, "tipoPadraoExibicaoAba");
Boolean exibirAbasAcompanhamento = Boolean.TRUE;
String hidAcao = Pagina.getParamStr(request, "hidAcao");
	
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


usuario = ((ecar.login.SegurancaECAR)session.getAttribute("seguranca")).getUsuario();

// essa variavel é utilizada no include do cabecalho das paginas
String telaAnterior =  _pathEcar + "/acompanhamento/posicaoGeral.jsp?codTipoAcompanhamento="+codTipoAcompanhamento 
				+"&mesReferencia="+ ari.getAcompReferenciaAref().getCodAref().toString()
				+"&periodo=" + Pagina.getParamStr(request, "periodo")
				+"&hidFormaVisualizacaoEscolhida="+Pagina.getParamStr(request, "hidFormaVisualizacaoEscolhida");
// essa variavel eh apenas utilizada no include do titulo
String tituloMonitoramento = "REGISTRO";
// essa variavel eh apenas utilizada no include do cabecalho da tela
boolean exibeCombo = false;

String itemDoNivelClicadoVoltar = ari.getItemEstruturaIett().getCodIett().toString();

String visaoDescricao = null;
%>
<%@ include file="tituloMonitoramento.jsp" %>
<%		
	if("".equals(codTipoAcompanhamento)) {
%>
			
<%@page import="ecar.dao.TipoAcompanhamentoDao"%><table width="100%" border="0" cellpadding="0" cellspacing="0">
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




		<input type="hidden" name="hidFormaVisualizacaoEscolhida" value='<%=Pagina.getParamStr(request, "hidFormaVisualizacaoEscolhida")%>'>
			
		
		<%  %>

		<util:barraLinksRegAcomp
			acompReferenciaItem="<%=ari%>"  
			selectedFuncao="<%=funcaoSelecionada%>"
			primeiroAcomp="<%=codTipoAcompanhamento%>"
			request="<%=request%>"
			usuario="<%=usuario%>"
			tela="acompanhamento"
			gruposUsuario="<%=seguranca.getGruposAcesso() %>"
			abaSuperior="<%= Dominios.SIM %>"
			listaGeral="<%=telaAnterior %>"
			telaDoItem = "R"
			caminho="<%=_pathEcar%>"
		/> 
		
		
		<%@ include file="botoesAvancaRetrocede.jsp" %>
		<!-- o cabecalho da tela -->
		<%@ include file="cabecalhoTelaItem.jsp" %>	
		
	
		
		<util:barraLinksRegAcomp
			acompReferenciaItem="<%=ari%>"  
			selectedFuncao="<%=funcaoSelecionada%>"
			primeiroAcomp="<%=codTipoAcompanhamento%>"
			request="<%=request%>"
			usuario="<%=usuario%>"
			tela="acompanhamento"
			gruposUsuario="<%=seguranca.getGruposAcesso() %>"
			caminho="<%=_pathEcar%>"
		/> 
		<br>
		
<input type="hidden" name="tipoPadraoExibicaoAba" value="<%= Pagina.getParamStr(request, "tipoPadraoExibicaoAba")%>">		
<input type="hidden" name="semInformacaoNivelPlanejamento" value="<%=Pagina.getParamStr(request, "semInformacaoNivelPlanejamento")%>">		
