package ecar.bean;

/**
 *
 * @author 70744416353
 */
public class DetalhamentoTotaisConta {
	
/**
 *A primeira linha refere-se aos valores totalizados das contas importadas
 *via arquivo txt.
 *A segunda linha refere-se aos valores totalizados das 
 *contas inseridas manualmente no Sistema. 
 */
	
	private Double[][] detalhesContas = new Double[2][6];
	
        /**
         *
         */
        public DetalhamentoTotaisConta() {

		for(int linha=0; linha<2 ; linha++) {
			for(int coluna=0; coluna<6; coluna++) {
				detalhesContas[linha][coluna] = new Double("0");	
			}
		}
		
	}

        /**
         *
         * @return
         */
        public Double[][] getDetalhesContas() {
		return detalhesContas;
	}

        /**
         *
         * @param detalhesContas
         */
        public void setDetalhesContas(Double[][] detalhesContas) {
		this.detalhesContas = detalhesContas;
	}
}
