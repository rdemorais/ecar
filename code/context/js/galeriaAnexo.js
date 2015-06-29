function conteudocategorias(z,imagem,titulo,url,texto) {	
			
	anexocategoriatext[z] += "<table id='anexocategorias' cellspacing='0' width='255'><tr><td>";
	if(imagem == null || imagem == "") {
		anexocategoriatext[z] += "&nbsp;";
	}
	else {
		//anexocategoriatext[z] += "<img src='" + imagem + "' width='100' height='100'>";
		anexocategoriatext[z] += "<img src='" + imagem + "'>";
	}
	anexocategoriatext[z] += "</td><td><div style='width:200px; vertical-align:top'><b>";
	anexocategoriatext[z] += titulo;
	anexocategoriatext[z] += "</b><br><a href='#' onClick=MM_openBrWindow('";
	anexocategoriatext[z] += url;
	anexocategoriatext[z] += "','popup','scrollbars=yes,resizable=yes,width=750,height=500')>";
	anexocategoriatext[z] += texto;
	anexocategoriatext[z] += "</a></div></td></tr></table>";
	
}

function conteudocategoriaImagem(z,imagem,titulo,url,texto,largura,altura) {	
			
	anexocategoriatext[z] += "<table id='anexocategorias' cellspacing='0' width='255'><tr><td>";
	if(imagem == null || imagem == "") {
		anexocategoriatext[z] += "&nbsp;";
	}
	else {
		if(largura == "0" || altura == "0"){
			anexocategoriatext[z] += "<img src='" + imagem + "'>";
		}
		else{
			anexocategoriatext[z] += "<img src='" + imagem + "' width='" + largura + "' height='" + altura + "'>";
		}
	}
	anexocategoriatext[z] += "</td><td><div style='width:200px; vertical-align:top'><b>";
	anexocategoriatext[z] += titulo;
	anexocategoriatext[z] += "</b><br><a href='#' onClick=MM_openBrWindow('";
	anexocategoriatext[z] += url;
	anexocategoriatext[z] += "','popup','scrollbars=yes,resizable=yes,width=750,height=500')>";
	anexocategoriatext[z] += texto;
	anexocategoriatext[z] += "</a></div></td></tr></table>";
	
}