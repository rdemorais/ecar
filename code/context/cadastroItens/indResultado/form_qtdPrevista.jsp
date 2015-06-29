<%@ page import="comum.util.Util" %>
<jsp:directive.page import="comum.util.Pagina"/>
<jsp:directive.page import="ecar.pojo.ItemEstrutFisicoIettf"/>
<jsp:directive.page import="java.util.GregorianCalendar"/>
<jsp:directive.page import="java.util.*"/>
<%
/*
	JSP reutilizado como include em outros casos de uso.
	
	Para o correto funcionamento ao incluir esse JSP deve-se atentar para os
	seguintes requisitos:
	
	- Ter instanciada a classe ItemEstruturaIett com o nome itemEstrutura;
    - Ter instanciada a classe ItemEstruturaDao com o nome ItemEstruturaDao;
	- Ter instanciada a classe EstruturaFuncaoDao com o nome estruturaFuncaoDao;
	- Ter instanciada a classe ExercicioDao com o nome exercicioDao;
	- Ter importado as classes ItemEstruturaIett, EstruturaFuncaoDao, ExercicioExe,
					ExercicioDao, ItemEstrutFisicoIettf, ItemEstrutFisicoDao;
	- Ter importado java.util.List;
*/

	EstruturaFuncaoEttf estruturaFuncaoQtdePrevista = new EstruturaFuncaoEttf();
	
	estruturaFuncaoQtdePrevista = (EstruturaFuncaoEttf) estruturaFuncaoDao.getQuantidadesPrevistas(itemEstrutura.getEstruturaEtt());
	
	Boolean permissaoAlterarQtdPrevista = new Boolean(true);// validaPermissao.permissaoAlterarItem(itemEstrutura, seguranca.getUsuario(), seguranca.getGruposAcesso(), estruturaFuncaoQtdePrevista);
	
	if (!permissaoAlterarQtdPrevista.booleanValue() && tipoAcao.equals("alterar")) {
		_disabled = "disabled";
	}

				StringBuffer validaQtdPrev = new StringBuffer();
				
				//lista com os meses 
				List<GregorianCalendar> listaMesesReferencia = itemEstruturaDao.GetMesesReferencia(itemEstrutura); 
				
				List<ItemEstrutFisicoIettf> listaItemEstrutFisico = null;
				
				if (tipoAcao.equals("incluir"))
				{
					listaItemEstrutFisico = new ArrayList<ItemEstrutFisicoIettf>();
				}
				else{
					ItemEstrutFisicoDao itemEstrutFisicoDao = new ItemEstrutFisicoDao(request);
					listaItemEstrutFisico = itemEstrutFisicoDao.getListaPrevistosIettr(itemEstrtIndResul);					
				}
								
				
				Iterator itMesesReferencia = listaMesesReferencia.iterator();
				
				Iterator itPrevistos = listaItemEstrutFisico.iterator();
				
				int i = 0;
				int anoAnterior = 0;
				int mes = 0;
				int ano = 0;
				ItemEstrutFisicoIettf itemEstrutFisico = null;
				String anoMes = null;
				
				String porLocal;
				String _disabledLocal = "";
				String _disabledGrid = _disabled;
				if(tipoAcao.equals("alterar")){
                    if ((("S").equalsIgnoreCase(itemEstrtIndResul.getIndPrevPorLocal()))&&!(_disabled.equalsIgnoreCase("disabled"))){  				  		
  				  		_disabledGrid = "disabled"; //desabilita o grid 
	                  }
				}
				
				
				if (itPrevistos.hasNext()){ //posiciona no primeiro previsto.
					itemEstrutFisico = (ItemEstrutFisicoIettf)itPrevistos.next(); 	
				}
				
				if (itMesesReferencia.hasNext()) {
					while (itMesesReferencia.hasNext()) {
						GregorianCalendar mesReferencia = (GregorianCalendar) itMesesReferencia.next();
						//String mes = String.format("%0" + Integer.toString(mesReferencia.get(Calendar.MONTH)) + "d", 2);
						//String ano = String.format("%0" + Integer.toString(mesReferencia.get(Calendar.YEAR)) + "d", 4);
						mes = mesReferencia.get(Calendar.MONTH) + 1;						
						ano = mesReferencia.get(Calendar.YEAR);
						anoMes = String.format("%04d", ano) + String.format("%02d", mes);   
						
						if (ano != anoAnterior){ //Quebra de linha
							if (anoAnterior == 0){ //Primeira linha
								%>
			<table class="texto_negrito" border ="1">
				<tr>
					<!-- <td width="20%" class="texto"></td> -->
					<td width="10%" class="titulo" align="center">Ano</td>
					<td width="5%" class="titulo" align="center">Jan</td>
					<td width="5%" class="titulo" align="center">Fev</td>
					<td width="5%" class="titulo" align="center">Mar</td>
					<td width="5%" class="titulo" align="center">Abr</td>
					<td width="5%" class="titulo" align="center">Mai</td>
					<td width="5%" class="titulo" align="center">Jun</td>
					<td width="5%" class="titulo" align="center">Jul</td>
					<td width="5%" class="titulo" align="center">Ago</td>
					<td width="5%" class="titulo" align="center">Set</td>
					<td width="5%" class="titulo" align="center">Out</td>
					<td width="5%" class="titulo" align="center">Nov</td>
					<td width="5%" class="titulo" align="center">Dez</td>
                    <td width="5%" class="titulo" align="center" style="display: none;">&nbsp;</td>					
				<tr>								
								
				<tr>
					<!-- <td class="texto"></td> -->
					<td align="center" class="texto"><%=Integer.toString(ano)%></td>
								
								<%
								for (int j = 1; j < mes; j++) //imprime os meses fora do ciclo
								{
								%>	
					<td width="5%" class="texto" align="center">-</td>
								<%	
								}  
							}
							else{ //fecha a linha quando não for a primeira								
						%>
                    <td width="5%" class="texto" align="left" style="display: none;">
                    <% if(tipoAcao.equals("alterar")){
                          if ((("S").equalsIgnoreCase(itemEstrtIndResul.getIndPrevPorLocal()))&&!(_disabled.equalsIgnoreCase("disabled"))){
	    				  		_disabledLocal = "";
	    				  		_disabledGrid = "disabled"; //desabilita o grid 
    	                  } else{
    					   		_disabledLocal = "disabled";    					   		
    				      }
                          %>
                    <input type="button" id="btnPorLocal<%=Integer.toString(anoAnterior)%>" name="btnPorLocal" value="Por Local" <%=_disabledLocal%> onclick="abrirPrevistoLocal('<%=itemEstrutura.getCodIett().toString()%>','<%=itemEstrtIndResul.getCodIettir().toString()%>','<%=Integer.toString(anoAnterior)%>','<%=Integer.toString(larguraLocal + listaDeExercicio.size()*larguraCampo + larguraTotal)%>')">                          
                          <%
                       }else{%>
                           <input type="button" id="btnPorLocal<%=Integer.toString(anoAnterior)%>" name="btnPorLocal" value="Por Local" disabled >                    	   
                    	   <%
                       }
                    %> 
                    </td>						
				</tr>
				<tr>
					<!-- <td class="texto"></td> -->
					<td align="center" class="texto"><%=Integer.toString(ano)%></td>				
						<% 
							}					 
						}// Fim quebra de linha
						
						if (itemEstrutFisico != null){						
							if (itemEstrutFisico.getAnoIettf() == ano && itemEstrutFisico.getMesIettf() == mes){//usa o previsto atual para imprimir coluna
								String qtdePrevisto = Pagina.trocaNullQtdValorPT_BR(itemEstrutFisico.getQtdPrevistaIettf(), itemEstrtIndResul.getIndTipoQtde());
						%>
					<td align="center" class="texto">
						<input type="text" name="qtdPrevistaIettf<%=anoMes%>" id="qtdPrevistaIettf<%=anoMes%>" value="<%=qtdePrevisto%>" size="6" <%=_disabledGrid%> class="previsto" onblur="javascript:verificaPreenchimentoCelula(this);">
						<input type="hidden" name="hidQtdPrevistaIettf" id="hidQtdPrevistaIettf<%=anoMes%>" value="<%=qtdePrevisto%>">																					
					</td>
		                <%						
								if (itPrevistos.hasNext()){//pega o próximo previsto.
									itemEstrutFisico = (ItemEstrutFisicoIettf)itPrevistos.next(); 	
								}
								else
									itemEstrutFisico = null;
							}
							else{//imprime coluna sem um previsto.
							%>
					<td align="center" class="texto">
						<input type="text" name="qtdPrevistaIettf<%=anoMes%>" id="qtdPrevistaIettf<%=anoMes%>" value="" size="6" <%=_disabledGrid%> class="previsto" onblur="javascript:verificaPreenchimentoCelula(this);">
						<input type="hidden" name="hidQtdPrevistaIettf" id="qtdPrevistaIettf<%=anoMes%>" value="">						 															
					</td>
					        <%								
							}
						}
						else{//imprime coluna sem um previsto.
						%>
					<td align="center" class="texto">
						<input type="text" name="qtdPrevistaIettf<%=anoMes%>" id="qtdPrevistaIettf<%=anoMes%>" value="" size="6" <%=_disabledGrid%> class="previsto" onblur="javascript:verificaPreenchimentoCelula(this);"">
						<input type="hidden" name="HidQtdPrevistaIettf" id="HidQtdPrevistaIettf<%=anoMes%>" value="">						 															
					</td>
		                <%
						}

						i++;
						anoAnterior = ano;	
					}
					for (int j = mes+1; j <= 12; j++) //imprime os meses fora do ciclo
					{
					%>	
					<td width="5%" class="texto" align="center">-</td>
					<%	
					}					
%>
                    <td width="5%" class="texto" align="left" style="display: none;">
                    <% if(tipoAcao.equals("alterar")){
                          if ((("S").equalsIgnoreCase(itemEstrtIndResul.getIndPrevPorLocal()))&&!(_disabled.equalsIgnoreCase("disabled"))){
	    				  		_disabledLocal = "";
    	                  } else{
    					   		_disabledLocal = "disabled";
    				      }
                          %>
                    <input type="button" id="btnPorLocal<%=Integer.toString(anoAnterior)%>" name="btnPorLocal" value="Por Local" <%=_disabledLocal%> onclick="abrirPrevistoLocal('<%=itemEstrutura.getCodIett().toString()%>','<%=itemEstrtIndResul.getCodIettir().toString()%>','<%=Integer.toString(anoAnterior)%>','<%=Integer.toString(larguraLocal + listaDeExercicio.size()*larguraCampo + larguraTotal)%>')">                          
                          <%
                       }else{%>
                           <input type="button" id="btnPorLocal<%=Integer.toString(anoAnterior)%>" name="btnPorLocal" value="Por Local" disabled >                    	   
                    	   <%
                       }
                    %> 

                    </td>
   				</tr>
			</table>				
<%			
				} 
				else {
%>
				<table>	
					<tr>
						<td class="texto"></td>
						<td align="center" class="texto" colspan="2">
							Imposs&#237;vel informar quantidade prevista. &#201; preciso informar a data inicial e final.
						</td>
						<td class="texto"></td>
					</tr>
				</table>
<%
				}
%>
			