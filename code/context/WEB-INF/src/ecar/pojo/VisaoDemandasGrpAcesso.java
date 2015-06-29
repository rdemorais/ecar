package ecar.pojo;

import java.io.Serializable;

/**
 *
 * @author 70744416353
 */
public class VisaoDemandasGrpAcesso implements Serializable {
	
	private static final long serialVersionUID = -5549149190114022412L;
		
    private VisaoDemandasGrpAcessoPK visaoDemandasGrpAcessoPk;

    /**
     *
     */
    public VisaoDemandasGrpAcesso() {
    }

    /**
     *
     * @return
     */
    public VisaoDemandasGrpAcessoPK getVisaoDemandasGrpAcessoPk() {
		return visaoDemandasGrpAcessoPk;
	}

        /**
         *
         * @param visaoDemandasGrpAcessoPk
         */
        public void setVisaoDemandasGrpAcessoPk(VisaoDemandasGrpAcessoPK visaoDemandasGrpAcessoPk) {
		this.visaoDemandasGrpAcessoPk = visaoDemandasGrpAcessoPk;
	}

	
    
}
