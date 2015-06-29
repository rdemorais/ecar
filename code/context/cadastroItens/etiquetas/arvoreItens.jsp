
<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>
<%@page import="ecar.pojo.ConfiguracaoCfg"%>
<%@page import="ecar.dao.FuncaoDao"%>
<jsp:directive.page import="ecar.dao.EstruturaDao"/>
<%@page import="java.util.Iterator"%>
<%@page import="ecar.pojo.EstruturaEtt"%><jsp:directive.page import="ecar.pojo.ItemEstruturaIett"/>
<jsp:directive.page import="ecar.servlet.geraFilhosIettCadastro.ItemEstruturaCadastroHtml"/>

<%@page import="comum.util.Pagina"%>
<%@page import="ecar.dao.ItemEstruturaDao"%>

<script language="javascript">

//chama a consulta dos itens que pertencem a estruturas reais
function aoClicarConsultarItem(form, codIett, idLinha){
	form.hidAcao.value = 'consultar';
	form.codIett.value = codIett;
	form.ultimoIdLinhaExpandido.value = idLinha;
	form.ultimoIdLinhaDetalhado.value = idLinha;
	form.action = "<%=_pathEcar%>/cadastroItens/dadosGerais/frm_con.jsp?codAba=<%=new FuncaoDao(request).getCodFuncaoDadosGerais()%>";
	form.submit();
}

//chama a consulta dos itens associados a estrutura virtual
function aoClicarConsultarItemEstruturaVirtual(form, codIett, idLinha){
	form.hidAcao.value = 'consultar';
	form.codIett.value = codIett;
	form.ultimoIdLinhaExpandido.value = idLinha;
	form.ultimoIdLinhaDetalhado.value = idLinha;
	form.action = "<%=_pathEcar%>/cadastroItens/dadosGerais/frm_con.jsp?codAba=<%=new FuncaoDao(request).getCodFuncaoDadosGerais()%>&ultimoIdLinhaDetalhado=" + idLinha;
	form.submit();
}

function aoClicarDetalharItem(form, idLinha){
	if(idLinha != ''){
		form.hidFuncaoAjaxSelecionada.value = "detalharArvoreItens";
		carregarFilhosAjax(idLinha);
	}
}


function aoClicarExpandirItem(form, idLinha){
	if(idLinha != ''){	
		form.hidFuncaoAjaxSelecionada.value = "expandirArvoreItens"; 
		form.ultimoIdLinhaExpandido.value = idLinha;
		carregarFilhosAjax(idLinha);
	}	
}


</script>


<% 

String ultimoIdLinhaDetalhado = Pagina.getParamStr(request, "ultimoIdLinhaDetalhado");
String ultimoIdLinhaExpandido = Pagina.getParamStr(request, "ultimoIdLinhaExpandido");
ItemEstruturaDao itemDaoArvoreItens = new ItemEstruturaDao(request);


//Tenta recuperar o estado da árvore pelo "ultimoIdLinhaDetalhado", se não conseguir tenta pelo "codIettPrincipal" e "ultEttSelecionado"
if(ultimoIdLinhaDetalhado.equals("")){
	String codIettPrincipalStr = Pagina.getParamStr(request, "codIettPrincipal");
	String ultEttSelecionado = "";
	ultEttSelecionado = Pagina.getParamStr(request, "ultEttSelecionado");
		
	if(ultEttSelecionado != null && !ultEttSelecionado.equals("")) {
		
		if(codIettPrincipalStr!= null && !codIettPrincipalStr.equals("") && !codIettPrincipalStr.equals("0")) {
			ultimoIdLinhaDetalhado =  "ett" + ultEttSelecionado + "_pai_iett" + codIettPrincipalStr;
		} else {
			ultimoIdLinhaDetalhado =  "ett" + ultEttSelecionado + "_pai_iett";
		}
	} else {
		//Esse caso acontece quando vier da pagina de cadastro e clicar em ir para listagem do proximo nivel
		// deve mostrar as pastas com todas as estruturas filhas que fazem parte do proximo nível
		if(codIettPrincipalStr!= null && !codIettPrincipalStr.equals("")) {
			ItemEstruturaIett itemEstruturaSelecionado = (ItemEstruturaIett) itemDaoArvoreItens.buscar(ItemEstruturaIett.class, new Long(codIettPrincipalStr));
			if(itemEstruturaSelecionado != null) {	
				ultimoIdLinhaDetalhado =  "iett" + codIettPrincipalStr  + "_pai_ett" + itemEstruturaSelecionado.getEstruturaEtt().getCodEtt();
			}	
		}
	
	}
}


%>	

	<input type="hidden" name="hidFuncaoAjaxSelecionada" id="hidFuncaoAjaxSelecionada" value="">
	<%if (!ehTelaListagem){
		%>
		<input type="hidden" name="ultimoIdLinhaDetalhado" id="ultimoIdLinhaDetalhado" value="<%=ultimoIdLinhaDetalhado%>">	
	<%}%>
	
	<input type="hidden" name="ultimoIdLinhaExpandido" id="ultimoIdLinhaExpandido" value="<%=Pagina.getParamStr(request,"ultimoIdLinhaExpandido")%>">
	<!-- Menu Cadastro -->
	
	<%
	
	if (configuracaoCfg.getIndExibirArvoreNavegacaoCfg() != null && configuracaoCfg.getIndExibirArvoreNavegacaoCfg().equals("S")){
	
	%>
	
	
	<div id="menuCadastro">	
	  <table border="0" cellpadding="0" cellspacing="0">
	    <tr>
	      <div id="conteudoCadastroPasta">		
	      <tr><td nowrap> 
				<!-- 
				*	A árvore de cadastro de itens foi implementada como uma "table" de HTML, onde cada linha 
				* da tabela representa um item ou uma estrutura. As linhas(<tr>) possuem um identificador(id)   
				* que obedece o seguinte formato:
				* - ett<codEtt>_pai_iett<codIett>	->	representa uma estrutura com o seu respectivo pai iett
				* - iett<codIett>_pai_ett<codEtt>	->	representa um itemEstrutura com o seu respectivo pai ett
				* - ett<codEtt>_pai_ett<codEtt>_avo_<codIett>	->	representa uma estrutura "filha" com a sua respectiva estrutura virtual pai ett 
				*                                       			e com o seu respectivo avo iett (pai da estrutura virtual) 
				* OBS 1: Como a estrutura virtual pode ser filha de qualquer estrutura, a mesma estrutura virtual pode estar abaixo de dois itens diferentes.  
				*
				* OBS 2: Inicialmente, a tela de listagem de cadastro carrega as estruturas raiz, ou seja as estruturas
				* que não possuem pai. 
				*
				 -->

				<!-- Tabela árvore formada de estruturas e itemEstruturas -->
				<table id="tabelaItens" width="100%" cellspacing="0" cellpadding="0" border="0">

<%			
				//Calcula o caminho que deve ser expandido na árvore														
				List caminho = new ArrayList();
				String codItemInferior = "";
				String codItemSuperior = "";
				String codPaiEstrutura = "";
				String codEstruturaFilhaVirtualExpandida = "";
				
							
				if(!ultimoIdLinhaDetalhado.equals("")){
					
					String idLinhaVirtual = ultimoIdLinhaDetalhado;
					
					
					// se vier na forma ettXX_pai_ettYY_avo_ZZ -> monta na forma aceita ettYY_pai_iettZZ
					if(idLinhaVirtual.contains("_avo_")) {
						String codEttLinha = idLinhaVirtual.substring(idLinhaVirtual.indexOf("_ett") + 4, idLinhaVirtual.indexOf("_avo_"));
						String codIettLinha = idLinhaVirtual.substring(idLinhaVirtual.indexOf("_avo_")+5, idLinhaVirtual.length());
						codEstruturaFilhaVirtualExpandida = idLinhaVirtual.substring(3, idLinhaVirtual.indexOf("_pai_"));
						idLinhaVirtual = "ett" + codEttLinha + "_pai_iett" + codIettLinha; 
					
					} else if(ultimoIdLinhaExpandido != null && ultimoIdLinhaExpandido.contains("_avo_")) {
						codEstruturaFilhaVirtualExpandida = ultimoIdLinhaExpandido.substring(3, ultimoIdLinhaExpandido.indexOf("_pai_"));
					}
							
					codItemInferior = idLinhaVirtual.substring(0, idLinhaVirtual.indexOf("_pai_"));
					codPaiEstrutura = idLinhaVirtual.substring(idLinhaVirtual.indexOf("_pai_")+5, idLinhaVirtual.length());
							
					//descobre a qual estrutura raiz o item inferior pertence
					if(codItemInferior.startsWith("iett")){
						String codIett = codItemInferior.substring(4,codItemInferior.length());
						ItemEstruturaIett item = (ItemEstruturaIett) itemDaoArvoreItens.buscar(ItemEstruturaIett.class, new Long(codIett));
						while(item.getItemEstruturaIett() != null){
							item = item.getItemEstruturaIett();
						}
						codItemSuperior = "ett" + item.getEstruturaEtt().getCodEtt().toString();
					}
					else if (codItemInferior.startsWith("ett")){
						if(!codPaiEstrutura.equals("iett")){
							String codIett = codPaiEstrutura.substring(4,codPaiEstrutura.length());
							ItemEstruturaIett item = (ItemEstruturaIett) itemDaoArvoreItens.buscar(ItemEstruturaIett.class, new Long(codIett));
							while(item.getItemEstruturaIett() != null){
								item = item.getItemEstruturaIett();
							}						
							codItemSuperior = "ett" + item.getEstruturaEtt().getCodEtt().toString();
						} 
						else	{
							codItemSuperior = codItemInferior;	
						}						
					}
					
					//gera o caminho
					if("".equals(Pagina.getParamStr(request, "recarregarArvore"))){
						caminho = itemDaoArvoreItens.geraCaminhoArvoreCadastro(codItemInferior, codItemSuperior, codPaiEstrutura, codEstruturaFilhaVirtualExpandida);
					}
					
						
				}
				
				List lEstruturasRaiz = estruturaDaoArvoreItens.getEstruturaPrincipal(); //lista das estruturas raizes		
				
				//Imprime as estruturas Raizes
				Iterator itEstruturaRaiz = lEstruturasRaiz.iterator();
				boolean primeiraEstrutura = true;
				while (itEstruturaRaiz.hasNext()){	
					
					EstruturaEtt estruturaRaiz = (EstruturaEtt) itEstruturaRaiz.next();
					
					if (primeiraEstrutura){
						estruturaEttSelecionada = estruturaRaiz;
						primeiraEstrutura = false;
					}
					
					//label de um item na árvore de cadastro
					String nomeItem = "";	
					
					//Gera o nome do item
					if(estruturaRaiz.getLabelEtt() != null && !"".equals(estruturaRaiz.getLabelEtt())){
						nomeItem = estruturaRaiz.getLabelEtt(); 
					}
					else {
						nomeItem = estruturaRaiz.getNomeEtt(); 
					} 
															
					String idLinha = "ett" + estruturaRaiz.getCodEtt() + "_pai_iett";
					out.println(ItemEstruturaCadastroHtml.geraFilhosHtml(idLinha, caminho, request, ultimoIdLinhaDetalhado,ehTelaListagem, ""));
					
				} %>
				</table>	
			<!-- fim árvore -->
						      
		      </td>
		      </tr>
		      </div> <!-- Fim do div conteudoCadastroPasta -->
		    </tr>
		  </table>
		  
		</div> <!-- Fim do div menuCadastro -->
			<!-- Guarda os hiddens com os filhos de cada nó. -->
		<div id="hiddenFilhosExibir">
		</div>
	
	<%} // Fim do if getIndExibirArvoreNavegacaoCfg%>		