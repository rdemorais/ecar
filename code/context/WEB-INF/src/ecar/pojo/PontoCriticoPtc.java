package ecar.pojo;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import java.util.Set;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;


/** @author Hibernate CodeGenerator */
public class PontoCriticoPtc implements Cloneable, Serializable,IConfiguracaoAtributoLivre {

    /**
	 * 
	 */
	private static final long serialVersionUID = 5955246838403938385L;

	/** identifier field */
    private Long codPtc;

    /** nullable persistent field */
    private String indAtivoPtc;

    /** nullable persistent field */
    private Date dataInclusaoPtc;

    /** nullable persistent field */
    private Date dataSolucaoPtc;
    
    /** nullable persistent field */
    private Date dataUltManutencaoPtc;

    /** nullable persistent field */
    private String descricaoSolucaoPtc;

    /** nullable persistent field */
    private String indAmbitoInternoGovPtc;

    /** nullable persistent field */
    private Date dataLimitePtc;

    /** nullable persistent field */
    private Date dataIdentificacaoPtc;

    /** nullable persistent field */
    private String descricaoPtc;

    /** persistent field */
    private ItemEstruturaIett itemEstruturaIett;
    
    /** persistent field */
    private UsuarioUsu usuarioUsu;

    /** persistent field */
    private UsuarioUsu usuarioUsuInclusao;
    
    /** persistent field */
    private UsuarioUsu usuarioUsuByCodUsuUltManutPtc;
    
    private SisAtributoSatb sisAtributoTipo;

    /** persistent field */
    private Set pontoCriticoCorPtccores;
    
    /** persistent field */
    private Set apontamentoApts;
    
    /** persistent field */
    private AcompRelatorioArel acompRelatorioArel;
    
    /** persistent field */
    private Set historicoPtcHs;
    
    /** persistent field */
    private String indExcluidoPtc;
    
    /** persistent field */
    private Set pontoCriticoSisAtributoPtcSatbs;

    /** full constructor
     * @param indAtivoPtc
     * @param dataInclusaoPtc
     * @param dataSolucaoPtc
     * @param pontoCriticoSisAtributoPtcSatbs
     * @param dataLimitePtc
     * @param descricaoSolucaoPtc
     * @param dataIdentificacaoPtc
     * @param indAmbitoInternoGovPtc
     * @param itemEstruturaIett
     * @param pontoCriticoCorPtccores
     * @param usuarioUsuInclusao
     * @param descricaoPtc
     * @param sisAtributoTipo
     * @param apontamentoApts
     * @param acompRelatorioArel
     */
    public PontoCriticoPtc(String indAtivoPtc, Date dataInclusaoPtc, Date dataSolucaoPtc, String descricaoSolucaoPtc, String indAmbitoInternoGovPtc, Date dataLimitePtc, Date dataIdentificacaoPtc, String descricaoPtc, ecar.pojo.ItemEstruturaIett itemEstruturaIett, Set apontamentoApts,
    		UsuarioUsu usuarioUsuInclusao, SisAtributoSatb sisAtributoTipo, Set pontoCriticoCorPtccores,
    		AcompRelatorioArel acompRelatorioArel, Set pontoCriticoSisAtributoPtcSatbs) {
        this.indAtivoPtc = indAtivoPtc;
        this.dataInclusaoPtc = dataInclusaoPtc;
        this.dataSolucaoPtc = dataSolucaoPtc;
        this.descricaoSolucaoPtc = descricaoSolucaoPtc;
        this.indAmbitoInternoGovPtc = indAmbitoInternoGovPtc;
        this.dataLimitePtc = dataLimitePtc;
        this.dataIdentificacaoPtc = dataIdentificacaoPtc;
        this.descricaoPtc = descricaoPtc;
        this.itemEstruturaIett = itemEstruturaIett;
        this.apontamentoApts = apontamentoApts;
        this.usuarioUsuInclusao = usuarioUsuInclusao;
        this.sisAtributoTipo = sisAtributoTipo;
        this.pontoCriticoCorPtccores = pontoCriticoCorPtccores;
        this.acompRelatorioArel = acompRelatorioArel;
        this.pontoCriticoSisAtributoPtcSatbs = pontoCriticoSisAtributoPtcSatbs;
    }

    /** default constructor */
    public PontoCriticoPtc() {
    }

    /** minimal constructor
     * @param itemEstruturaIett
     * @param apontamentoApts
     * @param pontoCriticoCorPtccores
     * @param usuarioUsuInclusao
     * @param sisAtributoTipo
     */
    public PontoCriticoPtc(ecar.pojo.ItemEstruturaIett itemEstruturaIett, Set apontamentoApts, UsuarioUsu usuarioUsuInclusao, SisAtributoSatb sisAtributoTipo, Set pontoCriticoCorPtccores) {
        this.itemEstruturaIett = itemEstruturaIett;
        this.apontamentoApts = apontamentoApts;
        this.usuarioUsuInclusao = usuarioUsuInclusao;
        this.sisAtributoTipo = sisAtributoTipo;
        this.pontoCriticoCorPtccores = pontoCriticoCorPtccores;
    }

    /**
     *
     * @return
     */
    public Long getCodPtc() {
        return this.codPtc;
    }

    /**
     *
     * @param codPtc
     */
    public void setCodPtc(Long codPtc) {
        this.codPtc = codPtc;
    }

    /**
     *
     * @return
     */
    public String getIndAtivoPtc() {
        return this.indAtivoPtc;
    }

    /**
     *
     * @param indAtivoPtc
     */
    public void setIndAtivoPtc(String indAtivoPtc) {
        this.indAtivoPtc = indAtivoPtc;
    }

    /**
     *
     * @return
     */
    public Date getDataInclusaoPtc() {
        return this.dataInclusaoPtc;
    }

    /**
     *
     * @param dataInclusaoPtc
     */
    public void setDataInclusaoPtc(Date dataInclusaoPtc) {
        this.dataInclusaoPtc = dataInclusaoPtc;
    }

    /**
     *
     * @return
     */
    public Date getDataSolucaoPtc() {
        return this.dataSolucaoPtc;
    }

    /**
     *
     * @param dataSolucaoPtc
     */
    public void setDataSolucaoPtc(Date dataSolucaoPtc) {
        this.dataSolucaoPtc = dataSolucaoPtc;
    }

    /**
     *
     * @return
     */
    public String getDescricaoSolucaoPtc() {
        return this.descricaoSolucaoPtc;
    }

    /**
     *
     * @param descricaoSolucaoPtc
     */
    public void setDescricaoSolucaoPtc(String descricaoSolucaoPtc) {
        this.descricaoSolucaoPtc = descricaoSolucaoPtc;
    }

    /**
     *
     * @return
     */
    public String getIndAmbitoInternoGovPtc() {
        return this.indAmbitoInternoGovPtc;
    }

    /**
     *
     * @param indAmbitoInternoGovPtc
     */
    public void setIndAmbitoInternoGovPtc(String indAmbitoInternoGovPtc) {
        this.indAmbitoInternoGovPtc = indAmbitoInternoGovPtc;
    }

    /**
     *
     * @return
     */
    public Date getDataLimitePtc() {
        return this.dataLimitePtc;
    }

    /**
     *
     * @param dataLimitePtc
     */
    public void setDataLimitePtc(Date dataLimitePtc) {
        this.dataLimitePtc = dataLimitePtc;
    }

    /**
     *
     * @return
     */
    public Date getDataIdentificacaoPtc() {
        return this.dataIdentificacaoPtc;
    }

    /**
     *
     * @param dataIdentificacaoPtc
     */
    public void setDataIdentificacaoPtc(Date dataIdentificacaoPtc) {
        this.dataIdentificacaoPtc = dataIdentificacaoPtc;
    }

    /**
     *
     * @return
     */
    public String getDescricaoPtc() {
        return this.descricaoPtc;
    }

    /**
     *
     * @param descricaoPtc
     */
    public void setDescricaoPtc(String descricaoPtc) {
        this.descricaoPtc = descricaoPtc;
    }

    /**
     *
     * @return
     */
    public ecar.pojo.ItemEstruturaIett getItemEstruturaIett() {
        return this.itemEstruturaIett;
    }

    /**
     *
     * @param itemEstruturaIett
     */
    public void setItemEstruturaIett(ecar.pojo.ItemEstruturaIett itemEstruturaIett) {
        this.itemEstruturaIett = itemEstruturaIett;
    }
        
    /**
     *
     * @return
     */
    public ecar.pojo.UsuarioUsu getUsuarioUsu() {
        return this.usuarioUsu;
    }
    
    /**
     *
     * @param usuarioUsu
     */
    public void setUsuarioUsu(ecar.pojo.UsuarioUsu usuarioUsu) {
        this.usuarioUsu = usuarioUsu;
    }

    /**
     *
     * @return
     */
    public Set getApontamentoApts() {
        return this.apontamentoApts;
    }

    /**
     *
     * @param apontamentoApts
     */
    public void setApontamentoApts(Set apontamentoApts) {
        this.apontamentoApts = apontamentoApts;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .append("codPtc", getCodPtc())
            .toString();
    }

    @Override
    public boolean equals(Object other) {
        if ( (this == other ) ) return true;
        if ( !(other instanceof PontoCriticoPtc) ) return false;
        PontoCriticoPtc castOther = (PontoCriticoPtc) other;
        return new EqualsBuilder()
            .append(this.getCodPtc(), castOther.getCodPtc())
            .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
            .append(getCodPtc())
            .toHashCode();
    }
    
    /**
	 * Todas as propriedades primitivas ou imutáveis como String são automaticamente copiadas.
	 * O método clone deverá inicilizar apenas as propriedades mutáveis.	
	 * @return Object
	 */
    @Override
	public Object clone() {
		
		try
		{
			PontoCriticoPtc clone = (PontoCriticoPtc) super.clone();

			clone.setCodPtc(codPtc);
			clone.setIndAtivoPtc(indAtivoPtc);
			clone.setDataInclusaoPtc(dataInclusaoPtc);
			clone.setDataSolucaoPtc(dataSolucaoPtc);
			clone.setDescricaoSolucaoPtc(descricaoSolucaoPtc);
			clone.setIndAmbitoInternoGovPtc(indAmbitoInternoGovPtc);
			clone.setDataLimitePtc(dataLimitePtc);
			clone.setDataIdentificacaoPtc(dataIdentificacaoPtc);
			clone.setDescricaoPtc(descricaoPtc);
			clone.setItemEstruturaIett(itemEstruturaIett);
			clone.setUsuarioUsu(usuarioUsu);
			clone.setUsuarioUsuInclusao(usuarioUsuInclusao);
			clone.setUsuarioUsuByCodUsuUltManutPtc(usuarioUsuByCodUsuUltManutPtc);
			clone.setPontoCriticoCorPtccores(pontoCriticoCorPtccores);
			clone.setApontamentoApts(apontamentoApts);
			clone.setSisAtributoTipo(sisAtributoTipo);
			clone.setAcompRelatorioArel(acompRelatorioArel);			
		    clone.setHistoricoPtcHs(historicoPtcHs);
		

		    return clone;
		
		} catch (CloneNotSupportedException e) {
	      return null;
		}
	}

        /**
         *
         * @return
         */
        public SisAtributoSatb getSisAtributoTipo() {
		return sisAtributoTipo;
	}

        /**
         *
         * @param sisAtributoTipo
         */
        public void setSisAtributoTipo(SisAtributoSatb sisAtributoTipo) {
		this.sisAtributoTipo = sisAtributoTipo;
	}

        /**
         *
         * @return
         */
        public ecar.pojo.UsuarioUsu getUsuarioUsuInclusao() {
		return usuarioUsuInclusao;
	}

        /**
         *
         * @param usuarioUsuInclusao
         */
        public void setUsuarioUsuInclusao(ecar.pojo.UsuarioUsu usuarioUsuInclusao) {
		this.usuarioUsuInclusao = usuarioUsuInclusao;
	}

        /**
         *
         * @return
         */
        public Set getPontoCriticoCorPtccores() {
		return pontoCriticoCorPtccores;
	}

        /**
         *
         * @param pontoCriticoCorPtccores
         */
        public void setPontoCriticoCorPtccores(Set pontoCriticoCorPtccores) {
		this.pontoCriticoCorPtccores = pontoCriticoCorPtccores;
	}

        /**
         *
         * @return
         */
        public AcompRelatorioArel getAcompRelatorioArel() {
		return acompRelatorioArel;
	}

        /**
         *
         * @param acompRelatorioArel
         */
        public void setAcompRelatorioArel(AcompRelatorioArel acompRelatorioArel) {
		this.acompRelatorioArel = acompRelatorioArel;
	}

        /**
         *
         * @return
         */
        public ecar.pojo.UsuarioUsu getUsuarioUsuByCodUsuUltManutPtc() {
		return usuarioUsuByCodUsuUltManutPtc;
	}

        /**
         *
         * @param usuarioUsuByCodUsuUltManutPtc
         */
        public void setUsuarioUsuByCodUsuUltManutPtc(
			ecar.pojo.UsuarioUsu usuarioUsuByCodUsuUltManutPtc) {
		this.usuarioUsuByCodUsuUltManutPtc = usuarioUsuByCodUsuUltManutPtc;
	}

        /**
         *
         * @return
         */
        public Set getHistoricoPtcHs() {
		return historicoPtcHs;
	}

        /**
         *
         * @param historicoPtcHs
         */
        public void setHistoricoPtcHs(Set historicoPtcHs) {
		this.historicoPtcHs = historicoPtcHs;
	}

        /**
         *
         * @return
         */
        public Date getDataUltManutencaoPtc() {
		return dataUltManutencaoPtc;
	}

        /**
         *
         * @param dataUltManutencaoPtc
         */
        public void setDataUltManutencaoPtc(Date dataUltManutencaoPtc) {
		this.dataUltManutencaoPtc = dataUltManutencaoPtc;
	}

        /**
         *
         * @return
         */
        public String getIndExcluidoPtc() {
		return indExcluidoPtc;
	}

        /**
         *
         * @param indExcluidoPtc
         */
        public void setIndExcluidoPtc(String indExcluidoPtc) {
		this.indExcluidoPtc = indExcluidoPtc;
	}

        /**
         *
         * @return
         */
        public Set getPontoCriticoSisAtributoPtcSatbs() {
		return pontoCriticoSisAtributoPtcSatbs;
	}

        /**
         *
         * @param pontoCriticoSisAtributoPtcSatbs
         */
        public void setPontoCriticoSisAtributoPtcSatbs(
			Set pontoCriticoSisAtributoPtcSatbs) {
		this.pontoCriticoSisAtributoPtcSatbs = pontoCriticoSisAtributoPtcSatbs;
	}

	
	/**
	 * Dado um SisAtributo é retornado o ItemEstruturaSisAtributoIettSatb(valor do atributo Livre no item) correspondente ao SisAtributo informado e o Item Estrutura Atual(this). 
	 * @param sisAtributo
	 * @return
	 */
	public PontoCriticoSisAtributoPtcSatb buscarItemEstruturaSisAtributoLista (SisAtributoSatb sisAtributo){
		
		Set atributoLivres = this.getPontoCriticoSisAtributoPtcSatbs();	
		PontoCriticoSisAtributoPtcSatb atributoLivre=null;
		
		for (Object object : atributoLivres) {
			 atributoLivre = (PontoCriticoSisAtributoPtcSatb)object;
			
			PontoCriticoSisAtributoPtcSatb atributoLivreInner = new PontoCriticoSisAtributoPtcSatb();
			
			atributoLivreInner.setPontoCriticoPtc(this);
			atributoLivreInner.setSisAtributoSatb(sisAtributo);
			
			//Se o atributoLivre for igual ao Inner interrompe o Loop, pois o atributoLivre correspondente ao atributoLivre que está sendo alterado na página, foi encontrado.
			if (atributoLivre.equals(atributoLivreInner)) {
				break;
			}
		}

		return atributoLivre;
		
	}

	public Collection<FuncaoSisAtributo> getListaAtributosLivres() {
		
		return getPontoCriticoSisAtributoPtcSatbs();
	}

	/**
	 * Retorna o Item proprietário do Ponto Critico 
	 */
	public ItemEstruturaIett itemProprietario() {

		return getItemEstruturaIett();
	}

	public Long getCodigo() {
		return getCodPtc();
	}

}
