<%@ page import="ecar.util.Dominios" %>


<%
	List listaRecurso = new ArrayList();
	StringBuffer validaRecursos = new StringBuffer();
%>

	<input type="hidden" name="codIett" value="<%=itemEstrutura.getCodIett()%>">
	<input type="hidden" name="codIettrev" value="<%=itemEstruturaRevisao.getCodIettrev()%>">
	<input type="hidden" name="codAba" value="<%=Pagina.getParamStr(request, "codAba")%>">
	<input type="hidden" name="codAbaRev" value="<%=Pagina.getParamStr(request, "codAbaRec")%>">
	<input type="hidden" name="hidAcao" value="">
	
<%
	/** CONTROLA O NÚMERO DE RECURSO QUE SERÃO EXIBIDOS NA TELA **/
	int numRecursos = 0;
	if (!"".equals(Pagina.getParamStr(request, "numRecursos"))) {
		numRecursos = Pagina.getParamInt(request, "numRecursos");
	}
%>
	<input type=hidden name="adicionou" value="">
	<input type=hidden name="excluiu" value="">
	<input type=hidden name="excluir" value="">

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
				codFonte = Pagina.getParamStr(request, "codFonr");
				FonteRecursoFonr fonteAux = (FonteRecursoFonr) estruturaFuncaoDao.buscar(FonteRecursoFonr.class, Long.valueOf(codFonte));
				nomeFonte = fonteAux.getNomeFonr();
			}
			if(ieFonte.getFonteRecursoFonr()!=null){
				codFonte = ieFonte.getFonteRecursoFonr().getCodFonr().toString();
				nomeFonte = ieFonte.getFonteRecursoFonr().getNomeFonr();
			}
			%>
			<input type="text" name="nomeFonr" size="55" value="<%=Pagina.trocaNull(nomeFonte)%>" disabled>
			<input type="hidden" name="codFonr" value="<%=codFonte%>">			
			<input type="button" name="pesqFonte" value="Pesquisar" class="botao" <%=_disabled%> onclick="popup_pesquisa('ecar.popup.PopUpFonteRecurso', 'getDadosPesquisa', '&codIett=<%=Pagina.getParamStr(request, "codIett")%>&codAba=<%=Pagina.getParamStr(request, "codAba")%>');">		
			<input type=hidden name="numRecursos" value="<%=numRecursos%>">	
			
		</td>
	</tr>		
<!-- 	<tr>
		<td class="label">* Data de C&acirc;mbio</td>
		<td> -->
			<%
			Date dataCambio = null;
			if (ieFonte.getDataValorEfieftr() != null) {
				dataCambio = ieFonte.getDataValorEfieftr();
			} else {
				if (!"".equals(Pagina.getParamStr(request, "dataValorEfieft"))) {
					dataCambio = Data.parseDate(request.getParameter("dataValorEfieft"));	
				} else {
					dataCambio = Data.getDataAtual();
				}
			}
			%>
		<input type="hidden" name="dataValorEfieft" value="<%=Pagina.trocaNullData(dataCambio)%>">	
<!--	<input type="text" name="dataValorEfieft" size="13" maxlength="10" value="<%=Pagina.trocaNullData(dataCambio)%>" onkeyup="mascaraData(event, this);" <%=_disabled%>>
		</td>
	</tr> -->
	
	<tr>
		<td colspan="2" align="center">
			<br>
			<table border="1" cellspacing="0" width="10%">
				<tr class="corpo_tabela">		
					<td align="center">Recurso</td>
					<td align="center">Valor</td>
					<%
					ExercicioDao exercicioDao = new ExercicioDao(request);
					
					Collection listaExercicios = exercicioDao.getExerciciosValidos(itemEstrutura.getCodIett());
					Iterator itExerc = listaExercicios.iterator();
					
					int numExe = 0;
					
					while (itExerc.hasNext()) {
						ExercicioExe exercicio = (ExercicioExe) itExerc.next();
						numExe++;
						%>
						<td align="center"><%=exercicio.getDescricaoExe()%></td>
						<%
					}
					
					double[] totalAprovExe = new double[numExe];
					double[] totalRevExe = new double[numExe];
					itExerc = listaExercicios.iterator();
					numExe = 0;
					while (itExerc.hasNext()) {
						ExercicioExe exercicio = (ExercicioExe) itExerc.next();
						totalAprovExe[numExe] = 0;
						totalRevExe[numExe] = 0;
						numExe++;
					}
					%>
					<td align="center">Total</td>
					<td>&nbsp;</td>
				</tr>
				<%
				ItemEstruturaPrevisaoRevDao iePrevisaoDao = new ItemEstruturaPrevisaoRevDao(request);
				
				List listaRec = new ArrayList();
				Iterator itListaRec = listaRec.iterator();
				
				List listaItemRec = new ArrayList();
				
				/** VERIFICAR SE VALORES VEM DO REQUEST **/
				if ("".equals(Pagina.getParamStr(request, "adicionou")) &&
							"".equals(Pagina.getParamStr(request, "excluiu"))) {
					if (ieFonte.getFonteRecursoFonr() != null) {
						// RecursoRec distintos cadastrados (linhas das tabelas)
						listaRec = iePrevisaoDao.getRecursosDistintosByFonteRecurso(ieFonte.getFonteRecursoFonr().getCodFonr(), ieFonte.getItemEstruturarevisaoIettrev().getCodIettrev());

						/** SE VALORES NÃO VEM DO REQUEST **/
						/** VERIFICAR O NÚMERO DE RECURSOS PARA A FONTE **/
						int numRecBanco = 0;

						if (listaRec != null && listaRec.size() > 0) {
							numRecBanco = listaRec.size();
							itListaRec = listaRec.iterator();
						}
						if (numRecBanco > numRecursos) {
							numRecursos = numRecBanco;
							%>
							<script language="JavaScript">
								document.form.numRecursos.value = <%=numRecBanco%>;
							</script>
							<%
						}
						
						// ItemEstPrevisao cadastrados
						listaItemRec = iePrevisaoDao.getRecursosByFonteRecurso(ieFonte.getFonteRecursoFonr().getCodFonr(), ieFonte.getItemEstruturarevisaoIettrev().getCodIettrev());
					}
				}
				
				double totalAprovadoGeral = 0;
				double totalRevisadoGeral = 0;
				
				/* Linha do Recurso que deve ser excluída */
				int excluir = Pagina.getParamInt(request, "excluir");
				
				if (!"".equals(Pagina.getParamStr(request, "excluiu"))) {
					if (numRecursos > 1) {
						numRecursos--;
					}
				}
				
				int col = 0;
				int linha = 0;				
				int i = 0;
				Iterator recursos = new RecursoDao(request).listar(RecursoRec.class, new String[]{"nomeRec", "ASC"}).iterator();
				
				double totalAprovRec = 0;
				double totalRevRec = 0;
									
				while(recursos.hasNext())
				{
					String nomeCombo = "recursoRec" + i;
					String selRecurso = "";
					String script = _disabled + " id=\"combo\"";

					col = 0;		
					linha = i;				
					RecursoRec rec = (RecursoRec) recursos.next();
					%>
					<tr class="corpo_tabela">		
						<td rowspan="2" align="center">						
							<%=rec.getNomeRec()%>
							<input type="hidden" name="<%=nomeCombo%>" value="<%=rec.getCodRec()%>">
						</td>

<!-- 
						<td rowspan="2" align="center">
							<combo:ComboTag 
									nome="<%=nomeCombo%>"
									objeto="ecar.pojo.RecursoRec" 
									label="nomeRec" 
									value="codRec" 
									order="nomeRec"
									filters="indAtivoRec=S"
									scripts="<%=script%>"
									selected="<%=selRecurso%>"
							/>
						</td>
-->

						
						<td>Aprovado</td>
						<%
						Iterator itExercAprov = listaExercicios.iterator();
						while (itExercAprov.hasNext()) {
							ExercicioExe exercicio = (ExercicioExe) itExercAprov.next();
							
							String valorCampo = "0";
							String nomeCampo = "valorAprovadoEfiep" + i + "e" + exercicio.getCodExe().toString();
							String nomeCampoRequest = "valorAprovadoEfiep" + linha + "e" + exercicio.getCodExe().toString();
							String idCampo = "aprovL" + i + "C" + col;
							
							/** VERIFICAR SE VALORES VEM DO REQUEST **/
							if (!"".equals(Pagina.getParamStr(request, "adicionou")) ||
										!"".equals(Pagina.getParamStr(request, "excluiu"))) {
								// vem do request
								valorCampo = Pagina.getParamStr(request, nomeCampoRequest);
								if (!"".equals(valorCampo)) {
									valorCampo = Pagina.trocaNullNumeroDecimalSemMilhar(Double.valueOf(Util.formataNumero(valorCampo)));
									totalAprovRec += Double.valueOf(Util.formataNumero(valorCampo)).doubleValue();
									totalAprovExe[col] += Double.valueOf(Util.formataNumero(valorCampo)).doubleValue();
								}
							} else {
								// não vem do request - selecionar do banco
								//** Comparar ItemEstPrevisao com exercicio e recurso
								Iterator itItemRec = listaItemRec.iterator();
								while (itItemRec.hasNext()) {
									EfIettPrevisaoRevEfiepr  ieRecurso = (EfIettPrevisaoRevEfiepr) itItemRec.next();
									if (ieRecurso.getExercicioExe().equals(exercicio) && 
												ieRecurso.getRecursoRec().equals(rec)) {
										valorCampo = Pagina.trocaNullNumeroDecimalSemMilhar(ieRecurso.getValorAprovadoEfiepr());
										totalAprovRec += ieRecurso.getValorAprovadoEfiepr().doubleValue();
										totalAprovExe[col] += ieRecurso.getValorAprovadoEfiepr().doubleValue();
									}
								}
							}
							%>
							<td align="center">
								<input type="text" name="<%=nomeCampo%>" id="<%=idCampo%>" value="<%=valorCampo%>" <%=_disabled%> size="10" onblur="calcularTotais()">
							</td>
							<%
							col++;
						}
						%>
						<td align="center">
							<input type="text" name="totalAprovRec<%=i%>" value="<%=Util.formataNumeroDecimalSemMilhar(totalAprovRec)%>" disabled size="10">
						</td>

<!-- 
						<td rowspan="2" align="center">
							<input type="button" value="Excluir Recurso" onclick="excluirRecurso(<%=i%>);">
						</td>
-->					</tr>
					<tr class="corpo_tabela">		
						<td>Revisado</td>
						<%
						col = 0;
						Iterator itExercRev = listaExercicios.iterator();
						while (itExercRev.hasNext()) {
							ExercicioExe exercicio = (ExercicioExe) itExercRev.next();
							
							String valorCampo = "0";
							String nomeCampoRev = "valorRevisadoEfiep" + i + "e" + exercicio.getCodExe().toString();
							String nomeCampoRevRequest = "valorRevisadoEfiep" + linha + "e" + exercicio.getCodExe().toString();
							String nomeCampoAprov = "valorAprovadoEfiep" + i + "e" + exercicio.getCodExe().toString();
							String idCampo = "revL" + i + "C" + col;
							
							/** VERIFICAR SE VALORES VEM DO REQUEST **/
							if (!"".equals(Pagina.getParamStr(request, "adicionou"))
										|| !"".equals(Pagina.getParamStr(request, "excluiu"))) {
								// vem do request
								valorCampo = Pagina.getParamStr(request, nomeCampoRevRequest);
								if (!"".equals(valorCampo)) {
									valorCampo = Pagina.trocaNullNumeroDecimalSemMilhar(Double.valueOf(Util.formataNumero(valorCampo)));
									totalRevRec += Double.valueOf(Util.formataNumero(valorCampo)).doubleValue();
									totalRevExe[col] += Double.valueOf(Util.formataNumero(valorCampo)).doubleValue();
								}
							} else {
								// não vem do request - selecionar do banco
								//** Comparar ItemEstPrevisao com exercicio e recurso
								Iterator itItemRec = listaItemRec.iterator();
								while (itItemRec.hasNext()) {
									EfIettPrevisaoRevEfiepr ieRecurso = (EfIettPrevisaoRevEfiepr) itItemRec.next();
									if (ieRecurso.getExercicioExe().equals(exercicio) && 
												ieRecurso.getRecursoRec().equals(rec)) {
										valorCampo = Pagina.trocaNullNumeroDecimalSemMilhar(ieRecurso.getValorRevisadoEfiepr());
										totalRevRec += ieRecurso.getValorRevisadoEfiepr().doubleValue();
										totalRevExe[col] += ieRecurso.getValorRevisadoEfiepr().doubleValue();
									}
								}
							}
							
							/** CRIAÇÃO DO JAVASCRIPT DE VALIDAÇÃO DOS VALORES **/
							validaRecursos.append("\t cont = 0; \n");
							validaRecursos.append("\t \n");
							validaRecursos.append("\t if (trim(form." + nomeCampoAprov + ".value) != \"\") { \n");
							validaRecursos.append("\t\t cont++; \n");
							validaRecursos.append("\t\t if (!validaDecimal(form." + nomeCampoAprov + ".value)) { \n");
							validaRecursos.append("\t\t\t alert(\"" + _msg.getMensagem("itemEstrutura.recurso.validacao.valorAprovadoEfiep.invalido") + "\"); \n");
							validaRecursos.append("\t\t\t form." + nomeCampoAprov + ".focus(); \n");
							validaRecursos.append("\t\t\t return false; \n");
							validaRecursos.append("\t\t } \n");
							validaRecursos.append("\t } \n");
							validaRecursos.append("\t \n");
							validaRecursos.append("\t if (trim(form." + nomeCampoRev + ".value) != \"\") { \n");
							validaRecursos.append("\t\t cont++; \n");
							validaRecursos.append("\t\t if (!validaDecimal(form." + nomeCampoRev + ".value)) { \n");
							validaRecursos.append("\t\t\t alert(\"" + _msg.getMensagem("itemEstrutura.recurso.validacao.valorRevisadoEfiep.invalido") + "\"); \n");
							validaRecursos.append("\t\t\t form." + nomeCampoRev + ".focus(); \n");
							validaRecursos.append("\t\t\t return false; \n");
							validaRecursos.append("\t\t } \n");
							validaRecursos.append("\t } \n");
							validaRecursos.append("\t \n");
							validaRecursos.append("\t if (cont > 0 && form." + nomeCombo + ".value != \"\") { \n");
							validaRecursos.append("\t\t cont++; \n");
							validaRecursos.append("\t } \n");
							validaRecursos.append("\t \n");
							validaRecursos.append("\t if (cont > 0 && cont < 3) { \n");
							validaRecursos.append("\t\t alert(\"Para que os valores sejam gravados é necessário preencher os dois valores, Aprovado e Revisado, e selecionar o Recurso.\"); \n");
							validaRecursos.append("\t\t return false; \n");
							validaRecursos.append("\t } \n");
							validaRecursos.append("\t \n");
							/** FIM -- CRIAÇÃO DO JAVASCRIPT DE VALIDAÇÃO DOS VALORES **/
							%>
							<td align="center">
								<input type="text" name="<%=nomeCampoRev%>" id="<%=idCampo%>" value="<%=valorCampo%>" <%=_disabled%> size="10" onblur="calcularTotais()">
							</td>
							<%
							col++;
						}
						%>
						<td align="center">
							<input type="text" name="totalRevRec<%=i%>" value="<%=Util.formataNumeroDecimalSemMilhar(totalRevRec)%>" disabled size="10">
						</td>
					</tr>
					<%
					i++;		
					numRecursos++;	
				}
%>					
				<tr class="titulo">
					<td rowspan="3" align="center">
<!-- 						<input type="button" value="Adicionar Recurso" onclick="adicionarRecurso();"> -->
						<input type="hidden" name="numLinhas" value="<%=i%>">
						<input type="hidden" name="numColunas" value="<%=col%>">
						<script>
							document.form.numRecursos.value = <%=numRecursos%>;
						</script>
					</td>
					<td>Total Aprovado</td>
					<%
					col = 0;
					Iterator itTotAprov = listaExercicios.iterator();
					while (itTotAprov.hasNext()) {
						ExercicioExe exercicio = (ExercicioExe) itTotAprov.next();
						
						String valorCampo = Pagina.trocaNullNumeroDecimalSemMilhar(new Double(totalAprovExe[col]));
						totalAprovadoGeral += totalAprovExe[col];
						%>
						<td align="center">
							<input type="text" name="totalAprovExe<%=col%>" value="<%=valorCampo%>" disabled size="10">
						</td>
						<%
						col++;
					}
					%>
					<td align="center">
						<input type="text" name="totalAprovadoGeral" value="<%=Util.formataNumeroDecimalSemMilhar(totalAprovadoGeral)%>" disabled size="10">
					</td>
					<td rowspan="3">&nbsp;</td>
				</tr>
				<tr class="titulo">
					<td>Total Revisado</td>
					<%
					col = 0;
					Iterator itTotRev = listaExercicios.iterator();
					while (itTotRev.hasNext()) {
						ExercicioExe exercicio = (ExercicioExe) itTotRev.next();
						
						String valorCampo = Pagina.trocaNullNumeroDecimalSemMilhar(new Double(totalRevExe[col]));
						totalRevisadoGeral += totalRevExe[col];
						%>
						<td align="center">
							<input type="text" name="totalRevExe<%=col%>" value="<%=valorCampo%>" disabled size="10">
						</td>
						<%
						col++;
					}
					%>
					<td align="center">
						<input type="text" name="totalRevisadoGeral" value="<%=Util.formataNumeroDecimalSemMilhar(totalRevisadoGeral)%>" disabled size="10">		
									</td>
				</tr>
				<tr class="titulo">
					<td>Aumento/Redução</td>
					<%
					col = 0;
					Iterator itDif = listaExercicios.iterator();
					while (itDif.hasNext()) {
						ExercicioExe exercicio = (ExercicioExe) itDif.next();
						
						String valorCampo = Util.formataNumeroDecimalSemMilhar(totalRevExe[col] - totalAprovExe[col]);
						%>
						<td align="center">
							<input type="text" name="diferenca<%=col%>" value="<%=valorCampo%>" disabled size="10">
						</td>
						<%
						col++;
					}
					
					double difTotal = totalAprovadoGeral - totalRevisadoGeral;
					%>
					<td align="center"
						<input type="text" name="diferencaTotal" value="<%=Util.formataNumeroDecimalSemMilhar(difTotal)%>" disabled size="10">
					</td>
				</tr>
			</table>
		</td>
	</tr>				
	<%	

//				for (int i = 0; i < numRecursos; i++) {
//					double totalAprovRec = 0;
//					double totalRevRec = 0;
//					
//					String nomeCombo = "recursoRec" + i;
//					String selRecurso = "";
//					String script = _disabled + " id=\"combo\"";
//					
//					int linha = i;
//					col = 0;
//					
//					/* QUANDO HÁ EXCLUSÃO PULAR A LINHA QUE FOI EXCLUÍDA 
//					**		PEGANDO A PRÓXIMA LINHA DO REQUEST CONTROLADA PELA 
//					**		VARIÁVEL i */
//					if (!"".equals(Pagina.getParamStr(request, "excluiu"))) {
//						if (excluir <= linha) {
//							linha++;
//						}
//					}
					
//					RecursoRec recurso = null;
//					/** VERIFICAR SE VALORES VEM DO REQUEST **/
//					if (!"".equals(Pagina.getParamStr(request, "adicionou")) ||
//								!"".equals(Pagina.getParamStr(request, "excluiu"))) {
//						// vem do request
//						selRecurso = Pagina.getParamStr(request, "recursoRec" + linha);
//					} else {
//						// não vem do request - exibir valores da lista (BD)
//						if (itListaRec.hasNext()) {
//							recurso = (RecursoRec) itListaRec.next();
//							selRecurso = recurso.getCodRec().toString();
//						}
//					}
				%>

	
<script language="JavaScript">
function validaRecursos() {
<%=validaRecursos.toString()%>
	return true;
}

function adicionarRecurso() {
	document.form.numRecursos.value = parseInt(document.form.numRecursos.value) + 1;
	document.form.adicionou.value = "adicionou";
	document.form.submit();
}

function excluirRecurso(num) {
	if (confirm("Confirma exclusão do Recurso? Para efetivar a exclusão clique em gravar.")) {
		document.form.excluir.value = num;
		document.form.excluiu.value = "excluiu";
		document.form.submit();
	}
}
</script>

