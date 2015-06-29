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
		<xsl:attribute name="font-size">12.0pt</xsl:attribute>
		<xsl:attribute name="font-family">sans-serif</xsl:attribute>
		<xsl:attribute name="font-weight">bold</xsl:attribute>		
		<xsl:attribute name="color">darkblue</xsl:attribute>
	</xsl:attribute-set>	

	<xsl:attribute-set name="nomeItem">
		<xsl:attribute name="font-size">12.0pt</xsl:attribute>
		<xsl:attribute name="font-family">sans-serif</xsl:attribute>
		<xsl:attribute name="font-weight">bold</xsl:attribute>		
		<xsl:attribute name="color">darkblue</xsl:attribute>
	</xsl:attribute-set>	

	<xsl:attribute-set name="texto">
		<xsl:attribute name="font-size">11.0pt</xsl:attribute>
		<xsl:attribute name="font-family">sans-serif</xsl:attribute>
		<xsl:attribute name="font-weight">normal</xsl:attribute>		
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
	
	<xsl:attribute-set name="itemArvore">
		<xsl:attribute name="font-size">13.0pt</xsl:attribute>
		<xsl:attribute name="font-family">sans-serif</xsl:attribute>
		<xsl:attribute name="font-weight">bold</xsl:attribute>		
		<xsl:attribute name="color">darkblue</xsl:attribute>
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
				<fo:simple-page-master master-name="A4_Paisagem" page-height="21cm" page-width="29.8cm"
					margin-top="0.5cm" margin-bottom="0.5cm" margin-left="1cm" margin-right="1cm">
					<fo:region-body margin-top="0.5cm" margin-bottom="1.5cm"/>
					<fo:region-before extent="2cm"/>
					<fo:region-after extent="1cm"/>
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
					<fo:block text-align="center" border-bottom-color="silver" border-bottom-style="solid" border-bottom-width="0.02mm"white-space-collapse="false">
						<fo:table table-layout="fixed">
							<fo:table-column column-width="22.90cm"/>
							<fo:table-column column-width="4.90cm"/>
							<fo:table-body>
								<fo:table-row>
									<fo:table-cell>
										<fo:block text-align="left" xsl:use-attribute-sets="cabecalho-titulo"white-space-collapse="false">
											<xsl:value-of select="@titulo"/>
										</fo:block>	
										<fo:block text-align="left" xsl:use-attribute-sets="cabecalho-subtitulo"white-space-collapse="false">
											Relatório de Acompanhamento - <xsl:value-of select="@mesReferencia"/>
										</fo:block>	
									</fo:table-cell>
									<fo:table-cell display-align="after">
										<fo:block text-align="right" xsl:use-attribute-sets="cabecalho-codigo"white-space-collapse="false">
											<xsl:value-of select="@codModelo"/>
										</fo:block>
									</fo:table-cell>								
								</fo:table-row>																											
							</fo:table-body>
						</fo:table>	
					</fo:block>
				</fo:static-content>

				<fo:static-content flow-name="xsl-region-after">
					<fo:block text-align="right"white-space-collapse="false">
						<fo:table table-layout="fixed">
							<fo:table-column column-width="25.0cm"/>
							<fo:table-column column-width="3.0cm"/>
							<fo:table-body>
								<fo:table-row>
									<fo:table-cell>
										<fo:block text-align="left" xsl:use-attribute-sets="rodape"white-space-collapse="false">
											<xsl:value-of select="@rodape"/>
										</fo:block>	
									</fo:table-cell>
									<fo:table-cell>
										<fo:block text-align="right" xsl:use-attribute-sets="rodape"white-space-collapse="false">
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
					<fo:block id="last-page"white-space-collapse="false"/>
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
			<fo:block space-before="1cm" break-before="page"white-space-collapse="false">
				<fo:table table-layout="fixed">
					<fo:table-column/>
					<fo:table-header>
						<fo:table-row>
							<fo:table-cell>
								<xsl:if test="@exibirOrgao = 'S'">
									<fo:block xsl:use-attribute-sets="orgao"white-space-collapse="false">
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
			<fo:block space-before="1cm"white-space-collapse="false">
				<fo:table table-layout="fixed">
					<fo:table-column/>
					<fo:table-header>
						<fo:table-row>
							<fo:table-cell>
								<xsl:if test="@exibirOrgao = 'S'">
									<fo:block xsl:use-attribute-sets="orgao"white-space-collapse="false">
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
			<fo:block break-before="page"white-space-collapse="false"/>
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
		<fo:block space-before="0.2cm"white-space-collapse="false"/>
	</xsl:template>
	
	<xsl:template match="itemHierarquia">
		<fo:table-row>
			<fo:table-cell>
				<fo:block xsl:use-attribute-sets="itemArvore"white-space-collapse="false">
					<xsl:value-of select="@sigla"/>
				</fo:block>
			</fo:table-cell>
			<fo:table-cell>
				<fo:block xsl:use-attribute-sets="itemArvore"white-space-collapse="false">
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
				<fo:block space-before="0.2cm"white-space-collapse="false">
				
					<xsl:apply-templates select="hierarquia"/>
				
					<fo:table>
						<fo:table-column column-width="1cm"/>
						<fo:table-column column-width="16cm"/>
						<fo:table-column column-width="0.5cm"/>
						<fo:table-column/>
						
						<fo:table-body>
						
							<xsl:if test="@nomeItem != ''">
								<fo:table-row>
									<fo:table-cell background-color="#CCC"  number-columns-spanned="2">
										<fo:block>
											<fo:table>
												<fo:table-column/>
												<fo:table-column column-width="2.cm"/>
												<fo:table-body>
													<fo:table-row>
														<fo:table-cell>
															<fo:block xsl:use-attribute-sets="nomeItem"white-space-collapse="false">
																<xsl:value-of select="@nomeItem"/>
															</fo:block>
														</fo:table-cell>
														<fo:table-cell>
															<fo:block xsl:use-attribute-sets="nomeItem" text-align="right"white-space-collapse="false">
																<xsl:value-of select="@orgaoItem"/>
															</fo:block>
														</fo:table-cell>
													</fo:table-row>
												</fo:table-body>
											</fo:table>
										</fo:block>
									</fo:table-cell>
									<fo:table-cell vertical-align="middle"/>
									<fo:table-cell background-color="#CCC" vertical-align="middle">
										<fo:block xsl:use-attribute-sets="labelObservacoes"white-space-collapse="false">
											Observações:
										</fo:block>
									</fo:table-cell>
								</fo:table-row>
							</xsl:if>
							
							<xsl:apply-templates select="parecer"/>
							
						</fo:table-body>
					</fo:table>	
					
					<xsl:apply-templates select="indicadores"/>
					<xsl:apply-templates select="evolucaoFinanceira"/>
					<xsl:apply-templates select="itensFilhos"/>
					
				</fo:block>
			</fo:table-cell>
		</fo:table-row>
	</xsl:template>
	
	<xsl:template match="parecer">
		<fo:table-row>
			<fo:table-cell vertical-align="middle">
				<fo:block>
					<fo:external-graphic src="url('{@caminhoImagem}')"/>
				</fo:block>
			</fo:table-cell>
			<fo:table-cell vertical-align="middle">
				<fo:block white-space-collapse="false" space-before="0.2cm" xsl:use-attribute-sets="texto"white-space-collapse="false">
					<xsl:value-of select="@descricao"/>
				</fo:block>
			</fo:table-cell>
			<fo:table-cell vertical-align="middle"/>
			<fo:table-cell vertical-align="middle">
				<fo:block white-space-collapse="false" space-before="0.2cm" xsl:use-attribute-sets="texto"white-space-collapse="false">
					<xsl:value-of select="@observacoes"/>
				</fo:block>
			</fo:table-cell>
		</fo:table-row>
	</xsl:template>

	<xsl:template match="semItens">
		<fo:block space-before="1cm"white-space-collapse="false">
			<fo:table table-layout="fixed">
				<fo:table-column/>
				<fo:table-body>
					<fo:table-row>
						<fo:table-cell>
							<fo:block xsl:use-attribute-sets="nomeItem"white-space-collapse="false">
								Nenhum item foi encontrado para os parâmetros deste relatório.
							</fo:block>
						</fo:table-cell>
					</fo:table-row>
				</fo:table-body>
			</fo:table>
		</fo:block>
	</xsl:template>

	<xsl:template match="evolucaoFinanceira">			
		<fo:table>
			<fo:table-column/>
			<fo:table-column/>
			<fo:table-body>
				<fo:table-row>
					<fo:table-cell>
						<fo:block space-before="0.3cm" xsl:use-attribute-sets="tituloComplemento" text-align="left"white-space-collapse="false">
							Evolução Financeira
						</fo:block>
					</fo:table-cell>
					<fo:table-cell>
						<fo:block space-before="0.3cm" xsl:use-attribute-sets="valorTabela" text-align="right"white-space-collapse="false">
							valores em R$ 1,00
						</fo:block>
					</fo:table-cell>
				</fo:table-row>
			</fo:table-body>
		</fo:table>
		<fo:table table-layout="fixed">
			<fo:table-column column-width="2.7cm"/>
			<fo:table-column column-width="2.7cm"/>
			
			<xsl:apply-templates select="colunasHeader"/>
			
			<fo:table-header>
				<fo:table-row background-color="lightgray">
					<fo:table-cell display-align="center">
						<fo:block text-align="center" xsl:use-attribute-sets="textoMenorNegrito"white-space-collapse="false">
							Fonte
						</fo:block>
					</fo:table-cell>
					<fo:table-cell display-align="center">
						<fo:block text-align="center" xsl:use-attribute-sets="textoMenorNegrito"white-space-collapse="false">
							Recurso
						</fo:block>
					</fo:table-cell>
					<fo:table-cell display-align="center" number-columns-spanned="2">
						<fo:block text-align="center" xsl:use-attribute-sets="textoMenorNegrito"white-space-collapse="false">
							Valores Previstos
						</fo:block>
					</fo:table-cell>
					<fo:table-cell display-align="center">
						<fo:block text-align="center" xsl:use-attribute-sets="textoMenorNegrito"white-space-collapse="false">
						</fo:block>
					</fo:table-cell>
					<xsl:if test="@colunasRealizadas != '0'">
						<fo:table-cell display-align="center" number-columns-spanned="{@colunasRealizadas - 1}">
							<fo:block text-align="center" xsl:use-attribute-sets="textoMenorNegrito"white-space-collapse="false">
								Valores Realizados
							</fo:block>
						</fo:table-cell>
						<fo:table-cell display-align="center">
							<fo:block text-align="center" xsl:use-attribute-sets="textoMenorNegrito"white-space-collapse="false">
							</fo:block>
						</fo:table-cell>
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
		<fo:block space-before="0.5cm"white-space-collapse="false"/>
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
			<fo:block text-align="center" xsl:use-attribute-sets="textoMenorNegrito"white-space-collapse="false">
				<xsl:value-of select="@nome"/>
			</fo:block>
		</fo:table-cell>
	</xsl:template>

	<xsl:template match="fonte">			
		<xsl:apply-templates select="recurso"/>		
	</xsl:template>
	
	<xsl:template match="recurso">			
		<fo:table-row>
	
			<xsl:if test="position() = '1'">
				<fo:table-cell display-align="center">
					<fo:block text-align="left" xsl:use-attribute-sets="textoMenorValorEvolucao"white-space-collapse="false">
						<xsl:value-of select="../@nome"/>
					</fo:block>
				</fo:table-cell>
			</xsl:if>
			<xsl:if test="position() != '1'">
				<fo:table-cell display-align="center">
					<fo:block text-align="left" xsl:use-attribute-sets="textoMenorValorEvolucao"white-space-collapse="false">
					</fo:block>
				</fo:table-cell>
			</xsl:if>
	
			<fo:table-cell display-align="center">
				<fo:block text-align="left" xsl:use-attribute-sets="textoMenorValorEvolucao"white-space-collapse="false">
					<xsl:value-of select="@nome"/>
				</fo:block>
			</fo:table-cell>
			
			<xsl:apply-templates select="rec"/>
		</fo:table-row>
	</xsl:template>
	
	<xsl:template match="rec">
		<fo:table-cell display-align="center">
			<fo:block>
				<fo:table>
					<fo:table-column column-width="0.5cm"/>
					<fo:table-column/>
					<fo:table-column column-width="0.2cm"/>
					<fo:table-body>
						<fo:table-row>
							<fo:table-cell>
								<fo:block text-align="left" xsl:use-attribute-sets="textoMenorValorEvolucao"white-space-collapse="false">
									R$
								</fo:block>
							</fo:table-cell>
							<fo:table-cell>
								<fo:block text-align="right" xsl:use-attribute-sets="textoMenorValorEvolucao"white-space-collapse="false">
									<xsl:value-of select="@valor"/>
								</fo:block>
							</fo:table-cell>
							<fo:table-cell/>
						</fo:table-row>
					</fo:table-body>
				</fo:table>
			</fo:block>
		</fo:table-cell>
	</xsl:template>
	
	<xsl:template match="totalEvolucaoFinanceira">			
		<fo:table-row background-color="lightgray">
			<fo:table-cell display-align="center">
				<fo:block text-align="left" xsl:use-attribute-sets="textoMenorNegrito"white-space-collapse="false">
					Total
				</fo:block>
			</fo:table-cell>
			<fo:table-cell display-align="center">
				<fo:block text-align="center" xsl:use-attribute-sets="textoMenorNegrito"white-space-collapse="false">
				</fo:block>
			</fo:table-cell>
			
			<xsl:apply-templates select="total"/>
		</fo:table-row>
	</xsl:template>

	<xsl:template match="total">
		<fo:table-cell display-align="center">
			<fo:block>
				<fo:table>
					<fo:table-column column-width="0.5cm"/>
					<fo:table-column/>
					<fo:table-column column-width="0.2cm"/>
					<fo:table-body>
						<fo:table-row>
							<fo:table-cell>
								<fo:block text-align="left" xsl:use-attribute-sets="textoMenorValorEvolucao"white-space-collapse="false">
									R$
								</fo:block>
							</fo:table-cell>
							<fo:table-cell>
								<fo:block text-align="right" xsl:use-attribute-sets="textoMenorValorEvolucao"white-space-collapse="false">
									<xsl:value-of select="@valor"/>
								</fo:block>
							</fo:table-cell>
							<fo:table-cell/>
						</fo:table-row>
					</fo:table-body>
				</fo:table>
			</fo:block>
		</fo:table-cell>
	</xsl:template>
	
	
	<xsl:template match="indicadores">
		<!--
		<fo:block space-before="0.3cm" xsl:use-attribute-sets="tituloComplemento" text-align="left"white-space-collapse="false">
			<xsl:value-of select="@labelFuncao"/>
		</fo:block>
		<fo:table table-layout="fixed">
			<fo:table-column column-width="6.0cm"/>
			<fo:table-column column-width="3.0cm"/>
			<fo:table-column column-width="0.3cm"/>
			<xsl:if test = "@ano1 != ''">
				<fo:table-column column-width="1.9cm"/>
			</xsl:if>
			<xsl:if test = "@ano2 != ''">
				<fo:table-column column-width="1.9cm"/>
			</xsl:if>
			<fo:table-column column-width="1.9cm"/>
			<fo:table-column column-width="3.0cm"/>
			<fo:table-column column-width="8.5cm"/>
			<fo:table-column column-width="1.0cm"/>
			<fo:table-header>
				<fo:table-row background-color="lightgray">
					<fo:table-cell display-align="before">
						<fo:block text-align="center" xsl:use-attribute-sets="textoMenorNegrito"white-space-collapse="false">Indicador</fo:block>
					</fo:table-cell>
					<fo:table-cell display-align="before">
						<fo:block text-align="center" xsl:use-attribute-sets="textoMenorNegrito"white-space-collapse="false">Realizado no Mês</fo:block>
					</fo:table-cell>
					<fo:table-cell display-align="before"> </fo:table-cell>
					<xsl:if test = "@ano1 != ''">
						<fo:table-cell display-align="before">
							<fo:block text-align="center" xsl:use-attribute-sets="textoMenorNegrito"white-space-collapse="false"><xsl:value-of select="@ano1"/></fo:block>
						</fo:table-cell>
					</xsl:if>
					<xsl:if test = "@ano2 != ''">
						<fo:table-cell display-align="before">
							<fo:block text-align="center" xsl:use-attribute-sets="textoMenorNegrito"white-space-collapse="false"><xsl:value-of select="@ano2"/></fo:block>
						</fo:table-cell>
					</xsl:if>
					<fo:table-cell display-align="before">
						<fo:block text-align="center" xsl:use-attribute-sets="textoMenorNegrito"white-space-collapse="false">Total</fo:block>
					</fo:table-cell>
					<fo:table-cell display-align="before">
						<fo:block text-align="center" xsl:use-attribute-sets="textoMenorNegrito"white-space-collapse="false">Realizado/ Previsto(%)</fo:block>
					</fo:table-cell>
					<fo:table-cell display-align="before">
						<xsl:if test="@mostrarProjecao = 'S'">
							<fo:block text-align="left" xsl:use-attribute-sets="textoMenorNegrito"white-space-collapse="false">Projeção na Data Final</fo:block>
							<fo:block text-align="left" xsl:use-attribute-sets="textoMenorNegrito">Situação Prevista</fo:block>
						</xsl:if>
					</fo:table-cell>
					<fo:table-cell display-align="before">
						<xsl:if test="@mostrarProjecao = 'S'">
							<fo:block text-align="center" xsl:use-attribute-sets="textoMenorNegrito"white-space-collapse="false">%</fo:block>
						</xsl:if>
					</fo:table-cell>
				</fo:table-row>
			</fo:table-header>
			<fo:table-body>
				<xsl:apply-templates select="indicador"/>		
			</fo:table-body>
		</fo:table>
		 -->
		 
		 
		<fo:block space-before="0.3cm" xsl:use-attribute-sets="tituloComplemento" text-align="left"white-space-collapse="false">
			<xsl:value-of select="@labelFuncao"/>
		</fo:block>
		<fo:table table-layout="fixed">
			<fo:table-column column-width="4.0cm"/>
			<fo:table-column column-width="2.5cm"/>
			<fo:table-column column-width="0.3cm"/>
			
			<xsl:apply-templates select="columnExercicio"/>
			
			<fo:table-column column-width="1.9cm"/>
			<fo:table-column column-width="3.0cm"/>
			<fo:table-column column-width="7.5cm"/>
			<fo:table-column column-width="1.0cm"/>

			<fo:table-header>
				<fo:table-row background-color="lightgray">
					<fo:table-cell display-align="before">
						<fo:block text-align="center" xsl:use-attribute-sets="textoMenorNegrito"white-space-collapse="false">Indicador</fo:block>
					</fo:table-cell>
					<fo:table-cell display-align="before">
						<fo:block text-align="center" xsl:use-attribute-sets="textoMenorNegrito"white-space-collapse="false">Realizado no Mês</fo:block>
					</fo:table-cell>
					<fo:table-cell display-align="before"> </fo:table-cell>
					
					<xsl:apply-templates select="indExercicio"/>

					<fo:table-cell display-align="before">
						<fo:block text-align="center" xsl:use-attribute-sets="textoMenorNegrito"white-space-collapse="false">Total</fo:block>
					</fo:table-cell>
					<fo:table-cell display-align="before">
						<fo:block text-align="center" xsl:use-attribute-sets="textoMenorNegrito"white-space-collapse="false">Realizado/ Previsto(%)</fo:block>
					</fo:table-cell>
					<fo:table-cell display-align="before">
						<xsl:if test="@mostrarProjecao = 'S'">
							<fo:block text-align="left" xsl:use-attribute-sets="textoMenorNegrito"white-space-collapse="false">Projeção na Data Final</fo:block>
							<fo:block text-align="left" xsl:use-attribute-sets="textoMenorNegrito"white-space-collapse="false">Situação Prevista</fo:block>
						</xsl:if>
						<xsl:if test="@mostrarProjecao = 'N'">
							<fo:block text-align="left" xsl:use-attribute-sets="textoMenorNegrito"white-space-collapse="false">Situação</fo:block>
						</xsl:if>
					</fo:table-cell>
					<fo:table-cell display-align="before">
						<xsl:if test="@mostrarProjecao = 'S'">
							<fo:block text-align="center" xsl:use-attribute-sets="textoMenorNegrito"white-space-collapse="false">%</fo:block>
						</xsl:if>
					</fo:table-cell>
				</fo:table-row>
			</fo:table-header>
			<fo:table-body>
				<xsl:apply-templates select="indicador"/>
			</fo:table-body>
		</fo:table>
	</xsl:template>
	
	<xsl:template match="columnExercicio">
		<xsl:if test = "@ano != ''">
			<fo:table-column column-width="1.9cm"/>
		</xsl:if>
	</xsl:template>
	
	<xsl:template match="indExercicio">
		<xsl:if test = "@exercicio != ''">
			<fo:table-cell display-align="before">
				<fo:block text-align="center" xsl:use-attribute-sets="textoMenorNegrito"white-space-collapse="false"><xsl:value-of select="@exercicio"/></fo:block>
			</fo:table-cell>
		</xsl:if>
	</xsl:template>

	<xsl:template match="indicador">
	
		<!-- 
		<fo:table-row>
			<fo:table-cell display-align="before" number-rows-spanned="2">
				<fo:block text-align="left" xsl:use-attribute-sets="textoMenor"white-space-collapse="false">
					<xsl:value-of select="@nome"/>
				</fo:block>
			</fo:table-cell>
			<fo:table-cell display-align="before">
				<fo:block text-align="center" xsl:use-attribute-sets="textoMenor"white-space-collapse="false">
				</fo:block>
			</fo:table-cell>
			<fo:table-cell display-align="before">
				<fo:block text-align="center" xsl:use-attribute-sets="textoMenorNegrito"white-space-collapse="false">
					P
				</fo:block>
			</fo:table-cell>
			<xsl:if test = "@previstoAno1 != ''">
				<fo:table-cell display-align="before">
					<fo:block text-align="right" xsl:use-attribute-sets="textoMenor"white-space-collapse="false">
						<xsl:value-of select="@previstoAno1"/>
					</fo:block>
				</fo:table-cell>
			</xsl:if>
			<xsl:if test = "@previstoAno2 != ''">
				<fo:table-cell display-align="before">
					<fo:block text-align="right" xsl:use-attribute-sets="textoMenor"white-space-collapse="false">
						<xsl:value-of select="@previstoAno2"/>
					</fo:block>
				</fo:table-cell>
			</xsl:if>
			<fo:table-cell display-align="before">
				<fo:block text-align="right" xsl:use-attribute-sets="textoMenor"white-space-collapse="false">
					<xsl:value-of select="@totalPrevisto"/>
				</fo:block>
			</fo:table-cell>
			<fo:table-cell display-align="before">
				<fo:block text-align="center" xsl:use-attribute-sets="textoMenor"white-space-collapse="false">
				</fo:block>
			</fo:table-cell>
			<fo:table-cell display-align="before" number-rows-spanned="4">
				<xsl:if test="@mostrarProjecao = 'S'">
					<fo:block text-align="left" xsl:use-attribute-sets="textoMenor"white-space-collapse="false">
						<xsl:value-of select="@projecao"/>
					</fo:block>
				</xsl:if>
			</fo:table-cell>
			<fo:table-cell display-align="before">
				<xsl:if test="@mostrarProjecao = 'S'">
					<fo:block text-align="center" xsl:use-attribute-sets="textoMenor"white-space-collapse="false">
						<xsl:value-of select="@percentual"/>
					</fo:block>
				</xsl:if>
			</fo:table-cell>
		</fo:table-row>
		
		<fo:table-row>
			<fo:table-cell display-align="before" background-color="#EEE9E9">
				<fo:block text-align="center" xsl:use-attribute-sets="textoMenor"white-space-collapse="false">
					<xsl:value-of select="@realizadoNoMes"/>
				</fo:block>
			</fo:table-cell>
			<fo:table-cell display-align="before" background-color="#EEE9E9">
				<fo:block text-align="center" xsl:use-attribute-sets="textoMenorNegrito"white-space-collapse="false">
					R
				</fo:block>
			</fo:table-cell>
			<xsl:if test = "@realizadoAno1 != ''">
				<fo:table-cell display-align="before" background-color="#EEE9E9">
					<fo:block text-align="right" xsl:use-attribute-sets="textoMenor"white-space-collapse="false">
						<xsl:value-of select="@realizadoAno1"/>
					</fo:block>
				</fo:table-cell>
			</xsl:if>
			<xsl:if test = "@realizadoAno2 != ''">
				<fo:table-cell display-align="before" background-color="#EEE9E9">
					<fo:block text-align="right" xsl:use-attribute-sets="textoMenor"white-space-collapse="false">
						<xsl:value-of select="@realizadoAno2"/>
					</fo:block>
				</fo:table-cell>
			</xsl:if>
			<fo:table-cell display-align="before" background-color="#EEE9E9">
				<fo:block text-align="right" xsl:use-attribute-sets="textoMenor"white-space-collapse="false">
					<xsl:value-of select="@totalRealizado"/>
				</fo:block>
			</fo:table-cell>
			<fo:table-cell display-align="before" background-color="#EEE9E9">
				<fo:block text-align="right" xsl:use-attribute-sets="textoMenorNegrito"white-space-collapse="false">
					<xsl:value-of select="@percentualRealizadoPrevisto"/>
				</fo:block>
			</fo:table-cell>
			<fo:table-cell display-align="before">
				<fo:block text-align="left" xsl:use-attribute-sets="textoMenor"white-space-collapse="false">
				</fo:block>
			</fo:table-cell>
			<fo:table-cell display-align="before">
				<fo:block text-align="center" xsl:use-attribute-sets="textoMenor"white-space-collapse="false">
				</fo:block>
			</fo:table-cell>
		</fo:table-row>
		
		<fo:table-row>
			<fo:table-cell display-align="before">
				<fo:block text-align="right" xsl:use-attribute-sets="textoMenorNegrito"white-space-collapse="false">
					Situação:
				</fo:block>
			</fo:table-cell>
			<fo:table-cell display-align="before" number-columns-spanned="6">
				<fo:block text-align="left" xsl:use-attribute-sets="textoMenor"white-space-collapse="false">
					<xsl:value-of select="@situacao"/>
				</fo:block>
			</fo:table-cell>
		</fo:table-row>

		<fo:table-row>
			<fo:table-cell display-align="center" number-columns-spanned="9">
				<fo:block space-before="0.5cm" border-bottom-color="black" border-bottom-width="0.1mm" border-bottom-style="solid"white-space-collapse="false">
				</fo:block>
			</fo:table-cell>
		</fo:table-row>
		-->
		
		
		<fo:table-row>
			<fo:table-cell display-align="before" number-rows-spanned="2">
				<fo:block text-align="left" xsl:use-attribute-sets="textoMenor"white-space-collapse="false">
					<xsl:value-of select="@nome"/>
				</fo:block>
			</fo:table-cell>
			<fo:table-cell display-align="before">
				<fo:block>
					<fo:table>
						<fo:table-column/>
						<fo:table-body>
							<fo:table-row>
								<fo:table-cell>
									<fo:block space-before="0.4cm"white-space-collapse="false"/>
								</fo:table-cell>
							</fo:table-row>
							<fo:table-row>
								<fo:table-cell>
									<fo:block text-align="center" xsl:use-attribute-sets="textoMenor" background-color="#EEE9E9"white-space-collapse="false">
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
									<fo:block text-align="center" xsl:use-attribute-sets="textoMenorNegrito"white-space-collapse="false">
										P
									</fo:block>
								</fo:table-cell>
							</fo:table-row>
							<fo:table-row>
								<fo:table-cell>
									<fo:block text-align="center" xsl:use-attribute-sets="textoMenorNegrito" background-color="#EEE9E9"white-space-collapse="false">
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
				<fo:block text-align="right" xsl:use-attribute-sets="textoMenorNegrito"white-space-collapse="false">
					Situação:
				</fo:block>
			</fo:table-cell>
			<fo:table-cell display-align="before" number-columns-spanned="{@numeroExercicios + 4}">
				<fo:block text-align="left" xsl:use-attribute-sets="textoMenor"white-space-collapse="false">
					<xsl:value-of select="@situacao"/>
				</fo:block>
			</fo:table-cell>
		</fo:table-row>
		</xsl:if>
		<!-- 
		<fo:table-row>
			<fo:table-cell display-align="center" number-columns-spanned="{@numeroExercicios + 7}">
				<fo:block space-before="0.5cm" border-bottom-color="black" border-bottom-width="0.1mm" border-bottom-style="solid"white-space-collapse="false">
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
								<fo:block text-align="right" xsl:use-attribute-sets="textoMenor"white-space-collapse="false">
									<xsl:value-of select="@valorPrevisto"/>
								</fo:block>
							</fo:table-cell>
						</fo:table-row>
						<fo:table-row>
							<fo:table-cell display-align="before">
								<fo:block text-align="right" xsl:use-attribute-sets="textoMenor" background-color="#EEE9E9"white-space-collapse="false">
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
								<fo:block text-align="right" xsl:use-attribute-sets="textoMenor"white-space-collapse="false">
									<xsl:value-of select="@totalPrevisto"/>
								</fo:block>
							</fo:table-cell>
						</fo:table-row>
						<fo:table-row>
							<fo:table-cell display-align="before">
								<fo:block text-align="right" xsl:use-attribute-sets="textoMenor" background-color="#EEE9E9"white-space-collapse="false">
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
								<fo:block space-before="0.4cm"white-space-collapse="false"/>
							</fo:table-cell>
						</fo:table-row>
						<fo:table-row>
							<fo:table-cell>
								<fo:block text-align="center" xsl:use-attribute-sets="textoMenorNegrito"white-space-collapse="false">
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
			<xsl:if test="@mostrarProjecao = 'S'">
				<fo:block text-align="left" xsl:use-attribute-sets="textoMenor"white-space-collapse="false">
					<xsl:value-of select="@projecao"/>
				</fo:block>
			</xsl:if>
			<xsl:if test="@mostrarProjecao = 'N'">
				<fo:block text-align="left" xsl:use-attribute-sets="textoMenor"white-space-collapse="false">
					<xsl:value-of select="@situacao"/>
				</fo:block>
			</xsl:if>
		</fo:table-cell>
		<fo:table-cell display-align="before">
			<xsl:if test="@mostrarProjecao = 'S'">
				<fo:block text-align="center" xsl:use-attribute-sets="textoMenor"white-space-collapse="false">
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
						<fo:block space-before="0.5cm" border-bottom-color="black" border-bottom-width="0.1mm" border-bottom-style="solid"white-space-collapse="false">
						</fo:block>
					</fo:table-cell>
				</fo:table-row>
				<fo:table-row>
					<fo:table-cell>
						<fo:block xsl:use-attribute-sets="nomeItem"white-space-collapse="false">
							<xsl:value-of select="@nomeItem"/>
						</fo:block>
					</fo:table-cell>
					<fo:table-cell>
						<!-- 
						Retirado do relatório de acordo com mantis 8549.
						<fo:block xsl:use-attribute-sets="nomeItem" text-align="right"white-space-collapse="false">
							<xsl:value-of select="@orgaoItem"/>
						</fo:block>
						 -->
					</fo:table-cell>
				</fo:table-row>
			</fo:table-body>
		</fo:table>
		<xsl:apply-templates select="indicadores"/>
	</xsl:template>
</xsl:stylesheet>
