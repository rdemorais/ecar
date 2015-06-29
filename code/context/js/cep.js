
/** validaCEP - verifica se o cep digitado esta correto.
    80530060 ou 80530-060 - correto
    @param - objeto cep
           - exibirMsg true - mostra mensagem se estiver incorreto
           - voltarFoco true - forca o foco no campo
           - ehObrigatorio true - nao permite campo vazio
    @return true - cep ok
*/
function validaCEP(objCEP, exibirMsg, voltarFoco, ehObrigatorio) {
    var cep = objCEP.value;
    var retorno = true;

    if (typeof exibirMsg == "undefined") exibirMsg = true;
    if (typeof voltarFoco == "undefined") voltarFoco = true;
    if (typeof ehObrigatorio == "undefined") ehObrigatorio = false;

    if (cep.length==8) {
        if (!isInteger(cep)){
            retorno = false;
        }
    } else if (cep.length==9) {
        if (!isInteger(cep.substring(0,5)))
            retorno = false;
        if (!isInteger(cep.substring(6,8)))
            retorno = false;
        if (cep.charAt(5)!='-')
            retorno = false;
    } else retorno = false;

    if ((isNull(cep) || isBlank(cep) || cep.length == 0) && !ehObrigatorio)
        retorno = true;

    if (!retorno){
        if (exibirMsg)
            alert("CEP inválido\nDigite novamente");
        if (voltarFoco)
            objCEP.focus();
    }
    return retorno;
}


function isNull(val){return(val==null);}

function isBlank(val){
    if(val==null){return true;}
    for(var i=0;i<val.length;i++) {
        if ((val.charAt(i)!=' ')&&(val.charAt(i)!="\t")&&(val.charAt(i)!="\n")&&(val.charAt(i)!="\r")){
            return false;
        }
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

function isDigit(num) {
    if (num.length>1){return false;}
    var string="0123456789";
    if (string.indexOf(num)!=-1)
        return true;
    return false;
}

function isFloat(val)
{
    var retorno = true;
    var strfloat = val;
    var contaponto = 0;
    var digit = strfloat.charAt(0);

    // testa a posicao zero
    if (digit == "+" || digit == "-")
    //
    // falta implementar  
    //
    for (var i = 0; i < strfloat.length; i++)
    {
        digit = strfloat.charAt(i);
        if (!isDigit(digit))
            if (digit == "." && contaponto == 0)
                contaponto++;
            else
                retorno = false;
    }
    return retorno;
}
