	<!-- imprime a arvore de estruturas -->
		
<%@page import="ecar.dao.AcompReferenciaDao"%>
<%@page import="comum.util.ConstantesECAR"%>
<%@page import="ecar.pojo.TipoAcompanhamentoTa"%><util:arvoreEstruturas 
			itemEstrutura="<%=itemEstrutura%>" 
			exibirLinks="false" 
			proximoNivel="false" 
			contextPath="<%=_pathEcar%>"
			primeiroIettClicado="<%=itemDoNivelClicadoVoltar%>"
			primeiroAriClicado="<%=itemDoNivelClicado%>"
			telaAnterior="<%=telaAnterior%>"
			codTipoAcompanhamentoSelecionado="<%=codTipoAcompanhamento%>"	
		/>
<%
if(ari != null) {
%>
	<table border="0" width="100%">
		<tr>
			<td valign="bottom" class="texto" align="right"> 
				<b>Ciclo de referência: </b> 
				<%
				if(exibeCombo) {
				%>
					<!-- Constroi a combo -->
		            <select name="referenciaCombo" id="referenciaCombo" onChange="javaScript:recarregar();">
					<%			            
		            //Montar as opções a partir do item
		            List lista = ariDao.getReferenciaByItem(ari);
					Iterator it = lista.iterator();
					AcompReferenciaItemAri acompRefItemLista;
		            
					String sel = "";
					
					while(it.hasNext()){
						acompRefItemLista = (AcompReferenciaItemAri) it.next();
						sel = "";
						
						if(acompRefItemLista.getCodAri() == ari.getCodAri())
							sel = "selected";
					%>
						<option <%=sel%> value="<%=acompRefItemLista.getAcompReferenciaAref().getCodAref()%>">
						<%=acompRefItemLista.getAcompReferenciaAref().getNomeAref()%></option>
					<%
					}
					%>
		            </select>
		         <%
		         } else {
                 
		        	AcompReferenciaDao acompReferenciaDaoCabecalho = new AcompReferenciaDao(request);
		        	arefSelecionado = ari.getAcompReferenciaAref();
		        	boolean ehSeparadoPorOrgaoCabecalho = false;
		        	TipoAcompanhamentoTa tipoAcompCabecalho = arefSelecionado.getTipoAcompanhamentoTa(); 
		        		
		        		
                    StringBuffer opcaoCombo = new StringBuffer(arefSelecionado.getDiaAref())
					.append("/").append(arefSelecionado.getMesAref())
					.append("/").append(arefSelecionado.getAnoAref());

					if(tipoAcompCabecalho != null && tipoAcompCabecalho.getIndSepararOrgaoTa() != null && tipoAcompCabecalho.getIndSepararOrgaoTa().equals("S") && acompReferenciaDaoCabecalho.getListaMesmaReferenciaDiaMesAno(arefSelecionado).size() > 1){
						opcaoCombo.append(" - " + ConstantesECAR.LABEL_ORGAO_CONSOLIDADO);
					} else {
						opcaoCombo.append(" - ").append(arefSelecionado.getNomeAref());
					}
                    
                    out.println(opcaoCombo);
                 } 
	            %>
			</td>
		</tr>
	</table>
<%
}
%>