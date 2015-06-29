<script language="javascript" type="text/javascript">
function validar(form){
	if(form.atributosDemandas.disabled == false && form.atributosDemandas.value == ""){
		alert("Obrigatória a seleção do atributo de demanda");
		form.atributosDemandas.focus();
		return(false);
	}
	if(form.visoes.disabled == false && form.visoes.value == ""){
		alert("Obrigatória a seleção da visão");
		form.atributosDemandas.focus();
		return(false);
	}	

	if(isBlank(form.seqApresListagemTelaAtbvis.value) && form.indListagemItensAtbvis.checked){ //? vazio
		alert("Seqüência da Lista de Demandas Inválida!");
		return false;
	}

	if(isBlank(form.seqApresTelaCampoAtbvis.value) && form.indExibivelAtbvis.checked){ //? vazio
		alert("Sequência no Formulário Inválida!");
		return false;
	}

	/*if(isBlank(form.seqApresListagemTelaAtbvis.value)){ //? vazio
		alert("Sequência na Lista de Demandas Inválida!");
		return false;
	}*/

	if(isBlank(form.larguraListagemTelaAtbvis.value) && form.indListagemItensAtbvis.checked){ //? vazio
		alert("Largura da Coluna na Lista de Demandas Inválida!");
		return false;
	}
	
	if(!isIntegerValid(form.seqApresTelaCampoAtbvis, "Seqüência em Tela Cadastramento")) {
		return false;
	}
	if(!isIntegerValid(form.seqApresListagemTelaAtbvis, "Seqüência na Lista de Demandas")) {
		return false;
	}
	if(!isIntegerValid(form.larguraListagemTelaAtbvis, "Largura na Lista de Demandas")) {
		return false;
	}

	if(form.indObrigatorioAtbvis.checked && !form.indExibivelAtbvis.checked && !form.indEditavelAtbvis.checked) {
		alert('Não pode existir atributo obrigatório se não for editável e exibível'); 
		form.indObrigatorioAtbvis.checked = false; 
		return false;
	}

	if(!form.indExibivelAtbvis.checked && form.indEditavelAtbvis.checked) {
		alert('Não pode existir atributo editável se não for exibível'); 
		form.indEditavelAtbvis.checked = false;
		return false; 
	}

	return true;
}

function isIntegerValid(obj, nome){    	
	val = obj.value;	
	for(var i=0;i<val.length;i++){
		if(!isDigit(val.charAt(i))){
			alert("O campo " + nome + " precisa ser inteiro!");
			//obj.focus();
			globalvar = obj;			
			setTimeout("globalvar.focus()",250);
			return false;
		}
	}
	return true;
}
</script>