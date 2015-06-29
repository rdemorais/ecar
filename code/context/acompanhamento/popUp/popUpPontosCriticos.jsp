<jsp:directive.page import="comum.util.Data"/>
<jsp:directive.page import="java.util.Calendar"/>
<jsp:directive.page import="java.util.Iterator"/>
<jsp:directive.page import="ecar.pojo.ApontamentoApt"/>
<jsp:directive.page import="comum.util.Util"/>
<%@ include file="../../login/validaAcesso.jsp"%>
<%@ include file="../../frm_global.jsp"%>
<%@ taglib uri="/WEB-INF/ecar-util.tld" prefix="util" %>
<%@ taglib uri="/WEB-INF/ecar-combo.tld" prefix="combo" %>

<%@ page import="comum.util.Pagina" %>
<%@ page import="ecar.pojo.PontoCriticoPtc" %>
<%@ page import="ecar.dao.PontoCriticoDao" %> 
 
<html lang="pt-br">
<head>
<%@ include file="../../include/meta.jsp"%>
<%@ include file="/titulo.jsp"%>
<link rel="stylesheet" href="<%=_pathEcar%>/css/style_<%=configuracaoCfg.getEstilo().getNome()%>.css" type="text/css">
<script language="javascript" src="<%=_pathEcar%>/js/menu_retratil.js"></script>
<script language="javascript" src="<%=_pathEcar%>/js/focoInicial.js"></script>


<script language="javascript" src="<%=_pathEcar%>/js/datas.js"></script>
<script language="javascript" src="<%=_pathEcar%>/js/numero.js"></script>
<script language="javascript" src="<%=_pathEcar%>/js/validacoes.js"></script>
</head>

<body class="corpo_popup">

<%

try{
%>
	<form  name="form" method="post">
		<table name="form" class="form" width="100%">
		
			<%
			PontoCriticoDao pontoCriticoDao = new PontoCriticoDao(request);
			String corRelogio = "Branco";
			String descRelogio = "";										
			PontoCriticoPtc pontoCritico = (PontoCriticoPtc)pontoCriticoDao.buscar(PontoCriticoPtc.class, Long.valueOf(Pagina.getParamStr(request, "codPtc")));
			
			if(!"S".equals(Pagina.getParamStr(request, "ehPopup"))){
			%>
				<tr>
					<td align="right" colspan=2 ><a href="#" onclick="document.getElementById('popup_flutuante').style.display='none'"><b>Fechar</b></a></td>
				</tr>
			<%
			}
			%>
			
			<tr>
				<td class="espacador" colspan=2><img src="<%=_pathEcar%>/images/pixel.gif"></td>
			</tr>
			<tr>
				<td class="label">Tipo/Assunto:</td>
				<td>
				<%
					if(pontoCritico.getSisAtributoTipo() != null) {
						out.print(Pagina.trocaNull(pontoCritico.getSisAtributoTipo().getDescricaoSatb()));
					}
				%>
				</td>
			</tr>
			<tr>
				<td class="label"><b>Descri&ccedil;&atilde;o:</b></td>
				<td><%=Util.normalizaQuebraDeLinhaHTML(pontoCritico.getDescricaoPtc())%></td>
			</tr>			
			<tr>
				<td class="label">Âmbito:</td>
				<td>
				<%
				if("I".equals(pontoCritico.getIndAmbitoInternoGovPtc())){%>
					Interno
				<%
				}
				%>
				<%
				
				if("E".equals(pontoCritico.getIndAmbitoInternoGovPtc())){%>
					Externo
				<%
				}
				%>
				</td>					
			</tr>			
			<tr>
				<td class="label">Estado:</td>
				<%						
					String[] relogioAtual = pontoCriticoDao.getRelogioNaData(pontoCritico, Data.getDataAtual());
					corRelogio = relogioAtual[0];
					descRelogio = relogioAtual[1];
				%>
				<td>
					<img src="<%=_pathEcar%>/images/pc<%=corRelogio%>1.png" title="<%=descRelogio%>">
				</td>										
			</tr>
			<tr>
				<td class="label" valign="top">Data de Registro</td>
				<td><%=Pagina.trocaNullData(pontoCritico.getDataIdentificacaoPtc())%></td>					
			</tr>			
			<tr>
				<td class="label" valign="top"> Data Limite:</td><td>
				<% 
					Calendar dataLimite = Calendar.getInstance();
	   	    
	   	    		if(pontoCritico.getDataLimitePtc() != null){
						dataLimite.setTime(pontoCritico.getDataLimitePtc());
	   	    		}
	   	       		dataLimite.clear(Calendar.HOUR);
	   	    		dataLimite.clear(Calendar.HOUR_OF_DAY);
	   	    		dataLimite.clear(Calendar.MINUTE);
	   	    		dataLimite.clear(Calendar.SECOND);
	   	    		dataLimite.clear(Calendar.MILLISECOND);
	   	    		dataLimite.clear(Calendar.AM_PM);
	   	    		
	   	    		
	   	    		Calendar dataAtual = Calendar.getInstance();
		
					dataAtual.clear(Calendar.HOUR);
				    dataAtual.clear(Calendar.HOUR_OF_DAY);
				    dataAtual.clear(Calendar.MINUTE);
				    dataAtual.clear(Calendar.SECOND);
				    dataAtual.clear(Calendar.MILLISECOND);
				    dataAtual.clear(Calendar.AM_PM);	
				    
					if (dataAtual.after(dataLimite) &&	pontoCritico.getDataSolucaoPtc() == null) { 
					%>
						<span id="dtLimite" class="spanDtLimite"><b>
						<%=Pagina.trocaNullData(pontoCritico.getDataLimitePtc())%>				
						</b></span>
					<%
					}
					else {
					%>
						<%=Pagina.trocaNullData(pontoCritico.getDataLimitePtc())%>
					<%
					}
					%>
				</td>					
			</tr>
				
			<tr>
				<td class="espacadorBranco" colspan=2><img src="<%=_pathEcar%>/images/pixel.gif"></td></tr>
			<tr>
				<td class="label" valign="top"> Data da Solução:</td>
				<td>
				<%
					if(pontoCritico.getDataSolucaoPtc() != null){
						out.println(Pagina.trocaNullData(pontoCritico.getDataSolucaoPtc()));
					}
				%>
				</td>					
			</tr>						
			<tr>
				<td class="label" valign="top">Sugestão de Solu&ccedil;&atilde;o:</td>
				<td>
				<%
					if(pontoCritico.getDescricaoSolucaoPtc() != null){
						out.print(Util.normalizaQuebraDeLinhaHTML(pontoCritico.getDescricaoSolucaoPtc()));
					}
				%>
				</td>					
			</tr>
			<tr>
				<td class="label" valign="top">
					Respons&aacute;vel pela Solu&ccedil;&atilde;o:
				</td>
				<td>
				<%
					if(pontoCritico.getUsuarioUsu() != null) {
						out.print(pontoCritico.getUsuarioUsu().getNomeUsu());
					}
				%>
				</td>
			</tr>
			<tr>
				<td class="label" valign="top">
						Respons&aacute;vel pela Inclus&atilde;o:
				</td>
				<td>
				<%
					if(pontoCritico.getUsuarioUsu() != null) {
						out.print(pontoCritico.getUsuarioUsuInclusao().getNomeUsu());
					}
				%>
				</td>
			</tr>
			<%
				if(pontoCritico.getApontamentoApts() != null && pontoCritico.getApontamentoApts().size() > 0){
			%>
					<tr>
						<td class="espacadorBranco" colspan=2><img src="<%=_pathEcar%>/images/pixel.gif"></td>
					</tr>						
					<tr>
						<td class="label" valign="top">Apontamentos:</td>
						<td>
							<table valign="top">
								<tr>
									<td><b>Data</b></td><td><b>Responsável</b></td><td><b>Comentários</b></td>
								<tr>
								<%
								Iterator itApontamentos = pontoCritico.getApontamentoApts().iterator();
								while(itApontamentos.hasNext()){
									ApontamentoApt apontamento = (ApontamentoApt) itApontamentos.next();
									%> 
									<tr>
										<td><%=Pagina.trocaNullData(apontamento.getDataInclusaoApt())%></td>
										<td><%=apontamento.getUsuarioUsu().getNomeUsuSent()%></td>
										<td><%=apontamento.getTextoApt()%></td>
									<tr>									
									<%
								}
								%>
							</table>
						</td>					
					</tr>							
				<%
				}
				%>		
			<tr>
				<td class="espacador" colspan=2><img src="<%=_pathEcar%>/images/pixel.gif"></td>
			</tr>
			<%
			if(!"S".equals(Pagina.getParamStr(request, "ehPopup"))){
			%>
				<tr>
					<td align="right" colspan=2 ><a href="#" onclick="document.getElementById('popup_flutuante').style.display=	'none'"><b>Fechar</b></a></td>
				</tr>
			<%
			}
			%>
		</table>
	</form>	
<%

} catch (Exception e){
%>
	<%@ include file="/excecao.jsp"%>
<%
}
%>
</body>
</html>

<% /* Controla o estado do Menu (aberto ou fechado) */%>
<%@ include file="../../include/estadoMenu.jsp"%>
<%@ include file="../../include/mensagemAlert.jsp" %>