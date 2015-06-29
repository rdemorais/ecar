package ecar.pojo;


/**
 * 
 * @author 70744416353
 */
public enum TipoValorEnum {
    MES(0,"Mês"),
    ANO(1, "Ano"),
    SEQUENCIAL(2, "Sequêncial"),
    MASCARA(3, "Máscara");
	
	private final int codigo;
	private String descricao;
	
	/**
	 * Construtor da enumeration
	 * @param _codigo    O codigo do registro
	 * @param _descricao A descrição do registro
	 */
	private TipoValorEnum(int _codigo, String _descricao) {
		this.codigo = _codigo;
		this.descricao = _descricao;
	}
	
	TipoValorEnum(int codigo) {
		this.codigo = codigo;
	}
	
	/**
	 * Recupera elemento da enumeration pelo codigo 
	 * @param _codigo                   O codigo pesquisado
	 * @return                          O elemento da enumeration encontrando
	 * @throws IllegalArgumentException Se não tiver constante correspondente ao código
	 */
	public static TipoValorEnum valueOf(int _codigo)
			throws IllegalArgumentException {
		TipoValorEnum value = null;
		for (TipoValorEnum element : TipoValorEnum.values()) {
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
