<%@ page import="ecar.dao.SituacaoDao"%>
<%@ page import="ecar.pojo.SituacaoSit"%>
<%@ page import="ecar.util.Dominios"%>
<%@ page import="ecar.pojo.SisAtributoSatb" %>
<%@ page import="ecar.pojo.TipoAcompanhamentoTa" %>
<%@ page import="ecar.pojo.TipoFuncAcompTpfa" %>
<%@ page import="ecar.pojo.TipoAcompFuncAcompTafc" %>
<%@ page import="ecar.dao.ConfiguracaoDao" %>
<%@ page import="ecar.dao.SisGrupoAtributoDao" %>
<%@ page import="ecar.dao.TipoAcompanhamentoDao" %>
<%@ page import="ecar.dao.TipoFuncAcompDao" %>
<%@ page import="ecar.dao.TipoAcompFuncAcompDao" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.Iterator" %>
<%@ page import="ecar.pojo.SisGrupoAtributoSga" %>
<jsp:directive.page import="ecar.dao.TipoAcompGrpAcessoDao"/>
<jsp:directive.page import="ecar.pojo.TipoAcompGrpAcesso"/>
<%@ include file="/include/estadoMenu.jsp"%>
<%
/*
 * arquivo comum para as telas de inclusao, alteracao, exclusao e pesquisa 
 * observar a variavel _disbled e setar o objeto corretamente pelo construtor
 * assinalando os campos com os valores iniciais para inclusao
 */ 
 
	SisGrupoAtributoDao sisGrupoAtributoDao = new SisGrupoAtributoDao(request);
	ConfiguracaoDao configuracaoDao         = new ConfiguracaoDao(request);
	SituacaoDao situacaoDao                 = new SituacaoDao(request);
	TipoAcompFuncAcompDao tafcDao           = new TipoAcompFuncAcompDao(request);
%>
<table class="form">
	<input type="hidden" name="codigo" value="<%=tipoAcomp.getCodTa()%>">

	<util:msgObrigatorio obrigatorio="<%=_obrigatorio%>" />
	<tr>
		<td class="label"><%=_obrigatorio%> Descrição</td>
		<td colspan="3" ><input type="text" name="descricaoTa" size="35" maxlength="30" value="<%=Pagina.trocaNull(tipoAcomp.getDescricaoTa())%>" <%=_disabled%>></td>
	</tr>
	
	<tr>
		<td class="label">Menor Nível para Visualizar Acompanhamento</td>
		<td colspan="3" >
		<combo:ComboTag 
						nome="estruturaEtt"
						objeto="ecar.pojo.EstruturaEtt" 
						label="nomeEtt,siglaEtt" 
						value="codEtt" 
						order="nomeEtt"
						selected="<%=(tipoAcomp.getEstruturaEtt() != null)?tipoAcomp.getEstruturaEtt().getCodEtt().toString():""%>"
						filters="indAtivoEtt=S"
						scripts="<%=_disabled%>"
		/>
		</td>
	</tr>

	<tr>
		<td class="label">Gerar acompanhamento a partir da estrutura</td>
		<td colspan="3" >
		<combo:ComboTag 
						nome="estruturaEttAcomp"
						objeto="ecar.pojo.EstruturaEtt" 
						label="nomeEtt,siglaEtt" 
						value="codEtt" 
						order="nomeEtt"
						selected="<%=(tipoAcomp.getEstruturaNivelGeracaoTa() != null)?tipoAcomp.getEstruturaNivelGeracaoTa().getCodEtt().toString():""%>"
						filters="indAtivoEtt=S"
						scripts="<%=_disabled%>"
		/>
		</td>
	</tr>
	<tr>
		<td class="label">Não Gerar Acompanhamento para</td>
		<td colspan="3" >
<%

		//List situacoes = situacaoDao.listar(SituacaoSit.class, null);
		List situacoes = situacaoDao.getSituacaoEmUsoPorEstrutura(null);
		
		if(situacoes.size() > 0) {
		
			Iterator itSituacoes = situacoes.iterator();
		
			while(itSituacoes.hasNext()){
				SituacaoSit situacaoSit = (SituacaoSit) itSituacoes.next();
				String checked = "";
					
				if (tipoAcomp.getSituacaoSits() != null && tipoAcomp.getSituacaoSits().size() > 0) {
					if (tipoAcomp.getSituacaoSits().contains(situacaoSit)){
						checked = "checked";
					}
				}
	%>
				
				<input type="checkbox" class="form_check_radio" name="situacao" value="<%=situacaoSit.getCodSit()%>" <%=checked%> <%=_disabled%>>
				<%=situacaoSit.getDescricaoSit()%><br>
	<%
			}
		}
%>

		</td>
	</tr>
	<tr>
		<td class="label">Exige Liberar Acompanhamento</td>
		<td colspan="3" >
<%
		if (ehPesquisa) {
%>
			<input type="radio" class="form_check_radio" name="indLiberarAcompTa" value="<%=Pagina.SIM%>">Sim
			<input type="radio" class="form_check_radio" name="indLiberarAcompTa" value="<%=Pagina.NAO%>">Não
			<input type="button" value="Limpar" onclick="javascript:limparRadioButtons(indLiberarAcompTa)"><br>
<%
		} else {
%>
			<input type="checkbox" class="form_check_radio" name="indLiberarAcompTa" value="<%=Pagina.SIM %>" 
				<%=Pagina.isChecked(tipoAcomp.getIndLiberarAcompTa(), Pagina.SIM)%> 
				<%=_pesqdisabled%>>
<% 
		}
%>
				
		</td>
	</tr>
	<tr>
		<td class="label">Exige Liberar Parecer</td>
		<td colspan="3" >
<%
		if (ehPesquisa) {
%>
			<input type="radio" class="form_check_radio" name="indLiberarParecerTa" value="<%=Pagina.SIM%>">Sim
			<input type="radio" class="form_check_radio" name="indLiberarParecerTa" value="<%=Pagina.NAO%>">Não
			<input type="button" value="Limpar" onclick="javascript:limparRadioButtons(indLiberarParecerTa)"><br>
<%
		} else if(ehIncluir){
%>
			<input type="checkbox" class="form_check_radio" name="indLiberarParecerTa" value="<%=Pagina.SIM %>"<%=_pesqdisabled%> checked>
<% 
		}else{
%>
			<input type="hidden" name="hidIndLiberarParecerTa" id="hidIndLiberarParecerTa" value="<%=tipoAcomp.getIndLiberarParecerTa() %>" >
			<input type="checkbox" class="form_check_radio" name="indLiberarParecerTa" value="<%=Pagina.SIM %>" 
				<%=Pagina.isChecked(tipoAcomp.getIndLiberarParecerTa(), Pagina.SIM)%> 
				<%=_pesqdisabled%>>
<%	
		}
%>
				
		</td>
	</tr>
	<tr>
		<td class="label"><%=_obrigatorio%> Inclui <%=configuracaoDao.getConfiguracao().getLabelMonitorado()%></td>
		<td colspan="3" >
<%
			if (ehPesquisa) {
%>
							
				<input type="radio" class="form_check_radio" name="indMonitoramentoTa" value="<%=Pagina.SIM%>">Sim
				<input type="radio" class="form_check_radio" name="indMonitoramentoTa" value="<%=Pagina.NAO%>">Não
				<input type="button" value="Limpar" onclick="javascript:limparRadioButtons(indMonitoramentoTa)"><br>
				
<%
			} else {
%>
				<input type="checkbox" class="form_check_radio" name="indMonitoramentoTa" value="<%=Pagina.SIM %>" 
					<%=Pagina.isChecked(tipoAcomp.getIndMonitoramentoTa(), Pagina.SIM)%> 
					<%=_pesqdisabled%>>
<% 
			}
%>
		</td>
	</tr>
	<tr>
		<td class="label"><%=_obrigatorio%> Inclui Não <%=configuracaoDao.getConfiguracao().getLabelMonitorado()%></td>
		<td colspan="3" >
			
<%
			if (ehPesquisa) {
%>
				<input type="radio" class="form_check_radio" name="indNaoMonitoramentoTa" value="<%=Pagina.SIM%>">Sim
				<input type="radio" class="form_check_radio" name="indNaoMonitoramentoTa" value="<%=Pagina.NAO%>">Não
				<input type="button" value="Limpar" onclick="javascript:limparRadioButtons(indNaoMonitoramentoTa)"><br>
<%
			} else {
%>
				<input type="checkbox" class="form_check_radio" name="indNaoMonitoramentoTa" value="<%=Pagina.SIM%>" 
				<%=Pagina.isChecked(tipoAcomp.getIndNaoMonitoramentoTa(), Pagina.SIM)%> 
				<%=_pesqdisabled%>>
<% 
			}
%>
		</td>
	</tr>
	<tr>
		<td class="label"><%=_obrigatorio%> Separar por <%=configuracaoDao.getConfiguracao().getLabelOrgao()%></td>
		<td colspan="3" >
		
<%
			if (ehPesquisa) {
%>
				<input type="radio" class="form_check_radio" name="indSepararOrgaoTa" value="<%=Pagina.SIM%>">Sim
				<input type="radio" class="form_check_radio" name="indSepararOrgaoTa" value="<%=Pagina.NAO%>">Não
				<input type="button" value="Limpar" onclick="javascript:limparRadioButtons(indSepararOrgaoTa)"><br>
<%
			} else {
				if ("disabled".equals(_disabledOrgao)){
					%>
					<input type="checkbox" class="form_check_radio" name="indSepararOrgaoTaTela" value="<%=tipoAcomp.getIndSepararOrgaoTa()%>"
					<%=Pagina.isChecked(tipoAcomp.getIndSepararOrgaoTa(), Pagina.SIM)%> 
					<%=_disabledOrgao%>>
					<input type="hidden" name="indSepararOrgaoTa" value="<%=tipoAcomp.getIndSepararOrgaoTa()%>">
					<%
				} else {
					%>
					<input type="checkbox" class="form_check_radio" name="indSepararOrgaoTa" value="<%=Pagina.SIM %>"
					<%=Pagina.isChecked(tipoAcomp.getIndSepararOrgaoTa(), Pagina.SIM)%> 
					<%=_disabledOrgao%>> 
					<%	
				}
			}
%>
		</td>
	</tr>
	<tr>
		<td class="label">Exibir Opção de Geração de Arquivos</td>
		<td colspan="3" >
<%
			if (ehPesquisa) {
%>
				<input type="radio" class="form_check_radio" name="indGerarArquivoTa" value="<%=Pagina.SIM%>">Sim
				<input type="radio" class="form_check_radio" name="indGerarArquivoTa" value="<%=Pagina.NAO%>">Não
				<input type="button" value="Limpar" onclick="javascript:limparRadioButtons(indGerarArquivoTa)"><br>
<%
			} else {
%>
				<input type="checkbox" class="form_check_radio" name="indGerarArquivoTa" value="<%=Pagina.SIM%>" 
				<%=Pagina.isChecked(tipoAcomp.getIndGerarArquivoTa(), Pagina.SIM)%> <%=_pesqdisabled%> >
<% 
			}
%>
		</td>
	</tr>
	<tr>
		<td class="label"><%=_obrigatorio%> Sequência de Apresentação</td>
		<td colspan="3" ><input type="text" name="seqApresentacaoTa" size="7" maxlength="5" value="<%=Pagina.trocaNull(tipoAcomp.getSeqApresentacaoTa())%>" <%=_disabled%>></td>
	</tr>
	<tr>
		<td class="label" valign="top"><%=configuracaoDao.getConfiguracao().getSisGrupoAtributoSgaByCodSgaGrAtrNvPlan().getDescricaoSga()%></td>
		<td  colspan="3" >
<%

		List niveisPlanejamento = sisGrupoAtributoDao.getAtributosOrdenados(configuracaoDao.getConfiguracao().getSisGrupoAtributoSgaByCodSgaGrAtrNvPlan());
		Iterator itNiveis = niveisPlanejamento.iterator();
%>		
		<input type="hidden" name="niveisAux" value="<%=niveisPlanejamento.size()%>">
<%		
		while(itNiveis.hasNext()){
			SisAtributoSatb nivel = (SisAtributoSatb) itNiveis.next();
			if(nivel.getIndAtivoSatb().equals(Dominios.SIM)) {
				String checked = "";
				
				if (tipoAcomp.getSisAtributoSatbs() != null && tipoAcomp.getSisAtributoSatbs().size() > 0) {
					if (tipoAcomp.getSisAtributoSatbs().contains(nivel)){
						checked = "checked";
					}
				}
				%>
				<img src="<%=_pathEcar%>/images/relAcomp/<%=nivel.getAtribInfCompSatb()%>" border='0'>
				<input type="checkbox" class="form_check_radio" name="nivel" value="<%=nivel.getCodSatb()%>" <%=checked%> <%=_disabled%>>
				<%=nivel.getDescricaoSatb()%><br>
				<%
			}
		}
%>
		</td>
	</tr>
	<tr>
		<td colspan="4">&nbsp;</td>
	</tr>  <%

    if( configuracaoDao.getConfiguracao().getSisGrupoAtributoSgaByCodSgaGrAtrMetasFisicas() != null ) { %>
	<tr>
		<td class="label" valign="top"><%=configuracaoDao.getConfiguracao().getSisGrupoAtributoSgaByCodSgaGrAtrMetasFisicas().getDescricaoSga()%></td>
		<td  colspan="3" >
<%

		List listAtribMetasFisicas = sisGrupoAtributoDao.getAtributosOrdenados(configuracaoDao.getConfiguracao().getSisGrupoAtributoSgaByCodSgaGrAtrMetasFisicas());
		Iterator itA = listAtribMetasFisicas.iterator();
%>		
		<input type="hidden" name="atribAux" value="<%=listAtribMetasFisicas.size()%>">
<%		
		while(itA.hasNext()){
			SisAtributoSatb nivel = (SisAtributoSatb) itA.next();
			if(nivel.getIndAtivoSatb().equals(Dominios.SIM)) {
				String checked = "";
				
				if (tipoAcomp.getSisAtributoSatbs() != null && tipoAcomp.getSisAtributoSatbs().size() > 0) {
					if (tipoAcomp.getSisAtributoSatbs().contains(nivel)){
						checked = "checked";
					}
				}
%>
				<input type="checkbox" class="form_check_radio" name="nivel" value="<%=nivel.getCodSatb()%>" <%=checked%> <%=_disabled%>>
				<%=nivel.getDescricaoSatb()%><br>
<%
			}
		}
%>
		</td>
	</tr> 
<%
    } 
%>
	<tr>
		<td colspan="4">&nbsp;</td>
	</tr>
	<tr>
		<td class="label">Função de Acompanhamento</td>
		<td colspan="3" >&nbsp;</td>
	</tr>
<%
	
//	Iterator itFuncAcomp = (new TipoFuncAcompDao(request)).getTipoFuncAcompEmitePosicao().iterator();
	Iterator itFuncAcomp = (new TipoFuncAcompDao(request)).getFuncAcompByLabel().iterator();
	
	String codTpfas = "";
	while (itFuncAcomp.hasNext()) {
		
		TipoFuncAcompTpfa funcao = (TipoFuncAcompTpfa) itFuncAcomp.next();
		
		codTpfas = codTpfas + funcao.getCodTpfa().toString() + "|";
		
		TipoAcompFuncAcompTafc tafc = new TipoAcompFuncAcompTafc();
		
		String checkedObrigatorio = "";
		String checkedOpcional = "";
		String checkedIgnorar = "checked";
		
		String checkedRegObrigatorio = "";
		String checkedRegOpcional = "";
		
		if (tipoAcomp.getCodTa() != null) {
			try {
				tafc = tafcDao.buscar(tipoAcomp.getCodTa(), funcao.getCodTpfa());
			} catch (Exception e) {
				tafc = null;
			}
			
			if (tafc != null) {
				checkedObrigatorio = Pagina.isChecked(tafc.getIndObrigatorio(), Pagina.SIM);
				checkedOpcional = Pagina.isChecked(tafc.getIndOpcional(), Pagina.SIM);
				if(!"".equals(checkedObrigatorio) || !"".equals(checkedOpcional))
					checkedIgnorar = "";
				
				checkedRegObrigatorio = Pagina.isChecked(tafc.getIndRegistroPosicaoTafc(), Dominios.OBRIGATORIO);
				checkedRegOpcional = Pagina.isChecked(tafc.getIndRegistroPosicaoTafc(), Dominios.OPCIONAL);
			}
		}
%>
	<tr>
		<td class="label" valign="top"><%=funcao.getLabelTpfa()%></td>
			<input type="hidden" name="funcaoAcomp<%=funcao.getCodTpfa()%>" value="<%=funcao.getLabelTpfa()%>">
		<td>
			<input onclick="form.indRegistroPosicao<%=funcao.getCodTpfa()%>[0].disabled=false; form.indRegistroPosicao<%=funcao.getCodTpfa()%>[1].disabled=false;" type="radio" class="form_check_radio" name="indObrigatorioOpcional<%=funcao.getCodTpfa()%>" 
				<%=checkedObrigatorio%> value="OBR" 
				<%=_disabled%>>
				Obrigatório<br>

			<input onclick="form.indRegistroPosicao<%=funcao.getCodTpfa()%>[0].disabled=false; form.indRegistroPosicao<%=funcao.getCodTpfa()%>[1].disabled=false;" type="radio" class="form_check_radio" name="indObrigatorioOpcional<%=funcao.getCodTpfa()%>" 
				<%=checkedOpcional%> value="OPC" 
				<%=_disabled%>>
				Opcional<br>

			<input onclick="form.indRegistroPosicao<%=funcao.getCodTpfa()%>[0].disabled=true; form.indRegistroPosicao<%=funcao.getCodTpfa()%>[1].disabled=true;" type="radio" class="form_check_radio" name="indObrigatorioOpcional<%=funcao.getCodTpfa()%>" 
				<%=checkedIgnorar%> value="IGN" 
				<%=_disabled%>>
			Ignorar<br>
		</td>
	</tr>
	<tr>
		<td>&nbsp;</td>
		<td valign="top">
			<table class="form">
			<tr>
				<td width="50%" align="right"><b>Registro de Posição:</b></td>
				<td width="50%">
			<%
			String disabIndRegPos = _disabled;
			if("".equals(disabIndRegPos) && !"".equals(checkedIgnorar)){
				disabIndRegPos = "disabled";
			}
			%>

			<input type="radio" class="form_check_radio" name="indRegistroPosicao<%=funcao.getCodTpfa()%>" 
				<%=checkedRegObrigatorio%> value="<%=Dominios.OBRIGATORIO%>" <%=disabIndRegPos%>>
				Obrigatório<br>


			<input type="radio" class="form_check_radio" name="indRegistroPosicao<%=funcao.getCodTpfa()%>" 
				<%=checkedRegOpcional%> value="<%=Dominios.OPCIONAL%>" <%=disabIndRegPos%> >
				Opcional<br>
				</td>
				<td width="40%">&nbsp;</td>
			</tr>
			</table>
		</td>
	</tr>
		
<%
	}
%>
	<tr>
		<td class="label"><%=_obrigatorio%> Ativo</td>
		<td colspan="3" >
			<util:comboSimNaoTag nome="indAtivoTa" valorSelecionado="<%=tipoAcomp.getIndAtivoTa()%>" scripts="<%=_disabled%>" opcaoBranco="<%=_comboSimNao%>"/>
		</td>
	</tr>
</table>
<input type="hidden" name="codTpfas" value="<%=codTpfas%>">
	