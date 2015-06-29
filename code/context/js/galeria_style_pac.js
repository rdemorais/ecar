function mudamenuanexos(itemdoanexo) {

	if (document.getElementById('anexoconteudo'+itemdoanexo).style.display == 'block') {
	
		document.getElementById('anexotipomenu'+itemdoanexo).style.backgroundColor = '#FFFFFF';
		document.getElementById('anexotipo'+itemdoanexo).style.backgroundPosition = 'left';
		document.getElementById('anexotipo'+itemdoanexo).style.backgroundRepeat = 'no-repeat';
		document.getElementById('anexotipo'+itemdoanexo).style.backgroundImage = 'url(../images/collapsed_button.gif)';
		document.getElementById('anexoconteudo'+itemdoanexo).style.display = 'none';	
	}
	else {
	
		for (z=0;z < qtdCategorias;z++) {
			document.getElementById('anexotipomenu'+z).style.backgroundColor = '#FFFFFF';
			document.getElementById('anexotipo'+z).style.backgroundPosition = 'left';
			document.getElementById('anexotipo'+z).style.backgroundRepeat = 'no-repeat';
			document.getElementById('anexotipo'+z).style.backgroundImage = 'url(../images/collapsed_button.gif)';
			document.getElementById('anexoconteudo'+z).style.display = 'none';
		}
	
		document.getElementById('anexotipomenu'+itemdoanexo).style.backgroundColor = '#F5E699';
		document.getElementById('anexotipo'+itemdoanexo).style.backgroundPosition = 'left';
		document.getElementById('anexotipo'+itemdoanexo).style.backgroundRepeat = 'no-repeat';
		document.getElementById('anexotipo'+itemdoanexo).style.backgroundImage = 'url(../images/expanded_button.gif)';
		document.getElementById('anexoconteudo'+itemdoanexo).style.display = 'block';
	}
}
