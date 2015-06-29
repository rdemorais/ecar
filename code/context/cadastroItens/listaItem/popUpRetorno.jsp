
<jsp:directive.page import="ecar.login.SegurancaECAR"/>
<jsp:directive.page import="ecar.permissao.ValidaPermissao"/><!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@ taglib uri="/WEB-INF/tlds/c.tld" prefix="c"%>

<%@ page import="java.util.Map" %>
<%@ page import="java.util.HashMap" %>
<%@ page import="ecar.dao.EstruturaDao" %>
<%@ page import="ecar.dao.ItemEstruturaDao" %>
<%@ page import="java.util.Set" %>
<%@ page import="java.util.List" %>
<%@page import="ecar.pojo.EstruturaEtt"%>
<%@page import="ecar.pojo.ItemEstruturaIett"%>

<script language="javascript" src="<%=_pathEcar%>/js/destaqueLinha.js"></script>
<script language="javascript">
function executarAcao(form,acao){

	var array = document.getElementsByName("itemEstruturaPesquisado");
	var i=0;
	var checado = false;

	if (acao == "confirmar") {
		for (i;i<array.length;i++){
			if (array[i].checked && !array[i].disabled){
				checado = true; 
			}
		}
	} else {
		checado = true;
	}

	if (checado){
		form.hidAcao.value = acao;
		form.action = "ctrl.jsp";
		form.submit();
	} else {
		alert ("Selecione pelo menos um item para associar!")
	}
}
</script>

	<%
	
		List listaItens = (List)session.getAttribute("listaItens");
		EstruturaDao estruturaDaoIncluded = new EstruturaDao(request);
		ItemEstruturaDao itemEstruturaDaoIncluded = new ItemEstruturaDao(request);
		
	%>
		
	<br><br>

	<div id="subconteudo" style="border-left-style: solid;border-left-color:#CED7E7; border-right-style: solid; border-right-color:#CED7E7;">
	
		<table width="100%" border="0" cellpadding="0" cellspacing="0">
			<tr><td class="espacador" colspan="2"></td></tr>
			<c:choose>
				<c:when test="<%=!((List)session.getAttribute("listaItens")).isEmpty()%>">
					<tr>
						<td width="30%" class="label" style="text-align: left">Resultado: <br></td>
					</tr>
					<tr>
						<td width="100%" colspan="2">
							<util:estruturaHierarquica lista="<%=listaItens%>" temCheckBox="true" nomeCheckBox="itemEstruturaPesquisado" marcarItensAssociadosEstruturaVirutal="true" estruturaVirtual="<%=(EstruturaEtt)session.getAttribute("estruturaVirtual")%>" exibirNomeEstrutura="true" seguranca="<%=(SegurancaECAR) session.getAttribute("seguranca")%>"/>
						</td>
					</tr>
					<tr><td class="espacador" colspan=2><img src="../../images/pixel.gif"></td></tr>
					<tr> 
						<td colspan="2" align="center">
							<input type="button" name="btnVoltar" value="Voltar" onclick='javascript:executarAcao(this.form,"voltar");'/>
							<input type="button" name="btnAssociar" value="Associar" onclick='javascript:executarAcao(this.form,"confirmar");'/>
						</td>
					</tr>
					<tr><td class="espacador" colspan=2><img src="../../images/pixel.gif"></td></tr>					
				</c:when>
				<c:otherwise>
					<tr><td class="espacador" colspan=2><img src="../../images/pixel.gif"></td></tr>
					<tr> 
						<td colspan=2 align=center>
							<input type="button" name="btnVoltar" value="Voltar" onclick='javascript:executarAcao(this.form,"voltar");'/>
						</td>
					</tr>
					<tr><td class="espacador" colspan=2><img src="../../images/pixel.gif"></td></tr>
					<script>
						javascript:alert('Não há itens para esta seleção.');
					</script>
				</c:otherwise>
			</c:choose>				
		</table>
	</div>
