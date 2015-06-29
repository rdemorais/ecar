/*
 Fun??o para valida??o de e-mail.
	@return - True para email v?lido
*/
function validaEmail(email)
{
	
	var CaracValid = "_-.@0123456789qwertyuiopasdfghjklzxcvbnmQWERTYUIOPASDFGHJKLZXCVBNM";
	var tam = email.length;
	var valido = true;
	var pos1 = email.indexOf('@');
	var pos2 = email.indexOf('.');
	var pos3 = email.indexOf('.',pos1);
		
	for (i = 0;  i < tam;  i++)
	{
		ch = email.charAt(i);
		for (j = 0;  j < CaracValid.length;  j++)
		if (ch == CaracValid.charAt(j))
			break;
		if (j == CaracValid.length)
		{
			valido = false;
			break;
		}    
	}
			
	if((pos1 == -1) || (pos1 == 0) )
	{
		valido = false;
	}
	else
	{		
		if(email.indexOf('@',pos1+1)!=-1)
		{
			valido = false;
		}
	}
		
	if(tam<=pos1+1)
	{
		valido = false;
	}
		
	if(pos3 == -1)
	{
		valido = false;
	}
		
	if(tam<=pos3+1)
	{
		valido = false;
	}
	
	if (tam < pos1+4) 	valido = false;
		
	if(valido)
	{
		return(true);
	}
	else
	{
		return(false);
	}
}