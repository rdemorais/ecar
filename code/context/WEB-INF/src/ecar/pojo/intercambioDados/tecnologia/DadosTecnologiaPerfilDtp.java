package ecar.pojo.intercambioDados.tecnologia;

import java.io.Serializable;

/**
 * 
 * Dados da tecnologia no perfil
 *
 */
public abstract class DadosTecnologiaPerfilDtp implements Serializable {
    
    /**
     * 
     */
    private static final long serialVersionUID = 8749956096174806700L;
    /**
     * Código da tecnologia
     * */
    private Long codDtp;
    /**
     * Codificação da tecnologia
     */
    private String encodeDtp;

    /**
     * Tipo da tecnologia
     */
    protected TipoTecnologiaEnum tipoTecnologia;

    public TipoTecnologiaEnum getTipoTecnologia() {
		return tipoTecnologia;
	}

	protected abstract void setTipoTecnologia(TipoTecnologiaEnum tipoTecnologia);

	/**
     * @return the codDtp
     */
    public Long getCodDtp() {
        return codDtp;
    }

    /**
     * @param codDtp the codDtp to set
     */
    public void setCodDtp(Long codDtp) {
        this.codDtp = codDtp;
    }

    /**
     * @return the encodeDtp
     */
    public String getEncodeDtp() {
        return encodeDtp;
    }

    /**
     * @param encodeDtp the encodeDtp to set
     */
    public void setEncodeDtp(String encodeDtp) {
        this.encodeDtp = encodeDtp;
    }

    /**
     * @return the serialversionuid
     */
    public static long getSerialversionuid() {
        return serialVersionUID;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
	final int prime = 31;
	int result = 1;
	result = prime * result + ((codDtp == null) ? 0 : codDtp.hashCode());
	result = prime * result
		+ ((encodeDtp == null) ? 0 : encodeDtp.hashCode());
	
	return result;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object obj) {
	if (this == obj) {
	    return true;
	}
	if (obj == null) {
	    return false;
	}
	if (!(obj instanceof DadosTecnologiaPerfilDtp)) {
	    return false;
	}
	DadosTecnologiaPerfilDtp other = (DadosTecnologiaPerfilDtp) obj;
	if (codDtp == null) {
	    if (other.codDtp != null) {
		return false;
	    }
	} else if (!codDtp.equals(other.codDtp)) {
	    return false;
	}
	if (encodeDtp == null) {
	    if (other.encodeDtp != null) {
		return false;
	    }
	} else if (!encodeDtp.equals(other.encodeDtp)) {
	    return false;
	}
	return true;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
	return "DadosTecnologiaPerfilDtp [codDtp=" + codDtp + ", encodeDtp="
		+ encodeDtp + "]";
    }
    
}
