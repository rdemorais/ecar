package ecar.pojo;

import java.io.Serializable;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;


/** @author Hibernate CodeGenerator */
public class ContatoMailCttm implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 4359043588101038371L;

	/** identifier field */
    private Long codCttm;

    /** nullable persistent field */
    private String nomeCttm;

    /** nullable persistent field */
    private String indOrigemCttm;

    /** nullable persistent field */
    private String emailCttm;

    /** persistent field */
    private ecar.pojo.ContatoAreaCtta contatoAreaCtta;

    /** persistent field */
    private ecar.pojo.ContatoMailCategoriaCttm contatoMailCategoriaCttm;

    /** 
     * full constructor.<br>
     * 
     * @author N/C
     * @since NString nomeCttm
     * @param nomeCttm
     * @param indOrigemCttm
     * @param emailCttm 
     * @param contatoAreaCtta
     * @param contatoMailCategoriaCttm
     */
    public ContatoMailCttm(String nomeCttm, String indOrigemCttm, String emailCttm, 
    		ecar.pojo.ContatoAreaCtta contatoAreaCtta, ecar.pojo.ContatoMailCategoriaCttm contatoMailCategoriaCttm) {
        this.nomeCttm = nomeCttm;
        this.indOrigemCttm = indOrigemCttm;
        this.emailCttm = emailCttm;
        this.contatoAreaCtta = contatoAreaCtta;
        this.contatoMailCategoriaCttm = contatoMailCategoriaCttm;
    }

    /** default constructor */
    public ContatoMailCttm() {
    }

    /** 
     * minimal constructor
     * 
     * @author N/C
	 * @since N/C 
     * @param contatoAreaCtta
     * @param contatoMailCategoriaCttm
     */
    public ContatoMailCttm(ecar.pojo.ContatoAreaCtta contatoAreaCtta, ecar.pojo.ContatoMailCategoriaCttm contatoMailCategoriaCttm) {
        this.contatoAreaCtta = contatoAreaCtta;
        this.contatoMailCategoriaCttm = contatoMailCategoriaCttm;
    }

    /**
     * @author N/C
	 * @since N/C 
     * @return Long
     */
    public Long getCodCttm() {
        return this.codCttm;
    }

    /**
     * @author N/C
	 * @since N/C 
     * @param codCttm
     */
    public void setCodCttm(Long codCttm) {
        this.codCttm = codCttm;
    }

    /**
     * @author N/C
	 * @since N/C 
     * @return String
     */
    public String getNomeCttm() {
        return this.nomeCttm;
    }

    /**
     * @author N/C
	 * @since N/C 
     * @param nomeCttm
     */
    public void setNomeCttm(String nomeCttm) {
        this.nomeCttm = nomeCttm;
    }

    /**
     * @author N/C
	 * @since N/C 
     * @return String
     */
    public String getIndOrigemCttm() {
        return this.indOrigemCttm;
    }

    /**
     * @author N/C
	 * @since N/C 
     * @param indOrigemCttm
     */
    public void setIndOrigemCttm(String indOrigemCttm) {
        this.indOrigemCttm = indOrigemCttm;
    }

    /**
     * @author N/C
	 * @since N/C 
     * @return String
     */
    public String getEmailCttm() {
        return this.emailCttm;
    }

    /**
     * @author N/C
	 * @since N/C 
     * @param emailCttm
     */
    public void setEmailCttm(String emailCttm) {
        this.emailCttm = emailCttm;
    }

    /**
     * @author N/C
	 * @since N/C 
     * @return ecar.pojo.ContatoAreaCtta
     */
    public ecar.pojo.ContatoAreaCtta getContatoAreaCtta() {
        return this.contatoAreaCtta;
    }

    /**
     * @param contatoAreaCtta
     * @author N/C
	 * @since N/C 
     */
    public void setContatoAreaCtta(ecar.pojo.ContatoAreaCtta contatoAreaCtta) {
        this.contatoAreaCtta = contatoAreaCtta;
    }

    /**
     * @author N/C
	 * @since N/C 
     * @return ecar.pojo.ContatoMailCategoriaCttm
     */
    public ecar.pojo.ContatoMailCategoriaCttm getContatoMailCategoriaCttm() {
        return this.contatoMailCategoriaCttm;
    }

    /**
     * @param contatoMailCategoriaCttm
     * @author N/C
	 * @since N/C 
     */
    public void setContatoMailCategoriaCttm(ecar.pojo.ContatoMailCategoriaCttm contatoMailCategoriaCttm) {
        this.contatoMailCategoriaCttm = contatoMailCategoriaCttm;
    }

    /**
     * @author N/C
	 * @since N/C 
	 * @return String
     */
    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .append("codCttm", getCodCttm())
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
        if ( !(other instanceof ContatoMailCttm) ) return false;
        ContatoMailCttm castOther = (ContatoMailCttm) other;
        return new EqualsBuilder()
            .append(this.getCodCttm(), castOther.getCodCttm())
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
            .append(getCodCttm())
            .toHashCode();
    }

}
