package ecar.intercambioDados.recurso.txt;

import java.math.BigDecimal;
import java.util.Date;

import org.apache.log4j.Logger;

import comum.database.Dao;
import comum.util.ConstantesECAR;
import comum.util.Data;
import comum.util.Util;

import ecar.dao.ItemEstruturaDao;
import ecar.exception.ECARException;
import ecar.exception.intercambioDados.SemanticValidationException;
import ecar.intercambioDados.dto.IBusinessObjectDTO;
import ecar.intercambioDados.dto.ItemEstruturaTXTDTO;
import ecar.intercambioDados.importacao.comunicacao.Configuracao;
import ecar.intercambioDados.validador.SemanticValidator;
import ecar.pojo.ItemEstruturaIett;
import ecar.pojo.intercambioDados.MotivoRejeicaoMtr;
import ecar.pojo.intercambioDados.PerfilIntercambioDadosPflid;
import ecar.pojo.intercambioDados.funcionalidade.PerfilIntercambioDadosCadastroPidc;
import ecar.pojo.intercambioDados.funcionalidade.TipoFuncionalidadeEnum;
import ecar.util.Dominios;

public class ValidadorSemanticoTXT extends SemanticValidator{
	/**
	 * 
	 * @param config
	 */
	public ValidadorSemanticoTXT(Configuracao config) {
		this.config = config;
		logger = Logger.getLogger(this.getClass());
	}
	
	/**
	 * Faz a valida��o sem�ntica no objeto passado como par�metro
	 */
	public boolean valida(IBusinessObjectDTO objNegocio) throws SemanticValidationException, ECARException{
		ItemEstruturaTXTDTO itemEstruturaDTO = (ItemEstruturaTXTDTO) objNegocio;
		PerfilIntercambioDadosPflid perfil = config.getPerfil();
		//chama o m�todo que faz a valida��o do c�digo do empreendimento
//		validaCodigo(itemEstruturaDTO);
		//chama o m�todo que valida se a data de inicio do arquivo � v�lida
		validaDataInicio(itemEstruturaDTO);
		//chama o m�todo que valida se a data de conclus�o do arquivo � v�lida
		validaDataConclusao(itemEstruturaDTO);
		//chama o m�todo de valida��o do custo
		validaCusto(itemEstruturaDTO);
		//chama o m�todo de valida��o do valor de associa��o
		validaValorAssociacao(itemEstruturaDTO, perfil);
		//chama o m�todo para validar a exclus�o quando a opera��o = E
		validaOperacao(itemEstruturaDTO, perfil);
		//chama o m�todo para validar o c�digo informado no trailer do item
		//validaCodigoTrailer(itemEstruturaDTO);
		//chama o m�todo para validar o valor de associa��o do trailer do item
//		validaValorAssociacaoTrailer(itemEstruturaDTO);
		
		//Novos campos acescentados
		
		//chama o m�todo de valida��o do valor do tipo de empreendimento
		validaValorTipoEmpreendimento(itemEstruturaDTO, perfil);
		//chama o m�todo para validar o valor do tipo de empreendimento do trailer do item
//		validaValorTipoEmpreendimentoTrailer(itemEstruturaDTO);
		//chama o m�todo de valida��o do Investimento Previsto 2007-2010
//		validaInvestPrev20072010(itemEstruturaDTO);
		//chama o m�todo de valida��o do Investimento Previsto at� 2010
//		validaInvestPrevAte2010(itemEstruturaDTO);
		
		//se n�o encontrou nenhum problema e n�o levantou nenhuma exce��o retorna true
		return true;
	}
	
	/**
	 * Valida se o c�digo do empreendimento foi informado
	 * @param itemEstruturaDTO
	 */
//	private void validaCodigo(ItemEstruturaTXTDTO ItemEstruturaTXTDTO) throws SemanticValidationException, ECARException{
//		if (ItemEstruturaTXTDTO.getCodigo() == null || ItemEstruturaTXTDTO.getCodigo().equals(Dominios.STRING_VAZIA)){
//			levantaExcecao(ConstantesECAR.SEMANTICO_HEADER_ITEM_IDENTIFICADOR_NAO_INFORMADO, null, ItemEstruturaTXTDTO, ItemEstruturaTXTDTO.getNumeroLinha(), null);
//			//Identificador do empreendimento n�o informado no cabe�alho
//		}
//	}
	
	/**
	 * Valida se a data de in�cio do empreendimento � v�lida
	 * @param ItemEstruturaTXTDTO
	 */
	private void validaDataInicio(ItemEstruturaTXTDTO ItemEstruturaTXTDTO) throws SemanticValidationException, ECARException{
		Date dataInicioDate = null;
		//valida se data de in�cio passada est� no formato esperado
		if (ItemEstruturaTXTDTO.getDataInicio() != null && !ItemEstruturaTXTDTO.getDataInicio().equals(Dominios.STRING_VAZIA)){
			dataInicioDate = Data.parseDate(ItemEstruturaTXTDTO.getDataInicio(), ConstantesECAR.FORMATO_DATA_IMPORTACAO); 
			if (dataInicioDate == null){
				levantaExcecao(ConstantesECAR.SEMANTICO_HEADER_ITEM_DATA_INICIO_INVALIDA, null, ItemEstruturaTXTDTO, ItemEstruturaTXTDTO.getNumeroLinha(), null);
				//Data de in�cio do empreendimento inv�lida
			}
		}
	}
	
	/**
	 * Valida se a data de conclus�o do empreendimento � v�lida
	 * @param ItemEstruturaTXTDTO
	 */
	private void validaDataConclusao(ItemEstruturaTXTDTO ItemEstruturaTXTDTO) throws SemanticValidationException, ECARException{
		Date dataInicioDate = null;
		Date dataConclusaoDate = null;
		//valida se data de conclus�o passada est� no formato esperado
		if (ItemEstruturaTXTDTO.getDataConclusao() != null && !ItemEstruturaTXTDTO.getDataConclusao().equals(Dominios.STRING_VAZIA)){
			dataInicioDate = Data.parseDate(ItemEstruturaTXTDTO.getDataInicio(), ConstantesECAR.FORMATO_DATA_IMPORTACAO);
			dataConclusaoDate = Data.parseDate(ItemEstruturaTXTDTO.getDataConclusao(), ConstantesECAR.FORMATO_DATA_IMPORTACAO); 
			if (dataConclusaoDate == null){
				levantaExcecao(ConstantesECAR.SEMANTICO_HEADER_ITEM_DATA_CONCLUSAO_INVALIDA, null, ItemEstruturaTXTDTO, ItemEstruturaTXTDTO.getNumeroLinha(), null);
				//Data de conclus�o do empreendimento inv�lida
			} else if (dataInicioDate != null && dataInicioDate.after(dataConclusaoDate)){
				levantaExcecao(ConstantesECAR.SEMANTICO_HEADER_ITEM_DATA_CONCLUSAO_INFERIOR_DATA_INICIO, null, ItemEstruturaTXTDTO, ItemEstruturaTXTDTO.getNumeroLinha(), null);
				//Data de conclus�o inferior a data de in�cio.
			}
		}
	}
	
	/**
	 * Valida se o custo passado � um valor v�lido
	 * @param ItemEstruturaTXTDTO
	 */
	private void validaCusto(ItemEstruturaTXTDTO ItemEstruturaTXTDTO) throws SemanticValidationException, ECARException{
		BigDecimal valorCustoFormatado = null;
		boolean custoInvalido = false;
		String custo = "";
		if (ItemEstruturaTXTDTO.getCusto() != null) {
			custo = ItemEstruturaTXTDTO.getCusto().replace(",", ".");
		}
		if (custo != null && !custo.equals(Dominios.STRING_VAZIA)) {
			if (!Util.ehValor(custo)){
				custoInvalido = true;
				//Custo do empreendimento inv�lido
			} else {
				valorCustoFormatado = new BigDecimal(Double.valueOf(custo).doubleValue());
				if (valorCustoFormatado.doubleValue() < 0){
					custoInvalido = true;
					//Custo do empreendimeneto inv�lido
				}
			}
		}
		if (custoInvalido){
			levantaExcecao(ConstantesECAR.SEMANTICO_HEADER_ITEM_CUSTO_INVALIDO, null, ItemEstruturaTXTDTO, ItemEstruturaTXTDTO.getNumeroLinha(), null);
			//Custo do empreendimeneto inv�lido
		}
	}
	
	/**
	 * Valida se o Investimento Pevisto de 2007-2010 passado � um valor v�lido
	 * @param ItemEstruturaTXTDTO
	 */
	private void validaInvestPrev20072010(ItemEstruturaTXTDTO ItemEstruturaTXTDTO) throws SemanticValidationException, ECARException{
		BigDecimal valorInvestPrevFormatado = null;
		boolean valorInvestPrevInvalido = false;
		String valorInvestPrev = "";
		if (ItemEstruturaTXTDTO.getCusto() != null) {
			valorInvestPrev = ItemEstruturaTXTDTO.getInvestPrev20072010().replace(",", ".");
		}
		if (valorInvestPrev != null && !valorInvestPrev.equals(Dominios.STRING_VAZIA)) {
			if (!Util.ehValor(valorInvestPrev)){
				valorInvestPrevInvalido = true;
				//Custo do empreendimento inv�lido
			} else {
				valorInvestPrevFormatado = new BigDecimal(Double.valueOf(valorInvestPrev).doubleValue());
				if (valorInvestPrevFormatado.doubleValue() < 0){
					valorInvestPrevInvalido = true;
					//Custo do empreendimeneto inv�lido
				}
			}
		}
		if (valorInvestPrevInvalido){
			levantaExcecao(ConstantesECAR.SEMANTICO_HEADER_ITEM_INVESTIMENTO_PREVISTO_2007_2010_INVALIDO, null, ItemEstruturaTXTDTO, ItemEstruturaTXTDTO.getNumeroLinha(), null);
			//Custo do empreendimeneto inv�lido
		}
	}
	
	/**
	 * Valida se o Investimento Pevisto de 2007-2010 passado � um valor v�lido
	 * @param ItemEstruturaTXTDTO
	 */
	private void validaInvestPrevAte2010(ItemEstruturaTXTDTO ItemEstruturaTXTDTO) throws SemanticValidationException, ECARException{
		BigDecimal valorInvestPrevFormatado = null;
		boolean valorInvestPrevInvalido = false;
		String valorInvestPrev = "";
		if (ItemEstruturaTXTDTO.getInvestPrevApos2010() != null) {
			valorInvestPrev = ItemEstruturaTXTDTO.getInvestPrevApos2010().replace(",", ".");
		}
		if (valorInvestPrev != null && !valorInvestPrev.equals(Dominios.STRING_VAZIA)) {
			if (!Util.ehValor(valorInvestPrev)){
				valorInvestPrevInvalido = true;
				//Custo do empreendimento inv�lido
			} else {
				valorInvestPrevFormatado = new BigDecimal(Double.valueOf(valorInvestPrev).doubleValue());
				if (valorInvestPrevFormatado.doubleValue() < 0){
					valorInvestPrevInvalido = true;
					//Custo do empreendimeneto inv�lido
				}
			}
		}
		if (valorInvestPrevInvalido){
			levantaExcecao(ConstantesECAR.SEMANTICO_HEADER_ITEM_INVESTIMENTO_PREVISTO_ATE_2010_INVALIDO, null, ItemEstruturaTXTDTO, ItemEstruturaTXTDTO.getNumeroLinha(), null);
			//Custo do empreendimeneto inv�lido
		}
	}
	
	/**
	 * Valida se o valor de associa��o foi informado e se existe empreendimento com o valor informado. 
	 * @param ItemEstruturaTXTDTO
	 */
	private void validaValorAssociacao(ItemEstruturaTXTDTO ItemEstruturaTXTDTO, PerfilIntercambioDadosPflid perfilIntercambioDadosPflid) throws SemanticValidationException, ECARException{
		ItemEstruturaDao itemEstruturaDao = new ItemEstruturaDao(null);
		ItemEstruturaIett itemBase = null;
		if (perfilIntercambioDadosPflid.getTipoFuncionalidade().equals(TipoFuncionalidadeEnum.CADASTRO)) {
			itemBase = itemEstruturaDao.getItemEstruturaIettByEstruturaDescricaoR1(((PerfilIntercambioDadosCadastroPidc)perfilIntercambioDadosPflid).getEstruturaBasePidc(), ItemEstruturaTXTDTO.getValorAssociacao());
		}
		if (itemBase == null){
			levantaExcecao(ConstantesECAR.SEMANTICO_HEADER_ITEM_VALOR_ASSOCIACAO_SEM_CORRESPONDENCIA, null, ItemEstruturaTXTDTO, ItemEstruturaTXTDTO.getNumeroLinha(), null);
			//N�o encontrada correspond�ncia do valor de associa��o do empreendimento com item no sistema.
		}
	}
	
	/**
	 * Valida se o valor Tipo do Empreendimento foi informado e se existe empreendimento com o valor informado. 
	 * @param ItemEstruturaTXTDTO
	 */
	private void validaValorTipoEmpreendimento(ItemEstruturaTXTDTO ItemEstruturaTXTDTO, PerfilIntercambioDadosPflid perfilIntercambioDadosPflid) throws SemanticValidationException, ECARException{
		
		ItemEstruturaDao itemEstruturaDao = new ItemEstruturaDao(null);
		ItemEstruturaIett itemNivelSuperior = null; 
		if (perfilIntercambioDadosPflid.getTipoFuncionalidade().equals(TipoFuncionalidadeEnum.CADASTRO)) {	
			itemEstruturaDao.getItemEstruturaIettByEstruturaDescricaoR3Validacao(((PerfilIntercambioDadosCadastroPidc)perfilIntercambioDadosPflid).getEstruturaItemNivelSuperiorPidc(),  ItemEstruturaTXTDTO.getValorAssociacao(), ItemEstruturaTXTDTO.getTipoEmpreendimento());
		}
		if (itemNivelSuperior == null){
			levantaExcecao(ConstantesECAR.SEMANTICO_HEADER_ITEM_VALOR_TIPO_EMPREENDIMENTO_SEM_CORRESPONDENCIA, null, ItemEstruturaTXTDTO, ItemEstruturaTXTDTO.getNumeroLinha(), null);
			//N�o encontrada correspond�ncia do valor de associa��o do empreendimento com item no sistema.
		}	

	}
	
	/**
	 * Valida se o item existe no e-car quando importado uma exclus�o de um item
	 * @param ItemEstruturaTXTDTO
	 */
	private void validaOperacao(ItemEstruturaTXTDTO ItemEstruturaTXTDTO, PerfilIntercambioDadosPflid perfilIntercambioDadosPflid) throws SemanticValidationException, ECARException{
		if (ItemEstruturaTXTDTO.getOperacao().equals(ConstantesECAR.TIPO_OPERACAO_EXCLUSAO)){
			ItemEstruturaIett itemEstruturaIett = null;
			ItemEstruturaDao itemEstruturaDao = new ItemEstruturaDao(null);
			
			if (perfilIntercambioDadosPflid.getTipoFuncionalidade().equals(TipoFuncionalidadeEnum.CADASTRO)) {
				itemEstruturaIett = itemEstruturaDao.getItemEstruturaIettByEstruturaSiglaIett(((PerfilIntercambioDadosCadastroPidc)perfilIntercambioDadosPflid).getEstruturaCriacaoItemPidc(), ItemEstruturaTXTDTO.getCodigo(), ItemEstruturaTXTDTO.getValorAssociacao(), ItemEstruturaTXTDTO.getTipoEmpreendimento());
			}
			if (itemEstruturaIett == null){
				levantaExcecao(ConstantesECAR.SEMANTICO_HEADER_ITEM_EXCLUSAO_EMPREENDIMENTO_INEXISTENTE, null, ItemEstruturaTXTDTO, ItemEstruturaTXTDTO.getNumeroLinha(), null);
				//Tentativa de exclus�o de empreendimento inexistente no sistema
			}
		}
	}

	/**
	 * Valida se o identificador foi informado no rodap� e se � igual ao informado no cabe�alho
	 * @param ItemEstruturaTXTDTO
	 */
//	private void validaCodigoTrailer(ItemEstruturaTXTDTO ItemEstruturaTXTDTO) throws SemanticValidationException, ECARException{
//		String codigoTrailer = ItemEstruturaTXTDTO.getItemEstruturaTrailerDTO().getCodigo();
//		if (codigoTrailer == null || codigoTrailer.equals(Dominios.STRING_VAZIA)){
//			levantaExcecao(ConstantesECAR.SEMANTICO_TRAILER_ITEM_IDENTIFICADOR_NAO_INFORMADO, null, ItemEstruturaTXTDTO, ItemEstruturaTXTDTO.getNumeroLinha(), null);
//			//Identificador do empreendimento n�o informado no rodap�
//		} 
//	}

	/**
	 * Valida se o valor de associa��o foi informado no rodap� e se � igual ao informado no cabe�alho 
	 * @param ItemEstruturaTXTDTO
	 */
//	private void validaValorAssociacaoTrailer(ItemEstruturaTXTDTO ItemEstruturaTXTDTO) throws SemanticValidationException, ECARException{
//		String valorAssociacaoTrailer = ItemEstruturaTXTDTO.getItemEstruturaTrailerDTO().getValorAssociacao();
//		if (valorAssociacaoTrailer == null || valorAssociacaoTrailer.equals(Dominios.STRING_VAZIA)){
//			levantaExcecao(ConstantesECAR.SEMANTICO_TRAILER_ITEM_VALOR_ASSOCIACAO_NAO_INFORMADO, null, ItemEstruturaTXTDTO, ItemEstruturaTXTDTO.getNumeroLinha(), null);
//			//Valor de associa��o do empreendimento n�o informado no rodap�
//		} else if (!ItemEstruturaTXTDTO.getValorAssociacao().equals(valorAssociacaoTrailer)){
//			levantaExcecao(ConstantesECAR.SEMANTICO_HEADER_ITEM_VALOR_ASSOCIACAO_DIFERENTE_TRAILER, null, ItemEstruturaTXTDTO, ItemEstruturaTXTDTO.getNumeroLinha(), null);
//			//Valor de asssocia��o do empreendimento no rodap� difere do indicado no cabe�alho
//		}
//	}
	
	/**
	 * Valida se o valor de associa��o foi informado no rodap� e se � igual ao informado no cabe�alho 
	 * @param ItemEstruturaTXTDTO
	 */
//	private void validaValorTipoEmpreendimentoTrailer(ItemEstruturaTXTDTO ItemEstruturaTXTDTO) throws SemanticValidationException, ECARException{
//		String valorTipoEmpreendimentoTrailer = ItemEstruturaTXTDTO.getItemEstruturaTrailerDTO().getTipoEmpreendimento();
//		if (valorTipoEmpreendimentoTrailer == null || valorTipoEmpreendimentoTrailer.equals(Dominios.STRING_VAZIA)){
//			levantaExcecao(ConstantesECAR.SEMANTICO_TRAILER_ITEM_TIPO_EMPREENDIMENTO_NAO_INFORMADO, null, ItemEstruturaTXTDTO, ItemEstruturaTXTDTO.getNumeroLinha(), null);
//			//Valor do Tipo de empreendimento n�o informado no rodap�
//		} else if (!ItemEstruturaTXTDTO.getTipoEmpreendimento().equals(valorTipoEmpreendimentoTrailer)){
//			levantaExcecao(ConstantesECAR.SEMANTICO_HEADER_ITEM_TIPO_EMPREENDIMENTO_DIFERENTE_TRAILER, null, ItemEstruturaTXTDTO, ItemEstruturaTXTDTO.getNumeroLinha(), null);
//			//Valor do Tipo de empreendimento no rodap� difere do indicado no cabe�alho
//		}
//	}

	/**
	 * Levanta a exce��o semantica
	 * @param codMotivo
	 * @param msgs
	 * @param iBusinessObjectDTO
	 * @param linha
	 * @param ex
	 * @throws ECARException
	 * @throws SemanticValidationException
	 */
	private void levantaExcecao(int codMotivo,String[] msgs, IBusinessObjectDTO iBusinessObjectDTO,int linha, Exception ex) throws ECARException, SemanticValidationException {
		dao = new Dao();
		MotivoRejeicaoMtr motivoRejeicaoMtr = (MotivoRejeicaoMtr)dao.buscar(MotivoRejeicaoMtr.class, new Long(codMotivo));
		SemanticValidationException semantic = new SemanticValidationException(ex, msgs, motivoRejeicaoMtr, null, new Long(linha));
		throw semantic;
	}
	
}
