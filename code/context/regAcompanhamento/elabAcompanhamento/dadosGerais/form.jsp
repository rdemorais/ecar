<table name="form" class="form" width="100%">
<%@ page import="java.util.List" %>
<%@ page import="ecar.dao.SisGrupoAtributoDao" %>

<script language="javascript" src="../../../js/limpezaCamposMultivalorados.js"></script>

<%
if(configuracaoDao.getConfiguracao() != null) {

%>

<%if(itemEstrutura.getCodIett() != null){%>
	<input type="hidden" name="codIett" value="<%=itemEstrutura.getCodIett()%>">
<%} else {%>	
	<input type="hidden" name="codIett" value="">
<%}%>

<%if(codIettPrincipal > 0){%>
	<input type="hidden" name="codIettPai" value="<%=codIettPrincipal%>">
<%} else {%>	
	<input type="hidden" name="codIettPai" value="">
<%}%>

	<input type="hidden" name="codAri"        value="<%=Pagina.getParamStr(request, "codAri")%>">
	<input type="hidden" name="primeiroAcomp" value="<%=Pagina.getParamStr(request, "primeiroAcomp")%>">
	<input type="hidden" name="codAcomp"      value="<%=Pagina.getParamStr(request, "codAcomp")%>">
	<input type="hidden" name="codAba"  value=<%=Pagina.getParamStr(request, "codAba")%>>
	
	<input type="hidden" name="codEtt"  value="<%=estrutura.getCodEtt()%>">
	<input type="hidden" name="hidAcao" value="">
	<input type="hidden" name="alterou" value="">

	<input type="hidden" name="configMailCfgmAtivar" value="<%=configMailCfgmAtivar.getAtivoCfgm()%>">
	<input type="hidden" name="configMailCfgmRetirar" value="<%=configMailCfgmRetirar.getAtivoCfgm()%>">
	<input type="hidden" name="configMailCfgmBloquear" value="<%=configMailCfgmBloquear.getAtivoCfgm()%>">
	<input type="hidden" name="configMailCfgmDesbloquear" value="<%=configMailCfgmDesbloquear.getAtivoCfgm()%>">
	<input type="hidden" name="configMailCfgmAlterarFuncAcomp" value="<%=configMailCfgmAlterarFuncAcomp.getAtivoCfgm()%>">
	
	<input type="hidden" name="configMailCfgmAtivarObrigatorio" value="<%=configMailCfgmAtivar.getIndEnvioObrigatorio()%>">
	<input type="hidden" name="configMailCfgmRetirarObrigatorio" value="<%=configMailCfgmRetirar.getIndEnvioObrigatorio()%>">
	<input type="hidden" name="configMailCfgmBloquearObrigatorio" value="<%=configMailCfgmBloquear.getIndEnvioObrigatorio()%>">
	<input type="hidden" name="configMailCfgmDesbloquearObrigatorio" value="<%=configMailCfgmDesbloquear.getIndEnvioObrigatorio()%>">
	<input type="hidden" name="configMailCfgmAlterarFuncAcompObrigatorio" value="<%=configMailCfgmAlterarFuncAcomp.getIndEnvioObrigatorio()%>">	
	
	<input type="hidden" name="msgAutorizaAtivar" value='<%=_msg.getMensagem("acompanhamento.cadastroItens.dadosGerais.ativar.autorizaEnviaEmail")%>'>
	<input type="hidden" name="msgAutorizaRetirar" value='<%=_msg.getMensagem("acompanhamento.cadastroItens.dadosGerais.retirar.autorizaEnviaEmail")%>'>
	<input type="hidden" name="msgAutorizaBloquear" value='<%=_msg.getMensagem("acompanhamento.cadastroItens.dadosGerais.bloquear.autorizaEnviaEmail")%>'>
	<input type="hidden" name="msgAutorizaDesbloquear" value='<%=_msg.getMensagem("acompanhamento.cadastroItens.dadosGerais.desbloquear.autorizaEnviaEmail")%>'>
	<input type="hidden" name="msgAtivarItensFilho" value='<%=_msg.getMensagem("acompanhamento.cadastroItens.dadosGerais.ativar.todosItensFilhos")%>'>
	<input type="hidden" name="msgRetirarItensFilho" value='<%=_msg.getMensagem("acompanhamento.cadastroItens.dadosGerais.retirar.todosItensFilhos")%>'>
	<input type="hidden" name="autorizarMail" value="N">
	<input type="hidden" name="ativarRetirarMonitoramentoItensFilho" value="N">
<%
if(atributos != null){
	Iterator it = atributos.iterator();
	while(it.hasNext()){
		ObjetoEstrutura atributo = (ObjetoEstrutura) it.next();
		%>
			<util:formItemEstrutura atributo="<%=atributo%>" itemEstruturaIett="<%=itemEstrutura%>" desabilitar="<%=desabilitar%>" seguranca="<%=seguranca%>" contextPath="<%=request.getContextPath()%>" request="<%=request %>"/>	
		<%
	} 
%>
<script language="javascript">
	atualizaUnidadeOrc(document.form.orgaoOrgByCodOrgaoResponsavel1Iett, '<%=_disabled%>');
	
	function abrirPopUpUpload(nome, labelCampo){
		abreJanela("../../usuarios/usuarios/popUpUpload.jsp?nomeCampo="+nome+"&labelCampo="+labelCampo+"&excluir=",
				500, 100, nome);
	}
	function abrirPopUpUpload(nome, labelCampo, excluir){
		abreJanela("../../usuarios/usuarios/popUpUpload.jsp?nomeCampo="+nome+"&labelCampo="+labelCampo+"&excluir="+excluir,
					500, 100, nome);
	}	
	
</script>
<%
}
%>
<%} else {
	out.println("<br> Efetue a Configura&ccedil;&atilde;o Geral do Sistema antes de realizar o Cadastro de Itens <br><br> ");
}%>

<% /* Controla o estado do Menu (aberto ou fechado) */%>
<%@ include file="/include/estadoMenu.jsp"%>
</table>