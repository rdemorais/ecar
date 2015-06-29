package ecar.pojo;

import java.io.Serializable;
import java.util.Set;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;


/** @author Hibernate CodeGenerator */
public class EmpresaEmp implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = -7146229322680304906L;

	/** identifier field */
    private Long codEmp;

    /** nullable persistent field */
    private String siglaEmp;

    /** nullable persistent field */
    private String razaoSocialEmp;

    /** nullable persistent field */
    private String cnpjCpfEmp;

    /** nullable persistent field */
    private String complementoEmp;

    /** nullable persistent field */
    private String enderecoEmp;

    /** nullable persistent field */
    private String inscrEstadualEmp;

    /** nullable persistent field */
    private String bairroEmp;

    /** nullable persistent field */
    private String telefone2Emp;

    /** nullable persistent field */
    private String ddd2Emp;

    /** nullable persistent field */
    private String telefone1Emp;

    /** nullable persistent field */
    private String ddd1Emp;

    /** nullable persistent field */
    private String cepEmp;

    /** nullable persistent field */
    private String cidadeEmp;

    /** nullable persistent field */
    private String logotipoEmailEmp;

    /** nullable persistent field */
    private String logotipoEmp;

    /** nullable persistent field */
    private String homePageEmp;

    /** nullable persistent field */
    private String emailErrosEmp;

    /** nullable persistent field */
    private String emailContatoEmp;

    /** nullable persistent field */
    private String faxEmp;

    /** nullable persistent field */
    private String dddFaxEmp;

    /** nullable persistent field */
    private String inscrMunicipalEmp;

    /** persistent field */
    private ecar.pojo.Uf uf;

    /** persistent field */
    private Set textosSiteTxts;
    
    /** nullable persistent field */
    private String logotipoRelatorioEmp;

    /** full constructor
     * @param siglaEmp
     * @param cidadeEmp
     * @param razaoSocialEmp
     * @param cnpjCpfEmp
     * @param enderecoEmp 
     * @param emailErrosEmp
     * @param complementoEmp
     * @param inscrEstadualEmp
     * @param logorelatorioEmp
     * @param ddd1Emp
     * @param telefone2Emp
     * @param bairroEmp
     * @param telefone1Emp
     * @param inscrMunicipalEmp
     * @param emailContatoEmp
     * @param ddd2Emp
     * @param logotipoEmp
     * @param cepEmp
     * @param homePageEmp
     * @param logotipoEmailEmp
     * @param uf
     * @param textosSiteTxts
     * @param faxEmp
     * @param dddFaxEmp
     */
    public EmpresaEmp(String siglaEmp, String razaoSocialEmp, String cnpjCpfEmp, String complementoEmp, String enderecoEmp, String inscrEstadualEmp, String bairroEmp, String telefone2Emp, String ddd2Emp, String telefone1Emp, String ddd1Emp, String cepEmp, String cidadeEmp, String logotipoEmailEmp, String logotipoEmp, String homePageEmp, String emailErrosEmp, String emailContatoEmp, String faxEmp, String dddFaxEmp, String inscrMunicipalEmp, ecar.pojo.Uf uf, Set textosSiteTxts, String logorelatorioEmp) {
        this.siglaEmp = siglaEmp;
        this.razaoSocialEmp = razaoSocialEmp;
        this.cnpjCpfEmp = cnpjCpfEmp;
        this.complementoEmp = complementoEmp;
        this.enderecoEmp = enderecoEmp;
        this.inscrEstadualEmp = inscrEstadualEmp;
        this.bairroEmp = bairroEmp;
        this.telefone2Emp = telefone2Emp;
        this.ddd2Emp = ddd2Emp;
        this.telefone1Emp = telefone1Emp;
        this.ddd1Emp = ddd1Emp;
        this.cepEmp = cepEmp;
        this.cidadeEmp = cidadeEmp;
        this.logotipoEmailEmp = logotipoEmailEmp;
        this.logotipoEmp = logotipoEmp;
        this.homePageEmp = homePageEmp;
        this.emailErrosEmp = emailErrosEmp;
        this.emailContatoEmp = emailContatoEmp;
        this.faxEmp = faxEmp;
        this.dddFaxEmp = dddFaxEmp;
        this.inscrMunicipalEmp = inscrMunicipalEmp;
        this.uf = uf;
        this.textosSiteTxts = textosSiteTxts;
        this.logotipoRelatorioEmp = logorelatorioEmp;
    }

    /** default constructor */
    public EmpresaEmp() {
    }

    /** minimal constructor
     * @param uf
     * @param textosSiteTxts
     */
    public EmpresaEmp(ecar.pojo.Uf uf, Set textosSiteTxts) {
        this.uf = uf;
        this.textosSiteTxts = textosSiteTxts;
    }
    
    public EmpresaEmp(String logotipoEmp){
    	this.logotipoEmp = logotipoEmp;
    }

    /**
     *
     * @return
     */
    public Long getCodEmp() {
        return this.codEmp;
    }

    /**
     *
     * @param codEmp
     */
    public void setCodEmp(Long codEmp) {
        this.codEmp = codEmp;
    }

    /**
     *
     * @return
     */
    public String getSiglaEmp() {
        return this.siglaEmp;
    }

    /**
     *
     * @param siglaEmp
     */
    public void setSiglaEmp(String siglaEmp) {
        this.siglaEmp = siglaEmp;
    }

    /**
     *
     * @return
     */
    public String getRazaoSocialEmp() {
        return this.razaoSocialEmp;
    }

    /**
     *
     * @param razaoSocialEmp
     */
    public void setRazaoSocialEmp(String razaoSocialEmp) {
        this.razaoSocialEmp = razaoSocialEmp;
    }

    /**
     *
     * @return
     */
    public String getCnpjCpfEmp() {
        return this.cnpjCpfEmp;
    }

    /**
     *
     * @param cnpjCpfEmp
     */
    public void setCnpjCpfEmp(String cnpjCpfEmp) {
        this.cnpjCpfEmp = cnpjCpfEmp;
    }

    /**
     *
     * @return
     */
    public String getComplementoEmp() {
        return this.complementoEmp;
    }

    /**
     *
     * @param complementoEmp
     */
    public void setComplementoEmp(String complementoEmp) {
        this.complementoEmp = complementoEmp;
    }

    /**
     *
     * @return
     */
    public String getEnderecoEmp() {
        return this.enderecoEmp;
    }

    /**
     *
     * @param enderecoEmp
     */
    public void setEnderecoEmp(String enderecoEmp) {
        this.enderecoEmp = enderecoEmp;
    }

    /**
     *
     * @return
     */
    public String getInscrEstadualEmp() {
        return this.inscrEstadualEmp;
    }

    /**
     *
     * @param inscrEstadualEmp
     */
    public void setInscrEstadualEmp(String inscrEstadualEmp) {
        this.inscrEstadualEmp = inscrEstadualEmp;
    }

    /**
     *
     * @return
     */
    public String getBairroEmp() {
        return this.bairroEmp;
    }

    /**
     *
     * @param bairroEmp
     */
    public void setBairroEmp(String bairroEmp) {
        this.bairroEmp = bairroEmp;
    }

    /**
     *
     * @return
     */
    public String getTelefone2Emp() {
        return this.telefone2Emp;
    }

    /**
     *
     * @param telefone2Emp
     */
    public void setTelefone2Emp(String telefone2Emp) {
        this.telefone2Emp = telefone2Emp;
    }

    /**
     *
     * @return
     */
    public String getDdd2Emp() {
        return this.ddd2Emp;
    }

    /**
     *
     * @param ddd2Emp
     */
    public void setDdd2Emp(String ddd2Emp) {
        this.ddd2Emp = ddd2Emp;
    }

    /**
     *
     * @return
     */
    public String getTelefone1Emp() {
        return this.telefone1Emp;
    }

    /**
     *
     * @param telefone1Emp
     */
    public void setTelefone1Emp(String telefone1Emp) {
        this.telefone1Emp = telefone1Emp;
    }

    /**
     *
     * @return
     */
    public String getDdd1Emp() {
        return this.ddd1Emp;
    }

    /**
     *
     * @param ddd1Emp
     */
    public void setDdd1Emp(String ddd1Emp) {
        this.ddd1Emp = ddd1Emp;
    }

    /**
     *
     * @return
     */
    public String getCepEmp() {
        return this.cepEmp;
    }

    /**
     *
     * @param cepEmp
     */
    public void setCepEmp(String cepEmp) {
        this.cepEmp = cepEmp;
    }

    /**
     *
     * @return
     */
    public String getCidadeEmp() {
        return this.cidadeEmp;
    }

    /**
     *
     * @param cidadeEmp
     */
    public void setCidadeEmp(String cidadeEmp) {
        this.cidadeEmp = cidadeEmp;
    }

    /**
     *
     * @return
     */
    public String getLogotipoEmailEmp() {
        return this.logotipoEmailEmp;
    }

    /**
     *
     * @param logotipoEmailEmp
     */
    public void setLogotipoEmailEmp(String logotipoEmailEmp) {
        this.logotipoEmailEmp = logotipoEmailEmp;
    }

    /**
     *
     * @return
     */
    public String getLogotipoEmp() {
        return this.logotipoEmp;
    }

    /**
     *
     * @param logotipoEmp
     */
    public void setLogotipoEmp(String logotipoEmp) {
        this.logotipoEmp = logotipoEmp;
    }

    /**
     *
     * @return
     */
    public String getHomePageEmp() {
        return this.homePageEmp;
    }

    /**
     *
     * @param homePageEmp
     */
    public void setHomePageEmp(String homePageEmp) {
        this.homePageEmp = homePageEmp;
    }

    /**
     *
     * @return
     */
    public String getEmailErrosEmp() {
        return this.emailErrosEmp;
    }

    /**
     *
     * @param emailErrosEmp
     */
    public void setEmailErrosEmp(String emailErrosEmp) {
        this.emailErrosEmp = emailErrosEmp;
    }

    /**
     *
     * @return
     */
    public String getEmailContatoEmp() {
        return this.emailContatoEmp;
    }

    /**
     *
     * @param emailContatoEmp
     */
    public void setEmailContatoEmp(String emailContatoEmp) {
        this.emailContatoEmp = emailContatoEmp;
    }

    /**
     *
     * @return
     */
    public String getFaxEmp() {
        return this.faxEmp;
    }

    /**
     *
     * @param faxEmp
     */
    public void setFaxEmp(String faxEmp) {
        this.faxEmp = faxEmp;
    }

    /**
     *
     * @return
     */
    public String getDddFaxEmp() {
        return this.dddFaxEmp;
    }

    /**
     *
     * @param dddFaxEmp
     */
    public void setDddFaxEmp(String dddFaxEmp) {
        this.dddFaxEmp = dddFaxEmp;
    }

    /**
     *
     * @return
     */
    public String getInscrMunicipalEmp() {
        return this.inscrMunicipalEmp;
    }

    /**
     *
     * @param inscrMunicipalEmp
     */
    public void setInscrMunicipalEmp(String inscrMunicipalEmp) {
        this.inscrMunicipalEmp = inscrMunicipalEmp;
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
    public Set getTextosSiteTxts() {
        return this.textosSiteTxts;
    }

    /**
     *
     * @param textosSiteTxts
     */
    public void setTextosSiteTxts(Set textosSiteTxts) {
        this.textosSiteTxts = textosSiteTxts;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .append("codEmp", getCodEmp())
            .toString();
    }

    @Override
    public boolean equals(Object other) {
        if ( (this == other ) ) return true;
        if ( !(other instanceof EmpresaEmp) ) return false;
        EmpresaEmp castOther = (EmpresaEmp) other;
        return new EqualsBuilder()
            .append(this.getCodEmp(), castOther.getCodEmp())
            .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
            .append(getCodEmp())
            .toHashCode();
    }
    
    /**
     *
     * @return
     */
    public String getLogotipoRelatorioEmp() {
        return this.logotipoRelatorioEmp;
    }

    /**
     *
     * @param logotipoRelatorioEmp
     */
    public void setLogotipoRelatorioEmp(String logotipoRelatorioEmp) {
        this.logotipoRelatorioEmp = logotipoRelatorioEmp;
    }


}
