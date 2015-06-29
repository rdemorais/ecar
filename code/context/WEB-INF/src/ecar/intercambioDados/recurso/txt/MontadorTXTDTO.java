package ecar.intercambioDados.recurso.txt;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import comum.util.ConstantesECAR;
import comum.util.Data;
import comum.util.Pagina;
import comum.util.Util;

import ecar.dao.EstruturaDao;
import ecar.dao.ItemEstruturaDao;
import ecar.dao.OrgaoDao;
import ecar.dao.SituacaoDao;
import ecar.exception.ECARException;
import ecar.intercambioDados.dto.IBusinessObjectDTO;
import ecar.intercambioDados.dto.ItemEstruturaTXTDTO;
import ecar.intercambioDados.dto.ItemEstruturaTrailerTXTDTO;
import ecar.intercambioDados.dto.MontadorDTO;
import ecar.intercambioDados.importacao.comunicacao.Configuracao;
import ecar.intercambioDados.importacao.comunicacao.IRegistro;
import ecar.intercambioDados.importacao.comunicacao.TipoRegistroEnum;
import ecar.permissao.ControlePermissao;
import ecar.pojo.ApontamentoApt;
import ecar.pojo.EstruturaEtt;
import ecar.pojo.ExercicioExe;
import ecar.pojo.ItemEstrtIndResulIettr;
import ecar.pojo.ItemEstrtIndResulLocalIettirl;
import ecar.pojo.ItemEstrutAcaoIetta;
import ecar.pojo.ItemEstrutEntidadeIette;
import ecar.pojo.ItemEstrutLocalIettl;
import ecar.pojo.ItemEstrutUsuarioIettus;
import ecar.pojo.ItemEstruturaIett;
import ecar.pojo.SituacaoSit;
import ecar.pojo.UsuarioUsu;
import ecar.pojo.intercambioDados.PerfilIntercambioDadosPflid;
import ecar.pojo.intercambioDados.funcionalidade.PerfilIntercambioDadosCadastroPidc;
import ecar.pojo.intercambioDados.funcionalidade.TipoFuncionalidadeEnum;
import ecar.pojo.intercambioDados.tecnologia.DadosTecnologiaPerfilTxtDtpt;
import ecar.util.Dominios;

public class MontadorTXTDTO extends MontadorDTO{

	//Header do Item
	public static final int POSICAO_CAMPO_IDENTIFICADOR = 0;
	public static final int POSICAO_CAMPO_OPERACAO = 1;
	public static final int POSICAO_CAMPO_CODIGO = 2;
	public static final int POSICAO_CAMPO_NOME = 3;
	public static final int POSICAO_CAMPO_DESCRICAO = 4;
	public static final int POSICAO_CAMPO_DATA_INICIO = 5;
	public static final int POSICAO_CAMPO_DATA_CONCLUSAO = 6;
//	public static final int POSICAO_CAMPO_ORGAO = 7;
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
	 * Contrutor
	 * @param config
	 */
	public MontadorTXTDTO(Configuracao config) {
		this.config = config;
	}
	
	/**
	 * Contrutor
	 */
	public MontadorTXTDTO() {	}
	
	
	/**
	 * Monta uma lista de DTO de acordo com a lista de registros passado como parâmetro
	 * @param listaRegistro
	 */
	public List<IBusinessObjectDTO> montaDTO(List<IRegistro> listaRegistro) {
		PerfilIntercambioDadosPflid perfil = config.getPerfil();
		//Cria a lista de IBusinessObjectDTO para retorno
		List<IBusinessObjectDTO> listDTO = new ArrayList<IBusinessObjectDTO>();
		RegistroTXT registro = null;
		if (listaRegistro != null){
			Iterator<IRegistro> itListaRegistro = listaRegistro.iterator();
			//Percorre a lista de registros pegando a linha e chamando o montaRegistroDTO que 
			//devolve um DTO de acordo com a linha passada
			String[] posicoes = null;
			TipoRegistroEnum tipoRegistro = null;
			ItemEstruturaTXTDTO itemEstruturaDTO = null;
			ItemEstruturaTrailerTXTDTO itemEstruturaTrailerDTO = null;
			int numeroLinha = REGISTRO_INICIAL;
			while (itListaRegistro.hasNext()){
				registro = (RegistroTXT) itListaRegistro.next();
				posicoes = Util.split(registro.getLinha(),((DadosTecnologiaPerfilTxtDtpt)perfil.getDadosTecnologiaPerfilDtp()).getSeparadorCamposDtpt());
				int tipo = Integer.parseInt(posicoes[0]);
				tipoRegistro = TipoRegistroEnum.valueOf(tipo);
				if (!tipoRegistro.equals(TipoRegistroEnum.HEADER_ARQUIVO) && 
						!tipoRegistro.equals(TipoRegistroEnum.TRAILER_ARQUIVO)){
					if (tipoRegistro.equals(TipoRegistroEnum.HEADER_ITEM)){
						itemEstruturaDTO = montaRegistroHeaderItemDTO(registro.getLinha(), posicoes, numeroLinha); 
						listDTO.add(itemEstruturaDTO);
					} else if (tipoRegistro.equals(TipoRegistroEnum.TRAILER_ITEM)){
						itemEstruturaTrailerDTO = montaRegistroTrailerItemDTO(registro.getLinha(), posicoes, numeroLinha);
						itemEstruturaDTO.setItemEstruturaTrailerDTO(itemEstruturaTrailerDTO);
					}
				}
				numeroLinha++;
			}
		}
		return listDTO;
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
//		itemEstruturaDTO.setOrgao(posicoes[POSICAO_CAMPO_ORGAO]);
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
	
	/**
	 * Monta o itemEstruturaIett de acordo com os dados do DTO de item
	 * @param itemEstruturaIett
	 * @param itemEstruturaDTO
	 * @return
	 */
	public ItemEstruturaIett montaItemEstruturaIett(ItemEstruturaIett itemEstruturaIett, ItemEstruturaTXTDTO itemEstruturaDTO, UsuarioUsu usuarioLogado) throws ECARException{
		SituacaoDao situacaoDao = new SituacaoDao(null);
		OrgaoDao orgaoDao = new OrgaoDao(null);
		ItemEstruturaDao itemEstruturaDao = new ItemEstruturaDao(null);
		EstruturaDao estruturaDao = new EstruturaDao(null);
		PerfilIntercambioDadosPflid perfil = config.getPerfil();
		
		ItemEstruturaIett iett = null;
		//Se o item passado como parâmetro for igual a null, 
		//será um novo item
		//caso contrário será uma alteração de um item já existente
		if (itemEstruturaIett == null){
			iett = new ItemEstruturaIett();	
		} else {
			iett = itemEstruturaIett;
		}	
		//Código - siglaIett
		iett.setSiglaIett(itemEstruturaDTO.getCodigo());
		//Nome - nomeIett
		iett.setNomeIett(itemEstruturaDTO.getNome());
		//Descrição - descricaoR2
		iett.setDescricaoR2(itemEstruturaDTO.getDescricao());
		//Data de Início - dataInicioIett
		iett.setDataInicioIett(Data.parseDate(itemEstruturaDTO.getDataInicio(), ConstantesECAR.FORMATO_DATA_IMPORTACAO));
		//Data de Término - dataTerminoIett
		iett.setDataTerminoIett(Data.parseDate(itemEstruturaDTO.getDataConclusao(), ConstantesECAR.FORMATO_DATA_IMPORTACAO));
		//Custo - valPrevistoFuturoIett		
		if (itemEstruturaDTO.getCusto() != null && !itemEstruturaDTO.getCusto().equals(Dominios.STRING_VAZIA)){
			BigDecimal custo = new BigDecimal(Double.valueOf(Util.formataNumero(itemEstruturaDTO.getCusto())).doubleValue());
			iett.setValPrevistoFuturoIett(custo);
		}
		//Situação - situacaoSit		
		SituacaoSit situacaoSit = null;
		//será usada a situação configurada no perfil (situacaoSitNaoInformadoImp) caso não seja informada a situação no arquivo
		if (itemEstruturaDTO.getSituacao() == null || itemEstruturaDTO.getSituacao().equals(Dominios.STRING_VAZIA)){
			if (perfil.getTipoFuncionalidade().equals(TipoFuncionalidadeEnum.CADASTRO)) {
				situacaoSit = ((PerfilIntercambioDadosCadastroPidc)perfil).getSituacaoNaoInformadaPidc();
			} else {
				//TODO caso haja importação de acompanhamento/parecer via arquivo texto, o código referente ao parecer deverá ser informado aqui.   
			}
		} else {
			//será usada a situação configurado no perfil (situacaoSitSemCorrespondenteImp)caso a situação informada não tenha correspondente no e-car
			situacaoSit = situacaoDao.getSituacaoSitByDescricao(itemEstruturaDTO.getSituacao());
			if (perfil.getTipoFuncionalidade().equals(TipoFuncionalidadeEnum.CADASTRO)) {
				if (situacaoSit == null){
					situacaoSit = ((PerfilIntercambioDadosCadastroPidc)perfil).getSituacaoSemCorrespondentePidc(); 
				}
			}
		}
		iett.setSituacaoSit(situacaoSit);
		//Tipo - descricaoR4
		iett.setDescricaoR4(itemEstruturaDTO.getTipo());
		//Sub-Tipo - descricaoR5		
		iett.setDescricaoR5(itemEstruturaDTO.getSubTipo());
		//Valor de ligação  - descricaoR1		
		iett.setDescricaoR1(itemEstruturaDTO.getValorAssociacao());
		
		//Novos campos acrescentados para importação
		
		//Executor  - descricaoIett
		iett.setDescricaoIett(itemEstruturaDTO.getExecutor());
		//Meta PAC  - origemIett
		iett.setOrigemIett(itemEstruturaDTO.getMetaPac());
		//Investimento Previsto 2007-2010  - objetivoGeralIett
		iett.setObjetivoGeralIett(itemEstruturaDTO.getInvestPrev20072010());
		//Investimento Previsto após 2010  - objetivoEspecificoIett
		iett.setObjetivoEspecificoIett(itemEstruturaDTO.getInvestPrevApos2010());
		//Estágio  - beneficiosIett
		iett.setBeneficiosIett(itemEstruturaDTO.getEstagio());
		//Tipo de Empreendimento  - descricaoR3
		iett.setDescricaoR3(itemEstruturaDTO.getTipoEmpreendimento());
		
		ItemEstruturaIett itemBase = null;
		
		if (perfil.getTipoFuncionalidade().equals(TipoFuncionalidadeEnum.CADASTRO)) {
			itemBase = itemEstruturaDao.getItemEstruturaIettByEstruturaDescricaoR1(((PerfilIntercambioDadosCadastroPidc)perfil).getEstruturaBasePidc(), itemEstruturaDTO.getValorAssociacao());
			ItemEstruturaIett itemNivelSuperior = itemEstruturaDao.getItemEstruturaIettByEstruturaDescricaoR3(((PerfilIntercambioDadosCadastroPidc)perfil).getEstruturaItemNivelSuperiorPidc(), itemBase, itemEstruturaDTO.getTipoEmpreendimento());
					
			iett.setItemEstruturaIett(itemNivelSuperior);
			iett.setEstruturaEtt((EstruturaEtt) estruturaDao.buscar(EstruturaEtt.class, ((PerfilIntercambioDadosCadastroPidc)perfil).getEstruturaCriacaoItemPidc().getCodEtt()));
			iett.setIndAtivoIett(Dominios.SIM);
			iett.setNivelIett(itemNivelSuperior.getNivelIett() + 1);
		}
		
		if (itemEstruturaDTO.getOperacao().equals(ConstantesECAR.TIPO_OPERACAO_INCLUSAO)){
			if (perfil.getIndUsuarioProcessamentoAssociacaoItemPflid() != null && perfil.getIndUsuarioProcessamentoAssociacaoItemPflid().equals(Dominios.SIM)){
				iett.setUsuarioUsuByCodUsuIncIett(usuarioLogado);
			} else {
				iett.setUsuarioUsuByCodUsuIncIett(perfil.getUsuarioImportacao());
			}
			
			if (perfil.getTipoFuncionalidade().equals(TipoFuncionalidadeEnum.CADASTRO)){
				if (((PerfilIntercambioDadosCadastroPidc)perfil).getGrupoAcessoItensImportadosPidc() != null){
					Set<ItemEstrutUsuarioIettus> iettus = new HashSet<ItemEstrutUsuarioIettus>();
					ItemEstrutUsuarioIettus itemEstrutUsuarioIettus = new ItemEstrutUsuarioIettus();
					itemEstrutUsuarioIettus.setItemEstruturaIett(iett);
					itemEstrutUsuarioIettus.setItemEstruturaIettOrigem(iett);
					itemEstrutUsuarioIettus.setCodTpPermIettus(ControlePermissao.PERMISSAO_GRUPO);
					itemEstrutUsuarioIettus.setSisAtributoSatb(((PerfilIntercambioDadosCadastroPidc)perfil).getGrupoAcessoItensImportadosPidc());
			
					itemEstrutUsuarioIettus.setIndLeituraIettus("S");
					itemEstrutUsuarioIettus.setIndEdicaoIettus("S");
					itemEstrutUsuarioIettus.setIndExcluirIettus("S");
					
					itemEstrutUsuarioIettus.setIndAtivMonitIettus("N");
					itemEstrutUsuarioIettus.setIndDesatMonitIettus("N");
					itemEstrutUsuarioIettus.setIndBloqPlanIettus("N");
					itemEstrutUsuarioIettus.setIndDesblPlanIettus("N");
					itemEstrutUsuarioIettus.setIndInfAndamentoIettus("N");
					itemEstrutUsuarioIettus.setIndEmitePosIettus("N");
					itemEstrutUsuarioIettus.setIndProxNivelIettus("N");
					
					itemEstrutUsuarioIettus.setDataInclusaoIettus(Data.getDataAtual());
					
					iettus.add(itemEstrutUsuarioIettus);
					iett.setItemEstrutUsuarioIettusesByCodIett(iettus);
				}
			}
		} else if (itemEstruturaDTO.getOperacao().equals(ConstantesECAR.TIPO_OPERACAO_ALTERACAO)){
			if (perfil.getIndUsuarioProcessamentoAssociacaoItemPflid() != null && perfil.getIndUsuarioProcessamentoAssociacaoItemPflid().equals(Dominios.SIM)){
				iett.setUsuarioUsuByCodUsuUltManutIett(usuarioLogado);
			} else {
				iett.setUsuarioUsuByCodUsuUltManutIett(perfil.getUsuarioImportacao());
			}
		} else if (itemEstruturaDTO.getOperacao().equals(ConstantesECAR.TIPO_OPERACAO_EXCLUSAO)){
			if (perfil.getIndUsuarioProcessamentoAssociacaoItemPflid() != null && perfil.getIndUsuarioProcessamentoAssociacaoItemPflid().equals(Dominios.SIM)){
				iett.setUsuarioUsuByCodUsuUltManutIett(usuarioLogado);
			} else {
				iett.setUsuarioUsuByCodUsuUltManutIett(perfil.getUsuarioImportacao());
			}
			iett.setIndAtivoIett(Pagina.NAO);
		}
		return iett;
	}


	
	

	/**
	 * Monta um IRegistro referente ao índice resultado/meta indicador do item. 
	 * @param indResultado
	 * @return
	 */
	public IRegistro montarRegistroIndResult (ItemEstrtIndResulIettr indResultado){
		//TODO
		return null;		
	}
	
	
	/**
	 * Monta um IRegistro referente ao Diario de Bordo  do item. 
	 * @param acao
	 * @return
	 */
	public IRegistro montarRegistroAcao (ItemEstrutAcaoIetta acao){
		//TODO
		return null;		
	}

	
	/**
	 * Monta um IRegistro referente ao Apontamento do ponto critico. 
	 * @param apontamento
	 * @return
	 */
	public IRegistro montarRegistroApontamentoPontoCritico (ApontamentoApt apontamento){
		//TODO
		return null;		
	}
	

	/**
	 * Monta um IRegistro referente aos Exercicios do Local 
	 * @param locais
	 * @param exercicios
	 * @return
	 */
	public IRegistro montarRegistroLocalIndResul (List<ItemEstrtIndResulLocalIettirl> locais,List<ExercicioExe> exercicios){
		//TODO
		return null;		
	}
	
	/**
	 * Monta um IRegistro referente a Localidade
	 * @param localidade
	 * @return
	 */
	public IRegistro montarRegistroLocalidade (ItemEstrutLocalIettl localidadeIett){
		//TODO
		return null;		
	}
	
	/**
	 * Monta um IRegistro referente a Entidade
	 * @param entidadeIett
	 * @return
	 */
	public IRegistro montarRegistroEntidade (ItemEstrutEntidadeIette entidadeIett){
		//TODO
		return null;		
	}
	
	
}