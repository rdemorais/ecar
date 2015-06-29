
/*
 * Abre uma janela de pesquisa.
 * Recebe como argumentos: 
 * @param pojo - o nome da classe que implementa a logica da pesquisa
 * @param funcaoGetDadosPesquisa - o nome da funcao que vai receber os valores de retorno (codigo e descricao)
 * @param parametros - uma lista de parametros adicionais que vao auxiliar a classe no resultado da pesquisa
 * @see PopUpLocalItem.java para ver um exemplo 
 */
function popup_pesquisa(pojo, funcaoGetDadosPesquisa, parametros) {
    if (typeof funcaoGetDadosPesquisa == "undefined") funcaoGetDadosPesquisa = 'getDadosPesquisa';
    if (typeof parametros == "undefined") parametros = '';
	return abreJanela(_pathEcar + '/pesquisa/popup_pesquisa.jsp?hidPojo=' + pojo + '&hidFuncao=' + funcaoGetDadosPesquisa + parametros,'500','470','pesquisa')
}

function popup_pesquisa_check(pojo, funcaoGetDadosPesquisa, parametros) {
    if (typeof funcaoGetDadosPesquisa == "undefined") funcaoGetDadosPesquisa = 'getDadosPesquisa';
    if (typeof parametros == "undefined") parametros = '';
	return abreJanela(_pathEcar + '/pesquisa/popup_pesquisa_check.jsp?hidPojo=' + pojo + '&hidFuncao=' + funcaoGetDadosPesquisa + parametros,'500','470','pesquisa')
}

function popup_pesquisa_pai(pojo, funcaoGetDadosPesquisa, parametros) {
    if (typeof funcaoGetDadosPesquisa == "undefined") funcaoGetDadosPesquisa = 'getDadosPesquisa';
    if (typeof parametros == "undefined") parametros = '';
	return abreJanela(_pathEcar + '/pesquisa/popup_pesquisa_pai.jsp?hidPojo=' + pojo + '&hidFuncao=' + funcaoGetDadosPesquisa + parametros,'500','470','pesquisa')
}

function popup_pesquisa_filho(pojo, funcaoGetDadosPesquisa, parametros) {
    if (typeof funcaoGetDadosPesquisa == "undefined") funcaoGetDadosPesquisa = 'getDadosPesquisa';
    if (typeof parametros == "undefined") parametros = '';
	return abreJanela(_pathEcar + '/pesquisa/popup_pesquisa_filho.jsp?hidPojo=' + pojo + '&hidFuncao=' + funcaoGetDadosPesquisa + parametros,'500','470','pesquisa')
}

/*
 * Abre uma janela qualquer
 */
function abreJanela(pagina, largura, altura, nome) {
	winAttr='width=' + largura + ',height=' + altura + ',toolbar=no,location=no,directories=no,status=no,menubar=no,resizable=yes,scrollbars=yes';
	return window.open(pagina, nome, winAttr); 
}
