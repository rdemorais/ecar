<%
	List listaRecurso = new ArrayList();
	StringBuffer validaRecursos = new StringBuffer();
%>

	<input type=hidden name="codIett" value="<%=itemEstrutura.getCodIett()%>">
	<input type=hidden name="codAba" value="<%=Pagina.getParamStr(request, "codAba")%>">
	<input type=hidden name="hidAcao" value="">

	<tr> 
		<td colspan=2>
			<div class="camposobrigatorios">* Campos de preenchimento obrigat&oacute;rio</div>
		</td>
	</tr>
	<tr>
		<td class="label">* Nome</td>
		<td>
			<%
			// Jogar valores para uma variável para evitar nullPointerException
			String nomeFonte = "";
			String codFonte = "";
			if(!"".equals(Pagina.getParamStr(request, "codFonr"))){
				nomeFonte = Pagina.getParamStr(request, "nomeFonr");
				codFonte = Pagina.getParamStr(request, "codFonr");
			}
			if(ieFonte.getFonteRecursoFonr()!=null){
				nomeFonte = ieFonte.getFonteRecursoFonr().getNomeFonr();
				codFonte = ieFonte.getFonteRecursoFonr().getCodFonr().toString();
			}
			%>
			<input type="text" name="nomeFonr" size="55" value="<%=Pagina.trocaNull(nomeFonte)%>" disabled>
			<input type="hidden" name="codFonr" value="<%=codFonte%>">			
			<input type="button" name="pesqFonte" value="Pesquisar" class="botao" <%=_disabled%> onclick="popup_pesquisa('ecar.popup.PopUpFonteRecurso');">
		</td>
	</tr>		
	<tr>
		<td class="label">* Data de C&acirc;mbio</td>
		<td>
			<%
			Date dataCambio = null;
			if (ieFonte.getDataValorEfieft() != null) {
				dataCambio = ieFonte.getDataValorEfieft();
			} else {
				if (!"".equals(Pagina.getParamStr(request, "dataValorEfieft"))) {
					dataCambio = Data.parseDate(request.getParameter("dataValorEfieft"));	
				}
			}
			%>
			<input type="text" name="dataValorEfieft" size="13" maxlength="10" value="<%=Pagina.trocaNullData(dataCambio)%>" onkeyup="mascaraData(event, this);" <%=_disabled%>>
		</td>
	</tr>
	<tr>
		<td colspan="2" align="center">
			<br>
			<table border="1" cellspacing="0" width="10%">
				<tr>
					<td width="5%">&nbsp;</td>
<%
					ExercicioDao exercicioDao = new ExercicioDao(request);
					FonteRecursoDao fonteRecursoDao = new FonteRecursoDao(request);
					
					Collection listaExercicios = exercicioDao.getExerciciosValidos(itemEstrutura.getCodIett());
					Iterator itExerc = listaExercicios.iterator();
					int cont = 0;
					
					while (itExerc.hasNext()) {
						ExercicioExe exercicio = (ExercicioExe) itExerc.next();
						cont++;
						
						//** Buscar Recurso do Exercício e adicionar na lista
						if (ieFonte.getComp_id() != null) {
							EfItemEstPrevisaoEfiep ieRecurso = new ItemEstruturaPrevisaoDao(request).buscar(
									itemEstrutura.getCodIett(),
									ieFonte.getFonteRecursoFonr().getCodFonr(),
									exercicio.getCodExe());
							
							if (ieRecurso != null) {
								listaRecurso.add(ieRecurso);
							}
						}
						
						validaRecursos.append("\t cont" + exercicio.getCodExe().toString() + " = 0; \n");
%>
						<td width="5%">
							<%=exercicio.getDescricaoExe()%>
							<input type="hidden" name="exercicioExe<%=exercicio.getCodExe()%>" value="<%=exercicio.getCodExe()%>">
						</td>
<%
					}
%>
					<td width="5%">Total</td>
				</tr>
				<tr>
					<td>Recurso</td>
<%
					Iterator itExerc2 = listaExercicios.iterator();
					while (itExerc2.hasNext()) {
						ExercicioExe exercicio = (ExercicioExe) itExerc2.next();
						
						String nomeCombo = "recursoRec" + exercicio.getCodExe().toString();
						String selRecurso = "";
						
						//** Comparar com exercicio da lista e atualizar valor
						Iterator itRecurso = listaRecurso.iterator();
						while (itRecurso.hasNext()) {
							EfItemEstPrevisaoEfiep recAux = (EfItemEstPrevisaoEfiep) itRecurso.next();
							if (recAux.getExercicioExe().equals(exercicio)) {
								selRecurso = recAux.getRecursoRec().getCodRec().toString();
							}
						}
						
						validaRecursos.append("\t if (form.").append(nomeCombo).append(".value != \"\") { \n");
						validaRecursos.append("\t\t cont").append(exercicio.getCodExe().toString()).append("++; \n");;
						validaRecursos.append("\t } \n");
%>
						<td>
							<combo:ComboTag 
									nome="<%=nomeCombo%>"
									objeto="ecar.pojo.RecursoRec" 
									label="nomeRec" 
									value="codRec" 
									order="nomeRec"
									filters="indAtivoRec=S"
									scripts="<%=_disabled%>"
									selected="<%=selRecurso%>"
							/>
						</td>
<%
					}
%>
					<td>&nbsp;</td>
				</tr>
				<tr>
					<td>Valor Aprovado</td>
<%
					double totalAprovado = 0;
					Iterator itExerc3 = listaExercicios.iterator();
					while (itExerc3.hasNext()) {
						ExercicioExe exercicio = (ExercicioExe) itExerc3.next();
						
						String valorCampo = "";
						
						//** Comparar com exercicio da lista e atualizar valor
						Iterator itRecurso = listaRecurso.iterator();
						while (itRecurso.hasNext()) {
							EfItemEstPrevisaoEfiep recAux = (EfItemEstPrevisaoEfiep) itRecurso.next();
							if (recAux.getExercicioExe().equals(exercicio)) {
								valorCampo = Pagina.trocaNullNumeroDecimalSemMilhar(recAux.getValorAprovadoEfiep());
								totalAprovado += recAux.getValorAprovadoEfiep().doubleValue();
							}
						}
						
						validaRecursos.append("\t if (trim(form.valorAprovadoEfiep").append(exercicio.getCodExe().toString()).append(".value) != \"\") { \n");
						validaRecursos.append("\t\t if (!validaDecimal(form.valorAprovadoEfiep").append(exercicio.getCodExe().toString()).append(".value)) { \n");
						validaRecursos.append("\t\t\t alert(\"").append(_msg.getMensagem("itemEstrutura.recurso.validacao.valorAprovadoEfiep.invalido")).append("\");");
						validaRecursos.append("\t\t\t form.valorAprovadoEfiep").append(exercicio.getCodExe().toString()).append(".focus(); \n");
						validaRecursos.append("\t\t\t return false; \n");
						validaRecursos.append("\t\t } else {");
						validaRecursos.append("\t\t\t cont").append(exercicio.getCodExe().toString()).append("++; \n");
						validaRecursos.append("\t\t } \n");
						validaRecursos.append("\t } \n");
%>
						<td>
							<input type="text" name="valorAprovadoEfiep<%=exercicio.getCodExe()%>" value="<%=valorCampo%>" <%=_disabled%> size="8">
						</td>
<%
					}
%>
					<td>
						<input type="text" name="totalAprovado" value="<%=Pagina.trocaNullNumeroDecimalSemMilhar(totalAprovado)%>" disabled size="8">
					</td>
				</tr>
				<tr>
					<td>Valor Revisado</td>
<%
					double totalRevisado = 0;
					Iterator itExerc4 = listaExercicios.iterator();
					while (itExerc4.hasNext()) {
						ExercicioExe exercicio = (ExercicioExe) itExerc4.next();
						
						String valorCampo = "";
						
						//** Comparar com exercicio da lista e atualizar valor
						Iterator itRecurso = listaRecurso.iterator();
						while (itRecurso.hasNext()) {
							EfItemEstPrevisaoEfiep recAux = (EfItemEstPrevisaoEfiep) itRecurso.next();
							if (recAux.getExercicioExe().equals(exercicio)) {
								valorCampo = Pagina.trocaNullNumeroDecimalSemMilhar(recAux.getValorRevisadoEfiep());
								totalRevisado += recAux.getValorRevisadoEfiep().doubleValue();
							}
						}
						
						validaRecursos.append("\t if (trim(form.valorRevisadoEfiep").append(exercicio.getCodExe().toString()).append(".value) != \"\") { \n");
						validaRecursos.append("\t\t if (!validaDecimal(form.valorRevisadoEfiep").append(exercicio.getCodExe().toString()).append(".value)) { \n");
						validaRecursos.append("\t\t\t alert(\"").append( _msg.getMensagem("itemEstrutura.recurso.validacao.valorRevisadoEfiep.invalido")).append("\");");
						validaRecursos.append("\t\t\t form.valorRevisadoEfiep").append(exercicio.getCodExe().toString()).append(".focus(); \n");
						validaRecursos.append("\t\t\t return false; \n");
						validaRecursos.append("\t\t } else {");
						validaRecursos.append("\t\t\t cont").append(exercicio.getCodExe().toString()).append("++; \n");
						validaRecursos.append("\t\t } \n");
						validaRecursos.append("\t } \n");
%>
						<td>
							<input type="text" name="valorRevisadoEfiep<%=exercicio.getCodExe()%>" value="<%=valorCampo%>" <%=_disabled%> size="8">
						</td>
<%
					}
%>
					<td>
						<input type="text" name="totalRevisado" value="<%=Pagina.trocaNullNumeroDecimalSemMilhar(totalRevisado)%>" disabled size="8">
					</td>
				</tr>
				<tr>
					<td>Total</td>
<%
					double totalGeral = 0;
					Iterator itExerc5 = listaExercicios.iterator();
					while (itExerc5.hasNext()) {
						ExercicioExe exercicio = (ExercicioExe) itExerc5.next();
						
						String valorTotal = "0,00";
						
						//** Comparar com exercicio da lista e atualizar valor
						Iterator itRecurso = listaRecurso.iterator();
						while (itRecurso.hasNext()) {
							EfItemEstPrevisaoEfiep recAux = (EfItemEstPrevisaoEfiep) itRecurso.next();
							if (recAux.getExercicioExe().equals(exercicio)) {
								valorTotal = Pagina.trocaNullNumeroDecimalSemMilhar(recAux.getValorRevisadoEfiep().doubleValue() + recAux.getValorAprovadoEfiep().doubleValue());
								totalGeral += recAux.getValorRevisadoEfiep().doubleValue() + recAux.getValorAprovadoEfiep().doubleValue();
							}
						}
						
						validaRecursos.append("\t if (cont").append(exercicio.getCodExe()).append(" > 0 && cont").append(exercicio.getCodExe()).append(" < 3) { \n");
						validaRecursos.append("\t\t return false; \n");
						validaRecursos.append("\t } \n");
%>
						<td>
							<input type="text" name="totalExercicio<%=exercicio.getCodExe()%>" value="<%=valorTotal%>" disabled size="8">
						</td>
<%
					}
%>
					<td>
						<input type="text" name="totalGeral" value="<%=Pagina.trocaNullNumeroDecimalSemMilhar(totalGeral)%>" disabled size="8">
					</td>
				</tr>
			</table>
		</td>
	</tr>
	
<script>
function gravaHidCodigo() {
	document.form.codExe.value = document.form.exercicioExe.value;
}
function validaRecursos() {
<%=validaRecursos.toString()%>
	return true;
}
</script>

	<% /* Controla o estado do Menu (aberto ou fechado) */%>
	<%@ include file="../../include/estadoMenu.jsp"%>
