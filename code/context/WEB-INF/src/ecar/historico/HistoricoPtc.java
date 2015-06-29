package ecar.historico;

import java.util.Date;
import java.util.Iterator;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.hibernate.Session;

import ecar.dao.ConfiguracaoDao;
import ecar.exception.ECARException;
import ecar.pojo.ConfiguracaoCfg;
import ecar.pojo.HistoricoCorPtcH;
import ecar.pojo.HistoricoMaster;
import ecar.pojo.HistoricoMotivo;
import ecar.pojo.HistoricoPtcH;
import ecar.pojo.PontoCriticoPtc;
import ecar.pojo.PontocriticoCorPtccor;

/**
 *
 * @author 70744416353
 */
public class HistoricoPtc implements IHistoricoAntigo{

	//Constantes
    /**
     *
     */
    public static final int alteracao = 29;
    /**
     *
     */
    public static final int exclusao = 30;
	
	private HistoricoMaster historicoMaster = null;
	private HistoricoCorPtcH historicoCorPtcH = null;
	private PontoCriticoPtc ptc = null;
	private Integer acao = null;
	private Session session = null;
	private HttpServletRequest request;
	private ConfiguracaoDao cfgDao = null;
	
        /**
         *
         * @param ptc
         * @param acao
         * @param session
         * @param cfgDao
         * @param request
         */
        public HistoricoPtc(PontoCriticoPtc ptc,
			int acao,
			Session session, 
			ConfiguracaoDao cfgDao, 
			HttpServletRequest request){

		this.ptc = ptc;
		this.acao = acao;
		this.session = session;
		this.cfgDao = cfgDao;
		this.request = request;
		
		this.ptc.setUsuarioUsuInclusao(((ecar.login.SegurancaECAR) request.getSession().getAttribute("seguranca")).getUsuario());
	
	}
	
        /**
         *
         * @param ptc
         * @throws ECARException
         */
        public void gerarHistorico(PontoCriticoPtc ptc) throws ECARException {
		
		this.ptc = ptc;
//		this.ptc.setUsuarioUsuInclusao(((ecar.login.SegurancaECAR) this.request.getSession().getAttribute("seguranca")).getUsuario());
		this.gerarHistorico();
		
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
				
				HistoricoPtcH ptch = new HistoricoPtcH();
								
				ptch.setPontoCriticoPtc(ptc);
				ptch.setHistoricoMaster(historicoMaster);
				ptch.setIndAtivoPtc(ptc.getIndAtivoPtc());
				ptch.setDataInclusaoPtc(ptc.getDataInclusaoPtc());
				ptch.setDataSolucaoPtc(ptc.getDataSolucaoPtc());
				ptch.setDescricaoSolucaoPtc(ptc.getDescricaoSolucaoPtc());
				ptch.setIndAmbitoInternoGovPtc(ptc.getIndAmbitoInternoGovPtc());
				ptch.setDataLimitePtc(ptc.getDataLimitePtc());
				ptch.setDataIdentificacaoPtc(ptc.getDataIdentificacaoPtc());
				ptch.setDescricaoPtc(ptc.getDescricaoPtc());
				ptch.setItemEstruturaIett(ptc.getItemEstruturaIett());
				ptch.setUsuarioUsu(ptc.getUsuarioUsu());
				ptch.setUsuarioUsuInclusao(ptc.getUsuarioUsuInclusao());
				ptch.setUsuarioUsuByCodUsuUltManutPtc(ptc.getUsuarioUsuByCodUsuUltManutPtc());
				ptch.setDataUltManutencaoPtc(ptc.getDataUltManutencaoPtc());
				ptch.setAcompRelatorioArel(ptc.getAcompRelatorioArel());
				ptch.setSisAtributoTipo(ptc.getSisAtributoTipo());
				ptch.setIndExcluidoPtc(ptc.getIndExcluidoPtc());
								
				session.save(ptch);
									
				this.setHistoricoCorPtch(ptc.getPontoCriticoCorPtccores(),ptch);
		}
		
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
		historicoMaster.setCodReferenciaGeral(ptc.getCodPtc());
		
		if(alteracao == acao.intValue()) {
			historicoMaster.setHistoricoMotivo((HistoricoMotivo)session.load(HistoricoMotivo.class, Long.valueOf(alteracao)));
		} else if(exclusao == acao.intValue()) {
			historicoMaster.setHistoricoMotivo((HistoricoMotivo)session.load(HistoricoMotivo.class, Long.valueOf(exclusao)));
		}
			
		session.save(historicoMaster);
	}
	
	
	 /**
	 * Cria o  historico de cores
	 * 
          * @param pontoCriticoCorPtccores
          * @param historicoPtcH
          * @throws ECARException
	 */
	public void setHistoricoCorPtch(Set pontoCriticoCorPtccores, HistoricoPtcH historicoPtcH) throws ECARException {
		
		
		
		//percorre a tabela de cores
		if(pontoCriticoCorPtccores != null) {
			Iterator it = pontoCriticoCorPtccores.iterator();
			while(it.hasNext()) {
				PontocriticoCorPtccor pontocriticoCorPtccor = (PontocriticoCorPtccor) it.next(); 
				
				this.historicoCorPtcH = new HistoricoCorPtcH();
				
				//seta os dados para criar o historico
				if(pontocriticoCorPtccor.getCor().getIndPontoCriticoCor().equals("S")) {
					historicoCorPtcH.setHistoricoPtcH(historicoPtcH);
					historicoCorPtcH.setPontoCriticoPtc(pontocriticoCorPtccor.getPontoCriticoPtc());
					historicoCorPtcH.setCor(pontocriticoCorPtccor.getCor());
					historicoCorPtcH.setAntecedenciaPrimEmailPtccor(pontocriticoCorPtccor.getAntecedenciaPrimEmailPtccor());
					historicoCorPtcH.setFrequenciaEnvioEmailPtccor(pontocriticoCorPtccor.getFrequenciaEnvioEmailPtccor());
					historicoCorPtcH.setIndAtivoEnvioEmailPtccor(pontocriticoCorPtccor.getIndAtivoEnvioEmailPtccor());
					
					session.save(historicoCorPtcH);
				}
			}
		}		
	}
	
}
