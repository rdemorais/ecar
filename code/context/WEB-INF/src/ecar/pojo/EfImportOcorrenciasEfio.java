package ecar.pojo;

import java.io.Serializable;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

import ecar.enumerator.TipoOcorrencia;


/**
 *
 * @author 70744416353
 */
public class EfImportOcorrenciasEfio implements Serializable {

	private static final long serialVersionUID = 6952721018172184119L;
	
    //Atributos não persistente (apenas de controle)
    private TipoOcorrencia tipoOcorrencia;
    private String conta = "";
	
	/** COD_EFIO - Primary Key (PK) */
    private Long codEfio;

    /** DESCRICAO_EFIO */
    private String descricaoEfio;

    /** COD_IMP - Foreign Key - <TB_Importacao_Imp> */
    private ImportacaoImp importacaoImp;
    
    /**
     *
     */
    public EfImportOcorrenciasEfio() {}

    /**
     *
     * @param codEfio
     * @param descricaoEfio
     * @param importacaoImp
     */
    public EfImportOcorrenciasEfio(Long codEfio, String descricaoEfio, ImportacaoImp importacaoImp) {
    	
		this.codEfio = codEfio;
		this.descricaoEfio = descricaoEfio;
		this.importacaoImp = importacaoImp;
	}

    /**
     *
     * @return
     */
    public Long getCodEfio() {
		return codEfio;
	}

        /**
         *
         * @param codEfio
         */
        public void setCodEfio(Long codEfio) {
		this.codEfio = codEfio;
	}

        /**
         *
         * @return
         */
        public String getDescricaoEfio() {
		return descricaoEfio;
	}

        /**
         *
         * @param descricaoEfio
         */
        public void setDescricaoEfio(String descricaoEfio) {
		this.descricaoEfio = descricaoEfio;
	}

        /**
         *
         * @return
         */
        public ImportacaoImp getImportacaoImp() {
		return importacaoImp;
	}

        /**
         *
         * @param importacaoImp
         */
        public void setImportacaoImp(ImportacaoImp importacaoImp) {
		this.importacaoImp = importacaoImp;
	}

    @Override
	public String toString() {
        return new ToStringBuilder(this)
            .append("codEfio", getCodEfio())
            .toString();
    }

    @Override
    public boolean equals(Object other) {
        if ( (this == other ) ) return true;
        if ( !(other instanceof EfImportOcorrenciasEfio) ) return false;
        EfImportOcorrenciasEfio castOther = (EfImportOcorrenciasEfio) other;
        return new EqualsBuilder()
            .append(this.getCodEfio(), castOther.getCodEfio())
            .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
            .append(getCodEfio())
            .toHashCode();
    }
    
    /**
     *
     * @return
     */
    public TipoOcorrencia getTipoOcorrencia() {
		return tipoOcorrencia;
	}

    /**
     *
     * @param tipoOcorrencia
     */
    public void setTipoOcorrencia(TipoOcorrencia tipoOcorrencia) {
		this.tipoOcorrencia = tipoOcorrencia;
	}

        /**
         *
         * @return
         */
        public String getConta() {
		return conta;
	}

        /**
         *
         * @param conta
         */
        public void setConta(String conta) {
		this.conta = conta;
	}

}
