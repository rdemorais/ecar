
<jsp:directive.page import="ecar.util.Dominios"/><%@ taglib uri="/WEB-INF/ecar-util.tld" prefix="util" %>
<%@ taglib uri="/WEB-INF/ecar-combo.tld" prefix="combo" %>
<%@ taglib uri="/WEB-INF/ecar-input.tld" prefix="ecar" %>
<%@ page import="ecar.pojo.UsuarioUsu" %>
<%@ page import="ecar.pojo.ConfiguracaoCfg" %>
<%@ page import="ecar.pojo.SisAtributoSatb" %>
<%@ page import="ecar.pojo.SisGrupoAtributoSga" %>
<%@ page import="ecar.dao.UsuarioDao" %>
<%@ page import="ecar.dao.ConfiguracaoDao" %>
<%@ page import="ecar.dao.SisGrupoAtributoDao" %>

<%@ page import="java.util.Iterator" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.Set" %>
<%@ page import="java.util.ArrayList" %> 
<%@ page import="comum.util.FileUpload" %>
<%@ page import="ecar.pojo.UsuarioAtributoUsua" %>
<%@ page import="ecar.pojo.OrgaoOrg" %>


<script>

function existeOrgao(codOrg)
{
	for(i = 1; i <= parseInt(document.getElementById('numOrgaos').value); i++)
	{
		if (document.getElementById("adicionaOrgaoOrg"+i).value == "S") 
		{	
			if(document.getElementById("orgaoOrg"+i).value == codOrg)
			{
				alert("�rg�o j� informado!");
				return true;
			}
		}
	}
	return false;
}

function removeOrgao() {
	var selecionados = 0;
	for (i = 1; i <= parseInt(document.getElementById('numOrgaos').value); i++){
		if (document.getElementById("adicionaOrgaoOrg"+i).value == "S") {
			if (document.getElementById("chkOrgaoOrg"+i).checked == true) {
				selecionados++;
			}
		}
	}
		
	if (selecionados > 0) {
		if (confirm("Os �rg�os selecionados ser�o removidos. Confirma?")) {
			for (i = 1; i <= parseInt(document.getElementById('numOrgaos').value); i++) {
				if (document.getElementById("adicionaOrgaoOrg"+i).value == "S") {
					if (document.getElementById("chkOrgaoOrg"+i).checked == true)
						document.getElementById("orgao"+i).innerHTML = "<input type=\"hidden\" id=\"adicionaOrgaoOrg" + i +"\" name=\"adicionaOrgaoOrg" + i +"\" value=\"N\">\n";
				}
			}
			alert("�rg�o(s) removido(s).");
		}
	} else {
		alert("Pelo menos um �rg�o deve ser selecionado");
	}
}

function adicionaOrgao (form)
{
	if (form.orgaoOrg.value == "")
	{
		alert ("Selecione um �rg�o!");
	}
	else
	{
		contador = parseInt(form.numOrgaos.value);
		if (!existeOrgao(form.orgaoOrg.value))
		{
			contador += 1;
			divConteudo  = "\n<!-- Orgao Adicionado dinamicamente -->";
			divConteudo += "\n<div id=\"orgao"+contador+ "\" name=\"orgao"+contador+"\">";		
			divConteudo += "\n<table>";		
			divConteudo += "\n<tr>";
			divConteudo += "\n\t<td>";
			divConteudo += "\n\t\t<input type=\"hidden\" id=\"adicionaOrgaoOrg"+contador+ "\" name=\"adicionaOrgaoOrg"+contador+"\" value=\"S\" >";
			divConteudo += "\n\t\t<input type=\"hidden\" id=\"orgaoOrg"+contador+ "\" name=\"orgaoOrg"+contador+"\" value=\""+form.orgaoOrg.value+"\" >";
			divConteudo += "\n\t\t<input type=\"checkbox\" class=\"form_check_radio\" id=\"chkOrgaoOrg"+contador+ "\" name=\"chkOrgaoOrg"+contador+"\" value=\""+form.orgaoOrg.value+"\" >";
			divConteudo += "\n\t\t"+form.orgaoOrg.options[form.orgaoOrg.selectedIndex].text;
			divConteudo += "\n\t</td>";
			divConteudo += "\n</tr>";
			divConteudo += "\n</table>";
			divConteudo += "\n</div>";
			document.getElementById("orgaos").innerHTML += divConteudo;		
			divConteudo += "\n<!-- FIM de: Orgao Adicionado dinamicamente -->";
			form.numOrgaos.value = contador;
			form.orgaoOrg.value = '';
			return true;
		}
	}
	
	return false;	
}


</script>

<util:msgObrigatorio 
	obrigatorio="<%=_obrigatorio%>" 
 />

<input type="hidden" name="idDominioUsu" value="<%=Pagina.trocaNull(usuario.getIdDominioUsu())%>">

<tr>
	<td class="label"><%=_obrigatorio%> Usu&aacute;rio Ativo?</td>
	<td>
		<input type="radio" class="form_check_radio" name="indAtivoUsu" value="<%=Dominios.SIM%>" <%=Pagina.isChecked(usuario.getIndAtivoUsu(), Dominios.SIM)%> <%=_disabled%>>Sim
		<input type="radio" class="form_check_radio" name="indAtivoUsu" value="<%=Dominios.NAO%>" <%=Pagina.isChecked(usuario.getIndAtivoUsu(), Dominios.NAO)%> <%=_disabled%>>N&atilde;o
	</td>
</tr>

<tr> 
	<td class="label"><%=_obrigatorio%> Nome</td>
	<td>
		<%if(!pesquisa){%>
			<input type="text" name="nomeUsu" size="55" maxlength="50" value="<%=Pagina.trocaNull(usuario.getNomeUsuSent())%>" disabled>
		<%}else{%>
			<input type="text" name="nomeUsu" size="55" maxlength="50" value="<%=Pagina.trocaNull(usuario.getNomeUsuSent())%>">
		<%}%>
		
		<%if(popUpPesquisa) {%>
			&nbsp;<input type=button class="botao" value=Pesquisar onclick="javascript:abrirPopUpSentinela()">
		<%}else{%>
			&nbsp;<input type=button class="botao" value=Pesquisar onclick="javascript:abrirPopUpSentinela()" disabled>
		<%}%>					
	</td>
</tr>
<tr>
	<td class="label"><%=_obrigatorio%> &Oacute;rg&atilde;os</td>
	<td>
		<%if(!"disabled".equals(_disabled)){%>
		<combo:ComboTag 
			nome="orgaoOrg" 
			objeto="ecar.pojo.OrgaoOrg" 
			label="descricaoOrg" 
			value="codOrg" 
			order="descricaoOrg"
			scripts="<%=_disabled%>"
			selected=""
		/>&nbsp; 
			<a href="#" onclick="adicionaOrgao(form)">Adicionar</a> &nbsp;&nbsp; 
			<a href="#" onclick="removeOrgao()">Excluir</a>
		<%}%>&nbsp; 
	</td>
</tr>
<tr>
	<td class="label">&nbsp;</td>
	<td>
		<div id="orgaos"><!--
			<table id="tbOrgaos">
			--><%
			int j = 0;
			if((usuario.getOrgaoOrgs()!=null) && (usuario.getOrgaoOrgs().size() > 0)){
				Iterator itOrgao = usuario.getOrgaoOrgs().iterator();
				OrgaoOrg selectedOrgao;
				while(itOrgao.hasNext())
				{
					j++;
					selectedOrgao = (OrgaoOrg)itOrgao.next();
				%>
			<div id="orgao<%=j%>" name="orgao<%=j%>">
			<table>  				
				<tr>
					<td>
						<input type="hidden" name="adicionaOrgaoOrg<%=j%>" id="adicionaOrgaoOrg<%=j%>"  value="S">
						<input type="hidden" name="orgaoOrg<%=j%>" id="orgaoOrg<%=j%>"  value=<%=selectedOrgao.getCodOrg()%> <%=_disabled%>>
						<input type="checkbox" class="form_check_radio" name="chkOrgaoOrg<%=j%>" id="chkOrgaoOrg<%=j%>"  value=<%=selectedOrgao.getCodOrg()%> <%=_disabled%>>
						<%=selectedOrgao.getDescricaoOrg()%>
					</td>
				</tr>	
			</table>
			</div>
				<%}
			}%>
			<input type="hidden" name="numOrgaos" id="numOrgaos" value="<%=j%>"><!--
	</table>
	

--></div>	

<tr>
		<td class="label">Data de Nascimento</td>
		<td><input type="text" name="dataNascimentoUsu" size="15" maxlength="10" value="<%=Pagina.trocaNullData(usuario.getDataNascimentoUsu())%>" <%=_disabled%> onkeyup="mascaraData(event, this);"></td>
</tr>
<tr>
	<td class="label">CPF/CNPJ</td>
	<td>
	<%if(!pesquisa){%>
		<input type="text" name="cnpjCpfUsu" size="20" maxlength="14" value="<%=Pagina.trocaNull(usuario.getCnpjCpfUsu())%>" disabled>
	<%}else{%>
		<input type="text" name="cnpjCpfUsu" size="20" maxlength="14" value="<%=Pagina.trocaNull(usuario.getCnpjCpfUsu())%>">
	<%}%>
	</td>
</tr>

<tr> 
		<td class="label" colspan="2">&nbsp;</td>		
</tr> 

<tr> 
		<td class="label"><span class="fontLabelEntidade">Endere&ccedil;o Residencial</span></td>
		<td>&nbsp;</td>		
</tr> 

<tr>
	<td class="label">Endere&ccedil;o</td>
	<td><input type="text" name="residEnderecoUsu" size="55" maxlength="50" value="<%=Pagina.trocaNull(usuario.getResidEnderecoUsu())%>" <%=_disabled%>></td>
</tr>
<tr>
	<td class="label">Complemento</td>
	<td><input type="text" name="residComplementoUsu" size="45" maxlength="40" value="<%=Pagina.trocaNull(usuario.getResidComplementoUsu())%>" <%=_disabled%>></td>
</tr>
<tr>
	<td class="label">Bairro</td>
	<td><input type="text" name="residBairroUsu" size="45" maxlength="40" value="<%=Pagina.trocaNull(usuario.getResidBairroUsu())%>" <%=_disabled%>></td>
</tr>
<tr>
	<td class="label">Cidade</td>
	<td><input type="text" name="residCidadeUsu" size="45" maxlength="40" value="<%=Pagina.trocaNull(usuario.getResidCidadeUsu())%>" <%=_disabled%>></td>
</tr>
<tr>
	<td class="label">CEP</td>
	<td><input type="text" name="residCepUsu" size="10" maxlength="8" value="<%=Pagina.trocaNull(usuario.getResidCepUsu())%>" <%=_disabled%>></td>
</tr>
<tr>
	<td class="label">UF</td>
	<td>
	<%
	String selectedUf = "";
    if(usuario.getUfByResidUfUsu()!=null){
		selectedUf = usuario.getUfByResidUfUsu().getCodUf().toString();
	}
	%>
		<combo:ComboTag 
						nome="residUfUsu"
						objeto="ecar.pojo.Uf" 
						label="descricaoUf" 
						value="codUf" 
						order="descricaoUf"
						scripts="<%=_disabled%>" 		
						selected="<%=selectedUf%>"
	/>
	</td>
</tr>
<tr>
	<td class="label">Telefone</td>
	<td>
	<input type="text" name="residDddUsu" size="4" maxlength="3" value="<%=Pagina.trocaNull(usuario.getResidDddUsu())%>" <%=_disabled%>>
	- <input type="text" name="residTelefoneUsu" size="13" maxlength="10" value="<%=Pagina.trocaNull(usuario.getResidTelefoneUsu())%>" <%=_disabled%>>
	</td>
</tr>
<tr>
	<td class="label">Ramal</td>
	<td><input type="text" name="residRamalUsu" size="10" maxlength="6" value="<%=Pagina.trocaNull(usuario.getResidRamalUsu())%>" <%=_disabled%>></td>
</tr>

<tr> 
		<td class="label" colspan="2">&nbsp;</td>		
</tr> 
<tr> 
		<td class="label"><span class="fontLabelEntidade">Endere&ccedil;o Comercial</span></td>
		<td>&nbsp;</td>		
</tr> 
<tr>
	<td class="label">Endere&ccedil;o</td>
	<td><input type="text" name="comercEnderecoUsu" size="55" maxlength="50" value="<%=Pagina.trocaNull(usuario.getComercEnderecoUsu())%>" <%=_disabled%>></td>
</tr>
<tr>
	<td class="label">Complemento</td>
	<td><input type="text" name="comercComplementoUsu" size="45" maxlength="40" value="<%=Pagina.trocaNull(usuario.getComercComplementoUsu())%>" <%=_disabled%>></td>
</tr>
<tr>
	<td class="label">Bairro</td>
	<td><input type="text" name="comercBairroUsu" size="45" maxlength="40" value="<%=Pagina.trocaNull(usuario.getComercBairroUsu())%>" <%=_disabled%>></td>
</tr>
<tr>
	<td class="label">Cidade</td>
	<td><input type="text" name="comercCidadeUsu" size="45" maxlength="40" value="<%=Pagina.trocaNull(usuario.getComercCidadeUsu())%>" <%=_disabled%>></td>
</tr>
<tr>
	<td class="label">CEP</td>
	<td><input type="text" name="comercCepUsu" size="10" maxlength="8" value="<%=Pagina.trocaNull(usuario.getComercCepUsu())%>" <%=_disabled%>></td>
</tr>
<tr>
	<td class="label">UF</td>
	<td>
	<%
	selectedUf = "";
	if(usuario.getUfByComercUfUsu()!=null){
		selectedUf = usuario.getUfByComercUfUsu().getCodUf().toString();
	}
	%>
		<combo:ComboTag 
						nome="comercUfUsu"
						objeto="ecar.pojo.Uf" 
						label="descricaoUf" 
						value="codUf" 
						order="descricaoUf"
						scripts="<%=_disabled%>" 		
						selected="<%=selectedUf%>"
	/>
	</td>
</tr>
<tr>
	<td class="label">Telefone</td>
	<td>
	<input type="text" name="comercDddUsu" size="4" maxlength="3" value="<%=Pagina.trocaNull(usuario.getComercDddUsu())%>" <%=_disabled%>>
	- <input type="text" name="comercTelefoneUsu" size="13" maxlength="10" value="<%=Pagina.trocaNull(usuario.getComercTelefoneUsu())%>" <%=_disabled%>>
	</td>
</tr>
<tr>
	<td class="label">Ramal</td>
	<td><input type="text" name="comercRamalUsu" size="10" maxlength="6" value="<%=Pagina.trocaNull(usuario.getComercRamalUsu())%>" <%=_disabled%>></td>
</tr>
<tr>
	<td class="label">Fax</td>
	<td>
	<input type="text" name="dddFaxUsu" size="4" maxlength="3" value="<%=Pagina.trocaNull(usuario.getDddFaxUsu())%>" <%=_disabled%>>
	- <input type="text" name="faxUsu" size="13" maxlength="10" value="<%=Pagina.trocaNull(usuario.getFaxUsu())%>" <%=_disabled%>>
	</td>
</tr>

<tr> 
		<td class="label" colspan="2">&nbsp;</td>		
</tr> 

<tr>
	<td class="label">Endere&ccedil;o para Correspond&ecirc;ncia</td>
	<td>
	<%
	String selectedTpec = "";
	if(usuario.getTipoEnderecoCorrTpec()!=null){
		selectedTpec = usuario.getTipoEnderecoCorrTpec().getCodTpec().toString();
	}
	%>
		<combo:ComboTag 
						nome="tipoEnderecoCorrTpec"
						objeto="ecar.pojo.TipoEnderecoCorrTpec" 
						label="descricaoTpec" 
						value="codTpec" 
						order="descricaoTpec"
						scripts="<%=_disabled%>" 		
						selected="<%=selectedTpec%>"
	/>
	</td>
</tr>

<tr>
	<td class="label"><%=_obrigatorio%> E Mail</td>
	<td>
	<%if(!pesquisa){%>
		<input type="text" name="email1Usu" size="55" maxlength="50" value="<%=Pagina.trocaNull(usuario.getEmail1UsuSent())%>" disabled>
	<%}else{%>
		<input type="text" name="email1Usu" size="55" maxlength="50" value="<%=Pagina.trocaNull(usuario.getEmail1UsuSent())%>">	
	<%}%>
	</td>
</tr>
<tr>
	<td class="label">E Mail 2</td>
	<td><input type="text" name="email2Usu" size="55" maxlength="50" value="<%=Pagina.trocaNull(usuario.getEmail2Usu())%>" <%=_disabled%>></td>
</tr>
<tr>
	<td class="label"><%=_obrigatorio%> Login</td>
	<td>
	<%if(!pesquisa){%>
		<input type="text" name="idUsuarioUsu" size="22" maxlength="20" value="<%=Pagina.trocaNull(usuario.getIdUsuarioUsu())%>" disabled>
	<%}else{%>
		<input type="text" name="idUsuarioUsu" size="22" maxlength="20" value="<%=Pagina.trocaNull(usuario.getIdUsuarioUsu())%>">
	<%}%>
	</td>
</tr>
<%/*Mostrar o campo Classe de Acesso*/%> 
<%
ConfiguracaoCfg configuracao = (ConfiguracaoCfg)session.getAttribute("configuracao");
try {
	if(configuracao.getSisGrupoAtributoSgaByCodSgaGrAtrClAcesso() != null ){
		SisGrupoAtributoSga grupoClasseAcesso = configuracao.getSisGrupoAtributoSgaByCodSgaGrAtrClAcesso();
		Set lSelected = new HashSet();
		
		List atributosAcesso = usuarioDao.ordenaSet(grupoClasseAcesso.getSisAtributoSatbs(), "this.descricaoSatb", "asc");
		%>
		<tr>
			<td class="label" valign="top"><%=_obrigatorio%> <%=configuracao.getSisGrupoAtributoSgaByCodSgaGrAtrClAcesso().getDescricaoSga()%></td>
			<td>
			<combo:CheckListTag 
					nome="classeAcesso"
					objeto="ecar.pojo.SisAtributoSatb" 
					label="descricaoSatb" 
					value="codSatb" 
					order="descricaoSatb"
					filters="indAtivoSatb=S"
					scripts="<%=_disabled%>"
					selected="<%=usuarioDao.getClassesAcessoUsuarioById(usuario)%>"
					colecao="<%=atributosAcesso%>"
				/>		
			</td>
		</tr>
<%
	} 
} catch (Exception e) {
	org.apache.log4j.Logger.getLogger(this.getClass()).error(e);
}
%>

<%
	List grupoAtributoObrigatorios = new ArrayList();
	try {
		// In�cio da Implementa��o dos ATRIBUTOS LIVRES!
		//--------------------------------------------------
		SisGrupoAtributoDao sisGrupoAtributoDao = new SisGrupoAtributoDao(request);
		List atributosCadastro = sisGrupoAtributoDao.getGruposAtributosCadastro("U");
		List atributosUsuario;
		
		if(atributosCadastro != null){
				Iterator it = atributosCadastro.iterator();
				while(it.hasNext()) {
					SisGrupoAtributoSga grupoAtributo = (SisGrupoAtributoSga) it.next();
					if (grupoAtributo.getSisAtributoSatbs().size() > 0)
					{
						atributosUsuario = usuarioDao.getAtributosUsuarioByGrupo(usuario, grupoAtributo);
						String strDisabled = "";
						List selecionados = new ArrayList();	
						SisAtributoSatb sisAtributo = (SisAtributoSatb) grupoAtributo.getSisAtributoSatbs().iterator().next();					
						String nomeCampo = "a" + grupoAtributo.getCodSga().toString();
						String pathRaiz = request.getContextPath();

						if("disabled".equals(_disabled))
							strDisabled="S";						
						selecionados.addAll(atributosUsuario);
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
							sisAtributo="<%=sisAtributo%>"
							pathRaiz="<%=pathRaiz%>"
							size="20"
							sisGrupoAtributoSga="<%=grupoAtributo%>"
							nivelPlanejamentoCheckBox="<%=new Boolean(true)%>"
						> 
						<%
											
						List opcoes = new ArrayList();
						String selectedCodSapadrao = "";
						if(pesquisa){
							if(grupoAtributo.getSisTipoOrdenacaoSto() != null){
								opcoes = sisGrupoAtributoDao.getAtributosOrdenados(grupoAtributo);
							}
						}
						else{
							if(grupoAtributo.getCodSga() != null && grupoAtributo.getCodSga().longValue() != 1){
								if(grupoAtributo.getSisTipoOrdenacaoSto() != null){
									opcoes = sisGrupoAtributoDao.getAtributosOrdenados(grupoAtributo);
								}
							} 
							else{
								selectedCodSapadrao = configuracao.getSisAtributoSatbByCodSapadrao().getCodSatb().toString();
								//Comentado ref mantis 7259:
								//opcoes.addAll(configuracao.getSisGrupoAtributoSgaByCodSgaGrAtrPgIni().getSisAtributoSatbs());
								//ordenando os atributos conforme configura��o
								opcoes = sisGrupoAtributoDao.getAtributosOrdenados(configuracao.getSisGrupoAtributoSgaByCodSgaGrAtrPgIni());
							}
						}
						
						if ((pesquisa) || (!insere))
						{
						%>
							<ecar:Options
								options="<%=opcoes%>" 
								valor="codSatb"
								label="descricaoSatb"
								nivelPlanejamentoCheckBox="<%=new Boolean(true)%>"
								imagem="../../images/relAcomp/"
							/>					
					
						<%
						}else{
							if (grupoAtributo.getCodSga() != null && grupoAtributo.getCodSga().longValue() != 1){
						%>
							<ecar:Options
								options="<%=opcoes%>" 
								valor="codSatb"
								label="descricaoSatb"
								nivelPlanejamentoCheckBox="<%=new Boolean(true)%>"
								imagem="../../images/relAcomp/"
							/>					
						
						<%
						
							} else {
						%>
								<combo:ComboTag 
									nome="codSapadrao"
									objeto="ecar.pojo.SisAtributoSatb" 
									label="descricaoSatb" 
									value="codSatb" 
									order="descricaoSatb"
									selected="<%=selectedCodSapadrao%>"
									colecao="<%=opcoes%>"
									filters="indAtivoSatb=S"
									ignorarTagSelect="<%=new Boolean(true)%>"
								/>							
						<%

							}
						}
						%>
						</ecar:Input>
						<%
					}
			}
		}
	} catch (Exception e) {
		org.apache.log4j.Logger.getLogger(this.getClass()).error(e);
	}
	
	
	%>
<tr><td class="label">&nbsp;</td></tr>
 
<%@ include file="../../include/estadoMenu.jsp"%>

<script language="Javascript">
function validaCamposVariaveis(form){
	<%= new ecar.dao.SisGrupoAtributoDao(request).getValidacoesAtributos(grupoAtributoObrigatorios)%>
	return true;
}

function desabilitarSenha(form, desabilita) {
	document.form.senhaUsu.disabled=desabilita;
	document.form.confSenhaUsu.disabled=desabilita;
}
</script>
