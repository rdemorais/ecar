/**  Verifica se o campo digitado ? num?rico, somente inteiros

    @param campo - nome do objeto input
           label - nome do campo para exibir no alert
           exibirMsg true - exibir a mensagem da fun??o
    @return true - um valor num?rico v?lido
            false - um valor inv?lido
*/
	function validaNum(campo, label, exibirMsg)
	{
		var checkStr = campo.value;
		var checkOK = "0123456789";  
		var allValid = true;
		
		if(validaString(campo, label, exibirMsg)){

				for (i = 0;  i < checkStr.length;  i++)
				{
					ch = checkStr.charAt(i);
					for (j = 0;  j < checkOK.length;  j++)
					if (ch == checkOK.charAt(j))
						break;
					if (j == checkOK.length)
					{
						allValid = false;
						break;
					}
				}
				if (!allValid)
				{
					if (exibirMsg)
						alert("O campo \""+label+"\" deve ser numérico!");
		
					campo.focus();
					campo.value='';
					
					return(false);
				}
		
		} else {
					return (false);
		}
		
		return(true);
	}
	
	
	
/***********************************************************************/
/* Funcao para validar numeros com casas decimais       */
/***********************************************************************/
function validaDecimal(numero){
	var pos = "";
	var ok = "sim";
	var virg = 0;
	var valido = "0123456789,";

	for(var i=0; i<numero.length; i++){
		pos = numero.substring(i, i+1);

		if(valido.indexOf(pos) == "-1"){
			return(false);
		}
		if(pos == ","){
			virg++;
			if((virg > 1)||(i == 0)||(i == numero.length-1)){
				return(false);
			}
		}
    } 
    
    return(true);
}

	
/***********************************************************************/
/* Funcao para aceitar somente numeros ao digitar				       */
/***********************************************************************/
function digitaNumero(campo, e) {
	var cod="";
	if(document.all) {cod=event.keyCode;} else {cod=e.which;} 
	if(cod == 8 || cod == 0) return; //backspace or tab
	if ((cod < 48 || cod > 57) && cod != 47){
		cod=0;
		campo.focus(); return false;
	}
}