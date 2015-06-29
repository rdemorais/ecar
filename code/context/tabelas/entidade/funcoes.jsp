
<script>

var contadorEnd = 0;

var bairroEnd = new Array();
var cepEnd = new Array();
var cidadeEnd = new Array();
var idEnd = new Array();
var complementoEnd = new Array();
var enderecoEnd = new Array();
var addEnd = new Array();
var uf = new Array();
var tpEnd = new Array();


function setOutrosEnderecos(n)
{
	for(i = 1; i <= n; i++)
	{
		if(document.getElementById("adicionaEndereco"+i).value == "S")
		{
			bairroEnd[i] = document.getElementById("bairroEnd"+i).value;
			cepEnd[i] = document.getElementById("cepEnd"+i).value;
			cidadeEnd[i] = document.getElementById("cidadeEnd"+i).value;
			idEnd[i] = document.getElementById("idEnd"+i).value;
			complementoEnd[i] = document.getElementById("complementoEnd"+i).value;
			enderecoEnd[i] = document.getElementById("enderecoEnd"+i).value;
			uf[i] = document.getElementById("uf"+i).value;
			tpEnd[i] = document.getElementById("tipoEnderecoTpend"+i).value;
		}
		addEnd[i] = document.getElementById("adicionaEndereco"+i).value;
	}
}

function getOutrosEnderecos(n)
{
	for(i = 1; i < n; i++)
	{
		if (addEnd[i] == "S")
		{
			document.getElementById("bairroEnd"+i).value = bairroEnd[i];
			document.getElementById("cepEnd"+i).value = cepEnd[i];
			document.getElementById("cidadeEnd"+i).value = cidadeEnd[i];
			document.getElementById("idEnd"+i).value = idEnd[i];
			document.getElementById("complementoEnd"+i).value = complementoEnd[i];
			document.getElementById("enderecoEnd"+i).value = enderecoEnd[i];
			document.getElementById("uf"+i).value = uf[i];
			document.getElementById("tipoEnderecoTpend"+i).value = tpEnd[i];
		}
		document.getElementById("adicionaEndereco"+i).value = addEnd[i];
	}
}

function setSelectedUf(i, uf)
{
	document.getElementById("uf"+i).value = uf;	
}

function setSelectedTpEnd(i, tipo)
{
	document.getElementById("tipoEnderecoTpend"+i).value = tipo;	
}

function adicionaEndereco(id, tipo, ende, compl, bairro, cep, cid, uf) {
	if ((id == null) || (id == ""))
		idEndereco = prompt("Digite um identificador para o endereço", "Comercial");
	else
		idEndereco = id;
	if ((idEndereco != null) && (idEndereco != ""))
	{
		setOutrosEnderecos(contadorEnd);
		contadorEnd += 1;
		divconteudo = "<table id='endereco"+contadorEnd+"'>\n";
		divconteudo += "<input type=\"hidden\" id=\"idEnd"+contadorEnd+"\" name=\"idEnd"+contadorEnd+"\" value="+ idEndereco +" <%=_disabled%>>\n";
		divconteudo += "<input type=\"hidden\" id=\"adicionaEndereco"+contadorEnd+"\" name=\"adicionaEndereco"+contadorEnd+"\" value=\"S\" <%=_disabled%>>\n";
		divconteudo += "\t<tr>\n";
		divconteudo += "\t\t<td class=\"label\">"+idEndereco+"</td>\n";
		divconteudo += "\t\t<td><a href=\"#\" onClick=\"removeEndereco("+ contadorEnd +")\">";
		if ("<%=_disabled%>" == "")
			divconteudo +="Excluir";
		divconteudo +="</a></td>\n";
		divconteudo += "\t</tr>\n\t<tr>\n";
		divconteudo += "\t\t<td class=\"labelNormal\">Tipo do Endereço</td>\n";
		divconteudo += "\t\t<td>\n";
		divconteudo += "\t\t\t<select id=\"tipoEnderecoTpend"+contadorEnd+"\" name=\"tipoEnderecoTpend"+contadorEnd+"\" value=\""+ tipo +"\" <%=_disabled%> >\n";
		divconteudo += getTipoEndereco();
		divconteudo += "\n\t\t\t</select>\n";		
		divconteudo += "\t\t</td>\n";
		divconteudo += "\t</tr>\n\t<tr>\n";
		divconteudo += "\t\t<td class=\"labelNormal\">Endereço</td>\n"
		divconteudo += "\t\t<td><input type=\"text\" size=\"40\" maxlength=\"50\" id=\"enderecoEnd" + contadorEnd + "\" name=\"enderecoEnd" + contadorEnd + "\" value=\""+ ende +"\" <%=_disabled%> /></td>\n";
		divconteudo += "\t</tr>\n\t<tr>\n";	
		divconteudo += "\t\t<td class=\"labelNormal\">Complemento</td>\n";
		divconteudo += "\t\t<td><input type=\"text\"  size=\"40\" maxlength=\"40\" id=\"complementoEnd" + contadorEnd + "\" name=\"complementoEnd" + contadorEnd + "\" value=\""+ compl +"\" <%=_disabled%> />\n</td>\n";
		divconteudo += "\t</tr>\n\t<tr>\n";
		divconteudo += "\t\t<td class=\"labelNormal\">Bairro</td>\n";
		divconteudo += "\t\t<td><input type=\"text\" size=\"40\" maxlength=\"40\" id=\"bairroEnd" + contadorEnd + "\" name=\"bairroEnd" + contadorEnd +"\" value=\""+ bairro +"\" <%=_disabled%> />\n</td>\n";
		divconteudo += "\t</tr>\n\t<tr>\n";
		divconteudo += "\t\t<td class=\"labelNormal\">CEP</td>\n";
		divconteudo += "\t\t<td><input type=\"text\" size=\"10\" maxlength=\"8\" id=\"cepEnd" + contadorEnd + "\" name=\"cepEnd" + contadorEnd + "\" value=\""+ cep +"\" <%=_disabled%> />\n</td>\n";
		divconteudo += "\t</tr>\n\t<tr>\n";
		divconteudo += "\t\t<td class=\"labelNormal\">Cidade</td>\n";
		divconteudo += "\t\t<td><input type=\"text\" size=\"40\" maxlength=\"40\" id=\"cidadeEnd" + contadorEnd + "\" name=\"cidadeEnd" + contadorEnd + "\" value=\""+ cid +"\" <%=_disabled%> />\n</td>\n";
		divconteudo += "\t</tr>\n\t<tr>\n";
		divconteudo += "\t\t<td class=\"labelNormal\">UF</td>\n";
		divconteudo += "\t\t<td>\n";
		divconteudo += "\t\t\t<select id=\"uf"+contadorEnd+"\" name=\"uf"+contadorEnd+"\" <%=_disabled%>>\n";
		divconteudo += getUf();
		divconteudo += "\n\t\t\t</select>\n";		
		divconteudo += "\t\t</td>\n";
		divconteudo += "\t</tr>\n";
		divconteudo += "</table>\n";	
		document.getElementById('endereco').innerHTML += divconteudo;
		getOutrosEnderecos(contadorEnd);	
		document.form.contEnderecos.value = contadorEnd;	
		setSelectedTpEnd(contadorEnd, tipo);		
		setSelectedUf(contadorEnd, uf);
	}
		
}

function removeEndereco(n) {
	if (confirm("O Endereço "+n+" será excluído. Confirma?"))
		document.getElementById("endereco"+n).innerHTML = "<input type=\"hidden\" id=\"adicionaEndereco"+n+"\" name=\"adicionaEndereco"+n+"\" value=\"N\">\n";
}

var contadorTel = 0;
var dddTel = new Array();
var telefoneTel = new Array();
var idTel = new Array();
var addTel = new Array();
function setOutrosTelefones(n)
{
	for(i = 1; i <= n; i++)
	{
		if(document.getElementById("adicionaTelefone"+i).value == "S")
		{
			dddTel[i] = document.getElementById("dddTel"+i).value;
			telefoneTel[i] = document.getElementById("telefoneTel"+i).value;
			idTel[i] = document.getElementById("idTel"+i).value;
		}
		addTel[i] = document.getElementById("adicionaTelefone"+i).value;
	}
}

function getOutrosTelefones(n)
{
	for(i = 1; i < n; i++)
	{
		if (addTel[i] == "S")
		{
			document.getElementById("dddTel"+i).value = dddTel[i];
			document.getElementById("telefoneTel"+i).value = telefoneTel[i];
			document.getElementById("idTel"+i).value = idTel[i];	
		}
		document.getElementById("adicionaTelefone"+i).value = addTel[i];
	}
}

function adicionaTelefone(ddd, tele, id) {
	if ((id == null) || (id == ""))
		idTelefone = prompt("Digite um identificador para o telefone", "Comercial");	
	else
		idTelefone = id;
	if ((idTelefone != null) && (idTelefone != ""))
	{	
		setOutrosTelefones(contadorTel);
		contadorTel += 1;
		divconteudo = "<table id='telefone"+contadorTel+"'>\n";
		divconteudo += "<input type=\"hidden\" id=\"idTel"+contadorTel+"\" name=\"idTel"+contadorTel+"\" value="+ idTelefone +" <%=_disabled%> >\n";
		divconteudo += "<input type=\"hidden\" id=\"adicionaTelefone"+ contadorTel +"\" name=\"adicionaTelefone"+ contadorTel +"\" value=\"S\" <%=_disabled%> >\n"	;
		divconteudo += "\t<tr>\n";
		divconteudo += "\t\t<td class=\"labelNormal\">"+idTelefone+"</td>\n"
		divconteudo += "\t\t<td><input type=\"text\" size=\"3\" maxlength=\"3\" id=\"dddTel" + contadorTel + "\" name=\"dddTel" + contadorTel + "\" value=\""+ ddd +"\" <%=_disabled%> /> - ";
		divconteudo += "<input type=\"text\" size=\"20\" maxlength=\"10\" id=\"telefoneTel" + contadorTel + "\" name=\"telefoneTel" + contadorTel + "\" value=\""+ tele +"\" <%=_disabled%> />\n";
		divconteudo += "<a href=\"#\" onClick=\"removeTelefone("+ contadorTel +")\">";
		if ("<%=_disabled%>" == "")
			divconteudo +="Excluir";
		divconteudo +="</a>\n";
		divconteudo += "\t</tr>\n";
		divconteudo += "</table>\n";
		document.getElementById("telefone").innerHTML += divconteudo;
		document.form.contTelefones.value = contadorTel;
		getOutrosTelefones(contadorTel);
	}
}

function removeTelefone(n) {
	if (confirm("O Telefone "+n+" será excluído. Confirma?"))
		document.getElementById("telefone"+n).innerHTML = "<input type=\"hidden\" id=\"adicionaTelefone" + n +"\" name=\"adicionaTelefone" + n +"\" value=\"N\">\n";
}


var contadorLocal = 0;
var	quantidadeLocal = 0;

function existeLocal(codLit)
{
	for(i = 1; i <= contadorLocal; i++)
	{
		if(document.getElementById("adicionaLocal"+i).value == "S")
		{		
			if(document.getElementById("codLit"+i).value == codLit)
			{
				alert("Local já informado!");
				return true;
			}
		}
	}
	return false;
}

function adicionaLocal(codLit, ident) {
	if(!existeLocal(codLit))
	{
		contadorLocal += 1;
		quantidadeLocal += 1;
		divconteudo = "<table id='local"+contadorLocal+"'>\n";
		divconteudo += "<input type=\"hidden\" id=\"idLit"+contadorLocal+"\" name=\"idLit"+contadorLocal+"\" value=\""+ ident +"\" <%=_disabled%> >\n";
		divconteudo += "<input type=\"hidden\" id=\"adicionaLocal"+ contadorLocal +"\" name=\"adicionaLocal"+ contadorLocal +"\" value=\"S\" <%=_disabled%> >\n";
		divconteudo += "<input type=\"hidden\" id=\"codLit" + contadorLocal + "\" name=\"codLit" + contadorLocal + "\" value=\""+ codLit +"\" <%=_disabled%> />\n";
		divconteudo += "\t<tr>\n";
		divconteudo += "\t\t<td class=\"labelNormal\">&nbsp;</td>\n";
		divconteudo += "\t\t<td><input type=\"checkbox\" class=\"form_check_radio\" id=\"chkLit" + contadorLocal + "\" name=\"chkLit" + contadorLocal + "\" value=\""+ codLit +"\" <%=_disabled%> /> &nbsp;";
		divconteudo += ident + "</td>\n";
		divconteudo += "\t</tr>\n";
		divconteudo += "</table>\n";
		document.getElementById('local').innerHTML += divconteudo;
		document.form.contLocal.value = contadorLocal;
		getOutrosTelefones(contadorTel);
		labelExcluirLocal();
		return true;
	}
	labelExcluirLocal();
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
					if (document.getElementById("chkLit"+i).checked == true)
						document.getElementById("local"+i).innerHTML = "<input type=\"hidden\" id=\"adicionaLocal" + i +"\" name=\"adicionaLocal" + i +"\" value=\"N\">\n";
				}
			}
			quantidadeLocal -= selecionados;
			alert("Local removido.");
		}
	} else {
		alert("Pelo menos um local deve ser selecionado");
	}
	labelExcluirLocal();
}

function labelExcluirLocal (){
 	if (quantidadeLocal > 0) {
 		document.getElementById('localExcluir').innerHTML = 'Excluir';		
 	} else {
 		document.getElementById('localExcluir').innerHTML = '';
 	}

}

</script>
