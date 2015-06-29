package ecar.pojo;

import java.io.Serializable;
import java.util.Set;


/** @author Hibernate CodeGenerator */
public class FonteFon implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3436758459800825209L;

	private Long codFon;
	
	private Long codigoIdentFon;
	
	private String nomeFon;
	
	private String indAtivoFon;
	
	private ecar.pojo.RecursoRec recursoRec;
	
	private Set efItemEstPrevisaoEfieps;
	
	/**
	 * Default Constructor
	 */
	public FonteFon(){
	}

	/**
	 * @return the codFon
	 */
	public Long getCodFon() {
		return codFon;
	}

	/**
	 * @param codFon the codFon to set
	 */
	public void setCodFon(Long codFon) {
		this.codFon = codFon;
	}

	/**
	 * @return the codigoIdentFon
	 */
	public Long getCodigoIdentFon() {
		return codigoIdentFon;
	}

	/**
	 * @param codigoIdentFon the codigoIdentFon to set
	 */
	public void setCodigoIdentFon(Long codigoIdentFon) {
		this.codigoIdentFon = codigoIdentFon;
	}

	/**
	 * @return the indAtivoFon
	 */
	public String getIndAtivoFon() {
		return indAtivoFon;
	}

	/**
	 * @param indAtivoFon the indAtivoFon to set
	 */
	public void setIndAtivoFon(String indAtivoFon) {
		this.indAtivoFon = indAtivoFon;
	}

	/**
	 * @return the nomeFon
	 */
	public String getNomeFon() {
		return nomeFon;
	}

	/**
	 * @param nomeFon the nomeFon to set
	 */
	public void setNomeFon(String nomeFon) {
		this.nomeFon = nomeFon;
	}

	/**
	 * @return the recursoRec
	 */
	public ecar.pojo.RecursoRec getRecursoRec() {
		return recursoRec;
	}

	/**
	 * @param recursoRec the recursoRec to set
	 */
	public void setRecursoRec(ecar.pojo.RecursoRec recursoRec) {
		this.recursoRec = recursoRec;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int PRIME = 31;
		int result = 1;
		result = PRIME * result + ((codFon == null) ? 0 : codFon.hashCode());
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
		final FonteFon other = (FonteFon) obj;
		if (codFon == null) {
			if (other.codFon != null)
				return false;
		} else if (!codFon.equals(other.codFon))
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
