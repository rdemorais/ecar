package ecar.pojo;

 

import java.util.List;


/**
 *
 * @author 70744416353
 */
public class NoArvoreEstrutura {

	Hierarchyable elemento;
	List listaFilhos;
	
        /**
         *
         * @return
         */
        public Hierarchyable getElemento() {
		return elemento;
	}
        /**
         *
         * @param elemento
         */
        public void setElemento(Hierarchyable elemento) {
		this.elemento = elemento;
	}
        /**
         *
         * @return
         */
        public List getListaFilhos() {
		return listaFilhos;
	}
        /**
         *
         * @param listaFilhos
         */
        public void setListaFilhos(List listaFilhos) {
		this.listaFilhos = listaFilhos;
	}
	
	
}
