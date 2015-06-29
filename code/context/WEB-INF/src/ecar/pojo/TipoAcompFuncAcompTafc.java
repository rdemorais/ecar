package ecar.pojo;

import java.io.Serializable;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;


/** @author Hibernate CodeGenerator */
public class TipoAcompFuncAcompTafc implements Serializable, PaiFilho {

	private static final long serialVersionUID = -2084647941512206682L;

	/** identifier field */
    private TipoAcompFuncAcompPK comp_id;
    
    private String indObrigatorio;

    private String indOpcional;

    /** nullable persistent field */
    private TipoFuncAcompTpfa tipoFuncAcompTpfa;

    /** nullable persistent field */
    private TipoAcompanhamentoTa tipoAcompanhamentoTa;
    
    private String indRegistroPosicaoTafc;
    
    private String indLeituraTipoAcomp;

    /** full constructor
     * @param comp_id
     * @param tipoAcompanhamentoTa
     * @param tipoFuncAcompTpfa
     * @param indObrigatorio
     * @param indOpcional
     * @param indRegistroPosicaoTacf
     * @param indLeituraTipoAcomp
     */
    public TipoAcompFuncAcompTafc(TipoAcompFuncAcompPK comp_id, 
    								TipoAcompanhamentoTa tipoAcompanhamentoTa, 
    								TipoFuncAcompTpfa tipoFuncAcompTpfa, 
    								String indOpcional, 
    								String indObrigatorio, 
    								String indRegistroPosicaoTacf,
    								String indLeituraTipoAcomp) {
        this.comp_id = comp_id;
        this.tipoFuncAcompTpfa = tipoFuncAcompTpfa;
        this.tipoAcompanhamentoTa = tipoAcompanhamentoTa;
        this.indOpcional = indOpcional;
        this.indObrigatorio = indObrigatorio;
        this.indRegistroPosicaoTafc = indRegistroPosicaoTacf;
        this.indLeituraTipoAcomp = indLeituraTipoAcomp;
    }

    /** default constructor */
    public TipoAcompFuncAcompTafc() {
    }

    /** minimal constructor
     * @param codTa
     * @param codTpfa
     */
    public TipoAcompFuncAcompTafc(Long codTa,Long codTpfa) {
        this.comp_id = new TipoAcompFuncAcompPK(codTa,codTpfa);
    }

    /**
     *
     * @return
     */
    public TipoAcompFuncAcompPK getComp_id() {
        return this.comp_id;
    }

    /**
     *
     * @param comp_id
     */
    public void setComp_id(TipoAcompFuncAcompPK comp_id) {
        this.comp_id = comp_id;
    }


    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .append("comp_id", getComp_id())
            .toString();
    }

    @Override
    public boolean equals(Object other) {
        if ( (this == other ) ) return true;
        if ( !(other instanceof TipoAcompFuncAcompTafc) ) return false;
        TipoAcompFuncAcompTafc castOther = (TipoAcompFuncAcompTafc) other;
        return new EqualsBuilder()
            .append(this.getTipoAcompanhamentoTa(), castOther.getTipoAcompanhamentoTa())
            .append(this.getTipoFuncAcompTpfa(), castOther.getTipoFuncAcompTpfa())
            .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
            .append(getComp_id())
            .toHashCode();
    }
    
    public void atribuirPKPai() {
        comp_id = new TipoAcompFuncAcompPK(this.getTipoAcompanhamentoTa().getCodTa(),this.getTipoFuncAcompTpfa().getCodTpfa());        
    }

    /**
     *
     * @return
     */
    public TipoAcompanhamentoTa getTipoAcompanhamentoTa() {
		return tipoAcompanhamentoTa;
	}

        /**
         *
         * @param tipoAcompanhamentoTa
         */
        public void setTipoAcompanhamentoTa(TipoAcompanhamentoTa tipoAcompanhamentoTa) {
		this.tipoAcompanhamentoTa = tipoAcompanhamentoTa;
	}

        /**
         *
         * @return
         */
        public TipoFuncAcompTpfa getTipoFuncAcompTpfa() {
		return tipoFuncAcompTpfa;
	}

        /**
         *
         * @param tipoFuncAcompTpfa
         */
        public void setTipoFuncAcompTpfa(TipoFuncAcompTpfa tipoFuncAcompTpfa) {
		this.tipoFuncAcompTpfa = tipoFuncAcompTpfa;
	}

        /**
         *
         * @return
         */
        public String getIndObrigatorio() {
		return indObrigatorio;
	}

        /**
         *
         * @param indObrigatorio
         */
        public void setIndObrigatorio(String indObrigatorio) {
		this.indObrigatorio = indObrigatorio;
	}

        /**
         *
         * @return
         */
        public String getIndOpcional() {
		return indOpcional;
	}

        /**
         *
         * @param indOpcional
         */
        public void setIndOpcional(String indOpcional) {
		this.indOpcional = indOpcional;
	}

        /**
         *
         * @return
         */
        public String getIndRegistroPosicaoTafc() {
		return indRegistroPosicaoTafc;
	}

        /**
         *
         * @param indRegistroPosicaoTafc
         */
        public void setIndRegistroPosicaoTafc(String indRegistroPosicaoTafc) {
		this.indRegistroPosicaoTafc = indRegistroPosicaoTafc;
	}
	
        /**
         *
         * @return
         */
        public String getIndLeituraTipoAcomp() {
		return indLeituraTipoAcomp;
	}

    /**
     *
     * @param indLeituraTipoAcomp
     */
    public void setIndLeituraTipoAcomp(String indLeituraTipoAcomp) {
		this.indLeituraTipoAcomp = indLeituraTipoAcomp;
	}
}
