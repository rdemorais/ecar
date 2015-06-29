
<jsp:directive.page import="ecar.pojo.AcompReferenciaAref"/>

<%@ include  file="../form_registro.jsp" %>		

<script type="text/javascript"> 


function reloadRegistro(){
	if(document.forms[0].periodo.value != ""){
		document.forms[0].action = "relatorios.jsp";
		document.forms[0].submit();		
	}	
}

function voltarTelaAnterior(tela) {
	document.forms[0].action = tela;
	document.forms[0].submit();
}

function trocarAba(nomeAba) {
	document.forms[0].action = nomeAba;
	document.forms[0].clicouAba.value = "S";
	document.forms[0].submit();
}


</script>