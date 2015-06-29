package ecar.historico;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.hibernate.Session;

import ecar.dao.ConfiguracaoDao;
import ecar.exception.ECARException;
import ecar.pojo.ConfiguracaoCfg;
import ecar.pojo.HistoricoIettH;
import ecar.pojo.HistoricoMaster;
import ecar.pojo.HistoricoMotivo;
import ecar.pojo.ItemEstruturaIett;

/**
 *
 * @author 70744416353
 */
public class HistoricoIett implements IHistoricoAntigo {
	
	//Constantes
    /**
     *
     */
    public static final int alteracao = 19;
    /**
     *
     */
    public static final int exclusao = 20;
	
	private HistoricoMaster historicoMaster = null;
	private ItemEstruturaIett iett = null;
	private Integer acao = null;
	private Session session = null;
	private HttpServletRequest request;
	private ConfiguracaoDao cfgDao = null;
		
        /**
         *
         * @param iett
         * @param acao
         * @param session
         * @param cfgDao
         * @param request
         */
        public HistoricoIett(ItemEstruturaIett iett,
							int acao,
							Session session, 
							ConfiguracaoDao cfgDao, 
							HttpServletRequest request){
		
		this.iett = iett;
		this.acao = acao;
		this.session = session;
		this.cfgDao = cfgDao;
		this.request = request;
		
		this.iett.setUsuarioUsuByCodUsuUltManutIett(((ecar.login.SegurancaECAR) request.getSession().getAttribute("seguranca")).getUsuario());
		
	}
	
        /**
         *
         * @param acao
         * @throws ECARException
         */
        public void gerarMaster(Integer acao) throws ECARException {
		
		this.historicoMaster = new HistoricoMaster();
		
		this.acao = acao;
		
		historicoMaster.setDataHoraHistorico(new Date());
		historicoMaster.setUsuManutencao(((ecar.login.SegurancaECAR) request.getSession().getAttribute("seguranca")).getUsuario());
		historicoMaster.setCodReferenciaGeral(iett.getCodIett());
		
		if(alteracao == acao.intValue()) {
			historicoMaster.setHistoricoMotivo((HistoricoMotivo)session.load(HistoricoMotivo.class, Long.valueOf(alteracao)));
		} else if(exclusao == acao.intValue()) {
			historicoMaster.setHistoricoMotivo((HistoricoMotivo)session.load(HistoricoMotivo.class, Long.valueOf(exclusao)));
		}
			
		session.save(historicoMaster);		
	}	

        /**
         * 
         * @throws ECARException
         */
        public void gerarHistorico() throws ECARException {

		ConfiguracaoCfg config = cfgDao.getConfiguracao();

		if("S".equals(config.getIndGerarHistoricoCfg())) {
			
				if(historicoMaster == null) {
					this.gerarMaster(this.acao);
				}
				
				HistoricoIettH ietth = new HistoricoIettH();
								
				ietth.setAreaAre(iett.getAreaAre());
				ietth.setBeneficiosIett(iett.getBeneficiosIett());
				ietth.setDataInclusaoIett(iett.getDataInclusaoIett());
				ietth.setDataInicioIett(iett.getDataInicioIett());
				ietth.setDataInicioMonitoramentoIett(iett.getDataInicioMonitoramentoIett());
				ietth.setDataR1(iett.getDataR1());
				ietth.setDataR2(iett.getDataR2());
				ietth.setDataR3(iett.getDataR3());
				ietth.setDataR4(iett.getDataR4());
				ietth.setDataR5(iett.getDataR5());
				ietth.setDataTerminoIett(iett.getDataTerminoIett());
				ietth.setDataUltManutencaoIett(iett.getDataUltManutencaoIett());
				ietth.setDescricaoIett(iett.getDescricaoIett());
				ietth.setDescricaoR1(iett.getDescricaoR1());
				ietth.setDescricaoR2(iett.getDescricaoR2());
				ietth.setDescricaoR3(iett.getDescricaoR3());
				ietth.setDescricaoR4(iett.getDescricaoR4());
				ietth.setDescricaoR5(iett.getDescricaoR5());
				ietth.setEstruturaEtt(iett.getEstruturaEtt());
				ietth.setHistoricoMaster(historicoMaster);
				ietth.setIndAtivoIett(iett.getIndAtivoIett());
				ietth.setIndBloqPlanejamentoIett(iett.getIndBloqPlanejamentoIett());
				ietth.setIndCriticaIett(iett.getIndCriticaIett());
				ietth.setIndMonitoramentoIett(iett.getIndMonitoramentoIett());
				ietth.setItemEstruturaIett(iett); //item que sofreu alteração/exclusão
				ietth.setItemEstruturaIettPai(iett.getItemEstruturaIett());
				ietth.setNivelIett(iett.getNivelIett());
				ietth.setNomeIett(iett.getNomeIett());
				ietth.setObjetivoEspecificoIett(iett.getObjetivoEspecificoIett());
				ietth.setObjetivoGeralIett(iett.getObjetivoGeralIett());
				ietth.setOrgaoOrgByCodOrgaoResponsavel1Iett(iett.getOrgaoOrgByCodOrgaoResponsavel1Iett());
				ietth.setOrgaoOrgByCodOrgaoResponsavel2Iett(iett.getOrgaoOrgByCodOrgaoResponsavel2Iett());
				ietth.setOrigemIett(iett.getOrigemIett());
				ietth.setPeriodicidadePrdc(iett.getPeriodicidadePrdc());
				ietth.setSiglaIett(iett.getSiglaIett());
				ietth.setSituacaoSit(iett.getSituacaoSit());
				ietth.setSubAreaSare(iett.getSubAreaSare());
				ietth.setUnidadeOrcamentariaUO(iett.getUnidadeOrcamentariaUO());
				ietth.setUsuarioUsuByCodUsuIncIett(iett.getUsuarioUsuByCodUsuIncIett());
				ietth.setUsuarioUsuByCodUsuUltManutIett(iett.getUsuarioUsuByCodUsuUltManutIett());
				ietth.setValPrevistoFuturoIett(iett.getValPrevistoFuturoIett());
								
				session.save(ietth);
												
		}
		
	}
        /**
         *
         * @param iett
         * @throws ECARException
         */
        public void gerarHistorico(ItemEstruturaIett iett) throws ECARException {
		
		this.iett = iett;
		this.iett.setUsuarioUsuByCodUsuUltManutIett(((ecar.login.SegurancaECAR) this.request.getSession().getAttribute("seguranca")).getUsuario());
		this.gerarHistorico();
		
	}

}
