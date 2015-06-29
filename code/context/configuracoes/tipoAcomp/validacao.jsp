
<jsp:directive.page import="java.util.Iterator"/>
<jsp:directive.page import="ecar.dao.TipoFuncAcompDao"/>
<jsp:directive.page import="ecar.pojo.TipoFuncAcompTpfa"/>

<script language="javascript">
var funcaoAcompanhamento = new Array();
var cont = 0;
/* Inicializa o array com as fun��es de acompanhamento listadas */
<%
Iterator itFuncoesAcompanhamento = (new TipoFuncAcompDao(request)).getFuncAcompByLabel().iterator();

while(itFuncoesAcompanhamento.hasNext()){
	TipoFuncAcompTpfa funcaoAcompanhamento = (TipoFuncAcompTpfa) itFuncoesAcompanhamento.next();
	%>
	funcaoAcompanhamento[cont++] = '<%= funcaoAcompanhamento.getCodTpfa()%>';
	<%
}

%>

function validarAlterar(form){
	if(!validaString(form.descricaoTa,"Descri��o",true)){
		return(false);
	}
	
	if (document.getElementById('hidIndLiberarParecerTa') ){
		if (form.hidIndLiberarParecerTa.value=='S' && (!form.indLiberarParecerTa.checked) ){
			if (!confirm ('Os acompanhamentos com pareceres gravados e n�o liberados ser�o sinalizados como liberados. \n\n' +
						'Esta opera��o n�o poder� ser revertida. Deseja confirmar a altera��o na configura��o do ' +
						'Tipo de Acompanhamento para N�O exigir libera��o de parecer?')){
				return false		
				}
		
		}
	}
	
	if(!validaString(form.seqApresentacaoTa, "Sequ�ncia de Apresenta��o",true)){
		return(false);
	}else{
		if(!validaNum(form.seqApresentacaoTa, "Sequ�ncia de Apresenta��o", false)){
			alert("<%=_msg.getMensagem("tipoAcompanhamento.validacao.seqApresentacaoTa.invalido")%>");
			form.seqApresentacaoTa.focus();
			return(false);
		}
	}
	
	mensagem  = validaRegistroDePosicao();
	if (mensagem.length > 0 ){
		alert (mensagem)
		return false;
	} 
	return(true);
}

function validarGravar(form){
	if(!validaString(form.descricaoTa,"Descri��o",true)){
		return(false);
	}
	
	if(!validaString(form.seqApresentacaoTa, "Sequ�ncia de Apresenta��o",true)){
		return(false);
	}else{
		if(!validaNum(form.seqApresentacaoTa, "Sequ�ncia de Apresenta��o", false)){
			alert("<%=_msg.getMensagem("tipoAcompanhamento.validacao.seqApresentacaoTa.invalido")%>");
			form.seqApresentacaoTa.focus();
			return(false);
		}
	}
	
	mensagem  = validaRegistroDePosicao();
	if (mensagem.length > 0 ){
		alert (mensagem)
		return false;
	} 
	
	return(true);
}

function validarPesquisar(form){
	if(trim(form.seqApresentacaoTa) != ""){
		if(!validaNum(form.seqApresentacaoTa, "Sequ�ncia de Apresenta��o", false)){
			alert("<%=_msg.getMensagem("tipoAcompanhamento.validacao.seqApresentacaoTa.invalido")%>");
			form.seqApresentacaoTa.focus();
			return(false);
		}
	}
	return(true);
}

function aoClicarValidarCheckObrigatorio(num){
	if (eval("document.form.indObrigatorio" + num + ".checked")) {
		eval("document.form.indOpcional" + num + ".checked = false");
	}
}

function aoClicarValidarCheckOpcional(num){
	if (eval("document.form.indOpcional" + num + ".checked")) {
		eval("document.form.indObrigatorio" + num + ".checked = false");
	}
}

function limparRadioButtons(radioObj){
		
	var radioLength = radioObj.length;
	if(radioLength == undefined) {
		radioObj.checked = false;
		
	}
	for(var i = 0; i < radioLength; i++) {
		radioObj[i].checked = false;
	}	
}

	/* Faz a valida��o do bot�o de radio. Para cada fun��o de acompahamento quando estiver marcado
		opcional ou obrigat�rio o registro de Posi��o correspondente dever� ser escolhido para n�o dar problema de null.
	*/
function validaRegistroDePosicao(){
	
	mensagem ='';
	for (i=0; i < funcaoAcompanhamento.length; i++) {
	
		indObrigatorio = document.getElementsByName('indObrigatorioOpcional' + funcaoAcompanhamento[i] );
		indRegistroPosicao = document.getElementsByName('indRegistroPosicao' + funcaoAcompanhamento[i] );
		
		
		if (document.getElementsByName('indObrigatorioOpcional' + funcaoAcompanhamento[i] )) {
			if (indObrigatorio[0].checked || indObrigatorio[1].checked) {
				if ( !( indRegistroPosicao[0].checked || indRegistroPosicao[1].checked) ){
					mensagem += '\t- '+ eval('document.form.funcaoAcomp'+funcaoAcompanhamento[i] +'.value') + '\n' ;
				}
			}
		}
	}
	
	if (mensagem != ''){
		mensagem = '<%=_msg.getMensagem("tipoAcompanhamento.validacao.obrigatorio.RegistroPosicao")%> para: \n' + mensagem;
	}
	
	return mensagem;
}
	

</script>
