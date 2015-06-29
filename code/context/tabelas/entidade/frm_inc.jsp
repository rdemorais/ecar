<%@ taglib uri="/WEB-INF/ecar-util.tld" prefix="util"%>
<%@ taglib uri="/WEB-INF/ecar-combo.tld" prefix="combo" %>
<%@ taglib uri="/WEB-INF/ecar-input.tld" prefix="ecar" %>
<%@ page import="ecar.pojo.EntidadeEnt"%>
<%@ page import="ecar.dao.EntidadeDao"%>
<%@ page import="ecar.pojo.SisGrupoAtributoSga" %>
<%@ page import="ecar.pojo.SisAtributoSatb" %>
<%@ page import="ecar.pojo.EnderecoEnd"%>
<%@ page import="ecar.pojo.LocalItemLit"%>
<%@ page import="ecar.dao.TipoEnderecoDao"%>
<%@ page import="ecar.dao.SisGrupoAtributoDao" %>
<%@ page import="ecar.dao.UfDao"%>
<%@ page import="java.util.Set"%>
<%@ page import="java.util.HashSet"%>
<%@ page import="java.util.Iterator"%>
<%@ page import="ecar.pojo.TelefoneTel" %>
<%@ include file="../../login/validaAcesso.jsp"%>
<%@ include file="../../frm_global.jsp"%>
<%@ page  import="java.util.List"%>

<%
session.removeAttribute("objPesquisa");
%>

<html lang="pt-br">
	<head>
		<%@ include file="../../include/meta.jsp"%>
		<%@ include file="/titulo.jsp"%>
		<link rel="stylesheet" href="<%=_pathEcar%>/css/style_<%=configuracaoCfg.getEstilo().getNome()%>.css" type="text/css">
		<script language="javascript" src="../../js/menu_retratil.js"></script>
		<script language="javascript" src="../../js/focoInicial.js"></script>
		<script language="javascript" src="../../js/frmPadrao.js"></script>
		<script language="javascript" src="../../js/validacoes.js"></script>
		<script language="javascript" src="../../js/popUp.js"></script>	
		<script language="javascript" src="../../js/cnpj.js"></script>
		<script language="javascript" src="../../js/cpf.js"></script>
		<script language="JavaScript" src="../../js/email.js"></script>
		
		<script language="javascript" src="../../js/datas.js"></script>
		<script language="javascript" src="../../js/validacoesAtributoLivre.js"></script>
		
		<script language="javascript">

function validarGravar(form){ 
	if(!validaString(form.nomeEnt, "Nome", true)){
		return(false);
	}
	if(Trim(form.cpfCnpjEnt.value) != ""){
		var valor = Trim(form.cpfCnpjEnt.value);
		
		if(valor.length == 14){ //CNPJ
			if(!validaCNPJ(form.cpfCnpjEnt, false, true, true)){
				alert("<%=_msg.getMensagem("empresa.validacao.cnpjCpfEmp.invalido")%>");
				form.cpfCnpjEnt.focus();
				return(false);
			}
		}
		if(valor.length == 11){ //CPF
			if(!validaCPF(form.cpfCnpjEnt, false, true, true)){
				alert("<%=_msg.getMensagem("empresa.validacao.cnpjCpfEmp.invalido")%>");
				form.cpfCnpjEnt.focus();
				return(false);
			}
		}
		if(valor.length != 14 && valor.length != 11){
			alert("<%=_msg.getMensagem("empresa.validacao.cnpjCpfEmp.invalido")%>");
			form.cpfCnpjEnt.focus();
			return(false);
		}
	}
	if ((document.form.emailEnt.value != "")&&(!validaEmail(document.form.emailEnt.value))) {
		alert("E-mail inválido");
		document.form.emailEnt.focus();
		return false;
	}
	if (!validaCamposVariaveis(form) ) 
		return (false);	
	return(true);
}

function setValoresLocal(codLit, identificacaoLit){
	document.forms[0].nomeUsu.value = nomeUsu;
	document.forms[0].idUsuarioUsu.value = login;	
}

<%
	TipoEnderecoDao tpEndDao = new TipoEnderecoDao(request);
	out.println(tpEndDao.getTipoEnderecoJS());
	UfDao ufDao = new UfDao(request);
	out.println(ufDao.getUfJS());
%>

</script>
<%
	_disabled = "";
%>

<%@ include file="funcoes.jsp"%>

	</head>
	
	<%@ include file="../../cabecalho.jsp" %>
	<%@ include file="../../divmenu.jsp"%>

	<!-- sempre colocar o foco inicial no primeiro campo -->
	<body onload="javascript:onLoad(document.form);">

<%
try {

				%>
		<div id="conteudo">

			<form name="form" method="post" onLoad="carregaEndsTels()">
				<input type="hidden" name="hidAcao" value="">

				<!-- TITULO -->
				<%@ include file="/titulo_tela.jsp"%>

				<util:linkstop incluir="frm_inc.jsp" pesquisar="frm_pes.jsp" />
				<util:barrabotoes incluir="Gravar" cancelar="Cancelar" />

				<%
				EntidadeEnt entidade;
				EntidadeDao entidadeDao = new EntidadeDao(request);
				if (session.getAttribute("objEntidade") != null) {
					entidade = (EntidadeEnt) session
							.getAttribute("objEntidade");
					session.removeAttribute("objEntidade");
				} else {
					entidade = new EntidadeEnt();
				}
			    boolean pesquisa = false;		
				boolean navega = false;		
			%>
				<table class="form">
					<%@ include file="form.jsp"%>
				</table>

				<util:barrabotoes incluir="Gravar" cancelar="Cancelar" />

			</form>

		</div>
		<%
} catch (Exception e) {
%>
		<%@ include file="/excecao.jsp"%>
		<%
}
%>
	</body>
	<%@ include file="../../include/mensagemAlert.jsp"%>
</html>
