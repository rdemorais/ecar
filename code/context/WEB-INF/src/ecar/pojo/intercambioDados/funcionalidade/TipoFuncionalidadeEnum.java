package ecar.pojo.intercambioDados.funcionalidade;


public enum TipoFuncionalidadeEnum {

    CADASTRO(1,"Cadastro");

	private final int codigo;
	private String descricao;

	/**
	 * Construtor da enumeration
	 * @param _codigo    O codigo do registro
	 * @param _descricao A descri��o do registro
	 */
	private TipoFuncionalidadeEnum(int _codigo, String _descricao) {
		this.codigo = _codigo;
		this.descricao = _descricao;
	}
	
	/**
	 * Recupera elemento da enumeration pelo codigo 
	 * @param _codigo                   O codigo pesquisado
	 * @return                          O elemento da enumeration encontrando
	 * @throws IllegalArgumentException Se n�o tiver constante correspondente ao c�digo
	 */
	public static TipoFuncionalidadeEnum valueOf(int _codigo)
			throws IllegalArgumentException {
		TipoFuncionalidadeEnum value = null;
		for (TipoFuncionalidadeEnum element : TipoFuncionalidadeEnum.values()) {
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
