<%@ include file="../login/validaAcesso.jsp"%>
<%@ include file="../frm_global.jsp"%>

<%@ taglib uri="/WEB-INF/ecar-util.tld" prefix="util" %>
<%@ taglib uri="/WEB-INF/ecar-combo.tld" prefix="combo" %>

<%@ page import="comum.util.Data" %>

<html lang="pt-br">
<head> 
<%@ include file="../include/meta.jsp"%>
<%@ include file="/titulo.jsp"%>
<link rel="stylesheet" href="<%=_pathEcar%>/css/style_<%=configuracaoCfg.getEstilo().getNome()%>.css" type="text/css">
<script language="javascript" src="../js/menu_retratil.js"></script>
<script language="javascript" src="<%=_pathEcar%>/js/destaqueLinha.js"></script>

<script language="javascript">
function pausar(form){
	if(form.pause.value == "S"){
		form.pause.value = "N";
		form.btPause.value = "Pausar [ || ]";
		refresh();
	}
	else {
		form.pause.value = "S";
		form.btPause.value = "Continue [ > ]";
	}
}

<%@ include file="tempoGrafico_temp.jsp"%>
function doLoad(){
	setTimeout("refresh()", <%=tempoTransicao%>);
}

function refresh(){
	if(document.form.pause.value == "N"){
		document.form.action = "situacaoIndicadores.jsp";
		document.form.submit();
	}
}
function proximo(form){
	form.action = "situacaoIndicadores.jsp";
	form.submit();
}
</script>
</head>
<%
try {
%>
<%@ include file="../cabecalho.jsp"%> 
<%@ include file="../divmenu.jsp"%> 
<body onload="javascript:doLoad()"> 
<div id="conteudo">
<%@ include file="/titulo_tela.jsp"%>
<form name="form" method="post">
	<input type="hidden" name="codAri" value="">	
	<input type="hidden" name="itemDoNivelClicado" value="">	
	<input type="hidden" name="pause" value="N">

	<input type="button" name="btPause" value="Pausar [ || ]" onclick="javascript:pausar(form)">
	<input type="button" name="btProximo" value="Pr�ximo [ >> ]" onclick="javascript:proximo(form)">
	<table border="0" width="100%">

		<tr>
			<td valign="bottom" class="texto" align="left"> 
				
				<!-- COMBO DE PER�ODOS DE EXIBI��O -->
				<!-- 
				<b>Ciclo de Exibi��o:</b>
				<select name="periodo">
					<option value="">--SELECIONE--</option>				
					<option value="1">1 Ciclo</option>
					<option value="3" selected>3 Ciclos</option>
					<option value="6">6 Ciclos</option>
					<option value="12">12 Ciclos</option>
				</select>								
				 -->
			</td>			
			<td align="right" valign="bottom" class="texto">
				Data do relat�rio: <b><%=Pagina.trocaNullData(Data.getDataAtual())%></b>
				<!-- 
				<br><br>
				
				<b>Ciclo de refer�ncia: </b>
				<!-- COMBO DE PER�ODOS DE REFER�NCIA -->
				<!-- 
				<select name="mesReferencia">
					<option value="0" selected>--SELECIONE--</option>				
				</select>				
				 -->
			</td>
		</tr>
	</table>
		
	<div id="subconteudo">
		<!-- 
		Ver com o Beier se vai ter Popup Gr�fico.
		
		<a href="javascript:abrirPopUpGrafico(0)" title="Gr�fico de Evolu��o das Posi��es"><img src="../images/relAcomp/icon_grafico.png" border="0"></a>			
		-->
		<table border="0" cellpadding="0" cellspacing="0" width="100%">		
			<tr>						
				<td class="espacador" colspan="10">
					<img src="../images/pixel.gif">
				</td>
			</tr>
			
			<!-- T�TULO DA TABELA -->
			<tr class="linha_titulo">
		 		<td colspan="10">
			 	</td>						 						 	
			</tr>

			<tr>						
				<td class="espacador" colspan="10">
					<img src="../images/pixel.gif">
				</td>
			</tr>
			
			<!-- CABE�ALHO DA TABELA -->
			<tr class="linha_subtitulo">
				<td width="1%"></td>							
				<td width="50%" colspan="3">Acompanhamentos</td>
				<td width="2%"></td>
				<td width="2%"></td>
				<td width="10%" align="center" nowrap>&Oacute;rg&atilde;o<!-- br><select><option>Todos</option> </select --></td>
				<td align="center">Agosto/2006</td>
				<td align="center">Setembro/1006</td>
				<td align="center" id="selecionado" class="corSelecao">Outubro/1006</td>
			</tr>	

			<tr>
				<td class="espacador" colspan="10">
					<img src="../images/pixel.gif">
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
							</td>
							<td valign="top"><img src="../images/icon_bullet_seta.png"></td>
							<td title="�rea de Investimento:Infra-Estrutura e Meio Ambiente">
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
						title="Inserir Anota��o">&nbsp;
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
							<td title="Programa:Resgate do Porto P�blico">
								<span class="fontSituacaoIndicadores">
									Resgate do Porto P�blico
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
						title="Inserir Anota��o">&nbsp;
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
							<td title="Sub-Programa:Gerenciamento da Infra-Estrutura e das Opera��es Portu�rias">
								<span class="fontSituacaoIndicadores">
									Gerenciamento da Infra-Estrutura e das Opera��es Portu�rias
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
						title="Inserir Anota��o">&nbsp;
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
							<td title="Realiza��es:Constru��o da malha de esgoto sanit�rio">
								<span class="fontSituacaoIndicadores">
									Constru��o da malha de esgoto sanit�rio
								</span>
							</td>
						</tr>
					</table>
				</td>
				<td nowrap>
					<!-- 
					<img src="../images/relAcomp/icon_operacional.png"
						title="Estrat�gico">&nbsp;
					<img src="../images/relAcomp/icon_ppa.png"
						title="PPA">&nbsp;
					-->
				</td>
				<td nowrap>
					<!-- 
					<img src="../images/relAcomp/icon_anotacoes.png"
						title="Inserir Anota��o">&nbsp;
					-->
				</td>
				<td align="center">
					APPA
				</td>
				<td align="center">
					<img src="../images/relAcomp/branco9.png">
				</td>
				<td align="center">
					<img src="../images/relAcomp/verde9.png">
				</td>
				<td align="center">
					<img src="../images/relAcomp/verde9.png">
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
							</td>
							<td valign="top"><img src="../images/icon_bullet_seta.png"></td>
							<td title="Realiza��es:Instala��o de novas estruturas de comunica��o">
								<span class="fontSituacaoIndicadores">
									Instala��o de novas estruturas de comunica��o
								</span>
							</td>
						</tr>
					</table>
				</td>
				<td nowrap>
					<!-- 
					<img src="../images/relAcomp/icon_estrategico.png"
						title="Estrat�gico">&nbsp;
					-->
				</td>
				<td nowrap>
					<!-- 
					<img src="../images/relAcomp/icon_anotacoes.png"
						title="Inserir Anota��o">&nbsp;
					-->
				</td>
				<td align="center">
					APPA
				</td>
				<td align="center">
					<img src="../images/relAcomp/branco9.png">
				</td>
				<td align="center">
					<img src="../images/relAcomp/branco9.png">
				</td>
				<td align="center">
					<img src="../images/relAcomp/vermelho9.png">
				</td>
			</tr>

			<tr id="cor_sim" class="linha_subtitulo2">
				<td colspan="10">
					&nbsp;
				</td>
			</tr>
		
		</table>
	</div>	
	
	
</form>
	<%
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