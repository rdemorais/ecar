package ecar.pojo;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

import javax.xml.bind.annotation.XmlRootElement;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;


/** @author Hibernate CodeGenerator */
@XmlRootElement
public class ExercicioExe implements Serializable, Comparable<ExercicioExe> {

    /**
	 * 
	 */
	private static final long serialVersionUID = 3728603026839574773L;

	/** identifier field */
    private Long codExe;

    /** nullable persistent field */
    private Date dataFinalExe;

    /** nullable persistent field */
    private Date dataInicialExe;

    /** nullable persistent field */
    private String descricaoExe;
    
    private Boolean cicloCorrenteExe;
    

	public Boolean getCicloCorrenteExe() {
		return cicloCorrenteExe;
	}

	public void setCicloCorrenteExe(Boolean cicloCorrenteExe) {
		this.cicloCorrenteExe = cicloCorrenteExe;
	}

	/** persistent field */
    private Set efItemEstContaEfiecs;

    /** persistent field */
    private Set efItemEstPrevisaoEfieps;

    /** persistent field */
    private Set itemEstrutFisicoIettfs;

    /* garten em 24/02/2005 */
    private Set acompReferenciaArefs;
    
    /* Igor */
    private Set itemEstFisicoRevIettfrs;
    
    private Set efIettPrevisaoRevEfieprs;
    
    private ecar.pojo.PeriodoExercicioPerExe periodoExercicioPerExe;
    
    /* Mantis #2156 */
    private Set historicoIettfHs;
    private Set historicoEfiecHs;
    private Set historicoEfiepHs;

    private Set itemEstrtIndResulLocalIettirls;
    /**
     *
     * @return
     */
    public Set getHistoricoEfiepHs() {
		return historicoEfiepHs;
	}

    /**
     *
     * @param historicoEfiepHs
     */
    public void setHistoricoEfiepHs(Set historicoEfiepHs) {
		this.historicoEfiepHs = historicoEfiepHs;
	}

        /**
         *
         * @return
         */
        public Set getHistoricoEfiecHs() {
		return historicoEfiecHs;
	}

        /**
         *
         * @param historicoEfiecHs
         */
        public void setHistoricoEfiecHs(Set historicoEfiecHs) {
		this.historicoEfiecHs = historicoEfiecHs;
	}

        /**
         *
         * @return
         */
        public Set getHistoricoIettfHs() {
		return historicoIettfHs;
	}

        /**
         *
         * @param historicoIettfHs
         */
        public void setHistoricoIettfHs(Set historicoIettfHs) {
		this.historicoIettfHs = historicoIettfHs;
	}

    /** default constructor */
    public ExercicioExe() {
    }
    
    public ExercicioExe(Long codExe, Date dataFinalExe, Date dataInicialExe, String descricaoExe) {
    	
    	this.codExe = codExe;
    	this.dataFinalExe = dataFinalExe;
    	this.dataInicialExe = dataInicialExe;
    	this.descricaoExe = descricaoExe;
    }

    /**
     *
     * @return
     */
    public Long getCodExe() {
        return this.codExe;
    }

    /**
     *
     * @param codExe
     */
    public void setCodExe(Long codExe) {
        this.codExe = codExe;
    }

    /**
     *
     * @return
     */
    public Date getDataFinalExe() {
        return this.dataFinalExe;
    }

    /**
     *
     * @param dataFinalExe
     */
    public void setDataFinalExe(Date dataFinalExe) {
        this.dataFinalExe = dataFinalExe;
    }

    /**
     *
     * @return
     */
    public Date getDataInicialExe() {
        return this.dataInicialExe;
    }

    /**
     *
     * @param dataInicialExe
     */
    public void setDataInicialExe(Date dataInicialExe) {
        this.dataInicialExe = dataInicialExe;
    }

    /**
     *
     * @return
     */
    public String getDescricaoExe() {
        return this.descricaoExe;
    }

    /**
     *
     * @param descricaoExe
     */
    public void setDescricaoExe(String descricaoExe) {
        this.descricaoExe = descricaoExe;
    }

    /**
     *
     * @return
     */
    public Set getEfItemEstContaEfiecs() {
        return this.efItemEstContaEfiecs;
    }

    /**
     *
     * @param efItemEstContaEfiecs
     */
    public void setEfItemEstContaEfiecs(Set efItemEstContaEfiecs) {
        this.efItemEstContaEfiecs = efItemEstContaEfiecs;
    }

    /**
     *
     * @return
     */
    public Set getEfItemEstPrevisaoEfieps() {
        return this.efItemEstPrevisaoEfieps;
    }

    /**
     *
     * @param efItemEstPrevisaoEfieps
     */
    public void setEfItemEstPrevisaoEfieps(Set efItemEstPrevisaoEfieps) {
        this.efItemEstPrevisaoEfieps = efItemEstPrevisaoEfieps;
    }

    /**
     *
     * @return
     */
    public Set getItemEstrutFisicoIettfs() {
        return this.itemEstrutFisicoIettfs;
    }

    /**
     *
     * @param itemEstrutFisicoIettfs
     */
    public void setItemEstrutFisicoIettfs(Set itemEstrutFisicoIettfs) {
        this.itemEstrutFisicoIettfs = itemEstrutFisicoIettfs;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .append("codExe", getCodExe())
            .toString();
    }

    @Override
    public boolean equals(Object other) {
        if ( (this == other ) ) return true;
        if ( !(other instanceof ExercicioExe) ) return false;
        ExercicioExe castOther = (ExercicioExe) other;
        return new EqualsBuilder()
            .append(this.getCodExe(), castOther.getCodExe())
            .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
            .append(getCodExe())
            .toHashCode();
    }

    /**
     * @return Returns the acompReferenciaArefs.
     */
    public Set getAcompReferenciaArefs() {
        return acompReferenciaArefs;
    }
    /**
     * @param acompReferenciaArefs The acompReferenciaArefs to set.
     */
    public void setAcompReferenciaArefs(Set acompReferenciaArefs) {
        this.acompReferenciaArefs = acompReferenciaArefs;
    }

    /**
     *
     * @return
     */
    public Set getItemEstFisicoRevIettfrs() {
		return itemEstFisicoRevIettfrs;
	}

    /**
     *
     * @param itemEstFisicoRevIettfrs
     */
    public void setItemEstFisicoRevIettfrs(Set itemEstFisicoRevIettfrs) {
		this.itemEstFisicoRevIettfrs = itemEstFisicoRevIettfrs;
	}

        /**
         *
         * @return
         */
        public Set getEfIettPrevisaoRevEfieprs() {
		return efIettPrevisaoRevEfieprs;
	}

        /**
         *
         * @param efIettPrevisaoRevEfieprs
         */
        public void setEfIettPrevisaoRevEfieprs(Set efIettPrevisaoRevEfieprs) {
		this.efIettPrevisaoRevEfieprs = efIettPrevisaoRevEfieprs;
	}

        /**
         *
         * @return
         */
        public ecar.pojo.PeriodoExercicioPerExe getPeriodoExercicioPerExe() {
		return periodoExercicioPerExe;
	}

        /**
         *
         * @param periodoExercicioPerExe
         */
        public void setPeriodoExercicioPerExe(
			ecar.pojo.PeriodoExercicioPerExe periodoExercicioPerExe) {
		this.periodoExercicioPerExe = periodoExercicioPerExe;
	}

	/**
	 * @return the itemEstrtIndResulLocalIettirls
	 */
	public Set getItemEstrtIndResulLocalIettirls() {
		return itemEstrtIndResulLocalIettirls;
	}

	/**
	 * @param itemEstrtIndResulLocalIettirls the itemEstrtIndResulLocalIettirls to set
	 */
	public void setItemEstrtIndResulLocalIettirls(Set itemEstrtIndResulLocalIettirls) {
		this.itemEstrtIndResulLocalIettirls = itemEstrtIndResulLocalIettirls;
	}

	/**
	 * 
	 */
	public int compareTo(ExercicioExe o) {
		if(this.getCodExe() < o.getCodExe())
			return -1;
		else if(this.getCodExe() > o.getCodExe())
			return 1;
		return 0;

	}
	

}
