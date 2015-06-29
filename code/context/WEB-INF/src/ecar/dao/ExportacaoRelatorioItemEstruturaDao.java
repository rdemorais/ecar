package ecar.dao;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import comum.database.Dao;
import comum.util.Data;
import comum.util.Pagina;
import comum.util.Util;

import ecar.action.ActionEstrutura;
import ecar.bean.AtributoEstruturaListagemItens;
import ecar.exception.ECARException;
import ecar.login.SegurancaECAR;
import ecar.permissao.ValidaPermissao;
import ecar.pojo.AcompReferenciaAref;
import ecar.pojo.AcompReferenciaItemAri;
import ecar.pojo.AcompRelatorioArel;
import ecar.pojo.ConfiguracaoCfg;
import ecar.pojo.EstrutTpFuncAcmpEtttfa;
import ecar.pojo.EstruturaEtt;
import ecar.pojo.ItemEstruturaIett;
import ecar.pojo.ItemEstruturaSisAtributoIettSatb;
import ecar.pojo.ObjetoEstrutura;
import ecar.pojo.SisAtributoSatb;
import ecar.pojo.SisGrupoAtributoSga;
import ecar.pojo.SituacaoSit;
import ecar.pojo.StatusRelatorioSrl;
import ecar.pojo.UsuarioUsu;
import ecar.util.Dominios;



/**
 *
 * @author 70744416353
 */
public class ExportacaoRelatorioItemEstruturaDao extends Dao{

	private String separadorCampos;
	private String separadorMultivalor;
	
	/**
	 * Construtor. Chama o Session factory do Hibernate
         *
         * @param request
         */
	public ExportacaoRelatorioItemEstruturaDao(HttpServletRequest request) {
		super();
		this.request = request;
	}
	
	/**
	 * Gera o arquivo de exportação
         * @param listaEstruturas
	 * @param configuracao
         * @param itemPrincipal
         * @return List de String[], onde: posicao 0 --> caminho do arquivo gravado no servidor
	 * 					 		       posicao 1 --> nome do arquivo gerado
	 * @throws ECARException
	 */
	@SuppressWarnings("unchecked")
	public List gerarArquivosExportacaoTxt(List<EstruturaEtt> listaEstruturas, 
			ConfiguracaoCfg configuracao,  ItemEstruturaIett itemPrincipal) 
			throws ECARException {
		
		this.separadorCampos = configuracao.getSeparadorArqTXT();
		this.separadorMultivalor = configuracao.getSeparadorCampoMultivalor();
		
		ItemEstruturaDao itemEstruturaDao = new ItemEstruturaDao(null);
		EstruturaDao estruturaDao = new EstruturaDao(null);
		
		//configuração de upload
		String caminho = configuracao.getRaizUpload() + configuracao.getUploadExportacaoDemandas();
		// indica que o relatorio a ser montado é gerado a partir de cadastro
		String relatorioTipoMonitoramento = "N";
		
		ItemEstruturaIett item = new ItemEstruturaIett();
		List retorno = new ArrayList();
								
		if(!caminho.endsWith("/"))
			caminho = caminho + "/";
		
				
		try{			
									
			Iterator itEstruturas = listaEstruturas.iterator();
			EstruturaEtt estruturaCorrente;
			List itensEstruturaCorrente = new ArrayList();
			List itensPais = new ArrayList();
			List listEstruturasGeral = new ArrayList();
			
			int sequencialItensPai = 1;
			int sequencialItemPaiAtual = 1;
			int sequencialEstrutura = 1;
			
			Date dataInicio = Data.getDataAtual();
			
		
			//gera uma lista com as estruturas filhas da estrutura selecionada
			while (itEstruturas.hasNext()){
											
				estruturaCorrente = (EstruturaEtt)itEstruturas.next();
				
				//adiciona a estrutura à lista de exportação caso a mesma esteja ativa
				if (estruturaCorrente.getIndAtivoEtt().equals("S")){
					listEstruturasGeral.add(estruturaCorrente);
				}
				
				//Re-consultar a estrutura para resolver exceção de LazyInitializationException
				estruturaCorrente = (EstruturaEtt) estruturaDao.buscar(EstruturaEtt.class, estruturaCorrente.getCodEtt());
				
				if (estruturaCorrente.getEstruturaEtts() != null && !estruturaCorrente.getEstruturaEtts().isEmpty()){
											
					listEstruturasGeral.addAll(getEstruturasFilhas(estruturaCorrente));		
					
				}
			}
									
			Iterator itEstruturasGeral = listEstruturasGeral.iterator();
			
			Date dataInicioItens = Data.getDataAtual();
			Collection<ItemEstruturaIett> listaItemPrincipal = null;
			Collection<ItemEstruturaIett> listItensPorEstrutura = null;
			
			while (itEstruturasGeral.hasNext()){
				
				
				int sequencialItens = 1;
				sequencialItensPai=1;
				
				estruturaCorrente = (EstruturaEtt) itEstruturasGeral.next();
				
				// Cria um arquivo para cada estrutura
				String caminhoCompleto = "";
				
				//Cria o nome do arquivo -> <ID Sequencial>_<Nome da Estrutura Pai>_<Nome da Estrutura Utilizada>.txt
				String nomeArquivo = String.valueOf(sequencialEstrutura);
				if(estruturaCorrente.getEstruturaEtt()!=null) {
					if(!estruturaCorrente.getEstruturaEtt().getNomeEtt().equals(""))
						nomeArquivo +=   "_" + estruturaCorrente.getEstruturaEtt().getNomeEtt();
					else
						nomeArquivo +=  "_" + estruturaCorrente.getEstruturaEtt().getLabelEtt();
				}
				if(!estruturaCorrente.getNomeEtt().equals(""))
					nomeArquivo +=   "_" + estruturaCorrente.getNomeEtt();
				else
					nomeArquivo +=  "_" + estruturaCorrente.getLabelEtt();
				nomeArquivo+= ".txt";
				
				nomeArquivo = nomeArquivo.replaceAll("/", "-");
				
				sequencialEstrutura++;
							
				caminhoCompleto = caminho + nomeArquivo;
				
				String[] caminhoArquivo = {caminhoCompleto, nomeArquivo};
				retorno.add(caminhoArquivo);
				
				File diretorio = new File(caminho);
			
				if(!diretorio.isDirectory()) 
					diretorio.mkdirs();
				
				FileOutputStream arquivo = new FileOutputStream(caminhoCompleto);
												
				//Re-consultar a estrutura para resolver exceção de LazyInitializationException
				//estruturaCorrente = (EstruturaEtt) estruturaDao.buscar(EstruturaEtt.class, estruturaCorrente.getCodEtt());
				System.out.println("Inicio Obter EstruturaAtributo:"+new Date());
				List<ObjetoEstrutura> atributosOrdenados = estruturaDao.getAtributosEstruturaDadosGerais(estruturaCorrente);
				Iterator<ObjetoEstrutura> atributosOrdenadosIt = atributosOrdenados.iterator();
				System.out.println("Fim Obter EstruturaAtributo:"+new Date());				
				
				String linha = montaLinhaCabecalhoEstrutura(atributosOrdenadosIt, estruturaCorrente, relatorioTipoMonitoramento);
				
				if (itemPrincipal != null){
					//Modelo antigo
					System.out.println("Inicio Obter Lista de descendentes: "+new Date());

					List codPossiveisItensPais = montarListaCodigosPais(itemPrincipal);
					listItensPorEstrutura = itemEstruturaDao.getItensByEstruturaOrdenadosPorItemPai(estruturaCorrente.getCodEtt(), codPossiveisItensPais);

					System.out.println("Fim Obter Lista de descendentes: "+new Date());
				} else {
					listItensPorEstrutura = itemEstruturaDao.getItensByEstrutura(estruturaCorrente.getCodEtt());
				}
				
				
				if( listItensPorEstrutura != null && !listItensPorEstrutura.isEmpty() && 
					atributosOrdenados != null && !atributosOrdenados.isEmpty()) {
														
					Iterator<ItemEstruturaIett> listItensPorEstruturaIt = listItensPorEstrutura.iterator();
					ItemEstruturaIett itemEstruturaPaiCorrente = null;
					int contItens = 0;
					
					//Para cada item de estrutura criar a linhas com os campos da estrutura correspondente					
					while(listItensPorEstruturaIt.hasNext()) {
						System.out.println("Inicio do procesamento do item: "+new Date());
						atributosOrdenadosIt = atributosOrdenados.iterator();
						ItemEstruturaIett itemEstruturaCorrente = (ItemEstruturaIett) listItensPorEstruturaIt.next();
						
						// Verifica se o usuário tem acesso ao item
						ValidaPermissao validaPermissao = new ValidaPermissao();
						SegurancaECAR seguranca = (SegurancaECAR) request.getSession().getAttribute("seguranca");
						
						boolean permissaoAcessoItem = validaPermissao.permissaoConsultarItem(itemEstruturaCorrente, seguranca.getUsuario(), seguranca.getGruposAcesso()) || 
															validaPermissao.permissaoExcluirItem(itemEstruturaCorrente, seguranca.getUsuario(), seguranca.getGruposAcesso()); 
						boolean permissaoAcessoItensFilhos = false;
			
						/* hint de otimização. Só testa se tem permissão para os filhos (recursivo) se não tiver para si próprio */			
						if(!permissaoAcessoItem){
							permissaoAcessoItensFilhos = validaPermissao.permissaoAcessoItensFilhos(itemEstruturaCorrente, seguranca.getUsuario(), seguranca.getGruposAcesso());
						}
						
						
						if(permissaoAcessoItem || permissaoAcessoItensFilhos){
							
							
							//Se for o primeiro item o item pai passa a ser o pai desse item
							if (itemEstruturaPaiCorrente == null && itemEstruturaCorrente.getItemEstruturaIett() != null){
								itemEstruturaPaiCorrente = itemEstruturaCorrente.getItemEstruturaIett();
								sequencialItemPaiAtual = verificaSequencialPai(itemEstruturaCorrente,itensPais);
							}
							
							//Se o próximo item tiver um item pai diferente do anterior, o sequencial de itens pais é 
							//incrementado e um novo item pai corrente é atribuido
							if (itemEstruturaPaiCorrente != null && 
									!itemEstruturaPaiCorrente.equals(itemEstruturaCorrente.getItemEstruturaIett())) {
								
								sequencialItemPaiAtual = verificaSequencialPai(itemEstruturaCorrente,itensPais);
								itemEstruturaPaiCorrente = itemEstruturaCorrente.getItemEstruturaIett();
							}
							
							int[] pai = {itemEstruturaCorrente.getCodIett().intValue(),sequencialItensPai};
							itensPais.add(pai);
							sequencialItensPai++;
							
							
							//Consulta o itemEstrutura pois quando vai pegar a coleção de 
							//atributos livres dá erro de hibernate (lazy) 
//							itemEstruturaCorrente = (ItemEstruturaIett) itemEstruturaDao.buscar(ItemEstruturaIett.class, itemEstruturaCorrente.getCodIett());
																			
							/*
							Para cada item escreve uma linha: 
							<campo1>[separador]<campo2>[separador]<campo3>...[quebra de linha]
							itItensPorEstrutura
							Campo deve estar na mesma ordem que está sendo exibida 
							na tela de cadastro --> atributosOrdenados 
							*/
							
//							System.out.println("Inicio da montagem do registro: "+new Date());
							linha += montaLinhaItemEstruturaComCamposOrdenados(atributosOrdenadosIt, itemEstruturaCorrente, sequencialItemPaiAtual, sequencialItens, "N",
																configuracao, null, false);
							
							System.out.println("Fim do processamento do item: "+new Date());
							sequencialItens++;
						}
						
					}
				}
				
				//escreve linha no arquivo .txt
				linha = Util.normalizaCaracterMarcador(linha);
				arquivo.write(linha.getBytes(Dominios.ENCODING_DEFAULT));
				arquivo.flush();
			
			}
			
//			Date dataFim = Data.getDataAtual();
			
//			long df = dataFim.getTime();
//			long di = dataInicio.getTime();
			
//			System.out.println("Tempo de processamento dos loops: " + Data.parseDateHourSegundos( new Date(df - di) ));
		}
		catch (Exception e) {
	        	Logger logger = Logger.getLogger(this.getClass());
	        	logger.error(e);
	    }
				    	
		return retorno;		
	}
	
        /**
         *
         * @param estruturaVirtual
         * @param configuracao
         * @return
         */
        public List gerarArquivosExportacaoVirtualTxt(EstruturaEtt estruturaVirtual, ConfiguracaoCfg configuracao){
		
		this.separadorCampos = configuracao.getSeparadorArqTXT();
		this.separadorMultivalor = configuracao.getSeparadorCampoMultivalor();
		
		ItemEstruturaDao itemEstruturaDao = new ItemEstruturaDao(null);
		EstruturaDao estruturaDao = new EstruturaDao(null);
		
		//configuração de upload
		String caminho = configuracao.getRaizUpload() + configuracao.getUploadExportacaoDemandas();
		// indica que o relatorio a ser montado é gerado a partir de cadastro
		String relatorioTipoMonitoramento = "N";
		
		ItemEstruturaIett item = new ItemEstruturaIett();
		List retorno = new ArrayList();
								
		if(!caminho.endsWith("/"))
			caminho = caminho + "/";
		
				
		try{
									
			List<ItemEstruturaIett> itensOrdenadosPorPai = new ItemEstruturaDao(request).getItensOrdenadosPorItemPai(estruturaVirtual.getItensEstruturaVirtual());
			
			Map itensEstruturaVirtual = new ActionEstrutura().montarMapItensEstruturaVirtualOrdenadosPorPai(itensOrdenadosPorPai);
						
			EstruturaEtt estruturaCorrente = null;
			ItemEstruturaIett paiAnterior = null;
												
			Iterator itItensPorEstrutura = itensEstruturaVirtual.keySet().iterator();
			
			int sequencialEstrutura = 1;			
			int sequencialItensPai = 0;
			int sequencialItemPaiAtual = 0;
									
			while (itItensPorEstrutura.hasNext()){
				
				estruturaCorrente = (EstruturaEtt) itItensPorEstrutura.next();
				int sequencialItens = 1;
								
				// Cria um arquivo para cada estrutura
				String caminhoCompleto = "";
				
				//Cria o nome do arquivo -> <ID Sequencial>_<Nome da Estrutura Pai>_<Nome da Estrutura Utilizada>.txt
				String nomeArquivo = String.valueOf(sequencialEstrutura);
				if(estruturaCorrente.getEstruturaEtt() != null) {
					if(!estruturaCorrente.getEstruturaEtt().getNomeEtt().equals(""))
						nomeArquivo +=   "_" + estruturaCorrente.getEstruturaEtt().getNomeEtt();
					else
						nomeArquivo +=  "_" + estruturaCorrente.getEstruturaEtt().getLabelEtt();
				}
				if(!estruturaCorrente.getNomeEtt().equals(""))
					nomeArquivo +=   "_" + estruturaCorrente.getNomeEtt();
				else
					nomeArquivo +=  "_" + estruturaCorrente.getLabelEtt();
				nomeArquivo+= ".txt";
				
				nomeArquivo = nomeArquivo.replaceAll("/", "-");
				
				sequencialEstrutura++;
							
				caminhoCompleto = caminho + nomeArquivo;
				
				String[] caminhoArquivo = {caminhoCompleto, nomeArquivo};
				retorno.add(caminhoArquivo);
	 			
				File diretorio = new File(caminho);
			
				if(!diretorio.isDirectory()) 
					diretorio.mkdirs();
				
				FileOutputStream arquivo = new FileOutputStream(caminhoCompleto);
				
				//Re-consultar a estrutura para resolver exceção de LazyInitializationException
//				estruturaCorrente = (EstruturaEtt) estruturaDao.buscar(EstruturaEtt.class, estruturaCorrente.getCodEtt());
				
				List<ObjetoEstrutura> atributosOrdenados = estruturaDao.getAtributosEstruturaDadosGerais(estruturaCorrente);
				Iterator<ObjetoEstrutura> atributosOrdenadosIt = atributosOrdenados.iterator();
								
				String linha = montaLinhaCabecalhoEstrutura(atributosOrdenadosIt, estruturaCorrente, relatorioTipoMonitoramento);
				
				List<ItemEstruturaIett> listItensEstruturaCorrente = (List<ItemEstruturaIett>) itensEstruturaVirtual.get(estruturaCorrente);
				
				if( listItensEstruturaCorrente != null && !listItensEstruturaCorrente.isEmpty() && 
						atributosOrdenados != null && !atributosOrdenados.isEmpty()) {
															
						Iterator<ItemEstruturaIett> listItensPorEstruturaIt = listItensEstruturaCorrente.iterator();
						
						int contItens = 0;
						
						//Para cada item de estrutura criar a linhas com os campos da estrutura correspondente					
						while(listItensPorEstruturaIt.hasNext()) {
						
							atributosOrdenadosIt = atributosOrdenados.iterator();
							ItemEstruturaIett itemEstruturaCorrente = (ItemEstruturaIett) listItensPorEstruturaIt.next();
														
							// Verifica se o usuário tem acesso ao item
							ValidaPermissao validaPermissao = new ValidaPermissao();
							SegurancaECAR seguranca = (SegurancaECAR) request.getSession().getAttribute("seguranca");
							
							boolean permissaoAcessoItem = validaPermissao.permissaoConsultarItem(itemEstruturaCorrente, seguranca.getUsuario(), seguranca.getGruposAcesso()) || 
																validaPermissao.permissaoExcluirItem(itemEstruturaCorrente, seguranca.getUsuario(), seguranca.getGruposAcesso());
						
							if (permissaoAcessoItem){
																								
								if (itemEstruturaCorrente.getItemEstruturaIett() != null && !itemEstruturaCorrente.getItemEstruturaIett().equals(paiAnterior)){
									
									sequencialItemPaiAtual ++;
									paiAnterior = itemEstruturaCorrente.getItemEstruturaIett();
								}
								
								/*
								Para cada item escreve uma linha: 
								<campo1>[separador]<campo2>[separador]<campo3>...[quebra de linha]
								itItensPorEstrutura
								Campo deve estar na mesma ordem que está sendo exibida 
								na tela de cadastro --> atributosOrdenados 
								*/
																								
								linha += montaLinhaItemEstruturaComCamposOrdenados(atributosOrdenadosIt, itemEstruturaCorrente, sequencialItemPaiAtual, sequencialItens, "N",
										configuracao, null, true);
				
								sequencialItens++;
							}
						}
				}
				
				//escreve linha no arquivo .txt
				linha = Util.normalizaCaracterMarcador(linha);
				arquivo.write(linha.getBytes());
				arquivo.flush();
			}
						
		}
		catch (ECARException e){
			Logger logger = Logger.getLogger(this.getClass());
        	logger.error(e);
        	
        	e.printStackTrace();
		}
		catch (Exception e) {
        	Logger logger = Logger.getLogger(this.getClass());
        	logger.error(e);
        	
        	e.printStackTrace();
		}
		
		return retorno;
	}
	
	/**
	 * Método que busca o sequencial do item do pai na lista de pais formada por (codIett, sequencial) com todos os itens
	 * de estrutura
	 * 	 
	 * 
	 * @param itemEstruturaCorrente
	 * @param itensPais
	 * @return int
	 */
	private int verificaSequencialPai(ItemEstruturaIett itemEstruturaCorrente, List itensPais ){
		int sequencial = 0;
		
		for(int i=0; i<itensPais.size(); i++) {
			int[] pai = (int[]) itensPais.get(i);
			//int[] pai = {itemEstruturaCorrente.getCodIett().intValue(),sequencialItensPai};
			if(itemEstruturaCorrente.getItemEstruturaIett() != null && pai[0] == itemEstruturaCorrente.getItemEstruturaIett().getCodIett().intValue()){
				sequencial= pai[1];
				break;
			}
		}
		
		return sequencial;
	}
	
	
	//DAVI
	private List<Long> montarListaCodigosPais(ItemEstruturaIett itemPai){
		
		List<Long> possiveisItensPais = new ArrayList<Long>();
		
		possiveisItensPais.add(itemPai.getCodIett());
		
		if (itemPai.getItemEstruturaIetts() != null && itemPai.getItemEstruturaIetts().size() > 0){
			
			Iterator<ItemEstruturaIett> itensFilhosIt = itemPai.getItemEstruturaIetts().iterator();
			
			while (itensFilhosIt.hasNext()){
				
				possiveisItensPais.addAll(montarListaCodigosPais((ItemEstruturaIett)itensFilhosIt.next()));
			}
		}
		
		return possiveisItensPais;
	}
	
	private String montaLinhaCabecalhoEstrutura(Iterator<ObjetoEstrutura> atributosOrdenadosIt, EstruturaEtt estruturaCorrente, String relatorioTipoMonitoramento)
			throws ECARException {
		
		String linhaRetorno = "";
		
		
		try {
		
			// se for relatorio a partir de cadastro adiciona o nome dos itens
			if(relatorioTipoMonitoramento.equals("N")) {
				if (estruturaCorrente.getEstruturaEtt() != null){
					linhaRetorno += "ID_" + estruturaCorrente.getEstruturaEtt().getNomeEtt().replaceAll("\n", " ").replaceAll("\r"," ").replaceAll("\t", " ");
					//definir separador
					linhaRetorno += this.separadorCampos;
				} else {
					linhaRetorno += this.separadorCampos;
				}
				
				linhaRetorno += "ID_" + estruturaCorrente.getNomeEtt().replaceAll("\n", " ").replaceAll("\r"," ").replaceAll("\t", " ");
				linhaRetorno += this.separadorCampos;
		    }	
			
			while(atributosOrdenadosIt.hasNext()) {
				ObjetoEstrutura atributo = (ObjetoEstrutura) atributosOrdenadosIt.next();
	
				//Se opcional é falso é porque o atributo é obrigatório. 
	        	if(atributo.iGetIndOpcional() == null || atributo.iGetIndOpcional().booleanValue() == false){
	    			// Mantis 6514: para atributos não opcionais verificar pelo campo "sequencia de apresentacao em telas de informaï¿½ï¿½o"
	        		if(atributo.iGetSequenciaCampoEmTela() != null && atributo.iGetSequenciaCampoEmTela().intValue() != 0){
	        			if (atributo.iGetLabel() != null && !"".equals(atributo.iGetLabel())){
	        				linhaRetorno +=	atributo.iGetLabel().replaceAll("\n", " ").replaceAll("\r"," ").replaceAll("\t", " ");
	        			}
	        			else{
	        				linhaRetorno +=	atributo.iGetNome().replaceAll("\n", " ").replaceAll("\r"," ").replaceAll("\t", " ");
	        			}
	        			
	        			// escreve o separador entre cada campo
	        			linhaRetorno += this.separadorCampos;
	        		}
	        	}
	        	else if(atributo.iGetNome().equals("nivelPlanejamento")){
	        		SisGrupoAtributoSga grupoAtributo = new ConfiguracaoDao(null).getConfiguracao().getSisGrupoAtributoSgaByCodSgaGrAtrNvPlan();
	        		linhaRetorno += grupoAtributo.getDescricaoSga().replaceAll("\n", " ").replaceAll("\r"," ").replaceAll("\t", " ");
	        		linhaRetorno += this.separadorCampos;
	        		
	        	} else {
	        		
	        		if (atributo.iGetLabel() != null && !"".equals(atributo.iGetLabel())){
	    				linhaRetorno +=	atributo.iGetLabel().replaceAll("\n", " ").replaceAll("\r"," ").replaceAll("\t", " ");
	    			}
	    			else{
	    				linhaRetorno +=	atributo.iGetNome().replaceAll("\n", " ").replaceAll("\r"," ").replaceAll("\t", " ");
	    			}
	    			
	    			// escreve o separador entre cada campo
	    			linhaRetorno += this.separadorCampos;
	        	}
				
				
					
			}
	
			// escreve o fim da linha com quebra de linha
			if(!linhaRetorno.equals("")) {
								  
				linhaRetorno = linhaRetorno.replaceAll("\n"," ").replaceAll("\r"," ").replaceAll("\t", " ");
								
				//Quebra de Linha
				linhaRetorno += "\n";
			}
		
		} catch (Exception e) {
	       	Logger logger = Logger.getLogger(this.getClass());
	       	logger.error(e);
	    }
		
		return linhaRetorno;
	}
	
	/**
	 * Método que monta a linha com os campos de item ordenados. A linha deve seguir o padrão: 
	 * <campo1>[separador]<campo2>[separador]<campo3>...[quebra de linha]
	 * 	 
	 * 
	 * @param atributosOrdenadosIt
	 * @param itemEstruturaIett
	 * @return linha
	 */
    @SuppressWarnings("empty-statement")
	private String montaLinhaItemEstruturaComCamposOrdenados(Iterator<ObjetoEstrutura> atributosOrdenadosIt, 
			ItemEstruturaIett itemEstruturaIett, int sequencialItensPai, int sequencialItens, String relatorioTipoMonitoramento, 
			ConfiguracaoCfg configuracao, AcompReferenciaAref acompReferenciaAref, boolean estruturaVirtual) {
		
		String linhaRetorno = "";
		
		boolean itemPertenceAref = verificarItemPertenceAref(itemEstruturaIett, acompReferenciaAref);
		if(atributosOrdenadosIt != null) {
			
			// Se nao for do tipo monitoramento, adiciona os sequenciais
			if(relatorioTipoMonitoramento.equals("N")) {
			
				//Campo sequencial de itens pais
				if (itemEstruturaIett.getItemEstruturaIett() != null){
					linhaRetorno += sequencialItensPai;	
					//definir separador
					linhaRetorno += this.separadorCampos;
				}
				else{
					linhaRetorno += this.separadorCampos;
				}
								
				//campo sequencial do item corrente
				linhaRetorno += sequencialItens;
				linhaRetorno += this.separadorCampos;
			}	
			
			while(atributosOrdenadosIt.hasNext()) {
				ObjetoEstrutura atributo = (ObjetoEstrutura) atributosOrdenadosIt.next();
				
				try {
										
					// Se for atributo livre pega valor pelo ItemEstruturaSisAtributoIettSatbs
					if( atributo.iGetGrupoAtributosLivres() != null 
			    		&& (atributo.iGetGrupoAtributosLivres().getIndAtivoSga()!=null 
			    	    &&  atributo.iGetGrupoAtributosLivres().getIndAtivoSga().equals("S"))) {
						
						if(itemEstruturaIett.getItemEstruturaSisAtributoIettSatbs() != null) {
			    	    	String separadorParaCamposMulti = "";
			    	    	String separadorParaCamposCheck = "";
							//Iterador com os atributos livres do ítem em questao
			    	    	Iterator<ItemEstruturaSisAtributoIettSatb> itAtribLivres = itemEstruturaIett.getItemEstruturaSisAtributoIettSatbs().iterator();
			    	    	
			    	    	//Para cada atributo livre do ítem, verifica-se se este corresponde 
			    	    	//ao atributo que esta sendo tratado
			    	    	while(itAtribLivres.hasNext()){
			    	    		
			    	    		ItemEstruturaSisAtributoIettSatb itemEstruturaSisAtrib = (ItemEstruturaSisAtributoIettSatb) itAtribLivres.next();
			    	    		//Iterador com os atributos livres associados ao atributo em questão
			    	    		Iterator<SisAtributoSatb> sisAtributosSatbIt = atributo.iGetGrupoAtributosLivres().getSisAtributoSatbs().iterator();
			    	    		
			    	    		while (sisAtributosSatbIt.hasNext()) {
			    	    			SisAtributoSatb sisAtributo = (SisAtributoSatb) sisAtributosSatbIt.next();
				    	    		if( (sisAtributo).equals(itemEstruturaSisAtrib.getSisAtributoSatb()) ) {
				    	    			//Caso seja um atributo tipo texto
				    	    			if(itemEstruturaSisAtrib.getInformacao() != null) {
				    	    				if (itemPertenceAref || relatorioTipoMonitoramento.equals(Pagina.NAO)){
				    	    					linhaRetorno += separadorParaCamposMulti + itemEstruturaSisAtrib.getInformacao().replaceAll("\n", " ").replaceAll("\r"," ").replaceAll("\t", " ");
					    	    				separadorParaCamposMulti = this.separadorMultivalor;
				    	    				} 
				    	    				
				    	    			//Caso seja atributo tipo check, radio ou select
				    	    			} else if(itemEstruturaSisAtrib.getSisAtributoSatb().getDescricaoSatb() != null) {
				    	    				if (itemPertenceAref || relatorioTipoMonitoramento.equals(Pagina.NAO)){
				    	    					linhaRetorno += separadorParaCamposCheck + (itemEstruturaSisAtrib.getSisAtributoSatb().getDescricaoSatb()).replaceAll("\n", " ").replaceAll("\r"," ").replaceAll("\t", " ");;
					    	    				separadorParaCamposCheck = this.separadorMultivalor;
				    	    				} 
				    	    				
				    	    			//Caso não tenha valor associado ao atributo
				    	    			} else {
				    	    				linhaRetorno += "";
				    	    			}
				    	    		}
			    	    		}
			    	    	}
			    	    	separadorParaCamposMulti = "";
			    	    	separadorParaCamposCheck = "";
			    	    	linhaRetorno += this.separadorCampos;
			        	}
						
					} else if(atributo.iGetNome().equals("situacaoSit")) {
						if (itemPertenceAref || relatorioTipoMonitoramento.equals(Pagina.NAO)){
							String nomeAtributo = "";
							List situacoes = new SituacaoDao(null).getSituacaoByEstrutura(itemEstruturaIett.getEstruturaEtt(), new String[] {"descricaoSit","asc"});
				        	Iterator it = situacoes.iterator();
				        	while(it.hasNext() && nomeAtributo.equals("")){
				        		SituacaoSit situacao = (SituacaoSit) it.next();
				        		if(situacao!= null && itemEstruturaIett.getSituacaoSit()!= null && situacao.getDescricaoSit().equals(itemEstruturaIett.getSituacaoSit().getDescricaoSit())) {
				        			nomeAtributo = situacao.getDescricaoSit(); 
				        		}
				        	}
				        	
				        	if(!nomeAtributo.equals(""))
				        		linhaRetorno +=	nomeAtributo.replaceAll("\n", " ").replaceAll("\r"," ").replaceAll("\t", " ");
						}
						
			        	linhaRetorno += this.separadorCampos;
					
					} else if(atributo.iGetNome().equals("nivelPlanejamento")) { 
					
						if (itemPertenceAref || relatorioTipoMonitoramento.equals(Pagina.NAO)){
							if(itemEstruturaIett.getItemEstruturaNivelIettns() != null && !itemEstruturaIett.getItemEstruturaNivelIettns().isEmpty()){
						    	Iterator itNiveis = itemEstruturaIett.getItemEstruturaNivelIettns().iterator();
						    	while(itNiveis.hasNext()){
						    		SisAtributoSatb nivel = (SisAtributoSatb) itNiveis.next();
						    		linhaRetorno +=	nivel.getDescricaoSatb().replaceAll("\n", " ").replaceAll("\r"," ").replaceAll("\t", " ");
						    	}
					    	}
						}
			    		linhaRetorno += this.separadorCampos;
					} else {
						
			        	if(atributo.iGetIndOpcional() == null || atributo.iGetIndOpcional().booleanValue() == false){
			    			// Mantis 6514: para atributos nï¿½o opcionais verificar pelo campo "sequencia de apresentacao em telas de informaï¿½ï¿½o"
			        		if(atributo.iGetSequenciaCampoEmTela() != null && atributo.iGetSequenciaCampoEmTela().intValue() != 0){
			        			// Se for campo fixo pega valor direto do atributo
			        			if (itemPertenceAref || relatorioTipoMonitoramento.equals(Pagina.NAO)){
			        				linhaRetorno +=	Pagina.trocaNull(atributo.iGetValor(itemEstruturaIett)).replaceAll("\n", " ").replaceAll("\r"," ").replaceAll("\t", " ");
								}
								linhaRetorno += this.separadorCampos;
			        		}
			        	} else {
			        		// Se for campo fixo pega valor direto do atributo
			        		if (itemPertenceAref || relatorioTipoMonitoramento.equals(Pagina.NAO)){
			        			linhaRetorno +=	Pagina.trocaNull(atributo.iGetValor(itemEstruturaIett)).replaceAll("\n", " ").replaceAll("\r"," ").replaceAll("\t", " ");
			        		}
							linhaRetorno += this.separadorCampos;
			        	}
				   } 	
			
				} catch(ECARException e) {
					Logger logger = Logger.getLogger(this.getClass());
		        	logger.error(e);
				}		
			}

			// escreve o fim da linha com quebra de linha
			if(!linhaRetorno.equals("")) {
								  
				linhaRetorno = linhaRetorno.replaceAll("\n"," ").replaceAll("\r"," ").replaceAll("\t", " ");
								
				//Quebra de Linha
				linhaRetorno += "\n";
			}
		}
		
		return linhaRetorno;		
	}
	
	private List getEstruturasFilhas(EstruturaEtt estrutura) throws ECARException {
		
		List retorno = new ArrayList();
		
		Iterator itEstruturaFilha = estrutura.getEstruturaEtts().iterator();
		
		while (itEstruturaFilha.hasNext()){
			
			EstruturaEtt estruturaFilha = (EstruturaEtt) itEstruturaFilha.next();
			
			if (estruturaFilha.getIndAtivoEtt().equals("S")){
				retorno.add(estruturaFilha);
			}
			
			if (estruturaFilha.getEstruturaEtts() != null && !estruturaFilha.getEstruturaEtts().isEmpty()){
				
				retorno.addAll(getEstruturasFilhas(estruturaFilha));
			}
		}
		
		return retorno;
	}
	
	/**
	 * Gera o arquivo de exportação para o monitoramento 
	 * @param configuracao
	 * @param mesReferencia
         * @param niveisPlanejamento
         * @param codTipoAcompanhamento 
         * @param filtroSituacoes
         * @param semInformacaoNivelPlanejamento
         * @return List de String[], onde: posicao 0 --> caminho do arquivo gravado no servidor
	 * 					 		       posicao 1 --> nome do arquivo gerado
	 * @throws ECARException
	 */
	 public List gerarArquivosExportacaoMonitoramentoPorReferenciaTxt(ConfiguracaoCfg configuracao, String mesReferencia, String niveisPlanejamento,
			 					String codTipoAcompanhamento, String semInformacaoNivelPlanejamento, String filtroSituacoes) 
				throws ECARException {
		 
		String linha = ""; 
		int numeroListas = 0;
		List retorno = new ArrayList();
		List itensAcompanhamentos = new ArrayList();
		
		
		this.separadorCampos = configuracao.getSeparadorArqTXT();
		this.separadorMultivalor = configuracao.getSeparadorCampoMultivalor();
		
			
		//configuração de upload
		String caminho = configuracao.getRaizUpload() + configuracao.getUploadExportacaoDemandas();
		// indica que o relatorio é do tipo monitoramento
		String relatorioTipoMonitoramento = "S";
		
		int sequencialItensPai = 1;
		int sequencialItemPaiAtual = 1;
		
		try {
				List itensEstruturaIett = new ArrayList();
				List itensPais = new ArrayList();
				ItemEstruturaDao itemEstruturaDao = new ItemEstruturaDao(request);
				EstruturaDao estruturaDao = new EstruturaDao(request);
				AcompReferenciaItemDao acompReferenciaItemDao = new AcompReferenciaItemDao(request);
				AcompReferenciaDao acompReferenciaDao = new AcompReferenciaDao(request);
			 	SisAtributoDao sisAtributoDao = new SisAtributoDao(request);
				ItemEstruturaDao itemDao = new ItemEstruturaDao(request);
				TipoFuncAcompDao tipoFuncAcompDao = new TipoFuncAcompDao(request);
				UsuarioDao usuarioDao = new UsuarioDao(request);
				OrgaoDao orgaoDao = new OrgaoDao(request);
				CorDao corDao = new CorDao(request);
				AbaDao abaDao = new AbaDao(request);
				ConfiguracaoDao configuracaoDao = new ConfiguracaoDao(request);
				TipoAcompanhamentoDao taDao = new TipoAcompanhamentoDao(request);
				AcompReferenciaAref acompReferenciaAref = (AcompReferenciaAref) acompReferenciaDao.buscar(AcompReferenciaAref.class, Long.valueOf(mesReferencia));
				String exigeLiberarAcompanhamento = acompReferenciaAref.getTipoAcompanhamentoTa().getIndLiberarAcompTa();
				
				SegurancaECAR seguranca = (SegurancaECAR) request.getSession().getAttribute("seguranca");
				UsuarioUsu usuario = seguranca.getUsuario();
				
				String periodo = "1";
			
				List orgaos = new ArrayList();
			 
				if(mesReferencia != null && !"".equals(mesReferencia)){	
					List listNiveis = new ArrayList();
					String[] niveis = niveisPlanejamento.split(":");
					for(int n = 0; n < niveis.length; n++){
						String codNivel = niveis[n];
						
						if(!"".equals(codNivel)){
							listNiveis.add(sisAtributoDao.buscar(SisAtributoSatb.class, Long.valueOf(codNivel)));
						}
					}
					
					StatusRelatorioSrl statusLiberado = (StatusRelatorioSrl) itemDao.
			                         buscar(StatusRelatorioSrl.class, Long.valueOf(AcompReferenciaItemDao.STATUS_LIBERADO));
					
					boolean primeiro = true;		
							
					Long codArefReferencia = Long.valueOf(mesReferencia);
					int qtdePeriodosAnteriores = Integer.valueOf(periodo).intValue();
						
					/* Busca coleção com períodos a serem considereados */
					Collection periodosConsiderados = new ArrayList();
						
					if(codArefReferencia.intValue() > 0) {
						periodosConsiderados = acompReferenciaDao.getPeriodosAnteriores(codArefReferencia, qtdePeriodosAnteriores,  Long.valueOf(codTipoAcompanhamento));
					}
						
					/* seta na sessão coleção de periodos, para ser utilizado no grafico */
					Boolean isSemInformacaoNivelPlanejamento = new Boolean(false);
					if("S".equals(semInformacaoNivelPlanejamento)) {
						isSemInformacaoNivelPlanejamento = new Boolean(true);
					}
					/* Recupera lista de itens para os periodos considerados por orgão responsavel */
						
					Long situacao = null;
					if(!"".equals(filtroSituacoes)){
						situacao = Long.valueOf(filtroSituacoes);
					}
						
//					String indLiberado = "S";
//					if("N".equals(exigeLiberarAcompanhamento)){
//						indLiberado = "";
//					}
					
					//Gerar também os ítens que não estão com o acompanhamento do item liberado
					//Alteração solicitada por Ueda: Doc. de Re-Homologação de 01/10/2008 
					String indLiberado = "";
						
					Object itens[] = acompReferenciaItemDao.getItensAcompanhamentoInPeriodosByOrgaoResp(
											periodosConsiderados, listNiveis, orgaos, 
											usuario, seguranca.getGruposAcesso(), 
											Long.valueOf(codTipoAcompanhamento), null,
											isSemInformacaoNivelPlanejamento, situacao, indLiberado, "");

					itensAcompanhamentos = new ArrayList((Collection)itens[0]);
									
			  }
		
			 //separar por item de estrutura
			  List itensEstruturaIettSeparadasPorEstrutura = separaPorEstrutura(itensAcompanhamentos);
			  int sequencialEstrutura = 1;
		
			 if(itensEstruturaIettSeparadasPorEstrutura != null) {
				
				 for(int i=0; i<itensEstruturaIettSeparadasPorEstrutura.size(); i++) {
					 
					 int sequencialItens = 1;
					 sequencialItensPai=1;
					 
					 List itensEstruturaIettPorNivel = (List) itensEstruturaIettSeparadasPorEstrutura.get(i);
					 
					 
					 ItemEstruturaIett itemEstrutura= (ItemEstruturaIett) itensEstruturaIettPorNivel.get(0);
					 
					 String caminhoCompleto = "";
					//Cria o nome do arquivo -> <ID Sequencial>_<Nome da Estrutura Pai>_<Nome da Estrutura Utilizada>.txt
					 String nomeArquivo = String.valueOf(sequencialEstrutura);
					 if(itemEstrutura != null && itemEstrutura.getEstruturaEtt() != null) {	
					 	 EstruturaEtt estruturaCorrente = itemEstrutura.getEstruturaEtt();
						 if(estruturaCorrente.getEstruturaEtt()!=null) {
							if(!estruturaCorrente.getEstruturaEtt().getNomeEtt().equals(""))
								nomeArquivo +=   "_" + estruturaCorrente.getEstruturaEtt().getNomeEtt();
							else
								nomeArquivo +=  "_" + estruturaCorrente.getEstruturaEtt().getLabelEtt();
						}
						if(!estruturaCorrente.getNomeEtt().equals(""))
							nomeArquivo +=   "_" + estruturaCorrente.getNomeEtt();
						else
							nomeArquivo +=  "_" + estruturaCorrente.getLabelEtt();
					 }
					 
					 nomeArquivo+= ".txt";
					 
					 nomeArquivo = nomeArquivo.replaceAll("/", "-");
					 
					 sequencialEstrutura++;		
					
										
					 caminhoCompleto = caminho + "/" + nomeArquivo;
						
					 String[] caminhoArquivo = {caminhoCompleto, nomeArquivo};
					 retorno.add(caminhoArquivo);
			 			
					 File diretorio = new File(caminho);
					
					 if(!diretorio.isDirectory()) 
						diretorio.mkdirs();
						
					 FileOutputStream arquivo = new FileOutputStream(caminhoCompleto);
					 
					 //Monta o cabeçalho
					 if(itemEstrutura != null) {
						 //monta o cabeçalho com nomes e os pareceres
						 linha += montaCabecalhoMonitoramento(itemEstrutura, acompReferenciaAref, configuracao);
						 
					 	 //monta o cabeçalho com os dados gerais
						 List<ObjetoEstrutura> atributosOrdenados = estruturaDao.getAtributosEstruturaDadosGerais(itemEstrutura.getEstruturaEtt());
					 	 Iterator atributosOrdenadosIt = atributosOrdenados.iterator();
					 	 
					  	 
						 linha += montaLinhaCabecalhoEstrutura(atributosOrdenadosIt, itemEstrutura.getEstruturaEtt(), relatorioTipoMonitoramento);
					 }
					 
					 ItemEstruturaIett itemEstruturaPaiCorrente = null; 
					 
					 for(int j=0; j<itensEstruturaIettPorNivel.size(); j++) {
						 itemEstrutura= (ItemEstruturaIett) itensEstruturaIettPorNivel.get(j);
						 
						 //Consulta o itemEstrutura pois quando vai pegar a coleção de 
						 //atributos livres dá erro de hibernate (lazy) 
						 itemEstrutura = (ItemEstruturaIett) itemEstruturaDao.buscar(ItemEstruturaIett.class, itemEstrutura.getCodIett());
						 EstruturaEtt estruturaCorrente = null;
						
						 
						 if(itemEstrutura != null )
							 estruturaCorrente = itemEstrutura.getEstruturaEtt();
							
						 //Se for o primeiro item o item pai passa a ser o pai desse item
						 if (itemEstruturaPaiCorrente == null && itemEstrutura.getItemEstruturaIett() != null){
							 sequencialItemPaiAtual = verificaSequencialPai(itemEstrutura,itensPais);
							 itemEstruturaPaiCorrente = itemEstrutura.getItemEstruturaIett();
						 }
							
						 //Se o próximo item tiver um item pai diferente do anterior, o sequencial de itens pais é 
						 //incrementado e um novo item pai corrente é atribuido
						 if (itemEstruturaPaiCorrente != null && 
								!itemEstruturaPaiCorrente.equals(itemEstrutura.getItemEstruturaIett())){
							 sequencialItemPaiAtual = verificaSequencialPai(itemEstrutura,itensPais);
							 itemEstruturaPaiCorrente = itemEstrutura.getItemEstruturaIett();
						 }
						 
						int[] pai = {itemEstrutura.getCodIett().intValue(),sequencialItensPai};
						itensPais.add(pai);
						sequencialItensPai++;
						 
						 List<ObjetoEstrutura> atributosOrdenados = estruturaDao.getAtributosEstruturaDadosGerais(estruturaCorrente);
						 
						 if(atributosOrdenados != null) {
						 
							  Iterator<ObjetoEstrutura> atributosOrdenadosIt = atributosOrdenados.iterator();
							 
							  //monta a linha com os sequenciais e pareceres
							  linha += montaLinhaMonitoramento(itemEstrutura, acompReferenciaAref,sequencialItemPaiAtual, sequencialItens, configuracao);
							  // monta a linha com os dados básicos	
							  linha += montaLinhaItemEstruturaComCamposOrdenados(atributosOrdenadosIt,itemEstrutura,sequencialItemPaiAtual,  sequencialItens, 
									  							relatorioTipoMonitoramento, configuracao, acompReferenciaAref, false);
							  
							  sequencialItens++;
						
						 }
						
					 }	
					 
					 
					//escreve linha no arquivo .txt
					linha = Util.normalizaCaracterMarcador(linha);
					arquivo.write(linha.getBytes(Dominios.ENCODING_DEFAULT));
					arquivo.flush();
					linha = "";
				 }
			 }	
			 
			 
		 } catch (Exception e) {
	        	Logger logger = Logger.getLogger(this.getClass());
	        	logger.error(e);
	     }
		 
		 
		 return retorno;
			
	 }
	 
	 
	 /**
		 * Método que monta a linha com o cabeçalho para a criação do txt para a parte de monitoramento. A linha deve seguir o padrão: 
		 * <campo1>[separador]<campo2>[separador]<campo3>...[quebra de linha]
		 * 	 
		 * 
		 * @param itemEstruturaIett
		 * @param acompReferenciaAref
		 * @param configuracao
		 * @return linha
		 */
	 private String montaCabecalhoMonitoramento(ItemEstruturaIett itemEstruturaIett, AcompReferenciaAref acompReferenciaAref, ConfiguracaoCfg configuracao) 
	 					throws ECARException { 
		 String linhaRetorno = "";
		 
		 
		 try {
			 
			if (itemEstruturaIett != null && acompReferenciaAref != null){
				
				//Campo: Tipo Acompanhamento
				if(acompReferenciaAref.getTipoAcompanhamentoTa() != null) {
					linhaRetorno += acompReferenciaAref.getTipoAcompanhamentoTa().getDescricaoTa().replaceAll("\n", " ").replaceAll("\r"," ").replaceAll("\t", " ");
					linhaRetorno += this.separadorCampos;
				}
						
				//Campo:Mes de Referencia
				if(acompReferenciaAref.getTipoAcompanhamentoTa() != null) {
					linhaRetorno += acompReferenciaAref.getNomeAref().replaceAll("\n", " ").replaceAll("\r"," ").replaceAll("\t", " ");
					linhaRetorno += this.separadorCampos;
				}
					
			}
				
			//Campo: Nome da Estrutura Pai (quando o ítem não for do primeiro nível) 
			if (itemEstruturaIett.getEstruturaEtt().getEstruturaEtt() != null){
				linhaRetorno += "ID_" + itemEstruturaIett.getEstruturaEtt().getEstruturaEtt().getNomeEtt().replaceAll("\n", " ").replaceAll("\r"," ").replaceAll("\t", " ");
				linhaRetorno += this.separadorCampos;
			} else {
				// se nao tiver pai deixa em branco ( no caso do primeiro nivel)
				linhaRetorno += this.separadorCampos;
			}
					
			//Campo: Nome da Estrutura Atual
			if(itemEstruturaIett.getEstruturaEtt() != null) {
				linhaRetorno += "ID_" + itemEstruturaIett.getEstruturaEtt().getNomeEtt().replaceAll("\n", " ").replaceAll("\r"," ").replaceAll("\t", " ");
				linhaRetorno += this.separadorCampos;
			}
					
					
			//Campo:Nome do Item
			linhaRetorno += "Nome do Item";
			linhaRetorno += this.separadorCampos;
				
			
			//Mostrar todas as funções de acompanhamento da estrutura
			EstruturaEtt estrutura = itemEstruturaIett.getEstruturaEtt();
			Set funcoesAcompanhamento = estrutura.getEstrutTpFuncAcmpEtttfas();

			if(funcoesAcompanhamento != null) {
				Iterator itTpFunAnt = funcoesAcompanhamento.iterator();
				while (itTpFunAnt.hasNext()) {
			         EstrutTpFuncAcmpEtttfa estruturaTpFuncaoAnt = (EstrutTpFuncAcmpEtttfa) itTpFunAnt.next();
			          	
			         //Campo: Função de Acompanhamento
			         linhaRetorno += "Função de Acompanhamento";
			         linhaRetorno += this.separadorCampos;
													
					//Campo: Label da Situação em Configuração Geral 
					linhaRetorno += configuracao.getLabelSituacaoParecer().replaceAll("\n"," ").replaceAll("\r"," ").replaceAll("\t", " ");
					linhaRetorno += this.separadorCampos;
							
					//Campo: Label da Cor em Configuração Geral 
					linhaRetorno += configuracao.getLabelCorParecer().replaceAll("\n"," ").replaceAll("\r"," ").replaceAll("\t", " ");
					linhaRetorno += this.separadorCampos;
						
							 
					//Campo: Label da posição em função de acompanhamento
					if(estruturaTpFuncaoAnt.getTipoFuncAcompTpfa() != null) 
						linhaRetorno += estruturaTpFuncaoAnt.getTipoFuncAcompTpfa().getLabelPosicaoTpfa().replaceAll("\n"," ").replaceAll("\r"," ").replaceAll("\t", " ");
						linhaRetorno += this.separadorCampos;
								
					//Campo: Observação
					String ocultarObservacoesParecer = configuracao.getIndOcultarObservacoesParecer();
					if (ocultarObservacoesParecer == null || ocultarObservacoesParecer.equals("N")){
						linhaRetorno += "Observação";
						linhaRetorno += this.separadorCampos;
					}	
					
				}
		
			}
			
		
		 } catch (Exception e) {
	        	Logger logger = Logger.getLogger(this.getClass());
	        	logger.error(e);
	     }
			
	
		 return linhaRetorno;
	 }
	 
	 /**
		 * Método que monta a linha para cada item de estrutura. A linha deve seguir o padrão: 
		 * <campo1>[separador]<campo2>[separador]<campo3>...[quebra de linha]
		 * 	 
		 * 
		 * @param itemEstruturaIett
		 * @param acompReferenciaAref
		 * @param sequencialItensPai
		 * @param sequencialItens
		 * @return linha
		 */
	 private String montaLinhaMonitoramento(ItemEstruturaIett itemEstruturaIett, AcompReferenciaAref acompReferenciaAref, int sequencialItensPai, int sequencialItens,
			 			ConfiguracaoCfg configuracao) throws ECARException { 
		 
		 String linhaRetorno = "";
		 AcompReferenciaDao acompReferenciaDao = new AcompReferenciaDao(request);
		 AcompReferenciaItemDao acompReferenciaItemDao = new AcompReferenciaItemDao(request);
		 AcompReferenciaItemDao ariDao = new AcompReferenciaItemDao(request);
		 CorDao corDao = new CorDao(request);
		 TipoFuncAcompDao tipoFuncAcompDao = new TipoFuncAcompDao (request);
		 ItemEstruturaDao itemDao = new ItemEstruturaDao(request);
		 
		 
		 String flag = "";
		 
		 try {
		 		 
			//Campo: Tipo Acompanhamento - Na linha vai pular esse campo
			if(acompReferenciaAref.getTipoAcompanhamentoTa() != null) {
				linhaRetorno += "";
				linhaRetorno += this.separadorCampos;
			}
						
			//Campo:Mes de Referencia - Na linha vai pular esse campo
			if(acompReferenciaAref.getTipoAcompanhamentoTa() != null) {
				linhaRetorno += "";
				linhaRetorno += this.separadorCampos;
			}
			
			//Campo: Nome do Pai - Na linha vai ser o sequencial referenciado
			if(itemEstruturaIett.getItemEstruturaIett() != null)
				linhaRetorno += sequencialItensPai;
			linhaRetorno += this.separadorCampos;
			
			//Campo: Nome da Estrutura Atual - Na linha vai ser o sequencial
			linhaRetorno += sequencialItens;
			linhaRetorno += this.separadorCampos;
			
			// Campo: Nome do Item - Na linha -> Label
			linhaRetorno += itemEstruturaIett.getNomeIett().replaceAll("\n"," ").replaceAll("\r"," ").replaceAll("\t"," "); 
			linhaRetorno += this.separadorCampos;
			
			Collection periodosConsiderados = new ArrayList();//varre todas as estruturas do nível corrente e gera uma lista com as estruturas filhas também
			periodosConsiderados.add(acompReferenciaAref);
			List tpfaOrdenadosPorEstrutura = tipoFuncAcompDao.getFuncaoAcompOrderByEstruturas();
			String exigeLiberarAcompanhamento = acompReferenciaAref.getTipoAcompanhamentoTa().getIndLiberarAcompTa();
			String ocultarObservacoesParecer = configuracao.getIndOcultarObservacoesParecer();
			StatusRelatorioSrl statusLiberado = (StatusRelatorioSrl) itemDao.
				buscar(StatusRelatorioSrl.class, Long.valueOf(AcompReferenciaItemDao.STATUS_LIBERADO));
			SegurancaECAR seguranca = (SegurancaECAR) request.getSession().getAttribute("seguranca");
				
			Iterator itPeriodos = periodosConsiderados.iterator();
			Map  map = acompReferenciaItemDao.criarMapPeriodoAri(periodosConsiderados, itemEstruturaIett);
			
			List listaPareceres =  new ArrayList();
				
			while(itPeriodos.hasNext()){ 
				AcompReferenciaAref acompReferencia = (AcompReferenciaAref) itPeriodos.next();
				AcompReferenciaItemAri ari = (AcompReferenciaItemAri) map.get(acompReferencia);	
					
				if(!map.isEmpty()){
						
//					if("N".equals(exigeLiberarAcompanhamento) || ari.getStatusRelatorioSrl().equals(statusLiberado)){
						List relatorios = acompReferenciaItemDao.getAcompRelatorioArelOrderByFuncaoAcomp(ari, tpfaOrdenadosPorEstrutura);
						Iterator itRelatorios = relatorios.iterator();
	
						
							
						ValidaPermissao validaPermissao = new ValidaPermissao();
						List listaPermissaoTpfa = validaPermissao.permissaoVisualizarPareceres(acompReferenciaAref.getTipoAcompanhamentoTa(),
																									seguranca.getGruposAcesso());
				
						
						while(itRelatorios.hasNext()){												
									
							AcompRelatorioArel relatorio = (AcompRelatorioArel) itRelatorios.next();
							if(listaPermissaoTpfa.contains(relatorio.getTipoFuncAcompTpfa()) 
									&& validaPermissao.permissaoLeituraAcompanhamento(relatorio.getAcompReferenciaItemAri(), seguranca.getUsuario(), seguranca.getGruposAcesso())){	
								
								listaPareceres.add(relatorio);
							}	
						}
						
						
					//} else if("N".equals(exigeLiberarAcompanhamento) || (ari.getAcompRelatorioArels() != null && ari.getAcompRelatorioArels().size() > 0)) {
						
						
						// Campo: Nome do Item - Na linha -> Label
						//linhaRetorno += "N/L";
						//linhaRetorno += this.separadorCampos;
						
						//Campo Função de Acompanhamento - Na linha -> Label da função de acompanhamento
//						linhaRetorno += "N/L";	
//						linhaRetorno += this.separadorCampos;
//						
//						
//						//Campo Situção - Na linha -> Atrasado
//						linhaRetorno += "N/L";
//						linhaRetorno += this.separadorCampos;
//						
//						//Campo Cor- Linha -> vermelho
//						linhaRetorno += "N/L";
//						linhaRetorno += this.separadorCampos;
//						
//						//Campo  Label da posição em função de acompanhamento - Linha: Repete o mesmo do cabeçalho
//						linhaRetorno += "N/L";
//						linhaRetorno += this.separadorCampos;
//						
//						//Campo: Observação
//						if (ocultarObservacoesParecer == null || ocultarObservacoesParecer.equals("N")) {
//							linhaRetorno += "N/L";
//							linhaRetorno += this.separadorCampos;
//						}	
					//}
				}
			}	
			
			StatusRelatorioSrl statusAri = null;
			if(listaPareceres != null) {
				
				EstruturaEtt estrutura = itemEstruturaIett.getEstruturaEtt();
				Set funcoesAcompanhamento = estrutura.getEstrutTpFuncAcmpEtttfas();

				if(funcoesAcompanhamento != null) {
					Iterator itTpFunAnt = funcoesAcompanhamento.iterator();
					while (itTpFunAnt.hasNext()) {
						EstrutTpFuncAcmpEtttfa estruturaTpFuncaoAnt = (EstrutTpFuncAcmpEtttfa) itTpFunAnt.next();
						boolean achou = false;
						for(int i=0; i<listaPareceres.size(); i++) {
							AcompRelatorioArel relatorio = (AcompRelatorioArel) listaPareceres.get(i);
							
							statusAri = relatorio.getAcompReferenciaItemAri().getStatusRelatorioSrl();
							
							if(relatorio.getTipoFuncAcompTpfa().getCodTpfa().equals(estruturaTpFuncaoAnt.getTipoFuncAcompTpfa().getCodTpfa())) {
								
								if("S".equals(exigeLiberarAcompanhamento) && !relatorio.getAcompReferenciaItemAri().getStatusRelatorioSrl().equals(statusLiberado)){
									//Campo Função de Acompanhamento - Na linha -> Label da função de acompanhamento
									linhaRetorno += estruturaTpFuncaoAnt.getTipoFuncAcompTpfa().getLabelTpfa().replaceAll("\n"," ").replaceAll("\r"," ").replaceAll("\t", " ");
									linhaRetorno += this.separadorCampos;
									//Campo Situção - Na linha -> Atrasado
									linhaRetorno += "N/L";
									linhaRetorno += this.separadorCampos;
									//Campo Cor- Linha -> vermelho
									linhaRetorno += "N/L";
									linhaRetorno += this.separadorCampos; 
									//Campo  Label da posição em função de acompanhamento - Linha: Repete o mesmo do cabeçalho
									linhaRetorno += "N/L";
									linhaRetorno += this.separadorCampos; 
									//Campo: Observação // Pode estar configurado para ser ocultado
									if (ocultarObservacoesParecer == null || ocultarObservacoesParecer.equals("N")) {
										linhaRetorno += "N/L";
										linhaRetorno += this.separadorCampos; 
									}	
									achou = true;
									break;
								} else {
									//Campo Função de Acompanhamento - Na linha -> Label da função de acompanhamento
									linhaRetorno += relatorio.getTipoFuncAcompTpfa().getLabelTpfa().replaceAll("\n"," ").replaceAll("\r"," ").replaceAll("\t", " ");
									linhaRetorno += this.separadorCampos;
									
									
									//Campo Situção - Na linha -> Atrasado
									if(relatorio.getSituacaoSit()!= null)
										linhaRetorno += relatorio.getSituacaoSit().getDescricaoSit().replaceAll("\n"," ").replaceAll("\r"," ").replaceAll("\t", " ");
									else 
										linhaRetorno += "N/I";
									
									linhaRetorno += this.separadorCampos;
									
									
									//Campo Cor- Linha -> vermelho
									if(relatorio.getCor() != null) 
										linhaRetorno += relatorio.getCor().getSignificadoCor().replaceAll("\n"," ").replaceAll("\r"," ").replaceAll("\t", " ");
									else
										linhaRetorno  += "N/I";
									
									linhaRetorno += this.separadorCampos;
									
									//Campo  Label da posição em função de acompanhamento - Linha: Repete o mesmo do cabeçalho
									if(relatorio.getDescricaoArel() != null)
										linhaRetorno +=  relatorio.getDescricaoArel().replaceAll("\n"," ").replaceAll("\r"," ").replaceAll("\t", " ");
									linhaRetorno += this.separadorCampos;
									
									//Campo: Observação // Pode estar configurado para ser ocultado
									if (ocultarObservacoesParecer == null || ocultarObservacoesParecer.equals("N")){
										if(relatorio.getComplementoArel() != null && !"".equals(relatorio.getComplementoArel().trim()))
											linhaRetorno += relatorio.getComplementoArel().replaceAll("\n"," ").replaceAll("\r"," ").replaceAll("\t", " ");
										else
											linhaRetorno  += "N/I";
										
										linhaRetorno += this.separadorCampos;
									}
									
									achou = true;
									break;
									
								}
								
							} 
							
						}
						
						if(achou == false) {
							if("S".equals(exigeLiberarAcompanhamento) && statusAri != null && !statusAri.equals(statusLiberado)){
								//Campo Função de Acompanhamento - Na linha -> Label da função de acompanhamento
								linhaRetorno += estruturaTpFuncaoAnt.getTipoFuncAcompTpfa().getLabelTpfa().replaceAll("\n"," ").replaceAll("\r"," ").replaceAll("\t", " ");
								linhaRetorno += this.separadorCampos;
								//Campo Situção - Na linha -> Atrasado
								linhaRetorno += "N/L";
								linhaRetorno += this.separadorCampos;
								//Campo Cor- Linha -> vermelho
								linhaRetorno += "N/L";
								linhaRetorno += this.separadorCampos; 
								//Campo  Label da posição em função de acompanhamento - Linha: Repete o mesmo do cabeçalho
								linhaRetorno += "N/L";
								linhaRetorno += this.separadorCampos; 
								//Campo: Observação // Pode estar configurado para ser ocultado
								if (ocultarObservacoesParecer == null || ocultarObservacoesParecer.equals("N")) {
									linhaRetorno += "N/L";
									linhaRetorno += this.separadorCampos; 
								}	
							} else {
								//Campo Função de Acompanhamento - Na linha -> Label da função de acompanhamento
								linhaRetorno += estruturaTpFuncaoAnt.getTipoFuncAcompTpfa().getLabelTpfa().replaceAll("\n"," ").replaceAll("\r"," ").replaceAll("\t", " ");
								linhaRetorno += this.separadorCampos;
								//Campo Situção - Na linha -> Atrasado
								linhaRetorno += this.separadorCampos;
								//Campo Cor- Linha -> vermelho
								linhaRetorno += this.separadorCampos; 
								//Campo  Label da posição em função de acompanhamento - Linha: Repete o mesmo do cabeçalho
								linhaRetorno += this.separadorCampos; 
								//Campo: Observação // Pode estar configurado para ser ocultado
								if (ocultarObservacoesParecer == null || ocultarObservacoesParecer.equals("N")) {
									linhaRetorno += this.separadorCampos; 
							
								}
							}
						}
					}
				}
			} 
		 
		 } catch (Exception e) {
	        	Logger logger = Logger.getLogger(this.getClass());
	        	logger.error(e);
	     }
		 
		 return Util.stripHTML(Util.normalizaCaracterMarcador(linhaRetorno));
		 
	 }

	 
	 /**
		 * Método que separa os itens por estruturas 
		 * 	 
		 * 
		 * @param itensEstruturaIett
		 * @return itensSeparadosPorNiveis
		 */
	 public List separaPorEstrutura(List itensEstruturaIett) {
	
		 List niveis = new ArrayList();
		 
		 //verifica quais os niveis presentes
	//	 AtributoEstruturaListagemItens aeIett = (AtributoEstruturaListagemItens) itItens.next();
		 
		 Iterator itItensEstruturaIett = itensEstruturaIett.iterator();
		 
		 while(itItensEstruturaIett.hasNext()) {
			 AtributoEstruturaListagemItens aeIett = (AtributoEstruturaListagemItens) itItensEstruturaIett.next();
			 ItemEstruturaIett itemEstruturaIett = aeIett.getItem();
			// ItemEstruturaIett itemEstruturaIett = (ItemEstruturaIett) itItensEstruturaIett.next();
			 if(!niveis.contains(itemEstruturaIett.getNivelIett()))
				 niveis.add(itemEstruturaIett.getNivelIett());
		 }
		 
		 //separa por niveis
		 List itensSeparadosPorNiveis =  new ArrayList();
		 Iterator itNiveis = niveis.iterator();
		 while(itNiveis.hasNext()) {
			 Integer nivel= (Integer) itNiveis.next();
			 itensSeparadosPorNiveis.add(separaPorNivel(itensEstruturaIett, nivel));
		 }
		 
		 return(itensSeparadosPorNiveis);
	 }
	 
	 /**
		 * Método que separa os itens de acordo com o nivel passado como parametro
			 * 
		 * @param itensEstruturaIett
		 * @param nivel
		 * @return itensSeparadosPorNivel
		 */
	 public List separaPorNivel(List itensEstruturaIett, Integer nivel) {
	 
		 List itensSeparadosPorNivel =  new ArrayList();
		 Iterator itItensEstruturaIett = itensEstruturaIett.iterator();
		 while(itItensEstruturaIett.hasNext()) {
			 AtributoEstruturaListagemItens aeIett = (AtributoEstruturaListagemItens) itItensEstruturaIett.next();
		 	 ItemEstruturaIett itemEstruturaIett = aeIett.getItem();
	//		 ItemEstruturaIett itemEstruturaIett = (ItemEstruturaIett) itItensEstruturaIett.next();
			 if(itemEstruturaIett.getNivelIett().equals(nivel)) {
				 itensSeparadosPorNivel.add(itemEstruturaIett);
			 }
			 
		 }
		 
		 return itensSeparadosPorNivel;
	
	 }
	 
	 /**
	 * Método que verifica se o item passado por parâmetro pertence ao Aref
	 * 
	 * @param itensEstruturaIett
	 * @param acompReferenciaAref
	 * @return itemPertenceAref
	 */
	 private boolean verificarItemPertenceAref(ItemEstruturaIett itemEstruturaIett, AcompReferenciaAref acompReferenciaAref){
		 boolean itemPertenceAref = false;
		 
		 if (acompReferenciaAref != null && itemEstruturaIett != null){
			 if (acompReferenciaAref.getAcompReferenciaItemAris() != null){
				 Iterator itAris = acompReferenciaAref.getAcompReferenciaItemAris().iterator();
				 while (itAris.hasNext()){
					 AcompReferenciaItemAri acompReferenciaItemAri = (AcompReferenciaItemAri) itAris.next();
					 if (acompReferenciaItemAri.getItemEstruturaIett().equals(itemEstruturaIett)){
						 itemPertenceAref = true;
						 break;
					 }
				 }
			 }
		 }
		 
		 return itemPertenceAref;
	 }

	
}
