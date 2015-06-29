package comum.database;

import java.util.Collection;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;

import comum.util.ConstantesECAR;

/**
 * Fornece suporte as operações de pesquisa, montando o Filtro que utiliza o Criterion do hibernate
 * 
 */
public abstract class Filter {

    /**
     *
     */
    public static final String OU = "ou";
    /**
     *
     */
    public static final String AND = "and";
        /**
         *
         */
        public static final String NULL = "null";
        /**
         *
         */
        public static final String EQUAL = "eq";
        /**
         *
         */
        public static final String LIKE = "like";
        /**
         *
         */
        public static final String IN = "in";
	
	
	private Criterion primeiroCriterio;
	private Criterion segundoCriterio;
	private String operacao;
	private String[] campo;

	
	/**
	 * 
	 * @param campo
	 * @param primeiroCriterio
	 * @param primeiroValor
	 * @param segundoCriterio
	 * @param segundoValor
	 */
	public void addOrClause (String campo,String primeiroCriterio,Object primeiroValor,String segundoCriterio,Object segundoValor) {
		this.operacao = OU;
		
		this.configuraCampo(campo);
		
		this.configuraPrimeiroCriterio(primeiroCriterio,primeiroValor);
		this.configuraSegundoCriterio(segundoCriterio,segundoValor);
	}


        /**
         *
         * @param campo
         * @param valor
         */
        public void addLikeClause(String campo, Object valor) {
		this.configuraCampo(campo);

		if (primeiroCriterio == null) {
			configuraPrimeiroCriterio(LIKE,valor);
		} else {
			configuraSegundoCriterio(LIKE,valor);
		}
	}

        /**
         *
         * @param campo
         * @param valor
         */
        public void addInClause(String campo, Object valor) {
		this.configuraCampo(campo);

		if (primeiroCriterio == null) {
			configuraPrimeiroCriterio(IN,valor);
		} else {
			configuraSegundoCriterio(IN,valor);
		}
	}	

	
        /**
         *
         */
        public void addAndOperator() {
		this.operacao = AND;		
	}
	

	private void configuraCampo(String campo) {
		
		if (this.campo == null) { 
			this.campo = new String[Integer.parseInt(ConstantesECAR.DOIS)];
		}
		
		this.campo[0] = campo;
		this.campo[1] = campo;
	}
	
	
        /**
         *
         * @return
         */
        public Criterion getPrimeiroCriterio() {
		return primeiroCriterio;
	}

        /**
         *
         * @param primeiroCriterio
         */
        public void setPrimeiroCriterio(Criterion primeiroCriterio) {
		this.primeiroCriterio = primeiroCriterio;
	}

        /**
         *
         * @return
         */
        public Criterion getSegundoCriterio() {
		return segundoCriterio;
	}

        /**
         *
         * @param segundoCriterio
         */
        public void setSegundoCriterio(Criterion segundoCriterio) {
		this.segundoCriterio = segundoCriterio;
	}
	
        /**
         *
         * @return
         */
        public String getOperacao() {
		return operacao;
	}


	private void configuraPrimeiroCriterio(String criterio, Object valor) {

		if (criterio != null) {
			if (criterio.equals(Filter.NULL)) {
				this.primeiroCriterio = Restrictions.isNull(this.campo[0]);
			} else if (criterio.equals(Filter.EQUAL)) {
				this.primeiroCriterio = Restrictions.eq(this.campo[0],valor);	
			} else if (criterio.equals(Filter.LIKE)) {
				this.primeiroCriterio = Restrictions.like(this.campo[0], valor);	
			} else if (criterio.equals(Filter.IN)) {
				this.primeiroCriterio = Restrictions.in(this.campo[0], (Collection<Integer>)valor);	
			} 
		}
		
	}
	
	private void configuraSegundoCriterio(String criterio, Object valor) {

		if (criterio != null) {
			if (criterio.equals(Filter.NULL)) {
				this.segundoCriterio = Restrictions.isNull(this.campo[1]);
			} else if (criterio.equals(Filter.EQUAL)) {
				this.segundoCriterio = Restrictions.eq(this.campo[1],valor);	
			} else if (criterio.equals(Filter.LIKE)) {
				this.segundoCriterio = Restrictions.like(campo[1], valor);	
			} else if (criterio.equals(Filter.IN)) {
				this.primeiroCriterio = Restrictions.in(this.campo[0], (Collection<Integer>)valor);	
			}
		}
		
	}

}
