<!--

function onLoad(form) {
	form.reset();
	focoInicial(form);
}

function aoClicarGravar(form){
	form.hidAcao.value = "<%=CONSULTA%>";
	if(validarGravar(form)){
		form.action = "ctrl.jsp"
		form.submit();
	}
}	

function aoClicarAlterar(form){
	form.hidAcao.value = "<%=ALTERA%>";
	form.action = "frm_inc.jsp";
	form.submit();
}

function aoClicarCancelar(form){
	form.hidAcao.value = "<%=CONSULTA%>";
	form.sisAtributoSatb.value = "<%=Pagina.getParamStr(request, "sisAtributoSatb").toString()%>";
	form.action = "frm_inc.jsp";
	form.submit();
}

function aoSelecionarClasseDeAcesso(form){
	form.action = "frm_inc.jsp";
	form.submit();
}

function validarGravar(form){
	if(form.sisAtributoSatb.value == ""){
		alert("<%=_msg.getMensagem("estruturaAcesso.validacao.sisAtributoSatb.obrigatorio")%>");
		form.sisAtributoSatb.focus();
		return(false);
	}
	
	if(form.sisAtributoSatb.value != "<%=Pagina.getParamStr(request, "sisAtributoSatb").toString()%>"){
		alert("<%=_msg.getMensagem("estruturaAcesso.validacao.sisAtributoSatb.definir")%>");
		form.sisAtributoSatb.focus();
		return(false);
	}

	return(true);
}

function ShowHide(check,campo){
	var theDiv = document.getElementById(campo);
	if(theDiv != null){
		if(check.checked){
			theDiv.style.display = '';
		}
		else{
			theDiv.style.display = 'none';
		}
	}
}

function DesabilitarDatasLimites(check,campo){
	if(document.form.elements[campo]){
		if(check.checked){		
			document.form.elements[campo+"_hidden"].checked=1;
			document.form.elements[campo+"_hidden"].disabled=true;
			document.form.elements[campo].value="S";
		}else{
			document.form.elements[campo+"_hidden"].disabled=false;
		}
	}
}

function DesabilitarSituacao(check,campo,parecer){
	var parecerChecked = 0;
	
	// Só entrar se o elementos existirem
	if (document.form.elements[campo]){ 
		if(check.checked ){
			document.form.elements[campo+"_hidden"].checked=1;
			document.form.elements[campo+"_hidden"].disabled=true;
			document.form.elements[campo].value="S";
		}else{
			for (i=0;i<document.form.elements.length;i++){
				nomeCampoForm = document.form.elements[i].name;
				codParecerForm = nomeCampoForm.split('-');			
				if(codParecerForm[0] == parecer){
					if(document.form.elements[i].checked){
						parecerChecked =1;
					}
				}
			}
			if(parecerChecked==0){
				document.form.elements[campo+"_hidden"].disabled=false;
			}	
		}
	}
}

function alteraCampoHidden(check,campo){
	if(check.checked){
		document.form.elements[campo].value="S";
	}else{
		document.form.elements[campo].value="N";
	}
}

//-->