


<%
	//Necessários para os botoes avanca/retrocede da tela de visuzalizacao
	AcompReferenciaItemAri acompReferenciaItem = ari;
%>

<%@ include  file="../form_visualizar.jsp" %>	


<script type="text/javascript"> 


function voltarTelaAnterior(tela) {
	document.forms[0].action = tela;
	document.forms[0].submit();
}

/*
function trocaAba(nomeAba) {
	document.forms[0].action = nomeAba;
	document.forms[0].submit();
}*/
function trocaAba(link, codAri, codRegd){
	document.forms[0].codIettRelacao.value = '';
	document.forms[0].codAri.value = codAri;
	document.forms[0].codRegd.value = codRegd;
	document.forms[0].action = link;
	document.forms[0].submit();
}
function reloadVisualizar(){
	if(document.forms[0].periodo.value != ""){
		document.forms[0].action = "relatorios.jsp?tela=V";
		document.forms[0].submit();		
	}	
}
function trocaAbaSemAri(link, codIettRelacao){
	document.form.codIettRelacao.value = codIettRelacao;
	document.form.itemDoNivelClicado.value = codIettRelacao;
	document.form.action = link;
	document.form.submit();
}

function trocaAbaImpressao(link){
	document.forms[0].action = link;
	document.forms[0].submit();
}

//pra quando for editar em visualização
function reloadRegistro(primeiroIettClicado, primeiroAriClicado, itemDoNivelClicado, codTpfa){
	if(document.forms[0].periodo.value != ""){
		document.forms[0].action = "<%=_pathEcar%>/acompanhamento/situacao/relatorios.jsp?primeiroIettClicado="+ 
									primeiroIettClicado+ "&primeiroAriClicado="+ primeiroAriClicado + 
									"&itemDoNivelClicado=" + primeiroIettClicado+ "&funcaoParecer=" + "<%=abaSelecionada%>" 
									+ "&codTpfa=" + codTpfa;
		document.forms[0].submit();		
	}	
}

</script>


