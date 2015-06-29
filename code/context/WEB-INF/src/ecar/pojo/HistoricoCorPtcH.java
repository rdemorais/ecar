package ecar.pojo;

import java.io.Serializable;

/** 
 * Classe responsavel para armazenar dados da tabela tb_historico_cor_ptch
 * 
 * @author 
 * @since 0.1, 28/07/2008
 */

public class HistoricoCorPtcH  implements Serializable {
	
	
	 private Long codCorPtcH; 
     private Integer frequenciaEnvioEmailPtccor;
     private Integer antecedenciaPrimEmailPtccor;
     private String indAtivoEnvioEmailPtccor;
     private PontoCriticoPtc pontoCriticoPtc;
     private Cor cor;
     private HistoricoPtcH historicoPtcH;
    
     
     // Constructors
    /** default constructor */
    public HistoricoCorPtcH() {
    }
    
    /**
     *
     * @return
     */
    public Long getCodCorPtcH() {
		return codCorPtcH;
	}
    
    /**
     *
     * @param codCorPtcH
     */
    public void setCodCorPtcH(Long codCorPtcH) {
    	this.codCorPtcH = codCorPtcH;
	}

    /**
     *
     * @return
     */
    public Integer getFrequenciaEnvioEmailPtccor() {
        return this.frequenciaEnvioEmailPtccor;
    }
    
    /**
     *
     * @param frequenciaEnvioEmailPtccor
     */
    public void setFrequenciaEnvioEmailPtccor(Integer frequenciaEnvioEmailPtccor) {
        this.frequenciaEnvioEmailPtccor = frequenciaEnvioEmailPtccor;
    }
    
    /**
     *
     * @return
     */
    public Integer getAntecedenciaPrimEmailPtccor() {
        return this.antecedenciaPrimEmailPtccor;
    }
    
    /**
     *
     * @param antecedenciaPrimEmailPtccor
     */
    public void setAntecedenciaPrimEmailPtccor(Integer antecedenciaPrimEmailPtccor) {
        this.antecedenciaPrimEmailPtccor = antecedenciaPrimEmailPtccor;
    }
    
    /**
     *
     * @return
     */
    public String getIndAtivoEnvioEmailPtccor() {
        return this.indAtivoEnvioEmailPtccor;
    }
    
    /**
     *
     * @param indAtivoEnvioEmailPtccor
     */
    public void setIndAtivoEnvioEmailPtccor(String indAtivoEnvioEmailPtccor) {
        this.indAtivoEnvioEmailPtccor = indAtivoEnvioEmailPtccor;
    }
    
    /**
     *
     * @return
     */
    public PontoCriticoPtc getPontoCriticoPtc() {
        return this.pontoCriticoPtc;
    }
    
    /**
     *
     * @param pontoCriticoPtc
     */
    public void setPontoCriticoPtc(PontoCriticoPtc pontoCriticoPtc) {
        this.pontoCriticoPtc = pontoCriticoPtc;
    }
    /**
     *
     * @return
     */
    public Cor getCor() {
        return this.cor;
    }
    /**
     *
     * @param cor
     */
    public void setCor(Cor cor) {
        this.cor = cor;
    }
    
    /**
     *
     * @return
     */
    public HistoricoPtcH getHistoricoPtcH() {
		return historicoPtcH;
	}
    /**
     *
     * @param historicoPtcH
     */
    public void setHistoricoPtcH(HistoricoPtcH historicoPtcH) {
		this.historicoPtcH = historicoPtcH;
	}
	
	
}


