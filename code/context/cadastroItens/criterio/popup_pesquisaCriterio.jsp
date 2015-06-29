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

function aoClicarPesquisar(form){
	document.form.action="popup_pesquisaCriterio.jsp?hidAcao=pesquisar";
	document.form.submit();
}

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
	var selecionado = false;
	var codigosSelecionados = "";
	var checkCriteriosExibidos;

	if (typeof funcaoGetDadosPesquisa == "undefined") {
		funcaoGetDadosPesquisa = "getDadosPesquisa";
	}

	checkCriteriosExibidos = document.getElementsByName("hidOpcoes");

	for (i = 0; i < checkCriteriosExibidos.length; i++) {
		if (checkCriteriosExibidos[i].checked == true) {
			//chama a funcao definida pelo programador para retornar os dados da pesquisa
			//eval('window.opener.' + funcaoGetDadosPesquisa)(form.hidOpcoes[i].value, eval('form.hidAux' + form.hidOpcoes[i].value + '.value'));
			codigosSelecionados += checkCriteriosExibidos[i].value + "|";
			selecionado = true;
			//break;
		}
	}
	
	if (selecionado){
		eval('window.opener.' + funcaoGetDadosPesquisa)(codigosSelecionados, "Teste");
		window.close();
	} else {
		alert('Selecione uma opção!');
	}
}

function aoClicarCancelar(form){
	window.close();
}
</SCRIPT>

</head>

<!-- sempre colocar o foco inicial no primeiro campo -->
<body onload="onLoad(document.form);" class="corpo_popup">

<form name="form" method="post" action="popup_pesquisaCriterio.jsp?hidAcao=navegar">
<input type="hidden" name="ultimoIdLinhaDetalhado" value=<%=Pagina.getParamStr(request, "ultimoIdLinhaDetalhado")%>>

	
<%
	try { 
		/*
		 * Formulario template de pesquisa
		 * Para cada tela que precisa exibir um popup de pesquisa, criar uma classe
		 * que implementa a iterface PopUpPesquisa
		 */
		 
		/* define a quantidade de itens exibidos por pagina */
		final int ITENS_PAGINA = configuracaoCfg.getNumRegistros().intValue();
		
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
		String jaIncluidos = Pagina.getParamStr(request, "jaIncluidos");
		
		/* funcao que vai receber os retorno da pesquisa ao clicar adicionar */
		String hidFuncao = Pagina.getParamStr(request, "hidFuncao");
		
		if ("pesquisar".equalsIgnoreCase(hidAcao) || "".equalsIgnoreCase(hidAcao.trim())) {
			session.removeAttribute("lista");
			p.setArgPesquisa(request);
			lista = p.pesquisar();
			session.setAttribute("lista", lista);
			hidNumPagina = 0;
			hidTotPagina = 0;
		} 
		else if ("navegar".equalsIgnoreCase(hidAcao))
			lista = (List) session.getAttribute("lista");
		else
			lista = new ArrayList();
		
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
		
		<h1>Pesquisa de <%=p.getTitulo()%></h1>

		<table class="form">
			<tr>
				<td class="label">Argumento de Pesquisa:</td>
				<td>
					<input type="text" name="hidArg" size="30" value=""> 	
					<input type="button" name="btnOk" class="botao" value="Ok" onclick="aoClicarPesquisar(document.form);">
				</td>
			</tr>
			<tr>
				<td class="label">Pesquisar em:</td>
				<td class="texto">
	<% 
			String[] opcoes = p.getPesquisarEm();
			for (int i = 0; i < opcoes.length; i++) {
				if (i == 0)
					out.println("<input type=\"checkbox\" class=\"form_check_radio\" name=\"hidPesquisarEm\" value=\"" + i + "\" checked >" + opcoes[i] + "&nbsp;&nbsp;");
				else
					out.println("<input type=\"checkbox\" class=\"form_check_radio\" name=\"hidPesquisarEm\" value=\"" + i + "\">" + opcoes[i] + "&nbsp;&nbsp;");
			}
	%>	
				</td>
			</tr>
		</table>
		<h2>Resultado:</h2>
<%
		int limite = lista.size();
		if (limite > 0) { 
%>
			<table cellpadding="3">
<%
		}
		for (int i = 0; i < lista.size(); i++) {
			p.setPojo(lista.get(i));
			
			/*
			 * Este bloco abaixo verifica se o item da vez já está incluso no array de códigos
			 * que foi passado em jaIncluidos
			 */
			
			boolean listar = true;
			
			String _checked = "";
			
			if(jaIncluidos.length() > 0){
				String[] codigosIncluidos = jaIncluidos.split("\\|");
				for(int j = 0; j < codigosIncluidos.length; j++){
					if(p.getCodigo().equals(codigosIncluidos[j])){
						listar = false;
						
						break;	
					}
				}
			}
			
			if(listar){
			
				if(p instanceof ecar.popup.PopUpUsuarioEGrupo) {
					String classRadio = "texto";
					if(p.getCodigo().startsWith("G")) {
						classRadio = "texto_negrito";
					}
					out.println("<tr><td><input type=\"checkbox\" class=\"form_check_radio\" name=\"hidOpcoes\" value=\"" + 
					p.getCodigo() + "\"></td><td class=\"" + classRadio + "\">" +  p.getDescricao());
				}
				else {
					out.println("<tr><td><input type=\"checkbox\" class=\"form_check_radio\" name=\"hidOpcoes\" value=\"" + 
					p.getCodigo() + "\" " + _checked +"></td><td class=\"texto\">" +  p.getDescricao());
				}
	
				out.println("<input type=\"hidden\" name=\"hidAux" + p.getCodigo() + "\" value=\"" + p.getDescricao() + "\"></td></tr>");
			}
		}
		if (limite > 0) { 
%>
			</table>
			<center>
			<input type="button" name="btnAdicionar" class="botao" value="Adicionar" onclick="aoClicarAdicionar(document.form, '<%=hidFuncao%>');">
			<input type="button" name="btnCancelar" class="botao" value="Cancelar" onclick="aoClicarCancelar(document.form);">
			</center>
<%
		}else{
			if("pesquisar".equalsIgnoreCase(hidAcao)){
%>
			<table>
				<tr><td class="texto">
					<b>Nenhum registro foi encontrado para os critérios de pesquisa especificados</b>
				</td></tr>
			</table>
<%
			}
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
