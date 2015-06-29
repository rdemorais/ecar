<html lang="pt-br">
<head> 
<%@ include file="../include/meta.jsp"%>
<%@ include file="/titulo.jsp"%>
<link rel="stylesheet" href="<%=_pathEcar%>/css/style_<%=new ecar.dao.ConfiguracaoDao(request).getConfiguracao().getEstilo().getNome()%>.css" type="text/css">
<script language="javascript" src="../js/menu_retratil.js"></script>
<script language="javascript" src="../js/popUp.js"></script>
<script language="javascript" src="<%=_pathEcar%>/js/destaqueLinha.js"></script>

</head>
<body class="bgBranco" TEXT="Black" LINK="blue">
<br>
<br>
<form name="nameform">	
	
	<table width="100%" border="0" cellpadding="0" cellspacing="0">
		<a name="ancoraEleicoes" id="ancoraEleicoes">
		<tr>
			<td width="24%" align="center"><h1>Eleições 2002 - Município de Londrina</h1></td>		
			<td width="76%"></td>	
		</tr>		
		</a>
	</table>
<br>
</form>
<table width=100% border="0" cellspacing="0">
<font size=1 face=verdana color="black"><b>
	
<tr><td colspan=3><font size=3><b>Governador 1º Turno</td></tr>
<tr><td colspan=3 height=2 class="bgPreto"></td></tr>
<tr><td><font size=2>Nome:</td><td><font size=2>Partido:</td><td><font size=2>Votos:</td></tr>

<tr class="cor_nao" onmouseover="javascript: destacaLinha(this,'over','')" onmouseout="javascript: destacaLinha(this,'out','cor_nao')" onClick="javascript: destacaLinha(this,'click','cor_nao')" ><td><font size=3><b>ALVARO DIAS</td><td><font size=2><b>PDT</td><td><font size=2><b>88569</td></tr>
<tr class="cor_sim" onmouseover="javascript:destacaLinha(this,'over','')" onmouseout="javascript: destacaLinha(this,'out','cor_sim')" onClick="javascript: destacaLinha(this,'click','cor_sim')" ><td><font size=3><b>BETO RICHA</td><td><font size=2><b>PSDB</td><td><font size=2><b>47681</td></tr>
<tr><td colspan=3><br></td></tr>
<tr><td><font size=3><b>Governador 2º Turno</td></tr>
<tr><td colspan=3 height=2 class="bgPreto"></td></tr>
<tr><td><font size=2>Nome:</td><td><font size=2>Partido:</td><td><font size=2>Votos:</td></tr>
<tr class="cor_nao" onmouseover="javascript: destacaLinha(this,'over','')" onmouseout="javascript: destacaLinha(this,'out','cor_nao')" onClick="javascript: destacaLinha(this,'click','cor_nao')" ><td><font size=3><b>ALVARO DIAS</td><td><font size=2><b>PDT</td><td><font size=2><b>139177</td></tr>

<tr class="cor_sim" onmouseover="javascript:destacaLinha(this,'over','')" onmouseout="javascript: destacaLinha(this,'out','cor_sim')" onClick="javascript: destacaLinha(this,'click','cor_sim')" ><td><font size=3><b>ROBERTO REQUIÃO</td><td><font size=2><b>PMDB</td><td><font size=2><b>92391</td></tr>
<tr><td colspan=3><br></td></tr>
<tr><td><font size=3><b>Senador</td></tr>
<tr><td colspan=3 height=2 class="bgPreto"></td></tr>
<tr><td><font size=2>Nome:</td><td><font size=2>Partido:</td><td><font size=2>Votos:</td></tr>
<tr class="cor_nao" onmouseover="javascript: destacaLinha(this,'over','')" onmouseout="javascript: destacaLinha(this,'out','cor_nao')" onClick="javascript: destacaLinha(this,'click','cor_nao')" ><td><font size=3><b>OSMAR DIAS</td><td><font size=2><b>PDT</td><td><font size=2><b>137846</td></tr>
<tr class="cor_sim" onmouseover="javascript:destacaLinha(this,'over','')" onmouseout="javascript: destacaLinha(this,'out','cor_sim')" onClick="javascript: destacaLinha(this,'click','cor_sim')" ><td><font size=3><b>NITIS JACON</td><td><font size=2><b>PSDB</td><td><font size=2><b>105076</td></tr>

<tr><td colspan=3><br></td></tr>
<tr><td><font size=3><b>Deputados Federais</td></tr>
<tr><td colspan=3 height=2 class="bgPreto"></td></tr>
<tr><td><font size=2>Nome:</td><td><font size=2>Partido:</td><td><font size=2>Votos:</td></tr>
<tr class="cor_nao" onmouseover="javascript: destacaLinha(this,'over','')" onmouseout="javascript: destacaLinha(this,'out','cor_nao')" onClick="javascript: destacaLinha(this,'click','cor_nao')" ><td><font size=3><b>HAULY</td><td><font size=2><b>PSDB </td><td><font size=2><b>44038</td></tr>
<tr class="cor_sim" onmouseover="javascript:destacaLinha(this,'over','')" onmouseout="javascript: destacaLinha(this,'out','cor_sim')" onClick="javascript: destacaLinha(this,'click','cor_sim')" ><td><font size=3><b>PAULO BERNARDO</td><td><font size=2><b>PT   </td><td><font size=2><b>24994</td></tr>
<tr class="cor_nao" onmouseover="javascript: destacaLinha(this,'over','')" onmouseout="javascript: destacaLinha(this,'out','cor_nao')" onClick="javascript: destacaLinha(this,'click','cor_nao')" ><td><font size=3><b>ALEX CANZIANI</td><td><font size=2><b>PTB  </td><td><font size=2><b>19354</td></tr>

<tr><td colspan=3><br></td></tr>
<tr><td><font size=3><b>Deputados Estaduais</td></tr>
<tr><td colspan=3 height=2 class="bgPreto"></td></tr>
<tr><td><font size=2>Nome:</td><td><font size=2>Partido:</td><td><font size=2>Votos:</td></tr>
<tr class="cor_nao" onmouseover="javascript: destacaLinha(this,'over','')" onmouseout="javascript: destacaLinha(this,'out','cor_nao')" onClick="javascript: destacaLinha(this,'click','cor_nao')"><td><font size=3><b>BARBOSA NETO</td><td><font size=2><b>PDT  </td><td><font size=2><b>63509.0</td></tr>
<tr class="cor_sim" onmouseover="javascript:destacaLinha(this,'over','')" onmouseout="javascript: destacaLinha(this,'out','cor_sim')" onClick="javascript: destacaLinha(this,'click','cor_sim')"><td><font size=3><b>ELZA CORREIA</td><td><font size=2><b>PMDB </td><td><font size=2><b>34994.0</td></tr>
<tr class="cor_nao" onmouseover="javascript: destacaLinha(this,'over','')" onmouseout="javascript: destacaLinha(this,'out','cor_nao')" onClick="javascript: destacaLinha(this,'click','cor_nao')"><td><font size=3><b>ANDRE VARGAS</td><td><font size=2><b>PT   </td><td><font size=2><b>10369.0</td></tr>

<tr><td colspan=3><br></td></tr>


</table>
<br>

</body>
<%@ include file="../include/ocultarAguarde.jsp"%>
<%@ include file="../include/estadoMenu.jsp"%>
<%@ include file="../include/mensagemAlert.jsp" %>
</html>