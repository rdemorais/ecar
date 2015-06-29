
<script>

var contadorEntidade = 0;

function existeEntidade(codLit)
{
	var el = document.getElementsByTagName("INPUT");
	var i = 0;
	while (i < el.length)
	{		
		if(el.item(i) != null){
			if (el.item(i).type == "checkbox" && el.item(i).value == codLit){
				alert("Entidade já adicionada!");
				return true;
			}
		}
		i++;
	}
	
	if(document.getElementById("codEnt").value == codLit)
	{
		alert("A entidade de referência não pode ser adicionada como pai ou filho.");
		return true;	
	}
	
	return false;
}

function adicionaPai(codLit, ident) {
	if(!existeEntidade(codLit))
	{
		contadorEntidade += 1;
		divconteudo = "<table id='pai"+contadorEntidade+"'>\n";
		divconteudo += "<input type=\"hidden\" id=\"idLit"+contadorEntidade+"\" name=\"idLit"+contadorEntidade+"\" value=\""+ ident +"\" <%=_disabled%> >\n";
		divconteudo += "<input type=\"hidden\" id=\"adicionaEntidade"+ contadorEntidade +"\" name=\"adicionaEntidade"+ contadorEntidade +"\" value=\"S\" <%=_disabled%> >\n";
		divconteudo += "<input type=\"hidden\" id=\"codLit" + contadorEntidade + "\" name=\"codLit" + contadorEntidade + "\" value=\""+ codLit +"\" <%=_disabled%> />\n";
		divconteudo += "\t<tr>\n";
		divconteudo += "\t\t<td class=\"labelNormal\">&nbsp;</td>\n";
		divconteudo += "\t\t<td><input type=\"checkbox\" class=\"form_check_radio\" id=\"chkLit" + contadorEntidade + "\" name=\"entidadeEntPai" + "\" value=\""+ codLit +"\" <%=_disabled%> checked/> &nbsp;";
		divconteudo += ident + "</td>\n";
		divconteudo += "\t</tr>\n";
		divconteudo += "</table>\n";
		document.getElementById('pai').innerHTML += divconteudo;
		document.form.contEntidade.value = contadorEntidade;
		return true;
	}
	return false;
}

function adicionaFilho(codLit, ident) {
	if(!existeEntidade(codLit))
	{
		contadorEntidade += 1;
		divconteudo = "<table id='filho"+contadorEntidade+"'>\n";
		divconteudo += "<input type=\"hidden\" id=\"idLit"+contadorEntidade+"\" name=\"idLit"+contadorEntidade+"\" value=\""+ ident +"\" <%=_disabled%> >\n";
		divconteudo += "<input type=\"hidden\" id=\"adicionaEntidade"+ contadorEntidade +"\" name=\"adicionaEntidade"+ contadorEntidade +"\" value=\"S\" <%=_disabled%> >\n";
		divconteudo += "<input type=\"hidden\" id=\"codLit" + contadorEntidade + "\" name=\"codLit" + contadorEntidade + "\" value=\""+ codLit +"\" <%=_disabled%> />\n";
		divconteudo += "\t<tr>\n";
		divconteudo += "\t\t<td class=\"labelNormal\">&nbsp;</td>\n";
		divconteudo += "\t\t<td><input type=\"checkbox\" class=\"form_check_radio\" id=\"chkLit" + contadorEntidade + "\" name=\"entidadeEntFilho" + "\" value=\""+ codLit +"\" <%=_disabled%>  checked/> &nbsp;";
		divconteudo += ident + "</td>\n";
		divconteudo += "\t</tr>\n";
		divconteudo += "</table>\n";
		document.getElementById('filho').innerHTML += divconteudo;
		document.form.contEntidade.value = contadorEntidade;
		return true;
	}
	return false;
}

function removeEntidadePai() {
	var selecionados = 0;
	var el = document.getElementsByTagName("INPUT");
	var i = 0;
	while (i < el.length)
	{		
		if(el.item(i) != null){
			if (el.item(i).type == "checkbox" && el.item(i).name == "entidadeEntPai" && el.item(i).checked == true)
				selecionados++;			
		}
		i++;
	}
		
	if (selecionados > 0) {
		if (confirm("As entidades pai selecionadas serão removidas. Confirma?")) {
			for (i = 1; i <= contadorEntidade; i++) {
				//if (document.getElementById("adicionaEntidade"+i).value == "S") {
					if (document.getElementById("chkLit"+i).checked == true)
						document.getElementById("pai"+i).innerHTML = "<input type=\"hidden\" id=\"adicionaEntidade" + i +"\" name=\"adicionaEntidade" + i +"\" value=\"N\">\n";
				//}
			}
			alert("Entidade(s) pai(s) removida(s).");
		}
	} else {
		alert("Pelo menos uma entidade pai deve ser selecionada");
	}
	
}

function removeEntidadeFilho() {
	var selecionados = 0;
	for (i = 1; i <= contadorEntidade; i++){
		if (document.getElementById("adicionaEntidade"+i).value == "S") {
			if (document.getElementById("chkLit"+i).checked == true) {
				selecionados++;
			}
		}
	}
		
	if (selecionados > 0) {
		if (confirm("As entidades filhos selecionadas serão removidas. Confirma?")) {
			for (i = 1; i <= contadorEntidade; i++) {
				if (document.getElementById("adicionaEntidade"+i).value == "S") {
					if (document.getElementById("chkLit"+i).checked == true)
						document.getElementById("filho"+i).innerHTML = "<input type=\"hidden\" id=\"adicionaEntidade" + i +"\" name=\"adicionaEntidade" + i +"\" value=\"N\">\n";
				}
			}
			alert("Entidade(s) filho(s) removida(s).");
		}
	} else {
		alert("Pelo menos uma entidade filho deve ser selecionada");
	}
}

</script>
