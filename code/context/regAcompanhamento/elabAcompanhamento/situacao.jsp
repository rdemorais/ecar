<jsp:directive.page import="ecar.util.Dominios"/>
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
<%@ page import="ecar.pojo.ConfigMailCfgm" %>
<%@ page import="ecar.dao.ConfigMailCfgmDAO" %>
<%@ page import="comum.util.Util" %>
<%@ page import="comum.util.FileUpload" %>

<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.List" %> 
<%@ page import="java.util.Set" %> 
<%@ page import="java.util.Iterator" %> 

<%@ taglib uri="/WEB-INF/ecar-util.tld" prefix="util" %>
<%@ taglib uri="/WEB-INF/ecar-combo.tld" prefix="combo" %>

<%
	ConfigMailCfgmDAO configMailCfgmDAO = new ConfigMailCfgmDAO();
	ConfigMailCfgm configMailCfgm = null;
	configMailCfgm = (ConfigMailCfgm) configMailCfgmDAO.buscar(ConfigMailCfgm.class, Dominios.CFG_MAIL_CONCLUSAO_ACOMPANHAMENTO);

try{ 
	/* carrega o usuário da session */
	
	boolean isFormUpload = FileUpload.isMultipartContent(request);
	List campos = new ArrayList();
	if(isFormUpload){
		campos = FileUpload.criaListaCampos(request); 
	}
	
	String codAri = null;
	String primeiroAcomp = null;
	String codAcomp = null;
	String vemDaPgAcompRelatorio = null;
	
	if (isFormUpload){
		codAri = FileUpload.verificaValorCampo(campos, "codAri");
		primeiroAcomp = FileUpload.verificaValorCampo(campos, "primeiroAcomp");
		codAcomp = FileUpload.verificaValorCampo(campos, "codAcomp");
		vemDaPgAcompRelatorio = FileUpload.verificaValorCampo(campos, "vemDaPgAcompRelatorio");
	} else {
		codAri = Pagina.getParamStr(request, "codAri");
		primeiroAcomp = Pagina.getParamStr(request, "primeiroAcomp");
		codAcomp = Pagina.getParamStr(request, "codAcomp");
		vemDaPgAcompRelatorio = Pagina.getParamStr(request, "vemDaPgAcompRelatorio");
	}
	
	UsuarioUsu usuario = ((ecar.login.SegurancaECAR)session.getAttribute("seguranca")).getUsuario();
	
	/* item pai do item que está sendo cadastrado */  
	AcompReferenciaItemDao acompReferenciaItemDao = new AcompReferenciaItemDao(request); 
	AcompReferenciaItemAri acompReferenciaItem = (AcompReferenciaItemAri) acompReferenciaItemDao.buscar(AcompReferenciaItemAri.class, Long.valueOf(codAri));
	
	ValidaPermissao validaPermissao = new ValidaPermissao();
	if ( !validaPermissao.permissaoConsultaIETT( acompReferenciaItem.getItemEstruturaIett().getCodIett() , seguranca ) )
	{
		response.sendRedirect( request.getContextPath() +  "/acessoIndevido.jsp" );
	}	

%>


<html lang="pt-br"> 
<head>
<%@ include file="/include/meta.jsp"%>
<%@ include file="/titulo.jsp"%>
<link rel="stylesheet" href="<%=_pathEcar%>/css/style_<%=configuracaoCfg.getEstilo().getNome()%>.css" type="text/css">
<script language="javascript" src="<%=_pathEcar%>/js/menu_retratil.js" type="text/javascript"></script>
<script language="javascript" src="<%=_pathEcar%>/js/focoInicial.js" type="text/javascript"></script>
<script language="javascript" src="<%=_pathEcar%>/js/datas.js" type="text/javascript"></script>
<script language="javascript" src="<%=_pathEcar%>/js/mascaraMoeda.js" type="text/javascript"></script>
<script language="javascript" src="<%=_pathEcar%>/js/validacoes.js" type="text/javascript"></script>
 

</head>
<%@ include file="/cabecalho.jsp" %>
<%@ include file="/divmenu.jsp"%> 

<body>
<div id="conteudo"> 

<!-- TÍTULO -->
<%@ include file="/titulo_tela.jsp"%>

<%@ include file="botoesAvancaRetrocede.jsp" %>

 <!--  br>
	<util:barraLinksRegAcomp
		acompReferenciaItem="<%=acompReferenciaItem%>"  
		selectedFuncao="SITUACAO"
		usuario="<%=usuario%>"
		primeiroAcomp="<%=primeiroAcomp%>"
		request="<%=request%>"
		gruposUsuario="<%=seguranca.getGruposAcesso() %>"
	/> 
<br-->

<table width="100%" border="0" cellpadding="0" cellspacing="0">
		<tr><td class="espacador"><img src="<%=_pathEcar%>/images/pixel.gif" alt=""></td></tr>
		<tr class="linha_titulo" align="left">
			<td>
				Refer&ecirc;ncia: <%=acompReferenciaItem.getAcompReferenciaAref().getNomeAref()%>&nbsp;&nbsp;&nbsp;(<%=acompReferenciaItem.getAcompReferenciaAref().getTipoAcompanhamentoTa().getDescricaoTa()%>)
			</td> 
		</tr>
		<tr class="linha_titulo" align="left">
			<td>
				M&ecirc;s/Ano: <%=acompReferenciaItem.getAcompReferenciaAref().getMesAref()%>/<%=acompReferenciaItem.getAcompReferenciaAref().getAnoAref()%>
			</td> 
		</tr>
		<tr><td class="espacador"><img src="<%=_pathEcar%>/images/pixel.gif" alt=""></td></tr>
</table>

<util:arvoreEstruturaElabAcomp
	itemEstrutura="<%=acompReferenciaItem.getItemEstruturaIett()%>" 
	codigoAcomp="<%=codAcomp%>"
	contextPath="<%=_pathEcar%>"
	listaParaArvore='<%=(java.util.List)session.getAttribute("listaParaArvore")%>'  
	nivelPrimeiroIettClicado='<%=(String)session.getAttribute("nivelPrimeiroIettClicado")%>'
	abaAtual="<%=ecar.taglib.util.ArvoreEstruturaElabAcompTag.ABA_ACOMPANHAMENTOS%>"
	primeiroAcomp="<%=primeiroAcomp%>"
/>

<util:barraLinksRegAcompParecer
		acompReferenciaItem="<%=acompReferenciaItem%>"  
		selectedFuncao=""
		usuario="<%=usuario%>"
		primeiroAcomp="<%=primeiroAcomp%>"
		request="<%=request%>"
	/> 
<br>	

<form  name="form" method="post" action="">
	<input type="hidden" name="autorizarMail" value="N">
	<input type="hidden" name="liberarOuRecuperar" value="N">
	<input type="hidden" name="primeiroAcomp" value="<%=primeiroAcomp%>">
	<input type="hidden" name="codAcomp" value="<%=codAcomp%>">
<%
	String codSrl = "";
	EstruturaAtributoDao estruturaAtributoDao = new EstruturaAtributoDao(request);
	if(acompReferenciaItem.getStatusRelatorioSrl() != null){
		codSrl = acompReferenciaItem.getStatusRelatorioSrl().getCodSrl().toString();
	}
	
	
%>
	<input type="hidden" name="obrigatorio" value="<%=configMailCfgm.getIndEnvioObrigatorio()%>">
	<input type="hidden" name="codAcomp" value="<%=codAcomp%>">
	<input type="hidden" name="codAri" value="<%=acompReferenciaItem.getCodAri()%>">
	<input type="hidden" name="codUsuario" value="<%=usuario.getCodUsu()%>"> 
	<input type="hidden" name="codSrl" value="<%=codSrl%>"> 	
	<input type="hidden" name="botaoClicado" value="">

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
<%@ include file="/include/estadoMenu.jsp"%>
<%@ include file="/include/mensagemAlert.jsp" %>

</html>