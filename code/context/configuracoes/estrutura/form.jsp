<%@page import="ecar.dao.FuncaoDao"%>
<%@page import="java.util.Collection"%>
<%@page import="java.util.ArrayList"%>
<%@page import="ecar.pojo.EstruturaEtt"%>
<%@page import="ecar.dao.EstruturaDao"%>

<%@page import="ecar.dao.ItemEstruturaDao"%>
<%@page import="ecar.util.Dominios"%>
<%@page import="java.util.List"%>
<%@page import="java.net.URLEncoder"%><jsp:directive.page import="java.util.Iterator"/>
<jsp:directive.page import="ecar.pojo.FuncaoFun"/>
<%@ taglib uri="/WEB-INF/tlds/c.tld" prefix="c"%> 

<%
/*
 * arquivo comum para as telas de inclusao, alteracao, exclusao e pesquisa 
 * observar a variavel _disabled e setar o objeto corretamente pelo construtor
 * assinalando os campos com os valores iniciais para inclusao
 */ 
%>

<script language="javascript">
function exibirOcultarCampo(idCampo){
	if (document.getElementById(idCampo).style.display == 'none'){
		document.getElementById(idCampo).style.display = '';
	} else {
		document.getElementById(idCampo).style.display = 'none';
	}
	 
}

function limparRadioButtons(radioObj){
		
	var radioLength = radioObj.length;
	if(radioLength == undefined) {
		radioObj.checked = false;
		
	}
	for(var i = 0; i < radioLength; i++) {
		radioObj[i].checked = false;
	}
	
	
}

function alterarStatusAtributos(nomeFuncao, idFuncao){

	checkAtributos = document.getElementsByName(nomeFuncao);
	
	for (var i=0; i< checkAtributos.length; i++){
		if (checkAtributos[i].id == idFuncao){
			checkAtributos[i].disabled = !document.getElementById(idFuncao).checked;
		}
	}
	if (document.getElementById(idFuncao).checked){
		document.getElementById('pontosCriticosHidden').value = "checked";
	}	
	else{
		document.getElementById('pontosCriticosHidden').value = "";
	}
}

function controlarAtributosVirtuaisImcompativeis() {

	var nivelSuperior = document.getElementsByName("indEtapaNivelSuperiorEtt");
	var imprimirListagem = document.getElementsByName("indExibirImprimirListagem");
	var gerarArquivo = document.getElementsByName("indExibirGerarArquivos");
	
	
	if (document.getElementsByName('virtual')[0].checked == true){//Trata a desabilitação dos campos

		//document.form.estruturaEttPai.disabled = true;
		//document.form.estruturaEttPai.value = "";
		
		//Inicio da desabilitação do Nivel Superior 
		//desabilita o(s) checkboxs ou radios(pesquisa) do nivel superior 
		for (i=0;i < nivelSuperior.length; i++) {  
			nivelSuperior[i].disabled=true;
		}
		//desabilita o botão limpar nivel superior
		var btnLimparNivelSuperior = document.getElementById('btnLimparNivelSuperior');
		if (btnLimparNivelSuperior != null) {
			btnLimparNivelSuperior.disabled = true;
		}
		//Fim da desabilitação do Nivel Superior	

		
		
		//Inicio da desabilitação do Imprimir Listagem
		//desabilita o(s) checkboxs ou radios(pesquisa) da impressão da listagem
		for (i=0;i < imprimirListagem.length; i++) {  
			imprimirListagem[i].disabled=true;
		}
		//desabilita o botão limpar Imprimir Listagem
		var btnLimparExibirImprimirListagem = document.getElementById('btnLimparImprimirListagem');
		if (btnLimparExibirImprimirListagem != null) {
			btnLimparExibirImprimirListagem.disabled = true;
		}
		//Fim do Imprimir Listagem
		

		//Inicio da desabilitação da Geração de arquivo
		//desabilita o(s) checkboxs ou radios(pesquisa) da geração de arquivo
//		for (i=0;i < gerarArquivo.length; i++) {  
//			gerarArquivo[i].disabled=true;
//		}
		//desabilita o botão limpar Geração de Arquivo
//		var btnGerarArquivo = document.getElementById('btnGerarArquivo');
//		if (btnGerarArquivo != null) {
//			btnGerarArquivo.disabled = true;
//		}
		//Fim da Geração de Arquivo
		
	} else {//Trata a Abilitação dos campos

		//document.form.estruturaEttPai.disabled = false;
		
		//Inicio da Abilitação do Nivel Superior
		for (i=0;i < nivelSuperior.length; i++) {  
			nivelSuperior[i].disabled=false
		}
		//abilita o botão limpar nivel superior
		var btnLimparNivelSuperiorAbilita = document.getElementById('btnLimparNivelSuperior');
		if (btnLimparNivelSuperiorAbilita != null) {
			btnLimparNivelSuperiorAbilita.disabled = false;
		}
		//Fim da Abilitação do Nivel Superior
	

		//Inicio da Abilitação do Imprimir Listagem
		for (i=0;i < imprimirListagem.length; i++) {  
			imprimirListagem[i].disabled=false
		}
		//abilita o botão limpar nivel superior
		var btnLimparExibirImprimirListagemAbilita = document.getElementById('btnLimparImprimirListagem');
		if (btnLimparExibirImprimirListagemAbilita != null) {
			btnLimparExibirImprimirListagemAbilita.disabled = false;
		}
		//Fim da Abilitação do Imprimir Listagem
		

		//Inicio da Abilitação da Geração de Arquivo
//		for (i=0;i < gerarArquivo.length; i++) {  
//			gerarArquivo[i].disabled=false
//		}
		//abilita o botão limpar nivel superior
//		var btnGerarArquivoAbilita = document.getElementById('btnGerarArquivo');
//		if (btnGerarArquivoAbilita != null) {
//			btnGerarArquivoAbilita.disabled = false;
//		}
		//Fim da Abilitação da Geração de Arquivo
	}
}


</script>

<table class="form">

	<util:msgObrigatorio obrigatorio="<%=_obrigatorio%>" />

	<tr>
		<td class="label"><%=_obrigatorio%> Nome</td>
		<td><input type="text" name="nomeEtt" size="35" maxlength="30" value="<%=Pagina.trocaNull(estrutura.getNomeEtt())%>" <%=_disabled%>></td>
	</tr>
	<tr>
		<td class="label"><%=_obrigatorio%> Sigla</td>
		<td><input type="text" name="siglaEtt" size="15" maxlength="10" value="<%=Pagina.trocaNull(estrutura.getSiglaEtt())%>" <%=_disabled%>></td>
	</tr>
	<tr>
		<td class="label">Label</td>
		<td><input type="text" name="labelEtt" size="35" maxlength="30" value="<%=Pagina.trocaNull(estrutura.getLabelEtt())%>" <%=_disabled%>></td>
	</tr>
	<tr>
		<td class="label">N&iacute;vel Superior</td>
		<td>
			<%
	
	String desabilitar = "";		
	if (alteraNivel)
	{
		desabilitar = "false";
	} 
	else 
	{ 
		desabilitar = "true";
		
		if (estrutura.getEstruturaEtt() != null){
			String codEttPai = estrutura.getEstruturaEtt().getCodEtt().toString();
			
			%>
			<input type="hidden" id = "estruturaEttPaiHidden" name="estruturaEttPaiHidden" value="<%=codEttPai%>" >
			<%
		}
		
	}

	Collection estruturasReais = null;
	
	String scriptAlteracaoNivelSuperior = "onchange=\"document.getElementById('indCarregouPagina').value = 'S';document.getElementById('indAlterouNivelSuperior').value = 'S';reload(document.forms[0]);\"";
	
	if (ehAlteracao || ehInclusao){
						
		estruturasReais = estruturaDao.getEstruturasReaisPaisPossiveis(estrutura);
		
		if (ehAlteracao){
			
			if (selected != null && !selected.equals("")){
			
				EstruturaEtt estruturaPaiSelecionada = (EstruturaEtt) estruturaDao.buscar(EstruturaEtt.class, Long.valueOf(selected));
				
				if (!estruturasReais.contains(estruturaPaiSelecionada)){
					estruturasReais.add(estruturaPaiSelecionada);
				}
			}
		}
		%>
		<combo:ComboTag nome="estruturaEttPai"
			objeto="ecar.pojo.EstruturaEtt" label="nomeEtt,siglaEtt"
			value="codEtt" order="nomeEtt" 
			selected="<%=selected%>" 
			disabled="<%=desabilitar%>"
			colecao="<%=estruturasReais%>"
			scripts="<%=scriptAlteracaoNivelSuperior %>"
		/>
		<%
	}
	else{
		%>
		<combo:ComboTag nome="estruturaEttPai"
			objeto="ecar.pojo.EstruturaEtt" label="nomeEtt,siglaEtt"
			value="codEtt" order="nomeEtt" 
			selected="<%=selected%>" 
			disabled="<%=desabilitar%>"
			filters="virtual=False"
			scripts="<%=scriptAlteracaoNivelSuperior %>"
		/>
			
		<%
	}
	
	
	
	%>
			
		</td>
		
		
	</tr>
	<script type="text/javascript">
	
			document.form.estruturaEttPai.disabled = <%=!alteraNivel%>;
	
	</script>
	<tr>
		<td class="label">&Eacute; virtual?</td>
		<td>
		
			<%
			if (ehPesquisa) {
			%>
							
				<input type="radio" class="form_check_radio" name="virtual" value="true" <%=Pagina.isChecked(estrutura.getVirtual(), true)%>>
												
						Sim &nbsp;
				<input type="radio" class="form_check_radio" name="virtual" value="false" <%=Pagina.isChecked(estrutura.getVirtual(), false)%>>
											
						Não &nbsp;
				<input type="button" value="Limpar" onclick="javascript:limparRadioButtons(virtual)" id ="btnLimparVirtual">
				<br>
				
			<%
			} else {
			%>
				<input type="checkbox" name="virtual" value='true' disabled onclick="controlarAtributosVirtuaisImcompativeis();" <%=Pagina.isChecked(estrutura.getVirtual(), true)%> >
				
				<input type="hidden" name="virtual" class="form_check_radio" value="<%=estrutura.getVirtual()%>">
			<% 
			}
			%>
		
			
		</td>
	</tr>
	<tr>
		<td class="label">Esta estrutura &eacute; etapa do n&iacute;vel superior</td>
		<td>
		
			<%
			if (ehPesquisa) {
			%>
							
				<input type="radio" class="form_check_radio" name="indEtapaNivelSuperiorEtt" value="<%=Pagina.SIM%>" <%=Pagina.isChecked(estrutura.getIndEtapaNivelSuperiorEtt(), Pagina.SIM)%>>
												
						Sim &nbsp;
				<input type="radio" class="form_check_radio" name="indEtapaNivelSuperiorEtt" value="<%=Pagina.NAO%>" <%=Pagina.isChecked(estrutura.getIndEtapaNivelSuperiorEtt(), Pagina.NAO)%>>
											
						Não &nbsp;
				<input type="button" value="Limpar" onclick="javascript:limparRadioButtons(indEtapaNivelSuperiorEtt)" name="btnLimparNivelSuperior" id ="btnLimparNivelSuperior">
				<br>
				
			<%
			} else {
			%>
				<input type="checkbox" class="form_check_radio" name="indEtapaNivelSuperiorEtt" value="<%=Pagina.SIM%>"
					<%=Pagina.isChecked(estrutura.getIndEtapaNivelSuperiorEtt(), Pagina.SIM)%>
					<%=_pesqdisabled%>></td>
					
			<% 
			}
			%>
					
	</tr>
	
	
	
	
	
	
	<!-- ----------------------------------------------------------------------------------------------------- !-->
	<%
	//Indica se o usuário pode alterar os campos restritivos
	//da estrutura. Os campos são:
	//- Esta estrutura depende do nível superior para visualização  	
	//- Grupo de Atributos do nível superior para visualização 	
	//- Atributos do nível superior para visualização
	//
	String indAlterarCamposRestritivos = Dominios.SIM;
	String indExibirEstruturaEttDisabled = "";
	String codAtbExibirEstruturaEttDisabled = "";
	String sisAtributosEttSuperiorDisabled = "";
	
	String indAlterouNivelSuperior = Pagina.getParam(request, "indAlterouNivelSuperior");
	String indExibirEstruturaEtt = Pagina.getParam(request, "indExibirEstruturaEtt");
	if (indExibirEstruturaEtt == null){
		indExibirEstruturaEtt = estrutura.getIndExibirEstruturaEtt();
	}
	
	ItemEstruturaDao itemEstruturaDao = new ItemEstruturaDao(request);
	List itensAtivos = null;
	if (estrutura.getCodEtt() != null) {
		itensAtivos = itemEstruturaDao.getItensAtivosByEstrutura(estrutura.getCodEtt());
	}
	//Caso exista algum item ativo na estrutura, o campos restritivos não podem 
	//ser alterados
	if(itensAtivos != null && itensAtivos.size() > 0){
		indAlterarCamposRestritivos = Dominios.NAO;
		indExibirEstruturaEttDisabled = "disabled";
		codAtbExibirEstruturaEttDisabled = "disabled";
		sisAtributosEttSuperiorDisabled = "disabled";
	} else {
		/*a variável selected guarda a estrutura selecionada
			no campo 'Nível Superior'
			Se não tiver nenhuma estrutura selecionada,
			o sistema deve desabilitar e limpar os 
			campos 'Esta estrutura depende do nível superior para visualização'
			e 'Grupo de Atributos do nível superior para visualização'*/
		
			
		// Quando escolhe o nivel superior em branco, o check box vai ser desabilitado e desmarcado. Só nao vai ser desabilitado na pesquisa
		if (selected == null || selected.equals("") && !ehPesquisa){
			indExibirEstruturaEttDisabled = "disabled";
			codAtbExibirEstruturaEttDisabled = "disabled";
			indExibirEstruturaEtt = Dominios.NAO;
		
		// Se houver alteração no nível superior e for escolhida uma estrutura
		// os campos de grupos de atributos e atributos vão ser desabilitados e retirados da tela
		} else if(indAlterouNivelSuperior != null && indAlterouNivelSuperior.equals("S")) {
			
			//na alteração e na inclusão, quando alterar o nivel superior, o indicativo vai ficar desmarcado (=valor NAO)
			if(ehInclusao || ehAlteracao) {
				indExibirEstruturaEtt = Dominios.NAO;
				//e se a estrutura nao tiver nenhum atributo, o check box vai ser desabilitado e desmarcado	
				if(listaAtributoEstrutura.isEmpty()) {
					indExibirEstruturaEttDisabled = "disabled";
				} 
			
			// na pesquisa, o indicativo vai ser um radio button	
			} else if(ehPesquisa) {
				
				
				//se for pesquisa e a estrutura selecionada for vazia ou possuir algum grupo de atributos, o radio button vai ser desmarcado 	
				if(selected == null || selected.equals("") || !listaAtributoEstrutura.isEmpty()){
					indExibirEstruturaEtt = "";
					
				//se for pesquisa e a estrutura nao tiver nenhum atributo, o radio button "Nao" vai ser selecionado e vai ser desabilitado
				}  else if(listaAtributoEstrutura.isEmpty()) {
					indExibirEstruturaEtt = Dominios.NAO;
					indExibirEstruturaEttDisabled = "disabled";
				}	
				
				
			}
			
			// pra qualquer alteração no nível superior, os grupos de atributo e os atributos vão ser desmarcados e limpados da tela
			codAtbExibirEstruturaEttDisabled = "disabled";
			sisAtributosEttSuperiorDisabled = "disabled";
		
			
		// caso o indicativo de estrutura seja igual a nao, os grupos de atributo e os atributos vão ser desmarcados e limpados da tela	
		} else if ((indExibirEstruturaEtt == null || indExibirEstruturaEtt.equals(Dominios.NAO)) ){
			
			// na pesquisa, quando é null, limpa os radio buttons
			if(indExibirEstruturaEtt == null && ehPesquisa)
				indExibirEstruturaEtt = "";
			
			//quando a estrutura possui um nivel superior sem grupos de atributos cadastrados, o check box fica desabilitado 
			// isso acontece quando o registro foi gravado com nivel superior sem grupo de atributos
			if(ehAlteracao && indExibirEstruturaEtt.equals(Dominios.NAO) && listaAtributoEstrutura.isEmpty())
				indExibirEstruturaEttDisabled = "disabled";
			
			
			//desabilia os campos de grupos de atributos e atributos ( não vai aparecer na tela)
			codAtbExibirEstruturaEttDisabled = "disabled";
			sisAtributosEttSuperiorDisabled = "disabled";
		
		// quando for para navegar entre as pesquisas, os campos ficam desabilitados	
		} else if(ehNavegacao) {
			codAtbExibirEstruturaEttDisabled = "disabled";
			sisAtributosEttSuperiorDisabled = "disabled";
		}
			
	}		
	
	%>
	<input type="hidden" id="indAlterarCamposRestritivos" name="indAlterarCamposRestritivos" value="<%=indAlterarCamposRestritivos%>">
	<input type="hidden" id="indAlterouNivelSuperior" name="indAlterouNivelSuperior" value="">
	<input type="hidden" id="indCarregouPagina" name="indCarregouPagina" value="">

	<tr>
		<td class="label"><nobr>Esta estrutura depende do n&iacute;vel superior para visualização</nobr></td>
		<td>
			<%	
			
			
			    //quando for pesquisa, o check box vai ser um radio button com a opção "Sim" ou "Não" e o botão limpar
				if (ehPesquisa) {
			%>		
				<!-- ao marcar um radio button, o reload da tela só vai acontecer se houver um nível superior selecionado -->					
				<input type="radio" class="form_check_radio" name="indExibirEstruturaEtt" id="indExibirEstruturaEtt" value="<%=Pagina.SIM%>" 	<%=Pagina.isChecked(indExibirEstruturaEtt, Pagina.SIM)%>
							<%=indExibirEstruturaEttDisabled%>  onclick="document.getElementById('indCarregouPagina').value = 'S';if(document.form.estruturaEttPai != null && document.form.estruturaEttPai.value != '')reload(document.forms[0]);">
												
						Sim &nbsp;
						
						
				<input type="radio" class="form_check_radio" name="indExibirEstruturaEtt" id="indExibirEstruturaEtt" value="<%=Pagina.NAO%>" <%=Pagina.isChecked(indExibirEstruturaEtt, Pagina.NAO)%>
							<%=indExibirEstruturaEttDisabled%>  onclick="document.getElementById('indCarregouPagina').value = 'S';if(document.form.estruturaEttPai != null && document.form.estruturaEttPai.value != '')reload(document.forms[0]);">
											
						Não &nbsp;
				<input type="button" value="Limpar" <%=indExibirEstruturaEttDisabled%> onclick="javascript:document.getElementById('indCarregouPagina').value = 'S';limparRadioButtons(indExibirEstruturaEtt); if(document.form.estruturaEttPai != null && document.form.estruturaEttPai.value != '')reload(document.forms[0]);  " name="btnLimparExibirEstrutura" id ="btnLimparExibirEstrutura">
				<br>
			<% } else {
			%>
				<input type="checkbox" class="form_check_radio" name="indExibirEstruturaEtt" id="indExibirEstruturaEtt" value="<%=Pagina.SIM%>"
					<%=Pagina.isChecked(indExibirEstruturaEtt, Pagina.SIM)%>
					<%=_pesqdisabled%> <%=indExibirEstruturaEttDisabled%> onclick="document.getElementById('indCarregouPagina').value = 'S';reload(document.forms[0]);"></td>
			<% 	} 	
			%>			
	</tr>
	
	
	
	
	<%String scriptCodAtbExibirEstruturaEtt = codAtbExibirEstruturaEttDisabled + " onchange=\"document.getElementById('indCarregouPagina').value = 'S';reload(document.forms[0]);\"";
	
		
		// a opção de grupo de atributos só vai ficar disponível quando selecionar o campo acima de esta estrutura depende do nivel superior para visualizacao
		if(indExibirEstruturaEtt != null && indExibirEstruturaEtt.equals(Pagina.SIM)) {
		%>
		<tr>
			<td class="label"><nobr>Grupo de Atributos do n&iacute;vel superior para visualização</nobr></td>
			<td>
				<combo:ComboTag nome="codAtbExibirEstruturaEtt" objeto="ecar.pojo.AtributosAtb"
					label="labelPadraoAtb" value="codAtb" order="labelPadraoAtb"
					scripts="<%=scriptCodAtbExibirEstruturaEtt%>" 
					selected="<%=selectedAtributo%>"
					colecao="<%=listaAtributoEstrutura%>"/>
			</td>
		</tr>
		<% 
		
			// os atributos só vao aparecer na tela se um atributo acima for selecionado
			if(selectedAtributo == null || selectedAtributo.equals("")) {
			%>
				<tr id="atributosEttSuperior" valign="top" style="display:none">
			<% } else if(ehAlteracao) {%>
				
				<td class="label"><nobr>Atributos do n&iacute;vel superior para visualização</nobr></td>
				
				<%if(atributosEttSuperior != null && !atributosEttSuperior.isEmpty()){
					String dica = "Os atributos desabilitados estão sendo utilizados em um ou mais itens para visualização de estruturas filhas";
				%>
				<!-- Alteração -->
				<td>
					<combo:CheckListTag nome="sisAtributoSatbEttSuperior"
						objeto="ecar.pojo.SisAtributoSatb" label="descricaoSatb" value="codSatb" order="descricaoSatb" 
						colecao="<%=atributosEttSuperior%>" selected="<%=lAtributosEttSuperior%>"
						scripts="disabled" dica="<%=dica%>" contextPath="<%=_pathEcar%>"/>
				</td>
				
				<%}  
				
				if(atributosEttSuperiorEditaveis != null && !atributosEttSuperiorEditaveis.isEmpty()){
				
					if(atributosEttSuperior != null && !atributosEttSuperior.isEmpty()){%>
						<tr><td>
					<%}%>	
					
				
				<td>
					<combo:CheckListTag id="sisAtributoSatbEttSuperiorEditaveis" nome="sisAtributoSatbEttSuperiorEditaveis"
						objeto="ecar.pojo.SisAtributoSatb" label="descricaoSatb" value="codSatb" order="descricaoSatb" 
						colecao="<%=atributosEttSuperiorEditaveis%>" selected="<%=lAtributosEttSuperiorEditaveis%>"
						/>
				</td>
				
					<%if(atributosEttSuperior != null && !atributosEttSuperior.isEmpty()){%>
						</tr></td>
					<%}%>	
				
				
				<%}
				
				
				
			
			 } else { %>
			
				<!-- Navegação/Inclusão/Pesquisa -->
				<td class="label"><nobr>Atributos do n&iacute;vel superior para visualização</nobr></td>
				<td>
					<combo:CheckListTag id="sisAtributoSatbEttSuperior" nome="sisAtributoSatbEttSuperior"
						objeto="ecar.pojo.SisAtributoSatb" label="descricaoSatb" value="codSatb" order="descricaoSatb" 
						colecao="<%=atributosEttSuperior%>" selected="<%=lAtributosEttSuperior%>"
						scripts="<%=sisAtributosEttSuperiorDisabled%>"/>
				</td>
				
				
				
				
			</tr>
		<% } 
		
	} else {
		%>
		<input type="hidden" id="codAtbExibirEstruturaEtt" name="codAtbExibirEstruturaEtt" value="">
		<% 
	}
	
	
	%>	
	
	
	<!-- ----------------------------------------------------------------------------------------------------- !-->
	
	
	<tr>
		<td class="label"><%=_obrigatorio%>
			Seqüência de Apresenta&ccedil;&atilde;o
		</td>
		<td>
			<input type="text" name="seqApresentacaoEtt" size="3" maxlength="3"
				value='<%=(estrutura.getSeqApresentacaoEtt() != null) ? estrutura.getSeqApresentacaoEtt().toString() : ""%>'
				<%=_disabled%>>
		</td>
	</tr>

	<tr>
		<td class="label"><%=_obrigatorio%>
			Cor de Fundo do Título
		</td>
		<td>
			<input type="text" name="codCor1Ett" size="20" maxlength="20"
				value="<%=Pagina.trocaNull(estrutura.getCodCor1Ett())%>"
				<%=_disabled%>>
			<%if (!"disabled".equals(_disabled)){%>
			<A HREF="#"
				onClick="selecionarCor(document.forms[0].codCor1Ett);return false;"
				NAME="pick" ID="pick">Selecionar Cor</A>
			<%}%>
		</td>
	</tr>

	<tr>
		<td class="label"><%=_obrigatorio%>
			Cor de Fundo de Cabe&ccedil;alho
		</td>
		<td>
			<input type="text" name="codCor2Ett" size="20" maxlength="20"
				value="<%=Pagina.trocaNull(estrutura.getCodCor2Ett())%>"
				<%=_disabled%>>
			<%if (!"disabled".equals(_disabled)){%>
			<A HREF="#"
				onClick="selecionarCor(document.forms[0].codCor2Ett);return false;"
				NAME="pick" ID="pick">Selecionar Cor</A>
			<%}%>
		</td>
	</tr>

	<tr>
		<td class="label"><%=_obrigatorio%>
			Cor de Fundo dos Itens
		</td>
		<td>
			<input type="text" name="codCor3Ett" size="20" maxlength="20"
				value="<%=Pagina.trocaNull(estrutura.getCodCor3Ett())%>"
				<%=_disabled%>>
			<%if (!"disabled".equals(_disabled)){%>
			<A HREF="#"
				onClick="selecionarCor(document.forms[0].codCor3Ett);return false;"
				NAME="pick" ID="pick">Selecionar Cor</A>
			<%}%>
		</td>
	</tr>

	<tr>
		<td class="label"><%=_obrigatorio%>
			Cor da Fonte
		</td>
		<td>
			<input type="text" name="codCor4Ett" size="20" maxlength="20"
				value="<%=Pagina.trocaNull(estrutura.getCodCor4Ett())%>"
				<%=_disabled%>>
			<%if (!"disabled".equals(_disabled)){%>
			<A HREF="#"
				onClick="selecionarCor(document.forms[0].codCor4Ett);return false;"
				NAME="pick" ID="pick">Selecionar Cor</A>
			<%}%>
		</td>
	</tr>
	
		<tr>
		<td class="label">Exibir Op&ccedil;&atilde;o de cria&ccedil;&atilde;o a partir do modelo</td>
		<td>
		
			<%
			if (ehPesquisa) {
			%>
							
				<input type="radio" class="form_check_radio" name="indExibirOpcaoModelo" value="<%=Pagina.SIM%>" <%=Pagina.isChecked(estrutura.getIndExibirOpcaoModelo(), Pagina.SIM)%>>
												
						Sim &nbsp;
				<input type="radio" class="form_check_radio" name="indExibirOpcaoModelo" value="<%=Pagina.NAO%>" <%=Pagina.isChecked(estrutura.getIndExibirOpcaoModelo(), Pagina.NAO)%>>
											
						Não &nbsp;
				<input type="button" value="Limpar" onclick="javascript:limparRadioButtons(indExibirOpcaoModelo)" id ="btnLimparImprimirListagem">
				<br>
				
			<%
			} else {
			%>
				<input type="checkbox" class="form_check_radio" name="indExibirOpcaoModelo" value="<%=Pagina.SIM%>"
				<%=Pagina.isChecked(estrutura.getIndExibirOpcaoModelo(), Pagina.SIM)%>
				<%=_pesqdisabled%>>
					
			<% 
			}
			%>
		
			
		</td>
	</tr>

	<tr>
		<td class="label">Exibir Op&ccedil;&atilde;o de Impress&atilde;o na Listagem de Itens</td>
		<td>
		
			<%
			if (ehPesquisa) {
			%>
							
				<input type="radio" class="form_check_radio" name="indExibirImprimirListagem" value="<%=Pagina.SIM%>" <%=Pagina.isChecked(estrutura.getIndExibirImprimirListagem(), Pagina.SIM)%>>
												
						Sim &nbsp;
				<input type="radio" class="form_check_radio" name="indExibirImprimirListagem" value="<%=Pagina.NAO%>" <%=Pagina.isChecked(estrutura.getIndExibirImprimirListagem(), Pagina.NAO)%>>
											
						Não &nbsp;
				<input type="button" value="Limpar" onclick="javascript:limparRadioButtons(indExibirImprimirListagem)" id ="btnLimparImprimirListagem">
				<br>
				
			<%
			} else {
			%>
				<input type="checkbox" class="form_check_radio" name="indExibirImprimirListagem" value="<%=Pagina.SIM%>"
				<%=Pagina.isChecked(estrutura.getIndExibirImprimirListagem(), Pagina.SIM)%>
				<%=_pesqdisabled%>>
					
			<% 
			}
			%>
		
			
		</td>
	</tr>
	
	<tr>
		<td class="label">Exibir Op&ccedil;&atilde;o de Gera&ccedil;&atilde;o de Arquivos na Listagem de Itens</td>
		<td>
		
			<%
			if (ehPesquisa) {
			%>
							
				<input type="radio" class="form_check_radio" name="indExibirGerarArquivos" value="<%=Pagina.SIM%>" 	<%=Pagina.isChecked(estrutura.getIndExibirGerarArquivos(), Pagina.SIM)%>>
												
						Sim &nbsp;
				<input type="radio" class="form_check_radio" name="indExibirGerarArquivos" value="<%=Pagina.NAO%>" 	<%=Pagina.isChecked(estrutura.getIndExibirGerarArquivos(), Pagina.NAO)%>>
											
						Não &nbsp;
				<input type="button" value="Limpar" onclick="javascript:limparRadioButtons(indExibirGerarArquivos)" id="btnGerarArquivo">
				<br>
				
			<%
			} else {
			%>
				<input type="checkbox" class="form_check_radio" name="indExibirGerarArquivos" value="<%=Pagina.SIM%>"
				<%=Pagina.isChecked(estrutura.getIndExibirGerarArquivos(), Pagina.SIM)%>
				<%=_pesqdisabled%>>
					
			<% 
			}
			%>
		
			
		</td>
	</tr>
	
	<tr>
		<td class="label">
			Tamanho Vertical da Listagem
		</td>
		<td>
			<input type="text" name="tamanhoListagemVerticalEtt" maxlength="4"
				size="6"
				value="<%=Pagina.trocaNull(estrutura.getTamanhoListagemVerticalEtt())%>"
				<%=_disabled%>>
			pixels
		</td>
	</tr>
	
	<tr>
		<td class="label" valign="top">
			Fun&ccedil;&otilde;es
		</td>
		<td>
			
		</td>
	</tr>
			<% 
			Iterator itFuncoesPermitidas = funcoesPermitidas.iterator();
			while (itFuncoesPermitidas.hasNext()) {
				FuncaoFun funcaoPermitida = (FuncaoFun) itFuncoesPermitidas.next();
				String checked = "";
				//if(!FuncaoDao.NOME_FUNCAO_FONTES_RECURSOS.equals(funcaoPermitida.getNomeFun())){
				if(lFuncoes != null){
		            Iterator itValue = lFuncoes.iterator();
		            while(itValue.hasNext()){
		                if( (itValue.next().toString()).equals(funcaoPermitida.getCodFun().toString())){
		                    checked = " checked ";        
		                }
		            }
		        }
		        		        
		        %>
		        		        
		        <tr>
		        	<td></td>
					
						<%
						
						String checkedPontosCriticos = checked;
						
						if (funcaoPermitida.equals(funcaoDadosGerais)){
						
						String checkedDadosGerais = "checked";
												
						if (ehPesquisa)
							checkedDadosGerais = "";											
						
						%>
						<td width="20%" valign="top">
							
							<input type="checkbox" class="form_check_radio" disabled="true" id = "dadosGerais" name="funcaoFun" value="<%=funcaoPermitida.getCodFun()%>" checked="true">
							<%=funcaoPermitida.getLabelPadraoFun()%>
							
							<input type="hidden" class="form_check_radio" id = "dadosGeraisHidden" name="funcaoFun" value="<%=funcaoPermitida.getCodFun()%>" <%=checkedDadosGerais %>>							
						<td>
						<td width="80%" valign="top">	
															
								<a href="javascript:exibirOcultarCampo('atributosDadosGerais');" name="exibirOcultarDadosGerais" id="exibirOcultarDadosGerais">Selecionar Campos</a>
						<%
						} else if (funcaoPermitida.equals(funcaoPontosCriticos)){							
							%>
							<td width="20%" valign="top">
								<input type="checkbox" class="form_check_radio" <%=_disabled%> id = "pontosCriticos" name="funcaoFun" onclick="javascript:alterarStatusAtributos('atributosAtb', 'pontosCriticos');" value="<%=funcaoPermitida.getCodFun()%>" <%=checkedPontosCriticos%>>
								<%=funcaoPermitida.getLabelPadraoFun()%>
								
								<input type="hidden" class="form_check_radio" id = "pontosCriticosHidden" name="pontosCriticosHidden" value="<%=checkedPontosCriticos%>">
							<td>
							<td width="80%" valign="top">	
																									
								<a href="javascript:exibirOcultarCampo('atributosPontosCriticos');" name="exibirOcultarPontosCriticos" id="exibirOcultarPontosCriticos">Selecionar Campos</a>
							<%
						}
						else{
						%>
							<td width="20%" valign="top">
								<input type="checkbox" class="form_check_radio" <%=_disabled%> name="funcaoFun" value="<%=funcaoPermitida.getCodFun()%>" <%=checked%>>
								<%=funcaoPermitida.getLabelPadraoFun()%>
							<td>
						<%
						}
						%>
						
					</td>
				</tr>
				<%
				
				String scriptsFuncao = "disabled";
				
				if (funcaoPermitida.equals(funcaoDadosGerais)){
				%>
					<script language="javascript">						
						
						funcoes = document.getElementsByName('funcaoFun');
						for (var i=0; i< funcoes.length; i++){	
							if (funcoes[i].id == 'dadosGerais'){															
								if (funcoes[i].checked){
									<%
									if (ehPesquisa || ehInclusao || ehAlteracao){
										scriptsFuncao = "";
									} else {
										scriptsFuncao = "disabled";
									}
									%>
								}								
							}
						}						
						
					</script>
															
					<tr id="atributosDadosGerais" style="display:none">
						<td></td>
						<td>
							<table align="left" border="0">
								<tr>
									<td width="5%">
									</td>
									<td width="95%" valign="top">
										<combo:CheckListTag id="dadosGerais" nome="atributosAtb"
										objeto="ecar.pojo.AtributosAtb" label="labelPadraoAtb"
										value="codAtb" order="labelPadraoAtb"
										colecao="<%=atributosPermitidosDadosGerais%>" scripts="<%=scriptsFuncao%>"
										selected="<%=lAtributosDadosGerais%>" />
									</td>
								</tr>
							</table>
						</td>
					</tr>

				<%
				} else if (funcaoPermitida.equals(funcaoPontosCriticos)){
				%>
					<script language="javascript">
						funcoes = document.getElementsByName('funcaoFun');
						for (var i=0; i< funcoes.length; i++){	
							if (funcoes[i].id == 'pontosCriticos'){								
								
								<%
									String scriptsFuncaoPontosCriticos = request.getParameter("pontosCriticosHidden");
								
									if (scriptsFuncaoPontosCriticos == null)
										scriptsFuncaoPontosCriticos = scriptsFuncao;
								
									if (!ehNavegacao && ( (lAtributosPontosCriticos != null && !lAtributosPontosCriticos.isEmpty()) || !scriptsFuncaoPontosCriticos.equals("")) ) {
										scriptsFuncao = "";
									} else {
										scriptsFuncao = "disabled";
									}
								
								%>
																								
							}
						}
					</script>
				
				
					
					<tr id="atributosPontosCriticos" style="display:none">
						<td></td>
						<td>
							<table align="left" border="0">
								<tr>
									<td width="5%">
									</td>
									<td width="95%" valign="top">
										<combo:CheckListTag id="pontosCriticos" nome="atributosAtb"
										objeto="ecar.pojo.AtributosAtb" label="labelPadraoAtb"
										value="codAtb" order="labelPadraoAtb"
										colecao="<%=atributosPermitidosPontosCriticos%>" scripts="<%=scriptsFuncao%>"
										selected="<%=lAtributosPontosCriticos%>" />
									</td>
								</tr>
							</table>
						</td>
					</tr>
				<%
				} 
				%>
					
				<%				
			//}
		}
			%>
		</td>
	</tr>
	<tr>
		<td class="label" valign="top">
			Fun&ccedil;&otilde;es de Acompanhamento
		</td>
		<td>
			<combo:CheckListTag nome="tipoFuncAcompTpfa"
				objeto="ecar.pojo.TipoFuncAcompTpfa" label="labelTpfa"
				value="codTpfa" order="labelTpfa" scripts="<%=_disabled%>"
				selected="<%=lFuncoesAcompanhamento%>" />
		</td>
	</tr>
	<tr>
		<td class="label"><%=_obrigatorio%>
			Ativo
		</td>
		<td>
			<util:comboSimNaoTag nome="indAtivoEtt"
				valorSelecionado="<%=estrutura.getIndAtivoEtt()%>"
				scripts="<%=_disabled%>" opcaoBranco="<%=_comboSimNao%>" />
		</td>
	</tr>

	<tr>
		<td class="label">
			&nbsp;
		</td>
	</tr>
	<%@ include file="/include/estadoMenu.jsp"%>
</table>
