package ecar.pojo;

import java.io.Serializable;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

/**
 *
 * @author 70744416353
 */
public class TipoFuncAcompTpfaPermiteAlterarPK implements Serializable{

    private TipoFuncAcompTpfa cod_superior_tpfapa;
    
    private TipoFuncAcompTpfa cod_inferior_tpfapa;

    /**
     *
     * @return
     */
    public TipoFuncAcompTpfa getCod_superior_tpfapa() {
		return cod_superior_tpfapa;
	}

    /**
     *
     * @param cod_superior_tpfapa
     */
    public void setCod_superior_tpfapa(TipoFuncAcompTpfa cod_superior_tpfapa) {
		this.cod_superior_tpfapa = cod_superior_tpfapa;
	}

    /**
     *
     * @return
     */
    public TipoFuncAcompTpfa getCod_inferior_tpfapa() {
		return cod_inferior_tpfapa;
	}

        /**
         *
         * @param cod_inferior_tpfapa
         */
        public void setCod_inferior_tpfapa(TipoFuncAcompTpfa cod_inferior_tpfapa) {
		this.cod_inferior_tpfapa = cod_inferior_tpfapa;
	}
	
    @Override
    public boolean equals(Object other) {
        if ( (this == other ) ) return true;
        if ( !(other instanceof TipoFuncAcompTpfaPermiteAlterarPK) ) return false;
        TipoFuncAcompTpfaPermiteAlterarPK castOther = (TipoFuncAcompTpfaPermiteAlterarPK) other;
        return new EqualsBuilder()
            .append(this.getCod_superior_tpfapa(), castOther.getCod_superior_tpfapa())
            .append(this.getCod_inferior_tpfapa(), castOther.getCod_inferior_tpfapa())
            .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
            .append(getCod_superior_tpfapa())
            .append(getCod_inferior_tpfapa())
            .toHashCode();
    }
    
    
    /*public TipoFuncAcompTpfaPermiteAlterarPK(Long cod_superior_tpfapa, Long cod_inferior_tpfapa){
    	this.cod_superior_tpfapa = cod_superior_tpfapa;
    	this.cod_inferior_tpfapa = cod_inferior_tpfapa;
    }*/

}
