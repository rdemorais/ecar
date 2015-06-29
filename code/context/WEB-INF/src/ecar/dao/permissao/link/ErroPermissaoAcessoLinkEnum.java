package ecar.dao.permissao.link;

/**
 *
 * @author 70744416353
 */
public enum ErroPermissaoAcessoLinkEnum {

    /**
     *
     */
    PERMISSAO_ITEM(1, "Usuário possui permissão para acessar o item."),
        /**
         *
         */
        SEM_PERMISSAO_ITEM(2, "Usuário não possui mais permissão para acessar o item."),
        /**
         *
         */
        ITEM_INATIVO(3, "O item não está mais ativo."),
        /**
         *
         */
        ITEM_INEXISTENTE(4, "O item não existe mais."),
        /**
         *
         */
        ACOMPANHAMENTO_INEXISTENTE(5, "O acompanhamento não existe mais no sistema."),
        /**
         *
         */
        SEM_PERMISSAO_ACOMPANHAMENTO(6, "Usuário não possui mais permissão para acessar a parte de registro deste acompanhamento."),
        /**
         *
         */
        REFERENCIA_INEXISTENTE(7, "A referência do acompanhamento não existe mais no sistema."),
        /**
         *
         */
        TIPO_DE_ACOMPANHAMENTO_INEXISTENTE(8, "O tipo de acompanhamento não existe mais no sistema."),
        /**
         *
         */
        ESTRUTURA_INEXISTENTE(9, "A estrutura não existe mais no sistema. "),
        /**
         *
         */
        ABA_INEXISTENTE(10, "A funcionalidade não está mais configurada no sistema para esta estrutura."),
        /**
         *
         */
        ABA_SITUACAO_NAO_CONFIGURADA(11, "A funcionalidade não está mais configurada no sistema.");

	
	
	 private int codigo;
	 private String descricao;
	 
	 ErroPermissaoAcessoLinkEnum(int codigo, String descricao) {
		 this.codigo = codigo;
		 this.descricao = descricao;
	}
	 
	 
         /**
          *
          * @param _codigo
          * @return
          * @throws IllegalArgumentException
          */
         public static ErroPermissaoAcessoLinkEnum valueOf(int _codigo) throws IllegalArgumentException {
		 ErroPermissaoAcessoLinkEnum value = null;
		 for (ErroPermissaoAcessoLinkEnum element : ErroPermissaoAcessoLinkEnum.values()) {
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
