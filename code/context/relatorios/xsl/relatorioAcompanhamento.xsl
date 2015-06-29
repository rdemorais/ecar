<?xml version="1.0" encoding="ISO-8859-1"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" 
	xmlns:fo="http://www.w3.org/1999/XSL/Format" version="1.0">
	
	<xsl:output method="xml" version="1.0" encoding="ISO-8859-1"/>
	
	<xsl:attribute-set name="label">
		<xsl:attribute name="font-size">10.0pt</xsl:attribute>
		<xsl:attribute name="font-family">sans-serif</xsl:attribute>
		<xsl:attribute name="font-weight">normal</xsl:attribute>		
	</xsl:attribute-set>	

	<xsl:attribute-set name="cabecalho-titulo">
		<xsl:attribute name="font-size">13.0pt</xsl:attribute>
		<xsl:attribute name="font-family">sans-serif</xsl:attribute>
		<xsl:attribute name="font-weight">bold</xsl:attribute>		
		<xsl:attribute name="color">black</xsl:attribute>
	</xsl:attribute-set>	

	<xsl:attribute-set name="cabecalho-subtitulo">
		<xsl:attribute name="font-size">13.0pt</xsl:attribute>
		<xsl:attribute name="font-family">sans-serif</xsl:attribute>
		<xsl:attribute name="font-weight">normal</xsl:attribute>		
		<xsl:attribute name="color">black</xsl:attribute>
	</xsl:attribute-set>	

	<xsl:attribute-set name="cabecalho-codigo">
		<xsl:attribute name="font-size">8.0pt</xsl:attribute>
		<xsl:attribute name="font-family">sans-serif</xsl:attribute>
		<xsl:attribute name="font-weight">bold</xsl:attribute>		
		<xsl:attribute name="color">black</xsl:attribute>
	</xsl:attribute-set>	

	<xsl:attribute-set name="rodape">
		<xsl:attribute name="font-size">10.0pt</xsl:attribute>
		<xsl:attribute name="font-family">sans-serif</xsl:attribute>
		<xsl:attribute name="font-weight">normal</xsl:attribute>		
		<xsl:attribute name="color">darkblue</xsl:attribute>
	</xsl:attribute-set>	

	<xsl:attribute-set name="orgao">
		<xsl:attribute name="font-size">12.0pt</xsl:attribute>
		<xsl:attribute name="font-family">sans-serif</xsl:attribute>
		<xsl:attribute name="font-weight">bold</xsl:attribute>		
		<xsl:attribute name="color">darkblue</xsl:attribute>
	</xsl:attribute-set>	

	<xsl:attribute-set name="labelObservacoes">
		<!-- xsl:attribute name="font-size">12.0pt</xsl:attribute -->
		<xsl:attribute name="font-size">9.0pt</xsl:attribute>
		<xsl:attribute name="font-family">sans-serif</xsl:attribute>
		<xsl:attribute name="font-weight">normal</xsl:attribute>		
		<xsl:attribute name="color">darkblue</xsl:attribute>
	</xsl:attribute-set>	

	<xsl:attribute-set name="labelRespTecnico">
		<!-- xsl:attribute name="font-size">12.0pt</xsl:attribute -->
		<xsl:attribute name="font-size">8.0pt</xsl:attribute>
		<xsl:attribute name="font-family">sans-serif</xsl:attribute>
		<xsl:attribute name="font-weight">normal</xsl:attribute>		
		<xsl:attribute name="color">darkblue</xsl:attribute>
	</xsl:attribute-set>	

	<xsl:attribute-set name="nomeItem">
		<xsl:attribute name="font-size">12.0pt</xsl:attribute>
		<xsl:attribute name="font-family">sans-serif</xsl:attribute>
		<xsl:attribute name="font-weight">bold</xsl:attribute>		
		<xsl:attribute name="color">darkblue</xsl:attribute>
	</xsl:attribute-set>	

	<xsl:attribute-set name="nomeItemFilho">
		<xsl:attribute name="font-size">10.0pt</xsl:attribute>
		<xsl:attribute name="font-family">sans-serif</xsl:attribute>
		<xsl:attribute name="font-weight">bold</xsl:attribute>		
		<xsl:attribute name="color">darkblue</xsl:attribute>
	</xsl:attribute-set>	

	<xsl:attribute-set name="texto">
		<xsl:attribute name="font-size">10.0pt</xsl:attribute>
		<xsl:attribute name="font-family">sans-serif</xsl:attribute>
		<xsl:attribute name="font-weight">normal</xsl:attribute>		
		<xsl:attribute name="color">black</xsl:attribute>
	</xsl:attribute-set>	

	<xsl:attribute-set name="textoNegrito">
		<xsl:attribute name="font-size">10.0pt</xsl:attribute>
		<xsl:attribute name="font-family">sans-serif</xsl:attribute>
		<xsl:attribute name="font-weight">bold</xsl:attribute>		
		<xsl:attribute name="color">black</xsl:attribute>
	</xsl:attribute-set>	

	<xsl:attribute-set name="textoMenor">
		<xsl:attribute name="font-size">9.0pt</xsl:attribute>
		<xsl:attribute name="font-family">sans-serif</xsl:attribute>
		<xsl:attribute name="font-weight">normal</xsl:attribute>		
	</xsl:attribute-set>	

	<xsl:attribute-set name="textoMenorNegrito">
		<xsl:attribute name="font-size">9.0pt</xsl:attribute>
		<xsl:attribute name="font-family">sans-serif</xsl:attribute>
		<xsl:attribute name="font-weight">bold</xsl:attribute>		
	</xsl:attribute-set>	

	<xsl:attribute-set name="textoMenorIndicador">
		<xsl:attribute name="font-size">7.0pt</xsl:attribute>
		<xsl:attribute name="font-family">sans-serif</xsl:attribute>
		<xsl:attribute name="font-weight">normal</xsl:attribute>		
	</xsl:attribute-set>	

	<xsl:attribute-set name="textoMenorNegritoIndicador">
		<xsl:attribute name="font-size">7.0pt</xsl:attribute>
		<xsl:attribute name="font-family">sans-serif</xsl:attribute>
		<xsl:attribute name="font-weight">bold</xsl:attribute>		
	</xsl:attribute-set>	


	<xsl:attribute-set name="textoMenorValorEvolucao">
		<xsl:attribute name="font-size">8.0pt</xsl:attribute>
		<xsl:attribute name="font-family">sans-serif</xsl:attribute>
		<xsl:attribute name="font-weight">normal</xsl:attribute>		
	</xsl:attribute-set>	

	<xsl:attribute-set name="valorTabela">
		<xsl:attribute name="font-size">8.0pt</xsl:attribute>
		<xsl:attribute name="font-family">sans-serif</xsl:attribute>
		<xsl:attribute name="font-weight">bold</xsl:attribute>		
	</xsl:attribute-set>	

	<xsl:attribute-set name="tituloComplemento">
		<xsl:attribute name="font-size">11.0pt</xsl:attribute>
		<xsl:attribute name="font-family">sans-serif</xsl:attribute>
		<xsl:attribute name="font-weight">bold</xsl:attribute>		
	</xsl:attribute-set>	
	
	<xsl:attribute-set name="tituloComplementoFilho">
		<xsl:attribute name="font-size">8.0pt</xsl:attribute>
		<xsl:attribute name="font-family">sans-serif</xsl:attribute>
		<xsl:attribute name="font-weight">bold</xsl:attribute>		
	</xsl:attribute-set>	

	<xsl:attribute-set name="itemArvore">
		<xsl:attribute name="font-size">13.0pt</xsl:attribute>
		<xsl:attribute name="font-family">sans-serif</xsl:attribute>
		<xsl:attribute name="font-weight">bold</xsl:attribute>		
		<xsl:attribute name="color">darkblue</xsl:attribute>
	</xsl:attribute-set>	

	<xsl:attribute-set name="textoLegenda">
		<xsl:attribute name="font-size">8.0pt</xsl:attribute>
		<xsl:attribute name="font-family">sans-serif</xsl:attribute>
		<xsl:attribute name="font-weight">normal</xsl:attribute>		
	</xsl:attribute-set>	

	<xsl:template match="relatorio">
		<fo:root>
			<fo:layout-master-set>
				<!-- 
				<fo:simple-page-master master-name="A4_Paisagem" page-height="21cm" page-width="29.8cm"
					margin-top="1.5cm" margin-bottom="1.5cm" margin-left="1cm" margin-right="1cm">
					<fo:region-body margin-top="0.5cm" margin-bottom="1.5cm"/>
					<fo:region-before extent="2cm"/>
					<fo:region-after extent="1cm"/>
				</fo:simple-page-master>
				-->
				
				<!-- fo:simple-page-master master-name="A4_Paisagem" page-height="21cm" page-width="29.8cm"
					margin-top="0.5cm" margin-bottom="0.5cm" margin-left="1cm" margin-right="1cm">
					<fo:region-body margin-top="0.5cm" margin-bottom="1.5cm"/>
					<fo:region-before extent="2cm"/>
					<fo:region-after extent="1cm"/>
				</fo:simple-page-master -->

				<fo:simple-page-master master-name="A4_Paisagem" page-height="21cm" page-width="29.8cm"
					margin-top="0.5cm" margin-bottom="0.5cm" margin-left="0.5cm" margin-right="0.5cm">
					<fo:region-body margin-top="2cm" margin-bottom="0.7cm"/>
					<fo:region-before extent="3.5cm"/>
					<fo:region-after extent="0.5cm"/>
				</fo:simple-page-master>

				<fo:simple-page-master master-name="A4_Retrato" page-height="29.8cm" page-width="21cm"
					margin-top="3cm" margin-bottom="2cm" margin-left="2cm" margin-right="2cm">
					<fo:region-body margin-top="2cm" margin-bottom="2cm"/>
					<fo:region-before extent="1cm"/>
					<fo:region-after extent="1cm"/>
				</fo:simple-page-master>
			</fo:layout-master-set>

			<fo:page-sequence master-reference="A4_Paisagem">
				<fo:static-content flow-name="xsl-region-before">
					<fo:block space-after="0.3cm">
						<fo:external-graphic src="url('{@caminhoImagemCab}')" width="29cm" height="2cm"/>
					</fo:block>
					<fo:block text-align="center" border-bottom-color="silver" border-bottom-style="solid" border-bottom-width="0.02mm">
						<!-- fo:table table-layout="fixed">
							<fo:table-column column-width="22.90cm"/>
							<fo:table-column column-width="4.90cm"/>
							<fo:table-body>
								<fo:table-row>
									<fo:table-cell>
										<fo:block text-align="left" xsl:use-attribute-sets="cabecalho-titulo">
											<xsl:value-of select="@titulo"/>
										</fo:block>	
										<fo:block text-align="left" xsl:use-attribute-sets="cabecalho-subtitulo">
											Relatório de Acompanhamento - <xsl:value-of select="@mesReferencia"/>
										</fo:block>	
									</fo:table-cell>
									<fo:table-cell display-align="after">
										<fo:block text-align="right" xsl:use-attribute-sets="cabecalho-codigo">
											<xsl:value-of select="@codModelo"/>
										</fo:block>
									</fo:table-cell>								
								</fo:table-row>																											
							</fo:table-body>
						</fo:table -->	

						<fo:table table-layout="fixed">
							<fo:table-column column-width="22.90cm"/>
							<fo:table-column column-width="4.90cm"/>
							<fo:table-body>
								<fo:table-row>
									<fo:table-cell>
										<fo:block text-align="left" xsl:use-attribute-sets="cabecalho-subtitulo">
											Relatório de Acompanhamento - <xsl:value-of select="@mesReferencia"/>
										</fo:block>	
									</fo:table-cell>
									<fo:table-cell display-align="after">
										<fo:block text-align="right" xsl:use-attribute-sets="cabecalho-codigo">
											<xsl:value-of select="@codModelo"/>
										</fo:block>
									</fo:table-cell>								
								</fo:table-row>																											
							</fo:table-body>
						</fo:table>	
					</fo:block>
				</fo:static-content>

				<fo:static-content flow-name="xsl-region-after">
					<fo:block text-align="right">
						<fo:table table-layout="fixed">
							<fo:table-column column-width="25.0cm"/>
							<fo:table-column column-width="3.0cm"/>
							<fo:table-body>
								<fo:table-row>
									<fo:table-cell>
										<fo:block text-align="left" xsl:use-attribute-sets="rodape">
											<xsl:value-of select="@rodape"/>
										</fo:block>	
									</fo:table-cell>
									<fo:table-cell>
										<fo:block text-align="right" xsl:use-attribute-sets="rodape">
											Página <fo:page-number/> de <fo:page-number-citation ref-id="last-page"/>
										</fo:block>
									</fo:table-cell>								
								</fo:table-row>																											
							</fo:table-body>
						</fo:table>	
					</fo:block>
				</fo:static-content>

				<fo:flow flow-name="xsl-region-body">
					<xsl:apply-templates select="principal"/>
					<fo:block id="last-page"/>
				</fo:flow>
			</fo:page-sequence>			

		</fo:root>
	</xsl:template>

	<xsl:template match="principal">
		<xsl:if test="itens">
			<xsl:apply-templates select="itens"/>
		</xsl:if>
		<xsl:if test="semItens">
			<xsl:apply-templates select="semItens"/>
		</xsl:if>
	</xsl:template>
	
	<xsl:template match="itens">
		<!-- 
		Se quebrarPaginaItens = N, então a quebra de página é controlada pelo template
		hierarquia. (ECAR-001C)
		 -->
		<xsl:if test="@quebrarPaginaItens = 'S'">
			<fo:block space-before="1cm" break-before="page">
				<fo:table table-layout="fixed">
					<fo:table-column/>
					<fo:table-header>
						<fo:table-row>
							<fo:table-cell>
								<xsl:if test="@exibirOrgao = 'S'">
									<fo:block xsl:use-attribute-sets="orgao">
										<xsl:value-of select="@orgao"/>
									</fo:block>
								</xsl:if>
							</fo:table-cell>
						</fo:table-row>
					</fo:table-header>
					<fo:table-body>
						<xsl:apply-templates select="item"/>
					</fo:table-body>
				</fo:table>
			</fo:block>
		</xsl:if>
		<xsl:if test="@quebrarPaginaItens = 'N'">
			<fo:block space-before="1cm">
				<fo:table table-layout="fixed">
					<fo:table-column/>
					<fo:table-header>
						<fo:table-row>
							<fo:table-cell>
								<xsl:if test="@exibirOrgao = 'S'">
									<fo:block xsl:use-attribute-sets="orgao">
										<xsl:value-of select="@orgao"/>
									</fo:block>
								</xsl:if>
							</fo:table-cell>
						</fo:table-row>
					</fo:table-header>
					<fo:table-body>
						<xsl:apply-templates select="item"/>
					</fo:table-body>
				</fo:table>
			</fo:block>
		</xsl:if>
	</xsl:template>
	
	<xsl:template match="hierarquia">
		<xsl:if test="@quebrarPagina = 'S'">
			<fo:block break-before="page"/>
		</xsl:if>
		<fo:table>
			<fo:table-column column-width="1.5cm"/>
			<fo:table-column/>
			<fo:table-body>
				<!-- fo:table-row -->
					<!-- fo:table-cell -->
						<xsl:apply-templates select="itemHierarquia"/>
					<!-- /fo:table-cell -->
				<!-- /fo:table-row -->
			</fo:table-body>
		</fo:table>
		<fo:block space-before="0.2cm"/>
	</xsl:template>
	
	<xsl:template match="itemHierarquia">
		<fo:table-row>
			<fo:table-cell>
				<fo:block xsl:use-attribute-sets="itemArvore">
					<xsl:value-of select="@sigla"/>
				</fo:block>
			</fo:table-cell>
			<fo:table-cell>
				<fo:block xsl:use-attribute-sets="itemArvore">
					<!-- fo:leader>
						<xsl:attribute name="leader-length">
							<xsl:value-of select="(@nivel * 3)"/>mm
						</xsl:attribute>											
					</fo:leader -->	
					<xsl:value-of select="@nomeItem"/>
				</fo:block>
			</fo:table-cell>
		</fo:table-row>
	</xsl:template>
	
	<xsl:template match="item">
		<fo:table-row>
			<fo:table-cell>
				<fo:block space-before="0.2cm">
				
					<xsl:apply-templates select="hierarquia"/>
				
					<fo:table>
						<fo:table-column column-width="1cm"/>
						<!--fo:table-column column-width="16cm"/ -->
						<fo:table-column column-width="13cm"/>
						<!-- fo:table-column column-width="0.5cm"/ -->
						<fo:table-column column-width="0.4cm"/>
						<fo:table-column/>
						
						<fo:table-body>
						
							<xsl:if test="@nomeItem != ''">
								<fo:table-row height="1cm">
									<fo:table-cell background-color="#CCC"  number-columns-spanned="2" display-align="center">
										<fo:block>
											<fo:table>
												<fo:table-column/>
												<fo:table-column column-width="2.cm"/>
												<fo:table-body>
													<fo:table-row>
														<fo:table-cell>
															<fo:block xsl:use-attribute-sets="nomeItem">
																<xsl:value-of select="@nomeItem"/>
															</fo:block>
														</fo:table-cell>
														<fo:table-cell>
															<fo:block xsl:use-attribute-sets="nomeItem" text-align="right">
																<xsl:value-of select="@orgaoItem"/>
															</fo:block>
														</fo:table-cell>
													</fo:table-row>
												</fo:table-body>
											</fo:table>
										</fo:block>
									</fo:table-cell>
									<fo:table-cell display-align="center"/>
									<fo:table-cell background-color="#CCC" display-align="center">
										<!-- fo:block xsl:use-attribute-sets="labelObservacoes">
											Observações:
										</fo:block -->
										<fo:block xsl:use-attribute-sets="labelRespTecnico">
											<xsl:value-of select="@labelRespTecnicoIett"/> <xsl:value-of select="@valorRespTecnicoIett"/>
										</fo:block>
									</fo:table-cell>
								</fo:table-row>
							</xsl:if>
							
							<xsl:apply-templates select="parecer"/>
						</fo:table-body>
					</fo:table>	
					
					<!-- xsl:apply-templates select="indicadores"/ -->
					<!-- xsl:apply-templates select="evolucaoFinanceira"/ -->
					<xsl:if test="@exibirEncaminhamentos != 'N'">			
						<!-- Quando tiver evolucaoFinanceira, não vai ter evolucaoFinanceiraSemValor
						para o mesmo item.  -->
						<xsl:apply-templates select="evolucaoFinanceira"/>
					
						<xsl:apply-templates select="etapas"/>
						<xsl:apply-templates select="itensFilhos"/>
					</xsl:if>
					<!-- xsl:apply-templates select="itensFilhos"/ -->
					
				</fo:block>
			</fo:table-cell>
		</fo:table-row>
	</xsl:template>
	
	<xsl:template match="parecer">
		<xsl:if test="../@ehPPA = 'S'">
			<fo:table-row>
				<!--
				Se descomentar, não esquecer de tirar o number-columns-spanned="2" da coluna abaixo.
				  fo:table-cell display-align="center">
					<fo:block>
						<fo:external-graphic src="url('{@caminhoImagem}')"/>
					</fo:block>
				</fo:table-cell-->
				<fo:table-cell number-columns-spanned="4">
					<xsl:if test="../@valorDR1 != ''">
						<fo:block space-before="0.2cm" xsl:use-attribute-sets="texto" text-align="left" white-space-collapse="false">
							<fo:inline xsl:use-attribute-sets="tituloComplemento"><xsl:value-of select="../@labelDR1"/></fo:inline>: <xsl:value-of select="../@valorDR1"/>
						</fo:block>
					</xsl:if>
					<!-- 
					<fo:block space-before="0.2cm" xsl:use-attribute-sets="texto" text-align="justify">
						<fo:inline xsl:use-attribute-sets="tituloComplemento"><xsl:value-of select="../@labelBeneficiosIett"/></fo:inline>: <xsl:value-of select="../@valorBeneficiosIett"/>
					</fo:block>
					-->
	
					<xsl:if test="../@valorDR4 != ''">
						<fo:block space-before="0.5cm" xsl:use-attribute-sets="texto" white-space-collapse="false">
							<fo:inline xsl:use-attribute-sets="tituloComplemento"><xsl:value-of select="../@labelDR4"/></fo:inline>: <xsl:value-of select="../@valorDR4"/>
						</fo:block>
					</xsl:if>
					
					<xsl:if test="@situacaoParecer != ''">
						<fo:block space-before="0.5cm" xsl:use-attribute-sets="texto" text-align="left">
							<fo:inline xsl:use-attribute-sets="tituloComplemento"><xsl:value-of select="@labelSituacaoParecer"/></fo:inline>: <xsl:value-of select="@situacaoParecer"/>
						</fo:block>
					</xsl:if>
					<!-- 
					<fo:block space-before="0.2cm" xsl:use-attribute-sets="texto">
						<fo:inline xsl:use-attribute-sets="textoNegrito"><xsl:value-of select="@labelParecer"/> <xsl:value-of select="@dataUltParecer"/>:</fo:inline>
					</fo:block>
					
					<fo:block space-before="0.2cm">
						<fo:table>
							<fo:table-column column-width="1cm"/>
							<fo:table-column/>
							<fo:table-body>
								<fo:table-row>
									<fo:table-cell display-align="top">
										<fo:external-graphic src="url('{@caminhoImagem}')"/>
									</fo:table-cell>
									<fo:table-cell display-align="top">
										<fo:block white-space-collapse="false" xsl:use-attribute-sets="texto">
										 	<xsl:value-of select="@descricao"/>
										 </fo:block>
									</fo:table-cell>
								</fo:table-row>
							</fo:table-body>
						</fo:table>
					</fo:block>
					-->
	
					<fo:block space-before="0.2cm">
						<fo:table>
							<fo:table-column column-width="1cm"/>
							<fo:table-column/>
							<fo:table-body>
								<fo:table-row>
									<fo:table-cell display-align="top">
										<fo:external-graphic src="url('{@caminhoImagem}')"/>
									</fo:table-cell>
									<fo:table-cell display-align="top">
										<fo:block space-before="0.2cm" xsl:use-attribute-sets="texto">
											<fo:inline xsl:use-attribute-sets="tituloComplemento"><xsl:value-of select="@labelParecer"/> <xsl:value-of select="@dataUltParecer"/>:</fo:inline>
										</fo:block>
									</fo:table-cell>
								</fo:table-row>
							</fo:table-body>
						</fo:table>
					</fo:block>
					
					<fo:block space-before="0.2cm" white-space-collapse="false" xsl:use-attribute-sets="texto">
					 	<xsl:value-of select="@descricao"/>
					 </fo:block>
	
					<!-- fo:block space-before="0.2cm" space-after="3cm" xsl:use-attribute-sets="texto">
						<fo:inline xsl:use-attribute-sets="textoNegrito">ANÁLISE CONSELHO REVISOR:</fo:inline>
					</fo:block -->
					
					<xsl:if test = "@observacoes != ''">
						<fo:block space-before="0.2cm" xsl:use-attribute-sets="tituloComplemento">
							Observações:
						</fo:block>
						<fo:block white-space-collapse="false" space-before="0.2cm" xsl:use-attribute-sets="texto" text-align="left">
							<xsl:value-of select="@observacoes"/>
						</fo:block>
					</xsl:if>
					
					<xsl:if test="../@exibirEncaminhamentos != 'N' and ../@labelEncaminhamentos != ''">
						<fo:block space-before="0.5cm" space-after="3cm" xsl:use-attribute-sets="tituloComplemento">
							<xsl:value-of select="../@labelEncaminhamentos"/>:
						</fo:block>
					</xsl:if>
				</fo:table-cell>
			</fo:table-row>
		</xsl:if>
		<xsl:if test="../@ehPPA = 'N'">
			<fo:table-row>
				<!--
				Se descomentar, não esquecer de tirar o number-columns-spanned="2" da coluna abaixo.
				  fo:table-cell display-align="center">
					<fo:block>
						<fo:external-graphic src="url('{@caminhoImagem}')"/>
					</fo:block>
				</fo:table-cell-->
				<fo:table-cell number-columns-spanned="2">
					<xsl:if test="../@valorDR1 != ''">
						<fo:block space-before="0.2cm" xsl:use-attribute-sets="texto" text-align="left" white-space-collapse="false">
							<fo:inline xsl:use-attribute-sets="tituloComplemento"><xsl:value-of select="../@labelDR1"/></fo:inline>: <xsl:value-of select="../@valorDR1"/>
						</fo:block>
					</xsl:if>
					<!-- 
					<fo:block space-before="0.2cm" xsl:use-attribute-sets="texto" text-align="justify">
						<fo:inline xsl:use-attribute-sets="tituloComplemento"><xsl:value-of select="../@labelBeneficiosIett"/></fo:inline>: <xsl:value-of select="../@valorBeneficiosIett"/>
					</fo:block>
					-->
	
					<xsl:if test="../@valorDR4 != ''">
						<fo:block space-before="0.5cm" xsl:use-attribute-sets="texto" white-space-collapse="false">
							<fo:inline xsl:use-attribute-sets="tituloComplemento"><xsl:value-of select="../@labelDR4"/></fo:inline>: <xsl:value-of select="../@valorDR4"/>
						</fo:block>
					</xsl:if>
					
					<xsl:if test="@situacaoParecer != ''">
						<fo:block space-before="0.5cm" xsl:use-attribute-sets="texto" text-align="left">
							<fo:inline xsl:use-attribute-sets="tituloComplemento"><xsl:value-of select="@labelSituacaoParecer"/></fo:inline>: <xsl:value-of select="@situacaoParecer"/>
						</fo:block>
					</xsl:if>
					<!-- 
					<fo:block space-before="0.2cm" xsl:use-attribute-sets="texto">
						<fo:inline xsl:use-attribute-sets="textoNegrito"><xsl:value-of select="@labelParecer"/> <xsl:value-of select="@dataUltParecer"/>:</fo:inline>
					</fo:block>
					
					<fo:block space-before="0.2cm">
						<fo:table>
							<fo:table-column column-width="1cm"/>
							<fo:table-column/>
							<fo:table-body>
								<fo:table-row>
									<fo:table-cell display-align="top">
										<fo:external-graphic src="url('{@caminhoImagem}')"/>
									</fo:table-cell>
									<fo:table-cell display-align="top">
										<fo:block white-space-collapse="false" xsl:use-attribute-sets="texto">
										 	<xsl:value-of select="@descricao"/>
										 </fo:block>
									</fo:table-cell>
								</fo:table-row>
							</fo:table-body>
						</fo:table>
					</fo:block>
					-->
	
					<fo:block space-before="0.2cm">
						<fo:table>
							<fo:table-column column-width="1cm"/>
							<fo:table-column/>
							<fo:table-body>
								<fo:table-row>
									<fo:table-cell display-align="top">
										<fo:external-graphic src="url('{@caminhoImagem}')"/>
									</fo:table-cell>
									<fo:table-cell display-align="top">
										<fo:block space-before="0.2cm" xsl:use-attribute-sets="texto">
											<fo:inline xsl:use-attribute-sets="tituloComplemento"><xsl:value-of select="@labelParecer"/> <xsl:value-of select="@dataUltParecer"/>:</fo:inline>
										</fo:block>
									</fo:table-cell>
								</fo:table-row>
							</fo:table-body>
						</fo:table>
					</fo:block>
					
					<fo:block space-before="0.2cm" white-space-collapse="false" xsl:use-attribute-sets="texto">
					 	<xsl:value-of select="@descricao"/>
					 </fo:block>
	
					<!-- fo:block space-before="0.2cm" space-after="3cm" xsl:use-attribute-sets="texto">
						<fo:inline xsl:use-attribute-sets="textoNegrito">ANÁLISE CONSELHO REVISOR:</fo:inline>
					</fo:block -->
	
					<xsl:if test="@observacoes != ''">
						<fo:block space-before="0.2cm" xsl:use-attribute-sets="tituloComplemento">
							Observações:
						</fo:block>
						<fo:block white-space-collapse="false" space-before="0.2cm" xsl:use-attribute-sets="texto" text-align="left">
							<xsl:value-of select="@observacoes"/>
						</fo:block>
					</xsl:if>
					
					<xsl:if test="../@exibirEncaminhamentos != 'N' and ../@labelEncaminhamentos != ''">
						<fo:block space-before="0.5cm" space-after="3cm" xsl:use-attribute-sets="tituloComplemento">
							<xsl:value-of select="../@labelEncaminhamentos"/>:
						</fo:block>
					</xsl:if>
				</fo:table-cell>
				<fo:table-cell display-align="center"/>
				<fo:table-cell>
					<!-- fo:block white-space-collapse="false" space-before="0.2cm" xsl:use-attribute-sets="texto">
						<xsl:value-of select="@observacoes"/>
					</fo:block -->
					<!-- fo:block space-before="0.2cm" xsl:use-attribute-sets="texto">
						<fo:inline xsl:use-attribute-sets="textoNegrito"><xsl:value-of select="../@labelData"/></fo:inline>: <xsl:value-of select="../@dataInicio"/>
					</fo:block -->
	
					<xsl:apply-templates select="../indicadores"/>
	
					<xsl:if test="../@valorOrigemIett != ''">
						<fo:block space-before="0.5cm" xsl:use-attribute-sets="tituloComplemento" text-align="left">
							<xsl:value-of select="../@labelOrigemIett"/>
						</fo:block>
		
						<fo:block xsl:use-attribute-sets="texto" text-align="left" white-space-collapse="false">
							<xsl:value-of select="../@valorOrigemIett"/>
						</fo:block>
					</xsl:if>
	
					<!-- xsl:apply-templates select="../etapas"/ -->
	
					<xsl:apply-templates select="../ocorrencias"/>
					
				</fo:table-cell>
			</fo:table-row>
		</xsl:if>
	</xsl:template>

	<xsl:template match="semItens">
		<fo:block space-before="1cm">
			<fo:table table-layout="fixed">
				<fo:table-column/>
				<fo:table-body>
					<fo:table-row>
						<fo:table-cell>
							<xsl:if test="@exibirOrgao = 'S'">
										<fo:block xsl:use-attribute-sets="orgao">
											<xsl:value-of select="@orgao"/>
										</fo:block>
										<fo:block xsl:use-attribute-sets="textoMenorNegrito" space-before="1cm">
											Nenhum item foi acompanhado.
										</fo:block>
							</xsl:if>							
						</fo:table-cell>
					</fo:table-row>
				</fo:table-body>
			</fo:table>
		</fo:block>
	</xsl:template>

	<xsl:template match="evolucaoFinanceira">			
		<xsl:if test="@nenhumValor != 'S'">
			<fo:table>
				<fo:table-column/>
				<fo:table-column/>
				<fo:table-column column-width="1cm"/>
				<fo:table-body>
					<fo:table-row>
						<fo:table-cell>
							<fo:block space-before="0.5cm" xsl:use-attribute-sets="tituloComplemento" text-align="left">
								Recursos Orçamentário-Financeiros
							</fo:block>
						</fo:table-cell>
						<fo:table-cell>
							<fo:block space-before="0.5cm" xsl:use-attribute-sets="valorTabela" text-align="right">
								valores em R$ 1,00
							</fo:block>
						</fo:table-cell>
						<fo:table-cell/>
					</fo:table-row>
				</fo:table-body>
			</fo:table>
		
		<!-- 
		
		Ref Mantis 10304 (item b: Não emitir o cabeçalho e nem mensagem caso o Quadro de Recursos Orçamentário-
   Financeiros noão contiver valores.)
   
		<xsl:if test="@nenhumValor = 'S'">
			<fo:block xsl:use-attribute-sets="textoMenorNegrito">
				Nenhum valor foi informado.
			</fo:block>
		</xsl:if>
		 -->
			<fo:table table-layout="fixed">
				<fo:table-column column-width="2.7cm"/>
				<fo:table-column column-width="2.7cm"/>
				
				<xsl:apply-templates select="colunasHeader"/>
				
				<fo:table-header>
					<fo:table-row background-color="lightgray">
						<!-- 
						<fo:table-cell display-align="center" number-columns-spanned="2">
							<fo:block text-align="center" xsl:use-attribute-sets="textoMenorNegrito">
								Categoria Econômica
							</fo:block>
						</fo:table-cell>
						<fo:table-cell display-align="center">
							<fo:block text-align="center" xsl:use-attribute-sets="textoMenorNegrito">
								Fonte
							</fo:block>
						</fo:table-cell>
						-->
						<!--  
						<fo:table-cell display-align="center" number-columns-spanned="2">
							<fo:block text-align="center" xsl:use-attribute-sets="textoMenorNegrito">
								Valores Previstos
							</fo:block>
						</fo:table-cell>
						<fo:table-cell display-align="center">
							<fo:block text-align="center" xsl:use-attribute-sets="textoMenorNegrito">
							</fo:block>
						</fo:table-cell>
						<xsl:if test="@colunasRealizadas != '0'">
							<fo:table-cell display-align="center" number-columns-spanned="{@colunasRealizadas - 1}">
								<fo:block text-align="center" xsl:use-attribute-sets="textoMenorNegrito">
									Valores Realizados
								</fo:block>
							</fo:table-cell>
							<fo:table-cell display-align="center">
								<fo:block text-align="center" xsl:use-attribute-sets="textoMenorNegrito">
								</fo:block>
							</fo:table-cell>
						</xsl:if>
						 -->
						 
						<fo:table-cell display-align="center" number-columns-spanned="2" />
						<fo:table-cell display-align="center" />
						<xsl:if test="@colunasRealizadas != '0'">
							<fo:table-cell display-align="center" number-columns-spanned="{@colunasRealizadas - 1}" />
							<fo:table-cell display-align="center"/>
						</xsl:if>
						 
					</fo:table-row>
					<fo:table-row background-color="lightgray">
						<xsl:apply-templates select="colunas"/>
					</fo:table-row>
				</fo:table-header>
				<fo:table-body>
					<xsl:apply-templates select="fonte"/>		
					<xsl:apply-templates select="totalEvolucaoFinanceira"/>
				</fo:table-body>
			</fo:table>	
		</xsl:if>
		<fo:block space-before="0.5cm"/>
	</xsl:template>
	
	<xsl:template match="colunasHeader">
		<xsl:apply-templates select="colunaHeader"/>
	</xsl:template>

	<xsl:template match="colunaHeader">
		<fo:table-column column-width="{@tamanho}"/>
	</xsl:template>

	<xsl:template match="colunas">			
		<xsl:apply-templates select="coluna"/>
	</xsl:template>

	<xsl:template match="coluna">			
		<fo:table-cell display-align="center">
			<fo:block text-align="center" xsl:use-attribute-sets="textoMenorNegrito">
				<xsl:value-of select="@nome"/>
			</fo:block>
		</fo:table-cell>
	</xsl:template>

	<xsl:template match="fonte">			
		<fo:table-row>
	
			<fo:table-cell display-align="center" number-columns-spanned="{../@colunasRealizadas + 4}">
				<fo:block text-align="left" xsl:use-attribute-sets="textoMenorNegrito"  background-color="#EEE9E9">
					<xsl:value-of select="@exercicio"/>
				</fo:block>
			</fo:table-cell>

		</fo:table-row>

		<xsl:apply-templates select="recurso"/>		

		<xsl:apply-templates select="totalEvolucaoFinanceiraExercicio"/>		
	</xsl:template>
	
	<xsl:template match="recurso">			
		<fo:table-row>
	
			<xsl:if test="position() = '1'">
				<fo:table-cell display-align="center">
					<fo:block text-align="left" xsl:use-attribute-sets="textoMenorValorEvolucao">
						<xsl:value-of select="../@nome"/>
					</fo:block>
				</fo:table-cell>
			</xsl:if>
			<xsl:if test="position() != '1'">
				<fo:table-cell display-align="center">
					<fo:block text-align="left" xsl:use-attribute-sets="textoMenorValorEvolucao">
					</fo:block>
				</fo:table-cell>
			</xsl:if>
	
			<fo:table-cell display-align="center">
				<fo:block text-align="left" xsl:use-attribute-sets="textoMenorValorEvolucao">
					<xsl:value-of select="@nome"/>
				</fo:block>
			</fo:table-cell>
			
			<xsl:apply-templates select="rec"/>
		</fo:table-row>
	</xsl:template>
	
	<xsl:template match="rec">
		<fo:table-cell display-align="center">
			<!-- 
			<fo:block>
				<fo:table>
					<fo:table-column column-width="0.5cm"/>
					<fo:table-column/>
					<fo:table-column column-width="0.2cm"/>
					<fo:table-body>
						<fo:table-row>
							<fo:table-cell>
								<fo:block text-align="left" xsl:use-attribute-sets="textoMenorValorEvolucao">
									R$
								</fo:block>
							</fo:table-cell>
							<fo:table-cell>
								<fo:block text-align="right" xsl:use-attribute-sets="textoMenorValorEvolucao">
									<xsl:value-of select="@valor"/>
								</fo:block>
							</fo:table-cell>
							<fo:table-cell/>
						</fo:table-row>
					</fo:table-body>
				</fo:table>
			</fo:block>
			-->
			<fo:block text-align="right" xsl:use-attribute-sets="textoMenorValorEvolucao">
				<xsl:value-of select="@valor"/>
			</fo:block>
		</fo:table-cell>
	</xsl:template>
	
	<xsl:template match="totalEvolucaoFinanceira">			
		<fo:table-row background-color="lightgray">
			<fo:table-cell display-align="center">
				<fo:block text-align="left" xsl:use-attribute-sets="textoMenorNegrito">
					Total Geral
				</fo:block>
			</fo:table-cell>
			<fo:table-cell display-align="center">
				<fo:block text-align="center" xsl:use-attribute-sets="textoMenorNegrito">
				</fo:block>
			</fo:table-cell>
			
			<xsl:apply-templates select="total"/>
		</fo:table-row>
	</xsl:template>

	<xsl:template match="totalEvolucaoFinanceiraExercicio">			
		<fo:table-row background-color="lightgray">
			<fo:table-cell display-align="center">
				<fo:block text-align="left" xsl:use-attribute-sets="textoMenorNegrito">
					Total <xsl:value-of select="@exercicio"/>
				</fo:block>
			</fo:table-cell>
			<fo:table-cell display-align="center">
				<fo:block text-align="center" xsl:use-attribute-sets="textoMenorNegrito">
				</fo:block>
			</fo:table-cell>
			
			<xsl:apply-templates select="total"/>
		</fo:table-row>
	</xsl:template>

	<xsl:template match="total">
		<fo:table-cell display-align="center">
			<!-- 
			<fo:block>
				<fo:table>
					<fo:table-column column-width="0.5cm"/>
					<fo:table-column/>
					<fo:table-column column-width="0.2cm"/>
					<fo:table-body>
						<fo:table-row>
							<fo:table-cell>
								<fo:block text-align="left" xsl:use-attribute-sets="textoMenorValorEvolucao">
									R$
								</fo:block>
							</fo:table-cell>
							<fo:table-cell>
								<fo:block text-align="right" xsl:use-attribute-sets="textoMenorValorEvolucao">
									<xsl:value-of select="@valor"/>
								</fo:block>
							</fo:table-cell>
							<fo:table-cell/>
						</fo:table-row>
					</fo:table-body>
				</fo:table>
			</fo:block>
			-->
			<fo:block text-align="right" xsl:use-attribute-sets="textoMenorValorEvolucao">
				<xsl:value-of select="@valor"/>
			</fo:block>
		</fo:table-cell>
	</xsl:template>
	
	
	<xsl:template match="indicadores">
		
		<xsl:if test="@filho='S'"> 
			<fo:block space-before="0.5cm" xsl:use-attribute-sets="tituloComplementoFilho" text-align="left">
				<xsl:value-of select="@labelFuncao"/>
			</fo:block>
		</xsl:if>
		<xsl:if test="@filho='N'"> 
			<fo:block space-before="0.5cm" xsl:use-attribute-sets="tituloComplemento" text-align="left">
				<xsl:value-of select="@labelFuncao"/>
			</fo:block>
		</xsl:if>
		<fo:table table-layout="fixed">
			<!-- fo:table-column column-width="4.0cm"/ -->
			<fo:table-column column-width="1.7cm"/>
			<fo:table-column column-width="0.3cm"/> <!-- Coluna para dar distancia entre o nome do indicador e o label Realizado no mês -->
			
			<!-- fo:table-column column-width="2.5cm"/ -->
			<fo:table-column column-width="1.0cm"/>
			<fo:table-column column-width="0.3cm"/>
			
			<xsl:apply-templates select="columnExercicio"/>
			
			<xsl:if test="@filho='N'">
				<fo:table-column column-width="1.7cm"/>
				<!-- fo:table-column column-width="3.0cm"/ -->
				<fo:table-column column-width="1.5cm"/>
				<!-- fo:table-column column-width="7.5cm"/ -->
				<fo:table-column column-width="1.5cm"/>
				<!-- fo:table-column column-width="1.0cm"/ -->
				<fo:table-column column-width="0.4cm"/>
			</xsl:if>
			
			<xsl:if test="@filho='S' and @mostrarProjecao = 'S'">
				<fo:table-column column-width="1.7cm"/>
				<!-- fo:table-column column-width="3.0cm"/ -->
				<fo:table-column column-width="1.5cm"/>
				
				<!-- fo:table-column column-width="7.5cm"/ -->
				<fo:table-column column-width="15cm"/>
				<!-- fo:table-column column-width="1.0cm"/ -->
				<fo:table-column/>
			</xsl:if>
			<xsl:if test="@filho='S' and @mostrarProjecao = 'N'">
				<fo:table-column column-width="1.7cm"/>
				<!-- fo:table-column column-width="3.0cm"/ -->
				<fo:table-column column-width="1.5cm"/>

				<!-- fo:table-column column-width="7.5cm"/ -->
				<fo:table-column column-width="1.5cm"/>
				<!-- fo:table-column column-width="1.0cm"/ -->
				<fo:table-column column-width="0.4cm"/>
			</xsl:if>
 			<fo:table-body>
				<xsl:apply-templates select="indicador"/>
			</fo:table-body>
		</fo:table>
		<xsl:if test="@mostrarProjecao = 'S' and @filho = 'N'">
			<xsl:apply-templates select="legendaIndicadores"/>
		</xsl:if>
	</xsl:template>

	<xsl:template match="legendaIndicadores">
		<fo:table>
			<fo:table-column column-width="10px"/>
			<fo:table-column/>
			<fo:table-body>
				<xsl:apply-templates select="legenda"/>
			</fo:table-body>
		</fo:table>
	</xsl:template>

	<xsl:template match="legenda">
		<fo:table-row>
			<fo:table-cell>
				<fo:block>
					<fo:external-graphic src="url('{@imagem}')" width="10px" height="10px"/>
				</fo:block>
			</fo:table-cell>
			<fo:table-cell display-align="center">
				<fo:block xsl:use-attribute-sets="textoLegenda">
					<xsl:value-of select="@descricao"/>
				</fo:block>
			</fo:table-cell>
		</fo:table-row>
	</xsl:template>

	<xsl:template match="columnExercicio">
		<xsl:if test = "@ano != ''">
			<fo:table-column column-width="1.5cm"/>
		</xsl:if>
	</xsl:template>
	
	<xsl:template match="indExercicio">
		<xsl:if test = "@exercicio != ''">
			<fo:table-cell display-align="before">
				<fo:block text-align="center" xsl:use-attribute-sets="textoMenorNegritoIndicador"><xsl:value-of select="@exercicio"/></fo:block>
			</fo:table-cell>
		</xsl:if>
	</xsl:template>

	<xsl:template match="indicador">
	
		<xsl:if test="@exibirGrupoIndicador = 'S'">
			<fo:table-row background-color="lightgray">
				<fo:table-cell display-align="before">
					<fo:block text-align="center" xsl:use-attribute-sets="textoMenorNegritoIndicador">
						<xsl:value-of select="@grupoIndicador"/>
					</fo:block>
				</fo:table-cell>

				<fo:table-cell/>
				
				<fo:table-cell display-align="before">
					<fo:block text-align="center" xsl:use-attribute-sets="textoMenorNegritoIndicador">Realizado no Mês</fo:block>
				</fo:table-cell>
				<fo:table-cell display-align="before"> </fo:table-cell>
				
				<xsl:apply-templates select="../indExercicio"/>

				<fo:table-cell display-align="before">
					<fo:block text-align="center" xsl:use-attribute-sets="textoMenorNegritoIndicador">Total</fo:block>
				</fo:table-cell>
				<fo:table-cell display-align="before">
					<fo:block text-align="center" xsl:use-attribute-sets="textoMenorNegritoIndicador">Realizado/ Previsto(%)</fo:block>
				</fo:table-cell>
				<fo:table-cell display-align="before">
					<xsl:if test="../@mostrarProjecao = 'S'">
						<fo:block text-align="left" xsl:use-attribute-sets="textoMenorNegritoIndicador">Projeção na Data Final</fo:block>
						<fo:block text-align="left" xsl:use-attribute-sets="textoMenorNegritoIndicador">Situação Prevista</fo:block>
					</xsl:if>
					<xsl:if test="../@mostrarProjecao = 'N'">
						<fo:block text-align="left" xsl:use-attribute-sets="textoMenorNegritoIndicador">Situação</fo:block>
					</xsl:if>
				</fo:table-cell>
				<fo:table-cell display-align="before">
					<xsl:if test="../@mostrarProjecao = 'S'">
						<fo:block text-align="center" xsl:use-attribute-sets="textoMenorNegritoIndicador">%</fo:block>
					</xsl:if>
				</fo:table-cell>
			</fo:table-row>
		</xsl:if>
		
		<fo:table-row>
			<!-- fo:table-cell display-align="before" number-rows-spanned="2" -->
			<fo:table-cell display-align="before">
				<fo:block text-align="left" xsl:use-attribute-sets="textoMenorIndicador">
					<xsl:value-of select="@nome"/>
				</fo:block>
			</fo:table-cell>
			
			<fo:table-cell/>
			
			<fo:table-cell display-align="before">
				<fo:block>
					<fo:table>
						<fo:table-column/>
						<fo:table-body>
							<fo:table-row>
								<fo:table-cell>
									<fo:block space-before="0.2cm"/>
								</fo:table-cell>
							</fo:table-row>
							<fo:table-row>
								<fo:table-cell>
									<fo:block text-align="center" xsl:use-attribute-sets="textoMenorIndicador" background-color="#EEE9E9">
										<xsl:value-of select="@realizadoNoMes"/>
									</fo:block>
								</fo:table-cell>
							</fo:table-row>
						</fo:table-body>
					</fo:table>
				</fo:block>
			</fo:table-cell>
			<fo:table-cell display-align="before">
				<fo:block>
					<fo:table>
						<fo:table-column/>
						<fo:table-body>
							<fo:table-row>
								<fo:table-cell>
									<fo:block text-align="center" xsl:use-attribute-sets="textoMenorNegritoIndicador">
										P
									</fo:block>
								</fo:table-cell>
							</fo:table-row>
							<fo:table-row>
								<fo:table-cell>
									<fo:block text-align="center" xsl:use-attribute-sets="textoMenorNegritoIndicador" background-color="#EEE9E9">
										R
									</fo:block>
								</fo:table-cell>
							</fo:table-row>
						</fo:table-body>
					</fo:table>
				</fo:block>
			</fo:table-cell>
			
			<xsl:apply-templates select="valorExercicio"/>
			
			<xsl:apply-templates select="valorTotal"/>
			
			<xsl:apply-templates select="valorProjecao"/>

		</fo:table-row>
		<xsl:if test = "@situacao != '' and @mostrarProjecao = 'S'">
		<fo:table-row>
			<fo:table-cell display-align="before">
				<fo:block text-align="right" xsl:use-attribute-sets="textoMenorNegritoIndicador">
					Situação:
				</fo:block>
			</fo:table-cell>
			<fo:table-cell display-align="before" number-columns-spanned="{@numeroExercicios + 5}">
				<fo:block text-align="left" xsl:use-attribute-sets="textoMenorIndicador">
					<xsl:value-of select="@situacao"/>
				</fo:block>
			</fo:table-cell>
		</fo:table-row>
		</xsl:if>
		<!-- 
		<fo:table-row>
			<fo:table-cell display-align="center" number-columns-spanned="{@numeroExercicios + 7}">
				<fo:block space-before="0.5cm" border-bottom-color="black" border-bottom-width="0.1mm" border-bottom-style="solid">
				</fo:block>
			</fo:table-cell>
		</fo:table-row>
		 -->		
	</xsl:template>
	
	<xsl:template match="valorExercicio">
		<fo:table-cell display-align="before">
			<fo:block>
				<fo:table>
					<fo:table-column/>
					<fo:table-body>
						<fo:table-row>
							<fo:table-cell display-align="before">
								<fo:block text-align="right" xsl:use-attribute-sets="textoMenorIndicador">
									<xsl:value-of select="@valorPrevisto"/>
								</fo:block>
							</fo:table-cell>
						</fo:table-row>
						<fo:table-row>
							<fo:table-cell display-align="before">
								<fo:block text-align="right" xsl:use-attribute-sets="textoMenorIndicador" background-color="#EEE9E9">
									<xsl:value-of select="@valorRealizado"/>
								</fo:block>
							</fo:table-cell>
						</fo:table-row>
					</fo:table-body>
				</fo:table>
			</fo:block>
		</fo:table-cell>
	</xsl:template>

	<xsl:template match="valorTotal">
		<fo:table-cell display-align="before">
			<fo:block>
				<fo:table>
					<fo:table-column/>
					<fo:table-body>
						<fo:table-row>
							<fo:table-cell display-align="before">
								<fo:block text-align="right" xsl:use-attribute-sets="textoMenorIndicador">
									<xsl:value-of select="@totalPrevisto"/>
								</fo:block>
							</fo:table-cell>
						</fo:table-row>
						<fo:table-row>
							<fo:table-cell display-align="before">
								<fo:block text-align="right" xsl:use-attribute-sets="textoMenorIndicador" background-color="#EEE9E9">
									<xsl:value-of select="@totalRealizado"/>
								</fo:block>
							</fo:table-cell>
						</fo:table-row>
					</fo:table-body>
				</fo:table>
			</fo:block>
		</fo:table-cell>

		<fo:table-cell display-align="before">
			<fo:block>
				<fo:table>
					<fo:table-column/>
					<fo:table-body>
						<fo:table-row>
							<fo:table-cell>
								<fo:block space-before="0.4cm"/>
							</fo:table-cell>
						</fo:table-row>
						<fo:table-row>
							<fo:table-cell>
								<fo:block text-align="center" xsl:use-attribute-sets="textoMenorNegritoIndicador">
									<xsl:value-of select="@percentualRealizadoPrevisto"/>
								</fo:block>
							</fo:table-cell>
						</fo:table-row>
					</fo:table-body>
				</fo:table>
			</fo:block>
		</fo:table-cell>
	</xsl:template>
	
	<xsl:template match="valorProjecao">
		<fo:table-cell display-align="before">
			<xsl:if test="@mostrarProjecao = 'S' and ../../@filho = 'N'">
				<fo:block text-align="center" xsl:use-attribute-sets="textoMenorIndicador">
					<fo:external-graphic src="url('{@imagemProjecao}')" height="10px" width="10px"/>
				</fo:block>
			</xsl:if>
			<xsl:if test="@mostrarProjecao = 'S' and ../../@filho = 'S'">
				<fo:block text-align="left" xsl:use-attribute-sets="textoMenorIndicador">
					<xsl:value-of select="@projecao"/>
				</fo:block>
			</xsl:if>
			<xsl:if test="@mostrarProjecao = 'N'">
				<fo:block text-align="left" xsl:use-attribute-sets="textoMenorIndicador">
					<xsl:value-of select="@situacao"/>
				</fo:block>
			</xsl:if>
		</fo:table-cell>
		<fo:table-cell display-align="before">
			<xsl:if test="@mostrarProjecao = 'S'">
				<fo:block text-align="center" xsl:use-attribute-sets="textoMenorIndicador">
					<xsl:value-of select="@percentual"/>
				</fo:block>
			</xsl:if>
		</fo:table-cell>
	</xsl:template>

	<xsl:template match="itensFilhos">
		<xsl:apply-templates select="itemFilho"/>
	</xsl:template>
	
	<xsl:template match="itemFilho">
		<fo:table>
			<fo:table-column/>
			<fo:table-column column-width="2.cm"/>
			<fo:table-body>
				<fo:table-row>
					<fo:table-cell number-columns-spanned="2">
						<fo:block space-before="0.5cm" xsl:use-attribute-sets="nomeItemFilho" text-align="center">
							<xsl:value-of select="@estruturaItem"/>
						</fo:block>
					</fo:table-cell>
				</fo:table-row>
				<fo:table-row>
					<fo:table-cell number-columns-spanned="2">
						<fo:block space-before="0.5cm" border-bottom-color="black" border-bottom-width="0.1mm" border-bottom-style="solid">
						</fo:block>
					</fo:table-cell>
				</fo:table-row>
				<fo:table-row>
					<fo:table-cell>
						<fo:block xsl:use-attribute-sets="nomeItemFilho">
							<xsl:value-of select="@nomeItem"/>
						</fo:block>
					</fo:table-cell>
					<fo:table-cell>
						<!-- 
						Retirado do relatório de acordo com mantis 8549.
						<fo:block xsl:use-attribute-sets="nomeItem" text-align="right">
							<xsl:value-of select="@orgaoItem"/>
						</fo:block>
						 -->
					</fo:table-cell>
				</fo:table-row>
			</fo:table-body>
		</fo:table>
		<xsl:apply-templates select="indicadores"/>
	</xsl:template>
	
	<xsl:template match="ocorrencias">
		<fo:block space-before="0.3cm" xsl:use-attribute-sets="tituloComplemento" text-align="left">
			<xsl:value-of select="@labelFuncao"/>
		</fo:block>
		<fo:table>
			<fo:table-column column-width="2cm"/>
			<fo:table-column/>
			<fo:table-body>
				<fo:table-row background-color="lightgray">
					<fo:table-cell display-align="before">
						<fo:block text-align="center" xsl:use-attribute-sets="textoMenorNegrito">Data</fo:block>
					</fo:table-cell>
					<fo:table-cell display-align="before">
						<fo:block text-align="center" xsl:use-attribute-sets="textoMenorNegrito">Descrição</fo:block>
					</fo:table-cell>
				</fo:table-row>
				
				<xsl:apply-templates select="ocorrencia"/>
				
			</fo:table-body>
		</fo:table>
	</xsl:template>

	<xsl:template match="ocorrencia">
		<fo:table-row>
			<fo:table-cell display-align="before">
				<fo:block text-align="center" xsl:use-attribute-sets="textoMenor"><xsl:value-of select="@data"/></fo:block>
			</fo:table-cell>
			<fo:table-cell display-align="before">
				<fo:block text-align="left" xsl:use-attribute-sets="textoMenor" white-space-collapse="false"><xsl:value-of select="@descricao"/></fo:block>
			</fo:table-cell>
		</fo:table-row>
	</xsl:template>
	
	<!-- 
	<xsl:template match="etapas">
		<xsl:if test="etapa">
			<fo:block space-before="0.5cm" xsl:use-attribute-sets="tituloComplemento" text-align="left">
				<xsl:value-of select="@labelFuncao"/>
			</fo:block>
			
			<xsl:apply-templates select="etapa"/>
		</xsl:if>
	</xsl:template>

	<xsl:template match="etapa">
		<fo:block space-before="0.3cm" xsl:use-attribute-sets="textoMenor" text-align="left">
			<xsl:value-of select="@numeroEtapa"/>: <xsl:value-of select="@nomeEtapa"/>
		</fo:block>
		
		<fo:table>
			<fo:table-column column-width="1cm"/>
			<fo:table-column/>
			<fo:table-body>
				<xsl:apply-templates select="dadosEtapa"/>
			</fo:table-body>
		</fo:table>
		
	</xsl:template>
	
	<xsl:template match="dadosEtapa">
		<fo:table-row>
			<fo:table-cell/>
			<fo:table-cell>
				<fo:block xsl:use-attribute-sets="textoMenor" text-align="left" white-space-collapse="false">
					<xsl:value-of select="@label"/>: <xsl:value-of select="@valor"/>
				</fo:block>
			</fo:table-cell>
		</fo:table-row>
	</xsl:template>
	
	-->
	
	<xsl:template match="etapas">
		<xsl:if test="etapa">
			<fo:block space-before="0.5cm" xsl:use-attribute-sets="tituloComplemento" text-align="left">
				<xsl:value-of select="@labelFuncao"/>
			</fo:block>
			
			<xsl:apply-templates select="etapa"/>
		</xsl:if>
	</xsl:template>

	<xsl:template match="etapa">
		<fo:block space-before="0.3cm" xsl:use-attribute-sets="tituloComplemento" text-align="left">
			<xsl:value-of select="@nomeEtapa"/>
		</fo:block>
		
		<fo:block>
			<fo:table>
				
				<xsl:apply-templates select="etapasColunaHeader"/>
				
				<fo:table-header>
					<!-- Cabeçalho da tabela -->
					<fo:table-row>
						<xsl:apply-templates select="etapasColuna" />
					</fo:table-row>
				</fo:table-header>
				
				<fo:table-body>
					
					<!-- Corpo da tabela -->
					<xsl:apply-templates select="itemEtapa" />
					
				</fo:table-body>
			</fo:table>		
		</fo:block>

		<!-- fo:block>
			<fo:table>
				
				<fo:table-column column-width="144mm" />
				
				<fo:table-body>
					<fo:table-row>
						<fo:table-cell background-color="blue">
							<fo:block>
								OI isso é um teste de coluna
							</fo:block>
						</fo:table-cell>
					</fo:table-row>
				</fo:table-body>
			</fo:table>		
		</fo:block -->
	</xsl:template>
	
	<xsl:template match="etapasColunaHeader">
		<fo:table-column column-width="{@largura}" />
	</xsl:template>
	
	<xsl:template match="etapasColuna">
		<fo:table-cell background-color="lightgray">
			<fo:block xsl:use-attribute-sets="textoNegrito" text-align="center">
				<xsl:value-of select="@label"/>
			</fo:block>
		</fo:table-cell>
	</xsl:template>

	<xsl:template match="itemEtapa">
		<fo:table-row background-color="{@corFundo}">
			<xsl:apply-templates select="etapasValor" />
		</fo:table-row>
	</xsl:template>

	<xsl:template match="etapasValor">
		<fo:table-cell>
			<fo:block xsl:use-attribute-sets="texto">
				<xsl:value-of select="@valor"/>
			</fo:block>
		</fo:table-cell>
	</xsl:template>

</xsl:stylesheet>