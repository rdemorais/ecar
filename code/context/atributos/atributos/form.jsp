<%@ page import="ecar.pojo.SisGrupoAtributoSga"%>
<%@ page import="ecar.pojo.SisTipoExibicGrupoSteg"%>
<%@ page import="ecar.taglib.util.Input"%>
<%@ page import="ecar.dao.SisGrupoAtributoDao"%>
<%@ page import="java.util.List" %>
<%@ page import="java.util.Iterator" %>

<%@ taglib uri="/WEB-INF/tlds/c.tld" prefix="c"%>

<%
/*
 * arquivo comum para as telas de inclusao, alteracao, exclusao e pesquisa 
 * observar a variavel _disabled e setar o objeto corretamente pelo construtor
 * assinalando os campos com os valores iniciais para inclusao
 */ 
 
 SisTipoExibicGrupoSteg sisTipoExibicGrupoSteg = new SisTipoExibicGrupoSteg();
 sisTipoExibicGrupoSteg.setCodSteg(new Long(Input.VALIDACAO));
 
 SisGrupoAtributoSga sisGrupoAtributoSga = new SisGrupoAtributoSga();
 sisGrupoAtributoSga.setSisTipoExibicGrupoSteg(sisTipoExibicGrupoSteg);
 
 SisGrupoAtributoDao sisGrupoAtributoDao = new SisGrupoAtributoDao();
 
 List teste = sisGrupoAtributoDao.pesquisar(sisGrupoAtributoSga, new String[]{"sisTipoExibicGrupoSteg", "asc"});
 
 Iterator it = teste.iterator();
 String codigosSisGrupoValidacao = "";
 while (it.hasNext()) {
 	sisGrupoAtributoSga = (SisGrupoAtributoSga) it.next(); 	
 	codigosSisGrupoValidacao += sisGrupoAtributoSga.getCodSga() + "_";
  }
%>
    
<%@page import="comum.util.EcarCfg"%><INPUT type="hidden" name="codigosSisGrupoValidacao" id="codigosSisGrupoValidacao" value="<%=codigosSisGrupoValidacao%>">
    
	<INPUT type="hidden" name="indTabelaUso" value="<%=indTabelaUso%>">	
	<util:msgObrigatorio obrigatorio="<%=_obrigatorio%>"/>
	
	<tr>
		<td class="label"><%=_obrigatorio%> Grupo de Atributos</td>
		<td>
<%
		String filtro = "indAtivoSga=S;indTabelaUsoSga=" + indTabelaUso;
		String scriptsCombo=_disabled + " onchange=\"aoMudarTipoValidacao()\"";
		if (atributo.getSisGrupoAtributoSga() != null){
%>			<combo:ComboTag 
					nome="sisGrupoAtributoSga"
					objeto="ecar.pojo.SisGrupoAtributoSga"
					label="descricaoSga"
					value="codSga"
					order="descricaoSga"
					filters="<%=filtro%>"
					selected="<%=atributo.getSisGrupoAtributoSga().getCodSga().toString()%>"
					scripts="<%=scriptsCombo %>" 
				/>
<%
		}else{
%>
			<combo:ComboTag 
					nome="sisGrupoAtributoSga"
					objeto="ecar.pojo.SisGrupoAtributoSga"
					label="descricaoSga"
					value="codSga"
					order="descricaoSga"
					filters="<%=filtro%>"
					scripts="<%=scriptsCombo%>"
				/>
<%
		}
%>
		</td>
	</tr>
	<tr>
		<td class="label"><%=_obrigatorio%> Descri&ccedil;&atilde;o</td>
		<td><input type="text" name="descricaoSatb" size="42" maxlength="100" value="<%=Pagina.trocaNull(atributo.getDescricaoSatb())%>" <%=_disabled%>></td>	
	</tr>		
				
	 	<tr id="textAreaInformacaoComplementar" style="display:">
			<td class="label" <% if (atributo.getSisGrupoAtributoSga() != null){ %> title="<%=Pagina.trocaNull(atributo.getSisGrupoAtributoSga().getDescInfoComplementar())%>" <% } %>>Informa&ccedil;&otilde;es Complementares</td>
			<td><textarea name="atribInfCompSatb" cols="60" rows="4" 
				onkeyup="return validaTamanhoLimite(this, 2000);" 
				onkeydown="return validaTamanhoLimite(this, 2000);"
				onblur="return validaTamanhoLimite(this, 2000);"
				<%=_disabled%>><%=Pagina.trocaNull(atributo.getAtribInfCompSatb())%></textarea>
			</td>
		</tr>

		<tr id="comboTipoValidacao" style="display:none">
			<td class="label" <% if (atributo.getSisGrupoAtributoSga() != null){ %> title="<%=Pagina.trocaNull(atributo.getSisGrupoAtributoSga().getDescInfoComplementar())%>" <% } %>>Tipo de Valida&ccedil;&atilde;o</td>		
			<td><select name="atribInfCompSatb2" <%=_disabled%> onchange="javascript:habilitaPanelsID();" id="atribInfCompSatb2"> 				
				<option value="" <%	if(atributo.getSisGrupoAtributoSga() != null )
										if(Pagina.trocaNull(atributo.getAtribInfCompSatb()).equals(""))
 											{%>selected <%} %>></option>
				
				<option value="dataScript" <% if(atributo.getSisGrupoAtributoSga() != null )
										if(Pagina.trocaNull(atributo.getAtribInfCompSatb()).equals("dataScript"))
 										{%>selected <%} %>>Data</option>
				
				<option value="numeroInteiroScript" <%	if(atributo.getSisGrupoAtributoSga() != null )
										if(Pagina.trocaNull(atributo.getAtribInfCompSatb()).equals("numeroInteiroScript"))
 										{%>selected <%} %>>Número Inteiro</option>
				
				<option value="numeroRealScript"  <%	if(atributo.getSisGrupoAtributoSga() != null )
										if(Pagina.trocaNull(atributo.getAtribInfCompSatb()).equals("numeroRealScript"))
 										{%>selected <%} %>>Número Real</option>
				
				<option value="cpfScript"  <%	if(atributo.getSisGrupoAtributoSga() != null )
										if(Pagina.trocaNull(atributo.getAtribInfCompSatb()).equals("cpfScript"))
 										{%>selected <%} %>>CPF</option>
 				
 				<option value="cnpjScript"  <%	if(atributo.getSisGrupoAtributoSga() != null )
										if(Pagina.trocaNull(atributo.getAtribInfCompSatb()).equals("cnpjScript"))
 										{%>selected <%} %>>CNPJ</option>
				
				<option value="emailScript"  <%	if(atributo.getSisGrupoAtributoSga() != null )
										if(Pagina.trocaNull(atributo.getAtribInfCompSatb()).equals("emailScript"))
 										{%>selected <%} %>>Email</option> 										
				
				<option value="valorMonetarioScript" <%	if(atributo.getSisGrupoAtributoSga() != null )
										if(Pagina.trocaNull(atributo.getAtribInfCompSatb()).equals("valorMonetarioScript"))
 										{%>selected <%} %>>Valor Monetário</option>

				<% String origem = (String)session.getAttribute("indTabelaUso");
					if (origem.equals("T") ) { %>
						<option value="autoIncrementalScript" 
								<%	if(atributo.getSisGrupoAtributoSga() != null ) {
										if(Pagina.trocaNull(atributo.getAtribInfCompSatb()).equals("autoIncrementalScript")){%>
											selected
										<% } %>			 
								<% } %>>
								Auto Incremental
						</option>
						<option value="mascaraScript" 
								<%	if(atributo.getSisGrupoAtributoSga() != null ) {
										if(Pagina.trocaNull(atributo.getAtribInfCompSatb()).equals("mascaraScript")) {%>
											selected
										<% } %> 
								<% } %>>
								Campo com Máscara
		 				</option>
						<option value="mascaraEditavelScript" 
								<%	if(atributo.getSisGrupoAtributoSga() != null ) {
										if(Pagina.trocaNull(atributo.getAtribInfCompSatb()).equals("mascaraEditavelScript")) {%>
											selected
										<% } %> 
								<% } %>>
								Campo com Máscara Editável
		 				</option>	 											 											 													
					<% } %>
			</select>
			</td>		
		</tr>	
	 	<tr id="labelIncremental" style="display:none">
			<td class="label" >Incremental</td>
			<td>&nbsp;</td>
		</tr>					
	 	<tr id="geralPanel" style="display:none">
			<td class="label"><%=_obrigatorio%> Geral</td>
			<td>
				<c:set scope="request" value="<%=atributo%>" var="atributoReq"/>
				<c:choose>
					<c:when test="${atributoReq.geral == null}">
						<input type="radio" name="geral" value="true" <%=_disabled%>>Sim
						&nbsp;
						<input type="radio" name="geral" value="false" <%=_disabled%>>Não
					</c:when>
					<c:when test="${atributoReq.geral}">
						<input type="radio" name="geral" value="true" checked="checked" <%=_disabled%>>Sim
						&nbsp;
						<input type="radio" name="geral" value="false" <%=_disabled%>>Não
					</c:when>
					<c:otherwise>
						<input type="radio" name="geral" value="true" <%=_disabled%>>Sim
						&nbsp;
						<input type="radio" name="geral" value="false" checked="checked" <%=_disabled%>>Não
					</c:otherwise>
				</c:choose>
			</td>
		</tr>
	 	<tr id="periodicoPanel" style="display:none">
			<td class="label" ><%=_obrigatorio%> Per&iacute;odico (anual)</td>
			<td>
				<c:choose>
					<c:when test="${atributoReq.periodico == null}">
						<input type="radio" name="periodico" value="true" <%=_disabled%>>Sim
						&nbsp;
						<input type="radio" name="periodico" value="false" <%=_disabled%>>Não
					</c:when>
					<c:when test="${atributoReq.periodico}">
						<input type="radio" name="periodico" value="true" checked="checked" <%=_disabled%>>Sim
						&nbsp;
						<input type="radio" name="periodico" value="false" <%=_disabled%>>Não
					</c:when>
					<c:otherwise>
						<input type="radio" name="periodico" value="true" <%=_disabled%>>Sim
						&nbsp;
						<input type="radio" name="periodico" value="false"checked="checked" <%=_disabled%>>Não
					</c:otherwise>
				</c:choose>
			</td>
		</tr>		
		<tr>
			<td class="label"><%=_obrigatorio%> Ativo</td>
			<td>
				<util:comboSimNaoTag nome="indAtivoSatb" valorSelecionado="<%=atributo.getIndAtivoSatb()%>" scripts="<%=_disabled%>" opcaoBranco="<%=_comboSimNao%>"/>		
			</td>
		</tr>
	 	<tr id="periodicoMascara" style="display:none">
			<td class="label" ><%=_obrigatorio%>  M&aacute;scara </td>
			<td>
				<c:choose>
					<c:when test="${atributoReq.mascara == null}">
						<input type="text" name="mascara" size="42" maxlength="40" value="" <%=_disabled%>>
					</c:when>
					<c:otherwise>
						<input type="text" name="mascara" size="42" maxlength="40" value="${atributoReq.mascara}" <%=_disabled%>>
					</c:otherwise>
				</c:choose>
			</td>
		</tr>		
	 	<tr id="labelCaracteresMascara" style="display:none">
			<td ></td>
			<td>
				<%=EcarCfg.getConfiguracao("caracter.mascara.ano")%> - 4 Caracteres correspondentes ao ano<br>
				<%=EcarCfg.getConfiguracao("caracter.mascara.mes")%> - 2 Caracteres correspondentes ao mês<br>
				<%=EcarCfg.getConfiguracao("caracter.mascara.sequencial")%> - N Caracter(es) correspondentes a parte incremental
			</td>
		</tr>				
	<tr><td class="label">&nbsp;</td></tr>

	<%@ include file="../../include/estadoMenu.jsp"%>
	
	
	<script> 
	
	function aoMudarTipoValidacao(){				
		if(eValidacao(document.getElementById('codigosSisGrupoValidacao').value)) {																	
				document.getElementById('textAreaInformacaoComplementar').style.display='none';																					
				document.getElementById('comboTipoValidacao').style.display='';
		} else  {			
				document.getElementById('textAreaInformacaoComplementar').style.display='';
				document.getElementById('comboTipoValidacao').style.display='none';															

				document.getElementById('atribInfCompSatb2').value = '';
								
				habilitaPanelsID();
		}		
	}


	function habilitaPanelsID(){

		var campo = document.getElementById('atribInfCompSatb2');
		
		if (campo.value == 'mascaraScript' || campo.value == 'mascaraEditavelScript') {
			document.getElementById('labelIncremental').style.display='';
			document.getElementById('geralPanel').style.display='';
			document.getElementById('periodicoPanel').style.display='';
			document.getElementById('periodicoMascara').style.display='';
			document.getElementById('labelCaracteresMascara').style.display='';
		} else if (campo.value == 'autoIncrementalScript') {
			document.getElementById('labelIncremental').style.display='';
			document.getElementById('geralPanel').style.display='';
			document.getElementById('periodicoPanel').style.display='';
			document.getElementById('periodicoMascara').style.display='none';
			document.getElementById('labelCaracteresMascara').style.display='none';
		} else {
			document.getElementById('labelIncremental').style.display='none';
			document.getElementById('geralPanel').style.display='none';
			document.getElementById('periodicoPanel').style.display='none';
			document.getElementById('periodicoMascara').style.display='none';
			document.getElementById('labelCaracteresMascara').style.display='none';
		}
	}

	
	function eValidacao(codigosSisGrupoValidacao){
		retorno = false;
		codigosSisGrupoValidacao = String(codigosSisGrupoValidacao);
		var cod;
		
		while(codigosSisGrupoValidacao.indexOf("_") != codigosSisGrupoValidacao.lastIndexOf("_")){
				cod = codigosSisGrupoValidacao.substr(0,codigosSisGrupoValidacao.indexOf("_"));
				if(document.getElementById('sisGrupoAtributoSga').value == cod){				
					retorno=true;
					break;
				}
				codigosSisGrupoValidacao = codigosSisGrupoValidacao.substr((codigosSisGrupoValidacao.indexOf("_")+1),codigosSisGrupoValidacao.length);
		}
		
		if(codigosSisGrupoValidacao.indexOf("_") == codigosSisGrupoValidacao.lastIndexOf("_")){			
			if(codigosSisGrupoValidacao.length==0)
				retorno=false;
			else{				
				cod = codigosSisGrupoValidacao.substr(0,codigosSisGrupoValidacao.lastIndexOf("_"));				
				if(document.getElementById('sisGrupoAtributoSga').value == cod){				
					retorno=true;										
				}	
			}				
		}		
		return retorno;
	}
	
	function habilitaDesabled(id){
		document.getElementById(id).disabled = false;
	}

	</script>