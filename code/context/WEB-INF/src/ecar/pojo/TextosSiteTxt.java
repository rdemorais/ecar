package ecar.pojo;

import java.io.Serializable;
import java.util.Set;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;


/** @author Hibernate CodeGenerator */
public class TextosSiteTxt implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = -9093504769858791091L;

	/** identifier field */
    private Long codTxtS;

    /** nullable persistent field */
    private String textoTxts;

    /** nullable persistent field */
    private String descricaoUsoTxts;
    
    private String indAtivoTxts;
    
    /** nullable persistent field */
    private String emailResponsavelTxts;

    /** nullable persistent field */
    private ecar.pojo.IdiomaIdm idiomaIdm;

    /** nullable persistent field */
    private ecar.pojo.EmpresaEmp empresaEmp;

    /** persistent field */
    private Set configMailSms;

    /** persistent field */
    private Set configMailMail;

    /** full constructor
     * @param textoTxts
     * @param descricaoUsoTxts
     * @param empresaEmp
     * @param idiomaIdm
     */
    public TextosSiteTxt(String textoTxts, String descricaoUsoTxts, ecar.pojo.IdiomaIdm idiomaIdm, ecar.pojo.EmpresaEmp empresaEmp) {
        this.textoTxts = textoTxts;
        this.descricaoUsoTxts = descricaoUsoTxts;
        this.idiomaIdm = idiomaIdm;
        this.empresaEmp = empresaEmp;
    }

    /** default constructor */
    public TextosSiteTxt() {
    }

    /** minimal constructor
     * @param idiomaIdm
     * @param empresaEmp
     */
    public TextosSiteTxt( ecar.pojo.IdiomaIdm idiomaIdm, ecar.pojo.EmpresaEmp empresaEmp) {
        this.idiomaIdm = idiomaIdm;
        this.empresaEmp = empresaEmp;    
    }

    /**
     *
     * @return
     */
    public Long getCodTxtS() {
		return codTxtS;
	}

    /**
     *
     * @param codTxtS
     */
    public void setCodTxtS(Long codTxtS) {
		this.codTxtS = codTxtS;
	}

        /**
         *
         * @return
         */
        public String getTextoTxts() {
        return this.textoTxts;
    }

        /**
         *
         * @param textoTxts
         */
        public void setTextoTxts(String textoTxts) {
        this.textoTxts = textoTxts;
    }

        /**
         *
         * @return
         */
        public String getDescricaoUsoTxts() {
        return this.descricaoUsoTxts;
    }

    /**
     *
     * @param descricaoUsoTxts
     */
    public void setDescricaoUsoTxts(String descricaoUsoTxts) {
        this.descricaoUsoTxts = descricaoUsoTxts;
    }

    /**
     * @return Returns the indAtivoTxts.
     */
    public String getIndAtivoTxts() {
        return indAtivoTxts;
    }
    /**
     * @param indAtivoTxts The indAtivoTxts to set.
     */
    public void setIndAtivoTxts(String indAtivoTxts) {
        this.indAtivoTxts = indAtivoTxts;
    }

    /**
     *
     * @return
     */
    public ecar.pojo.IdiomaIdm getIdiomaIdm() {
        return this.idiomaIdm;
    }

    /**
     *
     * @param idiomaIdm
     */
    public void setIdiomaIdm(ecar.pojo.IdiomaIdm idiomaIdm) {
        this.idiomaIdm = idiomaIdm;
    }

    /**
     *
     * @return
     */
    public ecar.pojo.EmpresaEmp getEmpresaEmp() {
        return this.empresaEmp;
    }

    /**
     *
     * @param empresaEmp
     */
    public void setEmpresaEmp(ecar.pojo.EmpresaEmp empresaEmp) {
        this.empresaEmp = empresaEmp;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .append("textoTxts", getTextoTxts())
            .toString();
    }

    @Override
    public boolean equals(Object other) {
        if ( (this == other ) ) return true;
        if ( !(other instanceof TextosSiteTxt) ) return false;
        TextosSiteTxt castOther = (TextosSiteTxt) other;
        return new EqualsBuilder()
            .append(this.getCodTxtS(), castOther.getCodTxtS())
            .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
            .append(getCodTxtS())
            .toHashCode();
    }

    /**
     *
     * @return
     */
    public Set getConfigMailMail() {
		return configMailMail;
	}

    /**
     *
     * @param configMailMail
     */
    public void setConfigMailMail(Set configMailMail) {
		this.configMailMail = configMailMail;
	}

        /**
         *
         * @return
         */
        public Set getConfigMailSms() {
		return configMailSms;
	}

        /**
         *
         * @param configMailSms
         */
        public void setConfigMailSms(Set configMailSms) {
		this.configMailSms = configMailSms;
	}

        /**
         *
         * @return
         */
        public String getEmailResponsavelTxts() {
		return emailResponsavelTxts;
	}

        /**
         *
         * @param emailResponsavelTxts
         */
        public void setEmailResponsavelTxts(String emailResponsavelTxts) {
		this.emailResponsavelTxts = emailResponsavelTxts;
	}

}
