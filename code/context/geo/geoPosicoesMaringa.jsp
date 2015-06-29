<html lang="pt-br">
<head>

<%@ include file="../include/meta.jsp"%>
<title>e-CAR</title>
<link rel="stylesheet" href="<%=_pathEcar%>/css/style_<%=new ecar.dao.ConfiguracaoDao(request).getConfiguracao().getEstilo().getNome()%>.css" type="text/css">

<script language="javascript" src="<%=_pathEcar%>/js/destaqueLinha.js"></script>
<script>
function aoClicarConsultar(form, codigo, codIett){ 
	form.codAri.value = codigo;
	document.form.action = "./geoAvaliacoesMaringa.jsp?primeiroIettClicado=" + codIett + "&primeiroAriClicado=" + codigo;
	document.form.submit();
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
<table width="100%" border="0" cellpadding="0" cellspacing="0">
		<tr>
			<a name="ancora" id="ancora">
			<td width="18%" align="center"><h1>Posições - Município de Maringá</h1></td>		
			<td width="92%"></td>	
			</a>
		</tr>
	</table>
<br>

<table border="0" cellpadding="0" cellspacing="0" width="100%">		
								<tr>						
									<td class="espacador" colspan="9">
										<img src="../images/pixel.gif">
									</td>
								</tr>
								
								<!-- TÍTULO DA TABELA -->

								<tr class="linha_titulo">
							 		<td colspan="9">
		
								 	</td>						 						 	
								</tr>
		
								<tr>						
									<td class="espacador" colspan="9">
										<img src="../images/pixel.gif">
									</td>
								</tr>
								
								<!-- CABEÇALHO DA TABELA -->

								<tr class="linha_subtitulo">
									<td width="1%"></td>							
									<td width="50%" colspan="3">Acompanhamentos</td>
									<td width="10%" align="center" nowrap>
										Orgão Responsável
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
												<td title="Linha de Ação:Infra-Estrutura e Meio Ambiente">
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
		<td align="center"><p title="Não foi solicitado acompanhamento"></p></td><td align="center"><p title="Não foi solicitado acompanhamento"></p></td><td align="center"><p title="Não foi solicitado acompanhamento"></p></td>										
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
													
														
													
														 04 - Bons Caminhos
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
		SETR

										</td>
		<td align="center"><p title="Não foi solicitado acompanhamento"></p></td><td align="center"><p title="Não foi solicitado acompanhamento"></p></td><td align="center"><p title="Não foi solicitado acompanhamento"></p></td>										
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
												<td title="Ação:Administração e Execução das Ações do FEMA">
													<font color="#596D9B">
													
														
													
														  Construção de Obras Rodoviárias - Boa Estrada
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

		SETR

										</td>
		<td align="center"><p title="Não foi solicitado acompanhamento"></p></td><td align="center"><p title="Não foi solicitado acompanhamento"></p></td><td align="center"><p title="Não foi solicitado acompanhamento"></p></td>										
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
												<td title="Produto:Jardim Botânico de Londrina">
													<font color="#596D9B">
													
														
													
														 PR-323 - Maringá - Doutor Camargo (duplicação)
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
		SETR

										</td>
		
												<td align="center" nowrap>
		
													<a name="ancora376" href="#" onclick="javascript:aoClicarConsultar(form, 11673, 376)">
												<image border="0" src="../images/relAcomp/verde9.png" title="Administrador"><image border="0" src="../images/relAcomp/verde3.png" title="Responsável Técnico">

													</a>
												
												</td>
		
												<td align="center" nowrap>
		
													<a name="ancora376" href="#" onclick="javascript:aoClicarConsultar(form, 20597, 376)">
												<image border="0" src="../images/relAcomp/verde9.png" title="Administrador"><image border="0" src="../images/relAcomp/verde3.png" title="Responsável Técnico">
													</a>
												
												</td>
		
												<td align="center" nowrap>
		
														<a name="ancora376"  href="#" onclick="javascript:aoClicarConsultar(form, 42171, 376)">

														
														<p title="Não liberado">N/L</p>
														
														</a>
													
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
												<td title="Linha de Ação:Emprego, Cidadania e Solidariedade">
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
		<td align="center"><p title="Não foi solicitado acompanhamento"></p></td><td align="center"><p title="Não foi solicitado acompanhamento"></p></td><td align="center"><p title="Não foi solicitado acompanhamento"></p></td>										
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
												<td title="Programa:Saúde">
													<font color="#596D9B">
													
														
													
														 16 - Assistência Social
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
		SETP

										</td>
		<td align="center"><p title="Não foi solicitado acompanhamento"></p></td><td align="center"><p title="Não foi solicitado acompanhamento"></p></td><td align="center"><p title="Não foi solicitado acompanhamento"></p></td>										
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
												<td title="Ação:Manutenção de Ações e Serviços de Saúde">
													<font color="#596D9B">
													
														
													
														 Atenção ao Adolescente em Medidas Sócio-Educativas
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
		SETP

										</td>
		<td align="center"><p title="Não foi solicitado acompanhamento"></p></td><td align="center"><p title="Não foi solicitado acompanhamento"></p></td><td align="center"><p title="Não foi solicitado acompanhamento"></p></td>										
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
												<td title="Produto:Realizar obras e reformas de unidades na Área de Saúde">
													<font color="#596D9B">
													
														
													
														 Centro Regionalizado de Sócio Educação de Maringá
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
		SETP

										</td>
										<td align="center" nowrap>
		
													<a name="ancora1740" href="#" onclick="javascript:aoClicarConsultar(form, 11702, 1740)">
												<image border="0" src="../images/relAcomp/verde9.png" title="Administrador"><image border="0" src="../images/relAcomp/verde3.png" title="Responsável Técnico">
													</a>

												
												</td>
		
												<td align="center" nowrap>
		
													<a name="ancora1740" href="#" onclick="javascript:aoClicarConsultar(form, 20624, 1740)">
												<image border="0" src="../images/relAcomp/verde9.png" title="Administrador"><image border="0" src="../images/relAcomp/verde3.png" title="Responsável Técnico">
													</a>
												
												</td>
		
												<td align="center" nowrap>
													<a name="ancora1740" href="#" onclick="javascript:aoClicarConsultar(form, 20624, 1740)">
														<p title="Não liberado">N/L</p>
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
												<td title="Programa:Justiça">
													<font color="#596D9B">
													
														
													
														20 - Justiça
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
		<td align="center"><p title="Não foi solicitado acompanhamento"></p></td><td align="center"><p title="Não foi solicitado acompanhamento"></p></td><td align="center"><p title="Não foi solicitado acompanhamento"></p></td>										
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
												<td title="Ação:Administração Geral do Sistema Penitenciário - DEPEN">
													<font color="#596D9B">
													
														
													
														Administração Geral do Sistema Penitenciário - DEPEN - Secretaria de Estado da Justiça e da Cidadania
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
		<td align="center"><p title="Não foi solicitado acompanhamento"></p></td><td align="center"><p title="Não foi solicitado acompanhamento"></p></td><td align="center"><p title="Não foi solicitado acompanhamento"></p></td>										
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
												<td title="Produto:Centro de Detenção e Ressocialização de Londrina">
													<font color="#596D9B">
													
														
													
														 Centro de Detenção Provisória de Maringá
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
												<image border="0" src="../images/relAcomp/amarelo9.png" title="Administrador"><image border="0" src="../images/relAcomp/amarelo3.png" title="Responsável Técnico">
													</a>
												
												</td>
		
												<td align="center" nowrap>
		
													<a name="ancora385" href="#" onclick="javascript:aoClicarConsultar(form, 20649, 385)">

												<image border="0" src="../images/relAcomp/verde9.png" title="Administrador"><image border="0" src="../images/relAcomp/amarelo3.png" title="Responsável Técnico">
													</a>
												
												</td>
		
												<td align="center" nowrap>
		
														<a name="ancora385"  href="#" onclick="javascript:aoClicarConsultar(form, 42222, 385)">
														
														<p title="Não liberado">N/L</p>
														
														</a>
													
												</td>

												
									</tr>
									<tr><td></td></tr>
									
</table>


</form>


</div>

</body>

</html>