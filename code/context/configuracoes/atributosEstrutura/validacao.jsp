<script language="javascript" type="text/javascript">
function validar(form){

	setHiddenVal('rte');
	form.richText.value = form.richText.value.replace(/>/g, "&gt;").replace(/\n/g, "");
	form.richText.value = form.richText.value.replace(/&gt;/g, ">");
	form.documentacaoAtb.value = form.richText.value;

	if(!validaString(form.nomeAtb, "Nome", true)){
		return false;
	}	
	if(!validaString(form.labelPadraoAtb, "Label Padr�o", true)){
		return false;		
	}	
	
	if(form.funcaoFun.value == ""){
		alert("Obrigat�ria a sele��o do campo Fun��o da Estrutura");
		return false;
	}
	
	if(form.sisGrupoAtributoSga.disabled == false && form.sisGrupoAtributoSga.value == ""){
		alert("Obrigat�ria a sele��o do campo Grupo de Atributos Livres");
		form.sisGrupoAtributoSga.focus();
		return(false);
	}
	
	return true;
}
</script>