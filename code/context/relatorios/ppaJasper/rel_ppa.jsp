<%@ taglib uri="/WEB-INF/ecar-util.tld" prefix="util" %>
<%@ taglib uri="/WEB-INF/ecar-combo.tld" prefix="combo" %>

<%@ page import="comum.util.Util" %>

<%@ include file="../../login/validaAcesso.jsp"%>
<%@ include file="../../frm_global.jsp"%>
<%@ include file="../../include/meta.jsp"%>
<%@ include file="../../titulo.jsp"%>
<%@ include file="../../include/exibirAguarde.jsp"%>
<script language="javascript" src="../../js/numero.js"></script>
<html lang="pt-br">
	<head>
		<link rel="stylesheet" href="<%=_pathEcar%>/css/style_<%=configuracaoCfg.getEstilo().getNome()%>.css" type="text/css">
		
		<script language="javascript" src="../../js/menu_retratil.js"></script>
		<script language="javascript" src="../../js/focoInicial.js"></script>
		<script language="javascript" src="../../js/validacoes.js"></script>
		<script language="javascript" src="../../js/modalbox.js"></script>	
		<script language="javascript" src="../../js/prototype.js"></script>	
		<script language="javascript" src="../../js/datas.js"></script>			
		
<script>

function escolhaRelatorio(){

var form = $('relatorioPPA');

	for (var i=0;i<form.length;i++)
  {
	if(form.elements[i].name=='relatorio' && form.elements[i].type=='radio' && form.elements[i].checked ){

		flag=true;
		form.target = "_blank";
			
		if( form.elements[i].value=='programa' ){
			form.titulo.value = 'ESTADO DO PARANÁ - PLANO PLURIANUAL 2008/2011 - Demonstrativo por Programa';
			var now = new Date(); 			
			form.data.value =  (strZero(now.getDate(),2)) + '/' + (strZero((now.getMonth()+1),2)) + '/' + now.getFullYear() + ' ' + (strZero(now.getHours(),2)) + ':' + (strZero(now.getMinutes(),2)) ;
			form.pagina.value = '1';			
		}
	
		if( form.elements[i].value=='linha' ){
			form.titulo.value = 'ESTADO DO PARANÁ - PLANO PLURIANUAL 2008/2011 - Demonstrativo por Linha de Ação/Programa';
			var now = new Date(); 			
			form.data.value =  (strZero(now.getDate(),2)) + '/' + (strZero((now.getMonth()+1),2)) + '/' + now.getFullYear() + ' ' + (strZero(now.getHours(),2)) + ':' + (strZero(now.getMinutes(),2)) ;
			form.pagina.value = '1';			
		}
		
		if( form.elements[i].value=='linhaPrograma' ){
			form.titulo.value = 'ESTADO DO PARANÁ - PLANO PLURIANUAL 2008/2011 - Demonstrativo por Linha de Ação/Programa/Órgão';
			var now = new Date(); 			
			form.data.value =  (strZero(now.getDate(),2)) + '/' + (strZero((now.getMonth()+1),2)) + '/' + now.getFullYear() + ' ' + (strZero(now.getHours(),2)) + ':' + (strZero(now.getMinutes(),2)) ;
			form.pagina.value = '1';
		}
		
		if( form.elements[i].value=='linhaProgGerente' ){
			form.titulo.value = 'ESTADO DO PARANÁ - PLANO PLURIANUAL 2008/2011 - Demonstrativo por Linha de Ação/Programa/Órgão/Gerente';
			var now = new Date(); 			
			form.data.value =  (strZero(now.getDate(),2)) + '/' + (strZero((now.getMonth()+1),2)) + '/' + now.getFullYear() + ' ' + (strZero(now.getHours(),2)) + ':' + (strZero(now.getMinutes(),2)) ;
			form.pagina.value = '1';
		}
		
		if( form.elements[i].value=='orgao' ){
			form.titulo.value = 'ESTADO DO PARANÁ - PLANO PLURIANUAL 2008/2011 - Demonstrativo por Órgão';
			var now = new Date(); 			
			form.data.value =  (strZero(now.getDate(),2)) + '/' + (strZero((now.getMonth()+1),2)) + '/' + now.getFullYear() + ' ' + (strZero(now.getHours(),2)) + ':' + (strZero(now.getMinutes(),2)) ;
			form.pagina.value = '1';
		}
		
		if( form.elements[i].value=='orgaoInd' ){
			form.titulo.value = 'ESTADO DO PARANÁ - PLANO PLURIANUAL 2008/2011 - Demonstrativo por Órgão e Administração Indireta';
			var now = new Date();
			form.data.value =  (strZero(now.getDate(),2)) + '/' + (strZero((now.getMonth()+1),2)) + '/' + now.getFullYear() + ' ' + (strZero(now.getHours(),2)) + ':' + (strZero(now.getMinutes(),2)) ;
			form.pagina.value = '1';
		}		
		
		if( form.elements[i].value=='funcao' ){
			form.titulo.value = 'ESTADO DO PARANÁ - PLANO PLURIANUAL 2008/2011 - Demonstrativo por Função / Subfunção';
			var now = new Date();
			form.data.value =  (strZero(now.getDate(),2)) + '/' + (strZero((now.getMonth()+1),2)) + '/' + now.getFullYear() + ' ' + (strZero(now.getHours(),2)) + ':' + (strZero(now.getMinutes(),2)) ;
			form.pagina.value = '1';
		}									
	
	}
  }
  
	if ( flag==false ){
		alert('Por favor, selecione um relatório para continuar!');
	}  
  
}

function aoClicarBtn3(form){

	var flag = false;

	for (var i=0;i<form.length;i++)
  {
	if(form.elements[i].name=='relatorio' && form.elements[i].type=='radio' && form.elements[i].checked ){

		flag=true;
		form.target = "_blank";
			
		if( form.elements[i].value=='programa' ){
			form.action = "../../Relatorio/PPAPrograma";
			form.submit();
			// ESTADO DO PARANÁ - PLANO PLURIANUAL 2008/2011 - Por Programa
		}
	
		if( form.elements[i].value=='linha' ){
			form.action = "../../Relatorio/PPALinhaPrograma";
			form.submit();
		}
		
		if( form.elements[i].value=='linhaPrograma' ){
			form.action = "../../Relatorio/PPALinhaAcao?possuiGerente=false";
			form.submit();
		}
		
		if( form.elements[i].value=='linhaProgGerente' ){
			form.action = "../../Relatorio/PPALinhaAcao?possuiGerente=true";
			form.submit();
		}
		
		if( form.elements[i].value=='orgao' ){
			form.action = "../../Relatorio/PPAOrgao";
			form.submit();
		}
		
		if( form.elements[i].value=='orgaoInd' ){
			form.action = "../../Relatorio/PPAOrgaoUnidade";
			form.submit();
		}	
		
		if( form.elements[i].value=='funcao' ){
			form.action = "../../Relatorio/PPAFuncao";
			form.submit();
		}										
	
	}
  }
  
	if ( flag==false ){
		alert('Por favor, selecione um relatório para continuar!');
	}  
  
}
</script>
	</head>

	<body>
		<%@ include file="../../cabecalho.jsp" %>
		<%@ include file="../../divmenu.jsp"%>		

<div id="conteudo">	

		<%@ include file="../../titulo_tela.jsp"%>
			<br/>
			<br/>
			<form id="relatorioPPA" name="relatorioPPA" method="post"  >
			<util:barrabotoes btn3="Gerar Relatório"/>

			<table width="100%" border="0" align="center" class="bgCinzaClaro2">
				<tbody>
					<tr>
						<td colspan="3" >
							&nbsp;
						</td>					
					</tr>
					<tr>
						<td width="30%" align="right" height="22" >
							<input type="radio" class="form_check_radio" name="relatorio" value="programa" onchange="escolhaRelatorio();" />
						</td>					
						<td width="40%" align="left"><label>Demonstrativo por Programa</label></td>						
						<td width="30%" align="center" height="22" >
							&nbsp;
						</td>						
					</tr>
					<tr>
						<td width="30%" align="right" height="22" >
							<input type="radio" class="form_check_radio" name="relatorio" value="linha" onchange="escolhaRelatorio();" />
						</td>					
						<td width="40%" align="left" ><label>Demonstrativo por Linha de Ação e Programa </label></td>						
						<td width="30%" align="center" height="22" >
							&nbsp;
						</td>						
					</tr>
					<tr>
						<td width="30%" align="right" height="22" >
							<input type="radio" class="form_check_radio" name="relatorio" value="linhaPrograma" onchange="escolhaRelatorio();" />
						</td>					
						<td width="40%"  align="left"><label>Demonstrativo por Linha de Ação / Programa / Órgão</label></td>						
						<td width="30%" align="center" height="22" >
							&nbsp;
						</td>						
					</tr>					
					<tr>
						<td width="30%" align="right" height="22" >
							<input type="radio" class="form_check_radio" name="relatorio" value="linhaProgGerente" onchange="escolhaRelatorio();" />
						</td>					
						<td width="40%" align="left"><label>Demonstrativo por Linha de Ação / Programa / Órgão / Gerente</label></td>						
						<td width="30%" align="center" height="22" >
							&nbsp;
						</td>						
					</tr>					
					<tr>
						<td width="30%" align="right" height="22" >
							<input type="radio" class="form_check_radio" name="relatorio" value="orgao" onchange="escolhaRelatorio();" />
						</td>
						<td width="40%" align="left" ><label>Demonstrativo por Órgão</label></td>						
						<td width="30%" align="center" height="22" >
							&nbsp;
						</td>						
					</tr>										
					<tr>
						<td width="30%" align="right" height="22" >
							<input type="radio" class="form_check_radio" name="relatorio" value="orgaoInd" onchange="escolhaRelatorio();" />
						</td>
						<td width="40%" align="left"><label>Demonstrativo por Órgão e Administração Indireta</label></td>						
						<td width="30%" align="center" height="22" >
							&nbsp;
						</td>						
					</tr>
					<tr>
						<td width="30%" align="right" height="22" >
							<input type="radio" class="form_check_radio" name="relatorio" value="funcao" onchange="escolhaRelatorio();" />
						</td>
						<td width="40%" align="left"><label>Demonstrativo por Função / Subfunção</label></td>						
						<td width="30%" align="center" height="22" >
							&nbsp;
						</td>						
					</tr>						
					<tr>
						<td colspan="3" >
							&nbsp;
						</td>					
					</tr>													
					<tr>
						<td align="center" colspan="3">
							<table border="0" cellpadding="0" cellspacing="0" align="center" >
								<tbody>
									<tr>
										<td>Título:&nbsp;</td>
										<td> <input type="text" id="titulo" name="titulo" size="60" maxlength="150"  >&nbsp;<%=Util.getTagDica("dicaTitulo",request.getContextPath(),"Título do relatório que pode ser personalizado alterando-se o texto padrão.") %></td>
									</tr>															
									<tr>
										<td>Data/Hora:&nbsp;</td>
										<td> <input type="text" id="data" name="data"  >&nbsp;<%=Util.getTagDica("dicaData",request.getContextPath(),"Data e hora de emissão do relatório, podendo-se alterar a data padrão (data e hora atual) para uma nova data e hora informadas.") %></td>
									</tr>
									<tr>
										<td>Página:&nbsp;</td>
										<td> <input type="text" id="pagina" name="pagina" onkeypress="javascript:return(digitaNumero(this, event));">&nbsp;<%=Util.getTagDica("dicaPagina",request.getContextPath(),"Número da página inicial do relatório, podendo-se alterar o valor padrão(1) para um novo valor inicial.") %></td>
									</tr>																					
								</tbody>
							</table>
						</td>					
					</tr>					
					<tr>
						<td colspan="3" >
							&nbsp;
						</td>					
					</tr>
				</tbody>

			</table>
			<util:barrabotoes btn3="Gerar Relatório"/>			
			</form>
			
</div>			
	</body>
	<%@ include file="../../include/ocultarAguarde.jsp"%>
	<%@ include file="../../include/estadoMenu.jsp" %>
	<%@ include file="../../include/mensagemAlert.jsp" %>
		
</HTML>