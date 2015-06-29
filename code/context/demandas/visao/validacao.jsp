<script language="javascript" type="text/javascript">
function validar(form){

	if(!validaString(form.descricaoVisao, "Nome", true)){
		return false;
	}		
	return true;
}

function validarAlterar(form){
	return validar(form);
}

</script>