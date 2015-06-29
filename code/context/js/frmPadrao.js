function aoClicarControlaFluxo(form,_hidAcao){
	form.hidAcao.value = _hidAcao;
	form.action = "ctrl.jsp";
	form.submit();
}

function aoClicarCancelar(form){
	form.reset();
	focoInicial(form);
}

function aoClicarVoltar(form) {
	form.hidAcao.value = "";
	form.action = "frm_nav.jsp";
	form.submit();
}

function aoClicarGravar(form){
	if(validarGravar(form)){
		form.hidAcao.value = "incluir";
		form.action = "ctrl.jsp";
		form.submit();
	}
}	

function aoClicarAlterar(form){
	if(validarAlterar(form)){
		form.hidAcao.value = "alterar";
		form.action = "ctrl.jsp";
		form.submit();
	}
}	

function aoClicarExcluir(form){
	if(!confirm("Confirma a exclusão?")){
		return(false);
	}
	form.hidAcao.value = "excluir";
	form.action = "ctrl.jsp";
	form.submit();
}	

function aoClicarPesquisar(form){
	if(validarPesquisar(form)){
		form.hidAcao.value = "pesquisar";
		form.action = "ctrl.jsp"
		form.submit();
	}
}	

function onLoad(form) {
	aoClicarCancelar(form);
}


function aoClicarPrimeiro(form){
	if (form.hidRegistro.value == 0){
		alert("Você já está no primeiro registro");
		return(false);
	}else{
		form.hidRegistro.value = 0;
	}
	form.submit();
}

function aoClicarUltimo(form){
	if (form.hidRegistro.value == (form.hidTotRegistro.value - 1)){
		alert("Você já está no último registro");
		return(false);
	}else{
		form.hidRegistro.value = (form.hidTotRegistro.value - 1);
	}
	form.submit();
}

function aoClicarAnterior(form){
	if (form.hidRegistro.value == 0){
		alert("Você já está no primeiro registro");		
		return(false);
	}else{
		form.hidRegistro.value = form.hidRegistro.value - 1;
	}
	form.submit();
}

function aoClicarProximo(form){
	if (form.hidRegistro.value == (form.hidTotRegistro.value - 1)){
		alert("Você já está no último registro");
		return(false);
	}else{
		var valor = form.hidRegistro.value;
		form.hidRegistro.value = parseInt(valor) + 0 + 1;
	}
	form.submit();
}

/*Funcoes para os botoes extras*/

/* este botao extra ficara convencionado como ir para a tela de alteracao
 * a partir da tela de navegacao
 */
function aoClicarBtn1(form){
	form.hidAcao.value = "";
	form.action = "frm_alt.jsp";
	form.submit();
}


/* este botao ficara convencionado para ir para a tela de navegacao a partir da
 * tela de alteracao
 */
function aoClicarBtn2(form){
	form.hidAcao.value = "";
	form.action = "frm_nav.jsp";
	form.submit();
}

function aoClicarBtn3(form){
}
