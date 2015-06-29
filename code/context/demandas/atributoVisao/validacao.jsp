<script language="javascript" type="text/javascript">
function validar(form){
	if(form.atributosDemandas.disabled == false && form.atributosDemandas.value == ""){
		alert("Obrigat�ria a sele��o do atributo de demanda");
		form.atributosDemandas.focus();
		return(false);
	}
	if(form.visoes.disabled == false && form.visoes.value == ""){
		alert("Obrigat�ria a sele��o da vis�o");
		form.atributosDemandas.focus();
		return(false);
	}	

	if(isBlank(form.seqApresListagemTelaAtbvis.value) && form.indListagemItensAtbvis.checked){ //? vazio
		alert("Seq��ncia da Lista de Demandas Inv�lida!");
		return false;
	}

	if(isBlank(form.seqApresTelaCampoAtbvis.value) && form.indExibivelAtbvis.checked){ //? vazio
		alert("Sequ�ncia no Formul�rio Inv�lida!");
		return false;
	}

	/*if(isBlank(form.seqApresListagemTelaAtbvis.value)){ //? vazio
		alert("Sequ�ncia na Lista de Demandas Inv�lida!");
		return false;
	}*/

	if(isBlank(form.larguraListagemTelaAtbvis.value) && form.indListagemItensAtbvis.checked){ //? vazio
		alert("Largura da Coluna na Lista de Demandas Inv�lida!");
		return false;
	}
	
	if(!isIntegerValid(form.seqApresTelaCampoAtbvis, "Seq��ncia em Tela Cadastramento")) {
		return false;
	}
	if(!isIntegerValid(form.seqApresListagemTelaAtbvis, "Seq��ncia na Lista de Demandas")) {
		return false;
	}
	if(!isIntegerValid(form.larguraListagemTelaAtbvis, "Largura na Lista de Demandas")) {
		return false;
	}

	if(form.indObrigatorioAtbvis.checked && !form.indExibivelAtbvis.checked && !form.indEditavelAtbvis.checked) {
		alert('N�o pode existir atributo obrigat�rio se n�o for edit�vel e exib�vel'); 
		form.indObrigatorioAtbvis.checked = false; 
		return false;
	}

	if(!form.indExibivelAtbvis.checked && form.indEditavelAtbvis.checked) {
		alert('N�o pode existir atributo edit�vel se n�o for exib�vel'); 
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