<%@ page import="ecar.dao.CorDao" %>
<%@ page import="ecar.pojo.Cor" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.Iterator" %>
<%@ page import="ecar.permissao.ValidaPermissao" %> 
<%@ page import="ecar.pojo.ItemEstruturaIett" %>
<%@ page import="ecar.dao.ItemEstruturaDao" %>
<%@ page import="ecar.pojo.ItemEstrutMarcadorIettm" %>
<%@ page import="ecar.dao.ItemEstrutMarcadorDao" %>
<jsp:directive.page import="ecar.login.SegurancaECAR"/>
<%@ taglib uri="/WEB-INF/ecar-util.tld" prefix="util" %>
<%@ include file="../frm_global.jsp"%>

<html lang="pt-br">
<head>
<%@ include file="../include/meta.jsp"%>
<%@ include file="/titulo.jsp"%>
<link rel="stylesheet" href="<%=_pathEcar%>/css/style_<%=configuracaoCfg.getEstilo().getNome()%>.css" type="text/css">
<script language="javascript" src="../js/focoInicial.js"></script>
<script language="javascript" src="../js/frmPesquisa.js"></script>
<script language="javascript" src="../js/validacoes.js"></script>

<%
ItemEstrutMarcadorIettm marcador = new ItemEstrutMarcadorIettm();
if(!"".equals(Pagina.getParamStr(request, "codIettm"))){
	marcador = (ItemEstrutMarcadorIettm) new ItemEstrutMarcadorDao(request)
										.buscar(ItemEstrutMarcadorIettm.class, 
										Long.valueOf(Pagina.getParamStr(request, "codIettm")));
}
%>

<script>
function aoClicarBtn1(form){
	if(form.codIettm.value != "")
		form.hidAcao.value = "alterar";
	else
		form.hidAcao.value = "incluir";	
	form.action = "popUpCtrlAnotacao.jsp";
	if(validar(form))
		form.submit();
}
function validar(form){
	if(!validaString(form.descricao,'Descrição',true))
		return false;
	return true;
}
function aoClicarBtn2(form){
	window.close();
}
</script>

</head>

<body class="corpo_popup">
<%
try{
%>
<H1>Inserir Anotação</H1>

<form name="form" method="post" action="">
	
	<input type="hidden" name="hidAcao">
	<%
	Long codUsu = ((ecar.login.SegurancaECAR)session.getAttribute("seguranca")).getCodUsu();
	%>
	<input type="hidden" name="codUsu" value=<%=codUsu%>>	
	<input type="hidden" name="codIettm" value=<%=Pagina.trocaNull(marcador.getCodIettm())%>>		
	<input type="hidden" name="codIett" value=<%=Pagina.getParamStr(request, "codIett")%>>		
	<util:barrabotoes btn1="Gravar" btn2="Cancelar"/>

	<table class="form">
		<tr>
			<td>
			<%
			ItemEstruturaIett item = (ItemEstruturaIett) new ItemEstruturaDao(request).buscar(ItemEstruturaIett.class, Long.valueOf(Pagina.getParamStr(request, "codIett")));
			
			ValidaPermissao validaPermissao = new ValidaPermissao();
			if ( !validaPermissao.permissaoConsultaParecerIETT( item.getCodIett() , (SegurancaECAR)session.getAttribute("seguranca") ) )
			{
				response.sendRedirect( request.getContextPath() +  "/acessoIndevido.jsp" );
			}
			
			%>
			<b><%=item.getEstruturaEtt().getNomeEtt()%></b> - <b><%=item.getNomeIett()%></b>
			</td>
		</tr>
		<tr>
			<td>
				<textarea name="descricao" rows="5" cols="40"><%=Pagina.trocaNull(marcador.getDescricaoIettm())%></textarea>
			</td>
		</tr>
		<tr >
				<td>
				<input type="radio" class="form_check_radio" name="codCor" value=""
				<%if(marcador.getCor() == null) out.println("checked");%>
				> <img src='../images/relAcomp/branco_inf.png' border='0'>  Sem Prioridade
				</td>
		</tr>

		<%
		List cores = new CorDao(request).listar(Cor.class, new String[]{"significadoCor", "asc"});
		Iterator it = cores.iterator();
		while(it.hasNext()){
			Cor cor = (Cor) it.next();
			%>
			<tr >
				<td>
				<input type="radio" class="form_check_radio" name="codCor" value="<%=cor.getCodCor()%>"
				<%
				if(marcador.getCor() != null && marcador.getCor().getCodCor().equals(cor.getCodCor())){
					out.println("checked");
				}
				%>
				> <img src='../images/relAcomp/<%=cor.getNomeCor().toLowerCase()%>_inf.png' border='0'> <%=cor.getSignificadoCor()%>
				</td>
			</tr>
			<%
		}
		%>
	</table>
	
	<util:barrabotoes btn1="Gravar" btn2="Cancelar"/>
	
</form>
<%
} catch (Exception e){
%>
	<%@ include file="/excecao.jsp"%>
<%
}
%>
</body>

</html>