<%@ include file="../login/validaAcesso.jsp"%>
<%@ include file="../frm_global.jsp"%>

<%@ page import="ecar.dao.AcompReferenciaItemDao" %>
<%@ page import="ecar.dao.ItemEstruturaUploadCategoriaDao" %>
<%@ page import="ecar.dao.UploadTipoCategoriaDao" %>
<%@ page import="ecar.pojo.AcompReferenciaItemAri" %>
<%@ page import="ecar.pojo.ItemEstrUplCategIettuc" %>
<%@ page import="ecar.pojo.UploadTipoCategoriaUtc" %>
<%@ page import="ecar.permissao.ValidaPermissao" %> 
<%@ page import="ecar.pojo.ImageInfo" %>

<%@ page import="comum.util.Data" %> 
<%@ page import="comum.util.FileUpload" %> 
<%@ page import="comum.util.Util" %> 

<%@ page import="java.util.List" %> 
<%@ page import="java.util.Iterator" %> 
<%@ page import="java.io.File" %>
<%@ page import="java.io.FileInputStream" %>
<%@ page import="ecar.dao.ConfiguracaoDao" %>
<%@ page import="ecar.pojo.ItemEstruturaIett" %>
<%@ page import="ecar.dao.ItemEstruturaDao" %>
<%@ page import="ecar.pojo.ConfiguracaoCfg" %>

<%@ taglib uri="/WEB-INF/ecar-util.tld" prefix="util" %>
<%@ taglib uri="/WEB-INF/ecar-combo.tld" prefix="combo" %>

<%
try{ 
	ItemEstruturaIett itemEstrutura = null;
	AcompReferenciaItemAri acompReferenciaItem = null;
	ItemEstruturaDao itemEstruturaDao = new ItemEstruturaDao(request);
	AcompReferenciaItemDao acompReferenciaItemDao = new AcompReferenciaItemDao(request); 

	ConfiguracaoCfg configuracao = (ConfiguracaoCfg)session.getAttribute("configuracao");
	String pathRaiz = configuracao.getRaizUpload();	

	
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
		ValidaPermissao validaPermissao = new ValidaPermissao();
		if ( !validaPermissao.permissaoConsultaParecerIETT( Long.valueOf(codIettRelacao) , seguranca ) )
		{
			response.sendRedirect( request.getContextPath() +  "/acessoIndevido.jsp" );
		}	

		itemEstrutura = (ItemEstruturaIett)itemEstruturaDao.buscar(ItemEstruturaIett.class, Long.valueOf(codIettRelacao));
	}
%>

<html lang="pt-br"> 
<head>
<%@ include file="../include/meta.jsp"%>
<%@ include file="/titulo.jsp"%>
<link rel="stylesheet" href="<%=_pathEcar%>/css/style_<%=configuracaoCfg.getEstilo().getNome()%>.css" type="text/css">
<link rel="stylesheet" href="<%=_pathEcar%>/css/css_galeria_<%=configuracaoCfg.getEstilo().getNome()%>.jsp" type="text/css">
<script language="javascript" src="../js/menu_retratil.js"></script>
<script language="javascript" src="../js/focoInicial.js"></script>
<script language="javascript" src="../js/windowopen.js"></script>
<script language="javascript" src="../js/popUp.js"></script>
<script>
function trocaAbaSemAri(link, codIettRelacao){
	document.form.codIettRelacao.value = codIettRelacao;
	document.form.itemDoNivelClicado.value = codIettRelacao;
	document.form.action = link;
	document.form.submit();
}
function recarregar(form){
	form.action = "galeria.jsp";
	form.submit();
}
function trocaAba(link, codAri){
	document.forms[0].codIettRelacao.value = '';
	document.forms[0].codAri.value = codAri;
	document.forms[0].action = link;
	document.forms[0].submit();
}
function setAberto(form, aberto){
	form.hidItemAberto.value = aberto;
}

function aoClicarProximo(form, categoria){
	var valor = parseInt(eval("form.hidPagina" + categoria + ".value")) + 1;
	var campo = "hidPagina" + categoria;
	
	var el = document.getElementsByTagName("INPUT")
	var i = 0;
	
	while (i < el.length){		
		if(el.item(i) != null){
			if (el.item(i).type == "hidden" && el.item(i).name == campo){
				el.item(i).value = valor;
			}
		}
		i++;
	}

	form.action = "galeria.jsp";
	form.submit();
}

function aoClicarAnterior(form, categoria){
	var valor = parseInt(eval("form.hidPagina" + categoria + ".value")) - 1;
	var campo = "hidPagina" + categoria;
	
	var el = document.getElementsByTagName("INPUT")
	var i = 0;
	
	while (i < el.length){		
		if(el.item(i) != null){
			if (el.item(i).type == "hidden" && el.item(i).name == campo){
				el.item(i).value = valor;
			}
		}
		i++;
	}

	form.action = "galeria.jsp";
	form.submit();
}
function trocaAbaImpressao(link){
	document.forms[0].action = link;
	document.forms[0].submit();
}
</script>
</head>

<body>

<%@ include file="../cabecalho.jsp" %>
<%@ include file="../divmenu.jsp"%> 

<div id="conteudo"> 
	
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
		funcaoSelecionada="GALERIA"
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
				<b>Mês de referência: </b> <combo:ComboReferenciaTag
					nome="referencia"
					acompReferenciaItem="<%=acompReferenciaItem%>"
					scripts="onchange='recarregar(form)'"/>
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

<input type="hidden" name="codAri" value="<%=Pagina.getParamStr(request, "codAri")%>">
<input type="hidden" name="hidItemAberto" value="<%=Pagina.getParamStr(request, "hidItemAberto")%>">

<%
	UploadTipoCategoriaDao uploadTipoCatDao = new UploadTipoCategoriaDao(request);
	UploadTipoCategoriaUtc uploadTipoCat = new UploadTipoCategoriaUtc();
	
	List listaUtc = uploadTipoCatDao.getListUploadTipoCategoria();
	Iterator itUtc = listaUtc.iterator();
	
	ItemEstruturaUploadCategoriaDao itemEstUCDao = new ItemEstruturaUploadCategoriaDao(request);

	int anexotipotext = 0; // QtdCategorias
	
	/* criar campos hidden para controle das páginas um para cada categoria */
	int hidPagina = 0;
	while (anexotipotext < listaUtc.size()){ // QtdCategorias p/ paginação
		if(!"".equals(Pagina.getParamStr(request, "hidPagina" + anexotipotext)))
			hidPagina = Pagina.getParamInt(request, "hidPagina" + anexotipotext);
%>
		<input type="hidden" name="hidPagina<%=anexotipotext%>" value="<%=hidPagina%>">
<%
		anexotipotext++;
	}
%>

<%/* ********* INÍCIO DE CÓDIGO JAVASCRIPT PARA MONTAGEM DA TELA ******** */%>
<script language="JavaScript" src="../js/galeriaAnexo.js"></script>
<script language="JavaScript">
//aqui entram os nomes dos itens, nível 1 início.
var anexotipotext = new Array();
var anexoQtdeCat = new Array();
var anexoQtdeAnexos = new Array();
<%
	anexotipotext = 0; // zerar variável para ser utilizada novamente.
	/* código JavaScript */
	while(itUtc.hasNext()){
		uploadTipoCat = (UploadTipoCategoriaUtc) itUtc.next();
		int qtdeCat = itemEstUCDao.contarItemEstrUplCategIettucs(uploadTipoCat, itemEstrutura);
		int arquivos = 0;
		
		Iterator itArquivos = itemEstUCDao.getItemEstrUplCategIettucs(uploadTipoCat, itemEstrutura).iterator();
		while(itArquivos.hasNext()){
			ItemEstrUplCategIettuc item = (ItemEstrUplCategIettuc) itArquivos.next();
			arquivos += item.getItemEstrutUploadIettups().size();
		}
		out.print("anexotipotext[" + anexotipotext + "] = \"" + uploadTipoCat.getNomeUtc() + " (" + qtdeCat + "/" + arquivos + ")\";\n");
		out.print("anexoQtdeCat[" + anexotipotext + "] = \"" + qtdeCat + "\";\n");
		out.print("anexoQtdeAnexos[" + anexotipotext + "] = \"" + arquivos + "\";\n");
		anexotipotext++;
	}
%>

var qtdCategorias = anexotipotext.length;

var anexocategoriatext = new Array();
for (z=0;z < qtdCategorias;z++) {
	anexocategoriatext[z] = "";
}

<%
	/* Quantidade de itens por página. */
	int itensPagina = configuracaoCfg.getQtdeItensGalAnexo().intValue();//_msg.getQtdeItensPaginaPesquisa("galeriaAnexo.itensPagina");
%>

var qtdTotalItens = new Array();
var qtdItensPagina = <%=itensPagina%>;
var qtdTotPagina = new Array();

<%
	/* Iterar pela lista novamente */
	listaUtc = uploadTipoCatDao.getListUploadTipoCategoria();
	itUtc = listaUtc.iterator();
	anexotipotext = 0; // QtdCategorias
	int larguraImagem = 0;
	int alturaImagem = 0;
	int limite = 150;
	String msg = "";
	
	while(itUtc.hasNext()){
		uploadTipoCat = (UploadTipoCategoriaUtc) itUtc.next();
		
		/* Recuperar em qual página da categoria está */
		hidPagina = 0;
		if(!"".equals(Pagina.getParamStr(request, "hidPagina" + anexotipotext)))
			hidPagina = Pagina.getParamInt(request, "hidPagina" + anexotipotext);
		
		/* Calcular pelo número da página, qual o primeiro ítem da página a ser selecionado */
		int primeiroDaPagina = (hidPagina * itensPagina);
		
		/* Pegar lista de ativos ItemEstUplCategIettuc */
		List listaItemEstUC = new ArrayList();
		listaItemEstUC = itemEstUCDao.getItemEstrUplCategIettucsPaginacao(uploadTipoCat, itemEstrutura, primeiroDaPagina, itensPagina);
		Iterator itItemEstUC = listaItemEstUC.iterator();
		
		while(itItemEstUC.hasNext()){
			ItemEstrUplCategIettuc itemEstUC = (ItemEstrUplCategIettuc) itItemEstUC.next();
			String numeroAnexos = "";
			if(itemEstUC.getItemEstrutUploadIettups() != null)
				numeroAnexos = " (" + itemEstUC.getItemEstrutUploadIettups().size() + ")";
			String string = "conteudocategoriaImagem(" + anexotipotext + ",";
			
			/* na linha abaixo utilizamos o trocaBarra, pois é passado para JavaScript e há a necessida de se trocar uma barra por duas */
			if(itemEstUC.getImagemIettuc() != null){
				String caminhoArquivo = pathRaiz + 
				itemEstUC.getImagemIettuc();
				File arquivo = new File(caminhoArquivo); 
				if (arquivo.exists()){
					FileInputStream file = new FileInputStream(caminhoArquivo);
					ImageInfo imagem = new ImageInfo();
    				imagem.setInput(file);
    				if (!imagem.check()) {
						msg = "Achei o arquivo mas não a imagem!";
					}
					else{
						imagem.redimensionaImagem(limite);
						larguraImagem = imagem.getWidth();
    					alturaImagem = imagem.getHeight();
						msg = "Achei o arquivo e a imagem!";
					}
				}
				else{
					msg = "Nao achei o arquivo!";
				}
				string = string + "'" + _pathEcar + "/DownloadFile?tipo=open&RemoteFile=" + Util.trocaBarraParaDuasBarras(pathRaiz) + 
				itemEstUC.getImagemIettuc() + "',";
			}
			else {
				string = string + "'',";
			}
			string = string + "'" + itemEstUC.getNomeIettuc() + numeroAnexos + "'," + 
					 "'" + itemEstUC.getUploadTipoCategoriaUtc().getUrlJanelaUtc() + "?codIettuc=" + itemEstUC.getCodIettuc() + "',";

			string = string + "'" + itemEstUC.getDescricaoIettuc() + "'," + larguraImagem + "," + alturaImagem + ");";
			out.print(string + "\n");			
		}
		
		int qtdTotalItens = itemEstUCDao.contarItemEstrUplCategIettucs(uploadTipoCat, itemEstrutura);
		out.print("qtdTotalItens[" + anexotipotext + "] = " + qtdTotalItens + ";\n");
		
		int qtdTotPagina = qtdTotalItens / itensPagina + (qtdTotalItens % itensPagina == 0 ? 0 : 1);
		out.print("qtdTotPagina[" + anexotipotext + "] = " + qtdTotPagina + ";\n");
		
		anexotipotext++;
	}
%>

document.writeln("<div id='anexos'>");

var hidPagina = 0;
for (z=0;z < qtdCategorias;z++) {
	hidPagina = parseInt(eval("document.form.hidPagina" + z + ".value")) + 1;

	if (anexocategoriatext[z] == "") {
		anexocategoriatext[z] = "<div id='listAnexos' class='nenhumCadastrado'>Nenhuma categoria cadastrada</div>";
	}else{
		anexocategoriatext[z] += "<table width=\"95%\"><tr>";
		
		if(hidPagina == 1){
			anexocategoriatext[z] += "<td width=\"33%\" id='listAnexos' class='nenhumCadastrado' align=\"left\"></td>";
		}else{
			anexocategoriatext[z] += "<td width=\"33%\" id='listAnexos' class='nenhumCadastrado' align=\"left\">";
			anexocategoriatext[z] += "<a href=\"#\" onclick=\"javascript:aoClicarAnterior(form,"+z+");\">";
			anexocategoriatext[z] += "<- Anterior</a></td>";
		}
		
		anexocategoriatext[z] += "<td width=\"33%\" id='listAnexos' class='nenhumCadastrado' align=\"center\">Página " + hidPagina + " de " + qtdTotPagina[z] + "</td>";
		
		if(hidPagina == qtdTotPagina[z]){
			anexocategoriatext[z] += "<td width=\"33%\" id='listAnexos' class='nenhumCadastrado' align=\"left\"></td>";
		}else{
			anexocategoriatext[z] += "<td width=\"33%\" id='listAnexos' class='nenhumCadastrado' align=\"right\">";
			anexocategoriatext[z] += "<a href=\"#\" onclick=\"javascript:aoClicarProximo(form,"+z+");\">";
			anexocategoriatext[z] += "Próximo -></a></td>";
		}
		
		anexocategoriatext[z] += "</tr></table>";
	}
	
	document.writeln("<div id='anexotipomenu"+z+"'>");
	document.writeln("	<a href='javascript:setAberto(form,"+z+");mudamenuanexos("+z+")' id='anexotipo"+z+"' title='(Qtde. Grupos: "+anexoQtdeCat[z]+" / Qtde. Anexos: "+anexoQtdeAnexos[z]+")'>"+anexotipotext[z]+"</a>");

	//document.writeln("	<a href='javascript:abrePopupGeral(\"" + anexotipotext[z] + "\")'>" +anexotipotext[z]+ "</a>");

	document.writeln("	<div id='anexoconteudo"+z+"'>"+anexocategoriatext[z]+"</div>");
	document.writeln("</div>");

	document.getElementById('anexotipomenu'+z).className = 'borda';
	document.getElementById('anexotipo'+z).className = 'tipo';
	
	document.getElementById('anexoconteudo'+z).style.display = 'block';
	document.getElementById('anexoconteudo'+z).style.paddingLeft = '15px';
	
	mudamenuanexos(z);
}
document.writeln("</div>");

<%/* Estabelece se algum item fica aberto após submit */%>
<%if(!"".equals(Pagina.getParamStr(request, "hidItemAberto"))){%>
	var aberto = document.form.hidItemAberto.value;
	document.getElementById('anexotipomenu'+aberto).className = 'aberto';
	document.getElementById('anexotipo'+aberto).className = 'aberto';
	document.getElementById('anexoconteudo'+aberto).style.display = 'block';
<%}%>
</script>
<%/* ***************************** FIM DE CÓDIGO JAVASCRIPT ***************** */%>

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
<%@ include file="../include/estadoMenu.jsp"%>
<%@ include file="../include/mensagemAlert.jsp" %>
</html>