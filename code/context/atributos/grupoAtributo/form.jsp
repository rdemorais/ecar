<%
/*
 * arquivo comum para as telas de inclusao, alteracao, exclusao e pesquisa 
 * observar a variavel _disabled e setar o objeto corretamente pelo construtor
 * assinalando os campos com os valores iniciais para inclusao
 */ 
%>

<script language="javascript">
function limparRadioButtons(radioObj){
		
	var radioLength = radioObj.length;
	if(radioLength == undefined) {
		radioObj.checked = false;
		if (radioObj.name == 'indCadUsuSga'){
			document.form.sisTipoExibicGrupoCadUsuSteg.selectedIndex = 0;
			habilitarTipoExibCadUsu(true);
		}
	}
	for(var i = 0; i < radioLength; i++) {
		radioObj[i].checked = false;
		if (radioObj[i].name == 'indCadUsuSga'){
			document.form.sisTipoExibicGrupoCadUsuSteg.selectedIndex = 0;
			document.form.sisTipoExibicGrupoCadUsuSteg.disabled = true;
			habilitarTipoExibCadUsu(true);
		}
	}	
}

function habilitarTipoExibCadUsu(desabilitar){
	if (document.form.sisTipoExibicGrupoCadUsuSteg != null){
	
		if (desabilitar == true) {
			document.form.sisTipoExibicGrupoCadUsuSteg.selectedIndex = 0;
		}
		document.form.sisTipoExibicGrupoCadUsuSteg.disabled = desabilitar;
	}

	document.form.indCadSiteSga[0].disabled = desabilitar;
	document.form.indCadSiteSga[0].checked = false;
	document.form.indCadSiteSga[1].disabled = desabilitar;
	document.form.indCadSiteSga[1].checked = false;
}

//Mantis: 8196
function validaOpcao (){

	if (document.form.sisTipoExibicGrupoCadUsuSteg != null){
		//Situa��o: No campo Tipo de Exibi��o Cadastro de Usu�rio foi selecionado a op��o entrada de texto com valida��o e no Campo Tipo de Exibi��o foi selecionada uma op��o diferente de: entrada de texto com valida��o  (N�o � permitido)
		if (document.form.sisTipoExibicGrupoCadUsuSteg.selectedIndex == 3 && document.form.sisTipoExibicGrupoSteg.selectedIndex != 3) { //campo Tipo de Exibi��o Cadastro de Usu�rio
				alert ("Informe Entrada de Texto com Valida��o na op��o Tipo de Exibi��o.");
				document.form.sisTipoExibicGrupoCadUsuSteg.selectedIndex = 0;
		}
	}
}

</script>

<input type="hidden" name="indTabelaUso" value="<%=indTabelaUso%>">


<util:msgObrigatorio obrigatorio="<%=_obrigatorio%>" />
	<tr>
		<td class="label"><%=_obrigatorio%> Descri&ccedil;&atilde;o</td>
		<td><input type="text" name="descricaoSga" size="42" maxlength="40" value="<%=Pagina.trocaNull(grupoAtributo.getDescricaoSga())%>" <%=_disabled%>></td>
	</tr>
	<tr>
		<td class="label"><%=_obrigatorio%> Tipo de Exibi&ccedil;&atilde;o</td>
		<td>
<%
		if (grupoAtributo.getSisTipoExibicGrupoSteg() != null){
%>		
			<combo:ComboTag 
					nome="sisTipoExibicGrupoSteg"
					objeto="ecar.pojo.SisTipoExibicGrupoSteg"
					label="descricaoSteg"
					value="codSteg"
					order="descricaoSteg"
					filters="indAtivoSteg=S"
					selected="<%=grupoAtributo.getSisTipoExibicGrupoSteg().getCodSteg().toString()%>"
					scripts="<%=_disabled%>"
					valida="true"
				/>
<%
		}else{
%>
			<combo:ComboTag 
					nome="sisTipoExibicGrupoSteg"
					objeto="ecar.pojo.SisTipoExibicGrupoSteg"
					label="descricaoSteg"
					value="codSteg"
					order="descricaoSteg"
					filters="indAtivoSteg=S"
					scripts="<%=_disabled%>"
					valida="true"
				/>
<%
		}
%>
		</td>
	</tr>
	<tr>
		<td class="label"><%=_obrigatorio%> Ordena��o</td>
		<td>
<%
		if (grupoAtributo.getSisTipoOrdenacaoSto() != null){
%>		
			<combo:ComboTag
					nome="sisTipoOrdenacaoSto"
					objeto="ecar.pojo.SisTipoOrdenacaoSto"
					label="descricaoSto"
					value="codSto"
					order="descricaoSto"
					filters="indAtivoSto=S"
					selected="<%=grupoAtributo.getSisTipoOrdenacaoSto().getCodSto().toString()%>"
					scripts="<%=_disabled%>"
				/>
<%
		}else{
%>
			<combo:ComboTag 
					nome="sisTipoOrdenacaoSto"
					objeto="ecar.pojo.SisTipoOrdenacaoSto"
					label="descricaoSto"
					value="codSto"
					order="descricaoSto"
					filters="indAtivoSto=S"
					scripts="<%=_disabled%>"
				/>
<%
		}
%>
		</td>
	</tr>
	
	<tr>
		<td class="label"><%=_obrigatorio%> Obrigat&oacute;rio</td>
		<td>
			<input type="radio" class="form_check_radio" name="indObrigatorioSga" value="S" <%=Pagina.isChecked(grupoAtributo.getIndObrigatorioSga(),"S")%> <%=_disabled%>> Sim
			<input type="radio" class="form_check_radio" name="indObrigatorioSga" value="N" <%=Pagina.isChecked(grupoAtributo.getIndObrigatorioSga(),"N")%> <%=_disabled%>> N�o
			
			<% 
			// Se for pesquisa, o bot�o limpar aparece
			if (ehPesquisa) {%>
				<input type="button" value="Limpar" onclick="javascript:limparRadioButtons(indObrigatorioSga)">
			<%}%>
			
			<br>
		</td>
	</tr>
	<tr>
		<td class="label"><%=_obrigatorio%> Seq. de Apresenta&ccedil;&atilde;o</td>
		<td><input type="text" name="seqApresentacaoSga" size="5" maxlength="3" value="<%=Pagina.trocaNull(grupoAtributo.getSeqApresentacaoSga())%>" <%=_disabled%>></td>
	</tr>
	<% //verifica se o atributo a ser inclu�do � de SISTEMA (indTabelaUso="S")
	   //se for, n�o permite selecionar o valor do indAtivo, sendo padr�o "S"
	if (!"S".equals(indTabelaUso)) {%>
	<tr>
		<td class="label"><%=_obrigatorio%> Ativo</td>
		<td>
				<util:comboSimNaoTag nome="indAtivoSga" valorSelecionado="<%=grupoAtributo.getIndAtivoSga()%>" scripts="<%=_disabled%>" opcaoBranco="<%=_comboSimNao%>"/>
		</td>
	</tr>
	<%} else {%>
		<input type="hidden"  name="indAtivoSga" value="S">
	<%}%>
	<%//verifica se o atributo a ser inclu�do � de SISTEMA (indTabelaUso="S")
	   //se for, permite selecionar se o atributo tamb�m deve aparecer no cadastro de usu�rios
	if ("S".equals(indTabelaUso) || "D".equals(indTabelaUso)) {
	%>
		<tr>
			<td class="label"><%=_obrigatorio%> Cadastro de Usu�rio</td>
			<td>
				<input type="radio" class="form_check_radio" name="indCadUsuSga" value="S" onchange="javascript:habilitarTipoExibCadUsu(false)" <%=Pagina.isChecked(grupoAtributo.getIndCadUsuSga(),"S")%> <%=_disabled%>> Sim
				<input type="radio" class="form_check_radio" name="indCadUsuSga" value="N" onchange="javascript:habilitarTipoExibCadUsu(true)" <%=Pagina.isChecked(grupoAtributo.getIndCadUsuSga(),"N")%> <%=_disabled%>> N�o
				<% 
				// Se for pesquisa, o bot�o limpar aparece
				if (ehPesquisa) {%>
					<input type="button" value="Limpar" onclick="javascript:limparRadioButtons(indCadUsuSga);habilitarTipoExibCadUsu(false);">
				<%}%>
			<br>
			</td>
		</tr>
		
		<tr>
		<td class="label">Tipo de Exibi&ccedil;&atilde;o Cadastro de Usu�rio</td>
		<td>
<%
		if (grupoAtributo.getSisTipoExibicGrupoCadUsuSteg() != null){
%>			
			<combo:ComboTag 
					nome="sisTipoExibicGrupoCadUsuSteg"
					objeto="ecar.pojo.SisTipoExibicGrupoSteg"
					label="descricaoSteg"
					value="codSteg"
					order="descricaoSteg"
					filters="indAtivoSteg=S"
					selected="<%=grupoAtributo.getSisTipoExibicGrupoCadUsuSteg().getCodSteg().toString()%>"
					scripts="<%=_disabled.equals("disabled")?_disabled:grupoAtributo.getIndCadUsuSga() != null && grupoAtributo.getIndCadUsuSga().equals("S")?"":"disabled"%>"
					valida="true"
				/>
<%
		}else{
%>
			<combo:ComboTag 
					nome="sisTipoExibicGrupoCadUsuSteg"
					objeto="ecar.pojo.SisTipoExibicGrupoSteg"
					label="descricaoSteg"
					value="codSteg"
					order="descricaoSteg"
					filters="indAtivoSteg=S"
					scripts="<%=_disabled.equals("disabled")?_disabled:grupoAtributo.getIndCadUsuSga() != null && grupoAtributo.getIndCadUsuSga().equals("S")?"":"disabled"%>"
					valida="true"
				/>
<%
		}
%>
		</td>
	</tr>
	
	   <tr>
			<td class="label"> Cadastro do Site</td>
			<td>
				<input type="radio" class="form_check_radio" name="indCadSiteSga" id="indCadSiteSga" value="S"  <%=Pagina.isChecked(grupoAtributo.getIndCadSiteSga(),"S")%> <%=_disabled.equals("disabled")?_disabled:grupoAtributo.getIndCadUsuSga() != null && grupoAtributo.getIndCadUsuSga().equals("S")?"":"disabled"%>> Sim
				<input type="radio" class="form_check_radio" name="indCadSiteSga" id="indCadSiteSga" value="N"  <%=Pagina.isChecked(grupoAtributo.getIndCadSiteSga(),"N")%> <%=_disabled.equals("disabled")?_disabled:grupoAtributo.getIndCadUsuSga() != null && grupoAtributo.getIndCadUsuSga().equals("S")?"":"disabled"%>> N�o
				<% 
				// Se for pesquisa, o bot�o limpar aparece
				if (ehPesquisa) {%>
					<input type="button" value="Limpar" onclick="javascript:limparRadioButtons(indCadSiteSga);habilitarTipoExibCadUsu(false);">
				<%}%>
			<br>
			</td>
		</tr>
	
	<% 
	} 
	else 
	{	
		if ("U".equals(indTabelaUso)) {
		%>
			<tr>
				<td class="label"><%=_obrigatorio%> Cadastro de Usu�rio</td>
				<td>
					<input type="radio" class="form_check_radio" name="indCadUsuSgaChk" value="S" checked disabled> Sim
					<input type="radio" class="form_check_radio" name="indCadUsuSgaChk" value="N" disabled> N�o
			    	<input type="hidden" name="indCadUsuSga" value="S">
					
				</td>
			</tr>
			
			<tr>
				<td class="label"><%=_obrigatorio%> Tipo de Exibi&ccedil;&atilde;o Cadastro de Usu�rio</td>
				<td>
		<%
				if (grupoAtributo.getSisTipoExibicGrupoCadUsuSteg() != null){
		%>			
					<combo:ComboTag 
							nome="sisTipoExibicGrupoCadUsuSteg"
							objeto="ecar.pojo.SisTipoExibicGrupoSteg"
							label="descricaoSteg"
							value="codSteg"
							order="descricaoSteg"
							filters="indAtivoSteg=S"
							selected="<%=grupoAtributo.getSisTipoExibicGrupoCadUsuSteg().getCodSteg().toString()%>"
							scripts="<%=_disabled.equals("disabled")?_disabled:""%>"
							valida="true"
						/>
		<%
				}else{
		%>
					<combo:ComboTag 
							nome="sisTipoExibicGrupoCadUsuSteg"
							objeto="ecar.pojo.SisTipoExibicGrupoSteg"
							label="descricaoSteg"
							value="codSteg"
							order="descricaoSteg"
							filters="indAtivoSteg=S"
							scripts="<%=_disabled.equals("disabled")?_disabled:""%>"
							valida="true"
						/>
		<%
				}
		%>
				</td>
			</tr>
			
				   <tr>
			<td class="label"> Cadastro do Site</td>
			<td>
				<input type="radio" class="form_check_radio" name="indCadSiteSga" id="indCadSiteSga" value="S" <%=Pagina.isChecked(grupoAtributo.getIndCadSiteSga(),"S")%> <%=_disabled.equals("disabled")?_disabled:""%>> Sim
				<input type="radio" class="form_check_radio" name="indCadSiteSga" id="indCadSiteSga" value="N" <%=Pagina.isChecked(grupoAtributo.getIndCadSiteSga(),"N")%>  <%=_disabled.equals("disabled")?_disabled:""%>> N�o
				<% 
				// Se for pesquisa, o bot�o limpar aparece
				if (ehPesquisa) {%>
					<input type="button" value="Limpar" onclick="javascript:limparRadioButtons(indCadSiteSga);habilitarTipoExibCadUsu(false);">
				<%}%>
			<br>
			</td>
		</tr>
			
		<%
		} else {
		%>
	    	<input type="hidden" name="indCadUsuSga" value="N">
		<%
		}
	}
		%>
	<tr>
		<td class="label"> Descri��o das Informa��es Complementares</td>
		<td>
			<textarea name="descInfCompSga" cols="50" rows="4" 
				onkeyup="return validaTamanhoLimite(this, 200);"
				onkeydown="return validaTamanhoLimite(this, 200);"
				onblur="return validaTamanhoLimite(this, 200);" <%=_readOnly%>><%=Pagina.trocaNull(grupoAtributo.getDescInfoComplementar())%></textarea>					
		</td>
	</tr>
	<tr><td class="label">&nbsp;</td></tr>

	<%@ include file="../../include/estadoMenu.jsp"%>