<script language="javascript" type="text/javascript">
function validar(form){

	setHiddenVal('rte');
	form.richText.value = form.richText.value.replace(/>/g, "&gt;").replace(/\n/g, "");
	form.richText.value = form.richText.value.replace(/&gt;/g, ">");
	form.documentacaoAtb.value = form.richText.value;

	if(!validaString(form.nomeAtb, "Nome", true)){
		return false;
	}	
	if(!validaString(form.labelPadraoAtb, "Label Padrão", true)){
		return false;		
	}	
	
	if(form.funcaoFun.value == ""){
		alert("Obrigatória a seleção do campo Função da Estrutura");
		return false;
	}
	
	if(form.sisGrupoAtributoSga.disabled == false && form.sisGrupoAtributoSga.value == ""){
		alert("Obrigatória a seleção do campo Grupo de Atributos Livres");
		form.sisGrupoAtributoSga.focus();
		return(false);
	}
	
	return true;
}
</script>