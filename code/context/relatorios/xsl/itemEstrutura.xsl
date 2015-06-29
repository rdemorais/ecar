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

	<xsl:attribute-set name="textoDescFR">
		<xsl:attribute name="font-size">9.0pt</xsl:attribute>
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

	<xsl:attribute-set name="itemNaoSelecionado">
		<xsl:attribute name="font-size">11.0pt</xsl:attribute>
		<xsl:attribute name="font-family">sans-serif</xsl:attribute>
		<xsl:attribute name="font-weight">bold</xsl:attribute>		
		<xsl:attribute name="color">silver</xsl:attribute>
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
					margin-top="1cm" margin-bottom="0.3cm" margin-left="0.3cm" margin-right="0.3cm">
					<fo:region-body margin-top="1cm" margin-bottom="0.5cm"/>
					<fo:region-before extent="2cm"/>
					<fo:region-after extent="0.5cm"/>
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
											<xsl:value-of select="@datahora"/>								
										</fo:block>	
									</fo:table-cell>
									<fo:table-cell>
										<fo:block xsl:use-attribute-sets="label" text-align="end">
											Página <fo:page-number/> de <fo:page-number-citation ref-id="last-page"/>								
										</fo:block>
									</fo:table-cell>								
								</fo:table-row>																											
							</fo:table-body>
						</fo:table>	
					</fo:block>
				</fo:static-content>

				<fo:flow flow-name="xsl-region-body">
					<xsl:apply-templates/>
					<fo:block id="last-page"/>
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
<!--							<fo:block xsl:use-attribute-sets="label" text-align="justify" space-before="3cm">-->
<!--								Tipo de Relatório: <xsl:value-of select="@tipoRelatorio" />-->
<!--							</fo:block>-->
							<fo:block xsl:use-attribute-sets="label" text-align="justify" space-before="3cm">
								Órgão: <xsl:value-of select="@orgao" />
							</fo:block>
							<fo:block xsl:use-attribute-sets="label" text-align="justify">
								Itens Selecionados: <xsl:value-of select="@tituloItens" />
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
			<xsl:apply-templates/>					
		</fo:block>
	</xsl:template>

	
	<xsl:template match="hierarquia">			
		<fo:table>
			<fo:table-column column-width="18.0cm"/>
			<fo:table-body>
				<xsl:apply-templates select="nivel"/>
			</fo:table-body>
		</fo:table>	
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

	<xsl:template match="nivel">	
			<fo:table-row>
				<fo:table-cell>
					<xsl:choose>
						<xsl:when test="position() != last()">
							<fo:table table-layout="fixed">
								<fo:table-column column-width="5.5cm"/>
								<fo:table-column column-width="14cm"/>
								<fo:table-body>
									<fo:table-row>
										<fo:table-cell>
											<fo:block space-before="0.1cm" text-align="left" xsl:use-attribute-sets="titulo">
												<xsl:call-template name="imagem">
					                                  <xsl:with-param name="total"><xsl:value-of select="position()"/></xsl:with-param>								
					                                  <xsl:with-param name="inicial">1</xsl:with-param>								
										              <xsl:with-param name="ultimo"><xsl:value-of select="last()"/></xsl:with-param>
												</xsl:call-template>				
												<fo:external-graphic src="url('images/icon_ident_preto.gif')"/>            							
												<xsl:value-of select="@estrutura"/>
											</fo:block>
										</fo:table-cell>
										<fo:table-cell>
											<fo:block space-before="0.1cm" text-align="left" xsl:use-attribute-sets="titulo">
												<xsl:value-of select="@nomeNivel"/>
											</fo:block>
										</fo:table-cell>
									</fo:table-row>
								</fo:table-body>
							</fo:table>
						</xsl:when>
						<xsl:otherwise>
							<fo:table table-layout="fixed">
								<fo:table-column column-width="5.5cm"/>
								<fo:table-column column-width="14cm"/>
								<fo:table-body>
									<fo:table-row>
										<fo:table-cell>
											<fo:block space-before="0.1cm" text-align="left" xsl:use-attribute-sets="titulo">
												<xsl:call-template name="imagem">
					                                  <xsl:with-param name="total"><xsl:value-of select="position()"/></xsl:with-param>								
					                                  <xsl:with-param name="inicial">1</xsl:with-param>								
										              <xsl:with-param name="ultimo"><xsl:value-of select="last()"/></xsl:with-param>
												</xsl:call-template>				
												<fo:external-graphic src="url('images/icon_ident_preto.gif')"/>            							
												<xsl:value-of select="@estrutura"/>
											</fo:block>
										</fo:table-cell>
										<fo:table-cell>
											<fo:block space-before="0.1cm" text-align="left" xsl:use-attribute-sets="titulo">
												<xsl:value-of select="@nomeNivel"/>
											</fo:block>
										</fo:table-cell>
									</fo:table-row>
								</fo:table-body>
							</fo:table>
						</xsl:otherwise>
					</xsl:choose>						
				</fo:table-cell>
			</fo:table-row>
	</xsl:template>
	
	<xsl:template match="dadosBasicos">	
		<fo:table table-layout="fixed" space-before="0.2cm">
			<fo:table-column column-width="2.0cm"/>
			<fo:table-column column-width="5.0cm"/>
			<fo:table-column column-width="13.0cm"/>		
			<fo:table-body>		
					<xsl:apply-templates/>									
			</fo:table-body>
		</fo:table>	
	</xsl:template>
	
	<xsl:template match="atributo1">	
			<fo:table-row>
				<fo:table-cell/>
				<fo:table-cell>
					<fo:block space-after="0.1cm" text-align="right" xsl:use-attribute-sets="label">
						<xsl:value-of select="@label"/>
					</fo:block>
				</fo:table-cell>
				<fo:table-cell>				
					<fo:block xsl:use-attribute-sets="label" text-align="justify">
<!--						<fo:leader leader-length="0.4cm"/>-->
						<xsl:value-of select="@valor"/>				
					</fo:block>
				</fo:table-cell>
			</fo:table-row>
	</xsl:template>

	<xsl:template match="atributo2">	
			<fo:table-row>
				<fo:table-cell/>
				<fo:table-cell>
					<fo:block space-after="0.1cm" text-align="right" xsl:use-attribute-sets="label">
						<xsl:value-of select="@label"/>
					</fo:block>
				</fo:table-cell>
				<fo:table-cell>				
					<fo:block xsl:use-attribute-sets="label" white-space-collapse="false">
<!--						<fo:leader leader-length="0.4cm"/>-->
						<xsl:value-of select="@valor"/>				
					</fo:block>
				</fo:table-cell>
			</fo:table-row>
	</xsl:template>

	<xsl:template match="beneficiarios">	
<!--		<fo:block start-indent="2.2cm" xsl:use-attribute-sets="titulo" space-before="0.4cm"><xsl:value-of select="@label"/></fo:block>-->
		<fo:block start-indent="5cm" xsl:use-attribute-sets="titulo" space-before="0.2cm"><xsl:value-of select="@label"/></fo:block>
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
	
	<xsl:template match="acoes">	
<!--		<fo:block start-indent="2.2cm" space-before="0.4cm" xsl:use-attribute-sets="titulo"><xsl:value-of select="@label"/></fo:block>-->
		<fo:block start-indent="5cm" space-before="0.2cm" xsl:use-attribute-sets="titulo"><xsl:value-of select="@label"/></fo:block>
		<fo:block>
			<fo:table space-before="0.1cm" table-layout="fixed">
				<fo:table-column column-width="1cm"/>
				<fo:table-column column-width="2.3cm"/>
				<fo:table-column column-width="10cm"/>
				<fo:table-column column-width="0.5cm"/>
				<fo:table-column column-width="3.3cm"/>
				<fo:table-column column-width="2.5cm"/>
				<fo:table-header>		
					<fo:table-row>
						<fo:table-cell/>
						<fo:table-cell background-color="#CCC"><fo:block text-align="justify" xsl:use-attribute-sets="label">Data</fo:block></fo:table-cell>
						<fo:table-cell background-color="#CCC"><fo:block text-align="justify" xsl:use-attribute-sets="label">Descrição</fo:block></fo:table-cell>
						<fo:table-cell background-color="#CCC"/>
						<fo:table-cell background-color="#CCC"><fo:block text-align="justify" xsl:use-attribute-sets="label">Ult. Manutenção</fo:block></fo:table-cell>
						<fo:table-cell background-color="#CCC"><fo:block text-align="justify" xsl:use-attribute-sets="label">Inclusão</fo:block></fo:table-cell>
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
				<fo:table-cell/>
				<fo:table-cell><fo:block space-before="0.1cm" text-align="justify" xsl:use-attribute-sets="textoDesc"><xsl:value-of select="@data"/></fo:block></fo:table-cell>
				<fo:table-cell><fo:block space-before="0.1cm" text-align="justify" xsl:use-attribute-sets="textoDesc"><xsl:value-of select="@descricao"/></fo:block></fo:table-cell>
				<fo:table-cell/>
				<fo:table-cell><fo:block space-before="0.1cm" text-align="justify" xsl:use-attribute-sets="textoDesc"><xsl:value-of select="@ultManutencao"/></fo:block></fo:table-cell>
				<fo:table-cell><fo:block space-before="0.1cm" text-align="justify" xsl:use-attribute-sets="textoDesc"><xsl:value-of select="@dataInclusao"/></fo:block></fo:table-cell>
			</fo:table-row>
	</xsl:template>
	
	<xsl:template match="criterios">	
<!--		<fo:block start-indent="2.2cm" space-before="0.4cm" xsl:use-attribute-sets="titulo"><xsl:value-of select="@label"/></fo:block>-->
		<fo:block start-indent="5cm" space-before="0.2cm" xsl:use-attribute-sets="titulo"><xsl:value-of select="@label"/></fo:block>
		<fo:block>
			<fo:table space-before="0.1cm" table-layout="fixed">
				<fo:table-column column-width="6cm"/>
				<fo:table-column column-width="13.6cm"/>
				<fo:table-body>		
						<xsl:apply-templates select="criterio"/>									
				</fo:table-body>
			</fo:table>					
		</fo:block>
	</xsl:template>	

	<xsl:template match="criterio">	
			<fo:table-row>
				<fo:table-cell/>
				<fo:table-cell><fo:block space-before="0.1cm" text-align="justify" xsl:use-attribute-sets="textoDesc">- <xsl:value-of select="."/></fo:block></fo:table-cell>
			</fo:table-row>
	</xsl:template>
	
	<xsl:template match="entidades">	
<!--		<fo:block start-indent="2.2cm" space-before="0.4cm" xsl:use-attribute-sets="titulo"><xsl:value-of select="@label"/></fo:block>-->
		<fo:block start-indent="5cm" space-before="0.2cm" xsl:use-attribute-sets="titulo"><xsl:value-of select="@label"/></fo:block>
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
			<fo:table-column column-width="2.0cm"/>
			<fo:table-column column-width="6.0cm"/>
			<fo:table-column column-width="11.0cm"/>		
			<fo:table-body>		
				<fo:table-row>
					<fo:table-cell/>
					<fo:table-cell>
						<fo:block space-after="0.1cm" text-align="right" xsl:use-attribute-sets="label">
							Níveis de Planejamento:
						</fo:block>
					</fo:table-cell>
					<fo:table-cell>	
							<xsl:apply-templates/>		
					</fo:table-cell>
				</fo:table-row>
			</fo:table-body>
		</fo:table>	
	</xsl:template>
	
	<xsl:template match="nivelPlanejamento">	
		<fo:block xsl:use-attribute-sets="label" space-after="0.1cm" text-align="justify">
<!--				<fo:leader leader-length="0.4cm"/>-->
				<xsl:value-of select="."/>
		</fo:block>								
	</xsl:template>
	
	<xsl:template match="localizacao">	
<!--		<fo:block start-indent="2.2cm" space-before="0.4cm" xsl:use-attribute-sets="titulo"><xsl:value-of select="@label"/></fo:block>		-->
		<fo:block start-indent="5cm" space-before="0.2cm" xsl:use-attribute-sets="titulo"><xsl:value-of select="@label"/></fo:block>		
		<fo:table table-layout="fixed" space-before="0.2cm">
			<fo:table-column column-width="5cm"/>
			<fo:table-column column-width="5.0cm"/>
			<fo:table-column column-width="10.0cm"/>		
			<fo:table-body>		
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
				<fo:table-row>
					<fo:table-cell/>
					<fo:table-cell>
						<fo:block space-after="0.1cm" text-align="right" xsl:use-attribute-sets="label">
							Locais:
						</fo:block>
					</fo:table-cell>
					<fo:table-cell>	
							<xsl:apply-templates/>		
					</fo:table-cell>
				</fo:table-row>
			</fo:table-body>
		</fo:table>	
	</xsl:template>
	
	<xsl:template match="local">	
		<fo:block xsl:use-attribute-sets="label" space-after="0.1cm" text-align="justify">
				<fo:leader leader-length="0.4cm"/>
				<xsl:value-of select="."/>
		</fo:block>								
	</xsl:template>
	
	
	<xsl:template match="fontesRecurso">	
<!--		<fo:block start-indent="2.2cm" space-before="0.4cm" xsl:use-attribute-sets="titulo"><xsl:value-of select="@label"/></fo:block>		-->
		<fo:block start-indent="5cm" space-before="0.2cm" xsl:use-attribute-sets="titulo"><xsl:value-of select="@label"/></fo:block>		
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
<!--		<fo:block start-indent="2.2cm" space-before="0.4cm" xsl:use-attribute-sets="titulo"><xsl:value-of select="@label"/></fo:block>-->
		<fo:block start-indent="5cm" space-before="0.2cm" xsl:use-attribute-sets="titulo"><xsl:value-of select="@label"/></fo:block>
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
	
	<xsl:template match="pontosCriticos">	
<!--		<fo:block start-indent="2.2cm" xsl:use-attribute-sets="titulo" space-before="0.4cm"><xsl:value-of select="@label"/></fo:block>		-->
		<fo:block start-indent="5cm" xsl:use-attribute-sets="titulo" space-before="0.2cm"><xsl:value-of select="@label"/></fo:block>		
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
							<fo:leader leader-length="0.4cm"/>
							<xsl:value-of select="@dataInclusao"/> - <xsl:value-of select="@texto"/>
						</fo:block>		
				</fo:table-cell>
				</fo:table-row>
			</fo:table-body>
		</fo:table>
	</xsl:template>

	<xsl:template match="categorias">	
<!--		<fo:block start-indent="2.2cm" xsl:use-attribute-sets="titulo" space-before="0.4cm"><xsl:value-of select="@label"/></fo:block>		-->
		<fo:block start-indent="5cm" xsl:use-attribute-sets="titulo" space-before="0.2cm"><xsl:value-of select="@label"/></fo:block>		
		<xsl:apply-templates select="categoria"/>		
	</xsl:template>
	
	<xsl:template match="categoria">		
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
							<fo:block xsl:use-attribute-sets="textoDesc" space-after="0.1cm" text-align="justify">
								<fo:leader leader-length="0.4cm"/>
								<xsl:value-of select="@nome"/>
							</fo:block>		
					</fo:table-cell>
				</fo:table-row>
				<fo:table-row>
					<fo:table-cell/>
					<fo:table-cell>
						<fo:block space-after="0.1cm" text-align="right" xsl:use-attribute-sets="label">
							Tipo:
						</fo:block>
					</fo:table-cell>
					<fo:table-cell>
							<fo:block xsl:use-attribute-sets="textoDesc" space-after="0.1cm" text-align="justify">
								<fo:leader leader-length="0.4cm"/>
								<xsl:value-of select="@tipo"/>
							</fo:block>		
					</fo:table-cell>
				</fo:table-row>
			</fo:table-body>			
		</fo:table>	
		<xsl:apply-templates select="anexo" />
	</xsl:template>	
	
	<xsl:template match="anexo">
		<fo:table space-before="0.2cm" table-layout="fixed">
			<fo:table-column column-width="6.0cm"/>
			<fo:table-column column-width="3.0cm"/>
			<fo:table-column column-width="10.0cm"/>		
			<fo:table-body>		
				<fo:table-row>
					<fo:table-cell/>
					<fo:table-cell border-width="0.1mm" border-before-style="solid" border-after-style="solid" border-start-style="solid" border-end-style="solid">
						<fo:block space-after="0.1cm" text-align="right" xsl:use-attribute-sets="label">
							Nome Original:
						</fo:block>
					</fo:table-cell>
					<fo:table-cell border-width="0.1mm" border-before-style="solid" border-after-style="solid" border-start-style="solid" border-end-style="solid">
							<fo:block xsl:use-attribute-sets="textoDesc" space-after="0.1cm" text-align="justify">
								<fo:leader leader-length="0.4cm"/>
								<xsl:value-of select="@nomeOriginal"/>
							</fo:block>		
					</fo:table-cell>
				</fo:table-row>
				<fo:table-row>
					<fo:table-cell/>
					<fo:table-cell border-width="0.1mm" border-before-style="solid" border-after-style="solid" border-start-style="solid" border-end-style="solid">
						<fo:block space-after="0.1cm" text-align="right" xsl:use-attribute-sets="label">
							Tamanho:
						</fo:block>
					</fo:table-cell>
					<fo:table-cell border-width="0.1mm" border-before-style="solid" border-after-style="solid" border-start-style="solid" border-end-style="solid">
							<fo:block xsl:use-attribute-sets="textoDesc" space-after="0.1cm" text-align="justify">
								<fo:leader leader-length="0.4cm"/>
								<xsl:value-of select="@tamanho"/>
							</fo:block>		
					</fo:table-cell>
				</fo:table-row>
				<fo:table-row>
					<fo:table-cell/>
					<fo:table-cell border-width="0.1mm" border-before-style="solid" border-after-style="solid" border-start-style="solid" border-end-style="solid">
						<fo:block space-after="0.1cm" text-align="right" xsl:use-attribute-sets="label">
							Descrição:
						</fo:block>
					</fo:table-cell>
					<fo:table-cell border-width="0.1mm" border-before-style="solid" border-after-style="solid" border-start-style="solid" border-end-style="solid">
							<fo:block xsl:use-attribute-sets="textoDesc" space-after="0.1cm" text-align="justify">
								<fo:leader leader-length="0.4cm"/>
								<xsl:value-of select="@descricao"/>
							</fo:block>		
					</fo:table-cell>
				</fo:table-row>
			</fo:table-body>
		</fo:table>
	</xsl:template>

	<xsl:template match="indicadoresResultado">	
<!--		<fo:block start-indent="2.2cm" space-before="0.4cm" xsl:use-attribute-sets="titulo"><xsl:value-of select="@label"/></fo:block>		-->
		<fo:block start-indent="5cm" space-before="0.1cm" xsl:use-attribute-sets="titulo"><xsl:value-of select="@label"/></fo:block>		
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
							Acumulável:
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
								Valor Final:
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
							Permite Projeções:						
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
	
	<xsl:template match="filho">			
		<fo:table table-layout="fixed">
			<fo:table-column column-width="5.5cm"/>
			<fo:table-column column-width="14cm"/>
			<fo:table-body>
				<fo:table-row>
					<fo:table-cell>
						<xsl:if test="@itemSelecionado='S'">
							<fo:block space-before="0.1cm" text-align="left" xsl:use-attribute-sets="titulo">
								<xsl:call-template name="imagem">
					                  <xsl:with-param name="total"><xsl:value-of select="@nivel"/></xsl:with-param>								                    
						              <xsl:with-param name="inicial">1</xsl:with-param>								
								</xsl:call-template>
								<fo:external-graphic src="url('images/icon_ident_preto.gif')"/>            							
								
								<xsl:value-of select="@nomeNivel"/>
							</fo:block>
						</xsl:if>
						<xsl:if test="@itemSelecionado='N'">
							<fo:block space-before="0.1cm" text-align="left" xsl:use-attribute-sets="itemNaoSelecionado">
								<xsl:call-template name="imagem">
					                  <xsl:with-param name="total"><xsl:value-of select="@nivel"/></xsl:with-param>								                    
						              <xsl:with-param name="inicial">1</xsl:with-param>								
								</xsl:call-template>
								<fo:external-graphic src="url('images/icon_ident_preto.gif')"/>            							
								
								<xsl:value-of select="@nomeNivel"/>
							</fo:block>
						</xsl:if>
					</fo:table-cell>
					<fo:table-cell>
						<xsl:if test="@itemSelecionado='S'">
							<fo:block space-before="0.1cm" text-align="left" xsl:use-attribute-sets="titulo">
								<xsl:value-of select="@nome"/>
							</fo:block>
						</xsl:if>
						<xsl:if test="@itemSelecionado='N'">
							<fo:block space-before="0.1cm" text-align="left" xsl:use-attribute-sets="itemNaoSelecionado">
								<xsl:value-of select="@nome"/>
							</fo:block>
						</xsl:if>
					</fo:table-cell>
				</fo:table-row>
			</fo:table-body>
		</fo:table>
		<xsl:apply-templates/>			
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
						<fo:block space-before="0.1cm" space-after="0.2cm" text-align="rigth" xsl:use-attribute-sets="texto" >
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
						<fo:block space-before="0.1cm" text-align="left" xsl:use-attribute-sets="label" >
							<xsl:value-of select="@nome"/>
						</fo:block>
					</fo:table-cell>
					<fo:table-cell>
						<fo:block space-before="0.1cm" text-align="right" xsl:use-attribute-sets="label" >
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
						<fo:block space-before="0.3cm" text-align="left" xsl:use-attribute-sets="texto" >
							<xsl:value-of select="@nome"/>
						</fo:block>
					</fo:table-cell>
					<fo:table-cell>
						<fo:block space-before="0.3cm" text-align="right" xsl:use-attribute-sets="texto" >
							<xsl:value-of select="@valor"/>
						</fo:block>
					</fo:table-cell>
				</fo:table-row>
			</fo:table-body>
		</fo:table>
	</xsl:template>

</xsl:stylesheet>




