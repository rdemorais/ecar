<jsp:directive.page import="ecar.util.Dominios" />
<jsp:directive.page import="ecar.dao.EstruturaDao" />
<jsp:directive.page import="ecar.pojo.ObjetoEstrutura" />
<jsp:directive.page import="java.util.Collections" />
<jsp:directive.page import="ecar.pojo.AcompRelatorioArel" />
<jsp:directive.page import="ecar.taglib.util.Input" />
<jsp:directive.page import="ecar.pojo.historico.HistoricoItemEstruturaIett"/>
<jsp:directive.page import="ecar.historico.Historico"/>
<jsp:directive.page import="java.util.Arrays"/>
<jsp:directive.page import="ecar.pojo.HistoricoConfig"/>
<jsp:directive.page import="ecar.dao.HistoricoDao"/>
<jsp:directive.page import="ecar.pojo.historico.HistoricoXml"/>
<%@ include file="/login/validaAcesso.jsp"%>
<%@ include file="/frm_global.jsp"%>

<%@ page import="ecar.pojo.ItemEstruturaIett"%>
<%@ page import="ecar.dao.ItemEstruturaDao"%>
<%@ page import="ecar.pojo.EstruturaFuncaoEttf"%>
<%@ page import="ecar.dao.EstruturaFuncaoDao"%>
<%@ page import="ecar.pojo.ConfigMailCfgm"%>
<%@ page import="ecar.permissao.ValidaPermissao"%>
<%@ page import="ecar.dao.ConfigMailCfgmDAO"%>

<%@ page import="comum.util.Util"%>

<%@ page import="java.util.Iterator"%>
<%@ page import="java.util.Collection"%>
<%@ page import="java.util.List"%>
<%@ page import="comum.util.Data"%>
<%@ page import="java.util.Date"%>
<%@ page import="ecar.pojo.ConfiguracaoCfg"%>
<%@ page import="ecar.dao.ConfiguracaoDao"%>
<%@ page import="ecar.pojo.SisGrupoAtributoSga"%>

<%@ taglib uri="/WEB-INF/ecar-util.tld" prefix="util"%>

<%@page import="ecar.pojo.historico.HistoricoPontoCriticoPtc"%>
<%@page import="ecar.pojo.UsuarioUsu"%><html lang="pt-br">
<head>
<%@ include file="/include/meta.jsp"%>
<%@ include file="/titulo.jsp"%>
<link rel="stylesheet"
	href="<%=_pathEcar%>/css/style_<%=configuracaoCfg.getEstilo().getNome()%>.css"
	type="text/css">

<script language="javascript" src="<%=_pathEcar%>/js/focoInicial.js"
	type="text/javascript"></script>
<script language="javascript"
	src="<%=_pathEcar%>/js/frmPadraoItemEstrut.js" type="text/javascript"></script>
<script language="javascript" src="<%=_pathEcar%>/js/validacoes.js"
	type="text/javascript"></script>
	<script language="javascript" src="<%=_pathEcar%>/js/datas.js"
	type="text/javascript"></script>
<script language="javascript" src="<%=_pathEcar%>/js/tableSort.js"></script>

<script language="javascript" src="<%=_pathEcar%>/js/destaqueLinha.js"></script>
<script language="javascript" src="<%=_pathEcar%>/js/listaItensCadastro.js" type="text/javascript"></script>		 
<script language="javascript" src="<%=_pathEcar%>/js/jquery.js" type="text/javascript"></script>
<script type="text/javascript">jQuery.noConflict();</script>		
<script language="javascript" src="<%=_pathEcar%>/js/cookie.js"></script>

<script language="javascript">

function aoClicarComparar(form){
	if (validarDoisChecksMarcados(form)){
		document.form.action = "frm_con_historico.jsp";
		document.form.submit();
	}
} 

function aoClicarConsultar(form){
	if (validarConsultar(form)){
		document.form.action = "lista_historico.jsp";
		document.form.submit();
	}
} 

function validarConsultar(form){

    if((form.dtInicio.value != '') && !validaData(form.dtInicio,false,true,true)){
        alert('Valor inválido para a data inicial.');
        return false;
    }
    if((form.dtFim.value != '') && !validaData(form.dtFim,false,true,true)){
        alert('Valor inválido para a data final.');
        return false;
    }
    
    if ((form.dtInicio.value != '') && (form.dtFim.value != '') && (form.dtInicio.value != form.dtFim.value)){
    	if(!DataMenor(form.dtInicio.value, form.dtFim.value)){
    		alert('Data inicial maior que final.');
			return false;
		}	
    }
    
	
	return true;
}

function aoClicarVoltar(form){
	document.form.action = "frm_con.jsp";
	document.form.submit();
}

function validarDoisChecksMarcados(form){
	var numChecks = 0;
	var count = 0;
    var nomeCheckBox = "codHist";

    numChecks = verificaChecks(form, nomeCheckBox);
    if (numChecks > 0) {
		if(numChecks > 1){
			for(i = 0; i < eval('form.' + nomeCheckBox + '.length'); i++)
				if(eval('form.' + nomeCheckBox + '[i]').checked)
					count++;	
		} else {
			count++
		}
	}
	
	if(count != 2){
		alert("Selecione dois registros para comparação.");
		return false;
	} else {
		return true;
	}
}

</script>
</head>

<%@ include file="/cabecalho.jsp"%>
<%@ include file="/divmenu.jsp"%>

<body>
<div id="conteudo">

<% 
try{
	ItemEstruturaDao itemEstruturaDao = new ItemEstruturaDao(request);
	EstruturaDao estruturaDao = new EstruturaDao(request); 
	
	ItemEstruturaIett itemEstrutura = (ItemEstruturaIett) itemEstruturaDao.buscar(ItemEstruturaIett.class, Long.valueOf(Pagina.getParamStr(request, "codIett")));
	
	ValidaPermissao validaPermissao = new ValidaPermissao();
	if ( !validaPermissao.permissaoConsultaIETT(  Long.valueOf(Pagina.getParamStr(request, "codIett")), seguranca ) )
	{response.sendRedirect( request.getContextPath() +  "/acessoIndevido.jsp" );
	}

	String[] codigos = {Pagina.getParamStr(request, "codIett")};
	String[] tipos = Pagina.getParamArray(request, "tipos");
	String consultaPadrao = Pagina.getParamStr(request, "consultaPadrao");
	String dataInicio = Pagina.getParamStr(request, "dtInicio");
	String dataFim = Pagina.getParamStr(request, "dtFim");
	if (!consultaPadrao.equals("N")){
		dataInicio = Data.parseDate(Data.addDias(-30, Data.getDataAtual()));
		dataFim = Data.parseDate(Data.getDataAtual());	
	}
	
	Date dataInicioDate = null;
	Date dataFimDate = null;
	if (dataInicio != null && !dataInicio.equals("")){
		dataInicioDate = Data.parseDate(dataInicio);	
	}
	if (dataFim != null && !dataFim.equals("")){
		dataFimDate = Data.addDias(1, Data.parseDate(dataFim));
	}
	
	Iterator lista = null;
	if(tipos == null || tipos.length == 0){
		String[] tiposHist = new String[2];
		tiposHist[0] = "1";
		tiposHist[1] = "2";
		lista = itemEstruturaDao.listaHistorico(dataInicioDate, dataFimDate, tiposHist, codigos).iterator();
	} else{	
		lista = itemEstruturaDao.listaHistorico(dataInicioDate, dataFimDate, tipos, codigos).iterator();
	}
		 
	String codAba = Pagina.getParamStr(request, "codAba");
	String tipoSelecao = "";
	
	List histCor = new HistoricoDao(null).listar(HistoricoConfig.class, new String[]{"codHistorico", "asc"});
	//*******************************************************
%>
 
<div id="tituloTelaCadastro">
	<!-- TITULO -->
	<%@ include file="/titulo_tela.jsp"%> 
</div>

<form name="form" method="post">

<%
boolean ehTelaListagem = false;
 
EstruturaEtt estruturaEttSelecionada = null;
EstruturaDao estruturaDaoArvoreItens = new EstruturaDao(request);


ConfiguracaoCfg conf = new ecar.dao.ConfiguracaoDao(request).getConfiguracao();

if(conf.getIndExibirArvoreNavegacaoCfg() != null && conf.getIndExibirArvoreNavegacaoCfg().equals("S")){
	%>
	
	<script language="javascript" src="../../js/menu_retratil_cadastro.js"></script>
	<script language="javascript" src="../../js/menu_cadastro.js"></script>	

	<%
	}else{
	%>
	
	<script language="javascript" src="../../js/menu_retratil.js"></script>
	<%
	}
	%>
<%@ include file="../arvoreItens.jsp"%>
 
<table width="100%" cellspacing="0" cellpadding="0" border="0">
	<tr>
		<td>
			<%
			//Se usar a árvore o id do div precisa ser "conteudoCadastroEstrutura" para que seja utilizado o estilo da árvore
			if(conf.getIndExibirArvoreNavegacaoCfg() != null && conf.getIndExibirArvoreNavegacaoCfg().equals("S")){ %>
			<div id="conteudoCadastroEstrutura">
			<%
			}else{
			%>
			<div>
			<%
			}
			%>
				<table width="100%" border="0" cellpadding="0" cellspacing="0">
					<tr>
					<%
					//Utilizado apenas quando a árvore está configurada para aparecer
					if (conf.getIndExibirArvoreNavegacaoCfg() != null && conf.getIndExibirArvoreNavegacaoCfg().equals("S")) {
					%>
					<!-- Barra amarela -->
		    			<td class="menuHideShowCadastro">
		    			<!-- Botão na barra -->
					<div id="btmenuCadastro"></div>
					</td>
					<script language="javascript">			
						//Inicia com o menu cadastro aberto
						botaoCadastro("aberto");
						mudaEstadoCadastro("aberto");			
					</script>
					<%} %>
						<td width="100%" valign="top">
							<!-- ############### Árvore de Estruturas  ################### -->
							<util:arvoreEstruturas 
								itemEstrutura="<%=itemEstrutura%>" 
								contextPath="<%=_pathEcar%>" 
								seguranca="<%=seguranca%>" 
								idLinhaCadastro="<%=Pagina.getParamStr(request,"ultimoIdLinhaDetalhado") %>"
								ultimoIdLinhaExpandido="<%=Pagina.getParamStr(request,"ultimoIdLinhaExpandido")%>"
								
							/>
							 <!-- ############### Árvore de Estruturas  ################### -->
							 
							<!-- ############### Barra de Links  ################### -->
							<util:barraLinksItemEstrutura 
								estrutura="<%=itemEstrutura.getEstruturaEtt()%>" 
								selectedFuncao="<%=codAba%>" 
								codItemEstrutura="<%=itemEstrutura.getCodIett().toString()%>" 
								contextPath="<%=request.getContextPath()%>" 
								idLinhaCadastro="<%=Pagina.getParamStr(request,"ultimoIdLinhaDetalhado") %>"
							/>
							<!-- ############### Barra de Links  ################### -->
							<%%>
							<br><br>
							
							<input type="hidden" name="hidAcao" value="">
							<input type="hidden" name="codIett" value="<%=itemEstrutura.getCodIett()%>">
							<input type="hidden" name="codAba" value="<%=codAba%>">
							
							<input type=hidden name="codIettPrincipal" value="<%=Pagina.getParam(request, "codIettPrincipal")%>">
							<input type=hidden name="ultEttSelecionado" value="<%=Pagina.getParam(request, "ultEttSelecionado")%>">
							 
							<input type="hidden" name="ultimoIdLinhaExpandido" id="ultimoIdLinhaExpandido" value="<%=Pagina.getParamStr(request,"ultimoIdLinhaExpandido")%>">
							<input type="hidden" name="consultaPadrao" id="consultaPadrao" value="<%=Pagina.NAO%>">
							<table width="100%" border="0" cellpadding="0" cellspacing="0">
								<tr><td class="espacador"><img src="<%=_pathEcar%>/images/pixel.gif" alt=""></td></tr>
								<tr class="linha_subtitulo" align="right">
									<td>
										Ciclo de &nbsp;
										<input type="text" name="dtInicio" maxlength="10" value="<%=dataInicio == null || dataInicio.equals("") ? "" : dataInicio%>" onkeyup="mascaraData(event, this);" onblur="validaData(this.form.dtInicio, true, true, false);">
										<img class="posicao" title="Selecione a data" src="../../images/icone_calendar.gif" onclick="open_calendar('data', document.forms[0].dtInicio, '')">
										 &nbsp; até &nbsp;
										<input type="text" name="dtFim" maxlength="10" value="<%=dataFim == null || dataFim.equals("") ? "" : dataFim%>" onkeyup="mascaraData(event, this);" onblur="validaData(this.form.dtFim, true, true, false);">
										<img class="posicao" title="Selecione a data" src="../../images/icone_calendar.gif" onclick="open_calendar('data', document.forms[0].dtFim, '')">
										<input type="checkbox" name="tipos" value="1" <%=Pagina.isChecked("1", tipos == null ? null : Arrays.asList(tipos))%>/>
										&nbsp;Incluídos
										<input type="checkbox" name="tipos" value="2" <%=Pagina.isChecked("2", tipos == null ? null : Arrays.asList(tipos))%>/>
										&nbsp;Alterados
										<!-- input type="checkbox" name="tipos" value="3"/>
										Excluídos
										-->
									</td>
								</tr>
							</table>
							<table width="100%" border="0" cellpadding="0" cellspacing="0">
								<tr><td class="espacador"><img src="<%=_pathEcar%>/images/pixel.gif" alt=""></td></tr>
								<tr class="linha_titulo" align="right">
									<td>
										<input type="button" class="botao" value="Comparar" onclick="javascript:aoClicarComparar(form);">
										<input type="button" class="botao" value="Consultar" onclick="javascript:aoClicarConsultar(form);">
										<input type="button" class="botao" value="Voltar" onclick="javascript:aoClicarVoltar(form);">
									</td>
								</tr>
							</table>
							<!-- ############### INICIO - Implementação da Lista  ################### -->
							<table width="100%" border="0" cellpadding="0" cellspacing="0">
								<tr>
									<td class="espacador" colspan="7">
										<img src="<%=request.getContextPath()%>/images/pixel.gif">
									</td>
								</tr>
								<tr class="linha_subtitulo">
									<td> </td>
									<td> </td>
									<td> Histórico </td>
									<td> Código </td>
									<td> Tipo </td>
									<td> Data </td>
									<td> Usuário </td>
								</tr>
								<%
								
								HistoricoDao historicoDao = new HistoricoDao(request);
								while (lista.hasNext())
								{
									HistoricoXml historicoItemEstruturaIett = (HistoricoXml) lista.next();
								%>
								<tr>
									<% 
									String tipo = ""; 
									String cor = "";
									String imagem = "";
									if (historicoItemEstruturaIett.getTipoHistorico() != null){ 
										switch (historicoItemEstruturaIett.getTipoHistorico().intValue()) {
							        		case 1: tipo = "Incluído";  
							        				//cor = ((HistoricoConfig) histCor.get(0)).getCorHistorico();
							        				//imagem = historicoDao.getImagemPersonalizadaHistorico(((HistoricoConfig) histCor.get(0)));
							        				break;
							            	case 2: tipo = "Alterado"; 
							            			//cor = ((HistoricoConfig) histCor.get(1)).getCorHistorico();
							            			//imagem = historicoDao.getImagemPersonalizadaHistorico(((HistoricoConfig) histCor.get(1)));
							            			break;
							            	case 3: tipo = "Excluído";  
							            			//cor = ((HistoricoConfig) histCor.get(2)).getCorHistorico();
							            			//imagem = historicoDao.getImagemPersonalizadaHistorico(((HistoricoConfig) histCor.get(2)));
							            			break;
							        		default: tipo = "-"; break;
							        	}
							   		}
										
									%>
									<td width="2%">
										<input type="checkbox" class="form_check_radio" name="codHist" value="<%=historicoItemEstruturaIett.getCodigo()%>">
										<input type="hidden" name="codIett" value="<%=historicoItemEstruturaIett.getIdObjetoSerializado()%>">
									</td>
									<td width="2%">
									<%
							    		UsuarioUsu usuarioImagem = null;
										String hashNomeArquivo = null;
										if (imagem != null){
											
											usuarioImagem = ((ecar.login.SegurancaECAR)request.getSession().getAttribute("seguranca")).getUsuario();  
										    hashNomeArquivo = Util.calcularHashNomeArquivo(conf.getRaizUpload(), imagem);
											Util.adicionarMapArquivosAtuaisUsuarios(usuarioImagem, hashNomeArquivo, conf.getRaizUpload(), imagem);
											
											imagem = request.getContextPath()+"/DownloadFile?tipo=open&RemoteFile=" + hashNomeArquivo;
											
											%>
											 <img name="iconeHistorico<%=historicoItemEstruturaIett.getCodigo()%>" src="<%//=imagem%>" style="width: 15px; height: 15px;" width="" heigth="">  
										<%} %>
									</td>
									 <td style="color:<%//=cor %>" width="5%"><%=historicoItemEstruturaIett.getCodigo()%></td> 
									<td style="color:<%//=cor %>" width="5%"><%=historicoItemEstruturaIett.getIdObjetoSerializado()%></td>
									<td style="color:<%//=cor %>">
										<%//=tipo%>
									</td>
									<td style="color:<%//=cor %>"><%=Pagina.trocaNullDataHora(historicoItemEstruturaIett.getDataHistorico())%></td>
									<td style="color:<%//=cor %>"><%=historicoItemEstruturaIett.getUsuarioUsu() != null ? historicoItemEstruturaIett.getUsuarioUsu().getNomeUsu() : ""%></td>
								</tr>>
								<%
								}%>
							</table>
						
						</td>
					</tr>
				</table>			
			</div>
			
		</td>
	</tr>
	
</table>
 




</form>
<!-- ############### FIM    - Implementação da Lista  ################### -->
<%
} catch ( ECARException e ){ 
	org.apache.log4j.Logger.getLogger(this.getClass()).error(e);
	Mensagem.alert(_jspx_page_context, e.getMessageKey());
} catch (Exception e){
%>
	<%@ include file="/excecao.jsp"%>
<%
}
%>
</div>
<br>
</div>

</body>
<% /* Controla o estado do Menu (aberto ou fechado) */%>
<%@ include file="/include/estadoMenu.jsp"%>
<%@ include file="/include/mensagemAlert.jsp" %>
</html>
