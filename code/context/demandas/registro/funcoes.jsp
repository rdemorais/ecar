
<script>
var contadorLocal = 0;
var contadorEntidade = 0;
var contadorEntidadeOrgao = 0;

//Essa variável serve para contabilizar o número de Locais Demandas exibidos na tela.
//Obs.: não foi utilizado a variável "contadorLocal", porque ela não representa, de fato, a quantidade exibida 
var contadorLocalReal = 0;
var contadorEntidadeReal = 0;
var contadorEntidadeOrgaoReal = 0; 

function adicionaLocal(codLit, ident) {
	if (!existeLocal(codLit)) {
		contadorLocal += 1;
		divconteudo = "<span id='local"+contadorLocal+"'>\n";
		divconteudo += "<input type=\"hidden\" id=\"idLit"+contadorLocal+"\" name=\"idLit"+contadorLocal+"\" value=\""+ ident +"\" <%=_disabled%> >\n";
		divconteudo += "<input type=\"hidden\" id=\"adicionaLocal"+ contadorLocal +"\" name=\"adicionaLocal"+ contadorLocal +"\" value=\"S\" <%=_disabled%> >\n";
		divconteudo += "<input type=\"hidden\" id=\"codLit" + contadorLocal + "\" name=\"codLit" + contadorLocal + "\" value=\""+ codLit +"\" <%=_disabled%> />\n";
		divconteudo += "<input type=\"checkbox\" id=\"chkLit" + contadorLocal + "\" name=\"chkLit" + contadorLocal + "\" value=\""+ codLit +"\" <%=_disabled%> /> &nbsp;";
		divconteudo += ident + "<br></span>\n";
		document.getElementById('local').innerHTML += divconteudo;
		document.form.contLocal.value = contadorLocal;		
		contadorLocalReal +=1;
		document.form.contadorLocalReal.value = contadorLocalReal;	
		return true;
	}
	return false;
}

function removeLocal() {
	var selecionados = 0;
	for (i = 1; i <= contadorLocal; i++){
		if (document.getElementById("adicionaLocal"+i).value == "S") {
			if (document.getElementById("chkLit"+i).checked == true) {
				selecionados++;
			}
		}
	}
		
	if (selecionados > 0) {
		if (confirm("Os locais selecionados serão removidos. Confirma?")) {
			for (i = 1; i <= contadorLocal; i++) {
				if (document.getElementById("adicionaLocal"+i).value == "S") {
					if (document.getElementById("chkLit"+i).checked == true) {
						document.getElementById("local"+i).innerHTML = "<input type=\"hidden\" id=\"adicionaLocal" + i +"\" name=\"adicionaLocal" + i +"\" value=\"N\">\n";						
						contadorLocalReal -=1;
					}
				}
			}
			document.form.contadorLocalReal.value = contadorLocalReal;
			document.form.contLocal.value = contadorLocal;						
			
			alert("Local removido.");
		}
	} else {
		alert("Pelo menos um local deve ser selecionado!");
	}
}

function existeLocal(codLit) {
	for (i = 1; i <= contadorLocal; i++) {
		if (document.getElementById("adicionaLocal"+i).value == "S") {		
			if (document.getElementById("codLit"+i).value == codLit) {
				alert("Local já informado!");
				return true;
			}
		}
	}
	return false;
}


function adicionaEntidade(codEnt, nome, adicionaCheck) {
	if (!existeEntidade(codEnt)) {
		contadorEntidade += 1;
		divconteudo = "<span id='entidade" + contadorEntidade + "'>\n";
		divconteudo += "<input type=\"hidden\" id=\"idEnt" + contadorEntidade + "\" name=\"idEnt" + contadorEntidade + "\" value=\"" + nome + "\" <%=_disabled%> >\n";
		divconteudo += "<input type=\"hidden\" id=\"adicionaEntidade" + contadorEntidade + "\" name=\"adicionaEntidade" + contadorEntidade + "\" value=\"S\" <%=_disabled%> >\n";
		divconteudo += "<input type=\"hidden\" id=\"codEnt" + contadorEntidade + "\" name=\"codEnt" + contadorEntidade + "\" value=\"" + codEnt + "\" <%=_disabled%> />\n";
		if (adicionaCheck != "false"){
			divconteudo += "<input type=\"checkbox\" id=\"chkEnt" + contadorEntidade + "\" name=\"chkEnt" + contadorEntidade + "\" value=\"" + codEnt + "\" <%=_disabled%> /> &nbsp;";
		}
		divconteudo += nome + "<br></span>\n";
		document.getElementById('entidade').innerHTML += divconteudo;
		document.form.contEntidade.value = contadorEntidade;		
		contadorEntidadeReal +=1;
		document.form.contadorEntidadeReal.value = contadorEntidadeReal;	
			
		return true;
	}
	return false;
}

function removeEntidade() {
	var selecionados = 0;
	for (i = 1; i <= contadorEntidade; i++){
		if (document.getElementById("adicionaEntidade"+i).value == "S") {
			if (document.getElementById("chkEnt"+i).checked == true) {
				selecionados++;
			}
		}
	}
		
	if (selecionados > 0) {
		if (confirm("As entidades selecionadas serão removidas. Confirma?")) {
			for (i = 1; i <= contadorEntidade; i++) {
				if (document.getElementById("adicionaEntidade"+i).value == "S") {
					if (document.getElementById("chkEnt"+i).checked == true) {
						document.getElementById("entidade"+i).innerHTML = "<input type=\"hidden\" id=\"adicionaEntidade" + i + "\" name=\"adicionaEntidade" + i + "\" value=\"N\">\n";
						contadorEntidadeReal -=1;
					}
				}
			}
			document.form.contadorEntidadeReal.value = contadorEntidadeReal;
			document.form.contEntidade.value = contadorEntidade;						

			alert("Entidade removida.");
		}
	} else {
		alert("Pelo menos uma entidade deve ser selecionada!");
	}
}

function existeEntidade(codEnt) {
	for (i = 1; i <= contadorEntidade; i++) {
		if (document.getElementById("adicionaEntidade"+i).value == "S") {		
			if (document.getElementById("codEnt"+i).value == codEnt) {
				alert("Entidade já informada!");
				return true;
			}
		}
	}
	return false;
}

function adicionaEntidadeOrgao(codEntOrg, nome, adicionaCheck) {
	
	if (!existeEntidadeOrgao(codEntOrg)) {
		
		contadorEntidadeOrgao += 1;
		divconteudo = "<span id='entidadeOrgao" + contadorEntidadeOrgao + "'>\n";
		divconteudo += "<input type=\"hidden\" id=\"idEntOrg" + contadorEntidadeOrgao + "\" name=\"idEntOrg" + contadorEntidadeOrgao + "\" value=\"" + nome + "\" <%=_disabled%> >\n";
		divconteudo += "<input type=\"hidden\" id=\"adicionaEntidadeOrgao" + contadorEntidadeOrgao + "\" name=\"adicionaEntidadeOrgao" + contadorEntidadeOrgao + "\" value=\"S\" <%=_disabled%> >\n";
		divconteudo += "<input type=\"hidden\" id=\"codEntOrg" + contadorEntidadeOrgao + "\" name=\"codEntOrg" + contadorEntidadeOrgao + "\" value=\"" + codEntOrg + "\" <%=_disabled%> />\n";
		if (adicionaCheck != "false"){
			divconteudo += "<input type=\"checkbox\" id=\"chkEntOrg" + contadorEntidadeOrgao + "\" name=\"chkEntOrg" + contadorEntidadeOrgao + "\" value=\"" + codEntOrg + "\" <%=_disabled%> /> &nbsp;";
		}
		divconteudo += nome + "<br></span>\n";
		document.getElementById('entidadeOrgao').innerHTML += divconteudo;
		document.form.contEntidadeOrgao.value = contadorEntidadeOrgao;		
		contadorEntidadeOrgaoReal +=1;
		document.form.contadorEntidadeOrgaoReal.value = contadorEntidadeOrgaoReal;		
		return true;
	}
	return false;
}

function removeEntidadeOrgao() {
	var selecionados = 0;
	for (i = 1; i <= contadorEntidadeOrgao; i++){
		if (document.getElementById("adicionaEntidadeOrgao"+i).value == "S") {
			if (document.getElementById("chkEntOrg"+i).checked == true) {
				selecionados++;
			}
		}
	}
		
	if (selecionados > 0) {
		if (confirm("Os orgãos selecionados serão removidos. Confirma?")) {
			for (i = 1; i <= contadorEntidadeOrgao; i++) {
				if (document.getElementById("adicionaEntidadeOrgao"+i).value == "S") {
					if (document.getElementById("chkEntOrg"+i).checked == true) {
						document.getElementById("entidadeOrgao"+i).innerHTML = "<input type=\"hidden\" id=\"adicionaEntidadeOrgao" + i + "\" name=\"adicionaEntidadeOrgao" + i + "\" value=\"N\">\n";
						contadorEntidadeOrgaoReal -=1;
					}
				}
			}
			document.form.contadorEntidadeOrgaoReal.value = contadorEntidadeOrgaoReal;
			document.form.contEntidadeOrgao.value = contadorEntidadeOrgao;						
			
			alert("Orgão removido.");
		}
	} else {
		alert("Pelo menos um orgão deve ser selecionado!");
	}
}

function existeEntidadeOrgao(codEntOrg) {
	
	for (i = 1; i <= contadorEntidadeOrgao; i++) {
		if (document.getElementById("adicionaEntidadeOrgao"+i).value == "S") {		
			if (document.getElementById("codEntOrg"+i).value == codEntOrg) {
				alert("Orgão já informado!");
				return true;
			}
		}
	}
	return false;
}

/* Funções para pesquisa de demandas relacionadas*/
function pesquisar(){
	popup_pesquisa('ecar.popup.PopUpRegDemanda', 'retornoDemanda');
}

function retornoDemanda(codigo, descricao){
	document.form.regDemandaRegd.value = codigo;
	document.form.demandaRelacionada.value = descricao;
	//alert(codigo + " - " + descricao);
}

function limpaCampos(){
	document.form.regDemandaRegd.value = '';
	document.form.demandaRelacionada.value = '';
}

</script>
