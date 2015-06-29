<%@ include file="../login/validaAcesso.jsp"%>
<%@ include file="../frm_global.jsp"%>
<%@ taglib uri="/WEB-INF/tlds/c.tld" prefix="c"%>
<%@page import="ecar.dao.ExercicioDao"%>
<%@page import="ecar.pojo.ExercicioExe"%>
<html lang="pt-br"> 
<head> 
<%@ include file="../include/meta.jsp"%>
<%@ include file="/titulo.jsp"%>
<link rel="stylesheet" href="<%=_pathEcar%>/css/style_<%=configuracaoCfg.getEstilo().getNome()%>.css" type="text/css">
<style type="text/css">
	.etiqueta{
		background-color: #DEE3EF;
	    font-family: Arial;
	    font-size: 13px;
	    font-weight: bold;
	    height: 30px;
	    text-align: center;
	    vertical-align: middle;
	    float: left;
	    font-size: 11px;
	    padding-right: 5px;
	    padding-bottom: 10px;
	}
	.etiqueta {background: transparent; width:23%;}
	.etiqueta .b1, .etiqueta .b2, .etiqueta .b3, .etiqueta .b4, .etiqueta .b1b, .etiqueta .b2b, .etiqueta .b3b, .etiqueta .b4b {display:block; overflow:hidden; font-size:1px;}
	.etiqueta .b1, .etiqueta .b2, .etiqueta .b3, .etiqueta .b1b, .etiqueta .b2b, .etiqueta .b3b {height:1px;}
	.etiqueta .b2, .etiqueta .b3, .etiqueta .b4 {background:#DEE3EF; border-left:1px solid #DEE3EF;; border-right:1px solid #DEE3EF;}
	.etiqueta .b1 {margin:0 5px; background:#DEE3EF;}
	.etiqueta .b2 {margin:0 3px; border-width:0 2px;}
	.etiqueta .b3 {margin:0 2px;}
	.etiqueta .b4 {height:2px; margin:0 1px;}
	.etiqueta .conteudo {padding:5px;display:block; background:#DEE3EF; border-left:1px solid #DEE3EF; border-right:1px solid #DEE3EF;}
	
	#itensPrioritarios{
		display: table-cell;
		text-align: center;
		vertical-align: middle;
		text-align: center;
		height: 314px;
		width: 45%;
	}
	
	.linkEtiqueta{
		padding-right: 5px;
		background:#DEE3EF;
	}
	
	fieldset{
		border: 1px solid #DEE3EF;
		padding-bottom: 5px;
	}
	legend{
		background-color: #DEE3EF;
    	color: #596A96;
    	font-size: 11px;
    	font-weight: bold;
    	padding: 5px;
	}
	
	.ui-menu .ui-menu-item a {
		background-color: white;
    	display: block;
    	line-height: 1.5;
    	padding: 0.2em 0.4em;
    	text-decoration: none;
	}
	.ui-menu {
    	list-style: none outside none;
	}
	ul.ui-autocomplete{
		padding-left: 0;
	}
	table.formTable {
	margin: 0 20px;
	}
	table.formTable td {
		padding: 3px;
	}
	input.button {
		background: #ddd url(images/button_bg.gif) left center repeat-x;
		border:1px solid #919191;
		color:#000000;
		font-weight:bold;
		font-size: 11px;
		margin:0px;
		padding:2px 4px;
		text-align:center;
		margin-right: 5px;
	}
	label.horizontalLabel {
		font-size: 0.8em;
	}
	
	table.formTable td.pickListTd {
		width: 50px;
	}
	table.formTable td.pickListRight {
		padding-right: 15px;
	}
	.pickList {
		border: 1px solid #ccc;
		width: auto;
	}
	
	table.formTable td.pickListButtons {
		padding: 0;
		width: 50px;
	}
	div.pickListButtons {
		padding-top: 20%;
		width: 100%;
		text-align: center;
	}
	input.addButton,
	input.removeButton,
	input.addAllButton,
	input.removeAllButton{
		border-color: #ccc;
		text-align: center;
		width: 30px;
		margin-right: 0;
	}
	input.removeButton,
	input.removeAllButton {
		margin-top: 5px;
	}
	input.addAllButton {
		margin-top: 15px;
	}
	.etiqueta a:link, a:visited, a:hover{
		text-decoration: none;
	}
	.objetivoEstrategico{
		float: left; 
		height: 90px; 
		width: 90px; 
		margin-right: 45px; 
		margin-bottom: 5px;
		display: table-cell;
		text-align: center;
		vertical-align: middle;
		font-weight: bold;
		font-size: 35px;
	}
	.checkBoxObjetivo{
		border: 0.5px solid #fff;
	}
	 
</style>
<script src="${pageContext.request.contextPath}/js/jquery.1.7.1.js"></script>
<script src="${pageContext.request.contextPath}/js/jquery.multiselects-0.3.js"></script>
<script src="${pageContext.request.contextPath}/js/jquery.ui.core.js"></script>
<script src="${pageContext.request.contextPath}/js/jquery.ui.widget.js"></script>
<script src="${pageContext.request.contextPath}/js/jquery.ui.position.js"></script>
<script src="${pageContext.request.contextPath}/js/jquery.ui.autocomplete.js"></script>
<script language="javascript" src="${pageContext.request.contextPath}/js/datas.js"></script>
<script language="javascript" src="../js/menu_retratil.js"></script>
<script language="javascript" src="../js/focoInicial.js"></script>
<script type="text/javascript">
	jQuery(function($){
		// (OPTIONAL) adjust the width of the select box to the bigger of the two
		$("select[name='objetivosSelecionados']").selectAdjustWidth("select[name='objetivos']");
		
		$("select[name='objetivosSelecionados']").multiSelect("select[name='objetivos']", {
			trigger: "input[name='remove']",
			triggerAll: "input[name='removeAll']"
			});
		
		$("select[name='objetivos']").multiSelect("select[name='objetivosSelecionados']", {
			trigger: "input[name='add']",
			triggerAll: "input[name='addAll']"
			});
	
	});
	var nomesEtiquetas = new Array();
	function autoCompleteEtiquetas(){
		$.ajax({
			url: "autoCompleteEtiqueta?nomeEtiqueta="+$("#nomeEtiqueta").val(),
			dataType: "xml",
			success: function( xmlResponse ) {
				var data = $( "etiqueta", xmlResponse ).map(function() {
					return {
						value: $( "nome", this ).text(), id: $( "codigo", this ).text()
					};
				}).get();
				$( "#nomeEtiqueta" ).autocomplete({
					source: data,
					minLength: 2,
					select: function( event, ui ) {
						inserirNomeEtiquetaSelecionada(ui.item.id, ui.item.value);
					}
				});
			}
		});
	}

	function inserirNomeEtiquetaSelecionadaPrioritaria(codigo, nome){
		inserirNomeEtiquetaSelecionada(codigo, nome);
	}

	function removerEtiquetaFiltro(nomeDiv, codigoEtiqueta){
		$(nomeDiv).remove();
		var index = nomesEtiquetas.indexOf(codigoEtiqueta);
		nomesEtiquetas.splice(index, 1);
	}

	function inserirNomeEtiquetaSelecionada(codigo, nome){
		var existe = false;
		for(var i = 0; i < nomesEtiquetas.length; i++){
			if(nomesEtiquetas[i] == nome){
				var existe = true;
			}
		}
		if(existe == false){
			var etiquetas = "<div class='etiqueta' id='etiqueta"+codigo+"'>"
								+"<div class='conteudo'>"
									+"<b class='b1'></b><b class='b2'></b><b class='b3'></b><b class='b4'></b>"
										+"<a href='javascript:javascript:removerEtiquetaFiltro(etiqueta"+codigo+", "+codigo+")'>"
											+"<div style='float: left; width: 85%;'>"
												+nome
											+"</div>"
											+"<div style='float: left;'>"
												+"<img src='"+${pageContext.request.contextPath}/+"images/excluir.png'/>"
											+"</div>"
										+"</a>"
									+"<b class='b4'></b><b class='b3'></b><b class='b2'></b><b class='b1'></b>"
								+"</div>"
		  				 +  "</div>";
			$("#etiquetasSelecionadas").append(etiquetas);
			nomesEtiquetas.push(nome);
			$("#etiquetas").attr('value', nomesEtiquetas);
		}else{
			alert('Esta etiqueta já foi adicionada ao filtro.');
		}
	}

	function validarEmissaoRelatorio(){
		if (jQuery("input[@name='objetivosSelecionados[]']:checked").length > 0) {
			return true;
		} else {
			alert("Marque ao menos um Objetivo Estratégico.");
			return false;
		}
	}

	function emitirRelatorioAcompanhamento(codigoRelatorio){
		if(validarEmissaoRelatorio()){
			$('#metodo').val("pdf");
			$("#codigoRelatorio").val( codigoRelatorio );
			$("#exercicio").val( $("#comboExercicio").val() );
			$("#acompReferencia").val( $("#comboCiclo").val() );
			$("#formRelatorioMonitoramento").submit();
		}
	}
	
	function emitirRelatorioAcompanhamentoExcel(){
		if(validarEmissaoRelatorio()){
			$('#metodo').val("excel");
			$("#codigoRelatorio").val( codigoRelatorio );
			$("#exercicio").val( $("#comboExercicio").val() );
			$("#acompReferencia").val( $("#comboCiclo").val() );
			$("#formRelatorioMonitoramento").submit();
		}
	}
	
	function emitirRelatorioAcompanhamentoExcelIndicador(){
		if(validarEmissaoRelatorio()){
			$('#metodo').val("excel_indicador");
			$("#codigoRelatorio").val( codigoRelatorio );
			$("#exercicio").val( $("#comboExercicio").val() );
			$("#acompReferencia").val( $("#comboCiclo").val() );
			$("#formRelatorioMonitoramento").submit();
		}
	}
	
	function populaCiclo(){
		
		var pExercicio = $("#comboExercicio").val();
		
		$.ajax({
			type: "POST",
			data: {exercicio : pExercicio},
			url: "cicloReferencia"
			}).done(function ( data ) {
				$("#comboCiclo").html(data);
			});
	}
	
	window.setTimeout("populaCiclo()", 2000);
	
</script>
</head>
<%@ include file="../cabecalho.jsp"%>
<%@ include file="../divmenu.jsp"%>
<body> 
	<div id="conteudo">
	<!-- TÍTULO -->
	<%@ include file="/titulo_tela.jsp"%>
		<div style="float: left; width: 700px;">
			<form method="post" action="${pageContext.request.contextPath}/relatorioAcompanhamento" id="formRelatorioMonitoramento">
				<input type="hidden" id="etiquetas" name="etiquetas"/>
				<input type="hidden" id="codigoRelatorio" name="codigoRelatorio"/>
				<input type="hidden" id="metodo" name="metodo" value="pdf" />
				<fieldset style="width: 100%; padding: 20px;">
					<legend>FILTROS</legend>
					<div style="padding: 5px; width: 665px;">
					
						<fieldset>
							<legend>EXERCÍCIO</legend>
							<select id="comboExercicio" onchange="populaCiclo()">
								<%
									
									ExercicioDao daoExercicio = new ExercicioDao(null);
									List<ExercicioExe> listaExercicios = daoExercicio.listarExercios();

									out.println("<option value='0'>Todos os exercícios</option>");
									for (ExercicioExe exercicioExe : listaExercicios) {
										out.println("<option value='"
												+ exercicioExe.getCodExe()
												+ "'"
												+ (exercicioExe.getCicloCorrenteExe() ? "selected='selected'"
														: "") + " >" + exercicioExe.getDescricaoExe()
												+ "</option>");
									}
								%>

							</select>
							<input type="hidden" id="exercicio" name="exercicio" value="">
						</fieldset>
						<!-- 
						<fieldset>
							<legend>CICLO</legend>
							<select id="comboCiclo">
							</select>
							<input type="hidden" id="acompReferencia" name="acompReferencia" value="">
						</fieldset>
						 -->



						<div style="width: 100%; padding-top: 5px; padding-bottom: 5px;">
							<fieldset>
								<legend>OBJETIVOS ESTRATÉGICOS</legend>
								<div style="text-align: center; width: 100%;">
									<c:forEach items="${listaOpcoesObjetivosEstrategicos}" var="objetivo">
										<div class="objetivoEstrategico" style="background-color: ${objetivo.codigoHexadecimalCor}; color: white;">
											<div style="text-align: right; width: 100%;">
												<input type="checkbox" value="${objetivo.codIett}" name="objetivosSelecionados[]"/>
											</div>
											<div style="text-align: center; width: 100%;">
												${objetivo.siglaFormatadaObjetivoEstrategico}
											</div>
										</div>
									</c:forEach>
								</div>
							</fieldset>
						</div>
						<div style="width: 100%; padding-top: 5px; padding-bottom: 10px;">
							<fieldset>
								<legend>STATUS</legend>
								<table>
									<tr>
										<td height="46" valign="middle">
											<input type="checkbox" name="status" value="1"/>
										</td>
										<td width="70">
											<img alt="" src="${pageContext.request.contextPath}/images/sVerde9.png">
										</td>
										<td height="46" valign="middle">
											<input type="checkbox" name="status" value="2"/>
										</td>
										<td width="70">
											<img alt="" src="${pageContext.request.contextPath}/images/sAmarelo9.png">
										</td>
										<td height="46" valign="middle">
											<input type="checkbox" name="status" value="3"/>
										</td>
										<td width="70">
											<img alt="" src="${pageContext.request.contextPath}/images/sVermelho9.png">
										</td>
										<td height="46" valign="middle">
											<input type="checkbox" name="status" value="10"/>
										</td>
										<td width="70">
											<img alt="" src="${pageContext.request.contextPath}/images/sAzul9.png">
										</td>
										<td height="46" valign="middle">
											<input type="checkbox" name="status" value="11"/>
										</td>
										<td width="70">
											<img alt="" src="${pageContext.request.contextPath}/images/sCinza9.png">
										</td>
										<td height="46" valign="middle">
											<input type="checkbox" name="status" value="0"/>
										</td>
										<td width="70">
											<img alt="" src="${pageContext.request.contextPath}/images/sBranco9.png">
										</td>
									</tr>
								</table>
							</fieldset>
						</div>
<!--						<div style="float: left;">-->
<!--							<fieldset style="width: 240px; height: 256px;">-->
<!--								-->
<!--							</fieldset>-->
<!--						</div>-->
						<div style="padding-bottom: 5px;">
							<fieldset>
								<legend>SECRETARIAS</legend>
								<table>
									<c:forEach items="${listaSecretarias}" var="secretaria">
										<tr>
											<td>
												<input type="checkbox" value="${secretaria.codigosSecretarias}" name="secretariasSelecionadas"/>
											</td>
											<td>
												${secretaria.siglaSecretaria}
											</td>
										</tr>
									</c:forEach>
								</table>
							</fieldset>
						</div>
						<div style="clear: both; padding-bottom: 5px;">
							<fieldset>
								<legend>ETIQUETAS</legend>
								<div style="width: 100%;">
									<input type="text" id="nomeEtiqueta" name="nomeEtiqueta" style="width: 100%;" onkeyup="autoCompleteEtiquetas();"/>
								</div>
								<div id="etiquetasSelecionadas" style="width: 100%; padding-top: 10px;">
									
								</div>
							</fieldset>
						</div>
						<div style="padding-bottom: 5px;">
							<fieldset>
								<legend>OUTROS FILTROS</legend>
								<div style="float: left; padding-right: 20px;">
									<table>
										<tr>
											<td>
												<input type="checkbox" name="prioritario"/>
											</td>
											<td>
												Somente Resultado Prioritário
											</td>
										</tr>
									</table>
								</div>
								<div style="float: left;">
									<table>
										<tr>
											<td>
												<input type="checkbox" name="ordenarPorStatus"/>
											</td>
											<td>
												Ordenar por Status
											</td>
										</tr>
									</table>
								</div>
								<div style="float: left;">
									<table>
										<tr>
											<td>
												<input type="checkbox" name="comIndicadores"/>
											</td>
											<td>
												Com Indicadores
											</td>
										</tr>
									</table>
								</div>	
								<div style="float: left;">
									<table>
										<tr>
											<td>
												<input type="checkbox" name="somenteREM"/>
											</td>
											<td>
												Somente indicadores REM
											</td>
										</tr>
									</table>
								</div>								
							</fieldset>
						</div>
						<div style="padding-bottom: 5px;">
							<fieldset>
								<legend>PERÍODO</legend>
								<div style="float: left; padding-right: 20px;">
									<table>
										<tr>
											<td>
												Inicial:
											</td>
											<td>
												<input type="text" id="dataInicial" name="dataInicial" size="13" maxlength="10" onkeyup="mascaraData(event, this);" />
											</td>
										</tr>
									</table>
								</div>
								<div style="float: left;">
									<table>
										<tr>
											<td>
												Final:
											</td>
											<td>
												<input type="text" id="dataFinal" name="dataFinal" size="13" maxlength="10" onkeyup="mascaraData(event, this);" />
											</td>
										</tr>
									</table>
								</div>
							</fieldset>
						</div>
						<div style="padding-bottom: 5px; clear: both;">
							<fieldset>
								<legend>
									Tipo de Relatório
								</legend>
								<div style="border: 1px solid #DEE3EF; float: left; height: 130px; padding: 5px; text-align: center; width: 90px; margin-right: 5px;">
									<a href="javascript:emitirRelatorioAcompanhamento(1);" title="Apresenta a lista dos resultados com a sinalização dos pareceres">
										<img src="${pageContext.request.contextPath}/images/icone_executivo.gif" />
										Executivo
									</a>
								</div>
								<div style="border: 1px solid #DEE3EF; float: left; height: 130px; padding: 5px; text-align: center; width: 90px; margin-right: 5px;">
									<a href="javascript:emitirRelatorioAcompanhamento(2);" title="Lista dos Resultados e Produtos com a sinalização dos pareceres">
										<img src="${pageContext.request.contextPath}/images/icone_gerencial.gif" />
										Gerencial
									</a>
								</div>
								<!-- comentei aqui para substituição do Relatorio de Indicadores -->
								<div style="border: 1px solid #DEE3EF; float: left; height: 130px; padding: 5px; text-align: center; width: 90px; margin-right: 5px; display:none;">
									<a href="javascript:emitirRelatorioAcompanhamento(3);" title="Apresenta o painel de indicadores relacionados aos resultados">
										<img src="${pageContext.request.contextPath}/images/icone_indicadores.gif" />
										Indicadores
									</a>
								</div>
								<div style="border: 1px solid #DEE3EF; float: left; height: 130px; padding: 5px; text-align: center; width: 90px; margin-right: 5px;">
									<a href="javascript:emitirRelatorioAcompanhamento(8);" title="Apresenta o painel de indicadores com detalhamento mensal relacionados aos resultados">
										<img src="${pageContext.request.contextPath}/images/icone_indicadores.gif" />
										Indicadores Detalhamento
									</a>
								</div>
								<div style="border: 1px solid #DEE3EF; float: left; height: 130px; padding: 5px; text-align: center; width: 90px; margin-right: 5px;">
									<a href="javascript:emitirRelatorioAcompanhamento(4);" title="Apresenta a lista de Resultados e Produtos com o parecer do Resultado">
										<img src="${pageContext.request.contextPath}/images/icone_operacional_produtos.gif" />
										Operacional<br>
										Produtos
									</a>
								</div>
								<div style="border: 1px solid #DEE3EF; float: left; height: 130px; padding: 5px; text-align: center; width: 90px; margin-right: 5px;">
									<a href="javascript:emitirRelatorioAcompanhamento(5);" title="Apresenta a lista de Resultados e Produtos com o parecer do Resultado e dos Produtos">
										<img src="${pageContext.request.contextPath}/images/icone_operacional_produtos_acoes.gif" />
										Operacional<br>
										Produtos<br>
										Ações
									</a>
								</div>
								<div style="border: 1px solid #DEE3EF; float: left; height: 130px; padding: 5px; text-align: center; width: 90px; margin-right: 5px;">
									<a href="javascript:emitirRelatorioAcompanhamento(6);" title="Apresenta a lista de Resultados e Produtos com o parecer do Resultado, dos Produtos e das Ações">
										<img src="${pageContext.request.contextPath}/images/icone_operacional_produtos_acoes_atividades.gif" />
										Operacional<br>
										Produtos<br>
										Ações<br>
										Atividades
									</a>
								</div>
								
								<div style="border: 1px solid #DEE3EF; float: left; height: 130px; padding: 5px; text-align: center; width: 90px; margin-right: 5px;">
									<a href="javascript:emitirRelatorioAcompanhamento(7);" title="Apresenta o painel de encaminhamentos relacionados aos resultados">
										<img src="${pageContext.request.contextPath}/images/icone_operacional_produtos_acoes_atividades.gif" />
										Encaminhamento
									</a>
								</div>
								
								<div style="border: 1px solid #DEE3EF; float: left; height: 130px; padding: 5px; text-align: center; width: 90px; margin-right: 5px;">
									<a href="javascript:emitirRelatorioAcompanhamentoExcel();" title="Apresenta os resultados em excel">
										<img src="${pageContext.request.contextPath}/images/icone_excel.gif" />
										Detalhamento Excel
									</a>
								</div>

								<div style="border: 1px solid #DEE3EF; float: left; height: 130px; padding: 5px; text-align: center; width: 90px; margin-right: 5px;">
									<a href="javascript:emitirRelatorioAcompanhamentoExcelIndicador();" title="Apresenta os resultados em excel somente dos Indicadores">
										<img src="${pageContext.request.contextPath}/images/icone_excel.gif" />
										Detalhamento Excel Indicadores
									</a>
								</div>
								
								<div style="border: 1px solid #DEE3EF; float: left; height: 130px; padding: 5px; text-align: center; width: 90px; margin-right: 5px;">
									<a href="javascript:emitirRelatorioAcompanhamento(9);" title="Lista dos Resultados e Produtos com a sinalização dos pareceres e os Três Últimos Monitoramentos">
										<img src="${pageContext.request.contextPath}/images/icone_gerencial.gif" />
										Gerencial com Monitoramentos
									</a>
								</div>
								
							</fieldset>
						</div>
					</div>
				</fieldset>
			</form>
		</div>
	</div>
</body>
<% /* Controla o estado do Menu (aberto ou fechado) */%>
<%@ include file="../include/estadoMenu.jsp"%>
</html>

