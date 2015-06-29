package ecar.pojo;

import java.io.Serializable;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/** @author Hibernate CodeGenerator */
public class EnderecoEnd implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 4162494951615301397L;

	/** identifier field */
    private Long codEnd;

    /** nullable persistent field */
    private String enderecoEnd;

    /** nullable persistent field */
    private String complementoEnd;

    /** nullable persistent field */
    private String bairroEnd;

    /** nullable persistent field */
    private String cidadeEnd;

    /** nullable persistent field */
    private Integer cepEnd;

    /** nullable persistent field */
    private String idEnd;
    
    /** persistent field */
    private ecar.pojo.TipoEnderecoTend tipoEnderecoTend;

    /** persistent field */
    private ecar.pojo.EntidadeEnt entidadeEnt;
    
    /** persistent field */
    private ecar.pojo.Uf uf;
    
    /** full constructor
     * @param enderecoEnd
     * @param complementoEnd
     * @param idEnd
     * @param bairroEnd
     * @param cidadeEnd
     * @param tipoEnderecoTend
     * @param uf
     * @param cepEnd
     * @param entidadeEnt
     */
    public EnderecoEnd(String enderecoEnd, String complementoEnd, String bairroEnd, String idEnd, String cidadeEnd, Integer cepEnd, ecar.pojo.Uf uf, ecar.pojo.TipoEnderecoTend tipoEnderecoTend, EntidadeEnt entidadeEnt) {
        this.enderecoEnd = enderecoEnd;
        this.complementoEnd = complementoEnd;
        this.bairroEnd = bairroEnd;
        this.cidadeEnd = cidadeEnd;
        this.cepEnd = cepEnd;
        this.idEnd = idEnd;
        this.uf = uf;
        this.tipoEnderecoTend = tipoEnderecoTend;
        this.entidadeEnt = entidadeEnt;
    }

    /** default constructor */
    public EnderecoEnd() {
    }

    /** minimal constructor
     * @param tipoEnderecoTend
     * @param entidadeEnt
     */
    public EnderecoEnd(ecar.pojo.TipoEnderecoTend tipoEnderecoTend, ecar.pojo.EntidadeEnt entidadeEnt) {
        this.tipoEnderecoTend = tipoEnderecoTend;
        this.entidadeEnt = entidadeEnt;
    }

    /**
     *
     * @return
     */
    public Long getCodEnd() {
        return this.codEnd;
    }

    /**
     *
     * @param codEnd
     */
    public void setCodEnd(Long codEnd) {
        this.codEnd = codEnd;
    }

    /**
     *
     * @return
     */
    public String getEnderecoEnd() {
        return this.enderecoEnd;
    }

    /**
     *
     * @param enderecoEnd
     */
    public void setEnderecoEnd(String enderecoEnd) {
        this.enderecoEnd = enderecoEnd;
    }

    /**
     *
     * @return
     */
    public String getComplementoEnd() {
        return this.complementoEnd;
    }

    /**
     *
     * @param complementoEnd
     */
    public void setComplementoEnd(String complementoEnd) {
        this.complementoEnd = complementoEnd;
    }

    /**
     *
     * @return
     */
    public String getBairroEnd() {
        return this.bairroEnd;
    }

    /**
     *
     * @param bairroEnd
     */
    public void setBairroEnd(String bairroEnd) {
        this.bairroEnd = bairroEnd;
    }

    /**
     *
     * @return
     */
    public String getCidadeEnd() {
        return this.cidadeEnd;
    }

    /**
     *
     * @param cidadeEnd
     */
    public void setCidadeEnd(String cidadeEnd) {
        this.cidadeEnd = cidadeEnd;
    }

    /**
     *
     * @return
     */
    public String getIdEnd() {
        return this.idEnd;
    }

    /**
     *
     * @param idEnd
     */
    public void setIdEnd(String idEnd) {
        this.idEnd = idEnd;
    }
    
    /**
     *
     * @return
     */
    public Integer getCepEnd() {
        return this.cepEnd;
    }

    /**
     *
     * @param cepEnd
     */
    public void setCepEnd(Integer cepEnd) {
        this.cepEnd = cepEnd;
    }

    /**
     *
     * @return
     */
    public ecar.pojo.Uf getUf() {
        return this.uf;
    }

    /**
     *
     * @param uf
     */
    public void setUf(ecar.pojo.Uf uf) {
        this.uf = uf;
    }

    /**
     *
     * @return
     */
    public ecar.pojo.TipoEnderecoTend getTipoEnderecoTend() {
        return this.tipoEnderecoTend;
    }

    /**
     *
     * @param tipoEnderecoTend
     */
    public void setTipoEnderecoTend(ecar.pojo.TipoEnderecoTend tipoEnderecoTend) {
        this.tipoEnderecoTend = tipoEnderecoTend;
    }

    /**
     *
     * @return
     */
    public ecar.pojo.EntidadeEnt getEntidadeEnt() {
        return this.entidadeEnt;
    }

    /**
     *
     * @param entidadeEnt
     */
    public void setEntidadeEnt(EntidadeEnt entidadeEnt) {
        this.entidadeEnt = entidadeEnt;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .append("codEnd", getCodEnd())
            .toString();
    }

    @Override
    public boolean equals(Object other) {
        if ( !(other instanceof EnderecoEnd) ) return false;
        EnderecoEnd castOther = (EnderecoEnd) other;
        return new EqualsBuilder()
            .append(this.getEntidadeEnt(), castOther.getEntidadeEnt())
            .append(this.getEnderecoEnd(), castOther.getEnderecoEnd())
            .append(this.getBairroEnd(), castOther.getBairroEnd())
            .append(this.getCidadeEnd(), castOther.getCidadeEnd())
            .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
            .append(this.getEntidadeEnt())
            .append(this.getEnderecoEnd())
            .append(this.getBairroEnd())
            .append(this.getCidadeEnd())
            .toHashCode();
    }

}
