<%@ taglib uri="/WEB-INF/tlds/c.tld" prefix="c"%>

<%@ page import="ecar.dao.SisGrupoAtributoDao" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.Set"%>
<%@ page import="ecar.dao.ConfiguracaoDao" %>
<%@ page import="ecar.dao.ItemEstrtIndResulDao" %>
<%@ page import="ecar.pojo.SisGrupoAtributoSga" %>
<%@ page import="ecar.pojo.SisAtributoSatb" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="ecar.pojo.AtributoLivre" %>

<%@ page import="ecar.pojo.ItemEstrtIndResulCorIettrcor" %>
<%@ page import="ecar.dao.CorDao" %>
<%@ page import="ecar.pojo.Cor" %>
<%@ page import="ecar.pojo.ItemEstrtIndResulCorIettrcorPK" %>
<%@ page import="ecar.dao.ItemEstrtIndResulCorIettrcorDAO" %>
<%@page import="ecar.pojo.UsuarioUsu"%>

<%@page import="ecar.pojo.LocalGrupoLgp"%>

<%@page import="ecar.pojo.ConfiguracaoCfg"%>
<%@page import="ecar.dao.ExercicioDao"%><jsp:directive.page import="java.util.Iterator"/>
<jsp:directive.page import="ecar.pojo.ItemEstrtIndResulIettr"/>
<jsp:directive.page import="ecar.dao.ServicoDao"/>
<jsp:directive.page import="ecar.pojo.ServicoSer"/>


<script language="javascript" src="../../js/form_cadItem_indresultado.js"></script> 
<!--  <script language="javascript" src="../../js/prototype.js"></script> -->	
<script language="javascript" src="../../js/popup.js"></script>	
<script language="javascript" src="../../js/datas.js"></script>	

<script language="javascript" src="../../js/jquery.js"></script>
<script language="javascript" src="../../js/jquery_autocomplete.js"></script>
<link rel="stylesheet" href="../../css/jquery_autocomplete.css" type="text/css">
<%
	ConfiguracaoCfg configura = (ConfiguracaoCfg)session.getAttribute("configuracao");
%>
<link rel="stylesheet" href="<%=_pathEcar%>/css/style_<%=configura.getEstilo().getNome()%>.css" type="text/css">

<jsp:directive.page import="comum.util.Pagina"/>
<jsp:directive.page import="ecar.pojo.PeriodicidadePrdc"/>

<script language="javascript">

function verificaPreenchimentoCelula(element){

    // Para não ficar testando todos elementos a qualquer momento,
    //testa somente em casos de interesse
    //Ou quando o elemento foi preenchido e a combo de abrangencia está habilitada
    //Ou quando saiu com branco e a combo está desabilitada
	if(((element.value != '')&&(!document.getElementById('indPrevPorLocal1').disabled))||((element.value == '')&&(document.getElementById('indPrevPorLocal1').disabled))){

	var temValores = false;

    	jQuery(".previsto").each(function(){
            if((this).value != ''){
            	temValores = true;
            	return false;
            }
         });
      
		if (!temValores){
			document.getElementById('existePrevistos').value = 'N';
			jQuery("[@name=indPrevPorLocal]").attr("disabled", false);

		}	
		else
		{	
			document.getElementById('existePrevistos').value = 'S';
			jQuery("#indPrevPorLocal2").attr("checked", "checked");
			jQuery("[@name=indPrevPorLocal]").attr("disabled", true);
			

	   }

  }
}

function atualizaValor()
{
	var valueSelect = document.getElementById('abrangenciaLocal').value; 

	document.getElementById('nivelAbrangencia').value = valueSelect; 
 	
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

function mostraBotoesPorLocal(mostrarBotoes, mostrarGrid){
	
	var botoesDisabled = (mostrarBotoes == 'N');
	var gridDisabled = (mostrarGrid == 'N');
	
	$("[@name=btnPorLocal]").attr("disabled", botoesDisabled);
	
	$(".previsto").attr("disabled", gridDisabled);

	//limpa os valores previstos.
	$(".previsto").attr("value", "");
	
}

function controlaNivelAbrangencia(){

	var valueSelect;

	if (document.getElementById('indPrevPorLocal1').checked)
		valueSelect = 'S';
	else
		valueSelect = 'N';
	
	document.getElementById('previstoPorLocal').value = valueSelect; 

	
    //Os dois radios marcados com não, desabilita a combo de abrangencia
	if((document.getElementById('indPrevPorLocal2').checked)&&(document.getElementById('indRealPorLocal2').checked)){
		document.getElementById('abrangenciaLocal').selectedIndex = 0;
		document.getElementById('abrangenciaLocal').disabled = true;
	}

	if ((!document.getElementById('indPrevPorLocal1').disabled)&&(document.getElementById('indPrevPorLocal1').checked)&&((!document.getElementById('indRealPorLocal1').disabled)||((document.getElementById('indRealPorLocal2').disabled)&&(document.getElementById('indRealPorLocal2').checked)))){
		document.getElementById('abrangenciaLocal').disabled = false;
	}

	if((!document.getElementById('indRealPorLocal1').disabled)&&(document.getElementById('indRealPorLocal1').checked)&&((!document.getElementById('indPrevPorLocal1').disabled)||((document.getElementById('indPrevPorLocal2').disabled)&&(document.getElementById('indPrevPorLocal2').checked)))){
		document.getElementById('abrangenciaLocal').disabled = false;		
	}

	
}


</script>

<%
	ExercicioDao exercicioDao = new ExercicioDao(request);
	String codigosCor = "";
	String pathCores = "";
	String titleCores = "";
	
	
%>


	ENTROU NO FORM!!!!
	<%@ include file="../../include/estadoMenu.jsp"%>
