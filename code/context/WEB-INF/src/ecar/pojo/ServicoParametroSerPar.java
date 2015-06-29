package ecar.pojo;

import java.io.Serializable;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

/**
 *
 * @author 70744416353
 */
public class ServicoParametroSerPar implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 2995187718800226720L;

	/** identifier field */
    private ecar.pojo.ServicoParametroSerParPK comp_id;
    
    /** not nullable persistent field */
    private ecar.pojo.ServicoSer servicoSer;

    /** not nullable persistent field */
    private ecar.pojo.ParametroPar parametroPar;
    
    /** not nullable persistent field */
    private Long sequencia;

    /** default constructor */
    public ServicoParametroSerPar(){
    	
    }
    
    /** full constructor
     * @param comp_id
     * @param parametroPar
     * @param servicoSer
     * @param sequencia
     */
    public ServicoParametroSerPar(ecar.pojo.ServicoParametroSerParPK comp_id, ecar.pojo.ServicoSer servicoSer, ecar.pojo.ParametroPar parametroPar, Long sequencia) {
        this.comp_id = comp_id;
        this.servicoSer = servicoSer;
        this.parametroPar = parametroPar;
        this.sequencia = sequencia;
    }

    /**
     *
     * @return
     */
    public ecar.pojo.ServicoParametroSerParPK getComp_id() {
		return comp_id;
	}

        /**
         *
         * @param comp_id
         */
        public void setComp_id(ecar.pojo.ServicoParametroSerParPK comp_id) {
		this.comp_id = comp_id;
	}

        /**
         *
         * @return
         */
        public ecar.pojo.ServicoSer getServicoSer() {
		return servicoSer;
	}

        /**
         *
         * @param servicoSer
         */
        public void setServicoSer(ecar.pojo.ServicoSer servicoSer) {
		this.servicoSer = servicoSer;
	}

        /**
         *
         * @return
         */
        public ecar.pojo.ParametroPar getParametroPar() {
		return parametroPar;
	}

        /**
         *
         * @param parametroPar
         */
        public void setParametroPar(ecar.pojo.ParametroPar parametroPar) {
		this.parametroPar = parametroPar;
	}

        /**
         *
         * @return
         */
        public Long getSequencia() {
		return sequencia;
	}

        /**
         *
         * @param sequencia
         */
        public void setSequencia(Long sequencia) {
		this.sequencia = sequencia;
	}
	
    @Override
	public boolean equals(Object other) {
        if ( (this == other ) ) return true;
        if ( !(other instanceof ServicoParametroSerPar) ) return false;
        ServicoParametroSerPar castOther = (ServicoParametroSerPar) other;
        return new EqualsBuilder()
            .append(this.getServicoSer(), castOther.getServicoSer())
            .append(this.getParametroPar(), castOther.getParametroPar())
            .append(this.getSequencia(), castOther.getSequencia())
            .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
            .append(getComp_id())
            .toHashCode();
    }
    
    /**
     *
     */
    public void atribuirPKPai() {
        comp_id = new ServicoParametroSerParPK();        
        comp_id.setCodServicoSerPar(this.getServicoSer().getCodServicoSer());
        comp_id.setCodParametroSerPar(this.getParametroPar().getCodParametroPar());
    }
}
