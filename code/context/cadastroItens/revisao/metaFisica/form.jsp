<%@ taglib uri="/WEB-INF/ecar-combo.tld" prefix="combo" %>

<%
	ExercicioDao exercicioDao = new ExercicioDao(request);
	String acao = Pagina.getParamStr(request, "hidAcao");
	
	String selecionado = "";
	if(itemEstrtIndResul.getItemEstrtIndResulIettr() != null){
		selecionado = itemEstrtIndResul.getItemEstrtIndResulIettr().getCodIettir().toString();
	}
%>

	<input type="hidden" name="codIett" value="<%=itemEstrutura.getCodIett()%>">
	<input type=hidden name="codIettrev" value="<%=itemEstruturaRev.getCodIettrev()%>">
	<input type=hidden name="codAba" value="<%=Pagina.getParamStr(request, "codAba")%>">
	<input type=hidden name="codAbaMeta" value="<%=Pagina.getParamStr(request, "codAbaMeta")%>">
	<input type="hidden" name="hidGravado" value="S">	


	<tr>
		<td colspan=2>
			<div class="camposobrigatorios">* Campos de preenchimento obrigat&oacute;rio</div>
		</td>
	</tr>
	<tr>
		<td class="label">Tipo</td>
		<td>
			<combo:ComboTag 
				nome="codIettir"
				objeto="ecar.pojo.ItemEstrtIndResulIettr"
				label="nomeIettir"
				value="codIettir"
				order="nomeIettir"
				colecao="<%=indicadoresIett%>"
				selected="<%=selecionado%>"
				scripts="<%=_disabled%>"
			/>
		</td>
	</tr>
	<tr>
		<td colspan="2">
			&nbsp;
		</td>
	</tr>
	<tr>
		<td colspan="2" align="center" class="texto_negrito">
			<%=estruturaFuncaoDao.getLabelQuantidadesPrevistas(itemEstrutura.getEstruturaEtt())%>
		</td>
	</tr>
	<tr>
		<td colspan="2" width="10%" align="center">
			<table class="texto_negrito">
				<tr>
					<td width="20%" class="texto"></td>
					<td width="30%" align="center">Exercício</td>
					<td width="30%" align="center">Quantidade Prevista</td>
					<td width="20%" class="texto"></td>
				<tr>
<%
				StringBuffer validaQtdPrev = new StringBuffer();
				
				List listaExercicio = exercicioDao.getExerciciosValidos(itemEstrutura.getCodIett());
				Iterator itExercicio = listaExercicio.iterator();
				
				if (itExercicio.hasNext()) {
					while (itExercicio.hasNext()) {
						ExercicioExe exercicio = (ExercicioExe) itExercicio.next();
						
						ItemEstFisicoRevIettfr itemEstrutFisico = new ItemEstFisicoRevIettfr();
						ItemEstFisicoRevIettfrDAO itemEstrutFisicoDao = new ItemEstFisicoRevIettfrDAO(request);
						
						if (itemEstrtIndResul.getCodIettirr() != null) {
							try {
								itemEstrutFisico = (ItemEstFisicoRevIettfr) itemEstrutFisicoDao.buscar(Long.valueOf(itemEstrtIndResul.getCodIettirr().intValue()), exercicio.getCodExe());
							} catch (Exception e) {
								org.apache.log4j.Logger.getLogger(this.getClass()).error(e);
								itemEstrutFisico = new ItemEstFisicoRevIettfr();
							}
						}
%>				
						<tr>
							<td class="texto"></td>
							<td align="center" class="texto">
								<%=exercicio.getDescricaoExe()%>
							</td>
							<td align="center" class="texto">
								<input type="text" name="qtdPrevistaIettf<%=exercicio.getCodExe()%>" value="<%=Pagina.trocaNullNumeroDecimalSemMilhar(itemEstrutFisico.getQtdPrevistaIettfr())%>" size="18" <%=_disabled%>>
							</td>
							<td class="texto"></td>
						</tr>
<%
						validaQtdPrev.append("if (Trim(form.qtdPrevistaIettf" + exercicio.getCodExe().toString() + ".value) != \"\") { \n");
						validaQtdPrev.append("	\t if(!validaDecimal(form.qtdPrevistaIettf" + exercicio.getCodExe().toString() + ".value)){ \n");
						validaQtdPrev.append("	\t	\t alert(\"" + _msg.getMensagem("itemEstrutura.quantPrevista.validacao.qtdPrevistaIettf.invalido") + "\"); \n");
						validaQtdPrev.append("	\t	\t form.qtdPrevistaIettf" + exercicio.getCodExe().toString() + ".focus(); \n");
						validaQtdPrev.append("	\t	\t return(false); \n");
						validaQtdPrev.append("	\t } \n");
						validaQtdPrev.append("} \n");
						validaQtdPrev.append("\n");
					}
				} else {
%>
					<tr>
						<td class="texto"></td>
						<td align="center" class="texto" colspan="2">
							Não há exercícios.
						</td>
						<td class="texto"></td>
					</tr>
<%
				}
%>
			</table>
		<td>
	</tr>
	<tr>
		<td colspan="2">
			&nbsp;
		</td>
	</tr>
	
<script language="JavaScript">
function validaQuantidadePrevista(form){
<%=validaQtdPrev%>
	return(true);
}
</script>
