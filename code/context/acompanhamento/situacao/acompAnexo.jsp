<!-- Esta tela chama a parte de anexo e de restrições de situação.
Comum  para a parte de registro e visualização-->

<%@page import="java.lang.Boolean"%>

<%
	String displayQuantidadeAnexos = "";
	String displayAnexos = "";
	String displayAnexosListaTag = "";
	String displayAnexosUploadTag = "";
	Boolean podeEditar = new Boolean (false);
	
	if(ehRegistro && usuarioLogadoEmiteParecer) {
		podeEditar = new Boolean (true);
	}
	
	
	
	if ( ((acompanhamento.getCodArel().toString()).equals(Pagina.getParamStr(request, "codArel"))) &&
		( ((cod != null && !cod.equals("")) || (inclusaoAnexo != null && (inclusaoAnexo.equals("S") 
		||inclusaoAnexo.equals("N")))) )) {
		displayAnexosUploadTag = "";
		displayQuantidadeAnexos = "none";
		displayAnexos = "";
		displayAnexosListaTag = "none";
	} else {
		displayAnexosUploadTag = "none";
		displayQuantidadeAnexos = "";
		displayAnexos = "none";
		displayAnexosListaTag = "";
	}
	
	boolean exibirAnexos = false;
	boolean exibirPontosCriticos = false;
	
	Aba aba = new Aba();
	aba.setNomeAba("GALERIA");
	
	AbaDao abaDao = new AbaDao(request);
	aba = (Aba)(abaDao.pesquisar(aba, new String[]{"codAba", "asc"}).get(0));
	if (aba != null && aba.getExibePosicaoAba().equals("S") && estruturaFuncaoDao.existeFuncaoAnexo(ari.getItemEstruturaIett().getEstruturaEtt())) {
		exibirAnexos = true;
	}
	
	
	
	if (exibirAnexos){
%>		
<span id="quantidade_anexos<%=acompanhamento.getCodArel()%>" style="display:<%=displayQuantidadeAnexos%>">	
   	<table class="form">
    	<tr class="label" align="left">
    		<td align="left">
    			<a href="javascript:mostrarEsconderAnexos('<%=acompanhamento.getCodArel()%>');"><%=estruturaFuncaoDao.getLabelFuncaoAnexo(ari.getItemEstruturaIett().getEstruturaEtt()) + ": " + anexos.size()%></a>
    		</td>
    	</tr>
    </table>
</span>
<span id="anexos<%=acompanhamento.getCodArel()%>" style="display:<%=displayAnexos%>">
	<!-- ############### Tag Lista de Anexos ################### -->
	<span id="anexosListaTag<%=acompanhamento.getCodArel()%>" style="display:<%=displayAnexosListaTag%>">
		<util:anexosListaTag 
			estruturaFuncao="<%=estruturaFuncao%>"
			categoriaAnexo="<%=categoriaAnexo%>"
			permissaoAlterar="<%=permissaoAlterar%>"
			anexo="<%=anexo%>" 
			caminhoRaizUpload="<%=caminhoRaizUpload%>"
			pathEcar="<%=_pathEcar%>"
			acompRelatorioArel="<%=acompanhamento%>"
			disabled="<%=_disabled%>"
			indice="<%=acompanhamento.getCodArel().toString()%>"
			podeEditar="<%=podeEditar%>"
			request="<%=request%>"
		/>
	</span>
	<!-- ############### Tag Lista de Anexos  ################### -->
	
	<!-- ############### Tag Upload de Anexos ################### -->
	<span id="anexosUploadTag<%=acompanhamento.getCodArel()%>" style="display:<%=displayAnexosUploadTag%>">
		<table class="form">
	    	<tr class="label" align="left">
	    		<td align="left">Arquivo Anexo:</td>
	    	</tr>
	    </table>
		<table class="barrabotoes" cellpadding="0" cellspacing="0">
			<tr>
				<td class="label">&nbsp;</td>
				<td>
					<% 
					Boolean exibirAlterarBtn = _disabled.equals("")?new Boolean(true):new Boolean(false); 
					if(exibirAlterarBtn.booleanValue()) { %>
					<input name="btnAlterar" type="button" value="Gravar" class="botao" onclick="aoClicarAlterar(form, '<%=acompanhamento.getCodArel()%>');">
					<% } %>
					<input name="btnVoltar" type="button" value="Cancelar" class="botao" onclick="aoClicarVoltar(form, '<%=acompanhamento.getCodArel()%>');">
				</td>
			</tr>
		</table>
		<util:anexosUploadTag 
			anexo="<%=anexo%>" 
			disabled=""
			readOnly=""
			nomeComboTag="uploadTipoArquivoUta"
			objetoComboTag="ecar.pojo.UploadTipoArquivoUta"
			labelComboTag="descricaoUta"
			valueComboTag="codUta"
			orderComboTag="descricaoUta"
			scriptsComboTag=""
			colecao="<%=categorias%>"
			indice="<%=acompanhamento.getCodArel().toString()%>"
		/>
		<table class="barrabotoes" cellpadding="0" cellspacing="0">
			<tr>
				<td class="label">&nbsp;</td>
				<td>
					<% 
					if(exibirAlterarBtn.booleanValue()) { %>
					<input name="btnAlterar" type="button" value="Gravar" class="botao" onclick="aoClicarAlterar(form, '<%=acompanhamento.getCodArel()%>');">
					<% } %>
					<input name="btnVoltar" type="button" value="Cancelar" class="botao" onclick="aoClicarVoltar(form, '<%=acompanhamento.getCodArel()%>');">
				</td>
			</tr>
		</table>
	</span>
</span>
<%
	}
%>
<!-- linha de anexos -->
			</td>
		</tr>
		<tr>
			<td class="espacador" colspan="2"><img src="<%=_pathEcar%>/images/pixel.gif" alt=""></td>
		</tr>
		<tr>
			<td colspan="2">


