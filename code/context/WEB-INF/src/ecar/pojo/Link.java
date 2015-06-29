package ecar.pojo;

import java.io.Serializable;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/** @author Hibernate CodeGenerator */
public class Link implements Serializable {

	/**
	 * 
	 */	
	private static final long serialVersionUID = -6779612130084130388L;

	/** identifier field */
    private Integer codLink;

    /** nullable persistent field */
    private String labelLink;
    
    /** nullable persistent field */
    private String nomeLink;

    /** nullable persistent field */
    private String exibeMonitoramentoLink;

    /** nullable persistent field */
    private String urlLink;

    /** nullable persistent field */
    private String iconeLink;

    /** nullable persistent field */
    private Integer ordemLink;

    /** full constructor
     * @param codLink
     * @param labelLink
     * @param iconeLink
     * @param ordemLink
     * @param exibeMonitoramentoLink
     * @param nomeLink
     * @param urlLink
     */
    public Link(Integer codLink, String labelLink, String exibeMonitoramentoLink, Integer ordemLink, 
    			String nomeLink, String urlLink, String iconeLink) {
        this.codLink = codLink;
        this.labelLink = labelLink;        
        this.exibeMonitoramentoLink = exibeMonitoramentoLink;
        this.ordemLink = ordemLink;
        this.nomeLink = nomeLink;
        this.urlLink = urlLink;
        this.iconeLink = iconeLink;
    }

    /** 
     * default constructor.<br>
     * 
     * @author N/C
     * @since N/C 
     */
    public Link() {
    }

    /**
     * @author N/C
     * @since N/C
     * @return Integer
     */
    public Integer getCodLink() {
        return this.codLink;
    }

    /**
     * @param codLink
     * @author N/C
     * @since N/C
     */
    public void setcodLink(Integer codLink) {
        this.codLink = codLink;
    }

    /**
     * @author N/C
     * @since N/C
     * @return String
     */
    public String getLabelLink() {
        return this.labelLink;
    }

    /**
     * @param labelLink
     * @author N/C
     * @since N/C
     */
    public void setLabelLink(String labelLink) {
        this.labelLink = labelLink;
    }

    /**
     * @author N/C
     * @since N/C
     * @return String
     */
    public String getExibeMonitoramentoLink() {
        return this.exibeMonitoramentoLink;
    }

    /**
     * @param exibeMonitoramentoLink
     * @author N/C
     * @since N/C
     */
    public void setExibeMonitoramentoLink(String exibeMonitoramentoLink) {
        this.exibeMonitoramentoLink = exibeMonitoramentoLink;
    }

    /**
     * @author N/C
     * @since N/C
     * @return Integer
     */
    public Integer getOrdemLink() {
        return this.ordemLink;
    }

    /**
     * @author N/C
     * @since N/C
     * @param ordemLink
     */
    public void setOrdemLink(Integer ordemLink) {
        this.ordemLink = ordemLink;
    }

    /**
     * @author N/C
     * @since N/C
     * @return String
     */
    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .append("codLink", getCodLink())
            .toString();
    }

    /**
     * @param other
     * @author N/C
     * @since N/C
     * @return boolean
     */
    @Override
    public boolean equals(Object other) {
        if ( !(other instanceof Link) ) return false;
        Link castOther = (Link) other;
        return new EqualsBuilder()
            .append(this.getCodLink(), castOther.getCodLink())
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
            .append(getCodLink())
            .toHashCode();
    }

    /**
     * @author N/C
     * @since N/C
     * @return String
     */
	public String getNomeLink() {
		return nomeLink;
	}

	/**
	 * @author N/C
     * @since N/C
         * @param nomeLink
	 */
	public void setNomeLink(String nomeLink) {
		this.nomeLink = nomeLink;
	}

	/**
     * @author N/C
     * @since N/C
     * @return String
     */
	public String getUrlLink() {
		return urlLink;
	}

	/**
	 * @author N/C
     * @since N/C
         * @param urlLink
	 */
	public void setUrlLink(String urlLink) {
		this.urlLink = urlLink;
	}

	/**
     * @author N/C
     * @since N/C
     * @return String
     */
	public String getIconeLink() {
		return iconeLink;
	}

	/**
	 * @author N/C
     * @since N/C
         * @param iconeLink
	 */
	public void setIconeLink(String iconeLink) {
		this.iconeLink = iconeLink;
	}

}
