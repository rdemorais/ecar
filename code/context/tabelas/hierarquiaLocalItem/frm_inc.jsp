<%@ include file="../../login/validaAcesso.jsp"%>

<%@ taglib uri="/WEB-INF/ecar-util.tld" prefix="util" %>
<%@ taglib uri="/WEB-INF/ecar-combo.tld" prefix="combo" %>
<%@ page import="ecar.pojo.LocalItemLit" %>
<%@ page import="ecar.dao.LocalItemDao" %>
<%@ page import="ecar.pojo.LocalGrupoLgp" %>
<%@ page import="ecar.dao.LocalGrupoDao" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.Iterator" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.Collections" %>
<%@ page import="java.util.Comparator" %>

<%@ include file="../../frm_global.jsp"%>

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
<script language="javascript">
function validaCheck(campoInicial, form, codigo, codigoGrupo){
	if(codigoGrupo != null){
		verificaGrupoSelecionado(campoInicial, form, codigoGrupo);
	}
	
	marcaLocal(campoInicial, form, codigo);
}

function verificaGrupoSelecionado(campoInicial, form, codigoGrupo){
	var el = document.getElementsByTagName("INPUT");
	var i = 0;
	
	while (i < el.length)
	{
		if(el.item(i) != null){
			if (el.item(i).name == "grupo"){
				if (el.item(i).value == codigoGrupo){
					if(el.item(i).checked){
						campoInicial.checked = true;
					}
				}
			}	
		}
		i++;
	}
	
	return;
}

function marcaLocal(campoInicial, form, codigo){ 
	//esta variavel pega o campo no nivel acima(municipio, estado....)
	var estado = campoInicial.checked;
	
	var campo = eval("document.form.grupo" + codigo);
	var numChecks = verificaChecksById(form, "grupo" + codigo)
	
	if(numChecks > 0){
		if(numChecks == 1){
			campo.checked = estado;		
		} else {
			for(var i = 0; i < numChecks; i++){
				campo[i].checked = estado;		
			}
		}			
	}
	
	return;
}

function selectAll(form, campoTodos){ 
	var estado = campoTodos.checked;
	
	//verifica a quantidade de checks de grupo
	var numChecksGrupo = verificaChecksById(form, campoTodos.name + campoTodos.value); 
	
	//marcar os checks dos grupos
	if(numChecksGrupo > 0){ 
		var campoGrupo = eval("document.form." + campoTodos.name + campoTodos.value);
		
		//para somente um grupo
		if(numChecksGrupo == 1){
			campoGrupo.checked = estado;
			
			//verifica a quantidade de checks de locais
			var numChecksLocal = verificaChecksById(form, campoGrupo.name + campoGrupo.value);
			
			//marcar os checks dos locais
			if(numChecksLocal > 0){ 
				var campoLocal = eval("document.form." + campoGrupo.name + campoGrupo.value);
				if(numChecksLocal == 1){
					campoLocal.checked = estado;
				} else {
					for(var i = 0; i < numChecksLocal; i++){
						campoLocal[i].checked = estado;		
					}
				}
			}
		} else {
			//mais de um grupo
			for(var i = 0; i < numChecksGrupo; i++){
				campoGrupo[i].checked = estado;	
				
				//verifica a quantidade de checks de locais
				var numChecksLocal = verificaChecksById(form, campoGrupo[i].name + campoGrupo[i].value);
				
				//marcar os checks dos locais
				if(numChecksLocal > 0){ 
					var campoLocal = eval("document.form." + campoGrupo[i].name + campoGrupo[i].value);
					if(numChecksLocal == 1){
						campoLocal.checked = estado;
					} else {
						for(var n = 0; n < numChecksLocal; n++){
							campoLocal[n].checked = estado;		
						}
					}
				}
			}
		}			
	}
	
	return;
}

function validarGravar(form){ 
	if(form.localItemLit.value == ""){
		alert("<%=_msg.getMensagem("hierarquiaLocalItem.validacao.localItemLit.obrigatorio")%>");
		form.localItemLit.focus();
		return(false);		
	}
	return(true);
}

function reloadGrupo(form){
	form.localItemLit.value = "";
	reloadLocal(form);
}

function reloadLocal(form){
	form.action = "frm_inc.jsp";
	form.submit();
}

</script>

</head>

<%@ include file="../../cabecalho.jsp" %>
<%@ include file="../../divmenu.jsp"%>

<!-- sempre colocar o foco inicial no primeiro campo -->
<body onload="javascript:onLoad(document.form);">

<div id="conteudo">
 
<form name="form" method="post">
	<input type="hidden" name="hidAcao" value="">
	
	<!-- TITULO -->
	<%@ include file="/titulo_tela.jsp"%>
 
	<util:barrabotoes incluir="Gravar" cancelar="Cancelar"/>

	<table class="form"> 
	<tr><td class="label">&nbsp;</td></tr>
	
<%
	LocalGrupoDao localGrupoDao = new LocalGrupoDao(request);
	LocalItemDao localItemDao = new LocalItemDao(request);
	
	LocalGrupoLgp localGrupo = new LocalGrupoLgp();
	LocalItemLit localItem = new LocalItemLit();
	
	/* contém a lista de itens selecionados */
	List locaisItem = new ArrayList();
	
	String selectedLgp = "";
	String selectedLit = "";
	
	try{
		if(!"".equals(Pagina.getParamStr(request, "localGrupoLgp"))){
			localGrupo = (LocalGrupoLgp) localGrupoDao.buscar(LocalGrupoLgp.class, Long.valueOf(Pagina.getParamStr(request, "localGrupoLgp")));
		   	selectedLgp = localGrupo.getCodLgp().toString();
			locaisItem = localItemDao.getLocalItemPorLocalGrupo(localGrupo);
		}
%>

	<tr>
		<td width="20%" class="label" valign="top">* Grupo de Local</td>
		<td>
			<combo:ComboTag 
					nome="localGrupoLgp"
					objeto="ecar.pojo.LocalGrupoLgp" 
					label="identificacaoLgp" 
					value="codLgp" 
					order="identificacaoLgp"
					selected="<%=selectedLgp%>"
					scripts="onchange=reloadGrupo(document.forms[0]);"
			/>
		</td>
	</tr>
	<tr>
		<td width="20%" class="label" valign="top">* Local</td>
		<td>
<%
		if(!"".equals(Pagina.getParamStr(request, "localGrupoLgp"))){
			if(!"".equals(Pagina.getParamStr(request, "localItemLit"))){
				localItem = (LocalItemLit) localItemDao.buscar(LocalItemLit.class, Long.valueOf(Pagina.getParamStr(request, "localItemLit")));
				selectedLit = Pagina.getParamStr(request, "localItemLit");
			}
%>
			<combo:ComboTag 
					nome="localItemLit"
					objeto="ecar.pojo.LocalItemLit" 
					label="identificacaoLit" 
					value="codLit" 
					order="identificacaoLit"
					colecao="<%=locaisItem%>"
					selected="<%=selectedLit%>"
					scripts="onchange=reloadLocal(document.forms[0]);"
			/>
<%
		} else {
%>
			<select name="localItemLit">
				<option value="">Selecione o Grupo de Local</option>
			</select>
<%
		}
%>
		</td>
	</tr>
	
	<tr><td class="label">&nbsp;</td></tr>
	
	<tr>
		<td width="20%" class="label" valign="top">Locais Pais</td>
		<td>
			<input type="checkbox" class="form_check_radio" name="todos" value="1" onclick="selectAll(document.forms[0], this)"> Todos <br><br>
		<%
			if(!"".equals(Pagina.getParamStr(request, "localItemLit"))){
				List gruposPais = localGrupoDao.getGruposPais(localGrupo);
				
				Iterator itGP = gruposPais.iterator();
				while (itGP.hasNext()) {
					LocalGrupoLgp grupoPai = (LocalGrupoLgp) itGP.next();
					
					/* cria uma tabela após o check do Grupo para que
						os locais fiquem identados ao utilizar taglib */
		%>			
					<input type="checkbox" class="form_check_radio" name="grupo" id="todos1" value="<%=grupoPai.getCodLgp()%>" onclick="validaCheck(this, form, this.value, null);"><%=grupoPai.getIdentificacaoLgp()%><br>
					<table>
						<tr>
						<td width="5%">&nbsp;&nbsp;&nbsp;</td>
						<td>
		<%
					List colecao = localItemDao.getLocalItemPorLocalGrupo(grupoPai);
					
					if (colecao != null && colecao.size() > 0) {
						List locaisPais = localItemDao.getLocaisPais(localItem);
						
			    		Iterator iCol = colecao.iterator();
			    		while (iCol.hasNext()) {
			    			String selecionar = "";
			    			LocalItemLit local = (LocalItemLit) iCol.next();
			    			
			    			if (locaisPais.contains(local)) {
			    				selecionar = "checked";
			    			}
		%>
			    			<input type="checkbox" class="form_check_radio" name="localItemLitPai" id="grupo<%=grupoPai.getCodLgp()%>" value="<%=local.getCodLit()%>" <%=selecionar%> onclick="validaCheck(this, form, this.value, <%=grupoPai.getCodLgp()%>);"><%=local.getIdentificacaoLit()%><br>
		<%
			    		}
					} else {
		%>
						Nenhum Local
		<%
					}
		%>
						</td>
						</tr>
					</table>
		<%
				}
			} else {
		%>
				Selecione o Local
		<%
			}
		%>
		</td>
	</tr>
	<tr><td class="label">&nbsp;</td></tr>
	<tr>
		<td width="20%" class="label" valign="top">Locais Filhos</td>
		<td>
			<input type="checkbox" class="form_check_radio" name="todos" value="2" onclick="selectAll(document.forms[0], this)"> Todos <br><br>
		<%
			if(!"".equals(Pagina.getParamStr(request, "localItemLit"))){
				List gruposFilhos = localGrupoDao.getGruposFilhos(localGrupo);
				
				Iterator itGF = gruposFilhos.iterator();
				while (itGF.hasNext()) {
					LocalGrupoLgp grupoFilho = (LocalGrupoLgp) itGF.next();
					
					/* cria uma tabela após o check do Grupo para que
						os locais fiquem identados ao utilizar taglib */
		%>			
					<input type="checkbox" class="form_check_radio" name="grupo" id="todos2" value="<%=grupoFilho.getCodLgp()%>" onclick="validaCheck(this, form, this.value, null);"><%=grupoFilho.getIdentificacaoLgp()%><br>
					<table>
						<tr>
						<td width="5%">&nbsp;&nbsp;&nbsp;</td>
						<td>
		<%
					List colecao = localItemDao.getLocalItemPorLocalGrupo(grupoFilho);
					
					if (colecao != null && colecao.size() > 0) {
						List locaisFilhos = localItemDao.getLocaisFilhos(localItem);
						
						Iterator iCol = colecao.iterator();
			    		while (iCol.hasNext()) {
			    			String selecionar = "";
			    			LocalItemLit local = (LocalItemLit) iCol.next();
			    			
			    			if (locaisFilhos.contains(local)) {
			    				selecionar = "checked";
			    			}
		%>
			    			<input type="checkbox" class="form_check_radio" name="localItemLitFilho" id="grupo<%=grupoFilho.getCodLgp()%>" value="<%=local.getCodLit()%>" <%=selecionar%> ><%=local.getIdentificacaoLit()%><br>
		<%
			    		}
					} else {
		%>
						Nenhum Local
		<%
					}
		%>
						</td>
						</tr>
					</table>
		<%
				}
			} else {
		%>
				Selecione o Local
		<%
			}
		%>
		</td>
	</tr>
<%
	}catch(ECARException e){
		org.apache.log4j.Logger.getLogger(this.getClass()).error(e);
		Mensagem.alert(_jspx_page_context, _msg.getMensagem("erro.exception"));
	} catch (Exception e){
	%>
		<%@ include file="/excecao.jsp"%>
	<%
	}
%>	

	<tr><td class="label">&nbsp;</td></tr>
	</table> 
	
	<util:barrabotoes incluir="Gravar" cancelar="Cancelar"/>
	
	<%@ include file="../../include/estadoMenu.jsp"%>
</form>

</div>

</body>
<%@ include file="../../include/mensagemAlert.jsp"%>
</html>