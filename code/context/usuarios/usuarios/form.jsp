<%@ taglib uri="/WEB-INF/tlds/c.tld" prefix="c"%> 

<jsp:directive.page import="ecar.util.Dominios"/>
<%@page import="gov.pr.celepar.sentinela.comunicacao.SentinelaInterface"%>
<%@page import="comum.database.SentinelaUtil"%>
<%@page import="gov.pr.celepar.sentinela.comunicacao.SentinelaParam"%><%@ taglib uri="/WEB-INF/ecar-util.tld" prefix="util" %>
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
<%@ page import="ecar.pojo.EntidadeEnt" %>

<script language="javascript" src="../../js/limpezaCamposMultivalorados.js"></script>

<%
		long codUsuarioSentinela = 0l;
		try{
			if (usuario.getIdDominioUsu()!=null && usuario.getIdDominioUsu().length()!=0  )
				codUsuarioSentinela = Long.parseLong(usuario.getIdDominioUsu() );
		} catch(NumberFormatException numExcep)	{
			codUsuarioSentinela=0l;	
		} 


 %>


<script>

function existeOrgao(codOrg)
{
	for(i = 1; i <= parseInt(document.getElementById('numOrgaos').value); i++)
	{
		if (document.getElementById("adicionaOrgaoOrg"+i).value == "S") 
		{	
			if(document.getElementById("orgaoOrg"+i).value == codOrg)
			{
				alert("Órgão já informado!");
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
		if (confirm("Os órgãos selecionados serão removidos. Confirma?")) {
			for (i = 1; i <= parseInt(document.getElementById('numOrgaos').value); i++) {
				if (document.getElementById("adicionaOrgaoOrg"+i).value == "S") {
					if (document.getElementById("chkOrgaoOrg"+i).checked == true)
						document.getElementById("orgao"+i).innerHTML = "<input type=\"hidden\" id=\"adicionaOrgaoOrg" + i +"\" name=\"adicionaOrgaoOrg" + i +"\" value=\"N\">\n";
				}
			}
			alert("Órgão(s) removido(s).");
		}
	} else {
		alert("Pelo menos um órgão deve ser selecionado");
	}
}

function adicionaOrgao (form)
{
	if (form.orgaoOrg.value == "")
	{
		alert ("Selecione um Órgão!");
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

function adicionaEntidade (form)
{
	if (form.entidadeEnt.value == "")
	{
		alert ("Selecione uma Entidade!");
	}
	else
	{
		contador = parseInt(form.numEntidades.value);
		if (!existeEntidade(form.entidadeEnt.value))
		{
			contador += 1;
			divConteudo  = "\n<!-- Entidade Adicionada dinamicamente -->";
			divConteudo += "\n<div id=\"entidade"+contador+ "\" name=\"entidade"+contador+"\">";		
			divConteudo += "\n<table>";		
			divConteudo += "\n<tr>";
			divConteudo += "\n\t<td>";
			divConteudo += "\n\t\t<input type=\"hidden\" id=\"adicionaEntidadeEnt"+contador+ "\" name=\"adicionaEntidadeEnt"+contador+"\" value=\"S\" >";
			divConteudo += "\n\t\t<input type=\"hidden\" id=\"entidadeEnt"+contador+ "\" name=\"entidadeEnt"+contador+"\" value=\""+form.entidadeEnt.value+"\" >";
			divConteudo += "\n\t\t<input type=\"checkbox\" id=\"chkEntidadeEnt"+contador+ "\" name=\"chkEntidadeEnt"+contador+"\" value=\""+form.entidadeEnt.value+"\" >";
			divConteudo += "\n\t\t"+form.entidadeEnt.options[form.entidadeEnt.selectedIndex].text;
			divConteudo += "\n\t</td>";
			divConteudo += "\n</tr>";
			divConteudo += "\n</table>";
			divConteudo += "\n</div>";
			document.getElementById("entidades").innerHTML += divConteudo;		
			divConteudo += "\n<!-- FIM de: Entidade Adicionada dinamicamente -->";
			form.numEntidades.value = contador;
			form.entidadeEnt.value = '';
			return true;
		}
	}
	
	return false;	
}

function existeEntidade(codEnt)
{
	for(i = 1; i <= parseInt(document.getElementById('numEntidades').value); i++)
	{
		if (document.getElementById("adicionaEntidadeEnt"+i).value == "S") 
		{	
			if(document.getElementById("entidadeEnt"+i).value == codEnt)
			{
				alert("Entidade já informada!");
				return true;
			}
		}
	}
	return false;
}

function removeEntidade() {
	var selecionados = 0;
	for (i = 1; i <= parseInt(document.getElementById('numEntidades').value); i++){
		if (document.getElementById("adicionaEntidadeEnt"+i).value == "S") {
			if (document.getElementById("chkEntidadeEnt"+i).checked == true) {
				selecionados++;
			}
		}
	}
		
	if (selecionados > 0) {
		if (confirm("As Entidades selecionadas serão removidas. Confirma?")) {
			for (i = 1; i <= parseInt(document.getElementById('numEntidades').value); i++) {
				if (document.getElementById("adicionaEntidadeEnt"+i).value == "S") {
					if (document.getElementById("chkEntidadeEnt"+i).checked == true)
						document.getElementById("entidade"+i).innerHTML = "<input type=\"hidden\" id=\"adicionaEntidadeEnt" + i +"\" name=\"adicionaEntidadeEnt" + i +"\" value=\"N\">\n";
				}
			}
			alert("Entidade(s) removida(s).");
		}
	} else {
		alert("Pelo menos uma entidade deve ser selecionada");
	}
}


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

<util:msgObrigatorio 
	obrigatorio="<%=_obrigatorio%>" 
 />

<input type="hidden" name="idDominioUsu" value="<%=Pagina.trocaNull(usuario.getIdDominioUsu())%>">

<tr>
	<td class="label"><%=_obrigatorio%> Usu&aacute;rio Ativo?</td>
	<td>
		<input type="radio" class="form_check_radio" name="indAtivoUsu" value="<%=Dominios.SIM%>" <%=Pagina.isChecked(usuario.getIndAtivoUsu(), Dominios.SIM)%> <%=_disabled%>>Sim
		<input type="radio" class="form_check_radio" name="indAtivoUsu" value="<%=Dominios.NAO%>" <%=Pagina.isChecked(usuario.getIndAtivoUsu(), Dominios.NAO)%> <%=_disabled%>>N&atilde;o
		
		<%
			if(pesquisa){
		%>
		<input type="button" value="Limpar" onclick="javascript:limparRadioButtons(indAtivoUsu)">
				<br>
		<%
			}
		 %>
	</td>
</tr>

<tr> 
	<td class="label"><%=_obrigatorio%> Nome</td>
	<td>
		<%if(!pesquisa){
		
		%>
			<input type="text" name="nomeUsu" size="55" maxlength="50" value="<%=Pagina.trocaNull(usuario.getNomeUsuSent())%>" disabled>
		<%}else{%>
			<input type="text" name="nomeUsu" size="55" maxlength="50" value="<%=Pagina.trocaNull(usuario.getNomeUsuSent())%>">
		<%}%>
		
		<%if(popUpPesquisa) {%>
			&nbsp;<input type=button class="botao" value=Pesquisar onclick="javascript:abrirPopUpSentinela()">
		<%} %>
		<%-- else{%>
			&nbsp;<input type=button class="botao" value=Pesquisar onclick="javascript:abrirPopUpSentinela()" disabled>
		<%}--%>					
	</td>
</tr>
<tr>
	<td class="label"><%=_obrigatorio%> &Oacute;rg&atilde;os </td>
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
			colecao="<%=listaOrgaos%>"
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
	

-->		</div>
	</td>	
</tr>	

<tr>
	<td class="label"> Entidades Solucionadoras</td>
	<td>
		<%if(!"disabled".equals(_disabled)){%>
		<!-- Falta colocar  filters="indOrgaoEnt=S" -->
		<combo:ComboTag 
			nome="entidadeEnt" 
			objeto="ecar.pojo.EntidadeEnt" 
			label="nomeEnt" 
			value="codEnt" 
			order="nomeEnt"
			scripts="<%=_disabled%>"
			selected=""
			filters=""
		/>&nbsp; 
			<a href="#" onclick="adicionaEntidade(form)">Adicionar</a> &nbsp;&nbsp; 
			<a href="#" onclick="removeEntidade()">Excluir</a>
		<%}%>&nbsp; 
	</td>
</tr>
<tr>
	<td class="label">&nbsp;</td>
	<td>
		<div id="entidades">
		<%
			j = 0;
			if((usuario.getEntidadeEnts()!=null) && (usuario.getEntidadeEnts().size() > 0)){
				Iterator itEntidade = usuario.getEntidadeEnts().iterator();
				EntidadeEnt selectedEntidade;
				while(itEntidade.hasNext())
				{
					j++;
					selectedEntidade = (EntidadeEnt)itEntidade.next();
				%>
			<div id="entidade<%=j%>" name="entidade<%=j%>">
			<table>  				
				<tr>
					<td>
						<input type="hidden" name="adicionaEntidadeEnt<%=j%>" id="adicionaEntidadeEnt<%=j%>"  value="S">
						<input type="hidden" name="entidadeEnt<%=j%>" id="entidadeEnt<%=j%>"  value=<%=selectedEntidade.getCodEnt()%> <%=_disabled%>>
						<input type="checkbox" name="chkEntidadeEnt<%=j%>" id="chkEntidadeEnt<%=j%>"  value=<%=selectedEntidade.getCodEnt()%> <%=_disabled%>>
						<%=selectedEntidade.getNomeEnt()%>
					</td>
				</tr>	
			</table>
			</div>
				<%}
			}%>
			<input type="hidden" name="numEntidades" id="numEntidades" value="<%=j%>">		
		</div>
	</td>	
</tr>	

<tr>
		<td class="label">Data de Nascimento</td>
		<td>
			<input type="text" name="dataNascimentoUsu" size="15" maxlength="10" value="<%=Pagina.trocaNullData(usuario.getDataNascimentoUsu())%>" <%=_disabled%> onkeyup="mascaraData(event, this);">
			<c:if test="<%=_disabled.equals("")%>">
				<img class="posicao" title="Selecione a data" src="../../images/icone_calendar.gif" onclick="open_calendar('data', document.forms[0].dataNascimentoUsu, '')">
			</c:if>
		</td>
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
	<%if(!pesquisa){
	%>
		<input type="text" name="idUsuarioUsu" size="22" maxlength="20" value="<%=Pagina.trocaNull(usuario.getIdUsuarioUsu())%>" disabled>
	<%}else{%>
		<input type="text" name="idUsuarioUsu" size="22" maxlength="20" value="<%=Pagina.trocaNull(usuario.getIdUsuarioUsu())%>">
	<%}%>
	</td>
</tr>

	<%
		if (codUsuarioSentinela!=0l ) {
		
			SentinelaInterface sentinela = SentinelaUtil.getSentinelaInterface();
			
			 /*Todos os grupos do sistema */
			SentinelaParam[] grupos =   sentinela.getGeralGrupos();
			
			/*Grupos pertencentes ao usuário */
			SentinelaParam[] vinculados =sentinela.getGruposByUsuario(codUsuarioSentinela );
			
			/*Grupos não pertencentes ao usuário */   
			SentinelaParam[] naoVinculados = SentinelaUtil.getGruposNaoVinculados(grupos, vinculados);

	%>
	<tr> 
		<td colspan="2">
		  <center>
		    <table style="width: 450px; ">
			 <tr>
			 	
		   		<td align="center" class="label" style="text-align: center; ">Grupos vinculados no Sentinela</td>
		   		<td>&nbsp;</td>
		     	<td align="center" class="label" style="text-align: center;">Grupos não vinculados no Sentinela</td>
			 </tr>
			 <tr>
		   		<td>
		       	  <select name="vinculandos" id="vinculandos_esquerda" class="lista" multiple="true" <%=_disabled%> >
				<%	for (int k =0; k<vinculados.length;k++){  %>
					 <option value="<%=vinculados[k].getCodigo() %>" ><%= vinculados[k].getNome()%> </option>
				<%} %>
		       	  </select>
		   		</td>
		   		<td valign="middle">
			    	<div align="center">
						<input name="btnExcluir" type="button" id="btnExcluir" onClick="adicionarMarcados(document.getElementById('vinculandos_esquerda'), document.getElementById('naoVinculandos_direita'))"  value="&gt; &gt;">
						<br><br>
						<input name="btnIncluir" id="btnIncluir" type="button" onClick="adicionarMarcados(document.getElementById('naoVinculandos_direita'), document.getElementById('vinculandos_esquerda'))"value="&lt; &lt;">
					</div>
		  		</td>
		     	<td>
		   	      <select name="naoVinculandos" id="naoVinculandos_direita" class="lista" multiple="true" <%=_disabled%> >
					<%	for (int k =0; k<naoVinculados.length;k++){  %>
						 <option value="<%=naoVinculados[k].getCodigo() %>" ><%= naoVinculados[k].getNome()%> </option>
					<%} %>
				  </select>
		   		</td>
			  </tr>
		  </table>
		</center>
	  </td>
	</tr>

	<%
	} //fim if codUsuarioSentinela;/
	%>


<%/*Mostrar o campo Classe de Acesso*/%> 
<%

	ConfiguracaoCfg conf = new ConfiguracaoDao(request).getConfiguracao();
try {
	if(conf.getSisGrupoAtributoSgaByCodSgaGrAtrClAcesso() != null ){
		SisGrupoAtributoSga grupoClasseAcesso = conf.getSisGrupoAtributoSgaByCodSgaGrAtrClAcesso();
		Set lSelected = new HashSet();
		
		List atributosAcesso = usuarioDao.ordenaSet(grupoClasseAcesso.getSisAtributoSatbs(), "this.descricaoSatb", "asc");
		%>
		<tr>
			<td class="label" valign="top"><%=_obrigatorio%> <%=conf.getSisGrupoAtributoSgaByCodSgaGrAtrClAcesso().getDescricaoSga()%></td>
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
		// Início da Implementação dos ATRIBUTOS LIVRES!
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
							tipo="<%=Integer.valueOf(grupoAtributo.getSisTipoExibicGrupoCadUsuSteg().getCodSteg().toString()).intValue()%>"
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
								selectedCodSapadrao = conf.getSisAtributoSatbByCodSapadrao().getCodSatb().toString();
								//Comentado ref mantis 7259:
								//opcoes.addAll(configuracao.getSisGrupoAtributoSgaByCodSgaGrAtrPgIni().getSisAtributoSatbs());
								//ordenando os atributos conforme configuração
								opcoes = sisGrupoAtributoDao.getAtributosOrdenados(conf.getSisGrupoAtributoSgaByCodSgaGrAtrPgIni());
							}
						}
						
						//se não for inserção
						if ((pesquisa) || (!insere))
						{
						%>
							<ecar:Options
								options="<%=opcoes%>" 
								valor="codSatb"
								label="descricaoSatb"
								nivelPlanejamentoCheckBox="<%=new Boolean(true)%>"
								imagem="../../images/relAcomp/"
								nome="<%=nomeCampo%>"
							/>					
					
						<%
						//se grupo de atributo não for nulo nem for Pagina Inicial
						}else{
							if (grupoAtributo.getCodSga() != null && grupoAtributo.getCodSga().longValue() != 1){
						%>
							<ecar:Options
								options="<%=opcoes%>" 
								valor="codSatb"
								label="descricaoSatb"
								nivelPlanejamentoCheckBox="<%=new Boolean(true)%>"
								imagem="../../images/relAcomp/"
								nome="<%=nomeCampo%>"
							/>					
						
						<%
						//se grupo de atributo for Pagina Inicial
							} else {
						%>
								<combo:ComboTag 
									nome="codSapadrao"
									objeto="ecar.pojo.SisAtributoSatb" 
									label="descricaoSatb" 
									value="codSatb" 
									order="descricaoSatb"
									selected="<%=""%>"
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
	<%= new ecar.dao.SisGrupoAtributoDao(request).getValidacoesAtributos(grupoAtributoObrigatorios, true)%>
	return true;
}

function desabilitarSenha(form, desabilita) {
	document.form.senhaUsu.disabled=desabilita;
	document.form.confSenhaUsu.disabled=desabilita;
}
</script>
