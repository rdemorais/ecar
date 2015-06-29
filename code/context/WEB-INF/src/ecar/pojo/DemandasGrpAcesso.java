package ecar.pojo;

import java.io.Serializable;

/**
 *
 * @author 70744416353
 */
public class DemandasGrpAcesso implements Serializable {
	
	private static final long serialVersionUID = -5549149190114022412L;
	
	private Long codSatb;
    private String acessoDemanda;

    /**
     *
     */
    public DemandasGrpAcesso() {
    }

    /**
     *
     * @return
     */
    public Long getCodSatb() {
		return codSatb;
	}

        /**
         *
         * @param codSatb
         */
        public void setCodSatb(Long codSatb) {
		this.codSatb = codSatb;
	}

        /**
         *
         * @return
         */
        public String getAcessoDemanda() {
		return acessoDemanda;
	}

        /**
         *
         * @param acessoDemanda
         */
        public void setAcessoDemanda(String acessoDemanda) {
		this.acessoDemanda = acessoDemanda;
	}
    
}
