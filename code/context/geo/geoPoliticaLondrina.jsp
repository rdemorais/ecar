<html lang="pt-br">
<head> 
<%@ include file="../include/meta.jsp"%>
<%@ include file="/titulo.jsp"%>
<link rel="stylesheet" href="<%=_pathEcar%>/css/style_<%=new ecar.dao.ConfiguracaoDao(request).getConfiguracao().getEstilo().getNome()%>.css" type="text/css">
<script language="javascript" src="../js/menu_retratil.js"></script>
<script language="javascript" src="../js/popUp.js"></script>
</head>

<body class="bgBranco" TEXT="Black" LINK="blue">
<br>
<br>

	<table width="100%" border="0" cellpadding="0" cellspacing="0">
		<a name="ancoraPolitica" id="ancoraPolitica">
		<tr>
			<td width="25%" align="center"><h1>Dados Políticos - Município de Londrina</h1></td>		
			<td width="75%"></td>	
		</tr>
		</a>
	</table>
<br>
<table width=100% border=0>
<font size=1 face=verdana color="black"><b>
	
<tr><td colspan=3><font size=2><b>Prefeito</td></tr>
<tr><td colspan=3 height=2 class="bgPreto"></td></tr>
<tr><td><font size=2>Nome:</td><td><font size=2>Partido:</td><td><font size=2>Cônjuge:</td></tr>

<tr><td><font size=2><b>NEDSON LUIZ MICHELETI                   </td><td><font size=2><b>PT   </td><td><font size=2><b>                                        </td></tr>
<tr><td colspan=3><br></td></tr>
<tr><td><font size=2>Telefone:</td><td><font size=2>Fax:</td><td><font size=2>Correio-eletrônico:</td></tr>
<tr><td><font size=2><b>3372-4000</td><td><font size=2><b>3342-1197</td><td><font size=2><b>gabprefeito@londrina.pr.gov.br                              </td></tr>
<tr><td colspan=3><br></td></tr>
<tr><td><font size=2>Número de Eleitores:</td><td><font size=2>Número de Votos:</td><td><font size=2>% da votação:</td></tr>

<tr><td><font size=2><b>328340</td><td><font size=2><b>137928</td><td><font size=2><b>53.25</td></tr>
<tr><td colspan=3><br></td></tr>
<tr><td colspan=3><font size=2><b>Vice-Prefeito</td></tr>
<tr><td colspan=3 height=2 class="bgPreto"></td></tr>
<tr><td><font size=2>Nome:</td><td colspan=2><font size=2>Partido:</td></tr>
<tr><td><font size=2><b>LUIS FERNANDO PINTO DIAS                </td><td colspan=2><font size=2><b>PT   </td></tr>
<tr><td colspan=3><br></td></tr>
<tr><td colspan=3><font size=2><b>Presidente da Câmara de Vereadores</td></tr>

<tr><td colspan=3 height=2 class="bgPreto"></td></tr>
<tr><td><font size=2>Nome:</td><td><font size=2>Partido:</td><td><font size=2>Cônjuge:</td></tr>
<tr><td><font size=2><b>ORLANDO BONILHA SOARES PROENÇA          </td><td><font size=2><b>PL   </td><td><font size=2><b>LUCIANA MENEZES PROENÇA                 </td></tr>
<tr><td colspan=3><br></td></tr>
<tr><td><font size=2>Telefone:</td><td><font size=2>Fax:</td><td><font size=2>Correio-eletrônico:</td></tr>
<tr><td><font size=2><b>3374-1300</td><td><font size=2><b>3374-1323</td><td><font size=2><b>camaraml@cml.pr.gov.br                                      </td></tr>

</table>
<br>
</body>
<%@ include file="../include/ocultarAguarde.jsp"%>
<%@ include file="../include/estadoMenu.jsp"%>
<%@ include file="../include/mensagemAlert.jsp" %>
</html>