

<%@ page import="java.util.List"%>
<%@ page import="ecar.dao.ConfiguracaoDao"%>
<%@ page import="ecar.dao.SisGrupoAtributoDao"%>
<%@ page import="java.util.Iterator"%>
<%@ page import="ecar.pojo.SisAtributoSatb"%>
<%@ taglib uri="/WEB-INF/ecar-util.tld" prefix="util"%>
<%@ include file="../frm_global.jsp"%>

<%
			String hidTelaGerarPeriodo = Pagina.getParamStr(request,
			"telaGerarPeriodo");
	String hidOpcaoSemInformacao = Pagina.getParamStr(request,
			"opcaoSemInformacao");
%>

<html lang="pt-br">

	<head>
		<%@ include file="../include/meta.jsp"%>
		<%@ include file="/titulo.jsp"%>
		<link rel="stylesheet" href="<%=_pathEcar%>/css/style_<%=configuracaoCfg.getEstilo().getNome()%>.css" type="text/css">
		<script language="javascript" src="../js/focoInicial.js"></script>
		<script language="javascript" src="../js/frmPesquisa.js"></script>

		<script>
function selecionarAtuais(){
	var codigos = window.opener.document.forms[0].niveisPlanejamento.value.split(":");
	for(i = 0; i< document.form.nivel.length;i++){
		for(j = 0; j < codigos.length; j++){
			if(codigos[j] == document.form.nivel[i].value){
				document.form.nivel[i].checked = true;
			}
		}			
	}		
	var semNivel = window.opener.document.forms[0].semInformacaoNivelPlanejamento.value;
	if(semNivel == 'S'){
		document.form.semInformacaoNivel.checked = true;
	}
}

function continuar(){
	//	luana		
	//vetor dos que ja vieram selecionados                    
	var selecionado = window.opener.document.forms[0].niveisPlanejamento.value.split(":");	
	var troca = true; 
    while(troca){
    	troca = false;
        for( i = 0; i< selecionado.length; i++){        	
			if( selecionado[i] > selecionado[i + 1]){
				var aux = selecionado[i];
				selecionado[i] = selecionado[i+1];
				selecionado[i+1] = aux;
				troca = true;
			}
		}
	}	
		
	var diferentes = 0;		
	window.opener.document.forms[0].niveisPlanejamento.value = "";
	
	for(i = 0; i< document.form.nivel.length;i++){									
			if(document.form.nivel[i].checked){							
				window.opener.document.forms[0].niveisPlanejamento.value += document.form.nivel[i].value + ":";					
			}			
	}	

	//cria um array com os novos selecionados sem separador ":"
	var novos = window.opener.document.forms[0].niveisPlanejamento.value.split(":");		
		
	//ordenei antes de comparar		
	//vetor com as novas seleções
	troca = true;                     
    while(troca){
    	troca = false;
        for( i = 0; i< novos.length; i++){        	
			if( novos[i] > novos[i + 1]){
				var aux = novos[i];
				novos[i] = novos[i+1];
				novos[i+1] = aux;
				troca = true;
			}
		}
	}
	for(j = 0;j < selecionado.length || j < novos.length; j++){
		//compara os que ja vieram selecionados e os que foram selecionados	
		if(novos[j] != selecionado[j]){
			diferentes++;				
		}
	}
	
	if(document.form.semInformacaoNivel.checked) {
		window.opener.document.forms[0].semInformacaoNivelPlanejamento.value='S';
	} else {
		window.opener.document.forms[0].semInformacaoNivelPlanejamento.value='N';
	}
	
	if(diferentes == 0 && window.opener.document.forms[0].semInformacaoNivelPlanejamento.value == "<%= hidOpcaoSemInformacao%>"){
		
		window.close();
		
	}else{		

		<%
		if(!"S".equals(hidTelaGerarPeriodo)){
		%>
			window.opener.reload();
		<%
		}
		%>

		window.close();
	}
}
</script>

	</head>
	<!-- sempre colocar o foco inicial no primeiro campo -->
	<body class="corpo_popup" onload="javascript:selecionarAtuais()">
		<%
				try {
				//ConfiguracaoDao configuracaoDao = new ConfiguracaoDao(request);
		%>
		<H1>
			<%=configuracaoCfg.getSisGrupoAtributoSgaByCodSgaGrAtrNvPlan()
								.getDescricaoSga()%>
		</H1>

		<form name="form" method="post" action="">


			<table width="100%" border="0" cellpadding="0" cellspacing="0">
				<tr>
					<td class="espacador" colspan="2">
						<img src="../images/pixel.gif">
					</td>
				</tr>
				<tr class="linha_titulo">
					<td>
						Selecione abaixo o(s) filtro(s) que deseja visualizar
					</td>
					<td align="right">
						<input type="button" value="Continuar" class="botao"
							onclick="continuar()">
						<input type="button" value="Cancelar" class="botao"
							onclick="javascript:window.close()">
					</td>
				</tr>
				<tr>
					<td class="espacador" colspan="2">
						<img src="../images/pixel.gif">
					</td>
				</tr>
			</table>
			<table width="100%" border="0" cellpadding="0" cellspacing="0"
				class="form">
				<%
						// comentado bug: 1339
						// Set niveisPlanejamento = new ConfiguracaoDao(request).getConfiguracao().getSisGrupoAtributoSgaByCodSgaGrAtrNvPlan().getSisAtributoSatbs();

						// funcionamento: 1. obter o grupo de atributos de nivel de planejamento configurado para o sistema;
						//                2. obter a lista de atributos ordenada desse grupo;	
						List niveisPlanejamento = new SisGrupoAtributoDao()
						.getAtributosOrdenados(configuracaoCfg.getSisGrupoAtributoSgaByCodSgaGrAtrNvPlan());
						Iterator it = niveisPlanejamento.iterator();
						while (it.hasNext()) {
							SisAtributoSatb nivel = (SisAtributoSatb) it.next();
				%>
				<tr>
					<td align="right">
						<img src="../images/relAcomp/<%=nivel.getAtribInfCompSatb()%>"
							border="0">
					</td>
					<td>
						<input type="checkbox" class="form_check_radio" name="nivel"
							value="<%=nivel.getCodSatb()%>">
						<%=nivel.getDescricaoSatb()%>
					</td>
				</tr>
				<%
				}
				%>
			
				<tr>
					<td align="right">
						<img src="../images/sem_conteudo.png" border="0" height="16"
							width="16">
					</td>
					<td>
						<input type="checkbox" class="form_check_radio" name="semInformacaoNivel" value="">
						Sem informação
					</td>
				</tr>
				
				<tr>
					<td class="espacador" colspan="2">
						<img src="../images/pixel.gif">
					</td>
				</tr>
			</table>
		</form>
		<%
		} catch (Exception e) {
		%>
		<%@ include file="/excecao.jsp"%>
		<%
		}
		%>
	</body>

</html>
