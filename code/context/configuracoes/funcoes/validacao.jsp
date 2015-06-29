<script language="javascript">
function validar(form){
	setHiddenVal('rte');
	form.richText.value = form.richText.value.replace(/>/g, "&gt;").replace(/\n/g, "");
	form.richText.value = form.richText.value.replace(/&gt;/g, ">");
	form.documentacaoFun.value = form.richText.value;

	if(!validaString(form.nomeFun, "Nome", true)){
		return(false);
	}
	if(!validaString(form.labelPadraoFun, "Label Padrão", true)){
		return(false);
	}
	if(!validaString(form.linkFuncaoFun, "Link", true)){
		return(false);
	}
	return(true);
}
</script>