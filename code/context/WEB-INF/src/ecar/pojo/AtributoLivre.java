package ecar.pojo;

import java.util.Date;


/**
 *
 * @author 70744416353
 */
public class AtributoLivre {
	
	ObjetoEstrutura atributo;	

    /** nullable persistent field */
    private Date dataInclusao;

    /** nullable persistent field */
    private String informacao; 
    
    /** nullable persistent field */
    private SisAtributoSatb sisAtributoSatb;    
    
    /**
     * @author N/CsisAtributoSatb
	 * @since N/C 
     */
    public AtributoLivre(){
    }

    /**
     * @author N/C
	 * @since N/C 
     * @return Date
     */
	public Date getDataInclusao() {
        return this.dataInclusao;
    }

	/**
	 * @author N/C
	 * @since N/C 
         * @param dataInclusao
	 */
    public void setDataInclusao(Date dataInclusao) {
        this.dataInclusao = dataInclusao;
    }

    /**
     * @author N/C
	 * @since N/C 
     * @return String
     */
    public String getInformacao() {
        return this.informacao;
    }

    /**
     * @param informacao
     * @author N/C
	 * @since N/C 
     */
    public void setInformacao(String informacao) {
        this.informacao = informacao;
    }
	
    /**
     * @author N/C
	 * @since N/C 
     * @return SisAtributoSatb
     */
    public SisAtributoSatb getSisAtributoSatb() {
        return this.sisAtributoSatb;
    }

    /**
     * @param sisAtributoSatb
     * @author N/C
	 * @since N/C 
     */
    public void setSisAtributoSatb(SisAtributoSatb sisAtributoSatb) {
        this.sisAtributoSatb = sisAtributoSatb;
    }

    /**
     *
     * @return
     */
    public ObjetoEstrutura getAtributo() {
		return atributo;
	}

    /**
     *
     * @param atributo
     */
    public void setAtributo(ObjetoEstrutura atributo) {
		this.atributo = atributo;
	}
    
}
