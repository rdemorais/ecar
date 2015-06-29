<jsp:directive.page import="ecar.util.Dominios"/>
<jsp:directive.page import="ecar.pojo.SisAtributoSatb"/>
<jsp:directive.page import="ecar.dao.SisAtributoDao"/>
<jsp:directive.page import="java.util.Date"/>
<jsp:directive.page import="ecar.dao.AbaDao"/>
<jsp:directive.page import="ecar.pojo.Aba"/>
<jsp:directive.page import="ecar.pojo.PontoCriticoPtc"/>
<jsp:directive.page import="ecar.dao.EstruturaFuncaoDao"/>
<%@ include file="/login/validaAcesso.jsp"%>
<%@ include file="/frm_global.jsp"%>
 
<%@ page import="ecar.pojo.AcompReferenciaItemAri" %> 
<%@ page import="ecar.pojo.ItemEstruturaIett" %>
<%@ page import="ecar.pojo.UsuarioUsu" %>
<%@ page import="ecar.pojo.AcompRefItemLimitesArli" %>
<%@ page import="ecar.pojo.TipoFuncAcompTpfa" %>
<%@ page import="ecar.pojo.ItemEstUsutpfuacIettutfa" %>
<%@ page import="ecar.dao.AcompReferenciaItemDao" %>
<%@ page import="ecar.dao.ItemEstruturaDao" %>
<%@ page import="ecar.dao.EstruturaAtributoDao" %>
<%@ page import="ecar.dao.UsuarioDao" %>
<%@ page import="ecar.dao.ItemEstUsutpfuacDao" %>
<%@ page import="comum.util.Util" %>
<%@ page import="comum.util.FileUpload" %>
<%@ page import="comum.util.Data" %>

<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.List" %> 
<%@ page import="java.util.Set" %> 
<%@ page import="java.util.Iterator" %> 

<%@ taglib uri="/WEB-INF/ecar-util.tld" prefix="util" %>
<%@ taglib uri="/WEB-INF/ecar-combo.tld" prefix="combo" %>

<%

try{ 

	// parametros do request
	String codTipoAcompanhamento = Pagina.getParamStr(request, "codTipoAcompanhamento");
	String itemDoNivelClicado = Pagina.getParamStr(request, "itemDoNivelClicado");
	String mesReferencia = Pagina.getParamStr(request, "referencia_hidden");
	
	


	boolean isFormUpload = FileUpload.isMultipartContent(request);
	List campos = new ArrayList();
	if(isFormUpload){
		campos = FileUpload.criaListaCampos(request); 
	}
	
	String codAri = null;
	String primeiroAcomp = null;
	String nivelPrimeiroIettClicadoListaItens = null;
	String codAcomp = null;
	String vemDaPgAcompRelatorio = null;
	
	if (isFormUpload){
		codAri = FileUpload.verificaValorCampo(campos, "codAri");
		primeiroAcomp = FileUpload.verificaValorCampo(campos, "primeiroAcomp");
		nivelPrimeiroIettClicadoListaItens = FileUpload.verificaValorCampo(campos, "nivelIettClicado");
		codAcomp = FileUpload.verificaValorCampo(campos, "codAcomp");
		vemDaPgAcompRelatorio = FileUpload.verificaValorCampo(campos, "vemDaPgAcompRelatorio");
	} else {
		codAri = Pagina.getParamStr(request, "codAri");
		primeiroAcomp = Pagina.getParamStr(request, "primeiroAcomp");
		nivelPrimeiroIettClicadoListaItens = Pagina.getParamStr(request, "nivelIettClicado");
		codAcomp = Pagina.getParamStr(request, "codAcomp");
		vemDaPgAcompRelatorio = Pagina.getParamStr(request, "vemDaPgAcompRelatorio");
	}
	
	UsuarioUsu usuario = ((ecar.login.SegurancaECAR)session.getAttribute("seguranca")).getUsuario();
	
	/* item pai do item que está sendo cadastrado */  
	AcompReferenciaItemDao ariDao = new AcompReferenciaItemDao(request); 
	AcompReferenciaItemAri ari = (AcompReferenciaItemAri) ariDao.buscar(AcompReferenciaItemAri.class, Long.valueOf(codAri));
	AcompReferenciaItemAri acompReferenciaItem  = ari;
	ItemEstruturaIett itemEstrutura = ari.getItemEstruturaIett();
	
	ValidaPermissao validaPermissao = new ValidaPermissao();
	String codArisControleV = Pagina.getParamStr(request, "codArisControleVisualizacao");
	String periodo = Pagina.getParamStr(request, "periodo");	
	String funcaoSelecionada = "GRAFICO_DE_GANTT";
	
	//Pega a aba
	AbaDao abaDao = new AbaDao(request);
	Aba aba = new Aba();
	aba.setNomeAba("GRAFICO_DE_GANTT");
	aba = (Aba)(abaDao.pesquisar(aba, new String[]{"codAba", "asc"}).get(0));
	
	EstruturaFuncaoDao estruturaFuncaoDao = new EstruturaFuncaoDao(request);
	String labelPontosCriticos = estruturaFuncaoDao.getLabelFuncaoPontosCriticos(itemEstrutura.getEstruturaEtt());
	
%>


<%@page import="ecar.dao.PontoCriticoDao"%><html lang="pt-br"> 
<head>
<%@ include file="/../include/meta.jsp"%>
<%@ include file="/titulo.jsp"%>
<link rel="stylesheet" href="<%=_pathEcar%>/css/style_<%=configuracaoCfg.getEstilo().getNome()%>.css" type="text/css">
<script language="javascript" src="<%=_pathEcar%>/js/menu_retratil.js" type="text/javascript"></script>
<script language="javascript" src="<%=_pathEcar%>/js/focoInicial.js" type="text/javascript"></script>
<script language="javascript" src="<%=_pathEcar%>/js/datas.js" type="text/javascript"></script>
<script language="javascript" src="<%=_pathEcar%>/js/mascaraMoeda.js" type="text/javascript"></script>
<script language="javascript" src="<%=_pathEcar%>/js/validacoes.js" type="text/javascript"></script>
 
<script type="text/javascript">
function trocaAbaSemAri(link, codIettRelacao){
	document.form.codIettRelacao.value = codIettRelacao;
	document.form.itemDoNivelClicado.value = codIettRelacao;
	document.form.action = link;
	document.forms[0].clicouAba.value = "S";
	document.form.submit();
}
function recarregar(){
	document.forms[0].action = "graficoGantt.jsp?codArisControleVisualizacao=<%=codArisControleV%>";
	document.forms[0].submit();
}
function trocaAba(link, codAri){
	document.forms[0].codIettRelacao.value = '';
	document.forms[0].codAri.value = codAri;
	document.form.codArisControleVisualizacao.value = "<%=codArisControleV%>";
	document.forms[0].action = link + "&codArisControleVisualizacao=<%=codArisControleV%>";
	document.forms[0].clicouAba.value = "S";
	document.forms[0].submit();
}
function trocaAbaImpressao(link){
	document.forms[0].action = link;
	document.forms[0].clicouAba.value = "S";
	document.forms[0].submit();
}
</script>

</head>
<%@ include file="/cabecalho.jsp" %>
<%@ include file="/divmenu.jsp"%> 

<%

 %>

<body>
<div id="conteudo"> 

<%
if(!"".equals(nivelPrimeiroIettClicadoListaItens)) {
	request.getSession().setAttribute("nivelPrimeiroIettClicado", nivelPrimeiroIettClicadoListaItens);
}
%>


<form  name="form" method="post" action="">

	<%@ include  file="../../form_visualizar.jsp" %>	

	<input type="hidden" name="primeiroAcomp" value="<%=primeiroAcomp%>">
	<input type="hidden" name="codAcomp" value="<%=codAcomp%>">
	<input type="hidden" name="codAcomp" value="<%=codAcomp%>">
	<input type="hidden" name="codUsuario" value="<%=usuario.getCodUsu()%>"> 	
	<input type="hidden" name="botaoClicado" value="">
	<input type="hidden" name="codTipoAcompanhamento" value="<%=codTipoAcompanhamento%>">
	<input type="hidden" name="mesReferencia" value="<%=ari.getAcompReferenciaAref().getCodAref().toString()%>">
	<input type="hidden" name="referencia_hidden" value="<%=Pagina.getParamStr(request, "referencia_hidden")%>">
	<input type="hidden" name="niveisPlanejamento" value="<%=Pagina.getParamStr(request, "niveisPlanejamento")%>">
	<input type="hidden" name="codAri" value="<%=Pagina.getParamStr(request, "codAri")%>">
	<input type="hidden" name="primeiroIettClicado" value="<%=Pagina.getParamStr(request, "primeiroIettClicado")%>">
	<input type="hidden" name="primeiroAriClicado" value="<%=Pagina.getParamStr(request, "primeiroAriClicado")%>">
	<input type="hidden" name="codIettRelacao" value="<%=Pagina.getParamStr(request, "codIettRelacao")%>">
	<input type="hidden" name="itemDoNivelClicado" value="<%=Pagina.getParamStr(request, "itemDoNivelClicado")%>">
	<input type="hidden" name="codigosItem" value="<%=Pagina.getParamStr(request, "codigosItem")%>">
	<input type="hidden" name="periodo" value="<%=Pagina.getParamStr(request, "periodo")%>">
	<!-- Campo necessario para guardar a forma de visualização escolhida no filtro -->
	<input type="hidden" name="hidFormaVisualizacaoEscolhida" value='<%=Pagina.getParamStr(request, "hidFormaVisualizacaoEscolhida")%>'>
	<input type="hidden" name="codArisControleVisualizacao" value="<%=Pagina.getParamStr(request, "codArisControleVisualizacao")%>">
	<input type="hidden" name="clicouAba" value="<%= Pagina.getParamStr(request, "clicouAba")%>">
	<input type="hidden" name="tipoPadraoExibicaoAba" value="<%= Pagina.getParamStr(request, "tipoPadraoExibicaoAba")%>">
	
	<!-- Campo necessário para botões de Avança/Retrocede -->
	<input type="hidden" name="codArisControle" value="<%=Pagina.getParamStr(request, "codArisControle")%>">
	
	<input type="hidden" name="codRegd" value="">


	<table class="barrabotoes" cellpadding="0" cellspacing="0">
		<tr class="linha_titulo"> 
			<td align="left" style="width: 50%">
				<b> <%=abaDao.getLabelAbaEstrutura(aba, ari.getItemEstruturaIett().getEstruturaEtt()) %> </b>
			</td>				
		</tr>
	</table>
	<table name="form" class="form" width="100%">		
		<tr> 
			<td colspan="2">
				<%
				String tipoDataBase = Pagina.getParamStr(request, "tipoDataBase");
				if("".equals(tipoDataBase))
					tipoDataBase = "Atual";
				%>
				<input type="radio" class="form_check_radio" name="tipoDataBase" value="Atual" onclick="recarregar()" <%if("Atual".equals(tipoDataBase)){ out.println("checked");}%>> Data Atual
				<input type="radio" class="form_check_radio" name="tipoDataBase" value="LimiteFisico" onclick="recarregar()" <%if("LimiteFisico".equals(tipoDataBase)){ out.println("checked");}%>> Data Limite para Realizado Físico
				<input type="radio" class="form_check_radio" name="tipoDataBase" value="InicioMonitoramento" onclick="recarregar()" <%if("InicioMonitoramento".equals(tipoDataBase)){ out.println("checked");}%>> Data de Início do Monitoramento								
			</td>
		</tr>
	</table>
	<table name="form" class="form" width="100%">
		<tr><td class="espacador" colspan="9"><img src="<%=_pathEcar%>/images/pixel.gif"></td></tr>
	
	
		<tr> 
		<td>
		<%
			request.getSession().removeAttribute("listPontosCriticos");
		
			PontoCriticoDao pontoCriticoDao = new PontoCriticoDao(request);
			List pontosCriticos = new ArrayList(pontoCriticoDao.getPontosCriticosAtivosOrdenadosDataLimite(itemEstrutura, false));

			if (pontosCriticos != null && pontosCriticos.size() > 0){
			
				String dataBaseStr = "";
							
				if (tipoDataBase.equals("LimiteFisico")){
					Date dataLimiteFisico = ari.getDataLimiteAcompFisicoAri();
					dataBaseStr = Data.getAno(dataLimiteFisico) + "/" + 
										(Data.getMes(dataLimiteFisico)+1) + "/" +
										Data.getDia(dataLimiteFisico);
				}
				else if (tipoDataBase.equals("InicioMonitoramento")){
					Date dataInicioMonitoramento = ari.getDataInicioAri();
					dataBaseStr = Data.getAno(dataInicioMonitoramento) + "/" + 
										(Data.getMes(dataInicioMonitoramento)+1) + "/" +
										Data.getDia(dataInicioMonitoramento);
				}
				else{ 
					//data atual
					Date dataAtual = Data.getDataAtual();
					dataBaseStr = Data.getAno(dataAtual) + "/" + 
										(Data.getMes(dataAtual)+1) + "/" +
										Data.getDia(dataAtual);
				}
				
				Date dataBase = new Date(dataBaseStr);
			    
			    List pontosCriticosSolucionados = new ArrayList();
		    	
			    Iterator itPontosCriticos = pontosCriticos.iterator();
			    
		    	while (itPontosCriticos.hasNext()){
		    	
		    		PontoCriticoPtc pontoCritico = (PontoCriticoPtc) itPontosCriticos.next();
	    			if ((pontoCritico.getDataSolucaoPtc() == null) || (pontoCritico.getDataSolucaoPtc() != null && pontoCritico.getDataSolucaoPtc().compareTo(dataBase) <= 0)){
	    				pontosCriticosSolucionados.add(pontoCritico);
	    			}
		    	}
				
				if (pontosCriticosSolucionados.size() > 0){
										
					request.getSession().setAttribute("listPontosCriticos", pontosCriticosSolucionados);
					
					 
				%>
					<table>
						<tr>
							<td valign="top" align="center">
								<img src="<%=_pathEcar%>/GraficoGanttPontosCriticos?dataBase=<%=dataBaseStr%>&labelPontosCriticos=<%=labelPontosCriticos%>">
							</td>
						</tr>				
					</table>
					
				<%
				}
				else{
				%>
					<table width="100%">
					<tr>
						<br><br>					
						<td valign="top" align="center">
							<h1>Não há restrições solucionadas até a data base selecionada</h1>
						</td>
					</tr>				
					</table>
				
				<%
				}
			}
			else{
			%>
				<table width="100%">
					<tr>
						<br><br>
						<td valign="top" align="center">
							<h1>Não há restrições cadastradas com data limite informada</h1>
						</td>
					</tr>				
				</table>
			<%
			}
		 %>
		</td>
	</tr>
	</table>
	<%@include file="../../../include/estadoMenu.jsp"%>
</form>
<br>
</div>
</body>

<%
} catch (ECARException e) {
	org.apache.log4j.Logger.getLogger(this.getClass()).error(e);
	Mensagem.alert(_jspx_page_context, e.getMessageKey());
} catch (Exception e){
%>
	<%@ include file="/excecao.jsp"%>
<%
}
%>

<% /* Controla o estado do Menu (aberto ou fechado) */%>
<%@ include file="/../../include/estadoMenu.jsp"%>
<%@ include file="/../../include/mensagemAlert.jsp" %>

</html>