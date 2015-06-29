<%@ page import="ecar.pojo.ObjetoDemanda" %>
<%@ page import="ecar.dao.AtributoDemandaDao" %>
<%@ page import="java.util.Iterator"%>
<%@ page import="java.util.List"%>

	<table class="form">
		
		<input type="hidden" name="codRegd" value="<%=regDemanda.getCodRegd()%>">
		
		<tr><td class="espacador" colspan="4"><img src="../../images/pixel.gif"></td></tr>
		<tr>
			<td width="25%"></td>
			<td width="30%"></td>
			<td width="20%" align="right">
			</td>
			<td width="25%"></td>
		</tr>
	<% 
		AtributoDemandaDao atributoDemandaDao = new AtributoDemandaDao(null);
		List atributos = atributoDemandaDao.getAtributosDemandaVisaoAtivosOrdenadosPorSequenciaTelaCampo();
		
		if(atributos != null){
			Iterator it = atributos.iterator();  
			
			while(it.hasNext()){
				ObjetoDemanda atributoColuna1 = (ObjetoDemanda) it.next(); %>
				<tr>
				<util:formConsultaRegDemanda 
					atributo="<%=atributoColuna1%>" 
					regDemanda="<%=regDemanda%>" 
				/>					
	<% 			
				if(it.hasNext()){
					ObjetoDemanda atributoColuna2 = (ObjetoDemanda) it.next(); %>					
					<util:formConsultaRegDemanda 
						atributo="<%=atributoColuna2%>" 
						regDemanda="<%=regDemanda%>" 
					/>					
	<%			} else{ %>
					<td></td><td></td>
	<%			} %>				
				</tr>
	<%		}	
		} %>		
			
		<tr><td colspan="4">&nbsp;</td></tr>
		<tr><td class="espacador" colspan="4"><img src="../../images/pixel.gif"></td></tr>
	</table>