function mudaEstado(estado){
	deleteCookie("estadoMenu", __pathSystemCookie, null);
	setCookie("estadoMenu", estado, null, __pathSystemCookie, null, null);	
}

function mudaFuncao(funcao){
	deleteCookie("menuAberto", __pathSystemCookie, null);
	setCookie("menuAberto", funcao, null, __pathSystemCookie, null, null);	
}

function botao(estado) {
	if (estado == "aberto") {
		if (document.getElementById("btmenu") != null)
		document.getElementById("btmenu").innerHTML = "<a href=\"javascript:esconde(); mudaEstado('fechado')\"><img src=\"" + _pathEcar + "/images/bt_menu_hide.gif\" border=\"0\"></a>";
	}
	if (estado == "fechado") {
		if (document.getElementById("btmenu") != null){
			document.getElementById("btmenu").innerHTML = "<a href=\"javascript:mostra(); mudaEstado('aberto')\"><img src=\"" + _pathEcar + "/images/bt_menu_show.gif\" border=\"0\"></a>";
		}
		if (document.getElementById("menu") != null){
			document.getElementById("menu").style.left = -170;
		}
		if (document.getElementById("conteudo") != null){
			document.getElementById("conteudo").style.paddingLeft = 17;
		}	
	}
}

function mostra() {
	count = 0;
	timeInterval = setInterval("mostrando()",1);
}

function mostrando() {

	count += 85;
	
	if (count <= 170) {
		
		var menu = document.getElementById("menu");
		var conteudo = document.getElementById("conteudo");
		
		menu.style.left = count - 170;
		conteudo.style.paddingLeft = count + 17;

	}
	else {
		clearInterval(timeInterval);
		botao("aberto");
		}
}

function esconde() {
	count = 0;
	timeInterval = setInterval("escondendo()",1);
}

function escondendo() {

	count += 85;
	
	if (count <= 170) {
		
		var menu = document.getElementById("menu");
		var conteudo = document.getElementById("conteudo");
		
		menu.style.left = count * -1;
		conteudo.style.paddingLeft = (count * -1)+(187);

	}
	else {
		clearInterval(timeInterval) ;
		botao("fechado");
	}
}
