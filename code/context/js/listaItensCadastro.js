//OBS.:
//Esse arquivo javascript usa a função verificaValorSelecionado() do javascript: "../../js/validacoes.js"

/**
 * Variável global de controle do ajax.
 */ 
var permissaoAjax = true; 

/**
 * Variáveis globais que representam as figuras.
 */ 
var imagemMais = '/images/collapsed_button.gif';
var imagemMenos = '/images/expanded_button.gif';
var imagemQuadradoBranco = '/images/square.gif';
var barraCarregando = '/images/relAcomp/progress_bar.gif';

/**
 * Funcao Ajax - Solicitação - realiza chamada para o servlet que processará os filhos da árvore
 */	
function carregarFilhosAjax(idLinha){
	var hidFuncaoAjaxSelecionada = document.getElementById('hidFuncaoAjaxSelecionada').value;	
	var ehTelaListagem = 'N';
	if (document.getElementById('ehTelaListagem') != null){
		ehTelaListagem = document.getElementById('ehTelaListagem').value; 
	}
	
	var pars = 'hidFuncaoAjaxSelecionada=' + hidFuncaoAjaxSelecionada + '&idLinha=' + idLinha + '&radConcluido=' + verificaValorSelecionado(form, 'radConcluido') + '&ehTelaListagem=' + ehTelaListagem;
	
	var ultimoIdLinhaExpandido = document.getElementById('ultimoIdLinhaExpandido').value;
	
	if (hidFuncaoAjaxSelecionada=='expandirArvoreItens') {
		pars+=
		'&ultimoIdLinhaExpandido=' + idLinha;
	} else if (ultimoIdLinhaExpandido!=null) {
		pars+=
			'&ultimoIdLinhaExpandido=' + ultimoIdLinhaExpandido;
	}
	
	var url = _pathEcar + '/ajax/geraFilhosIettCadastro';
		
	if(permissaoAjax == true){
		//bloqueia as solicitações Ajax
		permissaoAjax = false;
		
		jQuery.ajax({
			type: "POST", 
			url: url, 
			data: pars, 
			success: function(msg){ 
			  			montaPaginaLocal(msg);	
					},
			error: function(xmlHttpRequest, typeError, exception){ 					
						//remove a linha carregando
						removeLinha('tabelaItens', 'msgCarregando' + idLinha);
						//Colocar quadrado em branco no nó da árvore								
						setarIconeContrairExpandir(imagemQuadradoBranco, idLinha, false);			
						//adiciona a linha					
						adicionaLinhaCarregando('tabelaItens' ,'Falha!', idLinha); 
					},
			beforeSend: function(){ 
				  			if(hidFuncaoAjaxSelecionada == 'expandirArvoreItens'){
				  				adicionaLinhaCarregandoBarra('tabelaItens','Carregando...', idLinha);
				  			}
				  			else if(hidFuncaoAjaxSelecionada == 'detalharArvoreItens'){
									document.getElementById("overlay").style.display="block";
									document.getElementById("aguarde").style.display="block";
				  			}  			
						}					
		});
	}

}

function replaceAll(str, de, para){
    var pos = str.indexOf(de);
    while (pos > -1){
		str = str.replace(de, para);
		pos = str.indexOf(de);
	}
    return (str);
}

/**
 * Funcao Ajax - Resposta - trata a resposta da chamada ajax
 */	 
function montaPaginaLocal(requisicaoOriginal){			
	
	requisicaoOriginal = replaceAll(requisicaoOriginal, "\r", " ");
	requisicaoOriginal = replaceAll(requisicaoOriginal, "\n", " ");
	
	var objetoJSON = eval('('+requisicaoOriginal+')');
				
	//Atualiza a visão de detalhamento de cada item da árvore.
	if(objetoJSON.indicaErro == 'false' && document.getElementById('hidFuncaoAjaxSelecionada').value == 'detalharArvoreItens'){
		
		//Oculta o aguarde
		document.getElementById("overlay").style.display="none";
		document.getElementById("aguarde").style.display="none";
				
		//Atualiza o DIV que guarda as abas da estrutura
		var divAbasEstrutura = document.getElementById(objetoJSON.tabelaAbasEstrutura.idInsercao);
		divAbasEstrutura.innerHTML = objetoJSON.tabelaAbasEstrutura.conteudo;
		//Atualiza o DIV que guarda o detalhamento dos itens da árvore
		var divDetalharArvore = document.getElementById(objetoJSON.tabelaDetalharArvoreItens.idInsercao);	
		divDetalharArvore.innerHTML = objetoJSON.tabelaDetalharArvoreItens.conteudo;
		
		//Atualiza o DIV que guarda a árvore de localização da página
		var divArvoreLocalizacao = document.getElementById(objetoJSON.tabelaArvoreLocalizacao.idInsercao);	
		divArvoreLocalizacao.innerHTML = objetoJSON.tabelaArvoreLocalizacao.conteudo;			
			
		//	Atualiza a exibição dos botões da página. Caso não exista botão visível,
		// é setado um título para aparecer de cabeçalho, isso acontece quando os 
		// itens mostrados são estruturas.  	 
		var botoesHtml = objetoJSON.botoesHtml;
		var nomeEstrutura = objetoJSON.nomeEstrutura;
		var barraBotoes = objetoJSON.barraBotoes;
		var existeBtnVisivel = false;
		var i;
		
		var trNomeEstrutura = document.getElementById(nomeEstrutura[0].id);		
		var valorNomeEstrutura = document.getElementById(nomeEstrutura[1].id);
		var trBarraBotoes = document.getElementById(barraBotoes[0].id);
		
		trNomeEstrutura.style.backgroundColor = nomeEstrutura[0].bgColor;
		valorNomeEstrutura.innerHTML = nomeEstrutura[1].nomeEtt;
		trBarraBotoes.style.backgroundColor = barraBotoes[0].bgColor;
		
		for(i=0; i<botoesHtml.length; i++){
			var botao = document.getElementById(botoesHtml[i].id);
			botao.style.display = botoesHtml[i].style;
			var divBotao = document.getElementById(botoesHtml[i].tipo);
			divBotao.value = botoesHtml[i].parametro;
			if (botoesHtml[i].tipo == 'parametroIncluir'){
				botao.onclick = function(){
									aoClicarIncluirItem(document.form, document.getElementById('parametroIncluir').value);
								};  
			} else if (botoesHtml[i].tipo == 'parametroExcluir'){
				botao.onclick = function(){
									aoClicarExcluirItem(document.form, document.getElementById('parametroExcluir').value);
								};  
			} else if (botoesHtml[i].tipo == 'parametroImprimir'){
				botao.onclick = function(){
									parametros = document.getElementById('parametroImprimir').value;
									parametros = parametros.split('|');
									aoClicarImprimirItem(document.form, parametros[0], parametros[1]);
								};  
			} else if (botoesHtml[i].tipo == 'parametroGerarArquivos'){
				botao.onclick = function(){
									gerarArquivos();
								};  
			} else if (botoesHtml[i].tipo == 'parametroAssociarItem'){
				botao.onclick = function(){
									abrirPopup();
								};  
			} else if (botoesHtml[i].tipo == 'parametroDesassociarItem'){
				botao.onclick = function(){
									aoClicarDesassociarItem(document.form);
								};  
			} 
			
			if(botoesHtml[i].style == ""){
				existeBtnVisivel = true;
			}
		}
//		var cabecalho = document.getElementById("tituloCabecalho");
//		if(existeBtnVisivel == false){			
//			cabecalho.style.display = '';
//			//cabecalho.setAttribute('style','');
//		} else{
//			cabecalho.style.display = 'none'; 
//			//cabecalho.setAttribute('style','display:none');
//		}
		
	} 
	
	//Atualiza as linhas da tabela que representa os itens da árvore da página de cadastro
	else if(objetoJSON.indicaErro == 'false' && document.getElementById('hidFuncaoAjaxSelecionada').value == 'expandirArvoreItens'){		
		
		var tblBody = document.getElementById(objetoJSON.idTabela).tBodies[0];
		var idLinhaPai = objetoJSON.idLinhaPai;
		var linhas = objetoJSON.linhas;
		var filhosExibir = '';
		
		//remove a linha carregando
		removeLinha(objetoJSON.idTabela, 'msgCarregando' + idLinhaPai);
		
		var linhaPai = document.getElementById(idLinhaPai);	
	 	//onde os filhos serão inseridos
	 	var posicaoDeInsercao = linhaPai.rowIndex + 1;		
		
		//se tem filhos
		if(linhas.length>0){
			
			//Colocar '-'(menos) no nó da árvore								
			setarIconeContrairExpandir(imagemMenos, idLinhaPai, true);
		
			var i;
			for(i=0; i<linhas.length; i++){
	
				//cria uma nova linha na tabela
				var novaLinha = tblBody.insertRow(posicaoDeInsercao);			 
				novaLinha.setAttribute('id',linhas[i].parametros.id);									
				novaLinha.setAttribute('onMouseOver',linhas[i].parametros.onMouseOver);
				novaLinha.setAttribute('onMouseOut', linhas[i].parametros.onMouseOut);
				novaLinha.setAttribute('bgColor', linhas[i].parametros.bgColor);
				novaLinha.setAttribute('class','cor_sim');			
				//novaLinha.setAttribute('class', linhas[i].parametros.class); //Não pega no IE
				
				var novaColuna = novaLinha.insertCell(0);
				novaColuna.innerHTML = linhas[i].coluna;
				
				//Recupera o div com os filhos.
				//Cria um inputHidden com a quantidade de filhos que pode ser exibida inicialmente para este nó.
				//Como inicialmente não sabemos a quantidade de filhos deste nó, colocamos '0' 			
				var hiddenFilhosExibir = document.getElementById('hiddenFilhosExibir');
				hiddenFilhosExibir.innerHTML += '<input type="hidden" id="inputHiddenFilhos_' + linhas[i].parametros.id + '" value="0">';
				
				if(filhosExibir != ""){
					filhosExibir += ':' + linhas[i].parametros.id;
				}else{
					filhosExibir = linhas[i].parametros.id;
				}			 				
								
				posicaoDeInsercao++;
			}	

			//Seta os filhos que o nó pai pode exibir no formato: "idLinhaFilho1:idLinhaFilho2:idLinhaFilho3" 		
			var inputHiddenFilhos = document.getElementById('inputHiddenFilhos_' + idLinhaPai);
			inputHiddenFilhos.setAttribute('value', filhosExibir);						
		}
		
		//se não tem filhos
		else{			
			//Colocar quadrado em branco no nó da árvore								
			setarIconeContrairExpandir(imagemQuadradoBranco, idLinhaPai, false);			
		}				
	}

	//Em caso de erro no processamento
	if(objetoJSON.indicaErro == 'true'){		
		if(objetoJSON.funcaoAjaxSelecionada == 'expandirArvoreItens'){
			var idLinhaPai = objetoJSON.idLinhaPai;
			//remove a linha carregando
			removeLinha('tabelaItens', 'msgCarregando' + idLinhaPai);
			//Colocar quadrado em branco no nó da árvore								
			setarIconeContrairExpandir(imagemQuadradoBranco, idLinhaPai, false);			
			//adiciona a linha					
			adicionaLinhaCarregando('tabelaItens' ,'Falha!', idLinhaPai); 		
		}		
		else if(objetoJSON.funcaoAjaxSelecionada == 'detalharArvoreItens'){
			//Oculta o aguarde
			document.getElementById("overlay").style.display="none";
			document.getElementById("aguarde").style.display="none";
			
			//Atualiza o DIV que guarda o detalhamento dos itens da árvore
			//com uma mensagem de erro
			var divDetalharArvore = document.getElementById(objetoJSON.idInsercao);	
			divDetalharArvore.innerHTML = objetoJSON.conteudo;
		}
	}
	
	//Libera as chamadas Ajax
	permissaoAjax = true;
}

//********************FUNCOES AUXILIARES********************************
/**
 * Adiciona uma linha na tabela com a mensagem passada.
 * O local, onde será inserida a linha, será definido pelo "idLinha"
 */	  
function adicionaLinhaCarregando(tableId, message, idLinha){
	var table = document.getElementById(tableId)
	var tblBody = table.tBodies[0];			
	var idRowPai = idLinha;
	var posicaoDeInsercao = posicaoNaTabela(idLinha) +  1;	
	var rowPai = document.getElementById(idRowPai);
	var newRow = tblBody.insertRow(posicaoDeInsercao);
	var newCell;
	 				
	newRow.setAttribute('id','msgCarregando' + idLinha);
	newRow.setAttribute('class','cor_sim');
	newRow.setAttribute('bgColor', rowPai.getAttribute('bgColor'));	
	
	newCell = newRow.insertCell(0);
	newCell.setAttribute('align','center');
	newCell.innerHTML = message;
		
}

/**
 *Semelhante a 'adicionaLinhaCarregando' só que acrescenta a barra de carregamento
 */	  
function adicionaLinhaCarregandoBarra(tableId, message, idLinha){
	var pathEcar = _pathEcar;
	var table = document.getElementById(tableId)
	var tblBody = table.tBodies[0];	
				
	var posicaoDeInsercao = posicaoNaTabela(idLinha) +  1;	
	var rowPai = document.getElementById(idLinha);
	var newRow = tblBody.insertRow(posicaoDeInsercao);
	var newCell;
	 				
	newRow.setAttribute('id','msgCarregando' + idLinha);
	newRow.setAttribute('class','cor_sim');
	newRow.setAttribute('bgColor', rowPai.getAttribute('bgColor'));	
	
	newCell = newRow.insertCell(0);
	newCell.setAttribute('align','center');
	newCell.innerHTML = "<img src='" + pathEcar + barraCarregando + "' >" +
						"<br>&nbsp&nbsp&nbsp&nbsp&nbsp " + message;
}

/**
 * Retorna a posição de uma linha na tabela
 */	 
function posicaoNaTabela(id){	
	var newRow = document.getElementById(id);	
	return newRow.rowIndex;
}

/**
 * Remove a linha especificada da tabela
 */	 
function removeLinha(tableId, idLinha){
	var newRow = document.getElementById(idLinha);			
	var tblBody = document.getElementById(tableId).tBodies[0];
	
	tblBody.deleteRow(newRow.rowIndex);		
}


//******************FUNCOES DE CONTROLE DO JAVASCRIPT DA ARVORE*****************

/**
 * Função para expandir e contrair a árvore de filhos usando javascript
 */	 
function contrairExpandirArvore(idLinha){
	//Verifica se o menu está contraído ou expandido
	if(getStatusIconeContrairExpandir(idLinha) == 'mais')
		abrirItem(idLinha);
	else
		fecharItem(idLinha);		
}

/**
 * Abre os filhos de um item
 */	 
function abrirItem(idLinha){
	var filhosIett = recuperaFilhosImediatos(idLinha);
	var qtdeFilhos;		

	//Seta a quantidade de filhos de um Item. 
	if(filhosIett.length==1 && filhosIett[0]=='0')
		qtdeFilhos = 0;
	else
		qtdeFilhos = filhosIett.length;		

	//Abre todos os filhos imediatos
	for(var indice = 0; indice < qtdeFilhos; indice++) {
		var itemFilho = document.getElementById(filhosIett[indice]);
		if(itemFilho.style.display == "none")
			itemFilho.style.display = "";		
		
		var iconeExpandir = 'iconeExpandir' + filhosIett[indice];
		var newIconeExpandir = document.getElementById(iconeExpandir);					
		if(newIconeExpandir.innerHTML.indexOf('contrairExpandirArvore') != -1){
			//Colocar '+'(mais) no nó da árvore								
			setarIconeContrairExpandir(imagemMais, filhosIett[indice], true);
		}						
	}	
	//Colocar '-'(menos) no nó da árvore								
	setarIconeContrairExpandir(imagemMenos, idLinha, true);	
}

/**
 * Fecha todos os filhos de um item da tabela "tabelaItens"
 */	 
function fecharItem(idLinha){
	
	var filhos = recuperaFilhosImediatos(idLinha);	
	var qtdeFilhos;
	
	//Seta a quantidade de filhos de um Item. 
	if(filhos.length==1 && filhos[0]=='0')
		qtdeFilhos = 0;
	else if (filhos.length==1 && filhos[0]=='')
		qtdeFilhos = 0;
	else	
		qtdeFilhos = filhos.length;		
	
	//Manda todos os filhos fecharem
	var indice=0;
	for(var indice = 0; indice < qtdeFilhos; indice++) {			
		var idLinhaFilho = filhos[indice];						
		
		fecharTudo(idLinhaFilho);
						
	}
	//Colocar '+'(mais) no nó da árvore								
	setarIconeContrairExpandir(imagemMais, idLinha, true);	
}

/**
 * Recupera os filhos imediatos de um item na tabela "tabelaItens".<br> 
 * Cada linha(<tr>) possue um hidden que guarda os seus filhos imediatos.
 * Caso o item não possua filhos, retorna um array com apenas um elemento, 
 * de valor "0".
 */	 
 function recuperaFilhosImediatos(idLinhaPai){
	var retorno = (document.getElementById('inputHiddenFilhos_' + idLinhaPai).value).split(':');
	
	return retorno;
}

/**
 * Fecha o item e todos os seus filhos
 */	 
function fecharTudo(idLinha){
	
	// Oculta o item, setando seu style para "display:none". 
	var idRowItem = idLinha;
	var rowItem = document.getElementById(idRowItem);
	//Obs.: setAttribute('style','display:none') não funciona bem no IE
	rowItem.style.display = 'none';
	//Define um array de filhos
	var filhosIett = recuperaFilhosImediatos(idLinha);
	var qtdeFilhos;
	
	//Seta a quantidade de filhos de um Item. 
	if(filhosIett.length==1 && filhosIett[0]=='0')
		qtdeFilhos = 0;
	else if(filhosIett.length==1 && filhosIett[0]=='')
		qtdeFilhos = 0;
	else
		qtdeFilhos = filhosIett.length;	
	
	//Manda todos os filhos fecharem	
	for(var indice = 0; indice < qtdeFilhos; indice++) {		
		if( document.getElementById(filhosIett[indice]).style.display == ''){
			fecharTudo(filhosIett[indice]);
		}
	}
}

/**
 * Retorna 'menos' se o ícone da árvore estiver expandido, 
 * ou 'mais' se estiver contraído, ou branco
 */	 
function getStatusIconeContrairExpandir(idLinha){		 			                       
	var imgId = 'imagemMaisMenos' + idLinha;
	var newImg = document.getElementById(imgId);	
	
	if(newImg.getAttribute('src').indexOf(imagemMais) != -1)
		return 'mais';
	else if(newImg.getAttribute('src').indexOf(imagemMenos) != -1)
		return 'menos';
	else
		return 'branco';
		 	
}

/**
 * Método que modifica o ícone contrair/expandir de cada item da árvore
 */	 
function setarIconeContrairExpandir(caminhoImagem, idLinha, setarJavaScript){	
	var pathEcar = _pathEcar;
	var linkComeco = '';
	var linkFim = '';
	
	if(setarJavaScript == true){
		linkComeco = "<a href=\"javascript:contrairExpandirArvore('" + idLinha + "')\">";
		linkFim = "</a>"; 
	}
	
	var idCell = 'iconeExpandir' + idLinha;
	var newCellTemp = document.getElementById(idCell);
	 
	newCellTemp.innerHTML = linkComeco + '<img id="imagemMaisMenos' + idLinha +'" src=\"' + pathEcar + caminhoImagem + '\" alt=\"\" border=\"0\">' + linkFim;
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
 
