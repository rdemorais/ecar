<%@ page import="ecar.dao.ItemEstruturaRealizadoDao" %>
<%@ page import="comum.util.Util" %>
<%@ page import="comum.util.Pagina" %>
<%@ page import="ecar.dao.ConfigSisExecFinanDao" %>
<%@ page import="java.util.List" %>
<%@ page import="ecar.pojo.EfItemEstContaEfiec" %>
<%@ page import="ecar.dao.ConfigSisExecFinanCsefvDao" %>
<%@ page import="ecar.dao.ConfigExecFinanDao" %>
<%@ page import="java.util.Iterator" %>
<%@ page import="ecar.pojo.ConfigSisExecFinanCsefv" %>
<%@ page import="ecar.pojo.ConfigExecFinanCef" %>

<%@page import="ecar.dao.ConfiguracaoDao"%><jsp:directive.page import="java.util.ArrayList"/>



<% 
/*
 * arquivo comum para as telas de inclusão de vários orçamemtos 
  */ 
 
 		/*Para inclusão de valores passados anteriomente e não cadastrados devido alguma falha*/ 
		List realizados = (List) request.getSession().getAttribute("efierList");
		request.getSession().removeAttribute("efierList");
		String[][] valores = new String[6][10];
		String[] valoresEstruturaContabil = new String[10];
		
		/* Para estrutura Contábil */
		ConfigSisExecFinanCsefvDao versaoDao = new ConfigSisExecFinanCsefvDao(request);
		ConfigSisExecFinanCsefv versaoConfigSis = null; 
		List estruturasContabil = null; 
		
		if(realizados!=null) {
			Iterator iterator = realizados.iterator();
			int indiceLinha = 0;
			String versaoEscolhida = (String) request.getSession().getAttribute("sessaoEscolhida");
			request.getSession().removeAttribute("sessaoEscolhida");
			versaoConfigSis = (ConfigSisExecFinanCsefv) versaoDao.buscar(ConfigSisExecFinanCsefv.class, Long.valueOf(versaoEscolhida));
			estruturasContabil = new ConfigExecFinanDao(request).getConfigExecFinanByVersao(versaoConfigSis);
			
			while (iterator.hasNext()){
				//EfItemEstRealizadoEfier efier = null;
				efier = (EfItemEstRealizadoEfier) iterator.next();
				valores[0][indiceLinha] = Pagina.trocaNullNumeroDecimalSemMilhar(efier.getValor1Efier());
				valores[1][indiceLinha] = Pagina.trocaNullNumeroDecimalSemMilhar(efier.getValor2Efier());
				valores[2][indiceLinha] = Pagina.trocaNullNumeroDecimalSemMilhar(efier.getValor3Efier());
				valores[3][indiceLinha] = Pagina.trocaNullNumeroDecimalSemMilhar(efier.getValor4Efier());
				valores[4][indiceLinha] = Pagina.trocaNullNumeroDecimalSemMilhar(efier.getValor5Efier());
				valores[5][indiceLinha] = Pagina.trocaNullNumeroDecimalSemMilhar(efier.getValor6Efier());
				
				if ((efier.getContaSistemaOrcEfier()!=null) && (!efier.getContaSistemaOrcEfier().equals("")) ){
					
					valoresEstruturaContabil[indiceLinha] = efier.getContaSistemaOrcEfier();
//					valsFun[indiceLinha] = (efier.getContaSistemaOrcEfier().split(" ")[0]) ;
//					valsPa[indiceLinha]  = (efier.getContaSistemaOrcEfier().split(" ")[1]) ;
				}
				indiceLinha++;
		
			}
		}
%>

<input type="hidden" name="indManualEfier" value="S">
<input type="hidden" name="codEfier" value="<%=efier.getCodEfier()%>"> 
<%
	String codVersaoEscolhida = "";
	if(efier.getConfigSisExecFinanCsefv() != null && efier.getConfigSisExecFinanCsefv().getCodCsefv() != null){
		codVersaoEscolhida = efier.getConfigSisExecFinanCsefv().getCodCsefv().toString();
	}
	
	
	
	
%>
<input type="hidden" name="codVersaoEscolhida" value="<%=codVersaoEscolhida%>" />
<tr><td colspan="9"> <table >
<util:msgObrigatorio obrigatorio="<%=_obrigatorio%>" />
</table></td></tr>

<!-- tr><td colspan="4"> <table style="margin: 0px"-->


<tr> <!-- Cabecalho da tabela --> 
	<td class="label" style="text-align: left"><%=_obrigatorio%> Sistema</td>
	<td class="label" style="text-align: left;"> <%=_obrigatorio%> M&ecirc;s / Ano  </td>
	<td class="label" style="text-align: left;width:23%; "><%=_obrigatorio%> Conta</td>	
	<%
		String[] labels = new String[6];
		
		labels[0] = new ConfiguracaoDao(request).getConfiguracao().getFinanceiroDescValor1Cfg();
		labels[1] = new ConfiguracaoDao(request).getConfiguracao().getFinanceiroDescValor2Cfg();
		labels[2] = new ConfiguracaoDao(request).getConfiguracao().getFinanceiroDescValor3Cfg();
		labels[3] = new ConfiguracaoDao(request).getConfiguracao().getFinanceiroDescValor4Cfg();
		labels[4] = new ConfiguracaoDao(request).getConfiguracao().getFinanceiroDescValor5Cfg();
		labels[5] = new ConfiguracaoDao(request).getConfiguracao().getFinanceiroDescValor6Cfg();
		

		int indice = 1;
		for(int i = 0; i < 6; i++){
			if(realizadoDao.getVerificarMostrarValorByPosicaoCfg(i)){
				
	%>
		<td class="label" style="text-align:left;"> <%=labels[i]%></td>
		  <%}
		  indice++;
		} %>
	
</tr> <!-- fim cabecalho -->


<tr>
	<td style="text-align:left;vertical-align:bottom;">
<%
	String sistemaDisabled = _disabled;
	
	if(!pesquisa && alteracao){
		sistemaDisabled = "disabled";
	}
	
	ConfigSisExecFinanDao csefDao = new ConfigSisExecFinanDao(request);
	List sistemas = csefDao.getSistemasAtivos();
	String selecionado = Pagina.getParamStr(request, "filtroCodSistema");
	String nomeSelecionado = "";
	
	if(efier.getConfigSisExecFinanCsefv() != null && efier.getConfigSisExecFinanCsefv().getConfigSisExecFinanCsef() != null){
		selecionado = efier.getConfigSisExecFinanCsefv().getConfigSisExecFinanCsef().getCodCsef().toString();
		nomeSelecionado = efier.getConfigSisExecFinanCsefv().getConfigSisExecFinanCsef().getNomeCsef();
	}
	
	if("".equals(selecionado))
		selecionado = Pagina.getParamStr(request, "incSistema");

	String textoPadrao = "";
	
	String scripts = sistemaDisabled + " onchange=javascript:mostrarEsconderMult(form);";
	
	if(pesquisa)
		textoPadrao = "TODOS";
		
	if(!alteracao){
	%>
		<combo:ComboTag 
			textoPadrao="<%=textoPadrao%>"
			nome="configSisExecFinanCsef"
			objeto="ecar.pojo.ConfigSisExecFinanCsef" 
			label="nomeCsef" 
			value="codCsef" 
			order="nomeCsef"
			filters="indAtivoCsef"
			colecao="<%=sistemas%>"
			selected="<%=selecionado%>"
			scripts="<%=scripts%>"
		/>
	<%
	}
	if(alteracao){
		out.println(nomeSelecionado);
		out.println("<input type=\"hidden\" name=\"configSisExecFinanCsef\" value=\"" + selecionado + "\">");
	}
	%>
	</td> <!-- fim campo Sistema -->
	
		<td  style="text-align: left;vertical-align:bottom;">
	<%
		String mesReferenciaEfier = "";
		if(efier.getMesReferenciaEfier() != null)
			mesReferenciaEfier = (efier.getMesReferenciaEfier().longValue() < 10) ? "0" + String.valueOf(efier.getMesReferenciaEfier().longValue()) : String.valueOf(efier.getMesReferenciaEfier().longValue());
			
	if(!alteracao){
	%>
		<select name="mesReferenciaEfier" <%=_disabled%> onchange="javascript:mostrarEsconderMult(form)" <%=sistemaDisabled%>>
	<%
		if(pesquisa){
			out.println("<option value=\"\"></option>");
		}
	
		for(int i = 1; i <= 12; i++){
			String select = "";
			String mes = "";
			if(i < 10)
				mes += "0";
			mes = mes + i;
			if(mes.equals(mesReferenciaEfier))
				select = "selected";
			
			out.println("<option value=\"" + mes + "\" " + select + ">" + mes + "</option>");
		}
	%>
		</select>
		/	
		<input name="anoReferenciaEfier" value="<%=Pagina.trocaNull(efier.getAnoReferenciaEfier())%>" maxlength="4" size="5" <%=sistemaDisabled%> onblur="javascript:mostrarEsconderMult(form)">
	<%
	}
	if(alteracao){
		out.println(mesReferenciaEfier + "/" + Pagina.trocaNull(efier.getAnoReferenciaEfier()));
		out.println("<input type=\"hidden\" name=\"mesReferenciaEfier\" value=\"" + mesReferenciaEfier + "\">");
		out.println("<input type=\"hidden\" name=\"anoReferenciaEfier\" value=\"" + Pagina.trocaNull(efier.getAnoReferenciaEfier()) + "\">");
	}
	%>
	</td> <!-- fim de campo mês/ano -->
	<td colspan="<%=indice+1 %>"> </td>
</tr>



<%	
	int cont = 0;
	int j = 0;
	for(j = 1;j<=10;j++){ %>
  <tr>
	<td colspan="2"> </td>
	<td  style="text-align: left;vertical-align:bottom;"> <!-- Inicio Conta -->
<%
	ConfigExecFinanDao layoutDao = new ConfigExecFinanDao(request);
	
	//ConfigSisExecFinanCsefvDao versaoDao = new ConfigSisExecFinanCsefvDao(request);
	List versoes = versaoDao.getCsefvOrderBySistema();
	
	Iterator itVer = versoes.iterator();
	boolean desabilitarConta = false;
	if(!"".equals(_disabled)){
		desabilitarConta = true;
	}
	
	cont = 0;
	
	while(itVer.hasNext()){
		ConfigSisExecFinanCsefv versao = (ConfigSisExecFinanCsefv) itVer.next();
		String campos = layoutDao.geraHTMLLayoutConta(Pagina.trocaNull(efier.getContaSistemaOrcEfier()), versao, desabilitarConta, request);
		
		long mesGravado = 0;
		long anoGravado = 0;
		
		if(efier != null && efier.getConfigSisExecFinanCsefv() != null){
			if(efier.getConfigSisExecFinanCsefv() != null && efier.getConfigSisExecFinanCsefv().getMesVersaoCsefv() != null){
				mesGravado = efier.getConfigSisExecFinanCsefv().getMesVersaoCsefv().longValue();
			}
			if(efier.getConfigSisExecFinanCsefv() != null && efier.getConfigSisExecFinanCsefv().getAnoVersaoCsefv() != null){
				anoGravado = efier.getConfigSisExecFinanCsefv().getAnoVersaoCsefv().longValue();
			}
		}
		
		
		String display = "display:none";
		
		if(!"".equals(efier.getContaSistemaOrcEfier())){
			if(mesGravado == versao.getMesVersaoCsefv().longValue()
			&& anoGravado == versao.getAnoVersaoCsefv().longValue()){
				display = ""; //Exibe a conta!!!! já com o layout certo!!!
			}
		}
		
		if(!"".equals(campos)){
			cont++;
			String tokensVersao = versaoDao.getPeriodoByVersao(versao);
			
%>
		
			<input type="hidden" name="hidVer<%=cont%>" value="<%=tokensVersao%>">
	
			<span id="versao<%=j+"_"+cont%>" style="<%=display%>"> 
							<%=campos%>
			</span>
<%
		}
	}
%>
	</td> <!-- fim de campo Conta -->
	
	
	
 <% 	indice = 1;
		for(int i = 0; i < 6; i++){
			if(realizadoDao.getVerificarMostrarValorByPosicaoCfg(i)){

%>
		<td style="text-align:left;vertical-align:bottom;">
			<input type="text" name="valor<%=j+"_"+indice%>Efier" value="<%=(valores[i][j-1]!=null)?valores[i][j-1]:""%>" size="10" onblur="javascript:validaValorMult(this)">
		</td>
	<%
			}
			indice++;
		}
	}
	%> <!-- Fim das colunas de valores -->
</tr> <!-- fim 2ª linha -->
<input type="hidden" name="totalLayouts" value="<%=cont%>">
<tr>
	<!-- td colspan="2">&nbsp;</td-->
</tr>

<!-- /table></td></tr--> 
<script language="javascript" >
<% 
     if(realizados!=null){       
        if (estruturasContabil != null) {
            Iterator it = estruturasContabil.iterator();
            int contador=0;
            while(it.hasNext()){
                ConfigExecFinanCef estruturaContabil = (ConfigExecFinanCef) it.next();

				for (int indiceLinha=0; indiceLinha< 10; indiceLinha++) { %>
					document.form.<%="e" + estruturaContabil.getCodCef().toString() + String.valueOf(versaoConfigSis.getCodCsefv())%>[<%=indiceLinha%>].value = <%= (valoresEstruturaContabil[indiceLinha]!=null)? valoresEstruturaContabil[indiceLinha].split(" ")[contador]:"''"%>;
				<% }   
            	contador++;
            }
        } 
      } %>
</script>


<%@ include file="../../include/estadoMenu.jsp"%>


