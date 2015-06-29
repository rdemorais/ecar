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
	
	<xsl:template match="layout">
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
					<fo:block border-top-color="black" border-top-width="0.1mm" border-top-style="solid" text-align="right">
					</fo:block>
				</fo:static-content>

				<fo:flow flow-name="xsl-region-body">
					<xsl:apply-templates />
					<fo:block id="last-page" />
				</fo:flow>

			</fo:page-sequence>
		</fo:root>
	</xsl:template>

	<xsl:template match="tr">
		<fo:block xsl:use-attribute-sets="label" text-align="justify">
			TR<xsl:value-of select="@numero" />
		</fo:block>
		<fo:block>
			<fo:table>
				<fo:table-column column-width="5cm"/>
				<fo:table-column column-width="3cm"/>
				<fo:table-column column-width="3cm"/>
				<fo:table-column column-width="3cm"/>
				<fo:table-column column-width="5cm"/>
				<fo:table-body>
					<xsl:apply-templates select="campo" />
				</fo:table-body>
			</fo:table>	
		</fo:block>
		<fo:block space-before="1cm"/>
		
		<xsl:if test="@mostrarObs = 'S'">
		<fo:block xsl:use-attribute-sets="label" text-align="justify">
			* O Conteúdo dos valores está representado por 999999999999900 onde, nos 14 dígitos, os "noves" representam o valor inteiro e os "zeros" representam as casas decimais do valor.
			* A descrição da conta contábil deverá ser gerada em caixa alta.
		</fo:block>
		<fo:block xsl:use-attribute-sets="label" text-align="left">
			** O nome do arquivo foi gerado automaticamente, sendo: [nome_do_arquivo]_[sigla_do_sistema]_[ddmmaaaahhmmssSSS]_export.txt
		</fo:block>
		</xsl:if>
	</xsl:template>

	<xsl:template match="campo">
		<fo:table-row>
			<fo:table-cell border-width="0.2mm" border-after-style="solid" border-before-style="solid" border-end-style="solid" border-start-style="solid">
				<fo:block xsl:use-attribute-sets="label" text-align="left">
					<xsl:value-of select="@nome" />
				</fo:block>
			</fo:table-cell>
			<fo:table-cell border-width="0.2mm" border-after-style="solid" border-before-style="solid" border-end-style="solid" border-start-style="solid">
				<fo:block xsl:use-attribute-sets="label" text-align="left">
					<xsl:value-of select="@posIni" />
				</fo:block>
			</fo:table-cell>
			<fo:table-cell border-width="0.2mm" border-after-style="solid" border-before-style="solid" border-end-style="solid" border-start-style="solid">
				<fo:block xsl:use-attribute-sets="label" text-align="left">
					<xsl:value-of select="@tamanho" />
				</fo:block>
			</fo:table-cell>
			<fo:table-cell border-width="0.2mm" border-after-style="solid" border-before-style="solid" border-end-style="solid" border-start-style="solid">
				<fo:block xsl:use-attribute-sets="label" text-align="left">
					<xsl:value-of select="@tipo" />
				</fo:block>
			</fo:table-cell>
			<fo:table-cell border-width="0.2mm" border-after-style="solid" border-before-style="solid" border-end-style="solid" border-start-style="solid">
				<fo:block xsl:use-attribute-sets="label" text-align="left">
					<xsl:value-of select="@conteudo" />
				</fo:block>
			</fo:table-cell>
		</fo:table-row>
	</xsl:template>

</xsl:stylesheet>
