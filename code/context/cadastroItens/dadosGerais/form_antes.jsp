<jsp:directive.page import="ecar.pojo.ObjetoEstrutura"/>
<jsp:directive.page import="java.util.Iterator"/>
<%
if(atributosAntes != null){
	Iterator it = atributosAntes.iterator();
	while(it.hasNext()){
		ObjetoEstrutura atributo = (ObjetoEstrutura) it.next();
		%>
			<util:formItemEstrutura 
				atributo="<%=atributo%>" 
				historicoItemEstruturaIett="<%=historicoItemEstruturaIettAntes%>" 
				desabilitar="<%=Boolean.TRUE%>" 
				seguranca="<%=seguranca%>" 
				contextPath="<%=request.getContextPath()%>"
				ehHistorico="<%=Boolean.TRUE%>"
				codigo="<%="_antes"%>"
				request="<%=request %>"
			/>	
		<%
	}
} 
%>
