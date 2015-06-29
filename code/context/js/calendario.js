var hoje = new Date();
meses="01|02|03|04|05|06|07|08|09|10|11|12";
dias="01|02|03|04|05|06|07|08|09";
var dia = hoje.getDate();
if (dia>=1 && dia<10) dia = dias.split("|")[dia-1];
var mes = hoje.getMonth();
var ano = hoje.getFullYear();



var yearNow			= hoje.getFullYear();
var monthNow		= hoje.getMonth();
var dayNow			= dia;
var mMaxToDisplay	= 11; // TODO ver depois com mais detalhes...
var sector			= 1;
var default_month	= 1;
var dayMax			= 31;

var mCurrent 		= monthNow
var yCurrent 		= yearNow
var mOptionsIndex 	= 0
var swap_prior_ok	= true
var swap_next_ok	= true

													
function populate_calendar(mNewRequest, yNewRequest, month_offset)
{

	mNewRequest =  eval(mNewRequest) + eval(month_offset)
	yNewRequest =  eval(yNewRequest)

	if (mNewRequest > 12)
	{
    	mNewRequest = mNewRequest - 12
    	yNewRequest	= yNewRequest + 1
	}

	if (mNewRequest < 1)
	{
    	mNewRequest = mNewRequest + 12
    	yNewRequest = yNewRequest -1
	}

	var daysOfMonth = findDaysOfMonth(mNewRequest,yNewRequest)
	var monthName	= findMonthName(mNewRequest)

	var firstDay = new Date(yNewRequest, mNewRequest - 1, 1);
	var startBoxIndex = firstDay.getDay();

	mOptionsIndex += month_offset

	
	if(true)
	{
		mCurrent = mNewRequest
		yCurrent = yNewRequest

		fillCalHeader(monthName,yNewRequest)
		fillCalBoxes(startBoxIndex,daysOfMonth)

		//if (mOptionsIndex > 11)
		//{
		//	mOptionsIndex -= 12
		//}

		if (mOptionsIndex < 0)
		{
			mOptionsIndex += 12
		}
	}
}

function findDaysOfMonth(mNewRequest,yNewRequest) {
	var daysInMonthArray     = new Array()
		daysInMonthArray[0]  = 31
		daysInMonthArray[1]  = 28
		daysInMonthArray[2]  = 31
		daysInMonthArray[3]  = 30
		daysInMonthArray[4]  = 31
		daysInMonthArray[5]  = 30
		daysInMonthArray[6]  = 31
		daysInMonthArray[7]  = 31
		daysInMonthArray[8]  = 30
		daysInMonthArray[9]  = 31
		daysInMonthArray[10] = 30
		daysInMonthArray[11] = 31

	if (((yNewRequest % 4 == 0) && (yNewRequest % 100 != 0)) || (yNewRequest % 400 == 0)) {
		daysInMonthArray[1] = 29;
	}

	mNewResult = daysInMonthArray[mNewRequest - 1]
	return mNewResult;
}

function findMonthName(mNewRequest) {
	var monthNamesArray	= new Array()
		monthNamesArray[0]	= 'Janeiro'
		monthNamesArray[1]	= 'Fevereiro'
		monthNamesArray[2]	= 'Março'
		monthNamesArray[3]	= 'Abril'
		monthNamesArray[4]	= 'Maio'
		monthNamesArray[5]	= 'Junho'
		monthNamesArray[6]	= 'Julho'
		monthNamesArray[7]	= 'Agosto'
		monthNamesArray[8]	= 'Setembro'
		monthNamesArray[9]	= 'Outubro'
		monthNamesArray[10]	= 'Novembro'
		monthNamesArray[11]	= 'Dezembro'

	return monthNamesArray[mNewRequest-1];
}

function fillCalBoxes(startBoxIndex,daysOfMonth) {
	var dOffset	= 0
	var dayNum	= 1
	var dMax;

	// Comment out to stop scratching out past dates within current month
	//
	if ( (eval(mCurrent) == eval(monthNow)) && (eval(yCurrent) == eval(yearNow)) )
	{
		dOffset = eval(dayNow)-1
	}

	// Comment out to stop scratching out unavailable days within maximum month
	//
	if ( eval(mCurrent % 12) == ( ( eval(mMaxToDisplay) - 1 + eval(monthNow) ) % 12 ) )		// modulus in case mCurrent:12(Dec), mMaxToDisplay:1(Jan)
	{
		dMax = eval(dayMax) - 1
	}

	for (var i=0; i <=41; i++)
	{
		if ( (i+1 > startBoxIndex ) && (i+1 <= (startBoxIndex + daysOfMonth) ) )
		{
/*
			if ( i < (startBoxIndex + dOffset) )
			{
				document.myForm["box" + i].value	= "--"
				document.myForm["box" + i].disabled	= true
			}
			else if ( i > (startBoxIndex + dMax) )
			{
				document.myForm["box" + i].value	= "--"
				document.myForm["box" + i].disabled	= true
			}
			
			else */
			if ( i < (startBoxIndex + daysOfMonth ) )
			{
				if (dayNum < 10)
				{
					//To properly size squished form buttons in IE4&5
					document.myForm["box" + i].value = " " + dayNum
					document.myForm["box" + i].disabled	= false
				}
				else
				{
					document.myForm["box" + i].value = dayNum
					document.myForm["box" + i].disabled	= false
				}
			}

			dayNum++
		}
		else
		{
			document.myForm["box" + i].value 	= "  "
			document.myForm["box" + i].disabled	= true
		}
	}
}

function fillCalHeader(monthName,yNewRequest)
{
	document.myForm.date.value = monthName + " " + yNewRequest
}

function captureDaySelection(dNewRequest) {
	if ((dNewRequest == " ") || (dNewRequest == "  ") || (dNewRequest == "--")) {
		document.myForm.daySelected.value 		= ""	 // clear these
		document.myForm.monthYearSelected.value = ""     // two fields
	} else {
		document.myForm.daySelected.value 		= dNewRequest
		document.myForm.monthYearSelected.value = mOptionsIndex
	}

	submitDate();

	if (('true' == 'true') && (dNewRequest != " ") && (dNewRequest != "  ") && (dNewRequest != "--")) {
		closeWindow();
	}
}

function ajustaDia( dia ) {
	dias="01|02|03|04|05|06|07|08|09";
	if (dia>=1 && dia<10) dia = dias.split("|")[dia-1];
	return dia;
}

function ajustaMes( mes ) {
	meses="00|01|02|03|04|05|06|07|08|09|10|11|12";
	mes = meses.split("|")[mes];
	return mes;
}

function submitDate( )
{
	
	if (!opener.closed) {
		// se retornar data
		if (opener._tipoInformacao == "data") {		
			var dataFinal = ajustaDia(document.myForm.daySelected.value) + "/";
			dataFinal = dataFinal + ajustaMes(mCurrent) + "/";
			dataFinal = dataFinal + yCurrent;
			opener._fieldDateCalendar.value	= dataFinal;
		} else {
			// se retornar a diferença de dias
			if (opener._dataComparar != "") {
				var dataFinal = new Date(opener._dataComparar.substr(6, 4), opener._dataComparar.substr(3, 2) - 1, opener._dataComparar.substr(0, 2));
				var dataSelecionada = new Date(yCurrent, ajustaMes(mCurrent)-1, ajustaDia(document.myForm.daySelected.value));
				
				// se houver uma validação 
				if(opener._comValidacao == true) {
					
					// se a validação for : se a data a comparar for diferente que a data selecionada e se a data a comparar for menor que a data selecionada 
					if (dataFinal.getTime()!= dataSelecionada.getTime() && dataFinal.getTime()<dataSelecionada.getTime()) {
							alert("Data selecionada é posterior a data do campo " + opener._labelCampo +"." );
					} else {
						var difDias = diferencaDias(dataFinal, dataSelecionada);  
						opener._fieldDateCalendar.value	= difDias;
					}
				
				//se nao houver validacao
				} else {
					var difDias = diferencaDias(dataFinal, dataSelecionada);  
					opener._fieldDateCalendar.value	= difDias;
				}
				
			// se nao houver uma data para comparar e for com validação, o sistema avisa que é necessário antes preencher o campo data limite do item 
			// para o calculo da diferença de dias
			} else if(opener._comValidacao == true) {
				alert("Preencha antes o campo " + opener._labelCampo + " para calcular a diferença dos dias." );
			} else {
				opener._fieldDateCalendar.value	= "";
			}
		}
	}
}


function diferencaDias(data1, data2){
	var dif =
        Date.UTC(data1.getYear(),data1.getMonth(),data1.getDate(),0,0,0) - 
      	Date.UTC(data2.getYear(),data2.getMonth(),data2.getDate(),0,0,0);
    return Math.abs((dif / 1000 / 60 / 60 / 24));
}


function closeWindow(){
	close();
}

/*
if (document.images)
{
	prior_month_icon_cold		= new Image();
	prior_month_icon_cold.src 	= '/img/b-cal-l-up.gif';
	prior_month_icon_warm		= new Image();
	prior_month_icon_warm.src	= '/img/b-cal-l-db.gif';
	prior_month_icon_hot		= new Image();
	prior_month_icon_hot.src	= '/img/b-cal-l-dy.gif';
	prior_month_icon_frozen		= new Image();
	prior_month_icon_frozen.src	= '/img/b-cal-l-xx.gif';

	next_month_icon_cold		= new Image();
	next_month_icon_cold.src 	= '/img/b-cal-r-up.gif';
	next_month_icon_warm		= new Image();
	next_month_icon_warm.src	= '/img/b-cal-r-db.gif';
	next_month_icon_hot			= new Image();
	next_month_icon_hot.src		= '/img/b-cal-r-dy.gif';
	next_month_icon_frozen		= new Image();
	next_month_icon_frozen.src	= '/img/b-cal-r-xx.gif';
}
*/

function swap_icon(icon_direction, icon_state)
{
	if (document.images)
	{
		if (((icon_direction == 'prior') && (swap_prior_ok == true))
		 || ((icon_direction == 'next') && (swap_next_ok == true)))
		{
			icon = eval(icon_direction + "_month_icon_" + icon_state + ".src");
			document.images[icon_direction].src = icon;
		}
	}
}

function dataOntem() {
    hoje = new Date();
    meses="01|02|03|04|05|06|07|08|09|10|11|12";
    dias="01|02|03|04|05|06|07|08|09";
    dia = hoje.getDate()-1;
    if (dia>=1 && dia<10) dia = dias.split("|")[dia-1];
    mes = meses.split("|")[hoje.getMonth()];
    ano = hoje.getYear();
    if (ano<1900) ano+=1900;
    return dia +"/"+ mes +"/"+ ano;
}
