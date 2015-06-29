
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
		<b>Avaliações: Jardim Botânico de Londrina</b>

		</td>
	</tr>
</table>

<table class="form" width="100%" border="0" cellpadding="0" cellspacing="0">

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
					<td>Em execução</td>
				</tr>
				<tr>
					<td class="label" align="right">Cor:</td>
					<td><image src="../images/sVerde9.png"></td>
				</tr>

				<tr>
					<td class="label" align="right">Descrição:</td>
					<td>Na 1ª fase está prevista a construção do centro administrativo, anfiteatro, café, restaurante, loja, espaço para exposições, anfiteatro de arena para 1.000 pessoas, estufas para climatização, construção de via de acesso, 5 km de cercas, terraplanagem, construção de dois lagos, parte da pista de caminhada, ciclovia e vias internas.

As obras da via de acesso estão em fase final, devendo ser entregue nos próximos 30 dias.
Concluído o orçamento do Jardim e a SEOP deverá enviar à SEMA ainda em setembro para encaminhamento das primeiras licitações de obras.</td>
				</tr>
				<tr>
					<td class="label" align="right">Observações:</td>
					<td>Investimento previsto em 2006 de R$ 8 milhões (R$ 1,5 milhão provenientes de medida compensatória e R$ 5,5 milhões com recursos do FEMA). 

Término da 1ª fase no final de 2006. 

No último dia 11 de setembro foi encaminhado pela SEMA pedido de R$ 10 milhões para executar as obras no ano de 2007, é fundamental para o bom andamento do Jardim que seja liberado este recurso, uma vez que o orçamento para 2006 era todo com recursos do Fundo Estadual de Meio Ambiente (FEMA) e com as primeiras obras a serem licitadas ainda este ano, todo o recurso será usado.</td>

				</tr>
				<tr>
					<td class="label" align="right">Atualização:</td>
					<td>26/09/2006</td>
				</tr>
		
		<tr><td colspan=2>&nbsp;</td></tr>
		<tr><td class="espacador" colspan=2><img src="../images/pixel.gif"></td></tr>
		
		<!--  3 Ultimas Posicoes... -->

		
		<tr class="bgBranco" id="linkMostrar1">
			<td align="right"></td>			
			<td>&nbsp;</td>
		</tr>
		<tr class="bgBranco" id="linkEsconder1" style="display:none">
			<td align="right"><a href="javascript:history.back()">Voltar</a></td>			
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

								 <td>Via de acesso do Jardim Botânico: em fase final, a obra deverá ser entregue em novembro/2006.
O orçamento final foi fechado e encaminhado da SEOP/DECOM para a SEMA em 20 de outubro.
Solicitado ao DECOM que separasse as obras de infra-estrutura, pois ainda tem recursos pra executar este ano, o processo de licitação destas obras deverá ser iniciado ainda em outubro.</td>
							</tr> 
							<tr>
								 <td class="label">Observa&ccedil;&otilde;es:</td>
								 <td>Término da 1ª fase no final de 2006.</td>
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
								 <td>Em execução</td>
							</tr> 	
							<tr>

								 <td class="label">Cor:</td>
								 <td><img src="../images/sVerde9.png" align="top"></td>
							</tr> 
							<tr>
								 <td class="label">Descri&ccedil;&atilde;o:</td>
								 <td>Na 1ª fase está prevista a construção do centro administrativo, anfiteatro, café, restaurante, loja, espaço para exposições, anfiteatro de arena para 1.000 pessoas, estufas para climatização, construção de via de acesso, 5 km de cercas, terraplanagem, construção de dois lagos, parte da pista de caminhada, ciclovia e vias internas.

Via de acesso: obras em execução.
As obras do complexo do Jardim Botânico estão na SEOP aguardando orçamento final para posterior encaminhamento das licitações das obras, as quais poderão ser realizadas com recursos disponíveis neste ano.</td>
							</tr> 
							<tr>

								 <td class="label">Observa&ccedil;&otilde;es:</td>
								 <td>Investimento previsto em 2006 de R$ 8 milhões (R$ 1,5 milhão provenientes de medida compensatória e R$ 5,5 milhões com recursos do FEMA). 
Término da 1ª fase no final de 2006. 
Para 2007 há necessidade de se garantir novos recursos no orçamento para a continuidade do projeto, uma vez que há grande expectativa na população em relação ao Jardim Botânico.</td>
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
								 <td>Em execução</td>
							</tr> 	
							<tr>
								 <td class="label">Cor:</td>
								 <td><img src="../images/sVerde9.png" align="top"></td>
							</tr> 
							<tr>

								 <td class="label">Descri&ccedil;&atilde;o:</td>
								 <td>Na 1ª fase está prevista a construção do centro administrativo, anfiteatro, café, restaurante, loja, espaço para exposições, anfiteatro de arena para 1.000 pessoas, estufas para climatização, construção de via de acesso, 5 km de cercas, terraplanagem, construção de dois lagos, parte da pista de caminhada, ciclovia e vias internas.

Via de acesso: obras em execução.
Em julho foi encaminhado ao DECOM o orçamento final do Jardim Botânico, após a conferência dos preços contidos no orçamento, serão feitos os editais para as licitações das obras físicas. No entanto, os recursos são insuficientes para a realização de todas as licitações ainda este ano, o que não acarretará nenhum tipo de prejuízo para o Jardim pois outras obras serão feitas até o final do ano. </td>
							</tr> 
							<tr>
								 <td class="label">Observa&ccedil;&otilde;es:</td>
								 <td>Investimento previsto em 2006 de R$ 8 milhões (R$ 1,5 milhão provenientes de medida compensatória e R$ 5,5 milhões com recursos do FEMA). Término da 1ª fase no final de 2006.

Para 2007 há necessidade de se garantir novos recursos no orçamento para a continuidade do projeto, uma vez que há grande expectativa na população em relação ao Jardim Botânico.</td>

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
				Análise Responsável atribuído por:
			</td>
			<td  title="043 - 33240991   / rramires@pr.gov.br" onmouseover="javascript:style.cursor='help'">Ricardo José Durante Ramires</td>
		</tr>
		<tr>
			<td class="label" align="right">Função:</td>

			<td>Responsável Técnico</td>
		</tr>
		

				<tr>
					<td class="label" align="right">Situação:</td>
					<td>Em execução</td>
				</tr>
				<tr>

					<td class="label" align="right">Cor:</td>
					<td><image src="../images/sVerde3.png"></td>
				</tr>
				<tr>
					<td class="label" align="right">Descrição:</td>
					<td>As obras da via de acesso ao Jardim Botânico de Londrina estão no final e deverá ser entregue nos próximos 30 dias, o orçamento do Jardim ficou pronto e a Secretaria de Obras deverá enviar à Secretaria de Meio Ambiente ainda esta semana para que seja encaminhado as primeiras licitações de obras dentro da área do Jardim Botânico, no último dia 11 de setembro foi encaminhado pela SEMA pedido de R$10 milhões de reais para executar as obras no ano de 2007, é fundamental para o bom andamento do Jardim que seja liberado este recurso, uma vez que o orçamento que dispúnhamos para o exercício de 2006 era todo do FEMA(Fundo Estadual de Meio Ambiente) e com as primeiras obras a serem licitadas ainda este ano, todo o recurso será usado.</td>
				</tr>

				<tr>
					<td class="label" align="right">Observações:</td>
					<td></td>
				</tr>
				<tr>
					<td class="label" align="right">Atualização:</td>
					<td>26/09/2006</td>

				</tr>
		
		<tr><td colspan=2>&nbsp;</td></tr>
		<tr><td class="espacador" colspan=2><img src="../images/pixel.gif"></td></tr>
		
		<!--  3 Ultimas Posicoes... -->
		
		<tr class="bgBranco" id="linkMostrar2">
			<td align="right"></td>			
			<td>&nbsp;</td>
		</tr>

		<tr class="bgBranco" id="linkEsconder2">
			<td colspan=2 align="center"><a href="javascript:history.back()">Voltar</a></td>			
			
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
								 <td>Estamos na fase final da construção da via de acesso do Jardim Botânico de Londrina, a obra deverá ser entregue nas próximas semanas, o orçamento final do Jardim foi fechado e encaminhado da Secretaria de Obras(DECOM) para a SEMA, na última sexta-feira dia 20.
Foi feito o pedido ao DECOM que separasse as obras de infra-estrutura que ainda temos recursos pra executar este ano, o processo de licitação destas obras deverá ser iniciado ainda esta semana, é o relatório, qualquer dúvida ou informação estou a disposição para esclarescimentos, Ricardo Ramires.</td>
							</tr> 
							<tr>
								 <td class="label">Observa&ccedil;&otilde;es:</td>

								 <td>N/I</td>
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

								 <td>Em execução</td>
							</tr> 	
							<tr>
								 <td class="label">Cor:</td>
								 <td><img src="../images/sVerde3.png" align="top"></td>
							</tr> 
							<tr>
								 <td class="label">Descri&ccedil;&atilde;o:</td>

								 <td>As obras da via de acesso estão em execução, as obras do complexo do Jardim Botânico de Londrina estão na Secretaria de Obras aguardando o orçamento final para encaminharmos as licitações das obras que poderão serem realizadas ainda com os recursos que estão disponíveis este ano, é o relatório. </td>
							</tr> 
							<tr>
								 <td class="label">Observa&ccedil;&otilde;es:</td>
								 <td>N/I</td>
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
								 <td>Em execução</td>
							</tr> 	
							<tr>

								 <td class="label">Cor:</td>
								 <td><img src="../images/sVerde3.png" align="top"></td>
							</tr> 
							<tr>
								 <td class="label">Descri&ccedil;&atilde;o:</td>
								 <td>As obras da via de acesso estão em execução, neste mês foi encaminhado ao DECOM o orçamento final do Jardim Botânico de Londrina, após a conferência dos preços contidos no orçamento, serão feitos os editais para as licitações das obras físicas do Jardim propriamente dito, no entanto, os recursos disponíveis são insuficientes para a realização de todas as licitações ainda este ano, isso não acarretará nenhum tipo de prejuízo para o Jardim pois outras obras serão feitas até o final do ano, porém para o exercício de 2007 há a necessidade de se garantir novos recursos no orçamento para que o projeto não pare, uma vez que há grande expectativa na população em relação ao Jardim Botânico de Londrina, este é o relatório, fico a disposição para eventuais dúvidas ou informações que se fizerem necessárias.</td>
							</tr> 
							<tr>

								 <td class="label">Observa&ccedil;&otilde;es:</td>
								 <td>N/I</td>
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