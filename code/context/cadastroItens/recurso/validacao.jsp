function validar(form){
	
	if (!validaString(form.codFonr,'Nome',true)) {
		return(false);
	}
	if (!validaData(form.dataValorEfieft,false,true,true)) {
		if(form.dataValorEfieft.value == ""){
			alert("<%=_msg.getMensagem("itemEstrutura.fonteRecurso.validacao.dataValorEfieft.obrigatorio")%>");
		}else{
			alert("<%=_msg.getMensagem("itemEstrutura.fonteRecurso.validacao.dataValorEfieft.invalido")%>");
		}
		form.dataValorEfieft.focus();
		return(false);
	}
	
	//Verificar se valores das combos são diferentes;
//	if (!verificarCombos()) {
//		alert("Valores Recursos duplicados");
//		return false;
//	}
	
	if (!validaRecursos(form)) {
		return false;
	}
	return true;
}

/* **********************************************************************
	Verifica valores das combos de Recurso, todos os valores selecionados
	devem ser diferentes.
************************************************************************* */
/*function verificarCombos(){
	var el = document.getElementsByTagName('SELECT');
	var i = 0, n = 0;
	var strCombos = "";
	
	while (i < el.length){
		if (el[i] != null){
			if(el[i].id == "combo"){
				strCombos = strCombos + el[i].value + "|";
			}
		}
		i++;
	}
	var valCombos = strCombos.split('|');
	
	for (i = 0; i < valCombos.length; i++) {
		valor = valCombos[i];
		if (valCombos[i] != "") {
			for (n = 0; n < valCombos.length; n++) {
				if (i != n && valor == valCombos[n]) {
					return false;
				}
			}
		}
	}
	
	return true;
}*/

/** **********************************************************************
	Calcula totais
************************************************************************ */
function calcularTotais(recursoDesc1,recursoDesc2) {
	var numLinhas = document.form.numLinhas.value;
	var numColunas = document.form.numColunas.value;
	
	var totAprovLinha = 0, totAprovColuna = 0;
	var totRevLinha = 0, totRevColuna = 0;
	var totAprovGeral = 0, totRevGeral = 0;

	// CALCULAR TOTAIS DOS VALORES
	for (var i = 0; i < numLinhas; i++) {
		// passar por todas as linhas
		totAprovLinha = 0;
		totRevLinha = 0;
		for (var j = 0; j < numColunas; j++) {
			// passar por todas as colunas
			var elAprov = document.getElementById("aprovL" + i + "C" + j);
			var elRev = document.getElementById("revL" + i + "C" + j);
			var n = 0;
			
			if (elAprov != null) {
			
				if (trim(elAprov.value) == "") {
					elAprov.value = "0,00";
				}

				if (validaDecimal(elAprov.value)) {
					valorCampo = elAprov.value.replace(",",".");
					totAprovLinha = parseFloat(totAprovLinha) + parseFloat(valorCampo);
				} else {
					alert("<%=_msg.getMensagem("itemEstrutura.recurso.validacao.valorAprovadoEfiep.invalido")%>");
					elAprov.value = "";
					elAprov.focus();
					return false;
				}
			}
			
			if (elRev != null) {
				if (trim(elRev.value) == "") {
					elRev.value = "0,00";
				}

				if (validaDecimal(elRev.value)) {
					valorCampo = elRev.value.replace(",",".");
					totRevLinha = parseFloat(totRevLinha) + parseFloat(valorCampo);
				} else {
					alert("<%=_msg.getMensagem("itemEstrutura.recurso.validacao.valorAprovadoEfiep.invalido")%>");
					elRev.value = "";
					elRev.focus();
					return false;
				}
			}
		}
		
		if (recursoDesc1 != '') {
			valFmt = formataDecimal(totAprovLinha.toString());
			strEval = "document.form.totalAprovRec" + i + ".value = \"" + valFmt + "\"";
			eval(strEval);
		}
		
		if (recursoDesc2 != '') {
			valFmt = formataDecimal(totRevLinha.toString());
			strEval = "document.form.totalRevRec" + i + ".value = \"" + valFmt + "\"";
			eval(strEval);
		}
	}
	
	// CALCULAR TOTAIS DOS EXERCICIOS	
	for (var j = 0; j < numColunas; j++) {
	
		// passar por todas as colunas
		totAprovColuna = 0;
		totRevColuna = 0;
		for (var i = 0; i < numLinhas; i++) {
			// passar por todas as linhas
			var elAprov = document.getElementById("aprovL" + i + "C" + j);
			var elRev = document.getElementById("revL" + i + "C" + j);
			var n = 0;
			
			if (elAprov != null) {
				if (trim(elAprov.value) != "") {
					if (validaDecimal(elAprov.value)) {
						valorCampo = elAprov.value.replace(",",".");
						totAprovColuna = parseFloat(totAprovColuna) + parseFloat(valorCampo);
					} else {
						alert("<%=_msg.getMensagem("itemEstrutura.recurso.validacao.valorAprovadoEfiep.invalido")%>");
						elAprov.value = "";
						elAprov.focus();
						return false;
					}
				}
			}
			
			if (elRev != null) {
				if (trim(elRev.value) != "") {
					if (validaDecimal(elRev.value)) {
						valorCampo = elRev.value.replace(",",".");
						totRevColuna = parseFloat(totRevColuna) + parseFloat(valorCampo);
					} else {
						alert("<%=_msg.getMensagem("itemEstrutura.recurso.validacao.valorAprovadoEfiep.invalido")%>");
						elRev.value = "";
						elRev.focus();
						return false;
					}
				}
			}
			
		}
		/*
		valFmt = formataDecimal(totAprovColuna.toString());
		strEval = "document.form.totalAprovExe" + j + ".value = \"" + valFmt.toString() + "\"";
		eval(strEval);
		
		valFmt = formataDecimal(totRevColuna.toString());
		strEval = "document.form.totalRevExe" + j + ".value = \"" + valFmt.toString() + "\"";
		eval(strEval); 

		diferenca = totAprovColuna - totRevColuna;
		valFmt = formataDecimal(diferenca.toString());
		strEval = "document.form.diferenca" + j + ".value = \"" + valFmt + "\"";
		eval(strEval);
		*/
		totAprovGeral = totAprovGeral + totAprovColuna;
		totRevGeral = totRevGeral + totRevColuna;
		
	}

	if (recursoDesc1 != '') {
		valFmt = formataDecimal(totAprovGeral.toString());
		document.form.totalAprovadoGeral.value = valFmt;
	}
	
	if (recursoDesc2 != '') {
		valFmt = formataDecimal(totRevGeral.toString());
		document.form.totalRevisadoGeral.value = valFmt;
	}
	
	/*
	diferenca = totAprovGeral - totRevGeral;
	valFmt = formataDecimal(diferenca.toString());
	document.form.diferencaTotal.value = valFmt;
	*/
}

/** **********************************************************************
	Soma os valores Aprovados por recurso e por exercício
************************************************************************ */
function somaAprovado(linha, coluna){
	//alert("linha=" + linha + " e coluna=" + coluna);
	
	//calcularTotal();
}

/** **********************************************************************
	Soma os valores Revisados por recurso e por exercício
************************************************************************ */
function somaRevisado(linha, coluna){
	//alert("linha=" + linha + " e coluna=" + coluna);
	
	//calcularTotal();
}

function formataDecimal(valor) {
	valor = valor + "";
	var tam = valor.length;
	var pos = valor.indexOf(".");
	
	if (pos > 0) {
		var valPosV = valor.substr(pos + 1);
		if (valPosV.length == 1) {
    	 	valor = valor + "0";
		}
		if (valPosV.length > 2) {
			//alert(valor);
    	 	valor = valor.substr(0, pos + 3);
		}
	} else if (pos < 0) {
   	 	valor = valor + ".00";
	}
	
	valor = valor.replace(".",",");
	return valor;
}

function inserirConta(form, codExe, codRec){
	var codFonR = form.codFonr.value;
	if(codFonR == ""){
		alert("Informe uma categoria");
	}
	else {
		form.action = "../contasOrcamento/frm_inc.jsp?codIett=" + form.codIett.value + "&exercicioExe=" + codExe + "&fonteRecursoFonr=" + codFonR + "&recursoRec=" + codRec;
		form.submit();
	}
}