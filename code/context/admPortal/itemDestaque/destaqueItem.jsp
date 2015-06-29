<%@ page import="ecar.pojo.DestaqueAreaDtqa" %>
<%@ page import="ecar.dao.DestaqueAreaDao" %>
<%@ page import="ecar.pojo.DestaqueSubAreaDtqsa" %>
<%@ page import="java.util.Set" %>
<%@ page import="java.util.Iterator" %>
<%@ page import="ecar.dao.DestaqueSubAreaDao" %>
<%@ page import="java.util.List" %>
<%@ page import="ecar.pojo.SegmentoAgendaBean" %>
<%@ page import="ecar.pojo.SegmentoSgt" %>
<%@ page import="ecar.dao.SegmentoDao" %>
<%@ page import="ecar.dao.SegmentoCategoriaDao" %>
<%@ page import="java.util.Collection" %>
<%@ page import="ecar.pojo.SegmentoCategoriaSgtc" %>
<%@ page import="ecar.dao.SegmentoItemDao" %>
<%@ page import="ecar.pojo.AgendaAge" %>
<%@ page import="ecar.dao.AgendaDao" %>
<%@ page import="ecar.pojo.AgendaOcorrenciaAgeo" %>
<%@ page import="comum.util.Data" %>
<%@ page import="ecar.dao.AgendaOcorrenciaDao" %>
<%@ include file="../../frm_global.jsp"%>
<%@ include file="../../login/validaAcesso.jsp"%>
<%@ taglib uri="/WEB-INF/ecar-util.tld" prefix="util" %>
<%@ taglib uri="/WEB-INF/ecar-combo.tld" prefix="combo" %>

<%
DestaqueSubAreaDtqsa destaqueSubArea = (DestaqueSubAreaDtqsa) new DestaqueSubAreaDao(request).buscar(
										DestaqueSubAreaDtqsa.class, 
										Long.valueOf(Pagina.getParamStr(request, "codSubArea")));
int destaquesPermitidos = destaqueSubArea.getQtdMaxItensDtqsa().intValue();										
int destaquesExistentes = destaqueSubArea.getDestaqueItemRelDtqirs().size();
%>

<html lang="pt-br">
<head>
<%@ include file="../../include/meta.jsp"%>
<%@ include file="/titulo.jsp"%>
<link rel="stylesheet" href="<%=_pathEcar%>/css/style_<%=configuracaoCfg.getEstilo().getNome()%>.css" type="text/css">
<script language="javascript" src="../../js/menu_retratil.js"></script>
<script language="javascript" src="../../js/focoInicial.js"></script>
<script language="javascript" src="../../js/validacoes.js"></script>
<script>
function voltar(){
	document.forms[0].action = "selecaoSubArea.jsp";
	document.forms[0].submit();
}
function limpaCategoria(){
	document.forms[0].segmentoCategoriaSgtc.value = "";
}
function reload(){
	document.forms[0].action = "destaqueItem.jsp";
	document.forms[0].submit();
}
function vincularSegmentoItem(){
	//valida se esta selecionada alguma opcao e quantas selecionadas
	qtdeSelecionada = 0;
	for(i=0; i<	document.forms[0].segmentoItem.options.length; i++){
		if(document.forms[0].segmentoItem.options[i].selected)
			qtdeSelecionada++;
	}
	if(qtdeSelecionada > 0){
		if(qtdeSelecionada <= <%=destaquesPermitidos - destaquesExistentes%>){
			document.forms[0].tipoVinculo.value = "S";
			document.forms[0].action = "ctrlDestaqueItem.jsp";
			document.forms[0].submit();				
		} else {
			alert("Apenas mais <%=destaquesPermitidos - destaquesExistentes%> destaques podem ser vinculados a esta sub ·rea. Foram selecionados " + qtdeSelecionada + ".");
		}
	} else {
		alert("Selecione um item para inclus„o");
	}
}
function vincularOcorrenciaAgenda(){
	//valida se esta selecionada alguma opcao e quantas selecionadas
	qtdeSelecionada = 0;
	for(i=0; i<	document.forms[0].segmentoItem.options.length; i++){
		if(document.forms[0].segmentoItem.options[i].selected)
			qtdeSelecionada++;
	}
	if(qtdeSelecionada > 0){
		if(qtdeSelecionada <= <%=destaquesPermitidos - destaquesExistentes%>){
			document.forms[0].tipoVinculo.value = "A";
			document.forms[0].action = "ctrlDestaqueItem.jsp";
			document.forms[0].submit();				
		} else {
			alert("Apenas mais <%=destaquesPermitidos - destaquesExistentes%> destaques podem ser vinculados a esta sub ·rea. Foram selecionados " + qtdeSelecionada + ".");
		}
	} else {
		alert("Selecione um item para inclus„o");
	}
}
function excluirDestaque(){
	//valida se esta selecionada alguma opcao
	var achou = false;
	for(i=0; i<	document.forms[0].segmentoItemVinculados.options.length; i++){
		if(document.forms[0].segmentoItemVinculados.options[i].selected)
			achou = true;
	}
	if(achou){
		document.forms[0].tipoVinculo.value = "EXC";
		document.forms[0].action = "ctrlDestaqueItem.jsp";
		document.forms[0].submit();		
	} else {
		alert("Selecione um item para exclus„o");
	}
}

</script>
</head>

<!-- sempre colocar o foco inicial no primeiro campo -->
<body>

<%@ include file="../../cabecalho.jsp" %>
<%@ include file="../../divmenu.jsp"%>
<%
try{
%>
<div id="conteudo">

<form name="form" method="post">

<!-- TITULO -->
<%@ include file="/titulo_tela.jsp"%>

<table class="form"> 
	<input type="hidden" name="destaqueAreaDtqa" value="<%=destaqueSubArea.getDestaqueAreaDtqa().getCodDtqa()%>">
	<input type="hidden" name="codSubArea" value="<%=Pagina.getParamStr(request, "codSubArea")%>">
	<input type="hidden" name="tipoVinculo">
		
	<tr>
		<td colspan="2"></td>
	</tr>
	<tr>
		<td class="label">¡rea</td>
		<td><%=destaqueSubArea.getDestaqueAreaDtqa().getDescricaoDtqa()%></td>
	</tr>
	<tr>
		<td class="label">Sub ¡rea</td>
		<td><%=destaqueSubArea.getIdentificacaoDtqsa()%></td>
	</tr>	
	<tr>
		<td class="label">M·ximo de itens permitidos</td>
		<td><%=destaquesPermitidos%> (atualmente <%=destaquesExistentes%>)</td>
	</tr>	
	<tr>
		<td class="label">Segmentos e Agendas</td>
		<td>
			<select name="segAgenda" onchange="limpaCategoria(); reload()">
			<option></option>
			<%
			List segmentosAgendas = new DestaqueSubAreaDao(request).getSegmentosAgendas();
			Iterator it = segmentosAgendas.iterator();
			while(it.hasNext()){
				SegmentoAgendaBean sa = (SegmentoAgendaBean) it.next();
				String value = "";
				if(sa.isAgenda())
					value = "AGD" + sa.getCodigo(); 
				else
					value = "SEG" + sa.getCodigo();
				String strSelected = "";
				if(value.equals(Pagina.getParamStr(request,"segAgenda"))){
					strSelected = "selected";
				}
				%>
				<option <%=strSelected%> value="<%=value%>"><%=sa.getDescricao()%></option>
				<%
			}
			%>
			</select>			
		</td>
	</tr>	
	<%
	if(!"".equals(Pagina.getParamStr(request, "segAgenda"))){
		String valorParam = Pagina.getParamStr(request, "segAgenda");
		if("AGD".equals(valorParam.substring(0,3))){			
			%>
			<input type="hidden" name="segmentoCategoriaSgtc" value="">
			<%
		}
		if("SEG".equals(valorParam.substring(0,3))){
			Long codigo = Long.valueOf(valorParam.substring(3));
			SegmentoSgt segmento = (SegmentoSgt) new SegmentoDao(request).buscar(SegmentoSgt.class, codigo);
			Collection categorias = new SegmentoCategoriaDao(request).getAtivosBySegmento(segmento);
			%>
			<tr>
				<td class="label">Categoria</td>
				<td>
					<combo:ComboTag 	
						nome="segmentoCategoriaSgtc"
						objeto="ecar.pojo.SegmentoCategoriaSgtc" 
						label="tituloSgtc" 
						value="codSgtc" 
						style="width:200"
						order="tituloSgtc"
						selected="<%=Pagina.getParamStr(request,"segmentoCategoriaSgtc")%>"
						colecao="<%=categorias%>"
						scripts="onchange='reload()'"
					/>										
				</td>
			</tr>
			<%
		}		
	} else {
		%><input type="hidden" name="segmentoCategoriaSgtc" value=""><%
	}
	%>
	<%
	if(!"".equals(Pagina.getParamStr(request, "segmentoCategoriaSgtc"))){
		Long codSC = Long.valueOf(Pagina.getParamStr(request, "segmentoCategoriaSgtc"));
		SegmentoCategoriaSgtc segmentoCategoria = (SegmentoCategoriaSgtc) 
													new SegmentoCategoriaDao(request).buscar(SegmentoCategoriaSgtc.class, codSC);
		Collection segmentoItens = new SegmentoItemDao(request).getSegmentosItemNaoVinculadosASubArea(segmentoCategoria, destaqueSubArea);																		
		%>
			<tr>
				<td class="label">Itens do Segmento<br>(selecione para inserir)</td>
				<td>
					<combo:ComboTag 	
						nome="segmentoItem"
						objeto="ecar.pojo.SegmentoItemSgti" 
						label="tituloSgti" 
						value="codSgti"
						style="width:200"						
						scripts="size=5 multiple"
						order="tituloSgti"
						option="nao"						
						colecao="<%=segmentoItens%>"
					/>										
				</td>
			</tr>	
			<tr>
				<td>&nbsp;</td>
				<td>
					<input type="button" value="Incluir Destaque" onclick="vincularSegmentoItem()"
					<%if(!(destaquesExistentes < destaquesPermitidos)) out.println("disabled");%>
					>
				</td>
			</tr>	
		<%
	} %>
	<%
	if(!"".equals(Pagina.getParamStr(request, "segAgenda")) && Pagina.getParamStr(request, "segAgenda").substring(0,3).equals("AGD")){
			Long codigo = Long.valueOf(Pagina.getParamStr(request, "segAgenda").substring(3));
			AgendaDao agendaDao = new AgendaDao(request);
			AgendaAge agenda = (AgendaAge) agendaDao.buscar(AgendaAge.class, codigo);
			Collection ocorrencias = new AgendaOcorrenciaDao(request).getAgendaOcorrenciaNaoVinculadaASubAreaOrderByData(agenda, destaqueSubArea);	
			%>
			<tr>
				<td class="label">Itens do Segmento<br>(selecione para inserir)</td>
				<td>
					<select name="segmentoItem" style="width:200" size="5" multiple>
					<% 
						Iterator itOc = ocorrencias.iterator();
						while(itOc.hasNext()){
							AgendaOcorrenciaAgeo ocorrencia = (AgendaOcorrenciaAgeo) itOc.next();
							%><option value="<%=ocorrencia.getCodAgeo()%>">
							  <%=new AgendaOcorrenciaDao(request).getLabelExibicao(ocorrencia)%>
							  </option><%
						}
					%>
					</select>					
				</td>
			</tr>	
			<tr>
				<td>&nbsp;</td>
				<td>
					<input type="button" value="Incluir Destaque" onclick="vincularOcorrenciaAgenda()"
					<%if(!(destaquesExistentes < destaquesPermitidos)) out.println("disabled");%>
					>
				</td>
			</tr>	
							
			<%
	}
	%>
	<tr valign="top">
		<td class="label">Itens em Destaque na sub¡rea<br>(selecione para remover)</td>
			<td>
			<%
			List destaques = new DestaqueSubAreaDao(request).getItensDestaqueSubArea(destaqueSubArea);
			%>
				<combo:ComboTag 	
					nome="segmentoItemVinculados"
					objeto="ecar.pojo.SegmentoAgendaBean" 
					label="descricao" 
					value="codigo"	
					scripts="size=5 multiple"
					style="width:200"
					option="nao"						
					colecao="<%=destaques%>"
				/>					
			</td>		
	</tr>
	<tr>
			<td>&nbsp;</td>
			<td>
				<input type="button" value="Excluir Destaque" onclick="excluirDestaque()">
			</td>
	</tr>		
	<tr>
		<td colspan="2" class="label"> 
		<input type="button" value="Voltar" onclick="javascript:voltar();">				
		</td>		
	</tr>
</table> 

</form>

</div>
<%
} catch (Exception e){
%>
	<%@ include file="/excecao.jsp"%>
<%
}
%>
</body>
<% /* controla o estado do menu (aberto ou fechado)*/ %>
<%@ include file="../../include/estadoMenu.jsp"%>
<%@ include file="../../include/mensagemAlert.jsp"%>
</html>