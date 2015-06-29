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
<%@page import="ecar.pojo.UsuarioUsu"%>

<%@page import="ecar.pojo.LocalGrupoLgp"%>

<%@page import="ecar.pojo.ConfiguracaoCfg"%><jsp:directive.page import="java.util.Iterator"/>
<jsp:directive.page import="ecar.pojo.ItemEstrtIndResulIettr"/>
<jsp:directive.page import="ecar.dao.ServicoDao"/>
<jsp:directive.page import="ecar.pojo.ServicoSer"/>


<script language="javascript" src="../../js/form_cadItem_indresultado.js"></script> 
<!--  <script language="javascript" src="../../js/prototype.js"></script> -->	
<script language="javascript" src="../../js/popup.js"></script>	
<script language="javascript" src="../../js/datas.js"></script>	

<script language="javascript" src="../../js/jquery.js"></script>
<script language="javascript" src="../../js/jquery.maskMoney.min.js"></script>
<script language="javascript" src="../../js/jquery_autocomplete.js"></script>
<link rel="stylesheet" href="../../css/jquery_autocomplete.css" type="text/css">
<%
	ConfiguracaoCfg configuramento = (ConfiguracaoCfg)session.getAttribute("configuracao");
%>
<link rel="stylesheet" href="<%=_pathEcar%>/css/style_<%=configuramento.getEstilo().getNome()%>.css" type="text/css">

<jsp:directive.page import="comum.util.Pagina"/>
<jsp:directive.page import="ecar.pojo.PeriodicidadePrdc"/>

<script language="javascript">

function verificaPreenchimentoCelula(element){

    // Para não ficar testando todos elementos a qualquer momento,
    //testa somente em casos de interesse
    //Ou quando o elemento foi preenchido e a combo de abrangencia está habilitada
    //Ou quando saiu com branco e a combo está desabilitada
	if(((element.value != '')&&(!document.getElementById('indPrevPorLocal1').disabled))||((element.value == '')&&(document.getElementById('indPrevPorLocal1').disabled))){

	var temValores = false;

    	jQuery(".previsto").each(function(){
            if((this).value != ''){
            	temValores = true;
            	return false;
            }
         });
      
		if (!temValores){
			document.getElementById('existePrevistos').value = 'N';
			jQuery("[@name=indPrevPorLocal]").attr("disabled", false);

		}	
		else
		{	
			document.getElementById('existePrevistos').value = 'S';
			jQuery("#indPrevPorLocal2").attr("checked", "checked");
			jQuery("[@name=indPrevPorLocal]").attr("disabled", true);
			

	   }

  }
}

function atualizaValor()
{
	var valueSelect = document.getElementById('abrangenciaLocal').value; 

	document.getElementById('nivelAbrangencia').value = valueSelect; 
 	
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

function mostraBotoesPorLocal(mostrarBotoes, mostrarGrid){
	
	var botoesDisabled = (mostrarBotoes == 'N');
	var gridDisabled = (mostrarGrid == 'N');
	
	$("[@name=btnPorLocal]").attr("disabled", botoesDisabled);
	
	$(".previsto").attr("disabled", gridDisabled);

	//limpa os valores previstos.
	$(".previsto").attr("value", "");
	
}

function controlaNivelAbrangencia(){

	var valueSelect;

	if (document.getElementById('indPrevPorLocal1').checked)
		valueSelect = 'S';
	else
		valueSelect = 'N';
	
	document.getElementById('previstoPorLocal').value = valueSelect; 

	
    //Os dois radios marcados com não, desabilita a combo de abrangencia
	if((document.getElementById('indPrevPorLocal2').checked)&&(document.getElementById('indRealPorLocal2').checked)){
		document.getElementById('abrangenciaLocal').selectedIndex = 0;
		document.getElementById('abrangenciaLocal').disabled = true;
	}

	if ((!document.getElementById('indPrevPorLocal1').disabled)&&(document.getElementById('indPrevPorLocal1').checked)&&((!document.getElementById('indRealPorLocal1').disabled)||((document.getElementById('indRealPorLocal2').disabled)&&(document.getElementById('indRealPorLocal2').checked)))){
		document.getElementById('abrangenciaLocal').disabled = false;
	}

	if((!document.getElementById('indRealPorLocal1').disabled)&&(document.getElementById('indRealPorLocal1').checked)&&((!document.getElementById('indPrevPorLocal1').disabled)||((document.getElementById('indPrevPorLocal2').disabled)&&(document.getElementById('indPrevPorLocal2').checked)))){
		document.getElementById('abrangenciaLocal').disabled = false;		
	}

	
}


</script>

<%
	ExercicioDao exercicioDao = new ExercicioDao(request);
	String codigosCor = "";
	String pathCores = "";
	String titleCores = "";
	
	
%>

	<input type="hidden" name="codIett" id="codIett" value="<%=itemEstrutura.getCodIett()%>"/>
	<input type="hidden" name="codAba"  id="codAba"  value='<%=Pagina.getParamStr(request, "codAba")%>'/>
	<input type="hidden" name="hidAcao" id="hidAcao" value=""/>
	<input type="hidden" name="popup"   id="popup"   value=""/>
	<input type="hidden" name="ind_sinalizacao"   id="ind_sinalizacao"   value="S"/>
		

	<tr>
		<td colspan="2">
			<div class="camposobrigatorios">* Campos de preenchimento obrigat&oacute;rio</div>
		</td>
	</tr>	
	<%
	// Início da Implementação dos ATRIBUTOS LIVRES!
	//--------------------------------------------------
	List grupoMetasObrigatorios = new ArrayList();
		try{
			SisGrupoAtributoSga grupoMetas = configuramento.getSisGrupoAtributoSgaByCodSgaGrAtrMetasFisicas();
			SisGrupoAtributoDao sisGrupoAtributoDao = new SisGrupoAtributoDao(request);
			if(grupoMetas != null){
				if (grupoMetas.getSisAtributoSatbs().size() > 0)
				{
					
					String strDisabled = "";
					List selecionados = new ArrayList();	
					SisAtributoSatb sisAtributo = (SisAtributoSatb) grupoMetas.getSisAtributoSatbs().iterator().next();					
					String nomeCampo = "a" + grupoMetas.getCodSga().toString();
					String pathRaiz = request.getContextPath();

					if("disabled".equals(_disabled))
						strDisabled="S";						

					if(itemEstrtIndResul != null && itemEstrtIndResul.getSisAtributoSatb() != null){
						AtributoLivre atbLivre = new AtributoLivre();
						atbLivre.setSisAtributoSatb(itemEstrtIndResul.getSisAtributoSatb());
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
						selectedCodSapadrao = configuramento.getSisAtributoSatbByCodSapadrao().getCodSatb().toString();
						//Comentado ref mantis 7259:
						//opcoes.addAll(configuracao.getSisGrupoAtributoSgaByCodSgaGrAtrPgIni().getSisAtributoSatbs());
						//ordenando os atributos conforme configuração
						opcoes = sisGrupoAtributoDao.getAtributosOrdenados(configuramento.getSisGrupoAtributoSgaByCodSgaGrAtrPgIni());
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
		
	<tr style="display: none;">
		<td class="label">Ordem</td>
		<td>
			<input type="hidden" name="ordem" id="ordem"  <%=_readOnly%> value="<%=Pagina.trocaNull(itemEstrtIndResul.getOrdem())%>"/>			
		</td>
	</tr>		
	<tr>
		<td class="label">* Nome do Indicador</td>
		<td>
			<textarea name="nomeIettir" id="nomeIettir" rows="3" cols="60" <%=_readOnly%>><%=Pagina.trocaNull(itemEstrtIndResul.getNomeIettir())%></textarea>			
		</td>
	</tr>

	<tr>
		<td class="label">* Conceitua&ccedil;&atilde;o</td>
		<td>
			<textarea name="conceitIettir" id="conceitIettir" rows="3" cols="60" <%=_readOnly%>
			onkeyup="return validaTamanhoLimite(this, 2000);"
			onkeydown="return validaTamanhoLimite(this, 2000);"
			onblur="return validaTamanhoLimite(this, 2000);"><%=Pagina.trocaNull(itemEstrtIndResul.getConceituacao())%></textarea>
			<label class="dica" onmouseover="javascript:viewFieldTip(this, 'conceitIettirSPAN');" 
				onmouseout="javascript:noViewFieldTip('conceitIettirSPAN');">
				<img src="../../images/dica.png" align="absmiddle" border="0" 
					onclick="javascript:viewFieldTipPopUp('Conceituacao')">
					<span id="conceitIettirSPAN" style="display: none; ">
						informações que definem o indicador e a forma como ele se expressa, 
						se necessário agregando elementos para a compreensão de seu conteúdo.
					</span>
			</label>
		</td>
	</tr>

	<tr>
		<td class="label">* Interpreta&ccedil;&atilde;o</td>
		<td>
			<textarea name="interpIettir" id="interpIettir" rows="3" cols="60" <%=_readOnly%>
			onkeyup="return validaTamanhoLimite(this, 2000);"
			onkeydown="return validaTamanhoLimite(this, 2000);"
			onblur="return validaTamanhoLimite(this, 2000);"><%=Pagina.trocaNull(itemEstrtIndResul.getInterpretacao())%></textarea>
			<label class="dica" onmouseover="javascript:viewFieldTip(this, 'interpIettirSPAN');" 
				onmouseout="javascript:noViewFieldTip('interpIettirSPAN');">
				<img src="../../images/dica.png" align="absmiddle" border="0" 
					onclick="javascript:viewFieldTipPopUp('Interpretacao')">
					<span id="interpIettirSPAN" style="display: none; ">
						explicação sucinta do tipo de informação obtida e seu significado.
					</span>
			</label>
		</td>
	</tr>

	<tr>
		<td class="label">* Fontes</td>
		<td>
			<textarea name="fonteIettr" rows="3" cols="60" <%=_readOnly%> 
			onkeyup="return validaTamanhoLimite(this, 1000);"
			onkeydown="return validaTamanhoLimite(this, 1000);"
			onblur="return validaTamanhoLimite(this, 1000);"><%=Pagina.trocaNull(itemEstrtIndResul.getFonteIettr())%></textarea>
			<label class="dica" onmouseover="javascript:viewFieldTip(this, 'fonteIettrSPAN');" 
				onmouseout="javascript:noViewFieldTip('fonteIettrSPAN');">
				<img src="../../images/dica.png" align="absmiddle" border="0" 
					onclick="javascript:viewFieldTipPopUp('Fonte')">
					<span id="fonteIettrSPAN" style="display: none; ">
						instituições responsáveis pela produção dos dados utilizados 
						no cálculo do indicador e pelos sistemas de informação a que correspondem.
					</span>
			</label>
		</td>
	</tr>

	<tr>
		<td class="label">* M&eacute;todo de C&aacute;lculo</td>
		<td>
			<textarea name="mCalcIettir" id="mCalcIettir" rows="3" cols="60" <%=_readOnly%>
			onkeyup="return validaTamanhoLimite(this, 2000);"
			onkeydown="return validaTamanhoLimite(this, 2000);"
			onblur="return validaTamanhoLimite(this, 2000);"><%=Pagina.trocaNull(itemEstrtIndResul.getMetodoCalculo())%></textarea>
			<label class="dica" onmouseover="javascript:viewFieldTip(this, 'mCalcIettirSPAN');" 
				onmouseout="javascript:noViewFieldTip('mCalcIettirSPAN');">
				<img src="../../images/dica.png" align="absmiddle" border="0" 
					onclick="javascript:viewFieldTipPopUp('Fonte')">
					<span id="mCalcIettirSPAN" style="display: none; ">
						fórmula utilizada para calcular o indicador, definindo precisamente os elementos que a compõem.
					</span>
			</label>
		</td>
	</tr>

	<tr>
		<td class="label">Sinalizador</td>
		<td>
			<input type="radio" class="form_check_radio" id="indTipoSin1" name="idSinalizacao" value="1" <%=Pagina.isChecked(itemEstrtIndResul.getSinalizacao().getCodSin(), "1")%> <%=_disabled%>>Maior Melhor<br />
			<input type="radio" class="form_check_radio" id="indTipoSin2" name="idSinalizacao" value="2" <%=Pagina.isChecked(itemEstrtIndResul.getSinalizacao().getCodSin(), "2")%> <%=_disabled%>>Menor Melhor
		</td>
	</tr>

		<%
	// Início da Implementação dos ATRIBUTOS LIVRES!
	//--------------------------------------------------
		List grupoUnidadesObrigatorios = new ArrayList();
		try{
			SisGrupoAtributoSga grupoUnidades = configuramento.getSisGrupoAtributoSgaByUnidMedida();
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

					if(itemEstrtIndResul != null && itemEstrtIndResul.getCodUnidMedidaIettr() != null){
						AtributoLivre atbLivre = new AtributoLivre();
						atbLivre.setSisAtributoSatb(itemEstrtIndResul.getCodUnidMedidaIettr());
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
						selectedCodSapadrao = configuramento.getSisAtributoSatbByCodSapadrao().getCodSatb().toString();
						//Comentado ref mantis 7259:
						//opcoes.addAll(configuracao.getSisGrupoAtributoSgaByCodSgaGrAtrPgIni().getSisAtributoSatbs());
						//ordenando os atributos conforme configuração
						opcoes = sisGrupoAtributoDao.getAtributosOrdenados(configuramento.getSisGrupoAtributoSgaByCodSgaGrAtrPgIni());
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
						<input type="text" id="unidMedidaIettr" name="unidMedidaIettr" maxlength="20" size="25" value="<%=Pagina.trocaNull(itemEstrtIndResul.getUnidMedidaIettr())%>" <%=_disabled%>>
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
		<td class="label">* Formato</td>
		<td>
			<input type="radio" class="form_check_radio" id="indTipoQtde1" name="indTipoQtde" onchange="trocalabelQtdePrevista()" value="Q" <%=Pagina.isChecked(itemEstrtIndResul.getIndTipoQtde(), "Q")%> <%=_disabled%>> N&uacute;mero
			<input type="radio" class="form_check_radio" id="indTipoQtde2" name="indTipoQtde" onchange="trocalabelQtdePrevista()" value="V" <%=Pagina.isChecked(itemEstrtIndResul.getIndTipoQtde(), "V")%> <%=_disabled%>> Moeda
		</td>
	</tr>
	<tr style="display: none;">
		<td class="label">* Proje&ccedil;&atilde;o?</td>
		<td>
			<input type="radio" class="form_check_radio" name="indProjecaoIettr" value="S" checked="checked"   <%//=Pagina.isChecked(itemEstrtIndResul.getIndProjecaoIettr(), "S")%> <%=_disabled%>> Sim
			<input type="radio" class="form_check_radio" name="indProjecaoIettr" value="N" <%//=Pagina.isChecked(itemEstrtIndResul.getIndProjecaoIettr(), "N")%> <%=_disabled%>> Não
		</td>
	</tr> 
		
	<tr>
		<td class="label">* Acumul&aacute;vel?</td>
		<td>
			<input type="radio" class="form_check_radio" name="indAcumulavelIettr" value="S" <%=Pagina.isChecked(itemEstrtIndResul.getIndAcumulavelIettr(), "S")%> <%=_disabled%> onclick="javascript:MostraLinha('S');"> Sim
			<input type="radio" class="form_check_radio" name="indAcumulavelIettr" value="N" <%=Pagina.isChecked(itemEstrtIndResul.getIndAcumulavelIettr(), "N")%> <%=_disabled%> onclick="javascript:MostraLinha('N');"> Não
				
					<label class="dica" onmouseover="javascript:viewFieldTip(this, 'mCalcIettirSPAN');" 
						onmouseout="javascript:noViewFieldTip('mCalcIettirSPAN');">
						<img src="../../images/dica.png" align="absmiddle" border="0" 
							onclick="javascript:viewFieldTipPopUp('Fonte')">
							<span id="mAcumulavelSPAN" style="display: none; ">
								Sim - Soma os valores</br>
								N&atilde;o - Não soma valores
							</span>
					</label>						
				
		</td>
		
	</tr>
	<tr>
		<td colspan="2">
			<table id="linha" style="display: none" border="0">
				<td class="label">* Valor final</td>
				<td>
					<input type="radio" class="form_check_radio" name="indValorFinalIettr" value="M" <%=Pagina.isChecked(itemEstrtIndResul.getIndValorFinalIettr(), "M")%> <%=_disabled%>> Maior
					<input type="radio" class="form_check_radio" name="indValorFinalIettr" value="U" <%=Pagina.isChecked(itemEstrtIndResul.getIndValorFinalIettr(), "U")%> <%=_disabled%>> Último
					<!-- MANTIS #0010017 - Porto Alegre - [e-CAR - Metas e Indicadores] Cadastro de Indicadores  					
					<input type="radio" class="form_check_radio" name="indValorFinalIettr" value="N" <%=Pagina.isChecked(itemEstrtIndResul.getIndValorFinalIettr(), "N")%> <%=_disabled%>> Não se aplica
					 -->
				</td>
			</table>
	
		</td>
	</tr>
	<!--  NOVA TELA DE CADASTRO DE INDICADORES  - MANTIS #0011576 -->
 	<tr style="display: none;">
		<td class="label">* Qtde. Prevista Informada por Local?</td>
		<td>
			<!--
	   	    <input type="radio" class="form_check_radio" id="indPrevPorLocal1" name="indPrevPorLocal" onchange="atualizaCheckbox();disableTabela(this);abreJanela('popupQtdePrevista.jsp?codIett=<%=itemEstrutura.getCodIett()%>&codIettir=<%=itemEstrtIndResul.getCodIettir()%>','1000','600','Quantidade Prevista por Local e Exercício');" value="S" <%=Pagina.isChecked(itemEstrtIndResul.getIndPrevPorLocal(), "S")%> <%=_disabledPrevQtdePorLocal%>> Sim  
	   	    -->
			<%
			String mostraBotoes;			
			if (tipoAcao.equals("alterar"))
			{
				mostraBotoes = "S";
			}
			else
			{
				mostraBotoes = "N";
			}
			
			%>
	   	    <input type="radio" class="form_check_radio" id="indPrevPorLocal1" name="indPrevPorLocal" value="S" <%=Pagina.isChecked(itemEstrtIndResul.getIndPrevPorLocal(), "S")%> <%=_disabledPrevQtdePorLocal%> onmouseout="atualizarInfPrevistoLocal();disableTabela(this.form);desativaServicoPrevisto(true);" onclick="javascript:mostraBotoesPorLocal('<%=mostraBotoes%>', 'N');controlaNivelAbrangencia();"> Sim  

			<input type="radio" class="form_check_radio" id="indPrevPorLocal2" name="indPrevPorLocal" value="N" <%=Pagina.isChecked(itemEstrtIndResul.getIndPrevPorLocal(), "N")%> <%=_disabledPrevQtdePorLocal%> onmouseout="atualizarInfPrevistoLocal();enableTabela(this.form);desativaServicoPrevisto(false);" onclick="javascript:mostraBotoesPorLocal('N', 'S');controlaNivelAbrangencia();"> Não
			<%
//			if(!"".equals(_disabledPrevQtdePorLocal)){
//				<input type="hidden" name="indPrevPorLocal" value="<% //Pagina.trocaNull(itemEstrtIndResul.getIndPrevPorLocal())">
	%>

				<% 	//_msgPrevQtdePorLocal	%>
			<%
			//}
			List listaDeExercicio = exercicioDao.getExerciciosValidos(itemEstrutura.getCodIett());
			final int larguraLocal = 250;
			final int larguraCampo = 150;
			final int larguraTotal = 200;
			%>
		    <!-- Comentado porque nao funciona no Internet Explorer -->
			<!-- input type="button" id="btnInfPrevistoLocal" value="Informar Quantidade/Valor Previsto por Local" disabled="</%=_disabled%>" onclick="popup = abreJanela('popupQtdePrevista.jsp?codIett=</%=itemEstrutura.getCodIett()%>&amp;codIettir=</%=itemEstrtIndResul.getCodIettir()%>','</%=(larguraLocal + listaDeExercicio.size()*larguraCampo + larguraTotal) %>','600','Quantidade Prevista por Local e Exercício');" /-->	
			<!-- input type="button" id="btnInfPrevistoLocal" value="Informar Quantidade/Valor Previsto por Local" disabled="</%=disabled%>" onclick="abrirPrevistoLocal('</%=itemEstrutura.getCodIett()%>','</%=itemEstrtIndResul.getCodIettir()%>','2010','</%=(larguraLocal + listaDeExercicio.size()*larguraCampo + larguraTotal) %>')" --> 

			<script language="JavaScript">
			function abrirPrevistoLocal(codIett, codIettir, ano, tam) {

				if((form.indPrevPorLocal[0].checked)&&(form.abrangenciaLocal.selectedIndex < 1)){
			        alert("<%=_msg.getMensagem("itemEstrutura.indResultado.validacao.prevRealLocal.obrigatorio")%>");
					return(false);
				}
				if(document.getElementById("indTipoQtde1").checked)
					tipoQtd="Q";
				else
					tipoQtd="V";
					
				abreJanela("popupQtdePrevista.jsp?tipoQuantidade="+tipoQtd+"&abrangenciaLocal="+form.abrangenciaLocal.value+"&codIett="+codIett+"&codIettir="+codIettir+"&ano="+ano,tam , 600 , "Quantidade_Prevista_por_Local_e_Exercício");			
			}
			</script>
			
			<br>
			<span id="labelAviso" ></span>
		</td>
	</tr>	
	<!--  NOVA TELA DE CADASTRO DE INDICADORES  - MANTIS #0011576  -->	

	<tr style="display: none;">
		<td class="label">* Qtde. Realizada Informada por Local?</td>
		<td>
			<input type="radio" class="form_check_radio" id="indRealPorLocal1" name="indRealPorLocal" value="S" <%=Pagina.isChecked(itemEstrtIndResul.getIndRealPorLocal(), "S")%> <%=_disabledQtdePorLocal%> onchange="controlaNivelAbrangencia();desativaServicoRealizado(true);"> Sim
			<input type="radio" class="form_check_radio" id="indRealPorLocal2" name="indRealPorLocal" value="N" <%=Pagina.isChecked(itemEstrtIndResul.getIndRealPorLocal(), "N")%> <%=_disabledQtdePorLocal%> onchange="controlaNivelAbrangencia();desativaServicoRealizado(false);"> Não
			<%
			if((!"".equals(_disabledQtdePorLocal))&&!(((itemEstrutura.getItemEstrutLocalIettls() == null)||(itemEstrutura.getItemEstrutLocalIettls().size() == 0)))){
			%>
				<input type="hidden" name="indRealPorLocal" value="<%=Pagina.trocaNull(itemEstrtIndResul.getIndRealPorLocal())%>">
				<br>
				<%=_msgQtdePorLocal%>
			<%
			}
			%>
		</td>
	</tr>

	<tr style="display: none;">
		<td class="label">Nível de abrangência do previsto/realizado por local</td>
		<td>
		<%
		
		List<LocalGrupoLgp> lista = new ArrayList(iettIndResulDao.getGrupoLocaisItem(itemEstrutura));

		String _disabledComboAbrangencia;
		
		 if ((itemEstrutura.getItemEstrutLocalIettls() == null)||(itemEstrutura.getItemEstrutLocalIettls().size() == 0)){
				_disabledComboAbrangencia = "disabled";
		}
		 else

		if (tipoAcao.equals("alterar"))
		{
			Boolean podeAlterarNivelAbrangencia = iettIndResulDao.podeAlterarNivelAbrangencia(itemEstrtIndResul);
			if (podeAlterarNivelAbrangencia){
				_disabledComboAbrangencia = _disabled;
			}
			else
				_disabledComboAbrangencia = "disabled";
		}
		else
		{
			_disabledComboAbrangencia = _disabled;
		}
		
						
		
		
		String selecao;
		if (itemEstrtIndResul.getNivelAbrangencia() != null)
		{
			selecao = itemEstrtIndResul.getNivelAbrangencia().toString();
		}
		else
		{
			selecao = "";
		}   
		
		String scriptsCombo = _disabledComboAbrangencia + " onchange=\"javascript:atualizaValor()\"";

		%>
 				<combo:ComboTag 
					nome="abrangenciaLocal"
					objeto="ecar.pojo.LocalGrupoLgp"
					label="identificacaoLgp"
					value="codLgp" 
					order="identificacaoLgp"
                    colecao="<%=lista%>"
					selected="<%=selecao%>"
					scripts="<%=scriptsCombo%>"
				/>		
		
		</td>
	</tr>
	<tr>
		<td>
			<input type="hidden" class="form_check_radio" name="indPublicoIettr" value="S">
		</td>
	</tr>
	<tr style="display: none;">
		<td class="label"> Nome do agrupamento para o Gráfico de Grupos</td>
		<td>
			<input type="text" id="labelGraficoGrupoIettir" name="labelGraficoGrupoIettir" maxlength="20" size="20" value="<%=Pagina.trocaNull(itemEstrtIndResul.getLabelGraficoGrupoIettir())%>" <%=_disabled%>>
			<input type="hidden" id="nomesGraficoGrupo" value="<%=nomesGraficoGrupo.toString()%>"> 
		</td>
	</tr>
	<tr>
		<td class="label">Periodicidade de Acompanhamento</td>
		<td>
		
			<%
			PeriodicidadePrdc periodicidadeSel = itemEstrtIndResul.getPeriodicidadePrdc();
			
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
	<!-- 
	<tr>
		<td class="label">F&oacute;rmula</td>
		<td>
			<textarea name="formulaIettr" rows="4" cols="60" <%=_readOnly%> 
			onkeyup="return validaTamanhoLimite(this, 2000);"
			onkeydown="return validaTamanhoLimite(this, 2000);"
			onblur="return validaTamanhoLimite(this, 2000);"><%=Pagina.trocaNull(itemEstrtIndResul.getFormulaIettr())%></textarea>
		</td>
	</tr>	
	<tr>
		<td class="label">Informações Complementares</td>
		<td><textarea name="descricaoIettir"  rows="4" cols="60" 
			onkeyup="return validaTamanhoLimite(this, 2000);" 
			onkeydown="return validaTamanhoLimite(this, 2000);"
			onblur="return validaTamanhoLimite(this, 2000);" <%=_readOnly%>><%=Pagina.trocaNull(itemEstrtIndResul.getDescricaoIettir())%></textarea></td>
	</tr>
	 -->
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
				<tr style="display: none;">
					<td class="label">Preenchimento dos Dados</td>
					<td >&nbsp;</td>
				</tr>
				
				<tr style="display: none;">
					<td> &nbsp;</td>
					<td > Valor Previsto: &nbsp;
						<select name="previstoServicoSer" id="previstoServicoSer" style="width: 260px" <%=_disabled%>>
							<option value=""> </option>
							<%
							while(itServicos.hasNext() ){ 
								servico = (ServicoSer) itServicos.next();
							 %>
							<option value="<%=servico.getCodServicoSer().intValue() %>"
							<%if(itemEstrtIndResul.getPrevistoServicoSer() != null && itemEstrtIndResul.getPrevistoServicoSer().getCodServicoSer().equals(servico.getCodServicoSer())) out.println("selected");%>>
							<%=servico.getNomeSer() %>
							</option>
							<% } %>
						</select> 
						&nbsp;
						<label><input type="radio" name="indTipoAtualizacaoPrevisto" value="A" <%= Pagina.isChecked(Pagina.trocaNull(itemEstrtIndResul.getIndTipoAtualizacaoPrevisto()),"A" ) %> <%=_disabled%>> Autom&aacute;tico </label>
						<label><input type="radio" name="indTipoAtualizacaoPrevisto" value="M" <%= Pagina.isChecked(Pagina.trocaNull(itemEstrtIndResul.getIndTipoAtualizacaoPrevisto()),"M") %> <%=_disabled%>> Manual</label>&nbsp;
						
						<input type="button" value="Limpar" onclick="javascript:limparRadioButtons(indTipoAtualizacaoPrevisto)"  <%=_disabled%>>   
					</td>
				</tr>

				<tr style="display: none;">
					<td>&nbsp;</td>
					<td> Valor Realizado: 
						<select name="realizadoServicoSer" id="realizadoServicoSer" style="width: 260px" <%=_disabled%>>
							<option value=""> </option>
							<%
							itServicos = servicos.iterator();
							while(itServicos.hasNext() ){ 
								servico = (ServicoSer) itServicos.next();
							 %>
							<option value="<%=servico.getCodServicoSer().intValue() %>"
							<%if(itemEstrtIndResul.getRealizadoServicoSer() != null && itemEstrtIndResul.getRealizadoServicoSer().getCodServicoSer().equals(servico.getCodServicoSer())) out.println("selected");%>>
							<%=servico.getNomeSer() %> 
							</option>
							<% } %>
						</select> 
						&nbsp;
						<label><input type="radio" name="indTipoAtualizacaoRealizado" value="A" <%= Pagina.isChecked(Pagina.trocaNull(itemEstrtIndResul.getIndTipoAtualizacaoRealizado()), "A")  %> <%=_disabled%>> Autom&aacute;tico </label>
						<label><input type="radio" name="indTipoAtualizacaoRealizado" value="M" <%= Pagina.isChecked(Pagina.trocaNull(itemEstrtIndResul.getIndTipoAtualizacaoRealizado()), "M")  %> <%=_disabled%>> Manual</label>&nbsp;
						
						<input type="button" value="Limpar" onclick="javascript:limparRadioButtons(indTipoAtualizacaoRealizado)"  <%=_disabled%>>   
					</td>
				</tr>

<% } // fim if itServicos.hasNext() %>
<!--	</td>
	</tr>
 -->
 	<tr>
		<td class="label">Valor inicial de refer&ecirc;ncia</td>
		<td><input type="text" name="indiceMaisRecenteIettr" class="indiceMaisRecenteIettr"
			maxlength="14" 
			size="15" 
			value="<%=Pagina.trocaNullQtdValor(itemEstrtIndResul.getIndiceMaisRecenteIettr(), itemEstrtIndResul.getIndTipoQtde())%>" 
			<%=_disabled%> ></td><%//onblur="javascript:validarQtdeValor(form,this)" %>
	</tr>

	<tr>
		<td class="label">Data de Apura&ccedil;&atilde;o</td>
		<td>
			<input type="text" name="dataApuracaoIettr" size="13" maxlength="10" value="<%=Pagina.trocaNullData(itemEstrtIndResul.getDataApuracaoIettr())%>" onkeyup="mascaraData(event, this);" onblur="validaData(this.form.dataApuracaoIettr, true, true, false);" <%=_disabled%>>
			<!-- Adição de componente de Calendário Samuel M. de Oliveira -->
			<c:if test="<%=_disabled.equals("")%>">
				<img class="posicao" title="Selecione a data" src="../../images/icone_calendar.gif" onclick="open_calendar('data', document.forms[0].dataApuracaoIettr, '')">
			</c:if>
		</td>		
	</tr>

	<%
	String checked_ind_sinalizacao = "";
	if(itemEstrtIndResul.getIndSinalizacaoIettr() != null && itemEstrtIndResul.getIndSinalizacaoIettr().equals("S")){
		checked_ind_sinalizacao = "checked";
	}
	 %>
	<tr style="display: none;">
		<td class="label">Utiliza Sinalização</td>
		<td>
			<input type="checkbox" name="ind_sinalizacao" id="ind_sinalizacao" onclick="mostraSinalizadores()" value="N" <%=_disabled%> <%=checked_ind_sinalizacao%> >
		</td>
	</tr>
	<!-- 
	<tr id="sinalizacao1" style="display:">
		<td class="label">Estados</td>
		<td>&nbsp;</td>
	</tr>
	 -->
	<tr id="sinalizacao2" style="display:none">
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
			UsuarioUsu usuarioImagem = null;
			String hashNomeArquivo = null;
			
			if (listCoresCombo != null){
				Iterator itCoresCombo = listCoresCombo.iterator();
				String src = "";
				String title = "";
				while (itCoresCombo.hasNext()){
					Cor corCombo = (Cor) itCoresCombo.next();
					src = "";
					title = "";
					String imagePathIndResul = cDao.getImagemPersonalizadaIndResul(corCombo);
					if( imagePathIndResul != null ) {
						
						usuarioImagem = ((ecar.login.SegurancaECAR)request.getSession().getAttribute("seguranca")).getUsuario();  
						
						hashNomeArquivo = Util.calcularHashNomeArquivo(null, imagePathIndResul);
						Util.adicionarMapArquivosAtuaisUsuarios(usuarioImagem, hashNomeArquivo, null, imagePathIndResul);
						
						src = request.getContextPath()+"/DownloadFile?tipo=open&RemoteFile="+ hashNomeArquivo; 
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
			
			List listCores = new ItemEstrtIndResulCorIettrcorDAO(request).listarCoresIettr(itemEstrtIndResul);
						
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
				id = new ItemEstrtIndResulCorIettrcorPK(itemEstrtIndResul.getCodIettir(), cor.getCodCor());
				
				if("S".equals(Pagina.getParamStr(request, "existeNomeGraficoGrupo"))){
					Set listaResulCor = itemEstrtIndResul.getItemEstrtIndResulCorIettrcores();
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
					iettrcor = (ItemEstrtIndResulCorIettrcor)new ItemEstrtIndResulCorIettrcorDAO(request).buscar(cor, itemEstrtIndResul);
				}
				
				if (codigosCor != null && !codigosCor.equals("")){
					codigosCor = codigosCor + "@@" + cor.getCodCor();
				} else { 
					codigosCor = cor.getCodCor().toString();
				}
				
				 
				%>
				<tr>
				
				<td valign="middle" class="form_label" align="center">
						<input type="checkBox" class="form_check_radio" value="S" name="ativo[<%=count%>]" onclick="atualizarMenorValor()" id="ativo[<%=count%>]"
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
						if( imagePath != null ) {
							
							// código de tratamento de permissão de acesso a arquivo
							hashNomeArquivo = Util.calcularHashNomeArquivo(null, imagePath);
							Util.adicionarMapArquivosAtuaisUsuarios(usuarioImagem, hashNomeArquivo, null, imagePath);	
							
							/* -- As tags do CSS "max-width" e "max-height" não funcionam no IE6 ou menor -- */
							%>
							&nbsp;
							<span id="span_img_<%=count%>" style="display:none">
								
								<img id="img_<%=count%>" border="0" src="<%=request.getContextPath()+"/DownloadFile?tipo=open&RemoteFile="+ hashNomeArquivo%>" title="<%=iettrcor.getCor().getSignificadoCor()%>">
							
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
						
					 %
						
					</td>					
					
					
					</tr>
				<%		
				
			}
		%>	
		<input type="hidden" name="quantidadeCores" id="quantidadeCores" value="<%=count%>"> 		
		</table>
		</td>
	</tr>	
	<tr>
		<td class="label" >Metas - Quantidades Previstas</td>
		<td>
		</td>
	</tr> 	
	<tr>
		<td colspan="2" align="center">
			<div id="qtdeExercLocal" >
				<%@ include file="form_qtdPrevista.jsp"%>
			</div>
			
		</td><td>
	</td></tr>
	<tr>
		<td colspan="2">
			&nbsp;
		</td>
	</tr>

	
<!--  ****************************************************************  -->
<!--  Função autocomplete para montar o campo "labelGraficoGrupoIettir" -->
<!--  ****************************************************************  -->
<script language="JavaScript">

/**
 * Recupera os valores do hidden nomesGraficoGrupo 
 */
function recuperaNomesGraficoGrupo(){
	var separador = '**';
	var inputHidden = document.getElementById("nomesGraficoGrupo").value; 
	return inputHidden.split(separador);

}

$(document).ready(
function () {
	
	var arrayNomes = recuperaNomesGraficoGrupo();
	
	$("#labelGraficoGrupoIettir").autocompleteArray(
		arrayNomes,
		{
			delay:10,
			minChars:1,
			matchSubset:1,
			autoFill:true,
			maxItemsToShow:10
		}
	);
}
);
</script>

<script language="JavaScript">


	


function atualizaSinalizador(contImagem, indiceSelecionado){
	pathCores = '<%=pathCores%>';
	pathVetorCores = pathCores.split('@@');

	titleCores = '<%=titleCores%>';
	titleVetorCores = titleCores.split('@@');
	
	
	if (indiceSelecionado != 0){
		document.getElementById('img_' + contImagem).src = pathVetorCores[indiceSelecionado - 1];
		document.getElementById('img_' + contImagem).title = titleVetorCores[indiceSelecionado - 1];
		document.getElementById('span_img_' + contImagem).style.display = '';
	} else {
		document.getElementById('img_' + contImagem).src = '';
		document.getElementById('img_' + contImagem).title = '';
		document.getElementById('span_img_' + contImagem).style.display = 'none';
	}
	
}

function mostraImagemSinalizador(){
	qtdCores = <%=count%>;
	for (i = 1; i <= qtdCores; i++){
		if (document.getElementById('cor_' + i).selectedIndex != 0){
			document.getElementById('span_img_' + i).style.display = '';
		} else {
			document.getElementById('span_img_' + i).style.display = 'none';
		}
	
	}

}

function mostraSinalizadores(){
	if(document.getElementById("ind_sinalizacao").checked){
		document.getElementById("sinalizacao1").style.display='';
		document.getElementById("sinalizacao2").style.display='';
	} else{
		document.getElementById("sinalizacao1").style.display='none';
		document.getElementById("sinalizacao2").style.display='none';
	}	
}

function validaCamposVariaveis(form){
	<%
	if(configuramento.getSisGrupoAtributoSgaByCodSgaGrAtrMetasFisicas() != null){
		out.println(new ecar.dao.SisGrupoAtributoDao(request).getValidacoesAtributos(grupoMetasObrigatorios));
	}
	if(configuramento.getSisGrupoAtributoSgaByUnidMedida() != null) {
		out.println(new ecar.dao.SisGrupoAtributoDao(request).getValidacoesAtributos(grupoUnidadesObrigatorios));
	}
	
	%>
	return true;
}

function validaQuantidadePrevista(form){
<%=validaQtdPrev%>
	return(true);
}

function MostraLinha(opt) {
  if (opt == "N") {
     document.getElementById('linha').style.display='';
  } else if (opt == "S") {
     document.getElementById('linha').style.display='none';
     document.form.indValorFinalIettr[0].checked = false;
     document.form.indValorFinalIettr[1].checked = false;
  }
}

function atualizarMenorValor(){

	qtdCores = <%=count%>;
	
	atualizarCheckBox(qtdCores);
}

function mascaraMoeda(){
    var numero = $('#indTipoQtde1').is(":checked");
    if(numero){
    	$('.indiceMaisRecenteIettr').maskMoney('unmasked')[0];
    } else {
    	$('.indiceMaisRecenteIettr').maskMoney({prefix:'R$ ', allowNegative: true, thousands:'.', decimal:',', affixesStay: false});
    }	
}

function atualizarCheckBox(qtdCores){
	disabled = '<%=_disabled%>';
	if (disabled == ''){
	
		for (i = 1; i <= qtdCores; i++){
		
			if (i == 1){
				document.getElementById('ativo[' + i + ']').disabled = false;			
				if (document.getElementById('ativo[' + i + ']').checked){
					document.getElementById('ativo[' + (i + 1)+ ']').disabled = false;
					document.getElementById('valor_' + i).disabled = false;
					document.getElementById('cor_' + i).disabled = false;
					document.getElementById('valor_anterior_' + i).value = 'Menor Valor';
					if (!document.getElementById('ativo[' + (i + 1)+ ']').checked){
						document.getElementById('valor_' + i).value = 'Maior Valor';
						document.getElementById('valor_' + i).disabled = true;
					}
					
				} else {
					document.getElementById('ativo[' + (i + 1)+ ']').disabled = true;
					document.getElementById('valor_anterior_' + i).value = 'Menor Valor';
					document.getElementById('valor_' + i).value = 'Maior Valor';
					document.getElementById('valor_' + i).disabled = 'true';			
					document.getElementById('span_img_' + i).style.display = 'none';
					document.getElementById('cor_' + i).selectedIndex = 0;
					document.getElementById('cor_' + i).disabled = true;
					
				}
				
			} else if (i != qtdCores){
				if (document.getElementById('ativo[' + i + ']').checked){
					document.getElementById('ativo[' + (i - 1)+ ']').disabled = true;
					document.getElementById('ativo[' + (i + 1)+ ']').disabled = false;
					document.getElementById('cor_' + i).disabled = false;
					if (document.getElementById('valor_' + (i - 1) ).value == 'Maior Valor'){
						document.getElementById('valor_' + (i - 1) ).value = '';
					} 
					
					document.getElementById('valor_anterior_' + i).value = document.getElementById('valor_' + (i - 1)).value;
					document.getElementById('valor_' + (i - 1)).disabled = false;
					
					if (!document.getElementById('ativo[' + (i + 1)+ ']').checked){
						document.getElementById('valor_' + i).value = 'Maior Valor';
						document.getElementById('valor_' + i).disabled = true;
					}
					
					
				} else {
					document.getElementById('ativo[' + (i + 1)+ ']').disabled = true;
					
					document.getElementById('valor_anterior_' + i).value = '';
					document.getElementById('valor_' + i).value = '';
					
					document.getElementById('span_img_' + i).style.display = 'none';
					document.getElementById('cor_' + i).selectedIndex = 0;
					document.getElementById('cor_' + i).disabled = true;
					document.getElementById('valor_' + i).disabled = true;
						
				}
			} else if (i == qtdCores){
				if (document.getElementById('ativo[' + i + ']').checked){
					document.getElementById('ativo[' + (i - 1) + ']').disabled = true;
					document.getElementById('valor_' + i).value = 'Maior Valor';
					document.getElementById('cor_' + i).disabled = false;
					document.getElementById('valor_' + i).disabled = true;
					if (document.getElementById('valor_' + (i - 1) ).value == 'Maior Valor'){
						document.getElementById('valor_' + (i - 1) ).value = '';
						document.getElementById('valor_' + (i - 1) ).disabled = false;
					} 
					
					document.getElementById('valor_anterior_' + i).value = document.getElementById('valor_' + (i - 1)).value;
					
					
				} else {
					document.getElementById('valor_anterior_' + i).value = '';
					document.getElementById('valor_' + i).value = '';
					
					document.getElementById('span_img_' + i).style.display = 'none';
					document.getElementById('cor_' + i).selectedIndex = 0;
					document.getElementById('cor_' + i).disabled = true;
					document.getElementById('valor_' + i).disabled = true;
				}
			}
		}
	} else {
		for (i = 1; i <= qtdCores; i++){
		
			if (i == 1){	
				if (document.getElementById('ativo[' + i + ']').checked){
					document.getElementById('valor_anterior_' + i).value = 'Menor Valor';
					if (!document.getElementById('ativo[' + (i + 1)+ ']').checked){
						document.getElementById('valor_' + i).value = 'Maior Valor';
					}
					
				} else {
					document.getElementById('valor_anterior_' + i).value = 'Menor Valor';
					document.getElementById('valor_' + i).value = 'Maior Valor';	
					document.getElementById('span_img_' + i).style.display = 'none';
					document.getElementById('cor_' + i).selectedIndex = 0;					
				}
				
			} else if (i != qtdCores){
				if (document.getElementById('ativo[' + i + ']').checked){
					if (document.getElementById('valor_' + (i - 1) ).value == 'Maior Valor'){
						document.getElementById('valor_' + (i - 1) ).value = '';
					} 
					
					document.getElementById('valor_anterior_' + i).value = document.getElementById('valor_' + (i - 1)).value;
					
					if (!document.getElementById('ativo[' + (i + 1)+ ']').checked){
						document.getElementById('valor_' + i).value = 'Maior Valor';
					}
					
					
				} else {
					
					document.getElementById('valor_anterior_' + i).value = '';
					document.getElementById('valor_' + i).value = '';
					
					document.getElementById('span_img_' + i).style.display = 'none';
					document.getElementById('cor_' + i).selectedIndex = 0;
				}
			} else if (i == qtdCores){
				if (document.getElementById('ativo[' + i + ']').checked){
					document.getElementById('valor_' + i).value = 'Maior Valor';
					if (document.getElementById('valor_' + (i - 1) ).value == 'Maior Valor'){
						document.getElementById('valor_' + (i - 1) ).value = '';
					} 
					
					document.getElementById('valor_anterior_' + i).value = document.getElementById('valor_' + (i - 1)).value;
					
					
				} else {
					document.getElementById('valor_anterior_' + i).value = '';
					document.getElementById('valor_' + i).value = '';
					
					document.getElementById('span_img_' + i).style.display = 'none';
					document.getElementById('cor_' + i).selectedIndex = 0;
				}
			}
		}
	
	}
} 


atualizarMenorValor();
mostraSinalizadores();
mostraImagemSinalizador();
mascaraMoeda();

</script>
	
	<%@ include file="../../include/estadoMenu.jsp"%>
