package ecar.pojo;

import java.io.Serializable;

/**
 *
 * @author 70744416353
 */
public class ApontamentoAnexo implements Serializable {

	private static final long serialVersionUID = -2481032929186535170L;

    private Long cod;

    private String srcAnexo;

    private RegApontamentoRegda regApontamentoRegda;

    /**
     *
     */
    public ApontamentoAnexo() {
    	super();
    }
    
    /**
     *
     * @return
     */
    public Long getCod() {
		return cod;
	}

    /**
     *
     * @param cod
     */
    public void setCod(Long cod) {
		this.cod = cod;
	}

        /**
         *
         * @return
         */
        public String getSrcAnexo() {
		return srcAnexo;
	}

        /**
         *
         * @param srcAnexo
         */
        public void setSrcAnexo(String srcAnexo) {
		this.srcAnexo = srcAnexo;
	}

        /**
         *
         * @return
         */
        public RegApontamentoRegda getRegApontamentoRegda() {
		return regApontamentoRegda;
	}

        /**
         *
         * @param regApontamentoRegda
         */
        public void setRegApontamentoRegda(RegApontamentoRegda regApontamentoRegda) {
		this.regApontamentoRegda = regApontamentoRegda;
	}
	
}
