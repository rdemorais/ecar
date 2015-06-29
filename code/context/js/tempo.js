function window_load(){
	flag=1;
	aviso2();
	aviso();
	sivamtime(); 		
}

function aviso(){ 
if (flag==2){ 
		alert('Atenção: Esta sessão cairá em 2 minutos.\nSalve suas alterações.'); 
} else {
	if (flag != 3) setTimeout('aviso()',<%=(session.getMaxInactiveInterval()-120) *1000%>); // tempo - 2 Minutos 
}
	flag=2; 
} 

function aviso2(){ 
	if (flag==2){ 
		HoraHor = 0; 
  		HoraMin = 0; 
  		HoraSec = 0;
  		document.getElementById("tempoExp").innerHTML = "00:00:00"; 
		alert('Esta sessão foi encerrada!!!\nFaça o login novamente.');              
		flag=3; 
	} else { 
		setTimeout('aviso2()',<%=session.getMaxInactiveInterval()*1000%>); 
	} 
} 
	
	// Exibe uam hora no formato HH:MM:SS
	function sivamtime() 
	{
  		//now=horaIni; 
  		  		  		
  		//hour=now.getHours();
  		//min=now.getMinutes();
  		//sec=now.getSeconds();

  		//if (min<=9) { min="0"+min; }
  		//if (sec<=9) { sec="0"+sec; }
  		//if (hour<10) { hour="0"+hour; }
  		  		  		  		
  		var tempoServ = <%=session.getMaxInactiveInterval()%>; 
  		if (tempoServ >= 3600)
  		{
  		   HoraHor = parseInt(tempoServ / 3600); 
  		   tempoServ =  tempoServ % 3600; 
  		}  	
  		else
  		  HoraHor = 0; 	
  		HoraMin = tempoServ / 60;  		  		  		
  		HoraSec = 0;
  	    		  		
  		document.getElementById("tempoExp").innerHTML = (HoraHor<10?"0"+HoraHor:HoraHor) + ":" + (HoraMin<=9?"0"+HoraMin:HoraMin) + ":" + (HoraSec<9?"0"+HoraSec:HoraSec);   		
  		
  		
  		
	}	
	
	// Esta Função soma horas
	function sivamtime3(addH, addM, addS) 
	{
        
  		if ((HoraHor > 0) || (HoraMin > 0) || (HoraSec > 0))
  		{
  			hour3 = HoraHor - parseInt(addH);
	  		min3 =  HoraMin  - parseInt(addM);
  			sec3 =  HoraSec - parseInt(addS);

  			if (sec3<0) { sec3 = 59 ; min3 = parseInt(min3)-1; }
	  		if (min3<0) { min3 = 59; hour3 = parseInt(hour3)-1; }
	  		if (hour3<=0) { hour3 = 0; }
  		
  			if (sec3<=9) { sec3="0"+sec3; }
	  		if (min3<=9) { min3="0"+min3; }
	  		if (hour3<=9) { hour3="0"+hour3; }
  		  		
  			HoraHor = hour3; 
	  		HoraMin = min3; 
	  		HoraSec = sec3; 
  		  		
  		    document.getElementById("tempoExp").innerHTML = hour3 + ":" + min3 + ":" + sec3;   		
  			setTimeout("sivamtime3(0,0,1)", 1000);  		  		
  	    }
  		
	}

	 