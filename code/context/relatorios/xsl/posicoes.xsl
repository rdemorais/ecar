<?xml version="1.0" encoding="ISO-8859-1"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" 
	xmlns:fo="http://www.w3.org/1999/XSL/Format" version="1.0">
	
	<xsl:output method="xml" version="1.0" encoding="ISO-8859-1"/>
	
	<xsl:attribute-set name="label">
		<xsl:attribute name="font-size">10.0pt</xsl:attribute>
		<xsl:attribute name="font-family">sans-serif</xsl:attribute>
		<xsl:attribute name="font-weight">normal</xsl:attribute>		
	</xsl:attribute-set>	

	<xsl:attribute-set name="textoDescFR">
		<xsl:attribute name="font-size">9.0pt</xsl:attribute>
		<xsl:attribute name="font-family">sans-serif</xsl:attribute>
		<xsl:attribute name="font-weight">normal</xsl:attribute>		
	</xsl:attribute-set>	

	<xsl:attribute-set name="texto">
		<xsl:attribute name="font-size">10.0pt</xsl:attribute>
		<xsl:attribute name="font-family">sans-serif</xsl:attribute>
		<xsl:attribute name="font-weight">bold</xsl:attribute>		
	</xsl:attribute-set>	

	<xsl:attribute-set name="textoDesc">
		<xsl:attribute name="font-size">10.0pt</xsl:attribute>
		<xsl:attribute name="font-family">sans-serif</xsl:attribute>
		<xsl:attribute name="font-weight">normal</xsl:attribute>		
	</xsl:attribute-set>	

	<xsl:attribute-set name="titulo">
		<xsl:attribute name="font-size">11.0pt</xsl:attribute>
		<xsl:attribute name="font-family">sans-serif</xsl:attribute>
		<xsl:attribute name="font-weight">bold</xsl:attribute>		
	</xsl:attribute-set>	

	<xsl:attribute-set name="subtitulo">
		<xsl:attribute name="font-size">10.0pt</xsl:attribute>
		<xsl:attribute name="font-family">sans-serif</xsl:attribute>
		<xsl:attribute name="font-weight">bold</xsl:attribute>		
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

	<xsl:attribute-set name="valorTabela">
		<xsl:attribute name="font-size">8.0pt</xsl:attribute>
		<xsl:attribute name="font-family">sans-serif</xsl:attribute>
		<xsl:attribute name="font-weight">bold</xsl:attribute>		
	</xsl:attribute-set>	

	<xsl:attribute-set name="nomeItem">
		<xsl:attribute name="font-size">10.0pt</xsl:attribute>
		<xsl:attribute name="font-family">sans-serif</xsl:attribute>
		<xsl:attribute name="font-weight">bold</xsl:attribute>
		<xsl:attribute name="text-decoration">underline</xsl:attribute>
	</xsl:attribute-set>	

	<xsl:attribute-set name="textoMenorValorEvolucao">
		<xsl:attribute name="font-size">8.0pt</xsl:attribute>
		<xsl:attribute name="font-family">sans-serif</xsl:attribute>
		<xsl:attribute name="font-weight">normal</xsl:attribute>		
	</xsl:attribute-set>	
	
	<xsl:attribute-set name="orgao">
		<xsl:attribute name="font-size">12.0pt</xsl:attribute>
		<xsl:attribute name="font-family">sans-serif</xsl:attribute>
		<xsl:attribute name="font-weight">bold</xsl:attribute>		
		<xsl:attribute name="color">darkblue</xsl:attribute>
	</xsl:attribute-set>
	
<!-- Igor -->
<xsl:template match="processing-instruction('hard-pagebreak')">
   <fo:block break-before='page'/>
 </xsl:template>
<!-- \Igor -->

	<xsl:template match="relatorio">
		<fo:root>
			<fo:layout-master-set>
				<fo:simple-page-master master-name="A4" page-height="29.8cm" page-width="21cm"
					margin-top="0.8cm" margin-bottom="0.3cm" margin-left="0.3cm" margin-right="0.3cm">
					<fo:region-body margin-top="1cm" margin-bottom="0.5cm"/>
					<fo:region-before extent="2cm"/>
					<fo:region-after extent="0.5cm"/>
				</fo:simple-page-master>
			</fo:layout-master-set>
			<fo:page-sequence master-reference="A4" format="1">				

				<fo:static-content flow-name="xsl-region-before">
					<fo:block border-bottom-color="black" border-bottom-width="0.2mm" border-bottom-style="solid">
						<fo:table table-layout="fixed">
							<fo:table-column column-width="9.5cm"/>
							<fo:table-column column-width="9.5cm"/>
							<fo:table-body>
								<fo:table-row>
									<fo:table-cell>
										<fo:block xsl:use-attribute-sets="texto" text-align="start">
											Relatório dos Acompanhamentos
										</fo:block>
									</fo:table-cell>
									<fo:table-cell>
										<fo:block xsl:use-attribute-sets="texto" text-align="end">
											<xsl:value-of select="@titulo"/>								
										</fo:block>
									</fo:table-cell>								
								</fo:table-row>																											
							</fo:table-body>
						</fo:table>	
					</fo:block>
					<fo:block border-bottom-color="black">
						<fo:table table-layout="fixed">
							<fo:table-column column-width="9.0cm"/>
							<fo:table-column column-width="10.0cm"/>
							<fo:table-body>
								<fo:table-row>
									<fo:table-cell>
										<fo:block xsl:use-attribute-sets="texto" text-align="start">
											<xsl:value-of select="@tipoAcomp"/>
										</fo:block>
									</fo:table-cell>
									<fo:table-cell>
										<fo:block xsl:use-attribute-sets="texto" text-align="end">
											<xsl:value-of select="@mesReferencia"/>
										</fo:block>
									</fo:table-cell>								
								</fo:table-row>																											
							</fo:table-body>
						</fo:table>	
					</fo:block>
<!--					<xsl:apply-templates select="acompanhamentos/hierarquia"/>				-->
				</fo:static-content>

				<fo:static-content flow-name="xsl-region-after">
					<fo:block border-top-color="black" border-top-width="0.2mm" border-top-style="solid" text-align="right">
						<fo:table table-layout="fixed">
							<fo:table-column column-width="8.0cm"/>
							<fo:table-column column-width="3.0cm"/>
							<fo:table-column column-width="8.0cm"/>
							<fo:table-body>
								<fo:table-row>
									<fo:table-cell>
										<fo:block xsl:use-attribute-sets="texto" text-align="start">
											<xsl:value-of select="@titulo"/>								
										</fo:block>	
									</fo:table-cell>
									<fo:table-cell>
										<fo:block xsl:use-attribute-sets="label" text-align="center">
											<xsl:value-of select="@datahora"/>								
										</fo:block>	
									</fo:table-cell>								
									<fo:table-cell>
										<fo:block xsl:use-attribute-sets="label" text-align="end">
											<xsl:if test = "@tipoDadosGerais != ''">
												<fo:inline xsl:use-attribute-sets="textoMenor">
													(<xsl:value-of select="@tipoDadosGerais"/>)
												</fo:inline>
											</xsl:if>
											<fo:inline xsl:use-attribute-sets="label">
												Página <fo:page-number/> de <fo:page-number-citation ref-id="last-page"/>								
											</fo:inline>
										</fo:block>
									</fo:table-cell>								
								</fo:table-row>																											
							</fo:table-body>
						</fo:table>	
					</fo:block>
				</fo:static-content>

				<fo:flow flow-name="xsl-region-body">
					<xsl:apply-templates select="itensGeral"/>
					<fo:block id="last-page"/>
				</fo:flow>
				
			</fo:page-sequence>
		</fo:root>
	</xsl:template>

	<xsl:template match="itensGeral">
		<fo:block break-before="page"/>
		<fo:table>
			<fo:table-column/>
			<fo:table-header>
				<fo:table-row>
					<fo:table-cell>						
						<xsl:if test="@exibirOrgao = 'S'">
									<fo:block space-after="0.2cm" xsl:use-attribute-sets="orgao">
										<xsl:value-of select="@orgao"/>
									</fo:block>
						</xsl:if>
					</fo:table-cell>
				</fo:table-row>
			</fo:table-header>
			<fo:table-body>
				<fo:table-row>
					<fo:table-cell>
						<fo:block>
							<xsl:apply-templates select="itens"/>
						</fo:block>
					</fo:table-cell>
				</fo:table-row>
			</fo:table-body>
		</fo:table>
	</xsl:template>

	<xsl:template match="posicao">			
		<xsl:value-of select="@valor" />
		<xsl:apply-templates select="imagemRel" />
	</xsl:template>

	<xsl:template match="imagemRel">
		<fo:external-graphic>
			<xsl:attribute name="src">
				url('<xsl:value-of select="@src"/>')
			</xsl:attribute>
		</fo:external-graphic>
	</xsl:template>

	<xsl:template match="itens">
		<xsl:apply-templates select="item"/>
	</xsl:template>
	
	<xsl:template match="item">
		<xsl:apply-templates select="hierarquia"/>		
		<!-- 
		<fo:block space-before="0.3cm" xsl:use-attribute-sets="nomeItem" text-align="left">
			<xsl:value-of select="@nomeItem"/>
		</fo:block>
		 -->
		<xsl:if test="dadosGerais">
			<xsl:apply-templates select="dadosGerais"/>
		</xsl:if>

		<xsl:if test="niveisPlanejamento">
			<xsl:apply-templates select="niveisPlanejamento"/>
		</xsl:if>

		<xsl:if test="localizacao">
			<xsl:apply-templates select="localizacao"/>
		</xsl:if>

		<xsl:if test="indicadores">
			<xsl:apply-templates select="indicadores"/>
		</xsl:if>

		<xsl:if test="indicadoresResultado">
			<xsl:apply-templates select="indicadoresResultado"/>
		</xsl:if>

		<xsl:if test="acoes">
			<xsl:apply-templates select="acoes"/>
		</xsl:if>

		<xsl:if test="avaliacoes">
			<xsl:apply-templates select="avaliacoes"/>
		</xsl:if>

		<xsl:if test="beneficiarios">
			<xsl:apply-templates select="beneficiarios"/>
		</xsl:if>
		<xsl:if test="criterios">
			<xsl:apply-templates select="criterios"/>
		</xsl:if>
		<xsl:if test="entidades">
			<xsl:apply-templates select="entidades"/>
		</xsl:if>
		<xsl:if test="fontesRecurso">
			<xsl:apply-templates select="fontesRecurso"/>
		</xsl:if>
		<xsl:if test="pontosCriticos">
			<xsl:apply-templates select="pontosCriticos"/>
		</xsl:if>
		<xsl:if test="categorias">
			<xsl:apply-templates select="categorias"/>
		</xsl:if>
		<xsl:if test="contas">
			<xsl:apply-templates select="contas"/>
		</xsl:if>

		<!-- 
		<xsl:if test="localizacao">
			<xsl:apply-templates select="localizacao"/>
		</xsl:if>
		 -->

		<xsl:if test="evolucaoFinanceira">
			<xsl:apply-templates select="evolucaoFinanceira"/>
		</xsl:if>

		<!-- 
		<xsl:if test="eventos">
			<xsl:apply-templates select="eventos"/>
		</xsl:if>

		<xsl:if test="pontosCriticos">
			<xsl:apply-templates select="pontosCriticos"/>
		</xsl:if>
		 -->
	</xsl:template>

	<xsl:template match="hierarquia">			
		<!-- 
		<fo:block text-align="left" xsl:use-attribute-sets="texto">
			<xsl:value-of select="@estrutura"/> <xsl:value-of select="@nome"/>
		</fo:block>
		 -->
		<fo:block space-before="0.3cm">
		</fo:block>
		<fo:table table-layout="fixed">
			<fo:table-column column-width="19.0cm"/>
			<fo:table-body>
				<xsl:apply-templates select="itemHierarquia"/>		
			</fo:table-body>
		</fo:table>	
	</xsl:template>

	<xsl:template match="itemHierarquia">			
		<fo:table-row>
			<fo:table-cell display-align="center">
				<fo:block text-align="left" xsl:use-attribute-sets="label">
					<fo:leader>
						<xsl:attribute name="leader-length">
							<xsl:value-of select="(@nivel * 3)"/>mm
						</xsl:attribute>											
					</fo:leader>	
					<xsl:value-of select="@nomeEstrutura"/>: 
					<xsl:if test = "@destacarItem = 'S'">
						<fo:inline text-align="left" xsl:use-attribute-sets="nomeItem">
							<xsl:value-of select="@nome"/>
						</fo:inline>
					</xsl:if>
					<xsl:if test = "@destacarItem = 'N'">
						<fo:inline text-align="left" xsl:use-attribute-sets="texto">
							<xsl:value-of select="@nome"/>
						</fo:inline>
					</xsl:if>
				</fo:block>
			</fo:table-cell>
		</fo:table-row>
	</xsl:template>
	
	<xsl:template match="avaliacoes">
		<fo:block space-before="0.3cm" xsl:use-attribute-sets="titulo" text-align="left">
			<xsl:value-of select="@labelSituacaoListaParecer"/>:
		</fo:block>
		<xsl:apply-templates select="avaliacao"/>
	</xsl:template>

	<xsl:template match="avaliacao">			
		<fo:table table-layout="fixed">
			<fo:table-column column-width="5.5cm"/>
			<fo:table-column column-width="13.5cm"/>
			<fo:table-body>
				<xsl:if test = "@statusLiberado = 'S'">
					<xsl:if test = "@descricao != 'N/I'">
						<fo:table-row>
							<fo:table-cell display-align="before">
								<fo:block space-after="0.2cm" text-align="right" xsl:use-attribute-sets="label">
									<xsl:value-of select="@label"/>:
								</fo:block>
							</fo:table-cell> 
							<fo:table-cell display-align="center">
								<fo:block space-after="0.2cm" text-align="left" xsl:use-attribute-sets="label" white-space-collapse="false">
									<fo:inline xsl:use-attribute-sets="texto"><xsl:value-of select="@responsavel"/></fo:inline>  Desde: <fo:inline xsl:use-attribute-sets="texto"><xsl:value-of select="@ultManutencao"/></fo:inline> 
								</fo:block>
							</fo:table-cell>
						</fo:table-row>
					</xsl:if>
					<fo:table-row>
						<fo:table-cell display-align="before">
							<fo:block space-after="0.2cm" text-align="right" xsl:use-attribute-sets="label">
								<fo:table>
									<fo:table-column/>
									<fo:table-column column-width="1cm"/>
									<fo:table-body>
										<fo:table-row>
											<fo:table-cell display-align="before">
												<fo:block space-after="0.2cm" text-align="right" xsl:use-attribute-sets="label">
													Parecer:
												</fo:block>
											</fo:table-cell>
											<fo:table-cell>
												<fo:block space-after="0.2cm" text-align="center" xsl:use-attribute-sets="label">
													<fo:external-graphic>    
														<xsl:attribute name="src">
															url('<xsl:value-of select="@cor"/>')
														</xsl:attribute>
													</fo:external-graphic>
												</fo:block>
											</fo:table-cell>
										</fo:table-row>
									</fo:table-body>
								</fo:table>
							</fo:block>
						</fo:table-cell>
						<fo:table-cell display-align="center">
							<fo:block space-after="0.2cm" text-align="left" xsl:use-attribute-sets="label" white-space-collapse="false">
								<xsl:value-of select="@descricao"/>
							</fo:block>
						</fo:table-cell>
					</fo:table-row>
					<xsl:if test = "@descricao != 'N/I'">
						<fo:table-row>
							<xsl:if test = "@complemento != 'ocultar'">
								<fo:table-cell display-align="before">
									<fo:block space-after="0.2cm" text-align="right" xsl:use-attribute-sets="label">
										Observações:
									</fo:block>
								</fo:table-cell>							
								<fo:table-cell display-align="center">
									<fo:block space-after="0.2cm" text-align="left" xsl:use-attribute-sets="label" white-space-collapse="false">
										<xsl:value-of select="@complemento"/>
									</fo:block>
								</fo:table-cell>
							</xsl:if>
						</fo:table-row>
						<fo:table-row>
							<fo:table-cell display-align="center">
								<fo:block space-after="0.2cm" text-align="right" xsl:use-attribute-sets="label">
									<xsl:value-of select="@labelSituacaoParecer"/>:
								</fo:block>
							</fo:table-cell>
							<fo:table-cell display-align="center">
								<fo:block space-after="0.2cm" text-align="left" xsl:use-attribute-sets="texto">
									<xsl:value-of select="@situacao"/>
								</fo:block>
							</fo:table-cell>
						</fo:table-row>
						<!-- Comentado devido anotação (0014028) do mantis 0008119
						<fo:table-row>
							<fo:table-cell display-align="before">
								<fo:block space-after="0.2cm" text-align="right" xsl:use-attribute-sets="label">
									Atribuído por:
								</fo:block>
							</fo:table-cell>
							<fo:table-cell display-align="before">
								<fo:block space-after="0.2cm" text-align="left" xsl:use-attribute-sets="texto">
									<xsl:value-of select="@responsavel"/>
								</fo:block>
							</fo:table-cell>
						</fo:table-row>
						-->
					</xsl:if>
				</xsl:if>
				
				<xsl:if test = "@statusLiberado = 'N'">
					<fo:table-row>
						<fo:table-cell display-align="center" number-columns-spanned="2">
							<fo:block text-align="center" xsl:use-attribute-sets="texto">
								(Acompanhamento Não Liberado)
							</fo:block>
						</fo:table-cell>
					</fo:table-row>
				</xsl:if>
				<fo:table-row>
					<fo:table-cell display-align="center">
						<fo:block space-after="0.5cm" text-align="center" xsl:use-attribute-sets="texto">
						</fo:block>
					</fo:table-cell>
				</fo:table-row>
			</fo:table-body>
		</fo:table>	
	</xsl:template>

	<xsl:template match="dadosGerais">
		<fo:block space-before="0.3cm" xsl:use-attribute-sets="titulo" text-align="left">
			<xsl:value-of select="@labelDadosGerais"/>
		</fo:block>
		<fo:table table-layout="fixed">
			<fo:table-column column-width="5.5cm"/>
			<fo:table-column column-width="13.5cm"/>
			<fo:table-header>
				<fo:table-row>
					<fo:table-cell display-align="center"></fo:table-cell>
					<fo:table-cell display-align="center"></fo:table-cell>
				</fo:table-row>
			</fo:table-header>
			<fo:table-body>
				<xsl:apply-templates select="atributosDadosGerais"/>		
			</fo:table-body>
		</fo:table>	
	</xsl:template>

	<xsl:template match="atributosDadosGerais">			
		<fo:table-row>
			<fo:table-cell display-align="before">
				<fo:block space-after="0.2cm" text-align="left" xsl:use-attribute-sets="label">
					<xsl:value-of select="@label"/>: </fo:block>
				</fo:table-cell>
			<fo:table-cell display-align="before">
				<fo:block space-after="0.2cm" text-align="left" xsl:use-attribute-sets="textoDesc">
					<xsl:value-of select="@conteudo"/></fo:block>
				</fo:table-cell>
		</fo:table-row>
	</xsl:template>
	
	<xsl:template match="indicadores">	
		<fo:block space-before="0.3cm" xsl:use-attribute-sets="titulo" text-align="left">
			<xsl:value-of select="@labelFuncao"/>
		</fo:block>
		<fo:table table-layout="fixed">
			<fo:table-column column-width="2.0cm"/>
			<fo:table-column column-width="2.0cm"/>
			<fo:table-column column-width="0.3cm"/>
			
			<xsl:apply-templates select="columnExercicio"/>
			
			<fo:table-column column-width="1.9cm"/>
			<fo:table-column column-width="2.0cm"/>
			<fo:table-column column-width="2.5cm"/>
			<fo:table-column column-width="1.0cm"/>
			
			<!-- 
			<fo:table-header>
				<fo:table-row background-color="lightgray">
					<fo:table-cell display-align="before">
						<fo:block text-align="center" xsl:use-attribute-sets="textoMenorNegrito">Indicador</fo:block>
					</fo:table-cell>
					<fo:table-cell display-align="before">
						<fo:block text-align="center" xsl:use-attribute-sets="textoMenorNegrito">Realizado no Mês</fo:block>
					</fo:table-cell>
					<fo:table-cell display-align="before"> </fo:table-cell>
					
					<xsl:apply-templates select="indExercicio"/>

					<fo:table-cell display-align="before">
						<fo:block text-align="center" xsl:use-attribute-sets="textoMenorNegrito">Total</fo:block>
					</fo:table-cell>
					<fo:table-cell display-align="before">
						<fo:block text-align="center" xsl:use-attribute-sets="textoMenorNegrito">Realizado/ Previsto(%)</fo:block>
					</fo:table-cell>
					<xsl:if test = "@mostrarProjecao = 'N'">
						<xsl:if test = "@exibirSituacaoColuna = 'S'">
							<fo:table-cell display-align="before">
								<fo:block text-align="left" xsl:use-attribute-sets="textoMenorNegrito">Situação</fo:block>
							</fo:table-cell>
						</xsl:if>
					</xsl:if>
					<xsl:if test="@mostrarProjecao = 'S'">
						<fo:table-cell display-align="before">
							<fo:block text-align="left" xsl:use-attribute-sets="textoMenorNegrito">Projeção na Data Final</fo:block>
							<fo:block text-align="left" xsl:use-attribute-sets="textoMenorNegrito">Situação Prevista</fo:block>
						</fo:table-cell>
						<fo:table-cell display-align="before">
							<fo:block text-align="center" xsl:use-attribute-sets="textoMenorNegrito">Projeção de Término</fo:block>
						</fo:table-cell>
					</xsl:if>
				</fo:table-row>
			</fo:table-header>
			-->
			<fo:table-body>
				<xsl:apply-templates select="indicadorAcomp"/>
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
				<fo:block text-align="center" xsl:use-attribute-sets="textoMenorNegrito"><xsl:value-of select="@exercicio"/></fo:block>
			</fo:table-cell>
		</xsl:if>
	</xsl:template>


	<xsl:template match="indicadorAcomp">
	
		<xsl:if test="@exibirGrupoIndicador = 'S'">
			<fo:table-row background-color="lightgray">
				<fo:table-cell display-align="before">
					<fo:block text-align="center" xsl:use-attribute-sets="textoMenorNegrito">
						<xsl:value-of select="@grupoIndicador"/>
					</fo:block>
				</fo:table-cell>
				<fo:table-cell display-align="before">
					<fo:block text-align="center" xsl:use-attribute-sets="textoMenorNegrito">Realizado no Mês</fo:block>
				</fo:table-cell>
				<fo:table-cell display-align="before"> </fo:table-cell>
				
				<xsl:apply-templates select="../indExercicio"/>

				<fo:table-cell display-align="before">
					<fo:block text-align="center" xsl:use-attribute-sets="textoMenorNegrito">Total</fo:block>
				</fo:table-cell>
				<fo:table-cell display-align="before">
					<fo:block text-align="center" xsl:use-attribute-sets="textoMenorNegrito">Realizado/ Previsto(%)</fo:block>
				</fo:table-cell>
				<fo:table-cell display-align="before">
					<xsl:if test="../@mostrarProjecao = 'S'">
						<fo:block text-align="left" xsl:use-attribute-sets="textoMenorNegrito">Projeção na Data Final</fo:block>
						<fo:block text-align="left" xsl:use-attribute-sets="textoMenorNegrito">Situação Prevista</fo:block>
					</xsl:if>
					<xsl:if test="../@mostrarProjecao = 'N'">
						<fo:block text-align="left" xsl:use-attribute-sets="textoMenorNegrito">Situação</fo:block>
					</xsl:if>
				</fo:table-cell>
				<fo:table-cell display-align="before">
					<xsl:if test="../@mostrarProjecao = 'S'">
						<fo:block text-align="center" xsl:use-attribute-sets="textoMenorNegrito">Projeção de Término</fo:block>
					</xsl:if>
				</fo:table-cell>
			</fo:table-row>
		</xsl:if>
	
		<fo:table-row>
			<!-- fo:table-cell display-align="before" number-rows-spanned="2"-->
			<fo:table-cell display-align="before">
				<fo:block text-align="left" xsl:use-attribute-sets="textoMenor">
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
									<fo:block space-before="0.4cm"/>
								</fo:table-cell>
							</fo:table-row>
							<fo:table-row>
								<fo:table-cell>
									<fo:block text-align="center" xsl:use-attribute-sets="textoMenor" background-color="#EEE9E9">
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
									<fo:block text-align="center" xsl:use-attribute-sets="textoMenorNegrito">
										P
									</fo:block>
								</fo:table-cell>
							</fo:table-row>
							<fo:table-row>
								<fo:table-cell>
									<fo:block text-align="center" xsl:use-attribute-sets="textoMenorNegrito" background-color="#EEE9E9">
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
			
			<xsl:if test = "@mostrarProjecao = 'S'">
				<xsl:apply-templates select="valorProjecao"/>
			</xsl:if>
			<xsl:if test = "@mostrarProjecao = 'N'">
				<xsl:if test = "@situacao != ''">
					<fo:table-cell display-align="before">
						<fo:block text-align="left" xsl:use-attribute-sets="textoMenor">
							<xsl:value-of select="@situacao"/>
						</fo:block>
					</fo:table-cell>
				</xsl:if>
			</xsl:if>
		</fo:table-row>
		
		<xsl:if test = "@situacao != ''">
			<xsl:if test = "@mostrarProjecao = 'S'">
				<fo:table-row>
					<fo:table-cell display-align="before">
						<fo:block text-align="right" xsl:use-attribute-sets="textoMenorNegrito">
							<xsl:value-of select="@labelSituacaoParecer"/>:
						</fo:block>
					</fo:table-cell>
					<fo:table-cell display-align="before" number-columns-spanned="{@numeroExercicios + 4}">
						<fo:block text-align="left" xsl:use-attribute-sets="textoMenor">
							<xsl:value-of select="@situacao"/>
						</fo:block>
					</fo:table-cell>
				</fo:table-row>
			</xsl:if>
		</xsl:if>

		<fo:table-row>
			<fo:table-cell display-align="center" number-columns-spanned="{@numeroExercicios + 7}">
				<!-- <fo:block space-before="0.5cm" border-bottom-color="black" border-bottom-width="0.1mm" border-bottom-style="solid"> -->
				<fo:block space-before="0.5cm">
				</fo:block>
			</fo:table-cell>
		</fo:table-row>
		
	</xsl:template>

	<xsl:template match="valorExercicio">
		<fo:table-cell display-align="before">
			<fo:block>
				<fo:table>
					<fo:table-column/>
					<fo:table-body>
						<fo:table-row>
							<fo:table-cell display-align="before">
								<fo:block text-align="right" xsl:use-attribute-sets="textoMenor">
									<xsl:value-of select="@valorPrevisto"/>
								</fo:block>
							</fo:table-cell>
						</fo:table-row>
						<fo:table-row>
							<fo:table-cell display-align="before">
								<fo:block text-align="right" xsl:use-attribute-sets="textoMenor" background-color="#EEE9E9">
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
								<fo:block text-align="right" xsl:use-attribute-sets="textoMenor">
									<xsl:value-of select="@totalPrevisto"/>
								</fo:block>
							</fo:table-cell>
						</fo:table-row>
						<fo:table-row>
							<fo:table-cell display-align="before">
								<fo:block text-align="right" xsl:use-attribute-sets="textoMenor" background-color="#EEE9E9">
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
								<fo:block text-align="center" xsl:use-attribute-sets="textoMenorNegrito">
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
				<fo:block text-align="left" xsl:use-attribute-sets="textoMenor">
					<xsl:value-of select="@projecao"/>
				</fo:block>
			</xsl:if>
		</fo:table-cell>
		<fo:table-cell display-align="before">
			<xsl:if test="@mostrarProjecao = 'S'">
				<fo:block text-align="center" xsl:use-attribute-sets="textoMenor">
					<xsl:value-of select="@dataTermino"/>
				</fo:block>
			</xsl:if>
		</fo:table-cell>
	</xsl:template>


	<xsl:template match="evolucaoFinanceira">			
		<fo:table>
			<fo:table-column/>
			<fo:table-column/>
			<fo:table-body>
				<fo:table-row>
					<fo:table-cell>
						<fo:block space-before="0.3cm" xsl:use-attribute-sets="titulo" text-align="left">
							Quadro de Recursos Orçamentário-Financeiros
						</fo:block>
					</fo:table-cell>
					<fo:table-cell>
						<fo:block space-before="0.3cm" xsl:use-attribute-sets="valorTabela" text-align="right">
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
					<!-- 
					<fo:table-cell display-align="center">
						<fo:block text-align="center" xsl:use-attribute-sets="textoMenorNegrito">
							Fonte
						</fo:block>
					</fo:table-cell>
					<fo:table-cell display-align="center">
						<fo:block text-align="center" xsl:use-attribute-sets="textoMenorNegrito">
							Recurso
						</fo:block>
					</fo:table-cell>
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

		<xsl:apply-templates select="recursoFinanceiro"/>		

		<xsl:apply-templates select="totalEvolucaoFinanceiraExercicio"/>		
	
	</xsl:template>
	
	<xsl:template match="recursoFinanceiro">			
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

	<!-- Abas do Cadastro de Itens -->
	
	<xsl:template match="beneficiarios">
		<fo:block space-before="0.3cm" xsl:use-attribute-sets="titulo" text-align="left"><xsl:value-of select="@label"/></fo:block>
		<fo:block>
			<fo:table space-before="0.1cm" table-layout="fixed">
				<fo:table-column column-width="3.4cm"/>
				<fo:table-column column-width="5.7cm"/>
				<fo:table-column column-width="1.4cm"/>
				<fo:table-column column-width="8.5cm"/>		
				<fo:table-header>		
					<fo:table-row>
						<fo:table-cell/>
						<fo:table-cell background-color="#CCC"><fo:block text-align="justify" xsl:use-attribute-sets="label">Nome</fo:block></fo:table-cell>
						<fo:table-cell background-color="#CCC"><fo:block text-align="justify" xsl:use-attribute-sets="label">Qtde.</fo:block></fo:table-cell>
						<fo:table-cell background-color="#CCC"><fo:block text-align="justify" xsl:use-attribute-sets="label">Observações</fo:block></fo:table-cell>										
					</fo:table-row>
				</fo:table-header>				
				<fo:table-body>		
						<xsl:apply-templates select="beneficiario"/>									
				</fo:table-body>
			</fo:table>					
		</fo:block>
	</xsl:template>
	
	<xsl:template match="beneficiario">	
			<fo:table-row>
				<fo:table-cell/>
				<fo:table-cell><fo:block space-before="0.1cm" text-align="justify" xsl:use-attribute-sets="textoDesc"><xsl:value-of select="@descricao"/></fo:block></fo:table-cell>
				<fo:table-cell><fo:block space-before="0.1cm" text-align="justify" xsl:use-attribute-sets="textoDesc"><xsl:value-of select="@quantidade"/></fo:block></fo:table-cell>
				<fo:table-cell><fo:block space-before="0.1cm" text-align="justify" xsl:use-attribute-sets="textoDesc"><xsl:value-of select="@observacoes"/></fo:block></fo:table-cell>										
			</fo:table-row>
	</xsl:template>
	
	
	<xsl:template match="criterios">	
		<fo:block space-before="0.3cm" xsl:use-attribute-sets="titulo" text-align="left"><xsl:value-of select="@label"/></fo:block>
		<fo:block>
			<fo:table space-before="0.1cm" table-layout="fixed">
				<fo:table-column column-width="19cm"/>
				<fo:table-body>		
						<xsl:apply-templates select="criterio"/>									
				</fo:table-body>
			</fo:table>					
		</fo:block>
	</xsl:template>	

	<xsl:template match="criterio">	
			<fo:table-row>
				<fo:table-cell><fo:block space-before="0.1cm" text-align="justify" xsl:use-attribute-sets="textoDesc">- <xsl:value-of select="."/></fo:block></fo:table-cell>
			</fo:table-row>
	</xsl:template>

	<xsl:template match="entidades">	
		<fo:block space-before="0.3cm" xsl:use-attribute-sets="titulo" text-align="left"><xsl:value-of select="@label"/></fo:block>
		<fo:block>
			<fo:table space-before="0.1cm" table-layout="fixed">
				<fo:table-column column-width="6cm"/>
				<fo:table-column column-width="4.0cm"/>
				<fo:table-column column-width="2cm"/>
				<fo:table-column column-width="2.4cm"/>
				<fo:table-column column-width="2.4cm"/>
				<fo:table-column column-width="4.8cm"/>								
				<fo:table-header>		
					<fo:table-row>
						<fo:table-cell/>
						<fo:table-cell background-color="#CCC"><fo:block text-align="justify" xsl:use-attribute-sets="label">Entidade</fo:block></fo:table-cell>
						<fo:table-cell background-color="#CCC"><fo:block text-align="justify" xsl:use-attribute-sets="label">Atuação</fo:block></fo:table-cell>
						<fo:table-cell background-color="#CCC"><fo:block text-align="justify" xsl:use-attribute-sets="label">Início</fo:block></fo:table-cell>
						<fo:table-cell background-color="#CCC"><fo:block text-align="justify" xsl:use-attribute-sets="label">Término</fo:block></fo:table-cell>												
						<fo:table-cell background-color="#CCC"><fo:block text-align="justify" xsl:use-attribute-sets="label">Descrição/Tipo da Participação</fo:block></fo:table-cell>						
					</fo:table-row>
				</fo:table-header>				
				<fo:table-body>		
						<xsl:apply-templates select="entidade"/>									
				</fo:table-body>
			</fo:table>					
		</fo:block>
	</xsl:template>
	
	<xsl:template match="entidade">	
			<fo:table-row>
				<fo:table-cell/>
				<fo:table-cell><fo:block space-before="0.1cm" text-align="justify" xsl:use-attribute-sets="textoDesc"><xsl:value-of select="@nome"/></fo:block></fo:table-cell>
				<fo:table-cell><fo:block space-before="0.1cm" text-align="justify" xsl:use-attribute-sets="textoDesc"><xsl:value-of select="@atuacao"/></fo:block></fo:table-cell>
				<fo:table-cell><fo:block space-before="0.1cm" text-align="justify" xsl:use-attribute-sets="textoDesc"><xsl:value-of select="@inicio"/></fo:block></fo:table-cell>
				<fo:table-cell><fo:block space-before="0.1cm" text-align="justify" xsl:use-attribute-sets="textoDesc"><xsl:value-of select="@termino"/></fo:block></fo:table-cell>
				<fo:table-cell><fo:block space-before="0.1cm" text-align="justify" xsl:use-attribute-sets="textoDesc"><xsl:value-of select="@descricao"/></fo:block></fo:table-cell>								
			</fo:table-row>
	</xsl:template>	

	<xsl:template match="niveisPlanejamento">	
		<fo:table table-layout="fixed" space-before="0.1cm">
			<fo:table-column column-width="5.5cm"/>
			<fo:table-column column-width="13.5cm"/>
			<fo:table-body>		
				<fo:table-row>
					<fo:table-cell>
						<fo:block space-after="0.1cm" text-align="left" xsl:use-attribute-sets="label">
							Níveis de Planejamento:
						</fo:block>
					</fo:table-cell>
					<fo:table-cell>	
						<xsl:apply-templates select="nivelPlanejamento"/>
					</fo:table-cell>
				</fo:table-row>
			</fo:table-body>
		</fo:table>	
	</xsl:template>
	
	<xsl:template match="nivelPlanejamento">	
		<fo:block xsl:use-attribute-sets="label" space-after="0.1cm" text-align="justify">
				<xsl:value-of select="."/>
		</fo:block>								
	</xsl:template>

	
	<xsl:template match="fontesRecurso">	
		<fo:block space-before="0.3cm" xsl:use-attribute-sets="titulo" text-align="left"><xsl:value-of select="@label"/></fo:block>
		<xsl:apply-templates select="fonteRecurso"/>		
		<fo:block space-before="0.2cm"/>
		<xsl:apply-templates select="fonteTotais"/>		
	</xsl:template>

	<xsl:template match="fonteRecurso">		
		<fo:table table-layout="fixed" space-before="0.2cm">
			<fo:table-column column-width="0.1mm"/>
			<xsl:apply-templates select="fonteRecursoCabecalho"/>
		</fo:table>
		<fo:table table-layout="fixed">
			<xsl:apply-templates select="recursos"/>
		</fo:table>
		<fo:table table-layout="fixed">
			<xsl:apply-templates select="fonteRecursosRodape"/>
		</fo:table>
	</xsl:template>
	
<!-- Gera cabeçalho da tabela de fontes de recursos....  -->	
	<xsl:template match="fonteRecursoCabecalho">
			<xsl:apply-templates select="itemCabecalho" />
			<fo:table-body>
				<fo:table-row>
					<fo:table-cell />
					<xsl:apply-templates select="exercicioRecurso"/>
				</fo:table-row>
			</fo:table-body>
	</xsl:template>

	<xsl:template match="itemCabecalho">
		<fo:table-column column-width="{@tam}"/>
	</xsl:template>

	<xsl:template match="exercicioRecurso">
		<fo:table-cell background-color="#CCC" border-after-style="solid" border-before-style="solid" border-end-style="solid" border-start-style="solid" border-width="0.2mm">
			<fo:block xsl:use-attribute-sets="textoDescFR" text-align="center">
				<xsl:value-of select="@exercicio"/>
			</fo:block>
		</fo:table-cell>
	</xsl:template>
<!-- /Gera cabeçalho da tabela de fontes de recursos....  -->	

<!-- Gera Itens e valores da tabela de fontes de recursos....  -->	
	<xsl:template match="recursos">
			<fo:table-column column-width="0.1mm"/>
			<xsl:apply-templates select="itemRecurso" />
			<fo:table-body>
				<fo:table-row>
					<fo:table-cell/>
					<xsl:apply-templates select="recurso"/>
				</fo:table-row>
			</fo:table-body>
	</xsl:template>

	<xsl:template match="itemRecurso">
		<fo:table-column column-width="{@tam}"/>
	</xsl:template>

	
	<xsl:template match="recurso">	
		<xsl:if test="@conteudo='S'">
			<xsl:if test="@valor!=''">
			<fo:table-cell border-before-style="solid" border-end-style="solid" border-start-style="solid" border-width="0.2mm">
				<fo:block xsl:use-attribute-sets="textoDescFR" text-align="{@alinhamento}">
					<xsl:value-of select="@valor"/>
				</fo:block>
			</fo:table-cell>
			</xsl:if>
			<xsl:if test="@valor=''">
			<fo:table-cell border-end-style="solid" border-start-style="solid" border-width="0.2mm">
				<fo:block xsl:use-attribute-sets="textoDescFR" text-align="{@alinhamento}">
					<xsl:value-of select="@valor"/>
				</fo:block>
			</fo:table-cell>
			</xsl:if>
		</xsl:if>
		<xsl:if test="@conteudo='N'">
			<fo:table-cell/>
		</xsl:if>
	</xsl:template>	
<!-- /Gera Itens e valores da tabela de fontes de recursos....  -->	

<!-- Gera Itens e valores de rodapé da tabela de fontes de recursos....  -->	
	<xsl:template match="fonteRecursosRodape">
			<xsl:apply-templates select="linhaTotal" />
	</xsl:template>

	<xsl:template match="linhaTotal">
			<fo:table-column column-width="0.1mm"/>
			<xsl:apply-templates select="itemTotal" />
			<fo:table-body>
				<fo:table-row>
					<fo:table-cell/>
					<xsl:apply-templates select="linha"/>
				</fo:table-row>
			</fo:table-body>
	</xsl:template>

	<xsl:template match="itemTotal">
		<fo:table-column column-width="{@tam}"/>
	</xsl:template>

	<xsl:template match="linha">
		<xsl:if test="@conteudo='S'">
			<xsl:if test="@valor!=''">
			<fo:table-cell background-color="{@corFundo}" border-after-style="solid" border-before-style="solid" border-end-style="solid" border-start-style="solid" border-width="0.2mm">
				<fo:block xsl:use-attribute-sets="textoDescFR" text-align="{@alinhamento}">
					<xsl:value-of select="@valor"/>
				</fo:block>
			</fo:table-cell>
			</xsl:if>
			<xsl:if test="@valor=''">
				<xsl:if test="@borda=''">
				<fo:table-cell background-color="{@corFundo}" border-end-style="solid" border-start-style="solid" border-width="0.2mm">
					<fo:block xsl:use-attribute-sets="textoDescFR" text-align="{@alinhamento}">
						<xsl:value-of select="@valor"/>
					</fo:block>
				</fo:table-cell>
				</xsl:if>
				<xsl:if test="@borda='cima'">
				<fo:table-cell background-color="{@corFundo}" border-before-style="solid" border-end-style="solid" border-start-style="solid" border-width="0.2mm">
					<fo:block xsl:use-attribute-sets="textoDescFR" text-align="{@alinhamento}">
						<xsl:value-of select="@valor"/>
					</fo:block>
				</fo:table-cell>
				</xsl:if>
				<xsl:if test="@borda='baixo'">
				<fo:table-cell background-color="{@corFundo}" border-after-style="solid" border-end-style="solid" border-start-style="solid" border-width="0.2mm">
					<fo:block xsl:use-attribute-sets="textoDescFR" text-align="{@alinhamento}">
						<xsl:value-of select="@valor"/>
					</fo:block>
				</fo:table-cell>
				</xsl:if>
			</xsl:if>
		</xsl:if>
		<xsl:if test="@conteudo='N'">
			<xsl:if test="@borda=''">
				<fo:table-cell border-width="0.2mm"/>
			</xsl:if>
			<xsl:if test="@borda='cima'">
				<fo:table-cell border-before-style="solid" border-width="0.2mm"/>
			</xsl:if>
			<xsl:if test="@borda='baixo'">
				<fo:table-cell border-after-style="solid" border-width="0.2mm"/>
			</xsl:if>
		</xsl:if>
	</xsl:template>
<!-- /Gera Itens e valores de rodapé da tabela de fontes de recursos....  -->	

<!-- Gera a tabela de totais nas fontes de recursos....  -->	
	<xsl:template match="fonteTotais">		
		<fo:table table-layout="fixed" space-before="0.2cm">
			<fo:table-column column-width="0.1mm"/>
			<xsl:apply-templates select="fonteTotaisCabecalho"/>
		</fo:table>
		<fo:table table-layout="fixed">
			<xsl:apply-templates select="linhaTotalGeral"/>
		</fo:table>
	</xsl:template>
	
	<xsl:template match="fonteTotaisCabecalho">
			<xsl:apply-templates select="itemTotaisCabecalho" />
			<fo:table-body>
				<fo:table-row>
					<fo:table-cell />
					<xsl:apply-templates select="exercicioTotais"/>
				</fo:table-row>
			</fo:table-body>
	</xsl:template>

	<xsl:template match="itemTotaisCabecalho">
		<fo:table-column column-width="{@tam}"/>
	</xsl:template>

	<xsl:template match="exercicioTotais">
		<fo:table-cell background-color="#CCC" border-after-style="solid" border-before-style="solid" border-end-style="solid" border-start-style="solid" border-width="0.2mm">
			<fo:block xsl:use-attribute-sets="textoDescFR" text-align="center">
				<xsl:value-of select="@exercicio"/>
			</fo:block>
		</fo:table-cell>
	</xsl:template>

	<xsl:template match="linhaTotalGeral">
			<fo:table-column column-width="0.1mm"/>
			<xsl:apply-templates select="itemTotalGeral" />
			<fo:table-body>
				<fo:table-row>
					<fo:table-cell/>
					<xsl:apply-templates select="linhaGeral"/>
				</fo:table-row>
			</fo:table-body>
	</xsl:template>

	<xsl:template match="itemTotalGeral">
		<fo:table-column column-width="{@tam}"/>
	</xsl:template>

	<xsl:template match="linhaGeral">
		<xsl:if test="@conteudo='S'">
			<xsl:if test="@valor!=''">
			<fo:table-cell background-color="{@corFundo}" border-after-style="solid" border-before-style="solid" border-end-style="solid" border-start-style="solid" border-width="0.2mm">
				<fo:block xsl:use-attribute-sets="textoDescFR" text-align="{@alinhamento}">
					<xsl:value-of select="@valor"/>
				</fo:block>
			</fo:table-cell>
			</xsl:if>
			<xsl:if test="@valor=''">
				<xsl:if test="@borda=''">
				<fo:table-cell background-color="{@corFundo}" border-end-style="solid" border-start-style="solid" border-width="0.2mm">
					<fo:block xsl:use-attribute-sets="textoDescFR" text-align="{@alinhamento}">
						<xsl:value-of select="@valor"/>
					</fo:block>
				</fo:table-cell>
				</xsl:if>
				<xsl:if test="@borda='cima'">
				<fo:table-cell background-color="{@corFundo}" border-before-style="solid" border-end-style="solid" border-start-style="solid" border-width="0.2mm">
					<fo:block xsl:use-attribute-sets="textoDescFR" text-align="{@alinhamento}">
						<xsl:value-of select="@valor"/>
					</fo:block>
				</fo:table-cell>
				</xsl:if>
				<xsl:if test="@borda='baixo'">
				<fo:table-cell background-color="{@corFundo}" border-after-style="solid" border-end-style="solid" border-start-style="solid" border-width="0.2mm">
					<fo:block xsl:use-attribute-sets="textoDescFR" text-align="{@alinhamento}">
						<xsl:value-of select="@valor"/>
					</fo:block>
				</fo:table-cell>
				</xsl:if>
			</xsl:if>
		</xsl:if>
		<xsl:if test="@conteudo='N'">
			<xsl:if test="@borda=''">
				<fo:table-cell border-end-style="solid" border-width="0.2mm"/>
			</xsl:if>
			<xsl:if test="@borda='cima'">
				<fo:table-cell border-end-style="solid" border-before-style="solid" border-width="0.2mm"/>
			</xsl:if>
			<xsl:if test="@borda='baixo'">
				<fo:table-cell border-end-style="solid" border-after-style="solid" border-width="0.2mm"/>
			</xsl:if>
		</xsl:if>
	</xsl:template>
<!-- /Gera a tabela de totais nas fontes de recursos....  -->	
	
	<xsl:template match="contas">	
		<fo:block space-before="0.3cm" xsl:use-attribute-sets="titulo" text-align="left"><xsl:value-of select="@label"/></fo:block>
		<fo:block>
			<fo:table space-before="0.1cm" table-layout="fixed">
				<fo:table-column column-width="5cm"/>
				<fo:table-column column-width="5.0cm"/>
				<fo:table-column column-width="4.8cm"/>
				<fo:table-column column-width="2.4cm"/>
				<fo:table-column column-width="2.4cm"/>
				<fo:table-header>		
					<fo:table-row>
						<fo:table-cell/>
						<fo:table-cell background-color="#CCC"><fo:block text-align="justify" xsl:use-attribute-sets="label">Fontes de Recurso/Recursos</fo:block></fo:table-cell>
						<fo:table-cell background-color="#CCC"><fo:block text-align="justify" xsl:use-attribute-sets="label"><xsl:value-of select="@nomeColunaConta"/></fo:block></fo:table-cell>
						<fo:table-cell background-color="#CCC"><fo:block text-align="justify" xsl:use-attribute-sets="label">Exercício</fo:block></fo:table-cell>												
						<fo:table-cell background-color="#CCC"><fo:block text-align="justify" xsl:use-attribute-sets="label">Acumulado?</fo:block></fo:table-cell>						
					</fo:table-row>
				</fo:table-header>				
				<fo:table-body>		
						<xsl:apply-templates select="conta"/>									
				</fo:table-body>
			</fo:table>					
		</fo:block>
	</xsl:template>
	
	<xsl:template match="conta">	
			<fo:table-row>
				<fo:table-cell/>
				<fo:table-cell><fo:block space-before="0.1cm" text-align="justify" xsl:use-attribute-sets="textoDesc"><xsl:value-of select="@fonteRecurso"/></fo:block></fo:table-cell>
				<fo:table-cell><fo:block space-before="0.1cm" text-align="justify" xsl:use-attribute-sets="textoDesc"><xsl:value-of select="@estrutura"/></fo:block></fo:table-cell>
				<fo:table-cell><fo:block space-before="0.1cm" text-align="justify" xsl:use-attribute-sets="textoDesc"><xsl:value-of select="@exercicio"/></fo:block></fo:table-cell>
				<fo:table-cell><fo:block space-before="0.1cm" text-align="justify" xsl:use-attribute-sets="textoDesc"><xsl:value-of select="@acumulado"/></fo:block></fo:table-cell>								
			</fo:table-row>
	</xsl:template>		

	<xsl:template match="categorias">	
		<fo:block space-before="0.3cm" xsl:use-attribute-sets="titulo" text-align="left"><xsl:value-of select="@label"/></fo:block>
		<xsl:apply-templates select="categoria"/>		
	</xsl:template>
	
	<xsl:template match="categoria">		
		<fo:table space-before="0.2cm" table-layout="fixed">
			<fo:table-column column-width="19.0cm"/>		
			<fo:table-body>						
				<fo:table-row>
					<fo:table-cell>
							<fo:block xsl:use-attribute-sets="textoDesc" space-after="0.1cm" text-align="justify">
								-
								<fo:leader leader-length="0.2cm"/>
								<xsl:value-of select="@nome"/>
							</fo:block>		
					</fo:table-cell>
				</fo:table-row>
			</fo:table-body>			
		</fo:table>	
		<xsl:apply-templates select="anexo" />
	</xsl:template>	
	
	<xsl:template match="anexo">
		<fo:table space-before="0.2cm" table-layout="fixed">
			<fo:table-column column-width="2.0cm"/>
			<fo:table-column column-width="10.0cm"/>		
			<fo:table-body>		
				<fo:table-row>
					<fo:table-cell/>
					<fo:table-cell>
							<fo:block xsl:use-attribute-sets="textoDesc" space-after="0.1cm" text-align="justify">
								-
								<fo:leader leader-length="0.2cm"/>
								<xsl:value-of select="@descricao"/>
							</fo:block>		
					</fo:table-cell>
				</fo:table-row>
			</fo:table-body>
		</fo:table>
	</xsl:template>


	<xsl:template match="indicadoresResultado">	
		<fo:block space-before="0.3cm" xsl:use-attribute-sets="titulo" text-align="left"><xsl:value-of select="@label"/></fo:block>
		<xsl:apply-templates select="indicador"/>		
	</xsl:template>
	
	<xsl:template match="indicador">		
		<fo:table space-before="0.2cm" table-layout="fixed">
			<fo:table-column column-width="5cm"/>
			<fo:table-column column-width="5.0cm"/>
			<fo:table-column column-width="10.0cm"/>		
			<fo:table-body>		
				<fo:table-row>
					<fo:table-cell/>
					<fo:table-cell>
						<fo:block space-after="0.1cm" text-align="right" xsl:use-attribute-sets="label">
							Nome:
						</fo:block>
					</fo:table-cell>						
					<fo:table-cell>
							<fo:block xsl:use-attribute-sets="label" space-after="0.1cm" text-align="justify">
								<fo:leader leader-length="0.4cm"/>
								<xsl:value-of select="@nome"/>
							</fo:block>		
					</fo:table-cell>
				</fo:table-row>
				<fo:table-row>
					<fo:table-cell/>
					<fo:table-cell>
						<fo:block space-after="0.1cm" text-align="right" xsl:use-attribute-sets="label">
							Descrição:
						</fo:block>
					</fo:table-cell>
					<fo:table-cell>
							<fo:block xsl:use-attribute-sets="textoDesc" space-after="0.1cm" text-align="justify">
								<fo:leader leader-length="0.4cm"/>
								<xsl:value-of select="@descricao"/>
							</fo:block>		
					</fo:table-cell>
				</fo:table-row>
				<fo:table-row>
					<fo:table-cell/>
					<fo:table-cell>
						<fo:block space-after="0.1cm" text-align="right" xsl:use-attribute-sets="label">
							Unidade de Medida:
						</fo:block>
					</fo:table-cell>
					<fo:table-cell>
							<fo:block xsl:use-attribute-sets="textoDesc" space-after="0.1cm" text-align="justify">
								<fo:leader leader-length="0.4cm"/>
								<xsl:value-of select="@unidade"/>
							</fo:block>		
					</fo:table-cell>
				</fo:table-row>
				<fo:table-row>
					<fo:table-cell/>
					<fo:table-cell>
						<fo:block space-after="0.1cm" text-align="right" xsl:use-attribute-sets="label">
							Permite Totalizações?
						</fo:block>
					</fo:table-cell>
					<fo:table-cell>
							<fo:block xsl:use-attribute-sets="textoDesc" space-after="0.1cm" text-align="justify">
								<fo:leader leader-length="0.4cm"/>
								<xsl:value-of select="@totalizacoes"/>
							</fo:block>		
					</fo:table-cell>
				</fo:table-row>
				<xsl:if test="@valorFinal != ''">
					<fo:table-row>
						<fo:table-cell/>
						<fo:table-cell>
							<fo:block space-after="0.1cm" text-align="right" xsl:use-attribute-sets="label">
								Valor Final
							</fo:block>
						</fo:table-cell>
						<fo:table-cell>
								<fo:block xsl:use-attribute-sets="textoDesc" space-after="0.1cm" text-align="justify">
									<fo:leader leader-length="0.4cm"/>
									<xsl:value-of select="@valorFinal"/>
								</fo:block>		
						</fo:table-cell>
					</fo:table-row>
				</xsl:if>
				<fo:table-row>
					<fo:table-cell/>
					<fo:table-cell>
						<fo:block space-after="0.1cm" text-align="right" xsl:use-attribute-sets="label">
							Permite Projeções?						
						</fo:block>
					</fo:table-cell>
					<fo:table-cell>
							<fo:block xsl:use-attribute-sets="textoDesc" space-after="0.1cm" text-align="justify">
								<fo:leader leader-length="0.4cm"/>
								<xsl:value-of select="@projecoes"/>
							</fo:block>		
					</fo:table-cell>
				</fo:table-row>
			</fo:table-body>			
		</fo:table>	
		<fo:block>
			<fo:table space-before="0.1cm" table-layout="fixed">
				<fo:table-column column-width="5.7cm"/>
				<fo:table-column column-width="1.6cm"/>
				<fo:table-column column-width="4.1cm"/>
				<fo:table-column column-width="5.6cm"/>
				<fo:table-header>		
					<fo:table-row>
						<fo:table-cell/>
						<fo:table-cell background-color="#CCC"><fo:block text-align="right" xsl:use-attribute-sets="label">Exercício</fo:block></fo:table-cell>
<!--						<fo:table-cell background-color="#CCC"><fo:block text-align="right" xsl:use-attribute-sets="label">Quantidade Prevista (<xsl:value-of select="@unidade"/>)</fo:block></fo:table-cell>-->
						<fo:table-cell background-color="#CCC"><fo:block text-align="right" xsl:use-attribute-sets="label">Quantidade Prevista</fo:block></fo:table-cell>
						<fo:table-cell/>
					</fo:table-row>
				</fo:table-header>		
				<fo:table-body>		
						<xsl:apply-templates select="exercicio"/>									
						<fo:table-row>
							<xsl:if test="@total != ''">
								<fo:table-cell/>
								<fo:table-cell background-color="#CCC"><fo:block text-align="right" xsl:use-attribute-sets="label">Total</fo:block></fo:table-cell>
								<fo:table-cell background-color="#CCC"><fo:block text-align="right" xsl:use-attribute-sets="texto"><xsl:value-of select="@total"/></fo:block></fo:table-cell>
								<fo:table-cell/>
							</xsl:if>
						</fo:table-row>						
				</fo:table-body>
			</fo:table>					
		</fo:block>				
	</xsl:template>

	<xsl:template match="exercicio">	
			<fo:table-row>
				<fo:table-cell/>
				<fo:table-cell><fo:block space-before="0.1cm" text-align="right" xsl:use-attribute-sets="textoDesc"><xsl:value-of select="@descricao"/></fo:block></fo:table-cell>
				<fo:table-cell><fo:block space-before="0.1cm" text-align="right" xsl:use-attribute-sets="textoDesc"><xsl:value-of select="@quantidade"/></fo:block></fo:table-cell>
				<fo:table-cell/>
			</fo:table-row>
	</xsl:template>

	<xsl:template match="localizacao">	
		<fo:block space-before="0.3cm" xsl:use-attribute-sets="titulo" text-align="left"><xsl:value-of select="@label"/></fo:block>
		<fo:table table-layout="fixed" space-before="0.2cm">
			<fo:table-column column-width="19.0cm"/>		
			<fo:table-body>		
				<!-- 
				<fo:table-row>
					<fo:table-cell/>
					<fo:table-cell>
						<fo:block space-after="0.1cm" text-align="right" xsl:use-attribute-sets="label">
							Abrangência:
						</fo:block>
					</fo:table-cell>
					<fo:table-cell>
							<fo:block xsl:use-attribute-sets="label" space-after="0.1cm" text-align="justify">
								<fo:leader leader-length="0.4cm"/>
								<xsl:value-of select="@abrangencia"/>
							</fo:block>		
					</fo:table-cell>
				</fo:table-row>
				 -->
				<fo:table-row>
					<fo:table-cell>	
						<xsl:apply-templates select="local" />
					</fo:table-cell>
				</fo:table-row>
			</fo:table-body>
		</fo:table>	
	</xsl:template>
	
	<xsl:template match="local">	
		<fo:block xsl:use-attribute-sets="label" space-after="0.1cm" text-align="justify">
				<!-- <fo:leader leader-length="0.4cm"/> -->
				- <xsl:value-of select="."/>
		</fo:block>								
	</xsl:template>
	<xsl:template match="pontosCriticos">	
		<fo:block space-before="0.3cm" xsl:use-attribute-sets="titulo" text-align="left"><xsl:value-of select="@label"/></fo:block>
		<xsl:apply-templates select="pontoCritico"/>		
	</xsl:template>
	
	<xsl:template match="pontoCritico">		
		<fo:table table-layout="fixed" space-before="0.2cm">
			<fo:table-column column-width="5.5cm"/>
			<fo:table-column column-width="13.5cm"/>
			<fo:table-body>						
				<fo:table-row>
					<fo:table-cell>
						<fo:block space-after="0.1cm" text-align="right" xsl:use-attribute-sets="label">
							Data da Identificação:
						</fo:block>
					</fo:table-cell>
					<fo:table-cell>
							<fo:block xsl:use-attribute-sets="textoDesc" space-after="0.1cm" text-align="justify">
								<xsl:value-of select="@dataIdentificacao"/>
							</fo:block>		
					</fo:table-cell>
				</fo:table-row>
				<fo:table-row>
					<fo:table-cell>
						<fo:block space-after="0.1cm" text-align="right" xsl:use-attribute-sets="label">
							Descrição:
						</fo:block>
					</fo:table-cell>
					<fo:table-cell>
							<fo:block xsl:use-attribute-sets="textoDesc" space-after="0.1cm" text-align="justify">
								<xsl:value-of select="@descricao"/>
							</fo:block>		
					</fo:table-cell>
				</fo:table-row>
				<fo:table-row>
					<fo:table-cell>
						<fo:block space-after="0.1cm" text-align="right" xsl:use-attribute-sets="label">
							Âmbito do Governo:
						</fo:block>
					</fo:table-cell>
					<fo:table-cell>
							<fo:block xsl:use-attribute-sets="textoDesc" space-after="0.1cm" text-align="justify">
								<xsl:value-of select="@ambitoDoGoverno"/>
							</fo:block>		
					</fo:table-cell>
				</fo:table-row>
				<fo:table-row>
					<fo:table-cell>
						<fo:block space-after="0.1cm" text-align="right" xsl:use-attribute-sets="label">
							Data Limite para Solução:
						</fo:block>
					</fo:table-cell>
					<fo:table-cell>
							<fo:block xsl:use-attribute-sets="textoDesc" space-after="0.1cm" text-align="justify">
								<xsl:value-of select="@dataLimiteSolucao"/>
							</fo:block>		
					</fo:table-cell>
				</fo:table-row>
				<fo:table-row>
					<fo:table-cell>
						<fo:block space-after="0.1cm" text-align="right" xsl:use-attribute-sets="label">
							Sugestão para Solução:
						</fo:block>
					</fo:table-cell>
					<fo:table-cell>
							<fo:block xsl:use-attribute-sets="textoDesc" space-after="0.1cm" text-align="justify">
								<xsl:value-of select="@sugestao"/>
							</fo:block>		
					</fo:table-cell>
				</fo:table-row>
				<fo:table-row>
					<fo:table-cell>
						<fo:block space-after="0.1cm" text-align="right" xsl:use-attribute-sets="label">
							Data da Solução:
						</fo:block>
					</fo:table-cell>
					<fo:table-cell>
							<fo:block xsl:use-attribute-sets="textoDesc" space-after="0.1cm" text-align="justify">
								<xsl:value-of select="@dataSolucao"/>
							</fo:block>		
					</fo:table-cell>
				</fo:table-row>		
				<fo:table-row>
					<fo:table-cell>
						<fo:block space-after="0.1cm" text-align="right" xsl:use-attribute-sets="label">
							Responsável pela Solução:
						</fo:block>
					</fo:table-cell>
					<fo:table-cell>
							<fo:block xsl:use-attribute-sets="textoDesc" space-after="0.1cm" text-align="justify">
								<xsl:value-of select="@responsavel"/>
							</fo:block>		
					</fo:table-cell>
				</fo:table-row>														
			</fo:table-body>			
		</fo:table>	
		
		<xsl:if test="apontamento">
			<fo:block xsl:use-attribute-sets="subtitulo" space-before="0.2cm" text-align="center">
				Apontamentos
			</fo:block>		
			<xsl:apply-templates select="apontamento" />
		</xsl:if>
		<fo:block space-before="0.2cm" border-bottom-color="black" border-bottom-width="0.05mm" border-bottom-style="solid">
		</fo:block>
	</xsl:template>	
	
	<xsl:template match="apontamento">
		<fo:table space-before="0.2cm" table-layout="fixed">
			<fo:table-column column-width="5.5cm"/>
			<fo:table-column column-width="13.5cm"/>
			<fo:table-body>		
				<fo:table-row>
				<fo:table-cell>
					<fo:block space-after="0.1cm" text-align="right" xsl:use-attribute-sets="label">
						Apontamento:
					</fo:block>
				</fo:table-cell>
				<fo:table-cell>
						<fo:block xsl:use-attribute-sets="textoDesc" space-after="0.1cm" text-align="justify">
							<xsl:value-of select="@dataInclusao"/> - <xsl:value-of select="@texto"/>
						</fo:block>		
				</fo:table-cell>
				</fo:table-row>
			</fo:table-body>
		</fo:table>
	</xsl:template>

	<xsl:template match="acoes">	
		<fo:block space-before="0.3cm" xsl:use-attribute-sets="titulo" text-align="left"><xsl:value-of select="@label"/></fo:block>
		<fo:block>
			<fo:table space-before="0.1cm" table-layout="fixed">
				<!-- 
				<fo:table-column column-width="1cm"/>
				 -->
				<fo:table-column column-width="3cm"/>
				<fo:table-column column-width="16cm"/>
				<!-- 
				<fo:table-column column-width="0.5cm"/>
				<fo:table-column column-width="3.3cm"/>
				<fo:table-column column-width="2.5cm"/>
				 -->
				<fo:table-header>		
					<fo:table-row>
						<!-- 
						<fo:table-cell/>
						 -->
						<fo:table-cell background-color="#CCC"><fo:block text-align="justify" xsl:use-attribute-sets="label">Data</fo:block></fo:table-cell>
						<fo:table-cell background-color="#CCC"><fo:block text-align="justify" xsl:use-attribute-sets="label">Descrição</fo:block></fo:table-cell>
						<!-- 
						<fo:table-cell background-color="#CCC"/>
						<fo:table-cell background-color="#CCC"><fo:block text-align="justify" xsl:use-attribute-sets="label">Ult. Manutenção</fo:block></fo:table-cell>
						<fo:table-cell background-color="#CCC"><fo:block text-align="justify" xsl:use-attribute-sets="label">Inclusão</fo:block></fo:table-cell>
						 -->
					</fo:table-row>
				</fo:table-header>				
				<fo:table-body>		
						<xsl:apply-templates select="acao"/>									
				</fo:table-body>
			</fo:table>					
		</fo:block>
	</xsl:template>
	
	<xsl:template match="acao">	
			<fo:table-row>
				<!--
				<fo:table-cell/>
				 -->
				<fo:table-cell><fo:block space-before="0.1cm" text-align="justify" xsl:use-attribute-sets="textoDesc"><xsl:value-of select="@data"/></fo:block></fo:table-cell>
				<fo:table-cell><fo:block space-before="0.1cm" text-align="justify" xsl:use-attribute-sets="textoDesc"><xsl:value-of select="@descricao"/></fo:block></fo:table-cell>
				<!--
				<fo:table-cell/>
				<fo:table-cell><fo:block space-before="0.1cm" text-align="justify" xsl:use-attribute-sets="textoDesc"><xsl:value-of select="@ultManutencao"/></fo:block></fo:table-cell>
				<fo:table-cell><fo:block space-before="0.1cm" text-align="justify" xsl:use-attribute-sets="textoDesc"><xsl:value-of select="@dataInclusao"/></fo:block></fo:table-cell>
				 -->
			</fo:table-row>
	</xsl:template>
</xsl:stylesheet>
