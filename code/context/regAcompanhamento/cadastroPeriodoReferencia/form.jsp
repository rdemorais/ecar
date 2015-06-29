<%@ taglib uri="/WEB-INF/tlds/c.tld" prefix="c"%>

<%@ page import="ecar.dao.TipoAcompanhamentoDao"%>
<%@ page import="ecar.permissao.ValidaPermissao"%>
<%@ page import="ecar.pojo.TipoAcompanhamentoTa"%>
<%@ page import="ecar.util.Dominios"%>
<%@ page import="ecar.pojo.AcompReferenciaAref" %> 
<%@ page import="ecar.dao.AcompReferenciaDao" %> 
<%@ page import="ecar.pojo.AcompRefLimitesArl" %> 
<%@ page import="ecar.pojo.OrgaoOrg" %>
<%@ page import="ecar.pojo.TipoFuncAcompTpfa" %> 
<%@ page import="ecar.dao.TipoFuncAcompDao" %> 
<%@ page import="ecar.dao.OrgaoDao" %>
<%@ page import="ecar.dao.AbaDao" %>
<%@ page import="ecar.pojo.SisAtributoSatb"%>
<%@ page import="comum.util.Pagina"%>
<%@ page import="java.util.Iterator" %> 
<%@ page import="java.util.List" %> 
<%@ include file="/include/estadoMenu.jsp"%>
<%
	TipoAcompanhamentoDao taDao = new TipoAcompanhamentoDao();
	TipoFuncAcompDao tpfaDao    = new TipoFuncAcompDao();
	AcompReferenciaDao arefDao  = new AcompReferenciaDao(request);
	ValidaPermissao validaPermissao = new ValidaPermissao();
	
	AcompReferenciaAref aref = null;
	String codAref = Pagina.getParamStr(request, "codigo");
	
	if(!codAref.equals("null") && !"".equals(codAref))
		aref = (AcompReferenciaAref) arefDao.buscar(AcompReferenciaAref.class, Long.valueOf(codAref));
	
	if(aref == null || aref.getCodAref() == null) aref = new AcompReferenciaAref();
	
	TipoAcompanhamentoTa ta = null;
	String mesAref = "";
	String orgaoOrg = "";
	String mostrarItensOrgaoInformado = "";
	
	try{
		ta = aref.getTipoAcompanhamentoTa();
		mesAref = aref.getMesAref();
		
		if(aref.getIndInformacaoOrgaoAref() != null) {
			mostrarItensOrgaoInformado = aref.getIndInformacaoOrgaoAref();
		}
		
		if(aref.getOrgaoOrg() != null)
			orgaoOrg = aref.getOrgaoOrg().getCodOrg().toString();
		
	}
	catch(Exception e){
		Logger.getLogger(this.getClass()).error(e);
	}
	
	/* combo selecionado */
	String selected = Pagina.getParamStr(request, "tipoAcompanhamento");
	if (!"".equals(selected))
		ta = (TipoAcompanhamentoTa) taDao.buscar(TipoAcompanhamentoTa.class, Long.valueOf(selected));
		
		
		// controle devido a consulta de um AREF já gravado que ocorre quando:
		// volta para a lista através do voltar do browser e clicar em adicionar referência
	// if(aref == null || aref.getCodAref() != null) aref = new AcompReferenciaAref();
	
%>


<input type="hidden" name="hidAcao" value="">
<input type="hidden" name="codigo" value="<%=Pagina.trocaNull(aref.getCodAref())%>">
<input type="hidden" name="niveisPlanejamento" value="">

<input type="hidden" name="indItemSimMonitoradosAref" value=""> 
<input type="hidden" name="indItemNaoMonitoradosAref" value="">
	

<table class="form">
	<tr>
		<td colspan="2">
			<div class="camposobrigatorios">* Campos de preenchimento obrigat&oacute;rio</div>
		</td>
	</tr>
	<tr>
<%
			String script = "onchange=\"javascript:aoSelecionarClasseDeAcesso(form);\"" + alt_disabled;
%>	
		<td class="label">Tipo de Acompanhamento</td>
		<td><combo:ComboTag 
				nome="tipoAcompanhamento"
				objeto="ecar.pojo.TipoAcompanhamentoTa"
				label="descricaoTa"
				value="codTa"
				order="descricaoTa"
				colecao="<%=taDao.getTipoAcompanhamentoComAcesso(seguranca)%>"
				selected='<%=(ta!= null && ta.getCodTa() != null)?ta.getCodTa().toString():""%>'
				scripts="<%=script%>"  
			/>
		</td>
	</tr>
</table>

<%
	if(ta != null){
		String strniveisPlanejamento = "";
		if(ta.getSisAtributoSatbs() != null) {
			for(Iterator it = ta.getSisAtributoSatbs().iterator();it.hasNext();){
				SisAtributoSatb nivel = (SisAtributoSatb) it.next();
				strniveisPlanejamento += nivel.getCodSatb() + ":";
			}
		}
		
		boolean apenasOrgaosAtivos = false;
		
		if (inserirPeriodo){
			apenasOrgaosAtivos = true;
		}
		
		List secretarias = taDao.getOrgaosAcessoUsuarioPorTipoAcompanhamento(seguranca,ta, apenasOrgaosAtivos);
		script = " disabled ";
		
		//se for alterar o form
		if(!"".equals(_disabled)) {
			script = _disabled;
		} else {
			
			//se for para separar por orgao
			if(ta.getIndSepararOrgaoTa() != null 
				&& Dominios.SIM.equals(ta.getIndSepararOrgaoTa())) { 
				script = "";
			}
		}
		
		
		if(mostrarItensOrgaoInformado.equals("")) {
			mostrarItensOrgaoInformado = "S";
		}
		
	
		
%>		
<input type="hidden" name="niveisPlanejamento" value="<%=strniveisPlanejamento%>">
<input type="hidden" name="hidtipoAcompanhamento" value="<%=Pagina.trocaNull(ta.getCodTa())%>">

<table class="form">
	
	<%
	if((Dominios.SIM.equals(ta.getIndSepararOrgaoTa()) && !validaPermissao.permissaoAcessoReferenciaSeusOrgaos(ta, seguranca.getGruposAcesso()))){
			
	%>
	
	<tr>
	
		<td class="label">Gerar apenas para itens sem órgão informado</td>
		<td>
		<input type="checkbox" class="form_check_radio" name="checkItensOrgaoInformado" value="<%=Dominios.NAO %>" 
			<%=Pagina.isChecked(mostrarItensOrgaoInformado , Dominios.NAO)%> 
			<%=script%>  onclick="javascript:if(this.checked == true)document.form.orgaoOrg.disabled = true; else document.form.orgaoOrg.disabled = false;">
		</td>	
	</tr>
	
	<%}%>	

	<tr>	
		<td class="label">* Orgãos</td>
		<td> 
			<combo:ComboTag 
				nome="orgaoOrg" 
				objeto="ecar.pojo.OrgaoOrg" 
				label="descricaoOrg" 
				value="codOrg"
				order="descricaoOrg"
				colecao="<%=secretarias%>"
				scripts="<%=script%>"
				selected='<%=orgaoOrg%>'
				textoPadrao="Todos"
			>
			</combo:ComboTag>
		</td>
	</tr>
	<tr>
		<td class="label">* Referência</td>
		<td>
			<input type="text" name="nomeAref" value="<%=Pagina.trocaNull(aref.getNomeAref())%>" size="25" maxlength="20" <%=_disabled%>>
		</td>
	</tr>
	
	<tr>
		<td class="label">* Dia</td>
		<td>
			<input type="text" name="diaAref" value="<%=Pagina.trocaNull(aref.getDiaAref())%>" size="2" maxlength="2" <%=_disabled%>>
		</td>
	</tr>

	<tr>
		<td class="label">* M&ecirc;s</td>
		<td>
			<select name="mesAref">
				<option></option>
				<option value="01" <%=("01".equals(mesAref))?"selected":""%>>Janeiro</option>
				<option value="02" <%=("02".equals(mesAref))?"selected":""%>>Fevereiro</option>
				<option value="03" <%=("03".equals(mesAref))?"selected":""%>>Março</option>
				<option value="04" <%=("04".equals(mesAref))?"selected":""%>>Abril</option>
				<option value="05" <%=("05".equals(mesAref))?"selected":""%>>Maio</option>
				<option value="06" <%=("06".equals(mesAref))?"selected":""%>>Junho</option>
				<option value="07" <%=("07".equals(mesAref))?"selected":""%>>Julho</option>
				<option value="08" <%=("08".equals(mesAref))?"selected":""%>>Agosto</option>
				<option value="09" <%=("09".equals(mesAref))?"selected":""%>>Setembro</option>
				<option value="10" <%=("10".equals(mesAref))?"selected":""%>>Outubro</option>
				<option value="11" <%=("11".equals(mesAref))?"selected":""%>>Novembro</option>
				<option value="12" <%=("12".equals(mesAref))?"selected":""%>>Dezembro</option>
			</select>
		</td>
	</tr>
	<tr>
		<td class="label">* Ano</td>
		<td>
			<input type="text" name="anoAref" value="<%=Pagina.trocaNull(aref.getAnoAref())%>" size="7" maxlength="4" <%=_disabled%>>
		</td>
	</tr>
	<tr>
		<td class="label">* Início da elaboração da avaliação</td>
		<td>
			<input type="text" name="dataInicioAref" value="<%=Pagina.trocaNullData(aref.getDataInicioAref())%>" size="15" maxlength="10" onkeyup="mascaraData(event, this);" onblur="validaData(this, false,false,false);">
			<c:if test="<%=_disabled.equals("")%>">
				<img class="posicao" title="Selecione a data" src="../../images/icone_calendar.gif" onclick="open_calendar('data', document.forms[0].dataInicioAref, '')">
			</c:if>
		</td>
	</tr>
	<tr>
		<td class="label">* Prazo final para acompanhamento físico</td>
		<td>
			<input type="text" name="dataLimiteAcompFisicoAref" value="<%=Pagina.trocaNullData(aref.getDataLimiteAcompFisicoAref())%>" size="15" maxlength="10" onkeyup="mascaraData(event, this);" onblur="validaData(this, false,false,false);">		
			<c:if test="<%=_disabled.equals("")%>">
				<img class="posicao" title="Selecione a data" src="../../images/icone_calendar.gif" onclick="open_calendar('data', document.forms[0].dataLimiteAcompFisicoAref, '')">
			</c:if>
		</td>
	</tr>
</table>
	<script language="JavaScript">
	document.form.mesAref.value = "<%=Pagina.trocaNull(aref.getMesAref())%>";
	document.form.diaAref.value = "<%=Pagina.trocaNull(aref.getDiaAref())%>";
	
	
	function validaPrazoFinalPara(form){
		var dtInicial;
		var dtFinal;
		
		dtInicial = dataInversa(form.dataInicioAref.value);

<%
		List listtpfa = null;
		if(ta.getCodTa() != null){
			
			if(aref.getCodAref() != null)
				listtpfa = tpfaDao.getFuncAcompArlComAcesso(aref,ta);
			else
				listtpfa = tpfaDao.getFuncAcompComAcesso(ta);
				
			if(listtpfa != null && listtpfa.size() > 0){
				for(Iterator it = listtpfa.iterator(); it.hasNext();) {
					TipoFuncAcompTpfa tpfa = (TipoFuncAcompTpfa) it.next();
%>
		dtFinal = dataInversa(form.prazoFinalPara<%=tpfa.getCodTpfa()%>.value);
			
		if (!validaData(form.prazoFinalPara<%=tpfa.getCodTpfa()%>,false,true,true)){
			if(form.prazoFinalPara<%=tpfa.getCodTpfa()%>.value == ""){
				alert("<%=_msg.getMensagem("periodoReferencia.validacao.prazoFinalPara.obrigatorio")%> <%=tpfa.getLabelPosicaoTpfa()%>");
			} else {
				alert("<%=_msg.getMensagem("periodoReferencia.validacao.prazoFinalPara.invalido")%> <%=tpfa.getLabelPosicaoTpfa()%>");
			}
			return(false);
		}
				
		if (dtInicial > dtFinal){
			alert("<%=_msg.getMensagem("periodoReferencia.validacao.prazoFinalPara.dataFinalMenorQueDataInicial")%> <%=tpfa.getLabelPosicaoTpfa()%> deve ser maior ou igual ao Início da elaboração da avaliação");
			form.prazoFinalPara<%=tpfa.getCodTpfa()%>.focus();
			return(false);
		}
	<%
				}
			}
		}
	%>
		
		return(true);
	}
	</script>
<% 
		if(ta.getCodTa() != null){
			if(listtpfa != null && listtpfa.size() > 0 ){
		
%>	
<table class="form">
<%	
				for(Iterator it = listtpfa.iterator();it.hasNext();){
					TipoFuncAcompTpfa tpfa = (TipoFuncAcompTpfa) it.next();
					AcompRefLimitesArl arl = arefDao.getAcompRefLimitesByFuncaoAcomp(aref,tpfa);
					String dataLimite = "";
					if(arl != null)
						dataLimite = Pagina.trocaNullData(arl.getDataLimiteArl());
					if(aref.getCodAref() == null || !"".equals(dataLimite) || arl == null){
			%>
			<tr>
				<td class="label">* Prazo final para <%=tpfa.getLabelPosicaoTpfa()%></td>
				<td><input 
					type="text" 
					name="prazoFinalPara<%=tpfa.getCodTpfa()%>"
					value="<%=dataLimite%>""
					size="15" 
					maxlength="10"  
					onkeyup="mascaraData(event, this);" 
					onblur="validaData(this, false,false,false);"/>
					<c:if test="<%=_disabled.equals("")%>">
						<img class="posicao" title="Selecione a data" src="../../images/icone_calendar.gif" onclick="open_calendar('data', document.forms[0].prazoFinalPara<%=tpfa.getCodTpfa()%>, '')">
					</c:if>
				</td>
			</tr>
		<%	 
							}
						}
		%>		
</table>
<%
			}
		}
	}


   
%>