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
		
	}
	for(var i = 0; i < radioLength; i++) {
		radioObj[i].checked = false;
	}
	
	
}
</script>

<table class="form">

<util:msgObrigatorio obrigatorio="<%=_obrigatorio%>" />

<tr>
	<td class="label"><%=_obrigatorio%> Nome</td>
	<%if (ehPesquisa || atributo.getSisGrupoAtributoSga() != null || ehIncluir){ %>
		<td><input type="text" name="nomeAtb" size="42" maxlength="40" value="<%=Pagina.trocaNull(atributo.getNomeAtb())%>" <%=_disabled%>></td>
	<%}else{ %>
		<td><input type="text" name="nomeAtbDisabled" size="42" maxlength="40" value="<%=Pagina.trocaNull(atributo.getNomeAtb())%>" disabled></td>
		<input type="hidden" name="nomeAtb" size="42" maxlength="40" value="<%=Pagina.trocaNull(atributo.getNomeAtb())%>">
	<%}%>
</tr>
<tr>
	<td class="label"><%=_obrigatorio%> Label Padr&atilde;o</td>
	<td><input type="text" name="labelPadraoAtb" size="42" maxlength="40" value="<%=Pagina.trocaNull(atributo.getLabelPadraoAtb())%>" <%=_disabled%>></td>
</tr>

<tr>
	<td class="label"> <%=_obrigatorio%> Função da Estrutura</td>
	<td>
<%
		String filtroFuncao = "indAtivoFun=S";
		filtroFuncao += ";indPossuiAtributos=S";
		if (atributo.getFuncaoFun() != null){
%>			<combo:ComboTag 
					nome="funcaoFun"
					objeto="ecar.pojo.FuncaoFun"
					label="labelPadraoFun"
					value="codFun"
					order="labelPadraoFun"
					filters="<%=filtroFuncao%>"
					selected="<%=atributo.getFuncaoFun().getCodFun().toString()%>"
					scripts="<%=_disabled%>"
				/>
<%
		}else{
%>
			<combo:ComboTag 
					nome="funcaoFun"
					objeto="ecar.pojo.FuncaoFun"
					label="labelPadraoFun"
					value="codFun"
					order="labelPadraoFun"
					filters="<%=filtroFuncao%>"
					scripts="<%=_disabled%>"
				/>
<%
		}
%>		
	</td>
</tr>

<tr>
	<td class="label"> <%=(ehIncluir || atributo.getSisGrupoAtributoSga() != null) ? _obrigatorio: ""%> Grupo de Atributos Livres</td>
	<td>
<%
		String filtro = "indAtivoSga=S;indTabelaUsoSga=T";
		if (atributo.getSisGrupoAtributoSga() != null){
%>			<combo:ComboTag 
					nome="sisGrupoAtributoSga"
					objeto="ecar.pojo.SisGrupoAtributoSga"
					label="descricaoSga"
					value="codSga"
					order="descricaoSga"
					filters="<%=filtro%>"
					selected="<%=atributo.getSisGrupoAtributoSga().getCodSga().toString()%>"
					scripts="<%=_disabled%>"
				/>
<%
		}else if(!ehPesquisa && !ehIncluir){
%>
			<combo:ComboTag 
			nome="sisGrupoAtributoSga"
			objeto="ecar.pojo.SisGrupoAtributoSga"
			label="descricaoSga"
			value="codSga"
			order="descricaoSga"
			filters="<%=filtro%>"
			scripts="<%="disabled"%>"
		/>
<%
		}else{
%>
			<combo:ComboTag 
					nome="sisGrupoAtributoSga"
					objeto="ecar.pojo.SisGrupoAtributoSga"
					label="descricaoSga"
					value="codSga"
					order="descricaoSga"
					filters="<%=filtro%>"
					scripts="<%=_disabled%>"
				/>
<%
		}
%>		
	</td>
</tr>

<tr>
	<td class="label"> Campo que representa o c&oacute;digo da chave estrangeira</td>
	<td><input type="text" name="codFkAtb" size="42" maxlength="40" value="<%=Pagina.trocaNull(atributo.getCodFkAtb())%>" <%=_disabled%>></td>
</tr>
<tr>
	<td class="label"> Campo que representa a descri&ccedil;&atilde;o da chave estrangeira</td>
	<td><input type="text" name="nomeFkAtb" size="42" maxlength="40" value="<%=Pagina.trocaNull(atributo.getNomeFkAtb())%>" <%=_disabled%>></td>
</tr>

<tr>
		<td class="label">Atributo &uacute;nico na estrutura</td>
		<td>
		
			<%
			if (ehPesquisa) {
			%>
							
				<input type="radio" class="form_check_radio" name="indExclusivoEstruturaAtb" value="<%=Pagina.SIM%>">				
						Sim &nbsp;
				<input type="radio" class="form_check_radio" name="indExclusivoEstruturaAtb" value="<%=Pagina.NAO%>">					
						Não &nbsp;
				<input type="button" value="Limpar" onclick="javascript:limparRadioButtons(indExclusivoEstruturaAtb)">
				<br>
				
			<%
			} else {
			%>
				<input type="checkbox" class="form_check_radio" name="indExclusivoEstruturaAtb" value="<%=Pagina.SIM%>" 
				<%=Pagina.isChecked(atributo.getIndExclusivoEstruturaAtb(), Pagina.SIM)%> 
				<%=_pesqdisabled%>>
			<% 
			}
			%>
					
		</td>
</tr>
<tr>
		<td class="label">Atributo opcional</td>
		<td>
		
			<%
			if (ehPesquisa) {
			%>
							
				<input type="radio" class="form_check_radio" name="indOpcionalAtb" value="<%=Pagina.SIM%>">				
						Sim &nbsp;
				<input type="radio" class="form_check_radio" name="indOpcionalAtb" value="<%=Pagina.NAO%>">					
						Não &nbsp;
				<input type="button" value="Limpar" onclick="javascript:limparRadioButtons(indOpcionalAtb)">
				<br>
				
			<%
			} else {
			%>
				<input type="checkbox" class="form_check_radio" name="indOpcionalAtb" value="<%=Pagina.SIM%>" 
				<%=Pagina.isChecked(atributo.getIndOpcionalAtb(), Pagina.SIM)%> 
				<%=_pesqdisabled%>>
			<% 
			}
			%>
					
		</td>
</tr>
<tr>
		<td class="label"><%=_obrigatorio%> Ativo</td>
		<td>
				<util:comboSimNaoTag nome="indAtivoAtb" valorSelecionado="<%=atributo.getIndAtivoAtb()%>" scripts="<%=_disabled%>" opcaoBranco="<%=_comboSimNao%>"/>
			</td>
</tr>
<%if (blVisualizaDesc == true){ %>
        <tr>
            <td class="label">Documenta&ccedil;&atilde;o</td>
            <td>
               <script language="JavaScript" type="text/javascript">
               //Usage: initRTE(imagesPath, includesPath, cssFile, genXHTML)
				initRTE("<%=_pathEcar%>/images/rte/", "<%=_pathEcar%>", "", false);
               </script>
               
               <script language="JavaScript" type="text/javascript">
               //Usage: writeRichText(fieldname, html, width, height, buttons, readOnly)
               //writeRichText('rte', 'enter body text here', 400, 200, true, false);                          
               writeRichText('rte', "<%=Pagina.trocaNull(atributo.getDocumentacaoAtb()).replaceAll("\n", " ").replaceAll("\r", " ").replaceAll("</SCRIPT>","&lt;/SCRIPT&gt;").replaceAll("</script>","&lt;/script&gt;").replaceAll("</script -->","&lt;/script --&gt;").replaceAll("</SCRIPT -->","&lt;/SCRIPT --&gt;")%>", 400, 200, true,<%=strReadOnly%>);
               
               </script>       
               
               <input type="hidden" name="richText" id="richText" size="2000">
               <input type="hidden" name="documentacaoAtb" size="2000">
            </td>
        </tr>
<%
}
%>
<%@ include file="/include/estadoMenu.jsp"%>
</table>