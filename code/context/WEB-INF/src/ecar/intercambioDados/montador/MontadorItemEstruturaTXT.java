package ecar.intercambioDados.montador;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

import comum.util.ConstantesECAR;
import comum.util.Data;
import comum.util.Pagina;
import comum.util.Util;

import ecar.dao.EstruturaDao;
import ecar.dao.ItemEstruturaDao;
import ecar.dao.SituacaoDao;
import ecar.exception.ECARException;
import ecar.intercambioDados.IBusinessObject;
import ecar.intercambioDados.dto.ItemEstruturaTXTDTO;
import ecar.intercambioDados.importacao.comunicacao.IRegistro;
import ecar.intercambioDados.importacao.comunicacao.TipoRegistroEnum;
import ecar.intercambioDados.recurso.txt.RegistroTXT;
import ecar.permissao.ControlePermissao;
import ecar.pojo.EstruturaEtt;
import ecar.pojo.ItemEstrutUsuarioIettus;
import ecar.pojo.ItemEstruturaIett;
import ecar.pojo.SituacaoSit;
import ecar.pojo.UsuarioUsu;
import ecar.pojo.intercambioDados.PerfilIntercambioDadosPflid;
import ecar.pojo.intercambioDados.funcionalidade.PerfilIntercambioDadosCadastroPidc;
import ecar.pojo.intercambioDados.tecnologia.DadosTecnologiaPerfilTxtDtpt;
import ecar.util.Dominios;

public class MontadorItemEstruturaTXT implements IMontadorTXT {

	
	public IBusinessObject montar(IRegistro registro, PerfilIntercambioDadosPflid perfil, UsuarioUsu usuarioLogado) throws ECARException {
		
		
		ItemEstruturaTXTDTO itemEstruturaDTO = null;		
		SituacaoDao situacaoDao = new SituacaoDao(null);
		ItemEstruturaDao itemEstruturaDao = new ItemEstruturaDao(null);
		EstruturaDao estruturaDao = new EstruturaDao(null);
		
		String[] posicoes = Util.split(((RegistroTXT)registro).getLinha(),((DadosTecnologiaPerfilTxtDtpt)perfil.getDadosTecnologiaPerfilDtp()).getSeparadorCamposDtpt());
		int tipo = Integer.parseInt(posicoes[0]);
		TipoRegistroEnum tipoRegistro = TipoRegistroEnum.valueOf(tipo);
		if (!tipoRegistro.equals(TipoRegistroEnum.HEADER_ARQUIVO) && 
			!tipoRegistro.equals(TipoRegistroEnum.TRAILER_ARQUIVO)){
			if (tipoRegistro.equals(TipoRegistroEnum.HEADER_ITEM)){
				itemEstruturaDTO = (ItemEstruturaTXTDTO)montaRegistroHeaderItemDTO(((RegistroTXT)registro).getLinha(), posicoes, 0); 
			} 
		}
		
		ItemEstruturaIett iett = null;
		iett = new ItemEstruturaIett();
		
		//Código - se existir, é uma alteração ou exclusao, senao inserção
		iett.setIndAtivoIett(Dominios.SIM);
	
//		//siglaIett
		iett.setSiglaIett(itemEstruturaDTO.getCodigo());
		
//		//Nome - nomeIett
		iett.setNomeIett(itemEstruturaDTO.getNome());
		
//		//Descrição - descricaoR2
		iett.setDescricaoR2(itemEstruturaDTO.getDescricao());
		
//		//Data de Início - dataInicioIett
		iett.setDataInicioIett(Data.parseDate(itemEstruturaDTO.getDataInicio(), ConstantesECAR.FORMATO_DATA_IMPORTACAO));
		
//		//Data de Término - dataTerminoIett
		iett.setDataTerminoIett(Data.parseDate(itemEstruturaDTO.getDataConclusao(), ConstantesECAR.FORMATO_DATA_IMPORTACAO));
		
//		//Custo - valPrevistoFuturoIett		
		if (itemEstruturaDTO.getCusto() != null && !itemEstruturaDTO.getCusto().equals(Dominios.STRING_VAZIA)){
			BigDecimal custo = new BigDecimal(Double.valueOf(Util.formataNumero(itemEstruturaDTO.getCusto())).doubleValue());
			iett.setValPrevistoFuturoIett(custo);
		}
		
//		//Situação - situacaoSit		
		SituacaoSit situacaoSit = null;
		//será usada a situação configurada no perfil (situacaoSitNaoInformadoImp) caso não seja informada a situação no arquivo
		if (itemEstruturaDTO.getSituacao() == null || itemEstruturaDTO.getSituacao().equals(Dominios.STRING_VAZIA)){
			situacaoSit = ((PerfilIntercambioDadosCadastroPidc)perfil).getSituacaoNaoInformadaPidc();
		} else {
			//será usada a situação configurado no perfil (situacaoSitSemCorrespondenteImp)caso a situação informada não tenha correspondente no e-car
			situacaoSit = situacaoDao.getSituacaoSitByDescricao(itemEstruturaDTO.getSituacao());
			if (situacaoSit == null){
				situacaoSit = ((PerfilIntercambioDadosCadastroPidc)perfil).getSituacaoSemCorrespondentePidc(); 
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
		
		ItemEstruturaIett itemBase = itemEstruturaDao.getItemEstruturaIettByEstruturaDescricaoR1(((PerfilIntercambioDadosCadastroPidc)perfil).getEstruturaBasePidc(), itemEstruturaDTO.getValorAssociacao());
		ItemEstruturaIett itemNivelSuperior = itemEstruturaDao.getItemEstruturaIettByEstruturaDescricaoR3(((PerfilIntercambioDadosCadastroPidc)perfil).getEstruturaItemNivelSuperiorPidc(), itemBase, itemEstruturaDTO.getTipoEmpreendimento()); //itemEstruturaDao.getItemEstruturaIett(perfil.getEstruturaEttItemNivelSuperiorImp(), itemBase, perfil.getItemEstruturaIettNivelSuperiorImp().getNomeIett());
				
		iett.setItemEstruturaIett(itemNivelSuperior);
		iett.setEstruturaEtt((EstruturaEtt) estruturaDao.buscar(EstruturaEtt.class, ((PerfilIntercambioDadosCadastroPidc)perfil).getEstruturaCriacaoItemPidc().getCodEtt()));
		
		if (itemNivelSuperior != null)
		iett.setNivelIett(itemNivelSuperior.getNivelIett() + 1);
		
		if (itemEstruturaDTO.getOperacao().equals(ConstantesECAR.TIPO_OPERACAO_INCLUSAO)){
			if (((PerfilIntercambioDadosCadastroPidc)perfil).getIndUsuarioProcessamentoAssociacaoItemPflid() != null && ((PerfilIntercambioDadosCadastroPidc)perfil).getIndUsuarioProcessamentoAssociacaoItemPflid().equals(Dominios.SIM)){
				iett.setUsuarioUsuByCodUsuIncIett(usuarioLogado);
			} else {
				iett.setUsuarioUsuByCodUsuIncIett(((PerfilIntercambioDadosCadastroPidc)perfil).getUsuarioImportacao());
			}
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
		} else if (itemEstruturaDTO.getOperacao().equals(ConstantesECAR.TIPO_OPERACAO_ALTERACAO)){
			if (((PerfilIntercambioDadosCadastroPidc)perfil).getIndUsuarioProcessamentoAssociacaoItemPflid() != null && ((PerfilIntercambioDadosCadastroPidc)perfil).getIndUsuarioProcessamentoAssociacaoItemPflid().equals(Dominios.SIM)){
				iett.setUsuarioUsuByCodUsuUltManutIett(usuarioLogado);
			} else {
				iett.setUsuarioUsuByCodUsuUltManutIett(((PerfilIntercambioDadosCadastroPidc)perfil).getUsuarioImportacao());
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
	 * USADA PARA A MONTAGEM PARA 
	 */
	public IBusinessObject montar(IBusinessObject objetoNegocio, PerfilIntercambioDadosPflid perfil, UsuarioUsu usuarioLogado) throws ECARException {
	
		ItemEstruturaDao itemEstruturaDao = new ItemEstruturaDao(null);
		EstruturaDao estruturaDao = new EstruturaDao(null);
		
		ItemEstruturaIett iett = null;		
		iett = itemEstruturaDao.getItemEstruturaIettByEstruturaSiglaIett(((PerfilIntercambioDadosCadastroPidc)perfil).getEstruturaCriacaoItemPidc(), ((ItemEstruturaIett)objetoNegocio).getSiglaIett(), ((ItemEstruturaIett)objetoNegocio).getDescricaoR1(), ((ItemEstruturaIett)objetoNegocio).getDescricaoR3());
		
			// se não existir no banco
			if (iett==null) {	
				iett =  new ItemEstruturaIett();
			}
			
//			//siglaIett
			iett.setSiglaIett(((ItemEstruturaIett)objetoNegocio).getSiglaIett());
			
//			//Nome - nomeIett
			iett.setNomeIett(((ItemEstruturaIett)objetoNegocio).getNomeIett());
			
//			//Descrição - descricaoR2
			iett.setDescricaoR2(((ItemEstruturaIett)objetoNegocio).getDescricaoR2());
			
//			//Data de Início - dataInicioIett
			iett.setDataInicioIett(((ItemEstruturaIett)objetoNegocio).getDataInicioIett());
			
//			//Data de Término - dataTerminoIett
			iett.setDataTerminoIett(((ItemEstruturaIett)objetoNegocio).getDataTerminoIett());
			
//			//Custo - valPrevistoFuturoIett		
			iett.setValPrevistoFuturoIett(((ItemEstruturaIett)objetoNegocio).getValPrevistoFuturoIett());
			
//			//Situação - situacaoSit		
			iett.setSituacaoSit(((ItemEstruturaIett)objetoNegocio).getSituacaoSit());
			
			//Tipo - descricaoR4
			iett.setDescricaoR4(((ItemEstruturaIett)objetoNegocio).getDescricaoR4());
			
			//Sub-Tipo - descricaoR5		
			iett.setDescricaoR5(((ItemEstruturaIett)objetoNegocio).getDescricaoR5());
			
			//Valor de ligação  - descricaoR1		
			iett.setDescricaoR1(((ItemEstruturaIett)objetoNegocio).getDescricaoR1());
			
			//Novos campos acrescentados para importação
			
			//Executor  - descricaoIett
			iett.setDescricaoIett(((ItemEstruturaIett)objetoNegocio).getDescricaoIett());
			
			//Meta PAC  - origemIett
			iett.setOrigemIett(((ItemEstruturaIett)objetoNegocio).getOrigemIett());
			
			//Investimento Previsto 2007-2010  - objetivoGeralIett
			iett.setObjetivoGeralIett(((ItemEstruturaIett)objetoNegocio).getObjetivoGeralIett());
			
			//Investimento Previsto após 2010  - objetivoEspecificoIett
			iett.setObjetivoEspecificoIett(((ItemEstruturaIett)objetoNegocio).getObjetivoEspecificoIett());
			
			//Estágio  - beneficiosIett
			iett.setBeneficiosIett(((ItemEstruturaIett)objetoNegocio).getBeneficiosIett());
			
			//Tipo de Empreendimento  - descricaoR3
			iett.setDescricaoR3(((ItemEstruturaIett)objetoNegocio).getDescricaoR3());
		
			/*
			 * É FEITA A CONSULTA NOVAMENTE POR CAUSA DO LAZY INITIALIZE
			 */
			
			ItemEstruturaIett itemBase = itemEstruturaDao.getItemEstruturaIettByEstruturaDescricaoR1(((PerfilIntercambioDadosCadastroPidc)perfil).getEstruturaBasePidc(), ((ItemEstruturaIett)objetoNegocio).getDescricaoR1());
			ItemEstruturaIett itemNivelSuperior = itemEstruturaDao.getItemEstruturaIettByEstruturaDescricaoR3(((PerfilIntercambioDadosCadastroPidc)perfil).getEstruturaItemNivelSuperiorPidc(), itemBase, ((ItemEstruturaIett)objetoNegocio).getDescricaoR3()); 
					
			if (itemNivelSuperior!=null) {
				iett.setItemEstruturaIett(itemNivelSuperior);
				iett.setEstruturaEtt((EstruturaEtt) estruturaDao.buscar(EstruturaEtt.class, ((PerfilIntercambioDadosCadastroPidc)perfil).getEstruturaCriacaoItemPidc().getCodEtt()));
				iett.setNivelIett(itemNivelSuperior.getNivelIett() + 1);

			} else {
				System.out.println("ITEM ESTRUTURA SUPEPERIOR É NULO");
			}
			
			iett.setIndAtivoIett(((ItemEstruturaIett)objetoNegocio).getIndAtivoIett());
			
			// INCLUSAO
			if (iett.getCodigo()==null){
				if (((PerfilIntercambioDadosCadastroPidc)perfil).getIndUsuarioProcessamentoAssociacaoItemPflid() != null && ((PerfilIntercambioDadosCadastroPidc)perfil).getIndUsuarioProcessamentoAssociacaoItemPflid().equals(Dominios.SIM)){
					iett.setUsuarioUsuByCodUsuIncIett(usuarioLogado);
				} else {
					iett.setUsuarioUsuByCodUsuIncIett(((PerfilIntercambioDadosCadastroPidc)perfil).getUsuarioImportacao());
				}
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
				
			// ALTERACAO OU REMOÇÃO
			} else {
				if (((PerfilIntercambioDadosCadastroPidc)perfil).getIndUsuarioProcessamentoAssociacaoItemPflid() != null && ((PerfilIntercambioDadosCadastroPidc)perfil).getIndUsuarioProcessamentoAssociacaoItemPflid().equals(Dominios.SIM)){
					iett.setUsuarioUsuByCodUsuUltManutIett(usuarioLogado);
				} else {
					iett.setUsuarioUsuByCodUsuUltManutIett(((PerfilIntercambioDadosCadastroPidc)perfil).getUsuarioImportacao());
				}
			}
			
			
		
		
		return iett;
		
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
	
}

