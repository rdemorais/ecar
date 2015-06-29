package ecar.pojo;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;


/** @author Hibernate CodeGenerator */
public class EspecieEsp implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1170958740098484355L;

	/** identifier field */
    private Long codEsp;

    /** nullable persistent field */
    private String indAtivoEsp;

    /** nullable persistent field */
    private Date dataInclusaoEsp;

    /** nullable persistent field */
    private String descricaoEsp;

    private Set efItemEstPrevisaoEfieps;

    /** full constructor
     * @param codEsp
     * @param indAtivoEsp
     * @param dataInclusaoEsp
     * @param descricaoEsp
     */
    public EspecieEsp(Long codEsp, String indAtivoEsp, Date dataInclusaoEsp, String descricaoEsp) {
    	this.codEsp = codEsp;
        this.indAtivoEsp = indAtivoEsp;
        this.dataInclusaoEsp = dataInclusaoEsp;
        this.descricaoEsp = descricaoEsp;
    }

    /** default constructor */
    public EspecieEsp() {
    }

	/**
	 * @return the codEsp
	 */
	public Long getCodEsp() {
		return codEsp;
	}

	/**
	 * @param codEsp the codEsp to set
	 */
	public void setCodEsp(Long codEsp) {
		this.codEsp = codEsp;
	}

	/**
	 * @return the dataInclusaoEsp
	 */
	public Date getDataInclusaoEsp() {
		return dataInclusaoEsp;
	}

	/**
	 * @param dataInclusaoEsp the dataInclusaoEsp to set
	 */
	public void setDataInclusaoEsp(Date dataInclusaoEsp) {
		this.dataInclusaoEsp = dataInclusaoEsp;
	}

	/**
	 * @return the descricaoEsp
	 */
	public String getDescricaoEsp() {
		return descricaoEsp;
	}

	/**
	 * @param descricaoEsp the descricaoEsp to set
	 */
	public void setDescricaoEsp(String descricaoEsp) {
		this.descricaoEsp = descricaoEsp;
	}

	/**
	 * @return the indAtivoEsp
	 */
	public String getIndAtivoEsp() {
		return indAtivoEsp;
	}

	/**
	 * @param indAtivoEsp the indAtivoEsp to set
	 */
	public void setIndAtivoEsp(String indAtivoEsp) {
		this.indAtivoEsp = indAtivoEsp;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int PRIME = 31;
		int result = 1;
		result = PRIME * result + ((codEsp == null) ? 0 : codEsp.hashCode());
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		final EspecieEsp other = (EspecieEsp) obj;
		if (codEsp == null) {
			if (other.codEsp != null)
				return false;
		} else if (!codEsp.equals(other.codEsp))
			return false;
		return true;
	}

	/**
	 * @return the efItemEstPrevisaoEfieps
	 */
	public Set getEfItemEstPrevisaoEfieps() {
		return efItemEstPrevisaoEfieps;
	}

	/**
	 * @param efItemEstPrevisaoEfieps the efItemEstPrevisaoEfieps to set
	 */
	public void setEfItemEstPrevisaoEfieps(Set efItemEstPrevisaoEfieps) {
		this.efItemEstPrevisaoEfieps = efItemEstPrevisaoEfieps;
	}

}
