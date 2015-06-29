<?xml version="1.0" encoding="ISO-8859-1"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" 
	xmlns:fo="http://www.w3.org/1999/XSL/Format" version="1.0">
	
	<xsl:output method="xml" version="1.0" encoding="ISO-8859-1"/>
	
	<xsl:attribute-set name="label">
		<xsl:attribute name="font-size">10.0pt</xsl:attribute>
		<xsl:attribute name="font-family">sans-serif</xsl:attribute>
		<xsl:attribute name="font-weight">normal</xsl:attribute>		
	</xsl:attribute-set>	

	<xsl:attribute-set name="numeroPagina">
		<xsl:attribute name="font-size">12.0pt</xsl:attribute>
		<xsl:attribute name="font-family">sans-serif</xsl:attribute>
		<xsl:attribute name="font-weight">normal</xsl:attribute>		
		<xsl:attribute name="color">silver</xsl:attribute>		
	</xsl:attribute-set>	

	<xsl:attribute-set name="textoDesc">
		<xsl:attribute name="font-size">10.0pt</xsl:attribute>
		<xsl:attribute name="font-family">sans-serif</xsl:attribute>
		<xsl:attribute name="font-weight">normal</xsl:attribute>		
	</xsl:attribute-set>	

	<xsl:attribute-set name="textoDescNegrito">
		<xsl:attribute name="font-size">10.0pt</xsl:attribute>
		<xsl:attribute name="font-family">sans-serif</xsl:attribute>
		<xsl:attribute name="font-weight">bold</xsl:attribute>		
	</xsl:attribute-set>	

	<xsl:attribute-set name="textoDescVerde">
		<xsl:attribute name="font-size">10.0pt</xsl:attribute>
		<xsl:attribute name="font-family">sans-serif</xsl:attribute>
		<xsl:attribute name="font-weight">normal</xsl:attribute>		
		<xsl:attribute name="color">green</xsl:attribute>		
	</xsl:attribute-set>	

	<xsl:attribute-set name="textoDescVermelho">
		<xsl:attribute name="font-size">10.0pt</xsl:attribute>
		<xsl:attribute name="font-family">sans-serif</xsl:attribute>
		<xsl:attribute name="font-weight">normal</xsl:attribute>		
		<xsl:attribute name="color">red</xsl:attribute>		
	</xsl:attribute-set>	

	<xsl:attribute-set name="textoDescAzul">
		<xsl:attribute name="font-size">10.0pt</xsl:attribute>
		<xsl:attribute name="font-family">sans-serif</xsl:attribute>
		<xsl:attribute name="font-weight">normal</xsl:attribute>		
		<xsl:attribute name="color">blue</xsl:attribute>		
	</xsl:attribute-set>	

	<xsl:attribute-set name="titulo">
		<xsl:attribute name="font-size">16.0pt</xsl:attribute>
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
		
	<xsl:attribute-set name="programa">
		<xsl:attribute name="font-size">11.0pt</xsl:attribute>
		<xsl:attribute name="font-family">sans-serif</xsl:attribute>
		<xsl:attribute name="font-weight">bold</xsl:attribute>		
	</xsl:attribute-set>	

	<xsl:attribute-set name="acao">
		<xsl:attribute name="font-size">10.0pt</xsl:attribute>
		<xsl:attribute name="font-family">sans-serif</xsl:attribute>
		<xsl:attribute name="font-weight">bold</xsl:attribute>		
	</xsl:attribute-set>	

	<xsl:attribute-set name="textoInt">
		<xsl:attribute name="font-size">12.0pt</xsl:attribute>
		<xsl:attribute name="font-family">sans-serif</xsl:attribute>
		<xsl:attribute name="font-weight">normal</xsl:attribute>		
	</xsl:attribute-set>	

	<xsl:attribute-set name="textoIntNegrito">
		<xsl:attribute name="font-size">12.0pt</xsl:attribute>
		<xsl:attribute name="font-family">sans-serif</xsl:attribute>
		<xsl:attribute name="font-weight">bold</xsl:attribute>		
	</xsl:attribute-set>	

	<xsl:attribute-set name="textoIntNegritoUnder">
		<xsl:attribute name="font-size">12.0pt</xsl:attribute>
		<xsl:attribute name="font-family">sans-serif</xsl:attribute>
		<xsl:attribute name="font-weight">bold</xsl:attribute>
		<xsl:attribute name="text-decoration">underline</xsl:attribute>
	</xsl:attribute-set>	

	<xsl:attribute-set name="textoIntNegritoVermelho">
		<xsl:attribute name="font-size">12.0pt</xsl:attribute>
		<xsl:attribute name="font-family">sans-serif</xsl:attribute>
		<xsl:attribute name="font-weight">bold</xsl:attribute>		
		<xsl:attribute name="color">red</xsl:attribute>		
	</xsl:attribute-set>	

	<xsl:attribute-set name="textoIntNegritoVerde">
		<xsl:attribute name="font-size">12.0pt</xsl:attribute>
		<xsl:attribute name="font-family">sans-serif</xsl:attribute>
		<xsl:attribute name="font-weight">bold</xsl:attribute>		
		<xsl:attribute name="color">green</xsl:attribute>		
	</xsl:attribute-set>	

	<xsl:attribute-set name="textoIntNegritoAzul">
		<xsl:attribute name="font-size">12.0pt</xsl:attribute>
		<xsl:attribute name="font-family">sans-serif</xsl:attribute>
		<xsl:attribute name="font-weight">bold</xsl:attribute>		
		<xsl:attribute name="color">blue</xsl:attribute>		
	</xsl:attribute-set>	

	<xsl:template match="relatorio">
		<fo:root>
			<fo:layout-master-set>
				<fo:simple-page-master master-name="A4_Paisagem" page-height="21cm" page-width="29.8cm"
					margin-top="1.5cm" margin-bottom="1.5cm" margin-left="2cm" margin-right="2cm">
					<fo:region-body margin-top="0.5cm" margin-bottom="1.5cm"/>
					<fo:region-before extent="1cm"/>
					<fo:region-after extent="1cm"/>
				</fo:simple-page-master>

				<fo:simple-page-master master-name="A4_Retrato" page-height="29.8cm" page-width="21cm"
					margin-top="3cm" margin-bottom="2cm" margin-left="2cm" margin-right="2cm">
					<fo:region-body margin-top="2cm" margin-bottom="2cm"/>
					<fo:region-before extent="1cm"/>
					<fo:region-after extent="1cm"/>
				</fo:simple-page-master>
			</fo:layout-master-set>

			<fo:page-sequence master-reference="A4_Paisagem" format="1" initial-page-number="{@capa}">
				<fo:static-content flow-name="xsl-region-before">
				</fo:static-content>

				<fo:static-content flow-name="xsl-region-after">
					<fo:block text-align="right" xsl:use-attribute-sets="numeroPagina">
						<fo:page-number/>
					</fo:block>
				</fo:static-content>

				<fo:flow flow-name="xsl-region-body">
					<xsl:if test="@tipo = 'apendice2'"><!--  Se for Apendice 2 -->
						<fo:block text-align="center" xsl:use-attribute-sets="titulo">
							<fo:external-graphic src="url('images/brasao_parana.jpg')"/>
						</fo:block>
						<fo:block text-align="center" space-before="0.2cm" xsl:use-attribute-sets="titulo">
							ESTADO DO PARAN�
						</fo:block>
						<fo:block text-align="center" xsl:use-attribute-sets="titulo">
							SECRETARIA DE ESTADO DO PLANEJAMENTO E COORDENA��O GERAL
						</fo:block>
						<fo:block text-align="center" space-before="2cm" xsl:use-attribute-sets="titulo">
							PLANO PLURIANUAL -PPA
						</fo:block>
						<fo:block text-align="center" xsl:use-attribute-sets="titulo">
							<xsl:value-of select="@anoIni"/> - <xsl:value-of select="@anoFim"/>
						</fo:block>
						<fo:block text-align="center" xsl:use-attribute-sets="titulo">
							(ATUALIZADO)
						</fo:block>
						<fo:block text-align="center" space-before="2cm" xsl:use-attribute-sets="titulo">
							<xsl:value-of select="@mesAnoGeracao"/>
						</fo:block>
						<fo:block break-before="page"/>
						
						<xsl:if test="@valores = 'A'"> <!--  Se for valores Aprovados -->
							<fo:block text-align="center" space-before="8cm" xsl:use-attribute-sets="titulo">
								<fo:block>
									AP�NDICE 2 - METAS F�SICAS E FINANCEIRAS ATUALIZADAS
								</fo:block>
								<fo:block>
									VALORES APROVADOS - (HIST�RICO)
								</fo:block>
							</fo:block>
						</xsl:if>

						<xsl:if test="@valores = 'R'"><!--  Se for valores Revisados -->
							<xsl:if test="@anoIni = '2004' and @anoFim = '2007'">
								<fo:block text-align="justify" xsl:use-attribute-sets="textoIntNegrito">
								REVIS�O DO PPA 2004-2007
								</fo:block>

								<fo:block text-align="justify" space-before="0.2cm" xsl:use-attribute-sets="textoInt">
								O Plano Plurianual 2004-2007, aprovado pela Lei n� 14.276, de 29 de dezembro de 2003, em seu artigo 3�, prev� que os procedimentos or�ament�rios anuais constituem reavalia��es autom�ticas do Plano.
								</fo:block>

								<fo:block text-align="justify" space-before="0.2cm" xsl:use-attribute-sets="textoInt">
								Baseado neste dispositivo, a Secretaria de Estado do Planejamento e Coordena��o Geral, por meio da Coordenadoria de An�lise de Resultados, procedeu � atualiza��o/revis�o do <fo:inline xsl:use-attribute-sets="textoIntNegrito">AP�NDICE 2 - METAS F�SICAS E FINANCEIRAS</fo:inline>, que reflete a programa��o atualizada, decorrente das modifica��es or�ament�rias ocorridas ao longo dos exerc�cios.
								</fo:block>

								<fo:block text-align="justify" space-before="0.2cm" xsl:use-attribute-sets="textoInt">
								Nesse processo, n�o foi objeto a revis�o de Programas, apenas as A��es e seus Produtos.
								</fo:block>

								<fo:block text-align="justify" space-before="0.2cm" xsl:use-attribute-sets="textoInt">
								A atualiza��o do Plano foi estabelecida conforme segue:
								</fo:block>
								<fo:block text-align="justify" xsl:use-attribute-sets="textoInt">
								1. Os <fo:inline xsl:use-attribute-sets="textoIntNegrito">valores or�ament�rio-financeiros</fo:inline> das A��es foram atualizados da seguinte forma:
								</fo:block>
								<fo:block text-align="justify" xsl:use-attribute-sets="textoInt">
									<fo:external-graphic src="url('images/icon_ident_preto_ppa.png')"/> 2004 e 2005 - valores empenhados (sem Movimenta��o de Cr�dito Or�ament�rio);
								</fo:block>
								<fo:block text-align="justify" xsl:use-attribute-sets="textoInt">
									<fo:external-graphic src="url('images/icon_ident_preto_ppa.png')"/> 2006 - valores iniciais previstos na Lei Or�ament�ria Anual - 2006;
								</fo:block>
								<fo:block text-align="justify" xsl:use-attribute-sets="textoInt">
									<fo:external-graphic src="url('images/icon_ident_preto_ppa.png')"/> 2007 - valores originais do PPA, quando dispon�veis.
								</fo:block>
								<fo:block text-align="justify" xsl:use-attribute-sets="textoInt">
								2. As metas f�sicas:
								</fo:block>
								<fo:block text-align="justify" xsl:use-attribute-sets="textoInt">
									<fo:external-graphic src="url('images/icon_ident_preto_ppa.png')"/> 2004 e 2005 - extra�das dos Relat�rios de Acompanhamento F�sico e Financeiro (AFF) e revisadas pelos Grupos de Planejamento Setoriais;
								</fo:block>
								<fo:block text-align="justify" xsl:use-attribute-sets="textoInt">
									<fo:external-graphic src="url('images/icon_ident_preto_ppa.png')"/> 2006 e 2007 - atualizadas em conjunto com os Grupos de Planejamento Setoriais.
								</fo:block>

								<fo:block text-align="justify" space-before="0.2cm" xsl:use-attribute-sets="textoInt">
								Est�o identificadas as 
									<fo:inline xsl:use-attribute-sets="textoIntNegrito">A��es </fo:inline>
									<fo:inline xsl:use-attribute-sets="textoIntNegritoVerde">Inclu�das</fo:inline>
									<fo:inline xsl:use-attribute-sets="textoIntNegrito"> e</fo:inline>
									<fo:inline xsl:use-attribute-sets="textoIntNegritoVermelho"> Alteradas</fo:inline>.
								</fo:block>
								<fo:block text-align="justify" xsl:use-attribute-sets="textoInt">
									<fo:external-graphic src="url('images/icon_ident_preto_ppa.png')"/> As inclus�es referem-se �quelas incorporadas nas Leis Or�ament�rias Anuais.
								</fo:block>
								<fo:block text-align="justify" xsl:use-attribute-sets="textoInt">
									<fo:external-graphic src="url('images/icon_ident_preto_ppa.png')"/> As altera��es mais significativas foram: denomina��o, meta f�sica (quantidade, tipo de produto e totaliza��o incoerente) e valores or�ament�rio-financeiros.
								</fo:block>

								<fo:block text-align="justify" space-before="0.2cm" xsl:use-attribute-sets="textoInt">
								Quanto aos <fo:inline xsl:use-attribute-sets="textoIntNegrito">Produtos</fo:inline> identificados abaixo da A��o:
								</fo:block>
								<fo:block text-align="justify" xsl:use-attribute-sets="textoInt">
									<fo:external-graphic src="url('images/icon_ident_preto_ppa.png')"/> Os caracterizados como <fo:inline xsl:use-attribute-sets="textoIntNegritoVerde">Inclu�dos</fo:inline> referem-se �queles que foram transferidos de outras A��es, mas j� constavam do PPA original.
								</fo:block>
								<fo:block text-align="justify" xsl:use-attribute-sets="textoInt">
									<fo:external-graphic src="url('images/icon_ident_preto_ppa.png')"/> Os <fo:inline xsl:use-attribute-sets="textoIntNegritoVermelho">Alterados</fo:inline> foram objeto de revis�o de denomina��o, meta f�sica (quantidade, tipo de produto e totaliza��o incoerente).
								</fo:block>

								<fo:block text-align="justify" space-before="0.2cm" xsl:use-attribute-sets="textoInt">
								As A��es e Produtos inalterados n�o cont�m observa��es.
								</fo:block>
								
								<fo:block text-align="justify" space-before="0.2cm" xsl:use-attribute-sets="textoInt">
								As A��es e os Produtos <fo:inline xsl:use-attribute-sets="textoIntNegritoAzul">Exclu�dos</fo:inline> constam do <fo:inline xsl:use-attribute-sets="textoIntNegrito">ANEXO - A��ES/PRODUTOS EXCLU�DOS</fo:inline>.
								</fo:block>

								<fo:block text-align="justify" space-before="0.2cm" xsl:use-attribute-sets="textoInt">
								Principais justificativas para exclus�o: n�o houve execu��o or�ament�ria ou realiza��o; transferido, incorporado ou substituido por outra A��o/Produto; houve execu��o em um exerc�cio e deixou de ser realizado nos seguintes; produtos estabelecidos sem o conceito adequado, em raz�o de n�o gerar servi�o diretamente � sociedade, entre outras.
								</fo:block>
							</xsl:if>
							<xsl:if test="@anoIni != '2004' or @anoFim != '2007'">
								<fo:block text-align="justify" xsl:use-attribute-sets="textoInt">
								A mensagem na capa do Ap�ndice 2 � emitida somente quando o per�odo solicitado � 2004-2007.
								</fo:block>
							</xsl:if>									
							<fo:block break-before="page"/>
							<fo:block text-align="center" space-before="8cm" xsl:use-attribute-sets="titulo">
								AP�NDICE 2 - METAS F�SICAS E FINANCEIRAS ATUALIZADAS
							</fo:block>
						</xsl:if>							
					</xsl:if>
					<xsl:if test="@tipo = 'apendice3'"><!--  Se for Apendice 3 -->
						<fo:block text-align="center" space-before="8cm" xsl:use-attribute-sets="titulo">
							ANEXO - A��ES E PRODUTOS EXCLU�DOS
						</fo:block>
					</xsl:if>
				</fo:flow>
			</fo:page-sequence>			

			<fo:page-sequence master-reference="A4_Paisagem" format="1">
				<fo:static-content flow-name="xsl-region-before">
					<fo:block xsl:use-attribute-sets="textoMenor" text-align="center">
						<xsl:value-of select="@titulo"/>								
					</fo:block>
				</fo:static-content>

				<fo:static-content flow-name="xsl-region-after">
					<fo:block border-top-color="gray" border-top-width="0.5cm" border-top-style="solid" text-align="right"/>
					<fo:table>
						<fo:table-column column-width="20cm"/>
						<fo:table-column/>
						<fo:table-body>
							<fo:table-row>
								<fo:table-cell>
									<fo:block xsl:use-attribute-sets="label" text-align="start">
										<xsl:value-of select="@nomeRelatorio"/> <xsl:value-of select="@nomeRelatorioRodape"/>
									</fo:block>
								</fo:table-cell>
								<fo:table-cell>
									<fo:block xsl:use-attribute-sets="label" text-align="end">
										<fo:page-number/>
									</fo:block>
								</fo:table-cell>
							</fo:table-row>
						</fo:table-body>
					</fo:table>
<!--					<fo:block xsl:use-attribute-sets="label" text-align="end">-->
<!--						<fo:page-number/>-->
<!--					</fo:block>-->
				</fo:static-content>

				<fo:flow flow-name="xsl-region-body">
					<xsl:apply-templates select="programa"/>
					<xsl:if test="@mostrarTotalizador = 'S'">
						<fo:block break-before="page"/>
						<xsl:apply-templates select="totalizadores"/>
						<xsl:apply-templates select="totalizadorValores"/>
					</xsl:if>
				</fo:flow>
			</fo:page-sequence>
		</fo:root>
	</xsl:template>

	<xsl:template match="programa">
<!--		<xsl:if test="acao/itemAcao/produto">-->
		<xsl:if test="acao/itemAcao">
		<fo:block break-before="page" border-top-color="gray" border-top-style="solid" border-top-width="0.2cm">
			<fo:block space-before="0.2cm"/>
			<fo:table>
				<fo:table-column column-width="1cm"/>
				<fo:table-column column-width="3cm"/>
				<fo:table-column />
				<fo:table-body>
					<fo:table-row>
						<fo:table-cell />
						<fo:table-cell>
							<fo:block xsl:use-attribute-sets="programa">
							Programa <xsl:value-of select="@codigo"/>
							</fo:block>
						</fo:table-cell>
						<fo:table-cell>
							<fo:block xsl:use-attribute-sets="programa">
							<xsl:value-of select="@nome"/>
							</fo:block>
						</fo:table-cell>
					</fo:table-row>
				</fo:table-body>
			</fo:table>
		</fo:block>
		<xsl:apply-templates/>
		</xsl:if>
	</xsl:template>
	
	<xsl:template match="dados">
		<fo:table>
			<fo:table-column column-width="3cm"/>
			<fo:table-column column-width="0.1cm"/>
			<fo:table-column/>
			<fo:table-body>
				<xsl:apply-templates select="campo"/>
			</fo:table-body>
		</fo:table>
	</xsl:template>
	
	<xsl:template match="campo">
		<fo:table-row>
			<fo:table-cell>
				<fo:block text-align="right" xsl:use-attribute-sets="textoDesc">
					<xsl:value-of select="@label"/>
				</fo:block>
			</fo:table-cell>
			<fo:table-cell />
			<fo:table-cell>
				<fo:block text-align="justify" xsl:use-attribute-sets="textoDesc">
					<xsl:value-of select="@valor"/>
				</fo:block>
			</fo:table-cell>
		</fo:table-row>
	</xsl:template>
	
	<xsl:template match="valores-financeiros">
		<fo:block space-before="0.2mm" space-after="0.2cm">
			<fo:table>
				<fo:table-column column-width="5cm"/>
				<fo:table-column column-width="10cm"/>
				<fo:table-column column-width="6cm"/>
				<fo:table-column/>
				<fo:table-body>
					<fo:table-row>
						<fo:table-cell background-color="#CCC"/>
						<fo:table-cell background-color="#CCC">
							<fo:block text-align="justify" xsl:use-attribute-sets="textoDesc">
								VALORES FINANCEIROS:
							</fo:block>
						</fo:table-cell>
						<fo:table-cell background-color="#CCC">
							<fo:block text-align="right" xsl:use-attribute-sets="textoDesc">
								<xsl:value-of select="@periodo"/>
							</fo:block>
						</fo:table-cell>
						<fo:table-cell background-color="#CCC"/>
					</fo:table-row>
				</fo:table-body>
			</fo:table>
			<fo:table>
				<fo:table-column column-width="8cm"/>
				<fo:table-column column-width="7cm"/>
				<fo:table-column column-width="6cm"/>
				<fo:table-column/>
				<fo:table-body>
					<xsl:apply-templates select="valor"/>
				</fo:table-body>
			</fo:table>
		</fo:block>
	</xsl:template>
	
	<xsl:template match="valor">
		<fo:table-row>
			<fo:table-cell/>
			<fo:table-cell>
				<fo:block text-align="justify" xsl:use-attribute-sets="textoDesc" space-before="0.2cm">
					<xsl:value-of select="@label"/>
				</fo:block>
			</fo:table-cell>
			<fo:table-cell>
				<fo:block text-align="right" xsl:use-attribute-sets="textoDesc" space-before="0.2cm">
					<xsl:value-of select="@valor"/>
				</fo:block>
			</fo:table-cell>
			<fo:table-cell/>
		</fo:table-row>
	</xsl:template>

	<xsl:template match="acao">
		<fo:table>
			<fo:table-column/>
			<fo:table-header>
				<fo:table-row>
					<fo:table-cell>
						<fo:block space-before="0.5mm" border-top-color="black" border-top-style="solid" border-top-width="0.2mm" border-bottom-color="black" border-bottom-style="solid" border-bottom-width="0.2mm">
							<fo:table>
								<fo:table-column/>
								<fo:table-column/>
								<fo:table-body>
									<fo:table-row>
										<fo:table-cell background-color="#CCC">
											<fo:block text-align="justify" xsl:use-attribute-sets="acao">
												A��O
											</fo:block>
										</fo:table-cell>
										<fo:table-cell background-color="#CCC">
											<xsl:if test="@tipoApendice = 'apendice2'">
												<fo:block text-align="right" xsl:use-attribute-sets="acao">
													VALOR A��O
												</fo:block>
											</xsl:if>
										</fo:table-cell>
									</fo:table-row>
								</fo:table-body>
							</fo:table>
							<xsl:if test="@tipoApendice = 'apendice2'">
								<fo:table>
									<fo:table-column column-width="1cm"/>
									<fo:table-column column-width="14.8cm"/>
									<fo:table-column column-width="2.2cm"/>
									<fo:table-column column-width="4cm"/>
									<fo:table-column/>
									<fo:table-body>
										<fo:table-row>
											<fo:table-cell background-color="#CCC"/>
											<fo:table-cell background-color="#CCC">
												<fo:block text-align="left" xsl:use-attribute-sets="textoDesc">
													PRODUTO (TIPO PRODUTO/UNIDADE)
												</fo:block>
											</fo:table-cell>
											<fo:table-cell background-color="#CCC">
												<fo:block text-align="left" xsl:use-attribute-sets="textoDesc">
													REGI�O
												</fo:block>
											</fo:table-cell>
											<fo:table-cell background-color="#CCC">
												<fo:block text-align="right" xsl:use-attribute-sets="textoDesc">
													METAS <xsl:value-of select="@periodo"/>
												</fo:block>
											</fo:table-cell>
											<fo:table-cell background-color="#CCC"/>
										</fo:table-row>
									</fo:table-body>
								</fo:table>
							</xsl:if>	
							<xsl:if test="@tipoApendice = 'apendice3'">
								<fo:table>
									<fo:table-column column-width="1cm"/>
									<fo:table-column column-width="14cm"/>
									<fo:table-column column-width="0.5cm"/>
									<fo:table-column column-width="6.5cm"/>
									<fo:table-column/>
									<fo:table-body>
										<fo:table-row>
											<fo:table-cell background-color="#CCC"/>
											<fo:table-cell background-color="#CCC">
												<fo:block text-align="left" xsl:use-attribute-sets="textoDesc">
													PRODUTO
												</fo:block>
											</fo:table-cell>
											<fo:table-cell background-color="#CCC"/>
											<fo:table-cell background-color="#CCC">
												<fo:block text-align="left" xsl:use-attribute-sets="textoDesc">
													JUSTIFICATIVA
												</fo:block>
											</fo:table-cell>
											<fo:table-cell background-color="#CCC"/>
										</fo:table-row>
									</fo:table-body>
								</fo:table>
							</xsl:if>	
						</fo:block>
					</fo:table-cell>
				</fo:table-row>
			</fo:table-header>
			<fo:table-body>
				<fo:table-row>
					<fo:table-cell>
						<xsl:apply-templates select="itemAcao"/>
					</fo:table-cell>
				</fo:table-row>
			</fo:table-body>
		</fo:table>
	</xsl:template>
	
	<xsl:template match="itemAcao">
<!--		<xsl:if test="produto">-->
		<fo:block border-top-color="black" border-top-style="solid" border-top-width="0.2mm"/>
		<fo:block space-before="0.2cm">
			<fo:table>
				<fo:table-column column-width="1cm"/>
				<fo:table-column column-width="{@larguraColAcao}"/>
				<fo:table-column column-width="{@larguraColAux}"/>
				<fo:table-column/>
				<fo:table-body>
					<fo:table-row>
						<fo:table-cell>
							<fo:block text-align="justify" xsl:use-attribute-sets="acao">
								<xsl:value-of select="@codigo"/>
							</fo:block>
						</fo:table-cell>
						<fo:table-cell>
							<fo:block text-align="justify" xsl:use-attribute-sets="acao">
								<xsl:value-of select="@nome"/>
								<xsl:if test="@tipoSituacaoAcao='I'">
									<fo:inline xsl:use-attribute-sets="textoDescVerde">
										<xsl:value-of select="@situacaoAcao"/>
									</fo:inline>
								</xsl:if>
								<xsl:if test="@tipoSituacaoAcao='A'">
									<fo:inline xsl:use-attribute-sets="textoDescVermelho">
										<xsl:value-of select="@situacaoAcao"/>
									</fo:inline>
								</xsl:if>
								<xsl:if test="@tipoSituacaoAcao='E'">
									<fo:inline xsl:use-attribute-sets="textoDescAzul">
										<xsl:value-of select="@situacaoAcao"/>
									</fo:inline>
								</xsl:if>
							</fo:block>
						</fo:table-cell>
						<fo:table-cell/>
						<fo:table-cell>
							<xsl:if test="@justificativa != ''">
								<fo:block text-align="left" xsl:use-attribute-sets="textoDesc" white-space-collapse="false">
									<xsl:value-of select="@justificativa"/>
								</fo:block>
							</xsl:if>
							<xsl:if test="@justificativa = ''">
								<fo:block text-align="right" xsl:use-attribute-sets="acao">
									<xsl:value-of select="@valorTotal"/>
								</fo:block>
							</xsl:if>
						</fo:table-cell>
					</fo:table-row>
				</fo:table-body>
			</fo:table>
		</fo:block>
		<xsl:apply-templates select="produto"/>
<!--		</xsl:if>		-->
	</xsl:template>
	
	<xsl:template match="produto">
		<fo:block space-before="0.2mm">
			<xsl:if test="@tipoRelatorio='apendice2'">
				<fo:table>
					<fo:table-column column-width="0.2cm"/>
					<fo:table-column column-width="2cm"/>
					<fo:table-column column-width="12.8cm"/>
					<fo:table-column column-width="0.8cm"/>
					<fo:table-column column-width="4.2cm"/>
					<fo:table-column column-width="2cm"/>
					<fo:table-column/>
					<fo:table-body>
						<fo:table-row>
							<fo:table-cell/>
								<fo:table-cell>
								<xsl:if test="@tipoSituacao = 'I'">
									<fo:block text-align="justify" xsl:use-attribute-sets="textoDescVerde" space-before="2mm">
										<xsl:if test="@situacao != ''">
										<xsl:value-of select="@situacao"/>
										</xsl:if>
									</fo:block>
								</xsl:if>
								<xsl:if test="@tipoSituacao = 'A'">
									<fo:block text-align="justify" xsl:use-attribute-sets="textoDescVermelho" space-before="2mm">
										<xsl:if test="@situacao != ''">
										<xsl:value-of select="@situacao"/>
										</xsl:if>
									</fo:block>
								</xsl:if>
								<xsl:if test="@tipoSituacao = 'E'">
									<fo:block text-align="justify" xsl:use-attribute-sets="textoDescAzul" space-before="2mm">
										<xsl:if test="@situacao != ''">
										<xsl:value-of select="@situacao"/>
										</xsl:if>
									</fo:block>
								</xsl:if>
							</fo:table-cell>
							<fo:table-cell>
								<fo:block text-align="justify" xsl:use-attribute-sets="textoDesc" space-before="2mm">
									<xsl:value-of select="@nome"/> (<xsl:value-of select="@tipo"/>/<xsl:value-of select="@unidade"/>)
								</fo:block>
							</fo:table-cell>
							<fo:table-cell/>
							<fo:table-cell>
								<fo:block text-align="justify" xsl:use-attribute-sets="textoDesc" space-before="2mm">
									<xsl:value-of select="@regiao"/>
								</fo:block>
							</fo:table-cell>
							<fo:table-cell>
								<fo:block text-align="right" xsl:use-attribute-sets="textoDesc" space-before="2mm">
									<xsl:value-of select="@valor"/>
								</fo:block>
							</fo:table-cell>
							<fo:table-cell/>
						</fo:table-row>
					</fo:table-body>
				</fo:table>
			</xsl:if>
			<xsl:if test="@tipoRelatorio='apendice3'">
				<fo:table>
					<fo:table-column column-width="0.2mm"/>
					<fo:table-column column-width="2cm"/>
					<fo:table-column column-width="13cm"/>
					<fo:table-column column-width="0.5cm"/>
					<fo:table-column column-width="10.2cm"/>
					<fo:table-column/>
					<fo:table-body>
						<fo:table-row>
							<fo:table-cell/>
							<fo:table-cell>
								<fo:block text-align="justify" xsl:use-attribute-sets="textoDescAzul" space-before="2mm">
									<xsl:if test="@situacao != ''">
										<xsl:value-of select="@situacao"/>
									</xsl:if>
								</fo:block>
							</fo:table-cell>
							<fo:table-cell>
								<fo:block text-align="justify" xsl:use-attribute-sets="textoDesc" space-before="2mm">
									<xsl:value-of select="@nome"/>
								</fo:block>
							</fo:table-cell>
							<fo:table-cell/>
							<fo:table-cell>
								<fo:block text-align="left" xsl:use-attribute-sets="textoDesc" space-before="2mm" white-space-collapse="false">
									<xsl:value-of select="@justificativa"/>
								</fo:block>
							</fo:table-cell>
							<fo:table-cell/>
						</fo:table-row>
					</fo:table-body>
				</fo:table>
			</xsl:if>
		</fo:block>
	</xsl:template>
	
	<xsl:template match="totalizadores">
		<fo:block space-before="1cm" />
		<fo:table>
			<fo:table-column column-width="4cm"/>
			<fo:table-column />
			<fo:table-body>
				<fo:table-row>
					<fo:table-cell/>
					<fo:table-cell>
						<fo:block space-before="0.1cm" space-after="0.2cm" text-align="left" xsl:use-attribute-sets="textoDescNegrito" >
							Totaliza��es para confer�ncia:
						</fo:block>
					</fo:table-cell>
				</fo:table-row>
			</fo:table-body>
		</fo:table>
		<xsl:apply-templates select="totalizador" />
		<xsl:apply-templates select="total" />
	</xsl:template>

	<xsl:template match="totalizadorValores">
		<fo:block space-before="1cm" />
		<fo:table>
			<fo:table-column column-width="4cm"/>
			<fo:table-column />
			<fo:table-body>
				<fo:table-row>
					<fo:table-cell/>
					<fo:table-cell>
						<fo:block space-before="0.1cm" space-after="0.2cm" text-align="left" xsl:use-attribute-sets="textoDescNegrito" >
							Totaliza��o dos Programas/A��es:
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
			<fo:table-column column-width="5cm"/>
			<fo:table-column column-width="8cm"/>
			<fo:table-column column-width="5cm"/>
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
			<fo:table-column column-width="5cm"/>
			<fo:table-column column-width="8cm"/>
			<fo:table-column column-width="5cm"/>
			<fo:table-body>
				<fo:table-row>
					<fo:table-cell/>
					<fo:table-cell>
						<fo:block space-before="0.3cm" text-align="left" xsl:use-attribute-sets="textoDescNegrito" >
							<xsl:value-of select="@nome"/>
						</fo:block>
					</fo:table-cell>
					<fo:table-cell>
						<fo:block space-before="0.3cm" text-align="right" xsl:use-attribute-sets="textoDescNegrito" >
							<xsl:value-of select="@valor"/>
						</fo:block>
					</fo:table-cell>
				</fo:table-row>
			</fo:table-body>
		</fo:table>
	</xsl:template>
	
	
</xsl:stylesheet>
