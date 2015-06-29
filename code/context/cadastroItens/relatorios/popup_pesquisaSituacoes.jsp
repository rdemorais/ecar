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
	var codigosSelecionados = "";

    if (typeof funcaoGetDadosPesquisa == "undefined") funcaoGetDadosPesquisa = "getDadosPesquisaSituacoes";

	for (i = 0; i < form.hidOpcoes.length; i++) {
		if (form.hidOpcoes[i].checked == true) {
			codigosSelecionados += form.hidOpcoes[i].value + "|";
		}
	}

	//chama a funcao definida pelo programador para retornar os dados da pesquisa
	eval('window.opener.' + funcaoGetDadosPesquisa)(codigosSelecionados, "Teste");
	window.close()
}

function aoClicarCancelar(form){
	window.close();
}
</SCRIPT>

</head>

<!-- sempre colocar o foco inicial no primeiro campo -->
<body onload="onLoad(document.form);" class="corpo_popup">
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
		
		/* lista que cont�m o resultado da pesquisa */
		List lista;

		/* instancia o pojo que vai ser utilizado na pesquisa */
		Class c = Class.forName(Pagina.getParamStr(request, "hidPojo"));
		PopUpPesquisa p = (PopUpPesquisa) c.newInstance();

		/* controles de p�gina */
		int hidNumPagina = Pagina.getParamInt(request, "hidNumPagina");
		int hidTotPagina = Pagina.getParamInt(request, "hidTotPagina");

		/*
		 * Acao = nada - entrou na pagina; "pesquisar" - deve pesquisar; "navegar" - deve navegar
		 */
		String hidAcao = Pagina.getParamStr(request, "hidAcao");
		
		/* persiste o pojo que deve ser pesquisado */
		String hidPojo = Pagina.getParamStr(request, "hidPojo");

		/* pega os c�digos das situa��es que j� est�o cadastrados para que n�o apare�am na pesquisa */
		String jaIncluidos = Pagina.getParamStr(request, "jaIncluidos");
		
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
		
		<h1>Sele��o de <%=p.getTitulo()%></h1>
		<%

		int limite = lista.size();
		if (limite > 0) { 
%>
			<table cellpadding="3">
<!--			<tr>-->
<!--				<td class="texto">Com</td>-->
<!--				<td class="texto">Sem</td>-->
<!--				<td>&nbsp;</td>-->
<!--			</tr>-->
				
<%
			for (int i = 0; i < lista.size(); i++) {
				p.setPojo(lista.get(i));
				
				/*
				 * Este bloco abaixo verifica se o item da vez j� est� incluso no array de c�digos
				 * que foi passado em jaIncluidos
				 */
				
				String _checked = "";
				
				if(jaIncluidos.length() > 0){
					String[] codigosIncluidos = jaIncluidos.split("\\|");
					for(int j = 0; j < codigosIncluidos.length; j++){
						if(p.getCodigo().equals(codigosIncluidos[j])){
							_checked = "checked";
							break;	
						}
					}
				}
				
				out.println(
				"<tr>" +
				"<td>" + 
					"<input type=\"checkbox\" class=\"form_check_radio\" name=\"hidOpcoes\" value=\"" +	p.getCodigo() + "\" " + _checked + " >" +
				"</td>"+
				"<td class=\"texto\">" + p.getDescricao());
	
				out.println("<input type=\"hidden\" name=\"hidAux" + p.getCodigo() + "\" value=\"" + p.getDescricao() + "\"></td></tr>");
			}
%>
			</table>
			<center>
			<input type="button" name="btnAdicionar" class="botao" value="Filtrar" onclick="aoClicarAdicionar(document.form, '<%=hidFuncao%>');">
			<input type="button" name="btnCancelar" class="botao" value="Cancelar" onclick="aoClicarCancelar(document.form);">
			</center>
<%
		}
		else{
%>
			<table>
				<tr><td class="texto">
					<b>Nenhum registro foi encontrado para os crit�rios de pesquisa especificados</b>
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
