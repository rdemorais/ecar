
<jsp:directive.page import="ecar.pojo.UsuarioAtributoUsua"/>
<jsp:directive.page import="ecar.dao.UsuarioDao"/>
<jsp:directive.page import="ecar.pojo.UsuarioUsu"/>
<jsp:directive.page import="ecar.dao.AbaDao"/>
<jsp:directive.page import="ecar.dao.SisGrupoAtributoDao"/>
<jsp:directive.page import="ecar.pojo.SisAtributoSatb"/>
<%@ include file="../login/validaAcesso.jsp"%>
<%@ include file="../frm_global.jsp"%>

<%@ page import="ecar.pojo.AcompReferenciaAref" %> 
<%@ page import="ecar.dao.AcompReferenciaDao" %> 
<%@ page import="ecar.pojo.AcompRefLimitesArl" %>
<%@ page import="ecar.pojo.TipoFuncAcompTpfa" %> 
<%@ page import="ecar.dao.TipoFuncAcompDao" %>  

<%@ page import="comum.util.Data" %> 

<%@ page import="java.util.Iterator" %> 
<%@ page import="java.util.List" %>
<%@ page import="ecar.permissao.ValidaPermissao" %>
<%@ page import="java.util.Comparator" %> 
<%@ page import="java.util.Collections" %>
<%@ page import="comum.util.Util" %>
<%@ page import="ecar.dao.TipoAcompanhamentoDao" %>
<%@ page import="ecar.dao.TipoAcompGrpAcessoDao" %>
<%@ page import="ecar.pojo.TipoAcompanhamentoTa" %>
<%@ page import="ecar.pojo.TipoAcompGrpAcesso" %>
<%@ page import="ecar.bean.OrdenacaoDataTpfa" %>
<%@ page import="ecar.pojo.ModeloRelatorioMrel" %>
<%@ page import="ecar.dao.ModeloRelatorioMrelDAO" %>
<%@ page import="ecar.dao.LinkDao" %>
<%@ page import="java.util.Collection" %>

<%@ taglib uri="/WEB-INF/ecar-util.tld" prefix="util" %>
<%@ taglib uri="/WEB-INF/ecar-combo.tld" prefix="combo" %>


<%@page import="ecar.dao.OrgaoDao"%><html lang="pt-br"> 
<head> 
<%@ include file="../include/meta.jsp"%>
<%@ include file="/titulo.jsp"%>
<link rel="stylesheet" href="<%=_pathEcar%>/css/style_<%=configuracaoCfg.getEstilo().getNome()%>.css" type="text/css">
<script language="javascript" src="../js/menu_retratil.js"></script>
<script language="javascript" src="../js/focoInicial.js"></script>
<script language="javascript" src="../js/frmPadraoRegAcompanhamento.js"></script>
<script language="javascript" src="../js/frmPadraoItemEstrut.js"></script>
<script language="javascript" src="../js/validacoes.js"></script>

 
</head>

<%
request.setAttribute("exibirAguarde", "true");
%>
<%@ include file="../cabecalho.jsp" %>
<%@ include file="../divmenu.jsp"%> 
<%

LinkDao linkDao = new LinkDao(request); 
TipoAcompanhamentoDao taDao = new TipoAcompanhamentoDao(request);
AcompReferenciaDao acompReferenciaDao = new AcompReferenciaDao(request);
TipoAcompanhamentoTa tipoAcompanhamento = null;
List secretarias = new ArrayList();
AcompReferenciaAref acompAref = null;
boolean ehSeparadoPorOrgao = false;
OrgaoDao orgaoDao = new OrgaoDao(request);


String raizUpload= configuracao.getRaizUpload();

String codTipoAcompanhamento = Pagina.getParamStr(request, "codTipoAcompanhamento");
String mesReferencia = Pagina.getParamStr(request, "mesReferencia");

// controle para mostrar como default o primeiro tipo de acompanhamento das abas selecionado
if("".equals(codTipoAcompanhamento)) {
	List listTa = taDao.getTipoAcompanhamentoAtivosComAcompanhamentoBySeqApresentacao();
	
	if(!listTa.isEmpty()) {
		codTipoAcompanhamento = ((TipoAcompanhamentoTa)listTa.get(0)).getCodTa().toString();
	} 
}

if(!"".equals(codTipoAcompanhamento)) {
	tipoAcompanhamento = (TipoAcompanhamentoTa)taDao.buscar(TipoAcompanhamentoTa.class, Long.valueOf(codTipoAcompanhamento));
}

if(!"".equals(mesReferencia)) {
	acompAref = (AcompReferenciaAref)acompReferenciaDao.buscar(AcompReferenciaAref.class, Long.valueOf(mesReferencia));
	if(tipoAcompanhamento == null && acompAref != null)
		tipoAcompanhamento = acompAref.getTipoAcompanhamentoTa();
}

if(tipoAcompanhamento != null) {
	if(tipoAcompanhamento.getIndSepararOrgaoTa() != null && tipoAcompanhamento.getIndSepararOrgaoTa().equals("S")) {
		ehSeparadoPorOrgao = true;
		List listaReferenciasConsolidadas = acompReferenciaDao.getListaMesmaReferenciaDiaMesAno(acompAref);
		if(!listaReferenciasConsolidadas.isEmpty()) {
			Iterator itListaReferenciasConsolidadas = listaReferenciasConsolidadas.iterator();
			while(itListaReferenciasConsolidadas.hasNext()) {
				AcompReferenciaAref arefOrgao = (AcompReferenciaAref) itListaReferenciasConsolidadas.next();
				if(arefOrgao.getOrgaoOrg() != null)
					secretarias.add(arefOrgao.getOrgaoOrg());
			}
		} else {
			if(acompAref.getOrgaoOrg() != null)
				secretarias.add(acompAref.getOrgaoOrg());
		}
	} else {
		secretarias = orgaoDao.getListaOrgaos(false);
	}
}



UsuarioUsu usuario = ((ecar.login.SegurancaECAR)session.getAttribute("seguranca")).getUsuario();
AbaDao abaDao = new AbaDao(null);
//Níveis de Planejamento por usuário -> Lista em strAux os CodSatb separados por ":"  
List niveisUsuario = new UsuarioDao(null).getNiveisPlanejamentoUsuario(usuario);
Iterator itNiveisUsu = niveisUsuario.iterator();
StringBuffer strAux = new StringBuffer("");	
while(itNiveisUsu.hasNext()) {
	UsuarioAtributoUsua usuAtributo = (UsuarioAtributoUsua) itNiveisUsu.next();
	strAux.append(usuAtributo.getSisAtributoSatb().getCodSatb()); 
	strAux.append(":");
}
String strNiveisPlanejamentoUsuario = strAux.toString();

String niveisPlanejamento = "";

String isSemInformacaoNivelPlanejamento = Dominios.NAO;

// Seta "niveisPlanejamento" com os códigos do sisAtributo separados por ":" ex.:(64:59:), isso por usuário
/* Verifica a exibição do filtro Nível Planejamento se não mostrar selecionar TODOS */
if (linkDao.estaConfiguradoLink("NIVEL_PLANEJAMENTO")) {	
	niveisPlanejamento = strNiveisPlanejamentoUsuario;
	isSemInformacaoNivelPlanejamento = Dominios.SIM;
}


%>
<body> 
<div id="conteudo">
<%
try{
%>
<!-- TÍTULO -->
<%@ include file="/titulo_tela.jsp"%>

<util:barraLinkTipoAcompanhamentoTag
	codTipoAcompanhamentoSelecionado="<%=codTipoAcompanhamento%>"
	chamarPagina="listaRelAcomp.jsp"
	tela="opcoes"
	caminho="<%=_pathEcar%>"
	gerarRelatorio="<%=Boolean.TRUE%>"
	exibirAbaFiltro="<%=Boolean.FALSE%>"
/> 

	<form name="form" method="post">
		<input type="hidden" name="codTipoAcompanhamento" value="<%=codTipoAcompanhamento%>">
		<input type="hidden" name="mesReferencia" value="<%=Pagina.getParamStr(request, "mesReferencia")%>">
		<input type="hidden" name="chaveEscolhida" value="">
		<input type="hidden" name="raizUpload" value="<%=raizUpload%>">
		<input type="hidden" name="niveisPlanejamento" value="<%=niveisPlanejamento%>">
		<input type="hidden" name="semInformacaoNivelPlanejamento" value="<%=isSemInformacaoNivelPlanejamento%>">
		<input type="hidden" name="hidAcao" value="">
		
	<%
	
	String _widthTabela = "15%";
	
	List itensBarra = new ArrayList();
	itensBarra.add("Modelos");
	itensBarra.add("Filtros");
	itensBarra.add("Formatos");
	
	int index = 0; //Modelos
	%>
	<util:barraLinksRelatorioItens
		itensBarra="<%=itensBarra%>"
		chamarPagina="relAcompOpcoes"
		indexAtivo="<%=index%>"
		semLinks="S"
	/>

	<util:barrabotoes btn3="Gerar Relatório"/>

	<table class="form" border="0">
		<tr>
			<td width="<%=_widthTabela%>">&nbsp;</td>
			<td>&nbsp;</td>
		</tr>
		
		<%
		ModeloRelatorioMrelDAO modeloDao = new ModeloRelatorioMrelDAO(request);
		
		List modelos = modeloDao.listar(ModeloRelatorioMrel.class, new String[] {"codAlfaMrel", "asc"});
		
		//Collection orgaos = acompReferenciaDao.getOrgaosComAcompanhamentosCriados(codTipoAcompanhamento);
		
		if(modelos != null && !modelos.isEmpty()){
			int qtdeOpcoes = 0;
			Iterator it = modelos.iterator();
			while(it.hasNext()){
				ModeloRelatorioMrel modelo = (ModeloRelatorioMrel) it.next();
				String codModelo = modelo.getCodAlfaMrel();
				String nomeModelo = modelo.getDescricaoMrel() + " (" + codModelo + ")";
				String disabled = "";
				
				if(ehSeparadoPorOrgao && secretarias.isEmpty()) {
					disabled = "disabled";
				}
			
		%>
				<tr>
					<td width="<%=_widthTabela%>">&nbsp;</td>
					<td>
						<input type="radio" class="form_check_radio" name="opcaoModelo" <%=disabled%> value="<%=codModelo%>">&nbsp;<%=nomeModelo%>
					</td>
				</tr>
		<%
				//Se modelo for ECAR-001B, montar Combo de Órgãos.
				if("ECAR-001B".equalsIgnoreCase(codModelo)){
					String script = " onchange=\"document.form.opcaoModelo[" + qtdeOpcoes + "].checked = true\"";
		%>
				<tr>
					<td width="<%=_widthTabela%>">&nbsp;</td>
					<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Órgão:
						<combo:ComboTag 
							nome="orgao" 
							objeto="ecar.pojo.OrgaoOrg" 
							label="siglaOrg" 
							value="codOrg"
							order="siglaOrg"
							colecao="<%=secretarias%>"
							filters="indAtivoOrg=<%=Dominios.ATIVO%>"
							scripts="<%=script%>"
						/>
					</td>
				</tr>
				
		<%
				}

				//Se modelo for ECAR-002B, montar Combo de Situações.
				if("ECAR-002B".equalsIgnoreCase(codModelo)){
					String script = " onchange=\"document.form.opcaoModelo[" + qtdeOpcoes + "].checked = true\"";
		%>
				<tr>
					<td width="<%=_widthTabela%>">&nbsp;</td>
					<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<%=configuracao.getLabelSituacaoParecer() %>:
						<combo:ComboTag
							nome="situacao" 
							objeto="ecar.pojo.Cor" 
							label="significadoCor" 
							value="codCor"
							order="ordemCor"
							scripts="<%=script%>"
						/>
						<input type="radio" class="form_check_radio" name="tipoArquivoRelatorio" value="ppt" checked> PPT
						<input type="radio" class="form_check_radio" name="tipoArquivoRelatorio" value="pdf"> PDF
					</td>
				</tr>
		<%
				}
			
				
				qtdeOpcoes++;
			}
		%>
		<!-- 
		<tr>
			<td width="<%=_widthTabela%>">&nbsp;</td>
			<td>
				<input type="radio" class="form_check_radio" name="opcaoModelo" value="P">&nbsp;Personalizado
			</td>
		</tr>
		 -->
		<%
		boolean permiteGerarArquivoGrp = false;
		String desabilitaGerarArquivo = "disabled";
		

		Iterator gruposAcessoSatbIt = seguranca.getGruposAcesso().iterator();

		while(gruposAcessoSatbIt.hasNext()) {
    		SisAtributoSatb grupoPermissaoAcessoSatb = (SisAtributoSatb) gruposAcessoSatbIt.next();
    		TipoAcompGrpAcessoDao tipoAcompGrpAcessoDao = new TipoAcompGrpAcessoDao();
    		TipoAcompGrpAcesso tipoAcompGrpAcesso = tipoAcompGrpAcessoDao.getTipoAcompGrpAcesso(grupoPermissaoAcessoSatb, tipoAcompanhamento);
   			if(tipoAcompGrpAcesso != null && tipoAcompGrpAcesso.getIndGerarArquivo().equals(Dominios.SIM)) {
   				permiteGerarArquivoGrp = true;
   				break;
    		}
    	}
		if(tipoAcompanhamento != null && tipoAcompanhamento.getIndGerarArquivoTa().equals(Dominios.SIM) && permiteGerarArquivoGrp)
			desabilitaGerarArquivo = "";

		%>
		<tr>
			<td width="<%=_widthTabela%>">&nbsp;</td>
			<td>
				<input type="radio" class="form_check_radio" name="opcaoModelo" value="T" <%=desabilitaGerarArquivo%> >&nbsp;Gerar Arquivo de Exportação
			</td>
		</tr>		
	
		<tr>
			<td colspan="2"><input type="hidden" name="qtdeOpcoes" value="<%=qtdeOpcoes+2%>"></td>
		</tr>
		
		<%
		
		qtdeOpcoes++;
		
		} else { // fim dos tipos de modelos.
		%>
			<tr>
				<td width="<%=_widthTabela%>">&nbsp;</td>
				<td>N&atilde;o existe nenhum modelo de relat&oacute;rio cadastrado.</td>
			</tr>
		<%
		}
		%>
	
	
		<tr>
			<td width="<%=_widthTabela%>">&nbsp;</td>
			<td>&nbsp;</td>
		</tr>
	</table>
	
	<table class="barrabotoes" cellpadding="0" cellspacing="0">
		<tr>
			<td align="left">
				&nbsp;
			</td>
		</tr>
	</table>
	
	</form>
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
</div>

</body>
<%@ include file="../include/ocultarAguarde.jsp"%>
<% /* Controla o estado do Menu (aberto ou fechado) */%>
<%@ include file="../include/estadoMenu.jsp"%>
<%@ include file="../include/mensagemAlert.jsp" %>
</html>

<script language="javascript">
function aoClicarBtn3(form){
	var numOpcoes = parseInt(form.qtdeOpcoes.value);
	var possuiMarcado = false;
	var marcado = -1;
	
	if(numOpcoes > 0){
		for(i = 0; i < numOpcoes; i++){
			if(form.opcaoModelo[i].checked == true){
				possuiMarcado = true;
				marcado = i;
				break;
			}
		}
	}
	
	if(possuiMarcado){
	
		if(form.opcaoModelo[marcado].value == "ECAR-001B" || form.opcaoModelo[marcado].value == "ECAR-002B"){
			//Gerar relatório automaticamente para órgão ou situação escolhido.
			if(form.opcaoModelo[marcado].value == "ECAR-001B"){
				if(form.orgao.value == ""){
					alert("<%=_msg.getMensagem("relAcompanhamento.relAccess.opcoes.orgao.obrigatorio")%>");
					form.orgao.focus();
					return false;
				}
				form.chaveEscolhida.value = form.orgao.value;
			}
			if(form.opcaoModelo[marcado].value == "ECAR-002B"){
				if(form.situacao.value == ""){
					alert("<%=_msg.getMensagem("relAcompanhamento.relAccess.opcoes.situacao.obrigatorio")%>");
					form.situacao.focus();
					return false;
				}
				form.chaveEscolhida.value = form.situacao.value;
			}
			form.action = "<%=_pathEcar%>/RelatorioAcompanhamento";
			form.target = "_blank";
			form.submit();
			form.target = "";
			
		// Se o modelo for a geração do arquivo TXT	
		} else if(form.opcaoModelo[marcado].value == "T") {
			form.action = "geracaoArquivos.jsp";
			form.submit();
		
		}
		else {
			form.action = "relAcompRelacaoItens.jsp";
			form.submit();
		}
	}
	else {
		alert("<%=_msg.getMensagem("relAcompanhamento.relAccess.opcoes.nenhumItem")%>");
	}
}
</script>


