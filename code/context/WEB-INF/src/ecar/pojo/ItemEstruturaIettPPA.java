package ecar.pojo;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;


/** 
 * @author Hibernate CodeGenerator, rogerio
 * @since n/c
 * @version 0.2, 02/06/2007
 */
public class ItemEstruturaIettPPA implements Cloneable, Serializable {



	private static final long serialVersionUID = -1936097626324442629L;


	/** identifier field */
    private Long codIett;

    /** nullable persistent field */
    private String objetivoEspecificoIett;

    /** nullable persistent field */
    private String objetivoGeralIett;


    /** nullable persistent field */
    private Date dataTerminoIett;

    /** nullable persistent field */
    private Date dataInicioIett;

    /** nullable persistent field */
    private String indAtivoIett;

    /** nullable persistent field */
    private String descricaoIett;

    /** nullable persistent field */
    private String siglaIett;

    /** nullable persistent field */
    private String descricaoR2;

    /** nullable persistent field */
    private Integer nivelIett;

    private String nomeIett;

    /** persistent field */
    private ecar.pojo.EstruturaEtt estruturaEtt;

    /** persistent field */
    private ecar.pojo.ItemEstruturaIettPPA itemEstruturaIettPPA;

    /** persistent field */
    private Set itemEstruturaIetts;

    /** persistent field */
    private Set itemEstrtIndResulIettrs;    
    
    /** persistent field */
    private ecar.pojo.OrgaoOrg orgaoOrgByCodOrgaoResponsavel1Iett;
    
    private Set itemEstruturaSisAtributoIettSatbs;
    
    
    
    /**
     *
     * @return
     */
    public ecar.pojo.ItemEstruturaIettPPA getItemEstruturaIettPPA() {
		return itemEstruturaIettPPA;
	}

    /**
     *
     * @param itemEstruturaIettPPA
     */
    public void setItemEstruturaIettPPA(
			ecar.pojo.ItemEstruturaIettPPA itemEstruturaIettPPA) {
		this.itemEstruturaIettPPA = itemEstruturaIettPPA;
	}

        /**
         *
         * @return
         */
        public Set getItemEstruturaSisAtributoIettSatbs() {
		return itemEstruturaSisAtributoIettSatbs;
	}

        /**
         *
         * @param itemEstruturaSisAtributoIettSatbs
         */
        public void setItemEstruturaSisAtributoIettSatbs(
			Set itemEstruturaSisAtributoIettSatbs) {
		this.itemEstruturaSisAtributoIettSatbs = itemEstruturaSisAtributoIettSatbs;
	}

        /**
         *
         * @return
         */
        public ecar.pojo.OrgaoOrg getOrgaoOrgByCodOrgaoResponsavel1Iett() {
		return orgaoOrgByCodOrgaoResponsavel1Iett;
	}

        /**
         *
         * @param orgaoOrgByCodOrgaoResponsavel1Iett
         */
        public void setOrgaoOrgByCodOrgaoResponsavel1Iett(
			ecar.pojo.OrgaoOrg orgaoOrgByCodOrgaoResponsavel1Iett) {
		this.orgaoOrgByCodOrgaoResponsavel1Iett = orgaoOrgByCodOrgaoResponsavel1Iett;
	}

        /**
         *
         * @return
         */
        public Set getItemEstrtIndResulIettrs() {
		return itemEstrtIndResulIettrs;
	}

        /**
         *
         * @param itemEstrtIndResulIettrs
         */
        public void setItemEstrtIndResulIettrs(Set itemEstrtIndResulIettrs) {
		this.itemEstrtIndResulIettrs = itemEstrtIndResulIettrs;
	}

        /**
         *
         * @return
         */
        public Long getCodIett() {
		return codIett;
	}

        /**
         *
         * @param codIett
         */
        public void setCodIett(Long codIett) {
		this.codIett = codIett;
	}

        /**
         *
         * @return
         */
        public Date getDataInicioIett() {
		return dataInicioIett;
	}

        /**
         *
         * @param dataInicioIett
         */
        public void setDataInicioIett(Date dataInicioIett) {
		this.dataInicioIett = dataInicioIett;
	}

        /**
         *
         * @return
         */
        public Date getDataTerminoIett() {
		return dataTerminoIett;
	}

        /**
         *
         * @param dataTerminoIett
         */
        public void setDataTerminoIett(Date dataTerminoIett) {
		this.dataTerminoIett = dataTerminoIett;
	}

        /**
         *
         * @return
         */
        public String getDescricaoIett() {
		return descricaoIett;
	}

        /**
         *
         * @param descricaoIett
         */
        public void setDescricaoIett(String descricaoIett) {
		this.descricaoIett = descricaoIett;
	}

        /**
         *
         * @return
         */
        public String getDescricaoR2() {
		return descricaoR2;
	}

        /**
         *
         * @param descricaoR2
         */
        public void setDescricaoR2(String descricaoR2) {
		this.descricaoR2 = descricaoR2;
	}

        /**
         *
         * @return
         */
        public ecar.pojo.EstruturaEtt getEstruturaEtt() {
		return estruturaEtt;
	}

        /**
         *
         * @param estruturaEtt
         */
        public void setEstruturaEtt(ecar.pojo.EstruturaEtt estruturaEtt) {
		this.estruturaEtt = estruturaEtt;
	}

        /**
         *
         * @return
         */
        public String getIndAtivoIett() {
		return indAtivoIett;
	}

        /**
         *
         * @param indAtivoIett
         */
        public void setIndAtivoIett(String indAtivoIett) {
		this.indAtivoIett = indAtivoIett;
	}

        /**
         *
         * @return
         */
        public ecar.pojo.ItemEstruturaIettPPA getItemEstruturaIett() {
		return itemEstruturaIettPPA;
	}

        /**
         *
         * @param itemEstruturaIett
         */
        public void setItemEstruturaIett(
			ecar.pojo.ItemEstruturaIettPPA itemEstruturaIett) {
		this.itemEstruturaIettPPA = itemEstruturaIett;
	}

        /**
         *
         * @return
         */
        public Set getItemEstruturaIetts() {
		return itemEstruturaIetts;
	}

        /**
         *
         * @param itemEstruturaIetts
         */
        public void setItemEstruturaIetts(Set itemEstruturaIetts) {
		this.itemEstruturaIetts = itemEstruturaIetts;
	}

        /**
         *
         * @return
         */
        public Integer getNivelIett() {
		return nivelIett;
	}

        /**
         *
         * @param nivelIett
         */
        public void setNivelIett(Integer nivelIett) {
		this.nivelIett = nivelIett;
	}

        /**
         *
         * @return
         */
        public String getNomeIett() {
		return nomeIett;
	}

        /**
         *
         * @param nomeIett
         */
        public void setNomeIett(String nomeIett) {
		this.nomeIett = nomeIett;
	}

        /**
         *
         * @return
         */
        public String getObjetivoEspecificoIett() {
		return objetivoEspecificoIett;
	}

        /**
         *
         * @param objetivoEspecificoIett
         */
        public void setObjetivoEspecificoIett(String objetivoEspecificoIett) {
		this.objetivoEspecificoIett = objetivoEspecificoIett;
	}

        /**
         *
         * @return
         */
        public String getObjetivoGeralIett() {
		return objetivoGeralIett;
	}

        /**
         *
         * @param objetivoGeralIett
         */
        public void setObjetivoGeralIett(String objetivoGeralIett) {
		this.objetivoGeralIett = objetivoGeralIett;
	}

        /**
         *
         * @return
         */
        public String getSiglaIett() {
		return siglaIett;
	}

        /**
         *
         * @param siglaIett
         */
        public void setSiglaIett(String siglaIett) {
		this.siglaIett = siglaIett;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((codIett == null) ? 0 : codIett.hashCode());
		result = prime * result + ((dataInicioIett == null) ? 0 : dataInicioIett.hashCode());
		result = prime * result + ((dataTerminoIett == null) ? 0 : dataTerminoIett.hashCode());
		result = prime * result + ((descricaoIett == null) ? 0 : descricaoIett.hashCode());
		result = prime * result + ((descricaoR2 == null) ? 0 : descricaoR2.hashCode());
		result = prime * result + ((estruturaEtt == null) ? 0 : estruturaEtt.hashCode());
		result = prime * result + ((indAtivoIett == null) ? 0 : indAtivoIett.hashCode());
		result = prime * result + ((itemEstrtIndResulIettrs == null) ? 0 : itemEstrtIndResulIettrs.hashCode());
		result = prime * result + ((itemEstruturaIettPPA == null) ? 0 : itemEstruturaIettPPA.hashCode());
		result = prime * result + ((itemEstruturaIetts == null) ? 0 : itemEstruturaIetts.hashCode());
		result = prime * result + ((itemEstruturaSisAtributoIettSatbs == null) ? 0 : itemEstruturaSisAtributoIettSatbs.hashCode());
		result = prime * result + ((nivelIett == null) ? 0 : nivelIett.hashCode());
		result = prime * result + ((nomeIett == null) ? 0 : nomeIett.hashCode());
		result = prime * result + ((objetivoEspecificoIett == null) ? 0 : objetivoEspecificoIett.hashCode());
		result = prime * result + ((objetivoGeralIett == null) ? 0 : objetivoGeralIett.hashCode());
		result = prime * result + ((orgaoOrgByCodOrgaoResponsavel1Iett == null) ? 0 : orgaoOrgByCodOrgaoResponsavel1Iett.hashCode());
		result = prime * result + ((siglaIett == null) ? 0 : siglaIett.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof ItemEstruturaIettPPA))
			return false;
		ItemEstruturaIettPPA other = (ItemEstruturaIettPPA) obj;
		if (codIett == null) {
			if (other.codIett != null)
				return false;
		} else if (!codIett.equals(other.codIett))
			return false;
		if (dataInicioIett == null) {
			if (other.dataInicioIett != null)
				return false;
		} else if (!dataInicioIett.equals(other.dataInicioIett))
			return false;
		if (dataTerminoIett == null) {
			if (other.dataTerminoIett != null)
				return false;
		} else if (!dataTerminoIett.equals(other.dataTerminoIett))
			return false;
		if (descricaoIett == null) {
			if (other.descricaoIett != null)
				return false;
		} else if (!descricaoIett.equals(other.descricaoIett))
			return false;
		if (descricaoR2 == null) {
			if (other.descricaoR2 != null)
				return false;
		} else if (!descricaoR2.equals(other.descricaoR2))
			return false;
		if (estruturaEtt == null) {
			if (other.estruturaEtt != null)
				return false;
		} else if (!estruturaEtt.equals(other.estruturaEtt))
			return false;
		if (indAtivoIett == null) {
			if (other.indAtivoIett != null)
				return false;
		} else if (!indAtivoIett.equals(other.indAtivoIett))
			return false;
		if (itemEstrtIndResulIettrs == null) {
			if (other.itemEstrtIndResulIettrs != null)
				return false;
		} else if (!itemEstrtIndResulIettrs.equals(other.itemEstrtIndResulIettrs))
			return false;
		if (itemEstruturaIettPPA == null) {
			if (other.itemEstruturaIettPPA != null)
				return false;
		} else if (!itemEstruturaIettPPA.equals(other.itemEstruturaIettPPA))
			return false;
		if (itemEstruturaIetts == null) {
			if (other.itemEstruturaIetts != null)
				return false;
		} else if (!itemEstruturaIetts.equals(other.itemEstruturaIetts))
			return false;
		if (itemEstruturaSisAtributoIettSatbs == null) {
			if (other.itemEstruturaSisAtributoIettSatbs != null)
				return false;
		} else if (!itemEstruturaSisAtributoIettSatbs.equals(other.itemEstruturaSisAtributoIettSatbs))
			return false;
		if (nivelIett == null) {
			if (other.nivelIett != null)
				return false;
		} else if (!nivelIett.equals(other.nivelIett))
			return false;
		if (nomeIett == null) {
			if (other.nomeIett != null)
				return false;
		} else if (!nomeIett.equals(other.nomeIett))
			return false;
		if (objetivoEspecificoIett == null) {
			if (other.objetivoEspecificoIett != null)
				return false;
		} else if (!objetivoEspecificoIett.equals(other.objetivoEspecificoIett))
			return false;
		if (objetivoGeralIett == null) {
			if (other.objetivoGeralIett != null)
				return false;
		} else if (!objetivoGeralIett.equals(other.objetivoGeralIett))
			return false;
		if (orgaoOrgByCodOrgaoResponsavel1Iett == null) {
			if (other.orgaoOrgByCodOrgaoResponsavel1Iett != null)
				return false;
		} else if (!orgaoOrgByCodOrgaoResponsavel1Iett.equals(other.orgaoOrgByCodOrgaoResponsavel1Iett))
			return false;
		if (siglaIett == null) {
			if (other.siglaIett != null)
				return false;
		} else if (!siglaIett.equals(other.siglaIett))
			return false;
		return true;
	}

    


}
