package ecar.pojo;

import java.io.Serializable;
import java.util.Set;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;


/** @author Hibernate CodeGenerator */
public class Uf implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 2309470647245319755L;

	/** identifier field */
    private String codUf;

    /** nullable persistent field */
    private String descricaoUf;

    /** persistent field */
    private Set empresaEmps;

    /** persistent field */
    private Set usuarioUsusByComercUfUsu;

    /** persistent field */
    private Set usuarioUsusByResidUfUsu;

    /** persistent field */
    private Set enderecoEnds;

    /** full constructor
     * @param descricaoUf
     * @param empresaEmps
     * @param usuarioUsusByComercUfUsu
     * @param usuarioUsusByResidUfUsu
     * @param enderecoEnds
     */
    public Uf (String descricaoUf, Set empresaEmps, Set usuarioUsusByComercUfUsu, Set usuarioUsusByResidUfUsu, Set enderecoEnds) {
    	this.descricaoUf = descricaoUf;
        this.empresaEmps = empresaEmps;
        this.usuarioUsusByComercUfUsu = usuarioUsusByComercUfUsu;
        this.usuarioUsusByResidUfUsu = usuarioUsusByResidUfUsu;
        this.enderecoEnds = enderecoEnds;
    }

    /** default constructor */
    public Uf() {
    }

    /** minimal constructor
     * @param empresaEmps
     * @param usuarioUsusByComercUfUsu
     * @param usuarioUsusByResidUfUsu
     * @param enderecoEnds
     */
    public Uf(Set empresaEmps, Set usuarioUsusByComercUfUsu, Set usuarioUsusByResidUfUsu, Set enderecoEnds) {
        this.empresaEmps = empresaEmps;
        this.usuarioUsusByComercUfUsu = usuarioUsusByComercUfUsu;
        this.usuarioUsusByResidUfUsu = usuarioUsusByResidUfUsu;
        this.enderecoEnds = enderecoEnds;
    }

    /**
     *
     * @return
     */
    public String getCodUf() {
        return this.codUf;
    }

    /**
     *
     * @param codUf
     */
    public void setCodUf(String codUf) {
        this.codUf = codUf;
    }

    /**
     *
     * @return
     */
    public String getDescricaoUf() {
        return this.descricaoUf;
    }

    /**
     *
     * @param descricaoUf
     */
    public void setDescricaoUf(String descricaoUf) {
        this.descricaoUf = descricaoUf;
    }

    /**
     *
     * @return
     */
    public Set getEmpresaEmps() {
        return this.empresaEmps;
    }

    /**
     *
     * @param empresaEmps
     */
    public void setEmpresaEmps(Set empresaEmps) {
        this.empresaEmps = empresaEmps;
    }

    /**
     *
     * @return
     */
    public Set getUsuarioUsusByComercUfUsu() {
        return this.usuarioUsusByComercUfUsu;
    }

    /**
     *
     * @param usuarioUsusByComercUfUsu
     */
    public void setUsuarioUsusByComercUfUsu(Set usuarioUsusByComercUfUsu) {
        this.usuarioUsusByComercUfUsu = usuarioUsusByComercUfUsu;
    }

    /**
     *
     * @return
     */
    public Set getUsuarioUsusByResidUfUsu() {
        return this.usuarioUsusByResidUfUsu;
    }

    /**
     *
     * @param usuarioUsusByResidUfUsu
     */
    public void setUsuarioUsusByResidUfUsu(Set usuarioUsusByResidUfUsu) {
        this.usuarioUsusByResidUfUsu = usuarioUsusByResidUfUsu;
    }

    /**
     *
     * @return
     */
    public Set getEnderecoEnds() {
        return this.enderecoEnds;
    }

    /**
     *
     * @param enderecoEnds
     */
    public void setEnderecoEnds(Set enderecoEnds) {
        this.enderecoEnds = enderecoEnds;
    }    
    
    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .append("codUf", getCodUf())
            .toString();
    }

    @Override
    public boolean equals(Object other) {
        if ( (this == other ) ) return true;
        if ( !(other instanceof Uf) ) return false;
        Uf castOther = (Uf) other;
        return new EqualsBuilder()
            .append(this.getCodUf(), castOther.getCodUf())
            .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
            .append(getCodUf())
            .toHashCode();
    }

}
