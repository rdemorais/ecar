package ecar.pojo;

import java.io.Serializable;
import java.util.Set;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;


/** @author Hibernate CodeGenerator */
public class PerfilPfl implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = -7279860094745976830L;

	/** identifier field */
    private Long codPfl;

    /** nullable persistent field */
    private String descricaoPfl;

    /** nullable persistent field */
    private Integer nivelPfl;

    /** nullable persistent field */
    private String indAreaReservadaPfl;

    /** persistent field */
    private Set opcaoPerfilOpcps;

    /** full constructor
     * @param descricaoPfl
     * @param nivelPfl
     * @param opcaoPerfilOpcps
     * @param indAreaReservadaPfl
     */
    public PerfilPfl(String descricaoPfl, Integer nivelPfl, String indAreaReservadaPfl, Set opcaoPerfilOpcps) {
        this.descricaoPfl = descricaoPfl;
        this.nivelPfl = nivelPfl;
        this.indAreaReservadaPfl = indAreaReservadaPfl;
        this.opcaoPerfilOpcps = opcaoPerfilOpcps;
    }

    /** default constructor */
    public PerfilPfl() {
    }

    /** minimal constructor
     * @param opcaoPerfilOpcps
     */
    public PerfilPfl(Set opcaoPerfilOpcps) {
        this.opcaoPerfilOpcps = opcaoPerfilOpcps;
    }

    /**
     *
     * @return
     */
    public Long getCodPfl() {
        return this.codPfl;
    }

    /**
     *
     * @param codPfl
     */
    public void setCodPfl(Long codPfl) {
        this.codPfl = codPfl;
    }

    /**
     *
     * @return
     */
    public String getDescricaoPfl() {
        return this.descricaoPfl;
    }

    /**
     *
     * @param descricaoPfl
     */
    public void setDescricaoPfl(String descricaoPfl) {
        this.descricaoPfl = descricaoPfl;
    }

    /**
     *
     * @return
     */
    public Integer getNivelPfl() {
        return this.nivelPfl;
    }

    /**
     *
     * @param nivelPfl
     */
    public void setNivelPfl(Integer nivelPfl) {
        this.nivelPfl = nivelPfl;
    }

    /**
     *
     * @return
     */
    public String getIndAreaReservadaPfl() {
        return this.indAreaReservadaPfl;
    }

    /**
     *
     * @param indAreaReservadaPfl
     */
    public void setIndAreaReservadaPfl(String indAreaReservadaPfl) {
        this.indAreaReservadaPfl = indAreaReservadaPfl;
    }

    /**
     *
     * @return
     */
    public Set getOpcaoPerfilOpcps() {
        return this.opcaoPerfilOpcps;
    }

    /**
     *
     * @param opcaoPerfilOpcps
     */
    public void setOpcaoPerfilOpcps(Set opcaoPerfilOpcps) {
        this.opcaoPerfilOpcps = opcaoPerfilOpcps;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .append("codPfl", getCodPfl())
            .toString();
    }

    @Override
    public boolean equals(Object other) {
        if ( (this == other ) ) return true;
        if ( !(other instanceof PerfilPfl) ) return false;
        PerfilPfl castOther = (PerfilPfl) other;
        return new EqualsBuilder()
            .append(this.getCodPfl(), castOther.getCodPfl())
            .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
            .append(getCodPfl())
            .toHashCode();
    }

}
