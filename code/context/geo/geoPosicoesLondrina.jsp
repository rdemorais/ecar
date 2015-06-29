<%@ page import="comum.util.Pagina" %>

<html lang="pt-br">
<head>
<%
	String Opcao = request.getParameter("opcao");
%>
<%@ include file="../include/meta.jsp"%>
<title>e-CAR</title>
<link rel="stylesheet" href="<%=_pathEcar%>/css/style_<%=new ecar.dao.ConfiguracaoDao(request).getConfiguracao().getEstilo().getNome()%>.css" type="text/css">

<script language="javascript" src="<%=_pathEcar%>/js/destaqueLinha.js"></script>
<script>
function aoClicarConsultar(form, codigo, codIett){ 
	form.codAri.value = codigo;
	document.form.action = "./geoAvaliacoesLondrina.jsp?primeiroIettClicado=" + codIett + "&primeiroAriClicado=" + codigo;
	document.form.submit();
}

function reload(aux){
	if (aux == "posicoes"){
		form.radPosicoes.checked = true;
		document.form.action = "./geoMapa.jsp?cidade=londrina&opcao=posicoes#ancora";
		document.form.submit();
	}
	
	if (aux == "indicadores"){
		form.radIndicadores.checked = true;
		document.form.action = "./geoMapa.jsp?cidade=londrina&opcao=indicadores#ancora";
		document.form.submit();
	}
	
	if (aux == "realizado"){
		form.radIndicadores.checked = true;
		document.form.action = "./geoMapa.jsp?cidade=londrina&opcao=realizado#ancoraRealizado";
		document.form.submit();
	}
	
	if (aux == "eleicoes"){
		form.radEleicoes.checked = true;
		document.form.action = "./geoMapa.jsp?cidade=londrina&opcao=eleicoes#ancoraEleicoes";
		document.form.submit();
	}
	
	if (aux == "politica"){
		form.radPolitica.checked = true;
		document.form.action = "./geoMapa.jsp?cidade=londrina&opcao=politica#ancoraPolitica";
		document.form.submit();
	}
	
	if (aux == "socio"){
		form.radSocio.checked = true;
		document.form.action = "./geoMapa.jsp?cidade=londrina&opcao=socio#ancoraSocio";
		document.form.submit();
	}
}
</script>

</head>

<body> 


<div id="subconteudo">
	
	
	<form name="form" method="post">
	
		<input type="hidden" name="codTipoAcompanhamento" value="1">
		<input type="hidden" name="codAri" value="">	
		<input type="hidden" name="niveisPlanejamento" value="12:13:14:">
		<input type="hidden" name="primeiraChamada" value="">
		<input type="hidden" name="orgaoEscolhido" value="Todos">
		<input type="hidden" name="periodoEscolhido" value="3 Ciclos">
		<input type="hidden" name="ComboClicado" id="ComboClicado" value="">
		<input type="hidden" name="referencia_hidden" value="">

		<input type="hidden" name="itemDoNivelClicado" value="">
		<input type="hidden" name="semInformacaoNivelPlanejamento" value="">
		<input type="hidden" name="codIettRelacao" value="">
		
<br>
<br>
<br>
	<table class="barrabotoes" cellpadding="0" cellspacing="0">
		<a name="ancora" id="ancora">
		<tr>
			<td align="left">
				<b>Munic�pio de Londrina</b>
			</td>
		</tr>
		</a>
	</table>
	<table width="100%" border="0" cellpadding="0" cellspacing="0">
		<tr><td class="espacador" colspan="2"><img src="../images/pixel.gif"></td></tr>
		<tr  class="linha_subtitulo" valign="bottom">
			<td width="40%"><br>Selecione uma das op��es abaixo: <br> 
				<input type="radio" class="form_check_radio" name="radPosicoes" value="posicoes" onclick="reload('posicoes');" <% if(Opcao != null && Opcao.equals("posicoes")){ %> checked <% } %> > Posi��es de Projetos 
				<input type="radio" class="form_check_radio" name="radIndicadores" value="indicadores" onclick="reload('indicadores');" <% if(Opcao != null && Opcao.equals("indicadores")){ %> checked <% } %>> Situa��o de Indicadores 
				<input type="radio" class="form_check_radio" name="radRealizado" value="realizado" onclick="reload('realizado');" <% if(Opcao != null && Opcao.equals("realizado")){ %> checked <% } %>> Realizado
				<input type="radio" class="form_check_radio" name="radEleicoes" value="eleicao" onclick="reload('eleicoes');" <% if(Opcao != null && Opcao.equals("eleicoes")){ %> checked <% } %>> Elei��o
				<input type="radio" class="form_check_radio" name="radPolitica" value="politica" onclick="reload('politica');" <% if(Opcao != null && Opcao.equals("politica")){ %> checked <% } %>> Pol�tica
				<input type="radio" class="form_check_radio" name="radSocio" value="socio" onclick="reload('socio');" <% if(Opcao != null && Opcao.equals("socio")){ %> checked <% } %>> S�cio Econ�mico
			</td>			
		</tr>
	</table>	
<br>
<%
	if (Opcao != null){
	
		if (Opcao.equals("posicoes")){

%>

<br>	
	<table width="100%" border="0" cellpadding="0" cellspacing="0">
		<tr>
			<td width="21%" align="center"><h1>Posi��es - Munic�pio de Londrina</h1></td>		
			<td width="79%"></td>	
		</tr>
	</table>
<br>

<table border="0" cellpadding="0" cellspacing="0" width="100%">		
								<tr>						
									<td class="espacador" colspan="9">
										<img src="../images/pixel.gif">
									</td>
								</tr>
								
								<!-- T�TULO DA TABELA -->

								<tr class="linha_titulo">
							 		<td colspan="9">
		
								 	</td>						 						 	
								</tr>
		
								<tr>						
									<td class="espacador" colspan="9">
										<img src="../images/pixel.gif">
									</td>
								</tr>
								
								<!-- CABE�ALHO DA TABELA -->

								<tr class="linha_subtitulo">
									<td width="1%"></td>							
									<td width="50%" colspan="3">Acompanhamentos</td>
									<td width="10%" align="center" nowrap>
										Org�o Respons�vel
									</td>

		
									<td align="center"  width="12.0%">Setembro/2006</td>
		
									<td align="center"  width="12.0%">Outubro/2006</td>
		
									<td align="center" id="selecionado" class="corSelecao" width="12.0%">Novembro/2006</td>
		
								</tr>	
		
								<tr>
									<td class="espacador" colspan="9">
										<img src="../images/pixel.gif">
									</td>

								</tr>
								
								<tr class="cor_sim bgCinzaClaro" onmouseover="javascript:destacaLinha(this,'over','')" onmouseout="javascript: destacaLinha(this,'out','cor_sim bgCinzaClaro')" onClick="javascript: destacaLinha(this,'click','cor_sim bgCinzaClaro')">
										<td>
										
										</td>
										<td>
											<table>
											<tr>
												<td nowrap>											
													&nbsp;

		
												</td>
												<td valign="top"><img src="../images/icon_bullet_seta.png"></td>
												<td title="Linha de A��o:Infra-Estrutura e Meio Ambiente">
													<font color="#596D9B">
													
														
													
														2 - Infra-Estrutura e Meio Ambiente 
													</font>
												</td>
											</tr>
											</table>

										</td>
										<td nowrap>
		
		
										</td>
										
										<td>
											
										</td>	
																
										<td align="center">
		
										</td>
		<td align="center"><p title="N�o foi solicitado acompanhamento"></p></td><td align="center"><p title="N�o foi solicitado acompanhamento"></p></td><td align="center"><p title="N�o foi solicitado acompanhamento"></p></td>										
									</tr>
									
									<tr class="cor_nao" onmouseover="javascript: destacaLinha(this,'over','')" onmouseout="javascript: destacaLinha(this,'out','cor_nao')" onClick="javascript: destacaLinha(this,'click','cor_nao')">
										<td>
										
										</td>
										<td>
											<table>
											<tr >
												<td nowrap>											
													&nbsp;

		
														<img src="<%=_pathEcar%>/images/pixel.gif" width="22" height="9">
		
												</td>
												<td valign="top"><img src="../images/icon_bullet_seta.png"></td>
												<td title="Programa:Meio Ambiente">
													<font color="#596D9B">
													
														
													
														07 - Meio Ambiente
													</font>
												</td>
											</tr>

											</table>
										</td>
										<td nowrap>
		
										</td>
										
										<td>
											
										</td>	
																
										<td align="center">
		SEMA

										</td>
		<td align="center"><p title="N�o foi solicitado acompanhamento"></p></td><td align="center"><p title="N�o foi solicitado acompanhamento"></p></td><td align="center"><p title="N�o foi solicitado acompanhamento"></p></td>										
									</tr>
									
									<tr class="cor_sim" onmouseover="javascript:destacaLinha(this,'over','')" onmouseout="javascript: destacaLinha(this,'out','cor_sim')" onClick="javascript: destacaLinha(this,'click','cor_sim')">
										<td>

										
										</td>
										<td>
											<table>
											<tr>
												<td nowrap>											
													&nbsp;
		
														<img src="<%=_pathEcar%>/images/pixel.gif" width="22" height="9">
		
														<img src="<%=_pathEcar%>/images/pixel.gif" width="22" height="9">
		
												</td>

												<td valign="top"><img src="../images/icon_bullet_seta.png"></td>
												<td title="A��o:Administra��o e Execu��o das A��es do FEMA">
													<font color="#596D9B">
													
														
													
														Administra��o e Execu��o das A��es do FEMA - Secretaria de Estado do Meio Ambiente e Recursos H�dricos
													</font>
												</td>
											</tr>
											</table>
										</td>

										<td nowrap>
		
										</td>
										
										<td>
											
										</td>	
																
										<td align="center">

		SEMA

										</td>
		<td align="center"><p title="N�o foi solicitado acompanhamento"></p></td><td align="center"><p title="N�o foi solicitado acompanhamento"></p></td><td align="center"><p title="N�o foi solicitado acompanhamento"></p></td>										
									</tr>
									
									<tr class="cor_nao" onmouseover="javascript: destacaLinha(this,'over','')" onmouseout="javascript: destacaLinha(this,'out','cor_nao')" onClick="javascript: destacaLinha(this,'click','cor_nao')">
										<td>
										
										</td>
										<td>
											<table>
											<tr>
												<td nowrap>											
													&nbsp;

		
														<img src="<%=_pathEcar%>/images/pixel.gif" width="22" height="9">
		
														<img src="<%=_pathEcar%>/images/pixel.gif" width="22" height="9">
		
														<img src="<%=_pathEcar%>/images/pixel.gif" width="22" height="9">
		
												</td>
												<td valign="top"><img src="../images/icon_bullet_seta.png"></td>
												<td title="Produto:Jardim Bot�nico de Londrina">
													<font color="#596D9B">
													
														
													
														Jardim Bot�nico de Londrina
													</font>

												</td>
											</tr>
											</table>
										</td>
										<td nowrap>
		
										</td>
										
										<td>

										</td>	
																
										<td align="center">
		SEMA

										</td>
		
												<td align="center" nowrap>
		
													<a name="ancora376" href="#" onclick="javascript:aoClicarConsultar(form, 11673, 376)">
												<image border="0" src="../images/relAcomp/verde9.png" title="Administrador"><image border="0" src="../images/relAcomp/verde3.png" title="Respons�vel T�cnico">

													</a>
												
												</td>
		
												<td align="center" nowrap>
		
													<a name="ancora376" href="#" onclick="javascript:aoClicarConsultar(form, 20597, 376)">
												<image border="0" src="../images/relAcomp/verde9.png" title="Administrador"><image border="0" src="../images/relAcomp/verde3.png" title="Respons�vel T�cnico">
													</a>
												
												</td>
		
												<td align="center" nowrap>
		
														<a name="ancora376"  href="#" onclick="javascript:aoClicarConsultar(form, 42171, 376)">

														
														<p title="N�o liberado">N/L</p>
														
														</a>
													
												</td>
												
									</tr>
									
									<tr class="cor_sim bgCinzaClaro" onmouseover="javascript:destacaLinha(this,'over','')" onmouseout="javascript: destacaLinha(this,'out','cor_sim bgCinzaClaro')" onClick="javascript: destacaLinha(this,'click','cor_sim bgCinzaClaro')" >
										<td>
										
										</td>
										<td>
											<table>
											<tr >
												<td nowrap>											
													&nbsp;

		
												</td>
												<td valign="top"><img src="../images/icon_bullet_seta.png"></td>
												<td title="Linha de A��o:Emprego, Cidadania e Solidariedade">
													<font color="#596D9B">
													
														
													
														4 - Emprego, Cidadania e Solidariedade
													</font>
												</td>
											</tr>
											</table>

										</td>
										
										<td nowrap>
										</td>
										
										<td>
											
										</td>	
																
										<td align="center">
		
										</td>
		<td align="center"><p title="N�o foi solicitado acompanhamento"></p></td><td align="center"><p title="N�o foi solicitado acompanhamento"></p></td><td align="center"><p title="N�o foi solicitado acompanhamento"></p></td>										
									</tr>
									
									<tr class="cor_nao" onmouseover="javascript: destacaLinha(this,'over','')" onmouseout="javascript: destacaLinha(this,'out','cor_nao')" onClick="javascript: destacaLinha(this,'click','cor_nao')">
										<td>
										
										</td>
										<td>
											<table>
											<tr>

												<td nowrap>											
													&nbsp;
		
														<img src="<%=_pathEcar%>/images/pixel.gif" width="22" height="9">
		
												</td>
												<td valign="top"><img src="../images/icon_bullet_seta.png"></td>
												<td title="Programa:Sa�de">
													<font color="#596D9B">
													
														
													
														15 - Sa�de
													</font>
												</td>

											</tr>
											</table>
										</td>
										
										<td nowrap>
										</td>
										
										<td>
											
										</td>	
																
										<td align="center">
		SESA

										</td>
		<td align="center"><p title="N�o foi solicitado acompanhamento"></p></td><td align="center"><p title="N�o foi solicitado acompanhamento"></p></td><td align="center"><p title="N�o foi solicitado acompanhamento"></p></td>										
									</tr>
									
											
									<tr class="cor_sim" onmouseover="javascript:destacaLinha(this,'over','')" onmouseout="javascript: destacaLinha(this,'out','cor_sim')" onClick="javascript: destacaLinha(this,'click','cor_sim')">
										<td>
										
										</td>
										<td>
											<table>
											<tr>
												<td nowrap>											
													&nbsp;
		
														<img src="<%=_pathEcar%>/images/pixel.gif" width="22" height="9">

		
														<img src="<%=_pathEcar%>/images/pixel.gif" width="22" height="9">
		
												</td>
												<td valign="top"><img src="../images/icon_bullet_seta.png"></td>
												<td title="A��o:Manuten��o de A��es e Servi�os de Sa�de">
													<font color="#596D9B">
													
														
													
														Manuten��o de A��es e Servi�os de Sa�de - Secretaria de Estado da Sa�de
													</font>
												</td>
											</tr>

											</table>
										</td>
										<td nowrap>
		
										</td>
										
										<td>
											
										</td>	
																
										<td align="center">
		SESA

										</td>
		<td align="center"><p title="N�o foi solicitado acompanhamento"></p></td><td align="center"><p title="N�o foi solicitado acompanhamento"></p></td><td align="center"><p title="N�o foi solicitado acompanhamento"></p></td>										
									</tr>
									
											
									<tr class="cor_nao" onmouseover="javascript: destacaLinha(this,'over','')" onmouseout="javascript: destacaLinha(this,'out','cor_nao')" onClick="javascript: destacaLinha(this,'click','cor_nao')">
										<td>
										
										</td>
										<td>
											<table>
											<tr>
												<td nowrap>											
													&nbsp;
		
														<img src="<%=_pathEcar%>/images/pixel.gif" width="22" height="9">

		
														<img src="<%=_pathEcar%>/images/pixel.gif" width="22" height="9">
		
														<img src="<%=_pathEcar%>/images/pixel.gif" width="22" height="9">
		
												</td>
												<td valign="top"><img src="../images/icon_bullet_seta.png"></td>
												<td title="Produto:Realizar obras e reformas de unidades na �rea de Sa�de">
													<font color="#596D9B">
													
														
													
														Realizar obras e reformas de unidades na �rea de Sa�de
													</font>
												</td>

											</tr>
											</table>
										</td>
										<td nowrap>
		
										</td>
										
										<td>
											
										</td>	
																
										<td align="center">
		SESA

										</td>
		<td align="center"><p title="N�o foi solicitado acompanhamento"></p></td><td align="center"><p title="N�o foi solicitado acompanhamento"></p></td><td align="center"><p title="N�o foi solicitado acompanhamento"></p></td>										
									</tr>
									
											
									<tr class="cor_sim" onmouseover="javascript:destacaLinha(this,'over','')" onmouseout="javascript: destacaLinha(this,'out','cor_sim')" onClick="javascript: destacaLinha(this,'click','cor_sim')">
										<td>
										
										</td>
										<td>
											<table>
											<tr>
												<td nowrap>											
													&nbsp;
		
														<img src="<%=_pathEcar%>/images/pixel.gif" width="22" height="9">

		
														<img src="<%=_pathEcar%>/images/pixel.gif" width="22" height="9">
		
														<img src="<%=_pathEcar%>/images/pixel.gif" width="22" height="9">
		
														<img src="<%=_pathEcar%>/images/pixel.gif" width="22" height="9">
		
												</td>
												<td valign="top"><img src="../images/icon_bullet_seta.png"></td>
												<td title="Subproduto:Hospital Zona Norte - Londrina">
													<font color="#596D9B">
													
														
													
														Hospital Zona Norte - Londrina
													</font>

												</td>
											</tr>
											</table>
										</td>
										<td nowrap>
		
										</td>
										
										<td>
										
										</td>	
																
										<td align="center">
		SESA

										</td>
		
												<td align="center" nowrap>
		
													<a name="ancora1740" href="#" onclick="javascript:aoClicarConsultar(form, 11702, 1740)">
												<image border="0" src="../images/relAcomp/verde9.png" title="Administrador"><image border="0" src="../images/relAcomp/verde3.png" title="Respons�vel T�cnico">
													</a>

												
												</td>
		
												<td align="center" nowrap>
		
													<a name="ancora1740" href="#" onclick="javascript:aoClicarConsultar(form, 20624, 1740)">
												<image border="0" src="../images/relAcomp/verde9.png" title="Administrador"><image border="0" src="../images/relAcomp/verde3.png" title="Respons�vel T�cnico">
													</a>
												
												</td>
		
												<td align="center" nowrap>
		
													<a name="ancora1740" href="#" onclick="javascript:aoClicarConsultar(form, 42200, 1740)">
												<image border="0" src="../images/relAcomp/verde9.png" title="Administrador"><image border="0" src="../images/relAcomp/verde3.png" title="Respons�vel T�cnico">

													</a>
												
												</td>
												
									</tr>
		
									<tr class="cor_nao" onmouseover="javascript: destacaLinha(this,'over','')" onmouseout="javascript: destacaLinha(this,'out','cor_nao')" onClick="javascript: destacaLinha(this,'click','cor_nao')">
										<td>
										
										</td>
										<td>
											<table>
											<tr>

												<td nowrap>											
													&nbsp;
		
														<img src="<%=_pathEcar%>/images/pixel.gif" width="22" height="9">
		
														<img src="<%=_pathEcar%>/images/pixel.gif" width="22" height="9">
		
														<img src="<%=_pathEcar%>/images/pixel.gif" width="22" height="9">
		
														<img src="<%=_pathEcar%>/images/pixel.gif" width="22" height="9">
		
												</td>
												<td valign="top"><img src="../images/icon_bullet_seta.png"></td>
												<td title="Subproduto:Hospital Zona Sul - Londrina">

													<font color="#596D9B">
													
														
													
														Hospital Zona Sul - Londrina
													</font>
												</td>
											</tr>
											</table>
										</td>
										<td nowrap>
		
										</td>
										
										<td>
											
										</td>	
																
										<td align="center">
		SESA

										</td>
		
												<td align="center" nowrap>

		
													<a name="ancora1739" href="#" onclick="javascript:aoClicarConsultar(form, 11701, 1739)">
												<image border="0" src="../images/relAcomp/verde9.png" title="Administrador"><image border="0" src="../images/relAcomp/verde3.png" title="Respons�vel T�cnico">
													</a>
												
												</td>
		
												<td align="center" nowrap>
		
													<a name="ancora1739" href="#" onclick="javascript:aoClicarConsultar(form, 20623, 1739)">
												<image border="0" src="../images/relAcomp/verde9.png" title="Administrador"><image border="0" src="../images/relAcomp/verde3.png" title="Respons�vel T�cnico">
													</a>
												
												</td>

		
												<td align="center" nowrap>
		
													<a name="ancora1739" href="#" onclick="javascript:aoClicarConsultar(form, 42201, 1739)">
												<image border="0" src="../images/relAcomp/verde9.png" title="Administrador"><image border="0" src="../images/relAcomp/verde3.png" title="Respons�vel T�cnico">
													</a>
												
												</td>
												
									</tr>
									
									<tr class="cor_sim" onmouseover="javascript:destacaLinha(this,'over','')" onmouseout="javascript: destacaLinha(this,'out','cor_sim')" onClick="javascript: destacaLinha(this,'click','cor_sim')">
										<td>
										
										</td>
										<td>
											<table>
											<tr>

												<td nowrap>											
													&nbsp;
		
														<img src="<%=_pathEcar%>/images/pixel.gif" width="22" height="9">
		
												</td>
												<td valign="top"><img src="../images/icon_bullet_seta.png"></td>
												<td title="Programa:Justi�a">
													<font color="#596D9B">
													
														
													
														20 - Justi�a
													</font>
												</td>

											</tr>
											</table>
										</td>
										<td nowrap>
		
										</td>
										
										<td>
											
										</td>	
																
										<td align="center">
		SEJU

										</td>
		<td align="center"><p title="N�o foi solicitado acompanhamento"></p></td><td align="center"><p title="N�o foi solicitado acompanhamento"></p></td><td align="center"><p title="N�o foi solicitado acompanhamento"></p></td>										
									</tr>
									
									<tr class="cor_nao" onmouseover="javascript: destacaLinha(this,'over','')" onmouseout="javascript: destacaLinha(this,'out','cor_nao')" onClick="javascript: destacaLinha(this,'click','cor_nao')">

										<td>
										
										</td>
										<td>
											<table>
											<tr>
												<td nowrap>											
													&nbsp;
		
														<img src="<%=_pathEcar%>/images/pixel.gif" width="22" height="9">
		
														<img src="<%=_pathEcar%>/images/pixel.gif" width="22" height="9">

		
												</td>
												<td valign="top"><img src="../images/icon_bullet_seta.png"></td>
												<td title="A��o:Administra��o Geral do Sistema Penitenci�rio - DEPEN">
													<font color="#596D9B">
													
														
													
														Administra��o Geral do Sistema Penitenci�rio - DEPEN - Secretaria de Estado da Justi�a e da Cidadania
													</font>
												</td>
											</tr>
											</table>

										</td>
										<td nowrap>
		
										</td>
										
										<td>
											
										</td>	
																
										<td align="center">
		SEJU

										</td>
		<td align="center"><p title="N�o foi solicitado acompanhamento"></p></td><td align="center"><p title="N�o foi solicitado acompanhamento"></p></td><td align="center"><p title="N�o foi solicitado acompanhamento"></p></td>										
									</tr>
									
									<tr class="cor_sim" onmouseover="javascript:destacaLinha(this,'over','')" onmouseout="javascript: destacaLinha(this,'out','cor_sim')" onClick="javascript: destacaLinha(this,'click','cor_sim')">
										<td>
										
										</td>

										<td>
											<table>
											<tr>
												<td nowrap>											
													&nbsp;
		
														<img src="<%=_pathEcar%>/images/pixel.gif" width="22" height="9">
		
														<img src="<%=_pathEcar%>/images/pixel.gif" width="22" height="9">
		
														<img src="<%=_pathEcar%>/images/pixel.gif" width="22" height="9">
		
												</td>

												<td valign="top"><img src="../images/icon_bullet_seta.png"></td>
												<td title="Produto:Centro de Deten��o e Ressocializa��o de Londrina">
													<font color="#596D9B">
													
														
													
														Centro de Deten��o e Ressocializa��o de Londrina
													</font>
												</td>
											</tr>
											</table>
										</td>

										<td nowrap>
		
										</td>
										
										<td>
											
										</td>	
																
										<td align="center">

		SEJU

										</td>
		
												<td align="center" nowrap>
		
													<a name="ancora385" href="#" onclick="javascript:aoClicarConsultar(form, 11727, 385)">
												<image src="../images/relAcomp/amarelo9.png" title="Administrador" border="0"><image border="0" src="../images/relAcomp/amarelo3.png" title="Respons�vel T�cnico">
													</a>
												
												</td>
		
												<td align="center" nowrap>
		
													<a name="ancora385" href="#" onclick="javascript:aoClicarConsultar(form, 20649, 385)">

												<image border="0" src="../images/relAcomp/verde9.png" title="Administrador"><image border="0" src="../images/relAcomp/amarelo3.png" title="Respons�vel T�cnico">
													</a>
												
												</td>
		
												<td align="center" nowrap>
		
														<a name="ancora385"  href="#" onclick="javascript:aoClicarConsultar(form, 42222, 385)">
														
														<p title="N�o liberado">N/L</p>
														
														</a>
													
												</td>

												
									</tr>
									<tr><td></td></tr>
									
</table>


</form>


</div>

<%
		}
		if (Opcao.equals("indicadores")){

%>
<br>	
	<table width="100%" border="0" cellpadding="0" cellspacing="0">
		<tr>
			<td width="23%" align="center"><h1>Indicadores - Munic�pio de Londrina</h1></td>		
			<td width="77%"></td>	
		</tr>
	</table>
<br>

<table border="0" cellpadding="0" cellspacing="0" width="100%">		
								<tr>						
									<td class="espacador" colspan="9">
										<img src="../images/pixel.gif">
									</td>
								</tr>
								
								<!-- T�TULO DA TABELA -->

								<tr class="linha_titulo">
							 		<td colspan="9">
		
								 	</td>						 						 	
								</tr>
		
								<tr>						
									<td class="espacador" colspan="9">
										<img src="../images/pixel.gif">
									</td>
								</tr>
								
								<!-- CABE�ALHO DA TABELA -->

								<tr class="linha_subtitulo">
									<td width="1%"></td>							
									<td width="50%" colspan="3">Acompanhamentos</td>
									<td width="10%" align="center" nowrap>
										Org�o Respons�vel
									</td>

		
									<td align="center"  width="12.0%">Setembro/2006</td>
		
									<td align="center"  width="12.0%">Outubro/2006</td>
		
									<td align="center" id="selecionado" class="corSelecao" width="12.0%">Novembro/2006</td>
		
								</tr>	
		
								<tr>
									<td class="espacador" colspan="9">
										<img src="../images/pixel.gif">
									</td>

								</tr>
								
								<tr class="cor_sim bgCinzaClaro" onmouseover="javascript:destacaLinha(this,'over','')" onmouseout="javascript: destacaLinha(this,'out','cor_sim bgCinzaClaro')" onClick="javascript: destacaLinha(this,'click','cor_sim bgCinzaClaro')">
										<td>
										
										</td>
										<td>
											<table>
											<tr>
												<td nowrap>											
													&nbsp;

		
												</td>
												<td valign="top"><img src="../images/icon_bullet_seta.png"></td>
												<td title="Linha de A��o:Infra-Estrutura e Meio Ambiente">
													<font color="#596D9B">
													
														
													
														2 - Infra-Estrutura e Meio Ambiente 
													</font>
												</td>
											</tr>
											</table>

										</td>
										<td nowrap>
		
		
										</td>
										
										<td>
											
										</td>	
																
										<td align="center">
		
										</td>
		<td align="center"><p title="N�o foi solicitado acompanhamento"></p></td><td align="center"><p title="N�o foi solicitado acompanhamento"></p></td><td align="center"><p title="N�o foi solicitado acompanhamento"></p></td>										
									</tr>
									
									<tr class="cor_nao" onmouseover="javascript: destacaLinha(this,'over','')" onmouseout="javascript: destacaLinha(this,'out','cor_nao')" onClick="javascript: destacaLinha(this,'click','cor_nao')">
										<td>
										
										</td>
										<td>
											<table>
											<tr>
												<td nowrap>											
													&nbsp;

		
														<img src="<%=_pathEcar%>/images/pixel.gif" width="22" height="9">
		
												</td>
												<td valign="top"><img src="../images/icon_bullet_seta.png"></td>
												<td title="Programa:Meio Ambiente">
													<font color="#596D9B">
													
														
													
														07 - Meio Ambiente
													</font>
												</td>
											</tr>

											</table>
										</td>
										<td nowrap>
		
										</td>
										
										<td>
											
										</td>	
																
										<td align="center">
		SEMA

										</td>
		<td align="center"><p title="N�o foi solicitado acompanhamento"></p></td><td align="center"><p title="N�o foi solicitado acompanhamento"></p></td><td align="center"><p title="N�o foi solicitado acompanhamento"></p></td>										
									</tr>
									
									<tr class="cor_sim" onmouseover="javascript:destacaLinha(this,'over','')" onmouseout="javascript: destacaLinha(this,'out','cor_sim')" onClick="javascript: destacaLinha(this,'click','cor_sim')">
										<td>

										
										</td>
										<td>
											<table>
											<tr>
												<td nowrap>											
													&nbsp;
		
														<img src="<%=_pathEcar%>/images/pixel.gif" width="22" height="9">
		
														<img src="<%=_pathEcar%>/images/pixel.gif" width="22" height="9">
		
												</td>

												<td valign="top"><img src="../images/icon_bullet_seta.png"></td>
												<td title="A��o:Administra��o e Execu��o das A��es do FEMA">
													<font color="#596D9B">
													
														
													
														Administra��o e Execu��o das A��es do FEMA - Secretaria de Estado do Meio Ambiente e Recursos H�dricos
													</font>
												</td>
											</tr>
											</table>
										</td>

										<td nowrap>
		
										</td>
										
										<td>
											
										</td>	
																
										<td align="center">

		SEMA

										</td>
		<td align="center"><p title="N�o foi solicitado acompanhamento"></p></td><td align="center"><p title="N�o foi solicitado acompanhamento"></p></td><td align="center"><p title="N�o foi solicitado acompanhamento"></p></td>										
									</tr>
									
									<tr class="cor_nao" onmouseover="javascript: destacaLinha(this,'over','')" onmouseout="javascript: destacaLinha(this,'out','cor_nao')" onClick="javascript: destacaLinha(this,'click','cor_nao')">
										<td>
										
										</td>
										<td>
											<table>
											<tr>
												<td nowrap>											
													&nbsp;

		
														<img src="<%=_pathEcar%>/images/pixel.gif" width="22" height="9">
		
														<img src="<%=_pathEcar%>/images/pixel.gif" width="22" height="9">
		
														<img src="<%=_pathEcar%>/images/pixel.gif" width="22" height="9">
		
												</td>
												<td valign="top"><img src="../images/icon_bullet_seta.png"></td>
												<td title="Produto:Jardim Bot�nico de Londrina">
													<font color="#596D9B">
													
														
													
														Jardim Bot�nico de Londrina
													</font>

												</td>
											</tr>
											</table>
										</td>
										<td nowrap>
		
										</td>
										
										<td>

										</td>	
																
										<td align="center">
		SEMA

										</td>
		
												<td align="center" nowrap>
		
													<a name="ancora376" href="#" onclick="javascript:aoClicarConsultar(form, 11673, 376)">
												<image border="0" src="../images/indicadorVerde.png">

													</a>
												
												</td>
		
												<td align="center" nowrap>
		
													<a name="ancora376" href="#" onclick="javascript:aoClicarConsultar(form, 20597, 376)">
												<image border="0" src="../images/indicadorVerde.png">
													</a>
												
												</td>
		
												<td align="center" nowrap>
		
														<a name="ancora376"  href="#" onclick="javascript:aoClicarConsultar(form, 42171, 376)">

														
														<p title="N�o liberado">N/L</p>
														
														</a>
													
												</td>
												
									</tr>
									
									<tr class="cor_sim bgCinzaClaro" onmouseover="javascript:destacaLinha(this,'over','')" onmouseout="javascript: destacaLinha(this,'out','cor_sim bgCinzaClaro')" onClick="javascript: destacaLinha(this,'click','cor_sim bgCinzaClaro')">
										<td>
										
										</td>
										<td>
											<table>
											<tr >
												<td nowrap>											
													&nbsp;

		
												</td>
												<td valign="top"><img src="../images/icon_bullet_seta.png"></td>
												<td title="Linha de A��o:Emprego, Cidadania e Solidariedade">
													<font color="#596D9B">
													
														
													
														4 - Emprego, Cidadania e Solidariedade
													</font>
												</td>
											</tr>
											</table>

										</td>
										
										<td nowrap>
										</td>
										
										<td>
											
										</td>	
																
										<td align="center">
		
										</td>
		<td align="center"><p title="N�o foi solicitado acompanhamento"></p></td><td align="center"><p title="N�o foi solicitado acompanhamento"></p></td><td align="center"><p title="N�o foi solicitado acompanhamento"></p></td>										
									</tr>
									
									<tr class="cor_nao" onmouseover="javascript: destacaLinha(this,'over','')" onmouseout="javascript: destacaLinha(this,'out','cor_nao')" onClick="javascript: destacaLinha(this,'click','cor_nao')">
										<td>
										
										</td>
										<td>
											<table>
											<tr>

												<td nowrap>											
													&nbsp;
		
														<img src="<%=_pathEcar%>/images/pixel.gif" width="22" height="9">
		
												</td>
												<td valign="top"><img src="../images/icon_bullet_seta.png"></td>
												<td title="Programa:Sa�de">
													<font color="#596D9B">
													
														
													
														15 - Sa�de
													</font>
												</td>

											</tr>
											</table>
										</td>
										
										<td nowrap>
										</td>
										
										<td>
											
										</td>	
																
										<td align="center">
		SESA

										</td>
		<td align="center"><p title="N�o foi solicitado acompanhamento"></p></td><td align="center"><p title="N�o foi solicitado acompanhamento"></p></td><td align="center"><p title="N�o foi solicitado acompanhamento"></p></td>										
									</tr>
									
											
									<tr class="cor_sim" onmouseover="javascript:destacaLinha(this,'over','')" onmouseout="javascript: destacaLinha(this,'out','cor_sim')" onClick="javascript: destacaLinha(this,'click','cor_sim')">
										<td>
										
										</td>
										<td>
											<table>
											<tr>
												<td nowrap>											
													&nbsp;
		
														<img src="<%=_pathEcar%>/images/pixel.gif" width="22" height="9">

		
														<img src="<%=_pathEcar%>/images/pixel.gif" width="22" height="9">
		
												</td>
												<td valign="top"><img src="../images/icon_bullet_seta.png"></td>
												<td title="A��o:Manuten��o de A��es e Servi�os de Sa�de">
													<font color="#596D9B">
													
														
													
														Manuten��o de A��es e Servi�os de Sa�de - Secretaria de Estado da Sa�de
													</font>
												</td>
											</tr>

											</table>
										</td>
										<td nowrap>
		
										</td>
										
										<td>
											
										</td>	
																
										<td align="center">
		SESA

										</td>
		<td align="center"><p title="N�o foi solicitado acompanhamento"></p></td><td align="center"><p title="N�o foi solicitado acompanhamento"></p></td><td align="center"><p title="N�o foi solicitado acompanhamento"></p></td>										
									</tr>
									
											
									<tr class="cor_nao" onmouseover="javascript: destacaLinha(this,'over','')" onmouseout="javascript: destacaLinha(this,'out','cor_nao')" onClick="javascript: destacaLinha(this,'click','cor_nao')">
										<td>
										
										</td>
										<td>
											<table>
											<tr>
												<td nowrap>											
													&nbsp;
		
														<img src="<%=_pathEcar%>/images/pixel.gif" width="22" height="9">

		
														<img src="<%=_pathEcar%>/images/pixel.gif" width="22" height="9">
		
														<img src="<%=_pathEcar%>/images/pixel.gif" width="22" height="9">
		
												</td>
												<td valign="top"><img src="../images/icon_bullet_seta.png"></td>
												<td title="Produto:Realizar obras e reformas de unidades na �rea de Sa�de">
													<font color="#596D9B">
													
														
													
														Realizar obras e reformas de unidades na �rea de Sa�de
													</font>
												</td>

											</tr>
											</table>
										</td>
										<td nowrap>
		
										</td>
										
										<td>
											
										</td>	
																
										<td align="center">
		SESA

										</td>
		<td align="center"><p title="N�o foi solicitado acompanhamento"></p></td><td align="center"><p title="N�o foi solicitado acompanhamento"></p></td><td align="center"><p title="N�o foi solicitado acompanhamento"></p></td>										
									</tr>
									
											
									<tr class="cor_sim" onmouseover="javascript:destacaLinha(this,'over','')" onmouseout="javascript: destacaLinha(this,'out','cor_sim')" onClick="javascript: destacaLinha(this,'click','cor_sim')">
										<td>
										
										</td>
										<td>
											<table>
											<tr>
												<td nowrap>											
													&nbsp;
		
														<img src="<%=_pathEcar%>/images/pixel.gif" width="22" height="9">

		
														<img src="<%=_pathEcar%>/images/pixel.gif" width="22" height="9">
		
														<img src="<%=_pathEcar%>/images/pixel.gif" width="22" height="9">
		
														<img src="<%=_pathEcar%>/images/pixel.gif" width="22" height="9">
		
												</td>
												<td valign="top"><img src="../images/icon_bullet_seta.png"></td>
												<td title="Subproduto:Hospital Zona Norte - Londrina">
													<font color="#596D9B">
													
														
													
														Hospital Zona Norte - Londrina
													</font>

												</td>
											</tr>
											</table>
										</td>
										<td nowrap>
		
										</td>
										
										<td>
										
										</td>	
																
										<td align="center">
		SESA

										</td>
		
												<td align="center" nowrap>
		
													<a name="ancora1740" href="#" onclick="javascript:aoClicarConsultar(form, 11702, 1740)">
												<image border="0" src="../images/indicadorVerde.png">
													</a>

												
												</td>
		
												<td align="center" nowrap>
		
													<a name="ancora1740" href="#" onclick="javascript:aoClicarConsultar(form, 20624, 1740)">
												<image border="0" src="../images/indicadorVerde.png">
													</a>
												
												</td>
		
												<td align="center" nowrap>
		
													<a name="ancora1740" href="#" onclick="javascript:aoClicarConsultar(form, 42200, 1740)">
												<image border="0" src="../images/indicadorVerde.png">

													</a>
												
												</td>
												
									</tr>
		
									<tr class="cor_nao" onmouseover="javascript: destacaLinha(this,'over','')" onmouseout="javascript: destacaLinha(this,'out','cor_nao')" onClick="javascript: destacaLinha(this,'click','cor_nao')">
										<td>
										
										</td>
										<td>
											<table>
											<tr>

												<td nowrap>											
													&nbsp;
		
														<img src="<%=_pathEcar%>/images/pixel.gif" width="22" height="9">
		
														<img src="<%=_pathEcar%>/images/pixel.gif" width="22" height="9">
		
														<img src="<%=_pathEcar%>/images/pixel.gif" width="22" height="9">
		
														<img src="<%=_pathEcar%>/images/pixel.gif" width="22" height="9">
		
												</td>
												<td valign="top"><img src="../images/icon_bullet_seta.png"></td>
												<td title="Subproduto:Hospital Zona Sul - Londrina">

													<font color="#596D9B">
													
														
													
														Hospital Zona Sul - Londrina
													</font>
												</td>
											</tr>
											</table>
										</td>
										<td nowrap>
		
										</td>
										
										<td>
											
										</td>	
																
										<td align="center">
		SESA

										</td>
		
												<td align="center" nowrap>

		
													<a name="ancora1739" href="#" onclick="javascript:aoClicarConsultar(form, 11701, 1739)">
												<image border="0" src="../images/indicadorVerde.png">
													</a>
												
												</td>
		
												<td align="center" nowrap>
		
													<a name="ancora1739" href="#" onclick="javascript:aoClicarConsultar(form, 20623, 1739)">
												<image border="0" src="../images/indicadorVerde.png">
													</a>
												
												</td>

		
												<td align="center" nowrap>
		
													<a name="ancora1739" href="#" onclick="javascript:aoClicarConsultar(form, 42201, 1739)">
												<image border="0" src="../images/indicadorVerde.png">
													</a>
												
												</td>
												
									</tr>
									
									<tr class="cor_sim" onmouseover="javascript:destacaLinha(this,'over','')" onmouseout="javascript: destacaLinha(this,'out','cor_sim')" onClick="javascript: destacaLinha(this,'click','cor_sim')">
										<td>
										
										</td>
										<td>
											<table>
											<tr>

												<td nowrap>											
													&nbsp;
		
														<img src="<%=_pathEcar%>/images/pixel.gif" width="22" height="9">
		
												</td>
												<td valign="top"><img src="../images/icon_bullet_seta.png"></td>
												<td title="Programa:Justi�a">
													<font color="#596D9B">
													
														
													
														20 - Justi�a
													</font>
												</td>

											</tr>
											</table>
										</td>
										<td nowrap>
		
										</td>
										
										<td>
											
										</td>	
																
										<td align="center">
		SEJU

										</td>
		<td align="center"><p title="N�o foi solicitado acompanhamento"></p></td><td align="center"><p title="N�o foi solicitado acompanhamento"></p></td><td align="center"><p title="N�o foi solicitado acompanhamento"></p></td>										
									</tr>
									
									<tr class="cor_nao" onmouseover="javascript: destacaLinha(this,'over','')" onmouseout="javascript: destacaLinha(this,'out','cor_nao')" onClick="javascript: destacaLinha(this,'click','cor_nao')">

										<td>
										
										</td>
										<td>
											<table>
											<tr>
												<td nowrap>											
													&nbsp;
		
														<img src="<%=_pathEcar%>/images/pixel.gif" width="22" height="9">
		
														<img src="<%=_pathEcar%>/images/pixel.gif" width="22" height="9">

		
												</td>
												<td valign="top"><img src="../images/icon_bullet_seta.png"></td>
												<td title="A��o:Administra��o Geral do Sistema Penitenci�rio - DEPEN">
													<font color="#596D9B">
													
														
													
														Administra��o Geral do Sistema Penitenci�rio - DEPEN - Secretaria de Estado da Justi�a e da Cidadania
													</font>
												</td>
											</tr>
											</table>

										</td>
										<td nowrap>
		
										</td>
										
										<td>
											
										</td>	
																
										<td align="center">
		SEJU

										</td>
		<td align="center"><p title="N�o foi solicitado acompanhamento"></p></td><td align="center"><p title="N�o foi solicitado acompanhamento"></p></td><td align="center"><p title="N�o foi solicitado acompanhamento"></p></td>										
									</tr>
									
									<tr class="cor_sim" onmouseover="javascript:destacaLinha(this,'over','')" onmouseout="javascript: destacaLinha(this,'out','cor_sim')" onClick="javascript: destacaLinha(this,'click','cor_sim')">
										<td>
										
										</td>

										<td>
											<table>
											<tr>
												<td nowrap>											
													&nbsp;
		
														<img src="/ecar/images/pixel.gif" width="22" height="9">
		
														<img src="/ecar/images/pixel.gif" width="22" height="9">
		
														<img src="/ecar/images/pixel.gif" width="22" height="9">
		
												</td>

												<td valign="top"><img src="../images/icon_bullet_seta.png"></td>
												<td title="Produto:Centro de Deten��o e Ressocializa��o de Londrina">
													<font color="#596D9B">
													
														
													
														Centro de Deten��o e Ressocializa��o de Londrina
													</font>
												</td>
											</tr>
											</table>
										</td>

										<td nowrap>
		
										</td>
										
										<td>
											
										</td>	
																
										<td align="center">

		SEJU

										</td>
		
												<td align="center" nowrap>
		
													<a name="ancora385" href="#" onclick="javascript:aoClicarConsultar(form, 11727, 385)">
												<img border="0" src="../images/indicadorAmarelo.png">
													</a>
												
												</td>
		
												<td align="center" nowrap>
		
													<a name="ancora385" href="#" onclick="javascript:aoClicarConsultar(form, 20649, 385)">

												<img border="0" src="../images/indicadorAmarelo.png">
													</a>
												
												</td>
		
												<td align="center" nowrap>
		
														<a name="ancora385"  href="#" onclick="javascript:aoClicarConsultar(form, 42222, 385)">
														
														<p title="N�o liberado">N/L</p>
														
														</a>
													
												</td>

												
									</tr>
									<tr><td></td></tr>
									
</table>


</form>


</div>


<%
		}
		if (Opcao.equals("realizado")){
%>
	<%@ include file="./geoRealizadoLondrina.jsp"%>
<%
		} 
		if (Opcao.equals("eleicoes")){
%>
	<%@ include file="./geoEleicoesLondrina.jsp"%>
<%
		}
		if (Opcao.equals("politica")){
%>
	<%@ include file="./geoPoliticaLondrina.jsp"%>
<%
		}
		if (Opcao.equals("socio")){
%>
	<%@ include file="./geoSocioEconomicaLondrina.jsp"%>
<%
		}
	}

%>

</body>

</html>