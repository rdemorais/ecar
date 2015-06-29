package ecar.pojo;

import java.io.Serializable;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/**
 *
 * @author 70744416353
 */
public class ServicoParametroSerParPK implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -8626278528160741066L;
	
	/** identifier field */
    private Long codServicoSerPar;

    /** identifier field */
    private Long codParametroSerPar;
    
    

    /** full constructor
     * @param codServicoSerPar
     * @param codParametroSerPar
     */
    public ServicoParametroSerParPK(Long codServicoSerPar, Long codParametroSerPar) {
        this.codServicoSerPar = codServicoSerPar;
        this.codParametroSerPar = codParametroSerPar;
    }

    /** default constructor */
    public ServicoParametroSerParPK() {
    }

	
    
    /**
     *
     * @return
     */
    public Long getCodServicoSerPar() {
		return codServicoSerPar;
	}

        /**
         *
         * @param codServicoSerPar
         */
        public void setCodServicoSerPar(Long codServicoSerPar) {
		this.codServicoSerPar = codServicoSerPar;
	}

        /**
         *
         * @return
         */
        public Long getCodParametroSerPar() {
		return codParametroSerPar;
	}

        /**
         *
         * @param codParametroSerPar
         */
        public void setCodParametroSerPar(Long codParametroSerPar) {
		this.codParametroSerPar = codParametroSerPar;
	}

    @Override
	public String toString() {
        return new ToStringBuilder(this)
            .append("codServicoSer", getCodServicoSerPar())
            .append("codParametroPar", getCodParametroSerPar())
            .toString();
    }

    @Override
    public boolean equals(Object other) {
        if ( (this == other ) ) return true;
        if ( !(other instanceof ServicoParametroSerParPK) ) return false;
        ServicoParametroSerParPK castOther = (ServicoParametroSerParPK) other;
        return new EqualsBuilder()
            .append(this.getCodServicoSerPar(), castOther.getCodServicoSerPar())
            .append(this.getCodParametroSerPar(), castOther.getCodParametroSerPar())
            .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
            .append(getCodServicoSerPar())
            .append(getCodParametroSerPar())
            .toHashCode();
    }
}
