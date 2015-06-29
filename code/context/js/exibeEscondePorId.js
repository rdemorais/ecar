	
	/* Exibe bloco dentro do elemento cujo id é passado como paramêtro 
	* Ex: <span id="bloco" style="display:" > esse testo sera escondido </span>
	*/
	function exibeEsconde (identificador){
		elemento = document.getElementById(identificador);

		if (elemento==undefined) {
			alert ('id nao definido para o elemento que se deseja esconder');
			return ;
		}
		
		if (elemento.style.display == "none" ){ 
			elemento.style.display = "inline"; //exibe
		} else {
			elemento.style.display = "none"; //esconde
		}
	}
