package ecar.pojo;

import java.io.Serializable;
import java.util.Date;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;


/** 
 * @author Hibernate CodeGenerator, rogeriom
 * @since n/c
 * @version 0.2, 04/06/2007
 */
public class ItemEstrutUsuarioIettus implements Cloneable, Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = -3061940236025541482L;

	/** identifier field */
    private Long codIettus;

    /** nullable persistent field */
    private ecar.pojo.UsuarioUsu usuarioUsu;
	
    /** nullable persistent field */
    private ecar.pojo.ItemEstruturaIett itemEstruturaIett;
	
    /** nullable persistent field */
    private ecar.pojo.ItemEstruturaIett itemEstruturaIettOrigem;

    /** nullable persistent field */
    private String codTpPermIettus; 

    /** nullable persistent field */
    private ecar.pojo.SisAtributoSatb sisAtributoSatb;

	/** nullable persistent field */
    private ecar.pojo.TipoFuncAcompTpfa tipoFuncAcompTpfa;
	
    /** nullable persistent field */
    private Date dataInclusaoIettus;
   
	/** nullable persistent field */
    private String indLeituraIettus;

	/** nullable persistent field */
    private String indExcluirIettus;
	
	/** nullable persistent field */
    private String indEdicaoIettus;

    /** nullable persistent field */
    private String indAtivMonitIettus;
	
	/** nullable persistent field */
    private String indDesatMonitIettus;
	
	/** nullable persistent field */
    private String indBloqPlanIettus;
	
	/** nullable persistent field */
    private String indDesblPlanIettus;
	
	/** nullable persistent field */
    private String indInfAndamentoIettus;
	
	/** nullable persistent field */
    private String indEmitePosIettus;
	
	/** nullable persistent field */
    private String indProxNivelIettus;
    
    /* -- Mantis #2156 -- */
    private UsuarioUsu usuManutencao;
    private Boolean indExclusaoPosHistorico;
    
    private String indLeituraParecerIettus;
	
    /**
     *
     * @return
     */
    public String getIndLeituraParecerIettus() {
		return indLeituraParecerIettus;
	}

    /**
     *
     * @param indLeituraParecerIettus
     */
    public void setIndLeituraParecerIettus(String indLeituraParecerIettus) {
		this.indLeituraParecerIettus = indLeituraParecerIettus;
	}

        /**
         *
         * @return
         */
        public Boolean getIndExclusaoPosHistorico() {
		return indExclusaoPosHistorico;
	}

        /**
         *
         * @param indExclusaoPosHistorico
         */
        public void setIndExclusaoPosHistorico(Boolean indExclusaoPosHistorico) {
		this.indExclusaoPosHistorico = indExclusaoPosHistorico;
	}
	
	/** default constructor */
    public ItemEstrutUsuarioIettus() {
    }

    /** construtor utilizado para incluir permissoes
     * @param itemEstruturaIett
     * @param indInfAndamento
     * @param usuarioUsu
     * @param codTpPermIettus
     * @param itemEstruturaIettOrigem
     * @param indProxNiv
     * @param dataInclusao
     * @param indBloqPlan
     * @param tipoFuncAcompTpfa
     * @param indExcluir
     * @param indEdicao
     * @param indLeitura
     * @param indAtivMonit
     * @param sisAtributoSatb
     * @param indDesatMonit
     * @param indLeituraParecer
     * @param indDesblPlan
     * @param indEmitePos
     */
	public ItemEstrutUsuarioIettus(ItemEstruturaIett itemEstruturaIett, ItemEstruturaIett itemEstruturaIettOrigem, 
				UsuarioUsu usuarioUsu, String codTpPermIettus, SisAtributoSatb sisAtributoSatb, 
				TipoFuncAcompTpfa tipoFuncAcompTpfa, String indLeitura, String indEdicao, String indExcluir, 
				String indAtivMonit, String indDesatMonit, String indBloqPlan, String indDesblPlan, 
				String indInfAndamento, String indEmitePos, String indProxNiv, Date dataInclusao, String indLeituraParecer) {
			this.itemEstruturaIett = itemEstruturaIett;
			this.itemEstruturaIettOrigem = itemEstruturaIettOrigem;
			this.usuarioUsu = usuarioUsu;
			this.codTpPermIettus = codTpPermIettus;
			this.sisAtributoSatb = sisAtributoSatb;
			this.tipoFuncAcompTpfa = tipoFuncAcompTpfa;
			this.indLeituraIettus = indLeitura;
			this.indEdicaoIettus = indEdicao;
			this.indExcluirIettus = indExcluir;
			this.indAtivMonitIettus = indAtivMonit;
			this.indDesatMonitIettus = indDesatMonit;
			this.indBloqPlanIettus = indBloqPlan;
			this.indDesblPlanIettus = indDesblPlan;
			this.indInfAndamentoIettus = indInfAndamento;
			this.indEmitePosIettus = indEmitePos;
			this.indProxNivelIettus = indProxNiv;
			this.dataInclusaoIettus = dataInclusao;
			this.indLeituraParecerIettus = indLeituraParecer;
    }

	/**
	 * @return Returns the codEttUs.
	 */
	public Long getCodIettus() {
		return codIettus;
	}

	/**
         * @param codIettus
	 */
	public void setCodIettus(Long codIettus) {
		this.codIettus = codIettus;
	}

	/**
	 * @return Returns the codTpPermIettus.
	 */
	public String getCodTpPermIettus() {
		return codTpPermIettus;
	}

	/**
         *
         * @param codTpPermEttUs
         */
	public void setCodTpPermIettus(String codTpPermEttUs) {
		this.codTpPermIettus = codTpPermEttUs;
	}

	/**
	 * @return Returns the dataInclusaoIettus.
	 */
	public Date getDataInclusaoIettus() {
		return dataInclusaoIettus;
	}

	/**
	 * @param dataInclusaoIettus The dataInclusaoIettus to set.
	 */
	public void setDataInclusaoIettus(Date dataInclusaoIettus) {
		this.dataInclusaoIettus = dataInclusaoIettus;
	}

	/**
	 * @return Returns the indAtivMonitIettus.
	 */
	public String getIndAtivMonitIettus() {
		return indAtivMonitIettus;
	}

	/**
	 * @param indAtivMonitIettus The indAtivMonitIettus to set.
	 */
	public void setIndAtivMonitIettus(String indAtivMonitIettus) {
		this.indAtivMonitIettus = indAtivMonitIettus;
	}

	/**
	 * @return Returns the indBloqPlanIettus.
	 */
	public String getIndBloqPlanIettus() {
		return indBloqPlanIettus;
	}

	/**
	 * @param indBloqPlanIettus The indBloqPlanIettus to set.
	 */
	public void setIndBloqPlanIettus(String indBloqPlanIettus) {
		this.indBloqPlanIettus = indBloqPlanIettus;
	}

	/**
	 * @return Returns the indDesatMonitIettus.
	 */
	public String getIndDesatMonitIettus() {
		return indDesatMonitIettus;
	}

	/**
	 * @param indDesatMonitIettus The indDesatMonitIettus to set.
	 */
	public void setIndDesatMonitIettus(String indDesatMonitIettus) {
		this.indDesatMonitIettus = indDesatMonitIettus;
	}

	/**
	 * @return Returns the indDesblPlanIettus.
	 */
	public String getIndDesblPlanIettus() {
		return indDesblPlanIettus;
	}

	/**
	 * @param indDesblPlanIettus The indDesblPlanIettus to set.
	 */
	public void setIndDesblPlanIettus(String indDesblPlanIettus) {
		this.indDesblPlanIettus = indDesblPlanIettus;
	}

	/**
	 * @return Returns the indEmitePosIettus.
	 */
	public String getIndEmitePosIettus() {
		return indEmitePosIettus;
	}

	/**
	 * @param indEmitePosIettus The indEmitePosIettus to set.
	 */
	public void setIndEmitePosIettus(String indEmitePosIettus) {
		this.indEmitePosIettus = indEmitePosIettus;
	}

	/**
	 * @return Returns the indExcluirIettus.
	 */
	public String getIndExcluirIettus() {
		return indExcluirIettus;
	}

	/**
	 * @param indExcluirIettus The indExcluirIettus to set.
	 */
	public void setIndExcluirIettus(String indExcluirIettus) {
		this.indExcluirIettus = indExcluirIettus;
	}

	/**
	 * @return Returns the indInfAndamentoIettus.
	 */
	public String getIndInfAndamentoIettus() {
		return indInfAndamentoIettus;
	}



	/**
	 * @param indInfAndamentoIettus The indInfAndamentoIettus to set.
	 */
	public void setIndInfAndamentoIettus(String indInfAndamentoIettus) {
		this.indInfAndamentoIettus = indInfAndamentoIettus;
	}



	/**
	 * @return Returns the indLeituraIettus.
	 */
	public String getIndLeituraIettus() {
		return indLeituraIettus;
	}

	/**
	 * @param indLeituraIettus The indLeituraIettus to set.
	 */
	public void setIndLeituraIettus(String indLeituraIettus) {
		this.indLeituraIettus = indLeituraIettus;
	}

    /**
     * @return Returns the indEdicaoIettus.
     */
    public String getIndEdicaoIettus() {
        return indEdicaoIettus;
    }
    /**
     * @param indEdicaoIettus The indEdicaoIettus to set.
     */
    public void setIndEdicaoIettus(String indEdicaoIettus) {
        this.indEdicaoIettus = indEdicaoIettus;
    }
	/**
	 * @return Returns the indProxNivelIettus.
	 */
	public String getIndProxNivelIettus() {
		return indProxNivelIettus;
	}



	/**
	 * @param indProxNivelIettus The indProxNivelIettus to set.
	 */
	public void setIndProxNivelIettus(String indProxNivelIettus) {
		this.indProxNivelIettus = indProxNivelIettus;
	}



	/**
	 * @return Returns the itemEstruturaIett.
	 */
	public ecar.pojo.ItemEstruturaIett getItemEstruturaIett() {
		return itemEstruturaIett;
	}



	/**
	 * @param itemEstruturaIett The itemEstruturaIett to set.
	 */
	public void setItemEstruturaIett(ecar.pojo.ItemEstruturaIett itemEstruturaIett) {
		this.itemEstruturaIett = itemEstruturaIett;
	}



	/**
	 * @return Returns the itemEstruturaIettOrigem.
	 */
	public ecar.pojo.ItemEstruturaIett getItemEstruturaIettOrigem() {
		return itemEstruturaIettOrigem;
	}



	/**
	 * @param itemEstruturaIettOrigem The itemEstruturaIettOrigem to set.
	 */
	public void setItemEstruturaIettOrigem(
			ecar.pojo.ItemEstruturaIett itemEstruturaIettOrigem) {
		this.itemEstruturaIettOrigem = itemEstruturaIettOrigem;
	}



	/**
	 * @return Returns the sisAtributoSatb.
	 */
	public ecar.pojo.SisAtributoSatb getSisAtributoSatb() {
		return sisAtributoSatb;
	}



	/**
         * @param sisAtribtuoSatb
	 */
	public void setSisAtributoSatb(ecar.pojo.SisAtributoSatb sisAtribtuoSatb) {
		this.sisAtributoSatb = sisAtribtuoSatb;
	}



	/**
	 * @return Returns the tipoFuncAcompTpfa.
	 */
	public ecar.pojo.TipoFuncAcompTpfa getTipoFuncAcompTpfa() {
		return tipoFuncAcompTpfa;
	}



	/**
	 * @param tipoFuncAcompTpfa The tipoFuncAcompTpfa to set.
	 */
	public void setTipoFuncAcompTpfa(ecar.pojo.TipoFuncAcompTpfa tipoFuncAcompTpfa) {
		this.tipoFuncAcompTpfa = tipoFuncAcompTpfa;
	}



	/**
	 * @return Returns the usuarioUsu.
	 */
	public ecar.pojo.UsuarioUsu getUsuarioUsu() {
		return usuarioUsu;
	}



	/**
	 * @param usuarioUsu The usuarioUsu to set.
	 */
	public void setUsuarioUsu(ecar.pojo.UsuarioUsu usuarioUsu) {
		this.usuarioUsu = usuarioUsu;
	}



    @Override
	public String toString() {
        return new ToStringBuilder(this)
            .append("codIettus", getCodIettus())
            .toString();
    }

    @Override
    public boolean equals(Object other) {
        if ( (this == other ) ) return true;
        if ( !(other instanceof ItemEstrutUsuarioIettus) ) return false;
        ItemEstrutUsuarioIettus castOther = (ItemEstrutUsuarioIettus) other;
        /*
        return new EqualsBuilder()
        	.append(this.getCodIettus(), castOther.getCodIettus())
            .append(this.getItemEstruturaIett(), castOther.getItemEstruturaIett())
			.append(this.getItemEstruturaIettOrigem(), castOther.getItemEstruturaIettOrigem())
            .append(this.getUsuarioUsu(), castOther.getUsuarioUsu())
            .append(this.getSisAtributoSatb(), castOther.getSisAtributoSatb())
            .append(this.getTipoFuncAcompTpfa(), castOther.getTipoFuncAcompTpfa())
            .isEquals();
        */
        return new EqualsBuilder()
	        .append(this.getItemEstruturaIett(), castOther.getItemEstruturaIett())
			.append(this.getItemEstruturaIettOrigem(), castOther.getItemEstruturaIettOrigem())
	        .append(this.getTipoFuncAcompTpfa(), castOther.getTipoFuncAcompTpfa())
	        .append(this.getCodTpPermIettus(), castOther.getTipoFuncAcompTpfa())
	        .append(this.getUsuarioUsu(), castOther.getUsuarioUsu())
	        .append(this.getSisAtributoSatb(), castOther.getSisAtributoSatb())
	        .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
            .append(getCodIettus())
            .toHashCode();
    }

    /**
     *
     * @return
     */
    public UsuarioUsu getUsuManutencao() {
		return usuManutencao;
	}

    /**
     *
     * @param usuManutencao
     */
    public void setUsuManutencao(UsuarioUsu usuManutencao) {
		this.usuManutencao = usuManutencao;
	}
	
	/**
	 * Todas as propriedades primitivas ou imutáveis como String são automaticamente copiadas.
	 * O método clone deverá inicilizar apenas as propriedades mutáveis. 
	 * @author carlos
	 * @since 19/08/2007
	 * @return Object
	 */
    @Override
	public Object clone() {
		
		try
		{
			ItemEstrutUsuarioIettus clone = (ItemEstrutUsuarioIettus) super.clone();
			
			clone.setSisAtributoSatb(this.getSisAtributoSatb());
			clone.setDataInclusaoIettus(this.getDataInclusaoIettus());
			clone.setItemEstruturaIett(this.getItemEstruturaIett());
			clone.setItemEstruturaIettOrigem(this.getItemEstruturaIettOrigem());
			clone.setTipoFuncAcompTpfa(this.getTipoFuncAcompTpfa());
			clone.setUsuarioUsu(this.getUsuarioUsu());
			clone.setUsuManutencao(this.getUsuManutencao());
			
			return clone;
		
		} catch (CloneNotSupportedException e) {
	      return null;
		}
	}
}
