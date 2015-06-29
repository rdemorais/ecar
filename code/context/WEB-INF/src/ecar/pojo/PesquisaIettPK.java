package ecar.pojo;

import java.io.Serializable;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

public class PesquisaIettPK implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7326333992212640928L;

	/**
	 * Código da pesquisa
	 */
	private Long codPesquisa;
	
	/**
	 * Código do item
	 */
	private Long codIett;
	
	/**
	 * Código da referência
	 */
	private Long codAref;

	/**
	 * @param codPesquisa
	 * @param codIett
	 * @param codAref
	 */
	public PesquisaIettPK(Long codPesquisa, Long codIett, Long codAref) {
		super();
		this.codPesquisa = codPesquisa;
		this.codIett = codIett;
		this.codAref = codAref;
	}

	/**
	 * 
	 */
	public PesquisaIettPK() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @return the codPesquisa
	 */
	public Long getCodPesquisa() {
		return codPesquisa;
	}

	/**
	 * @param codPesquisa the codPesquisa to set
	 */
	public void setCodPesquisa(Long codPesquisa) {
		this.codPesquisa = codPesquisa;
	}

	/**
	 * @return the codIett
	 */
	public Long getCodIett() {
		return codIett;
	}

	/**
	 * @param codIett the codIett to set
	 */
	public void setCodIett(Long codIett) {
		this.codIett = codIett;
	}

	/**
	 * @return the codAref
	 */
	public Long getCodAref() {
		return codAref;
	}

	/**
	 * @param codAref the codAref to set
	 */
	public void setCodAref(Long codAref) {
		this.codAref = codAref;
	}
	
	@Override
    public String toString() {
        return new ToStringBuilder(this)
            .append("codPesquisa", getCodPesquisa())
            .append("codIett", getCodIett())
            .append("codAref", getCodAref())
            .toString();
    }

    @Override
    public boolean equals(Object other) {
        if ( !(other instanceof PesquisaIettPK) ) return false;
        PesquisaIettPK castOther = (PesquisaIettPK) other;
        return new EqualsBuilder()
            .append(this.getCodPesquisa(), castOther.getCodPesquisa())
            .append(this.getCodIett(), castOther.getCodIett())
            .append(this.getCodAref(), castOther.getCodAref())
            .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
            .append(getCodPesquisa())
            .append(getCodIett())
            .append(getCodAref())
            .toHashCode();
    }

}
