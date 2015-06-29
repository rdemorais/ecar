function replaceAll(string, token, newtoken) {
	while (string.indexOf(token) != -1) {
 		string = string.replace(token, newtoken);
	}
	return string;
}


function valorRadioSelecionado(campoRadio){
	
	var valor = "";
	
	for (i=0;i < campoRadio.length;i++){
		
		if (campoRadio[i].checked == true){
			valor = campoRadio[i].value;
			break;
		}
	}
	
	return valor;
	
}

/**
 * Retorna apenas o nome do arquivo, de acordo com a string do caminho completo ou relativo
 * informado como parâmetro do método.<br>
 * 
 * @return String
 */
function getNomeArquivo(path) {
	var nome;
	
	if( path != null ) {
		if( path.lastIndexOf("/") != -1 ) { 
			nome = path.substring(path.lastIndexOf("/")+1);  // windows
		} else if( path.lastIndexOf("\\") != -1 ) {
			nome = path.substring(path.lastIndexOf("\\")+1); // linux
		} else {
			nome = path;
		}
	}
	
	return nome;
} 
