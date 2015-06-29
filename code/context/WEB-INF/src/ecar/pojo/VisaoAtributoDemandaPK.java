package ecar.pojo;

import java.io.Serializable;

import org.apache.commons.lang.builder.HashCodeBuilder;

/**
 *
 * @author 70744416353
 */
public class VisaoAtributoDemandaPK implements Serializable {
	
	private static final long serialVersionUID = -5549149190114022412L;
	
	private AtributoDemandaAtbdem atributoDemanda;
	
    private VisaoDemandasVisDem visao;

    /**
     *
     */
    public VisaoAtributoDemandaPK() {
    }

    
    
    
    @Override
    public boolean equals(Object other) {
        if ( (this == other ) ) return true;
        if ( !(other instanceof VisaoAtributoDemandaPK) ) return false;
        VisaoAtributoDemandaPK castOther = (VisaoAtributoDemandaPK) other;
        return atributoDemanda.equals(castOther.getAtributoDemanda()) && 
               visao.equals(castOther.getVisao());
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
            .append(getAtributoDemanda().hashCode())
            .append(getVisao().hashCode())
            .toHashCode();
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




    /**
     *
     * @return
     */
    public AtributoDemandaAtbdem getAtributoDemanda() {
		return atributoDemanda;
	}




        /**
         *
         * @param atributoDemanda
         */
        public void setAtributoDemanda(AtributoDemandaAtbdem atributoDemanda) {
		this.atributoDemanda = atributoDemanda;
	}
	
    
}
