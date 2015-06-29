<?xml version="1.0" encoding="ISO-8859-1"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" 
	xmlns:fo="http://www.w3.org/1999/XSL/Format" version="1.0">
	
	<xsl:output method="xml" version="1.0" encoding="ISO-8859-1"/>
	
	<xsl:attribute-set name="label">
		<xsl:attribute name="font-size">10.0pt</xsl:attribute>
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

<!-- Igor -->
<xsl:template match="processing-instruction('hard-pagebreak')">
   <fo:block break-before='page'white-space-collapse="false"/>
 </xsl:template>
<!-- \Igor -->

	<xsl:template match="relatorio">
		<fo:root>
			<fo:layout-master-set>
				<fo:simple-page-master master-name="A4" page-height="29.8cm" page-width="21cm"
					margin-top="1cm" margin-bottom="1cm" margin-left="1cm" margin-right="1cm">
					<fo:region-body margin-top="0.5cm" margin-bottom="2cm"/>
					<fo:region-before extent="1cm"/>
					<fo:region-after extent="1cm"/>
				</fo:simple-page-master>
			</fo:layout-master-set>
			<fo:page-sequence master-reference="A4" format="1">				

				<fo:static-content flow-name="xsl-region-before">
					<fo:block border-bottom-color="black" border-bottom-width="0.2mm" border-bottom-style="solid"white-space-collapse="false">
						<fo:table table-layout="fixed">
							<fo:table-column column-width="3.0cm"/>
							<fo:table-column column-width="16.0cm"/>
							<fo:table-body>
								<fo:table-row>
									<fo:table-cell>
									</fo:table-cell>
									<fo:table-cell>
										<fo:block xsl:use-attribute-sets="texto" text-align="end"white-space-collapse="false">
											<xsl:value-of select="@titulo"/>								
										</fo:block>
									</fo:table-cell>								
								</fo:table-row>																											
							</fo:table-body>
						</fo:table>	
					</fo:block>
<!--					<xsl:apply-templates select="acompanhamentos/hierarquia"/>				-->
				</fo:static-content>

				<fo:static-content flow-name="xsl-region-after">
					<fo:block border-top-color="black" border-top-width="0.2mm" border-top-style="solid" text-align="right"white-space-collapse="false">
						<fo:table table-layout="fixed">
							<fo:table-column column-width="8.0cm"/>
							<fo:table-column column-width="3.0cm"/>
							<fo:table-column column-width="8.0cm"/>
							<fo:table-body>
								<fo:table-row>
									<fo:table-cell>
										<fo:block xsl:use-attribute-sets="texto" text-align="start"white-space-collapse="false">
											<xsl:value-of select="@titulo"/>								
										</fo:block>	
									</fo:table-cell>
									<fo:table-cell>
										<fo:block xsl:use-attribute-sets="label" text-align="center"white-space-collapse="false">
											<xsl:value-of select="@datahora"/>								
										</fo:block>	
									</fo:table-cell>								
									<fo:table-cell>
										<fo:block xsl:use-attribute-sets="label" text-align="end"white-space-collapse="false">
											Página <fo:page-number/> de <fo:page-number-citation ref-id="last-page"/>								
										</fo:block>
									</fo:table-cell>								
								</fo:table-row>																											
							</fo:table-body>
						</fo:table>	
					</fo:block>
				</fo:static-content>

				<fo:flow flow-name="xsl-region-body">
<!--					<xsl:apply-templates select="acompanhamentos"/>-->
<!--					<xsl:apply-templates select="itens"/>-->
					<xsl:apply-templates select="itensGeral"/>
					<fo:block id="last-page"white-space-collapse="false"/>
				</fo:flow>
				
			</fo:page-sequence>
		</fo:root>
	</xsl:template>

	<xsl:template match="itensGeral">
		<fo:block break-before="page"white-space-collapse="false"/>
		<fo:table>
			<fo:table-column/>
			<fo:table-header>
				<fo:table-row>
					<fo:table-cell>
						<fo:block space-after="0.2cm"white-space-collapse="false">
						<xsl:apply-templates select="hierarquia"/>				
						</fo:block>
					</fo:table-cell>
				</fo:table-row>
			</fo:table-header>
			<fo:table-body>
				<fo:table-row>
					<fo:table-cell>
						<fo:block>
							<xsl:apply-templates select="acompanhamentos"/>
						</fo:block>
						<fo:block>
							<xsl:apply-templates select="itens"/>
						</fo:block>
					</fo:table-cell>
				</fo:table-row>
			</fo:table-body>
		</fo:table>
	</xsl:template>

	<xsl:template match="acompanhamentos">	
		<fo:block xsl:use-attribute-sets="titulo" text-align="left" space-before="0.5cm"white-space-collapse="false">
			Acompanhamentos
		</fo:block>
		<fo:table table-layout="fixed">
			<fo:table-column column-width="3.0cm"/>
			<fo:table-column column-width="0.5cm"/>
			<fo:table-column column-width="7.5cm"/>
			<fo:table-column column-width="3.0cm"/>
			<fo:table-column column-width="2.5cm"/>
			<fo:table-column column-width="2.5cm"/>
			<fo:table-header>
				<fo:table-row>
					<fo:table-cell></fo:table-cell>
					<fo:table-cell></fo:table-cell>
					<fo:table-cell></fo:table-cell>
					<fo:table-cell display-align="center" border-width="0.1mm" border-before-style="solid" border-after-style="solid" border-start-style="solid" border-end-style="solid"><fo:block text-align="center" xsl:use-attribute-sets="label"white-space-collapse="false">N.P.</fo:block></fo:table-cell>
					<fo:table-cell display-align="center" border-width="0.1mm" border-before-style="solid" border-after-style="solid" border-start-style="solid" border-end-style="solid"><fo:block text-align="center" xsl:use-attribute-sets="label"white-space-collapse="false">Órgão Responsável</fo:block></fo:table-cell>
					<fo:table-cell display-align="center" border-width="0.1mm" border-before-style="solid" border-after-style="solid" border-start-style="solid" border-end-style="solid"><fo:block text-align="center" xsl:use-attribute-sets="label"white-space-collapse="false"><xsl:value-of select="@mes"/></fo:block></fo:table-cell>
				</fo:table-row>
			</fo:table-header>
			<fo:table-body>
				<xsl:apply-templates select="itemAcompanhamento"/>		
			</fo:table-body>
		</fo:table>	
	</xsl:template>

	<xsl:template match="itemAcompanhamento">			
		<fo:table-row>
			<fo:table-cell display-align="center">
				<xsl:if test="@negrito='S'">
					<fo:block text-align="left" xsl:use-attribute-sets="texto"white-space-collapse="false">
						<fo:leader>
							<xsl:attribute name="leader-length">
								<xsl:value-of select="(@nivel * 3)"/>mm
							</xsl:attribute>											
						</fo:leader>
	<!--					<xsl:value-of select="@estrutura"/> - <xsl:value-of select="@nome"/>-->
						<xsl:value-of select="@estrutura"/>
					</fo:block>
				</xsl:if>
				<xsl:if test="@negrito='N'">
					<fo:block text-align="left" xsl:use-attribute-sets="label"white-space-collapse="false">
						<fo:leader>
							<xsl:attribute name="leader-length">
								<xsl:value-of select="(@nivel * 3)"/>mm
							</xsl:attribute>											
						</fo:leader>
	<!--					<xsl:value-of select="@estrutura"/> - <xsl:value-of select="@nome"/>-->
						<xsl:value-of select="@estrutura"/>
					</fo:block>
				</xsl:if>

			</fo:table-cell>
			<fo:table-cell display-align="center">
				<xsl:if test="@negrito='S'">
					<fo:block text-align="left" xsl:use-attribute-sets="texto"white-space-collapse="false">
						-
					</fo:block>
				</xsl:if>
				<xsl:if test="@negrito='N'">
					<fo:block text-align="left" xsl:use-attribute-sets="label"white-space-collapse="false">
						-
					</fo:block>
				</xsl:if>
			</fo:table-cell>
			<fo:table-cell display-align="center">
				<xsl:if test="@negrito='S'">
					<fo:block text-align="left" xsl:use-attribute-sets="texto"white-space-collapse="false">
						<xsl:value-of select="@nome"/>
					</fo:block>
				</xsl:if>			
				<xsl:if test="@negrito='N'">
					<fo:block text-align="left" xsl:use-attribute-sets="label"white-space-collapse="false">
						<xsl:value-of select="@nome"/>
					</fo:block>
				</xsl:if>
			</fo:table-cell>

			<fo:table-cell display-align="center" border-width="0.1mm" border-before-style="solid" border-after-style="solid" border-start-style="solid" border-end-style="solid">
				<fo:block text-align="center" xsl:use-attribute-sets="label"white-space-collapse="false">
					<xsl:apply-templates select="niveis"/>
				</fo:block>
			</fo:table-cell>
			<fo:table-cell display-align="center" border-width="0.1mm" border-before-style="solid" border-after-style="solid" border-start-style="solid" border-end-style="solid">
				<fo:block text-align="center" xsl:use-attribute-sets="label"white-space-collapse="false">
					<xsl:value-of select="@orgao"/>
				</fo:block>
			</fo:table-cell>
			<fo:table-cell display-align="center" border-width="0.1mm" border-before-style="solid" border-after-style="solid" border-start-style="solid" border-end-style="solid">
				<fo:block text-align="center" xsl:use-attribute-sets="label"white-space-collapse="false">
					<xsl:apply-templates select="mes"/>
				</fo:block>
			</fo:table-cell>
		</fo:table-row>
	</xsl:template>
	
	<xsl:template match="niveis">			
		<xsl:apply-templates select="nivel"/>
	</xsl:template>

	<xsl:template match="nivel">			
		<fo:external-graphic>    
			<xsl:attribute name="src">
				url('<xsl:value-of select="."/>')
			</xsl:attribute>
		</fo:external-graphic>
	</xsl:template>
	
	<xsl:template match="mes">			
		<xsl:apply-templates select="posicao" />
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
<!--		<xsl:if test="hierarquia">-->
<!--			<fo:block space-before="0.5cm" space-after="0.5cm" border-top-color="black" border-top-style="solid" border-top-width="0.2mm"white-space-collapse="false" />-->
<!--			<xsl:apply-templates select="hierarquia"/>-->
<!--		</xsl:if>-->
		<xsl:if test="avaliacoes">
			<xsl:apply-templates select="avaliacoes"/>
		</xsl:if>
		<xsl:if test="dadosGerais">
			<xsl:apply-templates select="dadosGerais"/>
		</xsl:if>

		<xsl:if test="localizacao">
			<xsl:apply-templates select="localizacao"/>
		</xsl:if>

		<xsl:if test="indicadores">
			<xsl:apply-templates select="indicadores"/>
		</xsl:if>

		<xsl:if test="evolucaoFinanceira">
			<xsl:apply-templates select="evolucaoFinanceira"/>
		</xsl:if>

		<xsl:if test="eventos">
			<xsl:apply-templates select="eventos"/>
		</xsl:if>

		<xsl:if test="pontosCriticos">
			<xsl:apply-templates select="pontosCriticos"/>
		</xsl:if>
	</xsl:template>

	<xsl:template match="hierarquia">			
		<fo:block text-align="left" xsl:use-attribute-sets="texto"white-space-collapse="false">
			<xsl:value-of select="@estrutura"/> <xsl:value-of select="@nome"/>
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
				<fo:block text-align="left" xsl:use-attribute-sets="texto"white-space-collapse="false">
					<fo:leader>
						<xsl:attribute name="leader-length">
							<xsl:value-of select="(@nivel * 3)"/>mm
						</xsl:attribute>											
					</fo:leader>	
					<xsl:value-of select="@nomeEstrutura"/>: <xsl:value-of select="@nome"/>
				</fo:block>
			</fo:table-cell>
		</fo:table-row>
	</xsl:template>
	
	<xsl:template match="avaliacoes">
		<fo:block space-before="0.3cm" xsl:use-attribute-sets="titulo" text-align="left"white-space-collapse="false">
			Avaliações
		</fo:block>
		<xsl:apply-templates select="avaliacao"/>
	</xsl:template>

	<xsl:template match="avaliacao">			
		<fo:table table-layout="fixed">
			<fo:table-column column-width="5.5cm"/>
			<fo:table-column column-width="13.5cm"/>
			<fo:table-body>
				<fo:table-row>
					<fo:table-cell display-align="center">
						<fo:block space-after="0.2cm" text-align="right" xsl:use-attribute-sets="label"white-space-collapse="false">
							<xsl:value-of select="@label"/>:
						</fo:block>
					</fo:table-cell>
					<fo:table-cell display-align="center">
						<fo:block space-after="0.2cm" text-align="left" xsl:use-attribute-sets="texto"white-space-collapse="false">
							<xsl:value-of select="@responsavel"/>
						</fo:block>
					</fo:table-cell>
				</fo:table-row>
				<fo:table-row>
					<fo:table-cell display-align="center">
						<fo:block space-after="0.2cm" text-align="right" xsl:use-attribute-sets="label"white-space-collapse="false">
							Função:
						</fo:block>
					</fo:table-cell>
					<fo:table-cell display-align="center">
						<fo:block space-after="0.2cm" text-align="left" xsl:use-attribute-sets="label"white-space-collapse="false">
							<xsl:value-of select="@funcao"/>
						</fo:block>
					</fo:table-cell>
				</fo:table-row>
				
				<xsl:if test = "@statusLiberado = 'S'">
					<fo:table-row>
						<fo:table-cell display-align="center">
							<fo:block space-after="0.2cm" text-align="right" xsl:use-attribute-sets="label"white-space-collapse="false">
								Situação:
							</fo:block>
						</fo:table-cell>
						<fo:table-cell display-align="center">
							<fo:block space-after="0.2cm" text-align="left" xsl:use-attribute-sets="texto"white-space-collapse="false">
								<xsl:value-of select="@situacao"/>
							</fo:block>
						</fo:table-cell>
					</fo:table-row>
					<fo:table-row>
						<fo:table-cell display-align="before">
							<fo:block space-after="0.2cm" text-align="right" xsl:use-attribute-sets="label"white-space-collapse="false">
								Cor:
							</fo:block>
						</fo:table-cell>
						<fo:table-cell display-align="before" space-after="0.7cm">
							<fo:block text-align="left"white-space-collapse="false">
								<fo:external-graphic>    
									<xsl:attribute name="src">
										url('<xsl:value-of select="@cor"/>')
									</xsl:attribute>
								</fo:external-graphic>
							</fo:block>
						</fo:table-cell>
					</fo:table-row>
					<fo:table-row>
						<fo:table-cell display-align="before">
							<fo:block space-after="0.2cm" text-align="right" xsl:use-attribute-sets="label"white-space-collapse="false">
								Descrição:
							</fo:block>
						</fo:table-cell>
						<fo:table-cell display-align="center">
							<fo:block space-after="0.2cm" text-align="left" xsl:use-attribute-sets="label"white-space-collapse="false">
								<xsl:value-of select="@descricao"/>
							</fo:block>
						</fo:table-cell>
					</fo:table-row>
					<fo:table-row>
						<fo:table-cell display-align="before">
							<fo:block space-after="0.2cm" text-align="right" xsl:use-attribute-sets="label"white-space-collapse="false">
								Complemento:
							</fo:block>
						</fo:table-cell> 
						<fo:table-cell display-align="center">
							<fo:block space-after="0.2cm" text-align="left" xsl:use-attribute-sets="label"white-space-collapse="false">
								<xsl:value-of select="@complemento"/>
							</fo:block>
						</fo:table-cell>
					</fo:table-row>
					<fo:table-row>
						<fo:table-cell display-align="before">
							<fo:block space-after="0.2cm" text-align="right" xsl:use-attribute-sets="label"white-space-collapse="false">
								Atualização:
							</fo:block>
						</fo:table-cell> 
						<fo:table-cell display-align="center">
							<fo:block space-after="0.2cm" text-align="left" xsl:use-attribute-sets="label"white-space-collapse="false">
								<xsl:value-of select="@dataUltimaAtualizacao"/>
							</fo:block>
						</fo:table-cell>
					</fo:table-row>
				</xsl:if>
				
				<xsl:if test = "@statusLiberado = 'N'">
					<fo:table-row>
						<fo:table-cell display-align="center" number-columns-spanned="2">
							<fo:block text-align="center" xsl:use-attribute-sets="texto"white-space-collapse="false">
								(Acompanhamento Não Liberado)
							</fo:block>
						</fo:table-cell>
					</fo:table-row>
				</xsl:if>
				<fo:table-row>
					<fo:table-cell display-align="center">
						<fo:block space-after="0.5cm" text-align="center" xsl:use-attribute-sets="texto"white-space-collapse="false">
						</fo:block>
					</fo:table-cell>
				</fo:table-row>
			</fo:table-body>
		</fo:table>	
	</xsl:template>

	<xsl:template match="dadosGerais">
		<fo:block space-before="0.3cm" xsl:use-attribute-sets="titulo" text-align="left"white-space-collapse="false">
			Dados Gerais
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
				<fo:block space-after="0.2cm" text-align="right" xsl:use-attribute-sets="label"white-space-collapse="false">
					<xsl:value-of select="@label"/>: </fo:block>
				</fo:table-cell>
			<fo:table-cell display-align="before">
				<fo:block space-after="0.2cm" text-align="left" xsl:use-attribute-sets="textoDesc"white-space-collapse="false">
					<xsl:value-of select="@conteudo"/></fo:block>
				</fo:table-cell>
		</fo:table-row>
	</xsl:template>

	<xsl:template match="localizacao">
		<fo:block space-before="0.3cm" xsl:use-attribute-sets="titulo" text-align="left">white-space-collapse="false"
			Localização
		</fo:block>
		<fo:block space-before="0.3cm" xsl:use-attribute-sets="label" text-align="left"white-space-collapse="false">
			Abrangência: <xsl:value-of select="@abrangencia" />
		</fo:block>
		<fo:block space-before="0.3cm" xsl:use-attribute-sets="label" text-align="left"white-space-collapse="false">
			Locais:
		</fo:block>
		<xsl:apply-templates select="locais"/>		
	</xsl:template>

	<xsl:template match="locais">
		<fo:block xsl:use-attribute-sets="label" text-align="left"white-space-collapse="false">
			<xsl:value-of select="@local" />
		</fo:block>
	</xsl:template>

	<xsl:template match="indicadores">			
		<fo:block space-before="0.3cm" xsl:use-attribute-sets="titulo" text-align="left"white-space-collapse="false">
			Indicadores de Resultado
		</fo:block>
		<fo:table table-layout="fixed">
			<fo:table-column column-width="4.0cm"/>
			<fo:table-column column-width="2.0cm"/>
			<fo:table-column column-width="0.3cm"/>
			<xsl:if test = "@ano1 != ''">
				<fo:table-column column-width="1.9cm"/>
			</xsl:if>
			<xsl:if test = "@ano2 != ''">
				<fo:table-column column-width="1.9cm"/>
			</xsl:if>
			<fo:table-column column-width="1.9cm"/>
			<fo:table-column column-width="2.0cm"/>
			<fo:table-column column-width="3.5cm"/>
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
						<fo:block text-align="left" xsl:use-attribute-sets="textoMenorNegrito"white-space-collapse="false">Projeção na Data Final</fo:block>
						<fo:block text-align="left" xsl:use-attribute-sets="textoMenorNegrito"white-space-collapse="false">Situação Prevista</fo:block>
					</fo:table-cell>
					<fo:table-cell display-align="before">
						<fo:block text-align="center" xsl:use-attribute-sets="textoMenorNegrito"white-space-collapse="false">%</fo:block>
					</fo:table-cell>
				</fo:table-row>
			</fo:table-header>
			<fo:table-body>
				<xsl:apply-templates select="indicador"/>		
			</fo:table-body>
		</fo:table>
	</xsl:template>

	<xsl:template match="indicador">
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
				<fo:block text-align="left" xsl:use-attribute-sets="textoMenor"white-space-collapse="false">
					<xsl:value-of select="@projecao"/>
				</fo:block>
			</fo:table-cell>
			<fo:table-cell display-align="before">
				<fo:block text-align="center" xsl:use-attribute-sets="textoMenor"white-space-collapse="false">
					<xsl:value-of select="@percentual"/>
				</fo:block>
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
				<fo:block border-bottom-color="black" border-bottom-width="0.1mm" border-bottom-style="solid"white-space-collapse="false">
				</fo:block>
			</fo:table-cell>
		</fo:table-row>
	</xsl:template>

	<xsl:template match="evolucaoFinanceira">			
		<fo:block space-before="0.3cm" xsl:use-attribute-sets="titulo" text-align="left"white-space-collapse="false">
			Evolução Financeira
		</fo:block>
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
		<xsl:apply-templates select="subTotalFonte"/>		
	</xsl:template>
	
	<xsl:template match="subTotalFonte">			
		<fo:table-row>
			<fo:table-cell display-align="center">
				<fo:block text-align="center" xsl:use-attribute-sets="textoMenorNegrito"white-space-collapse="false">
				</fo:block>
			</fo:table-cell>
			<fo:table-cell display-align="center">
				<fo:block text-align="left" xsl:use-attribute-sets="textoMenorNegrito"white-space-collapse="false">
					Sub-Total
				</fo:block>
			</fo:table-cell>

			<xsl:apply-templates select="subTotal"/>
		</fo:table-row>
	</xsl:template>
	
	<xsl:template match="subTotal">
		<fo:table-cell display-align="center">
			<fo:block text-align="right" xsl:use-attribute-sets="textoMenorNegrito"white-space-collapse="false">
				R$ <xsl:value-of select="@valor"/>
			</fo:block>
		</fo:table-cell>
	</xsl:template>

	<xsl:template match="recurso">			
		<fo:table-row>
	
			<xsl:if test="position() = '1'">
				<fo:table-cell display-align="center">
					<fo:block text-align="left" xsl:use-attribute-sets="textoMenor"white-space-collapse="false">
						<xsl:value-of select="../@nome"/>
					</fo:block>
				</fo:table-cell>
			</xsl:if>
			<xsl:if test="position() != '1'">
				<fo:table-cell display-align="center">
					<fo:block text-align="left" xsl:use-attribute-sets="textoMenor"white-space-collapse="false">
					</fo:block>
				</fo:table-cell>
			</xsl:if>
	
			<fo:table-cell display-align="center">
				<fo:block text-align="left" xsl:use-attribute-sets="textoMenor"white-space-collapse="false">
					<xsl:value-of select="@nome"/>
				</fo:block>
			</fo:table-cell>
			
			<xsl:apply-templates select="rec"/>
		</fo:table-row>
	</xsl:template>
	
	<xsl:template match="rec">
		<fo:table-cell display-align="center">
			<fo:block text-align="right" xsl:use-attribute-sets="textoMenor"white-space-collapse="false">
				R$ <xsl:value-of select="@valor"/>
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
			<fo:block text-align="right" xsl:use-attribute-sets="textoMenorNegrito"white-space-collapse="false">
				R$ <xsl:value-of select="@valor"/>
			</fo:block>
		</fo:table-cell>
	</xsl:template>

	<xsl:template match="eventos">	
		<fo:block space-before="0.3cm" xsl:use-attribute-sets="titulo"white-space-collapse="false">Eventos</fo:block>
		<fo:block>
			<fo:table space-before="0.2cm" table-layout="fixed">
				<fo:table-column column-width="0.4cm"/>
				<fo:table-column column-width="2.3cm"/>
				<fo:table-column column-width="13.3cm"/>
				<fo:table-header>		
					<fo:table-row>
						<fo:table-cell/>
						<fo:table-cell background-color="#CCC"><fo:block text-align="justify" xsl:use-attribute-sets="label"white-space-collapse="false">Data</fo:block></fo:table-cell>
						<fo:table-cell background-color="#CCC"><fo:block text-align="justify" xsl:use-attribute-sets="label"white-space-collapse="false">Descrição</fo:block></fo:table-cell>
					</fo:table-row>
				</fo:table-header>				
				<fo:table-body>		
						<xsl:apply-templates select="evento"/>									
				</fo:table-body>
			</fo:table>					
		</fo:block>
	</xsl:template>
	
	<xsl:template match="evento">	
			<fo:table-row>
				<fo:table-cell/>
				<fo:table-cell><fo:block space-before="0.2cm" text-align="justify" xsl:use-attribute-sets="textoDesc"white-space-collapse="false"><xsl:value-of select="@data"/></fo:block></fo:table-cell>
				<fo:table-cell><fo:block space-before="0.2cm" text-align="justify" xsl:use-attribute-sets="textoDesc"white-space-collapse="false"><xsl:value-of select="@descricao"/></fo:block></fo:table-cell>
			</fo:table-row>
	</xsl:template>

	<xsl:template match="pontosCriticos">	
		<fo:block xsl:use-attribute-sets="titulo" space-before="0.3cm"white-space-collapse="false">Pontos Críticos</fo:block>
		<xsl:apply-templates select="pontoCritico"/>		
	</xsl:template>
	
	<xsl:template match="pontoCritico">		
		<fo:table table-layout="fixed" space-before="0.3cm">
			<fo:table-column column-width="0.3cm"/>
			<fo:table-column column-width="5.0cm"/>
			<fo:table-column column-width="10.0cm"/>		
			<fo:table-body>						
				<fo:table-row>
					<fo:table-cell/>
					<fo:table-cell>
						<fo:block space-after="0.2cm" text-align="right" xsl:use-attribute-sets="label"white-space-collapse="false">
							Data da Identificação:
						</fo:block>
					</fo:table-cell>
					<fo:table-cell>
							<fo:block xsl:use-attribute-sets="textoDesc" space-after="0.2cm" text-align="justify"white-space-collapse="false">
								<fo:leader leader-length="0.4cm"/>
								<xsl:value-of select="@dataIdentificacao"/>
							</fo:block>		
					</fo:table-cell>
				</fo:table-row>
				<fo:table-row>
					<fo:table-cell/>
					<fo:table-cell>
						<fo:block space-after="0.2cm" text-align="right" xsl:use-attribute-sets="label"white-space-collapse="false">
							Descrição:
						</fo:block>
					</fo:table-cell>
					<fo:table-cell>
							<fo:block xsl:use-attribute-sets="textoDesc" space-after="0.2cm" text-align="justify"white-space-collapse="false">
								<fo:leader leader-length="0.4cm"/>
								<xsl:value-of select="@descricao"/>
							</fo:block>		
					</fo:table-cell>
				</fo:table-row>
				<fo:table-row>
					<fo:table-cell/>
					<fo:table-cell>
						<fo:block space-after="0.2cm" text-align="right" xsl:use-attribute-sets="label"white-space-collapse="false">
							Âmbito do Governo
						</fo:block>
					</fo:table-cell>
					<fo:table-cell>
							<fo:block xsl:use-attribute-sets="textoDesc" space-after="0.2cm" text-align="justify"white-space-collapse="false">
								<fo:leader leader-length="0.4cm"/>
								<xsl:value-of select="@ambitoDoGoverno"/>
							</fo:block>		
					</fo:table-cell>
				</fo:table-row>
				<fo:table-row>
					<fo:table-cell/>
					<fo:table-cell>
						<fo:block space-after="0.2cm" text-align="right" xsl:use-attribute-sets="label"white-space-collapse="false">
							Data Limite para Solução:
						</fo:block>
					</fo:table-cell>
					<fo:table-cell>
							<fo:block xsl:use-attribute-sets="textoDesc" space-after="0.2cm" text-align="justify"white-space-collapse="false">
								<fo:leader leader-length="0.4cm"/>
								<xsl:value-of select="@dataLimiteSolucao"/>
							</fo:block>		
					</fo:table-cell>
				</fo:table-row>
				<fo:table-row>
					<fo:table-cell/>
					<fo:table-cell>
						<fo:block space-after="0.2cm" text-align="right" xsl:use-attribute-sets="label"white-space-collapse="false">
							Sugestão para Solução:
						</fo:block>
					</fo:table-cell>
					<fo:table-cell>
							<fo:block xsl:use-attribute-sets="textoDesc" space-after="0.2cm" text-align="justify"white-space-collapse="false">
								<fo:leader leader-length="0.4cm"/>
								<xsl:value-of select="@sugestao"/>
							</fo:block>		
					</fo:table-cell>
				</fo:table-row>
				<fo:table-row>
					<fo:table-cell/>
					<fo:table-cell>
						<fo:block space-after="0.2cm" text-align="right" xsl:use-attribute-sets="label"white-space-collapse="false">
							Data da Solução:
						</fo:block>
					</fo:table-cell>
					<fo:table-cell>
							<fo:block xsl:use-attribute-sets="textoDesc" space-after="0.2cm" text-align="justify"white-space-collapse="false">
								<fo:leader leader-length="0.4cm"/>
								<xsl:value-of select="@dataSolucao"/>
							</fo:block>		
					</fo:table-cell>
				</fo:table-row>		
				<fo:table-row>
					<fo:table-cell/>
					<fo:table-cell>
						<fo:block space-after="0.2cm" text-align="right" xsl:use-attribute-sets="label"white-space-collapse="false">
							Responsável pela Solução:
						</fo:block>
					</fo:table-cell>
					<fo:table-cell>
							<fo:block xsl:use-attribute-sets="textoDesc" space-after="0.2cm" text-align="justify"white-space-collapse="false">
								<fo:leader leader-length="0.4cm"/>
								<xsl:value-of select="@responsavel"/>
							</fo:block>		
					</fo:table-cell>
				</fo:table-row>														
			</fo:table-body>			
		</fo:table>	
		
		<fo:block start-indent="0.6cm" xsl:use-attribute-sets="titulo" space-before="0.4cm"white-space-collapse="false">
			Apontamentos
		</fo:block>		
		<xsl:apply-templates select="apontamento" />
	</xsl:template>	
	
	<xsl:template match="apontamento">
		<fo:table space-before="0.4cm" table-layout="fixed">
			<fo:table-column column-width="0.3cm"/>
			<fo:table-column column-width="5.0cm"/>
			<fo:table-column column-width="10.0cm"/>		
			<fo:table-body>		
				<fo:table-row>
				<fo:table-cell/>
				<fo:table-cell>
					<fo:block space-after="0.2cm" text-align="right" xsl:use-attribute-sets="label"white-space-collapse="false">
						Apontamento:
					</fo:block>
				</fo:table-cell>
				<fo:table-cell>
						<fo:block xsl:use-attribute-sets="textoDesc" space-after="0.2cm" text-align="justify"white-space-collapse="false">
							<fo:leader leader-length="0.4cm"/>
							<xsl:value-of select="@dataInclusao"/> - <xsl:value-of select="@texto"/>
						</fo:block>		
				</fo:table-cell>
				</fo:table-row>
			</fo:table-body>
		</fo:table>
	</xsl:template>
	
</xsl:stylesheet>
