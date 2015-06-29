
<jsp:directive.page import="java.util.List"/>
<%
/*
 * arquivo comum para as telas de inclusao, alteracao, exclusao e pesquisa 
 * observar a variavel disabled e setar o objeto corretamente pelo construtor
 * assinalando os campos com os valores iniciais para inclusao
 */ 
%>


<% 
	Set telefones = new HashSet();
	if (entidade.getTelefoneTels() == null)
		entidade.setTelefoneTels(new HashSet());
	telefones = entidade.getTelefoneTels();

	Set locais = new HashSet();
	if (entidade.getLocalEntidadeLents() == null)
		entidade.setLocalEntidadeLents(new HashSet());
	locais = entidade.getLocalEntidadeLents();

	
	Set enderecos = new HashSet();
	if (entidade.getEnderecoEnds() == null)
		entidade.setEnderecoEnds(new HashSet());
	enderecos = entidade.getEnderecoEnds();
%>

<input type="hidden" id="contTelefones" name="contTelefones"  value="0">
<input type="hidden" id="contEnderecos" name="contEnderecos" value="0">
<input type="hidden" id="contLocal" name="contLocal" value="0">
<script language="javascript" src="../../js/limpezaCamposMultivalorados.js"></script>
<util:msgObrigatorio obrigatorio="<%=_obrigatorio%>" />
<tr>
	<td class="label"><%=_obrigatorio%> Nome</td>
	<td>
		<textarea name="nomeEnt" rows="3" cols="55" 
			onkeyup="return validaTamanhoLimite(this, 2000);" 
			onkeydown="return validaTamanhoLimite(this, 2000);"
			onblur="return validaTamanhoLimite(this, 2000);"
			<%=_readOnly%>><%=Pagina.trocaNull(entidade.getNomeEnt())%></textarea>
	</td>
</tr>

<tr>
	<td class="label">Sigla</td>
	<td><input type="text" name="siglaEnt" size="20" maxlength="10" value="<%=Pagina.trocaNull(entidade.getSiglaEnt())%>" <%=_disabled%>></td>
</tr>

<tr>
	<td class="label"> CPF/CNPJ</td>
	<td><input type="text" name="cpfCnpjEnt" size="20" maxlength="20" value="<%=Pagina.trocaNull(entidade.getCpfCnpjEnt())%>" <%=_disabled%>></td>
</tr>

<tr>
	<td class="label"> e-mail</td>
	<td><input type="text" name="emailEnt" size="40" maxlength="50" value="<%=Pagina.trocaNull(entidade.getEmailEnt())%>" <%=_disabled%>></td>
</tr>
<%
if (!pesquisa) {
	if((!navega) || (locais.size() > 0)) {
%>
<tr> 
		<td class="label" colspan="2">&nbsp;</td>		
</tr> 
<tr id="labelLocais">
	<td class="label"><span class="fontLabelEntidade">Locais</span></td>
	<td>
		<%if (_disabled == "") {%>
		<a href="#" onClick="abreJanela('popUpLocal.jsp','500', '300', 'LocalItem');"> Adicionar Novo Local </a>
		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		<a id="localExcluir" href="#" onClick="removeLocal();"></a>
		<% } %>
	</td>
</tr>
<tr>
	<td id="local" colspan="2"> </td>
</tr>
<%
	}
	if((!navega) || (enderecos.size() > 0)) {
%>
<tr> 
		<td class="label" colspan="2">&nbsp;</td>		
</tr> 
<tr>
	<td class="label"><span class="fontLabelEntidade">Endereços</span></td>
	<td>
		<%if (_disabled == "") {%>	
			<a href="#" onClick="adicionaEndereco('','','','','','','','')"> Adicionar Novo Endereço </a>
		<% } %>
	</td>
</tr>
<tr>
	<td id="endereco" colspan="2"> </td>
</tr>
<%
	}
	if((!navega) || (telefones.size() > 0)) {
%>
<tr> 
		<td class="label" colspan="2">&nbsp;</td>		
</tr> 
<tr>
	<td class="label"><span class="fontLabelEntidade">Telefones</span></td>
	<td>
	<%if (_disabled == "") {%>	
		<a href="#" onClick="adicionaTelefone('','','')"> Adicionar Novo Telefone </a>
	<% } %>
	</td>
</tr>
<tr>
	<td id="telefone" colspan="2"> </td>
</tr>
<%		}
	 } %>
<tr> 
		<td class="label" colspan="2">&nbsp;</td>		
</tr> 

<%
// Início da Implementação dos ATRIBUTOS LIVRES!
//--------------------------------------------------
SisGrupoAtributoDao grupoAtributoDao = new SisGrupoAtributoDao(request);
List grupoAtributoObrigatorios = new ArrayList();
List atributosCadastro = new SisGrupoAtributoDao(request).getGruposAtributosCadastro("E");
List atributosEntidade;

		if(atributosCadastro != null){
				Iterator it = atributosCadastro.iterator();
				while(it.hasNext()) {
					SisGrupoAtributoSga grupoAtributo = (SisGrupoAtributoSga) it.next();
					if (grupoAtributo.getSisAtributoSatbs().size() > 0)
					{
						atributosEntidade = entidadeDao.getEntidadeAtributoByGrupo(entidade, grupoAtributo);
						String strDisabled = "";
						List selecionados = new ArrayList();	
						SisAtributoSatb atributo = (SisAtributoSatb) grupoAtributo.getSisAtributoSatbs().iterator().next();					
						String nomeCampo = "a" + grupoAtributo.getCodSga().toString();
						String pathRaiz = request.getContextPath();

						if("disabled".equals(_disabled))
							strDisabled="S";						
						selecionados.addAll(atributosEntidade);
						if("S".equals(grupoAtributo.getIndObrigatorioSga()))
							grupoAtributoObrigatorios.add(grupoAtributo);						
						%>
						<ecar:Input
							label="<%=grupoAtributo.getDescricaoSga()%>"
							tipo="<%=Integer.valueOf(grupoAtributo.getSisTipoExibicGrupoSteg().getCodSteg().toString()).intValue()%>"
							obrigatorio="<%=grupoAtributo.getIndObrigatorioSga()%>"
							labelObrigatorio="<%=_obrigatorio%>"
							disabled="<%=strDisabled%>"
							nome="<%=nomeCampo%>"
							classLabel="label"
							selecionados="<%=selecionados%>"
							sisAtributo="<%=atributo%>"
							pathRaiz="<%=pathRaiz%>"
							size="20"
						> 
						<%
											
						List opcoes = new ArrayList();
						String selectedCodSapadrao = "";
						if(pesquisa){
							if(grupoAtributo.getSisTipoOrdenacaoSto() != null){
								opcoes = grupoAtributoDao.getAtributosOrdenados(grupoAtributo);
							}
						}
						else{
							if(grupoAtributo.getCodSga() != null && grupoAtributo.getCodSga().longValue() != 1){
								if(grupoAtributo.getSisTipoOrdenacaoSto() != null){
									opcoes = grupoAtributoDao.getAtributosOrdenados(grupoAtributo);
								}
							} 
						}
						%>
							<ecar:Options
								options="<%=opcoes%>" 
								valor="codSatb"
								nome="<%=nomeCampo%>"
								label="descricaoSatb"
							/>					
					
						</ecar:Input>
						<%
					}
			}
	}
	%>
<script>	
<%
	Iterator itTel = telefones.iterator();
	TelefoneTel tel = new TelefoneTel();
	while (itTel.hasNext())
	{
		tel = (TelefoneTel) itTel.next();
		out.println("adicionaTelefone(\""+Pagina.trocaNull(tel.getDddTel()).trim()+"\", \""+Pagina.trocaNull(tel.getTelefoneTel()).trim()+"\", \""+Pagina.trocaNull(tel.getIdTel()).trim()+"\");\n");
	}

	Iterator itLoc = locais.iterator();
	LocalItemLit loc = new LocalItemLit();
	while (itLoc.hasNext())
	{
		loc = (LocalItemLit) itLoc.next();
		out.println("adicionaLocal(\""+loc.getCodLit().toString().trim()+"\", \""+loc.getIdentificacaoLit().trim()+"\");\n");
		out.println("labelExcluirLocal();\n");
	}


	Iterator itEnd = enderecos.iterator();
	EnderecoEnd end = new EnderecoEnd();

	while (itEnd.hasNext())
	{
		end = (EnderecoEnd) itEnd.next();
		String var = "";
		if (end.getTipoEnderecoTend() == null){
			var = "null";
		} else {
			var = end.getTipoEnderecoTend().getCodTpend().toString().trim();
		}
		out.println("adicionaEndereco(\""+
					Pagina.trocaNull(end.getIdEnd())+"\", \""+
					Pagina.trocaNull(var)+"\", \""+
					Pagina.trocaNull(end.getEnderecoEnd()).trim()+"\", \""+
					Pagina.trocaNull(end.getComplementoEnd()).trim()+"\", \""+
					Pagina.trocaNull(end.getBairroEnd()).trim()+"\", \""+
					Pagina.trocaNull(end.getCepEnd().toString()).trim()+"\", \""+
					Pagina.trocaNull(end.getCidadeEnd()).trim()+"\", \""+
					Pagina.trocaNull(end.getUf().getCodUf()).trim()+"\");\n"
				);
	}
%>

function validaCamposVariaveis(form){
	<%= new ecar.dao.SisGrupoAtributoDao(request).getValidacoesAtributos(grupoAtributoObrigatorios)%>
	return true;
}
</script>

<tr>
	<td class="label"><%=_obrigatorio%> Órgão </td>
	<td>
		<util:comboSimNaoTag nome="indOrgaoEnt" valorSelecionado="<%=entidade.getIndOrgaoEnt()%>" scripts="<%=_disabled%>" opcaoBranco="<%=_comboSimNao%>"/>
	</td>
</tr>

<tr>
	<td class="label"><%=_obrigatorio%> Entidade Ativa</td>
	<td>
		<util:comboSimNaoTag nome="indAtivoEnt" valorSelecionado="<%=entidade.getIndAtivoEnt()%>" scripts="<%=_disabled%>" opcaoBranco="<%=_comboSimNao%>"/>
	</td>
</tr>

<% /* controla o estado do menu (aberto ou fechado)*/ %>
<%@ include file="../../include/estadoMenu.jsp"%>
