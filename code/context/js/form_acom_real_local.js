
// Funcao para Carregar Local - passando por parametro o Grupo raiz ( Raiz da arvore )
function carregarlocalRaiz(){
	pesquisarLocal();
}

// Funcao para Carregar Local - passando por parametro o subgrupo ( carrega sub grupo )
function carregarlocal(){
	var grpId = $F('subgrupo');
	pesquisarLocal(grpId);
}

// Funcao Ajax - realiza chamada para o servico pesquisar local por grupo 
function pesquisarLocal(){
	
	var podeGravar = document.getElementById('podeGravar').value;
	
	var url = '../../ajax/PesquisaRealizadoFisicoPorLocalServlet';
	var pars = 'codARF=' + $F('codARF') + '&podeGravar=' + podeGravar;

	var myAjax = new Ajax.Request( url, { method: 'get', parameters: pars, 
											onComplete: montalistagemLocal, 
											onLoading: function(){ 
																	$('divLocal').innerHTML = 'Carregando...';
																  },
											onFailure: function(){ 
																	$('divLocal').innerHTML = 'Nao foi possivel carregar locais...';
																 } 
										 }
								   );
}

// Funcao Ajax - Resposta - trata a resposta da chamada ajax do servico de pesquisa de local por grupo
function montalistagemLocal(requisicaoOriginal)
{
	//alert('funcionou');
	$('divLocal').innerHTML = requisicaoOriginal.responseText;
}


// Funcao calcular total por local
function calcularTotal(){
	var form = $('form');
	var total = 0.0;

	for (var i=0;i<form.length;i++)
	{
		if(form.elements[i].type=='text'){
		
			var field = form.elements[i].name;
			var fieldArray = new Array();
			var valorTotal = 0.0;
			
			fieldArray = field.split('|');
			
			if (fieldArray[0] == 'cmp' && (fieldArray[1] != '' || fieldArray[1] != null)){

				var fieldNumero = 0.0;
				var strAnterior = new String( form.elements[i].value );
				var strAtual = new String('');
				forDiferente = true;
				while (forDiferente) {
					strAtual = strAnterior.replace('.','');
					if (strAtual != strAnterior){
						strAnterior = strAtual;
					}
					else{
						forDiferente = false;
					}
				}
				
				strAtual = strAtual.replace(',','.');
				fieldNumero = parseFloat(strAtual);
				if ( isNaN( fieldNumero )==false ){
					total = total + fieldNumero;
				}
			}
		}
	}
	var formatResult = new String( total );
	if( formatResult.indexOf('.')!= -1 ){
		formatResult = formatResult.replace('.',',');		
	}else{
		formatResult = formatResult + ',00';		
	}

	$('total').value = formatResult;
}


/**
 * Testa o browser que está sendo utilizado pelo usuário final.
 * Se for IE, retorna TRUE, caso contrário FALSE.
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
	
    if ($('podeGravar').value == 'N') {
    	alert('Você não tem autorização para alterar os dados apresentados!');
    	return false;
    }
    
	var url = '../../ajax/salvarAcompRealFisicoLocal';
	var pars = Form.serialize('form');
	var myAjax = new Ajax.Request( url, { method: 'post', parameters: pars, 
											onComplete: responseSalvarLocal, 
											onLoading: function(){ },
											onFailure: function(){} 
										 }
								   );
    return true;
}


// Funcao Ajax - realiza chamada para o servico Salvar indicador por local
function excluirLocal(){
	
	var url = '../../ajax/excluirIndicadorLocal';
	var pars = $F('qtdePrevistaAction');
	var myAjax = new Ajax.Request( url, { method: 'post', parameters: pars, 
											onComplete: responseSalvarLocal, 
											onLoading: function(){},
											onFailure: function(){} 
										}
								   );
}

// Funcao Ajax - Resposta - trata a resposta da chamada ajax do servico de pesquisa de local por grupo
function responseSalvarLocal(requisicaoOriginal)
{
	var resposta = requisicaoOriginal.responseText;
	if ( resposta == 'true'){
		
		alert('Operacao realizada com sucesso!');

		var nivelVisual = $('nivel').value;
		var documentMaster = window.opener.document;
		
	    documentMaster.action = "ctrlRealizadoFisico.jsp";	
	    documentMaster.getElementById('hidNivel').value = nivelVisual;
		documentMaster.form.submit();
	
	}else{
		alert('ERRO: Nao foi possivel salvar registro');
	}
	window.close();
	
}


// Funcao Ajax - realiza chamada para o servico Salvar indicador
function salvarIndicador(indicador){
    if ($('podeGravar').value == 'N') {
    	alert('Você não tem autorização para alterar os dados apresentados!');
    	return false;
    }
    
	var url = '../../ajax/salvarAcompRealFisicoLocal';
  
	var pars = Form.serialize('form');
	pars = pars + "&grupo=" + $F('subgrupo');
  
	var myAjax = new Ajax.Request( url, { method: 'post', parameters: pars, 
											onComplete: responseSalvarLocal, 
											onLoading: function(){},
											onFailure: function(){} 
										 }
								   );
    return true;
}
 
function responseSalvarIndicador(requisicaoOriginal)
{
  var resposta = requisicaoOriginal.responseText;
  if ( resposta == 'true'){
    alert('Operacao realizada com sucesso!');
  }else{
    alert('ERRO: Nao foi possivel salvar registro');
  }
  window.close();
}
 