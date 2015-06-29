function onLoad(form) {
	form.reset();
	focoInicial(form);
}

function verificaChecks(form, nome) {
	var el = document.getElementsByTagName("INPUT")
	var i = 0, n = 0;

	while (i < el.length) {								
		if (el.item(i).type == "radio" && el.item(i).name == nome)
			n++;
		i++;
	}
	if(n==0){
		i=0;
		while (i < el.length) {								
			if (el.item(i).type == "checkbox" && el.item(i).name == nome)
				n++;
			i++;
		}
	}
	return n; 
}


function aoClicarAdicionar(form, funcaoGetDadosPesquisa){
	var selecionado = false;
    if (typeof funcaoGetDadosPesquisa == "undefined") funcaoGetDadosPesquisa = "getDadosPesquisa";

	if (verificaChecks(form, "hidOpcoes") > 1) {
		for (i = 0; i < form.hidOpcoes.length; i++) {
			if (form.hidOpcoes[i].checked == true) {
				//chama a funcao definida pelo programador para retornar os dados da pesquisa
/*				alert(eval('window.opener.' + funcaoGetDadosPesquisa));
				alert('hidOpcoes[i]: '+(form.hidOpcoes[i].value));
				alert(eval('form.hidAux' + form.hidOpcoes[i].value + '.value'));
	*/			eval('window.opener.' + funcaoGetDadosPesquisa)(form.hidOpcoes[i].value, eval('form.hidAux' + form.hidOpcoes[i].value + '.value'));
				selecionado = true;
			//	break;
			}
		}
	} else {
		if (form.hidOpcoes.checked == true) {
			eval('window.opener.' + funcaoGetDadosPesquisa)(form.hidOpcoes.value, eval('form.hidAux' + form.hidOpcoes.value + '.value'));
			selecionado = true;
		}
	}
	if (selecionado)
		window.close();
	else
		alert('Selecione uma opção!');
}

function aoClicarPesquisar(form){
	document.form.action="popup_pesquisa.jsp?hidAcao=pesquisar";
	document.form.submit();
}

function aoClicarPesquisarCheck(form){
	document.form.action="popup_pesquisa_check.jsp?hidAcao=pesquisar";
	document.form.submit();
}

function aoClicarPesquisarPai(form){
	document.form.action="popup_pesquisa_pai.jsp?hidAcao=pesquisar";
	document.form.submit();
}

function aoClicarPesquisarFilho(form){
	document.form.action="popup_pesquisa_filho.jsp?hidAcao=pesquisar";
	document.form.submit();
}

function aoClicarCancelar(form){
	window.close();
}


function aoClicarPrimeiro(form){
	if (form.hidNumPagina.value == 0){
		alert("Você já está na primeira página");
		return(false);
	}else{
		form.hidNumPagina.value = 0;
	}
	form.submit();
}

function aoClicarUltimo(form){
	if (form.hidNumPagina.value == (form.hidTotPagina.value - 1)){
		alert("Você já está na última página");
		return(false);
	}else{
		form.hidNumPagina.value = form.hidTotPagina.value - 1;
	}
	form.submit();
}

function aoClicarAnterior(form){
	if (form.hidNumPagina.value == 0){
		alert("Você já está na primeira página");		
		return(false);
	}else{
		form.hidNumPagina.value = form.hidNumPagina.value - 1;
	}
	form.submit();
}

function aoClicarProximo(form){
	if (form.hidNumPagina.value == (form.hidTotPagina.value - 1)){
		alert("Você já está na última página");
		return(false);
	}else{
		form.hidNumPagina.value = parseInt(form.hidNumPagina.value) + 1;
	}
	form.submit();
}
