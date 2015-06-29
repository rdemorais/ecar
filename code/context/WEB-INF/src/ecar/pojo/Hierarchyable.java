package ecar.pojo;

/**
 *
 * @author 70744416353
 */
public interface Hierarchyable {

	
    /**
     *
     * @return
     */
    public Long iGetCodigo();
	
        /**
         *
         * @return
         */
        public String iGetNome();
	
        /**
         *
         * @return
         */
        public Hierarchyable iGetPai();
	
        /**
         *
         * @return
         */
        public Hierarchyable iGetEstrutura();
}
