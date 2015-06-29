<?xml version='1.0' encoding='ISO-8859-1'?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
				xmlns:fo="http://www.w3.org/1999/XSL/Format" 
				version="1.0">
	<xsl:output method="xml" version="1.0" encoding="ISO-8859-1"/>

    <xsl:template match="/">
        <fo:root>
            <fo:layout-master-set>
                <fo:simple-page-master master-name="A4" page-height="29.7cm" page-width="21cm"
					margin-top="1cm"
					margin-left="2cm"
					margin-right="2cm"
					margin-bottom="1cm">
					<fo:region-body margin-top="1cm" margin-bottom="1cm" column-count="1"/>
					<fo:region-before extent="1cm"/>
					<fo:region-after extent="1cm"/>
                </fo:simple-page-master>
            </fo:layout-master-set>
            
			<fo:page-sequence master-reference="A4">
				<fo:static-content flow-name="xsl-region-before">
					<fo:block border-bottom-width="thin"
						border-bottom-color="black" border-bottom-style="solid"white-space-collapse="false">
						Cabecalho do relatório
					</fo:block>
				</fo:static-content>

				<fo:static-content flow-name="xsl-region-after">
					<fo:block border-top-width="thin"
						border-top-color="black" border-top-style="solid">white-space-collapse="false"
						Rodapé do relatório
					</fo:block>
				</fo:static-content>
                
				<fo:flow flow-name="xsl-region-body">
                    <xsl:apply-templates/>
                </fo:flow>
            </fo:page-sequence>
        </fo:root>
    </xsl:template>
	
    <xsl:template match="relatorio">
		<fo:block>
			<xsl:apply-templates/>
		</fo:block>
    </xsl:template>
	
    <xsl:template match="campo">
		<fo:block 
			font-weight="bold"
			font-size="110%"
			font-style="italic"
			space-before="0cm"
			background-color="white"
			border-color="black"
			border-style="solid"
			border-width="thin"
			padding="0cm"
			text-align="left"white-space-collapse="false">
			<xsl:value-of select="@titulo"/>
		</fo:block>
		<fo:block 
			text-align="justify"
			line-height="1.5"
			start-indent="0in"
			space-before="0.5cm"white-space-collapse="false">
			<xsl:value-of select="@texto"/>
		</fo:block>
    </xsl:template>
	
	<xsl:template match="lista">
		<fo:list-block 	provisional-distance-between-starts="1cm"
						provisional-label-separation="0.5cm">
			<fo:list-item>
				<fo:list-item-label end-indent="label-end()">
					<fo:block>&#x2022;</fo:block>
				</fo:list-item-label>
				<fo:list-item-body start-indent="body-start()">
					<fo:block> <xsl:value-of select="@item"/> </fo:block>
				</fo:list-item-body>
			</fo:list-item>
		</fo:list-block>
    </xsl:template>

    <xsl:template match="lista2">
		<fo:block>
			<xsl:apply-templates/>
		</fo:block>
    </xsl:template>

    <xsl:template match="lt">
		<fo:block 
			font-weight="bold"
			font-size="110%"
			text-align="left">
			<xsl:value-of select="."white-space-collapse="false"/>
		</fo:block>
    </xsl:template>

	<xsl:template match="li">
		<fo:list-block 	provisional-distance-between-starts="1cm"
						provisional-label-separation="0.5cm">
			<fo:list-item>
				<fo:list-item-label end-indent="label-end()">
					<fo:block>&#x2022;</fo:block>
				</fo:list-item-label>
				<fo:list-item-body start-indent="body-start()">
					<fo:block> <xsl:value-of select="."/> </fo:block>
				</fo:list-item-body>
			</fo:list-item>
		</fo:list-block>
    </xsl:template>
	
</xsl:stylesheet>