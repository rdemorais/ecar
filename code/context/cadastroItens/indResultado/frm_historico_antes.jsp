<%@ taglib uri="/WEB-INF/tlds/c.tld" prefix="c"%>

<%@ page import="ecar.dao.SisGrupoAtributoDao" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.Set"%>
<%@ page import="ecar.dao.ConfiguracaoDao" %>
<%@ page import="ecar.dao.ItemEstrtIndResulDao" %>
<%@ page import="ecar.pojo.SisGrupoAtributoSga" %>
<%@ page import="ecar.pojo.SisAtributoSatb" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="ecar.pojo.AtributoLivre" %>

<%@ page import="ecar.pojo.ItemEstrtIndResulCorIettrcor" %>
<%@ page import="ecar.dao.CorDao" %>
<%@ page import="ecar.pojo.Cor" %>
<%@ page import="ecar.pojo.ItemEstrtIndResulCorIettrcorPK" %>
<%@ page import="ecar.dao.ItemEstrtIndResulCorIettrcorDAO" %>



<%@page import="ecar.pojo.ConfiguracaoCfg"%>
<%@page import="ecar.pojo.UsuarioUsu"%>
<%@page import="comum.util.Util"%><jsp:directive.page import="java.util.Iterator"/>
<jsp:directive.page import="ecar.pojo.ItemEstrtIndResulIettr"/>
<jsp:directive.page import="ecar.dao.ServicoDao"/>
<jsp:directive.page import="ecar.pojo.ServicoSer"/>


<script language="javascript" src="../../js/form_cadItem_indresultado.js"></script> 
<!--  <script language="javascript" src="../../js/prototype.js"></script> -->	
<script language="javascript" src="../../js/popup.js"></script>	
<script language="javascript" src="../../js/datas.js"></script>	

<script language="javascript" src="../../js/jquery.js"></script>
<script language="javascript" src="../../js/jquery_autocomplete.js"></script>
<link rel="stylesheet" href="../../css/jquery_autocomplete.css" type="text/css">

<jsp:directive.page import="comum.util.Pagina"/>
<jsp:directive.page import="ecar.pojo.PeriodicidadePrdc"/>

	<input type="hidden" name="codIett" id="codIett" value="<%=itemEstrutura.getCodIett()%>">
	<input type="hidden" name="codAba"  id="codAba"  value='<%=Pagina.getParamStr(request, "codAba")%>'>
	<input type="hidden" name="codHistAntes"  id="codHistAntes"  value="<%=Pagina.getParamStr(request, "codHistAntes")%>">
	<input type="hidden" name="codHistDepois" id="codHistDepois" value="<%=Pagina.getParamStr(request, "codHistDepois")%>">
	<input type="hidden" name="codHist" id="codHist" value="<%=Pagina.getParamStr(request, "codHist")%>">
	<input type="hidden" name="hidAcao" id="hidAcao" value="">
	<input type="hidden" name="popup"   id="popup"   value=""/>



	<tr>
		<td class="label">* Tipo</td>
		<td><input type="text" name="nomeIettir1" id="nomeIettir1" maxlength="30" size="35" value="<%=Pagina.trocaNull(itemEstrtIndResulAntes.getNomeIettir())%>" <%=_disabled%>></td>
	</tr>

	<%
	// Início da Implementação dos ATRIBUTOS LIVRES!
	//--------------------------------------------------
	List grupoMetasObrigatorios = new ArrayList();
		try{
			SisGrupoAtributoSga grupoMetas = configura.getSisGrupoAtributoSgaByCodSgaGrAtrMetasFisicas();
			SisGrupoAtributoDao sisGrupoAtributoDao = new SisGrupoAtributoDao(request);
			if(grupoMetas != null){
				if (grupoMetas.getSisAtributoSatbs().size() > 0)
				{
					
					String strDisabled = "";
					List selecionados = new ArrayList();	
					SisAtributoSatb sisAtributo = (SisAtributoSatb) grupoMetas.getSisAtributoSatbs().iterator().next();					
					String nomeCampo = "a" + grupoMetas.getCodSga().toString() +"1";
					String pathRaiz = request.getContextPath();

					if("disabled".equals(_disabled))
						strDisabled="S";						

					if(itemEstrtIndResulAntes != null && itemEstrtIndResulAntes.getSisAtributoSatb() != null){
						AtributoLivre atbLivre = new AtributoLivre();
						atbLivre.setSisAtributoSatb(itemEstrtIndResulAntes.getSisAtributoSatb());
						selecionados.add(atbLivre);
					}
					
					if("S".equals(grupoMetas.getIndObrigatorioSga())){
						grupoMetasObrigatorios.add(grupoMetas);
					}
					%>
					<ecar:Input
						label="<%=grupoMetas.getDescricaoSga()%>"
						tipo="<%=Integer.valueOf(grupoMetas.getSisTipoExibicGrupoSteg().getCodSteg().toString()).intValue()%>"
						obrigatorio="<%=grupoMetas.getIndObrigatorioSga()%>"
						labelObrigatorio="<%=_obrigatorio%>"
						disabled="<%=strDisabled%>"
						nome="<%=nomeCampo%>"
						classLabel="label"
						selecionados="<%=selecionados%>"
						sisAtributo="<%=sisAtributo%>"
						pathRaiz="<%=pathRaiz%>"
						size="20"
						sisGrupoAtributoSga="<%=grupoMetas%>"
					> 
					<%
										
					List opcoes = new ArrayList();
					String selectedCodSapadrao = "";
					if(grupoMetas.getCodSga() != null && grupoMetas.getCodSga().longValue() != 1){
						if(grupoMetas.getSisTipoOrdenacaoSto() != null){
							opcoes = sisGrupoAtributoDao.getAtributosOrdenados(grupoMetas);
						}
					} 
					else{
						selectedCodSapadrao = configura.getSisAtributoSatbByCodSapadrao().getCodSatb().toString();
						opcoes = sisGrupoAtributoDao.getAtributosOrdenados(configura.getSisGrupoAtributoSgaByCodSgaGrAtrPgIni());
					}

					if (grupoMetas.getCodSga() != null && grupoMetas.getCodSga().longValue() != 1){
					%>
						<ecar:Options
							options="<%=opcoes%>" 
							valor="codSatb"
							label="descricaoSatb"
							nivelPlanejamentoCheckBox="<%=new Boolean(true)%>"
							imagem="../../images/relAcomp/"
							nome="<%=nomeCampo %>"
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
					%>
					</ecar:Input>
					<%
				}
			}
		} catch (Exception e) {
			org.apache.log4j.Logger.getLogger(this.getClass()).error(e);
		}
			
		%>
	<tr>
		<td class="label">Informações Complementares</td>
		<td><textarea name="descricaoIettir"  rows="4" cols="60" 
			onkeyup="return validaTamanhoLimite(this, 2000);" 
			onkeydown="return validaTamanhoLimite(this, 2000);"
			onblur="return validaTamanhoLimite(this, 2000);" <%=_readOnly%>><%=Pagina.trocaNull(itemEstrtIndResulAntes.getDescricaoIettir())%></textarea></td>
	</tr>

		<%
	// Início da Implementação dos ATRIBUTOS LIVRES!
	//--------------------------------------------------
		List grupoUnidadesObrigatorios = new ArrayList();
		try{
			SisGrupoAtributoSga grupoUnidades = configura.getSisGrupoAtributoSgaByUnidMedida();
			SisGrupoAtributoDao sisGrupoAtributoDao = new SisGrupoAtributoDao(request);
			if(grupoUnidades != null) {
				if (grupoUnidades.getSisAtributoSatbs().size() > 0) {
					
					String strDisabled = "";
					List selecionados = new ArrayList();	
					SisAtributoSatb sisAtributo = (SisAtributoSatb) grupoUnidades.getSisAtributoSatbs().iterator().next();					
					String nomeCampo = "a" + grupoUnidades.getCodSga().toString();
					String pathRaiz = request.getContextPath();

					if("disabled".equals(_disabled))
						strDisabled="S";						

					if(itemEstrtIndResulAntes != null && itemEstrtIndResulAntes.getCodUnidMedidaIettr() != null){
						AtributoLivre atbLivre = new AtributoLivre();
						atbLivre.setSisAtributoSatb(itemEstrtIndResulAntes.getCodUnidMedidaIettr());
						selecionados.add(atbLivre);
					}
					
					if("S".equals(grupoUnidades.getIndObrigatorioSga())){
						grupoUnidadesObrigatorios.add(grupoUnidades);
					}
					%>
					<ecar:Input
						label="<%=grupoUnidades.getDescricaoSga()%>"
						tipo="1"
						obrigatorio="<%=grupoUnidades.getIndObrigatorioSga()%>"
						labelObrigatorio="<%=_obrigatorio%>"
						disabled="<%=strDisabled%>"
						nome="<%=nomeCampo%>"
						classLabel="label"
						selecionados="<%=selecionados%>"
						sisAtributo="<%=sisAtributo%>"
						pathRaiz="<%=pathRaiz%>"
						size="20"
						sisGrupoAtributoSga="<%=grupoUnidades%>"
					> 
					<%


										
					List opcoes = new ArrayList();
					String selectedCodSapadrao = "";
					if(grupoUnidades.getCodSga() != null && grupoUnidades.getCodSga().longValue() != 1){
						if(grupoUnidades.getSisTipoOrdenacaoSto() != null){
							opcoes = sisGrupoAtributoDao.getAtributosOrdenados(grupoUnidades);
						}
					} 
					else{
						selectedCodSapadrao = configura.getSisAtributoSatbByCodSapadrao().getCodSatb().toString();
						opcoes = sisGrupoAtributoDao.getAtributosOrdenados(configura.getSisGrupoAtributoSgaByCodSgaGrAtrPgIni());
					}

					if (grupoUnidades.getCodSga() != null && grupoUnidades.getCodSga().longValue() != 1){
					%>
						<ecar:Options
							options="<%=opcoes%>" 
							valor="codSatb"
							label="descricaoSatb"
							nivelPlanejamentoCheckBox="<%=new Boolean(true)%>"
							imagem="../../images/relAcomp/"
						/>					
					<%
						if(opcoes.iterator().hasNext()){ 
							String codSisAtb = "";
							codSisAtb = ((SisAtributoSatb) opcoes.iterator().next()).getCodSatb().toString();
						%>
							<!-- Hidden que será utilizado para o atributo livre de "Unidade de Medida", nos seguintes casos:TEXT, TEXTAREA, MULTITEXTO, VALIDACAO -->
							<input type="hidden" name="codigoSisAtbUnidadeMedida" id="codigoSisAtbUnidadeMedida" value="<%=codSisAtb %>">										
					<%							
						}
						
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
					%>
					</ecar:Input>
					<%
				}
			} else {
			%>
				<tr>
					<td class="label">* Unidade de Medida</td>
					<td>
						<input type="text" id="unidMedidaIettr" name="unidMedidaIettr" maxlength="20" size="25" value="<%=Pagina.trocaNull(itemEstrtIndResulAntes.getUnidMedidaIettr())%>" <%=_disabled%>>
					</td>
				</tr>
			<%
			}
		} catch (Exception e) {
			org.apache.log4j.Logger.getLogger(this.getClass()).error(e);
		}
			
		%>
	
	<%
		
	StringBuffer nomesGraficoGrupo = new StringBuffer("");
	ItemEstrtIndResulDao iettIndResulDao = new ItemEstrtIndResulDao(request);
	List arrayNomes = iettIndResulDao.retornaNomesGraficoGrupo(itemEstrutura);
	
	Iterator arrayNomesIt = arrayNomes.iterator();
	while(arrayNomesIt.hasNext()){
		String nomeGraficoGrupo = (String) arrayNomesIt.next();
		nomesGraficoGrupo.append(nomeGraficoGrupo);		
		if(arrayNomesIt.hasNext())
			nomesGraficoGrupo.append("**");
	}
	  
	%>
	<tr>
		<td class="label"> Nome do agrupamento para o Gráfico de Grupos</td>
		<td>
			<input type="text" id="labelGraficoGrupoIettir" name="labelGraficoGrupoIettir" maxlength="20" size="20" value="<%=Pagina.trocaNull(itemEstrtIndResulAntes.getLabelGraficoGrupoIettir())%>" <%=_disabled%>>
			<input type="hidden" id="nomesGraficoGrupo" value="<%=nomesGraficoGrupo.toString()%>"> 
		</td>
	</tr>

	<tr>
		<td class="label">* Formato</td>
		<td>
			<input type="radio" class="form_check_radio" id="indTipoQtde01" name="indTipoQtde1" onchange="trocalabelQtdePrevista()" value="Q" <%=Pagina.isChecked(itemEstrtIndResulAntes.getIndTipoQtde(), "Q")%> <%=_disabled%>> Quantidade
			<input type="radio" class="form_check_radio" id="indTipoQtde02" name="indTipoQtde2" onchange="trocalabelQtdePrevista()" value="V" <%=Pagina.isChecked(itemEstrtIndResulAntes.getIndTipoQtde(), "V")%> <%=_disabled%>> Valor
		</td>
	</tr>
	<tr>
		<td class="label">* Proje&ccedil;&atilde;o?</td>
		<td>
			<input type="radio" class="form_check_radio" name="indProjecaoIettr1" value="S" <%=Pagina.isChecked(itemEstrtIndResulAntes.getIndProjecaoIettr(), "S")%> <%=_disabled%>> Sim
			<input type="radio" class="form_check_radio" name="indProjecaoIettr2" value="N" <%=Pagina.isChecked(itemEstrtIndResulAntes.getIndProjecaoIettr(), "N")%> <%=_disabled%>> Não
		</td>
	</tr>
	<tr>
		<td class="label">* Acumul&aacute;vel?</td>
		<td>
			<input type="radio" class="form_check_radio" name="indAcumulavelIettr1" value="S" <%=Pagina.isChecked(itemEstrtIndResulAntes.getIndAcumulavelIettr(), "S")%> <%=_disabled%> onclick="javascript:MostraLinha('S');"> Sim
			<input type="radio" class="form_check_radio" name="indAcumulavelIettr2" value="N" <%=Pagina.isChecked(itemEstrtIndResulAntes.getIndAcumulavelIettr(), "N")%> <%=_disabled%> onclick="javascript:MostraLinha('N');"> Não
		</td>
	</tr>
	<tr>
		<td class="label">* Valor final</td>
		<td>
			<input type="radio" class="form_check_radio" name="indValorFinalIettr1" value="M" <%=Pagina.isChecked(itemEstrtIndResulAntes.getIndValorFinalIettr(), "M")%> <%=_disabled%>> Maior
			<input type="radio" class="form_check_radio" name="indValorFinalIettr2" value="U" <%=Pagina.isChecked(itemEstrtIndResulAntes.getIndValorFinalIettr(), "U")%> <%=_disabled%>> Último
			<input type="radio" class="form_check_radio" name="indValorFinalIettr3" value="N" <%=Pagina.isChecked(itemEstrtIndResulAntes.getIndValorFinalIettr(), "N")%> <%=_disabled%>> Não se aplica
		</td>
	</tr>
	<tr>
		<td class="label">* &Eacute; P&uacute;blico?</td>
		<td>
			<input type="radio" class="form_check_radio" name="indPublicoIettr1" value="S" <%=Pagina.isChecked(itemEstrtIndResulAntes.getIndPublicoIettr(), "S")%> <%=_disabled%>> Sim
			<input type="radio" class="form_check_radio" name="indPublicoIettr2" value="N" <%=Pagina.isChecked(itemEstrtIndResulAntes.getIndPublicoIettr(), "N")%> <%=_disabled%>> Não
		</td>
	</tr>

	<!--  NOVA TELA DE CADASTRO DE INDICADORES  - MANTIS #0011576 -->
 	<tr>
		<td class="label">* Qtde. Prevista Informada por Local?</td>
		<td>
			<!--
	   	    <input type="radio" class="form_check_radio" id="indPrevPorLocal1" name="indPrevPorLocal" onchange="atualizaCheckbox();disableTabela(this);abreJanela('popupQtdePrevista.jsp?codIett=<%=itemEstrutura.getCodIett()%>&codIettir=<%=itemEstrtIndResulAntes.getCodIettir()%>','1000','600','Quantidade Prevista por Local e Exercício');" value="S" <%=Pagina.isChecked(itemEstrtIndResulAntes.getIndPrevPorLocal(), "S")%> <%=_disabledPrevQtdePorLocal%>> Sim  
	   	    -->
	   	    <input type="radio" class="form_check_radio" id="indPrevPorLocal1" name="indPrevPorLocal1" value="S" <%=Pagina.isChecked(itemEstrtIndResulAntes.getIndPrevPorLocal(), "S")%> <%=_disabledPrevQtdePorLocal%> onmouseout="atualizarInfPrevistoLocal();disableTabela(this.form);desativaServicoPrevisto(true);"> Sim  
			<input type="radio" class="form_check_radio" id="indPrevPorLocal2" name="indPrevPorLocal2" value="N" <%=Pagina.isChecked(itemEstrtIndResulAntes.getIndPrevPorLocal(), "N")%> <%=_disabledPrevQtdePorLocal%> onmouseout="atualizarInfPrevistoLocal();enableTabela(this.form);desativaServicoPrevisto(false);"> Não
			<%
			if(!"".equals(_disabledPrevQtdePorLocal)){
			%>
				<input type="hidden" name="indPrevPorLocal" value="<%=Pagina.trocaNull(itemEstrtIndResulAntes.getIndPrevPorLocal())%>">
				<%=_msgPrevQtdePorLocal%>
			<%
			}
			List listaDeExercicio = exercicioDao.getExerciciosValidos(itemEstrutura.getCodIett());
			final int larguraLocal = 250;
			final int larguraCampo = 150;
			final int larguraTotal = 200;
			%>
		    <!-- Comentado porque nao funciona no Internet Explorer -->
			<!-- input type="button" id="btnInfPrevistoLocal" value="Informar Quantidade/Valor Previsto por Local" disabled="</%=_disabled%>" onclick="popup = abreJanela('popupQtdePrevista.jsp?codIett=</%=itemEstrutura.getCodIett()%>&amp;codIettir=</%=itemEstrtIndResulAntes.getCodIettir()%>','</%=(larguraLocal + listaDeExercicio.size()*larguraCampo + larguraTotal) %>','600','Quantidade Prevista por Local e Exercício');" /-->	
			<input type="button" id="btnInfPrevistoLocal" value="Informar Quantidade/Valor Previsto por Local" disabled="<%=_disabled%>" onclick="abrirPrevistoLocal('<%=itemEstrutura.getCodIett()%>','<%=itemEstrtIndResulAntes.getCodIettir()%>','<%=(larguraLocal + listaDeExercicio.size()*larguraCampo + larguraTotal) %>')" />
			
			<script language="JavaScript">
			function abrirPrevistoLocal(item1, item2, tam) {
				abreJanela("popupQtdePrevista.jsp?codIett="+item1+"&codIettir="+item2,tam , 600 , "Quantidade_Prevista_por_Local_e_Exercício");
			}
			</script>
			
			<br>
			<span id="labelAviso" ></span>
		</td>
	</tr>	
	<!--  NOVA TELA DE CADASTRO DE INDICADORES  - MANTIS #0011576  -->	

	<tr>
		<td class="label">* Qtde. Realizada Informada por Local?</td>
		<td>
			<input type="radio" class="form_check_radio" id="indRealPorLocal1" name="indRealPorLocal" value="S" <%=Pagina.isChecked(itemEstrtIndResulAntes.getIndRealPorLocal(), "S")%> <%=_disabledQtdePorLocal%> onchange="desativaServicoRealizado(true);"> Sim
			<input type="radio" class="form_check_radio" id="indRealPorLocal2" name="indRealPorLocal" value="N" <%=Pagina.isChecked(itemEstrtIndResulAntes.getIndRealPorLocal(), "N")%> <%=_disabledQtdePorLocal%> onchange="desativaServicoRealizado(false);"> Não
			<%
			if(!"".equals(_disabledQtdePorLocal)){
			%>
				<input type="hidden" name="indRealPorLocal" value="<%=Pagina.trocaNull(itemEstrtIndResulAntes.getIndRealPorLocal())%>">
				<br>
				<%=_msgQtdePorLocal%>
			<%
			}
			%>
		</td>
	</tr>
	
	<tr>
		<td class="label">&Iacute;ndice de Refer&ecirc;ncia</td>
		<td><input type="text" name="indiceMaisRecenteIettr" maxlength="14" size="15" value="<%=Pagina.trocaNullQtdValor(itemEstrtIndResulAntes.getIndiceMaisRecenteIettr(), itemEstrtIndResulAntes.getIndTipoQtde())%>" <%=_disabled%> ></td><%//onblur="javascript:validarQtdeValor(form,this)" %>
	</tr>

	<tr>
		<td class="label">Data de Apura&ccedil;&atilde;o</td>
		<td>
			<input type="text" name="dataApuracaoIettr" size="13" maxlength="10" value="<%=Pagina.trocaNullData(itemEstrtIndResulAntes.getDataApuracaoIettr())%>" onkeyup="mascaraData(event, this);" onblur="validaData(this.form.dataApuracaoIettr, true, true, false);" <%=_disabled%>>
			<c:if test="<%=_disabled.equals("")%>">
				<img class="posicao" title="Selecione a data" src="../../images/icone_calendar.gif" onclick="open_calendar('data', document.forms[0].dataApuracaoIettr, '')">
			</c:if>
		</td>		
	</tr>
	<tr>
		<td class="label">Base Geogr&aacute;fica</td>
		<td><input type="text" name="baseGeograficaIettr" maxlength="14" size="15" value="" <%=_disabled%>></td>
	</tr>
	<tr>
		<td class="label">Periodicidade</td>
		<td>
		
			<%
			PeriodicidadePrdc periodicidadeSel = itemEstrtIndResulAntes.getPeriodicidadePrdc();
			
			if(periodicidadeSel != null){
			%>
				<combo:ComboTag 
					nome="periodicidadePrdc"
					objeto="ecar.pojo.PeriodicidadePrdc" 
					label="descricaoPrdc" 
					value="codPrdc" 
					order="descricaoPrdc"
					selected="<%=periodicidadeSel.getCodPrdc().toString()%>"
					scripts="<%=_disabled%>"
				/>
			<%
			}
			else{
				%>
				<combo:ComboTag 
					nome="periodicidadePrdc"
					objeto="ecar.pojo.PeriodicidadePrdc" 
					label="descricaoPrdc" 
					value="codPrdc" 
					order="descricaoPrdc"
					scripts="<%=_disabled%>"
				/>
			<%
				
			}
			%>
		</td>
	</tr>
	<tr>
		<td class="label">Fonte</td>
		<td>
			<textarea name="fonteIettr" rows="3" cols="60" <%=_readOnly%> 
			onkeyup="return validaTamanhoLimite(this, 1000);"
			onkeydown="return validaTamanhoLimite(this, 1000);"
			onblur="return validaTamanhoLimite(this, 1000);"><%=Pagina.trocaNull(itemEstrtIndResulAntes.getFonteIettr())%></textarea>
		</td>
	</tr>
	
	<tr>
		<td class="label">F&oacute;rmula</td>
		<td>
			<textarea name="formulaIettr" rows="4" cols="60" <%=_readOnly%> 
			onkeyup="return validaTamanhoLimite(this, 2000);"
			onkeydown="return validaTamanhoLimite(this, 2000);"
			onblur="return validaTamanhoLimite(this, 2000);"><%=Pagina.trocaNull(itemEstrtIndResulAntes.getFormulaIettr())%></textarea>
		</td>
	</tr>

	<%
	String checked_ind_sinalizacao = "";
	if(itemEstrtIndResulAntes.getIndSinalizacaoIettr() != null && itemEstrtIndResulAntes.getIndSinalizacaoIettr().equals("S")){
		checked_ind_sinalizacao = "checked";
	}
	 %>
	<tr>
		<td class="label">Utiliza Sinalização</td>
		<td>
			<input type="checkbox" name="ind_sinalizacao" id="ind_sinalizacao" onchange="mostraSinalizadores()" value="S" <%=_disabled%> <%=checked_ind_sinalizacao%> >
		</td>
	</tr>
	
	<tr id="sinalizacao1" style="display:">
		<td class="label">Estados</td>
		<td>&nbsp;</td>
	</tr>
	<tr id="sinalizacao2" style="display:">
		<td>&nbsp;</td>
		<td>
			<table border="1">
				<tr>
					<td class="titulo" align="center" width="5">Ativo</td>
					<td class="titulo" align="center" width="20" colspan="2">Estado</td>
					<!--  td class="titulo" align="center" width="15" colspan="2">Coluna1</td-->
					<!--  td class="titulo" align="center" width="5">2</td-->
					<td class="titulo" align="center" width="35" colspan="4">Valor</td>				
				</tr>
<% 
			ItemEstrtIndResulCorIettrcor iettrcor = null;
			//ConfigMailCfgm configMailVencto = (ConfigMailCfgm) new ConfigMailCfgmDAO(request).buscar(ConfigMailCfgm.class, Dominios.CFG_MAIL_VENCIMENTO_LIMITE_PONTO_CRITICO);
			Cor corFiltro = new Cor();
			corFiltro.setIndIndicadoresFisicosCor("S");
			List listCoresCombo = new CorDao(request).pesquisar(corFiltro, new String[]{"ordemCor","asc"});
			CorDao cDao = new CorDao(request);
			if (listCoresCombo != null){
				Iterator itCoresCombo = listCoresCombo.iterator();
				String src = "";
				String title = "";
				
				// Configuração	
				ConfiguracaoCfg configuracao = new ConfiguracaoDao(request).getConfiguracao();
				String pathRaizUpload = configuracao.getRaizUpload();	
				
				while (itCoresCombo.hasNext()){
					Cor corCombo = (Cor) itCoresCombo.next();
					src = "";
					title = "";
					String imagePathIndResul = cDao.getImagemPersonalizadaIndResul(corCombo);
					
					UsuarioUsu usuarioImagem = null;  
            		String hashNomeArquivo = null;
					
					if( imagePathIndResul != null ) {
						
						hashNomeArquivo = Util.calcularHashNomeArquivo(pathRaizUpload, imagePathIndResul);
						usuarioImagem = ((ecar.login.SegurancaECAR)request.getSession().getAttribute("seguranca")).getUsuario();  
						Util.adicionarMapArquivosAtuaisUsuarios(usuarioImagem, hashNomeArquivo, pathRaizUpload, imagePathIndResul);
						
						src = request.getContextPath()+"/DownloadFile?tipo=open&RemoteFile="+hashNomeArquivo; 
						title = corCombo.getSignificadoCor();	
							
							
					} else {
						if( corCombo.getCodCor() != null ) {
							src = "../../images/ir"+ corCombo.getNomeCor()+"1.png"; 
							title = corCombo.getSignificadoCor();
						}
					}
					
					if (pathCores != null && !pathCores.equals("")){
						pathCores = pathCores + "@@" + src;
					} else { 
						pathCores = src;
					}
					
					if (titleCores != null && !titleCores.equals("")){
						titleCores = titleCores + "@@" + title;
					} else { 
						titleCores = title;
					}
					
						
				}
			}
			
			List listCores = new ItemEstrtIndResulCorIettrcorDAO(request).listarCoresIettr((ItemEstrtIndResulIettr) itemEstrtIndResulAntes.descarregar());
						
			Cor cor = null;
			ItemEstrtIndResulCorIettrcorPK id = null;
			Iterator itCores = null;
			if (listCores != null)
				itCores = listCores.iterator();
			
			String imagePath = "";
			
			StringBuffer s = new StringBuffer("");			
			int count = 0;
			boolean checkSinalizador = true;
			while (itCores.hasNext())
			{
				count++;
				cor = (Cor) itCores.next(); 			
				id = new ItemEstrtIndResulCorIettrcorPK(itemEstrtIndResulAntes.getCodIettir(), cor.getCodCor());
				
				if("S".equals(Pagina.getParamStr(request, "existeNomeGraficoGrupo"))){
					Set listaResulCor = itemEstrtIndResulAntes.getItemEstrtIndResulCorIettrcores();
					checkSinalizador = false;
					if(listaResulCor != null){
						Iterator itListaResulCor = listaResulCor.iterator();
						while(itListaResulCor.hasNext()){
							ItemEstrtIndResulCorIettrcor itemEstrtIndResulCor = (ItemEstrtIndResulCorIettrcor) itListaResulCor.next();
							if(itemEstrtIndResulCor.getCor().equals(cor)){
								iettrcor = itemEstrtIndResulCor;
								checkSinalizador = true;
							}
						}
					}
				}else{
					iettrcor = (ItemEstrtIndResulCorIettrcor)new ItemEstrtIndResulCorIettrcorDAO(request).buscar(cor, (ItemEstrtIndResulIettr) itemEstrtIndResulAntes.descarregar());
				}
				
				if (codigosCor != null && !codigosCor.equals("")){
					codigosCor = codigosCor + "@@" + cor.getCodCor();
				} else { 
					codigosCor = cor.getCodCor().toString();
				}
				
				 
				%>
				<tr>
				
				<td valign="middle" class="form_label" align="center">
						<input type="checkBox" class="form_check_radio" value="S" name="ativo[<%=count%>]" onChange="atualizarMenorValor()" id="ativo[<%=count%>]"
						<% 
						if (checkSinalizador && "S".equals(iettrcor.getIndAtivoEnvioEmailIettrcor())) {
							out.print(" checked");
						}
						%>
					<%=" " + _disabled + ">"%>
					</td>
				
				<td class="form_label" align="center">
				<%
				String scripts = "disabled onchange=\"atualizaSinalizador("+count+", this.selectedIndex)\"";
				if( cor.getCodCor() != null ) {
					%>
					<combo:ComboTag 
						nome="<%="cor_"+count%>"
						objeto="ecar.pojo.Cor" 
						label="significadoCor" 
						value="codCor" 
						order="significadoCor"
						selected="<%=iettrcor != null && iettrcor.getCor() != null? iettrcor.getCor().getCodCor().toString():""%>"
						colecao="<%=listCoresCombo%>"
						filters="indIndicadoresFisicosCor=S"
						scripts="<%=scripts%>"
						
					/>
				<%
						//s.append(cor.getSignificadoCor()); 
					}		
					%>			
					</td>
					<td valign="middle" width="30" class="form_label" align="center">
					<%
					if (iettrcor != null && iettrcor.getCor() != null){
						imagePath = cDao.getImagemPersonalizadaIndResul(iettrcor.getCor());
						
						UsuarioUsu usuarioImagem = null;  
	            		String hashNomeArquivo = null;
						
						if( imagePath != null ) {
							
							hashNomeArquivo = Util.calcularHashNomeArquivo(pathRaizUpload, imagePath);
							usuarioImagem = ((ecar.login.SegurancaECAR)request.getSession().getAttribute("seguranca")).getUsuario();  
							Util.adicionarMapArquivosAtuaisUsuarios(usuarioImagem, hashNomeArquivo, pathRaizUpload, imagePath);
							
							/* -- As tags do CSS "max-width" e "max-height" não funcionam no IE6 ou menor -- */
							%>
							&nbsp;
							<span id="span_img_<%=count%>" style="display:none">
								
								<img id="img_<%=count%>" border="0" src="<%=request.getContextPath()+"/DownloadFile?tipo=open&RemoteFile="+hashNomeArquivo%>" title="<%=iettrcor.getCor().getSignificadoCor()%>">
							
							</span>
							
							<%
						} else {
							if( iettrcor.getCor().getCodCor() != null ) {
								%>
								&nbsp;
								<span id="span_img_<%=count%>" style="display:none">
									
									<img id="img_<%=count%>" src="../../images/pc<%=iettrcor.getCor().getNomeCor()+"1.png"%>" title="<%=iettrcor.getCor().getSignificadoCor()%>">
								</span>
								<% 
							}
						}
						
					} else {
					%>
						&nbsp;
						<span id="span_img_<%=count%>" style="display:none">
							
							<img id="img_<%=count%>" title="">
						</span>
					<%
					}
					String textoValor = "";
					if (count == 1) {
						textoValor = "Menor Valor";
					}
					%>
					</td>
					<td valign="middle"  class="form_label" align="center">
						Maior que
					</td>
					
					<td valign="middle"  class="form_label" align="center">
						
						<input type="text" name="valor_anterior_<%=count%>" id="valor_anterior_<%=count%>" value="<%=textoValor%>" size="11" disabled >
					
					</td>
					<td valign="middle"  class="form_label" align="center">
						até
					</td>
					<td valign="middle"  class="form_label" align="center">
						<%if(checkSinalizador){%> 
							<input type="text" name="valor_<%=count%>" id="valor_<%=count%>" onChange="atualizarMenorValor()" value="<%=Pagina.trocaNullNumeroDecimalSemMilhar(iettrcor.getValorPrimEmailIettrcor())%>" size="11" disabled>
						<%}else{%>
							<input type="text" name="valor_<%=count%>" id="valor_<%=count%>" onChange="atualizarMenorValor()" value="" size="11" disabled>
						<%}%>						
						
					</td>					
					
					
					</tr>
				<%		
				
			}
		%>	
		<input type="hidden" name="quantidadeCores" id="quantidadeCores" value="<%=count%>"> 		
		</table>
		</td>
	</tr>	
	<% 
		ServicoDao servicoDao = new ServicoDao(request);
		ServicoSer servico = new ServicoSer();
				
		/* 
		Analisar uma forma de identificar quais os serviços usados na tela de indicadores 
		   Ver uma forma de expressar isso na tabela de parâmetros.
		   Entre os parâmetros o código da função que usará o serviço
		*/ 
		List servicos = servicoDao.pesquisar(servico, null); //servicosIndicadoresDeResultados();
		Iterator itServicos = servicos.iterator();
				
		if (itServicos.hasNext()) {	
	 %>
				<tr>
					<td class="label">Utiliza Servi&ccedil;o</td>
					<td >&nbsp;</td>
				<tr>
				
				<tr>
					<td> &nbsp;</td>
					<td > Valor Previsto: &nbsp;
						<select name="previstoServicoSer" id="previstoServicoSer" style="width: 300px" <%=_disabled%>>
							<option value=""> </option>
							<%
							while(itServicos.hasNext() ){ 
								servico = (ServicoSer) itServicos.next();
							 %>
							<option value="<%=servico.getCodServicoSer().intValue() %>"
							<%if(itemEstrtIndResulAntes.getPrevistoServicoSer() != null && itemEstrtIndResulAntes.getPrevistoServicoSer().getCodServicoSer().equals(servico.getCodServicoSer())) out.println("selected");%>>
							<%=servico.getNomeSer() %>
							</option>
							<% } %>
						</select> 
						&nbsp;&nbsp;
						<label><input type="radio" name="indTipoAtualizacaoPrevisto" value="A" <%= Pagina.isChecked(Pagina.trocaNull(itemEstrtIndResulAntes.getIndTipoAtualizacaoPrevisto()),"A" ) %> <%=_disabled%>> Autom&aacute;tico </label>&nbsp;&nbsp;&nbsp;
						<label><input type="radio" name="indTipoAtualizacaoPrevisto" value="M" <%= Pagina.isChecked(Pagina.trocaNull(itemEstrtIndResulAntes.getIndTipoAtualizacaoPrevisto()),"M") %> <%=_disabled%>> Manual</label>   
					</td>
				<tr>

				<tr>
					<td>&nbsp;</td>
					<td> Valor Realizado: 
						<select name="realizadoServicoSer" id="realizadoServicoSer" style="width: 300px" <%=_disabled%>>
							<option value=""> </option>
							<%
							itServicos = servicos.iterator();
							while(itServicos.hasNext() ){ 
								servico = (ServicoSer) itServicos.next();
							 %>
							<option value="<%=servico.getCodServicoSer().intValue() %>"
							<%if(itemEstrtIndResulAntes.getRealizadoServicoSer() != null && itemEstrtIndResulAntes.getRealizadoServicoSer().getCodServicoSer().equals(servico.getCodServicoSer())) out.println("selected");%>>
							<%=servico.getNomeSer() %> 
							</option>
							<% } %>
						</select> 
						&nbsp;&nbsp;
						<label><input type="radio" name="indTipoAtualizacaoRealizado" value="A" <%= Pagina.isChecked(Pagina.trocaNull(itemEstrtIndResulAntes.getIndTipoAtualizacaoRealizado()), "A")  %> <%=_disabled%>> Autom&aacute;tico </label>&nbsp;&nbsp;&nbsp;
						<label><input type="radio" name="indTipoAtualizacaoRealizado" value="M" <%= Pagina.isChecked(Pagina.trocaNull(itemEstrtIndResulAntes.getIndTipoAtualizacaoRealizado()), "M")  %> <%=_disabled%>> Manual</label>   
					</td>
				<tr>

<% } // fim if itServicos.hasNext() %>
			
			
		</td>
	</tr>
