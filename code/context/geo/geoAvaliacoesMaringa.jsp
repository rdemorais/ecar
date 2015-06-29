
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
		<b>Avaliações: PR-323 - Maringá - Doutor Camargo (duplicação)</b>

		</td>
	</tr>
</table>

<table class="form" width="100%" border="0" cellpadding="0" cellspacing="0">

		<tr><td colspan=2>&nbsp;</td></tr>
		<tr>
			<td class="label" align="right" width="30%">
				Análise Responsável atribuído por:
			</td>

			<td  title="041 - 3304-8342  / mlfranco@pr.gov.br" onmouseover="javascript:style.cursor='help'">Maurício Luiz de Oliveira Franco</td>
		</tr>
		<tr>
			<td class="label" align="right">Função:</td>
			<td>Responsável Técnico</td>
		</tr>
		

				<tr>

					<td class="label" align="right">Situação:</td>
					<td>Em andamento</td>
				</tr>
				<tr>
					<td class="label" align="right">Cor:</td>
					<td><image src="../images/sVerde3.png"></td>
				</tr>

				<tr>
					<td class="label" align="right">Descrição:</td>
					<td>Serviços de terraplenagem, pavimentação, obras de arte especiais, obras complementares, da duplicação e marginais, da PR-323, trecho Maringá - Paiçandú</td>
				</tr>
				<tr>
					<td class="label" align="right">Observações:</td>
					<td>Serviços concluídos
Marginal esquerda= terraplenagem 100%, pavimentação 80%
Marginal direita= terraplenagem 70%, pavimentação 0%
Pista central = terraplenagem 100%, pavimentação 0%
Obras de arte especiais= 50% , terraplenagem das alças 70%
Drenagem= marginal esquerda 100%, direita 0%

</td>
				</tr>
				<tr>
					<td class="label" align="right">Atualização:</td>
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
								<td class="label">Mês/Ano</td>
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

								 <td>Situaçãodas obras:
Marginal direita: Terraplenagem 90% e pavimentação 20%
Marginal esquerda: Terraplenagem 100% e pavimentação 20% 
Obrtas de arte especiais (trincheiras): 65%
Drenagem: Lado direirto 100% e lado esquerdo 30%
Pista central: Terraplenagem 100% e pavimentação 5%</td>
							</tr> 
							<tr>
								 <td class="label">Observa&ccedil;&otilde;es:</td>
								 <td>ESTIMA-SE QUE 35% DO TOTAL ENCONTRA-SE CONCLUÍDO</td>
							</tr> 
							<tr>
								 <td class="label" colspan="2">&nbsp;</td>

							</tr> 
						
							
							<tr>
								<td class="label">Per&iacute;odo</td>
								<td>Agosto/2006</td>
							</tr>
							<tr>
								<td class="label">Mês/Ano</td>

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
								 <td>Serviços de duplicação de via, que compreendem trabalhos de terraplenagem, pavimentação, marginais e pista central, trincheiras e dranagem</td>
							</tr> 
							<tr>

								 <td class="label">Observa&ccedil;&otilde;es:</td>
								 <td>Vias Marginais: lado esquerdo - terraplenagem 100%, pavimentação 70%; lado direito - terraplenagem 5%.
Pista central: terraplenagem 100%
Obras de arte especiais (trincheiras) : 30% (70% da terraplenagem das alças)
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
								<td class="label">Mês/Ano</td>
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
								 <td>Os serviços estão de acordo com o planejado:
Trincheiras: 10% executados, o que correspondem a parte de fundações e vigas já concretadas.
Terraplenagem: a movimentação de terra já atingiu 15% do total.
Pavimentação: os serviços de base já foram iniciados, tendo sido executados 5%.</td>
							</tr> 
							<tr>
								 <td class="label">Observa&ccedil;&otilde;es:</td>
								 <td>Em execução desvios de serviço, para início das escavações das trincheiras.</td>

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
				Análise CAR atribuído por:
			</td>
			<td  title="041 - 3351-6000  / roferraz@sepl.pr.gov.br" onmouseover="javascript:style.cursor='help'">SEPL</td>
		</tr>
		<tr>
			<td class="label" align="right">Função:</td>

			<td>Administrador</td>
		</tr>
		

				<tr>
					<td class="label" align="right">Situação:</td>
					<td>Em andamento</td>
				</tr>
				<tr>

					<td class="label" align="right">Cor:</td>
					<td><image src="../images/sVerde9.png"></td>
				</tr>
				<tr>
					<td class="label" align="right">Descrição:</td>
					<td>Serviços de terraplenagem, pavimentação, obras de arte especiais, obras complementares, da duplicação e marginais, da PR-323, trecho Maringá - Paiçandú.

Serviços concluídos: 
Vias marginais: esquerda - terraplenagem 100% e pavimentação 80%; direita  -terraplenagem 70%.
Pista central - terraplenagem 100%.
Obras de arte especiais - 50% e terraplenagem das alças 70%.
Drenagem - marginal esquerda 100%.</td>
				</tr>

				<tr>
					<td class="label" align="right">Observações:</td>
					<td>Término previsto para 2007.</td>
				</tr>
				<tr>
					<td class="label" align="right">Atualização:</td>
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
								<td class="label">Mês/Ano</td>
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
Situação das obras:
Vias marginais: direita - terraplenagem 90% e pavimentação 20%; 
esquerda - terraplenagem 100% e pavimentação 20%.
Obras de arte especiais (trincheiras): 65%.
Drenagem: lado direito 100% e lado esquerdo 30%.
Pista central: terraplenagem 100% e pavimentação 5%.</td>
							</tr> 
							<tr>
								 <td class="label">Observa&ccedil;&otilde;es:</td>

								 <td>Término previsto para 2007.</td>
							</tr> 
							<tr>
								 <td class="label" colspan="2">&nbsp;</td>
							</tr> 
						
							
							<tr>
								<td class="label">Per&iacute;odo</td>
								<td>Agosto/2006</td>

							</tr>
							<tr>
								<td class="label">Mês/Ano</td>
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

								 <td>Serviços de duplicação de via, compreendendo trabalhos de terraplenagem, pavimentação, marginais e pista central, trincheiras e drenagem.
Obras em andamento: 
- Vias Marginais: lado esquerdo - terraplenagem 100%, pavimentação 70%; lado direito - terraplenagem 5%.
- Pista central: terraplenagem 100%.
- Obras de arte especiais (trincheiras): 30% (70% da terraplenagem das alças),
- Drenagem: marginal esquerda 100%.</td>
							</tr> 
							<tr>
								 <td class="label">Observa&ccedil;&otilde;es:</td>
								 <td>Término previsto para 2007.</td>
							</tr> 
							<tr>
								 <td class="label" colspan="2">&nbsp;</td>

							</tr> 
						
							
							<tr>
								<td class="label">Per&iacute;odo</td>
								<td>Julho/2006</td>
							</tr>
							<tr>
								<td class="label">Mês/Ano</td>

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
Trincheiras correspondente a parte de fundações e vigas já concretadas: 10% executados. 
Terraplenagem: a movimentação de terra atingiu 15% do total. 
Pavimentação: os serviços de base já foram iniciados, 5% executados. 
Desvios de serviço para início das escavações das trincheiras: em execução.</td>
							</tr> 
							<tr>

								 <td class="label">Observa&ccedil;&otilde;es:</td>
								 <td>Término previsto para 2007.</td>
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