<%@ taglib uri="/WEB-INF/ecar-util.tld" prefix="util" %>
<%@ taglib uri="/WEB-INF/ecar-combo.tld" prefix="combo" %>
<%@ page import="java.util.ArrayList"%>
<%@ page import="ecar.dao.OrgaoDao"%>
<%@ page import="ecar.dao.OrgaoDao"%>
<%@ page import="ecar.dao.ItemEstruturaDao"%>
<%@ page import="ecar.pojo.ItemEstruturaIett"%>
<%@ page import="java.util.Iterator"%>
<%@ page import="ecar.bean.AtributoEstruturaListagemItens" %>

<%@ taglib uri="/WEB-INF/ecar-combo.tld" prefix="combo" %>
<script language="javascript" src="../../js/numero.js"></script>

<%@ page import="comum.util.Util" %>

<%@ include file="../../login/validaAcesso.jsp"%>
<%@ include file="../../frm_global.jsp"%>
<%@ include file="../../include/meta.jsp"%>
<%@ include file="../../titulo.jsp"%>
<%@ include file="../../include/exibirAguarde.jsp"%>	
<html lang="pt-br">
	<head>
		<link rel="stylesheet" href="<%=_pathEcar%>/css/style_<%=configuracaoCfg.getEstilo().getNome()%>.css" type="text/css">
		
		<script language="javascript" src="../../js/menu_retratil.js"></script>
		<script language="javascript" src="../../js/focoInicial.js"></script>
		<script language="javascript" src="../../js/validacoes.js"></script>
		<script language="javascript" src="../../js/modalbox.js"></script>	
		<script language="javascript" src="../../js/prototype.js"></script>	
		
<script>

<% 

OrgaoDao orgaoDao = new OrgaoDao(request);
List listOrgao = orgaoDao.getOrgaoByPeriodicidade(new Long(2), new Character('S'));

//Linhas de acao - gestão PPA-2008-2011
ItemEstruturaDao itemEstruturaDao = new ItemEstruturaDao(request);
List listaItemEstruturaIett = itemEstruturaDao.getItensByEstrutura(new Long("22"));

%>

function aoClicarBtn3(form){

	//Trata o value dos checkbox antes de envia-lo ao servidor.
	var objetos = document.getElementsByTagName("input");
	for(i=0; i<objetos.length; i++) {
		if(objetos[i].type == 'checkbox') {

			var posicao = objetos[i].value.indexOf(':');
		 
			if(posicao > -1) {
				var valores = objetos[i].value.split(':');
				objetos[i].value = valores[0];
			}
		}
	}	
  form.target = "_blank";
  form.action = "../../relatorio/RelatorioPPA";
  form.submit();
  
}

function desativaCombo() {
	var combo = document.getElementById("listOrgao");
	combo.disabled = true;
}

function ativaCombo() {
	var combo = document.getElementById("listOrgao");
	combo.disabled = false;
}

function mostrarProgramas() {
	document.getElementById("programas").style.display = "block";
}

function esconderProgramas() {
	document.getElementById("programas").style.display = "none";
}

function popup_pesquisaCriterio(pojo, funcaoGetDadosPesquisa, parametros) {
	var jaIncluidosCom = document.getElementById("criteriosIncluidosCom");
	var jaIncluidosSem = document.getElementById("criteriosIncluidosSem");
		
    if (typeof funcaoGetDadosPesquisa == "undefined") funcaoGetDadosPesquisa = 'getDadosPesquisa';
    if (typeof parametros == "undefined") parametros = '';

    if (jaIncluidosCom != "" && jaIncluidosSem == "")
		return abreJanela('popup_pesquisaCriterio.jsp?hidPojo=' + pojo + '&jaIncluidosCom=' + jaIncluidosCom + '&hidFuncao=' + funcaoGetDadosPesquisa + parametros,'500','400','pesquisa');
    else if (jaIncluidosCom == "" && jaIncluidosSem != "")
		return abreJanela('popup_pesquisaCriterio.jsp?hidPojo=' + pojo + '&jaIncluidosSem=' + jaIncluidosSem + '&hidFuncao=' + funcaoGetDadosPesquisa + parametros,'500','400','pesquisa');
    else if (jaIncluidosCom != "" && jaIncluidosSem != "")
		return abreJanela('popup_pesquisaCriterio.jsp?hidPojo=' + pojo + '&jaIncluidosCom=' + jaIncluidosCom + '&jaIncluidosSem=' + jaIncluidosSem + '&hidFuncao=' + funcaoGetDadosPesquisa + parametros,'500','400','pesquisa');
	else
		return abreJanela('popup_pesquisaCriterio.jsp?hidPojo=' + pojo + '&hidFuncao=' + funcaoGetDadosPesquisa + parametros,'500','400','pesquisa');
		
}

/*Essa função é usada para pegar os critérios selecionados 
no popup --> NÂO MUDE A ASSINATURA DESTA FUNÇÃO!
*/
function getDadosPesquisa(codigosCom, codigosSem, descricao){
	document.getElementById("criteriosIncluidosCom").value = codigosCom;
	document.getElementById("criteriosIncluidosSem").value = codigosSem;
}

/*
	Marca ou desmarca os filhos do item pai.
*/
function marcarFilhos(objPai) {

	var iettPai = objPai.value.split(':');
	var booleano = objPai.checked;
	var objetos = document.getElementsByTagName("input");
	
	for(i=0; i<objetos.length; i++) {
		if(objetos[i].type == 'checkbox') {

			var valores = objetos[i].value.split(':');
		 
			if(valores[1] == iettPai[1]) {
				
				objetos[i].checked = booleano;
			}
		}
	}	
}

</script>
			
	</head>

	<body onload="javascript:esconderProgramas();">
		<%@ include file="../../cabecalho.jsp" %>
		<%@ include file="../../divmenu.jsp"%>		

<div id="conteudo">	

		<%@ include file="../../titulo_tela.jsp"%>
			<br/>
			<br/>
			<form id="relatorioPPA" name="relatorioPPA" method="post" action="../../relatorio/RelatorioPPA" >
			<util:barrabotoes btn3="Gerar Relatório"/>

			<table width="100%" border="0" align="center" class="bgCinzaClaro2">
				<tbody>
					<tr>
						<td colspan="3" >
							&nbsp;
						</td>					
					</tr>
					<tr>
						<td width="30%" align="right" height="22" >
							<input type="radio" class="form_check_radio" name="relatorio" value="orgao" checked="checked" onclick="javascript:ativaCombo();esconderProgramas();"/>
						</td>					
						<td>&Oacute;rg&atilde;o 
						<combo:ComboTag 
							nome="listOrgao"
							objeto="ecar.pojo.OrgaoOrg" 
							label="descricaoOrg" 
							value="codOrg" 
							order="descricaoOrg"
							colecao="<%=listOrgao%>"
						/>
						</td>
						<td>
							&nbsp;
						</td>						
					</tr>
					<tr>
						<td width="30%" align="right" height="22" >
							<input type="radio" class="form_check_radio" name="relatorio" value="programa" onclick="javascript:desativaCombo();mostrarProgramas();"/>
						</td>					
						<td width="40%" align="left" ><label>Programa</label></td>						
						<td>&nbsp;</td>						
					</tr>
					<tr>
						<td colspan="3" align="center">
							<div id="programas">						
								<table>
										<%
											List atributosListagem = new ArrayList(itemEstruturaDao.getItensOrdenados(
													itemEstruturaDao.getArvoreItens(listaItemEstruturaIett, null), null));
											
											//Programas
											Iterator it = atributosListagem.iterator();
											String iettPai = "";
											while(it.hasNext()) {						
												
												AtributoEstruturaListagemItens atList = (AtributoEstruturaListagemItens) it.next();
												
										%>
												<tr>
													<td nowrap>
													<%
														/* Identação pelo nível do item */
														int nivel = atList.getItem().getNivelIett().intValue();
														for(int i = 0; i < nivel;i++){
													%>
															<img src="<%=request.getContextPath()%>/images/pixel.gif" width="22" height="9">
													<%
														}
														
														if(nivel == 1) {
															iettPai = ":" + atList.getItem().getCodIett().toString();
															%><input type="checkbox" class="form_check_radio" value="<%=atList.getItem().getCodIett()%><%=iettPai%>" onclick="javascript:marcarFilhos(this);"><%=atList.getDescricao()%><%
														} else { %>
															<input type="checkbox" class="form_check_radio" name="programa"  value="<%=atList.getItem().getCodIett()%><%=iettPai%>"><%=atList.getDescricao()%><%
														}
													%>
														
													</td>
												</tr>
											<%
												
											}
										%>
								</table>
							</div>
						</td>
					</tr>					
					<tr>
						<td width="30%" align="right" height="22" >
							<input type="radio" class="form_check_radio" name="relatorio" value="completo" onclick="javascript:desativaCombo();esconderProgramas();"/>
						</td>
						<td width="40%"  align="left"><label>Completo</label></td>						
						<td>&nbsp;</td>						
					</tr>
					<tr>
						<td></td>
						<td>
							<input type="button" class="botao" name="selCriterios" value="Selecionar Crit&eacute;rios" onclick="popup_pesquisaCriterio('ecar.popup.PopUpCriterio');">
						</td>
						<td></td>						
					</tr>					
					<tr>
						<td colspan="3" >
							&nbsp;
						</td>					
					</tr>								
				<tr>
						<td  align="center" colspan="3">
							<table border="0" cellpadding="0" cellspacing="0" align="center" >
								<tbody>
									<tr>
										<td>Título:&nbsp;</td>
										<td><input type="text" id="titulo" name="titulo" size="60" maxlength="150"  >&nbsp;<%=Util.getTagDica("dicaTitulo",request.getContextPath(),"Título do relatório que pode ser personalizado alterando-se o texto padrão.") %></td>
									</tr>								
									<tr>
										<td>Data/Hora:&nbsp;</td>
										<td> <input type="text" id="data" name="data"  >&nbsp;<%=Util.getTagDica("dicaData",request.getContextPath(),"Data e hora de emissão do relatório, podendo-se alterar a data padrão (data e hora atual) para uma nova data e hora informadas.") %></td>
									</tr>
									<tr>
										<td>Página:&nbsp;</td>
										<td> <input type="text" id="pagina" name="pagina" onkeypress="javascript:return(digitaNumero(this, event));">&nbsp;<%=Util.getTagDica("dicaPagina",request.getContextPath(),"Número da página inicial do relatório, podendo-se alterar o valor padrão(1) para um novo valor inicial.") %></td>
									</tr>																					
								</tbody>
							</table>
						</td>
					</tr>
					<tr>
						<td colspan="3" >
							&nbsp;
						</td>					
					</tr>										
				</tbody>
			</table>
			<input type=hidden name="criteriosIncluidosCom" id="criteriosIncluidosCom" value="">
			<input type=hidden name="criteriosIncluidosSem" id="criteriosIncluidosSem" value="">
			<util:barrabotoes btn3="Gerar Relatório"/>
			</form>
			
</div>
	</body>
	<%@ include file="../../include/ocultarAguarde.jsp"%>
	<%@ include file="../../include/estadoMenu.jsp" %>
	<%@ include file="../../include/mensagemAlert.jsp" %>
		
</HTML>