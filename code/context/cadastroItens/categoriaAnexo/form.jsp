<%@page import="ecar.pojo.UsuarioUsu"%>
<%@page import="comum.util.Util"%>
<script language="JavaScript">
// Função criada para tirar o evento de submit do formulário do campo texto
// SM 196765. Mantis 0022266 (projeto 72806)
function disableEnterKey(e) {
     var key;

     if(window.event)
          key = window.event.keyCode;     //IE
     else
          key = e.which;     //firefox

     if(key == 13)
          return false;
     else
          return true;
}
</script>

<%
/*
SOBRE ESSE CADASTRO (Felipe 21/01):
No cadastro de Categorias de Anexo, há um campo para imagem do tipo input type=File.
Para esse campo ser submetido corretamente os form das páginas frm_inc e frm_alt tiveram seu enctype alterados para 
enctype="multipart/form-data".
Devido a esta alteração o tratamento dado a requisições vindas desses dois formulário precisa ser diferente: o método Pagina.getParam, 
normalmente utilizado não funcionaria.
Os formulários dessa tela possuem uma variável "isFormUpload" que informa se o formulário tem o enctype para upload de arquivos (true) ou
se é um request comum (false).
As páginas frm_con e ctrl, que precisam utilizar parâmetros do request, fazem uma comparação com essa variável para decidir se vão usar métodos do Pagina
ou do FileUpload para voltar parãmetros da request. Quando o FileUpload é utilizado, o request deve passar pelo método criaListaCampos
que devolve um List de objetos FileItem ( campos ). Essa comparação não é feita no frm_inc  pois essa página é sempre acessada pela lista 
(request normal), nem no frm_alt que é acessado sempre pelo frm_con  e nem na lista que é acessada a partir do frm_con ou de uma
outra função do sistema.
*/
%>
<input type=hidden name="codIett" value="<%=itemEstrutura.getCodIett()%>">

<%
/* faz o tratamento do request dependendo se é um form de upload ou não */
if(!isFormUpload){%>
<input type=hidden name="cod" value="<%=Pagina.getParamStr(request, "cod")%>">
<input type=hidden name="codAba" value="<%=Pagina.getParamStr(request, "codAba")%>">
<%} else {%>

<input type="hidden" name="codAba" value="<%=FileUpload.verificaValorCampo(campos, "codAba")%>">
<input type="hidden" name="cod" value="<%=FileUpload.verificaValorCampo(campos, "cod")%>">
<%}%>

<input type=hidden name="codIettuc" value="">
<input type=hidden name="hidAcao" value="">

	<tr> 
			<td colspan="2">
				<div class="camposobrigatorios">* Campos de preenchimento obrigat&oacute;rio</div>
			</td>
	</tr>
	<tr>
		<td class="label">* Nome</td>
		<td><input type="text" name="nomeIettuc" size="33" maxlength="30" value="<%=Pagina.trocaNull(categoria.getNomeIettuc())%>" <%=_disabled%> onKeyPress="return disableEnterKey(event)"></td>
	</tr>
	<tr>
		<td class="label">* Descri&ccedil;&atilde;o</td>
		<td><textarea name="descricaoIettuc" rows="5" cols="60" <%=_readOnly%> 
			onkeyup="return validaTamanhoLimite(this, 2000);"
			onkeydown="return validaTamanhoLimite(this, 2000);"
			onblur="return validaTamanhoLimite(this, 2000);"><%=Pagina.trocaNull(categoria.getDescricaoIettuc())%></textarea>
		</td>
	</tr>
	<tr>
		<td class="label">Imagem associada p/ Galeria</td>
		<td>
			<input type="file" name="imagemIettuc" <%=_disabled%>>
			<%
				String hashNomeArquivo = null;
				UsuarioUsu usuarioImagem = null;  
				String pathRaiz = null;
				
				if(categoria.getImagemIettuc()!=null){
					usuarioImagem = ((ecar.login.SegurancaECAR)request.getSession().getAttribute("seguranca")).getUsuario();  
				
					pathRaiz = new ecar.dao.ConfiguracaoDao(request).getConfiguracao().getRaizUpload();
					
					// código de tratamento de permissão de acesso a arquivo
					
					hashNomeArquivo = Util.calcularHashNomeArquivo(pathRaiz, categoria.getImagemIettuc());
					Util.adicionarMapArquivosAtuaisUsuarios(usuarioImagem, hashNomeArquivo, pathRaiz, categoria.getImagemIettuc());	
					
					%><br><br><img src="<%=request.getContextPath()%>/DownloadFile?tipo=open&RemoteFile=<%=hashNomeArquivo%>">
					<input type="checkbox" class="form_check_radio" name="excluiImagem" <%=_disabled%>>excluir imagem
					<%
				}
			%>
		</td>
	</tr>	
	<tr>
		<td class="label">* Tipo de Anexos</td>
		<td>		
		<%
		String selectedTipo = "";
		if(categoria.getUploadTipoCategoriaUtc() != null)
			selectedTipo = categoria.getUploadTipoCategoriaUtc().getCodUtc().toString();
		String tipoDisabled = _disabled;
		if(categoria.getItemEstrutUploadIettups() != null && categoria.getItemEstrutUploadIettups().size() > 0){
			tipoDisabled = " disabled ";
		}
		%>
		<combo:ComboTag 
						nome="uploadTipoCategoriaUtc"
						objeto="ecar.pojo.UploadTipoCategoriaUtc" 
						label="nomeUtc" 
						value="codUtc" 
						order="nomeUtc"
						selected="<%=selectedTipo%>"
						scripts="<%=tipoDisabled%>"		/>
		</td>
	</tr>
<% /* Controla o estado do Menu (aberto ou fechado) */%>
<%@ include file="../../include/estadoMenu.jsp"%>
