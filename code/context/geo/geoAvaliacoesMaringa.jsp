
<%@ include file="../login/validaAcesso.jsp"%>
<%@ include file="../frm_global.jsp"%>

<%@ taglib uri="/WEB-INF/ecar-util.tld" prefix="util" %>
<%@ taglib uri="/WEB-INF/ecar-combo.tld" prefix="combo" %>

<html lang="pt-br">
<head> 
<%@ include file="../include/meta.jsp"%>
<%@ include file="/titulo.jsp"%>
<link rel="stylesheet" href="<%=_pathEcar%>/css/style_<%=new ecar.dao.ConfiguracaoDao(request).getConfiguracao().getEstilo().getNome()%>.css" type="text/css">
<script language="javascript" src="../js/menu_retratil.js"></script>
<script language="javascript" src="../js/popUp.js"></script>
</head>
<%
try {
%>
<%@ include file="../cabecalho.jsp"%> 
<%@ include file="../divmenu.jsp"%> 
<body onload="javascript:doLoad()"> 
<div id="conteudo">
<%@ include file="/titulo_tela.jsp"%>

<form name="form" method="post">
	<input type="hidden" name="codAri" value="">	
	<input type="hidden" name="itemDoNivelClicado" value="">	
	<input type="hidden" name="pause" value="N">
		
	<div id="subconteudo">
<table class="barrabotoes" cellpadding="0" cellspacing="0">
	<tr>
		<td align="left">
		<b>Avalia��es: PR-323 - Maring� - Doutor Camargo (duplica��o)</b>

		</td>
	</tr>
</table>

<table class="form" width="100%" border="0" cellpadding="0" cellspacing="0">

		<tr><td colspan=2>&nbsp;</td></tr>
		<tr>
			<td class="label" align="right" width="30%">
				An�lise Respons�vel atribu�do por:
			</td>

			<td  title="041 - 3304-8342  / mlfranco@pr.gov.br" onmouseover="javascript:style.cursor='help'">Maur�cio Luiz de Oliveira Franco</td>
		</tr>
		<tr>
			<td class="label" align="right">Fun��o:</td>
			<td>Respons�vel T�cnico</td>
		</tr>
		

				<tr>

					<td class="label" align="right">Situa��o:</td>
					<td>Em andamento</td>
				</tr>
				<tr>
					<td class="label" align="right">Cor:</td>
					<td><image src="../images/sVerde3.png"></td>
				</tr>

				<tr>
					<td class="label" align="right">Descri��o:</td>
					<td>Servi�os de terraplenagem, pavimenta��o, obras de arte especiais, obras complementares, da duplica��o e marginais, da PR-323, trecho Maring� - Pai�and�</td>
				</tr>
				<tr>
					<td class="label" align="right">Observa��es:</td>
					<td>Servi�os conclu�dos
Marginal esquerda= terraplenagem 100%, pavimenta��o 80%
Marginal direita= terraplenagem 70%, pavimenta��o 0%
Pista central = terraplenagem 100%, pavimenta��o 0%
Obras de arte especiais= 50% , terraplenagem das al�as 70%
Drenagem= marginal esquerda 100%, direita 0%

</td>
				</tr>
				<tr>
					<td class="label" align="right">Atualiza��o:</td>
					<td>25/09/2006</td>
				</tr>
		
		<tr><td colspan=2>&nbsp;</td></tr>
		<tr><td class="espacador" colspan=2><img src="../images/pixel.gif"></td></tr>

		
		<!--  3 Ultimas Posicoes... -->
		
		<tr class="bgBranco" id="linkMostrar2">
			<td align="right"></td>			
			<td>&nbsp;</td>
		</tr>
		<tr class="bgBranco" id="linkEsconder2" style="display:none">
			<td align="right"></td>			
			<td>&nbsp;</td>

		</tr>
		<tr class="bgBranco">
		<td colspan="2">
				<table id="ultimasPosicoes2" style="display:none" class="bgBranco">
					
							
							<tr>
								<td class="label">Per&iacute;odo</td>
								<td>Outubro/2006</td>

							</tr>
							<tr>
								<td class="label">M�s/Ano</td>
								<td>10/2006</td>
							</tr>
							<tr>
								 <td class="label">Situa&ccedil;&atilde;o:</td>

								 <td>Em andamento</td>
							</tr> 	
							<tr>
								 <td class="label">Cor:</td>
								 <td><img src="../images/sVerde3.png" align="top"></td>
							</tr> 
							<tr>
								 <td class="label">Descri&ccedil;&atilde;o:</td>

								 <td>Situa��odas obras:
Marginal direita: Terraplenagem 90% e pavimenta��o 20%
Marginal esquerda: Terraplenagem 100% e pavimenta��o 20% 
Obrtas de arte especiais (trincheiras): 65%
Drenagem: Lado direirto 100% e lado esquerdo 30%
Pista central: Terraplenagem 100% e pavimenta��o 5%</td>
							</tr> 
							<tr>
								 <td class="label">Observa&ccedil;&otilde;es:</td>
								 <td>ESTIMA-SE QUE 35% DO TOTAL ENCONTRA-SE CONCLU�DO</td>
							</tr> 
							<tr>
								 <td class="label" colspan="2">&nbsp;</td>

							</tr> 
						
							
							<tr>
								<td class="label">Per&iacute;odo</td>
								<td>Agosto/2006</td>
							</tr>
							<tr>
								<td class="label">M�s/Ano</td>

								<td>08/2006</td>
							</tr>
							<tr>
								 <td class="label">Situa&ccedil;&atilde;o:</td>
								 <td>Em dia</td>
							</tr> 	
							<tr>

								 <td class="label">Cor:</td>
								 <td><img src="../images/sVerde3.png" align="top"></td>
							</tr> 
							<tr>
								 <td class="label">Descri&ccedil;&atilde;o:</td>
								 <td>Servi�os de duplica��o de via, que compreendem trabalhos de terraplenagem, pavimenta��o, marginais e pista central, trincheiras e dranagem</td>
							</tr> 
							<tr>

								 <td class="label">Observa&ccedil;&otilde;es:</td>
								 <td>Vias Marginais: lado esquerdo - terraplenagem 100%, pavimenta��o 70%; lado direito - terraplenagem 5%.
Pista central: terraplenagem 100%
Obras de arte especiais (trincheiras) : 30% (70% da terraplenagem das al�as)
Drenagem: Marginal esuqerda 100%</td>
							</tr> 
							<tr>
								 <td class="label" colspan="2">&nbsp;</td>
							</tr> 
						
							
							<tr>
								<td class="label">Per&iacute;odo</td>

								<td>Julho/2006</td>
							</tr>
							<tr>
								<td class="label">M�s/Ano</td>
								<td>07/2006</td>
							</tr>
							<tr>

								 <td class="label">Situa&ccedil;&atilde;o:</td>
								 <td>Em andamento</td>
							</tr> 	
							<tr>
								 <td class="label">Cor:</td>
								 <td><img src="../images/sVerde3.png" align="top"></td>
							</tr> 
							<tr>

								 <td class="label">Descri&ccedil;&atilde;o:</td>
								 <td>Os servi�os est�o de acordo com o planejado:
Trincheiras: 10% executados, o que correspondem a parte de funda��es e vigas j� concretadas.
Terraplenagem: a movimenta��o de terra j� atingiu 15% do total.
Pavimenta��o: os servi�os de base j� foram iniciados, tendo sido executados 5%.</td>
							</tr> 
							<tr>
								 <td class="label">Observa&ccedil;&otilde;es:</td>
								 <td>Em execu��o desvios de servi�o, para in�cio das escava��es das trincheiras.</td>

							</tr> 
							<tr>
								 <td class="label" colspan="2">&nbsp;</td>
							</tr> 
						
				</table>
			</td>
		</tr>
		<tr><td class="espacador" colspan=2><img src="../images/pixel.gif"></td></tr>
		<tr><td colspan=2>&nbsp;</td></tr>
		
		<!-- /Ultimas Posicoes... -->

		
		<tr><td colspan=2>&nbsp;</td></tr>
		<tr>
			<td class="label" align="right" width="30%">
				An�lise CAR atribu�do por:
			</td>
			<td  title="041 - 3351-6000  / roferraz@sepl.pr.gov.br" onmouseover="javascript:style.cursor='help'">SEPL</td>
		</tr>
		<tr>
			<td class="label" align="right">Fun��o:</td>

			<td>Administrador</td>
		</tr>
		

				<tr>
					<td class="label" align="right">Situa��o:</td>
					<td>Em andamento</td>
				</tr>
				<tr>

					<td class="label" align="right">Cor:</td>
					<td><image src="../images/sVerde9.png"></td>
				</tr>
				<tr>
					<td class="label" align="right">Descri��o:</td>
					<td>Servi�os de terraplenagem, pavimenta��o, obras de arte especiais, obras complementares, da duplica��o e marginais, da PR-323, trecho Maring� - Pai�and�.

Servi�os conclu�dos: 
Vias marginais: esquerda - terraplenagem 100% e pavimenta��o 80%; direita  -terraplenagem 70%.
Pista central - terraplenagem 100%.
Obras de arte especiais - 50% e terraplenagem das al�as 70%.
Drenagem - marginal esquerda 100%.</td>
				</tr>

				<tr>
					<td class="label" align="right">Observa��es:</td>
					<td>T�rmino previsto para 2007.</td>
				</tr>
				<tr>
					<td class="label" align="right">Atualiza��o:</td>
					<td>25/09/2006</td>

				</tr>
		
		<tr><td colspan=2>&nbsp;</td></tr>
		<tr><td class="espacador" colspan=2><img src="../images/pixel.gif"></td></tr>
		
		<!--  3 Ultimas Posicoes... -->
		
		<tr class="bgBranco" id="linkEsconder2">
			<td colspan=2 align="center"><a href="javascript:history.back()">Voltar</a></td>			
			
		</tr>

		<tr class="bgBranco" id="linkEsconder1" style="display:none">
			<td align="right"></a></td>			
			<td>&nbsp;</td>
		</tr>
		<tr class="bgBranco">
		<td colspan="2">
				<table id="ultimasPosicoes1" style="display:none" class="bgBranco">
					
							
							<tr>

								<td class="label">Per&iacute;odo</td>
								<td>Outubro/2006</td>
							</tr>
							<tr>
								<td class="label">M�s/Ano</td>
								<td>10/2006</td>

							</tr>
							<tr>
								 <td class="label">Situa&ccedil;&atilde;o:</td>
								 <td>Em andamento</td>
							</tr> 	
							<tr>
								 <td class="label">Cor:</td>

								 <td><img src="../images/sVerde9.png" align="top"></td>
							</tr> 
							<tr>
								 <td class="label">Descri&ccedil;&atilde;o:</td>
								 <td>Obra em andamento com 35% executado.
Situa��o das obras:
Vias marginais: direita - terraplenagem 90% e pavimenta��o 20%; 
esquerda - terraplenagem 100% e pavimenta��o 20%.
Obras de arte especiais (trincheiras): 65%.
Drenagem: lado direito 100% e lado esquerdo 30%.
Pista central: terraplenagem 100% e pavimenta��o 5%.</td>
							</tr> 
							<tr>
								 <td class="label">Observa&ccedil;&otilde;es:</td>

								 <td>T�rmino previsto para 2007.</td>
							</tr> 
							<tr>
								 <td class="label" colspan="2">&nbsp;</td>
							</tr> 
						
							
							<tr>
								<td class="label">Per&iacute;odo</td>
								<td>Agosto/2006</td>

							</tr>
							<tr>
								<td class="label">M�s/Ano</td>
								<td>08/2006</td>
							</tr>
							<tr>
								 <td class="label">Situa&ccedil;&atilde;o:</td>

								 <td>Em andamento</td>
							</tr> 	
							<tr>
								 <td class="label">Cor:</td>
								 <td><img src="../images/sVerde9.png" align="top"></td>
							</tr> 
							<tr>
								 <td class="label">Descri&ccedil;&atilde;o:</td>

								 <td>Servi�os de duplica��o de via, compreendendo trabalhos de terraplenagem, pavimenta��o, marginais e pista central, trincheiras e drenagem.
Obras em andamento: 
- Vias Marginais: lado esquerdo - terraplenagem 100%, pavimenta��o 70%; lado direito - terraplenagem 5%.
- Pista central: terraplenagem 100%.
- Obras de arte especiais (trincheiras): 30% (70% da terraplenagem das al�as),
- Drenagem: marginal esquerda 100%.</td>
							</tr> 
							<tr>
								 <td class="label">Observa&ccedil;&otilde;es:</td>
								 <td>T�rmino previsto para 2007.</td>
							</tr> 
							<tr>
								 <td class="label" colspan="2">&nbsp;</td>

							</tr> 
						
							
							<tr>
								<td class="label">Per&iacute;odo</td>
								<td>Julho/2006</td>
							</tr>
							<tr>
								<td class="label">M�s/Ano</td>

								<td>07/2006</td>
							</tr>
							<tr>
								 <td class="label">Situa&ccedil;&atilde;o:</td>
								 <td>Em andamento</td>
							</tr> 	
							<tr>

								 <td class="label">Cor:</td>
								 <td><img src="../images/sVerde9.png" align="top"></td>
							</tr> 
							<tr>
								 <td class="label">Descri&ccedil;&atilde;o:</td>
								 <td>Obras em andamento. 
Trincheiras correspondente a parte de funda��es e vigas j� concretadas: 10% executados. 
Terraplenagem: a movimenta��o de terra atingiu 15% do total. 
Pavimenta��o: os servi�os de base j� foram iniciados, 5% executados. 
Desvios de servi�o para in�cio das escava��es das trincheiras: em execu��o.</td>
							</tr> 
							<tr>

								 <td class="label">Observa&ccedil;&otilde;es:</td>
								 <td>T�rmino previsto para 2007.</td>
							</tr> 
							<tr>
								 <td class="label" colspan="2">&nbsp;</td>
							</tr> 
						
				</table>
			</td>

		</tr>
		
		<!-- /Ultimas Posicoes... -->
		
</table>
	</div>	
	
	
</form>
	<%

} catch (ECARException e) {
	org.apache.log4j.Logger.getLogger(this.getClass()).error(e);
	Mensagem.alert(_jspx_page_context, e.getMessageKey());
} catch (Exception e){
%>
	<%@ include file="/excecao.jsp"%>
<%
}
%>
	
</div>

</body>
<%@ include file="../include/ocultarAguarde.jsp"%>
<%@ include file="../include/estadoMenu.jsp"%>
<%@ include file="../include/mensagemAlert.jsp" %>
</html>