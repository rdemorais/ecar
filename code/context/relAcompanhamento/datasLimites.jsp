
<jsp:directive.page import="ecar.pojo.SisAtributoSatb"/><%@ include file="../login/validaAcesso.jsp"%>
<%@ include file="../frm_global.jsp"%>
 
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
<%@ page import="ecar.permissao.ValidaPermissao" %> 
<%@ page import="ecar.dao.ItemEstUsutpfuacDao" %>
<%@ page import="ecar.pojo.ConfigMailCfgm" %>
<%@ page import="ecar.dao.ConfigMailCfgmDAO" %>
<%@ page import="comum.util.Util" %>
<%@ page import="comum.util.Data" %>

<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.List" %> 
<%@ page import="java.util.Set" %> 
<%@ page import="java.util.Iterator" %> 

<%@ taglib uri="/WEB-INF/ecar-util.tld" prefix="util" %>
<%@ taglib uri="/WEB-INF/ecar-combo.tld" prefix="combo" %>

<%
try{ 
	
	ItemEstruturaIett itemEstrutura = null;
	AcompReferenciaItemAri acompReferenciaItem = null;
	ItemEstruturaDao itemEstruturaDao = new ItemEstruturaDao(request);
	AcompReferenciaItemDao acompReferenciaItemDao = new AcompReferenciaItemDao(request); 
	EstruturaAtributoDao estruturaAtributoDao = new EstruturaAtributoDao(request);

	/* carrega o usuário da session */
	UsuarioUsu usuario = ((ecar.login.SegurancaECAR)session.getAttribute("seguranca")).getUsuario();
	
	String codIettRelacao = Pagina.getParamStr(request, "codIettRelacao");
	String strCodAri = Pagina.getParamStr(request, "codAri");
	
	if(!"".equals(Pagina.getParamStr(request, "referencia")) && !"S".equals(Pagina.getParamStr(request, "linkControle"))){
		// Veio nova referência, selecionada na combo, deve trocar o Ari.
		strCodAri = Pagina.getParamStr(request, "referencia");
	}
	
	if(!"".equals(strCodAri)) {
		acompReferenciaItem = (AcompReferenciaItemAri) new AcompReferenciaItemDao(request).buscar(
						AcompReferenciaItemAri.class, Long.valueOf(strCodAri));
		itemEstrutura = acompReferenciaItem.getItemEstruturaIett();
		
		ValidaPermissao validaPermissao = new ValidaPermissao();
		if ( !validaPermissao.permissaoConsultaParecerIETT( itemEstrutura.getCodIett() , seguranca ) )
		{
			response.sendRedirect( request.getContextPath() +  "/acessoIndevido.jsp" );
		}			
		
		
	} else if(!"".equals(codIettRelacao)) {
		itemEstrutura = (ItemEstruturaIett)itemEstruturaDao.buscar(ItemEstruturaIett.class, Long.valueOf(codIettRelacao));
	}
	
%>


<html lang="pt-br"> 
<head>
<%@ include file="../include/meta.jsp"%>
<%@ include file="/titulo.jsp"%>
<link rel="stylesheet" href="<%=_pathEcar%>/css/style_<%=configuracaoCfg.getEstilo().getNome()%>.css" type="text/css">
<script language="javascript" src="../js/menu_retratil.js"></script>
<script language="javascript" src="../js/focoInicial.js"></script>
<script language="javascript" src="../js/datas.js"></script>
<script language="javascript" src="../js/mascaraMoeda.js"></script>
<script language="javascript" src="../js/validacoes.js"></script>
 
<script language="javascript">
function trocaAbaSemAri(link, codIettRelacao){
	document.form.codIettRelacao.value = codIettRelacao;
	document.form.itemDoNivelClicado.value = codIettRelacao;
	document.form.action = link;
	document.form.submit();
}
function recarregar(){
	document.forms[0].action = "datasLimites.jsp";
	document.forms[0].submit();
}
function trocaAba(link, codAri){
	document.forms[0].codIettRelacao.value = '';
	document.forms[0].codAri.value = codAri;
	document.forms[0].action = link;
	document.forms[0].submit();
}
function trocaAbaImpressao(link){
	document.forms[0].action = link;
	document.forms[0].submit();
}
</script>
 
</head>
<%@ include file="../cabecalho.jsp" %>
<%@ include file="../divmenu.jsp"%> 

<body>
<div id="conteudo"> 

<!-- TÍTULO -->
<%@ include file="/titulo_tela.jsp"%>


	<util:barraLinkTipoAcompanhamentoTag
		codTipoAcompanhamentoSelecionado="<%=Pagina.getParamStr(request, "codTipoAcompanhamento")%>"
		chamarPagina="posicaoGeral.jsp"
	/> 

	<%@ include file="botoesAvancaRetrocede.jsp" %>

	<util:arvoreEstruturas 
		itemEstrutura="<%=itemEstrutura%>" 
		exibirLinks="false" 
		proximoNivel="false" 
		contextPath="<%=_pathEcar%>"
		primeiroIettClicado="<%=request.getParameter("primeiroIettClicado")%>"
		primeiroAriClicado="<%=request.getParameter("primeiroAriClicado")%>"
		codTipoAcompanhamentoSelecionado="<%=Pagina.getParamStr(request, "codTipoAcompanhamento")%>"
	/> 

	<br>
	
	<util:barraLinksRelatorioAcompanhamento
		acompReferenciaItem="<%=acompReferenciaItem%>"  
		funcaoSelecionada="DATAS_LIMITES"
		primeiroIettClicado="<%=request.getParameter("primeiroIettClicado")%>"
		primeiroAriClicado="<%=request.getParameter("primeiroAriClicado")%>"
		caminho="<%=_pathEcar + "/relAcompanhamento/"%>"
		codTipoAcompanhamento="<%=Pagina.getParamStr(request, "codTipoAcompanhamento")%>"
		gruposUsuario="<%=seguranca.getGruposAcesso() %>" 
	/>

<br>

<form  name="form" method="post" >	
<%
if(acompReferenciaItem != null) {
%>
	<table border="0" width="100%">
			<tr>
				<td valign="bottom" class="texto" align="left" colspan="2"> 
					<b>Data do relatório:</b> <%=Data.parseDate(Data.getDataAtual())%><br>
					<b>Mês de referência: </b>
					<combo:ComboReferenciaTag
						nome="referencia"
						acompReferenciaItem="<%=acompReferenciaItem%>"
						scripts="onchange='recarregar()'"
					/>
				</td>
			</tr>
	</table>
<%
}
%>

<!-- Campos para manter a seleção em Posição Geral -->
<input type="hidden" name="codTipoAcompanhamento" value="<%=Pagina.getParamStr(request, "codTipoAcompanhamento")%>">
<input type="hidden" name="periodo" value="<%=Pagina.getParamStr(request, "periodo")%>">
<!-- input type="hidden" name="mesReferencia" value="<%=Pagina.getParamStr(request, "mesReferencia")%>" -->
<input type="hidden" name="mesReferencia" value="<%=acompReferenciaItem.getAcompReferenciaAref().getCodAref().toString()%>">
<input type="hidden" name="niveisPlanejamento" value="<%=Pagina.getParamStr(request, "niveisPlanejamento")%>">
<input type="hidden" name="codAri" value="<%=Pagina.getParamStr(request, "codAri")%>">
<input type="hidden" name="primeiroIettClicado" value="<%=Pagina.getParamStr(request, "primeiroIettClicado")%>">
<input type="hidden" name="primeiroAriClicado" value="<%=Pagina.getParamStr(request, "primeiroAriClicado")%>">
<input type="hidden" name="codIettRelacao" value="<%=Pagina.getParamStr(request, "codIettRelacao")%>">
<input type="hidden" name="itemDoNivelClicado" value="<%=Pagina.getParamStr(request, "codIettRelacao")%>">
<input type="hidden" name="codigosItem" value="<%=Pagina.getParamStr(request, "codigosItem")%>">

<!-- Campo necessário para botões de Avança/Retrocede -->
<input type="hidden" name="codArisControle" value="<%=Pagina.getParamStr(request, "codArisControle")%>">

<!-- Campos para manter a seleção em Posição Geral -->

<input type="hidden" name="hidAcao" value="">

<div id="subconteudo">

	<table class="barrabotoes" cellpadding="0" cellspacing="0">
		<tr>
			<td align="left">
			<b>Datas Limites - Em desenvolvimento</b>
			</td>
		</tr>
	</table>

	<table name="form" class="form" width="100%">
		<%if(acompReferenciaItem.getItemEstruturaIett().getOrgaoOrgByCodOrgaoResponsavel1Iett()!=null){%>
			<tr>
			 <td class="label"><%=estruturaAtributoDao.getLabelAtributoEstrutura("orgaoOrgByCodOrgaoResponsavel1Iett", acompReferenciaItem.getItemEstruturaIett().getEstruturaEtt())%></td>
			 <td>
					<%=acompReferenciaItem.getItemEstruturaIett().getOrgaoOrgByCodOrgaoResponsavel1Iett().getDescricaoOrg()%>
			</td>
		</tr>  
		<%}%>		
		<%if(acompReferenciaItem.getItemEstruturaIett().getOrgaoOrgByCodOrgaoResponsavel2Iett()!=null){%>
			<tr>
			 <td class="label"><%=estruturaAtributoDao.getLabelAtributoEstrutura("orgaoOrgByCodOrgaoResponsavel2Iett", acompReferenciaItem.getItemEstruturaIett().getEstruturaEtt())%></td>
			 <td>
					<%=acompReferenciaItem.getItemEstruturaIett().getOrgaoOrgByCodOrgaoResponsavel2Iett().getDescricaoOrg()%>
			</td>
		</tr>  
		<%}%>		

		<%
			//Recuperar as funçoes de Acompanhamento do Item
			//Set funcoes = acompReferenciaItem.getItemEstruturaIett().getItemEstUsutpfuacIettutfas();
			List funcoes = new ItemEstUsutpfuacDao(request).getFuacOrderByFuncAcomp(acompReferenciaItem.getItemEstruturaIett());
			
			Iterator it = funcoes.iterator();
			while(it.hasNext()){
				ItemEstUsutpfuacIettutfa itemEstUsu = (ItemEstUsutpfuacIettutfa) it.next();
				%>
				<tr> 
				 <td class="label"><%=itemEstUsu.getTipoFuncAcompTpfa().getLabelTpfa()%></td>
			 	<%
			 	String nomeUsuarioFuac = "";
			 	
			 	if (itemEstUsu.getUsuarioUsu() != null){
				 	nomeUsuarioFuac = itemEstUsu.getUsuarioUsu().getNomeUsuSent();
				} else if (itemEstUsu.getSisAtributoSatb() != null) {
					nomeUsuarioFuac = itemEstUsu.getSisAtributoSatb().getDescricaoSatb();
				}
			 	%>
				 
				 <td><%=nomeUsuarioFuac%></td>				
				 </tr>
				 <%
				 ecar.pojo.AcompRelatorioArel relatorio = new ecar.dao.AcompRelatorioDao(request).getAcompRelatorio(itemEstUsu.getTipoFuncAcompTpfa(), acompReferenciaItem);
				 if(relatorio != null){
				 		UsuarioUsu usuarioUltimaManutencao = relatorio.getUsuarioUsuUltimaManutencao();
				 		if(usuarioUltimaManutencao != null && !usuarioUltimaManutencao.equals(itemEstUsu.getUsuarioUsu())){
				 			%>
							<tr>
								<td>&nbsp;</td>
								<td><%=itemEstUsu.getTipoFuncAcompTpfa().getLabelPosicaoTpfa()%> atribu&iacute;da por <b><%=usuarioUltimaManutencao.getNomeUsuSent()%></b></td>
							</tr>
							<%
				 		}
				 }
				 %>
				<%
			}
		%> 
		<tr>
			 <td class="label">Data de In&iacute;cio</td>
			 <td>
				 <input type="text" name="dataInicioAriDisabled" size="13" maxlength="10" value="<%=Pagina.trocaNullData(acompReferenciaItem.getDataInicioAri())%>" disabled>
			</td>
		</tr> 
		<tr>
			 <td class="label">Data Limite para Acompanhamento F&iacute;sico</td>
			 <td>
				 <input type="text" name="dataLimiteAcompFisicoDisabled" size="13" maxlength="10" value="<%=Pagina.trocaNullData(acompReferenciaItem.getDataLimiteAcompFisicoAri())%>" disabled>
			</td>
		</tr> 
		<%
		List funcoesAcompanhamento = new AcompReferenciaItemDao(request).getAcompRefItemLimitesArliByAcompRefrenciaItem(acompReferenciaItem);
        Iterator itFuncoes = funcoesAcompanhamento.iterator();
        while(itFuncoes.hasNext()){
        	AcompRefItemLimitesArli acompLimite = (AcompRefItemLimitesArli) itFuncoes.next();
        	//camposVariaveis.add(acompLimite.getTipoFuncAcompTpfa());
        	%>
			<tr>
				 <td class="label">Data limite para <%=acompLimite.getTipoFuncAcompTpfa().getLabelPosicaoTpfa()%></td>
				 <td>
					 <input type="text" name="dataLimite<%=acompLimite.getTipoFuncAcompTpfa().getCodTpfa()%>" size="13" maxlength="10" value="<%=Pagina.trocaNullData(acompLimite.getDataLimiteArli())%>" disabled>
				</td>
			</tr> 			
			<%
        }
		%>
		<tr>
			 <td class="label">Inclus&atilde;o:</td>
			 <td>
			 <%=Pagina.trocaNullData(acompReferenciaItem.getDataInclusaoAri())%>
			 <%if(acompReferenciaItem.getCodUsuincAri() != null) {
				 	UsuarioUsu usuarioInclusao = (UsuarioUsu) new UsuarioDao(request).buscar(UsuarioUsu.class, acompReferenciaItem.getCodUsuincAri());
				 	out.println("&nbsp;&nbsp;" + usuarioInclusao.getNomeUsuSent());
			 	}
			 %>
			</td>
		</tr> 	
		<tr>
			 <td class="label">&Uacute;ltima Altera&ccedil;&atilde;o:</td>
			 <td>
			 <%=Pagina.trocaNullData(acompReferenciaItem.getDataUltManutAri())%>
			 <%if(acompReferenciaItem.getCodUsuUltManutAri() != null) {
				 	UsuarioUsu usuarioAlteracao = (UsuarioUsu) new UsuarioDao(request).buscar(UsuarioUsu.class, acompReferenciaItem.getCodUsuUltManutAri());
				 	out.println("&nbsp;&nbsp;" + usuarioAlteracao.getNomeUsuSent());
			 	}
			 %>
			</td>
		</tr> 			
	</table>

</div>
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
<%@ include file="../include/estadoMenu.jsp"%>
<%@ include file="../include/mensagemAlert.jsp" %>

</html>