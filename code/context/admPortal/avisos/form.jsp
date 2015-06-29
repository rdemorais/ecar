<%
/*
 * arquivo comum para as telas de inclusao, alteracao, exclusao e pesquisa 
 * observar a variavel _disabled e setar o objeto corretamente pelo construtor
 * assinalando os campos com os valores iniciais para inclusao
 */ 
  
%>
<util:msgObrigatorio obrigatorio="<%=_obrigatorio%>" />

<tr>
	<td class="label"><%=_obrigatorio%> Nome</td>
	<td><input type="text" name="nomePpp" size="35" maxlength="30" value="<%=Pagina.trocaNull(popUp.getNomePpp())%>" <%=_disabled%>></td>
</tr> 
<tr>
	<td class="label"><%=_obrigatorio%> Descrição</td>

<%
	if (hidRichText != (Long.valueOf(1)).intValue()){
%>
	
		<td>
			<script language="JavaScript" type="text/javascript">
			//Usage: initRTE(imagesPath, includesPath, cssFile, genXHTML)
               initRTE("<%=_pathEcar%>/images/rte/", "<%=_pathEcar%>", "", false);
			</script>
			
			<script language="JavaScript" type="text/javascript">
			//Usage: writeRichText(fieldname, html, width, height, buttons, readOnly)
			writeRichText('rte', "<%=Pagina.trocaNull(popUp.getConteudoPpp()).replaceAll("\n", " ").replaceAll("\r", " ").replaceAll("</SCRIPT>","&lt;/SCRIPT&gt;").replaceAll("</script>","&lt;/script&gt;").replaceAll("</script -->","&lt;/script --&gt;").replaceAll("</SCRIPT -->","&lt;/SCRIPT --&gt;")%>", 400, 200, true, "<%=_readOnly%>");
			</script>	
			
			<input type="hidden" name="richText" size="90"  >
			<input type="hidden" name="conteudoPpp" size="90"  >	
		</td>			
<%
	}else{
%>		
		<td>	
			<textarea name="conteudoPpp" rows="4" cols="60" 
			onkeyup="return validaTamanhoLimite(this, 4000);" 
			onkeydown="return validaTamanhoLimite(this, 4000);"
			onblur="return validaTamanhoLimite(this, 4000);" <%=_readOnly%>><%=Pagina.trocaNull(popUp.getConteudoPpp())%></textarea>
		</td>	
<%
	}
%>
	
<%--	<td>--%>
<%--		<textarea name="conteudoPpp" <%=_readOnly%> rows="4" cols="60" onkeyup="return validaTamanhoLimite(this, 2000);"><%=Pagina.trocaNull(popUp.getConteudoPpp())%></textarea>		--%>
<%--	</td>--%>
</tr> 
<tr>
	<td class="label"><%=_obrigatorio%> Altura Janela</td>
	<td><input type="text" name="janelaAlturaPpp" size="15" maxlength="10" value="<%=Pagina.trocaNull(popUp.getJanelaAlturaPpp())%>" <%=_disabled%>></td>
</tr> 
<tr>
	<td class="label"><%=_obrigatorio%> Largura Janela</td>
	<td><input type="text" name="janelaLarguraPpp" size="15" maxlength="10" value="<%=Pagina.trocaNull(popUp.getJanelaLarguraPpp())%>" <%=_disabled%>></td>
</tr> 
<tr>
	<td class="label"><%=_obrigatorio%> Início Validade</td>
	<td>
		<input type="text" name="dataIniExibicaoPpp" size="15" maxlength="10" value="<%=Pagina.trocaNullData(popUp.getDataIniExibicaoPpp())%>" <%=_disabled%> onkeyup="mascaraData(event, this);">
		<img class="posicao" title="Selecione a data" src="../../images/icone_calendar.gif" onclick="open_calendar('data', document.forms[0].dataIniExibicaoPpp, '')">		
	</td>
</tr>
<tr>
	<td class="label"><%=_obrigatorio%> Fim Validade</td>
	<td>
		<input type="text" name="dataFimExibicaoPpp" size="15" maxlength="10" value="<%=Pagina.trocaNullData(popUp.getDataFimExibicaoPpp())%>" <%=_disabled%> onkeyup="mascaraData(event, this);">
		<img class="posicao" title="Selecione a data" src="../../images/icone_calendar.gif" onclick="open_calendar('data', document.forms[0].dataFimExibicaoPpp, '')">		
	</td>
</tr>
<tr>
	<td class="label"><%=_obrigatorio%> Comportamento</td>
	<td>
		<%
		String strComportamento = "";
		if(popUp.getPopupComportamentoPppc() != null){
			strComportamento = popUp.getPopupComportamentoPppc().getCodPppc().toString();
		}
		%>
		<combo:ComboTag 
				nome="popupComportamentoPppc"
				objeto="ecar.pojo.PopupComportamentoPppc" 
				label="descricaoPppc" 
				value="codPppc" 
				order="descricaoPppc"
				scripts="<%=_disabled%>"
				selected="<%=strComportamento%>"
		/>		
	</td>
</tr> 
<tr>
	<td class="label"><%=_obrigatorio%> Permite Desativar</td>
	<td>
		<util:comboSimNaoTag nome="indDesativarPpp" valorSelecionado="<%=popUp.getIndDesativarPpp()%>" scripts="<%=_disabled%>" opcaoBranco="<%=_comboSimNao%>"/>
	</td>
</tr>
<tr>
	<td class="label"><%=_obrigatorio%> Ativo</td>
	<td>
		<util:comboSimNaoTag nome="indAtivaPpp" valorSelecionado="<%=popUp.getIndAtivaPpp()%>" scripts="<%=_disabled%>" opcaoBranco="<%=_comboSimNao%>"/>
	</td>
</tr>


<tr><td class="label">&nbsp;</td></tr>

<% /* controla o estado do menu (aberto ou fechado)*/ %>
<%@ include file="../../include/estadoMenu.jsp"%>
