package ecar.pojo;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;


/** 
 * Esta classe � o resultado de um relacionamento conceitual entre {@link ItemEstruturaIett} e {@link AcompReferenciaAref}.
 * Aqui, cada item a ser monitorado ganha uma configura��o espec�fica, como data de inicio da elabora��o etc. Estas datas
 * s�o definidas no momento da gera��o do per�odo, mas podem ser atualizadas para cada item posteriormente, desta forma,
 * abre-se a possibilidade de dar mais um prazo para um item espec�fico sem a necessidade de abrir o sistema todo. 
 * 
 * @author Hibernate CodeGenerator 
 * */
public class AcompReferenciaItemAri implements Serializable {


	private static final long serialVersionUID = -5092130621134748898L;

	/** identifier field */
    private Long codAri;

    /** nullable persistent field */
    private Date dataUltManutAri;

    /** nullable persistent field */
    private Date dataInclusaoAri;

    /** nullable persistent field */
    private Date dataLimiteAcompFisicoAri;

    /** nullable persistent field */
    private Date dataInicioAri;

    /** nullable persistent field */
    private String indItemMonitoradosAri;

    /** nullable persistent field */
    private Long codUsuincAri;

    /** nullable persistent field */
    private Long codUsuUltManutAri;

    /** persistent field */
    private ecar.pojo.StatusRelatorioSrl statusRelatorioSrl;

    /** persistent field */
    private ecar.pojo.ItemEstruturaIett itemEstruturaIett;

    /** persistent field */
    private ecar.pojo.AcompReferenciaAref acompReferenciaAref;

    /** persistent field */
    private Set acompRelatorioArels;

    /** persistent field */
    private Set acompRefItemLimitesArlis;
    
    /** persistent field */
    private Set itemEstrUplCategIettuc;

    /** 
     * full constructor.<br>
     * 
     * @author N/C
     * @since N/C
     * @param dataUltManutAri 
     * @param dataInclusaoAri
     * @param dataLimiteAcompFisicoAri
     * @param dataInicioAri
     * @param indItemMonitoradosAri
     * @param codUsuincAri
     * @param codUsuUltManutAri
     * @param statusRelatorioSrl
     * @param itemEstruturaIett 
     * @param acompReferenciaAref
     * @param acompRefItemLimitesArlis 
     * @param acompRelatorioArels
     * @param itemEstrUplCategIettuc
     */
    public AcompReferenciaItemAri(Date dataUltManutAri, Date dataInclusaoAri, 
    		Date dataLimiteAcompFisicoAri, Date dataInicioAri, String indItemMonitoradosAri, 
    		Long codUsuincAri, Long codUsuUltManutAri, ecar.pojo.StatusRelatorioSrl statusRelatorioSrl, 
    		ecar.pojo.ItemEstruturaIett itemEstruturaIett, ecar.pojo.AcompReferenciaAref acompReferenciaAref, 
    		Set acompRelatorioArels, Set acompRefItemLimitesArlis, Set itemEstrUplCategIettuc) {
        this.dataUltManutAri = dataUltManutAri;
        this.dataInclusaoAri = dataInclusaoAri;
        this.dataLimiteAcompFisicoAri = dataLimiteAcompFisicoAri;
        this.dataInicioAri = dataInicioAri;
        this.indItemMonitoradosAri = indItemMonitoradosAri;
        this.codUsuincAri = codUsuincAri;
        this.codUsuUltManutAri = codUsuUltManutAri;
        this.statusRelatorioSrl = statusRelatorioSrl;
        this.itemEstruturaIett = itemEstruturaIett;
        this.acompReferenciaAref = acompReferenciaAref;
        this.acompRelatorioArels = acompRelatorioArels;
        this.acompRefItemLimitesArlis = acompRefItemLimitesArlis;
        this.itemEstrUplCategIettuc = itemEstrUplCategIettuc;
    }

    /** default constructor */
    public AcompReferenciaItemAri() {
    }

    /** 
     * minimal constructor.<br>
     * 
     * @param statusRelatorioSrl 
     * @param itemEstruturaIett 
     * @param acompRealFisicoArfs
     * @param acompRelatorioArels
     * @param acompRefItemLimitesArlis
     * @param acompReferenciaAref
     * @author N/C
     * @since N/C
     */
    public AcompReferenciaItemAri(ecar.pojo.StatusRelatorioSrl statusRelatorioSrl, 
    		ecar.pojo.ItemEstruturaIett itemEstruturaIett, ecar.pojo.AcompReferenciaAref acompReferenciaAref, 
    		Set acompRelatorioArels, Set acompRefItemLimitesArlis, Set acompRealFisicoArfs) {
        this.statusRelatorioSrl = statusRelatorioSrl;
        this.itemEstruturaIett = itemEstruturaIett;
        this.acompReferenciaAref = acompReferenciaAref;
        this.acompRelatorioArels = acompRelatorioArels;
        this.acompRefItemLimitesArlis = acompRefItemLimitesArlis;
    }

    /**
     * @author N/C
     * @since N/C
     * @return Long
     */
    public Long getCodAri() {
        return this.codAri;
    }

    /**
     * @author N/C
     * @since N/C
     * @param codAri
     */
    public void setCodAri(Long codAri) {
        this.codAri = codAri;
    }

    /**
     * @author N/C
     * @since N/C
     * @return Date
     */
    public Date getDataUltManutAri() {
        return this.dataUltManutAri;
    }

    /**
     * @author N/C
     * @since N/C
     * @param dataUltManutAri
     */
    public void setDataUltManutAri(Date dataUltManutAri) {
        this.dataUltManutAri = dataUltManutAri;
    }

    /**
     * @author N/C
     * @since N/C
     * @return Date
     */
    public Date getDataInclusaoAri() {
        return this.dataInclusaoAri;
    }

    /**
     * @author N/C
     * @since N/C
     * @param dataInclusaoAri
     */
    public void setDataInclusaoAri(Date dataInclusaoAri) {
        this.dataInclusaoAri = dataInclusaoAri;
    }

    /**
     * @author N/C
     * @since N/C
     * @return Date
     */
    public Date getDataLimiteAcompFisicoAri() {
        return this.dataLimiteAcompFisicoAri;
    }

    /**
     * @author N/C
     * @since N/C
     * @param dataLimiteAcompFisicoAri
     */
    public void setDataLimiteAcompFisicoAri(Date dataLimiteAcompFisicoAri) {
        this.dataLimiteAcompFisicoAri = dataLimiteAcompFisicoAri;
    }

    /**
     * @author N/C
     * @since N/C
     * @return Date
     */
    public Date getDataInicioAri() {
        return this.dataInicioAri;
    }

    /**
     * @param dataInicioAri
     * @author N/C
     * @since N/C
     */
    public void setDataInicioAri(Date dataInicioAri) {
        this.dataInicioAri = dataInicioAri;
    }

    /**
     * @author N/C
     * @since N/C
     * @return String
     */
    public String getIndItemMonitoradosAri() {
        return this.indItemMonitoradosAri;
    }

    /**
     * @author N/C
     * @since N/C
     * @param indItemMonitoradosAri
     */
    public void setIndItemMonitoradosAri(String indItemMonitoradosAri) {
        this.indItemMonitoradosAri = indItemMonitoradosAri;
    }

    /**
     * @author N/C
     * @since N/C
     * @return Long
     */
    public Long getCodUsuincAri() {
        return this.codUsuincAri;
    }

    /**
     * @param codUsuincAri
     * @author N/C
     * @since N/C
     */
    public void setCodUsuincAri(Long codUsuincAri) {
        this.codUsuincAri = codUsuincAri;
    }

    /**
     * @author N/C
     * @since N/C
     * @return Long
     */
    public Long getCodUsuUltManutAri() {
        return this.codUsuUltManutAri;
    }

    /**
     * @param codUsuUltManutAri
     * @author N/C
     * @since N/C
     */
    public void setCodUsuUltManutAri(Long codUsuUltManutAri) {
        this.codUsuUltManutAri = codUsuUltManutAri;
    }

    /**
     * @author N/C
     * @since N/C
     * @return ecar.pojo.StatusRelatorioSrl
     */
    public ecar.pojo.StatusRelatorioSrl getStatusRelatorioSrl() {
        return this.statusRelatorioSrl;
    }

    /**
     * @author N/C
     * @since N/C
     * @param statusRelatorioSrl
     */
    public void setStatusRelatorioSrl(ecar.pojo.StatusRelatorioSrl statusRelatorioSrl) {
        this.statusRelatorioSrl = statusRelatorioSrl;
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
     * @author N/C
     * @since N/C
     * @param itemEstruturaIett
     */
    public void setItemEstruturaIett(ecar.pojo.ItemEstruturaIett itemEstruturaIett) {
        this.itemEstruturaIett = itemEstruturaIett;
    }

    /**
     * @author N/C
     * @since N/C
     * @return ecar.pojo.AcompReferenciaAref
     */
    public ecar.pojo.AcompReferenciaAref getAcompReferenciaAref() {
        return this.acompReferenciaAref;
    }

    /**
     * @author N/C
     * @since N/C
     * @param acompReferenciaAref
     */
    public void setAcompReferenciaAref(ecar.pojo.AcompReferenciaAref acompReferenciaAref) {
        this.acompReferenciaAref = acompReferenciaAref;
    }

    /**
     * @author N/C
     * @since N/C
     * @return Set
     */
    public Set getAcompRelatorioArels() {
        return this.acompRelatorioArels;
    }

    /**
     * @author N/C
     * @since N/C
     * @param acompRelatorioArels
     */
    public void setAcompRelatorioArels(Set acompRelatorioArels) {
        this.acompRelatorioArels = acompRelatorioArels;
    }

    /**
     * @author N/C
     * @since N/C
     * @return Set
     */
    public Set getAcompRefItemLimitesArlis() {
        return this.acompRefItemLimitesArlis;
    }

    /**
     * @author N/C
     * @since N/C
     * @param acompRefItemLimitesArlis
     */
    public void setAcompRefItemLimitesArlis(Set acompRefItemLimitesArlis) {
        this.acompRefItemLimitesArlis = acompRefItemLimitesArlis;
    }
    
    /**
     * @author N/C
     * @since N/C
     * @return Set
	*/
	public Set getItemEstrUplCategIettuc() {
		return itemEstrUplCategIettuc;
	}

    /**
     * @author N/C
     * @since N/C
     * @param itemEstrUplCategIettuc
	*/
	public void setItemEstrUplCategIettuc(Set itemEstrUplCategIettuc) {
		this.itemEstrUplCategIettuc = itemEstrUplCategIettuc;
	}

	/**
	 * @author gustavo.edin
	 * @since 2013-08-29
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((codAri == null) ? 0 : codAri.hashCode());
		return result;
	}

	/**
	 * @author gustavo.edin
	 * @since 2013-08-29
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof AcompReferenciaItemAri)) {
			return false;
		}
		AcompReferenciaItemAri other = (AcompReferenciaItemAri) obj;
		if (codAri == null) {
			if (other.codAri != null) {
				return false;
			}
		} else if (!codAri.equals(other.codAri)) {
			return false;
		}
		return true;
	}

	/**
	 * @author gustavo.edin
	 * @since 2013-08-29
	 */
	@Override
	public String toString() {
		return "AcompReferenciaItemAri [codAri=" + codAri + "]";
	}

}
