package ecar.servlet.grafico.bean;

import java.util.ArrayList;
import java.util.List;

import ecar.pojo.AcompReferenciaAref;


/**
 * @author cristiano
 *
 */

public class PosicaoBean {

	private AcompReferenciaAref aRef = null;
	private List cor = new ArrayList();

        /**
         *
         */
        public PosicaoBean() {
		
	}

	/**
	 * Retorna AcompReferenciaAref aRef.<br>
	 * 
	 * @author N/C
     * @since N/C
     * @version N/C
	 * @return AcompReferenciaAref
	 */
	public AcompReferenciaAref getARef() {
		return aRef;
	}

	/**
	 * Atribui valor especificado para AcompReferenciaAref ref
	 * 
         * @param ref
         * @author N/C
     * @since N/C
     * @version N/C
	 */
	public void setARef(AcompReferenciaAref ref) {
		aRef = ref;
	}

	/**
	 * Retorna List cor
	 * 
	 * @author N/C
     * @since N/C
     * @version N/C
	 * @return List
	 */
	public List getCor() {
		return cor;
	}

	/**
	 * Atribui valor especificado para List cor.<br>
	 * 
         * @param cor
         * @author N/C
     * @since N/C
     * @version N/C
	 */
	public void setCor(List cor) {
		this.cor = cor;
	}
}
