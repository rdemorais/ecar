<%@ taglib uri="/WEB-INF/ecar-util.tld" prefix="util" %>
<%@ taglib uri="/WEB-INF/ecar-combo.tld" prefix="combo" %>
<%@ include file="/frm_global.jsp"%>
<%@ include file="/login/validaAcesso.jsp"%>
<%@ page import="ecar.popup.PopUpPesquisa" %>
<%@ page import="java.util.Enumeration" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.lang.Math" %>
<%@ page import="java.io.PrintStream" %>

<html lang="pt-br">
<head>
<%@ include file="../include/meta.jsp"%>
<%@ include file="/titulo.jsp"%>
<link rel="stylesheet" href="<%=_pathEcar%>/css/style_<%=configuracaoCfg.getEstilo().getNome()%>.css" type="text/css">
<script language="javascript" src="../js/focoInicial.js"></script>
<script language="javascript" src="../js/frmPesquisa.js"></script>
<script language="javascript" src="../js/validacoes.js"></script>
<script>
function adicionar(form, codLit, identificacaoLit){
	var selecionado = false;
	var inserido = false;
	if (verificaRadios(form, "hidOpcoes") > 1) {
		for (i = 0; i < form.hidOpcoes.length; i++) {
			if (form.hidOpcoes[i].checked == true) {
				dados = form.hidOpcoes[i].value.split(":");
				escolhido = form.hidOpcoes[i];
			if (window.opener.adicionaPai(dados[0], dados[1]))
				inserido = true;
			selecionado = true;
			break;
			}
		}
	} else {
		if (form.hidOpcoes.checked == true) {
			dados = form.hidOpcoes.value.split(":");
			escolhido = form.hidOpcoes;			
		if (window.opener.adicionaPai(dados[0], dados[1]))
			inserido = true;		
		selecionado = true;
		}
	}
	if (selecionado) {
		if (inserido) {
			alert ('Entidade adicionada.');
		}
		escolhido.checked = false;
	}
	else {
		alert('Selecione uma opção!');
	}
}

function aoClicarPesquisarPai(form){
	document.form.action="popup_pesquisa_pai.jsp?hidAcao=pesquisar";
	document.form.submit();
}

</script>
</head>

<!-- sempre colocar o foco inicial no primeiro campo -->
<body onload="onLoad(document.form);" class="corpo_popup">

<form name="form" method="post" action="popup_pesquisa_pai.jsp?hidAcao=navegar" onsubmit="aoClicarPesquisarPai(document.form);">
	
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
		
		/* funcao que vai receber os retorno da pesquisa ao clicar adicionar */
		String hidFuncao = Pagina.getParamStr(request, "hidFuncao");
		//String entidade = Pagina.getParamStr(request, "Entidade");
		
		boolean buscaPadrao = false;
		
		if("".equals(hidAcao.trim()) && !"ecar.popup.PopUpUsuario".equalsIgnoreCase(hidPojo) && !"ecar.popup.PopUpRegDemanda".equalsIgnoreCase(hidPojo) && !"ecar.popup.PopUpUsuarioEGrupo".equalsIgnoreCase(hidPojo)){
			buscaPadrao = true;
		}
		
		if ("pesquisar".equalsIgnoreCase(hidAcao) || buscaPadrao) {
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
					<input type="button" name="btnOk" class="botao" value="Ok" onclick="aoClicarPesquisarPai(document.form);">
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
		int tamLista = lista.size();
		int limite = Math.min(tamLista, hidNumPagina * ITENS_PAGINA + ITENS_PAGINA);
		hidTotPagina = tamLista / ITENS_PAGINA + (tamLista % ITENS_PAGINA == 0 ? 0 : 1);
		if (limite > 0) { 
%>
			<util:barrapesquisa atual="<%=hidNumPagina+1%>" total="<%=hidTotPagina%>"/>
			<input type="hidden" name="hidNumPagina" value="<%=hidNumPagina%>">	
			<input type="hidden" name="hidTotPagina" value="<%=hidTotPagina%>">	
			<table cellpadding="3">
<%
		}
		String cor = new String();
		for (int i = hidNumPagina*ITENS_PAGINA; i < limite; i++) {
			if (i % 2 == 0){
				cor = "cor_nao";
			} else {
				cor = "cor_sim"; 
			}
			p.setPojo(lista.get(i));
			String valorPai = p.getCodigo() + ":" + p.getDescricao();
			if(p instanceof ecar.popup.PopUpUsuarioEGrupo) {
				String classRadio = "texto";
				if(p.getCodigo().startsWith("G")) {
					classRadio = "texto_negrito";
				}
				out.println("<tr><td><input type=\"radio\" class=\"form_check_radio\" name=\"hidOpcoes\" value=\"" + 
				valorPai + "\"></td><td class=\"" + classRadio + "\">" +  p.getDescricao());
			} else if(p instanceof ecar.popup.PopUpUsuario){
				String classRadio = "texto";
				String imagem="<img src=\"" +_pathEcar + "/images/icon_usuario.png\" title=\"Usuário\">";
				if(p.getCodigo().startsWith("G")) {
					classRadio = "texto_negrito";
					imagem="<img src=\""+_pathEcar+"/images/icon_grupo.png\" title=\"Grupo\" alt=\"\">";
				}
				out.println("<tr class=\"" + cor + "\" onmouseover=\"javascript: destacaLinha(this,'over','')\" onmouseout=\"javascript: destacaLinha(this,'out','" + cor +"')\" onClick=\"javascript: destacaLinha(this,'click','"+ cor +"')\" ><td width=\"5px\"><input type=\"radio\" class=\"form_check_radio\" name=\"hidOpcoes\" value=\"" + 
				p.getCodigo() + "\"></td><td class=\"" + classRadio + "\">"  
				+ imagem + p.getDescricao());							
			} else {
				out.println("<tr><td><input type=\"radio\" class=\"form_check_radio\" name=\"hidOpcoes\" value=\"" + 
				valorPai + "\"></td><td class=\"texto\">" +  p.getDescricao());
			}

			out.println("<input type=\"hidden\" name=\"hidAux" + p.getCodigo() + "\" value=\"" + p.getDescricao() + "\"></td></tr>");
		}
		if (limite > 0) { 
%>
			</table>
			<util:barrapesquisa atual="<%=hidNumPagina+1%>" total="<%=hidTotPagina%>"/>
			<center>
			<!--  
			<input type="button" name="btnAdicionar" class="botao" value="Adicionar" onclick="aoClicarAdicionar(document.form, '<%=hidFuncao%>');">
			-->
			 
			<input type="button" name="btnAdicionar" class="botao" value="Adicionar" onclick="adicionar(document.form);">
			
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
	} catch (Exception e){
	%>
		<%@ include file="/excecao.jsp"%>
	<%
	}
%>	
	
</form>
</body>
</html>
