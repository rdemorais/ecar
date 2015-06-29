<%@ taglib uri="/WEB-INF/tlds/c.tld" prefix="c"%> 


<%@ include file="/login/validaAcesso.jsp"%>
<%@ include file="/frm_global.jsp"%>

<%@ taglib uri="/WEB-INF/ecar-util.tld" prefix="util" %>
<%@ taglib uri="/WEB-INF/ecar-combo.tld" prefix="combo" %>
<%@ taglib uri="/WEB-INF/ecar-input.tld" prefix="ecar" %>
<%@ taglib uri="http://celepar.pr.gov.br/taglibs/informacao.tld" prefix="info" %>

<%@ page language="java" %>
<%@ page import="ecar.pojo.UsuarioUsu" %>
<%@ page import="ecar.pojo.ConfiguracaoCfg" %>
<%@ page import="ecar.pojo.SisAtributoSatb" %>
<%@ page import="ecar.pojo.SisGrupoAtributoSga" %>
<%@ page import="ecar.dao.UsuarioDao" %>
<%@ page import="ecar.dao.ConfiguracaoDao" %>
<%@ page import="ecar.dao.SisGrupoAtributoDao" %>

<%@ page import="java.util.Iterator" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.Set" %>
<%@ page import="java.util.ArrayList" %> 
<%@ page import="java.util.HashSet" %><%@ page import="ecar.pojo.OrgaoOrg" %><%@ page import="ecar.pojo.SelecaoItemEstruturaTela" %> 

<html lang="pt-br">
<head>
<%@ include file="../../include/meta.jsp"%>
<%@ include file="/titulo.jsp"%>
<link rel="stylesheet" href="<%=_pathEcar%>/css/style_<%=configuracaoCfg.getEstilo().getNome()%>.css" type="text/css">
<script language="javascript" src="../../js/menu_retratil.js"></script>
<script language="javascript" src="../../js/focoInicial.js"></script>
<script language="javascript" src="../../js/validacoes.js"></script>

<script language="javascript" src="../../js/datas.js"></script>
<script language="javascript" src="../../js/cnpj.js"></script>
<script language="javascript" src="../../js/cpf.js"></script>
<script language="javascript" src="../../js/cep.js"></script>
<script language="javascript" src="../../js/email.js"></script>
<script language="javascript" src="../../js/numero.js"></script>
<script language="javascript" src="<%=_pathEcar%>/js/limpezaCamposMultivalorados.js"></script>

<script language="javascript">
<%@ include file="validacao.jsp"%>

function aoClicarBtn1(form){
	if(validar(form)){
		form.action = "ctrl.jsp";
		form.submit();
	}
}
function aoClicarVoltar(form){
	form.reset();
}
function abrirPopUpUpload(nome, labelCampo, excluir){
	abreJanela("../../usuarios/usuarios/popUpUpload.jsp?nomeCampo="+nome+"&labelCampo="+labelCampo+"&excluir="+excluir,
				500, 100, nome);
}
</script>

<%@ include file="/include/meta.jsp"%>

</head>

<!-- sempre colocar o foco inicial no primeiro campo -->
<body onload="javascript:onLoad(document.form);">

<%@ include file="../../cabecalho.jsp" %>
<%@ include file="../../divmenu.jsp"%>

<%
try{
%>
<div id="conteudo">

<form name="form" method="post">
<%
UsuarioDao usuarioDao = new UsuarioDao(request);
Long codUsuario = ((ecar.login.SegurancaECAR)session.getAttribute("seguranca")).getCodUsu();
UsuarioUsu usuario = (UsuarioUsu) usuarioDao.buscar(UsuarioUsu.class, codUsuario);

%>
<input type="hidden" name="codUsu" value="<%=usuario.getCodUsu()%>">

<!-- TITULO -->
	
	<table>
		<tr>
			<td width="12"></td>
			<td><%@ include file="/titulo_tela.jsp"%></td>			
		</tr>
	</table>
	
	
<util:barrabotoes btn1="Gravar" voltar="Cancelar"/>

<table cellspacing="0" class="form">
	<util:msgObrigatorio obrigatorio="<%=_obrigatorio%>" />
	<tr> 
		
		<td class="label"><%=_obrigatorio%> Nome</td>
		<td>
			<%=Pagina.trocaNull(usuario.getNomeUsuSent())%>
		<!-- <input type="text" name="nomeUsu" size="55" maxlength="50" value="<%=Pagina.trocaNull(usuario.getNomeUsuSent())%>" disabled> -->
		</td>
	</tr>
	<tr>	
		<td class="label"><%=_obrigatorio%> &Oacute;rg&atilde;os</td>
		<td>
		<%
		if((usuario.getOrgaoOrgs()!=null) && (usuario.getOrgaoOrgs().size() > 0)){
			Iterator itOrgao = usuario.getOrgaoOrgs().iterator();
			OrgaoOrg selectedOrgao;
			while(itOrgao.hasNext())
			{
				selectedOrgao = (OrgaoOrg)itOrgao.next();
		%>
		<%=selectedOrgao.getDescricaoOrg()%>
		
		</td>
	</tr>
	<tr>
		<td>&nbsp;</td>
		<td>
			
	<%		}
	}%>
		</td>
	</tr>
	
	<tr>
		<td class="label">Data de Nascimento</td>
			<td>
				<input type="text" name="dataNascimentoUsu" size="15" maxlength="10" value="<%=Pagina.trocaNullData(usuario.getDataNascimentoUsu())%>" <%=_disabled%> onkeyup="mascaraData(event, this);">
				<c:if test="<%=_disabled.equals("")%>">
					<img class="posicao" title="Selecione a data" src="../../images/icone_calendar.gif" onclick="open_calendar('data', document.forms[0].dataNascimentoUsu, '')">
				</c:if>
			</td>
	</tr>	
	<tr>
		<td class="label">CPF/CNPJ</td>
		<td>
			<%=Pagina.trocaNull(usuario.getCnpjCpfUsu())%>
		<!-- <input type="text" name="cnpjCpfUsu" size"20" maxlength="14" value="<%=Pagina.trocaNull(usuario.getCnpjCpfUsu())%>" disabled> -->
		</td>
	</tr>	
	<tr> 
		<td class="label" colspan="2">&nbsp;</td>		
	</tr> 
	<tr> 
		<td class="label"><span class="fontLabelEntidade">Endere&ccedil;o Residencial</span></td>
		<td>&nbsp;</td>		
	</tr> 	
	<tr>
		<td class="label">Endere&ccedil;o</td>
		<td><input type="text" name="residEnderecoUsu" size="55" maxlength="50" value="<%=Pagina.trocaNull(usuario.getResidEnderecoUsu())%>" <%=_disabled%>></td>
	</tr>
	<tr>
		<td class="label">Complemento</td>
		<td><input type="text" name="residComplementoUsu" size="45" maxlength="40" value="<%=Pagina.trocaNull(usuario.getResidComplementoUsu())%>" <%=_disabled%>></td>
	</tr>
	<tr>
		<td class="label">Bairro</td>
		<td><input type="text" name="residBairroUsu" size="45" maxlength="40" value="<%=Pagina.trocaNull(usuario.getResidBairroUsu())%>" <%=_disabled%>></td>
	</tr>
	<tr>
		<td class="label">Cidade</td>
		<td><input type="text" name="residCidadeUsu" size="45" maxlength="40" value="<%=Pagina.trocaNull(usuario.getResidCidadeUsu())%>" <%=_disabled%>></td>
	</tr>
	<tr>
		<td class="label">CEP</td>
		<td><input type="text" name="residCepUsu" size="10" maxlength="8" value="<%=Pagina.trocaNull(usuario.getResidCepUsu())%>" <%=_disabled%>></td>
	</tr>
	<tr>
		<td class="label">UF</td>
		<td>
		<%
		String selectedUf = "";
	    if(usuario.getUfByResidUfUsu()!=null){
			selectedUf = usuario.getUfByResidUfUsu().getCodUf().toString();
		}
		%>
			<combo:ComboTag 
							nome="residUfUsu"
							objeto="ecar.pojo.Uf" 
							label="descricaoUf" 
							value="codUf" 
							order="descricaoUf"
							scripts="<%=_disabled%>" 		
							selected="<%=selectedUf%>"
		/>
		</td>
	</tr>
	<tr>
		<td class="label">Telefone</td>
		<td>
		<input type="text" name="residDddUsu" size="4" maxlength="3" value="<%=Pagina.trocaNull(usuario.getResidDddUsu())%>" <%=_disabled%>>
		- <input type="text" name="residTelefoneUsu" size="13" maxlength="10" value="<%=Pagina.trocaNull(usuario.getResidTelefoneUsu())%>" <%=_disabled%>>
		</td>
	</tr>
	<tr>
		<td class="label">Ramal</td>
		<td><input type="text" name="residRamalUsu" size="10" maxlength="6" value="<%=Pagina.trocaNull(usuario.getResidRamalUsu())%>" <%=_disabled%>></td>
	</tr>
	
	<tr> 
			<td class="label" colspan="2">&nbsp;</td>		
	</tr> 
	<tr> 
			<td class="label"><span class="fontLabelEntidade">Endere&ccedil;o Comercial</span></td>
			<td>&nbsp;</td>		
	</tr> 
	<tr>
		<td class="label">Endere&ccedil;o</td>
		<td><input type="text" name="comercEnderecoUsu" size="55" maxlength="50" value="<%=Pagina.trocaNull(usuario.getComercEnderecoUsu())%>" <%=_disabled%>></td>
	</tr>
	<tr>
		<td class="label">Complemento</td>
		<td><input type="text" name="comercComplementoUsu" size="45" maxlength="40" value="<%=Pagina.trocaNull(usuario.getComercComplementoUsu())%>" <%=_disabled%>></td>
	</tr>
	<tr>
		<td class="label">Bairro</td>
		<td><input type="text" name="comercBairroUsu" size="45" maxlength="40" value="<%=Pagina.trocaNull(usuario.getComercBairroUsu())%>" <%=_disabled%>></td>
	</tr>
	<tr>
		<td class="label">Cidade</td>
		<td><input type="text" name="comercCidadeUsu" size="45" maxlength="40" value="<%=Pagina.trocaNull(usuario.getComercCidadeUsu())%>" <%=_disabled%>></td>
	</tr>
	<tr>
		<td class="label">CEP</td>
		<td><input type="text" name="comercCepUsu" size="10" maxlength="8" value="<%=Pagina.trocaNull(usuario.getComercCepUsu())%>" <%=_disabled%>></td>
	</tr>
	<tr>
		<td class="label">UF</td>
		<td>
		<%
		selectedUf = "";
		if(usuario.getUfByComercUfUsu()!=null){
			selectedUf = usuario.getUfByComercUfUsu().getCodUf().toString();
		}
		%>
			<combo:ComboTag 
							nome="comercUfUsu"
							objeto="ecar.pojo.Uf" 
							label="descricaoUf" 
							value="codUf" 
							order="descricaoUf"
							scripts="<%=_disabled%>" 		
							selected="<%=selectedUf%>"
		/>
		</td>
	</tr>
	<tr>
		<td class="label">Telefone</td>
		<td>
		<input type="text" name="comercDddUsu" size="4" maxlength="3" value="<%=Pagina.trocaNull(usuario.getComercDddUsu())%>" <%=_disabled%>>
		- <input type="text" name="comercTelefoneUsu" size="13" maxlength="10" value="<%=Pagina.trocaNull(usuario.getComercTelefoneUsu())%>" <%=_disabled%>>
		</td>
	</tr>
	<tr>
		<td class="label">Ramal</td>
		<td><input type="text" name="comercRamalUsu" size="10" maxlength="6" value="<%=Pagina.trocaNull(usuario.getComercRamalUsu())%>" <%=_disabled%>></td>
	</tr>
	<tr>
		<td class="label">Fax</td>
		<td>
		<input type="text" name="dddFaxUsu" size="4" maxlength="3" value="<%=Pagina.trocaNull(usuario.getDddFaxUsu())%>" <%=_disabled%>>
		- <input type="text" name="faxUsu" size="13" maxlength="10" value="<%=Pagina.trocaNull(usuario.getFaxUsu())%>" <%=_disabled%>>
		</td>
	</tr>
	
	<tr> 
			<td class="label" colspan="2">&nbsp;</td>		
	</tr> 
	
	<tr>
		<td class="label">Endere&ccedil;o para Correspond&ecirc;ncia</td>
		<td>
		<%
		String selectedTpec = "";
		if(usuario.getTipoEnderecoCorrTpec()!=null){
			selectedTpec = usuario.getTipoEnderecoCorrTpec().getCodTpec().toString();
		}
		%>
			<combo:ComboTag 
				nome="tipoEnderecoCorrTpec"
				objeto="ecar.pojo.TipoEnderecoCorrTpec" 
				label="descricaoTpec" 
				value="codTpec" 
				order="descricaoTpec"
				scripts="<%=_disabled%>" 		
				selected="<%=selectedTpec%>"
			/>
		</td>
	</tr>
	
	<tr>
		<td class="label"><%=_obrigatorio%> E Mail</td>
		<td>
			<%=Pagina.trocaNull(usuario.getEmail1UsuSent())%>
		<!-- <input type="text" name="email1Usu" size="55" maxlength="50" value="<%=Pagina.trocaNull(usuario.getEmail1UsuSent())%>" disabled> -->
		</td>
	</tr>			
	<tr>
		<td class="label">E Mail 2</td>
		<td><input type="text" name="email2Usu" size="55" maxlength="50" value="<%=Pagina.trocaNull(usuario.getEmail2Usu())%>" <%=_disabled%>></td>
	</tr>
	<tr>
		<td class="label"><%=_obrigatorio%> Login</td>		
		<td>
		<%=Pagina.trocaNull(usuario.getIdUsuarioUsu())%>
		<!--  <input type="text" name="idUsuarioUsu" size="22" maxlength="20" value="<%=Pagina.trocaNull(usuario.getIdUsuarioUsu())%>" disabled> -->
		</td>
	</tr>
	<%
	SisGrupoAtributoDao sisGrupoAtributoDao = new SisGrupoAtributoDao(request);
	List grupoAtributoObrigatorios = new ArrayList();
	List atributosCadastro = new SisGrupoAtributoDao(request).getGruposAtributosCadastroUsuarioSite();
	List atributosUsuario = usuarioDao.getAtributosUsuario( usuario );

	if( atributosCadastro != null ){
			Iterator itA = atributosCadastro.iterator();
			while( itA.hasNext() ) {
				SisGrupoAtributoSga grupoAtributo = (SisGrupoAtributoSga) itA.next();
				if (grupoAtributo.getSisAtributoSatbs().size() > 0)
				{				
					List selecionados = new ArrayList();	
					selecionados.addAll(atributosUsuario);					
					SisAtributoSatb sisAtributo = (SisAtributoSatb) grupoAtributo.getSisAtributoSatbs().iterator().next();					

					if("S".equals(grupoAtributo.getIndObrigatorioSga()))
						grupoAtributoObrigatorios.add(grupoAtributo);
					String nomeCampo = "a" + grupoAtributo.getCodSga().toString();
					String pathRaiz = request.getContextPath();
					
					%>
					<ecar:Input
						label="<%=grupoAtributo.getDescricaoSga()%>"
						tipo="<%=Integer.valueOf(grupoAtributo.getSisTipoExibicGrupoCadUsuSteg().getCodSteg().toString()).intValue()%>"
						obrigatorio="<%=grupoAtributo.getIndObrigatorioSga()%>"
						labelObrigatorio="<%=_obrigatorio%>"
						nome="<%=nomeCampo%>"
						classLabel="label"
						selecionados="<%=selecionados%>"
						sisAtributo="<%=sisAtributo%>"
						pathRaiz="<%=pathRaiz%>"
						size="20"
						sisGrupoAtributoSga="<%=grupoAtributo%>"
						nivelPlanejamentoCheckBox="<%=new Boolean(true)%>"
					> 
					<%
					List opcoes = new ArrayList();
					if(grupoAtributo.getSisTipoOrdenacaoSto() != null )
						opcoes = sisGrupoAtributoDao.getAtributosOrdenados(grupoAtributo);
					%>
						<ecar:Options
							options="<%=opcoes%>" 
							valor="codSatb"
							label="descricaoSatb"
							nivelPlanejamentoCheckBox="<%=new Boolean(true)%>"		
							imagem="../../images/relAcomp/"
							nome="<%=nomeCampo%>"			
						/>					
				</ecar:Input>
					<%
			}
		}	
	}
	%>	
	
	<script language="Javascript">
	function validaCamposVariaveis(form){
		<%= new ecar.dao.SisGrupoAtributoDao(request).getValidacoesAtributos(grupoAtributoObrigatorios, true)%>
		return true;
	}
	
	function desabilitarSenha(form, desabilita) {
		document.form.senhaUsu.disabled=desabilita;
		document.form.confSenhaUsu.disabled=desabilita;
	}
	</script>
		
	
	<%
	}catch(ECARException e){
		org.apache.log4j.Logger.getLogger(this.getClass()).error(e);
	} catch (Exception e){
%>
	<%@ include file="/excecao.jsp"%>
<%
}
%>	
		
</table> 

<util:barrabotoes btn1="Gravar" voltar="Cancelar"/>
</form>
 <!-- </div> -->
</body>
<%@ include file="../../include/estadoMenu.jsp"%>
<%@ include file="../../include/mensagemAlert.jsp"%>

</html>