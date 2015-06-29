function validar(form){


	if (!validaString(form.textoApt,'Texto',true)){
		return(false);
	}

	return true;
}