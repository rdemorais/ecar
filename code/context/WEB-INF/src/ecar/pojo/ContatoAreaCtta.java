package ecar.pojo;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;


/** @author Hibernate CodeGenerator */
public class ContatoAreaCtta implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1848627124334757078L;

	/** identifier field */
    private Long codCtta;

    /** nullable persistent field */
    private String nomeCtta;

    /** nullable persistent field */
    private String assuntoRetornoCtta;

    /** nullable persistent field */
    private String indEmailRespostaCtta;

    /** nullable persistent field */
    private String indLogadoCtta;

    /** nullable persistent field */
    private String indAtivoCtta;

    /** nullable persistent field */
    private Date dataInclusaoCtta;

    /** nullable persistent field */
    private String textoRetornoCtta;

    /** persistent field */
    private Set contatoMailCttms;

    /** 
     * full constructor.<br>
     * 
     * @author N/C
	 * @since N/C  
     * @param nomeCtta
     * @param assuntoRetornoCtta
     * @param indLogadoCtta 
     * @param indAtivoCtta 
     * @param indEmailRespostaCtta
     * @param dataInclusaoCtta 
     * @param textoRetornoCtta
     * @param contatoMailCttms
     */
    public ContatoAreaCtta(String nomeCtta, String assuntoRetornoCtta, String indEmailRespostaCtta, 
    		String indLogadoCtta, String indAtivoCtta, Date dataInclusaoCtta, String textoRetornoCtta, 
    		Set contatoMailCttms) {
        this.nomeCtta = nomeCtta;
        this.assuntoRetornoCtta = assuntoRetornoCtta;
        this.indEmailRespostaCtta = indEmailRespostaCtta;
        this.indLogadoCtta = indLogadoCtta;
        this.indAtivoCtta = indAtivoCtta;
        this.dataInclusaoCtta = dataInclusaoCtta;
        this.textoRetornoCtta = textoRetornoCtta;
        this.contatoMailCttms = contatoMailCttms;
    }

    /** default constructor */
    public ContatoAreaCtta() {
    }

    /** 
     * minimal constructor
     * 
     * @param contatoMailCttms
     * @author N/C
	 * @since N/C 
     */
    public ContatoAreaCtta(Set contatoMailCttms) {
        this.contatoMailCttms = contatoMailCttms;
    }

    /**
     * @author N/C
	 * @since N/C 
     * @return Long
     */
    public Long getCodCtta() {
        return this.codCtta;
    }

    /**
     * @param codCtta
     * @author N/C
	 * @since N/C 
     */
    public void setCodCtta(Long codCtta) {
        this.codCtta = codCtta;
    }

    /**
     * @author N/C
	 * @since N/C 
     * @return String
     */
    public String getNomeCtta() {
        return this.nomeCtta;
    }

    /**
     * @param nomeCtta
     * @author N/C
	 * @since N/C 
     */
    public void setNomeCtta(String nomeCtta) {
        this.nomeCtta = nomeCtta;
    }

    /**
     * @author N/C
	 * @since N/C 
     * @return String
     */
    public String getAssuntoRetornoCtta() {
        return this.assuntoRetornoCtta;
    }

    /**
     * @param assuntoRetornoCtta
     * @author N/C
	 * @since N/C 
     */
    public void setAssuntoRetornoCtta(String assuntoRetornoCtta) {
        this.assuntoRetornoCtta = assuntoRetornoCtta;
    }

    /**
     * @author N/C
	 * @since N/C 
     * @return String
     */
    public String getIndEmailRespostaCtta() {
        return this.indEmailRespostaCtta;
    }

    /**
     * @param indEmailRespostaCtta
     * @author N/C
	 * @since N/C 
     */
    public void setIndEmailRespostaCtta(String indEmailRespostaCtta) {
        this.indEmailRespostaCtta = indEmailRespostaCtta;
    }

    /**
     * @author N/C
	 * @since N/C 
     * @return String
     */
    public String getIndLogadoCtta() {
        return this.indLogadoCtta;
    }

    /**
     * @author N/C
	 * @since N/C 
     * @param indLogadoCtta
     */
    public void setIndLogadoCtta(String indLogadoCtta) {
        this.indLogadoCtta = indLogadoCtta;
    }

    /**
     * @author N/C
	 * @since N/C 
     * @return String
     */
    public String getIndAtivoCtta() {
        return this.indAtivoCtta;
    }

    /**
     * @author N/C
	 * @since N/C 
     * @param indAtivoCtta
     */
    public void setIndAtivoCtta(String indAtivoCtta) {
        this.indAtivoCtta = indAtivoCtta;
    }

    /**
     * @author N/C
	 * @since N/C 
     * @return Date
     */
    public Date getDataInclusaoCtta() {
        return this.dataInclusaoCtta;
    }

    /**
     * @param dataInclusaoCtta
     * @author N/C
	 * @since N/C 
     */
    public void setDataInclusaoCtta(Date dataInclusaoCtta) {
        this.dataInclusaoCtta = dataInclusaoCtta;
    }

    /**
     * @author N/C
	 * @since N/C 
     * @return String
     */
    public String getTextoRetornoCtta() {
        return this.textoRetornoCtta;
    }

    /**
     * @author N/C
	 * @since N/C 
     * @param textoRetornoCtta
     */
    public void setTextoRetornoCtta(String textoRetornoCtta) {
        this.textoRetornoCtta = textoRetornoCtta;
    }

    /**
     * @author N/C
	 * @since N/C 
     * @return Set
     */
    public Set getContatoMailCttms() {
        return this.contatoMailCttms;
    }

    /**
     * @param contatoMailCttms
     * @author N/C
	 * @since N/C 
     */
    public void setContatoMailCttms(Set contatoMailCttms) {
        this.contatoMailCttms = contatoMailCttms;
    }

    /**
     * @author N/C
	 * @since N/C 
	 * @return String
     */
    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .append("codCtta", getCodCtta())
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
        if ( !(other instanceof ContatoAreaCtta) ) return false;
        ContatoAreaCtta castOther = (ContatoAreaCtta) other;
        return new EqualsBuilder()
            .append(this.getCodCtta(), castOther.getCodCtta())
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
            .append(getCodCtta())
            .toHashCode();
    }

}
