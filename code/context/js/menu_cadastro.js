function mudaEstadoCadastro(estado){
	deleteCookie("estadoMenuCadastro", __pathSystemCookie, null);
	setCookie("estadoMenuCadastro", estado, null, __pathSystemCookie, null, null);	
}

function mudaFuncaoCadaastro(funcao){
	deleteCookie("menuAbertoCadastro", __pathSystemCookie, null);
	setCookie("menuAbertoCadastro", funcao, null, __pathSystemCookie, null, null);	
}

function botaoCadastro(estado) {

	
	if (estado == "aberto") {
	
		if(document.getElementById("conteudoCadastroEstrutura") != null){
				document.getElementById("conteudoCadastroEstrutura").style.paddingLeft = "220px";
		}		
		
	
		if(document.getElementById("btmenuCadastro") != null) {
			document.getElementById("btmenuCadastro").style.visibility="visible";
			document.getElementById("btmenuCadastro").innerHTML = "<a href=\"javascript:escondeCadastro(); mudaEstadoCadastro('fechado')\"><img src=\"" + _pathEcar + "/images/bt_menu_hide.gif\" border=\"0\"></a>";
		}
		
		
		if(getCookie("estadoMenu") == "fechado" && document.getElementById("menuCadastro") != null) {
			menuFechadoCadastroAberto();
		} else {
			 menuAbertoCadastroAberto();
		}
	}
	
	
	if (estado == "fechado") {
	
		if(getCookie("estadoMenu") == "fechado" && document.getElementById("menuCadastro") != null) {
			menuFechadoCadastroFechado();
			
		} else {
			 menuAbertoCadastroFechado();
		}	
	}
}

function mostraCadastro() {
	count = 0;
	timeInterval = setInterval("mostrandoCadastro()",1);
}

function mostrandoCadastro() {
	count += 50;
	
	if (count <= 250) {
			
		var menu = document.getElementById("menuCadastro");
		var conteudo = document.getElementById("conteudo");
		var conteudoEstrutura =  document.getElementById("conteudoCadastroEstrutura");
			
		if(menu!= null) {
		
			if(getCookie("estadoMenu") == "aberto") {
				menu.style.left = (count - 87);
			} 
		
		} 	
		
	}
	else {
		clearInterval(timeInterval);
		botaoCadastro("aberto");
	}
	

}

function escondeCadastro() {
	count = 0;
	timeInterval = setInterval("escondendoCadastro()",1);
	
	
}

function escondendoCadastro() {
	count += 50;
	
	
	if (count <= 250) {
	
	
		var menu = document.getElementById("menuCadastro");
		var conteudo = document.getElementById("conteudoCadastroEstrutura");
		
		if(getCookie("estadoMenu")!= null  && getCookie("estadoMenu")== "aberto") {
		
			if(menu != null)	{
				menu.style.left =(count * -2)+(250);
			}
			
			if(conteudo != null) {
				conteudo.style.paddingLeft = (count * -1)+(250);
			}
		
		} else {
		
	
			var conteudo= document.getElementById("conteudo");
			
			if(conteudo!= null) {
				conteudo.style.left = (count * -1)+(50);
			}
			
		}
		
		
	}
		
	else {
		clearInterval(timeInterval);
		botaoCadastro("fechado");
	}	

	
}


function escondeCadastroMenuFechado() {
	count = 0;
	timeInterval = setInterval("escondendoCadastro()",1);

}


function mostraCadastroMenuFechado() {
	count = 0;
	timeInterval = setInterval("mostrandoCadastro()",1);

}



//menu lateral aberto
// cadastro aberto
// Estado 1
function menuAbertoCadastroAberto () {
	

	
	//menuFechadoCadastroAberto();
//	alert("menuFechadoCadastroAberto");
	//cadastroFechadoMenuAberto();
//	alert("cadastroFechadoMenuAberto");
	//menuAbertoCadastroFechado();
//	alert("menuAbertoCadastroFechado");
	
	/*
	cadastroAbertoMenuAberto();
	alert("cadastroAbertoMenuAberto");
	menuAbertoCadastroFechado();
	alert("menuAbertoCadastroFechado");*/


	if(document.getElementById("btmenuCadastro") != null) {
		document.getElementById("btmenuCadastro").style.visibility="visible";
		document.getElementById("btmenuCadastro").innerHTML = "<a href=\"javascript:escondeCadastro(); mudaEstadoCadastro('fechado')\"><img src=\"" + _pathEcar + "/images/bt_menu_hide.gif\" border=\"0\"></a>";
	}


	if(document.getElementById("conteudo") != null) {
		document.getElementById("conteudo").style.left = 1;
	}
	
			
	if(document.getElementById("menuCadastro") != null) {
	//	if(jQuery.browser.msie == true) {
	//		document.getElementById("menuCadastro").style.width = "20em";
	//	} else {
	//		if(document.getElementById("menuCadastro").style.width == "200px")
	//		alert(document.getElementById("menuCadastro").style.width);
	//		document.getElementById("menuCadastro").style.width = "200px";
	//	}
		
		document.getElementById("menuCadastro").style.left = 187;
		//document.getElementById("menuCadastro").style.paddingRight = 1;	
	}
			
	if(document.getElementById("conteudoCadastroEstrutura") != null) {
		document.getElementById("conteudoCadastroEstrutura").style.left = 240;
	}
			

}



//Cadastro Aberto
// Menu Lateral Aberto
function cadastroAbertoMenuAberto() {

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


//menu lateral aberto
// cadastro fechado
// estado 2
function menuAbertoCadastroFechado() {

	

	if(document.getElementById("menuCadastro") != null) {
		document.getElementById("menuCadastro").style.left = -230;
	} 
					
	if(document.getElementById("btmenuCadastro") != null) {
		document.getElementById("btmenuCadastro").style.visibility="visible"; 
		document.getElementById("btmenuCadastro").innerHTML = "<a href=\"javascript:mostraCadastro(); mudaEstadoCadastro('aberto')\"><img src=\"" + _pathEcar + "/images/bt_menu_show.gif\" border=\"0\"></a>";
	} 
}


//menu lateral fechado
// cadastro fechado
// estado 3
function menuFechadoCadastroFechado() {

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

//menu lateral fechado
// cadastro aberto
// estado 4
function menuFechadoCadastroAberto(){


	if(document.getElementById("conteudoCadastroEstrutura") != null){
		document.getElementById("conteudoCadastroEstrutura").style.paddingLeft = "220px";
	}		
		
	
	if(document.getElementById("btmenuCadastro") != null) {
		document.getElementById("btmenuCadastro").style.visibility="visible";
		document.getElementById("btmenuCadastro").innerHTML = "<a href=\"javascript:escondeCadastro(); mudaEstadoCadastro('fechado')\"><img src=\"" + _pathEcar + "/images/bt_menu_hide.gif\" border=\"0\"></a>";
	}

	if(document.getElementById("menuCadastro") != null) {
		document.getElementById("menuCadastro").style.left = 10;
		if(jQuery.browser.msie == true) {
			document.getElementById("menuCadastro").style.width = "38em";
		} else {
			document.getElementById("menuCadastro").style.width = 200;
		}
	}
				
	if(document.getElementById("conteudoCadastroPasta")!= null) {
		document.getElementById("conteudoCadastroPasta").style.left = 10;
	}
			
	if(document.getElementById("conteudoCadastroEstrutura") != null) {
		document.getElementById("conteudoCadastroEstrutura").style.paddingLeft = 220;
	}
				
	if(document.getElementById("conteudo") != null) {
		document.getElementById("conteudo").style.left = 10;
	}
	
	cadastroAbertoMenuFechado();
		
	

}


function cadastroAbertoMenuFechado() {

	

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



