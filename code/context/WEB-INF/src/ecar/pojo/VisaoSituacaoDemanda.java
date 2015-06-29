package ecar.pojo;

import java.io.Serializable;

import org.apache.commons.lang.builder.EqualsBuilder;


public class VisaoSituacaoDemanda implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8757881930531246123L;
	private VisaoSituacaoDemandaPK id;
	private SitDemandaSitd sitDemandaSitd;
	private VisaoDemandasVisDem visaoDemandas;
	private Boolean indAlterar;
	private Boolean indConsultar;
	
	public VisaoSituacaoDemanda() {
		id = new VisaoSituacaoDemandaPK();
	}

	public VisaoSituacaoDemanda(VisaoSituacaoDemandaPK id) {
		this.setId(id);
	}

	public VisaoSituacaoDemanda(VisaoSituacaoDemandaPK id,
			SitDemandaSitd tbSitDemandaSitd, VisaoDemandasVisDem tbVisaoDemandas,
			Boolean indAlterar, Boolean indConsultar) {
		this.setId(id);
		this.setSitDemandaSitd(tbSitDemandaSitd);
		this.setVisaoDemandas(tbVisaoDemandas);
		this.indAlterar = indAlterar;
		this.indConsultar = indConsultar;
	}



	public Boolean getIndAlterar() {
		return this.indAlterar;
	}

	public void setIndAlterar(Boolean indAlterar) {
		this.indAlterar = indAlterar;
	}

	public Boolean getIndConsultar() {
		return this.indConsultar;
	}

	public void setIndConsultar(Boolean indConsultar) {
		this.indConsultar = indConsultar;
	}

	public void setId(VisaoSituacaoDemandaPK id) {
		this.id = id;
	}

	public VisaoSituacaoDemandaPK getId() {
		return id;
	}

	public void setSitDemandaSitd(SitDemandaSitd sitDemandaSitd) {
		this.sitDemandaSitd = sitDemandaSitd;
	}

	public SitDemandaSitd getSitDemandaSitd() {
		return sitDemandaSitd;
	}

	public void setVisaoDemandas(VisaoDemandasVisDem visaoDemandas) {
		this.visaoDemandas = visaoDemandas;
	}

	public VisaoDemandasVisDem getVisaoDemandas() {
		return visaoDemandas;
	}
	
	public boolean equals(Object other) {
    	if ( (this == other ) ) return true;
        if ( !(other instanceof VisaoSituacaoDemanda) ) return false;
        VisaoSituacaoDemanda castOther = (VisaoSituacaoDemanda) other;
        return new EqualsBuilder()
	        .append(this.getSitDemandaSitd(), castOther.getSitDemandaSitd())
	        .append(this.getVisaoDemandas(), castOther.getVisaoDemandas())
	        .isEquals();
    }

}