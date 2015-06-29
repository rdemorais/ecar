/*
M?scara para Data. Uso:
<input type=text name=dataAquisicao id=dataAquisicao size=10 maxlength=10 OnKeyPress='javascript:return(Formatar(this,event));'>
*/
function Formatar(campo,e) 
{
	var cod="";
	if(document.all) {
		cod=event.keyCode;
	} 
	else {
		cod=e.which;
	}
	if(cod == 0) 
		return;
	if(cod == 8) 
		return;
	if (cod < 48 || cod > 57)
	{
		if (cod < 45 || cod > 57) 
			alert("Digite somente Caracteres Numéricos!");
		cod=0;
		campo.focus(); 
		return false;
	}
	tam=campo.value.length; 
	if(tam > 9) 
		return false;
	var caract = String.fromCharCode(cod);
	if(tam == 2 || tam == 5) {
		campo.value+="/"+caract; return false;
	}
	campo.value+=caract; 
	return false; 
}
document.onKeyPress=Formatar;

function verificaData()
{
  /*Separa a data em dia, m?s e ano para passar como par?metro na fun??o isDate*/
  var str = document.form1.dataAquisicao.value;
  var day = str.charAt(0) + str.charAt(1);
  var month = str.charAt(3) + str.charAt(4);
  var year = str.charAt(6) + str.charAt(7) + str.charAt(8) + str.charAt(9);
   
  if (!isDate(day,month,year))
  {
    alert("O campo Data de Aquisição está preenchido incorretamente!");  
    document.form1.dataAquisicao.focus();	  
    return(false);
  }
  else
  {
    return(true);	
  }
}

function verificaData2(campo, nomeCampo)
{
  /*Separa a data em dia, m?s e ano para passar como par?metro na fun??o isDate*/
  var str = campo.value;
  var day = str.charAt(0) + str.charAt(1);
  var month = str.charAt(3) + str.charAt(4);
  var year = str.charAt(6) + str.charAt(7) + str.charAt(8) + str.charAt(9);
   
  if (!isDate(day,month,year))
  {
    alert("O campo " +  nomeCampo + " está preenchido incorretamente!");  
    campo.focus();	  
    return(false);
  }
  else
  {
    return(true);	
  }
}

function makeArray(n) 
{
 for (var i = 1; i <= n; i++) {
      this[i] = 0;
   } 
   return this;
}

function daysInFebruary (year)
{
  return (  ((year % 4 == 0) && ( (!(year % 100 == 0)) || (year % 400 == 0) ) ) ? 29 : 28 );
}


function isDate (day,month,year)
{  
  var daysInMonth = makeArray(12); 
  daysInMonth[1] = 31; 
  daysInMonth[2] = 29;   // deve ser programado
  daysInMonth[3] = 31;
  daysInMonth[4] = 30;
  daysInMonth[5] = 31;
  daysInMonth[6] = 30;
  daysInMonth[7] = 31;
  daysInMonth[8] = 31;
  daysInMonth[9] = 30;
  daysInMonth[10] = 31;
  daysInMonth[11] = 30;
  daysInMonth[12] = 31;

   // verifica se os dias, meses e anos sao inteiros v?lidos
  if ( isNaN(year) || (year.indexOf(".")!=-1) || (year.length==0) || (year.length!=4) || (Number(year)<0) )
    return (false);
  if ( isNaN(month) || (month.indexOf(".")!=-1) || (month.length == 0) || (Number(month)<1) || (Number(month)>12) )
    return (false);
  if ( isNaN(day) || (day.indexOf(".")!=-1) || (day.length==0) || (Number(day)<1) || (Number(day)>31) )
    return (false);
    var intYear = parseInt(year);
    var intMonth = parseInt(month);
    var intDay = parseInt(day);

    // verifica dias inv?lidos, exceto para fevereiro
    if (intDay > daysInMonth[intMonth]) return false; 

    if ((intMonth == 2) && (intDay > daysInFebruary(intYear))) return false;

    return true;
}