package ecar.pojo;

import java.io.Serializable;
import java.util.Set;

/** @author Hibernate CodeGenerator */
public class ServicoSer implements Serializable {

    /**
     *
     */
    public static final String TIPO_ATUALIZACAO_REALIZADO_AUTOMATICO = "A";
    /**
     *
     */
    public static final String TIPO_ATUALIZACAO_REALIZADO_MANUAL = "M";
	
    /**
	 * 
	 */
	private static final long serialVersionUID = 5528105977946214930L;

	/** identifier field */
    private Long codServicoSer;
    private String nomeSer;
    private String urlSer;
    private String entidadeDisponibilizadoraSer;
    private Set servicoParametros;
    
    
    /** full constructor
     * @param codServicoSer
     * @param urlSer
     * @param nomeSer
     * @param servicoParametros
     * @param entidadeDisponibilizadoraSer
     */
	public ServicoSer(Long codServicoSer, String nomeSer, String urlSer,
			String entidadeDisponibilizadoraSer, Set servicoParametros) {
		super();
		this.codServicoSer = codServicoSer;
		this.urlSer = urlSer;
		this.entidadeDisponibilizadoraSer = entidadeDisponibilizadoraSer;
		this.servicoParametros = servicoParametros;
	}
	
	/** 
     * default constructor.<br>
     * 
     * @author N/C
     * @since N/C 
     */
	public ServicoSer() {
		super();
	}

	/**
	 * 
	 * @return
	 */
	public Long getCodServicoSer() {
		return codServicoSer;
	}
	
	/**
	 * 
         *
         * @param codServicoSer
         */
	public void setCodServicoSer(Long codServicoSer) {
		this.codServicoSer = codServicoSer;
	}
	
	/**
	 * 
	 * @return
	 */
	public String getUrlSer() {
		return urlSer;
	}
	
	/**
	 * 
         *
         * @param urlSer
         */
	public void setUrlSer(String urlSer) {
		this.urlSer = urlSer;
	}
	
	/**
	 * 
	 * @return
	 */
	public String getEntidadeDisponibilizadoraSer() {
		return entidadeDisponibilizadoraSer;
	}
	
	/**
	 * 
         *
         * @param entidadeDisponibilizadoraSer
         */
	public void setEntidadeDisponibilizadoraSer(String entidadeDisponibilizadoraSer) {
		this.entidadeDisponibilizadoraSer = entidadeDisponibilizadoraSer;
	}
	
	
    
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((codServicoSer == null) ? 0 : codServicoSer.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		final ServicoSer other = (ServicoSer) obj;
		if (codServicoSer == null) {
			if (other.codServicoSer != null)
				return false;
		} else if (!codServicoSer.equals(other.codServicoSer))
			return false;
		return true;
	}

        /**
         *
         * @return
         */
        public String getNomeSer() {
		return nomeSer;
	}

        /**
         *
         * @param nomeSer
         */
        public void setNomeSer(String nomeSer) {
		this.nomeSer = nomeSer;
	}

        /**
         *
         * @return
         */
        public Set getServicoParametros() {
		return servicoParametros;
	}

        /**
         *
         * @param servicoParametros
         */
        public void setServicoParametros(Set servicoParametros) {
		this.servicoParametros = servicoParametros;
	}
}
