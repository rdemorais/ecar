
function aoClicarCancelar(form){
	form.reset();
}

function aoClicarAlterar(form){
	if(validar(form)){
		form.btnAlterar[0].disabled = true;
		form.btnAlterar[1].disabled = true;
		form.hidAcao.value = "alterar";
		form.action = "ctrl.jsp";
		form.submit();
	}
}	

function aoClicarGravar(form){
	if(validar(form)){
		form.btnGravar[0].disabled = true;
		form.btnGravar[1].disabled = true;
		form.hidAcao.value = "incluir";
		form.action = "ctrl.jsp";
		form.submit();
	}
}	

function aoClicarBtn1(form){
	form.btn1[0].disabled = true;
	form.btn1[1].disabled = true;
	form.hidAcao.value = 'alterar';
	form.action = "frm_alt.jsp";
	form.submit();
}


function aoClicarIncluir(form){
	form.hidAcao.value = 'incluir';
	form.action = "frm_inc.jsp";
	form.submit();
}

function aoClicarExcluir(form){
	if(validarExclusao(form)){
		if(!confirm("Confirma a exclus�o?")){
			return(false);
		}
		form.hidAcao.value = "excluir";
		form.action = "ctrl.jsp";
		form.submit();	
	}
}	

function selectAll(form, nomeCheckBoxCtrl, nomeCheckBoxDep){

    if (typeof nomeCheckBoxCtrl == "undefined") nomeCheckBoxCtrl = "todos";
    if (typeof nomeCheckBoxDep == "undefined") nomeCheckBoxDep = "excluir";

	// Funcao para selecionar todos os Checkbox
	var numChecks = verificaChecks(form, nomeCheckBoxDep);
	
	if(numChecks > 1) {
		for(i = 0; i < eval('form.' + nomeCheckBoxDep + '.length'); i++)
			if(eval('form.' + nomeCheckBoxDep + '[i].disabled') == false) {
				eval('form.' + nomeCheckBoxDep + '[i]').checked = eval('form.' + nomeCheckBoxCtrl).checked;	
			}
	}
	
	if(numChecks == 1) {
		if(eval('form.' + nomeCheckBoxDep + '.disabled') == false) {
			eval('form.' + nomeCheckBoxDep).checked = eval('form.' + nomeCheckBoxCtrl).checked;	
		}
	}
}

function validarExclusao(form, nomeCheckBox){
	var algumMarcado = false;
	var numChecks = 0;

    if (typeof nomeCheckBox == "undefined") nomeCheckBox = "excluir";

    numChecks = verificaChecks(form, nomeCheckBox);
    if (numChecks > 0) {
		if(numChecks > 1){
			for(i = 0; i < eval('form.' + nomeCheckBox + '.length'); i++)
				if(eval('form.' + nomeCheckBox + '[i]').checked)
					algumMarcado = true;	
		} else {
			algumMarcado = eval('form.' + nomeCheckBox).checked;
		}
	}
	
	if(!algumMarcado){
		alert("Pelo menos um registro deve ser selecionado.");
		return false;
	} else {
		return true;
	}
}
/*
function aoClicarConsultar(form, codigo){
	form.codigo.value = codigo;
	form.hidAcao.value = 'alterar';
	document.form.action = "frm.jsp";
	document.form.submit();
} */

function aoClicarMonitoramento(form){

	if(document.form.indMonitoramentoIett.value == 'S') {
		if(document.form.configMailCfgmAtivar.value == 'S') {
			if(document.form.configMailCfgmAtivarObrigatorio.value == 'S' || (document.form.configMailCfgmAtivarObrigatorio.value == 'N' && confirm(document.form.msgAutorizaAtivar.value) == true )) {
				document.form.autorizarMail.value = 'S';
			} 
		}	
		if( confirm(document.form.msgAtivarItensFilho.value) == true){
			document.form.ativarRetirarMonitoramentoItensFilho.value = 'S';
		}
	} else {
		if(document.form.configMailCfgmRetirar.value == 'S') {
			if(document.form.configMailCfgmRetirarObrigatorio.value == 'S' || (document.form.configMailCfgmRetirarObrigatorio.value == 'N' &&  confirm(document.form.msgAutorizaRetirar.value) == true )) {
				document.form.autorizarMail.value = 'S';
			} 
		}
		if( confirm(document.form.msgRetirarItensFilho.value) == true){
			document.form.ativarRetirarMonitoramentoItensFilho.value = 'S';
		}
	}
	
//	if(validar(form)){
		form.hidAcao.value = "alterarMonitoramento";
		form.action = "ctrl.jsp";
		form.submit();
//	}
}

function aoClicarPlanejamento(form){
	// se ao clicar no bot�o o usu�rio est� bloqueando
	if(document.form.indBloqPlanejamentoIett.value == 'S') {
		if(document.form.configMailCfgmBloquear.value == 'S') {
			if(document.form.configMailCfgmBloquearObrigatorio.value == 'S' || (document.form.configMailCfgmBloquearObrigatorio.value == 'N' && confirm(document.form.msgAutorizaBloquear.value) == true )) {
				document.form.autorizarMail.value = 'S';
			} 
		}
		// se ele deseja bloquear todos os itens filhos.
		if( confirm(document.form.msgBloquearItensFilho.value) == true){
			document.form.bloquearDesbloquearPlanejamentoItensFilho.value = 'S';
		}
	} else {
		// se ao clicar no bot�o o usu�rio est� desbloqueando
		if(document.form.configMailCfgmDesbloquear.value == 'S') {
			if(document.form.configMailCfgmDesbloquearObrigatorio.value == 'S' || (document.form.configMailCfgmDesbloquearObrigatorio.value == 'N' &&  confirm(document.form.msgAutorizaDesbloquear.value) == true )) {
				document.form.autorizarMail.value = 'S';
			} 
		}
		// se ele deseja desbloquear todos os itens filhos.
		if( confirm(document.form.msgDesbloquearItensFilho.value) == true){
			document.form.bloquearDesbloquearPlanejamentoItensFilho.value = 'S';
		}
	}
//	if(validar(form)){
		form.hidAcao.value = "alterarPlanejamento";
		form.action = "ctrl.jsp";
		form.submit();
		
//	} else {
//		if(form.hidTelaAlteracao.value != "S"){
//			form.action = "frm_alt.jsp";
//			form.submit();
//		} else {
//			if (form.indBloqPlanejamentoIett.value == "S"){
//				form.indBloqPlanejamentoIett.value = "N";
//			} else {
//				form.indBloqPlanejamentoIett.value = "S";
//			}
//		}
//	}
}

function habilitaCampoID (id){
	var campo = document.getElementById(id);
	
	campo.readOnly = false;
	campo.focus();
	
}