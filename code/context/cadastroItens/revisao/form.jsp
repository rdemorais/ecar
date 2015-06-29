
<script language="javascript" src="../../js/limpezaCamposMultivalorados.js"></script>

<%
ConfiguracaoDao configuracaoDao = new ConfiguracaoDao(request);
if(configuracaoDao.getConfiguracao() != null) {

if (!"".equals(Pagina.getParamStr(request, "codIettrev")))
	ieRevisao = (ItemEstruturarevisaoIettrev) ieRevisaoDao.buscar(ItemEstruturarevisaoIettrev.class, Integer.valueOf(Pagina.getParamStr(request, "codIettrev")));
	
	

if(ieRevisao.getCodIettrev() != null){%>
	<input type="hidden" name="codIettrev" value="<%=ieRevisao.getCodIettrev()%>">
<%} else {%>	
	<input type="hidden" name="codIettrev" value="">
<%}%>

<input type="hidden" name="codEtt" value="<%=estrutura.getCodEtt()%>">
<input type="hidden" name="codIett" value="<%=itemEstrutura.getCodIett()%>">
<input type="hidden" name="codAba" value=<%=codAba%>>
	<TR>
	<TD class="label">* Tipo</TD>
	<TD>
		<select name="situacaoIettrev" 
		<% if(desabilitar) out.write("disabled=\"disabled\"");%>><%
		%>		
			<option value="X"> </option>
			<option value="A" <%if("A".equals(ieRevisao.getSituacaoIettrev())) out.write("selected");%>>Alteração</option>
			<option value="I" <%if("I".equals(ieRevisao.getSituacaoIettrev())) out.write("selected");%>>Inclusão</option>
			<option value="E" <%if("E".equals(ieRevisao.getSituacaoIettrev())) out.write("selected");%>>Exclusão</option>
			<option value="S" <%if("S".equals(ieRevisao.getSituacaoIettrev())) out.write("selected");%>>Sem Modificação</option>
		</select>
	</TD>
</TR>			

<%
if(atributos != null){
	Iterator it = atributos.iterator();
	while(it.hasNext()){
		ObjetoEstrutura atributo = (ObjetoEstrutura) it.next();
		%>
			<util:formItemEstruturaRevisao 
				atributo="<%=atributo%>" 
				itemEstruturaRevisao="<%=ieRevisao%>" 
				desabilitar="<%=new Boolean(desabilitar)%>" 
				seguranca="<%=seguranca%>"
				
			/>	
		<%
	}
	%>
	<TR>
	<TD class="label">* Justificativa</TD>
	<TD>
		<textarea name="justificativaIettrev" rows="4" cols="60" 
		onkeyup="return validaTamanhoLimite(this, 2000);" 
		onkeydown="return validaTamanhoLimite(this, 2000);"
		onblur="return validaTamanhoLimite(this, 2000);"
		<% if(desabilitar) out.write("disabled=\"disabled\"");%>><%
		if(ieRevisao.getJustificativaIettrev() != null)
		out.write(ieRevisao.getJustificativaIettrev().toString());
		%></textarea>
	</TD>
</TR>			
<%
}

%>

<%} else {
	out.println("<br> Efetue a Configura&ccedil;&atilde;o Geral do Sistema antes de realizar o Cadastro de Itens <br><br> ");
}%>

<% /* Controla o estado do Menu (aberto ou fechado) */%>
<%@ include file="../../include/estadoMenu.jsp"%>