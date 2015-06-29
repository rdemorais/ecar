<%-- Imprime tabela com os acompanhamentos que foram consolidados --%>

<%@page import="java.util.Collection"%>
<%@page import="ecar.pojo.AcompReferenciaAref"%>
<%@page import="ecar.dao.AcompReferenciaDao"%>
<%@page import="java.util.ArrayList"%>
<%@page import="comum.util.ConstantesECAR"%><table style="width: 100%; border: 0; text-align: center; ">

	<!-- Linha de contendo os periodos exibidos -->
	<tr class="linha_subtitulo">
		<td>Ciclo </td>
<% 
	ItemEstUsutpfuacDao itemEstUsuDao = new ItemEstUsutpfuacDao(request);
	/* Imprime o nome dos ciclos */
	Collection periodosConsiderados = new ArrayList();
	Long codArefReferencia = ari.getAcompReferenciaAref().getCodAref();
	
	List referenciasAgrupadasCombo = acompReferenciaDao.getComboReferencia(ari.getAcompReferenciaAref().getTipoAcompanhamentoTa()); //(List) request.getSession().getAttribute("referenciasAgrupadasCombo");
	
	
    Collection periodosConsideradosAgrupados = new ArrayList();
    Collection periodosConsideradosListagem = new ArrayList();
    //Recupera da sessão lista total de referências
//    Collection periodosConsideradosTotais = (Collection) request.getSession().getAttribute("periodosConsideradosListagem");
	 
	if(codArefReferencia.intValue() > 0) {
//		periodosConsiderados =  acompReferenciaDao.getPeriodosAnterioresOrdenado(codArefReferencia, intPeriodo, ari.getAcompReferenciaAref().getTipoAcompanhamentoTa().getCodTa(), false);

		AcompReferenciaAref referenciaSelecionadaAri = (AcompReferenciaAref) acompReferenciaDao.buscar(AcompReferenciaAref.class, codArefReferencia);

		AcompReferenciaAref referenciaSelecionadaCombo = acompReferenciaDao.getReferenciaMesmoDiaMesAno(referenciasAgrupadasCombo, referenciaSelecionadaAri);
					
		if(ehSeparadoPorOrgao) {
			//Pega a lista de referências consideradas agrupadas por dia/mes/ano
			periodosConsideradosAgrupados = acompReferenciaDao.getPeriodosAgrupadosConsideradosMesmoOrgao(referenciasAgrupadasCombo, referenciaSelecionadaCombo, referenciaSelecionadaAri, intPeriodo);
			//Completa a lista de ciclos de referência com outas referências de mesmo dia/mes/ano das referências consideradas
			periodosConsideradosListagem = acompReferenciaDao.getListaMesmaReferenciaDiaMesAno(periodosConsideradosAgrupados);
		} else {
			periodosConsideradosListagem = acompReferenciaDao.getPeriodosAnterioresOrdenado(codArefReferencia, intPeriodo, ari.getAcompReferenciaAref().getTipoAcompanhamentoTa().getCodTa() , false);
			periodosConsideradosAgrupados = periodosConsideradosListagem;
		}
	}
	
	Iterator itPeriodo = periodosConsideradosAgrupados.iterator();
	
	double tamanhoColuna = 38 / periodosConsideradosAgrupados.size() ;
	while(itPeriodo.hasNext()){
	
		AcompReferenciaAref acompReferencia = (AcompReferenciaAref) itPeriodo.next();
		String estilo = "";
		if(!itPeriodo.hasNext()){
		} 
		
		StringBuffer labelReferencia = new StringBuffer();

		if(ehSeparadoPorOrgao && acompReferenciaDao.getListaMesmaReferenciaDiaMesAno(acompReferencia).size() > 1){
			 labelReferencia.append(acompReferencia.getDiaAref()).append("/").append(acompReferencia.getMesAref()).append("/").append(acompReferencia.getAnoAref());
			 //labelReferencia.append(" - " + ConstantesECAR.LABEL_ORGAO_CONSOLIDADO);
		} else {
			labelReferencia.append(acompReferencia.getNomeAref());
		}
%>
		<td align="center" width="<%=tamanhoColuna%>%"><%=labelReferencia%> </td>
<%							
	}
%>					
	</tr>
	<tr>
		<td class="espacador" colspan="<%= periodosConsideradosAgrupados.size()+1 %>"><img src="<%=_pathEcar%>/images/pixel.gif" alt=""></td>
	</tr>
<%	 
	List listAcompItemReferenciasOrderByReferecias = ariDao.getAcompItemReferenciasOrderByReferecias(ari, periodosConsideradosListagem, ari.getAcompReferenciaAref().getOrgaoOrg());
	
	//Valores da tabela
	Map mapLabelPosicaoRelatorio = ariDao.agrupaRelatorioTpfa_e_Aref (listAcompItemReferenciasOrderByReferecias,periodosConsideradosAgrupados );
	
	//coluna da tabela
	Set funcoesAcomp = ari.getAcompRelatorioArels();
	 
	String corLinha ="";
	int numLinhas = 1; 
	int cont =0;
	List itensFuncaoAcompanhamento = new ArrayList();
	 		
	Iterator itFuncoesAcomp = null;
	if(funcoesAcomp !=null )
		itFuncoesAcomp = funcoesAcomp.iterator();
		
	while(itFuncoesAcomp.hasNext())	{ 
			
		AcompRelatorioArel acompRelatorio = (AcompRelatorioArel)  itFuncoesAcomp.next();
		
		if (listaPermissaoTpfa.contains(acompRelatorio.getTipoFuncAcompTpfa()) 
				&& validaPermissao.permissaoLeituraAcompanhamento(acompRelatorio.getAcompReferenciaItemAri(), seguranca.getUsuario(), seguranca.getGruposAcesso())){
		
			TipoFuncAcompTpfa tpfa = acompRelatorio.getTipoFuncAcompTpfa(); //(TipoFuncAcompTpfa)itFuncoesAcomp.next();
		    itensFuncaoAcompanhamento.add(tpfa); 
		
			if(tpfa.getIndEmitePosicaoTpfa().equalsIgnoreCase("S")){  //  tpfa.getIndEmitePosicaoTpfa().equalsIgnoreCase("o") /*|| ( tpfa.getIndEmitePosicaoTpfa().equalsIgnoreCase("p") && tpfa.getAcompRelatorioArels()!=null  )*/ )  { //p-opcional o-obrigatorio
				corLinha = ( (numLinhas++) % 2== 0? "list_elab_acomp_cor_sim":"");
	%>
		<tr class="<%=corLinha  %>" style="border: 0" onmouseover="javascript:destacaLinha(this,'over','')" 
			onmouseout="javascript: destacaLinha(this,'out','<%=corLinha%>')" 
			onClick="javascript: destacaLinha(this,'click','<%=corLinha%>')"  >
			<td class="linha_subtitulo" style="text-align: left;" > <%=tpfa.getLabelPosicaoTpfa()%> </td>
	<%
			Map mapRefRelatorio = (Map )mapLabelPosicaoRelatorio.get(tpfa );
		
			for(itPeriodo = periodosConsideradosAgrupados.iterator(); itPeriodo.hasNext(); ){
				AcompReferenciaAref acompAref = (AcompReferenciaAref) itPeriodo.next();
			
				if (mapRefRelatorio.containsKey(acompAref ) ){
					AcompRelatorioArel tabArel = (AcompRelatorioArel) mapRefRelatorio.get(acompAref);
					cont++;
				
					String titulo = "";
					String titleImg = "";
					String srcImg= _pathEcar + "/images/sBranco3.png";
					boolean parecrLiberado = false;
						
					if (tabArel.getIndLiberadoArel()!=null && tabArel.getIndLiberadoArel().equals("S")) {
					 	srcImg = Util.getURLImagemAcompanhamento(tabArel.getCor(), request, tabArel.getTipoFuncAcompTpfa());
					 	titleImg = tabArel.getCor().getSignificadoCor();
				 	}
					 	
					StringBuffer strBfDica = new StringBuffer("");
					
					StringBuffer strBfDicaMouse = new StringBuffer("");
				    
					ItemEstUsutpfuacIettutfa itemEstUsu 
							= itemEstUsuDao.buscar(ari.getItemEstruturaIett().getCodIett(), tabArel.getTipoFuncAcompTpfa().getCodTpfa());
					
					if(itemEstUsu != null) {
						if (itemEstUsu.getUsuarioUsu() != null) {
						
							strBfDica.append("<table  cellpadding='0' cellspacing='0'>");
						
							if(itemEstUsu.getUsuarioUsu().getNomeUsu() != null) {
								strBfDica.append("<tr><td><b>Resp</b>: ").append(itemEstUsu.getUsuarioUsu().getNomeUsu()).append("</tr></td>");
								strBfDicaMouse.append("<b>Resp: </b>").append(itemEstUsu.getUsuarioUsu().getNomeUsu()).append("<br>");
							} else {
								strBfDica.append("<tr><td><b>Resp</b>: ").append("").append("</tr></td>");
							}
							
							if(itemEstUsu.getUsuarioUsu().getComercTelefoneUsu() != null) {
								strBfDica.append("<tr><td><b>Tel</b>: " ).append(itemEstUsu.getUsuarioUsu().getComercTelefoneUsu()).append("</tr></td>");
							} else {
								strBfDica.append("<tr><td><b>Tel</b>: " ).append("").append("</tr></td>");
							}
							
							if(itemEstUsu.getUsuarioUsu().getEmail1UsuSent() != null) {	 	
								strBfDica.append("<tr><td><b>E-mail</b>: " ).append(itemEstUsu.getUsuarioUsu().getEmail1UsuSent()).append("</tr></td>");
							} else {
								strBfDica.append("<tr><td><b>E-mail</b>: " ).append("").append("</tr></td>");
							}	
							
							strBfDica.append("</table>");	
							
							
								
						} else if (itemEstUsu.getSisAtributoSatb() != null) {
							if (itemEstUsu.getSisAtributoSatb().getUsuarioAtributoUsuas() != null) {
								
								strBfDica.append("<table border='0' cellpadding='0' cellspacing='0' >");
								strBfDica.append("<tr><td><b>GRUPO</b>: ").append(itemEstUsu.getSisAtributoSatb().getDescricaoSatb()).append("</tr></td>");
								strBfDicaMouse.append("<b>GRUPO</b>: ").append(itemEstUsu.getSisAtributoSatb().getDescricaoSatb()).append("<br>");
								strBfDica.append("</table>");
								
								
								Iterator itUsuarios = itemEstUsu.getSisAtributoSatb().getUsuarioAtributoUsuas().iterator();
								
								strBfDica.append("<table border='0' cellpadding='0' cellspacing='0' style='BORDER-COLLAPSE: collapse'>");
								strBfDica.append("<tr><td><b>Resp</b></td>");
								strBfDica.append("<td><b>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Tel</b></td>");
								strBfDica.append("<td><b>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;E-mail</b></tr></td>");
								
								while (itUsuarios.hasNext()) {
									UsuarioAtributoUsua usuarioAtributoUsua = (UsuarioAtributoUsua) itUsuarios.next();
									
									if(usuarioAtributoUsua.getUsuarioUsu() != null) {
										
										strBfDica.append("<tr>");
										
										if(usuarioAtributoUsua.getUsuarioUsu().getNomeUsu() != null) {
											strBfDica.append("<td>").append(usuarioAtributoUsua.getUsuarioUsu().getNomeUsu()).append("</td>");
										} else {
											strBfDica.append("</td>");
										}	
										
										if(usuarioAtributoUsua.getUsuarioUsu().getComercTelefoneUsu() != null) {
											strBfDica.append("<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;" ).append(usuarioAtributoUsua.getUsuarioUsu().getComercTelefoneUsu()).append("</td>");
										} else {
											strBfDica.append("<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>" );
										}
										
										if(usuarioAtributoUsua.getUsuarioUsu().getEmail1UsuSent() != null) { 	
											strBfDica.append("<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;" ).append(usuarioAtributoUsua.getUsuarioUsu().getEmail1UsuSent()).append("</td>");
										} else  {
											strBfDica.append("<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>");		
										}
								
										strBfDica.append("</tr>");
										
									}
								}
								
								strBfDica.append("</table>");
							}
						} 
					
					} 
							
	
	
	
									        
	%>
						<td nowrap >
				        	<label class="dica" 
				        		onmouseover="javascript:viewFieldTip(this, '<%=tabArel.getTipoFuncAcompTpfa().getLabelPosicaoTpfa()+"_"+cont %>');"  onmouseout="javascript:noViewFieldTip('<%=tabArel.getTipoFuncAcompTpfa().getLabelPosicaoTpfa()+"_"+cont %>');"
	<% 				 
							String parametros = "codIett=" + ari.getItemEstruturaIett().getCodIett() + "&codTpfa=" +  tabArel.getTipoFuncAcompTpfa().getCodTpfa();
	%>
				        	>
	<%			        	
							// se a imagem do parecer tiver label	
				        	if(!titleImg.equals("")) {	
	%>
				            	<img title=<%=titleImg %> src="<%=srcImg %>" align="absmiddle" border="0" onclick="javascript:viewFieldTipDicaParecerPopUp('<%=parametros%>')" />
	<%			        		
				        	} else {
				        		// se a imagem do parecer nao tiver label, nao coloca titulo na imagem
	%>
				            	<img src="<%=srcImg %>" align="absmiddle" border="0" onclick="javascript:viewFieldTipDicaParecerPopUp('<%=parametros%>')" />
	<%			        		
				        	}	
	
	
							//se tiver usuário associado				
							if(!strBfDicaMouse.toString().equals("")) {	
	%>			       	    	<span id="<%=tabArel.getTipoFuncAcompTpfa().getLabelPosicaoTpfa()+"_"+cont %>" style="z-index: 5"   ><br>
				        	   		 <%=strBfDicaMouse.toString() %>
				           		</span>
							
	<%			        		
	
				        	} else {
				        		// se não tiver usuário associado
	%>							<span id="<%=tabArel.getTipoFuncAcompTpfa().getLabelPosicaoTpfa()+"_"+cont %>" style="z-index: 5"   ><br>
				           			<b>Resp: </b>Não existe usuário ou grupo associado.
				           		</span>
				           	
				           		
				           		
	<%			        		
	
							}	
	
	%>
				         	</label>
				       </td>
	<%
				} else	{
	%>
					<td>&nbsp;</td>
	<%			}
			}
	%>
		</tr>	
	<%
	  	}//fim if 1
	} // fim do if listaPermissaoTpfa.contains	 	
} //fim while itFuncoesAcomp.hasNext()
%>
	<tr><td class="espacador" colspan="12"><img src="<%=_pathEcar%>/images/pixel.gif"></td></tr>
	<tr><td colspan="5">&nbsp; </td></tr> 
</table>



