<%@ page import="ecar.pojo.ObjetoDemanda" %>
<%@ page import="ecar.dao.AtributoDemandaDao" %>
<%@ page import="java.util.Iterator"%>
<%@ page import="java.util.List"%>

	
<%@page import="ecar.pojo.VisaoDemandasVisDem"%><table class="form">
				
		<tr><td class="espacador" colspan="4"><img src="../../images/pixel.gif"></td></tr>
		<tr>
			<td width="25%"></td>
			<td width="30%"></td>
			<td width="20%" align="right">
	<%
			if ("consulta".equals(acao)) {
				if (regDemandaDao.validaUsuarioAltExc(regDemanda, request)) {
					VisaoDemandasVisDem visaoSessaoConsulta = ((VisaoDemandasVisDem)request.getSession().getAttribute(VisaoDemandasVisDem.VISAO_SELECIONADA));
					if (visaoSessaoConsulta!=null && visaoSessaoConsulta.getEhPermitidoIncluirApontamento()!=null && visaoSessaoConsulta.getEhPermitidoAlterarDemanda().equals("S")) {
	%>
					<input type=button class="botao" value="Alterar Demanda" onclick="aoClicarAlterarDemanda(form,  <%=regDemanda.getCodRegd()%>);">
	<%
					}
				}
			}
	%>
			</td>
			<td width="25%"></td>
		</tr>
	<% 
		AtributoDemandaDao atributoDemandaDao = new AtributoDemandaDao(request);
	
		Long visaoParameter = null;
		
		if(request.getSession().getAttribute(VisaoDemandasVisDem.VISAO_SELECIONADA)!=null)
			visaoParameter = ((VisaoDemandasVisDem)request.getSession().getAttribute(VisaoDemandasVisDem.VISAO_SELECIONADA)).getCodVisao();
		
		List atributos = atributoDemandaDao.getAtributosDemandaVisaoAtivosOrdenadosPorSequenciaTelaCampo(visaoParameter);
		int qtdAtributosPrimeiraColuna = 0;
		if(atributos != null){
			
			if (atributos.size() > 0){
				if (atributos.size() % 2 == 0){
					qtdAtributosPrimeiraColuna = atributos.size() / 2;	 
				} else {
					qtdAtributosPrimeiraColuna = atributos.size() / 2 + 1;
				}	
			}
			
			List atributosPrimeiraColuna = atributos.subList(0, qtdAtributosPrimeiraColuna);
			List atributosSegundaColuna = atributos.subList(qtdAtributosPrimeiraColuna, atributos.size());
			
			Iterator itPrimeiraColuna = atributosPrimeiraColuna.iterator();
			Iterator itSegundaColuna = atributosSegundaColuna.iterator();
			
			%>
			<tr>
			<%
			while(itPrimeiraColuna.hasNext()){
				ObjetoDemanda atributoColuna1 = (ObjetoDemanda) itPrimeiraColuna.next(); 
				if (atributoColuna1.iGetExibivelConsulta(visaoParameter).booleanValue()) {
				%>
				<tr>
				<util:formConsultaRegDemanda 
					atributo="<%=atributoColuna1%>" 
					regDemanda="<%=regDemanda%>" 
					request="<%=request%>"
					contextPath="<%=request.getContextPath()%>"
				/>					
			<% 			
				}
				
				if(itSegundaColuna.hasNext()){
					ObjetoDemanda atributoColuna2 = (ObjetoDemanda) itSegundaColuna.next(); 
					if (atributoColuna2.iGetExibivelConsulta(visaoParameter).booleanValue()){
				%>					
					<util:formConsultaRegDemanda 
						atributo="<%=atributoColuna2%>" 
						regDemanda="<%=regDemanda%>"
						request="<%=request%>"
						contextPath="<%=request.getContextPath()%>"
					/>					
			<%				}
				} else{ %>
					<td></td><td></td>
			<%			} %>				
				</tr>
			<%		}	
			} %>		
		<tr><td colspan="4">&nbsp;</td></tr>
		<tr><td class="espacador" colspan="4"><img src="../../images/pixel.gif"></td></tr>
	</table>