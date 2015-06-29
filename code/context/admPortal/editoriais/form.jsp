<util:msgObrigatorio obrigatorio="<%=_obrigatorio%>" />

<%
if("".equals(codSegmento)){
	List segmentos = new SegmentoDao(request).getSegmentosLivresComGrupoDeAtributoParaEditorias(application);	
	%>
		<tr>
			<td class="label"><%=_obrigatorio%> Segmento</td>
			<td>
				<combo:ComboTag 	
					nome="codSegmento"
					objeto="ecar.pojo.SegmentoSgt" 
					label="tituloSgt" 
					value="codSgt" 
					order="tituloSgt"
					scripts="<%=_disabled%>"
					colecao="<%=segmentos%>"					
				/>						
			</td>
		</tr> 	
	<%
} else {
	%>
	<tr>
		<td class="label">Segmento</td>
		<td>
		<%=segmento.getTituloSgt()%>
		</td>
	</tr> 	
	<input type="hidden" name="codSegmento" value="<%=codSegmento%>">
	<%
}
%>

<tr>
	<td class="label"><%=_obrigatorio%> Título</td>
	<td><input type="text" name="descricaoSatb" size="45" maxlength="40" value="<%=Pagina.trocaNull(editoria.getDescricaoSatb())%>" <%=_disabled%>></td>
</tr> 
<tr>
	<td class="label"><%=_obrigatorio%> Ativo</td>
	<td>
		<util:comboSimNaoTag nome="indAtivoSatb" valorSelecionado="<%=editoria.getIndAtivoSatb()%>" scripts="<%=_disabled%>" opcaoBranco="<%=_comboSimNao%>"/>
	</td>
</tr>
<tr><td class="label">&nbsp;</td></tr>

<% /* controla o estado do menu (aberto ou fechado)*/ %>
<%@ include file="../../include/estadoMenu.jsp"%>
