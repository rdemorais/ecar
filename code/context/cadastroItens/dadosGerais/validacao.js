/* Codigo JS para controlar o preenchimento do combo sub-area em funcao
 * do combo area
 * O array aSubArea é um array de duas dimensoes contendo todas as areas
 * e sub areas. Esse array é declarado com Array(1) apenas para ser instanciado
 * Seu conteudo é alterado depois, dependendo da existencia dos campos area e
 * sua area.
 */

var aSubArea = new Array(1);

function existeSubArea () {
	var el = document.getElementsByTagName("SELECT");
	var existe = false;

	for (var i = 0; i < el.length && !existe; i++){
		if (el.item(i).name == 'subAreaSare')
			existe = true;
	}
	return existe;
}

/* Essa funcao zera o conteudo do combo cbSubArea e depois
 * o popula novamente de acordo com o indice (correspondente a uma area)
 * passado como parâmetro
 */
function updateSub(index, cbSubArea) { 
	if (existeSubArea()) {
		for (var j = 0; j < cbSubArea.options.length; ++j) { 
			cbSubArea.options[j] = null;
		} 
		
		for (var k = 0; k< aSubArea[index].length; ++k) { 
			cbSubArea.options[k] = aSubArea[index][k];
		} 
	}
}

/* na alteracao seleciona a subArea original depois de recarregar o combo */
function selecionaSubArea(options, codigo) {
	for (var i = 0; i < options.length; i++) {
		if (options[i].value == codigo)
			options[i].selected = true;
		else
			options[i].selected = false;
	}
}

function carregaUnidadeOrc(codOrgao, disabled){
	var url = _pathEcar + "/cadastroItens/dadosGerais/unidadeOrcForm.jsp?codOrgaoUnidadeOrc=" + codOrgao + "&codIett=" + document.form.codIett.value + "&disabled=" + disabled;
	openAjax(url, "unidadeOrcamentariaDiv", null, null, true, "../../images");
}

function atualizaUnidadeOrc(orgaoResp1, disabled){
	var unidadeOrc = document.getElementById("unidadeOrcamentariaDiv");
	if(orgaoResp1 != 'undefined' && unidadeOrc != null){
		if(orgaoResp1.value != ""){
			carregaUnidadeOrc(orgaoResp1.value, disabled);
		}
	}
}