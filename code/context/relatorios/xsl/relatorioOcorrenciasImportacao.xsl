<?xml version="1.0" encoding="ISO-8859-1"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:fo="http://www.w3.org/1999/XSL/Format" version="1.0">

	<xsl:output method="xml" version="1.0" encoding="ISO-8859-1" />

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

	<xsl:attribute-set name="textoNaoSelecionado">
		<xsl:attribute name="font-size">10.0pt</xsl:attribute>
		<xsl:attribute name="font-family">sans-serif</xsl:attribute>
		<xsl:attribute name="font-weight">bold</xsl:attribute>
		<xsl:attribute name="color">silver</xsl:attribute>
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

	<xsl:attribute-set name="titulo_capa">
		<xsl:attribute name="font-size">16.0pt</xsl:attribute>
		<xsl:attribute name="font-family">verdana</xsl:attribute>
		<xsl:attribute name="font-weight">bold</xsl:attribute>		
	</xsl:attribute-set>	
	
	<xsl:template match="relatorio">
		<fo:root>
			<fo:layout-master-set>
				<fo:simple-page-master master-name="A4" page-height="29.8cm" page-width="21cm"
					margin-top="1cm" margin-bottom="1cm" margin-left="0.3cm" margin-right="0.3cm">
					<fo:region-body margin-top="1cm" margin-bottom="1cm"/>
					<fo:region-before extent="3cm"/>
					<fo:region-after extent="1cm"/>
				</fo:simple-page-master>
			</fo:layout-master-set>
			<fo:page-sequence master-reference="A4" format="1">

				<fo:static-content flow-name="xsl-region-before">
					<fo:block border-bottom-color="black" border-bottom-width="0.1mm" border-bottom-style="solid">
						<xsl:value-of select="@titulo"/>
					</fo:block>
				</fo:static-content>

				<fo:static-content flow-name="xsl-region-after">
					<fo:table table-layout="fixed">
						<fo:table-column column-width="10cm"/>
						<fo:table-column column-width="10cm"/>
						<fo:table-body>
							<fo:table-row>
								<fo:table-cell>
									<fo:block xsl:use-attribute-sets="label" text-align="start" border-top-color="black" border-top-width="0.1mm" border-top-style="solid">
										<xsl:value-of select="@dataHora"/>								
									</fo:block>	
								</fo:table-cell>
								<fo:table-cell>
									<fo:block xsl:use-attribute-sets="label" text-align="end" border-top-color="black" border-top-width="0.1mm" border-top-style="solid">
										Página <fo:page-number/> de <fo:page-number-citation ref-id="last-page"/>								
									</fo:block>
								</fo:table-cell>								
							</fo:table-row>																											
						</fo:table-body>
					</fo:table>
				</fo:static-content>

				<fo:flow flow-name="xsl-region-body">
					<xsl:apply-templates />
					<fo:block id="last-page" />
				</fo:flow>

			</fo:page-sequence>
		</fo:root>
	</xsl:template>

	<xsl:template match="ocorrencias">
		<fo:block space-before="1cm">
			<fo:table>
				<fo:table-column column-width="1cm"/>
				<fo:table-column column-width="3cm"/>
				<fo:table-column column-width="15cm"/>
				<fo:table-body>
					<fo:table-row>
						<fo:table-cell/>
						<fo:table-cell background-color="#CCC" border-width="0.2mm" border-after-style="solid" border-before-style="solid" border-end-style="solid" border-start-style="solid">
							<fo:block xsl:use-attribute-sets="label" text-align="center">
								Data
							</fo:block>
						</fo:table-cell>
						<fo:table-cell background-color="#CCC" border-width="0.2mm" border-after-style="solid" border-before-style="solid" border-end-style="solid" border-start-style="solid">
							<fo:block xsl:use-attribute-sets="label" text-align="center">
								Descrição
							</fo:block>
						</fo:table-cell>
					</fo:table-row>
					<xsl:apply-templates select="ocorrencia"/>
				</fo:table-body>
			</fo:table>	
		</fo:block>
		<fo:block space-before="1cm"/>
	</xsl:template>

	<xsl:template match="ocorrencia">
		<fo:table-row>
			<fo:table-cell/>
			<fo:table-cell border-width="0.2mm" border-after-style="solid" border-before-style="solid" border-end-style="solid" border-start-style="solid">
				<fo:block xsl:use-attribute-sets="label" text-align="center">
					<xsl:value-of select="@data"/>
				</fo:block>
			</fo:table-cell>
			<fo:table-cell border-width="0.2mm" border-after-style="solid" border-before-style="solid" border-end-style="solid" border-start-style="solid">
				<fo:block xsl:use-attribute-sets="label" text-align="justify">
					<xsl:value-of select="@descricao"/>
				</fo:block>
			</fo:table-cell>
		</fo:table-row>
	</xsl:template>
</xsl:stylesheet>
