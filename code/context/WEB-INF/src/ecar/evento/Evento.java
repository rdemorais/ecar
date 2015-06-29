package ecar.evento;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author 70744416353
 */
public class Evento {
	
    /**
     *
     */
    protected Integer id;

        /**
         *
         */
        protected static List<String> parametros = new ArrayList<String>();
	
        /**
         *
         */
        public Evento() {
		// TODO Auto-generated constructor stub
	}

        /**
         *
         * @param id
         */
        public Evento(Integer id) {
		this.id = id;
	}
	
        /**
         *
         * @return
         */
        public Integer getId() {
		return this.id;
	}
	
        /**
         *
         * @return
         */
        public List<String> getParametros() {
		return parametros;
	}
}