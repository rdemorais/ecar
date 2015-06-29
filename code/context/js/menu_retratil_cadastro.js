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
		document.getElementById("btmenu").innerHTML = "<a href=\"javascript:esconde(); mudaEstado('fechado')\"><img src=\"" + _pathEcar + "/images/bt_menu_hide.gif\" border=\"0\"></a>";
	
		if(getCookie("estadoMenuCadastro") != null && getCookie("estadoMenuCadastro") == "aberto") {
			cadastroAbertoMenuRetratilAberto();
		} else {
			cadastroFechadoMenuRetratilAberto();
		}
		
		if(jQuery.browser.msie == true) {
			document.getElementById("menuCadastro").style.width = "20em";
		} else {
			//document.getElementById("menuCadastro").style.width = 200;
		}	
	
	}
	
	if (estado == "fechado") {
		document.getElementById("btmenu").innerHTML = "<a href=\"javascript:mostra(); mudaEstado('aberto')\"><img src=\"" + _pathEcar + "/images/bt_menu_show.gif\" border=\"0\"></a>";
		document.getElementById("menu").style.left = -170;
	
		if(getCookie("estadoMenuCadastro") != null && getCookie("estadoMenuCadastro") == "aberto") {
			cadastroAbertoMenuRetratilFechado();
		} else {
			cadastroFechadoMenuRetratilFechado();
		}
	}

}

function mostra() {

	if(document.getElementById("menuCadastro") != null) {
		if(jQuery.browser.msie == true) {
			document.getElementById("menuCadastro").style.width = "20em";
		} else {
			document.getElementById("menuCadastro").style.width = 200;
		}
	}
	
	count = 0;
	timeInterval = setInterval("mostrando()",1);
}

function mostrando() {

	count += 85;
	
	if (count <= 170) {
		
		var menu = document.getElementById("menu");
		menu.style.left = count - 170;

		
		// Se o menu cadastro estiver expandido
		if(getCookie("estadoMenuCadastro")!= null  && getCookie("estadoMenuCadastro")== "aberto") {
			
			var menuCadastro = document.getElementById("menuCadastro");
			var conteudoCadastroPasta = document.getElementById("conteudoCadastroPasta");
			
			
			if(conteudoCadastroPasta!= null ) {
				conteudoCadastroPasta.style.left = count - 107;
			}
			
			if(document.getElementById("menuCadastro") != null) {
				if(jQuery.browser.msie == true) {
					document.getElementById("menuCadastro").style.width = "20em";
				} else {
					document.getElementById("menuCadastro").style.width = 200;
				}
				
				menuCadastro.style.left = count - 217;
			}	
			
			
		} else {
			
			// Se o menu cadastro nao estiver expandido, move todo o conteudo
			var conteudo = document.getElementById("conteudo");
			conteudo.style.paddingLeft = count + 17;
		
		}
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
		var menuEstrutura = document.getElementById("menuCadastro");
		var conteudoCadastroPasta = document.getElementById("conteudoCadastroPasta");
		
		
		// move o menu do sentinela
		menu.style.left = count * -1;
		
		//Se o menu cadastro estiver aparecendo
		if(getCookie("estadoMenuCadastro")!= null  && getCookie("estadoMenuCadastro")== "aberto") {
		
			//move o menu cadastro (expandido)
			if(menuEstrutura!= null ) {
				menuEstrutura.style.left = (count * -87);
			}
		
			
		}

	}
	else {
		clearInterval(timeInterval) ;
		botao("fechado");
	}
}

// Cadastro Fechado
// Menu Lateral Aberto
function cadastroFechadoMenuRetratilAberto() {

	
	if(jQuery.browser.msie == true) {
		document.getElementById("menuCadastro").style.width = "10em";
	} else {
		document.getElementById("menuCadastro").style.width = "5px";
	}
	

	if(document.getElementById("conteudo") != null) {
		document.getElementById("conteudo").style.left = 1;
	}
			
	if(document.getElementById("conteudoCadastroEstrutura") != null){
		document.getElementById("conteudoCadastroEstrutura").style.left = 1;
	}
			
	if(document.getElementById("menuCadastro") != null) {
		document.getElementById("menuCadastro").style.left = -220;	
	}
			
	if(document.getElementById("btmenuCadastro") != null) {
		document.getElementById("btmenuCadastro").style.visibility="visible"; 
		document.getElementById("btmenuCadastro").innerHTML = "<a href=\"javascript:mostraCadastro(); mudaEstadoCadastro('aberto')\"><img src=\"" + _pathEcar + "/images/bt_menu_show.gif\" border=\"0\"></a>";
	} 

}

//Cadastro Aberto
// Menu Lateral Aberto
function cadastroAbertoMenuRetratilAberto() {

	if(document.getElementById("menuCadastro") != null) {
		document.getElementById("menuCadastro").style.left = 187;
		document.getElementById("menuCadastro").style.paddingRight = 1;	
	}
			
	if(document.getElementById("conteudo") != null) {
		document.getElementById("conteudo").style.left = 1;
	}
			
	if(document.getElementById("conteudoCadastroEstrutura") != null){
		document.getElementById("conteudoCadastroEstrutura").style.paddingLeft = 220;
	}		


}

//Cadastro Aberto
// Menu Lateral Fechado
function cadastroAbertoMenuRetratilFechado() {

	document.getElementById("btmenu").innerHTML = "<a href=\"javascript:mostra(); mudaEstado('aberto')\"><img src=\"" + _pathEcar + "/images/bt_menu_show.gif\" border=\"0\"></a>";
	document.getElementById("menu").style.left = -170;

	document.getElementById("menuCadastro").style.left = 10;
		
			
	if(document.getElementById("conteudo") != null) {	
		document.getElementById("conteudo").style.left = 1;
	}
			
			
	if(document.getElementById("conteudoCadastroEstrutura") != null){				
		document.getElementById("conteudoCadastroEstrutura").style.paddingLeft = 381;
	}	

	if(document.getElementById("menuCadastro") != null) {
		if(jQuery.browser.msie == true) {
			document.getElementById("menuCadastro").style.width = "38em";
		} else {
			document.getElementById("menuCadastro").style.width = 200;
		}
		document.getElementById("menuCadastro").style.paddingRight = 187;
	}
			
	if(document.getElementById("tituloTelaCadastro") != null) {	
		document.getElementById("conteudo").style.left = -160;
		document.getElementById("menuCadastro").style.left = 173;
		document.getElementById("tituloTelaCadastro").style.paddingRight = 200;
		document.getElementById("tituloTelaCadastro").style.left = 220;
	}	


}



// Cadastro Fechado
// Menu Lateral Fechado
function cadastroFechadoMenuRetratilFechado() {

	//aparece o menu cadastro - estado 1
	menuRetratilAbertoCadastroAberto();
	
	//desaparece com o menu retratil - estado 4
	cadastroAbertoMenuRetratilFechado();

	//desaparece com o menu cadastro - estado 3
	menuRetratilFechadoCadastroFechado();



}

//FUNCOES DE CADASTRO
// Menu Lateral Aberto
// Cadastro Aberto
// Estado 1
function menuRetratilAbertoCadastroAberto () {

		if(document.getElementById("btmenuCadastro") != null) {
			document.getElementById("btmenuCadastro").style.visibility="visible";
			document.getElementById("btmenuCadastro").innerHTML = "<a href=\"javascript:escondeCadastro(); mudaEstadoCadastro('fechado')\"><img src=\"" + _pathEcar + "/images/bt_menu_hide.gif\" border=\"0\"></a>";
		}


		if(document.getElementById("conteudo") != null) {
				document.getElementById("conteudo").style.left = 10;
		}
			
		if(document.getElementById("menuCadastro") != null) {
			if(jQuery.browser.msie == true) {
				document.getElementById("menuCadastro").style.width = "21em";
			} else {
				document.getElementById("menuCadastro").style.width = 200;
			}
			document.getElementById("menuCadastro").style.left = 187;
			document.getElementById("menuCadastro").style.paddingRight = 1;	
		}
			
		if(document.getElementById("conteudoCadastroEstrutura") != null){
			document.getElementById("conteudoCadastroEstrutura").style.left = 220;
		}	
			

}


//Menu Lateral Fechado
// Cadastro Fechado
// estado 3
function menuRetratilFechadoCadastroFechado() {

	//diminui o tamanho do menuCadastro
	if(jQuery.browser.msie == true) {
		document.getElementById("menuCadastro").style.width = "20em";
	} else {
		document.getElementById("menuCadastro").style.width = 200;
	} 
	
			
	//desaparece com o menu lateral
	if(document.getElementById("conteudo") != null) {
		document.getElementById("conteudo").style.left = -177;
	}
			
	if(document.getElementById("menuCadastro")!= null) {
		document.getElementById("menuCadastro").style.left = -230;
	}
		
	if(document.getElementById("conteudoCadastroEstrutura") != null){
		document.getElementById("conteudoCadastroEstrutura").style.paddingLeft= 1;
	}		
			
			
	//mostra o botão		
	if(document.getElementById("btmenuCadastro") != null) {
		document.getElementById("btmenuCadastro").style.visibility="visible"; 
		document.getElementById("btmenuCadastro").innerHTML = "<a href=\"javascript:mostraCadastro(); mudaEstadoCadastro('aberto')\"><img src=\"" + _pathEcar + "/images/bt_menu_show.gif\" border=\"0\"></a>";
	} 
			

}