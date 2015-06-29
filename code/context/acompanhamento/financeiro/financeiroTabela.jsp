<%@ page import="ecar.pojo.EfItemEstPrevisaoEfiep" %>
<%@ page import="ecar.pojo.RecursoRec" %>
<%@ page import="java.util.Collections" %>
<%@ page import="java.util.Comparator" %>
<%@ page import="java.util.List" %>
<%@ page import="ecar.pojo.ExercicioExe" %>
<script language="javascript" src="<%=_pathEcar%>/js/destaqueLinha.js"></script>
<div id="subconteudo">
<table width="100%" border="0" cellpadding="0" cellspacing="0">
	<tr class="linha_subtitulo">
		<td>&nbsp;Recurso&nbsp;</td>
		<td>&nbsp;Fonte&nbsp;</td>
<%
	ItemEstruturaRealizadoDao itemEstRealizadoDao = new ItemEstruturaRealizadoDao(request);

	String descFinanceiro[] = new String[3];
	boolean mostrarDescFinanceiro[] = new boolean[3];
	descFinanceiro[0] = configura.getRecursoDescValor1Cfg();
	descFinanceiro[1] = configura.getRecursoDescValor2Cfg();
	descFinanceiro[2] = configura.getRecursoDescValor3Cfg();
	
	int _colspan = 2;

	for(int i = 0; i < 3; i++){
		mostrarDescFinanceiro[i] = itemEstRealizadoDao.getVerificarMostrarRecursoByPosicaoCfg(i);
		if(mostrarDescFinanceiro[i]){
			out.println("<td align=center>&nbsp;" + descFinanceiro[i] + "&nbsp;</td>");
			_colspan++;
		}
	}
	
	ItemEstruturaPrevisaoDao itemEstPrevDao = new ItemEstruturaPrevisaoDao(request);
	List listaExercicios = itemEstPrevDao.getListaExerciciosItemEstruturaPrevisao(ari.getItemEstruturaIett());
	
	Iterator it = listaExercicios.iterator();
	
	EfItemEstPrevisaoEfiep itemEstPrev = new EfItemEstPrevisaoEfiep();
	
	//String fonte = "";
	FonteRecursoFonr fonte = null;
	
	ItemEstruturaContaOrcamentoDao itemEstContaOrcDao = new ItemEstruturaContaOrcamentoDao(request);

	boolean mostrarValores[] = new boolean[6];

	String descricoes[] = new String[6];
	descricoes[0] = configura.getFinanceiroDescValor1Cfg();
	descricoes[1] = configura.getFinanceiroDescValor2Cfg();
	descricoes[2] = configura.getFinanceiroDescValor3Cfg();
	descricoes[3] = configura.getFinanceiroDescValor4Cfg();
	descricoes[4] = configura.getFinanceiroDescValor5Cfg();
	descricoes[5] = configura.getFinanceiroDescValor6Cfg();
	
	for(int i = 0; i < 6; i++){
		mostrarValores[i] = itemEstRealizadoDao.getVerificarMostrarValorByPosicaoCfg(i);
		if(mostrarValores[i]){
			out.println("<td align=center>&nbsp;" + descricoes[i] + "&nbsp;</td>");
			_colspan++;
		}
	}
%>
	</tr>
<%
	
	if (it.hasNext()){
		double totFonAprovado = 0, totFonRevisado = 0, totFonAprovadoExercicio = 0, totFonRevisadoExercicio = 0;
		double totGerAprovado = 0, totGerRevisado = 0;
		double[] totFonValor = new double[6];
		double[] totGerValor = new double[6];
		double[] totExercicioValor = new double[6];
		
		/*Inicializar os valores...*/
		for(int i = 0; i < 6; i++){
			totFonValor[i] = 0;
			totGerValor[i] = 0;
			totExercicioValor[i] = 0;
		}
		
		boolean mostrouAlgumValor = false;
		
		Iterator itExerc = listaExercicios.iterator();
		ExercicioExe exeApresentado = null;
		ExercicioExe exeAnterior = null;
		boolean primeiroExe = true;
		while(itExerc.hasNext()){
			ExercicioExe exercicio = (ExercicioExe) itExerc.next();
			
			if(primeiroExe){
				if(!exercicio.equals(exeApresentado)){
					%>
						<tr class="linha_subtitulo2_sem_bg bgCinza">
							<td>&nbsp;<b><%=exercicio.getDescricaoExe()%></b>&nbsp;</td>
							<td>&nbsp;</td>
							<%
							for(int i = 0; i < 3; i++){
								if(mostrarDescFinanceiro[i]){
									if(i == 0)
										out.println("<td align=right>&nbsp;</td>");
									if(i == 1)
										out.println("<td align=right>&nbsp;</td>");
								}
							}
							
							for(int i = 0; i < 6; i++){
								if(mostrarValores[i])
									out.println("<td align=right>&nbsp;</td>");
							}
							%>
						</tr>
					<%
							exeApresentado = exercicio;
				}
				primeiroExe = false;
				
				totFonAprovadoExercicio = 0;
				totFonRevisadoExercicio = 0;
				
				for(int i = 0; i < 6; i++){
					totExercicioValor[i] = 0;
				}
				
				exeAnterior = exercicio;
			}
			List lista = itemEstPrevDao.getListaItemEstruturaPrevisao(ari.getItemEstruturaIett(), exercicio);		
			it = lista.iterator();
			int cont = 0;
			String cor = new String();
		while(it.hasNext()){
			itemEstPrev = (EfItemEstPrevisaoEfiep) it.next();
			
			
			/* ler EfItemEstContaEfiec */
			EfItemEstContaEfiec itemEstConta = 
					itemEstContaOrcDao.getItemEstruturaConta(
						ari.getItemEstruturaIett(),
						exercicio,
						itemEstPrev.getFonteRecursoFonr(),
						itemEstPrev.getRecursoRec());
			
			/* verificar valores em Efier */
			Double[] valores = itemEstRealizadoDao.getSomaItemEstruturaRealizado(
					itemEstConta,
					exercicio);
			
			boolean deveMostrar = false;
			
			if(itemEstPrev != null){
				if((itemEstPrev.getValorAprovadoEfiep() != null && itemEstPrev.getValorAprovadoEfiep().doubleValue() > 0)
				|| (itemEstPrev.getValorRevisadoEfiep() != null && itemEstPrev.getValorRevisadoEfiep().doubleValue() > 0)){
					deveMostrar = true;
					mostrouAlgumValor = true;
				}
				
				if(!deveMostrar){
					for(int i = 0; i < 6; i++){
						if(valores[i].doubleValue() != 0){
							deveMostrar = true;
							mostrouAlgumValor = true;
						}
					}
				}
			}
			
			if(!deveMostrar){
				//FIXME: REVER
				//continue;
			}
			
//			if(!fonte.equals(itemEstPrev.getFonteRecursoFonr())){
//				if(!"".equals(fonte)){
			if(fonte != null && !fonte.equals(itemEstPrev.getFonteRecursoFonr())){
				if(!"".equals(fonte.getNomeFonr())){

%>
					<!-- 
					<tr class="linha_subtitulo">
						<td>&nbsp;Total <%=fonte.getNomeFonr()%>&nbsp;</td>
						<td>&nbsp;</td>
						<%
						for(int i = 0; i < 3; i++){
							if(mostrarDescFinanceiro[i]){
								if(i == 0)
									out.println("<td align=right>&nbsp;" + Util.formataMoeda(totFonAprovado) + "</td>");
								if(i == 1)
									out.println("<td align=right>&nbsp;" + Util.formataMoeda(totFonRevisado) + "</td>");
							}
						}
						
						for(int i = 0; i < 6; i++){
							if(mostrarValores[i])
								out.println("<td align=right>&nbsp;" + Util.formataMoeda(totFonValor[i]) + "</td>");
						}
						%>
					</tr>
					 -->
		<%
		
				if(!exercicio.equals(exeApresentado)){
					%>
						<tr class="cor_sim_financeiro" onmouseover="javascript:destacaLinha(this,'over','')" onmouseout="javascript: destacaLinha(this,'out','cor_sim_financeiro')" onClick="javascript: destacaLinha(this,'click','cor_sim_financeiro')" onmousemove="javascript: destacaLinha(this,'out','cor_sim_financeiro')">
							<td colspan="2">&nbsp;Total <%=exeAnterior.getDescricaoExe()%>&nbsp;</td>
							<!--  <td>&nbsp;</td> -->
							<%
							for(int i = 0; i < 3; i++){
								if(mostrarDescFinanceiro[i]){
									if(i == 0)
										out.println("<td align=right>&nbsp;" + Util.formataMoeda(totFonAprovadoExercicio) + "</td>");
									if(i == 1)
										out.println("<td align=right>&nbsp;" + Util.formataMoeda(totFonRevisadoExercicio) + "</td>");
								}
							}
							
							for(int i = 0; i < 6; i++){
								if(mostrarValores[i])
									out.println("<td align=right>&nbsp;" + Util.formataMoeda(totExercicioValor[i]) + "</td>");
							}
							
							for(int i = 0; i < 6; i++){
								totExercicioValor[i] = 0;
							}
							
							totFonAprovadoExercicio = 0;
							totFonRevisadoExercicio = 0;
							%>
						</tr>

						<tr><td>&nbsp;</td></tr>
						<tr class="linha_subtitulo2_sem_bg bgCinza">
							<td>&nbsp;<b><%=exercicio.getDescricaoExe()%></b>&nbsp;</td>
							<td>&nbsp;</td>
							<%
							for(int i = 0; i < 3; i++){
								if(mostrarDescFinanceiro[i]){
									if(i == 0)
										out.println("<td align=right>&nbsp;</td>");
									if(i == 1)
										out.println("<td align=right>&nbsp;</td>");
								}
							}
							
							for(int i = 0; i < 6; i++){
								if(mostrarValores[i])
									out.println("<td align=right>&nbsp;</td>");
							}
							%>
						</tr>
					<%
							exeApresentado = exercicio;
					}
					exeAnterior = exeApresentado;
		
					
					/* somar nos valores do total geral */
					totGerAprovado = totGerAprovado + totFonAprovado;
					totGerRevisado = totGerRevisado + totFonRevisado;
					
					for(int i = 0; i < 6; i++){
						totGerValor[i] = totGerValor[i] + totFonValor[i];
					}
					

					/* zerar os valores do total da fonte */
					totFonAprovado = 0;
					totFonRevisado = 0;
					
					for(int i = 0; i < 6; i++){
						totFonValor[i] = 0;
					}
				}
				
%>
				<tr class="cor_sim_financeiro_destacaLinha" onmouseover="javascript:destacaLinha(this,'over','')" onmouseout="javascript: destacaLinha(this,'out','cor_sim_financeiro_destacaLinha')" onClick="javascript: destacaLinha(this,'click','cor_sim_financeiro_destacaLinha')" onmousemove="javascript: destacaLinha(this,'out','cor_sim_financeiro_destacaLinha')">
					<td colspan="<%=_colspan%>">&nbsp;<%=itemEstPrev.getFonteRecursoFonr().getNomeFonr()%>&nbsp;</td>
				</tr>
<%
			cont = 0;
			}
			else if (fonte == null){
%>
			<tr class="cor_sim_financeiro_destacaLinha" onmouseover="javascript:destacaLinha(this,'over','')" onmouseout="javascript: destacaLinha(this,'out','cor_sim_financeiro_destacaLinha')" onClick="javascript: destacaLinha(this,'click','cor_sim_financeiro_destacaLinha')" onmousemove="javascript: destacaLinha(this,'out','cor_sim_financeiro_destacaLinha')">
				<td colspan="<%=_colspan%>">&nbsp;<%=itemEstPrev.getFonteRecursoFonr().getNomeFonr()%>&nbsp;</td>
			</tr>
<%
			cont = 0;
			}

			//fonte = itemEstPrev.getFonteRecursoFonr().getNomeFonr();
			fonte = itemEstPrev.getFonteRecursoFonr();
			
			/* somar nos valores do total da fonte */
			if(itemEstPrev.getValorAprovadoEfiep() != null){
				totFonAprovado = totFonAprovado + itemEstPrev.getValorAprovadoEfiep().doubleValue();
				totFonAprovadoExercicio = totFonAprovadoExercicio + itemEstPrev.getValorAprovadoEfiep().doubleValue();
			}
			if(itemEstPrev.getValorRevisadoEfiep() != null){
				totFonRevisado = totFonRevisado + itemEstPrev.getValorRevisadoEfiep().doubleValue();
				totFonRevisadoExercicio = totFonRevisadoExercicio + itemEstPrev.getValorRevisadoEfiep().doubleValue();
			}
			
			boolean possuiAlgumValorParaMostrar = false;

			for(int i = 0; i < 6; i++){
				totFonValor[i] = totFonValor[i] + valores[i].doubleValue();
				totExercicioValor[i] = totExercicioValor[i] + valores[i].doubleValue();
				
				if(valores[i].doubleValue() != 0){
					possuiAlgumValorParaMostrar = true;
				}
			}
			
			if((itemEstPrev.getValorAprovadoEfiep() != null && itemEstPrev.getValorAprovadoEfiep().doubleValue() != 0)
			|| (itemEstPrev.getValorRevisadoEfiep() != null && itemEstPrev.getValorRevisadoEfiep().doubleValue() != 0)){
				possuiAlgumValorParaMostrar = true;
			}
			
			if(possuiAlgumValorParaMostrar){
				//mostrouAlgumValor = true;
				if (cont % 2 == 0){
					cor = "cor_nao";
				} else {
					cor = "cor_sim"; 
				}
				cont++;
%>






			<tr class="<%=cor%>" onmouseover="javascript:destacaLinha(this,'over','')" onmouseout="javascript: destacaLinha(this,'out','<%=cor%>')" onClick="javascript: destacaLinha(this,'click','<%=cor%>')" class="linha_subtitulo2">
				<td>&nbsp;</td>
				<td>&nbsp;<%=itemEstPrev.getRecursoRec().getNomeRec()%></td>
				<%
				for(int i = 0; i < 3; i++){
					if(mostrarDescFinanceiro[i]){
						if(i == 0)
							out.println("<td align=right>&nbsp;" + Pagina.trocaNullMoeda(itemEstPrev.getValorAprovadoEfiep()) + "</td>");
						if(i == 1)
							out.println("<td align=right>&nbsp;" + Pagina.trocaNullMoeda(itemEstPrev.getValorRevisadoEfiep()) + "</td>");
					}
				}
				
				for(int i = 0; i < 6; i++){
					if(mostrarValores[i])
						out.println("<td align=right>&nbsp;" + Util.formataMoeda(valores[i].doubleValue()) + "</td>");
				}
				%>
			</tr>
<%
			}
		}

		}

		/* somar nos valores do total geral (a ultima fonte ainda nao foi somada) */
		totGerAprovado = totGerAprovado + totFonAprovado;
		totGerRevisado = totGerRevisado + totFonRevisado;
		
		for(int i = 0; i < 6; i++){
			totGerValor[i] = totGerValor[i] + totFonValor[i];
		}
		
		if(mostrouAlgumValor){
%>	
		<!-- 
		<tr class="linha_subtitulo">
			<td>&nbsp;Total <%=fonte.getNomeFonr()%>&nbsp;</td>
			<td>&nbsp;</td>
			<%
			for(int i = 0; i < 3; i++){
				if(mostrarDescFinanceiro[i]){
					if(i == 0)
						out.println("<td align=right>&nbsp;" + Util.formataMoeda(totFonAprovado) + "</td>");
					if(i == 1)
						out.println("<td align=right>&nbsp;" + Util.formataMoeda(totFonRevisado) + "</td>");
				}
			}
			
			for(int i = 0; i < 6; i++){
				if(mostrarValores[i])
					out.println("<td align=right>&nbsp;" + Util.formataMoeda(totFonValor[i]) + "</td>");
			}
			%>
		</tr>
		 -->
		<tr class="cor_sim_financeiro" onmouseover="javascript:destacaLinha(this,'over','')" onmouseout="javascript: destacaLinha(this,'out','cor_sim_financeiro')" onClick="javascript: destacaLinha(this,'click','cor_sim_financeiro')" onmousemove="javascript: destacaLinha(this,'out','cor_sim_financeiro')">
			<td colspan="2">&nbsp;Total <%=exeAnterior.getDescricaoExe()%>&nbsp;</td>
			<!--  <td>&nbsp;</td> -->
			<%
			for(int i = 0; i < 3; i++){
				if(mostrarDescFinanceiro[i]){
					if(i == 0)
						out.println("<td align=right>&nbsp;" + Util.formataMoeda(totFonAprovadoExercicio) + "</td>");
					if(i == 1)
						out.println("<td align=right>&nbsp;" + Util.formataMoeda(totFonRevisadoExercicio) + "</td>");
				}
			}
			
			for(int i = 0; i < 6; i++){
				if(mostrarValores[i])
					out.println("<td align=right>&nbsp;" + Util.formataMoeda(totExercicioValor[i]) + "</td>");
			}
			%>
		</tr>
		
		<tr><td>&nbsp;</td></tr>
		<tr class="cor_sim_financeiro" onmouseover="javascript:destacaLinha(this,'over','')" onmouseout="javascript: destacaLinha(this,'out','cor_sim_financeiro')" onClick="javascript: destacaLinha(this,'click','cor_sim_financeiro')" onmousemove="javascript: destacaLinha(this,'out','cor_sim_financeiro')">
			<td colspan="2">&nbsp;Total Geral&nbsp;</td>
			<!--  <td>&nbsp;</td> -->
			<%
			for(int i = 0; i < 3; i++){
				if(mostrarDescFinanceiro[i]){
					if(i == 0)
						out.println("<td align=right>&nbsp;" + Util.formataMoeda(totGerAprovado) + "</td>");
					if(i == 1)
						out.println("<td align=right>&nbsp;" + Util.formataMoeda(totGerRevisado) + "</td>");
				}
			}
			
			for(int i = 0; i < 6; i++){
				if(mostrarValores[i])
					out.println("<td align=right>&nbsp;" + Util.formataMoeda(totGerValor[i]) + "</td>");
			}
			%>
		</tr>
<%
		}
		else {
%>
			<tr>
				<td colspan=<%=_colspan%> class="subtitulo">&nbsp;<b>Nenhum valor informado</b>&nbsp;</td>
			</tr>
<%
		}
	} else {
%>
		<tr>
			<td colspan=<%=_colspan%> class="subtitulo">&nbsp;<b>Nenhum dado cadastrado</b>&nbsp;</td>
		</tr>
<%
	}
%>
</table>
</div>
