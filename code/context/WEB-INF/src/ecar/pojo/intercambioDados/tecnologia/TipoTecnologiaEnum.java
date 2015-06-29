package ecar.pojo.intercambioDados.tecnologia;


public enum TipoTecnologiaEnum {

    ARQUIVOTEXTO_PACINTER(1,"Arquivo Texto em Formato Específico","Importação de dados de empreendimentos","1.0.1");

    //A importação do web service via pacinter foi cancelada pela SUNNE depois de longo e tenebroso inverno/tempo de implementação.
    //WEBSERVICE_PACINTER(2, "Via Serviços Web","Importação PACInter WS","1.0.0");

	private final int codigo;
	private String descricao;
	private String especificacao;
	private String versao;

	/**
	 * Construtor da enumeration
	 * @param _codigo    O codigo do registro
	 * @param _descricao A descrição do registro
	 */
	private TipoTecnologiaEnum(int _codigo, String _descricao,String _especificacao,String _versao) {
		this.codigo = _codigo;
		this.descricao = _descricao;
		this.especificacao = _especificacao;
		this.versao = _versao;
	}
	
	/**
	 * Recupera elemento da enumeration pelo codigo 
	 * @param _codigo                   O codigo pesquisado
	 * @return                          O elemento da enumeration encontrando
	 * @throws IllegalArgumentException Se não tiver constante correspondente ao código
	 */
	public static TipoTecnologiaEnum valueOf(int _codigo)
			throws IllegalArgumentException {
		TipoTecnologiaEnum value = null;
		for (TipoTecnologiaEnum element : TipoTecnologiaEnum.values()) {
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

		/**
		 * @return the especificacao
		 */
		public String getEspecificacao() {
			return especificacao;
		}

		/**
		 * @return the versao
		 */
		public String getVersao() {
			return versao;
		}
	
}
