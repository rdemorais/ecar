<%@ page import="java.util.List"%>
<%@ page import="ecar.dao.TipoAcompanhamentoDao"%>
<%@ page import="ecar.pojo.TipoAcompanhamentoTa"%>
<%@ page import="java.util.Iterator"%>
<%@ page import="ecar.dao.EstAtribTipoAcompEataDao"%>
<%@ page import="ecar.pojo.EstAtribTipoAcompEata"%>
<jsp:directive.page import="ecar.dao.TipoFuncAcompDao"/>
<jsp:directive.page import="ecar.pojo.EstruturaAtributoEttat"/>
<%@page import="java.util.Set"%>
<jsp:directive.page import="ecar.pojo.FuncaoFun"/>
<jsp:directive.page import="ecar.dao.FuncaoDao"/>


<%@ taglib  uri="/WEB-INF/ecar-combo.tld" prefix="combo" %>

<jsp:directive.page import="ecar.pojo.TipoFuncAcompTpfa"/>
<jsp:directive.page import="comum.util.Pagina"/>
<jsp:directive.page import="ecar.dao.TipoAcompFuncAcompDao"/>
<jsp:directive.page import="ecar.pojo.TipoAcompFuncAcompTafc"/>
<%
/*
 * arquivo comum para as telas de inclusao, alteracao, exclusao e pesquisa 
 * observar a variavel _disabled e setar o objeto corretamente pelo construtor
 * assinalando os campos com os valores iniciais para inclusao
 */ 
%>

<script language="javascript">
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

<table class="form">

	<util:msgObrigatorio obrigatorio="<%=_obrigatorio%>" />

	<tr>
		<td class="label">Estrutura</td>
		<td>
<%
	String scripts = _disabled + " onchange=reload(document.forms[0]);";
	FuncaoDao funcaoDao = new FuncaoDao(request);
	FuncaoFun funcaoDadosGerais = funcaoDao.getFuncaoDadosGerais();
	FuncaoFun funcaoPontosCriticos = funcaoDao.getFuncaoPontosCriticos();
	
	String filters = "indPossuiAtributos=S";
%>
			<combo:ComboTag nome="estruturaEtt" objeto="ecar.pojo.EstruturaEtt"
				label="nomeEtt,siglaEtt" value="codEtt" order="nomeEtt"
				scripts="<%=scripts%>" selected="<%=selectedEstrutura%>"
				colecao="<%=listaEstrutura%>" />
		</td>
	</tr>
	<tr>
		<td class="label">Função</td>
		<td>
			<combo:ComboTag nome="funcaoFun" objeto="ecar.pojo.FuncaoFun"
				label="labelPadraoFun" value="codFun" order="labelPadraoFun"
				scripts="<%=scripts%>" selected="<%=selectedFuncao%>"
				colecao="<%=listaFuncao%>" />
		</td>
	</tr>
	<tr>
	<tr>
		<td class="label">Atributo</td>
		<td>
			<combo:ComboTag nome="atributosAtb" objeto="ecar.pojo.AtributosAtb"
				label="labelPadraoAtb" value="codAtb" order="labelPadraoAtb"
				scripts="<%=scripts%>" selected="<%=selectedAtributo%>"
				colecao="<%=listaAtributo%>" />
		</td>
	</tr>
	<tr>
		<td class="label">Label a utilizar no n&iacute;vel</td>
		<td>
			<input type="text" name="labelEstruturaEttat" size="42"
				maxlength="40"
				value="<%=Pagina.trocaNull(estruturaAtributo.getLabelEstruturaEttat())%>"
				<%=_disabled%>>
		</td>
	</tr>

	<tr>
		<td class="label">Tamanho do conteúdo</td>
		<td>
			<input type="text" name="labelTamanhoConteudo" size="4" maxlength="4"
				value="<%=Pagina.trocaNull(estruturaAtributo.getTamanhoConteudoAtribEttat())%>"
				<%=_disabled%>>
		</td>

	</tr>

	<!-- *************************** Tela de Cadastramento ************************** -->
	<tr>
		<td class="label">
			Tela Cadastramento
		</td>
		<td>
			&nbsp;
		</td>
	</tr>
	<tr>
		<td class="label">&nbsp;</td>
		<td>
			<table class="form">
				<tr>
					<td width="110px">Obrigat&oacute;rio</td>
					<td>
					
						<%
						if (ehPesquisa) {
						%>
										
							<input type="radio" class="form_check_radio" name="indObrigatorioEttat" value="<%=Pagina.SIM%>">
															
									Sim &nbsp;
							<input type="radio" class="form_check_radio" name="indObrigatorioEttat" value="<%=Pagina.NAO%>">
														
									Não &nbsp;
							<input type="button" value="Limpar" onclick="javascript:limparRadioButtons(indObrigatorioEttat)">
							<br>
							
						<%
						} else {
						%> 
							<input type="checkbox" class="form_check_radio" name="indObrigatorioEttat" value="<%=Pagina.SIM%>"
							<%=Pagina.isChecked(estruturaAtributo.getIndObrigatorioEttat(), Pagina.SIM)%>
							<%=_pesqdisabled%> <%=obrigatorioDisabled%> >
								
						<% 
						}
						%>
					
					</td>
				</tr>

				<!-- Inicio SERPRO 17/08/07 -->
				<tr>
					<td width="110px">Pode ser Bloqueado?</td>
					<td>
					
						<%
						if (ehPesquisa) {
						%>
										
							<input type="radio" class="form_check_radio" name="indPodeBloquearEttat" value="<%=Pagina.SIM%>">
															
									Sim &nbsp;
							<input type="radio" class="form_check_radio" name="indPodeBloquearEttat" value="<%=Pagina.NAO%>">
														
									Não &nbsp;
							<input type="button" value="Limpar" onclick="javascript:limparRadioButtons(indPodeBloquearEttat)">
							<br>
							
						<%
						} else {
						%>
							<input  type="checkbox" class="form_check_radio" name="indPodeBloquearEttat" id="indPodeBloquearEttat" value="<%=Pagina.SIM%>"
							<%=Pagina.isChecked(estruturaAtributo.getIndPodeBloquearEttat(), Pagina.SIM)%>
							<%=_pesqdisabled%> 
							onclick="exibeEscondeListFuncaoAcomp('indPodeBloquearEttat','lstFuncAcomp');">
								
						<% 
						}
						%>
					
						
						<br />
					</td>
				</tr>
				<%  
					String displayFuncAcomp = "none";
					Set setTpfa = null; 
					if (estruturaAtributo != null && Pagina.trocaNull(estruturaAtributo.getIndPodeBloquearEttat()).equals(Pagina.SIM)){
						displayFuncAcomp = "inline";
						setTpfa = estruturaAtributo.getLibTipoFuncAcompTpfas();
					} 
				
				 %>
				<tr >
					<td style="vertical-align: text-top;"> <span id="tdLiberado" style="display: <%=displayFuncAcomp %>; vertical-align:top;"> Est&aacute; liberado para</span> </td>
					<td> <table id="lstFuncAcomp" style="display: <%=displayFuncAcomp %> ;margin: 0; padding: 0; border: 0"><tr><td>
						<combo:CheckListTag 
							nome="limbTipoFuncAcompTpfa"
							objeto="ecar.pojo.TipoFuncAcompTpfa" 
							label="labelTpfa" 
							value="codTpfa" 
							order="labelTpfa"
							selected="<%=setTpfa %>"
							scripts="<%=_disabled%>"
							
						/>
					</td></tr></table>
					</td>
				</tr>
				<!-- Fim SERPRO 17/08/07 -->

				<tr>
					<td width="110px"><%=_obrigatorio%> Seq&uuml;&ecirc;ncia</td>
					<td>
						<input type="text" name="seqApresentTelaCampoEttat" size="6"
							maxlength="4"
							value="<%=Pagina.trocaNull(estruturaAtributo.getSeqApresentTelaCampoEttat())%>"
							<%=_disabled%>>
						(Informe o valor 0 para não apresentar o campo na tela de
						cadastro, se o mesmo não for opcional)
					</td>
				</tr>
			</table>
		</td>
	</tr>

	<!-- *************************** Lista do Cadastro de Itens ************************** -->
	<tr>
		<td class="label">
			Lista do Cadastro de Itens
		</td>
		<td>
			&nbsp;
		</td>
	</tr>
	<tr>
		<td class="label">
			&nbsp;
		</td>
		<td>
			<table class="form">
				<tr>
					<td width="110px">Exibir na Lista</td>
					<td>
					
						<%
						if (ehPesquisa) {
						%>
										
							<input type="radio" class="form_check_radio" name="indListagemTelaEttat" value="<%=Pagina.SIM%>">
															
									Sim &nbsp;
							<input type="radio" class="form_check_radio" name="indListagemTelaEttat" value="<%=Pagina.NAO%>">
														
									Não &nbsp;
							<input type="button" value="Limpar" onclick="javascript:limparRadioButtons(indListagemTelaEttat)">
							<br>
							
						<%
						} else {
						%>
							<input type="checkbox" class="form_check_radio" name="indListagemTelaEttat" value="<%=Pagina.SIM%>"
							<%=Pagina.isChecked(estruturaAtributo.getIndListagemTelaEttat(), Pagina.SIM)%>
							<%=_pesqdisabled%>>
								
						<% 
						}
						%>
					
						
				</tr>
				
				<tr>
					<td width="110px">Exibir na Árvore</td>
					<td>
					
						<%
						if (ehPesquisa) {
						%>
										
							<input type="radio" class="form_check_radio" name="indListagemArvoreEttat" value="<%=Pagina.SIM%>">
															
									Sim &nbsp;
							<input type="radio" class="form_check_radio" name="indListagemArvoreEttat" value="<%=Pagina.NAO%>">
														
									Não &nbsp;
							<input type="button" value="Limpar" onclick="javascript:limparRadioButtons(indListagemArvoreEttat)">
							<br>
							
						<%
						} else {
						%>
							<input type="checkbox" class="form_check_radio" name="indListagemArvoreEttat" value="<%=Pagina.SIM%>"
							
							
							<%if (estruturaAtributo.getAtributosAtb().getFuncaoFun() != null && funcaoPontosCriticos.equals(estruturaAtributo.getAtributosAtb().getFuncaoFun())) {%>
								<%="disabled"%>
							<%} else {%>
								<%=Pagina.isChecked(estruturaAtributo.getIndListagemArvoreEttat(), Pagina.SIM)%>
								<%=_pesqdisabled%>
							<%} %>
							>
								
						<% 
						}
						%>											
				</tr>

				<!-- Inicio SERPRO 17/08/07 -->
				<tr>
					<td width="110px">&Eacute; Filtro?</td>
					<td>
					
						<%
						if (ehPesquisa) {
						%>
										
							<input type="radio" class="form_check_radio" name="indFiltroEttat" value="<%=Pagina.SIM%>">
															
									Sim &nbsp;
							<input type="radio" class="form_check_radio" name="indFiltroEttat" value="<%=Pagina.NAO%>">
														
									Não &nbsp;
							<input type="button" value="Limpar" onclick="javascript:limparRadioButtons(indFiltroEttat)">
							<br>
							
						<%
						} else {
						%>
							<input type="checkbox" class="form_check_radio" name="indFiltroEttat" value="<%=Pagina.SIM%>"
							<%=Pagina.isChecked(estruturaAtributo.getIndFiltroEttat(), Pagina.SIM)%>
							<%=_pesqdisabled%>>
								
						<% 
						}
						%>
											
					</td>
				</tr>
				<!-- Fim SERPRO 17/08/07 -->


				<tr>
					<td width="110px">
						Seq&uuml;&ecirc;ncia
					</td>
					<td>
						<input type="text" name="seqApresListagemTelaEttat" size="6"
							maxlength="4" value="<%=Pagina.trocaNull(estruturaAtributo.getSeqApresListagemTelaEttat())%>"
							<%=_disabled%>>
					</td>
				</tr>
				<tr>
					<td width="110px">
						Largura da Coluna
					</td>
					<td>
						<input type="text" name="larguraListagemTelaEttat" size="5"
							maxlength="2"
							value="<%=Pagina.trocaNull(estruturaAtributo.getLarguraListagemTelaEttat())%>"
							<%=_disabled%>>
					</td>
				</tr>
			</table>
		</td>
	</tr>
	
	<% 
	String nomeFuncao = "";
	if (estruturaAtributo.getAtributosAtb() != null 
		&& estruturaAtributo.getAtributosAtb().getFuncaoFun() != null
		&& estruturaAtributo.getAtributosAtb().getFuncaoFun().getNomeFun() != null){
		
		nomeFuncao = estruturaAtributo.getAtributosAtb().getFuncaoFun().getNomeFun(); 
		
	}
	
	if (ehPesquisa || nomeFuncao.equals("Dados_Gerais")){
	
	
	%>
		<!-- *************************** Impressão do Cadastro de Itens ************************** -->
		<tr>
			<td class="label">
				Impress&atilde;o do Cadastro de Itens
			</td>
			<td>
				&nbsp;
			</td>
		</tr>
		<tr>
			<td class="label">
				&nbsp;
			</td>
			<td>
				<table class="form">
					<tr>
						<td width="110px">Completa</td>
						<td>
						
							<%
							if (ehPesquisa) {
							%>
											
								<input type="radio" class="form_check_radio" name="indListagemImpressCompEtta" value="<%=Pagina.SIM%>">
																
										Sim &nbsp;
								<input type="radio" class="form_check_radio" name="indListagemImpressCompEtta" value="<%=Pagina.NAO%>">
															
										Não &nbsp;
								<input type="button" value="Limpar" onclick="javascript:limparRadioButtons(indListagemImpressCompEtta)">
								<br>
								
							<%
							} else {
							%>
								<input type="checkbox" class="form_check_radio" name="indListagemImpressCompEtta" value="<%=Pagina.SIM%>"
								<%=Pagina.isChecked(estruturaAtributo.getIndListagemImpressCompEtta(), Pagina.SIM)%>
								<%=_pesqdisabled%>>
									
							<% 
							}
							%>
						
							
						</td>
					</tr>
					<tr>
						<td width="110px">Resumo</td>
						<td>
						
							<%
							if (ehPesquisa) {
							%>
											
								<input type="radio" class="form_check_radio" name="indListagemImpressaResEtta" value="<%=Pagina.SIM%>">
																
										Sim &nbsp;
								<input type="radio" class="form_check_radio" name="indListagemImpressaResEtta" value="<%=Pagina.NAO%>">
															
										Não &nbsp;
								<input type="button" value="Limpar" onclick="javascript:limparRadioButtons(indListagemImpressaResEtta)">
								<br>
								
							<%
							} else {
							%>
								<input type="checkbox" class="form_check_radio" name="indListagemImpressaResEtta" value="<%=Pagina.SIM%>"
								<%=Pagina.isChecked(estruturaAtributo.getIndListagemImpressaResEtta(), Pagina.SIM)%>
								<%=_pesqdisabled%>>
									
							<% 
							}
							%>
						
							
						</td>
					</tr>
					<tr>
						<td width="110px">Rela&ccedil;&atilde;o</td>
						<td>
						
							<%
							if (ehPesquisa) {
							%>
											
								<input type="radio" class="form_check_radio" name="indRelacaoImpressaEttat" value="<%=Pagina.SIM%>">
																
										Sim &nbsp;
								<input type="radio" class="form_check_radio" name="indRelacaoImpressaEttat" value="<%=Pagina.NAO%>">
															
										Não &nbsp;
								<input type="button" value="Limpar" onclick="javascript:limparRadioButtons(indRelacaoImpressaEttat)">
								<br>
								
							<%
							} else {
							%>
								<input type="checkbox" class="form_check_radio" name="indRelacaoImpressaEttat" value="<%=Pagina.SIM%>"
								<%=Pagina.isChecked(estruturaAtributo.getIndRelacaoImpressaEttat(), Pagina.SIM)%>
								<%=_pesqdisabled%>>
									
							<% 
							}
							%>
												
						</td>
					</tr>
					<tr>
						<td width="110px">Revis&atilde;o</td>
						<td>
							<%
								String checkedRevisaoSim = Pagina.isChecked(estruturaAtributo.getIndRevisaoEttat(), Pagina.SIM);
							%>
	
							<%
							if (ehPesquisa) {
							%>
											
								<input type="radio" class="form_check_radio" name="indRevisaoEttat" value="<%=Pagina.SIM%>">
																
										Sim &nbsp;
								<input type="radio" class="form_check_radio" name="indRevisaoEttat" value="<%=Pagina.NAO%>">
															
										Não &nbsp;
								<input type="button" value="Limpar" onclick="javascript:limparRadioButtons(indRevisaoEttat)">
								<br>
								
							<%
							} else {
							%>
								<input type="checkbox" class="form_check_radio" name="indRevisaoEttat" value="<%=Pagina.SIM%>"
								<%=checkedRevisaoSim%> <%=_pesqdisabled%>
								onclick="javascript:form.indPermiteRevisaoEttat.checked = form.indRevisaoEttat.checked;">
									
							<% 
							}
							%>
							
						</td>
					</tr>
				</table>
			</td>
		</tr>
	
		<!-- *************************** Aba Revisão ************************** -->
		<tr>
			<td class="label">
				Aba Revis&atilde;o
			</td>
			<td>
				&nbsp;
			</td>
		</tr>
		<tr>
			<td class="label">
				&nbsp;
			</td>
			<td>
				<table class="form">
					<tr>
						<td width="110px">
							Permite Revis&atilde;o
						</td>
						<td>
						
							<%
							if (ehPesquisa) {
							%>
											
								<input type="radio" class="form_check_radio" name="indPermiteRevisaoEttat" value="<%=Pagina.SIM%>">
																
										Sim &nbsp;
								<input type="radio" class="form_check_radio" name="indPermiteRevisaoEttat" value="<%=Pagina.NAO%>">
															
										Não &nbsp;
								<input type="button" value="Limpar" onclick="javascript:limparRadioButtons(indPermiteRevisaoEttat)">
								<br>
								
							<%
							} else {
							%>
								<input type="checkbox" class="form_check_radio" name="indPermiteRevisaoEttat" value="<%=Pagina.SIM%>"
								<%=checkedRevisaoSim%> disabled>
									
							<% 
							}
							%>
						
							
						</td>
					</tr>
				</table>
			</td>
		</tr>
	<%} %>
		<!-- *************************** Tipos de Acompanhamentos ************************** -->
		<tr>
			<td class="label">
				Listar Itens Por Tipo de Acompanhamento
			</td>
			<td>
				&nbsp;
			</td>
		</tr>
		<tr>
			<td class="label">
				&nbsp;
			</td>
			<td>
				<table class="form">
					<tr>
						<td width="150px">
							<b>Tipo de Acompanhamento</b>
						</td>
						<td width="110px">
							<b>Exibir</b>
						</td>
						<td width="60px">
							<b>Seq&uuml;&ecirc;ncia</b>
						</td>
						<td width="110px">
							<b>&Eacute; Filtro?</b>
						</td>
					</tr>
					<%
			List tipoAcomps = new TipoAcompanhamentoDao(request).getTipoAcompanhamentoAtivosBySeqApresentacao();
			Iterator itAcomp = tipoAcomps.iterator();
			String codTas = "";
			while(itAcomp.hasNext()){
				TipoAcompanhamentoTa ta = (TipoAcompanhamentoTa) itAcomp.next();
				
				EstAtribTipoAcompEataDao eataDao = new EstAtribTipoAcompEataDao(request);
				EstAtribTipoAcompEata eata = null;
				
				if(estruturaAtributo != null && estruturaAtributo.getEstruturaEtt() != null && estruturaAtributo.getEstruturaEtt().getCodEtt() != null
				 && estruturaAtributo.getAtributosAtb() != null && estruturaAtributo.getAtributosAtb().getCodAtb() != null)
					 eata = eataDao.getEstAtribTipoAcompEata(estruturaAtributo, ta);
					 
				if(session.getAttribute("objPesquisaTipoAcomp") != null && eata == null){
					List lTipoAcomp = (List) session.getAttribute("objPesquisaTipoAcomp");
					
					Iterator itSessionAcomp = lTipoAcomp.iterator();
					while(itSessionAcomp.hasNext()){
						EstAtribTipoAcompEata sessionEata = (EstAtribTipoAcompEata) itSessionAcomp.next();
						if(ta.equals(sessionEata.getTipoAcompanhamentoTa())){
							eata = sessionEata;
							break;
						}
					}
				}
				
	
				if(eata == null)
					eata = new EstAtribTipoAcompEata();
			%>
					<tr>
						<td width="150px">
							<%=ta.getDescricaoTa()%></td>
						<td width="100px">
						
							<%
							if (ehPesquisa) {
							%>
											
								<input type="radio" class="form_check_radio" name="exibirEata<%=ta.getCodTa().toString()%>" value="<%=Pagina.SIM%>">
																
										Sim 
								<input type="radio" class="form_check_radio" name="exibirEata<%=ta.getCodTa().toString()%>" value="<%=Pagina.NAO%>">
															
										Não <!-- %>&nbsp; -->
								<input type="button" value="Limpar" onclick="javascript:limparRadioButtons(exibirEata<%=ta.getCodTa().toString()%>)">
								<br>
								
							<%
							} else {
							%>
								<input type="checkbox" class="form_check_radio" name="exibirEata<%=ta.getCodTa().toString()%>"
								value="<%=Pagina.SIM%>" <%=Pagina.isChecked(eata.getExibirEata(), Pagina.SIM)%>
								<%=_pesqdisabled%>>
									
							<% 
							}
							%>
												
						</td>
						<td>
							<input type="text"
								name="sequenciaEata<%=ta.getCodTa().toString()%>" size="6"
								maxlength="4"
								value="<%=Pagina.trocaNull(eata.getSequenciaEata())%>"
								<%=_pesqdisabled%>>
						</td>
						<!-- Inicio SERPRO -->
						<td width="100px">
						
							<%
							if (ehPesquisa) {
							%>
											
								<input type="radio" class="form_check_radio" name="filtroEata<%=ta.getCodTa().toString()%>" value="<%=Pagina.SIM%>">
																
										Sim 
								<input type="radio" class="form_check_radio" name="filtroEata<%=ta.getCodTa().toString()%>" value="<%=Pagina.NAO%>">
															
										Não 
								<input type="button" value="Limpar" onclick="javascript:limparRadioButtons(filtroEata<%=ta.getCodTa().toString()%>)">
								<br>
								
							<%
							} else {
							%>
								<input type="checkbox" class="form_check_radio" name="filtroEata<%=ta.getCodTa().toString()%>"
								value="<%=Pagina.SIM%>" <%=Pagina.isChecked(eata.getFiltroEata(), Pagina.SIM)%>
								<%=_pesqdisabled%>>
									
							<% 
							}
							%>
												
						</td>
						<!-- Fim SERPRO -->
					</tr>
					<%
				codTas = codTas + ta.getCodTa().toString() + "|";
			}
			session.removeAttribute("objPesquisaTipoAcomp");
			%>
				</table>
				<input type="hidden" name="codTas" value="<%=codTas%>">
			</td>
		</tr>
		
		<!-- ************************Fim Tipos de Acompanhamentos ************************** -->
	<%//} %>
	
	
	<tr>
		<td class="label">
			Dica do campo
		</td>
		<td>
			<textarea name="dicaEttat" cols="60" rows="4"
				onkeyup="return validaTamanhoLimite(this, 2000);"
				onkeydown="return validaTamanhoLimite(this, 2000);"
				onblur="return validaTamanhoLimite(this, 2000);" <%=_readOnly%>><%=Pagina.trocaNull(estruturaAtributo.getDicaEttat())%></textarea>
		</td>
	</tr>
	<%if (blVisualizaDesc == true){ %>
	<tr>
		<td class="label">
			Documenta&ccedil;&atilde;o
		</td>
		<td>
			<script language="JavaScript" type="text/javascript">
               //Usage: initRTE(imagesPath, includesPath, cssFile, genXHTML)
				initRTE("<%=_pathEcar%>/images/rte/", "<%=_pathEcar%>", "", false);
               </script>

			<script language="JavaScript" type="text/javascript">
               //Usage: writeRichText(fieldname, html, width, height, buttons, readOnly)
               //writeRichText('rte', 'enter body text here', 400, 200, true, false);                          
               writeRichText('rte', "<%=Pagina.trocaNull(estruturaAtributo.getDocumentacaoEttat()).replaceAll("\n", " ").replaceAll("\r", " ").replaceAll("</SCRIPT>","&lt;/SCRIPT&gt;").replaceAll("</script>","&lt;/script&gt;").replaceAll("</script -->","&lt;/script --&gt;").replaceAll("</script -->","&lt;/script --&gt;").replaceAll("</SCRIPT -->","&lt;/SCRIPT --&gt;")%>", 400, 200, true,<%=strReadOnly%>);
               
               </script>

			<input type="hidden" name="richText" id="richText" size="2000">
			<input type="hidden" name="documentacaoEttat" size="2000">
		</td>
	</tr>
<%
}
%>
	<tr>
		<td class="label">
			&nbsp;
			 
		</td>
	</tr>
</table>

<%@ include file="/include/estadoMenu.jsp"%>