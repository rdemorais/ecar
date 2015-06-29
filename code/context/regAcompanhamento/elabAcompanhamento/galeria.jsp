<%@ include file="/login/validaAcesso.jsp"%>
<%@ include file="/frm_global.jsp"%>

<%@ page import="ecar.dao.AcompReferenciaItemDao" %>
<%@ page import="ecar.dao.ItemEstruturaUploadCategoriaDao" %>
<%@ page import="ecar.dao.UploadTipoCategoriaDao" %>
<%@ page import="ecar.pojo.AcompReferenciaItemAri" %>
<%@ page import="ecar.pojo.ItemEstrUplCategIettuc" %>
<%@ page import="ecar.pojo.UploadTipoCategoriaUtc" %>
<%@ page import="ecar.permissao.ValidaPermissao" %> 
<%@ page import="ecar.pojo.ImageInfo" %>
<%@ page import="ecar.pojo.UsuarioUsu" %>

<%@ page import="comum.util.Data" %> 
<%@ page import="comum.util.FileUpload" %> 
<%@ page import="comum.util.Util" %> 

<%@ page import="java.util.List" %> 
<%@ page import="java.util.Iterator" %> 
<%@ page import="java.io.File" %>
<%@ page import="java.io.FileInputStream" %>
<%@ page import="ecar.dao.ConfiguracaoDao" %>

<%@ taglib uri="/WEB-INF/ecar-util.tld" prefix="util" %>
<%@ taglib uri="/WEB-INF/ecar-combo.tld" prefix="combo" %>

<%
try{ 
	/* carrega o usuário da session */
	UsuarioUsu usuario = ((ecar.login.SegurancaECAR)session.getAttribute("seguranca")).getUsuario();
	
	ConfiguracaoCfg configuracao = (ConfiguracaoCfg)session.getAttribute("configuracao");
	String pathRaiz = configuracao.getRaizUpload();	

	AcompReferenciaItemDao acompReferenciaItemDao = new AcompReferenciaItemDao(request); 
	
	String strCodAri = Pagina.getParamStr(request, "codAri");
	
	if(!"".equals(Pagina.getParamStr(request, "referencia"))){
		// Veio nova referência, selecionada na combo, deve trocar o Ari.
		strCodAri = Pagina.getParamStr(request, "referencia");
	}
	
	AcompReferenciaItemAri acompReferenciaItem = (AcompReferenciaItemAri) acompReferenciaItemDao.buscar(AcompReferenciaItemAri.class, Long.valueOf(strCodAri));
	
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
<link rel="stylesheet" href="<%=_pathEcar%>/css/css_galeria_<%=configuracaoCfg.getEstilo().getNome()%>.jsp" type="text/css">

<script language="javascript" src="../../js/menu_retratil.js"></script>
<script language="javascript" src="../../js/focoInicial.js"></script>
<script language="javascript" src="../../js/windowopen.js"></script>
<script language="javascript" src="../../js/popUp.js"></script>
<script>
function recarregar(form){
	form.action = "galeria.jsp";
	form.submit();
}
function trocaAba(link, codAri){
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
</script>
</head>

<body>

<%@ include file="/cabecalho.jsp" %>
<%@ include file="/divmenu.jsp"%> 

<div id="conteudo"> 

<!-- TÍTULO -->
<%@ include file="/titulo_tela.jsp"%>

<%@ include file="botoesAvancaRetrocede.jsp" %>
	<br>
	
	<util:barraLinksRegAcomp 
		acompReferenciaItem="<%=acompReferenciaItem%>"  
		selectedFuncao="GALERIA"
		usuario="<%=usuario%>"
		primeiroAcomp="<%=Pagina.getParamStr(request, "primeiroAcomp")%>"
		request="<%=request%>"
		gruposUsuario="<%=seguranca.getGruposAcesso() %>"
	/>
	
	<br>
	
	<table width="100%" border="0" cellpadding="0" cellspacing="0">
		<tr><td class="espacador"><img src="../../images/pixel.gif"></td></tr>
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
		<tr><td class="espacador"><img src="../../images/pixel.gif"></td></tr>
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

<form  name="form" method="post" >

<input type="hidden" name="codAri" value="<%=Pagina.getParamStr(request, "codAri")%>">
<input type="hidden" name="hidItemAberto" value="<%=Pagina.getParamStr(request, "hidItemAberto")%>">
<input type=hidden name="primeiroAcomp" value="<%=Pagina.getParamStr(request, "primeiroAcomp")%>">
<input type=hidden name="codAcomp" value="<%=Pagina.getParamStr(request, "codAcomp")%>">

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
<script language="JavaScript">
//aqui entram os nomes dos itens, nível 1 início.
var anexotipotext = new Array();
<%
	anexotipotext = 0; // zerar variável para ser utilizada novamente.
	/* código JavaScript */
	while(itUtc.hasNext()){
		uploadTipoCat = (UploadTipoCategoriaUtc) itUtc.next();
		int qtdeCat = itemEstUCDao.contarItemEstrUplCategIettucs(uploadTipoCat, acompReferenciaItem.getItemEstruturaIett());
		int arquivos = 0;
		
		Iterator itArquivos = itemEstUCDao.getItemEstrUplCategIettucs(uploadTipoCat, acompReferenciaItem.getItemEstruturaIett()).iterator();
		while(itArquivos.hasNext()){
			ItemEstrUplCategIettuc item = (ItemEstrUplCategIettuc) itArquivos.next();
			arquivos += item.getItemEstrutUploadIettups().size();
		}
		
		out.print("anexotipotext[" + anexotipotext + "] = \"" + uploadTipoCat.getNomeUtc() + " (" + qtdeCat + "/" + arquivos + ")\";\n");
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
		listaItemEstUC = itemEstUCDao.getItemEstrUplCategIettucsPaginacao(uploadTipoCat, acompReferenciaItem.getItemEstruturaIett(), primeiroDaPagina, itensPagina);
		Iterator itItemEstUC = listaItemEstUC.iterator();
		
		UsuarioUsu usuarioImagem = null;  
		String hashNomeArquivo = null;
		
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
				
				usuarioImagem = ((ecar.login.SegurancaECAR)request.getSession().getAttribute("seguranca")).getUsuario(); 
				hashNomeArquivo = Util.calcularHashNomeArquivo(pathRaiz, itemEstUC.getImagemIettuc());
				Util.adicionarMapArquivosAtuaisUsuarios(usuarioImagem, hashNomeArquivo, pathRaiz, itemEstUC.getImagemIettuc());
				
				string = string + "'" + _pathEcar + "/DownloadFile?tipo=open&RemoteFile=" + hashNomeArquivo + "',";
			}
			else {
				string = string + "'',";
			}
			string = string + "'" + itemEstUC.getNomeIettuc() + numeroAnexos + "'," + 
					 "'../../relAcompanhamento/" + itemEstUC.getUploadTipoCategoriaUtc().getUrlJanelaUtc() + "?codIettuc=" + itemEstUC.getCodIettuc() + "',";

			string = string + "'" + itemEstUC.getDescricaoIettuc() + "'," + larguraImagem + "," + alturaImagem + ");";
			out.print(string + "\n");			
		}
		
		int qtdTotalItens = itemEstUCDao.contarItemEstrUplCategIettucs(uploadTipoCat, acompReferenciaItem.getItemEstruturaIett());
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
	document.writeln("	<a href='javascript:setAberto(form,"+z+");mudamenuanexos("+z+")' id='anexotipo"+z+"'>"+anexotipotext[z]+"</a>");

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
<%@ include file="/include/estadoMenu.jsp"%>
<%@ include file="/include/mensagemAlert.jsp" %>
</html>