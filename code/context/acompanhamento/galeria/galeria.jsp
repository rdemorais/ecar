<jsp:directive.page import="ecar.pojo.UsuarioUsu"/>
<jsp:directive.page import="ecar.pojo.AcompReferenciaAref"/>
<%@ include file="../../login/validaAcesso.jsp"%>
<%@ include file="../../frm_global.jsp"%>

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
try { 

	/* carrega o usuário da session */
	UsuarioUsu usuario = ((ecar.login.SegurancaECAR)session.getAttribute("seguranca")).getUsuario();
	
	//Estrutura
	ItemEstruturaIett itemEstrutura = null;
	AcompReferenciaItemAri ari = null;
	AcompReferenciaAref arefSelecionado = null;
	
	// Configuração	
	//ConfiguracaoCfg configuracao = new ConfiguracaoDao(request).getConfiguracao();
	ConfiguracaoCfg cfg = (ConfiguracaoCfg)session.getAttribute("configuracao");
	String pathRaiz = cfg.getRaizUpload();	
	
	// Request
	String codIettRelacao = Pagina.getParamStr(request, "codIettRelacao");
	String strCodAri = Pagina.getParamStr(request, "codAri");
	String referencia= Pagina.getParamStr(request, "referencia");
	String linkControle = Pagina.getParamStr(request, "linkControle");
	String codTipoAcompanhamento = Pagina.getParamStr(request, "codTipoAcompanhamento");
	String itemDoNivelClicado = Pagina.getParamStr(request, "itemDoNivelClicado");
	String mesReferencia = Pagina.getParamStr(request, "referencia_hidden");
	
	//Em visualizar tem mais esse teste-> !"S".equals(Pagina.getParamStr(request, "linkControle")
	if(!"".equals(referencia)){
		// Veio nova referência, selecionada na combo, deve trocar o Ari.
//		strCodAri = Pagina.getParamStr(request, "referencia");
		strCodAri = Pagina.getParamStr(request, "codAri");
	}
	
	AcompReferenciaItemDao ariDao = new AcompReferenciaItemDao(request);
	// Se o codAri tiver sido passado como parametro
	if(!"".equals(strCodAri)) {
	
		// Busca o item de estrutura pelo codAri passado como parametro
		ari = (AcompReferenciaItemAri) ariDao.buscar(
						AcompReferenciaItemAri.class, Long.valueOf(strCodAri));
		itemEstrutura = ari.getItemEstruturaIett();
		
		arefSelecionado = ari.getAcompReferenciaAref();
		
			
	} else if(!"".equals(codIettRelacao)) {
	
		ItemEstruturaDao itemEstruturaDao = new ItemEstruturaDao(request);
		itemEstrutura = (ItemEstruturaIett)itemEstruturaDao.buscar(ItemEstruturaIett.class, Long.valueOf(codIettRelacao));
	}
	
	ValidaPermissao validaPermissao = new ValidaPermissao();
	String funcaoSelecionada = "GALERIA";
	
	
%>

<html lang="pt-br"> 
<head>
<%@ include file="../../include/meta.jsp"%>
<%@ include file="/titulo.jsp"%>
<link rel="stylesheet" href="<%=_pathEcar%>/css/style_<%=configuracaoCfg.getEstilo().getNome()%>.css" type="text/css">
<link rel="stylesheet" href="<%=_pathEcar%>/css/css_galeria_<%=configuracaoCfg.getEstilo().getNome()%>.jsp" type="text/css">
<script language="javascript" src="../../js/menu_retratil.js"></script>
<script language="javascript" src="../../js/focoInicial.js"></script>
<script language="javascript" src="../../js/windowopen.js"></script>
<script language="javascript" src="../../js/popUp.js"></script>
<script language="javascript">
function trocarAba(nomeAba) {
	document.forms[0].action = nomeAba;
	document.forms[0].clicouAba.value = "S";
	document.forms[0].submit();
}
<%--function voltarTelaAnterior(tela) {
	document.forms[0].action = tela;
	document.forms[0].submit();
}--%>

function recarregar(form){
	form.action = "galeria.jsp";
	form.submit();
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
	document.forms[0].clicouAba.value = "S";
	document.forms[0].submit();
}
</script>
</head>

<body>


<%@ include file="../../cabecalho.jsp" %>
<%@ include file="../../divmenu.jsp"%> 

<div id="conteudo"> 

	<%	
	String periodo = Pagina.getParamStr(request, "periodo");
	%>
	
<%@ include  file="../form_registro.jsp" %>	

<form  name="form" method="post" >

<!-- Campos para manter a seleção em Posição Geral -->

<!-- ESCOLHER UMA VARIAVEL PRA GUARDAR -->
<input type="hidden" name="codTipoAcompanhamento" value="<%=codTipoAcompanhamento%>">
<input type="hidden" name="referencia_hidden" value="<%=Pagina.getParamStr(request, "referencia_hidden")%>">
<input type="hidden" name="codAcomp" value="<%=Pagina.getParamStr(request, "codAcomp")%>">
<input type="hidden" name="periodo" value="<%=Pagina.getParamStr(request, "periodo")%>">
<input type="hidden" name="mesReferencia" value="<%=ari.getAcompReferenciaAref().getCodAref().toString()%>">
<input type="hidden" name="niveisPlanejamento" value="<%=Pagina.getParamStr(request, "niveisPlanejamento")%>">
<input type="hidden" name="primeiroIettClicado" value="<%=Pagina.getParamStr(request, "primeiroIettClicado")%>">
<input type="hidden" name="primeiroAriClicado" value="<%=Pagina.getParamStr(request, "primeiroAriClicado")%>">
<input type="hidden" name="codIettRelacao" value="<%=Pagina.getParamStr(request, "codIettRelacao")%>">
<input type="hidden" name="itemDoNivelClicado" value="<%=Pagina.getParamStr(request, "itemDoNivelClicado")%>">
<input type="hidden" name="codigosItem" value="<%=Pagina.getParamStr(request, "codigosItem")%>">
<!-- Campo necessario para guardar a forma de visualização escolhida no filtro -->
<input type="hidden" name="hidFormaVisualizacaoEscolhida" value='<%=Pagina.getParamStr(request, "hidFormaVisualizacaoEscolhida")%>'>

<!-- Campo necessário para botões de Avança/Retrocede -->
<input type="hidden" name="codArisControle" value="<%=Pagina.getParamStr(request, "codArisControle")%>">

<!-- Campos para manter a seleção em Posição Geral -->

<input type="hidden" name="codAri" value="<%=Pagina.getParamStr(request, "codAri")%>">
<input type="hidden" name="hidItemAberto" value="<%=Pagina.getParamStr(request, "hidItemAberto")%>">

<!-- PODE SER QUE USE -->
<input type=hidden name="primeiroAcomp" value="<%=Pagina.getParamStr(request, "primeiroAcomp")%>">
<input type="hidden" name="clicouAba" value="<%= Pagina.getParamStr(request, "clicouAba")%>">
<input type="hidden" name="tipoPadraoExibicaoAba" value="<%= Pagina.getParamStr(request, "tipoPadraoExibicaoAba")%>">

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
<script language="JavaScript" src="../../js/galeriaAnexo.js"></script>
<script language="JavaScript" src="../../js/galeria_style_<%=configuracao.getEstilo().getNome()%>.js"></script>
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
	int itensPagina = configuracao.getQtdeItensGalAnexo().intValue();//_msg.getQtdeItensPaginaPesquisa("galeriaAnexo.itensPagina");
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
		UsuarioUsu usuarioImagem = null;  
		String hashNomeArquivo = null;
		
		while(itItemEstUC.hasNext()){
			ItemEstrUplCategIettuc itemEstUC = (ItemEstrUplCategIettuc) itItemEstUC.next();
			if(itemEstUC.getItemEstrutUploadIettups().size() > 0){
				String numeroAnexos = "";
				if(itemEstUC.getItemEstrutUploadIettups() != null)
					numeroAnexos = " (" + itemEstUC.getItemEstrutUploadIettups().size() + ")";
				String string = "conteudocategoriaImagem(" + anexotipotext + ",";
				
				/* na linha abaixo utilizamos o trocaBarra, pois é passado para JavaScript e há a necessida de se trocar uma barra por duas */
				if(itemEstUC.getImagemIettuc() != null){
					String caminhoArquivo = pathRaiz + itemEstUC.getImagemIettuc();
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
					
					usuarioImagem = ((ecar.login.SegurancaECAR)request.getSession().getAttribute("seguranca")).getUsuario(); 
					hashNomeArquivo = Util.calcularHashNomeArquivo(pathRaiz, itemEstUC.getImagemIettuc());
					Util.adicionarMapArquivosAtuaisUsuarios(usuarioImagem, hashNomeArquivo, pathRaiz, itemEstUC.getImagemIettuc());	
					
					string = string + "'" + _pathEcar + "/DownloadFile?tipo=open&RemoteFile=" + hashNomeArquivo + "',";
//					itemEstUC.getImagemIettuc() + "',";
				}
				else {
					string = string + "'',";
				}
				string = string + "'" + itemEstUC.getNomeIettuc() + numeroAnexos + "'," + 
						 "'" + itemEstUC.getUploadTipoCategoriaUtc().getUrlJanelaUtc() + "?codIettuc=" + itemEstUC.getCodIettuc() + "',";
	
				// SM: 193663 (SS 70171), Mantis: 0021300
				// Retirada do caractere enter da descrição da categoria
				string = string + "'" + itemEstUC.getDescricaoIettuc().replaceAll("\r\n", "") + "'," + larguraImagem + "," + alturaImagem + ");";
				out.print(string + "\n");
			}
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
<%@ include file="../../include/estadoMenu.jsp"%>
<%@ include file="../../include/mensagemAlert.jsp" %>
</html>