function validar(form){
<%
	if (codSegmento == (Long.valueOf(_msg.getMensagem("admPortal.materias"))).intValue()){
%>
    	setHiddenVal('rte');
  	    form.richText.value = form.richText.value.replace(/>/g, "&gt;").replace(/\n/g, "");
  	    form.richText.value = form.richText.value.replace(/&gt;/g, ">");
  	    form.integraSgti.value = form.richText.value;
    
		if(form.segmentoCategoriaSgtc.value == ""){
			alert("<%=_msg.getMensagem("segmentoItem.validacao.segmentoCategoriaSgtc.obrigatorio")%>");
			form.segmentoCategoriaSgtc.focus();
			return false;
		}
		
		if(form.hidTamListaEditorias.value <= 0){
			alert("<%=_msg.getMensagem("segmentoItem.validacao.editoriasSisAtributoSatb.naoCadastradas")%>");
			return false;
		}else{
			if(!validarCheckSelecionado(form, "editoriasSisAtributoSatb")){
				alert("<%=_msg.getMensagem("segmentoItem.validacao.editoriasSisAtributoSatb.obrigatorio")%>");
				return false;
			}
		}
		
		if (!validaString(form.tituloSgti, "Título", true)){
			return false;
		}
		
		if (!validaString(form.linhaApoioSgti, "Linha de Apoio", true)){
			return false;
		}
		
		if (!validaString(form.integraSgti, "Íntegra", true)){
			return false;
		}
		
		if(form.segmentoItemFonteSgtif.value == ""){
			alert("<%=_msg.getMensagem("segmentoItem.validacao.segmentoItemFonteSgtif.obrigatorio")%>");
			form.segmentoItemFonteSgtif.focus();
			return false;
		}
		
		if (!validaData(form.dataItemSgti,false,true,true)){
			if(form.dataItemSgti.value == ""){
				alert("<%=_msg.getMensagem("segmentoItem.validacao.dataItemSgti.obrigatorio")%>");
			}else{
				alert("<%=_msg.getMensagem("segmentoItem.validacao.dataItemSgti.invalido")%>");
			}
			return false;
		}
		
		if (!validaData(form.dataIniValidadeSgti,false,true,true)){
			if(form.dataIniValidadeSgti.value == ""){
				alert("<%=_msg.getMensagem("segmentoItem.validacao.dataIniValidadeSgti.obrigatorio")%>");
			}else{
				alert("<%=_msg.getMensagem("segmentoItem.validacao.dataIniValidadeSgti.invalido")%>");
			}
			return false;
		}
		
		if (!validaData(form.dataFimValidadeSgti,false,true,true)){
			if(form.dataFimValidadeSgti.value == ""){
				alert("<%=_msg.getMensagem("segmentoItem.validacao.dataFimValidadeSgti.obrigatorio")%>");
			}else{
				alert("<%=_msg.getMensagem("segmentoItem.validacao.dataFimValidadeSgti.invalido")%>");
			}
			return false;
		}
		
		var dtIni;
		var dtLim;
		
		dtIni = dataInversa(form.dataIniValidadeSgti.value);
		dtLim = dataInversa(form.dataFimValidadeSgti.value);
		
		if (dtIni > dtLim){
			alert("<%=_msg.getMensagem("segmentoItem.validacao.dataFimValidadeSgti.dataFinalMenorQueDataInicial")%>");
			form.dataFimValidadeSgti.focus();
			return(false)
		}
		
		if(form.segmentoItemLeiauteSgtil.value == ""){
			alert("<%=_msg.getMensagem("segmentoItem.validacao.segmentoItemLeiauteSgtil.obrigatorio")%>");
			form.segmentoItemLeiauteSgtil.focus();
			return false;
		}
		
		if(form.indUtilizTpAcessoSgti.value == "S"){
			if(!validarCheckSelecionado(form, "tpAcessoSisAtributoSatb")){
				alert("<%=_msg.getMensagem("segmentoItem.validacao.tpAcessoSisAtributoSatb.obrigatorio")%>");
				return false;
			}
		}
		
		if(form.anexoEnderecoSgti.value != "" && form.anexoLegendaSgti.value == ""){
			alert("<%=_msg.getMensagem("segmentoItem.validacao.anexoLegendaSgti.obrigatorio")%>");
			return false;
		}
<%
	}else if (codSegmento == (Long.valueOf(_msg.getMensagem("admPortal.taxacoes"))).intValue()){
%>
		if(form.segmentoCategoriaSgtc.value == ""){
			alert("<%=_msg.getMensagem("segmentoItem.validacao.segmentoCategoriaSgtc.obrigatorio")%>");
			form.segmentoCategoriaSgtc.focus();
			return false;
		}
		
		if(form.hidTamListaEditorias.value <= 0){
			alert("<%=_msg.getMensagem("segmentoItem.validacao.editoriasSisAtributoSatb.naoCadastradas")%>");
			return false;
		}else{
			if(!validarCheckSelecionado(form, "editoriasSisAtributoSatb")){
				alert("<%=_msg.getMensagem("segmentoItem.validacao.editoriasSisAtributoSatb.obrigatorio")%>");
				return false;
			}
		}
		
		if (!validaString(form.tituloSgti, "Título", true)){
			return false;
		}
		
		if (!validaString(form.linhaApoioSgti, "Linha de Apoio", true)){
			return false;
		}
		
		if(form.segmentoItemFonteSgtif.value == ""){
			alert("<%=_msg.getMensagem("segmentoItem.validacao.segmentoItemFonteSgtif.obrigatorio")%>");
			form.segmentoItemFonteSgtif.focus();
			return false;
		}
		
		if (!validaData(form.dataItemSgti,false,true,true)){
			if(form.dataItemSgti.value == ""){
				alert("<%=_msg.getMensagem("segmentoItem.validacao.dataItemSgti.obrigatorio")%>");
			}else{
				alert("<%=_msg.getMensagem("segmentoItem.validacao.dataItemSgti.invalido")%>");
			}
			return false;
		}
		
		if (!validaData(form.dataIniValidadeSgti,false,true,true)){
			if(form.dataIniValidadeSgti.value == ""){
				alert("<%=_msg.getMensagem("segmentoItem.validacao.dataIniValidadeSgti.obrigatorio")%>");
			}else{
				alert("<%=_msg.getMensagem("segmentoItem.validacao.dataIniValidadeSgti.invalido")%>");
			}
			return false;
		}
		
		if (!validaData(form.dataFimValidadeSgti,false,true,true)){
			if(form.dataFimValidadeSgti.value == ""){
				alert("<%=_msg.getMensagem("segmentoItem.validacao.dataFimValidadeSgti.obrigatorio")%>");
			}else{
				alert("<%=_msg.getMensagem("segmentoItem.validacao.dataFimValidadeSgti.invalido")%>");
			}
			return false;
		}
		
		var dtIni;
		var dtLim;
		
//      Comentado em 12/01/2011 - Regra de negócio ainda não confirmada.		
//		var dtItem;
		
		dtIni = dataInversa(form.dataIniValidadeSgti.value);
		dtLim = dataInversa(form.dataFimValidadeSgti.value);
		
//      Comentado em 12/01/2011 - Regra de negócio ainda não confirmada.		
//		dtItem = dataInversa(form.dataItemSgti.value);
		
		if (dtIni > dtLim){
			alert("<%=_msg.getMensagem("segmentoItem.validacao.dataFimValidadeSgti.dataFinalMenorQueDataInicial")%>");
			form.dataFimValidadeSgti.focus();
			return(false)
		}

//      Comentado em 12/01/2011 - Regra de negócio ainda não confirmada.		
/*
		if (dtItem < dtIni){
			alert("<%=_msg.getMensagem("segmentoItem.validacao.dataItemSgti.dataMenorQueDataInicial")%>");
			form.dataItemSgti.focus();
			return(false)		
		}

		if (dtItem > dtLim){
			alert("<%=_msg.getMensagem("segmentoItem.validacao.dataItemSgti.dataMaiorQueDataFim")%>");
			form.dataItemSgti.focus();
			return(false)		
		}
*/		
		if(form.indUtilizTpAcessoSgti.value == "S"){
			if(!validarCheckSelecionado(form, "tpAcessoSisAtributoSatb")){
				alert("<%=_msg.getMensagem("segmentoItem.validacao.tpAcessoSisAtributoSatb.obrigatorio")%>");
				return false;
			}
		}
<%
	}else if (codSegmento == (Long.valueOf(_msg.getMensagem("admPortal.pergFreq"))).intValue()){
%>
    	setHiddenVal('rte');
  	    form.richText.value = form.richText.value.replace(/>/g, "&gt;").replace(/\n/g, "");
  	    form.richText.value = form.richText.value.replace(/&gt;/g, ">");
  	    form.integraSgti.value = form.richText.value;
	    
		if(form.segmentoCategoriaSgtc.value == ""){
			alert("<%=_msg.getMensagem("segmentoItem.validacao.segmentoCategoriaSgtc.obrigatorio")%>");
			form.segmentoCategoriaSgtc.focus();
			return false;
		}
		
		if(form.hidTamListaEditorias.value <= 0){
			alert("<%=_msg.getMensagem("segmentoItem.validacao.editoriasSisAtributoSatb.naoCadastradas")%>");
			return false;
		}else{
			if(!validarCheckSelecionado(form, "editoriasSisAtributoSatb")){
				alert("<%=_msg.getMensagem("segmentoItem.validacao.editoriasSisAtributoSatb.obrigatorio")%>");
				return false;
			}
		}
		
		if (!validaString(form.tituloSgti, "Pergunta", true)){
			return false;
		}
		
		if (!validaString(form.integraSgti, "Resposta", true)){
			return false;
		}
		
		if(form.segmentoItemFonteSgtif.value == ""){
			alert("<%=_msg.getMensagem("segmentoItem.validacao.segmentoItemFonteSgtif.obrigatorio")%>");
			form.segmentoItemFonteSgtif.focus();
			return false;
		}
		
		if (!validaData(form.dataItemSgti,false,true,true)){
			if(form.dataItemSgti.value == ""){
				alert("<%=_msg.getMensagem("segmentoItem.validacao.dataItemSgti.obrigatorio")%>");
			}else{
				alert("<%=_msg.getMensagem("segmentoItem.validacao.dataItemSgti.invalido")%>");
			}
			return false;
		}
		
		
		
		if(form.indUtilizTpAcessoSgti.value == "S"){
			if(!validarCheckSelecionado(form, "tpAcessoSisAtributoSatb")){
				alert("<%=_msg.getMensagem("segmentoItem.validacao.tpAcessoSisAtributoSatb.obrigatorio")%>");
				return false;
			}
		}
<%
	}else if (codSegmento == (Long.valueOf(_msg.getMensagem("admPortal.glossario"))).intValue()){
%>
    	setHiddenVal('rte');
  	    form.richText.value = form.richText.value.replace(/>/g, "&gt;").replace(/\n/g, "");
  	    form.richText.value = form.richText.value.replace(/&gt;/g, ">");
  	    form.integraSgti.value = form.richText.value;
	    
		if(form.segmentoCategoriaSgtc.value == ""){
			alert("<%=_msg.getMensagem("segmentoItem.validacao.segmentoCategoriaSgtcLetra.obrigatorio")%>");
			form.segmentoCategoriaSgtc.focus();
			return false;
		}
		
		if(form.hidTamListaEditorias.value <= 0){
			alert("<%=_msg.getMensagem("segmentoItem.validacao.editoriasSisAtributoSatb.naoCadastradas")%>");
			return false;
		}else{
			if(!validarCheckSelecionado(form, "editoriasSisAtributoSatb")){
				alert("<%=_msg.getMensagem("segmentoItem.validacao.editoriasSisAtributoSatb.obrigatorio")%>");
				return false;
			}
		}
		
		if (!validaString(form.tituloSgti, "Termo", true)){
			return false;
		}
		
		if (!validaString(form.integraSgti, "Descrição", true)){
			return false;
		}
		
		if(form.segmentoItemFonteSgtif.value == ""){
			alert("<%=_msg.getMensagem("segmentoItem.validacao.segmentoItemFonteSgtif.obrigatorio")%>");
			form.segmentoItemFonteSgtif.focus();
			return false;
		}
		
		if (!validaData(form.dataItemSgti,false,true,true)){
			if(form.dataItemSgti.value == ""){
				alert("<%=_msg.getMensagem("segmentoItem.validacao.dataItemSgti.obrigatorio")%>");
			}else{
				alert("<%=_msg.getMensagem("segmentoItem.validacao.dataItemSgti.invalido")%>");
			}
			return false;
		}
						
		if(form.indUtilizTpAcessoSgti.value == "S"){
			if(!validarCheckSelecionado(form, "tpAcessoSisAtributoSatb")){
				alert("<%=_msg.getMensagem("segmentoItem.validacao.tpAcessoSisAtributoSatb.obrigatorio")%>");
				return false;
			}
		}
<%
	}else{
%>    	
    	setHiddenVal('rte');
  	    form.richText.value = form.richText.value.replace(/>/g, "&gt;").replace(/\n/g, "");
  	    form.richText.value = form.richText.value.replace(/&gt;/g, ">");
  	    form.integraSgti.value = form.richText.value;
  	    
		if(form.segmentoSgt.value == ""){
			alert("<%=_msg.getMensagem("segmentoItem.validacao.segmentoSgt.obrigatorio")%>");
			form.segmentoSgt.focus();
			return false;
		}
		
		if(form.segmentoCategoriaSgtc.value == ""){
			alert("<%=_msg.getMensagem("segmentoItem.validacao.segmentoCategoriaSgtc.obrigatorio")%>");
			form.segmentoCategoriaSgtc.focus();
			return false;
		}
		
		if(form.hidTamListaEditorias.value <= 0){
			alert("<%=_msg.getMensagem("segmentoItem.validacao.editoriasSisAtributoSatb.naoCadastradas")%>");
			return false;
		}else{
			if(!validarCheckSelecionado(form, "editoriasSisAtributoSatb")){
				alert("<%=_msg.getMensagem("segmentoItem.validacao.editoriasSisAtributoSatb.obrigatorio")%>");
				return false;
			}
		}
		
		if (!validaString(form.tituloSgti, "Título", true)){
			return false;
		}
		
		if (!validaString(form.linhaApoioSgti, "Linha de Apoio", true)){
			return false;
		}
		
		if (!validaString(form.integraSgti, "Íntegra", true)){
			return false;
		}
		
		if(form.segmentoItemFonteSgtif.value == ""){
			alert("<%=_msg.getMensagem("segmentoItem.validacao.segmentoItemFonteSgtif.obrigatorio")%>");
			form.segmentoItemFonteSgtif.focus();
			return false;
		}
		
		if (!validaData(form.dataItemSgti,false,true,true)){
			if(form.dataItemSgti.value == ""){
				alert("<%=_msg.getMensagem("segmentoItem.validacao.dataItemSgti.obrigatorio")%>");
			}else{
				alert("<%=_msg.getMensagem("segmentoItem.validacao.dataItemSgti.invalido")%>");
			}
			return false;
		}
		
		if (!validaData(form.dataIniValidadeSgti,false,true,true)){
			if(form.dataIniValidadeSgti.value == ""){
				alert("<%=_msg.getMensagem("segmentoItem.validacao.dataIniValidadeSgti.obrigatorio")%>");
			}else{
				alert("<%=_msg.getMensagem("segmentoItem.validacao.dataIniValidadeSgti.invalido")%>");
			}
			return false;
		}
		
		if (!validaData(form.dataFimValidadeSgti,false,true,true)){
			if(form.dataFimValidadeSgti.value == ""){
				alert("<%=_msg.getMensagem("segmentoItem.validacao.dataFimValidadeSgti.obrigatorio")%>");
			}else{
				alert("<%=_msg.getMensagem("segmentoItem.validacao.dataFimValidadeSgti.invalido")%>");
			}
			return false;
		}
		
		var dtIni;
		var dtLim;
		
		dtIni = dataInversa(form.dataIniValidadeSgti.value);
		dtLim = dataInversa(form.dataFimValidadeSgti.value);
		
		if (dtIni > dtLim){
			alert("<%=_msg.getMensagem("segmentoItem.validacao.dataFimValidadeSgti.dataFinalMenorQueDataInicial")%>");
			form.dataFimValidadeSgti.focus();
			return(false)
		}
		
		if(form.indUtilizTpAcessoSgti.value == "S"){
			if(!validarCheckSelecionado(form, "tpAcessoSisAtributoSatb")){
				alert("<%=_msg.getMensagem("segmentoItem.validacao.tpAcessoSisAtributoSatb.obrigatorio")%>");
				return false;
			}
		}
<%
	}
%>
	return true;
}
