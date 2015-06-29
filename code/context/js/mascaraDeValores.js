
//MÁSCARA DE VALORES
//@fonte: http://scriptbrasil.com.br/forum/index.php?showtopic=3341&st=125&p=323596&#entry323596
/*Adaptado por Thaise */


/*Exemplo de uso

Data:<input type="text" size="20" onkeypress="return txtBoxFormat(this, '99/99/9999', event);">
CPF:<input type="text" size="20" onkeypress="return txtBoxFormat(this, '99.999.999-99', event);">
Telefone:<input type="text" size="20" onkeypress="return txtBoxFormat(this, '(99)9999-9999', event);">
Código:<input type="text" size="20" onkeypress="return txtBoxFormat(this, '99-999', event);">
*/


function txtBoxFormat(objeto, sMask, evtKeyPress) {
    var i, nCount, sValue, fldLen, mskLen,bolMask, sCod, nTecla;
	
if(document.all) { // Internet Explorer
    nTecla = evtKeyPress.keyCode;
} else if(document.layers) { // Nestcape
    nTecla = evtKeyPress.which;
} else {
    nTecla = evtKeyPress.which;
    if (nTecla == 8||nTecla == 0|| nTecla == 13 ) {
        return true;
    }
    
}
    mskLen = sMask.length;
    objLen = objeto.value.length;
    sValue = objVal =objeto.value;
    
    // Limpa todos os caracteres de formatação que
    // já estiverem no campo.
    sValue = sValue.toString().replace( "-", "" );
    sValue = sValue.toString().replace( "-", "" );
    sValue = sValue.toString().replace( ".", "" );
    sValue = sValue.toString().replace( ".", "" );
    sValue = sValue.toString().replace( "/", "" );
    sValue = sValue.toString().replace( "/", "" );
    sValue = sValue.toString().replace( ":", "" );
    sValue = sValue.toString().replace( ":", "" );
    sValue = sValue.toString().replace( "(", "" );
    sValue = sValue.toString().replace( "(", "" );
    sValue = sValue.toString().replace( ")", "" );
    sValue = sValue.toString().replace( ")", "" );
    sValue = sValue.toString().replace( " ", "" );
    sValue = sValue.toString().replace( " ", "" );
    fldLen = sValue.length;

    
    i = 0;
    nCount = 0;
    sCod = "";
    mskLen = fldLen;

    while (i <= mskLen) {
      bolMask = ((sMask.charAt(i) == "-") || (sMask.charAt(i) == ".") || (sMask.charAt(i) == "/") || (sMask.charAt(i) == ":"))
      bolMask = bolMask || ((sMask.charAt(i) == "(") || (sMask.charAt(i) == ")") || (sMask.charAt(i) == " "))

      if (bolMask) {
        sCod += sMask.charAt(i);
        mskLen++; }
      else {
        sCod += sValue.charAt(nCount);
        nCount++;
      }

      i++;
    }

    objeto.value = sCod;
    
    

    if (nTecla != 8) { // backspace
      if (sMask.charAt(i-1) == "9") { // apenas números...
        return ((nTecla > 47) && (nTecla < 58)); 
      } else if (mskLen <=  objLen ) { //maior do que a mascara
      	objeto.value = objVal.substring(0,mskLen);
      	return (false ); 
      }
      else { // qualquer caracter...
        return true;
      }
    }
    else {
      return true;
    }
  }
