function validar(form){
	if (form.codUsu.value == ""){
		alert("<%=_msg.getMensagem("itemEstrutura.usuario.validacao.usuarioUsu.obrigatorio")%>");
		return(false);
	}
	
	var el = document.getElementsByTagName("INPUT");
	var i = 0, n = 0;
	
	while (i < el.length){		
		if(el.item(i) != null){
			if (el.item(i).type == "checkbox" && el.item(i).checked == true){
				n++;
			}
		}
		i++;
	}
	
	if(n <= 0){
		alert("<%=_msg.getMensagem("itemEstrutura.usuario.validacao.indEdicaoIettus.obrigatorio")%>");
		return(false);
	}
	
	return(true);
}

<%-- 
	Caso a opção de Alteração ou Exclusão estejam marcados 
	a opção de leitura tem que estar marcada.
	Pois não se pode alterar ou excluir sem a permissão de 
	leitura 
--%>
function marcarLeitura(){
	if ((document.form.indEdicaoIettus.checked) || (document.form.indExcluirIettus.checked)) {
		document.form.indLeituraIettus.checked = true;
	} 
}
