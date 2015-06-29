package ecar.pojo;

import java.io.Serializable;


/** @author Hibernate CodeGenerator */
public class HistoricoConfig implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 4858751700892657719L;

	/**
	 * 
	 */
	
	
	public static final Long COD_ICONE_PARA_EXIBIR_HISTORICO = 1L;
	
        /**
         *
         */
        public static final String ICONE_PADRAO_PARA_EXIBIR_HISTORICO = "/images/exibirHistorico.png";
	
        /**
         *
         */
        public static final Long COD_ICONE_PARA_OCULTAR_HISTORICO = 2L;
	
        /**
         *
         */
        public static final String ICONE_PADRAO_PARA_OCULTAR_HISTORICO = "/images/ocultarHistorico.png";
	
        /**
         *
         */
        public static final Long COD_ICONE_EXCLUSAO = 3L;
	
        /**
         *
         */
        public static final String ICONE_PADRAO_EXCLUSAO = "/images/exclusaoHistorico.png";
	
        /**
         *
         */
        public static final Long COD_ICONE_ALTERACAO = 4L;
	
        /**
         *
         */
        public static final String ICONE_PADRAO_ALTERACAO = "/images/alteracaoHistorico.png";
	
        /**
         *
         */
        public static final Long COD_ICONE_MOVIDO_ENTRE_ESTRUTURAS = 5L;
	
        /**
         *
         */
        public static final String ICONE_PADRAO_MOVIDO_ENTRE_ESTRUTURAS = "/images/movidoEntreEstruturas.png";
	
        /**
         *
         */
        public static final String ICONE_PADRAO_INCLUSAO = "/images/inclusaoHistorico.png";
	
        /**
         *
         */
        public static final Long COD_PADRAO_INCLUSAO = 6L;


	
	/** identifier field */
    private Long codHistorico;

    /** nullable persistent field */
    private String nomeHistorico;

    /** nullable persistent field */
    private String iconeHistorico;
    
    /** nullable persistent field */
    private String corHistorico;

	
    /**
     *
     * @return
     */
    public Long getCodHistorico() {
    	return codHistorico;
    }

	
    /**
     *
     * @param codHistorico
     */
    public void setCodHistorico(Long codHistorico) {
    	this.codHistorico = codHistorico;
    }

	
    /**
     *
     * @return
     */
    public String getNomeHistorico() {
    	return nomeHistorico;
    }

	
    /**
     *
     * @param nomeHistorico
     */
    public void setNomeHistorico(String nomeHistorico) {
    	this.nomeHistorico = nomeHistorico;
    }

	
    /**
     *
     * @return
     */
    public String getIconeHistorico() {
    	return iconeHistorico;
    }

	
    /**
     *
     * @param iconeHistorico
     */
    public void setIconeHistorico(String iconeHistorico) {
    	this.iconeHistorico = iconeHistorico;
    }

	
    /**
     *
     * @return
     */
    public String getCorHistorico() {
    	return corHistorico;
    }

	
    /**
     *
     * @param corHistorico
     */
    public void setCorHistorico(String corHistorico) {
    	this.corHistorico = corHistorico;
    }


	@Override
    public int hashCode() {
	    final int prime = 31;
	    int result = 1;
	    result = prime * result + ((codHistorico == null) ? 0 : codHistorico.hashCode());
	    result = prime * result + ((corHistorico == null) ? 0 : corHistorico.hashCode());
	    result = prime * result + ((iconeHistorico == null) ? 0 : iconeHistorico.hashCode());
	    result = prime * result + ((nomeHistorico == null) ? 0 : nomeHistorico.hashCode());
	    return result;
    }


	@Override
    public boolean equals(Object obj) {
	    if (this == obj)
		    return true;
	    if (obj == null)
		    return false;
	    if (!(obj instanceof HistoricoConfig))
		    return false;
	    HistoricoConfig other = (HistoricoConfig) obj;
	    if (codHistorico == null) {
		    if (other.codHistorico != null)
			    return false;
	    }
	    else
		    if (!codHistorico.equals(other.codHistorico))
			    return false;
	    if (corHistorico == null) {
		    if (other.corHistorico != null)
			    return false;
	    }
	    else
		    if (!corHistorico.equals(other.corHistorico))
			    return false;
	    if (iconeHistorico == null) {
		    if (other.iconeHistorico != null)
			    return false;
	    }
	    else
		    if (!iconeHistorico.equals(other.iconeHistorico))
			    return false;
	    if (nomeHistorico == null) {
		    if (other.nomeHistorico != null)
			    return false;
	    }
	    else
		    if (!nomeHistorico.equals(other.nomeHistorico))
			    return false;
	    return true;
    }


        /**
         *
         * @param codHistorico
         * @param nomeHistorico
         * @param iconeHistorico
         * @param corHistorico
         */
        public HistoricoConfig(Long codHistorico, String nomeHistorico, String iconeHistorico, String corHistorico) {
	    super();
	    this.codHistorico = codHistorico;
	    this.nomeHistorico = nomeHistorico;
	    this.iconeHistorico = iconeHistorico;
	    this.corHistorico = corHistorico;
    }
	
        /**
         *
         */
        public HistoricoConfig() {
	    
    }

}
