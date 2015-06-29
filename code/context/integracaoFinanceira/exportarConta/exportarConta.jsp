<%@ page import="ecar.dao.ConfigSisExecFinanDao" %>
<%@ page import="ecar.pojo.ConfigSisExecFinanCsef" %>
<%@ page import="java.util.List" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="java.util.Date" %>
<%@ include file="../../login/validaAcesso.jsp"%>
<%@ include file="../../frm_global.jsp"%>

<%@ taglib uri="/WEB-INF/ecar-util.tld" prefix="util" %>
<%@ taglib uri="/WEB-INF/ecar-combo.tld" prefix="combo" %>

<%
	ConfigSisExecFinanDao configSisExecFinanDao = new ConfigSisExecFinanDao(request);
%>


<%@page import="ecar.pojo.UsuarioUsu"%>
<%@page import="comum.util.Util"%><html lang="pt-br">
<head>
<%@ include file="../../include/meta.jsp"%>
<%@ include file="/titulo.jsp"%>
<link rel="stylesheet" href="<%=_pathEcar%>/css/style_<%=configuracaoCfg.getEstilo().getNome()%>.css" type="text/css">
<script language="javascript" src="../../js/menu_retratil.js"></script>
<script language="javascript" src="../../js/datas.js"></script>
<script language="javascript" src="../../js/validacoes.js"></script>
<script language="javascript">
function validarExportacao(form){
	if (form.nomeArquivoExp.value == ""){
		alert("<%=_msg.getMensagem("integracaoFinanceira.exportarArquivo.nomeArquivo.obrigatorio")%>");
		form.nomeArquivoExp.focus();
		return false;
	}
	else {
		normalizaNomeArquivo(form);
		if(!validarNomeArquivo(form.nomeArquivoExp.value)){
			return false;
		}
	}
	
	if (form.mesAnoIni.value == ""){
		alert("<%=_msg.getMensagem("integracaoFinanceira.exportarArquivo.mesAnoIni.obrigatorio")%>");
		form.mesAnoIni.focus();
		return false;
	}
	
	if (validarMesAno(form.mesAnoIni.value) == false){
		alert("<%=_msg.getMensagem("integracaoFinanceira.exportarArquivo.mesAnoIni.invalido")%>");
		form.mesAnoIni.focus();
		return false;
	}

	if (form.mesAnoFim.value == ""){
		alert("<%=_msg.getMensagem("integracaoFinanceira.exportarArquivo.mesAnoFim.obrigatorio")%>");
		form.mesAnoFim.focus();
		return false;
	}
	
	if (validarMesAno(form.mesAnoFim.value) == false){
		alert("<%=_msg.getMensagem("integracaoFinanceira.exportarArquivo.mesAnoFim.invalido")%>");
		form.mesAnoFim.focus();
		return false;
	}
	
	if (validarMesAnoIniMesAnoFim(form.mesAnoIni.value, form.mesAnoFim.value) == false){
		alert("<%=_msg.getMensagem("integracaoFinanceira.exportarArquivo.mesAnoIniMesAnoFim.invalido")%>");
		form.mesAnoFim.focus();
		return false;
	}
	
	if (form.configSisExecFinanCsef.value == ""){
		alert("<%=_msg.getMensagem("integracaoFinanceira.exportarArquivo.configSisExecFinanCsef.obrigatorio")%>");
		form.configSisExecFinanCsef.focus();
		return false;
	}
	return true;
}

	
function exportar_arq(form){
	if(validarExportacao(form) == true){
		form.hidAcao.value = "exportar";
		form.hidGerarArquivo.value = "S";
		form.action = "exportarConta.jsp";
		form.submit();
	}
}

function validarMesAno(mesAno){
	var x = mesAno.split("/");
	
	/* É necessário usar o segundo parâmetro (10) no parseInt() para converter para decimal, 
	pois quando o mes vem com o zero na frente do numero (01,02,03...) o sistema pensa que é um número 
	octal e para o números 08 ele converte zero. */
	
	var mes = parseInt(x[0],10);
	var ano = parseInt(x[1],10);

	if(mesAno.length < 7){
		return false;
	}
	if(mes < 1 || mes > 12){
		return false;
	}
	if(isNumeric(ano) == false){
		return false;
	}
	else{
		if(ano < 1950 || ano > 2150){
			return false;
		}
	}
	return true;
}

function validarMesAnoIniMesAnoFim(mesAnoIni, mesAnoFim){
	var xIni = mesAnoIni.split("/");
	var xFim = mesAnoFim.split("/");

	/* É necessário usar o segundo parâmetro (10) no parseInt() para converter para decimal, 
	pois quando o mes vem com o zero na frente do numero (01,02,03...) o sistema pensa que é um número 
	octal e para o números 08 ele converte zero. */

	var mesIni = parseInt(xIni[0],10);
	var anoIni = parseInt(xIni[1],10);
	var mesFim = parseInt(xFim[0],10);
	var anoFim = parseInt(xFim[1],10);
	
	if(anoIni == anoFim && mesIni > mesFim){
		return false;
	}
	else if(anoFim < anoIni){
		return false;
	}
	return true;
}

function normalizaNomeArquivo(form){
	var nome = form.nomeArquivoExp.value;
	var retorno = "";
	for(i = 0; i < nome.length; i++){
		if(nome[i] == " "){
			retorno = retorno + "_";
		}
		else {
			retorno = retorno + nome[i];
		}
	}
	form.nomeArquivoExp.value = retorno;
}

function validarNomeArquivo(nome){
	for(i = 0; i < nome.length; i++){
		if(nome[i] == "/"
			|| nome[i] == "?"
			|| nome[i] == "*"
			|| nome[i] == "+"
			|| nome[i] == "."
			|| nome[i] == "&"
			|| nome[i] == "!"){
			alert("O nome do arquivo não deve conter o caracter '" + nome[i] + "'");
			return false;
		}
	}
	return true;
}

function downloadArquivoLayout(form, nomeArquivo){
	form.target = "_blank";
	form.action="<%=request.getContextPath()%>/LayoutArquivoExportacao?nomeArquivo=" + nomeArquivo;
	form.submit();	
	form.target = "";
}
</script>

</head>

<!-- sempre colocar o foco inicial no primeiro campo -->
<body onload="javascript:onLoad(document.form);">
<%@ include file="../../cabecalho.jsp" %>
<%@ include file="../../divmenu.jsp"%>

<div id="conteudo">

<form name="form" method="post">
	<input type="hidden" name="hidAcao" value="">
	<input type="hidden" name="hidGerarArquivo" value="">
	
	<!-- TITULO -->
	<%@ include file="/titulo_tela.jsp"%>
	
		<table width="100%" border="0" cellpadding="0" cellspacing="0">	
			<tr>
				<td class="espacador" colspan="2"><img src="../../images/pixel.gif" height="2"></td>
			</tr>
			<tr class="linha_subtitulo">
				<td align="right">* Nome para o Arquivo (sem extensão)</td>
				<td><input type="text" name="nomeArquivoExp" value="<%=Pagina.getParamStr(request, "nomeArquivoExp")%>" size="30"></td>
			</tr>
			<tr class="linha_subtitulo">
				<td align="right">* Mês/Ano Inicial</td>
				<td><input type="text" name="mesAnoIni" value="<%=Pagina.getParamStr(request, "mesAnoIni")%>" size="7" maxlength="7" onkeypress="javascript:mascaraMesAno(event, this)"></td>
			</tr>
			<tr class="linha_subtitulo">
				<td align="right">* Mês/Ano Final</td>
				<td><input type="text" name="mesAnoFim" value="<%=Pagina.getParamStr(request, "mesAnoFim")%>" size="7" maxlength="7" onkeypress="javascript:mascaraMesAno(event, this)"></td>
			</tr>
			<tr class="linha_subtitulo">
				<td align="right">* Sistema</td>
				<td>
				<%
				List lSistemas = configSisExecFinanDao.getSistemasAtivos();
				String selecionado = Pagina.getParamStr(request, "configSisExecFinanCsef");
				%>
					<combo:ComboTag 
						nome="configSisExecFinanCsef"
						objeto="ecar.pojo.ConfigSisExecFinanCsef" 
						label="nomeCsef" 
						value="codCsef" 
						order="nomeCsef"
						filters="indAtivoCsef"
						colecao="<%=lSistemas%>"
						selected="<%=selecionado%>"
					/>
				</td>
			</tr>
			<tr class="linha_subtitulo">
				<td class="label">
					&nbsp;
				</td>
				<td>
					<input type="button" name="exportar" value="Executar Exportação" onclick="javascript:exportar_arq(form);">
				</td>
			</tr>
			<tr class="linha_subtitulo">
				<td class="label" colspan="2">&nbsp;</td>
			</tr>
			<%
			
			String pathRaiz = configuracao.getRaizUpload();
			
			// Aqui começam os arquivos para download...
			// Um <tr> para cada arquivo gerado... 
			if("S".equals(Pagina.getParamStr(request, "hidGerarArquivo"))){

			%>
			<tr class="linha_subtitulo">
				<td class="label">Downloads</td>
				<td>&nbsp;</td>
			</tr>
			<%
				//Se codSistema for vazio, então foi escolhido opção TODOS. - Modificado: ver mantis 7550.
				String codSistema = Pagina.getParamStr(request, "configSisExecFinanCsef");
				String nomeArquivo = Pagina.getParamStr(request, "nomeArquivoExp");
				String mesAnoIni = Pagina.getParamStr(request, "mesAnoIni");
				String mesAnoFim = Pagina.getParamStr(request, "mesAnoFim");
				String label = "";
				
				String hashNomeArquivo = null;
				UsuarioUsu usuarioImagem = null;
				
				if(!"".equals(codSistema)){
					label = "Arquivo para Exportação";
					String[] arquivoExportado = configSisExecFinanDao.gerarArquivoExportacaoTxt(nomeArquivo, codSistema, mesAnoIni, mesAnoFim, configuracao);
					String caminhoArquivoGerado = arquivoExportado[0];
					String nomeArquivoGerado = arquivoExportado[1];
										
					hashNomeArquivo = Util.calcularHashNomeArquivo(caminhoArquivoGerado, caminhoArquivoGerado);
					usuarioImagem = ((ecar.login.SegurancaECAR)request.getSession().getAttribute("seguranca")).getUsuario();  
					Util.adicionarMapArquivosAtuaisUsuarios(usuarioImagem, hashNomeArquivo, caminhoArquivoGerado, caminhoArquivoGerado);
			%>
					<tr class="linha_subtitulo">
						<td class="label"><%=label%></td>
						<td><a href="<%=request.getContextPath()%>/DownloadFile?RemoteFile=<%=hashNomeArquivo%>"><%=nomeArquivoGerado%></a></td>
					</tr>
			<%
					String formato = "ddMMyyyy";
			        SimpleDateFormat formatter = new SimpleDateFormat(formato);
			        String codUsuariologado = seguranca.getUsuario().getCodUsu().toString();
			        String linkPDF = nomeArquivo + "_" + formatter.format(new Date()) + "_" + codUsuariologado + "_layout.pdf";
			        
			%>
					<tr class="linha_subtitulo">
						<td class="label">Arquivo de Layout</td>
						<td><a href="#" onclick="javascript:downloadArquivoLayout(form, '<%=nomeArquivoGerado%>')"><%=linkPDF%></a>&nbsp;</td>
					</tr>
			<%
				}
				/* Comentado ref. mantis 7550 (ver anotações).
				else {
					label = "Arquivos para Exportação";
					Iterator itSistema = lSistemas.iterator();
					while(itSistema.hasNext()){
						ConfigSisExecFinanCsef sistema = (ConfigSisExecFinanCsef) itSistema.next();
						String[] arquivoExportado = configSisExecFinanDao.gerarArquivoExportacaoTxt(nomeArquivo, sistema.getCodCsef().toString(), mesAnoIni, mesAnoFim, configuracao);
						String caminhoArquivoGerado = arquivoExportado[0];
						String nomeArquivoGerado = arquivoExportado[1];
						
						hashNomeArquivo = Util.calcularHashNomeArquivo(caminhoArquivoGerado, caminhoArquivoGerado);
						usuarioImagem = ((ecar.login.SegurancaECAR)request.getSession().getAttribute("seguranca")).getUsuario();  
						Util.adicionarMapArquivosAtuaisUsuarios(usuarioImagem, hashNomeArquivo, caminhoArquivoGerado, caminhoArquivoGerado);
			%>
						<tr class="linha_subtitulo">
							<td class="label"><%=label%></td>
							<td><a href="<%=request.getContextPath()%>/DownloadFile?RemoteFile=<%=hashNomeArquivo%>"><%=nomeArquivoGerado%></a></td>
						</tr>
			<%
						label = "&nbsp;";
					}
				}

				String formato = "ddMMyyyy";
		        SimpleDateFormat formatter = new SimpleDateFormat(formato);
		        String codUsuariologado = seguranca.getUsuario().getCodUsu().toString();
		        String linkPDF = nomeArquivo + "_" + formatter.format(new Date()) + "_" + codUsuariologado + "_layout.pdf";
		        
			%>
				<tr class="linha_subtitulo">
					<td class="label">Arquivo de Layout</td>
					<td><a href="#" onclick="javascript:downloadArquivoLayout(form, '<%=nomeArquivo%>')"><%=linkPDF%></a>&nbsp;</td>
				</tr>
			<%
				*/
			}
			%>
			<tr>
				<td class="espacador" colspan="2"><img src="../../images/pixel.gif" height="2"></td>
			</tr>
		</table>		
</form>

</div>
</body>
<% /* Controla o estado do Menu (aberto ou fechado) */%>
<%@ include file="../../include/estadoMenu.jsp"%>
<%@ include file="../../include/mensagemAlert.jsp" %>
</html>