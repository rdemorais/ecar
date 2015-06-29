<%@ include file="/login/validaAcesso.jsp"%>
<%@ include file="/frm_global.jsp"%>

<%@ page import="ecar.dao.AcompReferenciaItemDao" %>
<%@ page import="ecar.pojo.AcompReferenciaItemAri" %>
<%@ page import="ecar.dao.ItemEstruturaDao" %>
<%@ page import="ecar.dao.EstruturaDao" %>
<%@ page import="ecar.pojo.EstruturaEtt" %>
<%@ page import="ecar.pojo.ObjetoEstrutura" %>
<%@ page import="ecar.pojo.ItemEstruturaIett" %>
<%@ page import="ecar.pojo.SisAtributoSatb" %>
<%@ page import="ecar.pojo.UsuarioUsu"%>

<%@ page import="comum.util.Data" %> 
<%@ page import="comum.util.Util" %> 

<%@ page import="java.util.List" %> 
<%@ page import="java.util.Iterator" %>
<%@ page import="ecar.pojo.FonteRecursoFonr" %> 

<%@ taglib uri="/WEB-INF/ecar-util.tld" prefix="util" %>
<%@ taglib uri="/WEB-INF/ecar-combo.tld" prefix="combo" %>

<%
try{ 
	/* carrega o usuário da session */
	UsuarioUsu usuario = ((ecar.login.SegurancaECAR)session.getAttribute("seguranca")).getUsuario();

	AcompReferenciaItemDao acompReferenciaItemDao = new AcompReferenciaItemDao(request); 
	
	String strCodAri = Pagina.getParamStr(request, "codAri");
	
	if(!"".equals(Pagina.getParamStr(request, "referencia")) && !"S".equals(Pagina.getParamStr(request, "linkControle"))){
		// Veio nova referência, selecionada na combo, deve trocar o Ari.
		strCodAri = Pagina.getParamStr(request, "referencia");
	}
	
	AcompReferenciaItemAri acompReferenciaItem = (AcompReferenciaItemAri) acompReferenciaItemDao.buscar(AcompReferenciaItemAri.class, Long.valueOf(strCodAri));
	
	ValidaPermissao validaPermissao = new ValidaPermissao();
	if ( !validaPermissao.permissaoConsultaIETT( acompReferenciaItem.getItemEstruturaIett().getCodIett() , seguranca ) )
	{
		response.sendRedirect( request.getContextPath() +  "/acessoIndevido.jsp" );
	}
	
	ItemEstruturaDao itemEstruturaDao = new ItemEstruturaDao(request);
	EstruturaDao estruturaDao = new EstruturaDao(request);
	
	String codEttSelecionado = Pagina.getParamStr(request, "codEttSelecionado");
%>

<html lang="pt-br"> 
<head>
<%@ include file="/include/meta.jsp"%>
<%@ include file="/titulo.jsp"%>
<link rel="stylesheet" href="<%=_pathEcar%>/css/style_<%=configuracaoCfg.getEstilo().getNome()%>.css" type="text/css">
<script language="javascript" src="<%=_pathEcar%>/js/menu_retratil.js" type="text/javascript"></script>
<script language="javascript" src="<%=_pathEcar%>/js/focoInicial.js" type="text/javascript"></script>
<script language="javascript" src="<%=_pathEcar%>/js/tableSort.js" type="text/javascript"></script>

<script type="text/javascript">
function recarregar(form){
	form.action = "frm_con.jsp";
	form.submit();
}
function trocaAba(link, codAri){
	document.forms[0].codAri.value = codAri;
	document.forms[0].action = link;
	document.forms[0].submit();
}
function trocaAbaImpressao(link){
	document.forms[0].action = link;
	document.forms[0].submit();
}

function aoClicarTrocaAbaEstrutura(form, codEtt){
	form.codEttSelecionado.value = codEtt;
	form.action = "frm_con.jsp";
	form.submit();
}
</script>
</head>
<body>

<%@ include file="/cabecalho.jsp" %>
<%@ include file="/divmenu.jsp"%> 

<div id="conteudo"> 
<!-- TÍTULO -->
<%@ include file="/titulo_tela.jsp"%>
<%@ include file="../botoesAvancaRetrocede.jsp" %>
<br>
	<util:barraLinksRegAcomp 
		acompReferenciaItem="<%=acompReferenciaItem%>"  
		selectedFuncao="ETAPA"
		usuario="<%=usuario%>"
		primeiroAcomp="<%=Pagina.getParamStr(request, "primeiroAcomp")%>"
		request="<%=request%>"
		gruposUsuario="<%=seguranca.getGruposAcesso() %>"
	/>
<br>

<table width="100%" border="0" cellpadding="0" cellspacing="0">
		<tr><td class="espacador"><img src="<%=_pathEcar%>/images/pixel.gif"></td></tr>
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
		<tr><td class="espacador"><img src="<%=_pathEcar%>/images/pixel.gif"></td></tr>
</table>

<util:arvoreEstruturaElabAcomp
	itemEstrutura="<%=acompReferenciaItem.getItemEstruturaIett()%>" 
	codigoAcomp="<%=Pagina.getParamStr(request, "codAcomp")%>"
	contextPath="<%=_pathEcar%>"
	listaParaArvore="<%=(java.util.List)session.getAttribute("listaParaArvore")%>"  
	nivelPrimeiroIettClicado="<%=(String)session.getAttribute("nivelPrimeiroIettClicado")%>"
	abaAtual="<%=ecar.taglib.util.ArvoreEstruturaElabAcompTag.ABA_DADOS_BASICOS%>"
	primeiroAcomp="<%=Pagina.getParamStr(request, "primeiroAcomp")%>"
/>

<form  name="form" method="post" action="">
<!-- 	
<table border="0" width="100%">
	<tr>
	 	<td valign="bottom" class="texto" align="left">
			<b>Data do relatório:</b> <%=Data.parseDate(Data.getDataAtual())%><br>
			<b>Mês de referência: </b> <combo:ComboReferenciaTag
				nome="referencia"
				acompReferenciaItem="<%=acompReferenciaItem%>"
				scripts="onchange='recarregar(form)'"/>
		</td>
		<td align="right" valign="bottom">
			<util:barraSinaisTag 
				acompReferenciaItem="<%=acompReferenciaItem%>"
				caminho="<%=_pathEcar%>"
			/>
		</td>
	</tr>
</table>
-->
<!-- Campos para manter a seleção em Posição Geral -->
<input type="hidden" name="codTipoAcompanhamento" value='<%=Pagina.getParamStr(request, "codTipoAcompanhamento")%>'>
<input type="hidden" name="periodo" value='<%=Pagina.getParamStr(request, "periodo")%>'>
<input type="hidden" name="mesReferencia" value="<%=acompReferenciaItem.getAcompReferenciaAref().getCodAref().toString()%>">
<input type="hidden" name="niveisPlanejamento" value='<%=Pagina.getParamStr(request, "niveisPlanejamento")%>'>
<input type="hidden" name="codAri" value='<%=Pagina.getParamStr(request, "codAri")%>'>
<input type="hidden" name="primeiroIettClicado" value='<%=Pagina.getParamStr(request, "primeiroIettClicado")%>'>
<input type="hidden" name="primeiroAriClicado" value='<%=Pagina.getParamStr(request, "primeiroAriClicado")%>'>
<input type="hidden" name="codigosItem" value='<%=Pagina.getParamStr(request, "codigosItem")%>'>

<!-- Campo necessário para botões de Avança/Retrocede -->
<input type="hidden" name="codArisControle" value='<%=Pagina.getParamStr(request, "codArisControle")%>'>

<!-- Campos para manter a seleção em Posição Geral -->
<input type="hidden" name="codAri" value='<%=Pagina.getParamStr(request, "codAri")%>'>
<input type="hidden" name="codEttSelecionado" value="<%=codEttSelecionado%>">

<%
	List etapas = new ArrayList();
	List estruturaEtapas = estruturaDao.getEstruturasEtapas(acompReferenciaItem.getItemEstruturaIett().getEstruturaEtt());

	/* lista de colunas de uma estrutura */
	List lColunas;
	EstruturaEtt estruturaAtual = new EstruturaEtt();
%>
	<br>
	<table id="abasestrutura" border="0" cellpadding="0" cellspacing="0" width="100%">
		<tr>
			<td>
<%	
	
	boolean isPrimeiro = true;
	Iterator itEttEtapas = estruturaEtapas.iterator();
	boolean exibirNomeEtapa = (estruturaEtapas.size() > 1); //Se tiver mais de 1 etapa, exibir nome acima a listagem
	
	while(itEttEtapas.hasNext()){
		EstruturaEtt estruturaFilha = (EstruturaEtt) itEttEtapas.next();
		
		if("".equals(codEttSelecionado)){
			estruturaAtual = estruturaFilha;
			codEttSelecionado = estruturaAtual.getCodEtt().toString();
		}
		else {
			if(codEttSelecionado.equals(estruturaFilha.getCodEtt().toString())){
				estruturaAtual = estruturaFilha;
			}
		}

		/*
		O teste deve ser feito dentro deste while, pois é aqui dentro que é 
		setado o objeto estruturaAtual, que é usado após o while.
		*/
		if(exibirNomeEtapa){
			String tipoAba = "";
			if (codEttSelecionado.equals(estruturaFilha.getCodEtt().toString()))
				tipoAba = "abaestruturahabilitada";
			else
				tipoAba = "abaestruturadesabilitada";
					
			%>
					<table class="<%=tipoAba%>" style="background-color: <%= estruturaFilha . getCodCor1Ett() %>; border-bottom: <%= estruturaFilha . getCodCor1Ett() %>;">
							<tr>
								<td>
									<% if ("abaestruturadesabilitada".equals(tipoAba))
									{ %>
										<a href="#" onclick="javascript:aoClicarTrocaAbaEstrutura(document.form, <%=estruturaFilha.getCodEtt()%>)">
										<%=estruturaFilha.getNomeEtt() %>
										</a>
									<%} else { 
										out.print(estruturaFilha.getNomeEtt()); 
									} %>
								</td>
							</tr>
					</table>
			<%
		}
	}
		
%>
			</td>
		</tr>

		<tr>
			<td>	
<%
				lColunas = estruturaDao.getAtributosAcessoEstrutura(estruturaAtual);

				if(lColunas != null && lColunas.size() > 0) {
					//etapas = itemEstruturaDao.getItensFilho(acompReferenciaItem.getItemEstruturaIett(), estruturaAtual, ((ObjetoEstrutura)lColunas.get(0)).iGetNomeOrdenarLista());
					etapas = itemEstruturaDao.getItensFilho(acompReferenciaItem.getItemEstruturaIett(), estruturaAtual, lColunas);
				}
				else {
					etapas = itemEstruturaDao.getItensFilho(acompReferenciaItem.getItemEstruturaIett(), estruturaAtual, "");
				}

%>	
				<table border="0" cellpadding="0" cellspacing="0" width="100%">
					<thead>
						<tr class="linha_subtitulo_estrutura" bgcolor="<%=estruturaAtual.getCodCor1Ett()%>">
<%
						/* desenha as colunas de uma estrutura */
						Iterator itColunas = lColunas.iterator();
						int numColuna = 0;
						while (itColunas.hasNext()){
							ObjetoEstrutura coluna = (ObjetoEstrutura) itColunas.next();
%>			
							<td width="<%=coluna.iGetLargura()%>%"><u>
							<a href="#" onclick="this.blur(); return sortTable('corpo',  <%=numColuna%>, false);">											
							<%numColuna++;%>
							<%=coluna.iGetLabel()%>
							</a>
							</u>
							</td>
<%
						}
%>		
						</tr><tr><td class="espacadorestrutura" colspan="<%=lColunas.size()%>"><img src="<%=_pathEcar%>/images/pixel.gif" alt=""></td></tr>
					</thead>
					<tbody id="corpo">
<%
					Iterator itEtapa = etapas.iterator();
					while(itEtapa.hasNext()){
						ItemEstruturaIett etapa = (ItemEstruturaIett) itEtapa.next();
						
						//if(etapa.getEstruturaEtt().equals(estruturaAtual)){
%>
						<tr class="linha_subtitulo2_estrutura" bgcolor="<%=estruturaAtual.getCodCor3Ett()%>">
<%							itColunas = lColunas.iterator();
							while (itColunas.hasNext()){
								ObjetoEstrutura coluna = (ObjetoEstrutura) itColunas.next();
%>
							<td width="<%=coluna.iGetLargura()%>%">
								<font color="<%=estruturaAtual.getCodCor4Ett()%>">
									<%
									if("nivelPlanejamento".equals(coluna.iGetNome())){
										String niveis = "";
								    	if(etapa.getItemEstruturaNivelIettns() != null && !etapa.getItemEstruturaNivelIettns().isEmpty()){
									    	Iterator itNiveis = etapa.getItemEstruturaNivelIettns().iterator();
									    	while(itNiveis.hasNext()){
									    		SisAtributoSatb nivel = (SisAtributoSatb) itNiveis.next();
									    		niveis += nivel.getDescricaoSatb() + "; ";
									    	}
									    	niveis = niveis.substring(0, niveis.lastIndexOf(";"));
								    	}
										out.println(niveis);
									}
									else{
										out.println(coluna.iGetValor(etapa));
									}
									%>
								</font>
							</td>							
<%					
							}
%>
						</tr>
<%
						//}
					}
%>						
					</tbody>
				</table>
			</td>
		</tr>
	</table>
</form>

<%
} catch (ECARException e){
	org.apache.log4j.Logger.getLogger(this.getClass()).error(e);
	Mensagem.alert(_jspx_page_context, _msg.getMensagem(e.getMessageKey()));
} catch (Exception e){
%>
	<%@ include file="/excecao.jsp"%>
<%
}
%>

<br>
</div>
</body>
<% /* Controla o estado do Menu (aberto ou fechado) */%>
<%@ include file="/include/estadoMenu.jsp"%>
<%@ include file="/include/mensagemAlert.jsp" %>
</html>