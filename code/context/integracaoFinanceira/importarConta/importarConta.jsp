<%@ page import="java.util.List" %>
<%@ page import="ecar.dao.EfImportOcorrenciasDao" %>
<%@ page import="java.util.Iterator" %>
<%@ page import="java.util.Date" %>
<%@ page import="comum.util.*" %>
<%@ page import="ecar.pojo.EfImportOcorrenciasEfio" %>
<%@ page import="ecar.pojo.ImportacaoImp"%>
<%@ page import="ecar.enumerator.TipoOcorrencia"%>
<%@ include file="../../login/validaAcesso.jsp"%>
<%@ include file="../../frm_global.jsp"%>

<%@ taglib uri="/WEB-INF/ecar-util.tld" prefix="util" %>
<%@ taglib uri="/WEB-INF/ecar-combo.tld" prefix="combo" %>

<%
	ImportacaoImp dadosImportacao = (ImportacaoImp) request.getSession().getAttribute("dadosImportacao");
	EfImportOcorrenciasDao ocorrenciasDao = new EfImportOcorrenciasDao(request);
	String mostrarOcorrenciaAnterior = Pagina.getParamStr(request, "mostrarOcorrenciaAnterior");
	String ocorrenciaAnterior = Pagina.getParamStr(request, "ocorrenciaAnterior");
	String caminhoArquivoGravado = Pagina.getParamStr(request, "caminhoArquivoGravado");
	String arquivoVerificado = (dadosImportacao != null? dadosImportacao.getNomeArquivoImp():"");
	Long registros01 = new Long("0");
	Long registrosValidos = new Long("0");

%>

<%!
public String recuperaNomeArquivo(String nomeArquivoImportado) {
	
	String nomeArquivo = nomeArquivoImportado;

    if(nomeArquivo.lastIndexOf("_") != -1) {
        nomeArquivo = nomeArquivo.substring(nomeArquivo.lastIndexOf("_") + 1); 
    }

    return nomeArquivo;
}
%>

<html lang="pt-br">
<head>
<%@ include file="../../include/meta.jsp"%>
<%@ include file="/titulo.jsp"%>
<link rel="stylesheet" href="<%=_pathEcar%>/css/style_<%=configuracaoCfg.getEstilo().getNome()%>.css" type="text/css">
<script language="javascript" src="../../js/menu_retratil.js"></script>
<script language="javascript" src="../../js/validacoes.js"></script>

<script language="javascript">

	function verificar_arq(){
		if (document.form.arquivoImportacao.value == ""){
			alert("Selecione um arquivo para verificação");
			return false;
		}
		document.form.hidAcao.value='verificar';
		document.form.action = "ctrl.jsp";
		document.form.submit();
	}
	function importar_arq(qtdeOcorrencias){
		if (qtdeOcorrencias > 0){
			if (confirm("Houveram erros na crítica do arquivo. Deseja assim mesmo realizar a importação das informações?")){
				document.form.hidAcao.value='importar';
				document.form.action = "ctrl.jsp";
				document.form.submit();
			}
		}
		else{
			if (confirm("Confirma importação das informações?")){
				document.form.hidAcao.value='importar';
				document.form.action = "ctrl.jsp";
				document.form.submit();
			}
		}
	}
	function imprimir_arq(){
		document.form.hidAcao.value='imprimir';
		document.form.target = "_blank";
		document.form.action = "ctrl.jsp";
		document.form.submit();
		document.form.target = "";
	}
	
	function reload(form){
		form.mostrarOcorrenciaAnterior.value = "S";
		form.hidAcao.value='mostrarAnterior';
		form.action = "ctrl.jsp";
		form.submit();
	}
	function novaVerificacao(form) {
		form.action = "ctrl.jsp";
		form.hidAcao.value='limpar';
		form.submit();
	}
	function selecionarTodos() {
		var objetos = document.getElementsByTagName("input");
		var booleano = document.getElementById("checkPai").checked;
		
		for(i=0; i<objetos.length; i++) {
			if(objetos[i].type == 'checkbox') {
				objetos[i].checked = booleano;
			}
		}
	}
	function criarContas(form) {
	
		var objetos = document.getElementsByTagName("input");
		var algumSelecionado = false;
		
		for(i=0; i<objetos.length; i++) {
			if(objetos[i].type == 'checkbox') {
				if(objetos[i].checked && objetos[i].name == 'contasInexistentes') {
					algumSelecionado = true
					break;
				}
			}
		}
		
		if(algumSelecionado) {
		
			if(confirm('Você tem certeza que deseja criar as contas selecionadas da lista no Sistema?')) {
		
				form.action = "ctrl.jsp";
				form.method = "post";
				form.hidAcao.value='criarContas';
				form.submit();
			}
		
		}
	

	}	
</script>

</head>

<!-- sempre colocar o foco inicial no primeiro campo -->
<body>
<%@ include file="../../cabecalho.jsp" %>
<%@ include file="../../divmenu.jsp"%>

<div id="conteudo">

<form name="form" method="post" enctype="multipart/form-data" action="ctrl.jsp">
	<input type="hidden" name="hidAcao" value="">
	<input type="hidden" name="exibeImprimir" value="N">
	<input type="hidden" name="hidRegistro" value="<%=Pagina.getParamInt(request, "hidRegistro")%>">
	<input type="hidden" name="mostrarOcorrenciaAnterior" value="">
	<input type="hidden" name="caminhoArquivoGravado" value="<%=caminhoArquivoGravado%>">
	
	<!-- TITULO -->
	<%@ include file="/titulo_tela.jsp"%>
	
		<table class="form">
			<tr class="linha_titulo"  align="left">
				<td colspan="2" align="right">
				Ocorr&ecirc;ncias anteriores: 
				<%
				List lOcorrencias = ocorrenciasDao.getOcorrenciasAnteriores();
				%>
				
				<select name="ocorrenciasAnteriores" onchange="javascript:reload(form)">
					<option value=""></option>
					<%
					Iterator itOc = lOcorrencias.iterator();
					while(itOc.hasNext()){
						Date oc = (Date) itOc.next();
						String select = "";
						if(ocorrenciaAnterior.equals(Data.parseDateHour(oc)))
							select = "selected";
						%>
						<option value="<%=Data.parseDateHour(oc)%>" <%=select%>><%=Data.parseDateHourMinuteSecond(oc)%></option>
						<%
					}
					%>
				</select>
				
				</td>
			</tr>
			<%
			String _mostrarImport = "S";
			if("S".equals(mostrarOcorrenciaAnterior) || "".equals(caminhoArquivoGravado)){
				_mostrarImport = "N";
			}
			
			List ocorrencias = new ArrayList();
			if(!"".equals(ocorrenciaAnterior)){ //Escolheu ocorrencia anterior no combo
				
				ocorrencias.addAll(ocorrenciasDao.getOcorrencias(Data.parseDateHourTimeStamp(ocorrenciaAnterior)));
				request.getSession().setAttribute("listaCriticas", ocorrencias);

				if(ocorrencias.size() > 0) {
					EfImportOcorrenciasEfio ocorrencia = (EfImportOcorrenciasEfio) ocorrencias.get(0);
					dadosImportacao = ocorrencia.getImportacaoImp();
				}
			}
			else { //ocorrencias da critica do arquivo
				ocorrencias = (List)request.getSession().getAttribute("listaCriticas");
				if(ocorrencias == null)
					ocorrencias = new ArrayList();
			}
			
			%>
			<tr>
				<td class="label">
					* Arquivo
				</td>
				<td>
					<input type="file" id="arquivoImportacao" name="arquivoImportacao">
					<input type="hidden" name="hidArquivoImportacao" value="">
				</td>
			</tr>
			<tr>
				<td></td>
				<td>
					<input type='button' name='verificarArquivo' value='Verificar Arquivo' onclick='javascript:verificar_arq();'>
				</td>
			</tr>
			<tr>
				<td>&nbsp;</td>
				<td>&nbsp;</td>
			</tr>
			<tr>
				<td class="label">
					&nbsp;
				</td>
				<td>
				<%
				if(!ocorrencias.isEmpty()){
				%>
					<input type="button" name="limpar" value="Limpar" onclick="novaVerificacao(form);">
					<input type="button" name="imprimir" value="Imprimir Ocorr&ecirc;ncias" onclick="javascript:imprimir_arq();">
				<%
				}
				
				if("S".equals(_mostrarImport)){
				%>
					<input type="button" name="importar" value="Importar" onclick="javascript:importar_arq(<%=ocorrencias.size()%>);">
				<%
				}
				%>
				</td>
			</tr>
			<tr>
				<td colspan=2>
					<table width="100%" border="0" cellpadding="0" cellspacing="0">	
						<tr>
							<td class="espacador" colspan="3"><img src="../../images/pixel.gif" height="2"></td>
						</tr>
						<%
						
						if(!"".equals(arquivoVerificado)){
						%>
						<tr class="label"  style="text-align: left;">
							<td colspan="3">Arquivo verificado: <%=recuperaNomeArquivo(arquivoVerificado)%></td>
						</tr>
						<%
						%>
						<tr class="label"  style="text-align: left;">
							<td colspan="3">Data Ocorr&ecirc;ncia: <%=(dadosImportacao != null? Data.parseDateHourMinuteSecond(dadosImportacao.getDataHoraImp()): "")%></td>
						</tr>								
						<%						
						} else if((!ocorrencias.isEmpty()) && (!"".equals(ocorrenciaAnterior))){

							EfImportOcorrenciasEfio itemOc = null;
							String nomeArquivo = "";
							Iterator itImp = ocorrencias.iterator();
							if(itImp.hasNext()) {
								itemOc = (EfImportOcorrenciasEfio) itImp.next();
								nomeArquivo = (itemOc.getImportacaoImp().getNomeArquivoImp() == null?"":itemOc.getImportacaoImp().getNomeArquivoImp());
								registros01 = new Long(itemOc.getImportacaoImp().getNumRegistrosValidosImp().intValue());
								registrosValidos = new Long(itemOc.getImportacaoImp().getNumRegistrosValidosImp().longValue() - registros01.longValue());					
							}
							registros01.longValue();
							%>
							<tr class="label"  style="text-align: left;">
								<td colspan="3">Arquivo importado: <%=recuperaNomeArquivo(nomeArquivo)%></td>
							</tr>
							<%
							%>
							<tr class="label"  style="text-align: left;">
								<td colspan="3">Data Ocorr&ecirc;ncia: <%=(dadosImportacao != null? Data.parseDateHourMinuteSecond(dadosImportacao.getDataHoraImp()): "")%></td>
							</tr>								
							<%
						}
						%>
						<%							
							Long registrosRejeitados = new Long(registros01.longValue() - registrosValidos.longValue());
						%>
						<tr class="label"  align="left">
							<td colspan="3">
								Registros (TR01):&nbsp; <%=(dadosImportacao != null? (dadosImportacao.getNumRegistrosInvalidosImp().intValue() + dadosImportacao.getNumRegistrosValidosImp().intValue()):0)%>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
								V&aacute;lidos:&nbsp; <%=(dadosImportacao != null? dadosImportacao.getNumRegistrosValidosImp().intValue(): 0)%>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
								Rejeitados:&nbsp; <%=(dadosImportacao != null?dadosImportacao.getNumRegistrosInvalidosImp().intValue():0)%>
							</td>
						</tr>
						
						<tr class="linha_titulo"  align="left">
							<td width="10%">
								<input type="button" value="Criar Contas" class="botao" onclick="javascript:criarContas(form);">
							</td>
							<td colspan="2">Descrição</td>
						</tr>
						<tr>
							<td class="espacador" colspan="3"><img src="../../images/pixel.gif" height="2"></td>
						</tr>
						<tr>
							<td align="center">
								<input type="checkbox" class="form_check_radio" onclick="javascript:selecionarTodos();" name="checkPai" id="checkPai">
							</td>
							<td>
							</td>
							<td>
							</td>							
						</tr>
						<%
						if(!ocorrencias.isEmpty()){
							Iterator it = ocorrencias.iterator();
							TipoOcorrencia tipoOcorrencia = new TipoOcorrencia(TipoOcorrencia.CONTA_INEXISTENTE);
							String checkbox = "";
							int contador = 0;
							while(it.hasNext()){
								contador++;
								EfImportOcorrenciasEfio itemOc = (EfImportOcorrenciasEfio) it.next();
								String desc = itemOc.getDescricaoEfio();

								if((contador % 2) == 0) {
						%><tr class="linha_subtitulo2_sem_bg"><%
								}else{
							%><tr class="linha_subtitulo2"><%														
								}%>
								<td align="center">
									<% 
									if(itemOc.getTipoOcorrencia() != null) {
										if(itemOc.getTipoOcorrencia().equals(tipoOcorrencia)) { %>
											<input type='checkbox' name='contasInexistentes' value='<%=itemOc.getConta()%>'>
										<%} %>
									<%} %>
								</td>

								<td><%=desc%></td>
							</tr>
						<%}%>
							<tr>
								<td>
									<input type="button" value="Criar Contas" class="botao" onclick="javascript:criarContas(form);">
								</td>
								<td></td>
								<td></td>
							</tr>
						<%
						} else if(!"".equals(caminhoArquivoGravado)){
						%>
							<tr class="linha_subtitulo" align="center">
								<td colspan="3">Não houveram erros na verifica&ccedil;&atilde;o do arquivo.</td>
							</tr>
						<%
						}
						else {
						%>
							<tr class="linha_subtitulo" align="center">
								<td colspan="3">Não existem registros para exibi&ccedil;&atilde;o.</td>
							</tr>
						<%
						}
						%>
						<tr>
							<td class="espacador" colspan="3"><img src="../../images/pixel.gif" height="2"></td>
						</tr>
					</table>		
				</td>
			</tr>
		</table>
				
	<center>
		
	</center>
</form>

</div>
</body>
<% /* Controla o estado do Menu (aberto ou fechado) */%>
<%@ include file="../../include/estadoMenu.jsp"%>
<%@ include file="../../include/mensagemAlert.jsp" %>
</html>