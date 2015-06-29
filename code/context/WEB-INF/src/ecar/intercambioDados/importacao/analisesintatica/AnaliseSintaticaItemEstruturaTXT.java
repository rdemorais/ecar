package ecar.intercambioDados.importacao.analisesintatica;

import java.util.Date;
import java.util.List;

import comum.database.Dao;
import comum.util.ConstantesECAR;
import comum.util.Data;
import comum.util.Util;

import ecar.exception.ECARException;
import ecar.exception.intercambioDados.SintaticValidationException;
import ecar.intercambioDados.importacao.comunicacao.ConfiguracaoImportacaoTXT;
import ecar.intercambioDados.importacao.comunicacao.IRegistro;
import ecar.intercambioDados.importacao.comunicacao.TipoRegistroEnum;
import ecar.intercambioDados.recurso.txt.RegistroTXT;
import ecar.pojo.intercambioDados.MotivoRejeicaoMtr;
import ecar.pojo.intercambioDados.PerfilIntercambioDadosPflid;
import ecar.pojo.intercambioDados.tecnologia.DadosTecnologiaPerfilTxtDtpt;
import ecar.util.Dominios;


public class AnaliseSintaticaItemEstruturaTXT extends AnaliseSintatica {

	private static final int REGISTRO_INICIAL=1;
	private static final String PADRAO_DATA_HEADER="yyyyMMddHHmmss";
	
	private int qtdeHeaderArquivo =0, qtdeTrailerArquivo = 0, qtdeEmpreendimentosValidos = 0;
	private boolean headerItemEncontrado = false;
	
	//Header Arquivo
	private static final int HE_A_TOTAL_CAMPOS = 9;
	private static final int HE_A_IDENT = 0;
	private static final int HE_A_DTGERACAO = 1;
	private static final int HE_A_SERVICO = 2;
	private static final int HE_A_ORIGEM = 3;
	private static final int HE_A_DESTINO = 4;
	private static final int HE_A_NOME_LAYOUT = 5;
	private static final int HE_A_VERSAO = 6;
	private static final int HE_A_CAMPOSITENS = 7;
	private static final int HE_A_SEQREG = 8;
	
	//Header Item
	private static final int HE_I_TOTAL_CAMPOS = 19;
	private static final int HE_I_OPERACAO = 1;
	private static final int HE_I_SEQREG = 18;
	private static final int HE_I_CODIGO = 2;
	private static final int HE_I_VALOR_ASSOCIACAO = 11;
	private static final int HE_I_VALOR_ASSOCIACAO_ITEM = 17;
	private static final int HE_I_DATA_INICIO = 5;
	private static final int HE_I_DATA_CONCLUSAO = 6;
	
	//Trailer Item
	private static final int TR_I_TOTAL_CAMPOS = 5;
	private static final int TR_I_SEQREG = 4;
	private static final int TR_I_IDENT = 0;
	private static final int TR_I_CODIGO = 1;
	private static final int TR_I_VALOR_ASSOCIACAO = 2;
	private static final int TR_I_VALOR_ASSOCIACAO_ITEM = 3; 
	
	//Trailer Arquivo
	private static final int TR_A_TOTAL_CAMPOS = 3;
	private static final int TR_A_TOTAL_EMPREENDIMENTOS = 1;
	private static final int TR_A_SEQREG = 2;
	
	
	/**
	 * Realiza a validação sintática dos objetos da lista passada.
	 * @throws SintaticValidationException 
	 */
	public boolean valida(List<IRegistro> listaRegistro, PerfilIntercambioDadosPflid perfil) throws ECARException, SintaticValidationException {
		
		int linha=AnaliseSintaticaItemEstruturaTXT.REGISTRO_INICIAL;
		int linhaAnterior=AnaliseSintaticaItemEstruturaTXT.REGISTRO_INICIAL;
		RegistroTXT registro = null, registroAnterior = null;
		final int ULTIMA_LINHA = listaRegistro.size();
		String[] posicoes = null;
		
		if (ULTIMA_LINHA != 0) {
			//Itera nas linhas da lista
			for (;linha <= listaRegistro.size();linha++) {
				int posicaoRegistroNaLista=linha -1;
					
				registro = (RegistroTXT)listaRegistro.get(posicaoRegistroNaLista);
					
				if (!registro.getLinha().equals(Dominios.STRING_VAZIA)) { //Possui conteudo na linha
				
					posicoes = Util.split(registro.getLinha(),((DadosTecnologiaPerfilTxtDtpt)perfil.getDadosTecnologiaPerfilDtp()).getSeparadorCamposDtpt());
				
					TipoRegistroEnum tipoRegistro = null;
					
					//itera nos campos da linha
					for (int campo=0;campo<posicoes.length;campo++) {
						String valorCampo = posicoes[campo];
						
						if (campo == HE_A_IDENT) {
							tipoRegistro = validaTipoRegistro(registro,valorCampo,linha);
						}
		
						//Valida se a primeira linha do arquivo é do tipo header de arquivo
						if (linha == AnaliseSintaticaItemEstruturaTXT.REGISTRO_INICIAL) {
							validaLinhaHeaderArquivo (tipoRegistro,registro,linha,valorCampo,campo,posicoes, perfil);
						} else {
							
							linhaAnterior = linha -1;
							//Carrega os valores avaliados anteriormente, utilizados na validação do HEADER_ITEM e no TRAILER_ARQUIVO
							if (linhaAnterior > 0) {								
								registroAnterior = (RegistroTXT)listaRegistro.get(posicaoRegistroNaLista-1);
							}
							
							if (tipoRegistro.equals(TipoRegistroEnum.HEADER_ARQUIVO)) {
								validaLinhaHeaderArquivo (tipoRegistro,registro,linha,valorCampo,campo,posicoes, perfil);
							} else if (tipoRegistro.equals(TipoRegistroEnum.HEADER_ITEM)){
								validarLinhaHeaderItem (registro,linha,valorCampo,campo,posicoes,false, perfil);
							} else if (tipoRegistro.equals(TipoRegistroEnum.TRAILER_ITEM)){
								validarLinhaTrailerItem(registroAnterior,linhaAnterior, registro,linha,posicoes,campo,valorCampo, perfil);
							} else if (tipoRegistro.equals(TipoRegistroEnum.TRAILER_ARQUIVO)){
								validaLinhaTrailerArquivo (registroAnterior,linhaAnterior,registro,linha,ULTIMA_LINHA,posicoes,valorCampo,campo,false);
							}
						}
					}
				} else { //Linha em branco
					levantaExcecao(ConstantesECAR.SINTATICO_LINHA_VAZIA,null,registro,linha,null);
				}
			}
			
			//Decrementar a linha é necessário pois o loop incrementou uma vez a mais.
			linha--;
			
			//Ao final do processamento, valida o header do item novamente para garantir que há pelo menos a ocorrencia de um item válido(header e trailer). 
			validarLinhaHeaderItem(registro, linha, null, 0, null, true, perfil);
			
			//Ao final do processamento, valida o trailer novamente para garantir que há pelo menos a ocorrencia de um trailer de arquivo. 
			validaLinhaTrailerArquivo(registroAnterior,linhaAnterior, registro, linha, ULTIMA_LINHA, posicoes,null,0,true);
		} else {
			levantaExcecao(ConstantesECAR.SINTATICO_ARQUIVO_VAZIO,null,null,0,null);
		}
		
		return true;
	}


	private void validarLinhaTrailerItem(IRegistro registroAnterior, int linhaAnterior, IRegistro registro, int linha, String[] posicoes,int campo,String valorCampo, PerfilIntercambioDadosPflid perfil) throws SintaticValidationException, ECARException {
				
		//Se tiver encontrado um header item(headerItemPresente) entra na validação do trailer do Item.
		if (headerItemEncontrado){
			
			if (posicoes.length == TR_I_TOTAL_CAMPOS) {
				
				if (campo == TR_I_SEQREG){
					try {
						int sequencial = Integer.parseInt(valorCampo);
						
						//Sequencial em desacordo com a linha lida do arquivo.
						if (sequencial != linha){
							levantaExcecao(ConstantesECAR.SINTATICO_TRAILER_ITEM_SEQUENCIA_INVALIDA,null,registro,linha,null);
						}
						
					} catch (NumberFormatException nex){
						levantaExcecao(ConstantesECAR.SINTATICO_TRAILER_ITEM_SEQUENCIA_INVALIDA,null,registro,linha,null);
					}
	
					headerItemEncontrado = false;
					qtdeEmpreendimentosValidos++;
				}
				
				if (campo == TR_I_CODIGO){
					validaCodigoTrailerItem(registroAnterior, registro, linha, valorCampo, perfil);
				}
				
				if (campo == TR_I_VALOR_ASSOCIACAO){
					validaValorAssociacaoTrailerItem(registroAnterior, registro, linha, valorCampo, perfil);
				}
				
				if (campo == TR_I_VALOR_ASSOCIACAO_ITEM){
					validaValorAssociacaoItemTrailerItem(registroAnterior, registro, linha, valorCampo, perfil);	
				}

				
			} else {// qtde campos no registro diferente do esperado.
				levantaExcecao(ConstantesECAR.SINTATICO_TRAILER_ITEM_TOTAL_CAMPOS,null,registro,linha,null);
			}
			
		} else { //senão tiver encontrado um header de item erro de trailer de item sem header 
			levantaExcecao(ConstantesECAR.SINTATICO_TRAILER_ITEM_SEM_HEADER_ITEM, null, registro, linha, null);
		}
		
	}


	private void validarLinhaHeaderItem(IRegistro registroAtual,int linhaAtual,String valorCampo,int campo,String[] posicoes,boolean validaFimArquivo, PerfilIntercambioDadosPflid perfil) throws SintaticValidationException, ECARException {
		
		//Vide documentação no corpo do método.
		

		if (!validaFimArquivo) {
			
			//Se ainda não tiver encontrado um header item(headerItemPresente) entra na validação.
			if (!headerItemEncontrado){
				
				if (posicoes.length == HE_I_TOTAL_CAMPOS) {
					if (campo == HE_I_OPERACAO){ //Valida a operação.
						if (!valorCampo.equals(ConstantesECAR.TIPO_OPERACAO_EXCLUSAO) && !valorCampo.equals(ConstantesECAR.TIPO_OPERACAO_MANUTENCAO)){
							levantaExcecao(ConstantesECAR.SINTATICO_HEADER_ITEM_OPERACAO_INVALIDA, null, registroAtual, linhaAtual, null);
						}
					} else if (campo == HE_I_SEQREG){ //Valida a operação.
						try {
							int sequencial = Integer.parseInt(valorCampo);
							
							//Sequencial em desacordo com a linha lida do arquivo.
							if (sequencial != linhaAtual){
								levantaExcecao(ConstantesECAR.SINTATICO_HEADER_ITEM_SEQUENCIA_INVALIDA,null,registroAtual,linhaAtual,null);
							}
						} catch (NumberFormatException nex){
							levantaExcecao(ConstantesECAR.SINTATICO_HEADER_ARQUIVO_SEQUENCIA_INVALIDA,null,registroAtual,linhaAtual,nex);
						}
						headerItemEncontrado = true;
					} else if (campo == HE_I_CODIGO){
						validaCodigo(registroAtual, linhaAtual, valorCampo);
					} else if (campo == HE_I_VALOR_ASSOCIACAO){
						validaValorAssociacao(registroAtual, linhaAtual, valorCampo);
					} else if (campo == HE_I_VALOR_ASSOCIACAO_ITEM){
						validaValorAssociacaoItem(registroAtual, linhaAtual, valorCampo);
					} else if (campo == HE_I_DATA_INICIO){
						validaDataInicio(registroAtual, linhaAtual, valorCampo);
					} else if (campo == HE_I_DATA_CONCLUSAO){
						validaDataConclusao(registroAtual, linhaAtual, valorCampo);
					}
				} else { // qtde campos no registro diferente do esperado.
					levantaExcecao(ConstantesECAR.SINTATICO_HEADER_ITEM_TOTAL_CAMPOS,null,registroAtual,linhaAtual,null);
				}
			} else { //ocorre erro, não pode haver duas ocorrências de header item em sequencia.
				/**
				 * Lógica
				 * 
				 * Após a ocorrência de um registro do tipo header de item o processamento não espera outro registro do tipo header 
				 * de item, caso isso ocorra um erro será lançado. O registro causador do erro será o registro lido anteriormente, 
				 * visto que este está mal formado sem o seu trailer correspondente, nesse caso o registro anterior será enviado 
				 * a exceção.  
				 *   
				 */
				
				levantaExcecao(ConstantesECAR.SINTATICO_HEADER_ITEM_SEM_TRAILER, null, registroAtual, linhaAtual, null);
			}
			
		} else { // Valida fim de arquivo 
			if (headerItemEncontrado){ //encontrou um header e não encontrou um trailer.
				//Arquivo malformado
				levantaExcecao(ConstantesECAR.SINTATICO_HEADER_ITEM_SEM_TRAILER, null, registroAtual, linhaAtual, null);
			} else { 
				
				/**
				 * Lógica
				 * 
				 * Qdo o headerItemEncontrado é falso é pq não existe nenhum registro ou existe pelo menos um registro válido.
				 * 
				 * Pois a leitura de um header de item ativa a flag headerItemEncontrado para true que só retornará a false quando
				 * um trailer de item for lido completando o ciclo de um item. 
				 */
				
				if (qtdeEmpreendimentosValidos == 0) { //Qdo nenhum item válido é informado
					levantaExcecao(ConstantesECAR.SINTATICO_ARQUIVO_ITEM_AUSENTE, null, registroAtual, linhaAtual, null);
				} 
			}
		}

		
	}


	private void validaValorAssociacaoItem(IRegistro registroAtual,int linhaAtual, String valorCampo) throws SintaticValidationException, ECARException {

		if (valorCampo == null || valorCampo.equals(Dominios.STRING_VAZIA)){
			levantaExcecao(ConstantesECAR.SINTATICO_HEADER_ITEM_VALOR_ASSOCIACAO_ITEM_NAO_INFORMADO, null, registroAtual, linhaAtual, null);			
		}
		
	}


	private void validaValorAssociacao(IRegistro registroAtual, int linhaAtual,	String valorCampo) throws SintaticValidationException, ECARException {
		
		if (valorCampo == null || valorCampo.equals(Dominios.STRING_VAZIA)){
			levantaExcecao(ConstantesECAR.SINTATICO_HEADER_ITEM_VALOR_ASSOCIACAO_NAO_INFORMADO, null, registroAtual, linhaAtual, null);			
		}
	}


	private void validaCodigo(IRegistro registroAtual, int linhaAtual,String valorCampo) throws SintaticValidationException, ECARException {
	
		if (valorCampo == null || valorCampo.equals(Dominios.STRING_VAZIA)){
			levantaExcecao(ConstantesECAR.SINTATICO_HEADER_ITEM_IDENTIFICADOR_NAO_INFORMADO, null, registroAtual, linhaAtual, null);			
		}
		
	}


	private void validaLinhaTrailerArquivo(RegistroTXT registroAnterior,int linhaAnterior,RegistroTXT registro,int linha,int ultimaLinha,String[] posicoes,String valorCampo,int campo,boolean validaFimArquivo) throws ECARException, SintaticValidationException {

		
		//Nesse ponto o flag headerItemEncontrado deve ser sempre false, pois deve ter lido um header de item e um trailer de item. 
		if (!headerItemEncontrado) {
			//Senão for fim de arquivo
			if (!validaFimArquivo) {
				//Se o trailer estiver em uma linha diferente da última o arquivo deve ser rejeitado.
				if (linha == ultimaLinha){
					if (posicoes.length == TR_A_TOTAL_CAMPOS) {//Valida o total de campo do arquivo.
						if (campo == TR_A_TOTAL_EMPREENDIMENTOS){
							try {
								int qtdeEmpreendimentosInformadosTrailer = Integer.parseInt(valorCampo);
		
								if (qtdeEmpreendimentosInformadosTrailer == qtdeEmpreendimentosValidos){
									qtdeTrailerArquivo++;	
								} else {
									levantaExcecao(ConstantesECAR.SINTATICO_TRAILER_ARQUIVO_TOTAL_EMPREEND_INVALIDOS, null, registro, ultimaLinha, null);
								}
							} catch (NumberFormatException nex){
								levantaExcecao(ConstantesECAR.SINTATICO_TRAILER_ARQUIVO_TOTAL_EMPREEND_INVALIDOS, null, registro, ultimaLinha, nex);
							}
						} else if (campo == TR_A_SEQREG){
							try {
								int sequencial = Integer.parseInt(valorCampo);
								
								//Sequencial em desacordo com a linha lida do arquivo.
								if (sequencial != linha){
									levantaExcecao(ConstantesECAR.SINTATICO_TRAILER_ARQUIVO_SEQUENCIA_INVALIDA,null,registro,linha,null);
								}
							} catch (NumberFormatException nex){
								levantaExcecao(ConstantesECAR.SINTATICO_TRAILER_ARQUIVO_SEQUENCIA_INVALIDA,null,registro,linha,null);
							}				
						}
					} else {
						levantaExcecao(ConstantesECAR.SINTATICO_TRAILER_TOTAL_CAMPOS,null,registro,linha,null);
					}
				} else {
					levantaExcecao(ConstantesECAR.SINTATICO_TRAILER_ARQUIVO_POSICAO_INVALIDA, null, registro, linha, null);
				}
			} else {//Se for fim de arquivo
				if (linha == ultimaLinha && qtdeTrailerArquivo == 0){ //O processamento está no fim do arquivo e não encontrou nenhum trailler				
					levantaExcecao(ConstantesECAR.SINTATICO_TRAILER_ARQUIVO_AUSENTE, null, registro, ultimaLinha, null);
				} 
			}
		} else {
			//Arquivo malformado
			levantaExcecao(ConstantesECAR.SINTATICO_HEADER_ITEM_SEM_TRAILER, null, registroAnterior, linhaAnterior, null);
		}
	}


	/**
	 * Valida o header do arquivo de acordo com as regras.
	 * @param tipoRegistro
	 * @param registro
	 * @param linha
	 * @param valorCampo
	 * @param campo
	 * @throws ECARException
	 * @throws SintaticValidationException
	 */
	
	private void validaLinhaHeaderArquivo(TipoRegistroEnum tipoRegistro,IRegistro registro,int linha,String valorCampo,int campo,String[] posicoes, PerfilIntercambioDadosPflid perfil) throws ECARException, SintaticValidationException {
		

		if (tipoRegistro.equals(TipoRegistroEnum.HEADER_ARQUIVO)){ //A primeira linha do arquivo deve ser sempre o header de arquivo.
			if (posicoes.length == HE_A_TOTAL_CAMPOS) {
				if (campo == HE_A_IDENT) { //Valida o campo identificador no header 
					if (qtdeHeaderArquivo >= 1){ //Ocorrência de mais de um 1 header de arquivo, no arquivo analisado.
						levantaExcecao(ConstantesECAR.SINTATICO_HEADER_ARQUIVO_QUANTIDADE_INVALIDA, null, registro, linha, null);
					} else {//caso de sucesso
						qtdeHeaderArquivo++;
					}
		 		} else if (campo == HE_A_DTGERACAO){ 
		 			//Valida o formato da data															 
					Date dataHeader = Data.parseDate(valorCampo, AnaliseSintaticaItemEstruturaTXT.PADRAO_DATA_HEADER,false);//sdf.parse(valorCampo);
					if (dataHeader == null){
						levantaExcecao(ConstantesECAR.SINTATICO_HEADER_ARQUIVO_DTGERACAO_INVALIDA, null, registro, linha, null);
					}
					Date dataAtual = new Date(); 
					
					//se a data do header for posterior a data atual um erro será gerado. 
					if (dataHeader.after(dataAtual)){
						levantaExcecao(ConstantesECAR.SINTATICO_HEADER_ARQUIVO_DTGERACAO_INVALIDA, null, registro, linha, null);
					}
					if (configuracaoImportacao!=null)
						((ConfiguracaoImportacaoTXT)configuracaoImportacao).setDataGeracaoArquivo(dataHeader);
					
				} else if (campo == HE_A_SERVICO){ //Valida o tipo do serviço 
					try {
						Long codTipoServico = Long.parseLong(valorCampo);
						Long codTipoServicoConfigurado = perfil.getCodTipoServicoPflid();
						
						//Se o tipo de serviço configurado for diferente do tipo enviado no arquivo, levanta o erro.
						if (!codTipoServico.equals(codTipoServicoConfigurado)){
							levantaExcecao(ConstantesECAR.SINTATICO_HEADER_ARQUIVO_SERVICO_INVALIDO, null, registro, linha, null);
						}
					} catch (NumberFormatException nex){
						levantaExcecao(ConstantesECAR.SINTATICO_HEADER_ARQUIVO_SERVICO_INVALIDO, null, registro, linha, nex);
					}
				} else if (campo == HE_A_ORIGEM){ //Valida o sistema de origem 
					try {
						Long codSistemaOrigem = Long.parseLong(valorCampo);
						Long codSistemaOrigemConfigurado = perfil.getCodSistemaOrigemPflid();
						
						//Se o sistema de origem configurado for diferente do tipo enviado no arquivo, levanta o erro.
						if (!codSistemaOrigem.equals(codSistemaOrigemConfigurado)){
							levantaExcecao(ConstantesECAR.SINTATICO_HEADER_ARQUIVO_ORIGEM_INVALIDO, null, registro, linha, null);
						}
					} catch (NumberFormatException nex){
						levantaExcecao(ConstantesECAR.SINTATICO_HEADER_ARQUIVO_ORIGEM_INVALIDO, null, registro, linha, nex);
					}
				} else if (campo == HE_A_DESTINO){ //Valida o sistema de destino
					try {
						Long codSistemaDestino = Long.parseLong(valorCampo);
						Long codSistemaDestinoConfigurado = perfil.getCodSistemaDestinoPflid();
						
						//Se o sistema destino for diferente do tipo enviado no arquivo, levanta o erro.
						if (!codSistemaDestino.equals(codSistemaDestinoConfigurado)){
							levantaExcecao(ConstantesECAR.SINTATICO_HEADER_ARQUIVO_DESTINO_INVALIDO,null,registro,linha,null);
						}
					} catch (NumberFormatException nex){
						levantaExcecao(ConstantesECAR.SINTATICO_HEADER_ARQUIVO_DESTINO_INVALIDO,null,registro,linha,nex);
					}
				} else if (campo == HE_A_NOME_LAYOUT){ //Valida o nome do layout 
					String nomeLayOut = valorCampo;
					String nomeLayOutConfigurado = perfil.getDadosTecnologiaPerfilDtp().getTipoTecnologia().getEspecificacao();
					
					//Se o sistema destino for diferente do tipo enviado no arquivo, levanta o erro.
					if (!nomeLayOut.equals(nomeLayOutConfigurado)){
						levantaExcecao(ConstantesECAR.SINTATICO_HEADER_ARQUIVO_NOME_LAYOUT_INVALIDO,null,registro,linha,null);
					}
				} else if (campo == HE_A_VERSAO){ //Valida a versão do layout 
					String versaoLayOut = valorCampo;
					String versaoLayOutConfigurado = perfil.getDadosTecnologiaPerfilDtp().getTipoTecnologia().getVersao();
					
					//Se a versão do Lay out for diferente do tipo enviado no arquivo, levanta o erro.
					if (!versaoLayOut.equals(versaoLayOutConfigurado)){
						levantaExcecao(ConstantesECAR.SINTATICO_HEADER_ARQUIVO_VERSAO_LAYOUT_INVALIDO,null,registro,linha,null);
					}
				} else if (campo == HE_A_CAMPOSITENS){ //Valida o campo dos itens 
					//TODO utilizar qdo validar campos dinamicamente de acordo com a descição dos campos recebidas no arquivo.
				} else if (campo == HE_A_SEQREG){ //Valida a sequencia do registro 
					try {
						int sequencial = Integer.parseInt(valorCampo);
						
						//Sequencial em desacordo com a linha lida do arquivo.
						if (sequencial != linha){
							levantaExcecao(ConstantesECAR.SINTATICO_HEADER_ARQUIVO_SEQUENCIA_INVALIDA,null,registro,linha,null);
						}
					} catch (NumberFormatException nex){
						levantaExcecao(ConstantesECAR.SINTATICO_HEADER_ARQUIVO_SEQUENCIA_INVALIDA,null,registro,linha,null);
					}
				}
			} else {
				levantaExcecao(ConstantesECAR.SINTATICO_HEADER_TOTAL_CAMPOS,null,registro,linha,null);
			}
		} else { // A linha não é um header de arquivo.
			levantaExcecao(ConstantesECAR.SINTATICO_HEADER_ARQUIVO_AUSENTE, null, registro, linha, null);
		}
	}

	/**
	 * Valida o tipo do registro
	 * @param campo
	 * @throws ECARException 
	 * @throws ECARException 
	 */
	private TipoRegistroEnum validaTipoRegistro(IRegistro registro,String campo,int linha) throws SintaticValidationException, ECARException {
		
		int tipo;
		TipoRegistroEnum tipoRegistro = null;
		try {
			tipo = Integer.parseInt(campo);
			
			tipoRegistro = TipoRegistroEnum.valueOf(tipo);
			
		} catch (NumberFormatException nex) {
			levantaExcecao(ConstantesECAR.SINTATICO_HEADER_ARQUIVO_TIPO, null, registro, linha,nex);
		} catch (IllegalArgumentException ilex) {
			String[] msgs = new String[2];
			msgs[0] = ((ConfiguracaoImportacaoTXT)configuracaoImportacao).getFileName();
			msgs[1] = campo;
			
			levantaExcecao(ConstantesECAR.SINTATICO_HEADER_ARQUIVO_TIPO, msgs, registro, linha,ilex);
		}
		
		return tipoRegistro;
	}
	
	private void levantaExcecao(int codMotivo,String[] msgs,IRegistro registro,int linha,Exception ex) throws ECARException, SintaticValidationException {
		
		Dao dao = new Dao();
		
		MotivoRejeicaoMtr motivoRejeicaoMtr = (MotivoRejeicaoMtr)dao.buscar(MotivoRejeicaoMtr.class, new Long(codMotivo));

		
		SintaticValidationException sintex = new SintaticValidationException(ex,msgs,motivoRejeicaoMtr,registro,linha);
		this.logger.error(sintex);
		throw sintex;
	}
	
	/**
	 * Valida se o identificador foi informado no rodapé e se é igual ao informado no cabeçalho
	 * @param registroAnterior
	 * @param valorCampo
	 */
	private void validaCodigoTrailerItem(IRegistro registroAnterior, IRegistro registro, int linha, String valorCampo, PerfilIntercambioDadosPflid perfil) throws SintaticValidationException, ECARException{
		DadosTecnologiaPerfilTxtDtpt dadosTecnologiaPerfilTxtDtpt = (DadosTecnologiaPerfilTxtDtpt) perfil.getDadosTecnologiaPerfilDtp();		
		String codigoTrailer = valorCampo;
		String[] posicoes = Util.split(((RegistroTXT)registroAnterior).getLinha(), dadosTecnologiaPerfilTxtDtpt.getSeparadorCamposDtpt());
		String codigoHeader = posicoes[HE_I_CODIGO];
		if (codigoTrailer == null || codigoTrailer.equals(Dominios.STRING_VAZIA)){
			levantaExcecao(ConstantesECAR.SINTATICO_TRAILER_ITEM_IDENTIFICADOR_NAO_INFORMADO, null, registro, linha, null);
			//Identificador do empreendimento não informado no rodapé
		} else if (!codigoTrailer.equals(codigoHeader)){
			levantaExcecao(ConstantesECAR.SINTATICO_TRAILER_ITEM_IDENTIFICADOR_DIFERENTE_HEADER, null, registro, linha, null);
			//Identificador do empreendimento no rodapé difere do indicado no cabeçalho
		}
	}

	/**
	 * Valida se o valor de associação foi informado no rodapé e se é igual ao informado no cabeçalho 
	 * @param itemEstruturaDTO
	 */
	private void validaValorAssociacaoTrailerItem(IRegistro registroAnterior, IRegistro registro, int linha, String valorCampo, PerfilIntercambioDadosPflid perfil) throws SintaticValidationException, ECARException{
		DadosTecnologiaPerfilTxtDtpt dadosTecnologiaPerfilTxtDtpt = (DadosTecnologiaPerfilTxtDtpt) perfil.getDadosTecnologiaPerfilDtp();
		String valorAssociacaoTrailer = valorCampo;
		String[] posicoes = Util.split(((RegistroTXT)registroAnterior).getLinha(),dadosTecnologiaPerfilTxtDtpt.getSeparadorCamposDtpt());
		String valorAssociacaoHeader = posicoes[HE_I_VALOR_ASSOCIACAO];
		if (valorAssociacaoTrailer == null || valorAssociacaoTrailer.equals(Dominios.STRING_VAZIA)){
			levantaExcecao(ConstantesECAR.SINTATICO_TRAILER_ITEM_VALOR_ASSOCIACAO_NAO_INFORMADO, null, registro, linha, null);
			//Valor de associação do empreendimento não informado no rodapé
		} else if (!valorAssociacaoTrailer.equals(valorAssociacaoHeader)){
			levantaExcecao(ConstantesECAR.SINTATICO_TRAILER_ITEM_VALOR_ASSOCIACAO_DIFERENTE_HEADER, null, registro, linha, null);
			//Valor de asssociação do empreendimento no rodapé difere do indicado no cabeçalho
		}
	}
	
	/**
	 * Valida se o valor de associação foi informado no rodapé e se é igual ao informado no cabeçalho 
	 * @param itemEstruturaDTO
	 */
	private void validaValorAssociacaoItemTrailerItem(IRegistro registroAnterior, IRegistro registro, int linha, String valorCampo, PerfilIntercambioDadosPflid perfil) throws SintaticValidationException, ECARException{
		DadosTecnologiaPerfilTxtDtpt dadosTecnologiaPerfilTxtDtpt = (DadosTecnologiaPerfilTxtDtpt) perfil.getDadosTecnologiaPerfilDtp();
		String valorAssociacaoItemTrailer = valorCampo;
		String[] posicoes = Util.split(((RegistroTXT)registroAnterior).getLinha(), dadosTecnologiaPerfilTxtDtpt.getSeparadorCamposDtpt());
		String valorAssociacaoItemHeader = posicoes[HE_I_VALOR_ASSOCIACAO_ITEM];
		if (valorAssociacaoItemTrailer == null || valorAssociacaoItemTrailer.equals(Dominios.STRING_VAZIA)){
			levantaExcecao(ConstantesECAR.SINTATICO_TRAILER_ITEM_VALOR_ASSOCIACAO_ITEM_NAO_INFORMADO, null, registro, linha, null);
			//Valor do Tipo de empreendimento não informado no rodapé
		} else if (!valorAssociacaoItemTrailer.equals(valorAssociacaoItemHeader)){
			levantaExcecao(ConstantesECAR.SINTATICO_TRAILER_ITEM_VALOR_ASSOCIACAO_ITEM_DIFERENTE_HEADER, null, registro, linha, null);
			//Valor do Tipo de empreendimento no rodapé difere do indicado no cabeçalho
		}
	}
	
	/**
	 * Valida se a data de início do empreendimento é válida
	 * @param itemEstruturaDTO
	 */
	private void validaDataInicio(IRegistro registro, int linha, String valorCampo) throws SintaticValidationException, ECARException{
		Date dataInicioDate = null;
		//valida se data de início passada está no formato esperado
		if (valorCampo != null && !valorCampo.equals(Dominios.STRING_VAZIA)){
			dataInicioDate = Data.parseDate(valorCampo, ConstantesECAR.FORMATO_DATA_IMPORTACAO,false); 
			if (dataInicioDate == null){
				levantaExcecao(ConstantesECAR.SINTATICO_HEADER_ITEM_DATA_INICIO_INVALIDA, null, registro, linha, null);
				//Data de início do empreendimento inválida
			}
		}
	}
	
	/**
	 * Valida se a data de conclusão do empreendimento é válida
	 * @param itemEstruturaDTO
	 */
	private void validaDataConclusao(IRegistro registro, int linha, String valorCampo) throws SintaticValidationException, ECARException{
		Date dataConclusaoDate = null;
		//valida se data de conclusão passada está no formato esperado
		if (valorCampo != null && !valorCampo.equals(Dominios.STRING_VAZIA)){
			dataConclusaoDate = Data.parseDate(valorCampo, ConstantesECAR.FORMATO_DATA_IMPORTACAO,false); 
			if (dataConclusaoDate == null){
				levantaExcecao(ConstantesECAR.SINTATICO_HEADER_ITEM_DATA_CONCLUSAO_INVALIDA, null, registro, linha, null);
				//Data de conclusão do empreendimento inválida
			}
		}
	}
	
	
}