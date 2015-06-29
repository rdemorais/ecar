package ecar.intercambioDados.montador;

import comum.util.Util;

import ecar.exception.ECARException;
import ecar.intercambioDados.dto.IBusinessObjectDTO;
import ecar.intercambioDados.dto.ItemEstruturaTXTDTO;
import ecar.intercambioDados.dto.ItemEstruturaTrailerTXTDTO;
import ecar.intercambioDados.importacao.comunicacao.IRegistro;
import ecar.intercambioDados.importacao.comunicacao.TipoRegistroEnum;
import ecar.intercambioDados.recurso.txt.RegistroTXT;
import ecar.pojo.UsuarioUsu;
import ecar.pojo.intercambioDados.PerfilIntercambioDadosPflid;
import ecar.pojo.intercambioDados.tecnologia.DadosTecnologiaPerfilTxtDtpt;

public class MontadorItemEstruturaTXTDTO implements IMontadorDTO {

	//Header do Item
	public static final int POSICAO_CAMPO_IDENTIFICADOR = 0;
	public static final int POSICAO_CAMPO_OPERACAO = 1;
	public static final int POSICAO_CAMPO_CODIGO = 2;
	public static final int POSICAO_CAMPO_NOME = 3;
	public static final int POSICAO_CAMPO_DESCRICAO = 4;
	public static final int POSICAO_CAMPO_DATA_INICIO = 5;
	public static final int POSICAO_CAMPO_DATA_CONCLUSAO = 6;
	public static final int POSICAO_CAMPO_CUSTO = 7;
	public static final int POSICAO_CAMPO_SITUACAO = 8;
	public static final int POSICAO_CAMPO_TIPO = 9;
	public static final int POSICAO_CAMPO_SUBTIPO = 10;
	public static final int POSICAO_CAMPO_VALOR_ASSOCIACAO = 11;
	
	//-- Novos campos do Header do Item
	public static final int POSICAO_CAMPO_EXECUTOR = 12;
	public static final int POSICAO_CAMPO_META_PAC = 13;
	public static final int POSICAO_CAMPO_INVEST_PREV_2007_2010 = 14;
	public static final int POSICAO_CAMPO_INVEST_PREV_APOS_2010 = 15;
	public static final int POSICAO_CAMPO_ESTAGIO = 16;
	public static final int POSICAO_CAMPO_TIPO_EMPREENDIMENTO = 17;
	
	public static final int POSICAO_CAMPO_SEQUENCIAL = 18;
	
	//-- Trailer do Item
	public static final int POSICAO_CAMPO_IDENTIFICADOR_TRAILER = 0;
	public static final int POSICAO_CAMPO_CODIGO_TRAILER = 1;
	public static final int POSICAO_CAMPO_VALOR_ASSOCIACAO_TRAILER = 2;	
	public static final int POSICAO_CAMPO_TIPO_EMPREENDIMENTO_TRAILER = 3;
	public static final int POSICAO_CAMPO_SEQUENCIAL_TRAILER = 4;
	
	public static final int REGISTRO_INICIAL = 1;

	
	
	/**
	 * Verificar se é item pra montar
	 * @param listaRegistro
	 */
	public IBusinessObjectDTO getItemMontar(IRegistro registro, PerfilIntercambioDadosPflid perfil, UsuarioUsu usuarioLogado, IBusinessObjectDTO objetoNegocioAnterior, int numeroLinha) {
	

		IBusinessObjectDTO objetoNegocioDTO = null;

		
		String[] posicoes = Util.split(((RegistroTXT)registro).getLinha(),((DadosTecnologiaPerfilTxtDtpt)perfil.getDadosTecnologiaPerfilDtp()).getSeparadorCamposDtpt());
		int tipo = Integer.parseInt(posicoes[0]);
		TipoRegistroEnum tipoRegistro = TipoRegistroEnum.valueOf(tipo);
		if (!tipoRegistro.equals(TipoRegistroEnum.HEADER_ARQUIVO) && 
			!tipoRegistro.equals(TipoRegistroEnum.TRAILER_ARQUIVO)){
			if (tipoRegistro.equals(TipoRegistroEnum.HEADER_ITEM)){
				objetoNegocioDTO = montaRegistroHeaderItemDTO(((RegistroTXT)registro).getLinha(), posicoes, numeroLinha); 
			} else if (tipoRegistro.equals(TipoRegistroEnum.TRAILER_ITEM)){
				objetoNegocioDTO = montaRegistroTrailerItemDTO(((RegistroTXT)registro).getLinha(), posicoes, numeroLinha);
				if (objetoNegocioAnterior!=null)
					((ItemEstruturaTXTDTO)objetoNegocioAnterior).setItemEstruturaTrailerDTO((ItemEstruturaTrailerTXTDTO)objetoNegocioDTO);
				
				return null;
			}
		}
		
				
		return objetoNegocioDTO;
	}
	
	/**
	 * Monta e devolve um DTO (header do item) com as informações passadas na linha 
	 * @param registro
	 * @return
	 */
	private ItemEstruturaTXTDTO montaRegistroHeaderItemDTO(String linha, String[] posicoes, int numeroLinha){
		ItemEstruturaTXTDTO itemEstruturaDTO = new ItemEstruturaTXTDTO();
		itemEstruturaDTO.setIdentificador(posicoes[POSICAO_CAMPO_IDENTIFICADOR]);
		itemEstruturaDTO.setOperacao(posicoes[POSICAO_CAMPO_OPERACAO]);
		itemEstruturaDTO.setCodigo(posicoes[POSICAO_CAMPO_CODIGO]);
		itemEstruturaDTO.setNome(posicoes[POSICAO_CAMPO_NOME]);
		itemEstruturaDTO.setDescricao(posicoes[POSICAO_CAMPO_DESCRICAO]);
		itemEstruturaDTO.setDataInicio(posicoes[POSICAO_CAMPO_DATA_INICIO]);
		itemEstruturaDTO.setDataConclusao(posicoes[POSICAO_CAMPO_DATA_CONCLUSAO]);
		itemEstruturaDTO.setCusto(posicoes[POSICAO_CAMPO_CUSTO]);
		itemEstruturaDTO.setSituacao(posicoes[POSICAO_CAMPO_SITUACAO]);		
		itemEstruturaDTO.setTipo(posicoes[POSICAO_CAMPO_TIPO]);
		itemEstruturaDTO.setSubTipo(posicoes[POSICAO_CAMPO_SUBTIPO]);
		itemEstruturaDTO.setValorAssociacao(posicoes[POSICAO_CAMPO_VALOR_ASSOCIACAO]);
		itemEstruturaDTO.setSequencial(posicoes[POSICAO_CAMPO_SEQUENCIAL]);
		itemEstruturaDTO.setExecutor(posicoes[POSICAO_CAMPO_EXECUTOR]);
		itemEstruturaDTO.setMetaPac(posicoes[POSICAO_CAMPO_META_PAC]);
		itemEstruturaDTO.setInvestPrev20072010(posicoes[POSICAO_CAMPO_INVEST_PREV_2007_2010]);
		itemEstruturaDTO.setInvestPrevApos2010(posicoes[POSICAO_CAMPO_INVEST_PREV_APOS_2010]);
		itemEstruturaDTO.setEstagio(posicoes[POSICAO_CAMPO_ESTAGIO]);
		itemEstruturaDTO.setTipoEmpreendimento(posicoes[POSICAO_CAMPO_TIPO_EMPREENDIMENTO]);
		itemEstruturaDTO.setNumeroLinha(numeroLinha);
		itemEstruturaDTO.setLinha(linha);
		return itemEstruturaDTO;
	}
	
	/**
	 * Monta e devolve um DTO (trailer do item) com as informações passadas na linha 
	 * @param registro
	 * @return
	 */
	private ItemEstruturaTrailerTXTDTO montaRegistroTrailerItemDTO(String linha, String[] posicoes, int numeroLinha){
		ItemEstruturaTrailerTXTDTO itemEstruturaTrailerDTO = new ItemEstruturaTrailerTXTDTO();
		itemEstruturaTrailerDTO.setIdentificador(posicoes[POSICAO_CAMPO_IDENTIFICADOR_TRAILER]);
		itemEstruturaTrailerDTO.setCodigo(posicoes[POSICAO_CAMPO_CODIGO_TRAILER]);
		itemEstruturaTrailerDTO.setValorAssociacao(posicoes[POSICAO_CAMPO_VALOR_ASSOCIACAO_TRAILER]);
		itemEstruturaTrailerDTO.setSequencial(posicoes[POSICAO_CAMPO_SEQUENCIAL_TRAILER]);
		itemEstruturaTrailerDTO.setTipoEmpreendimento(posicoes[POSICAO_CAMPO_TIPO_EMPREENDIMENTO_TRAILER]);
		itemEstruturaTrailerDTO.setNumeroLinha(numeroLinha);
		itemEstruturaTrailerDTO.setLinha(linha);
		return itemEstruturaTrailerDTO;
	}
	
	
	public IBusinessObjectDTO montar(IRegistro registro, PerfilIntercambioDadosPflid perfil, UsuarioUsu usuarioLogado, IBusinessObjectDTO objetoNegocioAnterior, int numeroLinha) throws ECARException {
		return getItemMontar(registro, perfil, usuarioLogado, objetoNegocioAnterior, numeroLinha);
	}




}
