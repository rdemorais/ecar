package ecar.pojo;

import java.io.Serializable;
import java.util.Set;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;


/** @author Hibernate CodeGenerator */
public class TipoAcompanhamentoTa implements Serializable {
	
    /**
	 * 
	 */
	private static final long serialVersionUID = -4682970374690152578L;

    private Long codTa;
    private String descricaoTa;
    private String indMonitoramentoTa;
    private String indNaoMonitoramentoTa;
    private String indSepararOrgaoTa;
    private String indLiberarAcompTa;
    private String indLiberarParecerTa;
    private String indGerarArquivoTa;
    private Long seqApresentacaoTa;
    private String indAtivoTa;
    private EstruturaEtt estruturaEtt;
    private Set acompReferenciaArefs;
    private Set sisAtributoSatbs;
    private Set tipoAcompFuncAcompTafcs;
    private Set estAtribTipoAcompEatas; 
    private EstruturaEtt estruturaNivelGeracaoTa;
    private Set situacaoSits;
    private Set tipoAcompGrpAcessos;
	private Set tipoacompTipofuncacompSisatributoTatpfasatbs;
	private Set tipoacompAbasSisatributoTaabasatbs;
    
        /** full constructor
         * @param estruturaEtt
         * @param indLiberarAcompTa
         * @param indLiberarParecerTa
         * @param estAtribTipoAcompEatas
         * @param situacaoSits
         * @param indMonitoramentoTa
         * @param descricaoTa
         * @param sisAtributoSatbs
         * @param indSepararOrgaoTa
         * @param indNaoMonitoramentoTa
         * @param acompReferenciaArefs
         * @param seqApresentacaoTa
         * @param indAtivoTa
         * @param tipoAcompFuncAcompTafcs
         * @param tipoAcompGrpAcessos
         * @param tipoacompTipofuncacompSisatributoTatpfasatbs
         * @param tipoacompAbasSisatributoTaabasatbs
         */
    public TipoAcompanhamentoTa(EstruturaEtt estruturaEtt, 
    							String indLiberarAcompTa,
    							String indLiberarParecerTa,
								Set estAtribTipoAcompEatas, 
								String descricaoTa, 
								String indMonitoramentoTa, 
								String indNaoMonitoramentoTa, 
								String indSepararOrgaoTa, 
								Long seqApresentacaoTa,  
								String indAtivoTa, 
								Set acompReferenciaArefs, 
								Set sisAtributoSatbs, 
								Set tipoAcompFuncAcompTafcs,
								Set situacaoSits,
								Set tipoAcompGrpAcessos,
	    						Set tipoacompTipofuncacompSisatributoTatpfasatbs,
	    						Set tipoacompAbasSisatributoTaabasatbs) {
    	this.indLiberarAcompTa = indLiberarAcompTa;
    	this.indLiberarParecerTa = indLiberarParecerTa;
        this.descricaoTa = descricaoTa;
        this.indMonitoramentoTa = indMonitoramentoTa;
        this.indNaoMonitoramentoTa = indNaoMonitoramentoTa;
        this.indSepararOrgaoTa = indSepararOrgaoTa;
        this.seqApresentacaoTa = seqApresentacaoTa;
        this.indAtivoTa = indAtivoTa;
        this.acompReferenciaArefs = acompReferenciaArefs;
        this.sisAtributoSatbs = sisAtributoSatbs;
        this.tipoAcompFuncAcompTafcs = tipoAcompFuncAcompTafcs;
        this.estAtribTipoAcompEatas = estAtribTipoAcompEatas;
        this.estruturaEtt = estruturaEtt;
        this.situacaoSits = situacaoSits;
        this.tipoAcompGrpAcessos = tipoAcompGrpAcessos;
        this.tipoacompTipofuncacompSisatributoTatpfasatbs = tipoacompAbasSisatributoTaabasatbs;
        this.tipoacompAbasSisatributoTaabasatbs = tipoacompAbasSisatributoTaabasatbs;
    }

    /** default constructor */
    public TipoAcompanhamentoTa() {
    }

//    /** minimal constructor */
//    public TipoAcompanhamentoTa(EstruturaEtt estruturaEtt, Set estAtribTipoAcompEatas, Set acompReferenciaArefs, Set sisAtributoSatbs, Set tipoAcompFuncAcompTafcs) {
//        this.acompReferenciaArefs = acompReferenciaArefs;
//        this.sisAtributoSatbs = sisAtributoSatbs;
//        this.tipoAcompFuncAcompTafcs = tipoAcompFuncAcompTafcs;
//        this.estAtribTipoAcompEatas = estAtribTipoAcompEatas;
//        this.estruturaEtt = estruturaEtt;
//    }
        
    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .append("codTa", getCodTa())
            .toString();
    }

    @Override
    public boolean equals(Object other) {
        if ( (this == other ) ) return true;
        if ( !(other instanceof TipoAcompanhamentoTa) ) return false;
        TipoAcompanhamentoTa castOther = (TipoAcompanhamentoTa) other;
        return new EqualsBuilder()
            .append(this.getCodTa(), castOther.getCodTa())
            .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
            .append(getCodTa())
            .toHashCode();
    }

    /**
     *
     * @return
     */
    public Set getAcompReferenciaArefs() {
		return acompReferenciaArefs;
	}

    /**
     *
     * @param acompReferenciaArefs
     */
    public void setAcompReferenciaArefs(Set acompReferenciaArefs) {
		this.acompReferenciaArefs = acompReferenciaArefs;
	}

    /**
     *
     * @return
     */
    public Long getCodTa() {
		return codTa;
	}

        /**
         *
         * @param codTa
         */
        public void setCodTa(Long codTa) {
		this.codTa = codTa;
	}

        /**
         *
         * @return
         */
        public String getDescricaoTa() {
		return descricaoTa;
	}

        /**
         *
         * @param descricaoTa
         */
        public void setDescricaoTa(String descricaoTa) {
		this.descricaoTa = descricaoTa;
	}

        /**
         *
         * @return
         */
        public String getIndAtivoTa() {
		return indAtivoTa;
	}

        /**
         *
         * @param indAtivoTa
         */
        public void setIndAtivoTa(String indAtivoTa) {
		this.indAtivoTa = indAtivoTa;
	}

        /**
         *
         * @return
         */
        public String getIndMonitoramentoTa() {
		return indMonitoramentoTa;
	}

        /**
         *
         * @param indMonitoramentoTa
         */
        public void setIndMonitoramentoTa(String indMonitoramentoTa) {
		this.indMonitoramentoTa = indMonitoramentoTa;
	}

        /**
         *
         * @return
         */
        public String getIndNaoMonitoramentoTa() {
		return indNaoMonitoramentoTa;
	}

        /**
         *
         * @param indNaoMonitoramentoTa
         */
        public void setIndNaoMonitoramentoTa(String indNaoMonitoramentoTa) {
		this.indNaoMonitoramentoTa = indNaoMonitoramentoTa;
	}

        /**
         *
         * @return
         */
        public String getIndSepararOrgaoTa() {
		return indSepararOrgaoTa;
	}

        /**
         *
         * @param indSepararOrgaoTa
         */
        public void setIndSepararOrgaoTa(String indSepararOrgaoTa) {
		this.indSepararOrgaoTa = indSepararOrgaoTa;
	}

        /**
         *
         * @return
         */
        public Long getSeqApresentacaoTa() {
		return seqApresentacaoTa;
	}

        /**
         *
         * @param seqApresentacaoTa
         */
        public void setSeqApresentacaoTa(Long seqApresentacaoTa) {
		this.seqApresentacaoTa = seqApresentacaoTa;
	}

        /**
         *
         * @return
         */
        public Set getSisAtributoSatbs() {
		return sisAtributoSatbs;
	}

        /**
         *
         * @param sisAtributoSatbs
         */
        public void setSisAtributoSatbs(Set sisAtributoSatbs) {
		this.sisAtributoSatbs = sisAtributoSatbs;
	}

        /**
         *
         * @return
         */
        public Set getTipoAcompFuncAcompTafcs() {
		return tipoAcompFuncAcompTafcs;
	}

        /**
         *
         * @param tipoAcompFuncAcompTafcs
         */
        public void setTipoAcompFuncAcompTafcs(Set tipoAcompFuncAcompTafcs) {
		this.tipoAcompFuncAcompTafcs = tipoAcompFuncAcompTafcs;
	}

        /**
         *
         * @return
         */
        public Set getEstAtribTipoAcompEatas() {
		return estAtribTipoAcompEatas;
	}

        /**
         *
         * @param estAtribTipoAcompEatas
         */
        public void setEstAtribTipoAcompEatas(Set estAtribTipoAcompEatas) {
		this.estAtribTipoAcompEatas = estAtribTipoAcompEatas;
	}

        /**
         *
         * @return
         */
        public String getIndLiberarAcompTa() {
		return indLiberarAcompTa;
	}

        /**
         *
         * @param indLiberarAcompTa
         */
        public void setIndLiberarAcompTa(String indLiberarAcompTa) {
		this.indLiberarAcompTa = indLiberarAcompTa;
	}
	
        /**
         *
         * @return
         */
        public String getIndLiberarParecerTa() {
		return indLiberarParecerTa;
	}

       /**
        *
        * @return
        */
        public String getIndGerarArquivoTa() {
			return indGerarArquivoTa;
		}

       /**
        *
        * @param indGerarArquivoTa
        */
		public void setIndGerarArquivoTa(String indGerarArquivoTa) {
			this.indGerarArquivoTa = indGerarArquivoTa;
		}

		/**
         *
         * @param indLiberarParecerTa
         */
        public void setIndLiberarParecerTa(String indLiberarParecerTa) {
		this.indLiberarParecerTa = indLiberarParecerTa;
	}

        /**
         *
         * @return
         */
        public EstruturaEtt getEstruturaEtt() {
		return estruturaEtt;
	}

        /**
         *
         * @param estruturaEtt
         */
        public void setEstruturaEtt(EstruturaEtt estruturaEtt) {
		this.estruturaEtt = estruturaEtt;
	}

	/**
	 * @return the estruturaNivelGeracaoTa
	 */
	public ecar.pojo.EstruturaEtt getEstruturaNivelGeracaoTa() {
		return estruturaNivelGeracaoTa;
	}

	/**
	 * @param estruturaNivelGeracaoTa the estruturaNivelGeracaoTa to set
	 */
	public void setEstruturaNivelGeracaoTa(
			ecar.pojo.EstruturaEtt estruturaNivelGeracaoTa) {
		this.estruturaNivelGeracaoTa = estruturaNivelGeracaoTa;
	}

        /**
         *
         * @return
         */
        public Set getSituacaoSits() {
		return situacaoSits;
	}

        /**
         *
         * @param situacaoSits
         */
        public void setSituacaoSits(Set situacaoSits) {
		this.situacaoSits = situacaoSits;
	}

        /**
         *
         * @return
         */
        public Set getTipoAcompGrpAcessos() {
		return tipoAcompGrpAcessos;
	}

        /**
         *
         * @param tipoAcompGrpAcessos
         */
        public void setTipoAcompGrpAcessos(Set tipoAcompGrpAcessos) {
		this.tipoAcompGrpAcessos = tipoAcompGrpAcessos;
	}

        /**
         *
         * @return
         */
        public Set getTipoacompTipofuncacompSisatributoTatpfasatbs() {
		return tipoacompTipofuncacompSisatributoTatpfasatbs;
	}

        /**
         *
         * @param tipoacompTipofuncacompSisatributoTatpfasatbs
         */
        public void setTipoacompTipofuncacompSisatributoTatpfasatbs(
			Set tipoacompTipofuncacompSisatributoTatpfasatbs) {
		this.tipoacompTipofuncacompSisatributoTatpfasatbs = tipoacompTipofuncacompSisatributoTatpfasatbs;
	}

        /**
         *
         * @return
         */
        public Set getTipoacompAbasSisatributoTaabasatbs() {
		return tipoacompAbasSisatributoTaabasatbs;
	}

        /**
         *
         * @param tipoacompAbasSisatributoTaabasatbs
         */
        public void setTipoacompAbasSisatributoTaabasatbs(
			Set tipoacompAbasSisatributoTaabasatbs) {
		this.tipoacompAbasSisatributoTaabasatbs = tipoacompAbasSisatributoTaabasatbs;
	}
}
