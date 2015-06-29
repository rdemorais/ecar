
<jsp:directive.page import="ecar.login.SegurancaECAR"/>
<%@ taglib uri="/WEB-INF/ecar-util.tld" prefix="util" %>
<%@ include file="../../frm_global.jsp"%>

<%@ page import="java.util.List" %>
<%@ page import="java.util.Iterator" %>
<%@ page import="ecar.dao.AcompRealFisicoDao" %>
<%@ page import="ecar.pojo.AcompRealFisicoArf" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="ecar.login.SegurancaECAR" %>
<%@ page import="ecar.permissao.ValidaPermissao" %> 
<%@ page import="ecar.pojo.AcompRealFisicoLocalArfl" %>
<%@ page import="comum.util.Util" %>
<%@ page import="java.util.Collections" %>
<%@ page import="java.util.Comparator" %>
<%@ page import="ecar.dao.AcompRealFisicoLocalDao" %>
<%@ page import="comum.util.Data" %>

<html lang="pt-br">
<head>
<%@ include file="../../include/meta.jsp"%>
<%@ include file="/titulo.jsp"%>

<link rel="stylesheet" href="<%=_pathEcar%>/css/style_<%=configuracaoCfg.getEstilo().getNome()%>.css" type="text/css">

<script language="javascript" src="../../js/focoInicial.js"></script>
<script language="javascript" src="../../js/validacoes.js"></script>
<script language="javascript" src="../../js/frmPadraoItemEstrut.js"></script>
<script language="javascript" src="../../js/popUp.js"></script>
<script language="javascript" src="../../js/numero.js"></script>
<script language="javascript" src="../../js/tableSort.js"></script>

<%
AcompRealFisicoDao acompRealFisicoDao = new AcompRealFisicoDao(request);
AcompRealFisicoLocalDao acompRealFisicoLocalDao = new AcompRealFisicoLocalDao(request);

AcompRealFisicoArf arf = (AcompRealFisicoArf)acompRealFisicoDao.buscar(AcompRealFisicoArf.class, Long.valueOf(Pagina.getParamStr(request, "codARF")));

ValidaPermissao validaPermissao = new ValidaPermissao();
//if ( !validaPermissao.permissaoConsultaParecerIETT( arf.getItemEstruturaIett().getCodIett() , (SegurancaECAR)session.getAttribute("seguranca") ) )
//{
//	response.sendRedirect( request.getContextPath() +  "/acessoIndevido.jsp" );
//}



String qtdeOuValor = "Quantidade";
String indQtdeOuValor = arf.getItemEstrtIndResulIettr().getIndTipoQtde();
if ("V".equalsIgnoreCase(indQtdeOuValor)) {
	qtdeOuValor = "Valor";
}
%>


<script language="Javascript">
function mostrarLocaisAsc() {
   document.getElementById('tbLocaisAsc').style.display='';
   document.getElementById('tbLocaisDesc').style.display='none';
   document.getElementById('tbQtdeAsc').style.display='none';
   document.getElementById('tbQtdeDesc').style.display='none';
}
function mostrarLocaisDesc() {
   document.getElementById('tbLocaisAsc').style.display='none';
   document.getElementById('tbLocaisDesc').style.display='';
   document.getElementById('tbQtdeAsc').style.display='none';
   document.getElementById('tbQtdeDesc').style.display='none';
}
function mostrarQtdeAsc() {
   document.getElementById('tbLocaisAsc').style.display='none';
   document.getElementById('tbLocaisDesc').style.display='none';
   document.getElementById('tbQtdeAsc').style.display='';
   document.getElementById('tbQtdeDesc').style.display='none';
}
function mostrarQtdeDesc() {
   document.getElementById('tbLocaisAsc').style.display='none';
   document.getElementById('tbLocaisDesc').style.display='none';
   document.getElementById('tbQtdeAsc').style.display='none';
   document.getElementById('tbQtdeDesc').style.display='';
}
</script>

</head>

<body class="corpo_popup">
<%
try{
%>
<H1><%=qtdeOuValor%> por Local (<%=Data.getAbreviaturaMes(Integer.parseInt(arf.getMesArf().toString())) + "/" + arf.getAnoArf()%>)</H1>

<form name="form" method="post" action="">
	
	<input type="hidden" name="hidAcao">
	<input type="hidden" name="codARF" value="<%=Pagina.getParamStr(request, "codARF")%>">	

	<table border="0">
		<tr>
			<td class="label">
				Indicador de Resultado
			</td>
			<td class="texto">
				<%=Pagina.getParamStr(request, "nomeIndicador")%>
				<input type="hidden" name="nomeIndicador" value="<%=Pagina.getParamStr(request, "nomeIndicador")%>">
			</td>
		</tr>
	</table>
	<br>
	<%
	List listLocais = acompRealFisicoLocalDao.getAcompRealFisicoLocalByArf(arf);
	if(listLocais != null && !listLocais.isEmpty()) {
		
		List locaisOrdAsc = new ArrayList(listLocais);

		Collections.sort(locaisOrdAsc,
				new Comparator(){
	        		public int compare(Object o1, Object o2) {
	        			AcompRealFisicoLocalArfl a1 = (AcompRealFisicoLocalArfl) o1;
	        			AcompRealFisicoLocalArfl a2 = (AcompRealFisicoLocalArfl) o2;
	        			return a1.getLocalItemLit().getIdentificacaoLit().compareTo(a2.getLocalItemLit().getIdentificacaoLit());
	        		}
				}
			);
		
		List locaisOrdDesc = new ArrayList(locaisOrdAsc);
		Collections.reverse(locaisOrdDesc);

		List qtdeOrdAsc = new ArrayList(listLocais);
		Collections.sort(qtdeOrdAsc,
				new Comparator(){
	        		public int compare(Object o1, Object o2) {
	        			AcompRealFisicoLocalArfl a1 = (AcompRealFisicoLocalArfl) o1;
	        			AcompRealFisicoLocalArfl a2 = (AcompRealFisicoLocalArfl) o2;
	        			return a1.getQuantidadeArfl().compareTo(a2.getQuantidadeArfl());
	        		}
				}
			);
		List qtdeOrdDesc = new ArrayList(qtdeOrdAsc);
		Collections.reverse(qtdeOrdDesc);
		
					
		//Iterator it = listLocais.iterator();
		Iterator it = null;
		
		//Locais Asc
		it = locaisOrdAsc.iterator();
		double total = 0;
		String corSimCorNao = "";
		
		String qtdeWidth = "20%";
		%>
		<table id="tbLocaisAsc" width="100%" border="0" cellpadding="0" cellspacing="0">
				<tr class="linha_subtitulo">
					<td align="center">
						<a href="#" onclick="javascript:mostrarLocaisDesc()" title="Local">Local</a>
					</td>
					<td width="<%=qtdeWidth%>" align="center">
						<a href="#" onclick="javascript:mostrarQtdeAsc()" title="<%=qtdeOuValor%>"><%=qtdeOuValor%></a>
					</td>
				</tr>
			<%
			while(it.hasNext()){
				AcompRealFisicoLocalArfl arfl = (AcompRealFisicoLocalArfl) it.next();
			%>
				<tr class="linha_subtitulo2_sem_bg" <%=corSimCorNao%>>
					<td align="left">
						<%=arfl.getLocalItemLit().getIdentificacaoLit()%>
					</td>
					<td align="right">
						<%
						if ("V".equalsIgnoreCase(indQtdeOuValor)) {
							out.println(Pagina.trocaNullMoeda(arfl.getQuantidadeArfl()));
						}
						else {
							out.println(Pagina.trocaNullNumeroSemDecimal(arfl.getQuantidadeArfl()));
						}
						
						total += arfl.getQuantidadeArfl().doubleValue();
						%>
					</td>
				</tr>
			<%
				if(!"".equals(corSimCorNao))
					corSimCorNao = "";
				else
					corSimCorNao = "bgcolor='#EAEEF4'";
			}
			%>
			<tr class="linha_subtitulo">
				<td align="right" class="label">
					Total
				</td>
				<td align="right">
					<%
					if ("V".equalsIgnoreCase(indQtdeOuValor)) {
						out.println(Pagina.trocaNullMoeda(Double.valueOf(total)));
					}
					else {
						out.println(Pagina.trocaNullNumeroSemDecimal(Double.valueOf(total)));
					}
					%>

				</td>
			</tr>
		</table>
		<%
	
		//Locais Desc
		it = locaisOrdDesc.iterator();
		total = 0;
		corSimCorNao = "";
		%>
		<table id="tbLocaisDesc" width="100%" border="0" cellpadding="0" cellspacing="0" style="display:none">
				<tr class="linha_subtitulo">
					<td align="center">
						<a href="#" onclick="javascript:mostrarLocaisAsc()" title="Local">Local</a>
					</td>
					<td width="<%=qtdeWidth%>" align="center">
						<a href="#" onclick="javascript:mostrarQtdeAsc()" title="<%=qtdeOuValor%>"><%=qtdeOuValor%></a>
					</td>
				</tr>
			<%
			while(it.hasNext()){
				AcompRealFisicoLocalArfl arfl = (AcompRealFisicoLocalArfl) it.next();
			%>
				<tr class="linha_subtitulo2_sem_bg" <%=corSimCorNao%>>
					<td align="left">
						<%=arfl.getLocalItemLit().getIdentificacaoLit()%>
					</td>
					<td align="right">
						<%
						if ("V".equalsIgnoreCase(indQtdeOuValor)) {
							out.println(Pagina.trocaNullMoeda(arfl.getQuantidadeArfl()));
						}
						else {
							out.println(Pagina.trocaNullNumeroSemDecimal(arfl.getQuantidadeArfl()));
						}

						total += arfl.getQuantidadeArfl().doubleValue();
						%>
					</td>
				</tr>
			<%
				if(!"".equals(corSimCorNao))
					corSimCorNao = "";
				else
					corSimCorNao = "bgcolor='#EAEEF4'";
			
			}
			%>
			<tr class="linha_subtitulo">
				<td align="right" class="label">
					Total
				</td>
				<td align="right">
					<%
					if ("V".equalsIgnoreCase(indQtdeOuValor)) {
						out.println(Pagina.trocaNullMoeda(Double.valueOf(total)));
					}
					else {
						out.println(Pagina.trocaNullNumeroSemDecimal(Double.valueOf(total)));
					}
					%>

				</td>
			</tr>
		</table>
		
		<%
		//Qtde Asc
		it = qtdeOrdAsc.iterator();
		total = 0;
		corSimCorNao = "";
		%>
		<table id="tbQtdeAsc" width="100%" border="0" cellpadding="0" cellspacing="0" style="display:none">
				<tr class="linha_subtitulo">
					<td align="center">
						<a href="#" onclick="javascript:mostrarLocaisAsc()" title="Local">Local</a>
					</td>
					<td width="<%=qtdeWidth%>" align="center">
						<a href="#" onclick="javascript:mostrarQtdeDesc()" title="<%=qtdeOuValor%>"><%=qtdeOuValor%></a>
					</td>
				</tr>
			<%
			while(it.hasNext()){
				AcompRealFisicoLocalArfl arfl = (AcompRealFisicoLocalArfl) it.next();
			%>
				<tr class="linha_subtitulo2_sem_bg" <%=corSimCorNao%>>
					<td align="left">
						<%=arfl.getLocalItemLit().getIdentificacaoLit()%>
					</td>
					<td align="right">
						<%
						if ("V".equalsIgnoreCase(indQtdeOuValor)) {
							out.println(Pagina.trocaNullMoeda(arfl.getQuantidadeArfl()));
						}
						else {
							out.println(Pagina.trocaNullNumeroSemDecimal(arfl.getQuantidadeArfl()));
						}
						total += arfl.getQuantidadeArfl().doubleValue();
						%>
					</td>
				</tr>
			<%
				if(!"".equals(corSimCorNao))
					corSimCorNao = "";
				else
					corSimCorNao = "bgcolor='#EAEEF4'";
			
			}
			%>
			<tr class="linha_subtitulo">
				<td align="right" class="label">
					Total
				</td>
				<td align="right">
				<%
					if ("V".equalsIgnoreCase(indQtdeOuValor)) {
						out.println(Pagina.trocaNullMoeda(Double.valueOf(total)));
					}
					else {
						out.println(Pagina.trocaNullNumeroSemDecimal(Double.valueOf(total)));
					}
				%>
				</td>
			</tr>
		</table>
	<%

		//Qtde Desc
		it = qtdeOrdDesc.iterator();
		total = 0;
		corSimCorNao = "";
		%>
		<table id="tbQtdeDesc" width="100%" border="0" cellpadding="0" cellspacing="0" style="display:none">
				<tr class="linha_subtitulo">
					<td align="center">
						<a href="#" onclick="javascript:mostrarLocaisAsc()" title="Local">Local</a>
					</td>
					<td width="<%=qtdeWidth%>" align="center">
						<a href="#" onclick="javascript:mostrarQtdeAsc()" title="<%=qtdeOuValor%>"><%=qtdeOuValor%></a>
					</td>
				</tr>
			<%
			while(it.hasNext()){
				AcompRealFisicoLocalArfl arfl = (AcompRealFisicoLocalArfl) it.next();
			%>
				<tr class="linha_subtitulo2_sem_bg" <%=corSimCorNao%>>
					<td align="left">
						<%=arfl.getLocalItemLit().getIdentificacaoLit()%>
					</td>
					<td align="right">
						<%
						if ("V".equalsIgnoreCase(indQtdeOuValor)) {
							out.println(Pagina.trocaNullMoeda(arfl.getQuantidadeArfl()));
						}
						else {
							out.println(Pagina.trocaNullNumeroSemDecimal(arfl.getQuantidadeArfl()));
						}
						total += arfl.getQuantidadeArfl().doubleValue();
						%>
					</td>
				</tr>
			<%
				if(!"".equals(corSimCorNao))
					corSimCorNao = "";
				else
					corSimCorNao = "bgcolor='#EAEEF4'";
			
			}
			%>
			<tr class="linha_subtitulo">
				<td align="right" class="label">
					Total
				</td>
				<td align="right">
					<%
					if ("V".equalsIgnoreCase(indQtdeOuValor)) {
						out.println(Pagina.trocaNullMoeda(Double.valueOf(total)));
					}
					else {
						out.println(Pagina.trocaNullNumeroSemDecimal(Double.valueOf(total)));
					}
					%>
				</td>
			</tr>
		</table>
	<%
	}
	%>
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