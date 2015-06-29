<?xml version="1.0" encoding="ISO-8859-1"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" 
	xmlns:fo="http://www.w3.org/1999/XSL/Format" version="1.0">
	
	<xsl:output method="xml" version="1.0" encoding="ISO-8859-1"/>
	
	<xsl:attribute-set name="label">
		<xsl:attribute name="font-size">10.0pt</xsl:attribute>
		<xsl:attribute name="font-family">sans-serif</xsl:attribute>
		<xsl:attribute name="font-weight">bold</xsl:attribute>		
	</xsl:attribute-set>	

	<xsl:attribute-set name="texto">
		<xsl:attribute name="font-size">10.0pt</xsl:attribute>
		<xsl:attribute name="font-family">sans-serif</xsl:attribute>
		<xsl:attribute name="font-weight">normal</xsl:attribute>		
	</xsl:attribute-set>	

	<xsl:attribute-set name="titulo">
		<xsl:attribute name="font-size">11.0pt</xsl:attribute>
		<xsl:attribute name="font-family">sans-serif</xsl:attribute>
		<xsl:attribute name="font-weight">bold</xsl:attribute>		
	</xsl:attribute-set>	

	<xsl:template match="relatorio">
		<fo:root>
			<fo:layout-master-set>
				<fo:simple-page-master master-name="A4_Retrato" page-height="29.8cm" page-width="21cm"
					margin-top="1cm" margin-bottom="1cm" margin-left="2cm" margin-right="2cm">
					<fo:region-body margin-top="2.5cm" margin-bottom="2.5cm"/>
					<fo:region-before extent="3.5cm"/>
					<fo:region-after extent="1cm"/>
				</fo:simple-page-master>
			</fo:layout-master-set>

			<fo:page-sequence master-reference="A4_Retrato" format="1">
				<fo:static-content flow-name="xsl-region-before">
					<fo:block text-align="center">
						<fo:table table-layout="fixed">
							<fo:table-column column-width="18cm"/>
							<fo:table-body>
								<fo:table-row>
									<fo:table-cell>
										<fo:block xsl:use-attribute-sets="label" text-align="center" border-bottom-color="black" border-bottom-width="0.1mm" border-bottom-style="solid">
											Relação de Acessos de Usuários no Sistema <xsl:value-of select="@titulo"/>
										</fo:block>
									</fo:table-cell>
								</fo:table-row>
								<fo:table-row>
									<fo:table-cell>
										<fo:block space-before="0.2cm"/>
									</fo:table-cell>
								</fo:table-row>
								<xsl:apply-templates select="filtro"/>
							</fo:table-body>
						</fo:table>
					</fo:block>
				</fo:static-content>

				<fo:static-content flow-name="xsl-region-after">
					<fo:table table-layout="fixed">
						<fo:table-column column-width="9cm"/>
						<fo:table-column column-width="9cm"/>
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
					<xsl:apply-templates select="controleAcesso"/>
					<fo:block id="last-page"/>
				</fo:flow>
			</fo:page-sequence>
		</fo:root>
	</xsl:template>
	
	<xsl:template match="filtro">
		<xsl:apply-templates select="campo"/>
	</xsl:template>
	
	<xsl:template match="campo">
		<fo:table-row>
			<fo:table-cell>
				<fo:block text-align="left" xsl:use-attribute-sets="label">
					<xsl:value-of select="@label"/>
					<xsl:value-of select="@valor"/>
				</fo:block>
			</fo:table-cell>
		</fo:table-row>
	</xsl:template>

	<xsl:template match="controleAcesso">
		<fo:table>
			<fo:table-column column-width="18cm"/>
			<fo:table-header>
				<fo:table-row>
					<fo:table-cell>
						<fo:block space-before="0.2cm" border-top-color="black" border-top-style="solid" border-top-width="0.1mm" border-bottom-color="black" border-bottom-style="solid" border-bottom-width="0.1mm">
							<fo:table>
								<fo:table-column column-width="8.5cm"/>
								<fo:table-column column-width="1cm"/>
								<fo:table-column column-width="8.5cm"/>
								<fo:table-body>
									<fo:table-row>
										<fo:table-cell background-color="#CCC">
											<fo:block text-align="center" xsl:use-attribute-sets="label">
												<xsl:value-of select="@coluna1"/>
											</fo:block>
										</fo:table-cell>
										<fo:table-cell background-color="#CCC"/>
										<fo:table-cell background-color="#CCC">
											<fo:block text-align="center" xsl:use-attribute-sets="label">
												<xsl:value-of select="@coluna2"/>
											</fo:block>
										</fo:table-cell>
									</fo:table-row>
								</fo:table-body>
							</fo:table>
						</fo:block>
					</fo:table-cell>
				</fo:table-row>
			</fo:table-header>
			<fo:table-body>
				<fo:table-row>
					<fo:table-cell>
						<xsl:apply-templates select="itemAcesso"/>
					</fo:table-cell>
				</fo:table-row>
			</fo:table-body>
		</fo:table>
	</xsl:template>
	
	<xsl:template match="itemAcesso">
		<fo:block border-top-color="black" border-top-style="solid" border-top-width="0.2mm">
			<fo:table>
				<fo:table-column column-width="8.5cm"/>
				<fo:table-column column-width="1cm"/>
				<fo:table-column column-width="8.5cm"/>
				<fo:table-body>
					<fo:table-row>
						<fo:table-cell>
							<fo:block text-align="left" xsl:use-attribute-sets="texto">
								<xsl:value-of select="@campo1"/>
							</fo:block>
						</fo:table-cell>
						<fo:table-cell />
						<fo:table-cell>
							<fo:block text-align="left" xsl:use-attribute-sets="texto">
								<xsl:value-of select="@campo2"/>
							</fo:block>
						</fo:table-cell>
					</fo:table-row>
				</fo:table-body>
			</fo:table>
		</fo:block>

	</xsl:template>
</xsl:stylesheet>