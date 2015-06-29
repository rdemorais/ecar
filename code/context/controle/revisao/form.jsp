<%@ taglib uri="/WEB-INF/tlds/c.tld" prefix="c"%>

<input type=hidden name="hidAcao" value="">
<input type=hidden name="codPrev" value="<%=Pagina.getParamStr(request, "codPrev")%>">

<tr>
			<td colspan=2>
				<div class="camposobrigatorios">* Campos de preenchimento obrigat&oacute;rio</div>
			</td>
	</tr>
	<tr>
		<td class="label">* Descrição</td>
		<td>		
			<input type="text" name="descricaoPrev" size="30" <%=_disabled%> maxlength="30"  value="<%=Pagina.trocaNull(prev.getDescricaoPrev())%>">
		</td>
	</tr>		
	<tr>
		<td class="label">* Data de Início</td>
		<td>		
			<input type="text" name="dtInicioPrev" size="15" <%=_disabled%> maxlength="10"  value="<%=Pagina.trocaNullData(prev.getDtInicioPrev())%>"  onkeyup="mascaraData(event, this);">
			<c:if test="<%=_disabled.equals("")%>">
				<img class="posicao" title="Selecione a data" src="../../images/icone_calendar.gif" onclick="open_calendar('data', document.forms[0].dtInicioPrev, '')">
			</c:if>
		</td>
	</tr>		
	<tr>
		<td class="label">* Data de Fim</td>
		<td>		
			<input type="text" name="dtFimPrev" size="15" <%=_disabled%> maxlength="10"  value="<%=Pagina.trocaNullData(prev.getDtFimPrev())%>"  onkeyup="mascaraData(event, this);">
			<c:if test="<%=_disabled.equals("")%>">
				<img class="posicao" title="Selecione a data" src="../../images/icone_calendar.gif" onclick="open_calendar('data', document.forms[0].dtFimPrev, '')">
			</c:if>
		</td>
	</tr>		
<% /* Controla o estado do Menu (aberto ou fechado) */%>
<%@ include file="../../include/estadoMenu.jsp"%>
