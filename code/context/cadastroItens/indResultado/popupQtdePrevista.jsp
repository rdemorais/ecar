<%@ page isELIgnored="false"%>
<%@ include file="../../frm_global.jsp"%>
<%@ page import="ecar.pojo.LocalGrupoLgp"%>
<%@ page import="ecar.pojo.ItemEstruturaIett"%>
<%@ page import="ecar.pojo.ExercicioExe"%>
<%@ page import="ecar.dao.ItemEstruturaDao"%>
<%@ page import="ecar.dao.EstruturaDao"%>
<%@ page import="ecar.dao.ExercicioDao"%>
<%@ page import="java.util.Set"%>
<%@ page import="java.util.List"%>
<%@ page import="comum.util.Util"%>

<%@ page import="java.util.ArrayList"%>
<%@ page import="ecar.pojo.ItemEstrutLocalIettl"%>
<%@ page import="java.util.Iterator"%>
<%@ page import="ecar.pojo.LocalItemLit"%>
<%@ page import="ecar.pojo.EstruturaEtt"%>
<%@ page import="java.util.GregorianCalendar"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/sql" prefix="sql"%>
<%@ taglib uri="/WEB-INF/ecar-util.tld" prefix="util"%>
<%@ taglib uri="/WEB-INF/ecar-combo.tld" prefix="combo"%>
<%@ taglib uri="/WEB-INF/ecar-input.tld" prefix="ecar"%>
<%@ taglib uri="/WEB-INF/ecar-locais.tld" prefix="locais"%>

<html lang="pt-br">
<head>
<%@ include file="../../include/meta.jsp"%>
<%@ include file="../../include/exibirAguarde.jsp"%>
<title>Quantidade Prevista por Local e Mês</title>
<link rel="stylesheet" href="<%=_pathEcar%>/css/style_<%=new ecar.dao.ConfiguracaoDao(request).getConfiguracao().getEstilo().getNome()%>.css"
	type="text/css">
<script language="javascript" src="../../js/prototype.js"></script>
<script language="javascript" src="../../js/jquery.js"></script>
<script type="text/javascript">jQuery.noConflict();</script>		
<script language="javascript" src="../../js/form_cadItem_indresultado.js"></script>
<script language="javascript" src="../../js/validacoes.js"></script>

</head>

<script language="JavaScript">
<%
	String tipoQuantidade;	
	if(Pagina.getParamStr(request, "tipoQuantidade")!=null){
		tipoQuantidade = Pagina.getParamStr(request, "tipoQuantidade");
	}else{
		tipoQuantidade = "Q";
	}
%>
tipoQtd="<%=tipoQuantidade%>";
function aoClicarVoltar(Form){
 	alert('aoClicarVoltar');
}
function validarQtdeValor(form, qtdValor){
	
	var qtdValorAux = qtdValor;
		
	if (qtdValor.indexOf(',') != -1){
		var indiceVirgula = qtdValor.indexOf(',');
		qtdValorAux = qtdValor.substring(0, indiceVirgula);
	}
	if (qtdValorAux.length > 12){
		alert("<%=_msg
							.getMensagem("itemEstrutura.indResultado.validacao.qtdPrevistaIettf.MaiorQuePermitido")%>");
		return false;
	}
	
	return (true);
}

function aoClicarAlterar(Form){

	// Obtendo a referência para a tela principal e ao form
	// da popup
	var documentMaster = window.opener.document;
	var form = $('qtdeForm');
	var temValoresNoMes = false;
	var temValoresNoPopUp = false;

	// Obtendo os códigos dos exercícios
	var vetorCodigosExercicios = new Array();
	var tamVetorCodigos = 0;
	var ano = document.getElementById("ano").value;
	for (var i=0;i<form.length;i++){
		if (form.elements[i].name.match("mes") != null){
			tamVetorCodigos = vetorCodigosExercicios.push(form.elements[i]);
		}
	}

	if (!validarQtdeValor(Form, document.getElementById('totalGeral').value )){
       	return false;
    }


	jQuery(".inputPopup").each(function(){
        if((this).value != ''){
        	temValoresNoPopUp = true;
        	return false;
        }
     });
	
//	if ((document.getElementById('totalGeral').value != '0,00')&&(document.getElementById('totalGeral').value != '0')){	 
    if (temValoresNoPopUp){
		documentMaster.getElementById('existePrevistos').value = 'S';
		jQuery("[@name=indPrevPorLocal]", window.opener.document).attr("disabled", true);

		// Atribuindo os totais calculados na popup aos campos
		// correspondentes na tela principal.	
		for (var i = 0; i < tamVetorCodigos; i++){
			
	        var valorMes = vetorCodigosExercicios[i].value;

	        if (valorMes.length == 1)
	        {
			 valorMes = '0'+valorMes;
	        }

			var nomeCampoTelaPrincipal = "qtdPrevistaIettf" + ano + valorMes;
			var nomeCampoPopup = "qtdPrevistaIettf" + valorMes;
	        
			temValoresNoMes = false;
			jQuery(".previsto"+valorMes).each(function(){
	            if((this).value != ''){
	            	temValoresNoMes = true;
	            	return false;
	            }
	         });

 	        if (temValoresNoMes){
				if (!validarQtdeValor(Form, document.getElementById(nomeCampoPopup).value )){
						//converterParaMoeda( document.getElementById(nomeCampoPopup).value ))){
					return false;
				}
				//alert(documentMaster.getElementById(nomeCampoTelaPrincipal).name);
				documentMaster.getElementById(nomeCampoTelaPrincipal).value = $(nomeCampoPopup).value;
			} else {
					documentMaster.getElementById(nomeCampoTelaPrincipal).value = '';
					}
		}

		
        //se o radio do realizado por local estiver bloqueado, não pode liberar a combo de abrangencia
        //em qualquer situação
        //se não, depende do preenchimento do local ou não

        if (documentMaster.getElementById('existeAcompRealFisico').value == 'N'){
        	documentMaster.getElementById('abrangenciaLocal').disabled = true;
        }
        
        
		
    }
    else
    {
    	// Atribuindo brancos no ano que não terá mais valores
    	// correspondentes na tela principal.	
    	for (var i = 0; i < tamVetorCodigos; i++){
    		
            var valorMes = vetorCodigosExercicios[i].value;
            if (valorMes.length == 1)
            {
    		 valorMes = '0'+valorMes;
            }
    		var nomeCampoTelaPrincipal = "qtdPrevistaIettf" + ano + valorMes;
    		documentMaster.getElementById(nomeCampoTelaPrincipal).value = '';
    	}

    	var temValores = false;

    	jQuery(".previsto", window.opener.document).each(function(){
            if($(this).value != ''){
            	temValores = true;
            	return false;
            }
         });
          

    	if (!temValores){
    		documentMaster.getElementById('existePrevistos').value = 'N';
    		jQuery("[@name=indPrevPorLocal]", window.opener.document).attr("disabled", false);

        	if (documentMaster.getElementById('existeAcompRealFisico').value == 'N'){
	        	documentMaster.getElementById('abrangenciaLocal').disabled = false;
    	    }

    	}	
    }


	
	if(document.getElementById("codIettir") != null)	
		var codIettir = document.getElementById("codIettir").value;	

	salvarLocal();

}

function aoClicarVoltar(){
		window.close();
}

// monta tooltip e apresenta em uma popup
function montaTooltipMouseClick(mes, nomeMes, codLocal, nomeLocal){

	// Se a dica for relativa a checkbox " Copiar Quantidade(s) para o(s) exercício(s) posterior(es)?"
	// Exibir mensagem padrão. Senão exibir mensagem dinâmica relativa aos campos.
	if (mes == -1 && nomeMes == "" && codLocal == -1 && nomeLocal == ""){
		var htmlResult = '<br>Quando selecionada, essa opcao repete os<br>valores indicados no mês mais antigo para todos os demais meses. <br><br>Os valores ja fornecidos para os meses <br>posteriores nao sao afetados.';
	}
	else{
		var spanname = 'span|' + codLocal + '|' + mes;
		var cmpTotalExeNome = 'qtdPrevistaIettf' + mes ;
		var cmpTotalLocalNome = 'cmp|' + codLocal + '|total';
		var htmlResult = 'Mês: ' + nomeMes + '<br/>' + 'Local: ' + nomeLocal + '<br/><br/>Total por Mês: ' + $(cmpTotalExeNome).value + '<br/>Total por Local: ' + $(cmpTotalLocalNome).value + '<br/>';
	}

	var page = '<%=request.getContextPath()%>/popUp/toolTip.jsp?tip='+htmlResult;
	var options  = 'tollbar=no, directories=no, menubar=no, status=no, scrollbars=yes, ';
	    options += 'resizable=no, width=325, height=200';
	window.open(page, 'tooltip', options);

}

// Funcao gerar e apresentar tooltip com totais
function montaTooltipMouseOver(obj, ano, nomeMes, codLocal, nomeLocal){

	var spanname = 'span|' + codLocal + '|' + ano;

	if (ano > -1 && codLocal > -1){
		var cmpTotalExeNome = 'qtdPrevistaIettf' + ano ;
		var cmpTotalLocalNome = 'cmp|' + codLocal + '|total';
		var cmpTotalLocalValor = $(cmpTotalLocalNome).value;
		var cmpTotalExeValor = $(cmpTotalExeNome).value;

		$( spanname ).innerHTML = 'Mês: ' + nomeMes + '<br/>' + 'Local: ' + nomeLocal + '<br/><br/>Total por Mês: ' + cmpTotalExeValor + '<br/>Total por Local: ' + cmpTotalLocalValor + '<br/>';
	}
	else{
		$( spanname ).innerHTML = 'Quando selecionada, essa opcao repete os<br>valores indicados no mês mais antigo para todos os demais meses. <br><br>Os valores ja fornecidos para os meses <br>posteriores nao sao afetados.<br>';
	}

	var screenwidth = ( isIE() ? document.body.clientWidth : document.width );
	var objspan     = $(spanname);
	var imgposw     = 0;

    while (obj) { <%/* Obtem a posicao do objeto na tela */%>
        imgposw += obj.offsetLeft;
        obj      = obj.offsetParent;
    }

	if( !isIE() ) { <%/* Apenas mostra o tooltip se for != IE */%>
		objspan.className = ( screenwidth - imgposw <= 300 ? 'dicaright' : 'dicaleft');
    	objspan.style.display = 'block';				
	}
}

// Funcao gerar tooltip no evento onmouseout - faz tooltip sumir
function montaTooltipMouseOut( mes, codLocal ) {
	var spanname = 'span|' + codLocal + '|' + mes;
	var objspan = $(spanname);
   	objspan.style.display = 'none';
}


// copiar valores para demais exercicios
function copiarExercicio(localId){

	var form = $('qtdeForm');
	var total = 0.0;
	var campoReferencia;
	var indiceCampoReferencia;
 	var checkboxCopiar1 = $F('copiarQtd1');
 	var checkboxCopiar2 = $F('copiarQtd2'); 	
 	var result = 'false';
 	
 	if( checkboxCopiar1=='true' || checkboxCopiar2=='true' ){
 		result='true';
 	}else{
 		result='false';
 	}

	if ( result == 'true' ){
		for (var i=0;i<form.length;i++)
		{
			if(form.elements[i].type=='text'){
				var field = form.elements[i].name;
				var fieldArray = new Array();
				fieldArray = field.split('|');
				if ( fieldArray[1] == localId && fieldArray[2]!='total' ){	
					if ( form.elements[i].value != '' ) {
						campoReferencia = form.elements[i];
						indiceCampoReferencia = i;
					}
					if ( form.elements[i].value == '' && i > indiceCampoReferencia ) {
						form.elements[i].value = campoReferencia.value;	
						calcularTotalLocal(localId); 
						calcularTotalExercicio(fieldArray[2]);				
					}
				} 			
			}
		}
	}
 }
 
</script>

<body>
  <div id="divLocal"></div>
<%
	String codIett = Pagina.getParamStr(request, "codIett");
	String codIettir = Pagina.getParamStr(request, "codIettir");
	String ano = Pagina.getParamStr(request, "ano");
	String nivelAbrangencia = Pagina.getParamStr(request, "abrangenciaLocal");
	ItemEstruturaDao itemDao = new ItemEstruturaDao(request);
	EstruturaDao estruturaDao = new EstruturaDao(request);

	ItemEstruturaIett item = (ItemEstruturaIett) itemDao.buscar(ItemEstruturaIett.class, Long.valueOf(codIett));
	EstruturaEtt estruturaItens = (EstruturaEtt) estruturaDao.buscar(EstruturaEtt.class, item.getEstruturaEtt().getCodEtt());
	List locaisItem = new ArrayList(item.getItemEstrutLocalIettls());

	if (locaisItem == null || locaisItem.isEmpty()) {
%>
<table class="form" width="50%" align="center">
	<tr valign="middle">
		<td class="texto" style="font-size: 16px;" height="25px"></td>
	</tr>
	<tr valign="middle">
		<td class="texto" style="font-size: 16px;"><%=_msg.getMensagem("acompanhamento.realizadoFisico.quantidadePorLocal.faltaAbrangenciaItem")%>
		</td>
	</tr>
	<tr valign="middle">
		<td class="texto" style="font-size: 16px;"><b>Item:</b> 
		<%=new ItemEstruturaDao(request).criaColunaConteudoColunaArvoreAjax(item, estruturaItens)%>
		</td>
	</tr>
	<tr valign="bottom">
		<td class="texto" align="center" height="20" class="botao"><input
			type="button" onclick="window.close();" class="botao"
			value=" Fechar " /></td>
	</tr>
	<tr valign="middle">
		<td class="texto" style="font-size: 16px;" height="25px"></td>
	</tr>
</table>
<%
	} else {
%>
<form method="post" action="qtdePrevistaAction" name="qtdeForm"	id="qtdeForm">
	<input type="hidden" id="codIettir"	name="codIettir" value="<%=codIettir%>">
	<input type="hidden" id="ano"	name="ano" value="<%=ano%>">
	<input type="hidden" id="codIett" name="codIett" value="<%=Pagina.getParamStr(request, "codIett")%>">
	<input type="hidden" id="contextPath" name="contextPath" value="<%=request.getContextPath()%>"> <%

	
	List<GregorianCalendar> listaMeses = itemDao.GetMesesReferenciaFiltraAno(item, Long.valueOf(ano));

	for(GregorianCalendar mes: listaMeses){
		out.println(" <input type=\"hidden\" id=\"mes" + String.valueOf((mes.get(GregorianCalendar.MONTH)+1))	+ "\" name=\"mes" + String.valueOf((mes.get(GregorianCalendar.MONTH)+1)) + "\" value=\""	+ String.valueOf((mes.get(GregorianCalendar.MONTH)+1)) + "\" />");
		System.out.println(mes.get(GregorianCalendar.MONTH));
	}
	%>

    <util:barrabotoes alterar="Gravar" voltar="Cancelar" /> 
    <locais:GridLocaisItem	nivelAbrangencia="<%=nivelAbrangencia%>" tipo="<%=tipoQuantidade%>" ano="<%=ano%>" codItemEstrutura="<%=codIett%>"	codItemIndicadorResultado="<%=codIettir%>" /> 
    <util:barrabotoes	alterar="Gravar" voltar="Cancelar" /></form>


<%
	}
%>
<%@ include file="../../include/ocultarAguarde.jsp"%>
</body>

</html>
