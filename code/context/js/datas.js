/** Funcao para mascarar a data em "tempo de digita??o"
    Testa o browser para pegar o evento correto
    Verifica se foi teclado backspace e nas posicoes corretas adiciona uma barra
    @param evento, objeto data
    @return true
    @see <input .... onkeyup="mascaraData(window.event, this)">
*/    
function mascaraData(event, objData)
{
   var ehBackspace;
   var strData = objData.value;
   var nBytes = strData.length;
   

   // funciona, mas deve ser melhorado
   if (navigator.appName == "Netscape")  //mozilla ? netscape
       ehBackspace = (event.which == 8) ? true : false;
   else 
       ehBackspace = (event.keyCode == 8) ? true : false;
   if((nBytes==2 || nBytes==5) && !ehBackspace)
      objData.value = strData+"/";
   return true;
}

/** Funcao para mascarar a data em "tempo de digita??o" no padr?o "Mes/Ano"
    Testa o browser para pegar o evento correto
    Verifica se foi teclado backspace e nas posicoes corretas adiciona uma barra
    @param evento, objeto data
    @return true
    @see <input .... onkeyup="mascaraMesAno(window.event, this)">
*/    
function mascaraMesAno(event, objData)
{
   var ehBackspace;
   var strData = objData.value;
   var nBytes = strData.length;
   

   // funciona, mas deve ser melhorado
   if (navigator.appName == "Netscape")  //mozilla ? netscape
       ehBackspace = (event.which == 8) ? true : false;
   else 
       ehBackspace = (event.keyCode == 8) ? true : false;
   if(nBytes==2 && !ehBackspace)
      objData.value = strData+"/";
   return true;
}

/** Funcao para validar datas. Verifica se a data ? v?lida quanto a:
    dias, meses, anos, dias no mes e ano bissexto
    Se entrar: 010180 -> transforma para 01/01/1980
               010104 -> transforma para 01/01/2004
    @param objeto data
           exibirMsg true - exibe mensagem se estiver invalido
           voltarFoco true - volta o foco para o campo se estiver invalido
           ehObrigatorio true - nao permite campo vazio
    @return true - uma data valida
            false - data invalida
    @see function anoBissexto, function strZero
*/
function validaData(objData, exibirMsg, voltarFoco, ehObrigatorio) 
{
    var strData = objData.value;
    var datePat = /^(\d{1,2})(\/|-|.)(\d{1,2})\2(\d{2,4})$/;
    var retorno = true;
    var strNovaData;
    var dataDiv;
    var dia, mes, ano;

    if (typeof exibirMsg == "undefined") exibirMsg = true;
    if (typeof voltarFoco == "undefined") voltarFoco = true;
    if (typeof ehObrigatorio == "undefined") ehObrigatorio = false;
    
    // transforma sequencia de digitos para o formato data dd/mm/aa (ou aaaa)
    if (strData.length==6)
        strData = strData.substr(0,2)+"/"+strData.substr(2,2)+"/"+strData.substr(4,2);
    else if (strData.length==8 && strData.indexOf('/')==-1)
        strData = strData.substr(0,2)+"/"+strData.substr(2,2)+"/"+strData.substr(4,4);
       
    // testa a data com uma expressao regular
    datadiv = strData.match(datePat);

    if (datadiv==null)
        retorno = false;
    else
    {
        dia = datadiv[1];
        mes = datadiv[3];
        ano = datadiv[4];

        if (ano.length == 2){
        	        	
            ano = (eval(ano) > 30) ? eval(ano) + 1900 : eval(ano) + 2000;
 			
 		}
 		       
        if (ano.length==1 || ano.length==3)
        	retorno = false;
    
        if (dia<1 || dia>31 || mes<1 || mes>12)
            retorno = false;

        if ((mes==4 || mes==6 || mes==9 || mes==11) && dia>30)
            retorno = false;

        if (mes == 2 && (dia > 29 || (dia==29 && !anoBissexto(ano))))
            retorno = false;

        strNovaData = strZero(dia,2)+"/"+strZero(mes,2)+"/"+ano;
        //objData.value = retorno ? strNovaData : '';
        objData.value = strNovaData;
    }
    if (strData.length == 0 && !ehObrigatorio)        
        retorno = true;	
	
    if (!retorno)
    {
        if (exibirMsg)
            alert('Data incorreta\nDigite novamente');
        if (voltarFoco) {           
            //incluído por José André Fernandes, pra resolver o problema do foco
            globalvar = objData;			
			setTimeout("globalvar.focus()",250);			
            
            objData.focus();
            objData.select();
        }
    }
    return (retorno);
}





function validaDataStr(strData, exibirMsg) 
{

var datePat = /^(\d{1,2})(\/|-|.)(\d{1,2})\2(\d{2,4})$/;
var retorno = true;
var strNovaData;
var dataDiv;
var dia, mes, ano;

if (typeof exibirMsg == "undefined") exibirMsg = true;


// transforma sequencia de digitos para o formato data dd/mm/aa (ou aaaa)
if (strData.length==6)
    strData = strData.substr(0,2)+"/"+strData.substr(2,2)+"/"+strData.substr(4,2);
else if (strData.length==8 && strData.indexOf('/')==-1)
    strData = strData.substr(0,2)+"/"+strData.substr(2,2)+"/"+strData.substr(4,4);
   
// testa a data com uma expressao regular
datadiv = strData.match(datePat);

if (datadiv==null)
    retorno = false;
else
{
    dia = datadiv[1];
    mes = datadiv[3];
    ano = datadiv[4];

    if (ano.length == 2){
    	        	
        ano = (eval(ano) > 30) ? eval(ano) + 1900 : eval(ano) + 2000;
			
		}
		       
    if (ano.length==1 || ano.length==3)
    	retorno = false;

    if (dia<1 || dia>31 || mes<1 || mes>12)
        retorno = false;

    if ((mes==4 || mes==6 || mes==9 || mes==11) && dia>30)
        retorno = false;

    if (mes == 2 && (dia > 29 || (dia==29 && !anoBissexto(ano))))
        retorno = false;

    strNovaData = strZero(dia,2)+"/"+strZero(mes,2)+"/"+ano;
 
}

return (retorno);
}

/** Funcao que testa se um ano ? bissexto
    @param ano - valor numerico
    @return true - ano bissexto
*/
function anoBissexto(nAno)
{
    return (nAno % 4 == 0 && (nAno % 100 != 0 || nAno % 400 == 0));
}


/** diasEmFevereiro. Retorna o numero de dias em fevereiro de determinado ano
    @Param: ano numerico
    @return: 29 ou 28
*/
function diasEmFevereiro(nAno)
{
    return (anoBissexto(nAno) ? 29 : 28);
}

/** getDia. retorna o dia de uma strData
    @param: string strData dd/mm/aaaa
    @return: string dd
*/
function getDia(strData)
{  
   return (strData.substr(0,2));
}

/** getMes. retorna o mes de uma strData
    @param: string strData dd/mm/aaaa
    @return: string mm
*/
function getMes(strData)
{
   return (strData.substr(3,2));
}

/** getAno. retorna o ano de uma strData
    @param: string strData dd/mm/aaaa
    @return: string aaaa
*/
function getAno(strData)
{  
   return (strData.substr(6,4));
}

/** getUltimoDiaMes, retorna o ultima dia do mes, dado o mes e o ano
    @param: string mm
    @param: string aaaa
*/
function getUltimoDiaMes(strMes, strAno) {
	var daysInMonth = new Array();
	daysInMonth[1] = 31; 
	daysInMonth[2] = diasEmFevereiro(parseInt(strAno));
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
	return (daysInMonth[parseInt(strMes)]);
}

/** dataInversa. Retorna uma string data no formato aaaammdd
    @param: string data no formato brasileiro dd/mm/aaaa
    @return: string no formato inverso aaaammdd
*/    
function dataInversa(strData)
{
    var aaaa = "0000";
    var mm = "00";
    var dd = "00";

    if (strData.length == 10){
        aaaa = getAno(strData);
        mm   = getMes(strData);
        dd   = getDia(strData);
    }
    return (aaaa + mm + dd);
}


/** Funcao strZero - retorna um string com zeros a esquerda de acordo com o
    comprimento desejado
    @param - nNumero - string ou numero a prenecher com zeros a esquerda
             nBytes - comprimento da string             
    @return - string contendo nNumero com zeros ? esquerda no tamanho desejado.
    @see strZero(5, 4) -> "00005"
*/
function strZero(nNumero,nBytes)
{
   var strRetorno = nNumero.toString();
   if (nNumero.length != nBytes)
   	  var tamNumero = strRetorno.length;
      for (var i=1;i<=(nBytes-tamNumero);i++)
         strRetorno = "0" + strRetorno;
   return (strRetorno);
}

function DataMenor(dataini, datafim) { 

	if (dataini == null || datafim == null || dataini == '' || datafim == ''){
		return true;
	}
		
//	 var date1 = new Date(dataini.substr(6,4), dataini.substr(3,2), dataini.substr(0,2));
//	 var date2 = new Date(datafim.substr(6,4), datafim.substr(3,2), datafim.substr(0,2)); 
	 
	 var ano1 = parseInt(dataini.substr(6,4), 10);
	 var mes1 = parseInt(dataini.substr(3,2), 10);
	 var dia1 = parseInt(dataini.substr(0,2), 10);
	 
	 var ano2 = parseInt(datafim.substr(6,4), 10);
	 var mes2 = parseInt(datafim.substr(3,2), 10);
	 var dia2 = parseInt(datafim.substr(0,2), 10);
	
//	 alert('ano1: ' +  dataini.substr(6,4));
//	 alert('ano1 convertido: ' +  parseInt( dataini.substr(6,4)));
//	 alert('ano2: ' +  datafim.substr(6,4));
//	 alert('ano2 convertido: ' +  parseInt( datafim.substr(6,4)));
//	 
//	 alert('mes1: ' +  dataini.substr(3,2));
//	 alert('mes1 convertido: ' +  parseInt(dataini.substr(3,2), 10));
//	 
//	 alert('mes2: ' +  datafim.substr(3,2));
//	 alert('mes2 convertido: ' +  parseInt(datafim.substr(3,2), 10));
//	 
//	 alert('dia1: ' +  dataini.substr(0,2));
//	 alert('dia1 convertido: ' +  parseInt(dataini.substr(0,2), 10));
//	 alert('dia2: ' +  datafim.substr(0,2));
//	 alert('dia2 convertido: ' +  parseInt(datafim.substr(0,2), 10));
	 
	
	if (ano1<ano2) {			
		return (true);
	}
	else if (ano1==ano2 && mes1<mes2){
		
		return (true);
	}
	else if (ano1==ano2 && mes1==mes2 && dia1<dia2){
		return (true);
	}
	else if (ano1==ano2 && mes1==mes2 && dia1==dia2){
		return (true);
	}
	else {
		return (false);
	}
	
}

//////////////////////As variáveis abaixo são utilizadas pelo popup do calendáio. ///////////

// _fieldDateCalendar: campo edit que irá receber o retorno do dia selecionado no calendário.
var _fieldDateCalendar = new Object();
// _tipoInformacao: variável que diz que tipo de retorno que o calendário deve trazer: se "data" ou "difDias".
// Por padrão, deve retornar data.
var _tipoInformacao = "data";
// _dataComparar: A data que deve ser comparada em número de dias com o dia selecionado no calendário;
var _dataComparar = Date();

// Função abre uma popup do calendário. 
function open_calendar(tipoInf, field, dataComparar,path) {
	var sc = "no";
	var width = "243";
	var height = "225";
	var left = "200";
	var top = "200";
	var menubar = "no";
	var toolbar = "no";
	var statusbar = "no";
	var caracteristicas = "resizable=no";
	caracteristicas = caracteristicas +",toolbar="+ toolbar;
	caracteristicas = caracteristicas +",statusbar"+ statusbar;
	caracteristicas = caracteristicas +",menubar="+ menubar;
	caracteristicas = caracteristicas +",scrollbars="+ sc;
	caracteristicas = caracteristicas +",width="+ width;
	caracteristicas = caracteristicas +",height="+ height;
	caracteristicas = caracteristicas +",left="+ left +",top="+ top;
	_fieldDateCalendar = field;
	_tipoInformacao = tipoInf;
	if (_tipoInformacao == "difDias") {
		_dataComparar = dataComparar;
	}
	if (path == null) {
		calendarWindow = window.open("../../calendario/calendario.jsp", "calendario", caracteristicas);
	} else {
		calendarWindow = window.open(path, "calendario", caracteristicas);
	}
}

//Função que abre uma popup do calendário com validação em relação a um campo data passado como parametro(ex:data selecionada nao pode ser maior que a data limite). 
function open_calendar_comValidacao(tipoInf, field, dataComparar, path, label) {
	var sc = "no";
	var width = "243";
	var height = "225";
	var left = "200";
	var top = "200";
	var menubar = "no";
	var toolbar = "no";
	var statusbar = "no";
	var caracteristicas = "resizable=no";
	caracteristicas = caracteristicas +",toolbar="+ toolbar;
	caracteristicas = caracteristicas +",statusbar"+ statusbar;
	caracteristicas = caracteristicas +",menubar="+ menubar;
	caracteristicas = caracteristicas +",scrollbars="+ sc;
	caracteristicas = caracteristicas +",width="+ width;
	caracteristicas = caracteristicas +",height="+ height;
	caracteristicas = caracteristicas +",left="+ left +",top="+ top;
	_fieldDateCalendar = field;
	_tipoInformacao = tipoInf;
	//guarda o label do campo data que vai ser comparado com a data selecionada
	_labelCampo = label;
	//flag que indica se vai haver a validação ou não com o campo data
	_comValidacao = true;
	if (_tipoInformacao == "difDias") {
		_dataComparar = dataComparar;
	}
	if (path == null) {
		calendarWindow = window.open("../../calendario/calendario.jsp", "calendario", caracteristicas);
	} else {
		calendarWindow = window.open(path, "calendario", caracteristicas);
	}
}

