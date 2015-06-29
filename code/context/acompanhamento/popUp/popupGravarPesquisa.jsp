<jsp:directive.page import="ecar.pojo.PesquisaGrpAcesso"/>
<jsp:directive.page import="ecar.popup.PopUpFonteRecurso"/>
<jsp:directive.page import="ecar.popup.PopUpUsuario"/>
<jsp:directive.page import="ecar.dao.PesquisaGrpAcessoDao"/>
<jsp:directive.page import="ecar.dao.LinkDao"/>
<jsp:directive.page import="ecar.pojo.Link"/>
<%@ taglib uri="/WEB-INF/ecar-util.tld" prefix="util" %>
<%@ include file="/frm_global.jsp"%>
<%@ include file="/login/validaAcesso.jsp"%>
<%@ page import="ecar.popup.PopUpPesquisa" %>
<%@ page import="java.util.Enumeration" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.lang.Math" %>

<html lang="pt-br">
<head>
<%@ include file="../../include/meta.jsp"%>
<%@ include file="../../titulo.jsp"%>
<link rel="stylesheet" href="<%=_pathEcar%>/css/style_<%=configuracaoCfg.getEstilo().getNome()%>.css" type="text/css">
<script language="javascript" src="../../js/focoInicial.js"></script>
<script language="javascript" src="../../js/frmPesquisa.js"></script>
<script language="javascript" src="../../js/validacoes.js"></script>
<script language="javascript" src="<%=_pathEcar%>/js/destaqueLinha.js"></script>
<script language="javascript">
function validar(form){
	// validar os demais campos!
	if(!validaString(form.nomePesquisa,'Texto',true)){
		return(false);
	}
	return(true);
}
function aoClicarGravar(form){
	if(validar(form)){
		form.hidAcao.value = "incluir";
		form.action = "ctrlPesquisa.jsp"
		form.submit();
	}
}	
</script>

</head>



<!-- sempre colocar o foco inicial no primeiro campo -->
<body onload="onLoad(document.form);" class="corpo_popup">

<form name="form" method="post" action="" onsubmit="">
		<h1><b><%
		Link  link = new Link();
		link.setNomeLink("SALVAR_PESQUISA");
		List listLink = (new LinkDao(request)).pesquisar(link, new String[]{"codLink","asc"});
		if (listLink!= null && listLink.size()>0 ){
			link = (Link) listLink.get(0);
			if( link.getLabelLink()==null || link.getLabelLink().trim().equals("") ){
				link.setLabelLink("Salvar Pesquisa");
			} 
		} else {
			link.setLabelLink("Salvar Pesquisa");
		}
		
		out.print(link.getLabelLink());
		 %> </b><h3>
		<table class="form">
			<tr>
				<td class="label" width="35%">Nome</td>
				<td>
					<input type="text" name="nomePesquisa" size="40" maxlength="30" value="">
					<input type="hidden" name="hidAcao" value="incluir"> 	
					<input type="hidden" name="periodo" value="<%=Pagina.getParamStr(request, "periodo") %>">
					<input type="hidden" name="acompReferenciaAref" value="<%=Pagina.getParamStr(request, "acompReferenciaAref") %>">
					<input type="hidden" name="tipoAcompanhamentoTa" value="<%=Pagina.getParamStr(request, "codTipoAcompanhamento") %>">
					<!-- <input type="hidden" name="itemEstruturaIetts" value=""><!-- está na seção -->
				</td>
			</tr>

			<%
			PesquisaGrpAcesso configPesquisaGrpAcesso = new PesquisaGrpAcesso();
			PesquisaGrpAcessoDao pesqGrpAcessoDao = new PesquisaGrpAcessoDao();
			configPesquisaGrpAcesso = pesqGrpAcessoDao.getConfiguracaoPesquisaGrupoAcesso(seguranca.getGruposAcesso());
			
			if (configPesquisaGrpAcesso.getIndPodeCriarPesquisaSistema().equals("S")){
			%>
			<tr>
				<%	
					if (configPesquisaGrpAcesso.getIndPodeCriarPesquisaUsuario().equals("N")){
				%>
					<input type="hidden" name="indTipoPesquisa" value="S" >	
				<%	
					}else{
						if (configPesquisaGrpAcesso.getIndPodeCriarPesquisaUsuario().equals("S")){
				%>	
					<td class="label">Filtro do sistema</td>
					<td align="left">
							<input type="checkbox" name="indTipoPesquisa" value="S" > 
					</td>
				<%	
						}
					}
				%>	
			</tr>
			<%	
			} 
			%>
			
			<%
			if (configPesquisaGrpAcesso.getIndPodeCriarPesquisaSistema().equals("N")){
			%>
			<tr>
				<%	
					if (configPesquisaGrpAcesso.getIndPodeCriarPesquisaUsuario().equals("N")){
						// NÃO É EXIBIDO O BOTÃO SALVAR
						// VERIFICAR COMO TRATA O SENTINELA
				%>
				<%	
					}else{
						if (configPesquisaGrpAcesso.getIndPodeCriarPesquisaUsuario().equals("S")){
				%>	
						<input type="hidden" name="indTipoPesquisa" value="U" >	
				<%	
						}
					}
				%>	
			</tr>
			<%	
			} 
			%>

		
			<tr>
				<td class="label">Ciclo de referência</td>
				<td>
					<input type="radio" name="indTipoReferencia" value="U" checked >Última Referência
				</td>
			</tr>
			<tr>
				<td class="label"></td>
				<td>
					<input type="radio" name="indTipoReferencia" value="A">Referência Atual
				</td>
			</tr>
			<tr>
				<td class="label"></td>
				<td>
					<input type="radio" name="indTipoReferencia" value="E">Referência Exibida<p>
				</td>
			</tr>
		</table>
		<table align="center">
			<tr align="center">
				<td align="center">
					<input type="button" name="btnOk" class="botao" value="Ok" onclick="aoClicarGravar(document.form);">
				</td>
			</tr>
		</table>	
			
</form>
</body>
</html>