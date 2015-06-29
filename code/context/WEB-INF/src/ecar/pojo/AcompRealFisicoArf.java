package ecar.pojo;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

import ecar.bean.IndResulExercicioBean;


/** 
 * Esta classe representa o valor realizado no per�odo de refer�ncia correspondente.
 * 
 * @author Hibernate CodeGenerator 
 * */
public class AcompRealFisicoArf implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = -1032766926564988389L;

    /** nullable persistent field */
    private Date dataInclusaoArf;

    /** nullable persistent field */
    private Double qtdRealizadaArf;

    /** nullable persistent field */
    private ecar.pojo.ItemEstruturaIett itemEstruturaIett;

    /** nullable persistent field */
    private ecar.pojo.ItemEstrtIndResulIettr itemEstrtIndResulIettr;
    
    private Set acompRealFisicoLocalArfls;

    /** persistent field */
    private ecar.pojo.SituacaoSit situacaoSit;
    
    /* Inseridos por Igor em 23/06/06 */
    private Long codArf;
    
    private Date dataUltManut;
    
    private Long mesArf;
    
    private Long anoArf;
    
    private UsuarioUsu usuarioUltManut;
    
    private String justificativaArf;
    
    private Long mesReferenciaArf;
    
    private Long anoReferenciaArf;
    
    private String indEmApuracaoArf;
        

    public Long getMesReferenciaArf() {
		return mesReferenciaArf;
	}

	public void setMesReferenciaArf(Long mesReferenciaArf) {
		this.mesReferenciaArf = mesReferenciaArf;
	}

	public Long getAnoReferenciaArf() {
		return anoReferenciaArf;
	}

	public void setAnoReferenciaArf(Long anoReferenciaArf) {
		this.anoReferenciaArf = anoReferenciaArf;
	}

	public String getIndEmApuracaoArf() {
		return indEmApuracaoArf;
	}

	public void setIndEmApuracaoArf(String indEmApuracaoArf) {
		this.indEmApuracaoArf = indEmApuracaoArf;
	}

	/** 
	 * full constructor.<br>
	 * 
	 * @author N/C
	 * @since N/C
	 * @param dataInclusaoArf
	 * @param qtdRealizadaArf
	 * @param itemEstruturaIett
	 * @param itemEstrtIndResulIettr
	 * @param situacaoSit
	 * @param mesArf
	 * @param dataUltManut 
	 * @param anoArf 
	 * @param usuarioUltManut
	 * @param acompRealFisicoLocalArfls
	 * @deprecated Use {@link #AcompRealFisicoArf(Date,Double,ecar.pojo.ItemEstruturaIett,ecar.pojo.ItemEstrtIndResulIettr,ecar.pojo.SituacaoSit,Long,Long,Date,UsuarioUsu,Set,Long,Long,String)} instead
	 */
	public AcompRealFisicoArf( Date dataInclusaoArf,
			Double qtdRealizadaArf, 
			ecar.pojo.ItemEstruturaIett itemEstruturaIett,
			ecar.pojo.ItemEstrtIndResulIettr itemEstrtIndResulIettr,
			ecar.pojo.SituacaoSit situacaoSit,
			Long mesArf,
			Long anoArf,
			Date dataUltManut,
			UsuarioUsu usuarioUltManut,
			Set acompRealFisicoLocalArfls) {
				this(dataInclusaoArf, qtdRealizadaArf, itemEstruturaIett,
						itemEstrtIndResulIettr, situacaoSit, mesArf, anoArf,
						dataUltManut, usuarioUltManut,
						acompRealFisicoLocalArfls, null, null,
						null);
			}

	/** 
     * full constructor.<br>
     * 
     * @author N/C
     * @since N/C
     * @param dataInclusaoArf
	 * @param qtdRealizadaArf
	 * @param itemEstruturaIett
	 * @param itemEstrtIndResulIettr
	 * @param situacaoSit
	 * @param mesArf
	 * @param anoArf 
	 * @param dataUltManut 
	 * @param usuarioUltManut
	 * @param acompRealFisicoLocalArfls
	 * @param mesReferenciaArf TODO
	 * @param anoReferenciaArf TODO
	 * @param indEmApuracaoArf TODO
     */
    public AcompRealFisicoArf( Date dataInclusaoArf,
    		Double qtdRealizadaArf, 
    		ecar.pojo.ItemEstruturaIett itemEstruturaIett,
    		ecar.pojo.ItemEstrtIndResulIettr itemEstrtIndResulIettr,
    		ecar.pojo.SituacaoSit situacaoSit,
    		Long mesArf,
    		Long anoArf,
    		Date dataUltManut,
    		UsuarioUsu usuarioUltManut,
    		Set acompRealFisicoLocalArfls, Long mesReferenciaArf, Long anoReferenciaArf, String indEmApuracaoArf) {
        this.dataInclusaoArf = dataInclusaoArf;
        this.qtdRealizadaArf = qtdRealizadaArf;
        this.itemEstruturaIett = itemEstruturaIett;
        this.itemEstrtIndResulIettr = itemEstrtIndResulIettr;
        this.situacaoSit = situacaoSit;
        this.mesArf = mesArf;
        this.anoArf = anoArf;
        this.dataUltManut = dataUltManut; 
        this.usuarioUltManut = usuarioUltManut;        
        this.acompRealFisicoLocalArfls = acompRealFisicoLocalArfls;
        this.mesReferenciaArf = mesReferenciaArf;
        this.anoReferenciaArf = anoReferenciaArf;
        this.indEmApuracaoArf = indEmApuracaoArf;
    }

    /** 
     * default constructor 
     * @author N/C
     * @since N/C
     */
    public AcompRealFisicoArf() {
    }

    /** 
     * minimal constructor.<br>
     * 
     * @param situacaoSit 
     * @param itemEstruturaIett 
     * @param usuarioUltManut
     * @param itemEstrtIndResulIettr
     * @param acompRealFisicoLocalArfls
     */
    public AcompRealFisicoArf(ecar.pojo.SituacaoSit situacaoSit, ItemEstruturaIett itemEstruturaIett,
    		ItemEstrtIndResulIettr itemEstrtIndResulIettr, 
    		UsuarioUsu usuarioUltManut, Set acompRealFisicoLocalArfls) {
        this.situacaoSit = situacaoSit;
        this.itemEstruturaIett = itemEstruturaIett;
        this.itemEstrtIndResulIettr = itemEstrtIndResulIettr;
        this.usuarioUltManut = usuarioUltManut;
        this.acompRealFisicoLocalArfls = acompRealFisicoLocalArfls;
    }
    
    /**
     * @author N/C
     * @since N/C
     * @return Date
     */
    public Date getDataInclusaoArf() {
        return this.dataInclusaoArf;
    }

    /**
     * @param dataInclusaoArf
     * @author N/C
     * @since N/C
     */
    public void setDataInclusaoArf(Date dataInclusaoArf) {
        this.dataInclusaoArf = dataInclusaoArf;
    }

    /**
     * @author N/C
     * @since N/C
     * @return Double
     */
    public Double getQtdRealizadaArf() {
        return this.qtdRealizadaArf;
    }

    /**
     * @param qtdRealizadaArf
     * @author N/C
     * @since N/C
     */
    public void setQtdRealizadaArf(Double qtdRealizadaArf) {
        this.qtdRealizadaArf = qtdRealizadaArf;
    }

    /**
     * @author N/C
     * @since N/C
     * @return ecar.pojo.ItemEstruturaIett
     */
    public ecar.pojo.ItemEstruturaIett getItemEstruturaIett() {
        return this.itemEstruturaIett;
    }

    /**
     * @param itemEstruturaIett
     * @author N/C
     * @since N/C
     */
    public void setItemEstruturaIett(ecar.pojo.ItemEstruturaIett itemEstruturaIett) {
        this.itemEstruturaIett = itemEstruturaIett;
    }

    /**
     * @author N/C
     * @since N/C
     * @return ecar.pojo.ItemEstrtIndResulIettr
     */
    public ecar.pojo.ItemEstrtIndResulIettr getItemEstrtIndResulIettr() {
        return this.itemEstrtIndResulIettr;
    }

    /**
     * @author N/C
     * @since N/C
     * @param itemEstrtIndResulIettr
     */
    public void setItemEstrtIndResulIettr(ecar.pojo.ItemEstrtIndResulIettr itemEstrtIndResulIettr) {
        this.itemEstrtIndResulIettr = itemEstrtIndResulIettr;
    }

    /**
     * @author N/C
     * @since N/C
     * @return ecar.pojo.SituacaoSit
     */
    public ecar.pojo.SituacaoSit getSituacaoSit() {
        return this.situacaoSit;
    }

    /**
     * @param situacaoSit
     * @author N/C
     * @since N/C
     */
    public void setSituacaoSit(ecar.pojo.SituacaoSit situacaoSit) {
        this.situacaoSit = situacaoSit;
    }

    /**
     * @author N/C
     * @since N/C
     * @return String
     */
    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .append("codArf", getCodArf())
            .toString();
    }

    /**
     * @author N/C
     * @since N/C
     * @param other
     * @return boolean
     */
    @Override
    public boolean equals(Object other) {
        if ( (this == other ) ) return true;
        if ( !(other instanceof AcompRealFisicoArf) ) return false;
        AcompRealFisicoArf castOther = (AcompRealFisicoArf) other;
        return new EqualsBuilder()
            .append(this.getCodArf(), castOther.getCodArf())
            .isEquals();
    }

    /**
     * @author N/C
     * @since N/C
     * @return int
     */
    @Override
    public int hashCode() {
        return new HashCodeBuilder()
            .append(getCodArf())
            .toHashCode();
    }

    /**
     * @author N/C
     * @since N/C
     * @return Long
     */
	public Long getAnoArf() {
		return anoArf;
	}

	/**
	 * @author N/C
     * @since N/C
         * @param anoArf
	 */
	public void setAnoArf(Long anoArf) {
		this.anoArf = anoArf;
	}

	/**
	 * @author N/C
     * @since N/C
	 * @return Long
	 */
	public Long getCodArf() {
		return codArf;
	}

	/**
         * @param codArf
         * @author N/C
     * @since N/C
	 */
	public void setCodArf(Long codArf) {
		this.codArf = codArf;
	}

	/**
	 * @author N/C
     * @since N/C
	 * @return Date
	 */
	public Date getDataUltManut() {
		return dataUltManut;
	}

	/**
         * @param dataUltManut
         * @author N/C
     * @since N/C
	 */
	public void setDataUltManut(Date dataUltManut) {
		this.dataUltManut = dataUltManut;
	}

	/**
	 * @author N/C
     * @since N/C
	 * @return Long
	 */
	public Long getMesArf() {
		return mesArf;
	}

	/**
	 * @author N/C
     * @since N/C
         * @param mesArf
	 */
	public void setMesArf(Long mesArf) {
		this.mesArf = mesArf;
	}

	/**
	 * @author N/C
     * @since N/C
	 * @return UsuarioUsu
	 */
	public UsuarioUsu getUsuarioUltManut() {
		return usuarioUltManut;
	}

	/**
	 * @author N/C
     * @since N/C
         * @param usuarioUltManut
	 */
	public void setUsuarioUltManut(UsuarioUsu usuarioUltManut) {
		this.usuarioUltManut = usuarioUltManut;
	}

	/**
	 * @author N/C
     * @since N/C
	 * @return Set
	 */
	public Set getAcompRealFisicoLocalArfls() {
		return acompRealFisicoLocalArfls;
	}

	/**
         * @param acompRealFisicoLocalArfls
         * @author N/C
     * @since N/C
	 */
	public void setAcompRealFisicoLocalArfls(Set acompRealFisicoLocalArfls) {
		this.acompRealFisicoLocalArfls = acompRealFisicoLocalArfls;
	}
	
	public String getJustificativaArf() {
		return justificativaArf;
	}

	public void setJustificativaArf(String justificativaArf) {
		this.justificativaArf = justificativaArf;
	}
	
	public String getMesAnoReferencia(){
		if(this.mesReferenciaArf!=null&&this.anoReferenciaArf!=null){
			return String.format("%02d", this.mesReferenciaArf)+"-"+this.anoReferenciaArf.toString();
		}
		else
			return "";
	}
}
