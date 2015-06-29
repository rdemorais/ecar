package ecar.pojo;

import java.io.Serializable;
import java.util.Date;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;


/** @author Hibernate CodeGenerator */
public class RegControleAcessoRca implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = -8059153202517729778L;

	/** identifier field */
    private Long codRca;

    /** nullable persistent field */
    private String tpContAcessoRca;

    /** nullable persistent field */
    private Date dataAcessoRca;

    /** persistent field */
    private ecar.pojo.UsuarioUsu usuarioUsu;

    /** full constructor
     * @param tpContAcessoRca
     * @param dataAcessoRca
     * @param usuarioUsu
     */
    public RegControleAcessoRca(String tpContAcessoRca, Date dataAcessoRca, ecar.pojo.UsuarioUsu usuarioUsu) {
        this.tpContAcessoRca = tpContAcessoRca;
        this.dataAcessoRca = dataAcessoRca;
        this.usuarioUsu = usuarioUsu;
    }

    /** default constructor */
    public RegControleAcessoRca() {
    }

    /** minimal constructor
     * @param usuarioUsu
     */
    public RegControleAcessoRca(ecar.pojo.UsuarioUsu usuarioUsu) {
        this.usuarioUsu = usuarioUsu;
    }

    /**
     *
     * @return
     */
    public Long getCodRca() {
        return this.codRca;
    }

    /**
     *
     * @param codRca
     */
    public void setCodRca(Long codRca) {
        this.codRca = codRca;
    }

    /**
     *
     * @return
     */
    public String getTpContAcessoRca() {
        return this.tpContAcessoRca;
    }

    /**
     *
     * @param tpContAcessoRca
     */
    public void setTpContAcessoRca(String tpContAcessoRca) {
        this.tpContAcessoRca = tpContAcessoRca;
    }

    /**
     *
     * @return
     */
    public Date getDataAcessoRca() {
        return this.dataAcessoRca;
    }

    /**
     *
     * @param dataAcessoRca
     */
    public void setDataAcessoRca(Date dataAcessoRca) {
        this.dataAcessoRca = dataAcessoRca;
    }

    /**
     *
     * @return
     */
    public ecar.pojo.UsuarioUsu getUsuarioUsu() {
        return this.usuarioUsu;
    }

    /**
     *
     * @param usuarioUsu
     */
    public void setUsuarioUsu(ecar.pojo.UsuarioUsu usuarioUsu) {
        this.usuarioUsu = usuarioUsu;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .append("codRca", getCodRca())
            .toString();
    }

    @Override
    public boolean equals(Object other) {
        if ( (this == other ) ) return true;
        if ( !(other instanceof RegControleAcessoRca) ) return false;
        RegControleAcessoRca castOther = (RegControleAcessoRca) other;
        return new EqualsBuilder()
            .append(this.getCodRca(), castOther.getCodRca())
            .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
            .append(getCodRca())
            .toHashCode();
    }

}
