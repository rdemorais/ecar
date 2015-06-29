<%@ page import="comum.database.Dao" %>
<%@ page import="ecar.dao.EstruturaDao" %>
<%@ page import="ecar.pojo.EstruturaEtt" %>
<jsp:directive.page import="java.util.Map"/>
<jsp:directive.page import="java.util.HashMap"/>
<jsp:directive.page import="java.util.TreeMap"/>
<jsp:directive.page import="ecar.dao.ItemEstruturaDao"/>
<jsp:directive.page import="java.util.List"/>
<jsp:directive.page import="java.util.Iterator"/>
<jsp:directive.page import="ecar.pojo.ItemEstruturaIett"/>
<jsp:directive.page import="comum.util.Util"/>
<jsp:directive.page import="java.util.ArrayList"/>


<%@ include file="../../frm_global.jsp"%>
<%@ taglib uri="/WEB-INF/ecar-combo.tld" prefix="combo" %>
<%@ taglib uri="/WEB-INF/ecar-util.tld" prefix="util" %>
<%@ taglib uri="/WEB-INF/tlds/c.tld" prefix="c"%>


<html lang="pt-br">
<head>
<%@ include file="../../include/meta.jsp"%>
<%@ include file="/titulo.jsp"%>

<link rel="stylesheet" href="<%=_pathEcar%>/css/style_<%=configuracaoCfg.getEstilo().getNome()%>.css" type="text/css">
<script language="javascript" src="<%=_pathEcar%>/js/focoInicial.js"></script>
<script language="javascript" src="<%=_pathEcar%>/js/frmPesquisa.js"></script>
<script language="javascript" src="<%=_pathEcar%>/js/validacoes.js"></script>
<script language="javascript" src="<%=_pathEcar%>/js/destaqueLinha.js"></script>

<script language="javascript" type="text/javascript">
function validarPesquisa(form){
		form.hidAcao.value='pesquisarModelo';
		form.action='ctrl.jsp';
		form.submit();
}

function clickEnter(event, form){	
	if (event.keyCode == 13) {
		validarPesquisa(form);
	} 					
}


var itemAnterior=0;

function ativarFilhos(item){
	if (itemAnterior!=item){
		if (document.getElementsByName('iett_'+itemAnterior)){
			var itensFilhos = document.getElementsByName('iett_'+itemAnterior);
	
			for ( var i = 0; i < itensFilhos.length; i++) {
				itensFilhos[i].checked = false;
				itensFilhos[i].disabled = true;
			}
		}
	}	
	
	if (document.getElementsByName('iett_'+item)){
		var itensFilhos = document.getElementsByName('iett_'+item);
	
		for ( var i = 0; i < itensFilhos.length; i++) {
			itensFilhos[i].disabled = false;
		}
	}
	
	itemAnterior = item;
}


function prosseguir(form){
		if (document.getElementsByName('iett')){
			var itens = document.getElementsByName('iett');
			var marcou = false;
	
		for ( var i = 0; i < itens.length; i++) {
			if (itens[i].checked){
				marcou = true
			}
		}
		
		if (!marcou ){
			alert('Informar um item para servir de modelo.');
			return false;
		}
		
	}	
		form.hidAcao.value='funcoesModelo';
		form.action='ctrl.jsp';//?hidAcao=funcoesModelo'
		form.submit();
}

</script>

</head>
<%
try {
		EstruturaDao estruturaDao = new EstruturaDao(request);
		EstruturaEtt estrutura = (EstruturaEtt)estruturaDao.buscar(EstruturaEtt.class, Long.valueOf(Pagina.getParamStr(request, "codEtt")));
 %>

<h1> Modelos da Estrutura</h1>
<body onload="onLoad(document.form);" class="corpo_popup">
<form name="form" method="post" action="">
	<input type="hidden" name="hidAcao" value="pesquisarModelo" >
    <input type="hidden" name="codEtt" value="<%=estrutura.getCodEtt() %>">
    <input type="hidden" name="ultimoIdLinhaDetalhado" id="ultimoIdLinhaDetalhado" value="<%=Pagina.getParamStr(request,"ultimoIdLinhaDetalhado")%>">	
	<input type="hidden" name="ultimoIdLinhaExpandido" id="ultimoIdLinhaExpandido" value="<%=Pagina.getParamStr(request,"ultimoIdLinhaExpandido")%>"> 
					
	<table class="form">
		<tr>
			<td width="30%" class="label"><label for="nomePesquisa">Argumento de Pesquisa: </label></td>
			<td width="70%" >
				<input type="text" name="nomePesquisa" id="nomePesquisa" size="55" maxlength="50" onkeypress="return clickEnter(event, this.form);">
			</td>
		</tr>
		<tr>
			<td class="label" style="vertical-align: top;"></label>Pesquisar em: </td>
			<td>
				<label><input type="checkbox" checked="checked" name="campo" readonly="readonly"> Nome</label>
			</td>
		</tr>
		<tr>
			<td class="label" style="vertical-align: top;">Copiar </td>
			<td>
				<%
					String indCopiar = Pagina.getParam(request, "hidIndCopiar") != null ? Pagina.getParam(request, "hidIndCopiar") : null;
				 %>
			
				<label><input type="radio" name="indCopiar" value="apenasItem" <%if (indCopiar == null || indCopiar.equals("apenasItem")){ %> checked <%}%>> Estrutura Atual </label><br>
				<label><input type="radio" name="indCopiar" value="todaEstrutura" <%if (indCopiar != null && indCopiar.equals("todaEstrutura")){ %> checked <%}%>> Estruturas Atual e Filhas</label>
			</td>
		</tr>
		<tr> 
			<td>&nbsp;</td>
			<td style="padding-left: 60">
				<input type="button" name="pesquisar" value="Pesquisar" onclick='javascript:validarPesquisa(this.form);' />
			</td>
		</tr>
	</table>

<br>

<% 
int nivel=0; //Informa o nivel da arvore de itens 

if ("pesquisarModelo".equals(Pagina.getParamStr(request,"hidAcao") ) ) { 
		//Lista com os itens pertencentes a estrutura que são modelos
		List  listItens = (List) session.getAttribute("listItens");
		session.removeAttribute("listItens"); 

			
		
			if (listItens!= null && listItens.size()>0) {
			%>
		<h1>Resultado</h1>
		  <div id="subconteudo" style="border-left-style: solid;border-left-color:#CED7E7; border-right-style: solid; border-right-color:#CED7E7;">
		  
		  
			<table width="100%" border="0" cellpadding="0" cellspacing="0">	
				<tr><td class="espacador" colspan="2"></td></tr>
				<tr ><td colspan="2" class="label" style="text-align: left;"><%= estrutura.getNomeEtt()%></td></tr>
				<tr><td class="espacador" colspan="2"></td></tr>
			<% 
				
				Iterator itItens = listItens.iterator();
				ItemEstruturaIett itemPai=null;
				ItemEstruturaIett itemAnterior=null;
				//chave é o código da estrutura e o valor o nível em que se apresenta
				Map mapEstruturasNivel = new HashMap();
				mapEstruturasNivel.put(estrutura.getCodEtt(), new Integer(0));
				while (itItens.hasNext()) {
					ItemEstruturaIett item = (ItemEstruturaIett) itItens.next();
					EstruturaEtt estruturaItens = (EstruturaEtt)estruturaDao.buscar(EstruturaEtt.class, item.getEstruturaEtt().getCodEtt());
					
					if (item.getEstruturaEtt().getCodEtt().equals(estrutura.getCodEtt())){
						nivel=0;
						itemPai = item;
						//Verifica se o item anterior é pai do item atual
					}   else if (mapEstruturasNivel.containsKey(item.getEstruturaEtt().getCodEtt())){
						nivel = ((Integer)mapEstruturasNivel.get(item.getEstruturaEtt().getCodEtt())).intValue();
					} else { // if ( itemAnterior.getCodIett().equals(item.getItemEstruturaIett().getCodIett() )  ) {
						  int nivelPai = ((Integer) mapEstruturasNivel.get(item.getItemEstruturaIett().getEstruturaEtt().getCodEtt())).intValue();
						  nivel = ++nivelPai;
				 		  mapEstruturasNivel.put(item.getEstruturaEtt().getCodEtt(), Integer.valueOf(nivel));
					}
					
					if (nivel==0) {

			%>
				<tr class="cor_nao" onmouseover="javascript:destacaLinha(this,'over','')" onmouseout="javascript: destacaLinha(this,'out','cor_nao')" onClick="javascript: destacaLinha(this,'click','cor_nao');">
					<td width="2" nowrap> &nbsp; </td>
					<td> 
						<label>  
							<input type="radio" name="iett" value="<%=item.getCodIett()%>" onchange="ativarFilhos(<%=item.getCodIett()%>)"> <%=item.getEstruturaEtt().getNomeEtt()%> - <%=new ItemEstruturaDao(request).criaColunaConteudoColunaArvoreAjax(item, estruturaItens)%>
						</label>  
					</td>
				</tr>
			<%
					} else {
			%>
				<tr class="cor_nao" onmouseover="javascript:destacaLinha(this,'over','')" onmouseout="javascript: destacaLinha(this,'out','cor_nao')" >
					<td width="2"> &nbsp; </td>
					<td nowrap> 
						<label>  
							<%= Util.getEspacosEmBrancoHTML(nivel*3 ) %> <input type="checkbox" name="iett_<%=itemPai.getCodIett()%>" value="<%=item.getCodIett()%>" disabled> <%=item.getEstruturaEtt().getNomeEtt()%> - <%=new ItemEstruturaDao(request).criaColunaConteudoColunaArvoreAjax(item, estruturaItens)%>
						</label>  
					</td>
				</tr>
			<%
					}
					itemAnterior = item;

				}// fim while
			
%>		
					<tr>
						<td class="espacador" colspan="2">
							<img src="../../images/pixel.gif">
						</td>
					</tr>
					<tr> 
						<td colspan="2" align=center>
							<input type="button" name="btnAvancar" value="Prosseguir..." onclick="prosseguir(this.form);"/>
						</td>
					</tr>
					<tr><td class="espacador" colspan="2"><img src="../../images/pixel.gif"></td></tr>

				<%--c:otherwise>
					<tr><td class="espacador" colspan="2"><img src="../../images/pixel.gif"></td></tr>
					<tr> 
						<td colspan="2" align="center">
							<input type="button" name="btnVoltar" value="Voltar" onclick='javascript:executarAcao(this.form,"voltar");'/>
						</td>
					</tr>
					<tr><td class="espacador" colspan="2"><img src="../../images/pixel.gif"></td></tr>
					<script>
						javascript:alert('Não há itens para esta seleção.');
					</script>
				</c:otherwise>
			</c:choose--%>
		</table>
	</form>
	</div>
	<%
	} else { 
	%>
		<p style="padding-left: 30">
		Nenhum item encontrado.
		</p>
<%	}

} // fim if hidAcao=pesquisarModelo %>		


<%} catch (Exception e){
	org.apache.log4j.Logger.getLogger(this.getClass()).error(e);
%>
	<%@ include file="/excecao.jsp"%>
<%}%>

</body>
</html>