<%@ page import="ecar.dao.SisGrupoAtributoDao" %>
<%@ page import="ecar.dao.ConfiguracaoDao" %>
<%@ page import="java.util.List" %>
<%@ page import="ecar.pojo.SisAtributoSatb" %>
<%@ page import="ecar.pojo.SisGrupoAtributoSga" %>
<%@ page import="ecar.pojo.PontocriticoCorPtccor" %>
<%@ page import="ecar.pojo.PontocriticoCorPtccorPK" %>
<%@ page import="java.util.Iterator" %>
<%@ page import="ecar.dao.CorDao" %>
<%@ page import="ecar.pojo.Cor" %>
<%@ page import="ecar.dao.PontocriticoCorPtccorDAO" %>
<%@ page import="comum.util.Pagina" %>
<%@ page import="ecar.dao.ConfigMailCfgmDAO" %>
<%@ page import="comum.util.Data" %>
<%@ page import="ecar.pojo.ConfigMailCfgm" %>
<jsp:directive.page import="ecar.util.Dominios"/>
<jsp:directive.page import="ecar.dao.EstruturaDao"/>
<jsp:directive.page import="ecar.pojo.ObjetoEstrutura"/>

<input type="hidden" name="labelEttf" value="<%=estruturaFuncao.getLabelEttf()%>">
<input type=hidden name="codIett" value="<%=itemEstrutura.getCodIett()%>">
<input type=hidden name="cod" value="<%=Pagina.getParamStr(request, "cod")%>">
<input type=hidden name="codPtc" value="<%=Pagina.getParamStr(request, "codPtc")%>">
<input type=hidden name="codAba" value="<%=Pagina.getParamStr(request, "codAba")%>">
<input type=hidden name="hidAcao" value="">



	<%
	
	if(atributos != null){
		Iterator it = atributos.iterator();
		while(it.hasNext()){
			ObjetoEstrutura atributo = (ObjetoEstrutura) it.next();
			%>
				<util:formPontoCriticoTag 
					atributo="<%=atributo%>" 
					pontoCriticoPtc="<%=pontoCritico%>"
					iett="<%=itemEstrutura%>" 
					desabilitar="<%=_disabled.equals("disabled") ? Boolean.TRUE: Boolean.FALSE%>" 
					seguranca="<%=seguranca%>" 
					exibirBotoes="<%=Boolean.TRUE%>"
					request="<%=request%>"
					acompReferenciaItemAri="<%=null%>"
					contextPath="<%=request.getContextPath()%>"
					novoPontoCritico="<%= novoPontoCritico %>"
				/>	
			<%
		} 
	}
	%>

	<% /* Controla o estado do Menu (aberto ou fechado) */%>
<%@ include file="../../include/estadoMenu.jsp"%>
