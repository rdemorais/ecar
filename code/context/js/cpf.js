/** Fun??o que valida o cpf atrav?s do d?gito verificador
 *  @param objeto cpf
           exibirMsg true - exibe msg erro
           voltaFoco true - volta foco para o campo se valor digitado for invalido
           ehObrigatorio true - retorna falso se campo for vazio
 *  @return true - cpf ok
 *          false - cpf invalido
 *  @bug mozilla nao funciona o obj.focus();
 */
function validaCPF(objCpf, exibirMsg, voltarFoco, ehObrigatorio)
{
    var cpf = objCpf.value;
    var cpfCalc;
    var cpfSoma = 0;
    var cpfDigit = 0;
    var digit = "";      
    var retorno = false;

    if (typeof exibirMsg == "undefined") exibirMsg = true;
    if (typeof voltarFoco == "undefined") voltarFoco = true;
    if (typeof ehObrigatorio == "undefined") ehObrigatorio = false;

    if (cpf.length == 11)
    {
        // teste para verificar se ? uma sequencia de d?gitos iguais
        ch = cpf.charAt(0);
        sequencia = true;
        i = 0;
        while (sequencia && ++i < 11)
            if (cpf.charAt(i) != ch)
                sequencia = false;
       
        cpfCalc = cpf.substr(0,9);  
        for (i = 0; i < 9; i++)
            cpfSoma = cpfSoma + parseInt(cpfCalc.charAt(i)) * (10 - i);

        cpfDigit = 11 - cpfSoma%11;

        if (cpfDigit > 9)
            cpfCalc = cpfCalc + "0";
        else
        {
            digit = digit + cpfDigit;
            cpfCalc = cpfCalc + digit.charAt(0);
        }

        cpfSoma = 0;

        for (i = 0; i < 10; i++) 
            cpfSoma = cpfSoma + parseInt(cpfCalc.charAt(i)) * (11 - i);

        cpfDigit = 11 - cpfSoma%11;

        if (cpfDigit > 9)
            cpfCalc = cpfCalc + "0";
        else
        {
            digit = "";
            digit = digit + cpfDigit;
            cpfCalc = cpfCalc + digit.charAt(0);
        }  
        
        if (cpf == cpfCalc && !sequencia)
            retorno = true;
    }
    if (cpf.length == 0 && !ehObrigatorio)
        retorno = true;
        
    if (!retorno)
    {
        if (exibirMsg)
            alert('CPF incorreto\nDigite novamente');
        if (voltarFoco)
            objCpf.focus();
    }
    return(retorno);
}
