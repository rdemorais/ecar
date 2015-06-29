package ecar.pojo;

import java.io.Serializable;

import org.apache.commons.lang.builder.HashCodeBuilder;

/**
 *
 * @author 70744416353
 */
public class VisaoDemandasGrpAcessoPK implements Serializable {
	
	private static final long serialVersionUID = -5549149190114022412L;
	
	private SisAtributoSatb sisAtributo;
	
    private VisaoDemandasVisDem visao;

    /**
     *
     */
    public VisaoDemandasGrpAcessoPK() {
    }

    
    
    
    @Override
    public boolean equals(Object other) {
        if ( (this == other ) ) return true;
        if ( !(other instanceof VisaoDemandasGrpAcessoPK) ) return false;
        VisaoDemandasGrpAcessoPK castOther = (VisaoDemandasGrpAcessoPK) other;
        return sisAtributo.equals(castOther.getSisAtributo()) && 
               visao.equals(castOther.getVisao());
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
            .append(getSisAtributo().hashCode())
            .append(getVisao().hashCode())
            .toHashCode();
    }




    /**
     *
     * @return
     */
    public SisAtributoSatb getSisAtributo() {
		return sisAtributo;
	}




    /**
     *
     * @param sisAtributo
     */
    public void setSisAtributo(SisAtributoSatb sisAtributo) {
		this.sisAtributo = sisAtributo;
	}




    /**
     *
     * @return
     */
    public VisaoDemandasVisDem getVisao() {
		return visao;
	}




        /**
         *
         * @param visao
         */
        public void setVisao(VisaoDemandasVisDem visao) {
		this.visao = visao;
	}
	
    
}
