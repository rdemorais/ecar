	if(!validaString(form.descricaoSga,'Descrição',true)){
		return(false);
	}
	
	if(form.sisTipoExibicGrupoSteg.value == ""){
		alert("<%=_msg.getMensagem("demGrupoAtributo.validacao.sisTipoExibicGrupoSteg.obrigatorio")%>");
		form.sisTipoExibicGrupoSteg.focus();
		return(false);
	}
	
	if(form.sisTipoOrdenacaoSto.value == ""){
		alert("<%=_msg.getMensagem("demGrupoAtributo.validacao.sisTipoOrdenacaoSto.obrigatorio")%>");
		form.sisTipoOrdenacaoSto.focus();
		return(false);
	}
	
	if(form.indObrigatorioSga[0].checked == false && form.indObrigatorioSga[1].checked == false){
		alert("<%=_msg.getMensagem("demGrupoAtributo.validacao.indObrigatorioGatt.obrigatorio")%>");
		return(false);
	}
	
	if (form.indTabelaUso.value == 'U'){
		if (form.sisTipoExibicGrupoCadUsuSteg.selectedIndex == 0){
			alert("<%=_msg.getMensagem("demGrupoAtributo.validacao.sisTipoExibicGrupoCadUsuSteg.obrigatorio")%>");
			return(false);
		}
	}
	
	if (form.indCadUsuSga != null){		
		if (form.indCadUsuSga.value == 'S') {			
			if (form.indTabelaUso.value == 'U'){
				if (form.sisTipoExibicGrupoSteg.selectedIndex == 0){
					alert("<%=_msg.getMensagem("demGrupoAtributo.validacao.sisTipoExibicGrupoSteg.obrigatorio")%>");
					return(false);
				}
			}
			else{
				if (form.sisTipoExibicGrupoCadUsuSteg.selectedIndex == 0){
					alert("<%=_msg.getMensagem("demGrupoAtributo.validacao.sisTipoExibicGrupoCadUsuSteg.obrigatorio")%>");
					return(false);
				}
			}
		} else if (form.indCadUsuSga[0] != null && form.indCadUsuSga[0].checked == true){
			if (form.indTabelaUso.value == 'U'){
				if (form.sisTipoExibicGrupoSteg.selectedIndex == 0){
					alert("<%=_msg.getMensagem("demGrupoAtributo.validacao.sisTipoExibicGrupoSteg.obrigatorio")%>");
					return(false);
				}
			}
			else{
				if (form.sisTipoExibicGrupoCadUsuSteg.selectedIndex == 0){
					alert("<%=_msg.getMensagem("demGrupoAtributo.validacao.sisTipoExibicGrupoCadUsuSteg.obrigatorio")%>");
					return(false);
				}	
			}
		}
	}
	
	if(Trim(form.seqApresentacaoSga.value) != ""){
		if(!validaNum(form.seqApresentacaoSga, 'Seq. de Apresentação', false)){
			alert("<%=_msg.getMensagem("demGrupoAtributo.validacao.seqApresentacaoGatt.invalido")%>");
			form.seqApresentacaoSga.focus();
			return(false);
		}
	}else{
		alert("<%=_msg.getMensagem("demGrupoAtributo.validacao.seqApresentacaoGatt.obrigatorio")%>");
		form.seqApresentacaoSga.focus();
		return(false);
	}
	return(true);