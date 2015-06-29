
<%@ include file="../login/validaAcesso.jsp"%>
<%@ include file="../frm_global.jsp"%>

<%@ taglib uri="/WEB-INF/ecar-util.tld" prefix="util" %>
<%@ taglib uri="/WEB-INF/ecar-combo.tld" prefix="combo" %>

<%@ page import="comum.util.Util" %>
<%@ page import="ecar.bean.AtributoEstruturaListagemItens"%>
<%@ page import="ecar.pojo.AcompReferenciaAref" %>
<%@ page import="ecar.dao.AcompReferenciaDao" %>
<%@ page import="ecar.pojo.ItemEstruturaIett" %>  
<%@ page import="ecar.dao.ItemEstruturaDao" %> 
<%@ page import="ecar.pojo.UsuarioUsu" %>
<%@ page import="ecar.dao.TipoFuncAcompDao" %> 
<%@ page import="ecar.pojo.AcompReferenciaItemAri" %> 
<%@ page import="ecar.dao.AcompReferenciaItemDao" %> 
<%@ page import="ecar.pojo.AcompRelatorioArel" %> 
<%@ page import="ecar.dao.CorDao" %>
<%@ page import="ecar.dao.OrgaoDao" %>
<%@ page import="ecar.pojo.OrgaoOrg" %>
<%@ page import="ecar.dao.SisAtributoDao" %>
<%@ page import="ecar.pojo.SisAtributoSatb" %>
<%@ page import="ecar.dao.ItemEstrutMarcadorDao" %>
<%@ page import="ecar.pojo.ItemEstrutMarcadorIettm" %>
<%@ page import="ecar.pojo.StatusRelatorioSrl" %>
<%@ page import="ecar.pojo.UsuarioAtributoUsua" %>
<%@ page import="ecar.dao.SisGrupoAtributoDao" %>
<%@ page import="ecar.dao.ConfiguracaoDao" %>
<%@ page import="ecar.dao.AbaDao" %>

<%@ page import="comum.util.Pagina" %>
<%@ page import="comum.util.Data" %>
<%@ page import="comum.database.Dao" %>

<%@ page import="java.util.Calendar" %> 
<%@ page import="java.util.Iterator" %> 
<%@ page import="java.util.Collection" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.HashSet" %>
<%@ page import="java.util.Set" %>
<%@ page import="java.util.Collections" %>
<%@ page import="java.util.Map" %>
<%@ page import="java.util.Date" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.HashMap" %>
<%@ page import="ecar.dao.EstruturaDao" %>
<%@ page import="ecar.dao.UsuarioDao" %>
<%@ page import="ecar.dao.TipoAcompanhamentoDao" %>
<%@ page import="ecar.pojo.TipoAcompanhamentoTa" %>
<%@ page import="ecar.pojo.EstruturaEtt" %>

<%
Date dataInicio = Data.getDataAtual();
%>
 
<html lang="pt-br">
<head> 
<%@ include file="../include/meta.jsp"%>
<%@ include file="/titulo.jsp"%>
<link rel="stylesheet" href="<%=_pathEcar%>/css/style_<%=configuracaoCfg.getEstilo().getNome()%>.css" type="text/css">
<script language="javascript" src="../js/menu_retratil.js"></script>
<script language="javascript" src="<%=_pathEcar%>/js/destaqueLinha.js"></script>

<script language="javascript">
function trocaAbaSemAri(link, codIettRelacao){
	document.form.itemDoNivelClicado.value = codIettRelacao;
	document.form.action = link;
	document.form.submit();
}
function trocaAba(link, codAri){
	document.forms[0].codAri.value = codAri;
	document.forms[0].action = link;
	document.forms[0].submit();
}
function trocaAbaImpressao(link){
	document.forms[0].action = link;
	document.forms[0].submit();
}

</script>
</head>
<%
try {
	request.setAttribute("exibirAguarde", "true");
%>
<%@ include file="../cabecalho.jsp"%> 
<%@ include file="../divmenu.jsp"%> 
<%
	TipoAcompanhamentoDao taDao = new TipoAcompanhamentoDao(request);

	String status = Pagina.getParamStr(request, "relatorio");
	String flag = "";
	int contador = 0;
	String codTipoAcompanhamento = Pagina.getParamStr(request, "codTipoAcompanhamento");

	// controle para mostrar como default o primeiro tipo de acompanhamento das abas selecionado
	if("".equals(codTipoAcompanhamento)) {
		List listTa = taDao.getTipoAcompanhamentoAtivosComAcompanhamentoBySeqApresentacao();
		
		if(!listTa.isEmpty()) {
			codTipoAcompanhamento = ((TipoAcompanhamentoTa)listTa.get(0)).getCodTa().toString();
		}
	}

	TipoAcompanhamentoTa tipoAcompanhamento = (TipoAcompanhamentoTa) taDao.buscar(TipoAcompanhamentoTa.class, Long.valueOf(codTipoAcompanhamento));
%>
<body> 
<div id="conteudo">
<%
	if("".equals(codTipoAcompanhamento)) {
%>
	<table width="100%" border="0" cellpadding="0" cellspacing="0">	
		<tr class="linha_subtitulo" align="left">
			<td>
				<%=_msg.getMensagem("tipoAcompanhamento.ativo.comAref.nenhumRegistro")%>
			</td>
		</tr>
	</table>

<%
	} else {
%>
	<util:barraLinkTipoAcompanhamentoTag
		codTipoAcompanhamentoSelecionado="<%=codTipoAcompanhamento%>"
		chamarPagina="posicaoGeral.jsp"
	/> 

	<util:barraLinksRelatorioAcompanhamento
		funcaoSelecionada="SITUACAO_INDICADORES"
		links="N"
		caminho="<%=_pathEcar + "/relAcompanhamento/"%>"
		codTipoAcompanhamento="<%=codTipoAcompanhamento%>"
		gruposUsuario="<%=seguranca.getGruposAcesso() %>" 
	/> 

	<br>

<form name="form" method="post">
	<input type="hidden" name="codTipoAcompanhamento" value="<%=codTipoAcompanhamento%>">
	<input type="hidden" name="codAri" value="">	
	<input type="hidden" name="itemDoNivelClicado" value="">	
	<input type="hidden" name="mesReferencia" value="<%=Pagina.getParamStr(request, "mesReferencia")%>">
	<input type="hidden" name="codigosItem" value="<%=Pagina.getParamStr(request, "codigosItem")%>">
	<input type="hidden" name="primeiroCodAri" value="">

	<table border="0" width="100%">

		<tr>
			<td valign="bottom" class="texto" align="left"> 
				
				<!-- COMBO DE PERÍODOS DE EXIBIÇÃO -->
				<b>Ciclo de Exibição:</b>
				<select name="periodo">
					<option value="">--SELECIONE--</option>				
					<option value="1">1 Ciclo</option>
					<option value="3" selected>3 Ciclos</option>
					<option value="6">6 Ciclos</option>
					<option value="12">12 Ciclos</option>
				</select>								
			</td>			
			<td align="right" valign="bottom" class="texto">
				Data do relatório: <b><%=Pagina.trocaNullData(Data.getDataAtual())%></b>
				<br><br>
				
				<b>Ciclo de referência: </b>
				<!-- COMBO DE PERÍODOS DE REFERÊNCIA -->
				<select name="mesReferencia">
					<option value="0" selected>--SELECIONE--</option>				
				</select>				
			</td>
		</tr>
	</table>
		
	<div id="subconteudo">
		<!-- 
		Ver com o Beier se vai ter Popup Gráfico.
		
		<a href="javascript:abrirPopUpGrafico(0)" title="Gráfico de Evolução das Posições"><img src="../images/relAcomp/icon_grafico.png" border="0"></a>			
		-->
		<table border="0" cellpadding="0" cellspacing="0" width="100%">		
			<tr>						
				<td class="espacador" colspan="10">
					<img src="../images/pixel.gif">
				</td>
			</tr>
			
			<!-- TÍTULO DA TABELA -->
			<tr class="linha_titulo">
		 		<td colspan="10">
			 	</td>						 						 	
			</tr>

			<tr>						
				<td class="espacador" colspan="10">
					<img src="../images/pixel.gif">
				</td>
			</tr>
			
			<!-- CABEÇALHO DA TABELA -->
			<tr class="linha_subtitulo">
				<td width="1%"></td>							
				<td width="50%" colspan="3">Acompanhamentos</td>
				<td width="2%"></td>
				<td width="2%"></td>
				<td width="10%" align="center" nowrap>&Oacute;rg&atilde;o<br><select><option>Todos</option> </select></td>
				<td align="center">Agosto/2006</td>
				<td align="center">Setembro/1006</td>
				<td align="center" id="selecionado" class="corSelecao">Outubro/1006</td>
			</tr>	

			<tr>
				<td class="espacador" colspan="10">
					<img src="../images/pixel.gif">
				</td>
			</tr>

			<tr class="cor_nao" onmouseover="javascript: destacaLinha(this,'over','')" onmouseout="javascript: destacaLinha(this,'out','cor_nao')" onClick="javascript: destacaLinha(this,'click','cor_nao')" >
				<td>
				</td>
				<td colspan="3">
					<table>
						<tr>
							<td nowrap>											
								&nbsp;
							</td>
							<td valign="top"><img src="../images/icon_bullet_seta.png"></td>
							<td title="Área de Investimento:Infra-Estrutura e Meio Ambiente">
								<span class="fontSituacaoIndicadores">
									Infra-Estrutura e Meio Ambiente 
								</span>
							</td>
						</tr>
					</table>
				</td>
				<td nowrap>
					&nbsp;
				</td>
				<td nowrap>
					<!-- 
					<img src="../images/relAcomp/icon_anotacoes.png"
						title="Inserir Anotação">&nbsp;
					-->
				</td>
				<td>
					&nbsp;
				</td>
				<td align="center">
					&nbsp;
				</td>
				<td align="center">
					&nbsp;
				</td>
				<td align="center">
					&nbsp;
				</td>
			</tr>

			<tr class="cor_sim" onmouseover="javascript:destacaLinha(this,'over','')" onmouseout="javascript: destacaLinha(this,'out','cor_sim')" onClick="javascript: destacaLinha(this,'click','cor_sim')">
				<td>
				</td>
				<td colspan="3">
					<table>
						<tr>
							<td nowrap>											
								&nbsp;
								<img src="<%=request.getContextPath()%>/images/pixel.gif" width="22" height="9">
							</td>
							<td valign="top"><img src="../images/icon_bullet_seta.png"></td>
							<td title="Programa:Resgate do Porto Público">
								<span class="fontSituacaoIndicadores">
									Resgate do Porto Público
								</span>
							</td>
						</tr>
					</table>
				</td>
				<td nowrap>
					&nbsp;
				</td>
				<td nowrap>
					<!-- 
					<img src="../images/relAcomp/icon_anotacoes.png"
						title="Inserir Anotação">&nbsp;
					-->
				</td>
				<td align="center">
					SETR
				</td>
				<td align="center">
					&nbsp;
				</td>
				<td align="center">
					&nbsp;
				</td>
				<td align="center">
					&nbsp;
				</td>
			</tr>
			<tr class="cor_nao" onmouseover="javascript: destacaLinha(this,'over','')" onmouseout="javascript: destacaLinha(this,'out','cor_nao')" onClick="javascript: destacaLinha(this,'click','cor_nao')">
				<td>
				</td>
				<td colspan="3">
					<table>
						<tr>
							<td nowrap>											
								&nbsp;
								<img src="<%=request.getContextPath()%>/images/pixel.gif" width="22" height="9">
								<img src="<%=request.getContextPath()%>/images/pixel.gif" width="22" height="9">
							</td>
							<td valign="top"><img src="../images/icon_bullet_seta.png"></td>
							<td title="Sub-Programa:Gerenciamento da Infra-Estrutura e das Operações Portuárias">
								<span class="fontSituacaoIndicadores">
									Gerenciamento da Infra-Estrutura e das Operações Portuárias
								</span>
							</td>
						</tr>
					</table>
				</td>
				<td nowrap>
					&nbsp;
				</td>
				<td nowrap>
					<!-- 
					<img src="../images/relAcomp/icon_anotacoes.png"
						title="Inserir Anotação">&nbsp;
					-->
				</td>
				<td align="center">
					SETR
				</td>
				<td align="center">
					&nbsp;
				</td>
				<td align="center">
					&nbsp;
				</td>
				<td align="center">
					&nbsp;
				</td>
			</tr>
	
			<tr class="cor_sim" onmouseover="javascript:destacaLinha(this,'over','')" onmouseout="javascript: destacaLinha(this,'out','cor_sim')" onClick="javascript: destacaLinha(this,'click','cor_sim')">
				<td>
				</td>
				<td colspan="3">
					<table>
						<tr>
							<td nowrap>											
								&nbsp;
								<img src="<%=request.getContextPath()%>/images/pixel.gif" width="22" height="9">
								<img src="<%=request.getContextPath()%>/images/pixel.gif" width="22" height="9">
								<img src="<%=request.getContextPath()%>/images/pixel.gif" width="22" height="9">
							</td>
							<td valign="top"><img src="../images/icon_bullet_seta.png"></td>
							<td title="Realizações:Construção da malha de esgoto sanitário">
								<span class="fontSituacaoIndicadores">
									Construção da malha de esgoto sanitário
								</span>
							</td>
						</tr>
					</table>
				</td>
				<td nowrap>
					<!-- 
					<img src="../images/relAcomp/icon_operacional.png"
						title="Estratégico">&nbsp;
					<img src="../images/relAcomp/icon_ppa.png"
						title="PPA">&nbsp;
					-->
				</td>
				<td nowrap>
					<!-- 
					<img src="../images/relAcomp/icon_anotacoes.png"
						title="Inserir Anotação">&nbsp;
					-->
				</td>
				<td align="center">
					APPA
				</td>
				<td align="center">
					&nbsp;
				</td>
				<td align="center">
					&nbsp;
				</td>
				<td align="center">
					&nbsp;
				</td>
			</tr>

			<tr class="cor_nao" onmouseover="javascript: destacaLinha(this,'over','')" onmouseout="javascript: destacaLinha(this,'out','cor_nao')" onClick="javascript: destacaLinha(this,'click','cor_nao')">
				<td>
				</td>
				<td colspan="3">
					<table>
						<tr>
							<td nowrap>											
								&nbsp;
								<img src="<%=request.getContextPath()%>/images/pixel.gif" width="22" height="9">
								<img src="<%=request.getContextPath()%>/images/pixel.gif" width="22" height="9">
								<img src="<%=request.getContextPath()%>/images/pixel.gif" width="22" height="9">
								<img src="<%=request.getContextPath()%>/images/pixel.gif" width="22" height="9">
							</td>
							<td valign="top">&nbsp;</td>
							<td title="Realizações:Construção da malha de esgoto sanitário">
								<span class="fontSituacaoIndicadoresNegrito">
									Tratamento de Esgoto
								</span>
							</td>
						</tr>
					</table>
				</td>
				<td nowrap>
					<!-- 
					<img src="../images/relAcomp/icon_operacional.png"
						title="Estratégico">&nbsp;
					<img src="../images/relAcomp/icon_ppa.png"
						title="PPA">&nbsp;
					-->
				</td>
				<td nowrap>
					<!-- 
					<img src="../images/relAcomp/icon_anotacoes.png"
						title="Inserir Anotação">&nbsp;
					-->
				</td>
				<td align="center">
					APPA
				</td>
				<td align="center">
					<img src="../images/indicadorVerde.png">
				</td>
				<td align="center">
					<img src="../images/indicadorAmarelo.png">
				</td>
				<td align="center">
					<img src="../images/indicadorAmarelo.png">
				</td>
			</tr>

			<tr class="cor_sim" onmouseover="javascript:destacaLinha(this,'over','')" onmouseout="javascript: destacaLinha(this,'out','cor_sim')" onClick="javascript: destacaLinha(this,'click','cor_sim')">
				<td>
				</td>
				<td colspan="3">
					<table>
						<tr>
							<td nowrap>											
								&nbsp;
								<img src="<%=request.getContextPath()%>/images/pixel.gif" width="22" height="9">
								<img src="<%=request.getContextPath()%>/images/pixel.gif" width="22" height="9">
								<img src="<%=request.getContextPath()%>/images/pixel.gif" width="22" height="9">
								<img src="<%=request.getContextPath()%>/images/pixel.gif" width="22" height="9">
							</td>
							<td valign="top">&nbsp;</td>
							<td title="Realizações:Construção da malha de esgoto sanitário">
								<span class="fontSituacaoIndicadoresNegrito">
									Rede de Esgoto
								</span>
							</td>
						</tr>
					</table>
				</td>
				<td nowrap>
					<!-- 
					<img src="../images/relAcomp/icon_operacional.png"
						title="Estratégico">&nbsp;
					<img src="../images/relAcomp/icon_ppa.png"
						title="PPA">&nbsp;
					-->
				</td>
				<td nowrap>
					<!-- 
					<img src="../images/relAcomp/icon_anotacoes.png"
						title="Inserir Anotação">&nbsp;
					-->
				</td>
				<td align="center">
					APPA
				</td>
				<td align="center">
					<img src="../images/indicadorVerde.png">
				</td>
				<td align="center">
					<img src="../images/indicadorAmarelo.png">
				</td>
				<td align="center">
					<img src="../images/indicadorAmarelo.png">
				</td>
			</tr>

			<tr class="cor_nao" onmouseover="javascript: destacaLinha(this,'over','')" onmouseout="javascript: destacaLinha(this,'out','cor_nao')" onClick="javascript: destacaLinha(this,'click','cor_nao')" >
				<td>
				</td>
				<td colspan="3">
					<table>
						<tr>
							<td nowrap>											
								&nbsp;
								<img src="<%=request.getContextPath()%>/images/pixel.gif" width="22" height="9">
								<img src="<%=request.getContextPath()%>/images/pixel.gif" width="22" height="9">
								<img src="<%=request.getContextPath()%>/images/pixel.gif" width="22" height="9">
							</td>
							<td valign="top"><img src="../images/icon_bullet_seta.png"></td>
							<td title="Realizações:Instalação de novas estruturas de comunicação">
								<span class="fontSituacaoIndicadores">
									Instalação de novas estruturas de comunicação
								</span>
							</td>
						</tr>
					</table>
				</td>
				<td nowrap>
					<!-- 
					<img src="../images/relAcomp/icon_estrategico.png"
						title="Estratégico">&nbsp;
					-->
				</td>
				<td nowrap>
					<!-- 
					<img src="../images/relAcomp/icon_anotacoes.png"
						title="Inserir Anotação">&nbsp;
					-->
				</td>
				<td align="center">
					APPA
				</td>
				<td align="center">
					&nbsp;
				</td>
				<td align="center">
					&nbsp;
				</td>
				<td align="center">
					&nbsp;
				</td>
			</tr>
		
			<tr class="cor_sim" onmouseover="javascript:destacaLinha(this,'over','')" onmouseout="javascript: destacaLinha(this,'out','cor_sim')" onClick="javascript: destacaLinha(this,'click','cor_sim')">
				<td>
				</td>
				<td colspan="3">
					<table>
						<tr>
							<td nowrap>											
								&nbsp;
								<img src="<%=request.getContextPath()%>/images/pixel.gif" width="22" height="9">
								<img src="<%=request.getContextPath()%>/images/pixel.gif" width="22" height="9">
								<img src="<%=request.getContextPath()%>/images/pixel.gif" width="22" height="9">
								<img src="<%=request.getContextPath()%>/images/pixel.gif" width="22" height="9">
							</td>
							<td valign="top">&nbsp;</td>
							<td title="Realizações:Construção da malha de esgoto sanitário">
								<span class="fontSituacaoIndicadoresNegrito">
									Investimento em Software
								</span>
							</td>
						</tr>
					</table>
				</td>
				<td nowrap>
					<!-- 
					<img src="../images/relAcomp/icon_operacional.png"
						title="Estratégico">&nbsp;
					<img src="../images/relAcomp/icon_ppa.png"
						title="PPA">&nbsp;
					-->
				</td>
				<td nowrap>
					<!-- 
					<img src="../images/relAcomp/icon_anotacoes.png"
						title="Inserir Anotação">&nbsp;
					-->
				</td>
				<td align="center">
					APPA
				</td>
				<td align="center">
					<img src="../images/indicadorVerde.png">
				</td>
				<td align="center">
					<img src="../images/indicadorVermelho.png">
				</td>
				<td align="center">
					<img src="../images/indicadorAmarelo.png">
				</td>
			</tr>

			<tr class="cor_nao" onmouseover="javascript: destacaLinha(this,'over','')" onmouseout="javascript: destacaLinha(this,'out','cor_nao')" onClick="javascript: destacaLinha(this,'click','cor_nao')">
				<td>
				</td>
				<td colspan="3">
					<table>
						<tr>
							<td nowrap>											
								&nbsp;
								<img src="<%=request.getContextPath()%>/images/pixel.gif" width="22" height="9">
								<img src="<%=request.getContextPath()%>/images/pixel.gif" width="22" height="9">
								<img src="<%=request.getContextPath()%>/images/pixel.gif" width="22" height="9">
								<img src="<%=request.getContextPath()%>/images/pixel.gif" width="22" height="9">
							</td>
							<td valign="top">&nbsp;</td>
							<td title="Realizações:Construção da malha de esgoto sanitário">
								<span class="fontSituacaoIndicadoresNegrito">
									Estações de Trabalho
								</span>
							</td>
						</tr>
					</table>
				</td>
				<td nowrap>
					<!-- 
					<img src="../images/relAcomp/icon_operacional.png"
						title="Estratégico">&nbsp;
					<img src="../images/relAcomp/icon_ppa.png"
						title="PPA">&nbsp;
					-->
				</td>
				<td nowrap>
					<!-- 
					<img src="../images/relAcomp/icon_anotacoes.png"
						title="Inserir Anotação">&nbsp;
					-->
				</td>
				<td align="center">
					APPA
				</td>
				<td align="center">
					<img src="../images/indicadorVerde.png">
				</td>
				<td align="center">
					<img src="../images/indicadorAmarelo.png">
				</td>
				<td align="center">
					<img src="../images/indicadorAmarelo.png">
				</td>
			</tr>


			<tr  class="linha_subtitulo2">
				<td colspan="10">
					&nbsp;
				</td>
			</tr>

		</table>
	</div>	
	
	
</form>
	<%
	}

} catch (ECARException e) {
	org.apache.log4j.Logger.getLogger(this.getClass()).error(e);
	Mensagem.alert(_jspx_page_context, e.getMessageKey());
} catch (Exception e){
%>
	<%@ include file="/excecao.jsp"%>
<%
}
%>
	
</div>

</body>
<%@ include file="../include/ocultarAguarde.jsp"%>
<%@ include file="../include/estadoMenu.jsp"%>
<%@ include file="../include/mensagemAlert.jsp" %>
</html>