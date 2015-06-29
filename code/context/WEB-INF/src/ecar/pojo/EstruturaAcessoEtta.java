package ecar.pojo;

import java.io.Serializable;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;


/** @author Hibernate CodeGenerator */


public class EstruturaAcessoEtta implements Serializable {
	
    /**
     *
     */
    public final static String COM_ACESSO_INCLUSAO = "S";
        /**
         *
         */
        public final static String SEM_ACESSO_INCLUSAO = "N";

    /**
	 * 
	 */
	private static final long serialVersionUID = 6119575630669289499L;

	/** identifier field */
    private EstruturaAcessoEttaPK comp_id;

    /** nullable persistent field */
    private String indIncItemEtta;
    private String indIncAcompMonitoradoEtta;
    private String indIncAcompSecoutrEtta;
    private String indIncAcompSecpropEtta;
    private String indLeiAcompSecoutrEtta;
    private String indLeiAcompSecpropEtta;
    
    private String indLeiAcompMoniProprEtta;
    private String indLeiAcompMoniSecOutEtta;
    private String indLeiAcompMoniSecProEtta;
    private String indLeiAcompSuaResponsEtta;
	private String indExibirHistoricoEtta;
	private String indExibirGerarArquivosEtta;
	private String indExibirImprimirEtta;

    /** nullable persistent field */
    private EstruturaEtt estruturaEtt;

    /** nullable persistent field */
    private SisAtributoSatb sisAtributoSatb;

    /** default constructor */
    public EstruturaAcessoEtta() {
    }

    /** minimal constructor
     * @param atr
     * @param ett
     */
//    public EstruturaAcessoEtta(ecar.pojo.EstruturaAcessoEttaPK comp_id) {
//        this.comp_id = comp_id;
//    }

    public EstruturaAcessoEtta(SisAtributoSatb atr, EstruturaEtt ett) {
        this.comp_id = new EstruturaAcessoEttaPK(ett.getCodEtt(),atr.getCodSatb());
    }

    /**
     *
     * @return
     */
    public EstruturaAcessoEttaPK getComp_id() {
        return this.comp_id;
    }

    /**
     *
     * @param comp_id
     */
    public void setComp_id(EstruturaAcessoEttaPK comp_id) {
        this.comp_id = comp_id;
    }


    /**
     * @return Returns the indIncAcompMonitoradoEtta.
     */
    public String getIndIncAcompMonitoradoEtta() {
        return indIncAcompMonitoradoEtta;
    }
    /**
     * @param indIncAcompMonitoradoEtta The indIncAcompMonitoradoEtta to set.
     */
    public void setIndIncAcompMonitoradoEtta(String indIncAcompMonitoradoEtta) {
        this.indIncAcompMonitoradoEtta = indIncAcompMonitoradoEtta;
    }
    /**
     * @return Returns the indIncAcompSecoutrEtta.
     */
    public String getIndIncAcompSecoutrEtta() {
        return indIncAcompSecoutrEtta;
    }
    /**
     * @param indIncAcompSecoutrEtta The indIncAcompSecoutrEtta to set.
     */
    public void setIndIncAcompSecoutrEtta(String indIncAcompSecoutrEtta) {
        this.indIncAcompSecoutrEtta = indIncAcompSecoutrEtta;
    }
    /**
     * @return Returns the indIncAcompSecpropEtta.
     */
    public String getIndIncAcompSecpropEtta() {
        return indIncAcompSecpropEtta;
    }
    /**
     * @param indIncAcompSecpropEtta The indIncAcompSecpropEtta to set.
     */
    public void setIndIncAcompSecpropEtta(String indIncAcompSecpropEtta) {
        this.indIncAcompSecpropEtta = indIncAcompSecpropEtta;
    }
    /**
     * @return Returns the indIncItemEtta.
     */
    public String getIndIncItemEtta() {
        return indIncItemEtta;
    }
    /**
     * @param indIncItemEtta The indIncItemEtta to set.
     */
    public void setIndIncItemEtta(String indIncItemEtta) {
        this.indIncItemEtta = indIncItemEtta;
    }
    /**
     * @return Returns the indLeiAcompSecoutrEtta.
     */
    public String getIndLeiAcompSecoutrEtta() {
        return indLeiAcompSecoutrEtta;
    }
    /**
     * @param indLeiAcompSecoutrEtta The indLeiAcompSecoutrEtta to set.
     */
    public void setIndLeiAcompSecoutrEtta(String indLeiAcompSecoutrEtta) {
        this.indLeiAcompSecoutrEtta = indLeiAcompSecoutrEtta;
    }
    /**
     * @return Returns the indLeiAcompSecpropEtta.
     */
    public String getIndLeiAcompSecpropEtta() {
        return indLeiAcompSecpropEtta;
    }
    /**
     * @param indLeiAcompSecpropEtta The indLeiAcompSecpropEtta to set.
     */
    public void setIndLeiAcompSecpropEtta(String indLeiAcompSecpropEtta) {
        this.indLeiAcompSecpropEtta = indLeiAcompSecpropEtta;
    }
    
    /**
     *
     * @return
     */
    public ecar.pojo.EstruturaEtt getEstruturaEtt() {
        return this.estruturaEtt;
    }

    /**
     *
     * @param estruturaEtt
     */
    public void setEstruturaEtt(ecar.pojo.EstruturaEtt estruturaEtt) {
        this.estruturaEtt = estruturaEtt;
    }

    /**
     *
     * @return
     */
    public ecar.pojo.SisAtributoSatb getSisAtributoSatb() {
        return this.sisAtributoSatb;
    }

    /**
     *
     * @param sisAtributoSatb
     */
    public void setSisAtributoSatb(ecar.pojo.SisAtributoSatb sisAtributoSatb) {
        this.sisAtributoSatb = sisAtributoSatb;
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
        if ( !(other instanceof EstruturaAcessoEtta) ) return false;
        EstruturaAcessoEtta castOther = (EstruturaAcessoEtta) other;
        return new EqualsBuilder()
            .append(this.getComp_id(), castOther.getComp_id())
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
     * @return
     */
    public String getIndLeiAcompMoniProprEtta() {
		return indLeiAcompMoniProprEtta;
	}

    /**
     *
     * @param indLeiAcompMoniProprEtta
     */
    public void setIndLeiAcompMoniProprEtta(String indLeiAcompMoniProprEtta) {
		this.indLeiAcompMoniProprEtta = indLeiAcompMoniProprEtta;
	}

        /**
         *
         * @return
         */
        public String getIndLeiAcompSuaResponsEtta() {
		return indLeiAcompSuaResponsEtta;
	}

        /**
         *
         * @param indLeiAcompSuaResponsEtta
         */
        public void setIndLeiAcompSuaResponsEtta(String indLeiAcompSuaResponsEtta) {
		this.indLeiAcompSuaResponsEtta = indLeiAcompSuaResponsEtta;
	}

        /**
         *
         * @return
         */
        public String getIndLeiAcompMoniSecOutEtta() {
		return indLeiAcompMoniSecOutEtta;
	}

        /**
         *
         * @param indLeiAcompMoniSecOutEtta
         */
        public void setIndLeiAcompMoniSecOutEtta(String indLeiAcompMoniSecOutEtta) {
		this.indLeiAcompMoniSecOutEtta = indLeiAcompMoniSecOutEtta;
	}

        /**
         *
         * @return
         */
        public String getIndLeiAcompMoniSecProEtta() {
		return indLeiAcompMoniSecProEtta;
	}

        /**
         *
         * @param indLeiAcompMoniSecProEtta
         */
        public void setIndLeiAcompMoniSecProEtta(String indLeiAcompMoniSecProEtta) {
		this.indLeiAcompMoniSecProEtta = indLeiAcompMoniSecProEtta;
	}
	
        /**
         *
         * @return
         */
        public String getIndExibirHistoricoEtta() {
		return indExibirHistoricoEtta;
	}

        /**
         *
         * @param indExibirHistoricoEtta
         */
        public void setIndExibirHistoricoEtta(String indExibirHistoricoEtta) {
		this.indExibirHistoricoEtta = indExibirHistoricoEtta;
	}

		/**
		 * @return the indExibirGerarArquivos
		 */
		public String getIndExibirGerarArquivosEtta() {
			return indExibirGerarArquivosEtta;
		}

		/**
		 * @param indExibirGerarArquivos the indExibirGerarArquivos to set
		 */
		public void setIndExibirGerarArquivosEtta(String indExibirGerarArquivosEtta) {
			this.indExibirGerarArquivosEtta = indExibirGerarArquivosEtta;
		}

		/**
		 * @return the indExibirImprimir
		 */
		public String getIndExibirImprimirEtta() {
			return indExibirImprimirEtta;
		}

		/**
		 * @param indExibirImprimir the indExibirImprimir to set
		 */
		public void setIndExibirImprimirEtta(String indExibirImprimirEtta) {
			this.indExibirImprimirEtta = indExibirImprimirEtta;
		}
        
    
}
