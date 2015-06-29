function validar(form){
	if(form.configSisExecFinanCsef.value == ""){
		alert("Sistema inválido");
		form.configSisExecFinanCsef.focus();
		return false;
	}
	
	var ano = parseInt(form.anoReferenciaEfier.value);
	if(isNumeric(ano) == false){
		alert("Ano inválido");
		form.anoReferenciaEfier.focus();
		return false;
	}
	else{
		if(ano <= 1950 || ano >= 2150){
			alert("Ano inválido");
			form.anoReferenciaEfier.focus();
			return false;
		}
	}
	
	if(validarConta(form) == false){
		alert("Conta inválida");
		return false;
	}
	
	return true;
}

function validarConta(form){

	/*Percorre todos os layouts e verifica se o mes/ano informado corresponde
	a algum layout... */

	var numLayouts = parseInt(form.totalLayouts.value);
	var mesInf = form.mesReferenciaEfier.value;
	var anoInf = form.anoReferenciaEfier.value;
	var sistema = form.configSisExecFinanCsef.value;
	var versaoUtilizada = "";
	
	if(mesInf != "" && anoInf != ""){
		var achouVersao = false;
		for(i = 1; i <= numLayouts; i++){
			var versao = eval("form.hidVer" + i + ".value");
			var v = versao.split("|");
			var codVer = v[0];
			if(document.getElementById('versao'+i).style.display != 'none'){
				//achou alguma versão a ser preenchida
				achouVersao = true;
				versaoUtilizada = codVer;
			}
		}
		
		if(achouVersao == false){
			return false;
		}
	}

	if(versaoUtilizada != ""){	
		var estruturas = eval("form.layout" + versaoUtilizada + ".value.split(\"|\");");
		
		
		for(e = 0; e < estruturas.length; e++){
			if(estruturas[e] != ""){
				if(eval("form.e" + estruturas[e] + versaoUtilizada + ".value") == ""){
					return false;
				}
			}
		}
	}
	else {
		return false;
	}
	
	return true;
}

function mostrarEsconder(form) {

	/*Percorre todos os layouts e verifica se o mes/ano informado corresponde
	a algum layout... */

	var numLayouts = parseInt(form.totalLayouts.value);
	var mesInf = form.mesReferenciaEfier.value;
	var anoInf = form.anoReferenciaEfier.value;
	var sistema = form.configSisExecFinanCsef.value;
	
	if(mesInf != "" && anoInf != ""){
		var achouVersao = false;
		for(i = 1; i <= numLayouts; i++){
			var versao = eval("form.hidVer" + i + ".value");
			var v = versao.split("|");
			var codVer = v[0];
			var mesAnoIni = parseInt(v[2] + v[1]); //200401
			var mesAnoFim = parseInt(v[4] + v[3]); //200512
			var codSist = v[5];
			
			var mesAno = parseInt(anoInf + mesInf);
			
			/*
			mesAnoFim >= mesAno não é válido no if pq o mesAnoFim é o mesAnoInicial da próxima versão.
			*/
			if(mesAnoIni <= mesAno && (mesAnoIni == mesAnoFim || mesAnoFim > mesAno) && sistema == codSist){
				document.getElementById('versao'+i).style.display='';
				form.codVersaoEscolhida.value = v[0];
				achouVersao = true;
				contaFocus(form, codVer);
			}
			else{
				document.getElementById('versao'+i).style.display='none';
			}
			
		}
		
		if(achouVersao == false){
			alert("Não existe um leiaute de versão para o Sistema/Mês/Ano informado.");
		}
	}
}

function pegarConta(form){

	/*Percorre todos os layouts e verifica se o mes/ano informado corresponde
	a algum layout... */

	var numLayouts = parseInt(form.totalLayouts.value);
	var mesInf = form.mesReferenciaEfier.value;
	var anoInf = form.anoReferenciaEfier.value;
	var sistema = form.configSisExecFinanCsef.value;
	var versaoUtilizada = "";
	var conta = "";
	
	if(mesInf != "" && anoInf != ""){
		var achouVersao = false;
		for(i = 1; i <= numLayouts; i++){
			var versao = eval("form.hidVer" + i + ".value");
			var v = versao.split("|");
			var codVer = v[0];
			if(document.getElementById('versao'+i).style.display != 'none'){
				//achou alguma versão a ser preenchida
				achouVersao = true;
				versaoUtilizada = codVer;
			}
		}
		
		if(achouVersao == false){
			return "";
		}
	}

	if(versaoUtilizada != ""){	
		var estruturas = eval("form.layout" + versaoUtilizada + ".value.split(\"|\");");
		
		
		for(e = 0; e < estruturas.length; e++){
			if(estruturas[e] != ""){
				conta = conta + eval("form.e" + estruturas[e] + versaoUtilizada + ".value") + " ";
			}
		}
	}
	else {
		return "";
	}
	conta = trim(conta);
	return conta;
}

function contaFocus(form, codVer){
	if(codVer != ""){	
		var estruturas = eval("form.layout" + codVer + ".value.split(\"|\");");
		
		for(e = 0; e < estruturas.length; e++){
			eval("form.e" + estruturas[e] + codVer + ".focus();");
			break;
		}
	}
}

function validaValor(obj){
	if (trim(obj.value) != '') {
		var x = obj.value;
		var y = '';
		var possuiDecimal = false;
		for(i = 0; i < x.length; i++){
			if(x.charAt(i) != '.'){
				y = y + x.charAt(i);
			}

			if(x.charAt(i) == ','){
				possuiDecimal = true;
			}
		}
		obj.value = y;
		
		if(possuiDecimal == true){
			if (!validaDecimal(obj.value)) {
				obj.focus();
				alert("Valor decimal inválido");
			}
		}
	}
}

/****************** Validação para a inserção de múltiplos registros *******************/

function validarMult(form){
	if(form.configSisExecFinanCsef.value == ""){
		alert("Sistema inválido");
		form.configSisExecFinanCsef.focus();
		return false;
	}
	
	var ano = parseInt(form.anoReferenciaEfier.value);
	if(isNumeric(ano) == false){
		alert("Ano inválido");
		form.anoReferenciaEfier.focus();
		return false;
	}
	else{
		if(ano <= 1950 || ano >= 2150){
			alert("Ano inválido");
			form.anoReferenciaEfier.focus();
			return false;
		}
	}
	
	if(validarContaMult(form) == false){
		alert("Conta inválida");
		return false;
	}

	
	return true;
}

function validarContaMult(form){

	/*Percorre todos os layouts e verifica se o mes/ano informado corresponde
	a algum layout... */

	var numLayouts = parseInt(form.totalLayouts.value);
	var mesInf = form.mesReferenciaEfier.value;
	var anoInf = form.anoReferenciaEfier.value;
	var sistema = form.configSisExecFinanCsef.value;
	var versaoUtilizada = "";
	
	if(mesInf != "" && anoInf != ""){
		var achouVersao = false;
		for(j = 1; j <= 10; j++){
			for(i = 1; i <= numLayouts; i++){
				var versao = eval("form.hidVer" + i + "[j-1].value");
				var v = versao.split("|");
				var codVer = v[0];
				if(document.getElementById('versao'+j+'_'+i).style.display != 'none'){
					//achou alguma versão a ser preenchida
					achouVersao = true;
					versaoUtilizada = codVer;
				}
			}
		}
		
		if(achouVersao == false){
			return false;
		}
	}

	/*if(versaoUtilizada != ""){	
		var estruturas = eval("form.layout" + versaoUtilizada + "[0].value.split(\"|\");");
		
		for(j = 1; j <= 10; j++){
			for(e = 0; e < estruturas.length; e++){
				if(estruturas[e] != ""){
					if(eval("form.e" + estruturas[e] + versaoUtilizada + "[j-1].value") == ""){
						return false;
					}
				}
			}
		}
	}
	else {
		return false;
	}*/
	
	
	if(versaoUtilizada == ""){	
		return false;
	}
	
	
	
	return true;
}

function mostrarEsconderMult(form) {

	/*Percorre todos os layouts e verifica se o mes/ano informado corresponde
	a algum layout... */

	var numLayouts = parseInt(form.totalLayouts.value);
	var mesInf = form.mesReferenciaEfier.value;
	var anoInf = form.anoReferenciaEfier.value;
	var sistema = form.configSisExecFinanCsef.value;
	
	if(mesInf != "" && anoInf != ""){
		var achouVersao = false;
		for(j = 1; j <= 10; j++){
			for(i = 1; i <= numLayouts; i++){
				var versao = eval("form.hidVer" + i + "[j-1].value");
				var v = versao.split("|");
				var codVer = v[0];
				var mesAnoIni = parseInt(v[2] + v[1]); //200401
				var mesAnoFim = parseInt(v[4] + v[3]); //200512
				var codSist = v[5];
				
				var mesAno = parseInt(anoInf + mesInf);
				
				/*
				mesAnoFim >= mesAno não é válido no if pq o mesAnoFim é o mesAnoInicial da próxima versão.
				*/
				if(mesAnoIni <= mesAno && (mesAnoIni == mesAnoFim || mesAnoFim > mesAno) && sistema == codSist){
					document.getElementById('versao'+j+'_'+i).style.display='';
					form.codVersaoEscolhida.value = v[0];
					achouVersao = true;
					contaFocusMult(form, codVer);
				}
				else{
					document.getElementById('versao'+j+'_'+i).style.display='none';
				}
				
			}
		}
		
		if(achouVersao == false){
			alert("Não existe um leiaute de versão para o Sistema/Mês/Ano informado.");
		}
	}
}

function pegarContaMult(form){

	/*Percorre todos os layouts e verifica se o mes/ano informado corresponde
	a algum layout... */

	var numLayouts = parseInt(form.totalLayouts.value);
	var mesInf = form.mesReferenciaEfier.value;
	var anoInf = form.anoReferenciaEfier.value;
	var sistema = form.configSisExecFinanCsef.value;
	var versaoUtilizada = "";
	var conta = "";
	
	if(mesInf != "" && anoInf != ""){
		var achouVersao = false;
		for(j = 1; j <= 10; j++){
			for(i = 1; i <= numLayouts; i++){
				var versao = eval("form.hidVer" + i + ".value");
				var v = versao.split("|");
				var codVer = v[0];
				if(document.getElementById('versao'+i).style.display != 'none'){
					//achou alguma versão a ser preenchida
					achouVersao = true;
					versaoUtilizada = codVer;
				}
			}
		}
		
		if(achouVersao == false){
			return "";
		}
	}

	if(versaoUtilizada != ""){	
		var estruturas = eval("form.layout" + versaoUtilizada + ".value.split(\"|\");");
		
		
		for(e = 0; e < estruturas.length; e++){
			if(estruturas[e] != ""){
				conta = conta + eval("form.e" + estruturas[e] + versaoUtilizada + ".value") + " ";
			}
		}
	}
	else {
		return "";
	}
	conta = trim(conta);
	return conta;
}

function contaFocusMult(form, codVer){
	if(codVer != ""){	
		var estruturas = eval("form.layout" + codVer + "[0].value.split(\"|\");");
		
		for(e = 0; e < estruturas.length; e++){
			eval("form.e" + estruturas[e] + codVer + "[0].focus();");
			break;
		}
	}
}

function validaValorMult(obj){
	if (trim(obj.value) != '') {
		var x = obj.value;
		var y = '';
		var possuiDecimal = false;
		for(i = 0; i < x.length; i++){
			if(x.charAt(i) != '.'){
				y = y + x.charAt(i);
			}

			if(x.charAt(i) == ','){
				possuiDecimal = true;
			}
		}
		obj.value = y;
		
		if(possuiDecimal == true){
			if (!validaDecimal(obj.value)) {
				obj.focus();
				alert("Valor decimal inválido");
			}
		}
	}
}



