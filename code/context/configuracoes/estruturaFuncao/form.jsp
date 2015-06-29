
<%@page import="ecar.pojo.EstruturaFuncaoEttf"%>
<%@page import="java.util.List"%>
<%@page import="java.util.Set"%>
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
		<td class="label"><%=_obrigatorio%>
			Estrutura
		</td>
		<td>
			<%
	String scripts = _disabled + " onchange=reload(document.forms[0]);";
	%>
			<combo:ComboTag nome="estruturaEtt" objeto="ecar.pojo.EstruturaEtt"
				label="nomeEtt,siglaEtt" value="codEtt" order="nomeEtt"
				scripts="<%=scripts%>" selected="<%=selectedEstrutura%>"
				colecao="<%=listaEstrutura%>" />
		</td>
	</tr>
	<tr>
		<td class="label"><%=_obrigatorio%>
			Fun&ccedil;&atilde;o
		</td>
		<td>
			<%
	scripts = _disabled + " onchange=reload(document.forms[0]);";
	%>
			<combo:ComboTag nome="funcaoFun" objeto="ecar.pojo.FuncaoFun"
				label="nomeFun" value="codFun" order="nomeFun"
				scripts="<%=scripts%>" selected="<%=selectedFuncao%>"
				colecao="<%=listaFuncao%>" />
		</td>
	</tr>

	<tr>
		<td class="label"><%=_obrigatorio%>
			Label a utilizar no n&iacute;vel
		</td>
		<td>
			<input type="text" name="labelEttf" size="42" maxlength="40"
				value="<%=Pagina.trocaNull(estruturaFuncao.getLabelEttf())%>"
				<%=_disabled%>>
		</td>
	</tr>


	<!-- *************************** Tela de Cadastramento ************************** -->
	<tr>
		<td class="label">
			Tela Cadastramento
		</td>
		<td>
			&nbsp;
		</td>
	</tr>
	<tr>
		<td class="label">
			&nbsp;
		</td>
		<td>
			<table class="form">
				<tr>
					<td style="width:113px"><%=_obrigatorio%>
						Pode ser Bloqueado?
					</td>
					<td>
					
						<%
						if (ehPesquisa) {
						%>
										
							<input type="radio" class="form_check_radio" name="indPodeBloquearEttf" value="<%=Pagina.SIM%>">				
									Sim &nbsp;
							<input type="radio" class="form_check_radio" name="indPodeBloquearEttf" value="<%=Pagina.NAO%>">					
									Não &nbsp;
							<input type="button" value="Limpar" onclick="javascript:limparRadioButtons(indPodeBloquearEttf)">
							<br>
							
						<%
						} else {
						%>
							<input type="checkbox" class="form_check_radio" name="indPodeBloquearEttf" id="indPodeBloquearEttf" value="<%=Pagina.SIM %>"
							<%=Pagina.isChecked(estruturaFuncao.getIndPodeBloquearEttf(), Pagina.SIM)%>
							<%=_pesqdisabled%>
							onchange="exibeEscondeListFuncaoAcomp ('indPodeBloquearEttf', 'lstFuncAcomp');">
						<% 
						}
						%>
					
						
					</td>
				</tr>
				
				<%  
					String displayFuncAcomp = "none";
					Set setTpfa = null;
					
					if (estruturaFuncao != null && Pagina.trocaNull(estruturaFuncao.getIndPodeBloquearEttf()).equals(Pagina.SIM)){
						displayFuncAcomp = "inline";
						setTpfa = estruturaFuncao.getLibTipoFuncAcompTpfas();
					} 
					 
				 %>
				<tr >
					<td style="vertical-align: text-top;"> <span id="tdLiberado" style="display: <%=displayFuncAcomp %>; vertical-align:top;"> Est&aacute; liberado para</span> </td>
					<td > <table id="lstFuncAcomp" style="display: <%=displayFuncAcomp %> ;"><tr><td>
					
						<combo:CheckListTag 
							nome="limbTipoFuncAcompTpfa"
							objeto="ecar.pojo.TipoFuncAcompTpfa" 
							label="labelTpfa" 
							value="codTpfa" 
							order="labelTpfa"
							selected="<%=setTpfa %>"
							scripts="<%=_disabled%>"
						/>
					</td></tr></table>
					</td>
				</tr>

				<tr>
					<td width="110px">
						Seqüência
					</td>
					<td>
						<input type="text" name="seqApresentacaoTelaEttf" size="6"
							maxlength="4"
							value="<%=Pagina.trocaNull(estruturaFuncao.getSeqApresentacaoTelaEttf())%>"
							<%=_disabled%>>
					</td>
				</tr>
			</table>
		</td>
	</tr>

	<!-- *************************** Impressão do Cadastro de Itens ************************** -->
	<tr>
		<td class="label">
			Impress&atilde;o do Cadastro de Itens
		</td>
		<td>
			&nbsp;
		</td>
	</tr>
	<tr>
		<td class="label">
			&nbsp;
		</td>
		<td>
			<table class="form">
				<tr>
					<td width="110px">
						Seqüência
					</td>
					<td>
						<input type="text" name="seqApresentacaoRelatorioEttf" size="6"
							maxlength="4"
							value="<%=Pagina.trocaNull(estruturaFuncao.getSeqApresentacaoRelatorioEttf())%>"
							<%=_disabled%>>
					</td>
				</tr>

				<tr>
					<td width="110px"><%=_obrigatorio%>Completa
					</td>
					<td>
					
						<%
						if (ehPesquisa) {
						%>
										
							<input type="radio" class="form_check_radio" name="indListagemImpressCompEttf" value="<%=Pagina.SIM%>">				
									Sim &nbsp;
							<input type="radio" class="form_check_radio" name="indListagemImpressCompEttf" value="<%=Pagina.NAO%>">					
									Não &nbsp;
							<input type="button" value="Limpar" onclick="javascript:limparRadioButtons(indListagemImpressCompEttf)">
							<br>
							
						<%
						} else {
						%>
							<input type="checkbox" class="form_check_radio" name="indListagemImpressCompEttf" value="<%=Pagina.SIM %>"
							<%=Pagina.isChecked(estruturaFuncao.getIndListagemImpressCompEttf(), Pagina.SIM)%>
							<%=_pesqdisabled%>>
						<% 
						}
						%>
											
					</td>
				</tr>
				<tr>
					<td width="110px"><%=_obrigatorio%>Resumo
					</td>
					<td>
					
						<%
						if (ehPesquisa) {
						%>
										
							<input type="radio" class="form_check_radio" name="indListagemImpressaResEttf" value="<%=Pagina.SIM%>">				
									Sim &nbsp;
							<input type="radio" class="form_check_radio" name="indListagemImpressaResEttf" value="<%=Pagina.NAO%>">					
									Não &nbsp;
							<input type="button" value="Limpar" onclick="javascript:limparRadioButtons(indListagemImpressaResEttf)">
							<br>
							
						<%
						} else {
						%>
							<input type="checkbox" class="form_check_radio" name="indListagemImpressaResEttf" value="<%=Pagina.SIM %>"
							<%=Pagina.isChecked(estruturaFuncao.getIndListagemImpressaResEttf(), Pagina.SIM)%>
							<%=_pesqdisabled%>>
						<% 
						}
						%>
											
					</td>
				</tr>
				<tr>
					<td width="110px"><%=_obrigatorio%>Revis&atilde;o
					</td>
					<td>
					
						<%
						if (ehPesquisa) {
						%>
										
							<input type="radio" class="form_check_radio" name="indRevisaoEttf" value="<%=Pagina.SIM%>">				
									Sim &nbsp;
							<input type="radio" class="form_check_radio" name="indRevisaoEttf" value="<%=Pagina.NAO%>">					
									Não &nbsp;
							<input type="button" value="Limpar" onclick="javascript:limparRadioButtons(indRevisaoEttf)">
							<br>
							
						<%
						} else {
						%>
							<input type="checkbox" class="form_check_radio" name="indRevisaoEttf" value="<%=Pagina.SIM %>"
							<%=Pagina.isChecked(estruturaFuncao.getIndRevisaoEttf(), Pagina.SIM)%>
							<%=_pesqdisabled%>>
						<% 
						}
						%>
											
					</td>
				</tr>
			</table>
		</td>
	</tr>
	<tr>
		<td class="label">Dica do campo</td>
		<td>
			<textarea name="dicaEttf" cols="60" rows="4"
				onkeyup="return validaTamanhoLimite(this, 2000);"
				onkeydown="return validaTamanhoLimite(this, 2000);"
				onblur="return validaTamanhoLimite(this, 2000);" <%=_readOnly%>><%=Pagina.trocaNull(estruturaFuncao.getDicaEttf())%></textarea>
		</td>
	</tr>
	<%if (blVisualizaDesc == true){ %>
	<tr>
		<td class="label">
			Documenta&ccedil;&atilde;o
		</td>
		<td>
			<script language="JavaScript" type="text/javascript">
               //Usage: initRTE(imagesPath, includesPath, cssFile, genXHTML)
				initRTE("<%=_pathEcar%>/images/rte/", "<%=_pathEcar%>", "", false);
               </script>

			<script language="JavaScript" type="text/javascript">
               //Usage: writeRichText(fieldname, html, width, height, buttons, readOnly)
               //writeRichText('rte', 'enter body text here', 400, 200, true, false);                          
               writeRichText('rte', "<%=Pagina.trocaNull(estruturaFuncao.getDocumentacaoEttf()).replaceAll("\n", " ").replaceAll("\r", " ").replaceAll("</SCRIPT>","&lt;/SCRIPT&gt;").replaceAll("</script>","&lt;/script&gt;").replaceAll("</script -->","&lt;/script --&gt;").replaceAll("</SCRIPT -->","&lt;/SCRIPT --&gt;")%>", 400, 200, true,<%=strReadOnly%>);
               
               </script>

			<input type="hidden" name="richText" id="richText" size="2000">
			<input type="hidden" name="documentacaoEttf" size="2000">
		</td>
	</tr>
<%
	}
%>
	<tr>
		<td class="label"><%=_obrigatorio%>
			Exibir Histórico
		</td>
		<td>		
			<%
			if (ehPesquisa) {
			%>
							
				<input type="radio" class="form_check_radio" name="indExibirHistoricoEttf" value="<%=Pagina.SIM%>">				
						Sim &nbsp;
				<input type="radio" class="form_check_radio" name="indExibirHistoricoEttf" value="<%=Pagina.NAO%>">					
						Não &nbsp;
				<input type="button" value="Limpar" onclick="javascript:limparRadioButtons(indExibirHistoricoEttf)">
				<br>
				
			<%
			} else {
			%>
				<input type="checkbox" class="form_check_radio" name="indExibirHistoricoEttf" value="<%=Pagina.SIM%>"
				<%=Pagina.isChecked(estruturaFuncao.getIndExibirHistoricoEttf(), Pagina.SIM)%>
				<%=_pesqdisabled%>>
			<% 
			}
			%>
								
		</td>
	</tr>

	<%@ include file="/include/estadoMenu.jsp"%>

</table>