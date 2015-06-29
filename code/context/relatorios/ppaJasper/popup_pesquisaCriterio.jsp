<%@ taglib uri="/WEB-INF/ecar-util.tld" prefix="util" %>
<%@ include file="/frm_global.jsp"%>
<%@ include file="/login/validaAcesso.jsp"%>
<%@ page import="ecar.popup.PopUpPesquisa" %>
<%@ page import="java.util.Enumeration" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.lang.Math" %>

<html lang="pt-br">
<head>
<%@ include file="../../include/meta.jsp"%>
<%@ include file="/titulo.jsp"%>
<link rel="stylesheet" href="<%=_pathEcar%>/css/style_<%=configuracaoCfg.getEstilo().getNome()%>.css" type="text/css">
<script language="javascript" src="../../js/focoInicial.js"></script>
<!--<script language="javascript" src="../../js/frmPesquisa.js"></script>-->
<SCRIPT language="Javascript">

function verificaChecks(form, nome) {
	var el = document.getElementsByTagName("INPUT")
	var i = 0, n = 0;

	while (i < el.length) {								
		if (el.item(i).type == "checkbox" && el.item(i).name == nome)
			n++;
		i++;
	}
	return n; 
}

function aoClicarAdicionar(form, funcaoGetDadosPesquisa){
	var codigosSelecionadosCom = "";
	var codigosSelecionadosSem = "";

    if (typeof funcaoGetDadosPesquisa == "undefined") funcaoGetDadosPesquisa = "getDadosPesquisa";

	for (i = 0; i < form.hidOpcoesCom.length; i++) {
		if (form.hidOpcoesCom[i].checked == true) {
			codigosSelecionadosCom += form.hidOpcoesCom[i].value + "|";
		}
	}

	for (i = 0; i < form.hidOpcoesSem.length; i++) {
		if (form.hidOpcoesSem[i].checked == true) {
			codigosSelecionadosSem += form.hidOpcoesSem[i].value + "|";
		}
	}
	//chama a funcao definida pelo programador para retornar os dados da pesquisa
	eval('window.opener.' + funcaoGetDadosPesquisa)(codigosSelecionadosCom, codigosSelecionadosSem, "Teste");
	window.close()
}

function aoClicarCancelar(form){
	window.close();
}

/* opcao --> C: Com; S: Sem*/
function controleCheck(form, opcao, item){
	if(opcao == "C"){ //Com
		for (i = 0; i < form.hidOpcoesCom.length; i++) {
			if (form.hidOpcoesSem[i].value == item && form.hidOpcoesSem[i].checked == true) {
				form.hidOpcoesSem[i].checked = false;
				break;
			}
		}
	}

	if(opcao == "S"){ //Sem
		for (i = 0; i < form.hidOpcoesSem.length; i++) {
			if (form.hidOpcoesCom[i].value == item && form.hidOpcoesCom[i].checked == true) {
				form.hidOpcoesCom[i].checked = false;
				break;
			}
		}
	}
}

</SCRIPT>

</head>

<!-- sempre colocar o foco inicial no primeiro campo -->
<!-- <body onload="onLoad(document.form);" class="corpo_popup"> -->
<body class="corpo_popup">
<form name="form" method="post">
	
<%
	try { 
		/*
		 * Formulario template de pesquisa
		 * Para cada tela que precisa exibir um popup de pesquisa, criar uma classe
		 * que implementa a iterface PopUpPesquisa
		 */
		 
		/* define a quantidade de itens exibidos por pagina */
		final int ITENS_PAGINA = configuracaoCfg.getNumRegistros().intValue();//_msg.getQtdeItensPaginaPesquisa("popup.pesquisa.itensPagina");
		
		/* lista que contém o resultado da pesquisa */
		List lista;

		/* instancia o pojo que vai ser utilizado na pesquisa */
		Class c = Class.forName(Pagina.getParamStr(request, "hidPojo"));
		PopUpPesquisa p = (PopUpPesquisa) c.newInstance();

		/* controles de página */
		int hidNumPagina = Pagina.getParamInt(request, "hidNumPagina");
		int hidTotPagina = Pagina.getParamInt(request, "hidTotPagina");

		/*
		 * Acao = nada - entrou na pagina; "pesquisar" - deve pesquisar; "navegar" - deve navegar
		 */
		String hidAcao = Pagina.getParamStr(request, "hidAcao");
		
		/* persiste o pojo que deve ser pesquisado */
		String hidPojo = Pagina.getParamStr(request, "hidPojo");

		/* pega os códigos dos critérios que já estão cadastrados para que não apareçam na pesquisa */
		String jaIncluidosCom = Pagina.getParamStr(request, "jaIncluidosCom");
		String jaIncluidosSem = Pagina.getParamStr(request, "jaIncluidosSem");
		
		/* funcao que vai receber os retorno da pesquisa ao clicar adicionar */
		String hidFuncao = Pagina.getParamStr(request, "hidFuncao");
		
		session.removeAttribute("lista");
		p.setArgPesquisa(request);
		lista = p.pesquisar();
		session.setAttribute("lista", lista);
		hidNumPagina = 0;
		hidTotPagina = 0;
		
		Enumeration e = request.getParameterNames();
		String hidden = new String();
		
		/* Recupera os valores passados pelo request em campos hidden */
		/*    excluindo os campos fixos do popup_pesquisa, onde foi adotado que seus */
		/*    nomes iniciem com "hid" diferenciando dos demais que venham do request */
		/*    evitando de duplicar campos hidden com mesmo nome. */
		while (e.hasMoreElements()){
			hidden = (String) e.nextElement();
			
			if(!hidden.startsWith("hid")){
%>
				<input type="hidden" name="<%=hidden%>" value="<%=Pagina.getParamStr(request, hidden)%>">
<%
			}
		}
%>		

		<input type="hidden" name="hidPojo" value="<%=hidPojo%>">
		<input type="hidden" name="hidAcao" value="<%=hidAcao%>">
		<input type="hidden" name="hidFuncao" value="<%=hidFuncao%>">
		
		<h1>Seleção de <%=p.getTitulo()%></h1>
		<%

		int limite = lista.size();
		if (limite > 0) { 
%>
			<table cellpadding="3">
			<tr>
				<td class="texto">Com</td>
				<td class="texto">Sem</td>
				<td>&nbsp;</td>
			</tr>
				
<%
			for (int i = 0; i < lista.size(); i++) {
				p.setPojo(lista.get(i));
				
				/*
				 * Este bloco abaixo verifica se o item da vez já está incluso no array de códigos
				 * que foi passado em jaIncluidosCom e jaIncluidosSem
				 */
				
				String _checkedCom = "";
				String _checkedSem = "";
				
				if(jaIncluidosCom.length() > 0){
					String[] codigosIncluidosCom = jaIncluidosCom.split("\\|");
					for(int j = 0; j < codigosIncluidosCom.length; j++){
						if(p.getCodigo().equals(codigosIncluidosCom[j])){
							_checkedCom = "checked";
							break;	
						}
					}
				}
				
				if(jaIncluidosSem.length() > 0){
					String[] codigosIncluidosSem = jaIncluidosSem.split("\\|");
					for(int j = 0; j < codigosIncluidosSem.length; j++){
						if(p.getCodigo().equals(codigosIncluidosSem[j])){
							_checkedSem = "checked";
							break;	
						}
					}
				}

				out.println(
				"<tr>" +
				"<td>" + 
					"<input type=\"checkbox\" class=\"form_check_radio\" name=\"hidOpcoesCom\" value=\"" +	p.getCodigo() + "\" " + _checkedCom + " onclick=\"javascript:controleCheck(form, 'C'," +	p.getCodigo() + ")\">" +
				"</td>"+
				"<td>" + 
					"<input type=\"checkbox\" class=\"form_check_radio\" name=\"hidOpcoesSem\" value=\"" +	p.getCodigo() + "\" " + _checkedSem + "  onclick=\"javascript:controleCheck(form, 'S'," +	p.getCodigo() + ")\">" +
				"</td>"+
				"<td class=\"texto\">" + p.getDescricao());
	
				out.println("<input type=\"hidden\" name=\"hidAux" + p.getCodigo() + "\" value=\"" + p.getDescricao() + "\"></td></tr>");
			}
%>
			</table>
			<center>
			<input type="button" name="btnAdicionar" class="botao" value="OK" onclick="aoClicarAdicionar(document.form, '<%=hidFuncao%>');">			
			<input type="button" name="btnCancelar" class="botao" value="Cancelar" onclick="aoClicarCancelar(document.form);">
			</center>
<%
		}
		else{
%>
			<table>
				<tr><td class="texto">
					<b>Nenhum registro foi encontrado para os critérios de pesquisa especificados</b>
				</td></tr>
			</table>
<%
		}
	 } catch (ECARException e) {
		 org.apache.log4j.Logger.getLogger(this.getClass()).error(e);
		 Mensagem.alert(_jspx_page_context, _msg.getMensagem("erro.exception"));
%>
		<script language="javascript">
		aoClicarCancelar(document.form);
		</script>
<%
	 }
%>	
	
</form>
</body>
</html>
