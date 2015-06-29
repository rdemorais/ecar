<%@ page import="comum.util.Pagina" %>

<html lang="pt-br">
<head>
<%
	//String Opcao = request.getParameter("opcao");
%>
<%@ include file="../include/meta.jsp"%>
<title>e-CAR</title>
<link rel="stylesheet" href="<%=_pathEcar%>/css/style_<%=new ecar.dao.ConfiguracaoDao(request).getConfiguracao().getEstilo().getNome()%>.css" type="text/css">

<script language="javascript" src="<%=_pathEcar%>/js/destaqueLinha.js"></script>
<script>
function aoClicarConsultar(form, codigo, codIett){ 
	form.codAri.value = codigo;
	document.form.action = "./geoAvaliacoesLondrina.jsp?primeiroIettClicado=" + codIett + "&primeiroAriClicado=" + codigo;
	document.form.submit();
}

</script>

</head>

<body> 

<div align="center"><center>

<div align="center"><center>

<body class="bgBranco" text="#000000" marginwidth=0 / marginheight=0 / leftmargin=0 / topmargin=0 onLoad="MM_preloadImages('../images/b_login_2.gif','../images/b_voltar_2.gif')" link="#000000" vlink="#000000" alink="#000000">
<form name = "frmRelatorioMun">

	<table border="0" cellpadding="0" cellspacing="0" width="100%">
		<a name="ancoraRealizado" id="ancoraRealizado">
		<tr>
			<td align="center"><font face="Arial" size="4"><b>RELATÓRIO DE EMPREENDIMENTOS NO MUNICÍPIO</b></font></td>
		</tr>
		</a>
	</table>
	<br>
	<table border="1" cellpadding="0" cellspacing="0" width="40%" bordercolor="black">

		<tr>
			<td align="center"><font face="Arial" size="4">LONDRINA</td>
		</tr>
	</table>
	<br>
	<br>
	<table border="0" cellPadding="0" cellSpacing="1" width="95%">
				<tr>
					<td class="bgBranco">
						<table width="100%" border="1" cellpadding="0" cellspacing="0" bordercolor="gray">
							<tr>

								<td align="left" bordercolor="white" >
									<font face="Arial" size="2" ><b>EXPANSÃO PRODUTIVA</b></font>
								</td>
								<td align="right" bordercolor="white">
									
								</td>
							</tr>
						</table>

					</td>
				</tr>
	</table>
	<table border="0" cellPadding="0" cellSpacing="1" width="95%">
  <tr>
	<td class="bgBranco">

		
		<table border="1" cellPadding="0" cellSpacing="0" width="100%" bordercolor="black">
			<tr>
				<td width="1000" align="left"  bordercolor="white"><b><font size="2" face="Tahoma">Desenvolvimento da Produção                       </b></font></td>
				<td width="7%" align="right" valign="bottom"  bordercolor="white"><font face="Tahoma" size="1"><div id="Asifrao_setor"></div></font></td>
				<td width="6%" align="right" valign="bottom"  bordercolor="white"><font face="Tahoma" size="2"><b><div id="Anome_setor"></div></b></font></td>
				<td align="right" width=15% valign="bottom"   bordercolor="white"><font face="Tahoma" size="2"><b><div ID="Aset_11131"></div></b></font></td>
				<td width="7%" align="right" valign="bottom"  bordercolor="white"><font face="Tahoma" size="1"><div id="sifrao_setor"></div></font></td>
				<td width="6%" align="right" valign="bottom"  bordercolor="white"><font face="Tahoma" size="2"><b><div id="nome_setor"></div></b></font></td>

				<td align="right" width="15%" valign="bottom"  bordercolor="white"></td>
			</tr>
		</table>
  </td></tr>
 </table>
 <br>

 <table border="0" cellPadding="0" cellSpacing="0" width="95%">
 <tr class="cor_nao" onmouseover="javascript: destacaLinha(this,'over','')" onmouseout="javascript: destacaLinha(this,'out','cor_nao')" onClick="javascript: destacaLinha(this,'click','cor_nao')" >

  <td width="1%" ><font face="Tahoma" size="2"></font></td>           
  <td valign="top" align="left">
  <font face="Tahoma" size="2">
  	
  		Financiamento à Industria                                                                                                                             
  	
  </font></td>
  <td width="5%" colspan="2">
	<font face="Tahoma" size="2" color = "blue">R$</font>

  </td>
  <td valign="top" width="1%" align="left"></td>
		     <td valign="top" width="8%" align="right"><font face="Tahoma" size="1"><b>21.923.652</b></font></td>
  <td align = "right" width="10%">
  <font face="Tahoma" size="2" color = "blue">
  <span id = "realizado1"></span>
  </font>
  </td>
 </tr> 
 
 </table>
 
 <br>
	<table border="0" cellPadding="0" cellSpacing="1" width="95%">
  <tr>
	<td class="bgBranco">

		
		<table border="1" cellPadding="0" cellSpacing="0" width="100%" bordercolor="black">
			<tr>
				<td width="1000" align="left"  bordercolor="white"><b><font size="2" face="Tahoma">Turismo</b></font></td>
				<td width="7%" align="right" valign="bottom"  bordercolor="white"><font face="Tahoma" size="1"><div id="Asifrao_setor"></div></font></td>
				<td width="6%" align="right" valign="bottom"  bordercolor="white"><font face="Tahoma" size="2"><b><div id="Anome_setor"></div></b></font></td>
				<td align="right" width=15% valign="bottom"   bordercolor="white"><font face="Tahoma" size="2"><b><div ID="Aset_11131"></div></b></font></td>
				<td width="7%" align="right" valign="bottom"  bordercolor="white"><font face="Tahoma" size="1"><div id="sifrao_setor"></div></font></td>
				<td width="6%" align="right" valign="bottom"  bordercolor="white"><font face="Tahoma" size="2"><b><div id="nome_setor"></div></b></font></td>

				<td align="right" width="15%" valign="bottom"  bordercolor="white"></td>
			</tr>
		</table>
  </td></tr>
 </table>
 <br>

 <table border="0" cellPadding="0" cellSpacing="0" width="95%">
 <tr class="cor_nao" onmouseover="javascript: destacaLinha(this,'over','')" onmouseout="javascript: destacaLinha(this,'out','cor_nao')" onClick="javascript: destacaLinha(this,'click','cor_nao')" >

  <td width="1%" ><font face="Tahoma" size="2"></font></td>           
  <td valign="top" align="left">
  <font face="Tahoma" size="2">
  	
  		 	   Divulgação do Produto Turístico Paranaense                                                                                                                             
  	
  </font></td>
  <td width="5%" colspan="2">
	<font face="Tahoma" size="2" color = "blue">R$</font>

  </td>
  <td valign="top" width="1%" align="left"></td>
		     <td valign="top" width="8%" align="right"><font face="Tahoma" size="1"><b>9.300</b></font></td>
  <td align = "right" width="10%">
  <font face="Tahoma" size="2" color = "blue">
  <span id = "realizado1"></span>
  </font>
  </td>
 </tr> 
 
 </table>
<br>
<table border="0" cellPadding="0" cellSpacing="0" width="95%">
 <tr class="cor_nao" onmouseover="javascript: destacaLinha(this,'over','')" onmouseout="javascript: destacaLinha(this,'out','cor_nao')" onClick="javascript: destacaLinha(this,'click','cor_nao')" >

  <td width="1%" ><font face="Tahoma" size="2"></font></td>           
  <td valign="top" align="left">
  <font face="Tahoma" size="2">
  	
  		 	    	  Incentivar o Fluxo Turístico Através da Captação e Apoio a Eventos Regionais                                                                                                                            
  	
  </font></td>
  <td width="5%" colspan="2">
	<font face="Tahoma" size="2" color = "blue">R$</font>

  </td>
  <td valign="top" width="1%" align="left"></td>
		     <td valign="top" width="8%" align="right"><font face="Tahoma" size="1"><b>9.700</b></font></td>
  <td align = "right" width="10%">
  <font face="Tahoma" size="2" color = "blue">
  <span id = "realizado1"></span>
  </font>
  </td>
 </tr> 
 
 </table>
 <br>
 <table border="0" cellPadding="0" cellSpacing="1" width="95%">
  <tr>
	<td class="bgBranco">

		
		<table border="1" cellPadding="0" cellSpacing="0" width="100%" bordercolor="black">
			<tr>
				<td width="1000" align="left"  bordercolor="white"><b><font size="2" face="Tahoma">Agricultura</b></font></td>
				<td width="7%" align="right" valign="bottom"  bordercolor="white"><font face="Tahoma" size="1"><div id="Asifrao_setor"></div></font></td>
				<td width="6%" align="right" valign="bottom"  bordercolor="white"><font face="Tahoma" size="2"><b><div id="Anome_setor"></div></b></font></td>
				<td align="right" width=15% valign="bottom"   bordercolor="white"><font face="Tahoma" size="2"><b><div ID="Aset_11131"></div></b></font></td>
				<td width="7%" align="right" valign="bottom"  bordercolor="white"><font face="Tahoma" size="1"><div id="sifrao_setor"></div></font></td>
				<td width="6%" align="right" valign="bottom"  bordercolor="white"><font face="Tahoma" size="2"><b><div id="nome_setor"></div></b></font></td>

				<td align="right" width="15%" valign="bottom"  bordercolor="white"></td>
			</tr>
		</table>
  </td></tr>
 </table>
 <br>

 <table border="0" cellPadding="0" cellSpacing="0" width="95%">
 <tr class="cor_nao" onmouseover="javascript: destacaLinha(this,'over','')" onmouseout="javascript: destacaLinha(this,'out','cor_nao')" onClick="javascript: destacaLinha(this,'click','cor_nao')" >

  <td width="1%" ><font face="Tahoma" size="2"></font></td>           
  <td valign="top" align="left">
  <font face="Tahoma" size="2">
  	
  		 	    	   Assistência Técnica e Extensão Rural                                                                                                                             
  	
  </font></td>
  <td width="5%" colspan="2">
	<font face="Tahoma" size="2" color = "blue">R$</font>

  </td>
  <td valign="top" width="1%" align="left"></td>
		     <td valign="top" width="8%" align="right"><font face="Tahoma" size="1"><b>4.731.720</b></font></td>
  <td align = "right" width="10%">
  <font face="Tahoma" size="2" color = "blue">
  <span id = "realizado1"></span>
  </font>
  </td>
 </tr> 
 
 </table>
<br>
<table border="0" cellPadding="0" cellSpacing="0" width="95%">
 <tr class="cor_nao" onmouseover="javascript: destacaLinha(this,'over','')" onmouseout="javascript: destacaLinha(this,'out','cor_nao')" onClick="javascript: destacaLinha(this,'click','cor_nao')" >

  <td width="1%" ><font face="Tahoma" size="2"></font></td>           
  <td valign="top" align="left">
  <font face="Tahoma" size="2">
  	
  		 	    	   	  Capacitação                                                                                                                            
  	
  </font></td>
  <td width="5%" colspan="2">
	<font face="Tahoma" size="2" color = "blue">R$</font>

  </td>
  <td valign="top" width="1%" align="left"></td>
		     <td valign="top" width="8%" align="right"><font face="Tahoma" size="1"><b>199.941</b></font></td>
  <td align = "right" width="10%">
  <font face="Tahoma" size="2" color = "blue">
  <span id = "realizado1"></span>
  </font>
  </td>
 </tr> 
 
 </table>
 <br>
<table border="0" cellPadding="0" cellSpacing="0" width="95%">
 <tr class="cor_nao" onmouseover="javascript: destacaLinha(this,'over','')" onmouseout="javascript: destacaLinha(this,'out','cor_nao')" onClick="javascript: destacaLinha(this,'click','cor_nao')" >

  <td width="1%" ><font face="Tahoma" size="2"></font></td>           
  <td valign="top" align="left">
  <font face="Tahoma" size="2">
  	
  		 	    	   	   	  Classificação de Produtos de Origem Vegetal                                                                                                                            
  	
  </font></td>
  <td width="5%" colspan="2">
	<font face="Tahoma" size="2" color = "blue">R$</font>

  </td>
  <td valign="top" width="1%" align="left"></td>
		     <td valign="top" width="8%" align="right"><font face="Tahoma" size="1"><b>1.643.639</b></font></td>
  <td align = "right" width="10%">
  <font face="Tahoma" size="2" color = "blue">
  <span id = "realizado1"></span>
  </font>
  </td>
 </tr> 
 
 </table>
 <br>
<table border="0" cellPadding="0" cellSpacing="0" width="95%">
 <tr class="cor_nao" onmouseover="javascript: destacaLinha(this,'over','')" onmouseout="javascript: destacaLinha(this,'out','cor_nao')" onClick="javascript: destacaLinha(this,'click','cor_nao')" >

  <td width="1%" ><font face="Tahoma" size="2"></font></td>           
  <td valign="top" align="left">
  <font face="Tahoma" size="2">
  	
  		 	    	   	   	   	  Desenvolvimento Rural e da Agricultura Familiar                                                                                                                            
  	
  </font></td>
  <td width="5%" colspan="2">
	<font face="Tahoma" size="2" color = "blue">R$</font>

  </td>
  <td valign="top" width="1%" align="left"></td>
		     <td valign="top" width="8%" align="right"><font face="Tahoma" size="1"><b>87.304</b></font></td>
  <td align = "right" width="10%">
  <font face="Tahoma" size="2" color = "blue">
  <span id = "realizado1"></span>
  </font>
  </td>
 </tr> 
 
 </table>
 <br>
<table border="0" cellPadding="0" cellSpacing="0" width="95%">
 <tr class="cor_nao" onmouseover="javascript: destacaLinha(this,'over','')" onmouseout="javascript: destacaLinha(this,'out','cor_nao')" onClick="javascript: destacaLinha(this,'click','cor_nao')" >

  <td width="1%" ><font face="Tahoma" size="2"></font></td>           
  <td valign="top" align="left">
  <font face="Tahoma" size="2">
  	
  		 	    	   	   	   	   	  Empreendimentos de Alívio à Pobreza Rural                                                                                                                            
  	
  </font></td>
  <td width="5%" colspan="2">
	<font face="Tahoma" size="2" color = "blue">R$</font>

  </td>
  <td valign="top" width="1%" align="left"></td>
		     <td valign="top" width="8%" align="right"><font face="Tahoma" size="1"><b>704.907</b></font></td>
  <td align = "right" width="10%">
  <font face="Tahoma" size="2" color = "blue">
  <span id = "realizado1"></span>
  </font>
  </td>
 </tr> 
 
 </table>
 <br>
<table border="0" cellPadding="0" cellSpacing="0" width="95%">
 <tr class="cor_nao" onmouseover="javascript: destacaLinha(this,'over','')" onmouseout="javascript: destacaLinha(this,'out','cor_nao')" onClick="javascript: destacaLinha(this,'click','cor_nao')" >

  <td width="1%" ><font face="Tahoma" size="2"></font></td>           
  <td valign="top" align="left">
  <font face="Tahoma" size="2">
  	
  		 	    	   	   	   	   	   	  Piscicultura                                                                                                                            
  	
  </font></td>
  <td width="5%" colspan="2">
	<font face="Tahoma" size="2" color = "blue">R$</font>

  </td>
  <td valign="top" width="1%" align="left"></td>
		     <td valign="top" width="8%" align="right"><font face="Tahoma" size="1"><b>121.800</b></font></td>
  <td align = "right" width="10%">
  <font face="Tahoma" size="2" color = "blue">
  <span id = "realizado1"></span>
  </font>
  </td>
 </tr> 
 
 </table>
 <br>
<table border="0" cellPadding="0" cellSpacing="0" width="95%">
 <tr class="cor_nao" onmouseover="javascript: destacaLinha(this,'over','')" onmouseout="javascript: destacaLinha(this,'out','cor_nao')" onClick="javascript: destacaLinha(this,'click','cor_nao')" >

  <td width="1%" ><font face="Tahoma" size="2"></font></td>           
  <td valign="top" align="left">
  <font face="Tahoma" size="2">
  	
  		 	    	   	   	   	   	   	   	  Promoção da Política de Abastecimento Alimentar                                                                                                                            
  	
  </font></td>
  <td width="5%" colspan="2">
	<font face="Tahoma" size="2" color = "blue">R$</font>

  </td>
  <td valign="top" width="1%" align="left"></td>
		     <td valign="top" width="8%" align="right"><font face="Tahoma" size="1"><b>2.271.094</b></font></td>
  <td align = "right" width="10%">
  <font face="Tahoma" size="2" color = "blue">
  <span id = "realizado1"></span>
  </font>
  </td>
 </tr> 
 
 </table>
 <br>
 <table border="0" cellPadding="0" cellSpacing="1" width="95%">
				<tr>
					<td class="bgBranco">
						<table width="100%" border="1" cellpadding="0" cellspacing="0" bordercolor="gray">
							<tr>

								<td align="left" bordercolor="white" >
									<font face="Arial" size="2" ><b>INFRA-ESTRUTURA E MEIO AMBIENTE</b></font>
								</td>
								<td align="right" bordercolor="white">
									
								</td>
							</tr>
						</table>

					</td>
				</tr>
	</table>
	<table border="0" cellPadding="0" cellSpacing="1" width="95%">
  <tr>
	<td class="bgBranco">

		
		<table border="1" cellPadding="0" cellSpacing="0" width="100%" bordercolor="black">
			<tr>
				<td width="1000" align="left"  bordercolor="white"><b><font size="2" face="Tahoma">Desenvolvimento Urbano                       </b></font></td>
				<td width="7%" align="right" valign="bottom"  bordercolor="white"><font face="Tahoma" size="1"><div id="Asifrao_setor"></div></font></td>
				<td width="6%" align="right" valign="bottom"  bordercolor="white"><font face="Tahoma" size="2"><b><div id="Anome_setor"></div></b></font></td>
				<td align="right" width=15% valign="bottom"   bordercolor="white"><font face="Tahoma" size="2"><b><div ID="Aset_11131"></div></b></font></td>
				<td width="7%" align="right" valign="bottom"  bordercolor="white"><font face="Tahoma" size="1"><div id="sifrao_setor"></div></font></td>
				<td width="6%" align="right" valign="bottom"  bordercolor="white"><font face="Tahoma" size="2"><b><div id="nome_setor"></div></b></font></td>

				<td align="right" width="15%" valign="bottom"  bordercolor="white"></td>
			</tr>
		</table>
  </td></tr>
 </table>
 <br>

 <table border="0" cellPadding="0" cellSpacing="0" width="95%">
 <tr class="cor_nao" onmouseover="javascript: destacaLinha(this,'over','')" onmouseout="javascript: destacaLinha(this,'out','cor_nao')" onClick="javascript: destacaLinha(this,'click','cor_nao')" >

  <td width="1%" ><font face="Tahoma" size="2"></font></td>           
  <td valign="top" align="left">
  <font face="Tahoma" size="2">
  	
  		 	   Cine-Teatro                                                                                                                             
  	
  </font></td>
  <td width="5%" colspan="2">
	<font face="Tahoma" size="2" color = "blue">R$</font>

  </td>
  <td valign="top" width="1%" align="left"></td>
		     <td valign="top" width="8%" align="right"><font face="Tahoma" size="1"><b>1.823.210</b></font></td>
  <td align = "right" width="10%">
  <font face="Tahoma" size="2" color = "blue">
  <span id = "realizado1"></span>
  </font>
  </td>
 </tr> 
 
 </table>
 <br>

 <table border="0" cellPadding="0" cellSpacing="0" width="95%">
 <tr class="cor_nao" onmouseover="javascript: destacaLinha(this,'over','')" onmouseout="javascript: destacaLinha(this,'out','cor_nao')" onClick="javascript: destacaLinha(this,'click','cor_nao')" >

  <td width="1%" ><font face="Tahoma" size="2"></font></td>           
  <td valign="top" align="left">
  <font face="Tahoma" size="2">
  	
  		 	    	  Pavimentação de Vias Urbanas                                                                                                                             
  	
  </font></td>
  <td width="5%" colspan="2">
	<font face="Tahoma" size="2" color = "blue">R$</font>

  </td>
  <td valign="top" width="1%" align="left"></td>
		     <td valign="top" width="8%" align="right"><font face="Tahoma" size="1"><b>11.622.382</b></font></td>
  <td align = "right" width="10%">
  <font face="Tahoma" size="2" color = "blue">
  <span id = "realizado1"></span>
  </font>
  </td>
 </tr> 
 
 </table>
 <br>

 <table border="0" cellPadding="0" cellSpacing="0" width="95%">
 <tr class="cor_nao" onmouseover="javascript: destacaLinha(this,'over','')" onmouseout="javascript: destacaLinha(this,'out','cor_nao')" onClick="javascript: destacaLinha(this,'click','cor_nao')" >

  <td width="1%" ><font face="Tahoma" size="2"></font></td>           
  <td valign="top" align="left">
  <font face="Tahoma" size="2">
  	
  		 	    	   	  Recape Asfáltico                                                                                                                             
  	
  </font></td>
  <td width="5%" colspan="2">
	<font face="Tahoma" size="2" color = "blue">R$</font>

  </td>
  <td valign="top" width="1%" align="left"></td>
		     <td valign="top" width="8%" align="right"><font face="Tahoma" size="1"><b>4.045.411</b></font></td>
  <td align = "right" width="10%">
  <font face="Tahoma" size="2" color = "blue">
  <span id = "realizado1"></span>
  </font>
  </td>
 </tr> 
 
 </table>
 
 
 <br>
	<table border="0" cellPadding="0" cellSpacing="1" width="95%">
  <tr>
	<td class="bgBranco">

		
		<table border="1" cellPadding="0" cellSpacing="0" width="100%" bordercolor="black">
			<tr>
				<td width="1000" align="left"  bordercolor="white"><b><font size="2" face="Tahoma">Meio Ambiente</b></font></td>
				<td width="7%" align="right" valign="bottom"  bordercolor="white"><font face="Tahoma" size="1"><div id="Asifrao_setor"></div></font></td>
				<td width="6%" align="right" valign="bottom"  bordercolor="white"><font face="Tahoma" size="2"><b><div id="Anome_setor"></div></b></font></td>
				<td align="right" width=15% valign="bottom"   bordercolor="white"><font face="Tahoma" size="2"><b><div ID="Aset_11131"></div></b></font></td>
				<td width="7%" align="right" valign="bottom"  bordercolor="white"><font face="Tahoma" size="1"><div id="sifrao_setor"></div></font></td>
				<td width="6%" align="right" valign="bottom"  bordercolor="white"><font face="Tahoma" size="2"><b><div id="nome_setor"></div></b></font></td>

				<td align="right" width="15%" valign="bottom"  bordercolor="white"></td>
			</tr>
		</table>
  </td></tr>
 </table>
 <br>

 <table border="0" cellPadding="0" cellSpacing="0" width="95%">
 <tr class="cor_nao" onmouseover="javascript: destacaLinha(this,'over','')" onmouseout="javascript: destacaLinha(this,'out','cor_nao')" onClick="javascript: destacaLinha(this,'click','cor_nao')" >

  <td width="1%" ><font face="Tahoma" size="2"></font></td>           
  <td valign="top" align="left">
  <font face="Tahoma" size="2">
  	
  		 	    	   Obras de Drenagem e de Controle da Erosão                                                                                                                             
  	
  </font></td>
  <td width="5%" colspan="2">
	<font face="Tahoma" size="2" color = "blue">R$</font>

  </td>
  <td valign="top" width="1%" align="left"></td>
		     <td valign="top" width="8%" align="right"><font face="Tahoma" size="1"><b>339.396</b></font></td>
  <td align = "right" width="10%">
  <font face="Tahoma" size="2" color = "blue">
  <span id = "realizado1"></span>
  </font>
  </td>
 </tr> 
 
 </table>
<br>
<table border="0" cellPadding="0" cellSpacing="0" width="95%">
 <tr class="cor_nao" onmouseover="javascript: destacaLinha(this,'over','')" onmouseout="javascript: destacaLinha(this,'out','cor_nao')" onClick="javascript: destacaLinha(this,'click','cor_nao')" >

  <td width="1%" ><font face="Tahoma" size="2"></font></td>           
  <td valign="top" align="left">
  <font face="Tahoma" size="2">
  	
  		 	    	   	  Recuperação Florestal - Programa Mata Ciliar                                                                                                                            
  	
  </font></td>
  <td width="5%" colspan="2">
	<font face="Tahoma" size="2" color = "blue">R$</font>

  </td>
  <td valign="top" width="1%" align="left"></td>
		     <td valign="top" width="8%" align="right"><font face="Tahoma" size="1"><b>35.250</b></font></td>
  <td align = "right" width="10%">
  <font face="Tahoma" size="2" color = "blue">
  <span id = "realizado1"></span>
  </font>
  </td>
 </tr> 
 
 </table>
 <br>
 <table border="0" cellPadding="0" cellSpacing="1" width="95%">
  <tr>
	<td class="bgBranco">

		
		<table border="1" cellPadding="0" cellSpacing="0" width="100%" bordercolor="black">
			<tr>
				<td width="1000" align="left"  bordercolor="white"><b><font size="2" face="Tahoma">Transporte</b></font></td>
				<td width="7%" align="right" valign="bottom"  bordercolor="white"><font face="Tahoma" size="1"><div id="Asifrao_setor"></div></font></td>
				<td width="6%" align="right" valign="bottom"  bordercolor="white"><font face="Tahoma" size="2"><b><div id="Anome_setor"></div></b></font></td>
				<td align="right" width=15% valign="bottom"   bordercolor="white"><font face="Tahoma" size="2"><b><div ID="Aset_11131"></div></b></font></td>
				<td width="7%" align="right" valign="bottom"  bordercolor="white"><font face="Tahoma" size="1"><div id="sifrao_setor"></div></font></td>
				<td width="6%" align="right" valign="bottom"  bordercolor="white"><font face="Tahoma" size="2"><b><div id="nome_setor"></div></b></font></td>

				<td align="right" width="15%" valign="bottom"  bordercolor="white"></td>
			</tr>
		</table>
  </td></tr>
 </table>
 <br>

 <table border="0" cellPadding="0" cellSpacing="0" width="95%">
 <tr class="cor_nao" onmouseover="javascript: destacaLinha(this,'over','')" onmouseout="javascript: destacaLinha(this,'out','cor_nao')" onClick="javascript: destacaLinha(this,'click','cor_nao')" >

  <td width="1%" ><font face="Tahoma" size="2"></font></td>           
  <td valign="top" align="left">
  <font face="Tahoma" size="2">
  	
  		 	    	    	   Adequação de Estradas                                                                                                                             
  	
  </font></td>
  <td width="5%" colspan="2">
	<font face="Tahoma" size="2" color = "blue">R$</font>

  </td>
  <td valign="top" width="1%" align="left"></td>
		     <td valign="top" width="8%" align="right"><font face="Tahoma" size="1"><b>114.460</b></font></td>
  <td align = "right" width="10%">
  <font face="Tahoma" size="2" color = "blue">
  <span id = "realizado1"></span>
  </font>
  </td>
 </tr> 
 
 </table>
<br>
<table border="0" cellPadding="0" cellSpacing="0" width="95%">
 <tr class="cor_nao" onmouseover="javascript: destacaLinha(this,'over','')" onmouseout="javascript: destacaLinha(this,'out','cor_nao')" onClick="javascript: destacaLinha(this,'click','cor_nao')" >

  <td width="1%" ><font face="Tahoma" size="2"></font></td>           
  <td valign="top" align="left">
  <font face="Tahoma" size="2">
  	
  		 	    	   	   	  Conservação Rodoviária                                                                                                                            
  	
  </font></td>
  <td width="5%" colspan="2">
	<font face="Tahoma" size="2" color = "blue">R$</font>

  </td>
  <td valign="top" width="1%" align="left"></td>
		     <td valign="top" width="8%" align="right"><font face="Tahoma" size="1"><b>426.749</b></font></td>
  <td align = "right" width="10%">
  <font face="Tahoma" size="2" color = "blue">
  <span id = "realizado1"></span>
  </font>
  </td>
 </tr> 
 
 </table>
 <br>
<table border="0" cellPadding="0" cellSpacing="0" width="95%">
 <tr class="cor_nao" onmouseover="javascript: destacaLinha(this,'over','')" onmouseout="javascript: destacaLinha(this,'out','cor_nao')" onClick="javascript: destacaLinha(this,'click','cor_nao')" >

  <td width="1%" ><font face="Tahoma" size="2"></font></td>           
  <td valign="top" align="left">
  <font face="Tahoma" size="2">
  	
  		 	    	   	   	     Construção de Obra de Arte Especial                                                                                                                            
  	
  </font></td>
  <td width="5%" colspan="2">
	<font face="Tahoma" size="2" color = "blue">R$</font>

  </td>
  <td valign="top" width="1%" align="left"></td>
		     <td valign="top" width="8%" align="right"><font face="Tahoma" size="1"><b>1.692.814</b></font></td>
  <td align = "right" width="10%">
  <font face="Tahoma" size="2" color = "blue">
  <span id = "realizado1"></span>
  </font>
  </td>
 </tr> 
 
 </table>
 <br>
<table border="0" cellPadding="0" cellSpacing="0" width="95%">
 <tr class="cor_nao" onmouseover="javascript: destacaLinha(this,'over','')" onmouseout="javascript: destacaLinha(this,'out','cor_nao')" onClick="javascript: destacaLinha(this,'click','cor_nao')" >

  <td width="1%" ><font face="Tahoma" size="2"></font></td>           
  <td valign="top" align="left">
  <font face="Tahoma" size="2">
  	
  		 	    	   	   	   	      	  Pavimentação Asfáltica                                                                                                                            
  	
  </font></td>
  <td width="5%" colspan="2">
	<font face="Tahoma" size="2" color = "blue">R$</font>

  </td>
  <td valign="top" width="1%" align="left"></td>
		     <td valign="top" width="8%" align="right"><font face="Tahoma" size="1"><b>10.232.041</b></font></td>
  <td align = "right" width="10%">
  <font face="Tahoma" size="2" color = "blue">
  <span id = "realizado1"></span>
  </font>
  </td>
 </tr> 
 
 </table>
 <br>
<table border="0" cellPadding="0" cellSpacing="0" width="95%">
 <tr class="cor_nao" onmouseover="javascript: destacaLinha(this,'over','')" onmouseout="javascript: destacaLinha(this,'out','cor_nao')" onClick="javascript: destacaLinha(this,'click','cor_nao')" >

  <td width="1%" ><font face="Tahoma" size="2"></font></td>           
  <td valign="top" align="left">
  <font face="Tahoma" size="2">
  	
  		 	    	   	   	   	   	   	  Recuperação de Estradas                                                                                                                            
  	
  </font></td>
  <td width="5%" colspan="2">
	<font face="Tahoma" size="2" color = "blue">R$</font>

  </td>
  <td valign="top" width="1%" align="left"></td>
		     <td valign="top" width="8%" align="right"><font face="Tahoma" size="1"><b>2.878.762</b></font></td>
  <td align = "right" width="10%">
  <font face="Tahoma" size="2" color = "blue">
  <span id = "realizado1"></span>
  </font>
  </td>
 </tr> 
 
 </table>
 <br>
<table border="0" cellPadding="0" cellSpacing="0" width="95%">
 <tr class="cor_nao" onmouseover="javascript: destacaLinha(this,'over','')" onmouseout="javascript: destacaLinha(this,'out','cor_nao')" onClick="javascript: destacaLinha(this,'click','cor_nao')" >

  <td width="1%" ><font face="Tahoma" size="2"></font></td>           
  <td valign="top" align="left">
  <font face="Tahoma" size="2">
  	
  		 	    	   	   	   	   	   	   	  Recuperação de Obra de Arte Especial                                                                                                                            
  	
  </font></td>
  <td width="5%" colspan="2">
	<font face="Tahoma" size="2" color = "blue">R$</font>

  </td>
  <td valign="top" width="1%" align="left"></td>
		     <td valign="top" width="8%" align="right"><font face="Tahoma" size="1"><b>131.776</b></font></td>
  <td align = "right" width="10%">
  <font face="Tahoma" size="2" color = "blue">
  <span id = "realizado1"></span>
  </font>
  </td>
 </tr> 
 
 </table>
 
	
<p style="page-break-after:always;"></p>

</body>

</html>