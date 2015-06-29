package ecar.intercambioDados.importacao.analisesemantica;

import java.math.BigDecimal;
import java.util.Date;

import org.apache.log4j.Logger;

import comum.database.Dao;
import comum.util.ConstantesECAR;

import ecar.dao.ItemEstruturaDao;
import ecar.exception.ECARException;
import ecar.exception.intercambioDados.SemanticValidationException;
import ecar.intercambioDados.IBusinessObject;
import ecar.pojo.ItemEstruturaIett;
import ecar.pojo.intercambioDados.MotivoRejeicaoMtr;
import ecar.pojo.intercambioDados.PerfilIntercambioDadosPflid;
import ecar.pojo.intercambioDados.funcionalidade.PerfilIntercambioDadosCadastroPidc;
import ecar.pojo.intercambioDados.funcionalidade.TipoFuncionalidadeEnum;

public class AnaliseSemanticaItemEstruturaTXT extends AnaliseSemantica {
	
	/**
	 * 
	 * @param config
	 */
	public AnaliseSemanticaItemEstruturaTXT() {
		logger = Logger.getLogger(this.getClass());
	}
	
	/**
	 * Faz a validação semântica no objeto passado como parâmetro
	 */
	public boolean valida(IBusinessObject objNegocio, PerfilIntercambioDadosPflid perfil) throws SemanticValidationException, ECARException {
		
		
		ItemEstruturaIett itemEstruturaIett = (ItemEstruturaIett) objNegocio;
		
		//chama o método que faz a validação do código do empreendimento
		validaCodigo(itemEstruturaIett);
		
		//chama o método que valida se a data de conclusão do arquivo é válida
		validaDataConclusao(itemEstruturaIett);
		
		//chama o método de validação do custo
		validaCusto(itemEstruturaIett);
		
		//chama o método de validação do valor de associação
		validaValorAssociacao(itemEstruturaIett, perfil);
		
		//chama o método para validar a exclusão quando a operação = E
		validaOperacao(itemEstruturaIett, perfil);
		
		//chama o método de validação do valor do tipo de empreendimento
		validaValorTipoEmpreendimento(itemEstruturaIett, perfil);
		
		//se não encontrou nenhum problema e não levantou nenhuma exceção retorna true
		return true;
	}
	
	/**
	 * Valida se o código do empreendimento foi informado
	 * @param itemEstrutura
	 */
	private void validaCodigo(ItemEstruturaIett itemEstrutura) throws SemanticValidationException, ECARException{
//		if (itemEstrutura.getCodigo() == null){
//			levantaExcecao(ConstantesECAR.SEMANTICO_HEADER_ITEM_IDENTIFICADOR_NAO_INFORMADO, null, itemEstrutura, 0, null);
//			//Identificador do empreendimento não informado no cabeçalho
//		}
	}
	
	/**
	 * Valida se a data de conclusão do empreendimento é válida
	 * @param itemEstruturaDTO
	 */
	private void validaDataConclusao(ItemEstruturaIett itemEstrutura) throws SemanticValidationException, ECARException{
		Date dataInicioDate = null;
		Date dataConclusaoDate = null;
		
		//valida se data de conclusão passada está no formato esperado
		dataInicioDate = itemEstrutura.getDataInicioIett();
		dataConclusaoDate = itemEstrutura.getDataTerminoIett();; 
				
			if (dataInicioDate != null && dataConclusaoDate!=null && dataInicioDate.after(dataConclusaoDate)){
				levantaExcecao(ConstantesECAR.SEMANTICO_HEADER_ITEM_DATA_CONCLUSAO_INFERIOR_DATA_INICIO, null, itemEstrutura, 0, null);
				//Data de conclusão inferior a data de início.
			}

	}
	
	/**
	 * Valida se o custo passado é um valor válido
	 * @param itemEstruturaDTO
	 */
	private void validaCusto(ItemEstruturaIett itemEstrutura) throws SemanticValidationException, ECARException{
		BigDecimal valorCustoFormatado = null;
		boolean custoInvalido = false;
	
		valorCustoFormatado = itemEstrutura.getValPrevistoFuturoIett();
		
		if (valorCustoFormatado!=null && valorCustoFormatado.doubleValue() < 0){
			custoInvalido = true;
			//Custo do empreendimeneto inválido
		}
	
		if (custoInvalido){
			levantaExcecao(ConstantesECAR.SEMANTICO_HEADER_ITEM_CUSTO_INVALIDO, null, itemEstrutura, 0, null);
			//Custo do empreendimeneto inválido
		}
	}
	
	/**
	 * Valida se o valor de associação foi informado e se existe empreendimento com o valor informado. 
	 * @param itemEstruturaDTO
	 */
	private void validaValorAssociacao(ItemEstruturaIett itemEstrutura, PerfilIntercambioDadosPflid perfilIntercambioDadosPflid) throws SemanticValidationException, ECARException{

		ItemEstruturaDao itemEstruturaDao = new ItemEstruturaDao(null);
		ItemEstruturaIett itemBase = null;
		if (perfilIntercambioDadosPflid.getTipoFuncionalidade().equals(TipoFuncionalidadeEnum.CADASTRO)) {
			itemBase = itemEstruturaDao.getItemEstruturaIettByEstruturaDescricaoR1(((PerfilIntercambioDadosCadastroPidc)perfilIntercambioDadosPflid).getEstruturaBasePidc(), itemEstrutura.getDescricaoR1());
		}
		if (itemBase == null){
			levantaExcecao(ConstantesECAR.SEMANTICO_HEADER_ITEM_VALOR_ASSOCIACAO_SEM_CORRESPONDENCIA, null, itemEstrutura, 0, null);
			//Não encontrada correspondência do valor de associação do empreendimento com item no sistema.
		}
	}
	
	/**
	 * Valida se o valor Tipo do Empreendimento foi informado e se existe empreendimento com o valor informado. 
	 * @param itemEstruturaDTO
	 */
	private void validaValorTipoEmpreendimento(ItemEstruturaIett itemEstrutura, PerfilIntercambioDadosPflid perfilIntercambioDadosPflid) throws SemanticValidationException, ECARException{
		
		ItemEstruturaDao itemEstruturaDao = new ItemEstruturaDao(null);
		ItemEstruturaIett itemNivelSuperior = null; 
		if (perfilIntercambioDadosPflid.getTipoFuncionalidade().equals(TipoFuncionalidadeEnum.CADASTRO)) {	
			itemNivelSuperior = itemEstruturaDao.getItemEstruturaIettByEstruturaDescricaoR3Validacao(((PerfilIntercambioDadosCadastroPidc)perfilIntercambioDadosPflid).getEstruturaItemNivelSuperiorPidc(),  itemEstrutura.getDescricaoR1(), itemEstrutura.getDescricaoR3());
		}
		if (itemNivelSuperior == null){
			levantaExcecao(ConstantesECAR.SEMANTICO_HEADER_ITEM_VALOR_TIPO_EMPREENDIMENTO_SEM_CORRESPONDENCIA, null, itemEstrutura, 0, null);
			//Não encontrada correspondência do valor de associação do empreendimento com item no sistema.
		}	

	}
	
	/**
	 * Valida se o item existe no e-car quando importado uma exclusão de um item
	 * @param itemEstruturaDTO
	 */
	private void validaOperacao(ItemEstruturaIett itemEstrutura, PerfilIntercambioDadosPflid perfilIntercambioDadosPflid) throws SemanticValidationException, ECARException{
		
		if ((itemEstrutura.getIndAtivoIett() != null) && (itemEstrutura.getIndAtivoIett().equals("N"))){
			ItemEstruturaIett itemEstruturaIett = null;
			ItemEstruturaDao itemEstruturaDao = new ItemEstruturaDao(null);
			
			if (perfilIntercambioDadosPflid.getTipoFuncionalidade().equals(TipoFuncionalidadeEnum.CADASTRO)) {
				itemEstruturaIett = itemEstruturaDao.getItemEstruturaIettByEstruturaSiglaIett(((PerfilIntercambioDadosCadastroPidc)perfilIntercambioDadosPflid).getEstruturaCriacaoItemPidc(), itemEstrutura.getSiglaIett(), itemEstrutura.getDescricaoR1(), itemEstrutura.getDescricaoR3());
			}
			if (itemEstruturaIett == null){
				levantaExcecao(ConstantesECAR.SEMANTICO_HEADER_ITEM_EXCLUSAO_EMPREENDIMENTO_INEXISTENTE, null, itemEstrutura, 0, null);
				//Tentativa de exclusão de empreendimento inexistente no sistema
			}
		}
	}	

	/**
	 * Levanta a exceção semantica
	 * @param codMotivo
	 * @param msgs
	 * @param iBusinessObjectDTO
	 * @param linha
	 * @param ex
	 * @throws ECARException
	 * @throws SemanticValidationException
	 */
	private void levantaExcecao(int codMotivo,String[] msgs, IBusinessObject iBusinessObject,int linha, Exception ex) throws ECARException, SemanticValidationException {
		Dao dao = new Dao();
		MotivoRejeicaoMtr motivoRejeicaoMtr = (MotivoRejeicaoMtr)dao.buscar(MotivoRejeicaoMtr.class, new Long(codMotivo));
		SemanticValidationException semantic = new SemanticValidationException(ex, msgs, motivoRejeicaoMtr, iBusinessObject, new Long(linha));
		
		throw semantic;
	}
	
	
}
