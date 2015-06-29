

function gerarRelatorio(form, situacaoDatas){

	var selecionados = "";
	var primeiroCod = "";
	var i = 0;
	var el = document.getElementsByTagName("INPUT");
	var fim = el.length;
  
   	while (i < fim ){
    	if ((el.item(i).type  == "checkbox") && (el.item(i).value != "todos")) {          
        	if (el.item(i).checked == true){
         	  	selecionados = selecionados + el.item(i).value + ";";
         		if (primeiroCod == "")
        			primeiroCod = el.item(i).value;
        	}
        }        
        i++;  
   	}
   
	if (selecionados == "") {
    	alert("Não há ítens selecionados!");
   	}  	 
	else{
		document.form.codigosItem.value = selecionados;
		if(situacaoDatas == true) {
			document.form.action = "situacaoDatas.jsp?itemDoNivelClicado="+primeiroCod;
		} else {
			document.form.action = "relAcompanhamento/relatorios/relatorioImpresso2.jsp?itemDoNivelClicado="+primeiroCod;
		}
		document.form.submit();
	}
}

function trocarAba(nomeAba) {
	document.forms[0].action = nomeAba;
	document.forms[0].clicouAba.value = "S";
	document.forms[0].submit();
}		
		
function trocaAbaSemAri(link, codIettRelacao){
	document.form.codIettRelacao.value = codIettRelacao;
	document.form.itemDoNivelClicado.value = codIettRelacao;
	document.form.action = link;
	document.form.submit();
}
function trocaAba(link, codAri){
	document.forms[0].codIettRelacao.value = '';
	document.forms[0].codAri.value = codAri;
	document.forms[0].action = link;	
	document.forms[0].submit();
}
function trocaAbaImpressao(link){
	document.forms[0].action = link;
	document.forms[0].submit();
}
function abrirPopUpInserirAnotacao(codItem){
	abreJanela("popUp/popUpFormAnotacao.jsp?codIett=" + codItem, 500, 300, "Inserir");
}
function abrirPopUpListaAnotacao(codItem){
	abreJanela("popUp/popUpListaAnotacao.jsp?codIett=" + codItem, 500, 300, "Listar");
}
function abrirPopUpGrafico(codOrgao, codTipoAcompanhamento){
	var parametros = "niveisPlanejamento=" + document.form.niveisPlanejamento.value;
	parametros += "&orgaoEscolhido=" + document.form.orgaoEscolhido.value;
	parametros += "&periodoEscolhido=" + document.form.periodoEscolhido.value;
	parametros += "&semInformacaoNivelPlanejamento=" + document.form.semInformacaoNivelPlanejamento.value;
	if(document.form.referencia_hidden.value == null || document.form.referencia_hidden.value == ""){
		parametros += "&referencia=" + document.form.mesReferencia.value;
	}
	else{
		parametros += "&referencia=" + document.form.referencia_hidden.value;
	}
	
	if(codOrgao > 0){
		var janela = abreJanela("popUp/popUpGrafico.jsp?codOrgao=" + codOrgao + "&" + "codTipoAcompanhamento=" + codTipoAcompanhamento + "&" + parametros, 700, 550, "popupGrafico");
		janela.focus();
	} 
	else {
		var janela = abreJanela("popUp/popUpGrafico.jsp?codTipoAcompanhamento=" + codTipoAcompanhamento + "&" + parametros, 700, 550, "popupGrafico");
		janela.focus();
	}	
}

/* Chama o popup de Salvar Pesquisa */
function aoClicarSalvarPesquisa(){
	var parametros = 'periodo='+ document.form.periodo.value;
	parametros+= '&' + 'codTipoAcompanhamento=' + document.form.codTipoAcompanhamento.value;
	parametros+= '&' + 'acompReferenciaAref=' + document.form.referencia.value;
	var janela = abreJanela(_pathEcar + '/acompanhamento/popUp/popupGravarPesquisa.jsp?'+parametros,'500','270','');
	janela.focus();
}

function abrirDadosGerais(){
	document.form.action = "dadosGerais.jsp";
	document.form.submit();	
}


function detalharItemEstrutura(form, status, codAri, periodo, codAcomp, mesRef, pagina) {
	form.action = (pagina.indexOf("&")>0 || pagina.indexOf("?")>0)?_pathEcar + "/" + pagina + "&":_pathEcar + "/" + pagina + "?";
 	if(status == "true") {
		form.action += "relatorio=" + status + "&codAri=" + codAri + "&periodo=" + periodo + "&refer=" + mesRef + "&codAcomp=" + codAcomp;
 	} else {
		form.action += "codAri=" + codAri + "&periodo=" + periodo + "&mesReferencia=" + mesRef + "&codAcomp=" + codAcomp;
 	}
	
	form.submit();
}

function aoClicarConsultar(form, codigo, codIett, abaConfigurada, nomeAbaSituacao){ 

	
	if(abaConfigurada == true) {
		form.codAri.value = codigo;
		form.referencia.value = "";
		document.form.action = _pathEcar + "/acompanhamento/situacao/relatorios.jsp?primeiroIettClicado=" + codIett + "&primeiroAriClicado=" + codigo + "&tela=V";
		document.form.submit();
	} else {
		alert("Usuário não tem acesso a aba: " + nomeAbaSituacao);
	}	
}

function aoClicarConsultarExibicaoAba(form, link, codigo, codIett, abaConfigurada, nomeAbaSituacao){ 

	
	if(abaConfigurada == true) {
		form.codAri.value = codigo;
		form.referencia.value = "";
		// o "&tela=V" vem do link
		document.form.action = _pathEcar + "/" + link + "&primeiroIettClicado=" + codIett + "&primeiroAriClicado=" + codigo;
		document.form.submit();
	} else {
		alert("Usuário não tem acesso a aba: " + nomeAbaSituacao);
	}	
}

function abrirPopUpNivelPlanejamento(){

	var info = document.getElementById("semInformacaoNivelPlanejamento").value;
	if(info == ""){
		abreJanela("popUp/popUpNivelPlanejamento.jsp?opcaoSemInformacao=N", 500, 400, "Nivel");
	}
	else{
		abreJanela("popUp/popUpNivelPlanejamento.jsp?opcaoSemInformacao=" + info, 500, 400, "Nivel");
	}
}

function checkAll(idItem){
	var i = 0;
	var el = document.getElementsByTagName("INPUT");
   	var fim = el.length;
   	   	   	  		
   	if (idItem != null){
   		var idCombo = "ComboClicado" + idItem;    		
   		if(document.getElementById(idCombo).value == "")
   			document.getElementById(idCombo).value = "true";   	
   		else
  			document.getElementById(idCombo).value = "";
  			
   		while (i < fim ){
    		if (el.item(i).type  == "checkbox") {          
        		if (document.getElementById(idCombo).value == ""){        			
        			if (el.item(i).checked == true && el.item(i).id == idItem)
            			el.item(i).checked = false;            		
            	}
            	else{            		
            		if (el.item(i).checked == false && el.item(i).id == idItem)
            			el.item(i).checked = true;
            	}
        	}
        	i++;  
   		}   		
   }
   else{
   
   		if(document.getElementById('ComboClicado').value == "")
   			document.getElementById('ComboClicado').value = "true";   	
   		else
  			document.getElementById('ComboClicado').value = "";
  			
   		while (i < fim ){
    		if (el.item(i).type  == "checkbox") {          
        		if (document.getElementById('ComboClicado').value == ""){
        			if (el.item(i).checked == true)
            			el.item(i).checked = false;            		
            	}
            	else{
            		if (el.item(i).checked == false)
            			el.item(i).checked = true;
            	}
        	}
        	i++;  
   		}   
   }
}


 //Chama tela de filtros 
function aoClicarPesquisar(){
   codTipoAcompanhamento = document.form.codTipoAcompanhamento.value;
   periodo = document.form.periodo.value;
   codReferencia = document.form.mesReferencia.value;
   hidAcao = document.form.hidAcao.value;  
   document.form.action = "filtroItens.jsp?codTipoAcompanhamento="+codTipoAcompanhamento+"&periodo="+periodo+"&codReferencia="+codReferencia+"&vemMonitoramento=S";
   document.form.submit();
}



function aoClicarGrafico(){ 	
	codSecretaria = document.form.codSecretaria; 
	codTipoAcompanhamento = codTipoAcompanhamento = document.getElementById('codTipoAcompanhamento').value;
	abrirPopUpGrafico(codSecretaria, codTipoAcompanhamento);	
}









