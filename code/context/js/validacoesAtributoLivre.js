function isBlank(val){
	if(val==null){return true;}
	for(var i=0;i<val.length;i++) {
		if ((val.charAt(i)!=' ')&&(val.charAt(i)!="\t")&&(val.charAt(i)!="\n")&&(val.charAt(i)!="\r")){return false;}
		}
	return true;
	}

function isDigit(num) {
	if (num.length>1){return false;}
	var string="1234567890";
	if (string.indexOf(num)!=-1){return true;}
	return false;
	}
	
function isIntegerValid(obj, nome){    	
	val = obj.value;	
	for(var i=0;i<val.length;i++){
		if(!isDigit(val.charAt(i))){
			alert("O campo " + nome + " precisa ser inteiro!");
			//obj.focus();
			globalvar = obj;			
			setTimeout("globalvar.focus()",250);			
			return false;
		}
	}
	return true;
	}

function isNumericValid(obj, nome){
	val = obj.value;	
	if(trim(val)!=''){			
		if(!(val.match(/^[0-9]+(,[0-9]+)*$/))){			
			alert("O campo " + nome + " precisa ser um n�mero real v�lido");
			globalvar = obj;			
			setTimeout("globalvar.focus()",250);					
			return false;
		}
	}
	
	return true;
}

function isMoedaValid(obj, nome){
	val = obj.value;	
	if(trim(val)!=''){			
		if(!isValidMoeda(val)){			
			alert("O campo " + nome + " precisa ser um campo de moeda v�lido");
			globalvar = obj;			
			setTimeout("globalvar.focus()",250);					
			return false;
		}
	}	
	return true;
}
function isValidMoeda(str){
	for(var i=0;i<str.length;i++){
		if(!isValidCharMoeda(str.charAt(i))){return false;}
		}
	return true;
	}

function isValidCharMoeda(c) {
	if (c.length>1){return false;}
	var string="0123456789,.";
	if (string.indexOf(c)!=-1){return true;}
	return false;
	}

/**
 * Mascara de Moeda 
 **/
function mascaraMoeda(objTextBox, SeparadorMilesimo, SeparadorDecimal,e,maxLen){
    var sep = 0;
    var key = '';
    var i = j = 0;
    var len = len2 = 0;
    var strCheck = '0123456789';
    var aux = aux2 = '';    
    var whichCode = e ? (e.which ? e.which : e.keyCode) :e.keyCode;       
    if (whichCode == 8 || whichCode == 0) return true; // Alterado por Alisson
    key = String.fromCharCode(whichCode); // Valor para o c�digo da Chave
    if (strCheck.indexOf(key) == -1) return false; // Chave inv�lida
    len = objTextBox.value.length;        
    for(i = 0; i < len; i++)
        if ((objTextBox.value.charAt(i) != '0') && (objTextBox.value.charAt(i)
				!= SeparadorDecimal)) break;
    aux = '';
    for(; i < len; i++)
        if (strCheck.indexOf(objTextBox.value.charAt(i))!=-1) aux +=
				objTextBox.value.charAt(i);
    aux += key;
    len = aux.length;
    if(len == maxLen) return false;// Alterado por Alisson
    if (len == 0) objTextBox.value = '';
    if (len == 1) objTextBox.value = '0'+ SeparadorDecimal + '0' + aux;
    if (len == 2) objTextBox.value = '0'+ SeparadorDecimal + aux;
    if (len > 2) {
        aux2 = '';
        for (j = 0, i = len - 3; i >= 0; i--) {
            if (j == 3) {
                aux2 += SeparadorMilesimo;
                j = 0;
            }
            aux2 += aux.charAt(i);
            j++;
        }
        objTextBox.value = '';
        len2 = aux2.length;
        for (i = len2 - 1; i >= 0; i--)
        objTextBox.value += aux2.charAt(i);
        objTextBox.value += SeparadorDecimal + aux.substr(len - 2, len);
    }
    return false;
}



//*******************************IN�CIO M�SCARA CPF e CNPJ**********************************************
//adiciona mascara ao CPF
function MascaraCPF(cpf,event){
    if(mascaraInteiro(event)==false){
        event.returnValue = false;        
    }    
    return formataCampo(cpf, '000.000.000-00', event);
}

//adiciona mascara ao CNPJ
function MascaraCNPJ(cnpj,event){
    if(mascaraInteiro(event)==false){
        event.returnValue = false;        
    }    
    return formataCampo(cnpj, '00.000.000/0000-00', event);
}

//valida numero inteiro com mascara
function mascaraInteiro(event){
    
    var keyCode = event ? (event.which ? event.which : event.keyCode) :event.keyCode;    
    if (keyCode < 48 || keyCode > 57){
        event.returnValue = false;        
        return false;
    }    
    return true;
}
//formata de forma generica os campos
function formataCampo(campo, Mascara, evento) {
    var boleanoMascara;
    
    var Digitato = evento.keyCode;
    exp = /\-|\.|\/|\(|\)| /g
    campoSoNumeros = campo.value.toString().replace( exp, "" );
  
    var posicaoCampo = 0;    
    var NovoValorCampo="";
    var TamanhoMascara = campoSoNumeros.length;;
    
    if (Digitato != 8) { // backspace
        for(i=0; i<= TamanhoMascara; i++) {
            boleanoMascara  = ((Mascara.charAt(i) == "-") || (Mascara.charAt(i) == ".")
                                || (Mascara.charAt(i) == "/"))
            boleanoMascara  = boleanoMascara || ((Mascara.charAt(i) == "(")
                                || (Mascara.charAt(i) == ")") || (Mascara.charAt(i) == " "))
            if (boleanoMascara) {
                NovoValorCampo += Mascara.charAt(i);
                  TamanhoMascara++;
            }else {
                NovoValorCampo += campoSoNumeros.charAt(posicaoCampo);
                posicaoCampo++;
              }           
          }    
        campo.value = NovoValorCampo;
          return true;
    }else {
        return true;
    }
}
//*******************************************FIM M�SCARA CPF e CNPJ************************************************

var NUM_DIGITOS_CPF  = 11;
var NUM_DIGITOS_CNPJ = 14;
var NUM_DGT_CNPJ_BASE = 8;


/**
 * Adiciona m�todo lpad() � classe String.
 * Preenche a String � esquerda com o caractere fornecido,
 * at� que ela atinja o tamanho especificado.
 */
String.prototype.lpad = function(pSize, pCharPad)
{
	var str = this;
	var dif = pSize - str.length;
	var ch = String(pCharPad).charAt(0);
	for (; dif>0; dif--) str = ch + str;
	return (str);
} //String.lpad


/**
 * Adiciona m�todo trim() � classe String.
 * Elimina brancos no in�cio e fim da String.
 */
String.prototype.trim = function()
{
	return this.replace(/^\s*/, "").replace(/\s*$/, "");
} //String.trim


/**
 * Elimina caracteres de formata��o e zeros � esquerda da string
 * de n�mero fornecida.
 * @param String pNum
 *      String de n�mero fornecida para ser desformatada.
 * @return String de n�mero desformatada.
 */
function unformatNumber(pNum)
{
	return String(pNum).replace(/\D/g, "").replace(/^0+/, "");
} //unformatNumber


/**
 * Formata a string fornecida como CNPJ ou CPF, adicionando zeros
 * � esquerda se necess�rio e caracteres separadores, conforme solicitado.
 * @param String pCpfCnpj
 *      String fornecida para ser formatada.
 * @param boolean pUseSepar
 *      Indica se devem ser usados caracteres separadores (. - /).
 * @param boolean pIsCnpj
 *      Indica se a string fornecida � um CNPJ.
 *      Caso contr�rio, � CPF. Default = false (CPF).
 * @return String de CPF ou CNPJ devidamente formatada.
 */
function formatCpfCnpj(pCpfCnpj, pUseSepar, pIsCnpj)
{
	if (pIsCnpj==null) pIsCnpj = false;
	if (pUseSepar==null) pUseSepar = true;
	var maxDigitos = pIsCnpj? NUM_DIGITOS_CNPJ: NUM_DIGITOS_CPF;
	var numero = unformatNumber(pCpfCnpj);

	numero = numero.lpad(maxDigitos, '0');
	if (!pUseSepar) return numero;

	if (pIsCnpj)
	{
		reCnpj = /(\d{2})(\d{3})(\d{3})(\d{4})(\d{2})$/;
		numero = numero.replace(reCnpj, "$1.$2.$3/$4-$5");
	}
	else
	{
		reCpf  = /(\d{3})(\d{3})(\d{3})(\d{2})$/;
		numero = numero.replace(reCpf, "$1.$2.$3-$4");
	}
	return numero;
} //formatCpfCnpj


/**
 * Calcula os 2 d�gitos verificadores para o n�mero-efetivo pEfetivo de
 * CNPJ (12 d�gitos) ou CPF (9 d�gitos) fornecido. pIsCnpj � booleano e
 * informa se o n�mero-efetivo fornecido � CNPJ (default = false).
 * @param String pEfetivo
 *      String do n�mero-efetivo (SEM d�gitos verificadores) de CNPJ ou CPF.
 * @param boolean pIsCnpj
 *      Indica se a string fornecida � de um CNPJ.
 *      Caso contr�rio, � CPF. Default = false (CPF).
 * @return String com os dois d�gitos verificadores.
 */
function dvCpfCnpj(pEfetivo, pIsCnpj)
{
	if (pIsCnpj==null) pIsCnpj = false;
	var i, j, k, soma, dv;
	var cicloPeso = pIsCnpj? NUM_DGT_CNPJ_BASE: NUM_DIGITOS_CPF;
	var maxDigitos = pIsCnpj? NUM_DIGITOS_CNPJ: NUM_DIGITOS_CPF;
	var calculado = formatCpfCnpj(pEfetivo, false, pIsCnpj);
	calculado = calculado.substring(2, maxDigitos);
	var result = "";

	for (j = 1; j <= 2; j++)
	{
		k = 2;
		soma = 0;
		for (i = calculado.length-1; i >= 0; i--)
		{
			soma += (calculado.charAt(i) - '0') * k;
			k = (k-1) % cicloPeso + 2;
		}
		dv = 11 - soma % 11;
		if (dv > 9) dv = 0;
		calculado += dv;
		result += dv
	}

	return result;
} //dvCpfCnpj

/**
 * Testa se a String cpf fornecida est� no padr�o xxx.xxx.xxx-xx.
  * @param String cpf
 *      String fornecida para ser testada.
 * @return <code>true</code> se a String fornecida estiver no padr�o.
 */
function isFormatDefaultCpf(cpf){	
	if(trim(cpf)=='')
		return true;	
	// formato padr�o de CPF xxx.xxx.xxx-xx	
	if(!(cpf.charAt(3)=='.' && cpf.charAt(7)=='.' && cpf.charAt(11)=='-'))
		return false;			
	return true;
}

/**
 * Testa se a String cnpj fornecida est� no padr�o XX.XXX.XXX/XXXX-XX.
  * @param String cnpj
 *      String fornecida para ser testada.
 * @return <code>true</code> se a String fornecida estiver no padr�o.
 */
function isFormatDefaultCnpj(cnpj){	
	if(trim(cnpj)=='')
		return true;	
	// formato padr�o de cnpj XX.XXX.XXX/XXXX-XX.	
	if(!(cnpj.charAt(2)=='.' && cnpj.charAt(6)=='.' && cnpj.charAt(10)=='/' && cnpj.charAt(15)=='-'))
		return false;			
	return true;
}

/**
 * Testa se a String pCpf fornecida � um CPF v�lido.
 * Qualquer formata��o que n�o seja algarismos � desconsiderada.
 * @param String pCpf
 *      String fornecida para ser testada.
 * @return <code>true</code> se a String fornecida for um CPF v�lido.
 */
function isCpf(obj,nome)
{	pCpf = obj.value;	
	var numero = formatCpfCnpj(pCpf, false, false);
	var base = numero.substring(0, numero.length - 2);
	var digitos = dvCpfCnpj(base, false);
	var algUnico, i;
	
	var emissorevento;
	
	//Verificar se o CPF t� no padr�o xxx.xxx.xxx-xx
	if(isFormatDefaultCpf(pCpf)==false || pCpf.length > 14){
   		alert("O campo " + nome + " deve estar no padr�o xxx.xxx.xxx-xx");	
		globalvar = obj;			
		setTimeout("globalvar.focus()",250);			    		
		return false;	
	}
	
	if(trim(obj.value)=='')
		return true;
	
	// Valida d�gitos verificadores
	if (numero != base + digitos){
   		alert("O campo " + nome + " n�o � um CPF v�lido!");	
		globalvar = obj;			
		setTimeout("globalvar.focus()",250);			    
		
		return false;
	} 

	/* N�o ser�o considerados v�lidos os seguintes CPF:
	 * 000.000.000-00, 111.111.111-11, 222.222.222-22, 333.333.333-33, 444.444.444-44,
	 * 555.555.555-55, 666.666.666-66, 777.777.777-77, 888.888.888-88, 999.999.999-99.
	 */
	algUnico = true;
	for (i=1; i<NUM_DIGITOS_CPF; i++)
	{
		algUnico = algUnico && (numero.charAt(i-1) == numero.charAt(i));
	}
	
	if(algUnico==true){
   		alert("O campo " + nome + " n�o � um CPF v�lido!");	
		globalvar = obj;			
		setTimeout("globalvar.focus()",250);			    	
	}
	
	return (!algUnico);
} //isCpf


/**
 * Testa se a String pCnpj fornecida � um CNPJ v�lido.
 * Qualquer formata��o que n�o seja algarismos � desconsiderada.
 * @param String pCnpj
 *      String fornecida para ser testada.
 * @return <code>true</code> se a String fornecida for um CNPJ v�lido.
 */
function isCnpj(obj,nome)
{
	pCnpj = obj.value;
	var numero = formatCpfCnpj(pCnpj, false, true);
	var base = numero.substring(0, NUM_DGT_CNPJ_BASE);
	var ordem = numero.substring(NUM_DGT_CNPJ_BASE, 12);
	var digitos = dvCpfCnpj(base + ordem, true);
	var algUnico;

	//Verificar se o CNPJ t� no padr�o XX.XXX.XXX/XXXX-XX
	if(isFormatDefaultCnpj(pCnpj)==false){
   		alert("O campo " + nome + " deve estar no padr�o XX.XXX.XXX/XXXX-XX");	
		globalvar = obj;			
		setTimeout("globalvar.focus()",250);			    		
		return false;	
	}
	
	if(trim(obj.value)=='')
		return true;

	// Valida d�gitos verificadores
	if (numero != base + ordem + digitos){
	   	alert("O campo " + nome + " n�o � um CNPJ v�lido!");	
		globalvar = obj;			
		setTimeout("globalvar.focus()",250);			    		
		return false;	
	} 

	/* N�o ser�o considerados v�lidos os CNPJ com os seguintes n�meros B�SICOS:
	 * 11.111.111, 22.222.222, 33.333.333, 44.444.444, 55.555.555,
	 * 66.666.666, 77.777.777, 88.888.888, 99.999.999.
	 */
	algUnico = numero.charAt(0) != '0';
	for (i=1; i<NUM_DGT_CNPJ_BASE; i++)
	{
		algUnico = algUnico && (numero.charAt(i-1) == numero.charAt(i));
	}
	if(algUnico==true){
   		alert("O campo " + nome + " n�o � um CNPJ v�lido!");	
		globalvar = obj;			
		setTimeout("globalvar.focus()",250);
		return false;			    	
	}		

	/* N�o ser� considerado v�lido CNPJ com n�mero de ORDEM igual a 0000.
	 * N�o ser� considerado v�lido CNPJ com n�mero de ORDEM maior do que 0300
	 * e com as tr�s primeiras posi��es do n�mero B�SICO com 000 (zeros).
	 * Esta cr�tica n�o ser� feita quando o no B�SICO do CNPJ for igual a 00.000.000.
	 */
	if (ordem == "0000") return false;
	return (base == "00000000"
		|| parseInt(ordem, 10) <= 300 || base.substring(0, 3) != "000");
} //isCnpj


//********************************Valida��o de Email********************************************

var mascara = /^[\w-]+(\.[\w-]+)*@(([A-Za-z\d][A-Za-z\d-]{0,61}[A-Za-z\d]\.)+[A-Za-z]{2,6}|\[\d{1,3}(\.\d{1,3}){3}\])$/;

function isEmail(obj,nome)
{	email = obj.value;
	if (!(mascara.test(email)) && email != null && email != "") {
   		alert("O campo " + nome + " n�o � um Email v�lido!");	
		globalvar = obj;			
		setTimeout("globalvar.focus()",250);
		return false;
	}
} // isEmail

//************************Func�es complementares para o atributo livre de IMAGEM******************

function abrirPopUpUpload(nome, labelCampo){
abreJanela("../../usuarios/usuarios/popUpUpload.jsp?nomeCampo="+nome+"&labelCampo="+labelCampo+"&excluir=",
			500, 100, nome);
}

function abrirPopUpUpload(nome, labelCampo, excluir){
	abreJanela("../../usuarios/usuarios/popUpUpload.jsp?nomeCampo="+nome+"&labelCampo="+labelCampo+"&excluir="+excluir,
				500, 100, nome);
}

/**
 * Valida o conteudo digitado no campo de acordo com a m�scara. 
 * @param campoTela
 * @param mascaraAtual
 * @param caracterMascaraMes
 * @return
 */
function validaMascaraEditavel(campoTela,mascaraAtual,descricaoCampo,caracterMascaraMes,caracterMascaraAno,caracterMascaraSequencial,valorSequencialInformado,mascaraArmazenada) {

	var valorCampo = campoTela.value; 
	var i;
	var caracterMascara,caracterAtualValorCampo;
	var mes,ano;
	var retorno = true;
	

	
	//if (deveValidar(campoTela.name)){//O metodo deveValidar est� no arquivo frmPadraoItemEstrutura.js e s� deve fazer a valida��o da mascara caso o usu�rio clique no bot�o editar, caso contr�rio n�o dever� validar. 
		if (valorCampo == "") {//Se o campo estiver em branco informa que a m�scara e inv�lida.
			alert ("Campo "+descricaoCampo+" possui o formato "+mascaraAtual);
			retorno = false;
		} else {
			if (mascaraAtual.length != valorCampo.length) {//Se o conteudo digitado for maior que o conte�do da m�scara erro
				//TODO a altera��o de atributos livres ainda n�o est� disponivel qdo o atributo livre j� foi usado em um item.
				/*if (mascaraArmazenada != null && mascaraAtual.trim() != mascaraArmazenada.trim()) { //Verifica se houve mudan�a na m�scara do atributo livre 
					alert("O campo "+descricaoCampo+" possui uma nova formata��o: "+mascaraAtual+".");	
				} */
				
				alert ("Valor informado no campo "+descricaoCampo+" em desacordo com a m�scara "+mascaraAtual);
				
				retorno = false;
			} else {
				//itera na m�scara � partir do primeiro item. 
				for (i=0;i < mascaraAtual.length;i++){
					caracterMascara = mascaraAtual.charAt(i); //Obtem o caracter da m�scara
					caracterAtualValorCampo = valorCampo.charAt(i); //Obtem o caracter informado no conteudo 
					if (caracterMascara == caracterMascaraMes) { //Se o item de mascara for referente ao m�s, obtem o mes digitado e pula dois caracteres no valor.
						mes = valorCampo.substring(i,i+2);
						i++;
						
						if (isNaN(mes)){//Fun��o que identifica se o argumento N�O � um numero
							//TODO a altera��o de atributos livres ainda n�o est� disponivel qdo o atributo livre j� foi usado em um item.
							/*if (mascaraAtual != mascaraArmazenada) {
								alert("O campo "+descricaoCampo+" possui uma nova formata��o: "+mascaraAtual+".");	
							} else {*/
								alert ("M�s inv�lido no campo "+descricaoCampo);
							//}
							retorno = false;
							break;
						} else {
							var mesInteiro = parseInt(mes, 10)
							
							if (mesInteiro < 1 || mesInteiro > 12){//verifica se o m�s � v�lido.
								alert ("M�s inv�lido no campo "+descricaoCampo);
								retorno = false;
								break;
							}
						}
					} else if (caracterMascara == caracterMascaraAno) { //Se o item de m�scara for referente ao ano, obtem o ano digitado e pula quatro caracteres no valor.
						ano = valorCampo.substring(i,i+4);
						i=i+3;
						
						if (isNaN(ano)){//Fun��o que identifica se o argumento N�O � um numero
							//TODO a altera��o de atributos livres ainda n�o est� disponivel qdo o atributo livre j� foi usado em um item.
							/*if (mascaraAtual != mascaraArmazenada) {
								alert("O campo "+descricaoCampo+" possui uma nova formata��o: "+mascaraAtual+".");	
							} else {*/
								alert ("Ano inv�lido no campo"+descricaoCampo);
//							}
							retorno = false;
							break;
						} else {
							var anoInteiro = parseInt(ano);
							if (anoInteiro < 1900 || mesInteiro > 9999){//verifica se o ano � v�lido.
								alert ("Ano inv�lido no campo "+descricaoCampo);
								retorno = false;
								break;
							}
						}
					} else if (caracterMascara == caracterMascaraSequencial){ //Eh executado quando o caracter � diferente do caracter de ano e mes. V�lido para todos os caracteres da m�scara inclusive os caracteres do valor incremental
						
						if (valorSequencialInformado != null) { //S� deve realizar a valida��o quando o caracter da m�scara for correspondente ao caracter de m�scara sequencial e Durante a altera��o do item da Estrutura o valorSequencialInformado dever� estar preenchido com valor gerado automaticamente na inclus�o do item.
							var tamanho = valorSequencialInformado.length;
							
							if (valorSequencialInformado != valorCampo.substring(i,i+tamanho)){ //Se o usu�rio alterar a parte incremental o retorno � falso e n�o efetua a altera��o.
								//TODO a altera��o de atributos livres ainda n�o est� disponivel qdo o atributo livre j� foi usado em um item.
								/*if (mascaraAtual != mascaraArmazenada) {
									alert("O campo "+descricaoCampo+" possui uma nova formata��o: "+mascaraAtual+".");	
								} else {*/							
								
								alert ("A parte incremental do campo "+descricaoCampo+" � gerado automaticamente. Valor correto "+valorSequencialInformado+", n�o � permitida a altera��o!");
								
//								}
								retorno = false;
								break;
							}
							i = (i + tamanho) -1;
						}
					} else {//if (caracterMascara != caracterAtualValorCampo) {
						//TODO a altera��o de atributos livres ainda n�o est� disponivel qdo o atributo livre j� foi usado em um item.
						/*if (mascaraAtual != mascaraArmazenada) {
							alert("O campo "+descricaoCampo+" possui uma nova formata��o: "+mascaraAtual+".");
						} else {*/
						
						if (caracterMascara != caracterAtualValorCampo) {
							alert ("Valor informado no campo "+descricaoCampo+" em desacordo com a m�scara "+mascaraAtual);

							retorno = false;
							break;
						}
					}
				}
			}	
		}
//	}
	return retorno;
}