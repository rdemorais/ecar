<%@ taglib uri="/WEB-INF/ecar-combo.tld" prefix="combo" %>
<%
/*
 * arquivo comum para as telas de inclusao, alteracao, exclusao e pesquisa 
 * observar a variavel _disabled e setar o objeto corretamente pelo construtor
 * assinalando os campos com os valores iniciais para inclusao
 */ 
%>

<script language="javascript">

function desabilitar(){
	document.form.tituloMapaPgn.disabled=true;
	document.form.descricaoMapaPgn.disabled=true}

function habilitar(){
	document.form.tituloMapaPgn.disabled=false;
	document.form.descricaoMapaPgn.disabled=false
}

</script>


<util:msgObrigatorio obrigatorio="<%=_obrigatorio%>" />

	<tr>
	<td class="label"><%=_obrigatorio%> &Aacute;rea do Site</td>
		<td>
		<%if (paginaPgn.getPaginaAreaSitePa() != null ) {%>
				<combo:ComboTag 
						nome="paginaAreaSitePa"
						objeto="ecar.pojo.PaginaAreaSitePa" 
						label="nomePas" 
						value="codPas" 
						order="nomePas"
						selected="<%=paginaPgn.getPaginaAreaSitePa().getCodPas().toString()%>"
						scripts="<%=_disabled%>"
				/>		
		<%}else {%>
				<combo:ComboTag 
						nome="paginaAreaSitePa"
						objeto="ecar.pojo.PaginaAreaSitePa" 
						label="nomePas" 
						value="codPas" 
						order="nomePas"
						scripts="<%=_disabled%>"
				/>		
		<%}%>
		</td>
	</tr>
	<tr>
		<td class="label"><%=_obrigatorio%> Nome da P&aacute;gina</td>
		<td><input type="text" name="nomePgn" size="35" maxlength="30" value="<%=Pagina.trocaNull(paginaPgn.getNomePgn())%>" <%=_disabled%>></td>
	</tr>
<tr>
		<td class="label">P&aacute;gina Anterior</td>
		<td>
		<%if (paginaPgn.getPaginaPgn() != null ) {%>
			<combo:ComboTag 
					nome="paginaPgn"
					objeto="ecar.pojo.PaginaPgn" 
					label="nomePgn" 
					value="codPgn" 
					order="nomePgn"
					selected="<%=paginaPgn.getPaginaPgn().getCodPgn().toString()%>"
					scripts="<%=_disabled%>"
				/>		
		<%}else {%>
			<combo:ComboTag 
					nome="paginaPgn"
					objeto="ecar.pojo.PaginaPgn" 
					label="nomePgn" 
					value="codPgn" 
					order="nomePgn"
					scripts="<%=_disabled%>"
				/>		
		<%}%>
		</td>
	</tr>
	<tr>
		<td class="label"><%=_obrigatorio%> T&iacute;tulo da P&aacute;gina</td>
		<td><textarea name="tituloPgn" rows="4" cols="60" onkeyup="return validaTamanhoLimite(this, 2000);"
			onkeyup="return validaTamanhoLimite(this, 2000);"
			onkeydown="return validaTamanhoLimite(this, 2000);"
			onblur="return validaTamanhoLimite(this, 2000);" 
			<%=_readOnly%>><%=Pagina.trocaNull(paginaPgn.getTituloPgn())%></textarea></td>
	</tr>
	<tr>
		<td class="label"><%=_obrigatorio%> Idioma</td>
		<td>
		<%if (paginaPgn.getIdiomaIdm() != null ) {%>
			<combo:ComboTag 
					nome="idiomaIdm"
					objeto="ecar.pojo.IdiomaIdm" 
					label="nomeIdm" 
					value="codIdm" 
					order="nomeIdm"
					selected="<%=paginaPgn.getIdiomaIdm().getCodIdm().toString()%>"
					scripts="<%=_disabled%>"
		/>		
		<%} else {%>
				<combo:ComboTag 
					nome="idiomaIdm"
					objeto="ecar.pojo.IdiomaIdm" 
					label="nomeIdm" 
					value="codIdm" 
					order="nomeIdm"
					scripts="<%=_disabled%>"
		/>		
		<%}%>
		</td>
	</tr>
	<tr>
		<td class="label"><%=_obrigatorio%> URL</td>
		<td><input type="text" name="urlPgn" size="89" maxlength="100" value="<%=Pagina.trocaNull(paginaPgn.getUrlPgn())%>" <%=_disabled%>></td>
	</tr>
	<tr>
	<td class="label">Par&acirc;metros</td>
		<td><input type="text" name="parametrosPgn" size="25" maxlength="20" value="<%=Pagina.trocaNull(paginaPgn.getParametrosPgn())%>" <%=_disabled%>></td>
	</tr>
	<tr>
		<td class="label"><%=_obrigatorio%> &Eacute; p&aacute;gina Home</td>
		<td>
			<util:comboSimNaoTag nome="indHomePgn" valorSelecionado="<%=paginaPgn.getIndHomePgn()%>" scripts="<%=_disabled%>" opcaoBranco="<%=_comboSimNao%>"/>
		</td>
	</tr>
	<tr>
		<td class="label"><%=_obrigatorio%> &Eacute; p&aacute;gina de Login</td>
		<td>
			<util:comboSimNaoTag nome="indLoginPgn" valorSelecionado="<%=paginaPgn.getIndLoginPgn()%>" scripts="<%=_disabled%>" opcaoBranco="<%=_comboSimNao%>"/>		
		</td>
	</tr>
	<tr>
		<td class="label"><%=_obrigatorio%> P&aacute;gina aparece no Mapa do Site</td>
		<td>					
			<util:comboSimNaoTag nome="indMapaPgn" valorSelecionado="<%=paginaPgn.getIndMapaPgn()%>" scripts="<%=_disabled%>" opcaoBranco="<%=_comboSimNao%>"/>
		</td>
	</tr>
	<tr>
		<td class="label"><%=_obrigatorio%> T&iacute;tulo do Mapa</td>
		<td><input type="text" name="tituloMapaPgn" id="tituloMapaPng" size="25" maxlength="20" value="<%=Pagina.trocaNull(paginaPgn.getTituloMapaPgn())%>" <%=_disabled%>></td>
	</tr>
	<tr>
	<td class="label"><%=_obrigatorio%> Descri&ccedil;&atilde;o do Mapa</td>
		<td><textarea name="descricaoMapaPgn" id="descricaoMapaPgn" rows="4" cols="60" onkeyup="return validaTamanhoLimite(this, 2000);" <%=_readOnly%>><%=Pagina.trocaNull(paginaPgn.getDescricaoMapaPgn())%></textarea></td>
	</tr>
	<tr>
		<td class="label"><%=_obrigatorio%> Qtde. de Sub-&Aacute;reas</td>
		<td><input type="text" name="qtdSubAreaPgn" size="10" maxlength="4" value="<%=Pagina.trocaNull(paginaPgn.getQtdSubAreaPgn())%>" <%=_disabled%>></td>
	</tr>
	
	<!-- Se <indMapaPgn> == FALSE desabilita campos TiutloMapa e DescriçãoMapa -->
	<script language="javascript">
		if(document.form.indMapaPgn[1].checked == true){
			desabilitar();
		}
	</script>
	<% /*  controla o estado do menu (aberto ou fechado) */%>
	<%@ include file="../../include/estadoMenu.jsp" %>