package ecar.pojo;

import java.io.Serializable;

/**
 *
 * @author 70744416353
 */
public class TipoFuncAcompTpfaPermiteAlterar implements Serializable{

	private ecar.pojo.TipoFuncAcompTpfaPermiteAlterarPK comp_id;
	private TipoFuncAcompTpfa tipoFuncAcompTpfaSuperior;
	private TipoFuncAcompTpfa tipoFuncAcompTpfaInferior;
	private String permiteAlterarParecer;

	
        /**
         *
         * @return
         */
        public ecar.pojo.TipoFuncAcompTpfaPermiteAlterarPK getComp_id() {
		return comp_id;
	}
        /**
         *
         * @param comp_id
         */
        public void setComp_id(ecar.pojo.TipoFuncAcompTpfaPermiteAlterarPK comp_id) {
		this.comp_id = comp_id;
	}
        /**
         *
         * @return
         */
        public TipoFuncAcompTpfa getTipoFuncAcompTpfaSuperior() {
		return tipoFuncAcompTpfaSuperior;
	}
        /**
         *
         * @param tipoFuncAcompTpfaSuperior
         */
        public void setTipoFuncAcompTpfaSuperior(TipoFuncAcompTpfa tipoFuncAcompTpfaSuperior) {
		this.tipoFuncAcompTpfaSuperior = tipoFuncAcompTpfaSuperior;
	}
        /**
         *
         * @return
         */
        public TipoFuncAcompTpfa getTipoFuncAcompTpfaInferior() {
		return tipoFuncAcompTpfaInferior;
	}
        /**
         *
         * @param tipoFuncAcompTpfaInferior
         */
        public void setTipoFuncAcompTpfaInferior(TipoFuncAcompTpfa tipoFuncAcompTpfaInferior) {
		this.tipoFuncAcompTpfaInferior = tipoFuncAcompTpfaInferior;
	}
        @Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + ((comp_id == null) ? 0 : comp_id.hashCode());
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
			TipoFuncAcompTpfaPermiteAlterar other = (TipoFuncAcompTpfaPermiteAlterar) obj;
			if (comp_id == null) {
				if (other.comp_id != null)
					return false;
			} else if (!comp_id.equals(other.comp_id))
				return false;
			return true;
		}
		/**
         *
         * @return
         */
        public String getPermiteAlterarParecer() {
		return permiteAlterarParecer;
	}
        /**
         *
         * @param permiteAlterarParecer
         */
        public void setPermiteAlterarParecer(String permiteAlterarParecer) {
		this.permiteAlterarParecer = permiteAlterarParecer;
	}
}
