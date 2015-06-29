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
<%
/*
 * arquivo comum para as telas de inclusao, alteracao, exclusao e pesquisa 
 * observar a variavel _disabled e setar o objeto corretamente pelo construtor
 * assinalando os campos com os valores iniciais para inclusao
 */ 
 

%>

<%@page import="ecar.pojo.ConfiguracaoCfg"%><input type="hidden" name="indManualEfier" value="S">
<input type="hidden" name="codEfier" value="<%=efier.getCodEfier()%>"> 
<%
	ConfiguracaoCfg configura = (ConfiguracaoCfg)session.getAttribute("configuracao");
	String codVersaoEscolhida = "";
	if(efier.getConfigSisExecFinanCsefv() != null && efier.getConfigSisExecFinanCsefv().getCodCsefv() != null){
		codVersaoEscolhida = efier.getConfigSisExecFinanCsefv().getCodCsefv().toString();
	}

%>
<input type="hidden" name="codVersaoEscolhida" value="<%=codVersaoEscolhida%>">

<util:msgObrigatorio obrigatorio="<%=_obrigatorio%>" />
<tr>
	<td class="label"><%=_obrigatorio%> Sistema</td>
	<td>
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
	
	String scripts = sistemaDisabled + " onchange=javascript:mostrarEsconder(form);";
	
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
	</td>
</tr>
<tr>
	<td class="label"><%=_obrigatorio%> Mês/Ano</td>
	<td>
	<%
		String mesReferenciaEfier = "";
		if(efier.getMesReferenciaEfier() != null)
			mesReferenciaEfier = (efier.getMesReferenciaEfier().longValue() < 10) ? "0" + String.valueOf(efier.getMesReferenciaEfier().longValue()) : String.valueOf(efier.getMesReferenciaEfier().longValue());
			
	if(!alteracao){
	%>
		<select name="mesReferenciaEfier" <%=_disabled%> onchange="javascript:mostrarEsconder(form)" <%=sistemaDisabled%>>
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
		<input name="anoReferenciaEfier" value="<%=Pagina.trocaNull(efier.getAnoReferenciaEfier())%>" maxlength="4" size="5" <%=sistemaDisabled%> onblur="javascript:mostrarEsconder(form)">
	<%
	}
	if(alteracao){
		out.println(mesReferenciaEfier + "/" + Pagina.trocaNull(efier.getAnoReferenciaEfier()));
		out.println("<input type=\"hidden\" name=\"mesReferenciaEfier\" value=\"" + mesReferenciaEfier + "\">");
		out.println("<input type=\"hidden\" name=\"anoReferenciaEfier\" value=\"" + Pagina.trocaNull(efier.getAnoReferenciaEfier()) + "\">");
	}
	%>
	</td>
</tr>
<tr>
	<td colspan="2">
<%
	ConfigExecFinanDao layoutDao = new ConfigExecFinanDao(request);
	
	ConfigSisExecFinanCsefvDao versaoDao = new ConfigSisExecFinanCsefvDao(request);
	List versoes = versaoDao.getCsefvOrderBySistema();
	
	Iterator itVer = versoes.iterator();
	boolean desabilitarConta = false;
	if(!"".equals(_disabled)){
		desabilitarConta = true;
	}
	
	int cont = 0;
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
			<table id="versao<%=cont%>" style="<%=display%>">
				<tr>
					<td class="label"><%=_obrigatorio%> Conta</td>
					<td>
						<%=campos%>
					</td>
				</tr>
			</table>
<%
		}
	}
%>
	<input type="hidden" name="totalLayouts" value="<%=cont%>">
	</td>
</tr>
<%
	if(!pesquisa){

		String[] labels = new String[6];
		String[] valores = new String[6];
		
		labels[0] = configura.getFinanceiroDescValor1Cfg();
		labels[1] = configura.getFinanceiroDescValor2Cfg();
		labels[2] = configura.getFinanceiroDescValor3Cfg();
		labels[3] = configura.getFinanceiroDescValor4Cfg();
		labels[4] = configura.getFinanceiroDescValor5Cfg();
		labels[5] = configura.getFinanceiroDescValor6Cfg();
		
		valores[0] = Pagina.trocaNullNumeroDecimalSemMilhar(efier.getValor1Efier());
		valores[1] = Pagina.trocaNullNumeroDecimalSemMilhar(efier.getValor2Efier());
		valores[2] = Pagina.trocaNullNumeroDecimalSemMilhar(efier.getValor3Efier());
		valores[3] = Pagina.trocaNullNumeroDecimalSemMilhar(efier.getValor4Efier());
		valores[4] = Pagina.trocaNullNumeroDecimalSemMilhar(efier.getValor5Efier());
		valores[5] = Pagina.trocaNullNumeroDecimalSemMilhar(efier.getValor6Efier());
		
		int indice = 1;
		for(int i = 0; i < 6; i++){
			if(realizadoDao.getVerificarMostrarValorByPosicaoCfg(i)){
	%>
	<tr>
		<td class="label"> <%=labels[i]%></td>
		<td>
			<input type="text" name="valor<%=indice%>Efier" value="<%=valores[i]%>" size="10" <%=_disabled%> onblur="javascript:validaValor(this)">
		</td>
	</tr>
	<%
			}
			indice++;
		}
	}
%>
<tr>
	<td colspan="2">&nbsp;</td>
</tr>
<%@ include file="../../include/estadoMenu.jsp"%>

