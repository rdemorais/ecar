<script>
<!--

var cont = 0;
var	quant = 0;
var texto="";

function labelExcluir (idLabel){
 	if (quant > 0) {
 		document.getElementById(idLabel).innerHTML = 'Excluir';		
 	} else {
 		document.getElementById(idLabel).innerHTML = '';
 	}

}

function adicionarAtor(codigo, descricao) {
	if(!existeAtor(codigo)) {
		cont++;
		quant ++;
		texto = "<span id=\"ator"+cont+"\">\n";
		texto += "\t<input type=\"hidden\" id=\"codEnt"+cont+"\" name=\"codEnt\" value=\""+ codigo +"\" >\n";
		texto+= "\t<input type=\"hidden\" id=\"atorAdiocionado" + cont +"\" name=\"atorAdicionado" + cont +"\" value=\"S\">\n";
		texto += "\t<input type=\"checkbox\" class=\"form_check_radio\" id=\"chkEnt"+cont+"\" name=\"chkEnt"+cont+"\" value=\""+ codigo +"\" <%=_disabled%> /> &nbsp;";
		texto += descricao +  "<br/>\n";
		texto += "</span> \n";
		document.getElementById('ator').innerHTML += texto;
		labelExcluir('lbAtorExcluir');
		return true;
	} 
	return false;
}

function existeAtor(codigo) {
	for(i = 1; i <= cont; i++) 	{
		if (document.getElementById("atorAdiocionado"+i).value =='S'){
			if(document.getElementById("codEnt"+i).value == codigo) {
				alert("Ator já informado!");
				return true;
			}
		}
	}
	return false;
}

function removeAtor() {
	var selecionados = 0;
	for (i = 1; i <= cont; i++){
		if (document.getElementById("atorAdiocionado"+i).value =='S'){
			if (document.getElementById("chkEnt"+i).checked == true) {
				selecionados++;
			}
		}
	}

	if (selecionados > 0) {
		if (confirm("Os locais selecionados serão removidos. Confirma?")) {
			for (i = 1; i <= cont; i++) {
				if (document.getElementById("atorAdiocionado"+i).value =='S'){
					if (document.getElementById("chkEnt"+i).checked == true) {
						document.getElementById("ator"+i).innerHTML = "<input type=\"hidden\" id=\"atorAdiocionado" + i +"\" name=\"atorAdicionado" + i +"\" value=\"N\">\n";
					}
				}
			}
			quant -= selecionados;
			labelExcluir('lbAtorExcluir');
		}
	} else {
		alert("Pelo menos um ator deve ser selecionado");
	}
	
}

function getDadosPesquisa(codigo, descricao){
	adicionarAtor(codigo, descricao);
}

function exibe(elemento){
	elem= document.getElementById(elemento);
	elem.style.visibility='visible'
}

function esconde(elemento){
	elem= document.getElementById(elemento);
	elem.style.visibility='hidden'
}

function apagaCampo(elemento){
	document.getElementById(elemento).value='';
}

function respostaChk(campoChk, span){
	if (document.getElementById(campoChk) && document.getElementById(span) ){
		campo = document.getElementById(campoChk);
		texto = document.getElementById(span);
		if (campo.checked){
			texto.innerHTML = "Sim"
		} else {
			texto.innerHTML = "Não"
		}
	}

}

// Bloqueia clique do mouse 
// Fonte: http://www.arquivodecodigos.net/arquivo/visualizar_dica.php?qual_dica=287
var ns = (document.layers)? true:false;
var ie = (document.all)? true:false;
if (ns) document.captureEvents(Event.MOUSEDOWN || Event.CLICK);
document.onclick = sourcecodeprotect;
document.onmousedown = sourcecodeprotect;

// ***********************************************************
function sourcecodeprotect(e) {
  if (ns&&(e.which==3)) return false;
  else if (ie&&(window.event.button==2)) 
     alert("Não é permitido o uso do botão direito.");
  else return true;
  }

//***********************************************************
function cleanup() {
  if (ns) document.releaseEvents(Event.MOUSEDOWN || Event.CLICK);
  }



//-->
</script>
