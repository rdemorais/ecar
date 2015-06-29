<%@ page import="java.util.List" %>
<%@ page import="ecar.dao.SisGrupoAtributoDao" %>

<script language="javascript" src="../../js/limpezaCamposMultivalorados.js"></script>

<%
if(configuracao != null) {

%>

<%if(itemEstrutura.getCodIett() != null){%>
	<input type="hidden" name="codIett" value="<%=itemEstrutura.getCodIett()%>">
<%} else {%>	
	<input type="hidden" name="codIett" value="">
<%}%>

<%if(codIettPrincipal > 0){%>
	<input type="hidden" name="codIettPai" value="<%=codIettPrincipal%>">
<%} else {%>	
	<input type="hidden" name="codIettPai" value="">
<%}%>

<input type="hidden" name="codEtt" value="<%=estrutura.getCodEtt()%>">
<input type="hidden" name="hidAcao" value="">
<input type="hidden" name="alterou" value="">

<%
if(atributos != null){
	Iterator it = atributos.iterator();
	while(it.hasNext()){
		ObjetoEstrutura atributo = (ObjetoEstrutura) it.next();
		%>
			<util:formItemEstrutura 
				atributo="<%=atributo%>" 
				itemEstruturaIett="<%=itemEstrutura%>" 
				desabilitar="<%=desabilitar%>" 
				seguranca="<%=seguranca%>" 
				contextPath="<%=request.getContextPath()%>"
				hidden="<%=Boolean.TRUE%>"
				request="<%=request %>"
			/>	
		<%
	} 
%>
<script language="javascript">

	<%--Unidade orçamentária precisa das informações de órgão. O campo hidden é inserido em FormItemEstruturaTag para informar
	se o campo de unidadeOrcamentariaUO está bloqueado ou não--%>	
	if (document.getElementById('unidadeOrcamentariaDiv'))
		atualizaUnidadeOrc(document.form.orgaoOrgByCodOrgaoResponsavel1Iett, document.form.unidadeOrcamentariaUOHidden.value);
	
	function abrirPopUpUpload(nome, labelCampo){
	abreJanela("../../usuarios/usuarios/popUpUpload.jsp?nomeCampo="+nome+"&labelCampo="+labelCampo+"&excluir=",
				500, 100, nome);
	}
	function abrirPopUpUpload(nome, labelCampo, excluir){
		abreJanela("../../usuarios/usuarios/popUpUpload.jsp?nomeCampo="+nome+"&labelCampo="+labelCampo+"&excluir="+excluir,
					500, 100, nome);
	}	
</script>
<%
}
%>
<%} else {
	out.println("<br> Efetue a Configura&ccedil;&atilde;o Geral do Sistema antes de realizar o Cadastro de Itens <br><br> ");
}%>

<!-- usado para guardar a estrutura detalhada na tela de cadastro -->
<input type=hidden name="codIettPrincipal" value="<%=Pagina.getParam(request, "codIettPrincipal")%>">
<input type=hidden name="ultEttSelecionado" value="<%=Pagina.getParam(request, "ultEttSelecionado")%>">


<% /* Controla o estado do Menu (aberto ou fechado) */%>
<%@ include file="../../include/estadoMenu.jsp"%>