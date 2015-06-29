<script language="javascript" type="text/javascript">
function validar(form){

	setHiddenVal('rte');
	form.richText.value = form.richText.value.replace(/>/g, "&gt;").replace(/\n/g, "");
	form.richText.value = form.richText.value.replace(/&gt;/g, ">");
	form.documentacaoAtbdem.value = form.richText.value;

	if(!validaString(form.nomeAtbdem, "Nome", true)){
		return false;
	}	
	if(!validaString(form.labelPadraoAtbdem, "Label Padrão", true)){
		return false;		
	}
	if(form.sisGrupoAtributoSga.disabled == false && form.sisGrupoAtributoSga.value == ""){
		alert("Obrigatória a seleção do campo Grupo de Atributos Livres");
		form.sisGrupoAtributoSga.focus();
		return(false);
	}	
	if(!isIntegerValid(form.tamanhoConteudoAtbdem, "Tamanho do Conteúdo")) {
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