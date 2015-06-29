<%
/*
 * arquivo comum para as telas de inclusao, alteracao, exclusao e pesquisa 
 * observar a variavel _disabled e setar o objeto corretamente pelo construtor
 * assinalando os campos com os valores iniciais para inclusao
 */ 
%>
<script language="javascript" src="../../js/limpezaCamposMultivalorados.js"></script>
<util:msgObrigatorio obrigatorio="<%=_obrigatorio%>" />

	<tr>
		<td class="label"><%=_obrigatorio%> Grupo</td>
		<td>
		<%if ( localItem.getLocalGrupoLgp() != null ) {%>
				<combo:ComboTag 
						nome="localGrupoLgp"
						objeto="ecar.pojo.LocalGrupoLgp" 
						label="identificacaoLgp" 
						value="codLgp" 
						order="identificacaoLgp" 
						selected="<%=localItem.getLocalGrupoLgp().getCodLgp().toString()%>"
						filters="indAtivoLgp=S"
						scripts="<%=_disabled%>"
				/>		
		<%} else {%>
				<combo:ComboTag 
						nome="localGrupoLgp"
						objeto="ecar.pojo.LocalGrupoLgp" 
						label="identificacaoLgp" 
						value="codLgp" 
						order="identificacaoLgp"
						filters="indAtivoLgp=S"
						scripts="<%=_disabled%>"
				/>		
		<%}%>
		</td>
	</tr>
	<tr>
		<td class="label"><%=_obrigatorio%> Descri&ccedil;&atilde;o</td>
		<td><input type="text" name="identificacaoLit" size="45" maxlength="40" value="<%=Pagina.trocaNull(localItem.getIdentificacaoLit())%>" <%=_disabled%>></td>
	</tr>
	<tr>
		<td class="label">C&oacute;digo IBGE</td>
		<td><input type="text" name="codIbgeLit" size="8" maxlength="6" value="<%=Pagina.trocaNull(localItem.getCodIbgeLit())%>" <%=_disabled%>></td>
	</tr>
	
	<tr>
		<td class="label">Sigla</td>
		<td><input type="text" name="siglaLit" size="10" maxlength="10" value="<%=Pagina.trocaNull(localItem.getSiglaLit())%>" <%=_disabled%>></td>
	</tr>	
			
	<tr>
		<td class="label">C&oacute;digo Planejamento</td>
		<td><input type="text" name="codPlanejamentoLit" size="8" maxlength="6" value="<%=Pagina.trocaNull(localItem.getCodPlanejamentoLit())%>" <%=_disabled%>></td>
	</tr>	

<%
//--------------------------------------------------
//     Implementação dos ATRIBUTOS DINÂMICOS
//--------------------------------------------------
SisGrupoAtributoDao grupoAtributoDao = new SisGrupoAtributoDao(request);
List grupoAtributoObrigatorios = new ArrayList();
List atributosCadastro = new SisGrupoAtributoDao(request).getGruposAtributosCadastro("L");
List atributosLocal;

	if(atributosCadastro != null){
		Iterator it = atributosCadastro.iterator();
		while(it.hasNext()) {
			SisGrupoAtributoSga grupoAtributo = (SisGrupoAtributoSga) it.next();

			if (grupoAtributo.getSisAtributoSatbs().size() > 0) {
				atributosLocal = localItemDao.getAtributosLocalByGrupo(localItem, grupoAtributo);
				String strDisabled = "";
				List selecionados = new ArrayList();	
				SisAtributoSatb atributo = (SisAtributoSatb) grupoAtributo.getSisAtributoSatbs().iterator().next();					
				String nomeCampo = "a" + grupoAtributo.getCodSga().toString();
				String pathRaiz = request.getContextPath();

				if("disabled".equals(_disabled))
					strDisabled="S";
				selecionados.addAll(atributosLocal);
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
//--------------------------------------------------
//   FIM - Implementação dos ATRIBUTOS DINÂMICOS
//--------------------------------------------------
%>

	<tr>
		<td class="label"><%=_obrigatorio%> Ativo</td>
		<td>
			<util:comboSimNaoTag nome="indAtivoLit" valorSelecionado="<%=localItem.getIndAtivoLit()%>" scripts="<%=_disabled%>" opcaoBranco="<%=_comboSimNao%>"/>
		</td>
	</tr>
		
	<tr><td class="label">&nbsp;</td></tr>
	
	<script>
	function validarCamposVariaveis(form){
		<%=grupoAtributoDao.getValidacoesAtributos(grupoAtributoObrigatorios)%>
		
		return(true);
	}
	</script>
	
	<%@ include file="../../include/estadoMenu.jsp"%>
