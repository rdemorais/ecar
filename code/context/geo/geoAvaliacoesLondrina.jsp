
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
		<b>Avalia��es: Jardim Bot�nico de Londrina</b>

		</td>
	</tr>
</table>

<table class="form" width="100%" border="0" cellpadding="0" cellspacing="0">

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
					<td>Em execu��o</td>
				</tr>
				<tr>
					<td class="label" align="right">Cor:</td>
					<td><image src="../images/sVerde9.png"></td>
				</tr>

				<tr>
					<td class="label" align="right">Descri��o:</td>
					<td>Na 1� fase est� prevista a constru��o do centro administrativo, anfiteatro, caf�, restaurante, loja, espa�o para exposi��es, anfiteatro de arena para 1.000 pessoas, estufas para climatiza��o, constru��o de via de acesso, 5 km de cercas, terraplanagem, constru��o de dois lagos, parte da pista de caminhada, ciclovia e vias internas.

As obras da via de acesso est�o em fase final, devendo ser entregue nos pr�ximos 30 dias.
Conclu�do o or�amento do Jardim e a SEOP dever� enviar � SEMA ainda em setembro para encaminhamento das primeiras licita��es de obras.</td>
				</tr>
				<tr>
					<td class="label" align="right">Observa��es:</td>
					<td>Investimento previsto em 2006 de R$ 8 milh�es (R$ 1,5 milh�o provenientes de medida compensat�ria e R$ 5,5 milh�es com recursos do FEMA). 

T�rmino da 1� fase no final de 2006. 

No �ltimo dia 11 de setembro foi encaminhado pela SEMA pedido de R$ 10 milh�es para executar as obras no ano de 2007, � fundamental para o bom andamento do Jardim que seja liberado este recurso, uma vez que o or�amento para 2006 era todo com recursos do Fundo Estadual de Meio Ambiente (FEMA) e com as primeiras obras a serem licitadas ainda este ano, todo o recurso ser� usado.</td>

				</tr>
				<tr>
					<td class="label" align="right">Atualiza��o:</td>
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

								 <td>Via de acesso do Jardim Bot�nico: em fase final, a obra dever� ser entregue em novembro/2006.
O or�amento final foi fechado e encaminhado da SEOP/DECOM para a SEMA em 20 de outubro.
Solicitado ao DECOM que separasse as obras de infra-estrutura, pois ainda tem recursos pra executar este ano, o processo de licita��o destas obras dever� ser iniciado ainda em outubro.</td>
							</tr> 
							<tr>
								 <td class="label">Observa&ccedil;&otilde;es:</td>
								 <td>T�rmino da 1� fase no final de 2006.</td>
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
								 <td>Em execu��o</td>
							</tr> 	
							<tr>

								 <td class="label">Cor:</td>
								 <td><img src="../images/sVerde9.png" align="top"></td>
							</tr> 
							<tr>
								 <td class="label">Descri&ccedil;&atilde;o:</td>
								 <td>Na 1� fase est� prevista a constru��o do centro administrativo, anfiteatro, caf�, restaurante, loja, espa�o para exposi��es, anfiteatro de arena para 1.000 pessoas, estufas para climatiza��o, constru��o de via de acesso, 5 km de cercas, terraplanagem, constru��o de dois lagos, parte da pista de caminhada, ciclovia e vias internas.

Via de acesso: obras em execu��o.
As obras do complexo do Jardim Bot�nico est�o na SEOP aguardando or�amento final para posterior encaminhamento das licita��es das obras, as quais poder�o ser realizadas com recursos dispon�veis neste ano.</td>
							</tr> 
							<tr>

								 <td class="label">Observa&ccedil;&otilde;es:</td>
								 <td>Investimento previsto em 2006 de R$ 8 milh�es (R$ 1,5 milh�o provenientes de medida compensat�ria e R$ 5,5 milh�es com recursos do FEMA). 
T�rmino da 1� fase no final de 2006. 
Para 2007 h� necessidade de se garantir novos recursos no or�amento para a continuidade do projeto, uma vez que h� grande expectativa na popula��o em rela��o ao Jardim Bot�nico.</td>
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
								 <td>Em execu��o</td>
							</tr> 	
							<tr>
								 <td class="label">Cor:</td>
								 <td><img src="../images/sVerde9.png" align="top"></td>
							</tr> 
							<tr>

								 <td class="label">Descri&ccedil;&atilde;o:</td>
								 <td>Na 1� fase est� prevista a constru��o do centro administrativo, anfiteatro, caf�, restaurante, loja, espa�o para exposi��es, anfiteatro de arena para 1.000 pessoas, estufas para climatiza��o, constru��o de via de acesso, 5 km de cercas, terraplanagem, constru��o de dois lagos, parte da pista de caminhada, ciclovia e vias internas.

Via de acesso: obras em execu��o.
Em julho foi encaminhado ao DECOM o or�amento final do Jardim Bot�nico, ap�s a confer�ncia dos pre�os contidos no or�amento, ser�o feitos os editais para as licita��es das obras f�sicas. No entanto, os recursos s�o insuficientes para a realiza��o de todas as licita��es ainda este ano, o que n�o acarretar� nenhum tipo de preju�zo para o Jardim pois outras obras ser�o feitas at� o final do ano. </td>
							</tr> 
							<tr>
								 <td class="label">Observa&ccedil;&otilde;es:</td>
								 <td>Investimento previsto em 2006 de R$ 8 milh�es (R$ 1,5 milh�o provenientes de medida compensat�ria e R$ 5,5 milh�es com recursos do FEMA). T�rmino da 1� fase no final de 2006.

Para 2007 h� necessidade de se garantir novos recursos no or�amento para a continuidade do projeto, uma vez que h� grande expectativa na popula��o em rela��o ao Jardim Bot�nico.</td>

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
				An�lise Respons�vel atribu�do por:
			</td>
			<td  title="043 - 33240991   / rramires@pr.gov.br" onmouseover="javascript:style.cursor='help'">Ricardo Jos� Durante Ramires</td>
		</tr>
		<tr>
			<td class="label" align="right">Fun��o:</td>

			<td>Respons�vel T�cnico</td>
		</tr>
		

				<tr>
					<td class="label" align="right">Situa��o:</td>
					<td>Em execu��o</td>
				</tr>
				<tr>

					<td class="label" align="right">Cor:</td>
					<td><image src="../images/sVerde3.png"></td>
				</tr>
				<tr>
					<td class="label" align="right">Descri��o:</td>
					<td>As obras da via de acesso ao Jardim Bot�nico de Londrina est�o no final e dever� ser entregue nos pr�ximos 30 dias, o or�amento do Jardim ficou pronto e a Secretaria de Obras dever� enviar � Secretaria de Meio Ambiente ainda esta semana para que seja encaminhado as primeiras licita��es de obras dentro da �rea do Jardim Bot�nico, no �ltimo dia 11 de setembro foi encaminhado pela SEMA pedido de R$10 milh�es de reais para executar as obras no ano de 2007, � fundamental para o bom andamento do Jardim que seja liberado este recurso, uma vez que o or�amento que disp�nhamos para o exerc�cio de 2006 era todo do FEMA(Fundo Estadual de Meio Ambiente) e com as primeiras obras a serem licitadas ainda este ano, todo o recurso ser� usado.</td>
				</tr>

				<tr>
					<td class="label" align="right">Observa��es:</td>
					<td></td>
				</tr>
				<tr>
					<td class="label" align="right">Atualiza��o:</td>
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
								 <td>Estamos na fase final da constru��o da via de acesso do Jardim Bot�nico de Londrina, a obra dever� ser entregue nas pr�ximas semanas, o or�amento final do Jardim foi fechado e encaminhado da Secretaria de Obras(DECOM) para a SEMA, na �ltima sexta-feira dia 20.
Foi feito o pedido ao DECOM que separasse as obras de infra-estrutura que ainda temos recursos pra executar este ano, o processo de licita��o destas obras dever� ser iniciado ainda esta semana, � o relat�rio, qualquer d�vida ou informa��o estou a disposi��o para esclarescimentos, Ricardo Ramires.</td>
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
								<td class="label">M�s/Ano</td>
								<td>08/2006</td>
							</tr>
							<tr>
								 <td class="label">Situa&ccedil;&atilde;o:</td>

								 <td>Em execu��o</td>
							</tr> 	
							<tr>
								 <td class="label">Cor:</td>
								 <td><img src="../images/sVerde3.png" align="top"></td>
							</tr> 
							<tr>
								 <td class="label">Descri&ccedil;&atilde;o:</td>

								 <td>As obras da via de acesso est�o em execu��o, as obras do complexo do Jardim Bot�nico de Londrina est�o na Secretaria de Obras aguardando o or�amento final para encaminharmos as licita��es das obras que poder�o serem realizadas ainda com os recursos que est�o dispon�veis este ano, � o relat�rio. </td>
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
								<td class="label">M�s/Ano</td>

								<td>07/2006</td>
							</tr>
							<tr>
								 <td class="label">Situa&ccedil;&atilde;o:</td>
								 <td>Em execu��o</td>
							</tr> 	
							<tr>

								 <td class="label">Cor:</td>
								 <td><img src="../images/sVerde3.png" align="top"></td>
							</tr> 
							<tr>
								 <td class="label">Descri&ccedil;&atilde;o:</td>
								 <td>As obras da via de acesso est�o em execu��o, neste m�s foi encaminhado ao DECOM o or�amento final do Jardim Bot�nico de Londrina, ap�s a confer�ncia dos pre�os contidos no or�amento, ser�o feitos os editais para as licita��es das obras f�sicas do Jardim propriamente dito, no entanto, os recursos dispon�veis s�o insuficientes para a realiza��o de todas as licita��es ainda este ano, isso n�o acarretar� nenhum tipo de preju�zo para o Jardim pois outras obras ser�o feitas at� o final do ano, por�m para o exerc�cio de 2007 h� a necessidade de se garantir novos recursos no or�amento para que o projeto n�o pare, uma vez que h� grande expectativa na popula��o em rela��o ao Jardim Bot�nico de Londrina, este � o relat�rio, fico a disposi��o para eventuais d�vidas ou informa��es que se fizerem necess�rias.</td>
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