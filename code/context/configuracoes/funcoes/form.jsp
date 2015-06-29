
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

<jsp:directive.page import="comum.util.Pagina"/><%
/*
 * arquivo comum para as telas de inclusao, alteracao, exclusao e pesquisa 
 * observar a variavel _disabled e setar o objeto corretamente pelo construtor
 * assinalando os campos com os valores iniciais para inclusao
 */ 
%>


<table class="form">
<util:msgObrigatorio obrigatorio="<%=_obrigatorio%>" />
<tr>
	<td class="label"><%=_obrigatorio%> Nome</td>
	<td><input type="text" name="nomeFun" size="42" maxlength="40" value="<%=Pagina.trocaNull(funcao.getNomeFun())%>" <%=_disabled%>></td>
</tr>
<tr>
	<td class="label"><%=_obrigatorio%> Label Padr&atilde;o</td>
	<td><input type="text" name="labelPadraoFun" size="42" maxlength="40" value="<%=Pagina.trocaNull(funcao.getLabelPadraoFun())%>" <%=_disabled%>></td>
</tr>
<tr>
	<td class="label"><%=_obrigatorio%> Link</td>
	<td><textarea name="linkFuncaoFun" rows="2" cols="60" 
		onkeyup="return validaTamanhoLimite(this, 2000);" 
		onkeydown="return validaTamanhoLimite(this, 2000);" 
		onblur="return validaTamanhoLimite(this, 2000);"
		<%=_readOnly%>><%=Pagina.trocaNull(funcao.getLinkFuncaoFun())%></textarea>
	</td>
</tr>
<tr>
		<td class="label"><%=_obrigatorio%> Exclusivo em um Nível da Estrutura</td>
		<td>
		
			<%
			if (ehPesquisa) {
			%>
							
				<input type="radio" class="form_check_radio" name="indExclusivoEstruturaFun" value="<%=Pagina.SIM%>">				
						Sim &nbsp;
				<input type="radio" class="form_check_radio" name="indExclusivoEstruturaFun" value="<%=Pagina.NAO%>">					
						Não &nbsp;
				<input type="button" value="Limpar" onclick="javascript:limparRadioButtons(indExclusivoEstruturaFun)">
				<br>
				
			<%
			} else {
			%>
				<input type="checkbox" class="form_check_radio" name="indExclusivoEstruturaFun" value="<%=Pagina.SIM%>" 
				<%=Pagina.isChecked(funcao.getIndExclusivoEstruturaFun(), Pagina.SIM)%> 
				<%=_pesqdisabled%>>
			<% 
			}
			%>
		
			
		</td>
</tr>
<tr>
		<td class="label"><%=_obrigatorio%> Fun&ccedil;&atilde;o Opcional na Estrutura</td>
		<td>
		
			<%
			if (ehPesquisa) {
			%>
							
				<input type="radio" class="form_check_radio" name="indOpcionalFun" value="<%=Pagina.SIM%>">				
						Sim &nbsp;
				<input type="radio" class="form_check_radio" name="indOpcionalFun" value="<%=Pagina.NAO%>">					
						Não &nbsp;
				<input type="button" value="Limpar" onclick="javascript:limparRadioButtons(indOpcionalFun)">
				<br>
				
			<%
			} else {
			%>
				<input type="checkbox" class="form_check_radio" name="indOpcionalFun" value="<%=Pagina.SIM %>" 
				<%=Pagina.isChecked(funcao.getIndOpcionalFun(), Pagina.SIM)%> 
				<%=_pesqdisabled%>> 
			
			<% 
			}
			%>
		
			
		</td>
</tr>
<tr>
		<td class="label"><%=_obrigatorio%> Ativo</td>
		<td>
			<util:comboSimNaoTag nome="indAtivoFun" valorSelecionado="<%=funcao.getIndAtivoFun()%>" scripts="<%=_disabled%>" opcaoBranco="<%=_comboSimNao%>"/>
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
               writeRichText('rte', "<%=Pagina.trocaNull(funcao.getDocumentacaoFun()).replaceAll("\n", " ").replaceAll("\r", " ").replaceAll("</SCRIPT>","&lt;/SCRIPT&gt;").replaceAll("</script>","&lt;/script&gt;").replaceAll("</script -->","&lt;/script --&gt;").replaceAll("</SCRIPT -->","&lt;/SCRIPT --&gt;")%>", 400, 200, true,<%=strReadOnly%>);
               
			   </script>       
               
               <input type="hidden" name="richText" id="richText" size="2000">
               <input type="hidden" name="documentacaoFun" size="2000">
            </td>
        </tr>
<%
}
%>
		<tr>
			<td class="label">&nbsp;</td>
		</tr>
<%@ include file="/include/estadoMenu.jsp"%>
	