
<jsp:directive.page import="java.util.Iterator"/>
<jsp:directive.page import="ecar.pojo.ObjetoEstrutura"/><%
if(atributosDepois != null){
	Iterator it = atributosDepois.iterator();
	while(it.hasNext()){
		ObjetoEstrutura atributo = (ObjetoEstrutura) it.next();
		%>
			<util:formItemEstrutura 
				atributo="<%=atributo%>" 
				historicoItemEstruturaIett="<%=historicoItemEstruturaIettDepois%>" 
				desabilitar="<%=Boolean.TRUE%>" 
				seguranca="<%=seguranca%>" 
				contextPath="<%=request.getContextPath()%>"
				ehHistorico="<%=Boolean.TRUE%>"
				codigo="<%="_depois"%>"
				request="<%=request %>"
			/>	
		<%
	}
} 
%>
