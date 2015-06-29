package ecar.historico;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.hibernate.Session;

import ecar.dao.ConfiguracaoDao;
import ecar.exception.ECARException;
import ecar.pojo.ConfiguracaoCfg;
import ecar.pojo.HistoricoIettusH;
import ecar.pojo.HistoricoMaster;
import ecar.pojo.HistoricoMotivo;
import ecar.pojo.ItemEstrutUsuarioIettus;

/**
 *
 * @author 70744416353
 */
public class HistoricoIettus implements IHistoricoAntigo {
	
	//Constantes
    /**
     *
     */
    public static final int alterarPermissoes = 27;
        /**
         *
         */
        public static final int excluirPermissoes = 28;
		
	private HistoricoMaster historicoMaster = null;
	private HttpServletRequest request = null;
	ItemEstrutUsuarioIettus iettus = null;
	Integer acao = null;
	Session session = null;
	ConfiguracaoDao cfgDao = null;
	
        /**
         *
         * @param iettus
         * @param acao
         * @param session
         * @param cfgDao
         * @param request
         */
        public HistoricoIettus(ItemEstrutUsuarioIettus iettus, int acao, Session session, ConfiguracaoDao cfgDao, HttpServletRequest request) {
		
		this.iettus = iettus;
		this.acao = acao;
		this.session = session;
		this.cfgDao = cfgDao;
		this.request = request;
		
		this.iettus.setUsuManutencao(((ecar.login.SegurancaECAR) request.getSession().getAttribute("seguranca")).getUsuario());
	}
	
        /**
         *
         * @param acao
         * @throws ECARException
         */
        public void gerarMaster(Integer acao) throws ECARException {
		
		historicoMaster = new HistoricoMaster();
		this.acao = acao;
		historicoMaster.setDataHoraHistorico(new Date());
		historicoMaster.setUsuManutencao(((ecar.login.SegurancaECAR) request.getSession().getAttribute("seguranca")).getUsuario());
		
		if(iettus != null && iettus.getCodIettus()!= null) {
			historicoMaster.setCodReferenciaGeral(iettus.getItemEstruturaIett().getCodIett());
		} else {
			//não existe o código
			historicoMaster.setCodReferenciaGeral(new Long(0));
		}
		
		if(alterarPermissoes == acao.intValue()) {
			historicoMaster.setHistoricoMotivo((HistoricoMotivo)session.load(HistoricoMotivo.class, Long.valueOf(alterarPermissoes)));
		} else if(excluirPermissoes == acao.intValue()) {
			historicoMaster.setHistoricoMotivo((HistoricoMotivo)session.load(HistoricoMotivo.class, Long.valueOf(excluirPermissoes)));
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
				
				HistoricoIettusH iettush = new HistoricoIettusH();
								
				iettush.setCod_atb((iettus.getSisAtributoSatb() != null?iettus.getSisAtributoSatb().getCodSatb():null));
				iettush.setCodTpPermIettus(iettus.getCodTpPermIettus());
				iettush.setDataInclusaoIettus(iettus.getDataInclusaoIettus());
				iettush.setIndAtivMonitIettus(iettus.getIndAtivMonitIettus());
				iettush.setIndBloqPlanIettus(iettus.getIndBloqPlanIettus());
				iettush.setIndDesatMonitIettus(iettus.getIndDesatMonitIettus());
				iettush.setIndDesblPlanIettus(iettus.getIndDesblPlanIettus());
				iettush.setIndEdicaoIettus(iettus.getIndEdicaoIettus());
				iettush.setIndEmitePosIettus(iettus.getIndEmitePosIettus());
				iettush.setIndExcluirIettus(iettus.getIndExcluirIettus());
				iettush.setIndInfAndamentoIettus(iettus.getIndInfAndamentoIettus());
				iettush.setIndLeituraIettus(iettus.getIndLeituraIettus());
				iettush.setIndProxNivelIettus(iettus.getIndProxNivelIettus());
				iettush.setItemEstruturaIett(iettus.getItemEstruturaIett());
				iettush.setItemEstruturaIettOrigem(iettus.getItemEstruturaIettOrigem());
				iettush.setTipoFuncAcompTpfa(iettus.getTipoFuncAcompTpfa());
				iettush.setUsuarioUsu(iettus.getUsuarioUsu());
				iettush.setUsuManutencao(iettus.getUsuManutencao());	
				iettush.setHistoricoMaster(historicoMaster);
				iettush.setIndLeituraParecerIettus(iettus.getIndLeituraParecerIettus());
				
				session.save(iettush);
		}		
		
	}
	
        /**
         *
         * @param iettus
         * @throws ECARException
         */
        public void gerarHistorico(ItemEstrutUsuarioIettus iettus) throws ECARException {
		
		this.iettus = iettus;
		this.gerarHistorico();
	}	

}
