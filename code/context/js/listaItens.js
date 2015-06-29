/*
 *  Vari�vel global que ser� usada para recuperar o codIett do n� pai,
 * bem como para verificar se existe alguma solicita��o pendente.
 * Entende-se por solicita��o pendente, um pedido feito e ainda n�o retornado pelo o Servlet Ajax.
 */
var codItemEstruturaPai = '';

/**
 * Vari�vel global usada quando o Tipo de Acompanhamento estiver setado para "Separar por Orgao".
 * O formato � semelhante a "_org37" onde 37 � o c�digo do �rg�o
 */
var nomeOrgaoSepararPorOrgao = '';

/**
 * Vari�veis globais que representam as figuras.
 */
var imagemMais = '/images/collapsed_button.gif';
var imagemMenos = '/images/expanded_button.gif';
var imagemQuadradoBranco = '/images/square.gif';
var barraCarregando = '/images/relAcomp/progress_bar.gif';

/**
 * Funcao para Carregar todos os filhos de um determinado n� -
 * passando por parametro o identificador do n� ( codIett, codOrg ) 
 */	
function carregarFilhosNo( codIett, codOrg, enderecoAba, periodo, referenciaOrgaoAtual, abaConfigurada, nomeAbaVisualizacao){
	// S� processa se n�o existir solicita��o pendente
	if(codItemEstruturaPai ==  '')
		carregarFilhosAjax( codIett, codOrg, enderecoAba, periodo, referenciaOrgaoAtual, abaConfigurada, nomeAbaVisualizacao);
}

/**
 * Funcao Ajax - realiza chamada para o servlet que processar� os filhos do n�
 */	
function carregarFilhosAjax( codIett, codOrg, enderecoAbaRegistro, periodo, referenciaOrgaoAtual, abaConfigurada, nomeAbaVisualizacao){
	
	var codUsuarioLogado = document.getElementById('codUsuarioLogado').value;	
	var status = document.getElementById('status').value;	
	var pathEcar = document.getElementById('pathEcar').value;
	var idPagina = document.getElementById('idPagina').value;
	var caminhoUrl = document.getElementById('caminhoUrl').value;
	var codTipoAcompanhamento = document.getElementById('codTipoAcompanhamento').value;
	var exigeLiberarAcompanhamento = document.getElementById('exigeLiberarAcompanhamento').value;
	var permissaoAdministradorAcompanhamento = document.getElementById('permissaoAdministradorAcompanhamento').value;
	var codAref = referenciaOrgaoAtual;
	var codArefSelecionado = document.getElementById('codAref').value;
	var enderecoAbaRegistro = enderecoAbaRegistro;		
	var periodo = periodo;
	var url = pathEcar + '/ajax/geraFilhosIett';
	var abaConfigurada = abaConfigurada;
	var nomeAbaVisualizacao = nomeAbaVisualizacao;
	
	//seta a vari�vel global que ser� utilizada em 'montalistagemLocal' 
	codItemEstruturaPai = codIett;
	if(codOrg != '')
		nomeOrgaoSepararPorOrgao = '_org' + codOrg;
	else
		nomeOrgaoSepararPorOrgao = '';
	
	var pars = 'codUsuarioLogado=' + codUsuarioLogado + '&status=' + status + '&pathEcar=' + pathEcar + '&idPagina=' + idPagina
				+ '&caminhoUrl=' + caminhoUrl +'&codTipoAcompanhamento=' + codTipoAcompanhamento +'&exigeLiberarAcompanhamento=' + exigeLiberarAcompanhamento 
				+'&permissaoAdministradorAcompanhamento=' + permissaoAdministradorAcompanhamento +'&codAref=' + codAref
				+ '&codArefSelecionado=' + codArefSelecionado +'&codIett=' + codIett +'&codOrg=' + codOrg +'&endereco=' 
				+ enderecoAbaRegistro + "&periodo=" + periodo + "&abaConfigurada=" + abaConfigurada 
				+ "&nomeAbaVisualizacao=" + nomeAbaVisualizacao;
	

	$.ajax({
		type: "POST", 
		url: url, 
		data: pars, 
		success: function(msg){ 
  			montalistagemLocal(msg); 
		},
		error: function(xmlHttpRequest, typeError, exception){ 
			adicionaLinhaCarregando(document.getElementById('tabelaItemEstrutura' + nomeOrgaoSepararPorOrgao),'Falha no carregando!', codIett); 
		},
		beforeSend: function(){ 
  			adicionaLinhaCarregandoBarra(document.getElementById('tabelaItemEstrutura' + nomeOrgaoSepararPorOrgao),'Carregando...', codIett);; 
		}					
	});

}


/**
 * Funcao Ajax - Resposta - trata a resposta da chamada ajax
 */	 
function montalistagemLocal(requisicaoOriginal)
{	
	var tblBody = document.getElementById('tabelaItemEstrutura' + nomeOrgaoSepararPorOrgao).tBodies[0];			
	//populando um array com os dados dos filhosIett, que vieram do Servlet via Ajax
	var dadosItemEstrutura = populaArray(requisicaoOriginal);
	var numeroColunasPorFilho = dadosItemEstrutura[0];
	var corLinha = dadosItemEstrutura[1];
	var numeroFilhosExibir = (dadosItemEstrutura.length - 2)/ numeroColunasPorFilho; 		
	var contador = numeroFilhosExibir;
	var filhosExibir='';
	
	//antes de imprimir os filhos, remove a linha 'Carregando' 
	removeLinhaCarregando(document.getElementById('tabelaItemEstrutura' + nomeOrgaoSepararPorOrgao), codItemEstruturaPai);
		
	if(numeroFilhosExibir >= 1 && ((dadosItemEstrutura.length - 2) % numeroColunasPorFilho) == 0){
		//Colocar '-'(menos) no n� da �rvore								
		setarIconeContrairExpandir(imagemMenos, codItemEstruturaPai, nomeOrgaoSepararPorOrgao, true);

		while(contador > 0){	
			//os filhos v�m ordenados por isso devem ser impressos ordenados 
			var posicaoDeInsercao = posicaoNaTabela(codItemEstruturaPai) + (numeroFilhosExibir - contador + 1);	
			var newRow = tblBody.insertRow(posicaoDeInsercao);
			 
			newRow.setAttribute('class','cor_sim');				
			newRow.setAttribute('id','iett' + dadosItemEstrutura[(numeroFilhosExibir - contador) * numeroColunasPorFilho + 2] + nomeOrgaoSepararPorOrgao);			
			newRow.setAttribute('onMouseOver','javascript:destacaLinha(this,\'over\',\'\')');
			newRow.setAttribute('onMouseOut', 'javascript: destacaLinha(this,\'out\',\'cor_sim\')');
			newRow.setAttribute('style', '');
			newRow.setAttribute('bgColor', corLinha);

			//entra no "if" se s� existe 1 ou se � o primeiro filho 
			if(numeroFilhosExibir == 1 || contador == numeroFilhosExibir)
				filhosExibir = dadosItemEstrutura[(numeroFilhosExibir - contador) * numeroColunasPorFilho + 2];
			else
				filhosExibir += ':' + dadosItemEstrutura[(numeroFilhosExibir - contador) * numeroColunasPorFilho + 2];
			
			//Recupera o div com os filhos.
			//Cria um inputHidden com a quantidade de filhos que pode ser exibida inicialmente para este n�.
			//Como inicialmente n�o sabemos a quantidade de filhos deste n�, colocamos '0' 			
			var hiddenFilhosExibir = document.getElementById('hiddenFilhosExibir');
			hiddenFilhosExibir.innerHTML += '<input type="hidden" id="inputHiddenFilhos_iett' + dadosItemEstrutura[(numeroFilhosExibir - contador) * numeroColunasPorFilho + 2] 
											+ nomeOrgaoSepararPorOrgao +'" value="0">';
						
			//preenche todos os td's da linha na tabela
			var newCell;
			var numeroTdTabela = numeroColunasPorFilho -1;
			for(i=0; i<numeroTdTabela; i++){
				newCell = newRow.insertCell(i);		
				//Terminando a formata��o de alguns td's
				if(i==2)
					newCell.setAttribute('nowRap','nowrap');
				if(i==3)
					newCell.setAttribute('align','center');
				if(i==5)
					newCell.setAttribute('align','center');
				if(i>=6){
					newCell.setAttribute('align','center');
					newCell.setAttribute('nowRap','nowrap');
				}	
				if (((i+1) + (numeroFilhosExibir - contador)*numeroColunasPorFilho + 1)!=2)
					newCell.innerHTML = dadosItemEstrutura[(i+1) + (numeroFilhosExibir - contador)*numeroColunasPorFilho + 2];	
				else
					newCell.innerHTML = '';
			}				
			contador--;
		}		
		
		//Seta os filhos que o n� pai pode exibir no formato: "codIett1:codIett2:codIett3" 		
		var inputHiddenFilhos = document.getElementById('inputHiddenFilhos_iett' + codItemEstruturaPai + nomeOrgaoSepararPorOrgao);
		inputHiddenFilhos.setAttribute('value', filhosExibir);		
		
	} else{		
		//Colocar quadrado em branco no n� da �rvore								
		setarIconeContrairExpandir(imagemQuadradoBranco, codItemEstruturaPai, nomeOrgaoSepararPorOrgao, false);
		
		//Se o array n�o � m�ltiplo de ** significa que deu algum erro de processamento dos filhos,
		//ou que n�o tem filhos (dadosItemEstrutura[1]=='')
		if(((dadosItemEstrutura.length - 2) % numeroColunasPorFilho) != 0 && dadosItemEstrutura[1]!=''){
			adicionaLinhaCarregando(document.getElementById('tabelaItemEstrutura' + nomeOrgaoSepararPorOrgao),'Falha no carregamento!', codItemEstruturaPai);
		}				
	}
	
	//seta a vari�vel global para '', indicando que n�o existe requisi��es em aberto
	codItemEstruturaPai = '';	
}

/**
 * Adiciona uma linha na tabela com a mensagem passada.
 * O local, onde ser� inserida a linha, ser� definido pelo "codItemEstruturaPai"
 */	  
function adicionaLinhaCarregando(tableId, message, codIettPai){
	var tblBody = tableId.tBodies[0];			
	var idRowPai = 'iett' + codIettPai + nomeOrgaoSepararPorOrgao;
	var posicaoDeInsercao = posicaoNaTabela(codIettPai) +  1;	
	var rowPai = document.getElementById(idRowPai);
	var newRow = tblBody.insertRow(posicaoDeInsercao);
	var newCell;
	 				
	newRow.setAttribute('id','msgCarregando' + codIettPai + nomeOrgaoSepararPorOrgao);
	newRow.setAttribute('class','cor_sim');
	newRow.setAttribute('bgColor', rowPai.getAttribute('bgColor'));	
	
	newCell = newRow.insertCell(0);
	newCell.setAttribute('colSpan', '2');
	newCell.setAttribute('align','center');
	newCell.innerHTML = message;
	
	newCell = newRow.insertCell(1);
	newCell.setAttribute('colSpan', '5');	
	newCell.innerHTML = '';
	
}

/**
 *Semelhante a 'adicionaLinhaCarregando' s� que acrescenta a barra de carregamento
 */	  
function adicionaLinhaCarregandoBarra(tableId, message, codIettPai){
	var pathEcar = document.getElementById('pathEcar').value;
	//n�mero de colunas da tabela de filhos em "relacaoItens"
	var numeroColunasTabela = document.getElementById('numeroColunasTabela').value;   
	var tblBody = tableId.tBodies[0];			
	var idRowPai = 'iett' + codIettPai + nomeOrgaoSepararPorOrgao;
	var posicaoDeInsercao = posicaoNaTabela(codIettPai) +  1;	
	var rowPai = document.getElementById(idRowPai);
	var newRow = tblBody.insertRow(posicaoDeInsercao);
	var newCell;
	 				
	newRow.setAttribute('id','msgCarregando' + codIettPai + nomeOrgaoSepararPorOrgao);
	newRow.setAttribute('class','cor_sim');
	newRow.setAttribute('bgColor', rowPai.getAttribute('bgColor'));	
	
	newCell = newRow.insertCell(0);
	newCell.setAttribute('colSpan', '2');
	newCell.setAttribute('align','center');
	//newCell.innerHTML = message;
	newCell.innerHTML = "";
	
	newCell = newRow.insertCell(1);
	var numColSpan = numeroColunasTabela - 2;	
	newCell.setAttribute('colSpan', numColSpan);
	newCell.innerHTML = "<img src='" + pathEcar + barraCarregando + "' >" +
						"<br>&nbsp&nbsp&nbsp&nbsp&nbsp " + message;

}

/**
 * Remove a linha especificada da tabela
 */	 
function removeLinhaCarregando(tableId, codIettPai){
	var newRow = document.getElementById('msgCarregando' + codIettPai + nomeOrgaoSepararPorOrgao);			
	var tblBody = tableId.tBodies[0];
	
	tblBody.deleteRow(newRow.rowIndex);		
}

/**
 * Fun��o para expandir e contrair a �rvore de filhos usando javascript
 */	 
function contrairExpandirArvore(codIettPai, orgaoSepararPorOrgao){
	//Verifica se o menu est� contra�do ou expandido
	if(getStatusIconeContrairExpandir(codIettPai, orgaoSepararPorOrgao) == 'mais')
		abrirItem(codIettPai, orgaoSepararPorOrgao);
	else
		fecharItem(codIettPai, orgaoSepararPorOrgao);		
}

/**
 * Fecha todos os filhos de um item
 */	 
function fecharItem(codIett, orgaoSepararPorOrgao){
	
	var filhosIett = (document.getElementById('inputHiddenFilhos_iett' + codIett + orgaoSepararPorOrgao).value).split(':');	
	var qtdeFilhos = filhosIett.length;
	
	//Manda todos os filhos fecharem
	//var indice=0;
	for(var indice = 0; indice < qtdeFilhos; indice++) {			
		var itemFilho = document.getElementById('iett' + filhosIett[indice] + orgaoSepararPorOrgao);		
		var idItemFilho = itemFilho.getAttribute('id');						
		var codIettFechar = idItemFilho.substring(4,idItemFilho.length);
		
		if(codIettFechar.indexOf("_org") != -1)
			codIettFechar = codIettFechar.substring(0,codIettFechar.indexOf("_org"));

		fecharTudo(codIettFechar, orgaoSepararPorOrgao);		
	}
	//Colocar '+'(mais) no n� da �rvore								
	setarIconeContrairExpandir(imagemMais, codIett, orgaoSepararPorOrgao, true);	
}

/**
 * Fecha o item e todos os seus filhos
 */	 
function fecharTudo(codIett, orgaoSepararPorOrgao){
	
	// Oculta o item, setando seu style para "display:none". 
	var idRowItem = 'iett' + codIett + orgaoSepararPorOrgao;
	var rowItem = document.getElementById(idRowItem);
	//Obs.: setAttribute('style','display:none') n�o funciona bem no IE
	rowItem.style.display = 'none';
	//Define um array de filhos
	var filhosIett = (document.getElementById('inputHiddenFilhos_iett' + codIett + orgaoSepararPorOrgao).value).split(':');
	var qtdeFilhos;
	
	//Seta a quantidade de filhos de um Item, levando em considera��o 
	//que inicialmente eles s�o setados como zero. 
	if(filhosIett.length==1 && filhosIett[0]=='0')
		qtdeFilhos = 0;
	else
		qtdeFilhos = filhosIett.length;	
	
	//Manda todos os filhos fecharem	
	for(var indice = 0; indice < qtdeFilhos; indice++) {		
		var itemFilho = document.getElementById('iett' + filhosIett[indice] + orgaoSepararPorOrgao);
		var idItemFilho = itemFilho.getAttribute('id');								
		var codIettFechar = idItemFilho.substring(4,idItemFilho.length);
		
		if(codIettFechar.indexOf("_org") != -1)
			codIettFechar = codIettFechar.substring(0,codIettFechar.indexOf("_org"));
			
		fecharTudo(codIettFechar, orgaoSepararPorOrgao);
	}
}

/**
 * Abre os filhos de um item
 */	 
function abrirItem(codIett, orgaoSepararPorOrgao){
	var filhosIett = (document.getElementById('inputHiddenFilhos_iett' + codIett + orgaoSepararPorOrgao).value).split(':');
	var qtdeFilhos = filhosIett.length;		

	//Abre todos os filhos imediatos
	for(var indice = 0; indice < qtdeFilhos; indice++) {
		var itemFilho = document.getElementById('iett' + filhosIett[indice] + orgaoSepararPorOrgao);
		if(itemFilho.style.display == "none")
			itemFilho.style.display = "";		
		
		var iconeExpandir = 'iconeExpandirIett' + filhosIett[indice] + orgaoSepararPorOrgao;
		var newIconeExpandir = document.getElementById(iconeExpandir);					
		if(newIconeExpandir.innerHTML.indexOf('contrairExpandirArvore') != -1){
			//Colocar '+'(mais) no n� da �rvore								
			setarIconeContrairExpandir(imagemMais, filhosIett[indice], orgaoSepararPorOrgao, true);
		}						
	}	
	//Colocar '-'(menos) no n� da �rvore								
	setarIconeContrairExpandir(imagemMenos, codIett, orgaoSepararPorOrgao, true);	
}


/******************************
 ******FUN��ES AUXILIARES******
 ******************************
 */

/**
 * Retorna a posi��o de uma linha na tabela
 */	 
function posicaoNaTabela(codIett){	
	var newRow = document.getElementById('iett' + codIett + nomeOrgaoSepararPorOrgao);	
	return newRow.rowIndex;
}

/**
 * Retorna um array com os dados dos filhos(Iett)
 */	  
function populaArray(responseText){
	var separador = '*@*';
	return responseText.split(separador);
}

/**
 * Retorna 'menos' se o �cone da �rvore estiver expandido, 
 * ou 'mais' se estiver contra�do
 */	 
function getStatusIconeContrairExpandir(codIett, orgaoSepararPorOrgao){		 			                       
	var imgId = 'imagemMaisMenosIett' + codIett + orgaoSepararPorOrgao;
	var newImg = document.getElementById(imgId);	
	
	if(newImg.getAttribute('src').indexOf(imagemMais) != -1)
		return 'mais';
	else if(newImg.getAttribute('src').indexOf(imagemMenos) != -1)
		return 'menos';
	else
		return 'branco';
		 	
}

/**
 * 
 */	 
function setarIconeContrairExpandir(caminhoImagem, codIettPai, orgaoSepararPorOrgao, setarJavaScript){	
	var pathEcar = document.getElementById('pathEcar').value;
	var linkComeco = '';
	var linkFim = '';
	
	if(setarJavaScript == true){
		linkComeco = "<a href=\"javascript:contrairExpandirArvore('" + codIettPai + "','" + orgaoSepararPorOrgao + "')\">";
		linkFim = "</a>"; 
	}
	
	var idCell = 'iconeExpandirIett' + codIettPai + orgaoSepararPorOrgao;
	var newCellTemp = document.getElementById(idCell);
	 
	newCellTemp.innerHTML = linkComeco + '<img id="imagemMaisMenosIett' + codIettPai + orgaoSepararPorOrgao +'" src=\"' + pathEcar + caminhoImagem + '\" alt=\"\" border=\"0\">' + linkFim;
}
 