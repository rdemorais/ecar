/** valida CNPJ
 *  @param  objetoCNPJ
 *          exibirMsg - true - exibe msg de erro se estiver errado
 *          voltarfoco - true - volta o foco para o campo se estiver preenchido errado
 *          ehObrigatorio true - retorna falso se campo for vazio
 *  somente o primeiro parametro ? obrigatorio. caso nao sejam informados os outrso parametros
 *  serao tomados valores default
 *  @return true - CNPJ ok, false CNPJ incorreto         
 *  @bug mozilla nao funciona o objCNPJ.focus() quando s.o. = winxp
 *  exemplo: onblur="validaCNPJ(this)"> // igual a validaCNPJ(this, true, true, false);
 *           onblur="validaCNPJ(this, false, false, false)">
 *           onblur="validaCNPJ(this, true, true)">
 */
function validaCNPJ(objCNPJ, exibirMsg, voltarFoco, ehObrigatorio)
{
    var cnpj = objCNPJ.value;
    var retorno = false;
    var aux_cnpj = "";
    var cnpj1=0,cnpj2=0;

    if (typeof exibirMsg == "undefined") exibirMsg = true;
    if (typeof voltarFoco == "undefined") voltarFoco = true;
    if (typeof ehObrigatorio == "undefined") ehObrigatorio = false;

    if (cnpj.length>0)
    {
        for(j=0;j < cnpj.length; j++)
            if(cnpj.substr(j,1)>="0" && cnpj.substr(j,1)<="9")
                aux_cnpj += cnpj.substr(j,1);
        if(aux_cnpj.length == 14)
        {
            cnpj1 = aux_cnpj.substr(0,12);
            cnpj2 = aux_cnpj.substr(aux_cnpj.length-2,2);
            fator = "543298765432";
            controle = "";
            for(j=0;j<2;j++)
            {
                soma = 0;
                for(i=0;i<12;i++)
                    soma += cnpj1.substr(i,1) * fator.substr(i,1);
                if (j == 1) 
                    soma += digito * 2;
                digito = (soma * 10) % 11;
                if(digito == 10) 
                    digito = 0;
                controle += digito;
                fator = "654329876543";
            }
            if(controle == cnpj2 && cnpj != "00000000000000")
                retorno = true;
        }
    }
    if (cnpj.length == 0 && !ehObrigatorio)
        retorno = true;
        
    if (!retorno)
    {
        if (exibirMsg)
            alert("CNPJ incorreto\nDigite novamente");
        if (voltarFoco)
            // aparente bug no mozilla firefox 0.9.2 quando winxp
            objCNPJ.focus();
    }
    return (retorno);
}
