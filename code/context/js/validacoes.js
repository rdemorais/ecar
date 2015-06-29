
function validaCategoria(campo, label, exibirMsg) {

	var retorno = false;
	var categ = document.getElementById("categorias").value.split(",");

	for(i=0; i<categ.length; i++) {
		if(campo.value == categ[i]) {
			retorno = true;
			break
		}
	}
	
	if(!retorno) {
		alert('Categoria inválida.');
		campo.focus();
	}
	
	return retorno;
}

function validaFonte(campo, label, exibirMsg) {

	var retorno = false;
	var fonts = document.getElementById("fontes").value.split(",");

	for(i=0; i<fonts.length; i++) {
		if(campo.value == fonts[i]) {
			retorno = true;
			break
		}
	}
	
	if(!retorno) {
		alert('Fonte inválida.');
		campo.focus();
	}
	
	return retorno;
}


function validaString(obj, nome, ehObrigatorio, funcao){
	if(ehObrigatorio){
		if(Trim(obj.value) == ""){
			alert("Obrigatório o preenchimento do campo " + nome + "!");
			obj.focus();
			return false;
		}
	}
	
	if (typeof funcao != "undefined"){
		return (eval(funcao(obj, nome)));
		// funcao deve retornar true ou false.
		// tem como parâmetros obrigatorios o objeto e o nome do campo.
	}

	return true;
}

/** validaTamanhoLimite - Tamanho limite de uma String ou TextArea
	Ao atingir o tamanho limite nao permite que o usuario continue a digitacao.
	@param - str = Objeto para ser validado
			- size = Tamanho limite
	Exemplo:
	<textarea name="tx" onkeyup="return validaTamanhoLimite(this, 2000);"></text>
*/
function validaTamanhoLimite(obj, size){
	if(obj.value.length <= size)
		return true;
	else{	
		obj.value = obj.value.substring(0,size);
		alert("Atingiu o limite máximo do campo: " + size + " caracteres.");
		return false;
	}
}

function validaIntervalo(num, menor, maior){
	if(num >= menor && num <=maior){
		return true;
	}
	else {
		return false;
	}
}

function contaCaracteres(campo_cont, campo_msg){
     var n=eval("document.forms[0]."+campo_msg+".value.length");
     var n2=eval("document.forms[0]."+campo_msg);
     var c=eval("document.forms[0]."+campo_cont);

     var cont=parseInt(c.defaultValue);

     if(n>cont){
         if(c.value!='Sem'){
                 alert("Você está excedendo o número de caracteres permitidos!");
                 c.value='Sem';
         }
         n2.value = n2.value.substring(0,cont);
     }else c.value=cont-n;

  }

function validaNome(nome, size){
	if(isBlank(nome)){ //? vazio
		alert("Nome inválido !");
		return false;
	}
	else if(nome.length >= size){
		if(!isValidChar(nome.substring(0,1))){
			alert("Nome não pode começar com Branco ou Número!");//19
			return false;
		}
		if(!isValidChar(nome.substring(1,2))){
			alert("Nome tem que ter pelo menos duas letras!");//20
			return false;
		}
		if(TrimAll(nome.substring(2,4)).length == 0){
			alert("Nome tem que ter sobrenome!");//21
			return false;
		}else if(!isValidString(TrimAll(nome))){		
			alert("Nome com caracter inválido!");//18
			return false;
		}
		return true;	
	}
	else{ //? menor que 4 carcteres
		alert("Nome deve ter no mínimo 4 letras!");
		return false
	}
}
   
function validaDataNascimento(data){
	if(isNull(data)){
		alert("Data inválida!");
		return false;
	}
	if(isBlank(data)){
		alert("Data inválida!");
		return false;
	}
	if(isDate(data,"dd/MM/yyyy")){
		return true;
	}
	else{
		alert("Data inválida!");
		return false;
	}
}

/**
 * @author rodrigo.hjort
 * @param ano:	numero entre 1950 e 2050
 * @return	verdadeiro se ano for v?lido
 */
function validaAno(ano) {
	if (!isInteger(ano)) {
		alert("Ano deve ser numérico!");
		return(false);
	} else if (ano < 1950 || ano > 2050) {
		alert("Ano inválido! Valor deve estar entre 1950 e 2050.");
		return(false);
	} else {
		return(true);
	}
}

function validaCGM(cgm){
	if(onlySameNumber(cgm)){
		alert("CGM inválido !");
		return false;
	}
	if(calculaModulo11(cgm.substring(0,cgm.length-1)) != cgm.charAt(cgm.length - 1)){
		alert("Dígito Verificador Inválido !");
		return false;
	}else
		return true;
}

function calculaModulo11(cgm){
	while(cgm.length < 8){
		cgm = "0" + cgm;
	}
	codigo = cgm.substring(cgm.length - 8,cgm.length);
	//alert(codigo);
	digito = 0;
	for(var i=3; i <= 8; i++){
		var c = parseInt(codigo.charAt(i-1),10);
		//alert(c);
		digito = digito + c * (10 - i);
		//alert(digito);
	}
	for(var i=1; i <= 2; i++){
		var c = parseInt(codigo.charAt(i-1),10);
		//alert(c);
		digito = digito + c * (4 - i);
		//alert(digito);
	}
	//alert(parseInt(digito%11,10));
	digito = 11 - parseInt(digito%11,10);
	if(digito == 11 || digito == 10)
		digito = 0;
	//alert(digito);	
	return digito;
}


//-------------------------------------------------------------------

function trim(s){return String(s).replace(/^\s+/,"").replace(/\s+$/,"");}
function justNumbersStr(s){return String(s).replace(/\D*/g,"");}
function onlySameNumber(s){return isNumeric(s)&& (new RegExp("^("+s.charAt(0)+")(\\1)*$")).test(s);}
function invertStr(s){
	var t="",i;
	for(i=0;i<s.length;i++)t=s.charAt(i)+t;
	return t;
}
function removeStr(src,arg){
	var v=(typeof arg=="string")?[arg]:arg;
	var r="";
	for(var i=0;i<v.length;i++)r=changeStr(src,v[i],"");
	return r;
}
function repeatStr(src,str,size,orient){
	var r=String(src);
	if(!orient)orient="left";
	while(r.length < size)r=orient.toLowerCase()=="right"?(r+str):(str+r);
	return r;
}
function changeStr(src,from,to)
{
	src=String(src);
	var i,li=0,lFrom= from.length,dst="";
	while((i=src.indexOf(from,li))!=-1){
		dst+=src.substring(li,i)+to;
		li=i+lFrom;
	}
	dst+=src.substring(li);
	return dst;
}


function LTrim(str){
	if (str==null){return null;}
	for(var i=0; str.charAt(i)==" " || str.charAt(i)=="\n" || str.charAt(i)=="\t"; i++);
	return str.substring(i,str.length);
	}
function RTrim(str){
	if (str==null){return null;}
	for(var i=str.length-1; str.charAt(i)==" " || str.charAt(i)=="\n" || str.charAt(i)=="\t"; i--);
	return str.substring(0,i+1);
	}
function Trim(str){return LTrim(RTrim(str));}
function LTrimAll(str) {
	if (str==null){return str;}
	for (var i=0; str.charAt(i)==" " || str.charAt(i)=="\n" || str.charAt(i)=="\t"; i++);
	return str.substring(i,str.length);
	}
function RTrimAll(str) {
	if (str==null){return str;}
	for (var i=str.length-1; str.charAt(i)==" " || str.charAt(i)=="\n" || str.charAt(i)=="\t"; i--);
	return str.substring(0,i+1);
	}
function TrimAll(str) {
	if (str==null){return str;}
	var ret = "";
	for (var i=0; i < str.length; i++){
		if(str.charAt(i)==" " || str.charAt(i)=="\n" || str.charAt(i)=="\t"){
			ret += "";
		}else{
			ret += str.charAt(i);
		}
		
	}
	return ret;
	}
	
function isNull(val){return(val==null);}

function isBlank(val){
	if(val==null){return true;}
	for(var i=0;i<val.length;i++) {
		if ((val.charAt(i)!=' ')&&(val.charAt(i)!="\t")&&(val.charAt(i)!="\n")&&(val.charAt(i)!="\r")){return false;}
		}
	return true;
	}

function isInteger(val){
	if (isBlank(val)){return false;}
	for(var i=0;i<val.length;i++){
		if(!isDigit(val.charAt(i))){return false;}
		}
	return true;
	}

function isNumeric(val){return(parseFloat(val,10)==(val*1));}

function isArray(obj){return(typeof(obj.length)=="undefined")?false:true;}

function isDigit(num) {
	if (num.length>1){return false;}
	var string="1234567890";
	if (string.indexOf(num)!=-1){return true;}
	return false;
	}
	
function isValidString(str){
	if(isBlank(str)){return false;}
	for(var i=0;i<str.length;i++){
		if(!isValidChar(str.charAt(i))){return false;}
		}
	return true;
	}

function isValidMoeda(str){
	if(isBlank(str)){return false;}
	for(var i=0;i<str.length;i++){
		if(!isValidCharMoeda(str.charAt(i))){return false;}
		}
	return true;
	}
	
function isValidChar(c) {
	if (c.length>1){return false;}
	var string="abcdefghijklmnopqrstuvxwyzABCDEFGHIJKLMNOPQRSTUVXWYZáàãâéêíóõúüÁÀÃÂÉÊÍÓÕÚÜ ";
	if (string.indexOf(c)!=-1){return true;}
	return false;
	}
	
function isValidCharMoeda(c) {
	if (c.length>1){return false;}
	var string="0123456789,.";
	if (string.indexOf(c)!=-1){return true;}
	return false;
	}
	

/*
Fun??o para contar o n?mero de checkbox com um certo nome, passado como par?metro.
*/	
function verificaChecks(form, nome){
	var el = document.getElementsByTagName("INPUT");
	var i = 0, n = 0;
	
	while (i < el.length)
	{		
		if(el.item(i) != null){
			if (el.item(i).type == "checkbox" && el.item(i).name == nome){
				n++;
			}
		}
		i++;
	}
	
	return n;
}

function verificaChecksById(form, id){
	var el = document.getElementsByTagName('input');
	var i = 0, n = 0;
	
	while (i < el.length){
		if (el[i] != null){
			if(el[i].type == "checkbox" && el[i].id == id){
				n++;
			}
		}
		i++;
	}
	
	return n;
}

/*
Fun??o para contar o n?mero de radio com um certo nome, passado como par?metro.
*/	
function verificaRadios(form, nome){
	var el = document.getElementsByTagName("INPUT");
	var i = 0, n = 0;
	
	while (i < el.length)
	{		
		if(el.item(i) != null){
			if (el.item(i).type == "radio" && el.item(i).name == nome){
				n++;
			}
		}
		i++;
	}
	
	return n;
}

function getCheckByValue(form, value){
	var el = document.getElementsByTagName("INPUT");
	var i = 0, n = 0;

	while (i < el.length)
	{
		if (el.item(i)!= null && el.item(i).type == "checkbox" && el.item(i).value == value){
			return el.item(i);
		}
		i++;
	}
	
	return null;
}

function removeAcento(texto){ 
	retorno = texto.toLowerCase();
	var estranha = "áéíóúàèìòùâêîôûäëïöüãõ@#$%^&*()_+=-~` ç"; 
	var correta =  "aeiouaeiouaeiouaeiouao________________c"; 

	for(i=0; i<estranha.length; i++) { 
		retorno = retorno.replace(estranha.substr(i,1),correta.substr(i,1)); 
		retorno = retorno.replace("_",""); 
	} 
	
	return(retorno);
} 

/*
Valida se pelo menos um Check foi selecionado, se selecionado retorna true
*/
function validarCheckSelecionado(form, nomeCheckBox){
	var algumMarcado = false;
	var numChecks = 0;

    numChecks = verificaChecks(form, nomeCheckBox);
    if (numChecks > 0) {
		if(numChecks > 1){
			for(i = 0; i < eval('form.' + nomeCheckBox + '.length'); i++)
				if(eval('form.' + nomeCheckBox + '[i]').checked)
					algumMarcado = true;	
		} else {
			algumMarcado = eval('form.' + nomeCheckBox).checked;
		}
	}
	
	return algumMarcado;
}

/*
Valida se a Image foi selecionada, caso a imagem seja a default ele considera que a imagem não foi selecionada
*/
function validaImage(obj, nome, ehObrigatorio, figuraDefault){
	if(ehObrigatorio){
		if(Trim(obj.value) == "" || obj.value == figuraDefault){
			alert("Obrigatório o preenchimento do campo " + nome + "!");
			obj.focus();
			return false;
		}
	}
	
	return true;
}

/*
Valida se no conjunto de Radio, um foi selecionado, se selecionado retorna true
*/	
function validaRadioSelecionado(form, nome){
	var el = document.getElementsByTagName("INPUT");
	var i = 0;
	var algumMarcado = false;
	
	while (i < el.length)
	{		
		if(el.item(i) != null){
			if (el.item(i).type == "radio" && el.item(i).name == nome){
				if(el.item(i).checked)
					algumMarcado = true;
			}
		}
		i++;
	}
	
	return algumMarcado;
}

/*
Valida no conjunto de Radio, o valor que foi selecionado
*/	
function verificaValorSelecionado(form, nome){
	var el = document.getElementsByTagName("INPUT");
	var i = 0;
	var valorMarcado = '';
	
	while (i < el.length)
	{		
		if(el.item(i) != null){
			if (el.item(i).type == "radio" && el.item(i).name == nome){
				if(el.item(i).checked)
					valorMarcado = el.item(i).value;
			}
		}
		i++;
	}
	return valorMarcado;
}

/**
 * Testa o browser que está sendo utilizado pelo usuário final.
 * Se for IE, retorna TRUE, caso contrário FALSE.
 * @return boolean
 */
 function isIE() {
	var browserName = navigator.appName; 
	var isie = false;	
	
	if (browserName=="Microsoft Internet Explorer") {
		isie = true;
	}	
	
	return isie;
 } // end isIE

 
function validarMascara (mascara,tipoValidacao,caracterMes,caracterAno,caracterSequencial) {
	 
	var ret = true; 

	try {
		if (tipoValidacao == 'mascaraScript' || tipoValidacao == 'mascaraEditavelScript') { 
			
			if (mascara == '') {
				throw ('atributo.validacao.mascara.vazia');
			}
			
			validaMes(mascara, caracterMes);

			validaAno(mascara, caracterAno);

			validaCaracterIncremental(mascara, caracterSequencial)

		} 
		
		
	} catch (er){
		if (er == 'atributo.validacao.mascara.vazia') {
			alert ('Obrigatório o preenchimento do campo Máscara!');
		} else if (er == 'atributo.validacao.mascara.mes.invalida'){
			alert ('Máscara do mês inválida!');
		} else if (er == 'atributo.validacao.mascara.ano.invalida'){
			alert ('Máscara do ano inválida!');
		} else if (er == 'caracter.mascara.incremental.invalida'){
			alert ('Parte incremental da máscara, inválida!');
		}  else if (er == 'caracter.mascara.incremental.vazia'){
			alert ('Parte incremental da máscara é obrigatória!');
		} else {
			alert ('Erro inesperado na validação da máscara. Favor entrar em contato com o administrador do sistema!');
		}
		ret = false;
	}
	
	return ret;
}
 
function validaMes(mascara,caracterMes) {
		
	var primeiraOcorrenciaCaracterMes = mascara.indexOf(caracterMes);
	var ultimaOcorrenciaCaracterMes = mascara.lastIndexOf(caracterMes);
	
	if (primeiraOcorrenciaCaracterMes >= 0){//Se primeiraOcorrenciaCaracterMes for maior que 0 é porque existe caracter # 
		if (!(ultimaOcorrenciaCaracterMes == primeiraOcorrenciaCaracterMes+1)) {//verifica se há ultima ocorrencia do caracter # é igual a primeira ocorrencia mais uma posição, se for está correto senão a máscara está errada.
			throw ("atributo.validacao.mascara.mes.invalida");
		} 
	}
}

function validaAno(mascara,caracterAno) {

	var primeiraOcorrenciaCaracterAno = mascara.indexOf(caracterAno);
	var ultimaOcorrenciaCaracterAno = mascara.lastIndexOf(caracterAno);
	
	if (primeiraOcorrenciaCaracterAno >= 0){//Se primeiraOcorrenciaCaracterAno for maior que 0 é porque existe caracter @
		if (!(ultimaOcorrenciaCaracterAno == primeiraOcorrenciaCaracterAno+3)) {//verifica se há ultima ocorrencia do caracter @ é igual a primeira ocorrencia mais três posições, se for está correto senão a máscara está errada.
			throw ("atributo.validacao.mascara.ano.invalida");
		} else if (mascara.search(caracterAno+caracterAno+caracterAno+caracterAno) == -1) {//Verifica se há ocorrencia de 4 @ seguidos.
			throw ("atributo.validacao.mascara.ano.invalida");
		}
	}
}
	
	
function validaCaracterIncremental(mascara,caracterSequencial) {
		
		
	var primeiraOcorrenciaCaracterSequencial = mascara.indexOf(caracterSequencial);
	var ultimaOcorrenciaCaracterSequencial = mascara.lastIndexOf(caracterSequencial);
	
	if (primeiraOcorrenciaCaracterSequencial >= 0 ) {
		
		var caracterIncrementalCorreto=''; 
		//Os caracteres incrementais deve ser contínuos. Ex.: $$$ , a ocorrência ($$@ $) é inválida como máscara  
		for (var i = primeiraOcorrenciaCaracterSequencial; i <= ultimaOcorrenciaCaracterSequencial; i++) {
			caracterIncrementalCorreto = caracterIncrementalCorreto + caracterSequencial;
		}
		
		if (caracterIncrementalCorreto != mascara.substring(primeiraOcorrenciaCaracterSequencial, ultimaOcorrenciaCaracterSequencial+1)){
			throw ("caracter.mascara.incremental.invalida");
		}
		
	} else {
		throw ("caracter.mascara.incremental.vazia");
	}
}