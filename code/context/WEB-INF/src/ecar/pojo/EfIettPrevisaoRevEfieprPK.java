package ecar.pojo;

import java.io.Serializable;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/** @author Hibernate CodeGenerator */
public class EfIettPrevisaoRevEfieprPK implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 6052177569446333188L;

	/** identifier field */
    private Integer codIettrev;

    /** identifier field */
    private Integer codRec;

    /** identifier field */
    private Integer codFonr;

    /** identifier field */
    private Integer codExe;

    /** full constructor
     * @param codIettrev
     * @param codRec
     * @param codFonr
     * @param codExe
     */
    public EfIettPrevisaoRevEfieprPK(Integer codIettrev, Integer codRec, Integer codFonr, Integer codExe) {
        this.codIettrev = codIettrev;
        this.codRec = codRec;
        this.codFonr = codFonr;
        this.codExe = codExe;
    }

    /** default constructor */
    public EfIettPrevisaoRevEfieprPK() {
    }

    /**
     *
     * @return
     */
    public Integer getCodIettrev() {
        return this.codIettrev;
    }

    /**
     *
     * @param codIettrev
     */
    public void setCodIettrev(Integer codIettrev) {
        this.codIettrev = codIettrev;
    }

    /**
     *
     * @return
     */
    public Integer getCodRec() {
        return this.codRec;
    }

    /**
     *
     * @param codRec
     */
    public void setCodRec(Integer codRec) {
        this.codRec = codRec;
    }

    /**
     *
     * @return
     */
    public Integer getCodFonr() {
        return this.codFonr;
    }

    /**
     *
     * @param codFonr
     */
    public void setCodFonr(Integer codFonr) {
        this.codFonr = codFonr;
    }

    /**
     *
     * @return
     */
    public Integer getCodExe() {
        return this.codExe;
    }

    /**
     *
     * @param codExe
     */
    public void setCodExe(Integer codExe) {
        this.codExe = codExe;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .append("codIettrev", getCodIettrev())
            .append("codRec", getCodRec())
            .append("codFonr", getCodFonr())
            .append("codExe", getCodExe())
            .toString();
    }

    @Override
    public boolean equals(Object other) {
        if ( !(other instanceof EfIettPrevisaoRevEfieprPK) ) return false;
        EfIettPrevisaoRevEfieprPK castOther = (EfIettPrevisaoRevEfieprPK) other;
        return new EqualsBuilder()
            .append(this.getCodIettrev(), castOther.getCodIettrev())
            .append(this.getCodRec(), castOther.getCodRec())
            .append(this.getCodFonr(), castOther.getCodFonr())
            .append(this.getCodExe(), castOther.getCodExe())
            .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
            .append(getCodIettrev())
            .append(getCodRec())
            .append(getCodFonr())
            .append(getCodExe())
            .toHashCode();
    }

}
