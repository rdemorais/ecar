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
						<fo:table table-layout="fixed">
							<fo:table-column/>
							<fo:table-body>
								<fo:table-row>
									<fo:table-cell>
										<fo:block xsl:use-attribute-sets="titulo" text-align="center">
											<xsl:value-of select="@titulo" />
										</fo:block>
									</fo:table-cell>
								</fo:table-row>
<!--								<fo:table-row>-->
<!--									<fo:table-cell>-->
<!--										<fo:block xsl:use-attribute-sets="textoDesc" text-align="center">-->
<!--											<xsl:value-of select="@subtitulo" />-->
<!--										</fo:block>-->
<!--									</fo:table-cell>-->
<!--								</fo:table-row>-->
							</fo:table-body>
						</fo:table>
					</fo:block>
				</fo:static-content>

				<fo:static-content flow-name="xsl-region-after">
					<fo:block border-top-color="black" border-top-width="0.1mm" border-top-style="solid" text-align="right">
						<fo:table table-layout="fixed">
							<fo:table-column />
							<fo:table-column />
							<fo:table-body>
								<fo:table-row>
									<fo:table-cell>
										<fo:block xsl:use-attribute-sets="label" text-align="start">
											<xsl:value-of select="@datahora" />
										</fo:block>
									</fo:table-cell>
									<fo:table-cell>
										<fo:block xsl:use-attribute-sets="label" text-align="end">
											Página
											<fo:page-number />
											de
											<fo:page-number-citation ref-id="last-page" />
										</fo:block>
									</fo:table-cell>
								</fo:table-row>
							</fo:table-body>
						</fo:table>
					</fo:block>
				</fo:static-content>

				<fo:flow flow-name="xsl-region-body">
					<xsl:apply-templates />
					<fo:block id="last-page" />
				</fo:flow>

			</fo:page-sequence>
		</fo:root>
	</xsl:template>

	<xsl:template match="capa">
		<fo:block break-before="page">
			<fo:block xsl:use-attribute-sets="titulo_capa" text-align="center" space-before="10cm">
				Estrutura do <xsl:value-of select="@titulo"/>								
			</fo:block>
			<fo:table>
				<fo:table-column column-width="3cm"/>
				<fo:table-column />
				<fo:table-body>
					<fo:table-row>
						<fo:table-cell />
						<fo:table-cell>
<!--							<fo:block xsl:use-attribute-sets="label" text-align="justify">-->
<!--								Tipo de Relatório: <xsl:value-of select="@tipoRelatorio" />-->
<!--							</fo:block>-->
							<fo:block xsl:use-attribute-sets="label" text-align="justify" space-before="3cm">
								Órgão: <xsl:value-of select="@orgao" />
							</fo:block>
							<fo:block xsl:use-attribute-sets="label" text-align="justify">
								Itens selecionados: <xsl:value-of select="@tituloItens" />
							</fo:block>
						</fo:table-cell>
					</fo:table-row>
				</fo:table-body>
			</fo:table>
			<xsl:apply-templates select="filtros" />
		</fo:block>
	</xsl:template>

	<xsl:template match="filtros">
		<fo:block xsl:use-attribute-sets="label" text-align="justify" space-before="0.5cm">
			<fo:table>
				<fo:table-column column-width="3cm"/>
				<xsl:if test="@estilo='CS'">
					<fo:table-column column-width="13cm"/>
					<fo:table-column column-width="1cm"/>
					<fo:table-column column-width="1cm"/>
				</xsl:if>
				<xsl:if test="@estilo='SI'">
					<fo:table-column column-width="15cm"/>
				</xsl:if>
				<fo:table-body>
					<fo:table-row>
						<fo:table-cell />
						<fo:table-cell border-width="0.2mm" border-after-style="solid" border-before-style="solid" border-end-style="solid" border-start-style="solid" background-color="#CCC"><fo:block xsl:use-attribute-sets="label" text-align="justify"><xsl:value-of select="@tipo" /></fo:block></fo:table-cell>
						<xsl:if test="@estilo='CS'">
							<fo:table-cell border-width="0.2mm" border-after-style="solid" border-before-style="solid" border-end-style="solid" border-start-style="solid" background-color="#CCC"><fo:block xsl:use-attribute-sets="label" text-align="justify">Com</fo:block></fo:table-cell>
							<fo:table-cell border-width="0.2mm" border-after-style="solid" border-before-style="solid" border-end-style="solid" border-start-style="solid" background-color="#CCC"><fo:block xsl:use-attribute-sets="label" text-align="justify">Sem</fo:block></fo:table-cell>
						</xsl:if>
					</fo:table-row>

					<xsl:apply-templates select="filtro" />
				</fo:table-body>
			</fo:table>	
		</fo:block>
	</xsl:template>

	<xsl:template match="filtro">
			<fo:table-row>
				<fo:table-cell />
				<fo:table-cell border-width="0.2mm" border-after-style="solid" border-before-style="solid" border-end-style="solid" border-start-style="solid"><fo:block xsl:use-attribute-sets="label" text-align="justify"><xsl:value-of select="@valor"/></fo:block></fo:table-cell>
				<xsl:if test="@tipo='C'">
					<fo:table-cell border-width="0.2mm" border-after-style="solid" border-before-style="solid" border-end-style="solid" border-start-style="solid"><fo:block xsl:use-attribute-sets="label" text-align="center">X</fo:block></fo:table-cell>
					<fo:table-cell border-width="0.2mm" border-after-style="solid" border-before-style="solid" border-end-style="solid" border-start-style="solid"/>
				</xsl:if>
				<xsl:if test="@tipo='S'">
					<fo:table-cell border-width="0.2mm" border-after-style="solid" border-before-style="solid" border-end-style="solid" border-start-style="solid"/>
					<fo:table-cell border-width="0.2mm" border-after-style="solid" border-before-style="solid" border-end-style="solid" border-start-style="solid"><fo:block xsl:use-attribute-sets="label" text-align="center">X</fo:block></fo:table-cell>
				</xsl:if>
			</fo:table-row>
	</xsl:template>

	<xsl:template match="item">
		<fo:block break-before="page">
			<xsl:apply-templates />
		</fo:block>
	</xsl:template>

	<xsl:template name="imagem">
        <xsl:param name="inicial"/>
        <xsl:param name="total"/>
        <xsl:if test="$inicial &lt; $total">
				<fo:external-graphic src="url('images/icon_ident_branco.gif')"/>            
				<xsl:call-template name="imagem">
			            <xsl:with-param name="inicial"><xsl:value-of select="$inicial + 1"/></xsl:with-param>
			            <xsl:with-param name="total"><xsl:value-of select="$total"/></xsl:with-param>
				</xsl:call-template>		
        </xsl:if>
    </xsl:template>

	<xsl:template match="estrutura">
		<fo:table>
			<fo:table-column column-width="5.5cm"/>
			<fo:table-column column-width="14cm"/>
			<fo:table-body>
				<fo:table-row>
					<fo:table-cell>
						<xsl:if test="@itemSelecionado='S'">
							<fo:block space-before="0.1cm" text-align="left" xsl:use-attribute-sets="texto">
								<xsl:call-template name="imagem">
					                  <xsl:with-param name="total"><xsl:value-of select="@nivel"/></xsl:with-param>								                    
						              <xsl:with-param name="inicial">1</xsl:with-param>								
								</xsl:call-template>
								<fo:external-graphic src="url('images/icon_ident_preto.gif')"/>	
								<xsl:value-of select="@nome"/>: 
							</fo:block>
						</xsl:if>
						<xsl:if test="@itemSelecionado='N'">
							<fo:block space-before="0.1cm" text-align="left" xsl:use-attribute-sets="textoNaoSelecionado">
								<xsl:call-template name="imagem">
					                  <xsl:with-param name="total"><xsl:value-of select="@nivel"/></xsl:with-param>								                    
						              <xsl:with-param name="inicial">1</xsl:with-param>								
								</xsl:call-template>
								<fo:external-graphic src="url('images/icon_ident_preto.gif')"/>	
								<xsl:value-of select="@nome"/>: 
							</fo:block>
						</xsl:if>
					</fo:table-cell>
					<fo:table-cell>
						<xsl:if test="@itemSelecionado='S'">
							<fo:block space-before="0.1cm" text-align="left" xsl:use-attribute-sets="texto">
								<xsl:value-of select="@descricao"/>
								<fo:block text-align="left" xsl:use-attribute-sets="textoDesc">
									<xsl:apply-templates/>
								</fo:block>
							</fo:block>
						</xsl:if>
						<xsl:if test="@itemSelecionado='N'">
							<fo:block space-before="0.1cm" text-align="left" xsl:use-attribute-sets="textoNaoSelecionado">
								<xsl:value-of select="@descricao"/>
								<fo:block text-align="left" xsl:use-attribute-sets="textoDesc">
									<xsl:apply-templates/>
								</fo:block>
							</fo:block>
						</xsl:if>
					</fo:table-cell>
				</fo:table-row>
			</fo:table-body>
		</fo:table>
	</xsl:template>

	<xsl:template match="campos">
		<xsl:apply-templates />
	</xsl:template>

	<xsl:template match="campo">
		<fo:table>
			<fo:table-column column-width="4cm"/>
			<fo:table-column column-width="10cm"/>
<!--			<fo:table-column/>-->
			<fo:table-body>
				<fo:table-row>
<!--					<fo:table-cell />-->
					<fo:table-cell>
						<fo:block text-align="right">
							<xsl:value-of select="@label" />
						</fo:block>
					</fo:table-cell>
					<fo:table-cell>
						<fo:block white-space-collapse="false">
							<xsl:value-of select="@valor" />
						</fo:block>
					</fo:table-cell>
				</fo:table-row>
			</fo:table-body>
		</fo:table>
	</xsl:template>

	<xsl:template match="totalizadores">
		<fo:block space-before="1cm" border-top-color="black" border-top-style="solid" border-top-width="0.1mm"></fo:block>
		<fo:table>
			<fo:table-column column-width="2cm"/>
			<fo:table-column />
			<fo:table-body>
				<fo:table-row>
					<fo:table-cell/>
					<fo:table-cell>
						<fo:block space-before="0.1cm" space-after="0.1cm" text-align="left" xsl:use-attribute-sets="texto">
							Total de Itens por Nível da Estrutura:
						</fo:block>
					</fo:table-cell>
				</fo:table-row>
			</fo:table-body>
		</fo:table>
		<xsl:apply-templates select="totalizador" />
		<xsl:apply-templates select="total" />
	</xsl:template>

	<xsl:template match="totalizador">
		<fo:table>
			<fo:table-column column-width="3cm"/>
			<fo:table-column column-width="3cm"/>
			<fo:table-column column-width="3cm"/>
			<fo:table-body>
				<fo:table-row>
					<fo:table-cell />
					<fo:table-cell>
						<fo:block space-before="0.1cm" text-align="left" xsl:use-attribute-sets="label">
							<xsl:value-of select="@nome"/>
						</fo:block>
					</fo:table-cell>
					<fo:table-cell>
						<fo:block space-before="0.1cm" text-align="right" xsl:use-attribute-sets="label">
							<xsl:value-of select="@valor"/>
						</fo:block>
					</fo:table-cell>
				</fo:table-row>
			</fo:table-body>
		</fo:table>
	</xsl:template>

	<xsl:template match="total">
		<fo:table>
			<fo:table-column column-width="3cm"/>
			<fo:table-column column-width="3cm"/>
			<fo:table-column column-width="3cm"/>
			<fo:table-body>
				<fo:table-row>
					<fo:table-cell/>
					<fo:table-cell>
						<fo:block space-before="0.3cm" text-align="left" xsl:use-attribute-sets="texto">
							<xsl:value-of select="@nome"/>
						</fo:block>
					</fo:table-cell>
					<fo:table-cell>
						<fo:block space-before="0.3cm" text-align="right" xsl:use-attribute-sets="texto">
							<xsl:value-of select="@valor"/>
						</fo:block>
					</fo:table-cell>
				</fo:table-row>
			</fo:table-body>
		</fo:table>
	</xsl:template>

</xsl:stylesheet>
