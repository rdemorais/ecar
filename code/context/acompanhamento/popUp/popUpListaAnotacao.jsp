<%@ taglib uri="/WEB-INF/tlds/c.tld" prefix="c"%> 
<%@ include file="/frm_global.jsp"%>

<%@ page import="ecar.dao.ItemEstruturaDao" %>
<%@ page import="ecar.dao.ItemEstrutMarcadorDao" %>

<%@ page import="ecar.pojo.UsuarioUsu" %>
<%@ page import="ecar.pojo.ItemEstruturaIett" %>
<%@ page import="ecar.pojo.ItemEstrutMarcadorIettm" %>

<%@ page import="java.util.Collection" %>
<%@ page import="java.util.Iterator" %>

<html lang="pt-br">
<head>
<%@ include file="/include/meta.jsp"%>
<%@ include file="/titulo.jsp"%>

<link rel="stylesheet" href="<%=_pathEcar%>/css/style_<%=configuracaoCfg.getEstilo().getNome()%>.css" type="text/css">

<script language="javascript" src="../../js/focoInicial.js"></script>
<script language="javascript" src="../../js/frmPadraoItemEstrut.js"></script>
<script language="javascript" src="../../js/frmPesquisa.js"></script>
<script language="javascript" src="../../js/validacoes.js"></script>
<script>
function aoClicarExcluir(form){
	<% 
		// Mantis #8809
		// Ocorria erro na tentativa de excluir uma anotação sem selecioná-la antes.
	%>
	var elements = document.getElementsByTagName("INPUT");
	var total    = elements.length;
	var i        = -1;
	var selecionados = '';
  
   	while( ++i < total ) {
    	if ( (elements.item(i).type  == "checkbox") && (elements.item(i).value != "todos") ) {
        	if( elements.item(i).checked == true ) {
         	  	selecionados = selecionados + elements.item(i).value + ";";
        	}
        }
   	}
   
	if( selecionados == "" ) {
    	alert("Não há ítens selecionados!");
   	} else {
		form.action = "popUpCtrlAnotacao.jsp";
		form.hidAcao.value = "excluir";
		form.submit();
	}
}
function aoClicarConsultar(form, codigo){
	form.codIettm.value = codigo;
	form.hidAcao.value = "alterar";
	form.action = "popUpFormAnotacao.jsp";
	form.submit();
}
</script>
<!-- sempre colocar o foco inicial no primeiro campo -->
<body class="corpo_popup">

<div>

<H1>Anotações</H1>

<%
try{

	UsuarioUsu usuario = ((ecar.login.SegurancaECAR)session.getAttribute("seguranca")).getUsuario();
	ItemEstruturaIett item = (ItemEstruturaIett) new ItemEstruturaDao(request).buscar(ItemEstruturaIett.class, Long.valueOf(Pagina.getParamStr(request, "codIett")));
	ItemEstrutMarcadorDao itemEstrutMarcadorDao = new ItemEstrutMarcadorDao(request);
	Collection marcadores = itemEstrutMarcadorDao.getMarcadoresByUsuario(item, usuario);
	
	String origem = Pagina.getParamStr(request, "origem");
%>
<form name="form">
	<input type="hidden" name="codIett" value=<%=item.getCodIett()%>>
	<input type="hidden" name="codIettm" value="">	
	<input type="hidden" name="codUsu" value=<%=usuario.getCodUsu()%>>
	<input type="hidden" name="hidAcao" value="">	
		
	<table width="100%" border="0" cellpadding="0" cellspacing="0" class="form">
		<tr>
			<td>
			<b><%=item.getEstruturaEtt().getNomeEtt()%> <%=new ItemEstruturaDao(request).criaColunaConteudoColunaArvoreAjax(item, item.getEstruturaEtt())%></b>
			</td>
		</tr>	
		<tr class="linha_titulo" align="right"> 
			<td>
				<c:if test="<%=!origem.equals("p")%>">	
					<input type="button" value="Excluir" class="botao" onclick="javascript:aoClicarExcluir(document.form);">
				</c:if>
				<input type="button" value="Fechar" class="botao" onclick="javascript:window.close();window.opener.reload();">				
			</td>
		</tr>
	</table>
	<table width="100%" border="0" cellpadding="0" cellspacing="0">
		<tr><td class="espacador" colspan="4"><img src="../../images/pixel.gif"></td></tr>
		<tr class="linha_subtitulo">
			<td width="5%"><input type=checkBox class="form_check_radio" name=todos value="todos" onclick="javascript:selectAll(document.form)"></td>
			<td width="5%">&nbsp;</td>
			<td>Data</td>
			<td>Descri&ccedil;&atilde;o</td>
		</tr>	
		<tr><td class="espacador" colspan="4"><img src="../../images/pixel.gif"></td></tr>		
		<%
			
		Iterator it = marcadores.iterator();
		while(it.hasNext()){
			ItemEstrutMarcadorIettm marcador = (ItemEstrutMarcadorIettm) it.next();
			%>
			<tr class="linha_subtitulo2">
				<td >
					<input type="checkbox" class="form_check_radio" name="excluir" id="excluir" value="<%=marcador.getCodIettm()%>">
				</td>
				<td>
				<%if(marcador.getCor() != null){%>
					<img src='../../images/relAcomp/<%=marcador.getCor().getNomeCor().toLowerCase()%>_inf.png' border='0'>
				<%} else {%>
					<img src='../../images/relAcomp/branco_inf.png' border='0'>
				<%}%>
				</td>
				<td>
				<%if (origem.equals("p")){%>
					<%=Pagina.trocaNullData(marcador.getDataInclusaoIettm())%>
				<%}else{ %>
									
					<a href="javascript:aoClicarConsultar(document.form,<%=marcador.getCodIettm()%>)">
						<%=Pagina.trocaNullData(marcador.getDataInclusaoIettm())%>
					</a>
				<%} %>
				
				</td>		
				<td>
				<%=Pagina.trocaNull(marcador.getDescricaoIettm())%>
				</td> 
			</tr>					
			<%
		} 
} catch(ECARException e){
	org.apache.log4j.Logger.getLogger(this.getClass()).error(e);
	Mensagem.alert(_jspx_page_context, e.getMessageKey());
} catch (Exception e){
%>
	<%@ include file="/excecao.jsp"%>
<%
}
%>	
</form>		
</div>
</body>
</html>
