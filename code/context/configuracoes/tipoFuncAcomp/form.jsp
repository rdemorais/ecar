<%@ page import="java.util.List" %>
<%@ page import="java.util.Set"%>
<%@ page import="java.util.HashSet"%>
<%@ page import="java.util.ArrayList" %>
<%@ page import="ecar.pojo.ConfigMailCfgm" %>
<%@ page import="ecar.dao.ConfigMailCfgmDAO" %>
<%@ page import="ecar.dao.ConfiguracaoDao" %>
<%@ page import="java.util.Iterator" %>
<%@ page import="ecar.pojo.TfuncacompConfigmailTfacfgm" %>
<%@ page import="ecar.pojo.TfuncacompConfigmailTfacfgmPK" %>
<%@ page import="ecar.dao.TfuncacompConfigmailTfacfgmDAO" %>
<%@ page import="ecar.util.Dominios"%>


<%
/*
 * arquivo comum para as telas de inclusao, alteracao, exclusao e pesquisa 
 * observar a variavel _disbled e setar o objeto corretamente pelo construtor
 * assinalando os campos com os valores iniciais para inclusao
 */
%>

<%@page import="java.util.HashSet"%>
<%@page import="ecar.pojo.ConfiguracaoCfg"%><input type="hidden" name="indEmitePosicaoMonitorado"
	id="indEmitePosicaoMonitorado"
	value="<%=Pagina.trocaNull(tipoFuncAcomp.getIndAtivarMonitoramento())%>">
<table class="form">
	<util:msgObrigatorio obrigatorio="<%=_obrigatorio%>" />
	<tr>
		<td class="label"><%=_obrigatorio%>
			Nome
		</td>
		<td>
			<input type="text" name="descricaoTpfa" size="35" maxlength="30"
				value="<%=Pagina.trocaNull(tipoFuncAcomp.getDescricaoTpfa())%>"
				<%=_disabled%>>
		</td>
	</tr>
	<tr>
		<td class="label"><%=_obrigatorio%>
			Label da Fun&ccedil;&atilde;o
		</td>
		<td>
			<input type="text" name="labelTpfa" size="42" maxlength="40"
				value="<%=Pagina.trocaNull(tipoFuncAcomp.getLabelTpfa())%>"
				<%=_disabled%> >
		</td>
	</tr>
		<tr>
		<td class="label">Label da Posi&ccedil;&atilde;o</td>
		<td>
			<input type="text" name="labelPosicaoTpfa" size="42" maxlength="40"
				value="<%=Pagina.trocaNull(tipoFuncAcomp.getLabelPosicaoTpfa())%>"
				<%=_disabled%>>
		</td>
	</tr>
	<tr>
		<td class="label"><%=_obrigatorio%> Tamanho da Imagem de Sinal</td>
		<td>
			<input type="text" name="tamanhoSinalTpfa" size="5" maxlength="3"
				value="<%=Pagina.trocaNull(tipoFuncAcomp.getTamanhoSinalTpfa())%>"
				<%=_disabled%>>
		</td>
	</tr>
	<tr>
		<td class="label">Fun&ccedil;&atilde;o superior</td>
		<td>
<%
			String selected = new String();
			
			if(tipoFuncAcomp.getTipoFuncAcompTpfa() != null){
				request.getSession().setAttribute("tipoFuncAcompTpfaPaiAnterior", tipoFuncAcomp.getTipoFuncAcompTpfa());
				selected = tipoFuncAcomp.getTipoFuncAcompTpfa().getCodTpfa().toString();
			}
%>
			<combo:ComboTag nome="tipoFuncAcompTpfaPai"
				objeto="ecar.pojo.TipoFuncAcompTpfa" label="descricaoTpfa"
				value="codTpfa" order="descricaoTpfa"
				objetosExcluidos="<%=funcoesFilho%>" scripts="<%=_disabled%>"
				selected="<%=selected%>" />
		</td>
	</tr>
<%
			if(tipoFuncAcomp.getTipoFuncAcompTpfas() != null && tipoFuncAcomp.getTipoFuncAcompTpfas().size() > 0){
				List filhosTPFA = new ArrayList();
				//filhosTPFA.addAll(tipoFuncAcomp.getTipoFuncAcompTpfasPermiteAlterarInferior());
				if(tipoFuncAcomp.getTipoFuncAcompTpfas() != null)
					filhosTPFA.addAll(tipoFuncAcomp.getTipoFuncAcompTpfas());
%>
	<tr>
		<td class="label">Pode alterar parecer de função filha</td>
	</tr>
	<tr>
		<td class="label">
			
		</td>
		<td>
			<combo:CheckListVariosNiveisTag nome="filhosTPFA"
				objeto="ecar.pojo.TipoFuncAcompTpfa" label="labelTpfa"
				value="codTpfa" order="descricaoTpfa"
				colecao="<%=filhosTPFA%>" objetoAlterado="<%=tipoFuncAcomp%>" scripts="<%=_disabled%>" />
		</td>

	</tr>
	<%
			request.getSession().setAttribute("listaInferiores", tipoFuncAcomp.getTipoFuncAcompTpfasPermiteAlterarInferior());
			}
			else{
				request.getSession().removeAttribute("listaInferiores");
			}
	%>
	</table>
<%if (blVisualizaDesc == true){ 
	//ConfiguracaoDao configuracaoDao = new ConfiguracaoDao(request);
	//ConfiguracaoCfg configuracao = (ConfiguracaoCfg)session.getAttribute("configuracao");
%>

<table class="form">
	<tr>
		<td class="label">
			Permissões
		</td>
		<td>
			
		</td>
	</tr>
	
	<tr>
		<td class="label">
			
		</td>
		<td>
		<table class="form">

			<td width="150px">Ler Item da Estrutura</td>
			<td>
				<input type="checkbox" class="form_check_radio" name="indLerItemEstrutura" <%=_pesqdisabled%>
					<%=Pagina.isBoxChecked(tipoFuncAcomp.getIndLerItemEstrutura(),Dominios.SIM) %> >
			</td>
		</tr>
		<tr>
			<td width="150px">Alterar Item da Estrutura</td>
			<td>
				<input type="checkbox" class="form_check_radio" name="indAlterarItemEstrutura" <%=_pesqdisabled%> 
					<%=Pagina.isBoxChecked(tipoFuncAcomp.getIndAlterarItemEstrutura(),Dominios.SIM) %> >
			</td>
		</tr>
		<tr>
			<td width="150px">Excluir Item da Estrutura</td>
			<td>
				<input type="checkbox" class="form_check_radio" name="indExcluirItemEstrutura" <%=_pesqdisabled%>
					<%=Pagina.isBoxChecked(tipoFuncAcomp.getIndExcluirItemEstrutura(),Dominios.SIM) %> >
			</td>
		</tr>
		<tr>
			<td width="150px">Visualizar Parecer</td>
			<td>
				<input type="checkbox" class="form_check_radio" name="indVisualizarParecer" <%=_pesqdisabled%>
					<%=Pagina.isBoxChecked(tipoFuncAcomp.getIndVisualizarParecer(),Dominios.SIM) %> >
			</td>
		</tr>
		<tr>
			<td width="150px">Bloquear Planejamento</td>
			<td>
				<input type="checkbox" class="form_check_radio" name="indBloquearPlanejamento" <%=_pesqdisabled%> 
					<%=Pagina.isBoxChecked(tipoFuncAcomp.getIndBloquearPlanejamento(),Dominios.SIM) %> >
			</td>
		</tr>
		<tr>
			<td width="150px">Desbloquear Planejamento</td>
			<td>
				<input type="checkbox" class="form_check_radio" name="indDesbloquearPlanejamento" <%=_pesqdisabled%> 
					<%=Pagina.isBoxChecked(tipoFuncAcomp.getIndDesbloquearPlanejamento(),Dominios.SIM) %> >
			</td>
		</tr>
		<tr>
			<td width="150px">Ativar <%=configuracao.getLabelMonitorado()%></td>
			<td>
				<input type="checkbox" class="form_check_radio" name="indAtivarMonitoramento" <%=_pesqdisabled%> 
					<%=Pagina.isBoxChecked(tipoFuncAcomp.getIndAtivarMonitoramento(),Dominios.SIM) %> >
			</td>
		</tr>
		<tr>
			<td width="150px">Desativar <%=configuracao.getLabelMonitorado()%></td>
			<td>
				<input type="checkbox" class="form_check_radio" name="indDesativarMonitoramento" <%=_pesqdisabled%>
					<%=Pagina.isBoxChecked(tipoFuncAcomp.getIndDesativarMonitoramento(),Dominios.SIM) %> >
			</td>
		</tr>
		<tr>
			<td width="150px">Informa Realizado Físico</td>
			<td>
				<input type="checkbox" class="form_check_radio" name="indInformaAndamentoTpfa" <%=_pesqdisabled%> 
					<%=Pagina.isBoxChecked(tipoFuncAcomp.getIndInformaAndamentoTpfa(),Dominios.SIM) %> >
			</td>
		</tr>
		<tr>
			<td width="150px">Emite Posi&ccedil;&atilde;o</td>
			<td>
				<!-- Comentado ref. Bug 5255 -->
				<!--			<input type="radio" class="form_check_radio" onclick="javascript:selectEmitePosicao('S');" name="indEmitePosicaoTpfa" value="S" <%=Pagina.isChecked(tipoFuncAcomp.getIndEmitePosicaoTpfa(),"S")%> <%=_disabled%>>Sim-->
				<!--			<input type="radio" class="form_check_radio" onclick="javascript:selectEmitePosicao('N');" name="indEmitePosicaoTpfa" value="N" <%=Pagina.isChecked(tipoFuncAcomp.getIndEmitePosicaoTpfa(),"N")%> <%=_disabled%>>N&atilde;o-->
				<input type="checkbox" class="form_check_radio" name="indEmitePosicaoTpfa" <%=_pesqdisabled%> 
					<%=Pagina.isBoxChecked(tipoFuncAcomp.getIndEmitePosicaoTpfa(),Dominios.SIM) %> >
			</td>
		</tr>
		<tr>
			<td width="150px">Atualiza Situa&ccedil;&atilde;o no Cadastro</td>
			<td>
				<input type="checkbox" class="form_check_radio" name="indAtualizaSituacaoCadastro" <%=_pesqdisabled%> 
					<%=Pagina.isBoxChecked(tipoFuncAcomp.getIndAtualizaSituacaoCadastro(),Dominios.SIM) %> >
			</td>
		</tr>		
		</table>
	</table>

	<table class="form">
        <tr>
            <td class="label">Documenta&ccedil;&atilde;o</td>
            <td>
               <script language="JavaScript" type="text/javascript">
               //Usage: initRTE(imagesPath, includesPath, cssFile, genXHTML)
				initRTE("<%=_pathEcar%>/images/rte/", "<%=_pathEcar%>", "", false);
               </script>
               
               <script language="JavaScript" type="text/javascript">
               //Usage: writeRichText(fieldname, html, width, height, buttons, readOnly)
               //writeRichText('rte', 'enter body text here', 400, 200, true, false);                          
               writeRichText('rte', "<%=Pagina.trocaNull(tipoFuncAcomp.getDocumentacaoTpfa()).replaceAll("\n", " ").replaceAll("\r", " ").replaceAll("</SCRIPT>","&lt;/SCRIPT&gt;").replaceAll("</script>","&lt;/script&gt;").replaceAll("</script -->","&lt;/script --&gt;").replaceAll("</SCRIPT -->","&lt;/SCRIPT --&gt;")%>", 400, 200, true,<%=strReadOnly%>);
               
               </script>       
               
               <input type="hidden" name="richText" id="richText" size="2000">
               <input type="hidden" name="documentacaoTpfa" size="2000">
            </td>
        </tr>
	<tr>
		<td class="label">
			&nbsp;
		</td>
	</tr>
</table>
<table class="form">
	<tr>
		<td class="label">
			Op&ccedil;&otilde;es de Envio de E-Mail
		</td>
		<td>
			&nbsp;
		</td>
	</tr>

	<tr>
		<td colspan="2">
			<table border="0" align="center" style="width: 40%; align: center;">
				<tr>
					<td width="80%">
						<strong>Evento</strong>
					</td>
					<td width="10%">
						<strong>E-Mail</strong>
					</td>
					<td width="10%">
						<strong>SMS</strong>
					</td>
				</tr>
<%
				String __disabled = "";
				
				TfuncacompConfigmailTfacfgmDAO tFuncAcompDAO = new TfuncacompConfigmailTfacfgmDAO();
				
				ConfigMailCfgmDAO configMailDao = new ConfigMailCfgmDAO(request);
				List listaConfigMail = configMailDao.listar();		

				Iterator it = listaConfigMail.iterator();
				
				while( it.hasNext() ) {
					ConfigMailCfgm configMail = (ConfigMailCfgm) it.next();
					
					// trata de habilitar ou desabilitar os campos
					if( "disabled".equals(_disabled) ) {
						__disabled = _disabled;
					} else if( configMail.getAtivoCfgm().equals(String.valueOf("N")) ) {
						__disabled = "disabled";
					} else {
						__disabled = "";
					}
					
					String _enviaMail = "";
					String _enviaSMS = "";
					
					if( tipoFuncAcomp != null )  {
					
						TfuncacompConfigmailTfacfgmPK tFuncAcompPK = new TfuncacompConfigmailTfacfgmPK();
						
						tFuncAcompPK.setCodCfgm(configMail.getCodCfgm());
						tFuncAcompPK.setCodTpfa(tipoFuncAcomp.getCodTpfa());
										
						TfuncacompConfigmailTfacfgm tFuncAcomp = tFuncAcompDAO.obter(tFuncAcompPK);
						
						if( tFuncAcomp != null ) {
							_enviaMail = ((Dominios.SIM).equals(tFuncAcomp.getEnviaMailTfacfgm()) ? "checked" : "");
							_enviaSMS  = ((Dominios.SIM).equals(tFuncAcomp.getEnviaSms()) ? "checked" : "");
						}
					} %>

				<tr>
					<td width="80%"><%=Pagina.trocaNull(configMail.getDescricaoCfgm())%></td>
					<td width="10%" align="center">
						<input type="checkbox" class="form_check_radio" name="chkMailCfgM"
							value="<%=Pagina.trocaNull(configMail.getCodCfgm())%>"
							<%=__disabled%> <%=_enviaMail%>>
					</td>
					<td width="10%" align="center">
						<input type="checkbox" class="form_check_radio" name="chkSMSCfgM"
							value="<%=Pagina.trocaNull(configMail.getCodCfgm())%>"
							<%=__disabled%> <%=_enviaSMS%>>
					</td>
				</tr>
				<%
				} %>
			</table>
		</td>
	</tr>
<%
}
%>
	<%@ include file="/include/estadoMenu.jsp"%>
</table>

<script>
	function selecionaFilhos(selecionado){
		//alert(selecionado.checked);
		//alert(document.forms[0].filhosTPFA.length);
		
		if(selecionado.checked){
			//alert(selecionado.value);
			if(selecionado.value.indexOf(',') != -1){
				selecionadoFilhos = selecionado.value.split(',');
				tpFASelecionado = selecionadoFilhos[0];
			} else{
				tpFASelecionado = selecionado.value;
			}
			//alert(selecionado.value);
			tam = document.forms[0].filhosTPFA.length;
			for(i = 0; i < tam; i++){
				if(!document.forms[0].filhosTPFA[i].checked){				
					niveis = document.forms[0].filhosTPFA[i].value.split(',');
					for(j = 0; j < niveis.length; j++){
						if(tpFASelecionado == niveis[j]){
							document.forms[0].filhosTPFA[i].checked=true;
							break;
						}
					}
				}
			}
			//alert(document.forms[0].filhosTPFA[i].checked);
			//alert(document.forms[0].filhosTPFA[i].value);
		}
	}

</script>
