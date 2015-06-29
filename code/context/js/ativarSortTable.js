var celulasAntigas = new Array();
var objetoAntigo,objetoAtual, objetoPrimeiraVez;


function ordenacaoColunasListener(){

	var tabelas = document.getElementsByTagName('table');
	var celulasAtuais = new Array();

	for (var i=0;i <tabelas.length;i++) {

		var table = tabelas[i];
		
		if (table.id == 'tabelaOrdenada') {
			objetoAntigo = table;
			
			for (var j=0; j<table.tBodies[0].rows.length; j++) {

		      text = sorttable.getInnerText(table.tBodies[0].rows[j].cells[2]);

		      if (celulasAntigas.length == 0 && objetoAtual == null) {
		    	  celulasAntigas = carregarCelulas(table,celulasAntigas);

				  if (objetoAtual != objetoAntigo) {
	    		  	   javascript:sorttable.makeSortable(table);
				  }
				  objetoAtual = objetoAntigo;
				  
				  break;
		      } else {
		    	  celulasAtuais = carregarCelulas(table,celulasAtuais);

		    	  if (!dadosAlterados(celulasAntigas,celulasAtuais) && objetoAtual!=table && objetoAtual != null){
		    	  
		    		  javascript:sorttable.makeSortable(table);
		    	  	  objetoAtual = table;
		    	  }
		    	  
		    	  if (dadosAlterados(celulasAntigas,celulasAtuais) && objetoAtual != null){

		    		  javascript:sorttable.makeSortable(table);
		    	      celulasAntigas = celulasAtuais;
		    	      objetoAtual = objetoAntigo;
		    	  } else {
			    	  if ((objetoAntigo != objetoAtual && objetoAtual != null)){

		    		  	javascript:sorttable.makeSortable(table);
					  	objetoAtual = table;
			    	  } 
		    	  } 
		       }
			}
		}
	}
}


function dadosAlterados(arrayAntigo,arrayAtual){

	var alterado = false;
	var encontrado;

	//Percorre o array inicial(coluna inicial) 
	for (var i=0; arrayAntigo!=null && i<arrayAntigo.length; i++) {
		encontrado = false;

		//Percorre o array atual(coluna atual) 		
		for (var j=0; j<arrayAtual.length; j++) {

			//Compara o array inicial com o atual, se encontrar o valor do antigo array no novo array.
			//seta a variavel encontrada para true e interrompe a busca 
			if (arrayAntigo[i] == arrayAtual[j]){
				encontrado = true;
				break;
			}
		}

		//se percorrer todo o novo array e não encontrar, é porque houve alteração então, seta o alterado para true e
		//aborta o loop, retornando o alterado como true. 
		if (!encontrado){
			alterado = true;
			break;
		}			
	}

	return alterado;
}

function carregarCelulas(table,array){

	for (var j=0; table.tBodies[0]!=null && j<table.tBodies[0].rows.length; j++) {
	      text = sorttable.getInnerText(table.tBodies[0].rows[j].cells[2]);
	      array[j] = text;
	}

	return array;
}