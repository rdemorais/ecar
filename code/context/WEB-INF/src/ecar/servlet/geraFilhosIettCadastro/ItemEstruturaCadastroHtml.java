package ecar.servlet.geraFilhosIettCadastro;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import comum.util.Pagina;
import comum.util.Util;

import ecar.action.ActionEstrutura;
import ecar.bean.AtributoEstruturaListagemItens;
import ecar.dao.ConfiguracaoDao;
import ecar.dao.EstruturaDao;
import ecar.dao.FuncaoDao;
import ecar.dao.ItemEstruturaDao;
import ecar.exception.ECARException;
import ecar.login.SegurancaECAR;
import ecar.permissao.ValidaPermissao;
import ecar.pojo.ConfiguracaoCfg;
import ecar.pojo.EstruturaAtributoEttat;
import ecar.pojo.EstruturaEtt;
import ecar.pojo.ItemEstruturaIett;
import ecar.pojo.ItemEstruturaSisAtributoIettSatb;
import ecar.pojo.ItemEstruturaSisAtributoIettSatbPK;
import ecar.pojo.ObjetoEstrutura;
import ecar.pojo.SisAtributoSatb;
import ecar.taglib.util.Input;
import ecar.util.Dominios;

/**
 * @author  
 * @since 04/11/2008
 */

public class ItemEstruturaCadastroHtml {
	
	private ItemEstruturaIett itemEstruturaSelecionado;
	private EstruturaEtt estruturaSelecionada;
	/** guarda a estrutura virtual que é "pai" da estrutura "filha" que foi selecionada ou expandida **/
	private EstruturaEtt estruturaPai;
	private HttpServletRequest request;
	//verifica se a árvore é montada na tela de listagem
	private static boolean ehTelaListagem;
		
        /**
         *
         * @param itemEstruturaSelecionado
         * @param estruturaSelecionada
         * @param request
         */
        public ItemEstruturaCadastroHtml(ItemEstruturaIett itemEstruturaSelecionado,
			EstruturaEtt estruturaSelecionada, HttpServletRequest request) {
		this.itemEstruturaSelecionado = itemEstruturaSelecionado;
		this.estruturaSelecionada = estruturaSelecionada;
		this.request = request;
	}
	
        /**
         *
         * @param itemEstruturaSelecionado
         * @param estruturaSelecionada
         * @param request
         * @param estruturaPai
         */
        public ItemEstruturaCadastroHtml(ItemEstruturaIett itemEstruturaSelecionado,
			EstruturaEtt estruturaSelecionada, HttpServletRequest request, EstruturaEtt estruturaPai) {
		this.itemEstruturaSelecionado = itemEstruturaSelecionado;
		this.estruturaSelecionada = estruturaSelecionada;
		this.estruturaPai = estruturaPai;
		this.request = request;
	}
	
	/**
	 * Funçao que gera o objeto JSON para o Ajax da página de cadastro. 
	 * Esse objeto pode assumir dois formatos de dados: um para a expansão
	 * da árvore, outro para o detalhamento do item. 
	 * @param tipoItemClicado
	 * @param funcaoAjaxSelecionada
         * @param ehTelaListagem
         * @return
	 */
    @SuppressWarnings("static-access")
	public String geraObjetoJSON(String tipoItemClicado, String funcaoAjaxSelecionada, String ehTelaListagem){
		String objetoJSON="";
		
		//variável de instancia recebe boolean "true" se o parâmetro ehTelaListagem for não nulo e igual a "S"
		//caso contrário recebe false
		ItemEstruturaCadastroHtml.ehTelaListagem = ehTelaListagem != null && ehTelaListagem.equals("S") ? true : false;
				
		//Retorna os filhos, uma tabela,  para serem usados no detalhamento da árvore
		if(funcaoAjaxSelecionada.equals("detalharArvoreItens")){
			objetoJSON = geraDetalharArvoreItensJSON(tipoItemClicado);
		}		
		//Retorna os filhos, linhas de uma tabela, que preencherão a árvore de navegação de itens. 
		else if(funcaoAjaxSelecionada.equals("expandirArvoreItens")){
			objetoJSON = geraExpandirArvoreItensJSON(tipoItemClicado);
		}
			 		
		return objetoJSON;
	}
	
	/**
	 * Gera um objeto JSON, que encapsula os filhos de um determinado item da tabela.
	 * Retorna os filhos, linhas de uma tabela, que preencherão a árvore de navegação de itens.
         * @param tipoItemClicado
         * @return
	 */
	public String geraExpandirArvoreItensJSON(String tipoItemClicado) {
		String objetoJSON="";
		StringBuffer strBuffer = new StringBuffer();
		
		//Remonta o identificador da linha pai
		String idLinhaPai = recuperaIdentificadorLinhaPai(tipoItemClicado);
		
		try {									
			//Verifica se a sessão está ativa, caso não esteja levanta uma exceção 
			if(request.getSession().getAttribute("seguranca") == null){
				throw new ECARException();
			}
			
			//Chama o método de acordo com o item clicado
			String linhas = "";
			if(tipoItemClicado.equals("estrutura")){
				if(estruturaPai == null) {
					linhas = criaLinhasIettArvore();
				} else { 
					//se for uma chamada a uma estrutura "filha" de uma estrutura virtual (uso pratico apenas na montagem da arvore)
					linhas = criaLinhasIettVirtualArvore();
					idLinhaPai += "_avo_";
					if(this.itemEstruturaSelecionado != null)
						idLinhaPai +=  this.itemEstruturaSelecionado.getCodIett();
				}
			}
			else if(tipoItemClicado.equals("itemEstrutura")){
				linhas = criaLinhasEttArvore();
			} 
			else if(tipoItemClicado.equals("estruturaVirtual")){			
				linhas = criaLinhasEttVirtualArvore();
			}
				
			strBuffer.append("{");
			strBuffer.append("\"indicaErro\":\"false\",");
			strBuffer.append("\"idTabela\":\"tabelaItens\",");
			strBuffer.append("\"idLinhaPai\":\"" + idLinhaPai + "\",");		
			strBuffer.append("\"linhas\":[");
			strBuffer.append(		linhas );
			strBuffer.append(	"]");
	    	strBuffer.append("}");
	    	objetoJSON = strBuffer.toString();
			
		} 		
		catch (Exception e) {
			Logger.getLogger(this.getClass()).error(e);
        	e.printStackTrace();
        	//Em caso de erro, o retorno será um objeto JSON de erro.
        	objetoJSON = "{";
        	objetoJSON += "\"indicaErro\":\"true\",";        	
        	objetoJSON += "\"funcaoAjaxSelecionada\":\"expandirArvoreItens\",";
        	objetoJSON += "\"idLinhaPai\":\"" + idLinhaPai +"\"";
        	objetoJSON += "}";
		}
			    	
		return objetoJSON;
	}
	
    /**
     * Gera um objeto JSON, que encapsula os filhos de um determinado item da tabela. 
     * Retorna os filhos, uma tabela,  para serem usados no detalhamento da árvore.
     * @param tipoItemClicado
     * @return
     */
	public String geraDetalharArvoreItensJSON(String tipoItemClicado){
    	String objetoJSON = "";
		StringBuffer strBuffer = new StringBuffer();
		
		try{
		
		
			// a estrutura virtual será tratada como estrutura na hora de detalhar a árvore
			if(tipoItemClicado.equals("estruturaVirtual"))
				tipoItemClicado = "estrutura";
			
			//Remonta o identificador da linha pai
			String idLinhaPai = recuperaIdentificadorLinhaPai(tipoItemClicado);
			
			//Verifica se a sessão está ativa, caso não esteja levanta uma exceção 
			if(request.getSession().getAttribute("seguranca") == null){
				throw new ECARException();
			}
			
			strBuffer.append("{");
			strBuffer.append("\"indicaErro\":\"false\",");
			strBuffer.append("\"idLinhaPai\":\"" + idLinhaPai + "\",");	
			strBuffer.append(	criaTabelaAbasEstrutura(tipoItemClicado) + ",");
			strBuffer.append(	criaTabelaDetalharArvoreItens(tipoItemClicado) + ",");
	    	strBuffer.append(	criaTabelaArvoreLocalizacao(tipoItemClicado) + ",");
	    	strBuffer.append(	criaBotoesHTML(tipoItemClicado) );
	    	strBuffer.append("}");
			objetoJSON = strBuffer.toString();			
		}		        
		catch (Exception e) {
			Logger.getLogger(this.getClass()).error(e);
        	e.printStackTrace();
        	//Em caso de erro, o retorno será um objeto JSON de erro.
        	objetoJSON = "{";
        	objetoJSON += "\"indicaErro\":\"true\",";        	
        	objetoJSON += "\"funcaoAjaxSelecionada\":\"detalharArvoreItens\",";
        	objetoJSON += "\"idInsercao\":\"detalharArvoreItens\",";
        	objetoJSON += "\"conteudo\":\"Falha!\"";
        	objetoJSON += "}";
		}
		
        return objetoJSON;
    }
		
/**
	 * Identifica o objeto clicado e detalha a estrutura ou o item selecionado na árvore de menu. 
 * @param tipoItemClicado
 * @return
	 */
	public String criaTabelaDetalharArvoreItens(String tipoItemClicado){
		String strRetorno="";
		StringBuffer strBuffer = new StringBuffer();
		try {
			
		
			if(tipoItemClicado.equals("estrutura")){
				strBuffer.append("\"tabelaDetalharArvoreItens\":");
				if(estruturaSelecionada.isVirtual() || (estruturaPai != null && estruturaPai.isVirtual())){
					strBuffer.append("{\"idInsercao\":\"detalharArvoreItens\", \"conteudo\": \"" + geraConteudoVirtualDetalharArvoreItens(tipoItemClicado) + "\"}");
				}else{
					strBuffer.append("{\"idInsercao\":\"detalharArvoreItens\", \"conteudo\": \"" + geraConteudoIettDetalharArvoreItens(tipoItemClicado) + "\"}");	
				}						
			}
			else if(tipoItemClicado.equals("itemEstrutura")){
				strBuffer.append("\"tabelaDetalharArvoreItens\":");
				if (estruturaSelecionada.isVirtual() || (estruturaPai != null && estruturaPai.isVirtual())){
					strBuffer.append("{\"idInsercao\":\"detalharArvoreItens\", \"conteudo\": \"" + geraConteudoVirtualDetalharArvoreItens(tipoItemClicado) + "\"}");
				} else {
					strBuffer.append("{\"idInsercao\":\"detalharArvoreItens\", \"conteudo\": \"" + geraConteudoIettDetalharArvoreItens(tipoItemClicado) + "\"}");
				}
										
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		strRetorno = strBuffer.toString();
		
		return strRetorno;
	}
	
	
	/**
	 * Gera a linha do item para estrutura virtual
	 * @param tipoItemClicado
	 * @return
	 */
	public String geraConteudoVirtualDetalharArvoreItens(String tipoItemClicado){
		String strRetorno="";
		StringBuffer strBuffer = new StringBuffer();
		ItemEstruturaDao itemDao = new ItemEstruturaDao(null);
		EstruturaEtt estruturaVirtual = null;
		
		try {
			EstruturaDao estruturaDao = new EstruturaDao(null);
			List lColunas = estruturaDao.getAtributosAcessoEstruturaArvore(estruturaSelecionada);
			SegurancaECAR seguranca = (SegurancaECAR) request.getSession().getAttribute("seguranca");
			ValidaPermissao validaPermissao = new ValidaPermissao();
			
			String radConcluido = "";
			if("".equals(Pagina.getParamStr(request, "radConcluido"))){
				radConcluido = (String) request.getSession().getAttribute("radConcluidoSession");
			} else{
				radConcluido = Pagina.getParamStr(request, "radConcluido");
			}
			
			Map mapItensEstruturaVirtual = null;

			// verifica se a estrutura virtual é o pai ou é a selecionada	
			if(estruturaPai != null && estruturaPai.isVirtual()) {
				estruturaVirtual = estruturaPai;
			} else if (estruturaSelecionada.isVirtual()) {
				estruturaVirtual = estruturaSelecionada;

			}
			
			ActionEstrutura action = new ActionEstrutura();
			//ja verifica itens e estruturas permissões
			mapItensEstruturaVirtual = action.montarMapItensEstruturaVirtualComPermissao(estruturaVirtual, seguranca);
			
			
			strBuffer.append("<table border=\\\"0\\\" cellpadding=\\\"0\\\" cellspacing=\\\"0\\\" width=\\\"100%\\\">");
			strBuffer.append(	"<tr>");
			strBuffer.append(		"<td class=\\\"espacadorestrutura\\\" colspan=\\\"" + lColunas.size() + 3+ "\\\"><img src=\\\"../../images/pixel.gif\\\"></td>");
			strBuffer.append(	"</tr>");
			strBuffer.append(	"<tr>");
			strBuffer.append(		"<td>");
			strBuffer.append(			"<!-- Obtem a Lista Estruturas que funcionam como Key no Map -->");
			
			Iterator itEstruturas = mapItensEstruturaVirtual.keySet().iterator();
			while(itEstruturas.hasNext()){
				EstruturaEtt estrutura = (EstruturaEtt) itEstruturas.next();
				strBuffer.append("<!-- Imprime o cabeçalho com o nome da estrutura -->");
				strBuffer.append("<table border=\\\"0\\\" cellpadding=\\\"0\\\" cellspacing=\\\"0\\\" width=\\\"100%\\\" >");
				
				// Obtem a Lista de colunas da estrutura a serem exibidas
				List listaColunas = estruturaDao.getAtributosAcessoEstruturaArvore(estrutura);
				Iterator itCol = listaColunas.iterator();
				
				strBuffer.append(	"<!-- Linha com o nome da estrutura -->");
				strBuffer.append(	"<tr  class=\\\"linha_titulo_estrutura\\\" bgcolor=\\\"" + estrutura.getCodCor1Ett() + "\\\" >");
				strBuffer.append(		"<td colspan=\\\"" + listaColunas.size()+2 + "\\\">" + estrutura.getNomeEtt() + " </td>");
				strBuffer.append(	"</tr>");
				strBuffer.append("</table>");
				strBuffer.append(	"<!-- Fim da Linha com o nome da estrutura -->");
				
				strBuffer.append("<table class=\\\"sortable\\\" border=\\\"0\\\" cellpadding=\\\"0\\\" cellspacing=\\\"0\\\" width=\\\"100%\\\" id=\\\"tabelaOrdenada\\\" >");
				strBuffer.append(	"<thead>");				
				strBuffer.append(	"<!-- Linha com o nome das colunas visíveis da estrutura -->");
				strBuffer.append(	"<tr class=\\\"linha_subtitulo_estrutura\\\" bgcolor=\\\"" + estrutura.getCodCor2Ett() + "\\\">");
				strBuffer.append(		"<td class=\\\"sorttable_nosort\\\" width=\\\"3%\\\">&nbsp;</td>");
				
				if (!listaColunas.isEmpty()) { 
					while (itCol.hasNext()) {
						ObjetoEstrutura col = (ObjetoEstrutura)itCol.next();
						strBuffer.append("<td width=\\\"" + col.iGetLargura() + "% \\\"> "+col.iGetLabel() +" </td>");
					}
				} else{
					strBuffer.append(	"<td width=\\\"97%\\\"> &nbsp;</td>");
				}				
				strBuffer.append(	"</tr>");
				strBuffer.append(	"<!-- Fim da Linha com o nome das colunas visíveis da estrutura -->");
				
				strBuffer.append(	"</thead>");
				
				strBuffer.append(	"<!-- Linha com os dados de acordo com as colunas visiveis da estrutura -->");
				strBuffer.append(	"<tbody>");
				
				//este metodo vai buscar os itens associados agrupados por estrutura real e ordenados pela primeira coluna
				List listItemEstrutura=  itemDao.getItensAssociados(estrutura, listaColunas, estruturaVirtual, seguranca);
				listItemEstrutura = itemDao.getItensIndConclusao(listItemEstrutura, radConcluido);
				
				Iterator itItemEstrutura = listItemEstrutura.iterator();
				while(itItemEstrutura.hasNext()){
					ItemEstruturaIett itemEstrutura = (ItemEstruturaIett) itItemEstrutura.next();
			
				
						//monta o codigo da estrutura "filha" expandida
						String codExpandido = "ett" + estrutura.getCodEtt().toString() + "_pai_ett" + estruturaVirtual.getCodEtt().toString() + "_avo_";
						if(itemEstruturaSelecionado != null)	
								codExpandido += itemEstruturaSelecionado.getCodIett().toString(); 
						
							strBuffer.append("<tr class=\\\"linha_subtitulo2_estrutura\\\" bgcolor=\\\"" + estrutura.getCodCor3Ett()+ "\\\">");
							strBuffer.append("<td sorttable_customkey=\\\"0\\\" width=\\\"5%\\\"><input type=\\\"checkbox\\\" name=\\\"itemSelecionado\\\" value=\\\"" +
									itemEstrutura.getCodIett() + "\\\"></td>");
							if (!listaColunas.isEmpty()) {
						    	Iterator itColItens = listaColunas.iterator();
						    	boolean primeiraColuna =true;
						    	while (itColItens.hasNext()) {// Itera nas colunas para obter os valores dos itens correspondetes a coluna
						    		ObjetoEstrutura col = (ObjetoEstrutura)itColItens.next();
						    		strBuffer.append("<!-- Imprime os itens da estrutura -->");
						    		strBuffer.append("<td width=\\\"" + col.iGetLargura() + "%\\\" aliasSortableIndex >");
						    		String[] strArray = new String[2];					    					    		
						    		
						    		
						    		if("nivelPlanejamento".equals(col.iGetNome())){
										String niveis = "";
								    	if(itemEstrutura.getItemEstruturaNivelIettns() != null && !itemEstrutura.getItemEstruturaNivelIettns().isEmpty()){
									    	Iterator itNiveis = itemEstrutura.getItemEstruturaNivelIettns().iterator();
									    	while(itNiveis.hasNext()){
									    		SisAtributoSatb nivel = (SisAtributoSatb) itNiveis.next();
									    		niveis += nivel.getDescricaoSatb() + "; ";
									    	}
									    	niveis = niveis.substring(0, niveis.lastIndexOf(";"));
								    	}
								   	 	strArray[0] = Dominios.STRING_VAZIA;
								   	 	strArray[1] = Dominios.STRING_VAZIA;
								    	 strBuffer.append("<a href=\\\"javascript:aoClicarConsultarItemEstruturaVirtual(document.form, "+ itemEstrutura.getCodIett() + ", '" + codExpandido +  "');\\\">"
						    					  + "<font color=\\\"" + estrutura.getCodCor4Ett() + "\\\">" +
						    					  niveis
						    					  + "</font>"
						    					  + " </a>");
										
									} else {

							    		if (col.iGetGrupoAtributosLivres() != null)  {//Verifica se a coluna é atributo livre 
							    			strArray = montaConteudoAtributoLivre(itemEstrutura,col,itemDao);
							    			String conteudo = strArray[0];
							    	
						    				strBuffer.append("<a href=\\\"javascript:aoClicarConsultarItemEstruturaVirtual(document.form, " + itemEstrutura.getCodIett() + ", '" + codExpandido + "');\\\"> "+
						    							"<font color=\\\"" + estrutura.getCodCor4Ett() + "\\\">" +
						    							conteudo
						    							+ "</font>"
						    							+" </a>");
						    			 } else { //Quando a coluna não é atributo livre
						    				 strArray[0] = Dominios.STRING_VAZIA;
						    				 strArray[1] = Dominios.STRING_VAZIA;
						    				 String valor = col.iGetValor(itemEstrutura).replace("\n", " ").replace("\t", " ").replace("\r", " ");
						    				 strBuffer.append("<a href=\\\"javascript:aoClicarConsultarItemEstruturaVirtual(document.form, "+ itemEstrutura.getCodIett() + ", '" + codExpandido +  "');\\\">"
						    					  + "<font color=\\\"" + estrutura.getCodCor4Ett() + "\\\">" +
						    					  valor
						    					  + "</font>"
						    					  + " </a>");
						    			 }
									}
						    								    								    		
						 			strBuffer.append("</td>");
						    		primeiraColuna = false;
						    		
									//troca o alias aliasSortableIndex inserido na TD por branco se o atributo livre não for do tipo ID e pela string sorttable_customkey="x" onde x é o valor do sequencial do atributo livre do tipo ID  
									strBuffer = inserirSortableColumn (strBuffer,strArray[1]);
						    		
						    	}
							} else{
								strBuffer.append("<!-- Trata quando a estrtura não possui colunas associadas para serem visíveis -->");
								strBuffer.append("<td><font color=\\\"" + estrutura.getCodCor4Ett() + "\\\"><a href=\\\"javascript:aoClicarConsultarItemEstruturaVirtual(document.form," +
										itemEstrutura.getCodIett() + ", '" + codExpandido +  "');\\\"> " + itemEstrutura.getNomeIett() + "</a></font></td>");
							}					
							strBuffer.append("</tr>");
					//	}
					//}
				}
				strBuffer.append(	"</tbody>");
				strBuffer.append(	"<!-- Fim da Linha com os dados de acordo com as colunas visiveis da estrutura -->");
				strBuffer.append("</table>");				
				
			}//fim do while de estruturas

			strBuffer.append(		"<td>");
			strBuffer.append(	"</tr>");
			strBuffer.append("</table>");
			
			if(this.itemEstruturaSelecionado != null){
				strBuffer.append("<input type=hidden name=\\\"codIettPrincipal\\\" id=\\\"codIettPrincipal\\\" value=\\\"" +
						this.itemEstruturaSelecionado.getCodIett().toString() + "\\\">");	
			}						
			
			if(estruturaVirtual != null) {
				strBuffer.append("<input type=hidden name=\\\"codEttSelecionado\\\" id=\\\"codEttSelecionado\\\" value=\\\"" +
						estruturaVirtual.getCodEtt().toString() + "\\\">");
				strBuffer.append("<input type=hidden name=\\\"ultEttSelecionado\\\" id=\\\"ultEttSelecionado\\\" value=\\\"" +
						estruturaVirtual.getCodEtt().toString() + "\\\">");
			}
			
			//utilizado para guardar o último item que foi detalhado na árvore de cadastro
			strBuffer.append("<input type=hidden name=\\\"ultimoIdLinhaDetalhado\\\" id=\\\"ultimoIdLinhaDetalhado\\\" value=\\\"" +
					this.recuperaIdentificadorLinhaPai("estruturaVirtual") + "\\\">");
		
			strRetorno = strBuffer.toString();
			
		} catch (Exception e) {
			strRetorno = "Erro de processamento";
		}
				
		return strRetorno;
	}
	
	/**
	 * 
	 * @param item
	 * @param coluna
	 * @return
	 * @throws ECARException 
	 */
	private String[] montaConteudoAtributoLivre (ItemEstruturaIett item,ObjetoEstrutura coluna,ItemEstruturaDao itemDao) throws ECARException{
		
		String informacaoIettSatb = "";
		String strAliasIndex ="";
		
		ConfiguracaoCfg configuracao = new ConfiguracaoCfg();
		List confg = new ConfiguracaoDao(request).listar(ConfiguracaoCfg.class, null);					
		if(confg != null && confg.size() > 0)
			configuracao = (ConfiguracaoCfg) confg.iterator().next();
		
		Iterator itIettSatbs =  item.getItemEstruturaSisAtributoIettSatbs().iterator();
		while (itIettSatbs.hasNext()) {
			ItemEstruturaSisAtributoIettSatb itemEstruturaSisAtributoIettSatb = (ItemEstruturaSisAtributoIettSatb) itIettSatbs.next();
			
				if (itemEstruturaSisAtributoIettSatb.getSisAtributoSatb().getSisGrupoAtributoSga().equals(coluna.iGetGrupoAtributosLivres())){
					if (coluna.iGetGrupoAtributosLivres().getSisTipoExibicGrupoSteg().getCodSteg().equals(new Long(Input.TEXT)) ||
					 	coluna.iGetGrupoAtributosLivres().getSisTipoExibicGrupoSteg().getCodSteg().equals(new Long(Input.TEXTAREA)) ||
					 	coluna.iGetGrupoAtributosLivres().getSisTipoExibicGrupoSteg().getCodSteg().equals(new Long(Input.MULTITEXTO)) ||
					 	coluna.iGetGrupoAtributosLivres().getSisTipoExibicGrupoSteg().getCodSteg().equals(new Long(Input.VALIDACAO)) ) {
					 
						//Se for um atributo livre do tipo ID
						SisAtributoSatb sisAtributo = itemEstruturaSisAtributoIettSatb.getSisAtributoSatb();
						
						if (sisAtributo.isAtributoAutoIcremental() || sisAtributo.isAtributoContemMascara()) {
							
							strAliasIndex = "sorttable_customkey=\\\""+itemDao.obterTipoSequencial(itemEstruturaSisAtributoIettSatb).getConteudo()+"\\\"";
						} 
						
						informacaoIettSatb = informacaoIettSatb + itemEstruturaSisAtributoIettSatb.getInformacao() + Util.trocaBarra(configuracao.getSeparadorCampoMultivalor());
					
					} else if (!coluna.iGetGrupoAtributosLivres().getSisTipoExibicGrupoSteg().getCodSteg().equals(new Long(Input.IMAGEM))) {
						//se for do tipo imagem não faz nada, deixa em branco.
						informacaoIettSatb = informacaoIettSatb + itemEstruturaSisAtributoIettSatb.getSisAtributoSatb().getDescricaoSatb() + "; ";
					} 
				} 
			}
			if (informacaoIettSatb.length() > 0){
				informacaoIettSatb = informacaoIettSatb.substring(0, informacaoIettSatb.length() - 2); 
			}

			String[] strArray = new String[2]; 
		
			strArray[0] = informacaoIettSatb;
			strArray[1] = strAliasIndex;
			
		return strArray;
	}
	
	/**
	 * Gera a linha do item para uma estrutura real.
	 * @param tipoItemClicado
	 * @return
	 */
    @SuppressWarnings("static-access")
	public String geraConteudoIettDetalharArvoreItens(String tipoItemClicado) {
		String strRetorno="";
		StringBuffer strBuffer = new StringBuffer();

		try {

			ObjetoEstrutura coluna;	
			SegurancaECAR seguranca = (SegurancaECAR) request.getSession().getAttribute("seguranca");
			EstruturaDao estruturaDao = new EstruturaDao(null);
			ItemEstruturaDao itemDao = new ItemEstruturaDao(null);
			ConfiguracaoDao configuracaoDao = new ConfiguracaoDao(null);
			ConfiguracaoCfg configuracao = new ConfiguracaoCfg();
			List confg = configuracaoDao.listar(ConfiguracaoCfg.class, null);					
			if(confg != null && confg.size() > 0)
				configuracao = (ConfiguracaoCfg) confg.iterator().next();
			
			List lColunas = estruturaDao.getAtributosAcessoEstrutura(estruturaSelecionada);
			ValidaPermissao validaPermissao = new ValidaPermissao();
			List listaItensEstrutura = null;

			//Gera a lista de ietts (itens estrutura)
			if(lColunas != null && lColunas.size() > 0) {				
				listaItensEstrutura = itemDao.getItensFilho(itemEstruturaSelecionado, estruturaSelecionada, lColunas);
			}
			else {
				listaItensEstrutura = itemDao.getItensFilho(itemEstruturaSelecionado, estruturaSelecionada, "");
			}
			
			String radConcluido = "";
			if("".equals(Pagina.getParamStr(request, "radConcluido"))){
				radConcluido = (String) request.getSession().getAttribute("radConcluidoSession");
			} else{
				radConcluido = Pagina.getParamStr(request, "radConcluido");
			}
			
			listaItensEstrutura = itemDao.getItensIndConclusao(listaItensEstrutura, radConcluido);

			//parametros auxilixares
			String nomeCbCtrl = "cbCtrl" + estruturaSelecionada.getCodEtt();
			String nomeCbDep = "cbDep" + estruturaSelecionada.getCodEtt();
			String strCheckBox = "<td class=\\\"sorttable_nosort\\\" width=\\\"1%\\\" ><input type=\\\"checkbox\\\" class=\\\"form_check_radio\\\" name=\\\"" + nomeCbCtrl + "\\\" onclick=\\\"javascript:selectAll(document.form, '" + nomeCbCtrl + "', '" + nomeCbDep + "');\\\"></td>";
			String strColunaVazia = "<td class=\\\"sorttable_nosort\\\" width=\\\"1%\\\" > &nbsp;</td> <!-- Coluna para colocar icone para listagem -->";
			strBuffer.append("<table class=\\\"sortable\\\" border=\\\"0\\\" cellpadding=\\\"0\\\" cellspacing=\\\"0\\\" width=\\\"100%\\\" id=\\\"tabelaOrdenada\\\" >");
			strBuffer.append("	<thead >");
			strBuffer.append("	<tr bgcolor=\\\""+estruturaSelecionada.getCodCor2Ett()+"\\\">");

			/* desenha as colunas de uma estrutura */
			Iterator itColunas = lColunas.iterator();
			int numColuna = 2;
			while (itColunas.hasNext()){
				coluna = (ObjetoEstrutura) itColunas.next();
				strBuffer.append(strCheckBox);
				strBuffer.append(strColunaVazia);
				strBuffer.append("		<td width=\\\"" + coluna.iGetLargura() + "%\\\">");
				strBuffer.append(            coluna.iGetLabel());
				strBuffer.append("		</td>");
				
				numColuna++;
				strColunaVazia =  strCheckBox = "";
			}		
			strBuffer.append("		<td> &nbsp;</td>");
			strBuffer.append("	</tr> <!-- linha_subtitulo -->");
			strBuffer.append("	</thead>");

			strBuffer.append("<tbody id=\\\"corpo1\\\">");

			
			/* imprimir os itens da estrutura */
			Iterator itItens = listaItensEstrutura.iterator();
			while (itItens.hasNext()) {
				
				ItemEstruturaIett item = (ItemEstruturaIett) itItens.next();
				
				if ((item.getIndAtivoIett() != null || !"".equals(item.getIndAtivoIett())) 
												&& !"N".equals(item.getIndAtivoIett())) {
					
					validaPermissao.permissoesItem(item, seguranca.getUsuario(), seguranca.getGruposAcesso());
					
					boolean permissaoAcessoItem = validaPermissao.permissaoExcluirItem() || validaPermissao.permissaoConsultarItem();
					boolean permissaoAcessoItensFilhos = false;
		
					/* hint de otimização. Só testa se tem permissão para os filhos (recursivo) se não tiver para si próprio */			
					if(!permissaoAcessoItem){
						permissaoAcessoItensFilhos = validaPermissao.permissaoAcessoItensFilhos(item, seguranca.getUsuario(), seguranca.getGruposAcesso());
					}
					
					
					if(permissaoAcessoItem || permissaoAcessoItensFilhos){
		
						/* desenha as colunas de um item */
						itColunas = lColunas.iterator();
						
						if (validaPermissao.permissaoExcluirItem() && (item.getIndBloqPlanejamentoIett() == null || item.getIndBloqPlanejamentoIett().equals(ValidaPermissao.NAO))) {
							strCheckBox = "<td sorttable_customkey=\\\"0\\\" width=\\\"1%\\\"><input type=\\\"checkbox\\\" class=\\\"form_check_radio\\\" name=\\\"" + nomeCbDep + "\\\" value=\\\"" + item.getCodIett() + "\\\"></td>"; 
						} else {
							strCheckBox = "<td sorttable_customkey=\\\"0\\\" width=\\\"1%\\\"><input type=\\\"checkbox\\\" class=\\\"form_check_radio\\\" name=\\\"" + nomeCbDep + "\\\" value=\\\"" + item.getCodIett() + "\\\" disabled></td>"; 
						}
						
						String strHRef = "";
						String  strA = "";
						String strHRefProx = "";
						strColunaVazia = "<td sorttable_customkey=\\\"0\\\" width=\\\"1%\\\"> &nbsp;</td> <!-- Coluna para colocar icone para listagem -->";
						
					
						if (validaPermissao.permissaoConsultarItem()) {
							strHRef = "<a href=\\\"javascript:aoClicarConsultarItem(document.form, " + item.getCodIett() + ", '" + "iett" + item.getCodIett() + "_pai_ett" + item.getEstruturaEtt().getCodEtt()  + "');\\\">";
							strA = "</a>";
							/* Igor Desenho da seta para dar acesso aas estruturas filhas */
							strHRefProx = "<td sorttable_customkey=\\\"0\\\" width=\\\"1%\\\"><a href=\\\"javascript:aoClicarDetalharItem(document.form, 'iett" + item.getCodIett() + "_pai_ett" + item.getEstruturaEtt().getCodEtt() + "')\\\"> " +
									"<img src=\\\"" + request.getContextPath() + "/images/collapsed_button.gif\\\" border=0> </td>";
							/* /Igor */					
						} else{
							if (permissaoAcessoItensFilhos) {
								strHRef = "";
								strA = "</a>";
								strHRefProx = "<td sorttable_customkey=\\\"0\\\" width=\\\"1%\\\"><a href=\\\"javascript:aoClicarDetalharItem(document.form, 'iett" + item.getCodIett() + "_pai_ett" + item.getEstruturaEtt().getCodEtt() + "')\\\"> " +
										"<img src=\\\"" + request.getContextPath() + "/images/collapsed_button.gif\\\" border=0> </td>";		
							}			
						}
						
						// Linhas com os dados da Estrutura 
						strBuffer.append("<tr class=\\\"cor_sim\\\" onmouseover=\\\"javascript: destacaLinha(this,'over','')\\\" onmouseout=\\\"javascript: destacaLinha(this,'out','cor_sim')\\\" onClick=\\\"javascript: destacaLinha(this,'click','cor_sim')\\\" class=\\\"linha_subtitulo2_estrutura\\\" bgcolor=\\\"" + estruturaSelecionada.getCodCor3Ett() + "\\\">");
						
						String conteudo = "";
						
						boolean existeEstruturaFilhaPodeSerVisualizada = false;
						List lEstruturasFilhas = (new EstruturaDao(this.getRequest())).getSetEstruturasItem(item);
						//verifica se as estruturas filhas podem ser visualizadas
						if (lEstruturasFilhas != null && lEstruturasFilhas.size() != 0)
						{
							Iterator itlEstruturasFilhas = lEstruturasFilhas.iterator();
							
							while(itlEstruturasFilhas.hasNext() && !existeEstruturaFilhaPodeSerVisualizada){
								EstruturaEtt estruturaFilha = (EstruturaEtt) itlEstruturasFilhas.next();
								existeEstruturaFilhaPodeSerVisualizada = estruturaDao.verificarExibeEstrutura(estruturaFilha, item);
								
						   }		
						}
						
						while (itColunas.hasNext()){
							String strAliasIndex ="";
							coluna = (ObjetoEstrutura) itColunas.next();
							strBuffer.append(strCheckBox);	
							
							//verifica se as estruturas filhas podem ser visualizadas
							if (lEstruturasFilhas != null && lEstruturasFilhas.size() != 0 && existeEstruturaFilhaPodeSerVisualizada)
							{	strBuffer.append(strHRefProx);
								strBuffer.append(strA);
							}
							else{
								strBuffer.append(strColunaVazia);
							}
							
							strBuffer.append("<td ");

							//Se houver label na estrutura utiliza como hint, senão utiliza o nome da estrutura. 
							String title = "";
							String label = item.getEstruturaEtt().getLabelEtt();
							if (label == null || label.equals(Dominios.STRING_VAZIA)){
								title = item.getEstruturaEtt().getNomeEtt();
							}
							else{
								title = item.getEstruturaEtt().getNomeEtt() + " (" + item.getEstruturaEtt().getLabelEtt() + ")";
							}
							strBuffer.append("title = \\\""+title+"\\\"");
							
							strBuffer.append(" width=\\\""+coluna.iGetLargura()+"%\\\" aliasSortableIndex >");
							strBuffer.append(strHRef);
							strBuffer.append("<font color=\\\""+estruturaSelecionada.getCodCor4Ett()+"\\\">");
				
							if("nivelPlanejamento".equals(coluna.iGetNome())){
								String niveis = "";
						    	if(item.getItemEstruturaNivelIettns() != null && !item.getItemEstruturaNivelIettns().isEmpty()){
							    	Iterator itNiveis = item.getItemEstruturaNivelIettns().iterator();
							    	while(itNiveis.hasNext()){
							    		SisAtributoSatb nivel = (SisAtributoSatb) itNiveis.next();
							    		niveis += nivel.getDescricaoSatb() + "; ";
							    	}
							    	niveis = niveis.substring(0, niveis.lastIndexOf(";"));
						    	}
								strBuffer.append(niveis);
							}
							else{
								String informacaoIettSatb = "";
								if (coluna.iGetGrupoAtributosLivres() != null)  {
									Iterator itIettSatbs =  item.getItemEstruturaSisAtributoIettSatbs().iterator();
									while (itIettSatbs.hasNext()) {
										ItemEstruturaSisAtributoIettSatb itemEstruturaSisAtributoIettSatb = (ItemEstruturaSisAtributoIettSatb) itIettSatbs.next();
										
										if (itemEstruturaSisAtributoIettSatb.getSisAtributoSatb().getSisGrupoAtributoSga().equals(coluna.iGetGrupoAtributosLivres())){
											if (coluna.iGetGrupoAtributosLivres().getSisTipoExibicGrupoSteg().getCodSteg().equals(new Long(Input.TEXT)) ||
											 	coluna.iGetGrupoAtributosLivres().getSisTipoExibicGrupoSteg().getCodSteg().equals(new Long(Input.TEXTAREA)) ||
											 	coluna.iGetGrupoAtributosLivres().getSisTipoExibicGrupoSteg().getCodSteg().equals(new Long(Input.MULTITEXTO)) ||
											 	coluna.iGetGrupoAtributosLivres().getSisTipoExibicGrupoSteg().getCodSteg().equals(new Long(Input.VALIDACAO)) ) {
											 
												informacaoIettSatb = informacaoIettSatb + itemEstruturaSisAtributoIettSatb.getInformacao() + Util.trocaBarra(configuracao.getSeparadorCampoMultivalor());
											
												
												//Se for um atributo livre do tipo ID
												SisAtributoSatb sisAtributo = itemEstruturaSisAtributoIettSatb.getSisAtributoSatb();
												
												if (sisAtributo.isAtributoAutoIcremental() || sisAtributo.isAtributoContemMascara()) {
													strAliasIndex = "sorttable_customkey=\\\""+itemDao.obterTipoSequencial(itemEstruturaSisAtributoIettSatb).getConteudo()+"\\\"";
												} 
												
											} else if (!coluna.iGetGrupoAtributosLivres().getSisTipoExibicGrupoSteg().getCodSteg().equals(new Long(Input.IMAGEM))) {
												//se for do tipo imagem não faz nada, deixa em branco.
//												informacaoIettSatb = informacaoIettSatb + itemEstruturaSisAtributoIettSatb.getSisAtributoSatb().getDescricaoSatb() +  "; ";
												informacaoIettSatb = informacaoIettSatb + itemEstruturaSisAtributoIettSatb.getSisAtributoSatb().getDescricaoSatb() +  Util.trocaBarra(configuracao.getSeparadorCampoMultivalor());
												
											} 
										} 
									}
									if (informacaoIettSatb.length() > 0){
										informacaoIettSatb = informacaoIettSatb.substring(0, informacaoIettSatb.length() - configuracao.getSeparadorCampoMultivalor().length()); 
										//
									}
									strBuffer.append(informacaoIettSatb);
								} else {
									
//									strBuffer.append(coluna.iGetValor(item).replace("\n", "").replace("\t", "").replace("\r", ""));
									
									conteudo = coluna.iGetValor(item).replace("\n", " ").replace("\t", " ").replace("\r", " ")
									 .replace(Dominios.CARACTER_ESTRANHO_MARCADOR, "-")
									 .replace(Dominios.CARACTER_ESTRANHO_MARCADOR2, "-")
									 .replace(Dominios.CARACTER_ESTRANHO_ABREASPAS_SIMPLES, "'")
									 .replace(Dominios.CARACTER_ESTRANHO_FECHAASPAS_SIMPLES, "'");
									
//									conteudo = comum.util.Util.normalizaCaracterMarcador(conteudo);
//									System.out.println("conteudo: " + conteudo);
									
									strBuffer.append(conteudo);
								}
							}
							strBuffer.append(     "</font>");
							strBuffer.append(   strA);
							strBuffer.append("</td>");

							
							//troca o alias aliasSortableIndex inserido na TD por branco se o atributo livre não for do tipo ID e pela string sorttable_customkey="x" onde x é o valor do sequencial do atributo livre do tipo ID  
							strBuffer = inserirSortableColumn (strBuffer,strAliasIndex);
							
							strColunaVazia = strHRefProx = strCheckBox = strA = "";
							strColunaVazia = strCheckBox = strA = "";
						}
						strBuffer.append("<td sorttable_customkey=\\\"0\\\" > &nbsp; </td>");
						strBuffer.append("</tr>");
					}
					
				}
			}			
			strBuffer.append("</tbody></table>");
			
			
			if(this.itemEstruturaSelecionado != null){
				strBuffer.append("<input type=hidden name=\\\"codIettPrincipal\\\" id=\\\"codIettPrincipal\\\" value=\\\"" +
						this.itemEstruturaSelecionado.getCodIett().toString() + "\\\">");	
			}						
			
			if(estruturaSelecionada != null) {
				strBuffer.append("<input type=hidden name=\\\"codEttSelecionado\\\" id=\\\"codEttSelecionado\\\" value=\\\"" +
						estruturaSelecionada.getCodEtt().toString() + "\\\">");
				strBuffer.append("<input type=hidden name=\\\"ultEttSelecionado\\\" id=\\\"ultEttSelecionado\\\" value=\\\"" +
						estruturaSelecionada.getCodEtt().toString() + "\\\">");
			}
			
			//utilizado para guardar o último item que foi detalhado na árvore de cadastro
			strBuffer.append("<input type=hidden name=\\\"ultimoIdLinhaDetalhado\\\" id=\\\"ultimoIdLinhaDetalhado\\\" value=\\\"" +
					this.recuperaIdentificadorLinhaPai(tipoItemClicado) + "\\\">");
			
			strRetorno = strBuffer.toString();
		} catch (Exception e) {
			// TODO: handle exception
			strRetorno = "Erro de processamento!";
		}
				
		return strRetorno;
	}

	
	private StringBuffer inserirSortableColumn(StringBuffer htmlCode,String alias) {
		
		String novaStringBuffer;
		String str = htmlCode.toString();
		
		novaStringBuffer = str.replace("aliasSortableIndex", alias);
		
		
		return new StringBuffer(novaStringBuffer);
	}

	private String geraConteudoEttDetalharArvoreItens(String tipoItemClicado){
		String strRetorno="";
		StringBuffer conteudoTabela = new StringBuffer("");
		boolean exibirEstrutura = true;
		
		try {
			EstruturaDao estruturaDao= new EstruturaDao(request);
			//lista das estruturas raiz
			List lEstruturas = estruturaDao.getSetEstruturasItem(this.itemEstruturaSelecionado);		
		
		
					
			conteudoTabela.append("<table width=\\\"100%\\\" cellspacing=\\\"0\\\" cellpadding=\\\"0\\\" border=\\\"0\\\">");			
			
			//Imprimi as estruturas Raiz
			Iterator itEstrutura = lEstruturas.iterator();
			while (itEstrutura.hasNext()){	
				
				EstruturaEtt estrutura = (EstruturaEtt) itEstrutura.next();		
				exibirEstrutura = estruturaDao.verificarExibeEstrutura(estrutura, this.itemEstruturaSelecionado);
				
				if(exibirEstrutura) {
					//label de um item na árvore de cadastro
					String nomeItem = "";	
					//Gera o nome do item
					if(estrutura.getLabelEtt() != null && !"".equals(estrutura.getLabelEtt())){
						nomeItem = estrutura.getNomeEtt()  + " (" + estrutura.getLabelEtt() + ")"; 
					}
					else {
						nomeItem = estrutura.getNomeEtt(); 
					} 
					
					//Se houver label na estrutura utiliza como hint, senão utiliza o nome da estrutura.  
					
					String title = "";
					String label = estrutura.getLabelEtt();
					if (label == null || label.equals(Dominios.STRING_VAZIA)){
						title = estrutura.getNomeEtt();
					}
					else{
						title = estrutura.getNomeEtt() + " (" + estrutura.getLabelEtt() + ")";
					}
									
					conteudoTabela.append("<tr class=\\\"cor_sim\\\" bgcolor=\\\"#ffffff\\\" onmouseout=\\\"javascript: destacaLinha(this,'out','cor_sim')\\\" onmouseover=\\\"javascript:destacaLinha(this,'over','')\\\">");
					conteudoTabela.append(	"<td width=\\\"1%\\\">&nbsp;</td>");
					conteudoTabela.append(	"<td>");				
					conteudoTabela.append(	"<table>");
					conteudoTabela.append(		"<tr bgcolor=\\\"#ffffff\\\">");
					conteudoTabela.append(			"<td nowrap=\\\"\\\" title=\\\""+title+"\\\">");
					conteudoTabela.append(				"<img border=\\\"0\\\" alt=\\\"\\\" src=\\\"" + request.getContextPath() 
															+ "/images/iconePasta.png\\\"/>");			
					conteudoTabela.append(				"<a href=\\\"javascript:aoClicarDetalharItem(form, 'ett" +  estrutura.getCodEtt() + "_pai_iett" + this.itemEstruturaSelecionado.getCodIett() + "' )\\\">");
					conteudoTabela.append(					"<font color=\\\"#596d9b\\\"> " + nomeItem + " </font>");
					conteudoTabela.append(				"</a>");			
					conteudoTabela.append(			"</td>");
					conteudoTabela.append(		"</tr>");
					conteudoTabela.append(	"</table>");				
					conteudoTabela.append(	"</td>");
					conteudoTabela.append("</tr>");
				
				}
			}//fim do while
				
			conteudoTabela.append("</table>");
	
			//utilizado para guardar o último item que foi detalhado na árvore de cadastro
			conteudoTabela.append("<input type=hidden name=\\\"ultimoIdLinhaDetalhado\\\" id=\\\"ultimoIdLinhaDetalhado\\\" value=\\\"" +
					this.recuperaIdentificadorLinhaPai(tipoItemClicado) + "\\\">");
	
			strRetorno = conteudoTabela.toString();
			
		} catch (Exception e) {
			strRetorno = "Erro de processamento!";
		}
		
		return strRetorno;
	}
	
	
	/**
	 * 
         * @param tipoItemClicado
         * @return
	 */
	public String criaTabelaArvoreLocalizacao(String tipoItemClicado){
		String strRetorno="";
		StringBuffer strBuffer = new StringBuffer();
		
		strBuffer.append("\"tabelaArvoreLocalizacao\":");
		strBuffer.append("{\"idInsercao\":\"arvoreLocalizacao\", \"conteudo\": \"" + geraConteudoArvoreLocalizacao(tipoItemClicado) + "\"}");		
		strRetorno = strBuffer.toString();
		
		return strRetorno;
	}

        /**
         *
         * @param tipoItemClicado
         * @return
         */
	public String geraConteudoArvoreLocalizacao(String tipoItemClicado){
		String strRetorno="";
		StringBuffer strBuffer = new StringBuffer();
		String arvoreEstruturas = "";
		FuncaoDao funcaoDao = new FuncaoDao(request);
		ValidaPermissao validaPermissao = new ValidaPermissao();
		SegurancaECAR seguranca = (SegurancaECAR) request.getSession().getAttribute("seguranca");
		
        try {
            String s = "";
            List lista = new ArrayList();            
            
            if(this.itemEstruturaSelecionado != null)
            	lista = new ItemEstruturaDao(null).getAscendentes(this.itemEstruturaSelecionado);	
                        
            if (this.itemEstruturaSelecionado != null)
            	lista.add(this.itemEstruturaSelecionado);
            
            Iterator it = lista.iterator();
            ItemEstruturaIett itemEstruturaP;
            
                        
            //começa a árvore
            s += "<div id=\\\"menuemcascata\\\">";
            
            int nivel = 1;
            String connector = "";
            while (it.hasNext()) {
                itemEstruturaP = (ItemEstruturaIett) it.next();
                
                s += "<div class=\\\"cascata_nivel_"+ nivel+"\\\">";
                
                //testar o item selecionado
//                if(this.itemEstruturaSelecionado.getCodIett() == itemEstruturaP.getCodIett()){
//                	s += "<div class=\\\"selecionado\\\">";
//                }
                
                s += "<img src=\\\"" + request.getContextPath()+"/images/icon_seta_ident.gif\\\"> ";
                
		        //Gera o nome da estrutura        
				String nomeItem = "";	
				EstruturaEtt estrutura = itemEstruturaP.getEstruturaEtt();						
				if(estrutura.getLabelEtt() != null && !"".equals(estrutura.getLabelEtt())){
					nomeItem = estrutura.getNomeEtt() + " (" + estrutura.getLabelEtt() + ")"; 
				}
				else {
					nomeItem = estrutura.getNomeEtt(); 
				} 
                
                s += nomeItem;
                
                ItemEstruturaDao itemEstruturaDao = new ItemEstruturaDao(null);
                String descricaoItem = itemEstruturaDao.criaColunaConteudoColunaArvoreAjax(itemEstruturaP, itemEstruturaP.getEstruturaEtt());
                			
				boolean permissaoAcessoItem = validaPermissao.permissaoConsultarItem(itemEstruturaP, seguranca.getUsuario(), seguranca.getGruposAcesso());
                
				if(descricaoItem != null){
					s += " - ";
					if (permissaoAcessoItem){
						s += "<a href=\\\"" + request.getContextPath() + "/cadastroItens/dadosGerais/frm_con.jsp";
	                    s += "?codIett=" + itemEstruturaP.getCodIett();
	                	s += "&codAba="+ funcaoDao.getCodFuncaoDadosGerais();
	                	if (itemEstruturaP.getItemEstruturaIett() != null) {
	    	                s += "&" + "codIettPrincipal=" + itemEstruturaP.getItemEstruturaIett().getCodIett();
	    	            }
	    	            
	    	           	s += "&" + "ultEttSelecionado=" + itemEstruturaP.getEstruturaEtt().getCodEtt().toString();
	    	           	
	    	        	if (itemEstruturaP.getItemEstruturaIett() != null){
	    	        		s += "&" + "ultimoIdLinhaDetalhado=ett" + itemEstruturaP.getEstruturaEtt().getCodEtt() + "_pai_iett" + itemEstruturaP.getItemEstruturaIett().getCodIett();
	    	            	//ultimoIdLinhaDetalhado=ett6_pai_iett2917
	    	        	} else {
	    	        		s += "&" + "ultimoIdLinhaDetalhado=ett" + itemEstruturaP.getEstruturaEtt().getCodEtt() + "_pai_iett";
	    	        	}
						s += "\\\">";
	                    s += descricaoItem;
	                    s += "</a>";
	                } else {
	                	s += descricaoItem;
	                }
                }
				connector = "?";
	            s += "<a href=\\\"" + request.getContextPath() + "/cadastroItens/listaItem/lista.jsp";
	            String codIett="";
	            if (itemEstruturaP.getItemEstruturaIett() != null) {
	                s += connector + "codIettPrincipal=" + itemEstruturaP.getItemEstruturaIett().getCodIett();
	                connector = "&";
	                codIett = itemEstruturaP.getItemEstruturaIett().getCodIett().toString();
	            }
	            
	           	s += connector + "ultEttSelecionado=" + itemEstruturaP.getEstruturaEtt().getCodEtt().toString();
	           	connector = "&";
	           	
	        	if (itemEstruturaP.getItemEstruturaIett() != null){
	        		s += connector + "ultimoIdLinhaDetalhado=ett" + itemEstruturaP.getEstruturaEtt().getCodEtt() + "_pai_iett" + itemEstruturaP.getItemEstruturaIett().getCodIett();
	            	//ultimoIdLinhaDetalhado=ett6_pai_iett2917
	        	} else {
	        		s += connector + "ultimoIdLinhaDetalhado=ett" + itemEstruturaP.getEstruturaEtt().getCodEtt() + "_pai_iett";
	        	}
	        	
	            // Rogerio Fim Mantis #9358.
	            
	            s += "\\\">";
	            s += " [Ir para listagem]</a>";
                
                s += "</div>";
                s += "\\\n";
                nivel++;
            }
            
            
                s += "<div class=\\\"cascata_nivel_"+ nivel+"\\\">";
                s += "<img src=\\\"" + request.getContextPath()+"/images/icon_seta_ident.gif\\\"> ";
                

                if(estruturaPai != null && estruturaPai.isVirtual()){
                	if(this.estruturaPai.getLabelEtt() != null && !this.estruturaPai.getLabelEtt().equals("")){
                		s += this.estruturaPai.getNomeEtt() + " (" + this.estruturaPai.getLabelEtt() + ")";
                	} 
                	else{
                		s += this.estruturaPai.getNomeEtt();	
                	}
        		} else {
                
		          	if(this.estruturaSelecionada.getLabelEtt() != null && !this.estruturaSelecionada.getLabelEtt().equals("")){
		            	s += this.estruturaSelecionada.getNomeEtt() + " (" + this.estruturaSelecionada.getLabelEtt() + ")";
		            
		    		}		
		            else{
		            	s += this.estruturaSelecionada.getNomeEtt();	
		            }
				}
                s += "</div>";
            
            s += "</div>";            
            
            arvoreEstruturas = s;

        } catch (Exception e) {
			Logger.getLogger(this.getClass()).error(e);
        	e.printStackTrace();
        }


		strBuffer.append("<table border=\\\"0\\\" cellpadding=\\\"0\\\" cellspacing=\\\"0\\\" width=\\\"100%\\\">");
		strBuffer.append(	"<tr class=\\\"linha_titulo_estrutura\\\">");
		strBuffer.append(		"<td>");
		strBuffer.append(			arvoreEstruturas);
		strBuffer.append(		"</td>");
		strBuffer.append(	"</tr>");
		strBuffer.append("</table>");
		
		strRetorno = strBuffer.toString();		
		return strRetorno;
	}

	/**
	 * 
         * @param tipoItemClicado
         * @return
	 */
	public String criaBotoesHTML(String tipoItemClicado){
		String strRetorno="";
		StringBuffer strBuffer = new StringBuffer();

		SegurancaECAR seguranca = null;
		ValidaPermissao validaPermissao = null;
		EstruturaDao estruturaDao = new EstruturaDao(null);
		ItemEstruturaDao itemDao = new ItemEstruturaDao(null);
		List lColunas = null;			
		List listaItensEstrutura = null; //Lista de ItemEstruturas filhas		
		

		//Regras de visualização dos botões "Adicionar", "Excluir" e 
		//"Imprimir" na tela de listagem do cadastro.
		String permissaoVisualizarBotaoIncluirItem = "none";
		String permissaoVisualizarBotaoExcluirItem = "none";
		String permissaoVisualizarBotaoImprimirItem = "none";
		String permissaoVisualizarBotaoGerarArquivos = "none";		
		
		String permissaoVisualizarBotaoAssociarItem = "none";
		String permissaoVisualizarBotaoDesassociarItem = "none";
		
		String radConcluido = "";
		if("".equals(Pagina.getParamStr(request, "radConcluido"))){
			radConcluido = (String) request.getSession().getAttribute("radConcluidoSession");
		} else{
			radConcluido = Pagina.getParamStr(request, "radConcluido");
		}
	
		
			try {
				seguranca = (SegurancaECAR) request.getSession().getAttribute("seguranca");
				validaPermissao = new ValidaPermissao();
				lColunas = estruturaDao.getAtributosAcessoEstrutura(estruturaSelecionada);
				
				if(!estruturaSelecionada.isVirtual() && (estruturaPai == null|| !estruturaPai.isVirtual())) {
					if(lColunas != null && lColunas.size() > 0) {				
						listaItensEstrutura = itemDao.getItensFilho(itemEstruturaSelecionado, estruturaSelecionada, lColunas);
					}
					else {
						listaItensEstrutura = itemDao.getItensFilho(itemEstruturaSelecionado, estruturaSelecionada, "");
					}
					listaItensEstrutura = itemDao.getItensIndConclusao(listaItensEstrutura, radConcluido);
				}
					
				//Verifica as permissões		
				
				//se for uma estrutura "filha" de uma estrutura virtual
				if(estruturaPai != null && estruturaPai.isVirtual()){
					if(validaPermissao.permissaoAdicionarItem(this.estruturaPai, seguranca.getGruposAcesso())){
						permissaoVisualizarBotaoAssociarItem = "";
					}
					if(validaPermissao.permissaoAdicionarItem(this.estruturaPai, seguranca.getGruposAcesso())){
						permissaoVisualizarBotaoDesassociarItem = "";
					}
					
					if(validaPermissao.permissaoGerarArquivos(this.estruturaSelecionada, seguranca.getGruposAcesso()) && "S".equals(this.estruturaSelecionada.getIndExibirGerarArquivos())){							
						permissaoVisualizarBotaoGerarArquivos = "";
					}
					
					request.getSession().removeAttribute("estruturaVirtual");
					request.getSession().setAttribute("estruturaVirtual",this.estruturaPai);
				
				// se for uma estrutura virtual	
				} else if(this.estruturaSelecionada.isVirtual()){
					if(validaPermissao.permissaoAdicionarItem(this.estruturaSelecionada, seguranca.getGruposAcesso())){
						permissaoVisualizarBotaoAssociarItem = "";
					}
					if(validaPermissao.permissaoAdicionarItem(this.estruturaSelecionada, seguranca.getGruposAcesso())){
						permissaoVisualizarBotaoDesassociarItem = "";
					}
					
					if(validaPermissao.permissaoGerarArquivos(this.estruturaSelecionada, seguranca.getGruposAcesso()) && "S".equals(this.estruturaSelecionada.getIndExibirGerarArquivos())){							
						permissaoVisualizarBotaoGerarArquivos = "";
					}					
					
					request.getSession().removeAttribute("estruturaVirtual");
					request.getSession().setAttribute("estruturaVirtual",this.estruturaSelecionada);
					
					
				// se for uma estrutura normal	
				}else{
					if(validaPermissao.permissaoAdicionarItem(this.estruturaSelecionada, seguranca.getGruposAcesso())){
						permissaoVisualizarBotaoIncluirItem = "";
					}
					if((listaItensEstrutura!=null && listaItensEstrutura.size()>0)){
						permissaoVisualizarBotaoExcluirItem = "";
					}
					
					if(validaPermissao.permissaoImprimirListagem(this.estruturaSelecionada, seguranca.getGruposAcesso()) && this.estruturaSelecionada.getIndExibirImprimirListagem().equals("S")){
						permissaoVisualizarBotaoImprimirItem = "";
					}
					
					if(validaPermissao.permissaoGerarArquivos(this.estruturaSelecionada, seguranca.getGruposAcesso()) && "S".equals(this.estruturaSelecionada.getIndExibirGerarArquivos())){
						permissaoVisualizarBotaoGerarArquivos = "";
					}
					
					request.getSession().removeAttribute("estruturaVirtual");
					
				}
				

			} catch (Exception e) {
				//Em caso de exceção, os botões não aparecerão
				Logger.getLogger(this.getClass()).error(e);
	        	e.printStackTrace();

			}
		
		
		//Gera o objeto JSON
			
		strBuffer.append("\"nomeEstrutura\": [");
		
		if(estruturaPai != null && estruturaPai.isVirtual()){
			//Cor da linha em que se encontra o nome da estrutura, abaixo da linha das abas de estrutura
			strBuffer.append("{\"id\":\"trNomeEstrutura\", \"bgColor\":\"" 
					+ this.estruturaPai.getCodCor1Ett() + "\"},");
			//Nome da estrutura
			strBuffer.append("{\"id\":\"nomeEstrutura\", \"nomeEtt\":\"" 
					+ this.estruturaPai.getNomeEtt() + "\"}");
		}
		else{
			//Cor da linha em que se encontra o nome da estrutura, abaixo da linha das abas de estrutura
			strBuffer.append("{\"id\":\"trNomeEstrutura\", \"bgColor\":\"" 
					+ this.estruturaSelecionada.getCodCor1Ett() + "\"},");
			//Nome da estrutura
			strBuffer.append("{\"id\":\"nomeEstrutura\", \"nomeEtt\":\"" 
					+ this.estruturaSelecionada.getNomeEtt() + "\"}");
		}
		
		strBuffer.append("] , ");
		
		strBuffer.append("\"barraBotoes\": [");
		
		if(estruturaPai != null && estruturaPai.isVirtual()){
			//Cor da barra de botões
			strBuffer.append("{\"id\":\"barraBotoes\", \"bgColor\":\"" 
					+ this.estruturaPai.getCodCor1Ett() + "\"}");						
		}
		else{
			//Cor da barra de botões
			strBuffer.append("{\"id\":\"barraBotoes\", \"bgColor\":\"" 
					+ this.estruturaSelecionada.getCodCor1Ett() + "\"}");
		}
		
		strBuffer.append("] , ");
			
		strBuffer.append("\"botoesHtml\": [");
		
		if(estruturaPai != null && estruturaPai.isVirtual()){
			//botão incluir
			strBuffer.append("{\"id\":\"botaoIncluirItem\", \"tipo\":\"parametroIncluir\", \"style\":\""   
				+ permissaoVisualizarBotaoIncluirItem + "\", \"parametro\":\"" 
				+ this.estruturaPai.getCodEtt() + "\"},");
			//botão excluir
			strBuffer.append("{\"id\":\"botaoExcluirItem\", \"tipo\":\"parametroExcluir\", \"style\":\"" 
				+ permissaoVisualizarBotaoExcluirItem + "\", \"parametro\":\"" 
				+ "cbDep"+ this.estruturaPai.getCodEtt() + "\"},");					
			//botão imprimir
			strBuffer.append("{\"id\":\"botaoImprimirItem\", \"tipo\":\"parametroImprimir\", \"style\":\"" 
				+ permissaoVisualizarBotaoImprimirItem + "\", \"parametro\":\"" 
				+ this.estruturaPai.getCodEtt() + "|" + ((this.itemEstruturaSelecionado==null) ? 0 : this.itemEstruturaSelecionado.getCodIett()) + "\"},");
		} else {
			//botão incluir
			strBuffer.append("{\"id\":\"botaoIncluirItem\", \"tipo\":\"parametroIncluir\", \"style\":\""   
					+ permissaoVisualizarBotaoIncluirItem + "\", \"parametro\":\"" 
					+ this.estruturaSelecionada.getCodEtt() + "\"},");
			//botão excluir
			strBuffer.append("{\"id\":\"botaoExcluirItem\", \"tipo\":\"parametroExcluir\", \"style\":\"" 
					+ permissaoVisualizarBotaoExcluirItem + "\", \"parametro\":\"" 
					+ "cbDep"+ this.estruturaSelecionada.getCodEtt() + "\"},");					
			//botão imprimir
			strBuffer.append("{\"id\":\"botaoImprimirItem\", \"tipo\":\"parametroImprimir\", \"style\":\"" 
					+ permissaoVisualizarBotaoImprimirItem + "\", \"parametro\":\"" 
					+ this.estruturaSelecionada.getCodEtt() + "|" + ((this.itemEstruturaSelecionado==null) ? 0 : this.itemEstruturaSelecionado.getCodIett()) + "\"},");

		}
		
		//botão gerar arquivos
		strBuffer.append("{\"id\":\"botaoGerarArquivos\", \"tipo\":\"parametroGerarArquivos\", \"style\":\"" + permissaoVisualizarBotaoGerarArquivos 
				+ "\", \"parametro\":\"\"},");
		//botão associar item
		strBuffer.append("{\"id\":\"botaoAssociarItem\", \"tipo\":\"parametroAssociarItem\", \"style\":\"" + permissaoVisualizarBotaoAssociarItem 
				+ "\", \"parametro\":\"\"},");
		//botão desassociar
		strBuffer.append("{\"id\":\"botaoDesassociarItem\", \"tipo\":\"parametroDesassociarItem\", \"style\":\"" + permissaoVisualizarBotaoDesassociarItem 
				+ "\", \"parametro\":\"\"}");
		
		strBuffer.append("]");

		strRetorno = strBuffer.toString();		
		
		return strRetorno;
	}
	
/**
 * Retorna as linhas referente aos itens de uma estrutura que serão exibidos no menu.
 * @return
 * @throws ECARException
 */	
	public String criaLinhasIettArvore() throws ECARException{
		String strRetorno="";
		StringBuffer strBuffer = new StringBuffer();
		EstruturaDao estruturaDao = new EstruturaDao(request);
		ItemEstruturaDao itemDao = new ItemEstruturaDao(null);
		//decojaf verificar a ordenação no método static levando em consideração lColunas
		List lColunas = null;			
		List listaItensEstrutura = null; //Lista de ItemEstruturas filhas
		ValidaPermissao validaPermissao = null;
		SegurancaECAR seguranca = null; 
		
		lColunas = estruturaDao.getAtributosAcessoEstrutura(estruturaSelecionada);
		
		String radConcluido = "";
		if("".equals(Pagina.getParamStr(request, "radConcluido"))){
			radConcluido = (String) request.getSession().getAttribute("radConcluidoSession");
		} else{
			radConcluido = Pagina.getParamStr(request, "radConcluido");
		}
		
		if(lColunas != null && lColunas.size() > 0) {				
			listaItensEstrutura = itemDao.getItensFilho(itemEstruturaSelecionado, estruturaSelecionada, lColunas);
		}
		else {
			listaItensEstrutura = itemDao.getItensFilho(itemEstruturaSelecionado, estruturaSelecionada, "");
		}
		listaItensEstrutura = itemDao.getItensIndConclusao(listaItensEstrutura, radConcluido);
		
		//listaItensEstrutura = ordenaItensPrimeiroAtributo(listaItensEstrutura, estruturaSelecionada);
		
		validaPermissao = new ValidaPermissao();		
		seguranca = (SegurancaECAR) request.getSession().getAttribute("seguranca");	
		//Percorre todos os itens filhos
		Iterator itItens = listaItensEstrutura.iterator();
		boolean primeiroItemComPermissao = true;
		while (itItens.hasNext()) {
						
			ItemEstruturaIett item = (ItemEstruturaIett) itItens.next();
			
			if ((item.getIndAtivoIett() != null || !"".equals(item.getIndAtivoIett())) 
											&& !"N".equals(item.getIndAtivoIett())) {
				
				validaPermissao.permissoesItem(item, seguranca.getUsuario(), seguranca.getGruposAcesso());				
				boolean permissaoAcessoItem = validaPermissao.permissaoExcluirItem() || validaPermissao.permissaoConsultarItem();
				boolean permissaoAcessoItensFilhos = false;
	
				/* hint de otimização. Só testa se tem permissão para os filhos (recursivo) se iettvirtualnão tiver para si próprio */			
				if(!permissaoAcessoItem){
					permissaoAcessoItensFilhos = validaPermissao.permissaoAcessoItensFilhos(item, seguranca.getUsuario(), seguranca.getGruposAcesso());
				}				
				
				if(permissaoAcessoItem || permissaoAcessoItensFilhos){
					if (primeiroItemComPermissao){
						primeiroItemComPermissao = false;
					} else {
						strBuffer.append(",");
					}
					/*
					 * Cria o conteúdo da coluna(<td>) que faz parte da linha do item(<tr>) que
					 * compõe a tabela da árvore de ajax.
					 * O nome do item é setado de acordo com a configuração de estruturaAtributo.
					 */
					
					String nomeItem = itemDao.criaColunaConteudoColunaArvoreAjax(item, estruturaSelecionada);
					
					/* Identação pelo nível do item */
					String identacaoImagem = "";			
					int nivel = 0;
					int identacao = 0;
					nivel = item.getNivelIett().intValue();
					identacao = 2*nivel - 1; //fórmula deduzida por indução matemática
					
					for(int i = 1; i <= identacao;i++) {
							identacaoImagem += "<img src=\\\"" + request.getContextPath() +
									"/images/pixel.gif\\\" width=\\\"22\\\" height=\\\"9\\\" alt=\\\"\\\">";
					}
					
					/* Links de consultar(strHRef) e próximo nível(strHRefProx)*/
					String strHRefInicio = "";
					String strHRefFim = "";
					String strHRefProxInicio = "";
					String strHRefProxFim = "";
					List lEstruturasFilhas = (new EstruturaDao(request)).getSetEstruturasItem(item);
					boolean existeEstruturaFilhaPodeSerVisualizada = false;
					
					//verifica se existe alguma das estruturas filhas pode ser visualizadas
					if (lEstruturasFilhas != null && lEstruturasFilhas.size() != 0){
						Iterator itlEstruturasFilhas = lEstruturasFilhas.iterator();
						while(itlEstruturasFilhas.hasNext() && !existeEstruturaFilhaPodeSerVisualizada){
							EstruturaEtt estruturaFilha = (EstruturaEtt) itlEstruturasFilhas.next();
							existeEstruturaFilhaPodeSerVisualizada = estruturaDao.verificarExibeEstrutura(estruturaFilha, item);
							
						}
					}	
					
					if (validaPermissao.permissaoConsultarItem()) {
						strHRefInicio = "<a href=\\\"javascript:aoClicarConsultarItem(document.form, " + item.getCodIett() + ", 'iett" +  item.getCodIett() + "_pai_ett" + this.estruturaSelecionada.getCodEtt() + "');\\\">";
						strHRefFim = "</a>";
						strHRefProxInicio = "<a href=\\\"javascript:aoClicarExpandirItem(form, 'iett" +  item.getCodIett() + "_pai_ett" + this.estruturaSelecionada.getCodEtt() + "' )\\\">";
						strHRefProxFim = "</a>";
					} 
					else if (permissaoAcessoItensFilhos && (lEstruturasFilhas != null && lEstruturasFilhas.size() != 0 && existeEstruturaFilhaPodeSerVisualizada)) {
						strHRefProxInicio = "<a href=\\\"javascript:aoClicarExpandirItem(form, 'iett" +  item.getCodIett() + "_pai_ett" + this.estruturaSelecionada.getCodEtt() + "' )\\\">";
						strHRefProxFim = "</a>";
					}								
					
					//Se houver label na estrutura utiliza como hint, senão utiliza o nome da estrutura.  
					
					String titleEstrutura = "";
					String label = item.getEstruturaEtt().getLabelEtt();
					if (label == null || label.equals(Dominios.STRING_VAZIA)){
						titleEstrutura = item.getEstruturaEtt().getNomeEtt();
					}
					else{
						titleEstrutura = item.getEstruturaEtt().getNomeEtt() + " (" + item.getEstruturaEtt().getLabelEtt() + ")";
					}
															
					
					StringBuffer conteudoColuna = new StringBuffer("");
					conteudoColuna.append("<table>");
					conteudoColuna.append(	"<tr bgcolor=\\\"#ffffff\\\">");
					conteudoColuna.append(		"<td nowrap=\\\"\\\">");
					conteudoColuna.append(			identacaoImagem);
					conteudoColuna.append(		"</td>");
					conteudoColuna.append(		"<td id=\\\"iconeExpandiriett" + item.getCodIett() + "_pai_ett" +  this.estruturaSelecionada.getCodEtt() + "\\\" valign=\\\"top\\\">");
					conteudoColuna.append(			strHRefProxInicio); 
					conteudoColuna.append(				"<img id=\\\"imagemMaisMenosiett" + item.getCodIett() + "_pai_ett" + this.estruturaSelecionada.getCodEtt() + 
															"\\\" border=\\\"0\\\" alt=\\\"\\\" src=\\\"" + request.getContextPath() + "/images/collapsed_button.gif\\\"/>");
					conteudoColuna.append(			strHRefProxFim);					
					conteudoColuna.append(		"</td>");
					conteudoColuna.append(		"<td title=\\\""+titleEstrutura+"\\\" nowrap=\\\"\\\">");
					conteudoColuna.append(			strHRefInicio);
					conteudoColuna.append(				"<font color=\\\"#596d9b\\\"> " + nomeItem.replace(Dominios.CARACTER_ESTRANHO_MARCADOR, "-")
																								 .replace(Dominios.CARACTER_ESTRANHO_MARCADOR2, "-")
																								 .replace(Dominios.CARACTER_ESTRANHO_ABREASPAS_SIMPLES, "'")
																								 .replace(Dominios.CARACTER_ESTRANHO_FECHAASPAS_SIMPLES, "'") + " </font>");
					conteudoColuna.append(			strHRefFim);
					conteudoColuna.append(		"</td>");
					conteudoColuna.append(	"</tr>");
					conteudoColuna.append("</table>");
					
					
					/*
					 * Cria a linha da tabela no formato JSON
					 */
					
					strBuffer.append("{");
					strBuffer.append("\"parametros\":{");
					strBuffer.append("\"id\":\"iett" + item.getCodIett() + "_pai_ett" + this.estruturaSelecionada.getCodEtt() +
							"\", \"class\":\"cor_sim\", \"bgColor\":\"#ffffff\", \"onMouseOut\":\"javascript: destacaLinha(this,'out','cor_sim')\"," +
							" \"onMouseOver\":\"javascript:destacaLinha(this,'over','')\"");			 				
					strBuffer.append(	"},");
					strBuffer.append("\"coluna\":\"" + conteudoColuna.toString() + "\"");
					
					strBuffer.append("}");
										
				}
				
			}						
		}//fim do while 
		
		strRetorno = strBuffer.toString();		
		return strRetorno;
	}
	
	/**
	 * Cria as linhas da arvore do menu quando é um objeto do tipo estrutura.
	 * @return
	 * @throws ECARException
	 */
	public String criaLinhasEttArvore() throws ECARException{
		String strRetorno="";
		StringBuffer strBuffer = new StringBuffer();

		boolean exibirEstrutura = true;
		
		EstruturaDao estruturaDao= new EstruturaDao(request);
		//lista das estruturas raiz
		List lEstruturas = estruturaDao.getSetEstruturasItem(this.itemEstruturaSelecionado);		
		
		boolean primeiraEstrutura = true;
		
		Iterator itEstrutura = lEstruturas.iterator();			
		//Imprimi as estruturas Raiz
		while (itEstrutura.hasNext()){	
			
			EstruturaEtt estrutura = (EstruturaEtt) itEstrutura.next();		

			exibirEstrutura = estruturaDao.verificarExibeEstrutura(estrutura, this.itemEstruturaSelecionado);
			
			if(exibirEstrutura) {
				//label de um item na árvore de cadastro
				String nomeItem = "";	
				
				//Gera o nome do item
				if(estrutura.getLabelEtt() != null && !"".equals(estrutura.getLabelEtt())){
					nomeItem = estrutura.getNomeEtt() + " (" + estrutura.getLabelEtt() + ")"; 
				}
				else {
					nomeItem = estrutura.getNomeEtt(); 
				} 
	
				//Se houver label na estrutura utiliza como hint, senão utiliza o nome da estrutura.  
				
				String title = "";
				String label = estrutura.getLabelEtt();
				if (label == null || label.equals(Dominios.STRING_VAZIA)){
					title = estrutura.getNomeEtt();
				}
				else{
					title = estrutura.getNomeEtt() + " (" + estrutura.getLabelEtt() + ")";
				}
							
				/* Identação pelo nível do item */
				String identacaoImagem = "";			
				int nivel = 0;
				int identacao = 0;
				nivel = estruturaDao.getNivel(estrutura);
				identacao = 2*(nivel - 1); //fórmula deduzida por indução matemática
				for(int i = 1; i <= identacao ;i++) {
						identacaoImagem += "<img src=\\\"" + request.getContextPath() +
								"/images/pixel.gif\\\" width=\\\"22\\\" height=\\\"9\\\" alt=\\\"\\\">";
				}
				
				StringBuffer conteudoColuna = new StringBuffer("");
				conteudoColuna.append("<table>");
				conteudoColuna.append(	"<tr bgcolor=\\\"#ffffff\\\">");
				conteudoColuna.append(		"<td nowrap=\\\"\\\">");
				conteudoColuna.append(			identacaoImagem);
				conteudoColuna.append(		"</td>");
				conteudoColuna.append(		"<td id=\\\"iconeExpandirett" + estrutura.getCodEtt() + "_pai_iett" +  this.itemEstruturaSelecionado.getCodIett() + "\\\" valign=\\\"top\\\">");
				conteudoColuna.append(			"<a href=\\\"javascript:aoClicarExpandirItem(form, 'ett" + estrutura.getCodEtt()  + "_pai_iett" + this.itemEstruturaSelecionado.getCodIett() + "' )\\\">");
				conteudoColuna.append(				"<img id=\\\"imagemMaisMenosett" + estrutura.getCodEtt() + "_pai_iett" + this.itemEstruturaSelecionado.getCodIett() + 
														"\\\" border=\\\"0\\\" alt=\\\"\\\" src=\\\"" + request.getContextPath() + "/images/collapsed_button.gif\\\"/>");
				conteudoColuna.append(			"</a>");
				conteudoColuna.append(		"</td>");
				conteudoColuna.append(		"<td nowrap=\\\"\\\" title=\\\""+title+"\\\">");
				conteudoColuna.append(			"<img border=\\\"0\\\" alt=\\\"\\\" src=\\\"" + request.getContextPath()
														+ "/images/iconePasta.png\\\"/>");			
				
				//se for na tela de listagem, detalha o item
				if (ehTelaListagem){
					conteudoColuna.append(			"<a href=\\\"javascript:aoClicarDetalharItem(form, 'ett" +  estrutura.getCodEtt() + "_pai_iett" + this.itemEstruturaSelecionado.getCodIett() + "' )\\\">");
				}
				// se não for chamado da tela de listagem, volta para a lista
				else{
					conteudoColuna.append("<a href=\\\"").append(request.getContextPath()).append("/cadastroItens/listaItem/lista.jsp");
					String connector = "?"; // define o primeiro conector para parametros na url
					conteudoColuna.append(connector + "ultEttSelecionado=" + estruturaSelecionada.getCodEtt().toString() );
	               	connector = "&";
	            	if (itemEstruturaSelecionado != null){
	            		conteudoColuna.append(connector + "ultimoIdLinhaDetalhado=ett" + estrutura.getCodEtt() + "_pai_iett" + itemEstruturaSelecionado.getCodIett());
	            	} else {
	            		conteudoColuna.append(connector + "ultimoIdLinhaDetalhado=ett" + estrutura.getCodEtt() + "_pai_iett");
	            	}
	            	connector = "&";
	            	conteudoColuna.append(connector + "ultimoIdLinhaExpandido=" + Pagina.getParamStr(request,"ultimoIdLinhaExpandido"));                  	
	               	connector = "&";
	               	conteudoColuna.append("\\\" >");
	
				}
				
				
			
				conteudoColuna.append(				"<font color=\\\"#596d9b\\\"> " + nomeItem + " </font>");
				conteudoColuna.append(			"</a>");			
				conteudoColuna.append(		"</td>");
				conteudoColuna.append(	"</tr>");
				conteudoColuna.append("</table>");
				
				
				
				
				/*
				 * Cria a linha da tabela no formato JSON
				 */
				
				if (!primeiraEstrutura){
					strBuffer.append(",");
				}
				
				strBuffer.append("{");
				strBuffer.append("\"parametros\":{");
				strBuffer.append("\"id\":\"ett" + estrutura.getCodEtt() + "_pai_iett" + this.itemEstruturaSelecionado.getCodIett() +
						"\"," +
						"\"name\":\"ett" + estrutura.getCodEtt() + "_pai_iett" + this.itemEstruturaSelecionado.getCodIett() +
						"\"," +
						" \"class\":\"cor_sim\", \"bgColor\":\"#ffffff\", \"onMouseOut\":\"javascript: destacaLinha(this,'out','cor_sim')\"," +
						" \"onMouseOver\":\"javascript:destacaLinha(this,'over','')\"");			 				
				strBuffer.append(	"},");
				strBuffer.append("\"coluna\":\"" + conteudoColuna.toString() + "\"");
				
				strBuffer.append("}");
				primeiraEstrutura = false;
				
//				if(itEstrutura.hasNext()){
//					strBuffer.append("},");
//				}else{
//					strBuffer.append("}");
//				}					

			}//fim do if que verifica se pode exibir a Estrutura
				
		}//fim do while
		
		strRetorno = strBuffer.toString();		
		return strRetorno;
	}
	
	/**
	 * Cria as linhas da arvore do menu quando é um objeto do tipo estrutura virtual.
	 * No caso da estrutura virtual, os filhos vão ser estruturas reais dos itens pertencentes a estrutura virtual.
	 * Para saber quais a 
	 * 
	 * @return
	 * @throws ECARException
	 */
	public String criaLinhasEttVirtualArvore() throws ECARException{
		String strRetorno="";
		StringBuffer strBuffer = new StringBuffer();
		EstruturaDao estruturaDao = new EstruturaDao(request);
		ItemEstruturaDao itemDao = new ItemEstruturaDao(null);
		//verificar a ordenação no método static levando em consideração lColunas
		List lColunas = null;			
		List listaItensEstrutura = null; //Lista de ItemEstruturas filhas
		SegurancaECAR seguranca = (SegurancaECAR) request.getSession().getAttribute("seguranca");
		Map mapItensEstruturaVirtual = null;

		if (estruturaSelecionada.isVirtual()) {
			ActionEstrutura action = new ActionEstrutura();
			// já verifica a permissão de itens e estruturas
			mapItensEstruturaVirtual = action.montarMapItensEstruturaVirtualComPermissao(estruturaSelecionada, seguranca);
		}
		
		Iterator itEstruturas = mapItensEstruturaVirtual.keySet().iterator();
		
		
		while (itEstruturas.hasNext()){	
			
			EstruturaEtt estrutura = (EstruturaEtt) itEstruturas.next();		
			//label de um item na árvore de cadastro
			String nomeItem = "";	
			
			//Gera o nome do item
			if(estrutura.getLabelEtt() != null && !"".equals(estrutura.getLabelEtt())){
				nomeItem = estrutura.getNomeEtt() + " (" + estrutura.getLabelEtt() + ")"; 
			}
			else {
				nomeItem = estrutura.getNomeEtt(); 
			} 

			//Se houver label na estrutura utiliza como hint, senão utiliza o nome da estrutura.  
			
			String title = "";
			String label = estrutura.getLabelEtt();
			if (label == null || label.equals(Dominios.STRING_VAZIA)){
				title = estrutura.getNomeEtt();
			}
			else{
				title = estrutura.getNomeEtt() + " (" + estrutura.getLabelEtt() + ")";
			}
						
			// Nao existe identação por níveis das estruturas (todas vão ficar no mesmo nivel)
			// A identação depende apenas do nivel da estrutura virtual
			String identacaoImagem = "";			
			int nivel = 0;
			int identacao = 0;
			nivel = estruturaDao.getNivel(this.estruturaSelecionada);
			identacao = 2*nivel - 1; //fórmula deduzida por indução matemática
			for(int i = 1; i <= identacao;i++) {
				identacaoImagem += "<img src=\\\"" + request.getContextPath() +
							"/images/pixel.gif\\\" width=\\\"22\\\" height=\\\"9\\\" alt=\\\"\\\">";
			}
			
			
			//codigo item pai da estrutura virtual e avo da estrutura "filha"
			String codIettAvo =  "";
			if(itemEstruturaSelecionado != null)
				codIettAvo = itemEstruturaSelecionado.getCodIett().toString();
			
			//monta a linha de codigo da estrutura 
			String idLinha = estrutura.getCodEtt() + "_pai_ett"  + this.estruturaSelecionada.getCodEtt() + "_avo_" + codIettAvo;
			
			StringBuffer conteudoColuna = new StringBuffer("");
			conteudoColuna.append("<table>");
			conteudoColuna.append(	"<tr bgcolor=\\\"#ffffff\\\">");
			conteudoColuna.append(		"<td nowrap=\\\"\\\">");
			conteudoColuna.append(			identacaoImagem);
			conteudoColuna.append(		"</td>");
			conteudoColuna.append(		"<td id=\\\"iconeExpandirett" + idLinha + "\\\" valign=\\\"top\\\">");
			conteudoColuna.append(			"<a href=\\\"javascript:aoClicarExpandirItem(form, 'ett" + idLinha + "' )\\\">");
			conteudoColuna.append(				"<img id=\\\"imagemMaisMenosett" + idLinha + "\\\" border=\\\"0\\\" alt=\\\"\\\" src=\\\"" + request.getContextPath() + "/images/collapsed_button.gif\\\"/>");
			conteudoColuna.append(			"</a>");
			conteudoColuna.append(		"</td>");
			conteudoColuna.append(		"<td nowrap=\\\"\\\" title=\\\""+title+"\\\">");
			conteudoColuna.append(			"<img border=\\\"0\\\" alt=\\\"\\\" src=\\\"" + request.getContextPath()
													+ "/images/iconePasta.png\\\"/>");		
			
			
			
		
			//se for na tela de listagem, detalha o item
			if (ehTelaListagem){
				conteudoColuna.append("<a href=\\\"javascript:aoClicarDetalharItem(form, '"+ this.recuperaIdentificadorLinhaPai("estruturaVirtual") +"' )\\\">");
			}
			// se não for chamado da tela de listagem, volta para a lista
			else {
				conteudoColuna.append("<a href=\\\"").append(request.getContextPath()).append("/cadastroItens/listaItem/lista.jsp");
				String connector = "?"; // define o primeiro conector para parametros na url
				// Prepara o link para retorno na aba correta.
				
					
				// a estrutura pai guarda a estrutura virtual
				String idLinhaEstruturaVirtual = "ett" +  estruturaSelecionada.getCodEtt().toString()  + "_pai_iett" + codIettAvo;
				String idLinhaEstruturaFilhaVirtual = "ett" + estrutura.getCodEtt().toString() + "_pai_ett" + estruturaSelecionada.getCodEtt().toString() + 
												"_avo_" + codIettAvo;
					
				conteudoColuna.append(connector + "ultEttSelecionado=" + estruturaSelecionada.getCodEtt().toString() );
				connector = "&";
				conteudoColuna.append(connector + "ultimoIdLinhaDetalhado=" + idLinhaEstruturaVirtual);
				conteudoColuna.append(connector + "ultimoIdLinhaExpandido=" + idLinhaEstruturaFilhaVirtual);
				conteudoColuna.append("\\\" >");
				
			}
			
		  	
			
			conteudoColuna.append(				"<font color=\\\"#596d9b\\\"> " + nomeItem + " </font>");
			conteudoColuna.append(			"</a>");			
			conteudoColuna.append(		"</td>");
			conteudoColuna.append(	"</tr>");
			conteudoColuna.append("</table>");
			
			
			 
			/*
			 * Cria a linha da tabela no formato JSON
			 */
			
			strBuffer.append("{");
			strBuffer.append("\"parametros\":{");
			strBuffer.append("\"id\":\"ett" + estrutura.getCodEtt() + "_pai_ett" +  + this.estruturaSelecionada.getCodEtt() +  "_avo_" + codIettAvo +
					"\", \"class\":\"cor_sim\", \"bgColor\":\"#ffffff\", \"onMouseOut\":\"javascript: destacaLinha(this,'out','cor_sim')\"," +
					" \"onMouseOver\":\"javascript:destacaLinha(this,'over','')\"");			 				
			strBuffer.append(	"},");
			strBuffer.append("\"coluna\":\"" + conteudoColuna.toString() + "\"");
			
			if(itEstruturas.hasNext()){
				strBuffer.append("},");
			}else{
				strBuffer.append("}");
			}					

		}//fim do while
		
		strRetorno = strBuffer.toString();		
		return strRetorno;

	}
	
	
	/**
	 * Retorna as linhas referente aos itens de uma estrutura virtual que serão exibidos na árvore.
	 * A chamada acontece na árvore quando uma estrutura virtual é expandida em várias estruturas de acordo com os itens associados
	 * e uma dessas estruturas "filhas" é solicitada a expansão. Os itens vão ser mostrados abaixo da estrutura "filha".
	 * 
	 * @return String strRetorno
	 * @throws ECARException
	 */	
		public String criaLinhasIettVirtualArvore() throws ECARException{
			
			String strRetorno="";
			StringBuffer strBuffer = new StringBuffer();
			EstruturaDao estruturaDao = new EstruturaDao(request);
			ItemEstruturaDao itemDao = new ItemEstruturaDao(null);
			//verificar a ordenação no método static levando em consideração lColunas
			List lColunas = null;			
			List listaItensEstrutura = null; //Lista de ItemEstruturas filhas
			ValidaPermissao validaPermissao = new ValidaPermissao();
			SegurancaECAR seguranca = (SegurancaECAR) request.getSession().getAttribute("seguranca");
			
			String radConcluido = "";
			if("".equals(Pagina.getParamStr(request, "radConcluido"))){
				radConcluido = (String) request.getSession().getAttribute("radConcluidoSession");
			} else{
				radConcluido = Pagina.getParamStr(request, "radConcluido");
			}
			
			Map mapItensEstruturaVirtual = null;

			if (estruturaPai.isVirtual()) {
				ActionEstrutura action = new ActionEstrutura();
				// já verifica a permissão de itens e estruturas
				mapItensEstruturaVirtual = action.montarMapItensEstruturaVirtualComPermissao(estruturaPai, seguranca);
			}
			
			listaItensEstrutura = (List) mapItensEstruturaVirtual.get(estruturaSelecionada);
			
			lColunas = estruturaDao.getAtributosAcessoEstruturaArvore(estruturaSelecionada);
			
		
			
			listaItensEstrutura = itemDao.getItensIndConclusao(listaItensEstrutura, radConcluido);
						
			validaPermissao = new ValidaPermissao();		
			seguranca = (SegurancaECAR) request.getSession().getAttribute("seguranca");			
			
			//Percorre todos os itens filhos
			Iterator itItens = listaItensEstrutura.iterator();
			boolean primeiroItemComPermissao = true;
			while (itItens.hasNext()) {
							
				ItemEstruturaIett item = (ItemEstruturaIett) itItens.next();
				validaPermissao.permissoesItem(item, seguranca.getUsuario(), seguranca.getGruposAcesso());
				
			/*	if ((item.getIndAtivoIett() != null || !"".equals(item.getIndAtivoIett().trim())) 
												&& !"N".equals(item.getIndAtivoIett().toUpperCase())) {
					validaPermissao.permissoesItem(item, seguranca.getUsuario(), seguranca.getGruposAcesso());				
					boolean permissaoAcessoItem = validaPermissao.permissaoExcluirItem() || validaPermissao.permissaoConsultarItem();
					boolean permissaoAcessoItensFilhos = false;
					if(permissaoAcessoItem || permissaoAcessoItensFilhos){
			*/	
						if (primeiroItemComPermissao){
							primeiroItemComPermissao = false;
						} else {
							strBuffer.append(",");
						}
						/*
						 * Cria o conteúdo da coluna(<td>) que faz parte da linha do item(<tr>) que
						 * compõe a tabela da árvore de ajax.
						 * O nome do item é setado de acordo com a configuração de estruturaAtributo.
						 */
						
						String nomeItem = itemDao.criaColunaConteudoColunaArvoreAjax(item, estruturaSelecionada);
						
						// Nao existe identação por níveis das estruturas (todas vão ficar no mesmo nivel)
						// A identação depende apenas do nivel da estrutura virtual
						String identacaoImagem = "";			
						int nivel = 0;
						int identacao = 0;
						nivel = estruturaDao.getNivel(this.estruturaPai);
						identacao = 2*nivel - 1; //fórmula deduzida por indução matemática
						for(int i = 0; i <= identacao;i++) {
							identacaoImagem += "<img src=\\\"" + request.getContextPath() +
										"/images/pixel.gif\\\" width=\\\"22\\\" height=\\\"9\\\" alt=\\\"\\\">";
						}
						
						
						
						/* Links de consultar(strHRef) e próximo nível(strHRefProx)*/
						String strHRefInicio = "";
						String strHRefFim = "";
						String strHRefProxInicio = "";
						String strHRefProxFim = "";
						List lEstruturasFilhas = new ArrayList();
						
					
						if (validaPermissao.permissaoConsultarItem()) {
							strHRefInicio = "<a href=\\\"javascript:aoClicarConsultarItemEstruturaVirtual(document.form, " + item.getCodIett() + ", '" + request.getParameter("ultimoIdLinhaExpandido") + "');\\\">";
							strHRefFim = "</a>";
						} 
						
						//Se houver label na estrutura utiliza como hint, senão utiliza o nome da estrutura.
						
						String titleEstrutura = "";
						String label = item.getEstruturaEtt().getLabelEtt();
						if (label == null || label.equals(Dominios.STRING_VAZIA)){
							titleEstrutura = item.getEstruturaEtt().getNomeEtt();
						}
						else{
							titleEstrutura = item.getEstruturaEtt().getNomeEtt() + " (" + item.getEstruturaEtt().getLabelEtt() + ")";
						}
											
						//codigo item pai da estrutura virtual e avo da estrutura "filha"
						String codIettAvo =  "";
						if(itemEstruturaSelecionado != null)
							codIettAvo = itemEstruturaSelecionado.getCodIett().toString();
						
						StringBuffer conteudoColuna = new StringBuffer("");
						conteudoColuna.append("<table>");
						conteudoColuna.append(	"<tr bgcolor=\\\"#ffffff\\\">");
						conteudoColuna.append(		"<td nowrap=\\\"\\\">");
						conteudoColuna.append(			identacaoImagem);
						conteudoColuna.append(		"</td>");
						conteudoColuna.append(		"<td id=\\\"iconeExpandiriett" + item.getCodIett() + "_pai_ett" +  this.estruturaPai.getCodEtt() + "_avo_" + codIettAvo + "\\\" valign=\\\"top\\\">");
						conteudoColuna.append(			strHRefProxInicio); 
						conteudoColuna.append(				"<img id=\\\"imagemMaisMenosiett" + item.getCodIett() + "_pai_ett" + this.estruturaPai.getCodEtt() + "_avo_" + codIettAvo +
																"\\\" border=\\\"0\\\" alt=\\\"\\\" src=\\\"" + request.getContextPath() + "/images/square.gif\\\"/>");
						conteudoColuna.append(			strHRefProxFim);					
						conteudoColuna.append(		"</td>");
						conteudoColuna.append(		"<td title=\\\""+titleEstrutura+"\\\" nowrap=\\\"\\\">");
						conteudoColuna.append(			strHRefInicio);
						conteudoColuna.append(				"<font color=\\\"#596d9b\\\"> " + nomeItem + " </font>");
						conteudoColuna.append(			strHRefFim);
						conteudoColuna.append(		"</td>");
						conteudoColuna.append(	"</tr>");
						conteudoColuna.append("</table>");

						
						/*
						 * Cria a linha da tabela no formato JSON
						 */
						
						strBuffer.append("{");
						strBuffer.append("\"parametros\":{");
						strBuffer.append("\"id\":\"iett" + item.getCodIett() + "_pai_ett" + this.estruturaPai.getCodEtt() + "_avo_" + codIettAvo +
								"\", \"class\":\"cor_sim\", \"bgColor\":\"#ffffff\", \"onMouseOut\":\"javascript: destacaLinha(this,'out','cor_sim')\"," +
								" \"onMouseOver\":\"javascript:destacaLinha(this,'over','')\"");			 				
						strBuffer.append(	"},");
						strBuffer.append("\"coluna\":\"" + conteudoColuna.toString() + "\"");
						
						strBuffer.append("}");
											
				//	}
				//}						
			
			}//fim do while 
			
			strRetorno = strBuffer.toString();		
			return strRetorno;
		}
	
	
	/**
	 * Remonta o identificador da linha pai
	 * @param tipoItemClicado
	 * @return
	 */
	public String recuperaIdentificadorLinhaPai(String tipoItemClicado){
		String idLinhaPai = "";
		if(tipoItemClicado.equals("estrutura")){
			if(estruturaPai == null) {
				idLinhaPai = "ett" + this.estruturaSelecionada.getCodEtt().toString() + "_pai_iett" 
					+ (this.itemEstruturaSelecionado==null ? "" : this.itemEstruturaSelecionado.getCodIett().toString());  
			} else {
				//se for uma chamada a uma estrutura "filha" de uma estrutura virtual (uso pratico apenas na montagem da arvore)
				idLinhaPai = "ett" + this.estruturaSelecionada.getCodEtt().toString() + "_pai_ett" + this.estruturaPai.getCodEtt().toString();
			}
		}
		else if(tipoItemClicado.equals("itemEstrutura")){
			idLinhaPai = "iett" + this.itemEstruturaSelecionado.getCodIett() + "_pai_ett" 
				+ this.estruturaSelecionada.getCodEtt();  			
		}
		else if(tipoItemClicado.equals("estruturaVirtual")){
			if(estruturaPai == null) {
				idLinhaPai = "ett" + this.estruturaSelecionada.getCodEtt().toString() + "_pai_iett" 
					+ (this.itemEstruturaSelecionado==null ? "" : this.itemEstruturaSelecionado.getCodIett().toString());  
			} else {
				//se for uma chamada a uma estrutura "filha" de uma estrutura virtual (uso pratico apenas na montagem da arvore)
				idLinhaPai = "ett" + this.estruturaPai.getCodEtt().toString() + "_pai_iett" 
					+ (this.itemEstruturaSelecionado==null ? "" : this.itemEstruturaSelecionado.getCodIett().toString());
			}
		}
		
		return idLinhaPai;
		
	}
	
	/**
	 * Gera os descendentes, no formato html, de um determinado item passado, de acordo com o caminho 
	 * da árvore passado como parâmetro.
	 * @param idLinha
	 * @param caminhoArvore
         * @param request
         * @param ultimoIdLinhaDetalhado
         * @param isEstruturaItemFilhaEstruturaVirtual
         * @param ehTelaListagemParam
         * @return
	 */
	public static String geraFilhosHtml(String idLinha, List caminhoArvore, HttpServletRequest request, String ultimoIdLinhaDetalhado, boolean ehTelaListagemParam,
										String isEstruturaItemFilhaEstruturaVirtual){

		String filhosHtml = "";

		try {

			/**************************************************************
			 * Recupera o itemEstruturaSelecionado e a estruturaSelecionada
			 **************************************************************/
			
			String tipoItemClicado = "";
			ItemEstruturaIett itemEstruturaSelecionado = null;
			EstruturaEtt estruturaSelecionada = null;
			EstruturaEtt estruturaPai = null;
			String codEtt = "";
			String codIett = "";
			String codEttPai = "";
			ItemEstruturaDao itemDao = new ItemEstruturaDao(request);
			EstruturaDao estruturaDao= new EstruturaDao(request);
			ValidaPermissao validaPermissao = new ValidaPermissao();		
			SegurancaECAR seguranca = (SegurancaECAR) request.getSession().getAttribute("seguranca");	
			String ultimoIdLinhaExpandido = request.getParameter("ultimoIdLinhaExpandido");
			
			String radConcluido = "";
			if("".equals(Pagina.getParamStr(request, "radConcluido"))){
				radConcluido = (String) request.getSession().getAttribute("radConcluidoSession");
			} else{
				radConcluido = Pagina.getParamStr(request, "radConcluido");
			}
			
			ehTelaListagem = ehTelaListagemParam;
			if(idLinha.startsWith("iett")){
				tipoItemClicado = "itemEstrutura";
				
				codIett = idLinha.substring(4, idLinha.indexOf("_pai_"));
				itemEstruturaSelecionado = (ItemEstruturaIett) itemDao.buscar(ItemEstruturaIett.class, new Long(codIett));
				
				
				if(!idLinha.contains("_avo_")) {
					codEtt = idLinha.substring(idLinha.indexOf("_pai_ett")+8, idLinha.length());										
				} else {
					// se for um item associado a uma estrutura virtual
					codEtt = idLinha.substring(idLinha.indexOf("_pai_ett")+8, idLinha.indexOf("_avo"));
				}
					estruturaSelecionada = (EstruturaEtt) estruturaDao.buscar(EstruturaEtt.class, new Long(codEtt));
	
			}
			else if(idLinha.startsWith("ett")){
				tipoItemClicado = "estrutura";
	
				codEtt = idLinha.substring(3, idLinha.indexOf("_pai_"));
				estruturaSelecionada = (EstruturaEtt) estruturaDao.buscar(EstruturaEtt.class, new Long(codEtt));
				
				// se a estrutura partir de um item
				if(idLinha.contains("iett")) {
					codIett = idLinha.substring(idLinha.indexOf("_pai_iett")+9, idLinha.length());
					
				} else {
					// se a estrutura partir de uma outra estrutra (estrutura "filha" da estrutura virtual)
					codEttPai = idLinha.substring(idLinha.indexOf("_pai_ett") + 8, idLinha.indexOf("_avo_"));
					codIett =  idLinha.substring(idLinha.indexOf("_avo_") + 5, idLinha.length());
					if(codEttPai != null && !codEttPai.equals("")) {
						estruturaPai = (EstruturaEtt) estruturaDao.buscar(EstruturaEtt.class, new Long(codEttPai));
					}	
				}
				
				if(!codIett.equals("")){
					itemEstruturaSelecionado = (ItemEstruturaIett) itemDao.buscar(ItemEstruturaIett.class, new Long(codIett));	
				}	
				
				
				
			} 

			/**************************************************************
			 * Imprime o item e chama, se for o caso, o imprimir dos filhos
			 **************************************************************/
						
			//Seta os filhos que o nó pai pode exibir no formato: "idLinhaFilho1:idLinhaFilho2:idLinhaFilho3"
			String filhosInputHidden = "";
			
			boolean ehCaminho = false;
			
			// no caso de um item associado ou uma estrutura "filha" de uma estrutura virtual
			if(isEstruturaItemFilhaEstruturaVirtual != null && isEstruturaItemFilhaEstruturaVirtual.equals("S")) {
				
				
				//se for uma estrutura virtual expandida, o caminho da arvores vai conter o nome da estrutura real + "v" no final para fazer a distinção
				// com as estruturas reais presentes na árvore 
				ehCaminho = caminhoArvore.contains(idLinha.substring(0, idLinha.indexOf("_pai_")) + "v");
				
				if(idLinha.contains("_avo")) {
					filhosHtml = imprimeItemEstruturaVirtual(itemEstruturaSelecionado, estruturaSelecionada, estruturaPai, 
							 tipoItemClicado, request, caminhoArvore, ultimoIdLinhaDetalhado, 
							 idLinha);
				} else if(ultimoIdLinhaExpandido != null && ultimoIdLinhaExpandido.contains("_avo")){
					filhosHtml = imprimeItemEstruturaVirtual(itemEstruturaSelecionado, estruturaSelecionada, estruturaPai, 
							 tipoItemClicado, request, caminhoArvore, ultimoIdLinhaDetalhado, 
							 ultimoIdLinhaExpandido);
				}
				
				
			} else {
				
				ehCaminho = caminhoArvore.contains(idLinha.substring(0, idLinha.indexOf("_pai_")));
				filhosHtml = imprimeItem(itemEstruturaSelecionado, estruturaSelecionada, tipoItemClicado, request, caminhoArvore, ultimoIdLinhaDetalhado);
			}
				
			if(ehCaminho){
				if(tipoItemClicado.equals("itemEstrutura")){
					
					// se nao for um item associado a uma estrutura virtual
					if(isEstruturaItemFilhaEstruturaVirtual == null || !isEstruturaItemFilhaEstruturaVirtual.equals("S")){	
						
						List lEstruturas = estruturaDao.getSetEstruturasItem(itemEstruturaSelecionado);		
						if(lEstruturas != null && !lEstruturas.isEmpty()) {
							Iterator itEstrutura = lEstruturas.iterator();
							boolean exibirEstrutura = true;
							while(itEstrutura.hasNext()){
								EstruturaEtt estrutura = (EstruturaEtt) itEstrutura.next();
								
								exibirEstrutura = estruturaDao.verificarExibeEstrutura(estrutura, itemEstruturaSelecionado);
								
								if(exibirEstrutura) {
									String idLinhaFilho = "ett" + estrutura.getCodEtt() + "_pai_iett" + itemEstruturaSelecionado.getCodIett();
									
									if(!filhosInputHidden.equals("")){
										filhosInputHidden += ":" + idLinhaFilho;
									}else{
										filhosInputHidden = idLinhaFilho;
									}
									
									//chama a função para os filhos
									filhosHtml += geraFilhosHtml(idLinhaFilho, caminhoArvore, request, ultimoIdLinhaDetalhado, ehTelaListagem,
																 isEstruturaItemFilhaEstruturaVirtual);
								}
							}
						} else {
							// se o item nao tiver nenhuma estrutura filha
							filhosInputHidden = "0";
						}
					} 
				}
				else if(tipoItemClicado.equals("estrutura")){
					
					
					//ESTRUTURA VIRTUAL
					if(estruturaSelecionada.isVirtual()) {	
						
						String codAvo = "";
				
						
						//salva o codigo dos itens aos quais a estrutura pertence
						Map mapItensEstruturaVirtual = null;
						ActionEstrutura action = new ActionEstrutura();
						mapItensEstruturaVirtual = action.montarMapItensEstruturaVirtualComPermissao(estruturaSelecionada, seguranca);
				
						Iterator itEstruturas = mapItensEstruturaVirtual.keySet().iterator();
						
						if(ultimoIdLinhaExpandido != null && ultimoIdLinhaExpandido.contains("_avo")) {
							codAvo = ultimoIdLinhaExpandido.substring(ultimoIdLinhaExpandido.indexOf("_avo_") + 5, ultimoIdLinhaExpandido.length());
						} else if(itemEstruturaSelecionado != null){
							codAvo = itemEstruturaSelecionado.getCodIett().toString();
						}
							
						//chama as estruturas filhas	
						while(itEstruturas.hasNext()){
							String filhosItensVirtuaisHidden = "";
							EstruturaEtt estruturaFilha = (EstruturaEtt) itEstruturas.next();
							String idLinhaFilho = "ett" + estruturaFilha.getCodEtt() + "_pai_ett" + estruturaSelecionada.getCodEtt() + "_avo_" + codAvo; 
						
								
							if(!filhosInputHidden.equals("")){
								filhosInputHidden += ":" + idLinhaFilho;
							}else{
								filhosInputHidden = idLinhaFilho;
							}
							
							//chama a função para os filhos
							filhosHtml += geraFilhosHtml(idLinhaFilho, caminhoArvore, request, ultimoIdLinhaDetalhado, ehTelaListagem, "S");
							
							//chama os itens de cada estrutura "filha"
							if(caminhoArvore.contains(idLinhaFilho.substring(0, idLinhaFilho.indexOf("_pai_")) + "v")) {
								
								List listaItensEstrutura = (List) mapItensEstruturaVirtual.get(estruturaFilha);
								Iterator itLisItensEstrutura = listaItensEstrutura.iterator();
								while(itLisItensEstrutura.hasNext()) {
									ItemEstruturaIett item = (ItemEstruturaIett) itLisItensEstrutura.next();
									 String idLinhaNeto = "iett" + item.getCodIett() + "_pai_ett" + estruturaSelecionada.getCodEtt() + "_avo_" + codAvo;
									 
									 //adiciona o avo 
									 if(!filhosItensVirtuaisHidden.equals("")){
										 filhosItensVirtuaisHidden += ":" + idLinhaNeto; 
									}else{
										filhosItensVirtuaisHidden = idLinhaNeto; 
									}
									 
									filhosHtml += geraFilhosHtml(idLinhaNeto, caminhoArvore, request, ultimoIdLinhaDetalhado, ehTelaListagem, "S");
									
									//cria um hidden que guarda o valor zero para os filhos dos itens associados já que os itens associados não possui nenhum filho
									filhosHtml += "<input type=\"hidden\" id=\"inputHiddenFilhos_" + idLinhaNeto + "\" value=\"0\">";
									
								}
								
								//cria um hidden com os filhos das estruturas "filhas"
								filhosHtml += "<input type=\"hidden\" id=\"inputHiddenFilhos_" +	idLinhaFilho + 	"\" value=\"" + filhosItensVirtuaisHidden + "\">";
										
								
							} else {
								
								//cria um hidden que guarda o valor zero para as estruturas "filhas" que não foram expandidas
								filhosHtml += "<input type=\"hidden\" id=\"inputHiddenFilhos_" +	idLinhaFilho + "\" value=\"0\">";
								
							}
							
						}
						
						
					// se for uma estrutura normal	
					}	else if(isEstruturaItemFilhaEstruturaVirtual == null || !isEstruturaItemFilhaEstruturaVirtual.equals("S")){	
					
					
						List listaItensEstrutura = null; //Lista de ItemEstruturas filhas
						List lColunas = null;
						
						lColunas = estruturaDao.getAtributosAcessoEstruturaArvore(estruturaSelecionada);
						
						if(lColunas != null && lColunas.size() > 0) {				
							listaItensEstrutura = itemDao.getItensFilho(itemEstruturaSelecionado, estruturaSelecionada, lColunas);
						}
						else {
							listaItensEstrutura = itemDao.getItensFilho(itemEstruturaSelecionado, estruturaSelecionada, "");
						}
						
						listaItensEstrutura = itemDao.getItensIndConclusao(listaItensEstrutura, radConcluido);
	
						if(listaItensEstrutura != null && !listaItensEstrutura.isEmpty()) {		
						
							//Percorre todos os itens filhos
							Iterator itItens = listaItensEstrutura.iterator();
							while (itItens.hasNext()) {
											
								ItemEstruturaIett item = (ItemEstruturaIett) itItens.next();
								
								if ((item.getIndAtivoIett() != null || !"".equals(item.getIndAtivoIett())) 
																&& !"N".equals(item.getIndAtivoIett())) {
									
									validaPermissao.permissoesItem(item, seguranca.getUsuario(), seguranca.getGruposAcesso());				
									boolean permissaoAcessoItem = validaPermissao.permissaoExcluirItem() || validaPermissao.permissaoConsultarItem();
									boolean permissaoAcessoItensFilhos = false;
						
									/* hint de otimização. Só testa se tem permissão para os filhos (recursivo) se não tiver para si próprio */			
									if(!permissaoAcessoItem){
										permissaoAcessoItensFilhos = validaPermissao.permissaoAcessoItensFilhos(item, seguranca.getUsuario(), seguranca.getGruposAcesso());
									}				
									
									if(permissaoAcessoItem || permissaoAcessoItensFilhos){
										String idLinhaFilho = "iett" + item.getCodIett() + "_pai_ett" + item.getEstruturaEtt().getCodEtt();						
		
										if(!filhosInputHidden.equals("")){
											filhosInputHidden += ":" + idLinhaFilho;
										}else{
											filhosInputHidden = idLinhaFilho;
										}
		
										//chama a função para os filhos
										filhosHtml += geraFilhosHtml(idLinhaFilho, caminhoArvore, request, ultimoIdLinhaDetalhado, ehTelaListagem, 
																	 isEstruturaItemFilhaEstruturaVirtual);
									}							
								}
							}	
						} else {
							// se a estrutura não tiver nenhum item
							filhosInputHidden = "0";
						}
					
					}
					
				} 
			
			//se os filhos não forem expandidos, cria um hidden com a quantidade de filhos igual a zero
			} else{
				filhosInputHidden = "0";
			}
			
			
			//cria um hidden com os filhos das estruturas reais e dos itens
			if(tipoItemClicado.equals("estrutura")){
				
				//se for uma estrutura virtual ou uma estrutura NORMAL
				if(estruturaSelecionada.isVirtual() || isEstruturaItemFilhaEstruturaVirtual == null || !isEstruturaItemFilhaEstruturaVirtual.equals("S") ) {
					filhosHtml += "<input type=\"hidden\" id=\"inputHiddenFilhos_ett" +	codEtt +
					"_pai_iett" + codIett + "\" value=\"" + filhosInputHidden + "\">";	
				} 			
			
			}else{
				
				// se nao for um item associado a uma estrutura virtual
				if(isEstruturaItemFilhaEstruturaVirtual == null || !isEstruturaItemFilhaEstruturaVirtual.equals("S") ) {
					filhosHtml += "<input type=\"hidden\" id=\"inputHiddenFilhos_iett" + codIett +
						"_pai_ett" + codEtt + "\" value=\"" + filhosInputHidden + "\">";										
				}
			}				
			
		} catch (Exception e) {
			// Não precisa gerar exceção
		}
		
		return filhosHtml;
	}

	/**
	 * Gera uma linha de tabela Html de um determinado itemEstrutura ou estrutura
	 * @param itemEstruturaSelecionado
	 * @param estruturaSelecionada
	 * @param tipoItemClicado
	 * @param request
         * @param caminhoArvore
         * @param ultimoIdLinhaDetalhado
         * @return
	 */
	public static String imprimeItem(ItemEstruturaIett itemEstruturaSelecionado, EstruturaEtt estruturaSelecionada, String tipoItemClicado, 
									 HttpServletRequest request, List caminhoArvore, String ultimoIdLinhaDetalhado){
		String itemHtml = "";
		EstruturaDao estruturaDao = new EstruturaDao(request);
		ItemEstruturaDao itemEstruturaDao = new ItemEstruturaDao(request);
		String idLinha="";
		boolean ehCaminho = false;
		String codIett = "";
		
		String radConcluido = "";
		if("".equals(Pagina.getParamStr(request, "radConcluido"))){
			radConcluido = (String) request.getSession().getAttribute("radConcluidoSession");
		} else{
			radConcluido = Pagina.getParamStr(request, "radConcluido");
		}
		
		try {
			
			
			if(itemEstruturaSelecionado != null){
				codIett = itemEstruturaSelecionado.getCodIett().toString();
			}
			
			/*
			 * No caso de imprimir uma estrutura
			 */
			if(tipoItemClicado.equals("estrutura") || tipoItemClicado.equals("estruturaVirtual")){
				EstruturaEtt estrutura = estruturaSelecionada;
				idLinha = "ett" +  estrutura.getCodEtt() + "_pai_iett" + codIett;
				
				//label de um item na árvore de cadastro
				String nomeItem = "";	
				
				//Gera o nome do item
				if(estrutura.getLabelEtt() != null && !"".equals(estrutura.getLabelEtt())){
					nomeItem = estrutura.getNomeEtt() + " (" + estrutura.getLabelEtt() + ")"; 
				}
				else {
					nomeItem = estrutura.getNomeEtt(); 
				} 

				//Se houver label na estrutura utiliza como hint, senão utiliza o nome da estrutura.  
				
				String title = "";
				String label = estrutura.getLabelEtt();
				if (label == null || label.equals(Dominios.STRING_VAZIA)){
					title = estrutura.getNomeEtt();
				}
				else{
					title = estrutura.getNomeEtt() + " (" + estrutura.getLabelEtt() + ")";
				}
								
				/* Identação pelo nível do item */
				String identacaoImagem = "";			
				int nivel = 0;
				int identacao = 0;
				nivel = estruturaDao.getNivel(estrutura);
				identacao = 2*(nivel - 1); //fórmula deduzida por indução matemática
				for(int i = 1; i <= identacao ;i++) {
						identacaoImagem += "<img src=\"" + request.getContextPath() +
								"/images/pixel.gif\" width=\"22\" height=\"9\" alt=\"\">";
				}
				
				//gera o link ajax ou javascript local
				String linkComeco = "";
				String linkFim = "";				
				String imagemAbrirFechar = "";
				ehCaminho = caminhoArvore.contains(idLinha.substring(0, idLinha.indexOf("_pai_")));
				
				//se é caminho e não é a folha, seta javascript local 
				if( ehCaminho == true ){
					linkComeco = "<a href=\"javascript:contrairExpandirArvore('" + idLinha + "')\">";
					linkFim = "</a>"; 
					imagemAbrirFechar = "/images/expanded_button.gif"; //imagem menos
					
					String ultimoIdLinhaExpandido = Pagina.getParamStr(request, "ultimoIdLinhaExpandido");
					
					// se a ultima estrutura detalhada
					if (!estrutura.isVirtual() && ultimoIdLinhaDetalhado!=null && ultimoIdLinhaDetalhado.contains("ett" + estrutura.getCodEtt().toString() + "_pai")) {
						
						//verificar se a estrutura tem itens filhos
						List listaItensEstruturaTotal = itemEstruturaDao.getItensFilho(itemEstruturaSelecionado, estrutura, "");
						List listaItensEstrutura = itemEstruturaDao.getItensIndConclusao(listaItensEstruturaTotal, radConcluido);
						
						// se não tem filho e a ultima linha expandida é igual a estrutura do loop
						if ((listaItensEstrutura == null || listaItensEstrutura.isEmpty() )) {
							imagemAbrirFechar = "/images/square.gif";					
							// retira o link quando já foi selecionado anteriormente
							linkComeco = "";
							linkFim = "";
						}
					}					
						
				}
				//senão seta ajax
				else{
					linkComeco = "<a href=\"javascript:aoClicarExpandirItem(form, '" + idLinha + "' )\">";
					linkFim = "</a>"; 	
					imagemAbrirFechar = "/images/collapsed_button.gif"; //imagem mais
				}
			
				String consultaItem = "<a href=\\\"javascript:aoClicarConsultarItem(document.form, " + codIett + ", '" + idLinha + "');\\\">";
				
				//gera o conteúdo html
				StringBuffer conteudoHtml = new StringBuffer("");

				conteudoHtml.append("<tr id=\"" + idLinha + "\" class=\"cor_sim\" bgcolor=\"#ffffff\" onmouseout=\"javascript: destacaLinha(this,'out','cor_sim')\" onmouseover=\"javascript:destacaLinha(this,'over','')\">");
				conteudoHtml.append("<td>");
				
				conteudoHtml.append("<table>");
				conteudoHtml.append(	"<tr bgcolor=\"#ffffff\">");
				conteudoHtml.append(		"<td nowrap=\"\">");
				conteudoHtml.append(			identacaoImagem);
				conteudoHtml.append(		"</td>");
				conteudoHtml.append(		"<td id=\"iconeExpandir" + idLinha + "\" valign=\"top\">");
				conteudoHtml.append(			linkComeco);
				conteudoHtml.append(				"<img id=\"imagemMaisMenos" + idLinha + 
														"\" border=\"0\" alt=\"\" src=\"" + request.getContextPath() + imagemAbrirFechar + "\"/>");
				conteudoHtml.append(			linkFim);
				conteudoHtml.append(		"</td>");
				conteudoHtml.append(		"<td nowrap=\"\" title=\""+title+"\">");
				conteudoHtml.append(			"<img border=\"0\" alt=\"\" src=\"" + request.getContextPath()
														+ "/images/iconePasta.png\"/>");
			
				
				//se fora a tela de listagem, detalha o item
				if (ehTelaListagem){
					conteudoHtml.append("<a href=\"javascript:aoClicarDetalharItem(form, '" +  idLinha + "' )\">");
				}
				// se não for na tela de listagem, volta para a tela a de listagem
				else {
					conteudoHtml.append("<a href=\"").append(request.getContextPath()).append("/cadastroItens/listaItem/lista.jsp");
					String connector = "?"; // define o primeiro conector para parametros na url
					// Prepara o link para retorno na aba correta.
            		conteudoHtml.append(connector + "ultEttSelecionado=" + estruturaSelecionada.getCodEtt().toString() );
                   	connector = "&";
                   	conteudoHtml.append(connector + "ultimoIdLinhaDetalhado=ett" + estruturaSelecionada.getCodEtt() + "_pai_iett" + codIett);
                	connector = "&";
                	conteudoHtml.append(connector + "ultimoIdLinhaExpandido=" + Pagina.getParamStr(request,"ultimoIdLinhaExpandido"));                  	
                   	connector = "&";
                   	conteudoHtml.append("\" >");
				}
				
				conteudoHtml.append(				"<font color=\"#596d9b\"> " + nomeItem + " </font>");
				conteudoHtml.append(			"</a>");			
				conteudoHtml.append(		"</td>");
				conteudoHtml.append(	"</tr>");
				conteudoHtml.append("</table>");
				
				conteudoHtml.append("</td>");
				conteudoHtml.append("</tr>");
				
				itemHtml = conteudoHtml.toString();
			} 

			/*
			 * No caso de imprimir uma itemEstrutura
			 */			
			else if(tipoItemClicado.equals("itemEstrutura")){
				
				idLinha = "iett" + itemEstruturaSelecionado.getCodIett() + "_pai_ett" + estruturaSelecionada.getCodEtt(); 
				
				String nomeItem = "";
				ValidaPermissao validaPermissao = new ValidaPermissao();
				SegurancaECAR seguranca = (SegurancaECAR) request.getSession().getAttribute("seguranca");
				ItemEstruturaIett item = itemEstruturaSelecionado;
				
				validaPermissao.permissoesItem(item, seguranca.getUsuario(), seguranca.getGruposAcesso());				
				boolean permissaoAcessoItem = validaPermissao.permissaoExcluirItem() || validaPermissao.permissaoConsultarItem();
				boolean permissaoAcessoItensFilhos = false;
	
				/* hint de otimização. Só testa se tem permissão para os filhos (recursivo) se não tiver para si próprio */			
				if(!permissaoAcessoItem){
					permissaoAcessoItensFilhos = validaPermissao.permissaoAcessoItensFilhos(item, seguranca.getUsuario(), seguranca.getGruposAcesso());
				}				
				
				if(permissaoAcessoItem || permissaoAcessoItensFilhos){
					
					nomeItem = itemEstruturaDao.criaColunaConteudoColunaArvoreAjax(item, estruturaSelecionada);
					
					//Se houver label na estrutura utiliza como hint, senão utiliza o nome da estrutura.
					
					String titleEstrutura = "";
					String label = item.getEstruturaEtt().getLabelEtt();
					if (label == null || label.equals(Dominios.STRING_VAZIA)){
						titleEstrutura = item.getEstruturaEtt().getNomeEtt();
					}
					else{
						titleEstrutura = item.getEstruturaEtt().getNomeEtt() + " (" + item.getEstruturaEtt().getLabelEtt() + ")";
					}
					
					/* Identação pelo nível do item */
					String identacaoImagem = "";			
					int nivel = 0;
					int identacao = 0;
					nivel = item.getNivelIett().intValue();
					identacao = 2*nivel - 1; //fórmula deduzida por indução matemática
					
					for(int i = 1; i <= identacao;i++) {
							identacaoImagem += "<img src=\"" + request.getContextPath() +
									"/images/pixel.gif\" width=\"22\" height=\"9\" alt=\"\">";
					}
					
					/* Links de consultar(strHRef) e próximo nível(strHRefProx)*/
					String strHRefInicio = "";
					String strHRefFim = "";
					String strHRefProxInicio = "";
					String strHRefProxFim = "";
					List lEstruturasFilhas = (new EstruturaDao(request)).getSetEstruturasItem(item);
					
					if (validaPermissao.permissaoConsultarItem()) {
						strHRefInicio = "<a href=\"javascript:aoClicarConsultarItem(form, " + codIett + ", '" + idLinha + "')\">";
						strHRefFim = "</a>";
						strHRefProxInicio = "<a href=\"javascript:aoClicarExpandirItem(form, '" +  idLinha + "' )\">";
						strHRefProxFim = "</a>";
					} 
					else if (permissaoAcessoItensFilhos && (lEstruturasFilhas != null && lEstruturasFilhas.size() != 0)) {
						strHRefProxInicio = "<a href=\"javascript:aoClicarExpandirItem(form, '" +  idLinha + "' )\">";
						strHRefProxFim = "</a>";
					}								
					
					//gera o link ajax ou javascript local
					String imagemAbrirFechar = "";
					if(!strHRefProxInicio.equals("")){						
						ehCaminho = caminhoArvore.contains(idLinha.substring(0, idLinha.indexOf("_pai_")));
						
						//se é caminho e não é a folha, seta javascript local 
						//if( ehCaminho == true && !caminhoArvore.get(caminhoArvore.size()-1).equals(idLinha.substring(0, idLinha.indexOf("_pai_"))) ){
						if( ehCaminho == true ){	
							strHRefProxInicio = "<a href=\"javascript:contrairExpandirArvore('" + idLinha + "')\">";
							strHRefProxFim = "</a>"; 
							imagemAbrirFechar = "/images/expanded_button.gif"; //imagem menos
							
							//verifica se a estrutura do item tem estruturas filhas
							boolean existeEstruturaFilhaPodeSerVisualizada = false;
							List lEstruturas = estruturaDao.getSetEstruturasItem(item);	
							
							//verifica se alguma estrutura filha pode ser visualizada
							if(lEstruturas != null && !lEstruturas.isEmpty()) {
								Iterator itListaEstruturasFilhas = lEstruturas.iterator();
								while(!existeEstruturaFilhaPodeSerVisualizada && itListaEstruturasFilhas.hasNext()) {
									EstruturaEtt estruturaFilha = (EstruturaEtt) itListaEstruturasFilhas.next();
									existeEstruturaFilhaPodeSerVisualizada = estruturaDao.verificarExibeEstrutura(estruturaFilha, itemEstruturaSelecionado); 
								}
							}
							
						
							
							if ((lEstruturas == null ||lEstruturas.isEmpty() || !existeEstruturaFilhaPodeSerVisualizada) 
									&& (ultimoIdLinhaDetalhado!=null && ultimoIdLinhaDetalhado.contains("iett" + item.getCodIett().toString()))) {
								imagemAbrirFechar = "/images/square.gif";					
								strHRefProxInicio = "";
								strHRefProxFim = ""; 
							}	
						}
						//senão seta ajax
						else{
							strHRefProxInicio = "<a href=\"javascript:aoClicarExpandirItem(form, '" + idLinha + "' )\">";
							strHRefProxFim = "</a>"; 		
							imagemAbrirFechar = "/images/collapsed_button.gif"; //imagem mais
						}
						
					}
															
					StringBuffer conteudoHtml = new StringBuffer("");
					
					conteudoHtml.append("<tr id=\"" + idLinha + "\" class=\"cor_sim\" bgcolor=\"#ffffff\" onmouseout=\"javascript: destacaLinha(this,'out','cor_sim')\" onmouseover=\"javascript:destacaLinha(this,'over','')\">");
					conteudoHtml.append("<td>");

					conteudoHtml.append("<table>");
					conteudoHtml.append(	"<tr bgcolor=\"#ffffff\">");
					conteudoHtml.append(		"<td nowrap=\"\">");
					conteudoHtml.append(			identacaoImagem);
					conteudoHtml.append(		"</td>");
					conteudoHtml.append(		"<td id=\"iconeExpandir" + idLinha + "\" valign=\"top\">");
					conteudoHtml.append(			strHRefProxInicio); 
					conteudoHtml.append(				"<img id=\"imagemMaisMenos" + idLinha + 
															"\" border=\"0\" alt=\"\" src=\"" + request.getContextPath() + imagemAbrirFechar + "\"/>");
					conteudoHtml.append(			strHRefProxFim);					
					conteudoHtml.append(		"</td>");
					conteudoHtml.append(		"<td title=\""+titleEstrutura+"\" nowrap=\"\">");
					conteudoHtml.append(			strHRefInicio);
					conteudoHtml.append(				"<font color=\"#596d9b\"> " + nomeItem + " </font>");
					conteudoHtml.append(			strHRefFim);
					conteudoHtml.append(		"</td>");
					conteudoHtml.append(	"</tr>");
					conteudoHtml.append("</table>");
					
					conteudoHtml.append("</td>");
					conteudoHtml.append("</tr>");
					
					itemHtml = conteudoHtml.toString();

				}

			}		
			
		} catch (Exception e) {
			// Gera String vazia
			itemHtml = "";
		}
		
		return itemHtml;
	}
	
	
	/**
	 * Gera uma linha de tabela Html de um determinado itemEstrutura associado a uma estrutura virtual ou estrutura "filha" 
	 * 
         * @param itemEstruturaSelecionado
         * @param estruturaSelecionada
         * @param estruturaPai
         * @param request
         * @param caminhoArvore
         * @param tipoItemClicado
         * @param ultimoIdLinhaDetalhado
         * @param ultimoIdLinhaExpandido
         * @return String itemHtml
	 */
	public static String imprimeItemEstruturaVirtual(ItemEstruturaIett itemEstruturaSelecionado, EstruturaEtt estruturaSelecionada,EstruturaEtt estruturaPai,
													String tipoItemClicado, HttpServletRequest request, List caminhoArvore, String ultimoIdLinhaDetalhado,
													String ultimoIdLinhaExpandido){
		String itemHtml = "";
		EstruturaDao estruturaDao = new EstruturaDao(request);
		ItemEstruturaDao itemEstruturaDao = new ItemEstruturaDao(request);
		String idLinha="";
		boolean ehCaminho = false;
		String codIett = "";
		String codAvo = "";
		
		try {
			
			
			if(itemEstruturaSelecionado != null){
				codIett = itemEstruturaSelecionado.getCodIett().toString();
			}
			
			/*
			 * No caso de imprimir uma estrutura "filha"
			 */
			if(tipoItemClicado.equals("estrutura")) {
				EstruturaEtt estrutura = estruturaSelecionada;
				String codEttPai = "";
			
				if(estruturaPai != null){
					codEttPai = estruturaPai.getCodEtt().toString();
				}
				
				if(ultimoIdLinhaExpandido!=null && ultimoIdLinhaExpandido.contains("_avo_") ) {
					codAvo =  ultimoIdLinhaExpandido.substring(ultimoIdLinhaExpandido.indexOf("_avo_") + 5, ultimoIdLinhaExpandido.length());
				} else if(itemEstruturaSelecionado != null) {
					codAvo =  itemEstruturaSelecionado.getCodIett().toString();
				}
					
				idLinha = "ett" +  estrutura.getCodEtt() + "_pai_ett" + codEttPai + "_avo_" + codAvo ;
				
				//label de um item na árvore de cadastro
				String nomeItem = "";	
				
				//Gera o nome do item
				if(estrutura.getLabelEtt() != null && !"".equals(estrutura.getLabelEtt())){
					nomeItem = estrutura.getNomeEtt() + " (" + estrutura.getLabelEtt() + ")"; 
				} else {
					nomeItem = estrutura.getNomeEtt(); 
				} 

				String title = "";
				String label = estrutura.getLabelEtt();
				if (label == null || label.equals(Dominios.STRING_VAZIA)){
					title = estrutura.getNomeEtt();
				}
				else{
					title = estrutura.getNomeEtt() + " (" + estrutura.getLabelEtt() + ")";
				}
												
				/* Identação pelo nível do item */
				String identacaoImagem = "";			
				int nivel = 0;
				int identacao = 0;
				nivel = estruturaDao.getNivel(estruturaPai);
				identacao = 2*(nivel - 1); //fórmula deduzida por indução matemática
				for(int i = 0; i <= identacao ;i++) {
						identacaoImagem += "<img src=\"" + request.getContextPath() +
								"/images/pixel.gif\" width=\"22\" height=\"9\" alt=\"\">";
				}
				
				//gera o link ajax ou javascript local
				String linkComeco = "";
				String linkFim = "";				
				String imagemAbrirFechar = "";
				String codigoEstrutura = idLinha.substring(0, idLinha.indexOf("_pai_")).concat("v");
				ehCaminho = caminhoArvore.contains(codigoEstrutura);
				
				//se é caminho e não é a folha, seta javascript local 
				if( ehCaminho == true ){
					linkComeco = "<a href=\"javascript:contrairExpandirArvore('" + ultimoIdLinhaExpandido + "')\">";
					linkFim = "</a>"; 
					imagemAbrirFechar = "/images/expanded_button.gif"; //imagem menos
				}
				//senão seta ajax
				else{
					linkComeco = "<a href=\"javascript:aoClicarExpandirItem(form, '" + idLinha + "' )\">";
					linkFim = "</a>"; 	
					imagemAbrirFechar = "/images/collapsed_button.gif"; //imagem mais
				}

				
				StringBuffer conteudoHtml = new StringBuffer("");

				conteudoHtml.append("<tr id=\"" + idLinha + "\" class=\"cor_sim\" bgcolor=\"#ffffff\" onmouseout=\"javascript: destacaLinha(this,'out','cor_sim')\" onmouseover=\"javascript:destacaLinha(this,'over','')\">");
				conteudoHtml.append("<td>");
				
				conteudoHtml.append("<table>");
				conteudoHtml.append(	"<tr bgcolor=\"#ffffff\">");
				conteudoHtml.append(		"<td nowrap=\"\">");
				conteudoHtml.append(			identacaoImagem);
				conteudoHtml.append(		"</td>");
				conteudoHtml.append(		"<td id=\"iconeExpandir" + idLinha + "\" valign=\"top\">");
				conteudoHtml.append(			linkComeco);
				conteudoHtml.append(				"<img id=\"imagemMaisMenos" + idLinha + 
														"\" border=\"0\" alt=\"\" src=\"" + request.getContextPath() + imagemAbrirFechar + "\"/>");
				conteudoHtml.append(			linkFim);
				conteudoHtml.append(		"</td>");
				conteudoHtml.append(		"<td nowrap=\"\" title=\""+title+"\">");
				conteudoHtml.append(			"<img border=\"0\" alt=\"\" src=\"" + request.getContextPath()
														+ "/images/iconePasta.png\"/>");
				
				//se fora a tela de listagem, detalha o item
				if (ehTelaListagem){
					conteudoHtml.append("<a href=\"javascript:aoClicarDetalharItem(form, '" +  ultimoIdLinhaDetalhado + "' )\">");
				}
				// se não for na tela de listagem, volta para a tela a de listagem
				else {
					
					
					conteudoHtml.append("<a href=\"").append(request.getContextPath()).append("/cadastroItens/listaItem/lista.jsp");
					String connector = "?"; // define o primeiro conector para parametros na url
					
										
					// Prepara o link para retorno na aba correta.
					if(estruturaPai != null) {
						
						String idLinhaEstruturaVirtual = "ett" + estruturaPai.getCodEtt().toString() + "_pai_iett";
						if(itemEstruturaSelecionado != null) {
							idLinhaEstruturaVirtual += itemEstruturaSelecionado.getCodIett().toString();
						} else 	if(!codAvo.equals("")) {
							idLinhaEstruturaVirtual += codAvo;
						}
						
						conteudoHtml.append(connector + "ultEttSelecionado=" + estruturaPai.getCodEtt().toString() );
						connector = "&";
						conteudoHtml.append(connector + "ultimoIdLinhaDetalhado=" + idLinhaEstruturaVirtual);
					 	connector = "&";
					 	conteudoHtml.append(connector + "ultimoIdLinhaExpandido=" + idLinha);
					 	connector = "&";
					}	
                  
                   	conteudoHtml.append("\" >");
				}
			
				conteudoHtml.append(				"<font color=\"#596d9b\"> " + nomeItem + " </font>");
				conteudoHtml.append(			"</a>");			
				conteudoHtml.append(		"</td>");
				conteudoHtml.append(	"</tr>");
				conteudoHtml.append("</table>");
				
				conteudoHtml.append("</td>");
				conteudoHtml.append("</tr>");
				
				itemHtml = conteudoHtml.toString();
			} 

			/*
			 * No caso de imprimir um itemEstrutura associado
			 */			
			else if(tipoItemClicado.equals("itemEstrutura")){
				
				if(ultimoIdLinhaExpandido!=null && ultimoIdLinhaExpandido.contains("_avo_") ) {
					codAvo =  ultimoIdLinhaExpandido.substring(ultimoIdLinhaExpandido.indexOf("_avo_") + 5, ultimoIdLinhaExpandido.length());
				}
					
				idLinha = "iett" + itemEstruturaSelecionado.getCodIett() + "_pai_ett" + estruturaSelecionada.getCodEtt() + "_avo_" + codAvo ;
				
				String nomeItem = "";
				ValidaPermissao validaPermissao = new ValidaPermissao();
				SegurancaECAR seguranca = (SegurancaECAR) request.getSession().getAttribute("seguranca");
				ItemEstruturaIett item = itemEstruturaSelecionado;
				
				validaPermissao.permissoesItem(item, seguranca.getUsuario(), seguranca.getGruposAcesso());				
				boolean permissaoAcessoItem = validaPermissao.permissaoConsultarItem();
				
				if(permissaoAcessoItem ){
					
					nomeItem = itemEstruturaDao.criaColunaConteudoColunaArvoreAjax(item, item.getEstruturaEtt());
					
					String titleEstrutura = "";
					String label = item.getEstruturaEtt().getLabelEtt();
					if (label == null || label.equals(Dominios.STRING_VAZIA)){
						titleEstrutura = item.getEstruturaEtt().getNomeEtt();
					}
					else{
						titleEstrutura = item.getEstruturaEtt().getNomeEtt() + " (" + item.getEstruturaEtt().getLabelEtt() + ")";
					}
										
					/* Identação pelo nível do item */
					String identacaoImagem = "";			
					int nivel = 0;
					int identacao = 0;
					nivel = estruturaDao.getNivel(estruturaSelecionada);
					identacao = 2*nivel - 1; //fórmula deduzida por indução matemática
					
					for(int i = 0; i <= identacao;i++) {
							identacaoImagem += "<img src=\"" + request.getContextPath() +
									"/images/pixel.gif\" width=\"22\" height=\"9\" alt=\"\">";
					}
					
					/* Links de consultar(strHRef) e próximo nível(strHRefProx)*/
					String strHRefInicio = "";
					String strHRefFim = "";
					String strHRefProxInicio = "";
					String strHRefProxFim = "";
					String idLinhaPaiFilho = "ett" + item.getEstruturaEtt().getCodEtt() + "_pai_ett" + estruturaSelecionada.getCodEtt().toString() +
											 "_avo_" + codAvo;
				
					strHRefInicio = "<a href=\"javascript:aoClicarConsultarItemEstruturaVirtual(form, " + codIett + ", '" + idLinhaPaiFilho + "')\">";
					strHRefFim = "</a>";
					strHRefProxInicio = "<a href=\"javascript:aoClicarExpandirItem(form, '" +  idLinha + "' )\">";
					strHRefProxFim = "</a>";
					 
					
					//gera o link ajax ou javascript local
					String imagemAbrirFechar = "";
					if(!strHRefProxInicio.equals("")){						
						ehCaminho = caminhoArvore.contains(idLinha);
						
						strHRefProxInicio = "";
						strHRefProxFim = ""; 
						imagemAbrirFechar = "/images/square.gif";
						
			
					}
															
					StringBuffer conteudoHtml = new StringBuffer("");
					
					conteudoHtml.append("<tr id=\"" + idLinha + "\" class=\"cor_sim\" bgcolor=\"#ffffff\" onmouseout=\"javascript: destacaLinha(this,'out','cor_sim')\" onmouseover=\"javascript:destacaLinha(this,'over','')\">");
					conteudoHtml.append("<td>");

					conteudoHtml.append("<table>");
					conteudoHtml.append(	"<tr bgcolor=\"#ffffff\">");
					conteudoHtml.append(		"<td nowrap=\"\">");
					conteudoHtml.append(			identacaoImagem);
					conteudoHtml.append(		"</td>");
					conteudoHtml.append(		"<td id=\"iconeExpandir" + idLinha + "\" valign=\"top\">");
					conteudoHtml.append(			strHRefProxInicio); 
					conteudoHtml.append(				"<img id=\"imagemMaisMenos" + idLinha + 
															"\" border=\"0\" alt=\"\" src=\"" + request.getContextPath() + imagemAbrirFechar + "\"/>");
					conteudoHtml.append(			strHRefProxFim);					
					conteudoHtml.append(		"</td>");
					conteudoHtml.append(		"<td title=\\\""+titleEstrutura+"\\\" nowrap=\\\"\\\">");
					conteudoHtml.append(			strHRefInicio);
					conteudoHtml.append(				"<font color=\"#596d9b\"> " + nomeItem + " </font>");
					conteudoHtml.append(			strHRefFim);
					conteudoHtml.append(		"</td>");
					conteudoHtml.append(	"</tr>");
					conteudoHtml.append("</table>");
					
					conteudoHtml.append("</td>");
					conteudoHtml.append("</tr>");
					
					itemHtml = conteudoHtml.toString();

				}

			}		
			
		} catch (Exception e) {
			// Gera String vazia
			itemHtml = "";
		}
		
		return itemHtml;
	}
	
	/**
	 * Identifica o objeto clicado e gera as abas das estruturas. 
         * @param tipoItemClicado
         * @return
	 */
	public String criaTabelaAbasEstrutura(String tipoItemClicado){
		String strRetorno="";
		StringBuffer strBuffer = new StringBuffer();
		
		strBuffer.append("\"tabelaAbasEstrutura\":");

		strBuffer.append("{\"idInsercao\":\"abasEstrutura\", \"conteudo\": \"" + geraConteudoAbasEstrutura(tipoItemClicado, true) + "\"}");
		
		strRetorno = strBuffer.toString();
		
		return strRetorno;
	}

	/**
	 * Gera as abas com as estruturas
	 * @param tipoItemClicado
	 * @return
	 */
	public String geraConteudoAbasEstrutura(String tipoItemClicado, boolean ehTelaListagem){
		String strRetorno="";
		StringBuffer strBuffer = new StringBuffer();
		EstruturaDao estruturaDao = new EstruturaDao(request);
		List lEstruturasRaiz = null;
		String codIett = ""; 
		try {

			strBuffer.append("<table width=\\\"100%\\\" cellspacing=\\\"0\\\" cellpadding=\\\"0\\\" border=\\\"0\\\">");
			strBuffer.append("	<tr>");
			strBuffer.append("		<td>");								
			
			
			if (itemEstruturaSelecionado != null){
				codIett = itemEstruturaSelecionado.getCodIett().toString();
			}
			
			// no caso de ser uma estrutura "filha" de uma estrutura virtual
			if (estruturaPai!= null && estruturaPai.isVirtual()){
				if(estruturaPai.getEstruturaEtt() != null) {
					lEstruturasRaiz = estruturaDao.getEstruturas(estruturaPai.getEstruturaEtt());//busca as irmãs das estruturas
				} else  {
					lEstruturasRaiz = estruturaDao.getEstruturaPrincipal();
				}
			} else {
				if (estruturaSelecionada.getEstruturaEtt() != null){
					lEstruturasRaiz = estruturaDao.getEstruturas(estruturaSelecionada.getEstruturaEtt());//busca as irmãs das estruturas
				} else {
					//Detalha a estrutura raiz
					lEstruturasRaiz = estruturaDao.getEstruturaPrincipal();
				}
			}	
					 				
			//lista das estruturas raiz
				
			if (lEstruturasRaiz != null){
				boolean primeiraEstrutura = true;
				Iterator itEstruturaRaiz = lEstruturasRaiz.iterator();
				while (itEstruturaRaiz.hasNext()){	
										
					EstruturaEtt estruturaRaiz = (EstruturaEtt) itEstruturaRaiz.next();		

					boolean exibirEstruturaRaiz = estruturaDao.verificarExibeEstrutura(estruturaRaiz, itemEstruturaSelecionado);
					
					if(exibirEstruturaRaiz) {
						
						//label de um item na árvore de cadastro
						String nomeItem = "";	
						
						//Gera o nome do item
						if(estruturaRaiz.getLabelEtt() != null && !"".equals(estruturaRaiz.getLabelEtt())){
							nomeItem = estruturaRaiz.getLabelEtt(); 
						}
						else {
							nomeItem = estruturaRaiz.getNomeEtt(); 
						} 
						
						String titleEstrutura = "";
						String label = estruturaRaiz.getLabelEtt();
						if (label == null || label.equals(Dominios.STRING_VAZIA)){
							titleEstrutura = estruturaRaiz.getNomeEtt();
						}
						else{
							titleEstrutura = estruturaRaiz.getNomeEtt() + " (" + estruturaRaiz.getLabelEtt() + ")";
						}
											
						String tipoAba = "";
						
						// no caso de ser uma estrutura "filha" de uma estrutura virtual
						if (estruturaPai != null){
							if (estruturaPai.equals(estruturaRaiz)){
								tipoAba = "abahabilitada";
							} else {
								tipoAba = "abadesabilitada";
							}
						
						} else if (estruturaSelecionada != null){
							if (estruturaSelecionada.equals(estruturaRaiz)){
								tipoAba = "abahabilitada";
							} else {
								tipoAba = "abadesabilitada";
							}
						} else {
							if (primeiraEstrutura){
								tipoAba = "abahabilitada";
								primeiraEstrutura = false;
							} else {
								tipoAba = "abadesabilitada";
							}
						}
											
						strBuffer.append("<table class=\\\"" + tipoAba + "\\\" style=\\\"background-color: " + estruturaRaiz.getCodCor1Ett() + "; border-bottom: " + estruturaRaiz.getCodCor1Ett() + ";\\\">");
						strBuffer.append("<tr>");
						strBuffer.append("<td nowrap>");
	
						if (tipoAba.equals("abadesabilitada"))
						{
							if (ehTelaListagem){
								strBuffer.append("<a href=\\\"#\\\" onclick=\\\"javascript:aoClicarDetalharItem(form, 'ett" + estruturaRaiz.getCodEtt() + "_pai_iett" + codIett + "' )\\\">");
							} else {
								strBuffer.append("<a href=\\\"").append(request.getContextPath()).append("/cadastroItens/listaItem/lista.jsp");
								String connector = "?"; // define o primeiro conector para parametros na url
								strBuffer.append(connector + "ultEttSelecionado=" + estruturaSelecionada.getCodEtt().toString() );
				               	connector = "&";
				            	if (itemEstruturaSelecionado != null){
				            		strBuffer.append(connector + "ultimoIdLinhaDetalhado=ett" + estruturaRaiz.getCodEtt() + "_pai_iett" + itemEstruturaSelecionado.getCodIett());
				            	} else {
				            		strBuffer.append(connector + "ultimoIdLinhaDetalhado=ett" + estruturaRaiz.getCodEtt() + "_pai_iett");
				            	}
				            	connector = "&";
				            	strBuffer.append(connector + "ultimoIdLinhaExpandido=" + Pagina.getParamStr(request,"ultimoIdLinhaExpandido"));                  	
				               	connector = "&";
				               	strBuffer.append("\\\" >");
							}
							
						
							if(estruturaRaiz.getLabelEtt() != null && !"".equals(estruturaRaiz.getLabelEtt())){
								strBuffer.append(estruturaRaiz.getLabelEtt()); 
							}
							else {
								strBuffer.append(estruturaRaiz.getNomeEtt()); 
							}
						
							strBuffer.append("</a>");
						} else { 
							if(estruturaRaiz.getLabelEtt() != null && !"".equals(estruturaRaiz.getLabelEtt())){
								strBuffer.append(estruturaRaiz.getLabelEtt()); 
							}
							else {
								strBuffer.append(estruturaRaiz.getNomeEtt()); 
							}
						}
						strBuffer.append("</td>");
						strBuffer.append("</tr>");
						strBuffer.append("</table>");
					}
				
				}
				strBuffer.append("</td>");
				strBuffer.append("</tr>");
				strBuffer.append("</table>");

			}
							
			strRetorno = strBuffer.toString();
		} catch (Exception e) {
			// TODO: handle exception
			strRetorno = "Erro de processamento!";
		}
				
		return strRetorno;
	}
	
	/**
	 * Ordena uma lista de itens através do primeiro atributo configurado para aparecer na listagem de itens da estrutura passada.
	 * 
	 * @param itens
	 * @param estruturaSelecionada
	 * @return List
	 * @throws ECARException
	 */
	private List ordenaItensPrimeiroAtributo(List itens, EstruturaEtt estruturaSelecionada) throws ECARException{
		
		List listaItensOrdenada = new ArrayList();
		List listaAtributoEstruturaListagem = new ArrayList();
		EstruturaDao estruturaDao = new EstruturaDao(request);

		List estruturaAtributos = estruturaDao.getAtributosArvoreEstrutura(estruturaSelecionada);
		
		if (estruturaAtributos != null && estruturaAtributos.size() > 0){
		
			EstruturaAtributoEttat estruturaAtributo = (EstruturaAtributoEttat) estruturaAtributos.get(0);
			
			Iterator itItens = itens.iterator();
			
			while (itItens.hasNext()){
				
				AtributoEstruturaListagemItens atributoEstruturaListagem = new AtributoEstruturaListagemItens();
				ItemEstruturaIett item = (ItemEstruturaIett)itItens.next();
				atributoEstruturaListagem.setItem(item);
				
				String nomeItem = "";
				if (estruturaAtributo.getAtributosAtb().getSisGrupoAtributoSga() == null) { //Se não for atributo livre 
					nomeItem += estruturaAtributo.iGetValor(item); 
				} else {//Se for atributo livre 
					Set<SisAtributoSatb> sisAtributos = estruturaAtributo.getAtributosAtb().getSisGrupoAtributoSga().getSisAtributoSatbs();
					
					for (SisAtributoSatb sisAtributo : sisAtributos) { //Obtem os atributos livres do grupo  
						
						ItemEstruturaSisAtributoIettSatbPK itemSisAtributoPk = new ItemEstruturaSisAtributoIettSatbPK(item.getCodIett(),sisAtributo.getCodSatb());
												
						//Busca o conteudo gravado para o atributo livre  
						ItemEstruturaSisAtributoIettSatb itemEstruturaSisAtributo = (ItemEstruturaSisAtributoIettSatb)estruturaDao.buscar(ItemEstruturaSisAtributoIettSatb.class, itemSisAtributoPk);
							
						if (itemEstruturaSisAtributo.getInformacao() != null) { //Alguns atributos livres não possuem conteudo no campo informação, caso possua deve obter a informação   
							nomeItem += itemEstruturaSisAtributo.getInformacao();
						} else {//caso não possua, deve obter apenas a descrição do atributo livre. throws ECARException{
							nomeItem += sisAtributo.getDescricaoSatb();
						}						
					}
				}
				
				atributoEstruturaListagem.setDescricao(nomeItem);
				
				listaAtributoEstruturaListagem.add(atributoEstruturaListagem);
			}
					
			Collections.sort(listaAtributoEstruturaListagem);
			
			Iterator itlistaAtributosEstruturaListagem = listaAtributoEstruturaListagem.iterator();
			
			while (itlistaAtributosEstruturaListagem.hasNext()){
				
				listaItensOrdenada.add(((AtributoEstruturaListagemItens)itlistaAtributosEstruturaListagem.next()).getItem());
			}
		}
		else{
			return itens;
		}
		
		return listaItensOrdenada;
	}
	
        /**
         *
         * @return
         */
        public ItemEstruturaIett getItemEstruturaSelecionado() {
		return itemEstruturaSelecionado;
	}

        /**
         *
         * @param itemEstruturaSelecionado
         */
        public void setItemEstrutura(ItemEstruturaIett itemEstruturaSelecionado) {
		this.itemEstruturaSelecionado = itemEstruturaSelecionado;
	}

        /**
         *
         * @return
         */
        public EstruturaEtt getEstruturaSelecionada() {
		return estruturaSelecionada;
	}

        /**
         *
         * @param estruturaSelecionada
         */
        public void setEstruturaSelecionada(EstruturaEtt estruturaSelecionada) {
		this.estruturaSelecionada = estruturaSelecionada;
	}
	
        /**
         *
         * @return
         */
        public EstruturaEtt getEstruturaPai() {
		return estruturaPai;
	}

        /**
         *
         * @param estruturaPai
         */
        public void setEstruturaPai(EstruturaEtt estruturaPai) {
		this.estruturaPai = estruturaPai;
	}

        /**
         *
         * @return
         */
        public HttpServletRequest getRequest() {
		return request;
	}

        /**
         *
         * @param request
         */
        public void setRequest(HttpServletRequest request) {
		this.request = request;
	}

}
 
