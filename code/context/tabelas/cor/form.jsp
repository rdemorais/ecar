<%@ page import="ecar.dao.CorDao" %>
<%@ page import="ecar.dao.TipoFuncAcompDao" %>
<%@ page import="ecar.pojo.TipoFuncAcompTpfa" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.Iterator" %>
<%@ page import="ecar.pojo.PontocriticoCorPtccor" %>
<%@ page import="ecar.pojo.PontocriticoCorPtccorPK" %>
<%@ page import="ecar.dao.PontocriticoCorPtccorDAO" %>
<%@ page import="ecar.pojo.PontoCriticoPtc" %>
<%@ page import="ecar.dao.PontoCriticoDao" %>
<%@ page import="java.io.File" %>
<%@ page import="comum.util.Util" %>

<%
/*
 * arquivo comum para as telas de inclusao, alteracao, exclusao e pesquisa 
 * observar a variavel _disabled e setar o objeto corretamente pelo construtor
 * assinalando os campos com os valores iniciais para inclusao
 */ 
%>


<%@page import="ecar.util.Dominios"%>
<%@page import="ecar.pojo.ConfiguracaoCfg"%><script language="javascript">
function limparRadioButtons(radioObj){
		
	var radioLength = radioObj.length;
	if(radioLength == undefined) {
		radioObj.checked = false;
		
	}
	for(var i = 0; i < radioLength; i++) {
		radioObj[i].checked = false;
	}	
}
</script>

<util:msgObrigatorio obrigatorio="<%=_obrigatorio%>" />

<tr>
	<td class="label"><%=_obrigatorio%> Nome</td>
	<td><input type="text" name="nomeCor" size="23" maxlength="20" value="<%=Pagina.trocaNull(cor.getNomeCor())%>" <%=_disabled%>></td>
</tr>
<tr>
	<td class="label"><%=_obrigatorio%> Significado</td>
	<td><input type="text" name="significadoCor" size="23" maxlength="20" value="<%=Pagina.trocaNull(cor.getSignificadoCor())%>" <%=_disabled%>></td>
</tr>

<tr>
	<td class="label"><%=_obrigatorio%> Cor no Gráfico </td>
	<td><input type="text" name="codCorGrafico" id="codCorGrafico" size="23" maxlength="7" value="<%=Pagina.trocaNull(cor.getCodCorGrafico() )%>" <%=_disabled%> readonly="readonly">
		<%if (!"disabled".equals(_disabled)){%>
			<a href="#" onclick="selecionarCor('codCorGrafico', 'pick1');" name="pick1" id="pick1">Selecionar Cor</a>
		<%}%>
	</td>
</tr>

<tr>
		<td width="20%" class="label"><%=_obrigatorio%> Apresentar em Posições Gerais</td>
		<td>
				<input type="radio" class="form_check_radio" name="indPosicoesGeraisCor" value="S" 
					<%=Pagina.isChecked(cor.getIndPosicoesGeraisCor(), "S")%> 
					<%=_disabled%>>Sim 
				<input type="radio" class="form_check_radio" name="indPosicoesGeraisCor" value="N" 
  				 <%=Pagina.isChecked(cor.getIndPosicoesGeraisCor(), "N")%>
				 <%=_disabled%>>Não
				 
				 <%
				 	if (ehPesquisa){
				 %>
				 	<input type="button" value="Limpar" onclick="javascript:limparRadioButtons(indPosicoesGeraisCor)">
					<br>
				 <%
				 	}
				 %>
		</td>
</tr>
<tr>
		<td width="20%" class="label"><%=_obrigatorio%> Apresentar em Pontos Críticos</td>
		<td>
				<input type="radio" class="form_check_radio" name="indPontoCriticoCor" value="S" 
					<%=Pagina.isChecked(cor.getIndPontoCriticoCor(), "S")%> 
					<%=_disabled%>>Sim 
				<input type="radio" class="form_check_radio" name="indPontoCriticoCor" value="N" 
  				 <%=Pagina.isChecked(cor.getIndPontoCriticoCor(), "N")%>
				 <%=_disabled%>>Não
				 
				 <%
				 	if (ehPesquisa){
				 %>
				 	<input type="button" value="Limpar" onclick="javascript:limparRadioButtons(indPontoCriticoCor)">
					<br>
				 <%
				 	}
				 %>
		</td>
</tr>
<tr>
		<td width="20%" class="label"><%=_obrigatorio%> Apresentar em Indicadores Físicos</td>
		<td>
				<input type="radio" class="form_check_radio" name="indIndicadoresFisicosCor" value="S" 
					<%=Pagina.isChecked(cor.getIndIndicadoresFisicosCor(), "S")%> 
					<%=_disabled%>>Sim 
				<input type="radio" class="form_check_radio" name="indIndicadoresFisicosCor" value="N" 
  				 <%=Pagina.isChecked(cor.getIndIndicadoresFisicosCor(), "N")%>
				 <%=_disabled%>>Não
				 
				 <%
				 	if (ehPesquisa){
				 %>
				 	<input type="button" value="Limpar" onclick="javascript:limparRadioButtons(indIndicadoresFisicosCor)">
					<br>
				 <%
				 	}
				 %>
		</td>
</tr>
<tr>
		<td width="20%" class="label">Posições Listagem</td>
		<td> 		
		</td>
</tr>

<%
	CorDao cDao = new CorDao(request);
	TipoFuncAcompDao tipoFuncAcompDao = new TipoFuncAcompDao();	
	
	//ConfiguracaoCfg configuracao = (ConfiguracaoCfg)session.getAttribute("configuracao");
	
	String imagePath = configuracao.getRaizUpload() + Dominios.PATH_REMOTE_IMAGES;	

%>
<tr>
	<td></td>
	<td>
	<table>
	
	<tr>
	<td width="130">&nbsp; </td>
<!-- <td align="center" width="50">&nbsp;</td>   -->
	<td> Arquivo de imagens para posições</td>
	</tr>
	
	 <%

	List funcoes = tipoFuncAcompDao.getTipoFuncAcompEmitePosicao();
	Iterator itFuncoes = funcoes.iterator();	
	_obrigatorio = "";
	
	while(itFuncoes.hasNext()){												
		TipoFuncAcompTpfa funcao = (TipoFuncAcompTpfa) itFuncoes.next(); 
		
		String arquivo = Util.getNomeArquivo(cDao.getImagemPersonalizada(cor, funcao, "L"));
		String nomeCampo = "L_" + funcao.getCodTpfa();
		String nomeCorImagem = cDao.getImagemSinalRelPosicoes(cor,funcao);

	%>
		
		<util:uploadArquivoTag 
				nomeCampo="<%=nomeCampo%>" 
				labelCampo="<%=funcao.getLabelTpfa()%>"
				contextPath="<%=request.getContextPath()%>"
				pathRaiz="<%=imagePath%>"
				arquivo="<%=arquivo%>"
				desabilitado="<%=_disabled%>"
				obrigatorio="<%=_obrigatorio%>"
				ehTabelaCor= "true"
				nomeCorTabela= "<%=nomeCorImagem!=null?nomeCorImagem:""%>"
			/>
		
	<%
	} 
	%>
	</table></td>
	</tr>		

<tr>
		<td width="20%" class="label">Posições Detalhe</td>
		<td>			
		</td>
</tr>

<tr>
	<td></td>
	<td>
	<table>
<%	
	List funcoesDet = tipoFuncAcompDao.getTipoFuncAcompEmitePosicao();
	Iterator itFuncoesDet = funcoesDet.iterator();	
	
	while(itFuncoesDet.hasNext()){												
		TipoFuncAcompTpfa funcaoDet = (TipoFuncAcompTpfa) itFuncoesDet.next();	
		
		String arquivo = Util.getNomeArquivo(cDao.getImagemPersonalizada(cor, funcaoDet, "D"));
		String nomeCampo = "D_" + funcaoDet.getCodTpfa();
		String nomeCorImagem = cDao.getImagemSinalRelPosicoes(cor,funcaoDet);
		%>
			
			<util:uploadArquivoTag 
					nomeCampo="<%=nomeCampo%>" 
					labelCampo="<%=funcaoDet.getLabelTpfa()%>"
					contextPath="<%=request.getContextPath()%>"
					pathRaiz="<%=imagePath%>"
					arquivo="<%=arquivo%>"
					desabilitado="<%=_disabled%>"
					obrigatorio="<%=_obrigatorio%>"
					ehTabelaCor= "true"
					nomeCorTabela= "<%=nomeCorImagem!=null?nomeCorImagem:""%>"
					
				/>
		
	<%
	}	
	%>
	</table></td>
</tr>

<tr>
		<td width="20%" class="label">Posições Relatório</td>
		<td>			
		</td>
</tr>

<tr>
	<td></td>
	<td>
	<table>
<%
	
	List funcoesRel = tipoFuncAcompDao.getTipoFuncAcompEmitePosicao();
	Iterator itFuncoesRel = funcoesRel.iterator();

	while(itFuncoesRel.hasNext()){												
		TipoFuncAcompTpfa funcaoRel = (TipoFuncAcompTpfa) itFuncoesRel.next();
		
		String arquivo = Util.getNomeArquivo(cDao.getImagemPersonalizada(cor, funcaoRel, "R"));
		String nomeCampo = "R_" + funcaoRel.getCodTpfa();
		String nomeCorImagem = cDao.getImagemSinalRelPosicoes(cor,funcaoRel);
		%>
			
			<util:uploadArquivoTag 
					nomeCampo="<%=nomeCampo%>" 
					labelCampo="<%=funcaoRel.getLabelTpfa()%>"
					contextPath="<%=request.getContextPath()%>"
					pathRaiz="<%=imagePath%>"
					arquivo="<%=arquivo%>"
					desabilitado="<%=_disabled%>"
					obrigatorio="<%=_obrigatorio%>"
					ehTabelaCor= "true"
					nomeCorTabela= "<%=nomeCorImagem!=null?nomeCorImagem:""%>"
					
				/>			
	<%		
	}
	%>
	</table></td>
</tr>

<%
String	nomeCorImagem2 = "pc" + cor.getNomeCor() + "1.png";
%>

<util:uploadArquivoTag 
			nomeCampo="caminhoImagemPontoCriticoCor" 
			labelCampo="Ponto Crítico"
			contextPath="<%=request.getContextPath()%>"
			pathRaiz="<%=imagePath%>"
			arquivo="<%=cor.getCaminhoImagemPontoCriticoCor()%>"
			desabilitado="<%=_disabled%>"
			obrigatorio="<%=_obrigatorio%>"
			ehTabelaCor= "true"
			nomeCorTabela= "<%=nomeCorImagem2!=null?nomeCorImagem2:""%>"
		/>

<!-- AQUI FICAVA O CODIGO DE imagemPontoCritico. Pego arquivo BACKUP anexo cor -->

<%
nomeCorImagem2 = "ir" + cor.getNomeCor() + "1.png";
%>
<util:uploadArquivoTag 
			nomeCampo="caminhoImagemIndResul" 
			labelCampo="Indicador Físico"
			contextPath="<%=request.getContextPath()%>"
			pathRaiz="<%=imagePath%>"
			arquivo="<%=cor.getCaminhoImagemIndResulCor()%>"
			desabilitado="<%=_disabled%>"
			obrigatorio="<%=_obrigatorio%>"
						ehTabelaCor= "true"
			nomeCorTabela= "<%=nomeCorImagem2!=null?nomeCorImagem2:""%>"
		/>



<tr><td class="label">&nbsp;</td></tr>

<% /* controla o estado do menu (aberto ou fechado)*/ %>
<%@ include file="../../include/estadoMenu.jsp"%>
	