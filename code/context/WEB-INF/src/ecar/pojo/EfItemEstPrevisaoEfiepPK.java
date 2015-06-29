package ecar.pojo;

import java.io.Serializable;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;


/** @author Hibernate CodeGenerator */
public class EfItemEstPrevisaoEfiepPK implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = -5011456829814217061L;

	/** identifier field */
    private Long codIett;

    /** identifier field */
    private Long codRec;

    /** identifier field */
    private Long codFonr;

    /** identifier field */
    private Long codExe;
    

    /** full constructor
     * @param codIett
     * @param codRec
     * @param codFonr
     * @param codExe
     */
    public EfItemEstPrevisaoEfiepPK(Long codIett, Long codRec, Long codFonr, Long codExe) {
        this.codIett = codIett;
        this.codRec = codRec;
        this.codFonr = codFonr;
        this.codExe = codExe;
    }

    /** default constructor */
    public EfItemEstPrevisaoEfiepPK() {
    }

    /**
     *
     * @return
     */
    public Long getCodIett() {
        return this.codIett;
    }

    /**
     *
     * @param codIett
     */
    public void setCodIett(Long codIett) {
        this.codIett = codIett;
    }

    /**
     *
     * @return
     */
    public Long getCodRec() {
        return this.codRec;
    }

    /**
     *
     * @param codRec
     */
    public void setCodRec(Long codRec) {
        this.codRec = codRec;
    }

    /**
     *
     * @return
     */
    public Long getCodFonr() {
        return this.codFonr;
    }

    /**
     *
     * @param codFonr
     */
    public void setCodFonr(Long codFonr) {
        this.codFonr = codFonr;
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

    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .append("codIett", getCodIett())
            .append("codRec", getCodRec())
            .append("codFonr", getCodFonr())
            .append("codExe", getCodExe())
            .toString();
    }

    @Override
    public boolean equals(Object other) {
        if ( (this == other ) ) return true;
        if ( !(other instanceof EfItemEstPrevisaoEfiepPK) ) return false;
        EfItemEstPrevisaoEfiepPK castOther = (EfItemEstPrevisaoEfiepPK) other;
        return new EqualsBuilder()
            .append(this.getCodIett(), castOther.getCodIett())
            .append(this.getCodRec(), castOther.getCodRec())
            .append(this.getCodFonr(), castOther.getCodFonr())
            .append(this.getCodExe(), castOther.getCodExe())
            .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
            .append(getCodIett())
            .append(getCodRec())
            .append(getCodFonr())
            .append(getCodExe())
            .toHashCode();
    }

}
