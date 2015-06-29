function troca(str,strsai,strentra)
{
		while(str.indexOf(strsai)>-1)
		{
				str = str.replace(strsai,strentra);
		}
		return str;
}
		
function formataMoeda(campo,teclapres, caracter)
{
		if(teclapres == null || teclapres == "undefined")
		{
				var tecla = -1;
		}
		else
		{
				var tecla = teclapres.keyCode;
		}

    	if(caracter == null || caracter == "undefined")
    		{
			caracter = ".";
    		}

    		vr = campo.value;
    		if(caracter != "")
    		{
				vr = troca(vr,caracter,"");
    		}
    		vr = troca(vr,"/","");
    		vr = troca(vr,",","");
    		vr = troca(vr,".","");

    		tam = vr.length;
    		if(tecla > 0)
    		{
				if(tecla != 8)
				{
					tam = vr.length + 1;
				}
	    		
    			if(tecla == 8)
    			{
    				tam = tam - 1;
    			}
    		}
    		if(tecla == -1 || tecla == 8 || tecla >= 48 && tecla <= 57 || tecla >= 96 && tecla <= 105)
    		{
    			if(tam <= 2)
    			{ 
					campo.value = vr;
				}
    	 		if((tam > 2) && (tam <= 5))
    	 		{
					campo.value = vr.substr(0, tam - 2) + ',' + vr.substr(tam - 2, tam);
				}
    	 		if((tam >= 6) && (tam <= 8))
    	 		{
					campo.value = vr.substr(0, tam - 5) + caracter + vr.substr(tam - 5, 3) + ',' + vr.substr(tam - 2, tam);
				}
    	 		if((tam >= 9) && (tam <= 11))
    	 		{
					campo.value = vr.substr(0, tam - 8) + caracter + vr.substr(tam - 8, 3) + caracter + vr.substr(tam - 5, 3) + ',' + vr.substr(tam - 2, tam);
				}
    	 		if((tam >= 12) && (tam <= 14))
    	 		{
					campo.value = vr.substr(0, tam - 11) + caracter + vr.substr(tam - 11, 3) + caracter + vr.substr(tam - 8, 3) + caracter + vr.substr(tam - 5, 3) + ',' + vr.substr(tam - 2, tam);
				}
    	 		if((tam >= 15) && (tam <= 17))
    	 		{
					campo.value = vr.substr(0, tam - 14) + caracter + vr.substr(tam - 14, 3) + caracter + vr.substr(tam - 11, 3) + caracter + vr.substr(tam - 8, 3) + caracter + vr.substr(tam - 5, 3) + ',' + vr.substr(tam - 2, tam);
				}
    		}
	}