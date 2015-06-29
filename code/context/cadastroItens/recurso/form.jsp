<%@ page import="ecar.pojo.ItemEstruturaIett" %>
<%@ page import="ecar.dao.ItemEstruturaDao" %>
<%@ page import="ecar.pojo.EstruturaFuncaoEttf" %>
		
<%@ page import="ecar.dao.RecursoDao" %>
<%@ page import="ecar.pojo.RecursoRec" %>
<%@ page import="java.util.Iterator" %>
<%@ page import="ecar.dao.ItemEstruturaPrevisaoDao" %>
<%@ page import="java.util.List" %>
<%@ page  import="java.util.ArrayList" %>
<%@ page import="comum.util.Pagina" %>
<%@ page import="ecar.pojo.ExercicioExe" %>
<%@ page import="ecar.dao.ExercicioDao" %>
<%@ page import="comum.util.Util" %>
<%@ page import="ecar.pojo.EfItemEstPrevisaoEfiep" %>
<%@ page import="ecar.util.Dominios" %>

<%@page import="ecar.dao.ConfiguracaoDao"%><jsp:directive.page import="java.math.BigDecimal"/>
<jsp:directive.page import="ecar.pojo.EstruturaEtt;"/>
<%
	List listaRecurso = new ArrayList();
	StringBuffer validaRecursos = new StringBuffer();

	String recursoDesc1 = (Pagina.trocaNull(new ConfiguracaoDao(request).getConfiguracao().getRecursoDescValor1Cfg()));
	String recursoDesc2 = (Pagina.trocaNull(new ConfiguracaoDao(request).getConfiguracao().getRecursoDescValor2Cfg()));
	
	//FIXME: só trata os dois primeiros campos configurados
	String recursoDesc3 = "";
	//String recursoDesc3 = (Pagina.trocaNull(configura.getRecursoDescValor3Cfg()));

%>

	<input type=hidden name="codIett" value="<%=itemEstrutura.getCodIett()%>">
	<input type=hidden name="codAba" value="<%=Pagina.getParamStr(request, "codAba")%>">
	<input type=hidden name="hidAcao" value="">
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
 	<input type=hidden name="numRecursos" value="<%=numRecursos%>">	

	<tr> 
		<td colspan=2>
			<div class="camposobrigatorios">* Campos de preenchimento obrigat&oacute;rio</div>
		</td>
	</tr>
	<tr>
		<td class="label">*
		<% //TODO Aqui que recupera o valor do campo %>
		<%=estruturaFuncaoDao.getLabelFuncaoFonteRecurso(estruturaFuncao.getEstruturaEtt()).toString()%> 
		</td> 
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
			
			if("S".equals(Pagina.getParamStr(request, "incluirNovoValor"))){
				codFonte = Pagina.getParamStr(request, "codProximaFonte");
				nomeFonte = Pagina.getParamStr(request, "nomeProximaFonte");
			}
			%>
			<input type="text" name="nomeFonr" size="55" value="<%=Pagina.trocaNull(nomeFonte)%>" disabled>
			<input type="hidden" name="codFonr" value="<%=codFonte%>">			
			<input type="button" name="pesqFonte" value="Pesquisar" class="botao" <%=_disabled%> onclick="popup_pesquisa('ecar.popup.PopUpFonteRecurso', undefined ,'&codAba=<%=Pagina.getParamStr(request, "codAba")%>&codIett=<%=itemEstrutura.getCodIett()%>');">		
		</td>
	</tr>		
<!-- 	<tr>
		<td class="label">* Data de C&acirc;mbio</td>
		<td> -->
			<%
			Date dataCambio = null;
			if (ieFonte.getDataValorEfieft() != null) {
				dataCambio = ieFonte.getDataValorEfieft();
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

	<!-- ********************* A Tabela começa daqui até ************************** -->

	<tr>
		<td colspan="2" align="center">
			<br>
			<table border="1" cellspacing="0" width="10%">
				<tr class="titulo">		
					<td align="center"><%=estruturaFuncaoDao.getLabelFuncaoRecurso(estruturaFuncao.getEstruturaEtt()).toString()%></td>
					<td align="center" width="20px"><%=estruturaFuncaoDao.getLabelFuncaoContasOrcamento(estruturaFuncao.getEstruturaEtt()).toString()%></td>
					<td align="center">Exercício</td>
					<%if(!"".equals(recursoDesc1.trim())) {%>
						<td align="center"><%=recursoDesc1%></td>
					<%} %>
					<%if(!"".equals(recursoDesc2.trim())) {%>
						<td align="center"><%=recursoDesc2%></td>
					<%} %>
					<%if(!"".equals(recursoDesc3.trim())) {%>					
						<td align="center"><%=recursoDesc3%></td>
					<%} %>
					<td align="center">Ativo</td>
				</tr>
				<%
				//Obtendo os exercícios válidos...
				ExercicioDao exercicioDao = new ExercicioDao(request);
				List listaExercicios = exercicioDao.getExerciciosValidos(itemEstrutura.getCodIett());

				//Obtendo os recursos....
				ItemEstruturaPrevisaoDao iePrevisaoDao = new ItemEstruturaPrevisaoDao(request);
				
				List listaRec = new ArrayList();
				Iterator itListaRec = listaRec.iterator();
				
				List listaItemRec = new ArrayList();
				
				/** VERIFICAR SE VALORES VEM DO REQUEST **/
				if ("".equals(Pagina.getParamStr(request, "adicionou")) &&
							"".equals(Pagina.getParamStr(request, "excluiu"))) {
					if (ieFonte.getFonteRecursoFonr() != null) {
						// RecursoRec distintos cadastrados (linhas das tabelas)
						listaRec = iePrevisaoDao.getRecursosDistintosByFonteRecurso(ieFonte.getFonteRecursoFonr().getCodFonr(), ieFonte.getItemEstruturaIett().getCodIett());

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
						listaItemRec = iePrevisaoDao.getRecursosByFonteRecurso(ieFonte.getFonteRecursoFonr().getCodFonr(), ieFonte.getItemEstruturaIett().getCodIett(), Dominios.SIM);
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
				int i = 0;
				Iterator recursos = new RecursoDao(request).listar(RecursoRec.class, new String[]{"sequenciaRec", "ASC"}).iterator();
				
				double totalAprovRec = 0;
				double totalRevRec = 0;
									
				while(recursos.hasNext()){
					RecursoRec recurso = (RecursoRec) recursos.next();
					
					String nomeCombo = "recursoRec" + i;

					col = 0;

					//Verificar a logica para nomear os campos...
					
					//Listando os exercícios... 			
					String nomeRecurso = recurso.getNomeRec();
					int qtdeExe = listaExercicios.size();
					Iterator itExercAprov = listaExercicios.iterator();
					while (itExercAprov.hasNext()) {
						ExercicioExe exercicio = (ExercicioExe) itExercAprov.next();
						
						String valorCampoAprovado = "0";
						String nomeCampoAprovado = "valorAprovadoEfiep" + i + "e" + exercicio.getCodExe().toString();
						String nomeCampoAprovadoRequest = "valorAprovadoEfiep" + i + "e" + exercicio.getCodExe().toString();
						String idCampoAprovado = "aprovL" + i + "C" + col;

						String valorCampoRevisado = "0";
						String nomeCampoRevisado = "valorRevisadoEfiep" + i + "e" + exercicio.getCodExe().toString();
						String nomeCampoRevisadoRequest = "valorRevisadoEfiep" + i + "e" + exercicio.getCodExe().toString();
						String idCampoRevisado = "revL" + i + "C" + col;
						
						String ativoCheck = "S";
						String nomeCampoAtivo = "ativo" + i + "e" + exercicio.getCodExe().toString();
						
						/** VERIFICAR SE VALORES VEM DO REQUEST **/
						if (!"".equals(Pagina.getParamStr(request, "adicionou")) ||
									!"".equals(Pagina.getParamStr(request, "excluiu"))) {
							// vem do request
							valorCampoAprovado = Pagina.getParamStr(request, nomeCampoAprovadoRequest);
							valorCampoRevisado = Pagina.getParamStr(request, nomeCampoRevisadoRequest);
							ativoCheck = Pagina.getParamStr(request, nomeCampoAtivo);
							
							if (!"".equals(valorCampoAprovado)) {
								valorCampoAprovado = Pagina.trocaNullNumeroDecimalSemMilhar(Double.valueOf(Util.formataNumero(valorCampoAprovado)));
								totalAprovRec += Double.valueOf(Util.formataNumero(valorCampoAprovado)).doubleValue();
								//totalAprovExe[col] += Double.valueOf(Util.formataNumero(valorCampoAprovado)).doubleValue();
							}

							if (!"".equals(valorCampoRevisado)) {
								valorCampoRevisado = Pagina.trocaNullNumeroDecimalSemMilhar(Double.valueOf(Util.formataNumero(valorCampoRevisado)));
								totalRevRec += Double.valueOf(Util.formataNumero(valorCampoRevisado)).doubleValue();
								//totalRevExe[col] += Double.valueOf(Util.formataNumero(valorCampoRevisado)).doubleValue();
							}
							
						} else {
							// não vem do request - selecionar do banco
							//** Comparar ItemEstPrevisao com exercicio e recurso
							if(ieFonte != null && ieFonte.getFonteRecursoFonr() != null){
								EfItemEstPrevisaoEfiep ieRecurso = iePrevisaoDao.buscar(itemEstrutura.getCodIett(), ieFonte.getFonteRecursoFonr().getCodFonr(), recurso.getCodRec(), exercicio.getCodExe());							
								
								BigDecimal valAprov = iePrevisaoDao.previsaoSomaValores(itemEstrutura.getCodIett(), ieFonte.getFonteRecursoFonr().getCodFonr(), exercicio.getCodExe(), recurso.getCodRec(), Dominios.EFIEP_VALOR_APROVADO);
								BigDecimal valRev = iePrevisaoDao.previsaoSomaValores(itemEstrutura.getCodIett(), ieFonte.getFonteRecursoFonr().getCodFonr(), exercicio.getCodExe(), recurso.getCodRec(), Dominios.EFIEP_VALOR_REVISADO);
								
								valorCampoAprovado = Pagina.trocaNullNumeroDecimalSemMilhar(valAprov);
								valorCampoRevisado = Pagina.trocaNullNumeroDecimalSemMilhar(valRev);
								
								if(valAprov != null)
									totalAprovRec += valAprov.doubleValue();
								if(valRev != null)
									totalRevRec += valRev.doubleValue();
								//totalAprovExe[col] += ieRecurso.getValorAprovadoEfiep().doubleValue();
								//totalRevExe[col] += ieRecurso.getValorRevisadoEfiep().doubleValue();
								
								ativoCheck = Pagina.trocaNull(ieRecurso.getIndAtivoEfiep());
							}
						}
					
						/** CRIAÇÃO DO JAVASCRIPT DE VALIDAÇÃO DOS VALORES **/
						validaRecursos.append("\t cont = 0; \n");
						validaRecursos.append("\t \n");
						
						int qtdeRec = 0;
						
						if(!"".equals(recursoDesc1.trim())) {
							validaRecursos.append("\t if (trim(form." + nomeCampoAprovado + ".value) != \"\") { \n");
							validaRecursos.append("\t\t cont++; \n");
							validaRecursos.append("\t\t if (!validaDecimal(form." + nomeCampoAprovado + ".value)) { \n");
							validaRecursos.append("\t\t\t alert(\"" + _msg.getMensagem("itemEstrutura.recurso.validacao.valorAprovadoEfiep.invalido") + "\"); \n");
							validaRecursos.append("\t\t\t form." + nomeCampoAprovado + ".focus(); \n");
							validaRecursos.append("\t\t\t return false; \n");
							validaRecursos.append("\t\t } \n");
							validaRecursos.append("\t } \n");
							validaRecursos.append("\t \n");
							qtdeRec++;
						}
						
						if(!"".equals(recursoDesc2.trim())) {
							validaRecursos.append("\t if (trim(form." + nomeCampoRevisado + ".value) != \"\") { \n");
							validaRecursos.append("\t\t cont++; \n");
							validaRecursos.append("\t\t if (!validaDecimal(form." + nomeCampoRevisado + ".value)) { \n");
							validaRecursos.append("\t\t\t alert(\"" + _msg.getMensagem("itemEstrutura.recurso.validacao.valorRevisadoEfiep.invalido") + "\"); \n");
							validaRecursos.append("\t\t\t form." + nomeCampoRevisado + ".focus(); \n");
							validaRecursos.append("\t\t\t return false; \n");
							validaRecursos.append("\t\t } \n");
							validaRecursos.append("\t } \n");
							validaRecursos.append("\t \n");
							qtdeRec++;
						}
						
						
						validaRecursos.append("\t if (cont > 0 && form." + nomeCombo + ".value != \"\") { \n");
						validaRecursos.append("\t\t cont++; \n");
						validaRecursos.append("\t } \n");
						validaRecursos.append("\t \n");
						validaRecursos.append("\t if (cont > 0 && cont < " + qtdeRec + ") { \n");
						validaRecursos.append("\t\t alert(\"Para que os valores sejam gravados é necessário preencher os valores.\"); \n");
						validaRecursos.append("\t\t return false; \n");
						validaRecursos.append("\t } \n");
						validaRecursos.append("\t \n");
						/** FIM -- CRIAÇÃO DO JAVASCRIPT DE VALIDAÇÃO DOS VALORES **/

						
						%>
						<tr class="corpo_tabela">	
						<%
							if(!"".equals(nomeRecurso)){
						%>	
							<td align="center" valign="top" rowspan="<%=qtdeExe%>">
								<%=nomeRecurso%>
								<input type="hidden" name="<%=nomeCombo%>" value="<%=recurso.getCodRec()%>">							
							</td>

						<%
								//Obtendo o nome do sistema através da Categoria Econômica.
								String hintSistema = "Sistema Orçamentário-Financeiro";
								if(ieFonte != null && ieFonte.getFonteRecursoFonr() != null && ieFonte.getFonteRecursoFonr().getConfigSisExecFinanCsef() != null){
									hintSistema = ieFonte.getFonteRecursoFonr().getConfigSisExecFinanCsef().getNomeCsef();
								}
						%>
							<td align="center" rowspan="<%=qtdeExe%>">
								<!-- a href="#" onclick="javascript:inserirConta(form, <%=exercicio.getCodExe().toString()%>, <%=recurso.getCodRec().toString()%>)" -->
								<a href="#" onclick="javascript:popupContaOrcamento(form, form.codIett.value, <%=recurso.getCodRec().toString()%>, form.codFonr.value, form.codAba.value)">
									<img border="0" src="../../images/icon_cofre.png" alt="<%=hintSistema%>" title="<%=hintSistema%>"></a>
								</a>
							</td>

						<%
								nomeRecurso = "";
							}
						
						%>
							<td align="center">
								<%=exercicio.getDescricaoExe()%>
							</td>
							<%if(!"".equals(recursoDesc1.trim())) {%>
								<td align="center">
									<input type="text" name="<%=nomeCampoAprovado%>" id="<%=idCampoAprovado%>" value="<%=valorCampoAprovado%>" <%=_disabled%> size="10" onblur="javascript:calcularTotais('<%=recursoDesc1.trim()%>','<%=recursoDesc2.trim()%>')">
								</td>
							<%} %>
							<%if(!"".equals(recursoDesc2.trim())) {%>
								<td align="center">
									<input type="text" name="<%=nomeCampoRevisado%>" id="<%=idCampoRevisado%>" value="<%=valorCampoRevisado%>" <%=_disabled%> size="10" onblur="javascript:calcularTotais('<%=recursoDesc1.trim()%>','<%=recursoDesc2.trim()%>')">
								</td>
							<%} %>
							
						<%
						String valorCampo = "0,00";
						if (!"".equals(valorCampoRevisado) && Double.valueOf(Util.formataNumero(valorCampoRevisado)).doubleValue() > 0){
							valorCampo = Util.formataNumeroDecimalSemMilhar(Double.valueOf(Util.formataNumero(valorCampoRevisado)).doubleValue() - Double.valueOf(Util.formataNumero(valorCampoAprovado)).doubleValue());
						}
						%>
						<%if(!"".equals(recursoDesc3.trim())) {%>
							<td align="center">
								<input type="text" name="diferenca<%=i%>e<%=col%>" value="<%=valorCampo%>" size="10" onkeypress="javascript:formataMoeda(this,event,',');">
							</td>
						<%} %>
							
							<td align="center">
								<!-- Ativo -->
								<input type="checkbox" class="form_check_radio" name="<%=nomeCampoAtivo%>" value="S" <%=Pagina.isChecked(ativoCheck, "S")%>>
							</td>
						</tr>
					<%
						col++;
					}
					%>
					<tr class="titulo">	
						<td align="center" valign="top" colspan="2">&nbsp;</td>
						<td align="center">TOTAL</td>
						<%if(!"".equals(recursoDesc1.trim())) {%>
							<td align="center">
								<input type="text" name="totalAprovRec<%=i%>" value="<%=Util.formataNumeroDecimalSemMilhar(totalAprovRec)%>" disabled size="10">
							</td>
						<%} %>
						<%if(!"".equals(recursoDesc2.trim())) {%>						
							<td align="center">
								<input type="text" name="totalRevRec<%=i%>" value="<%=Util.formataNumeroDecimalSemMilhar(totalRevRec)%>" disabled size="10">
							</td>
						<%} %>
						<%if(!"".equals(recursoDesc3.trim())) {%>						
							<td align="center">
								<input type="text" name="totalDiferenca<%=i%>" value="<%=Util.formataNumeroDecimalSemMilhar(totalRevRec - totalAprovRec)%>" disabled size="10">
							</td>
						<%} %>
						<td align="center">&nbsp;</td>
					</tr>
				<%
					totalAprovadoGeral += totalAprovRec;
					totalRevisadoGeral += totalRevRec;
					
					totalAprovRec = 0;
					totalRevRec = 0;
					
					i++;
					numRecursos++;
				} //Fim While Recursos

				double difTotal = totalRevisadoGeral - totalAprovadoGeral;
				%>
				<tr class="titulo">	
					<td align="center" valign="top" colspan="2">&nbsp;</td>
					<td align="center">TOTAL GERAL</td>
					<%if(!"".equals(recursoDesc1.trim())) {%>
						<td align="center">
							<input type="text" name="totalAprovadoGeral" value="<%=Util.formataNumeroDecimalSemMilhar(totalAprovadoGeral)%>" disabled size="10">
						</td>
					<%} %>
					<%if(!"".equals(recursoDesc2.trim())) {%>					
						<td align="center">
							<input type="text" name="totalRevisadoGeral" value="<%=Util.formataNumeroDecimalSemMilhar(totalRevisadoGeral)%>" disabled size="10">
						</td>
					<%} %>
					<%if(!"".equals(recursoDesc3.trim())) {%>					
						<td align="center">
							<input type="text" name="diferencaTotal" value="<%=Util.formataNumeroDecimalSemMilhar(difTotal)%>" disabled size="10">							
						</td>
					<%} %>
					<td align="center">&nbsp;</td>
				</tr>
			</table>
			<input type="hidden" name="numLinhas" value="<%=i%>">
			<input type="hidden" name="numColunas" value="<%=col%>">

		</td>
	</tr>				
	<!-- ********************* ... Até aqui************************** -->


	
<script language="JavaScript">

document.form.numRecursos.value = "<%=String.valueOf(numRecursos)%>";

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

	<% /* Controla o estado do Menu (aberto ou fechado) */%>
	<%@ include file="../../include/estadoMenu.jsp"%>
