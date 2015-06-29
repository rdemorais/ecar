package ecar.intercambioDados.importacao.comunicacao;

public enum TipoRegistroEnum {
    HEADER_ARQUIVO(0,"Header Arquivo"),
    HEADER_ITEM(1, "Header Item"),
    TRAILER_ITEM(8, "Trailer Item"),
    TRAILER_ARQUIVO(9, "Trailer Arquivo");

	private final int codigo;
	private String descricao;
	
	/**
	 * Construtor da enumeration
	 * @param _codigo    O codigo do registro
	 * @param _descricao A descrição do registro
	 */
	private TipoRegistroEnum(int _codigo, String _descricao) {
		this.codigo = _codigo;
		this.descricao = _descricao;
	}
	
	TipoRegistroEnum(int codigo) {
		this.codigo = codigo;
	}
	
	/**
	 * Recupera elemento da enumeration pelo codigo 
	 * @param _codigo                   O codigo pesquisado
	 * @return                          O elemento da enumeration encontrando
	 * @throws IllegalArgumentException Se não tiver constante correspondente ao código
	 */
	public static TipoRegistroEnum valueOf(int _codigo)
			throws IllegalArgumentException {
		TipoRegistroEnum value = null;
		for (TipoRegistroEnum element : TipoRegistroEnum.values()) {
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
