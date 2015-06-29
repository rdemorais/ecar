package ecar.intercambioDados;

public enum TipoDadosImportacao {
    
	ITEM(0, "ITEM"),
    PONTO_CRITICO(1, "PONTO CRÍTICO"),
    PARECER(2, "PARECER");

	private final int codigo;
	private String descricao;
	
	/**
	 * Construtor da enumeration
	 * @param _codigo    O codigo do registro
	 * @param _descricao A descrição do registro
	 */
	private TipoDadosImportacao(int _codigo, String _descricao) {
		this.codigo = _codigo;
		this.descricao = _descricao;
	}
	
	TipoDadosImportacao(int codigo) {
		this.codigo = codigo;
	}
	
	/**
	 * Recupera elemento da enumeration pelo codigo 
	 * @param _codigo                   O codigo pesquisado
	 * @return                          O elemento da enumeration encontrando
	 * @throws IllegalArgumentException Se não tiver constante correspondente ao código
	 */
	public static TipoDadosImportacao valueOf(int _codigo)
			throws IllegalArgumentException {
		TipoDadosImportacao value = null;
		for (TipoDadosImportacao element : TipoDadosImportacao.values()) {
			if (element.getCodigo() == _codigo) {
				value = element;
				break;
			}
		}
		if (value == null) {
			throw new IllegalArgumentException(String.valueOf(_codigo));
		}
		return value;
	}

        /**
         *
         * @return
         */
        public int getCodigo() {
		return codigo;
	}

        /**
         *
         * @return
         */
        public String getDescricao() {
		return descricao;
	}
	
}
