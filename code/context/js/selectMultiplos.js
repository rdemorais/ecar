function getSelections(select) {
    var options = select.options,
    sOptions = [],
    opt, k=0;
    while(opt = options[k++])
        if(opt.selected) {
            sOptions[sOptions.length] = opt;
    }
    return sOptions;
}
function copyOptionsFromComboArray(aComboArray) {
  var options = new Array();
  for (var i = 0; i < aComboArray.options.length; ++i) {
     options[i] = new Option(aComboArray.options[i].text, aComboArray.options[i].value);
  }
  return options;
}
function cleanComboArray(aComboArray) {
  var arraySize = aComboArray.options.length;
  for (var i = 0; i < arraySize; ++i) {
     aComboArray.options[i] = null;
  }
  aComboArray.length = 0;
}
function fillComboArray(comboArray, options) {
  for (var i = 0; i < options.length; ++i) {
     comboArray[comboArray.length] = options[i];
  }
  return options;
}

function adicionarMarcados(comboOrigem, comboDestino){
  if (comboOrigem.selectedIndex < 0) {
       return;
  }
  var selecionados = getSelections(comboOrigem);

  //Limpa referências. Problemas da raposa...
  var comboOrigemOptionsCopy = copyOptionsFromComboArray(comboOrigem)
  cleanComboArray(comboOrigem)
  fillComboArray(comboOrigem, comboOrigemOptionsCopy);

  var comboDestinoOptionsCopy = copyOptionsFromComboArray(comboDestino);
  cleanComboArray(comboDestino);
  fillComboArray(comboDestino, comboDestinoOptionsCopy);
  fillComboArray(comboDestino, selecionados);

  if (selecionados != '') {
     if (comboDestino.options.length > 0 && comboDestino.options[0].value == '-1') {
        comboDestino.length = 0;
     }
       for (var k = 0; k < selecionados.length; ++k) {
          for (var i = 0; i < comboOrigem.length; ++i) {
           if (comboOrigem.options[i].value == selecionados[k].value) {
              comboOrigem.options[i] = null;
           }
        }
     }
  }
}
 
function selAllCombo(combo ){
	for (i = 0 ; i < combo.length ; i++) {
		combo.options[i].selected = true;
	}
}