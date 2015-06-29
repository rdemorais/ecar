package ecar.pojo;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

import comum.util.ConstantesECAR;

import ecar.util.Dominios;


/** @author Hibernate CodeGenerator */
public class SisAtributoSatb implements Serializable {
	
    /**
     *
     */
    public final static String ATIVO = "S";
        /**
         *
         */
        public final static String INATIVO = "N";

    /**
	 * 
	 */
	private static final long serialVersionUID = 60317785733970056L;

	/** identifier field */
    private Long codSatb;
    private Date dataInclusaoSatb;
    private String indAtivoSatb;
    private String descricaoSatb;
    private String atribInfCompSatb;
    private SisGrupoAtributoSga sisGrupoAtributoSga;
    private Set usuarioAtributoUsuas;
    private Set segmentoTpAcessoSgttas;
    private Set itemEstruturaNivelIettns;
    private Set segmentoItemTpacesSgtitas;
    private Set segmentoCategTpAcessSgts;
    private Set estruturaAcessoEttas;
    private Set segmentoSisAtribSgtsas;
    private Set configuracaoCfgsByCodSacapa;
    private Set configuracaoCfgsByCodSapadrao;
	private Set itemEstrutUsuarioIettuses;
    private Set entidadeAtributoEntas;
    private Set locAtributoLocas;
    private Set demAtributoDemas;	
    private Set tipoAcompanhamentoTas;
    private Set pontoCriticoPtcs;
    private Set itemEstrtIndResulIettrs;
    private Set agendaAges; 
    private Set itemEstruturaSisAtributoIettSatbs;
    private Set historicoIettSatbHs;
    private Set tipoAcompGrpAcessos;
	private Set tipoacompTipofuncacompSisatributoTatpfasatbs;
	private Set tipoacompAbasSisatributoTaabasatbs;
	private Boolean geral;
	private Boolean periodico;
	private String mascara;	
	private Set pesquisas;

        /**
         *
         * @return
         */
        public Set getPesquisas() {
		return pesquisas;
	}

        /**
         *
         * @param pesquisas
         */
        public void setPesquisas(Set pesquisas) {
		this.pesquisas = pesquisas;
	}

        /**
         *
         * @return
         */
        @AvaliaMetodo(valida=true)
	public Boolean getGeral() {
		return geral;
	}

        /**
         *
         * @param geral
         */
        public void setGeral(Boolean geral) {
		this.geral = geral;
	}

        /**
         *
         * @return
         */
        @AvaliaMetodo(valida=true)
	public Boolean getPeriodico() {
		return periodico;
	}

        /**
         *
         * @param periodico
         */
        public void setPeriodico(Boolean periodico) {
		this.periodico = periodico;
	}

        /**
         *
         * @return
         */
        public String getMascara() {
		return mascara;
	}

        /**
         *
         * @param mascara
         */
        public void setMascara(String mascara) {
		this.mascara = mascara;
	}

        /** full constructor
         * @param dataInclusaoSatb
         * @param usuarioAtributoUsuas
         * @param descricaoSatb
         * @param atribInfCompSatb
         * @param indAtivoSatb
         * @param segmentoTpAcessoSgttas
         * @param tipoacompAbasSisatributoTaabasatbs
         * @param locAtributoLocas
         * @param sisGrupoAtributoSga
         * @param configuracaoCfgsByCodSapadrao
         * @param segmentoItemTpacesSgtitas
         * @param configuracaoCfgsByCodSacapa
         * @param itemEstruturaNivelIettns
         * @param segmentoCategTpAcessSgts
         * @param estruturaAcessoEttas
         * @param segmentoSisAtribSgtsas
         * @param agendaAges
         * @param pontoCriticoPtcs
         * @param entidadeAtributoEntas
         * @param tipoAcompanhamentoTas
         * @param tipoAcompGrpAcessos
         * @param demAtributoDemas
         * @param itemEstrtIndResulIettrs
         * @param tipoacompTipofuncacompSisatributoTatpfasatbs
         */
    public SisAtributoSatb(Date dataInclusaoSatb, 
    						String indAtivoSatb, 
    						String descricaoSatb, 
    						String atribInfCompSatb, 
    						SisGrupoAtributoSga sisGrupoAtributoSga, 
    						Set usuarioAtributoUsuas, 
    						Set segmentoTpAcessoSgttas, 
    						Set itemEstruturaNivelIettns, 
    						Set segmentoItemTpacesSgtitas, 
    						Set segmentoCategTpAcessSgts, 
    						Set estruturaAcessoEttas, 
    						Set segmentoSisAtribSgtsas, 
    						Set configuracaoCfgsByCodSacapa, 
    						Set configuracaoCfgsByCodSapadrao,  
    						Set entidadeAtributoEntas, 
    						Set locAtributoLocas, 
    						Set demAtributoDemas, 
    						Set tipoAcompanhamentoTas, 
    						Set pontoCriticoPtcs, 
    						Set itemEstrtIndResulIettrs, 
    						Set agendaAges, 
    						Set tipoAcompGrpAcessos,
    						Set tipoacompTipofuncacompSisatributoTatpfasatbs,
    						Set tipoacompAbasSisatributoTaabasatbs) {
        this.dataInclusaoSatb = dataInclusaoSatb;
        this.indAtivoSatb = indAtivoSatb;
        this.descricaoSatb = descricaoSatb;
        this.atribInfCompSatb = atribInfCompSatb;
        this.sisGrupoAtributoSga = sisGrupoAtributoSga;
        this.usuarioAtributoUsuas = usuarioAtributoUsuas;
        this.segmentoTpAcessoSgttas = segmentoTpAcessoSgttas;
        this.itemEstruturaNivelIettns = itemEstruturaNivelIettns;
        this.segmentoItemTpacesSgtitas = segmentoItemTpacesSgtitas;
        this.segmentoCategTpAcessSgts = segmentoCategTpAcessSgts;
        this.estruturaAcessoEttas = estruturaAcessoEttas;
        this.segmentoSisAtribSgtsas = segmentoSisAtribSgtsas;
        this.configuracaoCfgsByCodSacapa = configuracaoCfgsByCodSacapa;
        this.configuracaoCfgsByCodSapadrao = configuracaoCfgsByCodSapadrao;
        this.entidadeAtributoEntas = entidadeAtributoEntas;
        this.locAtributoLocas = locAtributoLocas;
        this.demAtributoDemas = demAtributoDemas;    
        this.tipoAcompanhamentoTas = tipoAcompanhamentoTas;
        this.pontoCriticoPtcs = pontoCriticoPtcs; 
        this.itemEstrtIndResulIettrs = itemEstrtIndResulIettrs; 
        this.agendaAges = agendaAges;
        this.tipoAcompGrpAcessos = tipoAcompGrpAcessos;
        this.tipoacompTipofuncacompSisatributoTatpfasatbs = tipoacompAbasSisatributoTaabasatbs;
        this.tipoacompAbasSisatributoTaabasatbs = tipoacompAbasSisatributoTaabasatbs;
    }

    /** default constructor */
    public SisAtributoSatb() {
    }

    /** minimal constructor
     * @param sisGrupoAtributoSga
     * @param usuarioAtributoUsuas
     * @param itemEstrtIndResulIettrs
     * @param locAtributoLocas
     * @param itemEstruturaNivelIettns
     * @param segmentoTpAcessoSgttas
     * @param segmentoItemTpacesSgtitas
     * @param segmentoCategTpAcessSgts
     * @param configuracaoCfgsByCodSapadrao
     * @param pontoCriticoPtcs
     * @param entidadeAtributoEntas
     * @param segmentoSisAtribSgtsas
     * @param configuracaoCfgsByCodSacapa
     * @param estruturaAcessoEttas
     * @param demAtributoDemas
     * @param tipoAcompanhamentoTas
     */
    public SisAtributoSatb(ecar.pojo.SisGrupoAtributoSga sisGrupoAtributoSga, Set usuarioAtributoUsuas, Set segmentoTpAcessoSgttas, Set itemEstruturaNivelIettns, Set segmentoItemTpacesSgtitas, Set segmentoCategTpAcessSgts, Set estruturaAcessoEttas, Set segmentoSisAtribSgtsas, Set configuracaoCfgsByCodSacapa, Set configuracaoCfgsByCodSapadrao, Set entidadeAtributoEntas, Set locAtributoLocas, Set demAtributoDemas, Set tipoAcompanhamentoTas, Set pontoCriticoPtcs, Set itemEstrtIndResulIettrs) {
        this.sisGrupoAtributoSga = sisGrupoAtributoSga;
        this.usuarioAtributoUsuas = usuarioAtributoUsuas;
        this.segmentoTpAcessoSgttas = segmentoTpAcessoSgttas;
        this.itemEstruturaNivelIettns = itemEstruturaNivelIettns;
        this.segmentoItemTpacesSgtitas = segmentoItemTpacesSgtitas;
        this.segmentoCategTpAcessSgts = segmentoCategTpAcessSgts;
        this.estruturaAcessoEttas = estruturaAcessoEttas;
        this.segmentoSisAtribSgtsas = segmentoSisAtribSgtsas;
        this.configuracaoCfgsByCodSacapa = configuracaoCfgsByCodSacapa;
        this.configuracaoCfgsByCodSapadrao = configuracaoCfgsByCodSapadrao;
        this.entidadeAtributoEntas = entidadeAtributoEntas;
        this.locAtributoLocas = locAtributoLocas;
        this.demAtributoDemas = demAtributoDemas;     
        this.tipoAcompanhamentoTas = tipoAcompanhamentoTas;      
        this.pontoCriticoPtcs = pontoCriticoPtcs;
        this.itemEstrtIndResulIettrs = itemEstrtIndResulIettrs;
    }

    /**
     *
     * @return
     */
    public Long getCodSatb() {
        return this.codSatb;
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
    public Date getDataInclusaoSatb() {
        return this.dataInclusaoSatb;
    }

    /**
     *
     * @param dataInclusaoSatb
     */
    public void setDataInclusaoSatb(Date dataInclusaoSatb) {
        this.dataInclusaoSatb = dataInclusaoSatb;
    }

    /**
     *
     * @return
     */
    public String getIndAtivoSatb() {
        return this.indAtivoSatb;
    }

    /**
     *
     * @param indAtivoSatb
     */
    public void setIndAtivoSatb(String indAtivoSatb) {
        this.indAtivoSatb = indAtivoSatb;
    }

    /**
     *
     * @return
     */
    public String getDescricaoSatb() {
        return this.descricaoSatb;
    }

    /**
     *
     * @return
     */
    public Set getItemEstrtIndResulIettrs() {
		return itemEstrtIndResulIettrs;
	}

    /**
     *
     * @param itemEstrtIndResulIettrs
     */
    public void setItemEstrtIndResulIettrs(Set itemEstrtIndResulIettrs) {
		this.itemEstrtIndResulIettrs = itemEstrtIndResulIettrs;
	}

        /**
         *
         * @param descricaoSatb
         */
        public void setDescricaoSatb(String descricaoSatb) {
        this.descricaoSatb = descricaoSatb;
    }

        /**
         *
         * @return
         */
        public String getAtribInfCompSatb() {
        return this.atribInfCompSatb;
    }

    /**
     *
     * @param atribInfCompSatb
     */
    public void setAtribInfCompSatb(String atribInfCompSatb) {
        this.atribInfCompSatb = atribInfCompSatb;
    }

    /**
     *
     * @return
     */
    public ecar.pojo.SisGrupoAtributoSga getSisGrupoAtributoSga() {
        return this.sisGrupoAtributoSga;
    }

    /**
     *
     * @param sisGrupoAtributoSga
     */
    public void setSisGrupoAtributoSga(ecar.pojo.SisGrupoAtributoSga sisGrupoAtributoSga) {
        this.sisGrupoAtributoSga = sisGrupoAtributoSga;
    }

    /**
     *
     * @return
     */
    public Set getUsuarioAtributoUsuas() {
        return this.usuarioAtributoUsuas;
    }

    /**
     *
     * @param usuarioAtributoUsuas
     */
    public void setUsuarioAtributoUsuas(Set usuarioAtributoUsuas) {
        this.usuarioAtributoUsuas = usuarioAtributoUsuas;
    }

    /**
     *
     * @return
     */
    public Set getSegmentoTpAcessoSgttas() {
        return this.segmentoTpAcessoSgttas;
    }

    /**
     *
     * @param segmentoTpAcessoSgttas
     */
    public void setSegmentoTpAcessoSgttas(Set segmentoTpAcessoSgttas) {
        this.segmentoTpAcessoSgttas = segmentoTpAcessoSgttas;
    }

    /**
     *
     * @return
     */
    public Set getItemEstruturaNivelIettns() {
        return this.itemEstruturaNivelIettns;
    }

    /**
     *
     * @param itemEstruturaNivelIettns
     */
    public void setItemEstruturaNivelIettns(Set itemEstruturaNivelIettns) {
        this.itemEstruturaNivelIettns = itemEstruturaNivelIettns;
    }

    /**
     *
     * @return
     */
    public Set getSegmentoItemTpacesSgtitas() {
        return this.segmentoItemTpacesSgtitas;
    }

    /**
     *
     * @param segmentoItemTpacesSgtitas
     */
    public void setSegmentoItemTpacesSgtitas(Set segmentoItemTpacesSgtitas) {
        this.segmentoItemTpacesSgtitas = segmentoItemTpacesSgtitas;
    }

    /**
     *
     * @return
     */
    public Set getSegmentoCategTpAcessSgts() {
        return this.segmentoCategTpAcessSgts;
    }

    /**
     *
     * @param segmentoCategTpAcessSgts
     */
    public void setSegmentoCategTpAcessSgts(Set segmentoCategTpAcessSgts) {
        this.segmentoCategTpAcessSgts = segmentoCategTpAcessSgts;
    } 


    /**
     *
     * @return
     */
    public Set getEstruturaAcessoEttas() {
        return this.estruturaAcessoEttas;
    }

    /**
     *
     * @param estruturaAcessoEttas
     */
    public void setEstruturaAcessoEttas(Set estruturaAcessoEttas) {
        this.estruturaAcessoEttas = estruturaAcessoEttas;
    }

    /**
     *
     * @return
     */
    public Set getSegmentoSisAtribSgtsas() {
        return this.segmentoSisAtribSgtsas;
    }

    /**
     *
     * @param segmentoSisAtribSgtsas
     */
    public void setSegmentoSisAtribSgtsas(Set segmentoSisAtribSgtsas) {
        this.segmentoSisAtribSgtsas = segmentoSisAtribSgtsas;
    }

    /**
     *
     * @return
     */
    public Set getConfiguracaoCfgsByCodSacapa() {
        return this.configuracaoCfgsByCodSacapa;
    }

    /**
     *
     * @param configuracaoCfgsByCodSacapa
     */
    public void setConfiguracaoCfgsByCodSacapa(Set configuracaoCfgsByCodSacapa) {
        this.configuracaoCfgsByCodSacapa = configuracaoCfgsByCodSacapa;
    }

    /**
     *
     * @return
     */
    public Set getConfiguracaoCfgsByCodSapadrao() {
        return this.configuracaoCfgsByCodSapadrao;
    }

    /**
     *
     * @param configuracaoCfgsByCodSapadrao
     */
    public void setConfiguracaoCfgsByCodSapadrao(Set configuracaoCfgsByCodSapadrao) {
        this.configuracaoCfgsByCodSapadrao = configuracaoCfgsByCodSapadrao;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .append("codSatb", getCodSatb())
            .toString();
    }

    @Override
    public boolean equals(Object other) {
        if ( (this == other ) ) return true;
        if ( !(other instanceof SisAtributoSatb) ) return false;
        SisAtributoSatb castOther = (SisAtributoSatb) other;
        return new EqualsBuilder()
            .append(this.getCodSatb(), castOther.getCodSatb())
            .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
            .append(getCodSatb())
            .toHashCode();
    }

	/**
	 * @return Returns the itemEstrutUsuarioIettuses.
	 */
	public Set getItemEstrutUsuarioIettuses() {
		return itemEstrutUsuarioIettuses;
	}

	/**
	 * @param itemEstrutUsuarioIettuses The itemEstrutUsuarioIettuses to set.
	 */
	public void setItemEstrutUsuarioIettuses(Set itemEstrutUsuarioIettuses) {
		this.itemEstrutUsuarioIettuses = itemEstrutUsuarioIettuses;
	}


        /**
         *
         * @return
         */
        public Set getEntidadeAtributoEntas() {
        return this.entidadeAtributoEntas;
    }

    /**
     *
     * @param entidadeAtributoEntas
     */
    public void setEntidadeAtributoEntas(Set entidadeAtributoEntas) {
        this.entidadeAtributoEntas = entidadeAtributoEntas;
    }

    /**
     *
     * @return
     */
    public Set getLocAtributoLocas() {
        return this.locAtributoLocas;
    }

    /**
     *
     * @param locAtributoLocas
     */
    public void setLocAtributoLocas(Set locAtributoLocas) {
        this.locAtributoLocas = locAtributoLocas;
    }

    /**
     *
     * @return
     */
    public Set getDemAtributoDemas() {
        return this.demAtributoDemas;
    }

    /**
     *
     * @param demAtributoDemas
     */
    public void setDemAtributoDemas(Set demAtributoDemas) {
        this.demAtributoDemas = demAtributoDemas;
    }

    /**
     *
     * @return
     */
    public Set getTipoAcompanhamentoTas() {
		return tipoAcompanhamentoTas;
	}

    /**
     *
     * @param tipoAcompanhamentoTas
     */
    public void setTipoAcompanhamentoTas(Set tipoAcompanhamentoTas) {
		this.tipoAcompanhamentoTas = tipoAcompanhamentoTas;
	}

        /**
         *
         * @return
         */
        public Set getPontoCriticoPtcs() {
		return pontoCriticoPtcs;
	}

        /**
         *
         * @param pontoCriticoPtcs
         */
        public void setPontoCriticoPtcs(Set pontoCriticoPtcs) {
		this.pontoCriticoPtcs = pontoCriticoPtcs;
	}	

        /**
         *
         * @return
         */
        public Set getItemEstruturaSisAtributoIettSatbs() {
		return itemEstruturaSisAtributoIettSatbs;
	}

        /**
         *
         * @param itemEstruturaSisAtributoIettSatbs
         */
        public void setItemEstruturaSisAtributoIettSatbs(
			Set itemEstruturaSisAtributoIettSatbs) {
		this.itemEstruturaSisAtributoIettSatbs = itemEstruturaSisAtributoIettSatbs;
	}

        /**
         *
         * @return
         */
        public Set getHistoricoIettSatbHs() {
		return historicoIettSatbHs;
	}

        /**
         *
         * @param historicoIettSatbHs
         */
        public void setHistoricoIettSatbHs(Set historicoIettSatbHs) {
		this.historicoIettSatbHs = historicoIettSatbHs;
	}

        /**
         *
         * @return
         */
        public Set getAgendaAges() {
		return agendaAges;
	}

        /**
         *
         * @param agendaAges
         */
        public void setAgendaAges(Set agendaAges) {
		this.agendaAges = agendaAges;
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

        /**
         *
         * @return
         */
        public boolean isAtributoAutoIcremental() {
		
		boolean ret = false;

		if (getAtribInfCompSatb() != null && getAtribInfCompSatb().equals(ConstantesECAR.VALIDACAO_ATRIBUTO_INCREMENTAL)) {
			ret = true;
		}
		
		return ret;
	}
	
        /**
         *
         * @return
         */
        public boolean isAtributoMascara() {
		
		boolean ret = false;

		if (getAtribInfCompSatb() != null && getAtribInfCompSatb().equals(ConstantesECAR.VALIDACAO_ATRIBUTO_MASCARA)) {
			ret = true;
		}
		
		return ret;
	}

        /**
        *
        * @return
        */
        
        public boolean isAtributoData() {
    		
    		boolean ret = false;

    		if (getAtribInfCompSatb() != null && getAtribInfCompSatb().equals(ConstantesECAR.VALIDACAO_ATRIBUTO_DATA)) {
    			ret = true;
    		}
    		
    		return ret;
    	}

        /**
         *
         * @return
         */
        public boolean isAtributoMascaraEditavel() {
		
		boolean ret = false;

		if (getAtribInfCompSatb() != null && getAtribInfCompSatb().equals(ConstantesECAR.VALIDACAO_ATRIBUTO_MASCARA_EDITAVEL)) {
				ret = true;
		}
		
		return ret;
	}

        /**
         *
         * @return
         */
        public boolean isAtributoContemMascara() {
		
		boolean ret = false;
		
		if (isAtributoMascara() || isAtributoMascaraEditavel()) {
			ret = true;
		}
		
		return ret;
	}
	
        /**
         *
         * @return
         */
        public boolean isAtivo(){
		
		boolean ret=false;
		
		if (getIndAtivoSatb().equals(Dominios.ATIVO)){
			ret = true;
		}
		
		return ret;
	}
	
}