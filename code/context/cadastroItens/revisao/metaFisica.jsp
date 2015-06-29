<%@ page import="java.util.Collections" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.Comparator" %>
<%@ page import="ecar.pojo.ItemEstFisicoRevIettfr" %>
<script>

function aoClicarExcluirMeta(form){
	if(validarExclusao(form, "excluirMeta")){
		if(!confirm("Confirma a exclusão?")){
			return(false);
		}
		form.hidAcao.value = "excluir";
		form.action = "metaFisica/ctrl.jsp";
		form.submit();	
	}
}	

function MostraLinha(parmCodigo) {
  if (document.getElementById('linha' + '_' + parmCodigo).style.display=='none') {
     document.getElementById('linha' + '_' + parmCodigo).style.display='';
  } else {
     document.getElementById('linha' + '_' + parmCodigo).style.display='none';
  }
}

</script>

<%
		FuncaoFun funcao = new FuncaoFun();
		EstruturaFuncaoEttf etf = new EstruturaFuncaoEttf();
		funcao.setNomeFun("Indicadores_Resultado");
		List pesquisa = new FuncaoDao(request).pesquisar(funcao, new String[]{"nomeFun","asc"});
		if ((pesquisa != null) && (pesquisa.size() > 0))
		{
			FuncaoFun f = (FuncaoFun) pesquisa.iterator().next();
			etf.setComp_id(new EstruturaFuncaoEttfPK(estrutura.getCodEtt(), f.getCodFun()));
			List pesquisaEttf = new EstruturaFuncaoDao(request).pesquisar(etf, new String[]{"labelEttf","asc"});
			if ((pesquisaEttf != null) && (pesquisaEttf.size() > 0))
			{
				etf = (EstruturaFuncaoEttf) pesquisaEttf.iterator().next();
				String codAbaMeta = f.getCodFun().toString();
	
%>

<script>
function aoClicarIncluirMeta(form){
	if(form.hidGravado.value != "S")
		alert("Antes de inserir meta física é necessário GRAVAR a revisão\nFaça isto clicando em \"Gravar\"");
	else
	{
		opcoes = '?codAba=<%=codAba%>&codAbaMeta=<%=codAbaMeta%>&codIett=<%=Pagina.getParamStr(request, "codIett")%>&codIettrev=<%=Pagina.getParamStr(request, "codIettrev")%>';
		caminho=_pathEcar + '/cadastroItens/revisao/metaFisica/frm_inc.jsp'+opcoes;
		return abreJanela(caminho, '500', '300', 'MetasFisicas');
	}
}

function aoClicarEditarMeta(form, codIettirr){
		opcoes = '?codAba=<%=codAba%>&codAbaMeta=<%=codAbaMeta%>&codIett=<%=Pagina.getParamStr(request, "codIett")%>&codIettrev=<%=Pagina.getParamStr(request, "codIettrev")%>&codIettirr='+codIettirr;
		caminho=_pathEcar + '/cadastroItens/revisao/metaFisica/frm_con.jsp'+opcoes;
		return abreJanela(caminho, '500', '300', 'MetasFisicas');
} 
</script>
	<input type="hidden" name="codAbaMeta" value="<%=codAbaMeta%>">

	<table width="100%" border="0" cellpadding="0" cellspacing="0">
	<tr ><td colspan="2" class="espacador"><img src="../../images/pixel.gif"></td></tr>
	<tr>
	</tr>
	<tr class="linha_titulo">
	<td>
		<h1><%=etf.getLabelEttf()%></h1>
	</td>
		<td align="right">
		<%if (!desabilitar){%>
			<input type="button" class="botao" value="Adicionar revisão em <%=etf.getLabelEttf()%>" onclick="javascript:aoClicarIncluirMeta(form);">&nbsp;
			<input type="button" class="botao" value="Excluir revisão em <%=etf.getLabelEttf()%>" onclick="javascript:aoClicarExcluirMeta(form);">&nbsp;
		<%}%>
		</td>
	</tr>
	</table>
	
	<table width="100%" border="0" cellpadding="0" cellspacing="0">
	<tr><td class="espacador" colspan="8"><img src="../../images/pixel.gif"></td></tr>
	<tr class="linha_subtitulo">
	
		<td width="2%">
		<%if (!desabilitar){%>			
			<input type=checkBox class="form_check_radio" name="todosMeta" onclick="javascript:selectAll(document.form, 'todosMeta', 'excluirMeta')"></td>
		<%}%>
		<td width="4%"></td>
		<td>Tipo</td>
	</tr>
	<tr><td class="espacador" colspan="8"><img src="../../images/pixel.gif"></td></tr>
<%
	if (ieRevisao.getIettIndResulRevIettrrs() != null){
		//List lista = ieRevisaoDao.ordenaSet(ieRevisao.getIettIndResulRevIettrrs(), "this.itemEstrtIndResulIettr.nomeIettir", "asc");
		List lista = ieRevisaoDao.ordenaSetByCompare(ieRevisao.getIettIndResulRevIettrrs());
		Iterator it = lista.iterator();
		
		IettIndResulRevIettrr itemEstrtIndResul = new IettIndResulRevIettrr();
		
		int cont = 0;
		while (it.hasNext()) {
			cont++;
			itemEstrtIndResul = (IettIndResulRevIettrr) it.next();
%>		
			<tr class="linha_subtitulo2">
				<td width="2%">
				<%if (!desabilitar){%>				
					<input type="checkbox" class="form_check_radio" name="excluirMeta" value="<%=itemEstrtIndResul.getCodIettirr()%>">
				<%}%>
				</td>
				<td width="4%">
				<%if (!desabilitar){%>				
					<a href=# onClick="javascript:aoClicarEditarMeta(document.form,'<%=itemEstrtIndResul.getCodIettirr()%>')">
					<img border="0" src="../../images/icon_alterar.png" alt="Alterar"></a>&nbsp;
				<%}%>
				</td>
				<td>
					<a href="javascript:MostraLinha('<%=cont%>');" title='Mostrar/esconder resposta'>
					<%
						if (itemEstrtIndResul.getItemEstrtIndResulIettr() != null)
							out.println(itemEstrtIndResul.getItemEstrtIndResulIettr().getNomeIettir());
						else
							out.println("Não Informado");
					%></a>
				</td>
<%
//					if ("Q".equalsIgnoreCase(itemEstrtIndResul.getIndTipoqtde())) {
//						out.println("Quantidade");
//					}
//					else if ("V".equalsIgnoreCase(itemEstrtIndResul.getIndTipoqtde())) {
//						out.println("Valor");
//					}
//					if (itemEstrtIndResul.getIndProjecaoIettrr().equalsIgnoreCase("S")){
//						out.println("Sim");
///					} else {
//						out.println("Não");
//					}
%>
<%
//					if (itemEstrtIndResul.getIndAcumulavelIettrr().equalsIgnoreCase("S")){
//						out.println("Sim");
//					} else {
//						out.println("Não");
//					}
%>
			</tr>
			<tr>
				<td></td>
				<td colspan="7">
					<table cellspacing="2" id="linha_<%=cont%>" style="display:none">
<%
						if (itemEstrtIndResul.getItemEstFisicoRevIettfrs() != null &&
								 	itemEstrtIndResul.getItemEstFisicoRevIettfrs().size() > 0){
%>
							<tr>
								<td class="titulo">Exercício</td>
								<td class="titulo">Quantidade Revisada</td>
							</tr>
<%
							List valores = new ArrayList(itemEstrtIndResul.getItemEstFisicoRevIettfrs());
								

							Collections.sort(valores,
							            new Comparator() {
							        		public int compare(Object o1, Object o2) {		        		    	        		 
								        		    ItemEstFisicoRevIettfr item1 = (ItemEstFisicoRevIettfr) o1;
								        		    ItemEstFisicoRevIettfr item2 = (ItemEstFisicoRevIettfr) o2;	        		        		        
										        					return item1.getExercicioExe().getDescricaoExe().compareToIgnoreCase(item2.getExercicioExe().getDescricaoExe());	
											}
							   	}
								);
							
							Iterator itQtPrev = valores.iterator();

							while (itQtPrev.hasNext()) {
								ItemEstFisicoRevIettfr ieEstFis = (ItemEstFisicoRevIettfr) itQtPrev.next();
%>
								<tr>
									<td class="form_label" align="center">
										<%=ieEstFis.getExercicioExe().getDescricaoExe()%>
									</td>
									<td class="form_label" align="center">
										<%=Pagina.trocaNullNumeroDecimalSemMilhar(ieEstFis.getQtdPrevistaIettfr())%>
									</td>
								</tr>
<%
							}
						} else {
%>							
							<tr><td class="form_label">Não há quantidades previstas</td></tr>
<%
						}
%>
					</table>
				</td>
			</tr>
<%
		}
	}
%>	</table> <%}
}%>