

// Funcao responsavel por atualizar o campo <Qtde realizada informada por local>
function atualizaCheckbox ( ){
	var previsto1 = document.getElementById('indPrevPorLocal1').checked;
	document.getElementById('indRealPorLocal1').checked = previsto1;
	
	var previsto2 = document.getElementById('indPrevPorLocal2').checked;
	document.getElementById('indRealPorLocal2').checked = previsto2;
}

function checkboxSincroniza( campo1, campo2  ){

 	var checkboxCopiar2 = document.getElementById(campo2 );
 	var checkboxCopiar1 = document.getElementById( campo1 );
 	checkboxCopiar2.checked = checkboxCopiar1.checked;
}

// Funcao responsavel por habilitar/desabilitar os botoes e campos referentes
// a edicao de quantidades/valores por local.
function atualizarInfPrevistoLocal(){
	if (document.getElementById('codIettir') == null){
		// Inclusao
		if (document.getElementById('indPrevPorLocal1').checked == true){
			document.getElementById('labelAviso').innerHTML='� necess�rio salvar o registro antes de indicar as quantidades/valores por local';	
		}
		else {
			document.getElementById('labelAviso').innerHTML='';
		}
	}
	else {
		// Alteracao


		//$('indPrevPorLocal1').disabled = true;
		//$('indPrevPorLocal2').disabled = true;

		// Obtendo os c�digos dos exerc�cios
		//var vetorCodigosExercicios = new Array();
		//var tamVetorCodigos = 0;
		//for (var i=0;i<form.length;i++){
			//if (form.elements[i].name.match("exercicio") != null){
				//tamVetorCodigos = vetorCodigosExercicios.push(form.elements[i]);
			//}
		//}	
		
		// Se as quantidades/valores forem informadas por local
		document.getElementById('labelAviso').innerHTML='';
		//if (document.getElementById('indPrevPorLocal1').checked == true){
			
			// Habilita a edi��o de quantidades/valores
			//document.getElementById('btnInfPrevistoLocal').disabled = false;	
			
			// Desabilita a edi��o dos campos de total por exerc�cio na tela principal.
			//for (var i = 0; i < tamVetorCodigos; i++){
				//var nomeCampoTelaPrincipal = "qtdPrevistaIettf" + vetorCodigosExercicios[i].value;
				//document.getElementById(nomeCampoTelaPrincipal).readOnly = true;
			//}
		//}
		//else {
			// Desabilita a janela de edi��o de quantidades/valores por local e 
			// habilita os campos de total por exerc�cio.
			//document.getElementById('btnInfPrevistoLocal').disabled = true;
			//for (var i = 0; i < tamVetorCodigos; i++){
				//var nomeCampoTelaPrincipal = "qtdPrevistaIettf" + vetorCodigosExercicios[i].value;
				//document.getElementById(nomeCampoTelaPrincipal).readOnly = false;
			}

			//$('indPrevPorLocal1').checked = false;
			//$('indPrevPorLocal2').checked = true;	
		/*
			// Pede confirma��o ao usu�rio
		  	if (confirm("Essa opera��o excluir� todas as quantidades/valores atualmente gravados. Deseja execut�-la assim mesmo?")){
				// Exclui os registros
				excluirLocal();
				
				// Desabilita a janela de edi��o de quantidades/valores por local e 
				// habilita os campos de total por exerc�cio.
				$('btnInfPrevistoLocal').disabled = true;
				for (var i = 0; i < tamVetorCodigos; i++){
					var nomeCampoTelaPrincipal = "qtdPrevistaIettf" + vetorCodigosExercicios[i].value;
					$(nomeCampoTelaPrincipal).readOnly = false;
				}
    	  	}
		  	else{
		    	alert("A exclus�o foi cancelada!")

				// Atualiza os checkboxes
				$('indPrevPorLocal1').checked = true;
				$('indPrevPorLocal2').checked = false;				
    	  	}
    	*/ 	
		//}
	//}
}


// Funcao responsavel por atualizar o label da tabela
function trocalabelQtdePrevista ( ){

	if ( document.getElementById('indTipoQtde1').checked==true && document.getElementById('indTipoQtde2').checked==false ){
		//document.getElementById('label1').innerHTML='Quantidade';
		//document.getElementById('label2').innerHTML='Quantidade Prevista';
	
	}

	if ( document.getElementById('indTipoQtde1').checked==false && document.getElementById('indTipoQtde2').checked==true ){
		//document.getElementById('label1').innerHTML='Valor';
		//document.getElementById('label2').innerHTML='Valor Previsto';		
	}	
}

// Funcao para desabilitar quadro de quantidades prevista
function disableTabela(formulario){
	 for (var i=0;i<formulario.length;i++) {

		var nomeCampo = formulario.elements[i].name;
		if ( nomeCampo.indexOf('qtdPrevistaIettf')!=-1 ){
			document.getElementById(formulario.elements[i]).readOnly = true;
		}
     } 
}

// Funcao para habilitar quadro de quantidades prevista
function enableTabela(formulario){
	 for (var i=0;i<formulario.length;i++) {

		var nomeCampo = formulario.elements[i].name;
		if ( nomeCampo.indexOf('qtdPrevistaIettf')!=-1 ){
			document.getElementById(formulario.elements[i]).readOnly = false;
		}
     } 
}

// Funcao para Carregar Local - passando por parametro o Grupo raiz ( Raiz da arvore )
function carregarlocalRaiz(principal){
	pesquisarLocal(principal);
}

// Funcao para Carregar Local - passando por parametro o subgrupo ( carrega sub grupo )
function carregarlocal(){
	//var grpId = $F('subgrupo');
	var grpId = document.getElementById('subgrupo').value;
	pesquisarLocal(grpId);
}

// Funcao Ajax - realiza chamada para o servico pesquisar local por grupo 
function pesquisarLocal( grpId ){
	

	var codIettir = document.getElementById('codIettir').value;
	var codIett = document.getElementById('codIett').value;
	var itemSelecionado = document.getElementById('subgrupo');
	if (itemSelecionado == null){
		itemSelecionado = new String('0');
	}
	else{
		itemSelecionado = itemSelecionado.selectedIndex;
	}
	var url = '../../ajax/pesquisaLocalByGrupo';
	var pars = 'grupo=' + grpId + '&codIettir=' + codIettir + '&codIett=' + codIett + '&itemSelecionado=' + itemSelecionado;
	var myAjax = new Ajax.Request( url, { method: 'get', parameters: pars, 
											onComplete: montalistagemLocal, 
											onLoading: function(){ 
																	document.getElementById('divLocal').innerHTML = 'Carregando...';
																  },
											onFailure: function(){ 
																	document.getElementById('divLocal').innerHTML = 'Nao foi possivel carregar locais...';
																 } 
										 }
								   );
}

// Funcao Ajax - Resposta - trata a resposta da chamada ajax do servico de pesquisa de local por grupo
function montalistagemLocal(requisicaoOriginal)
{
	document.getElementById('divLocal').innerHTML = requisicaoOriginal.responseText;
}

// Funcao Ajax - realiza chamada para o servic pesquisar local por local
function pesquisarLocalHierarquia( localId ){
	
	//var exe1 = $F('exercicio1');	
	//var exe2 = $F('exercicio2');	
	//var exe3 = $F('exercicio3');	
	//var exe4 = $F('exercicio4');

	var exe1 = document.getElementById('exercicio1').value;	
	var exe2 = document.getElementById('exercicio2').value;	
	var exe3 = document.getElementById('exercicio3').value;	
	var exe4 = document.getElementById('exercicio4').value;
	

	var nomeCampo1 = 'cmp|' + localId + '|' + exe1;
	var nomeCampo2 = 'cmp|' + localId + '|' + exe2;
	var nomeCampo3 = 'cmp|' + localId + '|' + exe3;
	var nomeCampo4 = 'cmp|' + localId + '|' + exe4;			
	var nomeCampoTotal = 'cmp|' + localId + '|total';			

	var nomeLabel1 = 'label|' + localId + '|' + exe1;
	var nomeLabel2 = 'label|' + localId + '|' + exe2;
	var nomeLabel3 = 'label|' + localId + '|' + exe3;
	var nomeLabel4 = 'label|' + localId + '|' + exe4;			

	var campo1 = document.getElementById(nomeCampo1);
	var campo2 = document.getElementById(nomeCampo2);
	var campo3 = document.getElementById(nomeCampo3);
	var campo4 = document.getElementById(nomeCampo4);		
	var campoTotal = document.getElementById(nomeCampoTotal);			

	var label1 = document.getElementById(nomeLabel1);
	var label2 = document.getElementById(nomeLabel2);
	var label3 = document.getElementById(nomeLabel3);
	var label4 = document.getElementById(nomeLabel4);			


	campo1.value = '';	
	campo2.value = '';
	campo3.value = '';
	campo4.value = '';
	campoTotal.value= '';
	
	//campo1.style.background = '#FFF9DC';
//	campo1.style.background = '';
//	campo2.style.background = ""; 
//	campo3.style.background = ""; 
//	campo4.style.background = ""; 
//	campoTotal.style.background = ""; 
	
	campo1.disabled='disabled';
	campo2.disabled='disabled';
	campo3.disabled='disabled';
	campo4.disabled='disabled';
	campoTotal.disabled='disabled';	
	
	label1.style.visibility = 'hidden';
	label2.style.visibility = 'hidden';
	label3.style.visibility = 'hidden';
	label4.style.visibility = 'hidden';
	
	var divCampo = 'divLocal|' + localId;
	
	var codIettir = document.getElementById('codIettir').value;
	var url = '../../ajax/pesquisaLocal';
	var pars = 'local=' + localId  + '&codIettir=' + codIettir;
	var myAjax = new Ajax.Request( url, { method: 'get', parameters: pars, 
											onComplete: function(requisicaoOriginal){
																   document.getElementById(divCampo).innerHTML = requisicaoOriginal.responseText;
																                    },
											onLoading: function(){ 
																	document.getElementById(divCampo).innerHTML = 'Carregando...';
																  },
											onFailure: function(){ 
																	document.getElementById(divCampo).innerHTML = 'Nao foi possivel carregar locais...';
																 } 
										 }
								   );
}


// Funcao calcular total por local
function calcularTotalLocal(localId){
	var form = document.getElementById('qtdeForm');
	var total = 0.0;
	var nomeCampo;
	for (var i=0;i<form.length;i++) {
		if(form.elements[i].type=='text'){
		
			var field = form.elements[i].name;
			var fieldArray = new Array();
			fieldArray = field.split('|');
			if ( fieldArray[1] == localId && fieldArray[2]!='total' ){	

				if ( form.elements[i].value != '' ) {
					var fieldNumero = 0.0;
					var fieldFormat = new String( form.elements[i].value );
					fieldFormat = fieldFormat.replace(/(\.)/g,'');
					fieldFormat = fieldFormat.replace(/(\,)/g,'.');
					fieldNumero = parseFloat(fieldFormat);
					if ( isNaN( fieldNumero )==false ){
						total = total + fieldNumero;
						form.elements[i].value = formataValor(fieldNumero);
					}else{
						form.elements[i].value = "";
					}
				}
				
			} else if ( fieldArray[1] == localId ) {
				nomeCampo = form.elements[i].name;
			}
			
		}
	}

	document.getElementById( nomeCampo ).value = formataValor(total);
	calcularTotalGeral();
}

// Funcao calcular total por exercicio
function calcularTotalExercicio(exeId){

	var form = document.getElementById('qtdeForm');
	var total = 0.0;
	var nomeCampo;
	for (var i=0;i<form.length;i++)
	{
		if(form.elements[i].type=='text'){
		
			var field = form.elements[i].name;
			var fieldArray = new Array();
			fieldArray = field.split('|');
			if ( fieldArray[2] == exeId ){	

				if ( form.elements[i].value != '' ) {
					var fieldNumero = 0.0;
					var fieldFormat = new String( form.elements[i].value );
					fieldFormat = fieldFormat.replace(/(\.)/g,'');
					fieldFormat = fieldFormat.replace(/(\,)/g,'.');
					fieldNumero = parseFloat(fieldFormat);
					if ( isNaN( fieldNumero )==false ){
						total = total + fieldNumero;
						form.elements[i].value = formataValor(fieldNumero);
					}else{
						form.elements[i].value = "";
					}
				}
			} 
		}
	}
	nomeCampo = 'qtdPrevistaIettf' + exeId;
	document.getElementById(nomeCampo ).value = this.formataValor(total);
	calcularTotalGeral();
}

// Funcao calcular total geral
function calcularTotalGeral(){

	var form = document.getElementById('qtdeForm');
	var total = 0.0;
	for (var i=0;i<form.length;i++)
	{
		if(form.elements[i].type=='text'){
		
			var field = form.elements[i].name;
			var fieldArray = new Array();
			fieldArray = field.split('|');
			if ( fieldArray[2] == 'total' ){	

				if ( form.elements[i].value != '' ) {
					var fieldNumero = 0.0;
					var fieldFormat = new String( form.elements[i].value );
					fieldFormat = fieldFormat.replace(/(\.)/g,'');
					fieldFormat = fieldFormat.replace(/(\,)/g,'.');
					fieldNumero = parseFloat(fieldFormat);
					if ( isNaN( fieldNumero )==false ){
						total = total + fieldNumero;
						form.elements[i].value = formataValor(fieldNumero);
					}else{
						form.elements[i].value = "";
					}
				}
			} 
		}
	}

	document.getElementById( 'totalGeral' ).value = this.formataValor(total);

}

/**
 * Converte um double em um String monet�rio.
 */
function converterParaMoeda(valor){
	var formatResult = new String( valor );
	
	if ( formatResult.indexOf('.')!= -1){
		while (formatResult.length - formatResult.indexOf('.') - 1 < 2){
			formatResult = formatResult + '0';
		}
		formatResult = formatResult.replace('.',',');
	}else{
		formatResult = formatResult + ',00';	
	}
	return formatResult;
}
/*
 * Espera um valor em formato xxxx.xxxx
 * utilizando ponto como separador de casas decimais e sem virgulas
 * */
function ajustaCasasDecimais(valor,min,max){
	valor=parseFloat(valor);
	valor = new String( parseFloat(valor.toFixed(max)));	

	if (min==0 && valor.indexOf('.')== -1)
		return valor;
	if (min!=0 && valor.indexOf('.')== -1)
		valor = valor+".";
	while (valor.length - valor.indexOf('.') - 1 < min){
			valor = valor + '0';
	}
	
	return valor;
}

function formataValor(valor){
	if(undefined===window.tipoQtd){
		window.tipoQtd="Q";
	}
	if(window.tipoQtd=="Q"){
		min=0;
		max=10;
	}else{
		min=2;
		max=2;
	}
	valor = ajustaCasasDecimais(valor,min,max);
	return valor.replace(".", ",");
}

/**
 * Testa o browser que est� sendo utilizado pelo usu�rio final.
 * Se for IE, retorna TRUE, caso contr�rio FALSE.
 * @return boolean
 */
 function isIE() {
	var browserName = navigator.appName; 
	var isie = false;	
	
	if (browserName=="Microsoft Internet Explorer")
		isie = true;
	
	return isie;
 } // end isIE

 
 // Funcao Ajax - realiza chamada para o servico Salvar indicador por local
function salvarLocal(){
	
	var contextPath = document.getElementById('contextPath').value;
	var caminho = '<img src="'+contextPath+'/images/indicador.gif" border="0" align="absmiddle" title="loading ...">';
	var url = '../../ajax/salvarIndicadorLocal';
	var pars = Form.serialize('qtdeForm');
	var myAjax = new Ajax.Request( url, { method: 'post', parameters: pars, 
											onComplete: responseSalvarLocal, 
											onLoading: function(){
													var loading = '<div id="overlay" onclick="javascript:this.style.display=\'none\';"></div>'+
													              '<div id="aguarde" > '+
													              caminho +	
														          '<span class="fontAguarde">Aguarde ...</span>'+
													              '</div>';
													document.getElementById('divLocal').innerHTML = loading;
												  },
											onFailure: function(){ 
																	
																 } 
										 }
								   );
}

// Funcao Ajax - realiza chamada para o servico Salvar indicador por local
function excluirLocal(){
	
	var url = '../../ajax/excluirIndicadorLocal';
	var pars = document.getElementById('qtdePrevistaAction').value;
	var myAjax = new Ajax.Request( url, { method: 'post', parameters: pars, 
											onComplete: responseSalvarLocal, 
											onLoading: function(){ 
																	
																  },
											onFailure: function(){ 
																	
																 } 
										 }
								   );
}

// Funcao Ajax - Resposta - trata a resposta da chamada ajax do servico de pesquisa de local por grupo
function responseSalvarLocal(requisicaoOriginal)
{
	var resposta = requisicaoOriginal.responseText;
	if ( resposta == 'true'){
		alert('Operacao realizada com sucesso!');
	}else{
		alert('ERRO: Nao foi possivel salvar registro');
	}
	window.close();
}

function desativaServicoPrevisto(desativar){
	
	document.getElementById('indTipoAtualizacaoPrevistoA').disabled = desativar;
	document.getElementById('indTipoAtualizacaoPrevistoM').disabled = desativar;
	
	document.getElementById('previstoServicoSer').disabled = desativar;
	
}
 
function desativaServicoRealizado(desativar){
	
	document.getElementById('indTipoAtualizacaoRealizadoA').disabled = desativar;
	document.getElementById('indTipoAtualizacaoRealizadoM').disabled = desativar;
	
	document.getElementById('realizadoServicoSer').disabled = desativar;
	
}