package ecar.pojo;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

/**
 * @author rogerio
 * @since 0.1, 02/06/2007
 * @version 0.1, 02/06/2007
 */
public class HistoricoMaster implements Serializable {

	private static final long serialVersionUID = -4294871309213547771L;

    private Long codMah;
    private UsuarioUsu usuManutencao;
    private Date dataHoraHistorico;
    private HistoricoMotivo historicoMotivo;
    private Long codReferenciaGeral;
    
    private Set historicoIettHs;
    private Set historicoIettaHs;
    private Set historicoIettbHs;
    private Set historicoIettcHs;
    private Set historicoIetteHs;
    private Set historicoIettfHs;
    private Set historicoIettlHs;
    private Set historicoIettrHs;
    private Set historicoIettupHs;
    private Set historicoIettusHs;
    private Set historicoIettutfaHs;
    private Set historicoEfiecHs;
    private Set historicoEfieftHs;
    private Set historicoEfiepHs;
    private Set historicoEfierHs;
    private Set historicoIettSatbHs;
    //private Set historicoPtcHs;
    
    /**
     *
     * @return
     */
    public Set getHistoricoEfierHs() {
		return historicoEfierHs;
	}

        /**
         *
         * @param historicoEfierHs
         */
        public void setHistoricoEfierHs(Set historicoEfierHs) {
		this.historicoEfierHs = historicoEfierHs;
	}

        /**
         *
         * @return
         */
        public Set getHistoricoEfiepHs() {
		return historicoEfiepHs;
	}

        /**
         *
         * @param historicoEfiepHs
         */
        public void setHistoricoEfiepHs(Set historicoEfiepHs) {
		this.historicoEfiepHs = historicoEfiepHs;
	}

        /**
         *
         * @return
         */
        public Set getHistoricoEfieftHs() {
		return historicoEfieftHs;
	}

        /**
         *
         * @param historicoEfieftHs
         */
        public void setHistoricoEfieftHs(Set historicoEfieftHs) {
		this.historicoEfieftHs = historicoEfieftHs;
	}

        /**
         *
         * @return
         */
        public Set getHistoricoEfiecHs() {
		return historicoEfiecHs;
	}

        /**
         *
         * @param historicoEfiecHs
         */
        public void setHistoricoEfiecHs(Set historicoEfiecHs) {
		this.historicoEfiecHs = historicoEfiecHs;
	}

        /**
         *
         * @return
         */
        public Set getHistoricoIettutfaHs() {
		return historicoIettutfaHs;
	}

        /**
         *
         * @param historicoIettutfaHs
         */
        public void setHistoricoIettutfaHs(Set historicoIettutfaHs) {
		this.historicoIettutfaHs = historicoIettutfaHs;
	}

        /**
         *
         * @return
         */
        public Set getHistoricoIettrHs() {
		return historicoIettrHs;
	}

        /**
         *
         * @param historicoIettrHs
         */
        public void setHistoricoIettrHs(Set historicoIettrHs) {
		this.historicoIettrHs = historicoIettrHs;
	}

        /**
         *
         * @return
         */
        public Set getHistoricoIettlHs() {
		return historicoIettlHs;
	}

        /**
         *
         * @param historicoIettlHs
         */
        public void setHistoricoIettlHs(Set historicoIettlHs) {
		this.historicoIettlHs = historicoIettlHs;
	}

        /**
         *
         * @return
         */
        public Set getHistoricoIettfHs() {
		return historicoIettfHs;
	}

        /**
         *
         * @param historicoIettfHs
         */
        public void setHistoricoIettfHs(Set historicoIettfHs) {
		this.historicoIettfHs = historicoIettfHs;
	}

        /**
         *
         * @return
         */
        public Set getHistoricoIetteHs() {
		return historicoIetteHs;
	}

        /**
         *
         * @param historicoIetteHs
         */
        public void setHistoricoIetteHs(Set historicoIetteHs) {
		this.historicoIetteHs = historicoIetteHs;
	}

        /**
         *
         * @return
         */
        public Set getHistoricoIettcHs() {
		return historicoIettcHs;
	}

        /**
         *
         * @param historicoIettcHs
         */
        public void setHistoricoIettcHs(Set historicoIettcHs) {
		this.historicoIettcHs = historicoIettcHs;
	}

        /**
         *
         * @return
         */
        public Set getHistoricoIettaHs() {
		return historicoIettaHs;
	}

        /**
         *
         * @param historicoIettaHs
         */
        public void setHistoricoIettaHs(Set historicoIettaHs) {
		this.historicoIettaHs = historicoIettaHs;
	}

        /**
         *
         */
        public HistoricoMaster() { }
    
        /**
         *
         * @return
         */
        public Long getCodMah() {
		return codMah;
	}
        /**
         *
         * @param codMah
         */
        public void setCodMah(Long codMah) {
		this.codMah = codMah;
	}
        /**
         *
         * @return
         */
        public Long getCodReferenciaGeral() {
		return codReferenciaGeral;
	}
        /**
         *
         * @param codReferenciaGeral
         */
        public void setCodReferenciaGeral(Long codReferenciaGeral) {
		this.codReferenciaGeral = codReferenciaGeral;
	}
        /**
         *
         * @return
         */
        public Date getDataHoraHistorico() {
		return dataHoraHistorico;
	}
        /**
         *
         * @param dataHoraHistorico
         */
        public void setDataHoraHistorico(Date dataHoraHistorico) {
		this.dataHoraHistorico = dataHoraHistorico;
	}
        /**
         *
         * @return
         */
        public Set getHistoricoIettHs() {
		return historicoIettHs;
	}
        /**
         *
         * @param historicoIettHs
         */
        public void setHistoricoIettHs(Set historicoIettHs) {
		this.historicoIettHs = historicoIettHs;
	}
        /**
         *
         * @return
         */
        public HistoricoMotivo getHistoricoMotivo() {
		return historicoMotivo;
	}
        /**
         *
         * @param historicoMotivo
         */
        public void setHistoricoMotivo(HistoricoMotivo historicoMotivo) {
		this.historicoMotivo = historicoMotivo;
	}
        /**
         *
         * @return
         */
        public UsuarioUsu getUsuManutencao() {
		return usuManutencao;
	}
        /**
         *
         * @param usuManutencao
         */
        public void setUsuManutencao(UsuarioUsu usuManutencao) {
		this.usuManutencao = usuManutencao;
	}

        /**
         *
         * @return
         */
        public Set getHistoricoIettbHs() {
		return historicoIettbHs;
	}

        /**
         *
         * @param historicoIettbHs
         */
        public void setHistoricoIettbHs(Set historicoIettbHs) {
		this.historicoIettbHs = historicoIettbHs;
	}

        /**
         *
         * @return
         */
        public Set getHistoricoIettupHs() {
		return historicoIettupHs;
	}

        /**
         *
         * @param historicoIettupHs
         */
        public void setHistoricoIettupHs(Set historicoIettupHs) {
		this.historicoIettupHs = historicoIettupHs;
	}

        /**
         *
         * @return
         */
        public Set getHistoricoIettusHs() {
		return historicoIettusHs;
	}

        /**
         *
         * @param historicoIettusHs
         */
        public void setHistoricoIettusHs(Set historicoIettusHs) {
		this.historicoIettusHs = historicoIettusHs;
	}

        /**
         *
         * @return
         */
        public Set getHistoricoIettSatbHs() {
		return historicoIettSatbHs;
	}

        /**
         *
         * @param historicoIettSatbHs
         */
        public void setHistoricoIettSatbHs(Set historicoIettSatbHs) {
		this.historicoIettSatbHs = historicoIettSatbHs;
	}

	/*public Set getHistoricoPtcHs() {
		return historicoPtcHs;
	}

	public void setHistoricoPtcHs(Set historicoPtcHs) {
		this.historicoPtcHs = historicoPtcHs;
	}*/    
    
}
