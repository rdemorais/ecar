<%@ page import="comum.util.Util" %>
<%

/*
	JSP reutilizado como include em outros casos de uso.
	
	Para o correto funcionamento ao incluir esse JSP deve-se atentar para os
	seguintes requisitos:
	
	- Ter instanciada a classe ItemEstruturaIett com o nome itemEstrutura;
	- Ter instanciada a classe EstruturaFuncaoDao com o nome estruturaFuncaoDao;
	- Ter instanciada a classe ExercicioDao com o nome exercicioDao;
	- Ter importado as classes ItemEstruturaIett, EstruturaFuncaoDao, ExercicioExe,
					ExercicioDao, ItemEstrutFisicoIettf, ItemEstrutFisicoDao;
	- Ter importado java.util.List;
*/

	EstruturaFuncaoEttf estruturaFuncaoQtdePrevista = new EstruturaFuncaoEttf();
	
	estruturaFuncaoQtdePrevista = (EstruturaFuncaoEttf) estruturaFuncaoDao.getQuantidadesPrevistas(itemEstrutura.getEstruturaEtt());

	Boolean permissaoAlterarQtdPrevista = validaPermissao.permissaoAlterarItem(itemEstrutura, seguranca.getUsuario(), seguranca.getGruposAcesso(), estruturaFuncaoQtdePrevista);
	
	if (!permissaoAlterarQtdPrevista.booleanValue()) {
		_disabled = "disabled";
	}

%>

			<table class="texto_negrito">
				<tr>
					<td width="20%" class="texto"></td>
					<td width="10%" align="center">Exercício</td>
					<td width="25%" align="center"><%=estruturaFuncaoQtdePrevista.getLabelEttf()%></span></td>
					<td width="45%" class="texto"></td>
				<tr>
<%
				StringBuffer validaQtdPrev = new StringBuffer();
				ItemEstrutFisicoDao itemEstrutFisicoDao = new ItemEstrutFisicoDao(request);
				List listaExercicio = exercicioDao.getExercicioByPeriodicidade( Long.valueOf(2) );
				
					for (Iterator itExercicio = listaExercicio.iterator(); itExercicio.hasNext();) {
						ExercicioExe exercicio = (ExercicioExe) itExercicio.next();
									
						ItemEstrutFisicoIettf itemEstrutFisico = new ItemEstrutFisicoIettf();
						
						if (itemEstrtIndResul.getCodIettir() != null) {
							try {
								itemEstrutFisico = (ItemEstrutFisicoIettf) itemEstrutFisicoDao.buscar(itemEstrtIndResul.getCodIettir(), exercicio.getCodExe());
							} catch (Exception e) {
								org.apache.log4j.Logger.getLogger(this.getClass()).error(e);
								itemEstrutFisico = new ItemEstrutFisicoIettf();
							}
						}
%>				
						<tr>
							<td class="texto"></td>
							<td align="center" class="texto">
							<%=exercicio.getDescricaoExe()%>
							</td>
							<td align="center" class="texto">
								<input type="text" id="qtdPrevistaIettf<%=exercicio.getCodExe()%>" name="qtdPrevistaIettf<%=exercicio.getCodExe()%>" value="<%=Pagina.trocaNullNumeroDecimal(itemEstrutFisico.getQtdPrevistaIettf())%>" size="18" <%=_disabled%> >
								<input type="hidden" id="hideqtdPrevistaIettf<%=exercicio.getCodExe()%>" name="hideqtdPrevistaIettf<%=exercicio.getCodExe()%>" value="<%=Pagina.trocaNullNumeroDecimal(itemEstrutFisico.getQtdPrevistaIettf())%>"  >								
							</td>
							<td class="texto"></td>
						</tr>
<%

				}
				if (  listaExercicio!=null && listaExercicio.isEmpty() ){
					out.println("<tr>");
					out.println("<td class='texto'></td>");
					out.println("<td align='center' class='texto' colspan='2'>");
					out.println("Não há exercícios.");
					out.println("</td>");
					out.println("<td class='texto'></td>");					
					out.println("</tr>");										
				}
%>
			</table>
