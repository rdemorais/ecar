<%

	String codExercicio = new String();
	String nomeExercicio = new String();
	String codFonteRecurso = new String();
	String nomeFonteRecurso = new String();
	String codRecurso = new String();
	String nomeRecurso = new String();
	
	if (conta.getExercicioExe() != null){
		codExercicio = conta.getExercicioExe().getCodExe().toString();
		nomeExercicio = conta.getExercicioExe().getDescricaoExe();
	}else{
		codExercicio = "";
		nomeExercicio = "";
	}
	if (conta.getFonteRecursoFonr() != null){
		codFonteRecurso = conta.getFonteRecursoFonr().getCodFonr().toString();
		nomeFonteRecurso = conta.getFonteRecursoFonr().getNomeFonr();
	}else{
		codFonteRecurso = "";
		nomeFonteRecurso = "";
	}
	if (conta.getRecursoRec() != null){
		codRecurso = conta.getRecursoRec().getCodRec().toString();
		nomeRecurso = conta.getRecursoRec().getNomeRec();
	}else{
		codRecurso = "";
		nomeRecurso = "";
	}
%> 
	
	<input type=hidden name="codIett" value="<%=itemEstrutura.getCodIett()%>">
	<input type=hidden name="codAba" value="<%=Pagina.getParamStr(request, "codAba")%>">
	<input type=hidden name="cod" value="<%=Pagina.getParamStr(request, "cod")%>">	
	<input type=hidden name="codFonr" value="<%=codFonteRecurso%>">	
	<input type=hidden name="hidAcao" value="">

	<tr>
		<td colspan=2>
			<div class="camposobrigatorios">* Campos de preenchimento obrigat&oacute;rio</div>
		</td>
	</tr>
	<tr> 
		<td class="label">* Estrutura</td>
		<td>
		<%=ecar.dao.ItemEstruturaContaOrcamentoDao.geraHTMLCadastroEstruturaConta(conta.getContaSistemaOrcEfiec(), desabilitarEstrutura, request)%>
		</td>
	</tr>
	<tr>
		<td class="label">* Exerc&iacute;cio</td>
		<td>
		<%
					String selectedExercicio = "";
					if(conta.getExercicioExe() != null )
						selectedExercicio = conta.getExercicioExe().getCodExe().toString();
					if(!"".equals(Pagina.getParamStr(request, "exercicioExe")))
						selectedExercicio = Pagina.getParamStr(request, "exercicioExe");
					String scripts = _disabled + " onchange='atualizaComboExercicio(document.form);'";
		%>		
		<combo:ComboTag 
							nome="exercicioExe"
							objeto="ecar.pojo.ExercicioExe"
							label="descricaoExe"
							value="codExe"
							order="descricaoExe"
							selected="<%=selectedExercicio%>"
							scripts="<%=scripts%>"
		/>
		</td>
	</tr>
	<tr>
		<td class="label">* Recursos</td>
		<td>		 
			<div id="comboFonteRec">
			</div>
		</td>
	</tr>
	<tr>
		<td class="label">* Fonte de Recursos</td>
		<td>		 
			<div id="comboRec">
			</div>
		</td>
	</tr>
	<tr>
		<td class="label">* Acumulado</td>
		<td>
				<input type="radio" class="form_check_radio" name="indAcumuladoEfiec" value="S" <%=Pagina.isChecked(conta.getIndAcumuladoEfiec(), "S")%>  <%=_disabled%>>Sim 
				<input type="radio" class="form_check_radio" name="indAcumuladoEfiec" value="N" <%=Pagina.isChecked(conta.getIndAcumuladoEfiec(), "N")%> <%=_disabled%>>Não
			</td>
	</tr>
	<tr><td class="label">&nbsp;</td></tr>

	<%@ include file="../../include/estadoMenu.jsp"%>
