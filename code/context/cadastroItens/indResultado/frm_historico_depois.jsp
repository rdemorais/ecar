	<tr>
		<td class="label">* Tipo</td>
		<td><input type="text" name="nomeIettir" id="nomeIettir" maxlength="30" size="35" value="<%=Pagina.trocaNull(itemEstrtIndResulDepois.getNomeIettir())%>" <%=_disabled%>></td>
	</tr>

	<%
	// Início da Implementação dos ATRIBUTOS LIVRES!
	//--------------------------------------------------
	List grupoMetasObrigatorios1 = new ArrayList();
		try{
			SisGrupoAtributoSga grupoMetas1 = configura.getSisGrupoAtributoSgaByCodSgaGrAtrMetasFisicas();
			SisGrupoAtributoDao sisGrupoAtributoDao1 = new SisGrupoAtributoDao(request);
			if(grupoMetas1 != null){
				if (grupoMetas1.getSisAtributoSatbs().size() > 0)
				{
					
					String strDisabled = "";
					List selecionados = new ArrayList();	
					SisAtributoSatb sisAtributo = (SisAtributoSatb) grupoMetas1.getSisAtributoSatbs().iterator().next();					
					String nomeCampo1 = "a" + grupoMetas1.getCodSga().toString() + "2";
					String pathRaiz1 = request.getContextPath();

					if("disabled".equals(_disabled))
						strDisabled="S";						

					if(itemEstrtIndResulDepois != null && itemEstrtIndResulDepois.getSisAtributoSatb() != null){
						AtributoLivre atbLivre = new AtributoLivre();
						atbLivre.setSisAtributoSatb(itemEstrtIndResulDepois.getSisAtributoSatb());
						selecionados.add(atbLivre);
					}
					
					if("S".equals(grupoMetas1.getIndObrigatorioSga())){
						grupoMetasObrigatorios1.add(grupoMetas1);
					}
					%>
					<ecar:Input
						label="<%=grupoMetas1.getDescricaoSga()%>"
						tipo="<%=Integer.valueOf(grupoMetas1.getSisTipoExibicGrupoSteg().getCodSteg().toString()).intValue()%>"
						obrigatorio="<%=grupoMetas1.getIndObrigatorioSga()%>"
						labelObrigatorio="<%=_obrigatorio%>"
						disabled="<%=strDisabled%>"
						nome="<%=nomeCampo1%>"
						classLabel="label"
						selecionados="<%=selecionados%>"
						sisAtributo="<%=sisAtributo%>"
						pathRaiz="<%=pathRaiz1%>"
						size="20"
						sisGrupoAtributoSga="<%=grupoMetas1%>"
					> 
					<%
										
					List opcoes1 = new ArrayList();
					String selectedCodSapadrao1 = "";
					if(grupoMetas1.getCodSga() != null && grupoMetas1.getCodSga().longValue() != 1){
						if(grupoMetas1.getSisTipoOrdenacaoSto() != null){
							opcoes1 = sisGrupoAtributoDao1.getAtributosOrdenados(grupoMetas1);
						}
					} 
					else{
						selectedCodSapadrao1 = configura.getSisAtributoSatbByCodSapadrao().getCodSatb().toString();
						//Comentado ref mantis 7259:
						//opcoes.addAll(configuracao.getSisGrupoAtributoSgaByCodSgaGrAtrPgIni().getSisAtributoSatbs());
						//ordenando os atributos conforme configuração
						opcoes1 = sisGrupoAtributoDao1.getAtributosOrdenados(configura.getSisGrupoAtributoSgaByCodSgaGrAtrPgIni());
					}

					if (grupoMetas1.getCodSga() != null && grupoMetas1.getCodSga().longValue() != 1){
					%>
						<ecar:Options
							options="<%=opcoes1%>" 
							valor="codSatb"
							label="descricaoSatb"
							nivelPlanejamentoCheckBox="<%=new Boolean(true)%>"
							imagem="../../images/relAcomp/"
							nome="<%=nomeCampo1 %>"
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
							selected="<%=selectedCodSapadrao1%>"
							colecao="<%=opcoes1%>"
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
			onblur="return validaTamanhoLimite(this, 2000);" <%=_readOnly%>><%=Pagina.trocaNull(itemEstrtIndResulDepois.getDescricaoIettir())%></textarea></td>
	</tr>

		<%
	// Início da Implementação dos ATRIBUTOS LIVRES!
	//--------------------------------------------------
		List grupoUnidadesObrigatorios2 = new ArrayList();
		try{
			SisGrupoAtributoSga grupoUnidades1 = configura.getSisGrupoAtributoSgaByUnidMedida();
			SisGrupoAtributoDao sisGrupoAtributoDao1 = new SisGrupoAtributoDao(request);
			if(grupoUnidades1 != null) {
				if (grupoUnidades1.getSisAtributoSatbs().size() > 0) {
					
					String strDisabled = "";
					List selecionados = new ArrayList();	
					SisAtributoSatb sisAtributo = (SisAtributoSatb) grupoUnidades1.getSisAtributoSatbs().iterator().next();					
					String nomeCampo1 = "a" + grupoUnidades1.getCodSga().toString();
					String pathRaiz = request.getContextPath();

					if("disabled".equals(_disabled))
						strDisabled="S";						

					if(itemEstrtIndResulDepois != null && itemEstrtIndResulDepois.getCodUnidMedidaIettr() != null){
						AtributoLivre atbLivre = new AtributoLivre();
						atbLivre.setSisAtributoSatb(itemEstrtIndResulDepois.getCodUnidMedidaIettr());
						selecionados.add(atbLivre);
					}
					
					if("S".equals(grupoUnidades1.getIndObrigatorioSga())){
						grupoUnidadesObrigatorios.add(grupoUnidades1);
					}
					%>
					<ecar:Input
						label="<%=grupoUnidades1.getDescricaoSga()%>"
						tipo="1"
						obrigatorio="<%=grupoUnidades1.getIndObrigatorioSga()%>"
						labelObrigatorio="<%=_obrigatorio%>"
						disabled="<%=strDisabled%>"
						nome="<%=nomeCampo1%>"
						classLabel="label"
						selecionados="<%=selecionados%>"
						sisAtributo="<%=sisAtributo%>"
						pathRaiz="<%=pathRaiz%>"
						size="20"
						sisGrupoAtributoSga="<%=grupoUnidades1%>"
					> 
					<%


										
					List opcoes1 = new ArrayList();
					String selectedCodSapadrao1 = "";
					if(grupoUnidades1.getCodSga() != null && grupoUnidades1.getCodSga().longValue() != 1){
						if(grupoUnidades1.getSisTipoOrdenacaoSto() != null){
							opcoes1 = sisGrupoAtributoDao1.getAtributosOrdenados(grupoUnidades1);
						}
					} 
					else{
						selectedCodSapadrao1 = configura.getSisAtributoSatbByCodSapadrao().getCodSatb().toString();
						//Comentado ref mantis 7259:
						//opcoes.addAll(configuracao.getSisGrupoAtributoSgaByCodSgaGrAtrPgIni().getSisAtributoSatbs());
						//ordenando os atributos conforme configuração
						opcoes1 = sisGrupoAtributoDao1.getAtributosOrdenados(configura.getSisGrupoAtributoSgaByCodSgaGrAtrPgIni());
					}

					if (grupoUnidades1.getCodSga() != null && grupoUnidades1.getCodSga().longValue() != 1){
					%>
						<ecar:Options
							options="<%=opcoes1%>" 
							valor="codSatb"
							label="descricaoSatb"
							nivelPlanejamentoCheckBox="<%=new Boolean(true)%>"
							imagem="../../images/relAcomp/"
						/>					
					<%
						if(opcoes1.iterator().hasNext()){ 
							String codSisAtb = "";
							codSisAtb = ((SisAtributoSatb) opcoes1.iterator().next()).getCodSatb().toString();
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
							selected="<%=selectedCodSapadrao1%>"
							colecao="<%=opcoes1%>"
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
						<input type="text" id="unidMedidaIettr" name="unidMedidaIettr" maxlength="20" size="25" value="<%=Pagina.trocaNull(itemEstrtIndResulDepois.getUnidMedidaIettr())%>" <%=_disabled%>>
					</td>
				</tr>
			<%
			}
		} catch (Exception e) {
			org.apache.log4j.Logger.getLogger(this.getClass()).error(e);
		}
			
		%>
	
	<%
		
	StringBuffer nomesGraficoGrupo1 = new StringBuffer("");
	ItemEstrtIndResulDao iettIndResulDao1 = new ItemEstrtIndResulDao(request);
	List arrayNomes1 = iettIndResulDao1.retornaNomesGraficoGrupo(itemEstrutura);
	
	Iterator arrayNomesIt1 = arrayNomes1.iterator();
	while(arrayNomesIt1.hasNext()){
		String nomeGraficoGrupo1 = (String) arrayNomesIt1.next();
		nomesGraficoGrupo1.append(nomeGraficoGrupo1);		
		if(arrayNomesIt1.hasNext())
			nomesGraficoGrupo1.append("**");
	}
	  
	%>
	<tr>
		<td class="label"> Nome do agrupamento para o Gráfico de Grupos</td>
		<td>
			<input type="text" id="labelGraficoGrupoIettir2" name="labelGraficoGrupoIettir2" maxlength="20" size="20" value="<%=Pagina.trocaNull(itemEstrtIndResulDepois.getLabelGraficoGrupoIettir())%>" <%=_disabled%>>
			<input type="hidden" id="nomesGraficoGrupo2" value="<%=nomesGraficoGrupo1.toString()%>"> 
		</td>
	</tr>

	<tr>
		<td class="label">* Formato</td>
		<td>
			<input type="radio" class="form_check_radio" id="indTipoQtde03" name="indTipoQtde3" onchange="trocalabelQtdePrevista()" value="Q" <%=Pagina.isChecked(itemEstrtIndResulDepois.getIndTipoQtde(), "Q")%> <%=_disabled%>> Quantidade
			<input type="radio" class="form_check_radio" id="indTipoQtde04" name="indTipoQtde4" onchange="trocalabelQtdePrevista()" value="V" <%=Pagina.isChecked(itemEstrtIndResulDepois.getIndTipoQtde(), "V")%> <%=_disabled%>> Valor
		</td>
	</tr>
	<tr>
		<td class="label">* Proje&ccedil;&atilde;o?</td>
		<td>
			<input type="radio" class="form_check_radio" name="indProjecaoIettr3" value="S" <%=Pagina.isChecked(itemEstrtIndResulDepois.getIndProjecaoIettr(), "S")%> <%=_disabled%>> Sim
			<input type="radio" class="form_check_radio" name="indProjecaoIettr4" value="N" <%=Pagina.isChecked(itemEstrtIndResulDepois.getIndProjecaoIettr(), "N")%> <%=_disabled%>> Não
		</td>
	</tr>
	<tr>
		<td class="label">* Acumul&aacute;vel?</td>
		<td>
			<input type="radio" class="form_check_radio" name="indAcumulavelIettr3" value="S" <%=Pagina.isChecked(itemEstrtIndResulDepois.getIndAcumulavelIettr(), "S")%> <%=_disabled%> onclick="javascript:MostraLinha('S');"> Sim
			<input type="radio" class="form_check_radio" name="indAcumulavelIettr4" value="N" <%=Pagina.isChecked(itemEstrtIndResulDepois.getIndAcumulavelIettr(), "N")%> <%=_disabled%> onclick="javascript:MostraLinha('N');"> Não
		</td>
	</tr>
	<tr>
		<td class="label">* Valor final</td>
		<td>
			<input type="radio" class="form_check_radio" name="indValorFinalIettr4" value="M" <%=Pagina.isChecked(itemEstrtIndResulDepois.getIndValorFinalIettr(), "M")%> <%=_disabled%>> Maior
			<input type="radio" class="form_check_radio" name="indValorFinalIettr5" value="U" <%=Pagina.isChecked(itemEstrtIndResulDepois.getIndValorFinalIettr(), "U")%> <%=_disabled%>> Último
			<input type="radio" class="form_check_radio" name="indValorFinalIettr6" value="N" <%=Pagina.isChecked(itemEstrtIndResulDepois.getIndValorFinalIettr(), "N")%> <%=_disabled%>> Não se aplica
		</td>
	</tr>
	<tr>
		<td class="label">* &Eacute; P&uacute;blico?</td>
		<td>
			<input type="radio" class="form_check_radio" name="indPublicoIettr3" value="S" <%=Pagina.isChecked(itemEstrtIndResulDepois.getIndPublicoIettr(), "S")%> <%=_disabled%>> Sim
			<input type="radio" class="form_check_radio" name="indPublicoIettr4" value="N" <%=Pagina.isChecked(itemEstrtIndResulDepois.getIndPublicoIettr(), "N")%> <%=_disabled%>> Não
		</td>
	</tr>

	<!--  NOVA TELA DE CADASTRO DE INDICADORES  - MANTIS #0011576 -->
 	<tr>
		<td class="label">* Qtde. Prevista Informada por Local?</td>
		<td>
			<!--
	   	    <input type="radio" class="form_check_radio" id="indPrevPorLocal1" name="indPrevPorLocal" onchange="atualizaCheckbox();disableTabela(this);abreJanela('popupQtdePrevista.jsp?codIett=<%=itemEstrutura.getCodIett()%>&codIettir=<%=itemEstrtIndResulDepois.getCodIettir()%>','1000','600','Quantidade Prevista por Local e Exercício');" value="S" <%=Pagina.isChecked(itemEstrtIndResulDepois.getIndPrevPorLocal(), "S")%> <%=_disabledPrevQtdePorLocal%>> Sim  
	   	    -->
	   	    <input type="radio" class="form_check_radio" id="indPrevPorLocal3" name="indPrevPorLocal3" value="S" <%=Pagina.isChecked(itemEstrtIndResulDepois.getIndPrevPorLocal(), "S")%> <%=_disabledPrevQtdePorLocal%> onmouseout="atualizarInfPrevistoLocal();disableTabela(this.form);desativaServicoPrevisto(true);"> Sim  
			<input type="radio" class="form_check_radio" id="indPrevPorLocal4" name="indPrevPorLocal4" value="N" <%=Pagina.isChecked(itemEstrtIndResulDepois.getIndPrevPorLocal(), "N")%> <%=_disabledPrevQtdePorLocal%> onmouseout="atualizarInfPrevistoLocal();enableTabela(this.form);desativaServicoPrevisto(false);"> Não
			<%
			if(!"".equals(_disabledPrevQtdePorLocal)){
			%>
				<input type="hidden" name="indPrevPorLocal3" value="<%=Pagina.trocaNull(itemEstrtIndResulDepois.getIndPrevPorLocal())%>">
				<%=_msgPrevQtdePorLocal%>
			<%
			}
			List listaDeExercicio1 = exercicioDao.getExerciciosValidos(itemEstrutura.getCodIett());
			final int larguraLocal1 = 250;
			final int larguraCampo1 = 150;
			final int larguraTotal1 = 200;
			%>
		    <!-- Comentado porque nao funciona no Internet Explorer -->
			<!-- input type="button" id="btnInfPrevistoLocal" value="Informar Quantidade/Valor Previsto por Local" disabled="</%=_disabled%>" onclick="popup = abreJanela('popupQtdePrevista.jsp?codIett=</%=itemEstrutura.getCodIett()%>&amp;codIettir=</%=itemEstrtIndResulDepois.getCodIettir()%>','</%=(larguraLocal + listaDeExercicio.size()*larguraCampo + larguraTotal) %>','600','Quantidade Prevista por Local e Exercício');" /-->	
			<input type="button" id="btnInfPrevistoLocal" value="Informar Quantidade/Valor Previsto por Local" disabled="<%=_disabled%>" onclick="abrirPrevistoLocal('<%=itemEstrutura.getCodIett()%>','<%=itemEstrtIndResulDepois.getCodIettir()%>','<%=(larguraLocal + listaDeExercicio.size()*larguraCampo + larguraTotal) %>')" />
			
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
			<input type="radio" class="form_check_radio" id="indRealPorLocal3" name="indRealPorLocal3" value="S" <%=Pagina.isChecked(itemEstrtIndResulDepois.getIndRealPorLocal(), "S")%> <%=_disabledQtdePorLocal%> onchange="desativaServicoRealizado(true);"> Sim
			<input type="radio" class="form_check_radio" id="indRealPorLocal4" name="indRealPorLocal4" value="N" <%=Pagina.isChecked(itemEstrtIndResulDepois.getIndRealPorLocal(), "N")%> <%=_disabledQtdePorLocal%> onchange="desativaServicoRealizado(false);"> Não
			<%
			if(!"".equals(_disabledQtdePorLocal)){
			%>
				<input type="hidden" name="indRealPorLocal3" value="<%=Pagina.trocaNull(itemEstrtIndResulDepois.getIndRealPorLocal())%>">
				<br>
				<%=_msgQtdePorLocal%>
			<%
			}
			%>
		</td>
	</tr>
	
	<tr>
		<td class="label">&Iacute;ndice de Refer&ecirc;ncia</td>
		<td><input type="text" name="indiceMaisRecenteIettr3" maxlength="14" size="15" value="<%=Pagina.trocaNullQtdValor(itemEstrtIndResulDepois.getIndiceMaisRecenteIettr(), itemEstrtIndResulDepois.getIndTipoQtde())%>" <%=_disabled%> ></td><%//onblur="javascript:validarQtdeValor(form,this)" %>
	</tr>

	<tr>
		<td class="label">Data de Apura&ccedil;&atilde;o</td>
		<td>
			<input type="text" name="dataApuracaoIettr3" size="13" maxlength="10" value="<%=Pagina.trocaNullData(itemEstrtIndResulDepois.getDataApuracaoIettr())%>" onkeyup="mascaraData(event, this);" onblur="validaData(this.form.dataApuracaoIettr, true, true, false);" <%=_disabled%>>
			<c:if test="<%=_disabled.equals("")%>">
				<img class="posicao" title="Selecione a data" src="../../images/icone_calendar.gif" onclick="open_calendar('data', document.forms[0].dataApuracaoIettr, '')">
			</c:if>
		</td>		
	</tr>
	<tr>
		<td class="label">Base Geogr&aacute;fica</td>
		<td><input type="text" name="baseGeograficaIettr3" maxlength="14" size="15" value="" <%=_disabled%>></td>
	</tr>
	<tr>
		<td class="label">Periodicidade</td>
		<td>
		
			<%
			PeriodicidadePrdc periodicidadeSel1 = itemEstrtIndResulDepois.getPeriodicidadePrdc();
			
			if(periodicidadeSel1 != null){
			%>
				<combo:ComboTag 
					nome="periodicidadePrdc"
					objeto="ecar.pojo.PeriodicidadePrdc" 
					label="descricaoPrdc" 
					value="codPrdc" 
					order="descricaoPrdc"
					selected="<%=periodicidadeSel1.getCodPrdc().toString()%>"
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
			<textarea name="fonteIettr4" rows="3" cols="60" <%=_readOnly%> 
			onkeyup="return validaTamanhoLimite(this, 1000);"
			onkeydown="return validaTamanhoLimite(this, 1000);"
			onblur="return validaTamanhoLimite(this, 1000);"><%=Pagina.trocaNull(itemEstrtIndResulDepois.getFonteIettr())%></textarea>
		</td>
	</tr>
	
	<tr>
		<td class="label">F&oacute;rmula</td>
		<td>
			<textarea name="formulaIett4r" rows="4" cols="60" <%=_readOnly%> 
			onkeyup="return validaTamanhoLimite(this, 2000);"
			onkeydown="return validaTamanhoLimite(this, 2000);"
			onblur="return validaTamanhoLimite(this, 2000);"><%=Pagina.trocaNull(itemEstrtIndResulDepois.getFormulaIettr())%></textarea>
		</td>
	</tr>

	<%
	String checked_ind_sinalizacao1 = "";
	if(itemEstrtIndResulDepois.getIndSinalizacaoIettr() != null && itemEstrtIndResulDepois.getIndSinalizacaoIettr().equals("S")){
		checked_ind_sinalizacao1 = "checked";
	}
	 %>
	<tr>
		<td class="label">Utiliza Sinalização</td>
		<td>
			<input type="checkbox" name="ind_sinalizacao3" id="ind_sinalizacao3" onchange="mostraSinalizadores()" value="S" <%=_disabled%> <%=checked_ind_sinalizacao%> >
		</td>
	</tr>
	
	<tr id="sinalizacao3" style="display:">
		<td class="label">Estados</td>
		<td>&nbsp;</td>
	</tr>
	<tr id="sinalizacao4" style="display:">
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
			ItemEstrtIndResulCorIettrcor iettrcor1 = null;
			List listCores1 = itemEstrtIndResulDepois.getItemEstrtIndResulCorIettrcores();

			Cor cor1 = null;
			ItemEstrtIndResulCorIettrcorPK id1 = null;
			Iterator itCores1 = null;
			if (listCores1 != null)
				itCores1 = listCores.iterator();
			
			String imagePath1 = "";
			
			StringBuffer s1 = new StringBuffer("");			
			int count1 = 0;
			boolean checkSinalizador1 = true;
			while (itCores1.hasNext())
			{
				count++;
				cor1 = (Cor) itCores1.next(); 			
				//id1 = new ItemEstrtIndResulCorIettrcorPK(itemEstrtIndResulDepois.getCodIettir(), cor.getCodCor());
				
				if("S".equals(Pagina.getParamStr(request, "existeNomeGraficoGrupo"))){
					Set listaResulCor = itemEstrtIndResulDepois.getItemEstrtIndResulCorIettrcores();
					checkSinalizador = false;
					if(listaResulCor != null){
						Iterator itListaResulCor = listaResulCor.iterator();
						while(itListaResulCor.hasNext()){
							ItemEstrtIndResulCorIettrcor itemEstrtIndResulCor = (ItemEstrtIndResulCorIettrcor) itListaResulCor.next();
							if(itemEstrtIndResulCor.getCor().equals(cor1)){
								iettrcor = itemEstrtIndResulCor;
								checkSinalizador = true;
							}
						}
					}
				}else{
					iettrcor1 = (ItemEstrtIndResulCorIettrcor)new ItemEstrtIndResulCorIettrcorDAO(null).buscar(cor1, itemEstrtIndResulAntes);
				}
				
				if (codigosCor1 != null && !codigosCor1.equals("")){
					codigosCor1 = codigosCor1 + "@@" + cor1.getCodCor();
				} else { 
					codigosCor1 = cor1.getCodCor().toString();
				}
				
				 
				%>
				<tr>
				
				<td valign="middle" class="form_label" align="center">
						<input type="checkBox" class="form_check_radio" value="S" name="ativo[<%=count%>]2" onChange="atualizarMenorValor()" id="ativo[<%=count%>]"
						<% 
						if (checkSinalizador && "S".equals(iettrcor1.getIndAtivoEnvioEmailIettrcor())) {
							out.print(" checked");
						}
						%>
					<%=" " + _disabled + ">"%>
					</td>
				
				<td class="form_label" align="center">
				<%
				String scripts1 = "disabled onchange=\"atualizaSinalizador("+count+", this.selectedIndex)\"";
				if( cor1.getCodCor() != null ) {
					%>
					<combo:ComboTag 
						nome="<%="cor_"+count%>"
						objeto="ecar.pojo.Cor" 
						label="significadoCor" 
						value="codCor" 
						order="significadoCor"
						selected="<%=iettrcor1 != null && iettrcor1.getCor() != null? iettrcor1.getCor().getCodCor().toString():""%>"
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
					
					// Configuração	
					ConfiguracaoCfg configuracao = new ConfiguracaoDao(request).getConfiguracao();
					String pathRaizUpload = configuracao.getRaizUpload();
					
					if (iettrcor1 != null && iettrcor1.getCor() != null){
						imagePath1 = cDao.getImagemPersonalizadaIndResul(iettrcor.getCor());
						
						UsuarioUsu usuarioImagem = null;  
	            		String hashNomeArquivo = null;
						
						if( imagePath1 != null ) {
							
							hashNomeArquivo = Util.calcularHashNomeArquivo(pathRaizUpload, imagePath);
							usuarioImagem = ((ecar.login.SegurancaECAR)request.getSession().getAttribute("seguranca")).getUsuario();  
							Util.adicionarMapArquivosAtuaisUsuarios(usuarioImagem, hashNomeArquivo, pathRaizUpload, imagePath);
							
							/* -- As tags do CSS "max-width" e "max-height" não funcionam no IE6 ou menor -- */
							%>
							&nbsp;
							<span id="span_img_<%=count%>2" style="display:none">
								
								<img id="img_<%=count%>2" border="0" src="<%=request.getContextPath()+"/DownloadFile?tipo=open&RemoteFile="+hashNomeArquivo%>" title="<%=iettrcor1.getCor().getSignificadoCor()%>">
							
							</span>
							
							<%
						} else {
							if( iettrcor.getCor().getCodCor() != null ) {
								%>
								&nbsp;
								<span id="span_img_<%=count%>2" style="display:none">
									
									<img id="img_<%=count%>2" src="../../images/pc<%=iettrcor1.getCor().getNomeCor()+"1.png"%>" title="<%=iettrcor1.getCor().getSignificadoCor()%>">
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
						
						<input type="text" name="valor_anterior_<%=count%>2" id="valor_anterior_<%=count%>2" value="<%=textoValor%>" size="11" disabled >
					
					</td>
					<td valign="middle"  class="form_label" align="center">
						até
					</td>
					<td valign="middle"  class="form_label" align="center">
						<%if(checkSinalizador){%> 
							<input type="text" name="valor_<%=count%>2" id="valor_<%=count%>" onChange="atualizarMenorValor()" value="<%=Pagina.trocaNullNumeroDecimalSemMilhar(iettrcor1.getValorPrimEmailIettrcor())%>" size="11" disabled>
						<%}else{%>
							<input type="text" name="valor_<%=count%>2" id="valor_<%=count%>" onChange="atualizarMenorValor()" value="" size="11" disabled>
						<%}%>						
						
					</td>					
					
					
					</tr>
				<%		
				
			}
		%>	
		<input type="hidden" name="quantidadeCores2" id="quantidadeCores2" value="<%=count%>"> 		
		</table>
		</td>
	</tr>	
	<% 
		ServicoDao servicoDao1 = new ServicoDao(request);
		ServicoSer servico1 = new ServicoSer();
				
		/* 
		Analisar uma forma de identificar quais os serviços usados na tela de indicadores 
		   Ver uma forma de expressar isso na tabela de parâmetros.
		   Entre os parâmetros o código da função que usará o serviço
		*/ 
		List servicos1 = servicoDao1.pesquisar(servico, null); //servicosIndicadoresDeResultados();
		Iterator itServicos1 = servicos1.iterator();
				
		if (itServicos1.hasNext()) {	
	 %>
				<tr>
					<td class="label">Utiliza Servi&ccedil;o</td>
					<td >&nbsp;</td>
				<tr>
				
				<tr>
					<td> &nbsp;</td>
					<td > Valor Previsto: &nbsp;
						<select name="previstoServicoSer4" id="previstoServicoSer4" style="width: 300px" <%=_disabled%>>
							<option value=""> </option>
							
							<%
							while(itServicos1.hasNext() ){ 
								servico1 = (ServicoSer) itServicos1.next();
							 %>
							<option value="<%=servico1.getCodServicoSer().intValue() %>"
							<%if(itemEstrtIndResulDepois.getPrevistoServicoSer() != null && itemEstrtIndResulDepois.getPrevistoServicoSer().getCodServicoSer().equals(servico.getCodServicoSer())) out.println("selected");%>>
							<%=servico1.getNomeSer() %>
							</option>
							<% } %>
						</select> 
						&nbsp;&nbsp;
						<label><input type="radio" name="indTipoAtualizacaoPrevisto3" value="A" <%= Pagina.isChecked(Pagina.trocaNull(itemEstrtIndResulDepois.getIndTipoAtualizacaoPrevisto()),"A" ) %> <%=_disabled%>> Autom&aacute;tico </label>&nbsp;&nbsp;&nbsp;
						<label><input type="radio" name="indTipoAtualizacaoPrevisto4" value="M" <%= Pagina.isChecked(Pagina.trocaNull(itemEstrtIndResulDepois.getIndTipoAtualizacaoPrevisto()),"M") %> <%=_disabled%>> Manual</label>   
					</td>
				<tr>

				<tr>
					<td>&nbsp;</td>
					<td> Valor Realizado: 
						<select name="realizadoServicoSer3" id="realizadoServicoSer3" style="width: 300px" <%=_disabled%>>
							<option value=""> </option>
							<%
							itServicos1 = servicos1.iterator();
							while(itServicos1.hasNext() ){ 
								servico1 = (ServicoSer) itServicos1.next();
							 %>
							<option value="<%=servico1.getCodServicoSer().intValue() %>"
							<%if(itemEstrtIndResulDepois.getRealizadoServicoSer() != null && itemEstrtIndResulDepois.getRealizadoServicoSer().getCodServicoSer().equals(servico.getCodServicoSer())) out.println("selected");%>>
							<%=servico1.getNomeSer() %> 
							</option>
							<% } %>
						</select> 
						&nbsp;&nbsp;
						<label><input type="radio" name="indTipoAtualizacaoRealizado3" value="A" <%= Pagina.isChecked(Pagina.trocaNull(itemEstrtIndResulDepois.getIndTipoAtualizacaoRealizado()), "A")  %> <%=_disabled%>> Autom&aacute;tico </label>&nbsp;&nbsp;&nbsp;
						<label><input type="radio" name="indTipoAtualizacaoRealizado4" value="M" <%= Pagina.isChecked(Pagina.trocaNull(itemEstrtIndResulDepois.getIndTipoAtualizacaoRealizado()), "M")  %> <%=_disabled%>> Manual</label>   
					</td>
				<tr>

<% } // fim if itServicos.hasNext() %>
			
			
		</td>
	</tr>